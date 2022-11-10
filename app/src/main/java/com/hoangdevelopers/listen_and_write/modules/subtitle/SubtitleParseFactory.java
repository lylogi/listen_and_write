package com.hoangdevelopers.listen_and_write.modules.subtitle;

import com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text.TimedTextController;
import com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text_1_0.timed_text.TimedTextController10;

public class SubtitleParseFactory {
    public final static class SUB_TYPE {
        public final static String TIMED_TEXT = "TIMED_TEXT";
        public final static String TIMED_TEXT_1_0 = "TIMED_TEXT_1_0";
    }

    public final static BaseSubtitleController get(String subType) {
        if (SUB_TYPE.TIMED_TEXT.equals(subType)) {
            return new TimedTextController();
        } else if (SUB_TYPE.TIMED_TEXT_1_0.equals(subType)) {
            return new TimedTextController10();
        }
        return null;
    }
}
