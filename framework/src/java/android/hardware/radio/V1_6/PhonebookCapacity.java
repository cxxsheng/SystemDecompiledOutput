package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class PhonebookCapacity {
    public int maxAdnRecords = 0;
    public int usedAdnRecords = 0;
    public int maxEmailRecords = 0;
    public int usedEmailRecords = 0;
    public int maxAdditionalNumberRecords = 0;
    public int usedAdditionalNumberRecords = 0;
    public int maxNameLen = 0;
    public int maxNumberLen = 0;
    public int maxEmailLen = 0;
    public int maxAdditionalNumberLen = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.PhonebookCapacity.class) {
            return false;
        }
        android.hardware.radio.V1_6.PhonebookCapacity phonebookCapacity = (android.hardware.radio.V1_6.PhonebookCapacity) obj;
        if (this.maxAdnRecords == phonebookCapacity.maxAdnRecords && this.usedAdnRecords == phonebookCapacity.usedAdnRecords && this.maxEmailRecords == phonebookCapacity.maxEmailRecords && this.usedEmailRecords == phonebookCapacity.usedEmailRecords && this.maxAdditionalNumberRecords == phonebookCapacity.maxAdditionalNumberRecords && this.usedAdditionalNumberRecords == phonebookCapacity.usedAdditionalNumberRecords && this.maxNameLen == phonebookCapacity.maxNameLen && this.maxNumberLen == phonebookCapacity.maxNumberLen && this.maxEmailLen == phonebookCapacity.maxEmailLen && this.maxAdditionalNumberLen == phonebookCapacity.maxAdditionalNumberLen) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxAdnRecords))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.usedAdnRecords))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxEmailRecords))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.usedEmailRecords))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxAdditionalNumberRecords))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.usedAdditionalNumberRecords))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxNameLen))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxNumberLen))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxEmailLen))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxAdditionalNumberLen))));
    }

    public final java.lang.String toString() {
        return "{.maxAdnRecords = " + this.maxAdnRecords + ", .usedAdnRecords = " + this.usedAdnRecords + ", .maxEmailRecords = " + this.maxEmailRecords + ", .usedEmailRecords = " + this.usedEmailRecords + ", .maxAdditionalNumberRecords = " + this.maxAdditionalNumberRecords + ", .usedAdditionalNumberRecords = " + this.usedAdditionalNumberRecords + ", .maxNameLen = " + this.maxNameLen + ", .maxNumberLen = " + this.maxNumberLen + ", .maxEmailLen = " + this.maxEmailLen + ", .maxAdditionalNumberLen = " + this.maxAdditionalNumberLen + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.PhonebookCapacity> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.PhonebookCapacity> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.PhonebookCapacity phonebookCapacity = new android.hardware.radio.V1_6.PhonebookCapacity();
            phonebookCapacity.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(phonebookCapacity);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.maxAdnRecords = hwBlob.getInt32(0 + j);
        this.usedAdnRecords = hwBlob.getInt32(4 + j);
        this.maxEmailRecords = hwBlob.getInt32(8 + j);
        this.usedEmailRecords = hwBlob.getInt32(12 + j);
        this.maxAdditionalNumberRecords = hwBlob.getInt32(16 + j);
        this.usedAdditionalNumberRecords = hwBlob.getInt32(20 + j);
        this.maxNameLen = hwBlob.getInt32(24 + j);
        this.maxNumberLen = hwBlob.getInt32(28 + j);
        this.maxEmailLen = hwBlob.getInt32(32 + j);
        this.maxAdditionalNumberLen = hwBlob.getInt32(j + 36);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.PhonebookCapacity> arrayList) {
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
        hwBlob.putInt32(0 + j, this.maxAdnRecords);
        hwBlob.putInt32(4 + j, this.usedAdnRecords);
        hwBlob.putInt32(8 + j, this.maxEmailRecords);
        hwBlob.putInt32(12 + j, this.usedEmailRecords);
        hwBlob.putInt32(16 + j, this.maxAdditionalNumberRecords);
        hwBlob.putInt32(20 + j, this.usedAdditionalNumberRecords);
        hwBlob.putInt32(24 + j, this.maxNameLen);
        hwBlob.putInt32(28 + j, this.maxNumberLen);
        hwBlob.putInt32(32 + j, this.maxEmailLen);
        hwBlob.putInt32(j + 36, this.maxAdditionalNumberLen);
    }
}
