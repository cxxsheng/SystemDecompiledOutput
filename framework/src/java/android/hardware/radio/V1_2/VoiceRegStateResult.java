package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class VoiceRegStateResult {
    public int regState = 0;
    public int rat = 0;
    public boolean cssSupported = false;
    public int roamingIndicator = 0;
    public int systemIsInPrl = 0;
    public int defaultRoamingIndicator = 0;
    public int reasonForDenial = 0;
    public android.hardware.radio.V1_2.CellIdentity cellIdentity = new android.hardware.radio.V1_2.CellIdentity();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.VoiceRegStateResult.class) {
            return false;
        }
        android.hardware.radio.V1_2.VoiceRegStateResult voiceRegStateResult = (android.hardware.radio.V1_2.VoiceRegStateResult) obj;
        if (this.regState == voiceRegStateResult.regState && this.rat == voiceRegStateResult.rat && this.cssSupported == voiceRegStateResult.cssSupported && this.roamingIndicator == voiceRegStateResult.roamingIndicator && this.systemIsInPrl == voiceRegStateResult.systemIsInPrl && this.defaultRoamingIndicator == voiceRegStateResult.defaultRoamingIndicator && this.reasonForDenial == voiceRegStateResult.reasonForDenial && android.os.HidlSupport.deepEquals(this.cellIdentity, voiceRegStateResult.cellIdentity)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.regState))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rat))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.cssSupported))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.roamingIndicator))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.systemIsInPrl))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.defaultRoamingIndicator))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.reasonForDenial))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentity)));
    }

    public final java.lang.String toString() {
        return "{.regState = " + android.hardware.radio.V1_0.RegState.toString(this.regState) + ", .rat = " + this.rat + ", .cssSupported = " + this.cssSupported + ", .roamingIndicator = " + this.roamingIndicator + ", .systemIsInPrl = " + this.systemIsInPrl + ", .defaultRoamingIndicator = " + this.defaultRoamingIndicator + ", .reasonForDenial = " + this.reasonForDenial + ", .cellIdentity = " + this.cellIdentity + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.VoiceRegStateResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.VoiceRegStateResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.VoiceRegStateResult voiceRegStateResult = new android.hardware.radio.V1_2.VoiceRegStateResult();
            voiceRegStateResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            arrayList.add(voiceRegStateResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.regState = hwBlob.getInt32(0 + j);
        this.rat = hwBlob.getInt32(4 + j);
        this.cssSupported = hwBlob.getBool(8 + j);
        this.roamingIndicator = hwBlob.getInt32(12 + j);
        this.systemIsInPrl = hwBlob.getInt32(16 + j);
        this.defaultRoamingIndicator = hwBlob.getInt32(20 + j);
        this.reasonForDenial = hwBlob.getInt32(24 + j);
        this.cellIdentity.readEmbeddedFromParcel(hwParcel, hwBlob, j + 32);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.VoiceRegStateResult> arrayList) {
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
        hwBlob.putInt32(0 + j, this.regState);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putBool(8 + j, this.cssSupported);
        hwBlob.putInt32(12 + j, this.roamingIndicator);
        hwBlob.putInt32(16 + j, this.systemIsInPrl);
        hwBlob.putInt32(20 + j, this.defaultRoamingIndicator);
        hwBlob.putInt32(24 + j, this.reasonForDenial);
        this.cellIdentity.writeEmbeddedToBlob(hwBlob, j + 32);
    }
}
