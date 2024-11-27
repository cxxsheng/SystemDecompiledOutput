package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
class QuickAccessWalletServiceInfo {
    private static final java.lang.String TAG = "QAWalletSInfo";
    private static final java.lang.String TAG_WALLET_SERVICE = "quickaccesswallet-service";
    private final android.content.pm.ServiceInfo mServiceInfo;
    private final android.service.quickaccesswallet.QuickAccessWalletServiceInfo.ServiceMetadata mServiceMetadata;
    private final android.service.quickaccesswallet.QuickAccessWalletServiceInfo.TileServiceMetadata mTileServiceMetadata;

    private QuickAccessWalletServiceInfo(android.content.pm.ServiceInfo serviceInfo, android.service.quickaccesswallet.QuickAccessWalletServiceInfo.ServiceMetadata serviceMetadata, android.service.quickaccesswallet.QuickAccessWalletServiceInfo.TileServiceMetadata tileServiceMetadata) {
        this.mServiceInfo = serviceInfo;
        this.mServiceMetadata = serviceMetadata;
        this.mTileServiceMetadata = tileServiceMetadata;
    }

    static android.service.quickaccesswallet.QuickAccessWalletServiceInfo tryCreate(android.content.Context context) {
        java.lang.String defaultWalletApp = getDefaultWalletApp(context);
        if (defaultWalletApp == null) {
            android.content.ComponentName defaultPaymentApp = getDefaultPaymentApp(context);
            if (defaultPaymentApp == null) {
                return null;
            }
            defaultWalletApp = defaultPaymentApp.getPackageName();
        }
        android.content.pm.ServiceInfo walletServiceInfo = getWalletServiceInfo(context, defaultWalletApp);
        if (walletServiceInfo == null) {
            return null;
        }
        if (!android.Manifest.permission.BIND_QUICK_ACCESS_WALLET_SERVICE.equals(walletServiceInfo.permission)) {
            android.util.Log.w(TAG, java.lang.String.format("%s.%s does not require permission %s", walletServiceInfo.packageName, walletServiceInfo.name, android.Manifest.permission.BIND_QUICK_ACCESS_WALLET_SERVICE));
            return null;
        }
        return new android.service.quickaccesswallet.QuickAccessWalletServiceInfo(walletServiceInfo, parseServiceMetadata(context, walletServiceInfo), new android.service.quickaccesswallet.QuickAccessWalletServiceInfo.TileServiceMetadata(parseTileServiceMetadata(context, walletServiceInfo)));
    }

    private static java.lang.String getDefaultWalletApp(android.content.Context context) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.role.RoleManager roleManager = (android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class);
            if (!roleManager.isRoleAvailable("android.app.role.WALLET")) {
                return null;
            }
            java.util.List roleHolders = roleManager.getRoleHolders("android.app.role.WALLET");
            return roleHolders.isEmpty() ? null : (java.lang.String) roleHolders.get(0);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static android.content.ComponentName getDefaultPaymentApp(android.content.Context context) {
        java.lang.String string = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.NFC_PAYMENT_DEFAULT_COMPONENT);
        if (string == null) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(string);
    }

    private static android.content.pm.ServiceInfo getWalletServiceInfo(android.content.Context context, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(android.service.quickaccesswallet.QuickAccessWalletService.SERVICE_INTERFACE);
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 852096);
        if (queryIntentServices.isEmpty()) {
            return null;
        }
        return queryIntentServices.get(0).serviceInfo;
    }

    private static class TileServiceMetadata {
        private final android.graphics.drawable.Drawable mTileIcon;

        private TileServiceMetadata(android.graphics.drawable.Drawable drawable) {
            this.mTileIcon = drawable;
        }
    }

    private static android.graphics.drawable.Drawable parseTileServiceMetadata(android.content.Context context, android.content.pm.ServiceInfo serviceInfo) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        int i = serviceInfo.metaData.getInt(android.service.quickaccesswallet.QuickAccessWalletService.TILE_SERVICE_META_DATA);
        if (i != 0) {
            try {
                return packageManager.getResourcesForApplication(serviceInfo.applicationInfo).getDrawable(i, null);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(TAG, "Error parsing quickaccesswallet tile service meta-data", e);
            }
        }
        return null;
    }

    static class ServiceMetadata {
        private final java.lang.String mSettingsActivity;
        private final java.lang.CharSequence mShortcutLongLabel;
        private final java.lang.CharSequence mShortcutShortLabel;
        private final java.lang.String mTargetActivity;

        /* JADX INFO: Access modifiers changed from: private */
        public static android.service.quickaccesswallet.QuickAccessWalletServiceInfo.ServiceMetadata empty() {
            return new android.service.quickaccesswallet.QuickAccessWalletServiceInfo.ServiceMetadata(null, null, null, null);
        }

        private ServiceMetadata(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
            this.mTargetActivity = str;
            this.mSettingsActivity = str2;
            this.mShortcutShortLabel = charSequence;
            this.mShortcutLongLabel = charSequence2;
        }
    }

    static android.service.quickaccesswallet.QuickAccessWalletServiceInfo.ServiceMetadata parseServiceMetadata(android.content.Context context, android.content.pm.ServiceInfo serviceInfo) {
        android.content.res.Resources resourcesForApplication;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.service.quickaccesswallet.QuickAccessWalletService.SERVICE_META_DATA);
        if (loadXmlMetaData == null) {
            return android.service.quickaccesswallet.QuickAccessWalletServiceInfo.ServiceMetadata.empty();
        }
        try {
            resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
            for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Error parsing quickaccesswallet service meta-data", e);
        }
        if (!TAG_WALLET_SERVICE.equals(loadXmlMetaData.getName())) {
            android.util.Log.e(TAG, "Meta-data does not start with quickaccesswallet-service tag");
            return android.service.quickaccesswallet.QuickAccessWalletServiceInfo.ServiceMetadata.empty();
        }
        android.content.res.TypedArray typedArray = null;
        try {
            typedArray = resourcesForApplication.obtainAttributes(android.util.Xml.asAttributeSet(loadXmlMetaData), com.android.internal.R.styleable.QuickAccessWalletService);
            return new android.service.quickaccesswallet.QuickAccessWalletServiceInfo.ServiceMetadata(typedArray.getString(0), typedArray.getString(1), typedArray.getText(2), typedArray.getText(3));
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    android.content.ComponentName getComponentName() {
        return this.mServiceInfo.getComponentName();
    }

    java.lang.String getWalletActivity() {
        return this.mServiceMetadata.mTargetActivity;
    }

    java.lang.String getSettingsActivity() {
        return this.mServiceMetadata.mSettingsActivity;
    }

    android.graphics.drawable.Drawable getWalletLogo(android.content.Context context) {
        android.graphics.drawable.Drawable loadLogo = this.mServiceInfo.loadLogo(context.getPackageManager());
        if (loadLogo != null) {
            return loadLogo;
        }
        return this.mServiceInfo.loadIcon(context.getPackageManager());
    }

    android.graphics.drawable.Drawable getTileIcon() {
        return this.mTileServiceMetadata.mTileIcon;
    }

    java.lang.CharSequence getShortcutShortLabel(android.content.Context context) {
        if (!android.text.TextUtils.isEmpty(this.mServiceMetadata.mShortcutShortLabel)) {
            return this.mServiceMetadata.mShortcutShortLabel;
        }
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }

    java.lang.CharSequence getShortcutLongLabel(android.content.Context context) {
        if (!android.text.TextUtils.isEmpty(this.mServiceMetadata.mShortcutLongLabel)) {
            return this.mServiceMetadata.mShortcutLongLabel;
        }
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }

    java.lang.CharSequence getServiceLabel(android.content.Context context) {
        return this.mServiceInfo.loadLabel(context.getPackageManager());
    }
}
