package com.android.internal.org.bouncycastle.asn1.ocsp;

/* loaded from: classes4.dex */
public interface OCSPObjectIdentifiers {
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_basic = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_nonce = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.2");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_crl = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.3");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_response = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.4");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_nocheck = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.5");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_archive_cutoff = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.6");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_service_locator = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1.7");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_pref_sig_algs = id_pkix_ocsp.branch("8");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_pkix_ocsp_extended_revoke = id_pkix_ocsp.branch("9");
}
