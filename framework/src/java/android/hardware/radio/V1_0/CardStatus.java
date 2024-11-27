package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CardStatus {
    public int cardState = 0;
    public int universalPinState = 0;
    public int gsmUmtsSubscriptionAppIndex = 0;
    public int cdmaSubscriptionAppIndex = 0;
    public int imsSubscriptionAppIndex = 0;
    public java.util.ArrayList<android.hardware.radio.V1_0.AppStatus> applications = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CardStatus.class) {
            return false;
        }
        android.hardware.radio.V1_0.CardStatus cardStatus = (android.hardware.radio.V1_0.CardStatus) obj;
        if (this.cardState == cardStatus.cardState && this.universalPinState == cardStatus.universalPinState && this.gsmUmtsSubscriptionAppIndex == cardStatus.gsmUmtsSubscriptionAppIndex && this.cdmaSubscriptionAppIndex == cardStatus.cdmaSubscriptionAppIndex && this.imsSubscriptionAppIndex == cardStatus.imsSubscriptionAppIndex && android.os.HidlSupport.deepEquals(this.applications, cardStatus.applications)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cardState))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.universalPinState))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.gsmUmtsSubscriptionAppIndex))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cdmaSubscriptionAppIndex))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.imsSubscriptionAppIndex))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.applications)));
    }

    public final java.lang.String toString() {
        return "{.cardState = " + android.hardware.radio.V1_0.CardState.toString(this.cardState) + ", .universalPinState = " + android.hardware.radio.V1_0.PinState.toString(this.universalPinState) + ", .gsmUmtsSubscriptionAppIndex = " + this.gsmUmtsSubscriptionAppIndex + ", .cdmaSubscriptionAppIndex = " + this.cdmaSubscriptionAppIndex + ", .imsSubscriptionAppIndex = " + this.imsSubscriptionAppIndex + ", .applications = " + this.applications + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CardStatus> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CardStatus> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CardStatus cardStatus = new android.hardware.radio.V1_0.CardStatus();
            cardStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(cardStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cardState = hwBlob.getInt32(j + 0);
        this.universalPinState = hwBlob.getInt32(j + 4);
        this.gsmUmtsSubscriptionAppIndex = hwBlob.getInt32(j + 8);
        this.cdmaSubscriptionAppIndex = hwBlob.getInt32(j + 12);
        this.imsSubscriptionAppIndex = hwBlob.getInt32(j + 16);
        long j2 = j + 24;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, hwBlob.handle(), j2 + 0, true);
        this.applications.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.AppStatus appStatus = new android.hardware.radio.V1_0.AppStatus();
            appStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            this.applications.add(appStatus);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CardStatus> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.cardState);
        hwBlob.putInt32(4 + j, this.universalPinState);
        hwBlob.putInt32(j + 8, this.gsmUmtsSubscriptionAppIndex);
        hwBlob.putInt32(j + 12, this.cdmaSubscriptionAppIndex);
        hwBlob.putInt32(16 + j, this.imsSubscriptionAppIndex);
        int size = this.applications.size();
        long j2 = j + 24;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 64);
        for (int i = 0; i < size; i++) {
            this.applications.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
