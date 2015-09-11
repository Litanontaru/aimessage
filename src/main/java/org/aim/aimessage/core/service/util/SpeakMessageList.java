package org.aim.aimessage.core.service.util;

import org.aim.aimessage.core.service.util.SpeakMessage;

import java.util.List;

public class SpeakMessageList {
    private List<SpeakMessage> history;

    public SpeakMessageList() {
    }

    public SpeakMessageList(List<SpeakMessage> history) {
        this.history = history;
    }

    public List<SpeakMessage> getHistory() {
        return history;
    }

    public void setHistory(List<SpeakMessage> history) {
        this.history = history;
    }
}
