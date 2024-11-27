package com.android.server.backup;

/* loaded from: classes.dex */
public class AppGrammaticalGenderBackupHelper extends android.app.backup.BlobBackupHelper {
    private static final int BLOB_VERSION = 1;
    private static final java.lang.String KEY_APP_GENDER = "app_gender";
    private final com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal mGrammarInflectionManagerInternal;
    private final int mUserId;

    public AppGrammaticalGenderBackupHelper(int i) {
        super(1, new java.lang.String[]{KEY_APP_GENDER});
        this.mUserId = i;
        this.mGrammarInflectionManagerInternal = (com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal) com.android.server.LocalServices.getService(com.android.server.grammaticalinflection.GrammaticalInflectionManagerInternal.class);
    }

    public void performBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) {
        if ((backupDataOutput.getTransportFlags() & 1) == 0) {
            return;
        }
        super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }

    protected byte[] getBackupPayload(java.lang.String str) {
        if (!KEY_APP_GENDER.equals(str) || this.mGrammarInflectionManagerInternal == null) {
            return null;
        }
        return this.mGrammarInflectionManagerInternal.getBackupPayload(this.mUserId);
    }

    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        if (KEY_APP_GENDER.equals(str) && this.mGrammarInflectionManagerInternal != null) {
            this.mGrammarInflectionManagerInternal.stageAndApplyRestoredPayload(bArr, this.mUserId);
        }
    }
}
