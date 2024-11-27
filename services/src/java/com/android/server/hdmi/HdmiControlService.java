package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class HdmiControlService extends com.android.server.SystemService {
    static final int DEVICE_CLEANUP_TIMEOUT = 5000;
    static final int INITIATED_BY_BOOT_UP = 1;
    static final int INITIATED_BY_ENABLE_CEC = 0;
    static final int INITIATED_BY_ENABLE_EARC = 6;
    static final int INITIATED_BY_HOTPLUG = 4;
    static final int INITIATED_BY_SCREEN_ON = 2;
    static final int INITIATED_BY_SOUNDBAR_MODE = 5;
    static final int INITIATED_BY_WAKE_UP_MESSAGE = 3;
    static final java.lang.String PERMISSION = "android.permission.HDMI_CEC";
    static final int STANDBY_SCREEN_OFF = 0;
    static final int STANDBY_SHUTDOWN = 1;
    private static final java.lang.String TAG = "HdmiControlService";
    static final int WAKE_UP_BOOT_UP = 1;
    static final int WAKE_UP_SCREEN_ON = 0;
    private com.android.server.hdmi.HdmiControlService.AbsoluteVolumeChangedListener mAbsoluteVolumeChangedListener;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private int mActivePortId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected final com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource mActiveSource;
    private boolean mAddressAllocated;
    private com.android.server.hdmi.HdmiCecAtomWriter mAtomWriter;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Map<android.media.AudioDeviceAttributes, java.lang.Integer> mAudioDeviceVolumeBehaviors;

    @android.annotation.Nullable
    private com.android.server.hdmi.AudioDeviceVolumeManagerWrapper mAudioDeviceVolumeManager;

    @android.annotation.Nullable
    private com.android.server.hdmi.AudioManagerWrapper mAudioManager;

    @android.annotation.Nullable
    private com.android.server.hdmi.HdmiCecController mCecController;
    private final java.util.List<java.lang.Integer> mCecLocalDevices;
    private com.android.server.hdmi.CecMessageBuffer mCecMessageBuffer;
    private int mCecVersion;

    @android.annotation.Nullable
    private com.android.server.hdmi.DeviceConfigWrapper mDeviceConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.hdmi.HdmiControlService.DeviceEventListenerRecord> mDeviceEventListenerRecords;

    @android.annotation.Nullable
    private android.hardware.display.DisplayManager mDisplayManager;

    @android.annotation.Nullable
    private android.hardware.hdmi.IHdmiControlCallback mDisplayStatusCallback;

    @android.annotation.Nullable
    private com.android.server.hdmi.HdmiEarcController mEarcController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mEarcEnabled;

    @android.annotation.Nullable
    private com.android.server.hdmi.HdmiEarcLocalDevice mEarcLocalDevice;
    private int mEarcPortId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    private boolean mEarcSupported;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean mEarcTxFeatureFlagEnabled;
    private final android.os.Handler mHandler;
    private com.android.server.hdmi.HdmiCecConfig mHdmiCecConfig;
    private com.android.server.hdmi.HdmiCecNetwork mHdmiCecNetwork;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, android.os.RemoteCallbackList<android.hardware.hdmi.IHdmiCecSettingChangeListener>> mHdmiCecSettingChangeListenerRecords;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mHdmiCecVolumeControl;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.os.RemoteCallbackList<android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener> mHdmiCecVolumeControlFeatureListenerRecords;
    private final com.android.server.hdmi.HdmiControlService.HdmiControlBroadcastReceiver mHdmiControlBroadcastReceiver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mHdmiControlEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.hdmi.HdmiControlService.HdmiControlStatusChangeListenerRecord> mHdmiControlStatusChangeListenerRecords;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.hdmi.HdmiControlService.HotplugEventListenerRecord> mHotplugEventListenerRecords;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.hdmi.HdmiControlService.InputChangeListenerRecord mInputChangeListenerRecord;

    @android.annotation.Nullable
    private android.os.Looper mIoLooper;
    private final android.os.HandlerThread mIoThread;
    private boolean mIsCecAvailable;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private int mLastInputMhl;
    private final java.lang.Object mLock;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private java.lang.String mMenuLanguage;

    @android.annotation.Nullable
    private com.android.server.hdmi.HdmiMhlControllerStub mMhlController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.hardware.hdmi.HdmiDeviceInfo> mMhlDevices;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mMhlInputChangeEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.hdmi.HdmiControlService.HdmiMhlVendorCommandListenerRecord> mMhlVendorCommandListenerRecords;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled;

    @android.annotation.Nullable
    private android.hardware.hdmi.IHdmiControlCallback mOtpCallbackPendingAddressAllocation;

    @android.annotation.Nullable
    private com.android.server.hdmi.PowerManagerWrapper mPowerManager;

    @android.annotation.Nullable
    private com.android.server.hdmi.PowerManagerInternalWrapper mPowerManagerInternal;
    private com.android.server.hdmi.HdmiCecPowerStatusController mPowerStatusController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mProhibitMode;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.hdmi.HdmiControlService.HdmiRecordListenerRecord mRecordListenerRecord;
    private final com.android.server.hdmi.SelectRequestBuffer mSelectRequestBuffer;
    private final java.util.concurrent.Executor mServiceThreadExecutor;
    private com.android.server.hdmi.HdmiCecConfig.SettingChangeListener mSettingChangeListener;
    private final com.android.server.hdmi.HdmiControlService.SettingsObserver mSettingsObserver;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean mSoundbarModeFeatureFlagEnabled;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean mStandbyMessageReceived;
    private int mStreamMusicMaxVolume;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSystemAudioActivated;
    private final java.util.ArrayList<com.android.server.hdmi.HdmiControlService.SystemAudioModeChangeListenerRecord> mSystemAudioModeChangeListenerRecords;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean mTransitionFromArcToEarcTxEnabled;

    @android.annotation.Nullable
    private android.media.tv.TvInputManager mTvInputManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.hdmi.HdmiControlService.VendorCommandListenerRecord> mVendorCommandListenerRecords;

    @android.annotation.Nullable
    private com.android.server.hdmi.WakeLockWrapper mWakeLock;

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private boolean mWakeUpMessageReceived;
    private static final java.util.Locale HONG_KONG = new java.util.Locale("zh", "HK");
    private static final java.util.Locale MACAU = new java.util.Locale("zh", "MO");
    private static final java.util.Map<java.lang.String, java.lang.String> sTerminologyToBibliographicMap = createsTerminologyToBibliographicMap();

    @com.android.internal.annotations.VisibleForTesting
    static final android.media.AudioDeviceAttributes AUDIO_OUTPUT_DEVICE_HDMI = new android.media.AudioDeviceAttributes(2, 9, "");

    @com.android.internal.annotations.VisibleForTesting
    static final android.media.AudioDeviceAttributes AUDIO_OUTPUT_DEVICE_HDMI_ARC = new android.media.AudioDeviceAttributes(2, 10, "");
    static final android.media.AudioDeviceAttributes AUDIO_OUTPUT_DEVICE_HDMI_EARC = new android.media.AudioDeviceAttributes(2, 29, "");
    private static final java.util.List<android.media.AudioDeviceAttributes> AVB_AUDIO_OUTPUT_DEVICES = java.util.List.of(AUDIO_OUTPUT_DEVICE_HDMI, AUDIO_OUTPUT_DEVICE_HDMI_ARC, AUDIO_OUTPUT_DEVICE_HDMI_EARC);
    private static final java.util.List<android.media.AudioDeviceAttributes> TV_AVB_AUDIO_OUTPUT_DEVICES = java.util.List.of(AUDIO_OUTPUT_DEVICE_HDMI_ARC, AUDIO_OUTPUT_DEVICE_HDMI_EARC);
    private static final java.util.List<android.media.AudioDeviceAttributes> PLAYBACK_AVB_AUDIO_OUTPUT_DEVICES = java.util.List.of(AUDIO_OUTPUT_DEVICE_HDMI);
    private static final java.util.List<java.lang.Integer> ABSOLUTE_VOLUME_BEHAVIORS = java.util.List.of(3, 5);
    private static final java.util.List<java.lang.Integer> FULL_AND_ABSOLUTE_VOLUME_BEHAVIORS = java.util.List.of(1, 3, 5);

    @com.android.internal.annotations.VisibleForTesting
    static final android.media.AudioAttributes STREAM_MUSIC_ATTRIBUTES = new android.media.AudioAttributes.Builder().setLegacyStreamType(3).build();

    interface DevicePollingCallback {
        void onPollingFinished(java.util.List<java.lang.Integer> list);
    }

    interface SendMessageCallback {
        void onSendCompleted(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WakeReason {
    }

    private static java.util.Map<java.lang.String, java.lang.String> createsTerminologyToBibliographicMap() {
        java.util.HashMap hashMap = new java.util.HashMap();
        hashMap.put("sqi", "alb");
        hashMap.put("hye", "arm");
        hashMap.put("eus", "baq");
        hashMap.put("mya", "bur");
        hashMap.put("ces", "cze");
        hashMap.put("nld", "dut");
        hashMap.put("kat", "geo");
        hashMap.put("deu", "ger");
        hashMap.put("ell", "gre");
        hashMap.put("fra", "fre");
        hashMap.put("isl", "ice");
        hashMap.put("mkd", "mac");
        hashMap.put("mri", "mao");
        hashMap.put("msa", "may");
        hashMap.put("fas", "per");
        hashMap.put("ron", "rum");
        hashMap.put("slk", "slo");
        hashMap.put("bod", "tib");
        hashMap.put("cym", "wel");
        return java.util.Collections.unmodifiableMap(hashMap);
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String localeToMenuLanguage(java.util.Locale locale) {
        if (locale.equals(java.util.Locale.TAIWAN) || locale.equals(HONG_KONG) || locale.equals(MACAU)) {
            return "chi";
        }
        java.lang.String iSO3Language = locale.getISO3Language();
        if (sTerminologyToBibliographicMap.containsKey(iSO3Language)) {
            return sTerminologyToBibliographicMap.get(iSO3Language);
        }
        return iSO3Language;
    }

    java.util.concurrent.Executor getServiceThreadExecutor() {
        return this.mServiceThreadExecutor;
    }

    private class HdmiControlBroadcastReceiver extends android.content.BroadcastReceiver {
        private HdmiControlBroadcastReceiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            char c;
            com.android.server.hdmi.HdmiControlService.this.assertRunOnServiceThread();
            boolean contains = android.os.SystemProperties.get(com.android.server.power.ShutdownThread.SHUTDOWN_ACTION_PROPERTY).contains("1");
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case -2128145023:
                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1454123155:
                    if (action.equals("android.intent.action.SCREEN_ON")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 158859398:
                    if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1947666138:
                    if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
                        c = 3;
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
                    if (com.android.server.hdmi.HdmiControlService.this.isPowerOnOrTransient() && !contains) {
                        com.android.server.hdmi.HdmiControlService.this.onStandby(0);
                        break;
                    }
                    break;
                case 1:
                    if (com.android.server.hdmi.HdmiControlService.this.isPowerStandbyOrTransient()) {
                        com.android.server.hdmi.HdmiControlService.this.onWakeUp(0);
                        break;
                    }
                    break;
                case 2:
                    java.lang.String localeToMenuLanguage = com.android.server.hdmi.HdmiControlService.localeToMenuLanguage(java.util.Locale.getDefault());
                    if (!com.android.server.hdmi.HdmiControlService.this.mMenuLanguage.equals(localeToMenuLanguage)) {
                        com.android.server.hdmi.HdmiControlService.this.onLanguageChanged(localeToMenuLanguage);
                        break;
                    }
                    break;
                case 3:
                    if (com.android.server.hdmi.HdmiControlService.this.isPowerOnOrTransient() && !contains) {
                        com.android.server.hdmi.HdmiControlService.this.onStandby(1);
                        break;
                    }
                    break;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    HdmiControlService(android.content.Context context, java.util.List<java.lang.Integer> list, com.android.server.hdmi.AudioManagerWrapper audioManagerWrapper, com.android.server.hdmi.AudioDeviceVolumeManagerWrapper audioDeviceVolumeManagerWrapper) {
        super(context);
        this.mServiceThreadExecutor = new java.util.concurrent.Executor() { // from class: com.android.server.hdmi.HdmiControlService.1
            @Override // java.util.concurrent.Executor
            public void execute(java.lang.Runnable runnable) {
                com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(runnable);
            }
        };
        this.mActiveSource = new com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource();
        this.mSystemAudioActivated = false;
        this.mAudioDeviceVolumeBehaviors = new java.util.HashMap();
        this.mIoThread = new android.os.HandlerThread("Hdmi Control Io Thread");
        this.mLock = new java.lang.Object();
        this.mHdmiControlStatusChangeListenerRecords = new java.util.ArrayList<>();
        this.mHdmiCecVolumeControlFeatureListenerRecords = new android.os.RemoteCallbackList<>();
        this.mHotplugEventListenerRecords = new java.util.ArrayList<>();
        this.mDeviceEventListenerRecords = new java.util.ArrayList<>();
        this.mVendorCommandListenerRecords = new java.util.ArrayList<>();
        this.mHdmiCecSettingChangeListenerRecords = new android.util.ArrayMap<>();
        this.mEarcPortId = -1;
        this.mSystemAudioModeChangeListenerRecords = new java.util.ArrayList<>();
        this.mHandler = new android.os.Handler();
        this.mHdmiControlBroadcastReceiver = new com.android.server.hdmi.HdmiControlService.HdmiControlBroadcastReceiver();
        this.mDisplayStatusCallback = null;
        this.mOtpCallbackPendingAddressAllocation = null;
        this.mMenuLanguage = localeToMenuLanguage(java.util.Locale.getDefault());
        this.mStandbyMessageReceived = false;
        this.mWakeUpMessageReceived = false;
        this.mSoundbarModeFeatureFlagEnabled = false;
        this.mEarcTxFeatureFlagEnabled = false;
        this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled = false;
        this.mTransitionFromArcToEarcTxEnabled = false;
        this.mActivePortId = -1;
        this.mMhlVendorCommandListenerRecords = new java.util.ArrayList<>();
        this.mLastInputMhl = -1;
        this.mAddressAllocated = false;
        this.mIsCecAvailable = false;
        this.mAtomWriter = new com.android.server.hdmi.HdmiCecAtomWriter();
        this.mSelectRequestBuffer = new com.android.server.hdmi.SelectRequestBuffer();
        this.mSettingChangeListener = new com.android.server.hdmi.HdmiControlService.AnonymousClass27();
        this.mCecLocalDevices = list;
        this.mSettingsObserver = new com.android.server.hdmi.HdmiControlService.SettingsObserver(this.mHandler);
        this.mHdmiCecConfig = new com.android.server.hdmi.HdmiCecConfig(context);
        this.mDeviceConfig = new com.android.server.hdmi.DeviceConfigWrapper();
        this.mAudioManager = audioManagerWrapper;
        this.mAudioDeviceVolumeManager = audioDeviceVolumeManagerWrapper;
    }

    public HdmiControlService(android.content.Context context) {
        super(context);
        this.mServiceThreadExecutor = new java.util.concurrent.Executor() { // from class: com.android.server.hdmi.HdmiControlService.1
            @Override // java.util.concurrent.Executor
            public void execute(java.lang.Runnable runnable) {
                com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(runnable);
            }
        };
        this.mActiveSource = new com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource();
        this.mSystemAudioActivated = false;
        this.mAudioDeviceVolumeBehaviors = new java.util.HashMap();
        this.mIoThread = new android.os.HandlerThread("Hdmi Control Io Thread");
        this.mLock = new java.lang.Object();
        this.mHdmiControlStatusChangeListenerRecords = new java.util.ArrayList<>();
        this.mHdmiCecVolumeControlFeatureListenerRecords = new android.os.RemoteCallbackList<>();
        this.mHotplugEventListenerRecords = new java.util.ArrayList<>();
        this.mDeviceEventListenerRecords = new java.util.ArrayList<>();
        this.mVendorCommandListenerRecords = new java.util.ArrayList<>();
        this.mHdmiCecSettingChangeListenerRecords = new android.util.ArrayMap<>();
        this.mEarcPortId = -1;
        this.mSystemAudioModeChangeListenerRecords = new java.util.ArrayList<>();
        this.mHandler = new android.os.Handler();
        this.mHdmiControlBroadcastReceiver = new com.android.server.hdmi.HdmiControlService.HdmiControlBroadcastReceiver();
        this.mDisplayStatusCallback = null;
        this.mOtpCallbackPendingAddressAllocation = null;
        this.mMenuLanguage = localeToMenuLanguage(java.util.Locale.getDefault());
        this.mStandbyMessageReceived = false;
        this.mWakeUpMessageReceived = false;
        this.mSoundbarModeFeatureFlagEnabled = false;
        this.mEarcTxFeatureFlagEnabled = false;
        this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled = false;
        this.mTransitionFromArcToEarcTxEnabled = false;
        this.mActivePortId = -1;
        this.mMhlVendorCommandListenerRecords = new java.util.ArrayList<>();
        this.mLastInputMhl = -1;
        this.mAddressAllocated = false;
        this.mIsCecAvailable = false;
        this.mAtomWriter = new com.android.server.hdmi.HdmiCecAtomWriter();
        this.mSelectRequestBuffer = new com.android.server.hdmi.SelectRequestBuffer();
        this.mSettingChangeListener = new com.android.server.hdmi.HdmiControlService.AnonymousClass27();
        this.mCecLocalDevices = readDeviceTypes();
        this.mSettingsObserver = new com.android.server.hdmi.HdmiControlService.SettingsObserver(this.mHandler);
        this.mHdmiCecConfig = new com.android.server.hdmi.HdmiCecConfig(context);
        this.mDeviceConfig = new com.android.server.hdmi.DeviceConfigWrapper();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.List<android.sysprop.HdmiProperties.cec_device_types_values> getCecDeviceTypes() {
        return android.sysprop.HdmiProperties.cec_device_types();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.List<java.lang.Integer> getDeviceTypes() {
        return android.sysprop.HdmiProperties.device_type();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.List<java.lang.Integer> readDeviceTypes() {
        java.util.List<android.sysprop.HdmiProperties.cec_device_types_values> cecDeviceTypes = getCecDeviceTypes();
        if (!cecDeviceTypes.isEmpty()) {
            if (cecDeviceTypes.contains(null)) {
                android.util.Slog.w(TAG, "Error parsing ro.hdmi.cec_device_types: " + android.os.SystemProperties.get("ro.hdmi.cec_device_types"));
            }
            return (java.util.List) cecDeviceTypes.stream().map(new java.util.function.Function() { // from class: com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Integer enumToIntDeviceType;
                    enumToIntDeviceType = com.android.server.hdmi.HdmiControlService.enumToIntDeviceType((android.sysprop.HdmiProperties.cec_device_types_values) obj);
                    return enumToIntDeviceType;
                }
            }).filter(new java.util.function.Predicate() { // from class: com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return java.util.Objects.nonNull((java.lang.Integer) obj);
                }
            }).collect(java.util.stream.Collectors.toList());
        }
        java.util.List<java.lang.Integer> deviceTypes = getDeviceTypes();
        if (deviceTypes.contains(null)) {
            android.util.Slog.w(TAG, "Error parsing ro.hdmi.device_type: " + android.os.SystemProperties.get("ro.hdmi.device_type"));
        }
        return (java.util.List) deviceTypes.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return java.util.Objects.nonNull((java.lang.Integer) obj);
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static java.lang.Integer enumToIntDeviceType(@android.annotation.Nullable android.sysprop.HdmiProperties.cec_device_types_values cec_device_types_valuesVar) {
        if (cec_device_types_valuesVar == null) {
            return null;
        }
        switch (com.android.server.hdmi.HdmiControlService.AnonymousClass30.$SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[cec_device_types_valuesVar.ordinal()]) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                android.util.Slog.w(TAG, "Unrecognized device type in ro.hdmi.cec_device_types: " + cec_device_types_valuesVar.getPropValue());
                break;
        }
        return null;
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$30, reason: invalid class name */
    static /* synthetic */ class AnonymousClass30 {
        static final /* synthetic */ int[] $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values = new int[android.sysprop.HdmiProperties.cec_device_types_values.values().length];

        static {
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[android.sysprop.HdmiProperties.cec_device_types_values.TV.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[android.sysprop.HdmiProperties.cec_device_types_values.RECORDING_DEVICE.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[android.sysprop.HdmiProperties.cec_device_types_values.RESERVED.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[android.sysprop.HdmiProperties.cec_device_types_values.TUNER.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[android.sysprop.HdmiProperties.cec_device_types_values.PLAYBACK_DEVICE.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[android.sysprop.HdmiProperties.cec_device_types_values.AUDIO_SYSTEM.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[android.sysprop.HdmiProperties.cec_device_types_values.PURE_CEC_SWITCH.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[android.sysprop.HdmiProperties.cec_device_types_values.VIDEO_PROCESSOR.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
        }
    }

    protected static java.util.List<java.lang.Integer> getIntList(java.lang.String str) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.text.TextUtils.SimpleStringSplitter simpleStringSplitter = new android.text.TextUtils.SimpleStringSplitter(',');
        simpleStringSplitter.setString(str);
        java.util.Iterator<java.lang.String> it = simpleStringSplitter.iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            try {
                arrayList.add(java.lang.Integer.valueOf(java.lang.Integer.parseInt(next)));
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.w(TAG, "Can't parseInt: " + next);
            }
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        initService();
        publishBinderService("hdmi_control", new com.android.server.hdmi.HdmiControlService.BinderService());
        if (this.mCecController != null) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
            intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
            getContext().registerReceiver(this.mHdmiControlBroadcastReceiver, intentFilter);
            registerContentObserver();
        }
        this.mMhlController.setOption(104, 1);
    }

    @com.android.internal.annotations.VisibleForTesting
    void initService() {
        if (this.mIoLooper == null) {
            this.mIoThread.start();
            this.mIoLooper = this.mIoThread.getLooper();
        }
        if (this.mPowerStatusController == null) {
            this.mPowerStatusController = new com.android.server.hdmi.HdmiCecPowerStatusController(this);
        }
        this.mPowerStatusController.setPowerStatus(getInitialPowerStatus());
        setProhibitMode(false);
        this.mHdmiControlEnabled = this.mHdmiCecConfig.getIntValue("hdmi_cec_enabled");
        this.mSoundbarModeFeatureFlagEnabled = this.mDeviceConfig.getBoolean("enable_soundbar_mode", true);
        this.mEarcTxFeatureFlagEnabled = this.mDeviceConfig.getBoolean("enable_earc_tx", true);
        this.mTransitionFromArcToEarcTxEnabled = this.mDeviceConfig.getBoolean("transition_arc_to_earc_tx", true);
        this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled = this.mDeviceConfig.getBoolean("enable_numeric_soundbar_volume_ui_on_tv", true);
        synchronized (this.mLock) {
            try {
                this.mEarcEnabled = this.mHdmiCecConfig.getIntValue("earc_enabled") == 1;
                if (isTvDevice()) {
                    this.mEarcEnabled &= this.mEarcTxFeatureFlagEnabled;
                }
            } finally {
            }
        }
        setHdmiCecVolumeControlEnabledInternal(getHdmiCecConfig().getIntValue("volume_control_enabled"));
        this.mMhlInputChangeEnabled = readBooleanSetting("mhl_input_switching_enabled", true);
        if (this.mCecMessageBuffer == null) {
            this.mCecMessageBuffer = new com.android.server.hdmi.CecMessageBuffer(this);
        }
        if (this.mCecController == null) {
            this.mCecController = com.android.server.hdmi.HdmiCecController.create(this, getAtomWriter());
        }
        if (this.mCecController == null) {
            android.util.Slog.i(TAG, "Device does not support HDMI-CEC.");
            return;
        }
        if (this.mMhlController == null) {
            this.mMhlController = com.android.server.hdmi.HdmiMhlControllerStub.create(this);
        }
        if (!this.mMhlController.isReady()) {
            android.util.Slog.i(TAG, "Device does not support MHL-control.");
        }
        if (this.mEarcController == null) {
            this.mEarcController = com.android.server.hdmi.HdmiEarcController.create(this);
        }
        if (this.mEarcController == null) {
            android.util.Slog.i(TAG, "Device does not support eARC.");
        }
        this.mHdmiCecNetwork = new com.android.server.hdmi.HdmiCecNetwork(this, this.mCecController, this.mMhlController);
        if (!isCecControlEnabled()) {
            this.mCecController.enableCec(false);
        } else {
            initializeCec(1);
        }
        synchronized (this.mLock) {
            this.mMhlDevices = java.util.Collections.emptyList();
        }
        this.mHdmiCecNetwork.initPortInfo();
        java.util.List<android.hardware.hdmi.HdmiPortInfo> portInfo = getPortInfo();
        synchronized (this.mLock) {
            try {
                this.mEarcSupported = false;
                java.util.Iterator<android.hardware.hdmi.HdmiPortInfo> it = portInfo.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    android.hardware.hdmi.HdmiPortInfo next = it.next();
                    boolean isEarcSupported = next.isEarcSupported();
                    if (isEarcSupported && this.mEarcSupported) {
                        android.util.Slog.e(TAG, "HDMI eARC supported on more than 1 port.");
                        this.mEarcSupported = false;
                        this.mEarcPortId = -1;
                        break;
                    } else if (isEarcSupported) {
                        this.mEarcPortId = next.getId();
                        this.mEarcSupported = isEarcSupported;
                    }
                }
                this.mEarcSupported &= this.mEarcController != null;
            } finally {
            }
        }
        if (isEarcSupported()) {
            if (isEarcEnabled()) {
                initializeEarc(1);
            } else {
                setEarcEnabledInHal(false, false);
            }
        }
        this.mHdmiCecConfig.registerChangeListener("hdmi_cec_enabled", new com.android.server.hdmi.HdmiCecConfig.SettingChangeListener() { // from class: com.android.server.hdmi.HdmiControlService.2
            @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
            public void onChange(java.lang.String str) {
                com.android.server.hdmi.HdmiControlService.this.setCecEnabled(com.android.server.hdmi.HdmiControlService.this.mHdmiCecConfig.getIntValue("hdmi_cec_enabled"));
            }
        }, this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener("hdmi_cec_version", new com.android.server.hdmi.HdmiCecConfig.SettingChangeListener() { // from class: com.android.server.hdmi.HdmiControlService.3
            @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
            public void onChange(java.lang.String str) {
                com.android.server.hdmi.HdmiControlService.this.initializeCec(0);
            }
        }, this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener("routing_control", new com.android.server.hdmi.HdmiCecConfig.SettingChangeListener() { // from class: com.android.server.hdmi.HdmiControlService.4
            @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
            public void onChange(java.lang.String str) {
                boolean z = com.android.server.hdmi.HdmiControlService.this.mHdmiCecConfig.getIntValue("routing_control") == 1;
                if (com.android.server.hdmi.HdmiControlService.this.isAudioSystemDevice()) {
                    if (com.android.server.hdmi.HdmiControlService.this.audioSystem() == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Switch device has not registered yet. Can't turn routing on.");
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.audioSystem().setRoutingControlFeatureEnabled(z);
                    }
                }
            }
        }, this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener("system_audio_control", new com.android.server.hdmi.HdmiCecConfig.SettingChangeListener() { // from class: com.android.server.hdmi.HdmiControlService.5
            @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
            public void onChange(java.lang.String str) {
                boolean z = com.android.server.hdmi.HdmiControlService.this.mHdmiCecConfig.getIntValue("system_audio_control") == 1;
                if (com.android.server.hdmi.HdmiControlService.this.isTvDeviceEnabled()) {
                    com.android.server.hdmi.HdmiControlService.this.tv().setSystemAudioControlFeatureEnabled(z);
                }
                if (com.android.server.hdmi.HdmiControlService.this.isAudioSystemDevice()) {
                    if (com.android.server.hdmi.HdmiControlService.this.audioSystem() == null) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Audio System device has not registered yet. Can't turn system audio mode on.");
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.audioSystem().onSystemAudioControlFeatureSupportChanged(z);
                    }
                }
            }
        }, this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener("volume_control_enabled", new com.android.server.hdmi.HdmiCecConfig.SettingChangeListener() { // from class: com.android.server.hdmi.HdmiControlService.6
            @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
            public void onChange(java.lang.String str) {
                com.android.server.hdmi.HdmiControlService.this.setHdmiCecVolumeControlEnabledInternal(com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig().getIntValue("volume_control_enabled"));
            }
        }, this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener("tv_wake_on_one_touch_play", new com.android.server.hdmi.HdmiCecConfig.SettingChangeListener() { // from class: com.android.server.hdmi.HdmiControlService.7
            @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
            public void onChange(java.lang.String str) {
                if (com.android.server.hdmi.HdmiControlService.this.isTvDeviceEnabled()) {
                    com.android.server.hdmi.HdmiControlService.this.mCecController.enableWakeupByOtp(com.android.server.hdmi.HdmiControlService.this.tv().getAutoWakeup());
                }
            }
        }, this.mServiceThreadExecutor);
        if (isTvDevice()) {
            this.mDeviceConfig.addOnPropertiesChangedListener(getContext().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.hdmi.HdmiControlService.8
                public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                    com.android.server.hdmi.HdmiControlService.this.mEarcTxFeatureFlagEnabled = properties.getBoolean("enable_earc_tx", true);
                    com.android.server.hdmi.HdmiControlService.this.setEarcEnabled(((com.android.server.hdmi.HdmiControlService.this.mHdmiCecConfig.getIntValue("earc_enabled") == 1) && com.android.server.hdmi.HdmiControlService.this.mEarcTxFeatureFlagEnabled) ? 1 : 0);
                }
            });
        }
        this.mHdmiCecConfig.registerChangeListener("earc_enabled", new com.android.server.hdmi.HdmiCecConfig.SettingChangeListener() { // from class: com.android.server.hdmi.HdmiControlService.9
            @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
            public void onChange(java.lang.String str) {
                if (com.android.server.hdmi.HdmiControlService.this.isTvDevice()) {
                    int i = 0;
                    boolean z = com.android.server.hdmi.HdmiControlService.this.mHdmiCecConfig.getIntValue("earc_enabled") == 1;
                    com.android.server.hdmi.HdmiControlService hdmiControlService = com.android.server.hdmi.HdmiControlService.this;
                    if (z && com.android.server.hdmi.HdmiControlService.this.mEarcTxFeatureFlagEnabled) {
                        i = 1;
                    }
                    hdmiControlService.setEarcEnabled(i);
                    return;
                }
                com.android.server.hdmi.HdmiControlService.this.setEarcEnabled(com.android.server.hdmi.HdmiControlService.this.mHdmiCecConfig.getIntValue("earc_enabled"));
            }
        }, this.mServiceThreadExecutor);
        this.mDeviceConfig.addOnPropertiesChangedListener(getContext().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.hdmi.HdmiControlService.10
            public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.hdmi.HdmiControlService.this.mSoundbarModeFeatureFlagEnabled = properties.getBoolean("enable_soundbar_mode", true);
                com.android.server.hdmi.HdmiControlService.this.setSoundbarMode(((com.android.server.hdmi.HdmiControlService.this.mHdmiCecConfig.getIntValue("soundbar_mode") == 1) && com.android.server.hdmi.HdmiControlService.this.mSoundbarModeFeatureFlagEnabled) ? 1 : 0);
            }
        });
        this.mHdmiCecConfig.registerChangeListener("soundbar_mode", new com.android.server.hdmi.HdmiCecConfig.SettingChangeListener() { // from class: com.android.server.hdmi.HdmiControlService.11
            @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
            public void onChange(java.lang.String str) {
                int i = 0;
                boolean z = com.android.server.hdmi.HdmiControlService.this.mHdmiCecConfig.getIntValue("soundbar_mode") == 1;
                com.android.server.hdmi.HdmiControlService hdmiControlService = com.android.server.hdmi.HdmiControlService.this;
                if (z && com.android.server.hdmi.HdmiControlService.this.mSoundbarModeFeatureFlagEnabled) {
                    i = 1;
                }
                hdmiControlService.setSoundbarMode(i);
            }
        }, this.mServiceThreadExecutor);
        this.mDeviceConfig.addOnPropertiesChangedListener(getContext().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.hdmi.HdmiControlService.12
            public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.hdmi.HdmiControlService.this.mTransitionFromArcToEarcTxEnabled = properties.getBoolean("transition_arc_to_earc_tx", true);
            }
        });
        this.mDeviceConfig.addOnPropertiesChangedListener(getContext().getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.hdmi.HdmiControlService.13
            public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.hdmi.HdmiControlService.this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled = properties.getBoolean("enable_numeric_soundbar_volume_ui_on_tv", true);
                com.android.server.hdmi.HdmiControlService.this.checkAndUpdateAbsoluteVolumeBehavior();
            }
        });
    }

    boolean isScreenOff() {
        return this.mDisplayManager.getDisplay(0).getState() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bootCompleted() {
        if (this.mPowerManager.isInteractive() && isPowerStandbyOrTransient()) {
            this.mPowerStatusController.setPowerStatus(0);
            if (this.mAddressAllocated) {
                java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = getAllCecLocalDevices().iterator();
                while (it.hasNext()) {
                    it.next().startQueuedActions();
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    int getInitialPowerStatus() {
        return 3;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setCecController(com.android.server.hdmi.HdmiCecController hdmiCecController) {
        this.mCecController = hdmiCecController;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setEarcController(com.android.server.hdmi.HdmiEarcController hdmiEarcController) {
        this.mEarcController = hdmiEarcController;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setHdmiCecNetwork(com.android.server.hdmi.HdmiCecNetwork hdmiCecNetwork) {
        this.mHdmiCecNetwork = hdmiCecNetwork;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setHdmiCecConfig(com.android.server.hdmi.HdmiCecConfig hdmiCecConfig) {
        this.mHdmiCecConfig = hdmiCecConfig;
    }

    public com.android.server.hdmi.HdmiCecNetwork getHdmiCecNetwork() {
        return this.mHdmiCecNetwork;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setHdmiMhlController(com.android.server.hdmi.HdmiMhlControllerStub hdmiMhlControllerStub) {
        this.mMhlController = hdmiMhlControllerStub;
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            this.mDisplayManager = (android.hardware.display.DisplayManager) getContext().getSystemService(android.hardware.display.DisplayManager.class);
            this.mTvInputManager = (android.media.tv.TvInputManager) getContext().getSystemService("tv_input");
            this.mPowerManager = new com.android.server.hdmi.PowerManagerWrapper(getContext());
            this.mPowerManagerInternal = new com.android.server.hdmi.PowerManagerInternalWrapper();
            if (this.mAudioManager == null) {
                this.mAudioManager = new com.android.server.hdmi.DefaultAudioManagerWrapper(getContext());
            }
            this.mStreamMusicMaxVolume = getAudioManager().getStreamMaxVolume(3);
            if (this.mAudioDeviceVolumeManager == null) {
                this.mAudioDeviceVolumeManager = new com.android.server.hdmi.DefaultAudioDeviceVolumeManagerWrapper(getContext());
            }
            getAudioDeviceVolumeManager().addOnDeviceVolumeBehaviorChangedListener(this.mServiceThreadExecutor, new android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener() { // from class: com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda0
                public final void onDeviceVolumeBehaviorChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, int i2) {
                    com.android.server.hdmi.HdmiControlService.this.onDeviceVolumeBehaviorChanged(audioDeviceAttributes, i2);
                }
            });
            return;
        }
        if (i == 1000) {
            runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.hdmi.HdmiControlService.this.bootCompleted();
                }
            });
        }
    }

    android.media.tv.TvInputManager getTvInputManager() {
        return this.mTvInputManager;
    }

    void registerTvInputCallback(android.media.tv.TvInputManager.TvInputCallback tvInputCallback) {
        if (this.mTvInputManager == null) {
            return;
        }
        this.mTvInputManager.registerCallback(tvInputCallback, this.mHandler);
    }

    void unregisterTvInputCallback(android.media.tv.TvInputManager.TvInputCallback tvInputCallback) {
        if (this.mTvInputManager == null) {
            return;
        }
        this.mTvInputManager.unregisterCallback(tvInputCallback);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setDeviceConfig(com.android.server.hdmi.DeviceConfigWrapper deviceConfigWrapper) {
        this.mDeviceConfig = deviceConfigWrapper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setPowerManager(com.android.server.hdmi.PowerManagerWrapper powerManagerWrapper) {
        this.mPowerManager = powerManagerWrapper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setPowerManagerInternal(com.android.server.hdmi.PowerManagerInternalWrapper powerManagerInternalWrapper) {
        this.mPowerManagerInternal = powerManagerInternalWrapper;
    }

    com.android.server.hdmi.DeviceConfigWrapper getDeviceConfig() {
        return this.mDeviceConfig;
    }

    com.android.server.hdmi.PowerManagerWrapper getPowerManager() {
        return this.mPowerManager;
    }

    com.android.server.hdmi.PowerManagerInternalWrapper getPowerManagerInternal() {
        return this.mPowerManagerInternal;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void setSoundbarMode(int i) {
        boolean z;
        boolean isArcSupported = isArcSupported();
        com.android.server.hdmi.HdmiCecLocalDevicePlayback playback = playback();
        com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem = audioSystem();
        getAtomWriter().dsmStatusChanged(isArcSupported, i == 1, 2);
        if (playback == null) {
            android.util.Slog.w(TAG, "Device type not compatible to change soundbar mode.");
            return;
        }
        if (!isArcSupported) {
            android.util.Slog.w(TAG, "Device type doesn't support ARC.");
            return;
        }
        if (i == 0 && audioSystem != null) {
            z = audioSystem.isArcEnabled();
            if (isSystemAudioActivated()) {
                audioSystem.terminateSystemAudioMode();
            }
            if (z) {
                if (audioSystem.hasAction(com.android.server.hdmi.ArcTerminationActionFromAvr.class)) {
                    audioSystem.removeAction(com.android.server.hdmi.ArcTerminationActionFromAvr.class);
                }
                audioSystem.addAndStartAction(new com.android.server.hdmi.ArcTerminationActionFromAvr(audioSystem, new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiControlService.14
                    public void onComplete(int i2) {
                        com.android.server.hdmi.HdmiControlService.this.mAddressAllocated = false;
                        com.android.server.hdmi.HdmiControlService.this.initializeCecLocalDevices(5);
                    }
                }));
            }
        } else {
            z = false;
        }
        if (!z) {
            this.mAddressAllocated = false;
            initializeCecLocalDevices(5);
        }
    }

    public boolean isDeviceDiscoveryHandledByPlayback() {
        com.android.server.hdmi.HdmiCecLocalDevicePlayback playback = playback();
        if (playback != null) {
            if (playback.hasAction(com.android.server.hdmi.DeviceDiscoveryAction.class) || playback.hasAction(com.android.server.hdmi.HotplugDetectionAction.class)) {
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInitializeCecComplete(int i) {
        updatePowerStatusOnInitializeCecComplete();
        int i2 = 0;
        this.mWakeUpMessageReceived = false;
        if (isTvDeviceEnabled()) {
            this.mCecController.enableWakeupByOtp(tv().getAutoWakeup());
        }
        switch (i) {
            case 0:
                i2 = 1;
                break;
            case 1:
                break;
            case 2:
                java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = getAllCecLocalDevices().iterator();
                while (it.hasNext()) {
                    it.next().onInitializeCecComplete(i);
                }
                i2 = 2;
                break;
            case 3:
                i2 = 2;
                break;
            default:
                i2 = -1;
                break;
        }
        if (i2 != -1) {
            invokeVendorCommandListenersOnControlStateChanged(true, i2);
            announceHdmiControlStatusChange(1);
        }
    }

    private void updatePowerStatusOnInitializeCecComplete() {
        if (this.mPowerStatusController.isPowerStatusTransientToOn()) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.hdmi.HdmiControlService.this.lambda$updatePowerStatusOnInitializeCecComplete$0();
                }
            });
        } else if (this.mPowerStatusController.isPowerStatusTransientToStandby()) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.hdmi.HdmiControlService.this.lambda$updatePowerStatusOnInitializeCecComplete$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePowerStatusOnInitializeCecComplete$0() {
        this.mPowerStatusController.setPowerStatus(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePowerStatusOnInitializeCecComplete$1() {
        this.mPowerStatusController.setPowerStatus(1);
    }

    private void registerContentObserver() {
        android.content.ContentResolver contentResolver = getContext().getContentResolver();
        java.lang.String[] strArr = {"mhl_input_switching_enabled", "mhl_power_charge_enabled", "device_name"};
        for (int i = 0; i < 3; i++) {
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor(strArr[i]), false, this.mSettingsObserver, -1);
        }
    }

    private class SettingsObserver extends android.database.ContentObserver {
        public SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:4:0x001a, code lost:
        
            if (r3.equals("mhl_power_charge_enabled") != false) goto L15;
         */
        @Override // android.database.ContentObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onChange(boolean z, android.net.Uri uri) {
            java.lang.String lastPathSegment = uri.getLastPathSegment();
            char c = 1;
            boolean readBooleanSetting = com.android.server.hdmi.HdmiControlService.this.readBooleanSetting(lastPathSegment, true);
            switch (lastPathSegment.hashCode()) {
                case -1543071020:
                    if (lastPathSegment.equals("device_name")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1262529811:
                    if (lastPathSegment.equals("mhl_input_switching_enabled")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -885757826:
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    com.android.server.hdmi.HdmiControlService.this.setMhlInputChangeEnabled(readBooleanSetting);
                    break;
                case 1:
                    com.android.server.hdmi.HdmiControlService.this.mMhlController.setOption(102, com.android.server.hdmi.HdmiControlService.toInt(readBooleanSetting));
                    break;
                case 2:
                    com.android.server.hdmi.HdmiControlService.this.setDisplayName(com.android.server.hdmi.HdmiControlService.this.readStringSetting(lastPathSegment, android.os.Build.MODEL));
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int toInt(boolean z) {
        return z ? 1 : 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean readBooleanSetting(java.lang.String str, boolean z) {
        return android.provider.Settings.Global.getInt(getContext().getContentResolver(), str, toInt(z)) == 1;
    }

    @com.android.internal.annotations.VisibleForTesting
    int readIntSetting(java.lang.String str, int i) {
        return android.provider.Settings.Global.getInt(getContext().getContentResolver(), str, i);
    }

    void writeBooleanSetting(java.lang.String str, boolean z) {
        android.provider.Settings.Global.putInt(getContext().getContentResolver(), str, toInt(z));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void writeStringSystemProperty(java.lang.String str, java.lang.String str2) {
        android.os.SystemProperties.set(str, str2);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean readBooleanSystemProperty(java.lang.String str, boolean z) {
        return android.os.SystemProperties.getBoolean(str, z);
    }

    java.lang.String readStringSetting(java.lang.String str, java.lang.String str2) {
        java.lang.String string = android.provider.Settings.Global.getString(getContext().getContentResolver(), str);
        if (android.text.TextUtils.isEmpty(string)) {
            return str2;
        }
        return string;
    }

    void writeStringSetting(java.lang.String str, java.lang.String str2) {
        android.provider.Settings.Global.putString(getContext().getContentResolver(), str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initializeCec(int i) {
        this.mAddressAllocated = false;
        this.mCecVersion = java.lang.Math.max(5, java.lang.Math.min(getHdmiCecConfig().getIntValue("hdmi_cec_version"), this.mCecController.getVersion()));
        this.mCecController.enableSystemCecControl(true);
        this.mCecController.setLanguage(this.mMenuLanguage);
        initializeCecLocalDevices(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private java.util.List<java.lang.Integer> getCecLocalDeviceTypes() {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mCecLocalDevices);
        if (isDsmEnabled() && !arrayList.contains(5) && isArcSupported() && this.mSoundbarModeFeatureFlagEnabled) {
            arrayList.add(5);
        }
        return arrayList;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void initializeCecLocalDevices(int i) {
        assertRunOnServiceThread();
        java.util.ArrayList<com.android.server.hdmi.HdmiCecLocalDevice> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<java.lang.Integer> it = getCecLocalDeviceTypes().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            com.android.server.hdmi.HdmiCecLocalDevice localDevice = this.mHdmiCecNetwork.getLocalDevice(intValue);
            if (localDevice == null) {
                localDevice = com.android.server.hdmi.HdmiCecLocalDevice.create(this, intValue);
            }
            localDevice.init();
            arrayList.add(localDevice);
        }
        this.mHdmiCecNetwork.clearDeviceList();
        allocateLogicalAddress(arrayList, i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void allocateLogicalAddress(final java.util.ArrayList<com.android.server.hdmi.HdmiCecLocalDevice> arrayList, final int i) {
        assertRunOnServiceThread();
        this.mCecController.clearLogicalAddress();
        final java.util.ArrayList arrayList2 = new java.util.ArrayList();
        final int[] iArr = new int[1];
        this.mAddressAllocated = arrayList.isEmpty();
        this.mSelectRequestBuffer.clear();
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = arrayList.iterator();
        while (it.hasNext()) {
            final com.android.server.hdmi.HdmiCecLocalDevice next = it.next();
            this.mCecController.allocateLogicalAddress(next.getType(), next.getPreferredAddress(), new com.android.server.hdmi.HdmiCecController.AllocateAddressCallback() { // from class: com.android.server.hdmi.HdmiControlService.15
                @Override // com.android.server.hdmi.HdmiCecController.AllocateAddressCallback
                public void onAllocated(int i2, int i3) {
                    if (i3 == 15) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Failed to allocate address:[device_type:" + i2 + "]");
                        com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.removeLocalDeviceWithType(i2);
                    } else {
                        next.setDeviceInfo(com.android.server.hdmi.HdmiControlService.this.createDeviceInfo(i3, i2, 0, com.android.server.hdmi.HdmiControlService.this.getCecVersion()));
                        com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.addLocalDevice(i2, next);
                        com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.addCecDevice(next.getDeviceInfo());
                        com.android.server.hdmi.HdmiControlService.this.mCecController.addLogicalAddress(i3);
                        arrayList2.add(next);
                    }
                    int size = arrayList.size();
                    int[] iArr2 = iArr;
                    int i4 = iArr2[0] + 1;
                    iArr2[0] = i4;
                    if (size == i4) {
                        if (i != 4 && i != 5) {
                            com.android.server.hdmi.HdmiControlService.this.onInitializeCecComplete(i);
                        } else if (i == 4 && com.android.server.hdmi.HdmiControlService.this.mDisplayStatusCallback == null) {
                            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                                com.android.server.hdmi.HdmiControlService.this.announceHdmiControlStatusChange(com.android.server.hdmi.HdmiControlService.this.mHdmiControlEnabled);
                            }
                        }
                        com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.removeUnusedLocalDevices(arrayList2);
                        com.android.server.hdmi.HdmiControlService.this.mAddressAllocated = true;
                        com.android.server.hdmi.HdmiControlService.this.notifyAddressAllocated(arrayList2, i);
                        if (com.android.server.hdmi.HdmiControlService.this.mDisplayStatusCallback != null) {
                            com.android.server.hdmi.HdmiControlService.this.queryDisplayStatus(com.android.server.hdmi.HdmiControlService.this.mDisplayStatusCallback);
                            com.android.server.hdmi.HdmiControlService.this.mDisplayStatusCallback = null;
                        }
                        if (com.android.server.hdmi.HdmiControlService.this.mOtpCallbackPendingAddressAllocation != null) {
                            com.android.server.hdmi.HdmiControlService.this.oneTouchPlay(com.android.server.hdmi.HdmiControlService.this.mOtpCallbackPendingAddressAllocation);
                            com.android.server.hdmi.HdmiControlService.this.mOtpCallbackPendingAddressAllocation = null;
                        }
                        com.android.server.hdmi.HdmiControlService.this.mCecMessageBuffer.processMessages();
                    }
                }
            });
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    public void notifyAddressAllocated(java.util.ArrayList<com.android.server.hdmi.HdmiCecLocalDevice> arrayList, int i) {
        assertRunOnServiceThread();
        if (arrayList == null || arrayList.isEmpty()) {
            android.util.Slog.w(TAG, "No local device to notify.");
            return;
        }
        java.util.List<com.android.server.hdmi.HdmiCecMessage> buffer = this.mCecMessageBuffer.getBuffer();
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = arrayList.iterator();
        while (it.hasNext()) {
            com.android.server.hdmi.HdmiCecLocalDevice next = it.next();
            next.handleAddressAllocated(next.getDeviceInfo().getLogicalAddress(), buffer, i);
        }
        if (isTvDeviceEnabled()) {
            tv().setSelectRequestBuffer(this.mSelectRequestBuffer);
        }
    }

    boolean isAddressAllocated() {
        return this.mAddressAllocated;
    }

    java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() {
        java.util.List<android.hardware.hdmi.HdmiPortInfo> portInfo;
        synchronized (this.mLock) {
            portInfo = this.mHdmiCecNetwork.getPortInfo();
        }
        return portInfo;
    }

    android.hardware.hdmi.HdmiPortInfo getPortInfo(int i) {
        return this.mHdmiCecNetwork.getPortInfo(i);
    }

    int portIdToPath(int i) {
        return this.mHdmiCecNetwork.portIdToPath(i);
    }

    int pathToPortId(int i) {
        return this.mHdmiCecNetwork.physicalAddressToPortId(i);
    }

    boolean isValidPortId(int i) {
        return this.mHdmiCecNetwork.getPortInfo(i) != null;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected android.os.Looper getIoLooper() {
        return this.mIoLooper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setIoLooper(android.os.Looper looper) {
        this.mIoLooper = looper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setCecMessageBuffer(com.android.server.hdmi.CecMessageBuffer cecMessageBuffer) {
        this.mCecMessageBuffer = cecMessageBuffer;
    }

    protected android.os.Looper getServiceLooper() {
        return this.mHandler.getLooper();
    }

    int getPhysicalAddress() {
        return this.mCecController.getPhysicalAddress();
    }

    int getVendorId() {
        return this.mCecController.getVendorId();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @android.annotation.Nullable
    android.hardware.hdmi.HdmiDeviceInfo getDeviceInfo(int i) {
        assertRunOnServiceThread();
        return this.mHdmiCecNetwork.getCecDeviceInfo(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    android.hardware.hdmi.HdmiDeviceInfo getDeviceInfoByPort(int i) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiMhlLocalDeviceStub localDevice = this.mMhlController.getLocalDevice(i);
        if (localDevice != null) {
            return localDevice.getInfo();
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int getCecVersion() {
        return this.mCecVersion;
    }

    boolean isConnectedToArcPort(int i) {
        return this.mHdmiCecNetwork.isConnectedToArcPort(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isConnected(int i) {
        assertRunOnServiceThread();
        return this.mCecController.isConnected(i);
    }

    void runOnServiceThread(java.lang.Runnable runnable) {
        this.mHandler.post(new com.android.server.hdmi.WorkSourceUidPreservingRunnable(runnable));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertRunOnServiceThread() {
        if (android.os.Looper.myLooper() != this.mHandler.getLooper()) {
            throw new java.lang.IllegalStateException("Should run on service thread.");
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void sendCecCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        sendCecCommand(hdmiCecMessage, null);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void sendCecCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, @android.annotation.Nullable com.android.server.hdmi.HdmiControlService.SendMessageCallback sendMessageCallback) {
        switch (hdmiCecMessage.getOpcode()) {
            case 4:
            case 13:
            case 128:
            case 130:
            case 134:
            case 157:
                sendCecCommandWithRetries(hdmiCecMessage, sendMessageCallback);
                break;
            default:
                sendCecCommandWithoutRetries(hdmiCecMessage, sendMessageCallback);
                break;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void sendCecCommandWithRetries(final com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, @android.annotation.Nullable final com.android.server.hdmi.HdmiControlService.SendMessageCallback sendMessageCallback) {
        assertRunOnServiceThread();
        final com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice = getAllCecLocalDevices().get(0);
        if (hdmiCecLocalDevice != null) {
            sendCecCommandWithoutRetries(hdmiCecMessage, new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.HdmiControlService.16
                @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                public void onSendCompleted(int i) {
                    if (i != 0) {
                        hdmiCecLocalDevice.addAndStartAction(new com.android.server.hdmi.ResendCecCommandAction(hdmiCecLocalDevice, hdmiCecMessage, sendMessageCallback));
                    }
                }
            });
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void sendCecCommandWithoutRetries(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, @android.annotation.Nullable com.android.server.hdmi.HdmiControlService.SendMessageCallback sendMessageCallback) {
        assertRunOnServiceThread();
        if (hdmiCecMessage.getValidationResult() == 0 && verifyPhysicalAddresses(hdmiCecMessage)) {
            this.mCecController.sendCommand(hdmiCecMessage, sendMessageCallback);
            return;
        }
        com.android.server.hdmi.HdmiLogger.error("Invalid message type:" + hdmiCecMessage, new java.lang.Object[0]);
        if (sendMessageCallback != null) {
            sendMessageCallback.onSendCompleted(3);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void maySendFeatureAbortCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, int i) {
        assertRunOnServiceThread();
        this.mCecController.maySendFeatureAbortCommand(hdmiCecMessage, i);
    }

    boolean verifyPhysicalAddresses(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        byte[] params = hdmiCecMessage.getParams();
        switch (hdmiCecMessage.getOpcode()) {
            case 112:
                return params.length == 0 || verifyPhysicalAddress(params, 0);
            case 128:
                return verifyPhysicalAddress(params, 0) && verifyPhysicalAddress(params, 2);
            case 129:
            case 130:
            case 132:
            case 134:
            case 157:
                return verifyPhysicalAddress(params, 0);
            case 161:
            case 162:
                return verifyExternalSourcePhysicalAddress(params, 7);
            default:
                return true;
        }
    }

    private boolean verifyPhysicalAddress(byte[] bArr, int i) {
        if (!isTvDevice()) {
            return true;
        }
        if (bArr.length < i + 2) {
            return false;
        }
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(bArr, i);
        return (twoBytesToInt != 65535 && twoBytesToInt == getPhysicalAddress()) || pathToPortId(twoBytesToInt) != -1;
    }

    private boolean verifyExternalSourcePhysicalAddress(byte[] bArr, int i) {
        byte b = bArr[i];
        int i2 = i + 1;
        if (b != 5 || bArr.length - i2 < 2) {
            return true;
        }
        return verifyPhysicalAddress(bArr, i2);
    }

    private boolean sourceAddressIsLocal(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = getAllCecLocalDevices().iterator();
        while (it.hasNext()) {
            if (hdmiCecMessage.getSource() == it.next().getDeviceInfo().getLogicalAddress() && hdmiCecMessage.getSource() != 15) {
                com.android.server.hdmi.HdmiLogger.warning("Unexpected source: message sent from device itself, " + hdmiCecMessage, new java.lang.Object[0]);
                return true;
            }
        }
        return false;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected int handleCecCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int validationResult = hdmiCecMessage.getValidationResult();
        if (validationResult == 3 || validationResult == 5 || !verifyPhysicalAddresses(hdmiCecMessage)) {
            return 3;
        }
        if (validationResult != 0 || sourceAddressIsLocal(hdmiCecMessage)) {
            return -1;
        }
        getHdmiCecNetwork().handleCecMessage(hdmiCecMessage);
        int dispatchMessageToLocalDevice = dispatchMessageToLocalDevice(hdmiCecMessage);
        if (!this.mAddressAllocated && this.mCecMessageBuffer.bufferMessage(hdmiCecMessage)) {
            return -1;
        }
        return dispatchMessageToLocalDevice;
    }

    void enableAudioReturnChannel(int i, boolean z) {
        if (!this.mTransitionFromArcToEarcTxEnabled && z && this.mEarcController != null) {
            setEarcEnabledInHal(false, false);
        }
        this.mCecController.enableAudioReturnChannel(i, z);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected int dispatchMessageToLocalDevice(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = this.mHdmiCecNetwork.getLocalDeviceList().iterator();
        while (it.hasNext()) {
            int dispatchMessage = it.next().dispatchMessage(hdmiCecMessage);
            if (dispatchMessage != -2 && hdmiCecMessage.getDestination() != 15) {
                return dispatchMessage;
            }
        }
        if (hdmiCecMessage.getDestination() == 15) {
            return -1;
        }
        com.android.server.hdmi.HdmiLogger.warning("Unhandled cec command:" + hdmiCecMessage, new java.lang.Object[0]);
        return -2;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void onHotplug(int i, boolean z) {
        assertRunOnServiceThread();
        this.mHdmiCecNetwork.initPortInfo();
        android.hardware.hdmi.HdmiPortInfo portInfo = getPortInfo(i);
        if (z && !isTvDevice() && portInfo != null && portInfo.getType() == 1) {
            java.util.ArrayList<com.android.server.hdmi.HdmiCecLocalDevice> arrayList = new java.util.ArrayList<>();
            java.util.Iterator<java.lang.Integer> it = getCecLocalDeviceTypes().iterator();
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                com.android.server.hdmi.HdmiCecLocalDevice localDevice = this.mHdmiCecNetwork.getLocalDevice(intValue);
                if (localDevice == null) {
                    localDevice = com.android.server.hdmi.HdmiCecLocalDevice.create(this, intValue);
                    localDevice.init();
                }
                arrayList.add(localDevice);
            }
            allocateLogicalAddress(arrayList, 4);
        }
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it2 = this.mHdmiCecNetwork.getLocalDeviceList().iterator();
        while (it2.hasNext()) {
            it2.next().onHotplug(i, z);
        }
        announceHotplugEvent(i, z);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void pollDevices(com.android.server.hdmi.HdmiControlService.DevicePollingCallback devicePollingCallback, int i, int i2, int i3) {
        assertRunOnServiceThread();
        this.mCecController.pollDevices(devicePollingCallback, i, checkPollStrategy(i2), i3);
    }

    private int checkPollStrategy(int i) {
        int i2 = i & 3;
        if (i2 == 0) {
            throw new java.lang.IllegalArgumentException("Invalid poll strategy:" + i);
        }
        int i3 = 196608 & i;
        if (i3 == 0) {
            throw new java.lang.IllegalArgumentException("Invalid iteration strategy:" + i);
        }
        return i2 | i3;
    }

    java.util.List<com.android.server.hdmi.HdmiCecLocalDevice> getAllCecLocalDevices() {
        assertRunOnServiceThread();
        return this.mHdmiCecNetwork.getLocalDeviceList();
    }

    protected void checkLogicalAddressConflictAndReallocate(int i, int i2) {
        if (i2 == getPhysicalAddress()) {
            return;
        }
        for (com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice : getAllCecLocalDevices()) {
            if (hdmiCecLocalDevice.getDeviceInfo().getLogicalAddress() == i) {
                com.android.server.hdmi.HdmiLogger.debug("allocate logical address for " + hdmiCecLocalDevice.getDeviceInfo(), new java.lang.Object[0]);
                java.util.ArrayList<com.android.server.hdmi.HdmiCecLocalDevice> arrayList = new java.util.ArrayList<>();
                arrayList.add(hdmiCecLocalDevice);
                allocateLogicalAddress(arrayList, 4);
                return;
            }
        }
    }

    java.lang.Object getServiceLock() {
        return this.mLock;
    }

    void setAudioStatus(boolean z, int i) {
        if (!isTvDeviceEnabled() || !tv().isSystemAudioActivated() || !tv().isArcEstablished() || getHdmiCecVolumeControl() == 0) {
            return;
        }
        com.android.server.hdmi.AudioManagerWrapper audioManager = getAudioManager();
        boolean isStreamMute = audioManager.isStreamMute(3);
        if (z) {
            if (!isStreamMute) {
                audioManager.setStreamMute(3, true);
                return;
            }
            return;
        }
        if (isStreamMute) {
            audioManager.setStreamMute(3, false);
        }
        if (i >= 0 && i <= 100) {
            android.util.Slog.i(TAG, "volume: " + i);
            audioManager.setStreamVolume(3, i, 257);
        }
    }

    void announceSystemAudioModeChange(boolean z) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.hdmi.HdmiControlService.SystemAudioModeChangeListenerRecord> it = this.mSystemAudioModeChangeListenerRecords.iterator();
                while (it.hasNext()) {
                    invokeSystemAudioModeChangeLocked(it.next().mListener, z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.hdmi.HdmiDeviceInfo createDeviceInfo(int i, int i2, int i3, int i4) {
        return android.hardware.hdmi.HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(i).setPhysicalAddress(getPhysicalAddress()).setPortId(pathToPortId(getPhysicalAddress())).setDeviceType(i2).setVendorId(getVendorId()).setDisplayName(readStringSetting("device_name", android.os.Build.MODEL)).setDevicePowerStatus(i3).setCecVersion(i4).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisplayName(java.lang.String str) {
        for (com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice : getAllCecLocalDevices()) {
            android.hardware.hdmi.HdmiDeviceInfo deviceInfo = hdmiCecLocalDevice.getDeviceInfo();
            if (!deviceInfo.getDisplayName().equals(str)) {
                hdmiCecLocalDevice.setDeviceInfo(deviceInfo.toBuilder().setDisplayName(str).build());
                sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetOsdNameCommand(deviceInfo.getLogicalAddress(), 0, str));
            }
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void handleMhlHotplugEvent(int i, boolean z) {
        assertRunOnServiceThread();
        if (z) {
            com.android.server.hdmi.HdmiMhlLocalDeviceStub hdmiMhlLocalDeviceStub = new com.android.server.hdmi.HdmiMhlLocalDeviceStub(this, i);
            com.android.server.hdmi.HdmiMhlLocalDeviceStub addLocalDevice = this.mMhlController.addLocalDevice(hdmiMhlLocalDeviceStub);
            if (addLocalDevice != null) {
                addLocalDevice.onDeviceRemoved();
                android.util.Slog.i(TAG, "Old device of port " + i + " is removed");
            }
            invokeDeviceEventListeners(hdmiMhlLocalDeviceStub.getInfo(), 1);
            updateSafeMhlInput();
        } else {
            com.android.server.hdmi.HdmiMhlLocalDeviceStub removeLocalDevice = this.mMhlController.removeLocalDevice(i);
            if (removeLocalDevice != null) {
                removeLocalDevice.onDeviceRemoved();
                invokeDeviceEventListeners(removeLocalDevice.getInfo(), 2);
                updateSafeMhlInput();
            } else {
                android.util.Slog.w(TAG, "No device to remove:[portId=" + i);
            }
        }
        announceHotplugEvent(i, z);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void handleMhlBusModeChanged(int i, int i2) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiMhlLocalDeviceStub localDevice = this.mMhlController.getLocalDevice(i);
        if (localDevice != null) {
            localDevice.setBusMode(i2);
            return;
        }
        android.util.Slog.w(TAG, "No mhl device exists for bus mode change[portId:" + i + ", busmode:" + i2 + "]");
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void handleMhlBusOvercurrent(int i, boolean z) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiMhlLocalDeviceStub localDevice = this.mMhlController.getLocalDevice(i);
        if (localDevice != null) {
            localDevice.onBusOvercurrentDetected(z);
            return;
        }
        android.util.Slog.w(TAG, "No mhl device exists for bus overcurrent event[portId:" + i + "]");
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void handleMhlDeviceStatusChanged(int i, int i2, int i3) {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiMhlLocalDeviceStub localDevice = this.mMhlController.getLocalDevice(i);
        if (localDevice != null) {
            localDevice.setDeviceStatusChange(i2, i3);
            return;
        }
        android.util.Slog.w(TAG, "No mhl device exists for device status event[portId:" + i + ", adopterId:" + i2 + ", deviceId:" + i3 + "]");
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void updateSafeMhlInput() {
        assertRunOnServiceThread();
        java.util.List<android.hardware.hdmi.HdmiDeviceInfo> emptyList = java.util.Collections.emptyList();
        android.util.SparseArray<com.android.server.hdmi.HdmiMhlLocalDeviceStub> allLocalDevices = this.mMhlController.getAllLocalDevices();
        for (int i = 0; i < allLocalDevices.size(); i++) {
            com.android.server.hdmi.HdmiMhlLocalDeviceStub valueAt = allLocalDevices.valueAt(i);
            if (valueAt.getInfo() != null) {
                if (emptyList.isEmpty()) {
                    emptyList = new java.util.ArrayList<>();
                }
                emptyList.add(valueAt.getInfo());
            }
        }
        synchronized (this.mLock) {
            this.mMhlDevices = emptyList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getMhlDevicesLocked() {
        return this.mMhlDevices;
    }

    private class HdmiMhlVendorCommandListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.hdmi.IHdmiMhlVendorCommandListener mListener;

        public HdmiMhlVendorCommandListenerRecord(android.hardware.hdmi.IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) {
            this.mListener = iHdmiMhlVendorCommandListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.hdmi.HdmiControlService.this.mMhlVendorCommandListenerRecords.remove(this);
        }
    }

    private final class HdmiControlStatusChangeListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.hdmi.IHdmiControlStatusChangeListener mListener;

        HdmiControlStatusChangeListenerRecord(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            this.mListener = iHdmiControlStatusChangeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                com.android.server.hdmi.HdmiControlService.this.mHdmiControlStatusChangeListenerRecords.remove(this);
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (obj instanceof com.android.server.hdmi.HdmiControlService.HdmiControlStatusChangeListenerRecord) {
                return obj == this || ((com.android.server.hdmi.HdmiControlService.HdmiControlStatusChangeListenerRecord) obj).mListener == this.mListener;
            }
            return false;
        }

        public int hashCode() {
            return this.mListener.hashCode();
        }
    }

    private final class HotplugEventListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.hdmi.IHdmiHotplugEventListener mListener;

        public HotplugEventListenerRecord(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            this.mListener = iHdmiHotplugEventListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                com.android.server.hdmi.HdmiControlService.this.mHotplugEventListenerRecords.remove(this);
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (obj instanceof com.android.server.hdmi.HdmiControlService.HotplugEventListenerRecord) {
                return obj == this || ((com.android.server.hdmi.HdmiControlService.HotplugEventListenerRecord) obj).mListener == this.mListener;
            }
            return false;
        }

        public int hashCode() {
            return this.mListener.hashCode();
        }
    }

    private final class DeviceEventListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.hdmi.IHdmiDeviceEventListener mListener;

        public DeviceEventListenerRecord(android.hardware.hdmi.IHdmiDeviceEventListener iHdmiDeviceEventListener) {
            this.mListener = iHdmiDeviceEventListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                com.android.server.hdmi.HdmiControlService.this.mDeviceEventListenerRecords.remove(this);
            }
        }
    }

    private final class SystemAudioModeChangeListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.hdmi.IHdmiSystemAudioModeChangeListener mListener;

        public SystemAudioModeChangeListenerRecord(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            this.mListener = iHdmiSystemAudioModeChangeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                com.android.server.hdmi.HdmiControlService.this.mSystemAudioModeChangeListenerRecords.remove(this);
            }
        }
    }

    class VendorCommandListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.hdmi.IHdmiVendorCommandListener mListener;
        private final int mVendorId;

        VendorCommandListenerRecord(android.hardware.hdmi.IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) {
            this.mListener = iHdmiVendorCommandListener;
            this.mVendorId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                com.android.server.hdmi.HdmiControlService.this.mVendorCommandListenerRecords.remove(this);
            }
        }
    }

    private class HdmiRecordListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.hdmi.IHdmiRecordListener mListener;

        public HdmiRecordListenerRecord(android.hardware.hdmi.IHdmiRecordListener iHdmiRecordListener) {
            this.mListener = iHdmiRecordListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                try {
                    if (com.android.server.hdmi.HdmiControlService.this.mRecordListenerRecord == this) {
                        com.android.server.hdmi.HdmiControlService.this.mRecordListenerRecord = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void setWorkSourceUidToCallingUid() {
        android.os.Binder.setCallingWorkSourceUid(android.os.Binder.getCallingUid());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceAccessPermission() {
        getContext().enforceCallingOrSelfPermission(PERMISSION, TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBinderCall() {
        enforceAccessPermission();
        setWorkSourceUidToCallingUid();
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class BinderService extends android.hardware.hdmi.IHdmiControlService.Stub {
        private BinderService() {
        }

        public int[] getSupportedTypes() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            int size = com.android.server.hdmi.HdmiControlService.this.mCecLocalDevices.size();
            int[] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = ((java.lang.Integer) com.android.server.hdmi.HdmiControlService.this.mCecLocalDevices.get(i)).intValue();
            }
            return iArr;
        }

        @android.annotation.Nullable
        public android.hardware.hdmi.HdmiDeviceInfo getActiveSource() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            return com.android.server.hdmi.HdmiControlService.this.getActiveSource();
        }

        public void deviceSelect(final int i, final android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.1
                @Override // java.lang.Runnable
                public void run() {
                    if (iHdmiControlCallback == null) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Callback cannot be null");
                        return;
                    }
                    com.android.server.hdmi.HdmiCecLocalDeviceTv tv = com.android.server.hdmi.HdmiControlService.this.tv();
                    com.android.server.hdmi.HdmiCecLocalDevicePlayback playback = com.android.server.hdmi.HdmiControlService.this.playback();
                    if (tv == null && playback == null) {
                        if (!com.android.server.hdmi.HdmiControlService.this.mAddressAllocated) {
                            com.android.server.hdmi.HdmiControlService.this.mSelectRequestBuffer.set(com.android.server.hdmi.SelectRequestBuffer.newDeviceSelect(com.android.server.hdmi.HdmiControlService.this, i, iHdmiControlCallback));
                            return;
                        } else if (com.android.server.hdmi.HdmiControlService.this.isTvDevice()) {
                            android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Local tv device not available");
                            return;
                        } else {
                            com.android.server.hdmi.HdmiControlService.this.invokeCallback(iHdmiControlCallback, 2);
                            return;
                        }
                    }
                    if (tv != null) {
                        com.android.server.hdmi.HdmiMhlLocalDeviceStub localDeviceById = com.android.server.hdmi.HdmiControlService.this.mMhlController.getLocalDeviceById(i);
                        if (localDeviceById != null) {
                            if (localDeviceById.getPortId() == tv.getActivePortId()) {
                                com.android.server.hdmi.HdmiControlService.this.invokeCallback(iHdmiControlCallback, 0);
                                return;
                            } else {
                                localDeviceById.turnOn(iHdmiControlCallback);
                                tv.doManualPortSwitching(localDeviceById.getPortId(), null);
                                return;
                            }
                        }
                        tv.deviceSelect(i, iHdmiControlCallback);
                        return;
                    }
                    playback.deviceSelect(i, iHdmiControlCallback);
                }
            });
        }

        public void portSelect(final int i, final android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.2
                @Override // java.lang.Runnable
                public void run() {
                    if (iHdmiControlCallback == null) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Callback cannot be null");
                        return;
                    }
                    com.android.server.hdmi.HdmiCecLocalDeviceTv tv = com.android.server.hdmi.HdmiControlService.this.tv();
                    if (tv != null) {
                        tv.doManualPortSwitching(i, iHdmiControlCallback);
                        return;
                    }
                    com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem = com.android.server.hdmi.HdmiControlService.this.audioSystem();
                    if (audioSystem != null) {
                        audioSystem.doManualPortSwitching(i, iHdmiControlCallback);
                    } else if (!com.android.server.hdmi.HdmiControlService.this.mAddressAllocated) {
                        com.android.server.hdmi.HdmiControlService.this.mSelectRequestBuffer.set(com.android.server.hdmi.SelectRequestBuffer.newPortSelect(com.android.server.hdmi.HdmiControlService.this, i, iHdmiControlCallback));
                    } else {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local device not available");
                        com.android.server.hdmi.HdmiControlService.this.invokeCallback(iHdmiControlCallback, 2);
                    }
                }
            });
        }

        public void sendKeyEvent(final int i, final int i2, final boolean z) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.3
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiMhlLocalDeviceStub localDevice = com.android.server.hdmi.HdmiControlService.this.mMhlController.getLocalDevice(com.android.server.hdmi.HdmiControlService.this.mActivePortId);
                    if (localDevice != null) {
                        localDevice.sendKeyEvent(i2, z);
                        return;
                    }
                    if (com.android.server.hdmi.HdmiControlService.this.mCecController != null) {
                        com.android.server.hdmi.HdmiCecLocalDevice localDevice2 = com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.getLocalDevice(i);
                        if (localDevice2 == null) {
                            android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local device not available to send key event.");
                        } else {
                            localDevice2.sendKeyEvent(i2, z);
                        }
                    }
                }
            });
        }

        public void sendVolumeKeyEvent(final int i, final int i2, final boolean z) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.4
                @Override // java.lang.Runnable
                public void run() {
                    if (com.android.server.hdmi.HdmiControlService.this.mCecController == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "CEC controller not available to send volume key event.");
                        return;
                    }
                    com.android.server.hdmi.HdmiCecLocalDevice localDevice = com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.getLocalDevice(i);
                    if (localDevice == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local device " + i + " not available to send volume key event.");
                        return;
                    }
                    localDevice.sendVolumeKeyEvent(i2, z);
                }
            });
        }

        public void oneTouchPlay(final android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            android.util.Slog.d(com.android.server.hdmi.HdmiControlService.TAG, "Process pid: " + android.os.Binder.getCallingPid() + " is calling oneTouchPlay.");
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.5
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiControlService.this.oneTouchPlay(iHdmiControlCallback);
                }
            });
        }

        public void toggleAndFollowTvPower() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            android.util.Slog.d(com.android.server.hdmi.HdmiControlService.TAG, "Process pid: " + android.os.Binder.getCallingPid() + " is calling toggleAndFollowTvPower.");
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.6
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiControlService.this.toggleAndFollowTvPower();
                }
            });
        }

        public boolean shouldHandleTvPowerKey() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            return com.android.server.hdmi.HdmiControlService.this.shouldHandleTvPowerKey();
        }

        public void queryDisplayStatus(final android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.7
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiControlService.this.queryDisplayStatus(iHdmiControlCallback);
                }
            });
        }

        public void addHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.addHdmiControlStatusChangeListener(iHdmiControlStatusChangeListener);
        }

        public void removeHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.removeHdmiControlStatusChangeListener(iHdmiControlStatusChangeListener);
        }

        public void addHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.addHdmiCecVolumeControlFeatureListener(iHdmiCecVolumeControlFeatureListener);
        }

        public void removeHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.removeHdmiControlVolumeControlStatusChangeListener(iHdmiCecVolumeControlFeatureListener);
        }

        public void addHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.addHotplugEventListener(iHdmiHotplugEventListener);
        }

        public void removeHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.removeHotplugEventListener(iHdmiHotplugEventListener);
        }

        public void addDeviceEventListener(android.hardware.hdmi.IHdmiDeviceEventListener iHdmiDeviceEventListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.addDeviceEventListener(iHdmiDeviceEventListener);
        }

        public java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            if (com.android.server.hdmi.HdmiControlService.this.getPortInfo() == null) {
                return java.util.Collections.emptyList();
            }
            return com.android.server.hdmi.HdmiControlService.this.getPortInfo();
        }

        public boolean canChangeSystemAudioMode() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiCecLocalDeviceTv tv = com.android.server.hdmi.HdmiControlService.this.tv();
            if (tv == null) {
                return false;
            }
            return tv.hasSystemAudioDevice();
        }

        public boolean getSystemAudioMode() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiCecLocalDeviceTv tv = com.android.server.hdmi.HdmiControlService.this.tv();
            com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem = com.android.server.hdmi.HdmiControlService.this.audioSystem();
            return (tv != null && tv.isSystemAudioActivated()) || (audioSystem != null && audioSystem.isSystemAudioActivated());
        }

        public int getPhysicalAddress() {
            int physicalAddress;
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                physicalAddress = com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.getPhysicalAddress();
            }
            return physicalAddress;
        }

        public void setSystemAudioMode(final boolean z, final android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.8
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv tv = com.android.server.hdmi.HdmiControlService.this.tv();
                    if (tv == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local tv device not available");
                        com.android.server.hdmi.HdmiControlService.this.invokeCallback(iHdmiControlCallback, 2);
                    } else {
                        tv.changeSystemAudioMode(z, iHdmiControlCallback);
                    }
                }
            });
        }

        public void addSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.addSystemAudioModeChangeListner(iHdmiSystemAudioModeChangeListener);
        }

        public void removeSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.removeSystemAudioModeChangeListener(iHdmiSystemAudioModeChangeListener);
        }

        public void setInputChangeListener(android.hardware.hdmi.IHdmiInputChangeListener iHdmiInputChangeListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.setInputChangeListener(iHdmiInputChangeListener);
        }

        public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getInputDevices() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            return com.android.server.hdmi.HdmiUtils.mergeToUnmodifiableList(com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.getSafeExternalInputsLocked(), com.android.server.hdmi.HdmiControlService.this.getMhlDevicesLocked());
        }

        public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getDeviceList() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            return com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.getSafeCecDevicesLocked();
        }

        public void powerOffRemoteDevice(final int i, final int i2) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.9
                @Override // java.lang.Runnable
                public void run() {
                    android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Device " + i + " power status is " + i2 + " before standby command sent out");
                    com.android.server.hdmi.HdmiControlService.this.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildStandby(com.android.server.hdmi.HdmiControlService.this.getRemoteControlSourceAddress(), i));
                }
            });
        }

        public void powerOnRemoteDevice(final int i, final int i2) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.10
                @Override // java.lang.Runnable
                public void run() {
                    android.util.Slog.i(com.android.server.hdmi.HdmiControlService.TAG, "Device " + i + " power status is " + i2 + " before power on command sent out");
                    if (com.android.server.hdmi.HdmiControlService.this.getSwitchDevice() != null) {
                        com.android.server.hdmi.HdmiControlService.this.getSwitchDevice().sendUserControlPressedAndReleased(i, 109);
                    } else {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Can't get the correct local device to handle routing.");
                    }
                }
            });
        }

        public void askRemoteDeviceToBecomeActiveSource(final int i) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.11
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiCecMessage buildSetStreamPath = com.android.server.hdmi.HdmiCecMessageBuilder.buildSetStreamPath(com.android.server.hdmi.HdmiControlService.this.getRemoteControlSourceAddress(), i);
                    if (com.android.server.hdmi.HdmiControlService.this.pathToPortId(i) != -1) {
                        if (com.android.server.hdmi.HdmiControlService.this.getSwitchDevice() != null) {
                            com.android.server.hdmi.HdmiControlService.this.getSwitchDevice().handleSetStreamPath(buildSetStreamPath);
                        } else {
                            android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Can't get the correct local device to handle routing.");
                        }
                    }
                    com.android.server.hdmi.HdmiControlService.this.sendCecCommand(buildSetStreamPath);
                }
            });
        }

        public void setSystemAudioVolume(final int i, final int i2, final int i3) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.12
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv tv = com.android.server.hdmi.HdmiControlService.this.tv();
                    if (tv == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local tv device not available");
                    } else {
                        tv.changeVolume(i, i2 - i, i3);
                    }
                }
            });
        }

        public void setSystemAudioMute(final boolean z) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.13
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv tv = com.android.server.hdmi.HdmiControlService.this.tv();
                    if (tv == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local tv device not available");
                    } else {
                        tv.changeMute(z);
                    }
                }
            });
        }

        public void setArcMode(final boolean z) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.14
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiCecLocalDeviceTv tv = com.android.server.hdmi.HdmiControlService.this.tv();
                    if (tv == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local tv device not available to change arc mode.");
                    } else {
                        tv.startArcAction(z);
                    }
                }
            });
        }

        public void setProhibitMode(boolean z) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            if (!com.android.server.hdmi.HdmiControlService.this.isTvDevice()) {
                return;
            }
            com.android.server.hdmi.HdmiControlService.this.setProhibitMode(z);
        }

        public void addVendorCommandListener(android.hardware.hdmi.IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.addVendorCommandListener(iHdmiVendorCommandListener, i);
        }

        public void sendVendorCommand(final int i, final int i2, final byte[] bArr, final boolean z) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.15
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiCecLocalDevice localDevice = com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.getLocalDevice(i);
                    if (localDevice == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local device not available");
                    } else if (z) {
                        com.android.server.hdmi.HdmiControlService.this.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildVendorCommandWithId(localDevice.getDeviceInfo().getLogicalAddress(), i2, com.android.server.hdmi.HdmiControlService.this.getVendorId(), bArr));
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildVendorCommand(localDevice.getDeviceInfo().getLogicalAddress(), i2, bArr));
                    }
                }
            });
        }

        public void sendStandby(final int i, final int i2) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.16
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiMhlLocalDeviceStub localDeviceById = com.android.server.hdmi.HdmiControlService.this.mMhlController.getLocalDeviceById(i2);
                    if (localDeviceById != null) {
                        localDeviceById.sendStandby();
                        return;
                    }
                    com.android.server.hdmi.HdmiCecLocalDevice localDevice = com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.getLocalDevice(i);
                    if (localDevice == null) {
                        localDevice = com.android.server.hdmi.HdmiControlService.this.audioSystem();
                    }
                    if (localDevice == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local device not available");
                    } else {
                        localDevice.sendStandby(i2);
                    }
                }
            });
        }

        public void setHdmiRecordListener(android.hardware.hdmi.IHdmiRecordListener iHdmiRecordListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.setHdmiRecordListener(iHdmiRecordListener);
        }

        public void startOneTouchRecord(final int i, final byte[] bArr) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.17
                @Override // java.lang.Runnable
                public void run() {
                    if (!com.android.server.hdmi.HdmiControlService.this.isTvDeviceEnabled()) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "TV device is not enabled.");
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.tv().startOneTouchRecord(i, bArr);
                    }
                }
            });
        }

        public void stopOneTouchRecord(final int i) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.18
                @Override // java.lang.Runnable
                public void run() {
                    if (!com.android.server.hdmi.HdmiControlService.this.isTvDeviceEnabled()) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "TV device is not enabled.");
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.tv().stopOneTouchRecord(i);
                    }
                }
            });
        }

        public void startTimerRecording(final int i, final int i2, final byte[] bArr) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.19
                @Override // java.lang.Runnable
                public void run() {
                    if (!com.android.server.hdmi.HdmiControlService.this.isTvDeviceEnabled()) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "TV device is not enabled.");
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.tv().startTimerRecording(i, i2, bArr);
                    }
                }
            });
        }

        public void clearTimerRecording(final int i, final int i2, final byte[] bArr) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.20
                @Override // java.lang.Runnable
                public void run() {
                    if (!com.android.server.hdmi.HdmiControlService.this.isTvDeviceEnabled()) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "TV device is not enabled.");
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.tv().clearTimerRecording(i, i2, bArr);
                    }
                }
            });
        }

        public void sendMhlVendorCommand(final int i, final int i2, final int i3, final byte[] bArr) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.21
                @Override // java.lang.Runnable
                public void run() {
                    if (!com.android.server.hdmi.HdmiControlService.this.isCecControlEnabled()) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Hdmi control is disabled.");
                        return;
                    }
                    if (com.android.server.hdmi.HdmiControlService.this.mMhlController.getLocalDevice(i) == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Invalid port id:" + i);
                        return;
                    }
                    com.android.server.hdmi.HdmiControlService.this.mMhlController.sendVendorCommand(i, i2, i3, bArr);
                }
            });
        }

        public void addHdmiMhlVendorCommandListener(android.hardware.hdmi.IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.addHdmiMhlVendorCommandListener(iHdmiMhlVendorCommandListener);
        }

        public void setStandbyMode(final boolean z) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.22
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiControlService.this.setStandbyMode(z);
                }
            });
        }

        public void reportAudioStatus(final int i, int i2, int i3, boolean z) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.23
                @Override // java.lang.Runnable
                public void run() {
                    if (com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.getLocalDevice(i) == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Local device not available");
                        return;
                    }
                    if (com.android.server.hdmi.HdmiControlService.this.audioSystem() == null) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "audio system is not available");
                    } else if (!com.android.server.hdmi.HdmiControlService.this.audioSystem().isSystemAudioActivated()) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "audio system is not in system audio mode");
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.audioSystem().reportAudioStatus(0);
                    }
                }
            });
        }

        public void setSystemAudioModeOnForAudioOnlySource() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            com.android.server.hdmi.HdmiControlService.this.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.BinderService.24
                @Override // java.lang.Runnable
                public void run() {
                    if (!com.android.server.hdmi.HdmiControlService.this.isAudioSystemDevice()) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Not an audio system device. Won't set system audio mode on");
                        return;
                    }
                    if (com.android.server.hdmi.HdmiControlService.this.audioSystem() == null) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Audio System local device is not registered");
                    } else if (!com.android.server.hdmi.HdmiControlService.this.audioSystem().checkSupportAndSetSystemAudioMode(true)) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "System Audio Mode is not supported.");
                    } else {
                        com.android.server.hdmi.HdmiControlService.this.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildSetSystemAudioMode(com.android.server.hdmi.HdmiControlService.this.audioSystem().getDeviceInfo().getLogicalAddress(), 15, true));
                    }
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(@android.annotation.Nullable java.io.FileDescriptor fileDescriptor, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor2, @android.annotation.Nullable java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, @android.annotation.Nullable android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            new com.android.server.hdmi.HdmiControlShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.hdmi.HdmiControlService.this.getContext(), com.android.server.hdmi.HdmiControlService.TAG, printWriter)) {
                com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
                synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                    indentingPrintWriter.println("mProhibitMode: " + com.android.server.hdmi.HdmiControlService.this.mProhibitMode);
                }
                indentingPrintWriter.println("mPowerStatus: " + com.android.server.hdmi.HdmiControlService.this.mPowerStatusController.getPowerStatus());
                indentingPrintWriter.println("mIsCecAvailable: " + com.android.server.hdmi.HdmiControlService.this.mIsCecAvailable);
                indentingPrintWriter.println("mCecVersion: " + com.android.server.hdmi.HdmiControlService.this.mCecVersion);
                indentingPrintWriter.println("mIsAbsoluteVolumeBehaviorEnabled: " + com.android.server.hdmi.HdmiControlService.this.isAbsoluteVolumeBehaviorEnabled());
                indentingPrintWriter.println("System_settings:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.println("mMhlInputChangeEnabled: " + com.android.server.hdmi.HdmiControlService.this.isMhlInputChangeEnabled());
                indentingPrintWriter.println("mSystemAudioActivated: " + com.android.server.hdmi.HdmiControlService.this.isSystemAudioActivated());
                indentingPrintWriter.println("mHdmiCecVolumeControlEnabled: " + com.android.server.hdmi.HdmiControlService.this.getHdmiCecVolumeControl());
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("CEC settings:");
                indentingPrintWriter.increaseIndent();
                com.android.server.hdmi.HdmiCecConfig hdmiCecConfig = com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig();
                java.util.List<java.lang.String> allSettings = hdmiCecConfig.getAllSettings();
                java.util.HashSet hashSet = new java.util.HashSet(hdmiCecConfig.getUserSettings());
                for (java.lang.String str : allSettings) {
                    if (hdmiCecConfig.isStringValueType(str)) {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append(str);
                        sb.append(" (string): ");
                        sb.append(hdmiCecConfig.getStringValue(str));
                        sb.append(" (default: ");
                        sb.append(hdmiCecConfig.getDefaultStringValue(str));
                        sb.append(")");
                        sb.append(hashSet.contains(str) ? " [modifiable]" : "");
                        indentingPrintWriter.println(sb.toString());
                    } else if (hdmiCecConfig.isIntValueType(str)) {
                        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                        sb2.append(str);
                        sb2.append(" (int): ");
                        sb2.append(hdmiCecConfig.getIntValue(str));
                        sb2.append(" (default: ");
                        sb2.append(hdmiCecConfig.getDefaultIntValue(str));
                        sb2.append(")");
                        sb2.append(hashSet.contains(str) ? " [modifiable]" : "");
                        indentingPrintWriter.println(sb2.toString());
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("mMhlController: ");
                indentingPrintWriter.increaseIndent();
                com.android.server.hdmi.HdmiControlService.this.mMhlController.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.print("eARC local device: ");
                indentingPrintWriter.increaseIndent();
                if (com.android.server.hdmi.HdmiControlService.this.mEarcLocalDevice == null) {
                    indentingPrintWriter.println("None. eARC is either disabled or not available.");
                } else {
                    com.android.server.hdmi.HdmiControlService.this.mEarcLocalDevice.dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
                com.android.server.hdmi.HdmiControlService.this.mHdmiCecNetwork.dump(indentingPrintWriter);
                if (com.android.server.hdmi.HdmiControlService.this.mCecController != null) {
                    indentingPrintWriter.println("mCecController: ");
                    indentingPrintWriter.increaseIndent();
                    com.android.server.hdmi.HdmiControlService.this.mCecController.dump(indentingPrintWriter);
                    indentingPrintWriter.decreaseIndent();
                }
            }
        }

        public boolean setMessageHistorySize(int i) {
            com.android.server.hdmi.HdmiControlService.this.enforceAccessPermission();
            if (com.android.server.hdmi.HdmiControlService.this.mCecController == null) {
                return false;
            }
            return com.android.server.hdmi.HdmiControlService.this.mCecController.setMessageHistorySize(i);
        }

        public int getMessageHistorySize() {
            com.android.server.hdmi.HdmiControlService.this.enforceAccessPermission();
            if (com.android.server.hdmi.HdmiControlService.this.mCecController != null) {
                return com.android.server.hdmi.HdmiControlService.this.mCecController.getMessageHistorySize();
            }
            return 0;
        }

        public void addCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
            com.android.server.hdmi.HdmiControlService.this.enforceAccessPermission();
            com.android.server.hdmi.HdmiControlService.this.addCecSettingChangeListener(str, iHdmiCecSettingChangeListener);
        }

        public void removeCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
            com.android.server.hdmi.HdmiControlService.this.enforceAccessPermission();
            com.android.server.hdmi.HdmiControlService.this.removeCecSettingChangeListener(str, iHdmiCecSettingChangeListener);
        }

        public java.util.List<java.lang.String> getUserCecSettings() {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig().getUserSettings();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<java.lang.String> getAllowedCecSettingStringValues(java.lang.String str) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig().getAllowedStringValues(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int[] getAllowedCecSettingIntValues(java.lang.String str) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig().getAllowedIntValues(str).stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.hdmi.HdmiControlService$BinderService$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(java.lang.Object obj) {
                        int intValue;
                        intValue = ((java.lang.Integer) obj).intValue();
                        return intValue;
                    }
                }).toArray();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.lang.String getCecSettingStringValue(java.lang.String str) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig().getStringValue(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setCecSettingStringValue(java.lang.String str, java.lang.String str2) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig().setStringValue(str, str2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getCecSettingIntValue(java.lang.String str) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig().getIntValue(str);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setCecSettingIntValue(java.lang.String str, int i) {
            com.android.server.hdmi.HdmiControlService.this.initBinderCall();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.hdmi.HdmiControlService.this.getHdmiCecConfig().setIntValue(str, i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setHdmiCecVolumeControlEnabledInternal(int i) {
        this.mHdmiCecVolumeControl = i;
        announceHdmiCecVolumeControlFeatureChange(i);
        runOnServiceThread(new com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda2(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRemoteControlSourceAddress() {
        if (isAudioSystemDevice()) {
            return audioSystem().getDeviceInfo().getLogicalAddress();
        }
        if (isPlaybackDevice()) {
            return playback().getDeviceInfo().getLogicalAddress();
        }
        return 15;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.hdmi.HdmiCecLocalDeviceSource getSwitchDevice() {
        if (isAudioSystemDevice()) {
            return audioSystem();
        }
        if (isPlaybackDevice()) {
            return playback();
        }
        return null;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void oneTouchPlay(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (!this.mAddressAllocated) {
            this.mOtpCallbackPendingAddressAllocation = iHdmiControlCallback;
            android.util.Slog.d(TAG, "Local device is under address allocation. Save OTP callback for later process.");
            return;
        }
        com.android.server.hdmi.HdmiCecLocalDeviceSource playback = playback();
        if (playback == null) {
            playback = audioSystem();
        }
        if (playback == null) {
            android.util.Slog.w(TAG, "Local source device not available");
            invokeCallback(iHdmiControlCallback, 2);
        } else {
            playback.oneTouchPlay(iHdmiControlCallback);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void toggleAndFollowTvPower() {
        assertRunOnServiceThread();
        com.android.server.hdmi.HdmiCecLocalDeviceSource playback = playback();
        if (playback == null) {
            playback = audioSystem();
        }
        if (playback == null) {
            android.util.Slog.w(TAG, "Local source device not available");
        } else {
            playback.toggleAndFollowTvPower();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean shouldHandleTvPowerKey() {
        if (isTvDevice() || getHdmiCecConfig().getStringValue("power_control_mode").equals("none") || getHdmiCecConfig().getIntValue("hdmi_cec_enabled") != 1) {
            return false;
        }
        return this.mIsCecAvailable;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    protected void queryDisplayStatus(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (!this.mAddressAllocated) {
            this.mDisplayStatusCallback = iHdmiControlCallback;
            android.util.Slog.d(TAG, "Local device is under address allocation. Queue display callback for later process.");
            return;
        }
        com.android.server.hdmi.HdmiCecLocalDeviceSource playback = playback();
        if (playback == null) {
            playback = audioSystem();
        }
        if (playback == null) {
            android.util.Slog.w(TAG, "Local source device not available");
            invokeCallback(iHdmiControlCallback, -1);
        } else {
            playback.queryDisplayStatus(iHdmiControlCallback);
        }
    }

    protected android.hardware.hdmi.HdmiDeviceInfo getActiveSource() {
        int activePath;
        if (playback() != null && playback().isActiveSource()) {
            return playback().getDeviceInfo();
        }
        com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource localActiveSource = getLocalActiveSource();
        if (localActiveSource.isValid()) {
            android.hardware.hdmi.HdmiDeviceInfo safeCecDeviceInfo = this.mHdmiCecNetwork.getSafeCecDeviceInfo(localActiveSource.logicalAddress);
            if (safeCecDeviceInfo != null) {
                return safeCecDeviceInfo;
            }
            return android.hardware.hdmi.HdmiDeviceInfo.hardwarePort(localActiveSource.physicalAddress, pathToPortId(localActiveSource.physicalAddress));
        }
        if (tv() != null && (activePath = tv().getActivePath()) != 65535) {
            android.hardware.hdmi.HdmiDeviceInfo safeDeviceInfoByPath = this.mHdmiCecNetwork.getSafeDeviceInfoByPath(activePath);
            return safeDeviceInfoByPath != null ? safeDeviceInfoByPath : android.hardware.hdmi.HdmiDeviceInfo.hardwarePort(activePath, tv().getActivePortId());
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    void addHdmiControlStatusChangeListener(final android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
        final com.android.server.hdmi.HdmiControlService.HdmiControlStatusChangeListenerRecord hdmiControlStatusChangeListenerRecord = new com.android.server.hdmi.HdmiControlService.HdmiControlStatusChangeListenerRecord(iHdmiControlStatusChangeListener);
        try {
            iHdmiControlStatusChangeListener.asBinder().linkToDeath(hdmiControlStatusChangeListenerRecord, 0);
            synchronized (this.mLock) {
                this.mHdmiControlStatusChangeListenerRecords.add(hdmiControlStatusChangeListenerRecord);
            }
            runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.17
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                        try {
                            if (com.android.server.hdmi.HdmiControlService.this.mHdmiControlStatusChangeListenerRecords.contains(hdmiControlStatusChangeListenerRecord)) {
                                synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                                    com.android.server.hdmi.HdmiControlService.this.invokeHdmiControlStatusChangeListenerLocked(iHdmiControlStatusChangeListener, com.android.server.hdmi.HdmiControlService.this.mHdmiControlEnabled);
                                }
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Listener already died");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeHdmiControlStatusChangeListener(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.hdmi.HdmiControlService.HdmiControlStatusChangeListenerRecord> it = this.mHdmiControlStatusChangeListenerRecords.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    com.android.server.hdmi.HdmiControlService.HdmiControlStatusChangeListenerRecord next = it.next();
                    if (next.mListener.asBinder() == iHdmiControlStatusChangeListener.asBinder()) {
                        iHdmiControlStatusChangeListener.asBinder().unlinkToDeath(next, 0);
                        this.mHdmiControlStatusChangeListenerRecords.remove(next);
                        break;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addHdmiCecVolumeControlFeatureListener(final android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
        this.mHdmiCecVolumeControlFeatureListenerRecords.register(iHdmiCecVolumeControlFeatureListener);
        runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.18
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                    try {
                        iHdmiCecVolumeControlFeatureListener.onHdmiCecVolumeControlFeature(com.android.server.hdmi.HdmiControlService.this.mHdmiCecVolumeControl);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Failed to report HdmiControlVolumeControlStatusChange: " + com.android.server.hdmi.HdmiControlService.this.mHdmiCecVolumeControl, e);
                    }
                }
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    void removeHdmiControlVolumeControlStatusChangeListener(android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
        this.mHdmiCecVolumeControlFeatureListenerRecords.unregister(iHdmiCecVolumeControlFeatureListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addHotplugEventListener(final android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
        final com.android.server.hdmi.HdmiControlService.HotplugEventListenerRecord hotplugEventListenerRecord = new com.android.server.hdmi.HdmiControlService.HotplugEventListenerRecord(iHdmiHotplugEventListener);
        try {
            iHdmiHotplugEventListener.asBinder().linkToDeath(hotplugEventListenerRecord, 0);
            synchronized (this.mLock) {
                this.mHotplugEventListenerRecords.add(hotplugEventListenerRecord);
            }
            runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.19
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                        try {
                            if (com.android.server.hdmi.HdmiControlService.this.mHotplugEventListenerRecords.contains(hotplugEventListenerRecord)) {
                                for (android.hardware.hdmi.HdmiPortInfo hdmiPortInfo : com.android.server.hdmi.HdmiControlService.this.getPortInfo()) {
                                    android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent = new android.hardware.hdmi.HdmiHotplugEvent(hdmiPortInfo.getId(), com.android.server.hdmi.HdmiControlService.this.mCecController.isConnected(hdmiPortInfo.getId()));
                                    synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                                        com.android.server.hdmi.HdmiControlService.this.invokeHotplugEventListenerLocked(iHdmiHotplugEventListener, hdmiHotplugEvent);
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
            });
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Listener already died");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeHotplugEventListener(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.hdmi.HdmiControlService.HotplugEventListenerRecord> it = this.mHotplugEventListenerRecords.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    com.android.server.hdmi.HdmiControlService.HotplugEventListenerRecord next = it.next();
                    if (next.mListener.asBinder() == iHdmiHotplugEventListener.asBinder()) {
                        iHdmiHotplugEventListener.asBinder().unlinkToDeath(next, 0);
                        this.mHotplugEventListenerRecords.remove(next);
                        break;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDeviceEventListener(android.hardware.hdmi.IHdmiDeviceEventListener iHdmiDeviceEventListener) {
        com.android.server.hdmi.HdmiControlService.DeviceEventListenerRecord deviceEventListenerRecord = new com.android.server.hdmi.HdmiControlService.DeviceEventListenerRecord(iHdmiDeviceEventListener);
        try {
            iHdmiDeviceEventListener.asBinder().linkToDeath(deviceEventListenerRecord, 0);
            synchronized (this.mLock) {
                this.mDeviceEventListenerRecords.add(deviceEventListenerRecord);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Listener already died");
        }
    }

    void invokeDeviceEventListeners(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, int i) {
        synchronized (this.mLock) {
            java.util.Iterator<com.android.server.hdmi.HdmiControlService.DeviceEventListenerRecord> it = this.mDeviceEventListenerRecords.iterator();
            while (it.hasNext()) {
                try {
                    it.next().mListener.onStatusChanged(hdmiDeviceInfo, i);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Failed to report device event:" + e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSystemAudioModeChangeListner(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
        com.android.server.hdmi.HdmiControlService.SystemAudioModeChangeListenerRecord systemAudioModeChangeListenerRecord = new com.android.server.hdmi.HdmiControlService.SystemAudioModeChangeListenerRecord(iHdmiSystemAudioModeChangeListener);
        try {
            iHdmiSystemAudioModeChangeListener.asBinder().linkToDeath(systemAudioModeChangeListenerRecord, 0);
            synchronized (this.mLock) {
                this.mSystemAudioModeChangeListenerRecords.add(systemAudioModeChangeListenerRecord);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Listener already died");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeSystemAudioModeChangeListener(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.hdmi.HdmiControlService.SystemAudioModeChangeListenerRecord> it = this.mSystemAudioModeChangeListenerRecords.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    com.android.server.hdmi.HdmiControlService.SystemAudioModeChangeListenerRecord next = it.next();
                    if (next.mListener.asBinder() == iHdmiSystemAudioModeChangeListener) {
                        iHdmiSystemAudioModeChangeListener.asBinder().unlinkToDeath(next, 0);
                        this.mSystemAudioModeChangeListenerRecords.remove(next);
                        break;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private final class InputChangeListenerRecord implements android.os.IBinder.DeathRecipient {
        private final android.hardware.hdmi.IHdmiInputChangeListener mListener;

        public InputChangeListenerRecord(android.hardware.hdmi.IHdmiInputChangeListener iHdmiInputChangeListener) {
            this.mListener = iHdmiInputChangeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                try {
                    if (com.android.server.hdmi.HdmiControlService.this.mInputChangeListenerRecord == this) {
                        com.android.server.hdmi.HdmiControlService.this.mInputChangeListenerRecord = null;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setInputChangeListener(android.hardware.hdmi.IHdmiInputChangeListener iHdmiInputChangeListener) {
        synchronized (this.mLock) {
            try {
                this.mInputChangeListenerRecord = new com.android.server.hdmi.HdmiControlService.InputChangeListenerRecord(iHdmiInputChangeListener);
                try {
                    iHdmiInputChangeListener.asBinder().linkToDeath(this.mInputChangeListenerRecord, 0);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Listener already died");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void invokeInputChangeListener(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        synchronized (this.mLock) {
            if (this.mInputChangeListenerRecord != null) {
                try {
                    this.mInputChangeListenerRecord.mListener.onChanged(hdmiDeviceInfo);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Exception thrown by IHdmiInputChangeListener: " + e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHdmiRecordListener(android.hardware.hdmi.IHdmiRecordListener iHdmiRecordListener) {
        synchronized (this.mLock) {
            this.mRecordListenerRecord = new com.android.server.hdmi.HdmiControlService.HdmiRecordListenerRecord(iHdmiRecordListener);
            try {
                iHdmiRecordListener.asBinder().linkToDeath(this.mRecordListenerRecord, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Listener already died.", e);
            }
        }
    }

    byte[] invokeRecordRequestListener(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mRecordListenerRecord != null) {
                    try {
                        return this.mRecordListenerRecord.mListener.getOneTouchRecordSource(i);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to start record.", e);
                    }
                }
                return libcore.util.EmptyArray.BYTE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void invokeOneTouchRecordResult(int i, int i2) {
        synchronized (this.mLock) {
            if (this.mRecordListenerRecord != null) {
                try {
                    this.mRecordListenerRecord.mListener.onOneTouchRecordResult(i, i2);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Failed to call onOneTouchRecordResult.", e);
                }
            }
        }
    }

    void invokeTimerRecordingResult(int i, int i2) {
        synchronized (this.mLock) {
            if (this.mRecordListenerRecord != null) {
                try {
                    this.mRecordListenerRecord.mListener.onTimerRecordingResult(i, i2);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Failed to call onTimerRecordingResult.", e);
                }
            }
        }
    }

    void invokeClearTimerRecordingResult(int i, int i2) {
        synchronized (this.mLock) {
            if (this.mRecordListenerRecord != null) {
                try {
                    this.mRecordListenerRecord.mListener.onClearTimerRecordingResult(i, i2);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Failed to call onClearTimerRecordingResult.", e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeCallback(android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback, int i) {
        if (iHdmiControlCallback == null) {
            return;
        }
        try {
            iHdmiControlCallback.onComplete(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Invoking callback failed:" + e);
        }
    }

    private void invokeSystemAudioModeChangeLocked(android.hardware.hdmi.IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener, boolean z) {
        try {
            iHdmiSystemAudioModeChangeListener.onStatusChanged(z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Invoking callback failed:" + e);
        }
    }

    private void announceHotplugEvent(int i, boolean z) {
        android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent = new android.hardware.hdmi.HdmiHotplugEvent(i, z);
        synchronized (this.mLock) {
            try {
                java.util.Iterator<com.android.server.hdmi.HdmiControlService.HotplugEventListenerRecord> it = this.mHotplugEventListenerRecords.iterator();
                while (it.hasNext()) {
                    invokeHotplugEventListenerLocked(it.next().mListener, hdmiHotplugEvent);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeHotplugEventListenerLocked(android.hardware.hdmi.IHdmiHotplugEventListener iHdmiHotplugEventListener, android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent) {
        try {
            iHdmiHotplugEventListener.onReceived(hdmiHotplugEvent);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to report hotplug event:" + hdmiHotplugEvent.toString(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void announceHdmiControlStatusChange(int i) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mHdmiControlStatusChangeListenerRecords.size());
                java.util.Iterator<com.android.server.hdmi.HdmiControlService.HdmiControlStatusChangeListenerRecord> it = this.mHdmiControlStatusChangeListenerRecords.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().mListener);
                }
                invokeHdmiControlStatusChangeListenerLocked(arrayList, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeHdmiControlStatusChangeListenerLocked(android.hardware.hdmi.IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener, int i) {
        invokeHdmiControlStatusChangeListenerLocked(java.util.Collections.singletonList(iHdmiControlStatusChangeListener), i);
    }

    private void invokeHdmiControlStatusChangeListenerLocked(final java.util.Collection<android.hardware.hdmi.IHdmiControlStatusChangeListener> collection, final int i) {
        if (i == 1) {
            queryDisplayStatus(new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiControlService.20
                public void onComplete(int i2) {
                    com.android.server.hdmi.HdmiControlService.this.mIsCecAvailable = i2 != -1;
                    if (!collection.isEmpty()) {
                        com.android.server.hdmi.HdmiControlService.this.invokeHdmiControlStatusChangeListenerLocked(collection, i, com.android.server.hdmi.HdmiControlService.this.mIsCecAvailable);
                    }
                }
            });
            return;
        }
        this.mIsCecAvailable = false;
        if (!collection.isEmpty()) {
            invokeHdmiControlStatusChangeListenerLocked(collection, i, this.mIsCecAvailable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeHdmiControlStatusChangeListenerLocked(java.util.Collection<android.hardware.hdmi.IHdmiControlStatusChangeListener> collection, int i, boolean z) {
        java.util.Iterator<android.hardware.hdmi.IHdmiControlStatusChangeListener> it = collection.iterator();
        while (it.hasNext()) {
            try {
                it.next().onStatusChange(i, z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to report HdmiControlStatusChange: " + i + " isAvailable: " + z, e);
            }
        }
    }

    private void announceHdmiCecVolumeControlFeatureChange(final int i) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            this.mHdmiCecVolumeControlFeatureListenerRecords.broadcast(new java.util.function.Consumer() { // from class: com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.hdmi.HdmiControlService.lambda$announceHdmiCecVolumeControlFeatureChange$2(i, (android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$announceHdmiCecVolumeControlFeatureChange$2(int i, android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
        try {
            iHdmiCecVolumeControlFeatureListener.onHdmiCecVolumeControlFeature(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to report HdmiControlVolumeControlStatusChange: " + i);
        }
    }

    public com.android.server.hdmi.HdmiCecLocalDeviceTv tv() {
        return (com.android.server.hdmi.HdmiCecLocalDeviceTv) this.mHdmiCecNetwork.getLocalDevice(0);
    }

    boolean isTvDevice() {
        return this.mCecLocalDevices.contains(0);
    }

    boolean isAudioSystemDevice() {
        return this.mCecLocalDevices.contains(5);
    }

    boolean isPlaybackDevice() {
        return this.mCecLocalDevices.contains(4);
    }

    boolean isSwitchDevice() {
        return ((java.lang.Boolean) android.sysprop.HdmiProperties.is_switch().orElse(false)).booleanValue();
    }

    boolean isTvDeviceEnabled() {
        return isTvDevice() && tv() != null;
    }

    protected com.android.server.hdmi.HdmiCecLocalDevicePlayback playback() {
        return (com.android.server.hdmi.HdmiCecLocalDevicePlayback) this.mHdmiCecNetwork.getLocalDevice(4);
    }

    public com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem() {
        return (com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem) this.mHdmiCecNetwork.getLocalDevice(5);
    }

    @android.annotation.Nullable
    com.android.server.hdmi.AudioManagerWrapper getAudioManager() {
        return this.mAudioManager;
    }

    @android.annotation.Nullable
    private com.android.server.hdmi.AudioDeviceVolumeManagerWrapper getAudioDeviceVolumeManager() {
        return this.mAudioDeviceVolumeManager;
    }

    boolean isCecControlEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = true;
            if (this.mHdmiControlEnabled != 1) {
                z = false;
            }
        }
        return z;
    }

    public boolean isEarcEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEarcEnabled;
        }
        return z;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    protected boolean isEarcSupported() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEarcSupported;
        }
        return z;
    }

    private boolean isDsmEnabled() {
        return this.mHdmiCecConfig.getIntValue("soundbar_mode") == 1;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isArcSupported() {
        return android.os.SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int getPowerStatus() {
        assertRunOnServiceThread();
        return this.mPowerStatusController.getPowerStatus();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    void setPowerStatus(int i) {
        assertRunOnServiceThread();
        this.mPowerStatusController.setPowerStatus(i);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isPowerOnOrTransient() {
        assertRunOnServiceThread();
        return this.mPowerStatusController.isPowerStatusOn() || this.mPowerStatusController.isPowerStatusTransientToOn();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isPowerStandbyOrTransient() {
        assertRunOnServiceThread();
        return this.mPowerStatusController.isPowerStatusStandby() || this.mPowerStatusController.isPowerStatusTransientToStandby();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    boolean isPowerStandby() {
        assertRunOnServiceThread();
        return this.mPowerStatusController.isPowerStatusStandby();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void wakeUp() {
        assertRunOnServiceThread();
        this.mWakeUpMessageReceived = true;
        this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 8, "android.server.hdmi:WAKE");
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void standby() {
        assertRunOnServiceThread();
        if (!canGoToStandby()) {
            return;
        }
        this.mStandbyMessageReceived = true;
        this.mPowerManager.goToSleep(android.os.SystemClock.uptimeMillis(), 5, 0);
    }

    boolean isWakeUpMessageReceived() {
        return this.mWakeUpMessageReceived;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isStandbyMessageReceived() {
        return this.mStandbyMessageReceived;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void onWakeUp(int i) {
        int i2;
        assertRunOnServiceThread();
        int i3 = 2;
        this.mPowerStatusController.setPowerStatus(2, false);
        if (this.mCecController != null) {
            if (isCecControlEnabled()) {
                switch (i) {
                    case 0:
                        if (!this.mWakeUpMessageReceived) {
                            i2 = 2;
                            break;
                        } else {
                            i2 = 3;
                            break;
                        }
                    case 1:
                        i2 = 1;
                        break;
                    default:
                        android.util.Slog.e(TAG, "wakeUpAction " + i + " not defined.");
                        return;
                }
                initializeCec(i2);
            }
        } else {
            android.util.Slog.i(TAG, "Device does not support HDMI-CEC.");
        }
        if (isEarcSupported()) {
            if (isEarcEnabled()) {
                switch (i) {
                    case 1:
                        i3 = 1;
                    case 0:
                        initializeEarc(i3);
                        break;
                    default:
                        android.util.Slog.e(TAG, "wakeUpAction " + i + " not defined.");
                        break;
                }
                return;
            }
            setEarcEnabledInHal(false, false);
        }
        if (isTvDevice()) {
            int earcStatus = getEarcStatus();
            getAtomWriter().earcStatusChanged(isEarcSupported(), isEarcEnabled(), earcStatus, earcStatus, 1);
        } else if (isPlaybackDevice()) {
            getAtomWriter().dsmStatusChanged(isArcSupported(), isDsmEnabled(), 1);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void onStandby(final int i) {
        if (shouldAcquireWakeLockOnStandby()) {
            acquireWakeLock();
        }
        this.mWakeUpMessageReceived = false;
        assertRunOnServiceThread();
        this.mPowerStatusController.setPowerStatus(3, false);
        invokeVendorCommandListenersOnControlStateChanged(false, 3);
        final java.util.List<com.android.server.hdmi.HdmiCecLocalDevice> allCecLocalDevices = getAllCecLocalDevices();
        if (!isStandbyMessageReceived() && !canGoToStandby()) {
            this.mPowerStatusController.setPowerStatus(1);
            java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = allCecLocalDevices.iterator();
            while (it.hasNext()) {
                it.next().onStandby(this.mStandbyMessageReceived, i);
            }
            return;
        }
        disableCecLocalDevices(new com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback() { // from class: com.android.server.hdmi.HdmiControlService.21
            @Override // com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback
            public void onCleared(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
                android.util.Slog.v(com.android.server.hdmi.HdmiControlService.TAG, "On standby-action cleared:" + hdmiCecLocalDevice.mDeviceType);
                allCecLocalDevices.remove(hdmiCecLocalDevice);
                if (allCecLocalDevices.isEmpty()) {
                    com.android.server.hdmi.HdmiControlService.this.onPendingActionsCleared(i);
                }
            }
        });
        checkAndUpdateAbsoluteVolumeBehavior();
    }

    boolean canGoToStandby() {
        java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = this.mHdmiCecNetwork.getLocalDeviceList().iterator();
        while (it.hasNext()) {
            if (!it.next().canGoToStandby()) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void onLanguageChanged(java.lang.String str) {
        assertRunOnServiceThread();
        this.mMenuLanguage = str;
        if (isTvDeviceEnabled()) {
            tv().broadcastMenuLanguage(str);
            this.mCecController.setLanguage(str);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    java.lang.String getLanguage() {
        assertRunOnServiceThread();
        return this.mMenuLanguage;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void disableCecLocalDevices(com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        if (this.mCecController != null) {
            java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = this.mHdmiCecNetwork.getLocalDeviceList().iterator();
            while (it.hasNext()) {
                it.next().disableDevice(this.mStandbyMessageReceived, pendingActionClearedCallback);
            }
        }
        this.mMhlController.clearAllLocalDevices();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void clearCecLocalDevices() {
        assertRunOnServiceThread();
        if (this.mCecController == null) {
            return;
        }
        this.mCecController.clearLogicalAddress();
        this.mHdmiCecNetwork.clearLocalDevices();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void onPendingActionsCleared(int i) {
        assertRunOnServiceThread();
        android.util.Slog.v(TAG, "onPendingActionsCleared");
        final int size = getAllCecLocalDevices().size();
        final int[] iArr = new int[1];
        com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback = new com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback() { // from class: com.android.server.hdmi.HdmiControlService.22
            @Override // com.android.server.hdmi.HdmiCecLocalDevice.StandbyCompletedCallback
            public void onStandbyCompleted() {
                int i2 = size;
                int[] iArr2 = iArr;
                int i3 = iArr2[0] + 1;
                iArr2[0] = i3;
                if (i2 < i3) {
                    return;
                }
                com.android.server.hdmi.HdmiControlService.this.releaseWakeLock();
                if (com.android.server.hdmi.HdmiControlService.this.isAudioSystemDevice() || !com.android.server.hdmi.HdmiControlService.this.isPowerStandby()) {
                    return;
                }
                com.android.server.hdmi.HdmiControlService.this.mCecController.enableSystemCecControl(false);
                com.android.server.hdmi.HdmiControlService.this.mMhlController.setOption(104, 0);
            }
        };
        if (this.mPowerStatusController.isPowerStatusTransientToStandby()) {
            this.mPowerStatusController.setPowerStatus(1);
            java.util.Iterator<com.android.server.hdmi.HdmiCecLocalDevice> it = this.mHdmiCecNetwork.getLocalDeviceList().iterator();
            while (it.hasNext()) {
                it.next().onStandby(this.mStandbyMessageReceived, i, standbyCompletedCallback);
            }
        }
        this.mStandbyMessageReceived = false;
    }

    private boolean shouldAcquireWakeLockOnStandby() {
        boolean z;
        if (tv() != null) {
            z = this.mHdmiCecConfig.getIntValue("tv_send_standby_on_sleep") == 1;
        } else if (playback() == null) {
            z = false;
        } else {
            z = !this.mHdmiCecConfig.getStringValue("power_control_mode").equals("none");
        }
        return isCecControlEnabled() && isPowerOnOrTransient() && z;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void acquireWakeLock() {
        releaseWakeLock();
        this.mWakeLock = this.mPowerManager.newWakeLock(1, TAG);
        this.mWakeLock.acquire(5000L);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void releaseWakeLock() {
        if (this.mWakeLock != null) {
            try {
                if (this.mWakeLock.isHeld()) {
                    this.mWakeLock.release();
                }
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.w(TAG, "Exception when releasing wake lock.");
            }
            this.mWakeLock = null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addVendorCommandListener(android.hardware.hdmi.IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) {
        com.android.server.hdmi.HdmiControlService.VendorCommandListenerRecord vendorCommandListenerRecord = new com.android.server.hdmi.HdmiControlService.VendorCommandListenerRecord(iHdmiVendorCommandListener, i);
        try {
            iHdmiVendorCommandListener.asBinder().linkToDeath(vendorCommandListenerRecord, 0);
            synchronized (this.mLock) {
                this.mVendorCommandListenerRecords.add(vendorCommandListenerRecord);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Listener already died");
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:12|(2:14|(2:16|17))|18|19|20|21|22|17|10) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004b, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004d, code lost:
    
        android.util.Slog.e(com.android.server.hdmi.HdmiControlService.TAG, "Failed to notify vendor command reception", r3);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean invokeVendorCommandListenersOnReceived(int i, int i2, int i3, byte[] bArr, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mVendorCommandListenerRecords.isEmpty()) {
                    return false;
                }
                java.util.Iterator<com.android.server.hdmi.HdmiControlService.VendorCommandListenerRecord> it = this.mVendorCommandListenerRecords.iterator();
                boolean z2 = false;
                while (it.hasNext()) {
                    com.android.server.hdmi.HdmiControlService.VendorCommandListenerRecord next = it.next();
                    if (z) {
                        if (next.mVendorId != ((bArr[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 16) + ((bArr[1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) << 8) + (bArr[2] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE)) {
                        }
                    }
                    next.mListener.onReceived(i2, i3, bArr, z);
                    z2 = true;
                }
                return z2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean invokeVendorCommandListenersOnControlStateChanged(boolean z, int i) {
        synchronized (this.mLock) {
            try {
                if (this.mVendorCommandListenerRecords.isEmpty()) {
                    return false;
                }
                java.util.Iterator<com.android.server.hdmi.HdmiControlService.VendorCommandListenerRecord> it = this.mVendorCommandListenerRecords.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().mListener.onControlStateChanged(z, i);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "Failed to notify control-state-changed to vendor handler", e);
                    }
                }
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addHdmiMhlVendorCommandListener(android.hardware.hdmi.IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) {
        com.android.server.hdmi.HdmiControlService.HdmiMhlVendorCommandListenerRecord hdmiMhlVendorCommandListenerRecord = new com.android.server.hdmi.HdmiControlService.HdmiMhlVendorCommandListenerRecord(iHdmiMhlVendorCommandListener);
        try {
            iHdmiMhlVendorCommandListener.asBinder().linkToDeath(hdmiMhlVendorCommandListenerRecord, 0);
            synchronized (this.mLock) {
                this.mMhlVendorCommandListenerRecords.add(hdmiMhlVendorCommandListenerRecord);
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Listener already died.");
        }
    }

    void invokeMhlVendorCommandListeners(int i, int i2, int i3, byte[] bArr) {
        synchronized (this.mLock) {
            java.util.Iterator<com.android.server.hdmi.HdmiControlService.HdmiMhlVendorCommandListenerRecord> it = this.mMhlVendorCommandListenerRecords.iterator();
            while (it.hasNext()) {
                try {
                    it.next().mListener.onReceived(i, i2, i3, bArr);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Failed to notify MHL vendor command", e);
                }
            }
        }
    }

    void setStandbyMode(boolean z) {
        assertRunOnServiceThread();
        if (isPowerOnOrTransient() && z) {
            this.mPowerManager.goToSleep(android.os.SystemClock.uptimeMillis(), 5, 0);
            if (playback() != null) {
                playback().sendStandby(0);
                return;
            }
            return;
        }
        if (isPowerStandbyOrTransient() && !z) {
            this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 8, "android.server.hdmi:WAKE");
            if (playback() != null) {
                oneTouchPlay(new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiControlService.23
                    public void onComplete(int i) {
                        if (i != 0) {
                            android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "Failed to complete 'one touch play'. result=" + i);
                        }
                    }
                });
            }
        }
    }

    int getHdmiCecVolumeControl() {
        int i;
        synchronized (this.mLock) {
            i = this.mHdmiCecVolumeControl;
        }
        return i;
    }

    boolean isProhibitMode() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mProhibitMode;
        }
        return z;
    }

    void setProhibitMode(boolean z) {
        synchronized (this.mLock) {
            this.mProhibitMode = z;
        }
    }

    boolean isSystemAudioActivated() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSystemAudioActivated;
        }
        return z;
    }

    void setSystemAudioActivated(boolean z) {
        synchronized (this.mLock) {
            this.mSystemAudioActivated = z;
        }
        runOnServiceThread(new com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda2(this));
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setCecEnabled(int i) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            this.mHdmiControlEnabled = i;
        }
        if (i == 1) {
            onEnableCec();
            setHdmiCecVolumeControlEnabledInternal(getHdmiCecConfig().getIntValue("volume_control_enabled"));
        } else {
            setHdmiCecVolumeControlEnabledInternal(0);
            invokeVendorCommandListenersOnControlStateChanged(false, 1);
            runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.24
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.HdmiControlService.this.onDisableCec();
                }
            });
            announceHdmiControlStatusChange(i);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void onEnableCec() {
        this.mCecController.enableCec(true);
        this.mCecController.enableSystemCecControl(true);
        this.mMhlController.setOption(103, 1);
        initializeCec(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void onDisableCec() {
        disableCecLocalDevices(new com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback() { // from class: com.android.server.hdmi.HdmiControlService.25
            @Override // com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback
            public void onCleared(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
                com.android.server.hdmi.HdmiControlService.this.assertRunOnServiceThread();
                com.android.server.hdmi.HdmiControlService.this.mCecController.flush(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.25.1
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.hdmi.HdmiControlService.this.mCecController.enableCec(false);
                        com.android.server.hdmi.HdmiControlService.this.mCecController.enableSystemCecControl(false);
                        com.android.server.hdmi.HdmiControlService.this.mMhlController.setOption(103, 0);
                        com.android.server.hdmi.HdmiControlService.this.clearCecLocalDevices();
                    }
                });
            }
        });
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setActivePortId(int i) {
        assertRunOnServiceThread();
        this.mActivePortId = i;
        setLastInputForMhl(-1);
    }

    com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource getLocalActiveSource() {
        com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource activeSource;
        synchronized (this.mLock) {
            activeSource = this.mActiveSource;
        }
        return activeSource;
    }

    @com.android.internal.annotations.VisibleForTesting
    void pauseActiveMediaSessions() {
        java.util.Iterator<android.media.session.MediaController> it = ((android.media.session.MediaSessionManager) getContext().getSystemService(android.media.session.MediaSessionManager.class)).getActiveSessions(null).iterator();
        while (it.hasNext()) {
            it.next().getTransportControls().pause();
        }
    }

    void setActiveSource(int i, int i2, java.lang.String str) {
        synchronized (this.mLock) {
            this.mActiveSource.logicalAddress = i;
            this.mActiveSource.physicalAddress = i2;
        }
        getAtomWriter().activeSourceChanged(i, i2, com.android.server.hdmi.HdmiUtils.pathRelationship(getPhysicalAddress(), i2));
        for (com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice : getAllCecLocalDevices()) {
            hdmiCecLocalDevice.addActiveSourceHistoryItem(new com.android.server.hdmi.HdmiCecLocalDevice.ActiveSource(i, i2), i == hdmiCecLocalDevice.getDeviceInfo().getLogicalAddress() && i2 == getPhysicalAddress(), str);
        }
        runOnServiceThread(new com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda2(this));
    }

    protected void setAndBroadcastActiveSource(int i, int i2, int i3, java.lang.String str) {
        if (i2 == 4) {
            com.android.server.hdmi.HdmiCecLocalDevicePlayback playback = playback();
            playback.setActiveSource(playback.getDeviceInfo().getLogicalAddress(), i, str);
            playback.wakeUpIfActiveSource();
            playback.maySendActiveSource(i3);
        }
        if (i2 == 5) {
            com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem = audioSystem();
            if (playback() == null) {
                audioSystem.setActiveSource(audioSystem.getDeviceInfo().getLogicalAddress(), i, str);
                audioSystem.wakeUpIfActiveSource();
                audioSystem.maySendActiveSource(i3);
            }
        }
    }

    protected void setAndBroadcastActiveSourceFromOneDeviceType(int i, int i2, java.lang.String str) {
        com.android.server.hdmi.HdmiCecLocalDevicePlayback playback = playback();
        com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem audioSystem = audioSystem();
        if (playback != null) {
            playback.setActiveSource(playback.getDeviceInfo().getLogicalAddress(), i2, str);
            playback.wakeUpIfActiveSource();
            playback.maySendActiveSource(i);
        } else if (audioSystem != null) {
            audioSystem.setActiveSource(audioSystem.getDeviceInfo().getLogicalAddress(), i2, str);
            audioSystem.wakeUpIfActiveSource();
            audioSystem.maySendActiveSource(i);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void setLastInputForMhl(int i) {
        assertRunOnServiceThread();
        this.mLastInputMhl = i;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    int getLastInputForMhl() {
        assertRunOnServiceThread();
        return this.mLastInputMhl;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void changeInputForMhl(int i, boolean z) {
        assertRunOnServiceThread();
        if (tv() == null) {
            return;
        }
        final int activePortId = z ? tv().getActivePortId() : -1;
        if (i != -1) {
            tv().doManualPortSwitching(i, new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiControlService.26
                public void onComplete(int i2) throws android.os.RemoteException {
                    com.android.server.hdmi.HdmiControlService.this.setLastInputForMhl(activePortId);
                }
            });
        }
        tv().setActivePortId(i);
        com.android.server.hdmi.HdmiMhlLocalDeviceStub localDevice = this.mMhlController.getLocalDevice(i);
        invokeInputChangeListener(localDevice != null ? localDevice.getInfo() : this.mHdmiCecNetwork.getDeviceForPortId(i));
    }

    void setMhlInputChangeEnabled(boolean z) {
        this.mMhlController.setOption(101, toInt(z));
        synchronized (this.mLock) {
            this.mMhlInputChangeEnabled = z;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.hdmi.HdmiCecAtomWriter getAtomWriter() {
        return this.mAtomWriter;
    }

    boolean isMhlInputChangeEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mMhlInputChangeEnabled;
        }
        return z;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void displayOsd(int i) {
        assertRunOnServiceThread();
        android.content.Intent intent = new android.content.Intent("android.hardware.hdmi.action.OSD_MESSAGE");
        intent.putExtra("android.hardware.hdmi.extra.MESSAGE_ID", i);
        getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL, PERMISSION);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void displayOsd(int i, int i2) {
        assertRunOnServiceThread();
        android.content.Intent intent = new android.content.Intent("android.hardware.hdmi.action.OSD_MESSAGE");
        intent.putExtra("android.hardware.hdmi.extra.MESSAGE_ID", i);
        intent.putExtra("android.hardware.hdmi.extra.MESSAGE_EXTRA_PARAM1", i2);
        getContext().sendBroadcastAsUser(intent, android.os.UserHandle.ALL, PERMISSION);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.hdmi.HdmiCecConfig getHdmiCecConfig() {
        return this.mHdmiCecConfig;
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$27, reason: invalid class name */
    class AnonymousClass27 implements com.android.server.hdmi.HdmiCecConfig.SettingChangeListener {
        AnonymousClass27() {
        }

        @Override // com.android.server.hdmi.HdmiCecConfig.SettingChangeListener
        public void onChange(final java.lang.String str) {
            synchronized (com.android.server.hdmi.HdmiControlService.this.mLock) {
                try {
                    if (com.android.server.hdmi.HdmiControlService.this.mHdmiCecSettingChangeListenerRecords.containsKey(str)) {
                        ((android.os.RemoteCallbackList) com.android.server.hdmi.HdmiControlService.this.mHdmiCecSettingChangeListenerRecords.get(str)).broadcast(new java.util.function.Consumer() { // from class: com.android.server.hdmi.HdmiControlService$27$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.hdmi.HdmiControlService.AnonymousClass27.this.lambda$onChange$0(str, (android.hardware.hdmi.IHdmiCecSettingChangeListener) obj);
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChange$0(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
            com.android.server.hdmi.HdmiControlService.this.invokeCecSettingChangeListenerLocked(str, iHdmiCecSettingChangeListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
        synchronized (this.mLock) {
            try {
                if (!this.mHdmiCecSettingChangeListenerRecords.containsKey(str)) {
                    this.mHdmiCecSettingChangeListenerRecords.put(str, new android.os.RemoteCallbackList<>());
                    this.mHdmiCecConfig.registerChangeListener(str, this.mSettingChangeListener);
                }
                this.mHdmiCecSettingChangeListenerRecords.get(str).register(iHdmiCecSettingChangeListener);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
        synchronized (this.mLock) {
            try {
                if (this.mHdmiCecSettingChangeListenerRecords.containsKey(str)) {
                    this.mHdmiCecSettingChangeListenerRecords.get(str).unregister(iHdmiCecSettingChangeListener);
                    if (this.mHdmiCecSettingChangeListenerRecords.get(str).getRegisteredCallbackCount() == 0) {
                        this.mHdmiCecSettingChangeListenerRecords.remove(str);
                        this.mHdmiCecConfig.removeChangeListener(str, this.mSettingChangeListener);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeCecSettingChangeListenerLocked(java.lang.String str, android.hardware.hdmi.IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
        try {
            iHdmiCecSettingChangeListener.onChange(str);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to report setting change", e);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    void onDeviceVolumeBehaviorChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) {
        assertRunOnServiceThread();
        if (AVB_AUDIO_OUTPUT_DEVICES.contains(audioDeviceAttributes)) {
            synchronized (this.mLock) {
                this.mAudioDeviceVolumeBehaviors.put(audioDeviceAttributes, java.lang.Integer.valueOf(i));
            }
            checkAndUpdateAbsoluteVolumeBehavior();
        }
    }

    private int getDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (AVB_AUDIO_OUTPUT_DEVICES.contains(audioDeviceAttributes)) {
            synchronized (this.mLock) {
                try {
                    if (this.mAudioDeviceVolumeBehaviors.containsKey(audioDeviceAttributes)) {
                        return this.mAudioDeviceVolumeBehaviors.get(audioDeviceAttributes).intValue();
                    }
                } finally {
                }
            }
        }
        return getAudioManager().getDeviceVolumeBehavior(audioDeviceAttributes);
    }

    public boolean isAbsoluteVolumeBehaviorEnabled() {
        if (!isTvDevice() && !isPlaybackDevice()) {
            return false;
        }
        java.util.Iterator<android.media.AudioDeviceAttributes> it = getAvbCapableAudioOutputDevices().iterator();
        while (it.hasNext()) {
            if (ABSOLUTE_VOLUME_BEHAVIORS.contains(java.lang.Integer.valueOf(getDeviceVolumeBehavior(it.next())))) {
                return true;
            }
        }
        return false;
    }

    private java.util.List<android.media.AudioDeviceAttributes> getAvbCapableAudioOutputDevices() {
        if (tv() != null) {
            return TV_AVB_AUDIO_OUTPUT_DEVICES;
        }
        if (playback() != null) {
            return PLAYBACK_AVB_AUDIO_OUTPUT_DEVICES;
        }
        return java.util.Collections.emptyList();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void checkAndUpdateAbsoluteVolumeBehavior() {
        com.android.server.hdmi.HdmiCecLocalDevice playback;
        assertRunOnServiceThread();
        if (getAudioManager() == null) {
        }
        if (this.mPowerStatusController != null && isPowerStandbyOrTransient()) {
            switchToFullVolumeBehavior();
            return;
        }
        if (isTvDevice() && tv() != null) {
            playback = tv();
            if (!isSystemAudioActivated()) {
                switchToFullVolumeBehavior();
                return;
            }
        } else if (isPlaybackDevice() && playback() != null) {
            playback = playback();
        } else {
            return;
        }
        android.hardware.hdmi.HdmiDeviceInfo deviceInfo = getDeviceInfo(playback.findAudioReceiverAddress());
        int deviceVolumeBehavior = getDeviceVolumeBehavior(getAvbCapableAudioOutputDevices().get(0));
        boolean contains = FULL_AND_ABSOLUTE_VOLUME_BEHAVIORS.contains(java.lang.Integer.valueOf(deviceVolumeBehavior));
        if (!(getHdmiCecVolumeControl() == 1) || !contains) {
            switchToFullVolumeBehavior();
            return;
        }
        if (deviceInfo == null) {
            switchToFullVolumeBehavior();
            return;
        }
        switch (deviceInfo.getDeviceFeatures().getSetAudioVolumeLevelSupport()) {
            case 0:
                if (tv() != null && this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled) {
                    if (deviceVolumeBehavior != 5) {
                        if (deviceVolumeBehavior == 3) {
                            java.util.Iterator<android.media.AudioDeviceAttributes> it = getAvbCapableAudioOutputDevices().iterator();
                            while (it.hasNext()) {
                                getAudioManager().setDeviceVolumeBehavior(it.next(), 1);
                            }
                        }
                        playback.startNewAvbAudioStatusAction(deviceInfo.getLogicalAddress());
                        break;
                    }
                } else {
                    switchToFullVolumeBehavior();
                    break;
                }
                break;
            case 1:
                if (deviceVolumeBehavior != 3) {
                    playback.startNewAvbAudioStatusAction(deviceInfo.getLogicalAddress());
                    break;
                }
                break;
            case 2:
                if (deviceVolumeBehavior == 3) {
                    switchToFullVolumeBehavior();
                }
                playback.querySetAudioVolumeLevelSupport(deviceInfo.getLogicalAddress());
                break;
        }
    }

    private void switchToFullVolumeBehavior() {
        android.util.Slog.d(TAG, "Switching to full volume behavior");
        if (playback() != null) {
            playback().removeAvbAudioStatusAction();
        } else if (tv() != null) {
            tv().removeAvbAudioStatusAction();
        }
        for (android.media.AudioDeviceAttributes audioDeviceAttributes : getAvbCapableAudioOutputDevices()) {
            if (ABSOLUTE_VOLUME_BEHAVIORS.contains(java.lang.Integer.valueOf(getDeviceVolumeBehavior(audioDeviceAttributes)))) {
                getAudioManager().setDeviceVolumeBehavior(audioDeviceAttributes, 1);
            }
        }
    }

    void enableAbsoluteVolumeBehavior(com.android.server.hdmi.AudioStatus audioStatus) {
        com.android.server.hdmi.HdmiCecLocalDevice playback = isPlaybackDevice() ? playback() : tv();
        android.hardware.hdmi.HdmiDeviceInfo deviceInfo = getDeviceInfo(playback.findAudioReceiverAddress());
        android.media.VolumeInfo build = new android.media.VolumeInfo.Builder(3).setMuted(audioStatus.getMute()).setVolumeIndex(audioStatus.getVolume()).setMaxVolumeIndex(100).setMinVolumeIndex(0).build();
        this.mAbsoluteVolumeChangedListener = new com.android.server.hdmi.HdmiControlService.AbsoluteVolumeChangedListener(playback, deviceInfo);
        notifyAvbMuteChange(audioStatus.getMute());
        if (deviceInfo.getDeviceFeatures().getSetAudioVolumeLevelSupport() == 1) {
            android.util.Slog.d(TAG, "Enabling absolute volume behavior");
            java.util.Iterator<android.media.AudioDeviceAttributes> it = getAvbCapableAudioOutputDevices().iterator();
            while (it.hasNext()) {
                getAudioDeviceVolumeManager().setDeviceAbsoluteVolumeBehavior(it.next(), build, this.mServiceThreadExecutor, this.mAbsoluteVolumeChangedListener, true);
            }
            return;
        }
        if (tv() != null) {
            android.util.Slog.d(TAG, "Enabling adjust-only absolute volume behavior");
            java.util.Iterator<android.media.AudioDeviceAttributes> it2 = getAvbCapableAudioOutputDevices().iterator();
            while (it2.hasNext()) {
                getAudioDeviceVolumeManager().setDeviceAbsoluteVolumeAdjustOnlyBehavior(it2.next(), build, this.mServiceThreadExecutor, this.mAbsoluteVolumeChangedListener, true);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.hdmi.HdmiControlService.AbsoluteVolumeChangedListener getAbsoluteVolumeChangedListener() {
        return this.mAbsoluteVolumeChangedListener;
    }

    @com.android.internal.annotations.VisibleForTesting
    class AbsoluteVolumeChangedListener implements android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener {
        private com.android.server.hdmi.HdmiCecLocalDevice mLocalDevice;
        private android.hardware.hdmi.HdmiDeviceInfo mSystemAudioDevice;

        private AbsoluteVolumeChangedListener(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
            this.mLocalDevice = hdmiCecLocalDevice;
            this.mSystemAudioDevice = hdmiDeviceInfo;
        }

        public void onAudioDeviceVolumeChanged(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, @android.annotation.NonNull final android.media.VolumeInfo volumeInfo) {
            if (this.mSystemAudioDevice.getDeviceFeatures().getSetAudioVolumeLevelSupport() != 1) {
                return;
            }
            final int logicalAddress = this.mLocalDevice.getDeviceInfo().getLogicalAddress();
            com.android.server.hdmi.HdmiControlService.this.sendCecCommand(com.android.server.hdmi.SetAudioVolumeLevelMessage.build(logicalAddress, this.mSystemAudioDevice.getLogicalAddress(), volumeInfo.getVolumeIndex()), new com.android.server.hdmi.HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.HdmiControlService$AbsoluteVolumeChangedListener$$ExternalSyntheticLambda0
                @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                public final void onSendCompleted(int i) {
                    com.android.server.hdmi.HdmiControlService.AbsoluteVolumeChangedListener.this.lambda$onAudioDeviceVolumeChanged$0(volumeInfo, logicalAddress, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAudioDeviceVolumeChanged$0(android.media.VolumeInfo volumeInfo, int i, int i2) {
            if (i2 == 0) {
                (com.android.server.hdmi.HdmiControlService.this.isTvDevice() ? com.android.server.hdmi.HdmiControlService.this.tv() : com.android.server.hdmi.HdmiControlService.this.playback()).updateAvbVolume(volumeInfo.getVolumeIndex());
            } else {
                com.android.server.hdmi.HdmiControlService.this.sendCecCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveAudioStatus(i, this.mSystemAudioDevice.getLogicalAddress()));
            }
        }

        public void onAudioDeviceVolumeAdjusted(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, @android.annotation.NonNull android.media.VolumeInfo volumeInfo, int i, int i2) {
            int i3;
            switch (i) {
                case -100:
                case 100:
                case 101:
                    i3 = 164;
                    break;
                case -1:
                    i3 = 25;
                    break;
                case 0:
                    if (com.android.server.hdmi.HdmiControlService.this.tv() != null) {
                        com.android.server.hdmi.HdmiControlService.this.tv().requestAndUpdateAvbAudioStatus();
                        return;
                    }
                    return;
                case 1:
                    i3 = 24;
                    break;
                default:
            }
            switch (i2) {
                case 0:
                    this.mLocalDevice.sendVolumeKeyEvent(i3, true);
                    this.mLocalDevice.sendVolumeKeyEvent(i3, false);
                    break;
                case 1:
                    this.mLocalDevice.sendVolumeKeyEvent(i3, true);
                    break;
                case 2:
                    this.mLocalDevice.sendVolumeKeyEvent(i3, false);
                    break;
            }
        }
    }

    void notifyAvbVolumeChange(int i) {
        int i2;
        if (isAbsoluteVolumeBehaviorEnabled()) {
            java.util.Iterator<android.media.AudioDeviceAttributes> it = getAudioManager().getDevicesForAttributes(STREAM_MUSIC_ATTRIBUTES).iterator();
            while (it.hasNext()) {
                if (getAvbCapableAudioOutputDevices().contains(it.next())) {
                    if (!isTvDevice()) {
                        i2 = 8192;
                    } else {
                        i2 = com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937AC3;
                    }
                    setStreamMusicVolume(i, i2);
                    return;
                }
            }
        }
    }

    void notifyAvbMuteChange(boolean z) {
        int i;
        if (isAbsoluteVolumeBehaviorEnabled()) {
            java.util.Iterator<android.media.AudioDeviceAttributes> it = getAudioManager().getDevicesForAttributes(STREAM_MUSIC_ATTRIBUTES).iterator();
            while (it.hasNext()) {
                if (getAvbCapableAudioOutputDevices().contains(it.next())) {
                    int i2 = z ? -100 : 100;
                    if (!isTvDevice()) {
                        i = 8192;
                    } else {
                        i = com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937AC3;
                    }
                    getAudioManager().adjustStreamVolume(3, i2, i);
                    return;
                }
            }
        }
    }

    void setStreamMusicVolume(int i, int i2) {
        getAudioManager().setStreamVolume(3, (i * this.mStreamMusicMaxVolume) / 100, i2);
    }

    private void initializeEarc(int i) {
        android.util.Slog.i(TAG, "eARC initialized, reason = " + i);
        initializeEarcLocalDevice(i);
        if (i == 6) {
            setEarcEnabledInHal(true, true);
        } else {
            setEarcEnabledInHal(true, false);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void initializeEarcLocalDevice(int i) {
        assertRunOnServiceThread();
        if (this.mEarcLocalDevice == null) {
            this.mEarcLocalDevice = com.android.server.hdmi.HdmiEarcLocalDevice.create(this, 0);
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void setEarcEnabled(int i) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            try {
                this.mEarcEnabled = i == 1;
                if (!isEarcSupported()) {
                    android.util.Slog.i(TAG, "Enabled/disabled eARC setting, but the hardware doesn´t support eARC. This settings change doesn´t have an effect.");
                } else if (this.mEarcEnabled) {
                    onEnableEarc();
                } else {
                    runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.HdmiControlService.28
                        @Override // java.lang.Runnable
                        public void run() {
                            com.android.server.hdmi.HdmiControlService.this.onDisableEarc();
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setEarcSupported(boolean z) {
        synchronized (this.mLock) {
            this.mEarcSupported = z;
        }
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private void onEnableEarc() {
        initializeEarc(6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    public void onDisableEarc() {
        disableEarcLocalDevice();
        setEarcEnabledInHal(false, false);
        clearEarcLocalDevice();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void clearEarcLocalDevice() {
        assertRunOnServiceThread();
        this.mEarcLocalDevice = null;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void addEarcLocalDevice(com.android.server.hdmi.HdmiEarcLocalDevice hdmiEarcLocalDevice) {
        assertRunOnServiceThread();
        this.mEarcLocalDevice = hdmiEarcLocalDevice;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    private int getEarcStatus() {
        int i;
        assertRunOnServiceThread();
        if (this.mEarcLocalDevice != null) {
            synchronized (this.mLock) {
                i = this.mEarcLocalDevice.mEarcStatus;
            }
            return i;
        }
        return -1;
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.hdmi.HdmiEarcLocalDevice getEarcLocalDevice() {
        assertRunOnServiceThread();
        return this.mEarcLocalDevice;
    }

    private void disableEarcLocalDevice() {
        if (this.mEarcLocalDevice == null) {
            return;
        }
        this.mEarcLocalDevice.disableDevice();
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    @com.android.internal.annotations.VisibleForTesting
    protected void setEarcEnabledInHal(final boolean z, boolean z2) {
        assertRunOnServiceThread();
        if (z2) {
            startArcAction(false, new android.hardware.hdmi.IHdmiControlCallback.Stub() { // from class: com.android.server.hdmi.HdmiControlService.29
                public void onComplete(int i) throws android.os.RemoteException {
                    if (i != 0) {
                        android.util.Slog.w(com.android.server.hdmi.HdmiControlService.TAG, "ARC termination before enabling eARC in the HAL failed with result: " + i);
                    }
                    com.android.server.hdmi.HdmiControlService.this.mEarcController.setEarcEnabled(z);
                    com.android.server.hdmi.HdmiControlService.this.mCecController.setHpdSignalType(z ? 1 : 0, com.android.server.hdmi.HdmiControlService.this.mEarcPortId);
                }
            });
            return;
        }
        this.mEarcController.setEarcEnabled(z);
        this.mCecController.setHpdSignalType(z ? 1 : 0, this.mEarcPortId);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void handleEarcStateChange(int i, int i2) {
        assertRunOnServiceThread();
        int earcStatus = getEarcStatus();
        if (!getPortInfo(i2).isEarcSupported()) {
            android.util.Slog.w(TAG, "Tried to update eARC status on a port that doesn't support eARC.");
            getAtomWriter().earcStatusChanged(isEarcSupported(), isEarcEnabled(), earcStatus, i, 3);
            return;
        }
        if (this.mEarcLocalDevice != null) {
            this.mEarcLocalDevice.handleEarcStateChange(i);
            getAtomWriter().earcStatusChanged(isEarcSupported(), isEarcEnabled(), earcStatus, i, 2);
        } else {
            if (i == 2) {
                com.android.server.hdmi.HdmiLogger.debug("eARC state change [new: HDMI_EARC_STATUS_ARC_PENDING(2)]", new java.lang.Object[0]);
                notifyEarcStatusToAudioService(false, new java.util.ArrayList());
                startArcAction(true, null);
                getAtomWriter().earcStatusChanged(isEarcSupported(), isEarcEnabled(), earcStatus, i, 2);
                return;
            }
            getAtomWriter().earcStatusChanged(isEarcSupported(), isEarcEnabled(), earcStatus, i, 4);
        }
    }

    protected void notifyEarcStatusToAudioService(boolean z, java.util.List<android.media.AudioDescriptor> list) {
        android.media.AudioDeviceAttributes audioDeviceAttributes = new android.media.AudioDeviceAttributes(2, 29, "", "", new java.util.ArrayList(), list);
        if (!isCecControlEnabled()) {
            setSystemAudioActivated(true);
        }
        getAudioManager().setWiredDeviceConnectionState(audioDeviceAttributes, z ? 1 : 0);
    }

    @com.android.server.hdmi.HdmiAnnotations.ServiceThreadOnly
    void handleEarcCapabilitiesReported(byte[] bArr, int i) {
        assertRunOnServiceThread();
        if (!getPortInfo(i).isEarcSupported()) {
            android.util.Slog.w(TAG, "Tried to process eARC capabilities from a port that doesn't support eARC.");
        } else if (this.mEarcLocalDevice != null) {
            this.mEarcLocalDevice.handleEarcCapabilitiesReported(bArr);
        }
    }

    protected boolean earcBlocksArcConnection() {
        boolean z;
        if (this.mEarcLocalDevice == null) {
            return false;
        }
        synchronized (this.mLock) {
            z = this.mEarcLocalDevice.mEarcStatus != 2;
        }
        return z;
    }

    protected void startArcAction(boolean z, android.hardware.hdmi.IHdmiControlCallback iHdmiControlCallback) {
        if (!isTvDeviceEnabled()) {
            invokeCallback(iHdmiControlCallback, 6);
        } else {
            tv().startArcAction(z, iHdmiControlCallback);
        }
    }
}
