package android.os;

/* loaded from: classes3.dex */
public abstract class Vibrator {
    private static final java.lang.String TAG = "Vibrator";
    public static final int VIBRATION_EFFECT_SUPPORT_NO = 2;
    public static final int VIBRATION_EFFECT_SUPPORT_UNKNOWN = 0;
    public static final int VIBRATION_EFFECT_SUPPORT_YES = 1;
    public static final int VIBRATION_INTENSITY_HIGH = 3;
    public static final int VIBRATION_INTENSITY_LOW = 1;
    public static final int VIBRATION_INTENSITY_MEDIUM = 2;
    public static final int VIBRATION_INTENSITY_OFF = 0;
    private final java.lang.String mPackageName;
    private final android.content.res.Resources mResources;
    private volatile android.os.vibrator.VibrationConfig mVibrationConfig;

    @android.annotation.SystemApi
    public interface OnVibratorStateChangedListener {
        void onVibratorStateChanged(boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VibrationEffectSupport {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VibrationIntensity {
    }

    public abstract void cancel();

    public abstract void cancel(int i);

    public abstract boolean hasAmplitudeControl();

    public abstract boolean hasVibrator();

    public abstract void vibrate(int i, java.lang.String str, android.os.VibrationEffect vibrationEffect, java.lang.String str2, android.os.VibrationAttributes vibrationAttributes);

    public Vibrator() {
        this.mPackageName = android.app.ActivityThread.currentPackageName();
        this.mResources = null;
    }

    protected Vibrator(android.content.Context context) {
        this.mPackageName = context.getOpPackageName();
        this.mResources = context.getResources();
    }

    public android.os.VibratorInfo getInfo() {
        return android.os.VibratorInfo.EMPTY_VIBRATOR_INFO;
    }

    private android.os.vibrator.VibrationConfig getConfig() {
        if (this.mVibrationConfig == null) {
            android.content.res.Resources resources = this.mResources;
            if (resources == null) {
                android.app.ContextImpl systemContext = android.app.ActivityThread.currentActivityThread().getSystemContext();
                resources = systemContext != null ? systemContext.getResources() : null;
            }
            this.mVibrationConfig = new android.os.vibrator.VibrationConfig(resources);
        }
        return this.mVibrationConfig;
    }

    public int getDefaultVibrationIntensity(int i) {
        return getConfig().getDefaultVibrationIntensity(i);
    }

    public boolean isDefaultKeyboardVibrationEnabled() {
        return getConfig().isDefaultKeyboardVibrationEnabled();
    }

    public int getId() {
        return getInfo().getId();
    }

    public boolean hasFrequencyControl() {
        return getInfo().hasFrequencyControl();
    }

    public boolean areVibrationFeaturesSupported(android.os.VibrationEffect vibrationEffect) {
        return getInfo().areVibrationFeaturesSupported(vibrationEffect);
    }

    public boolean hasExternalControl() {
        return getInfo().hasCapability(8L);
    }

    public float getResonantFrequency() {
        return getInfo().getResonantFrequencyHz();
    }

    public float getQFactor() {
        return getInfo().getQFactor();
    }

    public android.os.vibrator.VibratorFrequencyProfile getFrequencyProfile() {
        android.os.VibratorInfo.FrequencyProfile frequencyProfile = getInfo().getFrequencyProfile();
        if (frequencyProfile.isEmpty()) {
            return null;
        }
        return new android.os.vibrator.VibratorFrequencyProfile(frequencyProfile);
    }

    public float getHapticChannelMaximumAmplitude() {
        return getConfig().getHapticChannelMaximumAmplitude();
    }

    public boolean setAlwaysOnEffect(int i, android.os.VibrationEffect vibrationEffect, android.os.VibrationAttributes vibrationAttributes) {
        return setAlwaysOnEffect(android.os.Process.myUid(), this.mPackageName, i, vibrationEffect, vibrationAttributes);
    }

    public boolean setAlwaysOnEffect(int i, java.lang.String str, int i2, android.os.VibrationEffect vibrationEffect, android.os.VibrationAttributes vibrationAttributes) {
        android.util.Log.w(TAG, "Always-on effects aren't supported");
        return false;
    }

    @java.lang.Deprecated
    public void vibrate(long j) {
        vibrate(j, (android.media.AudioAttributes) null);
    }

    @java.lang.Deprecated
    public void vibrate(long j, android.media.AudioAttributes audioAttributes) {
        try {
            vibrate(android.os.VibrationEffect.createOneShot(j, -1), audioAttributes);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Failed to create VibrationEffect", e);
        }
    }

    @java.lang.Deprecated
    public void vibrate(long[] jArr, int i) {
        vibrate(jArr, i, null);
    }

    @java.lang.Deprecated
    public void vibrate(long[] jArr, int i, android.media.AudioAttributes audioAttributes) {
        if (i < -1 || i >= jArr.length) {
            android.util.Log.e(TAG, "vibrate called with repeat index out of bounds (pattern.length=" + jArr.length + ", index=" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        try {
            vibrate(android.os.VibrationEffect.createWaveform(jArr, i), audioAttributes);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Failed to create VibrationEffect", e);
        }
    }

    public void vibrate(android.os.VibrationEffect vibrationEffect) {
        vibrate(vibrationEffect, new android.os.VibrationAttributes.Builder().build());
    }

    public void vibrate(android.os.VibrationEffect vibrationEffect, android.media.AudioAttributes audioAttributes) {
        android.os.VibrationAttributes build;
        if (audioAttributes == null) {
            build = new android.os.VibrationAttributes.Builder().build();
        } else {
            build = new android.os.VibrationAttributes.Builder(audioAttributes).build();
        }
        vibrate(vibrationEffect, build);
    }

    public void vibrate(android.os.VibrationEffect vibrationEffect, android.os.VibrationAttributes vibrationAttributes) {
        vibrate(android.os.Process.myUid(), this.mPackageName, vibrationEffect, null, vibrationAttributes);
    }

    public void performHapticFeedback(int i, boolean z, java.lang.String str, boolean z2) {
        android.util.Log.w(TAG, "performHapticFeedback is not supported");
    }

    public int[] areEffectsSupported(int... iArr) {
        android.os.VibratorInfo info = getInfo();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = info.isEffectSupported(iArr[i]);
        }
        return iArr2;
    }

    public final int areAllEffectsSupported(int... iArr) {
        android.os.VibratorInfo info = getInfo();
        int i = 1;
        for (int i2 : iArr) {
            switch (info.isEffectSupported(i2)) {
                case 1:
                    break;
                case 2:
                    return 2;
                default:
                    i = 0;
                    break;
            }
        }
        return i;
    }

    public boolean[] arePrimitivesSupported(int... iArr) {
        android.os.VibratorInfo info = getInfo();
        boolean[] zArr = new boolean[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            zArr[i] = info.isPrimitiveSupported(iArr[i]);
        }
        return zArr;
    }

    public final boolean areAllPrimitivesSupported(int... iArr) {
        android.os.VibratorInfo info = getInfo();
        for (int i : iArr) {
            if (!info.isPrimitiveSupported(i)) {
                return false;
            }
        }
        return true;
    }

    public int[] getPrimitiveDurations(int... iArr) {
        android.os.VibratorInfo info = getInfo();
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = info.getPrimitiveDuration(iArr[i]);
        }
        return iArr2;
    }

    @android.annotation.SystemApi
    public boolean isVibrating() {
        return false;
    }

    @android.annotation.SystemApi
    public void addVibratorStateListener(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
    }

    @android.annotation.SystemApi
    public void addVibratorStateListener(java.util.concurrent.Executor executor, android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
    }

    @android.annotation.SystemApi
    public void removeVibratorStateListener(android.os.Vibrator.OnVibratorStateChangedListener onVibratorStateChangedListener) {
    }
}
