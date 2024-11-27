package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class LazyConstructionEnumeration implements java.util.Enumeration {
    private com.android.internal.org.bouncycastle.asn1.ASN1InputStream aIn;
    private java.lang.Object nextObj = readObject();

    public LazyConstructionEnumeration(byte[] bArr) {
        this.aIn = new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(bArr, true);
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.nextObj != null;
    }

    @Override // java.util.Enumeration
    public java.lang.Object nextElement() {
        if (this.nextObj != null) {
            java.lang.Object obj = this.nextObj;
            this.nextObj = readObject();
            return obj;
        }
        throw new java.util.NoSuchElementException();
    }

    private java.lang.Object readObject() {
        try {
            return this.aIn.readObject();
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.asn1.ASN1ParsingException("malformed DER construction: " + e, e);
        }
    }
}
