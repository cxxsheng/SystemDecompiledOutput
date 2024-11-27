package android.service.autofill;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements android.service.autofill.FeatureFlags {
    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanDevIntegration() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegration() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean autofillCredmanIntegrationPhase2() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean fillFieldsFromCurrentSessionOnly() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean ignoreViewStateResetToEmpty() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean includeInvisibleViewGroupInAssistStructure() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean relayout() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean remoteFillServiceUseWeakReference() {
        return false;
    }

    @Override // android.service.autofill.FeatureFlags
    public boolean test() {
        return false;
    }
}
