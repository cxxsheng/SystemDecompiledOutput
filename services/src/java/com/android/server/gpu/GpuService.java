package com.android.server.gpu;

/* loaded from: classes.dex */
public class GpuService extends com.android.server.SystemService {
    private static final int BASE64_FLAGS = 3;
    public static final boolean DEBUG = false;
    private static final java.lang.String DEV_DRIVER_PROPERTY = "ro.gfx.driver.1";
    private static final java.lang.String PROD_DRIVER_PROPERTY = "ro.gfx.driver.0";
    public static final java.lang.String TAG = "GpuService";
    private static final java.lang.String UPDATABLE_DRIVER_PRODUCTION_ALLOWLIST_FILENAME = "allowlist.txt";
    private android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.updatabledriver.UpdatableDriverProto.Denylists mDenylists;
    private final java.lang.String mDevDriverPackageName;
    private com.android.server.gpu.GpuService.DeviceConfigListener mDeviceConfigListener;
    private final java.lang.Object mDeviceConfigLock;
    private final boolean mHasDevDriver;
    private final boolean mHasProdDriver;
    private final java.lang.Object mLock;
    private final android.content.pm.PackageManager mPackageManager;
    private final java.lang.String mProdDriverPackageName;
    private long mProdDriverVersionCode;
    private com.android.server.gpu.GpuService.SettingsObserver mSettingsObserver;

    private static native void nSetUpdatableDriverPath(java.lang.String str);

    public GpuService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mDeviceConfigLock = new java.lang.Object();
        this.mContext = context;
        this.mProdDriverPackageName = android.os.SystemProperties.get(PROD_DRIVER_PROPERTY);
        this.mProdDriverVersionCode = -1L;
        this.mDevDriverPackageName = android.os.SystemProperties.get(DEV_DRIVER_PROPERTY);
        this.mPackageManager = context.getPackageManager();
        this.mHasProdDriver = !android.text.TextUtils.isEmpty(this.mProdDriverPackageName);
        this.mHasDevDriver = !android.text.TextUtils.isEmpty(this.mDevDriverPackageName);
        if (this.mHasDevDriver || this.mHasProdDriver) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
            getContext().registerReceiverAsUser(new com.android.server.gpu.GpuService.PackageReceiver(), android.os.UserHandle.ALL, intentFilter, null, null);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 1000) {
            this.mContentResolver = this.mContext.getContentResolver();
            if (!this.mHasProdDriver && !this.mHasDevDriver) {
                return;
            }
            this.mSettingsObserver = new com.android.server.gpu.GpuService.SettingsObserver();
            this.mDeviceConfigListener = new com.android.server.gpu.GpuService.DeviceConfigListener();
            fetchProductionDriverPackageProperties();
            processDenylists();
            setDenylist();
            fetchPrereleaseDriverPackageProperties();
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        private final android.net.Uri mProdDriverDenylistsUri;

        SettingsObserver() {
            super(new android.os.Handler());
            this.mProdDriverDenylistsUri = android.provider.Settings.Global.getUriFor("updatable_driver_production_denylists");
            com.android.server.gpu.GpuService.this.mContentResolver.registerContentObserver(this.mProdDriverDenylistsUri, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (uri != null && this.mProdDriverDenylistsUri.equals(uri)) {
                com.android.server.gpu.GpuService.this.processDenylists();
                com.android.server.gpu.GpuService.this.setDenylist();
            }
        }
    }

    private final class DeviceConfigListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {
        DeviceConfigListener() {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("game_driver", com.android.server.gpu.GpuService.this.mContext.getMainExecutor(), this);
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            synchronized (com.android.server.gpu.GpuService.this.mDeviceConfigLock) {
                try {
                    if (properties.getKeyset().contains("updatable_driver_production_denylists")) {
                        com.android.server.gpu.GpuService.this.parseDenylists(properties.getString("updatable_driver_production_denylists", ""));
                        com.android.server.gpu.GpuService.this.setDenylist();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class PackageReceiver extends android.content.BroadcastReceiver {
        private PackageReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.Intent intent) {
            char c;
            java.lang.String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            boolean equals = schemeSpecificPart.equals(com.android.server.gpu.GpuService.this.mProdDriverPackageName);
            boolean equals2 = schemeSpecificPart.equals(com.android.server.gpu.GpuService.this.mDevDriverPackageName);
            if (!equals && !equals2) {
            }
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case 172491798:
                    if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1544582882:
                    if (action.equals("android.intent.action.PACKAGE_ADDED")) {
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
                case 1:
                case 2:
                    if (equals) {
                        com.android.server.gpu.GpuService.this.fetchProductionDriverPackageProperties();
                        com.android.server.gpu.GpuService.this.setDenylist();
                        break;
                    } else if (equals2) {
                        com.android.server.gpu.GpuService.this.fetchPrereleaseDriverPackageProperties();
                        break;
                    }
                    break;
            }
        }
    }

    private static void assetToSettingsGlobal(android.content.Context context, android.content.Context context2, java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence) {
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(context2.getAssets().open(str)));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    arrayList.add(readLine);
                } else {
                    android.provider.Settings.Global.putString(context.getContentResolver(), str2, java.lang.String.join(charSequence, arrayList));
                    return;
                }
            }
        } catch (java.io.IOException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchProductionDriverPackageProperties() {
        try {
            android.content.pm.ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(this.mProdDriverPackageName, 1048576);
            if (applicationInfo.targetSdkVersion < 26) {
                return;
            }
            android.provider.Settings.Global.putString(this.mContentResolver, "updatable_driver_production_allowlist", "");
            this.mProdDriverVersionCode = applicationInfo.longVersionCode;
            try {
                assetToSettingsGlobal(this.mContext, this.mContext.createPackageContext(this.mProdDriverPackageName, 4), UPDATABLE_DRIVER_PRODUCTION_ALLOWLIST_FILENAME, "updatable_driver_production_allowlist", ",");
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processDenylists() {
        java.lang.String property = android.provider.DeviceConfig.getProperty("game_driver", "updatable_driver_production_denylists");
        if (property == null) {
            property = android.provider.Settings.Global.getString(this.mContentResolver, "updatable_driver_production_denylists");
        }
        if (property == null) {
            property = "";
        }
        parseDenylists(property);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void parseDenylists(java.lang.String str) {
        synchronized (this.mLock) {
            this.mDenylists = null;
            try {
                this.mDenylists = android.updatabledriver.UpdatableDriverProto.Denylists.parseFrom(android.util.Base64.decode(str, 3));
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
            } catch (java.lang.IllegalArgumentException e2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDenylist() {
        android.provider.Settings.Global.putString(this.mContentResolver, "updatable_driver_production_denylist", "");
        synchronized (this.mLock) {
            try {
                if (this.mDenylists == null) {
                    return;
                }
                for (android.updatabledriver.UpdatableDriverProto.Denylist denylist : this.mDenylists.getDenylistsList()) {
                    if (denylist.getVersionCode() == this.mProdDriverVersionCode) {
                        android.provider.Settings.Global.putString(this.mContentResolver, "updatable_driver_production_denylist", java.lang.String.join(",", denylist.getPackageNamesList()));
                        return;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchPrereleaseDriverPackageProperties() {
        try {
            android.content.pm.ApplicationInfo applicationInfo = this.mPackageManager.getApplicationInfo(this.mDevDriverPackageName, 1048576);
            if (applicationInfo.targetSdkVersion < 26) {
                return;
            }
            setUpdatableDriverPath(applicationInfo);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
    }

    private void setUpdatableDriverPath(android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo.primaryCpuAbi == null) {
            nSetUpdatableDriverPath("");
            return;
        }
        nSetUpdatableDriverPath(applicationInfo.sourceDir + "!/lib/");
    }
}
