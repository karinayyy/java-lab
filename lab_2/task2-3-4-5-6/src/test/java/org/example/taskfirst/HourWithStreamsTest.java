package org.example.taskfirst;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HourWithStreamsTest {
    @Test
    void containsWord() {
        HourWithStreams hours = new HourWithStreams();
        hours.setComment("Very busy - test comment");
        assertTrue(hours.containsWord("Very"));
        assertTrue(hours.containsWord("busy"));
        assertFalse(hours.containsWord("Vary"));
        assertFalse(hours.containsWord("bisy"));
    }
}