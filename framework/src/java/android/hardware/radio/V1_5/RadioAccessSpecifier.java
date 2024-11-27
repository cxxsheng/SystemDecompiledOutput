package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class RadioAccessSpecifier {
    public int radioAccessNetwork = 0;
    public android.hardware.radio.V1_5.RadioAccessSpecifier.Bands bands = new android.hardware.radio.V1_5.RadioAccessSpecifier.Bands();
    public java.util.ArrayList<java.lang.Integer> channels = new java.util.ArrayList<>();

    public static final class Bands {
        private byte hidl_d = 0;
        private java.lang.Object hidl_o;

        public Bands() {
            this.hidl_o = null;
            this.hidl_o = new java.util.ArrayList();
        }

        public static final class hidl_discriminator {
            public static final byte eutranBands = 2;
            public static final byte geranBands = 0;
            public static final byte ngranBands = 3;
            public static final byte utranBands = 1;

            public static final java.lang.String getName(byte b) {
                switch (b) {
                    case 0:
                        return "geranBands";
                    case 1:
                        return "utranBands";
                    case 2:
                        return "eutranBands";
                    case 3:
                        return "ngranBands";
                    default:
                        return "Unknown";
                }
            }

            private hidl_discriminator() {
            }
        }

        public void geranBands(java.util.ArrayList<java.lang.Integer> arrayList) {
            this.hidl_d = (byte) 0;
            this.hidl_o = arrayList;
        }

        public java.util.ArrayList<java.lang.Integer> geranBands() {
            if (this.hidl_d != 0) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.RadioAccessSpecifier.Bands.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.util.ArrayList.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (java.util.ArrayList) this.hidl_o;
        }

        public void utranBands(java.util.ArrayList<java.lang.Integer> arrayList) {
            this.hidl_d = (byte) 1;
            this.hidl_o = arrayList;
        }

        public java.util.ArrayList<java.lang.Integer> utranBands() {
            if (this.hidl_d != 1) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.RadioAccessSpecifier.Bands.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.util.ArrayList.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (java.util.ArrayList) this.hidl_o;
        }

        public void eutranBands(java.util.ArrayList<java.lang.Integer> arrayList) {
            this.hidl_d = (byte) 2;
            this.hidl_o = arrayList;
        }

        public java.util.ArrayList<java.lang.Integer> eutranBands() {
            if (this.hidl_d != 2) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.RadioAccessSpecifier.Bands.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.util.ArrayList.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (java.util.ArrayList) this.hidl_o;
        }

        public void ngranBands(java.util.ArrayList<java.lang.Integer> arrayList) {
            this.hidl_d = (byte) 3;
            this.hidl_o = arrayList;
        }

        public java.util.ArrayList<java.lang.Integer> ngranBands() {
            if (this.hidl_d != 3) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.RadioAccessSpecifier.Bands.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.util.ArrayList.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (java.util.ArrayList) this.hidl_o;
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.radio.V1_5.RadioAccessSpecifier.Bands.class) {
                return false;
            }
            android.hardware.radio.V1_5.RadioAccessSpecifier.Bands bands = (android.hardware.radio.V1_5.RadioAccessSpecifier.Bands) obj;
            if (this.hidl_d == bands.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, bands.hidl_o)) {
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
                    sb.append(".geranBands = ");
                    sb.append(geranBands());
                    break;
                case 1:
                    sb.append(".utranBands = ");
                    sb.append(utranBands());
                    break;
                case 2:
                    sb.append(".eutranBands = ");
                    sb.append(eutranBands());
                    break;
                case 3:
                    sb.append(".ngranBands = ");
                    sb.append(ngranBands());
                    break;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.radio.V1_5.RadioAccessSpecifier.Bands> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.radio.V1_5.RadioAccessSpecifier.Bands> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.radio.V1_5.RadioAccessSpecifier.Bands bands = new android.hardware.radio.V1_5.RadioAccessSpecifier.Bands();
                bands.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
                arrayList.add(bands);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.hidl_d = hwBlob.getInt8(j + 0);
            int i = 0;
            switch (this.hidl_d) {
                case 0:
                    this.hidl_o = new java.util.ArrayList();
                    long j2 = j + 8;
                    int int32 = hwBlob.getInt32(8 + j2);
                    android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j2 + 0, true);
                    ((java.util.ArrayList) this.hidl_o).clear();
                    while (i < int32) {
                        ((java.util.ArrayList) this.hidl_o).add(java.lang.Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
                        i++;
                    }
                    return;
                case 1:
                    this.hidl_o = new java.util.ArrayList();
                    long j3 = j + 8;
                    int int322 = hwBlob.getInt32(8 + j3);
                    android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 4, hwBlob.handle(), j3 + 0, true);
                    ((java.util.ArrayList) this.hidl_o).clear();
                    while (i < int322) {
                        ((java.util.ArrayList) this.hidl_o).add(java.lang.Integer.valueOf(readEmbeddedBuffer2.getInt32(i * 4)));
                        i++;
                    }
                    return;
                case 2:
                    this.hidl_o = new java.util.ArrayList();
                    long j4 = j + 8;
                    int int323 = hwBlob.getInt32(8 + j4);
                    android.os.HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 4, hwBlob.handle(), j4 + 0, true);
                    ((java.util.ArrayList) this.hidl_o).clear();
                    while (i < int323) {
                        ((java.util.ArrayList) this.hidl_o).add(java.lang.Integer.valueOf(readEmbeddedBuffer3.getInt32(i * 4)));
                        i++;
                    }
                    return;
                case 3:
                    this.hidl_o = new java.util.ArrayList();
                    long j5 = j + 8;
                    int int324 = hwBlob.getInt32(8 + j5);
                    android.os.HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 4, hwBlob.handle(), j5 + 0, true);
                    ((java.util.ArrayList) this.hidl_o).clear();
                    while (i < int324) {
                        ((java.util.ArrayList) this.hidl_o).add(java.lang.Integer.valueOf(readEmbeddedBuffer4.getInt32(i * 4)));
                        i++;
                    }
                    return;
                default:
                    throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(24);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.RadioAccessSpecifier.Bands> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt8(j + 0, this.hidl_d);
            int i = 0;
            switch (this.hidl_d) {
                case 0:
                    int size = geranBands().size();
                    long j2 = j + 8;
                    hwBlob.putInt32(8 + j2, size);
                    hwBlob.putBool(12 + j2, false);
                    android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
                    while (i < size) {
                        hwBlob2.putInt32(i * 4, geranBands().get(i).intValue());
                        i++;
                    }
                    hwBlob.putBlob(j2 + 0, hwBlob2);
                    return;
                case 1:
                    int size2 = utranBands().size();
                    long j3 = j + 8;
                    hwBlob.putInt32(8 + j3, size2);
                    hwBlob.putBool(12 + j3, false);
                    android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 4);
                    while (i < size2) {
                        hwBlob3.putInt32(i * 4, utranBands().get(i).intValue());
                        i++;
                    }
                    hwBlob.putBlob(j3 + 0, hwBlob3);
                    return;
                case 2:
                    int size3 = eutranBands().size();
                    long j4 = j + 8;
                    hwBlob.putInt32(8 + j4, size3);
                    hwBlob.putBool(12 + j4, false);
                    android.os.HwBlob hwBlob4 = new android.os.HwBlob(size3 * 4);
                    while (i < size3) {
                        hwBlob4.putInt32(i * 4, eutranBands().get(i).intValue());
                        i++;
                    }
                    hwBlob.putBlob(j4 + 0, hwBlob4);
                    return;
                case 3:
                    int size4 = ngranBands().size();
                    long j5 = j + 8;
                    hwBlob.putInt32(8 + j5, size4);
                    hwBlob.putBool(12 + j5, false);
                    android.os.HwBlob hwBlob5 = new android.os.HwBlob(size4 * 4);
                    while (i < size4) {
                        hwBlob5.putInt32(i * 4, ngranBands().get(i).intValue());
                        i++;
                    }
                    hwBlob.putBlob(j5 + 0, hwBlob5);
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
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.RadioAccessSpecifier.class) {
            return false;
        }
        android.hardware.radio.V1_5.RadioAccessSpecifier radioAccessSpecifier = (android.hardware.radio.V1_5.RadioAccessSpecifier) obj;
        if (this.radioAccessNetwork == radioAccessSpecifier.radioAccessNetwork && android.os.HidlSupport.deepEquals(this.bands, radioAccessSpecifier.bands) && android.os.HidlSupport.deepEquals(this.channels, radioAccessSpecifier.channels)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.radioAccessNetwork))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.bands)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.channels)));
    }

    public final java.lang.String toString() {
        return "{.radioAccessNetwork = " + android.hardware.radio.V1_5.RadioAccessNetworks.toString(this.radioAccessNetwork) + ", .bands = " + this.bands + ", .channels = " + this.channels + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.RadioAccessSpecifier> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.RadioAccessSpecifier> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.RadioAccessSpecifier radioAccessSpecifier = new android.hardware.radio.V1_5.RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(radioAccessSpecifier);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.radioAccessNetwork = hwBlob.getInt32(j + 0);
        this.bands.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
        long j2 = j + 32;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j2 + 0, true);
        this.channels.clear();
        for (int i = 0; i < int32; i++) {
            this.channels.add(java.lang.Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.RadioAccessSpecifier> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.radioAccessNetwork);
        this.bands.writeEmbeddedToBlob(hwBlob, j + 8);
        int size = this.channels.size();
        long j2 = j + 32;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.channels.get(i).intValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
