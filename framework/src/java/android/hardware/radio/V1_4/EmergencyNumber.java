package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class EmergencyNumber {
    public int categories;
    public int sources;
    public java.lang.String number = new java.lang.String();
    public java.lang.String mcc = new java.lang.String();
    public java.lang.String mnc = new java.lang.String();
    public java.util.ArrayList<java.lang.String> urns = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.EmergencyNumber.class) {
            return false;
        }
        android.hardware.radio.V1_4.EmergencyNumber emergencyNumber = (android.hardware.radio.V1_4.EmergencyNumber) obj;
        if (android.os.HidlSupport.deepEquals(this.number, emergencyNumber.number) && android.os.HidlSupport.deepEquals(this.mcc, emergencyNumber.mcc) && android.os.HidlSupport.deepEquals(this.mnc, emergencyNumber.mnc) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.categories), java.lang.Integer.valueOf(emergencyNumber.categories)) && android.os.HidlSupport.deepEquals(this.urns, emergencyNumber.urns) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.sources), java.lang.Integer.valueOf(emergencyNumber.sources))) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.number)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mcc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mnc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.categories))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.urns)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sources))));
    }

    public final java.lang.String toString() {
        return "{.number = " + this.number + ", .mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .categories = " + android.hardware.radio.V1_4.EmergencyServiceCategory.dumpBitfield(this.categories) + ", .urns = " + this.urns + ", .sources = " + android.hardware.radio.V1_4.EmergencyNumberSource.dumpBitfield(this.sources) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.EmergencyNumber> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.EmergencyNumber> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.EmergencyNumber emergencyNumber = new android.hardware.radio.V1_4.EmergencyNumber();
            emergencyNumber.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 80);
            arrayList.add(emergencyNumber);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 16;
        this.mcc = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 32;
        this.mnc = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        this.categories = hwBlob.getInt32(j + 48);
        long j5 = j + 56;
        int int32 = hwBlob.getInt32(8 + j5);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j5 + 0, true);
        this.urns.clear();
        for (int i = 0; i < int32; i++) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer.getString(i * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), r5 + 0, false);
            this.urns.add(string);
        }
        this.sources = hwBlob.getInt32(j + 72);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.EmergencyNumber> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(j + 0, this.number);
        hwBlob.putString(16 + j, this.mcc);
        hwBlob.putString(32 + j, this.mnc);
        hwBlob.putInt32(48 + j, this.categories);
        int size = this.urns.size();
        long j2 = 56 + j;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.urns.get(i));
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putInt32(j + 72, this.sources);
    }
}
