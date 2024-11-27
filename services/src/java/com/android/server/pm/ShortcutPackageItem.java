package com.android.server.pm;

/* loaded from: classes2.dex */
abstract class ShortcutPackageItem {
    private static final java.lang.String KEY_NAME = "name";
    private static final java.lang.String TAG = "ShortcutService";
    private final com.android.server.pm.ShortcutPackageInfo mPackageInfo;
    private final java.lang.String mPackageName;
    private final int mPackageUserId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final com.android.server.pm.ShortcutBitmapSaver mShortcutBitmapSaver;
    protected com.android.server.pm.ShortcutUser mShortcutUser;
    protected final java.lang.Object mLock = new java.lang.Object();
    private final java.lang.Runnable mSaveShortcutPackageRunner = new java.lang.Runnable() { // from class: com.android.server.pm.ShortcutPackageItem$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.pm.ShortcutPackageItem.this.saveShortcutPackageItem();
        }
    };

    protected abstract boolean canRestoreAnyVersion();

    public abstract int getOwnerUserId();

    protected abstract java.io.File getShortcutPackageItemFile();

    protected abstract void onRestored(int i);

    public abstract void saveToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;

    protected ShortcutPackageItem(@android.annotation.NonNull com.android.server.pm.ShortcutUser shortcutUser, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.pm.ShortcutPackageInfo shortcutPackageInfo) {
        this.mShortcutUser = shortcutUser;
        this.mPackageUserId = i;
        this.mPackageName = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        java.util.Objects.requireNonNull(shortcutPackageInfo);
        this.mPackageInfo = shortcutPackageInfo;
        this.mShortcutBitmapSaver = new com.android.server.pm.ShortcutBitmapSaver(shortcutUser.mService);
    }

    public void replaceUser(com.android.server.pm.ShortcutUser shortcutUser) {
        this.mShortcutUser = shortcutUser;
    }

    public com.android.server.pm.ShortcutUser getUser() {
        return this.mShortcutUser;
    }

    public int getPackageUserId() {
        return this.mPackageUserId;
    }

    @android.annotation.NonNull
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public com.android.server.pm.ShortcutPackageInfo getPackageInfo() {
        return this.mPackageInfo;
    }

    public void refreshPackageSignatureAndSave() {
        if (this.mPackageInfo.isShadow()) {
            return;
        }
        this.mPackageInfo.refreshSignature(this.mShortcutUser.mService, this);
        scheduleSave();
    }

    public void attemptToRestoreIfNeededAndSave() {
        int canRestoreTo;
        if (!this.mPackageInfo.isShadow()) {
            return;
        }
        com.android.server.pm.ShortcutService shortcutService = this.mShortcutUser.mService;
        if (!shortcutService.isPackageInstalled(this.mPackageName, this.mPackageUserId)) {
            return;
        }
        if (!this.mPackageInfo.hasSignatures()) {
            shortcutService.wtf("Attempted to restore package " + this.mPackageName + "/u" + this.mPackageUserId + " but signatures not found in the restore data.");
            canRestoreTo = 102;
        } else {
            android.content.pm.PackageInfo packageInfoWithSignatures = shortcutService.getPackageInfoWithSignatures(this.mPackageName, this.mPackageUserId);
            packageInfoWithSignatures.getLongVersionCode();
            canRestoreTo = this.mPackageInfo.canRestoreTo(shortcutService, packageInfoWithSignatures, canRestoreAnyVersion());
        }
        onRestored(canRestoreTo);
        this.mPackageInfo.setShadow(false);
        scheduleSave();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void saveToFileLocked(java.io.File file, boolean z) {
        com.android.modules.utils.TypedXmlSerializer resolveSerializer;
        com.android.server.pm.ResilientAtomicFile resilientFile = getResilientFile(file);
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                java.io.FileOutputStream startWrite = resilientFile.startWrite();
                try {
                    if (z) {
                        resolveSerializer = android.util.Xml.newFastSerializer();
                        resolveSerializer.setOutput(startWrite, java.nio.charset.StandardCharsets.UTF_8.name());
                    } else {
                        resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    }
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    saveToXml(resolveSerializer, z);
                    resolveSerializer.endDocument();
                    startWrite.flush();
                    resilientFile.finishWrite(startWrite);
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    e = e;
                    fileOutputStream = startWrite;
                    android.util.Slog.e(TAG, "Failed to write to file " + resilientFile.getBaseFile(), e);
                    resilientFile.failWrite(fileOutputStream);
                    if (resilientFile == null) {
                    }
                }
            } catch (java.lang.Throwable th) {
                if (resilientFile != null) {
                    try {
                        resilientFile.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            e = e2;
        }
        if (resilientFile == null) {
            resilientFile.close();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void scheduleSaveToAppSearchLocked() {
    }

    public org.json.JSONObject dumpCheckin(boolean z) throws org.json.JSONException {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        jSONObject.put("name", this.mPackageName);
        return jSONObject;
    }

    public void verifyStates() {
    }

    public void scheduleSave() {
        this.mShortcutUser.mService.injectPostToHandlerDebounced(this.mSaveShortcutPackageRunner, this.mSaveShortcutPackageRunner);
    }

    void saveShortcutPackageItem() {
        waitForBitmapSaves();
        java.io.File shortcutPackageItemFile = getShortcutPackageItemFile();
        synchronized (this.mLock) {
            shortcutPackageItemFile.getParentFile().mkdirs();
            saveToFileLocked(shortcutPackageItemFile, false);
            scheduleSaveToAppSearchLocked();
        }
    }

    public boolean waitForBitmapSaves() {
        boolean waitForAllSavesLocked;
        synchronized (this.mLock) {
            waitForAllSavesLocked = this.mShortcutBitmapSaver.waitForAllSavesLocked();
        }
        return waitForAllSavesLocked;
    }

    public void saveBitmap(android.content.pm.ShortcutInfo shortcutInfo, int i, android.graphics.Bitmap.CompressFormat compressFormat, int i2) {
        synchronized (this.mLock) {
            this.mShortcutBitmapSaver.saveBitmapLocked(shortcutInfo, i, compressFormat, i2);
        }
    }

    @android.annotation.Nullable
    public java.lang.String getBitmapPathMayWait(android.content.pm.ShortcutInfo shortcutInfo) {
        java.lang.String bitmapPathMayWaitLocked;
        synchronized (this.mLock) {
            bitmapPathMayWaitLocked = this.mShortcutBitmapSaver.getBitmapPathMayWaitLocked(shortcutInfo);
        }
        return bitmapPathMayWaitLocked;
    }

    public void removeIcon(android.content.pm.ShortcutInfo shortcutInfo) {
        synchronized (this.mLock) {
            this.mShortcutBitmapSaver.removeIcon(shortcutInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeShortcutPackageItem() {
        synchronized (this.mLock) {
            getResilientFile(getShortcutPackageItemFile()).delete();
        }
    }

    protected static com.android.server.pm.ResilientAtomicFile getResilientFile(java.io.File file) {
        java.lang.String path = file.getPath();
        return new com.android.server.pm.ResilientAtomicFile(file, new java.io.File(path + ".backup"), new java.io.File(path + ".reservecopy"), 505, "shortcut package item", null);
    }
}
