package android.appwidget.flags;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements android.appwidget.flags.FeatureFlags {
    @Override // android.appwidget.flags.FeatureFlags
    public boolean drawDataParcel() {
        return false;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean generatedPreviews() {
        return false;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteAdapterConversion() {
        return false;
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean removeAppWidgetServiceIoFromCriticalPath() {
        return false;
    }
}
