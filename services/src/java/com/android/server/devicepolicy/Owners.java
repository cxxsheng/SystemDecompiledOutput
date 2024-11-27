package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class Owners {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "DevicePolicyManagerService";
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mData"})
    private final com.android.server.devicepolicy.OwnersData mData;
    private final com.android.server.devicepolicy.DeviceStateCacheImpl mDeviceStateCache;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private boolean mSystemReady;
    private final android.os.UserManager mUserManager;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;

    @com.android.internal.annotations.VisibleForTesting
    Owners(android.os.UserManager userManager, com.android.server.pm.UserManagerInternal userManagerInternal, android.content.pm.PackageManagerInternal packageManagerInternal, com.android.server.wm.ActivityTaskManagerInternal activityTaskManagerInternal, android.app.ActivityManagerInternal activityManagerInternal, com.android.server.devicepolicy.DeviceStateCacheImpl deviceStateCacheImpl, com.android.server.devicepolicy.PolicyPathProvider policyPathProvider) {
        this.mUserManager = userManager;
        this.mUserManagerInternal = userManagerInternal;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mActivityTaskManagerInternal = activityTaskManagerInternal;
        this.mActivityManagerInternal = activityManagerInternal;
        this.mDeviceStateCache = deviceStateCacheImpl;
        this.mData = new com.android.server.devicepolicy.OwnersData(policyPathProvider);
    }

    void load() {
        synchronized (this.mData) {
            try {
                int[] array = this.mUserManager.getAliveUsers().stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.devicepolicy.Owners$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(java.lang.Object obj) {
                        int i;
                        i = ((android.content.pm.UserInfo) obj).id;
                        return i;
                    }
                }).toArray();
                this.mData.load(array);
                int i = 0;
                if (android.provider.DeviceConfig.getBoolean("device_policy_manager", "deprecate_usermanagerinternal_devicepolicy", true)) {
                    if (hasDeviceOwner()) {
                        this.mDeviceStateCache.setDeviceOwnerType(this.mData.mDeviceOwnerTypes.getOrDefault(this.mData.mDeviceOwner.packageName, 0).intValue());
                    } else {
                        this.mDeviceStateCache.setDeviceOwnerType(-1);
                    }
                    int length = array.length;
                    while (i < length) {
                        int i2 = array[i];
                        this.mDeviceStateCache.setHasProfileOwner(i2, hasProfileOwner(i2));
                        i++;
                    }
                } else {
                    this.mUserManagerInternal.setDeviceManaged(hasDeviceOwner());
                    int length2 = array.length;
                    while (i < length2) {
                        int i3 = array[i];
                        this.mUserManagerInternal.setUserManaged(i3, hasProfileOwner(i3));
                        i++;
                    }
                }
                notifyChangeLocked();
                pushDeviceOwnerUidToActivityTaskManagerLocked();
                pushProfileOwnerUidsToActivityTaskManagerLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mData"})
    private void notifyChangeLocked() {
        pushToDevicePolicyManager();
        pushToPackageManagerLocked();
        pushToActivityManagerLocked();
        pushToAppOpsLocked();
    }

    private void pushToDevicePolicyManager() {
        com.android.server.devicepolicy.DevicePolicyManagerService.invalidateBinderCaches();
    }

    @com.android.internal.annotations.GuardedBy({"mData"})
    private void pushToPackageManagerLocked() {
        android.util.SparseArray<java.lang.String> sparseArray = new android.util.SparseArray<>();
        for (int size = this.mData.mProfileOwners.size() - 1; size >= 0; size--) {
            sparseArray.put(this.mData.mProfileOwners.keyAt(size).intValue(), this.mData.mProfileOwners.valueAt(size).packageName);
        }
        this.mPackageManagerInternal.setDeviceAndProfileOwnerPackages(this.mData.mDeviceOwnerUserId, this.mData.mDeviceOwner != null ? this.mData.mDeviceOwner.packageName : null, sparseArray);
    }

    @com.android.internal.annotations.GuardedBy({"mData"})
    private void pushDeviceOwnerUidToActivityTaskManagerLocked() {
        this.mActivityTaskManagerInternal.setDeviceOwnerUid(getDeviceOwnerUidLocked());
    }

    @com.android.internal.annotations.GuardedBy({"mData"})
    private void pushProfileOwnerUidsToActivityTaskManagerLocked() {
        this.mActivityTaskManagerInternal.setProfileOwnerUids(getProfileOwnerUidsLocked());
    }

    @com.android.internal.annotations.GuardedBy({"mData"})
    private void pushToActivityManagerLocked() {
        this.mActivityManagerInternal.setDeviceOwnerUid(getDeviceOwnerUidLocked());
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int size = this.mData.mProfileOwners.size() - 1; size >= 0; size--) {
            int packageUid = this.mPackageManagerInternal.getPackageUid(this.mData.mProfileOwners.valueAt(size).packageName, 4333568L, this.mData.mProfileOwners.keyAt(size).intValue());
            if (packageUid >= 0) {
                arraySet.add(java.lang.Integer.valueOf(packageUid));
            }
        }
        this.mActivityManagerInternal.setProfileOwnerUid(arraySet);
    }

    @com.android.internal.annotations.GuardedBy({"mData"})
    int getDeviceOwnerUidLocked() {
        if (this.mData.mDeviceOwner != null) {
            return this.mPackageManagerInternal.getPackageUid(this.mData.mDeviceOwner.packageName, 4333568L, this.mData.mDeviceOwnerUserId);
        }
        return -1;
    }

    @com.android.internal.annotations.GuardedBy({"mData"})
    java.util.Set<java.lang.Integer> getProfileOwnerUidsLocked() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i = 0; i < this.mData.mProfileOwners.size(); i++) {
            arraySet.add(java.lang.Integer.valueOf(this.mPackageManagerInternal.getPackageUid(this.mData.mProfileOwners.valueAt(i).packageName, 4333568L, this.mData.mProfileOwners.keyAt(i).intValue())));
        }
        return arraySet;
    }

    java.lang.String getDeviceOwnerPackageName() {
        java.lang.String str;
        synchronized (this.mData) {
            try {
                str = this.mData.mDeviceOwner != null ? this.mData.mDeviceOwner.packageName : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return str;
    }

    int getDeviceOwnerUserId() {
        int i;
        synchronized (this.mData) {
            i = this.mData.mDeviceOwnerUserId;
        }
        return i;
    }

    @android.annotation.Nullable
    android.util.Pair<java.lang.Integer, android.content.ComponentName> getDeviceOwnerUserIdAndComponent() {
        synchronized (this.mData) {
            try {
                if (this.mData.mDeviceOwner == null) {
                    return null;
                }
                return android.util.Pair.create(java.lang.Integer.valueOf(this.mData.mDeviceOwnerUserId), this.mData.mDeviceOwner.admin);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.content.ComponentName getDeviceOwnerComponent() {
        android.content.ComponentName componentName;
        synchronized (this.mData) {
            try {
                componentName = this.mData.mDeviceOwner != null ? this.mData.mDeviceOwner.admin : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return componentName;
    }

    java.lang.String getDeviceOwnerRemoteBugreportUri() {
        java.lang.String str;
        synchronized (this.mData) {
            try {
                str = this.mData.mDeviceOwner != null ? this.mData.mDeviceOwner.remoteBugreportUri : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return str;
    }

    java.lang.String getDeviceOwnerRemoteBugreportHash() {
        java.lang.String str;
        synchronized (this.mData) {
            try {
                str = this.mData.mDeviceOwner != null ? this.mData.mDeviceOwner.remoteBugreportHash : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return str;
    }

    void setDeviceOwner(android.content.ComponentName componentName, int i) {
        if (i < 0) {
            android.util.Slog.e(TAG, "Invalid user id for device owner user: " + i);
            return;
        }
        synchronized (this.mData) {
            try {
                this.mData.mDeviceOwner = new com.android.server.devicepolicy.OwnersData.OwnerInfo(componentName, null, null, true);
                this.mData.mDeviceOwnerUserId = i;
                if (android.provider.DeviceConfig.getBoolean("device_policy_manager", "deprecate_usermanagerinternal_devicepolicy", true)) {
                    this.mDeviceStateCache.setDeviceOwnerType(this.mData.mDeviceOwnerTypes.getOrDefault(this.mData.mDeviceOwner.packageName, 0).intValue());
                } else {
                    this.mUserManagerInternal.setDeviceManaged(true);
                }
                notifyChangeLocked();
                pushDeviceOwnerUidToActivityTaskManagerLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearDeviceOwner() {
        synchronized (this.mData) {
            try {
                this.mData.mDeviceOwnerTypes.remove(this.mData.mDeviceOwner.packageName);
                this.mData.mDeviceOwner = null;
                this.mData.mDeviceOwnerUserId = com.android.server.am.ProcessList.INVALID_ADJ;
                if (android.provider.DeviceConfig.getBoolean("device_policy_manager", "deprecate_usermanagerinternal_devicepolicy", true)) {
                    this.mDeviceStateCache.setDeviceOwnerType(-1);
                } else {
                    this.mUserManagerInternal.setDeviceManaged(false);
                }
                notifyChangeLocked();
                pushDeviceOwnerUidToActivityTaskManagerLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setProfileOwner(android.content.ComponentName componentName, int i) {
        synchronized (this.mData) {
            try {
                this.mData.mProfileOwners.put(java.lang.Integer.valueOf(i), new com.android.server.devicepolicy.OwnersData.OwnerInfo(componentName, null, null, false));
                if (android.provider.DeviceConfig.getBoolean("device_policy_manager", "deprecate_usermanagerinternal_devicepolicy", true)) {
                    this.mDeviceStateCache.setHasProfileOwner(i, true);
                } else {
                    this.mUserManagerInternal.setUserManaged(i, true);
                }
                notifyChangeLocked();
                pushProfileOwnerUidsToActivityTaskManagerLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeProfileOwner(int i) {
        synchronized (this.mData) {
            try {
                this.mData.mProfileOwners.remove(java.lang.Integer.valueOf(i));
                if (android.provider.DeviceConfig.getBoolean("device_policy_manager", "deprecate_usermanagerinternal_devicepolicy", true)) {
                    this.mDeviceStateCache.setHasProfileOwner(i, false);
                } else {
                    this.mUserManagerInternal.setUserManaged(i, false);
                }
                notifyChangeLocked();
                pushProfileOwnerUidsToActivityTaskManagerLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void transferProfileOwner(android.content.ComponentName componentName, int i) {
        synchronized (this.mData) {
            com.android.server.devicepolicy.OwnersData.OwnerInfo ownerInfo = this.mData.mProfileOwners.get(java.lang.Integer.valueOf(i));
            this.mData.mProfileOwners.put(java.lang.Integer.valueOf(i), new com.android.server.devicepolicy.OwnersData.OwnerInfo(componentName, ownerInfo.remoteBugreportUri, ownerInfo.remoteBugreportHash, ownerInfo.isOrganizationOwnedDevice));
            notifyChangeLocked();
            pushProfileOwnerUidsToActivityTaskManagerLocked();
        }
    }

    void transferDeviceOwnership(android.content.ComponentName componentName) {
        synchronized (this.mData) {
            try {
                java.lang.Integer remove = this.mData.mDeviceOwnerTypes.remove(this.mData.mDeviceOwner.packageName);
                this.mData.mDeviceOwner = new com.android.server.devicepolicy.OwnersData.OwnerInfo(componentName, this.mData.mDeviceOwner.remoteBugreportUri, this.mData.mDeviceOwner.remoteBugreportHash, this.mData.mDeviceOwner.isOrganizationOwnedDevice);
                if (remove != null) {
                    this.mData.mDeviceOwnerTypes.put(this.mData.mDeviceOwner.packageName, remove);
                }
                notifyChangeLocked();
                pushDeviceOwnerUidToActivityTaskManagerLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.content.ComponentName getProfileOwnerComponent(int i) {
        android.content.ComponentName componentName;
        synchronized (this.mData) {
            try {
                com.android.server.devicepolicy.OwnersData.OwnerInfo ownerInfo = this.mData.mProfileOwners.get(java.lang.Integer.valueOf(i));
                componentName = ownerInfo != null ? ownerInfo.admin : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return componentName;
    }

    java.lang.String getProfileOwnerPackage(int i) {
        java.lang.String str;
        synchronized (this.mData) {
            try {
                com.android.server.devicepolicy.OwnersData.OwnerInfo ownerInfo = this.mData.mProfileOwners.get(java.lang.Integer.valueOf(i));
                str = ownerInfo != null ? ownerInfo.packageName : null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return str;
    }

    boolean isProfileOwnerOfOrganizationOwnedDevice(int i) {
        boolean z;
        synchronized (this.mData) {
            try {
                com.android.server.devicepolicy.OwnersData.OwnerInfo ownerInfo = this.mData.mProfileOwners.get(java.lang.Integer.valueOf(i));
                z = ownerInfo != null ? ownerInfo.isOrganizationOwnedDevice : false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    java.util.Set<java.lang.Integer> getProfileOwnerKeys() {
        java.util.Set<java.lang.Integer> keySet;
        synchronized (this.mData) {
            keySet = this.mData.mProfileOwners.keySet();
        }
        return keySet;
    }

    java.util.List<com.android.server.devicepolicy.OwnerShellData> listAllOwners() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mData) {
            try {
                if (this.mData.mDeviceOwner != null) {
                    arrayList.add(com.android.server.devicepolicy.OwnerShellData.forDeviceOwner(this.mData.mDeviceOwnerUserId, this.mData.mDeviceOwner.admin));
                }
                for (int i = 0; i < this.mData.mProfileOwners.size(); i++) {
                    arrayList.add(com.android.server.devicepolicy.OwnerShellData.forUserProfileOwner(this.mData.mProfileOwners.keyAt(i).intValue(), this.mData.mProfileOwners.valueAt(i).admin));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    android.app.admin.SystemUpdatePolicy getSystemUpdatePolicy() {
        android.app.admin.SystemUpdatePolicy systemUpdatePolicy;
        synchronized (this.mData) {
            systemUpdatePolicy = this.mData.mSystemUpdatePolicy;
        }
        return systemUpdatePolicy;
    }

    void setSystemUpdatePolicy(android.app.admin.SystemUpdatePolicy systemUpdatePolicy) {
        synchronized (this.mData) {
            this.mData.mSystemUpdatePolicy = systemUpdatePolicy;
        }
    }

    void clearSystemUpdatePolicy() {
        synchronized (this.mData) {
            this.mData.mSystemUpdatePolicy = null;
        }
    }

    android.util.Pair<java.time.LocalDate, java.time.LocalDate> getSystemUpdateFreezePeriodRecord() {
        android.util.Pair<java.time.LocalDate, java.time.LocalDate> pair;
        synchronized (this.mData) {
            pair = new android.util.Pair<>(this.mData.mSystemUpdateFreezeStart, this.mData.mSystemUpdateFreezeEnd);
        }
        return pair;
    }

    java.lang.String getSystemUpdateFreezePeriodRecordAsString() {
        java.lang.String systemUpdateFreezePeriodRecordAsString;
        synchronized (this.mData) {
            systemUpdateFreezePeriodRecordAsString = this.mData.getSystemUpdateFreezePeriodRecordAsString();
        }
        return systemUpdateFreezePeriodRecordAsString;
    }

    boolean setSystemUpdateFreezePeriodRecord(java.time.LocalDate localDate, java.time.LocalDate localDate2) {
        boolean z;
        boolean z2;
        synchronized (this.mData) {
            try {
                z = true;
                if (java.util.Objects.equals(this.mData.mSystemUpdateFreezeStart, localDate)) {
                    z2 = false;
                } else {
                    this.mData.mSystemUpdateFreezeStart = localDate;
                    z2 = true;
                }
                if (java.util.Objects.equals(this.mData.mSystemUpdateFreezeEnd, localDate2)) {
                    z = z2;
                } else {
                    this.mData.mSystemUpdateFreezeEnd = localDate2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return z;
    }

    boolean hasDeviceOwner() {
        boolean z;
        synchronized (this.mData) {
            z = this.mData.mDeviceOwner != null;
        }
        return z;
    }

    boolean isDeviceOwnerUserId(int i) {
        boolean z;
        synchronized (this.mData) {
            try {
                z = this.mData.mDeviceOwner != null && this.mData.mDeviceOwnerUserId == i;
            } finally {
            }
        }
        return z;
    }

    boolean isDefaultDeviceOwnerUserId(int i) {
        boolean z;
        synchronized (this.mData) {
            try {
                z = this.mData.mDeviceOwner != null && this.mData.mDeviceOwnerUserId == i && getDeviceOwnerType(getDeviceOwnerPackageName()) == 0;
            } finally {
            }
        }
        return z;
    }

    boolean isFinancedDeviceOwnerUserId(int i) {
        boolean z;
        synchronized (this.mData) {
            try {
                if (this.mData.mDeviceOwner != null && this.mData.mDeviceOwnerUserId == i) {
                    z = true;
                    if (getDeviceOwnerType(getDeviceOwnerPackageName()) == 1) {
                    }
                }
                z = false;
            } finally {
            }
        }
        return z;
    }

    boolean hasProfileOwner(int i) {
        boolean z;
        synchronized (this.mData) {
            z = getProfileOwnerComponent(i) != null;
        }
        return z;
    }

    void setDeviceOwnerRemoteBugreportUriAndHash(java.lang.String str, java.lang.String str2) {
        synchronized (this.mData) {
            try {
                if (this.mData.mDeviceOwner != null) {
                    this.mData.mDeviceOwner.remoteBugreportUri = str;
                    this.mData.mDeviceOwner.remoteBugreportHash = str2;
                }
                writeDeviceOwner();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setProfileOwnerOfOrganizationOwnedDevice(int i, boolean z) {
        synchronized (this.mData) {
            try {
                com.android.server.devicepolicy.OwnersData.OwnerInfo ownerInfo = this.mData.mProfileOwners.get(java.lang.Integer.valueOf(i));
                if (ownerInfo != null) {
                    ownerInfo.isOrganizationOwnedDevice = z;
                } else {
                    android.util.Slog.e(TAG, java.lang.String.format("No profile owner for user %d to set org-owned flag.", java.lang.Integer.valueOf(i)));
                }
                writeProfileOwner(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setDeviceOwnerType(java.lang.String str, int i, boolean z) {
        synchronized (this.mData) {
            try {
                if (!hasDeviceOwner()) {
                    android.util.Slog.e(TAG, "Attempting to set a device owner type when there is no device owner");
                } else if (!z && isDeviceOwnerTypeSetForDeviceOwner(str)) {
                    android.util.Slog.e(TAG, "Setting the device owner type more than once is only allowed for test only admins");
                } else {
                    this.mData.mDeviceOwnerTypes.put(str, java.lang.Integer.valueOf(i));
                    writeDeviceOwner();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int getDeviceOwnerType(java.lang.String str) {
        synchronized (this.mData) {
            try {
                if (!isDeviceOwnerTypeSetForDeviceOwner(str)) {
                    return 0;
                }
                return this.mData.mDeviceOwnerTypes.get(str).intValue();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isDeviceOwnerTypeSetForDeviceOwner(java.lang.String str) {
        boolean z;
        synchronized (this.mData) {
            try {
                z = !this.mData.mDeviceOwnerTypes.isEmpty() && this.mData.mDeviceOwnerTypes.containsKey(str);
            } finally {
            }
        }
        return z;
    }

    void writeDeviceOwner() {
        synchronized (this.mData) {
            pushToDevicePolicyManager();
            this.mData.writeDeviceOwner();
        }
    }

    void writeProfileOwner(int i) {
        synchronized (this.mData) {
            pushToDevicePolicyManager();
            this.mData.writeProfileOwner(i);
        }
    }

    boolean saveSystemUpdateInfo(@android.annotation.Nullable android.app.admin.SystemUpdateInfo systemUpdateInfo) {
        synchronized (this.mData) {
            try {
                if (java.util.Objects.equals(systemUpdateInfo, this.mData.mSystemUpdateInfo)) {
                    return false;
                }
                this.mData.mSystemUpdateInfo = systemUpdateInfo;
                this.mData.writeDeviceOwner();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public android.app.admin.SystemUpdateInfo getSystemUpdateInfo() {
        android.app.admin.SystemUpdateInfo systemUpdateInfo;
        synchronized (this.mData) {
            systemUpdateInfo = this.mData.mSystemUpdateInfo;
        }
        return systemUpdateInfo;
    }

    void markMigrationToPolicyEngine() {
        synchronized (this.mData) {
            this.mData.mMigratedToPolicyEngine = true;
            this.mData.writeDeviceOwner();
        }
    }

    boolean isMigratedToPolicyEngine() {
        boolean z;
        synchronized (this.mData) {
            z = this.mData.mMigratedToPolicyEngine;
        }
        return z;
    }

    void markSecurityLoggingMigrated() {
        synchronized (this.mData) {
            this.mData.mSecurityLoggingMigrated = true;
            this.mData.writeDeviceOwner();
        }
    }

    void markPostUpgradeMigration() {
        synchronized (this.mData) {
            this.mData.mPoliciesMigratedPostUpdate = true;
            this.mData.writeDeviceOwner();
        }
    }

    boolean isSecurityLoggingMigrated() {
        boolean z;
        synchronized (this.mData) {
            z = this.mData.mSecurityLoggingMigrated;
        }
        return z;
    }

    boolean isMigratedPostUpdate() {
        boolean z;
        synchronized (this.mData) {
            z = this.mData.mPoliciesMigratedPostUpdate;
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mData"})
    void pushToAppOpsLocked() {
        int deviceOwnerUidLocked;
        if (!this.mSystemReady) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
            if (this.mData.mDeviceOwner != null && (deviceOwnerUidLocked = getDeviceOwnerUidLocked()) >= 0) {
                sparseIntArray.put(this.mData.mDeviceOwnerUserId, deviceOwnerUidLocked);
            }
            if (this.mData.mProfileOwners != null) {
                for (int size = this.mData.mProfileOwners.size() - 1; size >= 0; size--) {
                    int packageUid = this.mPackageManagerInternal.getPackageUid(this.mData.mProfileOwners.valueAt(size).packageName, 4333568L, this.mData.mProfileOwners.keyAt(size).intValue());
                    if (packageUid >= 0) {
                        sparseIntArray.put(this.mData.mProfileOwners.keyAt(size).intValue(), packageUid);
                    }
                }
            }
            android.app.AppOpsManagerInternal appOpsManagerInternal = (android.app.AppOpsManagerInternal) com.android.server.LocalServices.getService(android.app.AppOpsManagerInternal.class);
            if (appOpsManagerInternal != null) {
                if (sparseIntArray.size() <= 0) {
                    sparseIntArray = null;
                }
                appOpsManagerInternal.setDeviceAndProfileOwners(sparseIntArray);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void systemReady() {
        synchronized (this.mData) {
            this.mSystemReady = true;
            pushToActivityManagerLocked();
            pushToAppOpsLocked();
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mData) {
            this.mData.dump(indentingPrintWriter);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getDeviceOwnerFile() {
        return this.mData.getDeviceOwnerFile();
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getProfileOwnerFile(int i) {
        return this.mData.getProfileOwnerFile(i);
    }
}
