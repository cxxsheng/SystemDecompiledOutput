package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SetupDataCallResult {
    public int status = 0;
    public int suggestedRetryTime = 0;
    public int cid = 0;
    public int active = 0;
    public java.lang.String type = new java.lang.String();
    public java.lang.String ifname = new java.lang.String();
    public java.lang.String addresses = new java.lang.String();
    public java.lang.String dnses = new java.lang.String();
    public java.lang.String gateways = new java.lang.String();
    public java.lang.String pcscf = new java.lang.String();
    public int mtu = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.SetupDataCallResult.class) {
            return false;
        }
        android.hardware.radio.V1_0.SetupDataCallResult setupDataCallResult = (android.hardware.radio.V1_0.SetupDataCallResult) obj;
        if (this.status == setupDataCallResult.status && this.suggestedRetryTime == setupDataCallResult.suggestedRetryTime && this.cid == setupDataCallResult.cid && this.active == setupDataCallResult.active && android.os.HidlSupport.deepEquals(this.type, setupDataCallResult.type) && android.os.HidlSupport.deepEquals(this.ifname, setupDataCallResult.ifname) && android.os.HidlSupport.deepEquals(this.addresses, setupDataCallResult.addresses) && android.os.HidlSupport.deepEquals(this.dnses, setupDataCallResult.dnses) && android.os.HidlSupport.deepEquals(this.gateways, setupDataCallResult.gateways) && android.os.HidlSupport.deepEquals(this.pcscf, setupDataCallResult.pcscf) && this.mtu == setupDataCallResult.mtu) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.status))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.suggestedRetryTime))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.active))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.type)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.ifname)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.addresses)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.dnses)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.gateways)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.pcscf)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.mtu))));
    }

    public final java.lang.String toString() {
        return "{.status = " + android.hardware.radio.V1_0.DataCallFailCause.toString(this.status) + ", .suggestedRetryTime = " + this.suggestedRetryTime + ", .cid = " + this.cid + ", .active = " + this.active + ", .type = " + this.type + ", .ifname = " + this.ifname + ", .addresses = " + this.addresses + ", .dnses = " + this.dnses + ", .gateways = " + this.gateways + ", .pcscf = " + this.pcscf + ", .mtu = " + this.mtu + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.SetupDataCallResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.SetupDataCallResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SetupDataCallResult setupDataCallResult = new android.hardware.radio.V1_0.SetupDataCallResult();
            setupDataCallResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            arrayList.add(setupDataCallResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j + 0);
        this.suggestedRetryTime = hwBlob.getInt32(j + 4);
        this.cid = hwBlob.getInt32(j + 8);
        this.active = hwBlob.getInt32(j + 12);
        long j2 = j + 16;
        this.type = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.type.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 32;
        this.ifname = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.ifname.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 48;
        this.addresses = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.addresses.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        long j5 = j + 64;
        this.dnses = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(this.dnses.getBytes().length + 1, hwBlob.handle(), j5 + 0, false);
        long j6 = j + 80;
        this.gateways = hwBlob.getString(j6);
        hwParcel.readEmbeddedBuffer(this.gateways.getBytes().length + 1, hwBlob.handle(), j6 + 0, false);
        long j7 = j + 96;
        this.pcscf = hwBlob.getString(j7);
        hwParcel.readEmbeddedBuffer(this.pcscf.getBytes().length + 1, hwBlob.handle(), j7 + 0, false);
        this.mtu = hwBlob.getInt32(j + 112);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.SetupDataCallResult> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.status);
        hwBlob.putInt32(4 + j, this.suggestedRetryTime);
        hwBlob.putInt32(8 + j, this.cid);
        hwBlob.putInt32(12 + j, this.active);
        hwBlob.putString(16 + j, this.type);
        hwBlob.putString(32 + j, this.ifname);
        hwBlob.putString(48 + j, this.addresses);
        hwBlob.putString(64 + j, this.dnses);
        hwBlob.putString(80 + j, this.gateways);
        hwBlob.putString(96 + j, this.pcscf);
        hwBlob.putInt32(j + 112, this.mtu);
    }
}
