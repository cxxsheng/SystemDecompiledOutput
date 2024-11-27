package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1OctetString extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive implements com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser {
    byte[] string;

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    abstract void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException;

    public static com.android.internal.org.bouncycastle.asn1.ASN1OctetString getInstance(com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            if (!aSN1TaggedObject.isExplicit()) {
                throw new java.lang.IllegalArgumentException("object implicit - explicit expected.");
            }
            return getInstance(aSN1TaggedObject.getObject());
        }
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive object = aSN1TaggedObject.getObject();
        if (aSN1TaggedObject.isExplicit()) {
            com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString = getInstance(object);
            if (aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                return new com.android.internal.org.bouncycastle.asn1.BEROctetString(new com.android.internal.org.bouncycastle.asn1.ASN1OctetString[]{aSN1OctetString});
            }
            return (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) new com.android.internal.org.bouncycastle.asn1.BEROctetString(new com.android.internal.org.bouncycastle.asn1.ASN1OctetString[]{aSN1OctetString}).toDLObject();
        }
        if (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
            com.android.internal.org.bouncycastle.asn1.ASN1OctetString aSN1OctetString2 = (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) object;
            if (aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                return aSN1OctetString2;
            }
            return (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1OctetString2.toDLObject();
        }
        if (object instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) object;
            if (aSN1TaggedObject instanceof com.android.internal.org.bouncycastle.asn1.BERTaggedObject) {
                return com.android.internal.org.bouncycastle.asn1.BEROctetString.fromSequence(aSN1Sequence);
            }
            return (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) com.android.internal.org.bouncycastle.asn1.BEROctetString.fromSequence(aSN1Sequence).toDLObject();
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + aSN1TaggedObject.getClass().getName());
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1OctetString getInstance(java.lang.Object obj) {
        if (obj == null || (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString)) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(fromByteArray((byte[]) obj));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + e.getMessage());
            }
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Encodable) {
            com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive = ((com.android.internal.org.bouncycastle.asn1.ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString) {
                return (com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Primitive;
            }
        }
        throw new java.lang.IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    public ASN1OctetString(byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.NullPointerException("'string' cannot be null");
        }
        this.string = bArr;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser
    public java.io.InputStream getOctetStream() {
        return new java.io.ByteArrayInputStream(this.string);
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1OctetStringParser parser() {
        return this;
    }

    public byte[] getOctets() {
        return this.string;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return com.android.internal.org.bouncycastle.util.Arrays.hashCode(getOctets());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1OctetString)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.string, ((com.android.internal.org.bouncycastle.asn1.ASN1OctetString) aSN1Primitive).string);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getLoadedObject() {
        return toASN1Primitive();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.string);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return new com.android.internal.org.bouncycastle.asn1.DEROctetString(this.string);
    }

    public java.lang.String toString() {
        return "#" + com.android.internal.org.bouncycastle.util.Strings.fromByteArray(com.android.internal.org.bouncycastle.util.encoders.Hex.encode(this.string));
    }
}
