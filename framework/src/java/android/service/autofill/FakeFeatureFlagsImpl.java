package android.service.autofill;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.service.autofill.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION, false), java.util.Map.entry(android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION, false), java.util.Map.entry(android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2, false), java.util.Map.entry(android.service.autofill.Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY, false), java.util.Map.entry(android.service.autofill.Flags.FLAG_IGNORE_VIEW_STATE_RESET_TO_EMPTY, false), java.util.Map.entry(android.service.autofill.Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE, false), java.util.Map.entry(android.service.autofill.Flags.FLAG_RELAYOUT, false), java.util.Map.entry(android.service.autofill.Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE, false), java.util.Map.entry(android.service.autofill.Flags.FLAG_TEST, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION, android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION, android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2, android.service.autofill.Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY, android.service.autofill.Flags.FLAG_IGNORE_VIEW_STATE_RESET_TO_EMPTY, android.service.autofill.Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE, android.service.autofill.Flags.FLAG_RELAYOUT, android.service.autofill.Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE, android.service.autofill.Flags.FLAG_TEST, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanDevIntegration() {
        return getValue(android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_DEV_INTEGRATION);
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegration() {
        return getValue(android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION);
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegrationPhase2() {
        return getValue(android.service.autofill.Flags.FLAG_AUTOFILL_CREDMAN_INTEGRATION_PHASE2);
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillFieldsFromCurrentSessionOnly() {
        return getValue(android.service.autofill.Flags.FLAG_FILL_FIELDS_FROM_CURRENT_SESSION_ONLY);
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean ignoreViewStateResetToEmpty() {
        return getValue(android.service.autofill.Flags.FLAG_IGNORE_VIEW_STATE_RESET_TO_EMPTY);
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean includeInvisibleViewGroupInAssistStructure() {
        return getValue(android.service.autofill.Flags.FLAG_INCLUDE_INVISIBLE_VIEW_GROUP_IN_ASSIST_STRUCTURE);
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean relayout() {
        return getValue(android.service.autofill.Flags.FLAG_RELAYOUT);
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean remoteFillServiceUseWeakReference() {
        return getValue(android.service.autofill.Flags.FLAG_REMOTE_FILL_SERVICE_USE_WEAK_REFERENCE);
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean test() {
        return getValue(android.service.autofill.Flags.FLAG_TEST);
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
