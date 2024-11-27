package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class ContextHubClient implements java.io.Closeable {
    private static final java.lang.String TAG = "ContextHubClient";
    private final android.hardware.location.ContextHubInfo mAttachedHub;
    private final dalvik.system.CloseGuard mCloseGuard;
    private final boolean mPersistent;
    private android.hardware.location.IContextHubClient mClientProxy = null;
    private final java.util.concurrent.atomic.AtomicBoolean mIsClosed = new java.util.concurrent.atomic.AtomicBoolean(false);
    private java.lang.Integer mId = null;

    ContextHubClient(android.hardware.location.ContextHubInfo contextHubInfo, boolean z) {
        this.mAttachedHub = contextHubInfo;
        this.mPersistent = z;
        if (this.mPersistent) {
            this.mCloseGuard = null;
        } else {
            this.mCloseGuard = dalvik.system.CloseGuard.get();
            this.mCloseGuard.open("ContextHubClient.close");
        }
    }

    synchronized void setClientProxy(android.hardware.location.IContextHubClient iContextHubClient) {
        java.util.Objects.requireNonNull(iContextHubClient, "IContextHubClient cannot be null");
        if (this.mClientProxy != null) {
            throw new java.lang.IllegalStateException("Cannot change client proxy multiple times");
        }
        this.mClientProxy = iContextHubClient;
        try {
            this.mId = java.lang.Integer.valueOf(this.mClientProxy.getId());
            notifyAll();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.location.ContextHubInfo getAttachedHub() {
        return this.mAttachedHub;
    }

    public int getId() {
        if (this.mId == null) {
            throw new java.lang.IllegalStateException("ID was not set");
        }
        return this.mId.intValue() & 65535;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (!this.mIsClosed.getAndSet(true)) {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.close();
            }
            try {
                this.mClientProxy.close();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int sendMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) {
        return doSendMessageToNanoApp(nanoAppMessage, null);
    }

    public android.hardware.location.ContextHubTransaction<java.lang.Void> sendReliableMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage) {
        if (!android.chre.flags.Flags.reliableMessageImplementation()) {
            return null;
        }
        android.hardware.location.ContextHubTransaction<java.lang.Void> contextHubTransaction = new android.hardware.location.ContextHubTransaction<>(5);
        if (!this.mAttachedHub.supportsReliableMessages()) {
            contextHubTransaction.setResponse(new android.hardware.location.ContextHubTransaction.Response<>(9, null));
            return contextHubTransaction;
        }
        int doSendMessageToNanoApp = doSendMessageToNanoApp(nanoAppMessage, android.hardware.location.ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction));
        if (doSendMessageToNanoApp != 0) {
            contextHubTransaction.setResponse(new android.hardware.location.ContextHubTransaction.Response<>(doSendMessageToNanoApp, null));
        }
        return contextHubTransaction;
    }

    private int doSendMessageToNanoApp(android.hardware.location.NanoAppMessage nanoAppMessage, android.hardware.location.IContextHubTransactionCallback iContextHubTransactionCallback) {
        java.util.Objects.requireNonNull(nanoAppMessage, "NanoAppMessage cannot be null");
        int maxPacketLengthBytes = this.mAttachedHub.getMaxPacketLengthBytes();
        byte[] messageBody = nanoAppMessage.getMessageBody();
        if (messageBody != null && messageBody.length > maxPacketLengthBytes) {
            android.util.Log.e(TAG, "Message (%d bytes) exceeds max payload length (%d bytes)".formatted(java.lang.Integer.valueOf(messageBody.length), java.lang.Integer.valueOf(maxPacketLengthBytes)));
            return 2;
        }
        try {
            if (iContextHubTransactionCallback == null) {
                return this.mClientProxy.sendMessageToNanoApp(nanoAppMessage);
            }
            return this.mClientProxy.sendReliableMessageToNanoApp(nanoAppMessage, iContextHubTransactionCallback);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            if (!this.mPersistent) {
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public synchronized void callbackFinished() {
        try {
            waitForClientProxy();
            this.mClientProxy.callbackFinished();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public synchronized void reliableMessageCallbackFinished(int i, byte b) {
        try {
            waitForClientProxy();
            this.mClientProxy.reliableMessageCallbackFinished(i, b);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void waitForClientProxy() {
        while (this.mClientProxy == null) {
            try {
                wait();
            } catch (java.lang.InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
            }
        }
    }
}
