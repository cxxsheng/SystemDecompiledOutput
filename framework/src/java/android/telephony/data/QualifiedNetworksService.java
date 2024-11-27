package android.telephony.data;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class QualifiedNetworksService extends android.app.Service {
    private static final int QNS_APN_THROTTLE_STATUS_CHANGED = 5;
    private static final int QNS_CREATE_NETWORK_AVAILABILITY_PROVIDER = 1;
    private static final int QNS_EMERGENCY_DATA_NETWORK_PREFERRED_TRANSPORT_CHANGED = 6;
    private static final int QNS_RECONNECT_QUALIFIED_NETWORK = 8;
    private static final int QNS_REMOVE_ALL_NETWORK_AVAILABILITY_PROVIDERS = 3;
    private static final int QNS_REMOVE_NETWORK_AVAILABILITY_PROVIDER = 2;
    private static final int QNS_REQUEST_NETWORK_VALIDATION = 7;
    private static final int QNS_UPDATE_QUALIFIED_NETWORKS = 4;
    public static final java.lang.String QUALIFIED_NETWORKS_SERVICE_INTERFACE = "android.telephony.data.QualifiedNetworksService";
    private static final java.lang.String TAG = android.telephony.data.QualifiedNetworksService.class.getSimpleName();
    private static final com.android.internal.telephony.flags.FeatureFlags sFeatureFlag = new com.android.internal.telephony.flags.FeatureFlagsImpl();
    private final android.telephony.data.QualifiedNetworksService.QualifiedNetworksServiceHandler mHandler;
    private final android.util.SparseArray<android.telephony.data.QualifiedNetworksService.NetworkAvailabilityProvider> mProviders = new android.util.SparseArray<>();
    public final android.telephony.data.QualifiedNetworksService.IQualifiedNetworksServiceWrapper mBinder = new android.telephony.data.QualifiedNetworksService.IQualifiedNetworksServiceWrapper();
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread(TAG);

    public abstract android.telephony.data.QualifiedNetworksService.NetworkAvailabilityProvider onCreateNetworkAvailabilityProvider(int i);

    public abstract class NetworkAvailabilityProvider implements java.lang.AutoCloseable {
        private android.telephony.data.IQualifiedNetworksServiceCallback mCallback;
        private android.util.SparseArray<int[]> mQualifiedNetworkTypesList = new android.util.SparseArray<>();
        private final int mSlotIndex;

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public NetworkAvailabilityProvider(int i) {
            this.mSlotIndex = i;
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForQualifiedNetworkTypesChanged(android.telephony.data.IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) {
            this.mCallback = iQualifiedNetworksServiceCallback;
            if (this.mCallback != null) {
                for (int i = 0; i < this.mQualifiedNetworkTypesList.size(); i++) {
                    try {
                        this.mCallback.onQualifiedNetworkTypesChanged(this.mQualifiedNetworkTypesList.keyAt(i), this.mQualifiedNetworkTypesList.valueAt(i));
                    } catch (android.os.RemoteException e) {
                        android.telephony.data.QualifiedNetworksService.this.loge("Failed to call onQualifiedNetworksChanged. " + e);
                    }
                }
            }
        }

        public final void updateQualifiedNetworkTypes(int i, java.util.List<java.lang.Integer> list) {
            android.telephony.data.QualifiedNetworksService.this.mHandler.obtainMessage(4, this.mSlotIndex, i, list.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    int intValue;
                    intValue = ((java.lang.Integer) obj).intValue();
                    return intValue;
                }
            }).toArray()).sendToTarget();
        }

        public final void reconnectQualifiedNetworkType(int i, int i2) {
            android.telephony.data.QualifiedNetworksService.this.mHandler.obtainMessage(8, this.mSlotIndex, i, new java.lang.Integer(i2)).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onUpdateQualifiedNetworkTypes(int i, int[] iArr) {
            this.mQualifiedNetworkTypesList.put(i, iArr);
            if (this.mCallback != null) {
                try {
                    this.mCallback.onQualifiedNetworkTypesChanged(i, iArr);
                } catch (android.os.RemoteException e) {
                    android.telephony.data.QualifiedNetworksService.this.loge("Failed to call onQualifiedNetworksChanged. " + e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onReconnectQualifiedNetworkType(int i, int i2) {
            if (this.mCallback != null) {
                try {
                    this.mCallback.onReconnectQualifedNetworkType(i, i2);
                } catch (android.os.RemoteException e) {
                    android.telephony.data.QualifiedNetworksService.this.loge("Failed to call onReconnectQualifiedNetworkType. " + e);
                }
            }
        }

        public void reportThrottleStatusChanged(java.util.List<android.telephony.data.ThrottleStatus> list) {
            android.util.Log.d(android.telephony.data.QualifiedNetworksService.TAG, "reportThrottleStatusChanged: statuses size=" + list.size());
        }

        public void reportEmergencyDataNetworkPreferredTransportChanged(int i) {
            android.util.Log.d(android.telephony.data.QualifiedNetworksService.TAG, "reportEmergencyDataNetworkPreferredTransportChanged: " + android.telephony.AccessNetworkConstants.transportTypeToString(i));
        }

        public void requestNetworkValidation(int i, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
            java.util.Objects.requireNonNull(executor, "executor cannot be null");
            java.util.Objects.requireNonNull(consumer, "resultCodeCallback cannot be null");
            if (!android.telephony.data.QualifiedNetworksService.sFeatureFlag.networkValidation()) {
                android.telephony.data.QualifiedNetworksService.this.loge("networkValidation feature is disabled");
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(1);
                    }
                });
            } else {
                android.telephony.data.QualifiedNetworksService.this.mHandler.obtainMessage(7, this.mSlotIndex, 0, new android.telephony.data.QualifiedNetworksService.NetworkValidationRequestData(i, new android.telephony.data.QualifiedNetworksService.NetworkAvailabilityProvider.AnonymousClass1(executor, consumer))).sendToTarget();
            }
        }

        /* renamed from: android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$1, reason: invalid class name */
        class AnonymousClass1 extends com.android.internal.telephony.IIntegerConsumer.Stub {
            final /* synthetic */ java.util.concurrent.Executor val$executor;
            final /* synthetic */ java.util.function.Consumer val$resultCodeCallback;

            AnonymousClass1(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
                this.val$executor = executor;
                this.val$resultCodeCallback = consumer;
            }

            @Override // com.android.internal.telephony.IIntegerConsumer
            public void accept(final int i) {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$resultCodeCallback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.data.QualifiedNetworksService$NetworkAvailabilityProvider$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Integer.valueOf(i));
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onRequestNetworkValidation(android.telephony.data.QualifiedNetworksService.NetworkValidationRequestData networkValidationRequestData) {
            try {
                android.telephony.data.QualifiedNetworksService.this.log("onRequestNetworkValidation");
                this.mCallback.onNetworkValidationRequested(networkValidationRequestData.mNetworkCapability, networkValidationRequestData.mCallback);
            } catch (android.os.RemoteException | java.lang.NullPointerException e) {
                android.telephony.data.QualifiedNetworksService.this.loge("Failed to call onRequestNetworkValidation. " + e);
                com.android.internal.telephony.IIntegerConsumer iIntegerConsumer = networkValidationRequestData.mCallback;
                java.util.Objects.requireNonNull(iIntegerConsumer);
                com.android.internal.util.FunctionalUtils.ignoreRemoteException(new android.telephony.data.DataService$DataServiceHandler$$ExternalSyntheticLambda0(iIntegerConsumer)).accept(1);
            }
        }
    }

    private class QualifiedNetworksServiceHandler extends android.os.Handler {
        QualifiedNetworksServiceHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.arg1;
            android.telephony.data.QualifiedNetworksService.NetworkAvailabilityProvider networkAvailabilityProvider = (android.telephony.data.QualifiedNetworksService.NetworkAvailabilityProvider) android.telephony.data.QualifiedNetworksService.this.mProviders.get(i);
            switch (message.what) {
                case 1:
                    if (android.telephony.data.QualifiedNetworksService.this.mProviders.get(i) != null) {
                        android.telephony.data.QualifiedNetworksService.this.loge("Network availability provider for slot " + i + " already existed.");
                        break;
                    } else {
                        android.telephony.data.QualifiedNetworksService.NetworkAvailabilityProvider onCreateNetworkAvailabilityProvider = android.telephony.data.QualifiedNetworksService.this.onCreateNetworkAvailabilityProvider(i);
                        if (onCreateNetworkAvailabilityProvider != null) {
                            android.telephony.data.QualifiedNetworksService.this.mProviders.put(i, onCreateNetworkAvailabilityProvider);
                            onCreateNetworkAvailabilityProvider.registerForQualifiedNetworkTypesChanged((android.telephony.data.IQualifiedNetworksServiceCallback) message.obj);
                            break;
                        } else {
                            android.telephony.data.QualifiedNetworksService.this.loge("Failed to create network availability provider. slot index = " + i);
                            break;
                        }
                    }
                case 2:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.close();
                        android.telephony.data.QualifiedNetworksService.this.mProviders.remove(i);
                        break;
                    }
                    break;
                case 3:
                    for (int i2 = 0; i2 < android.telephony.data.QualifiedNetworksService.this.mProviders.size(); i2++) {
                        android.telephony.data.QualifiedNetworksService.NetworkAvailabilityProvider networkAvailabilityProvider2 = (android.telephony.data.QualifiedNetworksService.NetworkAvailabilityProvider) android.telephony.data.QualifiedNetworksService.this.mProviders.get(i2);
                        if (networkAvailabilityProvider2 != null) {
                            networkAvailabilityProvider2.close();
                        }
                    }
                    android.telephony.data.QualifiedNetworksService.this.mProviders.clear();
                    break;
                case 4:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.onUpdateQualifiedNetworkTypes(message.arg2, (int[]) message.obj);
                        break;
                    }
                    break;
                case 5:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.reportThrottleStatusChanged((java.util.List) message.obj);
                        break;
                    }
                    break;
                case 6:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.reportEmergencyDataNetworkPreferredTransportChanged(message.arg2);
                        break;
                    }
                    break;
                case 7:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.onRequestNetworkValidation((android.telephony.data.QualifiedNetworksService.NetworkValidationRequestData) message.obj);
                        break;
                    }
                    break;
                case 8:
                    if (networkAvailabilityProvider != null) {
                        networkAvailabilityProvider.onReconnectQualifiedNetworkType(message.arg2, ((java.lang.Integer) message.obj).intValue());
                        break;
                    }
                    break;
            }
        }
    }

    public QualifiedNetworksService() {
        this.mHandlerThread.start();
        this.mHandler = new android.telephony.data.QualifiedNetworksService.QualifiedNetworksServiceHandler(this.mHandlerThread.getLooper());
        log("Qualified networks service created");
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (intent == null || !QUALIFIED_NETWORKS_SERVICE_INTERFACE.equals(intent.getAction())) {
            loge("Unexpected intent " + intent);
            return null;
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        this.mHandler.obtainMessage(3).sendToTarget();
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandlerThread.quit();
    }

    private class IQualifiedNetworksServiceWrapper extends android.telephony.data.IQualifiedNetworksService.Stub {
        private IQualifiedNetworksServiceWrapper() {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void createNetworkAvailabilityProvider(int i, android.telephony.data.IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) {
            android.telephony.data.QualifiedNetworksService.this.mHandler.obtainMessage(1, i, 0, iQualifiedNetworksServiceCallback).sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void removeNetworkAvailabilityProvider(int i) {
            android.telephony.data.QualifiedNetworksService.this.mHandler.obtainMessage(2, i, 0).sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportThrottleStatusChanged(int i, java.util.List<android.telephony.data.ThrottleStatus> list) {
            android.telephony.data.QualifiedNetworksService.this.mHandler.obtainMessage(5, i, 0, list).sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void reportEmergencyDataNetworkPreferredTransportChanged(int i, int i2) {
            android.telephony.data.QualifiedNetworksService.this.mHandler.obtainMessage(6, i, i2).sendToTarget();
        }
    }

    private static final class NetworkValidationRequestData {
        final com.android.internal.telephony.IIntegerConsumer mCallback;
        final int mNetworkCapability;

        private NetworkValidationRequestData(int i, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) {
            this.mNetworkCapability = i;
            this.mCallback = iIntegerConsumer;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(java.lang.String str) {
        com.android.telephony.Rlog.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loge(java.lang.String str) {
        com.android.telephony.Rlog.e(TAG, str);
    }
}
