package android.service.autofill;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.service.autofill.FeatureFlags FEATURE_FLAGS = new android.service.autofill.FeatureFlagsImpl();
    public static final java.lang.String FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION = "android.service.autofill.autofill_credman_dev_integration";
    public static final java.lang.String FLAG_AUTOFILL_CREDMAN_INTEGRATION = "android.service.autofill.autofill_credman_integration";
    public static final java.lang.String FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2 = "android.service.autofill.autofill_credman_integration_phase2";
    public static final java.lang.String FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY = "android.service.autofill.fill_fields_from_current_session_only";
    public static final java.lang.String FLAG_IGNORE_VIEW_STATE_RESET_TO_EMPTY = "android.service.autofill.ignore_view_state_reset_to_empty";
    public static final java.lang.String FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE = "android.service.autofill.include_invisible_view_group_in_assist_structure";
    public static final java.lang.String FLAG_RELAYOUT = "android.service.autofill.relayout";
    public static final java.lang.String FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE = "android.service.autofill.remote_fill_service_use_weak_reference";
    public static final java.lang.String FLAG_TEST = "android.service.autofill.test";

    public static boolean autofillCredmanDevIntegration() {
        return FEATURE_FLAGS.autofillCredmanDevIntegration();
    }

    public static boolean autofillCredmanIntegration() {
        return FEATURE_FLAGS.autofillCredmanIntegration();
    }

    public static boolean autofillCredmanIntegrationPhase2() {
        return FEATURE_FLAGS.autofillCredmanIntegrationPhase2();
    }

    public static boolean fillFieldsFromCurrentSessionOnly() {
        return FEATURE_FLAGS.fillFieldsFromCurrentSessionOnly();
    }

    public static boolean ignoreViewStateResetToEmpty() {
        return FEATURE_FLAGS.ignoreViewStateResetToEmpty();
    }

    public static boolean includeInvisibleViewGroupInAssistStructure() {
        return FEATURE_FLAGS.includeInvisibleViewGroupInAssistStructure();
    }

    public static boolean relayout() {
        return FEATURE_FLAGS.relayout();
    }

    public static boolean remoteFillServiceUseWeakReference() {
        return FEATURE_FLAGS.remoteFillServiceUseWeakReference();
    }

    public static boolean test() {
        return FEATURE_FLAGS.test();
    }
}
