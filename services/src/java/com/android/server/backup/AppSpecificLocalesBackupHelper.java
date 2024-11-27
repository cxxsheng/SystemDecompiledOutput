package com.android.server.backup;

/* loaded from: classes.dex */
public class AppSpecificLocalesBackupHelper extends android.app.backup.BlobBackupHelper {
    private static final int BLOB_VERSION = 1;
    private static final boolean DEBUG = false;
    private static final java.lang.String KEY_APP_LOCALES = "app_locales";
    private static final java.lang.String TAG = "AppLocalesBackupHelper";

    @android.annotation.NonNull
    private final com.android.server.locales.LocaleManagerInternal mLocaleManagerInternal;
    private final int mUserId;

    public AppSpecificLocalesBackupHelper(int i) {
        super(1, new java.lang.String[]{KEY_APP_LOCALES});
        this.mUserId = i;
        this.mLocaleManagerInternal = (com.android.server.locales.LocaleManagerInternal) com.android.server.LocalServices.getService(com.android.server.locales.LocaleManagerInternal.class);
    }

    protected byte[] getBackupPayload(java.lang.String str) {
        if (KEY_APP_LOCALES.equals(str)) {
            try {
                return this.mLocaleManagerInternal.getBackupPayload(this.mUserId);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Couldn't communicate with locale manager", e);
                return null;
            }
        }
        android.util.Slog.w(TAG, "Unexpected backup key " + str);
        return null;
    }

    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        if (KEY_APP_LOCALES.equals(str)) {
            try {
                this.mLocaleManagerInternal.stageAndApplyRestoredPayload(bArr, this.mUserId);
                return;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Couldn't communicate with locale manager", e);
                return;
            }
        }
        android.util.Slog.w(TAG, "Unexpected restore key " + str);
    }
}
