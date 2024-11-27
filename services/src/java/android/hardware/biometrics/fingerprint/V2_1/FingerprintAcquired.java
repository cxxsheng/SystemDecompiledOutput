package android.hardware.biometrics.fingerprint.V2_1;

/* loaded from: classes.dex */
public final class FingerprintAcquired {
    public int acquiredInfo = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == android.hardware.biometrics.fingerprint.V2_1.FingerprintAcquired.class && this.acquiredInfo == ((android.hardware.biometrics.fingerprint.V2_1.FingerprintAcquired) obj).acquiredInfo) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.acquiredInfo))));
    }

    public final java.lang.String toString() {
        return "{.acquiredInfo = " + android.hardware.biometrics.fingerprint.V2_1.FingerprintAcquiredInfo.toString(this.acquiredInfo) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintAcquired> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintAcquired> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.biometrics.fingerprint.V2_1.FingerprintAcquired fingerprintAcquired = new android.hardware.biometrics.fingerprint.V2_1.FingerprintAcquired();
            fingerprintAcquired.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 4);
            arrayList.add(fingerprintAcquired);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.acquiredInfo = hwBlob.getInt32(j + 0);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(4);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintAcquired> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 4);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.acquiredInfo);
    }
}
