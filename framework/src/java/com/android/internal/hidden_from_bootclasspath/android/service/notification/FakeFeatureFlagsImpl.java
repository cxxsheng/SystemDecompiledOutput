package com.android.internal.hidden_from_bootclasspath.android.service.notification;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_CALLSTYLE_CALLBACK_API, false), java.util.Map.entry(com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_RANKING_UPDATE_ASHMEM, false), java.util.Map.entry(com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_CALLSTYLE_CALLBACK_API, com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_RANKING_UPDATE_ASHMEM, com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean callstyleCallbackApi() {
        return getValue(com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_CALLSTYLE_CALLBACK_API);
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean rankingUpdateAshmem() {
        return getValue(com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_RANKING_UPDATE_ASHMEM);
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.service.notification.FeatureFlags
    public boolean redactSensitiveNotificationsFromUntrustedListeners() {
        return getValue(com.android.internal.hidden_from_bootclasspath.android.service.notification.Flags.FLAG_REDACT_SENSITIVE_NOTIFICATIONS_FROM_UNTRUSTED_LISTENERS);
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
