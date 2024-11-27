package android.speech.tts;

/* loaded from: classes3.dex */
public abstract class TextToSpeechService extends android.app.Service {
    private static final boolean DBG = false;
    private static final java.lang.String SYNTH_THREAD_NAME = "SynthThread";
    private static final java.lang.String TAG = "TextToSpeechService";
    private android.speech.tts.AudioPlaybackHandler mAudioPlaybackHandler;
    private android.speech.tts.TextToSpeechService.CallbackMap mCallbacks;
    private android.speech.tts.TtsEngines mEngineHelper;
    private java.lang.String mPackageName;
    private android.speech.tts.TextToSpeechService.SynthHandler mSynthHandler;
    private final java.lang.Object mVoicesInfoLock = new java.lang.Object();
    private final android.speech.tts.ITextToSpeechService.Stub mBinder = new android.speech.tts.ITextToSpeechService.Stub() { // from class: android.speech.tts.TextToSpeechService.1
        @Override // android.speech.tts.ITextToSpeechService
        public int speak(android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i, android.os.Bundle bundle, java.lang.String str) {
            if (!checkNonNull(iBinder, charSequence, bundle)) {
                return -1;
            }
            return android.speech.tts.TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(i, android.speech.tts.TextToSpeechService.this.new SynthesisSpeechItem(iBinder, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), bundle, str, charSequence));
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int synthesizeToFileDescriptor(android.os.IBinder iBinder, java.lang.CharSequence charSequence, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Bundle bundle, java.lang.String str) {
            if (!checkNonNull(iBinder, charSequence, parcelFileDescriptor, bundle)) {
                return -1;
            }
            return android.speech.tts.TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, android.speech.tts.TextToSpeechService.this.new SynthesisToFileOutputStreamSpeechItem(iBinder, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), bundle, str, charSequence, new android.os.ParcelFileDescriptor.AutoCloseOutputStream(android.os.ParcelFileDescriptor.adoptFd(parcelFileDescriptor.detachFd()))));
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int playAudio(android.os.IBinder iBinder, android.net.Uri uri, int i, android.os.Bundle bundle, java.lang.String str) {
            if (!checkNonNull(iBinder, uri, bundle)) {
                return -1;
            }
            return android.speech.tts.TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(i, android.speech.tts.TextToSpeechService.this.new AudioSpeechItem(iBinder, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), bundle, str, uri));
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int playSilence(android.os.IBinder iBinder, long j, int i, java.lang.String str) {
            if (!checkNonNull(iBinder)) {
                return -1;
            }
            return android.speech.tts.TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(i, android.speech.tts.TextToSpeechService.this.new SilenceSpeechItem(iBinder, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), str, j));
        }

        @Override // android.speech.tts.ITextToSpeechService
        public boolean isSpeaking() {
            return android.speech.tts.TextToSpeechService.this.mSynthHandler.isSpeaking() || android.speech.tts.TextToSpeechService.this.mAudioPlaybackHandler.isSpeaking();
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int stop(android.os.IBinder iBinder) {
            if (!checkNonNull(iBinder)) {
                return -1;
            }
            return android.speech.tts.TextToSpeechService.this.mSynthHandler.stopForApp(iBinder);
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.lang.String[] getLanguage() {
            return android.speech.tts.TextToSpeechService.this.onGetLanguage();
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.lang.String[] getClientDefaultLanguage() {
            return android.speech.tts.TextToSpeechService.this.getSettingsLocale();
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int isLanguageAvailable(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            if (!checkNonNull(str)) {
                return -1;
            }
            return android.speech.tts.TextToSpeechService.this.onIsLanguageAvailable(str, str2, str3);
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.lang.String[] getFeaturesForLanguage(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            java.util.Set<java.lang.String> onGetFeaturesForLanguage = android.speech.tts.TextToSpeechService.this.onGetFeaturesForLanguage(str, str2, str3);
            if (onGetFeaturesForLanguage != null) {
                java.lang.String[] strArr = new java.lang.String[onGetFeaturesForLanguage.size()];
                onGetFeaturesForLanguage.toArray(strArr);
                return strArr;
            }
            return new java.lang.String[0];
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int loadLanguage(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            if (!checkNonNull(str)) {
                return -1;
            }
            int onIsLanguageAvailable = android.speech.tts.TextToSpeechService.this.onIsLanguageAvailable(str, str2, str3);
            if (onIsLanguageAvailable == 0 || onIsLanguageAvailable == 1 || onIsLanguageAvailable == 2) {
                if (android.speech.tts.TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, android.speech.tts.TextToSpeechService.this.new LoadLanguageItem(iBinder, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), str, str2, str3)) != 0) {
                    return -1;
                }
            }
            return onIsLanguageAvailable;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.util.List<android.speech.tts.Voice> getVoices() {
            return android.speech.tts.TextToSpeechService.this.onGetVoices();
        }

        @Override // android.speech.tts.ITextToSpeechService
        public int loadVoice(android.os.IBinder iBinder, java.lang.String str) {
            if (!checkNonNull(str)) {
                return -1;
            }
            int onIsValidVoiceName = android.speech.tts.TextToSpeechService.this.onIsValidVoiceName(str);
            if (onIsValidVoiceName == 0) {
                if (android.speech.tts.TextToSpeechService.this.mSynthHandler.enqueueSpeechItem(1, android.speech.tts.TextToSpeechService.this.new LoadVoiceItem(iBinder, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), str)) != 0) {
                    return -1;
                }
            }
            return onIsValidVoiceName;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public java.lang.String getDefaultVoiceNameFor(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            if (!checkNonNull(str)) {
                return null;
            }
            int onIsLanguageAvailable = android.speech.tts.TextToSpeechService.this.onIsLanguageAvailable(str, str2, str3);
            if (onIsLanguageAvailable == 0 || onIsLanguageAvailable == 1 || onIsLanguageAvailable == 2) {
                return android.speech.tts.TextToSpeechService.this.onGetDefaultVoiceNameFor(str, str2, str3);
            }
            return null;
        }

        @Override // android.speech.tts.ITextToSpeechService
        public void setCallback(android.os.IBinder iBinder, android.speech.tts.ITextToSpeechCallback iTextToSpeechCallback) {
            if (!checkNonNull(iBinder)) {
                return;
            }
            android.speech.tts.TextToSpeechService.this.mCallbacks.setCallback(iBinder, iTextToSpeechCallback);
        }

        private java.lang.String intern(java.lang.String str) {
            return str.intern();
        }

        private boolean checkNonNull(java.lang.Object... objArr) {
            for (java.lang.Object obj : objArr) {
                if (obj == null) {
                    return false;
                }
            }
            return true;
        }
    };

    interface UtteranceProgressDispatcher {
        void dispatchOnAudioAvailable(byte[] bArr);

        void dispatchOnBeginSynthesis(int i, int i2, int i3);

        void dispatchOnError(int i);

        void dispatchOnRangeStart(int i, int i2, int i3);

        void dispatchOnStart();

        void dispatchOnStop();

        void dispatchOnSuccess();
    }

    protected abstract java.lang.String[] onGetLanguage();

    protected abstract int onIsLanguageAvailable(java.lang.String str, java.lang.String str2, java.lang.String str3);

    protected abstract int onLoadLanguage(java.lang.String str, java.lang.String str2, java.lang.String str3);

    protected abstract void onStop();

    protected abstract void onSynthesizeText(android.speech.tts.SynthesisRequest synthesisRequest, android.speech.tts.SynthesisCallback synthesisCallback);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        android.speech.tts.TextToSpeechService.SynthThread synthThread = new android.speech.tts.TextToSpeechService.SynthThread();
        synthThread.start();
        this.mSynthHandler = new android.speech.tts.TextToSpeechService.SynthHandler(synthThread.getLooper());
        this.mAudioPlaybackHandler = new android.speech.tts.AudioPlaybackHandler();
        this.mAudioPlaybackHandler.start();
        this.mEngineHelper = new android.speech.tts.TtsEngines(this);
        this.mCallbacks = new android.speech.tts.TextToSpeechService.CallbackMap();
        this.mPackageName = getApplicationInfo().packageName;
        java.lang.String[] settingsLocale = getSettingsLocale();
        onLoadLanguage(settingsLocale[0], settingsLocale[1], settingsLocale[2]);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mSynthHandler.quit();
        this.mAudioPlaybackHandler.quit();
        this.mCallbacks.kill();
        super.onDestroy();
    }

    protected java.util.Set<java.lang.String> onGetFeaturesForLanguage(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return new java.util.HashSet();
    }

    private int getExpectedLanguageAvailableStatus(java.util.Locale locale) {
        if (!locale.getVariant().isEmpty()) {
            return 2;
        }
        if (locale.getCountry().isEmpty()) {
            return 0;
        }
        return 1;
    }

    public java.util.List<android.speech.tts.Voice> onGetVoices() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Locale locale : java.util.Locale.getAvailableLocales()) {
            try {
                if (onIsLanguageAvailable(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant()) == getExpectedLanguageAvailableStatus(locale)) {
                    arrayList.add(new android.speech.tts.Voice(onGetDefaultVoiceNameFor(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant()), locale, 300, 300, false, onGetFeaturesForLanguage(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant())));
                }
            } catch (java.util.MissingResourceException e) {
            }
        }
        return arrayList;
    }

    public java.lang.String onGetDefaultVoiceNameFor(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.util.Locale locale;
        switch (onIsLanguageAvailable(str, str2, str3)) {
            case 0:
                locale = new java.util.Locale(str);
                break;
            case 1:
                locale = new java.util.Locale(str, str2);
                break;
            case 2:
                locale = new java.util.Locale(str, str2, str3);
                break;
            default:
                return null;
        }
        java.lang.String languageTag = android.speech.tts.TtsEngines.normalizeTTSLocale(locale).toLanguageTag();
        if (onIsValidVoiceName(languageTag) != 0) {
            return null;
        }
        return languageTag;
    }

    public int onLoadVoice(java.lang.String str) {
        java.util.Locale forLanguageTag = java.util.Locale.forLanguageTag(str);
        if (forLanguageTag == null) {
            return -1;
        }
        try {
            if (onIsLanguageAvailable(forLanguageTag.getISO3Language(), forLanguageTag.getISO3Country(), forLanguageTag.getVariant()) != getExpectedLanguageAvailableStatus(forLanguageTag)) {
                return -1;
            }
            onLoadLanguage(forLanguageTag.getISO3Language(), forLanguageTag.getISO3Country(), forLanguageTag.getVariant());
            return 0;
        } catch (java.util.MissingResourceException e) {
            return -1;
        }
    }

    public int onIsValidVoiceName(java.lang.String str) {
        java.util.Locale forLanguageTag = java.util.Locale.forLanguageTag(str);
        if (forLanguageTag == null) {
            return -1;
        }
        try {
            if (onIsLanguageAvailable(forLanguageTag.getISO3Language(), forLanguageTag.getISO3Country(), forLanguageTag.getVariant()) != getExpectedLanguageAvailableStatus(forLanguageTag)) {
                return -1;
            }
            return 0;
        } catch (java.util.MissingResourceException e) {
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDefaultSpeechRate() {
        return getSecureSettingInt(android.provider.Settings.Secure.TTS_DEFAULT_RATE, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDefaultPitch() {
        return getSecureSettingInt(android.provider.Settings.Secure.TTS_DEFAULT_PITCH, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String[] getSettingsLocale() {
        return android.speech.tts.TtsEngines.toOldLocaleStringFormat(this.mEngineHelper.getLocalePrefForEngine(this.mPackageName));
    }

    private int getSecureSettingInt(java.lang.String str, int i) {
        return android.provider.Settings.Secure.getInt(getContentResolver(), str, i);
    }

    private class SynthThread extends android.os.HandlerThread implements android.os.MessageQueue.IdleHandler {
        private boolean mFirstIdle;

        public SynthThread() {
            super(android.speech.tts.TextToSpeechService.SYNTH_THREAD_NAME, 0);
            this.mFirstIdle = true;
        }

        @Override // android.os.HandlerThread
        protected void onLooperPrepared() {
            getLooper().getQueue().addIdleHandler(this);
        }

        @Override // android.os.MessageQueue.IdleHandler
        public boolean queueIdle() {
            if (this.mFirstIdle) {
                this.mFirstIdle = false;
                return true;
            }
            broadcastTtsQueueProcessingCompleted();
            return true;
        }

        private void broadcastTtsQueueProcessingCompleted() {
            android.speech.tts.TextToSpeechService.this.sendBroadcast(new android.content.Intent(android.speech.tts.TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED));
        }
    }

    private class SynthHandler extends android.os.Handler {
        private android.speech.tts.TextToSpeechService.SpeechItem mCurrentSpeechItem;
        private int mFlushAll;
        private java.util.List<java.lang.Object> mFlushedObjects;

        public SynthHandler(android.os.Looper looper) {
            super(looper);
            this.mCurrentSpeechItem = null;
            this.mFlushedObjects = new java.util.ArrayList();
            this.mFlushAll = 0;
        }

        private void startFlushingSpeechItems(java.lang.Object obj) {
            synchronized (this.mFlushedObjects) {
                if (obj == null) {
                    this.mFlushAll++;
                } else {
                    this.mFlushedObjects.add(obj);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void endFlushingSpeechItems(java.lang.Object obj) {
            synchronized (this.mFlushedObjects) {
                if (obj == null) {
                    this.mFlushAll--;
                } else {
                    this.mFlushedObjects.remove(obj);
                }
            }
        }

        private boolean isFlushed(android.speech.tts.TextToSpeechService.SpeechItem speechItem) {
            boolean z;
            synchronized (this.mFlushedObjects) {
                z = this.mFlushAll > 0 || this.mFlushedObjects.contains(speechItem.getCallerIdentity());
            }
            return z;
        }

        private synchronized android.speech.tts.TextToSpeechService.SpeechItem getCurrentSpeechItem() {
            return this.mCurrentSpeechItem;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized boolean setCurrentSpeechItem(android.speech.tts.TextToSpeechService.SpeechItem speechItem) {
            if (speechItem != null) {
                if (isFlushed(speechItem)) {
                    return false;
                }
            }
            this.mCurrentSpeechItem = speechItem;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized android.speech.tts.TextToSpeechService.SpeechItem removeCurrentSpeechItem() {
            android.speech.tts.TextToSpeechService.SpeechItem speechItem;
            speechItem = this.mCurrentSpeechItem;
            this.mCurrentSpeechItem = null;
            return speechItem;
        }

        private synchronized android.speech.tts.TextToSpeechService.SpeechItem maybeRemoveCurrentSpeechItem(java.lang.Object obj) {
            if (this.mCurrentSpeechItem == null || this.mCurrentSpeechItem.getCallerIdentity() != obj) {
                return null;
            }
            android.speech.tts.TextToSpeechService.SpeechItem speechItem = this.mCurrentSpeechItem;
            this.mCurrentSpeechItem = null;
            return speechItem;
        }

        public boolean isSpeaking() {
            return getCurrentSpeechItem() != null;
        }

        public void quit() {
            getLooper().quit();
            android.speech.tts.TextToSpeechService.SpeechItem removeCurrentSpeechItem = removeCurrentSpeechItem();
            if (removeCurrentSpeechItem != null) {
                removeCurrentSpeechItem.stop();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int enqueueSpeechItem(int i, final android.speech.tts.TextToSpeechService.SpeechItem speechItem) {
            android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher;
            if (!(speechItem instanceof android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher)) {
                utteranceProgressDispatcher = null;
            } else {
                utteranceProgressDispatcher = (android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher) speechItem;
            }
            if (!speechItem.isValid()) {
                if (utteranceProgressDispatcher != null) {
                    utteranceProgressDispatcher.dispatchOnError(-8);
                }
                return -1;
            }
            if (i == 0) {
                stopForApp(speechItem.getCallerIdentity());
            } else if (i == 2) {
                stopAll();
            }
            android.os.Message obtain = android.os.Message.obtain(this, new java.lang.Runnable() { // from class: android.speech.tts.TextToSpeechService.SynthHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    if (android.speech.tts.TextToSpeechService.SynthHandler.this.setCurrentSpeechItem(speechItem)) {
                        speechItem.play();
                        android.speech.tts.TextToSpeechService.SynthHandler.this.removeCurrentSpeechItem();
                    } else {
                        speechItem.stop();
                    }
                }
            });
            obtain.obj = speechItem.getCallerIdentity();
            if (sendMessage(obtain)) {
                return 0;
            }
            android.util.Log.w(android.speech.tts.TextToSpeechService.TAG, "SynthThread has quit");
            if (utteranceProgressDispatcher != null) {
                utteranceProgressDispatcher.dispatchOnError(-4);
            }
            return -1;
        }

        public int stopForApp(final java.lang.Object obj) {
            if (obj == null) {
                return -1;
            }
            startFlushingSpeechItems(obj);
            android.speech.tts.TextToSpeechService.SpeechItem maybeRemoveCurrentSpeechItem = maybeRemoveCurrentSpeechItem(obj);
            if (maybeRemoveCurrentSpeechItem != null) {
                maybeRemoveCurrentSpeechItem.stop();
            }
            android.speech.tts.TextToSpeechService.this.mAudioPlaybackHandler.stopForApp(obj);
            sendMessage(android.os.Message.obtain(this, new java.lang.Runnable() { // from class: android.speech.tts.TextToSpeechService.SynthHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    android.speech.tts.TextToSpeechService.SynthHandler.this.endFlushingSpeechItems(obj);
                }
            }));
            return 0;
        }

        public int stopAll() {
            startFlushingSpeechItems(null);
            android.speech.tts.TextToSpeechService.SpeechItem removeCurrentSpeechItem = removeCurrentSpeechItem();
            if (removeCurrentSpeechItem != null) {
                removeCurrentSpeechItem.stop();
            }
            android.speech.tts.TextToSpeechService.this.mAudioPlaybackHandler.stop();
            sendMessage(android.os.Message.obtain(this, new java.lang.Runnable() { // from class: android.speech.tts.TextToSpeechService.SynthHandler.3
                @Override // java.lang.Runnable
                public void run() {
                    android.speech.tts.TextToSpeechService.SynthHandler.this.endFlushingSpeechItems(null);
                }
            }));
            return 0;
        }
    }

    static class AudioOutputParams {
        public final android.media.AudioAttributes mAudioAttributes;
        public final float mPan;
        public final int mSessionId;
        public final float mVolume;

        AudioOutputParams() {
            this.mSessionId = 0;
            this.mVolume = 1.0f;
            this.mPan = 0.0f;
            this.mAudioAttributes = null;
        }

        AudioOutputParams(int i, float f, float f2, android.media.AudioAttributes audioAttributes) {
            this.mSessionId = i;
            this.mVolume = f;
            this.mPan = f2;
            this.mAudioAttributes = audioAttributes;
        }

        static android.speech.tts.TextToSpeechService.AudioOutputParams createFromParamsBundle(android.os.Bundle bundle, boolean z) {
            int i;
            if (bundle == null) {
                return new android.speech.tts.TextToSpeechService.AudioOutputParams();
            }
            android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) bundle.getParcelable(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_AUDIO_ATTRIBUTES, android.media.AudioAttributes.class);
            if (audioAttributes == null) {
                android.media.AudioAttributes.Builder legacyStreamType = new android.media.AudioAttributes.Builder().setLegacyStreamType(bundle.getInt(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_STREAM, 3));
                if (z) {
                    i = 1;
                } else {
                    i = 4;
                }
                audioAttributes = legacyStreamType.setContentType(i).build();
            }
            return new android.speech.tts.TextToSpeechService.AudioOutputParams(bundle.getInt("sessionId", 0), bundle.getFloat(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f), bundle.getFloat(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_PAN, 0.0f), audioAttributes);
        }
    }

    private abstract class SpeechItem {
        private final java.lang.Object mCallerIdentity;
        private final int mCallerPid;
        private final int mCallerUid;
        private boolean mStarted = false;
        private boolean mStopped = false;

        public abstract boolean isValid();

        protected abstract void playImpl();

        protected abstract void stopImpl();

        public SpeechItem(java.lang.Object obj, int i, int i2) {
            this.mCallerIdentity = obj;
            this.mCallerUid = i;
            this.mCallerPid = i2;
        }

        public java.lang.Object getCallerIdentity() {
            return this.mCallerIdentity;
        }

        public int getCallerUid() {
            return this.mCallerUid;
        }

        public int getCallerPid() {
            return this.mCallerPid;
        }

        public void play() {
            synchronized (this) {
                if (this.mStarted) {
                    throw new java.lang.IllegalStateException("play() called twice");
                }
                this.mStarted = true;
            }
            playImpl();
        }

        public void stop() {
            synchronized (this) {
                if (this.mStopped) {
                    throw new java.lang.IllegalStateException("stop() called twice");
                }
                this.mStopped = true;
            }
            stopImpl();
        }

        protected synchronized boolean isStopped() {
            return this.mStopped;
        }

        protected synchronized boolean isStarted() {
            return this.mStarted;
        }
    }

    private abstract class UtteranceSpeechItem extends android.speech.tts.TextToSpeechService.SpeechItem implements android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher {
        public abstract java.lang.String getUtteranceId();

        public UtteranceSpeechItem(java.lang.Object obj, int i, int i2) {
            super(obj, i, i2);
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnSuccess() {
            java.lang.String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                android.speech.tts.TextToSpeechService.this.mCallbacks.dispatchOnSuccess(getCallerIdentity(), utteranceId);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnStop() {
            java.lang.String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                android.speech.tts.TextToSpeechService.this.mCallbacks.dispatchOnStop(getCallerIdentity(), utteranceId, isStarted());
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnStart() {
            java.lang.String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                android.speech.tts.TextToSpeechService.this.mCallbacks.dispatchOnStart(getCallerIdentity(), utteranceId);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnError(int i) {
            java.lang.String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                android.speech.tts.TextToSpeechService.this.mCallbacks.dispatchOnError(getCallerIdentity(), utteranceId, i);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnBeginSynthesis(int i, int i2, int i3) {
            java.lang.String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                android.speech.tts.TextToSpeechService.this.mCallbacks.dispatchOnBeginSynthesis(getCallerIdentity(), utteranceId, i, i2, i3);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnAudioAvailable(byte[] bArr) {
            java.lang.String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                android.speech.tts.TextToSpeechService.this.mCallbacks.dispatchOnAudioAvailable(getCallerIdentity(), utteranceId, bArr);
            }
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceProgressDispatcher
        public void dispatchOnRangeStart(int i, int i2, int i3) {
            java.lang.String utteranceId = getUtteranceId();
            if (utteranceId != null) {
                android.speech.tts.TextToSpeechService.this.mCallbacks.dispatchOnRangeStart(getCallerIdentity(), utteranceId, i, i2, i3);
            }
        }

        java.lang.String getStringParam(android.os.Bundle bundle, java.lang.String str, java.lang.String str2) {
            return bundle == null ? str2 : bundle.getString(str, str2);
        }

        int getIntParam(android.os.Bundle bundle, java.lang.String str, int i) {
            return bundle == null ? i : bundle.getInt(str, i);
        }

        float getFloatParam(android.os.Bundle bundle, java.lang.String str, float f) {
            return bundle == null ? f : bundle.getFloat(str, f);
        }
    }

    private abstract class UtteranceSpeechItemWithParams extends android.speech.tts.TextToSpeechService.UtteranceSpeechItem {
        protected final android.os.Bundle mParams;
        protected final java.lang.String mUtteranceId;

        UtteranceSpeechItemWithParams(java.lang.Object obj, int i, int i2, android.os.Bundle bundle, java.lang.String str) {
            super(obj, i, i2);
            this.mParams = bundle;
            this.mUtteranceId = str;
        }

        boolean hasLanguage() {
            return !android.text.TextUtils.isEmpty(getStringParam(this.mParams, "language", null));
        }

        int getSpeechRate() {
            return getIntParam(this.mParams, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_RATE, android.speech.tts.TextToSpeechService.this.getDefaultSpeechRate());
        }

        int getPitch() {
            return getIntParam(this.mParams, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_PITCH, android.speech.tts.TextToSpeechService.this.getDefaultPitch());
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceSpeechItem
        public java.lang.String getUtteranceId() {
            return this.mUtteranceId;
        }

        android.speech.tts.TextToSpeechService.AudioOutputParams getAudioParams() {
            return android.speech.tts.TextToSpeechService.AudioOutputParams.createFromParamsBundle(this.mParams, true);
        }
    }

    class SynthesisSpeechItem extends android.speech.tts.TextToSpeechService.UtteranceSpeechItemWithParams {
        private final int mCallerUid;
        private final java.lang.String[] mDefaultLocale;
        private final android.speech.tts.EventLogger mEventLogger;
        private android.speech.tts.AbstractSynthesisCallback mSynthesisCallback;
        private final android.speech.tts.SynthesisRequest mSynthesisRequest;
        private final java.lang.CharSequence mText;

        public SynthesisSpeechItem(java.lang.Object obj, int i, int i2, android.os.Bundle bundle, java.lang.String str, java.lang.CharSequence charSequence) {
            super(obj, i, i2, bundle, str);
            this.mText = charSequence;
            this.mCallerUid = i;
            this.mSynthesisRequest = new android.speech.tts.SynthesisRequest(this.mText, this.mParams);
            this.mDefaultLocale = android.speech.tts.TextToSpeechService.this.getSettingsLocale();
            setRequestParams(this.mSynthesisRequest);
            this.mEventLogger = new android.speech.tts.EventLogger(this.mSynthesisRequest, i, i2, android.speech.tts.TextToSpeechService.this.mPackageName);
        }

        public java.lang.CharSequence getText() {
            return this.mText;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            if (this.mText == null) {
                android.util.Log.e(android.speech.tts.TextToSpeechService.TAG, "null synthesis text");
                return false;
            }
            if (this.mText.length() > android.speech.tts.TextToSpeech.getMaxSpeechInputLength()) {
                android.util.Log.w(android.speech.tts.TextToSpeechService.TAG, "Text too long: " + this.mText.length() + " chars");
                return false;
            }
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            this.mEventLogger.onRequestProcessingStart();
            synchronized (this) {
                if (isStopped()) {
                    return;
                }
                this.mSynthesisCallback = createSynthesisCallback();
                android.speech.tts.AbstractSynthesisCallback abstractSynthesisCallback = this.mSynthesisCallback;
                android.speech.tts.TextToSpeechService.this.onSynthesizeText(this.mSynthesisRequest, abstractSynthesisCallback);
                if (abstractSynthesisCallback.hasStarted() && !abstractSynthesisCallback.hasFinished()) {
                    abstractSynthesisCallback.done();
                }
            }
        }

        protected android.speech.tts.AbstractSynthesisCallback createSynthesisCallback() {
            return new android.speech.tts.PlaybackSynthesisCallback(getAudioParams(), android.speech.tts.TextToSpeechService.this.mAudioPlaybackHandler, this, getCallerIdentity(), this.mEventLogger, false);
        }

        private void setRequestParams(android.speech.tts.SynthesisRequest synthesisRequest) {
            java.lang.String voiceName = getVoiceName();
            synthesisRequest.setLanguage(getLanguage(), getCountry(), getVariant());
            if (!android.text.TextUtils.isEmpty(voiceName)) {
                synthesisRequest.setVoiceName(getVoiceName());
            }
            synthesisRequest.setSpeechRate(getSpeechRate());
            synthesisRequest.setCallerUid(this.mCallerUid);
            synthesisRequest.setPitch(getPitch());
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
            android.speech.tts.AbstractSynthesisCallback abstractSynthesisCallback;
            synchronized (this) {
                abstractSynthesisCallback = this.mSynthesisCallback;
            }
            if (abstractSynthesisCallback != null) {
                abstractSynthesisCallback.stop();
                android.speech.tts.TextToSpeechService.this.onStop();
            } else {
                dispatchOnStop();
            }
        }

        private java.lang.String getCountry() {
            return !hasLanguage() ? this.mDefaultLocale[1] : getStringParam(this.mParams, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_COUNTRY, "");
        }

        private java.lang.String getVariant() {
            return !hasLanguage() ? this.mDefaultLocale[2] : getStringParam(this.mParams, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VARIANT, "");
        }

        public java.lang.String getLanguage() {
            return getStringParam(this.mParams, "language", this.mDefaultLocale[0]);
        }

        public java.lang.String getVoiceName() {
            return getStringParam(this.mParams, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOICE_NAME, "");
        }
    }

    private class SynthesisToFileOutputStreamSpeechItem extends android.speech.tts.TextToSpeechService.SynthesisSpeechItem {
        private final java.io.FileOutputStream mFileOutputStream;

        public SynthesisToFileOutputStreamSpeechItem(java.lang.Object obj, int i, int i2, android.os.Bundle bundle, java.lang.String str, java.lang.CharSequence charSequence, java.io.FileOutputStream fileOutputStream) {
            super(obj, i, i2, bundle, str, charSequence);
            this.mFileOutputStream = fileOutputStream;
        }

        @Override // android.speech.tts.TextToSpeechService.SynthesisSpeechItem
        protected android.speech.tts.AbstractSynthesisCallback createSynthesisCallback() {
            return new android.speech.tts.FileSynthesisCallback(this.mFileOutputStream.getChannel(), this, false);
        }

        @Override // android.speech.tts.TextToSpeechService.SynthesisSpeechItem, android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            super.playImpl();
            try {
                this.mFileOutputStream.close();
            } catch (java.io.IOException e) {
                android.util.Log.w(android.speech.tts.TextToSpeechService.TAG, "Failed to close output file", e);
            }
        }
    }

    private class AudioSpeechItem extends android.speech.tts.TextToSpeechService.UtteranceSpeechItemWithParams {
        private final android.speech.tts.AudioPlaybackQueueItem mItem;

        public AudioSpeechItem(java.lang.Object obj, int i, int i2, android.os.Bundle bundle, java.lang.String str, android.net.Uri uri) {
            super(obj, i, i2, bundle, str);
            this.mItem = new android.speech.tts.AudioPlaybackQueueItem(this, getCallerIdentity(), android.speech.tts.TextToSpeechService.this, uri, getAudioParams());
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            android.speech.tts.TextToSpeechService.this.mAudioPlaybackHandler.enqueue(this.mItem);
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceSpeechItemWithParams, android.speech.tts.TextToSpeechService.UtteranceSpeechItem
        public java.lang.String getUtteranceId() {
            return getStringParam(this.mParams, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceSpeechItemWithParams
        android.speech.tts.TextToSpeechService.AudioOutputParams getAudioParams() {
            return android.speech.tts.TextToSpeechService.AudioOutputParams.createFromParamsBundle(this.mParams, false);
        }
    }

    private class SilenceSpeechItem extends android.speech.tts.TextToSpeechService.UtteranceSpeechItem {
        private final long mDuration;
        private final java.lang.String mUtteranceId;

        public SilenceSpeechItem(java.lang.Object obj, int i, int i2, java.lang.String str, long j) {
            super(obj, i, i2);
            this.mUtteranceId = str;
            this.mDuration = j;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            android.speech.tts.TextToSpeechService.this.mAudioPlaybackHandler.enqueue(new android.speech.tts.SilencePlaybackQueueItem(this, getCallerIdentity(), this.mDuration));
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
        }

        @Override // android.speech.tts.TextToSpeechService.UtteranceSpeechItem
        public java.lang.String getUtteranceId() {
            return this.mUtteranceId;
        }
    }

    private class LoadLanguageItem extends android.speech.tts.TextToSpeechService.SpeechItem {
        private final java.lang.String mCountry;
        private final java.lang.String mLanguage;
        private final java.lang.String mVariant;

        public LoadLanguageItem(java.lang.Object obj, int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            super(obj, i, i2);
            this.mLanguage = str;
            this.mCountry = str2;
            this.mVariant = str3;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            android.speech.tts.TextToSpeechService.this.onLoadLanguage(this.mLanguage, this.mCountry, this.mVariant);
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
        }
    }

    private class LoadVoiceItem extends android.speech.tts.TextToSpeechService.SpeechItem {
        private final java.lang.String mVoiceName;

        public LoadVoiceItem(java.lang.Object obj, int i, int i2, java.lang.String str) {
            super(obj, i, i2);
            this.mVoiceName = str;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        public boolean isValid() {
            return true;
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void playImpl() {
            android.speech.tts.TextToSpeechService.this.onLoadVoice(this.mVoiceName);
        }

        @Override // android.speech.tts.TextToSpeechService.SpeechItem
        protected void stopImpl() {
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (android.speech.tts.TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE.equals(intent.getAction())) {
            android.os.Binder.allowBlocking(this.mBinder.asBinder());
            return this.mBinder;
        }
        return null;
    }

    private class CallbackMap extends android.os.RemoteCallbackList<android.speech.tts.ITextToSpeechCallback> {
        private final java.util.HashMap<android.os.IBinder, android.speech.tts.ITextToSpeechCallback> mCallerToCallback;

        private CallbackMap() {
            this.mCallerToCallback = new java.util.HashMap<>();
        }

        public void setCallback(android.os.IBinder iBinder, android.speech.tts.ITextToSpeechCallback iTextToSpeechCallback) {
            android.speech.tts.ITextToSpeechCallback remove;
            synchronized (this.mCallerToCallback) {
                if (iTextToSpeechCallback != null) {
                    register(iTextToSpeechCallback, iBinder);
                    remove = this.mCallerToCallback.put(iBinder, iTextToSpeechCallback);
                } else {
                    remove = this.mCallerToCallback.remove(iBinder);
                }
                if (remove != null && remove != iTextToSpeechCallback) {
                    unregister(remove);
                }
            }
        }

        public void dispatchOnStop(java.lang.Object obj, java.lang.String str, boolean z) {
            android.speech.tts.ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onStop(str, z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.speech.tts.TextToSpeechService.TAG, "Callback onStop failed: " + e);
            }
        }

        public void dispatchOnSuccess(java.lang.Object obj, java.lang.String str) {
            android.speech.tts.ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onSuccess(str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.speech.tts.TextToSpeechService.TAG, "Callback onDone failed: " + e);
            }
        }

        public void dispatchOnStart(java.lang.Object obj, java.lang.String str) {
            android.speech.tts.ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onStart(str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.speech.tts.TextToSpeechService.TAG, "Callback onStart failed: " + e);
            }
        }

        public void dispatchOnError(java.lang.Object obj, java.lang.String str, int i) {
            android.speech.tts.ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onError(str, i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.speech.tts.TextToSpeechService.TAG, "Callback onError failed: " + e);
            }
        }

        public void dispatchOnBeginSynthesis(java.lang.Object obj, java.lang.String str, int i, int i2, int i3) {
            android.speech.tts.ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onBeginSynthesis(str, i, i2, i3);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.speech.tts.TextToSpeechService.TAG, "Callback dispatchOnBeginSynthesis(String, int, int, int) failed: " + e);
            }
        }

        public void dispatchOnAudioAvailable(java.lang.Object obj, java.lang.String str, byte[] bArr) {
            android.speech.tts.ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onAudioAvailable(str, bArr);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.speech.tts.TextToSpeechService.TAG, "Callback dispatchOnAudioAvailable(String, byte[]) failed: " + e);
            }
        }

        public void dispatchOnRangeStart(java.lang.Object obj, java.lang.String str, int i, int i2, int i3) {
            android.speech.tts.ITextToSpeechCallback callbackFor = getCallbackFor(obj);
            if (callbackFor == null) {
                return;
            }
            try {
                callbackFor.onRangeStart(str, i, i2, i3);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.speech.tts.TextToSpeechService.TAG, "Callback dispatchOnRangeStart(String, int, int, int) failed: " + e);
            }
        }

        @Override // android.os.RemoteCallbackList
        public void onCallbackDied(android.speech.tts.ITextToSpeechCallback iTextToSpeechCallback, java.lang.Object obj) {
            android.os.IBinder iBinder = (android.os.IBinder) obj;
            synchronized (this.mCallerToCallback) {
                this.mCallerToCallback.remove(iBinder);
            }
            android.speech.tts.TextToSpeechService.this.mSynthHandler.stopForApp(iBinder);
        }

        @Override // android.os.RemoteCallbackList
        public void kill() {
            synchronized (this.mCallerToCallback) {
                this.mCallerToCallback.clear();
                super.kill();
            }
        }

        private android.speech.tts.ITextToSpeechCallback getCallbackFor(java.lang.Object obj) {
            android.speech.tts.ITextToSpeechCallback iTextToSpeechCallback;
            android.os.IBinder iBinder = (android.os.IBinder) obj;
            synchronized (this.mCallerToCallback) {
                iTextToSpeechCallback = this.mCallerToCallback.get(iBinder);
            }
            return iTextToSpeechCallback;
        }
    }
}
