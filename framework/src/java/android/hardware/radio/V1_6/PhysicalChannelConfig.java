package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class PhysicalChannelConfig {
    public int status = 0;
    public int rat = 0;
    public int downlinkChannelNumber = 0;
    public int uplinkChannelNumber = 0;
    public int cellBandwidthDownlinkKhz = 0;
    public int cellBandwidthUplinkKhz = 0;
    public java.util.ArrayList<java.lang.Integer> contextIds = new java.util.ArrayList<>();
    public int physicalCellId = 0;
    public android.hardware.radio.V1_6.PhysicalChannelConfig.Band band = new android.hardware.radio.V1_6.PhysicalChannelConfig.Band();

    public static final class Band {
        private byte hidl_d = 0;
        private java.lang.Object hidl_o;

        public Band() {
            this.hidl_o = null;
            this.hidl_o = 0;
        }

        public static final class hidl_discriminator {
            public static final byte eutranBand = 2;
            public static final byte geranBand = 0;
            public static final byte ngranBand = 3;
            public static final byte utranBand = 1;

            public static final java.lang.String getName(byte b) {
                switch (b) {
                    case 0:
                        return "geranBand";
                    case 1:
                        return "utranBand";
                    case 2:
                        return "eutranBand";
                    case 3:
                        return "ngranBand";
                    default:
                        return "Unknown";
                }
            }

            private hidl_discriminator() {
            }
        }

        public void geranBand(int i) {
            this.hidl_d = (byte) 0;
            this.hidl_o = java.lang.Integer.valueOf(i);
        }

        public int geranBand() {
            if (this.hidl_d != 0) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.PhysicalChannelConfig.Band.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.lang.Integer.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return ((java.lang.Integer) this.hidl_o).intValue();
        }

        public void utranBand(int i) {
            this.hidl_d = (byte) 1;
            this.hidl_o = java.lang.Integer.valueOf(i);
        }

        public int utranBand() {
            if (this.hidl_d != 1) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.PhysicalChannelConfig.Band.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.lang.Integer.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return ((java.lang.Integer) this.hidl_o).intValue();
        }

        public void eutranBand(int i) {
            this.hidl_d = (byte) 2;
            this.hidl_o = java.lang.Integer.valueOf(i);
        }

        public int eutranBand() {
            if (this.hidl_d != 2) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.PhysicalChannelConfig.Band.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.lang.Integer.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return ((java.lang.Integer) this.hidl_o).intValue();
        }

        public void ngranBand(int i) {
            this.hidl_d = (byte) 3;
            this.hidl_o = java.lang.Integer.valueOf(i);
        }

        public int ngranBand() {
            if (this.hidl_d != 3) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.PhysicalChannelConfig.Band.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.lang.Integer.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return ((java.lang.Integer) this.hidl_o).intValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.radio.V1_6.PhysicalChannelConfig.Band.class) {
                return false;
            }
            android.hardware.radio.V1_6.PhysicalChannelConfig.Band band = (android.hardware.radio.V1_6.PhysicalChannelConfig.Band) obj;
            if (this.hidl_d == band.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, band.hidl_o)) {
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
                    sb.append(".geranBand = ");
                    sb.append(android.hardware.radio.V1_1.GeranBands.toString(geranBand()));
                    break;
                case 1:
                    sb.append(".utranBand = ");
                    sb.append(android.hardware.radio.V1_1.UtranBands.toString(utranBand()));
                    break;
                case 2:
                    sb.append(".eutranBand = ");
                    sb.append(android.hardware.radio.V1_1.EutranBands.toString(eutranBand()));
                    break;
                case 3:
                    sb.append(".ngranBand = ");
                    sb.append(android.hardware.radio.V1_6.NgranBands.toString(ngranBand()));
                    break;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.radio.V1_6.PhysicalChannelConfig.Band> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.radio.V1_6.PhysicalChannelConfig.Band> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.radio.V1_6.PhysicalChannelConfig.Band band = new android.hardware.radio.V1_6.PhysicalChannelConfig.Band();
                band.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
                arrayList.add(band);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.hidl_d = hwBlob.getInt8(0 + j);
            switch (this.hidl_d) {
                case 0:
                    this.hidl_o = 0;
                    this.hidl_o = java.lang.Integer.valueOf(hwBlob.getInt32(j + 4));
                    return;
                case 1:
                    this.hidl_o = 0;
                    this.hidl_o = java.lang.Integer.valueOf(hwBlob.getInt32(j + 4));
                    return;
                case 2:
                    this.hidl_o = 0;
                    this.hidl_o = java.lang.Integer.valueOf(hwBlob.getInt32(j + 4));
                    return;
                case 3:
                    this.hidl_o = 0;
                    this.hidl_o = java.lang.Integer.valueOf(hwBlob.getInt32(j + 4));
                    return;
                default:
                    throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(8);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.PhysicalChannelConfig.Band> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 8);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt8(0 + j, this.hidl_d);
            switch (this.hidl_d) {
                case 0:
                    hwBlob.putInt32(j + 4, geranBand());
                    return;
                case 1:
                    hwBlob.putInt32(j + 4, utranBand());
                    return;
                case 2:
                    hwBlob.putInt32(j + 4, eutranBand());
                    return;
                case 3:
                    hwBlob.putInt32(j + 4, ngranBand());
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
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.PhysicalChannelConfig.class) {
            return false;
        }
        android.hardware.radio.V1_6.PhysicalChannelConfig physicalChannelConfig = (android.hardware.radio.V1_6.PhysicalChannelConfig) obj;
        if (this.status == physicalChannelConfig.status && this.rat == physicalChannelConfig.rat && this.downlinkChannelNumber == physicalChannelConfig.downlinkChannelNumber && this.uplinkChannelNumber == physicalChannelConfig.uplinkChannelNumber && this.cellBandwidthDownlinkKhz == physicalChannelConfig.cellBandwidthDownlinkKhz && this.cellBandwidthUplinkKhz == physicalChannelConfig.cellBandwidthUplinkKhz && android.os.HidlSupport.deepEquals(this.contextIds, physicalChannelConfig.contextIds) && this.physicalCellId == physicalChannelConfig.physicalCellId && android.os.HidlSupport.deepEquals(this.band, physicalChannelConfig.band)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.status))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rat))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.downlinkChannelNumber))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.uplinkChannelNumber))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cellBandwidthDownlinkKhz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cellBandwidthUplinkKhz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.contextIds)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.physicalCellId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.band)));
    }

    public final java.lang.String toString() {
        return "{.status = " + android.hardware.radio.V1_2.CellConnectionStatus.toString(this.status) + ", .rat = " + android.hardware.radio.V1_4.RadioTechnology.toString(this.rat) + ", .downlinkChannelNumber = " + this.downlinkChannelNumber + ", .uplinkChannelNumber = " + this.uplinkChannelNumber + ", .cellBandwidthDownlinkKhz = " + this.cellBandwidthDownlinkKhz + ", .cellBandwidthUplinkKhz = " + this.cellBandwidthUplinkKhz + ", .contextIds = " + this.contextIds + ", .physicalCellId = " + this.physicalCellId + ", .band = " + this.band + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.PhysicalChannelConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.PhysicalChannelConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.PhysicalChannelConfig physicalChannelConfig = new android.hardware.radio.V1_6.PhysicalChannelConfig();
            physicalChannelConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(physicalChannelConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j + 0);
        this.rat = hwBlob.getInt32(j + 4);
        this.downlinkChannelNumber = hwBlob.getInt32(j + 8);
        this.uplinkChannelNumber = hwBlob.getInt32(j + 12);
        this.cellBandwidthDownlinkKhz = hwBlob.getInt32(j + 16);
        this.cellBandwidthUplinkKhz = hwBlob.getInt32(j + 20);
        long j2 = j + 24;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j2 + 0, true);
        this.contextIds.clear();
        for (int i = 0; i < int32; i++) {
            this.contextIds.add(java.lang.Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        this.physicalCellId = hwBlob.getInt32(j + 40);
        this.band.readEmbeddedFromParcel(hwParcel, hwBlob, j + 44);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.PhysicalChannelConfig> arrayList) {
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
        hwBlob.putInt32(j + 0, this.status);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putInt32(j + 8, this.downlinkChannelNumber);
        hwBlob.putInt32(j + 12, this.uplinkChannelNumber);
        hwBlob.putInt32(16 + j, this.cellBandwidthDownlinkKhz);
        hwBlob.putInt32(20 + j, this.cellBandwidthUplinkKhz);
        int size = this.contextIds.size();
        long j2 = 24 + j;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.contextIds.get(i).intValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putInt32(40 + j, this.physicalCellId);
        this.band.writeEmbeddedToBlob(hwBlob, j + 44);
    }
}
