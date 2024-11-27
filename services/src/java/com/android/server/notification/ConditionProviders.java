package com.android.server.notification;

/* loaded from: classes2.dex */
public class ConditionProviders extends com.android.server.notification.ManagedServices {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG_ENABLED_DND_APPS = "dnd_apps";
    private com.android.server.notification.ConditionProviders.Callback mCallback;
    private final java.util.ArrayList<com.android.server.notification.ConditionProviders.ConditionRecord> mRecords;
    private final android.util.ArraySet<java.lang.String> mSystemConditionProviderNames;
    private final android.util.ArraySet<com.android.server.notification.SystemConditionProviderService> mSystemConditionProviders;

    public interface Callback {
        void onBootComplete();

        void onConditionChanged(android.net.Uri uri, android.service.notification.Condition condition);

        void onServiceAdded(android.content.ComponentName componentName);

        void onUserSwitched();
    }

    public ConditionProviders(android.content.Context context, com.android.server.notification.ManagedServices.UserProfiles userProfiles, android.content.pm.IPackageManager iPackageManager) {
        super(context, new java.lang.Object(), userProfiles, iPackageManager);
        this.mRecords = new java.util.ArrayList<>();
        this.mSystemConditionProviders = new android.util.ArraySet<>();
        this.mSystemConditionProviderNames = safeSet(com.android.server.notification.PropConfig.getStringArray(this.mContext, "system.condition.providers", android.R.array.config_statusBarIcons));
        this.mApprovalLevel = 0;
    }

    public void setCallback(com.android.server.notification.ConditionProviders.Callback callback) {
        this.mCallback = callback;
    }

    public boolean isSystemProviderEnabled(java.lang.String str) {
        return this.mSystemConditionProviderNames.contains(str);
    }

    public void addSystemProvider(com.android.server.notification.SystemConditionProviderService systemConditionProviderService) {
        this.mSystemConditionProviders.add(systemConditionProviderService);
        systemConditionProviderService.attachBase(this.mContext);
        registerSystemService(systemConditionProviderService.asInterface(), systemConditionProviderService.getComponent(), 0, 1000);
    }

    public java.lang.Iterable<com.android.server.notification.SystemConditionProviderService> getSystemProviders() {
        return this.mSystemConditionProviders;
    }

    @Override // com.android.server.notification.ManagedServices
    protected android.util.ArrayMap<java.lang.Boolean, java.util.ArrayList<android.content.ComponentName>> resetComponents(java.lang.String str, int i) {
        resetPackage(str, i);
        android.util.ArrayMap<java.lang.Boolean, java.util.ArrayList<android.content.ComponentName>> arrayMap = new android.util.ArrayMap<>();
        arrayMap.put(true, new java.util.ArrayList<>(0));
        arrayMap.put(false, new java.util.ArrayList<>(0));
        return arrayMap;
    }

    boolean resetPackage(java.lang.String str, int i) {
        boolean isPackageOrComponentAllowed = super.isPackageOrComponentAllowed(str, i);
        boolean isDefaultComponentOrPackage = super.isDefaultComponentOrPackage(str);
        if (!isPackageOrComponentAllowed && isDefaultComponentOrPackage) {
            setPackageOrComponentEnabled(str, i, true, true);
        }
        if (isPackageOrComponentAllowed && !isDefaultComponentOrPackage) {
            setPackageOrComponentEnabled(str, i, true, false);
        }
        if (!isPackageOrComponentAllowed && isDefaultComponentOrPackage) {
            return true;
        }
        return false;
    }

    @Override // com.android.server.notification.ManagedServices
    void writeDefaults(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        synchronized (this.mDefaultsLock) {
            typedXmlSerializer.attribute((java.lang.String) null, "defaults", java.lang.String.join(":", this.mDefaultPackages));
        }
    }

    @Override // com.android.server.notification.ManagedServices
    protected com.android.server.notification.ManagedServices.Config getConfig() {
        com.android.server.notification.ManagedServices.Config config = new com.android.server.notification.ManagedServices.Config();
        config.caption = "condition provider";
        config.serviceInterface = "android.service.notification.ConditionProviderService";
        config.secureSettingName = null;
        config.xmlTag = TAG_ENABLED_DND_APPS;
        config.secondarySettingName = "enabled_notification_listeners";
        config.bindPermission = "android.permission.BIND_CONDITION_PROVIDER_SERVICE";
        config.settingsAction = "android.settings.ACTION_CONDITION_PROVIDER_SETTINGS";
        config.clientLabel = android.R.string.common_name_suffixes;
        return config;
    }

    @Override // com.android.server.notification.ManagedServices
    public void dump(java.io.PrintWriter printWriter, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        int i;
        super.dump(printWriter, dumpFilter);
        synchronized (this.mMutex) {
            try {
                printWriter.print("    mRecords(");
                printWriter.print(this.mRecords.size());
                printWriter.println("):");
                for (int i2 = 0; i2 < this.mRecords.size(); i2++) {
                    com.android.server.notification.ConditionProviders.ConditionRecord conditionRecord = this.mRecords.get(i2);
                    if (dumpFilter == null || dumpFilter.matches(conditionRecord.component)) {
                        printWriter.print("      ");
                        printWriter.println(conditionRecord);
                        java.lang.String tryParseDescription = com.android.server.notification.CountdownConditionProvider.tryParseDescription(conditionRecord.id);
                        if (tryParseDescription != null) {
                            printWriter.print("        (");
                            printWriter.print(tryParseDescription);
                            printWriter.println(")");
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.print("    mSystemConditionProviders: ");
        printWriter.println(this.mSystemConditionProviderNames);
        for (i = 0; i < this.mSystemConditionProviders.size(); i++) {
            this.mSystemConditionProviders.valueAt(i).dump(printWriter, dumpFilter);
        }
    }

    @Override // com.android.server.notification.ManagedServices
    protected android.os.IInterface asInterface(android.os.IBinder iBinder) {
        return android.service.notification.IConditionProvider.Stub.asInterface(iBinder);
    }

    @Override // com.android.server.notification.ManagedServices
    protected boolean checkType(android.os.IInterface iInterface) {
        return iInterface instanceof android.service.notification.IConditionProvider;
    }

    @Override // com.android.server.notification.ManagedServices
    public void onBootPhaseAppsCanStart() {
        super.onBootPhaseAppsCanStart();
        for (int i = 0; i < this.mSystemConditionProviders.size(); i++) {
            this.mSystemConditionProviders.valueAt(i).onBootComplete();
        }
        if (this.mCallback != null) {
            this.mCallback.onBootComplete();
        }
    }

    @Override // com.android.server.notification.ManagedServices
    public void onUserSwitched(int i) {
        super.onUserSwitched(i);
        if (this.mCallback != null) {
            this.mCallback.onUserSwitched();
        }
    }

    @Override // com.android.server.notification.ManagedServices
    protected void onServiceAdded(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        try {
            provider(managedServiceInfo).onConnected();
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(this.TAG, "can't connect to service " + managedServiceInfo, e);
        }
        if (this.mCallback != null) {
            this.mCallback.onServiceAdded(managedServiceInfo.component);
        }
    }

    @Override // com.android.server.notification.ManagedServices
    protected void ensureFilters(android.content.pm.ServiceInfo serviceInfo, int i) {
    }

    @Override // com.android.server.notification.ManagedServices
    protected void loadDefaultsFromConfig() {
        java.lang.String string = this.mContext.getResources().getString(android.R.string.config_defaultContentSuggestionsService);
        if (string != null) {
            java.lang.String[] split = string.split(":");
            for (int i = 0; i < split.length; i++) {
                if (!android.text.TextUtils.isEmpty(split[i])) {
                    addDefaultComponentOrPackage(split[i]);
                }
            }
        }
    }

    @Override // com.android.server.notification.ManagedServices
    protected void onServiceRemovedLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        if (managedServiceInfo == null) {
            return;
        }
        for (int size = this.mRecords.size() - 1; size >= 0; size--) {
            if (this.mRecords.get(size).component.equals(managedServiceInfo.component)) {
                this.mRecords.remove(size);
            }
        }
    }

    @Override // com.android.server.notification.ManagedServices
    public void onPackagesChanged(boolean z, java.lang.String[] strArr, int[] iArr) {
        if (z) {
            android.app.INotificationManager service = android.app.NotificationManager.getService();
            if (strArr != null && strArr.length > 0) {
                for (java.lang.String str : strArr) {
                    try {
                        service.removeAutomaticZenRules(str, false);
                        service.setNotificationPolicyAccessGranted(str, false);
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(this.TAG, "Failed to clean up rules for " + str, e);
                    }
                }
            }
        }
        super.onPackagesChanged(z, strArr, iArr);
    }

    @Override // com.android.server.notification.ManagedServices
    protected boolean isValidEntry(java.lang.String str, int i) {
        return true;
    }

    @Override // com.android.server.notification.ManagedServices
    protected boolean allowRebindForParentUser() {
        return true;
    }

    @Override // com.android.server.notification.ManagedServices
    protected java.lang.String getRequiredPermission() {
        return null;
    }

    public com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceToken(android.service.notification.IConditionProvider iConditionProvider) {
        com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked;
        synchronized (this.mMutex) {
            checkServiceTokenLocked = checkServiceTokenLocked(iConditionProvider);
        }
        return checkServiceTokenLocked;
    }

    private android.service.notification.Condition[] getValidConditions(java.lang.String str, android.service.notification.Condition[] conditionArr) {
        if (conditionArr == null || conditionArr.length == 0) {
            return null;
        }
        int length = conditionArr.length;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(length);
        for (int i = 0; i < length; i++) {
            if (conditionArr[i] == null) {
                android.util.Slog.w(this.TAG, "Ignoring null condition from " + str);
            } else {
                android.net.Uri uri = conditionArr[i].id;
                if (arrayMap.containsKey(uri)) {
                    android.util.Slog.w(this.TAG, "Ignoring condition from " + str + " for duplicate id: " + uri);
                } else {
                    arrayMap.put(uri, conditionArr[i]);
                }
            }
        }
        if (arrayMap.size() == 0) {
            return null;
        }
        if (arrayMap.size() == length) {
            return conditionArr;
        }
        int size = arrayMap.size();
        android.service.notification.Condition[] conditionArr2 = new android.service.notification.Condition[size];
        for (int i2 = 0; i2 < size; i2++) {
            conditionArr2[i2] = (android.service.notification.Condition) arrayMap.valueAt(i2);
        }
        return conditionArr2;
    }

    private com.android.server.notification.ConditionProviders.ConditionRecord getRecordLocked(android.net.Uri uri, android.content.ComponentName componentName, boolean z) {
        if (uri == null || componentName == null) {
            return null;
        }
        int size = this.mRecords.size();
        for (int i = 0; i < size; i++) {
            com.android.server.notification.ConditionProviders.ConditionRecord conditionRecord = this.mRecords.get(i);
            if (conditionRecord.id.equals(uri) && conditionRecord.component.equals(componentName)) {
                return conditionRecord;
            }
        }
        if (!z) {
            return null;
        }
        com.android.server.notification.ConditionProviders.ConditionRecord conditionRecord2 = new com.android.server.notification.ConditionProviders.ConditionRecord(uri, componentName);
        this.mRecords.add(conditionRecord2);
        return conditionRecord2;
    }

    public void notifyConditions(java.lang.String str, com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo, android.service.notification.Condition[] conditionArr) {
        synchronized (this.mMutex) {
            try {
                if (this.DEBUG) {
                    java.lang.String str2 = this.TAG;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("notifyConditions pkg=");
                    sb.append(str);
                    sb.append(" info=");
                    sb.append(managedServiceInfo);
                    sb.append(" conditions=");
                    sb.append(conditionArr == null ? null : java.util.Arrays.asList(conditionArr));
                    android.util.Slog.d(str2, sb.toString());
                }
                android.service.notification.Condition[] validConditions = getValidConditions(str, conditionArr);
                if (validConditions == null || validConditions.length == 0) {
                    return;
                }
                for (android.service.notification.Condition condition : validConditions) {
                    com.android.server.notification.ConditionProviders.ConditionRecord recordLocked = getRecordLocked(condition.id, managedServiceInfo.component, true);
                    recordLocked.info = managedServiceInfo;
                    recordLocked.condition = condition;
                }
                for (android.service.notification.Condition condition2 : validConditions) {
                    if (this.mCallback != null) {
                        this.mCallback.onConditionChanged(condition2.id, condition2);
                    }
                }
            } finally {
            }
        }
    }

    public android.service.notification.IConditionProvider findConditionProvider(android.content.ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        for (com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getServices()) {
            if (componentName.equals(managedServiceInfo.component)) {
                return provider(managedServiceInfo);
            }
        }
        return null;
    }

    public android.service.notification.Condition findCondition(android.content.ComponentName componentName, android.net.Uri uri) {
        android.service.notification.Condition condition;
        if (componentName == null || uri == null) {
            return null;
        }
        synchronized (this.mMutex) {
            try {
                com.android.server.notification.ConditionProviders.ConditionRecord recordLocked = getRecordLocked(uri, componentName, false);
                condition = recordLocked != null ? recordLocked.condition : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return condition;
    }

    public void ensureRecordExists(android.content.ComponentName componentName, android.net.Uri uri, android.service.notification.IConditionProvider iConditionProvider) {
        synchronized (this.mMutex) {
            try {
                com.android.server.notification.ConditionProviders.ConditionRecord recordLocked = getRecordLocked(uri, componentName, true);
                if (recordLocked.info == null) {
                    recordLocked.info = checkServiceTokenLocked(iConditionProvider);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean subscribeIfNecessary(android.content.ComponentName componentName, android.net.Uri uri) {
        synchronized (this.mMutex) {
            try {
                com.android.server.notification.ConditionProviders.ConditionRecord recordLocked = getRecordLocked(uri, componentName, false);
                if (recordLocked == null) {
                    android.util.Slog.w(this.TAG, "Unable to subscribe to " + componentName + " " + uri);
                    return false;
                }
                if (recordLocked.subscribed) {
                    return true;
                }
                subscribeLocked(recordLocked);
                return recordLocked.subscribed;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unsubscribeIfNecessary(android.content.ComponentName componentName, android.net.Uri uri) {
        synchronized (this.mMutex) {
            try {
                com.android.server.notification.ConditionProviders.ConditionRecord recordLocked = getRecordLocked(uri, componentName, false);
                if (recordLocked == null) {
                    android.util.Slog.w(this.TAG, "Unable to unsubscribe to " + componentName + " " + uri);
                    return;
                }
                if (recordLocked.subscribed) {
                    unsubscribeLocked(recordLocked);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void subscribeLocked(com.android.server.notification.ConditionProviders.ConditionRecord conditionRecord) {
        if (this.DEBUG) {
            android.util.Slog.d(this.TAG, "subscribeLocked " + conditionRecord);
        }
        android.service.notification.IConditionProvider provider = provider(conditionRecord);
        if (provider != null) {
            try {
                android.util.Slog.d(this.TAG, "Subscribing to " + conditionRecord.id + " with " + conditionRecord.component);
                provider.onSubscribe(conditionRecord.id);
                conditionRecord.subscribed = true;
            } catch (android.os.RemoteException e) {
                e = e;
                android.util.Slog.w(this.TAG, "Error subscribing to " + conditionRecord, e);
            }
        }
        e = null;
        com.android.server.notification.ZenLog.traceSubscribe(conditionRecord != null ? conditionRecord.id : null, provider, e);
    }

    @java.lang.SafeVarargs
    private static <T> android.util.ArraySet<T> safeSet(T... tArr) {
        android.util.ArraySet<T> arraySet = new android.util.ArraySet<>();
        if (tArr == null || tArr.length == 0) {
            return arraySet;
        }
        for (T t : tArr) {
            if (t != null) {
                arraySet.add(t);
            }
        }
        return arraySet;
    }

    private void unsubscribeLocked(com.android.server.notification.ConditionProviders.ConditionRecord conditionRecord) {
        if (this.DEBUG) {
            android.util.Slog.d(this.TAG, "unsubscribeLocked " + conditionRecord);
        }
        android.service.notification.IConditionProvider provider = provider(conditionRecord);
        if (provider == null) {
            e = null;
        } else {
            try {
                provider.onUnsubscribe(conditionRecord.id);
                e = null;
            } catch (android.os.RemoteException e) {
                e = e;
                android.util.Slog.w(this.TAG, "Error unsubscribing to " + conditionRecord, e);
            }
            conditionRecord.subscribed = false;
        }
        com.android.server.notification.ZenLog.traceUnsubscribe(conditionRecord != null ? conditionRecord.id : null, provider, e);
    }

    private static android.service.notification.IConditionProvider provider(com.android.server.notification.ConditionProviders.ConditionRecord conditionRecord) {
        if (conditionRecord == null) {
            return null;
        }
        return provider(conditionRecord.info);
    }

    private static android.service.notification.IConditionProvider provider(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        if (managedServiceInfo == null) {
            return null;
        }
        return managedServiceInfo.service;
    }

    void resetDefaultFromConfig() {
        synchronized (this.mDefaultsLock) {
            this.mDefaultComponents.clear();
            this.mDefaultPackages.clear();
        }
        loadDefaultsFromConfig();
    }

    boolean removeDefaultFromConfig(int i) {
        java.lang.String string = this.mContext.getResources().getString(android.R.string.config_defaultContextualSearchEnabled);
        if (string == null) {
            return false;
        }
        java.lang.String[] split = string.split(":");
        boolean z = false;
        for (int i2 = 0; i2 < split.length; i2++) {
            if (!android.text.TextUtils.isEmpty(split[i2])) {
                z |= removePackageFromApprovedLists(i, split[i2], "remove from config");
            }
        }
        return z;
    }

    private boolean removePackageFromApprovedLists(int i, java.lang.String str, java.lang.String str2) {
        boolean z;
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> arrayMap = this.mApproved.get(java.lang.Integer.valueOf(i));
                z = false;
                if (arrayMap != null) {
                    int size = arrayMap.size();
                    boolean z2 = false;
                    for (int i2 = 0; i2 < size; i2++) {
                        android.util.ArraySet<java.lang.String> valueAt = arrayMap.valueAt(i2);
                        for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                            java.lang.String valueAt2 = valueAt.valueAt(size2);
                            if (android.text.TextUtils.equals(str, getPackageName(valueAt2))) {
                                valueAt.removeAt(size2);
                                if (this.DEBUG) {
                                    android.util.Slog.v(this.TAG, "Removing " + valueAt2 + " from approved list; " + str2);
                                }
                                z2 = true;
                            }
                        }
                    }
                    z = z2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    private static class ConditionRecord {
        public final android.content.ComponentName component;
        public android.service.notification.Condition condition;
        public final android.net.Uri id;
        public com.android.server.notification.ManagedServices.ManagedServiceInfo info;
        public boolean subscribed;

        private ConditionRecord(android.net.Uri uri, android.content.ComponentName componentName) {
            this.id = uri;
            this.component = componentName;
        }

        public java.lang.String toString() {
            return "ConditionRecord[id=" + this.id + ",component=" + this.component + ",subscribed=" + this.subscribed + ']';
        }
    }
}
