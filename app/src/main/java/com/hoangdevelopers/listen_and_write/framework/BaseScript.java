package com.hoangdevelopers.listen_and_write.framework;

import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hoangdevelopers.listen_and_write.models.Quiz;
import com.hoangdevelopers.listen_and_write.models.WordQuiz;
import com.hoangdevelopers.listen_and_write.modules.scripts.BlankModeScript;
import com.hoangdevelopers.listen_and_write.service.QuizMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.halfbit.tinymachine.StateHandler;
import de.halfbit.tinymachine.TinyMachine;

public abstract class BaseScript {
    private Lesson lesson;

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public abstract void init();

    protected TinyMachine tinyMachine;
    protected Quiz quiz;
    private OnQuizChange onQuizChange;
    private OnCompletedLesson onCompletedLesson;
    private OnFilledWordQuiz onFilledWordQuiz;

    public OnQuizChange getOnQuizChange() {
        return onQuizChange;
    }

    public void setOnQuizChange(OnQuizChange onQuizChange) {
        this.onQuizChange = onQuizChange;
    }

    public OnCompletedLesson getOnCompletedLesson() {
        return onCompletedLesson;
    }

    public void setOnCompletedLesson(OnCompletedLesson onCompletedLesson) {
        this.onCompletedLesson = onCompletedLesson;
    }

    public OnFilledWordQuiz getOnFilledWordQuiz() {
        return onFilledWordQuiz;
    }

    public void setOnFilledWordQuiz(OnFilledWordQuiz onFilledWordQuiz) {
        this.onFilledWordQuiz = onFilledWordQuiz;
    }

    public static class BaseScriptHandler {
        private static final List<String> states = new ArrayList<String>(Arrays.asList(
                "PEDING",
                "CHECK_HAVE_SENTENCE",
                "PREPARE_QUIZ_DATA",
                "PREPARE_FINISH",
                "DOING_QUIZ",
                "CHECK_FINISH_QUIZ",
                "COMPLETED_QUIZ",
                "NEXT_SENTENCE",
                "FINISH",
                "STOP",
                "PREPARE_TO_PLAY"));
        public final static int PEDING = 0;
        public final static int CHECK_HAVE_SENTENCE = 1;
        public final static int PREPARE_QUIZ_DATA = 2;
        public final static int PREPARE_FINISH = 3;
        public final static int DOING_QUIZ = 4;
        public final static int CHECK_FINISH_QUIZ = 5;
        public final static int COMPLETED_QUIZ = 6;
        public final static int NEXT_SENTENCE = 8;
        public final static int FINISH = 9;
        public final static int STOP = 10;
        public final static int PREPARE_TO_PLAY = 11;

        protected BaseScript script;

        public BaseScriptHandler(BaseScript _script) {
            script = _script;
        }
        // Handlers for ANY_STATE

        @StateHandler(state = StateHandler.STATE_ANY, type = StateHandler.Type.OnEntry)
        public void onEntryState(TinyMachine tm) {
            // This method is called when machine enters any new state
            Log.d("BaseScript", " - Event = " + states.get(tm.getCurrentState()));
        }

        @StateHandler(state = CHECK_HAVE_SENTENCE, type = StateHandler.Type.OnEntry)
        public void onEntryCheckHaveSentence(TinyMachine tm) {
            if (script.getLesson().getPlayer().checkHaveParagraph()) {
                tm.transitionTo(PREPARE_QUIZ_DATA);
                return;
            }
            tm.transitionTo(PREPARE_FINISH);
        }

        @StateHandler(state = PREPARE_QUIZ_DATA, type = StateHandler.Type.OnEntry)
        public void onEntryPrepareQuizData(TinyMachine tm) {
            script.quiz = QuizMapping.SentenceToQuiz(script.getLesson().getPlayer().getCurrentParagraph(), new Long(1));
            script.onQuizChange.run(script.quiz);
            tm.transitionTo(DOING_QUIZ);
        }

        @StateHandler(state = DOING_QUIZ, type = StateHandler.Type.OnEntry)
        public void onEntryDoingQuiz(TinyMachine tm) {
            script.getLesson().getTimelineControl().requestPlay(script.quiz.getStart(), script.quiz.getDuration(), script.quiz);
        }

        @StateHandler(state = CHECK_FINISH_QUIZ, type = StateHandler.Type.OnEntry)
        public void onEntryCheckFinishQuiz(TinyMachine tm) {
            if (Stream.of(script.quiz.getWordQuizs()).filter(w -> !w.isDone()).collect(Collectors.toList()).size() == 0) {
                tm.transitionTo(COMPLETED_QUIZ);
            } else {
                tm.transitionTo(DOING_QUIZ);
            }
        }

        @StateHandler(state = COMPLETED_QUIZ, type = StateHandler.Type.OnEntry)
        public void onEntryCompletedQuiz(TinyMachine tm) {
            tm.transitionTo(NEXT_SENTENCE);
        }

        @StateHandler(state = NEXT_SENTENCE, type = StateHandler.Type.OnEntry)
        public void onEntryNextSentence(TinyMachine tm) {
            script.getLesson().getPlayer().nextParagraph();
            tm.transitionTo(CHECK_HAVE_SENTENCE);
        }

        @StateHandler(state = PREPARE_TO_PLAY, type = StateHandler.Type.OnEntry)
        public void onEntryPrepareToPlay(TinyMachine tm) {
            script.getLesson().getPlayer().reset();
            tm.transitionTo(CHECK_HAVE_SENTENCE);
        }

        @StateHandler(state = PREPARE_FINISH, type = StateHandler.Type.OnEntry)
        public void onEntryPrepareToFinish(TinyMachine tm) {
            script.getLesson().getTimelineControl().stop();
            script.onCompletedLesson.run();
        }
    }

    public void preInit() {
        getLesson().getPlayer().setOnPlayerReady(new BasePlayer.OnPlayerReady() {
            @Override
            public void run() {
                tinyMachine.transitionTo(BlankModeScript.ScriptHandlerImp.CHECK_HAVE_SENTENCE);
            }
        });
        getLesson().getTimelineControl().setOnReachTarget(new TimelineControl.OnReachTarget() {
            @Override
            public void run(String id) {
                if (tinyMachine.getCurrentState() == BaseScriptHandler.DOING_QUIZ) {
                    if (quiz.getId().equals(id) && quiz.isIgnoreQuest()) {
                        tinyMachine.transitionTo(BaseScriptHandler.CHECK_FINISH_QUIZ);
                    } else {
                        getLesson().getTimelineControl().requestPlay(quiz.getStart(), quiz.getDuration(), quiz);
                    }
                }
            }
        });

    }

    public interface OnQuizChange {
        void run(Quiz quiz);
    }

    public interface OnCompletedLesson {
        void run();
    }

    public interface OnFilledWordQuiz {
        void run(Quiz quiz);
    }

    public void onTextInput(WordQuiz wordQuiz, String input) {
        if (tinyMachine.getCurrentState() == BaseScriptHandler.DOING_QUIZ) {
            if (wordQuiz.getWordNormalize().equals(input.toLowerCase())) {
                wordQuiz.setDone(true);
                onFilledWordQuiz.run(quiz);
                tinyMachine.transitionTo(BaseScriptHandler.CHECK_FINISH_QUIZ);

            }
        }
    }

    public void stop() {
        tinyMachine.transitionTo(BaseScriptHandler.STOP);
    }

    public void replay() {
        if (tinyMachine.getCurrentState() == BaseScriptHandler.PREPARE_FINISH) {
            tinyMachine.transitionTo(BaseScriptHandler.PREPARE_TO_PLAY);
        }
    }
}
