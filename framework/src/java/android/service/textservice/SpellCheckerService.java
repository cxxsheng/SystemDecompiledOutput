package android.service.textservice;

/* loaded from: classes3.dex */
public abstract class SpellCheckerService extends android.app.Service {
    private static final boolean DBG = false;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.textservice.SpellCheckerService";
    private static final java.lang.String TAG = android.service.textservice.SpellCheckerService.class.getSimpleName();
    private final android.service.textservice.SpellCheckerService.SpellCheckerServiceBinder mBinder = new android.service.textservice.SpellCheckerService.SpellCheckerServiceBinder(this);

    public abstract android.service.textservice.SpellCheckerService.Session createSession();

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mBinder;
    }

    public static abstract class Session {
        private android.service.textservice.SpellCheckerService.InternalISpellCheckerSession mInternalSession;
        private volatile android.service.textservice.SpellCheckerService.SentenceLevelAdapter mSentenceLevelAdapter;

        public abstract void onCreate();

        public abstract android.view.textservice.SuggestionsInfo onGetSuggestions(android.view.textservice.TextInfo textInfo, int i);

        public final void setInternalISpellCheckerSession(android.service.textservice.SpellCheckerService.InternalISpellCheckerSession internalISpellCheckerSession) {
            this.mInternalSession = internalISpellCheckerSession;
        }

        public android.view.textservice.SuggestionsInfo[] onGetSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i, boolean z) {
            int length = textInfoArr.length;
            android.view.textservice.SuggestionsInfo[] suggestionsInfoArr = new android.view.textservice.SuggestionsInfo[length];
            for (int i2 = 0; i2 < length; i2++) {
                suggestionsInfoArr[i2] = onGetSuggestions(textInfoArr[i2], i);
                suggestionsInfoArr[i2].setCookieAndSequence(textInfoArr[i2].getCookie(), textInfoArr[i2].getSequence());
            }
            return suggestionsInfoArr;
        }

        public android.view.textservice.SentenceSuggestionsInfo[] onGetSentenceSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i) {
            if (textInfoArr == null || textInfoArr.length == 0) {
                return android.service.textservice.SpellCheckerService.SentenceLevelAdapter.EMPTY_SENTENCE_SUGGESTIONS_INFOS;
            }
            if (this.mSentenceLevelAdapter == null) {
                synchronized (this) {
                    if (this.mSentenceLevelAdapter == null) {
                        java.lang.String locale = getLocale();
                        if (!android.text.TextUtils.isEmpty(locale)) {
                            this.mSentenceLevelAdapter = new android.service.textservice.SpellCheckerService.SentenceLevelAdapter(new java.util.Locale(locale));
                        }
                    }
                }
            }
            if (this.mSentenceLevelAdapter == null) {
                return android.service.textservice.SpellCheckerService.SentenceLevelAdapter.EMPTY_SENTENCE_SUGGESTIONS_INFOS;
            }
            int length = textInfoArr.length;
            android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr = new android.view.textservice.SentenceSuggestionsInfo[length];
            for (int i2 = 0; i2 < length; i2++) {
                android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceTextInfoParams splitWords = this.mSentenceLevelAdapter.getSplitWords(textInfoArr[i2]);
                java.util.ArrayList<android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceWordItem> arrayList = splitWords.mItems;
                int size = arrayList.size();
                android.view.textservice.TextInfo[] textInfoArr2 = new android.view.textservice.TextInfo[size];
                for (int i3 = 0; i3 < size; i3++) {
                    textInfoArr2[i3] = arrayList.get(i3).mTextInfo;
                }
                sentenceSuggestionsInfoArr[i2] = android.service.textservice.SpellCheckerService.SentenceLevelAdapter.reconstructSuggestions(splitWords, onGetSuggestionsMultiple(textInfoArr2, i, true));
            }
            return sentenceSuggestionsInfoArr;
        }

        public void onCancel() {
        }

        public void onClose() {
        }

        public java.lang.String getLocale() {
            return this.mInternalSession.getLocale();
        }

        public android.os.Bundle getBundle() {
            return this.mInternalSession.getBundle();
        }

        public int getSupportedAttributes() {
            return this.mInternalSession.getSupportedAttributes();
        }
    }

    private static class InternalISpellCheckerSession extends com.android.internal.textservice.ISpellCheckerSession.Stub {
        private final android.os.Bundle mBundle;
        private com.android.internal.textservice.ISpellCheckerSessionListener mListener;
        private final java.lang.String mLocale;
        private final android.service.textservice.SpellCheckerService.Session mSession;
        private final int mSupportedAttributes;

        public InternalISpellCheckerSession(java.lang.String str, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, android.service.textservice.SpellCheckerService.Session session, int i) {
            this.mListener = iSpellCheckerSessionListener;
            this.mSession = session;
            this.mLocale = str;
            this.mBundle = bundle;
            this.mSupportedAttributes = i;
            session.setInternalISpellCheckerSession(this);
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i, boolean z) {
            int threadPriority = android.os.Process.getThreadPriority(android.os.Process.myTid());
            try {
                android.os.Process.setThreadPriority(10);
                this.mListener.onGetSuggestions(this.mSession.onGetSuggestionsMultiple(textInfoArr, i, z));
            } catch (android.os.RemoteException e) {
            } catch (java.lang.Throwable th) {
                android.os.Process.setThreadPriority(threadPriority);
                throw th;
            }
            android.os.Process.setThreadPriority(threadPriority);
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSentenceSuggestionsMultiple(android.view.textservice.TextInfo[] textInfoArr, int i) {
            try {
                this.mListener.onGetSentenceSuggestions(this.mSession.onGetSentenceSuggestionsMultiple(textInfoArr, i));
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onCancel() {
            int threadPriority = android.os.Process.getThreadPriority(android.os.Process.myTid());
            try {
                android.os.Process.setThreadPriority(10);
                this.mSession.onCancel();
            } finally {
                android.os.Process.setThreadPriority(threadPriority);
            }
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onClose() {
            int threadPriority = android.os.Process.getThreadPriority(android.os.Process.myTid());
            try {
                android.os.Process.setThreadPriority(10);
                this.mSession.onClose();
            } finally {
                android.os.Process.setThreadPriority(threadPriority);
                this.mListener = null;
            }
        }

        public java.lang.String getLocale() {
            return this.mLocale;
        }

        public android.os.Bundle getBundle() {
            return this.mBundle;
        }

        public int getSupportedAttributes() {
            return this.mSupportedAttributes;
        }
    }

    private static class SpellCheckerServiceBinder extends com.android.internal.textservice.ISpellCheckerService.Stub {
        private final java.lang.ref.WeakReference<android.service.textservice.SpellCheckerService> mInternalServiceRef;

        public SpellCheckerServiceBinder(android.service.textservice.SpellCheckerService spellCheckerService) {
            this.mInternalServiceRef = new java.lang.ref.WeakReference<>(spellCheckerService);
        }

        @Override // com.android.internal.textservice.ISpellCheckerService
        public void getISpellCheckerSession(java.lang.String str, com.android.internal.textservice.ISpellCheckerSessionListener iSpellCheckerSessionListener, android.os.Bundle bundle, int i, com.android.internal.textservice.ISpellCheckerServiceCallback iSpellCheckerServiceCallback) {
            android.service.textservice.SpellCheckerService.InternalISpellCheckerSession internalISpellCheckerSession;
            android.service.textservice.SpellCheckerService spellCheckerService = this.mInternalServiceRef.get();
            if (spellCheckerService == null) {
                internalISpellCheckerSession = null;
            } else {
                android.service.textservice.SpellCheckerService.Session createSession = spellCheckerService.createSession();
                android.service.textservice.SpellCheckerService.InternalISpellCheckerSession internalISpellCheckerSession2 = new android.service.textservice.SpellCheckerService.InternalISpellCheckerSession(str, iSpellCheckerSessionListener, bundle, createSession, i);
                createSession.onCreate();
                internalISpellCheckerSession = internalISpellCheckerSession2;
            }
            try {
                iSpellCheckerServiceCallback.onSessionCreated(internalISpellCheckerSession);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private static class SentenceLevelAdapter {
        public static final android.view.textservice.SentenceSuggestionsInfo[] EMPTY_SENTENCE_SUGGESTIONS_INFOS = new android.view.textservice.SentenceSuggestionsInfo[0];
        private static final android.view.textservice.SuggestionsInfo EMPTY_SUGGESTIONS_INFO = new android.view.textservice.SuggestionsInfo(0, null);
        private final android.text.method.WordIterator mWordIterator;

        public static class SentenceWordItem {
            public final int mLength;
            public final int mStart;
            public final android.view.textservice.TextInfo mTextInfo;

            public SentenceWordItem(android.view.textservice.TextInfo textInfo, int i, int i2) {
                this.mTextInfo = textInfo;
                this.mStart = i;
                this.mLength = i2 - i;
            }
        }

        public static class SentenceTextInfoParams {
            final java.util.ArrayList<android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceWordItem> mItems;
            final android.view.textservice.TextInfo mOriginalTextInfo;
            final int mSize;

            public SentenceTextInfoParams(android.view.textservice.TextInfo textInfo, java.util.ArrayList<android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceWordItem> arrayList) {
                this.mOriginalTextInfo = textInfo;
                this.mItems = arrayList;
                this.mSize = arrayList.size();
            }
        }

        public SentenceLevelAdapter(java.util.Locale locale) {
            this.mWordIterator = new android.text.method.WordIterator(locale);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceTextInfoParams getSplitWords(android.view.textservice.TextInfo textInfo) {
            int beginning;
            android.text.method.WordIterator wordIterator = this.mWordIterator;
            java.lang.String text = textInfo.getText();
            int cookie = textInfo.getCookie();
            int length = text.length();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            wordIterator.setCharSequence(text, 0, text.length());
            int following = wordIterator.following(0);
            if (following == -1) {
                beginning = -1;
            } else {
                beginning = wordIterator.getBeginning(following);
            }
            int i = following;
            int i2 = beginning;
            while (i2 <= length && i != -1 && i2 != -1) {
                if (i >= 0 && i > i2) {
                    java.lang.CharSequence subSequence = text.subSequence(i2, i);
                    arrayList.add(new android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceWordItem(new android.view.textservice.TextInfo(subSequence, 0, subSequence.length(), cookie, subSequence.hashCode()), i2, i));
                }
                i = wordIterator.following(i);
                if (i == -1) {
                    break;
                }
                i2 = wordIterator.getBeginning(i);
            }
            return new android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceTextInfoParams(textInfo, arrayList);
        }

        public static android.view.textservice.SentenceSuggestionsInfo reconstructSuggestions(android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceTextInfoParams sentenceTextInfoParams, android.view.textservice.SuggestionsInfo[] suggestionsInfoArr) {
            android.view.textservice.SuggestionsInfo suggestionsInfo;
            if (suggestionsInfoArr == null || suggestionsInfoArr.length == 0 || sentenceTextInfoParams == null) {
                return null;
            }
            int cookie = sentenceTextInfoParams.mOriginalTextInfo.getCookie();
            int sequence = sentenceTextInfoParams.mOriginalTextInfo.getSequence();
            int i = sentenceTextInfoParams.mSize;
            int[] iArr = new int[i];
            int[] iArr2 = new int[i];
            android.view.textservice.SuggestionsInfo[] suggestionsInfoArr2 = new android.view.textservice.SuggestionsInfo[i];
            for (int i2 = 0; i2 < i; i2++) {
                android.service.textservice.SpellCheckerService.SentenceLevelAdapter.SentenceWordItem sentenceWordItem = sentenceTextInfoParams.mItems.get(i2);
                int i3 = 0;
                while (true) {
                    if (i3 >= suggestionsInfoArr.length) {
                        suggestionsInfo = null;
                        break;
                    }
                    suggestionsInfo = suggestionsInfoArr[i3];
                    if (suggestionsInfo == null || suggestionsInfo.getSequence() != sentenceWordItem.mTextInfo.getSequence()) {
                        i3++;
                    } else {
                        suggestionsInfo.setCookieAndSequence(cookie, sequence);
                        break;
                    }
                }
                iArr[i2] = sentenceWordItem.mStart;
                iArr2[i2] = sentenceWordItem.mLength;
                if (suggestionsInfo == null) {
                    suggestionsInfo = EMPTY_SUGGESTIONS_INFO;
                }
                suggestionsInfoArr2[i2] = suggestionsInfo;
            }
            return new android.view.textservice.SentenceSuggestionsInfo(suggestionsInfoArr2, iArr, iArr2);
        }
    }
}
