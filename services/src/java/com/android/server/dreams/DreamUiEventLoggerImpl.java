package com.android.server.dreams;

/* loaded from: classes.dex */
public class DreamUiEventLoggerImpl implements com.android.server.dreams.DreamUiEventLogger {
    private final java.lang.String[] mLoggableDreamPrefixes;

    DreamUiEventLoggerImpl(java.lang.String[] strArr) {
        this.mLoggableDreamPrefixes = strArr;
    }

    @Override // com.android.server.dreams.DreamUiEventLogger
    public void log(com.android.internal.logging.UiEventLogger.UiEventEnum uiEventEnum, java.lang.String str) {
        int id = uiEventEnum.getId();
        if (id <= 0) {
            return;
        }
        if (!isFirstPartyDream(str)) {
            str = "other";
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DREAM_UI_EVENT_REPORTED, 0, id, 0, str);
    }

    private boolean isFirstPartyDream(java.lang.String str) {
        for (int i = 0; i < this.mLoggableDreamPrefixes.length; i++) {
            if (str.startsWith(this.mLoggableDreamPrefixes[i])) {
                return true;
            }
        }
        return false;
    }
}
