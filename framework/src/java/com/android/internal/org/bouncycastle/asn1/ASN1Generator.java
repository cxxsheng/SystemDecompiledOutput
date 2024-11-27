package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public abstract class ASN1Generator {
    protected java.io.OutputStream _out;

    public abstract java.io.OutputStream getRawOutputStream();

    public ASN1Generator(java.io.OutputStream outputStream) {
        this._out = outputStream;
    }
}
