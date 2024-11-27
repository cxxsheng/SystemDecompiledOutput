package android.hardware.biometrics.fingerprint.V2_1;

/* loaded from: classes.dex */
public final class FingerprintAcquiredInfo {
    public static final int ACQUIRED_GOOD = 0;
    public static final int ACQUIRED_IMAGER_DIRTY = 3;
    public static final int ACQUIRED_INSUFFICIENT = 2;
    public static final int ACQUIRED_PARTIAL = 1;
    public static final int ACQUIRED_TOO_FAST = 5;
    public static final int ACQUIRED_TOO_SLOW = 4;
    public static final int ACQUIRED_VENDOR = 6;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "ACQUIRED_GOOD";
        }
        if (i == 1) {
            return "ACQUIRED_PARTIAL";
        }
        if (i == 2) {
            return "ACQUIRED_INSUFFICIENT";
        }
        if (i == 3) {
            return "ACQUIRED_IMAGER_DIRTY";
        }
        if (i == 4) {
            return "ACQUIRED_TOO_SLOW";
        }
        if (i == 5) {
            return "ACQUIRED_TOO_FAST";
        }
        if (i == 6) {
            return "ACQUIRED_VENDOR";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("ACQUIRED_GOOD");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("ACQUIRED_PARTIAL");
        }
        if ((i & 2) == 2) {
            arrayList.add("ACQUIRED_INSUFFICIENT");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("ACQUIRED_IMAGER_DIRTY");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("ACQUIRED_TOO_SLOW");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("ACQUIRED_TOO_FAST");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("ACQUIRED_VENDOR");
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
