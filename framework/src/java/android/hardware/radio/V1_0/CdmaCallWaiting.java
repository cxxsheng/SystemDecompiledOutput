package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaCallWaiting {
    public java.lang.String number = new java.lang.String();
    public int numberPresentation = 0;
    public java.lang.String name = new java.lang.String();
    public android.hardware.radio.V1_0.CdmaSignalInfoRecord signalInfoRecord = new android.hardware.radio.V1_0.CdmaSignalInfoRecord();
    public int numberType = 0;
    public int numberPlan = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaCallWaiting.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaCallWaiting cdmaCallWaiting = (android.hardware.radio.V1_0.CdmaCallWaiting) obj;
        if (android.os.HidlSupport.deepEquals(this.number, cdmaCallWaiting.number) && this.numberPresentation == cdmaCallWaiting.numberPresentation && android.os.HidlSupport.deepEquals(this.name, cdmaCallWaiting.name) && android.os.HidlSupport.deepEquals(this.signalInfoRecord, cdmaCallWaiting.signalInfoRecord) && this.numberType == cdmaCallWaiting.numberType && this.numberPlan == cdmaCallWaiting.numberPlan) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.number)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.numberPresentation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalInfoRecord)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.numberType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.numberPlan))));
    }

    public final java.lang.String toString() {
        return "{.number = " + this.number + ", .numberPresentation = " + android.hardware.radio.V1_0.CdmaCallWaitingNumberPresentation.toString(this.numberPresentation) + ", .name = " + this.name + ", .signalInfoRecord = " + this.signalInfoRecord + ", .numberType = " + android.hardware.radio.V1_0.CdmaCallWaitingNumberType.toString(this.numberType) + ", .numberPlan = " + android.hardware.radio.V1_0.CdmaCallWaitingNumberPlan.toString(this.numberPlan) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaCallWaiting> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaCallWaiting> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaCallWaiting cdmaCallWaiting = new android.hardware.radio.V1_0.CdmaCallWaiting();
            cdmaCallWaiting.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(cdmaCallWaiting);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.numberPresentation = hwBlob.getInt32(j + 16);
        long j3 = j + 24;
        this.name = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        this.signalInfoRecord.readEmbeddedFromParcel(hwParcel, hwBlob, j + 40);
        this.numberType = hwBlob.getInt32(j + 44);
        this.numberPlan = hwBlob.getInt32(j + 48);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaCallWaiting> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(0 + j, this.number);
        hwBlob.putInt32(16 + j, this.numberPresentation);
        hwBlob.putString(24 + j, this.name);
        this.signalInfoRecord.writeEmbeddedToBlob(hwBlob, 40 + j);
        hwBlob.putInt32(44 + j, this.numberType);
        hwBlob.putInt32(j + 48, this.numberPlan);
    }
}
