package com.android.server.contentcapture;

/* loaded from: classes.dex */
public abstract class ContentCaptureManagerInternal {
    @android.annotation.Nullable
    public abstract android.content.ContentCaptureOptions getOptionsForPackage(int i, @android.annotation.NonNull java.lang.String str);

    public abstract boolean isContentCaptureServiceForUser(int i, int i2);

    public abstract void notifyActivityEvent(int i, @android.annotation.NonNull android.content.ComponentName componentName, int i2, @android.annotation.NonNull android.app.assist.ActivityId activityId);

    public abstract boolean sendActivityAssistData(int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.Bundle bundle);
}
