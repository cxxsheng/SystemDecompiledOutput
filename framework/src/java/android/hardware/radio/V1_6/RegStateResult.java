package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class RegStateResult {
    public int regState = 0;
    public int rat = 0;
    public int reasonForDenial = 0;
    public android.hardware.radio.V1_5.CellIdentity cellIdentity = new android.hardware.radio.V1_5.CellIdentity();
    public java.lang.String registeredPlmn = new java.lang.String();
    public android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo accessTechnologySpecificInfo = new android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo();

    public static final class AccessTechnologySpecificInfo {
        private byte hidl_d = 0;
        private java.lang.Object hidl_o;

        public AccessTechnologySpecificInfo() {
            this.hidl_o = null;
            this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte cdmaInfo = 1;
            public static final byte eutranInfo = 2;
            public static final byte geranDtmSupported = 4;
            public static final byte ngranNrVopsInfo = 3;
            public static final byte noinit = 0;

            public static final java.lang.String getName(byte b) {
                switch (b) {
                    case 0:
                        return "noinit";
                    case 1:
                        return "cdmaInfo";
                    case 2:
                        return "eutranInfo";
                    case 3:
                        return "ngranNrVopsInfo";
                    case 4:
                        return "geranDtmSupported";
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
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.internal.hidl.safe_union.V1_0.Monostate.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o;
        }

        public void cdmaInfo(android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo cdma2000RegistrationInfo) {
            this.hidl_d = (byte) 1;
            this.hidl_o = cdma2000RegistrationInfo;
        }

        public android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo cdmaInfo() {
            if (this.hidl_d != 1) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo) this.hidl_o;
        }

        public void eutranInfo(android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo eutranRegistrationInfo) {
            this.hidl_d = (byte) 2;
            this.hidl_o = eutranRegistrationInfo;
        }

        public android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo eutranInfo() {
            if (this.hidl_d != 2) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo) this.hidl_o;
        }

        public void ngranNrVopsInfo(android.hardware.radio.V1_6.NrVopsInfo nrVopsInfo) {
            this.hidl_d = (byte) 3;
            this.hidl_o = nrVopsInfo;
        }

        public android.hardware.radio.V1_6.NrVopsInfo ngranNrVopsInfo() {
            if (this.hidl_d != 3) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.hardware.radio.V1_6.NrVopsInfo.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.hardware.radio.V1_6.NrVopsInfo) this.hidl_o;
        }

        public void geranDtmSupported(boolean z) {
            this.hidl_d = (byte) 4;
            this.hidl_o = java.lang.Boolean.valueOf(z);
        }

        public boolean geranDtmSupported() {
            if (this.hidl_d != 4) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.lang.Boolean.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return ((java.lang.Boolean) this.hidl_o).booleanValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo.class) {
                return false;
            }
            android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo accessTechnologySpecificInfo = (android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo) obj;
            if (this.hidl_d == accessTechnologySpecificInfo.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, accessTechnologySpecificInfo.hidl_o)) {
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
                    sb.append(".cdmaInfo = ");
                    sb.append(cdmaInfo());
                    break;
                case 2:
                    sb.append(".eutranInfo = ");
                    sb.append(eutranInfo());
                    break;
                case 3:
                    sb.append(".ngranNrVopsInfo = ");
                    sb.append(ngranNrVopsInfo());
                    break;
                case 4:
                    sb.append(".geranDtmSupported = ");
                    sb.append(geranDtmSupported());
                    break;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo accessTechnologySpecificInfo = new android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo();
                accessTechnologySpecificInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
                arrayList.add(accessTechnologySpecificInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.hidl_d = hwBlob.getInt8(0 + j);
            switch (this.hidl_d) {
                case 0:
                    this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
                    ((android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
                    return;
                case 1:
                    this.hidl_o = new android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo();
                    ((android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
                    return;
                case 2:
                    this.hidl_o = new android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo();
                    ((android.hardware.radio.V1_5.RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
                    return;
                case 3:
                    this.hidl_o = new android.hardware.radio.V1_6.NrVopsInfo();
                    ((android.hardware.radio.V1_6.NrVopsInfo) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
                    return;
                case 4:
                    this.hidl_o = false;
                    this.hidl_o = java.lang.Boolean.valueOf(hwBlob.getBool(j + 4));
                    return;
                default:
                    throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(20);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.RegStateResult.AccessTechnologySpecificInfo> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt8(0 + j, this.hidl_d);
            switch (this.hidl_d) {
                case 0:
                    noinit().writeEmbeddedToBlob(hwBlob, j + 4);
                    return;
                case 1:
                    cdmaInfo().writeEmbeddedToBlob(hwBlob, j + 4);
                    return;
                case 2:
                    eutranInfo().writeEmbeddedToBlob(hwBlob, j + 4);
                    return;
                case 3:
                    ngranNrVopsInfo().writeEmbeddedToBlob(hwBlob, j + 4);
                    return;
                case 4:
                    hwBlob.putBool(j + 4, geranDtmSupported());
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
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.RegStateResult.class) {
            return false;
        }
        android.hardware.radio.V1_6.RegStateResult regStateResult = (android.hardware.radio.V1_6.RegStateResult) obj;
        if (this.regState == regStateResult.regState && this.rat == regStateResult.rat && this.reasonForDenial == regStateResult.reasonForDenial && android.os.HidlSupport.deepEquals(this.cellIdentity, regStateResult.cellIdentity) && android.os.HidlSupport.deepEquals(this.registeredPlmn, regStateResult.registeredPlmn) && android.os.HidlSupport.deepEquals(this.accessTechnologySpecificInfo, regStateResult.accessTechnologySpecificInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.regState))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rat))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.reasonForDenial))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentity)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.registeredPlmn)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.accessTechnologySpecificInfo)));
    }

    public final java.lang.String toString() {
        return "{.regState = " + android.hardware.radio.V1_0.RegState.toString(this.regState) + ", .rat = " + android.hardware.radio.V1_4.RadioTechnology.toString(this.rat) + ", .reasonForDenial = " + android.hardware.radio.V1_5.RegistrationFailCause.toString(this.reasonForDenial) + ", .cellIdentity = " + this.cellIdentity + ", .registeredPlmn = " + this.registeredPlmn + ", .accessTechnologySpecificInfo = " + this.accessTechnologySpecificInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(224L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.RegStateResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.RegStateResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 224, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.RegStateResult regStateResult = new android.hardware.radio.V1_6.RegStateResult();
            regStateResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 224);
            arrayList.add(regStateResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.regState = hwBlob.getInt32(j + 0);
        this.rat = hwBlob.getInt32(j + 4);
        this.reasonForDenial = hwBlob.getInt32(j + 8);
        this.cellIdentity.readEmbeddedFromParcel(hwParcel, hwBlob, j + 16);
        long j2 = j + 184;
        this.registeredPlmn = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.registeredPlmn.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.accessTechnologySpecificInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 200);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(224);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.RegStateResult> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 224);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 224);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.regState);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putInt32(8 + j, this.reasonForDenial);
        this.cellIdentity.writeEmbeddedToBlob(hwBlob, 16 + j);
        hwBlob.putString(184 + j, this.registeredPlmn);
        this.accessTechnologySpecificInfo.writeEmbeddedToBlob(hwBlob, j + 200);
    }
}
