package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class DataProfileInfo {
    public int bearerBitmap;
    public int supportedApnTypesBitmap;
    public int profileId = 0;
    public java.lang.String apn = new java.lang.String();
    public int protocol = 0;
    public int roamingProtocol = 0;
    public int authType = 0;
    public java.lang.String user = new java.lang.String();
    public java.lang.String password = new java.lang.String();
    public int type = 0;
    public int maxConnsTime = 0;
    public int maxConns = 0;
    public int waitTime = 0;
    public boolean enabled = false;
    public int mtuV4 = 0;
    public int mtuV6 = 0;
    public boolean preferred = false;
    public boolean persistent = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.DataProfileInfo.class) {
            return false;
        }
        android.hardware.radio.V1_5.DataProfileInfo dataProfileInfo = (android.hardware.radio.V1_5.DataProfileInfo) obj;
        if (this.profileId == dataProfileInfo.profileId && android.os.HidlSupport.deepEquals(this.apn, dataProfileInfo.apn) && this.protocol == dataProfileInfo.protocol && this.roamingProtocol == dataProfileInfo.roamingProtocol && this.authType == dataProfileInfo.authType && android.os.HidlSupport.deepEquals(this.user, dataProfileInfo.user) && android.os.HidlSupport.deepEquals(this.password, dataProfileInfo.password) && this.type == dataProfileInfo.type && this.maxConnsTime == dataProfileInfo.maxConnsTime && this.maxConns == dataProfileInfo.maxConns && this.waitTime == dataProfileInfo.waitTime && this.enabled == dataProfileInfo.enabled && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.supportedApnTypesBitmap), java.lang.Integer.valueOf(dataProfileInfo.supportedApnTypesBitmap)) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.bearerBitmap), java.lang.Integer.valueOf(dataProfileInfo.bearerBitmap)) && this.mtuV4 == dataProfileInfo.mtuV4 && this.mtuV6 == dataProfileInfo.mtuV6 && this.preferred == dataProfileInfo.preferred && this.persistent == dataProfileInfo.persistent) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.profileId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.apn)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.protocol))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.roamingProtocol))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.authType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.user)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.password)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxConnsTime))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxConns))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.waitTime))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.enabled))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.supportedApnTypesBitmap))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.bearerBitmap))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.mtuV4))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.mtuV6))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.preferred))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.persistent))));
    }

    public final java.lang.String toString() {
        return "{.profileId = " + android.hardware.radio.V1_0.DataProfileId.toString(this.profileId) + ", .apn = " + this.apn + ", .protocol = " + android.hardware.radio.V1_4.PdpProtocolType.toString(this.protocol) + ", .roamingProtocol = " + android.hardware.radio.V1_4.PdpProtocolType.toString(this.roamingProtocol) + ", .authType = " + android.hardware.radio.V1_0.ApnAuthType.toString(this.authType) + ", .user = " + this.user + ", .password = " + this.password + ", .type = " + android.hardware.radio.V1_0.DataProfileInfoType.toString(this.type) + ", .maxConnsTime = " + this.maxConnsTime + ", .maxConns = " + this.maxConns + ", .waitTime = " + this.waitTime + ", .enabled = " + this.enabled + ", .supportedApnTypesBitmap = " + android.hardware.radio.V1_5.ApnTypes.dumpBitfield(this.supportedApnTypesBitmap) + ", .bearerBitmap = " + android.hardware.radio.V1_4.RadioAccessFamily.dumpBitfield(this.bearerBitmap) + ", .mtuV4 = " + this.mtuV4 + ", .mtuV6 = " + this.mtuV6 + ", .preferred = " + this.preferred + ", .persistent = " + this.persistent + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.DataProfileInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.DataProfileInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.DataProfileInfo dataProfileInfo = new android.hardware.radio.V1_5.DataProfileInfo();
            dataProfileInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            arrayList.add(dataProfileInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.profileId = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.apn = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.apn.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.protocol = hwBlob.getInt32(j + 24);
        this.roamingProtocol = hwBlob.getInt32(j + 28);
        this.authType = hwBlob.getInt32(j + 32);
        long j3 = j + 40;
        this.user = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.user.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 56;
        this.password = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.password.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        this.type = hwBlob.getInt32(j + 72);
        this.maxConnsTime = hwBlob.getInt32(j + 76);
        this.maxConns = hwBlob.getInt32(j + 80);
        this.waitTime = hwBlob.getInt32(j + 84);
        this.enabled = hwBlob.getBool(j + 88);
        this.supportedApnTypesBitmap = hwBlob.getInt32(j + 92);
        this.bearerBitmap = hwBlob.getInt32(j + 96);
        this.mtuV4 = hwBlob.getInt32(j + 100);
        this.mtuV6 = hwBlob.getInt32(j + 104);
        this.preferred = hwBlob.getBool(j + 108);
        this.persistent = hwBlob.getBool(j + 109);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.DataProfileInfo> arrayList) {
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
        hwBlob.putInt32(0 + j, this.profileId);
        hwBlob.putString(8 + j, this.apn);
        hwBlob.putInt32(24 + j, this.protocol);
        hwBlob.putInt32(28 + j, this.roamingProtocol);
        hwBlob.putInt32(32 + j, this.authType);
        hwBlob.putString(40 + j, this.user);
        hwBlob.putString(56 + j, this.password);
        hwBlob.putInt32(72 + j, this.type);
        hwBlob.putInt32(76 + j, this.maxConnsTime);
        hwBlob.putInt32(80 + j, this.maxConns);
        hwBlob.putInt32(84 + j, this.waitTime);
        hwBlob.putBool(88 + j, this.enabled);
        hwBlob.putInt32(92 + j, this.supportedApnTypesBitmap);
        hwBlob.putInt32(96 + j, this.bearerBitmap);
        hwBlob.putInt32(100 + j, this.mtuV4);
        hwBlob.putInt32(104 + j, this.mtuV6);
        hwBlob.putBool(108 + j, this.preferred);
        hwBlob.putBool(j + 109, this.persistent);
    }
}
