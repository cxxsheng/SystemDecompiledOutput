package android.hardware.input;

/* loaded from: classes2.dex */
public class InputDeviceVibratorManager extends android.os.VibratorManager implements android.hardware.input.InputManager.InputDeviceListener {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "InputDeviceVibratorManager";
    private final int mDeviceId;
    private final android.util.SparseArray<android.os.Vibrator> mVibrators = new android.util.SparseArray<>();
    private final android.hardware.input.InputManagerGlobal mGlobal = android.hardware.input.InputManagerGlobal.getInstance();
    private final android.os.Binder mToken = new android.os.Binder();

    public InputDeviceVibratorManager(int i) {
        this.mDeviceId = i;
        initializeVibrators();
    }

    private void initializeVibrators() {
        synchronized (this.mVibrators) {
            this.mVibrators.clear();
            android.view.InputDevice.getDevice(this.mDeviceId);
            int[] vibratorIds = this.mGlobal.getVibratorIds(this.mDeviceId);
            for (int i = 0; i < vibratorIds.length; i++) {
                this.mVibrators.put(vibratorIds[i], new android.hardware.input.InputDeviceVibrator(this.mDeviceId, vibratorIds[i]));
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        synchronized (this.mVibrators) {
            if (i == this.mDeviceId) {
                this.mVibrators.clear();
            }
        }
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        if (i == this.mDeviceId) {
            initializeVibrators();
        }
    }

    @Override // android.os.VibratorManager
    public int[] getVibratorIds() {
        int[] iArr;
        synchronized (this.mVibrators) {
            iArr = new int[this.mVibrators.size()];
            for (int i = 0; i < this.mVibrators.size(); i++) {
                iArr[i] = this.mVibrators.keyAt(i);
            }
        }
        return iArr;
    }

    @Override // android.os.VibratorManager
    public android.os.Vibrator getVibrator(int i) {
        synchronized (this.mVibrators) {
            if (this.mVibrators.contains(i)) {
                return this.mVibrators.get(i);
            }
            return android.os.NullVibrator.getInstance();
        }
    }

    @Override // android.os.VibratorManager
    public android.os.Vibrator getDefaultVibrator() {
        synchronized (this.mVibrators) {
            if (this.mVibrators.size() > 0) {
                return this.mVibrators.valueAt(0);
            }
            return android.os.NullVibrator.getInstance();
        }
    }

    @Override // android.os.VibratorManager
    public void vibrate(int i, java.lang.String str, android.os.CombinedVibration combinedVibration, java.lang.String str2, android.os.VibrationAttributes vibrationAttributes) {
        this.mGlobal.vibrate(this.mDeviceId, combinedVibration, this.mToken);
    }

    @Override // android.os.VibratorManager
    public void cancel() {
        this.mGlobal.cancelVibrate(this.mDeviceId, this.mToken);
    }

    @Override // android.os.VibratorManager
    public void cancel(int i) {
        cancel();
    }
}
