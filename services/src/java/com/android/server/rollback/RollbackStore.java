package com.android.server.rollback;

/* loaded from: classes2.dex */
class RollbackStore {
    private static final java.lang.String TAG = "RollbackManager";
    private final java.io.File mRollbackDataDir;
    private final java.io.File mRollbackHistoryDir;

    RollbackStore(java.io.File file, java.io.File file2) {
        this.mRollbackDataDir = file;
        this.mRollbackHistoryDir = file2;
    }

    private static java.util.List<com.android.server.rollback.Rollback> loadRollbacks(java.io.File file) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        file.mkdirs();
        for (java.io.File file2 : file.listFiles()) {
            if (file2.isDirectory()) {
                try {
                    arrayList.add(loadRollback(file2));
                } catch (java.io.IOException e) {
                    android.util.Slog.e(TAG, "Unable to read rollback at " + file2, e);
                    removeFile(file2);
                }
            }
        }
        return arrayList;
    }

    java.util.List<com.android.server.rollback.Rollback> loadRollbacks() {
        return loadRollbacks(this.mRollbackDataDir);
    }

    java.util.List<com.android.server.rollback.Rollback> loadHistorialRollbacks() {
        return loadRollbacks(this.mRollbackHistoryDir);
    }

    @android.annotation.NonNull
    private static java.util.List<java.lang.Integer> toIntList(@android.annotation.NonNull org.json.JSONArray jSONArray) throws org.json.JSONException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(java.lang.Integer.valueOf(jSONArray.getInt(i)));
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private static org.json.JSONArray fromIntList(@android.annotation.NonNull java.util.List<java.lang.Integer> list) {
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        for (int i = 0; i < list.size(); i++) {
            jSONArray.put(list.get(i));
        }
        return jSONArray;
    }

    @android.annotation.NonNull
    private static org.json.JSONArray convertToJsonArray(@android.annotation.NonNull java.util.List<android.content.rollback.PackageRollbackInfo.RestoreInfo> list) throws org.json.JSONException {
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        for (android.content.rollback.PackageRollbackInfo.RestoreInfo restoreInfo : list) {
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            jSONObject.put("userId", restoreInfo.userId);
            jSONObject.put("appId", restoreInfo.appId);
            jSONObject.put("seInfo", restoreInfo.seInfo);
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    @android.annotation.NonNull
    private static java.util.ArrayList<android.content.rollback.PackageRollbackInfo.RestoreInfo> convertToRestoreInfoArray(@android.annotation.NonNull org.json.JSONArray jSONArray) throws org.json.JSONException {
        java.util.ArrayList<android.content.rollback.PackageRollbackInfo.RestoreInfo> arrayList = new java.util.ArrayList<>();
        for (int i = 0; i < jSONArray.length(); i++) {
            org.json.JSONObject jSONObject = jSONArray.getJSONObject(i);
            arrayList.add(new android.content.rollback.PackageRollbackInfo.RestoreInfo(jSONObject.getInt("userId"), jSONObject.getInt("appId"), jSONObject.getString("seInfo")));
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private static org.json.JSONArray extensionVersionsToJson(android.util.SparseIntArray sparseIntArray) throws org.json.JSONException {
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        for (int i = 0; i < sparseIntArray.size(); i++) {
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            jSONObject.put("sdkVersion", sparseIntArray.keyAt(i));
            jSONObject.put("extensionVersion", sparseIntArray.valueAt(i));
            jSONArray.put(jSONObject);
        }
        return jSONArray;
    }

    @android.annotation.NonNull
    private static android.util.SparseIntArray extensionVersionsFromJson(org.json.JSONArray jSONArray) throws org.json.JSONException {
        if (jSONArray == null) {
            return new android.util.SparseIntArray(0);
        }
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            org.json.JSONObject jSONObject = jSONArray.getJSONObject(i);
            sparseIntArray.append(jSONObject.getInt("sdkVersion"), jSONObject.getInt("extensionVersion"));
        }
        return sparseIntArray;
    }

    private static org.json.JSONObject rollbackInfoToJson(android.content.rollback.RollbackInfo rollbackInfo) throws org.json.JSONException {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        jSONObject.put("rollbackId", rollbackInfo.getRollbackId());
        jSONObject.put("packages", toJson((java.util.List<android.content.rollback.PackageRollbackInfo>) rollbackInfo.getPackages()));
        jSONObject.put("isStaged", rollbackInfo.isStaged());
        jSONObject.put("causePackages", versionedPackagesToJson(rollbackInfo.getCausePackages()));
        jSONObject.put("committedSessionId", rollbackInfo.getCommittedSessionId());
        if (android.content.pm.Flags.recoverabilityDetection()) {
            jSONObject.put("rollbackImpactLevel", rollbackInfo.getRollbackImpactLevel());
        }
        return jSONObject;
    }

    private static android.content.rollback.RollbackInfo rollbackInfoFromJson(org.json.JSONObject jSONObject) throws org.json.JSONException {
        android.content.rollback.RollbackInfo rollbackInfo = new android.content.rollback.RollbackInfo(jSONObject.getInt("rollbackId"), packageRollbackInfosFromJson(jSONObject.getJSONArray("packages")), jSONObject.getBoolean("isStaged"), versionedPackagesFromJson(jSONObject.getJSONArray("causePackages")), jSONObject.getInt("committedSessionId"));
        if (android.content.pm.Flags.recoverabilityDetection()) {
            rollbackInfo.setRollbackImpactLevel(jSONObject.optInt("rollbackImpactLevel", 0));
        }
        return rollbackInfo;
    }

    com.android.server.rollback.Rollback createNonStagedRollback(int i, int i2, int i3, java.lang.String str, int[] iArr, android.util.SparseIntArray sparseIntArray) {
        return new com.android.server.rollback.Rollback(i, new java.io.File(this.mRollbackDataDir, java.lang.Integer.toString(i)), i2, false, i3, str, iArr, sparseIntArray);
    }

    com.android.server.rollback.Rollback createStagedRollback(int i, int i2, int i3, java.lang.String str, int[] iArr, android.util.SparseIntArray sparseIntArray) {
        return new com.android.server.rollback.Rollback(i, new java.io.File(this.mRollbackDataDir, java.lang.Integer.toString(i)), i2, true, i3, str, iArr, sparseIntArray);
    }

    private static boolean isLinkPossible(java.io.File file, java.io.File file2) {
        try {
            return android.system.Os.stat(file.getAbsolutePath()).st_dev == android.system.Os.stat(file2.getAbsolutePath()).st_dev;
        } catch (android.system.ErrnoException e) {
            return false;
        }
    }

    static void backupPackageCodePath(com.android.server.rollback.Rollback rollback, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        java.io.File file = new java.io.File(str2);
        java.io.File file2 = new java.io.File(rollback.getBackupDir(), str);
        file2.mkdirs();
        java.io.File file3 = new java.io.File(file2, file.getName());
        boolean isLinkPossible = isLinkPossible(file, file2);
        boolean z = true;
        boolean z2 = !isLinkPossible;
        if (!z2) {
            try {
                android.system.Os.link(file.getAbsolutePath(), file3.getAbsolutePath());
            } catch (android.system.ErrnoException e) {
                if (android.os.SystemProperties.getBoolean("persist.rollback.is_test", false)) {
                    throw new java.io.IOException(e);
                }
            }
        }
        z = z2;
        if (z) {
            java.nio.file.Files.copy(file.toPath(), file3.toPath(), new java.nio.file.CopyOption[0]);
        }
    }

    static java.io.File[] getPackageCodePaths(com.android.server.rollback.Rollback rollback, java.lang.String str) {
        java.io.File[] listFiles = new java.io.File(rollback.getBackupDir(), str).listFiles();
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        return listFiles;
    }

    static void deletePackageCodePaths(com.android.server.rollback.Rollback rollback) {
        java.util.Iterator it = rollback.info.getPackages().iterator();
        while (it.hasNext()) {
            removeFile(new java.io.File(rollback.getBackupDir(), ((android.content.rollback.PackageRollbackInfo) it.next()).getPackageName()));
        }
    }

    private static void saveRollback(com.android.server.rollback.Rollback rollback, java.io.File file) {
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(file, "rollback.json"));
        java.io.FileOutputStream fileOutputStream = null;
        try {
            file.mkdirs();
            org.json.JSONObject jSONObject = new org.json.JSONObject();
            jSONObject.put("info", rollbackInfoToJson(rollback.info));
            jSONObject.put(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP, rollback.getTimestamp().toString());
            if (android.content.pm.Flags.rollbackLifetime()) {
                jSONObject.put("rollbackLifetimeMillis", rollback.getRollbackLifetimeMillis());
            }
            jSONObject.put("originalSessionId", rollback.getOriginalSessionId());
            jSONObject.put("state", rollback.getStateAsString());
            jSONObject.put("stateDescription", rollback.getStateDescription());
            jSONObject.put("restoreUserDataInProgress", rollback.isRestoreUserDataInProgress());
            jSONObject.put("userId", rollback.getUserId());
            jSONObject.putOpt("installerPackageName", rollback.getInstallerPackageName());
            jSONObject.putOpt("extensionVersions", extensionVersionsToJson(rollback.getExtensionVersions()));
            fileOutputStream = atomicFile.startWrite();
            fileOutputStream.write(jSONObject.toString().getBytes());
            fileOutputStream.flush();
            atomicFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException | org.json.JSONException e) {
            android.util.Slog.e(TAG, "Unable to save rollback for: " + rollback.info.getRollbackId(), e);
            if (fileOutputStream != null) {
                atomicFile.failWrite(fileOutputStream);
            }
        }
    }

    static void saveRollback(com.android.server.rollback.Rollback rollback) {
        saveRollback(rollback, rollback.getBackupDir());
    }

    void saveRollbackToHistory(com.android.server.rollback.Rollback rollback) {
        java.lang.String hexString = java.lang.Long.toHexString(rollback.getTimestamp().getEpochSecond());
        java.lang.String num = java.lang.Integer.toString(rollback.info.getRollbackId());
        saveRollback(rollback, new java.io.File(this.mRollbackHistoryDir, num + "-" + hexString));
    }

    static void deleteRollback(com.android.server.rollback.Rollback rollback) {
        removeFile(rollback.getBackupDir());
    }

    private static com.android.server.rollback.Rollback loadRollback(java.io.File file) throws java.io.IOException {
        try {
            return rollbackFromJson(new org.json.JSONObject(libcore.io.IoUtils.readFileAsString(new java.io.File(file, "rollback.json").getAbsolutePath())), file);
        } catch (java.text.ParseException | java.time.format.DateTimeParseException | org.json.JSONException e) {
            throw new java.io.IOException(e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static com.android.server.rollback.Rollback rollbackFromJson(org.json.JSONObject jSONObject, java.io.File file) throws org.json.JSONException, java.text.ParseException {
        com.android.server.rollback.Rollback rollback = new com.android.server.rollback.Rollback(rollbackInfoFromJson(jSONObject.getJSONObject("info")), file, java.time.Instant.parse(jSONObject.getString(com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.TIMESTAMP)), jSONObject.optInt("originalSessionId", jSONObject.optInt("stagedSessionId", -1)), com.android.server.rollback.Rollback.rollbackStateFromString(jSONObject.getString("state")), jSONObject.optString("stateDescription"), jSONObject.getBoolean("restoreUserDataInProgress"), jSONObject.optInt("userId", android.os.UserHandle.SYSTEM.getIdentifier()), jSONObject.optString("installerPackageName", ""), extensionVersionsFromJson(jSONObject.optJSONArray("extensionVersions")));
        if (android.content.pm.Flags.rollbackLifetime()) {
            rollback.setRollbackLifetimeMillis(jSONObject.optLong("rollbackLifetimeMillis"));
        }
        return rollback;
    }

    private static org.json.JSONObject toJson(android.content.pm.VersionedPackage versionedPackage) throws org.json.JSONException {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        jSONObject.put(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, versionedPackage.getPackageName());
        jSONObject.put("longVersionCode", versionedPackage.getLongVersionCode());
        return jSONObject;
    }

    private static android.content.pm.VersionedPackage versionedPackageFromJson(org.json.JSONObject jSONObject) throws org.json.JSONException {
        return new android.content.pm.VersionedPackage(jSONObject.getString(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME), jSONObject.getLong("longVersionCode"));
    }

    private static org.json.JSONObject toJson(android.content.rollback.PackageRollbackInfo packageRollbackInfo) throws org.json.JSONException {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        jSONObject.put("versionRolledBackFrom", toJson(packageRollbackInfo.getVersionRolledBackFrom()));
        jSONObject.put("versionRolledBackTo", toJson(packageRollbackInfo.getVersionRolledBackTo()));
        java.util.List pendingBackups = packageRollbackInfo.getPendingBackups();
        java.util.ArrayList pendingRestores = packageRollbackInfo.getPendingRestores();
        java.util.List snapshottedUsers = packageRollbackInfo.getSnapshottedUsers();
        jSONObject.put("pendingBackups", fromIntList(pendingBackups));
        jSONObject.put("pendingRestores", convertToJsonArray(pendingRestores));
        jSONObject.put("isApex", packageRollbackInfo.isApex());
        jSONObject.put("isApkInApex", packageRollbackInfo.isApkInApex());
        jSONObject.put("installedUsers", fromIntList(snapshottedUsers));
        jSONObject.put("rollbackDataPolicy", packageRollbackInfo.getRollbackDataPolicy());
        return jSONObject;
    }

    private static android.content.rollback.PackageRollbackInfo packageRollbackInfoFromJson(org.json.JSONObject jSONObject) throws org.json.JSONException {
        return new android.content.rollback.PackageRollbackInfo(versionedPackageFromJson(jSONObject.getJSONObject("versionRolledBackFrom")), versionedPackageFromJson(jSONObject.getJSONObject("versionRolledBackTo")), toIntList(jSONObject.getJSONArray("pendingBackups")), convertToRestoreInfoArray(jSONObject.getJSONArray("pendingRestores")), jSONObject.getBoolean("isApex"), jSONObject.getBoolean("isApkInApex"), toIntList(jSONObject.getJSONArray("installedUsers")), jSONObject.optInt("rollbackDataPolicy", 0));
    }

    private static org.json.JSONArray versionedPackagesToJson(java.util.List<android.content.pm.VersionedPackage> list) throws org.json.JSONException {
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        java.util.Iterator<android.content.pm.VersionedPackage> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(toJson(it.next()));
        }
        return jSONArray;
    }

    private static java.util.List<android.content.pm.VersionedPackage> versionedPackagesFromJson(org.json.JSONArray jSONArray) throws org.json.JSONException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(versionedPackageFromJson(jSONArray.getJSONObject(i)));
        }
        return arrayList;
    }

    private static org.json.JSONArray toJson(java.util.List<android.content.rollback.PackageRollbackInfo> list) throws org.json.JSONException {
        org.json.JSONArray jSONArray = new org.json.JSONArray();
        java.util.Iterator<android.content.rollback.PackageRollbackInfo> it = list.iterator();
        while (it.hasNext()) {
            jSONArray.put(toJson(it.next()));
        }
        return jSONArray;
    }

    private static java.util.List<android.content.rollback.PackageRollbackInfo> packageRollbackInfosFromJson(org.json.JSONArray jSONArray) throws org.json.JSONException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(packageRollbackInfoFromJson(jSONArray.getJSONObject(i)));
        }
        return arrayList;
    }

    private static void removeFile(java.io.File file) {
        if (file.isDirectory()) {
            for (java.io.File file2 : file.listFiles()) {
                removeFile(file2);
            }
        }
        if (file.exists()) {
            file.delete();
        }
    }
}
