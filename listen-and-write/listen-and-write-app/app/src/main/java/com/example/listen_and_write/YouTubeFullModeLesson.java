package com.example.listen_and_write;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.example.listen_and_write.framework.BaseScript;
import com.example.listen_and_write.framework.Lesson;
import com.example.listen_and_write.models.Quiz;
import com.example.listen_and_write.models.WordQuiz;
import com.example.listen_and_write.modules.LessonFactory;
import com.example.listen_and_write.modules.players.YoutubeControl;
import com.example.listen_and_write.modules.scripts.BlankModeScript;
import com.google.android.material.textfield.TextInputEditText;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.wefika.flowlayout.FlowLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

public class YouTubeFullModeLesson extends AppCompatActivity {
    private final Lesson lesson = LessonFactory.createYouTubeFullModeLesson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_full_mode_lesson);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.text_box);
        YouTubeFullModeLesson me = this;
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                ((YoutubeControl) lesson.getPlayer()).setVideoId("pZw9veQ76fo");
                ((YoutubeControl) lesson.getPlayer()).setYouTubePlayer(youTubePlayer);
                ((BlankModeScript) lesson.getScript()).setOnQuizChange(new BaseScript.OnQuizChange() {
                    @Override
                    public void run(Quiz quiz) {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                flowLayout.removeAllViews();
                                for(WordQuiz wordQuiz: quiz.getWordQuizs()) {
                                    if (wordQuiz.isBlank()) {
                                        TextInputEditText textInputEditText = new TextInputEditText(me);
                                        textInputEditText.setLayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                                                FlowLayout.LayoutParams.WRAP_CONTENT));
                                        textInputEditText.setTextSize(14);
//                                textInputEditText.setText(wordQuiz.getWord());
                                        textInputEditText.setMinWidth((int) (80 * (getResources().getDisplayMetrics().density)));

                                        textInputEditText.addTextChangedListener(new TextWatcher() {
                                            private Timer timer=new Timer();
                                            private final long DELAY = 1000; // milliseconds
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                timer.cancel();
                                                timer = new Timer();
                                                timer.schedule(
                                                        new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                lesson.getScript().onTextInput(textInputEditText, wordQuiz, s.toString());
//                                                        if (wordQuiz.getWord().indexOf(s.toString()) != 0) {
//                                                            textInputEditText.getText();
//
//                                                        } else if (wordQuiz.getWord().toLowerCase().equals(s.toString().toLowerCase())) {
//                                                            textInputEditText.setOutlineAmbientShadowColor(0x00ff00);
//                                                            textInputEditText.setFocusable(false);
//                                                        } else {
//                                                            textInputEditText.setOutlineAmbientShadowColor(0xffe0);
//
//                                                        }
                                                            }
                                                        },
                                                        DELAY
                                                );

                                            }
                                        });
                                        flowLayout.addView(textInputEditText);
                                    } else {
                                        TextView textView = new TextView(me);
                                        textView.setLayoutParams(new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                                                FlowLayout.LayoutParams.WRAP_CONTENT));
                                        textView.setText(wordQuiz.getWord());
                                        textView.setPadding(0, 20, 10, 0);
                                        flowLayout.addView(textView);
                                    }
                                }

                            }
                        });


                    }
                });
                lesson.init();
            }
        });

    }

}
