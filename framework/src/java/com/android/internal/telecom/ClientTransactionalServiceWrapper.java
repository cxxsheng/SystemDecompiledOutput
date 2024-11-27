package com.android.internal.telecom;

/* loaded from: classes5.dex */
public class ClientTransactionalServiceWrapper {
    private static final java.lang.String EXECUTOR_FAIL_MSG = "Telecom hit an exception while handling a CallEventCallback on an executor: ";
    private static final java.lang.String TAG = com.android.internal.telecom.ClientTransactionalServiceWrapper.class.getSimpleName();
    private final android.telecom.PhoneAccountHandle mPhoneAccountHandle;
    private final com.android.internal.telecom.ClientTransactionalServiceRepository mRepository;
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.android.internal.telecom.TransactionalCall> mCallIdToTransactionalCall = new java.util.concurrent.ConcurrentHashMap<>();
    private final com.android.internal.telecom.ICallEventCallback mCallEventCallback = new com.android.internal.telecom.ClientTransactionalServiceWrapper.AnonymousClass1();

    public ClientTransactionalServiceWrapper(android.telecom.PhoneAccountHandle phoneAccountHandle, com.android.internal.telecom.ClientTransactionalServiceRepository clientTransactionalServiceRepository) {
        this.mPhoneAccountHandle = phoneAccountHandle;
        this.mRepository = clientTransactionalServiceRepository;
    }

    public void untrackCall(java.lang.String str) {
        android.util.Log.i(TAG, android.text.TextUtils.formatSimple("removeCall: with id=[%s]", str));
        if (this.mCallIdToTransactionalCall.containsKey(str)) {
            com.android.internal.telecom.TransactionalCall remove = this.mCallIdToTransactionalCall.remove(str);
            if (remove.getCallControl() != null) {
                remove.setCallControl(null);
            }
        }
        if (this.mCallIdToTransactionalCall.size() == 0) {
            this.mRepository.removeServiceWrapper(this.mPhoneAccountHandle);
        }
    }

    public java.lang.String trackCall(android.telecom.CallAttributes callAttributes, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.telecom.CallControl, android.telecom.CallException> outcomeReceiver, android.telecom.CallControlCallback callControlCallback, android.telecom.CallEventCallback callEventCallback) {
        java.lang.String obj = java.util.UUID.randomUUID().toString();
        this.mCallIdToTransactionalCall.put(obj, new com.android.internal.telecom.TransactionalCall(obj, callAttributes, executor, outcomeReceiver, callControlCallback, callEventCallback));
        return obj;
    }

    public com.android.internal.telecom.ICallEventCallback getCallEventCallback() {
        return this.mCallEventCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ReceiverWrapper implements java.util.function.Consumer<java.lang.Boolean> {
        private final android.os.ResultReceiver mRepeaterReceiver;

        ReceiverWrapper(android.os.ResultReceiver resultReceiver) {
            this.mRepeaterReceiver = resultReceiver;
        }

        @Override // java.util.function.Consumer
        public void accept(java.lang.Boolean bool) {
            if (bool.booleanValue()) {
                this.mRepeaterReceiver.send(0, null);
            } else {
                this.mRepeaterReceiver.send(1, null);
            }
        }

        @Override // java.util.function.Consumer
        public java.util.function.Consumer<java.lang.Boolean> andThen(java.util.function.Consumer<? super java.lang.Boolean> consumer) {
            return super.andThen(consumer);
        }
    }

    /* renamed from: com.android.internal.telecom.ClientTransactionalServiceWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.telecom.ICallEventCallback.Stub {
        private static final java.lang.String ON_ANSWER = "onAnswer";
        private static final java.lang.String ON_AVAILABLE_CALL_ENDPOINTS = "onAvailableCallEndpointsChanged";
        private static final java.lang.String ON_CALL_STREAMING_FAILED = "onCallStreamingFailed";
        private static final java.lang.String ON_DISCONNECT = "onDisconnect";
        private static final java.lang.String ON_EVENT = "onEvent";
        private static final java.lang.String ON_MUTE_STATE_CHANGED = "onMuteStateChanged";
        private static final java.lang.String ON_REQ_ENDPOINT_CHANGE = "onRequestEndpointChange";
        private static final java.lang.String ON_SET_ACTIVE = "onSetActive";
        private static final java.lang.String ON_SET_INACTIVE = "onSetInactive";
        private static final java.lang.String ON_STREAMING_STARTED = "onStreamingStarted";
        private static final java.lang.String ON_VIDEO_STATE_CHANGED = "onVideoStateChanged";

        AnonymousClass1() {
        }

        private void handleCallEventCallback(final java.lang.String str, final java.lang.String str2, android.os.ResultReceiver resultReceiver, final java.lang.Object... objArr) {
            android.util.Log.i(com.android.internal.telecom.ClientTransactionalServiceWrapper.TAG, android.text.TextUtils.formatSimple("hCEC: id=[%s], action=[%s]", str2, str));
            com.android.internal.telecom.TransactionalCall transactionalCall = (com.android.internal.telecom.TransactionalCall) com.android.internal.telecom.ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(str2);
            if (transactionalCall != null) {
                final android.telecom.CallControlCallback callControlCallback = transactionalCall.getCallControlCallback();
                final com.android.internal.telecom.ClientTransactionalServiceWrapper.ReceiverWrapper receiverWrapper = com.android.internal.telecom.ClientTransactionalServiceWrapper.this.new ReceiverWrapper(resultReceiver);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    try {
                        transactionalCall.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.internal.telecom.ClientTransactionalServiceWrapper.AnonymousClass1.this.lambda$handleCallEventCallback$0(str, callControlCallback, receiverWrapper, objArr, str2);
                            }
                        });
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(com.android.internal.telecom.ClientTransactionalServiceWrapper.TAG, com.android.internal.telecom.ClientTransactionalServiceWrapper.EXECUTOR_FAIL_MSG + e);
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public /* synthetic */ void lambda$handleCallEventCallback$0(java.lang.String str, android.telecom.CallControlCallback callControlCallback, com.android.internal.telecom.ClientTransactionalServiceWrapper.ReceiverWrapper receiverWrapper, java.lang.Object[] objArr, java.lang.String str2) {
            char c;
            switch (str.hashCode()) {
                case -1801878642:
                    if (str.equals(ON_SET_INACTIVE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -423970853:
                    if (str.equals(ON_DISCONNECT)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 27661033:
                    if (str.equals(ON_SET_ACTIVE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 985601661:
                    if (str.equals(ON_ANSWER)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1633591742:
                    if (str.equals(ON_STREAMING_STARTED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    callControlCallback.onSetActive(receiverWrapper);
                    break;
                case 1:
                    callControlCallback.onSetInactive(receiverWrapper);
                    break;
                case 2:
                    callControlCallback.onDisconnect((android.telecom.DisconnectCause) objArr[0], receiverWrapper);
                    com.android.internal.telecom.ClientTransactionalServiceWrapper.this.untrackCall(str2);
                    break;
                case 3:
                    callControlCallback.onAnswer(((java.lang.Integer) objArr[0]).intValue(), receiverWrapper);
                    break;
                case 4:
                    callControlCallback.onCallStreamingStarted(receiverWrapper);
                    break;
            }
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAddCallControl(java.lang.String str, int i, com.android.internal.telecom.ICallControl iCallControl, android.telecom.CallException callException) {
            android.util.Log.i(com.android.internal.telecom.ClientTransactionalServiceWrapper.TAG, android.text.TextUtils.formatSimple("oACC: id=[%s], code=[%d]", str, java.lang.Integer.valueOf(i)));
            com.android.internal.telecom.TransactionalCall transactionalCall = (com.android.internal.telecom.TransactionalCall) com.android.internal.telecom.ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(str);
            if (transactionalCall != null) {
                android.os.OutcomeReceiver<android.telecom.CallControl, android.telecom.CallException> pendingControl = transactionalCall.getPendingControl();
                if (i == 0) {
                    android.telecom.CallControl callControl = new android.telecom.CallControl(str, iCallControl);
                    pendingControl.onResult(callControl);
                    transactionalCall.setCallControl(callControl);
                    return;
                } else {
                    pendingControl.onError(callException);
                    com.android.internal.telecom.ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.remove(str);
                    return;
                }
            }
            com.android.internal.telecom.ClientTransactionalServiceWrapper.this.untrackCall(str);
            android.util.Log.e(com.android.internal.telecom.ClientTransactionalServiceWrapper.TAG, "oACC: TransactionalCall object not found for call w/ id=" + str);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetActive(java.lang.String str, android.os.ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_SET_ACTIVE, str, resultReceiver, new java.lang.Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetInactive(java.lang.String str, android.os.ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_SET_INACTIVE, str, resultReceiver, new java.lang.Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAnswer(java.lang.String str, int i, android.os.ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_ANSWER, str, resultReceiver, java.lang.Integer.valueOf(i));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onDisconnect(java.lang.String str, android.telecom.DisconnectCause disconnectCause, android.os.ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_DISCONNECT, str, resultReceiver, disconnectCause);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallEndpointChanged(java.lang.String str, android.telecom.CallEndpoint callEndpoint) {
            handleEventCallback(str, ON_REQ_ENDPOINT_CHANGE, callEndpoint);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAvailableCallEndpointsChanged(java.lang.String str, java.util.List<android.telecom.CallEndpoint> list) {
            handleEventCallback(str, ON_AVAILABLE_CALL_ENDPOINTS, list);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onMuteStateChanged(java.lang.String str, boolean z) {
            handleEventCallback(str, ON_MUTE_STATE_CHANGED, java.lang.Boolean.valueOf(z));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onVideoStateChanged(java.lang.String str, int i) {
            handleEventCallback(str, ON_VIDEO_STATE_CHANGED, java.lang.Integer.valueOf(i));
        }

        public void handleEventCallback(java.lang.String str, final java.lang.String str2, final java.lang.Object obj) {
            android.util.Log.d(com.android.internal.telecom.ClientTransactionalServiceWrapper.TAG, android.text.TextUtils.formatSimple("hEC: [%s], callId=[%s]", str2, str));
            com.android.internal.telecom.TransactionalCall transactionalCall = (com.android.internal.telecom.TransactionalCall) com.android.internal.telecom.ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(str);
            if (transactionalCall != null) {
                final android.telecom.CallEventCallback callStateCallback = transactionalCall.getCallStateCallback();
                java.util.concurrent.Executor executor = transactionalCall.getExecutor();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    executor.execute(new java.lang.Runnable() { // from class: com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.internal.telecom.ClientTransactionalServiceWrapper.AnonymousClass1.lambda$handleEventCallback$1(str2, callStateCallback, obj);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        static /* synthetic */ void lambda$handleEventCallback$1(java.lang.String str, android.telecom.CallEventCallback callEventCallback, java.lang.Object obj) {
            char c;
            switch (str.hashCode()) {
                case -2134827621:
                    if (str.equals(ON_MUTE_STATE_CHANGED)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -564325214:
                    if (str.equals(ON_CALL_STREAMING_FAILED)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -103636834:
                    if (str.equals(ON_AVAILABLE_CALL_ENDPOINTS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 581203167:
                    if (str.equals(ON_VIDEO_STATE_CHANGED)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 879322485:
                    if (str.equals(ON_REQ_ENDPOINT_CHANGE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    callEventCallback.onCallEndpointChanged((android.telecom.CallEndpoint) obj);
                    break;
                case 1:
                    callEventCallback.onAvailableCallEndpointsChanged((java.util.List) obj);
                    break;
                case 2:
                    callEventCallback.onMuteStateChanged(((java.lang.Boolean) obj).booleanValue());
                    break;
                case 3:
                    if (com.android.server.telecom.flags.Flags.transactionalVideoState()) {
                        callEventCallback.onVideoStateChanged(((java.lang.Integer) obj).intValue());
                        break;
                    }
                    break;
                case 4:
                    callEventCallback.onCallStreamingFailed(((java.lang.Integer) obj).intValue());
                    break;
            }
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void removeCallFromTransactionalServiceWrapper(java.lang.String str) {
            com.android.internal.telecom.ClientTransactionalServiceWrapper.this.untrackCall(str);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingStarted(java.lang.String str, android.os.ResultReceiver resultReceiver) {
            handleCallEventCallback(ON_STREAMING_STARTED, str, resultReceiver, new java.lang.Object[0]);
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingFailed(java.lang.String str, int i) {
            android.util.Log.i(com.android.internal.telecom.ClientTransactionalServiceWrapper.TAG, android.text.TextUtils.formatSimple("oCSF: id=[%s], reason=[%s]", str, java.lang.Integer.valueOf(i)));
            handleEventCallback(str, ON_CALL_STREAMING_FAILED, java.lang.Integer.valueOf(i));
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onEvent(java.lang.String str, final java.lang.String str2, final android.os.Bundle bundle) {
            com.android.internal.telecom.TransactionalCall transactionalCall = (com.android.internal.telecom.TransactionalCall) com.android.internal.telecom.ClientTransactionalServiceWrapper.this.mCallIdToTransactionalCall.get(str);
            if (transactionalCall != null) {
                final android.telecom.CallEventCallback callStateCallback = transactionalCall.getCallStateCallback();
                java.util.concurrent.Executor executor = transactionalCall.getExecutor();
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    executor.execute(new java.lang.Runnable() { // from class: com.android.internal.telecom.ClientTransactionalServiceWrapper$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telecom.CallEventCallback.this.onEvent(str2, bundle);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }
}
