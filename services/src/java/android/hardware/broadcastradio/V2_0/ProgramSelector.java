package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class ProgramSelector {
    public android.hardware.broadcastradio.V2_0.ProgramIdentifier primaryId = new android.hardware.broadcastradio.V2_0.ProgramIdentifier();
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramIdentifier> secondaryIds = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.ProgramSelector.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.ProgramSelector programSelector = (android.hardware.broadcastradio.V2_0.ProgramSelector) obj;
        if (android.os.HidlSupport.deepEquals(this.primaryId, programSelector.primaryId) && android.os.HidlSupport.deepEquals(this.secondaryIds, programSelector.secondaryIds)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.primaryId)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.secondaryIds)));
    }

    public final java.lang.String toString() {
        return "{.primaryId = " + this.primaryId + ", .secondaryIds = " + this.secondaryIds + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramSelector> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramSelector> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.ProgramSelector programSelector = new android.hardware.broadcastradio.V2_0.ProgramSelector();
            programSelector.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(programSelector);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.primaryId.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.secondaryIds.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifier = new android.hardware.broadcastradio.V2_0.ProgramIdentifier();
            programIdentifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            this.secondaryIds.add(programIdentifier);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramSelector> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.primaryId.writeEmbeddedToBlob(hwBlob, j + 0);
        int size = this.secondaryIds.size();
        long j2 = j + 16;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            this.secondaryIds.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
