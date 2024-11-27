package com.android.server.dreams;

/* loaded from: classes.dex */
public interface DreamUiEventLogger {
    void log(@android.annotation.NonNull com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, @android.annotation.NonNull java.lang.String str);

    public enum DreamUiEventEnum implements com.android.internal.logging.UiEventLogger.UiEventEnum {
        DREAM_START(577),
        DREAM_STOP(com.android.internal.util.FrameworkStatsLog.HOTWORD_AUDIO_EGRESS_EVENT_REPORTED);

        private final int mId;

        DreamUiEventEnum(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }
}
