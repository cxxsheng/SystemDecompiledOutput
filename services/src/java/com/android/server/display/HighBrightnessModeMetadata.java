package com.android.server.display;

/* loaded from: classes.dex */
class HighBrightnessModeMetadata {
    private final java.util.ArrayDeque<com.android.server.display.HbmEvent> mEvents = new java.util.ArrayDeque<>();
    private long mRunningStartTimeMillis = -1;

    HighBrightnessModeMetadata() {
    }

    public long getRunningStartTimeMillis() {
        return this.mRunningStartTimeMillis;
    }

    public void setRunningStartTimeMillis(long j) {
        this.mRunningStartTimeMillis = j;
    }

    public java.util.ArrayDeque<com.android.server.display.HbmEvent> getHbmEventQueue() {
        return this.mEvents;
    }

    public void addHbmEvent(com.android.server.display.HbmEvent hbmEvent) {
        this.mEvents.addFirst(hbmEvent);
    }
}
