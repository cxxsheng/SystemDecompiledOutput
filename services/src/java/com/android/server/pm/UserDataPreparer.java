package com.android.server.pm;

/* loaded from: classes2.dex */
class UserDataPreparer {
    private static final java.lang.String TAG = "UserDataPreparer";
    private static final java.lang.String XATTR_SERIAL = "user.serial";
    private final android.content.Context mContext;
    private final java.lang.Object mInstallLock;
    private final com.android.server.pm.Installer mInstaller;

    UserDataPreparer(com.android.server.pm.Installer installer, java.lang.Object obj, android.content.Context context) {
        this.mInstallLock = obj;
        this.mContext = context;
        this.mInstaller = installer;
    }

    void prepareUserData(android.content.pm.UserInfo userInfo, int i) {
        synchronized (this.mInstallLock) {
            try {
                android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
                prepareUserDataLI(null, userInfo, i, true);
                java.util.Iterator it = storageManager.getWritablePrivateVolumes().iterator();
                while (it.hasNext()) {
                    java.lang.String fsUuid = ((android.os.storage.VolumeInfo) it.next()).getFsUuid();
                    if (fsUuid != null) {
                        prepareUserDataLI(fsUuid, userInfo, i, true);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void prepareUserDataLI(java.lang.String str, android.content.pm.UserInfo userInfo, int i, boolean z) {
        int i2 = userInfo.id;
        int i3 = userInfo.serialNumber;
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
        boolean z2 = userInfo.lastLoggedInTime == 0;
        com.android.server.utils.Slogf.d(TAG, "Preparing user data; volumeUuid=%s, userId=%d, flags=0x%x, isNewUser=%s", str, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z2));
        try {
            storageManager.prepareUserStorage(str, i2, i);
            if ((i & 1) != 0) {
                enforceSerialNumber(getDataUserDeDirectory(str, i2), i3);
                if (java.util.Objects.equals(str, android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL)) {
                    enforceSerialNumber(getDataSystemDeDirectory(i2), i3);
                }
            }
            int i4 = i & 2;
            if (i4 != 0) {
                enforceSerialNumber(getDataUserCeDirectory(str, i2), i3);
                if (java.util.Objects.equals(str, android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL)) {
                    enforceSerialNumber(getDataSystemCeDirectory(i2), i3);
                }
            }
            this.mInstaller.createUserData(str, i2, i3, i);
            if (i4 != 0 && i2 == 0) {
                java.lang.String str2 = "sys.user." + i2 + ".ce_available";
                android.util.Slog.d(TAG, "Setting property: " + str2 + "=true");
                android.os.SystemProperties.set(str2, "true");
            }
        } catch (java.lang.Exception e) {
            if (z2) {
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(6, "Destroying user " + i2 + " on volume " + str + " because we failed to prepare: " + e);
                destroyUserDataLI(str, i2, i);
            } else {
                com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(6, "Failed to prepare user " + i2 + " on volume " + str + ": " + e);
            }
            if (z) {
                prepareUserDataLI(str, userInfo, i | 1, false);
                return;
            }
            try {
                android.util.Log.e(TAG, "prepareUserData failed for user " + i2, e);
                if (z2 && i2 == 0 && str == null) {
                    android.os.RecoverySystem.rebootPromptAndWipeUserData(this.mContext, "failed to prepare internal storage for system user");
                }
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException("error rebooting into recovery", e2);
            }
        }
    }

    void destroyUserData(int i, int i2) {
        synchronized (this.mInstallLock) {
            try {
                java.util.Iterator it = ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).getWritablePrivateVolumes().iterator();
                while (it.hasNext()) {
                    java.lang.String fsUuid = ((android.os.storage.VolumeInfo) it.next()).getFsUuid();
                    if (fsUuid != null) {
                        destroyUserDataLI(fsUuid, i, i2);
                    }
                }
                destroyUserDataLI(null, i, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void destroyUserDataLI(java.lang.String str, int i, int i2) {
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
        try {
            this.mInstaller.destroyUserData(str, i, i2);
            if (java.util.Objects.equals(str, android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL)) {
                if ((i2 & 1) != 0) {
                    android.os.FileUtils.deleteContentsAndDir(getUserSystemDirectory(i));
                    android.os.FileUtils.deleteContents(getDataSystemDeDirectory(i));
                }
                if ((i2 & 2) != 0) {
                    android.os.FileUtils.deleteContents(getDataSystemCeDirectory(i));
                }
            }
            storageManager.destroyUserStorage(str, i, i2);
        } catch (java.lang.Exception e) {
            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Failed to destroy user " + i + " on volume " + str + ": " + e);
        }
    }

    void reconcileUsers(java.lang.String str, java.util.List<android.content.pm.UserInfo> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Collections.addAll(arrayList, android.os.FileUtils.listFilesOrEmpty(android.os.Environment.getDataUserDeDirectory(str)));
        java.util.Collections.addAll(arrayList, android.os.FileUtils.listFilesOrEmpty(android.os.Environment.getDataUserCeDirectory(str)));
        java.util.Collections.addAll(arrayList, android.os.FileUtils.listFilesOrEmpty(android.os.Environment.getDataSystemDeDirectory()));
        java.util.Collections.addAll(arrayList, android.os.FileUtils.listFilesOrEmpty(android.os.Environment.getDataSystemCeDirectory()));
        java.util.Collections.addAll(arrayList, android.os.FileUtils.listFilesOrEmpty(android.os.Environment.getDataMiscCeDirectory()));
        reconcileUsers(str, list, arrayList);
    }

    @com.android.internal.annotations.VisibleForTesting
    void reconcileUsers(java.lang.String str, java.util.List<android.content.pm.UserInfo> list, java.util.List<java.io.File> list2) {
        int size = list.size();
        android.util.SparseArray sparseArray = new android.util.SparseArray(size);
        for (int i = 0; i < size; i++) {
            android.content.pm.UserInfo userInfo = list.get(i);
            sparseArray.put(userInfo.id, userInfo);
        }
        for (java.io.File file : list2) {
            if (file.isDirectory()) {
                try {
                    int parseInt = java.lang.Integer.parseInt(file.getName());
                    android.content.pm.UserInfo userInfo2 = (android.content.pm.UserInfo) sparseArray.get(parseInt);
                    boolean z = true;
                    if (userInfo2 == null) {
                        com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Destroying user directory " + file + " because no matching user was found");
                    } else {
                        try {
                            enforceSerialNumber(file, userInfo2.serialNumber);
                            z = false;
                        } catch (java.io.IOException e) {
                            com.android.server.pm.PackageManagerServiceUtils.logCriticalInfo(5, "Destroying user directory " + file + " because we failed to enforce serial number: " + e);
                        }
                    }
                    if (z) {
                        synchronized (this.mInstallLock) {
                            destroyUserDataLI(str, parseInt, 3);
                        }
                    } else {
                        continue;
                    }
                } catch (java.lang.NumberFormatException e2) {
                    android.util.Slog.w(TAG, "Invalid user directory " + file);
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getDataMiscCeDirectory(int i) {
        return android.os.Environment.getDataMiscCeDirectory(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getDataSystemCeDirectory(int i) {
        return android.os.Environment.getDataSystemCeDirectory(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getDataMiscDeDirectory(int i) {
        return android.os.Environment.getDataMiscDeDirectory(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getUserSystemDirectory(int i) {
        return android.os.Environment.getUserSystemDirectory(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getDataUserCeDirectory(java.lang.String str, int i) {
        return android.os.Environment.getDataUserCeDirectory(str, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getDataSystemDeDirectory(int i) {
        return android.os.Environment.getDataSystemDeDirectory(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.io.File getDataUserDeDirectory(java.lang.String str, int i) {
        return android.os.Environment.getDataUserDeDirectory(str, i);
    }

    void enforceSerialNumber(java.io.File file, int i) throws java.io.IOException {
        int serialNumber = getSerialNumber(file);
        android.util.Slog.v(TAG, "Found " + file + " with serial number " + serialNumber);
        if (serialNumber == -1) {
            android.util.Slog.d(TAG, "Serial number missing on " + file + "; assuming current is valid");
            try {
                setSerialNumber(file, i);
                return;
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed to set serial number on " + file, e);
                return;
            }
        }
        if (serialNumber != i) {
            throw new java.io.IOException("Found serial number " + serialNumber + " doesn't match expected " + i);
        }
    }

    private static void setSerialNumber(java.io.File file, int i) throws java.io.IOException {
        try {
            android.system.Os.setxattr(file.getAbsolutePath(), XATTR_SERIAL, java.lang.Integer.toString(i).getBytes(java.nio.charset.StandardCharsets.UTF_8), android.system.OsConstants.XATTR_CREATE);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getSerialNumber(java.io.File file) throws java.io.IOException {
        try {
            java.lang.String str = new java.lang.String(android.system.Os.getxattr(file.getAbsolutePath(), XATTR_SERIAL));
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
                throw new java.io.IOException("Bad serial number: " + str);
            }
        } catch (android.system.ErrnoException e2) {
            if (e2.errno == android.system.OsConstants.ENODATA) {
                return -1;
            }
            throw e2.rethrowAsIOException();
        }
    }
}
