package com.example.listen_and_write.service;

import com.annimon.stream.Stream;
import com.example.listen_and_write.models.Sentence;
import com.example.listen_and_write.models.Subtitle;
import com.example.srtparser.TranscriptObject;

public class SubtitleMapping {
    public static final Subtitle transcriptObjectToSubtitle(TranscriptObject transcriptObject) {
        Subtitle subtitle = new Subtitle();
        subtitle.setLang("en");

        subtitle.setSentences(Stream.of(transcriptObject.getTexts()).map(text -> new Sentence(text.getStart(), text.getDuration(), text.getContent())).collect(com.annimon.stream.Collectors.toList()));
        return subtitle;
    }
}
