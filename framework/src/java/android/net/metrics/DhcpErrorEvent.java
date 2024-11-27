package android.net.metrics;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public final class DhcpErrorEvent implements android.net.metrics.IpConnectivityLog.Event {
    public static final int BOOTP_TOO_SHORT = 67174400;
    public static final int BUFFER_UNDERFLOW = 83951616;
    public static final android.os.Parcelable.Creator<android.net.metrics.DhcpErrorEvent> CREATOR = new android.os.Parcelable.Creator<android.net.metrics.DhcpErrorEvent>() { // from class: android.net.metrics.DhcpErrorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.DhcpErrorEvent createFromParcel(android.os.Parcel parcel) {
            return new android.net.metrics.DhcpErrorEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.metrics.DhcpErrorEvent[] newArray(int i) {
            return new android.net.metrics.DhcpErrorEvent[i];
        }
    };
    public static final int DHCP_BAD_MAGIC_COOKIE = 67239936;
    public static final int DHCP_ERROR = 4;
    private static final int DHCP_ERROR_TYPE = 1024;
    public static final int DHCP_INVALID_OPTION_LENGTH = 67305472;
    public static final int DHCP_NO_COOKIE = 67502080;
    public static final int DHCP_NO_MSG_TYPE = 67371008;
    public static final int DHCP_UNKNOWN_MSG_TYPE = 67436544;
    public static final int L2_ERROR = 1;
    private static final int L2_ERROR_TYPE = 256;
    public static final int L2_TOO_SHORT = 16842752;
    public static final int L2_WRONG_ETH_TYPE = 16908288;
    public static final int L3_ERROR = 2;
    private static final int L3_ERROR_TYPE = 512;
    public static final int L3_INVALID_IP = 33751040;
    public static final int L3_NOT_IPV4 = 33685504;
    public static final int L3_TOO_SHORT = 33619968;
    public static final int L4_ERROR = 3;
    private static final int L4_ERROR_TYPE = 768;
    public static final int L4_NOT_UDP = 50397184;
    public static final int L4_WRONG_PORT = 50462720;
    public static final int MISC_ERROR = 5;
    private static final int MISC_ERROR_TYPE = 1280;
    public static final int PARSING_ERROR = 84082688;
    public static final int RECEIVE_ERROR = 84017152;
    public final int errorCode;

    public DhcpErrorEvent(int i) {
        this.errorCode = i;
    }

    private DhcpErrorEvent(android.os.Parcel parcel) {
        this.errorCode = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.errorCode);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static int errorCodeWithOption(int i, int i2) {
        return (i & (-65536)) | (i2 & 255);
    }

    public java.lang.String toString() {
        return java.lang.String.format("DhcpErrorEvent(%s)", android.net.metrics.DhcpErrorEvent.Decoder.constants.get(this.errorCode));
    }

    static final class Decoder {
        static final android.util.SparseArray<java.lang.String> constants = com.android.internal.util.MessageUtils.findMessageNames(new java.lang.Class[]{android.net.metrics.DhcpErrorEvent.class}, new java.lang.String[]{"L2_", "L3_", "L4_", "BOOTP_", "DHCP_", "BUFFER_", "RECEIVE_", "PARSING_"});

        Decoder() {
        }
    }
}
