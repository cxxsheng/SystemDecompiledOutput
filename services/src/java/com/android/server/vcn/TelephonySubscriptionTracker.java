package com.android.server.vcn;

/* loaded from: classes2.dex */
public class TelephonySubscriptionTracker extends android.content.BroadcastReceiver {
    private static final boolean LOG_DBG = false;

    @android.annotation.NonNull
    private static final java.lang.String TAG = com.android.server.vcn.TelephonySubscriptionTracker.class.getSimpleName();

    @android.annotation.NonNull
    private final com.android.server.vcn.TelephonySubscriptionTracker.ActiveDataSubscriptionIdListener mActiveDataSubIdListener;

    @android.annotation.NonNull
    private final com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback mCallback;

    @android.annotation.NonNull
    private final android.telephony.CarrierConfigManager.CarrierConfigChangeListener mCarrierConfigChangeListener;

    @android.annotation.NonNull
    private final android.telephony.CarrierConfigManager mCarrierConfigManager;

    @android.annotation.NonNull
    private final java.util.List<android.telephony.TelephonyManager.CarrierPrivilegesCallback> mCarrierPrivilegesCallbacks;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot mCurrentSnapshot;

    @android.annotation.NonNull
    private final com.android.server.vcn.TelephonySubscriptionTracker.Dependencies mDeps;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, java.lang.Integer> mReadySubIdsBySlotId;

    @android.annotation.NonNull
    private final java.util.Map<java.lang.Integer, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper> mSubIdToCarrierConfigMap;

    @android.annotation.NonNull
    private final android.telephony.SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionChangedListener;

    @android.annotation.NonNull
    private final android.telephony.SubscriptionManager mSubscriptionManager;

    @android.annotation.NonNull
    private final android.telephony.TelephonyManager mTelephonyManager;

    public interface TelephonySubscriptionTrackerCallback {
        void onNewSnapshot(@android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, int i2, int i3, int i4) {
        handleActionCarrierConfigChanged(i, i2);
    }

    public TelephonySubscriptionTracker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback telephonySubscriptionTrackerCallback) {
        this(context, handler, telephonySubscriptionTrackerCallback, new com.android.server.vcn.TelephonySubscriptionTracker.Dependencies());
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    TelephonySubscriptionTracker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionTrackerCallback telephonySubscriptionTrackerCallback, @android.annotation.NonNull com.android.server.vcn.TelephonySubscriptionTracker.Dependencies dependencies) {
        this.mReadySubIdsBySlotId = new java.util.HashMap();
        this.mSubIdToCarrierConfigMap = new java.util.HashMap();
        this.mCarrierPrivilegesCallbacks = new java.util.ArrayList();
        this.mCarrierConfigChangeListener = new android.telephony.CarrierConfigManager.CarrierConfigChangeListener() { // from class: com.android.server.vcn.TelephonySubscriptionTracker$$ExternalSyntheticLambda1
            @Override // android.telephony.CarrierConfigManager.CarrierConfigChangeListener
            public final void onCarrierConfigChanged(int i, int i2, int i3, int i4) {
                com.android.server.vcn.TelephonySubscriptionTracker.this.lambda$new$0(i, i2, i3, i4);
            }
        };
        java.util.Objects.requireNonNull(context, "Missing context");
        this.mContext = context;
        java.util.Objects.requireNonNull(handler, "Missing handler");
        this.mHandler = handler;
        java.util.Objects.requireNonNull(telephonySubscriptionTrackerCallback, "Missing callback");
        this.mCallback = telephonySubscriptionTrackerCallback;
        java.util.Objects.requireNonNull(dependencies, "Missing deps");
        this.mDeps = dependencies;
        this.mTelephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
        this.mSubscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
        this.mCarrierConfigManager = (android.telephony.CarrierConfigManager) this.mContext.getSystemService(android.telephony.CarrierConfigManager.class);
        this.mActiveDataSubIdListener = new com.android.server.vcn.TelephonySubscriptionTracker.ActiveDataSubscriptionIdListener();
        this.mSubscriptionChangedListener = new android.telephony.SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.vcn.TelephonySubscriptionTracker.1
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public void onSubscriptionsChanged() {
                com.android.server.vcn.TelephonySubscriptionTracker.this.handleSubscriptionsChanged();
            }
        };
    }

    public void register() {
        java.util.concurrent.Executor handlerExecutor = new android.os.HandlerExecutor(this.mHandler);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.telephony.action.MULTI_SIM_CONFIG_CHANGED");
        this.mContext.registerReceiver(this, intentFilter, null, this.mHandler);
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(handlerExecutor, this.mSubscriptionChangedListener);
        this.mTelephonyManager.registerTelephonyCallback(handlerExecutor, this.mActiveDataSubIdListener);
        this.mCarrierConfigManager.registerCarrierConfigChangeListener(handlerExecutor, this.mCarrierConfigChangeListener);
        registerCarrierPrivilegesCallbacks();
    }

    private void registerCarrierPrivilegesCallbacks() {
        java.util.concurrent.Executor handlerExecutor = new android.os.HandlerExecutor(this.mHandler);
        int activeModemCount = this.mTelephonyManager.getActiveModemCount();
        for (int i = 0; i < activeModemCount; i++) {
            try {
                android.telephony.TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback = new android.telephony.TelephonyManager.CarrierPrivilegesCallback() { // from class: com.android.server.vcn.TelephonySubscriptionTracker.2
                    public void onCarrierPrivilegesChanged(@android.annotation.NonNull java.util.Set<java.lang.String> set, @android.annotation.NonNull java.util.Set<java.lang.Integer> set2) {
                        com.android.server.vcn.TelephonySubscriptionTracker.this.handleSubscriptionsChanged();
                    }
                };
                this.mTelephonyManager.registerCarrierPrivilegesCallback(i, handlerExecutor, carrierPrivilegesCallback);
                this.mCarrierPrivilegesCallbacks.add(carrierPrivilegesCallback);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.wtf(TAG, "Encounted exception registering carrier privileges listeners", e);
                return;
            }
        }
    }

    public void unregister() {
        this.mContext.unregisterReceiver(this);
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener(this.mSubscriptionChangedListener);
        this.mTelephonyManager.unregisterTelephonyCallback(this.mActiveDataSubIdListener);
        this.mCarrierConfigManager.unregisterCarrierConfigChangeListener(this.mCarrierConfigChangeListener);
        unregisterCarrierPrivilegesCallbacks();
    }

    private void unregisterCarrierPrivilegesCallbacks() {
        java.util.Iterator<android.telephony.TelephonyManager.CarrierPrivilegesCallback> it = this.mCarrierPrivilegesCallbacks.iterator();
        while (it.hasNext()) {
            this.mTelephonyManager.unregisterCarrierPrivilegesCallback(it.next());
        }
        this.mCarrierPrivilegesCallbacks.clear();
    }

    public void handleSubscriptionsChanged() {
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.HashMap hashMap2 = new java.util.HashMap();
        java.util.List<android.telephony.SubscriptionInfo> allSubscriptionInfoList = this.mSubscriptionManager.getAllSubscriptionInfoList();
        if (allSubscriptionInfoList == null) {
            return;
        }
        for (android.telephony.SubscriptionInfo subscriptionInfo : allSubscriptionInfoList) {
            if (subscriptionInfo.getGroupUuid() != null) {
                hashMap2.put(java.lang.Integer.valueOf(subscriptionInfo.getSubscriptionId()), subscriptionInfo);
                if (subscriptionInfo.getSimSlotIndex() != -1 && this.mReadySubIdsBySlotId.values().contains(java.lang.Integer.valueOf(subscriptionInfo.getSubscriptionId()))) {
                    android.telephony.TelephonyManager createForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId());
                    android.os.ParcelUuid groupUuid = subscriptionInfo.getGroupUuid();
                    java.util.Set set = (java.util.Set) hashMap.getOrDefault(groupUuid, new android.util.ArraySet());
                    set.addAll(createForSubscriptionId.getPackagesWithCarrierPrivileges());
                    hashMap.put(groupUuid, set);
                }
            }
        }
        final com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot = new com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot(this.mDeps.getActiveDataSubscriptionId(), hashMap2, this.mSubIdToCarrierConfigMap, hashMap);
        if (!telephonySubscriptionSnapshot.equals(this.mCurrentSnapshot)) {
            this.mCurrentSnapshot = telephonySubscriptionSnapshot;
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.vcn.TelephonySubscriptionTracker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.vcn.TelephonySubscriptionTracker.this.lambda$handleSubscriptionsChanged$1(telephonySubscriptionSnapshot);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleSubscriptionsChanged$1(com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot) {
        this.mCallback.onNewSnapshot(telephonySubscriptionSnapshot);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        char c;
        java.lang.String action = intent.getAction();
        switch (action.hashCode()) {
            case 1093296680:
                if (action.equals("android.telephony.action.MULTI_SIM_CONFIG_CHANGED")) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                handleActionMultiSimConfigChanged(context, intent);
                break;
            default:
                android.util.Slog.v(TAG, "Unknown intent received with action: " + intent.getAction());
                break;
        }
    }

    private void handleActionMultiSimConfigChanged(android.content.Context context, android.content.Intent intent) {
        unregisterCarrierPrivilegesCallbacks();
        int activeModemCount = this.mTelephonyManager.getActiveModemCount();
        java.util.Iterator<java.lang.Integer> it = this.mReadySubIdsBySlotId.keySet().iterator();
        while (it.hasNext()) {
            if (it.next().intValue() >= activeModemCount) {
                it.remove();
            }
        }
        registerCarrierPrivilegesCallbacks();
        handleSubscriptionsChanged();
    }

    private void handleActionCarrierConfigChanged(int i, int i2) {
        android.os.PersistableBundle configForSubId;
        if (i == -1) {
            return;
        }
        if (android.telephony.SubscriptionManager.isValidSubscriptionId(i2)) {
            if (com.android.internal.telephony.flags.Flags.fixCrashOnGettingConfigWhenPhoneIsGone()) {
                configForSubId = android.telephony.CarrierConfigManager.getCarrierConfigSubset(this.mContext, i2, android.net.vcn.VcnManager.VCN_RELATED_CARRIER_CONFIG_KEYS);
            } else {
                configForSubId = this.mCarrierConfigManager.getConfigForSubId(i2, android.net.vcn.VcnManager.VCN_RELATED_CARRIER_CONFIG_KEYS);
            }
            if (this.mDeps.isConfigForIdentifiedCarrier(configForSubId)) {
                this.mReadySubIdsBySlotId.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                if (!configForSubId.isEmpty()) {
                    this.mSubIdToCarrierConfigMap.put(java.lang.Integer.valueOf(i2), new com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper(configForSubId));
                }
                handleSubscriptionsChanged();
                return;
            }
            return;
        }
        java.lang.Integer remove = this.mReadySubIdsBySlotId.remove(java.lang.Integer.valueOf(i));
        if (remove != null) {
            this.mSubIdToCarrierConfigMap.remove(remove);
        }
        handleSubscriptionsChanged();
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setReadySubIdsBySlotId(java.util.Map<java.lang.Integer, java.lang.Integer> map) {
        this.mReadySubIdsBySlotId.clear();
        this.mReadySubIdsBySlotId.putAll(map);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    void setSubIdToCarrierConfigMap(java.util.Map<java.lang.Integer, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper> map) {
        this.mSubIdToCarrierConfigMap.clear();
        this.mSubIdToCarrierConfigMap.putAll(map);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    java.util.Map<java.lang.Integer, java.lang.Integer> getReadySubIdsBySlotId() {
        return java.util.Collections.unmodifiableMap(this.mReadySubIdsBySlotId);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    java.util.Map<java.lang.Integer, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper> getSubIdToCarrierConfigMap() {
        return java.util.Collections.unmodifiableMap(this.mSubIdToCarrierConfigMap);
    }

    public static class TelephonySubscriptionSnapshot {
        public static final com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot EMPTY_SNAPSHOT = new com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot(-1, java.util.Collections.emptyMap(), java.util.Collections.emptyMap(), java.util.Collections.emptyMap());
        private final int mActiveDataSubId;
        private final java.util.Map<android.os.ParcelUuid, java.util.Set<java.lang.String>> mPrivilegedPackages;
        private final java.util.Map<java.lang.Integer, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper> mSubIdToCarrierConfigMap;
        private final java.util.Map<java.lang.Integer, android.telephony.SubscriptionInfo> mSubIdToInfoMap;

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
        TelephonySubscriptionSnapshot(int i, @android.annotation.NonNull java.util.Map<java.lang.Integer, android.telephony.SubscriptionInfo> map, @android.annotation.NonNull java.util.Map<java.lang.Integer, com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper> map2, @android.annotation.NonNull java.util.Map<android.os.ParcelUuid, java.util.Set<java.lang.String>> map3) {
            this.mActiveDataSubId = i;
            java.util.Objects.requireNonNull(map, "subIdToInfoMap was null");
            java.util.Objects.requireNonNull(map3, "privilegedPackages was null");
            java.util.Objects.requireNonNull(map2, "subIdToCarrierConfigMap was null");
            this.mSubIdToInfoMap = java.util.Collections.unmodifiableMap(new java.util.HashMap(map));
            this.mSubIdToCarrierConfigMap = java.util.Collections.unmodifiableMap(new java.util.HashMap(map2));
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            for (java.util.Map.Entry<android.os.ParcelUuid, java.util.Set<java.lang.String>> entry : map3.entrySet()) {
                arrayMap.put(entry.getKey(), java.util.Collections.unmodifiableSet(entry.getValue()));
            }
            this.mPrivilegedPackages = java.util.Collections.unmodifiableMap(arrayMap);
        }

        public int getActiveDataSubscriptionId() {
            return this.mActiveDataSubId;
        }

        @android.annotation.Nullable
        public android.os.ParcelUuid getActiveDataSubscriptionGroup() {
            android.telephony.SubscriptionInfo subscriptionInfo = this.mSubIdToInfoMap.get(java.lang.Integer.valueOf(getActiveDataSubscriptionId()));
            if (subscriptionInfo == null) {
                return null;
            }
            return subscriptionInfo.getGroupUuid();
        }

        @android.annotation.NonNull
        public java.util.Set<android.os.ParcelUuid> getActiveSubscriptionGroups() {
            return this.mPrivilegedPackages.keySet();
        }

        public boolean packageHasPermissionsForSubscriptionGroup(@android.annotation.NonNull android.os.ParcelUuid parcelUuid, @android.annotation.NonNull java.lang.String str) {
            java.util.Set<java.lang.String> set = this.mPrivilegedPackages.get(parcelUuid);
            return set != null && set.contains(str);
        }

        @android.annotation.Nullable
        public android.os.ParcelUuid getGroupForSubId(int i) {
            if (this.mSubIdToInfoMap.containsKey(java.lang.Integer.valueOf(i))) {
                return this.mSubIdToInfoMap.get(java.lang.Integer.valueOf(i)).getGroupUuid();
            }
            return null;
        }

        @android.annotation.NonNull
        public java.util.Set<java.lang.Integer> getAllSubIdsInGroup(android.os.ParcelUuid parcelUuid) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            for (java.util.Map.Entry<java.lang.Integer, android.telephony.SubscriptionInfo> entry : this.mSubIdToInfoMap.entrySet()) {
                if (parcelUuid.equals(entry.getValue().getGroupUuid())) {
                    arraySet.add(entry.getKey());
                }
            }
            return arraySet;
        }

        @android.annotation.NonNull
        public boolean isOpportunistic(int i) {
            if (this.mSubIdToInfoMap.containsKey(java.lang.Integer.valueOf(i))) {
                return this.mSubIdToInfoMap.get(java.lang.Integer.valueOf(i)).isOpportunistic();
            }
            return false;
        }

        @android.annotation.Nullable
        public com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper getCarrierConfigForSubGrp(@android.annotation.NonNull android.os.ParcelUuid parcelUuid) {
            java.util.Iterator<java.lang.Integer> it = getAllSubIdsInGroup(parcelUuid).iterator();
            com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper = null;
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                com.android.server.vcn.util.PersistableBundleUtils.PersistableBundleWrapper persistableBundleWrapper2 = this.mSubIdToCarrierConfigMap.get(java.lang.Integer.valueOf(intValue));
                if (persistableBundleWrapper2 != null) {
                    if (isOpportunistic(intValue)) {
                        persistableBundleWrapper = persistableBundleWrapper2;
                    } else {
                        return persistableBundleWrapper2;
                    }
                }
            }
            return persistableBundleWrapper;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mActiveDataSubId), this.mSubIdToInfoMap, this.mSubIdToCarrierConfigMap, this.mPrivilegedPackages);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot)) {
                return false;
            }
            com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot telephonySubscriptionSnapshot = (com.android.server.vcn.TelephonySubscriptionTracker.TelephonySubscriptionSnapshot) obj;
            return this.mActiveDataSubId == telephonySubscriptionSnapshot.mActiveDataSubId && this.mSubIdToInfoMap.equals(telephonySubscriptionSnapshot.mSubIdToInfoMap) && this.mSubIdToCarrierConfigMap.equals(telephonySubscriptionSnapshot.mSubIdToCarrierConfigMap) && this.mPrivilegedPackages.equals(telephonySubscriptionSnapshot.mPrivilegedPackages);
        }

        public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("TelephonySubscriptionSnapshot:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mActiveDataSubId: " + this.mActiveDataSubId);
            indentingPrintWriter.println("mSubIdToInfoMap: " + this.mSubIdToInfoMap);
            indentingPrintWriter.println("mSubIdToCarrierConfigMap: " + this.mSubIdToCarrierConfigMap);
            indentingPrintWriter.println("mPrivilegedPackages: " + this.mPrivilegedPackages);
            indentingPrintWriter.decreaseIndent();
        }

        public java.lang.String toString() {
            return "TelephonySubscriptionSnapshot{ mActiveDataSubId=" + this.mActiveDataSubId + ", mSubIdToInfoMap=" + this.mSubIdToInfoMap + ", mSubIdToCarrierConfigMap=" + this.mSubIdToCarrierConfigMap + ", mPrivilegedPackages=" + this.mPrivilegedPackages + " }";
        }
    }

    private class ActiveDataSubscriptionIdListener extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener {
        private ActiveDataSubscriptionIdListener() {
        }

        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
        public void onActiveDataSubscriptionIdChanged(int i) {
            com.android.server.vcn.TelephonySubscriptionTracker.this.handleSubscriptionsChanged();
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public static class Dependencies {
        public boolean isConfigForIdentifiedCarrier(android.os.PersistableBundle persistableBundle) {
            return android.telephony.CarrierConfigManager.isConfigForIdentifiedCarrier(persistableBundle);
        }

        public int getActiveDataSubscriptionId() {
            return android.telephony.SubscriptionManager.getActiveDataSubscriptionId();
        }
    }
}
