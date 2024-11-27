package android.hardware.usb.gadget.V1_2;

/* loaded from: classes2.dex */
public final class GadgetFunction {
    public static final long ACCESSORY = 2;
    public static final long ADB = 1;
    public static final long AUDIO_SOURCE = 64;
    public static final long MIDI = 8;
    public static final long MTP = 4;
    public static final long NCM = 1024;
    public static final long NONE = 0;
    public static final long PTP = 16;
    public static final long RNDIS = 32;

    public static final java.lang.String toString(long j) {
        if (j == 0) {
            return android.security.keystore.KeyProperties.DIGEST_NONE;
        }
        if (j == 1) {
            return "ADB";
        }
        if (j == 2) {
            return "ACCESSORY";
        }
        if (j == 4) {
            return "MTP";
        }
        if (j == 8) {
            return "MIDI";
        }
        if (j == 16) {
            return "PTP";
        }
        if (j == 32) {
            return "RNDIS";
        }
        if (j == 64) {
            return "AUDIO_SOURCE";
        }
        if (j == 1024) {
            return "NCM";
        }
        return "0x" + java.lang.Long.toHexString(j);
    }

    public static final java.lang.String dumpBitfield(long j) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(android.security.keystore.KeyProperties.DIGEST_NONE);
        long j2 = 1;
        if ((j & 1) != 1) {
            j2 = 0;
        } else {
            arrayList.add("ADB");
        }
        if ((j & 2) == 2) {
            arrayList.add("ACCESSORY");
            j2 |= 2;
        }
        if ((j & 4) == 4) {
            arrayList.add("MTP");
            j2 |= 4;
        }
        if ((j & 8) == 8) {
            arrayList.add("MIDI");
            j2 |= 8;
        }
        if ((j & 16) == 16) {
            arrayList.add("PTP");
            j2 |= 16;
        }
        if ((j & 32) == 32) {
            arrayList.add("RNDIS");
            j2 |= 32;
        }
        if ((j & 64) == 64) {
            arrayList.add("AUDIO_SOURCE");
            j2 |= 64;
        }
        if ((j & 1024) == 1024) {
            arrayList.add("NCM");
            j2 |= 1024;
        }
        if (j != j2) {
            arrayList.add("0x" + java.lang.Long.toHexString(j & (~j2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
