package com.android.internal.org.bouncycastle.asn1.pkcs;

/* loaded from: classes4.dex */
public class PBKDF2Params extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private static final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algid_hmacWithSHA1 = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.id_hmacWithSHA1, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    private final com.android.internal.org.bouncycastle.asn1.ASN1Integer iterationCount;
    private final com.android.internal.org.bouncycastle.asn1.ASN1Integer keyLength;
    private final com.android.internal.org.bouncycastle.asn1.ASN1OctetString octStr;
    private final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier prf;

    public static com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params) {
            return (com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params) obj;
        }
        if (obj != null) {
            return new com.android.internal.org.bouncycastle.asn1.pkcs.PBKDF2Params(com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public PBKDF2Params(byte[] bArr, int i) {
        this(bArr, i, 0);
    }

    public PBKDF2Params(byte[] bArr, int i, int i2) {
        this(bArr, i, i2, null);
    }

    public PBKDF2Params(byte[] bArr, int i, int i2, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        this.octStr = new com.android.internal.org.bouncycastle.asn1.DEROctetString(com.android.internal.org.bouncycastle.util.Arrays.clone(bArr));
        this.iterationCount = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i);
        if (i2 > 0) {
            this.keyLength = new com.android.internal.org.bouncycastle.asn1.ASN1Integer(i2);
        } else {
            this.keyLength = null;
        }
        this.prf = algorithmIdentifier;
    }

    public PBKDF2Params(byte[] bArr, int i, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        this(bArr, i, 0, algorithmIdentifier);
    }

    private PBKDF2Params(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        this.octStr = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) objects.nextElement();
        this.iterationCount = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) objects.nextElement();
        if (objects.hasMoreElements()) {
            java.lang.Object nextElement = objects.nextElement();
            if (nextElement instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) {
                this.keyLength = com.android.internal.org.bouncycastle.asn1.ASN1Integer.getInstance(nextElement);
                if (objects.hasMoreElements()) {
                    nextElement = objects.nextElement();
                } else {
                    nextElement = null;
                }
            } else {
                this.keyLength = null;
            }
            if (nextElement != null) {
                this.prf = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(nextElement);
                return;
            } else {
                this.prf = null;
                return;
            }
        }
        this.keyLength = null;
        this.prf = null;
    }

    public byte[] getSalt() {
        return this.octStr.getOctets();
    }

    public java.math.BigInteger getIterationCount() {
        return this.iterationCount.getValue();
    }

    public java.math.BigInteger getKeyLength() {
        if (this.keyLength != null) {
            return this.keyLength.getValue();
        }
        return null;
    }

    public boolean isDefaultPrf() {
        return this.prf == null || this.prf.equals(algid_hmacWithSHA1);
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getPrf() {
        if (this.prf != null) {
            return this.prf;
        }
        return algid_hmacWithSHA1;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector(4);
        aSN1EncodableVector.add(this.octStr);
        aSN1EncodableVector.add(this.iterationCount);
        if (this.keyLength != null) {
            aSN1EncodableVector.add(this.keyLength);
        }
        if (this.prf != null && !this.prf.equals(algid_hmacWithSHA1)) {
            aSN1EncodableVector.add(this.prf);
        }
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
