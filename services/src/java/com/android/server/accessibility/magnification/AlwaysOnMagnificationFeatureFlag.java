package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class AlwaysOnMagnificationFeatureFlag extends com.android.server.accessibility.magnification.MagnificationFeatureFlagBase {
    private static final java.lang.String FEATURE_NAME_ENABLE_ALWAYS_ON_MAGNIFICATION = "AlwaysOnMagnifier__enable_always_on_magnifier";
    private static final java.lang.String NAMESPACE = "window_manager";

    @android.annotation.NonNull
    private android.content.Context mContext;

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    @android.annotation.NonNull
    public /* bridge */ /* synthetic */ android.provider.DeviceConfig.OnPropertiesChangedListener addOnChangedListener(@android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull java.lang.Runnable runnable) {
        return super.addOnChangedListener(executor, runnable);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public /* bridge */ /* synthetic */ boolean isFeatureFlagEnabled() {
        return super.isFeatureFlagEnabled();
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    public /* bridge */ /* synthetic */ void removeOnChangedListener(@android.annotation.NonNull android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        super.removeOnChangedListener(onPropertiesChangedListener);
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    @com.android.internal.annotations.VisibleForTesting
    public /* bridge */ /* synthetic */ boolean setFeatureFlagEnabled(boolean z) {
        return super.setFeatureFlagEnabled(z);
    }

    AlwaysOnMagnificationFeatureFlag(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    java.lang.String getNamespace() {
        return NAMESPACE;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    java.lang.String getFeatureName() {
        return FEATURE_NAME_ENABLE_ALWAYS_ON_MAGNIFICATION;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    boolean getDefaultValue() {
        return this.mContext.getResources().getBoolean(android.R.bool.config_lockUiMode);
    }
}
