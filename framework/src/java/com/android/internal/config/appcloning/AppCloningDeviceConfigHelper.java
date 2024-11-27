package com.android.internal.config.appcloning;

/* loaded from: classes4.dex */
public class AppCloningDeviceConfigHelper {
    public static final java.lang.String ENABLE_APP_CLONING_BUILDING_BLOCKS = "enable_app_cloning_building_blocks";
    private static com.android.internal.config.appcloning.AppCloningDeviceConfigHelper sInstance;
    private static final java.lang.Object sLock = new java.lang.Object();
    private android.provider.DeviceConfig.OnPropertiesChangedListener mDeviceConfigChangeListener;
    private volatile boolean mEnableAppCloningBuildingBlocks = true;

    private AppCloningDeviceConfigHelper() {
    }

    public static com.android.internal.config.appcloning.AppCloningDeviceConfigHelper getInstance(android.content.Context context) {
        com.android.internal.config.appcloning.AppCloningDeviceConfigHelper appCloningDeviceConfigHelper;
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new com.android.internal.config.appcloning.AppCloningDeviceConfigHelper();
                sInstance.init(context);
            }
            appCloningDeviceConfigHelper = sInstance;
        }
        return appCloningDeviceConfigHelper;
    }

    private void init(android.content.Context context) {
        initializeDeviceConfigChangeListener();
        android.provider.DeviceConfig.addOnPropertiesChangedListener("app_cloning", context.getMainExecutor(), this.mDeviceConfigChangeListener);
    }

    private void initializeDeviceConfigChangeListener() {
        this.mDeviceConfigChangeListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.internal.config.appcloning.AppCloningDeviceConfigHelper$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.internal.config.appcloning.AppCloningDeviceConfigHelper.this.lambda$initializeDeviceConfigChangeListener$0(properties);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initializeDeviceConfigChangeListener$0(android.provider.DeviceConfig.Properties properties) {
        java.lang.String str;
        if (!"app_cloning".equals(properties.getNamespace())) {
            return;
        }
        java.util.Iterator it = properties.getKeyset().iterator();
        while (it.hasNext() && (str = (java.lang.String) it.next()) != null) {
            if (ENABLE_APP_CLONING_BUILDING_BLOCKS.equals(str)) {
                updateEnableAppCloningBuildingBlocks();
            }
        }
    }

    private void updateEnableAppCloningBuildingBlocks() {
        this.mEnableAppCloningBuildingBlocks = android.provider.DeviceConfig.getBoolean("app_cloning", ENABLE_APP_CLONING_BUILDING_BLOCKS, true);
    }

    public boolean getEnableAppCloningBuildingBlocks() {
        return this.mEnableAppCloningBuildingBlocks;
    }
}
