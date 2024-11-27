package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class ProgramFilter {
    public java.util.ArrayList<java.lang.Integer> identifierTypes = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramIdentifier> identifiers = new java.util.ArrayList<>();
    public boolean includeCategories = false;
    public boolean excludeModifications = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.ProgramFilter.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.ProgramFilter programFilter = (android.hardware.broadcastradio.V2_0.ProgramFilter) obj;
        if (android.os.HidlSupport.deepEquals(this.identifierTypes, programFilter.identifierTypes) && android.os.HidlSupport.deepEquals(this.identifiers, programFilter.identifiers) && this.includeCategories == programFilter.includeCategories && this.excludeModifications == programFilter.excludeModifications) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.identifierTypes)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.identifiers)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.includeCategories))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.excludeModifications))));
    }

    public final java.lang.String toString() {
        return "{.identifierTypes = " + this.identifierTypes + ", .identifiers = " + this.identifiers + ", .includeCategories = " + this.includeCategories + ", .excludeModifications = " + this.excludeModifications + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramFilter> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramFilter> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.ProgramFilter programFilter = new android.hardware.broadcastradio.V2_0.ProgramFilter();
            programFilter.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(programFilter);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j2 + 0, true);
        this.identifierTypes.clear();
        for (int i = 0; i < int32; i++) {
            this.identifierTypes.add(java.lang.Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        long j3 = j + 16;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j3 + 0, true);
        this.identifiers.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifier = new android.hardware.broadcastradio.V2_0.ProgramIdentifier();
            programIdentifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 16);
            this.identifiers.add(programIdentifier);
        }
        this.includeCategories = hwBlob.getBool(j + 32);
        this.excludeModifications = hwBlob.getBool(j + 33);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramFilter> arrayList) {
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
        int size = this.identifierTypes.size();
        long j2 = j + 0;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.identifierTypes.get(i).intValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.identifiers.size();
        long j3 = j + 16;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(12 + j3, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            this.identifiers.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 16);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        hwBlob.putBool(j + 32, this.includeCategories);
        hwBlob.putBool(j + 33, this.excludeModifications);
    }
}
