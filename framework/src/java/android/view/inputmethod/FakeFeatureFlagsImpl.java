package android.view.inputmethod;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements android.view.inputmethod.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.view.inputmethod.Flags.FLAG_CONCURRENT_INPUT_METHODS, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_CONNECTIONLESS_HANDWRITING, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_IME_SWITCHER_REVAMP, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_IMM_USERHANDLE_HOSTSIDETESTS, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_PREDICTIVE_BACK_IME, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_REFACTOR_INSETS_CONTROLLER, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE, false), java.util.Map.entry(android.view.inputmethod.Flags.FLAG_USE_ZERO_JANK_PROXY, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.view.inputmethod.Flags.FLAG_CONCURRENT_INPUT_METHODS, android.view.inputmethod.Flags.FLAG_CONNECTIONLESS_HANDWRITING, android.view.inputmethod.Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED, android.view.inputmethod.Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR, android.view.inputmethod.Flags.FLAG_IME_SWITCHER_REVAMP, android.view.inputmethod.Flags.FLAG_IMM_USERHANDLE_HOSTSIDETESTS, android.view.inputmethod.Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION, android.view.inputmethod.Flags.FLAG_PREDICTIVE_BACK_IME, android.view.inputmethod.Flags.FLAG_REFACTOR_INSETS_CONTROLLER, android.view.inputmethod.Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE, android.view.inputmethod.Flags.FLAG_USE_ZERO_JANK_PROXY, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean concurrentInputMethods() {
        return getValue(android.view.inputmethod.Flags.FLAG_CONCURRENT_INPUT_METHODS);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean connectionlessHandwriting() {
        return getValue(android.view.inputmethod.Flags.FLAG_CONNECTIONLESS_HANDWRITING);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean editorinfoHandwritingEnabled() {
        return getValue(android.view.inputmethod.Flags.FLAG_EDITORINFO_HANDWRITING_ENABLED);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean homeScreenHandwritingDelegator() {
        return getValue(android.view.inputmethod.Flags.FLAG_HOME_SCREEN_HANDWRITING_DELEGATOR);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean imeSwitcherRevamp() {
        return getValue(android.view.inputmethod.Flags.FLAG_IME_SWITCHER_REVAMP);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean immUserhandleHostsidetests() {
        return getValue(android.view.inputmethod.Flags.FLAG_IMM_USERHANDLE_HOSTSIDETESTS);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean initiationWithoutInputConnection() {
        return getValue(android.view.inputmethod.Flags.FLAG_INITIATION_WITHOUT_INPUT_CONNECTION);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean predictiveBackIme() {
        return getValue(android.view.inputmethod.Flags.FLAG_PREDICTIVE_BACK_IME);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean refactorInsetsController() {
        return getValue(android.view.inputmethod.Flags.FLAG_REFACTOR_INSETS_CONTROLLER);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useHandwritingListenerForTooltype() {
        return getValue(android.view.inputmethod.Flags.FLAG_USE_HANDWRITING_LISTENER_FOR_TOOLTYPE);
    }

    @Override // android.view.inputmethod.FeatureFlags
    public boolean useZeroJankProxy() {
        return getValue(android.view.inputmethod.Flags.FLAG_USE_ZERO_JANK_PROXY);
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
