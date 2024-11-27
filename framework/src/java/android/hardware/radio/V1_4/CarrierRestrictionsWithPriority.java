package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class CarrierRestrictionsWithPriority {
    public java.util.ArrayList<android.hardware.radio.V1_0.Carrier> allowedCarriers = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_0.Carrier> excludedCarriers = new java.util.ArrayList<>();
    public boolean allowedCarriersPrioritized = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.CarrierRestrictionsWithPriority.class) {
            return false;
        }
        android.hardware.radio.V1_4.CarrierRestrictionsWithPriority carrierRestrictionsWithPriority = (android.hardware.radio.V1_4.CarrierRestrictionsWithPriority) obj;
        if (android.os.HidlSupport.deepEquals(this.allowedCarriers, carrierRestrictionsWithPriority.allowedCarriers) && android.os.HidlSupport.deepEquals(this.excludedCarriers, carrierRestrictionsWithPriority.excludedCarriers) && this.allowedCarriersPrioritized == carrierRestrictionsWithPriority.allowedCarriersPrioritized) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.allowedCarriers)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.excludedCarriers)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.allowedCarriersPrioritized))));
    }

    public final java.lang.String toString() {
        return "{.allowedCarriers = " + this.allowedCarriers + ", .excludedCarriers = " + this.excludedCarriers + ", .allowedCarriersPrioritized = " + this.allowedCarriersPrioritized + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.CarrierRestrictionsWithPriority> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.CarrierRestrictionsWithPriority> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.CarrierRestrictionsWithPriority carrierRestrictionsWithPriority = new android.hardware.radio.V1_4.CarrierRestrictionsWithPriority();
            carrierRestrictionsWithPriority.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(carrierRestrictionsWithPriority);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, hwBlob.handle(), j2 + 0, true);
        this.allowedCarriers.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.Carrier carrier = new android.hardware.radio.V1_0.Carrier();
            carrier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            this.allowedCarriers.add(carrier);
        }
        long j3 = j + 16;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 56, hwBlob.handle(), j3 + 0, true);
        this.excludedCarriers.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.radio.V1_0.Carrier carrier2 = new android.hardware.radio.V1_0.Carrier();
            carrier2.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 56);
            this.excludedCarriers.add(carrier2);
        }
        this.allowedCarriersPrioritized = hwBlob.getBool(j + 32);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.CarrierRestrictionsWithPriority> arrayList) {
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
        int size = this.allowedCarriers.size();
        long j2 = j + 0;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            this.allowedCarriers.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.excludedCarriers.size();
        long j3 = j + 16;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 56);
        for (int i2 = 0; i2 < size2; i2++) {
            this.excludedCarriers.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 56);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        hwBlob.putBool(j + 32, this.allowedCarriersPrioritized);
    }
}
