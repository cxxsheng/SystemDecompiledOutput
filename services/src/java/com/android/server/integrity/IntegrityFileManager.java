package com.android.server.integrity;

/* loaded from: classes2.dex */
public class IntegrityFileManager {
    private static final java.lang.String INDEXING_FILE = "indexing";
    private static final java.lang.String METADATA_FILE = "metadata";
    private static final java.lang.String RULES_FILE = "rules";
    private static final java.lang.String TAG = "IntegrityFileManager";
    private final java.io.File mDataDir;

    @android.annotation.Nullable
    private com.android.server.integrity.parser.RuleIndexingController mRuleIndexingController;

    @android.annotation.Nullable
    private com.android.server.integrity.model.RuleMetadata mRuleMetadataCache;
    private final com.android.server.integrity.parser.RuleParser mRuleParser;
    private final com.android.server.integrity.serializer.RuleSerializer mRuleSerializer;
    private final java.io.File mRulesDir;
    private final java.io.File mStagingDir;
    private static final java.lang.Object RULES_LOCK = new java.lang.Object();
    private static com.android.server.integrity.IntegrityFileManager sInstance = null;

    public static synchronized com.android.server.integrity.IntegrityFileManager getInstance() {
        com.android.server.integrity.IntegrityFileManager integrityFileManager;
        synchronized (com.android.server.integrity.IntegrityFileManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.integrity.IntegrityFileManager();
                }
                integrityFileManager = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return integrityFileManager;
    }

    private IntegrityFileManager() {
        this(new com.android.server.integrity.parser.RuleBinaryParser(), new com.android.server.integrity.serializer.RuleBinarySerializer(), android.os.Environment.getDataSystemDirectory());
    }

    @com.android.internal.annotations.VisibleForTesting
    IntegrityFileManager(com.android.server.integrity.parser.RuleParser ruleParser, com.android.server.integrity.serializer.RuleSerializer ruleSerializer, java.io.File file) {
        this.mRuleParser = ruleParser;
        this.mRuleSerializer = ruleSerializer;
        this.mDataDir = file;
        this.mRulesDir = new java.io.File(file, "integrity_rules");
        this.mStagingDir = new java.io.File(file, "integrity_staging");
        if (!this.mStagingDir.mkdirs() || !this.mRulesDir.mkdirs()) {
            android.util.Slog.e(TAG, "Error creating staging and rules directory");
        }
        java.io.File file2 = new java.io.File(this.mRulesDir, METADATA_FILE);
        if (file2.exists()) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file2);
                try {
                    this.mRuleMetadataCache = com.android.server.integrity.parser.RuleMetadataParser.parse(fileInputStream);
                    fileInputStream.close();
                } finally {
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Error reading metadata file.", e);
            }
        }
        updateRuleIndexingController();
    }

    public boolean initialized() {
        return new java.io.File(this.mRulesDir, RULES_FILE).exists() && new java.io.File(this.mRulesDir, METADATA_FILE).exists() && new java.io.File(this.mRulesDir, INDEXING_FILE).exists();
    }

    public void writeRules(java.lang.String str, java.lang.String str2, java.util.List<android.content.integrity.Rule> list) throws java.io.IOException, com.android.server.integrity.serializer.RuleSerializeException {
        try {
            writeMetadata(this.mStagingDir, str2, str);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error writing metadata.", e);
        }
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(new java.io.File(this.mStagingDir, RULES_FILE));
        try {
            java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream(new java.io.File(this.mStagingDir, INDEXING_FILE));
            try {
                this.mRuleSerializer.serialize(list, java.util.Optional.empty(), fileOutputStream, fileOutputStream2);
                fileOutputStream2.close();
                fileOutputStream.close();
                switchStagingRulesDir();
                updateRuleIndexingController();
            } finally {
            }
        } catch (java.lang.Throwable th) {
            try {
                fileOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public java.util.List<android.content.integrity.Rule> readRules(@android.annotation.Nullable android.content.integrity.AppInstallMetadata appInstallMetadata) throws java.io.IOException, com.android.server.integrity.parser.RuleParseException {
        java.util.List<android.content.integrity.Rule> parse;
        synchronized (RULES_LOCK) {
            java.util.List<com.android.server.integrity.parser.RuleIndexRange> emptyList = java.util.Collections.emptyList();
            if (appInstallMetadata != null) {
                try {
                    emptyList = this.mRuleIndexingController.identifyRulesToEvaluate(appInstallMetadata);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(TAG, "Error identifying the rule indexes. Trying unindexed.", e);
                }
            }
            parse = this.mRuleParser.parse(com.android.server.integrity.parser.RandomAccessObject.ofFile(new java.io.File(this.mRulesDir, RULES_FILE)), emptyList);
        }
        return parse;
    }

    @android.annotation.Nullable
    public com.android.server.integrity.model.RuleMetadata readMetadata() {
        return this.mRuleMetadataCache;
    }

    private void switchStagingRulesDir() throws java.io.IOException {
        synchronized (RULES_LOCK) {
            try {
                java.io.File file = new java.io.File(this.mDataDir, "temp");
                if (!this.mRulesDir.renameTo(file) || !this.mStagingDir.renameTo(this.mRulesDir) || !file.renameTo(this.mStagingDir)) {
                    throw new java.io.IOException("Error switching staging/rules directory");
                }
                for (java.io.File file2 : this.mStagingDir.listFiles()) {
                    file2.delete();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateRuleIndexingController() {
        java.io.File file = new java.io.File(this.mRulesDir, INDEXING_FILE);
        if (file.exists()) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                try {
                    this.mRuleIndexingController = new com.android.server.integrity.parser.RuleIndexingController(fileInputStream);
                    fileInputStream.close();
                } finally {
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Error parsing the rule indexing file.", e);
            }
        }
    }

    private void writeMetadata(java.io.File file, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        this.mRuleMetadataCache = new com.android.server.integrity.model.RuleMetadata(str, str2);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(new java.io.File(file, METADATA_FILE));
        try {
            com.android.server.integrity.serializer.RuleMetadataSerializer.serialize(this.mRuleMetadataCache, fileOutputStream);
            fileOutputStream.close();
        } catch (java.lang.Throwable th) {
            try {
                fileOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
