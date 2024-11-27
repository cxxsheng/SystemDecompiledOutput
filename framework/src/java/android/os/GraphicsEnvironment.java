package android.os;

/* loaded from: classes3.dex */
public class GraphicsEnvironment {
    private static final java.lang.String ACTION_ANGLE_FOR_ANDROID = "android.app.action.ANGLE_FOR_ANDROID";
    private static final java.lang.String ACTION_ANGLE_FOR_ANDROID_TOAST_MESSAGE = "android.app.action.ANGLE_FOR_ANDROID_TOAST_MESSAGE";
    private static final java.lang.String ANGLE_DRIVER_NAME = "angle";
    private static final long ANGLE_DRIVER_VERSION_CODE = 0;
    private static final java.lang.String ANGLE_DRIVER_VERSION_NAME = "";
    private static final int ANGLE_GL_DRIVER_ALL_ANGLE_OFF = 0;
    private static final int ANGLE_GL_DRIVER_ALL_ANGLE_ON = 1;
    private static final java.lang.String ANGLE_GL_DRIVER_CHOICE_ANGLE = "angle";
    private static final java.lang.String ANGLE_GL_DRIVER_CHOICE_DEFAULT = "default";
    private static final java.lang.String ANGLE_GL_DRIVER_CHOICE_NATIVE = "native";
    private static final boolean DEBUG = false;
    private static final java.lang.String INTENT_KEY_A4A_TOAST_MESSAGE = "A4A Toast Message";
    private static final java.lang.String METADATA_DEVELOPER_DRIVER_ENABLE = "com.android.graphics.developerdriver.enable";
    private static final java.lang.String METADATA_DRIVER_BUILD_TIME = "com.android.graphics.driver.build_time";
    private static final java.lang.String METADATA_INJECT_LAYERS_ENABLE = "com.android.graphics.injectLayers.enable";
    private static final java.lang.String PROPERTY_GFX_DRIVER_BUILD_TIME = "ro.gfx.driver_build_time";
    private static final java.lang.String PROPERTY_GFX_DRIVER_PRERELEASE = "ro.gfx.driver.1";
    private static final java.lang.String PROPERTY_GFX_DRIVER_PRODUCTION = "ro.gfx.driver.0";
    private static final java.lang.String SYSTEM_ANGLE_STRING = "system";
    private static final java.lang.String SYSTEM_DRIVER_NAME = "system";
    private static final long SYSTEM_DRIVER_VERSION_CODE = 0;
    private static final java.lang.String SYSTEM_DRIVER_VERSION_NAME = "";
    private static final java.lang.String TAG = "GraphicsEnvironment";
    private static final java.lang.String UPDATABLE_DRIVER_ALLOWLIST_ALL = "*";
    private static final int UPDATABLE_DRIVER_GLOBAL_OPT_IN_DEFAULT = 0;
    private static final int UPDATABLE_DRIVER_GLOBAL_OPT_IN_OFF = 3;
    private static final int UPDATABLE_DRIVER_GLOBAL_OPT_IN_PRERELEASE_DRIVER = 2;
    private static final int UPDATABLE_DRIVER_GLOBAL_OPT_IN_PRODUCTION_DRIVER = 1;
    private static final java.lang.String UPDATABLE_DRIVER_SPHAL_LIBRARIES_FILENAME = "sphal_libraries.txt";
    private static final int VULKAN_1_0 = 4194304;
    private static final int VULKAN_1_1 = 4198400;
    private static final int VULKAN_1_2 = 4202496;
    private static final int VULKAN_1_3 = 4206592;
    private static final android.os.GraphicsEnvironment sInstance = new android.os.GraphicsEnvironment();
    private java.lang.ClassLoader mClassLoader;
    private java.lang.String mLibraryPermittedPaths;
    private java.lang.String mLibrarySearchPaths;
    private int mAngleOptInIndex = -1;
    private boolean mShouldUseAngle = false;

    public static native void hintActivityLaunch();

    private static native boolean isDebuggable();

    private static native void nativeSetAngleInfo(java.lang.String str, boolean z, java.lang.String str2, java.lang.String[] strArr);

    private static native void nativeToggleAngleAsSystemDriver(boolean z);

    private static native void setDebugLayers(java.lang.String str);

    private static native void setDebugLayersGLES(java.lang.String str);

    private static native void setDriverPathAndSphalLibraries(java.lang.String str, java.lang.String str2);

    private static native void setGpuStats(java.lang.String str, java.lang.String str2, long j, long j2, java.lang.String str3, int i);

    private static native boolean setInjectLayersPrSetDumpable();

    private static native void setLayerPaths(java.lang.ClassLoader classLoader, java.lang.String str);

    public static android.os.GraphicsEnvironment getInstance() {
        return sInstance;
    }

    public void setup(android.content.Context context, android.os.Bundle bundle) {
        long j;
        android.content.pm.ApplicationInfo applicationInfo;
        java.lang.String str;
        long j2;
        android.content.pm.ApplicationInfo applicationInfo2;
        android.app.GameManager gameManager;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        java.lang.String packageName = context.getPackageName();
        android.content.pm.ApplicationInfo appInfoWithMetadata = getAppInfoWithMetadata(context, packageManager, packageName);
        android.os.Trace.traceBegin(2L, "setupGpuLayers");
        setupGpuLayers(context, bundle, packageManager, packageName, appInfoWithMetadata);
        android.os.Trace.traceEnd(2L);
        android.os.Trace.traceBegin(2L, "setupAngle");
        if (setupAngle(context, bundle, packageManager, packageName)) {
            this.mShouldUseAngle = true;
            j = 2;
            applicationInfo = appInfoWithMetadata;
            str = packageName;
            setGpuStats("angle", "", 0L, 0L, packageName, getVulkanVersion(packageManager));
        } else {
            j = 2;
            applicationInfo = appInfoWithMetadata;
            str = packageName;
        }
        android.os.Trace.traceEnd(j);
        long j3 = j;
        android.os.Trace.traceBegin(j3, "chooseDriver");
        android.content.pm.ApplicationInfo applicationInfo3 = applicationInfo;
        if (chooseDriver(context, bundle, packageManager, str, applicationInfo3)) {
            j2 = j3;
            applicationInfo2 = applicationInfo3;
        } else if (this.mShouldUseAngle) {
            j2 = j3;
            applicationInfo2 = applicationInfo3;
        } else {
            j2 = j3;
            applicationInfo2 = applicationInfo3;
            setGpuStats("system", "", 0L, android.os.SystemProperties.getLong(PROPERTY_GFX_DRIVER_BUILD_TIME, 0L), str, getVulkanVersion(packageManager));
        }
        android.os.Trace.traceEnd(j2);
        android.os.Trace.traceBegin(j2, "notifyGraphicsEnvironmentSetup");
        if (applicationInfo2.category == 0 && (gameManager = (android.app.GameManager) context.getSystemService(android.app.GameManager.class)) != null) {
            gameManager.notifyGraphicsEnvironmentSetup();
        }
        android.os.Trace.traceEnd(j2);
    }

    public void toggleAngleAsSystemDriver(boolean z) {
        nativeToggleAngleAsSystemDriver(z);
    }

    private java.lang.String queryAngleChoice(android.content.Context context, android.os.Bundle bundle, java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Log.v(TAG, "No package name specified; use the system driver");
            return "default";
        }
        return queryAngleChoiceInternal(context, bundle, str);
    }

    private int getVulkanVersion(android.content.pm.PackageManager packageManager) {
        if (packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, VULKAN_1_3)) {
            return VULKAN_1_3;
        }
        if (!packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, 4202496)) {
            if (!packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, VULKAN_1_1)) {
                if (packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_VULKAN_HARDWARE_VERSION, 4194304)) {
                    return 4194304;
                }
                return 0;
            }
            return VULKAN_1_1;
        }
        return 4202496;
    }

    private boolean canInjectLayers(android.content.pm.ApplicationInfo applicationInfo) {
        return applicationInfo.metaData != null && applicationInfo.metaData.getBoolean(METADATA_INJECT_LAYERS_ENABLE) && setInjectLayersPrSetDumpable();
    }

    public void setLayerPaths(java.lang.ClassLoader classLoader, java.lang.String str, java.lang.String str2) {
        this.mClassLoader = classLoader;
        this.mLibrarySearchPaths = str;
        this.mLibraryPermittedPaths = str2;
    }

    public java.lang.String getDebugLayerPathsFromSettings(android.os.Bundle bundle, android.content.pm.IPackageManager iPackageManager, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        if (!debugLayerEnabled(bundle, str, applicationInfo)) {
            return null;
        }
        android.util.Log.i(TAG, "GPU debug layers enabled for " + str);
        java.lang.String str2 = "";
        java.lang.String string = bundle.getString(android.provider.Settings.Global.GPU_DEBUG_LAYER_APP, "");
        if (!string.isEmpty()) {
            android.util.Log.i(TAG, "GPU debug layer apps: " + string);
            for (java.lang.String str3 : string.split(":")) {
                java.lang.String debugLayerAppPaths = getDebugLayerAppPaths(iPackageManager, str3);
                if (!debugLayerAppPaths.isEmpty()) {
                    str2 = str2 + debugLayerAppPaths + java.io.File.pathSeparator;
                }
            }
        }
        return str2;
    }

    private java.lang.String getDebugLayerAppPaths(android.content.pm.IPackageManager iPackageManager, java.lang.String str) {
        try {
            android.content.pm.ApplicationInfo applicationInfo = iPackageManager.getApplicationInfo(str, 131072L, android.os.UserHandle.myUserId());
            if (applicationInfo == null) {
                android.util.Log.w(TAG, "Debug layer app '" + str + "' not installed");
                return "";
            }
            java.lang.String chooseAbi = chooseAbi(applicationInfo);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(applicationInfo.nativeLibraryDir).append(java.io.File.pathSeparator).append(applicationInfo.sourceDir).append("!/lib/").append(chooseAbi);
            return sb.toString();
        } catch (android.os.RemoteException e) {
            return "";
        }
    }

    private boolean debugLayerEnabled(android.os.Bundle bundle, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        if ((!isDebuggable() && !canInjectLayers(applicationInfo)) || bundle.getInt(android.provider.Settings.Global.ENABLE_GPU_DEBUG_LAYERS, 0) == 0) {
            return false;
        }
        java.lang.String string = bundle.getString(android.provider.Settings.Global.GPU_DEBUG_APP, "");
        return (str == null || string.isEmpty() || str.isEmpty() || !string.equals(str)) ? false : true;
    }

    private void setupGpuLayers(android.content.Context context, android.os.Bundle bundle, android.content.pm.PackageManager packageManager, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        java.lang.String str2;
        if (!debugLayerEnabled(bundle, str, applicationInfo)) {
            str2 = "";
        } else {
            str2 = this.mLibraryPermittedPaths;
            java.lang.String string = bundle.getString(android.provider.Settings.Global.GPU_DEBUG_LAYERS);
            android.util.Log.i(TAG, "Vulkan debug layer list: " + string);
            if (string != null && !string.isEmpty()) {
                setDebugLayers(string);
            }
            java.lang.String string2 = bundle.getString(android.provider.Settings.Global.GPU_DEBUG_LAYERS_GLES);
            android.util.Log.i(TAG, "GLES debug layer list: " + string2);
            if (string2 != null && !string2.isEmpty()) {
                setDebugLayersGLES(string2);
            }
        }
        setLayerPaths(this.mClassLoader, str2 + this.mLibrarySearchPaths);
    }

    private static java.util.List<java.lang.String> getGlobalSettingsString(android.content.ContentResolver contentResolver, android.os.Bundle bundle, java.lang.String str) {
        java.lang.String string;
        if (bundle != null) {
            string = bundle.getString(str);
        } else {
            string = android.provider.Settings.Global.getString(contentResolver, str);
        }
        if (string != null) {
            return new java.util.ArrayList(java.util.Arrays.asList(string.split(",")));
        }
        return new java.util.ArrayList();
    }

    private static int getPackageIndex(java.lang.String str, java.util.List<java.lang.String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private static android.content.pm.ApplicationInfo getAppInfoWithMetadata(android.content.Context context, android.content.pm.PackageManager packageManager, java.lang.String str) {
        try {
            return packageManager.getApplicationInfo(str, 128);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return context.getApplicationInfo();
        }
    }

    private java.lang.String queryAngleChoiceInternal(android.content.Context context, android.os.Bundle bundle, java.lang.String str) {
        int i;
        if (android.text.TextUtils.isEmpty(str)) {
            return "default";
        }
        if (bundle == null) {
            i = android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.ANGLE_GL_DRIVER_ALL_ANGLE, 0);
        } else {
            i = bundle.getInt(android.provider.Settings.Global.ANGLE_GL_DRIVER_ALL_ANGLE);
        }
        if (i == 1) {
            android.util.Log.v(TAG, "Turn on ANGLE for all applications.");
            return "angle";
        }
        android.content.ContentResolver contentResolver = context.getContentResolver();
        java.util.List<java.lang.String> globalSettingsString = getGlobalSettingsString(contentResolver, bundle, android.provider.Settings.Global.ANGLE_GL_DRIVER_SELECTION_PKGS);
        java.util.List<java.lang.String> globalSettingsString2 = getGlobalSettingsString(contentResolver, bundle, android.provider.Settings.Global.ANGLE_GL_DRIVER_SELECTION_VALUES);
        android.util.Log.v(TAG, "Currently set values for:");
        android.util.Log.v(TAG, "  angle_gl_driver_selection_pkgs=" + globalSettingsString);
        android.util.Log.v(TAG, "  angle_gl_driver_selection_values=" + globalSettingsString2);
        if (globalSettingsString.size() == 0 || globalSettingsString.size() != globalSettingsString2.size()) {
            android.util.Log.v(TAG, "Global.Settings values are invalid: number of packages: " + globalSettingsString.size() + ", number of values: " + globalSettingsString2.size());
            return "default";
        }
        int packageIndex = getPackageIndex(str, globalSettingsString);
        if (packageIndex < 0) {
            android.util.Log.v(TAG, str + " is not listed in per-application setting");
            return "default";
        }
        this.mAngleOptInIndex = packageIndex;
        java.lang.String str2 = globalSettingsString2.get(packageIndex);
        android.util.Log.v(TAG, "ANGLE Developer option for '" + str + "' set to: '" + str2 + "'");
        return str2.equals("angle") ? "angle" : str2.equals(ANGLE_GL_DRIVER_CHOICE_NATIVE) ? ANGLE_GL_DRIVER_CHOICE_NATIVE : "default";
    }

    private java.lang.String getAnglePackageName(android.content.pm.PackageManager packageManager) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(new android.content.Intent(ACTION_ANGLE_FOR_ANDROID), 1048576);
        if (queryIntentActivities.size() != 1) {
            android.util.Log.v(TAG, "Invalid number of ANGLE packages. Required: 1, Found: " + queryIntentActivities.size());
            return "";
        }
        return queryIntentActivities.get(0).activityInfo.packageName;
    }

    private java.lang.String getAngleDebugPackage(android.content.Context context, android.os.Bundle bundle) {
        java.lang.String string;
        if (!isDebuggable()) {
            return "";
        }
        if (bundle != null) {
            string = bundle.getString(android.provider.Settings.Global.ANGLE_DEBUG_PACKAGE);
        } else {
            string = android.provider.Settings.Global.getString(context.getContentResolver(), android.provider.Settings.Global.ANGLE_DEBUG_PACKAGE);
        }
        return android.text.TextUtils.isEmpty(string) ? "" : string;
    }

    private boolean setupAngle(android.content.Context context, android.os.Bundle bundle, android.content.pm.PackageManager packageManager, java.lang.String str) {
        java.lang.String queryAngleChoice = queryAngleChoice(context, bundle, str);
        if (queryAngleChoice.equals("default")) {
            return false;
        }
        if (!queryAngleChoice.equals(ANGLE_GL_DRIVER_CHOICE_NATIVE)) {
            return setupAngleFromApk(context, bundle, packageManager, str) || setupAngleFromSystem(context, bundle, str);
        }
        nativeSetAngleInfo("", true, str, null);
        return false;
    }

    private boolean setupAngleFromApk(android.content.Context context, android.os.Bundle bundle, android.content.pm.PackageManager packageManager, java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo;
        java.lang.String angleDebugPackage = getAngleDebugPackage(context, bundle);
        if (angleDebugPackage.isEmpty()) {
            applicationInfo = null;
        } else {
            android.util.Log.v(TAG, "ANGLE debug package enabled: " + angleDebugPackage);
            try {
                applicationInfo = packageManager.getApplicationInfo(angleDebugPackage, 0);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.v(TAG, "ANGLE debug package '" + angleDebugPackage + "' not installed");
                return false;
            }
        }
        if (applicationInfo == null) {
            java.lang.String anglePackageName = getAnglePackageName(packageManager);
            if (android.text.TextUtils.isEmpty(anglePackageName)) {
                android.util.Log.v(TAG, "Failed to find ANGLE package.");
                return false;
            }
            android.util.Log.v(TAG, "ANGLE package enabled: " + anglePackageName);
            try {
                applicationInfo = packageManager.getApplicationInfo(anglePackageName, 1048576);
            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                android.util.Log.v(TAG, "ANGLE package '" + anglePackageName + "' not installed");
                return false;
            }
        }
        nativeSetAngleInfo(applicationInfo.nativeLibraryDir + java.io.File.pathSeparator + applicationInfo.sourceDir + "!/lib/" + chooseAbi(applicationInfo), false, str, getAngleEglFeatures(context, bundle));
        return true;
    }

    private boolean setupAngleFromSystem(android.content.Context context, android.os.Bundle bundle, java.lang.String str) {
        nativeSetAngleInfo("system", false, str, getAngleEglFeatures(context, bundle));
        return true;
    }

    private boolean shouldShowAngleInUseDialogBox(android.content.Context context) {
        try {
            if (android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.SHOW_ANGLE_IN_USE_DIALOG_BOX) != 1) {
                return false;
            }
            return true;
        } catch (android.provider.Settings.SettingNotFoundException | java.lang.SecurityException e) {
            return false;
        }
    }

    public void showAngleInUseDialogBox(android.content.Context context) {
        if (!shouldShowAngleInUseDialogBox(context) || !this.mShouldUseAngle) {
            return;
        }
        android.content.Intent intent = new android.content.Intent(ACTION_ANGLE_FOR_ANDROID_TOAST_MESSAGE);
        java.lang.String anglePackageName = getAnglePackageName(context.getPackageManager());
        if (anglePackageName.isEmpty()) {
            return;
        }
        intent.setPackage(anglePackageName);
        context.sendOrderedBroadcast(intent, null, new android.content.BroadcastReceiver() { // from class: android.os.GraphicsEnvironment.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent2) {
                android.widget.Toast.makeText(context2, getResultExtras(true).getString(android.os.GraphicsEnvironment.INTENT_KEY_A4A_TOAST_MESSAGE), 1).show();
            }
        }, null, -1, null, null);
    }

    private java.lang.String[] getAngleEglFeatures(android.content.Context context, android.os.Bundle bundle) {
        if (this.mAngleOptInIndex < 0) {
            return null;
        }
        java.util.List<java.lang.String> globalSettingsString = getGlobalSettingsString(context.getContentResolver(), bundle, android.provider.Settings.Global.ANGLE_EGL_FEATURES);
        if (globalSettingsString.size() <= this.mAngleOptInIndex) {
            return null;
        }
        return globalSettingsString.get(this.mAngleOptInIndex).split(":");
    }

    private java.lang.String chooseDriverInternal(android.os.Bundle bundle, android.content.pm.ApplicationInfo applicationInfo) {
        java.lang.String str = android.os.SystemProperties.get(PROPERTY_GFX_DRIVER_PRODUCTION);
        boolean z = true;
        boolean z2 = (str == null || str.isEmpty()) ? false : true;
        java.lang.String str2 = android.os.SystemProperties.get(PROPERTY_GFX_DRIVER_PRERELEASE);
        boolean z3 = (str2 == null || str2.isEmpty()) ? false : true;
        if (!z2 && !z3) {
            android.util.Log.v(TAG, "Neither updatable production driver nor prerelease driver is supported.");
            return null;
        }
        if (applicationInfo.isPrivilegedApp() || (applicationInfo.isSystemApp() && !applicationInfo.isUpdatedSystemApp())) {
            return null;
        }
        if ((applicationInfo.metaData == null || !applicationInfo.metaData.getBoolean(METADATA_DEVELOPER_DRIVER_ENABLE)) && !isDebuggable()) {
            z = false;
        }
        switch (bundle.getInt(android.provider.Settings.Global.UPDATABLE_DRIVER_ALL_APPS, 0)) {
            case 1:
                android.util.Log.v(TAG, "All apps opt in to use updatable production driver.");
                if (!z2) {
                    break;
                }
                break;
            case 2:
                android.util.Log.v(TAG, "All apps opt in to use updatable prerelease driver.");
                if (!z3 || !z) {
                    break;
                }
            case 3:
                android.util.Log.v(TAG, "The updatable driver is turned off on this device.");
                break;
            default:
                java.lang.String str3 = applicationInfo.packageName;
                if (getGlobalSettingsString(null, bundle, android.provider.Settings.Global.UPDATABLE_DRIVER_PRODUCTION_OPT_OUT_APPS).contains(str3)) {
                    android.util.Log.v(TAG, "App opts out for updatable production driver.");
                    break;
                } else if (getGlobalSettingsString(null, bundle, android.provider.Settings.Global.UPDATABLE_DRIVER_PRERELEASE_OPT_IN_APPS).contains(str3)) {
                    android.util.Log.v(TAG, "App opts in for updatable prerelease driver.");
                    if (!z3 || !z) {
                        break;
                    }
                } else if (!z2) {
                    android.util.Log.v(TAG, "Updatable production driver is not supported on the device.");
                    break;
                } else {
                    boolean contains = getGlobalSettingsString(null, bundle, android.provider.Settings.Global.UPDATABLE_DRIVER_PRODUCTION_OPT_IN_APPS).contains(str3);
                    java.util.List<java.lang.String> globalSettingsString = getGlobalSettingsString(null, bundle, android.provider.Settings.Global.UPDATABLE_DRIVER_PRODUCTION_ALLOWLIST);
                    if (!contains && globalSettingsString.indexOf("*") != 0 && !globalSettingsString.contains(str3)) {
                        android.util.Log.v(TAG, "App is not on the allowlist for updatable production driver.");
                        break;
                    } else if (!contains && getGlobalSettingsString(null, bundle, android.provider.Settings.Global.UPDATABLE_DRIVER_PRODUCTION_DENYLIST).contains(str3)) {
                        android.util.Log.v(TAG, "App is on the denylist for updatable production driver.");
                        break;
                    }
                }
                break;
        }
        return null;
    }

    private boolean chooseDriver(android.content.Context context, android.os.Bundle bundle, android.content.pm.PackageManager packageManager, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        java.lang.String chooseAbi;
        java.lang.String chooseDriverInternal = chooseDriverInternal(bundle, applicationInfo);
        if (chooseDriverInternal == null) {
            return false;
        }
        try {
            android.content.pm.PackageInfo packageInfo = packageManager.getPackageInfo(chooseDriverInternal, 1048704);
            android.content.pm.ApplicationInfo applicationInfo2 = packageInfo.applicationInfo;
            if (applicationInfo2.targetSdkVersion < 26 || (chooseAbi = chooseAbi(applicationInfo2)) == null) {
                return false;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(applicationInfo2.nativeLibraryDir).append(java.io.File.pathSeparator);
            sb.append(applicationInfo2.sourceDir).append("!/lib/").append(chooseAbi);
            java.lang.String sb2 = sb.toString();
            java.lang.String sphalLibraries = getSphalLibraries(context, chooseDriverInternal);
            android.util.Log.v(TAG, "Updatable driver package search path: " + sb2 + ", required sphal libraries: " + sphalLibraries);
            setDriverPathAndSphalLibraries(sb2, sphalLibraries);
            if (applicationInfo2.metaData == null) {
                throw new java.lang.NullPointerException("apk's meta-data cannot be null");
            }
            java.lang.String string = applicationInfo2.metaData.getString(METADATA_DRIVER_BUILD_TIME);
            if (string == null || string.length() <= 1) {
                android.util.Log.w(TAG, "com.android.graphics.driver.build_time is not set");
                string = "L0";
            }
            setGpuStats(chooseDriverInternal, packageInfo.versionName, applicationInfo2.longVersionCode, java.lang.Long.parseLong(string.substring(1)), str, 0);
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.w(TAG, "updatable driver package '" + chooseDriverInternal + "' not installed");
            return false;
        }
    }

    private static java.lang.String chooseAbi(android.content.pm.ApplicationInfo applicationInfo) {
        java.lang.String currentInstructionSet = dalvik.system.VMRuntime.getCurrentInstructionSet();
        if (applicationInfo.primaryCpuAbi != null && currentInstructionSet.equals(dalvik.system.VMRuntime.getInstructionSet(applicationInfo.primaryCpuAbi))) {
            return applicationInfo.primaryCpuAbi;
        }
        if (applicationInfo.secondaryCpuAbi != null && currentInstructionSet.equals(dalvik.system.VMRuntime.getInstructionSet(applicationInfo.secondaryCpuAbi))) {
            return applicationInfo.secondaryCpuAbi;
        }
        return null;
    }

    private java.lang.String getSphalLibraries(android.content.Context context, java.lang.String str) {
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.InputStreamReader(context.createPackageContext(str, 4).getAssets().open(UPDATABLE_DRIVER_SPHAL_LIBRARIES_FILENAME)));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    arrayList.add(readLine);
                } else {
                    return java.lang.String.join(":", arrayList);
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException e) {
            return "";
        }
    }
}
