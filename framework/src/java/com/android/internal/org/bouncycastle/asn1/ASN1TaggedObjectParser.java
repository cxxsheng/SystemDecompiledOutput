package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public interface ASN1TaggedObjectParser extends com.android.internal.org.bouncycastle.asn1.ASN1Encodable, com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable {
    com.android.internal.org.bouncycastle.asn1.ASN1Encodable getObjectParser(int i, boolean z) throws java.io.IOException;

    int getTagNo();
}
