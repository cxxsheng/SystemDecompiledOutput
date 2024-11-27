package android.security;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.security.FeatureFlags FEATURE_FLAGS = new android.security.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ASM_RESTRICTIONS_ENABLED = "android.security.asm_restrictions_enabled";
    public static final java.lang.String FLAG_ASM_TOASTS_ENABLED = "android.security.asm_toasts_enabled";
    public static final java.lang.String FLAG_BINARY_TRANSPARENCY_SEPOLICY_HASH = "android.security.binary_transparency_sepolicy_hash";
    public static final java.lang.String FLAG_BLOCK_NULL_ACTION_INTENTS = "android.security.block_null_action_intents";
    public static final java.lang.String FLAG_CERTIFICATE_TRANSPARENCY_CONFIGURATION = "android.security.certificate_transparency_configuration";
    public static final java.lang.String FLAG_CONTENT_URI_PERMISSION_APIS = "android.security.content_uri_permission_apis";
    public static final java.lang.String FLAG_DEPRECATE_FSV_SIG = "android.security.deprecate_fsv_sig";
    public static final java.lang.String FLAG_ENFORCE_INTENT_FILTER_MATCH = "android.security.enforce_intent_filter_match";
    public static final java.lang.String FLAG_EXTEND_ECM_TO_ALL_SETTINGS = "android.security.extend_ecm_to_all_settings";
    public static final java.lang.String FLAG_EXTEND_VB_CHAIN_TO_UPDATED_APK = "android.security.extend_vb_chain_to_updated_apk";
    public static final java.lang.String FLAG_FIX_UNLOCKED_DEVICE_REQUIRED_KEYS_V2 = "android.security.fix_unlocked_device_required_keys_v2";
    public static final java.lang.String FLAG_FRP_ENFORCEMENT = "android.security.frp_enforcement";
    public static final java.lang.String FLAG_FSVERITY_API = "android.security.fsverity_api";
    public static final java.lang.String FLAG_KEYINFO_UNLOCKED_DEVICE_REQUIRED = "android.security.keyinfo_unlocked_device_required";
    public static final java.lang.String FLAG_MGF1_DIGEST_SETTER_V2 = "android.security.mgf1_digest_setter_v2";
    public static final java.lang.String FLAG_REPORT_PRIMARY_AUTH_ATTEMPTS = "android.security.report_primary_auth_attempts";

    public static boolean asmRestrictionsEnabled() {
        return FEATURE_FLAGS.asmRestrictionsEnabled();
    }

    public static boolean asmToastsEnabled() {
        return FEATURE_FLAGS.asmToastsEnabled();
    }

    public static boolean binaryTransparencySepolicyHash() {
        return FEATURE_FLAGS.binaryTransparencySepolicyHash();
    }

    public static boolean blockNullActionIntents() {
        return FEATURE_FLAGS.blockNullActionIntents();
    }

    public static boolean certificateTransparencyConfiguration() {
        return FEATURE_FLAGS.certificateTransparencyConfiguration();
    }

    public static boolean contentUriPermissionApis() {
        return FEATURE_FLAGS.contentUriPermissionApis();
    }

    public static boolean deprecateFsvSig() {
        return FEATURE_FLAGS.deprecateFsvSig();
    }

    public static boolean enforceIntentFilterMatch() {
        return FEATURE_FLAGS.enforceIntentFilterMatch();
    }

    public static boolean extendEcmToAllSettings() {
        return FEATURE_FLAGS.extendEcmToAllSettings();
    }

    public static boolean extendVbChainToUpdatedApk() {
        return FEATURE_FLAGS.extendVbChainToUpdatedApk();
    }

    public static boolean fixUnlockedDeviceRequiredKeysV2() {
        return FEATURE_FLAGS.fixUnlockedDeviceRequiredKeysV2();
    }

    public static boolean frpEnforcement() {
        return FEATURE_FLAGS.frpEnforcement();
    }

    public static boolean fsverityApi() {
        return FEATURE_FLAGS.fsverityApi();
    }

    public static boolean keyinfoUnlockedDeviceRequired() {
        return FEATURE_FLAGS.keyinfoUnlockedDeviceRequired();
    }

    public static boolean mgf1DigestSetterV2() {
        return FEATURE_FLAGS.mgf1DigestSetterV2();
    }

    public static boolean reportPrimaryAuthAttempts() {
        return FEATURE_FLAGS.reportPrimaryAuthAttempts();
    }
}
