package android.hardware.soundtrigger.V2_0;

/* loaded from: classes.dex */
public final class PhraseRecognitionExtra {
    public int id = 0;
    public int recognitionModes = 0;
    public int confidenceLevel = 0;
    public java.util.ArrayList<android.hardware.soundtrigger.V2_0.ConfidenceLevel> levels = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra.class) {
            return false;
        }
        android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra phraseRecognitionExtra = (android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra) obj;
        if (this.id == phraseRecognitionExtra.id && this.recognitionModes == phraseRecognitionExtra.recognitionModes && this.confidenceLevel == phraseRecognitionExtra.confidenceLevel && android.os.HidlSupport.deepEquals(this.levels, phraseRecognitionExtra.levels)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.id))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.recognitionModes))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.confidenceLevel))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.levels)));
    }

    public final java.lang.String toString() {
        return "{.id = " + this.id + ", .recognitionModes = " + this.recognitionModes + ", .confidenceLevel = " + this.confidenceLevel + ", .levels = " + this.levels + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra phraseRecognitionExtra = new android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra();
            phraseRecognitionExtra.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(phraseRecognitionExtra);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.id = hwBlob.getInt32(j + 0);
        this.recognitionModes = hwBlob.getInt32(j + 4);
        this.confidenceLevel = hwBlob.getInt32(j + 8);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, hwBlob.handle(), j2 + 0, true);
        this.levels.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.soundtrigger.V2_0.ConfidenceLevel confidenceLevel = new android.hardware.soundtrigger.V2_0.ConfidenceLevel();
            confidenceLevel.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
            this.levels.add(confidenceLevel);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.soundtrigger.V2_0.PhraseRecognitionExtra> arrayList) {
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
        hwBlob.putInt32(j + 0, this.id);
        hwBlob.putInt32(4 + j, this.recognitionModes);
        hwBlob.putInt32(j + 8, this.confidenceLevel);
        int size = this.levels.size();
        long j2 = j + 16;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 8);
        for (int i = 0; i < size; i++) {
            this.levels.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
