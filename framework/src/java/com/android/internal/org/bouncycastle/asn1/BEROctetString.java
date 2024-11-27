package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BEROctetString extends com.android.internal.org.bouncycastle.asn1.ASN1OctetString {
    private static final int DEFAULT_CHUNK_SIZE = 1000;
    private final int chunkSize;
    private final com.android.internal.org.bouncycastle.asn1.ASN1OctetString[] octs;

    private static byte[] toBytes(com.android.internal.org.bouncycastle.asn1.ASN1OctetString[] aSN1OctetStringArr) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        for (int i = 0; i != aSN1OctetStringArr.length; i++) {
            try {
                byteArrayOutputStream.write(aSN1OctetStringArr[i].getOctets());
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("exception converting octets " + e.toString());
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public BEROctetString(byte[] bArr) {
        this(bArr, 1000);
    }

    public BEROctetString(com.android.internal.org.bouncycastle.asn1.ASN1OctetString[] aSN1OctetStringArr) {
        this(aSN1OctetStringArr, 1000);
    }

    public BEROctetString(byte[] bArr, int i) {
        this(bArr, null, i);
    }

    public BEROctetString(com.android.internal.org.bouncycastle.asn1.ASN1OctetString[] aSN1OctetStringArr, int i) {
        this(toBytes(aSN1OctetStringArr), aSN1OctetStringArr, i);
    }

    private BEROctetString(byte[] bArr, com.android.internal.org.bouncycastle.asn1.ASN1OctetString[] aSN1OctetStringArr, int i) {
        super(bArr);
        this.octs = aSN1OctetStringArr;
        this.chunkSize = i;
    }

    public java.util.Enumeration getObjects() {
        if (this.octs == null) {
            return new java.util.Enumeration() { // from class: com.android.internal.org.bouncycastle.asn1.BEROctetString.1
                int pos = 0;

                @Override // java.util.Enumeration
                public boolean hasMoreElements() {
                    return this.pos < com.android.internal.org.bouncycastle.asn1.BEROctetString.this.string.length;
                }

                @Override // java.util.Enumeration
                public java.lang.Object nextElement() {
                    if (this.pos < com.android.internal.org.bouncycastle.asn1.BEROctetString.this.string.length) {
                        int min = java.lang.Math.min(com.android.internal.org.bouncycastle.asn1.BEROctetString.this.string.length - this.pos, com.android.internal.org.bouncycastle.asn1.BEROctetString.this.chunkSize);
                        byte[] bArr = new byte[min];
                        java.lang.System.arraycopy(com.android.internal.org.bouncycastle.asn1.BEROctetString.this.string, this.pos, bArr, 0, min);
                        this.pos += min;
                        return new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr);
                    }
                    throw new java.util.NoSuchElementException();
                }
            };
        }
        return new java.util.Enumeration() { // from class: com.android.internal.org.bouncycastle.asn1.BEROctetString.2
            int counter = 0;

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.counter < com.android.internal.org.bouncycastle.asn1.BEROctetString.this.octs.length;
            }

            @Override // java.util.Enumeration
            public java.lang.Object nextElement() {
                if (this.counter < com.android.internal.org.bouncycastle.asn1.BEROctetString.this.octs.length) {
                    com.android.internal.org.bouncycastle.asn1.ASN1OctetString[] aSN1OctetStringArr = com.android.internal.org.bouncycastle.asn1.BEROctetString.this.octs;
                    int i = this.counter;
                    this.counter = i + 1;
                    return aSN1OctetStringArr[i];
                }
                throw new java.util.NoSuchElementException();
            }
        };
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return true;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        java.util.Enumeration objects = getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            i += ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) objects.nextElement()).toASN1Primitive().encodedLength();
        }
        return i + 2 + 2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetString, com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException {
        aSN1OutputStream.writeEncodedIndef(z, 36, getObjects());
    }

    static com.android.internal.org.bouncycastle.asn1.BEROctetString fromSequence(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        int size = aSN1Sequence.size();
        com.android.internal.org.bouncycastle.asn1.ASN1OctetString[] aSN1OctetStringArr = new com.android.internal.org.bouncycastle.asn1.ASN1OctetString[size];
        for (int i = 0; i < size; i++) {
            aSN1OctetStringArr[i] = com.android.internal.org.bouncycastle.asn1.ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i));
        }
        return new com.android.internal.org.bouncycastle.asn1.BEROctetString(aSN1OctetStringArr);
    }
}
