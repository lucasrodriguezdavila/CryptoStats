package org.lucasrodriguezdavila.cryptostats.models;

public class PlayerInfo {
    private final String uid;
    private boolean isScoreboardEnabled;

    public PlayerInfo(String uid) {
        this.uid = uid;
        this.isScoreboardEnabled = false;
    }

    public String getUid() {
        return uid;
    }


    public boolean isScoreboardEnabled() {
        return isScoreboardEnabled;
    }

    public void setScoreboardEnabled(boolean scoreboardEnabled) {
        isScoreboardEnabled = scoreboardEnabled;
    }
}
