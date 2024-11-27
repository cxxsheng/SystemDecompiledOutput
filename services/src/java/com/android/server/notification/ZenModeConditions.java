package com.android.server.notification;

/* loaded from: classes2.dex */
public class ZenModeConditions implements com.android.server.notification.ConditionProviders.Callback {
    private static final boolean DEBUG = com.android.server.notification.ZenModeHelper.DEBUG;
    private static final java.lang.String TAG = "ZenModeHelper";
    private final com.android.server.notification.ConditionProviders mConditionProviders;
    private final com.android.server.notification.ZenModeHelper mHelper;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.util.ArrayMap<android.net.Uri, android.content.ComponentName> mSubscriptions = new android.util.ArrayMap<>();

    public ZenModeConditions(com.android.server.notification.ZenModeHelper zenModeHelper, com.android.server.notification.ConditionProviders conditionProviders) {
        this.mHelper = zenModeHelper;
        this.mConditionProviders = conditionProviders;
        if (this.mConditionProviders.isSystemProviderEnabled("countdown")) {
            this.mConditionProviders.addSystemProvider(new com.android.server.notification.CountdownConditionProvider());
        }
        if (this.mConditionProviders.isSystemProviderEnabled("schedule")) {
            this.mConditionProviders.addSystemProvider(new com.android.server.notification.ScheduleConditionProvider());
        }
        if (this.mConditionProviders.isSystemProviderEnabled("event")) {
            this.mConditionProviders.addSystemProvider(new com.android.server.notification.EventConditionProvider());
        }
        this.mConditionProviders.setCallback(this);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("mSubscriptions=");
        printWriter.println(this.mSubscriptions);
    }

    public void evaluateConfig(android.service.notification.ZenModeConfig zenModeConfig, android.content.ComponentName componentName, boolean z) {
        if (zenModeConfig == null) {
            return;
        }
        if (zenModeConfig.manualRule != null && zenModeConfig.manualRule.condition != null && !zenModeConfig.manualRule.isTrueOrUnknown()) {
            if (DEBUG) {
                android.util.Log.d(TAG, "evaluateConfig: clearing manual rule");
            }
            zenModeConfig.manualRule = null;
        }
        android.util.ArraySet<android.net.Uri> arraySet = new android.util.ArraySet<>();
        evaluateRule(zenModeConfig.manualRule, arraySet, null, z);
        for (android.service.notification.ZenModeConfig.ZenRule zenRule : zenModeConfig.automaticRules.values()) {
            if (zenRule.component != null) {
                evaluateRule(zenRule, arraySet, componentName, z);
                updateSnoozing(zenRule);
            }
        }
        synchronized (this.mSubscriptions) {
            try {
                for (int size = this.mSubscriptions.size() - 1; size >= 0; size--) {
                    android.net.Uri keyAt = this.mSubscriptions.keyAt(size);
                    android.content.ComponentName valueAt = this.mSubscriptions.valueAt(size);
                    if (z && !arraySet.contains(keyAt)) {
                        this.mConditionProviders.unsubscribeIfNecessary(valueAt, keyAt);
                        this.mSubscriptions.removeAt(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.notification.ConditionProviders.Callback
    public void onBootComplete() {
    }

    @Override // com.android.server.notification.ConditionProviders.Callback
    public void onUserSwitched() {
    }

    @Override // com.android.server.notification.ConditionProviders.Callback
    public void onServiceAdded(android.content.ComponentName componentName) {
        if (DEBUG) {
            android.util.Log.d(TAG, "onServiceAdded " + componentName);
        }
        int callingUid = android.os.Binder.getCallingUid();
        this.mHelper.setConfig(this.mHelper.getConfig(), componentName, callingUid == 1000 ? 5 : 4, "zmc.onServiceAdded:" + componentName, callingUid);
    }

    @Override // com.android.server.notification.ConditionProviders.Callback
    public void onConditionChanged(android.net.Uri uri, android.service.notification.Condition condition) {
        if (DEBUG) {
            android.util.Log.d(TAG, "onConditionChanged " + uri + " " + condition);
        }
        if (this.mHelper.getConfig() == null) {
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        this.mHelper.setAutomaticZenRuleState(uri, condition, callingUid == 1000 ? 5 : 4, callingUid);
    }

    private void evaluateRule(android.service.notification.ZenModeConfig.ZenRule zenRule, android.util.ArraySet<android.net.Uri> arraySet, android.content.ComponentName componentName, boolean z) {
        if (zenRule == null || zenRule.conditionId == null || zenRule.configurationActivity != null) {
            return;
        }
        android.net.Uri uri = zenRule.conditionId;
        java.util.Iterator<com.android.server.notification.SystemConditionProviderService> it = this.mConditionProviders.getSystemProviders().iterator();
        boolean z2 = false;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            com.android.server.notification.SystemConditionProviderService next = it.next();
            if (next.isValidConditionId(uri)) {
                this.mConditionProviders.ensureRecordExists(next.getComponent(), uri, next.asInterface());
                zenRule.component = next.getComponent();
                z2 = true;
            }
        }
        if (!z2) {
            android.service.notification.IConditionProvider findConditionProvider = this.mConditionProviders.findConditionProvider(zenRule.component);
            if (DEBUG) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Ensure external rule exists: ");
                sb.append(findConditionProvider != null);
                sb.append(" for ");
                sb.append(uri);
                android.util.Log.d(TAG, sb.toString());
            }
            if (findConditionProvider != null) {
                this.mConditionProviders.ensureRecordExists(zenRule.component, uri, findConditionProvider);
            }
        }
        if (zenRule.component == null && zenRule.enabler == null) {
            android.util.Log.w(TAG, "No component found for automatic rule: " + zenRule.conditionId);
            zenRule.enabled = false;
            return;
        }
        if (arraySet != null) {
            arraySet.add(uri);
        }
        if (z && ((componentName != null && componentName.equals(zenRule.component)) || z2)) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Subscribing to " + zenRule.component);
            }
            if (this.mConditionProviders.subscribeIfNecessary(zenRule.component, zenRule.conditionId)) {
                synchronized (this.mSubscriptions) {
                    this.mSubscriptions.put(zenRule.conditionId, zenRule.component);
                }
            } else {
                zenRule.condition = null;
                if (DEBUG) {
                    android.util.Log.d(TAG, "zmc failed to subscribe");
                }
            }
        }
        if (zenRule.component != null && zenRule.condition == null) {
            zenRule.condition = this.mConditionProviders.findCondition(zenRule.component, zenRule.conditionId);
            if (zenRule.condition == null || !DEBUG) {
                return;
            }
            android.util.Log.d(TAG, "Found existing condition for: " + zenRule.conditionId);
        }
    }

    private boolean updateSnoozing(android.service.notification.ZenModeConfig.ZenRule zenRule) {
        if (zenRule == null || !zenRule.snoozing || zenRule.isTrueOrUnknown()) {
            return false;
        }
        zenRule.snoozing = false;
        if (DEBUG) {
            android.util.Log.d(TAG, "Snoozing reset for " + zenRule.conditionId);
            return true;
        }
        return true;
    }
}
