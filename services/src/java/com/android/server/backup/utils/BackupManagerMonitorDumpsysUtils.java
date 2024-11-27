package com.android.server.backup.utils;

/* loaded from: classes.dex */
public class BackupManagerMonitorDumpsysUtils {
    private static final java.lang.String BACKUP_PERSISTENT_DIR = "backup";
    private static final long BMM_FILE_SIZE_LIMIT_BYTES = 25600000;
    private static final java.lang.String INITIAL_SETUP_TIMESTAMP_KEY = "initialSetupTimestamp";
    private static final long LOGS_RETENTION_PERIOD_MILLISEC = java.util.concurrent.TimeUnit.DAYS.toMillis(1) * 60;
    private static final java.lang.String TAG = "BackupManagerMonitorDumpsysUtils";
    private boolean mIsAfterRetentionPeriod;
    private boolean mIsAfterRetentionPeriodCached = false;
    private boolean mIsFileLargerThanSizeLimit = false;

    public void parseBackupManagerMonitorRestoreEventForDumpsys(android.os.Bundle bundle) {
        if (isAfterRetentionPeriod() || bundle == null || !isOpTypeRestore(bundle)) {
            return;
        }
        if (!bundle.containsKey("android.app.backup.extra.LOG_EVENT_ID") || !bundle.containsKey("android.app.backup.extra.LOG_EVENT_CATEGORY")) {
            android.util.Slog.w(TAG, "Event id and category are not optional fields.");
            return;
        }
        java.io.File bMMEventsFile = getBMMEventsFile();
        if (bMMEventsFile.length() == 0) {
            recordSetUpTimestamp();
        }
        if (isFileLargerThanSizeLimit(bMMEventsFile)) {
            return;
        }
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(bMMEventsFile, true);
            try {
                com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(fileOutputStream);
                try {
                    bundle.getInt("android.app.backup.extra.LOG_EVENT_CATEGORY");
                    int i = bundle.getInt("android.app.backup.extra.LOG_EVENT_ID");
                    if (i == 52 && !hasAgentLogging(bundle)) {
                        fastPrintWriter.close();
                        fileOutputStream.close();
                        return;
                    }
                    fastPrintWriter.println("[" + timestamp() + "] - " + getId(i));
                    if (bundle.containsKey("android.app.backup.extra.LOG_EVENT_PACKAGE_NAME")) {
                        fastPrintWriter.println("\tPackage: " + bundle.getString("android.app.backup.extra.LOG_EVENT_PACKAGE_NAME"));
                    }
                    addAgentLogsIfAvailable(bundle, fastPrintWriter);
                    addExtrasIfAvailable(bundle, fastPrintWriter);
                    fastPrintWriter.close();
                    fileOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "IO Exception when writing BMM events to file: " + e);
        }
    }

    private boolean hasAgentLogging(android.os.Bundle bundle) {
        if (bundle.containsKey("android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS")) {
            return !bundle.getParcelableArrayList("android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS").isEmpty();
        }
        return false;
    }

    private void addAgentLogsIfAvailable(android.os.Bundle bundle, java.io.PrintWriter printWriter) {
        if (hasAgentLogging(bundle)) {
            printWriter.println("\tAgent Logs:");
            java.util.Iterator it = bundle.getParcelableArrayList("android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS").iterator();
            while (it.hasNext()) {
                android.app.backup.BackupRestoreEventLogger.DataTypeResult dataTypeResult = (android.app.backup.BackupRestoreEventLogger.DataTypeResult) it.next();
                int failCount = dataTypeResult.getFailCount() + dataTypeResult.getSuccessCount();
                printWriter.println("\t\tData Type: " + dataTypeResult.getDataType());
                printWriter.println("\t\t\tItem restored: " + dataTypeResult.getSuccessCount() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + failCount);
                for (java.util.Map.Entry entry : dataTypeResult.getErrors().entrySet()) {
                    printWriter.println("\t\t\tAgent Error - Category: " + ((java.lang.String) entry.getKey()) + ", Count: " + entry.getValue());
                }
            }
        }
    }

    private void addExtrasIfAvailable(android.os.Bundle bundle, java.io.PrintWriter printWriter) {
        if (bundle.getInt("android.app.backup.extra.LOG_EVENT_ID") == 27) {
            if (bundle.containsKey("android.app.backup.extra.LOG_RESTORE_ANYWAY")) {
                printWriter.println("\t\tPackage supports RestoreAnyVersion: " + bundle.getBoolean("android.app.backup.extra.LOG_RESTORE_ANYWAY"));
            }
            if (bundle.containsKey("android.app.backup.extra.LOG_RESTORE_VERSION")) {
                printWriter.println("\t\tPackage version on source: " + bundle.getLong("android.app.backup.extra.LOG_RESTORE_VERSION"));
            }
            if (bundle.containsKey("android.app.backup.extra.LOG_EVENT_PACKAGE_FULL_VERSION")) {
                printWriter.println("\t\tPackage version on target: " + bundle.getLong("android.app.backup.extra.LOG_EVENT_PACKAGE_FULL_VERSION"));
            }
        }
        if (bundle.getInt("android.app.backup.extra.LOG_EVENT_ID") == 72) {
            if (bundle.containsKey("android.app.backup.extra.V_TO_U_DENYLIST")) {
                printWriter.println("\t\tV to U Denylist : " + bundle.getString("android.app.backup.extra.V_TO_U_DENYLIST"));
            }
            if (bundle.containsKey("android.app.backup.extra.V_TO_U_ALLOWLIST")) {
                printWriter.println("\t\tV to U Allowllist : " + bundle.getString("android.app.backup.extra.V_TO_U_ALLOWLIST"));
            }
        }
    }

    public java.io.File getBMMEventsFile() {
        return new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "backup"), "bmmevents.txt");
    }

    public boolean isFileLargerThanSizeLimit(java.io.File file) {
        if (!this.mIsFileLargerThanSizeLimit) {
            this.mIsFileLargerThanSizeLimit = file.length() > getBMMEventsFileSizeLimit();
        }
        return this.mIsFileLargerThanSizeLimit;
    }

    private java.lang.String timestamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date(java.lang.System.currentTimeMillis()));
    }

    private java.lang.String getCategory(int i) {
        switch (i) {
            case 1:
                return "Transport";
            case 2:
                return "Agent";
            case 3:
                return "Backup Manager Policy";
            default:
                return "Unknown category code: " + i;
        }
    }

    private java.lang.String getId(int i) {
        switch (i) {
            case 4:
                return "Full backup cancel";
            case 5:
                return "Illegal key";
            case 6:
            case 8:
            case 17:
            case 20:
            case 32:
            case 33:
            default:
                return "Unknown log event ID: " + i;
            case 7:
                return "No data to send";
            case 9:
                return "Package ineligible";
            case 10:
                return "Package key-value participant";
            case 11:
                return "Package stopped";
            case 12:
                return "Package not found";
            case 13:
                return "Backup disabled";
            case 14:
                return "Device not provisioned";
            case 15:
                return "Package transport not present";
            case 16:
                return "Error preflight";
            case 18:
                return "Quota hit preflight";
            case 19:
                return "Exception full backup";
            case 21:
                return "Key-value backup cancel";
            case 22:
                return "No restore metadata available";
            case 23:
                return "No PM metadata received";
            case 24:
                return "PM agent has no metadata";
            case 25:
                return "Lost transport";
            case 26:
                return "Package not present";
            case 27:
                return "Restore version higher";
            case 28:
                return "App has no agent";
            case 29:
                return "Signature mismatch";
            case 30:
                return "Can't find agent";
            case 31:
                return "Key-value restore timeout";
            case 34:
                return "Restore any version";
            case 35:
                return "Versions match";
            case 36:
                return "Version of backup older";
            case 37:
                return "Full restore signature mismatch";
            case 38:
                return "System app no agent";
            case 39:
                return "Full restore allow backup false";
            case 40:
                return "APK not installed";
            case 41:
                return "Cannot restore without APK";
            case 42:
                return "Missing signature";
            case 43:
                return "Expected different package";
            case 44:
                return "Unknown version";
            case 45:
                return "Full restore timeout";
            case 46:
                return "Corrupt manifest";
            case 47:
                return "Widget metadata mismatch";
            case 48:
                return "Widget unknown version";
            case 49:
                return "No packages";
            case 50:
                return "Transport is null";
            case 51:
                return "Transport non-incremental backup required";
            case 52:
                return "Agent logging results";
            case 53:
                return "Start system restore";
            case 54:
                return "Start restore at install";
            case 55:
                return "Transport error during start restore";
            case 56:
                return "Cannot get next package name";
            case 57:
                return "Unknown restore type";
            case 58:
                return "KV restore";
            case 59:
                return "Full restore";
            case 60:
                return "No next restore target";
            case 61:
                return "KV agent error";
            case 62:
                return "Package restore finished";
            case 63:
                return "Transport error KV restore";
            case 64:
                return "No feeder thread";
            case 65:
                return "Full agent error";
            case 66:
                return "Transport error full restore";
            case 67:
                return "Start package restore";
            case 68:
                return "Restore complete";
            case 69:
                return "Agent failure";
            case 70:
                return "V to U restore pkg eligible";
            case 71:
                return "V to U restore pkg not eligible";
            case 72:
                return "V to U restore lists";
        }
    }

    private boolean isOpTypeRestore(android.os.Bundle bundle) {
        switch (bundle.getInt("android.app.backup.extra.OPERATION_TYPE", -1)) {
            case 1:
                return true;
            default:
                return false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void recordSetUpTimestamp() {
        java.io.File setUpDateFile = getSetUpDateFile();
        if (setUpDateFile.length() == 0) {
            try {
                java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(setUpDateFile, true);
                try {
                    com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(fileOutputStream);
                    try {
                        fastPrintWriter.println(java.lang.System.currentTimeMillis());
                        fastPrintWriter.close();
                        fileOutputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "An error occurred while recording the setup date: " + e.getMessage());
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.lang.String getSetUpDate() {
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(getSetUpDateFile());
            try {
                java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(fileInputStream);
                try {
                    java.io.BufferedReader bufferedReader = new java.io.BufferedReader(inputStreamReader);
                    try {
                        java.lang.String readLine = bufferedReader.readLine();
                        bufferedReader.close();
                        inputStreamReader.close();
                        fileInputStream.close();
                        return readLine;
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "An error occurred while reading the date: " + e.getMessage());
            return "Could not retrieve setup date";
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isDateAfterNMillisec(long j, long j2, long j3) {
        return j > j2 || j2 - j >= j3;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isAfterRetentionPeriod() {
        if (this.mIsAfterRetentionPeriodCached) {
            return this.mIsAfterRetentionPeriod;
        }
        if (getSetUpDateFile().length() == 0) {
            this.mIsAfterRetentionPeriod = false;
            this.mIsAfterRetentionPeriodCached = true;
            return false;
        }
        try {
            this.mIsAfterRetentionPeriod = isDateAfterNMillisec(java.lang.Long.parseLong(getSetUpDate()), java.lang.System.currentTimeMillis(), getRetentionPeriodInMillisec());
            this.mIsAfterRetentionPeriodCached = true;
            return this.mIsAfterRetentionPeriod;
        } catch (java.lang.NumberFormatException e) {
            this.mIsAfterRetentionPeriod = true;
            this.mIsAfterRetentionPeriodCached = true;
            return true;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.io.File getSetUpDateFile() {
        return new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "backup"), "initialSetupTimestamp.txt");
    }

    @com.android.internal.annotations.VisibleForTesting
    long getRetentionPeriodInMillisec() {
        return LOGS_RETENTION_PERIOD_MILLISEC;
    }

    @com.android.internal.annotations.VisibleForTesting
    long getBMMEventsFileSizeLimit() {
        return BMM_FILE_SIZE_LIMIT_BYTES;
    }

    public boolean deleteExpiredBMMEvents() {
        try {
            if (isAfterRetentionPeriod()) {
                java.io.File bMMEventsFile = getBMMEventsFile();
                if (bMMEventsFile.exists()) {
                    if (bMMEventsFile.delete()) {
                        android.util.Slog.i(TAG, "Deleted expired BMM Events");
                    } else {
                        android.util.Slog.e(TAG, "Unable to delete expired BMM Events");
                    }
                }
                return true;
            }
            return false;
        } catch (java.lang.Exception e) {
            return true;
        }
    }
}
