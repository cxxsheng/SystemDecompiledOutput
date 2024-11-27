package android.mtp;

/* loaded from: classes2.dex */
public class MtpDeviceInfo {
    private int[] mDevicePropertySupported;
    private int[] mEventsSupported;
    private java.lang.String mManufacturer;
    private java.lang.String mModel;
    private int[] mOperationsSupported;
    private java.lang.String mSerialNumber;
    private java.lang.String mVersion;

    private MtpDeviceInfo() {
    }

    public final java.lang.String getManufacturer() {
        return this.mManufacturer;
    }

    public final java.lang.String getModel() {
        return this.mModel;
    }

    public final java.lang.String getVersion() {
        return this.mVersion;
    }

    public final java.lang.String getSerialNumber() {
        return this.mSerialNumber;
    }

    public final int[] getOperationsSupported() {
        return this.mOperationsSupported;
    }

    public final int[] getEventsSupported() {
        return this.mEventsSupported;
    }

    public final int[] getDevicePropertySupported() {
        return this.mDevicePropertySupported;
    }

    public boolean isOperationSupported(int i) {
        return isSupported(this.mOperationsSupported, i);
    }

    public boolean isEventSupported(int i) {
        return isSupported(this.mEventsSupported, i);
    }

    public boolean isDevicePropertySupported(int i) {
        return isSupported(this.mDevicePropertySupported, i);
    }

    private static boolean isSupported(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
