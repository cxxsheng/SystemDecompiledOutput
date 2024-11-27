package android.hardware.soundtrigger.V2_3;

/* loaded from: classes.dex */
public final class Properties {
    public int audioCapabilities;
    public android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties base = new android.hardware.soundtrigger.V2_0.ISoundTriggerHw.Properties();
    public java.lang.String supportedModelArch = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.soundtrigger.V2_3.Properties.class) {
            return false;
        }
        android.hardware.soundtrigger.V2_3.Properties properties = (android.hardware.soundtrigger.V2_3.Properties) obj;
        if (android.os.HidlSupport.deepEquals(this.base, properties.base) && android.os.HidlSupport.deepEquals(this.supportedModelArch, properties.supportedModelArch) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.audioCapabilities), java.lang.Integer.valueOf(properties.audioCapabilities))) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.supportedModelArch)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.audioCapabilities))));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .supportedModelArch = " + this.supportedModelArch + ", .audioCapabilities = " + android.hardware.soundtrigger.V2_3.AudioCapabilities.dumpBitfield(this.audioCapabilities) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.soundtrigger.V2_3.Properties> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.soundtrigger.V2_3.Properties> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.soundtrigger.V2_3.Properties properties = new android.hardware.soundtrigger.V2_3.Properties();
            properties.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            arrayList.add(properties);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        long j2 = j + 88;
        this.supportedModelArch = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.supportedModelArch.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.audioCapabilities = hwBlob.getInt32(j + 104);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_3.Properties> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putString(88 + j, this.supportedModelArch);
        hwBlob.putInt32(j + 104, this.audioCapabilities);
    }
}
