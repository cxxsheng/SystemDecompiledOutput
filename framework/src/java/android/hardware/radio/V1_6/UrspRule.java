package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class UrspRule {
    public byte precedence = 0;
    public java.util.ArrayList<android.hardware.radio.V1_6.TrafficDescriptor> trafficDescriptors = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_6.RouteSelectionDescriptor> routeSelectionDescriptor = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.UrspRule.class) {
            return false;
        }
        android.hardware.radio.V1_6.UrspRule urspRule = (android.hardware.radio.V1_6.UrspRule) obj;
        if (this.precedence == urspRule.precedence && android.os.HidlSupport.deepEquals(this.trafficDescriptors, urspRule.trafficDescriptors) && android.os.HidlSupport.deepEquals(this.routeSelectionDescriptor, urspRule.routeSelectionDescriptor)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.precedence))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.trafficDescriptors)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.routeSelectionDescriptor)));
    }

    public final java.lang.String toString() {
        return "{.precedence = " + ((int) this.precedence) + ", .trafficDescriptors = " + this.trafficDescriptors + ", .routeSelectionDescriptor = " + this.routeSelectionDescriptor + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.UrspRule> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.UrspRule> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.UrspRule urspRule = new android.hardware.radio.V1_6.UrspRule();
            urspRule.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(urspRule);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.precedence = hwBlob.getInt8(j + 0);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, hwBlob.handle(), j2 + 0, true);
        this.trafficDescriptors.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.TrafficDescriptor trafficDescriptor = new android.hardware.radio.V1_6.TrafficDescriptor();
            trafficDescriptor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            this.trafficDescriptors.add(trafficDescriptor);
        }
        long j3 = j + 24;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 48, hwBlob.handle(), j3 + 0, true);
        this.routeSelectionDescriptor.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.radio.V1_6.RouteSelectionDescriptor routeSelectionDescriptor = new android.hardware.radio.V1_6.RouteSelectionDescriptor();
            routeSelectionDescriptor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 48);
            this.routeSelectionDescriptor.add(routeSelectionDescriptor);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.UrspRule> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt8(j + 0, this.precedence);
        int size = this.trafficDescriptors.size();
        long j2 = j + 8;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            this.trafficDescriptors.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.routeSelectionDescriptor.size();
        long j3 = j + 24;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 48);
        for (int i2 = 0; i2 < size2; i2++) {
            this.routeSelectionDescriptor.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 48);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
