package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class KeyPurposeId extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id;
    private static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_kp = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.3");
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId anyExtendedKeyUsage = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(com.android.internal.org.bouncycastle.asn1.x509.Extension.extendedKeyUsage.branch(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_serverAuth = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("1"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_clientAuth = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("2"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_codeSigning = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("3"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_emailProtection = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("4"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_ipsecEndSystem = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("5"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_ipsecTunnel = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("6"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_ipsecUser = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("7"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_timeStamping = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("8"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_OCSPSigning = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("9"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_dvcs = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("10"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_sbgpCertAAServerAuth = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("11"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_scvp_responder = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("12"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_eapOverPPP = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("13"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_eapOverLAN = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("14"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_scvpServer = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("15"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_scvpClient = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("16"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_ipsecIKE = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("17"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_capwapAC = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("18"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_capwapWTP = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(id_kp.branch("19"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_smartcardlogon = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.4.1.311.20.2.2"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_macAddress = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.1.1.1.22"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_msSGC = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.4.1.311.10.3.3"));
    public static final com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId id_kp_nsSGC = new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.16.840.1.113730.4.1"));

    private KeyPurposeId(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.id = aSN1ObjectIdentifier;
    }

    public KeyPurposeId(java.lang.String str) {
        this(new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier(str));
    }

    public static com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId) {
            return (com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.x509.KeyPurposeId(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier.getInstance(obj));
        }
        return null;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier toOID() {
        return this.id;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        return this.id;
    }

    public java.lang.String getId() {
        return this.id.getId();
    }

    public java.lang.String toString() {
        return this.id.toString();
    }
}
