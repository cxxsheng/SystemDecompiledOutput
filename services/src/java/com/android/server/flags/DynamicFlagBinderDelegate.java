package com.android.server.flags;

/* loaded from: classes.dex */
class DynamicFlagBinderDelegate {
    private static final java.util.function.Function<java.lang.Integer, java.util.Set<android.flags.IFeatureFlagsCallback>> NEW_CALLBACK_SET = new java.util.function.Function() { // from class: com.android.server.flags.DynamicFlagBinderDelegate$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final java.lang.Object apply(java.lang.Object obj) {
            java.util.Set lambda$static$0;
            lambda$static$0 = com.android.server.flags.DynamicFlagBinderDelegate.lambda$static$0((java.lang.Integer) obj);
            return lambda$static$0;
        }
    };
    private final com.android.server.flags.FlagOverrideStore mFlagStore;
    private final com.android.server.flags.FlagCache<com.android.server.flags.DynamicFlagBinderDelegate.DynamicFlagData> mDynamicFlags = new com.android.server.flags.FlagCache<>();
    private final java.util.Map<java.lang.Integer, java.util.Set<android.flags.IFeatureFlagsCallback>> mCallbacks = new java.util.HashMap();
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mDeviceConfigListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.flags.DynamicFlagBinderDelegate.1
        public void onPropertiesChanged(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
            java.lang.String namespace = properties.getNamespace();
            for (java.lang.String str : properties.getKeyset()) {
                if (com.android.server.flags.DynamicFlagBinderDelegate.this.mDynamicFlags.contains(namespace, str) && !com.android.server.flags.DynamicFlagBinderDelegate.this.mFlagStore.contains(namespace, str)) {
                    com.android.server.flags.DynamicFlagBinderDelegate.this.mFlagChangeCallback.onFlagChanged(namespace, str, properties.getString(str, (java.lang.String) null));
                }
            }
        }
    };
    private final com.android.server.flags.FlagOverrideStore.FlagChangeCallback mFlagChangeCallback = new com.android.server.flags.FlagOverrideStore.FlagChangeCallback() { // from class: com.android.server.flags.DynamicFlagBinderDelegate$$ExternalSyntheticLambda2
        @Override // com.android.server.flags.FlagOverrideStore.FlagChangeCallback
        public final void onFlagChanged(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            com.android.server.flags.DynamicFlagBinderDelegate.this.lambda$new$2(str, str2, str3);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Set lambda$static$0(java.lang.Integer num) {
        return new java.util.HashSet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        com.android.server.flags.DynamicFlagBinderDelegate.DynamicFlagData orNull;
        java.util.HashSet hashSet;
        if (!this.mDynamicFlags.contains(str, str2) || (orNull = this.mDynamicFlags.getOrNull(str, str2)) == null) {
            return;
        }
        if (str3 == null) {
            if (orNull.getValue().equals(orNull.getDefaultValue())) {
                return;
            } else {
                str3 = orNull.getDefaultValue();
            }
        } else if (orNull.getValue().equals(str3)) {
            return;
        }
        orNull.setValue(str3);
        synchronized (this.mCallbacks) {
            try {
                hashSet = new java.util.HashSet();
                for (java.lang.Integer num : this.mCallbacks.keySet()) {
                    if (orNull.containsPid(num.intValue())) {
                        hashSet.addAll(this.mCallbacks.get(num));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        final android.flags.SyncableFlag syncableFlag = new android.flags.SyncableFlag(str, str2, str3, true);
        hashSet.forEach(new java.util.function.Consumer() { // from class: com.android.server.flags.DynamicFlagBinderDelegate$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.flags.DynamicFlagBinderDelegate.lambda$new$1(syncableFlag, (android.flags.IFeatureFlagsCallback) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$1(android.flags.SyncableFlag syncableFlag, android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) {
        try {
            iFeatureFlagsCallback.onFlagChange(syncableFlag);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("FeatureFlagsService", "Failed to communicate flag change to client.");
        }
    }

    DynamicFlagBinderDelegate(com.android.server.flags.FlagOverrideStore flagOverrideStore) {
        this.mFlagStore = flagOverrideStore;
        this.mFlagStore.setChangeCallback(this.mFlagChangeCallback);
    }

    android.flags.SyncableFlag syncDynamicFlag(int i, android.flags.SyncableFlag syncableFlag) {
        if (!syncableFlag.isDynamic()) {
            return syncableFlag;
        }
        java.lang.String namespace = syncableFlag.getNamespace();
        java.lang.String name = syncableFlag.getName();
        com.android.server.flags.DynamicFlagBinderDelegate.DynamicFlagData orNull = this.mDynamicFlags.getOrNull(namespace, name);
        java.lang.String flagValue = getFlagValue(namespace, name, syncableFlag.getValue());
        if (!this.mDynamicFlags.containsNamespace(namespace)) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener(namespace, com.android.internal.os.BackgroundThread.getExecutor(), this.mDeviceConfigListener);
        }
        orNull.addClientPid(i);
        orNull.setValue(flagValue);
        orNull.setDefaultValue(syncableFlag.getValue());
        return new android.flags.SyncableFlag(syncableFlag.getNamespace(), syncableFlag.getName(), flagValue, true);
    }

    void registerCallback(int i, android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) {
        java.util.Set<android.flags.IFeatureFlagsCallback> computeIfAbsent;
        synchronized (this.mCallbacks) {
            computeIfAbsent = this.mCallbacks.computeIfAbsent(java.lang.Integer.valueOf(i), NEW_CALLBACK_SET);
            computeIfAbsent.add(iFeatureFlagsCallback);
        }
        try {
            iFeatureFlagsCallback.asBinder().linkToDeath(new com.android.server.flags.DynamicFlagBinderDelegate.BinderGriever(i), 0);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e("FeatureFlagsService", "Failed to link to binder death. Callback not registered.");
            synchronized (this.mCallbacks) {
                computeIfAbsent.remove(iFeatureFlagsCallback);
            }
        }
    }

    void unregisterCallback(int i, android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.computeIfAbsent(java.lang.Integer.valueOf(i), NEW_CALLBACK_SET).remove(iFeatureFlagsCallback);
        }
    }

    java.lang.String getFlagValue(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.lang.String str4;
        com.android.server.flags.DynamicFlagBinderDelegate.DynamicFlagData orNull = this.mDynamicFlags.getOrNull(str, str2);
        if (orNull != null) {
            str4 = orNull.getValue();
        } else {
            this.mDynamicFlags.setIfChanged(str, str2, new com.android.server.flags.DynamicFlagBinderDelegate.DynamicFlagData(str, str2));
            str4 = null;
        }
        if (!android.os.Build.IS_USER && str4 == null) {
            str4 = this.mFlagStore.get(str, str2);
        }
        if (str4 == null) {
            return android.provider.DeviceConfig.getString(str, str2, str3);
        }
        return str4;
    }

    private static class DynamicFlagData {
        private java.lang.String mDefaultValue;
        private final java.lang.String mName;
        private final java.lang.String mNamespace;
        private final java.util.Set<java.lang.Integer> mPids;
        private java.lang.String mValue;

        private DynamicFlagData(java.lang.String str, java.lang.String str2) {
            this.mPids = new java.util.HashSet();
            this.mNamespace = str;
            this.mName = str2;
        }

        java.lang.String getValue() {
            return this.mValue;
        }

        void setValue(java.lang.String str) {
            this.mValue = str;
        }

        java.lang.String getDefaultValue() {
            return this.mDefaultValue;
        }

        void setDefaultValue(java.lang.String str) {
            this.mDefaultValue = str;
        }

        void addClientPid(int i) {
            this.mPids.add(java.lang.Integer.valueOf(i));
        }

        boolean containsPid(int i) {
            return this.mPids.contains(java.lang.Integer.valueOf(i));
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || !(obj instanceof com.android.server.flags.DynamicFlagBinderDelegate.DynamicFlagData)) {
                return false;
            }
            com.android.server.flags.DynamicFlagBinderDelegate.DynamicFlagData dynamicFlagData = (com.android.server.flags.DynamicFlagBinderDelegate.DynamicFlagData) obj;
            return this.mName.equals(dynamicFlagData.mName) && this.mNamespace.equals(dynamicFlagData.mNamespace) && this.mValue.equals(dynamicFlagData.mValue) && this.mDefaultValue.equals(dynamicFlagData.mDefaultValue);
        }

        public int hashCode() {
            return this.mName.hashCode() + this.mNamespace.hashCode() + this.mValue.hashCode() + this.mDefaultValue.hashCode();
        }
    }

    private class BinderGriever implements android.os.IBinder.DeathRecipient {
        private final int mPid;

        private BinderGriever(int i) {
            this.mPid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.flags.DynamicFlagBinderDelegate.this.mCallbacks) {
                com.android.server.flags.DynamicFlagBinderDelegate.this.mCallbacks.remove(java.lang.Integer.valueOf(this.mPid));
            }
        }
    }
}
