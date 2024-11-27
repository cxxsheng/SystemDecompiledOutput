package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class PKCS7ProcessableObject implements com.android.internal.org.bouncycastle.cms.CMSTypedData {
    private final com.android.internal.org.bouncycastle.asn1.ASN1Encodable structure;
    private final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier type;

    public PKCS7ProcessableObject(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.ASN1Encodable aSN1Encodable) {
        this.type = aSN1ObjectIdentifier;
        this.structure = aSN1Encodable;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSTypedData
    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getContentType() {
        return this.type;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public void write(java.io.OutputStream outputStream) throws java.io.IOException, com.android.internal.org.bouncycastle.cms.CMSException {
        if (this.structure instanceof com.android.internal.org.bouncycastle.asn1.ASN1Sequence) {
            java.util.Iterator<com.android.internal.org.bouncycastle.asn1.ASN1Encodable> it = com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(this.structure).iterator();
            while (it.hasNext()) {
                outputStream.write(it.next().toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
            }
        } else {
            byte[] encoded = this.structure.toASN1Primitive().getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
            int i = 1;
            while ((encoded[i] & 255) > 127) {
                i++;
            }
            int i2 = i + 1;
            outputStream.write(encoded, i2, encoded.length - i2);
        }
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public java.lang.Object getContent() {
        return this.structure;
    }
}
