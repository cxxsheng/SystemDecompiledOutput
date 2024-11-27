package android.hardware.biometrics.fingerprint.V2_1;

/* loaded from: classes.dex */
public final class FingerprintAuthenticated {
    public android.hardware.biometrics.fingerprint.V2_1.FingerprintFingerId finger = new android.hardware.biometrics.fingerprint.V2_1.FingerprintFingerId();
    public byte[] hat = new byte[69];

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.biometrics.fingerprint.V2_1.FingerprintAuthenticated.class) {
            return false;
        }
        android.hardware.biometrics.fingerprint.V2_1.FingerprintAuthenticated fingerprintAuthenticated = (android.hardware.biometrics.fingerprint.V2_1.FingerprintAuthenticated) obj;
        if (android.os.HidlSupport.deepEquals(this.finger, fingerprintAuthenticated.finger) && android.os.HidlSupport.deepEquals(this.hat, fingerprintAuthenticated.hat)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.finger)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.hat)));
    }

    public final java.lang.String toString() {
        return "{.finger = " + this.finger + ", .hat = " + java.util.Arrays.toString(this.hat) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintAuthenticated> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintAuthenticated> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.biometrics.fingerprint.V2_1.FingerprintAuthenticated fingerprintAuthenticated = new android.hardware.biometrics.fingerprint.V2_1.FingerprintAuthenticated();
            fingerprintAuthenticated.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 80);
            arrayList.add(fingerprintAuthenticated);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.finger.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        hwBlob.copyToInt8Array(j + 8, this.hat, 69);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintAuthenticated> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.finger.writeEmbeddedToBlob(hwBlob, 0 + j);
        long j2 = j + 8;
        byte[] bArr = this.hat;
        if (bArr == null || bArr.length != 69) {
            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putInt8Array(j2, bArr);
    }
}
