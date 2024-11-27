package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public class CMSAttributeTableGenerationException extends com.android.internal.org.bouncycastle.cms.CMSRuntimeException {
    java.lang.Exception e;

    public CMSAttributeTableGenerationException(java.lang.String str) {
        super(str);
    }

    public CMSAttributeTableGenerationException(java.lang.String str, java.lang.Exception exc) {
        super(str);
        this.e = exc;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSRuntimeException
    public java.lang.Exception getUnderlyingException() {
        return this.e;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSRuntimeException, java.lang.Throwable
    public java.lang.Throwable getCause() {
        return this.e;
    }
}
