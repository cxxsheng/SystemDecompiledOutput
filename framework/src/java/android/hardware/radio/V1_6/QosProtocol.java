package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class QosProtocol {
    public static final byte AH = 51;
    public static final byte ESP = 50;
    public static final byte TCP = 6;
    public static final byte UDP = 17;
    public static final byte UNSPECIFIED = -1;

    public static final java.lang.String toString(byte b) {
        if (b == -1) {
            return "UNSPECIFIED";
        }
        if (b == 6) {
            return android.telephony.ims.SipDelegateImsConfiguration.SIP_TRANSPORT_TCP;
        }
        if (b == 17) {
            return android.telephony.ims.SipDelegateImsConfiguration.SIP_TRANSPORT_UDP;
        }
        if (b == 50) {
            return "ESP";
        }
        if (b == 51) {
            return "AH";
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt(b));
    }

    public static final java.lang.String dumpBitfield(byte b) {
        byte b2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((b & (-1)) != -1) {
            b2 = 0;
        } else {
            arrayList.add("UNSPECIFIED");
            b2 = (byte) (-1);
        }
        if ((b & 6) == 6) {
            arrayList.add(android.telephony.ims.SipDelegateImsConfiguration.SIP_TRANSPORT_TCP);
            b2 = (byte) (b2 | 6);
        }
        if ((b & 17) == 17) {
            arrayList.add(android.telephony.ims.SipDelegateImsConfiguration.SIP_TRANSPORT_UDP);
            b2 = (byte) (b2 | 17);
        }
        if ((b & 50) == 50) {
            arrayList.add("ESP");
            b2 = (byte) (b2 | 50);
        }
        if ((b & 51) == 51) {
            arrayList.add("AH");
            b2 = (byte) (b2 | 51);
        }
        if (b != b2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(java.lang.Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
