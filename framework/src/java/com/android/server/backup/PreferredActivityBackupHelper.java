package com.android.server.backup;

/* loaded from: classes5.dex */
public class PreferredActivityBackupHelper extends android.app.backup.BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final int STATE_VERSION = 4;
    private static final java.lang.String TAG = "PreferredBackup";
    private final int mUserId;
    private static final java.lang.String KEY_PREFERRED = "preferred-activity";
    private static final java.lang.String KEY_DEFAULT_APPS = "default-apps";

    @java.lang.Deprecated
    private static final java.lang.String KEY_INTENT_VERIFICATION = "intent-verification";
    private static final java.lang.String KEY_DOMAIN_VERIFICATION = "domain-verification";
    private static final java.lang.String[] KEYS = {KEY_PREFERRED, KEY_DEFAULT_APPS, KEY_INTENT_VERIFICATION, KEY_DOMAIN_VERIFICATION};

    private @interface Key {
    }

    public PreferredActivityBackupHelper(int i) {
        super(4, KEYS);
        this.mUserId = i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(java.lang.String str) {
        char c;
        android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
        try {
            switch (str.hashCode()) {
                case -696985986:
                    if (str.equals(KEY_DEFAULT_APPS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -549387132:
                    if (str.equals(KEY_DOMAIN_VERIFICATION)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -429170260:
                    if (str.equals(KEY_INTENT_VERIFICATION)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1336142555:
                    if (str.equals(KEY_PREFERRED)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Unable to store payload " + str, e);
        }
        switch (c) {
            case 0:
                return packageManager.getPreferredActivityBackup(this.mUserId);
            case 1:
                return packageManager.getDefaultAppsBackup(this.mUserId);
            case 2:
                return null;
            case 3:
                return packageManager.getDomainVerificationBackup(this.mUserId);
            default:
                android.util.Slog.w(TAG, "Unexpected backup key " + str);
                return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        char c;
        android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
        try {
            switch (str.hashCode()) {
                case -696985986:
                    if (str.equals(KEY_DEFAULT_APPS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -549387132:
                    if (str.equals(KEY_DOMAIN_VERIFICATION)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -429170260:
                    if (str.equals(KEY_INTENT_VERIFICATION)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1336142555:
                    if (str.equals(KEY_PREFERRED)) {
                        c = 0;
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
                    packageManager.restorePreferredActivities(bArr, this.mUserId);
                    break;
                case 1:
                    packageManager.restoreDefaultApps(bArr, this.mUserId);
                    break;
                case 2:
                    break;
                case 3:
                    packageManager.restoreDomainVerification(bArr, this.mUserId);
                    break;
                default:
                    android.util.Slog.w(TAG, "Unexpected restore key " + str);
                    break;
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Unable to restore key " + str, e);
        }
    }
}
