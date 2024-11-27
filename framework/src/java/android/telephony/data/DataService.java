package android.telephony.data;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class DataService extends android.app.Service {
    private static final int DATA_SERVICE_CREATE_DATA_SERVICE_PROVIDER = 1;
    private static final int DATA_SERVICE_INDICATION_APN_UNTHROTTLED = 16;
    private static final int DATA_SERVICE_INDICATION_DATA_CALL_LIST_CHANGED = 11;
    private static final int DATA_SERVICE_REMOVE_ALL_DATA_SERVICE_PROVIDERS = 3;
    private static final int DATA_SERVICE_REMOVE_DATA_SERVICE_PROVIDER = 2;
    private static final int DATA_SERVICE_REQUEST_CANCEL_HANDOVER = 13;
    private static final int DATA_SERVICE_REQUEST_DEACTIVATE_DATA_CALL = 5;
    private static final int DATA_SERVICE_REQUEST_REGISTER_APN_UNTHROTTLED = 14;
    private static final int DATA_SERVICE_REQUEST_REGISTER_DATA_CALL_LIST_CHANGED = 9;
    private static final int DATA_SERVICE_REQUEST_REQUEST_DATA_CALL_LIST = 8;
    private static final int DATA_SERVICE_REQUEST_SETUP_DATA_CALL = 4;
    private static final int DATA_SERVICE_REQUEST_SET_DATA_PROFILE = 7;
    private static final int DATA_SERVICE_REQUEST_SET_INITIAL_ATTACH_APN = 6;
    private static final int DATA_SERVICE_REQUEST_START_HANDOVER = 12;
    private static final int DATA_SERVICE_REQUEST_UNREGISTER_APN_UNTHROTTLED = 15;
    private static final int DATA_SERVICE_REQUEST_UNREGISTER_DATA_CALL_LIST_CHANGED = 10;
    private static final int DATA_SERVICE_REQUEST_VALIDATION = 17;
    public static final int REQUEST_REASON_HANDOVER = 3;
    public static final int REQUEST_REASON_NORMAL = 1;
    public static final int REQUEST_REASON_SHUTDOWN = 2;
    public static final int REQUEST_REASON_UNKNOWN = 0;
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.data.DataService";
    private static final java.lang.String TAG = android.telephony.data.DataService.class.getSimpleName();
    private final android.telephony.data.DataService.DataServiceHandler mHandler;
    private final java.util.concurrent.Executor mHandlerExecutor;
    private final android.util.SparseArray<android.telephony.data.DataService.DataServiceProvider> mServiceMap = new android.util.SparseArray<>();
    public final android.telephony.data.DataService.IDataServiceWrapper mBinder = new android.telephony.data.DataService.IDataServiceWrapper();
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread(TAG);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeactivateDataReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SetupDataReason {
    }

    public abstract android.telephony.data.DataService.DataServiceProvider onCreateDataServiceProvider(int i);

    public abstract class DataServiceProvider implements java.lang.AutoCloseable {
        private final int mSlotIndex;
        private final java.util.List<android.telephony.data.IDataServiceCallback> mDataCallListChangedCallbacks = new java.util.ArrayList();
        private final java.util.List<android.telephony.data.IDataServiceCallback> mApnUnthrottledCallbacks = new java.util.ArrayList();

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public DataServiceProvider(int i) {
            this.mSlotIndex = i;
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        public void setupDataCall(int i, android.telephony.data.DataProfile dataProfile, boolean z, boolean z2, int i2, android.net.LinkProperties linkProperties, android.telephony.data.DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetupDataCallComplete(1, null);
            }
        }

        public void setupDataCall(int i, android.telephony.data.DataProfile dataProfile, boolean z, boolean z2, int i2, android.net.LinkProperties linkProperties, int i3, android.telephony.data.NetworkSliceInfo networkSliceInfo, android.telephony.data.TrafficDescriptor trafficDescriptor, boolean z3, android.telephony.data.DataServiceCallback dataServiceCallback) {
            setupDataCall(i, dataProfile, z, z2, i2, linkProperties, dataServiceCallback);
        }

        public void deactivateDataCall(int i, int i2, android.telephony.data.DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onDeactivateDataCallComplete(1);
            }
        }

        public void setInitialAttachApn(android.telephony.data.DataProfile dataProfile, boolean z, android.telephony.data.DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetInitialAttachApnComplete(1);
            }
        }

        public void setDataProfile(java.util.List<android.telephony.data.DataProfile> list, boolean z, android.telephony.data.DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetDataProfileComplete(1);
            }
        }

        public void startHandover(int i, android.telephony.data.DataServiceCallback dataServiceCallback) {
            java.util.Objects.requireNonNull(dataServiceCallback, "callback cannot be null");
            android.util.Log.d(android.telephony.data.DataService.TAG, "startHandover: " + i);
            dataServiceCallback.onHandoverStarted(1);
        }

        public void cancelHandover(int i, android.telephony.data.DataServiceCallback dataServiceCallback) {
            java.util.Objects.requireNonNull(dataServiceCallback, "callback cannot be null");
            android.util.Log.d(android.telephony.data.DataService.TAG, "cancelHandover: " + i);
            dataServiceCallback.onHandoverCancelled(1);
        }

        public void requestDataCallList(android.telephony.data.DataServiceCallback dataServiceCallback) {
            dataServiceCallback.onRequestDataCallListComplete(1, java.util.Collections.EMPTY_LIST);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForDataCallListChanged(android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            synchronized (this.mDataCallListChangedCallbacks) {
                this.mDataCallListChangedCallbacks.add(iDataServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregisterForDataCallListChanged(android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            synchronized (this.mDataCallListChangedCallbacks) {
                this.mDataCallListChangedCallbacks.remove(iDataServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForApnUnthrottled(android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            synchronized (this.mApnUnthrottledCallbacks) {
                this.mApnUnthrottledCallbacks.add(iDataServiceCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unregisterForApnUnthrottled(android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            synchronized (this.mApnUnthrottledCallbacks) {
                this.mApnUnthrottledCallbacks.remove(iDataServiceCallback);
            }
        }

        public void requestNetworkValidation(int i, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
            java.util.Objects.requireNonNull(executor, "executor cannot be null");
            java.util.Objects.requireNonNull(consumer, "resultCodeCallback cannot be null");
            android.util.Log.d(android.telephony.data.DataService.TAG, "requestNetworkValidation: " + i);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.data.DataService$DataServiceProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(1);
                }
            });
        }

        public final void notifyDataCallListChanged(java.util.List<android.telephony.data.DataCallResponse> list) {
            synchronized (this.mDataCallListChangedCallbacks) {
                java.util.Iterator<android.telephony.data.IDataServiceCallback> it = this.mDataCallListChangedCallbacks.iterator();
                while (it.hasNext()) {
                    android.telephony.data.DataService.this.mHandler.obtainMessage(11, this.mSlotIndex, 0, new android.telephony.data.DataService.DataCallListChangedIndication(list, it.next())).sendToTarget();
                }
            }
        }

        public final void notifyApnUnthrottled(java.lang.String str) {
            synchronized (this.mApnUnthrottledCallbacks) {
                java.util.Iterator<android.telephony.data.IDataServiceCallback> it = this.mApnUnthrottledCallbacks.iterator();
                while (it.hasNext()) {
                    android.telephony.data.DataService.this.mHandler.obtainMessage(16, this.mSlotIndex, 0, new android.telephony.data.DataService.ApnUnthrottledIndication(str, it.next())).sendToTarget();
                }
            }
        }

        public final void notifyDataProfileUnthrottled(android.telephony.data.DataProfile dataProfile) {
            synchronized (this.mApnUnthrottledCallbacks) {
                java.util.Iterator<android.telephony.data.IDataServiceCallback> it = this.mApnUnthrottledCallbacks.iterator();
                while (it.hasNext()) {
                    android.telephony.data.DataService.this.mHandler.obtainMessage(16, this.mSlotIndex, 0, new android.telephony.data.DataService.ApnUnthrottledIndication(dataProfile, it.next())).sendToTarget();
                }
            }
        }
    }

    private static final class SetupDataCallRequest {
        public final int accessNetworkType;
        public final boolean allowRoaming;
        public final android.telephony.data.IDataServiceCallback callback;
        public final android.telephony.data.DataProfile dataProfile;
        public final boolean isRoaming;
        public final android.net.LinkProperties linkProperties;
        public final boolean matchAllRuleAllowed;
        public final int pduSessionId;
        public final int reason;
        public final android.telephony.data.NetworkSliceInfo sliceInfo;
        public final android.telephony.data.TrafficDescriptor trafficDescriptor;

        SetupDataCallRequest(int i, android.telephony.data.DataProfile dataProfile, boolean z, boolean z2, int i2, android.net.LinkProperties linkProperties, int i3, android.telephony.data.NetworkSliceInfo networkSliceInfo, android.telephony.data.TrafficDescriptor trafficDescriptor, boolean z3, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            this.accessNetworkType = i;
            this.dataProfile = dataProfile;
            this.isRoaming = z;
            this.allowRoaming = z2;
            this.linkProperties = linkProperties;
            this.reason = i2;
            this.pduSessionId = i3;
            this.sliceInfo = networkSliceInfo;
            this.trafficDescriptor = trafficDescriptor;
            this.matchAllRuleAllowed = z3;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class DeactivateDataCallRequest {
        public final android.telephony.data.IDataServiceCallback callback;
        public final int cid;
        public final int reason;

        DeactivateDataCallRequest(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            this.cid = i;
            this.reason = i2;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class SetInitialAttachApnRequest {
        public final android.telephony.data.IDataServiceCallback callback;
        public final android.telephony.data.DataProfile dataProfile;
        public final boolean isRoaming;

        SetInitialAttachApnRequest(android.telephony.data.DataProfile dataProfile, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            this.dataProfile = dataProfile;
            this.isRoaming = z;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class SetDataProfileRequest {
        public final android.telephony.data.IDataServiceCallback callback;
        public final java.util.List<android.telephony.data.DataProfile> dps;
        public final boolean isRoaming;

        SetDataProfileRequest(java.util.List<android.telephony.data.DataProfile> list, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            this.dps = list;
            this.isRoaming = z;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class BeginCancelHandoverRequest {
        public final android.telephony.data.IDataServiceCallback callback;
        public final int cid;

        BeginCancelHandoverRequest(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            this.cid = i;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class DataCallListChangedIndication {
        public final android.telephony.data.IDataServiceCallback callback;
        public final java.util.List<android.telephony.data.DataCallResponse> dataCallList;

        DataCallListChangedIndication(java.util.List<android.telephony.data.DataCallResponse> list, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            this.dataCallList = list;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class ApnUnthrottledIndication {
        public final java.lang.String apn;
        public final android.telephony.data.IDataServiceCallback callback;
        public final android.telephony.data.DataProfile dataProfile;

        ApnUnthrottledIndication(java.lang.String str, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            this.dataProfile = null;
            this.apn = str;
            this.callback = iDataServiceCallback;
        }

        ApnUnthrottledIndication(android.telephony.data.DataProfile dataProfile, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            this.dataProfile = dataProfile;
            this.apn = null;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class ValidationRequest {
        public final com.android.internal.telephony.IIntegerConsumer callback;
        public final int cid;
        public final java.util.concurrent.Executor executor;

        ValidationRequest(int i, java.util.concurrent.Executor executor, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) {
            this.cid = i;
            this.executor = executor;
            this.callback = iIntegerConsumer;
        }
    }

    private class DataServiceHandler extends android.os.Handler {
        DataServiceHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.telephony.data.DataServiceCallback dataServiceCallback;
            int i = message.arg1;
            android.telephony.data.DataService.DataServiceProvider dataServiceProvider = (android.telephony.data.DataService.DataServiceProvider) android.telephony.data.DataService.this.mServiceMap.get(i);
            switch (message.what) {
                case 1:
                    android.telephony.data.DataService.DataServiceProvider onCreateDataServiceProvider = android.telephony.data.DataService.this.onCreateDataServiceProvider(message.arg1);
                    if (onCreateDataServiceProvider != null) {
                        android.telephony.data.DataService.this.mServiceMap.put(i, onCreateDataServiceProvider);
                        break;
                    }
                    break;
                case 2:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.close();
                        android.telephony.data.DataService.this.mServiceMap.remove(i);
                        break;
                    }
                    break;
                case 3:
                    for (int i2 = 0; i2 < android.telephony.data.DataService.this.mServiceMap.size(); i2++) {
                        android.telephony.data.DataService.DataServiceProvider dataServiceProvider2 = (android.telephony.data.DataService.DataServiceProvider) android.telephony.data.DataService.this.mServiceMap.get(i2);
                        if (dataServiceProvider2 != null) {
                            dataServiceProvider2.close();
                        }
                    }
                    android.telephony.data.DataService.this.mServiceMap.clear();
                    break;
                case 4:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.SetupDataCallRequest setupDataCallRequest = (android.telephony.data.DataService.SetupDataCallRequest) message.obj;
                        int i3 = setupDataCallRequest.accessNetworkType;
                        android.telephony.data.DataProfile dataProfile = setupDataCallRequest.dataProfile;
                        boolean z = setupDataCallRequest.isRoaming;
                        boolean z2 = setupDataCallRequest.allowRoaming;
                        int i4 = setupDataCallRequest.reason;
                        android.net.LinkProperties linkProperties = setupDataCallRequest.linkProperties;
                        int i5 = setupDataCallRequest.pduSessionId;
                        android.telephony.data.NetworkSliceInfo networkSliceInfo = setupDataCallRequest.sliceInfo;
                        android.telephony.data.TrafficDescriptor trafficDescriptor = setupDataCallRequest.trafficDescriptor;
                        boolean z3 = setupDataCallRequest.matchAllRuleAllowed;
                        if (setupDataCallRequest.callback != null) {
                            dataServiceCallback = new android.telephony.data.DataServiceCallback(setupDataCallRequest.callback);
                        } else {
                            dataServiceCallback = null;
                        }
                        dataServiceProvider.setupDataCall(i3, dataProfile, z, z2, i4, linkProperties, i5, networkSliceInfo, trafficDescriptor, z3, dataServiceCallback);
                        break;
                    }
                    break;
                case 5:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.DeactivateDataCallRequest deactivateDataCallRequest = (android.telephony.data.DataService.DeactivateDataCallRequest) message.obj;
                        dataServiceProvider.deactivateDataCall(deactivateDataCallRequest.cid, deactivateDataCallRequest.reason, deactivateDataCallRequest.callback != null ? new android.telephony.data.DataServiceCallback(deactivateDataCallRequest.callback) : null);
                        break;
                    }
                    break;
                case 6:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.SetInitialAttachApnRequest setInitialAttachApnRequest = (android.telephony.data.DataService.SetInitialAttachApnRequest) message.obj;
                        dataServiceProvider.setInitialAttachApn(setInitialAttachApnRequest.dataProfile, setInitialAttachApnRequest.isRoaming, setInitialAttachApnRequest.callback != null ? new android.telephony.data.DataServiceCallback(setInitialAttachApnRequest.callback) : null);
                        break;
                    }
                    break;
                case 7:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.SetDataProfileRequest setDataProfileRequest = (android.telephony.data.DataService.SetDataProfileRequest) message.obj;
                        dataServiceProvider.setDataProfile(setDataProfileRequest.dps, setDataProfileRequest.isRoaming, setDataProfileRequest.callback != null ? new android.telephony.data.DataServiceCallback(setDataProfileRequest.callback) : null);
                        break;
                    }
                    break;
                case 8:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.requestDataCallList(new android.telephony.data.DataServiceCallback((android.telephony.data.IDataServiceCallback) message.obj));
                        break;
                    }
                    break;
                case 9:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.registerForDataCallListChanged((android.telephony.data.IDataServiceCallback) message.obj);
                        break;
                    }
                    break;
                case 10:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.unregisterForDataCallListChanged((android.telephony.data.IDataServiceCallback) message.obj);
                        break;
                    }
                    break;
                case 11:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.DataCallListChangedIndication dataCallListChangedIndication = (android.telephony.data.DataService.DataCallListChangedIndication) message.obj;
                        try {
                            dataCallListChangedIndication.callback.onDataCallListChanged(dataCallListChangedIndication.dataCallList);
                            break;
                        } catch (android.os.RemoteException e) {
                            android.telephony.data.DataService.this.loge("Failed to call onDataCallListChanged. " + e);
                            return;
                        }
                    }
                    break;
                case 12:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.BeginCancelHandoverRequest beginCancelHandoverRequest = (android.telephony.data.DataService.BeginCancelHandoverRequest) message.obj;
                        dataServiceProvider.startHandover(beginCancelHandoverRequest.cid, beginCancelHandoverRequest.callback != null ? new android.telephony.data.DataServiceCallback(beginCancelHandoverRequest.callback) : null);
                        break;
                    }
                    break;
                case 13:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.BeginCancelHandoverRequest beginCancelHandoverRequest2 = (android.telephony.data.DataService.BeginCancelHandoverRequest) message.obj;
                        dataServiceProvider.cancelHandover(beginCancelHandoverRequest2.cid, beginCancelHandoverRequest2.callback != null ? new android.telephony.data.DataServiceCallback(beginCancelHandoverRequest2.callback) : null);
                        break;
                    }
                    break;
                case 14:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.registerForApnUnthrottled((android.telephony.data.IDataServiceCallback) message.obj);
                        break;
                    }
                    break;
                case 15:
                    if (dataServiceProvider != null) {
                        dataServiceProvider.unregisterForApnUnthrottled((android.telephony.data.IDataServiceCallback) message.obj);
                        break;
                    }
                    break;
                case 16:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.ApnUnthrottledIndication apnUnthrottledIndication = (android.telephony.data.DataService.ApnUnthrottledIndication) message.obj;
                        try {
                            if (apnUnthrottledIndication.dataProfile != null) {
                                apnUnthrottledIndication.callback.onDataProfileUnthrottled(apnUnthrottledIndication.dataProfile);
                            } else {
                                apnUnthrottledIndication.callback.onApnUnthrottled(apnUnthrottledIndication.apn);
                            }
                            break;
                        } catch (android.os.RemoteException e2) {
                            android.telephony.data.DataService.this.loge("Failed to call onApnUnthrottled. " + e2);
                            return;
                        }
                    }
                    break;
                case 17:
                    if (dataServiceProvider != null) {
                        android.telephony.data.DataService.ValidationRequest validationRequest = (android.telephony.data.DataService.ValidationRequest) message.obj;
                        int i6 = validationRequest.cid;
                        java.util.concurrent.Executor executor = validationRequest.executor;
                        com.android.internal.telephony.IIntegerConsumer iIntegerConsumer = validationRequest.callback;
                        java.util.Objects.requireNonNull(iIntegerConsumer);
                        dataServiceProvider.requestNetworkValidation(i6, executor, com.android.internal.util.FunctionalUtils.ignoreRemoteException(new android.telephony.data.DataService$DataServiceHandler$$ExternalSyntheticLambda0(iIntegerConsumer)));
                        break;
                    }
                    break;
            }
        }
    }

    public DataService() {
        this.mHandlerThread.start();
        this.mHandler = new android.telephony.data.DataService.DataServiceHandler(this.mHandlerThread.getLooper());
        this.mHandlerExecutor = new android.os.HandlerExecutor(this.mHandler);
        log("Data service created");
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
        this.mHandler.obtainMessage(3).sendToTarget();
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandlerThread.quitSafely();
        super.onDestroy();
    }

    private class IDataServiceWrapper extends android.telephony.data.IDataService.Stub {
        private IDataServiceWrapper() {
        }

        @Override // android.telephony.data.IDataService
        public void createDataServiceProvider(int i) {
            android.telephony.data.DataService.this.mHandler.obtainMessage(1, i, 0).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void removeDataServiceProvider(int i) {
            android.telephony.data.DataService.this.mHandler.obtainMessage(2, i, 0).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void setupDataCall(int i, int i2, android.telephony.data.DataProfile dataProfile, boolean z, boolean z2, int i3, android.net.LinkProperties linkProperties, int i4, android.telephony.data.NetworkSliceInfo networkSliceInfo, android.telephony.data.TrafficDescriptor trafficDescriptor, boolean z3, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            android.telephony.data.DataService.this.mHandler.obtainMessage(4, i, 0, new android.telephony.data.DataService.SetupDataCallRequest(i2, dataProfile, z, z2, i3, linkProperties, i4, networkSliceInfo, trafficDescriptor, z3, iDataServiceCallback)).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void deactivateDataCall(int i, int i2, int i3, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            android.telephony.data.DataService.this.mHandler.obtainMessage(5, i, 0, new android.telephony.data.DataService.DeactivateDataCallRequest(i2, i3, iDataServiceCallback)).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void setInitialAttachApn(int i, android.telephony.data.DataProfile dataProfile, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            android.telephony.data.DataService.this.mHandler.obtainMessage(6, i, 0, new android.telephony.data.DataService.SetInitialAttachApnRequest(dataProfile, z, iDataServiceCallback)).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void setDataProfile(int i, java.util.List<android.telephony.data.DataProfile> list, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            android.telephony.data.DataService.this.mHandler.obtainMessage(7, i, 0, new android.telephony.data.DataService.SetDataProfileRequest(list, z, iDataServiceCallback)).sendToTarget();
        }

        @Override // android.telephony.data.IDataService
        public void requestDataCallList(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                android.telephony.data.DataService.this.loge("requestDataCallList: callback is null");
            } else {
                android.telephony.data.DataService.this.mHandler.obtainMessage(8, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void registerForDataCallListChanged(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                android.telephony.data.DataService.this.loge("registerForDataCallListChanged: callback is null");
            } else {
                android.telephony.data.DataService.this.mHandler.obtainMessage(9, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void unregisterForDataCallListChanged(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                android.telephony.data.DataService.this.loge("unregisterForDataCallListChanged: callback is null");
            } else {
                android.telephony.data.DataService.this.mHandler.obtainMessage(10, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void startHandover(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                android.telephony.data.DataService.this.loge("startHandover: callback is null");
            } else {
                android.telephony.data.DataService.this.mHandler.obtainMessage(12, i, 0, new android.telephony.data.DataService.BeginCancelHandoverRequest(i2, iDataServiceCallback)).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void cancelHandover(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                android.telephony.data.DataService.this.loge("cancelHandover: callback is null");
            } else {
                android.telephony.data.DataService.this.mHandler.obtainMessage(13, i, 0, new android.telephony.data.DataService.BeginCancelHandoverRequest(i2, iDataServiceCallback)).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void registerForUnthrottleApn(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                android.telephony.data.DataService.this.loge("registerForUnthrottleApn: callback is null");
            } else {
                android.telephony.data.DataService.this.mHandler.obtainMessage(14, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void unregisterForUnthrottleApn(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                android.telephony.data.DataService.this.loge("uregisterForUnthrottleApn: callback is null");
            } else {
                android.telephony.data.DataService.this.mHandler.obtainMessage(15, i, 0, iDataServiceCallback).sendToTarget();
            }
        }

        @Override // android.telephony.data.IDataService
        public void requestNetworkValidation(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) {
            if (iIntegerConsumer == null) {
                android.telephony.data.DataService.this.loge("requestNetworkValidation: resultCodeCallback is null");
            } else {
                android.telephony.data.DataService.this.mHandler.obtainMessage(17, i, 0, new android.telephony.data.DataService.ValidationRequest(i2, android.telephony.data.DataService.this.mHandlerExecutor, iIntegerConsumer)).sendToTarget();
            }
        }
    }

    private void log(java.lang.String str) {
        com.android.telephony.Rlog.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loge(java.lang.String str) {
        com.android.telephony.Rlog.e(TAG, str);
    }
}
