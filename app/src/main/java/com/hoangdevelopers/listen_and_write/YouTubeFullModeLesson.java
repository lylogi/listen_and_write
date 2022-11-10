package com.hoangdevelopers.listen_and_write;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.hoangdevelopers.listen_and_write.adapter.InputAdapter;
import com.hoangdevelopers.listen_and_write.adapter.KeyboardAdapter;
import com.hoangdevelopers.listen_and_write.framework.BaseScript;
import com.hoangdevelopers.listen_and_write.framework.Lesson;
import com.hoangdevelopers.listen_and_write.models.Quiz;
import com.hoangdevelopers.listen_and_write.models.WordQuiz;
import com.hoangdevelopers.listen_and_write.modules.LessonFactory;
import com.hoangdevelopers.listen_and_write.modules.players.YoutubeControl;
import com.hoangdevelopers.listen_and_write.modules.scripts.BlankModeScript;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.PlayerUiController;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class YouTubeFullModeLesson extends AppCompatActivity {
    private final Lesson lesson = LessonFactory.createYouTubeFullModeLesson();
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();

        String video_url = intent.getStringExtra("video_url");
        String sub_url = intent.getStringExtra("sub_url");
        String sub_type = intent.getStringExtra("sub_type");
        String video_title = intent.getStringExtra("video_title");

        setContentView(R.layout.activity_you_tube_full_mode_lesson);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(video_title);
        setSupportActionBar(toolbar);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.text_box);
        YouTubeFullModeLesson me = this;
        TextView currentSentenceTv = findViewById(R.id.youtube_full_mode_current_sentences);
        TextView totalSentenceTv = findViewById(R.id.youtube_full_mode_total_sentences);
        TextView hintTv = findViewById(R.id.youtube_full_mode_hint_text);
        Button hintBtn = findViewById(R.id.youtube_full_mode_hint_btn);
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter(this);
        List<InputAdapter> inputAdapters = new ArrayList<>();
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                PlayerUiController playerUiController = youTubePlayerView.getPlayerUiController();
                playerUiController.showPlayPauseButton(false);
                ((YoutubeControl) lesson.getPlayer()).setVideoId(video_url);
                ((YoutubeControl) lesson.getPlayer()).setSubUrl(sub_url);
                ((YoutubeControl) lesson.getPlayer()).setSubType(sub_type);
                ((YoutubeControl) lesson.getPlayer()).setYouTubePlayer(youTubePlayer);
                ((BlankModeScript) lesson.getScript()).setOnCompletedLesson(new BaseScript.OnCompletedLesson() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlertDialog();
                            }
                        });
                    }
                });
                lesson.getScript().setOnFilledWordQuiz(new BaseScript.OnFilledWordQuiz() {
                    @Override
                    public void run(Quiz quiz) {
                        Optional<WordQuiz> wordQuiz = quiz.getNextWordQuizBlank();
                        if (wordQuiz.isPresent()) {
                            InputAdapter inputAdapter = Stream.of(inputAdapters).filter(i -> i.getWordQuiz().getId().equals(wordQuiz.get().getId())).findFirst().get();
                            keyboardAdapter.setInputAdapter(inputAdapter);
                        }
                    }
                });
                ((BlankModeScript) lesson.getScript()).setOnQuizChange(new BaseScript.OnQuizChange() {
                    @Override
                    public void run(Quiz quiz) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                currentSentenceTv.setText(Integer.toString(quiz.getIndex()));
                                totalSentenceTv.setText(Integer.toString(lesson.getPlayer().getSubtitle().getParagraphs().size()));
                                flowLayout.removeAllViews();
                                hintTv.setText("");
                                hintBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Optional<WordQuiz> wordQuiz = quiz.getNextWordQuizBlank();
                                        if (wordQuiz.isPresent()) {
                                            hintTv.setText(wordQuiz.get().getWordNormalize());
                                        }
                                    }
                                });
                                inputAdapters.clear();
                                for (WordQuiz wordQuiz : quiz.getWordQuizs()) {
                                    if (wordQuiz.isBlank()) {
                                        InputAdapter inputAdapter = new InputAdapter(getApplicationContext(), wordQuiz);
                                        inputAdapter.setOnTextChanged(new InputAdapter.OnTextChanged() {
                                            private Timer timer = new Timer();
                                            private final long DELAY = 1000; // milliseconds

                                            @Override
                                            public void run(String str) {
                                                timer.cancel();
                                                timer = new Timer();
                                                timer.schedule(
                                                        new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                lesson.getScript().onTextInput(wordQuiz, str);
                                                            }
                                                        },
                                                        DELAY
                                                );
                                            }
                                        });
                                        inputAdapters.add(inputAdapter);
                                        flowLayout.addView(inputAdapter.getView());
                                    } else {
                                        TextView textView = new TextView(me);
                                        textView.setLayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                                                FlowLayout.LayoutParams.WRAP_CONTENT));
                                        textView.setText(wordQuiz.getWord());
                                        textView.setPadding(0, 20, 10, 0);
                                        flowLayout.addView(textView);
                                    }
                                }
                                if (inputAdapters.size() > 0) {
                                    keyboardAdapter.setInputAdapter(inputAdapters.get(0));
                                }
                            }
                        });


                    }
                });
                lesson.init();
            }
        });

    }

    @Override
    public void onDestroy() {
        lesson.destroy();
        super.onDestroy();
        youTubePlayerView.release();
    }

    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_completed_lesson, null);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Congratulations");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("REPLAY", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((BlankModeScript) lesson.getScript()).replay();
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                openHome();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void openHome() {
        Intent intent = new Intent(getApplicationContext(), YouTubeFullModeLesson.class);
        getApplicationContext().startActivity(intent);

    }
}
