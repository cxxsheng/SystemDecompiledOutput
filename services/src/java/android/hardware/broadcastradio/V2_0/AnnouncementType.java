package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class AnnouncementType {
    public static final byte EMERGENCY = 1;
    public static final byte EVENT = 6;
    public static final byte MISC = 8;
    public static final byte NEWS = 5;
    public static final byte SPORT = 7;
    public static final byte TRAFFIC = 3;
    public static final byte WARNING = 2;
    public static final byte WEATHER = 4;

    public static final java.lang.String toString(byte b) {
        if (b == 1) {
            return "EMERGENCY";
        }
        if (b == 2) {
            return "WARNING";
        }
        if (b == 3) {
            return "TRAFFIC";
        }
        if (b == 4) {
            return "WEATHER";
        }
        if (b == 5) {
            return "NEWS";
        }
        if (b == 6) {
            return "EVENT";
        }
        if (b == 7) {
            return "SPORT";
        }
        if (b == 8) {
            return "MISC";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((b & 1) != 1) {
            b2 = 0;
        } else {
            arrayList.add("EMERGENCY");
            b2 = (byte) 1;
        }
        if ((b & 2) == 2) {
            arrayList.add("WARNING");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("TRAFFIC");
            b2 = (byte) (b2 | 3);
        }
        if ((b & 4) == 4) {
            arrayList.add("WEATHER");
            b2 = (byte) (b2 | 4);
        }
        if ((b & 5) == 5) {
            arrayList.add("NEWS");
            b2 = (byte) (b2 | 5);
        }
        if ((b & 6) == 6) {
            arrayList.add("EVENT");
            b2 = (byte) (b2 | 6);
        }
        if ((b & 7) == 7) {
            arrayList.add("SPORT");
            b2 = (byte) (b2 | 7);
        }
        if ((b & 8) == 8) {
            arrayList.add("MISC");
            b2 = (byte) (b2 | 8);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
