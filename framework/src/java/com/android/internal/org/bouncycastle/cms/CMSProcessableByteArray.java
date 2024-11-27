package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class CMSProcessableByteArray implements com.android.internal.org.bouncycastle.cms.CMSTypedData, com.android.internal.org.bouncycastle.cms.CMSReadable {
    private final byte[] bytes;
    private final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier type;

    public CMSProcessableByteArray(byte[] bArr) {
        this(com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers.data, bArr);
    }

    public CMSProcessableByteArray(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        this.type = aSN1ObjectIdentifier;
        this.bytes = bArr;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSReadable
    public java.io.InputStream getInputStream() {
        return new java.io.ByteArrayInputStream(this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public void write(java.io.OutputStream outputStream) throws java.io.IOException, com.android.internal.org.bouncycastle.cms.CMSException {
        outputStream.write(this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public java.lang.Object getContent() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.bytes);
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSTypedData
    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getContentType() {
        return this.type;
    }
}
