package com.android.server.audio;

/* loaded from: classes.dex */
public class AudioService extends android.media.IAudioService.Stub implements android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener, android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener, com.android.server.audio.AudioSystemAdapter.OnRoutingUpdatedListener, com.android.server.audio.AudioSystemAdapter.OnVolRangeInitRequestListener {
    private static final java.lang.String AUDIO_HAL_SERVICE_PREFIX = "android.hardware.audio";

    @com.android.internal.annotations.VisibleForTesting
    public static final int BECOMING_NOISY_DELAY_MS = 1000;
    private static final int CHECK_MODE_FOR_UID_PERIOD_MS = 6000;
    static final int CONNECTION_STATE_CONNECTED = 1;
    static final int CONNECTION_STATE_DISCONNECTED = 0;
    protected static final boolean DEBUG_AP = false;
    protected static final boolean DEBUG_COMM_RTE = false;
    protected static final boolean DEBUG_DEVICES = false;
    protected static final boolean DEBUG_LOG_SOUND_FX = false;
    protected static final boolean DEBUG_MODE = false;
    protected static final boolean DEBUG_VOL = false;
    private static final int DEFAULT_STREAM_TYPE_OVERRIDE_DELAY_MS = 0;
    protected static final int DEFAULT_VOL_STREAM_NO_PLAYBACK = 3;
    private static final int FLAG_ADJUST_VOLUME = 1;
    private static final int INDICATE_SYSTEM_READY_RETRY_DELAY_MS = 1000;
    private static final java.lang.String KEY_AUDIO_ATTRIBUTES = "audio_attributes";
    private static final java.lang.String KEY_AUDIO_MIXER_ATTRIBUTES = "audio_mixer_attributes";
    static final int LOG_NB_EVENTS_DEVICE_CONNECTION = 50;
    static final int LOG_NB_EVENTS_DYN_POLICY = 10;
    static final int LOG_NB_EVENTS_FORCE_USE = 20;
    static final int LOG_NB_EVENTS_LIFECYCLE = 20;
    static final int LOG_NB_EVENTS_LOUDNESS_CODEC = 30;
    static final int LOG_NB_EVENTS_PHONE_STATE = 20;
    static final int LOG_NB_EVENTS_SOUND_DOSE = 30;
    static final int LOG_NB_EVENTS_SPATIAL = 30;
    static final int LOG_NB_EVENTS_VOLUME = 100;
    protected static final float MIN_ALARM_ATTENUATION_NON_PRIVILEGED_DB = -36.0f;
    private static final int MSG_ACCESSORY_PLUG_MEDIA_UNMUTE = 21;
    private static final int MSG_ADD_ASSISTANT_SERVICE_UID = 44;
    private static final int MSG_AUDIO_SERVER_DIED = 4;
    private static final int MSG_BROADCAST_MASTER_MUTE = 55;
    private static final int MSG_BROADCAST_MICROPHONE_MUTE = 30;
    private static final int MSG_BT_DEV_CHANGED = 38;
    private static final int MSG_CHECK_MODE_FOR_UID = 31;
    private static final int MSG_CONFIGURATION_CHANGED = 54;
    private static final int MSG_DISABLE_AUDIO_FOR_UID = 100;
    private static final int MSG_DISPATCH_AUDIO_MODE = 40;
    private static final int MSG_DISPATCH_AUDIO_SERVER_STATE = 23;
    private static final int MSG_DISPATCH_DEVICE_VOLUME_BEHAVIOR = 47;
    private static final int MSG_DISPATCH_PREFERRED_MIXER_ATTRIBUTES = 52;
    private static final int MSG_DYN_POLICY_MIX_STATE_UPDATE = 19;
    private static final int MSG_ENABLE_SURROUND_FORMATS = 24;
    private static final int MSG_FOLD_UPDATE = 49;
    private static final int MSG_HDMI_VOLUME_CHECK = 28;
    private static final int MSG_INDICATE_SYSTEM_READY = 20;
    private static final int MSG_INIT_ADI_DEVICE_STATES = 103;
    private static final int MSG_INIT_HEADTRACKING_SENSORS = 42;
    private static final int MSG_INIT_SPATIALIZER = 102;
    private static final int MSG_INIT_STREAMS_VOLUMES = 101;
    private static final int MSG_LOAD_SOUND_EFFECTS = 7;
    private static final int MSG_NOTIFY_VOL_EVENT = 22;
    private static final int MSG_NO_LOG_FOR_PLAYER_I = 51;
    private static final int MSG_OBSERVE_DEVICES_FOR_ALL_STREAMS = 27;
    private static final int MSG_PERSIST_RINGER_MODE = 3;
    private static final int MSG_PERSIST_VOLUME = 1;
    private static final int MSG_PERSIST_VOLUME_GROUP = 2;
    private static final int MSG_PLAYBACK_CONFIG_CHANGE = 29;
    private static final int MSG_PLAY_SOUND_EFFECT = 5;
    private static final int MSG_RECORDING_CONFIG_CHANGE = 37;
    private static final int MSG_REINIT_VOLUMES = 34;
    private static final int MSG_REMOVE_ASSISTANT_SERVICE_UID = 45;
    private static final int MSG_RESET_SPATIALIZER = 50;
    private static final int MSG_ROTATION_UPDATE = 48;
    private static final int MSG_ROUTING_UPDATED = 41;
    private static final int MSG_SET_ALL_VOLUMES = 10;
    private static final int MSG_SET_DEVICE_STREAM_VOLUME = 26;
    static final int MSG_SET_DEVICE_VOLUME = 0;
    private static final int MSG_SET_FORCE_USE = 8;
    private static final int MSG_STREAM_DEVICES_CHANGED = 32;
    private static final int MSG_SYSTEM_READY = 16;
    private static final int MSG_UNLOAD_SOUND_EFFECTS = 15;
    private static final int MSG_UNMUTE_STREAM_ON_SINGLE_VOL_DEVICE = 18;
    private static final int MSG_UPDATE_A11Y_SERVICE_UIDS = 35;
    private static final int MSG_UPDATE_ACTIVE_ASSISTANT_SERVICE_UID = 46;
    private static final int MSG_UPDATE_AUDIO_MODE = 36;
    private static final int MSG_UPDATE_RINGER_MODE = 25;
    private static final int MSG_UPDATE_VOLUME_STATES_FOR_DEVICE = 33;
    static final int MUSICFX_HELPER_MSG_START = 1100;
    private static final int PERSIST_DELAY = 500;
    private static final java.lang.String[] RINGER_MODE_NAMES;
    static final int SAFE_MEDIA_VOLUME_MSG_START = 1000;
    private static final int SENDMSG_NOOP = 1;
    private static final int SENDMSG_QUEUE = 2;
    private static final int SENDMSG_REPLACE = 0;
    private static final boolean SPATIAL_AUDIO_ENABLED_DEFAULT = true;
    private static final java.lang.String TAG = "AS.AudioService";
    private static final int TOUCH_EXPLORE_STREAM_TYPE_OVERRIDE_DELAY_MS = 1000;
    private static final int UNMUTE_STREAM_DELAY = 350;
    private static final int UNSET_INDEX = -1;
    private static final java.lang.String mMetricsId = "audio.service.";
    protected static int[] mStreamVolumeAlias;
    static android.media.VolumeInfo sDefaultVolumeInfo;
    static final com.android.server.utils.EventLogger sDeviceLogger;
    static final com.android.server.utils.EventLogger sForceUseLogger;
    private static boolean sIndependentA11yVolume;
    static final com.android.server.utils.EventLogger sLifecycleLogger;
    static final com.android.server.utils.EventLogger sMuteLogger;
    protected static volatile int sRingerAndZenModeMutedStreams;
    static final com.android.server.utils.EventLogger sSpatialLogger;
    private static int sStreamOverrideDelayMs;
    private static final android.util.SparseArray<com.android.server.audio.AudioService.VolumeGroupState> sVolumeGroupStates;
    static final com.android.server.utils.EventLogger sVolumeLogger;
    private final int[] STREAM_VOLUME_ALIAS_DEFAULT;
    private final int[] STREAM_VOLUME_ALIAS_NONE;
    private final int[] STREAM_VOLUME_ALIAS_TELEVISION;
    private final int[] STREAM_VOLUME_ALIAS_VOICE;
    java.util.Set<java.lang.Integer> mAbsVolumeMultiModeCaseDevices;
    java.util.Map<java.lang.Integer, com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo> mAbsoluteVolumeDeviceInfoMap;

    @com.android.internal.annotations.GuardedBy({"mAccessibilityServiceUidsLock"})
    private int[] mAccessibilityServiceUids;
    private final java.lang.Object mAccessibilityServiceUidsLock;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private int[] mActiveAssistantServiceUids;
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final android.app.AppOpsManager mAppOps;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private final android.util.ArraySet<java.lang.Integer> mAssistantUids;
    private android.os.PowerManager.WakeLock mAudioEventWakeLock;
    private com.android.server.audio.AudioService.AudioHandler mAudioHandler;
    private final java.util.HashMap<android.os.IBinder, com.android.server.audio.AudioService.AudioPolicyProxy> mAudioPolicies;
    private final com.android.server.audio.AudioPolicyFacade mAudioPolicy;

    @com.android.internal.annotations.GuardedBy({"mAudioPolicies"})
    private int mAudioPolicyCounter;
    private final java.util.HashMap<android.os.IBinder, com.android.server.audio.AudioService.AsdProxy> mAudioServerStateListeners;
    private final com.android.server.audio.AudioSystemAdapter mAudioSystem;
    private final android.media.AudioSystem.ErrorCallback mAudioSystemCallback;
    private com.android.server.audio.AudioService.AudioSystemThread mAudioSystemThread;
    private final com.android.server.audio.AudioVolumeGroupHelperBase mAudioVolumeGroupHelper;
    private volatile boolean mAvrcpAbsVolSupported;
    private boolean mBtScoOnByApp;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private boolean mCameraSoundForced;
    private final android.content.ContentResolver mContentResolver;
    final android.content.Context mContext;
    private final com.android.server.audio.AudioDeviceBroker mDeviceBroker;
    final android.os.RemoteCallbackList<android.media.IDeviceVolumeBehaviorDispatcher> mDeviceVolumeBehaviorDispatchers;
    private android.hardware.display.DisplayManager.DisplayListener mDisplayListener;
    private android.hardware.display.DisplayManager mDisplayManager;
    private boolean mDockAudioMediaEnabled;
    private int mDockState;
    private final android.media.AudioSystem.DynamicPolicyCallback mDynPolicyCallback;
    private final com.android.server.utils.EventLogger mDynPolicyLogger;
    private java.lang.String mEnabledSurroundFormats;
    private int mEncodedSurroundMode;
    private android.media.audiopolicy.IAudioPolicyCallback mExtVolumeController;
    private final java.lang.Object mExtVolumeControllerLock;
    java.util.Set<java.lang.Integer> mFixedVolumeDevices;
    private com.android.server.audio.AudioService.ForceControlStreamClient mForceControlStreamClient;
    private final java.lang.Object mForceControlStreamLock;
    java.util.Set<java.lang.Integer> mFullVolumeDevices;
    private final com.android.server.audio.HardeningEnforcer mHardeningEnforcer;
    private final boolean mHasSpatializerEffect;
    private final boolean mHasVibrator;

    @com.android.internal.annotations.GuardedBy({"mHdmiClientLock"})
    private android.hardware.hdmi.HdmiAudioSystemClient mHdmiAudioSystemClient;

    @com.android.internal.annotations.GuardedBy({"mHdmiClientLock"})
    private boolean mHdmiCecVolumeControlEnabled;
    private final java.lang.Object mHdmiClientLock;
    private com.android.server.audio.AudioService.MyHdmiControlStatusChangeListenerCallback mHdmiControlStatusChangeListenerCallback;

    @com.android.internal.annotations.GuardedBy({"mHdmiClientLock"})
    private android.hardware.hdmi.HdmiControlManager mHdmiManager;

    @com.android.internal.annotations.GuardedBy({"mHdmiClientLock"})
    private android.hardware.hdmi.HdmiPlaybackClient mHdmiPlaybackClient;
    private boolean mHdmiSystemAudioSupported;

    @com.android.internal.annotations.GuardedBy({"mHdmiClientLock"})
    private android.hardware.hdmi.HdmiTvClient mHdmiTvClient;
    private boolean mHomeSoundEffectEnabled;
    private int mInputMethodServiceUid;
    private final java.lang.Object mInputMethodServiceUidLock;
    private boolean mIsCallScreeningModeSupported;
    private final boolean mIsSingleVolume;
    private final com.android.server.audio.LoudnessCodecHelper mLoudnessCodecHelper;
    private long mLoweredFromNormalToVibrateTime;
    private java.util.concurrent.atomic.AtomicBoolean mMasterMute;
    private final com.android.server.audio.MediaFocusControl mMediaFocusControl;
    private java.util.concurrent.atomic.AtomicBoolean mMediaPlaybackActive;

    @android.annotation.Nullable
    private volatile android.media.session.MediaSessionManager mMediaSessionManager;
    private boolean mMicMuteFromApi;
    private boolean mMicMuteFromPrivacyToggle;
    private boolean mMicMuteFromRestrictions;
    private boolean mMicMuteFromSwitch;
    private boolean mMicMuteFromSystemCached;
    private java.util.concurrent.atomic.AtomicInteger mMode;
    final android.os.RemoteCallbackList<android.media.IAudioModeDispatcher> mModeDispatchers;
    private final com.android.server.utils.EventLogger mModeLogger;
    private final boolean mMonitorRotation;
    private final com.android.server.audio.MusicFxHelper mMusicFxHelper;
    private int mMuteAffectedStreams;
    final android.os.RemoteCallbackList<android.media.IMuteAwaitConnectionCallback> mMuteAwaitConnectionDispatchers;
    private final java.lang.Object mMuteAwaitConnectionLock;

    @com.android.internal.annotations.GuardedBy({"mMuteAwaitConnectionLock"})
    @android.annotation.Nullable
    private int[] mMutedUsagesAwaitingConnection;

    @com.android.internal.annotations.GuardedBy({"mMuteAwaitConnectionLock"})
    private android.media.AudioDeviceAttributes mMutingExpectedDevice;
    private com.android.server.audio.AudioService.MyHdmiCecVolumeControlFeatureListener mMyHdmiCecVolumeControlFeatureListener;
    private boolean mNavigationRepeatSoundEffectsEnabled;
    private android.app.NotificationManager mNm;
    private boolean mNotifAliasRing;
    private final int mPlatformType;
    private final android.media.IPlaybackConfigDispatcher mPlaybackActivityMonitor;
    private final com.android.server.audio.PlaybackActivityMonitor mPlaybackMonitor;
    final android.os.RemoteCallbackList<android.media.IPreferredMixerAttributesDispatcher> mPrefMixerAttrDispatcher;
    private float[] mPrescaleAbsoluteVolume;
    private int mPrevVolDirection;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private int mPrimaryAssistantUid;
    private android.media.projection.IMediaProjectionManager mProjectionService;
    private final android.content.BroadcastReceiver mReceiver;
    private final com.android.server.audio.RecordingActivityMonitor mRecordMonitor;
    private com.android.server.audio.AudioService.RestorableParameters mRestorableParameters;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private int mRingerMode;
    private int mRingerModeAffectedStreams;
    private final boolean mRingerModeAffectsAlarm;
    private android.media.AudioManagerInternal.RingerModeDelegate mRingerModeDelegate;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private int mRingerModeExternal;
    private volatile android.media.IRingtonePlayer mRingtonePlayer;
    private final java.util.ArrayList<com.android.server.audio.AudioService.RmtSbmxFullVolDeathHandler> mRmtSbmxFullVolDeathHandlers;
    private int mRmtSbmxFullVolRefCount;
    com.android.server.audio.AudioService.RoleObserver mRoleObserver;

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private boolean mRttEnabled;
    private final android.hardware.SensorPrivacyManagerInternal mSensorPrivacyManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mSetModeLock"})
    final java.util.ArrayList<com.android.server.audio.AudioService.SetModeDeathHandler> mSetModeDeathHandlers;
    private final com.android.server.audio.SettingsAdapter mSettings;
    private final java.lang.Object mSettingsLock;
    private com.android.server.audio.AudioService.SettingsObserver mSettingsObserver;

    @android.annotation.NonNull
    private com.android.server.audio.SoundEffectsHelper mSfxHelper;
    private final com.android.server.audio.SoundDoseHelper mSoundDoseHelper;

    @android.annotation.NonNull
    private final com.android.server.audio.SpatializerHelper mSpatializerHelper;
    final android.os.RemoteCallbackList<android.media.IStreamAliasingDispatcher> mStreamAliasingDispatchers;
    private com.android.server.audio.AudioService.VolumeStreamState[] mStreamStates;
    private android.telephony.SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionChangedListener;

    @com.android.internal.annotations.GuardedBy({"mSupportedSystemUsagesLock"})
    private int[] mSupportedSystemUsages;
    private final java.lang.Object mSupportedSystemUsagesLock;
    private boolean mSupportsMicPrivacyToggle;
    private boolean mSurroundModeChanged;
    private boolean mSystemReady;
    private final com.android.server.audio.SystemServerAdapter mSystemServer;
    private final android.app.IUidObserver mUidObserver;
    private final boolean mUseFixedVolume;
    private final boolean mUseVolumeGroupAliases;
    private final com.android.server.pm.UserManagerInternal mUserManagerInternal;
    private final com.android.server.pm.UserManagerInternal.UserRestrictionsListener mUserRestrictionsListener;
    private boolean mUserSelectedVolumeControlStream;
    private boolean mUserSwitchedReceived;
    private int mVibrateSetting;
    private android.os.Vibrator mVibrator;
    private java.util.concurrent.atomic.AtomicBoolean mVoicePlaybackActive;
    private final android.media.IRecordingConfigDispatcher mVoiceRecordingActivityMonitor;
    private int mVolumeControlStream;
    private final com.android.server.audio.AudioService.VolumeController mVolumeController;
    private android.media.VolumePolicy mVolumePolicy;
    private int mZenModeAffectedStreams;
    private static final int[] NO_ACTIVE_ASSISTANT_SERVICE_UIDS = new int[0];
    protected static int[] MAX_STREAM_VOLUME = {5, 7, 7, 15, 7, 7, 15, 7, 15, 15, 15, 15};
    protected static int[] MIN_STREAM_VOLUME = {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
    private static final int[] STREAM_VOLUME_OPS = {34, 36, 35, 36, 37, 38, 39, 36, 36, 36, 64, 36};
    private static final android.os.VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = android.os.VibrationAttributes.createForUsage(18);
    private static final byte[] DEFAULT_ARC_AUDIO_DESCRIPTOR = {9, Byte.MAX_VALUE, 7};
    private static final java.util.Set<java.lang.Integer> DEVICE_MEDIA_UNMUTED_ON_PLUG_SET = new java.util.HashSet();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BtProfile {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BtProfileConnectionState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionState {
    }

    public interface ISafeHearingVolumeController {
        void postDisplayCsdWarning(int i, int i2);

        void postDisplaySafeVolumeWarning(int i);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public void setNotifAliasRingForTest(boolean z) {
        super.setNotifAliasRingForTest_enforcePermission();
        boolean z2 = this.mNotifAliasRing != z;
        this.mNotifAliasRing = z;
        if (z2) {
            updateStreamVolumeAlias(true, "AudioServiceTest");
        }
    }

    boolean isPlatformVoice() {
        return this.mPlatformType == 1;
    }

    boolean isPlatformTelevision() {
        return this.mPlatformType == 2;
    }

    boolean isPlatformAutomotive() {
        return this.mPlatformType == 3;
    }

    static {
        DEVICE_MEDIA_UNMUTED_ON_PLUG_SET.add(4);
        DEVICE_MEDIA_UNMUTED_ON_PLUG_SET.add(8);
        DEVICE_MEDIA_UNMUTED_ON_PLUG_SET.add(131072);
        DEVICE_MEDIA_UNMUTED_ON_PLUG_SET.addAll(android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
        DEVICE_MEDIA_UNMUTED_ON_PLUG_SET.addAll(android.media.AudioSystem.DEVICE_OUT_ALL_BLE_SET);
        DEVICE_MEDIA_UNMUTED_ON_PLUG_SET.addAll(android.media.AudioSystem.DEVICE_OUT_ALL_USB_SET);
        sVolumeGroupStates = new android.util.SparseArray<>();
        sIndependentA11yVolume = false;
        sLifecycleLogger = new com.android.server.utils.EventLogger(20, "audio services lifecycle");
        sMuteLogger = new com.android.server.utils.EventLogger(30, "mute commands");
        sDeviceLogger = new com.android.server.utils.EventLogger(50, "wired/A2DP/hearing aid device connection");
        sForceUseLogger = new com.android.server.utils.EventLogger(20, "force use (logged before setForceUse() is executed)");
        sVolumeLogger = new com.android.server.utils.EventLogger(100, "volume changes (logged when command received by AudioService)");
        sSpatialLogger = new com.android.server.utils.EventLogger(30, "spatial audio");
        RINGER_MODE_NAMES = new java.lang.String[]{"SILENT", "VIBRATE", com.android.server.utils.PriorityDump.PRIORITY_ARG_NORMAL};
    }

    int getVssVolumeForDevice(int i, int i2) {
        return this.mStreamStates[i].getIndex(i2);
    }

    com.android.server.audio.AudioService.VolumeStreamState getVssVolumeForStream(int i) {
        return this.mStreamStates[i];
    }

    int getMaxVssVolumeForStream(int i) {
        return this.mStreamStates[i].getMaxIndex();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AbsoluteVolumeDeviceInfo {
        private final android.media.IAudioDeviceVolumeDispatcher mCallback;
        private final android.media.AudioDeviceAttributes mDevice;
        private int mDeviceVolumeBehavior;
        private final boolean mHandlesVolumeAdjustment;
        private final java.util.List<android.media.VolumeInfo> mVolumeInfos;

        private AbsoluteVolumeDeviceInfo(android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, android.media.IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, boolean z, int i) {
            this.mDevice = audioDeviceAttributes;
            this.mVolumeInfos = list;
            this.mCallback = iAudioDeviceVolumeDispatcher;
            this.mHandlesVolumeAdjustment = z;
            this.mDeviceVolumeBehavior = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.Nullable
        public android.media.VolumeInfo getMatchingVolumeInfoForStream(final int i) {
            for (android.media.VolumeInfo volumeInfo : this.mVolumeInfos) {
                boolean z = false;
                boolean z2 = volumeInfo.hasStreamType() && volumeInfo.getStreamType() == i;
                if (volumeInfo.hasVolumeGroup() && java.util.Arrays.stream(volumeInfo.getVolumeGroup().getLegacyStreamTypes()).anyMatch(new java.util.function.IntPredicate() { // from class: com.android.server.audio.AudioService$AbsoluteVolumeDeviceInfo$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i2) {
                        boolean lambda$getMatchingVolumeInfoForStream$0;
                        lambda$getMatchingVolumeInfoForStream$0 = com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo.lambda$getMatchingVolumeInfoForStream$0(i, i2);
                        return lambda$getMatchingVolumeInfoForStream$0;
                    }
                })) {
                    z = true;
                }
                if (z2 || z) {
                    return volumeInfo;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getMatchingVolumeInfoForStream$0(int i, int i2) {
            return i2 == i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class RestorableParameters {

        @com.android.internal.annotations.GuardedBy({"mMap"})
        private java.util.Map<java.lang.String, java.util.function.BooleanSupplier> mMap;

        private RestorableParameters() {
            this.mMap = new java.util.LinkedHashMap<java.lang.String, java.util.function.BooleanSupplier>() { // from class: com.android.server.audio.AudioService.RestorableParameters.1
                private static final int MAX_ENTRIES = 1000;

                @Override // java.util.LinkedHashMap
                protected boolean removeEldestEntry(java.util.Map.Entry<java.lang.String, java.util.function.BooleanSupplier> entry) {
                    if (size() <= 1000) {
                        return false;
                    }
                    android.util.Log.w(com.android.server.audio.AudioService.TAG, "Parameter map exceeds 1000 removing " + ((java.lang.Object) entry.getKey()));
                    return true;
                }
            };
        }

        public int setParameters(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull final java.lang.String str2) {
            int parameters;
            java.util.Objects.requireNonNull(str, "id must not be null");
            java.util.Objects.requireNonNull(str2, "parameter must not be null");
            synchronized (this.mMap) {
                try {
                    parameters = android.media.AudioSystem.setParameters(str2);
                    if (parameters == 0) {
                        queueRestoreWithRemovalIfTrue(str, new java.util.function.BooleanSupplier() { // from class: com.android.server.audio.AudioService$RestorableParameters$$ExternalSyntheticLambda1
                            @Override // java.util.function.BooleanSupplier
                            public final boolean getAsBoolean() {
                                boolean lambda$setParameters$0;
                                lambda$setParameters$0 = com.android.server.audio.AudioService.RestorableParameters.lambda$setParameters$0(str2);
                                return lambda$setParameters$0;
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return parameters;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$setParameters$0(java.lang.String str) {
            return android.media.AudioSystem.setParameters(str) != 0;
        }

        public void queueRestoreWithRemovalIfTrue(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.util.function.BooleanSupplier booleanSupplier) {
            java.util.Objects.requireNonNull(str, "id must not be null");
            synchronized (this.mMap) {
                try {
                    if (booleanSupplier != null) {
                        this.mMap.put(str, booleanSupplier);
                    } else {
                        this.mMap.remove(str);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void restoreAll() {
            synchronized (this.mMap) {
                this.mMap.values().removeIf(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioService$RestorableParameters$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean asBoolean;
                        asBoolean = ((java.util.function.BooleanSupplier) obj).getAsBoolean();
                        return asBoolean;
                    }
                });
            }
        }
    }

    public static java.lang.String makeAlsaAddressString(int i, int i2) {
        return "card=" + i + ";device=" + i2;
    }

    private static class AudioVolumeGroupHelper extends com.android.server.audio.AudioVolumeGroupHelperBase {
        private AudioVolumeGroupHelper() {
        }

        @Override // com.android.server.audio.AudioVolumeGroupHelperBase
        public java.util.List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() {
            return android.media.audiopolicy.AudioVolumeGroup.getAudioVolumeGroups();
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private com.android.server.audio.AudioService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mService = new com.android.server.audio.AudioService(context, com.android.server.audio.AudioSystemAdapter.getDefaultAdapter(), com.android.server.audio.SystemServerAdapter.getDefaultAdapter(context), com.android.server.audio.SettingsAdapter.getDefaultAdapter(), new com.android.server.audio.AudioService.AudioVolumeGroupHelper(), new com.android.server.audio.DefaultAudioPolicyFacade(), null);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("audio", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mService.systemReady();
            }
        }
    }

    public AudioService(android.content.Context context, com.android.server.audio.AudioSystemAdapter audioSystemAdapter, com.android.server.audio.SystemServerAdapter systemServerAdapter, com.android.server.audio.SettingsAdapter settingsAdapter, com.android.server.audio.AudioVolumeGroupHelperBase audioVolumeGroupHelperBase, com.android.server.audio.AudioPolicyFacade audioPolicyFacade, @android.annotation.Nullable android.os.Looper looper) {
        this(context, audioSystemAdapter, systemServerAdapter, settingsAdapter, audioVolumeGroupHelperBase, audioPolicyFacade, looper, (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class), android.os.PermissionEnforcer.fromContext(context));
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x052d  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x05ce  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x03f1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0413 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @android.annotation.RequiresPermission("android.permission.READ_DEVICE_CONFIG")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AudioService(android.content.Context context, com.android.server.audio.AudioSystemAdapter audioSystemAdapter, com.android.server.audio.SystemServerAdapter systemServerAdapter, com.android.server.audio.SettingsAdapter settingsAdapter, com.android.server.audio.AudioVolumeGroupHelperBase audioVolumeGroupHelperBase, com.android.server.audio.AudioPolicyFacade audioPolicyFacade, @android.annotation.Nullable android.os.Looper looper, android.app.AppOpsManager appOpsManager, @android.annotation.NonNull android.os.PermissionEnforcer permissionEnforcer) {
        super(permissionEnforcer);
        char c;
        char c2;
        int i;
        int i2;
        int i3;
        int i4;
        int integer;
        int integer2;
        this.mNotifAliasRing = false;
        this.mVolumeController = new com.android.server.audio.AudioService.VolumeController();
        this.mMode = new java.util.concurrent.atomic.AtomicInteger(0);
        this.mSettingsLock = new java.lang.Object();
        this.STREAM_VOLUME_ALIAS_VOICE = new int[]{0, 2, 2, 3, 4, 2, 6, 2, 2, 3, 3, 3};
        this.STREAM_VOLUME_ALIAS_TELEVISION = new int[]{3, 3, 3, 3, 3, 3, 6, 3, 3, 3, 3, 3};
        this.STREAM_VOLUME_ALIAS_NONE = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        this.STREAM_VOLUME_ALIAS_DEFAULT = new int[]{0, 2, 2, 3, 4, 2, 6, 2, 2, 3, 3, 3};
        this.mAvrcpAbsVolSupported = false;
        this.mAudioSystemCallback = new android.media.AudioSystem.ErrorCallback() { // from class: com.android.server.audio.AudioService.1
            public void onError(int i5) {
                switch (i5) {
                    case 100:
                        if (com.android.server.audio.AudioService.this.mRecordMonitor != null) {
                            com.android.server.audio.AudioService.this.mRecordMonitor.onAudioServerDied();
                        }
                        if (com.android.server.audio.AudioService.this.mPlaybackMonitor != null) {
                            com.android.server.audio.AudioService.this.mPlaybackMonitor.onAudioServerDied();
                        }
                        com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 4, 1, 0, 0, null, 0);
                        com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 23, 2, 0, 0, null, 0);
                        break;
                }
            }
        };
        this.mRingerModeExternal = -1;
        this.mRingerModeAffectedStreams = 0;
        this.mZenModeAffectedStreams = 0;
        this.mReceiver = new com.android.server.audio.AudioService.AudioServiceBroadcastReceiver();
        this.mUserRestrictionsListener = new com.android.server.audio.AudioService.AudioServiceUserRestrictionsListener();
        this.mSetModeDeathHandlers = new java.util.ArrayList<>();
        this.mPrevVolDirection = 0;
        this.mVolumeControlStream = -1;
        this.mUserSelectedVolumeControlStream = false;
        this.mForceControlStreamLock = new java.lang.Object();
        this.mForceControlStreamClient = null;
        this.mFixedVolumeDevices = new java.util.HashSet(java.util.Arrays.asList(2097152));
        this.mFullVolumeDevices = new java.util.HashSet(java.util.Arrays.asList(262144, 262145));
        this.mAbsoluteVolumeDeviceInfoMap = new android.util.ArrayMap();
        this.mAbsVolumeMultiModeCaseDevices = new java.util.HashSet(java.util.Arrays.asList(134217728));
        this.mDockAudioMediaEnabled = true;
        this.mRestorableParameters = new com.android.server.audio.AudioService.RestorableParameters();
        this.mDockState = 0;
        this.mPrescaleAbsoluteVolume = new float[]{0.6f, 0.8f, 0.9f};
        this.mVolumePolicy = android.media.VolumePolicy.DEFAULT;
        this.mAssistantUids = new android.util.ArraySet<>();
        this.mPrimaryAssistantUid = -1;
        this.mActiveAssistantServiceUids = NO_ACTIVE_ASSISTANT_SERVICE_UIDS;
        this.mAccessibilityServiceUidsLock = new java.lang.Object();
        this.mInputMethodServiceUid = -1;
        this.mInputMethodServiceUidLock = new java.lang.Object();
        this.mSupportedSystemUsagesLock = new java.lang.Object();
        this.mSupportedSystemUsages = new int[]{17};
        this.mUidObserver = new android.app.UidObserver() { // from class: com.android.server.audio.AudioService.2
            public void onUidGone(int i5, boolean z) {
                disableAudioForUid(false, i5);
            }

            public void onUidCachedChanged(int i5, boolean z) {
                disableAudioForUid(z, i5);
            }

            private void disableAudioForUid(boolean z, int i5) {
                com.android.server.audio.AudioService.this.queueMsgUnderWakeLock(com.android.server.audio.AudioService.this.mAudioHandler, 100, z ? 1 : 0, i5, null, 0);
            }
        };
        this.mRttEnabled = false;
        this.mMasterMute = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mDisplayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: com.android.server.audio.AudioService.3
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i5) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i5) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i5) {
                if (i5 != 0) {
                    return;
                }
                if (com.android.server.audio.AudioService.this.mDisplayManager.getDisplay(0).getState() == 2) {
                    if (com.android.server.audio.AudioService.this.mMonitorRotation) {
                        com.android.server.audio.RotationHelper.enable();
                    }
                    android.media.AudioSystem.setParameters("screen_state=on");
                } else {
                    if (com.android.server.audio.AudioService.this.mMonitorRotation) {
                        com.android.server.audio.RotationHelper.disable();
                    }
                    android.media.AudioSystem.setParameters("screen_state=off");
                }
            }
        };
        this.mSubscriptionChangedListener = new android.telephony.SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.audio.AudioService.4
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public void onSubscriptionsChanged() {
                android.util.Log.i(com.android.server.audio.AudioService.TAG, "onSubscriptionsChanged()");
                com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 54, 0, 0, 0, null, 0);
            }
        };
        this.mVoicePlaybackActive = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mMediaPlaybackActive = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mPlaybackActivityMonitor = new android.media.IPlaybackConfigDispatcher.Stub() { // from class: com.android.server.audio.AudioService.5
            public void dispatchPlaybackConfigChange(java.util.List<android.media.AudioPlaybackConfiguration> list, boolean z) {
                com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 29, 0, 0, 0, list, 0);
            }
        };
        this.mVoiceRecordingActivityMonitor = new android.media.IRecordingConfigDispatcher.Stub() { // from class: com.android.server.audio.AudioService.6
            public void dispatchRecordingConfigChange(java.util.List<android.media.AudioRecordingConfiguration> list) {
                com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 37, 0, 0, 0, list, 0);
            }
        };
        this.mRmtSbmxFullVolRefCount = 0;
        this.mRmtSbmxFullVolDeathHandlers = new java.util.ArrayList<>();
        this.mStreamAliasingDispatchers = new android.os.RemoteCallbackList<>();
        this.mIsCallScreeningModeSupported = false;
        this.mModeDispatchers = new android.os.RemoteCallbackList<>();
        this.mMuteAwaitConnectionLock = new java.lang.Object();
        this.mMuteAwaitConnectionDispatchers = new android.os.RemoteCallbackList<>();
        this.mDeviceVolumeBehaviorDispatchers = new android.os.RemoteCallbackList<>();
        this.mHdmiClientLock = new java.lang.Object();
        this.mHdmiSystemAudioSupported = false;
        this.mHdmiControlStatusChangeListenerCallback = new com.android.server.audio.AudioService.MyHdmiControlStatusChangeListenerCallback();
        this.mMyHdmiCecVolumeControlFeatureListener = new com.android.server.audio.AudioService.MyHdmiCecVolumeControlFeatureListener();
        this.mModeLogger = new com.android.server.utils.EventLogger(20, "phone state (logged after successful call to AudioSystem.setPhoneState(int, int))");
        this.mDynPolicyLogger = new com.android.server.utils.EventLogger(10, "dynamic policy events (logged when command received by AudioService)");
        this.mPrefMixerAttrDispatcher = new android.os.RemoteCallbackList<>();
        this.mExtVolumeControllerLock = new java.lang.Object();
        this.mDynPolicyCallback = new android.media.AudioSystem.DynamicPolicyCallback() { // from class: com.android.server.audio.AudioService.8
            public void onDynamicPolicyMixStateUpdate(java.lang.String str, int i5) {
                if (!android.text.TextUtils.isEmpty(str)) {
                    com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 19, 2, i5, 0, str, 0);
                }
            }
        };
        this.mAudioServerStateListeners = new java.util.HashMap<>();
        this.mAudioPolicies = new java.util.HashMap<>();
        this.mAudioPolicyCounter = 0;
        sLifecycleLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("AudioService()"));
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mAppOps = appOpsManager;
        this.mAudioSystem = audioSystemAdapter;
        this.mSystemServer = systemServerAdapter;
        this.mAudioVolumeGroupHelper = audioVolumeGroupHelperBase;
        this.mSettings = settingsAdapter;
        this.mAudioPolicy = audioPolicyFacade;
        this.mPlatformType = android.media.AudioSystem.getPlatformType(context);
        this.mDeviceBroker = new com.android.server.audio.AudioDeviceBroker(this.mContext, this, this.mAudioSystem);
        this.mIsSingleVolume = android.media.AudioSystem.isSingleVolume(context);
        this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mSensorPrivacyManagerInternal = (android.hardware.SensorPrivacyManagerInternal) com.android.server.LocalServices.getService(android.hardware.SensorPrivacyManagerInternal.class);
        this.mAudioEventWakeLock = ((android.os.PowerManager) context.getSystemService("power")).newWakeLock(1, "handleAudioEvent");
        this.mSfxHelper = new com.android.server.audio.SoundEffectsHelper(this.mContext, new java.util.function.Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.audio.AudioService.this.lambda$new$0((android.media.PlayerBase) obj);
            }
        });
        this.mSpatializerHelper = new com.android.server.audio.SpatializerHelper(this, this.mAudioSystem, this.mDeviceBroker, android.os.SystemProperties.getBoolean("ro.audio.spatializer_binaural_enabled_default", true), android.os.SystemProperties.getBoolean("ro.audio.spatializer_transaural_enabled_default", true), this.mContext.getResources().getBoolean(android.R.bool.config_sms_decode_gsm_8bit_data));
        this.mVibrator = (android.os.Vibrator) context.getSystemService("vibrator");
        this.mHasVibrator = this.mVibrator == null ? false : this.mVibrator.hasVibrator();
        this.mSupportsMicPrivacyToggle = ((android.hardware.SensorPrivacyManager) context.getSystemService(android.hardware.SensorPrivacyManager.class)).supportsSensorToggle(1);
        this.mUseVolumeGroupAliases = this.mContext.getResources().getBoolean(android.R.bool.config_goToSleepOnButtonPressTheaterMode);
        if (android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategies().size() > 0) {
            for (int numStreamTypes = android.media.AudioSystem.getNumStreamTypes() - 1; numStreamTypes >= 0; numStreamTypes--) {
                android.media.AudioAttributes audioAttributesForStrategyWithLegacyStreamType = android.media.audiopolicy.AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(numStreamTypes);
                int maxVolumeIndexForAttributes = android.media.AudioSystem.getMaxVolumeIndexForAttributes(audioAttributesForStrategyWithLegacyStreamType);
                if (maxVolumeIndexForAttributes != -1) {
                    MAX_STREAM_VOLUME[numStreamTypes] = maxVolumeIndexForAttributes;
                }
                int minVolumeIndexForAttributes = android.media.AudioSystem.getMinVolumeIndexForAttributes(audioAttributesForStrategyWithLegacyStreamType);
                if (minVolumeIndexForAttributes != -1) {
                    MIN_STREAM_VOLUME[numStreamTypes] = minVolumeIndexForAttributes;
                }
            }
            if (this.mUseVolumeGroupAliases) {
                for (int i5 = 0; i5 < android.media.AudioSystem.DEFAULT_STREAM_VOLUME.length; i5++) {
                    android.media.AudioSystem.DEFAULT_STREAM_VOLUME[i5] = -1;
                }
            }
        }
        int i6 = android.os.SystemProperties.getInt("ro.config.vc_call_vol_steps", -1);
        if (i6 == -1) {
            c = 0;
        } else {
            c = 0;
            MAX_STREAM_VOLUME[0] = i6;
        }
        int i7 = android.os.SystemProperties.getInt("ro.config.vc_call_vol_default", -1);
        if (i7 == -1 || i7 > MAX_STREAM_VOLUME[c] || i7 < MIN_STREAM_VOLUME[c]) {
            android.media.AudioSystem.DEFAULT_STREAM_VOLUME[c] = (MAX_STREAM_VOLUME[c] * 3) / 4;
        } else {
            android.media.AudioSystem.DEFAULT_STREAM_VOLUME[c] = i7;
        }
        int i8 = android.os.SystemProperties.getInt("ro.config.media_vol_steps", -1);
        if (i8 != -1) {
            MAX_STREAM_VOLUME[3] = i8;
        }
        int i9 = android.os.SystemProperties.getInt("ro.config.media_vol_default", -1);
        if (i9 != -1 && i9 <= MAX_STREAM_VOLUME[3] && i9 >= MIN_STREAM_VOLUME[3]) {
            android.media.AudioSystem.DEFAULT_STREAM_VOLUME[3] = i9;
        } else if (isPlatformTelevision()) {
            android.media.AudioSystem.DEFAULT_STREAM_VOLUME[3] = MAX_STREAM_VOLUME[3] / 4;
        } else {
            android.media.AudioSystem.DEFAULT_STREAM_VOLUME[3] = MAX_STREAM_VOLUME[3] / 3;
        }
        int i10 = android.os.SystemProperties.getInt("ro.config.alarm_vol_steps", -1);
        if (i10 != -1) {
            MAX_STREAM_VOLUME[4] = i10;
        }
        com.android.media.audio.Flags.alarmMinVolumeZero();
        try {
            int integer3 = this.mContext.getResources().getInteger(android.R.integer.config_audio_alarm_min_vol);
            if (integer3 <= MAX_STREAM_VOLUME[4]) {
                MIN_STREAM_VOLUME[4] = integer3;
            } else {
                android.util.Log.e(TAG, "Error min alarm volume greater than max alarm volume");
            }
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Log.e(TAG, "Error querying for alarm min volume ", e);
        }
        int i11 = android.os.SystemProperties.getInt("ro.config.alarm_vol_default", -1);
        if (i11 != -1) {
            c2 = 4;
            if (i11 <= MAX_STREAM_VOLUME[4]) {
                android.media.AudioSystem.DEFAULT_STREAM_VOLUME[4] = i11;
                i = android.os.SystemProperties.getInt("ro.config.system_vol_steps", -1);
                if (i != -1) {
                    MAX_STREAM_VOLUME[1] = i;
                }
                i2 = android.os.SystemProperties.getInt("ro.config.system_vol_default", -1);
                if (i2 == -1 && i2 <= MAX_STREAM_VOLUME[1]) {
                    android.media.AudioSystem.DEFAULT_STREAM_VOLUME[1] = i2;
                } else {
                    android.media.AudioSystem.DEFAULT_STREAM_VOLUME[1] = MAX_STREAM_VOLUME[1];
                }
                i3 = android.os.SystemProperties.getInt("ro.config.assistant_vol_min", -1);
                if (i3 != -1) {
                    MIN_STREAM_VOLUME[11] = i3;
                }
                int[] iArr = {5, 2};
                int[] iArr2 = {android.R.integer.config_audio_notif_vol_steps, android.R.integer.config_audio_ring_vol_steps};
                int[] iArr3 = {android.R.integer.config_audio_notif_vol_default, android.R.integer.config_audio_ring_vol_default};
                for (i4 = 0; i4 < 2; i4++) {
                    try {
                        integer2 = this.mContext.getResources().getInteger(iArr2[i4]);
                    } catch (android.content.res.Resources.NotFoundException e2) {
                        android.util.Log.e(TAG, "Error querying max vol for stream type " + iArr[i4], e2);
                    }
                    if (integer2 <= 0) {
                        throw new java.lang.IllegalArgumentException("Invalid negative max volume for stream " + iArr[i4]);
                    }
                    android.util.Log.i(TAG, "Stream " + iArr[i4] + ": using max vol of " + integer2);
                    MAX_STREAM_VOLUME[iArr[i4]] = integer2;
                    try {
                        integer = this.mContext.getResources().getInteger(iArr3[i4]);
                    } catch (android.content.res.Resources.NotFoundException e3) {
                        android.util.Log.e(TAG, "Error querying default vol for stream type " + iArr[i4], e3);
                    }
                    if (integer > MAX_STREAM_VOLUME[iArr[i4]]) {
                        throw new java.lang.IllegalArgumentException("Invalid default volume (" + integer + ") for stream " + iArr[i4] + ", greater than max volume of " + MAX_STREAM_VOLUME[iArr[i4]]);
                    }
                    if (integer < MIN_STREAM_VOLUME[iArr[i4]]) {
                        throw new java.lang.IllegalArgumentException("Invalid default volume (" + integer + ") for stream " + iArr[i4] + ", lower than min volume of " + MIN_STREAM_VOLUME[iArr[i4]]);
                    }
                    android.util.Log.i(TAG, "Stream " + iArr[i4] + ": using default vol of " + integer);
                    android.media.AudioSystem.DEFAULT_STREAM_VOLUME[iArr[i4]] = integer;
                }
                if (looper == null) {
                    this.mAudioHandler = new com.android.server.audio.AudioService.AudioHandler(looper);
                } else {
                    createAudioSystemThread();
                }
                this.mSoundDoseHelper = new com.android.server.audio.SoundDoseHelper(this, this.mContext, this.mAudioHandler, this.mSettings, this.mVolumeController);
                android.media.AudioSystem.setErrorCallback(this.mAudioSystemCallback);
                updateAudioHalPids();
                this.mUseFixedVolume = this.mContext.getResources().getBoolean(android.R.bool.config_useAutoSuspend);
                this.mRingerModeAffectsAlarm = this.mContext.getResources().getBoolean(android.R.bool.config_audio_ringer_mode_affects_alarm_stream);
                this.mRecordMonitor = new com.android.server.audio.RecordingActivityMonitor(this.mContext);
                this.mRecordMonitor.registerRecordingCallback(this.mVoiceRecordingActivityMonitor, true);
                updateStreamVolumeAlias(false, TAG);
                readPersistedSettings();
                readUserRestrictions();
                this.mLoudnessCodecHelper = new com.android.server.audio.LoudnessCodecHelper(this);
                this.mPlaybackMonitor = new com.android.server.audio.PlaybackActivityMonitor(context, MAX_STREAM_VOLUME[4], new java.util.function.Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda18
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.audio.AudioService.this.lambda$new$1((android.media.AudioDeviceAttributes) obj);
                    }
                });
                this.mPlaybackMonitor.registerPlaybackCallback(this.mPlaybackActivityMonitor, true);
                this.mMediaFocusControl = new com.android.server.audio.MediaFocusControl(this.mContext, this.mPlaybackMonitor);
                readAndSetLowRamDevice();
                this.mIsCallScreeningModeSupported = android.media.AudioSystem.isCallScreeningModeSupported();
                if (this.mSystemServer.isPrivileged()) {
                    com.android.server.LocalServices.addService(android.media.AudioManagerInternal.class, new com.android.server.audio.AudioService.AudioServiceInternal());
                    this.mUserManagerInternal.addUserRestrictionsListener(this.mUserRestrictionsListener);
                    this.mRecordMonitor.initMonitor();
                }
                this.mMonitorRotation = android.os.SystemProperties.getBoolean("ro.audio.monitorRotation", false);
                this.mHasSpatializerEffect = android.os.SystemProperties.getBoolean("ro.audio.spatializer_enabled", false);
                com.android.server.audio.AudioSystemAdapter.setRoutingListener(this);
                com.android.server.audio.AudioSystemAdapter.setVolRangeInitReqListener(this);
                queueMsgUnderWakeLock(this.mAudioHandler, 101, 0, 0, null, 0);
                queueMsgUnderWakeLock(this.mAudioHandler, 103, 0, 0, null, 0);
                queueMsgUnderWakeLock(this.mAudioHandler, 102, 0, 0, null, 0);
                this.mDisplayManager = (android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class);
                this.mMusicFxHelper = new com.android.server.audio.MusicFxHelper(this.mContext, this.mAudioHandler);
                this.mHardeningEnforcer = new com.android.server.audio.HardeningEnforcer(this.mContext, isPlatformAutomotive(), this.mAppOps, context.getPackageManager());
            }
        } else {
            c2 = 4;
        }
        android.media.AudioSystem.DEFAULT_STREAM_VOLUME[c2] = (MAX_STREAM_VOLUME[c2] * 6) / 7;
        i = android.os.SystemProperties.getInt("ro.config.system_vol_steps", -1);
        if (i != -1) {
        }
        i2 = android.os.SystemProperties.getInt("ro.config.system_vol_default", -1);
        if (i2 == -1) {
        }
        android.media.AudioSystem.DEFAULT_STREAM_VOLUME[1] = MAX_STREAM_VOLUME[1];
        i3 = android.os.SystemProperties.getInt("ro.config.assistant_vol_min", -1);
        if (i3 != -1) {
        }
        int[] iArr4 = {5, 2};
        int[] iArr22 = {android.R.integer.config_audio_notif_vol_steps, android.R.integer.config_audio_ring_vol_steps};
        int[] iArr32 = {android.R.integer.config_audio_notif_vol_default, android.R.integer.config_audio_ring_vol_default};
        while (i4 < 2) {
        }
        if (looper == null) {
        }
        this.mSoundDoseHelper = new com.android.server.audio.SoundDoseHelper(this, this.mContext, this.mAudioHandler, this.mSettings, this.mVolumeController);
        android.media.AudioSystem.setErrorCallback(this.mAudioSystemCallback);
        updateAudioHalPids();
        this.mUseFixedVolume = this.mContext.getResources().getBoolean(android.R.bool.config_useAutoSuspend);
        this.mRingerModeAffectsAlarm = this.mContext.getResources().getBoolean(android.R.bool.config_audio_ringer_mode_affects_alarm_stream);
        this.mRecordMonitor = new com.android.server.audio.RecordingActivityMonitor(this.mContext);
        this.mRecordMonitor.registerRecordingCallback(this.mVoiceRecordingActivityMonitor, true);
        updateStreamVolumeAlias(false, TAG);
        readPersistedSettings();
        readUserRestrictions();
        this.mLoudnessCodecHelper = new com.android.server.audio.LoudnessCodecHelper(this);
        this.mPlaybackMonitor = new com.android.server.audio.PlaybackActivityMonitor(context, MAX_STREAM_VOLUME[4], new java.util.function.Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.audio.AudioService.this.lambda$new$1((android.media.AudioDeviceAttributes) obj);
            }
        });
        this.mPlaybackMonitor.registerPlaybackCallback(this.mPlaybackActivityMonitor, true);
        this.mMediaFocusControl = new com.android.server.audio.MediaFocusControl(this.mContext, this.mPlaybackMonitor);
        readAndSetLowRamDevice();
        this.mIsCallScreeningModeSupported = android.media.AudioSystem.isCallScreeningModeSupported();
        if (this.mSystemServer.isPrivileged()) {
        }
        this.mMonitorRotation = android.os.SystemProperties.getBoolean("ro.audio.monitorRotation", false);
        this.mHasSpatializerEffect = android.os.SystemProperties.getBoolean("ro.audio.spatializer_enabled", false);
        com.android.server.audio.AudioSystemAdapter.setRoutingListener(this);
        com.android.server.audio.AudioSystemAdapter.setVolRangeInitReqListener(this);
        queueMsgUnderWakeLock(this.mAudioHandler, 101, 0, 0, null, 0);
        queueMsgUnderWakeLock(this.mAudioHandler, 103, 0, 0, null, 0);
        queueMsgUnderWakeLock(this.mAudioHandler, 102, 0, 0, null, 0);
        this.mDisplayManager = (android.hardware.display.DisplayManager) context.getSystemService(android.hardware.display.DisplayManager.class);
        this.mMusicFxHelper = new com.android.server.audio.MusicFxHelper(this.mContext, this.mAudioHandler);
        this.mHardeningEnforcer = new com.android.server.audio.HardeningEnforcer(this.mContext, isPlatformAutomotive(), this.mAppOps, context.getPackageManager());
    }

    private void initVolumeStreamStates() {
        int numStreamTypes = android.media.AudioSystem.getNumStreamTypes();
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            for (int i = numStreamTypes - 1; i >= 0; i--) {
                try {
                    com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i];
                    int volumeGroupForStreamType = getVolumeGroupForStreamType(i);
                    if (volumeGroupForStreamType != -1 && sVolumeGroupStates.indexOfKey(volumeGroupForStreamType) >= 0) {
                        volumeStreamState.setVolumeGroupState(sVolumeGroupStates.get(volumeGroupForStreamType));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInitStreamsAndVolumes() {
        synchronized (this.mSettingsLock) {
            this.mCameraSoundForced = readCameraSoundForced();
            sendMsg(this.mAudioHandler, 8, 2, 4, this.mCameraSoundForced ? 11 : 0, new java.lang.String("AudioService ctor"), 0);
        }
        createStreamStates();
        initVolumeGroupStates();
        this.mSoundDoseHelper.initSafeMediaVolumeIndex();
        initVolumeStreamStates();
        sRingerAndZenModeMutedStreams = 0;
        sMuteLogger.enqueue(new com.android.server.audio.AudioServiceEvents.RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "onInitStreamsAndVolumes"));
        setRingerModeInt(getRingerModeInternal(), false);
        com.android.media.audio.Flags.disablePrescaleAbsoluteVolume();
        initExternalEventReceivers();
        checkVolumeRangeInitialization("AudioService()");
    }

    private android.media.session.MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (android.media.session.MediaSessionManager) this.mContext.getSystemService("media_session");
        }
        return this.mMediaSessionManager;
    }

    private void initExternalEventReceivers() {
        this.mSettingsObserver = new com.android.server.audio.AudioService.SettingsObserver();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED");
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        if (this.mDisplayManager == null) {
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
        }
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_BACKGROUND");
        intentFilter.addAction("android.intent.action.USER_FOREGROUND");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        if (this.mMonitorRotation) {
            com.android.server.audio.RotationHelper.init(this.mContext, this.mAudioHandler, new java.util.function.Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.audio.AudioService.this.lambda$initExternalEventReceivers$2((java.lang.Integer) obj);
                }
            }, new java.util.function.Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.audio.AudioService.this.lambda$initExternalEventReceivers$3((java.lang.Boolean) obj);
                }
            });
        }
        intentFilter.addAction("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION");
        intentFilter.addAction("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION");
        intentFilter.addAction("com.android.server.audio.action.CHECK_MUSIC_ACTIVE");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiverAsUser(this.mReceiver, android.os.UserHandle.ALL, intentFilter, null, null, 2);
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
        if (subscriptionManager == null) {
            android.util.Log.e(TAG, "initExternalEventReceivers cannot create SubscriptionManager!");
        } else {
            subscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionChangedListener);
        }
        if (this.mDisplayManager != null) {
            this.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mAudioHandler);
        }
    }

    public void systemReady() {
        sendMsg(this.mAudioHandler, 16, 2, 0, 0, null, 0);
    }

    private void updateVibratorInfos() {
        android.os.VibratorManager vibratorManager = (android.os.VibratorManager) this.mContext.getSystemService(android.os.VibratorManager.class);
        if (vibratorManager == null) {
            android.util.Slog.e(TAG, "Vibrator manager is not found");
            return;
        }
        int[] vibratorIds = vibratorManager.getVibratorIds();
        if (vibratorIds.length == 0) {
            android.util.Slog.d(TAG, "No vibrator found");
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(vibratorIds.length);
        for (int i : vibratorIds) {
            android.os.Vibrator vibrator = vibratorManager.getVibrator(i);
            if (vibrator == null) {
                android.util.Slog.w(TAG, "Vibrator(" + i + ") is not found");
            } else {
                arrayList.add(vibrator);
            }
        }
        if (arrayList.isEmpty()) {
            android.util.Slog.w(TAG, "Cannot find any available vibrator");
        } else {
            android.media.AudioSystem.setVibratorInfos(arrayList);
        }
    }

    public void onSystemReady() {
        this.mSystemReady = true;
        scheduleLoadSoundEffects();
        this.mDeviceBroker.onSystemReady();
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.hdmi.cec")) {
            synchronized (this.mHdmiClientLock) {
                try {
                    this.mHdmiManager = (android.hardware.hdmi.HdmiControlManager) this.mContext.getSystemService(android.hardware.hdmi.HdmiControlManager.class);
                    if (this.mHdmiManager != null) {
                        this.mHdmiManager.addHdmiControlStatusChangeListener(this.mHdmiControlStatusChangeListenerCallback);
                        this.mHdmiManager.addHdmiCecVolumeControlFeatureListener(this.mContext.getMainExecutor(), this.mMyHdmiCecVolumeControlFeatureListener);
                    }
                    this.mHdmiTvClient = this.mHdmiManager.getTvClient();
                    if (this.mHdmiTvClient != null) {
                        this.mFixedVolumeDevices.removeAll(android.media.AudioSystem.DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER_SET);
                    }
                    this.mHdmiPlaybackClient = this.mHdmiManager.getPlaybackClient();
                    this.mHdmiAudioSystemClient = this.mHdmiManager.getAudioSystemClient();
                } finally {
                }
            }
        }
        if (this.mSupportsMicPrivacyToggle) {
            this.mSensorPrivacyManagerInternal.addSensorPrivacyListenerForAllUsers(1, new android.hardware.SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda12
                public final void onSensorPrivacyChanged(int i, boolean z) {
                    com.android.server.audio.AudioService.this.lambda$onSystemReady$4(i, z);
                }
            });
        }
        this.mNm = (android.app.NotificationManager) this.mContext.getSystemService("notification");
        this.mSoundDoseHelper.configureSafeMedia(true, TAG);
        initA11yMonitoring();
        this.mRoleObserver = new com.android.server.audio.AudioService.RoleObserver();
        this.mRoleObserver.register();
        onIndicateSystemReady();
        this.mMicMuteFromSystemCached = this.mAudioSystem.isMicrophoneMuted();
        setMicMuteFromSwitchInput();
        initMinStreamVolumeWithoutModifyAudioSettings();
        updateVibratorInfos();
        synchronized (this.mSupportedSystemUsagesLock) {
            android.media.AudioSystem.setSupportedSystemUsages(this.mSupportedSystemUsages);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$4(int i, boolean z) {
        if (i == getCurrentUserId()) {
            this.mMicMuteFromPrivacyToggle = z;
            setMicrophoneMuteNoCallerCheck(getCurrentUserId());
        }
    }

    @Override // com.android.server.audio.AudioSystemAdapter.OnRoutingUpdatedListener
    public void onRoutingUpdatedFromNative() {
        sendMsg(this.mAudioHandler, 41, 0, 0, 0, null, 0);
    }

    void onRoutingUpdatedFromAudioThread() {
        if (this.mHasSpatializerEffect) {
            this.mSpatializerHelper.onRoutingUpdated();
        }
        checkMuteAwaitConnection();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: onRotationUpdate, reason: merged with bridge method [inline-methods] */
    public void lambda$initExternalEventReceivers$2(java.lang.Integer num) {
        this.mSpatializerHelper.setDisplayOrientation((float) ((num.intValue() * 3.141592653589793d) / 180.0d));
        sendMsg(this.mAudioHandler, 48, 0, 0, 0, "rotation=" + num, 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: onFoldStateUpdate, reason: merged with bridge method [inline-methods] */
    public void lambda$initExternalEventReceivers$3(java.lang.Boolean bool) {
        this.mSpatializerHelper.setFoldState(bool.booleanValue());
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("device_folded=");
        sb.append(bool.booleanValue() ? "on" : "off");
        sendMsg(this.mAudioHandler, 49, 0, 0, 0, sb.toString(), 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: ignorePlayerLogs, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0(@android.annotation.NonNull android.media.PlayerBase playerBase) {
        sendMsg(this.mAudioHandler, 51, 0, playerBase.getPlayerIId(), 0, null, 0);
    }

    @Override // com.android.server.audio.AudioSystemAdapter.OnVolRangeInitRequestListener
    public void onVolumeRangeInitRequestFromNative() {
        sendMsg(this.mAudioHandler, 34, 0, 0, 0, "onVolumeRangeInitRequestFromNative", 0);
    }

    class RoleObserver implements android.app.role.OnRoleHoldersChangedListener {
        private final java.util.concurrent.Executor mExecutor;
        private android.app.role.RoleManager mRm;

        RoleObserver() {
            this.mExecutor = com.android.server.audio.AudioService.this.mContext.getMainExecutor();
        }

        public void register() {
            this.mRm = (android.app.role.RoleManager) com.android.server.audio.AudioService.this.mContext.getSystemService("role");
            if (this.mRm != null) {
                this.mRm.addOnRoleHoldersChangedListenerAsUser(this.mExecutor, this, android.os.UserHandle.ALL);
                synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                    com.android.server.audio.AudioService.this.updateAssistantUIdLocked(true);
                }
            }
        }

        public void onRoleHoldersChanged(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.UserHandle userHandle) {
            if ("android.app.role.ASSISTANT".equals(str)) {
                synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                    com.android.server.audio.AudioService.this.updateAssistantUIdLocked(false);
                }
            }
        }

        public java.lang.String getAssistantRoleHolder() {
            if (this.mRm == null) {
                return "";
            }
            java.util.List roleHolders = this.mRm.getRoleHolders("android.app.role.ASSISTANT");
            return roleHolders.size() == 0 ? "" : (java.lang.String) roleHolders.get(0);
        }
    }

    void onIndicateSystemReady() {
        if (android.media.AudioSystem.systemReady() == 0) {
            return;
        }
        sendMsg(this.mAudioHandler, 20, 0, 0, 0, null, 1000);
    }

    public void onAudioServerDied() {
        int i;
        if (!this.mSystemReady || android.media.AudioSystem.checkAudioFlinger() != 0) {
            android.util.Log.e(TAG, "Audioserver died.");
            sLifecycleLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("onAudioServerDied() audioserver died"));
            sendMsg(this.mAudioHandler, 4, 1, 0, 0, null, 500);
            return;
        }
        android.util.Log.i(TAG, "Audioserver started.");
        sLifecycleLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("onAudioServerDied() audioserver started"));
        updateAudioHalPids();
        android.media.AudioSystem.setParameters("restarting=true");
        readAndSetLowRamDevice();
        this.mIsCallScreeningModeSupported = android.media.AudioSystem.isCallScreeningModeSupported();
        this.mDeviceBroker.onAudioServerDied();
        synchronized (this.mDeviceBroker.mSetModeLock) {
            onUpdateAudioMode(-1, android.os.Process.myPid(), this.mContext.getPackageName(), true);
        }
        synchronized (this.mSettingsLock) {
            i = this.mCameraSoundForced ? 11 : 0;
        }
        this.mDeviceBroker.setForceUse_Async(4, i, "onAudioServerDied");
        onReinitVolumes("after audioserver restart");
        restoreVolumeGroups();
        updateMasterMono(this.mContentResolver);
        updateMasterBalance(this.mContentResolver);
        setRingerModeInt(getRingerModeInternal(), false);
        if (this.mMonitorRotation) {
            com.android.server.audio.RotationHelper.updateOrientation();
        }
        this.mRestorableParameters.restoreAll();
        synchronized (this.mSettingsLock) {
            this.mDeviceBroker.setForceUse_Async(3, this.mDockAudioMediaEnabled ? 9 : 0, "onAudioServerDied");
            sendEncodedSurroundMode(this.mContentResolver, "onAudioServerDied");
            sendEnabledSurroundFormats(this.mContentResolver, true);
            android.media.AudioSystem.setRttEnabled(this.mRttEnabled);
            resetAssistantServicesUidsLocked();
        }
        synchronized (this.mAccessibilityServiceUidsLock) {
            android.media.AudioSystem.setA11yServicesUids(this.mAccessibilityServiceUids);
        }
        synchronized (this.mInputMethodServiceUidLock) {
            this.mAudioSystem.setCurrentImeUid(this.mInputMethodServiceUid);
        }
        synchronized (this.mHdmiClientLock) {
            try {
                if (this.mHdmiManager != null && this.mHdmiTvClient != null) {
                    setHdmiSystemAudioSupported(this.mHdmiSystemAudioSupported);
                }
            } finally {
            }
        }
        synchronized (this.mSupportedSystemUsagesLock) {
            android.media.AudioSystem.setSupportedSystemUsages(this.mSupportedSystemUsages);
        }
        synchronized (this.mAudioPolicies) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (com.android.server.audio.AudioService.AudioPolicyProxy audioPolicyProxy : this.mAudioPolicies.values()) {
                    int connectMixes = audioPolicyProxy.connectMixes();
                    if (connectMixes != 0) {
                        android.util.Log.e(TAG, "onAudioServerDied: error " + android.media.AudioSystem.audioSystemErrorToString(connectMixes) + " when connecting mixes for policy " + audioPolicyProxy.toLogFriendlyString());
                        arrayList.add(audioPolicyProxy);
                    } else {
                        int i2 = audioPolicyProxy.setupDeviceAffinities();
                        if (i2 != 0) {
                            android.util.Log.e(TAG, "onAudioServerDied: error " + android.media.AudioSystem.audioSystemErrorToString(i2) + " when connecting device affinities for policy " + audioPolicyProxy.toLogFriendlyString());
                            arrayList.add(audioPolicyProxy);
                        }
                    }
                }
                arrayList.forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.audio.AudioService.AudioPolicyProxy) obj).release();
                    }
                });
            } finally {
            }
        }
        synchronized (this.mPlaybackMonitor) {
            try {
                for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry : this.mPlaybackMonitor.getAllAllowedCapturePolicies().entrySet()) {
                    int allowedCapturePolicy = this.mAudioSystem.setAllowedCapturePolicy(entry.getKey().intValue(), android.media.AudioAttributes.capturePolicyToFlags(entry.getValue().intValue(), 0));
                    if (allowedCapturePolicy != 0) {
                        android.util.Log.e(TAG, "Failed to restore capture policy, uid: " + entry.getKey() + ", capture policy: " + entry.getValue() + ", result: " + allowedCapturePolicy);
                        this.mPlaybackMonitor.setAllowedCapturePolicy(entry.getKey().intValue(), 1);
                    }
                }
            } finally {
            }
        }
        this.mSpatializerHelper.reset(this.mHasSpatializerEffect);
        this.mSoundDoseHelper.reset();
        if (this.mMonitorRotation) {
            com.android.server.audio.RotationHelper.forceUpdate();
        }
        onIndicateSystemReady();
        android.media.AudioSystem.setParameters("restarting=false");
        sendMsg(this.mAudioHandler, 23, 2, 1, 0, null, 0);
        setMicrophoneMuteNoCallerCheck(getCurrentUserId());
        setMicMuteFromSwitchInput();
        updateVibratorInfos();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRemoveAssistantServiceUids(int[] iArr) {
        synchronized (this.mSettingsLock) {
            removeAssistantServiceUidsLocked(iArr);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private void removeAssistantServiceUidsLocked(int[] iArr) {
        boolean z = false;
        for (int i = 0; i < iArr.length; i++) {
            if (!this.mAssistantUids.remove(java.lang.Integer.valueOf(iArr[i]))) {
                android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Cannot remove assistant service, uid(%d) not present", new java.lang.Object[]{java.lang.Integer.valueOf(iArr[i])}));
            } else {
                z = true;
            }
        }
        if (z) {
            updateAssistantServicesUidsLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAddAssistantServiceUids(int[] iArr) {
        synchronized (this.mSettingsLock) {
            addAssistantServiceUidsLocked(iArr);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private void addAssistantServiceUidsLocked(int[] iArr) {
        boolean z = false;
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] != -1) {
                if (!this.mAssistantUids.add(java.lang.Integer.valueOf(iArr[i]))) {
                    android.util.Slog.e(TAG, android.text.TextUtils.formatSimple("Cannot add assistant service, uid(%d) already present", new java.lang.Object[]{java.lang.Integer.valueOf(iArr[i])}));
                } else {
                    z = true;
                }
            }
        }
        if (z) {
            updateAssistantServicesUidsLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private void resetAssistantServicesUidsLocked() {
        this.mAssistantUids.clear();
        updateAssistantUIdLocked(true);
    }

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private void updateAssistantServicesUidsLocked() {
        android.media.AudioSystem.setAssistantServicesUids(this.mAssistantUids.stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActiveAssistantServiceUids() {
        int[] iArr;
        synchronized (this.mSettingsLock) {
            iArr = this.mActiveAssistantServiceUids;
        }
        android.media.AudioSystem.setActiveAssistantServicesUids(iArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReinitVolumes(@android.annotation.NonNull java.lang.String str) {
        int i;
        int numStreamTypes = android.media.AudioSystem.getNumStreamTypes() - 1;
        while (true) {
            if (numStreamTypes < 0) {
                i = 0;
                break;
            }
            com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[numStreamTypes];
            i = android.media.AudioSystem.initStreamVolume(numStreamTypes, volumeStreamState.mIndexMin / 10, volumeStreamState.mIndexMax / 10);
            if (i != 0) {
                android.util.Log.e(TAG, "Failed to initStreamVolume (" + i + ") for stream " + numStreamTypes);
                break;
            }
            volumeStreamState.applyAllVolumes();
            numStreamTypes--;
        }
        if (i != 0) {
            sLifecycleLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(str + ": initStreamVolume failed with " + i + " will retry").printLog(1, TAG));
            sendMsg(this.mAudioHandler, 34, 1, 0, 0, str, 2000);
            return;
        }
        if (!checkVolumeRangeInitialization(str)) {
            return;
        }
        sLifecycleLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(str + ": initStreamVolume succeeded").printLog(0, TAG));
    }

    private boolean checkVolumeRangeInitialization(java.lang.String str) {
        boolean z = false;
        int[] iArr = {4, 2, 3, 0, 10};
        int i = 0;
        while (true) {
            if (i >= 5) {
                z = true;
                break;
            }
            android.media.AudioAttributes build = new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(iArr[i]).build();
            if (android.media.AudioSystem.getMaxVolumeIndexForAttributes(build) < 0 || android.media.AudioSystem.getMinVolumeIndexForAttributes(build) < 0) {
                break;
            }
            i++;
        }
        if (!z) {
            sLifecycleLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(str + ": initStreamVolume succeeded but invalid mix/max levels, will retry").printLog(2, TAG));
            sendMsg(this.mAudioHandler, 34, 1, 0, 0, str, 2000);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDispatchAudioServerStateChange(boolean z) {
        synchronized (this.mAudioServerStateListeners) {
            java.util.Iterator<com.android.server.audio.AudioService.AsdProxy> it = this.mAudioServerStateListeners.values().iterator();
            while (it.hasNext()) {
                try {
                    it.next().callback().dispatchAudioServerStateChange(z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(TAG, "Could not call dispatchAudioServerStateChange()", e);
                }
            }
        }
    }

    private void createAudioSystemThread() {
        this.mAudioSystemThread = new com.android.server.audio.AudioService.AudioSystemThread();
        this.mAudioSystemThread.start();
        waitForAudioHandlerCreation();
    }

    private void waitForAudioHandlerCreation() {
        synchronized (this) {
            while (this.mAudioHandler == null) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.e(TAG, "Interrupted while waiting on volume handler.");
                }
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void setSupportedSystemUsages(@android.annotation.NonNull int[] iArr) {
        super.setSupportedSystemUsages_enforcePermission();
        verifySystemUsages(iArr);
        synchronized (this.mSupportedSystemUsagesLock) {
            android.media.AudioSystem.setSupportedSystemUsages(iArr);
            this.mSupportedSystemUsages = iArr;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    @android.annotation.NonNull
    public int[] getSupportedSystemUsages() {
        int[] copyOf;
        super.getSupportedSystemUsages_enforcePermission();
        synchronized (this.mSupportedSystemUsagesLock) {
            copyOf = java.util.Arrays.copyOf(this.mSupportedSystemUsages, this.mSupportedSystemUsages.length);
        }
        return copyOf;
    }

    private void verifySystemUsages(@android.annotation.NonNull int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            if (!android.media.AudioAttributes.isSystemUsage(iArr[i])) {
                throw new java.lang.IllegalArgumentException("Non-system usage provided: " + iArr[i]);
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    @android.annotation.NonNull
    public java.util.List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() {
        super.getAudioProductStrategies_enforcePermission();
        return android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategies();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    @android.annotation.NonNull
    public java.util.List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() {
        super.getAudioVolumeGroups_enforcePermission();
        return this.mAudioVolumeGroupHelper.getAudioVolumeGroups();
    }

    private void checkAllAliasStreamVolumes() {
        synchronized (this.mSettingsLock) {
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                try {
                    int numStreamTypes = android.media.AudioSystem.getNumStreamTypes();
                    for (int i = 0; i < numStreamTypes; i++) {
                        this.mStreamStates[i].setAllIndexes(this.mStreamStates[mStreamVolumeAlias[i]], TAG);
                        if (!this.mStreamStates[i].mIsMuted) {
                            this.mStreamStates[i].applyAllVolumes();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void postCheckVolumeCecOnHdmiConnection(int i, java.lang.String str) {
        sendMsg(this.mAudioHandler, 28, 0, i, 0, str, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCheckVolumeCecOnHdmiConnection(int i, java.lang.String str) {
        if (i == 1) {
            if (this.mSoundDoseHelper.safeDevicesContains(1024)) {
                this.mSoundDoseHelper.scheduleMusicActiveCheck();
            }
            if (isPlatformTelevision()) {
                synchronized (this.mHdmiClientLock) {
                    try {
                        if (this.mHdmiManager != null && this.mHdmiPlaybackClient != null) {
                            updateHdmiCecSinkLocked(this.mFullVolumeDevices.contains(1024));
                        }
                    } finally {
                    }
                }
            }
            sendEnabledSurroundFormats(this.mContentResolver, true);
            return;
        }
        if (isPlatformTelevision()) {
            synchronized (this.mHdmiClientLock) {
                try {
                    if (this.mHdmiManager != null) {
                        updateHdmiCecSinkLocked(this.mFullVolumeDevices.contains(1024));
                    }
                } finally {
                }
            }
        }
    }

    private void postUpdateVolumeStatesForAudioDevice(int i, java.lang.String str) {
        sendMsg(this.mAudioHandler, 33, 2, i, 0, str, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateVolumeStatesForAudioDevice(int i, java.lang.String str) {
        int numStreamTypes = android.media.AudioSystem.getNumStreamTypes();
        synchronized (this.mSettingsLock) {
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                for (int i2 = 0; i2 < numStreamTypes; i2++) {
                    try {
                        updateVolumeStates(i, i2, str);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    private void updateVolumeStates(int i, int i2, java.lang.String str) {
        if (i == 4194304) {
            i = 2;
        }
        if (!this.mStreamStates[i2].hasIndexForDevice(i)) {
            this.mStreamStates[i2].setIndex(this.mStreamStates[mStreamVolumeAlias[i2]].getIndex(1073741824), i, str, true);
        }
        java.util.Iterator<android.media.AudioDeviceAttributes> it = getDevicesForAttributesInt(new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(i2).build(), true).iterator();
        while (it.hasNext()) {
            if (it.next().getType() == android.media.AudioDeviceInfo.convertInternalDeviceToDeviceType(i)) {
                this.mStreamStates[i2].checkFixedVolumeDevices();
                if (isStreamMute(i2) && this.mFullVolumeDevices.contains(java.lang.Integer.valueOf(i))) {
                    this.mStreamStates[i2].mute(false, "updateVolumeStates(" + str);
                }
            }
        }
    }

    private void checkAllFixedVolumeDevices() {
        int numStreamTypes = android.media.AudioSystem.getNumStreamTypes();
        for (int i = 0; i < numStreamTypes; i++) {
            this.mStreamStates[i].checkFixedVolumeDevices();
        }
    }

    private void checkAllFixedVolumeDevices(int i) {
        this.mStreamStates[i].checkFixedVolumeDevices();
    }

    private void checkMuteAffectedStreams() {
        for (int i = 0; i < this.mStreamStates.length; i++) {
            com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i];
            if (volumeStreamState.mIndexMin > 0 && volumeStreamState.mStreamType != 0 && volumeStreamState.mStreamType != 6) {
                this.mMuteAffectedStreams = (~(1 << volumeStreamState.mStreamType)) & this.mMuteAffectedStreams;
            }
        }
    }

    private void createStreamStates() {
        int numStreamTypes = android.media.AudioSystem.getNumStreamTypes();
        com.android.server.audio.AudioService.VolumeStreamState[] volumeStreamStateArr = new com.android.server.audio.AudioService.VolumeStreamState[numStreamTypes];
        this.mStreamStates = volumeStreamStateArr;
        for (int i = 0; i < numStreamTypes; i++) {
            volumeStreamStateArr[i] = new com.android.server.audio.AudioService.VolumeStreamState(android.provider.Settings.System.VOLUME_SETTINGS_INT[mStreamVolumeAlias[i]], i);
        }
        checkAllFixedVolumeDevices();
        checkAllAliasStreamVolumes();
        checkMuteAffectedStreams();
        updateDefaultVolumes();
    }

    private void updateDefaultVolumes() {
        for (int i = 0; i < this.mStreamStates.length; i++) {
            int i2 = mStreamVolumeAlias[i];
            if (this.mUseVolumeGroupAliases) {
                if (android.media.AudioSystem.DEFAULT_STREAM_VOLUME[i] == -1) {
                    i2 = 3;
                    int uiDefaultRescaledIndex = getUiDefaultRescaledIndex(3, i);
                    if (uiDefaultRescaledIndex >= MIN_STREAM_VOLUME[i] && uiDefaultRescaledIndex <= MAX_STREAM_VOLUME[i]) {
                        android.media.AudioSystem.DEFAULT_STREAM_VOLUME[i] = uiDefaultRescaledIndex;
                    }
                }
            }
            if (i != i2) {
                android.media.AudioSystem.DEFAULT_STREAM_VOLUME[i] = getUiDefaultRescaledIndex(i2, i);
            }
        }
    }

    private int getUiDefaultRescaledIndex(int i, int i2) {
        return (rescaleIndex(android.media.AudioSystem.DEFAULT_STREAM_VOLUME[i] * 10, i, i2) + 5) / 10;
    }

    private void dumpStreamStates(java.io.PrintWriter printWriter) {
        printWriter.println("\nStream volumes (device: index)");
        int numStreamTypes = android.media.AudioSystem.getNumStreamTypes();
        for (int i = 0; i < numStreamTypes; i++) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (mStreamVolumeAlias[i] != i) {
                sb.append(" (aliased to: ");
                sb.append(android.media.AudioSystem.STREAM_NAMES[mStreamVolumeAlias[i]]);
                sb.append(")");
            }
            printWriter.println("- " + android.media.AudioSystem.STREAM_NAMES[i] + ((java.lang.Object) sb) + ":");
            this.mStreamStates[i].dump(printWriter);
            printWriter.println("");
        }
        printWriter.print("\n- mute affected streams = 0x");
        printWriter.println(java.lang.Integer.toHexString(this.mMuteAffectedStreams));
    }

    private void updateStreamVolumeAlias(boolean z, java.lang.String str) {
        int i = 3;
        int i2 = sIndependentA11yVolume ? 10 : 3;
        int i3 = this.mContext.getResources().getBoolean(android.R.bool.config_unplugTurnsOnScreen) ? 11 : 3;
        if (this.mIsSingleVolume) {
            mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_TELEVISION.clone();
        } else if (this.mUseVolumeGroupAliases) {
            mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_NONE.clone();
            i = 8;
        } else {
            switch (this.mPlatformType) {
                case 1:
                    mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_VOICE.clone();
                    i = 2;
                    break;
                default:
                    mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_DEFAULT.clone();
                    break;
            }
            if (!this.mNotifAliasRing) {
                mStreamVolumeAlias[5] = 5;
            }
        }
        if (this.mIsSingleVolume) {
            this.mRingerModeAffectedStreams = 0;
        } else if (isInCommunication()) {
            this.mRingerModeAffectedStreams &= -257;
            i = 0;
        } else {
            this.mRingerModeAffectedStreams |= 256;
        }
        mStreamVolumeAlias[8] = i;
        mStreamVolumeAlias[10] = i2;
        mStreamVolumeAlias[11] = i3;
        if (z && this.mStreamStates != null) {
            updateDefaultVolumes();
            synchronized (this.mSettingsLock) {
                synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                    this.mStreamStates[8].setAllIndexes(this.mStreamStates[i], str);
                    this.mStreamStates[10].setSettingName(android.provider.Settings.System.VOLUME_SETTINGS_INT[i2]);
                    this.mStreamStates[10].setAllIndexes(this.mStreamStates[i2], str);
                }
            }
            if (sIndependentA11yVolume) {
                this.mStreamStates[10].readSettings();
            }
            setRingerModeInt(getRingerModeInternal(), false);
            sendMsg(this.mAudioHandler, 10, 2, 0, 0, this.mStreamStates[8], 0);
            sendMsg(this.mAudioHandler, 10, 2, 0, 0, this.mStreamStates[10], 0);
        }
        dispatchStreamAliasingUpdate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readDockAudioSettings(android.content.ContentResolver contentResolver) {
        this.mDockAudioMediaEnabled = this.mSettings.getGlobalInt(contentResolver, "dock_audio_media_enabled", 0) == 1;
        sendMsg(this.mAudioHandler, 8, 2, 3, this.mDockAudioMediaEnabled ? 9 : 0, new java.lang.String("readDockAudioSettings"), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMasterMono(android.content.ContentResolver contentResolver) {
        android.media.AudioSystem.setMasterMono(this.mSettings.getSystemIntForUser(contentResolver, "master_mono", 0, -2) == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMasterBalance(android.content.ContentResolver contentResolver) {
        float floatForUser = android.provider.Settings.System.getFloatForUser(contentResolver, "master_balance", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -2);
        if (android.media.AudioSystem.setMasterBalance(floatForUser) != 0) {
            android.util.Log.e(TAG, java.lang.String.format("setMasterBalance failed for %f", java.lang.Float.valueOf(floatForUser)));
        }
    }

    private void sendEncodedSurroundMode(android.content.ContentResolver contentResolver, java.lang.String str) {
        sendEncodedSurroundMode(this.mSettings.getGlobalInt(contentResolver, "encoded_surround_output", 0), str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEncodedSurroundMode(int i, java.lang.String str) {
        int i2;
        switch (i) {
            case 0:
                i2 = 0;
                break;
            case 1:
                i2 = 13;
                break;
            case 2:
                i2 = 14;
                break;
            case 3:
                i2 = 15;
                break;
            default:
                android.util.Log.e(TAG, "updateSurroundSoundSettings: illegal value " + i);
                i2 = 16;
                break;
        }
        if (i2 != 16) {
            this.mDeviceBroker.setForceUse_Async(6, i2, str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_AUDIO_POLICY") != 0) {
            throw new java.lang.SecurityException("Missing MANAGE_AUDIO_POLICY permission");
        }
        new com.android.server.audio.AudioManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public java.util.Map<java.lang.Integer, java.lang.Boolean> getSurroundFormats() {
        java.util.HashMap hashMap = new java.util.HashMap();
        int surroundFormats = android.media.AudioSystem.getSurroundFormats(hashMap);
        if (surroundFormats != 0) {
            android.util.Log.e(TAG, "getSurroundFormats failed:" + surroundFormats);
            return new java.util.HashMap();
        }
        return hashMap;
    }

    public java.util.List<java.lang.Integer> getReportedSurroundFormats() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int reportedSurroundFormats = android.media.AudioSystem.getReportedSurroundFormats(arrayList);
        if (reportedSurroundFormats != 0) {
            android.util.Log.e(TAG, "getReportedSurroundFormats failed:" + reportedSurroundFormats);
            return new java.util.ArrayList();
        }
        return arrayList;
    }

    public boolean isSurroundFormatEnabled(int i) {
        boolean contains;
        if (!isSurroundFormat(i)) {
            android.util.Log.w(TAG, "audioFormat to enable is not a surround format.");
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                contains = getEnabledFormats().contains(java.lang.Integer.valueOf(i));
            }
            return contains;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setSurroundFormatEnabled(int i, boolean z) {
        if (!isSurroundFormat(i)) {
            android.util.Log.w(TAG, "audioFormat to enable is not a surround format.");
            return false;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") != 0) {
            throw new java.lang.SecurityException("Missing WRITE_SETTINGS permission");
        }
        java.util.HashSet<java.lang.Integer> enabledFormats = getEnabledFormats();
        if (z) {
            enabledFormats.add(java.lang.Integer.valueOf(i));
        } else {
            enabledFormats.remove(java.lang.Integer.valueOf(i));
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                this.mSettings.putGlobalString(this.mContentResolver, "encoded_surround_output_enabled_formats", android.text.TextUtils.join(",", enabledFormats));
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_SETTINGS")
    public boolean setEncodedSurroundMode(int i) {
        setEncodedSurroundMode_enforcePermission();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                this.mSettings.putGlobalInt(this.mContentResolver, "encoded_surround_output", toEncodedSurroundSetting(i));
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public int getEncodedSurroundMode(int i) {
        int encodedSurroundOutputMode;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                encodedSurroundOutputMode = toEncodedSurroundOutputMode(this.mSettings.getGlobalInt(this.mContentResolver, "encoded_surround_output", 0), i);
            }
            return encodedSurroundOutputMode;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private java.util.HashSet<java.lang.Integer> getEnabledFormats() {
        final java.util.HashSet<java.lang.Integer> hashSet = new java.util.HashSet<>();
        java.lang.String globalString = this.mSettings.getGlobalString(this.mContentResolver, "encoded_surround_output_enabled_formats");
        if (globalString != null) {
            try {
                java.util.stream.IntStream mapToInt = java.util.Arrays.stream(android.text.TextUtils.split(globalString, ",")).mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda14());
                java.util.Objects.requireNonNull(hashSet);
                mapToInt.forEach(new java.util.function.IntConsumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda15
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        hashSet.add(java.lang.Integer.valueOf(i));
                    }
                });
            } catch (java.lang.NumberFormatException e) {
                android.util.Log.w(TAG, "ENCODED_SURROUND_OUTPUT_ENABLED_FORMATS misformatted.", e);
            }
        }
        return hashSet;
    }

    private int toEncodedSurroundOutputMode(int i, int i2) {
        if (i2 <= 31 && i > 3) {
            return -1;
        }
        switch (i) {
        }
        return -1;
    }

    private int toEncodedSurroundSetting(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    private boolean isSurroundFormat(int i) {
        for (int i2 : android.media.AudioFormat.SURROUND_SOUND_ENCODING) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEnabledSurroundFormats(android.content.ContentResolver contentResolver, boolean z) {
        if (this.mEncodedSurroundMode != 3) {
            return;
        }
        java.lang.String globalString = this.mSettings.getGlobalString(contentResolver, "encoded_surround_output_enabled_formats");
        if (globalString == null) {
            globalString = "";
        }
        if (!z && android.text.TextUtils.equals(globalString, this.mEnabledSurroundFormats)) {
            return;
        }
        this.mEnabledSurroundFormats = globalString;
        java.lang.String[] split = android.text.TextUtils.split(globalString, ",");
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str : split) {
            try {
                int intValue = java.lang.Integer.valueOf(str).intValue();
                if (isSurroundFormat(intValue) && !arrayList.contains(java.lang.Integer.valueOf(intValue))) {
                    arrayList.add(java.lang.Integer.valueOf(intValue));
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Invalid enabled surround format:" + str);
            }
        }
        this.mSettings.putGlobalString(this.mContext.getContentResolver(), "encoded_surround_output_enabled_formats", android.text.TextUtils.join(",", arrayList));
        sendMsg(this.mAudioHandler, 24, 2, 0, 0, arrayList, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEnableSurroundFormats(java.util.ArrayList<java.lang.Integer> arrayList) {
        for (int i : android.media.AudioFormat.SURROUND_SOUND_ENCODING) {
            boolean contains = arrayList.contains(java.lang.Integer.valueOf(i));
            android.util.Log.i(TAG, "enable surround format:" + i + " " + contains + " " + android.media.AudioSystem.setSurroundFormatEnabled(i, contains));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    public void updateAssistantUIdLocked(boolean z) {
        java.lang.String str;
        int i;
        if (this.mRoleObserver == null) {
            str = "";
        } else {
            str = this.mRoleObserver.getAssistantRoleHolder();
        }
        if (android.text.TextUtils.isEmpty(str)) {
            java.lang.String secureStringForUser = this.mSettings.getSecureStringForUser(this.mContentResolver, "voice_interaction_service", -2);
            if (android.text.TextUtils.isEmpty(secureStringForUser)) {
                secureStringForUser = this.mSettings.getSecureStringForUser(this.mContentResolver, "assistant", -2);
            }
            if (!android.text.TextUtils.isEmpty(secureStringForUser)) {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(secureStringForUser);
                if (unflattenFromString == null) {
                    android.util.Slog.w(TAG, "Invalid service name for voice_interaction_service: " + secureStringForUser);
                    return;
                }
                str = unflattenFromString.getPackageName();
            }
        }
        if (!android.text.TextUtils.isEmpty(str)) {
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager.checkPermission("android.permission.CAPTURE_AUDIO_HOTWORD", str) == 0) {
                try {
                    i = packageManager.getPackageUidAsUser(str, getCurrentUserId());
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.e(TAG, "updateAssistantUId() could not find UID for package: " + str);
                }
                if (this.mPrimaryAssistantUid == i || z) {
                    this.mAssistantUids.remove(java.lang.Integer.valueOf(this.mPrimaryAssistantUid));
                    this.mPrimaryAssistantUid = i;
                    addAssistantServiceUidsLocked(new int[]{this.mPrimaryAssistantUid});
                }
                return;
            }
        }
        i = -1;
        if (this.mPrimaryAssistantUid == i) {
        }
        this.mAssistantUids.remove(java.lang.Integer.valueOf(this.mPrimaryAssistantUid));
        this.mPrimaryAssistantUid = i;
        addAssistantServiceUidsLocked(new int[]{this.mPrimaryAssistantUid});
    }

    private void readPersistedSettings() {
        int i;
        int i2;
        if (!this.mSystemServer.isPrivileged()) {
            return;
        }
        android.content.ContentResolver contentResolver = this.mContentResolver;
        int i3 = 2;
        int globalInt = this.mSettings.getGlobalInt(contentResolver, "mode_ringer", 2);
        if (isValidRingerMode(globalInt)) {
            i = globalInt;
        } else {
            i = 2;
        }
        if (i == 1 && !this.mHasVibrator) {
            i = 0;
        }
        if (i != globalInt) {
            this.mSettings.putGlobalInt(contentResolver, "mode_ringer", i);
        }
        if (this.mUseFixedVolume || this.mIsSingleVolume) {
            i = 2;
        }
        synchronized (this.mSettingsLock) {
            try {
                this.mRingerMode = i;
                if (this.mRingerModeExternal == -1) {
                    this.mRingerModeExternal = this.mRingerMode;
                }
                if (this.mHasVibrator) {
                    i2 = 2;
                } else {
                    i2 = 0;
                }
                this.mVibrateSetting = android.media.AudioSystem.getValueForVibrateSetting(0, 1, i2);
                int i4 = this.mVibrateSetting;
                if (!this.mHasVibrator) {
                    i3 = 0;
                }
                this.mVibrateSetting = android.media.AudioSystem.getValueForVibrateSetting(i4, 0, i3);
                updateRingerAndZenModeAffectedStreams();
                readDockAudioSettings(contentResolver);
                sendEncodedSurroundMode(contentResolver, "readPersistedSettings");
                sendEnabledSurroundFormats(contentResolver, true);
                updateAssistantUIdLocked(true);
                resetActiveAssistantUidsLocked();
                android.media.AudioSystem.setRttEnabled(this.mRttEnabled);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mMuteAffectedStreams = this.mSettings.getSystemIntForUser(contentResolver, "mute_streams_affected", 111, -2);
        updateMasterMono(contentResolver);
        updateMasterBalance(contentResolver);
        broadcastRingerMode("android.media.RINGER_MODE_CHANGED", this.mRingerModeExternal);
        broadcastRingerMode("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", this.mRingerMode);
        broadcastVibrateSetting(0);
        broadcastVibrateSetting(1);
        this.mVolumeController.loadSettings(contentResolver);
    }

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private void resetActiveAssistantUidsLocked() {
        this.mActiveAssistantServiceUids = NO_ACTIVE_ASSISTANT_SERVICE_UIDS;
        updateActiveAssistantServiceUids();
    }

    private void readUserRestrictions() {
        if (!this.mSystemServer.isPrivileged()) {
            return;
        }
        int currentUserId = getCurrentUserId();
        if (this.mUseFixedVolume) {
            android.media.AudioSystem.setMasterVolume(1.0f);
        }
        setMasterMuteInternalNoCallerCheck(this.mUserManagerInternal.getUserRestriction(currentUserId, "disallow_unmute_device") || this.mUserManagerInternal.getUserRestriction(currentUserId, "no_adjust_volume"), 0, currentUserId, "readUserRestrictions");
        this.mMicMuteFromRestrictions = this.mUserManagerInternal.getUserRestriction(currentUserId, "no_unmute_microphone");
        setMicrophoneMuteNoCallerCheck(currentUserId);
    }

    private int getIndexRange(int i) {
        return this.mStreamStates[i].getMaxIndex() - this.mStreamStates[i].getMinIndex();
    }

    private int rescaleIndex(android.media.VolumeInfo volumeInfo, int i) {
        if (volumeInfo.getVolumeIndex() == -100 || volumeInfo.getMinVolumeIndex() == -100 || volumeInfo.getMaxVolumeIndex() == -100) {
            android.util.Log.e(TAG, "rescaleIndex: volumeInfo has invalid index or range");
            return this.mStreamStates[i].getMinIndex();
        }
        return rescaleIndex(volumeInfo.getVolumeIndex(), volumeInfo.getMinVolumeIndex(), volumeInfo.getMaxVolumeIndex(), this.mStreamStates[i].getMinIndex(), this.mStreamStates[i].getMaxIndex());
    }

    private int rescaleIndex(int i, int i2, android.media.VolumeInfo volumeInfo) {
        int minVolumeIndex = volumeInfo.getMinVolumeIndex();
        int maxVolumeIndex = volumeInfo.getMaxVolumeIndex();
        if (minVolumeIndex == -100 || maxVolumeIndex == -100) {
            return i;
        }
        return rescaleIndex(i, this.mStreamStates[i2].getMinIndex(), this.mStreamStates[i2].getMaxIndex(), minVolumeIndex, maxVolumeIndex);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int rescaleIndex(int i, int i2, int i3) {
        return rescaleIndex(i, this.mStreamStates[i2].getMinIndex(), this.mStreamStates[i2].getMaxIndex(), this.mStreamStates[i3].getMinIndex(), this.mStreamStates[i3].getMaxIndex());
    }

    private int rescaleIndex(int i, int i2, int i3, int i4, int i5) {
        int i6 = i3 - i2;
        int i7 = i5 - i4;
        if (i6 == 0) {
            android.util.Log.e(TAG, "rescaleIndex : index range should not be zero");
            return i4;
        }
        return i4 + ((((i - i2) * i7) + (i6 / 2)) / i6);
    }

    private int rescaleStep(int i, int i2, int i3) {
        int indexRange = getIndexRange(i2);
        int indexRange2 = getIndexRange(i3);
        if (indexRange == 0) {
            android.util.Log.e(TAG, "rescaleStep : index range should not be zero");
            return 0;
        }
        return ((i * indexRange2) + (indexRange / 2)) / indexRange;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public int setPreferredDevicesForStrategy(int i, java.util.List<android.media.AudioDeviceAttributes> list) {
        super.setPreferredDevicesForStrategy_enforcePermission();
        if (list == null) {
            return -1;
        }
        java.util.List<android.media.AudioDeviceAttributes> retrieveBluetoothAddresses = retrieveBluetoothAddresses(list);
        java.lang.String format = java.lang.String.format("setPreferredDevicesForStrategy u/pid:%d/%d strat:%d dev:%s", java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), java.lang.Integer.valueOf(android.os.Binder.getCallingPid()), java.lang.Integer.valueOf(i), retrieveBluetoothAddresses.stream().map(new java.util.function.Function() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String audioDeviceAttributes;
                audioDeviceAttributes = ((android.media.AudioDeviceAttributes) obj).toString();
                return audioDeviceAttributes;
            }
        }).collect(java.util.stream.Collectors.joining(",")));
        sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(format).printLog(TAG));
        if (retrieveBluetoothAddresses.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$setPreferredDevicesForStrategy$7;
                lambda$setPreferredDevicesForStrategy$7 = com.android.server.audio.AudioService.lambda$setPreferredDevicesForStrategy$7((android.media.AudioDeviceAttributes) obj);
                return lambda$setPreferredDevicesForStrategy$7;
            }
        })) {
            android.util.Log.e(TAG, "Unsupported input routing in " + format);
            return -1;
        }
        int preferredDevicesForStrategySync = this.mDeviceBroker.setPreferredDevicesForStrategySync(i, retrieveBluetoothAddresses);
        if (preferredDevicesForStrategySync != 0) {
            android.util.Log.e(TAG, java.lang.String.format("Error %d in %s)", java.lang.Integer.valueOf(preferredDevicesForStrategySync), format));
        }
        return preferredDevicesForStrategySync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setPreferredDevicesForStrategy$7(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        return audioDeviceAttributes.getRole() == 1;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public int removePreferredDevicesForStrategy(int i) {
        super.removePreferredDevicesForStrategy_enforcePermission();
        java.lang.String format = java.lang.String.format("removePreferredDevicesForStrategy strat:%d", java.lang.Integer.valueOf(i));
        sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(format).printLog(TAG));
        int removePreferredDevicesForStrategySync = this.mDeviceBroker.removePreferredDevicesForStrategySync(i);
        if (removePreferredDevicesForStrategySync != 0 && removePreferredDevicesForStrategySync != -2) {
            android.util.Log.e(TAG, java.lang.String.format("Error %d in %s)", java.lang.Integer.valueOf(removePreferredDevicesForStrategySync), format));
        }
        return removePreferredDevicesForStrategySync;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForStrategy(int i) {
        super.getPreferredDevicesForStrategy_enforcePermission();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int devicesForRoleAndStrategy = android.media.AudioSystem.getDevicesForRoleAndStrategy(i, 1, arrayList);
            if (devicesForRoleAndStrategy != 0) {
                android.util.Log.e(TAG, java.lang.String.format("Error %d in getPreferredDeviceForStrategy(%d)", java.lang.Integer.valueOf(devicesForRoleAndStrategy), java.lang.Integer.valueOf(i)));
                return new java.util.ArrayList();
            }
            return anonymizeAudioDeviceAttributesList(arrayList);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public int setDeviceAsNonDefaultForStrategy(int i, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.setDeviceAsNonDefaultForStrategy_enforcePermission();
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        java.lang.String format = java.lang.String.format("setDeviceAsNonDefaultForStrategy u/pid:%d/%d strat:%d dev:%s", java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), java.lang.Integer.valueOf(android.os.Binder.getCallingPid()), java.lang.Integer.valueOf(i), retrieveBluetoothAddress.toString());
        sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(format).printLog(TAG));
        if (retrieveBluetoothAddress.getRole() == 1) {
            android.util.Log.e(TAG, "Unsupported input routing in " + format);
            return -1;
        }
        int deviceAsNonDefaultForStrategySync = this.mDeviceBroker.setDeviceAsNonDefaultForStrategySync(i, retrieveBluetoothAddress);
        if (deviceAsNonDefaultForStrategySync != 0) {
            android.util.Log.e(TAG, java.lang.String.format("Error %d in %s)", java.lang.Integer.valueOf(deviceAsNonDefaultForStrategySync), format));
        }
        return deviceAsNonDefaultForStrategySync;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public int removeDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.removeDeviceAsNonDefaultForStrategy_enforcePermission();
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        java.lang.String format = java.lang.String.format("removeDeviceAsNonDefaultForStrategy strat:%d dev:%s", java.lang.Integer.valueOf(i), retrieveBluetoothAddress.toString());
        sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(format).printLog(TAG));
        if (retrieveBluetoothAddress.getRole() == 1) {
            android.util.Log.e(TAG, "Unsupported input routing in " + format);
            return -1;
        }
        int removeDeviceAsNonDefaultForStrategySync = this.mDeviceBroker.removeDeviceAsNonDefaultForStrategySync(i, retrieveBluetoothAddress);
        if (removeDeviceAsNonDefaultForStrategySync != 0 && removeDeviceAsNonDefaultForStrategySync != -2) {
            android.util.Log.e(TAG, java.lang.String.format("Error %d in %s)", java.lang.Integer.valueOf(removeDeviceAsNonDefaultForStrategySync), format));
        }
        return removeDeviceAsNonDefaultForStrategySync;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public java.util.List<android.media.AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int i) {
        super.getNonDefaultDevicesForStrategy_enforcePermission();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            int devicesForRoleAndStrategy = android.media.AudioSystem.getDevicesForRoleAndStrategy(i, 2, arrayList);
            if (create != null) {
                create.close();
            }
            if (devicesForRoleAndStrategy != 0) {
                android.util.Log.e(TAG, java.lang.String.format("Error %d in getNonDefaultDeviceForStrategy(%d)", java.lang.Integer.valueOf(devicesForRoleAndStrategy), java.lang.Integer.valueOf(i)));
                return new java.util.ArrayList();
            }
            return anonymizeAudioDeviceAttributesList(arrayList);
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void registerStrategyPreferredDevicesDispatcher(@android.annotation.Nullable android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        if (iStrategyPreferredDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.registerStrategyPreferredDevicesDispatcher(iStrategyPreferredDevicesDispatcher, isBluetoothPrividged());
    }

    public void unregisterStrategyPreferredDevicesDispatcher(@android.annotation.Nullable android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        if (iStrategyPreferredDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.unregisterStrategyPreferredDevicesDispatcher(iStrategyPreferredDevicesDispatcher);
    }

    public void registerStrategyNonDefaultDevicesDispatcher(@android.annotation.Nullable android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        if (iStrategyNonDefaultDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.registerStrategyNonDefaultDevicesDispatcher(iStrategyNonDefaultDevicesDispatcher, isBluetoothPrividged());
    }

    public void unregisterStrategyNonDefaultDevicesDispatcher(@android.annotation.Nullable android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        if (iStrategyNonDefaultDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.unregisterStrategyNonDefaultDevicesDispatcher(iStrategyNonDefaultDevicesDispatcher);
    }

    public int setPreferredDevicesForCapturePreset(int i, java.util.List<android.media.AudioDeviceAttributes> list) {
        if (list == null) {
            return -1;
        }
        enforceModifyAudioRoutingPermission();
        java.lang.String format = java.lang.String.format("setPreferredDevicesForCapturePreset u/pid:%d/%d source:%d dev:%s", java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), java.lang.Integer.valueOf(android.os.Binder.getCallingPid()), java.lang.Integer.valueOf(i), list.stream().map(new java.util.function.Function() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String audioDeviceAttributes;
                audioDeviceAttributes = ((android.media.AudioDeviceAttributes) obj).toString();
                return audioDeviceAttributes;
            }
        }).collect(java.util.stream.Collectors.joining(",")));
        sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(format).printLog(TAG));
        if (list.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$setPreferredDevicesForCapturePreset$9;
                lambda$setPreferredDevicesForCapturePreset$9 = com.android.server.audio.AudioService.lambda$setPreferredDevicesForCapturePreset$9((android.media.AudioDeviceAttributes) obj);
                return lambda$setPreferredDevicesForCapturePreset$9;
            }
        })) {
            android.util.Log.e(TAG, "Unsupported output routing in " + format);
            return -1;
        }
        int preferredDevicesForCapturePresetSync = this.mDeviceBroker.setPreferredDevicesForCapturePresetSync(i, retrieveBluetoothAddresses(list));
        if (preferredDevicesForCapturePresetSync != 0) {
            android.util.Log.e(TAG, java.lang.String.format("Error %d in %s)", java.lang.Integer.valueOf(preferredDevicesForCapturePresetSync), format));
        }
        return preferredDevicesForCapturePresetSync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setPreferredDevicesForCapturePreset$9(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        return audioDeviceAttributes.getRole() == 2;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public int clearPreferredDevicesForCapturePreset(int i) {
        super.clearPreferredDevicesForCapturePreset_enforcePermission();
        java.lang.String format = java.lang.String.format("removePreferredDeviceForCapturePreset source:%d", java.lang.Integer.valueOf(i));
        sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(format).printLog(TAG));
        int clearPreferredDevicesForCapturePresetSync = this.mDeviceBroker.clearPreferredDevicesForCapturePresetSync(i);
        if (clearPreferredDevicesForCapturePresetSync != 0 && clearPreferredDevicesForCapturePresetSync != -2) {
            android.util.Log.e(TAG, java.lang.String.format("Error %d in %s", java.lang.Integer.valueOf(clearPreferredDevicesForCapturePresetSync), format));
        }
        return clearPreferredDevicesForCapturePresetSync;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) {
        super.getPreferredDevicesForCapturePreset_enforcePermission();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int devicesForRoleAndCapturePreset = android.media.AudioSystem.getDevicesForRoleAndCapturePreset(i, 1, arrayList);
            if (devicesForRoleAndCapturePreset != 0) {
                android.util.Log.e(TAG, java.lang.String.format("Error %d in getPreferredDeviceForCapturePreset(%d)", java.lang.Integer.valueOf(devicesForRoleAndCapturePreset), java.lang.Integer.valueOf(i)));
                return new java.util.ArrayList();
            }
            return anonymizeAudioDeviceAttributesList(arrayList);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerCapturePresetDevicesRoleDispatcher(@android.annotation.Nullable android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        if (iCapturePresetDevicesRoleDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.registerCapturePresetDevicesRoleDispatcher(iCapturePresetDevicesRoleDispatcher, isBluetoothPrividged());
    }

    public void unregisterCapturePresetDevicesRoleDispatcher(@android.annotation.Nullable android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        if (iCapturePresetDevicesRoleDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.unregisterCapturePresetDevicesRoleDispatcher(iCapturePresetDevicesRoleDispatcher);
    }

    @android.annotation.NonNull
    /* renamed from: getDevicesForAttributes, reason: merged with bridge method [inline-methods] */
    public java.util.ArrayList<android.media.AudioDeviceAttributes> m1928getDevicesForAttributes(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        enforceQueryStateOrModifyRoutingPermission();
        return new java.util.ArrayList<>(anonymizeAudioDeviceAttributesList(getDevicesForAttributesInt(audioAttributes, false)));
    }

    @android.annotation.NonNull
    /* renamed from: getDevicesForAttributesUnprotected, reason: merged with bridge method [inline-methods] */
    public java.util.ArrayList<android.media.AudioDeviceAttributes> m1929getDevicesForAttributesUnprotected(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        return new java.util.ArrayList<>(anonymizeAudioDeviceAttributesList(getDevicesForAttributesInt(audioAttributes, false)));
    }

    public boolean isMusicActive(boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return z ? android.media.AudioSystem.isStreamActiveRemotely(3, 0) : android.media.AudioSystem.isStreamActive(3, 0);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.NonNull
    protected java.util.ArrayList<android.media.AudioDeviceAttributes> getDevicesForAttributesInt(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, boolean z) {
        java.util.Objects.requireNonNull(audioAttributes);
        return this.mAudioSystem.getDevicesForAttributes(audioAttributes, z);
    }

    public void addOnDevicesForAttributesChangedListener(android.media.AudioAttributes audioAttributes, android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        this.mAudioSystem.addOnDevicesForAttributesChangedListener(audioAttributes, false, iDevicesForAttributesCallback);
    }

    public void removeOnDevicesForAttributesChangedListener(android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        this.mAudioSystem.removeOnDevicesForAttributesChangedListener(iDevicesForAttributesCallback);
    }

    public void handleVolumeKey(@android.annotation.NonNull android.view.KeyEvent keyEvent, boolean z, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        int i;
        if (z) {
            if (keyEvent.getAction() == 0) {
                i = 1;
            } else {
                i = 2;
            }
        } else if (keyEvent.getAction() == 0) {
            i = 0;
        } else {
            return;
        }
        switch (keyEvent.getKeyCode()) {
            case 24:
                adjustSuggestedStreamVolume(1, Integer.MIN_VALUE, 4101, str, str2, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), true, i);
                break;
            case 25:
                adjustSuggestedStreamVolume(-1, Integer.MIN_VALUE, 4101, str, str2, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), true, i);
                break;
            case 164:
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    adjustSuggestedStreamVolume(101, Integer.MIN_VALUE, 4101, str, str2, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), true, 0);
                    break;
                }
                break;
            default:
                android.util.Log.e(TAG, "Invalid key code " + keyEvent.getKeyCode() + " sent by " + str);
                break;
        }
    }

    public void setNavigationRepeatSoundEffectsEnabled(boolean z) {
        this.mNavigationRepeatSoundEffectsEnabled = z;
    }

    public boolean areNavigationRepeatSoundEffectsEnabled() {
        return this.mNavigationRepeatSoundEffectsEnabled;
    }

    public void setHomeSoundEffectEnabled(boolean z) {
        this.mHomeSoundEffectEnabled = z;
    }

    public boolean isHomeSoundEffectEnabled() {
        return this.mHomeSoundEffectEnabled;
    }

    private void adjustSuggestedStreamVolume(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5, boolean z, int i6) {
        int activeStreamType;
        boolean wasStreamActiveRecently;
        int i7;
        int i8;
        int i9;
        if (i != 0) {
            sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(0, i2, i, i3, str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str2 + " uid:" + i4));
        }
        boolean notifyExternalVolumeController = notifyExternalVolumeController(i);
        new android.media.MediaMetrics.Item("audio.service.adjustSuggestedStreamVolume").setUid(android.os.Binder.getCallingUid()).set(android.media.MediaMetrics.Property.CALLING_PACKAGE, str).set(android.media.MediaMetrics.Property.CLIENT_NAME, str2).set(android.media.MediaMetrics.Property.DIRECTION, i > 0 ? android.net.INetd.IF_STATE_UP : android.net.INetd.IF_STATE_DOWN).set(android.media.MediaMetrics.Property.EXTERNAL, notifyExternalVolumeController ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO).set(android.media.MediaMetrics.Property.FLAGS, java.lang.Integer.valueOf(i3)).record();
        if (notifyExternalVolumeController) {
            return;
        }
        synchronized (this.mForceControlStreamLock) {
            try {
                if (this.mUserSelectedVolumeControlStream) {
                    activeStreamType = this.mVolumeControlStream;
                } else {
                    activeStreamType = getActiveStreamType(i2);
                    if (activeStreamType == 2 || activeStreamType == 5) {
                        wasStreamActiveRecently = wasStreamActiveRecently(activeStreamType, 0);
                    } else {
                        wasStreamActiveRecently = this.mAudioSystem.isStreamActive(activeStreamType, 0);
                    }
                    if (!wasStreamActiveRecently && this.mVolumeControlStream != -1) {
                        activeStreamType = this.mVolumeControlStream;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        boolean isMuteAdjust = isMuteAdjust(i);
        ensureValidStreamType(activeStreamType);
        int i10 = mStreamVolumeAlias[activeStreamType];
        if ((i3 & 4) != 0 && i10 != 2) {
            i7 = i3 & (-5);
        } else {
            i7 = i3;
        }
        if (this.mVolumeController.suppressAdjustment(i10, i7, isMuteAdjust) && !this.mIsSingleVolume) {
            int i11 = i7 & (-5) & (-17);
            i9 = 0;
            i8 = i11;
        } else {
            i8 = i7;
            i9 = i;
        }
        adjustStreamVolume(activeStreamType, i9, i8, str, str2, i4, i5, null, z, i6);
    }

    private boolean notifyExternalVolumeController(int i) {
        android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback;
        synchronized (this.mExtVolumeControllerLock) {
            iAudioPolicyCallback = this.mExtVolumeController;
        }
        if (iAudioPolicyCallback == null) {
            return false;
        }
        sendMsg(this.mAudioHandler, 22, 2, i, 0, iAudioPolicyCallback, 0);
        return true;
    }

    public void adjustStreamVolume(int i, int i2, int i3, java.lang.String str) {
        adjustStreamVolumeWithAttribution(i, i2, i3, str, null);
    }

    public void adjustStreamVolumeWithAttribution(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        if (this.mHardeningEnforcer.blockVolumeMethod(103)) {
            return;
        }
        if (i == 10 && !canChangeAccessibilityVolume()) {
            android.util.Log.w(TAG, "Trying to call adjustStreamVolume() for a11y withoutCHANGE_ACCESSIBILITY_VOLUME / callingPackage=" + str);
            return;
        }
        com.android.server.audio.AudioServiceEvents.VolumeEvent volumeEvent = new com.android.server.audio.AudioServiceEvents.VolumeEvent(1, i, i2, i3, str);
        sVolumeLogger.enqueue(volumeEvent);
        if (isMuteAdjust(i2)) {
            sMuteLogger.enqueue(volumeEvent);
        }
        adjustStreamVolume(i, i2, i3, str, str, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), str2, callingHasAudioSettingsPermission(), 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11, types: [int] */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    protected void adjustStreamVolume(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5, java.lang.String str3, boolean z, int i6) {
        int i7;
        int rescaleStep;
        int i8;
        int i9;
        int i10;
        boolean z2;
        ?? r7;
        long clearCallingIdentity;
        boolean z3;
        int i11 = i4;
        if (this.mUseFixedVolume) {
            return;
        }
        ensureValidDirection(i2);
        ensureValidStreamType(i);
        boolean isMuteAdjust = isMuteAdjust(i2);
        if (isMuteAdjust && !isStreamAffectedByMute(i)) {
            return;
        }
        if (isMuteAdjust && ((i == 0 || i == 6) && this.mContext.checkPermission("android.permission.MODIFY_PHONE_STATE", i5, i11) != 0)) {
            android.util.Log.w(TAG, "MODIFY_PHONE_STATE Permission Denial: adjustStreamVolume from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid());
            return;
        }
        if (i == 11 && this.mContext.checkPermission("android.permission.MODIFY_AUDIO_ROUTING", i5, i11) != 0) {
            android.util.Log.w(TAG, "MODIFY_AUDIO_ROUTING Permission Denial: adjustStreamVolume from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid());
            return;
        }
        int i12 = mStreamVolumeAlias[i];
        com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i12];
        int deviceForStream = getDeviceForStream(i12);
        int index = volumeStreamState.getIndex(deviceForStream);
        if (!android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(java.lang.Integer.valueOf(deviceForStream)) && !android.media.AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(java.lang.Integer.valueOf(deviceForStream)) && (i3 & 64) != 0) {
            return;
        }
        if (i11 == 1000) {
            i11 = android.os.UserHandle.getUid(getCurrentUserId(), android.os.UserHandle.getAppId(i4));
        }
        if (!checkNoteAppOp(STREAM_VOLUME_OPS[i12], i11, str, str3)) {
            return;
        }
        this.mSoundDoseHelper.invalidatePendingVolumeCommand();
        int i13 = i3 & (-33);
        if (i12 != 3 || !isFixedVolumeDevice(deviceForStream)) {
            i7 = index;
            rescaleStep = rescaleStep(10, i, i12);
        } else {
            i13 |= 32;
            int safeMediaVolumeIndex = this.mSoundDoseHelper.getSafeMediaVolumeIndex(deviceForStream);
            if (safeMediaVolumeIndex < 0) {
                safeMediaVolumeIndex = volumeStreamState.getMaxIndex();
            }
            if (index == 0) {
                i7 = index;
                rescaleStep = safeMediaVolumeIndex;
            } else {
                i7 = safeMediaVolumeIndex;
                rescaleStep = i7;
            }
        }
        boolean z4 = false;
        if ((i13 & 2) != 0 || isUiSoundsStreamType(i12)) {
            if (getRingerModeInternal() != 1) {
                i8 = i13;
            } else {
                i8 = i13 & (-17);
            }
            int i14 = i8;
            i9 = 101;
            int checkForRingerModeChange = checkForRingerModeChange(i7, i2, rescaleStep, volumeStreamState.mIsMuted, str, i14);
            z4 = (checkForRingerModeChange & 1) != 0;
            if ((checkForRingerModeChange & 128) == 0) {
                i10 = i14;
            } else {
                i10 = i14 | 128;
            }
            if ((checkForRingerModeChange & 2048) != 0) {
                i10 |= 2048;
            }
            i13 = i10;
        } else if (isStreamMutedByRingerOrZenMode(i12) && volumeStreamState.mIsMuted && (i2 == 101 || i2 == 100 || i2 == 1)) {
            i9 = 101;
        } else {
            z4 = true;
            i9 = 101;
        }
        if (!volumeAdjustmentAllowedByDnd(i12, i13)) {
            z4 = false;
        }
        int index2 = this.mStreamStates[i].getIndex(deviceForStream);
        if (isAbsoluteVolumeDevice(deviceForStream) && (i13 & 8192) == 0) {
            com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo = this.mAbsoluteVolumeDeviceInfoMap.get(java.lang.Integer.valueOf(deviceForStream));
            if (absoluteVolumeDeviceInfo.mHandlesVolumeAdjustment) {
                dispatchAbsoluteVolumeAdjusted(i, absoluteVolumeDeviceInfo, index2, i2, i6);
                return;
            }
        }
        if (!z4 || i2 == 0 || i6 == 2) {
            z2 = false;
        } else {
            this.mAudioHandler.removeMessages(18);
            if (isMuteAdjust && !this.mFullVolumeDevices.contains(java.lang.Integer.valueOf(deviceForStream))) {
                if (i2 == i9) {
                    z3 = !volumeStreamState.mIsMuted;
                } else {
                    z3 = i2 == -100;
                }
                muteAliasStreams(i12, z3);
                z2 = false;
            } else if (i2 == 1 && this.mSoundDoseHelper.raiseVolumeDisplaySafeMediaVolume(i12, i7 + rescaleStep, deviceForStream, i13)) {
                android.util.Log.e(TAG, "adjustStreamVolume() safe volume index = " + index2);
                z2 = false;
            } else if (isFullVolumeDevice(deviceForStream)) {
                z2 = false;
            } else if (volumeStreamState.adjustIndex(i2 * rescaleStep, deviceForStream, str2, z) || volumeStreamState.mIsMuted) {
                if (!volumeStreamState.mIsMuted) {
                    z2 = false;
                } else if (i2 == 1) {
                    z2 = false;
                    muteAliasStreams(i12, false);
                } else {
                    z2 = false;
                    if (i2 == -1 && this.mIsSingleVolume) {
                        sendMsg(this.mAudioHandler, 18, 2, i12, i13, null, 350);
                    }
                }
                sendMsg(this.mAudioHandler, 0, 2, deviceForStream, 0, volumeStreamState, 0);
            } else {
                z2 = false;
            }
            int index3 = this.mStreamStates[i].getIndex(deviceForStream);
            if (i12 == 3 && android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(java.lang.Integer.valueOf(deviceForStream)) && (i13 & 64) == 0) {
                this.mDeviceBroker.postSetAvrcpAbsoluteVolumeIndex(index3 / 10);
            } else if (isAbsoluteVolumeDevice(deviceForStream) && (i13 & 8192) == 0) {
                dispatchAbsoluteVolumeChanged(i, this.mAbsoluteVolumeDeviceInfoMap.get(java.lang.Integer.valueOf(deviceForStream)), index3);
            }
            if (android.media.AudioSystem.isLeAudioDeviceType(deviceForStream) && i == getBluetoothContextualVolumeStream() && (i13 & 64) == 0) {
                this.mDeviceBroker.postSetLeAudioVolumeIndex(index3, this.mStreamStates[i].getMaxIndex(), i);
            }
            if (deviceForStream == 134217728 && i == getBluetoothContextualVolumeStream()) {
                this.mDeviceBroker.postSetHearingAidVolumeIndex(index3, i);
            }
        }
        int index4 = this.mStreamStates[i].getIndex(deviceForStream);
        if (z4) {
            synchronized (this.mHdmiClientLock) {
                try {
                    if (this.mHdmiManager != null) {
                        android.hardware.hdmi.HdmiTvClient hdmiTvClient = this.mHdmiPlaybackClient;
                        if (this.mHdmiTvClient != null) {
                            hdmiTvClient = this.mHdmiTvClient;
                        }
                        boolean z5 = (this.mHdmiPlaybackClient == null || !isFullVolumeDevice(deviceForStream)) ? z2 : true;
                        boolean z6 = (this.mHdmiTvClient == null || !this.mHdmiSystemAudioSupported || isAbsoluteVolumeDevice(deviceForStream) || isA2dpAbsoluteVolumeDevice(deviceForStream)) ? z2 : true;
                        if ((z5 || z6) && this.mHdmiCecVolumeControlEnabled && i12 == 3) {
                            switch (i2) {
                                case -100:
                                case 100:
                                case 101:
                                    r7 = 164;
                                    break;
                                case -1:
                                    r7 = 25;
                                    break;
                                case 1:
                                    r7 = 24;
                                    break;
                                default:
                                    r7 = z2;
                                    break;
                            }
                            if (r7 != 0) {
                                clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                                switch (i6) {
                                    case 0:
                                        hdmiTvClient.sendVolumeKeyEvent((int) r7, true);
                                        hdmiTvClient.sendVolumeKeyEvent((int) r7, z2);
                                        break;
                                    case 1:
                                        hdmiTvClient.sendVolumeKeyEvent((int) r7, true);
                                        break;
                                    case 2:
                                        hdmiTvClient.sendVolumeKeyEvent((int) r7, z2);
                                        break;
                                    default:
                                        android.util.Log.e(TAG, "Invalid keyEventMode " + i6);
                                        break;
                                }
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                        if (i12 == 3 && (index2 != index4 || isMuteAdjust)) {
                            maybeSendSystemAudioStatusCommand(isMuteAdjust);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                } finally {
                }
            }
        }
        sendVolumeUpdate(i, index2, index4, i13, deviceForStream);
    }

    private void muteAliasStreams(int i, final boolean z) {
        synchronized (this.mSettingsLock) {
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                try {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (int i2 = 0; i2 < this.mStreamStates.length; i2++) {
                        com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i2];
                        if (i == mStreamVolumeAlias[i2]) {
                            if (volumeStreamState.isMutable()) {
                                if (this.mCameraSoundForced && volumeStreamState.getStreamType() == 7) {
                                }
                                if (volumeStreamState.mute(z, false, "muteAliasStreams")) {
                                    arrayList.add(java.lang.Integer.valueOf(i2));
                                }
                            }
                        }
                    }
                    arrayList.forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda13
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.audio.AudioService.this.lambda$muteAliasStreams$10(z, (java.lang.Integer) obj);
                        }
                    });
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$muteAliasStreams$10(boolean z, java.lang.Integer num) {
        this.mStreamStates[num.intValue()].doMute();
        broadcastMuteSetting(num.intValue(), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastMuteSetting(int i, boolean z) {
        android.content.Intent intent = new android.content.Intent("android.media.STREAM_MUTE_CHANGED_ACTION");
        intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", i);
        intent.putExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", z);
        sendBroadcastToAll(intent, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x003b, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0042, code lost:
    
        throw r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onUnmuteStreamOnSingleVolDevice(int i, int i2) {
        boolean mute;
        synchronized (this.mSettingsLock) {
            try {
                synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                    com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i];
                    mute = volumeStreamState.mute(false, "onUnmuteStreamOnSingleVolDevice");
                    if (mute) {
                        muteAliasStreams(i, false);
                    }
                    int deviceForStream = getDeviceForStream(i);
                    int index = volumeStreamState.getIndex(deviceForStream);
                    sendVolumeUpdate(i, index, index, i2, deviceForStream);
                }
                if (i == 3 && mute) {
                    synchronized (this.mHdmiClientLock) {
                        maybeSendSystemAudioStatusCommand(true);
                    }
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mHdmiClientLock"})
    private void maybeSendSystemAudioStatusCommand(boolean z) {
        if (this.mHdmiAudioSystemClient == null || !this.mHdmiSystemAudioSupported || !this.mHdmiCecVolumeControlEnabled) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mHdmiAudioSystemClient.sendReportAudioStatusCecCommand(z, getStreamVolume(3), getStreamMaxVolume(3), isStreamMute(3));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private int getNewRingerMode(int i, int i2, int i3) {
        if (this.mIsSingleVolume) {
            return getRingerModeExternal();
        }
        if ((i3 & 2) != 0 || i == getUiSoundsStreamType()) {
            if (i2 != 0) {
                return 2;
            }
            if (this.mHasVibrator) {
                return 1;
            }
            return this.mVolumePolicy.volumeDownToEnterSilent ? 0 : 2;
        }
        return getRingerModeExternal();
    }

    private boolean isAndroidNPlus(java.lang.String str) {
        try {
            if (this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, android.os.UserHandle.getUserId(android.os.Binder.getCallingUid())).targetSdkVersion >= 24) {
                return true;
            }
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    private boolean wouldToggleZenMode(int i) {
        if (getRingerModeExternal() != 0 || i == 0) {
            return getRingerModeExternal() != 0 && i == 0;
        }
        return true;
    }

    void onSetStreamVolume(int i, int i2, int i3, int i4, java.lang.String str, boolean z, boolean z2) {
        int i5 = mStreamVolumeAlias[i];
        if ((i3 & 2) != 0 || i5 == getUiSoundsStreamType()) {
            setRingerMode(getNewRingerMode(i5, i2, i3), "AS.AudioService.onSetStreamVolume", false);
        }
        setStreamVolumeInt(i5, i2, i4, false, str, z);
        if (i != 6 && z2) {
            muteAliasStreams(i5, i2 == 0);
        }
    }

    private void enforceModifyAudioRoutingPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            throw new java.lang.SecurityException("Missing MODIFY_AUDIO_ROUTING permission");
        }
    }

    private void enforceAccessUltrasoundPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.ACCESS_ULTRASOUND") != 0) {
            throw new java.lang.SecurityException("Missing ACCESS_ULTRASOUND permission");
        }
    }

    private void enforceQueryStatePermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.QUERY_AUDIO_STATE") != 0) {
            throw new java.lang.SecurityException("Missing QUERY_AUDIO_STATE permissions");
        }
    }

    private void enforceQueryStateOrModifyRoutingPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.QUERY_AUDIO_STATE") != 0) {
            throw new java.lang.SecurityException("Missing MODIFY_AUDIO_ROUTING or QUERY_AUDIO_STATE permissions");
        }
    }

    private void enforceCallAudioInterceptionPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.CALL_AUDIO_INTERCEPTION") != 0) {
            throw new java.lang.SecurityException("Missing CALL_AUDIO_INTERCEPTION permission");
        }
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED", "android.permission.MODIFY_AUDIO_ROUTING"})
    public void setVolumeGroupVolumeIndex(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        super.setVolumeGroupVolumeIndex_enforcePermission();
        if (sVolumeGroupStates.indexOfKey(i) >= 0) {
            com.android.server.audio.AudioService.VolumeGroupState volumeGroupState = sVolumeGroupStates.get(i);
            sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(8, volumeGroupState.name(), i2, i3, str + ", user " + getCurrentUserId()));
            volumeGroupState.setVolumeIndex(i2, i3);
            int[] legacyStreamTypes = volumeGroupState.getLegacyStreamTypes();
            int length = legacyStreamTypes.length;
            for (int i4 = 0; i4 < length; i4++) {
                int i5 = legacyStreamTypes[i4];
                try {
                    ensureValidStreamType(i5);
                    setStreamVolume(i5, i2, i3, null, str, str, str2, android.os.Binder.getCallingUid(), true, true);
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Log.d(TAG, "volume group " + i + " has internal streams (" + i5 + "), do not change associated stream volume");
                }
            }
            return;
        }
        android.util.Log.e(TAG, ": no volume group found for id " + i);
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED", "android.permission.MODIFY_AUDIO_ROUTING"})
    public int getVolumeGroupVolumeIndex(int i) {
        int volumeIndex;
        super.getVolumeGroupVolumeIndex_enforcePermission();
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            try {
                if (sVolumeGroupStates.indexOfKey(i) < 0) {
                    throw new java.lang.IllegalArgumentException("No volume group for id " + i);
                }
                com.android.server.audio.AudioService.VolumeGroupState volumeGroupState = sVolumeGroupStates.get(i);
                volumeIndex = volumeGroupState.isMuted() ? 0 : volumeGroupState.getVolumeIndex();
            } finally {
            }
        }
        return volumeIndex;
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED", "android.permission.MODIFY_AUDIO_ROUTING"})
    public int getVolumeGroupMaxVolumeIndex(int i) {
        int maxIndex;
        super.getVolumeGroupMaxVolumeIndex_enforcePermission();
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            try {
                if (sVolumeGroupStates.indexOfKey(i) < 0) {
                    throw new java.lang.IllegalArgumentException("No volume group for id " + i);
                }
                maxIndex = sVolumeGroupStates.get(i).getMaxIndex();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return maxIndex;
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED", "android.permission.MODIFY_AUDIO_ROUTING"})
    public int getVolumeGroupMinVolumeIndex(int i) {
        int minIndex;
        super.getVolumeGroupMinVolumeIndex_enforcePermission();
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            try {
                if (sVolumeGroupStates.indexOfKey(i) < 0) {
                    throw new java.lang.IllegalArgumentException("No volume group for id " + i);
                }
                minIndex = sVolumeGroupStates.get(i).getMinIndex();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return minIndex;
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED"})
    public void setDeviceVolume(@android.annotation.NonNull android.media.VolumeInfo volumeInfo, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, @android.annotation.NonNull java.lang.String str) {
        super.setDeviceVolume_enforcePermission();
        java.util.Objects.requireNonNull(volumeInfo);
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        java.util.Objects.requireNonNull(str);
        if (!volumeInfo.hasStreamType()) {
            android.util.Log.e(TAG, "Unsupported non-stream type based VolumeInfo", new java.lang.Exception());
            return;
        }
        int volumeIndex = volumeInfo.getVolumeIndex();
        if (volumeIndex == -100 && !volumeInfo.hasMuteCommand()) {
            throw new java.lang.IllegalArgumentException("changing device volume requires a volume index or mute command");
        }
        this.mAudioSystem.clearRoutingCache();
        int deviceForStream = getDeviceForStream(volumeInfo.getStreamType());
        boolean z = deviceForStream == audioDeviceAttributes.getInternalType();
        sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.DeviceVolumeEvent(volumeInfo.getStreamType(), volumeIndex, audioDeviceAttributes, deviceForStream, str, z));
        if (z) {
            return;
        }
        if (volumeInfo.hasMuteCommand() && volumeInfo.isMuted() && !isStreamMute(volumeInfo.getStreamType())) {
            setStreamVolumeWithAttributionInt(volumeInfo.getStreamType(), this.mStreamStates[volumeInfo.getStreamType()].getMinIndex(), 0, audioDeviceAttributes, str, null, false);
            return;
        }
        sVolumeLogger.enqueueAndLog("setDeviceVolume from:" + str + " " + volumeInfo + " " + audioDeviceAttributes, 0, TAG);
        if (volumeInfo.getMinVolumeIndex() == -100 || volumeInfo.getMaxVolumeIndex() == -100) {
            int i = volumeIndex * 10;
            if (i < this.mStreamStates[volumeInfo.getStreamType()].getMinIndex() || i > this.mStreamStates[volumeInfo.getStreamType()].getMaxIndex()) {
                throw new java.lang.IllegalArgumentException("invalid volume index " + volumeIndex + " not between min/max for stream " + volumeInfo.getStreamType());
            }
        } else {
            int minIndex = (this.mStreamStates[volumeInfo.getStreamType()].getMinIndex() + 5) / 10;
            int maxIndex = (this.mStreamStates[volumeInfo.getStreamType()].getMaxIndex() + 5) / 10;
            if (volumeInfo.getMinVolumeIndex() != minIndex || volumeInfo.getMaxVolumeIndex() != maxIndex) {
                volumeIndex = rescaleIndex(volumeIndex, volumeInfo.getMinVolumeIndex(), volumeInfo.getMaxVolumeIndex(), minIndex, maxIndex);
            }
        }
        setStreamVolumeWithAttributionInt(volumeInfo.getStreamType(), volumeIndex, 0, audioDeviceAttributes, str, null, false);
    }

    public void setStreamVolume(int i, int i2, int i3, java.lang.String str) {
        setStreamVolumeWithAttribution(i, i2, i3, str, null);
    }

    public void adjustVolumeGroupVolume(int i, int i2, int i3, java.lang.String str) {
        ensureValidDirection(i2);
        if (sVolumeGroupStates.indexOfKey(i) < 0) {
            android.util.Log.e(TAG, ": no volume group found for id " + i);
            return;
        }
        com.android.server.audio.AudioService.VolumeGroupState volumeGroupState = sVolumeGroupStates.get(i);
        boolean z = false;
        for (int i4 : volumeGroupState.getLegacyStreamTypes()) {
            try {
                ensureValidStreamType(i4);
                if (volumeGroupState.isVssMuteBijective(i4)) {
                    adjustStreamVolume(i4, i2, i3, str);
                    if (isMuteAdjust(i2)) {
                        return;
                    } else {
                        z = true;
                    }
                } else {
                    continue;
                }
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.d(TAG, "volume group " + i + " has internal streams (" + i4 + "), do not change associated stream volume");
            }
        }
        if (z) {
            return;
        }
        sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(11, volumeGroupState.name(), i2, i3, str));
        volumeGroupState.adjustVolume(i2, i3);
    }

    @android.annotation.EnforcePermission("android.permission.QUERY_AUDIO_STATE")
    public int getLastAudibleVolumeForVolumeGroup(int i) {
        super.getLastAudibleVolumeForVolumeGroup_enforcePermission();
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            try {
                if (sVolumeGroupStates.indexOfKey(i) < 0) {
                    android.util.Log.e(TAG, ": no volume group found for id " + i);
                    return 0;
                }
                return sVolumeGroupStates.get(i).getVolumeIndex();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isVolumeGroupMuted(int i) {
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            try {
                if (sVolumeGroupStates.indexOfKey(i) < 0) {
                    android.util.Log.e(TAG, ": no volume group found for id " + i);
                    return false;
                }
                return sVolumeGroupStates.get(i).isMuted();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setStreamVolumeWithAttribution(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
        if (this.mHardeningEnforcer.blockVolumeMethod(100)) {
            return;
        }
        setStreamVolumeWithAttributionInt(i, i2, i3, null, str, str2, true);
    }

    protected void setStreamVolumeWithAttributionInt(int i, int i2, int i3, @android.annotation.Nullable android.media.AudioDeviceAttributes audioDeviceAttributes, java.lang.String str, java.lang.String str2, boolean z) {
        android.media.AudioDeviceAttributes audioDeviceAttributes2;
        if (i == 10 && !canChangeAccessibilityVolume()) {
            android.util.Log.w(TAG, "Trying to call setStreamVolume() for a11y without CHANGE_ACCESSIBILITY_VOLUME  callingPackage=" + str);
            return;
        }
        if (i == 0 && i2 == 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            android.util.Log.w(TAG, "Trying to call setStreamVolume() for STREAM_VOICE_CALL and index 0 without MODIFY_PHONE_STATE  callingPackage=" + str);
            return;
        }
        if (i == 11 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            android.util.Log.w(TAG, "Trying to call setStreamVolume() for STREAM_ASSISTANT without MODIFY_AUDIO_ROUTING  callingPackage=" + str);
            return;
        }
        if (audioDeviceAttributes != null) {
            audioDeviceAttributes2 = audioDeviceAttributes;
        } else {
            int deviceForStream = getDeviceForStream(i);
            sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(2, i, i2, i3, getStreamVolume(i, deviceForStream), str));
            audioDeviceAttributes2 = new android.media.AudioDeviceAttributes(deviceForStream, "");
        }
        setStreamVolume(i, i2, i3, audioDeviceAttributes2, str, str, str2, android.os.Binder.getCallingUid(), callingOrSelfHasAudioSettingsPermission(), z);
    }

    @android.annotation.EnforcePermission("android.permission.ACCESS_ULTRASOUND")
    public boolean isUltrasoundSupported() {
        super.isUltrasoundSupported_enforcePermission();
        return android.media.AudioSystem.isUltrasoundSupported();
    }

    @android.annotation.EnforcePermission("android.permission.CAPTURE_AUDIO_HOTWORD")
    public boolean isHotwordStreamSupported(boolean z) {
        super.isHotwordStreamSupported_enforcePermission();
        try {
            return this.mAudioPolicy.isHotwordStreamSupported(z);
        } catch (java.lang.IllegalStateException e) {
            android.util.Log.e(TAG, "Suppressing exception calling into AudioPolicy", e);
            return false;
        }
    }

    private boolean canChangeAccessibilityVolume() {
        synchronized (this.mAccessibilityServiceUidsLock) {
            try {
                if (this.mContext.checkCallingOrSelfPermission("android.permission.CHANGE_ACCESSIBILITY_VOLUME") == 0) {
                    return true;
                }
                if (this.mAccessibilityServiceUids != null) {
                    int callingUid = android.os.Binder.getCallingUid();
                    for (int i = 0; i < this.mAccessibilityServiceUids.length; i++) {
                        if (this.mAccessibilityServiceUids[i] == callingUid) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public int getBluetoothContextualVolumeStream() {
        return getBluetoothContextualVolumeStream(this.mMode.get());
    }

    private int getBluetoothContextualVolumeStream(int i) {
        boolean z;
        switch (i) {
            case 2:
            case 3:
                return 0;
            case 4:
            case 5:
            case 6:
                z = false;
                break;
            default:
                z = true;
                break;
        }
        if (z && this.mVoicePlaybackActive.get()) {
            return 0;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPlaybackConfigChange(java.util.List<android.media.AudioPlaybackConfiguration> list) {
        boolean z = false;
        boolean z2 = false;
        for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
            int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
            if (audioPlaybackConfiguration.isActive()) {
                if (usage == 2 || usage == 3) {
                    z = true;
                }
                if (usage == 1 || usage == 14) {
                    z2 = true;
                }
            }
        }
        if (this.mVoicePlaybackActive.getAndSet(z) != z) {
            updateHearingAidVolumeOnVoiceActivityUpdate();
        }
        if (this.mMediaPlaybackActive.getAndSet(z2) != z2 && z2) {
            this.mSoundDoseHelper.scheduleMusicActiveCheck();
        }
        this.mLoudnessCodecHelper.updateCodecParameters(list);
        updateAudioModeHandlers(list, null);
        this.mDeviceBroker.updateCommunicationRouteClientsActivity(list, null);
    }

    void updateAudioModeHandlers(java.util.List<android.media.AudioPlaybackConfiguration> list, java.util.List<android.media.AudioRecordingConfiguration> list2) {
        synchronized (this.mDeviceBroker.mSetModeLock) {
            try {
                java.util.Iterator<com.android.server.audio.AudioService.SetModeDeathHandler> it = this.mSetModeDeathHandlers.iterator();
                int i = 2;
                int i2 = 6000;
                boolean z = false;
                while (it.hasNext()) {
                    com.android.server.audio.AudioService.SetModeDeathHandler next = it.next();
                    boolean isActive = next.isActive();
                    if (list != null) {
                        next.setPlaybackActive(false);
                        java.util.Iterator<android.media.AudioPlaybackConfiguration> it2 = list.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            android.media.AudioPlaybackConfiguration next2 = it2.next();
                            int usage = next2.getAudioAttributes().getUsage();
                            if (next2.getClientUid() == next.getUid() && ((usage == 2 || usage == 3) && next2.isActive())) {
                                next.setPlaybackActive(true);
                                break;
                            }
                        }
                    }
                    if (list2 != null) {
                        next.setRecordingActive(false);
                        java.util.Iterator<android.media.AudioRecordingConfiguration> it3 = list2.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            android.media.AudioRecordingConfiguration next3 = it3.next();
                            if (next3.getClientUid() == next.getUid() && !next3.isClientSilenced() && next3.getAudioSource() == 7) {
                                next.setRecordingActive(true);
                                break;
                            }
                        }
                    }
                    if (isActive != next.isActive()) {
                        if (next.isActive() && next == getAudioModeOwnerHandler()) {
                            i = 0;
                            i2 = 0;
                            z = true;
                        } else {
                            z = true;
                        }
                    }
                }
                if (z) {
                    sendMsg(this.mAudioHandler, 36, i, -1, android.os.Process.myPid(), this.mContext.getPackageName(), i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRecordingConfigChange(java.util.List<android.media.AudioRecordingConfiguration> list) {
        updateAudioModeHandlers(null, list);
        this.mDeviceBroker.updateCommunicationRouteClientsActivity(null, list);
    }

    private void dumpFlags(java.io.PrintWriter printWriter) {
        printWriter.println("\nFun with Flags:");
        printWriter.println("\tandroid.media.audio.autoPublicVolumeApiHardening:" + android.media.audio.Flags.autoPublicVolumeApiHardening());
        printWriter.println("\tandroid.media.audio.Flags.automaticBtDeviceType:" + android.media.audio.Flags.automaticBtDeviceType());
        printWriter.println("\tandroid.media.audio.featureSpatialAudioHeadtrackingLowLatency:" + android.media.audio.Flags.featureSpatialAudioHeadtrackingLowLatency());
        printWriter.println("\tandroid.media.audio.focusFreezeTestApi:" + android.media.audio.Flags.focusFreezeTestApi());
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("\tcom.android.media.audio.disablePrescaleAbsoluteVolume:");
        com.android.media.audio.Flags.disablePrescaleAbsoluteVolume();
        sb.append(true);
        printWriter.println(sb.toString());
        printWriter.println("\tandroid.media.audio.foregroundAudioControl:" + android.media.audio.Flags.foregroundAudioControl());
    }

    private void dumpAudioMode(java.io.PrintWriter printWriter) {
        printWriter.println("\nAudio mode: ");
        printWriter.println("- Requested mode = " + android.media.AudioSystem.modeToString(getMode()));
        printWriter.println("- Actual mode = " + android.media.AudioSystem.modeToString(this.mMode.get()));
        printWriter.println("- Mode owner: ");
        com.android.server.audio.AudioService.SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
        if (audioModeOwnerHandler != null) {
            audioModeOwnerHandler.dump(printWriter, -1);
        } else {
            printWriter.println("   None");
        }
        printWriter.println("- Mode owner stack: ");
        if (this.mSetModeDeathHandlers.isEmpty()) {
            printWriter.println("   Empty");
            return;
        }
        for (int i = 0; i < this.mSetModeDeathHandlers.size(); i++) {
            this.mSetModeDeathHandlers.get(i).dump(printWriter, i);
        }
    }

    private void updateHearingAidVolumeOnVoiceActivityUpdate() {
        int bluetoothContextualVolumeStream = getBluetoothContextualVolumeStream();
        int streamVolume = getStreamVolume(bluetoothContextualVolumeStream);
        sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(6, this.mVoicePlaybackActive.get(), bluetoothContextualVolumeStream, streamVolume));
        this.mDeviceBroker.postSetHearingAidVolumeIndex(streamVolume * 10, bluetoothContextualVolumeStream);
    }

    void updateAbsVolumeMultiModeDevices(int i, int i2) {
        if (i == i2) {
        }
        switch (i2) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                int bluetoothContextualVolumeStream = getBluetoothContextualVolumeStream(i2);
                java.util.Set intersectionAudioDeviceTypes = android.media.AudioSystem.intersectionAudioDeviceTypes(this.mAbsVolumeMultiModeCaseDevices, getDeviceSetForStreamDirect(bluetoothContextualVolumeStream));
                if (!intersectionAudioDeviceTypes.isEmpty() && android.media.AudioSystem.isSingleAudioDeviceType(intersectionAudioDeviceTypes, 134217728)) {
                    int streamVolume = getStreamVolume(bluetoothContextualVolumeStream);
                    sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(7, i2, bluetoothContextualVolumeStream, streamVolume));
                    this.mDeviceBroker.postSetHearingAidVolumeIndex(streamVolume * 10, bluetoothContextualVolumeStream);
                    break;
                }
                break;
        }
    }

    private void setLeAudioVolumeOnModeUpdate(int i, int i2, int i3, int i4, int i5) {
        switch (i) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                if (!android.media.AudioSystem.isLeAudioDeviceType(i2)) {
                    android.util.Log.w(TAG, "setLeAudioVolumeOnModeUpdate ignoring invalid device=" + i2 + ", mode=" + i + ", index=" + i4 + " maxIndex=" + i5 + " streamType=" + i3);
                    break;
                } else {
                    this.mDeviceBroker.postSetLeAudioVolumeIndex(i4, i5, i3);
                    this.mDeviceBroker.postApplyVolumeOnDevice(i3, i2, "setLeAudioVolumeOnModeUpdate");
                    break;
                }
        }
    }

    private void setStreamVolume(int i, int i2, int i3, @android.annotation.Nullable android.media.AudioDeviceAttributes audioDeviceAttributes, java.lang.String str, java.lang.String str2, java.lang.String str3, int i4, boolean z, boolean z2) {
        int internalType;
        int i5;
        int i6;
        int i7;
        int i8;
        if (this.mUseFixedVolume) {
            return;
        }
        ensureValidStreamType(i);
        int i9 = mStreamVolumeAlias[i];
        com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i9];
        if (audioDeviceAttributes == null) {
            internalType = getDeviceForStream(i);
        } else {
            internalType = audioDeviceAttributes.getInternalType();
        }
        if (!android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(java.lang.Integer.valueOf(internalType)) && !android.media.AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(java.lang.Integer.valueOf(internalType)) && (i3 & 64) != 0) {
            return;
        }
        if (i4 != 1000) {
            i5 = i4;
        } else {
            i5 = android.os.UserHandle.getUid(getCurrentUserId(), android.os.UserHandle.getAppId(i4));
        }
        if (!checkNoteAppOp(STREAM_VOLUME_OPS[i9], i5, str, str3)) {
            return;
        }
        if (isAndroidNPlus(str) && wouldToggleZenMode(getNewRingerMode(i9, i2, i3)) && !this.mNm.isNotificationPolicyAccessGrantedForPackage(str)) {
            throw new java.lang.SecurityException("Not allowed to change Do Not Disturb state");
        }
        if (!volumeAdjustmentAllowedByDnd(i9, i3)) {
            return;
        }
        this.mSoundDoseHelper.invalidatePendingVolumeCommand();
        int index = volumeStreamState.getIndex(internalType);
        int rescaleIndex = rescaleIndex(i2 * 10, i, i9);
        if (i9 == 3 && android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(java.lang.Integer.valueOf(internalType)) && (i3 & 64) == 0) {
            this.mDeviceBroker.postSetAvrcpAbsoluteVolumeIndex(rescaleIndex / 10);
        } else if (isAbsoluteVolumeDevice(internalType) && (i3 & 8192) == 0) {
            dispatchAbsoluteVolumeChanged(i, this.mAbsoluteVolumeDeviceInfoMap.get(java.lang.Integer.valueOf(internalType)), rescaleIndex);
        }
        if (android.media.AudioSystem.isLeAudioDeviceType(internalType) && i == getBluetoothContextualVolumeStream() && (i3 & 64) == 0) {
            this.mDeviceBroker.postSetLeAudioVolumeIndex(rescaleIndex, this.mStreamStates[i].getMaxIndex(), i);
        }
        if (internalType == 134217728 && i == getBluetoothContextualVolumeStream()) {
            android.util.Log.i(TAG, "setStreamVolume postSetHearingAidVolumeIndex index=" + rescaleIndex + " stream=" + i);
            this.mDeviceBroker.postSetHearingAidVolumeIndex(rescaleIndex, i);
        }
        int i10 = i3 & (-33);
        if (i9 == 3 && isFixedVolumeDevice(internalType)) {
            int i11 = i10 | 32;
            if (rescaleIndex == 0) {
                i6 = rescaleIndex;
                i7 = i11;
            } else {
                int safeMediaVolumeIndex = this.mSoundDoseHelper.getSafeMediaVolumeIndex(internalType);
                if (safeMediaVolumeIndex >= 0) {
                    i6 = safeMediaVolumeIndex;
                    i7 = i11;
                } else {
                    i6 = volumeStreamState.getMaxIndex();
                    i7 = i11;
                }
            }
        } else {
            i6 = rescaleIndex;
            i7 = i10;
        }
        if (this.mSoundDoseHelper.willDisplayWarningAfterCheckVolume(i, i6, internalType, i7)) {
            i8 = i6;
        } else {
            onSetStreamVolume(i, i6, i7, internalType, str2, z, z2);
            i8 = this.mStreamStates[i].getIndex(internalType);
        }
        synchronized (this.mHdmiClientLock) {
            if (i9 == 3 && index != i8) {
                try {
                    maybeSendSystemAudioStatusCommand(false);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (z2) {
            sendVolumeUpdate(i, index, i8, i7, internalType);
        }
    }

    private void dispatchAbsoluteVolumeChanged(int i, com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo, int i2) {
        android.media.VolumeInfo matchingVolumeInfoForStream = absoluteVolumeDeviceInfo.getMatchingVolumeInfoForStream(i);
        if (matchingVolumeInfoForStream != null) {
            try {
                absoluteVolumeDeviceInfo.mCallback.dispatchDeviceVolumeChanged(absoluteVolumeDeviceInfo.mDevice, new android.media.VolumeInfo.Builder(matchingVolumeInfoForStream).setVolumeIndex(rescaleIndex(i2, i, matchingVolumeInfoForStream)).build());
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Couldn't dispatch absolute volume behavior volume change");
            }
        }
    }

    private void dispatchAbsoluteVolumeAdjusted(int i, com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo, int i2, int i3, int i4) {
        android.media.VolumeInfo matchingVolumeInfoForStream = absoluteVolumeDeviceInfo.getMatchingVolumeInfoForStream(i);
        if (matchingVolumeInfoForStream != null) {
            try {
                absoluteVolumeDeviceInfo.mCallback.dispatchDeviceVolumeAdjusted(absoluteVolumeDeviceInfo.mDevice, new android.media.VolumeInfo.Builder(matchingVolumeInfoForStream).setVolumeIndex(rescaleIndex(i2, i, matchingVolumeInfoForStream)).build(), i3, i4);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Couldn't dispatch absolute volume behavior volume adjustment");
            }
        }
    }

    private boolean volumeAdjustmentAllowedByDnd(int i, int i2) {
        switch (this.mNm.getZenMode()) {
            case 1:
            case 2:
            case 3:
                if (!isStreamMutedByRingerOrZenMode(i) || isUiSoundsStreamType(i) || (i2 & 2) != 0) {
                }
                break;
        }
        return true;
    }

    public void forceVolumeControlStream(int i, android.os.IBinder iBinder) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            return;
        }
        synchronized (this.mForceControlStreamLock) {
            try {
                if (this.mVolumeControlStream != -1 && i != -1) {
                    this.mUserSelectedVolumeControlStream = true;
                }
                this.mVolumeControlStream = i;
                if (this.mVolumeControlStream == -1) {
                    if (this.mForceControlStreamClient != null) {
                        this.mForceControlStreamClient.release();
                        this.mForceControlStreamClient = null;
                    }
                    this.mUserSelectedVolumeControlStream = false;
                } else if (this.mForceControlStreamClient == null) {
                    this.mForceControlStreamClient = new com.android.server.audio.AudioService.ForceControlStreamClient(iBinder);
                } else if (this.mForceControlStreamClient.getBinder() == iBinder) {
                    android.util.Log.d(TAG, "forceVolumeControlStream cb:" + iBinder + " is already linked.");
                } else {
                    this.mForceControlStreamClient.release();
                    this.mForceControlStreamClient = new com.android.server.audio.AudioService.ForceControlStreamClient(iBinder);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class ForceControlStreamClient implements android.os.IBinder.DeathRecipient {
        private android.os.IBinder mCb;

        ForceControlStreamClient(android.os.IBinder iBinder) {
            if (iBinder != null) {
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(com.android.server.audio.AudioService.TAG, "ForceControlStreamClient() could not link to " + iBinder + " binder death");
                    iBinder = null;
                }
            }
            this.mCb = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.audio.AudioService.this.mForceControlStreamLock) {
                try {
                    android.util.Log.w(com.android.server.audio.AudioService.TAG, "SCO client died");
                    if (com.android.server.audio.AudioService.this.mForceControlStreamClient != this) {
                        android.util.Log.w(com.android.server.audio.AudioService.TAG, "unregistered control stream client died");
                    } else {
                        com.android.server.audio.AudioService.this.mForceControlStreamClient = null;
                        com.android.server.audio.AudioService.this.mVolumeControlStream = -1;
                        com.android.server.audio.AudioService.this.mUserSelectedVolumeControlStream = false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void release() {
            if (this.mCb != null) {
                this.mCb.unlinkToDeath(this, 0);
                this.mCb = null;
            }
        }

        public android.os.IBinder getBinder() {
            return this.mCb;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBroadcastToAll(android.content.Intent intent, android.os.Bundle bundle) {
        if (!this.mSystemServer.isPrivileged()) {
            return;
        }
        intent.addFlags(67108864);
        intent.addFlags(268435456);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, null, bundle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void sendStickyBroadcastToAll(android.content.Intent intent) {
        intent.addFlags(268435456);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.sendStickyBroadcastAsUser(intent, android.os.UserHandle.ALL);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCurrentUserId() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int i = android.app.ActivityManager.getService().getCurrentUser().id;
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (android.os.RemoteException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    protected void sendVolumeUpdate(int i, int i2, int i3, int i4, int i5) {
        int i6 = mStreamVolumeAlias[i];
        if (i6 == 3 && isFullVolumeDevice(i5)) {
            i4 &= -2;
        }
        this.mVolumeController.postVolumeChanged(i6, i4);
    }

    private int updateFlagsForTvPlatform(int i) {
        synchronized (this.mHdmiClientLock) {
            try {
                if (this.mHdmiTvClient != null && this.mHdmiSystemAudioSupported && this.mHdmiCecVolumeControlEnabled) {
                    i &= -2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    private void sendMasterMuteUpdate(boolean z, int i) {
        this.mVolumeController.postMasterMuteChanged(updateFlagsForTvPlatform(i));
        sendMsg(this.mAudioHandler, 55, 2, z ? 1 : 0, 0, null, 0);
    }

    private void setStreamVolumeInt(int i, int i2, int i3, boolean z, java.lang.String str, boolean z2) {
        if (isFullVolumeDevice(i3)) {
            return;
        }
        com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i];
        if (volumeStreamState.setIndex(i2, i3, str, z2) || z) {
            sendMsg(this.mAudioHandler, 0, 2, i3, 0, volumeStreamState, 0);
        }
    }

    public boolean isStreamMute(int i) {
        boolean z;
        if (i == Integer.MIN_VALUE) {
            i = getActiveStreamType(i);
        }
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            ensureValidStreamType(i);
            z = this.mStreamStates[i].mIsMuted;
        }
        return z;
    }

    private class RmtSbmxFullVolDeathHandler implements android.os.IBinder.DeathRecipient {
        private android.os.IBinder mICallback;

        RmtSbmxFullVolDeathHandler(android.os.IBinder iBinder) {
            this.mICallback = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.audio.AudioService.TAG, "can't link to death", e);
            }
        }

        boolean isHandlerFor(android.os.IBinder iBinder) {
            return this.mICallback.equals(iBinder);
        }

        void forget() {
            try {
                this.mICallback.unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
                android.util.Log.e(com.android.server.audio.AudioService.TAG, "error unlinking to death", e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.w(com.android.server.audio.AudioService.TAG, "Recorder with remote submix at full volume died " + this.mICallback);
            com.android.server.audio.AudioService.this.forceRemoteSubmixFullVolume(false, this.mICallback);
        }
    }

    private boolean discardRmtSbmxFullVolDeathHandlerFor(android.os.IBinder iBinder) {
        java.util.Iterator<com.android.server.audio.AudioService.RmtSbmxFullVolDeathHandler> it = this.mRmtSbmxFullVolDeathHandlers.iterator();
        while (it.hasNext()) {
            com.android.server.audio.AudioService.RmtSbmxFullVolDeathHandler next = it.next();
            if (next.isHandlerFor(iBinder)) {
                next.forget();
                this.mRmtSbmxFullVolDeathHandlers.remove(next);
                return true;
            }
        }
        return false;
    }

    private boolean hasRmtSbmxFullVolDeathHandlerFor(android.os.IBinder iBinder) {
        java.util.Iterator<com.android.server.audio.AudioService.RmtSbmxFullVolDeathHandler> it = this.mRmtSbmxFullVolDeathHandlers.iterator();
        while (it.hasNext()) {
            if (it.next().isHandlerFor(iBinder)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x007a A[Catch: all -> 0x0048, TryCatch #0 {all -> 0x0048, blocks: (B:13:0x0020, B:15:0x0026, B:17:0x0034, B:18:0x004a, B:20:0x007a, B:21:0x0085, B:25:0x0051, B:27:0x0057, B:29:0x005b, B:31:0x0064), top: B:11:0x001e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void forceRemoteSubmixFullVolume(boolean z, android.os.IBinder iBinder) {
        if (iBinder == null) {
            return;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.CAPTURE_AUDIO_OUTPUT") != 0) {
            android.util.Log.w(TAG, "Trying to call forceRemoteSubmixFullVolume() without CAPTURE_AUDIO_OUTPUT");
            return;
        }
        synchronized (this.mRmtSbmxFullVolDeathHandlers) {
            boolean z2 = true;
            boolean z3 = false;
            try {
                if (z) {
                    if (!hasRmtSbmxFullVolDeathHandlerFor(iBinder)) {
                        this.mRmtSbmxFullVolDeathHandlers.add(new com.android.server.audio.AudioService.RmtSbmxFullVolDeathHandler(iBinder));
                        if (this.mRmtSbmxFullVolRefCount == 0) {
                            this.mFullVolumeDevices.add(32768);
                            this.mFixedVolumeDevices.add(32768);
                            z3 = true;
                        }
                        this.mRmtSbmxFullVolRefCount++;
                        z2 = z3;
                        if (z2) {
                            checkAllFixedVolumeDevices(3);
                            this.mStreamStates[3].applyAllVolumes();
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                } else {
                    if (discardRmtSbmxFullVolDeathHandlerFor(iBinder) && this.mRmtSbmxFullVolRefCount > 0) {
                        this.mRmtSbmxFullVolRefCount--;
                        if (this.mRmtSbmxFullVolRefCount == 0) {
                            this.mFullVolumeDevices.remove(32768);
                            this.mFixedVolumeDevices.remove(32768);
                            if (z2) {
                            }
                        }
                    }
                    z2 = false;
                    if (z2) {
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setMasterMuteInternal(boolean z, int i, java.lang.String str, int i2, int i3, int i4, java.lang.String str2) {
        if (i2 == 1000) {
            i2 = android.os.UserHandle.getUid(i3, android.os.UserHandle.getAppId(i2));
        }
        if (!z && !checkNoteAppOp(33, i2, str, str2)) {
            return;
        }
        if (i3 != android.os.UserHandle.getCallingUserId() && this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i4, i2) != 0) {
            return;
        }
        setMasterMuteInternalNoCallerCheck(z, i, i3, "setMasterMute");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMasterMuteInternalNoCallerCheck(boolean z, int i, int i2, java.lang.String str) {
        if (!isPlatformAutomotive() && this.mUseFixedVolume) {
            z = false;
        }
        if (((isPlatformAutomotive() && i2 == 0) || getCurrentUserId() == i2) && z != this.mMasterMute.getAndSet(z)) {
            sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(12, z));
            this.mAudioSystem.setMasterMute(z);
            sendMasterMuteUpdate(z, i);
        }
    }

    public boolean isMasterMute() {
        return this.mMasterMute.get();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void setMasterMute(boolean z, int i, java.lang.String str, int i2, java.lang.String str2) {
        super.setMasterMute_enforcePermission();
        setMasterMuteInternal(z, i, str, android.os.Binder.getCallingUid(), i2, android.os.Binder.getCallingPid(), str2);
    }

    public int getStreamVolume(int i) {
        ensureValidStreamType(i);
        return getStreamVolume(i, getDeviceForStream(i));
    }

    private int getStreamVolume(int i, int i2) {
        int i3;
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            try {
                int index = this.mStreamStates[i].getIndex(i2);
                if (this.mStreamStates[i].mIsMuted) {
                    index = 0;
                }
                if (index != 0 && mStreamVolumeAlias[i] == 3 && isFixedVolumeDevice(i2)) {
                    index = this.mStreamStates[i].getMaxIndex();
                }
                i3 = (index + 5) / 10;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i3;
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED"})
    @android.annotation.NonNull
    public android.media.VolumeInfo getDeviceVolume(@android.annotation.NonNull android.media.VolumeInfo volumeInfo, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, @android.annotation.NonNull java.lang.String str) {
        int index;
        android.media.VolumeInfo build;
        super.getDeviceVolume_enforcePermission();
        java.util.Objects.requireNonNull(volumeInfo);
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        java.util.Objects.requireNonNull(str);
        if (!volumeInfo.hasStreamType()) {
            android.util.Log.e(TAG, "Unsupported non-stream type based VolumeInfo", new java.lang.Exception());
            return getDefaultVolumeInfo();
        }
        int streamType = volumeInfo.getStreamType();
        android.media.VolumeInfo.Builder builder = new android.media.VolumeInfo.Builder(volumeInfo);
        builder.setMinVolumeIndex((this.mStreamStates[streamType].mIndexMin + 5) / 10);
        builder.setMaxVolumeIndex((this.mStreamStates[streamType].mIndexMax + 5) / 10);
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            try {
                if (isFixedVolumeDevice(audioDeviceAttributes.getInternalType())) {
                    index = (this.mStreamStates[streamType].mIndexMax + 5) / 10;
                } else {
                    index = (this.mStreamStates[streamType].getIndex(audioDeviceAttributes.getInternalType()) + 5) / 10;
                }
                builder.setVolumeIndex(index);
                if (this.mStreamStates[streamType].mIsMuted) {
                    builder.setMuted(true);
                }
                build = builder.build();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return build;
    }

    public int getStreamMaxVolume(int i) {
        ensureValidStreamType(i);
        return (this.mStreamStates[i].getMaxIndex() + 5) / 10;
    }

    public int getStreamMinVolume(int i) {
        ensureValidStreamType(i);
        return (this.mStreamStates[i].getMinIndex(android.os.Binder.getCallingUid() == 1000 || callingHasAudioSettingsPermission() || this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0) + 5) / 10;
    }

    @android.annotation.EnforcePermission("android.permission.QUERY_AUDIO_STATE")
    public int getLastAudibleStreamVolume(int i) {
        super.getLastAudibleStreamVolume_enforcePermission();
        ensureValidStreamType(i);
        return (this.mStreamStates[i].getIndex(getDeviceForStream(i)) + 5) / 10;
    }

    public android.media.VolumeInfo getDefaultVolumeInfo() {
        if (sDefaultVolumeInfo == null) {
            sDefaultVolumeInfo = new android.media.VolumeInfo.Builder(3).setMinVolumeIndex(getStreamMinVolume(3)).setMaxVolumeIndex(getStreamMaxVolume(3)).build();
        }
        return sDefaultVolumeInfo;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public void registerStreamAliasingDispatcher(android.media.IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) {
        super.registerStreamAliasingDispatcher_enforcePermission();
        java.util.Objects.requireNonNull(iStreamAliasingDispatcher);
        if (z) {
            this.mStreamAliasingDispatchers.register(iStreamAliasingDispatcher);
        } else {
            this.mStreamAliasingDispatchers.unregister(iStreamAliasingDispatcher);
        }
    }

    protected void dispatchStreamAliasingUpdate() {
        int beginBroadcast = this.mStreamAliasingDispatchers.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mStreamAliasingDispatchers.getBroadcastItem(i).dispatchStreamAliasingChanged();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error on stream alias update dispatch", e);
            }
        }
        this.mStreamAliasingDispatchers.finishBroadcast();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    /* renamed from: getIndependentStreamTypes, reason: merged with bridge method [inline-methods] */
    public java.util.ArrayList<java.lang.Integer> m1930getIndependentStreamTypes() {
        super.getIndependentStreamTypes_enforcePermission();
        if (this.mUseVolumeGroupAliases) {
            return new java.util.ArrayList<>(java.util.Arrays.stream(android.media.AudioManager.getPublicStreamTypes()).boxed().toList());
        }
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>(1);
        for (int i : mStreamVolumeAlias) {
            if (!arrayList.contains(java.lang.Integer.valueOf(i))) {
                arrayList.add(java.lang.Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public int getStreamTypeAlias(int i) {
        super.getStreamTypeAlias_enforcePermission();
        ensureValidStreamType(i);
        return mStreamVolumeAlias[i];
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public boolean isVolumeControlUsingVolumeGroups() {
        super.isVolumeControlUsingVolumeGroups_enforcePermission();
        return this.mUseVolumeGroupAliases;
    }

    public int getUiSoundsStreamType() {
        return this.mUseVolumeGroupAliases ? this.STREAM_VOLUME_ALIAS_VOICE[1] : mStreamVolumeAlias[1];
    }

    private boolean isUiSoundsStreamType(int i) {
        return this.mUseVolumeGroupAliases ? this.STREAM_VOLUME_ALIAS_VOICE[i] == this.STREAM_VOLUME_ALIAS_VOICE[1] : i == mStreamVolumeAlias[1];
    }

    public void setMicrophoneMute(boolean z, java.lang.String str, int i, java.lang.String str2) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 1000) {
            callingUid = android.os.UserHandle.getUid(i, android.os.UserHandle.getAppId(callingUid));
        }
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.mic").setUid(callingUid).set(android.media.MediaMetrics.Property.CALLING_PACKAGE, str).set(android.media.MediaMetrics.Property.EVENT, "setMicrophoneMute").set(android.media.MediaMetrics.Property.REQUEST, z ? "mute" : "unmute");
        if (!z && !checkNoteAppOp(44, callingUid, str, str2)) {
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "disallow unmuting").record();
            return;
        }
        if (!checkAudioSettingsPermission("setMicrophoneMute()")) {
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "!checkAudioSettingsPermission").record();
            return;
        }
        if (i != android.os.UserHandle.getCallingUserId() && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "permission").record();
            return;
        }
        this.mMicMuteFromApi = z;
        item.record();
        setMicrophoneMuteNoCallerCheck(i);
    }

    public void setMicrophoneMuteFromSwitch(boolean z) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 1000) {
            android.util.Log.e(TAG, "setMicrophoneMuteFromSwitch() called from non system user!");
            return;
        }
        this.mMicMuteFromSwitch = z;
        new android.media.MediaMetrics.Item("audio.mic").setUid(callingUid).set(android.media.MediaMetrics.Property.EVENT, "setMicrophoneMuteFromSwitch").set(android.media.MediaMetrics.Property.REQUEST, z ? "mute" : "unmute").record();
        setMicrophoneMuteNoCallerCheck(callingUid);
    }

    private void setMicMuteFromSwitchInput() {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        if (inputManager.isMicMuted() != -1) {
            setMicrophoneMuteFromSwitch(inputManager.isMicMuted() != 0);
        }
    }

    public boolean isMicrophoneMuted() {
        return this.mMicMuteFromSystemCached && (!this.mMicMuteFromPrivacyToggle || this.mMicMuteFromApi || this.mMicMuteFromRestrictions || this.mMicMuteFromSwitch);
    }

    private boolean isMicrophoneSupposedToBeMuted() {
        return this.mMicMuteFromSwitch || this.mMicMuteFromRestrictions || this.mMicMuteFromApi || this.mMicMuteFromPrivacyToggle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMicrophoneMuteNoCallerCheck(int i) {
        boolean isMicrophoneSupposedToBeMuted = isMicrophoneSupposedToBeMuted();
        if (getCurrentUserId() == i || i == 1000) {
            boolean isMicrophoneMuted = this.mAudioSystem.isMicrophoneMuted();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                int muteMicrophone = this.mAudioSystem.muteMicrophone(isMicrophoneSupposedToBeMuted);
                this.mMicMuteFromSystemCached = this.mAudioSystem.isMicrophoneMuted();
                if (muteMicrophone != 0) {
                    android.util.Log.e(TAG, "Error changing mic mute state to " + isMicrophoneSupposedToBeMuted + " current:" + this.mMicMuteFromSystemCached);
                }
                new android.media.MediaMetrics.Item("audio.mic").setUid(i).set(android.media.MediaMetrics.Property.EVENT, "setMicrophoneMuteNoCallerCheck").set(android.media.MediaMetrics.Property.MUTE, this.mMicMuteFromSystemCached ? "on" : "off").set(android.media.MediaMetrics.Property.REQUEST, isMicrophoneSupposedToBeMuted ? "mute" : "unmute").set(android.media.MediaMetrics.Property.STATUS, java.lang.Integer.valueOf(muteMicrophone)).record();
                if (isMicrophoneSupposedToBeMuted != isMicrophoneMuted) {
                    sendMsg(this.mAudioHandler, 30, 1, 0, 0, null, 0);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public int getRingerModeExternal() {
        int i;
        synchronized (this.mSettingsLock) {
            i = this.mRingerModeExternal;
        }
        return i;
    }

    public int getRingerModeInternal() {
        int i;
        synchronized (this.mSettingsLock) {
            i = this.mRingerMode;
        }
        return i;
    }

    private void ensureValidRingerMode(int i) {
        if (!isValidRingerMode(i)) {
            throw new java.lang.IllegalArgumentException("Bad ringer mode " + i);
        }
    }

    public boolean isValidRingerMode(int i) {
        return i >= 0 && i <= 2;
    }

    public void setRingerModeExternal(int i, java.lang.String str) {
        if (this.mHardeningEnforcer.blockVolumeMethod(200)) {
            return;
        }
        if (isAndroidNPlus(str) && wouldToggleZenMode(i) && !this.mNm.isNotificationPolicyAccessGrantedForPackage(str)) {
            throw new java.lang.SecurityException("Not allowed to change Do Not Disturb state");
        }
        setRingerMode(i, str, true);
    }

    public void setRingerModeInternal(int i, java.lang.String str) {
        enforceVolumeController("setRingerModeInternal");
        setRingerMode(i, str, false);
    }

    public void silenceRingerModeInternal(java.lang.String str) {
        int i;
        android.os.VibrationEffect vibrationEffect;
        int i2;
        if (!this.mContext.getResources().getBoolean(android.R.bool.config_viewRotaryEncoderHapticScrollFedbackEnabled)) {
            i = 0;
        } else {
            i = this.mSettings.getSecureIntForUser(this.mContentResolver, "volume_hush_gesture", 0, -2);
        }
        int i3 = 1;
        switch (i) {
            case 1:
                vibrationEffect = android.os.VibrationEffect.get(5);
                i2 = android.R.string.usb_midi_notification_title;
                break;
            case 2:
                vibrationEffect = android.os.VibrationEffect.get(1);
                i2 = 17041867;
                i3 = 0;
                break;
            default:
                vibrationEffect = null;
                i3 = 0;
                i2 = 0;
                break;
        }
        maybeVibrate(vibrationEffect, str);
        setRingerModeInternal(i3, str);
        android.widget.Toast.makeText(this.mContext, i2, 0).show();
    }

    private boolean maybeVibrate(android.os.VibrationEffect vibrationEffect, java.lang.String str) {
        if (!this.mHasVibrator || vibrationEffect == null) {
            return false;
        }
        this.mVibrator.vibrate(android.os.Binder.getCallingUid(), this.mContext.getOpPackageName(), vibrationEffect, str, TOUCH_VIBRATION_ATTRIBUTES);
        return true;
    }

    private void setRingerMode(int i, java.lang.String str, boolean z) {
        int i2;
        if (this.mUseFixedVolume || this.mIsSingleVolume || this.mUseVolumeGroupAliases) {
            return;
        }
        if (str == null || str.length() == 0) {
            throw new java.lang.IllegalArgumentException("Bad caller: " + str);
        }
        ensureValidRingerMode(i);
        if (i == 1 && !this.mHasVibrator) {
            i2 = 0;
        } else {
            i2 = i;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                try {
                    int ringerModeInternal = getRingerModeInternal();
                    int ringerModeExternal = getRingerModeExternal();
                    if (z) {
                        setRingerModeExt(i2);
                        if (this.mRingerModeDelegate != null) {
                            i2 = this.mRingerModeDelegate.onSetRingerModeExternal(ringerModeExternal, i2, str, ringerModeInternal, this.mVolumePolicy);
                        }
                        if (i2 != ringerModeInternal) {
                            setRingerModeInt(i2, true);
                        }
                    } else {
                        if (i2 != ringerModeInternal) {
                            setRingerModeInt(i2, true);
                        }
                        if (this.mRingerModeDelegate != null) {
                            i2 = this.mRingerModeDelegate.onSetRingerModeInternal(ringerModeInternal, i2, str, ringerModeExternal, this.mVolumePolicy);
                        }
                        setRingerModeExt(i2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void setRingerModeExt(int i) {
        synchronized (this.mSettingsLock) {
            try {
                if (i == this.mRingerModeExternal) {
                    return;
                }
                this.mRingerModeExternal = i;
                broadcastRingerMode("android.media.RINGER_MODE_CHANGED", i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    private void muteRingerModeStreams() {
        int numStreamTypes = android.media.AudioSystem.getNumStreamTypes();
        if (this.mNm == null) {
            this.mNm = (android.app.NotificationManager) this.mContext.getSystemService("notification");
        }
        int i = this.mRingerMode;
        boolean z = i == 1 || i == 0;
        boolean z2 = i == 1 && this.mDeviceBroker.isBluetoothScoActive();
        sendMsg(this.mAudioHandler, 8, 2, 7, z2 ? 3 : 0, "muteRingerModeStreams() from u/pid:" + android.os.Binder.getCallingUid() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.Binder.getCallingPid(), 0);
        int i2 = numStreamTypes - 1;
        while (i2 >= 0) {
            boolean isStreamMutedByRingerOrZenMode = isStreamMutedByRingerOrZenMode(i2);
            boolean z3 = isStreamAffectedByCurrentZen(i2) || (z && isStreamAffectedByRingerMode(i2) && (!z2 || i2 != 2));
            if (isStreamMutedByRingerOrZenMode != z3) {
                if (!z3) {
                    if (mStreamVolumeAlias[i2] == 2 || mStreamVolumeAlias[i2] == 5) {
                        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                            try {
                                com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i2];
                                for (int i3 = 0; i3 < volumeStreamState.mIndexMap.size(); i3++) {
                                    int keyAt = volumeStreamState.mIndexMap.keyAt(i3);
                                    if (volumeStreamState.mIndexMap.valueAt(i3) == 0) {
                                        volumeStreamState.setIndex(10, keyAt, TAG, true);
                                    }
                                }
                                sendMsg(this.mAudioHandler, 1, 2, getDeviceForStream(i2), 0, this.mStreamStates[i2], 500);
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    }
                    sRingerAndZenModeMutedStreams &= ~(1 << i2);
                    sMuteLogger.enqueue(new com.android.server.audio.AudioServiceEvents.RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "muteRingerModeStreams"));
                    this.mStreamStates[i2].mute(false, "muteRingerModeStreams");
                } else {
                    sRingerAndZenModeMutedStreams |= 1 << i2;
                    sMuteLogger.enqueue(new com.android.server.audio.AudioServiceEvents.RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "muteRingerModeStreams"));
                    this.mStreamStates[i2].mute(true, "muteRingerModeStreams");
                }
            }
            i2--;
        }
    }

    private boolean isAlarm(int i) {
        return i == 4;
    }

    private boolean isNotificationOrRinger(int i) {
        return i == 5 || i == 2;
    }

    private boolean isMedia(int i) {
        return i == 3;
    }

    private boolean isSystem(int i) {
        return i == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRingerModeInt(int i, boolean z) {
        boolean z2;
        synchronized (this.mSettingsLock) {
            z2 = this.mRingerMode != i;
            this.mRingerMode = i;
            muteRingerModeStreams();
        }
        if (z) {
            sendMsg(this.mAudioHandler, 3, 0, 0, 0, null, 500);
        }
        if (z2) {
            broadcastRingerMode("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", i);
        }
    }

    void postUpdateRingerModeServiceInt() {
        sendMsg(this.mAudioHandler, 25, 2, 0, 0, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateRingerModeServiceInt() {
        setRingerModeInt(getRingerModeInternal(), false);
    }

    public boolean shouldVibrate(int i) {
        if (!this.mHasVibrator) {
            return false;
        }
        switch (getVibrateSetting(i)) {
            case 1:
                if (getRingerModeExternal() != 0) {
                    break;
                }
                break;
            case 2:
                if (getRingerModeExternal() == 1) {
                    break;
                }
                break;
        }
        return false;
    }

    public int getVibrateSetting(int i) {
        if (this.mHasVibrator) {
            return (this.mVibrateSetting >> (i * 2)) & 3;
        }
        return 0;
    }

    public void setVibrateSetting(int i, int i2) {
        if (this.mHasVibrator) {
            this.mVibrateSetting = android.media.AudioSystem.getValueForVibrateSetting(this.mVibrateSetting, i, i2);
            broadcastVibrateSetting(i);
        }
    }

    private class SetModeDeathHandler implements android.os.IBinder.DeathRecipient {
        private final android.os.IBinder mCb;
        private final boolean mIsPrivileged;
        private int mMode;
        private final java.lang.String mPackage;
        private final int mPid;
        private final int mUid;
        private boolean mPlaybackActive = false;
        private boolean mRecordingActive = false;
        private long mUpdateTime = java.lang.System.currentTimeMillis();

        SetModeDeathHandler(android.os.IBinder iBinder, int i, int i2, boolean z, java.lang.String str, int i3) {
            this.mMode = i3;
            this.mCb = iBinder;
            this.mPid = i;
            this.mUid = i2;
            this.mPackage = str;
            this.mIsPrivileged = z;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.audio.AudioService.this.mDeviceBroker.mSetModeLock) {
                try {
                    android.util.Log.w(com.android.server.audio.AudioService.TAG, "SetModeDeathHandler client died");
                    int indexOf = com.android.server.audio.AudioService.this.mSetModeDeathHandlers.indexOf(this);
                    if (indexOf < 0) {
                        android.util.Log.w(com.android.server.audio.AudioService.TAG, "unregistered SetModeDeathHandler client died");
                    } else {
                        com.android.server.audio.AudioService.this.mSetModeDeathHandlers.get(indexOf);
                        com.android.server.audio.AudioService.this.mSetModeDeathHandlers.remove(indexOf);
                        com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 36, 2, -1, android.os.Process.myPid(), com.android.server.audio.AudioService.this.mContext.getPackageName(), 0);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getPid() {
            return this.mPid;
        }

        public void setMode(int i) {
            this.mMode = i;
            this.mUpdateTime = java.lang.System.currentTimeMillis();
        }

        public int getMode() {
            return this.mMode;
        }

        public android.os.IBinder getBinder() {
            return this.mCb;
        }

        public int getUid() {
            return this.mUid;
        }

        public java.lang.String getPackage() {
            return this.mPackage;
        }

        public boolean isPrivileged() {
            return this.mIsPrivileged;
        }

        public long getUpdateTime() {
            return this.mUpdateTime;
        }

        public void setPlaybackActive(boolean z) {
            this.mPlaybackActive = z;
        }

        public void setRecordingActive(boolean z) {
            this.mRecordingActive = z;
        }

        public boolean isActive() {
            if (this.mIsPrivileged) {
                return true;
            }
            return (this.mMode == 3 && (this.mRecordingActive || this.mPlaybackActive)) || this.mMode == 1 || this.mMode == 4;
        }

        public void dump(java.io.PrintWriter printWriter, int i) {
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("MM-dd HH:mm:ss:SSS");
            if (i >= 0) {
                printWriter.println("  Requester # " + (i + 1) + ":");
            }
            printWriter.println("  - Mode: " + android.media.AudioSystem.modeToString(this.mMode));
            printWriter.println("  - Binder: " + this.mCb);
            printWriter.println("  - Pid: " + this.mPid);
            printWriter.println("  - Uid: " + this.mUid);
            printWriter.println("  - Package: " + this.mPackage);
            printWriter.println("  - Privileged: " + this.mIsPrivileged);
            printWriter.println("  - Active: " + isActive());
            printWriter.println("    Playback active: " + this.mPlaybackActive);
            printWriter.println("    Recording active: " + this.mRecordingActive);
            printWriter.println("  - update time: " + simpleDateFormat.format(new java.util.Date(this.mUpdateTime)));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mSetModeLock"})
    private com.android.server.audio.AudioService.SetModeDeathHandler getAudioModeOwnerHandler() {
        java.util.Iterator<com.android.server.audio.AudioService.SetModeDeathHandler> it = this.mSetModeDeathHandlers.iterator();
        com.android.server.audio.AudioService.SetModeDeathHandler setModeDeathHandler = null;
        com.android.server.audio.AudioService.SetModeDeathHandler setModeDeathHandler2 = null;
        while (it.hasNext()) {
            com.android.server.audio.AudioService.SetModeDeathHandler next = it.next();
            if (next.isActive()) {
                if (next.isPrivileged()) {
                    if (setModeDeathHandler == null || next.getUpdateTime() > setModeDeathHandler.getUpdateTime()) {
                        setModeDeathHandler = next;
                    }
                } else if (setModeDeathHandler2 == null || next.getUpdateTime() > setModeDeathHandler2.getUpdateTime()) {
                    setModeDeathHandler2 = next;
                }
            }
        }
        return setModeDeathHandler != null ? setModeDeathHandler : setModeDeathHandler2;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mSetModeLock"})
    com.android.server.audio.AudioDeviceBroker.AudioModeInfo getAudioModeOwner() {
        com.android.server.audio.AudioService.SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
        if (audioModeOwnerHandler != null) {
            return new com.android.server.audio.AudioDeviceBroker.AudioModeInfo(audioModeOwnerHandler.getMode(), audioModeOwnerHandler.getPid(), audioModeOwnerHandler.getUid());
        }
        return new com.android.server.audio.AudioDeviceBroker.AudioModeInfo(0, 0, 0);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mSetModeLock"})
    int getModeOwnerUid() {
        com.android.server.audio.AudioService.SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
        if (audioModeOwnerHandler != null) {
            return audioModeOwnerHandler.getUid();
        }
        return 0;
    }

    public void setMode(int i, android.os.IBinder iBinder, java.lang.String str) {
        int i2;
        com.android.server.audio.AudioService.SetModeDeathHandler setModeDeathHandler;
        com.android.server.audio.AudioService.SetModeDeathHandler setModeDeathHandler2;
        int callingPid = android.os.Binder.getCallingPid();
        int callingUid = android.os.Binder.getCallingUid();
        if (!checkAudioSettingsPermission("setMode()")) {
            return;
        }
        if (iBinder == null) {
            android.util.Log.e(TAG, "setMode() called with null binder");
            return;
        }
        if (i < -1 || i >= 7) {
            android.util.Log.w(TAG, "setMode() invalid mode: " + i);
            return;
        }
        if (i != -1) {
            i2 = i;
        } else {
            i2 = getMode();
        }
        if (i2 == 4 && !this.mIsCallScreeningModeSupported) {
            android.util.Log.w(TAG, "setMode(MODE_CALL_SCREENING) not permitted when call screening is not supported");
            return;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
        if ((i2 == 2 || i2 == 5 || i2 == 6) && !z) {
            android.util.Log.w(TAG, "MODIFY_PHONE_STATE Permission Denial: setMode(" + android.media.AudioSystem.modeToString(i2) + ") from pid=" + callingPid + ", uid=" + android.os.Binder.getCallingUid());
            return;
        }
        synchronized (this.mDeviceBroker.mSetModeLock) {
            try {
                java.util.Iterator<com.android.server.audio.AudioService.SetModeDeathHandler> it = this.mSetModeDeathHandlers.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        setModeDeathHandler = null;
                        break;
                    } else {
                        setModeDeathHandler = it.next();
                        if (setModeDeathHandler.getPid() == callingPid) {
                            break;
                        }
                    }
                }
                if (i2 == 0) {
                    if (setModeDeathHandler != null) {
                        if (!setModeDeathHandler.isPrivileged() && setModeDeathHandler.getMode() == 3) {
                            this.mAudioHandler.removeEqualMessages(31, setModeDeathHandler);
                        }
                        this.mSetModeDeathHandlers.remove(setModeDeathHandler);
                        try {
                            setModeDeathHandler.getBinder().unlinkToDeath(setModeDeathHandler, 0);
                        } catch (java.util.NoSuchElementException e) {
                            android.util.Log.w(TAG, "setMode link does not exist ...");
                        }
                    }
                    sendMsg(this.mAudioHandler, 36, 0, i2, callingPid, str, 0);
                } else {
                    if (setModeDeathHandler != null) {
                        setModeDeathHandler.setMode(i2);
                        setModeDeathHandler2 = setModeDeathHandler;
                    } else {
                        com.android.server.audio.AudioService.SetModeDeathHandler setModeDeathHandler3 = new com.android.server.audio.AudioService.SetModeDeathHandler(iBinder, callingPid, callingUid, z, str, i2);
                        try {
                            iBinder.linkToDeath(setModeDeathHandler3, 0);
                            this.mSetModeDeathHandlers.add(setModeDeathHandler3);
                            setModeDeathHandler2 = setModeDeathHandler3;
                        } catch (android.os.RemoteException e2) {
                            android.util.Log.w(TAG, "setMode() could not link to " + iBinder + " binder death");
                            return;
                        }
                    }
                    if (i2 == 3 && !setModeDeathHandler2.isPrivileged()) {
                        setModeDeathHandler2.setPlaybackActive(true);
                        setModeDeathHandler2.setRecordingActive(true);
                        sendMsg(this.mAudioHandler, 31, 2, 0, 0, setModeDeathHandler2, 6000);
                    }
                    sendMsg(this.mAudioHandler, 36, 0, i2, callingPid, str, 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mSetModeLock"})
    void onUpdateAudioMode(int i, int i2, java.lang.String str, boolean z) {
        int i3;
        int i4;
        int i5;
        int i6;
        if (i != -1) {
            i3 = i;
        } else {
            i3 = getMode();
        }
        com.android.server.audio.AudioService.SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
        if (audioModeOwnerHandler == null) {
            i4 = 0;
            i5 = 0;
            i6 = 0;
        } else {
            int mode = audioModeOwnerHandler.getMode();
            int uid = audioModeOwnerHandler.getUid();
            i6 = audioModeOwnerHandler.getPid();
            i4 = mode;
            i5 = uid;
        }
        if (i4 != this.mMode.get() || z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (this.mAudioSystem.setPhoneState(i4, i5) == 0) {
                    sendMsg(this.mAudioHandler, 40, 0, i4, 0, null, 0);
                    int andSet = this.mMode.getAndSet(i4);
                    this.mModeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.PhoneStateEvent(str, i2, i3, i6, i4));
                    int activeStreamType = getActiveStreamType(Integer.MIN_VALUE);
                    int deviceForStream = getDeviceForStream(activeStreamType);
                    int i7 = mStreamVolumeAlias[activeStreamType];
                    int index = this.mStreamStates[i7].getIndex(deviceForStream);
                    int maxIndex = this.mStreamStates[i7].getMaxIndex();
                    int i8 = i4;
                    setStreamVolumeInt(i7, index, deviceForStream, true, str, true);
                    updateStreamVolumeAlias(true, str);
                    updateAbsVolumeMultiModeDevices(andSet, i8);
                    setLeAudioVolumeOnModeUpdate(i8, deviceForStream, i7, index, maxIndex);
                    this.mDeviceBroker.postSetModeOwner(i8, i6, i5);
                    return;
                }
                android.util.Log.w(TAG, "onUpdateAudioMode: failed to set audio mode to: " + i4);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public int getMode() {
        synchronized (this.mDeviceBroker.mSetModeLock) {
            try {
                com.android.server.audio.AudioService.SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
                if (audioModeOwnerHandler == null) {
                    return 0;
                }
                return audioModeOwnerHandler.getMode();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isCallScreeningModeSupported() {
        return this.mIsCallScreeningModeSupported;
    }

    protected void dispatchMode(int i) {
        int beginBroadcast = this.mModeDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mModeDispatchers.getBroadcastItem(i2).dispatchAudioModeChanged(i);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mModeDispatchers.finishBroadcast();
    }

    public void registerModeDispatcher(@android.annotation.NonNull android.media.IAudioModeDispatcher iAudioModeDispatcher) {
        this.mModeDispatchers.register(iAudioModeDispatcher);
    }

    public void unregisterModeDispatcher(@android.annotation.NonNull android.media.IAudioModeDispatcher iAudioModeDispatcher) {
        this.mModeDispatchers.unregister(iAudioModeDispatcher);
    }

    @android.annotation.EnforcePermission("android.permission.CALL_AUDIO_INTERCEPTION")
    public boolean isPstnCallAudioInterceptable() {
        super.isPstnCallAudioInterceptable_enforcePermission();
        boolean z = false;
        boolean z2 = false;
        for (android.media.AudioDeviceInfo audioDeviceInfo : android.media.AudioManager.getDevicesStatic(3)) {
            if (audioDeviceInfo.getInternalType() == 65536) {
                z = true;
            } else if (audioDeviceInfo.getInternalType() == -2147483584) {
                z2 = true;
            }
            if (z && z2) {
                return true;
            }
        }
        return false;
    }

    public void setRttEnabled(boolean z) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            android.util.Log.w(TAG, "MODIFY_PHONE_STATE Permission Denial: setRttEnabled from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid());
            return;
        }
        synchronized (this.mSettingsLock) {
            try {
                this.mRttEnabled = z;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.media.AudioSystem.setRttEnabled(z);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, @android.annotation.NonNull java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Should only be called from system process");
        }
        adjustSuggestedStreamVolume(i2, i, i3, str, str, i4, i5, hasAudioSettingsPermission(i4, i5), 0);
    }

    public void adjustStreamVolumeForUid(int i, int i2, int i3, @android.annotation.NonNull java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Should only be called from system process");
        }
        if (i2 != 0) {
            sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(5, i, i2, i3, str + " uid:" + i4));
        }
        adjustStreamVolume(i, i2, i3, str, str, i4, i5, null, hasAudioSettingsPermission(i4, i5), 0);
    }

    public void adjustVolume(int i, int i2) {
        if (this.mHardeningEnforcer.blockVolumeMethod(101)) {
            return;
        }
        getMediaSessionManager().dispatchAdjustVolume(Integer.MIN_VALUE, i, i2);
    }

    public void adjustSuggestedStreamVolume(int i, int i2, int i3) {
        if (this.mHardeningEnforcer.blockVolumeMethod(102)) {
            return;
        }
        getMediaSessionManager().dispatchAdjustVolume(i2, i, i3);
    }

    public void setStreamVolumeForUid(int i, int i2, int i3, @android.annotation.NonNull java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("Should only be called from system process");
        }
        setStreamVolume(i, i2, i3, null, str, str, null, i4, hasAudioSettingsPermission(i4, i5), true);
    }

    private static final class LoadSoundEffectReply implements com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler {
        private static final int SOUND_EFFECTS_ERROR = -1;
        private static final int SOUND_EFFECTS_LOADED = 0;
        private static final int SOUND_EFFECTS_LOADING = 1;
        private static final int SOUND_EFFECTS_LOAD_TIMEOUT_MS = 5000;
        private int mStatus;

        private LoadSoundEffectReply() {
            this.mStatus = 1;
        }

        @Override // com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler
        public synchronized void run(boolean z) {
            this.mStatus = z ? 0 : -1;
            notify();
        }

        public synchronized boolean waitForLoaded(int i) {
            while (true) {
                if (this.mStatus != 1) {
                    break;
                }
                int i2 = i - 1;
                if (i <= 0) {
                    break;
                }
                try {
                    wait(5000L);
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.w(com.android.server.audio.AudioService.TAG, "Interrupted while waiting sound pool loaded.");
                }
                i = i2;
            }
            return this.mStatus == 0;
        }
    }

    public void playSoundEffect(int i, int i2) {
        if (querySoundEffectsEnabled(i2)) {
            playSoundEffectVolume(i, -1.0f);
        }
    }

    private boolean querySoundEffectsEnabled(int i) {
        return this.mSettings.getSystemIntForUser(getContentResolver(), "sound_effects_enabled", 0, i) != 0;
    }

    public void playSoundEffectVolume(int i, float f) {
        if (isStreamMute(1)) {
            return;
        }
        if (i >= 16 || i < 0) {
            android.util.Log.w(TAG, "AudioService effectType value " + i + " out of range");
            return;
        }
        sendMsg(this.mAudioHandler, 5, 2, i, (int) (f * 1000.0f), null, 0);
    }

    public boolean loadSoundEffects() {
        com.android.server.audio.AudioService.LoadSoundEffectReply loadSoundEffectReply = new com.android.server.audio.AudioService.LoadSoundEffectReply();
        sendMsg(this.mAudioHandler, 7, 2, 0, 0, loadSoundEffectReply, 0);
        return loadSoundEffectReply.waitForLoaded(3);
    }

    protected void scheduleLoadSoundEffects() {
        sendMsg(this.mAudioHandler, 7, 2, 0, 0, null, 0);
    }

    public void unloadSoundEffects() {
        sendMsg(this.mAudioHandler, 15, 2, 0, 0, null, 0);
    }

    public void reloadAudioSettings() {
        readAudioSettings(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0039, code lost:
    
        if (r6.mUseFixedVolume != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void readAudioSettings(boolean z) {
        readPersistedSettings();
        readUserRestrictions();
        int numStreamTypes = android.media.AudioSystem.getNumStreamTypes();
        for (int i = 0; i < numStreamTypes; i++) {
            com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[i];
            if (!z || mStreamVolumeAlias[i] != 3) {
                volumeStreamState.readSettings();
                synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                    try {
                        if (volumeStreamState.mIsMuted) {
                            if (!isStreamAffectedByMute(i)) {
                                if (isStreamMutedByRingerOrZenMode(i)) {
                                }
                                volumeStreamState.mIsMuted = false;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
        readVolumeGroupsSettings(z);
        setRingerModeInt(getRingerModeInternal(), false);
        checkAllFixedVolumeDevices();
        checkAllAliasStreamVolumes();
        checkMuteAffectedStreams();
        this.mSoundDoseHelper.restoreMusicActiveMs();
        this.mSoundDoseHelper.enforceSafeMediaVolumeIfActive(TAG);
        restoreDeviceVolumeBehavior();
    }

    public int[] getAvailableCommunicationDeviceIds() {
        return com.android.server.audio.AudioDeviceBroker.getAvailableCommunicationDevices().stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda3
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                return ((android.media.AudioDeviceInfo) obj).getId();
            }
        }).toArray();
    }

    public boolean setCommunicationDevice(android.os.IBinder iBinder, int i) {
        android.media.AudioDeviceInfo audioDeviceInfo;
        int i2;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        java.lang.String str = null;
        if (i == 0) {
            audioDeviceInfo = null;
        } else {
            audioDeviceInfo = android.media.AudioManager.getDeviceForPortId(i, 2);
            if (audioDeviceInfo == null) {
                android.util.Log.w(TAG, "setCommunicationDevice: invalid portID " + i);
                return false;
            }
            if (!com.android.server.audio.AudioDeviceBroker.isValidCommunicationDevice(audioDeviceInfo)) {
                if (!audioDeviceInfo.isSink()) {
                    throw new java.lang.IllegalArgumentException("device must have sink role");
                }
                throw new java.lang.IllegalArgumentException("invalid device type: " + audioDeviceInfo.getType());
            }
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(audioDeviceInfo == null ? "clearCommunicationDevice(" : "setCommunicationDevice(");
        sb.append(") from u/pid:");
        sb.append(callingUid);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(callingPid);
        java.lang.String sb2 = sb.toString();
        if (audioDeviceInfo != null) {
            i2 = audioDeviceInfo.getPort().type();
            str = audioDeviceInfo.getAddress();
        } else {
            android.media.AudioDeviceInfo communicationDevice = this.mDeviceBroker.getCommunicationDevice();
            if (communicationDevice == null) {
                i2 = 1073741824;
            } else {
                int type = communicationDevice.getPort().type();
                str = communicationDevice.getAddress();
                i2 = type;
            }
        }
        if (i2 != 1073741824) {
            new android.media.MediaMetrics.Item("audio.device.setCommunicationDevice").set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(i2)).set(android.media.MediaMetrics.Property.ADDRESS, str).set(android.media.MediaMetrics.Property.STATE, audioDeviceInfo != null ? "connected" : "disconnected").record();
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mDeviceBroker.setCommunicationDevice(iBinder, callingUid, audioDeviceInfo, z, sb2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getCommunicationDevice() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.media.AudioDeviceInfo communicationDevice = this.mDeviceBroker.getCommunicationDevice();
            return communicationDevice != null ? communicationDevice.getId() : 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerCommunicationDeviceDispatcher(@android.annotation.Nullable android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
        if (iCommunicationDeviceDispatcher == null) {
            return;
        }
        this.mDeviceBroker.registerCommunicationDeviceDispatcher(iCommunicationDeviceDispatcher);
    }

    public void unregisterCommunicationDeviceDispatcher(@android.annotation.Nullable android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
        if (iCommunicationDeviceDispatcher == null) {
            return;
        }
        this.mDeviceBroker.unregisterCommunicationDeviceDispatcher(iCommunicationDeviceDispatcher);
    }

    public void setSpeakerphoneOn(android.os.IBinder iBinder, boolean z) {
        if (!checkAudioSettingsPermission("setSpeakerphoneOn()")) {
            return;
        }
        boolean z2 = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        java.lang.String str = "setSpeakerphoneOn(" + z + ") from u/pid:" + callingUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + callingPid;
        new android.media.MediaMetrics.Item("audio.device.setSpeakerphoneOn").setUid(callingUid).setPid(callingPid).set(android.media.MediaMetrics.Property.STATE, z ? "on" : "off").record();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.setSpeakerphoneOn(iBinder, callingUid, z, z2, str);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isSpeakerphoneOn() {
        return this.mDeviceBroker.isSpeakerphoneOn();
    }

    public void setBluetoothScoOn(boolean z) {
        if (!checkAudioSettingsPermission("setBluetoothScoOn()")) {
            return;
        }
        if (android.os.UserHandle.getCallingAppId() >= 10000) {
            this.mBtScoOnByApp = z;
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        java.lang.String str = "setBluetoothScoOn(" + z + ") from u/pid:" + callingUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + callingPid;
        new android.media.MediaMetrics.Item("audio.device.setBluetoothScoOn").setUid(callingUid).setPid(callingPid).set(android.media.MediaMetrics.Property.STATE, z ? "on" : "off").record();
        this.mDeviceBroker.setBluetoothScoOn(z, str);
    }

    @android.annotation.EnforcePermission("android.permission.BLUETOOTH_STACK")
    public void setA2dpSuspended(boolean z) {
        super.setA2dpSuspended_enforcePermission();
        this.mDeviceBroker.setA2dpSuspended(z, false, "setA2dpSuspended(" + z + ") from u/pid:" + android.os.Binder.getCallingUid() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.Binder.getCallingPid());
    }

    @android.annotation.EnforcePermission("android.permission.BLUETOOTH_STACK")
    public void setLeAudioSuspended(boolean z) {
        super.setLeAudioSuspended_enforcePermission();
        this.mDeviceBroker.setLeAudioSuspended(z, false, "setLeAudioSuspended(" + z + ") from u/pid:" + android.os.Binder.getCallingUid() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.Binder.getCallingPid());
    }

    public boolean isBluetoothScoOn() {
        return this.mBtScoOnByApp || this.mDeviceBroker.isBluetoothScoOn();
    }

    public void setBluetoothA2dpOn(boolean z) {
        if (!checkAudioSettingsPermission("setBluetoothA2dpOn()")) {
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        java.lang.String str = "setBluetoothA2dpOn(" + z + ") from u/pid:" + callingUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + callingPid;
        new android.media.MediaMetrics.Item("audio.device.setBluetoothA2dpOn").setUid(callingUid).setPid(callingPid).set(android.media.MediaMetrics.Property.STATE, z ? "on" : "off").record();
        this.mDeviceBroker.setBluetoothA2dpOn_Async(z, str);
    }

    public boolean isBluetoothA2dpOn() {
        return this.mDeviceBroker.isBluetoothA2dpOn();
    }

    public void startBluetoothSco(android.os.IBinder iBinder, int i) {
        if (!checkAudioSettingsPermission("startBluetoothSco()")) {
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        int i2 = i < 18 ? 0 : -1;
        java.lang.String str = "startBluetoothSco()) from u/pid:" + callingUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + callingPid;
        new android.media.MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(android.media.MediaMetrics.Property.EVENT, "startBluetoothSco").set(android.media.MediaMetrics.Property.SCO_AUDIO_MODE, com.android.server.audio.BtHelper.scoAudioModeToString(i2)).record();
        startBluetoothScoInt(iBinder, callingUid, i2, str);
    }

    public void startBluetoothScoVirtualCall(android.os.IBinder iBinder) {
        if (!checkAudioSettingsPermission("startBluetoothScoVirtualCall()")) {
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        java.lang.String str = "startBluetoothScoVirtualCall()) from u/pid:" + callingUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + callingPid;
        new android.media.MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(android.media.MediaMetrics.Property.EVENT, "startBluetoothScoVirtualCall").set(android.media.MediaMetrics.Property.SCO_AUDIO_MODE, com.android.server.audio.BtHelper.scoAudioModeToString(0)).record();
        startBluetoothScoInt(iBinder, callingUid, 0, str);
    }

    void startBluetoothScoInt(android.os.IBinder iBinder, int i, int i2, @android.annotation.NonNull java.lang.String str) {
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.bluetooth").set(android.media.MediaMetrics.Property.EVENT, "startBluetoothScoInt").set(android.media.MediaMetrics.Property.SCO_AUDIO_MODE, com.android.server.audio.BtHelper.scoAudioModeToString(i2));
        if (!checkAudioSettingsPermission("startBluetoothSco()") || !this.mSystemReady) {
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "permission or systemReady").record();
            return;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.startBluetoothScoForClient(iBinder, i, i2, z, str);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            item.record();
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void stopBluetoothSco(android.os.IBinder iBinder) {
        if (!checkAudioSettingsPermission("stopBluetoothSco()") || !this.mSystemReady) {
            return;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        java.lang.String str = "stopBluetoothSco()) from u/pid:" + callingUid + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + callingPid;
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.stopBluetoothScoForClient(iBinder, callingUid, z, str);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            new android.media.MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(android.media.MediaMetrics.Property.EVENT, "stopBluetoothSco").set(android.media.MediaMetrics.Property.SCO_AUDIO_MODE, com.android.server.audio.BtHelper.scoAudioModeToString(-1)).record();
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    android.content.ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public com.android.server.audio.SettingsAdapter getSettings() {
        return this.mSettings;
    }

    private int checkForRingerModeChange(int i, int i2, int i3, boolean z, java.lang.String str, int i4) {
        int i5;
        if (isPlatformTelevision() || this.mIsSingleVolume) {
            return 1;
        }
        int ringerModeInternal = getRingerModeInternal();
        switch (ringerModeInternal) {
            case 0:
                if (this.mIsSingleVolume && i2 == -1 && i >= i3 * 2 && z) {
                    ringerModeInternal = 2;
                } else if (i2 == 1 || i2 == 101 || i2 == 100) {
                    if (!this.mVolumePolicy.volumeUpToExitSilent) {
                        r1 = 129;
                    } else {
                        ringerModeInternal = (this.mHasVibrator && i2 == 1) ? 1 : 2;
                    }
                }
                i5 = r1 & (-2);
                r1 = ringerModeInternal;
                break;
            case 1:
                if (!this.mHasVibrator) {
                    android.util.Log.e(TAG, "checkForRingerModeChange() current ringer mode is vibratebut no vibrator is present");
                    i5 = 1;
                    r1 = ringerModeInternal;
                    break;
                } else {
                    if (i2 == -1) {
                        if (this.mIsSingleVolume && i >= i3 * 2 && z) {
                            ringerModeInternal = 2;
                        } else if (this.mPrevVolDirection != -1) {
                            if (this.mVolumePolicy.volumeDownToEnterSilent) {
                                if (android.os.SystemClock.uptimeMillis() - this.mLoweredFromNormalToVibrateTime > this.mVolumePolicy.vibrateToSilentDebounce && this.mRingerModeDelegate.canVolumeDownEnterSilent()) {
                                    ringerModeInternal = 0;
                                }
                            } else {
                                r1 = 2049;
                            }
                        }
                    } else if (i2 == 1 || i2 == 101 || i2 == 100) {
                        ringerModeInternal = 2;
                    }
                    i5 = r1 & (-2);
                    r1 = ringerModeInternal;
                    break;
                }
            case 2:
                if (i2 == -1) {
                    if (this.mHasVibrator) {
                        if (i3 <= i && i < i3 * 2) {
                            this.mLoweredFromNormalToVibrateTime = android.os.SystemClock.uptimeMillis();
                            i5 = 1;
                            break;
                        }
                        i5 = 1;
                        r1 = ringerModeInternal;
                        break;
                    } else {
                        if (i == i3 && this.mVolumePolicy.volumeDownToEnterSilent) {
                            i5 = 1;
                            r1 = 0;
                            break;
                        }
                        i5 = 1;
                        r1 = ringerModeInternal;
                    }
                } else {
                    if (this.mIsSingleVolume && (i2 == 101 || i2 == -100)) {
                        r1 = this.mHasVibrator ? 1 : 0;
                        i5 = 0;
                        break;
                    }
                    i5 = 1;
                    r1 = ringerModeInternal;
                }
                break;
            default:
                android.util.Log.e(TAG, "checkForRingerModeChange() wrong ringer mode: " + ringerModeInternal);
                i5 = 1;
                r1 = ringerModeInternal;
                break;
        }
        if (isAndroidNPlus(str) && wouldToggleZenMode(r1) && !this.mNm.isNotificationPolicyAccessGrantedForPackage(str) && (i4 & 4096) == 0) {
            throw new java.lang.SecurityException("Not allowed to change Do Not Disturb state");
        }
        setRingerMode(r1, "AS.AudioService.checkForRingerModeChange", false);
        this.mPrevVolDirection = i2;
        return i5;
    }

    public boolean isStreamAffectedByRingerMode(int i) {
        return ((1 << i) & this.mRingerModeAffectedStreams) != 0;
    }

    public boolean isStreamAffectedByCurrentZen(int i) {
        return ((1 << i) & this.mZenModeAffectedStreams) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isStreamMutedByRingerOrZenMode(int i) {
        return ((1 << i) & sRingerAndZenModeMutedStreams) != 0;
    }

    private boolean updateZenModeAffectedStreams() {
        int i;
        int i2;
        if (!this.mSystemReady) {
            return false;
        }
        int zenMode = this.mNm.getZenMode();
        if (zenMode == 2) {
            i = 62;
        } else if (zenMode == 3) {
            i = 38;
        } else if (zenMode != 1) {
            i = 0;
        } else {
            android.app.NotificationManager.Policy consolidatedNotificationPolicy = this.mNm.getConsolidatedNotificationPolicy();
            if ((consolidatedNotificationPolicy.priorityCategories & 32) != 0) {
                i2 = 0;
            } else {
                i2 = 16;
            }
            if ((consolidatedNotificationPolicy.priorityCategories & 64) == 0) {
                i2 |= 8;
            }
            if ((consolidatedNotificationPolicy.priorityCategories & 128) == 0) {
                i2 |= 2;
            }
            if (!android.service.notification.ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(consolidatedNotificationPolicy)) {
                i = i2;
            } else {
                i = i2 | 32 | 4;
            }
        }
        if (this.mZenModeAffectedStreams == i) {
            return false;
        }
        this.mZenModeAffectedStreams = i;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mSettingsLock"})
    public boolean updateRingerAndZenModeAffectedStreams() {
        int i;
        int i2;
        boolean updateZenModeAffectedStreams = updateZenModeAffectedStreams();
        int systemIntForUser = this.mSettings.getSystemIntForUser(this.mContentResolver, "mode_ringer_streams_affected", 166, -2);
        if (this.mIsSingleVolume) {
            systemIntForUser = 0;
        } else if (this.mRingerModeDelegate != null) {
            systemIntForUser = this.mRingerModeDelegate.getRingerModeAffectedStreams(systemIntForUser);
        }
        if (this.mCameraSoundForced) {
            i = systemIntForUser & (-129);
        } else {
            i = systemIntForUser | 128;
        }
        if (mStreamVolumeAlias[8] == 2) {
            i2 = i | 256;
        } else {
            i2 = i & (-257);
        }
        com.android.media.audio.Flags.ringerModeAffectsAlarm();
        if (i2 != this.mRingerModeAffectedStreams) {
            this.mSettings.putSystemIntForUser(this.mContentResolver, "mode_ringer_streams_affected", i2, -2);
            this.mRingerModeAffectedStreams = i2;
            return true;
        }
        return updateZenModeAffectedStreams;
    }

    public boolean isStreamAffectedByMute(int i) {
        return ((1 << i) & this.mMuteAffectedStreams) != 0;
    }

    private void ensureValidDirection(int i) {
        switch (i) {
            case -100:
            case -1:
            case 0:
            case 1:
            case 100:
            case 101:
                return;
            default:
                throw new java.lang.IllegalArgumentException("Bad direction " + i);
        }
    }

    private void ensureValidStreamType(int i) {
        if (i < 0 || i >= this.mStreamStates.length) {
            throw new java.lang.IllegalArgumentException("Bad stream type " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isMuteAdjust(int i) {
        return i == -100 || i == 100 || i == 101;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isInCommunication() {
        android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService("telecom");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            boolean isInCall = telecomManager.isInCall();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            int i = this.mMode.get();
            return isInCall || i == 3 || i == 2;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean wasStreamActiveRecently(int i, int i2) {
        return this.mAudioSystem.isStreamActive(i, i2) || this.mAudioSystem.isStreamActiveRemotely(i, i2);
    }

    private int getActiveStreamType(int i) {
        if (this.mIsSingleVolume && i == Integer.MIN_VALUE) {
            return 3;
        }
        switch (this.mPlatformType) {
            case 1:
                if (isInCommunication()) {
                    return this.mDeviceBroker.isBluetoothScoActive() ? 6 : 0;
                }
                if (i == Integer.MIN_VALUE) {
                    if (wasStreamActiveRecently(2, sStreamOverrideDelayMs)) {
                        return 2;
                    }
                    return wasStreamActiveRecently(5, sStreamOverrideDelayMs) ? 5 : 3;
                }
                if (wasStreamActiveRecently(5, sStreamOverrideDelayMs)) {
                    return 5;
                }
                if (wasStreamActiveRecently(2, sStreamOverrideDelayMs)) {
                    return 2;
                }
                break;
        }
        if (isInCommunication()) {
            return this.mDeviceBroker.isBluetoothScoActive() ? 6 : 0;
        }
        if (this.mAudioSystem.isStreamActive(5, sStreamOverrideDelayMs)) {
            return 5;
        }
        if (this.mAudioSystem.isStreamActive(2, sStreamOverrideDelayMs)) {
            return 2;
        }
        if (i == Integer.MIN_VALUE) {
            if (this.mAudioSystem.isStreamActive(5, sStreamOverrideDelayMs)) {
                return 5;
            }
            return this.mAudioSystem.isStreamActive(2, sStreamOverrideDelayMs) ? 2 : 3;
        }
        return i;
    }

    private void broadcastRingerMode(java.lang.String str, int i) {
        if (!this.mSystemServer.isPrivileged()) {
            return;
        }
        android.content.Intent intent = new android.content.Intent(str);
        intent.putExtra("android.media.EXTRA_RINGER_MODE", i);
        intent.addFlags(603979776);
        sendStickyBroadcastToAll(intent);
    }

    private void broadcastVibrateSetting(int i) {
        if (this.mSystemServer.isPrivileged() && this.mActivityManagerInternal.isSystemReady()) {
            android.content.Intent intent = new android.content.Intent("android.media.VIBRATE_SETTING_CHANGED");
            intent.putExtra("android.media.EXTRA_VIBRATE_TYPE", i);
            intent.putExtra("android.media.EXTRA_VIBRATE_SETTING", getVibrateSetting(i));
            sendBroadcastToAll(intent, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueMsgUnderWakeLock(android.os.Handler handler, int i, int i2, int i3, java.lang.Object obj, int i4) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mAudioEventWakeLock.acquire();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            sendMsg(handler, i, 2, i2, i3, obj, i4);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendMsg(android.os.Handler handler, int i, int i2, int i3, int i4, java.lang.Object obj, int i5) {
        if (i2 == 0) {
            handler.removeMessages(i);
        } else if (i2 == 1 && handler.hasMessages(i)) {
            return;
        }
        handler.sendMessageAtTime(handler.obtainMessage(i, i3, i4, obj), android.os.SystemClock.uptimeMillis() + i5);
    }

    private static void sendBundleMsg(android.os.Handler handler, int i, int i2, int i3, int i4, java.lang.Object obj, android.os.Bundle bundle, int i5) {
        if (i2 == 0) {
            handler.removeMessages(i);
        } else if (i2 == 1 && handler.hasMessages(i)) {
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis() + i5;
        android.os.Message obtainMessage = handler.obtainMessage(i, i3, i4, obj);
        obtainMessage.setData(bundle);
        handler.sendMessageAtTime(obtainMessage, uptimeMillis);
    }

    boolean checkAudioSettingsPermission(java.lang.String str) {
        if (callingOrSelfHasAudioSettingsPermission()) {
            return true;
        }
        android.util.Log.w(TAG, "Audio Settings Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid());
        return false;
    }

    private boolean callingOrSelfHasAudioSettingsPermission() {
        return this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0;
    }

    private boolean callingHasAudioSettingsPermission() {
        return this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0;
    }

    private boolean hasAudioSettingsPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.MODIFY_AUDIO_SETTINGS", i2, i) == 0;
    }

    protected void initMinStreamVolumeWithoutModifyAudioSettings() {
        int min;
        int i = java.lang.Float.isNaN(android.media.AudioSystem.getStreamVolumeDB(4, MIN_STREAM_VOLUME[4], 4194304)) ? 2 : 4194304;
        int i2 = MAX_STREAM_VOLUME[4];
        while (i2 >= MIN_STREAM_VOLUME[4] && android.media.AudioSystem.getStreamVolumeDB(4, i2, i) >= MIN_ALARM_ATTENUATION_NON_PRIVILEGED_DB) {
            i2--;
        }
        if (i2 <= MIN_STREAM_VOLUME[4]) {
            min = MIN_STREAM_VOLUME[4];
        } else {
            min = java.lang.Math.min(i2 + 1, MAX_STREAM_VOLUME[4]);
        }
        for (int i3 : mStreamVolumeAlias) {
            if (mStreamVolumeAlias[i3] == 4) {
                this.mStreamStates[i3].updateNoPermMinIndex(min);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public int getDeviceForStream(int i) {
        return selectOneAudioDevice(getDeviceSetForStream(i));
    }

    private int selectOneAudioDevice(java.util.Set<java.lang.Integer> set) {
        if (set.isEmpty()) {
            return 0;
        }
        if (set.size() == 1) {
            return set.iterator().next().intValue();
        }
        if (set.contains(4096)) {
            return 4096;
        }
        if (set.contains(2)) {
            return 2;
        }
        if (set.contains(4194304)) {
            return 4194304;
        }
        if (set.contains(262144)) {
            return 262144;
        }
        if (set.contains(262145)) {
            return 262145;
        }
        if (set.contains(2097152)) {
            return 2097152;
        }
        if (set.contains(524288)) {
            return 524288;
        }
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            if (android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(java.lang.Integer.valueOf(intValue))) {
                return intValue;
            }
        }
        android.util.Log.w(TAG, "selectOneAudioDevice returning DEVICE_NONE from invalid device combination " + android.media.AudioSystem.deviceSetToString(set));
        return 0;
    }

    @java.lang.Deprecated
    public int getDeviceMaskForStream(int i) {
        ensureValidStreamType(i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.media.AudioSystem.getDeviceMaskFromSet(getDeviceSetForStreamDirect(i));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public java.util.Set<java.lang.Integer> getDeviceSetForStreamDirect(int i) {
        return android.media.AudioSystem.generateAudioDeviceTypesSet(getDevicesForAttributesInt(android.media.audiopolicy.AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i), true));
    }

    @android.annotation.NonNull
    public java.util.Set<java.lang.Integer> getDeviceSetForStream(int i) {
        java.util.Set<java.lang.Integer> observeDevicesForStream_syncVSS;
        ensureValidStreamType(i);
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            observeDevicesForStream_syncVSS = this.mStreamStates[i].observeDevicesForStream_syncVSS(true);
        }
        return observeDevicesForStream_syncVSS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onObserveDevicesForAllStreams(int i) {
        synchronized (this.mSettingsLock) {
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                for (int i2 = 0; i2 < this.mStreamStates.length; i2++) {
                    try {
                        if (i2 != i) {
                            java.util.Iterator<java.lang.Integer> it = this.mStreamStates[i2].observeDevicesForStream_syncVSS(false).iterator();
                            while (it.hasNext()) {
                                updateVolumeStates(it.next().intValue(), i2, "AudioService#onObserveDevicesForAllStreams");
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void postObserveDevicesForAllStreams() {
        postObserveDevicesForAllStreams(-1);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void postObserveDevicesForAllStreams(int i) {
        sendMsg(this.mAudioHandler, 27, 2, i, 0, null, 0);
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.BLUETOOTH_PRIVILEGED"})
    public void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, android.media.IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, java.lang.String str, android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, boolean z2, int i) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.BLUETOOTH_PRIVILEGED") != 0) {
            throw new java.lang.SecurityException("Missing MODIFY_AUDIO_ROUTING or BLUETOOTH_PRIVILEGED permissions");
        }
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        java.util.Objects.requireNonNull(list);
        int internalType = audioDeviceAttributes.getInternalType();
        if (z) {
            com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo = new com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo(audioDeviceAttributes, list, iAudioDeviceVolumeDispatcher, z2, i);
            com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo2 = this.mAbsoluteVolumeDeviceInfoMap.get(java.lang.Integer.valueOf(internalType));
            if (absoluteVolumeDeviceInfo2 != null && absoluteVolumeDeviceInfo2.mDeviceVolumeBehavior == i) {
                r11 = false;
            }
            if (r11) {
                removeAudioSystemDeviceOutFromFullVolumeDevices(internalType);
                removeAudioSystemDeviceOutFromFixedVolumeDevices(internalType);
                addAudioSystemDeviceOutToAbsVolumeDevices(internalType, absoluteVolumeDeviceInfo);
                dispatchDeviceVolumeBehavior(audioDeviceAttributes, i);
            }
            for (android.media.VolumeInfo volumeInfo : list) {
                if (volumeInfo.getVolumeIndex() != -100 && volumeInfo.getMinVolumeIndex() != -100 && volumeInfo.getMaxVolumeIndex() != -100) {
                    if (volumeInfo.hasStreamType()) {
                        setStreamVolumeInt(volumeInfo.getStreamType(), rescaleIndex(volumeInfo, volumeInfo.getStreamType()), internalType, false, str, true);
                    } else {
                        for (int i2 : volumeInfo.getVolumeGroup().getLegacyStreamTypes()) {
                            setStreamVolumeInt(i2, rescaleIndex(volumeInfo, i2), internalType, false, str, true);
                        }
                    }
                }
            }
            return;
        }
        if (removeAudioSystemDeviceOutFromAbsVolumeDevices(internalType) != null) {
            dispatchDeviceVolumeBehavior(audioDeviceAttributes, 0);
        }
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED"})
    public void setDeviceVolumeBehavior(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, int i, @android.annotation.Nullable java.lang.String str) {
        super.setDeviceVolumeBehavior_enforcePermission();
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        android.media.AudioManager.enforceValidVolumeBehavior(i);
        android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        sVolumeLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("setDeviceVolumeBehavior: dev:" + android.media.AudioSystem.getOutputDeviceName(retrieveBluetoothAddress.getInternalType()) + " addr:" + android.media.Utils.anonymizeBluetoothAddress(retrieveBluetoothAddress.getAddress()) + " behavior:" + android.media.AudioDeviceVolumeManager.volumeBehaviorName(i) + " pack:" + str).printLog(TAG));
        if (str == null) {
            str = "";
        }
        if (retrieveBluetoothAddress.getType() == 8) {
            avrcpSupportsAbsoluteVolume(retrieveBluetoothAddress.getAddress(), i == 3);
        } else {
            setDeviceVolumeBehaviorInternal(retrieveBluetoothAddress, i, str);
            persistDeviceVolumeBehavior(retrieveBluetoothAddress.getInternalType(), i);
        }
    }

    private void setDeviceVolumeBehaviorInternal(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, int i, @android.annotation.NonNull java.lang.String str) {
        int internalType = audioDeviceAttributes.getInternalType();
        boolean z = false;
        switch (i) {
            case 0:
                z = false | (removeAudioSystemDeviceOutFromAbsVolumeDevices(internalType) != null) | removeAudioSystemDeviceOutFromFullVolumeDevices(internalType) | removeAudioSystemDeviceOutFromFixedVolumeDevices(internalType);
                break;
            case 1:
                z = false | (removeAudioSystemDeviceOutFromAbsVolumeDevices(internalType) != null) | addAudioSystemDeviceOutToFullVolumeDevices(internalType) | removeAudioSystemDeviceOutFromFixedVolumeDevices(internalType);
                break;
            case 2:
                z = false | (removeAudioSystemDeviceOutFromAbsVolumeDevices(internalType) != null) | removeAudioSystemDeviceOutFromFullVolumeDevices(internalType) | addAudioSystemDeviceOutToFixedVolumeDevices(internalType);
                break;
            case 3:
            case 4:
            case 5:
                throw new java.lang.IllegalArgumentException("Absolute volume unsupported for now");
        }
        if (z) {
            sendMsg(this.mAudioHandler, 47, 2, i, 0, audioDeviceAttributes, 0);
        }
        sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("Volume behavior " + i + " for dev=0x" + java.lang.Integer.toHexString(internalType) + " from:" + str));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("setDeviceVolumeBehavior:");
        sb.append(str);
        postUpdateVolumeStatesForAudioDevice(internalType, sb.toString());
    }

    @android.annotation.EnforcePermission(anyOf = {"android.permission.MODIFY_AUDIO_ROUTING", "android.permission.QUERY_AUDIO_STATE", "android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED"})
    public int getDeviceVolumeBehavior(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.getDeviceVolumeBehavior_enforcePermission();
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        return getDeviceVolumeBehaviorInt(retrieveBluetoothAddress(audioDeviceAttributes));
    }

    private int getDeviceVolumeBehaviorInt(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        int internalType = audioDeviceAttributes.getInternalType();
        if (this.mFullVolumeDevices.contains(java.lang.Integer.valueOf(internalType))) {
            return 1;
        }
        if (this.mFixedVolumeDevices.contains(java.lang.Integer.valueOf(internalType))) {
            return 2;
        }
        if (this.mAbsVolumeMultiModeCaseDevices.contains(java.lang.Integer.valueOf(internalType))) {
            return 4;
        }
        if (this.mAbsoluteVolumeDeviceInfoMap.containsKey(java.lang.Integer.valueOf(internalType))) {
            return this.mAbsoluteVolumeDeviceInfoMap.get(java.lang.Integer.valueOf(internalType)).mDeviceVolumeBehavior;
        }
        if (isA2dpAbsoluteVolumeDevice(internalType) || android.media.AudioSystem.isLeAudioDeviceType(internalType)) {
            return 3;
        }
        return 0;
    }

    public boolean isVolumeFixed() {
        if (this.mUseFixedVolume) {
            return true;
        }
        java.util.Iterator<android.media.AudioDeviceAttributes> it = getDevicesForAttributesInt(new android.media.AudioAttributes.Builder().setUsage(1).build(), true).iterator();
        while (it.hasNext()) {
            if (getDeviceVolumeBehaviorInt(it.next()) == 2) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void setWiredDeviceConnectionState(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) {
        super.setWiredDeviceConnectionState_enforcePermission();
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        if (retrieveBluetoothAddress.getType() == 10 && retrieveBluetoothAddress.getRole() == 2 && retrieveBluetoothAddress.getAudioDescriptors().isEmpty()) {
            retrieveBluetoothAddress = new android.media.AudioDeviceAttributes(retrieveBluetoothAddress.getRole(), retrieveBluetoothAddress.getType(), retrieveBluetoothAddress.getAddress(), retrieveBluetoothAddress.getName(), retrieveBluetoothAddress.getAudioProfiles(), new java.util.ArrayList(java.util.Collections.singletonList(new android.media.AudioDescriptor(1, 0, DEFAULT_ARC_AUDIO_DESCRIPTOR))));
        }
        if (i != 1 && i != 0) {
            throw new java.lang.IllegalArgumentException("Invalid state " + i);
        }
        new android.media.MediaMetrics.Item("audio.service.setWiredDeviceConnectionState").set(android.media.MediaMetrics.Property.ADDRESS, retrieveBluetoothAddress.getAddress()).set(android.media.MediaMetrics.Property.CLIENT_NAME, str).set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(retrieveBluetoothAddress.getInternalType())).set(android.media.MediaMetrics.Property.NAME, retrieveBluetoothAddress.getName()).set(android.media.MediaMetrics.Property.STATE, i == 1 ? "connected" : "disconnected").record();
        this.mDeviceBroker.setWiredDeviceConnectionState(retrieveBluetoothAddress, i, str);
        if (retrieveBluetoothAddress.getInternalType() == -2013265920) {
            updateHdmiAudioSystemClient();
        }
    }

    private void updateHdmiAudioSystemClient() {
        android.util.Slog.d(TAG, "Hdmi Audio System Client is updated");
        synchronized (this.mHdmiClientLock) {
            this.mHdmiAudioSystemClient = this.mHdmiManager.getAudioSystemClient();
        }
    }

    public void setTestDeviceConnectionState(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.setTestDeviceConnectionState(retrieveBluetoothAddress(audioDeviceAttributes), z ? 1 : 0);
        sendMsg(this.mAudioHandler, 41, 0, 0, 0, null, 0);
    }

    @android.annotation.EnforcePermission("android.permission.BLUETOOTH_STACK")
    public void handleBluetoothActiveDeviceChanged(android.bluetooth.BluetoothDevice bluetoothDevice, android.bluetooth.BluetoothDevice bluetoothDevice2, @android.annotation.NonNull android.media.BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) {
        handleBluetoothActiveDeviceChanged_enforcePermission();
        if (bluetoothProfileConnectionInfo == null) {
            throw new java.lang.IllegalArgumentException("Illegal null BluetoothProfileConnectionInfo for device " + bluetoothDevice2 + " -> " + bluetoothDevice);
        }
        int profile = bluetoothProfileConnectionInfo.getProfile();
        if (profile != 2 && profile != 11 && profile != 22 && profile != 26 && profile != 21) {
            throw new java.lang.IllegalArgumentException("Illegal BluetoothProfile profile for device " + bluetoothDevice2 + " -> " + bluetoothDevice + ". Got: " + profile);
        }
        sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("BlutoothActiveDeviceChanged for " + android.bluetooth.BluetoothProfile.getProfileName(profile) + ", device update " + bluetoothDevice2 + " -> " + bluetoothDevice).printLog(TAG));
        sendMsg(this.mAudioHandler, 38, 2, 0, 0, new com.android.server.audio.AudioDeviceBroker.BtDeviceChangedData(bluetoothDevice, bluetoothDevice2, bluetoothProfileConnectionInfo, "AudioService"), 0);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void setMusicMute(boolean z) {
        this.mStreamStates[3].muteInternally(z);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void postAccessoryPlugMediaUnmute(int i) {
        sendMsg(this.mAudioHandler, 21, 2, i, 0, null, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAccessoryPlugMediaUnmute(int i) {
        if (this.mNm.getZenMode() != 2 && !isStreamMutedByRingerOrZenMode(3) && DEVICE_MEDIA_UNMUTED_ON_PLUG_SET.contains(java.lang.Integer.valueOf(i)) && this.mStreamStates[3].mIsMuted && this.mStreamStates[3].getIndex(i) != 0 && getDeviceSetForStreamDirect(3).contains(java.lang.Integer.valueOf(i))) {
            synchronized (this.mSettingsLock) {
                this.mStreamStates[3].mute(false, "onAccessoryPlugMediaUnmute");
            }
        }
    }

    public boolean hasHapticChannels(android.net.Uri uri) {
        return android.media.AudioManager.hasHapticChannelsImpl(this.mContext, uri);
    }

    private void initVolumeGroupStates() {
        for (android.media.audiopolicy.AudioVolumeGroup audioVolumeGroup : getAudioVolumeGroups()) {
            try {
                ensureValidAttributes(audioVolumeGroup);
                sVolumeGroupStates.append(audioVolumeGroup.getId(), new com.android.server.audio.AudioService.VolumeGroupState(audioVolumeGroup));
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
        synchronized (this.mSettingsLock) {
            for (int i = 0; i < sVolumeGroupStates.size(); i++) {
                try {
                    sVolumeGroupStates.valueAt(i).applyAllVolumes(false);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void ensureValidAttributes(android.media.audiopolicy.AudioVolumeGroup audioVolumeGroup) {
        if (!audioVolumeGroup.getAudioAttributes().stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$ensureValidAttributes$11;
                lambda$ensureValidAttributes$11 = com.android.server.audio.AudioService.lambda$ensureValidAttributes$11((android.media.AudioAttributes) obj);
                return lambda$ensureValidAttributes$11;
            }
        })) {
            throw new java.lang.IllegalArgumentException("Volume Group " + audioVolumeGroup.name() + " has no valid audio attributes");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$ensureValidAttributes$11(android.media.AudioAttributes audioAttributes) {
        return !audioAttributes.equals(android.media.audiopolicy.AudioProductStrategy.getDefaultAttributes());
    }

    private void readVolumeGroupsSettings(boolean z) {
        synchronized (this.mSettingsLock) {
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                for (int i = 0; i < sVolumeGroupStates.size(); i++) {
                    try {
                        com.android.server.audio.AudioService.VolumeGroupState valueAt = sVolumeGroupStates.valueAt(i);
                        if (!z || !valueAt.isMusic()) {
                            valueAt.clearIndexCache();
                            valueAt.readSettings();
                        }
                        valueAt.applyAllVolumes(z);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    private void restoreVolumeGroups() {
        synchronized (this.mSettingsLock) {
            for (int i = 0; i < sVolumeGroupStates.size(); i++) {
                try {
                    sVolumeGroupStates.valueAt(i).applyAllVolumes(false);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void dumpVolumeGroups(java.io.PrintWriter printWriter) {
        printWriter.println("\nVolume Groups (device: index)");
        for (int i = 0; i < sVolumeGroupStates.size(); i++) {
            sVolumeGroupStates.valueAt(i).dump(printWriter);
            printWriter.println("");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCallStream(int i) {
        return i == 0 || i == 6;
    }

    private static int getVolumeGroupForStreamType(int i) {
        android.media.AudioAttributes audioAttributesForStrategyWithLegacyStreamType = android.media.audiopolicy.AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i);
        if (audioAttributesForStrategyWithLegacyStreamType.equals(new android.media.AudioAttributes.Builder().build())) {
            return -1;
        }
        return android.media.audiopolicy.AudioProductStrategy.getVolumeGroupIdForAudioAttributes(audioAttributesForStrategyWithLegacyStreamType, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class VolumeGroupState {
        private android.media.AudioAttributes mAudioAttributes;
        private final android.media.audiopolicy.AudioVolumeGroup mAudioVolumeGroup;
        private boolean mHasValidStreamType;
        private final android.util.SparseIntArray mIndexMap;
        private int mIndexMax;
        private int mIndexMin;
        private boolean mIsMuted;
        private int mPublicStreamType;
        private java.lang.String mSettingName;

        private int getDeviceForVolume() {
            return com.android.server.audio.AudioService.this.getDeviceForStream(this.mPublicStreamType);
        }

        private VolumeGroupState(android.media.audiopolicy.AudioVolumeGroup audioVolumeGroup) {
            this.mIndexMap = new android.util.SparseIntArray(8);
            int i = 0;
            this.mHasValidStreamType = false;
            this.mPublicStreamType = 3;
            this.mAudioAttributes = android.media.audiopolicy.AudioProductStrategy.getDefaultAttributes();
            this.mIsMuted = false;
            this.mAudioVolumeGroup = audioVolumeGroup;
            java.util.Iterator it = audioVolumeGroup.getAudioAttributes().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) it.next();
                if (!audioAttributes.equals(this.mAudioAttributes)) {
                    this.mAudioAttributes = audioAttributes;
                    break;
                }
            }
            int[] legacyStreamTypes = this.mAudioVolumeGroup.getLegacyStreamTypes();
            java.lang.String str = "";
            if (legacyStreamTypes.length != 0) {
                int length = legacyStreamTypes.length;
                while (true) {
                    if (i < length) {
                        int i2 = legacyStreamTypes[i];
                        if (i2 == -1 || i2 >= android.media.AudioSystem.getNumStreamTypes()) {
                            i++;
                        } else {
                            this.mPublicStreamType = i2;
                            this.mHasValidStreamType = true;
                            str = android.provider.Settings.System.VOLUME_SETTINGS_INT[this.mPublicStreamType];
                            break;
                        }
                    } else {
                        break;
                    }
                }
                this.mIndexMin = com.android.server.audio.AudioService.MIN_STREAM_VOLUME[this.mPublicStreamType];
                this.mIndexMax = com.android.server.audio.AudioService.MAX_STREAM_VOLUME[this.mPublicStreamType];
            } else if (!audioVolumeGroup.getAudioAttributes().isEmpty()) {
                this.mIndexMin = android.media.AudioSystem.getMinVolumeIndexForAttributes(this.mAudioAttributes);
                this.mIndexMax = android.media.AudioSystem.getMaxVolumeIndexForAttributes(this.mAudioAttributes);
            } else {
                throw new java.lang.IllegalArgumentException("volume group: " + this.mAudioVolumeGroup.name() + " has neither valid attributes nor valid stream types assigned");
            }
            if (str.isEmpty()) {
                str = "volume_" + name();
            }
            this.mSettingName = str;
            readSettings();
        }

        @android.annotation.NonNull
        public int[] getLegacyStreamTypes() {
            return this.mAudioVolumeGroup.getLegacyStreamTypes();
        }

        public java.lang.String name() {
            return this.mAudioVolumeGroup.name();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isVssMuteBijective(int i) {
            return com.android.server.audio.AudioService.this.isStreamAffectedByMute(i) && getMinIndex() == (com.android.server.audio.AudioService.this.mStreamStates[i].mIndexMin + 5) / 10 && (getMinIndex() == 0 || com.android.server.audio.AudioService.isCallStream(i));
        }

        private boolean isMutable() {
            return this.mIndexMin == 0 || (this.mHasValidStreamType && isVssMuteBijective(this.mPublicStreamType));
        }

        @com.android.internal.annotations.GuardedBy({"AudioService.VolumeStreamState.class"})
        public boolean mute(boolean z) {
            if (!isMutable()) {
                return false;
            }
            boolean z2 = this.mIsMuted != z;
            if (z2) {
                this.mIsMuted = z;
                applyAllVolumes(false);
            }
            return z2;
        }

        public boolean isMuted() {
            return this.mIsMuted;
        }

        public void adjustVolume(int i, int i2) {
            synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                    int deviceForVolume = getDeviceForVolume();
                    int index = getIndex(deviceForVolume);
                    if (!com.android.server.audio.AudioService.this.isMuteAdjust(i) || isMutable()) {
                        switch (i) {
                            case -100:
                                if (index != 0) {
                                    mute(true);
                                }
                                this.mIsMuted = true;
                                break;
                            case -1:
                                if (isMuted() && index != 0) {
                                    mute(false);
                                    break;
                                } else {
                                    setVolumeIndex(java.lang.Math.max(index - 1, this.mIndexMin), deviceForVolume, i2);
                                    break;
                                }
                            case 1:
                                setVolumeIndex(java.lang.Math.min(index + 1, this.mIndexMax), deviceForVolume, i2);
                                break;
                            case 100:
                                mute(false);
                                break;
                            case 101:
                                mute(this.mIsMuted ? false : true);
                                break;
                        }
                    }
                }
            }
        }

        public int getVolumeIndex() {
            int index;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                index = getIndex(getDeviceForVolume());
            }
            return index;
        }

        public void setVolumeIndex(int i, int i2) {
            synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                    if (com.android.server.audio.AudioService.this.mUseFixedVolume) {
                        return;
                    }
                    setVolumeIndex(i, getDeviceForVolume(), i2);
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"AudioService.VolumeStreamState.class"})
        private void setVolumeIndex(int i, int i2, int i3) {
            updateVolumeIndex(i, i2);
            if (!mute(i == 0)) {
                setVolumeIndexInt(getValidIndex(i), i2, i3);
            }
        }

        @com.android.internal.annotations.GuardedBy({"AudioService.VolumeStreamState.class"})
        public void updateVolumeIndex(int i, int i2) {
            if (this.mIndexMap.indexOfKey(i2) < 0 || this.mIndexMap.get(i2) != i) {
                this.mIndexMap.put(i2, getValidIndex(i));
                com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 2, 2, i2, 0, this, 500);
            }
        }

        @com.android.internal.annotations.GuardedBy({"AudioService.VolumeStreamState.class"})
        private void setVolumeIndexInt(int i, int i2, int i3) {
            if (this.mHasValidStreamType && isVssMuteBijective(this.mPublicStreamType) && com.android.server.audio.AudioService.this.mStreamStates[this.mPublicStreamType].isFullyMuted()) {
                i = 0;
            } else if (this.mPublicStreamType == 6 && i == 0) {
                i = 1;
            }
            com.android.server.audio.AudioService.this.mAudioSystem.setVolumeIndexForAttributes(this.mAudioAttributes, i, i2);
        }

        @com.android.internal.annotations.GuardedBy({"AudioService.VolumeStreamState.class"})
        private int getIndex(int i) {
            int i2 = this.mIndexMap.get(i, -1);
            return i2 != -1 ? i2 : this.mIndexMap.get(1073741824);
        }

        @com.android.internal.annotations.GuardedBy({"AudioService.VolumeStreamState.class"})
        private boolean hasIndexForDevice(int i) {
            return this.mIndexMap.get(i, -1) != -1;
        }

        public int getMaxIndex() {
            return this.mIndexMax;
        }

        public int getMinIndex() {
            return this.mIndexMin;
        }

        private boolean isValidStream(int i) {
            return i != -1 && i < com.android.server.audio.AudioService.this.mStreamStates.length;
        }

        public boolean isMusic() {
            return this.mHasValidStreamType && this.mPublicStreamType == 3;
        }

        public void applyAllVolumes(boolean z) {
            int i;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                int i2 = 0;
                while (true) {
                    try {
                        i = 1073741824;
                        if (i2 >= this.mIndexMap.size()) {
                            break;
                        }
                        int keyAt = this.mIndexMap.keyAt(i2);
                        int valueAt = this.mIndexMap.valueAt(i2);
                        if (keyAt != 1073741824) {
                            boolean z2 = false;
                            for (int i3 : getLegacyStreamTypes()) {
                                if (isValidStream(i3)) {
                                    boolean z3 = com.android.server.audio.AudioService.this.mStreamStates[i3].mIsMuted;
                                    int deviceForStream = com.android.server.audio.AudioService.this.getDeviceForStream(i3);
                                    int index = (com.android.server.audio.AudioService.this.mStreamStates[i3].getIndex(deviceForStream) + 5) / 10;
                                    if (keyAt == deviceForStream) {
                                        if (index == valueAt && isMuted() == z3 && isVssMuteBijective(i3)) {
                                            z2 = true;
                                        } else {
                                            if (index != valueAt) {
                                                com.android.server.audio.AudioService.this.mStreamStates[i3].setIndex(valueAt * 10, keyAt, "from vgs", true);
                                            }
                                            if (isMuted() != z3 && isVssMuteBijective(i3)) {
                                                com.android.server.audio.AudioService.this.mStreamStates[i3].mute(isMuted(), "VGS.applyAllVolumes#1");
                                            }
                                        }
                                    }
                                }
                            }
                            if (!z2) {
                                if (isMuted()) {
                                    valueAt = 0;
                                }
                                setVolumeIndexInt(valueAt, keyAt, 0);
                            }
                        }
                        i2++;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                int index2 = getIndex(1073741824);
                int deviceForVolume = getDeviceForVolume();
                boolean z4 = z && this.mIndexMap.indexOfKey(deviceForVolume) < 0;
                int[] legacyStreamTypes = getLegacyStreamTypes();
                int length = legacyStreamTypes.length;
                int i4 = 0;
                boolean z5 = false;
                while (i4 < length) {
                    int i5 = legacyStreamTypes[i4];
                    if (isValidStream(i5)) {
                        boolean z6 = com.android.server.audio.AudioService.this.mStreamStates[i5].mIsMuted;
                        int index3 = (com.android.server.audio.AudioService.this.mStreamStates[i5].getIndex(i) + 5) / 10;
                        if (z4) {
                            com.android.server.audio.AudioService.this.mStreamStates[i5].setIndex(index2 * 10, deviceForVolume, "from vgs", true);
                        }
                        if (index3 == index2 && isMuted() == z6 && isVssMuteBijective(i5)) {
                            z5 = true;
                        } else {
                            if (index3 != index2) {
                                com.android.server.audio.AudioService.this.mStreamStates[i5].setIndex(index2 * 10, 1073741824, "from vgs", true);
                            }
                            if (isMuted() != z6 && isVssMuteBijective(i5)) {
                                com.android.server.audio.AudioService.this.mStreamStates[i5].mute(isMuted(), "VGS.applyAllVolumes#2");
                            }
                        }
                    }
                    i4++;
                    i = 1073741824;
                }
                if (!z5) {
                    setVolumeIndexInt(isMuted() ? 0 : index2, 1073741824, 0);
                }
                if (z4) {
                    if (isMuted()) {
                        index2 = 0;
                    }
                    setVolumeIndexInt(index2, deviceForVolume, 0);
                }
            }
        }

        public void clearIndexCache() {
            this.mIndexMap.clear();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void persistVolumeGroup(int i) {
            if (com.android.server.audio.AudioService.this.mUseFixedVolume || this.mHasValidStreamType) {
                return;
            }
            if (!com.android.server.audio.AudioService.this.mSettings.putSystemIntForUser(com.android.server.audio.AudioService.this.mContentResolver, getSettingNameForDevice(i), getIndex(i), isMusic() ? 0 : -2)) {
                android.util.Log.e(com.android.server.audio.AudioService.TAG, "persistVolumeGroup failed for group " + this.mAudioVolumeGroup.name());
            }
        }

        public void readSettings() {
            int i;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                try {
                    if (com.android.server.audio.AudioService.this.mUseFixedVolume) {
                        this.mIndexMap.put(1073741824, this.mIndexMax);
                        return;
                    }
                    java.util.Iterator it = android.media.AudioSystem.DEVICE_OUT_ALL_SET.iterator();
                    while (it.hasNext()) {
                        int intValue = ((java.lang.Integer) it.next()).intValue();
                        if (intValue != 1073741824) {
                            i = -1;
                        } else {
                            i = android.media.AudioSystem.DEFAULT_STREAM_VOLUME[this.mPublicStreamType];
                        }
                        int systemIntForUser = com.android.server.audio.AudioService.this.mSettings.getSystemIntForUser(com.android.server.audio.AudioService.this.mContentResolver, getSettingNameForDevice(intValue), i, isMusic() ? 0 : -2);
                        if (systemIntForUser != -1) {
                            if (this.mPublicStreamType == 7 && com.android.server.audio.AudioService.this.mCameraSoundForced) {
                                systemIntForUser = this.mIndexMax;
                            }
                            this.mIndexMap.put(intValue, getValidIndex(systemIntForUser));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"AudioService.VolumeStreamState.class"})
        private int getValidIndex(int i) {
            if (i < this.mIndexMin) {
                return this.mIndexMin;
            }
            if (com.android.server.audio.AudioService.this.mUseFixedVolume || i > this.mIndexMax) {
                return this.mIndexMax;
            }
            return i;
        }

        @android.annotation.NonNull
        public java.lang.String getSettingNameForDevice(int i) {
            if (android.media.AudioSystem.getOutputDeviceName(i).isEmpty()) {
                return this.mSettingName;
            }
            return this.mSettingName + "_" + android.media.AudioSystem.getOutputDeviceName(i);
        }

        void setSettingName(java.lang.String str) {
            this.mSettingName = str;
        }

        java.lang.String getSettingName() {
            return this.mSettingName;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(final java.io.PrintWriter printWriter) {
            printWriter.println("- VOLUME GROUP " + this.mAudioVolumeGroup.name() + ":");
            printWriter.print("   Muted: ");
            printWriter.println(this.mIsMuted);
            printWriter.print("   Min: ");
            printWriter.println(this.mIndexMin);
            printWriter.print("   Max: ");
            printWriter.println(this.mIndexMax);
            printWriter.print("   Current: ");
            int i = 0;
            for (int i2 = 0; i2 < this.mIndexMap.size(); i2++) {
                if (i2 > 0) {
                    printWriter.print(", ");
                }
                int keyAt = this.mIndexMap.keyAt(i2);
                printWriter.print(java.lang.Integer.toHexString(keyAt));
                java.lang.String outputDeviceName = keyAt == 1073741824 ? "default" : android.media.AudioSystem.getOutputDeviceName(keyAt);
                if (!outputDeviceName.isEmpty()) {
                    printWriter.print(" (");
                    printWriter.print(outputDeviceName);
                    printWriter.print(")");
                }
                printWriter.print(": ");
                printWriter.print(this.mIndexMap.valueAt(i2));
            }
            printWriter.println();
            printWriter.print("   Devices: ");
            int deviceForVolume = getDeviceForVolume();
            java.util.Iterator it = android.media.AudioSystem.DEVICE_OUT_ALL_SET.iterator();
            while (it.hasNext()) {
                int intValue = ((java.lang.Integer) it.next()).intValue();
                if ((deviceForVolume & intValue) == intValue) {
                    int i3 = i + 1;
                    if (i > 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print(android.media.AudioSystem.getOutputDeviceName(intValue));
                    i = i3;
                }
            }
            printWriter.println();
            printWriter.print("   Streams: ");
            java.util.Arrays.stream(getLegacyStreamTypes()).forEach(new java.util.function.IntConsumer() { // from class: com.android.server.audio.AudioService$VolumeGroupState$$ExternalSyntheticLambda0
                @Override // java.util.function.IntConsumer
                public final void accept(int i4) {
                    com.android.server.audio.AudioService.VolumeGroupState.lambda$dump$0(printWriter, i4);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$dump$0(java.io.PrintWriter printWriter, int i) {
            printWriter.print(android.media.AudioSystem.streamToString(i) + " ");
        }
    }

    class VolumeStreamState {
        private final android.util.SparseIntArray mIndexMap;
        private int mIndexMax;
        private int mIndexMin;
        private int mIndexMinNoPerm;
        private boolean mIsMuted;
        private boolean mIsMutedInternally;

        @android.annotation.NonNull
        private java.util.Set<java.lang.Integer> mObservedDeviceSet;
        private final android.content.Intent mStreamDevicesChanged;
        private final android.os.Bundle mStreamDevicesChangedOptions;
        private final int mStreamType;
        private final android.content.Intent mVolumeChanged;
        private final android.os.Bundle mVolumeChangedOptions;
        private com.android.server.audio.AudioService.VolumeGroupState mVolumeGroupState;
        private java.lang.String mVolumeIndexSettingName;

        private VolumeStreamState(java.lang.String str, int i) {
            this.mVolumeGroupState = null;
            this.mIsMuted = false;
            this.mIsMutedInternally = false;
            this.mObservedDeviceSet = new java.util.TreeSet();
            this.mIndexMap = new android.util.SparseIntArray(8) { // from class: com.android.server.audio.AudioService.VolumeStreamState.1
                @Override // android.util.SparseIntArray
                public void put(int i2, int i3) {
                    super.put(i2, i3);
                    record("put", i2, i3);
                }

                @Override // android.util.SparseIntArray
                public void setValueAt(int i2, int i3) {
                    super.setValueAt(i2, i3);
                    record("setValueAt", keyAt(i2), i3);
                }

                private void record(java.lang.String str2, int i2, int i3) {
                    new android.media.MediaMetrics.Item("audio.volume." + android.media.AudioSystem.streamToString(com.android.server.audio.AudioService.VolumeStreamState.this.mStreamType) + "." + (i2 == 1073741824 ? "default" : android.media.AudioSystem.getOutputDeviceName(i2))).set(android.media.MediaMetrics.Property.EVENT, str2).set(android.media.MediaMetrics.Property.INDEX, java.lang.Integer.valueOf(i3)).set(android.media.MediaMetrics.Property.MIN_INDEX, java.lang.Integer.valueOf(com.android.server.audio.AudioService.VolumeStreamState.this.mIndexMin)).set(android.media.MediaMetrics.Property.MAX_INDEX, java.lang.Integer.valueOf(com.android.server.audio.AudioService.VolumeStreamState.this.mIndexMax)).record();
                }
            };
            this.mVolumeIndexSettingName = str;
            this.mStreamType = i;
            this.mIndexMin = com.android.server.audio.AudioService.MIN_STREAM_VOLUME[i] * 10;
            this.mIndexMinNoPerm = this.mIndexMin;
            this.mIndexMax = com.android.server.audio.AudioService.MAX_STREAM_VOLUME[i] * 10;
            int initStreamVolume = android.media.AudioSystem.initStreamVolume(i, this.mIndexMin / 10, this.mIndexMax / 10);
            if (initStreamVolume != 0) {
                com.android.server.audio.AudioService.sLifecycleLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("VSS() stream:" + i + " initStreamVolume=" + initStreamVolume).printLog(1, com.android.server.audio.AudioService.TAG));
                com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 34, 1, 0, 0, "VSS()", 2000);
            }
            readSettings();
            this.mVolumeChanged = new android.content.Intent("android.media.VOLUME_CHANGED_ACTION");
            this.mVolumeChanged.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", this.mStreamType);
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setDeliveryGroupPolicy(1);
            makeBasic.setDeliveryGroupMatchingKey("android.media.VOLUME_CHANGED_ACTION", java.lang.String.valueOf(this.mStreamType));
            makeBasic.setDeferralPolicy(2);
            this.mVolumeChangedOptions = makeBasic.toBundle();
            this.mStreamDevicesChanged = new android.content.Intent("android.media.STREAM_DEVICES_CHANGED_ACTION");
            this.mStreamDevicesChanged.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", this.mStreamType);
            android.app.BroadcastOptions makeBasic2 = android.app.BroadcastOptions.makeBasic();
            makeBasic2.setDeliveryGroupPolicy(1);
            makeBasic2.setDeliveryGroupMatchingKey("android.media.STREAM_DEVICES_CHANGED_ACTION", java.lang.String.valueOf(this.mStreamType));
            makeBasic2.setDeferralPolicy(2);
            this.mStreamDevicesChangedOptions = makeBasic2.toBundle();
        }

        public void setVolumeGroupState(com.android.server.audio.AudioService.VolumeGroupState volumeGroupState) {
            this.mVolumeGroupState = volumeGroupState;
            if (this.mVolumeGroupState != null) {
                this.mVolumeGroupState.setSettingName(this.mVolumeIndexSettingName);
            }
        }

        public void updateNoPermMinIndex(int i) {
            this.mIndexMinNoPerm = i * 10;
            if (this.mIndexMinNoPerm < this.mIndexMin) {
                android.util.Log.e(com.android.server.audio.AudioService.TAG, "Invalid mIndexMinNoPerm for stream " + this.mStreamType);
                this.mIndexMinNoPerm = this.mIndexMin;
            }
        }

        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"VolumeStreamState.class"})
        public java.util.Set<java.lang.Integer> observeDevicesForStream_syncVSS(boolean z) {
            if (!com.android.server.audio.AudioService.this.mSystemServer.isPrivileged()) {
                return new java.util.TreeSet();
            }
            java.util.Set<java.lang.Integer> deviceSetForStreamDirect = com.android.server.audio.AudioService.this.getDeviceSetForStreamDirect(this.mStreamType);
            if (deviceSetForStreamDirect.equals(this.mObservedDeviceSet)) {
                return this.mObservedDeviceSet;
            }
            int deviceMaskFromSet = android.media.AudioSystem.getDeviceMaskFromSet(deviceSetForStreamDirect);
            int deviceMaskFromSet2 = android.media.AudioSystem.getDeviceMaskFromSet(this.mObservedDeviceSet);
            this.mObservedDeviceSet = deviceSetForStreamDirect;
            if (z) {
                com.android.server.audio.AudioService.this.postObserveDevicesForAllStreams(this.mStreamType);
            }
            if (com.android.server.audio.AudioService.mStreamVolumeAlias[this.mStreamType] == this.mStreamType) {
                com.android.server.EventLogTags.writeStreamDevicesChanged(this.mStreamType, deviceMaskFromSet2, deviceMaskFromSet);
            }
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = this.mStreamDevicesChanged;
            obtain.arg2 = this.mStreamDevicesChangedOptions;
            com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 32, 2, deviceMaskFromSet2, deviceMaskFromSet, obtain, 0);
            return this.mObservedDeviceSet;
        }

        @android.annotation.Nullable
        public java.lang.String getSettingNameForDevice(int i) {
            if (!hasValidSettingsName()) {
                return null;
            }
            java.lang.String outputDeviceName = android.media.AudioSystem.getOutputDeviceName(i);
            if (outputDeviceName.isEmpty()) {
                return this.mVolumeIndexSettingName;
            }
            return this.mVolumeIndexSettingName + "_" + outputDeviceName;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasValidSettingsName() {
            return (this.mVolumeIndexSettingName == null || this.mVolumeIndexSettingName.isEmpty()) ? false : true;
        }

        void setSettingName(java.lang.String str) {
            this.mVolumeIndexSettingName = str;
            if (this.mVolumeGroupState != null) {
                this.mVolumeGroupState.setSettingName(this.mVolumeIndexSettingName);
            }
        }

        java.lang.String getSettingName() {
            return this.mVolumeIndexSettingName;
        }

        public void readSettings() {
            synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                    if (com.android.server.audio.AudioService.this.mUseFixedVolume) {
                        this.mIndexMap.put(1073741824, this.mIndexMax);
                        return;
                    }
                    if (this.mStreamType == 1 || this.mStreamType == 7) {
                        int i = android.media.AudioSystem.DEFAULT_STREAM_VOLUME[this.mStreamType] * 10;
                        if (com.android.server.audio.AudioService.this.mCameraSoundForced) {
                            i = this.mIndexMax;
                        }
                        this.mIndexMap.put(1073741824, i);
                        return;
                    }
                    synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                        try {
                            java.util.Iterator it = android.media.AudioSystem.DEVICE_OUT_ALL_SET.iterator();
                            while (it.hasNext()) {
                                int intValue = ((java.lang.Integer) it.next()).intValue();
                                int i2 = intValue == 1073741824 ? android.media.AudioSystem.DEFAULT_STREAM_VOLUME[this.mStreamType] : -1;
                                if (hasValidSettingsName()) {
                                    i2 = com.android.server.audio.AudioService.this.mSettings.getSystemIntForUser(com.android.server.audio.AudioService.this.mContentResolver, getSettingNameForDevice(intValue), i2, -2);
                                }
                                if (i2 != -1) {
                                    this.mIndexMap.put(intValue, getValidIndex(i2 * 10, true));
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
        }

        private int getAbsoluteVolumeIndex(int i) {
            if (i == 0) {
                return 0;
            }
            com.android.media.audio.Flags.disablePrescaleAbsoluteVolume();
            return (this.mIndexMax + 5) / 10;
        }

        private void setStreamVolumeIndex(int i, int i2) {
            if (this.mStreamType == 6 && i == 0 && !isFullyMuted()) {
                i = 1;
            }
            com.android.server.audio.AudioService.this.mAudioSystem.setStreamVolumeIndexAS(this.mStreamType, i, i2);
        }

        void applyDeviceVolume_syncVSS(int i) {
            int absoluteVolumeIndex;
            if (isFullyMuted()) {
                absoluteVolumeIndex = 0;
            } else if (com.android.server.audio.AudioService.this.isAbsoluteVolumeDevice(i) || com.android.server.audio.AudioService.this.isA2dpAbsoluteVolumeDevice(i) || android.media.AudioSystem.isLeAudioDeviceType(i)) {
                absoluteVolumeIndex = getAbsoluteVolumeIndex((getIndex(i) + 5) / 10);
            } else if (com.android.server.audio.AudioService.this.isFullVolumeDevice(i)) {
                absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
            } else if (i == 134217728) {
                absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
            } else {
                absoluteVolumeIndex = (getIndex(i) + 5) / 10;
            }
            setStreamVolumeIndex(absoluteVolumeIndex, i);
        }

        public void applyAllVolumes() {
            int absoluteVolumeIndex;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                int i = 0;
                boolean z = false;
                for (int i2 = 0; i2 < this.mIndexMap.size(); i2++) {
                    try {
                        int keyAt = this.mIndexMap.keyAt(i2);
                        if (keyAt != 1073741824) {
                            if (isFullyMuted()) {
                                absoluteVolumeIndex = 0;
                            } else {
                                if (!com.android.server.audio.AudioService.this.isAbsoluteVolumeDevice(keyAt) && !com.android.server.audio.AudioService.this.isA2dpAbsoluteVolumeDevice(keyAt) && !android.media.AudioSystem.isLeAudioDeviceType(keyAt)) {
                                    if (com.android.server.audio.AudioService.this.isFullVolumeDevice(keyAt)) {
                                        absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
                                    } else if (keyAt == 134217728) {
                                        absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
                                    } else {
                                        absoluteVolumeIndex = (this.mIndexMap.valueAt(i2) + 5) / 10;
                                    }
                                }
                                absoluteVolumeIndex = getAbsoluteVolumeIndex((getIndex(keyAt) + 5) / 10);
                                z = true;
                            }
                            com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 1006, 0, keyAt, z ? 1 : 0, this, 0);
                            setStreamVolumeIndex(absoluteVolumeIndex, keyAt);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (!isFullyMuted()) {
                    i = (getIndex(1073741824) + 5) / 10;
                }
                setStreamVolumeIndex(i, 1073741824);
            }
        }

        public boolean adjustIndex(int i, int i2, java.lang.String str, boolean z) {
            return setIndex(getIndex(i2) + i, i2, str, z);
        }

        public boolean setIndex(int i, int i2, java.lang.String str, boolean z) {
            int index;
            int i3;
            boolean z2;
            boolean z3;
            synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                    try {
                        index = getIndex(i2);
                        int validIndex = getValidIndex(i, z);
                        if (this.mStreamType == 7 && com.android.server.audio.AudioService.this.mCameraSoundForced) {
                            i3 = this.mIndexMax;
                        } else {
                            i3 = validIndex;
                        }
                        this.mIndexMap.put(i2, i3);
                        z2 = index != i3;
                        z3 = i2 == com.android.server.audio.AudioService.this.getDeviceForStream(this.mStreamType);
                        for (int numStreamTypes = android.media.AudioSystem.getNumStreamTypes() - 1; numStreamTypes >= 0; numStreamTypes--) {
                            com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = com.android.server.audio.AudioService.this.mStreamStates[numStreamTypes];
                            if (numStreamTypes != this.mStreamType && com.android.server.audio.AudioService.mStreamVolumeAlias[numStreamTypes] == this.mStreamType && (z2 || !volumeStreamState.hasIndexForDevice(i2))) {
                                int rescaleIndex = com.android.server.audio.AudioService.this.rescaleIndex(validIndex, this.mStreamType, numStreamTypes);
                                volumeStreamState.setIndex(rescaleIndex, i2, str, z);
                                if (z3) {
                                    volumeStreamState.setIndex(rescaleIndex, com.android.server.audio.AudioService.this.getDeviceForStream(numStreamTypes), str, z);
                                }
                            }
                        }
                        if (z2 && this.mStreamType == 2 && i2 == 2) {
                            for (int i4 = 0; i4 < this.mIndexMap.size(); i4++) {
                                int keyAt = this.mIndexMap.keyAt(i4);
                                if (android.media.AudioSystem.DEVICE_OUT_ALL_SCO_SET.contains(java.lang.Integer.valueOf(keyAt))) {
                                    this.mIndexMap.put(keyAt, i3);
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            if (z2) {
                updateVolumeGroupIndex(i2, false);
                int i5 = (index + 5) / 10;
                int i6 = (i3 + 5) / 10;
                if (com.android.server.audio.AudioService.mStreamVolumeAlias[this.mStreamType] == this.mStreamType) {
                    if (str == null) {
                        android.util.Log.w(com.android.server.audio.AudioService.TAG, "No caller for volume_changed event", new java.lang.Throwable());
                    }
                    com.android.server.EventLogTags.writeVolumeChanged(this.mStreamType, i5, i6, this.mIndexMax / 10, str);
                }
                if (i6 != i5 && z3 && (!com.android.server.audio.AudioService.this.mIsSingleVolume || com.android.server.audio.AudioService.mStreamVolumeAlias[this.mStreamType] == this.mStreamType)) {
                    this.mVolumeChanged.putExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", i6);
                    this.mVolumeChanged.putExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", i5);
                    this.mVolumeChanged.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE_ALIAS", com.android.server.audio.AudioService.mStreamVolumeAlias[this.mStreamType]);
                    com.android.server.audio.AudioService.sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolChangedBroadcastEvent(this.mStreamType, com.android.server.audio.AudioService.mStreamVolumeAlias[this.mStreamType], i6, i5));
                    com.android.server.audio.AudioService.this.sendBroadcastToAll(this.mVolumeChanged, this.mVolumeChangedOptions);
                }
            }
            return z2;
        }

        public int getIndex(int i) {
            int i2;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                try {
                    i2 = this.mIndexMap.get(i, -1);
                    if (i2 == -1) {
                        i2 = this.mIndexMap.get(1073741824);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return i2;
        }

        @android.annotation.NonNull
        public android.media.VolumeInfo getVolumeInfo(int i) {
            android.media.VolumeInfo build;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                try {
                    int i2 = this.mIndexMap.get(i, -1);
                    if (i2 == -1) {
                        i2 = this.mIndexMap.get(1073741824);
                    }
                    build = new android.media.VolumeInfo.Builder(this.mStreamType).setMinVolumeIndex(this.mIndexMin).setMaxVolumeIndex(this.mIndexMax).setVolumeIndex(i2).setMuted(isFullyMuted()).build();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return build;
        }

        public boolean hasIndexForDevice(int i) {
            boolean z;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                z = this.mIndexMap.get(i, -1) != -1;
            }
            return z;
        }

        public int getMaxIndex() {
            return this.mIndexMax;
        }

        public int getMinIndex() {
            return this.mIndexMin;
        }

        public int getMinIndex(boolean z) {
            return z ? this.mIndexMin : this.mIndexMinNoPerm;
        }

        @com.android.internal.annotations.GuardedBy({"VolumeStreamState.class"})
        public void setAllIndexes(com.android.server.audio.AudioService.VolumeStreamState volumeStreamState, java.lang.String str) {
            if (this.mStreamType == volumeStreamState.mStreamType) {
                return;
            }
            int streamType = volumeStreamState.getStreamType();
            int rescaleIndex = com.android.server.audio.AudioService.this.rescaleIndex(volumeStreamState.getIndex(1073741824), streamType, this.mStreamType);
            for (int i = 0; i < this.mIndexMap.size(); i++) {
                this.mIndexMap.put(this.mIndexMap.keyAt(i), rescaleIndex);
            }
            android.util.SparseIntArray sparseIntArray = volumeStreamState.mIndexMap;
            for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                setIndex(com.android.server.audio.AudioService.this.rescaleIndex(sparseIntArray.valueAt(i2), streamType, this.mStreamType), sparseIntArray.keyAt(i2), str, true);
            }
        }

        @com.android.internal.annotations.GuardedBy({"VolumeStreamState.class"})
        public void setAllIndexesToMax() {
            for (int i = 0; i < this.mIndexMap.size(); i++) {
                this.mIndexMap.put(this.mIndexMap.keyAt(i), this.mIndexMax);
            }
        }

        private void updateVolumeGroupIndex(int i, boolean z) {
            boolean z2;
            synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                    try {
                        if (this.mVolumeGroupState != null) {
                            int index = (getIndex(i) + 5) / 10;
                            this.mVolumeGroupState.updateVolumeIndex(index, i);
                            if (isMutable()) {
                                com.android.server.audio.AudioService.VolumeGroupState volumeGroupState = this.mVolumeGroupState;
                                if (z) {
                                    z2 = this.mIsMuted;
                                } else {
                                    z2 = (index == 0 && !com.android.server.audio.AudioService.isCallStream(this.mStreamType)) || this.mIsMuted;
                                }
                                volumeGroupState.mute(z2);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public boolean mute(boolean z, java.lang.String str) {
            boolean mute;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                mute = mute(z, true, str);
            }
            if (mute) {
                com.android.server.audio.AudioService.this.broadcastMuteSetting(this.mStreamType, z);
            }
            return mute;
        }

        public boolean muteInternally(boolean z) {
            boolean z2;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                try {
                    if (z == this.mIsMutedInternally) {
                        z2 = false;
                    } else {
                        this.mIsMutedInternally = z;
                        applyAllVolumes();
                        z2 = true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z2) {
                com.android.server.audio.AudioService.sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(9, this.mStreamType, z));
            }
            return z2;
        }

        @com.android.internal.annotations.GuardedBy({"VolumeStreamState.class"})
        public boolean isFullyMuted() {
            return this.mIsMuted || this.mIsMutedInternally;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isMutable() {
            return com.android.server.audio.AudioService.this.isStreamAffectedByMute(this.mStreamType) && (this.mIndexMin == 0 || com.android.server.audio.AudioService.isCallStream(this.mStreamType));
        }

        public boolean mute(boolean z, boolean z2, java.lang.String str) {
            boolean z3;
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                try {
                    z3 = z != this.mIsMuted;
                    if (z3) {
                        com.android.server.audio.AudioService.sMuteLogger.enqueue(new com.android.server.audio.AudioServiceEvents.StreamMuteEvent(this.mStreamType, z, str));
                        if (!z && com.android.server.audio.AudioService.this.isStreamMutedByRingerOrZenMode(this.mStreamType)) {
                            android.util.Log.e(com.android.server.audio.AudioService.TAG, "Unmuting stream " + this.mStreamType + " despite ringer-zen muted stream 0x" + java.lang.Integer.toHexString(com.android.server.audio.AudioService.sRingerAndZenModeMutedStreams), new java.lang.Exception());
                            com.android.server.audio.AudioService.sMuteLogger.enqueue(new com.android.server.audio.AudioServiceEvents.StreamUnmuteErrorEvent(this.mStreamType, com.android.server.audio.AudioService.sRingerAndZenModeMutedStreams));
                        }
                        this.mIsMuted = z;
                        if (z2) {
                            doMute();
                        }
                    }
                } finally {
                }
            }
            return z3;
        }

        public void doMute() {
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                updateVolumeGroupIndex(com.android.server.audio.AudioService.this.getDeviceForStream(this.mStreamType), true);
                com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 10, 2, 0, 0, this, 0);
            }
        }

        public int getStreamType() {
            return this.mStreamType;
        }

        public void checkFixedVolumeDevices() {
            synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                try {
                    if (com.android.server.audio.AudioService.mStreamVolumeAlias[this.mStreamType] == 3) {
                        for (int i = 0; i < this.mIndexMap.size(); i++) {
                            int keyAt = this.mIndexMap.keyAt(i);
                            int valueAt = this.mIndexMap.valueAt(i);
                            if (!com.android.server.audio.AudioService.this.isFullVolumeDevice(keyAt)) {
                                if (com.android.server.audio.AudioService.this.isFixedVolumeDevice(keyAt) && valueAt != 0) {
                                }
                                applyDeviceVolume_syncVSS(keyAt);
                            }
                            this.mIndexMap.put(keyAt, this.mIndexMax);
                            applyDeviceVolume_syncVSS(keyAt);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private int getValidIndex(int i, boolean z) {
            int i2 = z ? this.mIndexMin : this.mIndexMinNoPerm;
            if (i < i2) {
                return i2;
            }
            if (com.android.server.audio.AudioService.this.mUseFixedVolume || i > this.mIndexMax) {
                return this.mIndexMax;
            }
            return i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(java.io.PrintWriter printWriter) {
            printWriter.print("   Muted: ");
            printWriter.println(this.mIsMuted);
            printWriter.print("   Muted Internally: ");
            printWriter.println(this.mIsMutedInternally);
            printWriter.print("   Min: ");
            printWriter.print((this.mIndexMin + 5) / 10);
            if (this.mIndexMin != this.mIndexMinNoPerm) {
                printWriter.print(" w/o perm:");
                printWriter.println((this.mIndexMinNoPerm + 5) / 10);
            } else {
                printWriter.println();
            }
            printWriter.print("   Max: ");
            printWriter.println((this.mIndexMax + 5) / 10);
            printWriter.print("   streamVolume:");
            printWriter.println(com.android.server.audio.AudioService.this.getStreamVolume(this.mStreamType));
            printWriter.print("   Current: ");
            for (int i = 0; i < this.mIndexMap.size(); i++) {
                if (i > 0) {
                    printWriter.print(", ");
                }
                int keyAt = this.mIndexMap.keyAt(i);
                printWriter.print(java.lang.Integer.toHexString(keyAt));
                java.lang.String outputDeviceName = keyAt == 1073741824 ? "default" : android.media.AudioSystem.getOutputDeviceName(keyAt);
                if (!outputDeviceName.isEmpty()) {
                    printWriter.print(" (");
                    printWriter.print(outputDeviceName);
                    printWriter.print(")");
                }
                printWriter.print(": ");
                printWriter.print((this.mIndexMap.valueAt(i) + 5) / 10);
            }
            printWriter.println();
            printWriter.print("   Devices: ");
            printWriter.print(android.media.AudioSystem.deviceSetToString(com.android.server.audio.AudioService.this.getDeviceSetForStream(this.mStreamType)));
            printWriter.println();
            printWriter.print("   Volume Group: ");
            printWriter.println(this.mVolumeGroupState != null ? this.mVolumeGroupState.name() : "n/a");
        }
    }

    private class AudioSystemThread extends java.lang.Thread {
        AudioSystemThread() {
            super("AudioService");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.os.Looper.prepare();
            synchronized (com.android.server.audio.AudioService.this) {
                com.android.server.audio.AudioService.this.mAudioHandler = com.android.server.audio.AudioService.this.new AudioHandler();
                com.android.server.audio.AudioService.this.notify();
            }
            android.os.Looper.loop();
        }
    }

    private static final class DeviceVolumeUpdate {
        private static final int NO_NEW_INDEX = -2049;

        @android.annotation.NonNull
        final java.lang.String mCaller;
        final int mDevice;
        final int mStreamType;
        private final int mVssVolIndex;

        DeviceVolumeUpdate(int i, int i2, int i3, @android.annotation.NonNull java.lang.String str) {
            this.mStreamType = i;
            this.mVssVolIndex = i2;
            this.mDevice = i3;
            this.mCaller = str;
        }

        DeviceVolumeUpdate(int i, int i2, @android.annotation.NonNull java.lang.String str) {
            this.mStreamType = i;
            this.mVssVolIndex = NO_NEW_INDEX;
            this.mDevice = i2;
            this.mCaller = str;
        }

        boolean hasVolumeIndex() {
            return this.mVssVolIndex != NO_NEW_INDEX;
        }

        int getVolumeIndex() throws java.lang.IllegalStateException {
            com.android.internal.util.Preconditions.checkState(this.mVssVolIndex != NO_NEW_INDEX);
            return this.mVssVolIndex;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void postSetVolumeIndexOnDevice(int i, int i2, int i3, java.lang.String str) {
        sendMsg(this.mAudioHandler, 26, 2, 0, 0, new com.android.server.audio.AudioService.DeviceVolumeUpdate(i, i2, i3, str), 0);
    }

    void postApplyVolumeOnDevice(int i, int i2, @android.annotation.NonNull java.lang.String str) {
        sendMsg(this.mAudioHandler, 26, 2, 0, 0, new com.android.server.audio.AudioService.DeviceVolumeUpdate(i, i2, str), 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSetVolumeIndexOnDevice(@android.annotation.NonNull com.android.server.audio.AudioService.DeviceVolumeUpdate deviceVolumeUpdate) {
        com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[deviceVolumeUpdate.mStreamType];
        if (deviceVolumeUpdate.hasVolumeIndex()) {
            int volumeIndex = deviceVolumeUpdate.getVolumeIndex();
            if (this.mSoundDoseHelper.checkSafeMediaVolume(deviceVolumeUpdate.mStreamType, volumeIndex, deviceVolumeUpdate.mDevice)) {
                volumeIndex = this.mSoundDoseHelper.safeMediaVolumeIndex(deviceVolumeUpdate.mDevice);
            }
            volumeStreamState.setIndex(volumeIndex, deviceVolumeUpdate.mDevice, deviceVolumeUpdate.mCaller, true);
            sVolumeLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(deviceVolumeUpdate.mCaller + " dev:0x" + java.lang.Integer.toHexString(deviceVolumeUpdate.mDevice) + " volIdx:" + volumeIndex));
        } else {
            sVolumeLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(deviceVolumeUpdate.mCaller + " update vol on dev:0x" + java.lang.Integer.toHexString(deviceVolumeUpdate.mDevice)));
        }
        setDeviceVolume(volumeStreamState, deviceVolumeUpdate.mDevice);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0034 A[Catch: all -> 0x001d, TryCatch #0 {all -> 0x001d, blocks: (B:4:0x0003, B:6:0x000d, B:8:0x0013, B:12:0x0020, B:14:0x0034, B:16:0x003a, B:18:0x0044, B:20:0x004a, B:22:0x0050, B:24:0x0056, B:26:0x005c, B:27:0x0063, B:29:0x006a, B:33:0x006d), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setDeviceVolume(com.android.server.audio.AudioService.VolumeStreamState volumeStreamState, int i) {
        int i2;
        int numStreamTypes;
        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
            try {
                com.android.server.audio.AudioService.AudioHandler audioHandler = this.mAudioHandler;
                if (!isAbsoluteVolumeDevice(i) && !isA2dpAbsoluteVolumeDevice(i) && !android.media.AudioSystem.isLeAudioDeviceType(i)) {
                    i2 = 0;
                    sendMsg(audioHandler, 1006, 0, i, i2, volumeStreamState, 0);
                    volumeStreamState.applyDeviceVolume_syncVSS(i);
                    for (numStreamTypes = android.media.AudioSystem.getNumStreamTypes() - 1; numStreamTypes >= 0; numStreamTypes--) {
                        if (numStreamTypes != volumeStreamState.mStreamType && mStreamVolumeAlias[numStreamTypes] == volumeStreamState.mStreamType) {
                            int deviceForStream = getDeviceForStream(numStreamTypes);
                            if (i != deviceForStream && (isAbsoluteVolumeDevice(i) || isA2dpAbsoluteVolumeDevice(i) || android.media.AudioSystem.isLeAudioDeviceType(i))) {
                                this.mStreamStates[numStreamTypes].applyDeviceVolume_syncVSS(i);
                            }
                            this.mStreamStates[numStreamTypes].applyDeviceVolume_syncVSS(deviceForStream);
                        }
                    }
                }
                i2 = 1;
                sendMsg(audioHandler, 1006, 0, i, i2, volumeStreamState, 0);
                volumeStreamState.applyDeviceVolume_syncVSS(i);
                while (numStreamTypes >= 0) {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        sendMsg(this.mAudioHandler, 1, 2, i, 0, volumeStreamState, 500);
    }

    class AudioHandler extends android.os.Handler {
        AudioHandler() {
        }

        AudioHandler(android.os.Looper looper) {
            super(looper);
        }

        private void setAllVolumes(com.android.server.audio.AudioService.VolumeStreamState volumeStreamState) {
            volumeStreamState.applyAllVolumes();
            for (int numStreamTypes = android.media.AudioSystem.getNumStreamTypes() - 1; numStreamTypes >= 0; numStreamTypes--) {
                if (numStreamTypes != volumeStreamState.mStreamType && com.android.server.audio.AudioService.mStreamVolumeAlias[numStreamTypes] == volumeStreamState.mStreamType) {
                    com.android.server.audio.AudioService.this.mStreamStates[numStreamTypes].applyAllVolumes();
                }
            }
        }

        private void persistVolume(com.android.server.audio.AudioService.VolumeStreamState volumeStreamState, int i) {
            if (com.android.server.audio.AudioService.this.mUseFixedVolume) {
                return;
            }
            if ((!com.android.server.audio.AudioService.this.mIsSingleVolume || volumeStreamState.mStreamType == 3) && volumeStreamState.mStreamType != 7 && volumeStreamState.hasValidSettingsName()) {
                com.android.server.audio.AudioService.this.mSettings.putSystemIntForUser(com.android.server.audio.AudioService.this.mContentResolver, volumeStreamState.getSettingNameForDevice(i), (volumeStreamState.getIndex(i) + 5) / 10, -2);
            }
        }

        private void persistRingerMode(int i) {
            if (com.android.server.audio.AudioService.this.mUseFixedVolume) {
                return;
            }
            com.android.server.audio.AudioService.this.mSettings.putGlobalInt(com.android.server.audio.AudioService.this.mContentResolver, "mode_ringer", i);
        }

        private void onNotifyVolumeEvent(@android.annotation.NonNull android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) {
            try {
                iAudioPolicyCallback.notifyVolumeAdjust(i);
            } catch (java.lang.Exception e) {
            }
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    com.android.server.audio.AudioService.this.setDeviceVolume((com.android.server.audio.AudioService.VolumeStreamState) message.obj, message.arg1);
                    return;
                case 1:
                    persistVolume((com.android.server.audio.AudioService.VolumeStreamState) message.obj, message.arg1);
                    return;
                case 2:
                    ((com.android.server.audio.AudioService.VolumeGroupState) message.obj).persistVolumeGroup(message.arg1);
                    return;
                case 3:
                    persistRingerMode(com.android.server.audio.AudioService.this.getRingerModeInternal());
                    return;
                case 4:
                    com.android.server.audio.AudioService.this.onAudioServerDied();
                    return;
                case 5:
                    com.android.server.audio.AudioService.this.mSfxHelper.playSoundEffect(message.arg1, message.arg2);
                    return;
                case 7:
                    com.android.server.audio.AudioService.LoadSoundEffectReply loadSoundEffectReply = (com.android.server.audio.AudioService.LoadSoundEffectReply) message.obj;
                    if (com.android.server.audio.AudioService.this.mSystemReady) {
                        com.android.server.audio.AudioService.this.mSfxHelper.loadSoundEffects(loadSoundEffectReply);
                        return;
                    }
                    android.util.Log.w(com.android.server.audio.AudioService.TAG, "[schedule]loadSoundEffects() called before boot complete");
                    if (loadSoundEffectReply != null) {
                        loadSoundEffectReply.run(false);
                        return;
                    }
                    return;
                case 8:
                    java.lang.String str = (java.lang.String) message.obj;
                    int i = message.arg1;
                    int i2 = message.arg2;
                    if (i == 1) {
                        android.util.Log.wtf(com.android.server.audio.AudioService.TAG, "Invalid force use FOR_MEDIA in AudioService from " + str);
                        return;
                    }
                    new android.media.MediaMetrics.Item("audio.forceUse." + android.media.AudioSystem.forceUseUsageToString(i)).set(android.media.MediaMetrics.Property.EVENT, "setForceUse").set(android.media.MediaMetrics.Property.FORCE_USE_DUE_TO, str).set(android.media.MediaMetrics.Property.FORCE_USE_MODE, android.media.AudioSystem.forceUseConfigToString(i2)).record();
                    com.android.server.audio.AudioService.sForceUseLogger.enqueue(new com.android.server.audio.AudioServiceEvents.ForceUseEvent(i, i2, str));
                    com.android.server.audio.AudioService.this.mAudioSystem.setForceUse(i, i2);
                    return;
                case 10:
                    setAllVolumes((com.android.server.audio.AudioService.VolumeStreamState) message.obj);
                    return;
                case 15:
                    com.android.server.audio.AudioService.this.mSfxHelper.unloadSoundEffects();
                    return;
                case 16:
                    com.android.server.audio.AudioService.this.onSystemReady();
                    return;
                case 18:
                    com.android.server.audio.AudioService.this.onUnmuteStreamOnSingleVolDevice(message.arg1, message.arg2);
                    return;
                case 19:
                    com.android.server.audio.AudioService.this.onDynPolicyMixStateUpdate((java.lang.String) message.obj, message.arg1);
                    return;
                case 20:
                    com.android.server.audio.AudioService.this.onIndicateSystemReady();
                    return;
                case 21:
                    com.android.server.audio.AudioService.this.onAccessoryPlugMediaUnmute(message.arg1);
                    return;
                case 22:
                    onNotifyVolumeEvent((android.media.audiopolicy.IAudioPolicyCallback) message.obj, message.arg1);
                    return;
                case 23:
                    com.android.server.audio.AudioService.this.onDispatchAudioServerStateChange(message.arg1 == 1);
                    return;
                case 24:
                    com.android.server.audio.AudioService.this.onEnableSurroundFormats((java.util.ArrayList) message.obj);
                    return;
                case 25:
                    com.android.server.audio.AudioService.this.onUpdateRingerModeServiceInt();
                    return;
                case 26:
                    com.android.server.audio.AudioService.this.onSetVolumeIndexOnDevice((com.android.server.audio.AudioService.DeviceVolumeUpdate) message.obj);
                    return;
                case 27:
                    com.android.server.audio.AudioService.this.onObserveDevicesForAllStreams(message.arg1);
                    return;
                case 28:
                    com.android.server.audio.AudioService.this.onCheckVolumeCecOnHdmiConnection(message.arg1, (java.lang.String) message.obj);
                    return;
                case 29:
                    com.android.server.audio.AudioService.this.onPlaybackConfigChange((java.util.List) message.obj);
                    return;
                case 30:
                    com.android.server.audio.AudioService.this.mSystemServer.sendMicrophoneMuteChangedIntent();
                    return;
                case 31:
                    synchronized (com.android.server.audio.AudioService.this.mDeviceBroker.mSetModeLock) {
                        try {
                            if (message.obj != null) {
                                com.android.server.audio.AudioService.SetModeDeathHandler setModeDeathHandler = (com.android.server.audio.AudioService.SetModeDeathHandler) message.obj;
                                if (com.android.server.audio.AudioService.this.mSetModeDeathHandlers.indexOf(setModeDeathHandler) >= 0) {
                                    boolean isActive = setModeDeathHandler.isActive();
                                    setModeDeathHandler.setPlaybackActive(com.android.server.audio.AudioService.this.isPlaybackActiveForUid(setModeDeathHandler.getUid()));
                                    setModeDeathHandler.setRecordingActive(com.android.server.audio.AudioService.this.isRecordingActiveForUid(setModeDeathHandler.getUid()));
                                    if (isActive != setModeDeathHandler.isActive()) {
                                        com.android.server.audio.AudioService.this.onUpdateAudioMode(-1, android.os.Process.myPid(), com.android.server.audio.AudioService.this.mContext.getPackageName(), false);
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        } finally {
                        }
                    }
                case 32:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.content.Intent intent = (android.content.Intent) someArgs.arg1;
                    android.os.Bundle bundle = (android.os.Bundle) someArgs.arg2;
                    someArgs.recycle();
                    com.android.server.audio.AudioService.this.sendBroadcastToAll(intent.putExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", message.arg1).putExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", message.arg2), bundle);
                    return;
                case 33:
                    com.android.server.audio.AudioService.this.onUpdateVolumeStatesForAudioDevice(message.arg1, (java.lang.String) message.obj);
                    return;
                case 34:
                    com.android.server.audio.AudioService.this.onReinitVolumes((java.lang.String) message.obj);
                    return;
                case 35:
                    com.android.server.audio.AudioService.this.onUpdateAccessibilityServiceUids();
                    return;
                case 36:
                    synchronized (com.android.server.audio.AudioService.this.mDeviceBroker.mSetModeLock) {
                        com.android.server.audio.AudioService.this.onUpdateAudioMode(message.arg1, message.arg2, (java.lang.String) message.obj, false);
                    }
                    return;
                case 37:
                    com.android.server.audio.AudioService.this.onRecordingConfigChange((java.util.List) message.obj);
                    return;
                case 38:
                    com.android.server.audio.AudioService.this.mDeviceBroker.queueOnBluetoothActiveDeviceChanged((com.android.server.audio.AudioDeviceBroker.BtDeviceChangedData) message.obj);
                    return;
                case 40:
                    com.android.server.audio.AudioService.this.dispatchMode(message.arg1);
                    return;
                case 41:
                    com.android.server.audio.AudioService.this.onRoutingUpdatedFromAudioThread();
                    return;
                case 42:
                    com.android.server.audio.AudioService.this.mSpatializerHelper.onInitSensors();
                    return;
                case 44:
                    com.android.server.audio.AudioService.this.onAddAssistantServiceUids(new int[]{message.arg1});
                    return;
                case 45:
                    com.android.server.audio.AudioService.this.onRemoveAssistantServiceUids(new int[]{message.arg1});
                    return;
                case 46:
                    com.android.server.audio.AudioService.this.updateActiveAssistantServiceUids();
                    return;
                case 47:
                    com.android.server.audio.AudioService.this.dispatchDeviceVolumeBehavior((android.media.AudioDeviceAttributes) message.obj, message.arg1);
                    return;
                case 48:
                    com.android.server.audio.AudioService.this.mAudioSystem.setParameters((java.lang.String) message.obj);
                    return;
                case 49:
                    com.android.server.audio.AudioService.this.mAudioSystem.setParameters((java.lang.String) message.obj);
                    return;
                case 50:
                    com.android.server.audio.AudioService.this.mSpatializerHelper.reset(com.android.server.audio.AudioService.this.mHasSpatializerEffect);
                    return;
                case 51:
                    com.android.server.audio.AudioService.this.mPlaybackMonitor.ignorePlayerIId(message.arg1);
                    return;
                case 52:
                    com.android.server.audio.AudioService.this.onDispatchPreferredMixerAttributesChanged(message.getData(), message.arg1);
                    return;
                case 54:
                    com.android.server.audio.AudioService.this.onConfigurationChanged();
                    return;
                case 55:
                    com.android.server.audio.AudioService.this.mSystemServer.broadcastMasterMuteStatus(message.arg1 == 1);
                    return;
                case 100:
                    com.android.server.audio.AudioService.this.mPlaybackMonitor.disableAudioForUid(message.arg1 == 1, message.arg2);
                    com.android.server.audio.AudioService.this.mAudioEventWakeLock.release();
                    return;
                case 101:
                    com.android.server.audio.AudioService.this.onInitStreamsAndVolumes();
                    com.android.server.audio.AudioService.this.mAudioEventWakeLock.release();
                    return;
                case 102:
                    com.android.server.audio.AudioService.this.onInitSpatializer();
                    com.android.server.audio.AudioService.this.mAudioEventWakeLock.release();
                    return;
                case 103:
                    com.android.server.audio.AudioService.this.onInitAdiDeviceStates();
                    com.android.server.audio.AudioService.this.mAudioEventWakeLock.release();
                    return;
                case 1001:
                case 1002:
                case 1003:
                case 1004:
                case 1005:
                case 1006:
                case 1007:
                    com.android.server.audio.AudioService.this.mSoundDoseHelper.handleMessage(message);
                    return;
                case 1101:
                    com.android.server.audio.AudioService.this.mMusicFxHelper.handleMessage(message);
                    return;
                default:
                    android.util.Log.e(com.android.server.audio.AudioService.TAG, "Unsupported msgId " + message.what);
                    return;
            }
        }
    }

    private class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver() {
            super(new android.os.Handler());
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("zen_mode"), false, this);
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("zen_mode_config_etag"), false, this);
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("mute_alarm_stream_with_ringer_mode"), false, this);
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("mode_ringer_streams_affected"), false, this);
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("dock_audio_media_enabled"), false, this);
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("master_mono"), false, this);
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.System.getUriFor("master_balance"), false, this);
            com.android.server.audio.AudioService.this.mEncodedSurroundMode = com.android.server.audio.AudioService.this.mSettings.getGlobalInt(com.android.server.audio.AudioService.this.mContentResolver, "encoded_surround_output", 0);
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("encoded_surround_output"), false, this);
            com.android.server.audio.AudioService.this.mEnabledSurroundFormats = com.android.server.audio.AudioService.this.mSettings.getGlobalString(com.android.server.audio.AudioService.this.mContentResolver, "encoded_surround_output_enabled_formats");
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("encoded_surround_output_enabled_formats"), false, this);
            com.android.server.audio.AudioService.this.mContentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor("voice_interaction_service"), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                try {
                    if (com.android.server.audio.AudioService.this.updateRingerAndZenModeAffectedStreams()) {
                        com.android.server.audio.AudioService.this.setRingerModeInt(com.android.server.audio.AudioService.this.getRingerModeInternal(), false);
                    }
                    com.android.server.audio.AudioService.this.readDockAudioSettings(com.android.server.audio.AudioService.this.mContentResolver);
                    com.android.server.audio.AudioService.this.updateMasterMono(com.android.server.audio.AudioService.this.mContentResolver);
                    com.android.server.audio.AudioService.this.updateMasterBalance(com.android.server.audio.AudioService.this.mContentResolver);
                    updateEncodedSurroundOutput();
                    com.android.server.audio.AudioService.this.sendEnabledSurroundFormats(com.android.server.audio.AudioService.this.mContentResolver, com.android.server.audio.AudioService.this.mSurroundModeChanged);
                    com.android.server.audio.AudioService.this.updateAssistantUIdLocked(false);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void updateEncodedSurroundOutput() {
            int globalInt = com.android.server.audio.AudioService.this.mSettings.getGlobalInt(com.android.server.audio.AudioService.this.mContentResolver, "encoded_surround_output", 0);
            if (com.android.server.audio.AudioService.this.mEncodedSurroundMode != globalInt) {
                com.android.server.audio.AudioService.this.sendEncodedSurroundMode(globalInt, "SettingsObserver");
                com.android.server.audio.AudioService.this.mDeviceBroker.toggleHdmiIfConnected_Async();
                com.android.server.audio.AudioService.this.mEncodedSurroundMode = globalInt;
                com.android.server.audio.AudioService.this.mSurroundModeChanged = true;
                return;
            }
            com.android.server.audio.AudioService.this.mSurroundModeChanged = false;
        }
    }

    private void avrcpSupportsAbsoluteVolume(java.lang.String str, boolean z) {
        sVolumeLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("avrcpSupportsAbsoluteVolume addr=" + android.media.Utils.anonymizeBluetoothAddress(str) + " support=" + z).printLog(TAG));
        this.mDeviceBroker.setAvrcpAbsoluteVolumeSupported(z);
        setAvrcpAbsoluteVolumeSupported(z);
    }

    void setAvrcpAbsoluteVolumeSupported(boolean z) {
        this.mAvrcpAbsVolSupported = z;
        sendMsg(this.mAudioHandler, 0, 2, 128, 0, this.mStreamStates[3], 0);
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean hasMediaDynamicPolicy() {
        synchronized (this.mAudioPolicies) {
            try {
                if (this.mAudioPolicies.isEmpty()) {
                    return false;
                }
                java.util.Iterator<com.android.server.audio.AudioService.AudioPolicyProxy> it = this.mAudioPolicies.values().iterator();
                while (it.hasNext()) {
                    if (it.next().hasMixAffectingUsage(1, 3)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void checkMusicActive(int i, java.lang.String str) {
        if (this.mSoundDoseHelper.safeDevicesContains(i)) {
            this.mSoundDoseHelper.scheduleMusicActiveCheck();
        }
    }

    private class AudioServiceBroadcastReceiver extends android.content.BroadcastReceiver {
        private AudioServiceBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            int i = 0;
            if (action.equals("android.intent.action.DOCK_EVENT")) {
                int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                switch (intExtra) {
                    case 1:
                        i = 7;
                        break;
                    case 2:
                        i = 6;
                        break;
                    case 3:
                        i = 8;
                        break;
                    case 4:
                        i = 9;
                        break;
                }
                if (intExtra != 3 && (intExtra != 0 || com.android.server.audio.AudioService.this.mDockState != 3)) {
                    com.android.server.audio.AudioService.this.mDeviceBroker.setForceUse_Async(3, i, "ACTION_DOCK_EVENT intent");
                }
                com.android.server.audio.AudioService.this.mDockState = intExtra;
                return;
            }
            if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED") || action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                com.android.server.audio.AudioService.this.mDeviceBroker.postReceiveBtEvent(intent);
                return;
            }
            if (action.equals("android.intent.action.SCREEN_ON")) {
                if (com.android.server.audio.AudioService.this.mMonitorRotation) {
                    com.android.server.audio.RotationHelper.enable();
                }
                android.media.AudioSystem.setParameters("screen_state=on");
                return;
            }
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                if (com.android.server.audio.AudioService.this.mMonitorRotation) {
                    com.android.server.audio.RotationHelper.disable();
                }
                android.media.AudioSystem.setParameters("screen_state=off");
                return;
            }
            if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 54, 0, 0, 0, null, 0);
                return;
            }
            if (action.equals("android.intent.action.USER_SWITCHED")) {
                if (com.android.server.audio.AudioService.this.mMediaFocusControl.maybeDiscardAudioFocusOwner() && com.android.server.audio.AudioService.this.mUserSwitchedReceived) {
                    com.android.server.audio.AudioService.this.mDeviceBroker.postBroadcastBecomingNoisy();
                }
                com.android.server.audio.AudioService.this.mUserSwitchedReceived = true;
                if (com.android.server.audio.AudioService.this.mSupportsMicPrivacyToggle) {
                    com.android.server.audio.AudioService.this.mMicMuteFromPrivacyToggle = com.android.server.audio.AudioService.this.mSensorPrivacyManagerInternal.isSensorPrivacyEnabled(com.android.server.audio.AudioService.this.getCurrentUserId(), 1);
                    com.android.server.audio.AudioService.this.setMicrophoneMuteNoCallerCheck(com.android.server.audio.AudioService.this.getCurrentUserId());
                }
                com.android.server.audio.AudioService.this.readAudioSettings(true);
                com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 10, 2, 0, 0, com.android.server.audio.AudioService.this.mStreamStates[3], 0);
                return;
            }
            if (action.equals("android.intent.action.USER_BACKGROUND")) {
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra2 >= 0) {
                    com.android.server.audio.AudioService.this.killBackgroundUserProcessesWithRecordAudioPermission(com.android.server.pm.UserManagerService.getInstance().getUserInfo(intExtra2));
                }
                try {
                    com.android.server.pm.UserManagerService.getInstance().setUserRestriction("no_record_audio", true, intExtra2);
                    return;
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.w(com.android.server.audio.AudioService.TAG, "Failed to apply DISALLOW_RECORD_AUDIO restriction: " + e);
                    return;
                }
            }
            if (action.equals("android.intent.action.USER_FOREGROUND")) {
                try {
                    com.android.server.pm.UserManagerService.getInstance().setUserRestriction("no_record_audio", false, intent.getIntExtra("android.intent.extra.user_handle", -1));
                    return;
                } catch (java.lang.IllegalArgumentException e2) {
                    android.util.Slog.w(com.android.server.audio.AudioService.TAG, "Failed to apply DISALLOW_RECORD_AUDIO restriction: " + e2);
                    return;
                }
            }
            if (action.equals("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION") || action.equals("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION")) {
                com.android.server.audio.AudioService.this.mMusicFxHelper.handleAudioEffectBroadcast(context, intent);
                return;
            }
            if (action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                int[] intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                if (stringArrayExtra == null || intArrayExtra == null || stringArrayExtra.length != intArrayExtra.length) {
                    return;
                }
                while (i < intArrayExtra.length) {
                    if (!android.text.TextUtils.isEmpty(stringArrayExtra[i])) {
                        com.android.server.audio.AudioService.this.mMediaFocusControl.noFocusForSuspendedApp(stringArrayExtra[i], intArrayExtra[i]);
                    }
                    i++;
                }
                return;
            }
            if (action.equals("com.android.server.audio.action.CHECK_MUSIC_ACTIVE")) {
                com.android.server.audio.AudioService.this.mSoundDoseHelper.onCheckMusicActive("com.android.server.audio.action.CHECK_MUSIC_ACTIVE", com.android.server.audio.AudioService.this.mAudioSystem.isStreamActive(3, 0));
            }
        }
    }

    private class AudioServiceUserRestrictionsListener implements com.android.server.pm.UserManagerInternal.UserRestrictionsListener {
        private AudioServiceUserRestrictionsListener() {
        }

        @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
        public void onUserRestrictionsChanged(int i, android.os.Bundle bundle, android.os.Bundle bundle2) {
            boolean z = bundle2.getBoolean("no_unmute_microphone");
            boolean z2 = bundle.getBoolean("no_unmute_microphone");
            if (z != z2) {
                com.android.server.audio.AudioService.this.mMicMuteFromRestrictions = z2;
                com.android.server.audio.AudioService.this.setMicrophoneMuteNoCallerCheck(i);
            }
            boolean z3 = true;
            boolean z4 = bundle2.getBoolean("no_adjust_volume") || bundle2.getBoolean("disallow_unmute_device");
            if (!bundle.getBoolean("no_adjust_volume") && !bundle.getBoolean("disallow_unmute_device")) {
                z3 = false;
            }
            if (z4 != z3) {
                com.android.server.audio.AudioService.this.setMasterMuteInternalNoCallerCheck(z3, 0, i, "onUserRestrictionsChanged");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void killBackgroundUserProcessesWithRecordAudioPermission(android.content.pm.UserInfo userInfo) {
        android.content.ComponentName componentName;
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (userInfo.isManagedProfile()) {
            componentName = null;
        } else {
            componentName = ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getHomeActivityForUser(userInfo.id);
        }
        try {
            java.util.List list = android.app.AppGlobals.getPackageManager().getPackagesHoldingPermissions(new java.lang.String[]{"android.permission.RECORD_AUDIO"}, 0L, userInfo.id).getList();
            for (int size = list.size() - 1; size >= 0; size--) {
                android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) list.get(size);
                if (android.os.UserHandle.getAppId(packageInfo.applicationInfo.uid) >= 10000 && packageManager.checkPermission("android.permission.INTERACT_ACROSS_USERS", packageInfo.packageName) != 0 && (componentName == null || !packageInfo.packageName.equals(componentName.getPackageName()) || !packageInfo.applicationInfo.isSystemApp())) {
                    try {
                        int i = packageInfo.applicationInfo.uid;
                        android.app.ActivityManager.getService().killUid(android.os.UserHandle.getAppId(i), android.os.UserHandle.getUserId(i), "killBackgroundUserProcessesWithAudioRecordPermission");
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(TAG, "Error calling killUid", e);
                    }
                }
            }
        } catch (android.os.RemoteException e2) {
            throw new android.util.AndroidRuntimeException(e2);
        }
    }

    private boolean forceFocusDuckingForAccessibility(@android.annotation.Nullable android.media.AudioAttributes audioAttributes, int i, int i2) {
        android.os.Bundle bundle;
        if (audioAttributes == null || audioAttributes.getUsage() != 11 || i != 3 || (bundle = audioAttributes.getBundle()) == null || !bundle.getBoolean("a11y_force_ducking")) {
            return false;
        }
        if (i2 == 0) {
            return true;
        }
        synchronized (this.mAccessibilityServiceUidsLock) {
            try {
                if (this.mAccessibilityServiceUids != null) {
                    int callingUid = android.os.Binder.getCallingUid();
                    for (int i3 = 0; i3 < this.mAccessibilityServiceUids.length; i3++) {
                        if (this.mAccessibilityServiceUids[i3] == callingUid) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isSupportedSystemUsage(int i) {
        synchronized (this.mSupportedSystemUsagesLock) {
            for (int i2 = 0; i2 < this.mSupportedSystemUsages.length; i2++) {
                try {
                    if (this.mSupportedSystemUsages[i2] == i) {
                        return true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return false;
        }
    }

    private void validateAudioAttributesUsage(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        int systemUsage = audioAttributes.getSystemUsage();
        if (android.media.AudioAttributes.isSystemUsage(systemUsage)) {
            if ((systemUsage == 17 && (audioAttributes.getAllFlags() & 65536) != 0 && callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) || callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING")) {
                if (!isSupportedSystemUsage(systemUsage)) {
                    throw new java.lang.IllegalArgumentException("Unsupported usage " + android.media.AudioAttributes.usageToString(systemUsage));
                }
                return;
            }
            throw new java.lang.SecurityException("Missing MODIFY_AUDIO_ROUTING permission");
        }
    }

    private boolean isValidAudioAttributesUsage(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        int systemUsage = audioAttributes.getSystemUsage();
        if (android.media.AudioAttributes.isSystemUsage(systemUsage)) {
            return isSupportedSystemUsage(systemUsage) && ((systemUsage == 17 && (audioAttributes.getAllFlags() & 65536) != 0 && callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) || callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING"));
        }
        return true;
    }

    public int requestAudioFocus(android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i3) {
        boolean z;
        if ((i2 & 8) != 0) {
            throw new java.lang.IllegalArgumentException("Invalid test flag");
        }
        int callingUid = android.os.Binder.getCallingUid();
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.service.focus").setUid(callingUid).set(android.media.MediaMetrics.Property.CALLING_PACKAGE, str2).set(android.media.MediaMetrics.Property.CLIENT_NAME, str).set(android.media.MediaMetrics.Property.EVENT, "requestAudioFocus").set(android.media.MediaMetrics.Property.FLAGS, java.lang.Integer.valueOf(i2));
        if (audioAttributes != null && !isValidAudioAttributesUsage(audioAttributes)) {
            android.util.Log.w(TAG, "Request using unsupported usage");
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "Request using unsupported usage").record();
            return 0;
        }
        if ((i2 & 4) == 4) {
            if ("AudioFocus_For_Phone_Ring_And_Calls".equals(str)) {
                if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
                    android.util.Log.e(TAG, "Invalid permission to (un)lock audio focus", new java.lang.Exception());
                    item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "Invalid permission to (un)lock audio focus").record();
                    return 0;
                }
            } else {
                synchronized (this.mAudioPolicies) {
                    try {
                        if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                            android.util.Log.e(TAG, "Invalid unregistered AudioPolicy to (un)lock audio focus");
                            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "Invalid unregistered AudioPolicy to (un)lock audio focus").record();
                            return 0;
                        }
                    } finally {
                    }
                }
            }
        }
        if (str2 == null || str == null || audioAttributes == null) {
            android.util.Log.e(TAG, "Invalid null parameter to request audio focus");
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "Invalid null parameter to request audio focus").record();
            return 0;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0) {
            z = true;
        } else if (callingUid >= 10000) {
            z = false;
        } else {
            z = true;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        if (!z) {
            try {
                if (this.mHardeningEnforcer.blockFocusMethod(callingUid, 300, str, i, str2)) {
                    android.util.Log.w(TAG, "Audio focus request blocked by hardening");
                    item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "Audio focus request blocked by hardening").record();
                    return 0;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        item.record();
        return this.mMediaFocusControl.requestAudioFocus(audioAttributes, i, iBinder, iAudioFocusDispatcher, str, str2, str3, i2, i3, forceFocusDuckingForAccessibility(audioAttributes, i, callingUid), -1, z);
    }

    public int requestAudioFocusForTest(android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, java.lang.String str2, int i2, int i3, int i4) {
        if (!enforceQueryAudioStateForTest("focus request")) {
            return 0;
        }
        if (str2 == null || str == null || audioAttributes == null) {
            android.util.Log.e(TAG, "Invalid null parameter to request audio focus");
            return 0;
        }
        return this.mMediaFocusControl.requestAudioFocus(audioAttributes, i, iBinder, iAudioFocusDispatcher, str, str2, null, i2, i4, false, i3, true);
    }

    public int abandonAudioFocus(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) {
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.service.focus").set(android.media.MediaMetrics.Property.CALLING_PACKAGE, str2).set(android.media.MediaMetrics.Property.CLIENT_NAME, str).set(android.media.MediaMetrics.Property.EVENT, "abandonAudioFocus");
        if (audioAttributes != null && !isValidAudioAttributesUsage(audioAttributes)) {
            android.util.Log.w(TAG, "Request using unsupported usage.");
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "unsupported usage").record();
            return 0;
        }
        item.record();
        return this.mMediaFocusControl.abandonAudioFocus(iAudioFocusDispatcher, str, audioAttributes, str2);
    }

    public int abandonAudioFocusForTest(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) {
        if (!enforceQueryAudioStateForTest("focus abandon")) {
            return 0;
        }
        return this.mMediaFocusControl.abandonAudioFocus(iAudioFocusDispatcher, str, audioAttributes, str2);
    }

    @android.annotation.EnforcePermission("android.permission.QUERY_AUDIO_STATE")
    @android.annotation.NonNull
    public java.util.List<java.lang.Integer> getFocusDuckedUidsForTest() {
        super.getFocusDuckedUidsForTest_enforcePermission();
        return this.mPlaybackMonitor.getFocusDuckedUids();
    }

    public void unregisterAudioFocusClient(java.lang.String str) {
        new android.media.MediaMetrics.Item("audio.service.focus").set(android.media.MediaMetrics.Property.CLIENT_NAME, str).set(android.media.MediaMetrics.Property.EVENT, "unregisterAudioFocusClient").record();
        this.mMediaFocusControl.unregisterAudioFocusClient(str);
    }

    public int getCurrentAudioFocus() {
        return this.mMediaFocusControl.getCurrentAudioFocus();
    }

    public int getFocusRampTimeMs(int i, android.media.AudioAttributes audioAttributes) {
        return com.android.server.audio.MediaFocusControl.getFocusRampTimeMs(i, audioAttributes);
    }

    @android.annotation.EnforcePermission("android.permission.QUERY_AUDIO_STATE")
    public long getFocusFadeOutDurationForTest() {
        super.getFocusFadeOutDurationForTest_enforcePermission();
        return this.mMediaFocusControl.getFocusFadeOutDurationForTest();
    }

    @android.annotation.EnforcePermission("android.permission.QUERY_AUDIO_STATE")
    public long getFocusUnmuteDelayAfterFadeOutForTest() {
        super.getFocusUnmuteDelayAfterFadeOutForTest_enforcePermission();
        return this.mMediaFocusControl.getFocusUnmuteDelayAfterFadeOutForTest();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public boolean enterAudioFocusFreezeForTest(android.os.IBinder iBinder, int[] iArr) {
        super.enterAudioFocusFreezeForTest_enforcePermission();
        java.util.Objects.requireNonNull(iArr);
        java.util.Objects.requireNonNull(iBinder);
        return this.mMediaFocusControl.enterAudioFocusFreezeForTest(iBinder, iArr);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public boolean exitAudioFocusFreezeForTest(android.os.IBinder iBinder) {
        super.exitAudioFocusFreezeForTest_enforcePermission();
        java.util.Objects.requireNonNull(iBinder);
        return this.mMediaFocusControl.exitAudioFocusFreezeForTest(iBinder);
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean hasAudioFocusUsers() {
        return this.mMediaFocusControl.hasAudioFocusUsers();
    }

    public long getFadeOutDurationOnFocusLossMillis(android.media.AudioAttributes audioAttributes) {
        if (!enforceQueryAudioStateForTest("fade out duration")) {
            return 0L;
        }
        return this.mMediaFocusControl.getFadeOutDurationOnFocusLossMillis(audioAttributes);
    }

    private boolean enforceQueryAudioStateForTest(java.lang.String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.QUERY_AUDIO_STATE") != 0) {
            android.util.Log.e(TAG, "Doesn't have QUERY_AUDIO_STATE permission for " + str + " test API", new java.lang.Exception());
            return false;
        }
        return true;
    }

    private void enforceModifyDefaultAudioEffectsPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS") != 0) {
            throw new java.lang.SecurityException("Missing MODIFY_DEFAULT_AUDIO_EFFECTS permission");
        }
    }

    public int getSpatializerImmersiveAudioLevel() {
        return this.mSpatializerHelper.getCapableImmersiveAudioLevel();
    }

    public boolean isSpatializerEnabled() {
        return this.mSpatializerHelper.isEnabled();
    }

    public boolean isSpatializerAvailable() {
        return this.mSpatializerHelper.isAvailable();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public boolean isSpatializerAvailableForDevice(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.isSpatializerAvailableForDevice_enforcePermission();
        com.android.server.audio.SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        return spatializerHelper.isAvailableForDevice(audioDeviceAttributes);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public boolean hasHeadTracker(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.hasHeadTracker_enforcePermission();
        com.android.server.audio.SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        return spatializerHelper.hasHeadTracker(audioDeviceAttributes);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void setHeadTrackerEnabled(boolean z, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.setHeadTrackerEnabled_enforcePermission();
        com.android.server.audio.SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        spatializerHelper.setHeadTrackerEnabled(z, audioDeviceAttributes);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public boolean isHeadTrackerEnabled(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.isHeadTrackerEnabled_enforcePermission();
        com.android.server.audio.SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        return spatializerHelper.isHeadTrackerEnabled(audioDeviceAttributes);
    }

    public boolean isHeadTrackerAvailable() {
        return this.mSpatializerHelper.isHeadTrackerAvailable();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void setSpatializerEnabled(boolean z) {
        super.setSpatializerEnabled_enforcePermission();
        this.mSpatializerHelper.setFeatureEnabled(z);
    }

    public boolean canBeSpatialized(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, @android.annotation.NonNull android.media.AudioFormat audioFormat) {
        java.util.Objects.requireNonNull(audioAttributes);
        java.util.Objects.requireNonNull(audioFormat);
        return this.mSpatializerHelper.canBeSpatialized(audioAttributes, audioFormat);
    }

    public void registerSpatializerCallback(@android.annotation.NonNull android.media.ISpatializerCallback iSpatializerCallback) {
        java.util.Objects.requireNonNull(iSpatializerCallback);
        this.mSpatializerHelper.registerStateCallback(iSpatializerCallback);
    }

    public void unregisterSpatializerCallback(@android.annotation.NonNull android.media.ISpatializerCallback iSpatializerCallback) {
        java.util.Objects.requireNonNull(iSpatializerCallback);
        this.mSpatializerHelper.unregisterStateCallback(iSpatializerCallback);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void registerSpatializerHeadTrackingCallback(@android.annotation.NonNull android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) {
        super.registerSpatializerHeadTrackingCallback_enforcePermission();
        java.util.Objects.requireNonNull(iSpatializerHeadTrackingModeCallback);
        this.mSpatializerHelper.registerHeadTrackingModeCallback(iSpatializerHeadTrackingModeCallback);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void unregisterSpatializerHeadTrackingCallback(@android.annotation.NonNull android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) {
        super.unregisterSpatializerHeadTrackingCallback_enforcePermission();
        java.util.Objects.requireNonNull(iSpatializerHeadTrackingModeCallback);
        this.mSpatializerHelper.unregisterHeadTrackingModeCallback(iSpatializerHeadTrackingModeCallback);
    }

    public void registerSpatializerHeadTrackerAvailableCallback(@android.annotation.NonNull android.media.ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) {
        java.util.Objects.requireNonNull(iSpatializerHeadTrackerAvailableCallback);
        this.mSpatializerHelper.registerHeadTrackerAvailableCallback(iSpatializerHeadTrackerAvailableCallback, z);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void registerHeadToSoundstagePoseCallback(@android.annotation.NonNull android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) {
        super.registerHeadToSoundstagePoseCallback_enforcePermission();
        java.util.Objects.requireNonNull(iSpatializerHeadToSoundStagePoseCallback);
        this.mSpatializerHelper.registerHeadToSoundstagePoseCallback(iSpatializerHeadToSoundStagePoseCallback);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void unregisterHeadToSoundstagePoseCallback(@android.annotation.NonNull android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) {
        super.unregisterHeadToSoundstagePoseCallback_enforcePermission();
        java.util.Objects.requireNonNull(iSpatializerHeadToSoundStagePoseCallback);
        this.mSpatializerHelper.unregisterHeadToSoundstagePoseCallback(iSpatializerHeadToSoundStagePoseCallback);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    @android.annotation.NonNull
    public java.util.List<android.media.AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() {
        super.getSpatializerCompatibleAudioDevices_enforcePermission();
        return this.mSpatializerHelper.getCompatibleAudioDevices();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void addSpatializerCompatibleAudioDevice(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.addSpatializerCompatibleAudioDevice_enforcePermission();
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        this.mSpatializerHelper.addCompatibleAudioDevice(audioDeviceAttributes);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void removeSpatializerCompatibleAudioDevice(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        super.removeSpatializerCompatibleAudioDevice_enforcePermission();
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        this.mSpatializerHelper.removeCompatibleAudioDevice(audioDeviceAttributes);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public int[] getSupportedHeadTrackingModes() {
        super.getSupportedHeadTrackingModes_enforcePermission();
        return this.mSpatializerHelper.getSupportedHeadTrackingModes();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public int getActualHeadTrackingMode() {
        super.getActualHeadTrackingMode_enforcePermission();
        return this.mSpatializerHelper.getActualHeadTrackingMode();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public int getDesiredHeadTrackingMode() {
        super.getDesiredHeadTrackingMode_enforcePermission();
        return this.mSpatializerHelper.getDesiredHeadTrackingMode();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void setSpatializerGlobalTransform(@android.annotation.NonNull float[] fArr) {
        super.setSpatializerGlobalTransform_enforcePermission();
        java.util.Objects.requireNonNull(fArr);
        this.mSpatializerHelper.setGlobalTransform(fArr);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void recenterHeadTracker() {
        super.recenterHeadTracker_enforcePermission();
        this.mSpatializerHelper.recenterHeadTracker();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void setDesiredHeadTrackingMode(int i) {
        super.setDesiredHeadTrackingMode_enforcePermission();
        switch (i) {
            case -1:
            case 1:
            case 2:
                this.mSpatializerHelper.setDesiredHeadTrackingMode(i);
                break;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void setSpatializerParameter(int i, @android.annotation.NonNull byte[] bArr) {
        super.setSpatializerParameter_enforcePermission();
        java.util.Objects.requireNonNull(bArr);
        this.mSpatializerHelper.setEffectParameter(i, bArr);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void getSpatializerParameter(int i, @android.annotation.NonNull byte[] bArr) {
        super.getSpatializerParameter_enforcePermission();
        java.util.Objects.requireNonNull(bArr);
        this.mSpatializerHelper.getEffectParameter(i, bArr);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public int getSpatializerOutput() {
        super.getSpatializerOutput_enforcePermission();
        return this.mSpatializerHelper.getOutput();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void registerSpatializerOutputCallback(android.media.ISpatializerOutputCallback iSpatializerOutputCallback) {
        super.registerSpatializerOutputCallback_enforcePermission();
        java.util.Objects.requireNonNull(iSpatializerOutputCallback);
        this.mSpatializerHelper.registerSpatializerOutputCallback(iSpatializerOutputCallback);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_DEFAULT_AUDIO_EFFECTS")
    public void unregisterSpatializerOutputCallback(android.media.ISpatializerOutputCallback iSpatializerOutputCallback) {
        super.unregisterSpatializerOutputCallback_enforcePermission();
        java.util.Objects.requireNonNull(iSpatializerOutputCallback);
        this.mSpatializerHelper.unregisterSpatializerOutputCallback(iSpatializerOutputCallback);
    }

    void postInitSpatializerHeadTrackingSensors() {
        sendMsg(this.mAudioHandler, 42, 0, 0, 0, TAG, 0);
    }

    void postResetSpatializer() {
        sendMsg(this.mAudioHandler, 50, 0, 0, 0, TAG, 0);
    }

    void onInitAdiDeviceStates() {
        this.mDeviceBroker.onReadAudioDeviceSettings();
        this.mSoundDoseHelper.initCachedAudioDeviceCategories(this.mDeviceBroker.getImmutableDeviceInventory());
    }

    void onInitSpatializer() {
        this.mSpatializerHelper.init(this.mHasSpatializerEffect);
        this.mSpatializerHelper.setFeatureEnabled(this.mHasSpatializerEffect);
    }

    boolean isSADevice(com.android.server.audio.AdiDeviceState adiDeviceState) {
        return this.mSpatializerHelper.isSADevice(adiDeviceState);
    }

    private boolean isBluetoothPrividged() {
        return this.mContext.checkCallingOrSelfPermission("android.permission.BLUETOOTH_CONNECT") == 0 || android.os.Binder.getCallingUid() == 1000;
    }

    java.util.List<android.media.AudioDeviceAttributes> retrieveBluetoothAddresses(java.util.List<android.media.AudioDeviceAttributes> list) {
        if (isBluetoothPrividged()) {
            return list;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.media.AudioDeviceAttributes audioDeviceAttributes : list) {
            if (audioDeviceAttributes != null) {
                arrayList.add(retrieveBluetoothAddressUncheked(audioDeviceAttributes));
            }
        }
        return arrayList;
    }

    android.media.AudioDeviceAttributes retrieveBluetoothAddress(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (isBluetoothPrividged()) {
            return audioDeviceAttributes;
        }
        return retrieveBluetoothAddressUncheked(audioDeviceAttributes);
    }

    android.media.AudioDeviceAttributes retrieveBluetoothAddressUncheked(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        if (android.media.AudioSystem.isBluetoothDevice(audioDeviceAttributes.getInternalType())) {
            java.lang.String anonymizeBluetoothAddress = android.media.Utils.anonymizeBluetoothAddress(audioDeviceAttributes.getAddress());
            java.util.Iterator<com.android.server.audio.AdiDeviceState> it = this.mDeviceBroker.getImmutableDeviceInventory().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                com.android.server.audio.AdiDeviceState next = it.next();
                if (android.media.AudioSystem.isBluetoothDevice(next.getInternalDeviceType()) && audioDeviceAttributes.getInternalType() == next.getInternalDeviceType() && anonymizeBluetoothAddress.equals(android.media.Utils.anonymizeBluetoothAddress(next.getDeviceAddress()))) {
                    audioDeviceAttributes.setAddress(next.getDeviceAddress());
                    break;
                }
            }
        }
        return audioDeviceAttributes;
    }

    private java.util.List<android.media.AudioDeviceAttributes> anonymizeAudioDeviceAttributesList(java.util.List<android.media.AudioDeviceAttributes> list) {
        if (isBluetoothPrividged()) {
            return list;
        }
        return anonymizeAudioDeviceAttributesListUnchecked(list);
    }

    java.util.List<android.media.AudioDeviceAttributes> anonymizeAudioDeviceAttributesListUnchecked(java.util.List<android.media.AudioDeviceAttributes> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.media.AudioDeviceAttributes> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(anonymizeAudioDeviceAttributesUnchecked(it.next()));
        }
        return arrayList;
    }

    private android.media.AudioDeviceAttributes anonymizeAudioDeviceAttributesUnchecked(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (!android.media.AudioSystem.isBluetoothDevice(audioDeviceAttributes.getInternalType())) {
            return audioDeviceAttributes;
        }
        android.media.AudioDeviceAttributes audioDeviceAttributes2 = new android.media.AudioDeviceAttributes(audioDeviceAttributes);
        audioDeviceAttributes2.setAddress(android.media.Utils.anonymizeBluetoothAddress(audioDeviceAttributes.getAddress()));
        return audioDeviceAttributes2;
    }

    private android.media.AudioDeviceAttributes anonymizeAudioDeviceAttributes(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (isBluetoothPrividged()) {
            return audioDeviceAttributes;
        }
        return anonymizeAudioDeviceAttributesUnchecked(audioDeviceAttributes);
    }

    public void registerLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) {
        this.mLoudnessCodecHelper.registerLoudnessCodecUpdatesDispatcher(iLoudnessCodecUpdatesDispatcher);
    }

    public void unregisterLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) {
        this.mLoudnessCodecHelper.unregisterLoudnessCodecUpdatesDispatcher(iLoudnessCodecUpdatesDispatcher);
    }

    public void startLoudnessCodecUpdates(int i) {
        this.mLoudnessCodecHelper.startLoudnessCodecUpdates(i);
    }

    public void stopLoudnessCodecUpdates(int i) {
        this.mLoudnessCodecHelper.stopLoudnessCodecUpdates(i);
    }

    public void addLoudnessCodecInfo(int i, int i2, android.media.LoudnessCodecInfo loudnessCodecInfo) {
        this.mLoudnessCodecHelper.addLoudnessCodecInfo(i, i2, loudnessCodecInfo);
    }

    public void removeLoudnessCodecInfo(int i, android.media.LoudnessCodecInfo loudnessCodecInfo) {
        this.mLoudnessCodecHelper.removeLoudnessCodecInfo(i, loudnessCodecInfo);
    }

    public android.os.PersistableBundle getLoudnessParams(android.media.LoudnessCodecInfo loudnessCodecInfo) {
        return this.mLoudnessCodecHelper.getLoudnessParams(loudnessCodecInfo);
    }

    private boolean readCameraSoundForced() {
        if (android.os.SystemProperties.getBoolean("audio.camerasound.force", false) || this.mContext.getResources().getBoolean(android.R.bool.config_camera_sound_forced)) {
            return true;
        }
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
        if (subscriptionManager == null) {
            android.util.Log.e(TAG, "readCameraSoundForced cannot create SubscriptionManager!");
            return false;
        }
        for (int i : subscriptionManager.getActiveSubscriptionIdList(false)) {
            if (android.telephony.SubscriptionManager.getResourcesForSubId(this.mContext, i).getBoolean(android.R.bool.config_camera_sound_forced)) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.SuppressLint({"EmptyCatch"})
    public void muteAwaitConnection(@android.annotation.NonNull final int[] iArr, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, long j) {
        java.util.Objects.requireNonNull(iArr);
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        final android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        if (j <= 0 || iArr.length == 0) {
            throw new java.lang.IllegalArgumentException("Invalid timeOutMs/usagesToMute");
        }
        android.util.Log.i(TAG, "muteAwaitConnection dev:" + audioDeviceAttributes + " timeOutMs:" + j + " usages:" + java.util.Arrays.toString(iArr));
        if (this.mDeviceBroker.isDeviceConnected(retrieveBluetoothAddress)) {
            android.util.Log.i(TAG, "muteAwaitConnection ignored, device (" + audioDeviceAttributes + ") already connected");
            return;
        }
        synchronized (this.mMuteAwaitConnectionLock) {
            if (this.mMutingExpectedDevice != null) {
                android.util.Log.e(TAG, "muteAwaitConnection ignored, another in progress for device:" + this.mMutingExpectedDevice);
                throw new java.lang.IllegalStateException("muteAwaitConnection already in progress");
            }
            this.mMutingExpectedDevice = retrieveBluetoothAddress;
            this.mMutedUsagesAwaitingConnection = iArr;
            this.mPlaybackMonitor.muteAwaitConnection(iArr, retrieveBluetoothAddress, j);
        }
        dispatchMuteAwaitConnection(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda20
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioService.this.lambda$muteAwaitConnection$12(retrieveBluetoothAddress, iArr, (android.media.IMuteAwaitConnectionCallback) obj, (java.lang.Boolean) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$muteAwaitConnection$12(android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr, android.media.IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, java.lang.Boolean bool) {
        try {
            if (!bool.booleanValue()) {
                audioDeviceAttributes = anonymizeAudioDeviceAttributesUnchecked(audioDeviceAttributes);
            }
            iMuteAwaitConnectionCallback.dispatchOnMutedUntilConnection(audioDeviceAttributes, iArr);
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    @android.annotation.Nullable
    public android.media.AudioDeviceAttributes getMutingExpectedDevice() {
        android.media.AudioDeviceAttributes anonymizeAudioDeviceAttributes;
        super.getMutingExpectedDevice_enforcePermission();
        synchronized (this.mMuteAwaitConnectionLock) {
            anonymizeAudioDeviceAttributes = anonymizeAudioDeviceAttributes(this.mMutingExpectedDevice);
        }
        return anonymizeAudioDeviceAttributes;
    }

    @android.annotation.SuppressLint({"EmptyCatch"})
    public void cancelMuteAwaitConnection(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        final android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        android.util.Log.i(TAG, "cancelMuteAwaitConnection for device:" + audioDeviceAttributes);
        synchronized (this.mMuteAwaitConnectionLock) {
            try {
                if (this.mMutingExpectedDevice == null) {
                    android.util.Log.i(TAG, "cancelMuteAwaitConnection ignored, no expected device");
                    return;
                }
                if (!retrieveBluetoothAddress.equalTypeAddress(this.mMutingExpectedDevice)) {
                    android.util.Log.e(TAG, "cancelMuteAwaitConnection ignored, got " + audioDeviceAttributes + "] but expected device is" + this.mMutingExpectedDevice);
                    throw new java.lang.IllegalStateException("cancelMuteAwaitConnection for wrong device");
                }
                final int[] iArr = this.mMutedUsagesAwaitingConnection;
                this.mMutingExpectedDevice = null;
                this.mMutedUsagesAwaitingConnection = null;
                this.mPlaybackMonitor.cancelMuteAwaitConnection("cancelMuteAwaitConnection dev:" + audioDeviceAttributes);
                dispatchMuteAwaitConnection(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda19
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        com.android.server.audio.AudioService.this.lambda$cancelMuteAwaitConnection$13(retrieveBluetoothAddress, iArr, (android.media.IMuteAwaitConnectionCallback) obj, (java.lang.Boolean) obj2);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelMuteAwaitConnection$13(android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr, android.media.IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, java.lang.Boolean bool) {
        try {
            if (!bool.booleanValue()) {
                audioDeviceAttributes = anonymizeAudioDeviceAttributesUnchecked(audioDeviceAttributes);
            }
            iMuteAwaitConnectionCallback.dispatchOnUnmutedEvent(3, audioDeviceAttributes, iArr);
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void registerMuteAwaitConnectionDispatcher(@android.annotation.NonNull android.media.IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) {
        super.registerMuteAwaitConnectionDispatcher_enforcePermission();
        if (z) {
            this.mMuteAwaitConnectionDispatchers.register(iMuteAwaitConnectionCallback, java.lang.Boolean.valueOf(isBluetoothPrividged()));
        } else {
            this.mMuteAwaitConnectionDispatchers.unregister(iMuteAwaitConnectionCallback);
        }
    }

    @android.annotation.SuppressLint({"EmptyCatch"})
    void checkMuteAwaitConnection() {
        synchronized (this.mMuteAwaitConnectionLock) {
            try {
                if (this.mMutingExpectedDevice == null) {
                    return;
                }
                final android.media.AudioDeviceAttributes audioDeviceAttributes = this.mMutingExpectedDevice;
                final int[] iArr = this.mMutedUsagesAwaitingConnection;
                if (this.mDeviceBroker.isDeviceConnected(audioDeviceAttributes)) {
                    this.mMutingExpectedDevice = null;
                    this.mMutedUsagesAwaitingConnection = null;
                    this.mPlaybackMonitor.cancelMuteAwaitConnection("checkMuteAwaitConnection device " + audioDeviceAttributes + " connected, unmuting");
                    dispatchMuteAwaitConnection(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda8
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            com.android.server.audio.AudioService.this.lambda$checkMuteAwaitConnection$14(audioDeviceAttributes, iArr, (android.media.IMuteAwaitConnectionCallback) obj, (java.lang.Boolean) obj2);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkMuteAwaitConnection$14(android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr, android.media.IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, java.lang.Boolean bool) {
        try {
            if (!bool.booleanValue()) {
                audioDeviceAttributes = anonymizeAudioDeviceAttributesUnchecked(audioDeviceAttributes);
            }
            iMuteAwaitConnectionCallback.dispatchOnUnmutedEvent(1, audioDeviceAttributes, iArr);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @android.annotation.SuppressLint({"EmptyCatch"})
    /* renamed from: onMuteAwaitConnectionTimeout, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1(@android.annotation.NonNull final android.media.AudioDeviceAttributes audioDeviceAttributes) {
        synchronized (this.mMuteAwaitConnectionLock) {
            try {
                if (audioDeviceAttributes.equals(this.mMutingExpectedDevice)) {
                    android.util.Log.i(TAG, "muteAwaitConnection timeout, clearing expected device " + this.mMutingExpectedDevice);
                    final int[] iArr = this.mMutedUsagesAwaitingConnection;
                    this.mMutingExpectedDevice = null;
                    this.mMutedUsagesAwaitingConnection = null;
                    dispatchMuteAwaitConnection(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda4
                        @Override // java.util.function.BiConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                            com.android.server.audio.AudioService.lambda$onMuteAwaitConnectionTimeout$15(audioDeviceAttributes, iArr, (android.media.IMuteAwaitConnectionCallback) obj, (java.lang.Boolean) obj2);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onMuteAwaitConnectionTimeout$15(android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr, android.media.IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, java.lang.Boolean bool) {
        try {
            iMuteAwaitConnectionCallback.dispatchOnUnmutedEvent(2, audioDeviceAttributes, iArr);
        } catch (android.os.RemoteException e) {
        }
    }

    private void dispatchMuteAwaitConnection(java.util.function.BiConsumer<android.media.IMuteAwaitConnectionCallback, java.lang.Boolean> biConsumer) {
        int beginBroadcast = this.mMuteAwaitConnectionDispatchers.beginBroadcast();
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                biConsumer.accept(this.mMuteAwaitConnectionDispatchers.getBroadcastItem(i), (java.lang.Boolean) this.mMuteAwaitConnectionDispatchers.getBroadcastCookie(i));
            } catch (java.lang.Exception e) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList(1);
                }
                arrayList.add(this.mMuteAwaitConnectionDispatchers.getBroadcastItem(i));
            }
        }
        if (arrayList != null) {
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mMuteAwaitConnectionDispatchers.unregister((android.media.IMuteAwaitConnectionCallback) it.next());
            }
        }
        this.mMuteAwaitConnectionDispatchers.finishBroadcast();
    }

    public void registerDeviceVolumeBehaviorDispatcher(boolean z, @android.annotation.NonNull android.media.IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) {
        enforceQueryStateOrModifyRoutingPermission();
        java.util.Objects.requireNonNull(iDeviceVolumeBehaviorDispatcher);
        if (z) {
            this.mDeviceVolumeBehaviorDispatchers.register(iDeviceVolumeBehaviorDispatcher);
        } else {
            this.mDeviceVolumeBehaviorDispatchers.unregister(iDeviceVolumeBehaviorDispatcher);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) {
        int beginBroadcast = this.mDeviceVolumeBehaviorDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mDeviceVolumeBehaviorDispatchers.getBroadcastItem(i2).dispatchDeviceVolumeBehaviorChanged(audioDeviceAttributes, i);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mDeviceVolumeBehaviorDispatchers.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConfigurationChanged() {
        try {
            android.content.res.Configuration configuration = this.mContext.getResources().getConfiguration();
            this.mSoundDoseHelper.configureSafeMedia(false, TAG);
            boolean readCameraSoundForced = readCameraSoundForced();
            synchronized (this.mSettingsLock) {
                boolean z = readCameraSoundForced != this.mCameraSoundForced;
                this.mCameraSoundForced = readCameraSoundForced;
                if (z) {
                    if (!this.mIsSingleVolume) {
                        synchronized (com.android.server.audio.AudioService.VolumeStreamState.class) {
                            try {
                                com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = this.mStreamStates[7];
                                if (readCameraSoundForced) {
                                    volumeStreamState.setAllIndexesToMax();
                                    this.mRingerModeAffectedStreams &= -129;
                                } else {
                                    volumeStreamState.setAllIndexes(this.mStreamStates[1], TAG);
                                    this.mRingerModeAffectedStreams |= 128;
                                }
                            } finally {
                            }
                        }
                        setRingerModeInt(getRingerModeInternal(), false);
                    }
                    this.mDeviceBroker.setForceUse_Async(4, readCameraSoundForced ? 11 : 0, "onConfigurationChanged");
                    sendMsg(this.mAudioHandler, 10, 2, 0, 0, this.mStreamStates[7], 0);
                }
            }
            this.mVolumeController.setLayoutDirection(configuration.getLayoutDirection());
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error handling configuration change: ", e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.REMOTE_AUDIO_PLAYBACK")
    public void setRingtonePlayer(android.media.IRingtonePlayer iRingtonePlayer) {
        setRingtonePlayer_enforcePermission();
        this.mRingtonePlayer = iRingtonePlayer;
    }

    public android.media.IRingtonePlayer getRingtonePlayer() {
        return this.mRingtonePlayer;
    }

    public android.media.AudioRoutesInfo startWatchingRoutes(android.media.IAudioRoutesObserver iAudioRoutesObserver) {
        return this.mDeviceBroker.startWatchingRoutes(iAudioRoutesObserver);
    }

    public void disableSafeMediaVolume(java.lang.String str) {
        enforceVolumeController("disable the safe media volume");
        this.mSoundDoseHelper.disableSafeMediaVolume(str);
    }

    public void lowerVolumeToRs1(java.lang.String str) {
        enforceVolumeController("lowerVolumeToRs1");
        postLowerVolumeToRs1();
    }

    void postLowerVolumeToRs1() {
        sendMsg(this.mAudioHandler, 1007, 2, 0, 0, null, 0);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public float getOutputRs2UpperBound() {
        super.getOutputRs2UpperBound_enforcePermission();
        return this.mSoundDoseHelper.getOutputRs2UpperBound();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public void setOutputRs2UpperBound(float f) {
        super.setOutputRs2UpperBound_enforcePermission();
        this.mSoundDoseHelper.setOutputRs2UpperBound(f);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public float getCsd() {
        super.getCsd_enforcePermission();
        return this.mSoundDoseHelper.getCsd();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public void setCsd(float f) {
        super.setCsd_enforcePermission();
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            this.mSoundDoseHelper.resetCsdTimeouts();
        } else {
            this.mSoundDoseHelper.setCsd(f);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public void forceUseFrameworkMel(boolean z) {
        super.forceUseFrameworkMel_enforcePermission();
        this.mSoundDoseHelper.forceUseFrameworkMel(z);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public void forceComputeCsdOnAllDevices(boolean z) {
        super.forceComputeCsdOnAllDevices_enforcePermission();
        this.mSoundDoseHelper.forceComputeCsdOnAllDevices(z);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public boolean isCsdEnabled() {
        super.isCsdEnabled_enforcePermission();
        return this.mSoundDoseHelper.isCsdEnabled();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public boolean isCsdAsAFeatureAvailable() {
        super.isCsdAsAFeatureAvailable_enforcePermission();
        return this.mSoundDoseHelper.isCsdAsAFeatureAvailable();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public boolean isCsdAsAFeatureEnabled() {
        super.isCsdAsAFeatureEnabled_enforcePermission();
        return this.mSoundDoseHelper.isCsdAsAFeatureEnabled();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public void setCsdAsAFeatureEnabled(boolean z) {
        super.setCsdAsAFeatureEnabled_enforcePermission();
        this.mSoundDoseHelper.setCsdAsAFeatureEnabled(z);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public void setBluetoothAudioDeviceCategory_legacy(@android.annotation.NonNull java.lang.String str, boolean z, int i) {
        int i2;
        super.setBluetoothAudioDeviceCategory_legacy_enforcePermission();
        if (android.media.audio.Flags.automaticBtDeviceType()) {
            return;
        }
        java.util.Objects.requireNonNull(str);
        int i3 = 128;
        com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress = this.mDeviceBroker.findBtDeviceStateForAddress(str, z ? 536870912 : 128);
        if (z) {
            i3 = i == 3 ? 536870912 : 536870913;
        }
        if (z) {
            i2 = i == 3 ? 26 : 27;
        } else {
            i2 = 8;
        }
        if (findBtDeviceStateForAddress == null) {
            findBtDeviceStateForAddress = new com.android.server.audio.AdiDeviceState(i2, i3, str);
        }
        findBtDeviceStateForAddress.setAudioDeviceCategory(i);
        this.mDeviceBroker.addOrUpdateBtAudioDeviceCategoryInInventory(findBtDeviceStateForAddress);
        this.mDeviceBroker.postPersistAudioDeviceSettings();
        this.mSpatializerHelper.refreshDevice(findBtDeviceStateForAddress.getAudioDeviceAttributes());
        this.mSoundDoseHelper.setAudioDeviceCategory(str, i3, i == 3);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public int getBluetoothAudioDeviceCategory_legacy(@android.annotation.NonNull java.lang.String str, boolean z) {
        super.getBluetoothAudioDeviceCategory_legacy_enforcePermission();
        if (android.media.audio.Flags.automaticBtDeviceType()) {
            return 0;
        }
        com.android.server.audio.AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        java.util.Objects.requireNonNull(str);
        com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress = audioDeviceBroker.findBtDeviceStateForAddress(str, z ? 536870912 : 128);
        if (findBtDeviceStateForAddress == null) {
            return 0;
        }
        return findBtDeviceStateForAddress.getAudioDeviceCategory();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public boolean setBluetoothAudioDeviceCategory(@android.annotation.NonNull java.lang.String str, int i) {
        super.setBluetoothAudioDeviceCategory_enforcePermission();
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return false;
        }
        java.util.Objects.requireNonNull(str);
        if (isBluetoothAudioDeviceCategoryFixed(str)) {
            android.util.Log.w(TAG, "Cannot set fixed audio device type for address " + android.media.Utils.anonymizeBluetoothAddress(str));
            return false;
        }
        this.mDeviceBroker.addAudioDeviceWithCategoryInInventoryIfNeeded(str, i);
        return true;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public int getBluetoothAudioDeviceCategory(@android.annotation.NonNull java.lang.String str) {
        super.getBluetoothAudioDeviceCategory_enforcePermission();
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return 0;
        }
        return this.mDeviceBroker.getAndUpdateBtAdiDeviceStateCategoryForAddress(str);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public boolean isBluetoothAudioDeviceCategoryFixed(@android.annotation.NonNull java.lang.String str) {
        super.isBluetoothAudioDeviceCategoryFixed_enforcePermission();
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return false;
        }
        return this.mDeviceBroker.isBluetoothAudioDeviceCategoryFixed(str);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public void onUpdatedAdiDeviceState(com.android.server.audio.AdiDeviceState adiDeviceState) {
        if (adiDeviceState == null) {
            return;
        }
        this.mSpatializerHelper.refreshDevice(adiDeviceState.getAudioDeviceAttributes());
        this.mSoundDoseHelper.setAudioDeviceCategory(adiDeviceState.getDeviceAddress(), adiDeviceState.getInternalDeviceType(), adiDeviceState.getAudioDeviceCategory() == 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mHdmiClientLock"})
    public void updateHdmiCecSinkLocked(boolean z) {
        if (!hasDeviceVolumeBehavior(1024)) {
            if (z) {
                setDeviceVolumeBehaviorInternal(new android.media.AudioDeviceAttributes(1024, ""), 1, "AudioService.updateHdmiCecSinkLocked()");
            } else {
                setDeviceVolumeBehaviorInternal(new android.media.AudioDeviceAttributes(1024, ""), 0, "AudioService.updateHdmiCecSinkLocked()");
            }
            postUpdateVolumeStatesForAudioDevice(1024, "HdmiPlaybackClient.DisplayStatusCallback");
        }
    }

    private class MyHdmiControlStatusChangeListenerCallback implements android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener {
        private MyHdmiControlStatusChangeListenerCallback() {
        }

        public void onStatusChange(int i, boolean z) {
            synchronized (com.android.server.audio.AudioService.this.mHdmiClientLock) {
                try {
                    if (com.android.server.audio.AudioService.this.mHdmiManager == null) {
                        return;
                    }
                    boolean z2 = true;
                    if (i != 1) {
                        z2 = false;
                    }
                    com.android.server.audio.AudioService audioService = com.android.server.audio.AudioService.this;
                    if (!z2) {
                        z = false;
                    }
                    audioService.updateHdmiCecSinkLocked(z);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private class MyHdmiCecVolumeControlFeatureListener implements android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener {
        private MyHdmiCecVolumeControlFeatureListener() {
        }

        public void onHdmiCecVolumeControlFeature(int i) {
            synchronized (com.android.server.audio.AudioService.this.mHdmiClientLock) {
                try {
                    if (com.android.server.audio.AudioService.this.mHdmiManager == null) {
                        return;
                    }
                    com.android.server.audio.AudioService audioService = com.android.server.audio.AudioService.this;
                    boolean z = true;
                    if (i != 1) {
                        z = false;
                    }
                    audioService.mHdmiCecVolumeControlEnabled = z;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public int setHdmiSystemAudioSupported(boolean z) {
        synchronized (this.mHdmiClientLock) {
            try {
                if (this.mHdmiManager != null) {
                    if (this.mHdmiTvClient == null && this.mHdmiAudioSystemClient == null) {
                        android.util.Log.w(TAG, "Only Hdmi-Cec enabled TV or audio system device supportssystem audio mode.");
                        return 0;
                    }
                    if (this.mHdmiSystemAudioSupported != z) {
                        this.mHdmiSystemAudioSupported = z;
                        this.mDeviceBroker.setForceUse_Async(5, z ? 12 : 0, "setHdmiSystemAudioSupported");
                    }
                    r2 = getDeviceMaskForStream(3);
                }
                return r2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isHdmiSystemAudioSupported() {
        return this.mHdmiSystemAudioSupported;
    }

    private void initA11yMonitoring() {
        android.view.accessibility.AccessibilityManager accessibilityManager = (android.view.accessibility.AccessibilityManager) this.mContext.getSystemService("accessibility");
        updateDefaultStreamOverrideDelay(accessibilityManager.isTouchExplorationEnabled());
        updateA11yVolumeAlias(accessibilityManager.isAccessibilityVolumeStreamActive());
        accessibilityManager.addTouchExplorationStateChangeListener(this, null);
        accessibilityManager.addAccessibilityServicesStateChangeListener(this);
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public void onTouchExplorationStateChanged(boolean z) {
        updateDefaultStreamOverrideDelay(z);
    }

    private void updateDefaultStreamOverrideDelay(boolean z) {
        if (z) {
            sStreamOverrideDelayMs = 1000;
        } else {
            sStreamOverrideDelayMs = 0;
        }
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener
    public void onAccessibilityServicesStateChanged(android.view.accessibility.AccessibilityManager accessibilityManager) {
        updateA11yVolumeAlias(accessibilityManager.isAccessibilityVolumeStreamActive());
    }

    private void updateA11yVolumeAlias(boolean z) {
        if (!this.mIsSingleVolume && sIndependentA11yVolume != z) {
            sIndependentA11yVolume = z;
            updateStreamVolumeAlias(true, TAG);
            this.mVolumeController.setA11yMode(sIndependentA11yVolume ? 1 : 0);
            this.mVolumeController.postVolumeChanged(10, 0);
        }
    }

    public boolean isCameraSoundForced() {
        boolean z;
        synchronized (this.mSettingsLock) {
            z = this.mCameraSoundForced;
        }
        return z;
    }

    private void dumpRingerMode(java.io.PrintWriter printWriter) {
        printWriter.println("\nRinger mode: ");
        printWriter.println("- mode (internal) = " + RINGER_MODE_NAMES[this.mRingerMode]);
        printWriter.println("- mode (external) = " + RINGER_MODE_NAMES[this.mRingerModeExternal]);
        printWriter.println("- zen mode:" + android.provider.Settings.Global.zenModeToString(this.mNm.getZenMode()));
        dumpRingerModeStreams(printWriter, "affected", this.mRingerModeAffectedStreams);
        dumpRingerModeStreams(printWriter, "muted", sRingerAndZenModeMutedStreams);
        printWriter.print("- delegate = ");
        printWriter.println(this.mRingerModeDelegate);
    }

    private void dumpRingerModeStreams(java.io.PrintWriter printWriter, java.lang.String str, int i) {
        printWriter.print("- ringer mode ");
        printWriter.print(str);
        printWriter.print(" streams = 0x");
        printWriter.print(java.lang.Integer.toHexString(i));
        if (i != 0) {
            printWriter.print(" (");
            boolean z = true;
            for (int i2 = 0; i2 < android.media.AudioSystem.STREAM_NAMES.length; i2++) {
                int i3 = 1 << i2;
                if ((i & i3) != 0) {
                    if (!z) {
                        printWriter.print(',');
                    }
                    printWriter.print(android.media.AudioSystem.STREAM_NAMES[i2]);
                    i &= ~i3;
                    z = false;
                }
            }
            if (i != 0) {
                if (!z) {
                    printWriter.print(',');
                }
                printWriter.print(i);
            }
            printWriter.print(')');
        }
        printWriter.println();
    }

    private java.util.Set<java.lang.Integer> getAbsoluteVolumeDevicesWithBehavior(final int i) {
        return (java.util.Set) this.mAbsoluteVolumeDeviceInfoMap.entrySet().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getAbsoluteVolumeDevicesWithBehavior$16;
                lambda$getAbsoluteVolumeDevicesWithBehavior$16 = com.android.server.audio.AudioService.lambda$getAbsoluteVolumeDevicesWithBehavior$16(i, (java.util.Map.Entry) obj);
                return lambda$getAbsoluteVolumeDevicesWithBehavior$16;
            }
        }).map(new com.android.server.UiModeManagerService$Stub$$ExternalSyntheticLambda3()).collect(java.util.stream.Collectors.toSet());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAbsoluteVolumeDevicesWithBehavior$16(int i, java.util.Map.Entry entry) {
        return ((com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo) entry.getValue()).mDeviceVolumeBehavior == i;
    }

    private java.lang.String dumpDeviceTypes(@android.annotation.NonNull java.util.Set<java.lang.Integer> set) {
        java.util.Iterator<java.lang.Integer> it = set.iterator();
        if (!it.hasNext()) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("0x" + java.lang.Integer.toHexString(it.next().intValue()));
        while (it.hasNext()) {
            sb.append(",0x" + java.lang.Integer.toHexString(it.next().intValue()));
        }
        return sb.toString();
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            sLifecycleLogger.dump(printWriter);
            if (this.mAudioHandler != null) {
                printWriter.println("\nMessage handler (watch for unhandled messages):");
                this.mAudioHandler.dump(new android.util.PrintWriterPrinter(printWriter), "  ");
            } else {
                printWriter.println("\nMessage handler is null");
            }
            dumpFlags(printWriter);
            this.mHardeningEnforcer.dump(printWriter);
            this.mMediaFocusControl.dump(printWriter);
            dumpStreamStates(printWriter);
            dumpVolumeGroups(printWriter);
            dumpRingerMode(printWriter);
            dumpAudioMode(printWriter);
            printWriter.println("\nAudio routes:");
            printWriter.print("  mMainType=0x");
            printWriter.println(java.lang.Integer.toHexString(this.mDeviceBroker.getCurAudioRoutes().mainType));
            printWriter.print("  mBluetoothName=");
            printWriter.println(this.mDeviceBroker.getCurAudioRoutes().bluetoothName);
            printWriter.println("\nOther state:");
            printWriter.print("  mUseVolumeGroupAliases=");
            printWriter.println(this.mUseVolumeGroupAliases);
            printWriter.print("  mVolumeController=");
            printWriter.println(this.mVolumeController);
            this.mSoundDoseHelper.dump(printWriter);
            printWriter.print("  sIndependentA11yVolume=");
            printWriter.println(sIndependentA11yVolume);
            printWriter.print("  mCameraSoundForced=");
            printWriter.println(isCameraSoundForced());
            printWriter.print("  mHasVibrator=");
            printWriter.println(this.mHasVibrator);
            printWriter.print("  mVolumePolicy=");
            printWriter.println(this.mVolumePolicy);
            printWriter.print("  mAvrcpAbsVolSupported=");
            printWriter.println(this.mAvrcpAbsVolSupported);
            printWriter.print("  mBtScoOnByApp=");
            printWriter.println(this.mBtScoOnByApp);
            printWriter.print("  mIsSingleVolume=");
            printWriter.println(this.mIsSingleVolume);
            printWriter.print("  mUseFixedVolume=");
            printWriter.println(this.mUseFixedVolume);
            printWriter.print("  mNotifAliasRing=");
            printWriter.println(this.mNotifAliasRing);
            printWriter.print("  mFixedVolumeDevices=");
            printWriter.println(dumpDeviceTypes(this.mFixedVolumeDevices));
            printWriter.print("  mFullVolumeDevices=");
            printWriter.println(dumpDeviceTypes(this.mFullVolumeDevices));
            printWriter.print("  absolute volume devices=");
            printWriter.println(dumpDeviceTypes(getAbsoluteVolumeDevicesWithBehavior(3)));
            printWriter.print("  adjust-only absolute volume devices=");
            printWriter.println(dumpDeviceTypes(getAbsoluteVolumeDevicesWithBehavior(5)));
            printWriter.print("  pre-scale for bluetooth absolute volume ");
            com.android.media.audio.Flags.disablePrescaleAbsoluteVolume();
            printWriter.println("= disabled");
            printWriter.print("  mExtVolumeController=");
            printWriter.println(this.mExtVolumeController);
            printWriter.print("  mHdmiAudioSystemClient=");
            printWriter.println(this.mHdmiAudioSystemClient);
            printWriter.print("  mHdmiPlaybackClient=");
            printWriter.println(this.mHdmiPlaybackClient);
            printWriter.print("  mHdmiTvClient=");
            printWriter.println(this.mHdmiTvClient);
            printWriter.print("  mHdmiSystemAudioSupported=");
            printWriter.println(this.mHdmiSystemAudioSupported);
            synchronized (this.mHdmiClientLock) {
                printWriter.print("  mHdmiCecVolumeControlEnabled=");
                printWriter.println(this.mHdmiCecVolumeControlEnabled);
            }
            printWriter.print("  mIsCallScreeningModeSupported=");
            printWriter.println(this.mIsCallScreeningModeSupported);
            printWriter.println("  mic mute FromSwitch=" + this.mMicMuteFromSwitch + " FromRestrictions=" + this.mMicMuteFromRestrictions + " FromApi=" + this.mMicMuteFromApi + " from system=" + this.mMicMuteFromSystemCached);
            printWriter.print("  mMasterMute=");
            printWriter.println(this.mMasterMute.get());
            dumpAccessibilityServiceUids(printWriter);
            dumpAssistantServicesUids(printWriter);
            printWriter.print("  supportsBluetoothVariableLatency=");
            printWriter.println(android.media.AudioSystem.supportsBluetoothVariableLatency());
            printWriter.print("  isBluetoothVariableLatencyEnabled=");
            printWriter.println(android.media.AudioSystem.isBluetoothVariableLatencyEnabled());
            dumpAudioPolicies(printWriter);
            this.mDynPolicyLogger.dump(printWriter);
            this.mPlaybackMonitor.dump(printWriter);
            this.mRecordMonitor.dump(printWriter);
            printWriter.println("\nAudioDeviceBroker:");
            this.mDeviceBroker.dump(printWriter, "  ");
            printWriter.println("\nSoundEffects:");
            this.mSfxHelper.dump(printWriter, "  ");
            printWriter.println("\n");
            printWriter.println("\nEvent logs:");
            this.mModeLogger.dump(printWriter);
            printWriter.println("\n");
            sDeviceLogger.dump(printWriter);
            printWriter.println("\n");
            sForceUseLogger.dump(printWriter);
            printWriter.println("\n");
            sVolumeLogger.dump(printWriter);
            printWriter.println("\n");
            sMuteLogger.dump(printWriter);
            printWriter.println("\n");
            dumpSupportedSystemUsage(printWriter);
            printWriter.println("\n");
            printWriter.println("\nSpatial audio:");
            printWriter.println("mHasSpatializerEffect:" + this.mHasSpatializerEffect + " (effect present)");
            printWriter.println("isSpatializerEnabled:" + isSpatializerEnabled() + " (routing dependent)");
            this.mSpatializerHelper.dump(printWriter);
            sSpatialLogger.dump(printWriter);
            printWriter.println("\n");
            printWriter.println("\nLoudness alignment:");
            this.mLoudnessCodecHelper.dump(printWriter);
            this.mAudioSystem.dump(printWriter);
        }
    }

    private void dumpSupportedSystemUsage(java.io.PrintWriter printWriter) {
        printWriter.println("Supported System Usages:");
        synchronized (this.mSupportedSystemUsagesLock) {
            for (int i = 0; i < this.mSupportedSystemUsages.length; i++) {
                try {
                    printWriter.printf("\t%s\n", android.media.AudioAttributes.usageToString(this.mSupportedSystemUsages[i]));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void dumpAssistantServicesUids(java.io.PrintWriter printWriter) {
        synchronized (this.mSettingsLock) {
            try {
                if (this.mAssistantUids.size() > 0) {
                    printWriter.println("  Assistant service UIDs:");
                    java.util.Iterator<java.lang.Integer> it = this.mAssistantUids.iterator();
                    while (it.hasNext()) {
                        printWriter.println("  - " + it.next().intValue());
                    }
                } else {
                    printWriter.println("  No Assistant service Uids.");
                }
            } finally {
            }
        }
    }

    private void dumpAccessibilityServiceUids(java.io.PrintWriter printWriter) {
        synchronized (this.mSupportedSystemUsagesLock) {
            try {
                if (this.mAccessibilityServiceUids != null && this.mAccessibilityServiceUids.length > 0) {
                    printWriter.println("  Accessibility service Uids:");
                    for (int i : this.mAccessibilityServiceUids) {
                        printWriter.println("  - " + i);
                    }
                } else {
                    printWriter.println("  No accessibility service Uids.");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void readAndSetLowRamDevice() {
        long j;
        boolean isLowRamDeviceStatic = android.app.ActivityManager.isLowRamDeviceStatic();
        try {
            android.app.ActivityManager.MemoryInfo memoryInfo = new android.app.ActivityManager.MemoryInfo();
            android.app.ActivityManager.getService().getMemoryInfo(memoryInfo);
            j = memoryInfo.totalMem;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Cannot obtain MemoryInfo from ActivityManager, assume low memory device");
            j = 1073741824;
            isLowRamDeviceStatic = true;
        }
        int lowRamDevice = android.media.AudioSystem.setLowRamDevice(isLowRamDeviceStatic, j);
        if (lowRamDevice != 0) {
            android.util.Log.w(TAG, "AudioFlinger informed of device's low RAM attribute; status " + lowRamDevice);
        }
    }

    private void enforceVolumeController(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE", "Only SystemUI can " + str);
    }

    public void setVolumeController(final android.media.IVolumeController iVolumeController) {
        enforceVolumeController("set the volume controller");
        if (this.mVolumeController.isSameBinder(iVolumeController)) {
            return;
        }
        this.mVolumeController.postDismiss();
        if (iVolumeController != null) {
            try {
                iVolumeController.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.audio.AudioService.7
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        if (com.android.server.audio.AudioService.this.mVolumeController.isSameBinder(iVolumeController)) {
                            android.util.Log.w(com.android.server.audio.AudioService.TAG, "Current remote volume controller died, unregistering");
                            com.android.server.audio.AudioService.this.setVolumeController(null);
                        }
                    }
                }, 0);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mVolumeController.setController(iVolumeController);
    }

    @android.annotation.Nullable
    public android.media.IVolumeController getVolumeController() {
        enforceVolumeController("get the volume controller");
        return this.mVolumeController.getController();
    }

    public void notifyVolumeControllerVisible(android.media.IVolumeController iVolumeController, boolean z) {
        enforceVolumeController("notify about volume controller visibility");
        if (!this.mVolumeController.isSameBinder(iVolumeController)) {
            return;
        }
        this.mVolumeController.setVisible(z);
    }

    public void setVolumePolicy(android.media.VolumePolicy volumePolicy) {
        enforceVolumeController("set volume policy");
        if (volumePolicy != null && !volumePolicy.equals(this.mVolumePolicy)) {
            this.mVolumePolicy = volumePolicy;
        }
    }

    public class VolumeController implements com.android.server.audio.AudioService.ISafeHearingVolumeController {
        private static final java.lang.String TAG = "VolumeController";
        private android.media.IVolumeController mController;
        private int mLongPressTimeout;
        private long mNextLongPress;
        private boolean mVisible;

        public VolumeController() {
        }

        public void setController(android.media.IVolumeController iVolumeController) {
            this.mController = iVolumeController;
            this.mVisible = false;
        }

        public android.media.IVolumeController getController() {
            return this.mController;
        }

        public void loadSettings(android.content.ContentResolver contentResolver) {
            this.mLongPressTimeout = com.android.server.audio.AudioService.this.mSettings.getSecureIntForUser(contentResolver, "long_press_timeout", 500, -2);
        }

        public boolean suppressAdjustment(int i, int i2, boolean z) {
            if (z || i != 3 || this.mController == null) {
                return false;
            }
            if (i == 3 && com.android.server.audio.AudioService.this.mAudioSystem.isStreamActive(3, this.mLongPressTimeout)) {
                return false;
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if ((i2 & 1) != 0 && !this.mVisible) {
                if (this.mNextLongPress < uptimeMillis) {
                    this.mNextLongPress = uptimeMillis + this.mLongPressTimeout;
                }
                return true;
            }
            if (this.mNextLongPress <= 0) {
                return false;
            }
            if (uptimeMillis <= this.mNextLongPress) {
                return true;
            }
            this.mNextLongPress = 0L;
            return false;
        }

        public void setVisible(boolean z) {
            this.mVisible = z;
        }

        public boolean isSameBinder(android.media.IVolumeController iVolumeController) {
            return java.util.Objects.equals(asBinder(), binder(iVolumeController));
        }

        public android.os.IBinder asBinder() {
            return binder(this.mController);
        }

        private android.os.IBinder binder(android.media.IVolumeController iVolumeController) {
            if (iVolumeController == null) {
                return null;
            }
            return iVolumeController.asBinder();
        }

        public java.lang.String toString() {
            return "VolumeController(" + asBinder() + ",mVisible=" + this.mVisible + ")";
        }

        @Override // com.android.server.audio.AudioService.ISafeHearingVolumeController
        public void postDisplaySafeVolumeWarning(int i) {
            if (this.mController == null) {
                return;
            }
            try {
                this.mController.displaySafeVolumeWarning(i | 1);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error calling displaySafeVolumeWarning", e);
            }
        }

        @Override // com.android.server.audio.AudioService.ISafeHearingVolumeController
        public void postDisplayCsdWarning(int i, int i2) {
            if (this.mController == null) {
                android.util.Log.e(TAG, "Unable to display CSD warning, no controller");
                return;
            }
            try {
                this.mController.displayCsdWarning(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error calling displayCsdWarning for warning " + i, e);
            }
        }

        public void postVolumeChanged(int i, int i2) {
            if (this.mController == null) {
                return;
            }
            try {
                this.mController.volumeChanged(i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error calling volumeChanged", e);
            }
        }

        public void postMasterMuteChanged(int i) {
            if (this.mController == null) {
                return;
            }
            try {
                this.mController.masterMuteChanged(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error calling masterMuteChanged", e);
            }
        }

        public void setLayoutDirection(int i) {
            if (this.mController == null) {
                return;
            }
            try {
                this.mController.setLayoutDirection(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error calling setLayoutDirection", e);
            }
        }

        public void postDismiss() {
            if (this.mController == null) {
                return;
            }
            try {
                this.mController.dismiss();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error calling dismiss", e);
            }
        }

        public void setA11yMode(int i) {
            if (this.mController == null) {
                return;
            }
            try {
                this.mController.setA11yMode(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error calling setA11Mode", e);
            }
        }
    }

    final class AudioServiceInternal extends android.media.AudioManagerInternal {
        AudioServiceInternal() {
        }

        public void setRingerModeDelegate(android.media.AudioManagerInternal.RingerModeDelegate ringerModeDelegate) {
            com.android.server.audio.AudioService.this.mRingerModeDelegate = ringerModeDelegate;
            if (com.android.server.audio.AudioService.this.mRingerModeDelegate != null) {
                synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                    com.android.server.audio.AudioService.this.updateRingerAndZenModeAffectedStreams();
                }
                setRingerModeInternal(getRingerModeInternal(), "AS.AudioService.setRingerModeDelegate");
            }
        }

        public int getRingerModeInternal() {
            return com.android.server.audio.AudioService.this.getRingerModeInternal();
        }

        public void setRingerModeInternal(int i, java.lang.String str) {
            com.android.server.audio.AudioService.this.setRingerModeInternal(i, str);
        }

        public void silenceRingerModeInternal(java.lang.String str) {
            com.android.server.audio.AudioService.this.silenceRingerModeInternal(str);
        }

        public void updateRingerModeAffectedStreamsInternal() {
            synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                try {
                    if (com.android.server.audio.AudioService.this.updateRingerAndZenModeAffectedStreams()) {
                        com.android.server.audio.AudioService.this.setRingerModeInt(getRingerModeInternal(), false);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void addAssistantServiceUid(int i) {
            com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 44, 2, i, 0, null, 0);
        }

        public void removeAssistantServiceUid(int i) {
            com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 45, 2, i, 0, null, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0037 A[Catch: all -> 0x0017, LOOP:0: B:19:0x0037->B:23:0x0050, LOOP_START, PHI: r2
          0x0037: PHI (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:18:0x0034, B:23:0x0050] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {all -> 0x0017, blocks: (B:4:0x0007, B:6:0x000d, B:7:0x005f, B:12:0x0019, B:14:0x0023, B:19:0x0037, B:21:0x0040, B:27:0x0056, B:23:0x0050), top: B:3:0x0007 }] */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0056 A[Catch: all -> 0x0017, TryCatch #0 {all -> 0x0017, blocks: (B:4:0x0007, B:6:0x000d, B:7:0x005f, B:12:0x0019, B:14:0x0023, B:19:0x0037, B:21:0x0040, B:27:0x0056, B:23:0x0050), top: B:3:0x0007 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setActiveAssistantServicesUids(android.util.IntArray intArray) {
            boolean z;
            synchronized (com.android.server.audio.AudioService.this.mSettingsLock) {
                try {
                    if (intArray.size() == 0) {
                        com.android.server.audio.AudioService.this.mActiveAssistantServiceUids = com.android.server.audio.AudioService.NO_ACTIVE_ASSISTANT_SERVICE_UIDS;
                    } else {
                        boolean z2 = true;
                        if (com.android.server.audio.AudioService.this.mActiveAssistantServiceUids != null && com.android.server.audio.AudioService.this.mActiveAssistantServiceUids.length == intArray.size()) {
                            z = false;
                            if (!z) {
                                for (int i = 0; i < com.android.server.audio.AudioService.this.mActiveAssistantServiceUids.length; i++) {
                                    if (intArray.get(i) != com.android.server.audio.AudioService.this.mActiveAssistantServiceUids[i]) {
                                        break;
                                    }
                                }
                            }
                            z2 = z;
                            if (z2) {
                                com.android.server.audio.AudioService.this.mActiveAssistantServiceUids = intArray.toArray();
                            }
                        }
                        z = true;
                        if (!z) {
                        }
                        z2 = z;
                        if (z2) {
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 46, 0, 0, 0, null, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x003d A[Catch: all -> 0x001d, LOOP:0: B:22:0x003d->B:26:0x0056, LOOP_START, PHI: r2
          0x003d: PHI (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:21:0x003a, B:26:0x0056] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {all -> 0x001d, blocks: (B:8:0x0010, B:10:0x0016, B:11:0x0065, B:12:0x0075, B:15:0x001f, B:17:0x0029, B:22:0x003d, B:24:0x0046, B:30:0x005c, B:26:0x0056), top: B:7:0x0010 }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x005c A[Catch: all -> 0x001d, TryCatch #0 {all -> 0x001d, blocks: (B:8:0x0010, B:10:0x0016, B:11:0x0065, B:12:0x0075, B:15:0x001f, B:17:0x0029, B:22:0x003d, B:24:0x0046, B:30:0x005c, B:26:0x0056), top: B:7:0x0010 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setAccessibilityServiceUids(android.util.IntArray intArray) {
            boolean z;
            if (com.android.server.audio.AudioService.this.isPlatformAutomotive()) {
                return;
            }
            synchronized (com.android.server.audio.AudioService.this.mAccessibilityServiceUidsLock) {
                try {
                    if (intArray.size() == 0) {
                        com.android.server.audio.AudioService.this.mAccessibilityServiceUids = null;
                    } else {
                        boolean z2 = true;
                        if (com.android.server.audio.AudioService.this.mAccessibilityServiceUids != null && com.android.server.audio.AudioService.this.mAccessibilityServiceUids.length == intArray.size()) {
                            z = false;
                            if (!z) {
                                for (int i = 0; i < com.android.server.audio.AudioService.this.mAccessibilityServiceUids.length; i++) {
                                    if (intArray.get(i) != com.android.server.audio.AudioService.this.mAccessibilityServiceUids[i]) {
                                        break;
                                    }
                                }
                            }
                            z2 = z;
                            if (z2) {
                                com.android.server.audio.AudioService.this.mAccessibilityServiceUids = intArray.toArray();
                            }
                        }
                        z = true;
                        if (!z) {
                        }
                        z2 = z;
                        if (z2) {
                        }
                    }
                    com.android.server.audio.AudioService.sendMsg(com.android.server.audio.AudioService.this.mAudioHandler, 35, 0, 0, 0, null, 0);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setInputMethodServiceUid(int i) {
            synchronized (com.android.server.audio.AudioService.this.mInputMethodServiceUidLock) {
                try {
                    if (com.android.server.audio.AudioService.this.mInputMethodServiceUid != i) {
                        com.android.server.audio.AudioService.this.mAudioSystem.setCurrentImeUid(i);
                        com.android.server.audio.AudioService.this.mInputMethodServiceUid = i;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateAccessibilityServiceUids() {
        int[] iArr;
        synchronized (this.mAccessibilityServiceUidsLock) {
            iArr = this.mAccessibilityServiceUids;
        }
        android.media.AudioSystem.setA11yServicesUids(iArr);
    }

    public java.lang.String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, android.media.projection.IMediaProjection iMediaProjection) {
        android.media.AudioSystem.setDynamicPolicyCallback(this.mDynPolicyCallback);
        if (!isPolicyRegisterAllowed(audioPolicyConfig, z2 || z3 || z, z4, iMediaProjection)) {
            android.util.Slog.w(TAG, "Permission denied to register audio policy for pid " + android.os.Binder.getCallingPid() + " / uid " + android.os.Binder.getCallingUid() + ", need system permission or a MediaProjection that can project audio");
            return null;
        }
        synchronized (this.mAudioPolicies) {
            if (this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                android.util.Slog.e(TAG, "Cannot re-register policy");
                return null;
            }
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy audioPolicyProxy = new com.android.server.audio.AudioService.AudioPolicyProxy(audioPolicyConfig, iAudioPolicyCallback, z, z2, z3, z4, iMediaProjection);
                iAudioPolicyCallback.asBinder().linkToDeath(audioPolicyProxy, 0);
                this.mDynPolicyLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("registerAudioPolicy for " + iAudioPolicyCallback.asBinder() + " u/pid:" + android.os.Binder.getCallingUid() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.Binder.getCallingPid() + " with config:" + audioPolicyProxy.toCompactLogString()).printLog(TAG));
                java.lang.String registrationId = audioPolicyProxy.getRegistrationId();
                this.mAudioPolicies.put(iAudioPolicyCallback.asBinder(), audioPolicyProxy);
                return registrationId;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Audio policy registration failed, could not link to " + iAudioPolicyCallback + " binder death", e);
                return null;
            } catch (java.lang.IllegalStateException e2) {
                android.util.Slog.w(TAG, "Audio policy registration failed for binder " + iAudioPolicyCallback, e2);
                return null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPolicyClientDeath(java.util.List<java.lang.String> list) {
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            if (this.mPlaybackMonitor.hasActiveMediaPlaybackOnSubmixWithAddress(it.next())) {
                this.mDeviceBroker.postBroadcastBecomingNoisy();
                return;
            }
        }
    }

    private boolean isPolicyRegisterAllowed(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, boolean z, boolean z2, android.media.projection.IMediaProjection iMediaProjection) {
        boolean z3;
        if (z || z2) {
            z3 = true;
        } else if (!audioPolicyConfig.getMixes().isEmpty()) {
            z3 = false;
        } else {
            z3 = true;
        }
        java.util.Iterator it = audioPolicyConfig.getMixes().iterator();
        java.util.ArrayList arrayList = null;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        while (it.hasNext()) {
            android.media.audiopolicy.AudioMix audioMix = (android.media.audiopolicy.AudioMix) it.next();
            if (audioMix.getRule().allowPrivilegedMediaPlaybackCapture()) {
                java.lang.String canBeUsedForPrivilegedMediaCapture = android.media.audiopolicy.AudioMix.canBeUsedForPrivilegedMediaCapture(audioMix.getFormat());
                if (canBeUsedForPrivilegedMediaCapture != null) {
                    android.util.Log.e(TAG, canBeUsedForPrivilegedMediaCapture);
                    return false;
                }
                z4 = true;
            }
            if (audioMix.containsMatchAttributeRuleForUsage(2) && audioMix.getRouteFlags() == 3) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(audioMix);
            }
            if (audioMix.getRouteFlags() == 3 && iMediaProjection != null) {
                z5 = true;
            } else if (audioMix.isForCallRedirection()) {
                z6 = true;
            } else if (audioMix.containsMatchAttributeRuleForUsage(2)) {
                z3 = true;
            }
        }
        if (z4 && !callerHasPermission("android.permission.CAPTURE_MEDIA_OUTPUT") && !callerHasPermission("android.permission.CAPTURE_AUDIO_OUTPUT")) {
            android.util.Log.e(TAG, "Privileged audio capture requires CAPTURE_MEDIA_OUTPUT or CAPTURE_AUDIO_OUTPUT system permission");
            return false;
        }
        if (arrayList != null && arrayList.size() > 0) {
            if (!callerHasPermission("android.permission.CAPTURE_VOICE_COMMUNICATION_OUTPUT")) {
                android.util.Log.e(TAG, "Audio capture for voice communication requires CAPTURE_VOICE_COMMUNICATION_OUTPUT system permission");
                return false;
            }
            java.util.Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((android.media.audiopolicy.AudioMix) it2.next()).getRule().setVoiceCommunicationCaptureAllowed(true);
            }
        }
        if (z5 && !canProjectAudio(iMediaProjection)) {
            return false;
        }
        if (z3 && !callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING")) {
            android.util.Log.e(TAG, "Can not capture audio without MODIFY_AUDIO_ROUTING");
            return false;
        }
        if (!z6 || callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) {
            return true;
        }
        android.util.Log.e(TAG, "Can not capture audio without CALL_AUDIO_INTERCEPTION");
        return false;
    }

    private boolean callerHasPermission(java.lang.String str) {
        return this.mContext.checkCallingPermission(str) == 0;
    }

    private boolean canProjectAudio(android.media.projection.IMediaProjection iMediaProjection) {
        if (iMediaProjection == null) {
            android.util.Log.e(TAG, "MediaProjection is null");
            return false;
        }
        android.media.projection.IMediaProjectionManager projectionService = getProjectionService();
        if (projectionService == null) {
            android.util.Log.e(TAG, "Can't get service IMediaProjectionManager");
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!projectionService.isCurrentProjection(iMediaProjection)) {
                android.util.Log.w(TAG, "App passed invalid MediaProjection token");
                return false;
            }
            try {
                if (iMediaProjection.canProjectAudio()) {
                    return true;
                }
                android.util.Log.w(TAG, "App passed MediaProjection that can not project audio");
                return false;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Can't call .canProjectAudio() on valid IMediaProjection" + iMediaProjection.asBinder(), e);
                return false;
            }
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Can't call .isCurrentProjection() on IMediaProjectionManager" + projectionService.asBinder(), e2);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private android.media.projection.IMediaProjectionManager getProjectionService() {
        if (this.mProjectionService == null) {
            this.mProjectionService = android.media.projection.IMediaProjectionManager.Stub.asInterface(android.os.ServiceManager.getService("media_projection"));
        }
        return this.mProjectionService;
    }

    public void unregisterAudioPolicyAsync(@android.annotation.Nullable android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        unregisterAudioPolicyInt(iAudioPolicyCallback, "unregisterAudioPolicyAsync");
    }

    public void unregisterAudioPolicy(@android.annotation.Nullable android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        unregisterAudioPolicyInt(iAudioPolicyCallback, "unregisterAudioPolicy");
    }

    private void unregisterAudioPolicyInt(@android.annotation.NonNull android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, java.lang.String str) {
        this.mDynPolicyLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(str + " for " + iAudioPolicyCallback.asBinder()).printLog(TAG));
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy remove = this.mAudioPolicies.remove(iAudioPolicyCallback.asBinder());
                if (remove == null) {
                    android.util.Slog.w(TAG, "Trying to unregister unknown audio policy for pid " + android.os.Binder.getCallingPid() + " / uid " + android.os.Binder.getCallingUid());
                    return;
                }
                iAudioPolicyCallback.asBinder().unlinkToDeath(remove, 0);
                remove.release();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mAudioPolicies"})
    private com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, java.lang.String str) {
        if (!(this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0)) {
            android.util.Slog.w(TAG, str + " for pid " + android.os.Binder.getCallingPid() + " / uid " + android.os.Binder.getCallingUid() + ", need MODIFY_AUDIO_ROUTING");
            return null;
        }
        com.android.server.audio.AudioService.AudioPolicyProxy audioPolicyProxy = this.mAudioPolicies.get(iAudioPolicyCallback.asBinder());
        if (audioPolicyProxy == null) {
            android.util.Slog.w(TAG, str + " for pid " + android.os.Binder.getCallingPid() + " / uid " + android.os.Binder.getCallingUid() + ", unregistered policy");
            return null;
        }
        return audioPolicyProxy;
    }

    public java.util.List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() {
        java.util.List<android.media.audiopolicy.AudioMix> registeredPolicyMixes;
        if (!android.media.audiopolicy.Flags.audioMixTestApi()) {
            return java.util.Collections.emptyList();
        }
        synchronized (this.mAudioPolicies) {
            registeredPolicyMixes = this.mAudioSystem.getRegisteredPolicyMixes();
        }
        return registeredPolicyMixes;
    }

    public int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot add AudioMix in audio policy");
                int i = -1;
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (checkUpdateForPolicy.addMixes(audioPolicyConfig.getMixes()) == 0) {
                    i = 0;
                }
                return i;
            } finally {
            }
        }
    }

    public int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot add AudioMix in audio policy");
                int i = -1;
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (android.media.audiopolicy.Flags.audioMixOwnership()) {
                    java.util.Iterator it = audioPolicyConfig.getMixes().iterator();
                    while (it.hasNext()) {
                        if (!checkUpdateForPolicy.getMixes().contains((android.media.audiopolicy.AudioMix) it.next())) {
                            android.util.Slog.e(TAG, "removeMixForPolicy attempted to unregister AudioMix(es) not belonging to the AudioPolicy");
                            return -1;
                        }
                    }
                }
                if (checkUpdateForPolicy.removeMixes(audioPolicyConfig.getMixes()) == 0) {
                    i = 0;
                }
                return i;
            } finally {
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public int updateMixingRulesForPolicy(@android.annotation.NonNull android.media.audiopolicy.AudioMix[] audioMixArr, @android.annotation.NonNull android.media.audiopolicy.AudioMixingRule[] audioMixingRuleArr, @android.annotation.NonNull android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        super.updateMixingRulesForPolicy_enforcePermission();
        java.util.Objects.requireNonNull(audioMixArr);
        java.util.Objects.requireNonNull(audioMixingRuleArr);
        java.util.Objects.requireNonNull(iAudioPolicyCallback);
        if (audioMixArr.length != audioMixingRuleArr.length) {
            android.util.Log.e(TAG, "Provided list of audio mixes to update and corresponding mixing rules have mismatching length (mixesToUpdate.length = " + audioMixArr.length + ", updatedMixingRules.length = " + audioMixingRuleArr.length + ").");
            return -1;
        }
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot add AudioMix in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                return checkUpdateForPolicy.updateMixingRules(audioMixArr, audioMixingRuleArr) == 0 ? 0 : -1;
            } finally {
            }
        }
    }

    public int setUidDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull java.lang.String[] strArr) {
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change device affinity in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (!checkUpdateForPolicy.hasMixRoutedToDevices(iArr, strArr)) {
                    return -1;
                }
                return checkUpdateForPolicy.setUidDeviceAffinities(i, iArr, strArr);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int setUserIdDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull java.lang.String[] strArr) {
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change device affinity in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (!checkUpdateForPolicy.hasMixRoutedToDevices(iArr, strArr)) {
                    return -1;
                }
                return checkUpdateForPolicy.setUserIdDeviceAffinities(i, iArr, strArr);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int removeUidDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) {
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot remove device affinity in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                return checkUpdateForPolicy.removeUidDeviceAffinities(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int removeUserIdDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) {
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot remove device affinity in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                return checkUpdateForPolicy.removeUserIdDeviceAffinities(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int setFocusPropertiesForPolicy(int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        synchronized (this.mAudioPolicies) {
            try {
                com.android.server.audio.AudioService.AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change audio policy focus properties");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                    android.util.Slog.e(TAG, "Cannot change audio policy focus properties, unregistered policy");
                    return -1;
                }
                boolean z = true;
                if (i == 1) {
                    java.util.Iterator<com.android.server.audio.AudioService.AudioPolicyProxy> it = this.mAudioPolicies.values().iterator();
                    while (it.hasNext()) {
                        if (it.next().mFocusDuckBehavior == 1) {
                            android.util.Slog.e(TAG, "Cannot change audio policy ducking behavior, already handled");
                            return -1;
                        }
                    }
                }
                checkUpdateForPolicy.mFocusDuckBehavior = i;
                com.android.server.audio.MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
                if (i != 1) {
                    z = false;
                }
                mediaFocusControl.setDuckingInExtPolicyAvailable(z);
                return 0;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public java.util.List<android.media.AudioFocusInfo> getFocusStack() {
        super.getFocusStack_enforcePermission();
        return this.mMediaFocusControl.getFocusStack();
    }

    public boolean sendFocusLoss(@android.annotation.NonNull android.media.AudioFocusInfo audioFocusInfo, @android.annotation.NonNull android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        java.util.Objects.requireNonNull(audioFocusInfo);
        java.util.Objects.requireNonNull(iAudioPolicyCallback);
        enforceModifyAudioRoutingPermission();
        if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
            throw new java.lang.IllegalStateException("Only registered AudioPolicy can change focus");
        }
        if (!this.mAudioPolicies.get(iAudioPolicyCallback.asBinder()).mHasFocusListener) {
            throw new java.lang.IllegalStateException("AudioPolicy must have focus listener to change focus");
        }
        return this.mMediaFocusControl.sendFocusLoss(audioFocusInfo);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public int setFadeManagerConfigurationForFocusLoss(@android.annotation.NonNull android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        super.setFadeManagerConfigurationForFocusLoss_enforcePermission();
        ensureFadeManagerConfigIsEnabled();
        java.util.Objects.requireNonNull(fadeManagerConfiguration, "Fade manager config for focus loss cannot be null");
        validateFadeManagerConfiguration(fadeManagerConfiguration);
        return this.mPlaybackMonitor.setFadeManagerConfiguration(-1, fadeManagerConfiguration);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public int clearFadeManagerConfigurationForFocusLoss() {
        super.clearFadeManagerConfigurationForFocusLoss_enforcePermission();
        ensureFadeManagerConfigIsEnabled();
        return this.mPlaybackMonitor.clearFadeManagerConfiguration(-1);
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public android.media.FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() {
        super.getFadeManagerConfigurationForFocusLoss_enforcePermission();
        ensureFadeManagerConfigIsEnabled();
        return this.mPlaybackMonitor.getFadeManagerConfiguration(-1);
    }

    @android.annotation.Nullable
    public android.media.AudioHalVersionInfo getHalVersion() {
        for (android.media.AudioHalVersionInfo audioHalVersionInfo : android.media.AudioHalVersionInfo.VERSIONS) {
            try {
                java.lang.String format = java.lang.String.format("android.hardware.audio@%s::IDevicesFactory", audioHalVersionInfo.getMajorVersion() + "." + audioHalVersionInfo.getMinorVersion());
                if (android.os.ServiceManager.checkService("android.hardware.audio.core.IModule/default") != null) {
                    return audioHalVersionInfo;
                }
                android.os.HwBinder.getService(format, "default");
                return audioHalVersionInfo;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Remote exception when getting hardware audio service:", e);
            } catch (java.util.NoSuchElementException e2) {
            }
        }
        return null;
    }

    public boolean hasRegisteredDynamicPolicy() {
        boolean z;
        synchronized (this.mAudioPolicies) {
            z = !this.mAudioPolicies.isEmpty();
        }
        return z;
    }

    public int setPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i, android.media.AudioMixerAttributes audioMixerAttributes) {
        java.util.Objects.requireNonNull(audioAttributes);
        java.util.Objects.requireNonNull(audioMixerAttributes);
        if (!checkAudioSettingsPermission("setPreferredMixerAttributes()")) {
            return -4;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String formatSimple = android.text.TextUtils.formatSimple("setPreferredMixerAttributes u/pid:%d/%d attr:%s mixerAttributes:%s portId:%d", new java.lang.Object[]{java.lang.Integer.valueOf(callingUid), java.lang.Integer.valueOf(callingPid), audioAttributes.toString(), audioMixerAttributes.toString(), java.lang.Integer.valueOf(i)});
            sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(formatSimple).printLog(TAG));
            int preferredMixerAttributes = this.mAudioSystem.setPreferredMixerAttributes(audioAttributes, i, callingUid, audioMixerAttributes);
            if (preferredMixerAttributes != 0) {
                android.util.Log.e(TAG, android.text.TextUtils.formatSimple("Error %d in %s)", new java.lang.Object[]{java.lang.Integer.valueOf(preferredMixerAttributes), formatSimple}));
            } else {
                dispatchPreferredMixerAttributesChanged(audioAttributes, i, audioMixerAttributes);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return preferredMixerAttributes;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public int clearPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i) {
        java.util.Objects.requireNonNull(audioAttributes);
        if (!checkAudioSettingsPermission("clearPreferredMixerAttributes()")) {
            return -4;
        }
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String formatSimple = android.text.TextUtils.formatSimple("clearPreferredMixerAttributes u/pid:%d/%d attr:%s", new java.lang.Object[]{java.lang.Integer.valueOf(callingUid), java.lang.Integer.valueOf(callingPid), audioAttributes.toString()});
            sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(formatSimple).printLog(TAG));
            int clearPreferredMixerAttributes = this.mAudioSystem.clearPreferredMixerAttributes(audioAttributes, i, callingUid);
            if (clearPreferredMixerAttributes != 0) {
                android.util.Log.e(TAG, android.text.TextUtils.formatSimple("Error %d in %s)", new java.lang.Object[]{java.lang.Integer.valueOf(clearPreferredMixerAttributes), formatSimple}));
            } else {
                dispatchPreferredMixerAttributesChanged(audioAttributes, i, null);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return clearPreferredMixerAttributes;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    void dispatchPreferredMixerAttributesChanged(android.media.AudioAttributes audioAttributes, int i, android.media.AudioMixerAttributes audioMixerAttributes) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(KEY_AUDIO_ATTRIBUTES, audioAttributes);
        bundle.putParcelable(KEY_AUDIO_MIXER_ATTRIBUTES, audioMixerAttributes);
        sendBundleMsg(this.mAudioHandler, 52, 2, i, 0, null, bundle, 0);
    }

    public void registerPreferredMixerAttributesDispatcher(@android.annotation.Nullable android.media.IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) {
        if (iPreferredMixerAttributesDispatcher == null) {
            return;
        }
        this.mPrefMixerAttrDispatcher.register(iPreferredMixerAttributesDispatcher);
    }

    public void unregisterPreferredMixerAttributesDispatcher(@android.annotation.Nullable android.media.IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) {
        if (iPreferredMixerAttributesDispatcher == null) {
            return;
        }
        this.mPrefMixerAttrDispatcher.unregister(iPreferredMixerAttributesDispatcher);
    }

    protected void onDispatchPreferredMixerAttributesChanged(android.os.Bundle bundle, int i) {
        int beginBroadcast = this.mPrefMixerAttrDispatcher.beginBroadcast();
        android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) bundle.getParcelable(KEY_AUDIO_ATTRIBUTES, android.media.AudioAttributes.class);
        android.media.AudioMixerAttributes audioMixerAttributes = (android.media.AudioMixerAttributes) bundle.getParcelable(KEY_AUDIO_MIXER_ATTRIBUTES, android.media.AudioMixerAttributes.class);
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mPrefMixerAttrDispatcher.getBroadcastItem(i2).dispatchPrefMixerAttributesChanged(audioAttributes, i, audioMixerAttributes);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Can't call dispatchPrefMixerAttributesChanged() IPreferredMixerAttributesDispatcher " + this.mPrefMixerAttrDispatcher.getBroadcastItem(i2).asBinder(), e);
            }
        }
        this.mPrefMixerAttrDispatcher.finishBroadcast();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public boolean supportsBluetoothVariableLatency() {
        super.supportsBluetoothVariableLatency_enforcePermission();
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            boolean supportsBluetoothVariableLatency = android.media.AudioSystem.supportsBluetoothVariableLatency();
            if (create != null) {
                create.close();
            }
            return supportsBluetoothVariableLatency;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void setBluetoothVariableLatencyEnabled(boolean z) {
        super.setBluetoothVariableLatencyEnabled_enforcePermission();
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            android.media.AudioSystem.setBluetoothVariableLatencyEnabled(z);
            if (create != null) {
                create.close();
            }
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public boolean isBluetoothVariableLatencyEnabled() {
        super.isBluetoothVariableLatencyEnabled_enforcePermission();
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            boolean isBluetoothVariableLatencyEnabled = android.media.AudioSystem.isBluetoothVariableLatencyEnabled();
            if (create != null) {
                create.close();
            }
            return isBluetoothVariableLatencyEnabled;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setExtVolumeController(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        if (!this.mContext.getResources().getBoolean(android.R.bool.config_guestUserAllowEphemeralStateChange)) {
            android.util.Log.e(TAG, "Cannot set external volume controller: device not set for volume keys handled in PhoneWindowManager");
            return;
        }
        synchronized (this.mExtVolumeControllerLock) {
            try {
                if (this.mExtVolumeController != null && !this.mExtVolumeController.asBinder().pingBinder()) {
                    android.util.Log.e(TAG, "Cannot set external volume controller: existing controller");
                }
                this.mExtVolumeController = iAudioPolicyCallback;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dumpAudioPolicies(java.io.PrintWriter printWriter) {
        printWriter.println("\nAudio policies:");
        synchronized (this.mAudioPolicies) {
            try {
                java.util.Iterator<com.android.server.audio.AudioService.AudioPolicyProxy> it = this.mAudioPolicies.values().iterator();
                while (it.hasNext()) {
                    printWriter.println(it.next().toLogFriendlyString());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void ensureFadeManagerConfigIsEnabled() {
        com.android.internal.util.Preconditions.checkState(android.media.audiopolicy.Flags.enableFadeManagerConfiguration(), "Fade manager configuration not supported");
    }

    private void validateFadeManagerConfiguration(android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        java.util.List audioAttributesWithVolumeShaperConfigs = fadeManagerConfiguration.getAudioAttributesWithVolumeShaperConfigs();
        for (int i = 0; i < audioAttributesWithVolumeShaperConfigs.size(); i++) {
            validateAudioAttributesUsage((android.media.AudioAttributes) audioAttributesWithVolumeShaperConfigs.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDynPolicyMixStateUpdate(java.lang.String str, int i) {
        synchronized (this.mAudioPolicies) {
            for (com.android.server.audio.AudioService.AudioPolicyProxy audioPolicyProxy : this.mAudioPolicies.values()) {
                java.util.Iterator it = audioPolicyProxy.getMixes().iterator();
                while (it.hasNext()) {
                    if (((android.media.audiopolicy.AudioMix) it.next()).getRegistration().equals(str)) {
                        try {
                            audioPolicyProxy.mPolicyCallback.notifyMixStateUpdate(str, i);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(TAG, "Can't call notifyMixStateUpdate() on IAudioPolicyCallback " + audioPolicyProxy.mPolicyCallback.asBinder(), e);
                        }
                        return;
                    }
                }
            }
        }
    }

    public void registerRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        this.mRecordMonitor.registerRecordingCallback(iRecordingConfigDispatcher, this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public void unregisterRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        this.mRecordMonitor.unregisterRecordingCallback(iRecordingConfigDispatcher);
    }

    public java.util.List<android.media.AudioRecordingConfiguration> getActiveRecordingConfigurations() {
        return this.mRecordMonitor.getActiveRecordingConfigurations(android.os.Binder.getCallingUid() == 1000 || this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public int trackRecorder(android.os.IBinder iBinder) {
        return this.mRecordMonitor.trackRecorder(iBinder);
    }

    public void recorderEvent(int i, int i2) {
        this.mRecordMonitor.recorderEvent(i, i2);
    }

    public void releaseRecorder(int i) {
        this.mRecordMonitor.releaseRecorder(i);
    }

    public void registerPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
        this.mPlaybackMonitor.registerPlaybackCallback(iPlaybackConfigDispatcher, this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public void unregisterPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
        this.mPlaybackMonitor.unregisterPlaybackCallback(iPlaybackConfigDispatcher);
    }

    public java.util.List<android.media.AudioPlaybackConfiguration> getActivePlaybackConfigurations() {
        return this.mPlaybackMonitor.getActivePlaybackConfigurations(this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public int trackPlayer(android.media.PlayerBase.PlayerIdCard playerIdCard) {
        if (playerIdCard != null && playerIdCard.mAttributes != null) {
            validateAudioAttributesUsage(playerIdCard.mAttributes);
        }
        return this.mPlaybackMonitor.trackPlayer(playerIdCard);
    }

    public void playerAttributes(int i, android.media.AudioAttributes audioAttributes) {
        if (audioAttributes != null) {
            validateAudioAttributesUsage(audioAttributes);
        }
        this.mPlaybackMonitor.playerAttributes(i, audioAttributes, android.os.Binder.getCallingUid());
    }

    public void playerSessionId(int i, int i2) {
        if (i2 <= 0) {
            throw new java.lang.IllegalArgumentException("invalid session Id " + i2);
        }
        this.mPlaybackMonitor.playerSessionId(i, i2, android.os.Binder.getCallingUid());
    }

    public void playerEvent(int i, int i2, int i3) {
        this.mPlaybackMonitor.playerEvent(i, i2, i3, android.os.Binder.getCallingUid());
    }

    public void portEvent(int i, int i2, @android.annotation.Nullable android.os.PersistableBundle persistableBundle) {
        this.mPlaybackMonitor.portEvent(i, i2, persistableBundle, android.os.Binder.getCallingUid());
    }

    public void playerHasOpPlayAudio(int i, boolean z) {
        this.mPlaybackMonitor.playerHasOpPlayAudio(i, z, android.os.Binder.getCallingUid());
    }

    public void releasePlayer(int i) {
        this.mPlaybackMonitor.releasePlayer(i, android.os.Binder.getCallingUid());
    }

    public int setAllowedCapturePolicy(int i) {
        int allowedCapturePolicy;
        int callingUid = android.os.Binder.getCallingUid();
        int capturePolicyToFlags = android.media.AudioAttributes.capturePolicyToFlags(i, 0);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mPlaybackMonitor) {
                try {
                    allowedCapturePolicy = this.mAudioSystem.setAllowedCapturePolicy(callingUid, capturePolicyToFlags);
                    if (allowedCapturePolicy == 0) {
                        this.mPlaybackMonitor.setAllowedCapturePolicy(callingUid, i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return allowedCapturePolicy;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getAllowedCapturePolicy() {
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mPlaybackMonitor.getAllowedCapturePolicy(callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    boolean isPlaybackActiveForUid(int i) {
        return this.mPlaybackMonitor.isPlaybackActiveForUid(i);
    }

    boolean isRecordingActiveForUid(int i) {
        return this.mRecordMonitor.isRecordingActiveForUid(i);
    }

    private static final class AudioDeviceArray {

        @android.annotation.NonNull
        final java.lang.String[] mDeviceAddresses;

        @android.annotation.NonNull
        final int[] mDeviceTypes;

        AudioDeviceArray(@android.annotation.NonNull int[] iArr, @android.annotation.NonNull java.lang.String[] strArr) {
            this.mDeviceTypes = iArr;
            this.mDeviceAddresses = strArr;
        }
    }

    public class AudioPolicyProxy extends android.media.audiopolicy.AudioPolicyConfig implements android.os.IBinder.DeathRecipient {
        private static final java.lang.String TAG = "AudioPolicyProxy";
        int mFocusDuckBehavior;
        final boolean mHasFocusListener;
        boolean mIsFocusPolicy;
        boolean mIsTestFocusPolicy;
        final boolean mIsVolumeController;
        final android.media.audiopolicy.IAudioPolicyCallback mPolicyCallback;
        final android.media.projection.IMediaProjection mProjection;
        com.android.server.audio.AudioService.AudioPolicyProxy.UnregisterOnStopCallback mProjectionCallback;
        final java.util.HashMap<java.lang.Integer, com.android.server.audio.AudioService.AudioDeviceArray> mUidDeviceAffinities;
        final java.util.HashMap<java.lang.Integer, com.android.server.audio.AudioService.AudioDeviceArray> mUserIdDeviceAffinities;

        private final class UnregisterOnStopCallback extends android.media.projection.IMediaProjectionCallback.Stub {
            private UnregisterOnStopCallback() {
            }

            public void onStop() {
                com.android.server.audio.AudioService.this.unregisterAudioPolicyAsync(com.android.server.audio.AudioService.AudioPolicyProxy.this.mPolicyCallback);
            }

            public void onCapturedContentResize(int i, int i2) {
            }

            public void onCapturedContentVisibilityChanged(boolean z) {
            }
        }

        AudioPolicyProxy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, android.media.projection.IMediaProjection iMediaProjection) {
            super(audioPolicyConfig);
            this.mUidDeviceAffinities = new java.util.HashMap<>();
            this.mUserIdDeviceAffinities = new java.util.HashMap<>();
            this.mFocusDuckBehavior = 0;
            this.mIsFocusPolicy = false;
            this.mIsTestFocusPolicy = false;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(audioPolicyConfig.hashCode());
            sb.append(":ap:");
            int i = com.android.server.audio.AudioService.this.mAudioPolicyCounter;
            com.android.server.audio.AudioService.this.mAudioPolicyCounter = i + 1;
            sb.append(i);
            setRegistration(new java.lang.String(sb.toString()));
            this.mPolicyCallback = iAudioPolicyCallback;
            this.mHasFocusListener = z;
            this.mIsVolumeController = z4;
            this.mProjection = iMediaProjection;
            if (this.mHasFocusListener) {
                com.android.server.audio.AudioService.this.mMediaFocusControl.addFocusFollower(this.mPolicyCallback);
                if (z2) {
                    this.mIsFocusPolicy = true;
                    this.mIsTestFocusPolicy = z3;
                    com.android.server.audio.AudioService.this.mMediaFocusControl.setFocusPolicy(this.mPolicyCallback, this.mIsTestFocusPolicy);
                }
            }
            if (this.mIsVolumeController) {
                com.android.server.audio.AudioService.this.setExtVolumeController(this.mPolicyCallback);
            }
            if (this.mProjection != null) {
                this.mProjectionCallback = new com.android.server.audio.AudioService.AudioPolicyProxy.UnregisterOnStopCallback();
                try {
                    this.mProjection.registerCallback(this.mProjectionCallback);
                } catch (android.os.RemoteException e) {
                    release();
                    throw new java.lang.IllegalStateException("MediaProjection callback registration failed, could not link to " + iMediaProjection + " binder death", e);
                }
            }
            int connectMixes = connectMixes();
            if (connectMixes != 0) {
                release();
                throw new java.lang.IllegalStateException("Could not connect mix, error: " + connectMixes);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.audio.AudioService.this.mDynPolicyLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("AudioPolicy " + this.mPolicyCallback.asBinder() + " died").printLog(TAG));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator it = ((android.media.audiopolicy.AudioPolicyConfig) this).mMixes.iterator();
            while (it.hasNext()) {
                arrayList.add(((android.media.audiopolicy.AudioMix) it.next()).getRegistration());
            }
            com.android.server.audio.AudioService.this.onPolicyClientDeath(arrayList);
            release();
        }

        java.lang.String getRegistrationId() {
            return getRegistration();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Finally extract failed */
        public void release() {
            if (this.mIsFocusPolicy) {
                com.android.server.audio.AudioService.this.mMediaFocusControl.unsetFocusPolicy(this.mPolicyCallback, this.mIsTestFocusPolicy);
            }
            if (this.mFocusDuckBehavior == 1) {
                com.android.server.audio.AudioService.this.mMediaFocusControl.setDuckingInExtPolicyAvailable(false);
            }
            if (this.mHasFocusListener) {
                com.android.server.audio.AudioService.this.mMediaFocusControl.removeFocusFollower(this.mPolicyCallback);
            }
            if (this.mProjectionCallback != null) {
                try {
                    this.mProjection.unregisterCallback(this.mProjectionCallback);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Fail to unregister Audiopolicy callback from MediaProjection");
                }
            }
            if (this.mIsVolumeController) {
                synchronized (com.android.server.audio.AudioService.this.mExtVolumeControllerLock) {
                    com.android.server.audio.AudioService.this.mExtVolumeController = null;
                }
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (android.media.audiopolicy.Flags.audioMixOwnership()) {
                    synchronized (((android.media.audiopolicy.AudioPolicyConfig) this).mMixes) {
                        removeMixes(new java.util.ArrayList<>(((android.media.audiopolicy.AudioPolicyConfig) this).mMixes));
                    }
                } else {
                    com.android.server.audio.AudioService.this.mAudioSystem.registerPolicyMixes(((android.media.audiopolicy.AudioPolicyConfig) this).mMixes, false);
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                synchronized (com.android.server.audio.AudioService.this.mAudioPolicies) {
                    com.android.server.audio.AudioService.this.mAudioPolicies.remove(this.mPolicyCallback.asBinder());
                }
                try {
                    this.mPolicyCallback.notifyUnregistration();
                } catch (android.os.RemoteException e2) {
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        boolean hasMixAffectingUsage(int i, int i2) {
            java.util.Iterator it = ((android.media.audiopolicy.AudioPolicyConfig) this).mMixes.iterator();
            while (it.hasNext()) {
                android.media.audiopolicy.AudioMix audioMix = (android.media.audiopolicy.AudioMix) it.next();
                if (audioMix.isAffectingUsage(i) && (audioMix.getRouteFlags() & i2) != i2) {
                    return true;
                }
            }
            return false;
        }

        boolean hasMixRoutedToDevices(@android.annotation.NonNull int[] iArr, @android.annotation.NonNull java.lang.String[] strArr) {
            int i = 0;
            while (true) {
                boolean z = true;
                if (i >= iArr.length) {
                    return true;
                }
                java.util.Iterator it = ((android.media.audiopolicy.AudioPolicyConfig) this).mMixes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    if (((android.media.audiopolicy.AudioMix) it.next()).isRoutedToDevice(iArr[i], strArr[i])) {
                        break;
                    }
                }
                if (!z) {
                    return false;
                }
                i++;
            }
        }

        int addMixes(@android.annotation.NonNull java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList) {
            synchronized (((android.media.audiopolicy.AudioPolicyConfig) this).mMixes) {
                try {
                    if (android.media.audiopolicy.Flags.audioMixOwnership()) {
                        java.util.Iterator<android.media.audiopolicy.AudioMix> it = arrayList.iterator();
                        while (it.hasNext()) {
                            setMixRegistration(it.next());
                        }
                        int registerPolicyMixes = com.android.server.audio.AudioService.this.mAudioSystem.registerPolicyMixes(arrayList, true);
                        if (registerPolicyMixes == 0) {
                            add(arrayList);
                        }
                        return registerPolicyMixes;
                    }
                    add(arrayList);
                    return com.android.server.audio.AudioService.this.mAudioSystem.registerPolicyMixes(arrayList, true);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        int removeMixes(@android.annotation.NonNull java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList) {
            int registerPolicyMixes;
            synchronized (((android.media.audiopolicy.AudioPolicyConfig) this).mMixes) {
                remove(arrayList);
                registerPolicyMixes = com.android.server.audio.AudioService.this.mAudioSystem.registerPolicyMixes(arrayList, false);
            }
            return registerPolicyMixes;
        }

        int connectMixes() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.audio.AudioService.this.mAudioSystem.registerPolicyMixes(((android.media.audiopolicy.AudioPolicyConfig) this).mMixes, true);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        int updateMixingRules(@android.annotation.NonNull android.media.audiopolicy.AudioMix[] audioMixArr, @android.annotation.NonNull android.media.audiopolicy.AudioMixingRule[] audioMixingRuleArr) {
            int updateMixingRules;
            java.util.Objects.requireNonNull(audioMixArr);
            java.util.Objects.requireNonNull(audioMixingRuleArr);
            if (audioMixArr.length != audioMixingRuleArr.length) {
                android.util.Log.e(TAG, "Provided list of audio mixes to update and corresponding mixing rules have mismatching length (mixesToUpdate.length = " + audioMixArr.length + ", updatedMixingRules.length = " + audioMixingRuleArr.length + ").");
                return -2;
            }
            synchronized (((android.media.audiopolicy.AudioPolicyConfig) this).mMixes) {
                try {
                    android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
                    try {
                        updateMixingRules = com.android.server.audio.AudioService.this.mAudioSystem.updateMixingRules(audioMixArr, audioMixingRuleArr);
                        if (updateMixingRules == 0) {
                            for (int i = 0; i < audioMixArr.length; i++) {
                                final android.media.audiopolicy.AudioMix audioMix = audioMixArr[i];
                                final android.media.audiopolicy.AudioMixingRule audioMixingRule = audioMixingRuleArr[i];
                                java.util.stream.Stream stream = ((android.media.audiopolicy.AudioPolicyConfig) this).mMixes.stream();
                                java.util.Objects.requireNonNull(audioMix);
                                stream.filter(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioService$AudioPolicyProxy$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(java.lang.Object obj) {
                                        return audioMix.equals((android.media.audiopolicy.AudioMix) obj);
                                    }
                                }).findAny().ifPresent(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioService$AudioPolicyProxy$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Consumer
                                    public final void accept(java.lang.Object obj) {
                                        ((android.media.audiopolicy.AudioMix) obj).setAudioMixingRule(audioMixingRule);
                                    }
                                });
                            }
                        }
                        if (create != null) {
                            create.close();
                        }
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return updateMixingRules;
        }

        int setUidDeviceAffinities(int i, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull java.lang.String[] strArr) {
            java.lang.Integer num = new java.lang.Integer(i);
            if (this.mUidDeviceAffinities.remove(num) != null && removeUidDeviceAffinitiesFromSystem(i) != 0) {
                android.util.Log.e(TAG, "AudioSystem. removeUidDeviceAffinities(" + i + ") failed,  cannot call AudioSystem.setUidDeviceAffinities");
                return -1;
            }
            com.android.server.audio.AudioService.AudioDeviceArray audioDeviceArray = new com.android.server.audio.AudioService.AudioDeviceArray(iArr, strArr);
            if (setUidDeviceAffinitiesOnSystem(i, audioDeviceArray) == 0) {
                this.mUidDeviceAffinities.put(num, audioDeviceArray);
                return 0;
            }
            android.util.Log.e(TAG, "AudioSystem. setUidDeviceAffinities(" + i + ") failed");
            return -1;
        }

        int removeUidDeviceAffinities(int i) {
            if (this.mUidDeviceAffinities.remove(new java.lang.Integer(i)) != null && removeUidDeviceAffinitiesFromSystem(i) == 0) {
                return 0;
            }
            android.util.Log.e(TAG, "AudioSystem. removeUidDeviceAffinities failed");
            return -1;
        }

        private int removeUidDeviceAffinitiesFromSystem(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.audio.AudioService.this.mAudioSystem.removeUidDeviceAffinities(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private int setUidDeviceAffinitiesOnSystem(int i, com.android.server.audio.AudioService.AudioDeviceArray audioDeviceArray) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.audio.AudioService.this.mAudioSystem.setUidDeviceAffinities(i, audioDeviceArray.mDeviceTypes, audioDeviceArray.mDeviceAddresses);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        int setUserIdDeviceAffinities(int i, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull java.lang.String[] strArr) {
            java.lang.Integer num = new java.lang.Integer(i);
            if (this.mUserIdDeviceAffinities.remove(num) != null && removeUserIdDeviceAffinitiesFromSystem(i) != 0) {
                android.util.Log.e(TAG, "AudioSystem. removeUserIdDeviceAffinities(" + num + ") failed,  cannot call AudioSystem.setUserIdDeviceAffinities");
                return -1;
            }
            com.android.server.audio.AudioService.AudioDeviceArray audioDeviceArray = new com.android.server.audio.AudioService.AudioDeviceArray(iArr, strArr);
            if (setUserIdDeviceAffinitiesOnSystem(i, audioDeviceArray) == 0) {
                this.mUserIdDeviceAffinities.put(num, audioDeviceArray);
                return 0;
            }
            android.util.Log.e(TAG, "AudioSystem.setUserIdDeviceAffinities(" + i + ") failed");
            return -1;
        }

        int removeUserIdDeviceAffinities(int i) {
            if (this.mUserIdDeviceAffinities.remove(new java.lang.Integer(i)) != null && removeUserIdDeviceAffinitiesFromSystem(i) == 0) {
                return 0;
            }
            android.util.Log.e(TAG, "AudioSystem.removeUserIdDeviceAffinities failed");
            return -1;
        }

        private int removeUserIdDeviceAffinitiesFromSystem(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.audio.AudioService.this.mAudioSystem.removeUserIdDeviceAffinities(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private int setUserIdDeviceAffinitiesOnSystem(int i, com.android.server.audio.AudioService.AudioDeviceArray audioDeviceArray) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.audio.AudioService.this.mAudioSystem.setUserIdDeviceAffinities(i, audioDeviceArray.mDeviceTypes, audioDeviceArray.mDeviceAddresses);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        int setupDeviceAffinities() {
            for (java.util.Map.Entry<java.lang.Integer, com.android.server.audio.AudioService.AudioDeviceArray> entry : this.mUidDeviceAffinities.entrySet()) {
                int removeUidDeviceAffinitiesFromSystem = removeUidDeviceAffinitiesFromSystem(entry.getKey().intValue());
                if (removeUidDeviceAffinitiesFromSystem != 0) {
                    android.util.Log.e(TAG, "setupDeviceAffinities failed to remove device affinity for uid " + entry.getKey());
                    return removeUidDeviceAffinitiesFromSystem;
                }
                int uidDeviceAffinitiesOnSystem = setUidDeviceAffinitiesOnSystem(entry.getKey().intValue(), entry.getValue());
                if (uidDeviceAffinitiesOnSystem != 0) {
                    android.util.Log.e(TAG, "setupDeviceAffinities failed to set device affinity for uid " + entry.getKey());
                    return uidDeviceAffinitiesOnSystem;
                }
            }
            for (java.util.Map.Entry<java.lang.Integer, com.android.server.audio.AudioService.AudioDeviceArray> entry2 : this.mUserIdDeviceAffinities.entrySet()) {
                int removeUserIdDeviceAffinitiesFromSystem = removeUserIdDeviceAffinitiesFromSystem(entry2.getKey().intValue());
                if (removeUserIdDeviceAffinitiesFromSystem != 0) {
                    android.util.Log.e(TAG, "setupDeviceAffinities failed to remove device affinity for userId " + entry2.getKey());
                    return removeUserIdDeviceAffinitiesFromSystem;
                }
                int userIdDeviceAffinitiesOnSystem = setUserIdDeviceAffinitiesOnSystem(entry2.getKey().intValue(), entry2.getValue());
                if (userIdDeviceAffinitiesOnSystem != 0) {
                    android.util.Log.e(TAG, "setupDeviceAffinities failed to set device affinity for userId " + entry2.getKey());
                    return userIdDeviceAffinitiesOnSystem;
                }
            }
            return 0;
        }

        public java.lang.String toLogFriendlyString() {
            java.lang.String str = (((((super.toLogFriendlyString() + " Uid Device Affinities:\n") + logFriendlyAttributeDeviceArrayMap("Uid", this.mUidDeviceAffinities, "     ")) + " UserId Device Affinities:\n") + logFriendlyAttributeDeviceArrayMap("UserId", this.mUserIdDeviceAffinities, "     ")) + " Proxy:\n") + "   is focus policy= " + this.mIsFocusPolicy + "\n";
            if (this.mIsFocusPolicy) {
                str = ((str + "     focus duck behaviour= " + this.mFocusDuckBehavior + "\n") + "     is test focus policy= " + this.mIsTestFocusPolicy + "\n") + "     has focus listener= " + this.mHasFocusListener + "\n";
            }
            return str + "   media projection= " + this.mProjection + "\n";
        }

        private java.lang.String logFriendlyAttributeDeviceArrayMap(java.lang.String str, java.util.Map<java.lang.Integer, com.android.server.audio.AudioService.AudioDeviceArray> map, java.lang.String str2) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (java.util.Map.Entry<java.lang.Integer, com.android.server.audio.AudioService.AudioDeviceArray> entry : map.entrySet()) {
                sb.append(str2);
                sb.append(str);
                sb.append(": ");
                sb.append(entry.getKey());
                sb.append("\n");
                com.android.server.audio.AudioService.AudioDeviceArray value = entry.getValue();
                java.lang.String str3 = str2 + "   ";
                for (int i = 0; i < value.mDeviceTypes.length; i++) {
                    sb.append(str3);
                    sb.append("Type: 0x");
                    sb.append(java.lang.Integer.toHexString(value.mDeviceTypes[i]));
                    sb.append(" Address: ");
                    sb.append(value.mDeviceAddresses[i]);
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
    }

    public int dispatchFocusChange(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        int dispatchFocusChange;
        if (audioFocusInfo == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (iAudioPolicyCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioPolicy callback");
        }
        synchronized (this.mAudioPolicies) {
            try {
                if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                    throw new java.lang.IllegalStateException("Unregistered AudioPolicy for focus dispatch");
                }
                dispatchFocusChange = this.mMediaFocusControl.dispatchFocusChange(audioFocusInfo, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return dispatchFocusChange;
    }

    public void setFocusRequestResultFromExtPolicy(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) {
        if (audioFocusInfo == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (iAudioPolicyCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioPolicy callback");
        }
        synchronized (this.mAudioPolicies) {
            try {
                if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                    throw new java.lang.IllegalStateException("Unregistered AudioPolicy for external focus");
                }
                this.mMediaFocusControl.setFocusRequestResultFromExtPolicy(audioFocusInfo, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED")
    public int dispatchFocusChangeWithFade(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, java.util.List<android.media.AudioFocusInfo> list, android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        int dispatchFocusChangeWithFade;
        super.dispatchFocusChangeWithFade_enforcePermission();
        ensureFadeManagerConfigIsEnabled();
        java.util.Objects.requireNonNull(audioFocusInfo, "AudioFocusInfo cannot be null");
        java.util.Objects.requireNonNull(iAudioPolicyCallback, "AudioPolicy callback cannot be null");
        java.util.Objects.requireNonNull(list, "Other active AudioFocusInfo list cannot be null");
        if (fadeManagerConfiguration != null) {
            validateFadeManagerConfiguration(fadeManagerConfiguration);
        }
        synchronized (this.mAudioPolicies) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder()), "Unregistered AudioPolicy for focus dispatch with fade");
                if (fadeManagerConfiguration != null) {
                    this.mPlaybackMonitor.setTransientFadeManagerConfiguration(i, fadeManagerConfiguration);
                }
                dispatchFocusChangeWithFade = this.mMediaFocusControl.dispatchFocusChangeWithFade(audioFocusInfo, i, list);
                if (fadeManagerConfiguration != null) {
                    this.mPlaybackMonitor.clearTransientFadeManagerConfiguration(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return dispatchFocusChangeWithFade;
    }

    @android.annotation.EnforcePermission("android.permission.QUERY_AUDIO_STATE")
    public boolean shouldNotificationSoundPlay(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        super.shouldNotificationSoundPlay_enforcePermission();
        java.util.Objects.requireNonNull(audioAttributes);
        if (getStreamVolume(android.media.AudioAttributes.toLegacyStreamType(audioAttributes)) == 0) {
            return false;
        }
        int exclusiveFocusOwnerUid = this.mMediaFocusControl.getExclusiveFocusOwnerUid();
        return exclusiveFocusOwnerUid == -1 || !this.mRecordMonitor.isRecordingActiveForUid(exclusiveFocusOwnerUid);
    }

    private class AsdProxy implements android.os.IBinder.DeathRecipient {
        private final android.media.IAudioServerStateDispatcher mAsd;

        AsdProxy(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) {
            this.mAsd = iAudioServerStateDispatcher;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.audio.AudioService.this.mAudioServerStateListeners) {
                com.android.server.audio.AudioService.this.mAudioServerStateListeners.remove(this.mAsd.asBinder());
            }
        }

        android.media.IAudioServerStateDispatcher callback() {
            return this.mAsd;
        }
    }

    private void checkMonitorAudioServerStatePermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            throw new java.lang.SecurityException("Not allowed to monitor audioserver state");
        }
    }

    public void registerAudioServerStateDispatcher(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) {
        checkMonitorAudioServerStatePermission();
        synchronized (this.mAudioServerStateListeners) {
            try {
                if (this.mAudioServerStateListeners.containsKey(iAudioServerStateDispatcher.asBinder())) {
                    android.util.Slog.w(TAG, "Cannot re-register audio server state dispatcher");
                    return;
                }
                com.android.server.audio.AudioService.AsdProxy asdProxy = new com.android.server.audio.AudioService.AsdProxy(iAudioServerStateDispatcher);
                try {
                    iAudioServerStateDispatcher.asBinder().linkToDeath(asdProxy, 0);
                } catch (android.os.RemoteException e) {
                }
                this.mAudioServerStateListeners.put(iAudioServerStateDispatcher.asBinder(), asdProxy);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterAudioServerStateDispatcher(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) {
        checkMonitorAudioServerStatePermission();
        synchronized (this.mAudioServerStateListeners) {
            try {
                com.android.server.audio.AudioService.AsdProxy remove = this.mAudioServerStateListeners.remove(iAudioServerStateDispatcher.asBinder());
                if (remove == null) {
                    android.util.Slog.w(TAG, "Trying to unregister unknown audioserver state dispatcher for pid " + android.os.Binder.getCallingPid() + " / uid " + android.os.Binder.getCallingUid());
                    return;
                }
                iAudioServerStateDispatcher.asBinder().unlinkToDeath(remove, 0);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAudioServerRunning() {
        checkMonitorAudioServerStatePermission();
        return android.media.AudioSystem.checkAudioFlinger() == 0;
    }

    private void getAudioAidlHalPids(java.util.HashSet<java.lang.Integer> hashSet) {
        try {
            android.os.ServiceDebugInfo[] serviceDebugInfo = android.os.ServiceManager.getServiceDebugInfo();
            if (serviceDebugInfo == null) {
                return;
            }
            for (android.os.ServiceDebugInfo serviceDebugInfo2 : serviceDebugInfo) {
                if (serviceDebugInfo2.debugPid > 0 && serviceDebugInfo2.name.startsWith(AUDIO_HAL_SERVICE_PREFIX)) {
                    hashSet.add(java.lang.Integer.valueOf(serviceDebugInfo2.debugPid));
                }
            }
        } catch (java.lang.RuntimeException e) {
        }
    }

    private void getAudioHalHidlPids(java.util.HashSet<java.lang.Integer> hashSet) {
        try {
            java.util.Iterator<android.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo> it = android.hidl.manager.V1_0.IServiceManager.getService().debugDump().iterator();
            while (it.hasNext()) {
                android.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo next = it.next();
                if (next.pid != -1 && next.interfaceName != null && next.interfaceName.startsWith(AUDIO_HAL_SERVICE_PREFIX)) {
                    hashSet.add(java.lang.Integer.valueOf(next.pid));
                }
            }
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
        }
    }

    private java.util.Set<java.lang.Integer> getAudioHalPids() {
        java.util.HashSet<java.lang.Integer> hashSet = new java.util.HashSet<>();
        getAudioAidlHalPids(hashSet);
        getAudioHalHidlPids(hashSet);
        return hashSet;
    }

    private void updateAudioHalPids() {
        java.util.Set<java.lang.Integer> audioHalPids = getAudioHalPids();
        if (audioHalPids.isEmpty()) {
            android.util.Slog.w(TAG, "Could not retrieve audio HAL service pids");
        } else {
            android.media.AudioSystem.setAudioHalPids(audioHalPids.stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray());
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void setMultiAudioFocusEnabled(boolean z) {
        super.setMultiAudioFocusEnabled_enforcePermission();
        if (this.mMediaFocusControl != null && this.mMediaFocusControl.getMultiAudioFocusEnabled() != z) {
            this.mMediaFocusControl.updateMultiAudioFocus(z);
            if (!z) {
                this.mDeviceBroker.postBroadcastBecomingNoisy();
            }
        }
    }

    public boolean setAdditionalOutputDeviceDelay(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, long j) {
        java.util.Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        enforceModifyAudioRoutingPermission();
        android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        java.lang.String str = "additional_output_device_delay=" + retrieveBluetoothAddress.getInternalType() + "," + retrieveBluetoothAddress.getAddress();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append(",");
        sb.append(j);
        return this.mRestorableParameters.setParameters(str, sb.toString()) == 0;
    }

    public long getAdditionalOutputDeviceDelay(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        try {
            return java.lang.Long.parseLong(android.media.AudioSystem.getParameters("additional_output_device_delay=" + retrieveBluetoothAddress.getInternalType() + "," + retrieveBluetoothAddress.getAddress()).substring("additional_output_device_delay".length() + 1));
        } catch (java.lang.NullPointerException e) {
            return 0L;
        }
    }

    public long getMaxAdditionalOutputDeviceDelay(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        android.media.AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        try {
            return java.lang.Long.parseLong(android.media.AudioSystem.getParameters("max_additional_output_device_delay=" + retrieveBluetoothAddress.getInternalType() + "," + retrieveBluetoothAddress.getAddress()).substring("max_additional_output_device_delay".length() + 1));
        } catch (java.lang.NullPointerException e) {
            return 0L;
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void addAssistantServicesUids(int[] iArr) {
        super.addAssistantServicesUids_enforcePermission();
        java.util.Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            addAssistantServiceUidsLocked(iArr);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void removeAssistantServicesUids(int[] iArr) {
        super.removeAssistantServicesUids_enforcePermission();
        java.util.Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            removeAssistantServiceUidsLocked(iArr);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public int[] getAssistantServicesUids() {
        int[] array;
        super.getAssistantServicesUids_enforcePermission();
        synchronized (this.mSettingsLock) {
            array = this.mAssistantUids.stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray();
        }
        return array;
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void setActiveAssistantServiceUids(int[] iArr) {
        super.setActiveAssistantServiceUids_enforcePermission();
        java.util.Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            this.mActiveAssistantServiceUids = iArr;
        }
        updateActiveAssistantServiceUids();
    }

    @android.annotation.EnforcePermission("android.permission.MODIFY_AUDIO_ROUTING")
    public int[] getActiveAssistantServiceUids() {
        int[] iArr;
        super.getActiveAssistantServiceUids_enforcePermission();
        synchronized (this.mSettingsLock) {
            iArr = (int[]) this.mActiveAssistantServiceUids.clone();
        }
        return iArr;
    }

    java.util.List<java.lang.String> getDeviceIdentityAddresses(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        return this.mDeviceBroker.getDeviceIdentityAddresses(audioDeviceAttributes);
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    com.android.server.audio.MusicFxHelper getMusicFxHelper() {
        return this.mMusicFxHelper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFixedVolumeDevice(int i) {
        if (i == 32768 && this.mRecordMonitor.isLegacyRemoteSubmixActive()) {
            return false;
        }
        return this.mFixedVolumeDevices.contains(java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFullVolumeDevice(int i) {
        if (i == 32768 && this.mRecordMonitor.isLegacyRemoteSubmixActive()) {
            return false;
        }
        return this.mFullVolumeDevices.contains(java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAbsoluteVolumeDevice(int i) {
        return this.mAbsoluteVolumeDeviceInfoMap.containsKey(java.lang.Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isA2dpAbsoluteVolumeDevice(int i) {
        return this.mAvrcpAbsVolSupported && android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(java.lang.Integer.valueOf(i));
    }

    private static java.lang.String getSettingsNameForDeviceVolumeBehavior(int i) {
        return "AudioService_DeviceVolumeBehavior_" + android.media.AudioSystem.getOutputDeviceName(i);
    }

    private void persistDeviceVolumeBehavior(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mSettings.putSystemIntForUser(this.mContentResolver, getSettingsNameForDeviceVolumeBehavior(i), i2, -2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private int retrieveStoredDeviceVolumeBehavior(int i) {
        return this.mSettings.getSystemIntForUser(this.mContentResolver, getSettingsNameForDeviceVolumeBehavior(i), -1, -2);
    }

    private void restoreDeviceVolumeBehavior() {
        java.util.Iterator it = android.media.AudioSystem.DEVICE_OUT_ALL_SET.iterator();
        while (it.hasNext()) {
            int intValue = ((java.lang.Integer) it.next()).intValue();
            int retrieveStoredDeviceVolumeBehavior = retrieveStoredDeviceVolumeBehavior(intValue);
            if (retrieveStoredDeviceVolumeBehavior != -1) {
                setDeviceVolumeBehaviorInternal(new android.media.AudioDeviceAttributes(intValue, ""), retrieveStoredDeviceVolumeBehavior, "AudioService.restoreDeviceVolumeBehavior()");
            }
        }
    }

    private boolean hasDeviceVolumeBehavior(int i) {
        return retrieveStoredDeviceVolumeBehavior(i) != -1;
    }

    private boolean addAudioSystemDeviceOutToFixedVolumeDevices(int i) {
        return this.mFixedVolumeDevices.add(java.lang.Integer.valueOf(i));
    }

    private boolean removeAudioSystemDeviceOutFromFixedVolumeDevices(int i) {
        return this.mFixedVolumeDevices.remove(java.lang.Integer.valueOf(i));
    }

    private boolean addAudioSystemDeviceOutToFullVolumeDevices(int i) {
        return this.mFullVolumeDevices.add(java.lang.Integer.valueOf(i));
    }

    private boolean removeAudioSystemDeviceOutFromFullVolumeDevices(int i) {
        return this.mFullVolumeDevices.remove(java.lang.Integer.valueOf(i));
    }

    private void addAudioSystemDeviceOutToAbsVolumeDevices(int i, com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo) {
        this.mAbsoluteVolumeDeviceInfoMap.put(java.lang.Integer.valueOf(i), absoluteVolumeDeviceInfo);
    }

    private com.android.server.audio.AudioService.AbsoluteVolumeDeviceInfo removeAudioSystemDeviceOutFromAbsVolumeDevices(int i) {
        return this.mAbsoluteVolumeDeviceInfoMap.remove(java.lang.Integer.valueOf(i));
    }

    private boolean checkNoteAppOp(int i, int i2, java.lang.String str, java.lang.String str2) {
        try {
            if (this.mAppOps.noteOp(i, i2, str, str2, (java.lang.String) null) != 0) {
                return false;
            }
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error noting op:" + i + " on uid:" + i2 + " for package:" + str, e);
            return false;
        }
    }
}
