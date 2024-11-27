package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class AppStatus {
    public int appType = 0;
    public int appState = 0;
    public int persoSubstate = 0;
    public java.lang.String aidPtr = new java.lang.String();
    public java.lang.String appLabelPtr = new java.lang.String();
    public int pin1Replaced = 0;
    public int pin1 = 0;
    public int pin2 = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.AppStatus.class) {
            return false;
        }
        android.hardware.radio.V1_0.AppStatus appStatus = (android.hardware.radio.V1_0.AppStatus) obj;
        if (this.appType == appStatus.appType && this.appState == appStatus.appState && this.persoSubstate == appStatus.persoSubstate && android.os.HidlSupport.deepEquals(this.aidPtr, appStatus.aidPtr) && android.os.HidlSupport.deepEquals(this.appLabelPtr, appStatus.appLabelPtr) && this.pin1Replaced == appStatus.pin1Replaced && this.pin1 == appStatus.pin1 && this.pin2 == appStatus.pin2) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.appType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.appState))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.persoSubstate))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.aidPtr)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.appLabelPtr)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.pin1Replaced))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.pin1))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.pin2))));
    }

    public final java.lang.String toString() {
        return "{.appType = " + android.hardware.radio.V1_0.AppType.toString(this.appType) + ", .appState = " + android.hardware.radio.V1_0.AppState.toString(this.appState) + ", .persoSubstate = " + android.hardware.radio.V1_0.PersoSubstate.toString(this.persoSubstate) + ", .aidPtr = " + this.aidPtr + ", .appLabelPtr = " + this.appLabelPtr + ", .pin1Replaced = " + this.pin1Replaced + ", .pin1 = " + android.hardware.radio.V1_0.PinState.toString(this.pin1) + ", .pin2 = " + android.hardware.radio.V1_0.PinState.toString(this.pin2) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.AppStatus> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.AppStatus> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.AppStatus appStatus = new android.hardware.radio.V1_0.AppStatus();
            appStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            arrayList.add(appStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.appType = hwBlob.getInt32(j + 0);
        this.appState = hwBlob.getInt32(j + 4);
        this.persoSubstate = hwBlob.getInt32(j + 8);
        long j2 = j + 16;
        this.aidPtr = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.aidPtr.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 32;
        this.appLabelPtr = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.appLabelPtr.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        this.pin1Replaced = hwBlob.getInt32(j + 48);
        this.pin1 = hwBlob.getInt32(j + 52);
        this.pin2 = hwBlob.getInt32(j + 56);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.AppStatus> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 64);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.appType);
        hwBlob.putInt32(4 + j, this.appState);
        hwBlob.putInt32(8 + j, this.persoSubstate);
        hwBlob.putString(16 + j, this.aidPtr);
        hwBlob.putString(32 + j, this.appLabelPtr);
        hwBlob.putInt32(48 + j, this.pin1Replaced);
        hwBlob.putInt32(52 + j, this.pin1);
        hwBlob.putInt32(j + 56, this.pin2);
    }
}
