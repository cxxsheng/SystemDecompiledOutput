package com.android.server;

/* loaded from: classes.dex */
public class CountryDetectorService extends android.location.ICountryDetector.Stub {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CountryDetector";
    private final android.content.Context mContext;
    private com.android.server.location.countrydetector.CountryDetectorBase mCountryDetector;
    private android.os.Handler mHandler;
    private android.location.CountryListener mLocationBasedDetectorListener;
    private final java.util.HashMap<android.os.IBinder, com.android.server.CountryDetectorService.Receiver> mReceivers;
    private boolean mSystemReady;

    private final class Receiver implements android.os.IBinder.DeathRecipient {
        private final android.os.IBinder mKey;
        private final android.location.ICountryListener mListener;

        public Receiver(android.location.ICountryListener iCountryListener) {
            this.mListener = iCountryListener;
            this.mKey = iCountryListener.asBinder();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.CountryDetectorService.this.removeListener(this.mKey);
        }

        public boolean equals(java.lang.Object obj) {
            if (obj instanceof com.android.server.CountryDetectorService.Receiver) {
                return this.mKey.equals(((com.android.server.CountryDetectorService.Receiver) obj).mKey);
            }
            return false;
        }

        public int hashCode() {
            return this.mKey.hashCode();
        }

        public android.location.ICountryListener getListener() {
            return this.mListener;
        }
    }

    public CountryDetectorService(android.content.Context context) {
        this(context, com.android.internal.os.BackgroundThread.getHandler());
    }

    @com.android.internal.annotations.VisibleForTesting
    CountryDetectorService(android.content.Context context, android.os.Handler handler) {
        this.mReceivers = new java.util.HashMap<>();
        this.mContext = context;
        this.mHandler = handler;
    }

    public android.location.Country detectCountry() {
        if (!this.mSystemReady) {
            return null;
        }
        return this.mCountryDetector.detectCountry();
    }

    public void addCountryListener(android.location.ICountryListener iCountryListener) throws android.os.RemoteException {
        if (!this.mSystemReady) {
            throw new android.os.RemoteException();
        }
        addListener(iCountryListener);
    }

    public void removeCountryListener(android.location.ICountryListener iCountryListener) throws android.os.RemoteException {
        if (!this.mSystemReady) {
            throw new android.os.RemoteException();
        }
        removeListener(iCountryListener.asBinder());
    }

    private void addListener(android.location.ICountryListener iCountryListener) {
        synchronized (this.mReceivers) {
            try {
                com.android.server.CountryDetectorService.Receiver receiver = new com.android.server.CountryDetectorService.Receiver(iCountryListener);
                try {
                    iCountryListener.asBinder().linkToDeath(receiver, 0);
                    android.location.Country detectCountry = detectCountry();
                    if (detectCountry != null) {
                        iCountryListener.onCountryDetected(detectCountry);
                    }
                    this.mReceivers.put(iCountryListener.asBinder(), receiver);
                    if (this.mReceivers.size() == 1) {
                        android.util.Slog.d(TAG, "The first listener is added");
                        setCountryListener(this.mLocationBasedDetectorListener);
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "linkToDeath failed:", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeListener(android.os.IBinder iBinder) {
        synchronized (this.mReceivers) {
            try {
                this.mReceivers.remove(iBinder);
                if (this.mReceivers.isEmpty()) {
                    setCountryListener(null);
                    android.util.Slog.d(TAG, "No listener is left");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: notifyReceivers, reason: merged with bridge method [inline-methods] */
    public void lambda$initialize$1(android.location.Country country) {
        synchronized (this.mReceivers) {
            java.util.Iterator<com.android.server.CountryDetectorService.Receiver> it = this.mReceivers.values().iterator();
            while (it.hasNext()) {
                try {
                    it.next().getListener().onCountryDetected(country);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "notifyReceivers failed:", e);
                }
            }
        }
    }

    void systemRunning() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.CountryDetectorService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.CountryDetectorService.this.lambda$systemRunning$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemRunning$0() {
        initialize();
        this.mSystemReady = true;
    }

    @com.android.internal.annotations.VisibleForTesting
    void initialize() {
        java.lang.String string = this.mContext.getString(android.R.string.config_companionDeviceManagerPackage);
        if (!android.text.TextUtils.isEmpty(string)) {
            this.mCountryDetector = loadCustomCountryDetectorIfAvailable(string);
        }
        if (this.mCountryDetector == null) {
            android.util.Slog.d(TAG, "Using default country detector");
            this.mCountryDetector = new com.android.server.location.countrydetector.ComprehensiveCountryDetector(this.mContext);
        }
        this.mLocationBasedDetectorListener = new android.location.CountryListener() { // from class: com.android.server.CountryDetectorService$$ExternalSyntheticLambda2
            public final void onCountryDetected(android.location.Country country) {
                com.android.server.CountryDetectorService.this.lambda$initialize$2(country);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialize$2(final android.location.Country country) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.CountryDetectorService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.CountryDetectorService.this.lambda$initialize$1(country);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCountryListener$3(android.location.CountryListener countryListener) {
        this.mCountryDetector.setCountryListener(countryListener);
    }

    protected void setCountryListener(final android.location.CountryListener countryListener) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.CountryDetectorService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.CountryDetectorService.this.lambda$setCountryListener$3(countryListener);
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.location.countrydetector.CountryDetectorBase getCountryDetector() {
        return this.mCountryDetector;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isSystemReady() {
        return this.mSystemReady;
    }

    private com.android.server.location.countrydetector.CountryDetectorBase loadCustomCountryDetectorIfAvailable(java.lang.String str) {
        android.util.Slog.d(TAG, "Using custom country detector class: " + str);
        try {
            return (com.android.server.location.countrydetector.CountryDetectorBase) java.lang.Class.forName(str).asSubclass(com.android.server.location.countrydetector.CountryDetectorBase.class).getConstructor(android.content.Context.class).newInstance(this.mContext);
        } catch (java.lang.ClassNotFoundException | java.lang.IllegalAccessException | java.lang.InstantiationException | java.lang.NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
            android.util.Slog.e(TAG, "Could not instantiate the custom country detector class");
            return null;
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter);
    }
}
