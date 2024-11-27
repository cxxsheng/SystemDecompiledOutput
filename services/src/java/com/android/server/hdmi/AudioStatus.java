package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class AudioStatus {
    public static final int MAX_VOLUME = 100;
    public static final int MIN_VOLUME = 0;
    boolean mMute;
    int mVolume;

    public AudioStatus(int i, boolean z) {
        this.mVolume = java.lang.Math.max(java.lang.Math.min(i, 100), 0);
        this.mMute = z;
    }

    public int getVolume() {
        return this.mVolume;
    }

    public boolean getMute() {
        return this.mMute;
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (!(obj instanceof com.android.server.hdmi.AudioStatus)) {
            return false;
        }
        com.android.server.hdmi.AudioStatus audioStatus = (com.android.server.hdmi.AudioStatus) obj;
        return this.mVolume == audioStatus.mVolume && this.mMute == audioStatus.mMute;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mVolume), java.lang.Boolean.valueOf(this.mMute));
    }

    public java.lang.String toString() {
        return "AudioStatus mVolume:" + this.mVolume + " mMute:" + this.mMute;
    }
}
