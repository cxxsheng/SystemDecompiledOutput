package android.internal.hidl.safe_union.V1_0;

/* loaded from: classes2.dex */
public final class Monostate {
    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.internal.hidl.safe_union.V1_0.Monostate.class) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return java.util.Objects.hash(new java.lang.Object[0]);
    }

    public final java.lang.String toString() {
        return "{}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(1L), 0L);
    }

    public static final java.util.ArrayList<android.internal.hidl.safe_union.V1_0.Monostate> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.internal.hidl.safe_union.V1_0.Monostate> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.internal.hidl.safe_union.V1_0.Monostate monostate = new android.internal.hidl.safe_union.V1_0.Monostate();
            monostate.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 1);
            arrayList.add(monostate);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(1);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.internal.hidl.safe_union.V1_0.Monostate> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 1);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
    }
}
