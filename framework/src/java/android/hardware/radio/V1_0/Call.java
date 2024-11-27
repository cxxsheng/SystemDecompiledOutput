package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class Call {
    public int state = 0;
    public int index = 0;
    public int toa = 0;
    public boolean isMpty = false;
    public boolean isMT = false;
    public byte als = 0;
    public boolean isVoice = false;
    public boolean isVoicePrivacy = false;
    public java.lang.String number = new java.lang.String();
    public int numberPresentation = 0;
    public java.lang.String name = new java.lang.String();
    public int namePresentation = 0;
    public java.util.ArrayList<android.hardware.radio.V1_0.UusInfo> uusInfo = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.Call.class) {
            return false;
        }
        android.hardware.radio.V1_0.Call call = (android.hardware.radio.V1_0.Call) obj;
        if (this.state == call.state && this.index == call.index && this.toa == call.toa && this.isMpty == call.isMpty && this.isMT == call.isMT && this.als == call.als && this.isVoice == call.isVoice && this.isVoicePrivacy == call.isVoicePrivacy && android.os.HidlSupport.deepEquals(this.number, call.number) && this.numberPresentation == call.numberPresentation && android.os.HidlSupport.deepEquals(this.name, call.name) && this.namePresentation == call.namePresentation && android.os.HidlSupport.deepEquals(this.uusInfo, call.uusInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.state))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.index))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.toa))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isMpty))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isMT))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.als))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isVoice))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isVoicePrivacy))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.number)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.numberPresentation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.namePresentation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.uusInfo)));
    }

    public final java.lang.String toString() {
        return "{.state = " + android.hardware.radio.V1_0.CallState.toString(this.state) + ", .index = " + this.index + ", .toa = " + this.toa + ", .isMpty = " + this.isMpty + ", .isMT = " + this.isMT + ", .als = " + ((int) this.als) + ", .isVoice = " + this.isVoice + ", .isVoicePrivacy = " + this.isVoicePrivacy + ", .number = " + this.number + ", .numberPresentation = " + android.hardware.radio.V1_0.CallPresentation.toString(this.numberPresentation) + ", .name = " + this.name + ", .namePresentation = " + android.hardware.radio.V1_0.CallPresentation.toString(this.namePresentation) + ", .uusInfo = " + this.uusInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.Call> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.Call> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.Call call = new android.hardware.radio.V1_0.Call();
            call.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(call);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.state = hwBlob.getInt32(j + 0);
        this.index = hwBlob.getInt32(j + 4);
        this.toa = hwBlob.getInt32(j + 8);
        this.isMpty = hwBlob.getBool(j + 12);
        this.isMT = hwBlob.getBool(j + 13);
        this.als = hwBlob.getInt8(j + 14);
        this.isVoice = hwBlob.getBool(j + 15);
        this.isVoicePrivacy = hwBlob.getBool(j + 16);
        long j2 = j + 24;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.numberPresentation = hwBlob.getInt32(j + 40);
        long j3 = j + 48;
        this.name = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        this.namePresentation = hwBlob.getInt32(j + 64);
        long j4 = j + 72;
        int int32 = hwBlob.getInt32(8 + j4);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, hwBlob.handle(), j4 + 0, true);
        this.uusInfo.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.UusInfo uusInfo = new android.hardware.radio.V1_0.UusInfo();
            uusInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            this.uusInfo.add(uusInfo);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.Call> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.state);
        hwBlob.putInt32(4 + j, this.index);
        hwBlob.putInt32(j + 8, this.toa);
        hwBlob.putBool(j + 12, this.isMpty);
        hwBlob.putBool(13 + j, this.isMT);
        hwBlob.putInt8(14 + j, this.als);
        hwBlob.putBool(15 + j, this.isVoice);
        hwBlob.putBool(16 + j, this.isVoicePrivacy);
        hwBlob.putString(24 + j, this.number);
        hwBlob.putInt32(40 + j, this.numberPresentation);
        hwBlob.putString(48 + j, this.name);
        hwBlob.putInt32(64 + j, this.namePresentation);
        int size = this.uusInfo.size();
        long j2 = j + 72;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            this.uusInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
