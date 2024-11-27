package android.net;

/* loaded from: classes2.dex */
public abstract class PlatformVpnProfile {
    public static final int MAX_MTU_DEFAULT = 1360;
    public static final int TYPE_IKEV2_IPSEC_PSK = 7;
    public static final int TYPE_IKEV2_IPSEC_RSA = 8;
    public static final int TYPE_IKEV2_IPSEC_USER_PASS = 6;
    protected final boolean mExcludeLocalRoutes;
    protected final boolean mRequiresInternetValidation;
    protected final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlatformVpnType {
    }

    public abstract com.android.internal.net.VpnProfile toVpnProfile() throws java.io.IOException, java.security.GeneralSecurityException;

    PlatformVpnProfile(int i, boolean z, boolean z2) {
        this.mType = i;
        this.mExcludeLocalRoutes = z;
        this.mRequiresInternetValidation = z2;
    }

    public final int getType() {
        return this.mType;
    }

    public final boolean areLocalRoutesExcluded() {
        return this.mExcludeLocalRoutes;
    }

    public final boolean isInternetValidationRequired() {
        return this.mRequiresInternetValidation;
    }

    public final java.lang.String getTypeString() {
        switch (this.mType) {
            case 6:
                return "IKEv2/IPsec Username/Password";
            case 7:
                return "IKEv2/IPsec Preshared key";
            case 8:
                return "IKEv2/IPsec RSA Digital Signature";
            default:
                return "Unknown VPN profile type";
        }
    }

    public static android.net.PlatformVpnProfile fromVpnProfile(com.android.internal.net.VpnProfile vpnProfile) throws java.io.IOException, java.security.GeneralSecurityException {
        switch (vpnProfile.type) {
            case 6:
            case 7:
            case 8:
                return android.net.Ikev2VpnProfile.fromVpnProfile(vpnProfile);
            default:
                throw new java.lang.IllegalArgumentException("Unknown VPN Profile type");
        }
    }
}
