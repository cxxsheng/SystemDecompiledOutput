package android.hardware.radio.V1_1;

/* loaded from: classes2.dex */
public final class RadioAccessSpecifier {
    public int radioAccessNetwork = 0;
    public java.util.ArrayList<java.lang.Integer> geranBands = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.Integer> utranBands = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.Integer> eutranBands = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.Integer> channels = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_1.RadioAccessSpecifier.class) {
            return false;
        }
        android.hardware.radio.V1_1.RadioAccessSpecifier radioAccessSpecifier = (android.hardware.radio.V1_1.RadioAccessSpecifier) obj;
        if (this.radioAccessNetwork == radioAccessSpecifier.radioAccessNetwork && android.os.HidlSupport.deepEquals(this.geranBands, radioAccessSpecifier.geranBands) && android.os.HidlSupport.deepEquals(this.utranBands, radioAccessSpecifier.utranBands) && android.os.HidlSupport.deepEquals(this.eutranBands, radioAccessSpecifier.eutranBands) && android.os.HidlSupport.deepEquals(this.channels, radioAccessSpecifier.channels)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.radioAccessNetwork))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.geranBands)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.utranBands)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.eutranBands)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.channels)));
    }

    public final java.lang.String toString() {
        return "{.radioAccessNetwork = " + android.hardware.radio.V1_1.RadioAccessNetworks.toString(this.radioAccessNetwork) + ", .geranBands = " + this.geranBands + ", .utranBands = " + this.utranBands + ", .eutranBands = " + this.eutranBands + ", .channels = " + this.channels + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_1.RadioAccessSpecifier> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_1.RadioAccessSpecifier> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_1.RadioAccessSpecifier radioAccessSpecifier = new android.hardware.radio.V1_1.RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(radioAccessSpecifier);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.radioAccessNetwork = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j2 + 0, true);
        this.geranBands.clear();
        for (int i = 0; i < int32; i++) {
            this.geranBands.add(java.lang.Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        long j3 = j + 24;
        int int322 = hwBlob.getInt32(j3 + 8);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 4, hwBlob.handle(), j3 + 0, true);
        this.utranBands.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            this.utranBands.add(java.lang.Integer.valueOf(readEmbeddedBuffer2.getInt32(i2 * 4)));
        }
        long j4 = j + 40;
        int int323 = hwBlob.getInt32(j4 + 8);
        android.os.HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 4, hwBlob.handle(), j4 + 0, true);
        this.eutranBands.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            this.eutranBands.add(java.lang.Integer.valueOf(readEmbeddedBuffer3.getInt32(i3 * 4)));
        }
        long j5 = j + 56;
        int int324 = hwBlob.getInt32(8 + j5);
        android.os.HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 4, hwBlob.handle(), j5 + 0, true);
        this.channels.clear();
        for (int i4 = 0; i4 < int324; i4++) {
            this.channels.add(java.lang.Integer.valueOf(readEmbeddedBuffer4.getInt32(i4 * 4)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_1.RadioAccessSpecifier> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.radioAccessNetwork);
        int size = this.geranBands.size();
        long j2 = j + 8;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.geranBands.get(i).intValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.utranBands.size();
        long j3 = j + 24;
        hwBlob.putInt32(j3 + 8, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 4);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putInt32(i2 * 4, this.utranBands.get(i2).intValue());
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        int size3 = this.eutranBands.size();
        long j4 = j + 40;
        hwBlob.putInt32(j4 + 8, size3);
        hwBlob.putBool(j4 + 12, false);
        android.os.HwBlob hwBlob4 = new android.os.HwBlob(size3 * 4);
        for (int i3 = 0; i3 < size3; i3++) {
            hwBlob4.putInt32(i3 * 4, this.eutranBands.get(i3).intValue());
        }
        hwBlob.putBlob(j4 + 0, hwBlob4);
        int size4 = this.channels.size();
        long j5 = j + 56;
        hwBlob.putInt32(8 + j5, size4);
        hwBlob.putBool(12 + j5, false);
        android.os.HwBlob hwBlob5 = new android.os.HwBlob(size4 * 4);
        for (int i4 = 0; i4 < size4; i4++) {
            hwBlob5.putInt32(i4 * 4, this.channels.get(i4).intValue());
        }
        hwBlob.putBlob(j5 + 0, hwBlob5);
    }
}
