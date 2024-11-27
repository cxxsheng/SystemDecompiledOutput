package com.android.internal.org.bouncycastle.cms;

/* loaded from: classes4.dex */
public interface CMSProcessable {
    java.lang.Object getContent();

    void write(java.io.OutputStream outputStream) throws java.io.IOException, com.android.internal.org.bouncycastle.cms.CMSException;
}
