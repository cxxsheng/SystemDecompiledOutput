package android.credentials;

/* loaded from: classes.dex */
public final class PrepareGetCredentialResponse {
    private static final android.os.Bundle OPTIONS_SENDER_BAL_OPTIN = android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
    private static final java.lang.String TAG = "CredentialManager";
    private final android.credentials.PrepareGetCredentialResponse.PendingGetCredentialHandle mPendingGetCredentialHandle;
    private final android.credentials.PrepareGetCredentialResponseInternal mResponseInternal;

    protected interface GetPendingCredentialInternalCallback {
        void onError(java.lang.String str, java.lang.String str2);

        void onPendingIntent(android.app.PendingIntent pendingIntent);

        void onResponse(android.credentials.GetCredentialResponse getCredentialResponse);
    }

    public static final class PendingGetCredentialHandle {
        private final android.credentials.CredentialManager.GetCredentialTransportPendingUseCase mGetCredentialTransport;
        private final android.app.PendingIntent mPendingIntent;

        PendingGetCredentialHandle(android.credentials.CredentialManager.GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase, android.app.PendingIntent pendingIntent) {
            this.mGetCredentialTransport = getCredentialTransportPendingUseCase;
            this.mPendingIntent = pendingIntent;
        }

        void show(android.content.Context context, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
            if (this.mPendingIntent == null) {
                executor.execute(new java.lang.Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError(new android.credentials.GetCredentialException(android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL));
                    }
                });
                return;
            }
            this.mGetCredentialTransport.setCallback(new android.credentials.PrepareGetCredentialResponse.PendingGetCredentialHandle.AnonymousClass1(context, executor, outcomeReceiver));
            try {
                context.startIntentSender(this.mPendingIntent.getIntentSender(), null, 0, 0, 0, android.credentials.PrepareGetCredentialResponse.OPTIONS_SENDER_BAL_OPTIN);
            } catch (android.content.IntentSender.SendIntentException e) {
                android.util.Log.e(android.credentials.PrepareGetCredentialResponse.TAG, "startIntentSender() failed for intent for show()", e);
                executor.execute(new java.lang.Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError(new android.credentials.GetCredentialException(android.credentials.GetCredentialException.TYPE_UNKNOWN));
                    }
                });
            }
        }

        /* renamed from: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1, reason: invalid class name */
        class AnonymousClass1 implements android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback {
            final /* synthetic */ android.os.OutcomeReceiver val$callback;
            final /* synthetic */ android.content.Context val$context;
            final /* synthetic */ java.util.concurrent.Executor val$executor;

            AnonymousClass1(android.content.Context context, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
                this.val$context = context;
                this.val$executor = executor;
                this.val$callback = outcomeReceiver;
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onPendingIntent(android.app.PendingIntent pendingIntent) {
                try {
                    this.val$context.startIntentSender(pendingIntent.getIntentSender(), null, 0, 0, 0, android.credentials.PrepareGetCredentialResponse.OPTIONS_SENDER_BAL_OPTIN);
                } catch (android.content.IntentSender.SendIntentException e) {
                    android.util.Log.e(android.credentials.PrepareGetCredentialResponse.TAG, "startIntentSender() failed for intent for show()", e);
                    java.util.concurrent.Executor executor = this.val$executor;
                    final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new java.lang.Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError(new android.credentials.GetCredentialException(android.credentials.GetCredentialException.TYPE_UNKNOWN));
                        }
                    });
                }
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onResponse(final android.credentials.GetCredentialResponse getCredentialResponse) {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onResult(getCredentialResponse);
                    }
                });
            }

            @Override // android.credentials.PrepareGetCredentialResponse.GetPendingCredentialInternalCallback
            public void onError(final java.lang.String str, final java.lang.String str2) {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.credentials.PrepareGetCredentialResponse$PendingGetCredentialHandle$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError(new android.credentials.GetCredentialException(str, str2));
                    }
                });
            }
        }
    }

    public boolean hasCredentialResults(java.lang.String str) {
        return this.mResponseInternal.hasCredentialResults(str);
    }

    public boolean hasAuthenticationResults() {
        return this.mResponseInternal.hasAuthenticationResults();
    }

    public boolean hasRemoteResults() {
        return this.mResponseInternal.hasRemoteResults();
    }

    public android.credentials.PrepareGetCredentialResponse.PendingGetCredentialHandle getPendingGetCredentialHandle() {
        return this.mPendingGetCredentialHandle;
    }

    protected PrepareGetCredentialResponse(android.credentials.PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal, android.credentials.CredentialManager.GetCredentialTransportPendingUseCase getCredentialTransportPendingUseCase) {
        this.mResponseInternal = prepareGetCredentialResponseInternal;
        this.mPendingGetCredentialHandle = new android.credentials.PrepareGetCredentialResponse.PendingGetCredentialHandle(getCredentialTransportPendingUseCase, prepareGetCredentialResponseInternal.getPendingIntent());
    }
}
