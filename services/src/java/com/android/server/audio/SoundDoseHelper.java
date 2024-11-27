package com.android.server.audio;

/* loaded from: classes.dex */
public class SoundDoseHelper {
    static final java.lang.String ACTION_CHECK_MUSIC_ACTIVE = "com.android.server.audio.action.CHECK_MUSIC_ACTIVE";
    private static final int CSD_WARNING_TIMEOUT_MS_ACCUMULATION_START = -1;
    private static final int CSD_WARNING_TIMEOUT_MS_DOSE_1X = 7000;
    private static final int CSD_WARNING_TIMEOUT_MS_DOSE_5X = 5000;
    private static final int CSD_WARNING_TIMEOUT_MS_MOMENTARY_EXPOSURE = 5000;
    private static final long GLOBAL_TIME_OFFSET_UNINITIALIZED = -1;
    private static final int MAX_NUMBER_OF_CACHED_RECORDS = 655;
    private static final int MAX_RECORDS_STRING_LENGTH = 50;
    private static final int MAX_SETTINGS_LENGTH = 32768;
    private static final int MOMENTARY_EXPOSURE_TIMEOUT_MS = 72000000;
    private static final int MOMENTARY_EXPOSURE_TIMEOUT_UNINITIALIZED = -1;
    static final int MSG_CONFIGURE_SAFE_MEDIA = 1001;
    static final int MSG_CONFIGURE_SAFE_MEDIA_FORCED = 1002;
    static final int MSG_CSD_UPDATE_ATTENUATION = 1006;
    static final int MSG_LOWER_VOLUME_TO_RS1 = 1007;
    static final int MSG_PERSIST_CSD_VALUES = 1005;
    static final int MSG_PERSIST_MUSIC_ACTIVE_MS = 1004;
    static final int MSG_PERSIST_SAFE_VOLUME_STATE = 1003;
    private static final int MUSIC_ACTIVE_POLL_PERIOD_MS = 60000;
    private static final java.lang.String PERSIST_CSD_RECORD_FIELD_SEPARATOR = ",";
    private static final java.lang.String PERSIST_CSD_RECORD_SEPARATOR = "\\|";
    private static final java.lang.String PERSIST_CSD_RECORD_SEPARATOR_CHAR = "|";
    private static final int REQUEST_CODE_CHECK_MUSIC_ACTIVE = 1;
    private static final int SAFE_MEDIA_VOLUME_ACTIVE = 3;
    private static final int SAFE_MEDIA_VOLUME_DISABLED = 1;
    private static final int SAFE_MEDIA_VOLUME_INACTIVE = 2;
    private static final int SAFE_MEDIA_VOLUME_NOT_CONFIGURED = 0;
    private static final int SAFE_MEDIA_VOLUME_UNINITIALIZED = -1;
    private static final int SAFE_VOLUME_CONFIGURE_TIMEOUT_MS = 30000;
    private static final java.lang.String SYSTEM_PROPERTY_SAFEMEDIA_BYPASS = "audio.safemedia.bypass";
    private static final java.lang.String SYSTEM_PROPERTY_SAFEMEDIA_CSD_FORCE = "audio.safemedia.csd.force";
    private static final java.lang.String SYSTEM_PROPERTY_SAFEMEDIA_FORCE = "audio.safemedia.force";
    private static final java.lang.String TAG = "AS.SoundDoseHelper";
    private static final int UNSAFE_VOLUME_MUSIC_ACTIVE_MS_MAX = 72000000;
    private final android.app.AlarmManager mAlarmManager;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioService.AudioHandler mAudioHandler;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioService mAudioService;
    private final android.content.Context mContext;
    private int mMusicActiveMs;
    private com.android.server.audio.SoundDoseHelper.StreamVolumeCommand mPendingVolumeCommand;
    private float mSafeMediaVolumeDbfs;
    private int mSafeMediaVolumeIndex;
    private int mSafeMediaVolumeState;

    @android.annotation.NonNull
    private final com.android.server.audio.SettingsAdapter mSettings;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioService.ISafeHearingVolumeController mVolumeController;
    private final com.android.server.utils.EventLogger mLogger = new com.android.server.utils.EventLogger(30, "CSD updates");
    private int mMcc = 0;
    private final java.lang.Object mSafeMediaVolumeStateLock = new java.lang.Object();
    private final android.util.SparseIntArray mSafeMediaVolumeDevices = new android.util.SparseIntArray();
    private long mLastMusicActiveTimeMs = 0;
    private android.app.PendingIntent mMusicActiveIntent = null;
    private final java.util.concurrent.atomic.AtomicBoolean mEnableCsd = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.util.concurrent.atomic.AtomicBoolean mForceCsdProperty = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.lang.Object mCsdAsAFeatureLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mCsdAsAFeatureLock"})
    private boolean mIsCsdAsAFeatureAvailable = false;

    @com.android.internal.annotations.GuardedBy({"mCsdAsAFeatureLock"})
    private boolean mIsCsdAsAFeatureEnabled = false;
    private final java.util.ArrayList<android.media.ISoundDose.AudioDeviceCategory> mCachedAudioDeviceCategories = new java.util.ArrayList<>();
    private final java.lang.Object mCsdStateLock = new java.lang.Object();
    private final java.util.concurrent.atomic.AtomicReference<android.media.ISoundDose> mSoundDose = new java.util.concurrent.atomic.AtomicReference<>();

    @com.android.internal.annotations.GuardedBy({"mCsdStateLock"})
    private float mCurrentCsd = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;

    @com.android.internal.annotations.GuardedBy({"mCsdStateLock"})
    private long mLastMomentaryExposureTimeMs = -1;

    @com.android.internal.annotations.GuardedBy({"mCsdStateLock"})
    private float mNextCsdWarning = 1.0f;

    @com.android.internal.annotations.GuardedBy({"mCsdStateLock"})
    private final java.util.List<android.media.SoundDoseRecord> mDoseRecords = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mCsdStateLock"})
    private long mGlobalTimeOffsetInSecs = -1;
    private final android.media.ISoundDoseCallback.Stub mSoundDoseCallback = new android.media.ISoundDoseCallback.Stub() { // from class: com.android.server.audio.SoundDoseHelper.1
        public void onMomentaryExposure(float f, int i) {
            boolean z;
            if (!com.android.server.audio.SoundDoseHelper.this.mEnableCsd.get()) {
                android.util.Log.w(com.android.server.audio.SoundDoseHelper.TAG, "onMomentaryExposure: csd not supported, ignoring callback");
                return;
            }
            android.util.Log.w(com.android.server.audio.SoundDoseHelper.TAG, "DeviceId " + i + " triggered momentary exposure with value: " + f);
            com.android.server.audio.SoundDoseHelper.this.mLogger.enqueue(com.android.server.audio.AudioServiceEvents.SoundDoseEvent.getMomentaryExposureEvent(f));
            synchronized (com.android.server.audio.SoundDoseHelper.this.mCsdStateLock) {
                try {
                    z = com.android.server.audio.SoundDoseHelper.this.mLastMomentaryExposureTimeMs < 0 || java.lang.System.currentTimeMillis() - com.android.server.audio.SoundDoseHelper.this.mLastMomentaryExposureTimeMs >= 72000000;
                    com.android.server.audio.SoundDoseHelper.this.mLastMomentaryExposureTimeMs = java.lang.System.currentTimeMillis();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z) {
                com.android.server.audio.SoundDoseHelper.this.mVolumeController.postDisplayCsdWarning(3, com.android.server.audio.SoundDoseHelper.this.getTimeoutMsForWarning(3));
            }
        }

        public void onNewCsdValue(float f, android.media.SoundDoseRecord[] soundDoseRecordArr) {
            if (!com.android.server.audio.SoundDoseHelper.this.mEnableCsd.get()) {
                android.util.Log.w(com.android.server.audio.SoundDoseHelper.TAG, "onNewCsdValue: csd not supported, ignoring value");
                return;
            }
            android.util.Log.i(com.android.server.audio.SoundDoseHelper.TAG, "onNewCsdValue: " + f);
            synchronized (com.android.server.audio.SoundDoseHelper.this.mCsdStateLock) {
                try {
                    if (com.android.server.audio.SoundDoseHelper.this.mCurrentCsd < f) {
                        if (com.android.server.audio.SoundDoseHelper.this.mCurrentCsd < com.android.server.audio.SoundDoseHelper.this.mNextCsdWarning && f >= com.android.server.audio.SoundDoseHelper.this.mNextCsdWarning) {
                            if (com.android.server.audio.SoundDoseHelper.this.mNextCsdWarning == 5.0f) {
                                com.android.server.audio.SoundDoseHelper.this.mVolumeController.postDisplayCsdWarning(2, com.android.server.audio.SoundDoseHelper.this.getTimeoutMsForWarning(2));
                                com.android.server.audio.SoundDoseHelper.this.mAudioService.postLowerVolumeToRs1();
                            } else {
                                com.android.server.audio.SoundDoseHelper.this.mVolumeController.postDisplayCsdWarning(1, com.android.server.audio.SoundDoseHelper.this.getTimeoutMsForWarning(1));
                            }
                            com.android.server.audio.SoundDoseHelper.this.mNextCsdWarning += 1.0f;
                        }
                    } else if (f < com.android.server.audio.SoundDoseHelper.this.mNextCsdWarning - 1.0f && com.android.server.audio.SoundDoseHelper.this.mNextCsdWarning >= 2.0f) {
                        com.android.server.audio.SoundDoseHelper.this.mNextCsdWarning -= 1.0f;
                    }
                    com.android.server.audio.SoundDoseHelper.this.mCurrentCsd = f;
                    com.android.server.audio.SoundDoseHelper.this.updateSoundDoseRecords_l(soundDoseRecordArr, f);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    SoundDoseHelper(@android.annotation.NonNull com.android.server.audio.AudioService audioService, android.content.Context context, @android.annotation.NonNull com.android.server.audio.AudioService.AudioHandler audioHandler, @android.annotation.NonNull com.android.server.audio.SettingsAdapter settingsAdapter, @android.annotation.NonNull com.android.server.audio.AudioService.ISafeHearingVolumeController iSafeHearingVolumeController) {
        this.mAudioService = audioService;
        this.mAudioHandler = audioHandler;
        this.mSettings = settingsAdapter;
        this.mVolumeController = iSafeHearingVolumeController;
        this.mContext = context;
        initSafeVolumes();
        this.mSafeMediaVolumeState = this.mSettings.getGlobalInt(audioService.getContentResolver(), "audio_safe_volume_state", 0);
        this.mSafeMediaVolumeIndex = this.mContext.getResources().getInteger(android.R.integer.config_reduceBrightColorsStrengthMax) * 10;
        this.mSoundDose.set(android.media.AudioSystem.getSoundDoseInterface(this.mSoundDoseCallback));
        initCsd();
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
    }

    void initSafeVolumes() {
        this.mSafeMediaVolumeDevices.append(4, -1);
        this.mSafeMediaVolumeDevices.append(8, -1);
        this.mSafeMediaVolumeDevices.append(67108864, -1);
        this.mSafeMediaVolumeDevices.append(536870912, -1);
        this.mSafeMediaVolumeDevices.append(536870914, -1);
        this.mSafeMediaVolumeDevices.append(256, -1);
        this.mSafeMediaVolumeDevices.append(128, -1);
    }

    float getOutputRs2UpperBound() {
        if (!this.mEnableCsd.get()) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Sound dose interface not initialized");
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        try {
            return iSoundDose.getOutputRs2UpperBound();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception while getting the RS2 exposure value", e);
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
    }

    void setOutputRs2UpperBound(float f) {
        if (!this.mEnableCsd.get()) {
            return;
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Sound dose interface not initialized");
            return;
        }
        try {
            iSoundDose.setOutputRs2UpperBound(f);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception while setting the RS2 exposure value", e);
        }
    }

    private boolean updateCsdForTestApi() {
        if (this.mForceCsdProperty.get() != android.os.SystemProperties.getBoolean(SYSTEM_PROPERTY_SAFEMEDIA_CSD_FORCE, false)) {
            updateCsdEnabled("SystemPropertiesChangeCallback");
        }
        return this.mEnableCsd.get();
    }

    float getCsd() {
        if (!this.mEnableCsd.get() && !updateCsdForTestApi()) {
            return -1.0f;
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Sound dose interface not initialized");
            return -1.0f;
        }
        try {
            return iSoundDose.getCsd();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception while getting the CSD value", e);
            return -1.0f;
        }
    }

    void setCsd(float f) {
        android.media.SoundDoseRecord[] soundDoseRecordArr;
        if (!this.mEnableCsd.get() && !updateCsdForTestApi()) {
            return;
        }
        synchronized (this.mCsdStateLock) {
            try {
                this.mCurrentCsd = f;
                this.mNextCsdWarning = (float) java.lang.Math.floor(f + 1.0d);
                this.mDoseRecords.clear();
                if (this.mCurrentCsd > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    android.media.SoundDoseRecord soundDoseRecord = new android.media.SoundDoseRecord();
                    soundDoseRecord.timestamp = android.os.SystemClock.elapsedRealtime() / 1000;
                    soundDoseRecord.value = f;
                    this.mDoseRecords.add(soundDoseRecord);
                }
                soundDoseRecordArr = (android.media.SoundDoseRecord[]) this.mDoseRecords.toArray(new android.media.SoundDoseRecord[0]);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Sound dose interface not initialized");
            return;
        }
        try {
            iSoundDose.resetCsd(f, soundDoseRecordArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception while setting the CSD value", e);
        }
    }

    void resetCsdTimeouts() {
        if (!this.mEnableCsd.get() && !updateCsdForTestApi()) {
            return;
        }
        synchronized (this.mCsdStateLock) {
            this.mLastMomentaryExposureTimeMs = -1L;
        }
    }

    void forceUseFrameworkMel(boolean z) {
        if (!this.mEnableCsd.get() && !updateCsdForTestApi()) {
            return;
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Sound dose interface not initialized");
            return;
        }
        try {
            iSoundDose.forceUseFrameworkMel(z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception while forcing the internal MEL computation", e);
        }
    }

    void forceComputeCsdOnAllDevices(boolean z) {
        if (!this.mEnableCsd.get() && !updateCsdForTestApi()) {
            return;
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Sound dose interface not initialized");
            return;
        }
        try {
            iSoundDose.forceComputeCsdOnAllDevices(z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception while forcing CSD computation on all devices", e);
        }
    }

    boolean isCsdEnabled() {
        if (!this.mEnableCsd.get()) {
            return false;
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Sound dose interface not initialized");
            return false;
        }
        try {
            return iSoundDose.isSoundDoseHalSupported();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception while querying the csd enabled status", e);
            return false;
        }
    }

    boolean isCsdAsAFeatureAvailable() {
        boolean z;
        synchronized (this.mCsdAsAFeatureLock) {
            z = this.mIsCsdAsAFeatureAvailable;
        }
        return z;
    }

    boolean isCsdAsAFeatureEnabled() {
        boolean z;
        synchronized (this.mCsdAsAFeatureLock) {
            z = this.mIsCsdAsAFeatureEnabled;
        }
        return z;
    }

    void setCsdAsAFeatureEnabled(boolean z) {
        boolean z2;
        synchronized (this.mCsdAsAFeatureLock) {
            try {
                z2 = this.mIsCsdAsAFeatureEnabled != z && this.mIsCsdAsAFeatureAvailable;
                this.mIsCsdAsAFeatureEnabled = z;
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    this.mSettings.putSecureIntForUser(this.mAudioService.getContentResolver(), "audio_safe_csd_as_a_feature_enabled", this.mIsCsdAsAFeatureEnabled ? 1 : 0, -2);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z2) {
            updateCsdEnabled("setCsdAsAFeatureEnabled");
        }
    }

    void setAudioDeviceCategory(java.lang.String str, int i, boolean z) {
        if (!this.mEnableCsd.get()) {
            return;
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Sound dose interface not initialized");
            return;
        }
        try {
            android.media.ISoundDose.AudioDeviceCategory audioDeviceCategory = new android.media.ISoundDose.AudioDeviceCategory();
            audioDeviceCategory.address = str;
            audioDeviceCategory.internalAudioType = i;
            audioDeviceCategory.csdCompatible = z;
            iSoundDose.setAudioDeviceCategory(audioDeviceCategory);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Exception while setting the audio device category", e);
        }
    }

    void initCachedAudioDeviceCategories(java.util.Collection<com.android.server.audio.AdiDeviceState> collection) {
        for (com.android.server.audio.AdiDeviceState adiDeviceState : collection) {
            if (adiDeviceState.getAudioDeviceCategory() != 0) {
                android.media.ISoundDose.AudioDeviceCategory audioDeviceCategory = new android.media.ISoundDose.AudioDeviceCategory();
                audioDeviceCategory.address = adiDeviceState.getDeviceAddress();
                audioDeviceCategory.internalAudioType = adiDeviceState.getInternalDeviceType();
                audioDeviceCategory.csdCompatible = adiDeviceState.getAudioDeviceCategory() == 3;
                this.mCachedAudioDeviceCategories.add(audioDeviceCategory);
            }
        }
    }

    int safeMediaVolumeIndex(int i) {
        int i2 = this.mSafeMediaVolumeDevices.get(i);
        if (i2 == -1) {
            return com.android.server.audio.AudioService.MAX_STREAM_VOLUME[3];
        }
        return i2;
    }

    void restoreMusicActiveMs() {
        synchronized (this.mSafeMediaVolumeStateLock) {
            this.mMusicActiveMs = android.util.MathUtils.constrain(this.mSettings.getSecureIntForUser(this.mAudioService.getContentResolver(), "unsafe_volume_music_active_ms", 0, -2), 0, 72000000);
        }
    }

    void enforceSafeMediaVolumeIfActive(java.lang.String str) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            try {
                if (this.mSafeMediaVolumeState == 3) {
                    enforceSafeMediaVolume(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void enforceSafeMediaVolume(java.lang.String str) {
        com.android.server.audio.AudioService.VolumeStreamState vssVolumeForStream = this.mAudioService.getVssVolumeForStream(3);
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            int keyAt = this.mSafeMediaVolumeDevices.keyAt(i);
            int index = vssVolumeForStream.getIndex(keyAt);
            int safeMediaVolumeIndex = safeMediaVolumeIndex(keyAt);
            if (index > safeMediaVolumeIndex) {
                vssVolumeForStream.setIndex(safeMediaVolumeIndex, keyAt, str, true);
                this.mAudioHandler.sendMessageAtTime(this.mAudioHandler.obtainMessage(0, keyAt, 0, vssVolumeForStream), 0L);
            }
        }
    }

    boolean checkSafeMediaVolume(int i, int i2, int i3) {
        boolean checkSafeMediaVolume_l;
        synchronized (this.mSafeMediaVolumeStateLock) {
            checkSafeMediaVolume_l = checkSafeMediaVolume_l(i, i2, i3);
        }
        return checkSafeMediaVolume_l;
    }

    @com.android.internal.annotations.GuardedBy({"mSafeMediaVolumeStateLock"})
    private boolean checkSafeMediaVolume_l(int i, int i2, int i3) {
        return this.mSafeMediaVolumeState == 3 && com.android.server.audio.AudioService.mStreamVolumeAlias[i] == 3 && safeDevicesContains(i3) && i2 > safeMediaVolumeIndex(i3);
    }

    boolean willDisplayWarningAfterCheckVolume(int i, int i2, int i3, int i4) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            try {
                if (checkSafeMediaVolume_l(i, i2, i3)) {
                    this.mVolumeController.postDisplaySafeVolumeWarning(i4);
                    this.mPendingVolumeCommand = new com.android.server.audio.SoundDoseHelper.StreamVolumeCommand(i, i2, i4, i3);
                    return true;
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void disableSafeMediaVolume(java.lang.String str) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            try {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                setSafeMediaVolumeEnabled(false, str);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                if (this.mPendingVolumeCommand != null) {
                    this.mAudioService.onSetStreamVolume(this.mPendingVolumeCommand.mStreamType, this.mPendingVolumeCommand.mIndex, this.mPendingVolumeCommand.mFlags, this.mPendingVolumeCommand.mDevice, str, true, true);
                    this.mPendingVolumeCommand = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void scheduleMusicActiveCheck() {
        synchronized (this.mSafeMediaVolumeStateLock) {
            cancelMusicActiveCheck();
            this.mMusicActiveIntent = android.app.PendingIntent.getBroadcast(this.mContext, 1, new android.content.Intent(ACTION_CHECK_MUSIC_ACTIVE), android.hardware.audio.common.V2_0.AudioFormat.DTS_HD);
            this.mAlarmManager.setExactAndAllowWhileIdle(2, android.os.SystemClock.elapsedRealtime() + 60000, this.mMusicActiveIntent);
        }
    }

    void onCheckMusicActive(java.lang.String str, boolean z) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            try {
                if (this.mSafeMediaVolumeState == 2) {
                    int deviceForStream = this.mAudioService.getDeviceForStream(3);
                    if (safeDevicesContains(deviceForStream) && z) {
                        scheduleMusicActiveCheck();
                        if (this.mAudioService.getVssVolumeForDevice(3, deviceForStream) > safeMediaVolumeIndex(deviceForStream)) {
                            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                            if (this.mLastMusicActiveTimeMs != 0) {
                                this.mMusicActiveMs += (int) (elapsedRealtime - this.mLastMusicActiveTimeMs);
                            }
                            this.mLastMusicActiveTimeMs = elapsedRealtime;
                            android.util.Log.i(TAG, "onCheckMusicActive() mMusicActiveMs: " + this.mMusicActiveMs);
                            if (this.mMusicActiveMs > 72000000) {
                                setSafeMediaVolumeEnabled(true, str);
                                this.mMusicActiveMs = 0;
                            }
                            saveMusicActiveMs();
                        }
                    } else {
                        cancelMusicActiveCheck();
                        this.mLastMusicActiveTimeMs = 0L;
                    }
                }
            } finally {
            }
        }
    }

    void configureSafeMedia(boolean z, java.lang.String str) {
        long j;
        int i = z ? 1002 : 1001;
        this.mAudioHandler.removeMessages(i);
        if (!z) {
            j = 0;
        } else {
            j = android.os.SystemClock.uptimeMillis() + (android.os.SystemProperties.getBoolean(SYSTEM_PROPERTY_SAFEMEDIA_BYPASS, false) ? 0 : 30000);
        }
        this.mAudioHandler.sendMessageAtTime(this.mAudioHandler.obtainMessage(i, 0, 0, str), j);
    }

    void initSafeMediaVolumeIndex() {
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            int keyAt = this.mSafeMediaVolumeDevices.keyAt(i);
            if (this.mSafeMediaVolumeDevices.valueAt(i) == -1) {
                this.mSafeMediaVolumeDevices.put(keyAt, getSafeDeviceMediaVolumeIndex(keyAt));
            }
        }
    }

    int getSafeMediaVolumeIndex(int i) {
        if (this.mSafeMediaVolumeState == 3 && safeDevicesContains(i)) {
            return safeMediaVolumeIndex(i);
        }
        return -1;
    }

    boolean raiseVolumeDisplaySafeMediaVolume(int i, int i2, int i3, int i4) {
        if (!checkSafeMediaVolume(i, i2, i3)) {
            return false;
        }
        this.mVolumeController.postDisplaySafeVolumeWarning(i4);
        return true;
    }

    boolean safeDevicesContains(int i) {
        return this.mSafeMediaVolumeDevices.get(i, -1) >= 0;
    }

    void invalidatePendingVolumeCommand() {
        synchronized (this.mSafeMediaVolumeStateLock) {
            this.mPendingVolumeCommand = null;
        }
    }

    void handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1001:
            case 1002:
                onConfigureSafeMedia(message.what == 1002, (java.lang.String) message.obj);
                break;
            case 1003:
                onPersistSafeVolumeState(message.arg1);
                break;
            case 1004:
                this.mSettings.putSecureIntForUser(this.mAudioService.getContentResolver(), "unsafe_volume_music_active_ms", message.arg1, -2);
                break;
            case 1005:
                onPersistSoundDoseRecords();
                break;
            case 1006:
                int i = message.arg1;
                boolean z = message.arg2 == 1;
                com.android.server.audio.AudioService.VolumeStreamState volumeStreamState = (com.android.server.audio.AudioService.VolumeStreamState) message.obj;
                updateDoseAttenuation(volumeStreamState.getIndex(i), i, volumeStreamState.getStreamType(), z);
                break;
            case 1007:
                onLowerVolumeToRs1();
                break;
            default:
                android.util.Log.e(TAG, "Unexpected msg to handle: " + message.what);
                break;
        }
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.print("  mEnableCsd=");
        printWriter.println(this.mEnableCsd.get());
        if (this.mEnableCsd.get()) {
            synchronized (this.mCsdStateLock) {
                printWriter.print("  mCurrentCsd=");
                printWriter.println(this.mCurrentCsd);
            }
        }
        printWriter.print("  mSafeMediaVolumeState=");
        printWriter.println(safeMediaVolumeStateToString(this.mSafeMediaVolumeState));
        printWriter.print("  mSafeMediaVolumeIndex=");
        printWriter.println(this.mSafeMediaVolumeIndex);
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            printWriter.print("  mSafeMediaVolumeIndex[");
            printWriter.print(this.mSafeMediaVolumeDevices.keyAt(i));
            printWriter.print("]=");
            printWriter.println(this.mSafeMediaVolumeDevices.valueAt(i));
        }
        printWriter.print("  mSafeMediaVolumeDbfs=");
        printWriter.println(this.mSafeMediaVolumeDbfs);
        printWriter.print("  mMusicActiveMs=");
        printWriter.println(this.mMusicActiveMs);
        printWriter.print("  mMcc=");
        printWriter.println(this.mMcc);
        printWriter.print("  mPendingVolumeCommand=");
        printWriter.println(this.mPendingVolumeCommand);
        printWriter.println();
        this.mLogger.dump(printWriter);
        printWriter.println();
    }

    void reset() {
        android.util.Log.d(TAG, "Reset the sound dose helper");
        this.mSoundDose.compareAndExchange(null, android.media.AudioSystem.getSoundDoseInterface(this.mSoundDoseCallback));
        synchronized (this.mCsdStateLock) {
            try {
                android.media.ISoundDose iSoundDose = this.mSoundDose.get();
                if (iSoundDose != null && iSoundDose.asBinder().isBinderAlive() && this.mCurrentCsd != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    android.util.Log.d(TAG, "Resetting the saved sound dose value " + this.mCurrentCsd);
                    iSoundDose.resetCsd(this.mCurrentCsd, (android.media.SoundDoseRecord[]) this.mDoseRecords.toArray(new android.media.SoundDoseRecord[0]));
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void updateDoseAttenuation(int i, int i2, int i3, boolean z) {
        if (!this.mEnableCsd.get()) {
            return;
        }
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "Can not apply attenuation. ISoundDose itf is null.");
            return;
        }
        try {
            if (!z) {
                iSoundDose.updateAttenuation(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i2);
            } else if (com.android.server.audio.AudioService.mStreamVolumeAlias[i3] == 3 && safeDevicesContains(i2)) {
                iSoundDose.updateAttenuation(-android.media.AudioSystem.getStreamVolumeDB(3, (i + 5) / 10, i2), i2);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Could not apply the attenuation for MEL calculation with volume index " + i, e);
        }
    }

    private void initCsd() {
        android.media.ISoundDose iSoundDose = this.mSoundDose.get();
        if (iSoundDose == null) {
            android.util.Log.w(TAG, "ISoundDose instance is null.");
            return;
        }
        try {
            iSoundDose.setCsdEnabled(this.mEnableCsd.get());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Cannot disable CSD", e);
        }
        if (!this.mEnableCsd.get()) {
            return;
        }
        android.util.Log.v(TAG, "Initializing sound dose");
        try {
            if (!this.mCachedAudioDeviceCategories.isEmpty()) {
                iSoundDose.initCachedAudioDeviceCategories((android.media.ISoundDose.AudioDeviceCategory[]) this.mCachedAudioDeviceCategories.toArray(new android.media.ISoundDose.AudioDeviceCategory[0]));
                this.mCachedAudioDeviceCategories.clear();
            }
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Exception while initializing the cached audio device categories", e2);
        }
        synchronized (this.mCsdAsAFeatureLock) {
            this.mIsCsdAsAFeatureEnabled = this.mSettings.getSecureIntForUser(this.mAudioService.getContentResolver(), "audio_safe_csd_as_a_feature_enabled", 0, -2) != 0;
        }
        synchronized (this.mCsdStateLock) {
            try {
                if (this.mGlobalTimeOffsetInSecs == -1) {
                    this.mGlobalTimeOffsetInSecs = java.lang.System.currentTimeMillis() / 1000;
                }
                float f = this.mCurrentCsd;
                this.mCurrentCsd = parseGlobalSettingFloat("audio_safe_csd_current_value", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                if (this.mCurrentCsd != f) {
                    this.mNextCsdWarning = parseGlobalSettingFloat("audio_safe_csd_next_warning", 1.0f);
                    java.util.List<android.media.SoundDoseRecord> persistedStringToRecordList = persistedStringToRecordList(this.mSettings.getGlobalString(this.mAudioService.getContentResolver(), "audio_safe_csd_dose_records"), this.mGlobalTimeOffsetInSecs);
                    if (persistedStringToRecordList != null) {
                        this.mDoseRecords.addAll(persistedStringToRecordList);
                        sanitizeDoseRecords_l();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        reset();
    }

    private void onConfigureSafeMedia(boolean z, java.lang.String str) {
        updateCsdEnabled(str);
        synchronized (this.mSafeMediaVolumeStateLock) {
            try {
                int i = this.mContext.getResources().getConfiguration().mcc;
                if (this.mMcc != i || (this.mMcc == 0 && z)) {
                    this.mSafeMediaVolumeIndex = this.mContext.getResources().getInteger(android.R.integer.config_reduceBrightColorsStrengthMax) * 10;
                    initSafeMediaVolumeIndex();
                    updateSafeMediaVolume_l(str);
                    this.mMcc = i;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSafeMediaVolumeStateLock"})
    private void updateSafeMediaVolume_l(java.lang.String str) {
        int i = 1;
        if ((this.mContext.getResources().getBoolean(android.R.bool.config_restartRadioAfterProvisioning) || android.os.SystemProperties.getBoolean(SYSTEM_PROPERTY_SAFEMEDIA_FORCE, false)) && !(android.os.SystemProperties.getBoolean(SYSTEM_PROPERTY_SAFEMEDIA_BYPASS, false) || this.mEnableCsd.get())) {
            if (this.mSafeMediaVolumeState != 2) {
                if (this.mMusicActiveMs == 0) {
                    this.mSafeMediaVolumeState = 3;
                    enforceSafeMediaVolume(str);
                } else {
                    this.mSafeMediaVolumeState = 2;
                    this.mLastMusicActiveTimeMs = 0L;
                }
            }
            i = 3;
        } else {
            this.mSafeMediaVolumeState = 1;
        }
        this.mAudioHandler.sendMessageAtTime(this.mAudioHandler.obtainMessage(1003, i, 0, null), 0L);
    }

    private void updateCsdEnabled(java.lang.String str) {
        this.mForceCsdProperty.set(android.os.SystemProperties.getBoolean(SYSTEM_PROPERTY_SAFEMEDIA_CSD_FORCE, false));
        boolean z = this.mContext.getResources().getBoolean(android.R.bool.config_restartRadioAfterProvisioning);
        boolean z2 = this.mContext.getResources().getBoolean(android.R.bool.config_restart_radio_on_pdp_fail_regular_deactivation);
        boolean z3 = (z && z2) || this.mForceCsdProperty.get();
        synchronized (this.mCsdAsAFeatureLock) {
            try {
                if (!z && z2) {
                    this.mIsCsdAsAFeatureAvailable = true;
                    boolean z4 = this.mIsCsdAsAFeatureEnabled || this.mForceCsdProperty.get();
                    android.util.Log.v(TAG, str + ": CSD as a feature is not enforced and enabled: " + z4);
                    z3 = z4;
                } else {
                    this.mIsCsdAsAFeatureAvailable = false;
                }
            } finally {
            }
        }
        if (this.mEnableCsd.compareAndSet(!z3, z3)) {
            android.util.Log.i(TAG, str + ": enabled CSD " + z3);
            initCsd();
            synchronized (this.mSafeMediaVolumeStateLock) {
                initSafeMediaVolumeIndex();
                updateSafeMediaVolume_l(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTimeoutMsForWarning(int i) {
        switch (i) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                android.util.Log.e(TAG, "Invalid CSD warning " + i, new java.lang.Exception());
                break;
        }
        return -1;
    }

    @com.android.internal.annotations.GuardedBy({"mSafeMediaVolumeStateLock"})
    private void setSafeMediaVolumeEnabled(boolean z, java.lang.String str) {
        if (this.mSafeMediaVolumeState == 0 || this.mSafeMediaVolumeState == 1) {
            return;
        }
        if (z && this.mSafeMediaVolumeState == 2) {
            this.mSafeMediaVolumeState = 3;
            enforceSafeMediaVolume(str);
        } else if (!z && this.mSafeMediaVolumeState == 3) {
            this.mSafeMediaVolumeState = 2;
            this.mMusicActiveMs = 1;
            this.mLastMusicActiveTimeMs = 0L;
            saveMusicActiveMs();
            scheduleMusicActiveCheck();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSafeMediaVolumeStateLock"})
    private void cancelMusicActiveCheck() {
        if (this.mMusicActiveIntent != null) {
            this.mAlarmManager.cancel(this.mMusicActiveIntent);
            this.mMusicActiveIntent = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mSafeMediaVolumeStateLock"})
    private void saveMusicActiveMs() {
        this.mAudioHandler.obtainMessage(1004, this.mMusicActiveMs, 0).sendToTarget();
    }

    private int getSafeDeviceMediaVolumeIndex(int i) {
        if (!this.mEnableCsd.get()) {
            if (i == 8 || i == 4) {
                return this.mSafeMediaVolumeIndex;
            }
            if (i != 67108864) {
                return -1;
            }
        }
        int i2 = com.android.server.audio.AudioService.MIN_STREAM_VOLUME[3];
        int i3 = com.android.server.audio.AudioService.MAX_STREAM_VOLUME[3];
        this.mSafeMediaVolumeDbfs = this.mContext.getResources().getInteger(android.R.integer.config_reduceBrightColorsStrengthMin) / 100.0f;
        while (true) {
            if (java.lang.Math.abs(i3 - i2) <= 1) {
                break;
            }
            int i4 = (i3 + i2) / 2;
            float streamVolumeDB = android.media.AudioSystem.getStreamVolumeDB(3, i4, i);
            if (java.lang.Float.isNaN(streamVolumeDB)) {
                break;
            }
            if (streamVolumeDB == this.mSafeMediaVolumeDbfs) {
                i2 = i4;
                break;
            }
            if (streamVolumeDB < this.mSafeMediaVolumeDbfs) {
                i2 = i4;
            } else {
                i3 = i4;
            }
        }
        return i2 * 10;
    }

    private void onPersistSafeVolumeState(int i) {
        this.mSettings.putGlobalInt(this.mAudioService.getContentResolver(), "audio_safe_volume_state", i);
    }

    private static java.lang.String safeMediaVolumeStateToString(int i) {
        switch (i) {
            case 0:
                return "SAFE_MEDIA_VOLUME_NOT_CONFIGURED";
            case 1:
                return "SAFE_MEDIA_VOLUME_DISABLED";
            case 2:
                return "SAFE_MEDIA_VOLUME_INACTIVE";
            case 3:
                return "SAFE_MEDIA_VOLUME_ACTIVE";
            default:
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mCsdStateLock"})
    public void updateSoundDoseRecords_l(android.media.SoundDoseRecord[] soundDoseRecordArr, float f) {
        long j = 0;
        for (final android.media.SoundDoseRecord soundDoseRecord : soundDoseRecordArr) {
            android.util.Log.i(TAG, "  new record: " + soundDoseRecord);
            j += (long) soundDoseRecord.duration;
            if (soundDoseRecord.value < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                if (!this.mDoseRecords.removeIf(new java.util.function.Predicate() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$updateSoundDoseRecords_l$0;
                        lambda$updateSoundDoseRecords_l$0 = com.android.server.audio.SoundDoseHelper.lambda$updateSoundDoseRecords_l$0(soundDoseRecord, (android.media.SoundDoseRecord) obj);
                        return lambda$updateSoundDoseRecords_l$0;
                    }
                })) {
                    android.util.Log.w(TAG, "Could not find cached record to remove: " + soundDoseRecord);
                }
            } else if (soundDoseRecord.value > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                this.mDoseRecords.add(soundDoseRecord);
            }
        }
        sanitizeDoseRecords_l();
        this.mAudioHandler.sendMessageAtTime(this.mAudioHandler.obtainMessage(1005, 0, 0, null), 0L);
        this.mLogger.enqueue(com.android.server.audio.AudioServiceEvents.SoundDoseEvent.getDoseUpdateEvent(f, j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateSoundDoseRecords_l$0(android.media.SoundDoseRecord soundDoseRecord, android.media.SoundDoseRecord soundDoseRecord2) {
        return soundDoseRecord2.value == (-soundDoseRecord.value) && soundDoseRecord2.timestamp == soundDoseRecord.timestamp && soundDoseRecord2.averageMel == soundDoseRecord.averageMel && soundDoseRecord2.duration == soundDoseRecord.duration;
    }

    @com.android.internal.annotations.GuardedBy({"mCsdStateLock"})
    private void sanitizeDoseRecords_l() {
        if (this.mDoseRecords.size() > MAX_NUMBER_OF_CACHED_RECORDS) {
            int size = this.mDoseRecords.size() - MAX_NUMBER_OF_CACHED_RECORDS;
            android.util.Log.w(TAG, "Removing " + size + " records from the total of " + this.mDoseRecords.size());
            java.util.Iterator<android.media.SoundDoseRecord> it = this.mDoseRecords.iterator();
            while (it.hasNext() && size > 0) {
                it.next();
                it.remove();
                size--;
            }
        }
    }

    private void onPersistSoundDoseRecords() {
        synchronized (this.mCsdStateLock) {
            try {
                if (this.mGlobalTimeOffsetInSecs == -1) {
                    this.mGlobalTimeOffsetInSecs = java.lang.System.currentTimeMillis() / 1000;
                }
                this.mSettings.putGlobalString(this.mAudioService.getContentResolver(), "audio_safe_csd_current_value", java.lang.Float.toString(this.mCurrentCsd));
                this.mSettings.putGlobalString(this.mAudioService.getContentResolver(), "audio_safe_csd_next_warning", java.lang.Float.toString(this.mNextCsdWarning));
                this.mSettings.putGlobalString(this.mAudioService.getContentResolver(), "audio_safe_csd_dose_records", (java.lang.String) this.mDoseRecords.stream().map(new java.util.function.Function() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        java.lang.String lambda$onPersistSoundDoseRecords$1;
                        lambda$onPersistSoundDoseRecords$1 = com.android.server.audio.SoundDoseHelper.this.lambda$onPersistSoundDoseRecords$1((android.media.SoundDoseRecord) obj);
                        return lambda$onPersistSoundDoseRecords$1;
                    }
                }).collect(java.util.stream.Collectors.joining(PERSIST_CSD_RECORD_SEPARATOR_CHAR)));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$onPersistSoundDoseRecords$1(android.media.SoundDoseRecord soundDoseRecord) {
        return recordToPersistedString(soundDoseRecord, this.mGlobalTimeOffsetInSecs);
    }

    private static java.lang.String recordToPersistedString(android.media.SoundDoseRecord soundDoseRecord, long j) {
        return convertToGlobalTime(soundDoseRecord.timestamp, j) + PERSIST_CSD_RECORD_FIELD_SEPARATOR + soundDoseRecord.duration + PERSIST_CSD_RECORD_FIELD_SEPARATOR + java.lang.String.format("%.3f", java.lang.Float.valueOf(soundDoseRecord.value)) + PERSIST_CSD_RECORD_FIELD_SEPARATOR + java.lang.String.format("%.3f", java.lang.Float.valueOf(soundDoseRecord.averageMel));
    }

    private static long convertToGlobalTime(long j, long j2) {
        return j + j2;
    }

    private static long convertToBootTime(long j, long j2) {
        return j - j2;
    }

    private static java.util.List<android.media.SoundDoseRecord> persistedStringToRecordList(java.lang.String str, final long j) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return (java.util.List) java.util.Arrays.stream(android.text.TextUtils.split(str, PERSIST_CSD_RECORD_SEPARATOR)).map(new java.util.function.Function() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.media.SoundDoseRecord lambda$persistedStringToRecordList$2;
                lambda$persistedStringToRecordList$2 = com.android.server.audio.SoundDoseHelper.lambda$persistedStringToRecordList$2(j, (java.lang.String) obj);
                return lambda$persistedStringToRecordList$2;
            }
        }).filter(new java.util.function.Predicate() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return java.util.Objects.nonNull((android.media.SoundDoseRecord) obj);
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.media.SoundDoseRecord lambda$persistedStringToRecordList$2(long j, java.lang.String str) {
        return persistedStringToRecord(str, j);
    }

    private static android.media.SoundDoseRecord persistedStringToRecord(java.lang.String str, long j) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        java.lang.String[] split = android.text.TextUtils.split(str, PERSIST_CSD_RECORD_FIELD_SEPARATOR);
        if (split.length != 4) {
            android.util.Log.w(TAG, "Expecting 4 fields for a SoundDoseRecord, parsed " + split.length);
            return null;
        }
        android.media.SoundDoseRecord soundDoseRecord = new android.media.SoundDoseRecord();
        try {
            soundDoseRecord.timestamp = convertToBootTime(java.lang.Long.parseLong(split[0]), j);
            soundDoseRecord.duration = java.lang.Integer.parseInt(split[1]);
            soundDoseRecord.value = java.lang.Float.parseFloat(split[2]);
            soundDoseRecord.averageMel = java.lang.Float.parseFloat(split[3]);
            return soundDoseRecord;
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(TAG, "Unable to parse persisted SoundDoseRecord: " + str, e);
            return null;
        }
    }

    private float parseGlobalSettingFloat(java.lang.String str, float f) {
        java.lang.String globalString = this.mSettings.getGlobalString(this.mAudioService.getContentResolver(), str);
        if (globalString == null || globalString.isEmpty()) {
            android.util.Log.v(TAG, "No value stored in settings " + str);
            return f;
        }
        try {
            return java.lang.Float.parseFloat(globalString);
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(TAG, "Error parsing float from settings " + str, e);
            return f;
        }
    }

    private void onLowerVolumeToRs1() {
        android.media.AudioDeviceAttributes audioDeviceAttributes;
        int i;
        this.mLogger.enqueue(com.android.server.audio.AudioServiceEvents.SoundDoseEvent.getLowerVolumeToRs1Event());
        java.util.ArrayList<android.media.AudioDeviceAttributes> devicesForAttributesInt = this.mAudioService.getDevicesForAttributesInt(new android.media.AudioAttributes.Builder().setUsage(1).build(), true);
        if (!devicesForAttributesInt.isEmpty()) {
            android.media.AudioDeviceAttributes audioDeviceAttributes2 = devicesForAttributesInt.get(0);
            i = audioDeviceAttributes2.getInternalType();
            audioDeviceAttributes = audioDeviceAttributes2;
        } else {
            audioDeviceAttributes = new android.media.AudioDeviceAttributes(67108864, "");
            i = 67108864;
        }
        this.mAudioService.setStreamVolumeWithAttributionInt(3, safeMediaVolumeIndex(i) / 10, 0, audioDeviceAttributes, this.mContext.getOpPackageName(), null, true);
    }

    private static class StreamVolumeCommand {
        public final int mDevice;
        public final int mFlags;
        public final int mIndex;
        public final int mStreamType;

        StreamVolumeCommand(int i, int i2, int i3, int i4) {
            this.mStreamType = i;
            this.mIndex = i2;
            this.mFlags = i3;
            this.mDevice = i4;
        }

        public java.lang.String toString() {
            return "{streamType=" + this.mStreamType + ",index=" + this.mIndex + ",flags=" + this.mFlags + ",device=" + this.mDevice + '}';
        }
    }
}
