package android.net.wifi.nl80211;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class WifiNl80211Manager {
    public static final java.lang.String EXTRA_SCANNING_PARAM_VENDOR_IES = "android.net.wifi.nl80211.extra.SCANNING_PARAM_VENDOR_IES";
    public static final java.lang.String SCANNING_PARAM_ENABLE_6GHZ_RNR = "android.net.wifi.nl80211.SCANNING_PARAM_ENABLE_6GHZ_RNR";
    public static final int SCAN_TYPE_PNO_SCAN = 1;
    public static final int SCAN_TYPE_SINGLE_SCAN = 0;
    public static final int SEND_MGMT_FRAME_ERROR_ALREADY_STARTED = 5;
    public static final int SEND_MGMT_FRAME_ERROR_MCS_UNSUPPORTED = 2;
    public static final int SEND_MGMT_FRAME_ERROR_NO_ACK = 3;
    public static final int SEND_MGMT_FRAME_ERROR_TIMEOUT = 4;
    public static final int SEND_MGMT_FRAME_ERROR_UNKNOWN = 1;
    private static final int SEND_MGMT_FRAME_TIMEOUT_MS = 1000;
    private static final java.lang.String TAG = "WifiNl80211Manager";
    private static final java.lang.String TIMEOUT_ALARM_TAG = "WifiNl80211Manager Send Management Frame Timeout";
    private android.app.AlarmManager mAlarmManager;
    private java.util.HashMap<java.lang.String, android.net.wifi.nl80211.IApInterfaceEventCallback> mApInterfaceListeners;
    private java.util.HashMap<java.lang.String, android.net.wifi.nl80211.IApInterface> mApInterfaces;
    private java.util.HashMap<java.lang.String, android.net.wifi.nl80211.IClientInterface> mClientInterfaces;
    private java.lang.Runnable mDeathEventHandler;
    private android.os.Handler mEventHandler;
    private java.lang.Object mLock;
    private java.util.HashMap<java.lang.String, android.net.wifi.nl80211.IPnoScanEvent> mPnoScanEventHandlers;
    private java.util.HashMap<java.lang.String, android.net.wifi.nl80211.IScanEvent> mScanEventHandlers;
    private java.util.concurrent.atomic.AtomicBoolean mSendMgmtFrameInProgress;
    private boolean mVerboseLoggingEnabled;
    private android.net.wifi.nl80211.IWificond mWificond;
    private android.net.wifi.nl80211.WifiNl80211Manager.WificondEventHandler mWificondEventHandler;
    private java.util.HashMap<java.lang.String, android.net.wifi.nl80211.IWifiScannerImpl> mWificondScanners;

    public interface CountryCodeChangedListener {
        void onCountryCodeChanged(java.lang.String str);
    }

    public interface PnoScanRequestCallback {
        void onPnoRequestFailed();

        void onPnoRequestSucceeded();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScanResultType {
    }

    public interface SendMgmtFrameCallback {
        void onAck(int i);

        void onFailure(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SendMgmtFrameError {
    }

    @java.lang.Deprecated
    public interface SoftApCallback {
        void onConnectedClientsChanged(android.net.wifi.nl80211.NativeWifiClient nativeWifiClient, boolean z);

        void onFailure();

        void onSoftApChannelSwitched(int i, int i2);
    }

    public interface ScanEventCallback {
        void onScanFailed();

        void onScanResultReady();

        default void onScanFailed(int i) {
        }
    }

    public class WificondEventHandler extends android.net.wifi.nl80211.IWificondEventCallback.Stub {
        private java.util.Map<android.net.wifi.nl80211.WifiNl80211Manager.CountryCodeChangedListener, java.util.concurrent.Executor> mCountryCodeChangedListenerHolder = new java.util.HashMap();

        public WificondEventHandler() {
        }

        public void registerCountryCodeChangedListener(java.util.concurrent.Executor executor, android.net.wifi.nl80211.WifiNl80211Manager.CountryCodeChangedListener countryCodeChangedListener) {
            this.mCountryCodeChangedListenerHolder.put(countryCodeChangedListener, executor);
        }

        public void unregisterCountryCodeChangedListener(android.net.wifi.nl80211.WifiNl80211Manager.CountryCodeChangedListener countryCodeChangedListener) {
            this.mCountryCodeChangedListenerHolder.remove(countryCodeChangedListener);
        }

        @Override // android.net.wifi.nl80211.IWificondEventCallback
        public void OnRegDomainChanged(final java.lang.String str) {
            android.util.Log.d(android.net.wifi.nl80211.WifiNl80211Manager.TAG, "OnRegDomainChanged " + str);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mCountryCodeChangedListenerHolder.forEach(new java.util.function.BiConsumer() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$WificondEventHandler$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        java.util.concurrent.Executor executor = (java.util.concurrent.Executor) obj2;
                        executor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$WificondEventHandler$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.net.wifi.nl80211.WifiNl80211Manager.CountryCodeChangedListener.this.onCountryCodeChanged(r2);
                            }
                        });
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ScanEventHandler extends android.net.wifi.nl80211.IScanEvent.Stub {
        private android.net.wifi.nl80211.WifiNl80211Manager.ScanEventCallback mCallback;
        private java.util.concurrent.Executor mExecutor;

        ScanEventHandler(java.util.concurrent.Executor executor, android.net.wifi.nl80211.WifiNl80211Manager.ScanEventCallback scanEventCallback) {
            this.mExecutor = executor;
            this.mCallback = scanEventCallback;
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanResultReady() {
            android.util.Log.d(android.net.wifi.nl80211.WifiNl80211Manager.TAG, "Scan result ready event");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ScanEventHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.ScanEventHandler.this.lambda$OnScanResultReady$0();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnScanResultReady$0() {
            this.mCallback.onScanResultReady();
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanFailed() {
            android.util.Log.d(android.net.wifi.nl80211.WifiNl80211Manager.TAG, "Scan failed event");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ScanEventHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.ScanEventHandler.this.lambda$OnScanFailed$1();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnScanFailed$1() {
            this.mCallback.onScanFailed();
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanRequestFailed(final int i) {
            android.util.Log.d(android.net.wifi.nl80211.WifiNl80211Manager.TAG, "Scan failed event with error code: " + i);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ScanEventHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.ScanEventHandler.this.lambda$OnScanRequestFailed$2(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnScanRequestFailed$2(int i) {
            this.mCallback.onScanFailed(android.net.wifi.nl80211.WifiNl80211Manager.this.toFrameworkScanStatusCode(i));
        }
    }

    @java.lang.Deprecated
    public static class SignalPollResult {
        public final int associationFrequencyMHz;
        public final int currentRssiDbm;
        public final int rxBitrateMbps;
        public final int txBitrateMbps;

        public SignalPollResult(int i, int i2, int i3, int i4) {
            this.currentRssiDbm = i;
            this.txBitrateMbps = i2;
            this.rxBitrateMbps = i3;
            this.associationFrequencyMHz = i4;
        }
    }

    public static class TxPacketCounters {
        public final int txPacketFailed;
        public final int txPacketSucceeded;

        public TxPacketCounters(int i, int i2) {
            this.txPacketSucceeded = i;
            this.txPacketFailed = i2;
        }
    }

    public WifiNl80211Manager(android.content.Context context) {
        this.mVerboseLoggingEnabled = false;
        this.mWificondEventHandler = new android.net.wifi.nl80211.WifiNl80211Manager.WificondEventHandler();
        this.mClientInterfaces = new java.util.HashMap<>();
        this.mApInterfaces = new java.util.HashMap<>();
        this.mWificondScanners = new java.util.HashMap<>();
        this.mScanEventHandlers = new java.util.HashMap<>();
        this.mPnoScanEventHandlers = new java.util.HashMap<>();
        this.mApInterfaceListeners = new java.util.HashMap<>();
        this.mLock = new java.lang.Object();
        this.mSendMgmtFrameInProgress = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mAlarmManager = (android.app.AlarmManager) context.getSystemService(android.app.AlarmManager.class);
        this.mEventHandler = new android.os.Handler(context.getMainLooper());
    }

    public WifiNl80211Manager(android.content.Context context, android.os.IBinder iBinder) {
        this(context);
        this.mWificond = android.net.wifi.nl80211.IWificond.Stub.asInterface(iBinder);
        if (this.mWificond == null) {
            android.util.Log.e(TAG, "Failed to get reference to wificond");
        }
    }

    public WifiNl80211Manager(android.content.Context context, android.net.wifi.nl80211.IWificond iWificond) {
        this(context);
        this.mWificond = iWificond;
    }

    public android.net.wifi.nl80211.WifiNl80211Manager.WificondEventHandler getWificondEventHandler() {
        return this.mWificondEventHandler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PnoScanEventHandler extends android.net.wifi.nl80211.IPnoScanEvent.Stub {
        private android.net.wifi.nl80211.WifiNl80211Manager.ScanEventCallback mCallback;
        private java.util.concurrent.Executor mExecutor;

        PnoScanEventHandler(java.util.concurrent.Executor executor, android.net.wifi.nl80211.WifiNl80211Manager.ScanEventCallback scanEventCallback) {
            this.mExecutor = executor;
            this.mCallback = scanEventCallback;
        }

        @Override // android.net.wifi.nl80211.IPnoScanEvent
        public void OnPnoNetworkFound() {
            android.util.Log.d(android.net.wifi.nl80211.WifiNl80211Manager.TAG, "Pno scan result event");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$PnoScanEventHandler$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.PnoScanEventHandler.this.lambda$OnPnoNetworkFound$0();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnPnoNetworkFound$0() {
            this.mCallback.onScanResultReady();
        }

        @Override // android.net.wifi.nl80211.IPnoScanEvent
        public void OnPnoScanFailed() {
            android.util.Log.d(android.net.wifi.nl80211.WifiNl80211Manager.TAG, "Pno Scan failed event");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$PnoScanEventHandler$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.PnoScanEventHandler.this.lambda$OnPnoScanFailed$1();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnPnoScanFailed$1() {
            this.mCallback.onScanFailed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ApInterfaceEventCallback extends android.net.wifi.nl80211.IApInterfaceEventCallback.Stub {
        private java.util.concurrent.Executor mExecutor;
        private android.net.wifi.nl80211.WifiNl80211Manager.SoftApCallback mSoftApListener;

        ApInterfaceEventCallback(java.util.concurrent.Executor executor, android.net.wifi.nl80211.WifiNl80211Manager.SoftApCallback softApCallback) {
            this.mExecutor = executor;
            this.mSoftApListener = softApCallback;
        }

        @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
        public void onConnectedClientsChanged(final android.net.wifi.nl80211.NativeWifiClient nativeWifiClient, final boolean z) {
            if (android.net.wifi.nl80211.WifiNl80211Manager.this.mVerboseLoggingEnabled) {
                android.util.Log.d(android.net.wifi.nl80211.WifiNl80211Manager.TAG, "onConnectedClientsChanged called with " + nativeWifiClient.getMacAddress() + " isConnected: " + z);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ApInterfaceEventCallback$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.ApInterfaceEventCallback.this.lambda$onConnectedClientsChanged$0(nativeWifiClient, z);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConnectedClientsChanged$0(android.net.wifi.nl80211.NativeWifiClient nativeWifiClient, boolean z) {
            this.mSoftApListener.onConnectedClientsChanged(nativeWifiClient, z);
        }

        @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
        public void onSoftApChannelSwitched(final int i, final int i2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$ApInterfaceEventCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.ApInterfaceEventCallback.this.lambda$onSoftApChannelSwitched$1(i, i2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSoftApChannelSwitched$1(int i, int i2) {
            this.mSoftApListener.onSoftApChannelSwitched(i, toFrameworkBandwidth(i2));
        }

        private int toFrameworkBandwidth(int i) {
            switch (i) {
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SendMgmtFrameEvent extends android.net.wifi.nl80211.ISendMgmtFrameEvent.Stub {
        private android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameCallback mCallback;
        private java.util.concurrent.Executor mExecutor;
        private android.app.AlarmManager.OnAlarmListener mTimeoutCallback = new android.app.AlarmManager.OnAlarmListener() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda4
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$new$2();
            }
        };
        private boolean mWasCalled = false;

        private void runIfFirstCall(java.lang.Runnable runnable) {
            if (this.mWasCalled) {
                return;
            }
            this.mWasCalled = true;
            android.net.wifi.nl80211.WifiNl80211Manager.this.mSendMgmtFrameInProgress.set(false);
            runnable.run();
        }

        SendMgmtFrameEvent(java.util.concurrent.Executor executor, android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameCallback sendMgmtFrameCallback) {
            this.mExecutor = executor;
            this.mCallback = sendMgmtFrameCallback;
            android.net.wifi.nl80211.WifiNl80211Manager.this.mAlarmManager.set(2, android.os.SystemClock.elapsedRealtime() + 1000, android.net.wifi.nl80211.WifiNl80211Manager.TIMEOUT_ALARM_TAG, this.mTimeoutCallback, android.net.wifi.nl80211.WifiNl80211Manager.this.mEventHandler);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2() {
            runIfFirstCall(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$new$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1() {
            if (android.net.wifi.nl80211.WifiNl80211Manager.this.mVerboseLoggingEnabled) {
                android.util.Log.e(android.net.wifi.nl80211.WifiNl80211Manager.TAG, "Timed out waiting for ACK");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$new$0();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            this.mCallback.onFailure(4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnAck$5(final int i) {
            runIfFirstCall(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$OnAck$4(i);
                }
            });
        }

        @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
        public void OnAck(final int i) {
            android.net.wifi.nl80211.WifiNl80211Manager.this.mEventHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$OnAck$5(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnAck$4(final int i) {
            android.net.wifi.nl80211.WifiNl80211Manager.this.mAlarmManager.cancel(this.mTimeoutCallback);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$OnAck$3(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnAck$3(int i) {
            this.mCallback.onAck(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnFailure$8(final int i) {
            runIfFirstCall(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$OnFailure$7(i);
                }
            });
        }

        @Override // android.net.wifi.nl80211.ISendMgmtFrameEvent
        public void OnFailure(final int i) {
            android.net.wifi.nl80211.WifiNl80211Manager.this.mEventHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$OnFailure$8(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnFailure$7(final int i) {
            android.net.wifi.nl80211.WifiNl80211Manager.this.mAlarmManager.cancel(this.mTimeoutCallback);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$SendMgmtFrameEvent$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent.this.lambda$OnFailure$6(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$OnFailure$6(int i) {
            this.mCallback.onFailure(i);
        }
    }

    /* renamed from: binderDied, reason: merged with bridge method [inline-methods] */
    public void lambda$retrieveWificondAndRegisterForDeath$1() {
        this.mEventHandler.post(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.net.wifi.nl80211.WifiNl80211Manager.this.lambda$binderDied$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$0() {
        synchronized (this.mLock) {
            android.util.Log.e(TAG, "Wificond died!");
            clearState();
            this.mWificond = null;
            if (this.mDeathEventHandler != null) {
                this.mDeathEventHandler.run();
            }
        }
    }

    public void enableVerboseLogging(boolean z) {
        this.mVerboseLoggingEnabled = z;
    }

    public void setOnServiceDeadCallback(java.lang.Runnable runnable) {
        if (this.mDeathEventHandler != null) {
            android.util.Log.e(TAG, "Death handler already present");
        }
        this.mDeathEventHandler = runnable;
    }

    private boolean retrieveWificondAndRegisterForDeath() {
        if (this.mWificond != null) {
            if (this.mVerboseLoggingEnabled) {
                android.util.Log.d(TAG, "Wificond handle already retrieved");
            }
            return true;
        }
        this.mWificond = android.net.wifi.nl80211.IWificond.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.WIFI_NL80211_SERVICE));
        if (this.mWificond == null) {
            android.util.Log.e(TAG, "Failed to get reference to wificond");
            return false;
        }
        try {
            this.mWificond.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda0
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    android.net.wifi.nl80211.WifiNl80211Manager.this.lambda$retrieveWificondAndRegisterForDeath$1();
                }
            }, 0);
            this.mWificond.registerWificondEventCallback(this.mWificondEventHandler);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to register death notification for wificond");
            return false;
        }
    }

    public boolean setupInterfaceForClientMode(java.lang.String str, java.util.concurrent.Executor executor, android.net.wifi.nl80211.WifiNl80211Manager.ScanEventCallback scanEventCallback, android.net.wifi.nl80211.WifiNl80211Manager.ScanEventCallback scanEventCallback2) {
        android.util.Log.d(TAG, "Setting up interface for client mode: " + str);
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        if (scanEventCallback == null || scanEventCallback2 == null || executor == null) {
            android.util.Log.e(TAG, "setupInterfaceForClientMode invoked with null callbacks");
            return false;
        }
        try {
            android.net.wifi.nl80211.IClientInterface createClientInterface = this.mWificond.createClientInterface(str);
            if (createClientInterface == null) {
                android.util.Log.e(TAG, "Could not get IClientInterface instance from wificond");
                return false;
            }
            android.os.Binder.allowBlocking(createClientInterface.asBinder());
            this.mClientInterfaces.put(str, createClientInterface);
            try {
                android.net.wifi.nl80211.IWifiScannerImpl wifiScannerImpl = createClientInterface.getWifiScannerImpl();
                if (wifiScannerImpl == null) {
                    android.util.Log.e(TAG, "Failed to get WificondScannerImpl");
                    return false;
                }
                this.mWificondScanners.put(str, wifiScannerImpl);
                android.os.Binder.allowBlocking(wifiScannerImpl.asBinder());
                android.net.wifi.nl80211.WifiNl80211Manager.ScanEventHandler scanEventHandler = new android.net.wifi.nl80211.WifiNl80211Manager.ScanEventHandler(executor, scanEventCallback);
                this.mScanEventHandlers.put(str, scanEventHandler);
                wifiScannerImpl.subscribeScanEvents(scanEventHandler);
                android.net.wifi.nl80211.WifiNl80211Manager.PnoScanEventHandler pnoScanEventHandler = new android.net.wifi.nl80211.WifiNl80211Manager.PnoScanEventHandler(executor, scanEventCallback2);
                this.mPnoScanEventHandlers.put(str, pnoScanEventHandler);
                wifiScannerImpl.subscribePnoScanEvents(pnoScanEventHandler);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to refresh wificond scanner due to remote exception");
                return true;
            }
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Failed to get IClientInterface due to remote exception");
            return false;
        }
    }

    public boolean tearDownClientInterface(java.lang.String str) {
        if (getClientInterface(str) == null) {
            android.util.Log.e(TAG, "No valid wificond client interface handler for iface=" + str);
            return false;
        }
        try {
            android.net.wifi.nl80211.IWifiScannerImpl iWifiScannerImpl = this.mWificondScanners.get(str);
            if (iWifiScannerImpl != null) {
                iWifiScannerImpl.unsubscribeScanEvents();
                iWifiScannerImpl.unsubscribePnoScanEvents();
            }
            if (this.mWificond == null) {
                android.util.Log.e(TAG, "tearDownClientInterface: mWificond binder is null! Did wificond die?");
                return false;
            }
            try {
                if (!this.mWificond.tearDownClientInterface(str)) {
                    android.util.Log.e(TAG, "Failed to teardown client interface");
                    return false;
                }
                this.mClientInterfaces.remove(str);
                this.mWificondScanners.remove(str);
                this.mScanEventHandlers.remove(str);
                this.mPnoScanEventHandlers.remove(str);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to teardown client interface due to remote exception");
                return false;
            }
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Failed to unsubscribe wificond scanner due to remote exception");
            return false;
        }
    }

    public boolean setupInterfaceForSoftApMode(java.lang.String str) {
        android.util.Log.d(TAG, "Setting up interface for soft ap mode for iface=" + str);
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        try {
            android.net.wifi.nl80211.IApInterface createApInterface = this.mWificond.createApInterface(str);
            if (createApInterface == null) {
                android.util.Log.e(TAG, "Could not get IApInterface instance from wificond");
                return false;
            }
            android.os.Binder.allowBlocking(createApInterface.asBinder());
            this.mApInterfaces.put(str, createApInterface);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to get IApInterface due to remote exception");
            return false;
        }
    }

    public boolean tearDownSoftApInterface(java.lang.String str) {
        if (getApInterface(str) == null) {
            android.util.Log.e(TAG, "No valid wificond ap interface handler for iface=" + str);
            return false;
        }
        if (this.mWificond == null) {
            android.util.Log.e(TAG, "tearDownSoftApInterface: mWificond binder is null! Did wificond die?");
            return false;
        }
        try {
            if (!this.mWificond.tearDownApInterface(str)) {
                android.util.Log.e(TAG, "Failed to teardown AP interface");
                return false;
            }
            this.mApInterfaces.remove(str);
            this.mApInterfaceListeners.remove(str);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to teardown AP interface due to remote exception");
            return false;
        }
    }

    public boolean tearDownInterfaces() {
        synchronized (this.mLock) {
            android.util.Log.d(TAG, "tearing down interfaces in wificond");
            if (!retrieveWificondAndRegisterForDeath()) {
                return false;
            }
            try {
                for (java.util.Map.Entry<java.lang.String, android.net.wifi.nl80211.IWifiScannerImpl> entry : this.mWificondScanners.entrySet()) {
                    entry.getValue().unsubscribeScanEvents();
                    entry.getValue().unsubscribePnoScanEvents();
                }
                this.mWificond.tearDownInterfaces();
                clearState();
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to tear down interfaces due to remote exception");
                return false;
            }
        }
    }

    private android.net.wifi.nl80211.IClientInterface getClientInterface(java.lang.String str) {
        return this.mClientInterfaces.get(str);
    }

    @java.lang.Deprecated
    public android.net.wifi.nl80211.WifiNl80211Manager.SignalPollResult signalPoll(java.lang.String str) {
        android.net.wifi.nl80211.IClientInterface clientInterface = getClientInterface(str);
        if (clientInterface == null) {
            android.util.Log.e(TAG, "No valid wificond client interface handler for iface=" + str);
            return null;
        }
        try {
            int[] signalPoll = clientInterface.signalPoll();
            if (signalPoll == null || signalPoll.length != 4) {
                android.util.Log.e(TAG, "Invalid signal poll result from wificond");
                return null;
            }
            return new android.net.wifi.nl80211.WifiNl80211Manager.SignalPollResult(signalPoll[0], signalPoll[1], signalPoll[3], signalPoll[2]);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to do signal polling due to remote exception");
            return null;
        }
    }

    public android.net.wifi.nl80211.WifiNl80211Manager.TxPacketCounters getTxPacketCounters(java.lang.String str) {
        android.net.wifi.nl80211.IClientInterface clientInterface = getClientInterface(str);
        if (clientInterface == null) {
            android.util.Log.e(TAG, "No valid wificond client interface handler for iface=" + str);
            return null;
        }
        try {
            int[] packetCounters = clientInterface.getPacketCounters();
            if (packetCounters == null || packetCounters.length != 2) {
                android.util.Log.e(TAG, "Invalid signal poll result from wificond");
                return null;
            }
            return new android.net.wifi.nl80211.WifiNl80211Manager.TxPacketCounters(packetCounters[0], packetCounters[1]);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to do signal polling due to remote exception");
            return null;
        }
    }

    private android.net.wifi.nl80211.IWifiScannerImpl getScannerImpl(java.lang.String str) {
        return this.mWificondScanners.get(str);
    }

    public java.util.List<android.net.wifi.nl80211.NativeScanResult> getScanResults(java.lang.String str, int i) {
        java.util.List<android.net.wifi.nl80211.NativeScanResult> list;
        android.net.wifi.nl80211.IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            android.util.Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return new java.util.ArrayList();
        }
        try {
            if (i == 0) {
                list = java.util.Arrays.asList(scannerImpl.getScanResults());
            } else {
                list = java.util.Arrays.asList(scannerImpl.getPnoScanResults());
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to create ScanDetail ArrayList");
            list = null;
        }
        if (list == null) {
            list = new java.util.ArrayList<>();
        }
        if (this.mVerboseLoggingEnabled) {
            android.util.Log.d(TAG, "get " + list.size() + " scan results from wificond");
        }
        return list;
    }

    public int getMaxSsidsPerScan(java.lang.String str) {
        android.net.wifi.nl80211.IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            android.util.Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return 0;
        }
        try {
            return scannerImpl.getMaxSsidsPerScan();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to getMaxSsidsPerScan");
            return 0;
        }
    }

    private static int getScanType(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                throw new java.lang.IllegalArgumentException("Invalid scan type " + i);
        }
    }

    @java.lang.Deprecated
    public boolean startScan(java.lang.String str, int i, java.util.Set<java.lang.Integer> set, java.util.List<byte[]> list) {
        return startScan(str, i, set, list, null);
    }

    @java.lang.Deprecated
    public boolean startScan(java.lang.String str, int i, java.util.Set<java.lang.Integer> set, java.util.List<byte[]> list, android.os.Bundle bundle) {
        android.net.wifi.nl80211.IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            android.util.Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return false;
        }
        android.net.wifi.nl80211.SingleScanSettings createSingleScanSettings = createSingleScanSettings(i, set, list, bundle);
        if (createSingleScanSettings == null) {
            return false;
        }
        try {
            return scannerImpl.scan(createSingleScanSettings);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to request scan due to remote exception");
            return false;
        }
    }

    public int startScan2(java.lang.String str, int i, java.util.Set<java.lang.Integer> set, java.util.List<byte[]> list, android.os.Bundle bundle) {
        android.net.wifi.nl80211.IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            android.util.Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return -9;
        }
        android.net.wifi.nl80211.SingleScanSettings createSingleScanSettings = createSingleScanSettings(i, set, list, bundle);
        if (createSingleScanSettings == null) {
            return -9;
        }
        try {
            return toFrameworkScanStatusCode(scannerImpl.scanRequest(createSingleScanSettings));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to request scan due to remote exception");
            return -1;
        }
    }

    private android.net.wifi.nl80211.SingleScanSettings createSingleScanSettings(int i, java.util.Set<java.lang.Integer> set, java.util.List<byte[]> list, android.os.Bundle bundle) {
        android.net.wifi.nl80211.SingleScanSettings singleScanSettings = new android.net.wifi.nl80211.SingleScanSettings();
        try {
            singleScanSettings.scanType = getScanType(i);
            singleScanSettings.channelSettings = new java.util.ArrayList<>();
            singleScanSettings.hiddenNetworks = new java.util.ArrayList<>();
            if (bundle != null) {
                singleScanSettings.enable6GhzRnr = bundle.getBoolean(SCANNING_PARAM_ENABLE_6GHZ_RNR);
                singleScanSettings.vendorIes = bundle.getByteArray(EXTRA_SCANNING_PARAM_VENDOR_IES);
            }
            if (set != null) {
                for (java.lang.Integer num : set) {
                    android.net.wifi.nl80211.ChannelSettings channelSettings = new android.net.wifi.nl80211.ChannelSettings();
                    channelSettings.frequency = num.intValue();
                    singleScanSettings.channelSettings.add(channelSettings);
                }
            }
            if (list != null) {
                for (byte[] bArr : list) {
                    android.net.wifi.nl80211.HiddenNetwork hiddenNetwork = new android.net.wifi.nl80211.HiddenNetwork();
                    hiddenNetwork.ssid = bArr;
                    if (!singleScanSettings.hiddenNetworks.contains(hiddenNetwork)) {
                        singleScanSettings.hiddenNetworks.add(hiddenNetwork);
                    }
                }
            }
            return singleScanSettings;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Invalid scan type ", e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int toFrameworkScanStatusCode(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
            default:
                return -1;
            case 2:
                return -6;
            case 3:
                return -7;
            case 4:
                return -8;
            case 5:
                return -9;
        }
    }

    public boolean startPnoScan(java.lang.String str, android.net.wifi.nl80211.PnoSettings pnoSettings, java.util.concurrent.Executor executor, final android.net.wifi.nl80211.WifiNl80211Manager.PnoScanRequestCallback pnoScanRequestCallback) {
        android.net.wifi.nl80211.IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            android.util.Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return false;
        }
        if (pnoScanRequestCallback == null || executor == null) {
            android.util.Log.e(TAG, "startPnoScan called with a null callback");
            return false;
        }
        try {
            boolean startPnoScan = scannerImpl.startPnoScan(pnoSettings);
            if (startPnoScan) {
                java.util.Objects.requireNonNull(pnoScanRequestCallback);
                executor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.PnoScanRequestCallback.this.onPnoRequestSucceeded();
                    }
                });
            } else {
                java.util.Objects.requireNonNull(pnoScanRequestCallback);
                executor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.PnoScanRequestCallback.this.onPnoRequestFailed();
                    }
                });
            }
            return startPnoScan;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to start pno scan due to remote exception");
            return false;
        }
    }

    public boolean stopPnoScan(java.lang.String str) {
        android.net.wifi.nl80211.IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            android.util.Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return false;
        }
        try {
            return scannerImpl.stopPnoScan();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to stop pno scan due to remote exception");
            return false;
        }
    }

    public void abortScan(java.lang.String str) {
        android.net.wifi.nl80211.IWifiScannerImpl scannerImpl = getScannerImpl(str);
        if (scannerImpl == null) {
            android.util.Log.e(TAG, "No valid wificond scanner interface handler for iface=" + str);
            return;
        }
        try {
            scannerImpl.abortScan();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to request abortScan due to remote exception");
        }
    }

    public int[] getChannelsMhzForBand(int i) {
        int[] iArr;
        if (this.mWificond == null) {
            android.util.Log.e(TAG, "getChannelsMhzForBand: mWificond binder is null! Did wificond die?");
            return new int[0];
        }
        try {
            switch (i) {
                case 1:
                    iArr = this.mWificond.getAvailable2gChannels();
                    break;
                case 2:
                    iArr = this.mWificond.getAvailable5gNonDFSChannels();
                    break;
                case 4:
                    iArr = this.mWificond.getAvailableDFSChannels();
                    break;
                case 8:
                    iArr = this.mWificond.getAvailable6gChannels();
                    break;
                case 16:
                    iArr = this.mWificond.getAvailable60gChannels();
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("unsupported band " + i);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to request getChannelsForBand due to remote exception");
            iArr = null;
        }
        if (iArr == null) {
            return new int[0];
        }
        return iArr;
    }

    private android.net.wifi.nl80211.IApInterface getApInterface(java.lang.String str) {
        return this.mApInterfaces.get(str);
    }

    public android.net.wifi.nl80211.DeviceWiphyCapabilities getDeviceWiphyCapabilities(java.lang.String str) {
        if (this.mWificond == null) {
            android.util.Log.e(TAG, "getDeviceWiphyCapabilities: mWificond binder is null! Did wificond die?");
            return null;
        }
        try {
            return this.mWificond.getDeviceWiphyCapabilities(str);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public boolean registerCountryCodeChangedListener(java.util.concurrent.Executor executor, android.net.wifi.nl80211.WifiNl80211Manager.CountryCodeChangedListener countryCodeChangedListener) {
        if (!retrieveWificondAndRegisterForDeath()) {
            return false;
        }
        android.util.Log.d(TAG, "registerCountryCodeEventListener called");
        this.mWificondEventHandler.registerCountryCodeChangedListener(executor, countryCodeChangedListener);
        return true;
    }

    public void unregisterCountryCodeChangedListener(android.net.wifi.nl80211.WifiNl80211Manager.CountryCodeChangedListener countryCodeChangedListener) {
        android.util.Log.d(TAG, "unregisterCountryCodeEventListener called");
        this.mWificondEventHandler.unregisterCountryCodeChangedListener(countryCodeChangedListener);
    }

    public void notifyCountryCodeChanged(java.lang.String str) {
        if (this.mWificond == null) {
            new android.os.RemoteException("Wificond service doesn't exist!").rethrowFromSystemServer();
        }
        try {
            this.mWificond.notifyCountryCodeChanged();
            android.util.Log.i(TAG, "Receive country code change to " + str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean registerApCallback(java.lang.String str, java.util.concurrent.Executor executor, android.net.wifi.nl80211.WifiNl80211Manager.SoftApCallback softApCallback) {
        android.net.wifi.nl80211.IApInterface apInterface = getApInterface(str);
        if (apInterface == null) {
            android.util.Log.e(TAG, "No valid ap interface handler for iface=" + str);
            return false;
        }
        if (softApCallback == null || executor == null) {
            android.util.Log.e(TAG, "registerApCallback called with a null callback");
            return false;
        }
        try {
            android.net.wifi.nl80211.WifiNl80211Manager.ApInterfaceEventCallback apInterfaceEventCallback = new android.net.wifi.nl80211.WifiNl80211Manager.ApInterfaceEventCallback(executor, softApCallback);
            this.mApInterfaceListeners.put(str, apInterfaceEventCallback);
            if (!apInterface.registerCallback(apInterfaceEventCallback)) {
                android.util.Log.e(TAG, "Failed to register ap callback.");
                return false;
            }
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception in registering AP callback: " + e);
            return false;
        }
    }

    public void sendMgmtFrame(java.lang.String str, byte[] bArr, int i, java.util.concurrent.Executor executor, final android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameCallback sendMgmtFrameCallback) {
        if (sendMgmtFrameCallback == null || executor == null) {
            android.util.Log.e(TAG, "callback cannot be null!");
            return;
        }
        if (bArr == null) {
            android.util.Log.e(TAG, "frame cannot be null!");
            executor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameCallback.this.onFailure(1);
                }
            });
            return;
        }
        android.net.wifi.nl80211.IClientInterface clientInterface = getClientInterface(str);
        if (clientInterface == null) {
            android.util.Log.e(TAG, "No valid wificond client interface handler for iface=" + str);
            executor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameCallback.this.onFailure(1);
                }
            });
        } else {
            if (!this.mSendMgmtFrameInProgress.compareAndSet(false, true)) {
                android.util.Log.e(TAG, "An existing management frame transmission is in progress!");
                executor.execute(new java.lang.Runnable() { // from class: android.net.wifi.nl80211.WifiNl80211Manager$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameCallback.this.onFailure(5);
                    }
                });
                return;
            }
            android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent sendMgmtFrameEvent = new android.net.wifi.nl80211.WifiNl80211Manager.SendMgmtFrameEvent(executor, sendMgmtFrameCallback);
            try {
                clientInterface.SendMgmtFrame(bArr, sendMgmtFrameEvent, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Exception while starting link probe: " + e);
                sendMgmtFrameEvent.OnFailure(1);
            }
        }
    }

    private void clearState() {
        this.mClientInterfaces.clear();
        this.mWificondScanners.clear();
        this.mPnoScanEventHandlers.clear();
        this.mScanEventHandlers.clear();
        this.mApInterfaces.clear();
        this.mApInterfaceListeners.clear();
        this.mSendMgmtFrameInProgress.set(false);
    }

    public static class OemSecurityType {
        public final int groupCipher;
        public final java.util.List<java.lang.Integer> keyManagement;
        public final java.util.List<java.lang.Integer> pairwiseCipher;
        public final int protocol;

        public OemSecurityType(int i, java.util.List<java.lang.Integer> list, java.util.List<java.lang.Integer> list2, int i2) {
            this.protocol = i;
            this.keyManagement = list == null ? new java.util.ArrayList<>() : list;
            this.pairwiseCipher = list2 == null ? new java.util.ArrayList<>() : list2;
            this.groupCipher = i2;
        }
    }

    public static android.net.wifi.nl80211.WifiNl80211Manager.OemSecurityType parseOemSecurityTypeElement(int i, int i2, byte[] bArr) {
        return null;
    }
}
