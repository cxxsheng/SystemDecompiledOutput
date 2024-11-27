package android.content.pm;

/* loaded from: classes.dex */
public class ActivityInfo extends android.content.pm.ComponentInfo implements android.os.Parcelable {
    public static final long ALWAYS_SANDBOX_DISPLAY_APIS = 185004937;
    private static final long CHECK_MIN_WIDTH_HEIGHT_FOR_MULTI_WINDOW = 197654537;
    public static final int COLOR_MODE_A8 = 4;
    public static final int COLOR_MODE_DEFAULT = 0;
    public static final int COLOR_MODE_HDR = 2;
    public static final int COLOR_MODE_HDR10 = 3;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT = 1;
    public static final int CONFIG_ASSETS_PATHS = Integer.MIN_VALUE;
    public static final int CONFIG_COLOR_MODE = 16384;
    public static final int CONFIG_DENSITY = 4096;
    public static final int CONFIG_FONT_SCALE = 1073741824;
    public static final int CONFIG_FONT_WEIGHT_ADJUSTMENT = 268435456;
    public static final int CONFIG_GRAMMATICAL_GENDER = 32768;
    public static final int CONFIG_KEYBOARD = 16;
    public static final int CONFIG_KEYBOARD_HIDDEN = 32;
    public static final int CONFIG_LAYOUT_DIRECTION = 8192;
    public static final int CONFIG_LOCALE = 4;
    public static final int CONFIG_MCC = 1;
    public static final int CONFIG_MNC = 2;
    public static final int CONFIG_NAVIGATION = 64;
    public static final int CONFIG_ORIENTATION = 128;
    public static final int CONFIG_SCREEN_LAYOUT = 256;
    public static final int CONFIG_SCREEN_SIZE = 1024;
    public static final int CONFIG_SMALLEST_SCREEN_SIZE = 2048;
    public static final int CONFIG_TOUCHSCREEN = 8;
    public static final int CONFIG_UI_MODE = 512;
    public static final int CONFIG_WINDOW_CONFIGURATION = 536870912;
    public static final int CONTENT_URI_PERMISSION_NONE = 0;
    public static final int CONTENT_URI_PERMISSION_READ = 1;
    public static final int CONTENT_URI_PERMISSION_READ_AND_WRITE = 4;
    public static final int CONTENT_URI_PERMISSION_READ_OR_WRITE = 3;
    public static final int CONTENT_URI_PERMISSION_WRITE = 2;
    public static final int DOCUMENT_LAUNCH_ALWAYS = 2;
    public static final int DOCUMENT_LAUNCH_INTO_EXISTING = 1;
    public static final int DOCUMENT_LAUNCH_NEVER = 3;
    public static final int DOCUMENT_LAUNCH_NONE = 0;

    @java.lang.Deprecated
    public static final int FLAG_ALLOW_EMBEDDED = Integer.MIN_VALUE;
    public static final int FLAG_ALLOW_TASK_REPARENTING = 64;
    public static final int FLAG_ALLOW_UNTRUSTED_ACTIVITY_EMBEDDING = 268435456;
    public static final int FLAG_ALWAYS_FOCUSABLE = 262144;
    public static final int FLAG_ALWAYS_RETAIN_TASK_STATE = 8;
    public static final int FLAG_AUTO_REMOVE_FROM_RECENTS = 8192;
    public static final int FLAG_CAN_DISPLAY_ON_REMOTE_DEVICES = 65536;
    public static final int FLAG_CLEAR_TASK_ON_LAUNCH = 4;
    public static final int FLAG_ENABLE_VR_MODE = 32768;
    public static final int FLAG_EXCLUDE_FROM_RECENTS = 32;
    public static final int FLAG_FINISH_ON_CLOSE_SYSTEM_DIALOGS = 256;
    public static final int FLAG_FINISH_ON_TASK_LAUNCH = 2;
    public static final int FLAG_HARDWARE_ACCELERATED = 512;
    public static final int FLAG_IMMERSIVE = 2048;
    public static final int FLAG_IMPLICITLY_VISIBLE_TO_INSTANT_APP = 2097152;
    public static final int FLAG_INHERIT_SHOW_WHEN_LOCKED = 1;
    public static final int FLAG_MULTIPROCESS = 1;
    public static final int FLAG_NO_HISTORY = 128;
    public static final int FLAG_PREFER_MINIMAL_POST_PROCESSING = 33554432;
    public static final int FLAG_RELINQUISH_TASK_IDENTITY = 4096;
    public static final int FLAG_RESUME_WHILE_PAUSING = 16384;
    public static final int FLAG_SHOW_FOR_ALL_USERS = 1024;
    public static final int FLAG_SHOW_WHEN_LOCKED = 8388608;
    public static final int FLAG_SINGLE_USER = 1073741824;
    public static final int FLAG_STATE_NOT_NEEDED = 16;
    public static final int FLAG_SUPPORTS_PICTURE_IN_PICTURE = 4194304;
    public static final int FLAG_SYSTEM_USER_ONLY = 536870912;
    public static final int FLAG_TURN_SCREEN_ON = 16777216;
    public static final int FLAG_VISIBLE_TO_INSTANT_APP = 1048576;
    public static final long FORCE_NON_RESIZE_APP = 181136395;
    public static final long FORCE_RESIZE_APP = 174042936;
    public static final int LAUNCH_MULTIPLE = 0;
    public static final int LAUNCH_SINGLE_INSTANCE = 3;
    public static final int LAUNCH_SINGLE_INSTANCE_PER_TASK = 4;
    public static final int LAUNCH_SINGLE_TASK = 2;
    public static final int LAUNCH_SINGLE_TOP = 1;
    public static final int LOCK_TASK_LAUNCH_MODE_ALWAYS = 2;
    public static final int LOCK_TASK_LAUNCH_MODE_DEFAULT = 0;
    public static final int LOCK_TASK_LAUNCH_MODE_IF_ALLOWLISTED = 3;
    public static final int LOCK_TASK_LAUNCH_MODE_NEVER = 1;
    public static final long NEVER_SANDBOX_DISPLAY_APIS = 184838306;
    public static final long OVERRIDE_ANY_ORIENTATION = 265464455;
    public static final long OVERRIDE_ANY_ORIENTATION_TO_USER = 310816437;
    public static final long OVERRIDE_CAMERA_COMPAT_DISABLE_FORCE_ROTATION = 263959004;
    public static final long OVERRIDE_CAMERA_COMPAT_DISABLE_REFRESH = 264304459;
    public static final long OVERRIDE_CAMERA_COMPAT_ENABLE_REFRESH_VIA_PAUSE = 264301586;
    public static final long OVERRIDE_ENABLE_COMPAT_FAKE_FOCUS = 263259275;
    public static final long OVERRIDE_ENABLE_COMPAT_IGNORE_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED = 273509367;
    public static final long OVERRIDE_ENABLE_COMPAT_IGNORE_REQUESTED_ORIENTATION = 254631730;
    public static final long OVERRIDE_LANDSCAPE_ORIENTATION_TO_REVERSE_LANDSCAPE = 266124927;
    public static final long OVERRIDE_MIN_ASPECT_RATIO = 174042980;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_EXCLUDE_PORTRAIT_FULLSCREEN = 218959984;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_LARGE = 180326787;
    public static final float OVERRIDE_MIN_ASPECT_RATIO_LARGE_VALUE = 1.7777778f;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_MEDIUM = 180326845;
    public static final float OVERRIDE_MIN_ASPECT_RATIO_MEDIUM_VALUE = 1.5f;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_PORTRAIT_ONLY = 203647190;
    public static final long OVERRIDE_MIN_ASPECT_RATIO_TO_ALIGN_WITH_SPLIT_SCREEN = 208648326;
    public static final long OVERRIDE_ORIENTATION_ONLY_FOR_CAMERA = 265456536;
    public static final long OVERRIDE_RESPECT_REQUESTED_ORIENTATION = 236283604;
    public static final long OVERRIDE_SANDBOX_VIEW_BOUNDS_APIS = 237531167;
    public static final long OVERRIDE_UNDEFINED_ORIENTATION_TO_NOSENSOR = 265451093;
    public static final long OVERRIDE_UNDEFINED_ORIENTATION_TO_PORTRAIT = 265452344;
    public static final long OVERRIDE_USE_DISPLAY_LANDSCAPE_NATURAL_ORIENTATION = 255940284;
    public static final int PERSIST_ACROSS_REBOOTS = 2;
    public static final int PERSIST_NEVER = 1;
    public static final int PERSIST_ROOT_ONLY = 0;
    public static final int PRIVATE_FLAG_DISABLE_ON_BACK_INVOKED_CALLBACK = 8;
    public static final int PRIVATE_FLAG_ENABLE_ON_BACK_INVOKED_CALLBACK = 4;
    public static final int PRIVATE_FLAG_HOME_TRANSITION_SOUND = 2;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_LANDSCAPE_ONLY = 5;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_PORTRAIT_ONLY = 6;
    public static final int RESIZE_MODE_FORCE_RESIZABLE_PRESERVE_ORIENTATION = 7;
    public static final int RESIZE_MODE_FORCE_RESIZEABLE = 4;
    public static final int RESIZE_MODE_RESIZEABLE = 2;
    public static final int RESIZE_MODE_RESIZEABLE_AND_PIPABLE_DEPRECATED = 3;
    public static final int RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION = 1;
    public static final int RESIZE_MODE_UNRESIZEABLE = 0;
    public static final int SCREEN_ORIENTATION_BEHIND = 3;
    public static final int SCREEN_ORIENTATION_FULL_SENSOR = 10;
    public static final int SCREEN_ORIENTATION_FULL_USER = 13;
    public static final int SCREEN_ORIENTATION_LANDSCAPE = 0;
    public static final int SCREEN_ORIENTATION_LOCKED = 14;
    public static final int SCREEN_ORIENTATION_NOSENSOR = 5;
    public static final int SCREEN_ORIENTATION_PORTRAIT = 1;
    public static final int SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8;
    public static final int SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9;
    public static final int SCREEN_ORIENTATION_SENSOR = 4;
    public static final int SCREEN_ORIENTATION_SENSOR_LANDSCAPE = 6;
    public static final int SCREEN_ORIENTATION_SENSOR_PORTRAIT = 7;
    public static final int SCREEN_ORIENTATION_UNSET = -2;
    public static final int SCREEN_ORIENTATION_UNSPECIFIED = -1;
    public static final int SCREEN_ORIENTATION_USER = 2;
    public static final int SCREEN_ORIENTATION_USER_LANDSCAPE = 11;
    public static final int SCREEN_ORIENTATION_USER_PORTRAIT = 12;
    public static final int SIZE_CHANGES_SUPPORTED_METADATA = 2;
    public static final int SIZE_CHANGES_SUPPORTED_OVERRIDE = 3;
    public static final int SIZE_CHANGES_UNSUPPORTED_METADATA = 0;
    public static final int SIZE_CHANGES_UNSUPPORTED_OVERRIDE = 1;
    public static final int UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW = 1;
    public int colorMode;
    public int configChanges;
    public int documentLaunchMode;
    public int flags;
    public int launchMode;
    public java.lang.String launchToken;
    public int lockTaskLaunchMode;
    private java.util.Set<java.lang.String> mKnownActivityEmbeddingCerts;
    private float mMaxAspectRatio;
    private float mMinAspectRatio;
    public int maxRecents;
    public java.lang.String parentActivityName;
    public java.lang.String permission;
    public int persistableMode;
    public int privateFlags;
    public java.lang.String requestedVrComponent;
    public int requireContentUriPermissionFromCaller;
    public java.lang.String requiredDisplayCategory;
    public int resizeMode;
    public int rotationAnimation;
    public int screenOrientation;
    public int softInputMode;
    public boolean supportsSizeChanges;
    public java.lang.String targetActivity;
    public java.lang.String taskAffinity;
    public int theme;
    public int uiOptions;
    public android.content.pm.ActivityInfo.WindowLayout windowLayout;
    private static final com.android.internal.util.Parcelling.BuiltIn.ForStringSet sForStringSet = (com.android.internal.util.Parcelling.BuiltIn.ForStringSet) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForStringSet.class);
    public static int[] CONFIG_NATIVE_BITS = {2, 1, 4, 8, 16, 32, 64, 128, 2048, 4096, 512, 8192, 256, 16384, 65536, 131072};
    public static final android.os.Parcelable.Creator<android.content.pm.ActivityInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ActivityInfo>() { // from class: android.content.pm.ActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ActivityInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ActivityInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ActivityInfo[] newArray(int i) {
            return new android.content.pm.ActivityInfo[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ColorMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Config {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LaunchMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequiredContentUriPermission {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScreenOrientation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SizeChangesSupportMode {
    }

    public static java.lang.String launchModeToString(int i) {
        switch (i) {
            case 0:
                return "LAUNCH_MULTIPLE";
            case 1:
                return "LAUNCH_SINGLE_TOP";
            case 2:
                return "LAUNCH_SINGLE_TASK";
            case 3:
                return "LAUNCH_SINGLE_INSTANCE";
            case 4:
                return "LAUNCH_SINGLE_INSTANCE_PER_TASK";
            default:
                return "unknown=" + i;
        }
    }

    public static boolean isRequiredContentUriPermissionRead(int i) {
        switch (i) {
            case 1:
            case 3:
            case 4:
                return true;
            case 2:
            default:
                return false;
        }
    }

    public static boolean isRequiredContentUriPermissionWrite(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    private java.lang.String requiredContentUriPermissionToFullString(int i) {
        switch (i) {
            case 0:
                return "CONTENT_URI_PERMISSION_NONE";
            case 1:
                return "CONTENT_URI_PERMISSION_READ";
            case 2:
                return "CONTENT_URI_PERMISSION_WRITE";
            case 3:
                return "CONTENT_URI_PERMISSION_READ_OR_WRITE";
            case 4:
                return "CONTENT_URI_PERMISSION_READ_AND_WRITE";
            default:
                return "unknown=" + i;
        }
    }

    public static java.lang.String requiredContentUriPermissionToShortString(int i) {
        switch (i) {
            case 0:
                return "none";
            case 1:
                return "read";
            case 2:
                return "write";
            case 3:
                return "read or write";
            case 4:
                return "read and write";
            default:
                return "unknown=" + i;
        }
    }

    public static int activityInfoConfigJavaToNative(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < CONFIG_NATIVE_BITS.length; i3++) {
            if (((1 << i3) & i) != 0) {
                i2 |= CONFIG_NATIVE_BITS[i3];
            }
        }
        return i2;
    }

    public static int activityInfoConfigNativeToJava(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < CONFIG_NATIVE_BITS.length; i3++) {
            if ((CONFIG_NATIVE_BITS[i3] & i) != 0) {
                i2 |= 1 << i3;
            }
        }
        return i2;
    }

    public int getRealConfigChanged() {
        if (this.applicationInfo.targetSdkVersion < 13) {
            return this.configChanges | 1024 | 2048;
        }
        return this.configChanges;
    }

    public static final java.lang.String lockTaskLaunchModeToString(int i) {
        switch (i) {
            case 0:
                return "LOCK_TASK_LAUNCH_MODE_DEFAULT";
            case 1:
                return "LOCK_TASK_LAUNCH_MODE_NEVER";
            case 2:
                return "LOCK_TASK_LAUNCH_MODE_ALWAYS";
            case 3:
                return "LOCK_TASK_LAUNCH_MODE_IF_ALLOWLISTED";
            default:
                return "unknown=" + i;
        }
    }

    public ActivityInfo() {
        this.resizeMode = 2;
        this.colorMode = 0;
        this.screenOrientation = -1;
        this.uiOptions = 0;
        this.rotationAnimation = -1;
    }

    public ActivityInfo(android.content.pm.ActivityInfo activityInfo) {
        super(activityInfo);
        this.resizeMode = 2;
        this.colorMode = 0;
        this.screenOrientation = -1;
        this.uiOptions = 0;
        this.rotationAnimation = -1;
        this.theme = activityInfo.theme;
        this.launchMode = activityInfo.launchMode;
        this.documentLaunchMode = activityInfo.documentLaunchMode;
        this.permission = activityInfo.permission;
        this.mKnownActivityEmbeddingCerts = activityInfo.mKnownActivityEmbeddingCerts;
        this.taskAffinity = activityInfo.taskAffinity;
        this.targetActivity = activityInfo.targetActivity;
        this.flags = activityInfo.flags;
        this.privateFlags = activityInfo.privateFlags;
        this.screenOrientation = activityInfo.screenOrientation;
        this.configChanges = activityInfo.configChanges;
        this.softInputMode = activityInfo.softInputMode;
        this.uiOptions = activityInfo.uiOptions;
        this.parentActivityName = activityInfo.parentActivityName;
        this.maxRecents = activityInfo.maxRecents;
        this.lockTaskLaunchMode = activityInfo.lockTaskLaunchMode;
        this.windowLayout = activityInfo.windowLayout;
        this.resizeMode = activityInfo.resizeMode;
        this.requestedVrComponent = activityInfo.requestedVrComponent;
        this.rotationAnimation = activityInfo.rotationAnimation;
        this.colorMode = activityInfo.colorMode;
        this.mMaxAspectRatio = activityInfo.mMaxAspectRatio;
        this.mMinAspectRatio = activityInfo.mMinAspectRatio;
        this.supportsSizeChanges = activityInfo.supportsSizeChanges;
        this.requiredDisplayCategory = activityInfo.requiredDisplayCategory;
        this.requireContentUriPermissionFromCaller = activityInfo.requireContentUriPermissionFromCaller;
    }

    public final int getThemeResource() {
        return this.theme != 0 ? this.theme : this.applicationInfo.theme;
    }

    private java.lang.String persistableModeToString() {
        switch (this.persistableMode) {
            case 0:
                return "PERSIST_ROOT_ONLY";
            case 1:
                return "PERSIST_NEVER";
            case 2:
                return "PERSIST_ACROSS_REBOOTS";
            default:
                return "UNKNOWN=" + this.persistableMode;
        }
    }

    public boolean hasFixedAspectRatio() {
        return (getMaxAspectRatio() == 0.0f && getMinAspectRatio() == 0.0f) ? false : true;
    }

    public boolean isFixedOrientation() {
        return isFixedOrientation(this.screenOrientation);
    }

    public static boolean isFixedOrientation(int i) {
        return i == 14 || i == 5 || isFixedOrientationLandscape(i) || isFixedOrientationPortrait(i);
    }

    boolean isFixedOrientationLandscape() {
        return isFixedOrientationLandscape(this.screenOrientation);
    }

    public static boolean isFixedOrientationLandscape(int i) {
        return i == 0 || i == 6 || i == 8 || i == 11;
    }

    boolean isFixedOrientationPortrait() {
        return isFixedOrientationPortrait(this.screenOrientation);
    }

    public static boolean isFixedOrientationPortrait(int i) {
        return i == 1 || i == 7 || i == 9 || i == 12;
    }

    public static int reverseOrientation(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
            default:
                return i;
            case 6:
                return 7;
            case 7:
                return 6;
            case 8:
                return 9;
            case 9:
                return 8;
            case 11:
                return 12;
            case 12:
                return 11;
        }
    }

    public boolean supportsPictureInPicture() {
        return (this.flags & 4194304) != 0;
    }

    public boolean neverSandboxDisplayApis(android.content.pm.ConstrainDisplayApisConfig constrainDisplayApisConfig) {
        return isChangeEnabled(NEVER_SANDBOX_DISPLAY_APIS) || constrainDisplayApisConfig.getNeverConstrainDisplayApis(this.applicationInfo);
    }

    public boolean alwaysSandboxDisplayApis(android.content.pm.ConstrainDisplayApisConfig constrainDisplayApisConfig) {
        return isChangeEnabled(ALWAYS_SANDBOX_DISPLAY_APIS) || constrainDisplayApisConfig.getAlwaysConstrainDisplayApis(this.applicationInfo);
    }

    public void setMaxAspectRatio(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.mMaxAspectRatio = f;
    }

    public float getMaxAspectRatio() {
        return this.mMaxAspectRatio;
    }

    public void setMinAspectRatio(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.mMinAspectRatio = f;
    }

    public float getMinAspectRatio() {
        return this.mMinAspectRatio;
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

    public boolean isChangeEnabled(long j) {
        return this.applicationInfo.isChangeEnabled(j);
    }

    public float getManifestMinAspectRatio() {
        return this.mMinAspectRatio;
    }

    public static boolean isResizeableMode(int i) {
        return i == 2 || i == 4 || i == 6 || i == 5 || i == 7 || i == 1;
    }

    public static boolean isPreserveOrientationMode(int i) {
        return i == 6 || i == 5 || i == 7;
    }

    public static java.lang.String resizeModeToString(int i) {
        switch (i) {
            case 0:
                return "RESIZE_MODE_UNRESIZEABLE";
            case 1:
                return "RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION";
            case 2:
                return "RESIZE_MODE_RESIZEABLE";
            case 3:
            default:
                return "unknown=" + i;
            case 4:
                return "RESIZE_MODE_FORCE_RESIZEABLE";
            case 5:
                return "RESIZE_MODE_FORCE_RESIZABLE_LANDSCAPE_ONLY";
            case 6:
                return "RESIZE_MODE_FORCE_RESIZABLE_PORTRAIT_ONLY";
            case 7:
                return "RESIZE_MODE_FORCE_RESIZABLE_PRESERVE_ORIENTATION";
        }
    }

    public static java.lang.String sizeChangesSupportModeToString(int i) {
        switch (i) {
            case 0:
                return "SIZE_CHANGES_UNSUPPORTED_METADATA";
            case 1:
                return "SIZE_CHANGES_UNSUPPORTED_OVERRIDE";
            case 2:
                return "SIZE_CHANGES_SUPPORTED_METADATA";
            case 3:
                return "SIZE_CHANGES_SUPPORTED_OVERRIDE";
            default:
                return "unknown=" + i;
        }
    }

    public boolean shouldCheckMinWidthHeightForMultiWindow() {
        return isChangeEnabled(CHECK_MIN_WIDTH_HEIGHT_FOR_MULTI_WINDOW);
    }

    public boolean hasOnBackInvokedCallbackEnabled() {
        return (this.privateFlags & 12) != 0;
    }

    public boolean isOnBackInvokedCallbackEnabled() {
        return hasOnBackInvokedCallbackEnabled() && (this.privateFlags & 4) != 0;
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        dump(printer, str, 3);
    }

    public void dump(android.util.Printer printer, java.lang.String str, int i) {
        super.dumpFront(printer, str);
        if (this.permission != null) {
            printer.println(str + "permission=" + this.permission);
        }
        int i2 = i & 1;
        if (i2 != 0) {
            printer.println(str + "taskAffinity=" + this.taskAffinity + " targetActivity=" + this.targetActivity + " persistableMode=" + persistableModeToString());
        }
        if (this.launchMode != 0 || this.flags != 0 || this.privateFlags != 0 || this.theme != 0) {
            printer.println(str + "launchMode=" + launchModeToString(this.launchMode) + " flags=0x" + java.lang.Integer.toHexString(this.flags) + " privateFlags=0x" + java.lang.Integer.toHexString(this.privateFlags) + " theme=0x" + java.lang.Integer.toHexString(this.theme));
        }
        if (this.screenOrientation != -1 || this.configChanges != 0 || this.softInputMode != 0) {
            printer.println(str + "screenOrientation=" + this.screenOrientation + " configChanges=0x" + java.lang.Integer.toHexString(this.configChanges) + " softInputMode=0x" + java.lang.Integer.toHexString(this.softInputMode));
        }
        if (this.uiOptions != 0) {
            printer.println(str + " uiOptions=0x" + java.lang.Integer.toHexString(this.uiOptions));
        }
        if (i2 != 0) {
            printer.println(str + "lockTaskLaunchMode=" + lockTaskLaunchModeToString(this.lockTaskLaunchMode));
        }
        if (this.windowLayout != null) {
            printer.println(str + "windowLayout=" + this.windowLayout.width + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.windowLayout.widthFraction + ", " + this.windowLayout.height + android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.windowLayout.heightFraction + ", " + this.windowLayout.gravity);
        }
        printer.println(str + "resizeMode=" + resizeModeToString(this.resizeMode));
        if (this.requestedVrComponent != null) {
            printer.println(str + "requestedVrComponent=" + this.requestedVrComponent);
        }
        if (getMaxAspectRatio() != 0.0f) {
            printer.println(str + "maxAspectRatio=" + getMaxAspectRatio());
        }
        float minAspectRatio = getMinAspectRatio();
        if (minAspectRatio != 0.0f) {
            printer.println(str + "minAspectRatio=" + minAspectRatio);
        }
        if (this.supportsSizeChanges) {
            printer.println(str + "supportsSizeChanges=true");
        }
        if (this.mKnownActivityEmbeddingCerts != null) {
            printer.println(str + "knownActivityEmbeddingCerts=" + this.mKnownActivityEmbeddingCerts);
        }
        if (this.requiredDisplayCategory != null) {
            printer.println(str + "requiredDisplayCategory=" + this.requiredDisplayCategory);
        }
        if (i2 != 0) {
            printer.println(str + "requireContentUriPermissionFromCaller=" + requiredContentUriPermissionToFullString(this.requireContentUriPermissionFromCaller));
        }
        super.dumpBack(printer, str, i);
    }

    public java.lang.String toString() {
        return "ActivityInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.name + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.ComponentInfo, android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.launchMode);
        parcel.writeInt(this.documentLaunchMode);
        parcel.writeString8(this.permission);
        parcel.writeString8(this.taskAffinity);
        parcel.writeString8(this.targetActivity);
        parcel.writeString8(this.launchToken);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.privateFlags);
        parcel.writeInt(this.screenOrientation);
        parcel.writeInt(this.configChanges);
        parcel.writeInt(this.softInputMode);
        parcel.writeInt(this.uiOptions);
        parcel.writeString8(this.parentActivityName);
        parcel.writeInt(this.persistableMode);
        parcel.writeInt(this.maxRecents);
        parcel.writeInt(this.lockTaskLaunchMode);
        if (this.windowLayout != null) {
            parcel.writeInt(1);
            this.windowLayout.writeToParcel(parcel);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.resizeMode);
        parcel.writeString8(this.requestedVrComponent);
        parcel.writeInt(this.rotationAnimation);
        parcel.writeInt(this.colorMode);
        parcel.writeFloat(this.mMaxAspectRatio);
        parcel.writeFloat(this.mMinAspectRatio);
        parcel.writeBoolean(this.supportsSizeChanges);
        sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, parcel, this.flags);
        parcel.writeString8(this.requiredDisplayCategory);
        parcel.writeInt(this.requireContentUriPermissionFromCaller);
    }

    public static boolean isTranslucentOrFloating(android.content.res.TypedArray typedArray) {
        return typedArray.getBoolean(4, false) || typedArray.getBoolean(5, false);
    }

    public static java.lang.String screenOrientationToString(int i) {
        switch (i) {
            case -2:
                return "SCREEN_ORIENTATION_UNSET";
            case -1:
                return "SCREEN_ORIENTATION_UNSPECIFIED";
            case 0:
                return "SCREEN_ORIENTATION_LANDSCAPE";
            case 1:
                return "SCREEN_ORIENTATION_PORTRAIT";
            case 2:
                return "SCREEN_ORIENTATION_USER";
            case 3:
                return "SCREEN_ORIENTATION_BEHIND";
            case 4:
                return "SCREEN_ORIENTATION_SENSOR";
            case 5:
                return "SCREEN_ORIENTATION_NOSENSOR";
            case 6:
                return "SCREEN_ORIENTATION_SENSOR_LANDSCAPE";
            case 7:
                return "SCREEN_ORIENTATION_SENSOR_PORTRAIT";
            case 8:
                return "SCREEN_ORIENTATION_REVERSE_LANDSCAPE";
            case 9:
                return "SCREEN_ORIENTATION_REVERSE_PORTRAIT";
            case 10:
                return "SCREEN_ORIENTATION_FULL_SENSOR";
            case 11:
                return "SCREEN_ORIENTATION_USER_LANDSCAPE";
            case 12:
                return "SCREEN_ORIENTATION_USER_PORTRAIT";
            case 13:
                return "SCREEN_ORIENTATION_FULL_USER";
            case 14:
                return "SCREEN_ORIENTATION_LOCKED";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String colorModeToString(int i) {
        switch (i) {
            case 0:
                return "COLOR_MODE_DEFAULT";
            case 1:
                return "COLOR_MODE_WIDE_COLOR_GAMUT";
            case 2:
                return "COLOR_MODE_HDR";
            case 3:
            default:
                return java.lang.Integer.toString(i);
            case 4:
                return "COLOR_MODE_A8";
        }
    }

    private ActivityInfo(android.os.Parcel parcel) {
        super(parcel);
        this.resizeMode = 2;
        this.colorMode = 0;
        this.screenOrientation = -1;
        this.uiOptions = 0;
        this.rotationAnimation = -1;
        this.theme = parcel.readInt();
        this.launchMode = parcel.readInt();
        this.documentLaunchMode = parcel.readInt();
        this.permission = parcel.readString8();
        this.taskAffinity = parcel.readString8();
        this.targetActivity = parcel.readString8();
        this.launchToken = parcel.readString8();
        this.flags = parcel.readInt();
        this.privateFlags = parcel.readInt();
        this.screenOrientation = parcel.readInt();
        this.configChanges = parcel.readInt();
        this.softInputMode = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.parentActivityName = parcel.readString8();
        this.persistableMode = parcel.readInt();
        this.maxRecents = parcel.readInt();
        this.lockTaskLaunchMode = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.windowLayout = new android.content.pm.ActivityInfo.WindowLayout(parcel);
        }
        this.resizeMode = parcel.readInt();
        this.requestedVrComponent = parcel.readString8();
        this.rotationAnimation = parcel.readInt();
        this.colorMode = parcel.readInt();
        this.mMaxAspectRatio = parcel.readFloat();
        this.mMinAspectRatio = parcel.readFloat();
        this.supportsSizeChanges = parcel.readBoolean();
        this.mKnownActivityEmbeddingCerts = sForStringSet.unparcel(parcel);
        if (this.mKnownActivityEmbeddingCerts.isEmpty()) {
            this.mKnownActivityEmbeddingCerts = null;
        }
        this.requiredDisplayCategory = parcel.readString8();
        this.requireContentUriPermissionFromCaller = parcel.readInt();
    }

    public static final class WindowLayout {
        public final int gravity;
        public final int height;
        public final float heightFraction;
        public final int minHeight;
        public final int minWidth;
        public final int width;
        public final float widthFraction;
        public java.lang.String windowLayoutAffinity;

        public WindowLayout(int i, float f, int i2, float f2, int i3, int i4, int i5) {
            this(i, f, i2, f2, i3, i4, i5, null);
        }

        public WindowLayout(int i, float f, int i2, float f2, int i3, int i4, int i5, java.lang.String str) {
            this.width = i;
            this.widthFraction = f;
            this.height = i2;
            this.heightFraction = f2;
            this.gravity = i3;
            this.minWidth = i4;
            this.minHeight = i5;
            this.windowLayoutAffinity = str;
        }

        public WindowLayout(android.os.Parcel parcel) {
            this.width = parcel.readInt();
            this.widthFraction = parcel.readFloat();
            this.height = parcel.readInt();
            this.heightFraction = parcel.readFloat();
            this.gravity = parcel.readInt();
            this.minWidth = parcel.readInt();
            this.minHeight = parcel.readInt();
            this.windowLayoutAffinity = parcel.readString8();
        }

        public boolean hasSpecifiedSize() {
            return this.width >= 0 || this.height >= 0 || this.widthFraction >= 0.0f || this.heightFraction >= 0.0f;
        }

        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeInt(this.width);
            parcel.writeFloat(this.widthFraction);
            parcel.writeInt(this.height);
            parcel.writeFloat(this.heightFraction);
            parcel.writeInt(this.gravity);
            parcel.writeInt(this.minWidth);
            parcel.writeInt(this.minHeight);
            parcel.writeString8(this.windowLayoutAffinity);
        }
    }
}
