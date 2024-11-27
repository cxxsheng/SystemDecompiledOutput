package android.hardware.biometrics.fingerprint.V2_1;

/* loaded from: classes.dex */
public final class FingerprintEnroll {
    public android.hardware.biometrics.fingerprint.V2_1.FingerprintFingerId finger = new android.hardware.biometrics.fingerprint.V2_1.FingerprintFingerId();
    public int samplesRemaining = 0;
    public long msg = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.biometrics.fingerprint.V2_1.FingerprintEnroll.class) {
            return false;
        }
        android.hardware.biometrics.fingerprint.V2_1.FingerprintEnroll fingerprintEnroll = (android.hardware.biometrics.fingerprint.V2_1.FingerprintEnroll) obj;
        if (android.os.HidlSupport.deepEquals(this.finger, fingerprintEnroll.finger) && this.samplesRemaining == fingerprintEnroll.samplesRemaining && this.msg == fingerprintEnroll.msg) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.finger)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.samplesRemaining))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.msg))));
    }

    public final java.lang.String toString() {
        return "{.finger = " + this.finger + ", .samplesRemaining = " + this.samplesRemaining + ", .msg = " + this.msg + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintEnroll> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintEnroll> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.biometrics.fingerprint.V2_1.FingerprintEnroll fingerprintEnroll = new android.hardware.biometrics.fingerprint.V2_1.FingerprintEnroll();
            fingerprintEnroll.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(fingerprintEnroll);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.finger.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.samplesRemaining = hwBlob.getInt32(8 + j);
        this.msg = hwBlob.getInt64(j + 16);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.biometrics.fingerprint.V2_1.FingerprintEnroll> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.finger.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt32(8 + j, this.samplesRemaining);
        hwBlob.putInt64(j + 16, this.msg);
    }
}
