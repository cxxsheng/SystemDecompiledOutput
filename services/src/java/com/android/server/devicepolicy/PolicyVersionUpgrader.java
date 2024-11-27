package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class PolicyVersionUpgrader {
    private static final java.lang.String LOG_TAG = "DevicePolicyManager";
    private static final boolean VERBOSE_LOG = false;
    private final com.android.server.devicepolicy.PolicyPathProvider mPathProvider;
    private final com.android.server.devicepolicy.PolicyUpgraderDataProvider mProvider;

    @com.android.internal.annotations.VisibleForTesting
    PolicyVersionUpgrader(com.android.server.devicepolicy.PolicyUpgraderDataProvider policyUpgraderDataProvider, com.android.server.devicepolicy.PolicyPathProvider policyPathProvider) {
        this.mProvider = policyUpgraderDataProvider;
        this.mPathProvider = policyPathProvider;
    }

    public void upgradePolicy(int i) {
        int readVersion = readVersion();
        if (readVersion >= i) {
            android.util.Slog.i(LOG_TAG, java.lang.String.format("Current version %d, latest version %d, not upgrading.", java.lang.Integer.valueOf(readVersion), java.lang.Integer.valueOf(i)));
            return;
        }
        int[] usersForUpgrade = this.mProvider.getUsersForUpgrade();
        com.android.server.devicepolicy.OwnersData loadOwners = loadOwners(usersForUpgrade);
        android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> loadAllUsersData = loadAllUsersData(usersForUpgrade, readVersion, loadOwners);
        if (readVersion == 0) {
            android.util.Slog.i(LOG_TAG, java.lang.String.format("Upgrading from version %d", java.lang.Integer.valueOf(readVersion)));
            readVersion = 1;
        }
        if (readVersion == 1) {
            android.util.Slog.i(LOG_TAG, java.lang.String.format("Upgrading from version %d", java.lang.Integer.valueOf(readVersion)));
            upgradeSensorPermissionsAccess(usersForUpgrade, loadOwners, loadAllUsersData);
            readVersion = 2;
        }
        if (readVersion == 2) {
            android.util.Slog.i(LOG_TAG, java.lang.String.format("Upgrading from version %d", java.lang.Integer.valueOf(readVersion)));
            upgradeProtectedPackages(loadOwners, loadAllUsersData);
            readVersion = 3;
        }
        if (readVersion == 3) {
            android.util.Slog.i(LOG_TAG, java.lang.String.format("Upgrading from version %d", java.lang.Integer.valueOf(readVersion)));
            upgradePackageSuspension(usersForUpgrade, loadOwners, loadAllUsersData);
            readVersion = 4;
        }
        if (readVersion == 4) {
            android.util.Slog.i(LOG_TAG, java.lang.String.format("Upgrading from version %d", java.lang.Integer.valueOf(readVersion)));
            initializeEffectiveKeepProfilesRunning(loadAllUsersData);
            readVersion = 5;
        }
        if (readVersion == 5) {
            android.util.Slog.i(LOG_TAG, java.lang.String.format("Upgrading from version %d", java.lang.Integer.valueOf(readVersion)));
            readVersion = 6;
        }
        writePoliciesAndVersion(usersForUpgrade, loadAllUsersData, loadOwners, readVersion);
    }

    private void upgradeSensorPermissionsAccess(int[] iArr, com.android.server.devicepolicy.OwnersData ownersData, android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> sparseArray) {
        for (int i : iArr) {
            com.android.server.devicepolicy.DevicePolicyData devicePolicyData = sparseArray.get(i);
            if (devicePolicyData != null) {
                java.util.Iterator<com.android.server.devicepolicy.ActiveAdmin> it = devicePolicyData.mAdminList.iterator();
                while (it.hasNext()) {
                    com.android.server.devicepolicy.ActiveAdmin next = it.next();
                    if (ownersData.mDeviceOwnerUserId == i && ownersData.mDeviceOwner != null && ownersData.mDeviceOwner.admin.equals(next.info.getComponent())) {
                        android.util.Slog.i(LOG_TAG, java.lang.String.format("Marking Device Owner in user %d for permission grant ", java.lang.Integer.valueOf(i)));
                        next.mAdminCanGrantSensorsPermissions = true;
                    }
                }
            }
        }
    }

    private void upgradeProtectedPackages(com.android.server.devicepolicy.OwnersData ownersData, android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> sparseArray) {
        if (ownersData.mDeviceOwner == null) {
            return;
        }
        com.android.server.devicepolicy.DevicePolicyData devicePolicyData = sparseArray.get(ownersData.mDeviceOwnerUserId);
        if (devicePolicyData == null) {
            android.util.Slog.e(LOG_TAG, "No policy data for do user");
            return;
        }
        java.util.List<java.lang.String> list = null;
        if (ownersData.mDeviceOwnerProtectedPackages != null) {
            java.util.List<java.lang.String> list2 = ownersData.mDeviceOwnerProtectedPackages.get(ownersData.mDeviceOwner.packageName);
            if (list2 != null) {
                android.util.Slog.i(LOG_TAG, "Found protected packages in Owners");
            }
            ownersData.mDeviceOwnerProtectedPackages = null;
            list = list2;
        } else if (devicePolicyData.mUserControlDisabledPackages != null) {
            android.util.Slog.i(LOG_TAG, "Found protected packages in DevicePolicyData");
            java.util.List<java.lang.String> list3 = devicePolicyData.mUserControlDisabledPackages;
            devicePolicyData.mUserControlDisabledPackages = null;
            list = list3;
        }
        com.android.server.devicepolicy.ActiveAdmin activeAdmin = devicePolicyData.mAdminMap.get(ownersData.mDeviceOwner.admin);
        if (activeAdmin == null) {
            android.util.Slog.e(LOG_TAG, "DO admin not found in DO user");
        } else if (list != null) {
            activeAdmin.protectedPackages = new java.util.ArrayList(list);
        }
    }

    private void upgradePackageSuspension(int[] iArr, com.android.server.devicepolicy.OwnersData ownersData, android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> sparseArray) {
        if (ownersData.mDeviceOwner != null) {
            saveSuspendedPackages(sparseArray, ownersData.mDeviceOwnerUserId, ownersData.mDeviceOwner.admin);
        }
        for (int i = 0; i < ownersData.mProfileOwners.size(); i++) {
            saveSuspendedPackages(sparseArray, ownersData.mProfileOwners.keyAt(i).intValue(), ownersData.mProfileOwners.valueAt(i).admin);
        }
    }

    private void saveSuspendedPackages(android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> sparseArray, int i, android.content.ComponentName componentName) {
        com.android.server.devicepolicy.DevicePolicyData devicePolicyData = sparseArray.get(i);
        if (devicePolicyData == null) {
            android.util.Slog.e(LOG_TAG, "No policy data for owner user, cannot migrate suspended packages");
            return;
        }
        com.android.server.devicepolicy.ActiveAdmin activeAdmin = devicePolicyData.mAdminMap.get(componentName);
        if (activeAdmin == null) {
            android.util.Slog.e(LOG_TAG, "No admin for owner, cannot migrate suspended packages");
        } else {
            activeAdmin.suspendedPackages = this.mProvider.getPlatformSuspendedPackages(i);
            android.util.Slog.i(LOG_TAG, java.lang.String.format("Saved %d packages suspended by %s in user %d", java.lang.Integer.valueOf(activeAdmin.suspendedPackages.size()), componentName, java.lang.Integer.valueOf(i)));
        }
    }

    private void initializeEffectiveKeepProfilesRunning(android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> sparseArray) {
        com.android.server.devicepolicy.DevicePolicyData devicePolicyData = sparseArray.get(0);
        if (devicePolicyData == null) {
            return;
        }
        devicePolicyData.mEffectiveKeepProfilesRunning = false;
        android.util.Slog.i(LOG_TAG, "Keep profile running effective state set to false");
    }

    private com.android.server.devicepolicy.OwnersData loadOwners(int[] iArr) {
        com.android.server.devicepolicy.OwnersData ownersData = new com.android.server.devicepolicy.OwnersData(this.mPathProvider);
        ownersData.load(iArr);
        return ownersData;
    }

    private void writePoliciesAndVersion(int[] iArr, android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> sparseArray, com.android.server.devicepolicy.OwnersData ownersData, int i) {
        boolean z = true;
        for (int i2 : iArr) {
            z = z && writeDataForUser(i2, sparseArray.get(i2));
        }
        boolean z2 = z && ownersData.writeDeviceOwner();
        for (int i3 : iArr) {
            z2 = z2 && ownersData.writeProfileOwner(i3);
        }
        if (z2) {
            writeVersion(i);
        } else {
            android.util.Slog.e(LOG_TAG, java.lang.String.format("Error: Failed upgrading policies to version %d", java.lang.Integer.valueOf(i)));
        }
    }

    private android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> loadAllUsersData(int[] iArr, int i, com.android.server.devicepolicy.OwnersData ownersData) {
        android.util.SparseArray<com.android.server.devicepolicy.DevicePolicyData> sparseArray = new android.util.SparseArray<>();
        for (int i2 : iArr) {
            sparseArray.append(i2, loadDataForUser(i2, i, getOwnerForUser(ownersData, i2)));
        }
        return sparseArray;
    }

    @android.annotation.Nullable
    private android.content.ComponentName getOwnerForUser(com.android.server.devicepolicy.OwnersData ownersData, int i) {
        if (ownersData.mDeviceOwnerUserId == i && ownersData.mDeviceOwner != null) {
            return ownersData.mDeviceOwner.admin;
        }
        if (!ownersData.mProfileOwners.containsKey(java.lang.Integer.valueOf(i))) {
            return null;
        }
        return ownersData.mProfileOwners.get(java.lang.Integer.valueOf(i)).admin;
    }

    private com.android.server.devicepolicy.DevicePolicyData loadDataForUser(int i, int i2, android.content.ComponentName componentName) {
        com.android.server.devicepolicy.DevicePolicyData devicePolicyData = new com.android.server.devicepolicy.DevicePolicyData(i);
        if (i2 == 5 && i == 0) {
            devicePolicyData.mEffectiveKeepProfilesRunning = true;
        }
        com.android.server.devicepolicy.DevicePolicyData.load(devicePolicyData, this.mProvider.makeDevicePoliciesJournaledFile(i), this.mProvider.getAdminInfoSupplier(i), componentName);
        return devicePolicyData;
    }

    private boolean writeDataForUser(int i, com.android.server.devicepolicy.DevicePolicyData devicePolicyData) {
        return com.android.server.devicepolicy.DevicePolicyData.store(devicePolicyData, this.mProvider.makeDevicePoliciesJournaledFile(i));
    }

    private com.android.internal.util.JournaledFile getVersionFile() {
        return this.mProvider.makePoliciesVersionJournaledFile(0);
    }

    private int readVersion() {
        try {
            return java.lang.Integer.parseInt(java.nio.file.Files.readAllLines(getVersionFile().chooseForRead().toPath(), java.nio.charset.Charset.defaultCharset()).get(0));
        } catch (java.nio.file.NoSuchFileException e) {
            return 0;
        } catch (java.io.IOException | java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e2) {
            android.util.Slog.e(LOG_TAG, "Error reading version", e2);
            return 0;
        }
    }

    private void writeVersion(int i) {
        com.android.internal.util.JournaledFile versionFile = getVersionFile();
        java.io.File chooseForWrite = versionFile.chooseForWrite();
        try {
            java.nio.file.Files.write(chooseForWrite.toPath(), java.lang.String.format("%d", java.lang.Integer.valueOf(i)).getBytes(), new java.nio.file.OpenOption[0]);
            versionFile.commit();
        } catch (java.io.IOException e) {
            android.util.Slog.e(LOG_TAG, java.lang.String.format("Writing version %d failed", java.lang.Integer.valueOf(i)), e);
            versionFile.rollback();
        }
    }
}
