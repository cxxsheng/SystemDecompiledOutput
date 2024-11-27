package com.android.server.rotationresolver;

/* loaded from: classes2.dex */
final class RotationResolverManagerPerUserService extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.rotationresolver.RotationResolverManagerPerUserService, com.android.server.rotationresolver.RotationResolverManagerService> {
    private static final long CONNECTION_TTL_MILLIS = 60000;
    private static final java.lang.String TAG = com.android.server.rotationresolver.RotationResolverManagerPerUserService.class.getSimpleName();
    private android.content.ComponentName mComponentName;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest mCurrentRequest;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.internal.util.LatencyTracker mLatencyTracker;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.rotationresolver.RemoteRotationResolverService mRemoteService;

    RotationResolverManagerPerUserService(@android.annotation.NonNull com.android.server.rotationresolver.RotationResolverManagerService rotationResolverManagerService, @android.annotation.NonNull java.lang.Object obj, int i) {
        super(rotationResolverManagerService, obj, i);
        this.mLatencyTracker = com.android.internal.util.LatencyTracker.getInstance(getContext());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void destroyLocked() {
        if (isVerbose()) {
            android.util.Slog.v(TAG, "destroyLocked()");
        }
        if (this.mCurrentRequest == null) {
            return;
        }
        android.util.Slog.d(TAG, "Trying to cancel the remote request. Reason: Service destroyed.");
        cancelLocked();
        if (this.mRemoteService != null) {
            this.mRemoteService.unbind();
            this.mRemoteService = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    void resolveRotationLocked(@android.annotation.NonNull final android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal rotationResolverCallbackInternal, @android.annotation.NonNull android.service.rotationresolver.RotationResolutionRequest rotationResolutionRequest, @android.annotation.NonNull android.os.CancellationSignal cancellationSignal) {
        if (!isServiceAvailableLocked()) {
            android.util.Slog.w(TAG, "Service is not available at this moment.");
            rotationResolverCallbackInternal.onFailure(0);
            com.android.server.rotationresolver.RotationResolverManagerService.logRotationStats(rotationResolutionRequest.getProposedRotation(), rotationResolutionRequest.getCurrentRotation(), 7);
            return;
        }
        ensureRemoteServiceInitiated();
        if (this.mCurrentRequest != null && !this.mCurrentRequest.mIsFulfilled) {
            cancelLocked();
        }
        synchronized (this.mLock) {
            this.mLatencyTracker.onActionStart(9);
        }
        this.mCurrentRequest = new com.android.server.rotationresolver.RemoteRotationResolverService.RotationRequest(new android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal() { // from class: com.android.server.rotationresolver.RotationResolverManagerPerUserService.1
            public void onSuccess(int i) {
                synchronized (com.android.server.rotationresolver.RotationResolverManagerPerUserService.this.mLock) {
                    com.android.server.rotationresolver.RotationResolverManagerPerUserService.this.mLatencyTracker.onActionEnd(9);
                }
                rotationResolverCallbackInternal.onSuccess(i);
            }

            public void onFailure(int i) {
                synchronized (com.android.server.rotationresolver.RotationResolverManagerPerUserService.this.mLock) {
                    com.android.server.rotationresolver.RotationResolverManagerPerUserService.this.mLatencyTracker.onActionEnd(9);
                }
                rotationResolverCallbackInternal.onFailure(i);
            }
        }, rotationResolutionRequest, cancellationSignal, this.mLock);
        cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: com.android.server.rotationresolver.RotationResolverManagerPerUserService$$ExternalSyntheticLambda0
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                com.android.server.rotationresolver.RotationResolverManagerPerUserService.this.lambda$resolveRotationLocked$0();
            }
        });
        this.mRemoteService.resolveRotation(this.mCurrentRequest);
        this.mCurrentRequest.mIsDispatched = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resolveRotationLocked$0() {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentRequest != null && !this.mCurrentRequest.mIsFulfilled) {
                    android.util.Slog.d(TAG, "Trying to cancel the remote request. Reason: Client cancelled.");
                    this.mCurrentRequest.cancelInternal();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void ensureRemoteServiceInitiated() {
        if (this.mRemoteService == null) {
            this.mRemoteService = new com.android.server.rotationresolver.RemoteRotationResolverService(getContext(), this.mComponentName, getUserId(), 60000L);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean isServiceAvailableLocked() {
        if (this.mComponentName == null) {
            this.mComponentName = updateServiceInfoLocked();
        }
        return this.mComponentName != null;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 128L, this.mUserId);
            if (serviceInfo != null && !"android.permission.BIND_ROTATION_RESOLVER_SERVICE".equals(serviceInfo.permission)) {
                throw new java.lang.SecurityException(java.lang.String.format("Service %s requires %s permission. Found %s permission", serviceInfo.getComponentName(), "android.permission.BIND_ROTATION_RESOLVER_SERVICE", serviceInfo.permission));
            }
            return serviceInfo;
        } catch (android.os.RemoteException e) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get service for " + componentName);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void cancelLocked() {
        if (this.mCurrentRequest == null) {
            return;
        }
        this.mCurrentRequest.cancelInternal();
        this.mCurrentRequest = null;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected void dumpLocked(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.io.PrintWriter printWriter) {
        super.dumpLocked(str, printWriter);
        dumpInternal(new android.util.IndentingPrintWriter(printWriter, "  "));
    }

    void dumpInternal(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                if (this.mRemoteService != null) {
                    this.mRemoteService.dump("", indentingPrintWriter);
                }
                if (this.mCurrentRequest != null) {
                    this.mCurrentRequest.dump(indentingPrintWriter);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
