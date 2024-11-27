package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public final class IkeIdentificationUtils {
    private static final java.lang.String DER_ASN1_DN_KEY = "DER_ASN1_DN_KEY";
    private static final java.lang.String FQDN_KEY = "FQDN_KEY";
    private static final int ID_TYPE_DER_ASN1_DN = 1;
    private static final int ID_TYPE_FQDN = 2;
    private static final int ID_TYPE_IPV4_ADDR = 3;
    private static final int ID_TYPE_IPV6_ADDR = 4;
    private static final java.lang.String ID_TYPE_KEY = "ID_TYPE_KEY";
    private static final int ID_TYPE_KEY_ID = 5;
    private static final int ID_TYPE_RFC822_ADDR = 6;
    private static final java.lang.String IP4_ADDRESS_KEY = "IP4_ADDRESS_KEY";
    private static final java.lang.String IP6_ADDRESS_KEY = "IP6_ADDRESS_KEY";
    private static final java.lang.String KEY_ID_KEY = "KEY_ID_KEY";
    private static final java.lang.String RFC822_ADDRESS_KEY = "RFC822_ADDRESS_KEY";

    public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeIdentification ikeIdentification) {
        if (ikeIdentification instanceof android.net.ipsec.ike.IkeDerAsn1DnIdentification) {
            android.os.PersistableBundle createPersistableBundle = createPersistableBundle(1);
            createPersistableBundle.putPersistableBundle(DER_ASN1_DN_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray(((android.net.ipsec.ike.IkeDerAsn1DnIdentification) ikeIdentification).derAsn1Dn.getEncoded()));
            return createPersistableBundle;
        }
        if (ikeIdentification instanceof android.net.ipsec.ike.IkeFqdnIdentification) {
            android.os.PersistableBundle createPersistableBundle2 = createPersistableBundle(2);
            createPersistableBundle2.putString(FQDN_KEY, ((android.net.ipsec.ike.IkeFqdnIdentification) ikeIdentification).fqdn);
            return createPersistableBundle2;
        }
        if (ikeIdentification instanceof android.net.ipsec.ike.IkeIpv4AddrIdentification) {
            android.os.PersistableBundle createPersistableBundle3 = createPersistableBundle(3);
            createPersistableBundle3.putString(IP4_ADDRESS_KEY, ((android.net.ipsec.ike.IkeIpv4AddrIdentification) ikeIdentification).ipv4Address.getHostAddress());
            return createPersistableBundle3;
        }
        if (ikeIdentification instanceof android.net.ipsec.ike.IkeIpv6AddrIdentification) {
            android.os.PersistableBundle createPersistableBundle4 = createPersistableBundle(4);
            createPersistableBundle4.putString(IP6_ADDRESS_KEY, ((android.net.ipsec.ike.IkeIpv6AddrIdentification) ikeIdentification).ipv6Address.getHostAddress());
            return createPersistableBundle4;
        }
        if (ikeIdentification instanceof android.net.ipsec.ike.IkeKeyIdIdentification) {
            android.os.PersistableBundle createPersistableBundle5 = createPersistableBundle(5);
            createPersistableBundle5.putPersistableBundle(KEY_ID_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray(((android.net.ipsec.ike.IkeKeyIdIdentification) ikeIdentification).keyId));
            return createPersistableBundle5;
        }
        if (ikeIdentification instanceof android.net.ipsec.ike.IkeRfc822AddrIdentification) {
            android.os.PersistableBundle createPersistableBundle6 = createPersistableBundle(6);
            createPersistableBundle6.putString(RFC822_ADDRESS_KEY, ((android.net.ipsec.ike.IkeRfc822AddrIdentification) ikeIdentification).rfc822Name);
            return createPersistableBundle6;
        }
        throw new java.lang.IllegalStateException("Unrecognized IkeIdentification subclass");
    }

    private static android.os.PersistableBundle createPersistableBundle(int i) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(ID_TYPE_KEY, i);
        return persistableBundle;
    }

    public static android.net.ipsec.ike.IkeIdentification fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        int i = persistableBundle.getInt(ID_TYPE_KEY);
        switch (i) {
            case 1:
                android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(DER_ASN1_DN_KEY);
                java.util.Objects.requireNonNull(persistableBundle2, "ASN1 DN was null");
                return new android.net.ipsec.ike.IkeDerAsn1DnIdentification(new javax.security.auth.x500.X500Principal(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle2)));
            case 2:
                return new android.net.ipsec.ike.IkeFqdnIdentification(persistableBundle.getString(FQDN_KEY));
            case 3:
                java.lang.String string = persistableBundle.getString(IP4_ADDRESS_KEY);
                java.util.Objects.requireNonNull(string, "IPv4 address was null");
                return new android.net.ipsec.ike.IkeIpv4AddrIdentification((java.net.Inet4Address) android.net.InetAddresses.parseNumericAddress(string));
            case 4:
                java.lang.String string2 = persistableBundle.getString(IP6_ADDRESS_KEY);
                java.util.Objects.requireNonNull(string2, "IPv6 address was null");
                return new android.net.ipsec.ike.IkeIpv6AddrIdentification((java.net.Inet6Address) android.net.InetAddresses.parseNumericAddress(string2));
            case 5:
                android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(KEY_ID_KEY);
                java.util.Objects.requireNonNull(persistableBundle, "Key ID was null");
                return new android.net.ipsec.ike.IkeKeyIdIdentification(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle3));
            case 6:
                return new android.net.ipsec.ike.IkeRfc822AddrIdentification(persistableBundle.getString(RFC822_ADDRESS_KEY));
            default:
                throw new java.lang.IllegalStateException("Unrecognized IKE ID type: " + i);
        }
    }
}
