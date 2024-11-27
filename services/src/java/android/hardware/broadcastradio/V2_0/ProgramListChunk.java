package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class ProgramListChunk {
    public boolean purge = false;
    public boolean complete = false;
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramInfo> modified = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramIdentifier> removed = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.ProgramListChunk.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.ProgramListChunk programListChunk = (android.hardware.broadcastradio.V2_0.ProgramListChunk) obj;
        if (this.purge == programListChunk.purge && this.complete == programListChunk.complete && android.os.HidlSupport.deepEquals(this.modified, programListChunk.modified) && android.os.HidlSupport.deepEquals(this.removed, programListChunk.removed)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.purge))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.complete))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.modified)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.removed)));
    }

    public final java.lang.String toString() {
        return "{.purge = " + this.purge + ", .complete = " + this.complete + ", .modified = " + this.modified + ", .removed = " + this.removed + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramListChunk> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramListChunk> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.ProgramListChunk programListChunk = new android.hardware.broadcastradio.V2_0.ProgramListChunk();
            programListChunk.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(programListChunk);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.purge = hwBlob.getBool(j + 0);
        this.complete = hwBlob.getBool(j + 1);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, hwBlob.handle(), j2 + 0, true);
        this.modified.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.ProgramInfo programInfo = new android.hardware.broadcastradio.V2_0.ProgramInfo();
            programInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            this.modified.add(programInfo);
        }
        long j3 = j + 24;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j3 + 0, true);
        this.removed.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifier = new android.hardware.broadcastradio.V2_0.ProgramIdentifier();
            programIdentifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 16);
            this.removed.add(programIdentifier);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramListChunk> arrayList) {
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
        hwBlob.putBool(j + 0, this.purge);
        hwBlob.putBool(j + 1, this.complete);
        int size = this.modified.size();
        long j2 = j + 8;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            this.modified.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.removed.size();
        long j3 = j + 24;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            this.removed.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 16);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
