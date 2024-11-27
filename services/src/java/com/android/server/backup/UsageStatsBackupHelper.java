package com.android.server.backup;

/* loaded from: classes.dex */
public class UsageStatsBackupHelper extends android.app.backup.BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final boolean DEBUG = false;
    static final java.lang.String KEY_USAGE_STATS = "usage_stats";
    static final java.lang.String TAG = "UsgStatsBackupHelper";
    private final int mUserId;

    public UsageStatsBackupHelper(int i) {
        super(1, new java.lang.String[]{KEY_USAGE_STATS});
        this.mUserId = i;
    }

    protected byte[] getBackupPayload(java.lang.String str) {
        if (KEY_USAGE_STATS.equals(str)) {
            android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeInt(0);
                dataOutputStream.write(usageStatsManagerInternal.getBackupPayload(this.mUserId, str));
            } catch (java.io.IOException e) {
                byteArrayOutputStream.reset();
            }
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        if (KEY_USAGE_STATS.equals(str)) {
            android.app.usage.UsageStatsManagerInternal usageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
            java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
            try {
                dataInputStream.readInt();
                int length = bArr.length - 4;
                byte[] bArr2 = new byte[length];
                dataInputStream.read(bArr2, 0, length);
                usageStatsManagerInternal.applyRestoredPayload(this.mUserId, str, bArr2);
            } catch (java.io.IOException e) {
            }
        }
    }
}
