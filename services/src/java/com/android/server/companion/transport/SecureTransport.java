package com.android.server.companion.transport;

/* loaded from: classes.dex */
class SecureTransport extends com.android.server.companion.transport.Transport implements com.android.server.companion.securechannel.SecureChannel.Callback {
    private final java.util.concurrent.BlockingQueue<byte[]> mRequestQueue;
    private final com.android.server.companion.securechannel.SecureChannel mSecureChannel;
    private volatile boolean mShouldProcessRequests;

    SecureTransport(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.Context context) {
        super(i, parcelFileDescriptor, context);
        this.mShouldProcessRequests = false;
        this.mRequestQueue = new java.util.concurrent.ArrayBlockingQueue(500);
        this.mSecureChannel = new com.android.server.companion.securechannel.SecureChannel(this.mRemoteIn, this.mRemoteOut, this, context);
    }

    SecureTransport(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.Context context, byte[] bArr, com.android.server.companion.securechannel.AttestationVerifier attestationVerifier) {
        super(i, parcelFileDescriptor, context);
        this.mShouldProcessRequests = false;
        this.mRequestQueue = new java.util.concurrent.ArrayBlockingQueue(500);
        this.mSecureChannel = new com.android.server.companion.securechannel.SecureChannel(this.mRemoteIn, this.mRemoteOut, this, bArr, attestationVerifier);
    }

    @Override // com.android.server.companion.transport.Transport
    void start() {
        this.mSecureChannel.start();
    }

    @Override // com.android.server.companion.transport.Transport
    void stop() {
        this.mSecureChannel.stop();
        this.mShouldProcessRequests = false;
    }

    @Override // com.android.server.companion.transport.Transport
    void close() {
        this.mSecureChannel.close();
        this.mShouldProcessRequests = false;
        super.close();
    }

    @Override // com.android.server.companion.transport.Transport
    protected void sendMessage(int i, int i2, @android.annotation.NonNull byte[] bArr) throws java.io.IOException {
        if (!this.mShouldProcessRequests) {
            establishSecureConnection();
        }
        if (com.android.server.companion.transport.Transport.DEBUG) {
            android.util.Slog.d("CDM_CompanionTransport", "Queueing message 0x" + java.lang.Integer.toHexString(i) + " sequence " + i2 + " length " + bArr.length + " to association " + this.mAssociationId);
        }
        try {
            this.mRequestQueue.add(java.nio.ByteBuffer.allocate(bArr.length + 12).putInt(i).putInt(i2).putInt(bArr.length).put(bArr).array());
        } catch (java.lang.IllegalStateException e) {
            android.util.Slog.w("CDM_CompanionTransport", "Failed to queue message 0x" + java.lang.Integer.toHexString(i) + " . Request buffer is full; detaching transport.", e);
            close();
        }
    }

    private void establishSecureConnection() {
        android.util.Slog.d("CDM_CompanionTransport", "Establishing secure connection.");
        try {
            this.mSecureChannel.establishSecureConnection();
        } catch (java.lang.Exception e) {
            android.util.Slog.e("CDM_CompanionTransport", "Failed to initiate secure channel handshake.", e);
            close();
        }
    }

    @Override // com.android.server.companion.securechannel.SecureChannel.Callback
    public void onSecureConnection() {
        this.mShouldProcessRequests = true;
        android.util.Slog.d("CDM_CompanionTransport", "Secure connection established.");
        new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.companion.transport.SecureTransport$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.companion.transport.SecureTransport.this.lambda$onSecureConnection$0();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSecureConnection$0() {
        while (this.mShouldProcessRequests) {
            try {
                this.mSecureChannel.sendSecureMessage(this.mRequestQueue.take());
            } catch (java.lang.Exception e) {
                android.util.Slog.e("CDM_CompanionTransport", "Failed to send secure message.", e);
                close();
            }
        }
    }

    @Override // com.android.server.companion.securechannel.SecureChannel.Callback
    public void onSecureMessageReceived(byte[] bArr) {
        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
        int i = wrap.getInt();
        int i2 = wrap.getInt();
        byte[] bArr2 = new byte[wrap.getInt()];
        wrap.get(bArr2);
        try {
            handleMessage(i, i2, bArr2);
        } catch (java.io.IOException e) {
        }
    }

    @Override // com.android.server.companion.securechannel.SecureChannel.Callback
    public void onError(java.lang.Throwable th) {
        android.util.Slog.e("CDM_CompanionTransport", "Secure transport encountered an error.", th);
        if (this.mSecureChannel.isStopped()) {
            close();
        }
    }

    public java.lang.String toString() {
        return "SecureTransport{mAssociationId=" + this.mAssociationId + ", mSecureChannel=" + this.mSecureChannel + '}';
    }
}
