package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class EmfIndicator {
    public static final byte EMF_BOTH_NR_EUTRA_CONNECTED_TO_5GCN = 3;
    public static final byte EMF_EUTRA_CONNECTED_TO_5GCN = 2;
    public static final byte EMF_NOT_SUPPORTED = 0;
    public static final byte EMF_NR_CONNECTED_TO_5GCN = 1;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "EMF_NOT_SUPPORTED";
        }
        if (b == 1) {
            return "EMF_NR_CONNECTED_TO_5GCN";
        }
        if (b == 2) {
            return "EMF_EUTRA_CONNECTED_TO_5GCN";
        }
        if (b == 3) {
            return "EMF_BOTH_NR_EUTRA_CONNECTED_TO_5GCN";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("EMF_NOT_SUPPORTED");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("EMF_NR_CONNECTED_TO_5GCN");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("EMF_EUTRA_CONNECTED_TO_5GCN");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("EMF_BOTH_NR_EUTRA_CONNECTED_TO_5GCN");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
