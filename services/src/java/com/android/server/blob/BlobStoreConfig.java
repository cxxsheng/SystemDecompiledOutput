package com.android.server.blob;

/* loaded from: classes.dex */
class BlobStoreConfig {
    private static final java.lang.String BLOBS_DIR_NAME = "blobs";
    private static final java.lang.String BLOBS_INDEX_FILE_NAME = "blobs_index.xml";
    public static final int IDLE_JOB_ID = 191934935;
    public static final long INVALID_BLOB_ID = 0;
    public static final long INVALID_BLOB_SIZE = 0;
    private static final java.lang.String ROOT_DIR_NAME = "blobstore";
    private static final java.lang.String SESSIONS_INDEX_FILE_NAME = "sessions_index.xml";
    public static final int XML_VERSION_ADD_COMMIT_TIME = 4;
    public static final int XML_VERSION_ADD_DESC_RES_NAME = 3;
    public static final int XML_VERSION_ADD_SESSION_CREATION_TIME = 5;
    public static final int XML_VERSION_ADD_STRING_DESC = 2;
    public static final int XML_VERSION_ALLOW_ACCESS_ACROSS_USERS = 6;
    public static final int XML_VERSION_CURRENT = 6;
    public static final int XML_VERSION_INIT = 1;
    public static final java.lang.String TAG = "BlobStore";
    public static final boolean LOGV = android.util.Log.isLoggable(TAG, 2);

    BlobStoreConfig() {
    }

    public static class DeviceConfigProperties {
        public static final float DEFAULT_TOTAL_BYTES_PER_APP_LIMIT_FRACTION = 0.01f;
        public static final boolean DEFAULT_USE_REVOCABLE_FD_FOR_READS = false;
        public static final java.lang.String KEY_COMMIT_COOL_OFF_DURATION_MS = "commit_cool_off_duration_ms";
        public static final java.lang.String KEY_DELETE_ON_LAST_LEASE_DELAY_MS = "delete_on_last_lease_delay_ms";
        public static final java.lang.String KEY_IDLE_JOB_PERIOD_MS = "idle_job_period_ms";
        public static final java.lang.String KEY_LEASE_ACQUISITION_WAIT_DURATION_MS = "lease_acquisition_wait_time_ms";
        public static final java.lang.String KEY_LEASE_DESC_CHAR_LIMIT = "lease_desc_char_limit";
        public static final java.lang.String KEY_MAX_ACTIVE_SESSIONS = "max_active_sessions";
        public static final java.lang.String KEY_MAX_BLOB_ACCESS_PERMITTED_PACKAGES = "max_permitted_pks";
        public static final java.lang.String KEY_MAX_COMMITTED_BLOBS = "max_committed_blobs";
        public static final java.lang.String KEY_MAX_LEASED_BLOBS = "max_leased_blobs";
        public static final java.lang.String KEY_SESSION_EXPIRY_TIMEOUT_MS = "session_expiry_timeout_ms";
        public static final java.lang.String KEY_TOTAL_BYTES_PER_APP_LIMIT_FLOOR = "total_bytes_per_app_limit_floor";
        public static final java.lang.String KEY_TOTAL_BYTES_PER_APP_LIMIT_FRACTION = "total_bytes_per_app_limit_fraction";
        public static final java.lang.String KEY_USE_REVOCABLE_FD_FOR_READS = "use_revocable_fd_for_reads";
        public static final long DEFAULT_IDLE_JOB_PERIOD_MS = java.util.concurrent.TimeUnit.DAYS.toMillis(1);
        public static long IDLE_JOB_PERIOD_MS = DEFAULT_IDLE_JOB_PERIOD_MS;
        public static final long DEFAULT_SESSION_EXPIRY_TIMEOUT_MS = java.util.concurrent.TimeUnit.DAYS.toMillis(7);
        public static long SESSION_EXPIRY_TIMEOUT_MS = DEFAULT_SESSION_EXPIRY_TIMEOUT_MS;
        public static final long DEFAULT_TOTAL_BYTES_PER_APP_LIMIT_FLOOR = android.util.DataUnit.MEBIBYTES.toBytes(300);
        public static long TOTAL_BYTES_PER_APP_LIMIT_FLOOR = DEFAULT_TOTAL_BYTES_PER_APP_LIMIT_FLOOR;
        public static float TOTAL_BYTES_PER_APP_LIMIT_FRACTION = 0.01f;
        public static final long DEFAULT_LEASE_ACQUISITION_WAIT_DURATION_MS = java.util.concurrent.TimeUnit.HOURS.toMillis(6);
        public static long LEASE_ACQUISITION_WAIT_DURATION_MS = DEFAULT_LEASE_ACQUISITION_WAIT_DURATION_MS;
        public static final long DEFAULT_COMMIT_COOL_OFF_DURATION_MS = java.util.concurrent.TimeUnit.HOURS.toMillis(48);
        public static long COMMIT_COOL_OFF_DURATION_MS = DEFAULT_COMMIT_COOL_OFF_DURATION_MS;
        public static boolean USE_REVOCABLE_FD_FOR_READS = false;
        public static final long DEFAULT_DELETE_ON_LAST_LEASE_DELAY_MS = java.util.concurrent.TimeUnit.HOURS.toMillis(6);
        public static long DELETE_ON_LAST_LEASE_DELAY_MS = DEFAULT_DELETE_ON_LAST_LEASE_DELAY_MS;
        public static int DEFAULT_MAX_ACTIVE_SESSIONS = 250;
        public static int MAX_ACTIVE_SESSIONS = DEFAULT_MAX_ACTIVE_SESSIONS;
        public static int DEFAULT_MAX_COMMITTED_BLOBS = 1000;
        public static int MAX_COMMITTED_BLOBS = DEFAULT_MAX_COMMITTED_BLOBS;
        public static int DEFAULT_MAX_LEASED_BLOBS = 500;
        public static int MAX_LEASED_BLOBS = DEFAULT_MAX_LEASED_BLOBS;
        public static int DEFAULT_MAX_BLOB_ACCESS_PERMITTED_PACKAGES = 300;
        public static int MAX_BLOB_ACCESS_PERMITTED_PACKAGES = DEFAULT_MAX_BLOB_ACCESS_PERMITTED_PACKAGES;
        public static int DEFAULT_LEASE_DESC_CHAR_LIMIT = 300;
        public static int LEASE_DESC_CHAR_LIMIT = DEFAULT_LEASE_DESC_CHAR_LIMIT;

        /* JADX INFO: Access modifiers changed from: package-private */
        public static void refresh(final android.provider.DeviceConfig.Properties properties) {
            if (!com.android.server.blob.BlobStoreConfig.ROOT_DIR_NAME.equals(properties.getNamespace())) {
                return;
            }
            properties.getKeyset().forEach(new java.util.function.Consumer() { // from class: com.android.server.blob.BlobStoreConfig$DeviceConfigProperties$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.lambda$refresh$0(properties, (java.lang.String) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public static /* synthetic */ void lambda$refresh$0(android.provider.DeviceConfig.Properties properties, java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -1925137189:
                    if (str.equals(KEY_MAX_ACTIVE_SESSIONS)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1390984180:
                    if (str.equals(KEY_USE_REVOCABLE_FD_FOR_READS)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -409761467:
                    if (str.equals(KEY_LEASE_ACQUISITION_WAIT_DURATION_MS)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -284382148:
                    if (str.equals(KEY_MAX_LEASED_BLOBS)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 12210070:
                    if (str.equals(KEY_MAX_BLOB_ACCESS_PERMITTED_PACKAGES)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 271099507:
                    if (str.equals(KEY_COMMIT_COOL_OFF_DURATION_MS)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 964101945:
                    if (str.equals(KEY_TOTAL_BYTES_PER_APP_LIMIT_FLOOR)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1401805851:
                    if (str.equals(KEY_LEASE_DESC_CHAR_LIMIT)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1419134232:
                    if (str.equals(KEY_MAX_COMMITTED_BLOBS)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1733063605:
                    if (str.equals(KEY_TOTAL_BYTES_PER_APP_LIMIT_FRACTION)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1838729636:
                    if (str.equals(KEY_DELETE_ON_LAST_LEASE_DELAY_MS)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1903375799:
                    if (str.equals(KEY_IDLE_JOB_PERIOD_MS)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2022996583:
                    if (str.equals(KEY_SESSION_EXPIRY_TIMEOUT_MS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    IDLE_JOB_PERIOD_MS = properties.getLong(str, DEFAULT_IDLE_JOB_PERIOD_MS);
                    break;
                case 1:
                    SESSION_EXPIRY_TIMEOUT_MS = properties.getLong(str, DEFAULT_SESSION_EXPIRY_TIMEOUT_MS);
                    break;
                case 2:
                    TOTAL_BYTES_PER_APP_LIMIT_FLOOR = properties.getLong(str, DEFAULT_TOTAL_BYTES_PER_APP_LIMIT_FLOOR);
                    break;
                case 3:
                    TOTAL_BYTES_PER_APP_LIMIT_FRACTION = properties.getFloat(str, 0.01f);
                    break;
                case 4:
                    LEASE_ACQUISITION_WAIT_DURATION_MS = properties.getLong(str, DEFAULT_LEASE_ACQUISITION_WAIT_DURATION_MS);
                    break;
                case 5:
                    COMMIT_COOL_OFF_DURATION_MS = properties.getLong(str, DEFAULT_COMMIT_COOL_OFF_DURATION_MS);
                    break;
                case 6:
                    USE_REVOCABLE_FD_FOR_READS = properties.getBoolean(str, false);
                    break;
                case 7:
                    DELETE_ON_LAST_LEASE_DELAY_MS = properties.getLong(str, DEFAULT_DELETE_ON_LAST_LEASE_DELAY_MS);
                    break;
                case '\b':
                    MAX_ACTIVE_SESSIONS = properties.getInt(str, DEFAULT_MAX_ACTIVE_SESSIONS);
                    break;
                case '\t':
                    MAX_COMMITTED_BLOBS = properties.getInt(str, DEFAULT_MAX_COMMITTED_BLOBS);
                    break;
                case '\n':
                    MAX_LEASED_BLOBS = properties.getInt(str, DEFAULT_MAX_LEASED_BLOBS);
                    break;
                case 11:
                    MAX_BLOB_ACCESS_PERMITTED_PACKAGES = properties.getInt(str, DEFAULT_MAX_BLOB_ACCESS_PERMITTED_PACKAGES);
                    break;
                case '\f':
                    LEASE_DESC_CHAR_LIMIT = properties.getInt(str, DEFAULT_LEASE_DESC_CHAR_LIMIT);
                    break;
                default:
                    android.util.Slog.wtf(com.android.server.blob.BlobStoreConfig.TAG, "Unknown key in device config properties: " + str);
                    break;
            }
        }

        static void dump(android.util.IndentingPrintWriter indentingPrintWriter, android.content.Context context) {
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_IDLE_JOB_PERIOD_MS, android.util.TimeUtils.formatDuration(IDLE_JOB_PERIOD_MS), android.util.TimeUtils.formatDuration(DEFAULT_IDLE_JOB_PERIOD_MS)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_SESSION_EXPIRY_TIMEOUT_MS, android.util.TimeUtils.formatDuration(SESSION_EXPIRY_TIMEOUT_MS), android.util.TimeUtils.formatDuration(DEFAULT_SESSION_EXPIRY_TIMEOUT_MS)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_TOTAL_BYTES_PER_APP_LIMIT_FLOOR, android.text.format.Formatter.formatFileSize(context, TOTAL_BYTES_PER_APP_LIMIT_FLOOR, 8), android.text.format.Formatter.formatFileSize(context, DEFAULT_TOTAL_BYTES_PER_APP_LIMIT_FLOOR, 8)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_TOTAL_BYTES_PER_APP_LIMIT_FRACTION, java.lang.Float.valueOf(TOTAL_BYTES_PER_APP_LIMIT_FRACTION), java.lang.Float.valueOf(0.01f)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_LEASE_ACQUISITION_WAIT_DURATION_MS, android.util.TimeUtils.formatDuration(LEASE_ACQUISITION_WAIT_DURATION_MS), android.util.TimeUtils.formatDuration(DEFAULT_LEASE_ACQUISITION_WAIT_DURATION_MS)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_COMMIT_COOL_OFF_DURATION_MS, android.util.TimeUtils.formatDuration(COMMIT_COOL_OFF_DURATION_MS), android.util.TimeUtils.formatDuration(DEFAULT_COMMIT_COOL_OFF_DURATION_MS)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_USE_REVOCABLE_FD_FOR_READS, java.lang.Boolean.valueOf(USE_REVOCABLE_FD_FOR_READS), false));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_DELETE_ON_LAST_LEASE_DELAY_MS, android.util.TimeUtils.formatDuration(DELETE_ON_LAST_LEASE_DELAY_MS), android.util.TimeUtils.formatDuration(DEFAULT_DELETE_ON_LAST_LEASE_DELAY_MS)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_MAX_ACTIVE_SESSIONS, java.lang.Integer.valueOf(MAX_ACTIVE_SESSIONS), java.lang.Integer.valueOf(DEFAULT_MAX_ACTIVE_SESSIONS)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_MAX_COMMITTED_BLOBS, java.lang.Integer.valueOf(MAX_COMMITTED_BLOBS), java.lang.Integer.valueOf(DEFAULT_MAX_COMMITTED_BLOBS)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_MAX_LEASED_BLOBS, java.lang.Integer.valueOf(MAX_LEASED_BLOBS), java.lang.Integer.valueOf(DEFAULT_MAX_LEASED_BLOBS)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_MAX_BLOB_ACCESS_PERMITTED_PACKAGES, java.lang.Integer.valueOf(MAX_BLOB_ACCESS_PERMITTED_PACKAGES), java.lang.Integer.valueOf(DEFAULT_MAX_BLOB_ACCESS_PERMITTED_PACKAGES)));
            indentingPrintWriter.println(java.lang.String.format("%s: [cur: %s, def: %s]", KEY_LEASE_DESC_CHAR_LIMIT, java.lang.Integer.valueOf(LEASE_DESC_CHAR_LIMIT), java.lang.Integer.valueOf(DEFAULT_LEASE_DESC_CHAR_LIMIT)));
        }
    }

    public static void initialize(android.content.Context context) {
        android.provider.DeviceConfig.addOnPropertiesChangedListener(ROOT_DIR_NAME, context.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.blob.BlobStoreConfig$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.refresh(properties);
            }
        });
        com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.refresh(android.provider.DeviceConfig.getProperties(ROOT_DIR_NAME, new java.lang.String[0]));
    }

    public static long getIdleJobPeriodMs() {
        return com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.IDLE_JOB_PERIOD_MS;
    }

    public static boolean hasSessionExpired(long j) {
        return j < java.lang.System.currentTimeMillis() - com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.SESSION_EXPIRY_TIMEOUT_MS;
    }

    public static long getAppDataBytesLimit() {
        return java.lang.Math.max(com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.TOTAL_BYTES_PER_APP_LIMIT_FLOOR, (long) (android.os.Environment.getDataSystemDirectory().getTotalSpace() * com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.TOTAL_BYTES_PER_APP_LIMIT_FRACTION));
    }

    public static boolean hasLeaseWaitTimeElapsed(long j) {
        return j + com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.LEASE_ACQUISITION_WAIT_DURATION_MS < java.lang.System.currentTimeMillis();
    }

    public static long getAdjustedCommitTimeMs(long j, long j2) {
        if (j == 0 || hasCommitCoolOffPeriodElapsed(j)) {
            return j2;
        }
        return j;
    }

    private static boolean hasCommitCoolOffPeriodElapsed(long j) {
        return j + com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.COMMIT_COOL_OFF_DURATION_MS < java.lang.System.currentTimeMillis();
    }

    public static boolean shouldUseRevocableFdForReads() {
        return com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.USE_REVOCABLE_FD_FOR_READS;
    }

    public static long getDeletionOnLastLeaseDelayMs() {
        return com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.DELETE_ON_LAST_LEASE_DELAY_MS;
    }

    public static int getMaxActiveSessions() {
        return com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.MAX_ACTIVE_SESSIONS;
    }

    public static int getMaxCommittedBlobs() {
        return com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.MAX_COMMITTED_BLOBS;
    }

    public static int getMaxLeasedBlobs() {
        return com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.MAX_LEASED_BLOBS;
    }

    public static int getMaxPermittedPackages() {
        return com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.MAX_BLOB_ACCESS_PERMITTED_PACKAGES;
    }

    public static java.lang.CharSequence getTruncatedLeaseDescription(java.lang.CharSequence charSequence) {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        return android.text.TextUtils.trimToLengthWithEllipsis(charSequence, com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.LEASE_DESC_CHAR_LIMIT);
    }

    @android.annotation.Nullable
    public static java.io.File prepareBlobFile(long j) {
        java.io.File prepareBlobsDir = prepareBlobsDir();
        if (prepareBlobsDir == null) {
            return null;
        }
        return getBlobFile(prepareBlobsDir, j);
    }

    @android.annotation.NonNull
    public static java.io.File getBlobFile(long j) {
        return getBlobFile(getBlobsDir(), j);
    }

    @android.annotation.NonNull
    private static java.io.File getBlobFile(java.io.File file, long j) {
        return new java.io.File(file, java.lang.String.valueOf(j));
    }

    @android.annotation.Nullable
    public static java.io.File prepareBlobsDir() {
        java.io.File blobsDir = getBlobsDir(prepareBlobStoreRootDir());
        if (!blobsDir.exists() && !blobsDir.mkdir()) {
            android.util.Slog.e(TAG, "Failed to mkdir(): " + blobsDir);
            return null;
        }
        return blobsDir;
    }

    @android.annotation.NonNull
    public static java.io.File getBlobsDir() {
        return getBlobsDir(getBlobStoreRootDir());
    }

    @android.annotation.NonNull
    private static java.io.File getBlobsDir(java.io.File file) {
        return new java.io.File(file, BLOBS_DIR_NAME);
    }

    @android.annotation.Nullable
    public static java.io.File prepareSessionIndexFile() {
        java.io.File prepareBlobStoreRootDir = prepareBlobStoreRootDir();
        if (prepareBlobStoreRootDir == null) {
            return null;
        }
        return new java.io.File(prepareBlobStoreRootDir, SESSIONS_INDEX_FILE_NAME);
    }

    @android.annotation.Nullable
    public static java.io.File prepareBlobsIndexFile() {
        java.io.File prepareBlobStoreRootDir = prepareBlobStoreRootDir();
        if (prepareBlobStoreRootDir == null) {
            return null;
        }
        return new java.io.File(prepareBlobStoreRootDir, BLOBS_INDEX_FILE_NAME);
    }

    @android.annotation.Nullable
    public static java.io.File prepareBlobStoreRootDir() {
        java.io.File blobStoreRootDir = getBlobStoreRootDir();
        if (!blobStoreRootDir.exists() && !blobStoreRootDir.mkdir()) {
            android.util.Slog.e(TAG, "Failed to mkdir(): " + blobStoreRootDir);
            return null;
        }
        return blobStoreRootDir;
    }

    @android.annotation.NonNull
    public static java.io.File getBlobStoreRootDir() {
        return new java.io.File(android.os.Environment.getDataSystemDirectory(), ROOT_DIR_NAME);
    }

    public static void dump(android.util.IndentingPrintWriter indentingPrintWriter, android.content.Context context) {
        indentingPrintWriter.println("XML current version: 6");
        indentingPrintWriter.println("Idle job ID: 191934935");
        indentingPrintWriter.println("Total bytes per app limit: " + android.text.format.Formatter.formatFileSize(context, getAppDataBytesLimit(), 8));
        indentingPrintWriter.println("Device config properties:");
        indentingPrintWriter.increaseIndent();
        com.android.server.blob.BlobStoreConfig.DeviceConfigProperties.dump(indentingPrintWriter, context);
        indentingPrintWriter.decreaseIndent();
    }
}
