package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1External extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    protected com.android.internal.org.bouncycastle.asn1.ASN1Primitive dataValueDescriptor;
    protected com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier directReference;
    protected int encoding;
    protected com.android.internal.org.bouncycastle.asn1.ASN1Primitive externalContent;
    protected com.android.internal.org.bouncycastle.asn1.ASN1Integer indirectReference;

    public ASN1External(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector) {
        int i = 0;
        com.android.internal.org.bouncycastle.asn1.ASN1Primitive objFromVector = getObjFromVector(aSN1EncodableVector, 0);
        if (objFromVector instanceof com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) {
            this.directReference = (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) objFromVector;
            objFromVector = getObjFromVector(aSN1EncodableVector, 1);
            i = 1;
        }
        if (objFromVector instanceof com.android.internal.org.bouncycastle.asn1.ASN1Integer) {
            this.indirectReference = (com.android.internal.org.bouncycastle.asn1.ASN1Integer) objFromVector;
            i++;
            objFromVector = getObjFromVector(aSN1EncodableVector, i);
        }
        if (!(objFromVector instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject)) {
            this.dataValueDescriptor = objFromVector;
            i++;
            objFromVector = getObjFromVector(aSN1EncodableVector, i);
        }
        if (aSN1EncodableVector.size() != i + 1) {
            throw new java.lang.IllegalArgumentException("input vector too large");
        }
        if (!(objFromVector instanceof com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject)) {
            throw new java.lang.IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
        }
        com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject aSN1TaggedObject = (com.android.internal.org.bouncycastle.asn1.ASN1TaggedObject) objFromVector;
        setEncoding(aSN1TaggedObject.getTagNo());
        this.externalContent = aSN1TaggedObject.getObject();
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1Primitive getObjFromVector(com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector, int i) {
        if (aSN1EncodableVector.size() <= i) {
            throw new java.lang.IllegalArgumentException("too few objects in input vector");
        }
        return aSN1EncodableVector.get(i).toASN1Primitive();
    }

    public ASN1External(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer, com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive, com.android.internal.org.bouncycastle.asn1.DERTaggedObject dERTaggedObject) {
        this(aSN1ObjectIdentifier, aSN1Integer, aSN1Primitive, dERTaggedObject.getTagNo(), dERTaggedObject.toASN1Primitive());
    }

    public ASN1External(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer, com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive, int i, com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive2) {
        setDirectReference(aSN1ObjectIdentifier);
        setIndirectReference(aSN1Integer);
        setDataValueDescriptor(aSN1Primitive);
        setEncoding(i);
        setExternalContent(aSN1Primitive2.toASN1Primitive());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDERObject() {
        return new com.android.internal.org.bouncycastle.asn1.DERExternal(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    com.android.internal.org.bouncycastle.asn1.ASN1Primitive toDLObject() {
        return new com.android.internal.org.bouncycastle.asn1.DLExternal(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        int i;
        if (this.directReference == null) {
            i = 0;
        } else {
            i = this.directReference.hashCode();
        }
        if (this.indirectReference != null) {
            i ^= this.indirectReference.hashCode();
        }
        if (this.dataValueDescriptor != null) {
            i ^= this.dataValueDescriptor.hashCode();
        }
        return i ^ this.externalContent.hashCode();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean isConstructed() {
        return true;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws java.io.IOException {
        return getEncoded().length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1External)) {
            return false;
        }
        if (this == aSN1Primitive) {
            return true;
        }
        com.android.internal.org.bouncycastle.asn1.ASN1External aSN1External = (com.android.internal.org.bouncycastle.asn1.ASN1External) aSN1Primitive;
        if (this.directReference != null && (aSN1External.directReference == null || !aSN1External.directReference.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) this.directReference))) {
            return false;
        }
        if (this.indirectReference != null && (aSN1External.indirectReference == null || !aSN1External.indirectReference.equals((com.android.internal.org.bouncycastle.asn1.ASN1Primitive) this.indirectReference))) {
            return false;
        }
        if (this.dataValueDescriptor == null || (aSN1External.dataValueDescriptor != null && aSN1External.dataValueDescriptor.equals(this.dataValueDescriptor))) {
            return this.externalContent.equals(aSN1External.externalContent);
        }
        return false;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getDataValueDescriptor() {
        return this.dataValueDescriptor;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getDirectReference() {
        return this.directReference;
    }

    public int getEncoding() {
        return this.encoding;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive getExternalContent() {
        return this.externalContent;
    }

    public com.android.internal.org.bouncycastle.asn1.ASN1Integer getIndirectReference() {
        return this.indirectReference;
    }

    private void setDataValueDescriptor(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        this.dataValueDescriptor = aSN1Primitive;
    }

    private void setDirectReference(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.directReference = aSN1ObjectIdentifier;
    }

    private void setEncoding(int i) {
        if (i < 0 || i > 2) {
            throw new java.lang.IllegalArgumentException("invalid encoding value: " + i);
        }
        this.encoding = i;
    }

    private void setExternalContent(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        this.externalContent = aSN1Primitive;
    }

    private void setIndirectReference(com.android.internal.org.bouncycastle.asn1.ASN1Integer aSN1Integer) {
        this.indirectReference = aSN1Integer;
    }
}
