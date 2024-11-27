package com.android.internal.infra;

/* loaded from: classes4.dex */
public abstract class RemoteStream<RES, IOSTREAM extends java.io.Closeable> extends com.android.internal.infra.AndroidFuture<RES> implements java.lang.Runnable {
    private final com.android.internal.util.FunctionalUtils.ThrowingFunction<IOSTREAM, RES> mHandleStream;
    private volatile android.os.ParcelFileDescriptor mLocalPipe;

    protected abstract IOSTREAM createStream(android.os.ParcelFileDescriptor parcelFileDescriptor);

    public static <R> com.android.internal.infra.AndroidFuture<R> receiveBytes(com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.os.ParcelFileDescriptor> throwingConsumer, com.android.internal.util.FunctionalUtils.ThrowingFunction<java.io.InputStream, R> throwingFunction) {
        return new com.android.internal.infra.RemoteStream<R, java.io.InputStream>(throwingConsumer, throwingFunction, android.os.AsyncTask.THREAD_POOL_EXECUTOR, true) { // from class: com.android.internal.infra.RemoteStream.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.android.internal.infra.RemoteStream
            public java.io.InputStream createStream(android.os.ParcelFileDescriptor parcelFileDescriptor) {
                return new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            }
        };
    }

    public static com.android.internal.infra.AndroidFuture<byte[]> receiveBytes(com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.os.ParcelFileDescriptor> throwingConsumer) {
        return receiveBytes(throwingConsumer, new com.android.internal.util.FunctionalUtils.ThrowingFunction() { // from class: com.android.internal.infra.RemoteStream$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
            public final java.lang.Object applyOrThrow(java.lang.Object obj) {
                return com.android.internal.infra.RemoteStream.readAll((java.io.InputStream) obj);
            }
        });
    }

    public static byte[] readAll(java.io.InputStream inputStream) throws java.io.IOException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        byte[] bArr = new byte[16384];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static <R> com.android.internal.infra.AndroidFuture<R> sendBytes(com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.os.ParcelFileDescriptor> throwingConsumer, com.android.internal.util.FunctionalUtils.ThrowingFunction<java.io.OutputStream, R> throwingFunction) {
        return new com.android.internal.infra.RemoteStream<R, java.io.OutputStream>(throwingConsumer, throwingFunction, android.os.AsyncTask.THREAD_POOL_EXECUTOR, false) { // from class: com.android.internal.infra.RemoteStream.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.android.internal.infra.RemoteStream
            public java.io.OutputStream createStream(android.os.ParcelFileDescriptor parcelFileDescriptor) {
                return new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
            }
        };
    }

    public static com.android.internal.infra.AndroidFuture<java.lang.Void> sendBytes(com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.os.ParcelFileDescriptor> throwingConsumer, final com.android.internal.util.FunctionalUtils.ThrowingConsumer<java.io.OutputStream> throwingConsumer2) {
        return sendBytes(throwingConsumer, new com.android.internal.util.FunctionalUtils.ThrowingFunction() { // from class: com.android.internal.infra.RemoteStream$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
            public final java.lang.Object applyOrThrow(java.lang.Object obj) {
                return com.android.internal.infra.RemoteStream.lambda$sendBytes$0(com.android.internal.util.FunctionalUtils.ThrowingConsumer.this, (java.io.OutputStream) obj);
            }
        });
    }

    static /* synthetic */ java.lang.Void lambda$sendBytes$0(com.android.internal.util.FunctionalUtils.ThrowingConsumer throwingConsumer, java.io.OutputStream outputStream) throws java.lang.Exception {
        throwingConsumer.acceptOrThrow(outputStream);
        return null;
    }

    public static com.android.internal.infra.AndroidFuture<java.lang.Void> sendBytes(com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.os.ParcelFileDescriptor> throwingConsumer, final byte[] bArr) {
        return sendBytes(throwingConsumer, new com.android.internal.util.FunctionalUtils.ThrowingFunction() { // from class: com.android.internal.infra.RemoteStream$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.FunctionalUtils.ThrowingFunction
            public final java.lang.Object applyOrThrow(java.lang.Object obj) {
                return com.android.internal.infra.RemoteStream.lambda$sendBytes$1(bArr, (java.io.OutputStream) obj);
            }
        });
    }

    static /* synthetic */ java.lang.Void lambda$sendBytes$1(byte[] bArr, java.io.OutputStream outputStream) throws java.lang.Exception {
        outputStream.write(bArr);
        return null;
    }

    private RemoteStream(com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.os.ParcelFileDescriptor> throwingConsumer, com.android.internal.util.FunctionalUtils.ThrowingFunction<IOSTREAM, RES> throwingFunction, java.util.concurrent.Executor executor, boolean z) {
        this.mHandleStream = throwingFunction;
        try {
            android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
            android.os.ParcelFileDescriptor parcelFileDescriptor = createPipe[z ? (char) 1 : (char) 0];
            try {
                throwingConsumer.acceptOrThrow(parcelFileDescriptor);
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
                this.mLocalPipe = createPipe[z ? (char) 0 : (char) 1];
                executor.execute(this);
                orTimeout(30L, java.util.concurrent.TimeUnit.SECONDS);
            } finally {
            }
        } catch (java.lang.Throwable th) {
            completeExceptionally(th);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            IOSTREAM createStream = createStream(this.mLocalPipe);
            try {
                complete(this.mHandleStream.applyOrThrow(createStream));
                if (createStream != null) {
                    createStream.close();
                }
            } finally {
            }
        } catch (java.lang.Throwable th) {
            completeExceptionally(th);
        }
    }

    @Override // com.android.internal.infra.AndroidFuture
    protected void onCompleted(RES res, java.lang.Throwable th) {
        super.onCompleted(res, th);
        libcore.io.IoUtils.closeQuietly(this.mLocalPipe);
    }
}
