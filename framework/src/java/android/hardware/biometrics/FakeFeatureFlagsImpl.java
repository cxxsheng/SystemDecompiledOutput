package android.hardware.biometrics;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.hardware.biometrics.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.hardware.biometrics.Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT, false), java.util.Map.entry(android.hardware.biometrics.Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT, false), java.util.Map.entry(android.hardware.biometrics.Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT, false), java.util.Map.entry(android.hardware.biometrics.Flags.FLAG_LAST_AUTHENTICATION_TIME, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.hardware.biometrics.Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT, android.hardware.biometrics.Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT, android.hardware.biometrics.Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT, android.hardware.biometrics.Flags.FLAG_LAST_AUTHENTICATION_TIME, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.hardware.biometrics.FeatureFlags
    public boolean addKeyAgreementCryptoObject() {
        return getValue(android.hardware.biometrics.Flags.FLAG_ADD_KEY_AGREEMENT_CRYPTO_OBJECT);
    }

    @Override // android.hardware.biometrics.FeatureFlags
    public boolean customBiometricPrompt() {
        return getValue(android.hardware.biometrics.Flags.FLAG_CUSTOM_BIOMETRIC_PROMPT);
    }

    @Override // android.hardware.biometrics.FeatureFlags
    public boolean getOpIdCryptoObject() {
        return getValue(android.hardware.biometrics.Flags.FLAG_GET_OP_ID_CRYPTO_OBJECT);
    }

    @Override // android.hardware.biometrics.FeatureFlags
    public boolean lastAuthenticationTime() {
        return getValue(android.hardware.biometrics.Flags.FLAG_LAST_AUTHENTICATION_TIME);
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
