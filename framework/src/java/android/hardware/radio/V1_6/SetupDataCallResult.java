package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class SetupDataCallResult {
    public int cause = 0;
    public long suggestedRetryTime = 0;
    public int cid = 0;
    public int active = 0;
    public int type = 0;
    public java.lang.String ifname = new java.lang.String();
    public java.util.ArrayList<android.hardware.radio.V1_5.LinkAddress> addresses = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.String> dnses = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.String> gateways = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.String> pcscf = new java.util.ArrayList<>();
    public int mtuV4 = 0;
    public int mtuV6 = 0;
    public android.hardware.radio.V1_6.Qos defaultQos = new android.hardware.radio.V1_6.Qos();
    public java.util.ArrayList<android.hardware.radio.V1_6.QosSession> qosSessions = new java.util.ArrayList<>();
    public byte handoverFailureMode = 0;
    public int pduSessionId = 0;
    public android.hardware.radio.V1_6.OptionalSliceInfo sliceInfo = new android.hardware.radio.V1_6.OptionalSliceInfo();
    public java.util.ArrayList<android.hardware.radio.V1_6.TrafficDescriptor> trafficDescriptors = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.SetupDataCallResult.class) {
            return false;
        }
        android.hardware.radio.V1_6.SetupDataCallResult setupDataCallResult = (android.hardware.radio.V1_6.SetupDataCallResult) obj;
        if (this.cause == setupDataCallResult.cause && this.suggestedRetryTime == setupDataCallResult.suggestedRetryTime && this.cid == setupDataCallResult.cid && this.active == setupDataCallResult.active && this.type == setupDataCallResult.type && android.os.HidlSupport.deepEquals(this.ifname, setupDataCallResult.ifname) && android.os.HidlSupport.deepEquals(this.addresses, setupDataCallResult.addresses) && android.os.HidlSupport.deepEquals(this.dnses, setupDataCallResult.dnses) && android.os.HidlSupport.deepEquals(this.gateways, setupDataCallResult.gateways) && android.os.HidlSupport.deepEquals(this.pcscf, setupDataCallResult.pcscf) && this.mtuV4 == setupDataCallResult.mtuV4 && this.mtuV6 == setupDataCallResult.mtuV6 && android.os.HidlSupport.deepEquals(this.defaultQos, setupDataCallResult.defaultQos) && android.os.HidlSupport.deepEquals(this.qosSessions, setupDataCallResult.qosSessions) && this.handoverFailureMode == setupDataCallResult.handoverFailureMode && this.pduSessionId == setupDataCallResult.pduSessionId && android.os.HidlSupport.deepEquals(this.sliceInfo, setupDataCallResult.sliceInfo) && android.os.HidlSupport.deepEquals(this.trafficDescriptors, setupDataCallResult.trafficDescriptors)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cause))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.suggestedRetryTime))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.active))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.ifname)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.addresses)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.dnses)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.gateways)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.pcscf)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.mtuV4))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.mtuV6))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.defaultQos)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.qosSessions)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.handoverFailureMode))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.pduSessionId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.sliceInfo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.trafficDescriptors)));
    }

    public final java.lang.String toString() {
        return "{.cause = " + android.hardware.radio.V1_6.DataCallFailCause.toString(this.cause) + ", .suggestedRetryTime = " + this.suggestedRetryTime + ", .cid = " + this.cid + ", .active = " + android.hardware.radio.V1_4.DataConnActiveStatus.toString(this.active) + ", .type = " + android.hardware.radio.V1_4.PdpProtocolType.toString(this.type) + ", .ifname = " + this.ifname + ", .addresses = " + this.addresses + ", .dnses = " + this.dnses + ", .gateways = " + this.gateways + ", .pcscf = " + this.pcscf + ", .mtuV4 = " + this.mtuV4 + ", .mtuV6 = " + this.mtuV6 + ", .defaultQos = " + this.defaultQos + ", .qosSessions = " + this.qosSessions + ", .handoverFailureMode = " + android.hardware.radio.V1_6.HandoverFailureMode.toString(this.handoverFailureMode) + ", .pduSessionId = " + this.pduSessionId + ", .sliceInfo = " + this.sliceInfo + ", .trafficDescriptors = " + this.trafficDescriptors + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(216L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.SetupDataCallResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.SetupDataCallResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 216, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.SetupDataCallResult setupDataCallResult = new android.hardware.radio.V1_6.SetupDataCallResult();
            setupDataCallResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 216);
            arrayList.add(setupDataCallResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cause = hwBlob.getInt32(j + 0);
        this.suggestedRetryTime = hwBlob.getInt64(j + 8);
        this.cid = hwBlob.getInt32(j + 16);
        this.active = hwBlob.getInt32(j + 20);
        this.type = hwBlob.getInt32(j + 24);
        long j2 = j + 32;
        this.ifname = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.ifname.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 48;
        int int32 = hwBlob.getInt32(j3 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, hwBlob.handle(), j3 + 0, true);
        this.addresses.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.LinkAddress linkAddress = new android.hardware.radio.V1_5.LinkAddress();
            linkAddress.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            this.addresses.add(linkAddress);
        }
        long j4 = j + 64;
        int int322 = hwBlob.getInt32(j4 + 8);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j4 + 0, true);
        this.dnses.clear();
        int i2 = 0;
        while (i2 < int322) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer2.getString(i2 * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer2.handle(), r1 + 0, false);
            this.dnses.add(string);
            i2++;
            readEmbeddedBuffer2 = readEmbeddedBuffer2;
        }
        long j5 = j + 80;
        int int323 = hwBlob.getInt32(j5 + 8);
        android.os.HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 16, hwBlob.handle(), j5 + 0, true);
        this.gateways.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            new java.lang.String();
            java.lang.String string2 = readEmbeddedBuffer3.getString(i3 * 16);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, readEmbeddedBuffer3.handle(), r1 + 0, false);
            this.gateways.add(string2);
        }
        long j6 = j + 96;
        int int324 = hwBlob.getInt32(j6 + 8);
        android.os.HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 16, hwBlob.handle(), j6 + 0, true);
        this.pcscf.clear();
        for (int i4 = 0; i4 < int324; i4++) {
            new java.lang.String();
            java.lang.String string3 = readEmbeddedBuffer4.getString(i4 * 16);
            hwParcel.readEmbeddedBuffer(string3.getBytes().length + 1, readEmbeddedBuffer4.handle(), r1 + 0, false);
            this.pcscf.add(string3);
        }
        this.mtuV4 = hwBlob.getInt32(j + 112);
        this.mtuV6 = hwBlob.getInt32(j + 116);
        this.defaultQos.readEmbeddedFromParcel(hwParcel, hwBlob, j + 120);
        long j7 = j + 152;
        int int325 = hwBlob.getInt32(j7 + 8);
        android.os.HwBlob readEmbeddedBuffer5 = hwParcel.readEmbeddedBuffer(int325 * 48, hwBlob.handle(), j7 + 0, true);
        this.qosSessions.clear();
        for (int i5 = 0; i5 < int325; i5++) {
            android.hardware.radio.V1_6.QosSession qosSession = new android.hardware.radio.V1_6.QosSession();
            qosSession.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer5, i5 * 48);
            this.qosSessions.add(qosSession);
        }
        this.handoverFailureMode = hwBlob.getInt8(j + 168);
        this.pduSessionId = hwBlob.getInt32(j + 172);
        this.sliceInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 176);
        long j8 = j + 200;
        int int326 = hwBlob.getInt32(j8 + 8);
        android.os.HwBlob readEmbeddedBuffer6 = hwParcel.readEmbeddedBuffer(int326 * 48, hwBlob.handle(), 0 + j8, true);
        this.trafficDescriptors.clear();
        for (int i6 = 0; i6 < int326; i6++) {
            android.hardware.radio.V1_6.TrafficDescriptor trafficDescriptor = new android.hardware.radio.V1_6.TrafficDescriptor();
            trafficDescriptor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer6, i6 * 48);
            this.trafficDescriptors.add(trafficDescriptor);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(216);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.SetupDataCallResult> arrayList) {
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
        hwBlob.putInt32(j + 0, this.cause);
        hwBlob.putInt64(j + 8, this.suggestedRetryTime);
        hwBlob.putInt32(j + 16, this.cid);
        hwBlob.putInt32(j + 20, this.active);
        hwBlob.putInt32(j + 24, this.type);
        hwBlob.putString(j + 32, this.ifname);
        int size = this.addresses.size();
        long j2 = j + 48;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            this.addresses.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.dnses.size();
        long j3 = j + 64;
        hwBlob.putInt32(j3 + 8, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.dnses.get(i2));
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        int size3 = this.gateways.size();
        long j4 = j + 80;
        hwBlob.putInt32(j4 + 8, size3);
        hwBlob.putBool(j4 + 12, false);
        android.os.HwBlob hwBlob4 = new android.os.HwBlob(size3 * 16);
        for (int i3 = 0; i3 < size3; i3++) {
            hwBlob4.putString(i3 * 16, this.gateways.get(i3));
        }
        hwBlob.putBlob(j4 + 0, hwBlob4);
        int size4 = this.pcscf.size();
        long j5 = j + 96;
        hwBlob.putInt32(j5 + 8, size4);
        hwBlob.putBool(j5 + 12, false);
        android.os.HwBlob hwBlob5 = new android.os.HwBlob(size4 * 16);
        for (int i4 = 0; i4 < size4; i4++) {
            hwBlob5.putString(i4 * 16, this.pcscf.get(i4));
        }
        hwBlob.putBlob(j5 + 0, hwBlob5);
        hwBlob.putInt32(j + 112, this.mtuV4);
        hwBlob.putInt32(j + 116, this.mtuV6);
        this.defaultQos.writeEmbeddedToBlob(hwBlob, j + 120);
        int size5 = this.qosSessions.size();
        long j6 = j + 152;
        hwBlob.putInt32(j6 + 8, size5);
        hwBlob.putBool(j6 + 12, false);
        android.os.HwBlob hwBlob6 = new android.os.HwBlob(size5 * 48);
        for (int i5 = 0; i5 < size5; i5++) {
            this.qosSessions.get(i5).writeEmbeddedToBlob(hwBlob6, i5 * 48);
        }
        hwBlob.putBlob(j6 + 0, hwBlob6);
        hwBlob.putInt8(j + 168, this.handoverFailureMode);
        hwBlob.putInt32(j + 172, this.pduSessionId);
        this.sliceInfo.writeEmbeddedToBlob(hwBlob, j + 176);
        int size6 = this.trafficDescriptors.size();
        long j7 = j + 200;
        hwBlob.putInt32(8 + j7, size6);
        hwBlob.putBool(j7 + 12, false);
        android.os.HwBlob hwBlob7 = new android.os.HwBlob(size6 * 48);
        for (int i6 = 0; i6 < size6; i6++) {
            this.trafficDescriptors.get(i6).writeEmbeddedToBlob(hwBlob7, i6 * 48);
        }
        hwBlob.putBlob(j7 + 0, hwBlob7);
    }
}
