package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1Null extends com.android.internal.org.bouncycastle.asn1.ASN1Primitive {
    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    abstract void encode(com.android.internal.org.bouncycastle.asn1.ASN1OutputStream aSN1OutputStream, boolean z) throws java.io.IOException;

    ASN1Null() {
    }

    public static com.android.internal.org.bouncycastle.asn1.ASN1Null getInstance(java.lang.Object obj) {
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Null) {
            return (com.android.internal.org.bouncycastle.asn1.ASN1Null) obj;
        }
        if (obj != null) {
            try {
                return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("failed to construct NULL from byte[]: " + e.getMessage());
            } catch (java.lang.ClassCastException e2) {
                throw new java.lang.IllegalArgumentException("unknown object in getInstance(): " + obj.getClass().getName());
            }
        }
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return -1;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(com.android.internal.org.bouncycastle.asn1.ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof com.android.internal.org.bouncycastle.asn1.ASN1Null)) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        return "NULL";
    }
}
