package com.android.server.autofill;

/* loaded from: classes.dex */
final class RemoteAugmentedAutofillService extends com.android.internal.infra.ServiceConnector.Impl<android.service.autofill.augmented.IAugmentedAutofillService> {
    private static final java.lang.String TAG = com.android.server.autofill.RemoteAugmentedAutofillService.class.getSimpleName();
    private final com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks mCallbacks;
    private final android.content.ComponentName mComponentName;
    private final int mIdleUnbindTimeoutMs;
    private final int mRequestTimeoutMs;
    private final com.android.server.autofill.AutofillUriGrantsManager mUriGrantsManager;

    public interface RemoteAugmentedAutofillServiceCallbacks extends com.android.internal.infra.AbstractRemoteService.VultureCallback<com.android.server.autofill.RemoteAugmentedAutofillService> {
        void logAugmentedAutofillAuthenticationSelected(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle);

        void logAugmentedAutofillSelected(int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle);

        void logAugmentedAutofillShown(int i, @android.annotation.Nullable android.os.Bundle bundle);

        void resetLastResponse();

        void setLastResponse(int i);
    }

    RemoteAugmentedAutofillService(android.content.Context context, int i, android.content.ComponentName componentName, int i2, com.android.server.autofill.RemoteAugmentedAutofillService.RemoteAugmentedAutofillServiceCallbacks remoteAugmentedAutofillServiceCallbacks, boolean z, boolean z2, int i3, int i4) {
        super(context, new android.content.Intent("android.service.autofill.augmented.AugmentedAutofillService").setComponent(componentName), z ? 4194304 : 0, i2, new java.util.function.Function() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.service.autofill.augmented.IAugmentedAutofillService.Stub.asInterface((android.os.IBinder) obj);
            }
        });
        this.mIdleUnbindTimeoutMs = i3;
        this.mRequestTimeoutMs = i4;
        this.mComponentName = componentName;
        this.mCallbacks = remoteAugmentedAutofillServiceCallbacks;
        this.mUriGrantsManager = new com.android.server.autofill.AutofillUriGrantsManager(i);
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

    public com.android.server.autofill.AutofillUriGrantsManager getAutofillUriGrantsManager() {
        return this.mUriGrantsManager;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onServiceConnectionStatusChanged(android.service.autofill.augmented.IAugmentedAutofillService iAugmentedAutofillService, boolean z) {
        try {
            if (z) {
                iAugmentedAutofillService.onConnected(com.android.server.autofill.Helper.sDebug, com.android.server.autofill.Helper.sVerbose);
            } else {
                iAugmentedAutofillService.onDisconnected();
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Exception calling onServiceConnectionStatusChanged(" + z + "): ", e);
        }
    }

    protected long getAutoDisconnectTimeoutMs() {
        return this.mIdleUnbindTimeoutMs;
    }

    public void onRequestAutofillLocked(final int i, @android.annotation.NonNull final android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, final int i2, @android.annotation.NonNull final android.content.ComponentName componentName, @android.annotation.NonNull final android.os.IBinder iBinder, @android.annotation.NonNull final android.view.autofill.AutofillId autofillId, @android.annotation.Nullable final android.view.autofill.AutofillValue autofillValue, @android.annotation.Nullable final android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, @android.annotation.Nullable final java.util.function.Function<com.android.server.autofill.ui.InlineFillUi, java.lang.Boolean> function, @android.annotation.NonNull final java.lang.Runnable runnable, @android.annotation.Nullable final com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService, final int i3) {
        final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        postAsync(new com.android.internal.infra.ServiceConnector.Job() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService$$ExternalSyntheticLambda3
            public final java.lang.Object run(java.lang.Object obj) {
                java.util.concurrent.CompletableFuture lambda$onRequestAutofillLocked$0;
                lambda$onRequestAutofillLocked$0 = com.android.server.autofill.RemoteAugmentedAutofillService.this.lambda$onRequestAutofillLocked$0(iAutoFillManagerClient, i, i2, componentName, autofillId, autofillValue, elapsedRealtime, inlineSuggestionsRequest, function, runnable, remoteInlineSuggestionRenderService, i3, iBinder, atomicReference, (android.service.autofill.augmented.IAugmentedAutofillService) obj);
                return lambda$onRequestAutofillLocked$0;
            }
        }).orTimeout(this.mRequestTimeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS).whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.autofill.RemoteAugmentedAutofillService.this.lambda$onRequestAutofillLocked$1(atomicReference, componentName, i, (java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.concurrent.CompletableFuture lambda$onRequestAutofillLocked$0(final android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, final int i, final int i2, final android.content.ComponentName componentName, final android.view.autofill.AutofillId autofillId, final android.view.autofill.AutofillValue autofillValue, final long j, final android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, final java.util.function.Function function, final java.lang.Runnable runnable, final com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService, final int i3, final android.os.IBinder iBinder, final java.util.concurrent.atomic.AtomicReference atomicReference, final android.service.autofill.augmented.IAugmentedAutofillService iAugmentedAutofillService) throws java.lang.Exception {
        final com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
        iAutoFillManagerClient.getAugmentedAutofillClient(new com.android.internal.os.IResultReceiver.Stub() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService.1
            public void send(int i4, android.os.Bundle bundle) throws android.os.RemoteException {
                iAugmentedAutofillService.onFillRequest(i, bundle.getBinder("android.view.autofill.extra.AUGMENTED_AUTOFILL_CLIENT"), i2, componentName, autofillId, autofillValue, j, inlineSuggestionsRequest, new android.service.autofill.augmented.IFillCallback.Stub() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService.1.1
                    public void onSuccess(@android.annotation.Nullable java.util.List<android.service.autofill.Dataset> list, @android.annotation.Nullable android.os.Bundle bundle2, boolean z) {
                        com.android.server.autofill.RemoteAugmentedAutofillService.this.mCallbacks.resetLastResponse();
                        com.android.server.autofill.RemoteAugmentedAutofillService.this.maybeRequestShowInlineSuggestions(i, inlineSuggestionsRequest, list, bundle2, autofillId, autofillValue, function, iAutoFillManagerClient, runnable, remoteInlineSuggestionRenderService, i3, componentName, iBinder);
                        if (!z) {
                            androidFuture.complete((java.lang.Object) null);
                        }
                    }

                    public boolean isCompleted() {
                        return androidFuture.isDone() && !androidFuture.isCancelled();
                    }

                    public void onCancellable(android.os.ICancellationSignal iCancellationSignal) {
                        if (androidFuture.isCancelled()) {
                            com.android.server.autofill.RemoteAugmentedAutofillService.this.dispatchCancellation(iCancellationSignal);
                        } else {
                            atomicReference.set(iCancellationSignal);
                        }
                    }

                    public void cancel() {
                        androidFuture.cancel(true);
                    }
                });
            }
        });
        return androidFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestAutofillLocked$1(java.util.concurrent.atomic.AtomicReference atomicReference, android.content.ComponentName componentName, int i, java.lang.Void r10, java.lang.Throwable th) {
        if (th instanceof java.util.concurrent.CancellationException) {
            dispatchCancellation((android.os.ICancellationSignal) atomicReference.get());
            return;
        }
        if (th instanceof java.util.concurrent.TimeoutException) {
            android.util.Slog.w(TAG, "PendingAutofillRequest timed out (" + this.mRequestTimeoutMs + "ms) for " + this);
            dispatchCancellation((android.os.ICancellationSignal) atomicReference.get());
            if (this.mComponentName != null) {
                android.service.autofill.augmented.Helper.logResponse(15, this.mComponentName.getPackageName(), componentName, i, this.mRequestTimeoutMs);
                return;
            }
            return;
        }
        if (th != null) {
            android.util.Slog.e(TAG, "exception handling getAugmentedAutofillClient() for " + i + ": ", th);
        }
    }

    void dispatchCancellation(@android.annotation.Nullable final android.os.ICancellationSignal iCancellationSignal) {
        if (iCancellationSignal == null) {
            return;
        }
        android.os.Handler.getMain().post(new java.lang.Runnable() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.RemoteAugmentedAutofillService.lambda$dispatchCancellation$2(iCancellationSignal);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dispatchCancellation$2(android.os.ICancellationSignal iCancellationSignal) {
        try {
            iCancellationSignal.cancel();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error requesting a cancellation", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeRequestShowInlineSuggestions(final int i, @android.annotation.Nullable android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, @android.annotation.Nullable java.util.List<android.service.autofill.Dataset> list, @android.annotation.Nullable final android.os.Bundle bundle, @android.annotation.NonNull final android.view.autofill.AutofillId autofillId, @android.annotation.Nullable android.view.autofill.AutofillValue autofillValue, @android.annotation.Nullable final java.util.function.Function<com.android.server.autofill.ui.InlineFillUi, java.lang.Boolean> function, @android.annotation.NonNull final android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient, @android.annotation.NonNull final java.lang.Runnable runnable, @android.annotation.Nullable com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService, final int i2, @android.annotation.NonNull final android.content.ComponentName componentName, @android.annotation.NonNull final android.os.IBinder iBinder) {
        if (list == null || list.isEmpty() || function == null || inlineSuggestionsRequest == null || remoteInlineSuggestionRenderService == null) {
            if (function != null && inlineSuggestionsRequest != null) {
                function.apply(com.android.server.autofill.ui.InlineFillUi.emptyUi(autofillId));
                return;
            }
            return;
        }
        this.mCallbacks.setLastResponse(i);
        if (function.apply(com.android.server.autofill.ui.InlineFillUi.forAugmentedAutofill(new com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo(inlineSuggestionsRequest, autofillId, (autofillValue == null || !autofillValue.isText()) ? null : autofillValue.getTextValue().toString(), remoteInlineSuggestionRenderService, i2, i), list, new com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService.2
            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void autofill(android.service.autofill.Dataset dataset, int i3) {
                boolean z = true;
                if (dataset.getAuthentication() != null) {
                    com.android.server.autofill.RemoteAugmentedAutofillService.this.mCallbacks.logAugmentedAutofillAuthenticationSelected(i, dataset.getId(), bundle);
                    android.content.IntentSender authentication = dataset.getAuthentication();
                    int makeAuthenticationId = android.view.autofill.AutofillManager.makeAuthenticationId(1, i3);
                    android.content.Intent intent = new android.content.Intent();
                    intent.putExtra("android.view.autofill.extra.CLIENT_STATE", bundle);
                    try {
                        iAutoFillManagerClient.authenticate(i, makeAuthenticationId, authentication, intent, false);
                        return;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(com.android.server.autofill.RemoteAugmentedAutofillService.TAG, "Error starting auth flow");
                        function.apply(com.android.server.autofill.ui.InlineFillUi.emptyUi(autofillId));
                        return;
                    }
                }
                com.android.server.autofill.RemoteAugmentedAutofillService.this.mCallbacks.logAugmentedAutofillSelected(i, dataset.getId(), bundle);
                try {
                    java.util.ArrayList fieldIds = dataset.getFieldIds();
                    android.content.ClipData fieldContent = dataset.getFieldContent();
                    if (fieldContent != null) {
                        com.android.server.autofill.RemoteAugmentedAutofillService.this.mUriGrantsManager.grantUriPermissions(componentName, iBinder, i2, fieldContent);
                        android.view.autofill.AutofillId autofillId2 = (android.view.autofill.AutofillId) fieldIds.get(0);
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(com.android.server.autofill.RemoteAugmentedAutofillService.TAG, "Calling client autofillContent(): id=" + autofillId2 + ", content=" + fieldContent);
                        }
                        iAutoFillManagerClient.autofillContent(i, autofillId2, fieldContent);
                    } else {
                        if (fieldIds.size() != 1 || !((android.view.autofill.AutofillId) fieldIds.get(0)).equals(autofillId)) {
                            z = false;
                        }
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(com.android.server.autofill.RemoteAugmentedAutofillService.TAG, "Calling client autofill(): ids=" + fieldIds + ", values=" + dataset.getFieldValues());
                        }
                        iAutoFillManagerClient.autofill(i, fieldIds, dataset.getFieldValues(), z);
                    }
                    function.apply(com.android.server.autofill.ui.InlineFillUi.emptyUi(autofillId));
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.w(com.android.server.autofill.RemoteAugmentedAutofillService.TAG, "Encounter exception autofilling the values");
                }
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void authenticate(int i3, int i4) {
                android.util.Slog.e(com.android.server.autofill.RemoteAugmentedAutofillService.TAG, "authenticate not implemented for augmented autofill");
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void startIntentSender(android.content.IntentSender intentSender) {
                try {
                    iAutoFillManagerClient.startIntentSender(intentSender, new android.content.Intent());
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.autofill.RemoteAugmentedAutofillService.TAG, "RemoteException starting intent sender");
                }
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void onError() {
                runnable.run();
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void onInflate() {
            }
        })).booleanValue()) {
            this.mCallbacks.logAugmentedAutofillShown(i, bundle);
        }
    }

    public java.lang.String toString() {
        return "RemoteAugmentedAutofillService[" + android.content.ComponentName.flattenToShortString(this.mComponentName) + "]";
    }

    public void onDestroyAutofillWindowsRequest() {
        run(new com.android.internal.infra.ServiceConnector.VoidJob() { // from class: com.android.server.autofill.RemoteAugmentedAutofillService$$ExternalSyntheticLambda2
            public final void runNoResult(java.lang.Object obj) {
                ((android.service.autofill.augmented.IAugmentedAutofillService) obj).onDestroyAllFillWindowsRequest();
            }
        });
    }
}
