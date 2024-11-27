package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class CellInfo {
    public int cellInfoType = 0;
    public boolean registered = false;
    public int timeStampType = 0;
    public long timeStamp = 0;
    public java.util.ArrayList<android.hardware.radio.V1_2.CellInfoGsm> gsm = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_2.CellInfoCdma> cdma = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_2.CellInfoLte> lte = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_2.CellInfoWcdma> wcdma = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_2.CellInfoTdscdma> tdscdma = new java.util.ArrayList<>();
    public int connectionStatus = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.CellInfo.class) {
            return false;
        }
        android.hardware.radio.V1_2.CellInfo cellInfo = (android.hardware.radio.V1_2.CellInfo) obj;
        if (this.cellInfoType == cellInfo.cellInfoType && this.registered == cellInfo.registered && this.timeStampType == cellInfo.timeStampType && this.timeStamp == cellInfo.timeStamp && android.os.HidlSupport.deepEquals(this.gsm, cellInfo.gsm) && android.os.HidlSupport.deepEquals(this.cdma, cellInfo.cdma) && android.os.HidlSupport.deepEquals(this.lte, cellInfo.lte) && android.os.HidlSupport.deepEquals(this.wcdma, cellInfo.wcdma) && android.os.HidlSupport.deepEquals(this.tdscdma, cellInfo.tdscdma) && this.connectionStatus == cellInfo.connectionStatus) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cellInfoType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.registered))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.timeStampType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.timeStamp))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.gsm)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.lte)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.wcdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.tdscdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.connectionStatus))));
    }

    public final java.lang.String toString() {
        return "{.cellInfoType = " + android.hardware.radio.V1_0.CellInfoType.toString(this.cellInfoType) + ", .registered = " + this.registered + ", .timeStampType = " + android.hardware.radio.V1_0.TimeStampType.toString(this.timeStampType) + ", .timeStamp = " + this.timeStamp + ", .gsm = " + this.gsm + ", .cdma = " + this.cdma + ", .lte = " + this.lte + ", .wcdma = " + this.wcdma + ", .tdscdma = " + this.tdscdma + ", .connectionStatus = " + android.hardware.radio.V1_2.CellConnectionStatus.toString(this.connectionStatus) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.CellInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.CellInfo cellInfo = new android.hardware.radio.V1_2.CellInfo();
            cellInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            arrayList.add(cellInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cellInfoType = hwBlob.getInt32(j + 0);
        this.registered = hwBlob.getBool(j + 4);
        this.timeStampType = hwBlob.getInt32(j + 8);
        this.timeStamp = hwBlob.getInt64(j + 16);
        long j2 = j + 24;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, hwBlob.handle(), j2 + 0, true);
        this.gsm.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.CellInfoGsm cellInfoGsm = new android.hardware.radio.V1_2.CellInfoGsm();
            cellInfoGsm.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            this.gsm.add(cellInfoGsm);
        }
        long j3 = j + 40;
        int int322 = hwBlob.getInt32(j3 + 8);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 80, hwBlob.handle(), j3 + 0, true);
        this.cdma.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.radio.V1_2.CellInfoCdma cellInfoCdma = new android.hardware.radio.V1_2.CellInfoCdma();
            cellInfoCdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 80);
            this.cdma.add(cellInfoCdma);
        }
        long j4 = j + 56;
        int int323 = hwBlob.getInt32(j4 + 8);
        android.os.HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 112, hwBlob.handle(), j4 + 0, true);
        this.lte.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            android.hardware.radio.V1_2.CellInfoLte cellInfoLte = new android.hardware.radio.V1_2.CellInfoLte();
            cellInfoLte.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer3, i3 * 112);
            this.lte.add(cellInfoLte);
        }
        long j5 = j + 72;
        int int324 = hwBlob.getInt32(j5 + 8);
        android.os.HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 96, hwBlob.handle(), j5 + 0, true);
        this.wcdma.clear();
        for (int i4 = 0; i4 < int324; i4++) {
            android.hardware.radio.V1_2.CellInfoWcdma cellInfoWcdma = new android.hardware.radio.V1_2.CellInfoWcdma();
            cellInfoWcdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer4, i4 * 96);
            this.wcdma.add(cellInfoWcdma);
        }
        long j6 = j + 88;
        int int325 = hwBlob.getInt32(8 + j6);
        android.os.HwBlob readEmbeddedBuffer5 = hwParcel.readEmbeddedBuffer(int325 * 104, hwBlob.handle(), j6 + 0, true);
        this.tdscdma.clear();
        for (int i5 = 0; i5 < int325; i5++) {
            android.hardware.radio.V1_2.CellInfoTdscdma cellInfoTdscdma = new android.hardware.radio.V1_2.CellInfoTdscdma();
            cellInfoTdscdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer5, i5 * 104);
            this.tdscdma.add(cellInfoTdscdma);
        }
        this.connectionStatus = hwBlob.getInt32(j + 104);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.cellInfoType);
        hwBlob.putBool(j + 4, this.registered);
        hwBlob.putInt32(j + 8, this.timeStampType);
        hwBlob.putInt64(j + 16, this.timeStamp);
        int size = this.gsm.size();
        long j2 = j + 24;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            this.gsm.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.cdma.size();
        long j3 = j + 40;
        hwBlob.putInt32(j3 + 8, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 80);
        for (int i2 = 0; i2 < size2; i2++) {
            this.cdma.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 80);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        int size3 = this.lte.size();
        long j4 = j + 56;
        hwBlob.putInt32(j4 + 8, size3);
        hwBlob.putBool(j4 + 12, false);
        android.os.HwBlob hwBlob4 = new android.os.HwBlob(size3 * 112);
        for (int i3 = 0; i3 < size3; i3++) {
            this.lte.get(i3).writeEmbeddedToBlob(hwBlob4, i3 * 112);
        }
        hwBlob.putBlob(j4 + 0, hwBlob4);
        int size4 = this.wcdma.size();
        long j5 = j + 72;
        hwBlob.putInt32(j5 + 8, size4);
        hwBlob.putBool(j5 + 12, false);
        android.os.HwBlob hwBlob5 = new android.os.HwBlob(size4 * 96);
        for (int i4 = 0; i4 < size4; i4++) {
            this.wcdma.get(i4).writeEmbeddedToBlob(hwBlob5, i4 * 96);
        }
        hwBlob.putBlob(j5 + 0, hwBlob5);
        int size5 = this.tdscdma.size();
        long j6 = j + 88;
        hwBlob.putInt32(8 + j6, size5);
        hwBlob.putBool(j6 + 12, false);
        android.os.HwBlob hwBlob6 = new android.os.HwBlob(size5 * 104);
        for (int i5 = 0; i5 < size5; i5++) {
            this.tdscdma.get(i5).writeEmbeddedToBlob(hwBlob6, i5 * 104);
        }
        hwBlob.putBlob(j6 + 0, hwBlob6);
        hwBlob.putInt32(j + 104, this.connectionStatus);
    }
}
