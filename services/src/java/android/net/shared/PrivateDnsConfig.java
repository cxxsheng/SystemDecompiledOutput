package android.net.shared;

/* loaded from: classes.dex */
public class PrivateDnsConfig {

    @android.annotation.NonNull
    public final java.net.InetAddress[] dohIps;

    @android.annotation.NonNull
    public final java.lang.String dohName;

    @android.annotation.NonNull
    public final java.lang.String dohPath;
    public final int dohPort;

    @android.annotation.NonNull
    public final java.lang.String hostname;

    @android.annotation.NonNull
    public final java.net.InetAddress[] ips;
    public final int mode;

    public PrivateDnsConfig() {
        this(false);
    }

    public PrivateDnsConfig(boolean z) {
        this(z ? 2 : 1, null, null, null, null, null, -1);
    }

    public PrivateDnsConfig(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.net.InetAddress[] inetAddressArr) {
        this(android.text.TextUtils.isEmpty(str) ? 1 : 3, str, inetAddressArr, null, null, null, -1);
    }

    public PrivateDnsConfig(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.net.InetAddress[] inetAddressArr, @android.annotation.Nullable java.lang.String str2, @android.annotation.Nullable java.net.InetAddress[] inetAddressArr2, @android.annotation.Nullable java.lang.String str3, int i2) {
        this.mode = i;
        this.hostname = str == null ? "" : str;
        this.ips = inetAddressArr != null ? (java.net.InetAddress[]) inetAddressArr.clone() : new java.net.InetAddress[0];
        this.dohName = str2 == null ? "" : str2;
        this.dohIps = inetAddressArr2 != null ? (java.net.InetAddress[]) inetAddressArr2.clone() : new java.net.InetAddress[0];
        this.dohPath = str3 == null ? "" : str3;
        this.dohPort = i2;
    }

    public PrivateDnsConfig(android.net.shared.PrivateDnsConfig privateDnsConfig) {
        this.mode = privateDnsConfig.mode;
        this.hostname = privateDnsConfig.hostname;
        this.ips = privateDnsConfig.ips;
        this.dohName = privateDnsConfig.dohName;
        this.dohIps = privateDnsConfig.dohIps;
        this.dohPath = privateDnsConfig.dohPath;
        this.dohPort = privateDnsConfig.dohPort;
    }

    public boolean inStrictMode() {
        return this.mode == 3;
    }

    public boolean inOpportunisticMode() {
        return this.mode == 2;
    }

    public java.lang.String toString() {
        return android.net.shared.PrivateDnsConfig.class.getSimpleName() + "{" + modeAsString(this.mode) + ":" + this.hostname + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + java.util.Arrays.toString(this.ips) + ", dohName=" + this.dohName + ", dohIps=" + java.util.Arrays.toString(this.dohIps) + ", dohPath=" + this.dohPath + ", dohPort=" + this.dohPort + "}";
    }

    @android.annotation.NonNull
    private static java.lang.String modeAsString(int i) {
        switch (i) {
            case 1:
                return "off";
            case 2:
                return "opportunistic";
            case 3:
                return "strict";
            default:
                return "unknown";
        }
    }

    public android.net.PrivateDnsConfigParcel toParcel() {
        android.net.PrivateDnsConfigParcel privateDnsConfigParcel = new android.net.PrivateDnsConfigParcel();
        privateDnsConfigParcel.hostname = this.hostname;
        privateDnsConfigParcel.ips = (java.lang.String[]) android.net.shared.ParcelableUtil.toParcelableArray(java.util.Arrays.asList(this.ips), new android.net.shared.InitialConfiguration$$ExternalSyntheticLambda11(), java.lang.String.class);
        privateDnsConfigParcel.privateDnsMode = this.mode;
        privateDnsConfigParcel.dohName = this.dohName;
        privateDnsConfigParcel.dohIps = (java.lang.String[]) android.net.shared.ParcelableUtil.toParcelableArray(java.util.Arrays.asList(this.dohIps), new android.net.shared.InitialConfiguration$$ExternalSyntheticLambda11(), java.lang.String.class);
        privateDnsConfigParcel.dohPath = this.dohPath;
        privateDnsConfigParcel.dohPort = this.dohPort;
        return privateDnsConfigParcel;
    }

    public static android.net.shared.PrivateDnsConfig fromParcel(android.net.PrivateDnsConfigParcel privateDnsConfigParcel) {
        java.net.InetAddress[] inetAddressArr = (java.net.InetAddress[]) android.net.shared.ParcelableUtil.fromParcelableArray(privateDnsConfigParcel.ips, new android.net.shared.InitialConfiguration$$ExternalSyntheticLambda8()).toArray(new java.net.InetAddress[privateDnsConfigParcel.ips.length]);
        if (privateDnsConfigParcel.privateDnsMode == -1) {
            return new android.net.shared.PrivateDnsConfig(privateDnsConfigParcel.hostname, inetAddressArr);
        }
        return new android.net.shared.PrivateDnsConfig(privateDnsConfigParcel.privateDnsMode, privateDnsConfigParcel.hostname, inetAddressArr, privateDnsConfigParcel.dohName, (java.net.InetAddress[]) android.net.shared.ParcelableUtil.fromParcelableArray(privateDnsConfigParcel.dohIps, new android.net.shared.InitialConfiguration$$ExternalSyntheticLambda8()).toArray(new java.net.InetAddress[privateDnsConfigParcel.dohIps.length]), privateDnsConfigParcel.dohPath, privateDnsConfigParcel.dohPort);
    }
}
