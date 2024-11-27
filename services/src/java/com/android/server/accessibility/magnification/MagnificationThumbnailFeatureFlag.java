package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
public class MagnificationThumbnailFeatureFlag extends com.android.server.accessibility.magnification.MagnificationFeatureFlagBase {
    private static final java.lang.String FEATURE_NAME_ENABLE_MAGNIFIER_THUMBNAIL = "enable_magnifier_thumbnail";
    private static final java.lang.String NAMESPACE = "accessibility";

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

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    java.lang.String getNamespace() {
        return NAMESPACE;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    java.lang.String getFeatureName() {
        return FEATURE_NAME_ENABLE_MAGNIFIER_THUMBNAIL;
    }

    @Override // com.android.server.accessibility.magnification.MagnificationFeatureFlagBase
    boolean getDefaultValue() {
        return false;
    }
}
