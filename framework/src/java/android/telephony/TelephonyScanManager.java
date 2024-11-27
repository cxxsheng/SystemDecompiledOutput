package android.telephony;

/* loaded from: classes3.dex */
public final class TelephonyScanManager {
    public static final int CALLBACK_RESTRICTED_SCAN_RESULTS = 4;
    public static final int CALLBACK_SCAN_COMPLETE = 3;
    public static final int CALLBACK_SCAN_ERROR = 2;
    public static final int CALLBACK_SCAN_RESULTS = 1;
    public static final int CALLBACK_TELEPHONY_DIED = 5;
    public static final int INVALID_SCAN_ID = -1;
    public static final java.lang.String SCAN_RESULT_KEY = "scanResult";
    private static final java.lang.String TAG = "TelephonyScanManager";
    private final android.os.IBinder.DeathRecipient mDeathRecipient;
    private final android.os.Handler mHandler;
    private final android.os.Looper mLooper;
    private final android.os.Messenger mMessenger;
    private final android.util.SparseArray<android.telephony.TelephonyScanManager.NetworkScanInfo> mScanInfo = new android.util.SparseArray<>();

    public static abstract class NetworkScanCallback {
        public void onResults(java.util.List<android.telephony.CellInfo> list) {
        }

        public void onComplete() {
        }

        public void onError(int i) {
        }
    }

    private static class NetworkScanInfo {
        private final android.telephony.TelephonyScanManager.NetworkScanCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final android.telephony.NetworkScanRequest mRequest;

        NetworkScanInfo(android.telephony.NetworkScanRequest networkScanRequest, java.util.concurrent.Executor executor, android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback) {
            this.mRequest = networkScanRequest;
            this.mExecutor = executor;
            this.mCallback = networkScanCallback;
        }
    }

    public TelephonyScanManager() {
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mLooper = handlerThread.getLooper();
        this.mHandler = new android.telephony.TelephonyScanManager.AnonymousClass1(this.mLooper);
        this.mMessenger = new android.os.Messenger(this.mHandler);
        this.mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.telephony.TelephonyScanManager.2
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                android.telephony.TelephonyScanManager.this.mHandler.obtainMessage(5).sendToTarget();
            }
        };
    }

    /* renamed from: android.telephony.TelephonyScanManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.os.Handler {
        AnonymousClass1(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.telephony.TelephonyScanManager.NetworkScanInfo networkScanInfo;
            com.android.internal.util.Preconditions.checkNotNull(message, "message cannot be null");
            int i = 0;
            if (message.what == 5) {
                synchronized (android.telephony.TelephonyScanManager.this.mScanInfo) {
                    while (i < android.telephony.TelephonyScanManager.this.mScanInfo.size()) {
                        android.telephony.TelephonyScanManager.NetworkScanInfo networkScanInfo2 = (android.telephony.TelephonyScanManager.NetworkScanInfo) android.telephony.TelephonyScanManager.this.mScanInfo.valueAt(i);
                        if (networkScanInfo2 != null) {
                            java.util.concurrent.Executor executor = networkScanInfo2.mExecutor;
                            final android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback = networkScanInfo2.mCallback;
                            if (executor != null && networkScanCallback != null) {
                                try {
                                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyScanManager$1$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            android.telephony.TelephonyScanManager.NetworkScanCallback.this.onError(3);
                                        }
                                    });
                                } catch (java.util.concurrent.RejectedExecutionException e) {
                                }
                            }
                        }
                        i++;
                    }
                    android.telephony.TelephonyScanManager.this.mScanInfo.clear();
                }
                return;
            }
            synchronized (android.telephony.TelephonyScanManager.this.mScanInfo) {
                networkScanInfo = (android.telephony.TelephonyScanManager.NetworkScanInfo) android.telephony.TelephonyScanManager.this.mScanInfo.get(message.arg2);
            }
            if (networkScanInfo == null) {
                com.android.telephony.Rlog.e(android.telephony.TelephonyScanManager.TAG, "Unexpceted message " + message.what + " as there is no NetworkScanInfo with id " + message.arg2);
                return;
            }
            final android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback2 = networkScanInfo.mCallback;
            java.util.concurrent.Executor executor2 = networkScanInfo.mExecutor;
            switch (message.what) {
                case 1:
                case 4:
                    try {
                        android.os.Parcelable[] parcelableArray = message.getData().getParcelableArray(android.telephony.TelephonyScanManager.SCAN_RESULT_KEY);
                        final android.telephony.CellInfo[] cellInfoArr = new android.telephony.CellInfo[parcelableArray.length];
                        while (i < parcelableArray.length) {
                            cellInfoArr[i] = (android.telephony.CellInfo) parcelableArray[i];
                            i++;
                        }
                        executor2.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyScanManager$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.telephony.TelephonyScanManager.AnonymousClass1.lambda$handleMessage$1(cellInfoArr, networkScanCallback2);
                            }
                        });
                        return;
                    } catch (java.lang.Exception e2) {
                        com.android.telephony.Rlog.e(android.telephony.TelephonyScanManager.TAG, "Exception in networkscan callback onResults", e2);
                        return;
                    }
                case 2:
                    try {
                        final int i2 = message.arg1;
                        executor2.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyScanManager$1$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.telephony.TelephonyScanManager.AnonymousClass1.lambda$handleMessage$2(i2, networkScanCallback2);
                            }
                        });
                        synchronized (android.telephony.TelephonyScanManager.this.mScanInfo) {
                            android.telephony.TelephonyScanManager.this.mScanInfo.remove(message.arg2);
                        }
                        return;
                    } catch (java.lang.Exception e3) {
                        com.android.telephony.Rlog.e(android.telephony.TelephonyScanManager.TAG, "Exception in networkscan callback onError", e3);
                        return;
                    }
                case 3:
                    try {
                        executor2.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyScanManager$1$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.telephony.TelephonyScanManager.AnonymousClass1.lambda$handleMessage$3(android.telephony.TelephonyScanManager.NetworkScanCallback.this);
                            }
                        });
                        synchronized (android.telephony.TelephonyScanManager.this.mScanInfo) {
                            android.telephony.TelephonyScanManager.this.mScanInfo.remove(message.arg2);
                        }
                        return;
                    } catch (java.lang.Exception e4) {
                        com.android.telephony.Rlog.e(android.telephony.TelephonyScanManager.TAG, "Exception in networkscan callback onComplete", e4);
                        return;
                    }
                default:
                    com.android.telephony.Rlog.e(android.telephony.TelephonyScanManager.TAG, "Unhandled message " + java.lang.Integer.toHexString(message.what));
                    return;
            }
        }

        static /* synthetic */ void lambda$handleMessage$1(android.telephony.CellInfo[] cellInfoArr, android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback) {
            com.android.telephony.Rlog.d(android.telephony.TelephonyScanManager.TAG, "onResults: " + java.util.Arrays.toString(cellInfoArr));
            networkScanCallback.onResults(java.util.Arrays.asList(cellInfoArr));
        }

        static /* synthetic */ void lambda$handleMessage$2(int i, android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback) {
            com.android.telephony.Rlog.d(android.telephony.TelephonyScanManager.TAG, "onError: " + i);
            networkScanCallback.onError(i);
        }

        static /* synthetic */ void lambda$handleMessage$3(android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback) {
            com.android.telephony.Rlog.d(android.telephony.TelephonyScanManager.TAG, "onComplete");
            networkScanCallback.onComplete();
        }
    }

    public android.telephony.NetworkScan requestNetworkScan(int i, boolean z, android.telephony.NetworkScanRequest networkScanRequest, java.util.concurrent.Executor executor, android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback, java.lang.String str, java.lang.String str2) {
        try {
            java.util.Objects.requireNonNull(networkScanRequest, "Request was null");
            java.util.Objects.requireNonNull(networkScanCallback, "Callback was null");
            java.util.Objects.requireNonNull(executor, "Executor was null");
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            synchronized (this.mScanInfo) {
                int requestNetworkScan = iTelephony.requestNetworkScan(i, z, networkScanRequest, this.mMessenger, new android.os.Binder(), str, str2);
                if (requestNetworkScan != -1) {
                    iTelephony.asBinder().linkToDeath(this.mDeathRecipient, 0);
                    saveScanInfo(requestNetworkScan, networkScanRequest, executor, networkScanCallback);
                    return new android.telephony.NetworkScan(requestNetworkScan, i);
                }
                com.android.telephony.Rlog.e(TAG, "Failed to initiate network scan");
                return null;
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "requestNetworkScan RemoteException", e);
            return null;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "requestNetworkScan NPE", e2);
            return null;
        }
    }

    private void saveScanInfo(int i, android.telephony.NetworkScanRequest networkScanRequest, java.util.concurrent.Executor executor, android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        this.mScanInfo.put(i, new android.telephony.TelephonyScanManager.NetworkScanInfo(networkScanRequest, executor, networkScanCallback));
    }

    private com.android.internal.telephony.ITelephony getITelephony() {
        return com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }
}
