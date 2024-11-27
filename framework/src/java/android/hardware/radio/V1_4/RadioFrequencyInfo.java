package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class RadioFrequencyInfo {
    private byte hidl_d = 0;
    private java.lang.Object hidl_o;

    public RadioFrequencyInfo() {
        this.hidl_o = null;
        this.hidl_o = 0;
    }

    public static final class hidl_discriminator {
        public static final byte channelNumber = 1;
        public static final byte range = 0;

        public static final java.lang.String getName(byte b) {
            switch (b) {
                case 0:
                    return "range";
                case 1:
                    return "channelNumber";
                default:
                    return "Unknown";
            }
        }

        private hidl_discriminator() {
        }
    }

    public void range(int i) {
        this.hidl_d = (byte) 0;
        this.hidl_o = java.lang.Integer.valueOf(i);
    }

    public int range() {
        if (this.hidl_d != 0) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_4.RadioFrequencyInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !java.lang.Integer.class.isInstance(this.hidl_o)) {
            throw new java.lang.Error("Union is in a corrupted state.");
        }
        return ((java.lang.Integer) this.hidl_o).intValue();
    }

    public void channelNumber(int i) {
        this.hidl_d = (byte) 1;
        this.hidl_o = java.lang.Integer.valueOf(i);
    }

    public int channelNumber() {
        if (this.hidl_d != 1) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_4.RadioFrequencyInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
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
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.RadioFrequencyInfo.class) {
            return false;
        }
        android.hardware.radio.V1_4.RadioFrequencyInfo radioFrequencyInfo = (android.hardware.radio.V1_4.RadioFrequencyInfo) obj;
        if (this.hidl_d == radioFrequencyInfo.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, radioFrequencyInfo.hidl_o)) {
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
                sb.append(".range = ");
                sb.append(android.hardware.radio.V1_4.FrequencyRange.toString(range()));
                break;
            case 1:
                sb.append(".channelNumber = ");
                sb.append(channelNumber());
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

    public static final java.util.ArrayList<android.hardware.radio.V1_4.RadioFrequencyInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.RadioFrequencyInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.RadioFrequencyInfo radioFrequencyInfo = new android.hardware.radio.V1_4.RadioFrequencyInfo();
            radioFrequencyInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
            arrayList.add(radioFrequencyInfo);
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
            default:
                throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.RadioFrequencyInfo> arrayList) {
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
                hwBlob.putInt32(j + 4, range());
                return;
            case 1:
                hwBlob.putInt32(j + 4, channelNumber());
                return;
            default:
                throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }
}
