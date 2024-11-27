package com.android.server.notification;

/* loaded from: classes2.dex */
public final class NotificationAttentionHelper {
    private static final int DEFAULT_NOTIFICATION_COOLDOWN_ALL = 1;
    private static final int DEFAULT_NOTIFICATION_COOLDOWN_ENABLED = 1;
    private static final int DEFAULT_NOTIFICATION_COOLDOWN_ENABLED_FOR_WORK = 1;
    private static final int DEFAULT_NOTIFICATION_COOLDOWN_VIBRATE_UNLOCKED = 0;
    private static final float DEFAULT_VOLUME = 1.0f;
    private android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private com.android.server.lights.LogicalLight mAttentionLight;
    private android.media.AudioManager mAudioManager;
    private int mCallState;
    private final android.content.Context mContext;
    private boolean mDisableNotificationEffects;
    private final com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver mFlagResolver;
    boolean mHasLight;
    private final android.media.AudioAttributes mInCallNotificationAudioAttributes;
    private final android.net.Uri mInCallNotificationUri;
    private final float mInCallNotificationVolume;
    private boolean mIsAutomotive;
    private android.app.KeyguardManager mKeyguardManager;
    private org.lineageos.internal.notification.LineageNotificationLights mLineageNotificationLights;
    private final com.android.server.notification.NotificationManagerPrivate mNMP;
    private boolean mNotificationCooldownApplyToAll;
    private boolean mNotificationCooldownEnabled;
    private boolean mNotificationCooldownForWorkEnabled;
    private boolean mNotificationCooldownVibrateUnlocked;
    private boolean mNotificationEffectsEnabledForAutomotive;
    private com.android.server.lights.LogicalLight mNotificationLight;
    boolean mNotificationPulseEnabled;
    private final android.content.pm.PackageManager mPackageManager;
    private final com.android.server.notification.NotificationAttentionHelper.SettingsObserver mSettingsObserver;
    private java.lang.String mSoundNotificationKey;
    private final com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy mStrategy;
    private boolean mSystemReady;
    private final android.telephony.TelephonyManager mTelephonyManager;
    private final android.os.UserManager mUm;
    private final com.android.server.notification.NotificationUsageStats mUsageStats;
    private final boolean mUseAttentionLight;
    private java.lang.String mVibrateNotificationKey;
    private com.android.server.notification.VibratorHelper mVibratorHelper;
    private final com.android.server.notification.ZenModeHelper mZenModeHelper;
    static final java.lang.String TAG = "NotifAttentionHelper";
    static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    static final boolean DEBUG_INTERRUPTIVENESS = android.os.SystemProperties.getBoolean("debug.notification.interruptiveness", false);

    @com.android.internal.annotations.VisibleForTesting
    static final java.util.Set<java.lang.String> NOTIFICATION_AVALANCHE_TRIGGER_INTENTS = java.util.Set.of("android.intent.action.AIRPLANE_MODE", "android.intent.action.BOOT_COMPLETED", "android.intent.action.USER_SWITCHED", "android.intent.action.MANAGED_PROFILE_AVAILABLE");

    @com.android.internal.annotations.VisibleForTesting
    static final java.util.Map<java.lang.String, android.util.Pair<java.lang.String, java.lang.Boolean>> NOTIFICATION_AVALANCHE_TRIGGER_EXTRAS = java.util.Map.of("android.intent.action.AIRPLANE_MODE", new android.util.Pair("state", false), "android.intent.action.MANAGED_PROFILE_AVAILABLE", new android.util.Pair("android.intent.extra.QUIET_MODE", false));
    java.util.ArrayList<java.lang.String> mLights = new java.util.ArrayList<>();
    private boolean mInCallStateOffHook = false;
    private boolean mScreenOn = true;
    private boolean mUserPresent = false;
    private android.os.Binder mCallNotificationToken = null;
    private final android.util.ArrayMap<java.lang.String, java.lang.Long> mLastSoundTimestamps = new android.util.ArrayMap<>();
    private int mCurrentWorkProfileId = com.android.server.am.ProcessList.INVALID_ADJ;
    private final android.content.BroadcastReceiver mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.notification.NotificationAttentionHelper.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            if (action.equals("android.intent.action.SCREEN_ON")) {
                com.android.server.notification.NotificationAttentionHelper.this.mScreenOn = true;
                com.android.server.notification.NotificationAttentionHelper.this.updateLightsLocked();
            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                com.android.server.notification.NotificationAttentionHelper.this.mScreenOn = false;
                com.android.server.notification.NotificationAttentionHelper.this.mUserPresent = false;
                com.android.server.notification.NotificationAttentionHelper.this.updateLightsLocked();
            } else if (action.equals("android.intent.action.PHONE_STATE")) {
                com.android.server.notification.NotificationAttentionHelper.this.mInCallStateOffHook = android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK.equals(intent.getStringExtra("state"));
                com.android.server.notification.NotificationAttentionHelper.this.updateLightsLocked();
            } else if (action.equals("android.intent.action.USER_PRESENT")) {
                com.android.server.notification.NotificationAttentionHelper.this.mUserPresent = true;
                if (com.android.server.notification.NotificationAttentionHelper.this.mNotificationLight != null && !com.android.server.notification.NotificationAttentionHelper.this.mLineageNotificationLights.showLightsScreenOn()) {
                    com.android.server.notification.NotificationAttentionHelper.this.mNotificationLight.turnOff();
                }
            } else if (action.equals("android.intent.action.USER_ADDED") || action.equals("android.intent.action.USER_REMOVED") || action.equals("android.intent.action.USER_SWITCHED") || action.equals("android.intent.action.USER_UNLOCKED")) {
                com.android.server.notification.NotificationAttentionHelper.this.loadUserSettings();
            }
            com.android.server.notification.Flags.crossAppPoliteNotifications();
        }
    };

    public NotificationAttentionHelper(android.content.Context context, com.android.server.lights.LightsManager lightsManager, android.view.accessibility.AccessibilityManager accessibilityManager, android.content.pm.PackageManager packageManager, android.os.UserManager userManager, com.android.server.notification.NotificationUsageStats notificationUsageStats, com.android.server.notification.NotificationManagerPrivate notificationManagerPrivate, com.android.server.notification.ZenModeHelper zenModeHelper, com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.FlagResolver flagResolver) {
        this.mHasLight = true;
        this.mContext = context;
        this.mPackageManager = packageManager;
        this.mTelephonyManager = (android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class);
        this.mAccessibilityManager = accessibilityManager;
        this.mUm = userManager;
        this.mNMP = notificationManagerPrivate;
        this.mUsageStats = notificationUsageStats;
        this.mZenModeHelper = zenModeHelper;
        this.mFlagResolver = flagResolver;
        this.mVibratorHelper = new com.android.server.notification.VibratorHelper(context);
        this.mNotificationLight = lightsManager.getLight(4);
        this.mAttentionLight = lightsManager.getLight(5);
        android.content.res.Resources resources = context.getResources();
        this.mUseAttentionLight = resources.getBoolean(android.R.bool.config_usbChargingMessage);
        this.mHasLight = resources.getBoolean(android.R.bool.config_hotswapCapable);
        if (android.provider.Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) == 0) {
            this.mDisableNotificationEffects = true;
        }
        this.mInCallNotificationUri = android.net.Uri.parse("file://" + resources.getString(android.R.string.config_globalAppSearchDataQuerierPackage));
        this.mInCallNotificationAudioAttributes = new android.media.AudioAttributes.Builder().setContentType(4).setUsage(2).build();
        this.mInCallNotificationVolume = resources.getFloat(android.R.dimen.config_displayWhiteBalanceColorTemperatureFilterIntercept);
        this.mLineageNotificationLights = new org.lineageos.internal.notification.LineageNotificationLights(this.mContext, new org.lineageos.internal.notification.LineageNotificationLights.LedUpdater() { // from class: com.android.server.notification.NotificationAttentionHelper.1
            public void update() {
                com.android.server.notification.NotificationAttentionHelper.this.updateLightsLocked();
            }
        });
        this.mZenModeHelper.addCallback(new com.android.server.notification.NotificationAttentionHelper.AnonymousClass2());
        com.android.server.notification.Flags.politeNotifications();
        this.mStrategy = null;
        this.mSettingsObserver = new com.android.server.notification.NotificationAttentionHelper.SettingsObserver();
        loadUserSettings();
    }

    /* renamed from: com.android.server.notification.NotificationAttentionHelper$2, reason: invalid class name */
    class AnonymousClass2 extends com.android.server.notification.ZenModeHelper.Callback {
        AnonymousClass2() {
        }

        @Override // com.android.server.notification.ZenModeHelper.Callback
        void onZenModeChanged() {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.notification.NotificationAttentionHelper$2$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    com.android.server.notification.NotificationAttentionHelper.AnonymousClass2.this.lambda$onZenModeChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onZenModeChanged$0() throws java.lang.Exception {
            com.android.server.notification.NotificationAttentionHelper.this.mLineageNotificationLights.setZenMode(com.android.server.notification.NotificationAttentionHelper.this.mZenModeHelper.getZenMode());
        }
    }

    private com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy createPolitenessStrategy() {
        com.android.server.notification.Flags.crossAppPoliteNotifications();
        return new com.android.server.notification.NotificationAttentionHelper.StrategyPerApp(this.mFlagResolver.getIntValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.NOTIF_COOLDOWN_T1), this.mFlagResolver.getIntValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.NOTIF_COOLDOWN_T2), this.mFlagResolver.getIntValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.NOTIF_VOLUME1), this.mFlagResolver.getIntValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.NOTIF_VOLUME2), this.mFlagResolver.getIntValue(com.android.internal.config.sysui.SystemUiSystemPropertiesFlags.NotificationFlags.NOTIF_COOLDOWN_COUNTER_RESET));
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy getPolitenessStrategy() {
        return this.mStrategy;
    }

    public void onSystemReady() {
        this.mSystemReady = true;
        this.mIsAutomotive = this.mPackageManager.hasSystemFeature("android.hardware.type.automotive", 0);
        this.mNotificationEffectsEnabledForAutomotive = this.mContext.getResources().getBoolean(android.R.bool.config_enableScreenshotChord);
        this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class);
        this.mKeyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class);
        registerBroadcastListeners();
    }

    private void registerBroadcastListeners() {
        if (this.mPackageManager.hasSystemFeature("android.hardware.telephony")) {
            this.mTelephonyManager.listen(new android.telephony.PhoneStateListener() { // from class: com.android.server.notification.NotificationAttentionHelper.3
                @Override // android.telephony.PhoneStateListener
                public void onCallStateChanged(int i, java.lang.String str) {
                    if (com.android.server.notification.NotificationAttentionHelper.this.mCallState == i) {
                        return;
                    }
                    if (com.android.server.notification.NotificationAttentionHelper.DEBUG) {
                        android.util.Slog.d(com.android.server.notification.NotificationAttentionHelper.TAG, "Call state changed: " + com.android.server.notification.NotificationAttentionHelper.callStateToString(i));
                    }
                    com.android.server.notification.NotificationAttentionHelper.this.mCallState = i;
                }
            }, 32);
        }
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        com.android.server.notification.Flags.crossAppPoliteNotifications();
        this.mContext.registerReceiverAsUser(this.mIntentReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        this.mContext.getContentResolver().registerContentObserver(com.android.server.notification.NotificationAttentionHelper.SettingsObserver.NOTIFICATION_LIGHT_PULSE_URI, false, this.mSettingsObserver, -1);
        com.android.server.notification.Flags.politeNotifications();
        this.mSettingsObserver.onChange(false, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadUserSettings() {
        com.android.server.notification.Flags.politeNotifications();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [int] */
    /* JADX WARN: Type inference failed for: r12v14 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r12v7 */
    @com.android.internal.annotations.VisibleForTesting
    int buzzBeepBlinkLocked(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationAttentionHelper.Signals signals) {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        ?? r12;
        boolean z4;
        int i2;
        boolean z5;
        boolean z6;
        int i3;
        if (this.mIsAutomotive && !this.mNotificationEffectsEnabledForAutomotive) {
            return 0;
        }
        java.lang.String key = notificationRecord.getKey();
        if (DEBUG) {
            android.util.Log.d(TAG, "buzzBeepBlinkLocked " + notificationRecord);
        }
        if (isPoliteNotificationFeatureEnabled(notificationRecord)) {
            this.mStrategy.onNotificationPosted(notificationRecord);
        }
        if (this.mIsAutomotive) {
            z = notificationRecord.getImportance() > 3;
        } else {
            z = notificationRecord.getImportance() >= 3;
        }
        boolean z7 = key != null && key.equals(this.mSoundNotificationKey);
        boolean z8 = key != null && key.equals(this.mVibrateNotificationKey);
        boolean z9 = notificationRecord.isIntercepted() && (notificationRecord.getSuppressedVisualEffects() & 32) != 0;
        if (!notificationRecord.isUpdate && notificationRecord.getImportance() > 1 && !z9 && isNotificationForCurrentUser(notificationRecord, signals)) {
            sendAccessibilityEvent(notificationRecord);
            z2 = true;
        } else {
            z2 = false;
        }
        if (z && isNotificationForCurrentUser(notificationRecord, signals) && this.mSystemReady && this.mAudioManager != null) {
            android.net.Uri sound = notificationRecord.getSound();
            z4 = (sound == null || android.net.Uri.EMPTY.equals(sound)) ? false : true;
            android.os.VibrationEffect vibration = notificationRecord.getVibration();
            if (vibration == null && z4 && this.mAudioManager.getRingerModeInternal() == 1 && this.mAudioManager.getStreamVolume(android.media.AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes())) == 0) {
                vibration = this.mVibratorHelper.createFallbackVibration((notificationRecord.getFlags() & 4) != 0);
            }
            z3 = vibration != null;
            boolean z10 = z3 && this.mNotificationCooldownVibrateUnlocked && this.mUserPresent;
            if ((z4 || z3) && !shouldMuteNotificationLocked(notificationRecord, signals) && !isInSoundTimeoutPeriod(notificationRecord)) {
                if (!z2) {
                    sendAccessibilityEvent(notificationRecord);
                }
                if (DEBUG) {
                    android.util.Slog.v(TAG, "Interrupting!");
                }
                boolean isInsistentUpdate = isInsistentUpdate(notificationRecord);
                if (z4 && !z10) {
                    if (isInsistentUpdate) {
                        z6 = true;
                    } else {
                        if (isInCall()) {
                            playInCallNotification();
                            z6 = true;
                        } else {
                            z6 = playSound(notificationRecord, sound);
                        }
                        if (z6) {
                            this.mSoundNotificationKey = key;
                        }
                    }
                } else {
                    z6 = false;
                }
                boolean z11 = this.mAudioManager.getRingerModeInternal() == 0;
                if (!isInCall() && z3 && !z11) {
                    if (!isInsistentUpdate) {
                        boolean playVibration = playVibration(notificationRecord, vibration, z4 && !z10);
                        i3 = playVibration;
                        if (playVibration) {
                            this.mVibrateNotificationKey = key;
                            i3 = playVibration;
                        }
                    } else {
                        i3 = 1;
                    }
                } else {
                    i3 = 0;
                }
                this.mAccessibilityManager.startFlashNotificationEvent(this.mContext, 3, notificationRecord.getSbn().getPackageName());
                i = i3;
                r12 = z6;
            } else if ((notificationRecord.getFlags() & 4) == 0) {
                i = 0;
                r12 = 0;
            } else {
                i = 0;
                r12 = 0;
                z4 = false;
            }
        } else {
            z3 = false;
            i = 0;
            r12 = 0;
            z4 = false;
        }
        if (z7 && !z4) {
            clearSoundLocked();
        }
        if (z8 && !z3) {
            clearVibrateLocked();
        }
        boolean remove = this.mLights.remove(key);
        if (canShowLightsLocked(notificationRecord, signals, z)) {
            this.mLights.add(key);
            updateLightsLocked();
            if (this.mUseAttentionLight && this.mAttentionLight != null) {
                this.mAttentionLight.pulse();
            }
            i2 = 1;
        } else {
            if (remove) {
                updateLightsLocked();
            }
            i2 = 0;
        }
        if (i != 0 || r12 != 0 || i2 != 0) {
            if (notificationRecord.getSbn().isGroup() && notificationRecord.getSbn().getNotification().isGroupSummary()) {
                if (DEBUG_INTERRUPTIVENESS) {
                    android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is not interruptive: summary");
                }
            } else if (notificationRecord.canBubble()) {
                if (DEBUG_INTERRUPTIVENESS) {
                    android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is not interruptive: bubble");
                }
            } else {
                notificationRecord.setInterruptive(true);
                if (DEBUG_INTERRUPTIVENESS) {
                    android.util.Slog.v(TAG, "INTERRUPTIVENESS: " + notificationRecord.getKey() + " is interruptive: alerted");
                }
            }
        }
        if (i != 0 || r12 != 0) {
            this.mLastSoundTimestamps.put(generateLastSoundTimeoutKey(notificationRecord), java.lang.Long.valueOf(android.os.SystemClock.elapsedRealtime()));
        }
        int politeBit = (r12 != 0 ? 2 : 0) | i | (i2 == 0 ? 0 : 4) | getPoliteBit(notificationRecord);
        if (politeBit <= 0) {
            z5 = true;
        } else {
            z5 = true;
            com.android.internal.logging.MetricsLogger.action(notificationRecord.getLogMaker().setCategory(com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_PREFERENTIAL_NETWORK_SERVICE_ENABLED).setType(1).setSubtype(politeBit));
            com.android.server.EventLogTags.writeNotificationAlert(key, i, r12, i2, getPolitenessState(notificationRecord));
        }
        notificationRecord.setAudiblyAlerted((i == 0 && r12 == 0) ? false : z5);
        com.android.server.notification.Flags.politeNotifications();
        return politeBit;
    }

    private boolean isInSoundTimeoutPeriod(com.android.server.notification.NotificationRecord notificationRecord) {
        java.lang.Long l;
        long notificationSoundTimeout = this.mNMP.getNotificationSoundTimeout(notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getUid());
        return (notificationSoundTimeout == 0 || (l = this.mLastSoundTimestamps.get(generateLastSoundTimeoutKey(notificationRecord))) == null || android.os.SystemClock.elapsedRealtime() - l.longValue() >= notificationSoundTimeout) ? false : true;
    }

    private java.lang.String generateLastSoundTimeoutKey(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.getSbn().getPackageName() + "|" + notificationRecord.getSbn().getUid();
    }

    private int getPoliteBit(com.android.server.notification.NotificationRecord notificationRecord) {
        switch (getPolitenessState(notificationRecord)) {
            case 1:
                return 8;
            case 2:
                return 16;
            default:
                return 0;
        }
    }

    private int getPolitenessState(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!isPoliteNotificationFeatureEnabled(notificationRecord)) {
            return 0;
        }
        return this.mStrategy.getPolitenessState(notificationRecord);
    }

    boolean isInsistentUpdate(com.android.server.notification.NotificationRecord notificationRecord) {
        return (java.util.Objects.equals(notificationRecord.getKey(), this.mSoundNotificationKey) || java.util.Objects.equals(notificationRecord.getKey(), this.mVibrateNotificationKey)) && isCurrentlyInsistent();
    }

    boolean isCurrentlyInsistent() {
        return isLoopingRingtoneNotification(this.mNMP.getNotificationByKey(this.mSoundNotificationKey)) || isLoopingRingtoneNotification(this.mNMP.getNotificationByKey(this.mVibrateNotificationKey));
    }

    boolean shouldMuteNotificationLocked(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationAttentionHelper.Signals signals) {
        android.app.Notification notification = notificationRecord.getNotification();
        if ((notificationRecord.isUpdate && (notification.flags & 8) != 0) || notificationRecord.shouldPostSilently()) {
            return true;
        }
        java.lang.String disableNotificationEffects = disableNotificationEffects(notificationRecord, signals.listenerHints);
        if (disableNotificationEffects != null) {
            com.android.server.notification.ZenLog.traceDisableEffects(notificationRecord, disableNotificationEffects);
            return true;
        }
        if (notificationRecord.isIntercepted()) {
            return true;
        }
        if (notificationRecord.getSbn().isGroup() && notification.suppressAlertingDueToGrouping()) {
            return true;
        }
        if (this.mUsageStats.isAlertRateLimited(notificationRecord.getSbn().getPackageName())) {
            android.util.Slog.e(TAG, "Muting recently noisy " + notificationRecord.getKey());
            return true;
        }
        if (!isCurrentlyInsistent() || isInsistentUpdate(notificationRecord)) {
            return notificationRecord.isUpdate && !notificationRecord.isInterruptive() && (notificationRecord.canBubble() && (notificationRecord.isFlagBubbleRemoved() || notificationRecord.getNotification().isBubbleNotification())) && notificationRecord.getNotification().getBubbleMetadata() != null && notificationRecord.getNotification().getBubbleMetadata().isNotificationSuppressed();
        }
        return true;
    }

    private boolean isLoopingRingtoneNotification(com.android.server.notification.NotificationRecord notificationRecord) {
        if (notificationRecord != null && notificationRecord.getAudioAttributes().getUsage() == 6 && (notificationRecord.getNotification().flags & 4) != 0) {
            return true;
        }
        return false;
    }

    private boolean playSound(com.android.server.notification.NotificationRecord notificationRecord, android.net.Uri uri) {
        if (!(android.media.audio.Flags.focusExclusiveWithRecording() ? this.mAudioManager.shouldNotificationSoundPlay(notificationRecord.getAudioAttributes()) : (this.mAudioManager.isAudioFocusExclusive() || this.mAudioManager.getStreamVolume(android.media.AudioAttributes.toLegacyStreamType(notificationRecord.getAudioAttributes())) == 0) ? false : true)) {
            if (DEBUG) {
                android.util.Slog.v(TAG, "Not playing sound " + uri + " due to focus/volume");
            }
            return false;
        }
        boolean z = (notificationRecord.getNotification().flags & 4) != 0;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                android.media.IRingtonePlayer ringtonePlayer = this.mAudioManager.getRingtonePlayer();
                if (ringtonePlayer != null) {
                    if (DEBUG) {
                        android.util.Slog.v(TAG, "Playing sound " + uri + " with attributes " + notificationRecord.getAudioAttributes());
                    }
                    ringtonePlayer.playAsync(uri, notificationRecord.getSbn().getUser(), z, notificationRecord.getAudioAttributes(), getSoundVolume(notificationRecord));
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed playSound: " + e);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean isPoliteNotificationFeatureEnabled(com.android.server.notification.NotificationRecord notificationRecord) {
        com.android.server.notification.Flags.politeNotifications();
        return false;
    }

    private float getSoundVolume(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!isPoliteNotificationFeatureEnabled(notificationRecord)) {
            return 1.0f;
        }
        return this.mStrategy.getSoundVolume(notificationRecord);
    }

    private float getVibrationIntensity(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!isPoliteNotificationFeatureEnabled(notificationRecord)) {
            return 1.0f;
        }
        return this.mStrategy.getVibrationIntensity(notificationRecord);
    }

    private boolean playVibration(final com.android.server.notification.NotificationRecord notificationRecord, final android.os.VibrationEffect vibrationEffect, boolean z) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            float vibrationIntensity = getVibrationIntensity(notificationRecord);
            if (java.lang.Float.compare(vibrationIntensity, 1.0f) != 0) {
                vibrationEffect = this.mVibratorHelper.scale(vibrationEffect, vibrationIntensity);
            }
            if (z) {
                new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.notification.NotificationAttentionHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.notification.NotificationAttentionHelper.this.lambda$playVibration$0(notificationRecord, vibrationEffect);
                    }
                }).start();
            } else {
                vibrate(notificationRecord, vibrationEffect, false);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playVibration$0(com.android.server.notification.NotificationRecord notificationRecord, android.os.VibrationEffect vibrationEffect) {
        int focusRampTimeMs = this.mAudioManager.getFocusRampTimeMs(3, notificationRecord.getAudioAttributes());
        if (DEBUG) {
            android.util.Slog.v(TAG, "Delaying vibration for notification " + notificationRecord.getKey() + " by " + focusRampTimeMs + "ms");
        }
        try {
            java.lang.Thread.sleep(focusRampTimeMs);
        } catch (java.lang.InterruptedException e) {
        }
        if (this.mNMP.getNotificationByKey(notificationRecord.getKey()) != null) {
            if (notificationRecord.getKey().equals(this.mVibrateNotificationKey)) {
                vibrate(notificationRecord, vibrationEffect, true);
                return;
            }
            if (DEBUG) {
                android.util.Slog.v(TAG, "No vibration for notification " + notificationRecord.getKey() + ": a new notification is vibrating, or effects were cleared while waiting");
                return;
            }
            return;
        }
        android.util.Slog.w(TAG, "No vibration for canceled notification " + notificationRecord.getKey());
    }

    private void vibrate(com.android.server.notification.NotificationRecord notificationRecord, android.os.VibrationEffect vibrationEffect, boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Notification (");
        sb.append(notificationRecord.getSbn().getOpPkg());
        sb.append(" ");
        sb.append(notificationRecord.getSbn().getUid());
        sb.append(") ");
        sb.append(z ? "(Delayed)" : "");
        this.mVibratorHelper.vibrate(vibrationEffect, notificationRecord.getAudioAttributes(), sb.toString());
    }

    void playInCallNotification() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mAudioManager.getRingerModeInternal() == 2 && android.provider.Settings.Secure.getIntForUser(contentResolver, "in_call_notification_enabled", 1, contentResolver.getUserId()) != 0) {
            new java.lang.Thread() { // from class: com.android.server.notification.NotificationAttentionHelper.4
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        try {
                            android.media.IRingtonePlayer ringtonePlayer = com.android.server.notification.NotificationAttentionHelper.this.mAudioManager.getRingtonePlayer();
                            if (ringtonePlayer != null) {
                                if (com.android.server.notification.NotificationAttentionHelper.this.mCallNotificationToken != null) {
                                    ringtonePlayer.stop(com.android.server.notification.NotificationAttentionHelper.this.mCallNotificationToken);
                                }
                                com.android.server.notification.NotificationAttentionHelper.this.mCallNotificationToken = new android.os.Binder();
                                ringtonePlayer.play(com.android.server.notification.NotificationAttentionHelper.this.mCallNotificationToken, com.android.server.notification.NotificationAttentionHelper.this.mInCallNotificationUri, com.android.server.notification.NotificationAttentionHelper.this.mInCallNotificationAudioAttributes, com.android.server.notification.NotificationAttentionHelper.this.mInCallNotificationVolume, false);
                            }
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(com.android.server.notification.NotificationAttentionHelper.TAG, "Failed playInCallNotification: " + e);
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            }.start();
        }
    }

    void clearSoundLocked() {
        this.mSoundNotificationKey = null;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                android.media.IRingtonePlayer ringtonePlayer = this.mAudioManager.getRingtonePlayer();
                if (ringtonePlayer != null) {
                    ringtonePlayer.stopAsync();
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed clearSoundLocked: " + e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void clearVibrateLocked() {
        this.mVibrateNotificationKey = null;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mVibratorHelper.cancelVibration();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void clearLightsLocked() {
        if (!this.mLineageNotificationLights.isKeyguardLocked()) {
            this.mLights.clear();
            updateLightsLocked();
        }
    }

    public void clearEffectsLocked(java.lang.String str) {
        if (str.equals(this.mSoundNotificationKey)) {
            clearSoundLocked();
        }
        if (str.equals(this.mVibrateNotificationKey)) {
            clearVibrateLocked();
        }
        if (this.mLights.remove(str)) {
            updateLightsLocked();
        }
    }

    public void clearAttentionEffects() {
        clearSoundLocked();
        clearVibrateLocked();
        clearLightsLocked();
    }

    void updateLightsLocked() {
        if (this.mNotificationLight == null) {
            return;
        }
        com.android.server.notification.NotificationRecord.Light light = null;
        com.android.server.notification.NotificationRecord notificationRecord = null;
        while (notificationRecord == null && !this.mLights.isEmpty()) {
            java.lang.String str = this.mLights.get(this.mLights.size() - 1);
            com.android.server.notification.NotificationRecord notificationByKey = this.mNMP.getNotificationByKey(str);
            if (notificationByKey == null) {
                android.util.Slog.wtfStack(TAG, "LED Notification does not exist: " + str);
                this.mLights.remove(str);
            }
            notificationRecord = notificationByKey;
        }
        if (notificationRecord != null) {
            light = notificationRecord.getLight();
        }
        if (notificationRecord == null || this.mLineageNotificationLights == null || light == null) {
            this.mNotificationLight.turnOff();
            return;
        }
        int i = light.color;
        if (isLedForcedOn(notificationRecord) && i == 0) {
            i = 16777216;
        }
        org.lineageos.internal.notification.LedValues ledValues = new org.lineageos.internal.notification.LedValues(i, light.onMs, light.offMs);
        this.mLineageNotificationLights.calcLights(ledValues, notificationRecord.getSbn().getPackageName(), notificationRecord.getSbn().getNotification(), this.mScreenOn || isInCall(), notificationRecord.getSuppressedVisualEffects());
        if (!ledValues.isEnabled()) {
            this.mNotificationLight.turnOff();
            return;
        }
        this.mNotificationLight.setModes(ledValues.getBrightness());
        if (ledValues.getOnMs() == 1 && ledValues.getOffMs() == 0) {
            this.mNotificationLight.setColor(ledValues.getColor());
        } else {
            this.mNotificationLight.setFlashing(ledValues.getColor(), 1, ledValues.getOnMs(), ledValues.getOffMs());
        }
    }

    private boolean isLedForcedOn(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord != null && this.mLineageNotificationLights.isForcedOn(notificationRecord.getSbn().getNotification());
    }

    boolean canShowLightsLocked(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationAttentionHelper.Signals signals, boolean z) {
        if (!this.mHasLight) {
            return false;
        }
        if (isLedForcedOn(notificationRecord)) {
            return true;
        }
        if (!this.mNotificationPulseEnabled || notificationRecord.getLight() == null || !z) {
            return false;
        }
        android.app.Notification notification = notificationRecord.getNotification();
        if (!notificationRecord.isUpdate || (notification.flags & 8) == 0) {
            return !(notificationRecord.getSbn().isGroup() && notificationRecord.getNotification().suppressAlertingDueToGrouping()) && isNotificationForCurrentUser(notificationRecord, signals);
        }
        return false;
    }

    private java.lang.String disableNotificationEffects(com.android.server.notification.NotificationRecord notificationRecord, int i) {
        if (this.mDisableNotificationEffects) {
            return "booleanState";
        }
        if ((i & 1) != 0) {
            return "listenerHints";
        }
        if (notificationRecord != null && notificationRecord.getAudioAttributes() != null) {
            if ((i & 2) != 0 && notificationRecord.getAudioAttributes().getUsage() != 6) {
                return "listenerNoti";
            }
            if ((i & 4) != 0 && notificationRecord.getAudioAttributes().getUsage() == 6) {
                return "listenerCall";
            }
        }
        if (this.mCallState != 0 && !this.mZenModeHelper.isCall(notificationRecord)) {
            return "callState";
        }
        return null;
    }

    public void updateDisableNotificationEffectsLocked(int i) {
        this.mDisableNotificationEffects = (i & 262144) != 0;
        if (this.mDisableNotificationEffects) {
            clearAttentionEffects();
        }
    }

    private boolean isInCall() {
        int mode;
        return this.mInCallStateOffHook || (mode = this.mAudioManager.getMode()) == 2 || mode == 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String callStateToString(int i) {
        switch (i) {
            case 0:
                return "CALL_STATE_IDLE";
            case 1:
                return "CALL_STATE_RINGING";
            case 2:
                return "CALL_STATE_OFFHOOK";
            default:
                return "CALL_STATE_UNKNOWN_" + i;
        }
    }

    private boolean isNotificationForCurrentUser(com.android.server.notification.NotificationRecord notificationRecord, com.android.server.notification.NotificationAttentionHelper.Signals signals) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int currentUser = android.app.ActivityManager.getCurrentUser();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return notificationRecord.getUserId() == -1 || notificationRecord.getUserId() == currentUser || signals.isCurrentProfile;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean isNotificationForWorkProfile(com.android.server.notification.NotificationRecord notificationRecord) {
        return notificationRecord.getUser().getIdentifier() == this.mCurrentWorkProfileId && this.mCurrentWorkProfileId != -10000;
    }

    private int getManagedProfileId(int i) {
        for (android.content.pm.UserInfo userInfo : this.mUm.getProfiles(i)) {
            if (userInfo.isManagedProfile() && userInfo.getUserHandle().getIdentifier() != i) {
                return userInfo.getUserHandle().getIdentifier();
            }
        }
        return com.android.server.am.ProcessList.INVALID_ADJ;
    }

    void sendAccessibilityEvent(com.android.server.notification.NotificationRecord notificationRecord) {
        if (!this.mAccessibilityManager.isEnabled()) {
            return;
        }
        android.app.Notification notification = notificationRecord.getNotification();
        java.lang.String packageName = notificationRecord.getSbn().getPackageName();
        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(64);
        obtain.setPackageName(packageName);
        obtain.setClassName(android.app.Notification.class.getName());
        int packageVisibilityOverride = notificationRecord.getPackageVisibilityOverride();
        if (packageVisibilityOverride == -1000) {
            packageVisibilityOverride = notification.visibility;
        }
        int identifier = notificationRecord.getUser().getIdentifier();
        if ((identifier >= 0 && this.mKeyguardManager.isDeviceLocked(identifier)) && packageVisibilityOverride != 1) {
            obtain.setParcelableData(notification.publicVersion);
        } else {
            obtain.setParcelableData(notification);
        }
        java.lang.CharSequence charSequence = notification.tickerText;
        if (!android.text.TextUtils.isEmpty(charSequence)) {
            obtain.getText().add(charSequence);
        }
        this.mAccessibilityManager.sendAccessibilityEvent(obtain);
    }

    public void onUserInteraction(com.android.server.notification.NotificationRecord notificationRecord) {
        if (isPoliteNotificationFeatureEnabled(notificationRecord)) {
            this.mStrategy.onUserInteraction(notificationRecord);
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        printWriter.println("\n  Notification attention state:");
        printWriter.print(str);
        printWriter.println("  mSoundNotificationKey=" + this.mSoundNotificationKey);
        printWriter.print(str);
        printWriter.println("  mVibrateNotificationKey=" + this.mVibrateNotificationKey);
        printWriter.print(str);
        printWriter.println("  mDisableNotificationEffects=" + this.mDisableNotificationEffects);
        printWriter.print(str);
        printWriter.println("  mCallState=" + callStateToString(this.mCallState));
        printWriter.print(str);
        printWriter.println("  mSystemReady=" + this.mSystemReady);
        printWriter.print(str);
        printWriter.println("  mNotificationPulseEnabled=" + this.mNotificationPulseEnabled);
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        printWriter.println("\n  Last notification sound timestamps:");
        for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : this.mLastSoundTimestamps.entrySet()) {
            printWriter.print("    " + entry.getKey() + " -> ");
            android.util.TimeUtils.formatDuration(entry.getValue().longValue(), elapsedRealtime, printWriter);
            printWriter.println(" ago");
        }
        int size = this.mLights.size();
        if (size > 0) {
            printWriter.print(str);
            printWriter.println("  Lights List:");
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    printWriter.print("  > ");
                } else {
                    printWriter.print("    ");
                }
                printWriter.println(this.mLights.get(i));
            }
            printWriter.println("  ");
        }
    }

    public static class Signals {
        private final boolean isCurrentProfile;
        private final int listenerHints;

        public Signals(boolean z, int i) {
            this.isCurrentProfile = z;
            this.listenerHints = i;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static abstract class PolitenessStrategy {
        static final int POLITE_STATE_DEFAULT = 0;
        static final int POLITE_STATE_MUTED = 2;
        static final int POLITE_STATE_POLITE = 1;
        protected boolean mApplyPerPackage;
        protected final int mTimeoutMuted;
        protected final int mTimeoutPolite;
        protected final float mVolumeMuted;
        protected final float mVolumePolite;
        protected boolean mIsActive = true;
        protected final java.util.Map<java.lang.String, java.lang.Integer> mVolumeStates = new java.util.HashMap();
        protected final java.util.Map<java.lang.String, java.lang.Long> mLastUpdatedTimestampByPackage = new java.util.HashMap();

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface PolitenessState {
        }

        abstract void onNotificationPosted(com.android.server.notification.NotificationRecord notificationRecord);

        public PolitenessStrategy(int i, int i2, int i3, int i4) {
            this.mTimeoutPolite = i;
            this.mTimeoutMuted = i2;
            this.mVolumePolite = i3 / 100.0f;
            this.mVolumeMuted = i4 / 100.0f;
        }

        void setApplyCooldownPerPackage(boolean z) {
            this.mApplyPerPackage = z;
        }

        boolean shouldIgnoreNotification(com.android.server.notification.NotificationRecord notificationRecord) {
            return notificationRecord.getSbn().isGroup() && notificationRecord.getSbn().getNotification().isGroupSummary();
        }

        java.lang.String getChannelKey(com.android.server.notification.NotificationRecord notificationRecord) {
            java.lang.String conversationId = notificationRecord.getChannel().getConversationId() != null ? notificationRecord.getChannel().getConversationId() : notificationRecord.getChannel().getId();
            if (this.mApplyPerPackage && !notificationRecord.getChannel().hasUserSetSound()) {
                conversationId = "";
            }
            return notificationRecord.getSbn().getNormalizedUserId() + ":" + notificationRecord.getSbn().getPackageName() + ":" + conversationId;
        }

        public float getSoundVolume(com.android.server.notification.NotificationRecord notificationRecord) {
            java.lang.String channelKey = getChannelKey(notificationRecord);
            int politenessState = getPolitenessState(notificationRecord);
            float f = 1.0f;
            switch (politenessState) {
                case 0:
                    break;
                case 1:
                    f = this.mVolumePolite;
                    break;
                case 2:
                    f = this.mVolumeMuted;
                    break;
                default:
                    android.util.Log.w(com.android.server.notification.NotificationAttentionHelper.TAG, "getSoundVolume unexpected volume state: " + politenessState);
                    break;
            }
            if (com.android.server.notification.NotificationAttentionHelper.DEBUG) {
                android.util.Log.i(com.android.server.notification.NotificationAttentionHelper.TAG, "getSoundVolume state: " + politenessState + " vol: " + f + " key: " + channelKey);
            }
            return f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getVibrationIntensity(com.android.server.notification.NotificationRecord notificationRecord) {
            return getSoundVolume(notificationRecord);
        }

        public void onUserInteraction(com.android.server.notification.NotificationRecord notificationRecord) {
            this.mVolumeStates.put(getChannelKey(notificationRecord), 0);
            setLastNotificationUpdateTimeMs(notificationRecord, 0L);
        }

        public final int getPolitenessState(com.android.server.notification.NotificationRecord notificationRecord) {
            return this.mVolumeStates.getOrDefault(getChannelKey(notificationRecord), 0).intValue();
        }

        void setLastNotificationUpdateTimeMs(com.android.server.notification.NotificationRecord notificationRecord, long j) {
            notificationRecord.getChannel().setLastNotificationUpdateTimeMs(j);
            this.mLastUpdatedTimestampByPackage.put(notificationRecord.getSbn().getPackageName(), java.lang.Long.valueOf(j));
        }

        long getLastNotificationUpdateTimeMs(com.android.server.notification.NotificationRecord notificationRecord) {
            if (notificationRecord.getChannel().hasUserSetSound() || !this.mApplyPerPackage) {
                return notificationRecord.getChannel().getLastNotificationUpdateTimeMs();
            }
            return this.mLastUpdatedTimestampByPackage.getOrDefault(notificationRecord.getSbn().getPackageName(), 0L).longValue();
        }

        int getNextState(int i, long j) {
            switch (i) {
                case 0:
                    if (j < this.mTimeoutPolite) {
                        return 1;
                    }
                    return i;
                case 1:
                    if (j < this.mTimeoutMuted) {
                        return 2;
                    }
                    return j > ((long) this.mTimeoutPolite) ? 0 : 1;
                case 2:
                    return j > ((long) this.mTimeoutMuted) ? 1 : 2;
                default:
                    android.util.Log.w(com.android.server.notification.NotificationAttentionHelper.TAG, "getNextState unexpected volume state: " + i);
                    return i;
            }
        }

        boolean isActive() {
            return this.mIsActive;
        }
    }

    private static class StrategyPerApp extends com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy {
        private final int mMaxPostedForReset;
        private final java.util.Map<java.lang.String, java.lang.Integer> mNumPosted;

        public StrategyPerApp(int i, int i2, int i3, int i4, int i5) {
            super(i, i2, i3, i4);
            this.mNumPosted = new java.util.HashMap();
            this.mMaxPostedForReset = i5;
            if (com.android.server.notification.NotificationAttentionHelper.DEBUG) {
                android.util.Log.i(com.android.server.notification.NotificationAttentionHelper.TAG, "StrategyPerApp: " + i + " " + i2);
            }
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        public void onNotificationPosted(com.android.server.notification.NotificationRecord notificationRecord) {
            if (shouldIgnoreNotification(notificationRecord)) {
                return;
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis() - getLastNotificationUpdateTimeMs(notificationRecord);
            java.lang.String channelKey = getChannelKey(notificationRecord);
            int politenessState = getPolitenessState(notificationRecord);
            int nextState = getNextState(politenessState, currentTimeMillis);
            int intValue = this.mNumPosted.getOrDefault(channelKey, 0).intValue() + 1;
            this.mNumPosted.put(channelKey, java.lang.Integer.valueOf(intValue));
            if (politenessState == 2 && intValue >= this.mMaxPostedForReset) {
                this.mNumPosted.put(channelKey, 0);
                nextState = 0;
            }
            if (com.android.server.notification.NotificationAttentionHelper.DEBUG) {
                android.util.Log.i(com.android.server.notification.NotificationAttentionHelper.TAG, "onNotificationPosted time delta: " + currentTimeMillis + " vol state: " + nextState + " key: " + channelKey + " numposted " + intValue);
            }
            this.mVolumeStates.put(channelKey, java.lang.Integer.valueOf(nextState));
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        public void onUserInteraction(com.android.server.notification.NotificationRecord notificationRecord) {
            super.onUserInteraction(notificationRecord);
            this.mNumPosted.put(getChannelKey(notificationRecord), 0);
        }
    }

    private static class StrategyAvalanche extends com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy {
        private static final java.lang.String COMMON_KEY = "cross_app_common_key";
        private final com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy mAppStrategy;
        private long mLastAvalancheTriggerTimestamp;
        private long mLastNotificationTimestamp;
        private final int mTimeoutAvalanche;

        StrategyAvalanche(int i, int i2, int i3, int i4, int i5, com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy politenessStrategy) {
            super(i, i2, i3, i4);
            this.mLastNotificationTimestamp = 0L;
            this.mLastAvalancheTriggerTimestamp = 0L;
            this.mTimeoutAvalanche = i5;
            this.mAppStrategy = politenessStrategy;
            if (com.android.server.notification.NotificationAttentionHelper.DEBUG) {
                android.util.Log.i(com.android.server.notification.NotificationAttentionHelper.TAG, "StrategyAvalanche: " + i + " " + i2 + " " + i5);
            }
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        void onNotificationPosted(com.android.server.notification.NotificationRecord notificationRecord) {
            if (isAvalancheActive()) {
                if (shouldIgnoreNotification(notificationRecord)) {
                    return;
                }
                long currentTimeMillis = java.lang.System.currentTimeMillis() - getLastNotificationUpdateTimeMs(notificationRecord);
                java.lang.String channelKey = getChannelKey(notificationRecord);
                int nextState = getNextState(getPolitenessState(notificationRecord), currentTimeMillis);
                if (com.android.server.notification.NotificationAttentionHelper.DEBUG) {
                    android.util.Log.i(com.android.server.notification.NotificationAttentionHelper.TAG, "StrategyAvalanche onNotificationPosted time delta: " + currentTimeMillis + " vol state: " + nextState + " key: " + channelKey);
                }
                this.mVolumeStates.put(channelKey, java.lang.Integer.valueOf(nextState));
            }
            this.mAppStrategy.onNotificationPosted(notificationRecord);
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        public float getSoundVolume(com.android.server.notification.NotificationRecord notificationRecord) {
            if (isAvalancheActive()) {
                return super.getSoundVolume(notificationRecord);
            }
            return this.mAppStrategy.getSoundVolume(notificationRecord);
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        public void onUserInteraction(com.android.server.notification.NotificationRecord notificationRecord) {
            super.onUserInteraction(notificationRecord);
            this.mAppStrategy.onUserInteraction(notificationRecord);
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        java.lang.String getChannelKey(com.android.server.notification.NotificationRecord notificationRecord) {
            if (notificationRecord.getChannel().hasUserSetSound()) {
                return super.getChannelKey(notificationRecord);
            }
            return notificationRecord.getSbn().getNormalizedUserId() + ":" + COMMON_KEY;
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        public void setLastNotificationUpdateTimeMs(com.android.server.notification.NotificationRecord notificationRecord, long j) {
            super.setLastNotificationUpdateTimeMs(notificationRecord, j);
            this.mLastNotificationTimestamp = j;
            this.mAppStrategy.setLastNotificationUpdateTimeMs(notificationRecord, j);
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        long getLastNotificationUpdateTimeMs(com.android.server.notification.NotificationRecord notificationRecord) {
            if (notificationRecord.getChannel().hasUserSetSound()) {
                return super.getLastNotificationUpdateTimeMs(notificationRecord);
            }
            return this.mLastNotificationTimestamp;
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        void setApplyCooldownPerPackage(boolean z) {
            super.setApplyCooldownPerPackage(z);
            this.mAppStrategy.setApplyCooldownPerPackage(z);
        }

        boolean isAvalancheActive() {
            this.mIsActive = java.lang.System.currentTimeMillis() - this.mLastAvalancheTriggerTimestamp < ((long) this.mTimeoutAvalanche);
            if (com.android.server.notification.NotificationAttentionHelper.DEBUG) {
                android.util.Log.i(com.android.server.notification.NotificationAttentionHelper.TAG, "StrategyAvalanche: active " + this.mIsActive);
            }
            return this.mIsActive;
        }

        @Override // com.android.server.notification.NotificationAttentionHelper.PolitenessStrategy
        boolean isActive() {
            return isAvalancheActive();
        }

        void setTriggerTimeMs(long j) {
            this.mLastAvalancheTriggerTimestamp = j;
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        private static final android.net.Uri NOTIFICATION_LIGHT_PULSE_URI = android.provider.Settings.System.getUriFor("notification_light_pulse");
        private static final android.net.Uri NOTIFICATION_COOLDOWN_ENABLED_URI = android.provider.Settings.System.getUriFor("notification_cooldown_enabled");
        private static final android.net.Uri NOTIFICATION_COOLDOWN_ALL_URI = android.provider.Settings.System.getUriFor("notification_cooldown_all");
        private static final android.net.Uri NOTIFICATION_COOLDOWN_VIBRATE_UNLOCKED_URI = android.provider.Settings.System.getUriFor("notification_cooldown_vibrate_unlocked");

        public SettingsObserver() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (uri == null || NOTIFICATION_LIGHT_PULSE_URI.equals(uri)) {
                boolean z2 = android.provider.Settings.System.getIntForUser(com.android.server.notification.NotificationAttentionHelper.this.mContext.getContentResolver(), "notification_light_pulse", 0, -2) != 0;
                if (com.android.server.notification.NotificationAttentionHelper.this.mNotificationPulseEnabled != z2) {
                    com.android.server.notification.NotificationAttentionHelper.this.mNotificationPulseEnabled = z2;
                    com.android.server.notification.NotificationAttentionHelper.this.updateLightsLocked();
                }
            }
            com.android.server.notification.Flags.politeNotifications();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setIsAutomotive(boolean z) {
        this.mIsAutomotive = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setNotificationEffectsEnabledForAutomotive(boolean z) {
        this.mNotificationEffectsEnabledForAutomotive = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setSystemReady(boolean z) {
        this.mSystemReady = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setKeyguardManager(android.app.KeyguardManager keyguardManager) {
        this.mKeyguardManager = keyguardManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAccessibilityManager(android.view.accessibility.AccessibilityManager accessibilityManager) {
        this.mAccessibilityManager = accessibilityManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.notification.VibratorHelper getVibratorHelper() {
        return this.mVibratorHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setVibratorHelper(com.android.server.notification.VibratorHelper vibratorHelper) {
        this.mVibratorHelper = vibratorHelper;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setScreenOn(boolean z) {
        this.mScreenOn = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setUserPresent(boolean z) {
        this.mUserPresent = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setLights(com.android.server.lights.LogicalLight logicalLight) {
        this.mNotificationLight = logicalLight;
        this.mAttentionLight = logicalLight;
        this.mNotificationPulseEnabled = true;
        this.mHasLight = true;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAudioManager(android.media.AudioManager audioManager) {
        this.mAudioManager = audioManager;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setInCallStateOffHook(boolean z) {
        this.mInCallStateOffHook = z;
    }
}
