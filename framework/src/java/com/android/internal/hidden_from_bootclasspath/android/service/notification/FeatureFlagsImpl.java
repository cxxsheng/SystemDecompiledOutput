package com.android.internal.hidden_from_bootclasspath.android.service.notification;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean callstyleCallbackApi() {
        return false;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean rankingUpdateAshmem() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean redactSensitiveNotificationsFromUntrustedListeners() {
        return false;
    }
}
