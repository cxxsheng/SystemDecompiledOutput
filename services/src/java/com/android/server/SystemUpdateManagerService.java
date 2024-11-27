package com.android.server;

/* loaded from: classes.dex */
public class SystemUpdateManagerService extends android.os.ISystemUpdateManager.Stub {
    private static final java.lang.String INFO_FILE = "system-update-info.xml";
    private static final int INFO_FILE_VERSION = 0;
    private static final java.lang.String KEY_BOOT_COUNT = "boot-count";
    private static final java.lang.String KEY_INFO_BUNDLE = "info-bundle";
    private static final java.lang.String KEY_UID = "uid";
    private static final java.lang.String KEY_VERSION = "version";
    private static final java.lang.String TAG = "SystemUpdateManagerService";
    private static final java.lang.String TAG_INFO = "info";
    private static final int UID_UNKNOWN = -1;
    private final android.content.Context mContext;
    private final java.lang.Object mLock = new java.lang.Object();
    private int mLastUid = -1;
    private int mLastStatus = 0;
    private final android.util.AtomicFile mFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), INFO_FILE));

    public SystemUpdateManagerService(android.content.Context context) {
        this.mContext = context;
        synchronized (this.mLock) {
            loadSystemUpdateInfoLocked();
        }
    }

    @android.annotation.EnforcePermission("android.permission.RECOVERY")
    public void updateSystemUpdateInfo(android.os.PersistableBundle persistableBundle) {
        updateSystemUpdateInfo_enforcePermission();
        int i = persistableBundle.getInt("status", 0);
        if (i == 0) {
            android.util.Slog.w(TAG, "Invalid status info. Ignored");
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        if (this.mLastUid == -1 || this.mLastUid == callingUid || i != 1) {
            synchronized (this.mLock) {
                saveSystemUpdateInfoLocked(persistableBundle, callingUid);
            }
            return;
        }
        android.util.Slog.i(TAG, "Inactive updater reporting IDLE status. Ignored");
    }

    public android.os.Bundle retrieveSystemUpdateInfo() {
        android.os.Bundle loadSystemUpdateInfoLocked;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.READ_SYSTEM_UPDATE_INFO") == -1 && this.mContext.checkCallingOrSelfPermission("android.permission.RECOVERY") == -1) {
            throw new java.lang.SecurityException("Can't read system update info. Requiring READ_SYSTEM_UPDATE_INFO or RECOVERY permission.");
        }
        synchronized (this.mLock) {
            loadSystemUpdateInfoLocked = loadSystemUpdateInfoLocked();
        }
        return loadSystemUpdateInfoLocked;
    }

    private android.os.Bundle loadSystemUpdateInfoLocked() {
        java.io.FileInputStream openRead;
        android.os.PersistableBundle persistableBundle = null;
        try {
            openRead = this.mFile.openRead();
        } catch (java.io.FileNotFoundException e) {
            android.util.Slog.i(TAG, "No existing info file " + this.mFile.getBaseFile());
        } catch (java.io.IOException e2) {
            android.util.Slog.e(TAG, "Failed to read the info file:", e2);
        } catch (org.xmlpull.v1.XmlPullParserException e3) {
            android.util.Slog.e(TAG, "Failed to parse the info file:", e3);
        }
        try {
            persistableBundle = readInfoFileLocked(android.util.Xml.resolvePullParser(openRead));
            if (openRead != null) {
                openRead.close();
            }
            if (persistableBundle == null) {
                return removeInfoFileAndGetDefaultInfoBundleLocked();
            }
            if (persistableBundle.getInt(KEY_VERSION, -1) == -1) {
                android.util.Slog.w(TAG, "Invalid info file (invalid version). Ignored");
                return removeInfoFileAndGetDefaultInfoBundleLocked();
            }
            int i = persistableBundle.getInt("uid", -1);
            if (i == -1) {
                android.util.Slog.w(TAG, "Invalid info file (invalid UID). Ignored");
                return removeInfoFileAndGetDefaultInfoBundleLocked();
            }
            int i2 = persistableBundle.getInt(KEY_BOOT_COUNT, -1);
            if (i2 == -1 || i2 != getBootCount()) {
                android.util.Slog.w(TAG, "Outdated info file. Ignored");
                return removeInfoFileAndGetDefaultInfoBundleLocked();
            }
            android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(KEY_INFO_BUNDLE);
            if (persistableBundle2 == null) {
                android.util.Slog.w(TAG, "Invalid info file (missing info). Ignored");
                return removeInfoFileAndGetDefaultInfoBundleLocked();
            }
            int i3 = persistableBundle2.getInt("status", 0);
            if (i3 == 0) {
                android.util.Slog.w(TAG, "Invalid info file (invalid status). Ignored");
                return removeInfoFileAndGetDefaultInfoBundleLocked();
            }
            this.mLastStatus = i3;
            this.mLastUid = i;
            return new android.os.Bundle(persistableBundle2);
        } catch (java.lang.Throwable th) {
            if (openRead != null) {
                try {
                    openRead.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void saveSystemUpdateInfoLocked(android.os.PersistableBundle persistableBundle, int i) {
        android.os.PersistableBundle persistableBundle2 = new android.os.PersistableBundle();
        persistableBundle2.putPersistableBundle(KEY_INFO_BUNDLE, persistableBundle);
        persistableBundle2.putInt(KEY_VERSION, 0);
        persistableBundle2.putInt("uid", i);
        persistableBundle2.putInt(KEY_BOOT_COUNT, getBootCount());
        if (writeInfoFileLocked(persistableBundle2)) {
            this.mLastUid = i;
            this.mLastStatus = persistableBundle.getInt("status");
        }
    }

    @android.annotation.Nullable
    private android.os.PersistableBundle readInfoFileLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 2 && TAG_INFO.equals(typedXmlPullParser.getName())) {
                    return android.os.PersistableBundle.restoreFromXml(typedXmlPullParser);
                }
            } else {
                return null;
            }
        }
    }

    private boolean writeInfoFileLocked(android.os.PersistableBundle persistableBundle) {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            java.io.FileOutputStream startWrite = this.mFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, TAG_INFO);
                persistableBundle.saveToXml(resolveSerializer);
                resolveSerializer.endTag((java.lang.String) null, TAG_INFO);
                resolveSerializer.endDocument();
                this.mFile.finishWrite(startWrite);
                return true;
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                e = e;
                fileOutputStream = startWrite;
                android.util.Slog.e(TAG, "Failed to save the info file:", e);
                if (fileOutputStream != null) {
                    this.mFile.failWrite(fileOutputStream);
                    return false;
                }
                return false;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            e = e2;
        }
    }

    private android.os.Bundle removeInfoFileAndGetDefaultInfoBundleLocked() {
        if (this.mFile.exists()) {
            android.util.Slog.i(TAG, "Removing info file");
            this.mFile.delete();
        }
        this.mLastStatus = 0;
        this.mLastUid = -1;
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("status", 0);
        return bundle;
    }

    private int getBootCount() {
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "boot_count", 0);
    }
}
