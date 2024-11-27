package com.android.server.media;

/* loaded from: classes2.dex */
public abstract class MediaSessionRecordImpl {
    static final java.util.concurrent.atomic.AtomicInteger sNextMediaSessionRecordId = new java.util.concurrent.atomic.AtomicInteger(1);
    int mUniqueId;

    public abstract void adjustVolume(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, int i3, int i4, boolean z2);

    public abstract boolean canHandleVolumeKey();

    public abstract boolean checkPlaybackActiveState(boolean z);

    public abstract void close();

    public abstract void dump(java.io.PrintWriter printWriter, java.lang.String str);

    public abstract android.app.ForegroundServiceDelegationOptions getForegroundServiceDelegationOptions();

    public abstract java.lang.String getPackageName();

    public abstract int getSessionPolicies();

    public abstract int getUid();

    public abstract int getUserId();

    public abstract boolean isActive();

    public abstract boolean isClosed();

    public abstract boolean isPlaybackTypeLocal();

    public abstract boolean isSystemPriority();

    public abstract boolean sendMediaButton(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, int i3, android.os.ResultReceiver resultReceiver);

    public abstract void setSessionPolicies(int i);

    public int getUniqueId() {
        return this.mUniqueId;
    }

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof com.android.server.media.MediaSessionRecordImpl) && this.mUniqueId == ((com.android.server.media.MediaSessionRecordImpl) obj).mUniqueId) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mUniqueId));
    }
}
