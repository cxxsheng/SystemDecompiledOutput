package android.se.omapi;

/* loaded from: classes3.dex */
public final class SEService {
    public static final java.lang.String ACTION_SECURE_ELEMENT_STATE_CHANGED = "android.se.omapi.action.SECURE_ELEMENT_STATE_CHANGED";
    public static final java.lang.String EXTRA_READER_NAME = "android.se.omapi.extra.READER_NAME";
    public static final java.lang.String EXTRA_READER_STATE = "android.se.omapi.extra.READER_STATE";
    public static final int IO_ERROR = 1;
    public static final int NO_SUCH_ELEMENT_ERROR = 2;
    private static final java.lang.String TAG = "OMAPI.SEService";
    private static final java.lang.String UICC_TERMINAL = "SIM";
    private android.content.ServiceConnection mConnection;
    private final android.content.Context mContext;
    private volatile android.se.omapi.ISecureElementService mSecureElementService;
    private android.se.omapi.SEService.SEListener mSEListener = new android.se.omapi.SEService.SEListener();
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.HashMap<java.lang.String, android.se.omapi.Reader> mReaders = new java.util.HashMap<>();

    public interface OnConnectedListener {
        void onConnected();
    }

    private class SEListener extends android.se.omapi.ISecureElementListener.Stub {
        public java.util.concurrent.Executor mExecutor;
        public android.se.omapi.SEService.OnConnectedListener mListener;

        private SEListener() {
            this.mListener = null;
            this.mExecutor = null;
        }

        @Override // android.se.omapi.ISecureElementListener.Stub, android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public void onConnected() {
            if (this.mListener != null && this.mExecutor != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.se.omapi.SEService.SEListener.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.se.omapi.SEService.SEListener.this.mListener.onConnected();
                    }
                });
            }
        }

        @Override // android.se.omapi.ISecureElementListener
        public java.lang.String getInterfaceHash() {
            return "894069bcfe4f35ceb2088278ddf87c83adee8014";
        }

        @Override // android.se.omapi.ISecureElementListener
        public int getInterfaceVersion() {
            return 1;
        }
    }

    public SEService(android.content.Context context, java.util.concurrent.Executor executor, android.se.omapi.SEService.OnConnectedListener onConnectedListener) {
        if (context == null || onConnectedListener == null || executor == null) {
            throw new java.lang.NullPointerException("Arguments must not be null");
        }
        this.mContext = context;
        this.mSEListener.mListener = onConnectedListener;
        this.mSEListener.mExecutor = executor;
        this.mConnection = new android.content.ServiceConnection() { // from class: android.se.omapi.SEService.1
            @Override // android.content.ServiceConnection
            public synchronized void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                android.se.omapi.SEService.this.mSecureElementService = android.se.omapi.ISecureElementService.Stub.asInterface(iBinder);
                if (android.se.omapi.SEService.this.mSEListener != null) {
                    android.se.omapi.SEService.this.mSEListener.onConnected();
                }
                android.util.Log.i(android.se.omapi.SEService.TAG, "Service onServiceConnected");
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                android.se.omapi.SEService.this.mSecureElementService = null;
                android.util.Log.i(android.se.omapi.SEService.TAG, "Service onServiceDisconnected");
            }
        };
        android.content.Intent intent = new android.content.Intent(android.se.omapi.ISecureElementService.class.getName());
        intent.setClassName("com.android.se", "com.android.se.SecureElementService");
        if (this.mContext.bindService(intent, this.mConnection, 1)) {
            android.util.Log.i(TAG, "bindService successful");
        }
    }

    public boolean isConnected() {
        return this.mSecureElementService != null;
    }

    public android.se.omapi.Reader[] getReaders() {
        loadReaders();
        return (android.se.omapi.Reader[]) this.mReaders.values().toArray(new android.se.omapi.Reader[0]);
    }

    public android.se.omapi.Reader getUiccReader(int i) {
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("slotNumber should be larger than 0");
        }
        loadReaders();
        java.lang.String str = UICC_TERMINAL + i;
        android.se.omapi.Reader reader = this.mReaders.get(str);
        if (reader == null) {
            throw new java.lang.IllegalArgumentException("Reader:" + str + " doesn't exist");
        }
        return reader;
    }

    public void shutdown() {
        synchronized (this.mLock) {
            if (this.mSecureElementService != null) {
                java.util.Iterator<android.se.omapi.Reader> it = this.mReaders.values().iterator();
                while (it.hasNext()) {
                    try {
                        it.next().closeSessions();
                    } catch (java.lang.Exception e) {
                    }
                }
            }
            try {
                this.mContext.unbindService(this.mConnection);
            } catch (java.lang.IllegalArgumentException e2) {
            }
            this.mSecureElementService = null;
        }
    }

    public java.lang.String getVersion() {
        return "3.3";
    }

    android.se.omapi.ISecureElementListener getListener() {
        return this.mSEListener;
    }

    private android.se.omapi.ISecureElementReader getReader(java.lang.String str) {
        try {
            return this.mSecureElementService.getReader(str);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException(e.getMessage());
        }
    }

    private void loadReaders() {
        if (this.mSecureElementService == null) {
            throw new java.lang.IllegalStateException("service not connected to system");
        }
        try {
            for (java.lang.String str : this.mSecureElementService.getReaders()) {
                if (this.mReaders.get(str) == null) {
                    try {
                        this.mReaders.put(str, new android.se.omapi.Reader(this, str, getReader(str)));
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(TAG, "Error adding Reader: " + str, e);
                    }
                }
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowAsRuntimeException();
        }
    }
}
