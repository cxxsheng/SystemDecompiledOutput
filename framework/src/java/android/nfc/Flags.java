package android.nfc;

/* loaded from: classes2.dex */
public final class Flags {
    private static android.nfc.FeatureFlags FEATURE_FLAGS = new android.nfc.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ENABLE_NFC_CHARGING = "android.nfc.enable_nfc_charging";
    public static final java.lang.String FLAG_ENABLE_NFC_MAINLINE = "android.nfc.enable_nfc_mainline";
    public static final java.lang.String FLAG_ENABLE_NFC_READER_OPTION = "android.nfc.enable_nfc_reader_option";
    public static final java.lang.String FLAG_ENABLE_NFC_SET_DISCOVERY_TECH = "android.nfc.enable_nfc_set_discovery_tech";
    public static final java.lang.String FLAG_ENABLE_NFC_USER_RESTRICTION = "android.nfc.enable_nfc_user_restriction";
    public static final java.lang.String FLAG_ENABLE_TAG_DETECTION_BROADCASTS = "android.nfc.enable_tag_detection_broadcasts";
    public static final java.lang.String FLAG_NFC_OBSERVE_MODE = "android.nfc.nfc_observe_mode";
    public static final java.lang.String FLAG_NFC_OBSERVE_MODE_ST_SHIM = "android.nfc.nfc_observe_mode_st_shim";
    public static final java.lang.String FLAG_NFC_READ_POLLING_LOOP = "android.nfc.nfc_read_polling_loop";
    public static final java.lang.String FLAG_NFC_READ_POLLING_LOOP_ST_SHIM = "android.nfc.nfc_read_polling_loop_st_shim";
    public static final java.lang.String FLAG_NFC_VENDOR_CMD = "android.nfc.nfc_vendor_cmd";

    public static boolean enableNfcCharging() {
        return FEATURE_FLAGS.enableNfcCharging();
    }

    public static boolean enableNfcMainline() {
        return FEATURE_FLAGS.enableNfcMainline();
    }

    public static boolean enableNfcReaderOption() {
        return FEATURE_FLAGS.enableNfcReaderOption();
    }

    public static boolean enableNfcSetDiscoveryTech() {
        return FEATURE_FLAGS.enableNfcSetDiscoveryTech();
    }

    public static boolean enableNfcUserRestriction() {
        return FEATURE_FLAGS.enableNfcUserRestriction();
    }

    public static boolean enableTagDetectionBroadcasts() {
        return FEATURE_FLAGS.enableTagDetectionBroadcasts();
    }

    public static boolean nfcObserveMode() {
        return FEATURE_FLAGS.nfcObserveMode();
    }

    public static boolean nfcObserveModeStShim() {
        return FEATURE_FLAGS.nfcObserveModeStShim();
    }

    public static boolean nfcReadPollingLoop() {
        return FEATURE_FLAGS.nfcReadPollingLoop();
    }

    public static boolean nfcReadPollingLoopStShim() {
        return FEATURE_FLAGS.nfcReadPollingLoopStShim();
    }

    public static boolean nfcVendorCmd() {
        return FEATURE_FLAGS.nfcVendorCmd();
    }
}
