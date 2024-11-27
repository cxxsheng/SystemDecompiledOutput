package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class Flags {
    private static android.view.inputmethod.FeatureFlags FEATURE_FLAGS = new android.view.inputmethod.FeatureFlagsImpl();
    public static final java.lang.String FLAG_CONCURRENT_INPUT_METHODS = "android.view.inputmethod.concurrent_input_methods";
    public static final java.lang.String FLAG_CONNECTIONLESS_HANDWRITING = "android.view.inputmethod.connectionless_handwriting";
    public static final java.lang.String FLAG_EDITORINFO_HANDWRITING_ENABLED = "android.view.inputmethod.editorinfo_handwriting_enabled";
    public static final java.lang.String FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR = "android.view.inputmethod.home_screen_handwriting_delegator";
    public static final java.lang.String FLAG_IME_SWITCHER_REVAMP = "android.view.inputmethod.ime_switcher_revamp";
    public static final java.lang.String FLAG_IMM_USERHANDLE_HOSTSIDETESTS = "android.view.inputmethod.imm_userhandle_hostsidetests";
    public static final java.lang.String FLAG_INITIATION_WITHOUT_INPUT_CONNECTION = "android.view.inputmethod.initiation_without_input_connection";
    public static final java.lang.String FLAG_PREDICTIVE_BACK_IME = "android.view.inputmethod.predictive_back_ime";
    public static final java.lang.String FLAG_REFACTOR_INSETS_CONTROLLER = "android.view.inputmethod.refactor_insets_controller";
    public static final java.lang.String FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE = "android.view.inputmethod.use_handwriting_listener_for_tooltype";
    public static final java.lang.String FLAG_USE_ZERO_JANK_PROXY = "android.view.inputmethod.use_zero_jank_proxy";

    public static boolean concurrentInputMethods() {
        return FEATURE_FLAGS.concurrentInputMethods();
    }

    public static boolean connectionlessHandwriting() {
        return FEATURE_FLAGS.connectionlessHandwriting();
    }

    public static boolean editorinfoHandwritingEnabled() {
        return FEATURE_FLAGS.editorinfoHandwritingEnabled();
    }

    public static boolean homeScreenHandwritingDelegator() {
        return FEATURE_FLAGS.homeScreenHandwritingDelegator();
    }

    public static boolean imeSwitcherRevamp() {
        return FEATURE_FLAGS.imeSwitcherRevamp();
    }

    public static boolean immUserhandleHostsidetests() {
        return FEATURE_FLAGS.immUserhandleHostsidetests();
    }

    public static boolean initiationWithoutInputConnection() {
        return FEATURE_FLAGS.initiationWithoutInputConnection();
    }

    public static boolean predictiveBackIme() {
        return FEATURE_FLAGS.predictiveBackIme();
    }

    public static boolean refactorInsetsController() {
        return FEATURE_FLAGS.refactorInsetsController();
    }

    public static boolean useHandwritingListenerForTooltype() {
        return FEATURE_FLAGS.useHandwritingListenerForTooltype();
    }

    public static boolean useZeroJankProxy() {
        return FEATURE_FLAGS.useZeroJankProxy();
    }
}
