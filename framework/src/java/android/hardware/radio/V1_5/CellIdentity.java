package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class CellIdentity {
    private byte hidl_d = 0;
    private java.lang.Object hidl_o;

    public CellIdentity() {
        this.hidl_o = null;
        this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
    }

    public static final class hidl_discriminator {
        public static final byte cdma = 4;
        public static final byte gsm = 1;
        public static final byte lte = 5;
        public static final byte noinit = 0;
        public static final byte nr = 6;
        public static final byte tdscdma = 3;
        public static final byte wcdma = 2;

        public static final java.lang.String getName(byte b) {
            switch (b) {
                case 0:
                    return "noinit";
                case 1:
                    return "gsm";
                case 2:
                    return "wcdma";
                case 3:
                    return "tdscdma";
                case 4:
                    return "cdma";
                case 5:
                    return "lte";
                case 6:
                    return "nr";
                default:
                    return "Unknown";
            }
        }

        private hidl_discriminator() {
        }
    }

    public void noinit(android.internal.hidl.safe_union.V1_0.Monostate monostate) {
        this.hidl_d = (byte) 0;
        this.hidl_o = monostate;
    }

    public android.internal.hidl.safe_union.V1_0.Monostate noinit() {
        if (this.hidl_d != 0) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellIdentity.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !android.internal.hidl.safe_union.V1_0.Monostate.class.isInstance(this.hidl_o)) {
            throw new java.lang.Error("Union is in a corrupted state.");
        }
        return (android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o;
    }

    public void gsm(android.hardware.radio.V1_5.CellIdentityGsm cellIdentityGsm) {
        this.hidl_d = (byte) 1;
        this.hidl_o = cellIdentityGsm;
    }

    public android.hardware.radio.V1_5.CellIdentityGsm gsm() {
        if (this.hidl_d != 1) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellIdentity.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !android.hardware.radio.V1_5.CellIdentityGsm.class.isInstance(this.hidl_o)) {
            throw new java.lang.Error("Union is in a corrupted state.");
        }
        return (android.hardware.radio.V1_5.CellIdentityGsm) this.hidl_o;
    }

    public void wcdma(android.hardware.radio.V1_5.CellIdentityWcdma cellIdentityWcdma) {
        this.hidl_d = (byte) 2;
        this.hidl_o = cellIdentityWcdma;
    }

    public android.hardware.radio.V1_5.CellIdentityWcdma wcdma() {
        if (this.hidl_d != 2) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellIdentity.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !android.hardware.radio.V1_5.CellIdentityWcdma.class.isInstance(this.hidl_o)) {
            throw new java.lang.Error("Union is in a corrupted state.");
        }
        return (android.hardware.radio.V1_5.CellIdentityWcdma) this.hidl_o;
    }

    public void tdscdma(android.hardware.radio.V1_5.CellIdentityTdscdma cellIdentityTdscdma) {
        this.hidl_d = (byte) 3;
        this.hidl_o = cellIdentityTdscdma;
    }

    public android.hardware.radio.V1_5.CellIdentityTdscdma tdscdma() {
        if (this.hidl_d != 3) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellIdentity.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !android.hardware.radio.V1_5.CellIdentityTdscdma.class.isInstance(this.hidl_o)) {
            throw new java.lang.Error("Union is in a corrupted state.");
        }
        return (android.hardware.radio.V1_5.CellIdentityTdscdma) this.hidl_o;
    }

    public void cdma(android.hardware.radio.V1_2.CellIdentityCdma cellIdentityCdma) {
        this.hidl_d = (byte) 4;
        this.hidl_o = cellIdentityCdma;
    }

    public android.hardware.radio.V1_2.CellIdentityCdma cdma() {
        if (this.hidl_d != 4) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellIdentity.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !android.hardware.radio.V1_2.CellIdentityCdma.class.isInstance(this.hidl_o)) {
            throw new java.lang.Error("Union is in a corrupted state.");
        }
        return (android.hardware.radio.V1_2.CellIdentityCdma) this.hidl_o;
    }

    public void lte(android.hardware.radio.V1_5.CellIdentityLte cellIdentityLte) {
        this.hidl_d = (byte) 5;
        this.hidl_o = cellIdentityLte;
    }

    public android.hardware.radio.V1_5.CellIdentityLte lte() {
        if (this.hidl_d != 5) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellIdentity.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !android.hardware.radio.V1_5.CellIdentityLte.class.isInstance(this.hidl_o)) {
            throw new java.lang.Error("Union is in a corrupted state.");
        }
        return (android.hardware.radio.V1_5.CellIdentityLte) this.hidl_o;
    }

    public void nr(android.hardware.radio.V1_5.CellIdentityNr cellIdentityNr) {
        this.hidl_d = (byte) 6;
        this.hidl_o = cellIdentityNr;
    }

    public android.hardware.radio.V1_5.CellIdentityNr nr() {
        if (this.hidl_d != 6) {
            throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_5.CellIdentity.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.hidl_o != null && !android.hardware.radio.V1_5.CellIdentityNr.class.isInstance(this.hidl_o)) {
            throw new java.lang.Error("Union is in a corrupted state.");
        }
        return (android.hardware.radio.V1_5.CellIdentityNr) this.hidl_o;
    }

    public byte getDiscriminator() {
        return this.hidl_d;
    }

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.CellIdentity.class) {
            return false;
        }
        android.hardware.radio.V1_5.CellIdentity cellIdentity = (android.hardware.radio.V1_5.CellIdentity) obj;
        if (this.hidl_d == cellIdentity.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, cellIdentity.hidl_o)) {
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
                sb.append(".noinit = ");
                sb.append(noinit());
                break;
            case 1:
                sb.append(".gsm = ");
                sb.append(gsm());
                break;
            case 2:
                sb.append(".wcdma = ");
                sb.append(wcdma());
                break;
            case 3:
                sb.append(".tdscdma = ");
                sb.append(tdscdma());
                break;
            case 4:
                sb.append(".cdma = ");
                sb.append(cdma());
                break;
            case 5:
                sb.append(".lte = ");
                sb.append(lte());
                break;
            case 6:
                sb.append(".nr = ");
                sb.append(nr());
                break;
            default:
                throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
        sb.append("}");
        return sb.toString();
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(168L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.CellIdentity> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.CellIdentity> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 168, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.CellIdentity cellIdentity = new android.hardware.radio.V1_5.CellIdentity();
            cellIdentity.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 168);
            arrayList.add(cellIdentity);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.hidl_d = hwBlob.getInt8(0 + j);
        switch (this.hidl_d) {
            case 0:
                this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
                ((android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            case 1:
                this.hidl_o = new android.hardware.radio.V1_5.CellIdentityGsm();
                ((android.hardware.radio.V1_5.CellIdentityGsm) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            case 2:
                this.hidl_o = new android.hardware.radio.V1_5.CellIdentityWcdma();
                ((android.hardware.radio.V1_5.CellIdentityWcdma) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            case 3:
                this.hidl_o = new android.hardware.radio.V1_5.CellIdentityTdscdma();
                ((android.hardware.radio.V1_5.CellIdentityTdscdma) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            case 4:
                this.hidl_o = new android.hardware.radio.V1_2.CellIdentityCdma();
                ((android.hardware.radio.V1_2.CellIdentityCdma) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            case 5:
                this.hidl_o = new android.hardware.radio.V1_5.CellIdentityLte();
                ((android.hardware.radio.V1_5.CellIdentityLte) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            case 6:
                this.hidl_o = new android.hardware.radio.V1_5.CellIdentityNr();
                ((android.hardware.radio.V1_5.CellIdentityNr) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            default:
                throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(168);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.CellIdentity> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 168);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 168);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt8(0 + j, this.hidl_d);
        switch (this.hidl_d) {
            case 0:
                noinit().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            case 1:
                gsm().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            case 2:
                wcdma().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            case 3:
                tdscdma().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            case 4:
                cdma().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            case 5:
                lte().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            case 6:
                nr().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            default:
                throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }
}
