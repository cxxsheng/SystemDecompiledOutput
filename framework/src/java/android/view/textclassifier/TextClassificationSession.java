package android.view.textclassifier;

/* loaded from: classes4.dex */
final class TextClassificationSession implements android.view.textclassifier.TextClassifier {
    private static final java.lang.String LOG_TAG = "TextClassificationSession";
    private final android.view.textclassifier.TextClassificationContext mClassificationContext;
    private final sun.misc.Cleaner mCleaner;
    private final android.view.textclassifier.TextClassifier mDelegate;
    private boolean mDestroyed;
    private final android.view.textclassifier.TextClassificationSession.SelectionEventHelper mEventHelper;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.view.textclassifier.TextClassificationSessionId mSessionId = new android.view.textclassifier.TextClassificationSessionId();

    TextClassificationSession(android.view.textclassifier.TextClassificationContext textClassificationContext, android.view.textclassifier.TextClassifier textClassifier) {
        this.mClassificationContext = (android.view.textclassifier.TextClassificationContext) java.util.Objects.requireNonNull(textClassificationContext);
        this.mDelegate = (android.view.textclassifier.TextClassifier) java.util.Objects.requireNonNull(textClassifier);
        this.mEventHelper = new android.view.textclassifier.TextClassificationSession.SelectionEventHelper(this.mSessionId, this.mClassificationContext);
        initializeRemoteSession();
        this.mCleaner = sun.misc.Cleaner.create(this, new android.view.textclassifier.TextClassificationSession.CleanerRunnable(this.mEventHelper, this.mDelegate));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.textclassifier.TextSelection lambda$suggestSelection$0(android.view.textclassifier.TextSelection.Request request) {
        return this.mDelegate.suggestSelection(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.TextSelection suggestSelection(final android.view.textclassifier.TextSelection.Request request) {
        return (android.view.textclassifier.TextSelection) checkDestroyedAndRun(new java.util.function.Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.view.textclassifier.TextSelection lambda$suggestSelection$0;
                lambda$suggestSelection$0 = android.view.textclassifier.TextClassificationSession.this.lambda$suggestSelection$0(request);
                return lambda$suggestSelection$0;
            }
        });
    }

    private void initializeRemoteSession() {
        if (this.mDelegate instanceof android.view.textclassifier.SystemTextClassifier) {
            ((android.view.textclassifier.SystemTextClassifier) this.mDelegate).initializeRemoteSession(this.mClassificationContext, this.mSessionId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.textclassifier.TextClassification lambda$classifyText$1(android.view.textclassifier.TextClassification.Request request) {
        return this.mDelegate.classifyText(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.TextClassification classifyText(final android.view.textclassifier.TextClassification.Request request) {
        return (android.view.textclassifier.TextClassification) checkDestroyedAndRun(new java.util.function.Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.view.textclassifier.TextClassification lambda$classifyText$1;
                lambda$classifyText$1 = android.view.textclassifier.TextClassificationSession.this.lambda$classifyText$1(request);
                return lambda$classifyText$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.textclassifier.TextLinks lambda$generateLinks$2(android.view.textclassifier.TextLinks.Request request) {
        return this.mDelegate.generateLinks(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.TextLinks generateLinks(final android.view.textclassifier.TextLinks.Request request) {
        return (android.view.textclassifier.TextLinks) checkDestroyedAndRun(new java.util.function.Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.view.textclassifier.TextLinks lambda$generateLinks$2;
                lambda$generateLinks$2 = android.view.textclassifier.TextClassificationSession.this.lambda$generateLinks$2(request);
                return lambda$generateLinks$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.textclassifier.ConversationActions lambda$suggestConversationActions$3(android.view.textclassifier.ConversationActions.Request request) {
        return this.mDelegate.suggestConversationActions(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.ConversationActions suggestConversationActions(final android.view.textclassifier.ConversationActions.Request request) {
        return (android.view.textclassifier.ConversationActions) checkDestroyedAndRun(new java.util.function.Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.view.textclassifier.ConversationActions lambda$suggestConversationActions$3;
                lambda$suggestConversationActions$3 = android.view.textclassifier.TextClassificationSession.this.lambda$suggestConversationActions$3(request);
                return lambda$suggestConversationActions$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.textclassifier.TextLanguage lambda$detectLanguage$4(android.view.textclassifier.TextLanguage.Request request) {
        return this.mDelegate.detectLanguage(request);
    }

    @Override // android.view.textclassifier.TextClassifier
    public android.view.textclassifier.TextLanguage detectLanguage(final android.view.textclassifier.TextLanguage.Request request) {
        return (android.view.textclassifier.TextLanguage) checkDestroyedAndRun(new java.util.function.Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.view.textclassifier.TextLanguage lambda$detectLanguage$4;
                lambda$detectLanguage$4 = android.view.textclassifier.TextClassificationSession.this.lambda$detectLanguage$4(request);
                return lambda$detectLanguage$4;
            }
        });
    }

    @Override // android.view.textclassifier.TextClassifier
    public int getMaxGenerateLinksTextLength() {
        final android.view.textclassifier.TextClassifier textClassifier = this.mDelegate;
        java.util.Objects.requireNonNull(textClassifier);
        return ((java.lang.Integer) checkDestroyedAndRun(new java.util.function.Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                return java.lang.Integer.valueOf(android.view.textclassifier.TextClassifier.this.getMaxGenerateLinksTextLength());
            }
        })).intValue();
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onSelectionEvent(final android.view.textclassifier.SelectionEvent selectionEvent) {
        checkDestroyedAndRun(new java.util.function.Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Object lambda$onSelectionEvent$5;
                lambda$onSelectionEvent$5 = android.view.textclassifier.TextClassificationSession.this.lambda$onSelectionEvent$5(selectionEvent);
                return lambda$onSelectionEvent$5;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Object lambda$onSelectionEvent$5(android.view.textclassifier.SelectionEvent selectionEvent) {
        try {
            if (this.mEventHelper.sanitizeEvent(selectionEvent)) {
                this.mDelegate.onSelectionEvent(selectionEvent);
                return null;
            }
            return null;
        } catch (java.lang.Exception e) {
            android.view.textclassifier.Log.e(LOG_TAG, "Error reporting text classifier selection event", e);
            return null;
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void onTextClassifierEvent(final android.view.textclassifier.TextClassifierEvent textClassifierEvent) {
        checkDestroyedAndRun(new java.util.function.Supplier() { // from class: android.view.textclassifier.TextClassificationSession$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Object lambda$onTextClassifierEvent$6;
                lambda$onTextClassifierEvent$6 = android.view.textclassifier.TextClassificationSession.this.lambda$onTextClassifierEvent$6(textClassifierEvent);
                return lambda$onTextClassifierEvent$6;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Object lambda$onTextClassifierEvent$6(android.view.textclassifier.TextClassifierEvent textClassifierEvent) {
        try {
            textClassifierEvent.mHiddenTempSessionId = this.mSessionId;
            this.mDelegate.onTextClassifierEvent(textClassifierEvent);
            return null;
        } catch (java.lang.Exception e) {
            android.view.textclassifier.Log.e(LOG_TAG, "Error reporting text classifier event", e);
            return null;
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public void destroy() {
        synchronized (this.mLock) {
            if (!this.mDestroyed) {
                this.mCleaner.clean();
                this.mDestroyed = true;
            }
        }
    }

    @Override // android.view.textclassifier.TextClassifier
    public boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    private <T> T checkDestroyedAndRun(java.util.function.Supplier<T> supplier) {
        if (!isDestroyed()) {
            T t = supplier.get();
            synchronized (this.mLock) {
                if (!this.mDestroyed) {
                    return t;
                }
            }
        }
        throw new java.lang.IllegalStateException("This TextClassification session has been destroyed");
    }

    private static final class SelectionEventHelper {
        private final android.view.textclassifier.TextClassificationContext mContext;
        private int mInvocationMethod = 0;
        private android.view.textclassifier.SelectionEvent mPrevEvent;
        private final android.view.textclassifier.TextClassificationSessionId mSessionId;
        private android.view.textclassifier.SelectionEvent mSmartEvent;
        private android.view.textclassifier.SelectionEvent mStartEvent;

        SelectionEventHelper(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassificationContext textClassificationContext) {
            this.mSessionId = (android.view.textclassifier.TextClassificationSessionId) java.util.Objects.requireNonNull(textClassificationSessionId);
            this.mContext = (android.view.textclassifier.TextClassificationContext) java.util.Objects.requireNonNull(textClassificationContext);
        }

        boolean sanitizeEvent(android.view.textclassifier.SelectionEvent selectionEvent) {
            updateInvocationMethod(selectionEvent);
            modifyAutoSelectionEventType(selectionEvent);
            if (selectionEvent.getEventType() != 1 && this.mStartEvent == null) {
                android.view.textclassifier.Log.d(android.view.textclassifier.TextClassificationSession.LOG_TAG, "Selection session not yet started. Ignoring event");
                return false;
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            switch (selectionEvent.getEventType()) {
                case 1:
                    com.android.internal.util.Preconditions.checkArgument(selectionEvent.getAbsoluteEnd() == selectionEvent.getAbsoluteStart() + 1);
                    selectionEvent.setSessionId(this.mSessionId);
                    this.mStartEvent = selectionEvent;
                    break;
                case 2:
                    if (this.mPrevEvent != null && this.mPrevEvent.getAbsoluteStart() == selectionEvent.getAbsoluteStart() && this.mPrevEvent.getAbsoluteEnd() == selectionEvent.getAbsoluteEnd()) {
                        return false;
                    }
                    break;
                case 3:
                case 4:
                case 5:
                    this.mSmartEvent = selectionEvent;
                    break;
                case 100:
                case 107:
                    if (this.mPrevEvent != null) {
                        selectionEvent.setEntityType(this.mPrevEvent.getEntityType());
                        break;
                    }
                    break;
            }
            selectionEvent.setEventTime(currentTimeMillis);
            if (this.mStartEvent != null) {
                selectionEvent.setSessionId(this.mStartEvent.getSessionId()).setDurationSinceSessionStart(currentTimeMillis - this.mStartEvent.getEventTime()).setStart(selectionEvent.getAbsoluteStart() - this.mStartEvent.getAbsoluteStart()).setEnd(selectionEvent.getAbsoluteEnd() - this.mStartEvent.getAbsoluteStart());
            }
            if (this.mSmartEvent != null) {
                selectionEvent.setResultId(this.mSmartEvent.getResultId()).setSmartStart(this.mSmartEvent.getAbsoluteStart() - this.mStartEvent.getAbsoluteStart()).setSmartEnd(this.mSmartEvent.getAbsoluteEnd() - this.mStartEvent.getAbsoluteStart());
            }
            if (this.mPrevEvent != null) {
                selectionEvent.setDurationSincePreviousEvent(currentTimeMillis - this.mPrevEvent.getEventTime()).setEventIndex(this.mPrevEvent.getEventIndex() + 1);
            }
            this.mPrevEvent = selectionEvent;
            return true;
        }

        void endSession() {
            this.mPrevEvent = null;
            this.mSmartEvent = null;
            this.mStartEvent = null;
        }

        private void updateInvocationMethod(android.view.textclassifier.SelectionEvent selectionEvent) {
            selectionEvent.setTextClassificationSessionContext(this.mContext);
            if (selectionEvent.getInvocationMethod() == 0) {
                selectionEvent.setInvocationMethod(this.mInvocationMethod);
            } else {
                this.mInvocationMethod = selectionEvent.getInvocationMethod();
            }
        }

        private void modifyAutoSelectionEventType(android.view.textclassifier.SelectionEvent selectionEvent) {
            switch (selectionEvent.getEventType()) {
                case 3:
                case 4:
                case 5:
                    if (android.view.textclassifier.SelectionSessionLogger.isPlatformLocalTextClassifierSmartSelection(selectionEvent.getResultId())) {
                        if (selectionEvent.getAbsoluteEnd() - selectionEvent.getAbsoluteStart() > 1) {
                            selectionEvent.setEventType(4);
                            break;
                        } else {
                            selectionEvent.setEventType(3);
                            break;
                        }
                    } else {
                        selectionEvent.setEventType(5);
                        break;
                    }
            }
        }
    }

    private static class CleanerRunnable implements java.lang.Runnable {
        private final android.view.textclassifier.TextClassifier mDelegate;
        private final android.view.textclassifier.TextClassificationSession.SelectionEventHelper mEventHelper;

        CleanerRunnable(android.view.textclassifier.TextClassificationSession.SelectionEventHelper selectionEventHelper, android.view.textclassifier.TextClassifier textClassifier) {
            this.mEventHelper = (android.view.textclassifier.TextClassificationSession.SelectionEventHelper) java.util.Objects.requireNonNull(selectionEventHelper);
            this.mDelegate = (android.view.textclassifier.TextClassifier) java.util.Objects.requireNonNull(textClassifier);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mEventHelper.endSession();
            this.mDelegate.destroy();
        }
    }
}
