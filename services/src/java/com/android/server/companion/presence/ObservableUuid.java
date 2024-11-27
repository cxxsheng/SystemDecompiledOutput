package com.android.server.companion.presence;

/* loaded from: classes.dex */
public class ObservableUuid {
    private final java.lang.String mPackageName;
    private final long mTimeApprovedMs;
    private final int mUserId;
    private final android.os.ParcelUuid mUuid;

    public ObservableUuid(int i, @android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull java.lang.String str, java.lang.Long l) {
        this.mUserId = i;
        this.mUuid = parcelUuid;
        this.mPackageName = str;
        this.mTimeApprovedMs = l.longValue();
    }

    public int getUserId() {
        return this.mUserId;
    }

    public android.os.ParcelUuid getUuid() {
        return this.mUuid;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public long getTimeApprovedMs() {
        return this.mTimeApprovedMs;
    }
}
