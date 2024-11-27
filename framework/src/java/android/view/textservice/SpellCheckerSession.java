package android.view.textservice;

/* loaded from: classes4.dex */
public class SpellCheckerSession {
    private static final boolean DBG = false;
    private static final int MSG_ON_GET_SUGGESTION_MULTIPLE = 1;
    private static final int MSG_ON_GET_SUGGESTION_MULTIPLE_FOR_SENTENCE = 2;
    public static final java.lang.String SERVICE_META_DATA = "android.view.textservice.scs";
    private static final java.lang.String TAG = android.view.textservice.SpellCheckerSession.class.getSimpleName();
    private final java.util.concurrent.Executor mExecutor;
    private final dalvik.system.CloseGuard mGuard = dalvik.system.CloseGuard.get();
    private final android.view.textservice.SpellCheckerSession.InternalListener mInternalListener;
    private final android.view.textservice.SpellCheckerInfo mSpellCheckerInfo;
    private final android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener mSpellCheckerSessionListener;
    private final android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl mSpellCheckerSessionListenerImpl;
    private final android.view.textservice.TextServicesManager mTextServicesManager;

    public interface SpellCheckerSessionListener {
        void onGetSentenceSuggestions(android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr);

        void onGetSuggestions(android.view.textservice.SuggestionsInfo[] suggestionsInfoArr);
    }

    public SpellCheckerSession(android.view.textservice.SpellCheckerInfo spellCheckerInfo, android.view.textservice.TextServicesManager textServicesManager, android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener spellCheckerSessionListener, java.util.concurrent.Executor executor) {
        if (spellCheckerInfo == null || spellCheckerSessionListener == null || textServicesManager == null) {
            throw new java.lang.NullPointerException();
        }
        this.mSpellCheckerInfo = spellCheckerInfo;
        this.mSpellCheckerSessionListenerImpl = new android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl(this);
        this.mInternalListener = new android.view.textservice.SpellCheckerSession.InternalListener(this.mSpellCheckerSessionListenerImpl);
        this.mTextServicesManager = textServicesManager;
        this.mSpellCheckerSessionListener = spellCheckerSessionListener;
        this.mExecutor = executor;
        this.mGuard.open("finishSession");
    }

    public boolean isSessionDisconnected() {
        return this.mSpellCheckerSessionListenerImpl.isDisconnected();
    }

    public android.view.textservice.SpellCheckerInfo getSpellChecker() {
        return this.mSpellCheckerInfo;
    }

    public void cancel() {
        this.mSpellCheckerSessionListenerImpl.cancel();
    }

    public void close() {
        this.mGuard.close();
        this.mSpellCheckerSessionListenerImpl.close();
        this.mTextServicesManager.finishSpellCheckerService(this.mSpellCheckerSessionListenerImpl);
    }

    public void getSentenceSuggestions(android.view.textservice.TextInfo[] textInfoArr, int i) {
        android.view.inputmethod.InputMethodManager inputMethodManager = this.mTextServicesManager.getInputMethodManager();
        if (inputMethodManager != null && inputMethodManager.isInputMethodSuppressingSpellChecker()) {
            handleOnGetSentenceSuggestionsMultiple(new android.view.textservice.SentenceSuggestionsInfo[0]);
        } else {
            this.mSpellCheckerSessionListenerImpl.getSentenceSuggestionsMultiple(textInfoArr, i);
        }
    }

    @java.lang.Deprecated
    public void getSuggestions(android.view.textservice.TextInfo textInfo, int i) {
        getSuggestions(new android.view.textservice.TextInfo[]{textInfo}, i, false);
    }

    @java.lang.Deprecated
    public void getSuggestions(android.view.textservice.TextInfo[] textInfoArr, int i, boolean z) {
        android.view.inputmethod.InputMethodManager inputMethodManager = this.mTextServicesManager.getInputMethodManager();
        if (inputMethodManager != null && inputMethodManager.isInputMethodSuppressingSpellChecker()) {
            handleOnGetSuggestionsMultiple(new android.view.textservice.SuggestionsInfo[0]);
        } else {
            this.mSpellCheckerSessionListenerImpl.getSuggestionsMultiple(textInfoArr, i, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleOnGetSuggestionsMultiple$0(android.view.textservice.SuggestionsInfo[] suggestionsInfoArr) {
        this.mSpellCheckerSessionListener.onGetSuggestions(suggestionsInfoArr);
    }

    void handleOnGetSuggestionsMultiple(final android.view.textservice.SuggestionsInfo[] suggestionsInfoArr) {
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.textservice.SpellCheckerSession$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.view.textservice.SpellCheckerSession.this.lambda$handleOnGetSuggestionsMultiple$0(suggestionsInfoArr);
            }
        });
    }

    void handleOnGetSentenceSuggestionsMultiple(final android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.textservice.SpellCheckerSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.view.textservice.SpellCheckerSession.this.lambda$handleOnGetSentenceSuggestionsMultiple$1(sentenceSuggestionsInfoArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleOnGetSentenceSuggestionsMultiple$1(android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
        this.mSpellCheckerSessionListener.onGetSentenceSuggestions(sentenceSuggestionsInfoArr);
    }

    private static final class SpellCheckerSessionListenerImpl extends com.android.internal.textservice.ISpellCheckerSessionListener.Stub {
        private static final int STATE_CLOSED_AFTER_CONNECTION = 2;
        private static final int STATE_CLOSED_BEFORE_CONNECTION = 3;
        private static final int STATE_CONNECTED = 1;
        private static final int STATE_WAIT_CONNECTION = 0;
        private static final int TASK_CANCEL = 1;
        private static final int TASK_CLOSE = 3;
        private static final int TASK_GET_SUGGESTIONS_MULTIPLE = 2;
        private static final int TASK_GET_SUGGESTIONS_MULTIPLE_FOR_SENTENCE = 4;
        private android.os.Handler mAsyncHandler;
        private com.android.internal.textservice.ISpellCheckerSession mISpellCheckerSession;
        private android.view.textservice.SpellCheckerSession mSpellCheckerSession;
        private android.os.HandlerThread mThread;
        private final java.util.Queue<android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams> mPendingTasks = new java.util.ArrayDeque();
        private int mState = 0;

        private static java.lang.String taskToString(int i) {
            switch (i) {
                case 1:
                    return "TASK_CANCEL";
                case 2:
                    return "TASK_GET_SUGGESTIONS_MULTIPLE";
                case 3:
                    return "TASK_CLOSE";
                case 4:
                    return "TASK_GET_SUGGESTIONS_MULTIPLE_FOR_SENTENCE";
                default:
                    return "Unexpected task=" + i;
            }
        }

        private static java.lang.String stateToString(int i) {
            switch (i) {
                case 0:
                    return "STATE_WAIT_CONNECTION";
                case 1:
                    return "STATE_CONNECTED";
                case 2:
                    return "STATE_CLOSED_AFTER_CONNECTION";
                case 3:
                    return "STATE_CLOSED_BEFORE_CONNECTION";
                default:
                    return "Unexpected state=" + i;
            }
        }

        SpellCheckerSessionListenerImpl(android.view.textservice.SpellCheckerSession spellCheckerSession) {
            this.mSpellCheckerSession = spellCheckerSession;
        }

        private static class SpellCheckerParams {
            public final boolean mSequentialWords;
            public com.android.internal.textservice.ISpellCheckerSession mSession;
            public final int mSuggestionsLimit;
            public final android.view.textservice.TextInfo[] mTextInfos;
            public final int mWhat;

            public SpellCheckerParams(int i, android.view.textservice.TextInfo[] textInfoArr, int i2, boolean z) {
                this.mWhat = i;
                this.mTextInfos = textInfoArr;
                this.mSuggestionsLimit = i2;
                this.mSequentialWords = z;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void processTask(com.android.internal.textservice.ISpellCheckerSession iSpellCheckerSession, android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams spellCheckerParams, boolean z) {
            if (z || this.mAsyncHandler == null) {
                switch (spellCheckerParams.mWhat) {
                    case 1:
                        try {
                            iSpellCheckerSession.onCancel();
                            break;
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(android.view.textservice.SpellCheckerSession.TAG, "Failed to cancel " + e);
                            break;
                        }
                    case 2:
                        try {
                            iSpellCheckerSession.onGetSuggestionsMultiple(spellCheckerParams.mTextInfos, spellCheckerParams.mSuggestionsLimit, spellCheckerParams.mSequentialWords);
                            break;
                        } catch (android.os.RemoteException e2) {
                            android.util.Log.e(android.view.textservice.SpellCheckerSession.TAG, "Failed to get suggestions " + e2);
                            break;
                        }
                    case 3:
                        try {
                            iSpellCheckerSession.onClose();
                            break;
                        } catch (android.os.RemoteException e3) {
                            android.util.Log.e(android.view.textservice.SpellCheckerSession.TAG, "Failed to close " + e3);
                            break;
                        }
                    case 4:
                        try {
                            iSpellCheckerSession.onGetSentenceSuggestionsMultiple(spellCheckerParams.mTextInfos, spellCheckerParams.mSuggestionsLimit);
                            break;
                        } catch (android.os.RemoteException e4) {
                            android.util.Log.e(android.view.textservice.SpellCheckerSession.TAG, "Failed to get suggestions " + e4);
                            break;
                        }
                }
            } else {
                spellCheckerParams.mSession = iSpellCheckerSession;
                this.mAsyncHandler.sendMessage(android.os.Message.obtain(this.mAsyncHandler, 1, spellCheckerParams));
            }
            if (spellCheckerParams.mWhat == 3) {
                synchronized (this) {
                    processCloseLocked();
                }
            }
        }

        private void processCloseLocked() {
            this.mISpellCheckerSession = null;
            if (this.mThread != null) {
                this.mThread.quit();
            }
            this.mSpellCheckerSession = null;
            this.mPendingTasks.clear();
            this.mThread = null;
            this.mAsyncHandler = null;
            switch (this.mState) {
                case 0:
                    this.mState = 3;
                    break;
                case 1:
                    this.mState = 2;
                    break;
                default:
                    android.util.Log.e(android.view.textservice.SpellCheckerSession.TAG, "processCloseLocked is called unexpectedly. mState=" + stateToString(this.mState));
                    break;
            }
        }

        public void onServiceConnected(com.android.internal.textservice.ISpellCheckerSession iSpellCheckerSession) {
            synchronized (this) {
                switch (this.mState) {
                    case 0:
                        if (iSpellCheckerSession == null) {
                            android.util.Log.e(android.view.textservice.SpellCheckerSession.TAG, "ignoring onServiceConnected due to session=null");
                            return;
                        }
                        this.mISpellCheckerSession = iSpellCheckerSession;
                        if ((iSpellCheckerSession.asBinder() instanceof android.os.Binder) && this.mThread == null) {
                            this.mThread = new android.os.HandlerThread("SpellCheckerSession", 10);
                            this.mThread.start();
                            this.mAsyncHandler = new android.os.Handler(this.mThread.getLooper()) { // from class: android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.1
                                @Override // android.os.Handler
                                public void handleMessage(android.os.Message message) {
                                    android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams spellCheckerParams = (android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams) message.obj;
                                    android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.this.processTask(spellCheckerParams.mSession, spellCheckerParams, true);
                                }
                            };
                        }
                        this.mState = 1;
                        while (!this.mPendingTasks.isEmpty()) {
                            processTask(iSpellCheckerSession, this.mPendingTasks.poll(), false);
                        }
                        return;
                    case 3:
                        return;
                    default:
                        android.util.Log.e(android.view.textservice.SpellCheckerSession.TAG, "ignoring onServiceConnected due to unexpected mState=" + stateToString(this.mState));
                        return;
                }
            }
        }

        public void cancel() {
            processOrEnqueueTask(new android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams(1, null, 0, false));
        }

        public void getSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i, boolean z) {
            processOrEnqueueTask(new android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams(2, textInfoArr, i, z));
        }

        public void getSentenceSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i) {
            processOrEnqueueTask(new android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams(4, textInfoArr, i, false));
        }

        public void close() {
            processOrEnqueueTask(new android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams(3, null, 0, false));
        }

        public boolean isDisconnected() {
            boolean z;
            synchronized (this) {
                z = true;
                if (this.mState == 1) {
                    z = false;
                }
            }
            return z;
        }

        private void processOrEnqueueTask(android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams spellCheckerParams) {
            synchronized (this) {
                if (spellCheckerParams.mWhat == 3 && (this.mState == 2 || this.mState == 3)) {
                    return;
                }
                if (this.mState != 0 && this.mState != 1) {
                    android.util.Log.e(android.view.textservice.SpellCheckerSession.TAG, "ignoring processOrEnqueueTask due to unexpected mState=" + stateToString(this.mState) + " scp.mWhat=" + taskToString(spellCheckerParams.mWhat));
                    return;
                }
                if (this.mState == 0) {
                    if (spellCheckerParams.mWhat == 3) {
                        processCloseLocked();
                        return;
                    }
                    android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams spellCheckerParams2 = null;
                    if (spellCheckerParams.mWhat == 1) {
                        while (!this.mPendingTasks.isEmpty()) {
                            android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl.SpellCheckerParams poll = this.mPendingTasks.poll();
                            if (poll.mWhat == 3) {
                                spellCheckerParams2 = poll;
                            }
                        }
                    }
                    this.mPendingTasks.offer(spellCheckerParams);
                    if (spellCheckerParams2 != null) {
                        this.mPendingTasks.offer(spellCheckerParams2);
                    }
                    return;
                }
                processTask(this.mISpellCheckerSession, spellCheckerParams, false);
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSessionListener
        public void onGetSuggestions(android.view.textservice.SuggestionsInfo[] suggestionsInfoArr) {
            android.view.textservice.SpellCheckerSession spellCheckerSession = getSpellCheckerSession();
            if (spellCheckerSession != null) {
                spellCheckerSession.handleOnGetSuggestionsMultiple(suggestionsInfoArr);
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSessionListener
        public void onGetSentenceSuggestions(android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
            android.view.textservice.SpellCheckerSession spellCheckerSession = getSpellCheckerSession();
            if (spellCheckerSession != null) {
                spellCheckerSession.handleOnGetSentenceSuggestionsMultiple(sentenceSuggestionsInfoArr);
            }
        }

        private android.view.textservice.SpellCheckerSession getSpellCheckerSession() {
            android.view.textservice.SpellCheckerSession spellCheckerSession;
            synchronized (this) {
                spellCheckerSession = this.mSpellCheckerSession;
            }
            return spellCheckerSession;
        }
    }

    public static class SpellCheckerSessionParams {
        private final android.os.Bundle mExtras;
        private final java.util.Locale mLocale;
        private final boolean mShouldReferToSpellCheckerLanguageSettings;
        private final int mSupportedAttributes;

        private SpellCheckerSessionParams(java.util.Locale locale, boolean z, int i, android.os.Bundle bundle) {
            this.mLocale = locale;
            this.mShouldReferToSpellCheckerLanguageSettings = z;
            this.mSupportedAttributes = i;
            this.mExtras = bundle;
        }

        public java.util.Locale getLocale() {
            return this.mLocale;
        }

        public boolean shouldReferToSpellCheckerLanguageSettings() {
            return this.mShouldReferToSpellCheckerLanguageSettings;
        }

        public int getSupportedAttributes() {
            return this.mSupportedAttributes;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private java.util.Locale mLocale;
            private boolean mShouldReferToSpellCheckerLanguageSettings = false;
            private int mSupportedAttributes = 0;
            private android.os.Bundle mExtras = android.os.Bundle.EMPTY;

            public android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams build() {
                if (this.mLocale == null && !this.mShouldReferToSpellCheckerLanguageSettings) {
                    throw new java.lang.IllegalArgumentException("mLocale should not be null if  mShouldReferToSpellCheckerLanguageSettings is false.");
                }
                return new android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams(this.mLocale, this.mShouldReferToSpellCheckerLanguageSettings, this.mSupportedAttributes, this.mExtras);
            }

            public android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams.Builder setLocale(java.util.Locale locale) {
                this.mLocale = locale;
                return this;
            }

            public android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams.Builder setShouldReferToSpellCheckerLanguageSettings(boolean z) {
                this.mShouldReferToSpellCheckerLanguageSettings = z;
                return this;
            }

            public android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams.Builder setSupportedAttributes(int i) {
                this.mSupportedAttributes = i;
                return this;
            }

            public android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams.Builder setExtras(android.os.Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }
        }
    }

    private static final class InternalListener extends com.android.internal.textservice.ITextServicesSessionListener.Stub {
        private final android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl mParentSpellCheckerSessionListenerImpl;

        public InternalListener(android.view.textservice.SpellCheckerSession.SpellCheckerSessionListenerImpl spellCheckerSessionListenerImpl) {
            this.mParentSpellCheckerSessionListenerImpl = spellCheckerSessionListenerImpl;
        }

        @Override // com.android.internal.textservice.ITextServicesSessionListener
        public void onServiceConnected(com.android.internal.textservice.ISpellCheckerSession iSpellCheckerSession) {
            this.mParentSpellCheckerSessionListenerImpl.onServiceConnected(iSpellCheckerSession);
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mGuard != null) {
                this.mGuard.warnIfOpen();
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public com.android.internal.textservice.ITextServicesSessionListener getTextServicesSessionListener() {
        return this.mInternalListener;
    }

    public com.android.internal.textservice.ISpellCheckerSessionListener getSpellCheckerSessionListener() {
        return this.mSpellCheckerSessionListenerImpl;
    }
}
