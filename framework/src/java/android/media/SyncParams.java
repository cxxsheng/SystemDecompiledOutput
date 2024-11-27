package android.media;

/* loaded from: classes2.dex */
public final class SyncParams {
    public static final int AUDIO_ADJUST_MODE_DEFAULT = 0;
    public static final int AUDIO_ADJUST_MODE_RESAMPLE = 2;
    public static final int AUDIO_ADJUST_MODE_STRETCH = 1;
    private static final int SET_AUDIO_ADJUST_MODE = 2;
    private static final int SET_FRAME_RATE = 8;
    private static final int SET_SYNC_SOURCE = 1;
    private static final int SET_TOLERANCE = 4;
    public static final int SYNC_SOURCE_AUDIO = 2;
    public static final int SYNC_SOURCE_DEFAULT = 0;
    public static final int SYNC_SOURCE_SYSTEM_CLOCK = 1;
    public static final int SYNC_SOURCE_VSYNC = 3;
    private int mSet = 0;
    private int mAudioAdjustMode = 0;
    private int mSyncSource = 0;
    private float mTolerance = 0.0f;
    private float mFrameRate = 0.0f;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioAdjustMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SyncSource {
    }

    public android.media.SyncParams allowDefaults() {
        this.mSet |= 7;
        return this;
    }

    public android.media.SyncParams setAudioAdjustMode(int i) {
        this.mAudioAdjustMode = i;
        this.mSet |= 2;
        return this;
    }

    public int getAudioAdjustMode() {
        if ((this.mSet & 2) == 0) {
            throw new java.lang.IllegalStateException("audio adjust mode not set");
        }
        return this.mAudioAdjustMode;
    }

    public android.media.SyncParams setSyncSource(int i) {
        this.mSyncSource = i;
        this.mSet |= 1;
        return this;
    }

    public int getSyncSource() {
        if ((this.mSet & 1) == 0) {
            throw new java.lang.IllegalStateException("sync source not set");
        }
        return this.mSyncSource;
    }

    public android.media.SyncParams setTolerance(float f) {
        if (f < 0.0f || f >= 1.0f) {
            throw new java.lang.IllegalArgumentException("tolerance must be less than one and non-negative");
        }
        this.mTolerance = f;
        this.mSet |= 4;
        return this;
    }

    public float getTolerance() {
        if ((this.mSet & 4) == 0) {
            throw new java.lang.IllegalStateException("tolerance not set");
        }
        return this.mTolerance;
    }

    public android.media.SyncParams setFrameRate(float f) {
        this.mFrameRate = f;
        this.mSet |= 8;
        return this;
    }

    public float getFrameRate() {
        if ((this.mSet & 8) == 0) {
            throw new java.lang.IllegalStateException("frame rate not set");
        }
        return this.mFrameRate;
    }
}
