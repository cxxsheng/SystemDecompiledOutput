package com.android.internal.org.bouncycastle.jce.interfaces;

/* loaded from: classes4.dex */
public interface PKCS12BagAttributeCarrier {
    com.android.internal.org.bouncycastle.asn1.ASN1Encodable getBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier);

    java.util.Enumeration getBagAttributeKeys();

    void setBagAttribute(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable);
}
