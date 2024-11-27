package com.android.server.appop;

/* loaded from: classes.dex */
public abstract class OnOpModeChangedListener {
    private static final int UID_ANY = -2;
    private int mCallingPid;
    private int mCallingUid;
    private int mFlags;
    private int mWatchedOpCode;
    private int mWatchingUid;

    public abstract void onOpModeChanged(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    public abstract java.lang.String toString();

    OnOpModeChangedListener(int i, int i2, int i3, int i4, int i5) {
        this.mWatchingUid = i;
        this.mFlags = i2;
        this.mWatchedOpCode = i3;
        this.mCallingUid = i4;
        this.mCallingPid = i5;
    }

    public int getWatchingUid() {
        return this.mWatchingUid;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getWatchedOpCode() {
        return this.mWatchedOpCode;
    }

    public int getCallingUid() {
        return this.mCallingUid;
    }

    public int getCallingPid() {
        return this.mCallingPid;
    }

    public boolean isWatchingUid(int i) {
        return i == -2 || this.mWatchingUid < 0 || this.mWatchingUid == i;
    }

    public void onOpModeChanged(int i, int i2, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        if (java.util.Objects.equals(str2, "default:0")) {
            onOpModeChanged(i, i2, str);
        }
    }
}
