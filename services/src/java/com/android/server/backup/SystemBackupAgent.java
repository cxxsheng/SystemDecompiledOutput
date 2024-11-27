package com.android.server.backup;

/* loaded from: classes.dex */
public class SystemBackupAgent extends android.app.backup.BackupAgentHelper {
    private static final java.lang.String APP_GENDER_HELPER = "app_gender";
    private static final java.lang.String PEOPLE_HELPER = "people";
    private static final java.lang.String SLICES_HELPER = "slices";
    private static final java.lang.String TAG = "SystemBackupAgent";
    private static final java.lang.String WALLPAPER_HELPER = "wallpaper";
    private static final java.lang.String WALLPAPER_IMAGE_FILENAME = "wallpaper";
    private static final java.lang.String WALLPAPER_IMAGE_KEY = "/data/data/com.android.settings/files/wallpaper";
    private android.app.backup.BackupRestoreEventLogger mLogger;
    private static final java.lang.String WALLPAPER_IMAGE_DIR = android.os.Environment.getUserSystemDirectory(0).getAbsolutePath();
    public static final java.lang.String WALLPAPER_IMAGE = new java.io.File(android.os.Environment.getUserSystemDirectory(0), "wallpaper").getAbsolutePath();
    private static final java.lang.String WALLPAPER_INFO_DIR = android.os.Environment.getUserSystemDirectory(0).getAbsolutePath();
    private static final java.lang.String WALLPAPER_INFO_FILENAME = "wallpaper_info.xml";
    public static final java.lang.String WALLPAPER_INFO = new java.io.File(android.os.Environment.getUserSystemDirectory(0), WALLPAPER_INFO_FILENAME).getAbsolutePath();
    private static final java.lang.String PERMISSION_HELPER = "permissions";
    private static final java.lang.String NOTIFICATION_HELPER = "notifications";
    private static final java.lang.String SYNC_SETTINGS_HELPER = "account_sync_settings";
    private static final java.lang.String APP_LOCALES_HELPER = "app_locales";
    private static final java.lang.String COMPANION_HELPER = "companion";
    private static final java.lang.String NETWORK_POLICY_HELPER = "network_policy";
    private static final java.util.Set<java.lang.String> sEligibleHelpersForProfileUser = com.google.android.collect.Sets.newArraySet(new java.lang.String[]{PERMISSION_HELPER, NOTIFICATION_HELPER, SYNC_SETTINGS_HELPER, APP_LOCALES_HELPER, COMPANION_HELPER, NETWORK_POLICY_HELPER});
    private static final java.lang.String ACCOUNT_MANAGER_HELPER = "account_manager";
    private static final java.lang.String USAGE_STATS_HELPER = "usage_stats";
    private static final java.lang.String PREFERRED_HELPER = "preferred_activities";
    private static final java.lang.String SHORTCUT_MANAGER_HELPER = "shortcut_manager";
    private static final java.util.Set<java.lang.String> sEligibleHelpersForNonSystemUser = com.android.server.backup.SetUtils.union(sEligibleHelpersForProfileUser, com.google.android.collect.Sets.newArraySet(new java.lang.String[]{ACCOUNT_MANAGER_HELPER, USAGE_STATS_HELPER, PREFERRED_HELPER, SHORTCUT_MANAGER_HELPER}));
    private int mUserId = 0;
    private boolean mIsProfileUser = false;

    public void onCreate(android.os.UserHandle userHandle, int i) {
        super.onCreate(userHandle, i);
        this.mLogger = getBackupRestoreEventLogger();
        this.mUserId = userHandle.getIdentifier();
        if (this.mUserId != 0) {
            this.mIsProfileUser = ((android.os.UserManager) createContextAsUser(userHandle, 0).getSystemService(android.os.UserManager.class)).isProfile();
        }
        addHelperIfEligibleForUser(SYNC_SETTINGS_HELPER, new com.android.server.backup.AccountSyncSettingsBackupHelper(this, this.mUserId));
        addHelperIfEligibleForUser(PREFERRED_HELPER, new com.android.server.backup.PreferredActivityBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(NOTIFICATION_HELPER, new com.android.server.backup.NotificationBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(PERMISSION_HELPER, new com.android.server.backup.PermissionBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(USAGE_STATS_HELPER, new com.android.server.backup.UsageStatsBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(SHORTCUT_MANAGER_HELPER, new com.android.server.backup.ShortcutBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(ACCOUNT_MANAGER_HELPER, new com.android.server.backup.AccountManagerBackupHelper(this.mUserId));
        if (!getPackageManager().hasSystemFeature("android.software.slices_disabled")) {
            addHelperIfEligibleForUser(SLICES_HELPER, new com.android.server.backup.SliceBackupHelper(this));
        }
        addHelperIfEligibleForUser(PEOPLE_HELPER, new com.android.server.backup.PeopleBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(APP_LOCALES_HELPER, new com.android.server.backup.AppSpecificLocalesBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(APP_GENDER_HELPER, new com.android.server.backup.AppGrammaticalGenderBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(COMPANION_HELPER, new com.android.server.backup.CompanionBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(NETWORK_POLICY_HELPER, new com.android.server.backup.NetworkPolicyBackupHelper(this.mUserId));
    }

    @Override // android.app.backup.BackupAgent
    public void onFullBackup(android.app.backup.FullBackupDataOutput fullBackupDataOutput) throws java.io.IOException {
    }

    @Override // android.app.backup.BackupAgentHelper, android.app.backup.BackupAgent
    public void onRestore(android.app.backup.BackupDataInput backupDataInput, int i, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        addHelper("wallpaper", new android.app.backup.WallpaperBackupHelper(this, new java.lang.String[]{WALLPAPER_IMAGE_KEY}));
        addHelper("system_files", new android.app.backup.WallpaperBackupHelper(this, new java.lang.String[]{WALLPAPER_IMAGE_KEY}));
        super.onRestore(backupDataInput, i, parcelFileDescriptor);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onRestoreFile(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str, java.lang.String str2, long j2, long j3) throws java.io.IOException {
        boolean z;
        java.io.File file;
        android.app.IWallpaperManager service;
        android.util.Slog.i(TAG, "Restoring file domain=" + str + " path=" + str2);
        if (str.equals(com.android.server.wm.ActivityTaskManagerService.DUMP_RECENTS_SHORT_CMD)) {
            z = true;
            if (str2.equals(WALLPAPER_INFO_FILENAME)) {
                file = new java.io.File(WALLPAPER_INFO);
            } else if (str2.equals("wallpaper")) {
                file = new java.io.File(WALLPAPER_IMAGE);
            }
            if (file == null) {
                try {
                    android.util.Slog.w(TAG, "Skipping unrecognized system file: [ " + str + " : " + str2 + " ]");
                } catch (java.io.IOException e) {
                    if (z) {
                        new java.io.File(WALLPAPER_IMAGE).delete();
                        new java.io.File(WALLPAPER_INFO).delete();
                        return;
                    }
                    return;
                }
            }
            android.app.backup.FullBackup.restoreFile(parcelFileDescriptor, j, i, j2, j3, file);
            if (!z && (service = android.os.ServiceManager.getService("wallpaper")) != null) {
                try {
                    service.settingsRestored();
                    return;
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(TAG, "Couldn't restore settings\n" + e2);
                    return;
                }
            }
        }
        z = false;
        file = null;
        if (file == null) {
        }
        android.app.backup.FullBackup.restoreFile(parcelFileDescriptor, j, i, j2, j3, file);
        if (!z) {
        }
    }

    private void addHelperIfEligibleForUser(java.lang.String str, android.app.backup.BackupHelperWithLogger backupHelperWithLogger) {
        if (isHelperEligibleForUser(str)) {
            addHelper(str, backupHelperWithLogger);
            if (com.android.server.backup.Flags.enableMetricsSystemBackupAgents()) {
                backupHelperWithLogger.setLogger(this.mLogger);
            }
        }
    }

    private boolean isHelperEligibleForUser(java.lang.String str) {
        if (this.mUserId == 0) {
            return true;
        }
        if (this.mIsProfileUser) {
            return sEligibleHelpersForProfileUser.contains(str);
        }
        return sEligibleHelpersForNonSystemUser.contains(str);
    }
}
