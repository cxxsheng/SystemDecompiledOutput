package com.android.internal.os;

/* loaded from: classes4.dex */
public class ByteTransferPipe extends com.android.internal.os.TransferPipe {
    static final java.lang.String TAG = "ByteTransferPipe";
    private java.io.ByteArrayOutputStream mOutputStream;

    public ByteTransferPipe() throws java.io.IOException {
    }

    public ByteTransferPipe(java.lang.String str) throws java.io.IOException {
        super(str, TAG);
    }

    @Override // com.android.internal.os.TransferPipe
    protected java.io.OutputStream getNewOutputStream() {
        this.mOutputStream = new java.io.ByteArrayOutputStream();
        return this.mOutputStream;
    }

    public byte[] get() throws java.io.IOException {
        go(null);
        return this.mOutputStream.toByteArray();
    }
}
