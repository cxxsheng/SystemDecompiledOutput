package android.content.pm;

/* loaded from: classes.dex */
public class ApplicationInfo extends android.content.pm.PackageItemInfo implements android.os.Parcelable {
    public static final int AUTO_REVOKE_ALLOWED = 0;
    public static final int AUTO_REVOKE_DISALLOWED = 2;
    public static final int AUTO_REVOKE_DISCOURAGED = 1;
    public static final int CATEGORY_ACCESSIBILITY = 8;
    public static final int CATEGORY_AUDIO = 1;
    public static final int CATEGORY_GAME = 0;
    public static final int CATEGORY_IMAGE = 3;
    public static final int CATEGORY_MAPS = 6;
    public static final int CATEGORY_NEWS = 5;
    public static final int CATEGORY_PRODUCTIVITY = 7;
    public static final int CATEGORY_SOCIAL = 4;
    public static final int CATEGORY_UNDEFINED = -1;
    public static final int CATEGORY_VIDEO = 2;
    public static final int FLAG_ALLOW_BACKUP = 32768;
    public static final int FLAG_ALLOW_CLEAR_USER_DATA = 64;
    public static final int FLAG_ALLOW_TASK_REPARENTING = 32;
    public static final int FLAG_DEBUGGABLE = 2;
    public static final int FLAG_EXTERNAL_STORAGE = 262144;
    public static final int FLAG_EXTRACT_NATIVE_LIBS = 268435456;
    public static final int FLAG_FACTORY_TEST = 16;
    public static final int FLAG_FULL_BACKUP_ONLY = 67108864;
    public static final int FLAG_HARDWARE_ACCELERATED = 536870912;
    public static final int FLAG_HAS_CODE = 4;
    public static final int FLAG_INSTALLED = 8388608;
    public static final int FLAG_IS_DATA_ONLY = 16777216;

    @java.lang.Deprecated
    public static final int FLAG_IS_GAME = 33554432;
    public static final int FLAG_KILL_AFTER_RESTORE = 65536;
    public static final int FLAG_LARGE_HEAP = 1048576;
    public static final int FLAG_MULTIARCH = Integer.MIN_VALUE;
    public static final int FLAG_PERSISTENT = 8;
    public static final int FLAG_RESIZEABLE_FOR_SCREENS = 4096;
    public static final int FLAG_RESTORE_ANY_VERSION = 131072;
    public static final int FLAG_STOPPED = 2097152;
    public static final int FLAG_SUPPORTS_LARGE_SCREENS = 2048;
    public static final int FLAG_SUPPORTS_NORMAL_SCREENS = 1024;
    public static final int FLAG_SUPPORTS_RTL = 4194304;

    @java.lang.Deprecated
    public static final int FLAG_SUPPORTS_SCREEN_DENSITIES = 8192;
    public static final int FLAG_SUPPORTS_SMALL_SCREENS = 512;
    public static final int FLAG_SUPPORTS_XLARGE_SCREENS = 524288;
    public static final int FLAG_SUSPENDED = 1073741824;
    public static final int FLAG_SYSTEM = 1;
    public static final int FLAG_TEST_ONLY = 256;
    public static final int FLAG_UPDATED_SYSTEM_APP = 128;
    public static final int FLAG_USES_CLEARTEXT_TRAFFIC = 134217728;
    public static final int FLAG_VM_SAFE_MODE = 16384;
    public static final int GWP_ASAN_ALWAYS = 1;
    public static final int GWP_ASAN_DEFAULT = -1;
    public static final int GWP_ASAN_NEVER = 0;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_DEFAULT = -1;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_DISABLED = 0;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_ENABLED = 2;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int HIDDEN_API_ENFORCEMENT_JUST_WARN = 1;
    private static final int HIDDEN_API_ENFORCEMENT_MAX = 2;
    private static final int HIDDEN_API_ENFORCEMENT_MIN = -1;
    public static final int MEMTAG_ASYNC = 1;
    public static final int MEMTAG_DEFAULT = -1;
    public static final int MEMTAG_OFF = 0;
    public static final int MEMTAG_SYNC = 2;
    public static final java.lang.String METADATA_PRELOADED_FONTS = "preloaded_fonts";
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE = 1024;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION = 4096;
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE = 2048;
    public static final int PRIVATE_FLAG_ALLOW_AUDIO_PLAYBACK_CAPTURE = 134217728;
    public static final int PRIVATE_FLAG_ALLOW_CLEAR_USER_DATA_ON_FAILED_RESTORE = 67108864;
    public static final int PRIVATE_FLAG_ALLOW_NATIVE_HEAP_POINTER_TAGGING = Integer.MIN_VALUE;
    public static final int PRIVATE_FLAG_BACKUP_IN_FOREGROUND = 8192;
    public static final int PRIVATE_FLAG_CANT_SAVE_STATE = 2;
    public static final int PRIVATE_FLAG_DEFAULT_TO_DEVICE_PROTECTED_STORAGE = 32;
    public static final int PRIVATE_FLAG_DIRECT_BOOT_AWARE = 64;
    public static final int PRIVATE_FLAG_EXT_ALLOWLISTED_FOR_HIDDEN_APIS = 16;
    public static final int PRIVATE_FLAG_EXT_ATTRIBUTIONS_ARE_USER_VISIBLE = 4;
    public static final int PRIVATE_FLAG_EXT_CPU_OVERRIDE = 32;
    public static final int PRIVATE_FLAG_EXT_ENABLE_ON_BACK_INVOKED_CALLBACK = 8;
    public static final int PRIVATE_FLAG_EXT_PROFILEABLE = 1;
    public static final int PRIVATE_FLAG_EXT_REQUEST_FOREGROUND_SERVICE_EXEMPTION = 2;
    public static final int PRIVATE_FLAG_HAS_DOMAIN_URLS = 16;
    public static final int PRIVATE_FLAG_HAS_FRAGILE_USER_DATA = 16777216;
    public static final int PRIVATE_FLAG_HIDDEN = 1;
    public static final int PRIVATE_FLAG_INSTANT = 128;
    public static final int PRIVATE_FLAG_ISOLATED_SPLIT_LOADING = 32768;
    public static final int PRIVATE_FLAG_IS_RESOURCE_OVERLAY = 268435456;
    public static final int PRIVATE_FLAG_ODM = 1073741824;
    public static final int PRIVATE_FLAG_OEM = 131072;
    public static final int PRIVATE_FLAG_PARTIALLY_DIRECT_BOOT_AWARE = 256;
    public static final int PRIVATE_FLAG_PRIVILEGED = 8;
    public static final int PRIVATE_FLAG_PRODUCT = 524288;
    public static final int PRIVATE_FLAG_PROFILEABLE_BY_SHELL = 8388608;
    public static final int PRIVATE_FLAG_REQUEST_LEGACY_EXTERNAL_STORAGE = 536870912;
    public static final int PRIVATE_FLAG_REQUIRED_FOR_SYSTEM_USER = 512;
    public static final int PRIVATE_FLAG_SIGNED_WITH_PLATFORM_KEY = 1048576;
    public static final int PRIVATE_FLAG_STATIC_SHARED_LIBRARY = 16384;
    public static final int PRIVATE_FLAG_SYSTEM_EXT = 2097152;
    public static final int PRIVATE_FLAG_USES_NON_SDK_API = 4194304;
    public static final int PRIVATE_FLAG_USE_EMBEDDED_DEX = 33554432;
    public static final int PRIVATE_FLAG_VENDOR = 262144;
    public static final int PRIVATE_FLAG_VIRTUAL_PRELOAD = 65536;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_DEFAULT = 0;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_NOT_REQUESTED = 2;
    public static final int RAW_EXTERNAL_STORAGE_ACCESS_REQUESTED = 1;
    public static final int ZEROINIT_DEFAULT = -1;
    public static final int ZEROINIT_DISABLED = 0;
    public static final int ZEROINIT_ENABLED = 1;
    public boolean allowCrossUidActivitySwitchFromBelow;
    public java.lang.String appComponentFactory;
    public java.lang.String backupAgentName;
    public int category;
    public java.lang.String classLoaderName;
    public java.lang.String className;
    public int compatibleWidthLimitDp;
    public int compileSdkVersion;
    public java.lang.String compileSdkVersionCodename;
    public long createTimestamp;

    @android.annotation.SystemApi
    public java.lang.String credentialProtectedDataDir;
    public boolean crossProfile;
    public java.lang.String dataDir;
    public int dataExtractionRulesRes;
    public int descriptionRes;
    public java.lang.String deviceProtectedDataDir;
    public boolean enabled;
    public int enabledSetting;
    public int flags;
    public int fullBackupContent;
    private int gwpAsanMode;
    public boolean hiddenUntilInstalled;
    public int iconRes;
    public int installLocation;
    public int largestWidthLimitDp;
    private int localeConfigRes;
    public long longVersionCode;
    private android.util.ArrayMap<java.lang.String, java.lang.String> mAppClassNamesByProcess;
    private int mHiddenApiPolicy;
    private java.util.Set<java.lang.String> mKnownActivityEmbeddingCerts;
    public java.lang.String manageSpaceActivityName;
    public float maxAspectRatio;
    private int memtagMode;
    public float minAspectRatio;
    public int minSdkVersion;
    private int nativeHeapZeroInitialized;
    public java.lang.String nativeLibraryDir;
    public java.lang.String nativeLibraryRootDir;
    public boolean nativeLibraryRootRequiresIsa;
    public int networkSecurityConfigRes;
    public java.util.List<android.content.pm.SharedLibraryInfo> optionalSharedLibraryInfos;
    public java.lang.String[] overlayPaths;
    public java.lang.String permission;
    public java.lang.String primaryCpuAbi;
    public int privateFlags;
    public int privateFlagsExt;
    public java.lang.String processName;
    public java.lang.String publicSourceDir;
    private java.lang.Boolean requestRawExternalStorageAccess;
    public int requiresSmallestWidthDp;
    public java.lang.String[] resourceDirs;
    public int roundIconRes;
    public java.lang.String scanPublicSourceDir;
    public java.lang.String scanSourceDir;
    public java.lang.String seInfo;
    public java.lang.String seInfoUser;
    public java.lang.String secondaryCpuAbi;
    public java.lang.String secondaryNativeLibraryDir;
    public java.lang.String[] sharedLibraryFiles;
    public java.util.List<android.content.pm.SharedLibraryInfo> sharedLibraryInfos;
    public java.lang.String sourceDir;
    public java.lang.String[] splitClassLoaderNames;
    public android.util.SparseArray<int[]> splitDependencies;
    public java.lang.String[] splitNames;
    public java.lang.String[] splitPublicSourceDirs;
    public java.lang.String[] splitSourceDirs;
    public java.util.UUID storageUuid;

    @android.annotation.SystemApi
    public int targetSandboxVersion;
    public int targetSdkVersion;
    public java.lang.String taskAffinity;
    public int theme;
    public int uiOptions;
    public int uid;

    @java.lang.Deprecated
    public int versionCode;
    public java.lang.String volumeUuid;
    public java.lang.String zygotePreloadName;
    private static final com.android.internal.util.Parcelling.BuiltIn.ForBoolean sForBoolean = (com.android.internal.util.Parcelling.BuiltIn.ForBoolean) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForBoolean.class);
    private static final com.android.internal.util.Parcelling.BuiltIn.ForStringSet sForStringSet = (com.android.internal.util.Parcelling.BuiltIn.ForStringSet) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForStringSet.class);
    public static final android.os.Parcelable.Creator<android.content.pm.ApplicationInfo> CREATOR = new android.content.pm.ApplicationInfo.AnonymousClass1();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ApplicationInfoPrivateFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ApplicationInfoPrivateFlagsExt {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Category {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GwpAsanMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HiddenApiEnforcementPolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MemtagMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NativeHeapZeroInitialized {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RawExternalStorage {
    }

    public static java.lang.CharSequence getCategoryTitle(android.content.Context context, int i) {
        switch (i) {
            case 0:
                return context.getText(com.android.internal.R.string.app_category_game);
            case 1:
                return context.getText(com.android.internal.R.string.app_category_audio);
            case 2:
                return context.getText(com.android.internal.R.string.app_category_video);
            case 3:
                return context.getText(com.android.internal.R.string.app_category_image);
            case 4:
                return context.getText(com.android.internal.R.string.app_category_social);
            case 5:
                return context.getText(com.android.internal.R.string.app_category_news);
            case 6:
                return context.getText(com.android.internal.R.string.app_category_maps);
            case 7:
                return context.getText(com.android.internal.R.string.app_category_productivity);
            case 8:
                return context.getText(com.android.internal.R.string.app_category_accessibility);
            default:
                return null;
        }
    }

    public static boolean isValidHiddenApiEnforcementPolicy(int i) {
        return i >= -1 && i <= 2;
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        dump(printer, str, 3);
    }

    public void dump(android.util.Printer printer, java.lang.String str, int i) {
        super.dumpFront(printer, str);
        int i2 = i & 1;
        if (i2 != 0) {
            if (this.className != null) {
                printer.println(str + "className=" + this.className);
            }
            for (int i3 = 0; i3 < com.android.internal.util.ArrayUtils.size(this.mAppClassNamesByProcess); i3++) {
                printer.println(str + "  process=" + this.mAppClassNamesByProcess.keyAt(i3) + " className=" + this.mAppClassNamesByProcess.valueAt(i3));
            }
        }
        if (this.permission != null) {
            printer.println(str + "permission=" + this.permission);
        }
        printer.println(str + "processName=" + this.processName);
        if (i2 != 0) {
            printer.println(str + "taskAffinity=" + this.taskAffinity);
        }
        printer.println(str + "uid=" + this.uid + " flags=0x" + java.lang.Integer.toHexString(this.flags) + " privateFlags=0x" + java.lang.Integer.toHexString(this.privateFlags) + " theme=0x" + java.lang.Integer.toHexString(this.theme));
        if (i2 != 0) {
            printer.println(str + "requiresSmallestWidthDp=" + this.requiresSmallestWidthDp + " compatibleWidthLimitDp=" + this.compatibleWidthLimitDp + " largestWidthLimitDp=" + this.largestWidthLimitDp);
        }
        printer.println(str + "sourceDir=" + this.sourceDir);
        if (!java.util.Objects.equals(this.sourceDir, this.publicSourceDir)) {
            printer.println(str + "publicSourceDir=" + this.publicSourceDir);
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitSourceDirs)) {
            printer.println(str + "splitSourceDirs=" + java.util.Arrays.toString(this.splitSourceDirs));
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitPublicSourceDirs) && !java.util.Arrays.equals(this.splitSourceDirs, this.splitPublicSourceDirs)) {
            printer.println(str + "splitPublicSourceDirs=" + java.util.Arrays.toString(this.splitPublicSourceDirs));
        }
        if (this.resourceDirs != null) {
            printer.println(str + "resourceDirs=" + java.util.Arrays.toString(this.resourceDirs));
        }
        if (this.overlayPaths != null) {
            printer.println(str + "overlayPaths=" + java.util.Arrays.toString(this.overlayPaths));
        }
        if (i2 != 0 && this.seInfo != null) {
            printer.println(str + "seinfo=" + this.seInfo);
            printer.println(str + "seinfoUser=" + this.seInfoUser);
        }
        printer.println(str + "dataDir=" + this.dataDir);
        if (i2 != 0) {
            printer.println(str + "deviceProtectedDataDir=" + this.deviceProtectedDataDir);
            printer.println(str + "credentialProtectedDataDir=" + this.credentialProtectedDataDir);
            if (this.sharedLibraryFiles != null) {
                printer.println(str + "sharedLibraryFiles=" + java.util.Arrays.toString(this.sharedLibraryFiles));
            }
        }
        if (this.classLoaderName != null) {
            printer.println(str + "classLoaderName=" + this.classLoaderName);
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitClassLoaderNames)) {
            printer.println(str + "splitClassLoaderNames=" + java.util.Arrays.toString(this.splitClassLoaderNames));
        }
        printer.println(str + "enabled=" + this.enabled + " minSdkVersion=" + this.minSdkVersion + " targetSdkVersion=" + this.targetSdkVersion + " versionCode=" + this.longVersionCode + " targetSandboxVersion=" + this.targetSandboxVersion);
        if (i2 != 0) {
            if (this.manageSpaceActivityName != null) {
                printer.println(str + "manageSpaceActivityName=" + this.manageSpaceActivityName);
            }
            if (this.descriptionRes != 0) {
                printer.println(str + "description=0x" + java.lang.Integer.toHexString(this.descriptionRes));
            }
            if (this.uiOptions != 0) {
                printer.println(str + "uiOptions=0x" + java.lang.Integer.toHexString(this.uiOptions));
            }
            printer.println(str + "supportsRtl=" + (hasRtlSupport() ? "true" : "false"));
            if (this.fullBackupContent > 0) {
                printer.println(str + "fullBackupContent=@xml/" + this.fullBackupContent);
            } else {
                printer.println(str + "fullBackupContent=" + (this.fullBackupContent < 0 ? "false" : "true"));
            }
            if (this.dataExtractionRulesRes != 0) {
                printer.println(str + "dataExtractionRules=@xml/" + this.dataExtractionRulesRes);
            }
            printer.println(str + "crossProfile=" + (this.crossProfile ? "true" : "false"));
            if (this.networkSecurityConfigRes != 0) {
                printer.println(str + "networkSecurityConfigRes=0x" + java.lang.Integer.toHexString(this.networkSecurityConfigRes));
            }
            if (this.category != -1) {
                printer.println(str + "category=" + this.category);
            }
            printer.println(str + "HiddenApiEnforcementPolicy=" + getHiddenApiEnforcementPolicy());
            printer.println(str + "usesNonSdkApi=" + usesNonSdkApi());
            printer.println(str + "allowsPlaybackCapture=" + (isAudioPlaybackCaptureAllowed() ? "true" : "false"));
            if (this.gwpAsanMode != -1) {
                printer.println(str + "gwpAsanMode=" + this.gwpAsanMode);
            }
            if (this.memtagMode != -1) {
                printer.println(str + "memtagMode=" + this.memtagMode);
            }
            if (this.nativeHeapZeroInitialized != -1) {
                printer.println(str + "nativeHeapZeroInitialized=" + this.nativeHeapZeroInitialized);
            }
            if (this.requestRawExternalStorageAccess != null) {
                printer.println(str + "requestRawExternalStorageAccess=" + this.requestRawExternalStorageAccess);
            }
            if (this.localeConfigRes != 0) {
                printer.println(str + "localeConfigRes=0x" + java.lang.Integer.toHexString(this.localeConfigRes));
            }
            printer.println(str + "enableOnBackInvokedCallback=" + isOnBackInvokedCallbackEnabled());
            printer.println(str + "allowCrossUidActivitySwitchFromBelow=" + this.allowCrossUidActivitySwitchFromBelow);
        }
        printer.println(str + "createTimestamp=" + this.createTimestamp);
        if (this.mKnownActivityEmbeddingCerts != null) {
            printer.println(str + "knownActivityEmbeddingCerts=" + this.mKnownActivityEmbeddingCerts);
        }
        super.dumpBack(printer, str);
    }

    @Override // android.content.pm.PackageItemInfo
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        protoOutputStream.write(1138166333442L, this.permission);
        protoOutputStream.write(1138166333443L, this.processName);
        protoOutputStream.write(1120986464260L, this.uid);
        protoOutputStream.write(1120986464261L, this.flags);
        protoOutputStream.write(1120986464262L, this.privateFlags);
        protoOutputStream.write(1120986464263L, this.theme);
        protoOutputStream.write(1138166333448L, this.sourceDir);
        if (!java.util.Objects.equals(this.sourceDir, this.publicSourceDir)) {
            protoOutputStream.write(1138166333449L, this.publicSourceDir);
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitSourceDirs)) {
            for (java.lang.String str : this.splitSourceDirs) {
                protoOutputStream.write(2237677961226L, str);
            }
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitPublicSourceDirs) && !java.util.Arrays.equals(this.splitSourceDirs, this.splitPublicSourceDirs)) {
            for (java.lang.String str2 : this.splitPublicSourceDirs) {
                protoOutputStream.write(2237677961227L, str2);
            }
        }
        if (this.resourceDirs != null) {
            for (java.lang.String str3 : this.resourceDirs) {
                protoOutputStream.write(2237677961228L, str3);
            }
        }
        if (this.overlayPaths != null) {
            for (java.lang.String str4 : this.overlayPaths) {
                protoOutputStream.write(2237677961234L, str4);
            }
        }
        protoOutputStream.write(1138166333453L, this.dataDir);
        protoOutputStream.write(1138166333454L, this.classLoaderName);
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitClassLoaderNames)) {
            for (java.lang.String str5 : this.splitClassLoaderNames) {
                protoOutputStream.write(android.content.pm.ApplicationInfoProto.SPLIT_CLASS_LOADER_NAMES, str5);
            }
        }
        long start2 = protoOutputStream.start(1146756268048L);
        protoOutputStream.write(1133871366145L, this.enabled);
        protoOutputStream.write(1120986464258L, this.minSdkVersion);
        protoOutputStream.write(1120986464259L, this.targetSdkVersion);
        protoOutputStream.write(1120986464260L, this.longVersionCode);
        protoOutputStream.write(1120986464261L, this.targetSandboxVersion);
        protoOutputStream.end(start2);
        if ((i & 1) != 0) {
            long start3 = protoOutputStream.start(1146756268049L);
            if (this.className != null) {
                protoOutputStream.write(1138166333441L, this.className);
            }
            protoOutputStream.write(1138166333442L, this.taskAffinity);
            protoOutputStream.write(1120986464259L, this.requiresSmallestWidthDp);
            protoOutputStream.write(1120986464260L, this.compatibleWidthLimitDp);
            protoOutputStream.write(1120986464261L, this.largestWidthLimitDp);
            if (this.seInfo != null) {
                protoOutputStream.write(1138166333446L, this.seInfo);
                protoOutputStream.write(1138166333447L, this.seInfoUser);
            }
            protoOutputStream.write(1138166333448L, this.deviceProtectedDataDir);
            protoOutputStream.write(1138166333449L, this.credentialProtectedDataDir);
            if (this.sharedLibraryFiles != null) {
                for (java.lang.String str6 : this.sharedLibraryFiles) {
                    protoOutputStream.write(2237677961226L, str6);
                }
            }
            if (this.manageSpaceActivityName != null) {
                protoOutputStream.write(1138166333451L, this.manageSpaceActivityName);
            }
            if (this.descriptionRes != 0) {
                protoOutputStream.write(1120986464268L, this.descriptionRes);
            }
            if (this.uiOptions != 0) {
                protoOutputStream.write(1120986464269L, this.uiOptions);
            }
            protoOutputStream.write(1133871366158L, hasRtlSupport());
            if (this.fullBackupContent > 0) {
                protoOutputStream.write(1138166333455L, "@xml/" + this.fullBackupContent);
            } else {
                protoOutputStream.write(1133871366160L, this.fullBackupContent == 0);
            }
            if (this.networkSecurityConfigRes != 0) {
                protoOutputStream.write(1120986464273L, this.networkSecurityConfigRes);
            }
            if (this.category != -1) {
                protoOutputStream.write(1120986464274L, this.category);
            }
            if (this.gwpAsanMode != -1) {
                protoOutputStream.write(1120986464275L, this.gwpAsanMode);
            }
            if (this.memtagMode != -1) {
                protoOutputStream.write(1120986464276L, this.memtagMode);
            }
            if (this.nativeHeapZeroInitialized != -1) {
                protoOutputStream.write(1133871366165L, this.nativeHeapZeroInitialized);
            }
            protoOutputStream.write(1133871366166L, this.allowCrossUidActivitySwitchFromBelow);
            protoOutputStream.end(start3);
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.mKnownActivityEmbeddingCerts)) {
            java.util.Iterator<java.lang.String> it = this.mKnownActivityEmbeddingCerts.iterator();
            while (it.hasNext()) {
                protoOutputStream.write(android.content.pm.ApplicationInfoProto.KNOWN_ACTIVITY_EMBEDDING_CERTS, it.next());
            }
        }
        protoOutputStream.end(start);
    }

    public boolean hasRtlSupport() {
        return (this.flags & 4194304) == 4194304;
    }

    public boolean hasCode() {
        return (this.flags & 4) != 0;
    }

    public static class DisplayNameComparator implements java.util.Comparator<android.content.pm.ApplicationInfo> {
        private final android.content.pm.PackageManager mPM;
        private final java.text.Collator sCollator = java.text.Collator.getInstance();

        public DisplayNameComparator(android.content.pm.PackageManager packageManager) {
            this.mPM = packageManager;
        }

        @Override // java.util.Comparator
        public final int compare(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.ApplicationInfo applicationInfo2) {
            java.lang.CharSequence applicationLabel = this.mPM.getApplicationLabel(applicationInfo);
            if (applicationLabel == null) {
                applicationLabel = applicationInfo.packageName;
            }
            java.lang.CharSequence applicationLabel2 = this.mPM.getApplicationLabel(applicationInfo2);
            if (applicationLabel2 == null) {
                applicationLabel2 = applicationInfo2.packageName;
            }
            return this.sCollator.compare(applicationLabel.toString(), applicationLabel2.toString());
        }
    }

    public ApplicationInfo() {
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.createTimestamp = android.os.SystemClock.uptimeMillis();
    }

    public ApplicationInfo(android.content.pm.ApplicationInfo applicationInfo) {
        super(applicationInfo);
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.taskAffinity = applicationInfo.taskAffinity;
        this.permission = applicationInfo.permission;
        this.mKnownActivityEmbeddingCerts = applicationInfo.mKnownActivityEmbeddingCerts;
        this.processName = applicationInfo.processName;
        this.className = applicationInfo.className;
        this.theme = applicationInfo.theme;
        this.flags = applicationInfo.flags;
        this.privateFlags = applicationInfo.privateFlags;
        this.privateFlagsExt = applicationInfo.privateFlagsExt;
        this.requiresSmallestWidthDp = applicationInfo.requiresSmallestWidthDp;
        this.compatibleWidthLimitDp = applicationInfo.compatibleWidthLimitDp;
        this.largestWidthLimitDp = applicationInfo.largestWidthLimitDp;
        this.volumeUuid = applicationInfo.volumeUuid;
        this.storageUuid = applicationInfo.storageUuid;
        this.scanSourceDir = applicationInfo.scanSourceDir;
        this.scanPublicSourceDir = applicationInfo.scanPublicSourceDir;
        this.sourceDir = applicationInfo.sourceDir;
        this.publicSourceDir = applicationInfo.publicSourceDir;
        this.splitNames = applicationInfo.splitNames;
        this.splitSourceDirs = applicationInfo.splitSourceDirs;
        this.splitPublicSourceDirs = applicationInfo.splitPublicSourceDirs;
        this.splitDependencies = applicationInfo.splitDependencies;
        this.nativeLibraryDir = applicationInfo.nativeLibraryDir;
        this.secondaryNativeLibraryDir = applicationInfo.secondaryNativeLibraryDir;
        this.nativeLibraryRootDir = applicationInfo.nativeLibraryRootDir;
        this.nativeLibraryRootRequiresIsa = applicationInfo.nativeLibraryRootRequiresIsa;
        this.primaryCpuAbi = applicationInfo.primaryCpuAbi;
        this.secondaryCpuAbi = applicationInfo.secondaryCpuAbi;
        this.resourceDirs = applicationInfo.resourceDirs;
        this.overlayPaths = applicationInfo.overlayPaths;
        this.seInfo = applicationInfo.seInfo;
        this.seInfoUser = applicationInfo.seInfoUser;
        this.sharedLibraryFiles = applicationInfo.sharedLibraryFiles;
        this.sharedLibraryInfos = applicationInfo.sharedLibraryInfos;
        this.optionalSharedLibraryInfos = applicationInfo.optionalSharedLibraryInfos;
        this.dataDir = applicationInfo.dataDir;
        this.deviceProtectedDataDir = applicationInfo.deviceProtectedDataDir;
        this.credentialProtectedDataDir = applicationInfo.credentialProtectedDataDir;
        this.uid = applicationInfo.uid;
        this.minSdkVersion = applicationInfo.minSdkVersion;
        this.targetSdkVersion = applicationInfo.targetSdkVersion;
        setVersionCode(applicationInfo.longVersionCode);
        this.enabled = applicationInfo.enabled;
        this.enabledSetting = applicationInfo.enabledSetting;
        this.installLocation = applicationInfo.installLocation;
        this.manageSpaceActivityName = applicationInfo.manageSpaceActivityName;
        this.descriptionRes = applicationInfo.descriptionRes;
        this.uiOptions = applicationInfo.uiOptions;
        this.backupAgentName = applicationInfo.backupAgentName;
        this.fullBackupContent = applicationInfo.fullBackupContent;
        this.dataExtractionRulesRes = applicationInfo.dataExtractionRulesRes;
        this.crossProfile = applicationInfo.crossProfile;
        this.networkSecurityConfigRes = applicationInfo.networkSecurityConfigRes;
        this.category = applicationInfo.category;
        this.targetSandboxVersion = applicationInfo.targetSandboxVersion;
        this.classLoaderName = applicationInfo.classLoaderName;
        this.splitClassLoaderNames = applicationInfo.splitClassLoaderNames;
        this.appComponentFactory = applicationInfo.appComponentFactory;
        this.iconRes = applicationInfo.iconRes;
        this.roundIconRes = applicationInfo.roundIconRes;
        this.compileSdkVersion = applicationInfo.compileSdkVersion;
        this.compileSdkVersionCodename = applicationInfo.compileSdkVersionCodename;
        this.mHiddenApiPolicy = applicationInfo.mHiddenApiPolicy;
        this.hiddenUntilInstalled = applicationInfo.hiddenUntilInstalled;
        this.zygotePreloadName = applicationInfo.zygotePreloadName;
        this.gwpAsanMode = applicationInfo.gwpAsanMode;
        this.memtagMode = applicationInfo.memtagMode;
        this.nativeHeapZeroInitialized = applicationInfo.nativeHeapZeroInitialized;
        this.requestRawExternalStorageAccess = applicationInfo.requestRawExternalStorageAccess;
        this.localeConfigRes = applicationInfo.localeConfigRes;
        this.allowCrossUidActivitySwitchFromBelow = applicationInfo.allowCrossUidActivitySwitchFromBelow;
        this.createTimestamp = android.os.SystemClock.uptimeMillis();
    }

    public java.lang.String toString() {
        return "ApplicationInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.packageName + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (parcel.maybeWriteSquashed(this)) {
            return;
        }
        super.writeToParcel(parcel, i);
        parcel.writeString8(this.taskAffinity);
        parcel.writeString8(this.permission);
        parcel.writeString8(this.processName);
        parcel.writeString8(this.className);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.privateFlags);
        parcel.writeInt(this.privateFlagsExt);
        parcel.writeInt(this.requiresSmallestWidthDp);
        parcel.writeInt(this.compatibleWidthLimitDp);
        parcel.writeInt(this.largestWidthLimitDp);
        if (this.storageUuid != null) {
            parcel.writeInt(1);
            parcel.writeLong(this.storageUuid.getMostSignificantBits());
            parcel.writeLong(this.storageUuid.getLeastSignificantBits());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.scanSourceDir);
        parcel.writeString8(this.scanPublicSourceDir);
        parcel.writeString8(this.sourceDir);
        parcel.writeString8(this.publicSourceDir);
        parcel.writeString8Array(this.splitNames);
        parcel.writeString8Array(this.splitSourceDirs);
        parcel.writeString8Array(this.splitPublicSourceDirs);
        parcel.writeSparseArray(this.splitDependencies);
        parcel.writeString8(this.nativeLibraryDir);
        parcel.writeString8(this.secondaryNativeLibraryDir);
        parcel.writeString8(this.nativeLibraryRootDir);
        parcel.writeInt(this.nativeLibraryRootRequiresIsa ? 1 : 0);
        parcel.writeString8(this.primaryCpuAbi);
        parcel.writeString8(this.secondaryCpuAbi);
        parcel.writeString8Array(this.resourceDirs);
        parcel.writeString8Array(this.overlayPaths);
        parcel.writeString8(this.seInfo);
        parcel.writeString8(this.seInfoUser);
        parcel.writeString8Array(this.sharedLibraryFiles);
        parcel.writeTypedList(this.sharedLibraryInfos);
        parcel.writeTypedList(this.optionalSharedLibraryInfos);
        parcel.writeString8(this.dataDir);
        parcel.writeString8(this.deviceProtectedDataDir);
        parcel.writeString8(this.credentialProtectedDataDir);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.minSdkVersion);
        parcel.writeInt(this.targetSdkVersion);
        parcel.writeLong(this.longVersionCode);
        parcel.writeInt(this.enabled ? 1 : 0);
        parcel.writeInt(this.enabledSetting);
        parcel.writeInt(this.installLocation);
        parcel.writeString8(this.manageSpaceActivityName);
        parcel.writeString8(this.backupAgentName);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.uiOptions);
        parcel.writeInt(this.fullBackupContent);
        parcel.writeInt(this.dataExtractionRulesRes);
        parcel.writeBoolean(this.crossProfile);
        parcel.writeInt(this.networkSecurityConfigRes);
        parcel.writeInt(this.category);
        parcel.writeInt(this.targetSandboxVersion);
        parcel.writeString8(this.classLoaderName);
        parcel.writeString8Array(this.splitClassLoaderNames);
        parcel.writeInt(this.compileSdkVersion);
        parcel.writeString8(this.compileSdkVersionCodename);
        parcel.writeString8(this.appComponentFactory);
        parcel.writeInt(this.iconRes);
        parcel.writeInt(this.roundIconRes);
        parcel.writeInt(this.mHiddenApiPolicy);
        parcel.writeInt(this.hiddenUntilInstalled ? 1 : 0);
        parcel.writeString8(this.zygotePreloadName);
        parcel.writeInt(this.gwpAsanMode);
        parcel.writeInt(this.memtagMode);
        parcel.writeInt(this.nativeHeapZeroInitialized);
        sForBoolean.parcel(this.requestRawExternalStorageAccess, parcel, i);
        parcel.writeLong(this.createTimestamp);
        if (this.mAppClassNamesByProcess == null) {
            parcel.writeInt(0);
        } else {
            int size = this.mAppClassNamesByProcess.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeString(this.mAppClassNamesByProcess.keyAt(i2));
                parcel.writeString(this.mAppClassNamesByProcess.valueAt(i2));
            }
        }
        parcel.writeInt(this.localeConfigRes);
        parcel.writeInt(this.allowCrossUidActivitySwitchFromBelow ? 1 : 0);
        sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, parcel, this.flags);
    }

    /* renamed from: android.content.pm.ApplicationInfo$1, reason: invalid class name */
    class AnonymousClass1 implements android.os.Parcelable.Creator<android.content.pm.ApplicationInfo> {
        /* renamed from: $r8$lambda$1E1P6HJEl7Ns7qcxzJ0zM-xcHGA, reason: not valid java name */
        public static /* synthetic */ android.content.pm.ApplicationInfo m850$r8$lambda$1E1P6HJEl7Ns7qcxzJ0zMxcHGA(android.os.Parcel parcel) {
            return new android.content.pm.ApplicationInfo(parcel);
        }

        AnonymousClass1() {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ApplicationInfo createFromParcel(android.os.Parcel parcel) {
            return (android.content.pm.ApplicationInfo) parcel.readSquashed(new android.os.Parcel.SquashReadHelper() { // from class: android.content.pm.ApplicationInfo$1$$ExternalSyntheticLambda0
                @Override // android.os.Parcel.SquashReadHelper
                public final java.lang.Object readRawParceled(android.os.Parcel parcel2) {
                    return android.content.pm.ApplicationInfo.AnonymousClass1.m850$r8$lambda$1E1P6HJEl7Ns7qcxzJ0zMxcHGA(parcel2);
                }
            });
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ApplicationInfo[] newArray(int i) {
            return new android.content.pm.ApplicationInfo[i];
        }
    }

    private ApplicationInfo(android.os.Parcel parcel) {
        super(parcel);
        boolean z;
        boolean z2;
        boolean z3;
        this.fullBackupContent = 0;
        this.dataExtractionRulesRes = 0;
        this.uiOptions = 0;
        this.flags = 0;
        this.requiresSmallestWidthDp = 0;
        this.compatibleWidthLimitDp = 0;
        this.largestWidthLimitDp = 0;
        this.enabled = true;
        this.enabledSetting = 0;
        this.installLocation = -1;
        this.category = -1;
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.allowCrossUidActivitySwitchFromBelow = true;
        this.mHiddenApiPolicy = -1;
        this.taskAffinity = parcel.readString8();
        this.permission = parcel.readString8();
        this.processName = parcel.readString8();
        this.className = parcel.readString8();
        this.theme = parcel.readInt();
        this.flags = parcel.readInt();
        this.privateFlags = parcel.readInt();
        this.privateFlagsExt = parcel.readInt();
        this.requiresSmallestWidthDp = parcel.readInt();
        this.compatibleWidthLimitDp = parcel.readInt();
        this.largestWidthLimitDp = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.storageUuid = new java.util.UUID(parcel.readLong(), parcel.readLong());
            this.volumeUuid = android.os.storage.StorageManager.convert(this.storageUuid);
        }
        this.scanSourceDir = parcel.readString8();
        this.scanPublicSourceDir = parcel.readString8();
        this.sourceDir = parcel.readString8();
        this.publicSourceDir = parcel.readString8();
        this.splitNames = parcel.createString8Array();
        this.splitSourceDirs = parcel.createString8Array();
        this.splitPublicSourceDirs = parcel.createString8Array();
        this.splitDependencies = parcel.readSparseArray(null, int[].class);
        this.nativeLibraryDir = parcel.readString8();
        this.secondaryNativeLibraryDir = parcel.readString8();
        this.nativeLibraryRootDir = parcel.readString8();
        if (parcel.readInt() == 0) {
            z = false;
        } else {
            z = true;
        }
        this.nativeLibraryRootRequiresIsa = z;
        this.primaryCpuAbi = parcel.readString8();
        this.secondaryCpuAbi = parcel.readString8();
        this.resourceDirs = parcel.createString8Array();
        this.overlayPaths = parcel.createString8Array();
        this.seInfo = parcel.readString8();
        this.seInfoUser = parcel.readString8();
        this.sharedLibraryFiles = parcel.createString8Array();
        this.sharedLibraryInfos = parcel.createTypedArrayList(android.content.pm.SharedLibraryInfo.CREATOR);
        this.optionalSharedLibraryInfos = parcel.createTypedArrayList(android.content.pm.SharedLibraryInfo.CREATOR);
        this.dataDir = parcel.readString8();
        this.deviceProtectedDataDir = parcel.readString8();
        this.credentialProtectedDataDir = parcel.readString8();
        this.uid = parcel.readInt();
        this.minSdkVersion = parcel.readInt();
        this.targetSdkVersion = parcel.readInt();
        setVersionCode(parcel.readLong());
        if (parcel.readInt() == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.enabled = z2;
        this.enabledSetting = parcel.readInt();
        this.installLocation = parcel.readInt();
        this.manageSpaceActivityName = parcel.readString8();
        this.backupAgentName = parcel.readString8();
        this.descriptionRes = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.fullBackupContent = parcel.readInt();
        this.dataExtractionRulesRes = parcel.readInt();
        this.crossProfile = parcel.readBoolean();
        this.networkSecurityConfigRes = parcel.readInt();
        this.category = parcel.readInt();
        this.targetSandboxVersion = parcel.readInt();
        this.classLoaderName = parcel.readString8();
        this.splitClassLoaderNames = parcel.createString8Array();
        this.compileSdkVersion = parcel.readInt();
        this.compileSdkVersionCodename = parcel.readString8();
        this.appComponentFactory = parcel.readString8();
        this.iconRes = parcel.readInt();
        this.roundIconRes = parcel.readInt();
        this.mHiddenApiPolicy = parcel.readInt();
        if (parcel.readInt() == 0) {
            z3 = false;
        } else {
            z3 = true;
        }
        this.hiddenUntilInstalled = z3;
        this.zygotePreloadName = parcel.readString8();
        this.gwpAsanMode = parcel.readInt();
        this.memtagMode = parcel.readInt();
        this.nativeHeapZeroInitialized = parcel.readInt();
        this.requestRawExternalStorageAccess = sForBoolean.unparcel(parcel);
        this.createTimestamp = parcel.readLong();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.mAppClassNamesByProcess = new android.util.ArrayMap<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.mAppClassNamesByProcess.put(parcel.readString(), parcel.readString());
            }
        }
        this.localeConfigRes = parcel.readInt();
        this.allowCrossUidActivitySwitchFromBelow = parcel.readInt() != 0;
        this.mKnownActivityEmbeddingCerts = sForStringSet.unparcel(parcel);
        if (this.mKnownActivityEmbeddingCerts.isEmpty()) {
            this.mKnownActivityEmbeddingCerts = null;
        }
    }

    public java.lang.CharSequence loadDescription(android.content.pm.PackageManager packageManager) {
        java.lang.CharSequence text;
        if (this.descriptionRes != 0 && (text = packageManager.getText(this.packageName, this.descriptionRes, this)) != null) {
            return text;
        }
        return null;
    }

    public void disableCompatibilityMode() {
        this.flags |= 540160;
    }

    public boolean usesCompatibilityMode() {
        return this.targetSdkVersion < 4 || (this.flags & 540160) == 0;
    }

    public void initForUser(int i) {
        this.uid = android.os.UserHandle.getUid(i, android.os.UserHandle.getAppId(this.uid));
        if ("android".equals(this.packageName)) {
            this.dataDir = android.os.Environment.getDataSystemDirectory().getAbsolutePath();
            return;
        }
        this.deviceProtectedDataDir = android.os.Environment.getDataUserDePackageDirectory(this.volumeUuid, i, this.packageName).getAbsolutePath();
        this.credentialProtectedDataDir = android.os.Environment.getDataUserCePackageDirectory(this.volumeUuid, i, this.packageName).getAbsolutePath();
        if ((this.privateFlags & 32) != 0) {
            this.dataDir = this.deviceProtectedDataDir;
        } else {
            this.dataDir = this.credentialProtectedDataDir;
        }
    }

    private boolean isPackageWhitelistedForHiddenApis() {
        return (this.privateFlagsExt & 16) != 0;
    }

    public boolean usesNonSdkApi() {
        return (this.privateFlags & 4194304) != 0;
    }

    @android.annotation.SystemApi
    public boolean hasFragileUserData() {
        return (this.privateFlags & 16777216) != 0;
    }

    public boolean isAudioPlaybackCaptureAllowed() {
        return (this.privateFlags & 134217728) != 0;
    }

    public boolean hasRequestedLegacyExternalStorage() {
        return (this.privateFlags & 536870912) != 0;
    }

    public int getRequestRawExternalStorageAccess() {
        if (this.requestRawExternalStorageAccess == null) {
            return 0;
        }
        return this.requestRawExternalStorageAccess.booleanValue() ? 1 : 2;
    }

    public boolean allowsNativeHeapPointerTagging() {
        return (this.privateFlags & Integer.MIN_VALUE) != 0;
    }

    private boolean isAllowedToUseHiddenApis() {
        if (isSignedWithPlatformKey()) {
            return true;
        }
        if (isSystemApp() || isUpdatedSystemApp()) {
            return usesNonSdkApi() || isPackageWhitelistedForHiddenApis();
        }
        return false;
    }

    public int getHiddenApiEnforcementPolicy() {
        if (isAllowedToUseHiddenApis()) {
            return 0;
        }
        if (this.mHiddenApiPolicy != -1) {
            return this.mHiddenApiPolicy;
        }
        return 2;
    }

    public void setHiddenApiEnforcementPolicy(int i) {
        if (!isValidHiddenApiEnforcementPolicy(i)) {
            throw new java.lang.IllegalArgumentException("Invalid API enforcement policy: " + i);
        }
        this.mHiddenApiPolicy = i;
    }

    public void maybeUpdateHiddenApiEnforcementPolicy(int i) {
        if (isPackageWhitelistedForHiddenApis()) {
            return;
        }
        setHiddenApiEnforcementPolicy(i);
    }

    public void setVersionCode(long j) {
        this.longVersionCode = j;
        this.versionCode = (int) j;
    }

    @Override // android.content.pm.PackageItemInfo
    public android.graphics.drawable.Drawable loadDefaultIcon(android.content.pm.PackageManager packageManager) {
        if ((this.flags & 262144) != 0 && isPackageUnavailable(packageManager)) {
            return android.content.res.Resources.getSystem().getDrawable(com.android.internal.R.drawable.sym_app_on_sd_unavailable_icon);
        }
        return packageManager.getDefaultActivityIcon();
    }

    private boolean isPackageUnavailable(android.content.pm.PackageManager packageManager) {
        try {
            return packageManager.getPackageInfo(this.packageName, 0) == null;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    public boolean isDefaultToDeviceProtectedStorage() {
        return (this.privateFlags & 32) != 0;
    }

    public boolean isDirectBootAware() {
        return (this.privateFlags & 64) != 0;
    }

    @android.annotation.SystemApi
    public boolean isEncryptionAware() {
        return isDirectBootAware() || isPartiallyDirectBootAware();
    }

    public boolean isExternal() {
        return (this.flags & 262144) != 0;
    }

    @android.annotation.SystemApi
    public boolean isInstantApp() {
        return (this.privateFlags & 128) != 0;
    }

    public boolean isInternal() {
        return (this.flags & 262144) == 0;
    }

    @android.annotation.SystemApi
    public boolean isOem() {
        return (this.privateFlags & 131072) != 0;
    }

    public boolean isOdm() {
        return (this.privateFlags & 1073741824) != 0;
    }

    public boolean isPartiallyDirectBootAware() {
        return (this.privateFlags & 256) != 0;
    }

    public boolean isSignedWithPlatformKey() {
        return (this.privateFlags & 1048576) != 0;
    }

    @android.annotation.SystemApi
    public boolean isPrivilegedApp() {
        return (this.privateFlags & 8) != 0;
    }

    public boolean isRequiredForSystemUser() {
        return (this.privateFlags & 512) != 0;
    }

    public boolean isStaticSharedLibrary() {
        return (this.privateFlags & 16384) != 0;
    }

    public boolean isSystemApp() {
        return (this.flags & 1) != 0;
    }

    public boolean isUpdatedSystemApp() {
        return (this.flags & 128) != 0;
    }

    @android.annotation.SystemApi
    public boolean isVendor() {
        return (this.privateFlags & 262144) != 0;
    }

    @android.annotation.SystemApi
    public boolean isProduct() {
        return (this.privateFlags & 524288) != 0;
    }

    public boolean isSystemExt() {
        return (this.privateFlags & 2097152) != 0;
    }

    public boolean isEmbeddedDexUsed() {
        return (this.privateFlags & 33554432) != 0;
    }

    public boolean isVirtualPreload() {
        return (this.privateFlags & 65536) != 0;
    }

    public boolean isProfileableByShell() {
        return (this.privateFlags & 8388608) != 0;
    }

    public boolean isProfileable() {
        return (this.privateFlagsExt & 1) != 0;
    }

    public boolean areAttributionsUserVisible() {
        return (this.privateFlagsExt & 4) != 0;
    }

    public boolean requestsIsolatedSplitLoading() {
        return (this.privateFlags & 32768) != 0;
    }

    public boolean isResourceOverlay() {
        return (this.privateFlags & 268435456) != 0;
    }

    public boolean isChangeEnabled(long j) {
        return android.app.compat.CompatChanges.isChangeEnabled(j, this.packageName, android.os.UserHandle.getUserHandleForUid(this.uid));
    }

    public boolean hasRequestForegroundServiceExemption() {
        return (this.privateFlagsExt & 2) != 0;
    }

    public boolean isOnBackInvokedCallbackEnabled() {
        return (this.privateFlagsExt & 8) != 0;
    }

    @Override // android.content.pm.PackageItemInfo
    protected android.content.pm.ApplicationInfo getApplicationInfo() {
        return this;
    }

    public java.lang.String[] getAllApkPaths() {
        java.lang.String[][] strArr = {this.splitSourceDirs, this.sharedLibraryFiles, this.resourceDirs, this.overlayPaths};
        java.util.ArrayList arrayList = new java.util.ArrayList(10);
        if (this.sourceDir != null) {
            arrayList.add(this.sourceDir);
        }
        for (int i = 0; i < 4; i++) {
            java.lang.String[] strArr2 = strArr[i];
            if (strArr2 != null) {
                for (java.lang.String str : strArr2) {
                    arrayList.add(str);
                }
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
    }

    public void setCodePath(java.lang.String str) {
        this.scanSourceDir = str;
    }

    public void setBaseCodePath(java.lang.String str) {
        this.sourceDir = str;
    }

    public void setSplitCodePaths(java.lang.String[] strArr) {
        this.splitSourceDirs = strArr;
    }

    public void setResourcePath(java.lang.String str) {
        this.scanPublicSourceDir = str;
    }

    public void setBaseResourcePath(java.lang.String str) {
        this.publicSourceDir = str;
    }

    public void setSplitResourcePaths(java.lang.String[] strArr) {
        this.splitPublicSourceDirs = strArr;
    }

    public void setGwpAsanMode(int i) {
        this.gwpAsanMode = i;
    }

    public void setMemtagMode(int i) {
        this.memtagMode = i;
    }

    public void setNativeHeapZeroInitialized(int i) {
        this.nativeHeapZeroInitialized = i;
    }

    public void setRequestRawExternalStorageAccess(java.lang.Boolean bool) {
        this.requestRawExternalStorageAccess = bool;
    }

    public void setAppClassNamesByProcess(android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap) {
        if (com.android.internal.util.ArrayUtils.size(arrayMap) == 0) {
            this.mAppClassNamesByProcess = null;
        } else {
            this.mAppClassNamesByProcess = arrayMap;
        }
    }

    public java.lang.String getCodePath() {
        return this.scanSourceDir;
    }

    public java.lang.String getBaseCodePath() {
        return this.sourceDir;
    }

    public java.lang.String[] getSplitCodePaths() {
        return this.splitSourceDirs;
    }

    public java.lang.String getResourcePath() {
        return this.scanPublicSourceDir;
    }

    public java.lang.String getBaseResourcePath() {
        return this.publicSourceDir;
    }

    public java.lang.String[] getSplitResourcePaths() {
        return this.splitPublicSourceDirs;
    }

    public int getGwpAsanMode() {
        return this.gwpAsanMode;
    }

    public int getMemtagMode() {
        return this.memtagMode;
    }

    public int getNativeHeapZeroInitialized() {
        return this.nativeHeapZeroInitialized;
    }

    public java.lang.String getCustomApplicationClassNameForProcess(java.lang.String str) {
        java.lang.String str2;
        if (this.mAppClassNamesByProcess != null && (str2 = this.mAppClassNamesByProcess.get(str)) != null) {
            return str2;
        }
        return this.className;
    }

    public void setLocaleConfigRes(int i) {
        this.localeConfigRes = i;
    }

    public int getLocaleConfigRes() {
        return this.localeConfigRes;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.List<android.content.pm.SharedLibraryInfo> getSharedLibraryInfos() {
        if (this.sharedLibraryInfos == null) {
            return java.util.Collections.EMPTY_LIST;
        }
        return this.sharedLibraryInfos;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.List<android.content.pm.SharedLibraryInfo> getOptionalSharedLibraryInfos() {
        if (this.optionalSharedLibraryInfos == null) {
            return java.util.Collections.EMPTY_LIST;
        }
        return this.optionalSharedLibraryInfos;
    }

    public java.util.Set<java.lang.String> getKnownActivityEmbeddingCerts() {
        return this.mKnownActivityEmbeddingCerts == null ? java.util.Collections.emptySet() : this.mKnownActivityEmbeddingCerts;
    }

    public void setKnownActivityEmbeddingCerts(java.util.Set<java.lang.String> set) {
        this.mKnownActivityEmbeddingCerts = new android.util.ArraySet();
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            this.mKnownActivityEmbeddingCerts.add(it.next().toUpperCase(java.util.Locale.US));
        }
    }

    public void setEnableOnBackInvokedCallback(boolean z) {
        if (z) {
            this.privateFlagsExt |= 8;
        } else {
            this.privateFlagsExt &= -9;
        }
    }
}
