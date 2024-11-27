package com.android.server.autofill;

/* loaded from: classes.dex */
final class RemoteFieldClassificationService extends com.android.internal.infra.ServiceConnector.Impl<android.service.assist.classification.IFieldClassificationService> {
    private static final java.lang.String TAG = "Autofill" + com.android.server.autofill.RemoteFieldClassificationService.class.getSimpleName();
    private static final long TIMEOUT_IDLE_UNBIND_MS = 0;
    private final android.content.ComponentName mComponentName;

    public interface FieldClassificationServiceCallbacks {
        void logFieldClassificationEvent(long j, @android.annotation.NonNull android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse, int i);

        void onClassificationRequestFailure(int i, @android.annotation.Nullable java.lang.CharSequence charSequence);

        void onClassificationRequestSuccess(@android.annotation.NonNull android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse);

        void onClassificationRequestTimeout(int i);

        void onServiceDied(@android.annotation.NonNull com.android.server.autofill.RemoteFieldClassificationService remoteFieldClassificationService);
    }

    RemoteFieldClassificationService(android.content.Context context, android.content.ComponentName componentName, int i, int i2) {
        super(context, new android.content.Intent("android.service.assist.classification.FieldClassificationService").setComponent(componentName), 0, i2, new java.util.function.Function() { // from class: com.android.server.autofill.RemoteFieldClassificationService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.assist.classification.IFieldClassificationService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        this.mComponentName = componentName;
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "About to connect to serviceName: " + componentName);
        }
        connect();
    }

    @android.annotation.Nullable
    static android.util.Pair<android.content.pm.ServiceInfo, android.content.ComponentName> getComponentName(@android.annotation.NonNull java.lang.String str, int i, boolean z) {
        int i2;
        if (z) {
            i2 = 128;
        } else {
            i2 = 1048704;
        }
        try {
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(unflattenFromString, i2, i);
            if (serviceInfo == null) {
                android.util.Slog.e(TAG, "Bad service name for flags " + i2 + ": " + str);
                return null;
            }
            return new android.util.Pair<>(serviceInfo, unflattenFromString);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error getting service info for '" + str + "': " + e);
            return null;
        }
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onServiceConnectionStatusChanged(android.service.assist.classification.IFieldClassificationService iFieldClassificationService, boolean z) {
        try {
            if (z) {
                iFieldClassificationService.onConnected(false, false);
            } else {
                iFieldClassificationService.onDisconnected();
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Exception calling onServiceConnectionStatusChanged(" + z + "): ", e);
        }
    }

    protected long getAutoDisconnectTimeoutMs() {
        return 0L;
    }

    public void onFieldClassificationRequest(@android.annotation.NonNull final android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest, final java.lang.ref.WeakReference<com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks> weakReference) {
        final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "onFieldClassificationRequest request:" + fieldClassificationRequest);
        }
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.autofill.RemoteFieldClassificationService$$ExternalSyntheticLambda1
            public final void runNoResult(java.lang.Object obj) {
                com.android.server.autofill.RemoteFieldClassificationService.this.lambda$onFieldClassificationRequest$0(fieldClassificationRequest, weakReference, elapsedRealtime, (android.service.assist.classification.IFieldClassificationService) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFieldClassificationRequest$0(android.service.assist.classification.FieldClassificationRequest fieldClassificationRequest, final java.lang.ref.WeakReference weakReference, final long j, android.service.assist.classification.IFieldClassificationService iFieldClassificationService) throws java.lang.Exception {
        iFieldClassificationService.onFieldClassificationRequest(fieldClassificationRequest, new android.service.assist.classification.IFieldClassificationCallback.Stub() { // from class: com.android.server.autofill.RemoteFieldClassificationService.1
            public void onCancellable(android.os.ICancellationSignal iCancellationSignal) {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Log.d(com.android.server.autofill.RemoteFieldClassificationService.TAG, "onCancellable");
                }
                com.android.server.autofill.RemoteFieldClassificationService.this.logFieldClassificationEvent(j, (com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks) com.android.server.autofill.Helper.weakDeref(weakReference, com.android.server.autofill.RemoteFieldClassificationService.TAG, "onCancellable "), 3, null);
            }

            public void onSuccess(android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse) {
                java.lang.String str;
                if (com.android.server.autofill.Helper.sDebug) {
                    if (android.os.Build.IS_DEBUGGABLE) {
                        android.util.Slog.d(com.android.server.autofill.RemoteFieldClassificationService.TAG, "onSuccess Response: " + fieldClassificationResponse);
                    } else {
                        if (fieldClassificationResponse == null || fieldClassificationResponse.getClassifications() == null) {
                            str = "null response";
                        } else {
                            str = "size: " + fieldClassificationResponse.getClassifications().size();
                        }
                        android.util.Slog.d(com.android.server.autofill.RemoteFieldClassificationService.TAG, "onSuccess " + str);
                    }
                }
                com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks fieldClassificationServiceCallbacks = (com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks) com.android.server.autofill.Helper.weakDeref(weakReference, com.android.server.autofill.RemoteFieldClassificationService.TAG, "onSuccess ");
                com.android.server.autofill.RemoteFieldClassificationService.this.logFieldClassificationEvent(j, fieldClassificationServiceCallbacks, 1, fieldClassificationResponse);
                if (fieldClassificationServiceCallbacks == null) {
                    return;
                }
                fieldClassificationServiceCallbacks.onClassificationRequestSuccess(fieldClassificationResponse);
            }

            public void onFailure() {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(com.android.server.autofill.RemoteFieldClassificationService.TAG, "onFailure");
                }
                com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks fieldClassificationServiceCallbacks = (com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks) com.android.server.autofill.Helper.weakDeref(weakReference, com.android.server.autofill.RemoteFieldClassificationService.TAG, "onFailure ");
                com.android.server.autofill.RemoteFieldClassificationService.this.logFieldClassificationEvent(j, fieldClassificationServiceCallbacks, 2, null);
                if (fieldClassificationServiceCallbacks == null) {
                    return;
                }
                fieldClassificationServiceCallbacks.onClassificationRequestFailure(0, null);
            }

            public boolean isCompleted() throws android.os.RemoteException {
                return false;
            }

            public void cancel() throws android.os.RemoteException {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logFieldClassificationEvent(long j, @android.annotation.Nullable com.android.server.autofill.RemoteFieldClassificationService.FieldClassificationServiceCallbacks fieldClassificationServiceCallbacks, int i, android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse) {
        if (fieldClassificationServiceCallbacks == null) {
            com.android.server.autofill.FieldClassificationEventLogger createLogger = com.android.server.autofill.FieldClassificationEventLogger.createLogger();
            createLogger.startNewLogForRequest();
            createLogger.maybeSetLatencyMillis(android.os.SystemClock.elapsedRealtime() - j);
            createLogger.maybeSetSessionGc(true);
            createLogger.maybeSetRequestStatus(i);
            createLogger.logAndEndEvent();
            return;
        }
        fieldClassificationServiceCallbacks.logFieldClassificationEvent(j, fieldClassificationResponse, i);
    }
}
