package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class SystemTextClassifier implements android.view.textclassifier.TextClassifier {
    private static final java.lang.String LOG_TAG = "androidtc";
    private android.view.textclassifier.TextClassificationSessionId mSessionId;
    private final android.view.textclassifier.TextClassificationConstants mSettings;
    private final android.view.textclassifier.SystemTextClassifierMetadata mSystemTcMetadata;
    private final android.service.textclassifier.ITextClassifierService mManagerService = android.service.textclassifier.ITextClassifierService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.TEXT_CLASSIFICATION_SERVICE));
    private final android.view.textclassifier.TextClassifier mFallback = android.view.textclassifier.TextClassifier.NO_OP;

    public SystemTextClassifier(android.content.Context context, android.view.textclassifier.TextClassificationConstants textClassificationConstants, boolean z) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mSettings = (android.view.textclassifier.TextClassificationConstants) java.util.Objects.requireNonNull(textClassificationConstants);
        this.mSystemTcMetadata = new android.view.textclassifier.SystemTextClassifierMetadata((java.lang.String) java.util.Objects.requireNonNull(context.getOpPackageName()), context.getUserId(), z);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.TextSelection suggestSelection(android.view.textclassifier.TextSelection.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            android.view.textclassifier.SystemTextClassifier.BlockingCallback blockingCallback = new android.view.textclassifier.SystemTextClassifier.BlockingCallback("textselection", this.mSettings);
            this.mManagerService.onSuggestSelection(this.mSessionId, request, blockingCallback);
            android.view.textclassifier.TextSelection textSelection = (android.view.textclassifier.TextSelection) blockingCallback.get();
            if (textSelection != null) {
                return textSelection;
            }
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error suggesting selection for text. Using fallback.", e);
        }
        return this.mFallback.suggestSelection(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.TextClassification classifyText(android.view.textclassifier.TextClassification.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            android.view.textclassifier.SystemTextClassifier.BlockingCallback blockingCallback = new android.view.textclassifier.SystemTextClassifier.BlockingCallback(android.content.Context.TEXT_CLASSIFICATION_SERVICE, this.mSettings);
            this.mManagerService.onClassifyText(this.mSessionId, request, blockingCallback);
            android.view.textclassifier.TextClassification textClassification = (android.view.textclassifier.TextClassification) blockingCallback.get();
            if (textClassification != null) {
                return textClassification;
            }
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error classifying text. Using fallback.", e);
        }
        return this.mFallback.classifyText(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.TextLinks generateLinks(android.view.textclassifier.TextLinks.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        if (!android.view.textclassifier.TextClassifier.Utils.checkTextLength(request.getText(), getMaxGenerateLinksTextLength())) {
            return this.mFallback.generateLinks(request);
        }
        if (!this.mSettings.isSmartLinkifyEnabled() && request.isLegacyFallback()) {
            return android.view.textclassifier.TextClassifier.Utils.generateLegacyLinks(request);
        }
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            android.view.textclassifier.SystemTextClassifier.BlockingCallback blockingCallback = new android.view.textclassifier.SystemTextClassifier.BlockingCallback("textlinks", this.mSettings);
            this.mManagerService.onGenerateLinks(this.mSessionId, request, blockingCallback);
            android.view.textclassifier.TextLinks textLinks = (android.view.textclassifier.TextLinks) blockingCallback.get();
            if (textLinks != null) {
                return textLinks;
            }
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error generating links. Using fallback.", e);
        }
        return this.mFallback.generateLinks(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onSelectionEvent(android.view.textclassifier.SelectionEvent selectionEvent) {
        java.util.Objects.requireNonNull(selectionEvent);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        try {
            selectionEvent.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            this.mManagerService.onSelectionEvent(this.mSessionId, selectionEvent);
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error reporting selection event.", e);
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onTextClassifierEvent(android.view.textclassifier.TextClassifierEvent textClassifierEvent) {
        android.view.textclassifier.TextClassificationContext eventContext;
        java.util.Objects.requireNonNull(textClassifierEvent);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        try {
            if (textClassifierEvent.getEventContext() == null) {
                eventContext = new android.view.textclassifier.TextClassificationContext.Builder(this.mSystemTcMetadata.getCallingPackageName(), "unknown").build();
            } else {
                eventContext = textClassifierEvent.getEventContext();
            }
            eventContext.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            textClassifierEvent.setEventContext(eventContext);
            this.mManagerService.onTextClassifierEvent(this.mSessionId, textClassifierEvent);
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error reporting textclassifier event.", e);
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.TextLanguage detectLanguage(android.view.textclassifier.TextLanguage.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            android.view.textclassifier.SystemTextClassifier.BlockingCallback blockingCallback = new android.view.textclassifier.SystemTextClassifier.BlockingCallback("textlanguage", this.mSettings);
            this.mManagerService.onDetectLanguage(this.mSessionId, request, blockingCallback);
            android.view.textclassifier.TextLanguage textLanguage = (android.view.textclassifier.TextLanguage) blockingCallback.get();
            if (textLanguage != null) {
                return textLanguage;
            }
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error detecting language.", e);
        }
        return this.mFallback.detectLanguage(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.ConversationActions suggestConversationActions(android.view.textclassifier.ConversationActions.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        try {
            request.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            android.view.textclassifier.SystemTextClassifier.BlockingCallback blockingCallback = new android.view.textclassifier.SystemTextClassifier.BlockingCallback("conversation-actions", this.mSettings);
            this.mManagerService.onSuggestConversationActions(this.mSessionId, request, blockingCallback);
            android.view.textclassifier.ConversationActions conversationActions = (android.view.textclassifier.ConversationActions) blockingCallback.get();
            if (conversationActions != null) {
                return conversationActions;
            }
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error reporting selection event.", e);
        }
        return this.mFallback.suggestConversationActions(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public int getMaxGenerateLinksTextLength() {
        return this.mSettings.getGenerateLinksMaxTextLength();
    }

    @Override // android.view.textclassifier.TextClassifier
    public void destroy() {
        try {
            if (this.mSessionId != null) {
                this.mManagerService.onDestroyTextClassificationSession(this.mSessionId);
            }
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error destroying classification session.", e);
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("SystemTextClassifier:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("mFallback", this.mFallback);
        indentingPrintWriter.printPair("mSessionId", this.mSessionId);
        indentingPrintWriter.printPair("mSystemTcMetadata", this.mSystemTcMetadata);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    void initializeRemoteSession(android.view.textclassifier.TextClassificationContext textClassificationContext, android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) {
        this.mSessionId = (android.view.textclassifier.TextClassificationSessionId) java.util.Objects.requireNonNull(textClassificationSessionId);
        try {
            textClassificationContext.setSystemTextClassifierMetadata(this.mSystemTcMetadata);
            this.mManagerService.onCreateTextClassificationSession(textClassificationContext, this.mSessionId);
        } catch (android.os.RemoteException e) {
            android.view.textclassifier.Log.e("androidtc", "Error starting a new classification session.", e);
        }
    }

    private static final class BlockingCallback<T extends android.os.Parcelable> extends android.service.textclassifier.ITextClassifierCallback.Stub {
        private final android.view.textclassifier.SystemTextClassifier.ResponseReceiver<T> mReceiver;

        BlockingCallback(java.lang.String str, android.view.textclassifier.TextClassificationConstants textClassificationConstants) {
            this.mReceiver = new android.view.textclassifier.SystemTextClassifier.ResponseReceiver<>(str, textClassificationConstants);
        }

        @Override // android.service.textclassifier.ITextClassifierCallback
        public void onSuccess(android.os.Bundle bundle) {
            this.mReceiver.onSuccess(android.service.textclassifier.TextClassifierService.getResponse(bundle));
        }

        @Override // android.service.textclassifier.ITextClassifierCallback
        public void onFailure() {
            this.mReceiver.onFailure();
        }

        public T get() {
            return this.mReceiver.get();
        }
    }

    private static final class ResponseReceiver<T> {
        private final java.util.concurrent.CountDownLatch mLatch;
        private final java.lang.String mName;
        private T mResponse;
        private final android.view.textclassifier.TextClassificationConstants mSettings;

        private ResponseReceiver(java.lang.String str, android.view.textclassifier.TextClassificationConstants textClassificationConstants) {
            this.mLatch = new java.util.concurrent.CountDownLatch(1);
            this.mName = str;
            this.mSettings = textClassificationConstants;
        }

        public void onSuccess(T t) {
            this.mResponse = t;
            this.mLatch.countDown();
        }

        public void onFailure() {
            android.view.textclassifier.Log.e("androidtc", "Request failed at " + this.mName, null);
            this.mLatch.countDown();
        }

        public T get() {
            if (android.os.Looper.myLooper() != android.os.Looper.getMainLooper()) {
                try {
                    if (!this.mLatch.await(this.mSettings.getSystemTextClassifierApiTimeoutInSecond(), java.util.concurrent.TimeUnit.SECONDS)) {
                        android.view.textclassifier.Log.w("androidtc", "Timeout in ResponseReceiver.get(): " + this.mName);
                    }
                } catch (java.lang.InterruptedException e) {
                    java.lang.Thread.currentThread().interrupt();
                    android.view.textclassifier.Log.e("androidtc", "Interrupted during ResponseReceiver.get(): " + this.mName, e);
                }
            }
            return this.mResponse;
        }
    }
}
