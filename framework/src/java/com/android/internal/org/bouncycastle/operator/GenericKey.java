package com.android.internal.org.bouncycastle.operator;

/* loaded from: classes4.dex */
public class GenericKey {
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier;
    private java.lang.Object representation;

    public GenericKey(java.lang.Object obj) {
        this.algorithmIdentifier = null;
        this.representation = obj;
    }

    public GenericKey(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.algorithmIdentifier = algorithmIdentifier;
        this.representation = bArr;
    }

    protected GenericKey(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, java.lang.Object obj) {
        this.algorithmIdentifier = algorithmIdentifier;
        this.representation = obj;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getAlgorithmIdentifier() {
        return this.algorithmIdentifier;
    }

    public java.lang.Object getRepresentation() {
        return this.representation;
    }
}
