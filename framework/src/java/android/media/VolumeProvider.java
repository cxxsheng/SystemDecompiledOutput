package android.media;

/* loaded from: classes2.dex */
public abstract class VolumeProvider {
    public static final int VOLUME_CONTROL_ABSOLUTE = 2;
    public static final int VOLUME_CONTROL_FIXED = 0;
    public static final int VOLUME_CONTROL_RELATIVE = 1;
    private android.media.VolumeProvider.Callback mCallback;
    private final java.lang.String mControlId;
    private final int mControlType;
    private int mCurrentVolume;
    private final int mMaxVolume;

    public static abstract class Callback {
        public abstract void onVolumeChanged(android.media.VolumeProvider volumeProvider);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ControlType {
    }

    public VolumeProvider(int i, int i2, int i3) {
        this(i, i2, i3, null);
    }

    public VolumeProvider(int i, int i2, int i3, java.lang.String str) {
        this.mControlType = i;
        this.mMaxVolume = i2;
        this.mCurrentVolume = i3;
        this.mControlId = str;
    }

    public final int getVolumeControl() {
        return this.mControlType;
    }

    public final int getMaxVolume() {
        return this.mMaxVolume;
    }

    public final int getCurrentVolume() {
        return this.mCurrentVolume;
    }

    public final void setCurrentVolume(int i) {
        this.mCurrentVolume = i;
        if (this.mCallback != null) {
            this.mCallback.onVolumeChanged(this);
        }
    }

    public final java.lang.String getVolumeControlId() {
        return this.mControlId;
    }

    public void onSetVolumeTo(int i) {
    }

    public void onAdjustVolume(int i) {
    }

    public void setCallback(android.media.VolumeProvider.Callback callback) {
        this.mCallback = callback;
    }
}
