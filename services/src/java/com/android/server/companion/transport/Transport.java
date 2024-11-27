package com.android.server.companion.transport;

/* loaded from: classes.dex */
public abstract class Transport {
    protected static final boolean DEBUG = android.os.Build.IS_DEBUGGABLE;
    protected static final int HEADER_LENGTH = 12;
    static final int MESSAGE_RESPONSE_FAILURE = 863004019;
    static final int MESSAGE_RESPONSE_SUCCESS = 864257383;
    protected static final java.lang.String TAG = "CDM_CompanionTransport";
    protected final int mAssociationId;
    protected final android.content.Context mContext;
    protected final android.os.ParcelFileDescriptor mFd;
    private com.android.server.companion.transport.Transport.OnTransportClosedListener mOnTransportClosed;
    protected final java.io.InputStream mRemoteIn;
    protected final java.io.OutputStream mRemoteOut;

    @com.android.internal.annotations.GuardedBy({"mPendingRequests"})
    protected final android.util.SparseArray<java.util.concurrent.CompletableFuture<byte[]>> mPendingRequests = new android.util.SparseArray<>();
    protected final java.util.concurrent.atomic.AtomicInteger mNextSequence = new java.util.concurrent.atomic.AtomicInteger();
    private final java.util.Map<java.lang.Integer, android.companion.IOnMessageReceivedListener> mListeners = new java.util.HashMap();

    @java.lang.FunctionalInterface
    interface OnTransportClosedListener {
        void onClosed(com.android.server.companion.transport.Transport transport);
    }

    protected abstract void sendMessage(int i, int i2, @android.annotation.NonNull byte[] bArr) throws java.io.IOException;

    abstract void start();

    abstract void stop();

    private static boolean isRequest(int i) {
        return (i & android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK) == 1660944384;
    }

    private static boolean isResponse(int i) {
        return (i & android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK) == 855638016;
    }

    private static boolean isOneway(int i) {
        return (i & android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK) == 1124073472;
    }

    Transport(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, android.content.Context context) {
        this.mAssociationId = i;
        this.mFd = parcelFileDescriptor;
        this.mRemoteIn = new android.os.ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        this.mRemoteOut = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
        this.mContext = context;
    }

    public void addListener(int i, android.companion.IOnMessageReceivedListener iOnMessageReceivedListener) {
        this.mListeners.put(java.lang.Integer.valueOf(i), iOnMessageReceivedListener);
    }

    public int getAssociationId() {
        return this.mAssociationId;
    }

    protected android.os.ParcelFileDescriptor getFd() {
        return this.mFd;
    }

    void close() {
        if (this.mOnTransportClosed != null) {
            this.mOnTransportClosed.onClosed(this);
        }
    }

    public java.util.concurrent.Future<byte[]> sendMessage(int i, byte[] bArr) {
        java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        if (isOneway(i)) {
            return sendAndForget(i, bArr);
        }
        if (isRequest(i)) {
            return requestForResponse(i, bArr);
        }
        android.util.Slog.w(TAG, "Failed to send message 0x" + java.lang.Integer.toHexString(i));
        completableFuture.completeExceptionally(new java.lang.IllegalArgumentException("The message being sent must be either a one-way or a request."));
        return completableFuture;
    }

    @java.lang.Deprecated
    public java.util.concurrent.Future<byte[]> requestForResponse(int i, byte[] bArr) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Requesting for response");
        }
        int incrementAndGet = this.mNextSequence.incrementAndGet();
        java.util.concurrent.CompletableFuture<byte[]> completableFuture = new java.util.concurrent.CompletableFuture<>();
        synchronized (this.mPendingRequests) {
            this.mPendingRequests.put(incrementAndGet, completableFuture);
        }
        try {
            sendMessage(i, incrementAndGet, bArr);
        } catch (java.io.IOException e) {
            synchronized (this.mPendingRequests) {
                this.mPendingRequests.remove(incrementAndGet);
                completableFuture.completeExceptionally(e);
            }
        }
        return completableFuture;
    }

    private java.util.concurrent.Future<byte[]> sendAndForget(int i, byte[] bArr) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Sending a one-way message");
        }
        java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        try {
            sendMessage(i, -1, bArr);
            completableFuture.complete(null);
        } catch (java.io.IOException e) {
            completableFuture.completeExceptionally(e);
        }
        return completableFuture;
    }

    protected final void handleMessage(int i, int i2, @android.annotation.NonNull byte[] bArr) throws java.io.IOException {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Received message 0x" + java.lang.Integer.toHexString(i) + " sequence " + i2 + " length " + bArr.length + " from association " + this.mAssociationId);
        }
        if (isOneway(i)) {
            processOneway(i, bArr);
            return;
        }
        if (isRequest(i)) {
            try {
                processRequest(i, i2, bArr);
                return;
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed to respond to 0x" + java.lang.Integer.toHexString(i), e);
                return;
            }
        }
        if (isResponse(i)) {
            processResponse(i, i2, bArr);
            return;
        }
        android.util.Slog.w(TAG, "Unknown message 0x" + java.lang.Integer.toHexString(i));
    }

    private void processOneway(int i, byte[] bArr) {
        switch (i) {
            case 1131446919:
            case 1132491640:
            case 1132755335:
                callback(i, bArr);
                break;
            default:
                android.util.Slog.w(TAG, "Ignoring unknown message 0x" + java.lang.Integer.toHexString(i));
                break;
        }
    }

    private void processRequest(int i, int i2, byte[] bArr) throws java.io.IOException {
        switch (i) {
            case 1667729539:
            case 1669494629:
                callback(i, bArr);
                sendMessage(MESSAGE_RESPONSE_SUCCESS, i2, libcore.util.EmptyArray.BYTE);
                break;
            case 1669362552:
                sendMessage(MESSAGE_RESPONSE_SUCCESS, i2, bArr);
                break;
            case 1669491075:
                try {
                    callback(i, bArr);
                    sendMessage(MESSAGE_RESPONSE_SUCCESS, i2, libcore.util.EmptyArray.BYTE);
                    break;
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(TAG, "Failed to restore permissions");
                    sendMessage(MESSAGE_RESPONSE_FAILURE, i2, libcore.util.EmptyArray.BYTE);
                    return;
                }
            default:
                android.util.Slog.w(TAG, "Unknown request 0x" + java.lang.Integer.toHexString(i));
                sendMessage(MESSAGE_RESPONSE_FAILURE, i2, libcore.util.EmptyArray.BYTE);
                break;
        }
    }

    private void callback(int i, byte[] bArr) {
        if (this.mListeners.containsKey(java.lang.Integer.valueOf(i))) {
            try {
                this.mListeners.get(java.lang.Integer.valueOf(i)).onMessageReceived(getAssociationId(), bArr);
                android.util.Slog.d(TAG, "Message 0x" + java.lang.Integer.toHexString(i) + " is received from associationId " + this.mAssociationId + ", sending data length " + bArr.length + " to the listener.");
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void processResponse(int i, int i2, byte[] bArr) {
        java.util.concurrent.CompletableFuture completableFuture;
        synchronized (this.mPendingRequests) {
            completableFuture = (java.util.concurrent.CompletableFuture) this.mPendingRequests.removeReturnOld(i2);
        }
        if (completableFuture == null) {
            android.util.Slog.w(TAG, "Ignoring unknown sequence " + i2);
            return;
        }
        switch (i) {
            case MESSAGE_RESPONSE_FAILURE /* 863004019 */:
                completableFuture.completeExceptionally(new java.lang.RuntimeException("Remote failure"));
                return;
            case MESSAGE_RESPONSE_SUCCESS /* 864257383 */:
                completableFuture.complete(bArr);
                return;
            default:
                android.util.Slog.w(TAG, "Ignoring unknown response 0x" + java.lang.Integer.toHexString(i));
                return;
        }
    }

    void setOnTransportClosedListener(com.android.server.companion.transport.Transport.OnTransportClosedListener onTransportClosedListener) {
        this.mOnTransportClosed = onTransportClosedListener;
    }
}
