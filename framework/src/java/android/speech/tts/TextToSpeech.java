package android.speech.tts;

/* loaded from: classes3.dex */
public class TextToSpeech {
    public static final java.lang.String ACTION_TTS_QUEUE_PROCESSING_COMPLETED = "android.speech.tts.TTS_QUEUE_PROCESSING_COMPLETED";
    private static final boolean DEBUG = false;
    public static final int ERROR = -1;
    public static final int ERROR_INVALID_REQUEST = -8;
    public static final int ERROR_NETWORK = -6;
    public static final int ERROR_NETWORK_TIMEOUT = -7;
    public static final int ERROR_NOT_INSTALLED_YET = -9;
    public static final int ERROR_OUTPUT = -5;
    public static final int ERROR_SERVICE = -4;
    public static final int ERROR_SYNTHESIS = -3;
    public static final int LANG_AVAILABLE = 0;
    public static final int LANG_COUNTRY_AVAILABLE = 1;
    public static final int LANG_COUNTRY_VAR_AVAILABLE = 2;
    public static final int LANG_MISSING_DATA = -1;
    public static final int LANG_NOT_SUPPORTED = -2;
    public static final int QUEUE_ADD = 1;
    static final int QUEUE_DESTROY = 2;
    public static final int QUEUE_FLUSH = 0;
    public static final int STOPPED = -2;
    public static final int SUCCESS = 0;
    private static final java.lang.String TAG = "TextToSpeech";
    private android.speech.tts.TextToSpeech.Connection mConnectingServiceConnection;
    private final android.content.Context mContext;
    private volatile java.lang.String mCurrentEngine;
    private final java.util.Map<java.lang.String, android.net.Uri> mEarcons;
    private final android.speech.tts.TtsEngines mEnginesHelper;
    private final java.util.concurrent.Executor mInitExecutor;
    private android.speech.tts.TextToSpeech.OnInitListener mInitListener;
    private final boolean mIsSystem;
    private final android.os.Bundle mParams;
    private java.lang.String mRequestedEngine;
    private android.speech.tts.TextToSpeech.Connection mServiceConnection;
    private final java.lang.Object mStartLock;
    private final boolean mUseFallback;
    private volatile android.speech.tts.UtteranceProgressListener mUtteranceProgressListener;
    private final java.util.Map<java.lang.CharSequence, android.net.Uri> mUtterances;

    /* JADX INFO: Access modifiers changed from: private */
    interface Action<R> {
        R run(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Error {
    }

    public interface OnInitListener {
        void onInit(int i);
    }

    @java.lang.Deprecated
    public interface OnUtteranceCompletedListener {
        void onUtteranceCompleted(java.lang.String str);
    }

    public class Engine {
        public static final java.lang.String ACTION_CHECK_TTS_DATA = "android.speech.tts.engine.CHECK_TTS_DATA";
        public static final java.lang.String ACTION_GET_SAMPLE_TEXT = "android.speech.tts.engine.GET_SAMPLE_TEXT";
        public static final java.lang.String ACTION_INSTALL_TTS_DATA = "android.speech.tts.engine.INSTALL_TTS_DATA";
        public static final java.lang.String ACTION_TTS_DATA_INSTALLED = "android.speech.tts.engine.TTS_DATA_INSTALLED";

        @java.lang.Deprecated
        public static final int CHECK_VOICE_DATA_BAD_DATA = -1;
        public static final int CHECK_VOICE_DATA_FAIL = 0;

        @java.lang.Deprecated
        public static final int CHECK_VOICE_DATA_MISSING_DATA = -2;

        @java.lang.Deprecated
        public static final int CHECK_VOICE_DATA_MISSING_VOLUME = -3;
        public static final int CHECK_VOICE_DATA_PASS = 1;

        @java.lang.Deprecated
        public static final java.lang.String DEFAULT_ENGINE = "com.svox.pico";
        public static final float DEFAULT_PAN = 0.0f;
        public static final int DEFAULT_PITCH = 100;
        public static final int DEFAULT_RATE = 100;
        public static final int DEFAULT_STREAM = 3;
        public static final float DEFAULT_VOLUME = 1.0f;
        public static final java.lang.String EXTRA_AVAILABLE_VOICES = "availableVoices";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_CHECK_VOICE_DATA_FOR = "checkVoiceDataFor";
        public static final java.lang.String EXTRA_SAMPLE_TEXT = "sampleText";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_TTS_DATA_INSTALLED = "dataInstalled";
        public static final java.lang.String EXTRA_UNAVAILABLE_VOICES = "unavailableVoices";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_VOICE_DATA_FILES = "dataFiles";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_VOICE_DATA_FILES_INFO = "dataFilesInfo";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_VOICE_DATA_ROOT_DIRECTORY = "dataRoot";
        public static final java.lang.String INTENT_ACTION_TTS_SERVICE = "android.intent.action.TTS_SERVICE";

        @java.lang.Deprecated
        public static final java.lang.String KEY_FEATURE_EMBEDDED_SYNTHESIS = "embeddedTts";
        public static final java.lang.String KEY_FEATURE_NETWORK_RETRIES_COUNT = "networkRetriesCount";

        @java.lang.Deprecated
        public static final java.lang.String KEY_FEATURE_NETWORK_SYNTHESIS = "networkTts";
        public static final java.lang.String KEY_FEATURE_NETWORK_TIMEOUT_MS = "networkTimeoutMs";
        public static final java.lang.String KEY_FEATURE_NOT_INSTALLED = "notInstalled";
        public static final java.lang.String KEY_PARAM_AUDIO_ATTRIBUTES = "audioAttributes";
        public static final java.lang.String KEY_PARAM_COUNTRY = "country";
        public static final java.lang.String KEY_PARAM_ENGINE = "engine";
        public static final java.lang.String KEY_PARAM_LANGUAGE = "language";
        public static final java.lang.String KEY_PARAM_PAN = "pan";
        public static final java.lang.String KEY_PARAM_PITCH = "pitch";
        public static final java.lang.String KEY_PARAM_RATE = "rate";
        public static final java.lang.String KEY_PARAM_SESSION_ID = "sessionId";
        public static final java.lang.String KEY_PARAM_STREAM = "streamType";
        public static final java.lang.String KEY_PARAM_UTTERANCE_ID = "utteranceId";
        public static final java.lang.String KEY_PARAM_VARIANT = "variant";
        public static final java.lang.String KEY_PARAM_VOICE_NAME = "voiceName";
        public static final java.lang.String KEY_PARAM_VOLUME = "volume";
        public static final java.lang.String SERVICE_META_DATA = "android.speech.tts";
        public static final int USE_DEFAULTS = 0;

        public Engine() {
        }
    }

    public TextToSpeech(android.content.Context context, android.speech.tts.TextToSpeech.OnInitListener onInitListener) {
        this(context, onInitListener, null);
    }

    public TextToSpeech(android.content.Context context, android.speech.tts.TextToSpeech.OnInitListener onInitListener, java.lang.String str) {
        this(context, onInitListener, str, null, true);
    }

    public TextToSpeech(android.content.Context context, android.speech.tts.TextToSpeech.OnInitListener onInitListener, java.lang.String str, java.lang.String str2, boolean z) {
        this(context, null, onInitListener, str, str2, z, true);
    }

    private TextToSpeech(android.content.Context context, java.util.concurrent.Executor executor, android.speech.tts.TextToSpeech.OnInitListener onInitListener, java.lang.String str, java.lang.String str2, boolean z, boolean z2) {
        this.mStartLock = new java.lang.Object();
        this.mParams = new android.os.Bundle();
        this.mCurrentEngine = null;
        this.mContext = context;
        this.mInitExecutor = executor;
        this.mInitListener = onInitListener;
        this.mRequestedEngine = str;
        this.mUseFallback = z;
        this.mEarcons = new java.util.HashMap();
        this.mUtterances = new java.util.HashMap();
        this.mUtteranceProgressListener = null;
        this.mEnginesHelper = new android.speech.tts.TtsEngines(this.mContext);
        this.mIsSystem = z2;
        addDeviceSpecificSessionIdToParams(this.mContext, this.mParams);
        initTts();
    }

    private static void addDeviceSpecificSessionIdToParams(android.content.Context context, android.os.Bundle bundle) {
        int deviceSpecificPlaybackSessionId = getDeviceSpecificPlaybackSessionId(context);
        if (deviceSpecificPlaybackSessionId != 0) {
            bundle.putInt("sessionId", deviceSpecificPlaybackSessionId);
        }
    }

    private static int getDeviceSpecificPlaybackSessionId(android.content.Context context) {
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager;
        int deviceId = context.getDeviceId();
        if (deviceId == 0 || (virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) context.getSystemService(android.companion.virtual.VirtualDeviceManager.class)) == null) {
            return 0;
        }
        return virtualDeviceManager.getAudioPlaybackSessionId(deviceId);
    }

    private <R> R runActionNoReconnect(android.speech.tts.TextToSpeech.Action<R> action, R r, java.lang.String str, boolean z) {
        return (R) runAction(action, r, str, false, z);
    }

    private <R> R runAction(android.speech.tts.TextToSpeech.Action<R> action, R r, java.lang.String str) {
        return (R) runAction(action, r, str, true, true);
    }

    private <R> R runAction(android.speech.tts.TextToSpeech.Action<R> action, R r, java.lang.String str, boolean z, boolean z2) {
        synchronized (this.mStartLock) {
            if (this.mServiceConnection == null) {
                android.util.Log.w(TAG, str + " failed: not bound to TTS engine");
                return r;
            }
            return (R) this.mServiceConnection.runAction(action, r, str, z, z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int initTts() {
        if (this.mRequestedEngine != null) {
            if (this.mEnginesHelper.isEngineInstalled(this.mRequestedEngine)) {
                if (connectToEngine(this.mRequestedEngine)) {
                    this.mCurrentEngine = this.mRequestedEngine;
                    return 0;
                }
                if (!this.mUseFallback) {
                    this.mCurrentEngine = null;
                    dispatchOnInit(-1);
                    return -1;
                }
            } else if (!this.mUseFallback) {
                android.util.Log.i(TAG, "Requested engine not installed: " + this.mRequestedEngine);
                this.mCurrentEngine = null;
                dispatchOnInit(-1);
                return -1;
            }
        }
        java.lang.String defaultEngine = getDefaultEngine();
        if (defaultEngine != null && !defaultEngine.equals(this.mRequestedEngine) && connectToEngine(defaultEngine)) {
            this.mCurrentEngine = defaultEngine;
            return 0;
        }
        java.lang.String highestRankedEngineName = this.mEnginesHelper.getHighestRankedEngineName();
        if (highestRankedEngineName != null && !highestRankedEngineName.equals(this.mRequestedEngine) && !highestRankedEngineName.equals(defaultEngine) && connectToEngine(highestRankedEngineName)) {
            this.mCurrentEngine = highestRankedEngineName;
            return 0;
        }
        this.mCurrentEngine = null;
        dispatchOnInit(-1);
        return -1;
    }

    private boolean connectToEngine(java.lang.String str) {
        android.speech.tts.TextToSpeech.Connection directConnection;
        byte b = 0;
        if (this.mIsSystem) {
            directConnection = new android.speech.tts.TextToSpeech.SystemConnection();
        } else {
            directConnection = new android.speech.tts.TextToSpeech.DirectConnection();
        }
        if (!directConnection.connect(str)) {
            android.util.Log.e(TAG, "Failed to bind to " + str);
            return false;
        }
        android.util.Log.i(TAG, "Sucessfully bound to " + str);
        this.mConnectingServiceConnection = directConnection;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnInit(final int i) {
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                android.speech.tts.TextToSpeech.this.lambda$dispatchOnInit$0(i);
            }
        };
        if (this.mInitExecutor != null) {
            this.mInitExecutor.execute(runnable);
        } else {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchOnInit$0(int i) {
        synchronized (this.mStartLock) {
            if (this.mInitListener != null) {
                this.mInitListener.onInit(i);
                this.mInitListener = null;
            }
        }
    }

    private android.os.IBinder getCallerIdentity() {
        return this.mServiceConnection.getCallerIdentity();
    }

    public void shutdown() {
        synchronized (this.mStartLock) {
            if (this.mConnectingServiceConnection != null) {
                this.mConnectingServiceConnection.disconnect();
                this.mConnectingServiceConnection = null;
            } else {
                runActionNoReconnect(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda12
                    @Override // android.speech.tts.TextToSpeech.Action
                    public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                        java.lang.Object lambda$shutdown$1;
                        lambda$shutdown$1 = android.speech.tts.TextToSpeech.this.lambda$shutdown$1(iTextToSpeechService);
                        return lambda$shutdown$1;
                    }
                }, null, "shutdown", false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Object lambda$shutdown$1(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        iTextToSpeechService.setCallback(getCallerIdentity(), null);
        iTextToSpeechService.stop(getCallerIdentity());
        this.mServiceConnection.disconnect();
        this.mServiceConnection = null;
        this.mCurrentEngine = null;
        return null;
    }

    public int addSpeech(java.lang.String str, java.lang.String str2, int i) {
        return addSpeech(str, makeResourceUri(str2, i));
    }

    public int addSpeech(java.lang.CharSequence charSequence, java.lang.String str, int i) {
        return addSpeech(charSequence, makeResourceUri(str, i));
    }

    public int addSpeech(java.lang.String str, java.lang.String str2) {
        return addSpeech(str, android.net.Uri.parse(str2));
    }

    public int addSpeech(java.lang.CharSequence charSequence, java.io.File file) {
        return addSpeech(charSequence, android.net.Uri.fromFile(file));
    }

    public int addSpeech(java.lang.CharSequence charSequence, android.net.Uri uri) {
        synchronized (this.mStartLock) {
            this.mUtterances.put(charSequence, uri);
        }
        return 0;
    }

    public int addEarcon(java.lang.String str, java.lang.String str2, int i) {
        return addEarcon(str, makeResourceUri(str2, i));
    }

    @java.lang.Deprecated
    public int addEarcon(java.lang.String str, java.lang.String str2) {
        return addEarcon(str, android.net.Uri.parse(str2));
    }

    public int addEarcon(java.lang.String str, java.io.File file) {
        return addEarcon(str, android.net.Uri.fromFile(file));
    }

    public int addEarcon(java.lang.String str, android.net.Uri uri) {
        synchronized (this.mStartLock) {
            this.mEarcons.put(str, uri);
        }
        return 0;
    }

    private android.net.Uri makeResourceUri(java.lang.String str, int i) {
        return new android.net.Uri.Builder().scheme(android.content.ContentResolver.SCHEME_ANDROID_RESOURCE).encodedAuthority(str).appendEncodedPath(java.lang.String.valueOf(i)).build();
    }

    public int speak(final java.lang.CharSequence charSequence, final int i, final android.os.Bundle bundle, final java.lang.String str) {
        return ((java.lang.Integer) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda1
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.lang.Integer lambda$speak$2;
                lambda$speak$2 = android.speech.tts.TextToSpeech.this.lambda$speak$2(charSequence, i, bundle, str, iTextToSpeechService);
                return lambda$speak$2;
            }
        }, -1, "speak")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$speak$2(java.lang.CharSequence charSequence, int i, android.os.Bundle bundle, java.lang.String str, android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        android.net.Uri uri = this.mUtterances.get(charSequence);
        if (uri != null) {
            return java.lang.Integer.valueOf(iTextToSpeechService.playAudio(getCallerIdentity(), uri, i, getParams(bundle), str));
        }
        return java.lang.Integer.valueOf(iTextToSpeechService.speak(getCallerIdentity(), charSequence, i, getParams(bundle), str));
    }

    @java.lang.Deprecated
    public int speak(java.lang.String str, int i, java.util.HashMap<java.lang.String, java.lang.String> hashMap) {
        return speak(str, i, convertParamsHashMaptoBundle(hashMap), hashMap == null ? null : hashMap.get(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID));
    }

    public int playEarcon(final java.lang.String str, final int i, final android.os.Bundle bundle, final java.lang.String str2) {
        return ((java.lang.Integer) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda10
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.lang.Integer lambda$playEarcon$3;
                lambda$playEarcon$3 = android.speech.tts.TextToSpeech.this.lambda$playEarcon$3(str, i, bundle, str2, iTextToSpeechService);
                return lambda$playEarcon$3;
            }
        }, -1, "playEarcon")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$playEarcon$3(java.lang.String str, int i, android.os.Bundle bundle, java.lang.String str2, android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        android.net.Uri uri = this.mEarcons.get(str);
        if (uri == null) {
            return -1;
        }
        return java.lang.Integer.valueOf(iTextToSpeechService.playAudio(getCallerIdentity(), uri, i, getParams(bundle), str2));
    }

    @java.lang.Deprecated
    public int playEarcon(java.lang.String str, int i, java.util.HashMap<java.lang.String, java.lang.String> hashMap) {
        return playEarcon(str, i, convertParamsHashMaptoBundle(hashMap), hashMap == null ? null : hashMap.get(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID));
    }

    public int playSilentUtterance(final long j, final int i, final java.lang.String str) {
        return ((java.lang.Integer) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda2
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.lang.Integer lambda$playSilentUtterance$4;
                lambda$playSilentUtterance$4 = android.speech.tts.TextToSpeech.this.lambda$playSilentUtterance$4(j, i, str, iTextToSpeechService);
                return lambda$playSilentUtterance$4;
            }
        }, -1, "playSilentUtterance")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$playSilentUtterance$4(long j, int i, java.lang.String str, android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        return java.lang.Integer.valueOf(iTextToSpeechService.playSilence(getCallerIdentity(), j, i, str));
    }

    @java.lang.Deprecated
    public int playSilence(long j, int i, java.util.HashMap<java.lang.String, java.lang.String> hashMap) {
        return playSilentUtterance(j, i, hashMap == null ? null : hashMap.get(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID));
    }

    @java.lang.Deprecated
    public java.util.Set<java.lang.String> getFeatures(final java.util.Locale locale) {
        return (java.util.Set) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda16
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                return android.speech.tts.TextToSpeech.lambda$getFeatures$5(locale, iTextToSpeechService);
            }
        }, null, "getFeatures");
    }

    static /* synthetic */ java.util.Set lambda$getFeatures$5(java.util.Locale locale, android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        try {
            java.lang.String[] featuresForLanguage = iTextToSpeechService.getFeaturesForLanguage(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant());
            if (featuresForLanguage == null) {
                return null;
            }
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.Collections.addAll(hashSet, featuresForLanguage);
            return hashSet;
        } catch (java.util.MissingResourceException e) {
            android.util.Log.w(TAG, "Couldn't retrieve 3 letter ISO 639-2/T language and/or ISO 3166 country code for locale: " + locale, e);
            return null;
        }
    }

    public boolean isSpeaking() {
        return ((java.lang.Boolean) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda17
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.lang.Boolean valueOf;
                valueOf = java.lang.Boolean.valueOf(iTextToSpeechService.isSpeaking());
                return valueOf;
            }
        }, false, "isSpeaking")).booleanValue();
    }

    public int stop() {
        return ((java.lang.Integer) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda7
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.lang.Integer lambda$stop$7;
                lambda$stop$7 = android.speech.tts.TextToSpeech.this.lambda$stop$7(iTextToSpeechService);
                return lambda$stop$7;
            }
        }, -1, "stop")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$stop$7(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        return java.lang.Integer.valueOf(iTextToSpeechService.stop(getCallerIdentity()));
    }

    public int setSpeechRate(float f) {
        int i;
        if (f > 0.0f && (i = (int) (f * 100.0f)) > 0) {
            synchronized (this.mStartLock) {
                this.mParams.putInt(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_RATE, i);
            }
            return 0;
        }
        return -1;
    }

    public int setPitch(float f) {
        int i;
        if (f > 0.0f && (i = (int) (f * 100.0f)) > 0) {
            synchronized (this.mStartLock) {
                this.mParams.putInt(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_PITCH, i);
            }
            return 0;
        }
        return -1;
    }

    public int setAudioAttributes(android.media.AudioAttributes audioAttributes) {
        if (audioAttributes != null) {
            synchronized (this.mStartLock) {
                this.mParams.putParcelable(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_AUDIO_ATTRIBUTES, audioAttributes);
            }
            return 0;
        }
        return -1;
    }

    public java.lang.String getCurrentEngine() {
        return this.mCurrentEngine;
    }

    @java.lang.Deprecated
    public java.util.Locale getDefaultLanguage() {
        return (java.util.Locale) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda15
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                return android.speech.tts.TextToSpeech.lambda$getDefaultLanguage$8(iTextToSpeechService);
            }
        }, null, "getDefaultLanguage");
    }

    static /* synthetic */ java.util.Locale lambda$getDefaultLanguage$8(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        java.lang.String[] clientDefaultLanguage = iTextToSpeechService.getClientDefaultLanguage();
        return new java.util.Locale(clientDefaultLanguage[0], clientDefaultLanguage[1], clientDefaultLanguage[2]);
    }

    public int setLanguage(final java.util.Locale locale) {
        return ((java.lang.Integer) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda8
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.lang.Integer lambda$setLanguage$9;
                lambda$setLanguage$9 = android.speech.tts.TextToSpeech.this.lambda$setLanguage$9(locale, iTextToSpeechService);
                return lambda$setLanguage$9;
            }
        }, -2, "setLanguage")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$setLanguage$9(java.util.Locale locale, android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        java.lang.String str;
        java.lang.String str2 = "";
        if (locale == null) {
            return -2;
        }
        try {
            java.lang.String iSO3Language = locale.getISO3Language();
            try {
                java.lang.String iSO3Country = locale.getISO3Country();
                java.lang.String variant = locale.getVariant();
                int isLanguageAvailable = iTextToSpeechService.isLanguageAvailable(iSO3Language, iSO3Country, variant);
                if (isLanguageAvailable >= 0) {
                    java.lang.String defaultVoiceNameFor = iTextToSpeechService.getDefaultVoiceNameFor(iSO3Language, iSO3Country, variant);
                    if (android.text.TextUtils.isEmpty(defaultVoiceNameFor)) {
                        android.util.Log.w(TAG, "Couldn't find the default voice for " + iSO3Language + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + iSO3Country + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + variant);
                        return -2;
                    }
                    if (iTextToSpeechService.loadVoice(getCallerIdentity(), defaultVoiceNameFor) == -1) {
                        android.util.Log.w(TAG, "The service claimed " + iSO3Language + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + iSO3Country + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + variant + " was available with voice name " + defaultVoiceNameFor + " but loadVoice returned ERROR");
                        return -2;
                    }
                    android.speech.tts.Voice voice = getVoice(iTextToSpeechService, defaultVoiceNameFor);
                    if (voice == null) {
                        android.util.Log.w(TAG, "getDefaultVoiceNameFor returned " + defaultVoiceNameFor + " for locale " + iSO3Language + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + iSO3Country + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + variant + " but getVoice returns null");
                        return -2;
                    }
                    try {
                        str = voice.getLocale().getISO3Language();
                    } catch (java.util.MissingResourceException e) {
                        android.util.Log.w(TAG, "Couldn't retrieve ISO 639-2/T language code for locale: " + voice.getLocale(), e);
                        str = "";
                    }
                    try {
                        str2 = voice.getLocale().getISO3Country();
                    } catch (java.util.MissingResourceException e2) {
                        android.util.Log.w(TAG, "Couldn't retrieve ISO 3166 country code for locale: " + voice.getLocale(), e2);
                    }
                    this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOICE_NAME, defaultVoiceNameFor);
                    this.mParams.putString("language", str);
                    this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_COUNTRY, str2);
                    this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VARIANT, voice.getLocale().getVariant());
                }
                return java.lang.Integer.valueOf(isLanguageAvailable);
            } catch (java.util.MissingResourceException e3) {
                android.util.Log.w(TAG, "Couldn't retrieve ISO 3166 country code for locale: " + locale, e3);
                return -2;
            }
        } catch (java.util.MissingResourceException e4) {
            android.util.Log.w(TAG, "Couldn't retrieve ISO 639-2/T language code for locale: " + locale, e4);
            return -2;
        }
    }

    @java.lang.Deprecated
    public java.util.Locale getLanguage() {
        return (java.util.Locale) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda6
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.util.Locale lambda$getLanguage$10;
                lambda$getLanguage$10 = android.speech.tts.TextToSpeech.this.lambda$getLanguage$10(iTextToSpeechService);
                return lambda$getLanguage$10;
            }
        }, null, "getLanguage");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.util.Locale lambda$getLanguage$10(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        return new java.util.Locale(this.mParams.getString("language", ""), this.mParams.getString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_COUNTRY, ""), this.mParams.getString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VARIANT, ""));
    }

    public java.util.Set<java.util.Locale> getAvailableLanguages() {
        return (java.util.Set) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda3
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                return android.speech.tts.TextToSpeech.lambda$getAvailableLanguages$11(iTextToSpeechService);
            }
        }, null, "getAvailableLanguages");
    }

    static /* synthetic */ java.util.HashSet lambda$getAvailableLanguages$11(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        java.util.List<android.speech.tts.Voice> voices = iTextToSpeechService.getVoices();
        if (voices == null) {
            return new java.util.HashSet();
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator<android.speech.tts.Voice> it = voices.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getLocale());
        }
        return hashSet;
    }

    public java.util.Set<android.speech.tts.Voice> getVoices() {
        return (java.util.Set) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda11
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                return android.speech.tts.TextToSpeech.lambda$getVoices$12(iTextToSpeechService);
            }
        }, null, "getVoices");
    }

    static /* synthetic */ java.util.HashSet lambda$getVoices$12(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        java.util.List<android.speech.tts.Voice> voices = iTextToSpeechService.getVoices();
        return voices != null ? new java.util.HashSet(voices) : new java.util.HashSet();
    }

    public int setVoice(final android.speech.tts.Voice voice) {
        return ((java.lang.Integer) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda14
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.lang.Integer lambda$setVoice$13;
                lambda$setVoice$13 = android.speech.tts.TextToSpeech.this.lambda$setVoice$13(voice, iTextToSpeechService);
                return lambda$setVoice$13;
            }
        }, -2, "setVoice")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$setVoice$13(android.speech.tts.Voice voice, android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        java.lang.String str;
        java.lang.String str2 = "";
        int loadVoice = iTextToSpeechService.loadVoice(getCallerIdentity(), voice.getName());
        if (loadVoice == 0) {
            this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOICE_NAME, voice.getName());
            try {
                str = voice.getLocale().getISO3Language();
            } catch (java.util.MissingResourceException e) {
                android.util.Log.w(TAG, "Couldn't retrieve ISO 639-2/T language code for locale: " + voice.getLocale(), e);
                str = "";
            }
            try {
                str2 = voice.getLocale().getISO3Country();
            } catch (java.util.MissingResourceException e2) {
                android.util.Log.w(TAG, "Couldn't retrieve ISO 3166 country code for locale: " + voice.getLocale(), e2);
            }
            this.mParams.putString("language", str);
            this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_COUNTRY, str2);
            this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VARIANT, voice.getLocale().getVariant());
        }
        return java.lang.Integer.valueOf(loadVoice);
    }

    public android.speech.tts.Voice getVoice() {
        return (android.speech.tts.Voice) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda0
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                android.speech.tts.Voice lambda$getVoice$14;
                lambda$getVoice$14 = android.speech.tts.TextToSpeech.this.lambda$getVoice$14(iTextToSpeechService);
                return lambda$getVoice$14;
            }
        }, null, "getVoice");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.speech.tts.Voice lambda$getVoice$14(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        java.lang.String string = this.mParams.getString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOICE_NAME, "");
        if (android.text.TextUtils.isEmpty(string)) {
            return null;
        }
        return getVoice(iTextToSpeechService, string);
    }

    private android.speech.tts.Voice getVoice(android.speech.tts.ITextToSpeechService iTextToSpeechService, java.lang.String str) throws android.os.RemoteException {
        java.util.List<android.speech.tts.Voice> voices = iTextToSpeechService.getVoices();
        if (voices == null) {
            android.util.Log.w(TAG, "getVoices returned null");
            return null;
        }
        for (android.speech.tts.Voice voice : voices) {
            if (voice.getName().equals(str)) {
                return voice;
            }
        }
        android.util.Log.w(TAG, "Could not find voice " + str + " in voice list");
        return null;
    }

    public android.speech.tts.Voice getDefaultVoice() {
        return (android.speech.tts.Voice) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda9
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                return android.speech.tts.TextToSpeech.lambda$getDefaultVoice$15(iTextToSpeechService);
            }
        }, null, "getDefaultVoice");
    }

    static /* synthetic */ android.speech.tts.Voice lambda$getDefaultVoice$15(android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        java.util.List<android.speech.tts.Voice> voices;
        java.lang.String[] clientDefaultLanguage = iTextToSpeechService.getClientDefaultLanguage();
        if (clientDefaultLanguage == null || clientDefaultLanguage.length == 0) {
            android.util.Log.e(TAG, "service.getClientDefaultLanguage() returned empty array");
            return null;
        }
        java.lang.String str = clientDefaultLanguage[0];
        java.lang.String str2 = clientDefaultLanguage.length > 1 ? clientDefaultLanguage[1] : "";
        java.lang.String str3 = clientDefaultLanguage.length > 2 ? clientDefaultLanguage[2] : "";
        if (iTextToSpeechService.isLanguageAvailable(str, str2, str3) < 0) {
            return null;
        }
        java.lang.String defaultVoiceNameFor = iTextToSpeechService.getDefaultVoiceNameFor(str, str2, str3);
        if (android.text.TextUtils.isEmpty(defaultVoiceNameFor) || (voices = iTextToSpeechService.getVoices()) == null) {
            return null;
        }
        for (android.speech.tts.Voice voice : voices) {
            if (voice.getName().equals(defaultVoiceNameFor)) {
                return voice;
            }
        }
        return null;
    }

    public int isLanguageAvailable(final java.util.Locale locale) {
        return ((java.lang.Integer) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda4
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                return android.speech.tts.TextToSpeech.lambda$isLanguageAvailable$16(locale, iTextToSpeechService);
            }
        }, -2, "isLanguageAvailable")).intValue();
    }

    static /* synthetic */ java.lang.Integer lambda$isLanguageAvailable$16(java.util.Locale locale, android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        try {
            try {
                return java.lang.Integer.valueOf(iTextToSpeechService.isLanguageAvailable(locale.getISO3Language(), locale.getISO3Country(), locale.getVariant()));
            } catch (java.util.MissingResourceException e) {
                android.util.Log.w(TAG, "Couldn't retrieve ISO 3166 country code for locale: " + locale, e);
                return -2;
            }
        } catch (java.util.MissingResourceException e2) {
            android.util.Log.w(TAG, "Couldn't retrieve ISO 639-2/T language code for locale: " + locale, e2);
            return -2;
        }
    }

    public int synthesizeToFile(final java.lang.CharSequence charSequence, final android.os.Bundle bundle, final android.os.ParcelFileDescriptor parcelFileDescriptor, final java.lang.String str) {
        return ((java.lang.Integer) runAction(new android.speech.tts.TextToSpeech.Action() { // from class: android.speech.tts.TextToSpeech$$ExternalSyntheticLambda5
            @Override // android.speech.tts.TextToSpeech.Action
            public final java.lang.Object run(android.speech.tts.ITextToSpeechService iTextToSpeechService) {
                java.lang.Integer lambda$synthesizeToFile$17;
                lambda$synthesizeToFile$17 = android.speech.tts.TextToSpeech.this.lambda$synthesizeToFile$17(charSequence, parcelFileDescriptor, bundle, str, iTextToSpeechService);
                return lambda$synthesizeToFile$17;
            }
        }, -1, "synthesizeToFile")).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$synthesizeToFile$17(java.lang.CharSequence charSequence, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Bundle bundle, java.lang.String str, android.speech.tts.ITextToSpeechService iTextToSpeechService) throws android.os.RemoteException {
        return java.lang.Integer.valueOf(iTextToSpeechService.synthesizeToFileDescriptor(getCallerIdentity(), charSequence, parcelFileDescriptor, getParams(bundle), str));
    }

    public int synthesizeToFile(java.lang.CharSequence charSequence, android.os.Bundle bundle, java.io.File file, java.lang.String str) {
        if (file.exists() && !file.canWrite()) {
            android.util.Log.e(TAG, "Can't write to " + file);
            return -1;
        }
        try {
            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, android.media.audio.Enums.AUDIO_FORMAT_MPEGH);
            try {
                int synthesizeToFile = synthesizeToFile(charSequence, bundle, open, str);
                open.close();
                if (open != null) {
                    open.close();
                }
                return synthesizeToFile;
            } finally {
            }
        } catch (java.io.FileNotFoundException e) {
            android.util.Log.e(TAG, "Opening file " + file + " failed", e);
            return -1;
        } catch (java.io.IOException e2) {
            android.util.Log.e(TAG, "Closing file " + file + " failed", e2);
            return -1;
        }
    }

    @java.lang.Deprecated
    public int synthesizeToFile(java.lang.String str, java.util.HashMap<java.lang.String, java.lang.String> hashMap, java.lang.String str2) {
        return synthesizeToFile(str, convertParamsHashMaptoBundle(hashMap), new java.io.File(str2), hashMap.get(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID));
    }

    private android.os.Bundle convertParamsHashMaptoBundle(java.util.HashMap<java.lang.String, java.lang.String> hashMap) {
        if (hashMap != null && !hashMap.isEmpty()) {
            android.os.Bundle bundle = new android.os.Bundle();
            copyIntParam(bundle, hashMap, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_STREAM);
            copyIntParam(bundle, hashMap, "sessionId");
            copyStringParam(bundle, hashMap, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
            copyFloatParam(bundle, hashMap, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOLUME);
            copyFloatParam(bundle, hashMap, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_PAN);
            copyStringParam(bundle, hashMap, android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS);
            copyStringParam(bundle, hashMap, android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_EMBEDDED_SYNTHESIS);
            copyIntParam(bundle, hashMap, android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_NETWORK_TIMEOUT_MS);
            copyIntParam(bundle, hashMap, android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_NETWORK_RETRIES_COUNT);
            if (!android.text.TextUtils.isEmpty(this.mCurrentEngine)) {
                for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : hashMap.entrySet()) {
                    java.lang.String key = entry.getKey();
                    if (key != null && key.startsWith(this.mCurrentEngine)) {
                        bundle.putString(key, entry.getValue());
                    }
                }
            }
            return bundle;
        }
        return null;
    }

    private android.os.Bundle getParams(android.os.Bundle bundle) {
        if (bundle != null && !bundle.isEmpty()) {
            android.os.Bundle bundle2 = new android.os.Bundle(this.mParams);
            bundle2.putAll(bundle);
            verifyIntegerBundleParam(bundle2, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_STREAM);
            verifyIntegerBundleParam(bundle2, "sessionId");
            verifyStringBundleParam(bundle2, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID);
            verifyFloatBundleParam(bundle2, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOLUME);
            verifyFloatBundleParam(bundle2, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_PAN);
            verifyBooleanBundleParam(bundle2, android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS);
            verifyBooleanBundleParam(bundle2, android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_EMBEDDED_SYNTHESIS);
            verifyIntegerBundleParam(bundle2, android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_NETWORK_TIMEOUT_MS);
            verifyIntegerBundleParam(bundle2, android.speech.tts.TextToSpeech.Engine.KEY_FEATURE_NETWORK_RETRIES_COUNT);
            return bundle2;
        }
        return this.mParams;
    }

    private static boolean verifyIntegerBundleParam(android.os.Bundle bundle, java.lang.String str) {
        if (bundle.containsKey(str) && !(bundle.get(str) instanceof java.lang.Integer) && !(bundle.get(str) instanceof java.lang.Long)) {
            bundle.remove(str);
            android.util.Log.w(TAG, "Synthesis request paramter " + str + " containst value  with invalid type. Should be an Integer or a Long");
            return false;
        }
        return true;
    }

    private static boolean verifyStringBundleParam(android.os.Bundle bundle, java.lang.String str) {
        if (bundle.containsKey(str) && !(bundle.get(str) instanceof java.lang.String)) {
            bundle.remove(str);
            android.util.Log.w(TAG, "Synthesis request paramter " + str + " containst value  with invalid type. Should be a String");
            return false;
        }
        return true;
    }

    private static boolean verifyBooleanBundleParam(android.os.Bundle bundle, java.lang.String str) {
        if (bundle.containsKey(str) && !(bundle.get(str) instanceof java.lang.Boolean) && !(bundle.get(str) instanceof java.lang.String)) {
            bundle.remove(str);
            android.util.Log.w(TAG, "Synthesis request paramter " + str + " containst value  with invalid type. Should be a Boolean or String");
            return false;
        }
        return true;
    }

    private static boolean verifyFloatBundleParam(android.os.Bundle bundle, java.lang.String str) {
        if (bundle.containsKey(str) && !(bundle.get(str) instanceof java.lang.Float) && !(bundle.get(str) instanceof java.lang.Double)) {
            bundle.remove(str);
            android.util.Log.w(TAG, "Synthesis request paramter " + str + " containst value  with invalid type. Should be a Float or a Double");
            return false;
        }
        return true;
    }

    private void copyStringParam(android.os.Bundle bundle, java.util.HashMap<java.lang.String, java.lang.String> hashMap, java.lang.String str) {
        java.lang.String str2 = hashMap.get(str);
        if (str2 != null) {
            bundle.putString(str, str2);
        }
    }

    private void copyIntParam(android.os.Bundle bundle, java.util.HashMap<java.lang.String, java.lang.String> hashMap, java.lang.String str) {
        java.lang.String str2 = hashMap.get(str);
        if (!android.text.TextUtils.isEmpty(str2)) {
            try {
                bundle.putInt(str, java.lang.Integer.parseInt(str2));
            } catch (java.lang.NumberFormatException e) {
            }
        }
    }

    private void copyFloatParam(android.os.Bundle bundle, java.util.HashMap<java.lang.String, java.lang.String> hashMap, java.lang.String str) {
        java.lang.String str2 = hashMap.get(str);
        if (!android.text.TextUtils.isEmpty(str2)) {
            try {
                bundle.putFloat(str, java.lang.Float.parseFloat(str2));
            } catch (java.lang.NumberFormatException e) {
            }
        }
    }

    @java.lang.Deprecated
    public int setOnUtteranceCompletedListener(android.speech.tts.TextToSpeech.OnUtteranceCompletedListener onUtteranceCompletedListener) {
        this.mUtteranceProgressListener = android.speech.tts.UtteranceProgressListener.from(onUtteranceCompletedListener);
        return 0;
    }

    public int setOnUtteranceProgressListener(android.speech.tts.UtteranceProgressListener utteranceProgressListener) {
        this.mUtteranceProgressListener = utteranceProgressListener;
        return 0;
    }

    @java.lang.Deprecated
    public int setEngineByPackageName(java.lang.String str) {
        this.mRequestedEngine = str;
        return initTts();
    }

    public java.lang.String getDefaultEngine() {
        return this.mEnginesHelper.getDefaultEngine();
    }

    @java.lang.Deprecated
    public boolean areDefaultsEnforced() {
        return false;
    }

    public java.util.List<android.speech.tts.TextToSpeech.EngineInfo> getEngines() {
        return this.mEnginesHelper.getEngines();
    }

    private abstract class Connection implements android.content.ServiceConnection {
        private final android.speech.tts.ITextToSpeechCallback.Stub mCallback;
        private boolean mEstablished;
        private android.speech.tts.TextToSpeech.Connection.SetupConnectionAsyncTask mOnSetupConnectionAsyncTask;
        private android.speech.tts.ITextToSpeechService mService;

        abstract boolean connect(java.lang.String str);

        abstract void disconnect();

        private Connection() {
            this.mCallback = new android.speech.tts.ITextToSpeechCallback.Stub() { // from class: android.speech.tts.TextToSpeech.Connection.1
                @Override // android.speech.tts.ITextToSpeechCallback
                public void onStop(java.lang.String str, boolean z) throws android.os.RemoteException {
                    android.speech.tts.UtteranceProgressListener utteranceProgressListener = android.speech.tts.TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onStop(str, z);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onSuccess(java.lang.String str) {
                    android.speech.tts.UtteranceProgressListener utteranceProgressListener = android.speech.tts.TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onDone(str);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onError(java.lang.String str, int i) {
                    android.speech.tts.UtteranceProgressListener utteranceProgressListener = android.speech.tts.TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onError(str, i);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onStart(java.lang.String str) {
                    android.speech.tts.UtteranceProgressListener utteranceProgressListener = android.speech.tts.TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onStart(str);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onBeginSynthesis(java.lang.String str, int i, int i2, int i3) {
                    android.speech.tts.UtteranceProgressListener utteranceProgressListener = android.speech.tts.TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onBeginSynthesis(str, i, i2, i3);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onAudioAvailable(java.lang.String str, byte[] bArr) {
                    android.speech.tts.UtteranceProgressListener utteranceProgressListener = android.speech.tts.TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onAudioAvailable(str, bArr);
                    }
                }

                @Override // android.speech.tts.ITextToSpeechCallback
                public void onRangeStart(java.lang.String str, int i, int i2, int i3) {
                    android.speech.tts.UtteranceProgressListener utteranceProgressListener = android.speech.tts.TextToSpeech.this.mUtteranceProgressListener;
                    if (utteranceProgressListener != null) {
                        utteranceProgressListener.onRangeStart(str, i, i2, i3);
                    }
                }
            };
        }

        private class SetupConnectionAsyncTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Integer> {
            private SetupConnectionAsyncTask() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public java.lang.Integer doInBackground(java.lang.Void... voidArr) {
                synchronized (android.speech.tts.TextToSpeech.this.mStartLock) {
                    if (isCancelled()) {
                        return null;
                    }
                    try {
                        android.speech.tts.TextToSpeech.Connection.this.mService.setCallback(android.speech.tts.TextToSpeech.Connection.this.getCallerIdentity(), android.speech.tts.TextToSpeech.Connection.this.mCallback);
                        if (android.speech.tts.TextToSpeech.this.mParams.getString("language") == null) {
                            java.lang.String[] clientDefaultLanguage = android.speech.tts.TextToSpeech.Connection.this.mService.getClientDefaultLanguage();
                            android.speech.tts.TextToSpeech.this.mParams.putString("language", clientDefaultLanguage[0]);
                            android.speech.tts.TextToSpeech.this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_COUNTRY, clientDefaultLanguage[1]);
                            android.speech.tts.TextToSpeech.this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VARIANT, clientDefaultLanguage[2]);
                            android.speech.tts.TextToSpeech.this.mParams.putString(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_VOICE_NAME, android.speech.tts.TextToSpeech.Connection.this.mService.getDefaultVoiceNameFor(clientDefaultLanguage[0], clientDefaultLanguage[1], clientDefaultLanguage[2]));
                        }
                        android.util.Log.i(android.speech.tts.TextToSpeech.TAG, "Setting up the connection to TTS engine...");
                        return 0;
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.speech.tts.TextToSpeech.TAG, "Error connecting to service, setCallback() failed");
                        return -1;
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(java.lang.Integer num) {
                synchronized (android.speech.tts.TextToSpeech.this.mStartLock) {
                    if (android.speech.tts.TextToSpeech.Connection.this.mOnSetupConnectionAsyncTask == this) {
                        android.speech.tts.TextToSpeech.Connection.this.mOnSetupConnectionAsyncTask = null;
                    }
                    android.speech.tts.TextToSpeech.Connection.this.mEstablished = true;
                    android.speech.tts.TextToSpeech.this.dispatchOnInit(num.intValue());
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (android.speech.tts.TextToSpeech.this.mStartLock) {
                android.speech.tts.TextToSpeech.this.mConnectingServiceConnection = null;
                android.util.Log.i(android.speech.tts.TextToSpeech.TAG, "Connected to TTS engine");
                if (this.mOnSetupConnectionAsyncTask != null) {
                    this.mOnSetupConnectionAsyncTask.cancel(false);
                }
                this.mService = android.speech.tts.ITextToSpeechService.Stub.asInterface(iBinder);
                android.speech.tts.TextToSpeech.this.mServiceConnection = this;
                this.mEstablished = false;
                this.mOnSetupConnectionAsyncTask = new android.speech.tts.TextToSpeech.Connection.SetupConnectionAsyncTask();
                this.mOnSetupConnectionAsyncTask.execute(new java.lang.Void[0]);
            }
        }

        public android.os.IBinder getCallerIdentity() {
            return this.mCallback;
        }

        protected boolean clearServiceConnection() {
            boolean z;
            synchronized (android.speech.tts.TextToSpeech.this.mStartLock) {
                z = false;
                if (this.mOnSetupConnectionAsyncTask != null) {
                    z = this.mOnSetupConnectionAsyncTask.cancel(false);
                    this.mOnSetupConnectionAsyncTask = null;
                }
                this.mService = null;
                if (android.speech.tts.TextToSpeech.this.mServiceConnection == this) {
                    android.speech.tts.TextToSpeech.this.mServiceConnection = null;
                }
            }
            return z;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.util.Log.i(android.speech.tts.TextToSpeech.TAG, "Disconnected from TTS engine");
            if (clearServiceConnection()) {
                android.speech.tts.TextToSpeech.this.dispatchOnInit(-1);
            }
        }

        public boolean isEstablished() {
            return this.mService != null && this.mEstablished;
        }

        public <R> R runAction(android.speech.tts.TextToSpeech.Action<R> action, R r, java.lang.String str, boolean z, boolean z2) {
            synchronized (android.speech.tts.TextToSpeech.this.mStartLock) {
                try {
                    try {
                        if (this.mService == null) {
                            android.util.Log.w(android.speech.tts.TextToSpeech.TAG, str + " failed: not connected to TTS engine");
                            return r;
                        }
                        if (z2 && !isEstablished()) {
                            android.util.Log.w(android.speech.tts.TextToSpeech.TAG, str + " failed: TTS engine connection not fully set up");
                            return r;
                        }
                        return action.run(this.mService);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.speech.tts.TextToSpeech.TAG, str + " failed", e);
                        if (z) {
                            disconnect();
                            android.speech.tts.TextToSpeech.this.initTts();
                        }
                        return r;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private class DirectConnection extends android.speech.tts.TextToSpeech.Connection {
        private DirectConnection() {
            super();
        }

        @Override // android.speech.tts.TextToSpeech.Connection
        boolean connect(java.lang.String str) {
            android.content.Intent intent = new android.content.Intent(android.speech.tts.TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE);
            intent.setPackage(str);
            return android.speech.tts.TextToSpeech.this.mContext.bindService(intent, this, 1);
        }

        @Override // android.speech.tts.TextToSpeech.Connection
        void disconnect() {
            android.speech.tts.TextToSpeech.this.mContext.unbindService(this);
            clearServiceConnection();
        }
    }

    private class SystemConnection extends android.speech.tts.TextToSpeech.Connection {
        private volatile android.speech.tts.ITextToSpeechSession mSession;

        private SystemConnection() {
            super();
        }

        @Override // android.speech.tts.TextToSpeech.Connection
        boolean connect(java.lang.String str) {
            android.speech.tts.ITextToSpeechManager asInterface = android.speech.tts.ITextToSpeechManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.TEXT_TO_SPEECH_MANAGER_SERVICE));
            if (asInterface == null) {
                android.util.Log.e(android.speech.tts.TextToSpeech.TAG, "System service is not available!");
                return false;
            }
            try {
                asInterface.createSession(str, new android.speech.tts.ITextToSpeechSessionCallback.Stub() { // from class: android.speech.tts.TextToSpeech.SystemConnection.1
                    @Override // android.speech.tts.ITextToSpeechSessionCallback
                    public void onConnected(android.speech.tts.ITextToSpeechSession iTextToSpeechSession, android.os.IBinder iBinder) {
                        android.speech.tts.TextToSpeech.SystemConnection.this.mSession = iTextToSpeechSession;
                        android.speech.tts.TextToSpeech.SystemConnection.this.onServiceConnected(null, iBinder);
                    }

                    @Override // android.speech.tts.ITextToSpeechSessionCallback
                    public void onDisconnected() {
                        android.speech.tts.TextToSpeech.SystemConnection.this.onServiceDisconnected(null);
                        android.speech.tts.TextToSpeech.SystemConnection.this.mSession = null;
                    }

                    @Override // android.speech.tts.ITextToSpeechSessionCallback
                    public void onError(java.lang.String str2) {
                        android.util.Log.w(android.speech.tts.TextToSpeech.TAG, "System TTS connection error: " + str2);
                        android.speech.tts.TextToSpeech.this.dispatchOnInit(-1);
                    }
                });
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.speech.tts.TextToSpeech.TAG, "Error communicating with the System Server: ", e);
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.speech.tts.TextToSpeech.Connection
        void disconnect() {
            android.speech.tts.ITextToSpeechSession iTextToSpeechSession = this.mSession;
            if (iTextToSpeechSession != null) {
                try {
                    iTextToSpeechSession.disconnect();
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(android.speech.tts.TextToSpeech.TAG, "Error disconnecting session", e);
                }
                clearServiceConnection();
            }
        }
    }

    public static class EngineInfo {
        public int icon;
        public java.lang.String label;
        public java.lang.String name;
        public int priority;
        public boolean system;

        public java.lang.String toString() {
            return "EngineInfo{name=" + this.name + "}";
        }
    }

    public static int getMaxSpeechInputLength() {
        return 4000;
    }
}
