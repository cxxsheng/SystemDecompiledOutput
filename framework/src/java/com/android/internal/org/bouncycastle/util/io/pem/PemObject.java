package com.android.internal.org.bouncycastle.util.io.pem;

/* loaded from: classes4.dex */
public class PemObject implements com.android.internal.org.bouncycastle.util.io.pem.PemObjectGenerator {
    private static final java.util.List EMPTY_LIST = java.util.Collections.unmodifiableList(new java.util.ArrayList());
    private byte[] content;
    private java.util.List headers;
    private java.lang.String type;

    public PemObject(java.lang.String str, byte[] bArr) {
        this(str, EMPTY_LIST, bArr);
    }

    public PemObject(java.lang.String str, java.util.List list, byte[] bArr) {
        this.type = str;
        this.headers = java.util.Collections.unmodifiableList(list);
        this.content = bArr;
    }

    public java.lang.String getType() {
        return this.type;
    }

    public java.util.List getHeaders() {
        return this.headers;
    }

    public byte[] getContent() {
        return this.content;
    }

    @Override // com.android.internal.org.bouncycastle.util.io.pem.PemObjectGenerator
    public com.android.internal.org.bouncycastle.util.io.pem.PemObject generate() throws com.android.internal.org.bouncycastle.util.io.pem.PemGenerationException {
        return this;
    }
}
