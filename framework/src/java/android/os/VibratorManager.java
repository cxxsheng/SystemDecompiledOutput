package android.os;

/* loaded from: classes3.dex */
public abstract class VibratorManager {
    private static final java.lang.String TAG = "VibratorManager";
    protected final java.lang.String mPackageName;

    public abstract void cancel();

    public abstract void cancel(int i);

    public abstract android.os.Vibrator getDefaultVibrator();

    public abstract android.os.Vibrator getVibrator(int i);

    public abstract int[] getVibratorIds();

    public abstract void vibrate(int i, java.lang.String str, android.os.CombinedVibration combinedVibration, java.lang.String str2, android.os.VibrationAttributes vibrationAttributes);

    public VibratorManager() {
        this.mPackageName = android.app.ActivityThread.currentPackageName();
    }

    protected VibratorManager(android.content.Context context) {
        this.mPackageName = context.getOpPackageName();
    }

    public boolean setAlwaysOnEffect(int i, java.lang.String str, int i2, android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes) {
        android.util.Log.w(TAG, "Always-on effects aren't supported");
        return false;
    }

    public final void vibrate(android.os.CombinedVibration combinedVibration) {
        vibrate(combinedVibration, null);
    }

    public final void vibrate(android.os.CombinedVibration combinedVibration, android.os.VibrationAttributes vibrationAttributes) {
        vibrate(android.os.Process.myUid(), this.mPackageName, combinedVibration, null, vibrationAttributes);
    }

    public void performHapticFeedback(int i, boolean z, java.lang.String str, boolean z2) {
        android.util.Log.w(TAG, "performHapticFeedback is not supported");
    }
}
