package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class ModuleInfoProvider {
    private static final java.lang.String MODULE_METADATA_KEY = "android.content.pm.MODULE_METADATA";
    private static final java.lang.String TAG = "PackageManager.ModuleInfoProvider";
    private final com.android.server.pm.ApexManager mApexManager;
    private final android.content.Context mContext;
    private volatile boolean mMetadataLoaded;
    private final java.util.Map<java.lang.String, android.content.pm.ModuleInfo> mModuleInfo;
    private android.content.pm.IPackageManager mPackageManager;
    private volatile java.lang.String mPackageName;

    ModuleInfoProvider(android.content.Context context) {
        this.mContext = context;
        this.mApexManager = com.android.server.pm.ApexManager.getInstance();
        this.mModuleInfo = new android.util.ArrayMap();
    }

    @com.android.internal.annotations.VisibleForTesting
    public ModuleInfoProvider(android.content.res.XmlResourceParser xmlResourceParser, android.content.res.Resources resources, com.android.server.pm.ApexManager apexManager) {
        this.mContext = null;
        this.mApexManager = apexManager;
        this.mModuleInfo = new android.util.ArrayMap();
        loadModuleMetadata(xmlResourceParser, resources);
    }

    @android.annotation.NonNull
    private android.content.pm.IPackageManager getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager = android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService(com.android.server.pm.Settings.ATTR_PACKAGE));
        }
        return this.mPackageManager;
    }

    public void systemReady() {
        this.mPackageName = this.mContext.getResources().getString(android.R.string.config_defaultCredentialManagerHybridService);
        if (android.text.TextUtils.isEmpty(this.mPackageName)) {
            android.util.Slog.w(TAG, "No configured module metadata provider.");
            return;
        }
        try {
            android.content.pm.PackageInfo packageInfo = getPackageManager().getPackageInfo(this.mPackageName, 128L, 0);
            android.content.res.Resources resources = this.mContext.createPackageContext(this.mPackageName, 0).getResources();
            loadModuleMetadata(resources.getXml(packageInfo.applicationInfo.metaData.getInt(MODULE_METADATA_KEY)), resources);
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Unable to discover metadata package: " + this.mPackageName, e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0020, code lost:
    
        android.util.Slog.w(com.android.server.pm.ModuleInfoProvider.TAG, "Unexpected metadata element: " + r7.getName());
        r6.mModuleInfo.clear();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void loadModuleMetadata(android.content.res.XmlResourceParser xmlResourceParser, android.content.res.Resources resources) {
        try {
            try {
                com.android.internal.util.XmlUtils.beginDocument(xmlResourceParser, "module-metadata");
                while (true) {
                    com.android.internal.util.XmlUtils.nextElement(xmlResourceParser);
                    if (xmlResourceParser.getEventType() == 1) {
                        break;
                    }
                    if (!"module".equals(xmlResourceParser.getName())) {
                        break;
                    }
                    java.lang.CharSequence text = resources.getText(java.lang.Integer.parseInt(xmlResourceParser.getAttributeValue(null, "name").substring(1)));
                    java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(xmlResourceParser, com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME);
                    boolean readBooleanAttribute = com.android.internal.util.XmlUtils.readBooleanAttribute(xmlResourceParser, "isHidden");
                    android.content.pm.ModuleInfo moduleInfo = new android.content.pm.ModuleInfo();
                    moduleInfo.setHidden(readBooleanAttribute);
                    moduleInfo.setPackageName(readStringAttribute);
                    moduleInfo.setName(text);
                    moduleInfo.setApexModuleName(this.mApexManager.getApexModuleNameForPackageName(readStringAttribute));
                    if (android.content.pm.Flags.provideInfoOfApkInApex()) {
                        moduleInfo.setApkInApexPackageNames(this.mApexManager.getApksInApex(readStringAttribute));
                    }
                    this.mModuleInfo.put(readStringAttribute, moduleInfo);
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.w(TAG, "Error parsing module metadata", e);
                this.mModuleInfo.clear();
            }
        } finally {
            xmlResourceParser.close();
            this.mMetadataLoaded = true;
        }
    }

    java.util.List<android.content.pm.ModuleInfo> getInstalledModules(int i) {
        if (!this.mMetadataLoaded) {
            throw new java.lang.IllegalStateException("Call to getInstalledModules before metadata loaded");
        }
        if ((131072 & i) != 0) {
            return new java.util.ArrayList(this.mModuleInfo.values());
        }
        try {
            java.util.List list = getPackageManager().getInstalledPackages(i | 1073741824, android.os.UserHandle.getCallingUserId()).getList();
            java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
            java.util.Iterator it = list.iterator();
            while (it.hasNext()) {
                android.content.pm.ModuleInfo moduleInfo = this.mModuleInfo.get(((android.content.pm.PackageInfo) it.next()).packageName);
                if (moduleInfo != null) {
                    arrayList.add(moduleInfo);
                }
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Unable to retrieve all package names", e);
            return java.util.Collections.emptyList();
        }
    }

    android.content.pm.ModuleInfo getModuleInfo(java.lang.String str, int i) {
        if (!this.mMetadataLoaded) {
            throw new java.lang.IllegalStateException("Call to getModuleInfo before metadata loaded");
        }
        if ((i & 1) != 0) {
            for (android.content.pm.ModuleInfo moduleInfo : this.mModuleInfo.values()) {
                if (str.equals(moduleInfo.getApexModuleName())) {
                    return moduleInfo;
                }
            }
            return null;
        }
        return this.mModuleInfo.get(str);
    }

    java.lang.String getPackageName() {
        if (!this.mMetadataLoaded) {
            throw new java.lang.IllegalStateException("Call to getVersion before metadata loaded");
        }
        return this.mPackageName;
    }
}
