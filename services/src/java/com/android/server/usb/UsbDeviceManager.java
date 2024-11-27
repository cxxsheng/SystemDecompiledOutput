package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbDeviceManager implements com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver {
    private static final int ACCESSORY_HANDSHAKE_TIMEOUT = 10000;
    private static final int ACCESSORY_REQUEST_TIMEOUT = 10000;
    private static final java.lang.String ACCESSORY_START_MATCH = "DEVPATH=/devices/virtual/misc/usb_accessory";
    private static final java.lang.String ADB_NOTIFICATION_CHANNEL_ID_TV = "usbdevicemanager.adb.tv";
    private static final int AUDIO_MODE_SOURCE = 1;
    private static final java.lang.String AUDIO_SOURCE_PCM_PATH = "/sys/class/android_usb/android0/f_audio_source/pcm";
    private static final java.lang.String BOOT_MODE_PROPERTY = "ro.bootmode";
    private static final boolean DEBUG = false;
    private static final int DEVICE_STATE_UPDATE_DELAY = 1000;
    private static final int DEVICE_STATE_UPDATE_DELAY_EXT = 3000;
    private static final int DUMPSYS_LOG_BUFFER = 200;
    private static final java.lang.String FUNCTIONS_PATH = "/sys/class/android_usb/android0/functions";
    private static final int HOST_STATE_UPDATE_DELAY = 1000;
    private static final java.lang.String MIDI_ALSA_PATH = "/sys/class/android_usb/android0/f_midi/alsa";
    private static final int MSG_ACCESSORY_HANDSHAKE_TIMEOUT = 20;
    private static final int MSG_ACCESSORY_MODE_ENTER_TIMEOUT = 8;
    private static final int MSG_BOOT_COMPLETED = 4;
    private static final int MSG_ENABLE_ADB = 1;
    private static final int MSG_FUNCTION_SWITCH_TIMEOUT = 17;
    private static final int MSG_GADGET_HAL_REGISTERED = 18;
    private static final int MSG_GET_CURRENT_USB_FUNCTIONS = 16;
    private static final int MSG_INCREASE_SENDSTRING_COUNT = 21;
    private static final int MSG_LOCALE_CHANGED = 11;
    private static final int MSG_RESET_USB_GADGET = 19;
    private static final int MSG_SET_CHARGING_FUNCTIONS = 14;
    private static final int MSG_SET_CURRENT_FUNCTIONS = 2;
    private static final int MSG_SET_FUNCTIONS_TIMEOUT = 15;
    private static final int MSG_SET_SCREEN_UNLOCKED_FUNCTIONS = 12;
    private static final int MSG_SYSTEM_READY = 3;
    private static final int MSG_UPDATE_CHARGING_STATE = 9;
    private static final int MSG_UPDATE_HAL_VERSION = 23;
    private static final int MSG_UPDATE_HOST_STATE = 10;
    private static final int MSG_UPDATE_PORT_STATE = 7;
    private static final int MSG_UPDATE_SCREEN_LOCK = 13;
    private static final int MSG_UPDATE_STATE = 0;
    private static final int MSG_UPDATE_USB_SPEED = 22;
    private static final int MSG_UPDATE_USER_RESTRICTIONS = 6;
    private static final int MSG_USER_SWITCHED = 5;
    private static final java.lang.String NORMAL_BOOT = "normal";
    private static final java.lang.String RNDIS_ETH_ADDR_PATH = "/sys/class/android_usb/android0/f_rndis/ethaddr";
    private static final java.lang.String STATE_PATH = "/sys/class/android_usb/android0/state";
    static final java.lang.String UNLOCKED_CONFIG_PREF = "usb-screen-unlocked-config-%d";
    private static final java.lang.String USB_PREFS_XML = "UsbDeviceManagerPrefs.xml";
    private static final java.lang.String USB_STATE_MATCH = "DEVPATH=/devices/virtual/android_usb/android0";
    private static com.android.server.usb.hal.gadget.UsbGadgetHal mUsbGadgetHal;
    private static com.android.server.utils.EventLogger sEventLogger;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String[] mAccessoryStrings;
    private final android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;
    private java.util.HashMap<java.lang.Long, java.io.FileDescriptor> mControlFds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.usb.UsbProfileGroupSettingsManager mCurrentSettings;
    private com.android.server.usb.UsbDeviceManager.UsbHandler mHandler;
    private final boolean mHasUsbAccessory;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.os.UEventObserver mUEventObserver;
    private static final java.lang.String TAG = com.android.server.usb.UsbDeviceManager.class.getSimpleName();
    private static final java.util.concurrent.atomic.AtomicInteger sUsbOperationCount = new java.util.concurrent.atomic.AtomicInteger();
    private static java.util.Set<java.lang.Integer> sDenyInterfaces = new java.util.HashSet();

    private native java.lang.String[] nativeGetAccessoryStrings();

    private native int nativeGetAudioMode();

    private native boolean nativeIsStartRequested();

    private native android.os.ParcelFileDescriptor nativeOpenAccessory();

    private native java.io.FileDescriptor nativeOpenControl(java.lang.String str);

    static {
        sDenyInterfaces.add(1);
        sDenyInterfaces.add(2);
        sDenyInterfaces.add(3);
        sDenyInterfaces.add(7);
        sDenyInterfaces.add(8);
        sDenyInterfaces.add(9);
        sDenyInterfaces.add(10);
        sDenyInterfaces.add(11);
        sDenyInterfaces.add(13);
        sDenyInterfaces.add(14);
        sDenyInterfaces.add(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbDescriptor.CLASSID_WIRELESS));
    }

    private final class UsbUEventObserver extends android.os.UEventObserver {
        private UsbUEventObserver() {
        }

        public void onUEvent(android.os.UEventObserver.UEvent uEvent) {
            if (com.android.server.usb.UsbDeviceManager.sEventLogger != null) {
                com.android.server.usb.UsbDeviceManager.sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("USB UEVENT: " + uEvent.toString()));
            }
            java.lang.String str = uEvent.get("USB_STATE");
            java.lang.String str2 = uEvent.get("ACCESSORY");
            if (str != null) {
                com.android.server.usb.UsbDeviceManager.this.mHandler.updateState(str);
                return;
            }
            if ("GETPROTOCOL".equals(str2)) {
                com.android.server.usb.UsbDeviceManager.this.mHandler.setAccessoryUEventTime(android.os.SystemClock.elapsedRealtime());
                com.android.server.usb.UsbDeviceManager.this.resetAccessoryHandshakeTimeoutHandler();
            } else if ("SENDSTRING".equals(str2)) {
                com.android.server.usb.UsbDeviceManager.this.mHandler.sendEmptyMessage(21);
                com.android.server.usb.UsbDeviceManager.this.resetAccessoryHandshakeTimeoutHandler();
            } else if ("START".equals(str2)) {
                com.android.server.usb.UsbDeviceManager.this.mHandler.removeMessages(20);
                com.android.server.usb.UsbDeviceManager.this.mHandler.setStartAccessoryTrue();
                com.android.server.usb.UsbDeviceManager.this.startAccessoryMode();
            }
        }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onKeyguardStateChanged(boolean z) {
        boolean isDeviceSecure = ((android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class)).isDeviceSecure(android.app.ActivityManager.getCurrentUser());
        this.mHandler.removeMessages(13);
        this.mHandler.sendMessageDelayed(13, z, isDeviceSecure, 250L);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onAwakeStateChanged(boolean z) {
    }

    public void onUnlockUser(int i) {
        onKeyguardStateChanged(false);
    }

    public UsbDeviceManager(android.content.Context context, com.android.server.usb.UsbAlsaManager usbAlsaManager, com.android.server.usb.UsbSettingsManager usbSettingsManager, com.android.server.usb.UsbPermissionManager usbPermissionManager) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mHasUsbAccessory = this.mContext.getPackageManager().hasSystemFeature("android.hardware.usb.accessory");
        initRndisAddress();
        int incrementAndGet = sUsbOperationCount.incrementAndGet();
        mUsbGadgetHal = com.android.server.usb.hal.gadget.UsbGadgetHalInstance.getInstance(this, null);
        android.util.Slog.d(TAG, "getInstance done");
        this.mControlFds = new java.util.HashMap<>();
        java.io.FileDescriptor nativeOpenControl = nativeOpenControl("mtp");
        if (nativeOpenControl == null) {
            android.util.Slog.e(TAG, "Failed to open control for mtp");
        }
        this.mControlFds.put(4L, nativeOpenControl);
        java.io.FileDescriptor nativeOpenControl2 = nativeOpenControl("ptp");
        if (nativeOpenControl2 == null) {
            android.util.Slog.e(TAG, "Failed to open control for ptp");
        }
        this.mControlFds.put(16L, nativeOpenControl2);
        if (mUsbGadgetHal == null) {
            this.mHandler = new com.android.server.usb.UsbDeviceManager.UsbHandlerLegacy(com.android.server.FgThread.get().getLooper(), this.mContext, this, usbAlsaManager, usbPermissionManager);
        } else {
            this.mHandler = new com.android.server.usb.UsbDeviceManager.UsbHandlerHal(com.android.server.FgThread.get().getLooper(), this.mContext, this, usbAlsaManager, usbPermissionManager);
        }
        this.mHandler.handlerInitDone(incrementAndGet);
        if (nativeIsStartRequested()) {
            startAccessoryMode();
        }
        android.content.BroadcastReceiver broadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                android.hardware.usb.ParcelableUsbPort parcelableUsbPort = (android.hardware.usb.ParcelableUsbPort) intent.getParcelableExtra("port", android.hardware.usb.ParcelableUsbPort.class);
                com.android.server.usb.UsbDeviceManager.this.mHandler.updateHostState(parcelableUsbPort.getUsbPort((android.hardware.usb.UsbManager) context2.getSystemService(android.hardware.usb.UsbManager.class)), (android.hardware.usb.UsbPortStatus) intent.getParcelableExtra("portStatus", android.hardware.usb.UsbPortStatus.class));
            }
        };
        android.content.BroadcastReceiver broadcastReceiver2 = new android.content.BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.usb.UsbDeviceManager.this.mHandler.sendMessage(9, intent.getIntExtra("plugged", -1) == 2);
            }
        };
        android.content.BroadcastReceiver broadcastReceiver3 = new android.content.BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.util.Iterator<java.util.Map.Entry<java.lang.String, android.hardware.usb.UsbDevice>> it = ((android.hardware.usb.UsbManager) context2.getSystemService("usb")).getDeviceList().entrySet().iterator();
                if (intent.getAction().equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                    com.android.server.usb.UsbDeviceManager.this.mHandler.sendMessage(10, (java.lang.Object) it, true);
                } else {
                    com.android.server.usb.UsbDeviceManager.this.mHandler.sendMessage(10, (java.lang.Object) it, false);
                }
            }
        };
        android.content.BroadcastReceiver broadcastReceiver4 = new android.content.BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.usb.UsbDeviceManager.this.mHandler.sendEmptyMessage(11);
            }
        };
        this.mContext.registerReceiver(broadcastReceiver, new android.content.IntentFilter("android.hardware.usb.action.USB_PORT_CHANGED"));
        this.mContext.registerReceiver(broadcastReceiver2, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED"));
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.mContext.registerReceiver(broadcastReceiver3, intentFilter);
        this.mContext.registerReceiver(broadcastReceiver4, new android.content.IntentFilter("android.intent.action.LOCALE_CHANGED"));
        this.mUEventObserver = new com.android.server.usb.UsbDeviceManager.UsbUEventObserver();
        this.mUEventObserver.startObserving(USB_STATE_MATCH);
        this.mUEventObserver.startObserving(ACCESSORY_START_MATCH);
        sEventLogger = new com.android.server.utils.EventLogger(200, "UsbDeviceManager activity");
        this.mContentResolver.registerContentObserver(lineageos.providers.LineageSettings.Global.getUriFor("trust_restrict_usb"), false, new android.database.ContentObserver(0 == true ? 1 : 0) { // from class: com.android.server.usb.UsbDeviceManager.5
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.usb.UsbDeviceManager.this.mHandler.setTrustRestrictUsb();
            }
        });
    }

    com.android.server.usb.UsbProfileGroupSettingsManager getCurrentSettings() {
        com.android.server.usb.UsbProfileGroupSettingsManager usbProfileGroupSettingsManager;
        synchronized (this.mLock) {
            usbProfileGroupSettingsManager = this.mCurrentSettings;
        }
        return usbProfileGroupSettingsManager;
    }

    java.lang.String[] getAccessoryStrings() {
        java.lang.String[] strArr;
        synchronized (this.mLock) {
            strArr = this.mAccessoryStrings;
        }
        return strArr;
    }

    public void systemReady() {
        ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).registerScreenObserver(this);
        this.mHandler.sendEmptyMessage(3);
    }

    public void bootCompleted() {
        this.mHandler.sendEmptyMessage(4);
    }

    public void setCurrentUser(int i, com.android.server.usb.UsbProfileGroupSettingsManager usbProfileGroupSettingsManager) {
        synchronized (this.mLock) {
            this.mCurrentSettings = usbProfileGroupSettingsManager;
            this.mHandler.obtainMessage(5, i, 0).sendToTarget();
        }
    }

    public void updateUserRestrictions() {
        this.mHandler.sendEmptyMessage(6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetAccessoryHandshakeTimeoutHandler() {
        if ((getCurrentFunctions() & 2) == 0) {
            this.mHandler.removeMessages(20);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(20), com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAccessoryMode() {
        long j;
        if (this.mHasUsbAccessory) {
            int incrementAndGet = sUsbOperationCount.incrementAndGet();
            this.mAccessoryStrings = nativeGetAccessoryStrings();
            boolean z = false;
            boolean z2 = nativeGetAudioMode() == 1;
            if (this.mAccessoryStrings != null && this.mAccessoryStrings[0] != null && this.mAccessoryStrings[1] != null) {
                z = true;
            }
            if (!z) {
                j = 0;
            } else {
                j = 2;
            }
            if (z2) {
                j |= 64;
            }
            if (j != 0) {
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(8), com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(20), com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                setCurrentFunctions(j, incrementAndGet);
            }
        }
    }

    private static void initRndisAddress() {
        int[] iArr = new int[6];
        iArr[0] = 2;
        java.lang.String str = android.os.SystemProperties.get("ro.serialno", "1234567890ABCDEF");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            int i2 = (i % 5) + 1;
            iArr[i2] = iArr[i2] ^ str.charAt(i);
        }
        try {
            android.os.FileUtils.stringToFile(RNDIS_ETH_ADDR_PATH, java.lang.String.format(java.util.Locale.US, "%02X:%02X:%02X:%02X:%02X:%02X", java.lang.Integer.valueOf(iArr[0]), java.lang.Integer.valueOf(iArr[1]), java.lang.Integer.valueOf(iArr[2]), java.lang.Integer.valueOf(iArr[3]), java.lang.Integer.valueOf(iArr[4]), java.lang.Integer.valueOf(iArr[5])));
        } catch (java.io.IOException e) {
            android.util.Slog.i(TAG, "failed to write to /sys/class/android_usb/android0/f_rndis/ethaddr");
        }
    }

    public static void logAndPrint(int i, com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str) {
        android.util.Slog.println(i, TAG, str);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str);
        }
    }

    public static void logAndPrintException(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.Exception exc) {
        android.util.Slog.e(TAG, str, exc);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str + exc);
        }
    }

    static abstract class UsbHandler extends android.os.Handler {
        protected static final java.lang.String USB_PERSISTENT_CONFIG_PROPERTY = "persist.sys.usb.config";
        private long mAccessoryConnectionStartTime;
        private boolean mAdbNotificationShown;
        private boolean mAudioAccessoryConnected;
        private boolean mAudioAccessorySupported;
        private boolean mAudioSourceEnabled;
        protected boolean mBootCompleted;
        private android.content.Intent mBroadcastedIntent;
        protected boolean mConfigured;
        protected boolean mConnected;
        private boolean mConnectedToDataDisabledPort;
        protected final android.content.ContentResolver mContentResolver;
        private final android.content.Context mContext;
        private android.hardware.usb.UsbAccessory mCurrentAccessory;
        protected long mCurrentFunctions;
        protected boolean mCurrentFunctionsApplied;
        protected int mCurrentGadgetHalVersion;
        protected boolean mCurrentUsbFunctionsReceived;
        protected int mCurrentUser;
        private boolean mHideUsbNotification;
        private boolean mHostConnected;
        private boolean mInHostModeWithNoAccessoryConnected;
        private boolean mIsKeyguardShowing;
        private int mMidiCard;
        private int mMidiDevice;
        private boolean mMidiEnabled;
        private android.app.NotificationManager mNotificationManager;
        protected boolean mPendingBootAccessoryHandshakeBroadcast;
        private boolean mPendingBootBroadcast;
        private final com.android.server.usb.UsbPermissionManager mPermissionManager;
        private int mPowerBrickConnectionStatus;
        protected boolean mResetUsbGadgetDisableDebounce;
        private boolean mScreenLocked;
        protected long mScreenUnlockedFunctions;
        private int mSendStringCount;
        protected android.content.SharedPreferences mSettings;
        private boolean mSinkPower;
        private boolean mSourcePower;
        private boolean mStartAccessory;
        private boolean mSupportsAllCombinations;
        private boolean mSystemReady;
        private boolean mUsbAccessoryConnected;
        private final com.android.server.usb.UsbAlsaManager mUsbAlsaManager;
        private boolean mUsbCharging;
        protected final com.android.server.usb.UsbDeviceManager mUsbDeviceManager;
        private int mUsbNotificationId;
        protected int mUsbSpeed;
        protected boolean mUseUsbNotification;

        public abstract void getUsbSpeedCb(int i);

        public abstract void handlerInitDone(int i);

        public abstract void resetCb(int i);

        public abstract void setCurrentUsbFunctionsCb(long j, int i, int i2, long j2, boolean z);

        protected abstract void setEnabledFunctions(long j, boolean z, int i);

        UsbHandler(android.os.Looper looper, android.content.Context context, com.android.server.usb.UsbDeviceManager usbDeviceManager, com.android.server.usb.UsbAlsaManager usbAlsaManager, com.android.server.usb.UsbPermissionManager usbPermissionManager) {
            super(looper);
            boolean z;
            this.mAccessoryConnectionStartTime = 0L;
            boolean z2 = false;
            this.mSendStringCount = 0;
            this.mStartAccessory = false;
            this.mContext = context;
            this.mUsbDeviceManager = usbDeviceManager;
            this.mUsbAlsaManager = usbAlsaManager;
            this.mPermissionManager = usbPermissionManager;
            this.mContentResolver = context.getContentResolver();
            this.mCurrentUser = android.app.ActivityManager.getCurrentUser();
            this.mScreenLocked = true;
            this.mIsKeyguardShowing = true;
            this.mSettings = getPinnedSharedPrefs(this.mContext);
            if (this.mSettings == null) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Couldn't load shared preferences");
            } else {
                this.mScreenUnlockedFunctions = android.hardware.usb.UsbManager.usbFunctionsFromString(this.mSettings.getString(java.lang.String.format(java.util.Locale.ENGLISH, com.android.server.usb.UsbDeviceManager.UNLOCKED_CONFIG_PREF, java.lang.Integer.valueOf(this.mCurrentUser)), ""));
            }
            android.os.storage.StorageManager from = android.os.storage.StorageManager.from(this.mContext);
            android.os.storage.StorageVolume primaryVolume = from != null ? from.getPrimaryVolume() : null;
            if (primaryVolume == null || !primaryVolume.allowMassStorage()) {
                z = false;
            } else {
                z = true;
            }
            if (!z && this.mContext.getResources().getBoolean(android.R.bool.config_unfoldTransitionHapticsEnabled)) {
                z2 = true;
            }
            this.mUseUsbNotification = z2;
        }

        public void sendMessage(int i, boolean z) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.arg1 = z ? 1 : 0;
            sendMessage(obtain);
        }

        public boolean sendMessage(int i) {
            removeMessages(i);
            return sendMessageDelayed(android.os.Message.obtain(this, i), 0L);
        }

        public void sendMessage(int i, int i2) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.arg1 = i2;
            sendMessage(obtain);
        }

        public void sendMessage(int i, java.lang.Object obj) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.obj = obj;
            sendMessage(obtain);
        }

        public void sendMessage(int i, java.lang.Object obj, int i2) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.obj = obj;
            obtain.arg1 = i2;
            sendMessage(obtain);
        }

        public void sendMessage(int i, boolean z, int i2) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.arg1 = z ? 1 : 0;
            obtain.arg2 = i2;
            sendMessage(obtain);
        }

        public void sendMessage(int i, java.lang.Object obj, boolean z) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.obj = obj;
            obtain.arg1 = z ? 1 : 0;
            sendMessage(obtain);
        }

        public void sendMessage(int i, long j, boolean z, int i2) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.obj = java.lang.Long.valueOf(j);
            obtain.arg1 = z ? 1 : 0;
            obtain.arg2 = i2;
            sendMessage(obtain);
        }

        public void sendMessage(int i, boolean z, boolean z2) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.arg1 = z ? 1 : 0;
            obtain.arg2 = z2 ? 1 : 0;
            sendMessage(obtain);
        }

        public void sendMessageDelayed(int i, boolean z, long j) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.arg1 = z ? 1 : 0;
            sendMessageDelayed(obtain, j);
        }

        public void sendMessageDelayed(int i, boolean z, boolean z2, long j) {
            removeMessages(i);
            android.os.Message obtain = android.os.Message.obtain(this, i);
            obtain.arg1 = z ? 1 : 0;
            obtain.arg2 = z2 ? 1 : 0;
            sendMessageDelayed(obtain, j);
        }

        public void updateState(java.lang.String str) {
            int i;
            int i2;
            long j;
            if ("DISCONNECTED".equals(str)) {
                i = 0;
                i2 = 0;
            } else if ("CONNECTED".equals(str)) {
                i = 1;
                i2 = 0;
            } else if ("CONFIGURED".equals(str)) {
                i = 1;
                i2 = 1;
            } else {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "unknown state " + str);
                return;
            }
            if (i == 1) {
                removeMessages(17);
            }
            android.os.Message obtain = android.os.Message.obtain(this, 0);
            obtain.arg1 = i;
            obtain.arg2 = i2;
            if (this.mResetUsbGadgetDisableDebounce) {
                sendMessage(obtain);
                if (i == 1) {
                    this.mResetUsbGadgetDisableDebounce = false;
                    return;
                }
                return;
            }
            if (i2 == 0) {
                removeMessages(0);
            }
            if (i == 1) {
                removeMessages(17);
            }
            if (i == 0) {
                j = this.mScreenLocked ? 1000 : 3000;
            } else {
                j = 0;
            }
            sendMessageDelayed(obtain, j);
        }

        public void updateHostState(android.hardware.usb.UsbPort usbPort, android.hardware.usb.UsbPortStatus usbPortStatus) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = usbPort;
            obtain.arg2 = usbPortStatus;
            removeMessages(7);
            sendMessageDelayed(obtainMessage(7, obtain), 1000L);
        }

        private void setAdbEnabled(boolean z, int i) {
            if (z) {
                setSystemProperty(USB_PERSISTENT_CONFIG_PROPERTY, com.android.server.integrity.AppIntegrityManagerServiceImpl.ADB_INSTALLER);
            } else {
                setSystemProperty(USB_PERSISTENT_CONFIG_PROPERTY, "");
            }
            setEnabledFunctions(this.mCurrentFunctions, true, i);
            updateAdbNotification(false);
        }

        protected boolean isUsbTransferAllowed() {
            return !((android.os.UserManager) this.mContext.getSystemService("user")).hasUserRestriction("no_usb_file_transfer");
        }

        private void updateCurrentAccessory() {
            int incrementAndGet = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
            boolean hasMessages = hasMessages(8);
            if (this.mConfigured && hasMessages) {
                java.lang.String[] accessoryStrings = this.mUsbDeviceManager.getAccessoryStrings();
                if (accessoryStrings != null) {
                    com.android.server.usb.UsbSerialReader usbSerialReader = new com.android.server.usb.UsbSerialReader(this.mContext, this.mPermissionManager, accessoryStrings[5]);
                    this.mCurrentAccessory = new android.hardware.usb.UsbAccessory(accessoryStrings[0], accessoryStrings[1], accessoryStrings[2], accessoryStrings[3], accessoryStrings[4], usbSerialReader);
                    usbSerialReader.setDevice(this.mCurrentAccessory);
                    android.util.Slog.d(com.android.server.usb.UsbDeviceManager.TAG, "entering USB accessory mode: " + this.mCurrentAccessory);
                    if (this.mBootCompleted) {
                        this.mUsbDeviceManager.getCurrentSettings().accessoryAttached(this.mCurrentAccessory);
                        removeMessages(20);
                        broadcastUsbAccessoryHandshake();
                        return;
                    }
                    return;
                }
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "nativeGetAccessoryStrings failed");
                return;
            }
            if (!hasMessages) {
                notifyAccessoryModeExit(incrementAndGet);
            }
        }

        private void notifyAccessoryModeExit(int i) {
            android.util.Slog.d(com.android.server.usb.UsbDeviceManager.TAG, "exited USB accessory mode");
            setEnabledFunctions(0L, false, i);
            if (this.mCurrentAccessory != null) {
                if (this.mBootCompleted) {
                    this.mPermissionManager.usbAccessoryRemoved(this.mCurrentAccessory);
                }
                this.mCurrentAccessory = null;
            }
        }

        protected android.content.SharedPreferences getPinnedSharedPrefs(android.content.Context context) {
            return context.createDeviceProtectedStorageContext().getSharedPreferences(new java.io.File(android.os.Environment.getDataSystemDeDirectory(0), com.android.server.usb.UsbDeviceManager.USB_PREFS_XML), 0);
        }

        private boolean isUsbStateChanged(android.content.Intent intent) {
            java.util.Set<java.lang.String> keySet = intent.getExtras().keySet();
            if (this.mBroadcastedIntent == null) {
                java.util.Iterator<java.lang.String> it = keySet.iterator();
                while (it.hasNext()) {
                    if (intent.getBooleanExtra(it.next(), false)) {
                        return true;
                    }
                }
            } else {
                if (!keySet.equals(this.mBroadcastedIntent.getExtras().keySet())) {
                    return true;
                }
                for (java.lang.String str : keySet) {
                    if (intent.getBooleanExtra(str, false) != this.mBroadcastedIntent.getBooleanExtra(str, false)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void broadcastUsbAccessoryHandshake() {
            sendStickyBroadcast(new android.content.Intent("android.hardware.usb.action.USB_ACCESSORY_HANDSHAKE").addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB).putExtra("android.hardware.usb.extra.ACCESSORY_UEVENT_TIME", this.mAccessoryConnectionStartTime).putExtra("android.hardware.usb.extra.ACCESSORY_STRING_COUNT", this.mSendStringCount).putExtra("android.hardware.usb.extra.ACCESSORY_START", this.mStartAccessory).putExtra("android.hardware.usb.extra.ACCESSORY_HANDSHAKE_END", android.os.SystemClock.elapsedRealtime()));
            resetUsbAccessoryHandshakeDebuggingInfo();
        }

        protected void updateUsbStateBroadcastIfNeeded(long j) {
            android.content.Intent intent = new android.content.Intent("android.hardware.usb.action.USB_STATE");
            intent.addFlags(822083584);
            intent.putExtra("connected", this.mConnected);
            intent.putExtra("host_connected", this.mHostConnected);
            intent.putExtra("configured", this.mConfigured);
            intent.putExtra("unlocked", isUsbTransferAllowed() && isUsbDataTransferActive(this.mCurrentFunctions));
            while (j != 0) {
                intent.putExtra(android.hardware.usb.UsbManager.usbFunctionsToString(java.lang.Long.highestOneBit(j)), true);
                j -= java.lang.Long.highestOneBit(j);
            }
            if (!isUsbStateChanged(intent)) {
                return;
            }
            sendStickyBroadcast(intent);
            this.mBroadcastedIntent = intent;
        }

        protected void sendStickyBroadcast(android.content.Intent intent) {
            this.mContext.sendStickyBroadcastAsUser(intent, android.os.UserHandle.ALL);
            com.android.server.usb.UsbDeviceManager.sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("USB intent: " + intent));
        }

        private void updateUsbFunctions() {
            updateMidiFunction();
        }

        private void updateMidiFunction() {
            boolean z = (this.mCurrentFunctions & 8) != 0;
            if (z != this.mMidiEnabled) {
                if (z) {
                    java.util.Scanner scanner = null;
                    java.util.Scanner scanner2 = null;
                    try {
                        try {
                            java.util.Scanner scanner3 = new java.util.Scanner(new java.io.File(com.android.server.usb.UsbDeviceManager.MIDI_ALSA_PATH));
                            try {
                                this.mMidiCard = scanner3.nextInt();
                                int nextInt = scanner3.nextInt();
                                this.mMidiDevice = nextInt;
                                scanner3.close();
                                scanner = nextInt;
                            } catch (java.io.FileNotFoundException e) {
                                e = e;
                                scanner2 = scanner3;
                                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "could not open MIDI file", e);
                                if (scanner2 != null) {
                                    scanner2.close();
                                }
                                z = false;
                                scanner = scanner2;
                                this.mMidiEnabled = z;
                                this.mUsbAlsaManager.setPeripheralMidiState(!this.mMidiEnabled && this.mConfigured, this.mMidiCard, this.mMidiDevice);
                            } catch (java.lang.Throwable th) {
                                th = th;
                                scanner = scanner3;
                                if (scanner != null) {
                                    scanner.close();
                                }
                                throw th;
                            }
                        } catch (java.io.FileNotFoundException e2) {
                            e = e2;
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                    }
                }
                this.mMidiEnabled = z;
            }
            this.mUsbAlsaManager.setPeripheralMidiState(!this.mMidiEnabled && this.mConfigured, this.mMidiCard, this.mMidiDevice);
        }

        private void setScreenUnlockedFunctions(int i) {
            setEnabledFunctions(this.mScreenUnlockedFunctions, false, i);
        }

        private static class AdbTransport extends android.debug.IAdbTransport.Stub {
            private final com.android.server.usb.UsbDeviceManager.UsbHandler mHandler;

            AdbTransport(com.android.server.usb.UsbDeviceManager.UsbHandler usbHandler) {
                this.mHandler = usbHandler;
            }

            public void onAdbEnabled(boolean z, byte b) {
                if (b == 0) {
                    this.mHandler.sendMessage(1, z, com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet());
                }
            }
        }

        long getAppliedFunctions(long j) {
            if (j == 0) {
                return getChargingFunctions();
            }
            if (isAdbEnabled()) {
                return j | 1;
            }
            return j;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    int incrementAndGet = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    this.mConnected = message.arg1 == 1;
                    this.mConfigured = message.arg2 == 1;
                    setTrustRestrictUsb();
                    updateUsbNotification(false);
                    updateAdbNotification(false);
                    if (this.mBootCompleted) {
                        updateUsbStateBroadcastIfNeeded(getAppliedFunctions(this.mCurrentFunctions));
                    }
                    if ((2 & this.mCurrentFunctions) != 0) {
                        updateCurrentAccessory();
                    }
                    if (this.mBootCompleted) {
                        if (!this.mConnected && !hasMessages(8) && !hasMessages(17)) {
                            if (!this.mScreenLocked && this.mScreenUnlockedFunctions != 0) {
                                setScreenUnlockedFunctions(incrementAndGet);
                            } else {
                                setEnabledFunctions(0L, false, incrementAndGet);
                            }
                        }
                        updateUsbFunctions();
                    } else {
                        this.mPendingBootBroadcast = true;
                    }
                    updateUsbSpeed();
                    break;
                case 1:
                    setAdbEnabled(message.arg1 == 1, message.arg2);
                    break;
                case 2:
                    setEnabledFunctions(((java.lang.Long) message.obj).longValue(), false, message.arg1);
                    break;
                case 3:
                    int incrementAndGet2 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    this.mNotificationManager = (android.app.NotificationManager) this.mContext.getSystemService("notification");
                    ((android.debug.AdbManagerInternal) com.android.server.LocalServices.getService(android.debug.AdbManagerInternal.class)).registerTransport(new com.android.server.usb.UsbDeviceManager.UsbHandler.AdbTransport(this));
                    if (isTv()) {
                        this.mNotificationManager.createNotificationChannel(new android.app.NotificationChannel(com.android.server.usb.UsbDeviceManager.ADB_NOTIFICATION_CHANNEL_ID_TV, this.mContext.getString(android.R.string.activity_resolver_use_once), 4));
                    }
                    this.mSystemReady = true;
                    finishBoot(incrementAndGet2);
                    break;
                case 4:
                    int incrementAndGet3 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    this.mBootCompleted = true;
                    finishBoot(incrementAndGet3);
                    break;
                case 5:
                    int incrementAndGet4 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    if (this.mCurrentUser != message.arg1) {
                        this.mCurrentUser = message.arg1;
                        this.mScreenLocked = true;
                        this.mScreenUnlockedFunctions = 0L;
                        if (this.mSettings != null) {
                            this.mScreenUnlockedFunctions = android.hardware.usb.UsbManager.usbFunctionsFromString(this.mSettings.getString(java.lang.String.format(java.util.Locale.ENGLISH, com.android.server.usb.UsbDeviceManager.UNLOCKED_CONFIG_PREF, java.lang.Integer.valueOf(this.mCurrentUser)), ""));
                        }
                        setEnabledFunctions(0L, false, incrementAndGet4);
                        break;
                    }
                    break;
                case 6:
                    int incrementAndGet5 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    if (isUsbDataTransferActive(this.mCurrentFunctions) && !isUsbTransferAllowed()) {
                        setEnabledFunctions(0L, true, incrementAndGet5);
                        break;
                    }
                    break;
                case 7:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    boolean z = this.mHostConnected;
                    android.hardware.usb.UsbPort usbPort = (android.hardware.usb.UsbPort) someArgs.arg1;
                    android.hardware.usb.UsbPortStatus usbPortStatus = (android.hardware.usb.UsbPortStatus) someArgs.arg2;
                    boolean z2 = usbPortStatus != null && usbPortStatus.getUsbDataStatus() == 16;
                    if (usbPortStatus != null && !z2) {
                        this.mHostConnected = usbPortStatus.getCurrentDataRole() == 1;
                        this.mSourcePower = usbPortStatus.getCurrentPowerRole() == 1;
                        this.mSinkPower = usbPortStatus.getCurrentPowerRole() == 2;
                        this.mAudioAccessoryConnected = usbPortStatus.getCurrentMode() == 4;
                        this.mSupportsAllCombinations = usbPortStatus.isRoleCombinationSupported(1, 1) && usbPortStatus.isRoleCombinationSupported(2, 1) && usbPortStatus.isRoleCombinationSupported(1, 2) && usbPortStatus.isRoleCombinationSupported(2, 2);
                        this.mConnectedToDataDisabledPort = usbPortStatus.isConnected() && (usbPortStatus.getUsbDataStatus() != 1);
                        this.mPowerBrickConnectionStatus = usbPortStatus.getPowerBrickConnectionStatus();
                    } else {
                        this.mHostConnected = false;
                        this.mSourcePower = false;
                        this.mSinkPower = false;
                        this.mAudioAccessoryConnected = false;
                        this.mSupportsAllCombinations = false;
                        this.mConnectedToDataDisabledPort = false;
                        this.mPowerBrickConnectionStatus = 0;
                    }
                    if (z2) {
                        this.mConnected = false;
                        setEnabledFunctions(0L, false, com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet());
                    }
                    if (this.mHostConnected) {
                        if (!this.mUsbAccessoryConnected) {
                            this.mInHostModeWithNoAccessoryConnected = true;
                        } else {
                            this.mInHostModeWithNoAccessoryConnected = false;
                        }
                    } else {
                        this.mInHostModeWithNoAccessoryConnected = false;
                    }
                    setTrustRestrictUsb();
                    this.mAudioAccessorySupported = usbPort.isModeSupported(4);
                    someArgs.recycle();
                    updateUsbNotification(false);
                    if (this.mBootCompleted) {
                        if (this.mHostConnected || z || z2) {
                            updateUsbStateBroadcastIfNeeded(getAppliedFunctions(this.mCurrentFunctions));
                            break;
                        }
                    } else {
                        this.mPendingBootBroadcast = true;
                        break;
                    }
                    break;
                case 8:
                    int incrementAndGet6 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    if (!this.mConnected || (this.mCurrentFunctions & 2) == 0) {
                        notifyAccessoryModeExit(incrementAndGet6);
                        break;
                    }
                    break;
                case 9:
                    this.mUsbCharging = message.arg1 == 1;
                    updateUsbNotification(false);
                    break;
                case 10:
                    java.util.Iterator it = (java.util.Iterator) message.obj;
                    this.mUsbAccessoryConnected = message.arg1 == 1;
                    if (!it.hasNext()) {
                        this.mInHostModeWithNoAccessoryConnected = true;
                    } else {
                        this.mInHostModeWithNoAccessoryConnected = false;
                    }
                    this.mHideUsbNotification = false;
                    while (it.hasNext()) {
                        android.hardware.usb.UsbDevice usbDevice = (android.hardware.usb.UsbDevice) ((java.util.Map.Entry) it.next()).getValue();
                        int configurationCount = usbDevice.getConfigurationCount() - 1;
                        while (configurationCount >= 0) {
                            android.hardware.usb.UsbConfiguration configuration = usbDevice.getConfiguration(configurationCount);
                            configurationCount--;
                            int interfaceCount = configuration.getInterfaceCount() - 1;
                            while (true) {
                                if (interfaceCount >= 0) {
                                    android.hardware.usb.UsbInterface usbInterface = configuration.getInterface(interfaceCount);
                                    interfaceCount--;
                                    if (com.android.server.usb.UsbDeviceManager.sDenyInterfaces.contains(java.lang.Integer.valueOf(usbInterface.getInterfaceClass()))) {
                                        this.mHideUsbNotification = true;
                                    }
                                }
                            }
                        }
                    }
                    updateUsbNotification(false);
                    break;
                case 11:
                    updateAdbNotification(true);
                    updateUsbNotification(true);
                    break;
                case 12:
                    int incrementAndGet7 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    this.mScreenUnlockedFunctions = ((java.lang.Long) message.obj).longValue();
                    if (this.mSettings != null) {
                        android.content.SharedPreferences.Editor edit = this.mSettings.edit();
                        edit.putString(java.lang.String.format(java.util.Locale.ENGLISH, com.android.server.usb.UsbDeviceManager.UNLOCKED_CONFIG_PREF, java.lang.Integer.valueOf(this.mCurrentUser)), android.hardware.usb.UsbManager.usbFunctionsToString(this.mScreenUnlockedFunctions));
                        edit.commit();
                    }
                    if (!this.mScreenLocked && this.mScreenUnlockedFunctions != 0) {
                        setScreenUnlockedFunctions(incrementAndGet7);
                        break;
                    } else {
                        setEnabledFunctions(0L, false, incrementAndGet7);
                        break;
                    }
                    break;
                case 13:
                    int incrementAndGet8 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    this.mIsKeyguardShowing = message.arg1 == 1;
                    boolean z3 = message.arg2 == 1;
                    setTrustRestrictUsb();
                    if ((this.mIsKeyguardShowing && z3) != this.mScreenLocked) {
                        this.mScreenLocked = this.mIsKeyguardShowing && z3;
                        if (this.mBootCompleted) {
                            if (!this.mScreenLocked) {
                                if (this.mScreenUnlockedFunctions != 0 && this.mCurrentFunctions == 0) {
                                    setScreenUnlockedFunctions(incrementAndGet8);
                                    break;
                                }
                            } else if (!this.mConnected) {
                                setEnabledFunctions(0L, false, incrementAndGet8);
                                break;
                            }
                        }
                    }
                    break;
                case 20:
                    if (this.mBootCompleted) {
                        broadcastUsbAccessoryHandshake();
                        break;
                    } else {
                        this.mPendingBootAccessoryHandshakeBroadcast = true;
                        break;
                    }
                case 21:
                    this.mSendStringCount++;
                    break;
            }
        }

        protected void finishBoot(int i) {
            if (this.mBootCompleted && this.mCurrentUsbFunctionsReceived && this.mSystemReady) {
                setTrustRestrictUsb();
                if (this.mPendingBootBroadcast) {
                    updateUsbStateBroadcastIfNeeded(getAppliedFunctions(this.mCurrentFunctions));
                    this.mPendingBootBroadcast = false;
                }
                if (!this.mScreenLocked && this.mScreenUnlockedFunctions != 0) {
                    setScreenUnlockedFunctions(i);
                } else {
                    setEnabledFunctions(0L, false, i);
                }
                if (this.mCurrentAccessory != null) {
                    this.mUsbDeviceManager.getCurrentSettings().accessoryAttached(this.mCurrentAccessory);
                    broadcastUsbAccessoryHandshake();
                } else if (this.mPendingBootAccessoryHandshakeBroadcast) {
                    broadcastUsbAccessoryHandshake();
                }
                this.mPendingBootAccessoryHandshakeBroadcast = false;
                updateUsbNotification(false);
                updateAdbNotification(false);
                updateUsbFunctions();
            }
        }

        protected boolean isUsbDataTransferActive(long j) {
            return ((4 & j) == 0 && (j & 16) == 0) ? false : true;
        }

        public android.hardware.usb.UsbAccessory getCurrentAccessory() {
            return this.mCurrentAccessory;
        }

        protected void updateUsbGadgetHalVersion() {
            sendMessage(23, (java.lang.Object) null);
        }

        protected void updateUsbSpeed() {
            if (this.mCurrentGadgetHalVersion < 10) {
                this.mUsbSpeed = -1;
            } else if (this.mConnected && this.mConfigured) {
                sendMessage(22, (java.lang.Object) null);
            } else {
                this.mUsbSpeed = -1;
            }
        }

        protected void updateUsbNotification(boolean z) {
            int i;
            int i2;
            int i3;
            int i4;
            android.app.PendingIntent pendingIntent;
            java.lang.String str;
            android.app.PendingIntent pendingIntent2;
            if (this.mNotificationManager == null || !this.mUseUsbNotification || "0".equals(getSystemProperty("persist.charging.notify", ""))) {
                return;
            }
            if ((this.mHideUsbNotification || this.mInHostModeWithNoAccessoryConnected) && !this.mSupportsAllCombinations) {
                if (this.mUsbNotificationId != 0) {
                    this.mNotificationManager.cancelAsUser(null, this.mUsbNotificationId, android.os.UserHandle.ALL);
                    this.mUsbNotificationId = 0;
                    android.util.Slog.d(com.android.server.usb.UsbDeviceManager.TAG, "Clear notification");
                    return;
                }
                return;
            }
            android.content.res.Resources resources = this.mContext.getResources();
            java.lang.CharSequence text = resources.getText(android.R.string.toolbar_collapse_description);
            if (this.mAudioAccessoryConnected && !this.mAudioAccessorySupported) {
                i2 = 41;
                i = 17041847;
            } else {
                boolean z2 = this.mConnected;
                i = android.R.string.turn_on_magnification_settings_action;
                if (z2) {
                    if (this.mCurrentFunctions == 4) {
                        i3 = android.R.string.time_placeholder;
                        i4 = 27;
                    } else if (this.mCurrentFunctions == 16) {
                        i3 = android.R.string.turn_off_radio;
                        i4 = 28;
                    } else if (this.mCurrentFunctions == 8) {
                        i3 = android.R.string.time_picker_input_error;
                        i4 = 29;
                    } else if (this.mCurrentFunctions == 32 || this.mCurrentFunctions == 1024) {
                        i3 = android.R.string.turn_on_radio;
                        i4 = 47;
                    } else if (this.mCurrentFunctions == 128) {
                        i3 = android.R.string.ui_translation_accessibility_translated_text;
                        i4 = 75;
                    } else if (this.mCurrentFunctions != 2) {
                        i3 = 0;
                        i4 = 0;
                    } else {
                        i3 = android.R.string.time_picker_decrement_set_am_button;
                        i4 = 30;
                    }
                    if (this.mSourcePower) {
                        if (i3 != 0) {
                            text = resources.getText(android.R.string.tooltip_popup_title);
                            i = i3;
                            i2 = i4;
                        } else {
                            i2 = 31;
                        }
                    } else if (i3 != 0) {
                        i = i3;
                        i2 = i4;
                    } else {
                        i2 = 32;
                        i = 17041828;
                    }
                } else if (this.mSourcePower) {
                    i2 = 31;
                } else if (this.mHostConnected && this.mSinkPower && (this.mUsbCharging || this.mUsbAccessoryConnected)) {
                    i2 = 32;
                    i = 17041828;
                } else if (this.mSinkPower && this.mConnectedToDataDisabledPort && this.mPowerBrickConnectionStatus == 2) {
                    i2 = 32;
                    i = 17041828;
                } else {
                    i2 = 0;
                    i = 0;
                }
            }
            if (i2 != this.mUsbNotificationId || z) {
                if (this.mUsbNotificationId != 0) {
                    this.mNotificationManager.cancelAsUser(null, this.mUsbNotificationId, android.os.UserHandle.ALL);
                    android.util.Slog.d(com.android.server.usb.UsbDeviceManager.TAG, "Clear notification");
                    this.mUsbNotificationId = 0;
                }
                if ((this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive") || this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch")) && i2 == 32) {
                    this.mUsbNotificationId = 0;
                    return;
                }
                if (i2 != 0) {
                    java.lang.CharSequence text2 = resources.getText(i);
                    if (i != 17041847) {
                        pendingIntent2 = android.app.PendingIntent.getActivityAsUser(this.mContext, 0, android.content.Intent.makeRestartActivityTask(new android.content.ComponentName("com.android.settings", "com.android.settings.Settings$UsbDetailsActivity")), 67108864, null, android.os.UserHandle.CURRENT);
                        str = com.android.internal.notification.SystemNotificationChannels.USB;
                    } else {
                        android.content.Intent intent = new android.content.Intent();
                        intent.setClassName("com.android.settings", "com.android.settings.HelpTrampoline");
                        intent.putExtra("android.intent.extra.TEXT", "help_url_audio_accessory_not_supported");
                        if (this.mContext.getPackageManager().resolveActivity(intent, 0) != null) {
                            pendingIntent = android.app.PendingIntent.getActivity(this.mContext, 0, intent, 67108864);
                        } else {
                            pendingIntent = null;
                        }
                        str = com.android.internal.notification.SystemNotificationChannels.ALERTS;
                        android.app.PendingIntent pendingIntent3 = pendingIntent;
                        text = resources.getText(android.R.string.tutorial_double_tap_to_zoom_message_short);
                        pendingIntent2 = pendingIntent3;
                    }
                    android.app.Notification.Builder visibility = new android.app.Notification.Builder(this.mContext, str).setSmallIcon(android.R.drawable.stat_sys_battery_charge_anim85).setWhen(0L).setOngoing(true).setTicker(text2).setDefaults(0).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).setContentTitle(text2).setContentText(text).setSubText(android.os.Build.ID).setContentIntent(pendingIntent2).setVisibility(1);
                    if (i == 17041847) {
                        visibility.setStyle(new android.app.Notification.BigTextStyle().bigText(text));
                    }
                    this.mNotificationManager.notifyAsUser(null, i2, visibility.build(), android.os.UserHandle.ALL);
                    android.util.Slog.d(com.android.server.usb.UsbDeviceManager.TAG, "push notification:" + ((java.lang.Object) text2));
                    this.mUsbNotificationId = i2;
                }
            }
        }

        protected boolean isAdbEnabled() {
            return ((android.debug.AdbManagerInternal) com.android.server.LocalServices.getService(android.debug.AdbManagerInternal.class)).isAdbEnabled((byte) 0);
        }

        protected void updateAdbNotification(boolean z) {
            if (this.mNotificationManager == null) {
                return;
            }
            if (isAdbEnabled() && this.mConnected) {
                if ("0".equals(getSystemProperty("persist.adb.notify", ""))) {
                    return;
                }
                if (z && this.mAdbNotificationShown) {
                    this.mAdbNotificationShown = false;
                    this.mNotificationManager.cancelAsUser(null, 26, android.os.UserHandle.ALL);
                }
                if (!this.mAdbNotificationShown) {
                    android.app.Notification createNotification = android.debug.AdbNotifications.createNotification(this.mContext, (byte) 0);
                    this.mAdbNotificationShown = true;
                    this.mNotificationManager.notifyAsUser(null, 26, createNotification, android.os.UserHandle.ALL);
                    return;
                }
                return;
            }
            if (this.mAdbNotificationShown) {
                this.mAdbNotificationShown = false;
                this.mNotificationManager.cancelAsUser(null, 26, android.os.UserHandle.ALL);
            }
        }

        private boolean isTv() {
            return this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
        }

        protected long getChargingFunctions() {
            if (isAdbEnabled()) {
                return 1L;
            }
            return 4L;
        }

        protected void setSystemProperty(java.lang.String str, java.lang.String str2) {
            android.os.SystemProperties.set(str, str2);
        }

        protected java.lang.String getSystemProperty(java.lang.String str, java.lang.String str2) {
            return android.os.SystemProperties.get(str, str2);
        }

        protected void putGlobalSettings(android.content.ContentResolver contentResolver, java.lang.String str, int i) {
            android.provider.Settings.Global.putInt(contentResolver, str, i);
        }

        public long getEnabledFunctions() {
            return this.mCurrentFunctions;
        }

        public long getScreenUnlockedFunctions() {
            return this.mScreenUnlockedFunctions;
        }

        public int getUsbSpeed() {
            return this.mUsbSpeed;
        }

        public int getGadgetHalVersion() {
            return this.mCurrentGadgetHalVersion;
        }

        private void dumpFunctions(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j, long j2) {
            for (int i = 0; i < 63; i++) {
                long j3 = 1 << i;
                if ((j2 & j3) != 0) {
                    if (dualDumpOutputStream.isProto()) {
                        dualDumpOutputStream.write(str, j, j3);
                    } else {
                        dualDumpOutputStream.write(str, j, android.hardware.usb.gadget.V1_0.GadgetFunction.toString(j3));
                    }
                }
            }
        }

        public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
            long start = dualDumpOutputStream.start(str, j);
            dumpFunctions(dualDumpOutputStream, "current_functions", 2259152797697L, this.mCurrentFunctions);
            dualDumpOutputStream.write("current_functions_applied", 1133871366146L, this.mCurrentFunctionsApplied);
            dumpFunctions(dualDumpOutputStream, "screen_unlocked_functions", 2259152797699L, this.mScreenUnlockedFunctions);
            dualDumpOutputStream.write("screen_locked", 1133871366148L, this.mScreenLocked);
            dualDumpOutputStream.write("connected", 1133871366149L, this.mConnected);
            dualDumpOutputStream.write("configured", 1133871366150L, this.mConfigured);
            if (this.mCurrentAccessory != null) {
                com.android.internal.usb.DumpUtils.writeAccessory(dualDumpOutputStream, "current_accessory", 1146756268039L, this.mCurrentAccessory);
            }
            dualDumpOutputStream.write("host_connected", 1133871366152L, this.mHostConnected);
            dualDumpOutputStream.write("source_power", 1133871366153L, this.mSourcePower);
            dualDumpOutputStream.write("sink_power", 1133871366154L, this.mSinkPower);
            dualDumpOutputStream.write("usb_charging", 1133871366155L, this.mUsbCharging);
            dualDumpOutputStream.write("hide_usb_notification", 1133871366156L, this.mHideUsbNotification);
            dualDumpOutputStream.write("audio_accessory_connected", 1133871366157L, this.mAudioAccessoryConnected);
            try {
                com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "kernel_state", 1138166333455L, android.os.FileUtils.readTextFile(new java.io.File(com.android.server.usb.UsbDeviceManager.STATE_PATH), 0, null).trim());
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.w(com.android.server.usb.UsbDeviceManager.TAG, "Ignore missing legacy kernel path in bugreport dump: kernel state:/sys/class/android_usb/android0/state");
            } catch (java.lang.Exception e2) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Could not read kernel state", e2);
            }
            try {
                com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "kernel_function_list", 1138166333456L, android.os.FileUtils.readTextFile(new java.io.File(com.android.server.usb.UsbDeviceManager.FUNCTIONS_PATH), 0, null).trim());
            } catch (java.io.FileNotFoundException e3) {
                android.util.Slog.w(com.android.server.usb.UsbDeviceManager.TAG, "Ignore missing legacy kernel path in bugreport dump: kernel function list:/sys/class/android_usb/android0/functions");
            } catch (java.lang.Exception e4) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Could not read kernel function list", e4);
            }
            dualDumpOutputStream.end(start);
        }

        public void setAccessoryUEventTime(long j) {
            this.mAccessoryConnectionStartTime = j;
        }

        public void setStartAccessoryTrue() {
            this.mStartAccessory = true;
        }

        public void resetUsbAccessoryHandshakeDebuggingInfo() {
            this.mAccessoryConnectionStartTime = 0L;
            this.mSendStringCount = 0;
            this.mStartAccessory = false;
        }

        public void setTrustRestrictUsb() {
            int i = lineageos.providers.LineageSettings.Global.getInt(this.mContentResolver, "trust_restrict_usb", 0);
            boolean z = (i == 1 && this.mIsKeyguardShowing && !(this.mConnected || this.mHostConnected)) || i == 2;
            android.hardware.usb.UsbManager usbManager = (android.hardware.usb.UsbManager) this.mContext.getSystemService(android.hardware.usb.UsbManager.class);
            if (usbManager != null) {
                try {
                    if (usbManager.getUsbHalVersion() >= 13) {
                        usbManager.enableUsbDataSignal(z ? false : true);
                    }
                } catch (java.lang.RuntimeException e) {
                }
            }
        }
    }

    private static final class UsbHandlerLegacy extends com.android.server.usb.UsbDeviceManager.UsbHandler {
        private static final java.lang.String USB_CONFIG_PROPERTY = "sys.usb.config";
        private static final java.lang.String USB_STATE_PROPERTY = "sys.usb.state";
        private java.lang.String mCurrentFunctionsStr;
        private java.lang.String mCurrentOemFunctions;
        private int mCurrentRequest;
        private java.util.HashMap<java.lang.String, java.util.HashMap<java.lang.String, android.util.Pair<java.lang.String, java.lang.String>>> mOemModeMap;
        private boolean mUsbDataUnlocked;

        UsbHandlerLegacy(android.os.Looper looper, android.content.Context context, com.android.server.usb.UsbDeviceManager usbDeviceManager, com.android.server.usb.UsbAlsaManager usbAlsaManager, com.android.server.usb.UsbPermissionManager usbPermissionManager) {
            super(looper, context, usbDeviceManager, usbAlsaManager, usbPermissionManager);
            this.mCurrentRequest = 0;
            try {
                readOemUsbOverrideConfig(context);
                this.mCurrentOemFunctions = getSystemProperty(getPersistProp(false), "none");
                if (!isNormalBoot()) {
                    this.mCurrentFunctionsStr = getSystemProperty(getPersistProp(true), "none");
                    this.mCurrentFunctionsApplied = getSystemProperty(USB_CONFIG_PROPERTY, "none").equals(getSystemProperty(USB_STATE_PROPERTY, "none"));
                } else {
                    this.mCurrentFunctionsStr = getSystemProperty(USB_CONFIG_PROPERTY, "none");
                    this.mCurrentFunctionsApplied = this.mCurrentFunctionsStr.equals(getSystemProperty(USB_STATE_PROPERTY, "none"));
                }
                this.mCurrentFunctions = 0L;
                this.mCurrentUsbFunctionsReceived = true;
                this.mUsbSpeed = -1;
                this.mCurrentGadgetHalVersion = -1;
                updateState(android.os.FileUtils.readTextFile(new java.io.File(com.android.server.usb.UsbDeviceManager.STATE_PATH), 0, null).trim());
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Error initializing UsbHandler", e);
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void handlerInitDone(int i) {
        }

        private void readOemUsbOverrideConfig(android.content.Context context) {
            java.lang.String[] stringArray = context.getResources().getStringArray(android.R.array.config_notificationMsgPkgsAllowedAsConvos);
            if (stringArray != null) {
                for (java.lang.String str : stringArray) {
                    java.lang.String[] split = str.split(":");
                    if (split.length == 3 || split.length == 4) {
                        if (this.mOemModeMap == null) {
                            this.mOemModeMap = new java.util.HashMap<>();
                        }
                        java.util.HashMap<java.lang.String, android.util.Pair<java.lang.String, java.lang.String>> hashMap = this.mOemModeMap.get(split[0]);
                        if (hashMap == null) {
                            hashMap = new java.util.HashMap<>();
                            this.mOemModeMap.put(split[0], hashMap);
                        }
                        if (!hashMap.containsKey(split[1])) {
                            if (split.length == 3) {
                                hashMap.put(split[1], new android.util.Pair<>(split[2], ""));
                            } else {
                                hashMap.put(split[1], new android.util.Pair<>(split[2], split[3]));
                            }
                        }
                    }
                }
            }
        }

        private java.lang.String applyOemOverrideFunction(java.lang.String str) {
            java.lang.String str2;
            if (str == null || this.mOemModeMap == null) {
                return str;
            }
            java.lang.String systemProperty = getSystemProperty(com.android.server.usb.UsbDeviceManager.BOOT_MODE_PROPERTY, "unknown");
            android.util.Slog.d(com.android.server.usb.UsbDeviceManager.TAG, "applyOemOverride usbfunctions=" + str + " bootmode=" + systemProperty);
            java.util.HashMap<java.lang.String, android.util.Pair<java.lang.String, java.lang.String>> hashMap = this.mOemModeMap.get(systemProperty);
            if (hashMap != null && !systemProperty.equals(com.android.server.usb.UsbDeviceManager.NORMAL_BOOT) && !systemProperty.equals("unknown")) {
                android.util.Pair<java.lang.String, java.lang.String> pair = hashMap.get(str);
                if (pair != null) {
                    android.util.Slog.d(com.android.server.usb.UsbDeviceManager.TAG, "OEM USB override: " + str + " ==> " + ((java.lang.String) pair.first) + " persist across reboot " + ((java.lang.String) pair.second));
                    if (!((java.lang.String) pair.second).equals("")) {
                        if (isAdbEnabled()) {
                            str2 = addFunction((java.lang.String) pair.second, com.android.server.integrity.AppIntegrityManagerServiceImpl.ADB_INSTALLER);
                        } else {
                            str2 = (java.lang.String) pair.second;
                        }
                        android.util.Slog.d(com.android.server.usb.UsbDeviceManager.TAG, "OEM USB override persisting: " + str2 + "in prop: " + getPersistProp(false));
                        setSystemProperty(getPersistProp(false), str2);
                    }
                    return (java.lang.String) pair.first;
                }
                if (isAdbEnabled()) {
                    setSystemProperty(getPersistProp(false), addFunction("none", com.android.server.integrity.AppIntegrityManagerServiceImpl.ADB_INSTALLER));
                } else {
                    setSystemProperty(getPersistProp(false), "none");
                }
            }
            return str;
        }

        private boolean waitForState(java.lang.String str) {
            java.lang.String str2 = null;
            for (int i = 0; i < 20; i++) {
                str2 = getSystemProperty(USB_STATE_PROPERTY, "");
                if (str.equals(str2)) {
                    return true;
                }
                android.os.SystemClock.sleep(50L);
            }
            android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "waitForState(" + str + ") FAILED: got " + str2);
            return false;
        }

        private void setUsbConfig(java.lang.String str) {
            setSystemProperty(USB_CONFIG_PROPERTY, str);
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        protected void setEnabledFunctions(long j, boolean z, int i) {
            boolean isUsbDataTransferActive = isUsbDataTransferActive(j);
            if (isUsbDataTransferActive != this.mUsbDataUnlocked) {
                this.mUsbDataUnlocked = isUsbDataTransferActive;
                updateUsbNotification(false);
                z = true;
            }
            long j2 = this.mCurrentFunctions;
            boolean z2 = this.mCurrentFunctionsApplied;
            if (trySetEnabledFunctions(j, z)) {
                return;
            }
            if (z2 && j2 != j) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Failsafe 1: Restoring previous USB functions.");
                if (trySetEnabledFunctions(j2, false)) {
                    return;
                }
            }
            android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Failsafe 2: Restoring default USB functions.");
            if (trySetEnabledFunctions(0L, false)) {
                return;
            }
            android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Failsafe 3: Restoring empty function list (with ADB if enabled).");
            if (trySetEnabledFunctions(0L, false)) {
                return;
            }
            android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Unable to set any USB functions!");
        }

        private boolean isNormalBoot() {
            java.lang.String systemProperty = getSystemProperty(com.android.server.usb.UsbDeviceManager.BOOT_MODE_PROPERTY, "unknown");
            return systemProperty.equals(com.android.server.usb.UsbDeviceManager.NORMAL_BOOT) || systemProperty.equals("unknown");
        }

        protected java.lang.String applyAdbFunction(java.lang.String str) {
            if (str == null) {
                str = "";
            }
            if (isAdbEnabled()) {
                return addFunction(str, com.android.server.integrity.AppIntegrityManagerServiceImpl.ADB_INSTALLER);
            }
            return removeFunction(str, com.android.server.integrity.AppIntegrityManagerServiceImpl.ADB_INSTALLER);
        }

        private boolean trySetEnabledFunctions(long j, boolean z) {
            java.lang.String str;
            if (j == 0) {
                str = null;
            } else {
                str = android.hardware.usb.UsbManager.usbFunctionsToString(j);
            }
            this.mCurrentFunctions = j;
            if (str == null || applyAdbFunction(str).equals("none")) {
                str = android.hardware.usb.UsbManager.usbFunctionsToString(getChargingFunctions());
            }
            java.lang.String applyAdbFunction = applyAdbFunction(str);
            java.lang.String applyOemOverrideFunction = applyOemOverrideFunction(applyAdbFunction);
            if (!isNormalBoot() && !this.mCurrentFunctionsStr.equals(applyAdbFunction)) {
                setSystemProperty(getPersistProp(true), applyAdbFunction);
            }
            if ((!applyAdbFunction.equals(applyOemOverrideFunction) && !this.mCurrentOemFunctions.equals(applyOemOverrideFunction)) || !this.mCurrentFunctionsStr.equals(applyAdbFunction) || !this.mCurrentFunctionsApplied || z) {
                this.mCurrentFunctionsStr = applyAdbFunction;
                this.mCurrentOemFunctions = applyOemOverrideFunction;
                this.mCurrentFunctionsApplied = false;
                setUsbConfig("none");
                if (!waitForState("none")) {
                    android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Failed to kick USB config");
                    return false;
                }
                setUsbConfig(applyOemOverrideFunction);
                if (this.mBootCompleted && (containsFunction(applyAdbFunction, "mtp") || containsFunction(applyAdbFunction, "ptp"))) {
                    updateUsbStateBroadcastIfNeeded(getAppliedFunctions(this.mCurrentFunctions));
                }
                if (!waitForState(applyOemOverrideFunction)) {
                    android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Failed to switch USB config to " + applyAdbFunction);
                    return false;
                }
                this.mCurrentFunctionsApplied = true;
            }
            return true;
        }

        private java.lang.String getPersistProp(boolean z) {
            java.lang.String systemProperty = getSystemProperty(com.android.server.usb.UsbDeviceManager.BOOT_MODE_PROPERTY, "unknown");
            if (systemProperty.equals(com.android.server.usb.UsbDeviceManager.NORMAL_BOOT) || systemProperty.equals("unknown")) {
                return "persist.sys.usb.config";
            }
            if (z) {
                return "persist.sys.usb." + systemProperty + ".func";
            }
            return "persist.sys.usb." + systemProperty + ".config";
        }

        private static java.lang.String addFunction(java.lang.String str, java.lang.String str2) {
            if ("none".equals(str)) {
                return str2;
            }
            if (!containsFunction(str, str2)) {
                if (str.length() > 0) {
                    str = str + ",";
                }
                return str + str2;
            }
            return str;
        }

        private static java.lang.String removeFunction(java.lang.String str, java.lang.String str2) {
            java.lang.String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                if (str2.equals(split[i])) {
                    split[i] = null;
                }
            }
            if (split.length == 1 && split[0] == null) {
                return "none";
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (java.lang.String str3 : split) {
                if (str3 != null) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(str3);
                }
            }
            return sb.toString();
        }

        static boolean containsFunction(java.lang.String str, java.lang.String str2) {
            int indexOf = str.indexOf(str2);
            if (indexOf < 0) {
                return false;
            }
            if (indexOf > 0 && str.charAt(indexOf - 1) != ',') {
                return false;
            }
            int length = indexOf + str2.length();
            if (length < str.length() && str.charAt(length) != ',') {
                return false;
            }
            return true;
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void setCurrentUsbFunctionsCb(long j, int i, int i2, long j2, boolean z) {
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void getUsbSpeedCb(int i) {
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void resetCb(int i) {
        }
    }

    private static final class UsbHandlerHal extends com.android.server.usb.UsbDeviceManager.UsbHandler {
        private static final int ENUMERATION_TIME_OUT_MS = 2000;
        protected static final java.lang.String GADGET_HAL_FQ_NAME = "android.hardware.usb.gadget@1.0::IUsbGadget";
        private static final int SET_FUNCTIONS_LEEWAY_MS = 500;
        private static final int SET_FUNCTIONS_TIMEOUT_MS = 3000;
        private static final int USB_GADGET_HAL_DEATH_COOKIE = 2000;
        private int mCurrentRequest;
        protected boolean mCurrentUsbFunctionsRequested;
        private final java.lang.Object mGadgetProxyLock;

        UsbHandlerHal(android.os.Looper looper, android.content.Context context, com.android.server.usb.UsbDeviceManager usbDeviceManager, com.android.server.usb.UsbAlsaManager usbAlsaManager, com.android.server.usb.UsbPermissionManager usbPermissionManager) {
            super(looper, context, usbDeviceManager, usbAlsaManager, usbPermissionManager);
            this.mGadgetProxyLock = new java.lang.Object();
            this.mCurrentRequest = 0;
            com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
            try {
                synchronized (this.mGadgetProxyLock) {
                    this.mCurrentFunctions = 0L;
                    this.mCurrentUsbFunctionsRequested = true;
                    this.mUsbSpeed = -1;
                    this.mCurrentGadgetHalVersion = 10;
                    updateUsbGadgetHalVersion();
                }
                updateState(android.os.FileUtils.readTextFile(new java.io.File(com.android.server.usb.UsbDeviceManager.STATE_PATH), 0, null).trim());
            } catch (java.util.NoSuchElementException e) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Usb gadget hal not found", e);
            } catch (java.lang.Exception e2) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Error initializing UsbHandler", e2);
            }
        }

        final class UsbGadgetDeathRecipient implements android.os.IHwBinder.DeathRecipient {
            UsbGadgetDeathRecipient() {
            }

            public void serviceDied(long j) {
                if (j == 2000) {
                    android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Usb Gadget hal service died cookie: " + j);
                    synchronized (com.android.server.usb.UsbDeviceManager.UsbHandlerHal.this.mGadgetProxyLock) {
                        com.android.server.usb.UsbDeviceManager.mUsbGadgetHal = null;
                    }
                }
            }
        }

        final class ServiceNotification extends android.hidl.manager.V1_0.IServiceNotification.Stub {
            ServiceNotification() {
            }

            @Override // android.hidl.manager.V1_0.IServiceNotification
            public void onRegistration(java.lang.String str, java.lang.String str2, boolean z) {
                android.util.Slog.i(com.android.server.usb.UsbDeviceManager.TAG, "Usb gadget hal service started " + str + " " + str2);
                if (!str.equals(com.android.server.usb.UsbDeviceManager.UsbHandlerHal.GADGET_HAL_FQ_NAME)) {
                    android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "fqName does not match");
                } else {
                    com.android.server.usb.UsbDeviceManager.UsbHandlerHal.this.sendMessage(18, z);
                }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler, android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 14:
                    setEnabledFunctions(0L, false, com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet());
                    return;
                case 15:
                    int incrementAndGet = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Set functions timed out! no reply from usb hal ,operationId:" + incrementAndGet);
                    if (message.arg1 != 1) {
                        setEnabledFunctions(this.mScreenUnlockedFunctions, false, incrementAndGet);
                        return;
                    }
                    return;
                case 16:
                    android.util.Slog.i(com.android.server.usb.UsbDeviceManager.TAG, "processing MSG_GET_CURRENT_USB_FUNCTIONS");
                    this.mCurrentUsbFunctionsReceived = true;
                    int i = message.arg2;
                    if (this.mCurrentUsbFunctionsRequested) {
                        android.util.Slog.i(com.android.server.usb.UsbDeviceManager.TAG, "updating mCurrentFunctions");
                        this.mCurrentFunctions = ((java.lang.Long) message.obj).longValue() & (-2);
                        android.util.Slog.i(com.android.server.usb.UsbDeviceManager.TAG, "mCurrentFunctions:" + this.mCurrentFunctions + "applied:" + message.arg1);
                        this.mCurrentFunctionsApplied = message.arg1 == 1;
                    }
                    finishBoot(i);
                    return;
                case 17:
                    int incrementAndGet2 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    if (message.arg1 != 1) {
                        setEnabledFunctions(this.mScreenUnlockedFunctions, false, incrementAndGet2);
                        return;
                    }
                    return;
                case 18:
                    boolean z = message.arg1 == 1;
                    int incrementAndGet3 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    synchronized (this.mGadgetProxyLock) {
                        try {
                            com.android.server.usb.UsbDeviceManager.mUsbGadgetHal = com.android.server.usb.hal.gadget.UsbGadgetHalInstance.getInstance(this.mUsbDeviceManager, null);
                            if (!this.mCurrentFunctionsApplied && !z) {
                                setEnabledFunctions(this.mCurrentFunctions, false, incrementAndGet3);
                            }
                        } catch (java.util.NoSuchElementException e) {
                            android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Usb gadget hal not found", e);
                        }
                    }
                    return;
                case 19:
                    int incrementAndGet4 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    synchronized (this.mGadgetProxyLock) {
                        if (com.android.server.usb.UsbDeviceManager.mUsbGadgetHal == null) {
                            android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "reset Usb Gadget mUsbGadgetHal is null");
                            return;
                        }
                        try {
                            removeMessages(8);
                            if (this.mConfigured) {
                                this.mResetUsbGadgetDisableDebounce = true;
                            }
                            com.android.server.usb.UsbDeviceManager.mUsbGadgetHal.reset(incrementAndGet4);
                        } catch (java.lang.Exception e2) {
                            android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "reset Usb Gadget failed", e2);
                            this.mResetUsbGadgetDisableDebounce = false;
                        }
                        return;
                    }
                case 20:
                case 21:
                default:
                    super.handleMessage(message);
                    return;
                case 22:
                    int incrementAndGet5 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    if (com.android.server.usb.UsbDeviceManager.mUsbGadgetHal == null) {
                        android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "mGadgetHal is null, operationId:" + incrementAndGet5);
                        return;
                    }
                    try {
                        com.android.server.usb.UsbDeviceManager.mUsbGadgetHal.getUsbSpeed(incrementAndGet5);
                        return;
                    } catch (java.lang.Exception e3) {
                        android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "get UsbSpeed failed", e3);
                        return;
                    }
                case 23:
                    if (com.android.server.usb.UsbDeviceManager.mUsbGadgetHal == null) {
                        android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "mUsbGadgetHal is null");
                        return;
                    }
                    try {
                        this.mCurrentGadgetHalVersion = com.android.server.usb.UsbDeviceManager.mUsbGadgetHal.getGadgetHalVersion();
                        return;
                    } catch (android.os.RemoteException e4) {
                        android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "update Usb gadget version failed", e4);
                        return;
                    }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void setCurrentUsbFunctionsCb(long j, int i, int i2, long j2, boolean z) {
            if (this.mCurrentRequest != i2 || !hasMessages(15) || j2 != j) {
                return;
            }
            removeMessages(15);
            android.util.Slog.i(com.android.server.usb.UsbDeviceManager.TAG, "notifyCurrentFunction request:" + i2 + " status:" + i);
            if (i == 0) {
                this.mCurrentFunctionsApplied = true;
            } else if (!z) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Setting default fuctions");
                sendEmptyMessage(14);
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void getUsbSpeedCb(int i) {
            this.mUsbSpeed = i;
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void resetCb(int i) {
            if (i != 0) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "resetCb fail");
            }
        }

        private void setUsbConfig(long j, boolean z, int i) {
            java.lang.String str = com.android.server.usb.UsbDeviceManager.TAG;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("setUsbConfig(");
            sb.append(j);
            sb.append(") request:");
            int i2 = this.mCurrentRequest + 1;
            this.mCurrentRequest = i2;
            sb.append(i2);
            android.util.Slog.d(str, sb.toString());
            removeMessages(17);
            removeMessages(15);
            removeMessages(14);
            synchronized (this.mGadgetProxyLock) {
                if (com.android.server.usb.UsbDeviceManager.mUsbGadgetHal != null) {
                    try {
                        if ((1 & j) != 0) {
                            ((android.debug.AdbManagerInternal) com.android.server.LocalServices.getService(android.debug.AdbManagerInternal.class)).startAdbdForTransport((byte) 0);
                        } else {
                            ((android.debug.AdbManagerInternal) com.android.server.LocalServices.getService(android.debug.AdbManagerInternal.class)).stopAdbdForTransport((byte) 0);
                        }
                        com.android.server.usb.UsbDeviceManager.mUsbGadgetHal.setCurrentUsbFunctions(this.mCurrentRequest, j, z, 2500, i);
                        sendMessageDelayed(15, z, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS);
                        if (this.mConnected) {
                            sendMessageDelayed(17, z, 5000L);
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Remoteexception while calling setCurrentUsbFunctions", e);
                    }
                    return;
                }
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "setUsbConfig mUsbGadgetHal is null");
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        protected void setEnabledFunctions(long j, boolean z, int i) {
            if (this.mCurrentGadgetHalVersion < 12 && (1024 & j) != 0) {
                android.util.Slog.e(com.android.server.usb.UsbDeviceManager.TAG, "Could not set unsupported function for the GadgetHal");
                return;
            }
            if (this.mCurrentFunctions != j || !this.mCurrentFunctionsApplied || z) {
                android.util.Slog.i(com.android.server.usb.UsbDeviceManager.TAG, "Setting USB config to " + android.hardware.usb.UsbManager.usbFunctionsToString(j));
                this.mCurrentFunctions = j;
                this.mCurrentFunctionsApplied = false;
                this.mCurrentUsbFunctionsRequested = false;
                boolean z2 = j == 0;
                long appliedFunctions = getAppliedFunctions(j);
                setUsbConfig(appliedFunctions, z2, i);
                if (this.mBootCompleted && isUsbDataTransferActive(appliedFunctions)) {
                    updateUsbStateBroadcastIfNeeded(appliedFunctions);
                }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void handlerInitDone(int i) {
            com.android.server.usb.UsbDeviceManager.mUsbGadgetHal.getCurrentUsbFunctions(i);
        }
    }

    public android.hardware.usb.UsbAccessory getCurrentAccessory() {
        return this.mHandler.getCurrentAccessory();
    }

    public android.os.ParcelFileDescriptor openAccessory(android.hardware.usb.UsbAccessory usbAccessory, com.android.server.usb.UsbUserPermissionManager usbUserPermissionManager, int i, int i2) {
        android.hardware.usb.UsbAccessory currentAccessory = this.mHandler.getCurrentAccessory();
        if (currentAccessory == null) {
            throw new java.lang.IllegalArgumentException("no accessory attached");
        }
        if (!currentAccessory.equals(usbAccessory)) {
            throw new java.lang.IllegalArgumentException(usbAccessory.toString() + " does not match current accessory " + currentAccessory);
        }
        usbUserPermissionManager.checkPermission(usbAccessory, i, i2);
        return nativeOpenAccessory();
    }

    public long getCurrentFunctions() {
        return this.mHandler.getEnabledFunctions();
    }

    public int getCurrentUsbSpeed() {
        return this.mHandler.getUsbSpeed();
    }

    public int getGadgetHalVersion() {
        return this.mHandler.getGadgetHalVersion();
    }

    public void setCurrentUsbFunctionsCb(long j, int i, int i2, long j2, boolean z) {
        this.mHandler.setCurrentUsbFunctionsCb(j, i, i2, j2, z);
    }

    public void getCurrentUsbFunctionsCb(long j, int i) {
        this.mHandler.sendMessage(16, java.lang.Long.valueOf(j), i == 2);
    }

    public void getUsbSpeedCb(int i) {
        this.mHandler.getUsbSpeedCb(i);
    }

    public void resetCb(int i) {
        this.mHandler.resetCb(i);
    }

    public android.os.ParcelFileDescriptor getControlFd(long j) {
        java.io.FileDescriptor fileDescriptor = this.mControlFds.get(java.lang.Long.valueOf(j));
        if (fileDescriptor == null) {
            return null;
        }
        try {
            return android.os.ParcelFileDescriptor.dup(fileDescriptor);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Could not dup fd for " + j);
            return null;
        }
    }

    public long getScreenUnlockedFunctions() {
        return this.mHandler.getScreenUnlockedFunctions();
    }

    public void setCurrentFunctions(long j, int i) {
        if (j == 0) {
            com.android.internal.logging.MetricsLogger.action(this.mContext, 1275);
        } else if (j == 4) {
            com.android.internal.logging.MetricsLogger.action(this.mContext, 1276);
        } else if (j == 16) {
            com.android.internal.logging.MetricsLogger.action(this.mContext, 1277);
        } else if (j == 8) {
            com.android.internal.logging.MetricsLogger.action(this.mContext, 1279);
        } else if (j == 32) {
            com.android.internal.logging.MetricsLogger.action(this.mContext, 1278);
        } else if (j == 2) {
            com.android.internal.logging.MetricsLogger.action(this.mContext, 1280);
        }
        this.mHandler.sendMessage(2, java.lang.Long.valueOf(j), i);
    }

    public void setScreenUnlockedFunctions(long j) {
        this.mHandler.sendMessage(12, java.lang.Long.valueOf(j));
    }

    public void resetUsbGadget() {
        this.mHandler.sendMessage(19, (java.lang.Object) null);
    }

    private void onAdbEnabled(boolean z) {
        this.mHandler.sendMessage(1, z, sUsbOperationCount.incrementAndGet());
    }

    public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        if (this.mHandler != null) {
            this.mHandler.dump(dualDumpOutputStream, "handler", 1146756268033L);
            sEventLogger.dump(new com.android.server.usb.DualOutputStreamDumpSink(dualDumpOutputStream, 1138166333457L));
        }
        dualDumpOutputStream.end(start);
    }
}
