package android.se.omapi;

/* loaded from: classes3.dex */
public final class Session {
    private static final java.lang.String TAG = "OMAPI.Session";
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.se.omapi.Reader mReader;
    private final android.se.omapi.SEService mService;
    private final android.se.omapi.ISecureElementSession mSession;

    Session(android.se.omapi.SEService sEService, android.se.omapi.ISecureElementSession iSecureElementSession, android.se.omapi.Reader reader) {
        if (sEService == null || reader == null || iSecureElementSession == null) {
            throw new java.lang.IllegalArgumentException("Parameters cannot be null");
        }
        this.mService = sEService;
        this.mReader = reader;
        this.mSession = iSecureElementSession;
    }

    public android.se.omapi.Reader getReader() {
        return this.mReader;
    }

    public byte[] getATR() {
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service not connected to system");
        }
        try {
            return this.mSession.getAtr();
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e.getMessage());
        }
    }

    public void close() {
        if (!this.mService.isConnected()) {
            android.util.Log.e(TAG, "service not connected to system");
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mSession.close();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error closing session", e);
            }
        }
    }

    public boolean isClosed() {
        try {
            return this.mSession.isClosed();
        } catch (android.os.RemoteException e) {
            return true;
        }
    }

    public void closeChannels() {
        if (!this.mService.isConnected()) {
            android.util.Log.e(TAG, "service not connected to system");
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mSession.closeChannels();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error closing channels", e);
            }
        }
    }

    public android.se.omapi.Channel openBasicChannel(byte[] bArr, byte b) throws java.io.IOException {
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service not connected to system");
        }
        synchronized (this.mLock) {
            try {
                try {
                    android.se.omapi.ISecureElementChannel openBasicChannel = this.mSession.openBasicChannel(bArr, b, this.mReader.getSEService().getListener());
                    if (openBasicChannel == null) {
                        return null;
                    }
                    return new android.se.omapi.Channel(this.mService, this, openBasicChannel);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.IllegalStateException(e.getMessage());
                } catch (android.os.ServiceSpecificException e2) {
                    if (e2.errorCode == 1) {
                        throw new java.io.IOException(e2.getMessage());
                    }
                    if (e2.errorCode == 2) {
                        throw new java.util.NoSuchElementException(e2.getMessage());
                    }
                    throw new java.lang.IllegalStateException(e2.getMessage());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.se.omapi.Channel openBasicChannel(byte[] bArr) throws java.io.IOException {
        return openBasicChannel(bArr, (byte) 0);
    }

    public android.se.omapi.Channel openLogicalChannel(byte[] bArr, byte b) throws java.io.IOException {
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service not connected to system");
        }
        synchronized (this.mLock) {
            try {
                try {
                    android.se.omapi.ISecureElementChannel openLogicalChannel = this.mSession.openLogicalChannel(bArr, b, this.mReader.getSEService().getListener());
                    if (openLogicalChannel == null) {
                        return null;
                    }
                    return new android.se.omapi.Channel(this.mService, this, openLogicalChannel);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.IllegalStateException(e.getMessage());
                } catch (android.os.ServiceSpecificException e2) {
                    if (e2.errorCode == 1) {
                        throw new java.io.IOException(e2.getMessage());
                    }
                    if (e2.errorCode == 2) {
                        throw new java.util.NoSuchElementException(e2.getMessage());
                    }
                    throw new java.lang.IllegalStateException(e2.getMessage());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.se.omapi.Channel openLogicalChannel(byte[] bArr) throws java.io.IOException {
        return openLogicalChannel(bArr, (byte) 0);
    }
}
