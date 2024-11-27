package android.app.backup;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class BackupRestoreEventLogger {
    public static final int DATA_TYPES_ALLOWED = 150;
    private static final java.lang.String TAG = "BackupRestoreEventLogger";
    private final java.security.MessageDigest mHashDigest;
    private final int mOperationType;
    private final java.util.Map<java.lang.String, android.app.backup.BackupRestoreEventLogger.DataTypeResult> mResults = new java.util.HashMap();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BackupRestoreDataType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BackupRestoreError {
    }

    public BackupRestoreEventLogger(int i) {
        java.security.MessageDigest messageDigest;
        this.mOperationType = i;
        try {
            messageDigest = java.security.MessageDigest.getInstance("SHA-256");
        } catch (java.security.NoSuchAlgorithmException e) {
            android.util.Slog.w("Couldn't create MessageDigest for hash computation", e);
            messageDigest = null;
        }
        this.mHashDigest = messageDigest;
    }

    public void logItemsBackedUp(java.lang.String str, int i) {
        logSuccess(0, str, i);
    }

    public void logItemsBackupFailed(java.lang.String str, int i, java.lang.String str2) {
        logFailure(0, str, i, str2);
    }

    public void logBackupMetadata(java.lang.String str, java.lang.String str2) {
        logMetaData(0, str, str2);
    }

    public void logItemsRestored(java.lang.String str, int i) {
        logSuccess(1, str, i);
    }

    public void logItemsRestoreFailed(java.lang.String str, int i, java.lang.String str2) {
        logFailure(1, str, i, str2);
    }

    public void logRestoreMetadata(java.lang.String str, java.lang.String str2) {
        logMetaData(1, str, str2);
    }

    public java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult> getLoggingResults() {
        return new java.util.ArrayList(this.mResults.values());
    }

    public int getOperationType() {
        return this.mOperationType;
    }

    public void clearData() {
        this.mResults.clear();
    }

    private void logSuccess(int i, java.lang.String str, int i2) {
        android.app.backup.BackupRestoreEventLogger.DataTypeResult dataTypeResult = getDataTypeResult(i, str);
        if (dataTypeResult == null) {
            return;
        }
        dataTypeResult.mSuccessCount += i2;
        this.mResults.put(str, dataTypeResult);
    }

    private void logFailure(int i, java.lang.String str, int i2, java.lang.String str2) {
        android.app.backup.BackupRestoreEventLogger.DataTypeResult dataTypeResult = getDataTypeResult(i, str);
        if (dataTypeResult == null) {
            return;
        }
        dataTypeResult.mFailCount += i2;
        if (str2 != null) {
            dataTypeResult.mErrors.merge(str2, java.lang.Integer.valueOf(i2), new android.app.backup.BackupRestoreEventLogger$$ExternalSyntheticLambda0());
        }
    }

    private void logMetaData(int i, java.lang.String str, java.lang.String str2) {
        android.app.backup.BackupRestoreEventLogger.DataTypeResult dataTypeResult;
        if (this.mHashDigest == null || (dataTypeResult = getDataTypeResult(i, str)) == null) {
            return;
        }
        dataTypeResult.mMetadataHash = getMetaDataHash(str2);
    }

    private android.app.backup.BackupRestoreEventLogger.DataTypeResult getDataTypeResult(int i, java.lang.String str) {
        if (i != this.mOperationType) {
            android.util.Slog.d(TAG, "Operation type mismatch: logger created for " + this.mOperationType + ", trying to log for " + i);
            return null;
        }
        if (!this.mResults.containsKey(str)) {
            if (this.mResults.keySet().size() == getDataTypesAllowed()) {
                android.util.Slog.d(TAG, "Logger is full, ignoring new data type");
                return null;
            }
            this.mResults.put(str, new android.app.backup.BackupRestoreEventLogger.DataTypeResult(str));
        }
        return this.mResults.get(str);
    }

    private byte[] getMetaDataHash(java.lang.String str) {
        return this.mHashDigest.digest(str.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    private int getDataTypesAllowed() {
        if (com.android.server.backup.Flags.enableIncreaseDatatypesForAgentLogging()) {
            return 150;
        }
        return 15;
    }

    public static final class DataTypeResult implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.backup.BackupRestoreEventLogger.DataTypeResult> CREATOR = new android.os.Parcelable.Creator<android.app.backup.BackupRestoreEventLogger.DataTypeResult>() { // from class: android.app.backup.BackupRestoreEventLogger.DataTypeResult.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.backup.BackupRestoreEventLogger.DataTypeResult createFromParcel(android.os.Parcel parcel) {
                java.lang.String readString = parcel.readString();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                android.os.Bundle readBundle = parcel.readBundle(getClass().getClassLoader());
                for (java.lang.String str : readBundle.keySet()) {
                    arrayMap.put(str, java.lang.Integer.valueOf(readBundle.getInt(str)));
                }
                byte[] createByteArray = parcel.createByteArray();
                android.app.backup.BackupRestoreEventLogger.DataTypeResult dataTypeResult = new android.app.backup.BackupRestoreEventLogger.DataTypeResult(readString);
                dataTypeResult.mSuccessCount = readInt;
                dataTypeResult.mFailCount = readInt2;
                dataTypeResult.mErrors.putAll(arrayMap);
                dataTypeResult.mMetadataHash = createByteArray;
                return dataTypeResult;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.backup.BackupRestoreEventLogger.DataTypeResult[] newArray(int i) {
                return new android.app.backup.BackupRestoreEventLogger.DataTypeResult[i];
            }
        };
        private final java.lang.String mDataType;
        private final java.util.Map<java.lang.String, java.lang.Integer> mErrors = new java.util.HashMap();
        private int mFailCount;
        private byte[] mMetadataHash;
        private int mSuccessCount;

        public DataTypeResult(java.lang.String str) {
            this.mDataType = str;
        }

        public java.lang.String getDataType() {
            return this.mDataType;
        }

        public int getSuccessCount() {
            return this.mSuccessCount;
        }

        public int getFailCount() {
            return this.mFailCount;
        }

        public java.util.Map<java.lang.String, java.lang.Integer> getErrors() {
            return this.mErrors;
        }

        public byte[] getMetadataHash() {
            return this.mMetadataHash;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mDataType);
            parcel.writeInt(this.mSuccessCount);
            parcel.writeInt(this.mFailCount);
            android.os.Bundle bundle = new android.os.Bundle();
            for (java.util.Map.Entry<java.lang.String, java.lang.Integer> entry : this.mErrors.entrySet()) {
                bundle.putInt(entry.getKey(), entry.getValue().intValue());
            }
            parcel.writeBundle(bundle);
            parcel.writeByteArray(this.mMetadataHash);
        }
    }
}
