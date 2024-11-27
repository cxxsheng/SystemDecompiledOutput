package com.android.server.companion.transport;

/* loaded from: classes.dex */
class RawTransport extends com.android.server.companion.transport.Transport {
    private volatile boolean mStopped;

    RawTransport(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.Context context) {
        super(i, parcelFileDescriptor, context);
    }

    @Override // com.android.server.companion.transport.Transport
    void start() {
        if (com.android.server.companion.transport.Transport.DEBUG) {
            android.util.Slog.d("CDM_CompanionTransport", "Starting raw transport.");
        }
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.companion.transport.RawTransport$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.transport.RawTransport.this.lambda$start$0();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0() {
        while (!this.mStopped) {
            try {
                receiveMessage();
            } catch (java.io.IOException e) {
                if (!this.mStopped) {
                    android.util.Slog.w("CDM_CompanionTransport", "Trouble during transport", e);
                    close();
                    return;
                }
                return;
            }
        }
    }

    @Override // com.android.server.companion.transport.Transport
    void stop() {
        if (com.android.server.companion.transport.Transport.DEBUG) {
            android.util.Slog.d("CDM_CompanionTransport", "Stopping raw transport.");
        }
        this.mStopped = true;
    }

    @Override // com.android.server.companion.transport.Transport
    void close() {
        stop();
        if (com.android.server.companion.transport.Transport.DEBUG) {
            android.util.Slog.d("CDM_CompanionTransport", "Closing raw transport.");
        }
        libcore.io.IoUtils.closeQuietly(this.mRemoteIn);
        libcore.io.IoUtils.closeQuietly(this.mRemoteOut);
        super.close();
    }

    @Override // com.android.server.companion.transport.Transport
    protected void sendMessage(int i, int i2, @android.annotation.NonNull byte[] bArr) throws java.io.IOException {
        if (com.android.server.companion.transport.Transport.DEBUG) {
            android.util.Slog.e("CDM_CompanionTransport", "Sending message 0x" + java.lang.Integer.toHexString(i) + " sequence " + i2 + " length " + bArr.length + " to association " + this.mAssociationId);
        }
        synchronized (this.mRemoteOut) {
            this.mRemoteOut.write(java.nio.ByteBuffer.allocate(12).putInt(i).putInt(i2).putInt(bArr.length).array());
            this.mRemoteOut.write(bArr);
            this.mRemoteOut.flush();
        }
    }

    public java.lang.String toString() {
        return "RawTransport{mAssociationId=" + this.mAssociationId + '}';
    }

    private void receiveMessage() throws java.io.IOException {
        synchronized (this.mRemoteIn) {
            byte[] bArr = new byte[12];
            libcore.io.Streams.readFully(this.mRemoteIn, bArr);
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
            int i = wrap.getInt();
            int i2 = wrap.getInt();
            byte[] bArr2 = new byte[wrap.getInt()];
            libcore.io.Streams.readFully(this.mRemoteIn, bArr2);
            handleMessage(i, i2, bArr2);
        }
    }
}
