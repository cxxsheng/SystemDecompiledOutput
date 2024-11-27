package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class RouteSelectionDescriptor {
    public byte precedence = 0;
    public android.hardware.radio.V1_6.OptionalPdpProtocolType sessionType = new android.hardware.radio.V1_6.OptionalPdpProtocolType();
    public android.hardware.radio.V1_6.OptionalSscMode sscMode = new android.hardware.radio.V1_6.OptionalSscMode();
    public java.util.ArrayList<android.hardware.radio.V1_6.SliceInfo> sliceInfo = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.String> dnn = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.RouteSelectionDescriptor.class) {
            return false;
        }
        android.hardware.radio.V1_6.RouteSelectionDescriptor routeSelectionDescriptor = (android.hardware.radio.V1_6.RouteSelectionDescriptor) obj;
        if (this.precedence == routeSelectionDescriptor.precedence && android.os.HidlSupport.deepEquals(this.sessionType, routeSelectionDescriptor.sessionType) && android.os.HidlSupport.deepEquals(this.sscMode, routeSelectionDescriptor.sscMode) && android.os.HidlSupport.deepEquals(this.sliceInfo, routeSelectionDescriptor.sliceInfo) && android.os.HidlSupport.deepEquals(this.dnn, routeSelectionDescriptor.dnn)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.precedence))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.sessionType)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.sscMode)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.sliceInfo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.dnn)));
    }

    public final java.lang.String toString() {
        return "{.precedence = " + ((int) this.precedence) + ", .sessionType = " + this.sessionType + ", .sscMode = " + this.sscMode + ", .sliceInfo = " + this.sliceInfo + ", .dnn = " + this.dnn + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.RouteSelectionDescriptor> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.RouteSelectionDescriptor> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.RouteSelectionDescriptor routeSelectionDescriptor = new android.hardware.radio.V1_6.RouteSelectionDescriptor();
            routeSelectionDescriptor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(routeSelectionDescriptor);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.precedence = hwBlob.getInt8(j + 0);
        this.sessionType.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
        this.sscMode.readEmbeddedFromParcel(hwParcel, hwBlob, j + 12);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, hwBlob.handle(), j2 + 0, true);
        this.sliceInfo.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.SliceInfo sliceInfo = new android.hardware.radio.V1_6.SliceInfo();
            sliceInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            this.sliceInfo.add(sliceInfo);
        }
        long j3 = j + 32;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j3 + 0, true);
        this.dnn.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer2.getString(i2 * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer2.handle(), r1 + 0, false);
            this.dnn.add(string);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.RouteSelectionDescriptor> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt8(j + 0, this.precedence);
        this.sessionType.writeEmbeddedToBlob(hwBlob, j + 4);
        this.sscMode.writeEmbeddedToBlob(hwBlob, j + 12);
        int size = this.sliceInfo.size();
        long j2 = j + 16;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            this.sliceInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.dnn.size();
        long j3 = j + 32;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(12 + j3, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.dnn.get(i2));
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
