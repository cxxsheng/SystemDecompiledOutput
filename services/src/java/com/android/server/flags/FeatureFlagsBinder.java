package com.android.server.flags;

/* loaded from: classes.dex */
class FeatureFlagsBinder extends android.flags.IFeatureFlags.Stub {
    private final com.android.server.flags.DynamicFlagBinderDelegate mDynamicFlagDelegate;
    private final com.android.server.flags.FlagCache<java.lang.String> mFlagCache = new com.android.server.flags.FlagCache<>();
    private final com.android.server.flags.FlagOverrideStore mFlagStore;
    private final com.android.server.flags.FeatureFlagsService.PermissionsChecker mPermissionsChecker;
    private final com.android.server.flags.FlagsShellCommand mShellCommand;

    FeatureFlagsBinder(com.android.server.flags.FlagOverrideStore flagOverrideStore, com.android.server.flags.FlagsShellCommand flagsShellCommand, com.android.server.flags.FeatureFlagsService.PermissionsChecker permissionsChecker) {
        this.mFlagStore = flagOverrideStore;
        this.mShellCommand = flagsShellCommand;
        this.mDynamicFlagDelegate = new com.android.server.flags.DynamicFlagBinderDelegate(flagOverrideStore);
        this.mPermissionsChecker = permissionsChecker;
    }

    public void registerCallback(android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) {
        this.mDynamicFlagDelegate.registerCallback(android.flags.IFeatureFlags.Stub.getCallingPid(), iFeatureFlagsCallback);
    }

    public void unregisterCallback(android.flags.IFeatureFlagsCallback iFeatureFlagsCallback) {
        this.mDynamicFlagDelegate.unregisterCallback(android.flags.IFeatureFlags.Stub.getCallingPid(), iFeatureFlagsCallback);
    }

    public java.util.List<android.flags.SyncableFlag> syncFlags(java.util.List<android.flags.SyncableFlag> list) {
        java.lang.SecurityException securityException;
        boolean z;
        android.flags.SyncableFlag syncableFlag;
        android.flags.SyncableFlag syncableFlag2;
        int callingPid = android.flags.IFeatureFlags.Stub.getCallingPid();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            assertSyncPermission();
            z = true;
            securityException = null;
        } catch (java.lang.SecurityException e) {
            securityException = e;
            z = false;
        }
        for (android.flags.SyncableFlag syncableFlag3 : list) {
            if (!z && !com.android.internal.flags.CoreFlags.isCoreFlag(syncableFlag3)) {
                throw securityException;
            }
            java.lang.String namespace = syncableFlag3.getNamespace();
            java.lang.String name = syncableFlag3.getName();
            if (syncableFlag3.isDynamic()) {
                syncableFlag2 = this.mDynamicFlagDelegate.syncDynamicFlag(callingPid, syncableFlag3);
            } else {
                synchronized (this.mFlagCache) {
                    try {
                        java.lang.String orNull = this.mFlagCache.getOrNull(namespace, name);
                        if (orNull == null) {
                            orNull = android.os.Build.IS_USER ? null : this.mFlagStore.get(namespace, name);
                            if (orNull == null) {
                                orNull = syncableFlag3.getValue();
                            }
                            this.mFlagCache.setIfChanged(namespace, name, orNull);
                        }
                        syncableFlag = new android.flags.SyncableFlag(syncableFlag3.getNamespace(), syncableFlag3.getName(), orNull, false);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                syncableFlag2 = syncableFlag;
            }
            arrayList.add(syncableFlag2);
        }
        return arrayList;
    }

    public void overrideFlag(android.flags.SyncableFlag syncableFlag) {
        assertWritePermission();
        this.mFlagStore.set(syncableFlag.getNamespace(), syncableFlag.getName(), syncableFlag.getValue());
    }

    public void resetFlag(android.flags.SyncableFlag syncableFlag) {
        assertWritePermission();
        this.mFlagStore.erase(syncableFlag.getNamespace(), syncableFlag.getName());
    }

    public java.util.List<android.flags.SyncableFlag> queryFlags(java.util.List<android.flags.SyncableFlag> list) {
        java.lang.String str;
        assertSyncPermission();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.flags.SyncableFlag syncableFlag : list) {
            java.lang.String namespace = syncableFlag.getNamespace();
            java.lang.String name = syncableFlag.getName();
            java.lang.String str2 = this.mFlagStore.get(namespace, name);
            boolean z = str2 != null;
            if (syncableFlag.isDynamic()) {
                str = this.mDynamicFlagDelegate.getFlagValue(namespace, name, syncableFlag.getValue());
            } else {
                java.lang.String orNull = this.mFlagCache.getOrNull(namespace, name);
                if (orNull != null) {
                    str = orNull;
                } else {
                    if (android.os.Build.IS_USER) {
                        str2 = null;
                    }
                    if (str2 != null) {
                        str = str2;
                    } else {
                        str = syncableFlag.getValue();
                    }
                }
            }
            arrayList.add(new android.flags.SyncableFlag(syncableFlag.getNamespace(), syncableFlag.getName(), str, syncableFlag.isDynamic(), z));
        }
        return arrayList;
    }

    private void assertSyncPermission() {
        this.mPermissionsChecker.assertSyncPermission();
        android.flags.IFeatureFlags.Stub.clearCallingIdentity();
    }

    private void assertWritePermission() {
        this.mPermissionsChecker.assertWritePermission();
        android.flags.IFeatureFlags.Stub.clearCallingIdentity();
    }

    @android.annotation.SystemApi
    public int handleShellCommand(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor2, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr) {
        return this.mShellCommand.process(strArr, new java.io.FileOutputStream(parcelFileDescriptor2.getFileDescriptor()), new java.io.FileOutputStream(parcelFileDescriptor3.getFileDescriptor()));
    }
}
