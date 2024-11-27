package com.android.internal.org.bouncycastle.its.asn1;

/* loaded from: classes4.dex */
public class SspRange extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    private final com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange bitmapSspRange;
    private final boolean isAll;
    private final com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString opaque;

    private SspRange() {
        this.isAll = true;
        this.opaque = null;
        this.bitmapSspRange = null;
    }

    private SspRange(com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString sequenceOfOctetString) {
        this.isAll = false;
        com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange bitmapSspRange = null;
        if (sequenceOfOctetString.size() != 2) {
            this.opaque = sequenceOfOctetString;
            this.bitmapSspRange = null;
        } else {
            this.opaque = com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString.getInstance(sequenceOfOctetString);
            try {
                bitmapSspRange = com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange.getInstance(sequenceOfOctetString);
            } catch (java.lang.IllegalArgumentException e) {
            }
            this.bitmapSspRange = bitmapSspRange;
        }
    }

    public SspRange(com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange bitmapSspRange) {
        this.isAll = false;
        this.bitmapSspRange = bitmapSspRange;
        this.opaque = null;
    }

    public static com.android.internal.org.bouncycastle.its.asn1.SspRange getInstance(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.its.asn1.SspRange) {
            return (com.android.internal.org.bouncycastle.its.asn1.SspRange) obj;
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Null) {
            return new com.android.internal.org.bouncycastle.its.asn1.SspRange();
        }
        if (obj instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            return new com.android.internal.org.bouncycastle.its.asn1.SspRange(com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString.getInstance(obj));
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(com.android.internal.org.bouncycastle.asn1.ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("unable to parse encoded general name");
            }
        }
        throw new java.lang.IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public boolean isAll() {
        return this.isAll;
    }

    public boolean maybeOpaque() {
        return this.opaque != null;
    }

    public com.android.internal.org.bouncycastle.its.asn1.BitmapSspRange getBitmapSspRange() {
        return this.bitmapSspRange;
    }

    public com.android.internal.org.bouncycastle.its.asn1.SequenceOfOctetString getOpaque() {
        return this.opaque;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        if (this.isAll) {
            return com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE;
        }
        if (this.bitmapSspRange != null) {
            return this.bitmapSspRange.toASN1Primitive();
        }
        return this.opaque.toASN1Primitive();
    }
}
