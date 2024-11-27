package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class CMSAbsentContent implements com.android.internal.org.bouncycastle.cms.CMSTypedData, com.android.internal.org.bouncycastle.cms.CMSReadable {
    private final com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier type;

    public CMSAbsentContent() {
        this(com.android.internal.org.bouncycastle.asn1.cms.CMSObjectIdentifiers.data);
    }

    public CMSAbsentContent(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.type = aSN1ObjectIdentifier;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSReadable
    public java.io.InputStream getInputStream() {
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public void write(java.io.OutputStream outputStream) throws java.io.IOException, com.android.internal.org.bouncycastle.cms.CMSException {
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSProcessable
    public java.lang.Object getContent() {
        return null;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSTypedData
    public com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier getContentType() {
        return this.type;
    }
}
