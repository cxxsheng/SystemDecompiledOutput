package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class BERConstructedOctetString extends com.android.internal.org.bouncycastle.asn1.BEROctetString {
    private static final int MAX_LENGTH = 1000;
    private java.util.Vector octs;

    private static byte[] toBytes(java.util.Vector vector) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        for (int i = 0; i != vector.size(); i++) {
            try {
                byteArrayOutputStream.write(((com.android.internal.org.bouncycastle.asn1.DEROctetString) vector.elementAt(i)).getOctets());
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("exception converting octets " + e.toString());
            } catch (java.lang.ClassCastException e2) {
                throw new java.lang.IllegalArgumentException(vector.elementAt(i).getClass().getName() + " found in input should only contain DEROctetString");
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public BERConstructedOctetString(byte[] bArr) {
        super(bArr);
    }

    public BERConstructedOctetString(java.util.Vector vector) {
        super(toBytes(vector));
        this.octs = vector;
    }

    public BERConstructedOctetString(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        super(toByteArray(aSN1Primitive));
    }

    private static byte[] toByteArray(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        try {
            return aSN1Primitive.getEncoded();
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("Unable to encode object");
        }
    }

    public BERConstructedOctetString(com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this(aSN1Encodable.toASN1Primitive());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetString
    public byte[] getOctets() {
        return this.string;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.BEROctetString
    public java.util.Enumeration getObjects() {
        if (this.octs == null) {
            return generateOcts().elements();
        }
        return this.octs.elements();
    }

    private java.util.Vector generateOcts() {
        int i;
        java.util.Vector vector = new java.util.Vector();
        int i2 = 0;
        while (i2 < this.string.length) {
            int i3 = i2 + 1000;
            if (i3 > this.string.length) {
                i = this.string.length;
            } else {
                i = i3;
            }
            int i4 = i - i2;
            byte[] bArr = new byte[i4];
            java.lang.System.arraycopy(this.string, i2, bArr, 0, i4);
            vector.addElement(new com.android.internal.org.bouncycastle.asn1.DEROctetString(bArr));
            i2 = i3;
        }
        return vector;
    }

    public static com.android.internal.org.bouncycastle.asn1.BEROctetString fromSequence(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        java.util.Vector vector = new java.util.Vector();
        java.util.Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            vector.addElement(objects.nextElement());
        }
        return new com.android.internal.org.bouncycastle.asn1.BERConstructedOctetString(vector);
    }
}
