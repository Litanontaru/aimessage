package org.aim.aimessage.backend.ws;

import org.aim.aimessage.core.service.util.ChatEntryVO;

import java.util.List;

public class SpeakMessage {
    private List<ChatEntryVO> history;

    public SpeakMessage() {
    }

    public SpeakMessage(List<ChatEntryVO> history) {
        this.history = history;
    }

    public List<ChatEntryVO> getHistory() {
        return history;
    }

    public void setHistory(List<ChatEntryVO> history) {
        this.history = history;
    }
}
