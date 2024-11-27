package com.android.server.wm;

/* loaded from: classes3.dex */
final class ActivityResult extends android.app.ResultInfo {
    final com.android.server.wm.ActivityRecord mFrom;

    public ActivityResult(com.android.server.wm.ActivityRecord activityRecord, java.lang.String str, int i, int i2, android.content.Intent intent, android.os.IBinder iBinder) {
        super(str, i, i2, intent, iBinder);
        this.mFrom = activityRecord;
    }
}
