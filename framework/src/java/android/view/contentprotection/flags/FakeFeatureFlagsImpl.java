package android.view.contentprotection.flags;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements android.view.contentprotection.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.view.contentprotection.flags.Flags.FLAG_BLOCKLIST_UPDATE_ENABLED, false), java.util.Map.entry(android.view.contentprotection.flags.Flags.FLAG_CREATE_ACCESSIBILITY_OVERLAY_APP_OP_ENABLED, false), java.util.Map.entry(android.view.contentprotection.flags.Flags.FLAG_MANAGE_DEVICE_POLICY_ENABLED, false), java.util.Map.entry(android.view.contentprotection.flags.Flags.FLAG_PARSE_GROUPS_CONFIG_ENABLED, false), java.util.Map.entry(android.view.contentprotection.flags.Flags.FLAG_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER_APP_OP_ENABLED, false), java.util.Map.entry(android.view.contentprotection.flags.Flags.FLAG_SETTING_UI_ENABLED, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.view.contentprotection.flags.Flags.FLAG_BLOCKLIST_UPDATE_ENABLED, android.view.contentprotection.flags.Flags.FLAG_CREATE_ACCESSIBILITY_OVERLAY_APP_OP_ENABLED, android.view.contentprotection.flags.Flags.FLAG_MANAGE_DEVICE_POLICY_ENABLED, android.view.contentprotection.flags.Flags.FLAG_PARSE_GROUPS_CONFIG_ENABLED, android.view.contentprotection.flags.Flags.FLAG_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER_APP_OP_ENABLED, android.view.contentprotection.flags.Flags.FLAG_SETTING_UI_ENABLED, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean blocklistUpdateEnabled() {
        return getValue(android.view.contentprotection.flags.Flags.FLAG_BLOCKLIST_UPDATE_ENABLED);
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean createAccessibilityOverlayAppOpEnabled() {
        return getValue(android.view.contentprotection.flags.Flags.FLAG_CREATE_ACCESSIBILITY_OVERLAY_APP_OP_ENABLED);
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean manageDevicePolicyEnabled() {
        return getValue(android.view.contentprotection.flags.Flags.FLAG_MANAGE_DEVICE_POLICY_ENABLED);
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean parseGroupsConfigEnabled() {
        return getValue(android.view.contentprotection.flags.Flags.FLAG_PARSE_GROUPS_CONFIG_ENABLED);
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean rapidClearNotificationsByListenerAppOpEnabled() {
        return getValue(android.view.contentprotection.flags.Flags.FLAG_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER_APP_OP_ENABLED);
    }

    @Override // android.view.contentprotection.flags.FeatureFlags
    public boolean settingUiEnabled() {
        return getValue(android.view.contentprotection.flags.Flags.FLAG_SETTING_UI_ENABLED);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
