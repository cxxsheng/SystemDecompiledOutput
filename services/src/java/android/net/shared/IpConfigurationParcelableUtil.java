package android.net.shared;

/* loaded from: classes.dex */
public final class IpConfigurationParcelableUtil {
    public static java.lang.String parcelAddress(@android.annotation.Nullable java.net.InetAddress inetAddress) {
        if (inetAddress == null) {
            return null;
        }
        return inetAddress.getHostAddress();
    }

    public static java.net.InetAddress unparcelAddress(@android.annotation.Nullable java.lang.String str) {
        if (str == null) {
            return null;
        }
        return android.net.InetAddresses.parseNumericAddress(str);
    }
}
