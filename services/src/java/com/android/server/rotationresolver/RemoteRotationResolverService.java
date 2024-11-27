package com.android.server.rotationresolver;

/* loaded from: classes2.dex */
class RemoteRotationResolverService extends com.android.internal.infra.ServiceConnector.Impl<android.service.rotationresolver.IRotationResolverService> {
    private static final java.lang.String TAG = com.android.server.rotationresolver.RemoteRotationResolverService.class.getSimpleName();
    private final long mIdleUnbindTimeoutMs;

    RemoteRotationResolverService(android.content.Context context, android.content.ComponentName componentName, int i, long j) {
        super(context, new android.content.Intent("android.service.rotationresolver.RotationResolverService").setComponent(componentName), 67112960, i, new java.util.function.Function() { // from class: com.android.server.rotationresolver.RemoteRotationResolverService$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.rotationresolver.IRotationResolverService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        this.mIdleUnbindTimeoutMs = j;
        connect();
    }

    protected long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    public void resolveRotation(final com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest rotationRequest) {
        final android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest = rotationRequest.mRemoteRequest;
        post(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.rotationresolver.RemoteRotationResolverService$$ExternalSyntheticLambda0
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.rotationresolver.RemoteRotationResolverService.lambda$resolveRotation$0(com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest.this, rotationResolutionRequest, (android.service.rotationresolver.IRotationResolverService) obj);
            }
        });
        getJobHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.rotationresolver.RemoteRotationResolverService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rotationresolver.RemoteRotationResolverService.lambda$resolveRotation$1(com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest.this);
            }
        }, rotationRequest.mRemoteRequest.getTimeoutMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$resolveRotation$0(com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest rotationRequest, android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest, android.service.rotationresolver.IRotationResolverService iRotationResolverService) throws java.lang.Exception {
        iRotationResolverService.resolveRotation(rotationRequest.mIRotationResolverCallback, rotationResolutionRequest);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$resolveRotation$1(com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest rotationRequest) {
        synchronized (rotationRequest.mLock) {
            try {
                if (!rotationRequest.mIsFulfilled) {
                    rotationRequest.mCallbackInternal.onFailure(1);
                    android.util.Slog.d(TAG, "Trying to cancel the remote request. Reason: Timed out.");
                    rotationRequest.cancelInternal();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class RotationRequest {

        @android.annotation.NonNull
        final android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal mCallbackInternal;

        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.os.ICancellationSignal mCancellation;

        @android.annotation.NonNull
        private final android.os.CancellationSignal mCancellationSignalInternal;
        boolean mIsDispatched;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean mIsFulfilled;
        private final java.lang.Object mLock;

        @com.android.internal.annotations.VisibleForTesting
        final android.service.rotationresolver.RotationResolutionRequest mRemoteRequest;

        @android.annotation.NonNull
        private final android.service.rotationresolver.IRotationResolverCallback mIRotationResolverCallback = new com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest.RotationResolverCallback(this);
        private final long mRequestStartTimeMillis = android.os.SystemClock.elapsedRealtime();

        RotationRequest(@android.annotation.NonNull android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal rotationResolverCallbackInternal, android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest, @android.annotation.NonNull android.os.CancellationSignal cancellationSignal, java.lang.Object obj) {
            this.mCallbackInternal = rotationResolverCallbackInternal;
            this.mRemoteRequest = rotationResolutionRequest;
            this.mCancellationSignalInternal = cancellationSignal;
            this.mLock = obj;
        }

        void cancelInternal() {
            android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.rotationresolver.RemoteRotationResolverService$RotationRequest$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest.this.lambda$cancelInternal$0();
                }
            });
            this.mCallbackInternal.onFailure(0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$cancelInternal$0() {
            synchronized (this.mLock) {
                try {
                    if (this.mIsFulfilled) {
                        return;
                    }
                    this.mIsFulfilled = true;
                    try {
                        if (this.mCancellation != null) {
                            this.mCancellation.cancel();
                            this.mCancellation = null;
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(com.android.server.rotationresolver.RemoteRotationResolverService.TAG, "Failed to cancel request in remote service.");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("is dispatched=" + this.mIsDispatched);
            indentingPrintWriter.println("is fulfilled:=" + this.mIsFulfilled);
            indentingPrintWriter.decreaseIndent();
        }

        private static class RotationResolverCallback extends android.service.rotationresolver.IRotationResolverCallback.Stub {
            private final java.lang.ref.WeakReference<com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest> mRequestWeakReference;

            RotationResolverCallback(com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest rotationRequest) {
                this.mRequestWeakReference = new java.lang.ref.WeakReference<>(rotationRequest);
            }

            public void onSuccess(int i) {
                com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest rotationRequest = this.mRequestWeakReference.get();
                synchronized (rotationRequest.mLock) {
                    try {
                        if (rotationRequest.mIsFulfilled) {
                            android.util.Slog.w(com.android.server.rotationresolver.RemoteRotationResolverService.TAG, "Callback received after the rotation request is fulfilled.");
                            return;
                        }
                        rotationRequest.mIsFulfilled = true;
                        rotationRequest.mCallbackInternal.onSuccess(i);
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - rotationRequest.mRequestStartTimeMillis;
                        com.android.server.rotationresolver.RotationResolverManagerService.logRotationStatsWithTimeToCalculate(rotationRequest.mRemoteRequest.getProposedRotation(), rotationRequest.mRemoteRequest.getCurrentRotation(), com.android.server.rotationresolver.RotationResolverManagerService.surfaceRotationToProto(i), elapsedRealtime);
                        android.util.Slog.d(com.android.server.rotationresolver.RemoteRotationResolverService.TAG, "onSuccess:" + i);
                        android.util.Slog.d(com.android.server.rotationresolver.RemoteRotationResolverService.TAG, "timeToCalculate:" + elapsedRealtime);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onFailure(int i) {
                com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest rotationRequest = this.mRequestWeakReference.get();
                synchronized (rotationRequest.mLock) {
                    try {
                        if (rotationRequest.mIsFulfilled) {
                            android.util.Slog.w(com.android.server.rotationresolver.RemoteRotationResolverService.TAG, "Callback received after the rotation request is fulfilled.");
                            return;
                        }
                        rotationRequest.mIsFulfilled = true;
                        rotationRequest.mCallbackInternal.onFailure(i);
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - rotationRequest.mRequestStartTimeMillis;
                        com.android.server.rotationresolver.RotationResolverManagerService.logRotationStatsWithTimeToCalculate(rotationRequest.mRemoteRequest.getProposedRotation(), rotationRequest.mRemoteRequest.getCurrentRotation(), com.android.server.rotationresolver.RotationResolverManagerService.errorCodeToProto(i), elapsedRealtime);
                        android.util.Slog.d(com.android.server.rotationresolver.RemoteRotationResolverService.TAG, "onFailure:" + i);
                        android.util.Slog.d(com.android.server.rotationresolver.RemoteRotationResolverService.TAG, "timeToCalculate:" + elapsedRealtime);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onCancellable(@android.annotation.NonNull android.os.ICancellationSignal iCancellationSignal) {
                com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest rotationRequest = this.mRequestWeakReference.get();
                synchronized (rotationRequest.mLock) {
                    rotationRequest.mCancellation = iCancellationSignal;
                    if (rotationRequest.mCancellationSignalInternal.isCanceled()) {
                        try {
                            iCancellationSignal.cancel();
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.w(com.android.server.rotationresolver.RemoteRotationResolverService.TAG, "Failed to cancel the remote request.");
                        }
                    }
                }
            }
        }
    }
}
