package android.service.chooser;

/* loaded from: classes3.dex */
public final class Flags {
    private static android.service.chooser.FeatureFlags FEATURE_FLAGS = new android.service.chooser.FeatureFlagsImpl();
    public static final java.lang.String FLAG_CHOOSER_ALBUM_TEXT = "android.service.chooser.chooser_album_text";
    public static final java.lang.String FLAG_CHOOSER_PAYLOAD_TOGGLING = "android.service.chooser.chooser_payload_toggling";
    public static final java.lang.String FLAG_ENABLE_CHOOSER_RESULT = "android.service.chooser.enable_chooser_result";
    public static final java.lang.String FLAG_ENABLE_SHARESHEET_METADATA_EXTRA = "android.service.chooser.enable_sharesheet_metadata_extra";
    public static final java.lang.String FLAG_LEGACY_CHOOSER_PINNING_REMOVAL = "android.service.chooser.legacy_chooser_pinning_removal";

    public static boolean chooserAlbumText() {
        return FEATURE_FLAGS.chooserAlbumText();
    }

    public static boolean chooserPayloadToggling() {
        return FEATURE_FLAGS.chooserPayloadToggling();
    }

    public static boolean enableChooserResult() {
        return FEATURE_FLAGS.enableChooserResult();
    }

    public static boolean enableSharesheetMetadataExtra() {
        return FEATURE_FLAGS.enableSharesheetMetadataExtra();
    }

    public static boolean legacyChooserPinningRemoval() {
        return FEATURE_FLAGS.legacyChooserPinningRemoval();
    }
}
