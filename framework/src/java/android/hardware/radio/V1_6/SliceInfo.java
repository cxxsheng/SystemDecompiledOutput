package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class SliceInfo {
    public byte sst = 0;
    public int sliceDifferentiator = 0;
    public byte mappedHplmnSst = 0;
    public int mappedHplmnSD = 0;
    public byte status = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.SliceInfo.class) {
            return false;
        }
        android.hardware.radio.V1_6.SliceInfo sliceInfo = (android.hardware.radio.V1_6.SliceInfo) obj;
        if (this.sst == sliceInfo.sst && this.sliceDifferentiator == sliceInfo.sliceDifferentiator && this.mappedHplmnSst == sliceInfo.mappedHplmnSst && this.mappedHplmnSD == sliceInfo.mappedHplmnSD && this.status == sliceInfo.status) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.sst))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sliceDifferentiator))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.mappedHplmnSst))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.mappedHplmnSD))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.status))));
    }

    public final java.lang.String toString() {
        return "{.sst = " + android.hardware.radio.V1_6.SliceServiceType.toString(this.sst) + ", .sliceDifferentiator = " + this.sliceDifferentiator + ", .mappedHplmnSst = " + android.hardware.radio.V1_6.SliceServiceType.toString(this.mappedHplmnSst) + ", .mappedHplmnSD = " + this.mappedHplmnSD + ", .status = " + android.hardware.radio.V1_6.SliceStatus.toString(this.status) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.SliceInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.SliceInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.SliceInfo sliceInfo = new android.hardware.radio.V1_6.SliceInfo();
            sliceInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            arrayList.add(sliceInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.sst = hwBlob.getInt8(0 + j);
        this.sliceDifferentiator = hwBlob.getInt32(4 + j);
        this.mappedHplmnSst = hwBlob.getInt8(8 + j);
        this.mappedHplmnSD = hwBlob.getInt32(12 + j);
        this.status = hwBlob.getInt8(j + 16);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.SliceInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt8(0 + j, this.sst);
        hwBlob.putInt32(4 + j, this.sliceDifferentiator);
        hwBlob.putInt8(8 + j, this.mappedHplmnSst);
        hwBlob.putInt32(12 + j, this.mappedHplmnSD);
        hwBlob.putInt8(j + 16, this.status);
    }
}
