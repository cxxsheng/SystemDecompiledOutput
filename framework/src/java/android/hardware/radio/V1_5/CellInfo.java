package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class CellInfo {
    public boolean registered = false;
    public int timeStampType = 0;
    public long timeStamp = 0;
    public int connectionStatus = 0;
    public android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo ratSpecificInfo = new android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo();

    public static final class CellInfoRatSpecificInfo {
        private byte hidl_d = 0;
        private java.lang.Object hidl_o;

        public CellInfoRatSpecificInfo() {
            this.hidl_o = null;
            this.hidl_o = new android.hardware.radio.V1_5.CellInfoGsm();
        }

        public static final class hidl_discriminator {
            public static final byte cdma = 5;
            public static final byte gsm = 0;
            public static final byte lte = 3;
            public static final byte nr = 4;
            public static final byte tdscdma = 2;
            public static final byte wcdma = 1;

            public static final java.lang.String getName(byte b) {
                switch (b) {
                    case 0:
                        return "gsm";
                    case 1:
                        return "wcdma";
                    case 2:
                        return "tdscdma";
                    case 3:
                        return "lte";
                    case 4:
                        return "nr";
                    case 5:
                        return "cdma";
                    default:
                        return "Unknown";
                }
            }

            private hidl_discriminator() {
            }
        }

        public void gsm(android.hardware.radio.V1_5.CellInfoGsm cellInfoGsm) {
            this.hidl_d = (byte) 0;
            this.hidl_o = cellInfoGsm;
        }

        public android.hardware.radio.V1_5.CellInfoGsm gsm() {
            if (this.hidl_d != 0) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_5.CellInfoGsm.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_5.CellInfoGsm) this.hidl_o;
        }

        public void wcdma(android.hardware.radio.V1_5.CellInfoWcdma cellInfoWcdma) {
            this.hidl_d = (byte) 1;
            this.hidl_o = cellInfoWcdma;
        }

        public android.hardware.radio.V1_5.CellInfoWcdma wcdma() {
            if (this.hidl_d != 1) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_5.CellInfoWcdma.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_5.CellInfoWcdma) this.hidl_o;
        }

        public void tdscdma(android.hardware.radio.V1_5.CellInfoTdscdma cellInfoTdscdma) {
            this.hidl_d = (byte) 2;
            this.hidl_o = cellInfoTdscdma;
        }

        public android.hardware.radio.V1_5.CellInfoTdscdma tdscdma() {
            if (this.hidl_d != 2) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_5.CellInfoTdscdma.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_5.CellInfoTdscdma) this.hidl_o;
        }

        public void lte(android.hardware.radio.V1_5.CellInfoLte cellInfoLte) {
            this.hidl_d = (byte) 3;
            this.hidl_o = cellInfoLte;
        }

        public android.hardware.radio.V1_5.CellInfoLte lte() {
            if (this.hidl_d != 3) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_5.CellInfoLte.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_5.CellInfoLte) this.hidl_o;
        }

        public void nr(android.hardware.radio.V1_5.CellInfoNr cellInfoNr) {
            this.hidl_d = (byte) 4;
            this.hidl_o = cellInfoNr;
        }

        public android.hardware.radio.V1_5.CellInfoNr nr() {
            if (this.hidl_d != 4) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_5.CellInfoNr.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_5.CellInfoNr) this.hidl_o;
        }

        public void cdma(android.hardware.radio.V1_2.CellInfoCdma cellInfoCdma) {
            this.hidl_d = (byte) 5;
            this.hidl_o = cellInfoCdma;
        }

        public android.hardware.radio.V1_2.CellInfoCdma cdma() {
            if (this.hidl_d != 5) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_2.CellInfoCdma.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_2.CellInfoCdma) this.hidl_o;
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo.class) {
                return false;
            }
            android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo cellInfoRatSpecificInfo = (android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo) obj;
            if (this.hidl_d == cellInfoRatSpecificInfo.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, cellInfoRatSpecificInfo.hidl_o)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.hidl_o)), java.lang.Integer.valueOf(java.util.Objects.hashCode(java.lang.Byte.valueOf(this.hidl_d))));
        }

        public final java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{");
            switch (this.hidl_d) {
                case 0:
                    sb.append(".gsm = ");
                    sb.append(gsm());
                    break;
                case 1:
                    sb.append(".wcdma = ");
                    sb.append(wcdma());
                    break;
                case 2:
                    sb.append(".tdscdma = ");
                    sb.append(tdscdma());
                    break;
                case 3:
                    sb.append(".lte = ");
                    sb.append(lte());
                    break;
                case 4:
                    sb.append(".nr = ");
                    sb.append(nr());
                    break;
                case 5:
                    sb.append(".cdma = ");
                    sb.append(cdma());
                    break;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(192L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 192, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo cellInfoRatSpecificInfo = new android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo();
                cellInfoRatSpecificInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 192);
                arrayList.add(cellInfoRatSpecificInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.hidl_d = hwBlob.getInt8(0 + j);
            switch (this.hidl_d) {
                case 0:
                    this.hidl_o = new android.hardware.radio.V1_5.CellInfoGsm();
                    ((android.hardware.radio.V1_5.CellInfoGsm) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                    return;
                case 1:
                    this.hidl_o = new android.hardware.radio.V1_5.CellInfoWcdma();
                    ((android.hardware.radio.V1_5.CellInfoWcdma) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                    return;
                case 2:
                    this.hidl_o = new android.hardware.radio.V1_5.CellInfoTdscdma();
                    ((android.hardware.radio.V1_5.CellInfoTdscdma) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                    return;
                case 3:
                    this.hidl_o = new android.hardware.radio.V1_5.CellInfoLte();
                    ((android.hardware.radio.V1_5.CellInfoLte) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                    return;
                case 4:
                    this.hidl_o = new android.hardware.radio.V1_5.CellInfoNr();
                    ((android.hardware.radio.V1_5.CellInfoNr) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                    return;
                case 5:
                    this.hidl_o = new android.hardware.radio.V1_2.CellInfoCdma();
                    ((android.hardware.radio.V1_2.CellInfoCdma) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                    return;
                default:
                    throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(192);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.CellInfo.CellInfoRatSpecificInfo> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 192);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 192);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt8(0 + j, this.hidl_d);
            switch (this.hidl_d) {
                case 0:
                    gsm().writeEmbeddedToBlob(hwBlob, j + 8);
                    return;
                case 1:
                    wcdma().writeEmbeddedToBlob(hwBlob, j + 8);
                    return;
                case 2:
                    tdscdma().writeEmbeddedToBlob(hwBlob, j + 8);
                    return;
                case 3:
                    lte().writeEmbeddedToBlob(hwBlob, j + 8);
                    return;
                case 4:
                    nr().writeEmbeddedToBlob(hwBlob, j + 8);
                    return;
                case 5:
                    cdma().writeEmbeddedToBlob(hwBlob, j + 8);
                    return;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.CellInfo.class) {
            return false;
        }
        android.hardware.radio.V1_5.CellInfo cellInfo = (android.hardware.radio.V1_5.CellInfo) obj;
        if (this.registered == cellInfo.registered && this.timeStampType == cellInfo.timeStampType && this.timeStamp == cellInfo.timeStamp && this.connectionStatus == cellInfo.connectionStatus && android.os.HidlSupport.deepEquals(this.ratSpecificInfo, cellInfo.ratSpecificInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.registered))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.timeStampType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.timeStamp))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.connectionStatus))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.ratSpecificInfo)));
    }

    public final java.lang.String toString() {
        return "{.registered = " + this.registered + ", .timeStampType = " + android.hardware.radio.V1_0.TimeStampType.toString(this.timeStampType) + ", .timeStamp = " + this.timeStamp + ", .connectionStatus = " + android.hardware.radio.V1_2.CellConnectionStatus.toString(this.connectionStatus) + ", .ratSpecificInfo = " + this.ratSpecificInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(216L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.CellInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.CellInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 216, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.CellInfo cellInfo = new android.hardware.radio.V1_5.CellInfo();
            cellInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 216);
            arrayList.add(cellInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.registered = hwBlob.getBool(0 + j);
        this.timeStampType = hwBlob.getInt32(4 + j);
        this.timeStamp = hwBlob.getInt64(8 + j);
        this.connectionStatus = hwBlob.getInt32(16 + j);
        this.ratSpecificInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(216);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.CellInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 216);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 216);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putBool(0 + j, this.registered);
        hwBlob.putInt32(4 + j, this.timeStampType);
        hwBlob.putInt64(8 + j, this.timeStamp);
        hwBlob.putInt32(16 + j, this.connectionStatus);
        this.ratSpecificInfo.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
