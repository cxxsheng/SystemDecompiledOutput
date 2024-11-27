package com.android.internal.org.bouncycastle.asn1.cms;

/* loaded from: classes4.dex */
public interface CMSObjectIdentifiers {
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier data = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.data;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier signedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.signedData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier envelopedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.envelopedData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier signedAndEnvelopedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.signedAndEnvelopedData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier digestedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.digestedData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier encryptedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.encryptedData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier authenticatedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_ct_authData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier compressedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_ct_compressedData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier authEnvelopedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_ct_authEnvelopedData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier timestampedData = com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_ct_timestampedData;
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ri = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1.5.5.7.16");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ri_ocsp_response = id_ri.branch("2");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier id_ri_scvp = id_ri.branch("4");
}
