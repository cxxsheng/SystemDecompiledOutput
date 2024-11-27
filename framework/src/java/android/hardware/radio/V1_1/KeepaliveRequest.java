package android.hardware.radio.V1_1;

/* loaded from: classes2.dex */
public final class KeepaliveRequest {
    public int type = 0;
    public java.util.ArrayList<java.lang.Byte> sourceAddress = new java.util.ArrayList<>();
    public int sourcePort = 0;
    public java.util.ArrayList<java.lang.Byte> destinationAddress = new java.util.ArrayList<>();
    public int destinationPort = 0;
    public int maxKeepaliveIntervalMillis = 0;
    public int cid = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_1.KeepaliveRequest.class) {
            return false;
        }
        android.hardware.radio.V1_1.KeepaliveRequest keepaliveRequest = (android.hardware.radio.V1_1.KeepaliveRequest) obj;
        if (this.type == keepaliveRequest.type && android.os.HidlSupport.deepEquals(this.sourceAddress, keepaliveRequest.sourceAddress) && this.sourcePort == keepaliveRequest.sourcePort && android.os.HidlSupport.deepEquals(this.destinationAddress, keepaliveRequest.destinationAddress) && this.destinationPort == keepaliveRequest.destinationPort && this.maxKeepaliveIntervalMillis == keepaliveRequest.maxKeepaliveIntervalMillis && this.cid == keepaliveRequest.cid) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.sourceAddress)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sourcePort))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.destinationAddress)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.destinationPort))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxKeepaliveIntervalMillis))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cid))));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.radio.V1_1.KeepaliveType.toString(this.type) + ", .sourceAddress = " + this.sourceAddress + ", .sourcePort = " + this.sourcePort + ", .destinationAddress = " + this.destinationAddress + ", .destinationPort = " + this.destinationPort + ", .maxKeepaliveIntervalMillis = " + this.maxKeepaliveIntervalMillis + ", .cid = " + this.cid + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_1.KeepaliveRequest> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_1.KeepaliveRequest> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_1.KeepaliveRequest keepaliveRequest = new android.hardware.radio.V1_1.KeepaliveRequest();
            keepaliveRequest.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            arrayList.add(keepaliveRequest);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j2 + 0, true);
        this.sourceAddress.clear();
        for (int i = 0; i < int32; i++) {
            this.sourceAddress.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
        this.sourcePort = hwBlob.getInt32(j + 24);
        long j3 = j + 32;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 1, hwBlob.handle(), j3 + 0, true);
        this.destinationAddress.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            this.destinationAddress.add(java.lang.Byte.valueOf(readEmbeddedBuffer2.getInt8(i2 * 1)));
        }
        this.destinationPort = hwBlob.getInt32(j + 48);
        this.maxKeepaliveIntervalMillis = hwBlob.getInt32(j + 52);
        this.cid = hwBlob.getInt32(j + 56);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_1.KeepaliveRequest> arrayList) {
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
        hwBlob.putInt32(j + 0, this.type);
        int size = this.sourceAddress.size();
        long j2 = j + 8;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.sourceAddress.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putInt32(j + 24, this.sourcePort);
        int size2 = this.destinationAddress.size();
        long j3 = j + 32;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(12 + j3, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 1);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putInt8(i2 * 1, this.destinationAddress.get(i2).byteValue());
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        hwBlob.putInt32(j + 48, this.destinationPort);
        hwBlob.putInt32(j + 52, this.maxKeepaliveIntervalMillis);
        hwBlob.putInt32(j + 56, this.cid);
    }
}
