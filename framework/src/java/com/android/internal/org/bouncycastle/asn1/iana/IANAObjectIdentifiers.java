package com.android.internal.org.bouncycastle.asn1.iana;

/* loaded from: classes4.dex */
public interface IANAObjectIdentifiers {
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier internet = new com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier("1.3.6.1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier directory = internet.branch("1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier mgmt = internet.branch("2");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier experimental = internet.branch("3");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier _private = internet.branch("4");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier security = internet.branch("5");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier SNMPv2 = internet.branch("6");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier mail = internet.branch("7");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier security_mechanisms = security.branch("5");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier security_nametypes = security.branch("6");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier pkix = security_mechanisms.branch("6");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier ipsec = security_mechanisms.branch("8");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier isakmpOakley = ipsec.branch("1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier hmacMD5 = isakmpOakley.branch("1");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier hmacSHA1 = isakmpOakley.branch("2");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier hmacTIGER = isakmpOakley.branch("3");
    public static final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier hmacRIPEMD160 = isakmpOakley.branch("4");
}
