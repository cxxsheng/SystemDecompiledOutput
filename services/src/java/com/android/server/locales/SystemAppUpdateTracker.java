package com.android.server.locales;

/* loaded from: classes2.dex */
public class SystemAppUpdateTracker {
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String PACKAGE_XML_TAG = "package";
    private static final java.lang.String SYSTEM_APPS_XML_TAG = "system_apps";
    private static final java.lang.String TAG = "SystemAppUpdateTracker";
    private final android.content.Context mContext;
    private final java.lang.Object mFileLock;
    private final com.android.server.locales.LocaleManagerService mLocaleManagerService;
    private final java.util.Set<java.lang.String> mUpdatedApps;
    private final android.util.AtomicFile mUpdatedAppsFile;

    SystemAppUpdateTracker(com.android.server.locales.LocaleManagerService localeManagerService) {
        this(localeManagerService.mContext, localeManagerService, new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), "locale_manager_service_updated_system_apps.xml")));
    }

    @com.android.internal.annotations.VisibleForTesting
    SystemAppUpdateTracker(android.content.Context context, com.android.server.locales.LocaleManagerService localeManagerService, android.util.AtomicFile atomicFile) {
        this.mFileLock = new java.lang.Object();
        this.mUpdatedApps = new java.util.HashSet();
        this.mContext = context;
        this.mLocaleManagerService = localeManagerService;
        this.mUpdatedAppsFile = atomicFile;
    }

    void init() {
        loadUpdatedSystemApps();
    }

    private void loadUpdatedSystemApps() {
        if (!this.mUpdatedAppsFile.getBaseFile().exists()) {
            return;
        }
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = this.mUpdatedAppsFile.openRead();
                readFromXml(fileInputStream);
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(TAG, "loadUpdatedSystemApps: Could not parse storage file ", e);
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(fileInputStream);
        }
    }

    private void readFromXml(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
        newFastPullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        com.android.internal.util.XmlUtils.beginDocument(newFastPullParser, SYSTEM_APPS_XML_TAG);
        int depth = newFastPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(newFastPullParser, depth)) {
            if (newFastPullParser.getName().equals("package")) {
                java.lang.String attributeValue = newFastPullParser.getAttributeValue((java.lang.String) null, "name");
                if (!android.text.TextUtils.isEmpty(attributeValue)) {
                    this.mUpdatedApps.add(attributeValue);
                }
            }
        }
    }

    void onPackageUpdateFinished(java.lang.String str, int i) {
        try {
            if (!this.mUpdatedApps.contains(str) && isUpdatedSystemApp(str)) {
                int userId = android.os.UserHandle.getUserId(i);
                if (this.mLocaleManagerService.getInstallingPackageName(str, userId) == null) {
                    return;
                }
                try {
                    android.os.LocaleList applicationLocales = this.mLocaleManagerService.getApplicationLocales(str, userId);
                    if (!applicationLocales.isEmpty()) {
                        this.mLocaleManagerService.notifyInstallerOfAppWhoseLocaleChanged(str, userId, applicationLocales);
                    }
                } catch (android.os.RemoteException e) {
                }
                updateBroadcastedAppsList(str);
            }
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Exception in onPackageUpdateFinished.", e2);
        }
    }

    private void updateBroadcastedAppsList(java.lang.String str) {
        synchronized (this.mFileLock) {
            this.mUpdatedApps.add(str);
            writeUpdatedAppsFileLocked();
        }
    }

    private void writeUpdatedAppsFileLocked() {
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mUpdatedAppsFile.startWrite();
            writeToXmlLocked(fileOutputStream);
            this.mUpdatedAppsFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException e) {
            this.mUpdatedAppsFile.failWrite(fileOutputStream);
            android.util.Slog.e(TAG, "Failed to persist the updated apps list", e);
        }
    }

    private void writeToXmlLocked(java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        newFastSerializer.startDocument((java.lang.String) null, true);
        newFastSerializer.startTag((java.lang.String) null, SYSTEM_APPS_XML_TAG);
        for (java.lang.String str : this.mUpdatedApps) {
            newFastSerializer.startTag((java.lang.String) null, "package");
            newFastSerializer.attribute((java.lang.String) null, "name", str);
            newFastSerializer.endTag((java.lang.String) null, "package");
        }
        newFastSerializer.endTag((java.lang.String) null, SYSTEM_APPS_XML_TAG);
        newFastSerializer.endDocument();
    }

    private boolean isUpdatedSystemApp(java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo;
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, android.content.pm.PackageManager.ApplicationInfoFlags.of(1048576L));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return (applicationInfo == null || (applicationInfo.flags & 128) == 0) ? false : true;
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Set<java.lang.String> getUpdatedApps() {
        return this.mUpdatedApps;
    }
}
