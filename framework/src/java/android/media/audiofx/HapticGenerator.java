package android.media.audiofx;

/* loaded from: classes2.dex */
public class HapticGenerator extends android.media.audiofx.AudioEffect implements java.lang.AutoCloseable {
    private static final java.lang.String TAG = "HapticGenerator";
    private android.media.audiofx.AudioEffect mVolumeControlEffect;

    public static boolean isAvailable() {
        return android.media.AudioManager.isHapticPlaybackSupported() && android.media.audiofx.AudioEffect.isEffectTypeAvailable(android.media.audiofx.AudioEffect.EFFECT_TYPE_HAPTIC_GENERATOR);
    }

    public static android.media.audiofx.HapticGenerator create(int i) {
        return new android.media.audiofx.HapticGenerator(i);
    }

    private HapticGenerator(int i) {
        super(EFFECT_TYPE_HAPTIC_GENERATOR, EFFECT_TYPE_NULL, 0, i);
        this.mVolumeControlEffect = new android.media.audiofx.AudioEffect(android.media.audiofx.AudioEffect.EFFECT_TYPE_NULL, java.util.UUID.fromString("119341a0-8469-11df-81f9-0002a5d5c51b"), 0, i);
    }

    @Override // android.media.audiofx.AudioEffect
    public int setEnabled(boolean z) {
        int enabled = super.setEnabled(z);
        if (enabled == 0 && (this.mVolumeControlEffect == null || this.mVolumeControlEffect.setEnabled(z) != 0)) {
            android.util.Log.w(TAG, "Failed to enable volume control effect for HapticGenerator");
        }
        return enabled;
    }

    @Override // android.media.audiofx.AudioEffect
    public void release() {
        if (this.mVolumeControlEffect != null) {
            this.mVolumeControlEffect.release();
        }
        super.release();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        release();
    }
}
