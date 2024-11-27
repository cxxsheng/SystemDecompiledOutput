package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class PhonebookRecordInfo {
    public int recordId = 0;
    public java.lang.String name = new java.lang.String();
    public java.lang.String number = new java.lang.String();
    public java.util.ArrayList<java.lang.String> emails = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.String> additionalNumbers = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.PhonebookRecordInfo.class) {
            return false;
        }
        android.hardware.radio.V1_6.PhonebookRecordInfo phonebookRecordInfo = (android.hardware.radio.V1_6.PhonebookRecordInfo) obj;
        if (this.recordId == phonebookRecordInfo.recordId && android.os.HidlSupport.deepEquals(this.name, phonebookRecordInfo.name) && android.os.HidlSupport.deepEquals(this.number, phonebookRecordInfo.number) && android.os.HidlSupport.deepEquals(this.emails, phonebookRecordInfo.emails) && android.os.HidlSupport.deepEquals(this.additionalNumbers, phonebookRecordInfo.additionalNumbers)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.recordId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.number)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.emails)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.additionalNumbers)));
    }

    public final java.lang.String toString() {
        return "{.recordId = " + this.recordId + ", .name = " + this.name + ", .number = " + this.number + ", .emails = " + this.emails + ", .additionalNumbers = " + this.additionalNumbers + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.PhonebookRecordInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.PhonebookRecordInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.PhonebookRecordInfo phonebookRecordInfo = new android.hardware.radio.V1_6.PhonebookRecordInfo();
            phonebookRecordInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(phonebookRecordInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.recordId = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 24;
        this.number = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 40;
        int int32 = hwBlob.getInt32(j4 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j4 + 0, true);
        this.emails.clear();
        for (int i = 0; i < int32; i++) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer.getString(i * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), r10 + 0, false);
            this.emails.add(string);
        }
        long j5 = j + 56;
        int int322 = hwBlob.getInt32(8 + j5);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j5 + 0, true);
        this.additionalNumbers.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            new java.lang.String();
            java.lang.String string2 = readEmbeddedBuffer2.getString(i2 * 16);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, readEmbeddedBuffer2.handle(), r3 + 0, false);
            this.additionalNumbers.add(string2);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.PhonebookRecordInfo> arrayList) {
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
        hwBlob.putInt32(j + 0, this.recordId);
        hwBlob.putString(j + 8, this.name);
        hwBlob.putString(j + 24, this.number);
        int size = this.emails.size();
        long j2 = j + 40;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.emails.get(i));
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.additionalNumbers.size();
        long j3 = j + 56;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(12 + j3, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.additionalNumbers.get(i2));
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
