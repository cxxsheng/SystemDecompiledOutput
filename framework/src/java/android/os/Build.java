package android.os;

/* loaded from: classes3.dex */
public class Build {

    @java.lang.Deprecated
    public static final java.lang.String CPU_ABI;

    @java.lang.Deprecated
    public static final java.lang.String CPU_ABI2;
    public static final java.lang.String FINGERPRINT;
    public static final java.lang.String HOST;
    public static final int HW_TIMEOUT_MULTIPLIER;
    public static final boolean IS_ARC;
    public static final boolean IS_DEBUGGABLE;
    public static final boolean IS_ENG;
    public static final boolean IS_TREBLE_ENABLED;
    public static final boolean IS_USER;
    public static final boolean IS_USERDEBUG;

    @android.annotation.SystemApi
    public static final boolean PERMISSIONS_REVIEW_REQUIRED = true;
    private static final java.lang.String TAG = "Build";
    public static final java.lang.String TAGS;
    public static final long TIME;
    public static final java.lang.String TYPE;
    public static final java.lang.String UNKNOWN = "unknown";
    public static final java.lang.String USER;
    public static final java.lang.String ID = getString("ro.build.id");
    public static final java.lang.String DISPLAY = getString("ro.build.display.id");
    public static final java.lang.String PRODUCT = getString("ro.product.name");
    public static final java.lang.String PRODUCT_FOR_ATTESTATION = getVendorDeviceIdProperty("name");
    public static final java.lang.String DEVICE = getString("ro.product.device");
    public static final java.lang.String DEVICE_FOR_ATTESTATION = getVendorDeviceIdProperty(android.hardware.usb.UsbManager.EXTRA_DEVICE);
    public static final java.lang.String BOARD = getString("ro.product.board");
    public static final java.lang.String MANUFACTURER = getString("ro.product.manufacturer");
    public static final java.lang.String MANUFACTURER_FOR_ATTESTATION = getVendorDeviceIdProperty(android.media.midi.MidiDeviceInfo.PROPERTY_MANUFACTURER);
    public static final java.lang.String BRAND = getString("ro.product.brand");
    public static final java.lang.String BRAND_FOR_ATTESTATION = getVendorDeviceIdProperty("brand");
    public static final java.lang.String MODEL = getString("ro.product.model");
    public static final java.lang.String MODEL_FOR_ATTESTATION = getVendorDeviceIdProperty("model");
    public static final java.lang.String SOC_MANUFACTURER = android.sysprop.SocProperties.soc_manufacturer().orElse("unknown");
    public static final java.lang.String SOC_MODEL = android.sysprop.SocProperties.soc_model().orElse("unknown");
    public static final java.lang.String BOOTLOADER = getString("ro.bootloader");

    @java.lang.Deprecated
    public static final java.lang.String RADIO = joinListOrElse(android.sysprop.TelephonyProperties.baseband_version(), "unknown");
    public static final java.lang.String HARDWARE = getString("ro.hardware");
    public static final java.lang.String SKU = getString("ro.boot.hardware.sku");
    public static final java.lang.String ODM_SKU = getString("ro.boot.product.hardware.sku");
    public static final boolean IS_EMULATOR = getString("ro.boot.qemu").equals("1");

    @java.lang.Deprecated
    public static final java.lang.String SERIAL = getString("no.such.thing");
    public static final java.lang.String[] SUPPORTED_ABIS = getStringList("ro.product.cpu.abilist", ",");
    public static final java.lang.String[] SUPPORTED_32_BIT_ABIS = getStringList("ro.product.cpu.abilist32", ",");
    public static final java.lang.String[] SUPPORTED_64_BIT_ABIS = getStringList("ro.product.cpu.abilist64", ",");

    public static class VERSION_CODES {
        public static final int BASE = 1;
        public static final int BASE_1_1 = 2;
        public static final int CUPCAKE = 3;
        public static final int CUR_DEVELOPMENT = 10000;
        public static final int DONUT = 4;
        public static final int ECLAIR = 5;
        public static final int ECLAIR_0_1 = 6;
        public static final int ECLAIR_MR1 = 7;
        public static final int FROYO = 8;
        public static final int GINGERBREAD = 9;
        public static final int GINGERBREAD_MR1 = 10;
        public static final int HONEYCOMB = 11;
        public static final int HONEYCOMB_MR1 = 12;
        public static final int HONEYCOMB_MR2 = 13;
        public static final int ICE_CREAM_SANDWICH = 14;
        public static final int ICE_CREAM_SANDWICH_MR1 = 15;
        public static final int JELLY_BEAN = 16;
        public static final int JELLY_BEAN_MR1 = 17;
        public static final int JELLY_BEAN_MR2 = 18;
        public static final int KITKAT = 19;
        public static final int KITKAT_WATCH = 20;
        public static final int L = 21;
        public static final int LOLLIPOP = 21;
        public static final int LOLLIPOP_MR1 = 22;
        public static final int M = 23;
        public static final int N = 24;
        public static final int N_MR1 = 25;
        public static final int O = 26;
        public static final int O_MR1 = 27;
        public static final int P = 28;
        public static final int Q = 29;
        public static final int R = 30;
        public static final int S = 31;
        public static final int S_V2 = 32;
        public static final int TIRAMISU = 33;
        public static final int UPSIDE_DOWN_CAKE = 34;
        public static final int VANILLA_ICE_CREAM = 10000;
    }

    static {
        java.lang.String[] strArr;
        if (android.os.Process.is64Bit()) {
            strArr = SUPPORTED_64_BIT_ABIS;
        } else {
            strArr = SUPPORTED_32_BIT_ABIS;
        }
        CPU_ABI = strArr[0];
        if (strArr.length > 1) {
            CPU_ABI2 = strArr[1];
        } else {
            CPU_ABI2 = "";
        }
        TYPE = getString("ro.build.type");
        TAGS = getString("ro.build.tags");
        FINGERPRINT = deriveFingerprint();
        HW_TIMEOUT_MULTIPLIER = android.os.SystemProperties.getInt("ro.hw_timeout_multiplier", 1);
        IS_TREBLE_ENABLED = android.os.SystemProperties.getBoolean("ro.treble.enabled", false);
        TIME = getLong("ro.build.date.utc") * 1000;
        USER = getString("ro.build.user");
        HOST = getString("ro.build.host");
        IS_DEBUGGABLE = android.os.SystemProperties.getInt("ro.debuggable", 0) == 1;
        IS_ENG = "eng".equals(TYPE);
        IS_USERDEBUG = "userdebug".equals(TYPE);
        IS_USER = "user".equals(TYPE);
        IS_ARC = android.os.SystemProperties.getBoolean("ro.boot.container", false);
    }

    public static java.lang.String getSerial() {
        android.os.IDeviceIdentifiersPolicyService asInterface = android.os.IDeviceIdentifiersPolicyService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.DEVICE_IDENTIFIERS_SERVICE));
        try {
            android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
            return asInterface.getSerialForPackage(currentApplication != null ? currentApplication.getPackageName() : null, null);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return "unknown";
        }
    }

    public static boolean is64BitAbi(java.lang.String str) {
        return dalvik.system.VMRuntime.is64BitAbi(str);
    }

    public static class VERSION {
        public static final java.lang.String[] ACTIVE_CODENAMES;
        public static final int MIN_SUPPORTED_TARGET_SDK_INT;
        public static final int RESOURCES_SDK_INT;
        public static final java.lang.String INCREMENTAL = android.os.Build.getString("ro.build.version.incremental");
        public static final java.lang.String RELEASE = android.os.Build.getString("ro.build.version.release");
        public static final java.lang.String RELEASE_OR_CODENAME = android.os.Build.getString("ro.build.version.release_or_codename");
        public static final java.lang.String RELEASE_OR_PREVIEW_DISPLAY = android.os.Build.getString("ro.build.version.release_or_preview_display");
        public static final java.lang.String BASE_OS = android.os.SystemProperties.get("ro.build.version.base_os", "");
        public static final java.lang.String SECURITY_PATCH = android.os.SystemProperties.get("ro.build.version.security_patch", "");
        public static final int MEDIA_PERFORMANCE_CLASS = android.sysprop.DeviceProperties.media_performance_class().orElse(0).intValue();

        @java.lang.Deprecated
        public static final java.lang.String SDK = android.os.Build.getString("ro.build.version.sdk");
        public static final int SDK_INT = android.os.SystemProperties.getInt("ro.build.version.sdk", 0);

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public static final int DEVICE_INITIAL_SDK_INT = android.os.SystemProperties.getInt("ro.product.first_api_level", 0);
        public static final int PREVIEW_SDK_INT = android.os.SystemProperties.getInt("ro.build.version.preview_sdk", 0);

        @android.annotation.SystemApi
        public static final java.lang.String PREVIEW_SDK_FINGERPRINT = android.os.SystemProperties.get("ro.build.version.preview_sdk_fingerprint", "REL");
        public static final java.lang.String CODENAME = android.os.Build.getString("ro.build.version.codename");

        @android.annotation.SystemApi
        public static final java.util.Set<java.lang.String> KNOWN_CODENAMES = new android.util.ArraySet(android.os.Build.getStringList("ro.build.version.known_codenames", ","));
        private static final java.lang.String[] ALL_CODENAMES = android.os.Build.getStringList("ro.build.version.all_codenames", ",");

        static {
            ACTIVE_CODENAMES = "REL".equals(ALL_CODENAMES[0]) ? new java.lang.String[0] : ALL_CODENAMES;
            RESOURCES_SDK_INT = SDK_INT + ACTIVE_CODENAMES.length;
            MIN_SUPPORTED_TARGET_SDK_INT = android.os.SystemProperties.getInt("ro.build.version.min_supported_target_sdk", 0);
        }
    }

    private static java.lang.String deriveFingerprint() {
        java.lang.String str = android.os.SystemProperties.get("ro.build.fingerprint");
        if (android.text.TextUtils.isEmpty(str)) {
            return getString("ro.product.brand") + '/' + getString("ro.product.name") + '/' + getString("ro.product.device") + com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR + getString("ro.build.version.release") + '/' + getString("ro.build.id") + '/' + getString("ro.build.version.incremental") + com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR + getString("ro.build.type") + '/' + getString("ro.build.tags");
        }
        return str;
    }

    public static void ensureFingerprintProperty() {
        if (android.text.TextUtils.isEmpty(android.os.SystemProperties.get("ro.build.fingerprint"))) {
            try {
                android.os.SystemProperties.set("ro.build.fingerprint", FINGERPRINT);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(TAG, "Failed to set fingerprint property", e);
            }
        }
    }

    public static boolean isBuildConsistent() {
        if (IS_ENG) {
            return true;
        }
        if (IS_TREBLE_ENABLED) {
            int verifyBuildAtBoot = android.os.VintfObject.verifyBuildAtBoot();
            if (verifyBuildAtBoot != 0) {
                android.util.Slog.e(TAG, "Vendor interface is incompatible, error=" + java.lang.String.valueOf(verifyBuildAtBoot));
            }
            return verifyBuildAtBoot == 0;
        }
        java.lang.String str = android.os.SystemProperties.get("ro.system.build.fingerprint");
        java.lang.String str2 = android.os.SystemProperties.get("ro.vendor.build.fingerprint");
        android.os.SystemProperties.get("ro.bootimage.build.fingerprint");
        android.os.SystemProperties.get("ro.build.expect.bootloader");
        android.os.SystemProperties.get("ro.bootloader");
        android.os.SystemProperties.get("ro.build.expect.baseband");
        joinListOrElse(android.sysprop.TelephonyProperties.baseband_version(), "");
        if (android.text.TextUtils.isEmpty(str)) {
            android.util.Slog.e(TAG, "Required ro.system.build.fingerprint is empty!");
            return false;
        }
        if (android.text.TextUtils.isEmpty(str2) || java.util.Objects.equals(str, str2)) {
            return true;
        }
        android.util.Slog.e(TAG, "Mismatched fingerprints; system reported " + str + " but vendor reported " + str2);
        return false;
    }

    public static class Partition {
        public static final java.lang.String PARTITION_NAME_BOOTIMAGE = "bootimage";
        public static final java.lang.String PARTITION_NAME_ODM = "odm";
        public static final java.lang.String PARTITION_NAME_OEM = "oem";
        public static final java.lang.String PARTITION_NAME_PRODUCT = "product";
        public static final java.lang.String PARTITION_NAME_SYSTEM = "system";
        public static final java.lang.String PARTITION_NAME_SYSTEM_EXT = "system_ext";
        public static final java.lang.String PARTITION_NAME_VENDOR = "vendor";
        private final java.lang.String mFingerprint;
        private final java.lang.String mName;
        private final long mTimeMs;

        private Partition(java.lang.String str, java.lang.String str2, long j) {
            this.mName = str;
            this.mFingerprint = str2;
            this.mTimeMs = j;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.lang.String getFingerprint() {
            return this.mFingerprint;
        }

        public long getBuildTimeMillis() {
            return this.mTimeMs;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.os.Build.Partition)) {
                return false;
            }
            android.os.Build.Partition partition = (android.os.Build.Partition) obj;
            return this.mName.equals(partition.mName) && this.mFingerprint.equals(partition.mFingerprint) && this.mTimeMs == partition.mTimeMs;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mName, this.mFingerprint, java.lang.Long.valueOf(this.mTimeMs));
        }
    }

    public static java.util.List<android.os.Build.Partition> getFingerprintedPartitions() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String[] strArr = {android.os.Build.Partition.PARTITION_NAME_BOOTIMAGE, android.os.Build.Partition.PARTITION_NAME_ODM, "product", android.os.Build.Partition.PARTITION_NAME_SYSTEM_EXT, "system", "vendor"};
        for (int i = 0; i < 6; i++) {
            java.lang.String str = strArr[i];
            java.lang.String str2 = android.os.SystemProperties.get("ro." + str + ".build.fingerprint");
            if (!android.text.TextUtils.isEmpty(str2)) {
                arrayList.add(new android.os.Build.Partition(str, str2, 1000 * getLong("ro." + str + ".build.date.utc")));
            }
        }
        return arrayList;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static boolean isDebuggable() {
        return IS_DEBUGGABLE;
    }

    public static java.lang.String getRadioVersion() {
        return joinListOrElse(android.sysprop.TelephonyProperties.baseband_version(), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getString(java.lang.String str) {
        return android.os.SystemProperties.get(str, "unknown");
    }

    private static java.lang.String getVendorDeviceIdProperty(java.lang.String str) {
        java.lang.String string = getString(android.text.TextUtils.formatSimple("ro.product.%s_for_attestation", str));
        if (!string.equals("unknown")) {
            return string;
        }
        return getString(android.text.TextUtils.formatSimple("ro.product.vendor.%s", str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String[] getStringList(java.lang.String str, java.lang.String str2) {
        java.lang.String str3 = android.os.SystemProperties.get(str);
        if (str3.isEmpty()) {
            return new java.lang.String[0];
        }
        return str3.split(str2);
    }

    private static long getLong(java.lang.String str) {
        try {
            return java.lang.Long.parseLong(android.os.SystemProperties.get(str));
        } catch (java.lang.NumberFormatException e) {
            return -1L;
        }
    }

    private static <T> java.lang.String joinListOrElse(java.util.List<T> list, java.lang.String str) {
        java.lang.String str2 = (java.lang.String) list.stream().map(new java.util.function.Function() { // from class: android.os.Build$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return android.os.Build.lambda$joinListOrElse$0(obj);
            }
        }).collect(java.util.stream.Collectors.joining(","));
        return str2.isEmpty() ? str : str2;
    }

    static /* synthetic */ java.lang.String lambda$joinListOrElse$0(java.lang.Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
