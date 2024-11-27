package com.android.internal.org.bouncycastle.jcajce.provider.config;

/* loaded from: classes4.dex */
public interface ConfigurableProvider {
    public static final java.lang.String ACCEPTABLE_EC_CURVES = "acceptableEcCurves";
    public static final java.lang.String ADDITIONAL_EC_PARAMETERS = "additionalEcParameters";
    public static final java.lang.String DH_DEFAULT_PARAMS = "DhDefaultParams";
    public static final java.lang.String EC_IMPLICITLY_CA = "ecImplicitlyCa";
    public static final java.lang.String THREAD_LOCAL_DH_DEFAULT_PARAMS = "threadLocalDhDefaultParams";
    public static final java.lang.String THREAD_LOCAL_EC_IMPLICITLY_CA = "threadLocalEcImplicitlyCa";

    void addAlgorithm(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str2);

    void addAlgorithm(java.lang.String str, java.lang.String str2);

    void addAttributes(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map);

    void addKeyInfoConverter(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter asymmetricKeyInfoConverter);

    void addPrivateAlgorithm(java.lang.String str, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, java.lang.String str2);

    void addPrivateAlgorithm(java.lang.String str, java.lang.String str2);

    com.android.internal.org.bouncycastle.jcajce.provider.util.AsymmetricKeyInfoConverter getKeyInfoConverter(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier);

    boolean hasAlgorithm(java.lang.String str, java.lang.String str2);

    void setParameter(java.lang.String str, java.lang.Object obj);
}
