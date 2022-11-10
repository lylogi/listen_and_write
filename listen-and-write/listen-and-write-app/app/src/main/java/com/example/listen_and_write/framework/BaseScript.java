package com.example.listen_and_write.framework;

import android.util.Log;

import com.annimon.stream.Collectors;
import com.example.listen_and_write.models.Quiz;
import com.example.listen_and_write.models.WordQuiz;
import com.example.listen_and_write.modules.scripts.BlankModeScript;
import com.example.listen_and_write.service.QuizMapping;
import com.google.android.material.textfield.TextInputEditText;

import com.annimon.stream.Stream;

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
    public abstract  void init();
    protected TinyMachine tinyMachine;
    protected Quiz quiz;
    private OnQuizChange onQuizChange;

    public OnQuizChange getOnQuizChange() {
        return onQuizChange;
    }

    public void setOnQuizChange(OnQuizChange onQuizChange) {
        this.onQuizChange = onQuizChange;
    }

    public static class BaseScriptHandler {
        private static final List<String> states = new ArrayList<String>(Arrays.asList("PEDING", "CHECK_HAVE_SENTENCE", "PREPARE_QUIZ_DATA", "PREPARE_FINISH", "DOING_QUIZ", "NEXT_SENTENCE"));
        public final static int PEDING = 0;
        public final static int CHECK_HAVE_SENTENCE = 1;
        public final static int PREPARE_QUIZ_DATA = 2;
        public final static int PREPARE_FINISH = 3;
        public final static int DOING_QUIZ = 4;
        public final static int NEXT_SENTENCE = 5;
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
            if (script.getLesson().getPlayer().checkHaveSentence()) {
                tm.transitionTo(PREPARE_QUIZ_DATA);
                return;
            }
            tm.transitionTo(PREPARE_FINISH);
        }
        @StateHandler(state = PREPARE_QUIZ_DATA, type = StateHandler.Type.OnEntry)
        public void onEntryPrepareQuizData(TinyMachine tm) {
            script.quiz = QuizMapping.SentenceToQuiz(script.getLesson().getPlayer().getCurrentSentence(),new Long(1));
            script.onQuizChange.run(script.quiz);
            tm.transitionTo(DOING_QUIZ);
        }
        @StateHandler(state = DOING_QUIZ, type = StateHandler.Type.OnEntry)
        public void onEntryDoingQuiz(TinyMachine tm) {
            script.getLesson().getTimelineControl().requestPlay(script.quiz.getStart(), script.quiz.getDuration());
        }
        @StateHandler(state = NEXT_SENTENCE, type = StateHandler.Type.OnEntry)
        public void onEntryNextSentence(TinyMachine tm) {
            script.getLesson().getPlayer().nextSentence();
            tm.transitionTo(CHECK_HAVE_SENTENCE);
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
            public void run() {
                if(tinyMachine.getCurrentState() == BaseScriptHandler.DOING_QUIZ) {
                    getLesson().getTimelineControl().requestPlay(quiz.getStart(), quiz.getDuration());
                }
            }
        });

    }
    public interface OnQuizChange {
        void run (Quiz quiz);
    }
    public void onTextInput(TextInputEditText textInputEditText, WordQuiz wordQuiz, String input) {
        if (tinyMachine.getCurrentState() == BaseScriptHandler.DOING_QUIZ) {
            if (wordQuiz.getWord().toLowerCase().equals(input.toLowerCase())) {
                wordQuiz.setDone(true);
                if (Stream.of(quiz.getWordQuizs()).filter(w -> !w.isDone()).collect(Collectors.toList()).size() == 0) {
                    tinyMachine.transitionTo(BaseScriptHandler.NEXT_SENTENCE);
                }
            }
        }
    }
}
