package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class ProgramInfo {
    public int infoFlags;
    public android.hardware.broadcastradio.V2_0.ProgramSelector selector = new android.hardware.broadcastradio.V2_0.ProgramSelector();
    public android.hardware.broadcastradio.V2_0.ProgramIdentifier logicallyTunedTo = new android.hardware.broadcastradio.V2_0.ProgramIdentifier();
    public android.hardware.broadcastradio.V2_0.ProgramIdentifier physicallyTunedTo = new android.hardware.broadcastradio.V2_0.ProgramIdentifier();
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramIdentifier> relatedContent = new java.util.ArrayList<>();
    public int signalQuality = 0;
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.Metadata> metadata = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> vendorInfo = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.ProgramInfo.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.ProgramInfo programInfo = (android.hardware.broadcastradio.V2_0.ProgramInfo) obj;
        if (android.os.HidlSupport.deepEquals(this.selector, programInfo.selector) && android.os.HidlSupport.deepEquals(this.logicallyTunedTo, programInfo.logicallyTunedTo) && android.os.HidlSupport.deepEquals(this.physicallyTunedTo, programInfo.physicallyTunedTo) && android.os.HidlSupport.deepEquals(this.relatedContent, programInfo.relatedContent) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.infoFlags), java.lang.Integer.valueOf(programInfo.infoFlags)) && this.signalQuality == programInfo.signalQuality && android.os.HidlSupport.deepEquals(this.metadata, programInfo.metadata) && android.os.HidlSupport.deepEquals(this.vendorInfo, programInfo.vendorInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.selector)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.logicallyTunedTo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.physicallyTunedTo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.relatedContent)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.infoFlags))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.signalQuality))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.metadata)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.vendorInfo)));
    }

    public final java.lang.String toString() {
        return "{.selector = " + this.selector + ", .logicallyTunedTo = " + this.logicallyTunedTo + ", .physicallyTunedTo = " + this.physicallyTunedTo + ", .relatedContent = " + this.relatedContent + ", .infoFlags = " + android.hardware.broadcastradio.V2_0.ProgramInfoFlags.dumpBitfield(this.infoFlags) + ", .signalQuality = " + this.signalQuality + ", .metadata = " + this.metadata + ", .vendorInfo = " + this.vendorInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.ProgramInfo programInfo = new android.hardware.broadcastradio.V2_0.ProgramInfo();
            programInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            arrayList.add(programInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.selector.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        this.logicallyTunedTo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 32);
        this.physicallyTunedTo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 48);
        long j2 = j + 64;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.relatedContent.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifier = new android.hardware.broadcastradio.V2_0.ProgramIdentifier();
            programIdentifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            this.relatedContent.add(programIdentifier);
        }
        this.infoFlags = hwBlob.getInt32(j + 80);
        this.signalQuality = hwBlob.getInt32(j + 84);
        long j3 = j + 88;
        int int322 = hwBlob.getInt32(j3 + 8);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 32, hwBlob.handle(), j3 + 0, true);
        this.metadata.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.broadcastradio.V2_0.Metadata metadata = new android.hardware.broadcastradio.V2_0.Metadata();
            metadata.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 32);
            this.metadata.add(metadata);
        }
        long j4 = j + 104;
        int int323 = hwBlob.getInt32(8 + j4);
        android.os.HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 32, hwBlob.handle(), j4 + 0, true);
        this.vendorInfo.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            android.hardware.broadcastradio.V2_0.VendorKeyValue vendorKeyValue = new android.hardware.broadcastradio.V2_0.VendorKeyValue();
            vendorKeyValue.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer3, i3 * 32);
            this.vendorInfo.add(vendorKeyValue);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.selector.writeEmbeddedToBlob(hwBlob, j + 0);
        this.logicallyTunedTo.writeEmbeddedToBlob(hwBlob, j + 32);
        this.physicallyTunedTo.writeEmbeddedToBlob(hwBlob, j + 48);
        int size = this.relatedContent.size();
        long j2 = j + 64;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            this.relatedContent.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putInt32(j + 80, this.infoFlags);
        hwBlob.putInt32(j + 84, this.signalQuality);
        int size2 = this.metadata.size();
        long j3 = j + 88;
        hwBlob.putInt32(j3 + 8, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 32);
        for (int i2 = 0; i2 < size2; i2++) {
            this.metadata.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 32);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        int size3 = this.vendorInfo.size();
        long j4 = j + 104;
        hwBlob.putInt32(8 + j4, size3);
        hwBlob.putBool(j4 + 12, false);
        android.os.HwBlob hwBlob4 = new android.os.HwBlob(size3 * 32);
        for (int i3 = 0; i3 < size3; i3++) {
            this.vendorInfo.get(i3).writeEmbeddedToBlob(hwBlob4, i3 * 32);
        }
        hwBlob.putBlob(j4 + 0, hwBlob4);
    }
}
