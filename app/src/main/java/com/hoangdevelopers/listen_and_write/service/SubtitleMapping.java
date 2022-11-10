package com.hoangdevelopers.listen_and_write.service;

import com.annimon.stream.Stream;
import com.hoangdevelopers.listen_and_write.models.Paragraph;
import com.hoangdevelopers.listen_and_write.models.Sentence;
import com.hoangdevelopers.listen_and_write.models.Subtitle;
import com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text.TimedText;
import com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text_1_0.timed_text.TimedText10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SubtitleMapping {
    public static final Subtitle timedTextToSubtitle(TimedText transcriptObject) {
        Subtitle subtitle = new Subtitle();
        subtitle.setLang("en");
        AtomicInteger i = new AtomicInteger(0);
        subtitle.setSentences(Stream.of(transcriptObject.getTexts()).map(text -> {
            i.set(i.get() + 1);
            return new Sentence(
                    i.get(),
                    text.getStart(),
                    text.getDuration(),
                    text.getContent(),
                    text.isIgnoreQuest());
        }).collect(com.annimon.stream.Collectors.toList()));
        subtitle.setParagraphs(SubtitleMapping.sentencesToParagraph(subtitle.getSentences()));
        return subtitle;
    }

    public static final Subtitle timedText10ToSubtitle(TimedText10 transcriptObject) {
        Subtitle subtitle = new Subtitle();
        subtitle.setLang("en");
        AtomicInteger i = new AtomicInteger(0);
        subtitle.setSentences(Stream.of(transcriptObject.getTexts()).map(text -> {
            i.set(i.get() + 1);
            return new Sentence(
                    i.get(),
                    Float.parseFloat(text.getBegin().replaceAll("([a-zA-Z])", "") + "f"),
                    Float.parseFloat(text.getEnd().replaceAll("([a-zA-z])", "") + "f") - Float.parseFloat(text.getBegin().replaceAll("([a-zA-z])", "") + "f"),
                    text.getContent(),
                    text.isIgnoreQuest());
        }).collect(com.annimon.stream.Collectors.toList()));
        subtitle.setParagraphs(SubtitleMapping.sentencesToParagraph(subtitle.getSentences()));
        return subtitle;
    }

    public static final List<Paragraph> sentencesToParagraph(List<Sentence> _sentences) {
        float PARAGRAPH_DURATION_MIN = 10f;
        List<Sentence> sentences = new ArrayList<>(_sentences);
        List<Paragraph> paragraphs = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(0);
        i.set(i.get() + 1);
        Paragraph paragraph = new Paragraph();
        paragraph.setIndex(i.get());
        while (sentences.size() > 0) {
            if (paragraph.getDuration().compareTo(PARAGRAPH_DURATION_MIN) < 0) {
                Sentence sentenceAdded = sentences.get(0);
                sentences.remove(0);
                paragraph.addSentence(sentenceAdded);
            } else {
                paragraphs.add(paragraph);
                i.set(i.get() + 1);
                paragraph = new Paragraph();
                paragraph.setIndex(i.get());
            }
        }
        return paragraphs;
    }
}
