package android.view.contentprotection.flags;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements android.view.contentprotection.flags.FeatureFlags {
    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean blocklistUpdateEnabled() {
        return true;
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean createAccessibilityOverlayAppOpEnabled() {
        return false;
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean manageDevicePolicyEnabled() {
        return false;
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean parseGroupsConfigEnabled() {
        return true;
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean rapidClearNotificationsByListenerAppOpEnabled() {
        return false;
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean settingUiEnabled() {
        return true;
    }
}
