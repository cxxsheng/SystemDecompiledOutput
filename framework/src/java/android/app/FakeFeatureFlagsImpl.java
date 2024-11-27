package android.app;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.app.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.app.Flags.FLAG_API_TVEXTENDER, false), java.util.Map.entry(android.app.Flags.FLAG_APP_RESTRICTIONS_API, false), java.util.Map.entry(android.app.Flags.FLAG_APP_START_INFO, false), java.util.Map.entry(android.app.Flags.FLAG_BCAST_EVENT_TIMESTAMPS, false), java.util.Map.entry(android.app.Flags.FLAG_BIC_CLIENT, false), java.util.Map.entry(android.app.Flags.FLAG_CATEGORY_VOICEMAIL, false), java.util.Map.entry(android.app.Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE, false), java.util.Map.entry(android.app.Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING, false), java.util.Map.entry(android.app.Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT, false), java.util.Map.entry(android.app.Flags.FLAG_GET_BINDING_UID_IMPORTANCE, false), java.util.Map.entry(android.app.Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK, false), java.util.Map.entry(android.app.Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS, false), java.util.Map.entry(android.app.Flags.FLAG_LIFETIME_EXTENSION_REFACTOR, false), java.util.Map.entry(android.app.Flags.FLAG_MODES_API, false), java.util.Map.entry(android.app.Flags.FLAG_MODES_UI, false), java.util.Map.entry(android.app.Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API, false), java.util.Map.entry(android.app.Flags.FLAG_PINNER_SERVICE_CLIENT_API, false), java.util.Map.entry(android.app.Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED, false), java.util.Map.entry(android.app.Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS, false), java.util.Map.entry(android.app.Flags.FLAG_VISIT_RISKY_URIS, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.app.Flags.FLAG_API_TVEXTENDER, android.app.Flags.FLAG_APP_RESTRICTIONS_API, android.app.Flags.FLAG_APP_START_INFO, android.app.Flags.FLAG_BCAST_EVENT_TIMESTAMPS, android.app.Flags.FLAG_BIC_CLIENT, android.app.Flags.FLAG_CATEGORY_VOICEMAIL, android.app.Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE, android.app.Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING, android.app.Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT, android.app.Flags.FLAG_GET_BINDING_UID_IMPORTANCE, android.app.Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK, android.app.Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS, android.app.Flags.FLAG_LIFETIME_EXTENSION_REFACTOR, android.app.Flags.FLAG_MODES_API, android.app.Flags.FLAG_MODES_UI, android.app.Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API, android.app.Flags.FLAG_PINNER_SERVICE_CLIENT_API, android.app.Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED, android.app.Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS, android.app.Flags.FLAG_VISIT_RISKY_URIS, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.app.FeatureFlags
    public boolean apiTvextender() {
        return getValue(android.app.Flags.FLAG_API_TVEXTENDER);
    }

    @Override // android.app.FeatureFlags
    public boolean appRestrictionsApi() {
        return getValue(android.app.Flags.FLAG_APP_RESTRICTIONS_API);
    }

    @Override // android.app.FeatureFlags
    public boolean appStartInfo() {
        return getValue(android.app.Flags.FLAG_APP_START_INFO);
    }

    @Override // android.app.FeatureFlags
    public boolean bcastEventTimestamps() {
        return getValue(android.app.Flags.FLAG_BCAST_EVENT_TIMESTAMPS);
    }

    @Override // android.app.FeatureFlags
    public boolean bicClient() {
        return getValue(android.app.Flags.FLAG_BIC_CLIENT);
    }

    @Override // android.app.FeatureFlags
    public boolean categoryVoicemail() {
        return getValue(android.app.Flags.FLAG_CATEGORY_VOICEMAIL);
    }

    @Override // android.app.FeatureFlags
    public boolean enableNightModeBinderCache() {
        return getValue(android.app.Flags.FLAG_ENABLE_NIGHT_MODE_BINDER_CACHE);
    }

    @Override // android.app.FeatureFlags
    public boolean enablePipUiStateCallbackOnEntering() {
        return getValue(android.app.Flags.FLAG_ENABLE_PIP_UI_STATE_CALLBACK_ON_ENTERING);
    }

    @Override // android.app.FeatureFlags
    public boolean evenlyDividedCallStyleActionLayout() {
        return getValue(android.app.Flags.FLAG_EVENLY_DIVIDED_CALL_STYLE_ACTION_LAYOUT);
    }

    @Override // android.app.FeatureFlags
    public boolean getBindingUidImportance() {
        return getValue(android.app.Flags.FLAG_GET_BINDING_UID_IMPORTANCE);
    }

    @Override // android.app.FeatureFlags
    public boolean introduceNewServiceOntimeoutCallback() {
        return getValue(android.app.Flags.FLAG_INTRODUCE_NEW_SERVICE_ONTIMEOUT_CALLBACK);
    }

    @Override // android.app.FeatureFlags
    public boolean keyguardPrivateNotifications() {
        return getValue(android.app.Flags.FLAG_KEYGUARD_PRIVATE_NOTIFICATIONS);
    }

    @Override // android.app.FeatureFlags
    public boolean lifetimeExtensionRefactor() {
        return getValue(android.app.Flags.FLAG_LIFETIME_EXTENSION_REFACTOR);
    }

    @Override // android.app.FeatureFlags
    public boolean modesApi() {
        return getValue(android.app.Flags.FLAG_MODES_API);
    }

    @Override // android.app.FeatureFlags
    public boolean modesUi() {
        return getValue(android.app.Flags.FLAG_MODES_UI);
    }

    @Override // android.app.FeatureFlags
    public boolean notificationChannelVibrationEffectApi() {
        return getValue(android.app.Flags.FLAG_NOTIFICATION_CHANNEL_VIBRATION_EFFECT_API);
    }

    @Override // android.app.FeatureFlags
    public boolean pinnerServiceClientApi() {
        return getValue(android.app.Flags.FLAG_PINNER_SERVICE_CLIENT_API);
    }

    @Override // android.app.FeatureFlags
    public boolean systemTermsOfAddressEnabled() {
        return getValue(android.app.Flags.FLAG_SYSTEM_TERMS_OF_ADDRESS_ENABLED);
    }

    @Override // android.app.FeatureFlags
    public boolean uidImportanceListenerForUids() {
        return getValue(android.app.Flags.FLAG_UID_IMPORTANCE_LISTENER_FOR_UIDS);
    }

    @Override // android.app.FeatureFlags
    public boolean visitRiskyUris() {
        return getValue(android.app.Flags.FLAG_VISIT_RISKY_URIS);
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
