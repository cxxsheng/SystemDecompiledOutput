package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public interface X509ObjectIdentifiers {
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier commonName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.3").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier countryName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.6").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier localityName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.7").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier stateOrProvinceName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.8").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier organization = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.10").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier organizationalUnitName = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.11").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_at_telephoneNumber = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.20").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_at_name = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.41").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_at_organizationIdentifier = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.4.97").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_SHA1 = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.14.3.2.26").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ripemd160 = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.36.3.2.1").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ripemd160WithRSAEncryption = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.36.3.3.1.2").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ea_rsa = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.8.1.1").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_rsassa_pss_shake128 = id_pkix.branch("6.30");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_rsassa_pss_shake256 = id_pkix.branch("6.31");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ecdsa_with_shake128 = id_pkix.branch("6.32");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ecdsa_with_shake256 = id_pkix.branch("6.33");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pe = id_pkix.branch("1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ce = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("2.5.29");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ad = id_pkix.branch("48");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ad_caIssuers = id_ad.branch("2").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ad_ocsp = id_ad.branch("1").intern();
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ocspAccessMethod = id_ad_ocsp;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier crlAccessMethod = id_ad_caIssuers;
}
