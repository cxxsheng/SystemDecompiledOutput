package android.se.omapi;

/* loaded from: classes3.dex */
public final class Reader {
    private static final java.lang.String TAG = "OMAPI.Reader";
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.lang.String mName;
    private android.se.omapi.ISecureElementReader mReader;
    private final android.se.omapi.SEService mService;

    Reader(android.se.omapi.SEService sEService, java.lang.String str, android.se.omapi.ISecureElementReader iSecureElementReader) {
        if (iSecureElementReader == null || sEService == null || str == null) {
            throw new java.lang.IllegalArgumentException("Parameters cannot be null");
        }
        this.mName = str;
        this.mService = sEService;
        this.mReader = iSecureElementReader;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public android.se.omapi.Session openSession() throws java.io.IOException {
        android.se.omapi.Session session;
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service is not connected");
        }
        synchronized (this.mLock) {
            try {
                try {
                    android.se.omapi.ISecureElementSession openSession = this.mReader.openSession();
                    if (openSession == null) {
                        throw new java.io.IOException("service session is null.");
                    }
                    session = new android.se.omapi.Session(this.mService, openSession, this);
                } catch (android.os.RemoteException e) {
                    throw new java.lang.IllegalStateException(e.getMessage());
                } catch (android.os.ServiceSpecificException e2) {
                    throw new java.io.IOException(e2.getMessage());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return session;
    }

    public boolean isSecureElementPresent() {
        if (!this.mService.isConnected()) {
            throw new java.lang.IllegalStateException("service is not connected");
        }
        try {
            return this.mReader.isSecureElementPresent();
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Error in isSecureElementPresent()");
        }
    }

    public android.se.omapi.SEService getSEService() {
        return this.mService;
    }

    public void closeSessions() {
        if (!this.mService.isConnected()) {
            android.util.Log.e(TAG, "service is not connected");
            return;
        }
        synchronized (this.mLock) {
            try {
                this.mReader.closeSessions();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @android.annotation.SystemApi
    public boolean reset() {
        boolean reset;
        if (!this.mService.isConnected()) {
            android.util.Log.e(TAG, "service is not connected");
            return false;
        }
        synchronized (this.mLock) {
            try {
                try {
                    closeSessions();
                    reset = this.mReader.reset();
                } catch (android.os.RemoteException e) {
                    return false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return reset;
    }
}
