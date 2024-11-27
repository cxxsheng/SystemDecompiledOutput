package android.app;

/* loaded from: classes.dex */
public class ActivityManager {
    public static final java.lang.String ACTION_REPORT_HEAP_LIMIT = "android.app.action.REPORT_HEAP_LIMIT";
    public static final int APP_START_MODE_DELAYED = 1;
    public static final int APP_START_MODE_DELAYED_RIGID = 2;
    public static final int APP_START_MODE_DISABLED = 3;
    public static final int APP_START_MODE_NORMAL = 0;
    public static final int ASSIST_CONTEXT_AUTOFILL = 2;
    public static final int ASSIST_CONTEXT_BASIC = 0;
    public static final int ASSIST_CONTEXT_CONTENT = 3;
    public static final int ASSIST_CONTEXT_FULL = 1;
    public static final int BROADCAST_FAILED_USER_STOPPED = -2;
    public static final int BROADCAST_STICKY_CANT_HAVE_PERMISSION = -1;
    public static final int BROADCAST_SUCCESS = 0;
    public static final int COMPAT_MODE_ALWAYS = -1;
    public static final int COMPAT_MODE_DISABLED = 0;
    public static final int COMPAT_MODE_ENABLED = 1;
    public static final int COMPAT_MODE_NEVER = -2;
    public static final int COMPAT_MODE_TOGGLE = 2;
    public static final int COMPAT_MODE_UNKNOWN = -3;
    public static final long DROP_CLOSE_SYSTEM_DIALOGS = 174664120;
    private static final int FIRST_START_FATAL_ERROR_CODE = -100;
    private static final int FIRST_START_NON_FATAL_ERROR_CODE = 100;
    private static final int FIRST_START_SUCCESS_CODE = 0;
    public static final int FLAG_AND_LOCKED = 2;
    public static final int FLAG_AND_UNLOCKED = 4;
    public static final int FLAG_AND_UNLOCKING_OR_UNLOCKED = 8;
    public static final int FLAG_OR_STOPPED = 1;
    public static final int FOREGROUND_SERVICE_API_EVENT_BEGIN = 1;
    public static final int FOREGROUND_SERVICE_API_EVENT_END = 2;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_AUDIO = 5;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_BLUETOOTH = 2;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_CAMERA = 1;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_CDM = 9;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_LOCATION = 3;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_MEDIA_PLAYBACK = 4;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_MICROPHONE = 6;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_PHONE_CALL = 7;

    @android.annotation.SystemApi
    public static final int FOREGROUND_SERVICE_API_TYPE_USB = 8;
    public static final int INSTR_FLAG_ALWAYS_CHECK_SIGNATURE = 16;
    public static final int INSTR_FLAG_DISABLE_HIDDEN_API_CHECKS = 1;
    public static final int INSTR_FLAG_DISABLE_ISOLATED_STORAGE = 2;
    public static final int INSTR_FLAG_DISABLE_TEST_API_CHECKS = 4;
    public static final int INSTR_FLAG_INSTRUMENT_SDK_IN_SANDBOX = 64;
    public static final int INSTR_FLAG_INSTRUMENT_SDK_SANDBOX = 32;
    public static final int INSTR_FLAG_NO_RESTART = 8;
    public static final int INTENT_SENDER_ACTIVITY = 2;
    public static final int INTENT_SENDER_ACTIVITY_RESULT = 3;
    public static final int INTENT_SENDER_BROADCAST = 1;
    public static final int INTENT_SENDER_FOREGROUND_SERVICE = 5;
    public static final int INTENT_SENDER_SERVICE = 4;
    public static final int INTENT_SENDER_UNKNOWN = 0;
    private static final int LAST_START_FATAL_ERROR_CODE = -1;
    private static final int LAST_START_NON_FATAL_ERROR_CODE = 199;
    private static final int LAST_START_SUCCESS_CODE = 99;
    public static final long LOCK_DOWN_CLOSE_SYSTEM_DIALOGS = 174664365;
    public static final int LOCK_TASK_MODE_LOCKED = 1;
    public static final int LOCK_TASK_MODE_NONE = 0;
    public static final int LOCK_TASK_MODE_PINNED = 2;
    public static final int MAX_PROCESS_STATE = 20;
    public static final java.lang.String META_HOME_ALTERNATE = "android.app.home.alternate";
    public static final int MIN_PROCESS_STATE = 0;
    public static final int MOVE_TASK_NO_USER_ACTION = 2;
    public static final int MOVE_TASK_WITH_HOME = 1;
    public static final int PROCESS_CAPABILITY_ALL = 127;
    public static final int PROCESS_CAPABILITY_ALL_IMPLICIT = 6;
    public static final int PROCESS_CAPABILITY_BFSL = 16;

    @android.annotation.SystemApi
    public static final int PROCESS_CAPABILITY_FOREGROUND_AUDIO_CONTROL = 64;

    @android.annotation.SystemApi
    public static final int PROCESS_CAPABILITY_FOREGROUND_CAMERA = 2;

    @android.annotation.SystemApi
    public static final int PROCESS_CAPABILITY_FOREGROUND_LOCATION = 1;

    @android.annotation.SystemApi
    public static final int PROCESS_CAPABILITY_FOREGROUND_MICROPHONE = 4;

    @android.annotation.SystemApi
    public static final int PROCESS_CAPABILITY_NONE = 0;
    public static final int PROCESS_CAPABILITY_POWER_RESTRICTED_NETWORK = 8;
    public static final int PROCESS_CAPABILITY_USER_RESTRICTED_NETWORK = 32;
    public static final int PROCESS_STATE_BACKUP = 9;
    public static final int PROCESS_STATE_BOUND_FOREGROUND_SERVICE = 5;
    public static final int PROCESS_STATE_BOUND_TOP = 3;
    public static final int PROCESS_STATE_CACHED_ACTIVITY = 16;
    public static final int PROCESS_STATE_CACHED_ACTIVITY_CLIENT = 17;
    public static final int PROCESS_STATE_CACHED_EMPTY = 19;
    public static final int PROCESS_STATE_CACHED_RECENT = 18;
    public static final int PROCESS_STATE_FOREGROUND_SERVICE = 4;
    public static final int PROCESS_STATE_HEAVY_WEIGHT = 13;
    public static final int PROCESS_STATE_HOME = 14;
    public static final int PROCESS_STATE_IMPORTANT_BACKGROUND = 7;
    public static final int PROCESS_STATE_IMPORTANT_FOREGROUND = 6;
    public static final int PROCESS_STATE_LAST_ACTIVITY = 15;
    public static final int PROCESS_STATE_NONEXISTENT = 20;
    public static final int PROCESS_STATE_PERSISTENT = 0;
    public static final int PROCESS_STATE_PERSISTENT_UI = 1;
    public static final int PROCESS_STATE_RECEIVER = 11;
    public static final int PROCESS_STATE_SERVICE = 10;
    public static final int PROCESS_STATE_TOP = 2;
    public static final int PROCESS_STATE_TOP_SLEEPING = 12;
    public static final int PROCESS_STATE_TRANSIENT_BACKGROUND = 8;
    public static final int PROCESS_STATE_UNKNOWN = -1;
    public static final int RECENT_IGNORE_UNAVAILABLE = 2;
    public static final int RECENT_WITH_EXCLUDED = 1;
    public static final int RESTRICTION_LEVEL_ADAPTIVE_BUCKET = 30;
    public static final int RESTRICTION_LEVEL_BACKGROUND_RESTRICTED = 50;
    public static final int RESTRICTION_LEVEL_EXEMPTED = 20;
    public static final int RESTRICTION_LEVEL_HIBERNATION = 60;
    public static final int RESTRICTION_LEVEL_MAX = 100;
    public static final int RESTRICTION_LEVEL_RESTRICTED_BUCKET = 40;
    public static final int RESTRICTION_LEVEL_UNKNOWN = 0;
    public static final int RESTRICTION_LEVEL_UNRESTRICTED = 10;
    public static final int START_ABORTED = 102;
    public static final int START_ASSISTANT_HIDDEN_SESSION = -90;
    public static final int START_ASSISTANT_NOT_ACTIVE_SESSION = -89;
    public static final int START_CANCELED = -96;
    public static final int START_CLASS_NOT_FOUND = -92;
    public static final int START_DELIVERED_TO_TOP = 3;
    public static final int START_FLAG_DEBUG = 2;
    public static final int START_FLAG_DEBUG_SUSPEND = 16;
    public static final int START_FLAG_NATIVE_DEBUGGING = 8;
    public static final int START_FLAG_ONLY_IF_NEEDED = 1;
    public static final int START_FLAG_TRACK_ALLOCATION = 4;
    public static final int START_FORWARD_AND_REQUEST_CONFLICT = -93;
    public static final int START_INTENT_NOT_RESOLVED = -91;
    public static final int START_NOT_ACTIVITY = -95;
    public static final int START_NOT_CURRENT_USER_ACTIVITY = -98;
    public static final int START_NOT_VOICE_COMPATIBLE = -97;
    public static final int START_PERMISSION_DENIED = -94;
    public static final int START_RETURN_INTENT_TO_CALLER = 1;
    public static final int START_RETURN_LOCK_TASK_MODE_VIOLATION = 101;
    public static final int START_SUCCESS = 0;
    public static final int START_SWITCHES_CANCELED = 100;
    public static final int START_TASK_TO_FRONT = 2;
    public static final int START_VOICE_HIDDEN_SESSION = -100;
    public static final int START_VOICE_NOT_ACTIVE_SESSION = -99;
    public static final int STOP_USER_ON_SWITCH_DEFAULT = -1;
    public static final int STOP_USER_ON_SWITCH_FALSE = 0;
    public static final int STOP_USER_ON_SWITCH_TRUE = 1;
    public static final int UID_OBSERVER_ACTIVE = 8;
    public static final int UID_OBSERVER_CACHED = 16;
    public static final int UID_OBSERVER_CAPABILITY = 32;
    public static final int UID_OBSERVER_GONE = 2;
    public static final int UID_OBSERVER_IDLE = 4;
    public static final int UID_OBSERVER_PROCSTATE = 1;
    public static final int UID_OBSERVER_PROC_OOM_ADJ = 64;
    public static final int USER_OP_ERROR_IS_SYSTEM = -3;
    public static final int USER_OP_ERROR_RELATED_USERS_CANNOT_STOP = -4;
    public static final int USER_OP_IS_CURRENT = -2;
    public static final int USER_OP_SUCCESS = 0;
    public static final int USER_OP_UNKNOWN_USER = -1;
    android.graphics.Point mAppTaskThumbnailSize;
    private final android.content.Context mContext;
    private static java.lang.String TAG = "ActivityManager";
    private static volatile boolean sSystemReady = false;
    private static volatile int sCurrentUser$ravenwood = -10000;
    private static final boolean DEVELOPMENT_FORCE_LOW_RAM = android.os.SystemProperties.getBoolean("debug.force_low_ram", false);
    private static final android.util.Singleton<android.app.IActivityManager> IActivityManagerSingleton = new android.util.Singleton<android.app.IActivityManager>() { // from class: android.app.ActivityManager.3
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.app.IActivityManager create() {
            return android.app.IActivityManager.Stub.asInterface(android.os.ServiceManager.getService("activity"));
        }
    };
    final android.util.ArrayMap<android.app.ActivityManager.OnUidImportanceListener, android.app.ActivityManager.MyUidObserver> mImportanceListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.app.ActivityManager.UidFrozenStateChangedCallback, java.util.concurrent.Executor> mFrozenStateChangedCallbacks = new android.util.ArrayMap<>();
    private final android.app.IUidFrozenStateChangedCallback mFrozenStateChangedCallback = new android.app.ActivityManager.AnonymousClass1();
    private final java.util.ArrayList<android.app.ActivityManager.AppStartInfoCallbackWrapper> mAppStartInfoCallbacks = new java.util.ArrayList<>();
    private android.app.IApplicationStartInfoCompleteListener mAppStartInfoCompleteListener = null;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ForegroundServiceApiEvent {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ForegroundServiceApiType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MoveTaskFlags {
    }

    @android.annotation.SystemApi
    public interface OnUidImportanceListener {
        void onUidImportance(int i, int i2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProcessCapability {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProcessState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RestrictionLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StopUserOnSwitch {
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public interface UidFrozenStateChangedCallback {

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public static final int UID_FROZEN_STATE_FROZEN = 1;

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public static final int UID_FROZEN_STATE_UNFROZEN = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface UidFrozenState {
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        void onUidFrozenStateChanged(int[] iArr, int[] iArr2);
    }

    static final class MyUidObserver extends android.app.UidObserver {
        final android.content.Context mContext;
        final android.app.ActivityManager.OnUidImportanceListener mListener;

        MyUidObserver(android.app.ActivityManager.OnUidImportanceListener onUidImportanceListener, android.content.Context context) {
            this.mListener = onUidImportanceListener;
            this.mContext = context;
        }

        @Override // android.app.UidObserver, android.app.IUidObserver
        public void onUidStateChanged(int i, int i2, long j, int i3) {
            this.mListener.onUidImportance(i, android.app.ActivityManager.RunningAppProcessInfo.procStateToImportanceForClient(i2, this.mContext));
        }

        @Override // android.app.UidObserver, android.app.IUidObserver
        public void onUidGone(int i, boolean z) {
            this.mListener.onUidImportance(i, 1000);
        }
    }

    /* renamed from: android.app.ActivityManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.app.IUidFrozenStateChangedCallback.Stub {
        AnonymousClass1() {
        }

        @Override // android.app.IUidFrozenStateChangedCallback
        public void onUidFrozenStateChanged(final int[] iArr, final int[] iArr2) {
            synchronized (android.app.ActivityManager.this.mFrozenStateChangedCallbacks) {
                android.app.ActivityManager.this.mFrozenStateChangedCallbacks.forEach(new java.util.function.BiConsumer() { // from class: android.app.ActivityManager$1$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        java.util.concurrent.Executor executor = (java.util.concurrent.Executor) obj2;
                        executor.execute(new java.lang.Runnable() { // from class: android.app.ActivityManager$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.app.ActivityManager.UidFrozenStateChangedCallback.this.onUidFrozenStateChanged(r2, r3);
                            }
                        });
                    }
                });
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void registerUidFrozenStateChangedCallback(java.util.concurrent.Executor executor, android.app.ActivityManager.UidFrozenStateChangedCallback uidFrozenStateChangedCallback) {
        com.android.internal.util.Preconditions.checkNotNull(executor, "executor cannot be null");
        com.android.internal.util.Preconditions.checkNotNull(uidFrozenStateChangedCallback, "callback cannot be null");
        synchronized (this.mFrozenStateChangedCallbacks) {
            if (this.mFrozenStateChangedCallbacks.containsKey(uidFrozenStateChangedCallback)) {
                throw new java.lang.IllegalStateException("Callback already registered: " + uidFrozenStateChangedCallback);
            }
            this.mFrozenStateChangedCallbacks.put(uidFrozenStateChangedCallback, executor);
            if (this.mFrozenStateChangedCallbacks.size() > 1) {
                return;
            }
            try {
                getService().registerUidFrozenStateChangedCallback(this.mFrozenStateChangedCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void unregisterUidFrozenStateChangedCallback(android.app.ActivityManager.UidFrozenStateChangedCallback uidFrozenStateChangedCallback) {
        com.android.internal.util.Preconditions.checkNotNull(uidFrozenStateChangedCallback, "callback cannot be null");
        synchronized (this.mFrozenStateChangedCallbacks) {
            this.mFrozenStateChangedCallbacks.remove(uidFrozenStateChangedCallback);
            if (this.mFrozenStateChangedCallbacks.isEmpty()) {
                try {
                    getService().unregisterUidFrozenStateChangedCallback(this.mFrozenStateChangedCallback);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public int[] getUidFrozenState(int[] iArr) {
        try {
            return getService().getUidFrozenState(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void printCapabilitiesSummary(java.io.PrintWriter printWriter, int i) {
        printWriter.print((i & 1) != 0 ? android.text.format.DateFormat.STANDALONE_MONTH : '-');
        printWriter.print((i & 2) != 0 ? 'C' : '-');
        printWriter.print((i & 4) != 0 ? android.text.format.DateFormat.MONTH : '-');
        printWriter.print((i & 8) != 0 ? android.telephony.PhoneNumberUtils.WILD : '-');
        printWriter.print((i & 16) != 0 ? 'F' : '-');
        printWriter.print((i & 32) != 0 ? 'U' : '-');
        printWriter.print((i & 64) != 0 ? android.text.format.DateFormat.CAPITAL_AM_PM : '-');
    }

    public static void printCapabilitiesSummary(java.lang.StringBuilder sb, int i) {
        sb.append((i & 1) != 0 ? android.text.format.DateFormat.STANDALONE_MONTH : '-');
        sb.append((i & 2) != 0 ? 'C' : '-');
        sb.append((i & 4) != 0 ? android.text.format.DateFormat.MONTH : '-');
        sb.append((i & 8) != 0 ? android.telephony.PhoneNumberUtils.WILD : '-');
        sb.append((i & 16) != 0 ? 'F' : '-');
        sb.append((i & 32) != 0 ? 'U' : '-');
        sb.append((i & 64) != 0 ? android.text.format.DateFormat.CAPITAL_AM_PM : '-');
    }

    public static void printCapabilitiesFull(java.io.PrintWriter printWriter, int i) {
        printCapabilitiesSummary(printWriter, i);
        int i2 = i & (-128);
        if (i2 != 0) {
            printWriter.print("+0x");
            printWriter.print(java.lang.Integer.toHexString(i2));
        }
    }

    public static java.lang.String getCapabilitiesSummary(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        printCapabilitiesSummary(sb, i);
        return sb.toString();
    }

    public static final int processStateAmToProto(int i) {
        switch (i) {
            case -1:
                return 999;
            case 0:
                return 1000;
            case 1:
                return 1001;
            case 2:
                return 1002;
            case 3:
                return 1020;
            case 4:
                return 1003;
            case 5:
                return 1004;
            case 6:
                return 1005;
            case 7:
                return 1006;
            case 8:
                return 1007;
            case 9:
                return 1008;
            case 10:
                return 1009;
            case 11:
                return 1010;
            case 12:
                return 1011;
            case 13:
                return 1012;
            case 14:
                return 1013;
            case 15:
                return 1014;
            case 16:
                return 1015;
            case 17:
                return 1016;
            case 18:
                return 1017;
            case 19:
                return 1018;
            case 20:
                return 1019;
            default:
                return 998;
        }
    }

    public static final boolean isProcStateBackground(int i) {
        return i >= 8;
    }

    public static final boolean isProcStateCached(int i) {
        return i >= 16;
    }

    public static boolean isForegroundService(int i) {
        return i == 4;
    }

    ActivityManager(android.content.Context context, android.os.Handler handler) {
        this.mContext = context;
    }

    public static final boolean isStartResultSuccessful(int i) {
        return i >= 0 && i <= 99;
    }

    public static final boolean isStartResultFatalError(int i) {
        return -100 <= i && i <= -1;
    }

    public static java.lang.String restrictionLevelToName(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 10:
                return "unrestricted";
            case 20:
                return "exempted";
            case 30:
                return "adaptive_bucket";
            case 40:
                return "restricted_bucket";
            case 50:
                return "background_restricted";
            case 60:
                return "hibernation";
            case 100:
                return "max";
            default:
                return "";
        }
    }

    public int getFrontActivityScreenCompatMode() {
        try {
            return getTaskService().getFrontActivityScreenCompatMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFrontActivityScreenCompatMode(int i) {
        try {
            getTaskService().setFrontActivityScreenCompatMode(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPackageScreenCompatMode(java.lang.String str) {
        try {
            return getTaskService().getPackageScreenCompatMode(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPackageScreenCompatMode(java.lang.String str, int i) {
        try {
            getTaskService().setPackageScreenCompatMode(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getPackageAskScreenCompat(java.lang.String str) {
        try {
            return getTaskService().getPackageAskScreenCompat(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPackageAskScreenCompat(java.lang.String str, boolean z) {
        try {
            getTaskService().setPackageAskScreenCompat(str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMemoryClass() {
        return staticGetMemoryClass();
    }

    public static int staticGetMemoryClass() {
        java.lang.String str = android.os.SystemProperties.get("dalvik.vm.heapgrowthlimit", "");
        if (str != null && !"".equals(str)) {
            return java.lang.Integer.parseInt(str.substring(0, str.length() - 1));
        }
        return staticGetLargeMemoryClass();
    }

    public int getLargeMemoryClass() {
        return staticGetLargeMemoryClass();
    }

    public static int staticGetLargeMemoryClass() {
        return java.lang.Integer.parseInt(android.os.SystemProperties.get("dalvik.vm.heapsize", "16m").substring(0, r0.length() - 1));
    }

    public boolean isLowRamDevice() {
        return isLowRamDeviceStatic();
    }

    public static boolean isLowRamDeviceStatic() {
        return com.android.internal.os.RoSystemProperties.CONFIG_LOW_RAM || (android.os.Build.IS_DEBUGGABLE && DEVELOPMENT_FORCE_LOW_RAM);
    }

    public static boolean isSmallBatteryDevice() {
        return com.android.internal.os.RoSystemProperties.CONFIG_SMALL_BATTERY;
    }

    public static boolean isHighEndGfx() {
        return (isLowRamDeviceStatic() || com.android.internal.os.RoSystemProperties.CONFIG_AVOID_GFX_ACCEL || android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_avoidGfxAccel)) ? false : true;
    }

    public long getTotalRam() {
        com.android.internal.util.MemInfoReader memInfoReader = new com.android.internal.util.MemInfoReader();
        memInfoReader.readMemInfo();
        return memInfoReader.getTotalSize();
    }

    @java.lang.Deprecated
    public static int getMaxRecentTasksStatic() {
        return android.app.ActivityTaskManager.getMaxRecentTasksStatic();
    }

    public static class TaskDescription implements android.os.Parcelable {
        private static final java.lang.String ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND = "task_description_color_background";
        private static final java.lang.String ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING = "task_description_color_background_floating";
        private static final java.lang.String ATTR_TASKDESCRIPTIONCOLOR_PRIMARY = "task_description_color";
        private static final java.lang.String ATTR_TASKDESCRIPTIONICON_FILENAME = "task_description_icon_filename";
        private static final java.lang.String ATTR_TASKDESCRIPTIONICON_RESOURCE = "task_description_icon_resource";
        private static final java.lang.String ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE = "task_description_icon_package";
        private static final java.lang.String ATTR_TASKDESCRIPTIONLABEL = "task_description_label";
        public static final java.lang.String ATTR_TASKDESCRIPTION_PREFIX = "task_description_";
        public static final android.os.Parcelable.Creator<android.app.ActivityManager.TaskDescription> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityManager.TaskDescription>() { // from class: android.app.ActivityManager.TaskDescription.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.TaskDescription createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityManager.TaskDescription(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.TaskDescription[] newArray(int i) {
                return new android.app.ActivityManager.TaskDescription[i];
            }
        };
        private int mColorBackground;
        private int mColorBackgroundFloating;
        private int mColorPrimary;
        private boolean mEnsureNavigationBarContrastWhenTransparent;
        private boolean mEnsureStatusBarContrastWhenTransparent;
        private android.graphics.drawable.Icon mIcon;
        private java.lang.String mIconFilename;
        private java.lang.String mLabel;
        private int mMinHeight;
        private int mMinWidth;
        private int mNavigationBarColor;
        private int mResizeMode;
        private int mStatusBarAppearance;
        private int mStatusBarColor;

        public static final class Builder {
            private java.lang.String mLabel = null;
            private int mIconRes = 0;
            private int mPrimaryColor = 0;
            private int mBackgroundColor = 0;
            private int mStatusBarColor = 0;
            private int mNavigationBarColor = 0;

            public android.app.ActivityManager.TaskDescription.Builder setLabel(java.lang.String str) {
                this.mLabel = str;
                return this;
            }

            public android.app.ActivityManager.TaskDescription.Builder setIcon(int i) {
                this.mIconRes = i;
                return this;
            }

            public android.app.ActivityManager.TaskDescription.Builder setPrimaryColor(int i) {
                this.mPrimaryColor = i;
                return this;
            }

            public android.app.ActivityManager.TaskDescription.Builder setBackgroundColor(int i) {
                this.mBackgroundColor = i;
                return this;
            }

            public android.app.ActivityManager.TaskDescription.Builder setStatusBarColor(int i) {
                this.mStatusBarColor = i;
                return this;
            }

            public android.app.ActivityManager.TaskDescription.Builder setNavigationBarColor(int i) {
                this.mNavigationBarColor = i;
                return this;
            }

            public android.app.ActivityManager.TaskDescription build() {
                return new android.app.ActivityManager.TaskDescription(this.mLabel, this.mIconRes == 0 ? null : android.graphics.drawable.Icon.createWithResource(android.app.ActivityThread.currentPackageName(), this.mIconRes), this.mPrimaryColor, this.mBackgroundColor, this.mStatusBarColor, this.mNavigationBarColor, 0, false, false, 2, -1, -1, 0);
            }
        }

        @java.lang.Deprecated
        public TaskDescription(java.lang.String str, int i, int i2) {
            this(str, android.graphics.drawable.Icon.createWithResource(android.app.ActivityThread.currentPackageName(), i), i2, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
            if (i2 != 0 && android.graphics.Color.alpha(i2) != 255) {
                throw new java.lang.RuntimeException("A TaskDescription's primary color should be opaque");
            }
        }

        @java.lang.Deprecated
        public TaskDescription(java.lang.String str, int i) {
            this(str, android.graphics.drawable.Icon.createWithResource(android.app.ActivityThread.currentPackageName(), i), 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        @java.lang.Deprecated
        public TaskDescription(java.lang.String str) {
            this(str, null, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        @java.lang.Deprecated
        public TaskDescription() {
            this(null, null, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        @java.lang.Deprecated
        public TaskDescription(java.lang.String str, android.graphics.Bitmap bitmap, int i) {
            this(str, bitmap != null ? android.graphics.drawable.Icon.createWithBitmap(bitmap) : null, i, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
            if (i != 0 && android.graphics.Color.alpha(i) != 255) {
                throw new java.lang.RuntimeException("A TaskDescription's primary color should be opaque");
            }
        }

        @java.lang.Deprecated
        public TaskDescription(java.lang.String str, android.graphics.Bitmap bitmap) {
            this(str, bitmap != null ? android.graphics.drawable.Icon.createWithBitmap(bitmap) : null, 0, 0, 0, 0, 0, false, false, 2, -1, -1, 0);
        }

        public TaskDescription(java.lang.String str, android.graphics.drawable.Icon icon, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, int i6, int i7, int i8, int i9) {
            this.mLabel = str;
            this.mIcon = icon;
            this.mColorPrimary = i;
            this.mColorBackground = i2;
            this.mStatusBarColor = i3;
            this.mNavigationBarColor = i4;
            this.mStatusBarAppearance = i5;
            this.mEnsureStatusBarContrastWhenTransparent = z;
            this.mEnsureNavigationBarContrastWhenTransparent = z2;
            this.mResizeMode = i6;
            this.mMinWidth = i7;
            this.mMinHeight = i8;
            this.mColorBackgroundFloating = i9;
        }

        public TaskDescription(android.app.ActivityManager.TaskDescription taskDescription) {
            copyFrom(taskDescription);
        }

        public void copyFrom(android.app.ActivityManager.TaskDescription taskDescription) {
            this.mLabel = taskDescription.mLabel;
            this.mIcon = taskDescription.mIcon;
            this.mIconFilename = taskDescription.mIconFilename;
            this.mColorPrimary = taskDescription.mColorPrimary;
            this.mColorBackground = taskDescription.mColorBackground;
            this.mStatusBarColor = taskDescription.mStatusBarColor;
            this.mNavigationBarColor = taskDescription.mNavigationBarColor;
            this.mStatusBarAppearance = taskDescription.mStatusBarAppearance;
            this.mEnsureStatusBarContrastWhenTransparent = taskDescription.mEnsureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent = taskDescription.mEnsureNavigationBarContrastWhenTransparent;
            this.mResizeMode = taskDescription.mResizeMode;
            this.mMinWidth = taskDescription.mMinWidth;
            this.mMinHeight = taskDescription.mMinHeight;
            this.mColorBackgroundFloating = taskDescription.mColorBackgroundFloating;
        }

        public void copyFromPreserveHiddenFields(android.app.ActivityManager.TaskDescription taskDescription) {
            this.mLabel = taskDescription.mLabel;
            this.mIcon = taskDescription.mIcon;
            this.mIconFilename = taskDescription.mIconFilename;
            this.mColorPrimary = taskDescription.mColorPrimary;
            if (taskDescription.mColorBackground != 0) {
                this.mColorBackground = taskDescription.mColorBackground;
            }
            if (taskDescription.mStatusBarColor != 0) {
                this.mStatusBarColor = taskDescription.mStatusBarColor;
            }
            if (taskDescription.mNavigationBarColor != 0) {
                this.mNavigationBarColor = taskDescription.mNavigationBarColor;
            }
            if (taskDescription.mStatusBarAppearance != 0) {
                this.mStatusBarAppearance = taskDescription.mStatusBarAppearance;
            }
            this.mEnsureStatusBarContrastWhenTransparent = taskDescription.mEnsureStatusBarContrastWhenTransparent;
            this.mEnsureNavigationBarContrastWhenTransparent = taskDescription.mEnsureNavigationBarContrastWhenTransparent;
            if (taskDescription.mResizeMode != 2) {
                this.mResizeMode = taskDescription.mResizeMode;
            }
            if (taskDescription.mMinWidth != -1) {
                this.mMinWidth = taskDescription.mMinWidth;
            }
            if (taskDescription.mMinHeight != -1) {
                this.mMinHeight = taskDescription.mMinHeight;
            }
            if (taskDescription.mColorBackgroundFloating != 0) {
                this.mColorBackgroundFloating = taskDescription.mColorBackgroundFloating;
            }
        }

        private TaskDescription(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }

        public void setLabel(java.lang.String str) {
            this.mLabel = str;
        }

        public void setPrimaryColor(int i) {
            if (i != 0 && android.graphics.Color.alpha(i) != 255) {
                throw new java.lang.RuntimeException("A TaskDescription's primary color should be opaque");
            }
            this.mColorPrimary = i;
        }

        public void setBackgroundColor(int i) {
            if (i != 0 && android.graphics.Color.alpha(i) != 255) {
                throw new java.lang.RuntimeException("A TaskDescription's background color should be opaque");
            }
            this.mColorBackground = i;
        }

        public void setBackgroundColorFloating(int i) {
            if (i != 0 && android.graphics.Color.alpha(i) != 255) {
                throw new java.lang.RuntimeException("A TaskDescription's background color floating should be opaque");
            }
            this.mColorBackgroundFloating = i;
        }

        public void setStatusBarColor(int i) {
            this.mStatusBarColor = i;
        }

        public void setNavigationBarColor(int i) {
            this.mNavigationBarColor = i;
        }

        public void setIcon(android.graphics.drawable.Icon icon) {
            this.mIcon = icon;
        }

        public void setIconFilename(java.lang.String str) {
            this.mIconFilename = str;
            if (str != null) {
                this.mIcon = null;
            }
        }

        public void setResizeMode(int i) {
            this.mResizeMode = i;
        }

        public void setMinWidth(int i) {
            this.mMinWidth = i;
        }

        public void setMinHeight(int i) {
            this.mMinHeight = i;
        }

        public java.lang.String getLabel() {
            return this.mLabel;
        }

        public android.graphics.drawable.Icon loadIcon() {
            if (this.mIcon != null) {
                return this.mIcon;
            }
            android.graphics.Bitmap loadTaskDescriptionIcon = loadTaskDescriptionIcon(this.mIconFilename, android.os.UserHandle.myUserId());
            if (loadTaskDescriptionIcon != null) {
                return android.graphics.drawable.Icon.createWithBitmap(loadTaskDescriptionIcon);
            }
            return null;
        }

        @java.lang.Deprecated
        public android.graphics.Bitmap getIcon() {
            android.graphics.Bitmap inMemoryIcon = getInMemoryIcon();
            if (inMemoryIcon != null) {
                return inMemoryIcon;
            }
            return loadTaskDescriptionIcon(this.mIconFilename, android.os.UserHandle.myUserId());
        }

        public android.graphics.drawable.Icon getRawIcon() {
            return this.mIcon;
        }

        public java.lang.String getIconResourcePackage() {
            if (this.mIcon != null && this.mIcon.getType() == 2) {
                return this.mIcon.getResPackage();
            }
            return "";
        }

        public int getIconResource() {
            if (this.mIcon != null && this.mIcon.getType() == 2) {
                return this.mIcon.getResId();
            }
            return 0;
        }

        public java.lang.String getIconFilename() {
            return this.mIconFilename;
        }

        public android.graphics.Bitmap getInMemoryIcon() {
            if (this.mIcon != null && this.mIcon.getType() == 1) {
                return this.mIcon.getBitmap();
            }
            return null;
        }

        public static android.graphics.Bitmap loadTaskDescriptionIcon(java.lang.String str, int i) {
            if (str != null) {
                try {
                    return android.app.ActivityManager.getTaskService().getTaskDescriptionIcon(str, i);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return null;
        }

        public int getPrimaryColor() {
            return this.mColorPrimary;
        }

        public int getBackgroundColor() {
            return this.mColorBackground;
        }

        public int getBackgroundColorFloating() {
            return this.mColorBackgroundFloating;
        }

        public int getStatusBarColor() {
            return this.mStatusBarColor;
        }

        public int getNavigationBarColor() {
            return this.mNavigationBarColor;
        }

        public boolean getEnsureStatusBarContrastWhenTransparent() {
            return this.mEnsureStatusBarContrastWhenTransparent;
        }

        public int getStatusBarAppearance() {
            return this.mStatusBarAppearance;
        }

        public void setEnsureStatusBarContrastWhenTransparent(boolean z) {
            this.mEnsureStatusBarContrastWhenTransparent = z;
        }

        public void setStatusBarAppearance(int i) {
            this.mStatusBarAppearance = i;
        }

        public boolean getEnsureNavigationBarContrastWhenTransparent() {
            return this.mEnsureNavigationBarContrastWhenTransparent;
        }

        public void setEnsureNavigationBarContrastWhenTransparent(boolean z) {
            this.mEnsureNavigationBarContrastWhenTransparent = z;
        }

        public int getResizeMode() {
            return this.mResizeMode;
        }

        public int getMinWidth() {
            return this.mMinWidth;
        }

        public int getMinHeight() {
            return this.mMinHeight;
        }

        public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (this.mLabel != null) {
                typedXmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONLABEL, this.mLabel);
            }
            if (this.mColorPrimary != 0) {
                typedXmlSerializer.attributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_PRIMARY, this.mColorPrimary);
            }
            if (this.mColorBackground != 0) {
                typedXmlSerializer.attributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND, this.mColorBackground);
            }
            if (this.mColorBackgroundFloating != 0) {
                typedXmlSerializer.attributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING, this.mColorBackgroundFloating);
            }
            if (this.mIconFilename != null) {
                typedXmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONICON_FILENAME, this.mIconFilename);
            }
            if (this.mIcon != null && this.mIcon.getType() == 2) {
                typedXmlSerializer.attributeInt(null, ATTR_TASKDESCRIPTIONICON_RESOURCE, this.mIcon.getResId());
                typedXmlSerializer.attribute(null, ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE, this.mIcon.getResPackage());
            }
        }

        public void restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
            java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, ATTR_TASKDESCRIPTIONLABEL);
            if (attributeValue != null) {
                setLabel(attributeValue);
            }
            int attributeIntHex = typedXmlPullParser.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_PRIMARY, 0);
            if (attributeIntHex != 0) {
                setPrimaryColor(attributeIntHex);
            }
            int attributeIntHex2 = typedXmlPullParser.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND, 0);
            if (attributeIntHex2 != 0) {
                setBackgroundColor(attributeIntHex2);
            }
            int attributeIntHex3 = typedXmlPullParser.getAttributeIntHex(null, ATTR_TASKDESCRIPTIONCOLOR_BACKGROUND_FLOATING, 0);
            if (attributeIntHex3 != 0) {
                setBackgroundColorFloating(attributeIntHex3);
            }
            java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue(null, ATTR_TASKDESCRIPTIONICON_FILENAME);
            if (attributeValue2 != null) {
                setIconFilename(attributeValue2);
            }
            int attributeInt = typedXmlPullParser.getAttributeInt(null, ATTR_TASKDESCRIPTIONICON_RESOURCE, 0);
            java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue(null, ATTR_TASKDESCRIPTIONICON_RESOURCE_PACKAGE);
            if (attributeInt != 0 && attributeValue3 != null) {
                setIcon(android.graphics.drawable.Icon.createWithResource(attributeValue3, attributeInt));
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            if (this.mLabel == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcel.writeString(this.mLabel);
            }
            android.graphics.Bitmap inMemoryIcon = getInMemoryIcon();
            if (this.mIcon == null || (inMemoryIcon != null && inMemoryIcon.isRecycled())) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.mIcon.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mColorPrimary);
            parcel.writeInt(this.mColorBackground);
            parcel.writeInt(this.mStatusBarColor);
            parcel.writeInt(this.mNavigationBarColor);
            parcel.writeInt(this.mStatusBarAppearance);
            parcel.writeBoolean(this.mEnsureStatusBarContrastWhenTransparent);
            parcel.writeBoolean(this.mEnsureNavigationBarContrastWhenTransparent);
            parcel.writeInt(this.mResizeMode);
            parcel.writeInt(this.mMinWidth);
            parcel.writeInt(this.mMinHeight);
            if (this.mIconFilename == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcel.writeString(this.mIconFilename);
            }
            parcel.writeInt(this.mColorBackgroundFloating);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.mLabel = parcel.readInt() > 0 ? parcel.readString() : null;
            if (parcel.readInt() > 0) {
                this.mIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
            }
            this.mColorPrimary = parcel.readInt();
            this.mColorBackground = parcel.readInt();
            this.mStatusBarColor = parcel.readInt();
            this.mNavigationBarColor = parcel.readInt();
            this.mStatusBarAppearance = parcel.readInt();
            this.mEnsureStatusBarContrastWhenTransparent = parcel.readBoolean();
            this.mEnsureNavigationBarContrastWhenTransparent = parcel.readBoolean();
            this.mResizeMode = parcel.readInt();
            this.mMinWidth = parcel.readInt();
            this.mMinHeight = parcel.readInt();
            this.mIconFilename = parcel.readInt() > 0 ? parcel.readString() : null;
            this.mColorBackgroundFloating = parcel.readInt();
        }

        public java.lang.String toString() {
            return "TaskDescription Label: " + this.mLabel + " Icon: " + this.mIcon + " IconFilename: " + this.mIconFilename + " colorPrimary: " + this.mColorPrimary + " colorBackground: " + this.mColorBackground + " statusBarColor: " + this.mStatusBarColor + (this.mEnsureStatusBarContrastWhenTransparent ? " (contrast when transparent)" : "") + " navigationBarColor: " + this.mNavigationBarColor + (this.mEnsureNavigationBarContrastWhenTransparent ? " (contrast when transparent)" : "") + " resizeMode: " + android.content.pm.ActivityInfo.resizeModeToString(this.mResizeMode) + " minWidth: " + this.mMinWidth + " minHeight: " + this.mMinHeight + " colorBackgrounFloating: " + this.mColorBackgroundFloating;
        }

        public int hashCode() {
            int i;
            if (this.mLabel == null) {
                i = 17;
            } else {
                i = 527 + this.mLabel.hashCode();
            }
            if (this.mIcon != null) {
                i = (i * 31) + this.mIcon.hashCode();
            }
            if (this.mIconFilename != null) {
                i = (i * 31) + this.mIconFilename.hashCode();
            }
            return (((((((((((((((((((((i * 31) + this.mColorPrimary) * 31) + this.mColorBackground) * 31) + this.mColorBackgroundFloating) * 31) + this.mStatusBarColor) * 31) + this.mNavigationBarColor) * 31) + this.mStatusBarAppearance) * 31) + (this.mEnsureStatusBarContrastWhenTransparent ? 1 : 0)) * 31) + (this.mEnsureNavigationBarContrastWhenTransparent ? 1 : 0)) * 31) + this.mResizeMode) * 31) + this.mMinWidth) * 31) + this.mMinHeight;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.app.ActivityManager.TaskDescription)) {
                return false;
            }
            android.app.ActivityManager.TaskDescription taskDescription = (android.app.ActivityManager.TaskDescription) obj;
            return android.text.TextUtils.equals(this.mLabel, taskDescription.mLabel) && android.text.TextUtils.equals(this.mIconFilename, taskDescription.mIconFilename) && this.mIcon == taskDescription.mIcon && this.mColorPrimary == taskDescription.mColorPrimary && this.mColorBackground == taskDescription.mColorBackground && this.mStatusBarColor == taskDescription.mStatusBarColor && this.mNavigationBarColor == taskDescription.mNavigationBarColor && this.mStatusBarAppearance == taskDescription.mStatusBarAppearance && this.mEnsureStatusBarContrastWhenTransparent == taskDescription.mEnsureStatusBarContrastWhenTransparent && this.mEnsureNavigationBarContrastWhenTransparent == taskDescription.mEnsureNavigationBarContrastWhenTransparent && this.mResizeMode == taskDescription.mResizeMode && this.mMinWidth == taskDescription.mMinWidth && this.mMinHeight == taskDescription.mMinHeight && this.mColorBackgroundFloating == taskDescription.mColorBackgroundFloating;
        }

        public static boolean equals(android.app.ActivityManager.TaskDescription taskDescription, android.app.ActivityManager.TaskDescription taskDescription2) {
            if (taskDescription == null && taskDescription2 == null) {
                return true;
            }
            if (taskDescription != null && taskDescription2 != null) {
                return taskDescription.equals(taskDescription2);
            }
            return false;
        }
    }

    public static class RecentTaskInfo extends android.app.TaskInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityManager.RecentTaskInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityManager.RecentTaskInfo>() { // from class: android.app.ActivityManager.RecentTaskInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.RecentTaskInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityManager.RecentTaskInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.RecentTaskInfo[] newArray(int i) {
                return new android.app.ActivityManager.RecentTaskInfo[i];
            }
        };

        @java.lang.Deprecated
        public int affiliatedTaskId;
        public java.util.ArrayList<android.app.ActivityManager.RecentTaskInfo> childrenTaskInfos;

        @java.lang.Deprecated
        public java.lang.CharSequence description;

        @java.lang.Deprecated
        public int id;
        public android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData lastSnapshotData;

        @java.lang.Deprecated
        public int persistentId;

        public static class PersistedTaskSnapshotData {
            public android.graphics.Point bufferSize;
            public android.graphics.Rect contentInsets;
            public android.graphics.Point taskSize;

            public void set(android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData persistedTaskSnapshotData) {
                this.taskSize = persistedTaskSnapshotData.taskSize;
                this.contentInsets = persistedTaskSnapshotData.contentInsets;
                this.bufferSize = persistedTaskSnapshotData.bufferSize;
            }

            public void set(android.window.TaskSnapshot taskSnapshot) {
                android.graphics.Point point = null;
                if (taskSnapshot == null) {
                    this.taskSize = null;
                    this.contentInsets = null;
                    this.bufferSize = null;
                } else {
                    android.hardware.HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
                    this.taskSize = new android.graphics.Point(taskSnapshot.getTaskSize());
                    this.contentInsets = new android.graphics.Rect(taskSnapshot.getContentInsets());
                    if (hardwareBuffer != null) {
                        point = new android.graphics.Point(hardwareBuffer.getWidth(), hardwareBuffer.getHeight());
                    }
                    this.bufferSize = point;
                }
            }
        }

        public RecentTaskInfo() {
            this.childrenTaskInfos = new java.util.ArrayList<>();
            this.lastSnapshotData = new android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData();
        }

        private RecentTaskInfo(android.os.Parcel parcel) {
            this.childrenTaskInfos = new java.util.ArrayList<>();
            this.lastSnapshotData = new android.app.ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData();
            readFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.app.TaskInfo
        public void readFromParcel(android.os.Parcel parcel) {
            this.id = parcel.readInt();
            this.persistentId = parcel.readInt();
            this.childrenTaskInfos = parcel.readArrayList(android.app.ActivityManager.RecentTaskInfo.class.getClassLoader(), android.app.ActivityManager.RecentTaskInfo.class);
            this.lastSnapshotData.taskSize = (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR);
            this.lastSnapshotData.contentInsets = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
            this.lastSnapshotData.bufferSize = (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR);
            super.readFromParcel(parcel);
        }

        @Override // android.app.TaskInfo, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.id);
            parcel.writeInt(this.persistentId);
            parcel.writeList(this.childrenTaskInfos);
            parcel.writeTypedObject(this.lastSnapshotData.taskSize, i);
            parcel.writeTypedObject(this.lastSnapshotData.contentInsets, i);
            parcel.writeTypedObject(this.lastSnapshotData.bufferSize, i);
            super.writeToParcel(parcel, i);
        }

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println();
            printWriter.print("   ");
            printWriter.print(" id=");
            printWriter.print(this.persistentId);
            printWriter.print(" userId=");
            printWriter.print(this.userId);
            printWriter.print(" hasTask=");
            boolean z = true;
            printWriter.print(this.id != -1);
            printWriter.print(" lastActiveTime=");
            printWriter.println(this.lastActiveTime);
            printWriter.print("   ");
            printWriter.print(" baseIntent=");
            printWriter.println(this.baseIntent);
            if (this.baseActivity != null) {
                printWriter.print("   ");
                printWriter.print(" baseActivity=");
                printWriter.println(this.baseActivity.toShortString());
            }
            if (this.topActivity != null) {
                printWriter.print("   ");
                printWriter.print(" topActivity=");
                printWriter.println(this.topActivity.toShortString());
            }
            if (this.origActivity != null) {
                printWriter.print("   ");
                printWriter.print(" origActivity=");
                printWriter.println(this.origActivity.toShortString());
            }
            if (this.realActivity != null) {
                printWriter.print("   ");
                printWriter.print(" realActivity=");
                printWriter.println(this.realActivity.toShortString());
            }
            printWriter.print("   ");
            printWriter.print(" isExcluded=");
            printWriter.print((this.baseIntent.getFlags() & 8388608) != 0);
            printWriter.print(" activityType=");
            printWriter.print(android.app.WindowConfiguration.activityTypeToString(getActivityType()));
            printWriter.print(" windowingMode=");
            printWriter.print(android.app.WindowConfiguration.windowingModeToString(getWindowingMode()));
            printWriter.print(" supportsMultiWindow=");
            printWriter.println(this.supportsMultiWindow);
            if (this.taskDescription != null) {
                printWriter.print("   ");
                android.app.ActivityManager.TaskDescription taskDescription = this.taskDescription;
                printWriter.print(" taskDescription {");
                printWriter.print(" colorBackground=#");
                printWriter.print(java.lang.Integer.toHexString(taskDescription.getBackgroundColor()));
                printWriter.print(" colorPrimary=#");
                printWriter.print(java.lang.Integer.toHexString(taskDescription.getPrimaryColor()));
                printWriter.print(" iconRes=");
                printWriter.print(taskDescription.getIconResourcePackage() + "/" + taskDescription.getIconResource());
                printWriter.print(" iconBitmap=");
                if (taskDescription.getIconFilename() == null && taskDescription.getInMemoryIcon() == null) {
                    z = false;
                }
                printWriter.print(z);
                printWriter.print(" resizeMode=");
                printWriter.print(android.content.pm.ActivityInfo.resizeModeToString(taskDescription.getResizeMode()));
                printWriter.print(" minWidth=");
                printWriter.print(taskDescription.getMinWidth());
                printWriter.print(" minHeight=");
                printWriter.print(taskDescription.getMinHeight());
                printWriter.print(" colorBackgroundFloating=#");
                printWriter.print(java.lang.Integer.toHexString(taskDescription.getBackgroundColorFloating()));
                printWriter.println(" }");
            }
            printWriter.print("   ");
            printWriter.print(" lastSnapshotData {");
            printWriter.print(" taskSize=" + this.lastSnapshotData.taskSize);
            printWriter.print(" contentInsets=" + this.lastSnapshotData.contentInsets);
            printWriter.print(" bufferSize=" + this.lastSnapshotData.bufferSize);
            printWriter.println(" }");
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.app.ActivityManager.RecentTaskInfo> getRecentTasks(int i, int i2) throws java.lang.SecurityException {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("The requested number of tasks should be >= 0");
        }
        return android.app.ActivityTaskManager.getInstance().getRecentTasks(i, i2, this.mContext.getUserId());
    }

    public static class RunningTaskInfo extends android.app.TaskInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityManager.RunningTaskInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityManager.RunningTaskInfo>() { // from class: android.app.ActivityManager.RunningTaskInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.RunningTaskInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityManager.RunningTaskInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.RunningTaskInfo[] newArray(int i) {
                return new android.app.ActivityManager.RunningTaskInfo[i];
            }
        };

        @java.lang.Deprecated
        public java.lang.CharSequence description;

        @java.lang.Deprecated
        public int id;

        @java.lang.Deprecated
        public int numRunning;

        @java.lang.Deprecated
        public android.graphics.Bitmap thumbnail;

        public RunningTaskInfo() {
        }

        private RunningTaskInfo(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.app.TaskInfo
        public void readFromParcel(android.os.Parcel parcel) {
            this.id = parcel.readInt();
            super.readFromParcel(parcel);
        }

        @Override // android.app.TaskInfo, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.id);
            super.writeToParcel(parcel, i);
        }
    }

    public java.util.List<android.app.ActivityManager.AppTask> getAppTasks() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            java.util.List<android.os.IBinder> appTasks = getTaskService().getAppTasks(this.mContext.getOpPackageName());
            int size = appTasks.size();
            for (int i = 0; i < size; i++) {
                arrayList.add(new android.app.ActivityManager.AppTask(android.app.IAppTask.Stub.asInterface(appTasks.get(i))));
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.util.Size getAppTaskThumbnailSize() {
        android.util.Size size;
        synchronized (this) {
            ensureAppTaskThumbnailSizeLocked();
            size = new android.util.Size(this.mAppTaskThumbnailSize.x, this.mAppTaskThumbnailSize.y);
        }
        return size;
    }

    private void ensureAppTaskThumbnailSizeLocked() {
        if (this.mAppTaskThumbnailSize == null) {
            try {
                this.mAppTaskThumbnailSize = getTaskService().getAppTaskThumbnailSize();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int addAppTask(android.app.Activity activity, android.content.Intent intent, android.app.ActivityManager.TaskDescription taskDescription, android.graphics.Bitmap bitmap) {
        android.graphics.Point point;
        float f;
        float f2;
        synchronized (this) {
            ensureAppTaskThumbnailSizeLocked();
            point = this.mAppTaskThumbnailSize;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width != point.x || height != point.y) {
            android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(point.x, point.y, bitmap.getConfig());
            if (point.x * width > point.y * height) {
                f = point.x / height;
                f2 = (point.y - (width * f)) * 0.5f;
            } else {
                f = point.y / width;
                int i = point.x;
                f2 = 0.0f;
            }
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            matrix.setScale(f, f);
            matrix.postTranslate((int) (f2 + 0.5f), 0.0f);
            android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
            canvas.drawBitmap(bitmap, matrix, null);
            canvas.setBitmap(null);
            bitmap = createBitmap;
        }
        if (taskDescription == null) {
            taskDescription = new android.app.ActivityManager.TaskDescription();
        }
        try {
            return getTaskService().addAppTask(activity.getActivityToken(), intent, taskDescription, bitmap);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.app.ActivityManager.RunningTaskInfo> getRunningTasks(int i) throws java.lang.SecurityException {
        return android.app.ActivityTaskManager.getInstance().getTasks(i);
    }

    public void moveTaskToFront(int i, int i2) {
        moveTaskToFront(i, i2, null);
    }

    public void moveTaskToFront(int i, int i2, android.os.Bundle bundle) {
        try {
            getTaskService().moveTaskToFront(android.app.ActivityThread.currentActivityThread().getApplicationThread(), this.mContext.getOpPackageName(), i, i2, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActivityStartAllowedOnDisplay(android.content.Context context, int i, android.content.Intent intent) {
        try {
            return getTaskService().isActivityStartAllowedOnDisplay(i, intent, intent.resolveTypeIfNeeded(context.getContentResolver()), context.getUserId());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public static class RunningServiceInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityManager.RunningServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityManager.RunningServiceInfo>() { // from class: android.app.ActivityManager.RunningServiceInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.RunningServiceInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityManager.RunningServiceInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.RunningServiceInfo[] newArray(int i) {
                return new android.app.ActivityManager.RunningServiceInfo[i];
            }
        };
        public static final int FLAG_FOREGROUND = 2;
        public static final int FLAG_PERSISTENT_PROCESS = 8;
        public static final int FLAG_STARTED = 1;
        public static final int FLAG_SYSTEM_PROCESS = 4;
        public long activeSince;
        public int clientCount;
        public int clientLabel;
        public java.lang.String clientPackage;
        public int crashCount;
        public int flags;
        public boolean foreground;
        public long lastActivityTime;
        public int pid;
        public java.lang.String process;
        public long restarting;
        public android.content.ComponentName service;
        public boolean started;
        public int uid;

        public RunningServiceInfo() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            android.content.ComponentName.writeToParcel(this.service, parcel);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeString(this.process);
            parcel.writeInt(this.foreground ? 1 : 0);
            parcel.writeLong(this.activeSince);
            parcel.writeInt(this.started ? 1 : 0);
            parcel.writeInt(this.clientCount);
            parcel.writeInt(this.crashCount);
            parcel.writeLong(this.lastActivityTime);
            parcel.writeLong(this.restarting);
            parcel.writeInt(this.flags);
            parcel.writeString(this.clientPackage);
            parcel.writeInt(this.clientLabel);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.service = android.content.ComponentName.readFromParcel(parcel);
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.process = parcel.readString();
            this.foreground = parcel.readInt() != 0;
            this.activeSince = parcel.readLong();
            this.started = parcel.readInt() != 0;
            this.clientCount = parcel.readInt();
            this.crashCount = parcel.readInt();
            this.lastActivityTime = parcel.readLong();
            this.restarting = parcel.readLong();
            this.flags = parcel.readInt();
            this.clientPackage = parcel.readString();
            this.clientLabel = parcel.readInt();
        }

        private RunningServiceInfo(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.app.ActivityManager.RunningServiceInfo> getRunningServices(int i) throws java.lang.SecurityException {
        try {
            return getService().getServices(i, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.PendingIntent getRunningServiceControlPanel(android.content.ComponentName componentName) throws java.lang.SecurityException {
        try {
            return getService().getRunningServiceControlPanel(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class MemoryInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityManager.MemoryInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityManager.MemoryInfo>() { // from class: android.app.ActivityManager.MemoryInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.MemoryInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityManager.MemoryInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.MemoryInfo[] newArray(int i) {
                return new android.app.ActivityManager.MemoryInfo[i];
            }
        };
        public long advertisedMem;
        public long availMem;
        public long foregroundAppThreshold;
        public long hiddenAppThreshold;
        public boolean lowMemory;
        public long secondaryServerThreshold;
        public long threshold;
        public long totalMem;
        public long visibleAppThreshold;

        public MemoryInfo() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.advertisedMem);
            parcel.writeLong(this.availMem);
            parcel.writeLong(this.totalMem);
            parcel.writeLong(this.threshold);
            parcel.writeInt(this.lowMemory ? 1 : 0);
            parcel.writeLong(this.hiddenAppThreshold);
            parcel.writeLong(this.secondaryServerThreshold);
            parcel.writeLong(this.visibleAppThreshold);
            parcel.writeLong(this.foregroundAppThreshold);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.advertisedMem = parcel.readLong();
            this.availMem = parcel.readLong();
            this.totalMem = parcel.readLong();
            this.threshold = parcel.readLong();
            this.lowMemory = parcel.readInt() != 0;
            this.hiddenAppThreshold = parcel.readLong();
            this.secondaryServerThreshold = parcel.readLong();
            this.visibleAppThreshold = parcel.readLong();
            this.foregroundAppThreshold = parcel.readLong();
        }

        private MemoryInfo(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    public void getMemoryInfo(android.app.ActivityManager.MemoryInfo memoryInfo) {
        try {
            getService().getMemoryInfo(memoryInfo);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearApplicationUserData(java.lang.String str, android.content.pm.IPackageDataObserver iPackageDataObserver) {
        try {
            return getService().clearApplicationUserData(str, false, iPackageDataObserver, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearApplicationUserData() {
        return clearApplicationUserData(this.mContext.getPackageName(), null);
    }

    @java.lang.Deprecated
    public android.content.pm.ParceledListSlice<android.app.GrantedUriPermission> getGrantedUriPermissions(java.lang.String str) {
        return ((android.app.UriGrantsManager) this.mContext.getSystemService(android.content.Context.URI_GRANTS_SERVICE)).getGrantedUriPermissions(str);
    }

    @java.lang.Deprecated
    public void clearGrantedUriPermissions(java.lang.String str) {
        ((android.app.UriGrantsManager) this.mContext.getSystemService(android.content.Context.URI_GRANTS_SERVICE)).clearGrantedUriPermissions(str);
    }

    public static class ProcessErrorStateInfo implements android.os.Parcelable {
        public static final int CRASHED = 1;
        public static final android.os.Parcelable.Creator<android.app.ActivityManager.ProcessErrorStateInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityManager.ProcessErrorStateInfo>() { // from class: android.app.ActivityManager.ProcessErrorStateInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.ProcessErrorStateInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityManager.ProcessErrorStateInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.ProcessErrorStateInfo[] newArray(int i) {
                return new android.app.ActivityManager.ProcessErrorStateInfo[i];
            }
        };
        public static final int NOT_RESPONDING = 2;
        public static final int NO_ERROR = 0;
        public int condition;
        public byte[] crashData;
        public java.lang.String longMsg;
        public int pid;
        public java.lang.String processName;
        public java.lang.String shortMsg;
        public java.lang.String stackTrace;
        public java.lang.String tag;
        public int uid;

        public ProcessErrorStateInfo() {
            this.crashData = null;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.condition);
            parcel.writeString(this.processName);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeString(this.tag);
            parcel.writeString(this.shortMsg);
            parcel.writeString(this.longMsg);
            parcel.writeString(this.stackTrace);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.condition = parcel.readInt();
            this.processName = parcel.readString();
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.tag = parcel.readString();
            this.shortMsg = parcel.readString();
            this.longMsg = parcel.readString();
            this.stackTrace = parcel.readString();
        }

        private ProcessErrorStateInfo(android.os.Parcel parcel) {
            this.crashData = null;
            readFromParcel(parcel);
        }
    }

    public java.util.List<android.app.ActivityManager.ProcessErrorStateInfo> getProcessesInErrorState() {
        try {
            return getService().getProcessesInErrorState();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class RunningAppProcessInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityManager.RunningAppProcessInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityManager.RunningAppProcessInfo>() { // from class: android.app.ActivityManager.RunningAppProcessInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.RunningAppProcessInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityManager.RunningAppProcessInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.RunningAppProcessInfo[] newArray(int i) {
                return new android.app.ActivityManager.RunningAppProcessInfo[i];
            }
        };
        public static final int FLAG_CANT_SAVE_STATE = 1;
        public static final int FLAG_HAS_ACTIVITIES = 4;
        public static final int FLAG_PERSISTENT = 2;
        public static final int IMPORTANCE_BACKGROUND = 400;
        public static final int IMPORTANCE_CACHED = 400;
        public static final int IMPORTANCE_CANT_SAVE_STATE = 350;
        public static final int IMPORTANCE_CANT_SAVE_STATE_PRE_26 = 170;

        @java.lang.Deprecated
        public static final int IMPORTANCE_EMPTY = 500;
        public static final int IMPORTANCE_FOREGROUND = 100;
        public static final int IMPORTANCE_FOREGROUND_SERVICE = 125;
        public static final int IMPORTANCE_GONE = 1000;
        public static final int IMPORTANCE_PERCEPTIBLE = 230;
        public static final int IMPORTANCE_PERCEPTIBLE_PRE_26 = 130;
        public static final int IMPORTANCE_SERVICE = 300;
        public static final int IMPORTANCE_TOP_SLEEPING = 325;

        @java.lang.Deprecated
        public static final int IMPORTANCE_TOP_SLEEPING_PRE_28 = 150;
        public static final int IMPORTANCE_VISIBLE = 200;
        public static final int REASON_PROVIDER_IN_USE = 1;
        public static final int REASON_SERVICE_IN_USE = 2;
        public static final int REASON_UNKNOWN = 0;
        public int flags;
        public int importance;
        public int importanceReasonCode;
        public android.content.ComponentName importanceReasonComponent;
        public int importanceReasonImportance;
        public int importanceReasonPid;
        public boolean isFocused;
        public long lastActivityTime;
        public int lastTrimLevel;
        public int lru;
        public int pid;
        public java.lang.String[] pkgDeps;
        public java.lang.String[] pkgList;
        public java.lang.String processName;
        public int processState;
        public int uid;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Importance {
        }

        public static int procStateToImportance(int i) {
            if (i == 20) {
                return 1000;
            }
            if (i >= 14) {
                return 400;
            }
            if (i == 13) {
                return 350;
            }
            if (i >= 12) {
                return 325;
            }
            if (i >= 10) {
                return 300;
            }
            if (i >= 8) {
                return 230;
            }
            if (i >= 6) {
                return 200;
            }
            if (i >= 4) {
                return 125;
            }
            return 100;
        }

        public static int procStateToImportanceForClient(int i, android.content.Context context) {
            return procStateToImportanceForTargetSdk(i, context.getApplicationInfo().targetSdkVersion);
        }

        public static int procStateToImportanceForTargetSdk(int i, int i2) {
            int procStateToImportance = procStateToImportance(i);
            if (i2 < 26) {
                switch (procStateToImportance) {
                    case 230:
                        return 130;
                    case 325:
                        return 150;
                    case 350:
                        return 170;
                }
            }
            return procStateToImportance;
        }

        public static int importanceToProcState(int i) {
            if (i == 1000) {
                return 20;
            }
            if (i >= 400) {
                return 14;
            }
            if (i >= 350) {
                return 13;
            }
            if (i >= 325) {
                return 12;
            }
            if (i >= 300) {
                return 10;
            }
            if (i >= 230) {
                return 8;
            }
            if (i >= 200 || i >= 150) {
                return 6;
            }
            if (i >= 125) {
                return 4;
            }
            return 2;
        }

        public RunningAppProcessInfo() {
            this.importance = 100;
            this.importanceReasonCode = 0;
            this.processState = 6;
            this.isFocused = false;
            this.lastActivityTime = 0L;
        }

        public RunningAppProcessInfo(java.lang.String str, int i, java.lang.String[] strArr) {
            this.processName = str;
            this.pid = i;
            this.pkgList = strArr;
            this.isFocused = false;
            this.lastActivityTime = 0L;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.processName);
            parcel.writeInt(this.pid);
            parcel.writeInt(this.uid);
            parcel.writeStringArray(this.pkgList);
            parcel.writeStringArray(this.pkgDeps);
            parcel.writeInt(this.flags);
            parcel.writeInt(this.lastTrimLevel);
            parcel.writeInt(this.importance);
            parcel.writeInt(this.lru);
            parcel.writeInt(this.importanceReasonCode);
            parcel.writeInt(this.importanceReasonPid);
            android.content.ComponentName.writeToParcel(this.importanceReasonComponent, parcel);
            parcel.writeInt(this.importanceReasonImportance);
            parcel.writeInt(this.processState);
            parcel.writeInt(this.isFocused ? 1 : 0);
            parcel.writeLong(this.lastActivityTime);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.processName = parcel.readString();
            this.pid = parcel.readInt();
            this.uid = parcel.readInt();
            this.pkgList = parcel.readStringArray();
            this.pkgDeps = parcel.readStringArray();
            this.flags = parcel.readInt();
            this.lastTrimLevel = parcel.readInt();
            this.importance = parcel.readInt();
            this.lru = parcel.readInt();
            this.importanceReasonCode = parcel.readInt();
            this.importanceReasonPid = parcel.readInt();
            this.importanceReasonComponent = android.content.ComponentName.readFromParcel(parcel);
            this.importanceReasonImportance = parcel.readInt();
            this.processState = parcel.readInt();
            this.isFocused = parcel.readInt() != 0;
            this.lastActivityTime = parcel.readLong();
        }

        private RunningAppProcessInfo(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }
    }

    public java.util.List<android.content.pm.ApplicationInfo> getRunningExternalApplications() {
        try {
            return getService().getRunningExternalApplications();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBackgroundRestricted() {
        try {
            return getService().isBackgroundRestricted(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setProcessMemoryTrimLevel(java.lang.String str, int i, int i2) {
        try {
            return getService().setProcessMemoryTrimLevel(str, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() {
        try {
            return getService().getRunningAppProcesses();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.ApplicationStartInfo> getHistoricalProcessStartReasons(int i) {
        try {
            android.content.pm.ParceledListSlice<android.app.ApplicationStartInfo> historicalProcessStartReasons = getService().getHistoricalProcessStartReasons(null, i, this.mContext.getUserId());
            return historicalProcessStartReasons == null ? java.util.Collections.emptyList() : historicalProcessStartReasons.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.app.ApplicationStartInfo> getExternalHistoricalProcessStartReasons(java.lang.String str, int i) {
        try {
            android.content.pm.ParceledListSlice<android.app.ApplicationStartInfo> historicalProcessStartReasons = getService().getHistoricalProcessStartReasons(str, i, this.mContext.getUserId());
            return historicalProcessStartReasons == null ? java.util.Collections.emptyList() : historicalProcessStartReasons.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AppStartInfoCallbackWrapper {
        final java.util.concurrent.Executor mExecutor;
        final java.util.function.Consumer<android.app.ApplicationStartInfo> mListener;

        AppStartInfoCallbackWrapper(java.util.concurrent.Executor executor, java.util.function.Consumer<android.app.ApplicationStartInfo> consumer) {
            this.mExecutor = executor;
            this.mListener = consumer;
        }
    }

    public void addApplicationStartInfoCompletionListener(java.util.concurrent.Executor executor, java.util.function.Consumer<android.app.ApplicationStartInfo> consumer) {
        com.android.internal.util.Preconditions.checkNotNull(executor, "executor cannot be null");
        com.android.internal.util.Preconditions.checkNotNull(consumer, "listener cannot be null");
        synchronized (this.mAppStartInfoCallbacks) {
            for (int i = 0; i < this.mAppStartInfoCallbacks.size(); i++) {
                if (consumer.equals(this.mAppStartInfoCallbacks.get(i).mListener)) {
                    return;
                }
            }
            if (this.mAppStartInfoCompleteListener == null) {
                this.mAppStartInfoCompleteListener = new android.app.ActivityManager.AnonymousClass2();
                try {
                    getService().addApplicationStartInfoCompleteListener(this.mAppStartInfoCompleteListener, this.mContext.getUserId());
                    this.mAppStartInfoCallbacks.add(new android.app.ActivityManager.AppStartInfoCallbackWrapper(executor, consumer));
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } else {
                this.mAppStartInfoCallbacks.add(new android.app.ActivityManager.AppStartInfoCallbackWrapper(executor, consumer));
            }
        }
    }

    /* renamed from: android.app.ActivityManager$2, reason: invalid class name */
    class AnonymousClass2 extends android.app.IApplicationStartInfoCompleteListener.Stub {
        AnonymousClass2() {
        }

        @Override // android.app.IApplicationStartInfoCompleteListener
        public void onApplicationStartInfoComplete(final android.app.ApplicationStartInfo applicationStartInfo) {
            synchronized (android.app.ActivityManager.this.mAppStartInfoCallbacks) {
                for (int i = 0; i < android.app.ActivityManager.this.mAppStartInfoCallbacks.size(); i++) {
                    final android.app.ActivityManager.AppStartInfoCallbackWrapper appStartInfoCallbackWrapper = (android.app.ActivityManager.AppStartInfoCallbackWrapper) android.app.ActivityManager.this.mAppStartInfoCallbacks.get(i);
                    appStartInfoCallbackWrapper.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.ActivityManager$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ActivityManager.AppStartInfoCallbackWrapper.this.mListener.accept(applicationStartInfo);
                        }
                    });
                }
                android.app.ActivityManager.this.mAppStartInfoCallbacks.clear();
                android.app.ActivityManager.this.mAppStartInfoCompleteListener = null;
            }
        }
    }

    public void removeApplicationStartInfoCompletionListener(java.util.function.Consumer<android.app.ApplicationStartInfo> consumer) {
        com.android.internal.util.Preconditions.checkNotNull(consumer, "listener cannot be null");
        synchronized (this.mAppStartInfoCallbacks) {
            int i = 0;
            while (true) {
                if (i >= this.mAppStartInfoCallbacks.size()) {
                    break;
                }
                if (!consumer.equals(this.mAppStartInfoCallbacks.get(i).mListener)) {
                    i++;
                } else {
                    this.mAppStartInfoCallbacks.remove(i);
                    break;
                }
            }
            if (this.mAppStartInfoCompleteListener != null && this.mAppStartInfoCallbacks.isEmpty()) {
                try {
                    getService().removeApplicationStartInfoCompleteListener(this.mAppStartInfoCompleteListener, this.mContext.getUserId());
                    this.mAppStartInfoCompleteListener = null;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void addStartInfoTimestamp(int i, long j) {
        if (i <= 20 || i > 30) {
            throw new java.lang.IllegalArgumentException("Key not in allowed range.");
        }
        try {
            getService().addStartInfoTimestamp(i, j, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.ApplicationExitInfo> getHistoricalProcessExitReasons(java.lang.String str, int i, int i2) {
        try {
            android.content.pm.ParceledListSlice<android.app.ApplicationExitInfo> historicalProcessExitReasons = getService().getHistoricalProcessExitReasons(str, i, i2, this.mContext.getUserId());
            return historicalProcessExitReasons == null ? java.util.Collections.emptyList() : historicalProcessExitReasons.getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setProcessStateSummary(byte[] bArr) {
        try {
            getService().setProcessStateSummary(bArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isLowMemoryKillReportSupported() {
        return android.os.SystemProperties.getBoolean("persist.sys.lmk.reportkills", false);
    }

    public int getUidProcessState(int i) {
        try {
            return getService().getUidProcessState(i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUidProcessCapabilities(int i) {
        try {
            return getService().getUidProcessCapabilities(i, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getPackageImportance(java.lang.String str) {
        try {
            return android.app.ActivityManager.RunningAppProcessInfo.procStateToImportanceForClient(getService().getPackageProcessState(str, this.mContext.getOpPackageName()), this.mContext);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getUidImportance(int i) {
        try {
            return android.app.ActivityManager.RunningAppProcessInfo.procStateToImportanceForClient(getService().getUidProcessState(i, this.mContext.getOpPackageName()), this.mContext);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getBindingUidImportance(int i) {
        try {
            return android.app.ActivityManager.RunningAppProcessInfo.procStateToImportanceForClient(getService().getBindingUidProcessState(i, this.mContext.getOpPackageName()), this.mContext);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void addOnUidImportanceListener(android.app.ActivityManager.OnUidImportanceListener onUidImportanceListener, int i) {
        addOnUidImportanceListenerInternal(onUidImportanceListener, i, null);
    }

    @android.annotation.SystemApi
    public void addOnUidImportanceListener(android.app.ActivityManager.OnUidImportanceListener onUidImportanceListener, int i, int[] iArr) {
        java.util.Objects.requireNonNull(onUidImportanceListener);
        java.util.Objects.requireNonNull(iArr);
        addOnUidImportanceListenerInternal(onUidImportanceListener, i, iArr);
    }

    private void addOnUidImportanceListenerInternal(android.app.ActivityManager.OnUidImportanceListener onUidImportanceListener, int i, int[] iArr) {
        synchronized (this.mImportanceListeners) {
            if (this.mImportanceListeners.containsKey(onUidImportanceListener)) {
                throw new java.lang.IllegalArgumentException("Listener already registered: " + onUidImportanceListener);
            }
            android.app.ActivityManager.MyUidObserver myUidObserver = new android.app.ActivityManager.MyUidObserver(onUidImportanceListener, this.mContext);
            try {
                getService().registerUidObserverForUids(myUidObserver, 3, android.app.ActivityManager.RunningAppProcessInfo.importanceToProcState(i), this.mContext.getOpPackageName(), iArr);
                this.mImportanceListeners.put(onUidImportanceListener, myUidObserver);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public void removeOnUidImportanceListener(android.app.ActivityManager.OnUidImportanceListener onUidImportanceListener) {
        synchronized (this.mImportanceListeners) {
            android.app.ActivityManager.MyUidObserver remove = this.mImportanceListeners.remove(onUidImportanceListener);
            if (remove == null) {
                throw new java.lang.IllegalArgumentException("Listener not registered: " + onUidImportanceListener);
            }
            try {
                getService().unregisterUidObserver(remove);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public static void getMyMemoryState(android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo) {
        try {
            getService().getMyMemoryState(runningAppProcessInfo);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Debug.MemoryInfo[] getProcessMemoryInfo(int[] iArr) {
        try {
            return getService().getProcessMemoryInfo(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void restartPackage(java.lang.String str) {
        killBackgroundProcesses(str);
    }

    public void killBackgroundProcesses(java.lang.String str) {
        try {
            getService().killBackgroundProcesses(str, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void killUid(int i, java.lang.String str) {
        try {
            getService().killUid(android.os.UserHandle.getAppId(i), android.os.UserHandle.getUserId(i), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceStopPackageAsUser(java.lang.String str, int i) {
        try {
            getService().forceStopPackage(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void forceStopPackage(java.lang.String str) {
        forceStopPackageAsUser(str, this.mContext.getUserId());
    }

    public void forceStopPackageAsUserEvenWhenStopping(java.lang.String str, int i) {
        try {
            getService().forceStopPackageEvenWhenStopping(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setDeviceLocales(android.os.LocaleList localeList) {
        com.android.internal.app.LocalePicker.updateLocales(localeList);
    }

    @android.annotation.SystemApi
    public java.util.Collection<java.util.Locale> getSupportedLocales() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : com.android.internal.app.LocalePicker.getSupportedLocales(this.mContext)) {
            arrayList.add(java.util.Locale.forLanguageTag(str));
        }
        return arrayList;
    }

    public android.content.pm.ConfigurationInfo getDeviceConfigurationInfo() {
        try {
            return getTaskService().getDeviceConfigurationInfo();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLauncherLargeIconDensity() {
        android.content.res.Resources resources = this.mContext.getResources();
        int i = resources.getDisplayMetrics().densityDpi;
        if (resources.getConfiguration().smallestScreenWidthDp < 600) {
            return i;
        }
        switch (i) {
            case 120:
                return 160;
            case 160:
                return 240;
            case 213:
                return 320;
            case 240:
                return 320;
            case 320:
                return 480;
            case 480:
                return 640;
            default:
                return (int) ((i * 1.5f) + 0.5f);
        }
    }

    public int getLauncherLargeIconSize() {
        return getLauncherLargeIconSizeInner(this.mContext);
    }

    static int getLauncherLargeIconSizeInner(android.content.Context context) {
        android.content.res.Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(17104896);
        if (resources.getConfiguration().smallestScreenWidthDp < 600) {
            return dimensionPixelSize;
        }
        switch (resources.getDisplayMetrics().densityDpi) {
            case 120:
                return (dimensionPixelSize * 160) / 120;
            case 160:
                return (dimensionPixelSize * 240) / 160;
            case 213:
                return (dimensionPixelSize * 320) / 240;
            case 240:
                return (dimensionPixelSize * 320) / 240;
            case 320:
                return (dimensionPixelSize * 480) / 320;
            case 480:
                return ((dimensionPixelSize * 320) * 2) / 480;
            default:
                return (int) ((dimensionPixelSize * 1.5f) + 0.5f);
        }
    }

    public static boolean isUserAMonkey() {
        try {
            return getService().isUserAMonkey();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isUserAMonkey$ravenwood() {
        return false;
    }

    @java.lang.Deprecated
    public static boolean isRunningInTestHarness() {
        return android.os.SystemProperties.getBoolean("ro.test_harness", false);
    }

    public static boolean isRunningInUserTestHarness() {
        return android.os.SystemProperties.getBoolean("persist.sys.test_harness", false);
    }

    public void alwaysShowUnsupportedCompileSdkWarning(android.content.ComponentName componentName) {
        try {
            getTaskService().alwaysShowUnsupportedCompileSdkWarning(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean canAccessUnexportedComponents(int i) {
        int appId = android.os.UserHandle.getAppId(i);
        return appId == 0 || appId == 1000;
    }

    public static int checkComponentPermission(java.lang.String str, int i, int i2, boolean z) {
        return checkComponentPermission(str, i, 0, i2, z);
    }

    public static int checkComponentPermission(java.lang.String str, int i, int i2, int i3, boolean z) {
        if (canAccessUnexportedComponents(i)) {
            return 0;
        }
        if (android.os.UserHandle.isIsolated(i)) {
            return -1;
        }
        if (i3 >= 0 && android.os.UserHandle.isSameApp(i, i3)) {
            return 0;
        }
        if (!z) {
            return -1;
        }
        if (str == null) {
            return 0;
        }
        try {
            return android.app.AppGlobals.getPermissionManager().checkUidPermission(i, str, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int checkUidPermission(java.lang.String str, int i) {
        try {
            return android.app.AppGlobals.getPermissionManager().checkUidPermission(i, str, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int handleIncomingUser(int i, int i2, int i3, boolean z, boolean z2, java.lang.String str, java.lang.String str2) {
        if (android.os.UserHandle.getUserId(i2) == i3) {
            return i3;
        }
        try {
            return getService().handleIncomingUser(i, i2, i3, z, z2, str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static int getCurrentUser() {
        try {
            return getService().getCurrentUserId();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean switchUser(int i) {
        try {
            return getService().switchUser(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean switchUser(android.os.UserHandle userHandle) {
        com.android.internal.util.Preconditions.checkArgument(userHandle != null, "UserHandle cannot be null.");
        return switchUser(userHandle.getIdentifier());
    }

    public boolean startUserInBackgroundVisibleOnDisplay(int i, int i2) {
        if (!android.os.UserManager.isVisibleBackgroundUsersEnabled()) {
            throw new java.lang.UnsupportedOperationException("device does not support users on secondary displays");
        }
        try {
            return getService().startUserInBackgroundVisibleOnDisplay(i, i2, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getDisplayIdsForStartingVisibleBackgroundUsers() {
        try {
            return getService().getDisplayIdsForStartingVisibleBackgroundUsers();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getSwitchingFromUserMessage() {
        try {
            return getService().getSwitchingFromUserMessage();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getSwitchingToUserMessage() {
        try {
            return getService().getSwitchingToUserMessage();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setStopUserOnSwitch(int i) {
        try {
            getService().setStopUserOnSwitch(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean startProfile(android.os.UserHandle userHandle) {
        try {
            return getService().startProfile(userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean stopProfile(android.os.UserHandle userHandle) {
        try {
            return getService().stopProfile(userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public boolean updateMccMncConfiguration(java.lang.String str, java.lang.String str2) {
        if (str == null || str2 == null) {
            throw new java.lang.IllegalArgumentException("mcc or mnc cannot be null.");
        }
        try {
            return getService().updateMccMncConfiguration(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean stopUser(int i, boolean z) {
        if (i == 0) {
            return false;
        }
        try {
            return getService().stopUser(i, z, null) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isUserRunning(int i) {
        try {
            return getService().isUserRunning(i, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVrModePackageEnabled(android.content.ComponentName componentName) {
        try {
            return getService().isVrModePackageEnabled(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void dumpPackageState(java.io.FileDescriptor fileDescriptor, java.lang.String str) {
        dumpPackageStateStatic(fileDescriptor, str);
    }

    public static void dumpPackageStateStatic(java.io.FileDescriptor fileDescriptor, java.lang.String str) {
        com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(fileDescriptor));
        dumpService(fastPrintWriter, fileDescriptor, "package", new java.lang.String[]{str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, "activity", new java.lang.String[]{"-a", "package", str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, "meminfo", new java.lang.String[]{"--local", "--package", str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, com.android.internal.app.procstats.ProcessStats.SERVICE_NAME, new java.lang.String[]{str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, android.content.Context.USAGE_STATS_SERVICE, new java.lang.String[]{str});
        fastPrintWriter.println();
        dumpService(fastPrintWriter, fileDescriptor, "batterystats", new java.lang.String[]{str});
        fastPrintWriter.flush();
    }

    public static boolean isSystemReady() {
        if (!sSystemReady) {
            if (android.app.ActivityThread.isSystem()) {
                sSystemReady = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).isSystemReady();
            } else {
                sSystemReady = true;
            }
        }
        return sSystemReady;
    }

    public static boolean isSystemReady$ravenwood() {
        return true;
    }

    public static void broadcastStickyIntent(android.content.Intent intent, int i) {
        broadcastStickyIntent(intent, -1, null, i);
    }

    public static void broadcastStickyIntent(android.content.Intent intent, int i, int i2) {
        broadcastStickyIntent(intent, i, null, i2);
    }

    public static void broadcastStickyIntent(android.content.Intent intent, int i, android.os.Bundle bundle, int i2) {
        broadcastStickyIntent(intent, null, i, bundle, i2);
    }

    public static void broadcastStickyIntent(android.content.Intent intent, java.lang.String[] strArr, int i, android.os.Bundle bundle, int i2) {
        try {
            getService().broadcastIntentWithFeature(null, null, intent, null, null, -1, null, null, null, null, strArr, i, bundle, false, true, i2);
        } catch (android.os.RemoteException e) {
        }
    }

    public static void resumeAppSwitches() throws android.os.RemoteException {
        getService().resumeAppSwitches();
    }

    public static void noteWakeupAlarm(android.app.PendingIntent pendingIntent, android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2) {
        try {
            getService().noteWakeupAlarm(pendingIntent != null ? pendingIntent.getTarget() : null, workSource, i, str, str2);
        } catch (android.os.RemoteException e) {
        }
    }

    public static void noteAlarmStart(android.app.PendingIntent pendingIntent, android.os.WorkSource workSource, int i, java.lang.String str) {
        try {
            getService().noteAlarmStart(pendingIntent != null ? pendingIntent.getTarget() : null, workSource, i, str);
        } catch (android.os.RemoteException e) {
        }
    }

    public static void noteAlarmFinish(android.app.PendingIntent pendingIntent, android.os.WorkSource workSource, int i, java.lang.String str) {
        try {
            getService().noteAlarmFinish(pendingIntent != null ? pendingIntent.getTarget() : null, workSource, i, str);
        } catch (android.os.RemoteException e) {
        }
    }

    public static android.app.IActivityManager getService() {
        return IActivityManagerSingleton.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.IActivityTaskManager getTaskService() {
        return android.app.ActivityTaskManager.getService();
    }

    private static void dumpService(java.io.PrintWriter printWriter, java.io.FileDescriptor fileDescriptor, java.lang.String str, java.lang.String[] strArr) {
        com.android.internal.os.TransferPipe transferPipe;
        printWriter.print("DUMP OF SERVICE ");
        printWriter.print(str);
        printWriter.println(":");
        android.os.IBinder checkService = android.os.ServiceManager.checkService(str);
        if (checkService == null) {
            printWriter.println("  (Service not found)");
            printWriter.flush();
            return;
        }
        printWriter.flush();
        if (checkService instanceof android.os.Binder) {
            try {
                checkService.dump(fileDescriptor, strArr);
                return;
            } catch (java.lang.Throwable th) {
                printWriter.println("Failure dumping service:");
                th.printStackTrace(printWriter);
                printWriter.flush();
                return;
            }
        }
        com.android.internal.os.TransferPipe transferPipe2 = null;
        try {
            printWriter.flush();
            transferPipe = new com.android.internal.os.TransferPipe();
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
        try {
            transferPipe.setBufferPrefix("  ");
            checkService.dumpAsync(transferPipe.getWriteFd().getFileDescriptor(), strArr);
            transferPipe.go(fileDescriptor, android.app.job.JobInfo.MIN_BACKOFF_MILLIS);
        } catch (java.lang.Throwable th3) {
            th = th3;
            transferPipe2 = transferPipe;
            if (transferPipe2 != null) {
                transferPipe2.kill();
            }
            printWriter.println("Failure dumping service:");
            th.printStackTrace(printWriter);
        }
    }

    public void setWatchHeapLimit(long j) {
        try {
            getService().setDumpHeapDebugLimit(null, 0, j, this.mContext.getPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearWatchHeapLimit() {
        try {
            getService().setDumpHeapDebugLimit(null, 0, 0L, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean isInLockTaskMode() {
        return getLockTaskModeState() != 0;
    }

    public int getLockTaskModeState() {
        try {
            return getTaskService().getLockTaskModeState();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setVrThread(int i) {
        try {
            getTaskService().setVrThread(i);
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi
    public static void setPersistentVrThread(int i) {
        try {
            getService().setPersistentVrThread(i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void scheduleApplicationInfoChanged(java.util.List<java.lang.String> list, int i) {
        try {
            getService().scheduleApplicationInfoChanged(list, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProfileForeground(android.os.UserHandle userHandle) {
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        if (userManager != null) {
            java.util.Iterator<android.content.pm.UserInfo> it = userManager.getProfiles(getCurrentUser()).iterator();
            while (it.hasNext()) {
                if (it.next().id == userHandle.getIdentifier()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @android.annotation.SystemApi
    public void killProcessesWhenImperceptible(int[] iArr, java.lang.String str) {
        try {
            getService().killProcessesWhenImperceptible(iArr, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isProcStateConsideredInteraction(int i) {
        return i <= 2 || i == 3;
    }

    public static java.lang.String procStateToString(int i) {
        switch (i) {
            case 0:
                return "PER ";
            case 1:
                return "PERU";
            case 2:
                return "TOP ";
            case 3:
                return "BTOP";
            case 4:
                return "FGS ";
            case 5:
                return "BFGS";
            case 6:
                return "IMPF";
            case 7:
                return "IMPB";
            case 8:
                return "TRNB";
            case 9:
                return "BKUP";
            case 10:
                return "SVC ";
            case 11:
                return "RCVR";
            case 12:
                return "TPSL";
            case 13:
                return "HVY ";
            case 14:
                return "HOME";
            case 15:
                return "LAST";
            case 16:
                return "CAC ";
            case 17:
                return "CACC";
            case 18:
                return "CRE ";
            case 19:
                return "CEM ";
            case 20:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            default:
                return "??";
        }
    }

    public static class AppTask {
        private android.app.IAppTask mAppTaskImpl;

        public AppTask(android.app.IAppTask iAppTask) {
            this.mAppTaskImpl = iAppTask;
        }

        public void finishAndRemoveTask() {
            try {
                this.mAppTaskImpl.finishAndRemoveTask();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public android.app.ActivityManager.RecentTaskInfo getTaskInfo() {
            try {
                return this.mAppTaskImpl.getTaskInfo();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void moveToFront() {
            try {
                this.mAppTaskImpl.moveToFront(android.app.ActivityThread.currentActivityThread().getApplicationThread(), android.app.ActivityThread.currentPackageName());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void startActivity(android.content.Context context, android.content.Intent intent, android.os.Bundle bundle) {
            android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
            currentActivityThread.getInstrumentation().execStartActivityFromAppTask(context, currentActivityThread.getApplicationThread(), this.mAppTaskImpl, intent, bundle);
        }

        public void setExcludeFromRecents(boolean z) {
            try {
                this.mAppTaskImpl.setExcludeFromRecents(z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public java.util.List<java.lang.String> getBugreportWhitelistedPackages() {
        try {
            return getService().getBugreportWhitelistedPackages();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void appNotResponding(java.lang.String str) {
        try {
            getService().appNotResponding(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void addHomeVisibilityListener(java.util.concurrent.Executor executor, final android.app.HomeVisibilityListener homeVisibilityListener) {
        com.android.internal.util.Preconditions.checkNotNull(homeVisibilityListener);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        try {
            homeVisibilityListener.init(this.mContext, executor);
            getService().registerProcessObserver(homeVisibilityListener.mObserver);
            executor.execute(new java.lang.Runnable() { // from class: android.app.ActivityManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    r0.onHomeVisibilityChanged(android.app.HomeVisibilityListener.this.mIsHomeActivityVisible);
                }
            });
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void removeHomeVisibilityListener(android.app.HomeVisibilityListener homeVisibilityListener) {
        com.android.internal.util.Preconditions.checkNotNull(homeVisibilityListener);
        try {
            getService().unregisterProcessObserver(homeVisibilityListener.mObserver);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setThemeOverlayReady(int i) {
        try {
            getService().setThemeOverlayReady(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetAppErrors() {
        try {
            getService().resetAppErrors();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void holdLock(android.os.IBinder iBinder, int i) {
        try {
            getService().holdLock(iBinder, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void waitForBroadcastIdle() {
        try {
            getService().waitForBroadcastIdle();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void forceDelayBroadcastDelivery(java.lang.String str, long j) {
        try {
            getService().forceDelayBroadcastDelivery(str, j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isModernBroadcastQueueEnabled() {
        try {
            return getService().isModernBroadcastQueueEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isProcessFrozen(int i) {
        try {
            return getService().isProcessFrozen(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void noteForegroundResourceUseBegin(int i, int i2, int i3) throws java.lang.SecurityException {
        try {
            getService().logFgsApiBegin(i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void noteForegroundResourceUseEnd(int i, int i2, int i3) throws java.lang.SecurityException {
        try {
            getService().logFgsApiEnd(i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getBackgroundRestrictionExemptionReason(int i) {
        try {
            return getService().getBackgroundRestrictionExemptionReason(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    public void notifySystemPropertiesChanged() {
        android.os.IBinder asBinder = getService().asBinder();
        if (asBinder != null) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                asBinder.transact(android.os.IBinder.SYSPROPS_TRANSACTION, obtain, null, 0);
                obtain.recycle();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public static final class PendingIntentInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityManager.PendingIntentInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityManager.PendingIntentInfo>() { // from class: android.app.ActivityManager.PendingIntentInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.PendingIntentInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityManager.PendingIntentInfo(parcel.readString(), parcel.readInt(), parcel.readBoolean(), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityManager.PendingIntentInfo[] newArray(int i) {
                return new android.app.ActivityManager.PendingIntentInfo[i];
            }
        };
        private final java.lang.String mCreatorPackage;
        private final int mCreatorUid;
        private final boolean mImmutable;
        private final int mIntentSenderType;

        public PendingIntentInfo(java.lang.String str, int i, boolean z, int i2) {
            this.mCreatorPackage = str;
            this.mCreatorUid = i;
            this.mImmutable = z;
            this.mIntentSenderType = i2;
        }

        public java.lang.String getCreatorPackage() {
            return this.mCreatorPackage;
        }

        public int getCreatorUid() {
            return this.mCreatorUid;
        }

        public boolean isImmutable() {
            return this.mImmutable;
        }

        public int getIntentSenderType() {
            return this.mIntentSenderType;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mCreatorPackage);
            parcel.writeInt(this.mCreatorUid);
            parcel.writeBoolean(this.mImmutable);
            parcel.writeInt(this.mIntentSenderType);
        }
    }
}
