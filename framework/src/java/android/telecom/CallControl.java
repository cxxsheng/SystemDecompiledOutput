package android.telecom;

/* loaded from: classes3.dex */
public final class CallControl {
    private static final java.lang.String TAG = android.telecom.CallControl.class.getSimpleName();
    private final java.lang.String mCallId;
    private final com.android.internal.telecom.ICallControl mServerInterface;

    public CallControl(java.lang.String str, com.android.internal.telecom.ICallControl iCallControl) {
        this.mCallId = str;
        this.mServerInterface = iCallControl;
    }

    public android.os.ParcelUuid getCallId() {
        return android.os.ParcelUuid.fromString(this.mCallId);
    }

    public void setActive(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.setActive(this.mCallId, new android.telecom.CallControl.CallControlResultReceiver("setActive", executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void answer(int i, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
        validateVideoState(i);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.answer(i, this.mCallId, new android.telecom.CallControl.CallControlResultReceiver("answer", executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setInactive(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.setInactive(this.mCallId, new android.telecom.CallControl.CallControlResultReceiver("setInactive", executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void disconnect(android.telecom.DisconnectCause disconnectCause, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
        java.util.Objects.requireNonNull(disconnectCause);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        validateDisconnectCause(disconnectCause);
        try {
            this.mServerInterface.disconnect(this.mCallId, disconnectCause, new android.telecom.CallControl.CallControlResultReceiver(android.media.MediaMetrics.Value.DISCONNECT, executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void startCallStreaming(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.startCallStreaming(this.mCallId, new android.telecom.CallControl.CallControlResultReceiver("startCallStreaming", executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestCallEndpointChange(android.telecom.CallEndpoint callEndpoint, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
        java.util.Objects.requireNonNull(callEndpoint);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.requestCallEndpointChange(callEndpoint, new android.telecom.CallControl.CallControlResultReceiver("requestCallEndpointChange", executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestMuteState(boolean z, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.setMuteState(z, new android.telecom.CallControl.CallControlResultReceiver("requestMuteState", executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void requestVideoState(int i, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
        validateVideoState(i);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            this.mServerInterface.requestVideoState(i, this.mCallId, new android.telecom.CallControl.CallControlResultReceiver("requestVideoState", executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void sendEvent(java.lang.String str, android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(bundle);
        try {
            this.mServerInterface.sendEvent(this.mCallId, str, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CallControlResultReceiver extends android.os.ResultReceiver {
        private final java.lang.String mCallingMethod;
        private final android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> mClientCallback;
        private final java.util.concurrent.Executor mExecutor;

        CallControlResultReceiver(java.lang.String str, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<java.lang.Void, android.telecom.CallException> outcomeReceiver) {
            super((android.os.Handler) null);
            this.mCallingMethod = str;
            this.mExecutor = executor;
            this.mClientCallback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, final android.os.Bundle bundle) {
            android.telecom.Log.d(android.telecom.CallControl.TAG, "%s: oRR: resultCode=[%s]", this.mCallingMethod, java.lang.Integer.valueOf(i));
            super.onReceiveResult(i, bundle);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (i == 0) {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telecom.CallControl$CallControlResultReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telecom.CallControl.CallControlResultReceiver.this.lambda$onReceiveResult$0();
                        }
                    });
                } else {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telecom.CallControl$CallControlResultReceiver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telecom.CallControl.CallControlResultReceiver.this.lambda$onReceiveResult$1(bundle);
                        }
                    });
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$0() {
            this.mClientCallback.onResult(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$1(android.os.Bundle bundle) {
            this.mClientCallback.onError(android.telecom.CallControl.this.getTransactionException(bundle));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.telecom.CallException getTransactionException(android.os.Bundle bundle) {
        if (bundle != null && bundle.containsKey(android.telecom.CallException.TRANSACTION_EXCEPTION_KEY)) {
            return (android.telecom.CallException) bundle.getParcelable(android.telecom.CallException.TRANSACTION_EXCEPTION_KEY, android.telecom.CallException.class);
        }
        return new android.telecom.CallException("unknown error", 1);
    }

    private void validateDisconnectCause(android.telecom.DisconnectCause disconnectCause) {
        int code = disconnectCause.getCode();
        if (code != 2 && code != 3 && code != 5 && code != 6) {
            throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("The DisconnectCause code provided, %d , is not a valid Disconnect code. Valid DisconnectCause codes are limited to [DisconnectCause.LOCAL, DisconnectCause.REMOTE, DisconnectCause.MISSED, or DisconnectCause.REJECTED]", java.lang.Integer.valueOf(disconnectCause.getCode())));
        }
    }

    private void validateVideoState(int i) {
        if (i != 1 && i != 2) {
            throw new java.lang.IllegalArgumentException(android.text.TextUtils.formatSimple("The VideoState argument passed in, %d , is not a valid VideoState. The VideoState choices are limited to CallAttributes.AUDIO_CALL orCallAttributes.VIDEO_CALL", java.lang.Integer.valueOf(i)));
        }
    }
}
