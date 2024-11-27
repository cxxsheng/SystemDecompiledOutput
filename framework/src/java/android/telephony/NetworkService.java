package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class NetworkService extends android.app.Service {
    private static final int NETWORK_SERVICE_CREATE_NETWORK_SERVICE_PROVIDER = 1;
    private static final int NETWORK_SERVICE_GET_REGISTRATION_INFO = 4;
    private static final int NETWORK_SERVICE_INDICATION_NETWORK_INFO_CHANGED = 7;
    private static final int NETWORK_SERVICE_REGISTER_FOR_INFO_CHANGE = 5;
    private static final int NETWORK_SERVICE_REMOVE_ALL_NETWORK_SERVICE_PROVIDERS = 3;
    private static final int NETWORK_SERVICE_REMOVE_NETWORK_SERVICE_PROVIDER = 2;
    private static final int NETWORK_SERVICE_UNREGISTER_FOR_INFO_CHANGE = 6;
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.NetworkService";
    private final android.telephony.NetworkService.NetworkServiceHandler mHandler;
    private final java.lang.String TAG = android.telephony.NetworkService.class.getSimpleName();
    private final android.util.SparseArray<android.telephony.NetworkService.NetworkServiceProvider> mServiceMap = new android.util.SparseArray<>();
    public final android.telephony.NetworkService.INetworkServiceWrapper mBinder = new android.telephony.NetworkService.INetworkServiceWrapper();
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread(this.TAG);

    public abstract android.telephony.NetworkService.NetworkServiceProvider onCreateNetworkServiceProvider(int i);

    public abstract class NetworkServiceProvider implements java.lang.AutoCloseable {
        private final java.util.List<android.telephony.INetworkServiceCallback> mNetworkRegistrationInfoChangedCallbacks = new java.util.ArrayList();
        private final int mSlotIndex;

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public NetworkServiceProvider(int i) {
            this.mSlotIndex = i;
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        public void requestNetworkRegistrationInfo(int i, android.telephony.NetworkServiceCallback networkServiceCallback) {
            networkServiceCallback.onRequestNetworkRegistrationInfoComplete(1, null);
        }

        public final void notifyNetworkRegistrationInfoChanged() {
            android.telephony.NetworkService.this.mHandler.obtainMessage(7, this.mSlotIndex, 0, null).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForInfoChanged(android.telephony.INetworkServiceCallback iNetworkServiceCallback) {
            synchronized (this.mNetworkRegistrationInfoChangedCallbacks) {
                this.mNetworkRegistrationInfoChangedCallbacks.add(iNetworkServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregisterForInfoChanged(android.telephony.INetworkServiceCallback iNetworkServiceCallback) {
            synchronized (this.mNetworkRegistrationInfoChangedCallbacks) {
                this.mNetworkRegistrationInfoChangedCallbacks.remove(iNetworkServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyInfoChangedToCallbacks() {
            java.util.Iterator<android.telephony.INetworkServiceCallback> it = this.mNetworkRegistrationInfoChangedCallbacks.iterator();
            while (it.hasNext()) {
                try {
                    it.next().onNetworkStateChanged();
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    private class NetworkServiceHandler extends android.os.Handler {
        NetworkServiceHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.arg1;
            android.telephony.INetworkServiceCallback iNetworkServiceCallback = (android.telephony.INetworkServiceCallback) message.obj;
            android.telephony.NetworkService.NetworkServiceProvider networkServiceProvider = (android.telephony.NetworkService.NetworkServiceProvider) android.telephony.NetworkService.this.mServiceMap.get(i);
            switch (message.what) {
                case 1:
                    if (networkServiceProvider == null) {
                        android.telephony.NetworkService.this.mServiceMap.put(i, android.telephony.NetworkService.this.onCreateNetworkServiceProvider(i));
                        break;
                    }
                    break;
                case 2:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.close();
                        android.telephony.NetworkService.this.mServiceMap.remove(i);
                        break;
                    }
                    break;
                case 3:
                    for (int i2 = 0; i2 < android.telephony.NetworkService.this.mServiceMap.size(); i2++) {
                        android.telephony.NetworkService.NetworkServiceProvider networkServiceProvider2 = (android.telephony.NetworkService.NetworkServiceProvider) android.telephony.NetworkService.this.mServiceMap.get(i2);
                        if (networkServiceProvider2 != null) {
                            networkServiceProvider2.close();
                        }
                    }
                    android.telephony.NetworkService.this.mServiceMap.clear();
                    break;
                case 4:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.requestNetworkRegistrationInfo(message.arg2, new android.telephony.NetworkServiceCallback(iNetworkServiceCallback));
                        break;
                    }
                    break;
                case 5:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.registerForInfoChanged(iNetworkServiceCallback);
                        break;
                    }
                    break;
                case 6:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.unregisterForInfoChanged(iNetworkServiceCallback);
                        break;
                    }
                    break;
                case 7:
                    if (networkServiceProvider != null) {
                        networkServiceProvider.notifyInfoChangedToCallbacks();
                        break;
                    }
                    break;
            }
        }
    }

    public NetworkService() {
        this.mHandlerThread.start();
        this.mHandler = new android.telephony.NetworkService.NetworkServiceHandler(this.mHandlerThread.getLooper());
        log("network service created");
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (intent == null || !SERVICE_INTERFACE.equals(intent.getAction())) {
            loge("Unexpected intent " + intent);
            return null;
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        this.mHandler.obtainMessage(3, 0, 0, null).sendToTarget();
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandlerThread.quitSafely();
        super.onDestroy();
    }

    private class INetworkServiceWrapper extends android.telephony.INetworkService.Stub {
        private INetworkServiceWrapper() {
        }

        @Override // android.telephony.INetworkService
        public void createNetworkServiceProvider(int i) {
            android.telephony.NetworkService.this.mHandler.obtainMessage(1, i, 0, null).sendToTarget();
        }

        @Override // android.telephony.INetworkService
        public void removeNetworkServiceProvider(int i) {
            android.telephony.NetworkService.this.mHandler.obtainMessage(2, i, 0, null).sendToTarget();
        }

        @Override // android.telephony.INetworkService
        public void requestNetworkRegistrationInfo(int i, int i2, android.telephony.INetworkServiceCallback iNetworkServiceCallback) {
            android.telephony.NetworkService.this.mHandler.obtainMessage(4, i, i2, iNetworkServiceCallback).sendToTarget();
        }

        @Override // android.telephony.INetworkService
        public void registerForNetworkRegistrationInfoChanged(int i, android.telephony.INetworkServiceCallback iNetworkServiceCallback) {
            android.telephony.NetworkService.this.mHandler.obtainMessage(5, i, 0, iNetworkServiceCallback).sendToTarget();
        }

        @Override // android.telephony.INetworkService
        public void unregisterForNetworkRegistrationInfoChanged(int i, android.telephony.INetworkServiceCallback iNetworkServiceCallback) {
            android.telephony.NetworkService.this.mHandler.obtainMessage(6, i, 0, iNetworkServiceCallback).sendToTarget();
        }
    }

    private final void log(java.lang.String str) {
        com.android.telephony.Rlog.d(this.TAG, str);
    }

    private final void loge(java.lang.String str) {
        com.android.telephony.Rlog.e(this.TAG, str);
    }
}
