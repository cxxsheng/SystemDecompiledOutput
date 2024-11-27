package android.os;

/* loaded from: classes3.dex */
public class NullVibrator extends android.os.Vibrator {
    private static final android.os.NullVibrator sInstance = new android.os.NullVibrator();

    private NullVibrator() {
    }

    public static android.os.NullVibrator getInstance() {
        return sInstance;
    }

    @Override // android.os.Vibrator
    public boolean hasVibrator() {
        return false;
    }

    @Override // android.os.Vibrator
    public boolean isVibrating() {
        return false;
    }

    @Override // android.os.Vibrator
    public boolean hasAmplitudeControl() {
        return false;
    }

    @Override // android.os.Vibrator
    public void vibrate(int i, java.lang.String str, android.os.VibrationEffect vibrationEffect, java.lang.String str2, android.os.VibrationAttributes vibrationAttributes) {
    }

    @Override // android.os.Vibrator
    public void cancel() {
    }

    @Override // android.os.Vibrator
    public void cancel(int i) {
    }
}
