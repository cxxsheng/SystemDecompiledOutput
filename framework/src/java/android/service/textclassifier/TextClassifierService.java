package android.service.textclassifier;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class TextClassifierService extends android.app.Service {
    public static final int CONNECTED = 0;
    public static final int DISCONNECTED = 1;
    private static final java.lang.String KEY_RESULT = "key_result";
    private static final java.lang.String LOG_TAG = "TextClassifierService";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.textclassifier.TextClassifierService";
    private final android.os.Handler mMainThreadHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    private final java.util.concurrent.ExecutorService mSingleThreadExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
    private final android.service.textclassifier.ITextClassifierService.Stub mBinder = new android.service.textclassifier.TextClassifierService.AnonymousClass1();

    public interface Callback<T> {
        void onFailure(java.lang.CharSequence charSequence);

        void onSuccess(T t);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionState {
    }

    public abstract void onClassifyText(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassification.Request request, android.os.CancellationSignal cancellationSignal, android.service.textclassifier.TextClassifierService.Callback<android.view.textclassifier.TextClassification> callback);

    public abstract void onGenerateLinks(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLinks.Request request, android.os.CancellationSignal cancellationSignal, android.service.textclassifier.TextClassifierService.Callback<android.view.textclassifier.TextLinks> callback);

    public abstract void onSuggestSelection(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextSelection.Request request, android.os.CancellationSignal cancellationSignal, android.service.textclassifier.TextClassifierService.Callback<android.view.textclassifier.TextSelection> callback);

    /* renamed from: android.service.textclassifier.TextClassifierService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.textclassifier.ITextClassifierService.Stub {
        private final android.os.CancellationSignal mCancellationSignal = new android.os.CancellationSignal();

        AnonymousClass1() {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSuggestSelection(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextSelection.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            java.util.Objects.requireNonNull(request);
            java.util.Objects.requireNonNull(iTextClassifierCallback);
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onSuggestSelection$0(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuggestSelection$0(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextSelection.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            android.service.textclassifier.TextClassifierService.this.onSuggestSelection(textClassificationSessionId, request, this.mCancellationSignal, new android.service.textclassifier.TextClassifierService.ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onClassifyText(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextClassification.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            java.util.Objects.requireNonNull(request);
            java.util.Objects.requireNonNull(iTextClassifierCallback);
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onClassifyText$1(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClassifyText$1(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassification.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            android.service.textclassifier.TextClassifierService.this.onClassifyText(textClassificationSessionId, request, this.mCancellationSignal, new android.service.textclassifier.TextClassifierService.ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onGenerateLinks(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextLinks.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            java.util.Objects.requireNonNull(request);
            java.util.Objects.requireNonNull(iTextClassifierCallback);
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onGenerateLinks$2(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onGenerateLinks$2(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLinks.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            android.service.textclassifier.TextClassifierService.this.onGenerateLinks(textClassificationSessionId, request, this.mCancellationSignal, new android.service.textclassifier.TextClassifierService.ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSelectionEvent(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.SelectionEvent selectionEvent) {
            java.util.Objects.requireNonNull(selectionEvent);
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onSelectionEvent$3(textClassificationSessionId, selectionEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSelectionEvent$3(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.SelectionEvent selectionEvent) {
            android.service.textclassifier.TextClassifierService.this.onSelectionEvent(textClassificationSessionId, selectionEvent);
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onTextClassifierEvent(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextClassifierEvent textClassifierEvent) {
            java.util.Objects.requireNonNull(textClassifierEvent);
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onTextClassifierEvent$4(textClassificationSessionId, textClassifierEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTextClassifierEvent$4(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassifierEvent textClassifierEvent) {
            android.service.textclassifier.TextClassifierService.this.onTextClassifierEvent(textClassificationSessionId, textClassifierEvent);
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onDetectLanguage(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextLanguage.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            java.util.Objects.requireNonNull(request);
            java.util.Objects.requireNonNull(iTextClassifierCallback);
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onDetectLanguage$5(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDetectLanguage$5(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextLanguage.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            android.service.textclassifier.TextClassifierService.this.onDetectLanguage(textClassificationSessionId, request, this.mCancellationSignal, new android.service.textclassifier.TextClassifierService.ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSuggestConversationActions(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.ConversationActions.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            java.util.Objects.requireNonNull(request);
            java.util.Objects.requireNonNull(iTextClassifierCallback);
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onSuggestConversationActions$6(textClassificationSessionId, request, iTextClassifierCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuggestConversationActions$6(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.ConversationActions.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            android.service.textclassifier.TextClassifierService.this.onSuggestConversationActions(textClassificationSessionId, request, this.mCancellationSignal, new android.service.textclassifier.TextClassifierService.ProxyCallback(iTextClassifierCallback));
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onCreateTextClassificationSession(final android.view.textclassifier.TextClassificationContext textClassificationContext, final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) {
            java.util.Objects.requireNonNull(textClassificationContext);
            java.util.Objects.requireNonNull(textClassificationSessionId);
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onCreateTextClassificationSession$7(textClassificationContext, textClassificationSessionId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateTextClassificationSession$7(android.view.textclassifier.TextClassificationContext textClassificationContext, android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) {
            android.service.textclassifier.TextClassifierService.this.onCreateTextClassificationSession(textClassificationContext, textClassificationSessionId);
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onDestroyTextClassificationSession(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) {
            android.service.textclassifier.TextClassifierService.this.mMainThreadHandler.post(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.textclassifier.TextClassifierService.AnonymousClass1.this.lambda$onDestroyTextClassificationSession$8(textClassificationSessionId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDestroyTextClassificationSession$8(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) {
            android.service.textclassifier.TextClassifierService.this.onDestroyTextClassificationSession(textClassificationSessionId);
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onConnectedStateChanged(int i) {
            java.lang.Runnable runnable;
            android.os.Handler handler = android.service.textclassifier.TextClassifierService.this.mMainThreadHandler;
            if (i == 0) {
                final android.service.textclassifier.TextClassifierService textClassifierService = android.service.textclassifier.TextClassifierService.this;
                runnable = new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.service.textclassifier.TextClassifierService.this.onConnected();
                    }
                };
            } else {
                final android.service.textclassifier.TextClassifierService textClassifierService2 = android.service.textclassifier.TextClassifierService.this;
                runnable = new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$1$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.service.textclassifier.TextClassifierService.this.onDisconnected();
                    }
                };
            }
            handler.post(runnable);
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        onDisconnected();
        return super.onUnbind(intent);
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public void onDetectLanguage(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextLanguage.Request request, android.os.CancellationSignal cancellationSignal, final android.service.textclassifier.TextClassifierService.Callback<android.view.textclassifier.TextLanguage> callback) {
        this.mSingleThreadExecutor.submit(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.service.textclassifier.TextClassifierService.this.lambda$onDetectLanguage$0(callback, request);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDetectLanguage$0(android.service.textclassifier.TextClassifierService.Callback callback, android.view.textclassifier.TextLanguage.Request request) {
        callback.onSuccess(getLocalTextClassifier().detectLanguage(request));
    }

    public void onSuggestConversationActions(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.ConversationActions.Request request, android.os.CancellationSignal cancellationSignal, final android.service.textclassifier.TextClassifierService.Callback<android.view.textclassifier.ConversationActions> callback) {
        this.mSingleThreadExecutor.submit(new java.lang.Runnable() { // from class: android.service.textclassifier.TextClassifierService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.service.textclassifier.TextClassifierService.this.lambda$onSuggestConversationActions$1(callback, request);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSuggestConversationActions$1(android.service.textclassifier.TextClassifierService.Callback callback, android.view.textclassifier.ConversationActions.Request request) {
        callback.onSuccess(getLocalTextClassifier().suggestConversationActions(request));
    }

    @java.lang.Deprecated
    public void onSelectionEvent(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.SelectionEvent selectionEvent) {
    }

    public void onTextClassifierEvent(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassifierEvent textClassifierEvent) {
    }

    public void onCreateTextClassificationSession(android.view.textclassifier.TextClassificationContext textClassificationContext, android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) {
    }

    public void onDestroyTextClassificationSession(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) {
    }

    @java.lang.Deprecated
    public final android.view.textclassifier.TextClassifier getLocalTextClassifier() {
        return android.view.textclassifier.TextClassifier.NO_OP;
    }

    public static android.view.textclassifier.TextClassifier getDefaultTextClassifierImplementation(android.content.Context context) {
        java.lang.String defaultTextClassifierPackageName = context.getPackageManager().getDefaultTextClassifierPackageName();
        if (android.text.TextUtils.isEmpty(defaultTextClassifierPackageName)) {
            return android.view.textclassifier.TextClassifier.NO_OP;
        }
        if (defaultTextClassifierPackageName.equals(context.getPackageName())) {
            throw new java.lang.RuntimeException("The default text classifier itself should not call thegetDefaultTextClassifierImplementation() method.");
        }
        return ((android.view.textclassifier.TextClassificationManager) context.getSystemService(android.view.textclassifier.TextClassificationManager.class)).getTextClassifier(2);
    }

    public static <T extends android.os.Parcelable> T getResponse(android.os.Bundle bundle) {
        return (T) bundle.getParcelable(KEY_RESULT);
    }

    public static <T extends android.os.Parcelable> void putResponse(android.os.Bundle bundle, T t) {
        bundle.putParcelable(KEY_RESULT, t);
    }

    public static android.content.ComponentName getServiceComponentName(android.content.Context context, java.lang.String str, int i) {
        android.content.pm.ResolveInfo resolveService = context.getPackageManager().resolveService(new android.content.Intent(SERVICE_INTERFACE).setPackage(str), i);
        if (resolveService == null || resolveService.serviceInfo == null) {
            android.util.Slog.w(LOG_TAG, java.lang.String.format("Package or service not found in package %s for user %d", str, java.lang.Integer.valueOf(context.getUserId())));
            return null;
        }
        android.content.pm.ServiceInfo serviceInfo = resolveService.serviceInfo;
        if (android.Manifest.permission.BIND_TEXTCLASSIFIER_SERVICE.equals(serviceInfo.permission)) {
            return serviceInfo.getComponentName();
        }
        android.util.Slog.w(LOG_TAG, java.lang.String.format("Service %s should require %s permission. Found %s permission", serviceInfo.getComponentName(), android.Manifest.permission.BIND_TEXTCLASSIFIER_SERVICE, serviceInfo.permission));
        return null;
    }

    private static final class ProxyCallback<T extends android.os.Parcelable> implements android.service.textclassifier.TextClassifierService.Callback<T> {
        private android.service.textclassifier.ITextClassifierCallback mTextClassifierCallback;

        private ProxyCallback(android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            this.mTextClassifierCallback = (android.service.textclassifier.ITextClassifierCallback) java.util.Objects.requireNonNull(iTextClassifierCallback);
        }

        @Override // android.service.textclassifier.TextClassifierService.Callback
        public void onSuccess(T t) {
            try {
                android.os.Bundle bundle = new android.os.Bundle(1);
                bundle.putParcelable(android.service.textclassifier.TextClassifierService.KEY_RESULT, t);
                this.mTextClassifierCallback.onSuccess(bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.d(android.service.textclassifier.TextClassifierService.LOG_TAG, "Error calling callback");
            }
        }

        @Override // android.service.textclassifier.TextClassifierService.Callback
        public void onFailure(java.lang.CharSequence charSequence) {
            try {
                android.util.Slog.w(android.service.textclassifier.TextClassifierService.LOG_TAG, "Request fail: " + ((java.lang.Object) charSequence));
                this.mTextClassifierCallback.onFailure();
            } catch (android.os.RemoteException e) {
                android.util.Slog.d(android.service.textclassifier.TextClassifierService.LOG_TAG, "Error calling callback");
            }
        }
    }
}
