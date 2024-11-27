package android.companion;

/* loaded from: classes.dex */
public final class Flags {
    private static android.companion.FeatureFlags FEATURE_FLAGS = new android.companion.FeatureFlagsImpl();
    public static final java.lang.String FLAG_ASSOCIATION_TAG = "android.companion.association_tag";
    public static final java.lang.String FLAG_COMPANION_TRANSPORT_APIS = "android.companion.companion_transport_apis";
    public static final java.lang.String FLAG_DEVICE_PRESENCE = "android.companion.device_presence";
    public static final java.lang.String FLAG_NEW_ASSOCIATION_BUILDER = "android.companion.new_association_builder";
    public static final java.lang.String FLAG_PERM_SYNC_USER_CONSENT = "android.companion.perm_sync_user_consent";

    public static boolean associationTag() {
        return FEATURE_FLAGS.associationTag();
    }

    public static boolean companionTransportApis() {
        return FEATURE_FLAGS.companionTransportApis();
    }

    public static boolean devicePresence() {
        return FEATURE_FLAGS.devicePresence();
    }

    public static boolean newAssociationBuilder() {
        return FEATURE_FLAGS.newAssociationBuilder();
    }

    public static boolean permSyncUserConsent() {
        return FEATURE_FLAGS.permSyncUserConsent();
    }
}
