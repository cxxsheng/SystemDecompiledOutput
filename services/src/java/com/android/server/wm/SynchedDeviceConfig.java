package com.android.server.wm;

/* loaded from: classes3.dex */
final class SynchedDeviceConfig implements android.provider.DeviceConfig.OnPropertiesChangedListener {
    private final java.util.Map<java.lang.String, com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry> mDeviceConfigEntries;
    private final java.util.concurrent.Executor mExecutor;
    private final java.lang.String mNamespace;

    @android.annotation.NonNull
    static com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigBuilder builder(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.concurrent.Executor executor) {
        return new com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigBuilder(str, executor);
    }

    private SynchedDeviceConfig(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull java.util.Map<java.lang.String, com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry> map) {
        this.mNamespace = str;
        this.mExecutor = executor;
        this.mDeviceConfigEntries = map;
    }

    public void onPropertiesChanged(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties) {
        for (com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry synchedDeviceConfigEntry : this.mDeviceConfigEntries.values()) {
            if (properties.getKeyset().contains(synchedDeviceConfigEntry.mFlagKey)) {
                synchedDeviceConfigEntry.updateValue(properties.getBoolean(synchedDeviceConfigEntry.mFlagKey, synchedDeviceConfigEntry.mDefaultValue));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public com.android.server.wm.SynchedDeviceConfig start() {
        android.provider.DeviceConfig.addOnPropertiesChangedListener(this.mNamespace, this.mExecutor, this);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFlags$0(java.lang.String str, com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry synchedDeviceConfigEntry) {
        synchedDeviceConfigEntry.updateValue(isDeviceConfigFlagEnabled(str, synchedDeviceConfigEntry.mDefaultValue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public com.android.server.wm.SynchedDeviceConfig updateFlags() {
        this.mDeviceConfigEntries.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.wm.SynchedDeviceConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.wm.SynchedDeviceConfig.this.lambda$updateFlags$0((java.lang.String) obj, (com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry) obj2);
            }
        });
        return this;
    }

    boolean getFlagValue(@android.annotation.NonNull java.lang.String str) {
        com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry synchedDeviceConfigEntry = this.mDeviceConfigEntries.get(str);
        if (synchedDeviceConfigEntry == null) {
            throw new java.lang.IllegalArgumentException("Unexpected flag name: " + str);
        }
        return synchedDeviceConfigEntry.getValue();
    }

    boolean isBuildTimeFlagEnabled(@android.annotation.NonNull java.lang.String str) {
        com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry synchedDeviceConfigEntry = this.mDeviceConfigEntries.get(str);
        if (synchedDeviceConfigEntry == null) {
            throw new java.lang.IllegalArgumentException("Unexpected flag name: " + str);
        }
        return synchedDeviceConfigEntry.isBuildTimeFlagEnabled();
    }

    private boolean isDeviceConfigFlagEnabled(@android.annotation.NonNull java.lang.String str, boolean z) {
        return android.provider.DeviceConfig.getBoolean(this.mNamespace, str, z);
    }

    static class SynchedDeviceConfigBuilder {
        private final java.util.Map<java.lang.String, com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry> mDeviceConfigEntries;
        private final java.util.concurrent.Executor mExecutor;
        private final java.lang.String mNamespace;

        private SynchedDeviceConfigBuilder(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.concurrent.Executor executor) {
            this.mDeviceConfigEntries = new java.util.concurrent.ConcurrentHashMap();
            this.mNamespace = str;
            this.mExecutor = executor;
        }

        @android.annotation.NonNull
        com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigBuilder addDeviceConfigEntry(@android.annotation.NonNull java.lang.String str, boolean z, boolean z2) {
            if (this.mDeviceConfigEntries.containsKey(str)) {
                throw new java.lang.AssertionError("Key already present: " + str);
            }
            this.mDeviceConfigEntries.put(str, new com.android.server.wm.SynchedDeviceConfig.SynchedDeviceConfigEntry(str, z, z2));
            return this;
        }

        @android.annotation.NonNull
        com.android.server.wm.SynchedDeviceConfig build() {
            return new com.android.server.wm.SynchedDeviceConfig(this.mNamespace, this.mExecutor, this.mDeviceConfigEntries).updateFlags().start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SynchedDeviceConfigEntry {
        private final boolean mBuildTimeFlagEnabled;
        private final boolean mDefaultValue;
        private final java.lang.String mFlagKey;
        private volatile boolean mOverrideValue;

        private SynchedDeviceConfigEntry(@android.annotation.NonNull java.lang.String str, boolean z, boolean z2) {
            this.mFlagKey = str;
            this.mDefaultValue = z;
            this.mOverrideValue = z;
            this.mBuildTimeFlagEnabled = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public void updateValue(boolean z) {
            this.mOverrideValue = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean getValue() {
            return this.mBuildTimeFlagEnabled && this.mOverrideValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isBuildTimeFlagEnabled() {
            return this.mBuildTimeFlagEnabled;
        }
    }
}
