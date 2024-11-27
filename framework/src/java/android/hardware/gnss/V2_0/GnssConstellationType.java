package android.hardware.gnss.V2_0;

/* loaded from: classes2.dex */
public final class GnssConstellationType {
    public static final byte BEIDOU = 5;
    public static final byte GALILEO = 6;
    public static final byte GLONASS = 3;
    public static final byte GPS = 1;
    public static final byte IRNSS = 7;
    public static final byte QZSS = 4;
    public static final byte SBAS = 2;
    public static final byte UNKNOWN = 0;

    public static final java.lang.String toString(byte b) {
        if (b == 0) {
            return "UNKNOWN";
        }
        if (b == 1) {
            return "GPS";
        }
        if (b == 2) {
            return "SBAS";
        }
        if (b == 3) {
            return "GLONASS";
        }
        if (b == 4) {
            return "QZSS";
        }
        if (b == 5) {
            return "BEIDOU";
        }
        if (b == 6) {
            return "GALILEO";
        }
        if (b == 7) {
            return "IRNSS";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("UNKNOWN");
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("GPS");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("SBAS");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("GLONASS");
            b2 = (byte) (b2 | 3);
        }
        if ((b & 4) == 4) {
            arrayList.add("QZSS");
            b2 = (byte) (b2 | 4);
        }
        if ((b & 5) == 5) {
            arrayList.add("BEIDOU");
            b2 = (byte) (b2 | 5);
        }
        if ((b & 6) == 6) {
            arrayList.add("GALILEO");
            b2 = (byte) (b2 | 6);
        }
        if ((b & 7) == 7) {
            arrayList.add("IRNSS");
            b2 = (byte) (b2 | 7);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
