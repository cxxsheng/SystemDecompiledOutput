package android.speech;

/* loaded from: classes3.dex */
public class RecognizerIntent {
    public static final java.lang.String ACTION_GET_LANGUAGE_DETAILS = "android.speech.action.GET_LANGUAGE_DETAILS";
    public static final java.lang.String ACTION_RECOGNIZE_SPEECH = "android.speech.action.RECOGNIZE_SPEECH";
    public static final java.lang.String ACTION_VOICE_SEARCH_HANDS_FREE = "android.speech.action.VOICE_SEARCH_HANDS_FREE";
    public static final java.lang.String ACTION_WEB_SEARCH = "android.speech.action.WEB_SEARCH";
    public static final java.lang.String DETAILS_META_DATA = "android.speech.DETAILS";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_AUDIO_INJECT_SOURCE = "android.speech.extra.AUDIO_INJECT_SOURCE";
    public static final java.lang.String EXTRA_AUDIO_SOURCE = "android.speech.extra.AUDIO_SOURCE";
    public static final java.lang.String EXTRA_AUDIO_SOURCE_CHANNEL_COUNT = "android.speech.extra.AUDIO_SOURCE_CHANNEL_COUNT";
    public static final java.lang.String EXTRA_AUDIO_SOURCE_ENCODING = "android.speech.extra.AUDIO_SOURCE_ENCODING";
    public static final java.lang.String EXTRA_AUDIO_SOURCE_SAMPLING_RATE = "android.speech.extra.AUDIO_SOURCE_SAMPLING_RATE";
    public static final java.lang.String EXTRA_BIASING_STRINGS = "android.speech.extra.BIASING_STRINGS";
    public static final java.lang.String EXTRA_CALLING_PACKAGE = "calling_package";
    public static final java.lang.String EXTRA_CONFIDENCE_SCORES = "android.speech.extra.CONFIDENCE_SCORES";
    public static final java.lang.String EXTRA_ENABLE_BIASING_DEVICE_CONTEXT = "android.speech.extra.ENABLE_BIASING_DEVICE_CONTEXT";
    public static final java.lang.String EXTRA_ENABLE_FORMATTING = "android.speech.extra.ENABLE_FORMATTING";
    public static final java.lang.String EXTRA_ENABLE_LANGUAGE_DETECTION = "android.speech.extra.ENABLE_LANGUAGE_DETECTION";
    public static final java.lang.String EXTRA_ENABLE_LANGUAGE_SWITCH = "android.speech.extra.ENABLE_LANGUAGE_SWITCH";
    public static final java.lang.String EXTRA_HIDE_PARTIAL_TRAILING_PUNCTUATION = "android.speech.extra.HIDE_PARTIAL_TRAILING_PUNCTUATION";
    public static final java.lang.String EXTRA_LANGUAGE = "android.speech.extra.LANGUAGE";
    public static final java.lang.String EXTRA_LANGUAGE_DETECTION_ALLOWED_LANGUAGES = "android.speech.extra.LANGUAGE_DETECTION_ALLOWED_LANGUAGES";
    public static final java.lang.String EXTRA_LANGUAGE_MODEL = "android.speech.extra.LANGUAGE_MODEL";
    public static final java.lang.String EXTRA_LANGUAGE_PREFERENCE = "android.speech.extra.LANGUAGE_PREFERENCE";
    public static final java.lang.String EXTRA_LANGUAGE_SWITCH_ALLOWED_LANGUAGES = "android.speech.extra.LANGUAGE_SWITCH_ALLOWED_LANGUAGES";
    public static final java.lang.String EXTRA_LANGUAGE_SWITCH_INITIAL_ACTIVE_DURATION_TIME_MILLIS = "android.speech.extra.LANGUAGE_SWITCH_INITIAL_ACTIVE_DURATION_TIME_MILLIS";
    public static final java.lang.String EXTRA_LANGUAGE_SWITCH_MAX_SWITCHES = "android.speech.extra.LANGUAGE_SWITCH_MAX_SWITCHES";
    public static final java.lang.String EXTRA_MASK_OFFENSIVE_WORDS = "android.speech.extra.MASK_OFFENSIVE_WORDS";
    public static final java.lang.String EXTRA_MAX_RESULTS = "android.speech.extra.MAX_RESULTS";
    public static final java.lang.String EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE = "android.speech.extra.ONLY_RETURN_LANGUAGE_PREFERENCE";
    public static final java.lang.String EXTRA_ORIGIN = "android.speech.extra.ORIGIN";
    public static final java.lang.String EXTRA_PARTIAL_RESULTS = "android.speech.extra.PARTIAL_RESULTS";
    public static final java.lang.String EXTRA_PREFER_OFFLINE = "android.speech.extra.PREFER_OFFLINE";
    public static final java.lang.String EXTRA_PROMPT = "android.speech.extra.PROMPT";
    public static final java.lang.String EXTRA_REQUEST_WORD_CONFIDENCE = "android.speech.extra.REQUEST_WORD_CONFIDENCE";
    public static final java.lang.String EXTRA_REQUEST_WORD_TIMING = "android.speech.extra.REQUEST_WORD_TIMING";
    public static final java.lang.String EXTRA_RESULTS = "android.speech.extra.RESULTS";
    public static final java.lang.String EXTRA_RESULTS_PENDINGINTENT = "android.speech.extra.RESULTS_PENDINGINTENT";
    public static final java.lang.String EXTRA_RESULTS_PENDINGINTENT_BUNDLE = "android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE";
    public static final java.lang.String EXTRA_SECURE = "android.speech.extras.EXTRA_SECURE";
    public static final java.lang.String EXTRA_SEGMENTED_SESSION = "android.speech.extra.SEGMENTED_SESSION";
    public static final java.lang.String EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS = "android.speech.extras.SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS";
    public static final java.lang.String EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS = "android.speech.extras.SPEECH_INPUT_MINIMUM_LENGTH_MILLIS";
    public static final java.lang.String EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS = "android.speech.extras.SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS";
    public static final java.lang.String EXTRA_SUPPORTED_LANGUAGES = "android.speech.extra.SUPPORTED_LANGUAGES";
    public static final java.lang.String EXTRA_WEB_SEARCH_ONLY = "android.speech.extra.WEB_SEARCH_ONLY";
    public static final java.lang.String FORMATTING_OPTIMIZE_LATENCY = "latency";
    public static final java.lang.String FORMATTING_OPTIMIZE_QUALITY = "quality";
    public static final java.lang.String LANGUAGE_MODEL_FREE_FORM = "free_form";
    public static final java.lang.String LANGUAGE_MODEL_WEB_SEARCH = "web_search";
    public static final java.lang.String LANGUAGE_SWITCH_BALANCED = "balanced";
    public static final java.lang.String LANGUAGE_SWITCH_HIGH_PRECISION = "high_precision";
    public static final java.lang.String LANGUAGE_SWITCH_QUICK_RESPONSE = "quick_response";
    public static final int RESULT_AUDIO_ERROR = 5;
    public static final int RESULT_CLIENT_ERROR = 2;
    public static final int RESULT_NETWORK_ERROR = 4;
    public static final int RESULT_NO_MATCH = 1;
    public static final int RESULT_SERVER_ERROR = 3;

    private RecognizerIntent() {
    }

    public static final android.content.Intent getVoiceDetailsIntent(android.content.Context context) {
        java.lang.String string;
        android.content.pm.ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(new android.content.Intent(ACTION_WEB_SEARCH), 128);
        if (resolveActivity == null || resolveActivity.activityInfo == null || resolveActivity.activityInfo.metaData == null || (string = resolveActivity.activityInfo.metaData.getString(DETAILS_META_DATA)) == null) {
            return null;
        }
        android.content.Intent intent = new android.content.Intent(ACTION_GET_LANGUAGE_DETAILS);
        intent.setComponent(new android.content.ComponentName(resolveActivity.activityInfo.packageName, string));
        return intent;
    }
}
