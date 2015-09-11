package org.aim.aimessage.core.service.util;

import java.util.Date;

public class SpeakMessage {
    private String user;
    private Boolean updated;
    private Date dt;
    private String phrase;

    public SpeakMessage(String user, Boolean updated, Date dt, String phrase) {
        this.user = user;
        this.updated = updated;
        this.dt = dt;
        this.phrase = phrase;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
