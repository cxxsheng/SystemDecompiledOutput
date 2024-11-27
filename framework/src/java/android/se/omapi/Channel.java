package android.se.omapi;

/* loaded from: classes3.dex */
public final class Channel implements java.nio.channels.Channel {
    private static final java.lang.String TAG = "OMAPI.Channel";
    private final android.se.omapi.ISecureElementChannel mChannel;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.se.omapi.SEService mService;
    private android.se.omapi.Session mSession;

    Channel(android.se.omapi.SEService sEService, android.se.omapi.Session session, android.se.omapi.ISecureElementChannel iSecureElementChannel) {
        if (sEService == null || session == null || iSecureElementChannel == null) {
            throw new java.lang.IllegalArgumentException("Parameters cannot be null");
        }
        this.mService = sEService;
        this.mSession = session;
        this.mChannel = iSecureElementChannel;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (isOpen()) {
            synchronized (this.mLock) {
                try {
                    this.mChannel.close();
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "Error closing channel", e);
                }
            }
        }
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        if (!this.mService.isConnected()) {
            android.util.Log.e(TAG, "service not connected to system");
            return false;
        }
        try {
            return !this.mChannel.isClosed();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in isClosed()");
            return false;
        }
    }

    public boolean isBasicChannel() {
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service not connected to system");
        }
        try {
            return this.mChannel.isBasicChannel();
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e.getMessage());
        }
    }

    public byte[] transmit(byte[] bArr) throws java.io.IOException {
        byte[] transmit;
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service not connected to system");
        }
        synchronized (this.mLock) {
            try {
                transmit = this.mChannel.transmit(bArr);
                if (transmit == null) {
                    throw new java.io.IOException("Error in communicating with Secure Element");
                }
            } catch (android.os.RemoteException e) {
                throw new java.lang.IllegalStateException(e.getMessage());
            } catch (android.os.ServiceSpecificException e2) {
                throw new java.io.IOException(e2.getMessage());
            }
        }
        return transmit;
    }

    public android.se.omapi.Session getSession() {
        return this.mSession;
    }

    public byte[] getSelectResponse() {
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service not connected to system");
        }
        try {
            byte[] selectResponse = this.mChannel.getSelectResponse();
            if (selectResponse != null && selectResponse.length == 0) {
                return null;
            }
            return selectResponse;
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e.getMessage());
        }
    }

    public boolean selectNext() throws java.io.IOException {
        boolean selectNext;
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service not connected to system");
        }
        try {
            synchronized (this.mLock) {
                selectNext = this.mChannel.selectNext();
            }
            return selectNext;
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e.getMessage());
        } catch (android.os.ServiceSpecificException e2) {
            throw new java.io.IOException(e2.getMessage());
        }
    }
}
