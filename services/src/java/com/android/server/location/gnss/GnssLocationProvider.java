package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssLocationProvider extends com.android.server.location.provider.AbstractLocationProvider implements com.android.server.location.gnss.NetworkTimeHelper.InjectTimeCallback, com.android.server.location.gnss.GnssSatelliteBlocklistHelper.GnssSatelliteBlocklistCallback, com.android.server.location.gnss.hal.GnssNative.BaseCallbacks, com.android.server.location.gnss.hal.GnssNative.LocationCallbacks, com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks, com.android.server.location.gnss.hal.GnssNative.AGpsCallbacks, com.android.server.location.gnss.hal.GnssNative.PsdsCallbacks, com.android.server.location.gnss.hal.GnssNative.NotificationCallbacks, com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks, com.android.server.location.gnss.hal.GnssNative.TimeCallbacks {
    private static final int AGPS_SUPL_MODE_MSA = 2;
    private static final int AGPS_SUPL_MODE_MSB = 1;
    private static final long DOWNLOAD_PSDS_DATA_TIMEOUT_MS = 60000;
    private static final int EMERGENCY_LOCATION_UPDATE_DURATION_MULTIPLIER = 3;
    private static final int GPS_POLLING_THRESHOLD_INTERVAL = 10000;
    private static final long LOCATION_OFF_DELAY_THRESHOLD_ERROR_MILLIS = 15000;
    private static final long LOCATION_OFF_DELAY_THRESHOLD_WARN_MILLIS = 2000;
    private static final long LOCATION_UPDATE_DURATION_MILLIS = 10000;
    private static final long LOCATION_UPDATE_MIN_TIME_INTERVAL_MILLIS = 1000;
    private static final long MAX_BATCH_LENGTH_MS = 86400000;
    private static final long MAX_BATCH_TIMESTAMP_DELTA_MS = 500;
    private static final long MAX_RETRY_INTERVAL = 14400000;
    private static final int MIN_BATCH_INTERVAL_MS = 1000;
    private static final int NO_FIX_TIMEOUT = 60000;
    private static final long RETRY_INTERVAL = 300000;
    private static final int TCP_MAX_PORT = 65535;
    private static final int TCP_MIN_PORT = 0;
    private static final long WAKELOCK_TIMEOUT_MILLIS = 30000;
    private final android.app.AlarmManager mAlarmManager;
    private final android.app.AppOpsManager mAppOps;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mAutomotiveSuspend;
    private android.app.AlarmManager.OnAlarmListener mBatchingAlarm;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mBatchingEnabled;
    private boolean mBatchingStarted;
    private final com.android.internal.app.IBatteryStats mBatteryStats;
    private java.lang.String mC2KServerHost;
    private int mC2KServerPort;
    private final android.os.WorkSource mClientSource;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.Integer> mDownloadInProgressPsdsTypes;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.os.PowerManager.WakeLock mDownloadPsdsWakeLock;
    private int mFixInterval;
    private long mFixRequestTime;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<java.lang.Runnable> mFlushListeners;
    private final com.android.server.location.gnss.GnssConfiguration mGnssConfiguration;
    private final com.android.server.location.gnss.GnssMetrics mGnssMetrics;
    private final com.android.server.location.gnss.hal.GnssNative mGnssNative;
    private final com.android.server.location.gnss.GnssSatelliteBlocklistHelper mGnssSatelliteBlocklistHelper;
    private com.android.server.location.gnss.GnssVisibilityControl mGnssVisibilityControl;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mGpsEnabled;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mInitialized;
    private android.content.BroadcastReceiver mIntentReceiver;
    private long mLastFixTime;
    private com.android.server.location.gnss.GnssPositionMode mLastPositionMode;
    private final com.android.server.location.gnss.GnssLocationProvider.LocationExtras mLocationExtras;
    private final java.lang.Object mLock;
    private final com.android.internal.location.GpsNetInitiatedHandler mNIHandler;
    private final com.android.server.location.gnss.GnssNetworkConnectivityHandler mNetworkConnectivityHandler;
    private final com.android.server.location.gnss.NetworkTimeHelper mNetworkTimeHelper;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.Integer> mPendingDownloadPsdsTypes;
    private int mPositionMode;
    private android.location.provider.ProviderRequest mProviderRequest;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.location.gnss.ExponentialBackOff mPsdsBackOff;
    private final java.lang.Object mPsdsPeriodicDownloadToken;
    private boolean mShutdown;
    private boolean mStarted;
    private long mStartedChangedElapsedRealtime;
    private boolean mSuplEsEnabled;
    private java.lang.String mSuplServerHost;
    private int mSuplServerPort;
    private boolean mSupportsPsds;
    private int mTimeToFirstFix;
    private final android.app.AlarmManager.OnAlarmListener mTimeoutListener;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private final android.app.AlarmManager.OnAlarmListener mWakeupListener;
    private static final java.lang.String TAG = "GnssLocationProvider";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final boolean VERBOSE = android.util.Log.isLoggable(TAG, 2);
    private static final android.location.provider.ProviderProperties PROPERTIES = new android.location.provider.ProviderProperties.Builder().setHasSatelliteRequirement(true).setHasAltitudeSupport(true).setHasSpeedSupport(true).setHasBearingSupport(true).setPowerUsage(3).setAccuracy(1).build();

    private static class LocationExtras {
        private final android.os.Bundle mBundle = new android.os.Bundle();
        private int mMaxCn0;
        private int mMeanCn0;
        private int mSvCount;

        LocationExtras() {
        }

        public void set(int i, int i2, int i3) {
            synchronized (this) {
                this.mSvCount = i;
                this.mMeanCn0 = i2;
                this.mMaxCn0 = i3;
            }
            setBundle(this.mBundle);
        }

        public void reset() {
            set(0, 0, 0);
        }

        public void setBundle(android.os.Bundle bundle) {
            if (bundle != null) {
                synchronized (this) {
                    bundle.putInt("satellites", this.mSvCount);
                    bundle.putInt("meanCn0", this.mMeanCn0);
                    bundle.putInt("maxCn0", this.mMaxCn0);
                }
            }
        }

        public android.os.Bundle getBundle() {
            android.os.Bundle bundle;
            synchronized (this) {
                bundle = new android.os.Bundle(this.mBundle);
            }
            return bundle;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUpdateSatelliteBlocklist$0(int[] iArr, int[] iArr2) {
        this.mGnssConfiguration.setSatelliteBlocklist(iArr, iArr2);
    }

    @Override // com.android.server.location.gnss.GnssSatelliteBlocklistHelper.GnssSatelliteBlocklistCallback
    public void onUpdateSatelliteBlocklist(final int[] iArr, final int[] iArr2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.lambda$onUpdateSatelliteBlocklist$0(iArr, iArr2);
            }
        });
        this.mGnssMetrics.resetConstellationTypes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void subscriptionOrCarrierConfigChanged() {
        boolean z;
        if (DEBUG) {
            android.util.Log.d(TAG, "received SIM related action: ");
        }
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE);
        android.telephony.CarrierConfigManager carrierConfigManager = (android.telephony.CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        int defaultDataSubscriptionId = android.telephony.SubscriptionManager.getDefaultDataSubscriptionId();
        if (android.telephony.SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            telephonyManager = telephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
        }
        java.lang.String simOperator = telephonyManager.getSimOperator();
        if (!android.text.TextUtils.isEmpty(simOperator)) {
            if (DEBUG) {
                android.util.Log.d(TAG, "SIM MCC/MNC is available: " + simOperator);
            }
            if (carrierConfigManager != null) {
                android.os.PersistableBundle configForSubId = android.telephony.SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId) ? carrierConfigManager.getConfigForSubId(defaultDataSubscriptionId) : null;
                if (configForSubId != null) {
                    z = configForSubId.getBoolean("gps.persist_lpp_mode_bool");
                    if (!z) {
                        this.mGnssConfiguration.loadPropertiesFromCarrierConfig(false, -1);
                        java.lang.String lppProfile = this.mGnssConfiguration.getLppProfile();
                        if (lppProfile != null) {
                            android.os.SystemProperties.set("persist.sys.gps.lpp", lppProfile);
                        }
                    } else {
                        android.os.SystemProperties.set("persist.sys.gps.lpp", "");
                    }
                    reloadGpsProperties();
                    return;
                }
            }
            z = false;
            if (!z) {
            }
            reloadGpsProperties();
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "SIM MCC/MNC is still not available");
        }
        this.mGnssConfiguration.reloadGpsProperties();
    }

    private void reloadGpsProperties() {
        this.mGnssConfiguration.reloadGpsProperties();
        setSuplHostPort();
        this.mC2KServerHost = this.mGnssConfiguration.getC2KHost();
        this.mC2KServerPort = this.mGnssConfiguration.getC2KPort(0);
        this.mNIHandler.setEmergencyExtensionSeconds(this.mGnssConfiguration.getEsExtensionSec());
        this.mSuplEsEnabled = this.mGnssConfiguration.getSuplEs(0) == 1;
        if (this.mGnssVisibilityControl != null) {
            this.mGnssVisibilityControl.onConfigurationUpdated(this.mGnssConfiguration);
        }
        toggleXtraDaemon();
    }

    public GnssLocationProvider(android.content.Context context, com.android.server.location.gnss.hal.GnssNative gnssNative, com.android.server.location.gnss.GnssMetrics gnssMetrics) {
        super(com.android.server.FgThread.getExecutor(), android.location.util.identity.CallerIdentity.fromContext(context), PROPERTIES, java.util.Collections.emptySet());
        this.mLock = new java.lang.Object();
        this.mPsdsBackOff = new com.android.server.location.gnss.ExponentialBackOff(300000L, 14400000L);
        this.mFixInterval = 1000;
        this.mFixRequestTime = 0L;
        this.mTimeToFirstFix = 0;
        this.mClientSource = new android.os.WorkSource();
        this.mPsdsPeriodicDownloadToken = new java.lang.Object();
        this.mPendingDownloadPsdsTypes = new java.util.HashSet();
        this.mDownloadInProgressPsdsTypes = new java.util.HashSet();
        this.mSuplServerPort = 0;
        this.mSuplEsEnabled = false;
        this.mLocationExtras = new com.android.server.location.gnss.GnssLocationProvider.LocationExtras();
        this.mWakeupListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda23
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.location.gnss.GnssLocationProvider.this.startNavigating();
            }
        };
        this.mTimeoutListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda24
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.location.gnss.GnssLocationProvider.this.hibernate();
            }
        };
        this.mFlushListeners = new java.util.ArrayList<>(0);
        this.mIntentReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.location.gnss.GnssLocationProvider.5
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                if (com.android.server.location.gnss.GnssLocationProvider.DEBUG) {
                    android.util.Log.d(com.android.server.location.gnss.GnssLocationProvider.TAG, "receive broadcast intent, action: " + action);
                }
                if (action == null) {
                }
                switch (action.hashCode()) {
                    case -1138588223:
                        if (action.equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -873963303:
                        if (action.equals("android.provider.Telephony.WAP_PUSH_RECEIVED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -25388475:
                        if (action.equals("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2142067319:
                        if (action.equals("android.intent.action.DATA_SMS_RECEIVED")) {
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
                    case 1:
                        com.android.server.location.gnss.GnssLocationProvider.this.subscriptionOrCarrierConfigChanged();
                        break;
                    case 2:
                    case 3:
                        com.android.server.location.gnss.GnssLocationProvider.this.injectSuplInit(intent);
                        break;
                }
            }
        };
        this.mContext = context;
        this.mGnssNative = gnssNative;
        this.mGnssMetrics = gnssMetrics;
        android.os.PowerManager powerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
        java.util.Objects.requireNonNull(powerManager);
        this.mWakeLock = powerManager.newWakeLock(1, "*location*:GnssLocationProvider");
        this.mWakeLock.setReferenceCounted(true);
        this.mDownloadPsdsWakeLock = powerManager.newWakeLock(1, "*location*:PsdsDownload");
        this.mDownloadPsdsWakeLock.setReferenceCounted(true);
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        this.mBatteryStats = com.android.internal.app.IBatteryStats.Stub.asInterface(android.os.ServiceManager.getService("batterystats"));
        this.mHandler = com.android.server.FgThread.getHandler();
        this.mGnssConfiguration = this.mGnssNative.getConfiguration();
        this.mNIHandler = new com.android.internal.location.GpsNetInitiatedHandler(context, new com.android.server.location.gnss.GnssLocationProvider.AnonymousClass1(), this.mSuplEsEnabled);
        this.mPendingDownloadPsdsTypes.add(1);
        this.mNetworkConnectivityHandler = new com.android.server.location.gnss.GnssNetworkConnectivityHandler(context, new com.android.server.location.gnss.GnssNetworkConnectivityHandler.GnssNetworkListener() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda25
            @Override // com.android.server.location.gnss.GnssNetworkConnectivityHandler.GnssNetworkListener
            public final void onNetworkAvailable() {
                com.android.server.location.gnss.GnssLocationProvider.this.onNetworkAvailable();
            }
        }, this.mHandler.getLooper(), this.mNIHandler);
        this.mNetworkTimeHelper = com.android.server.location.gnss.NetworkTimeHelper.create(this.mContext, this.mHandler.getLooper(), this);
        this.mGnssSatelliteBlocklistHelper = new com.android.server.location.gnss.GnssSatelliteBlocklistHelper(this.mContext, this.mHandler.getLooper(), this);
        setAllowed(true);
        this.mGnssNative.addBaseCallbacks(this);
        this.mGnssNative.addLocationCallbacks(this);
        this.mGnssNative.addSvStatusCallbacks(this);
        this.mGnssNative.setAGpsCallbacks(this);
        this.mGnssNative.setPsdsCallbacks(this);
        this.mGnssNative.setNotificationCallbacks(this);
        this.mGnssNative.setLocationRequestCallbacks(this);
        this.mGnssNative.setTimeCallbacks(this);
    }

    /* renamed from: com.android.server.location.gnss.GnssLocationProvider$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.internal.location.GpsNetInitiatedHandler.EmergencyCallCallback {
        AnonymousClass1() {
        }

        public void onEmergencyCallStart(final int i) {
            if (!com.android.server.location.gnss.GnssLocationProvider.this.mGnssConfiguration.isActiveSimEmergencySuplEnabled()) {
                return;
            }
            com.android.server.location.gnss.GnssLocationProvider.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssLocationProvider.AnonymousClass1.this.lambda$onEmergencyCallStart$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEmergencyCallStart$0(int i) {
            com.android.server.location.gnss.GnssLocationProvider.this.mGnssConfiguration.reloadGpsProperties(com.android.server.location.gnss.GnssLocationProvider.this.mNIHandler.getInEmergency(), i);
        }

        public void onEmergencyCallEnd() {
            if (!com.android.server.location.gnss.GnssLocationProvider.this.mGnssConfiguration.isActiveSimEmergencySuplEnabled()) {
                return;
            }
            com.android.server.location.gnss.GnssLocationProvider.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssLocationProvider.AnonymousClass1.this.lambda$onEmergencyCallEnd$1();
                }
            }, java.util.concurrent.TimeUnit.SECONDS.toMillis(com.android.server.location.gnss.GnssLocationProvider.this.mGnssConfiguration.getEsExtensionSec()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onEmergencyCallEnd$1() {
            com.android.server.location.gnss.GnssLocationProvider.this.mGnssConfiguration.reloadGpsProperties(false, android.telephony.SubscriptionManager.getDefaultDataSubscriptionId());
        }
    }

    public synchronized void onSystemReady() {
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.location.gnss.GnssLocationProvider.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (getSendingUserId() == -1) {
                    com.android.server.location.gnss.GnssLocationProvider.this.mShutdown = true;
                    com.android.server.location.gnss.GnssLocationProvider.this.updateEnabled();
                }
            }
        }, android.os.UserHandle.ALL, new android.content.IntentFilter("android.intent.action.ACTION_SHUTDOWN"), null, this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("location_mode"), true, new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.location.gnss.GnssLocationProvider.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.location.gnss.GnssLocationProvider.this.updateEnabled();
            }
        }, -1);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("assisted_gps_enabled"), false, new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.location.gnss.GnssLocationProvider.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.location.gnss.GnssLocationProvider.this.toggleXtraDaemon();
            }
        }, -1);
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.handleInitialize();
            }
        });
        android.os.Handler handler = this.mHandler;
        final com.android.server.location.gnss.GnssSatelliteBlocklistHelper gnssSatelliteBlocklistHelper = this.mGnssSatelliteBlocklistHelper;
        java.util.Objects.requireNonNull(gnssSatelliteBlocklistHelper);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssSatelliteBlocklistHelper.this.updateSatelliteBlocklist();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInitialize() {
        if (this.mGnssNative.isGnssVisibilityControlSupported()) {
            this.mGnssVisibilityControl = new com.android.server.location.gnss.GnssVisibilityControl(this.mContext, this.mHandler.getLooper(), this.mNIHandler);
        }
        reloadGpsProperties();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        this.mContext.registerReceiver(this.mIntentReceiver, intentFilter, null, this.mHandler);
        if (this.mNetworkConnectivityHandler.isNativeAgpsRilSupported() && this.mGnssConfiguration.isNiSuplMessageInjectionEnabled()) {
            android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
            intentFilter2.addAction("android.provider.Telephony.WAP_PUSH_RECEIVED");
            try {
                intentFilter2.addDataType("application/vnd.omaloc-supl-init");
            } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
                android.util.Log.w(TAG, "Malformed SUPL init mime type");
            }
            this.mContext.registerReceiver(this.mIntentReceiver, intentFilter2, null, this.mHandler);
            android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
            intentFilter3.addAction("android.intent.action.DATA_SMS_RECEIVED");
            intentFilter3.addDataScheme("sms");
            intentFilter3.addDataAuthority("localhost", "7275");
            this.mContext.registerReceiver(this.mIntentReceiver, intentFilter3, null, this.mHandler);
        }
        this.mNetworkConnectivityHandler.registerNetworkCallbacks();
        android.location.LocationManager locationManager = (android.location.LocationManager) this.mContext.getSystemService(android.location.LocationManager.class);
        java.util.Objects.requireNonNull(locationManager);
        if (locationManager.getAllProviders().contains("network")) {
            locationManager.requestLocationUpdates("network", new android.location.LocationRequest.Builder(com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME).setMinUpdateIntervalMillis(0L).setHiddenFromAppOps(true).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.location.LocationListener() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda19
                @Override // android.location.LocationListener
                public final void onLocationChanged(android.location.Location location) {
                    com.android.server.location.gnss.GnssLocationProvider.this.injectLocation(location);
                }
            });
        }
        updateEnabled();
        synchronized (this.mLock) {
            this.mInitialized = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectSuplInit(android.content.Intent intent) {
        if (!isNfwLocationAccessAllowed()) {
            android.util.Log.w(TAG, "Reject SUPL INIT as no NFW location access");
            return;
        }
        int intExtra = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
        if (intExtra == -1) {
            android.util.Log.e(TAG, "Invalid slot index");
            return;
        }
        java.lang.String action = intent.getAction();
        if (!action.equals("android.intent.action.DATA_SMS_RECEIVED")) {
            if (action.equals("android.provider.Telephony.WAP_PUSH_RECEIVED")) {
                injectSuplInit(intent.getByteArrayExtra("data"), intExtra);
                return;
            }
            return;
        }
        android.telephony.SmsMessage[] messagesFromIntent = android.provider.Telephony.Sms.Intents.getMessagesFromIntent(intent);
        if (messagesFromIntent == null) {
            android.util.Log.e(TAG, "Message does not exist in the intent");
            return;
        }
        for (android.telephony.SmsMessage smsMessage : messagesFromIntent) {
            injectSuplInit(smsMessage.getUserData(), intExtra);
        }
    }

    private void injectSuplInit(byte[] bArr, int i) {
        if (bArr != null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "suplInit = " + com.android.internal.util.HexDump.toHexString(bArr) + " slotIndex = " + i);
            }
            this.mGnssNative.injectNiSuplMessageData(bArr, bArr.length, i);
        }
    }

    private boolean isNfwLocationAccessAllowed() {
        if (this.mGnssNative.isInEmergencySession()) {
            return true;
        }
        return this.mGnssVisibilityControl != null && this.mGnssVisibilityControl.hasLocationPermissionEnabledProxyApps();
    }

    @Override // com.android.server.location.gnss.NetworkTimeHelper.InjectTimeCallback
    public void injectTime(long j, long j2, int i) {
        this.mGnssNative.injectTime(j, j2, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNetworkAvailable() {
        this.mNetworkTimeHelper.onNetworkAvailable();
        if (this.mSupportsPsds && isAssistedGpsEnabled()) {
            synchronized (this.mLock) {
                try {
                    java.util.Iterator<java.lang.Integer> it = this.mPendingDownloadPsdsTypes.iterator();
                    while (it.hasNext()) {
                        final int intValue = it.next().intValue();
                        postWithWakeLockHeld(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda18
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.location.gnss.GnssLocationProvider.this.lambda$onNetworkAvailable$1(intValue);
                            }
                        });
                    }
                    this.mPendingDownloadPsdsTypes.clear();
                } finally {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleRequestLocation, reason: merged with bridge method [inline-methods] */
    public void lambda$onRequestLocation$15(boolean z, boolean z2) {
        android.location.LocationListener locationListener;
        java.lang.String str;
        if (isRequestLocationRateLimited()) {
            if (DEBUG) {
                android.util.Log.d(TAG, "RequestLocation is denied due to too frequent requests.");
                return;
            }
            return;
        }
        long j = android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "gnss_hal_location_request_duration_millis", 10000L);
        if (j == 0) {
            android.util.Log.i(TAG, "GNSS HAL location request is disabled by Settings.");
            return;
        }
        android.location.LocationManager locationManager = (android.location.LocationManager) this.mContext.getSystemService("location");
        android.location.LocationRequest.Builder maxUpdates = new android.location.LocationRequest.Builder(1000L).setMaxUpdates(1);
        if (z) {
            locationListener = new android.location.LocationListener() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda13
                @Override // android.location.LocationListener
                public final void onLocationChanged(android.location.Location location) {
                    com.android.server.location.gnss.GnssLocationProvider.lambda$handleRequestLocation$2(location);
                }
            };
            maxUpdates.setQuality(104);
            str = "network";
        } else {
            locationListener = new android.location.LocationListener() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda14
                @Override // android.location.LocationListener
                public final void onLocationChanged(android.location.Location location) {
                    com.android.server.location.gnss.GnssLocationProvider.this.injectBestLocation(location);
                }
            };
            maxUpdates.setQuality(100);
            str = "fused";
        }
        if (this.mNIHandler.getInEmergency()) {
            com.android.server.location.gnss.GnssConfiguration.HalInterfaceVersion halInterfaceVersion = this.mGnssConfiguration.getHalInterfaceVersion();
            if (z2 || halInterfaceVersion.mMajor < 2) {
                maxUpdates.setLocationSettingsIgnored(true);
                j *= 3;
            }
        }
        maxUpdates.setDurationMillis(j);
        android.util.Log.i(TAG, java.lang.String.format("GNSS HAL Requesting location updates from %s provider for %d millis.", str, java.lang.Long.valueOf(j)));
        if (locationManager.getProvider(str) != null) {
            locationManager.requestLocationUpdates(str, maxUpdates.build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, locationListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleRequestLocation$2(android.location.Location location) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectBestLocation(android.location.Location location) {
        if (DEBUG) {
            android.util.Log.d(TAG, "injectBestLocation: " + location);
        }
        if (location.isMock()) {
            return;
        }
        this.mGnssNative.injectBestLocation(location);
    }

    private boolean isRequestLocationRateLimited() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleDownloadPsdsData, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$onRequestPsdsDownload$14(final int i) {
        if (!this.mSupportsPsds) {
            android.util.Log.d(TAG, "handleDownloadPsdsData() called when PSDS not supported");
            return;
        }
        if (!isAssistedGpsEnabled()) {
            android.util.Log.d(TAG, "handleDownloadPsdsData() called when PSDS disabled by system setting");
            return;
        }
        if (!this.mNetworkConnectivityHandler.isDataNetworkConnected()) {
            synchronized (this.mLock) {
                this.mPendingDownloadPsdsTypes.add(java.lang.Integer.valueOf(i));
            }
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mDownloadInProgressPsdsTypes.contains(java.lang.Integer.valueOf(i))) {
                    if (DEBUG) {
                        android.util.Log.d(TAG, "PSDS type " + i + " download in progress. Ignore the request.");
                    }
                    return;
                }
                this.mDownloadPsdsWakeLock.acquire(60000L);
                this.mDownloadInProgressPsdsTypes.add(java.lang.Integer.valueOf(i));
                android.util.Log.i(TAG, "WakeLock acquired by handleDownloadPsdsData()");
                java.util.concurrent.Executors.newSingleThreadExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.gnss.GnssLocationProvider.this.lambda$handleDownloadPsdsData$6(i);
                    }
                });
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDownloadPsdsData$6(final int i) {
        long nextBackoffMillis;
        final byte[] downloadPsdsData = new com.android.server.location.gnss.GnssPsdsDownloader(this.mGnssConfiguration.getProperties()).downloadPsdsData(i);
        if (downloadPsdsData != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssLocationProvider.this.lambda$handleDownloadPsdsData$3(i, downloadPsdsData);
                }
            });
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager != null && packageManager.hasSystemFeature("android.hardware.type.watch") && i == 1 && this.mGnssConfiguration.isPsdsPeriodicDownloadEnabled()) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "scheduling next long term Psds download");
                }
                this.mHandler.removeCallbacksAndMessages(this.mPsdsPeriodicDownloadToken);
                this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.gnss.GnssLocationProvider.this.lambda$handleDownloadPsdsData$4(i);
                    }
                }, this.mPsdsPeriodicDownloadToken, 86400000L);
            }
        } else {
            synchronized (this.mLock) {
                nextBackoffMillis = this.mPsdsBackOff.nextBackoffMillis();
            }
            this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.gnss.GnssLocationProvider.this.lambda$handleDownloadPsdsData$5(i);
                }
            }, nextBackoffMillis);
        }
        synchronized (this.mLock) {
            try {
                if (this.mDownloadPsdsWakeLock.isHeld()) {
                    this.mDownloadPsdsWakeLock.release();
                    if (DEBUG) {
                        android.util.Log.d(TAG, "WakeLock released by handleDownloadPsdsData()");
                    }
                } else {
                    android.util.Log.e(TAG, "WakeLock expired before release in handleDownloadPsdsData()");
                }
                this.mDownloadInProgressPsdsTypes.remove(java.lang.Integer.valueOf(i));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDownloadPsdsData$3(int i, byte[] bArr) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.GNSS_PSDS_DOWNLOAD_REPORTED, i);
        if (DEBUG) {
            android.util.Log.d(TAG, "calling native_inject_psds_data");
        }
        this.mGnssNative.injectPsdsData(bArr, bArr.length, i);
        synchronized (this.mLock) {
            this.mPsdsBackOff.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void injectLocation(android.location.Location location) {
        if (!location.isMock()) {
            this.mGnssNative.injectLocation(location);
        }
    }

    private void setSuplHostPort() {
        this.mSuplServerHost = this.mGnssConfiguration.getSuplHost();
        this.mSuplServerPort = this.mGnssConfiguration.getSuplPort(0);
        if (this.mSuplServerHost != null && this.mSuplServerPort > 0 && this.mSuplServerPort <= 65535) {
            this.mGnssNative.setAgpsServer(1, this.mSuplServerHost, this.mSuplServerPort);
        }
    }

    private int getSuplMode(boolean z) {
        int suplMode;
        return (!z || (suplMode = this.mGnssConfiguration.getSuplMode(0)) == 0 || !this.mGnssNative.getCapabilities().hasMsb() || (suplMode & 1) == 0) ? 0 : 1;
    }

    private void setGpsEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mGpsEnabled = z;
        }
    }

    public void setAutomotiveGnssSuspended(boolean z) {
        synchronized (this.mLock) {
            this.mAutomotiveSuspend = z;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.updateEnabled();
            }
        });
    }

    public boolean isAutomotiveGnssSuspended() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mAutomotiveSuspend && !this.mGpsEnabled;
            } finally {
            }
        }
        return z;
    }

    private void handleEnable() {
        if (DEBUG) {
            android.util.Log.d(TAG, "handleEnable");
        }
        boolean z = false;
        if (this.mGnssNative.init()) {
            setGpsEnabled(true);
            this.mSupportsPsds = this.mGnssNative.isPsdsSupported();
            if (this.mSuplServerHost != null) {
                this.mGnssNative.setAgpsServer(1, this.mSuplServerHost, this.mSuplServerPort);
            }
            if (this.mC2KServerHost != null) {
                this.mGnssNative.setAgpsServer(2, this.mC2KServerHost, this.mC2KServerPort);
            }
            if (this.mGnssNative.initBatching() && this.mGnssNative.getBatchSize() > 1) {
                z = true;
            }
            this.mBatchingEnabled = z;
            if (this.mGnssVisibilityControl != null) {
                this.mGnssVisibilityControl.onGpsEnabledChanged(true);
                return;
            }
            return;
        }
        setGpsEnabled(false);
        android.util.Log.w(TAG, "Failed to enable location provider");
    }

    private void handleDisable() {
        if (DEBUG) {
            android.util.Log.d(TAG, "handleDisable");
        }
        setGpsEnabled(false);
        updateClientUids(new android.os.WorkSource());
        stopNavigating();
        stopBatching();
        if (this.mGnssVisibilityControl != null) {
            this.mGnssVisibilityControl.onGpsEnabledChanged(false);
        }
        this.mGnssNative.cleanupBatching();
        this.mGnssNative.cleanup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEnabled() {
        boolean z;
        android.location.LocationManager locationManager = (android.location.LocationManager) this.mContext.getSystemService(android.location.LocationManager.class);
        java.util.Iterator it = ((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).getVisibleUsers().iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            z2 |= locationManager.isLocationEnabledForUser((android.os.UserHandle) it.next());
        }
        boolean z3 = (this.mProviderRequest != null && this.mProviderRequest.isActive() && this.mProviderRequest.isBypass()) | z2;
        synchronized (this.mLock) {
            z = z3 & (this.mAutomotiveSuspend ? false : true);
        }
        boolean z4 = z & (true ^ this.mShutdown);
        if (z4 == isGpsEnabled()) {
            return;
        }
        if (z4) {
            handleEnable();
        } else {
            handleDisable();
        }
    }

    private boolean isGpsEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mGpsEnabled;
        }
        return z;
    }

    public int getBatchSize() {
        return this.mGnssNative.getBatchSize();
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onFlush(java.lang.Runnable runnable) {
        boolean z;
        synchronized (this.mLock) {
            try {
                if (!this.mBatchingEnabled) {
                    z = false;
                } else {
                    z = this.mFlushListeners.add(runnable);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (!z) {
            runnable.run();
        } else {
            this.mGnssNative.flushBatch();
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void onSetRequest(android.location.provider.ProviderRequest providerRequest) {
        this.mProviderRequest = providerRequest;
        updateEnabled();
        updateRequirements();
    }

    private void updateRequirements() {
        if (this.mProviderRequest == null || this.mProviderRequest.getWorkSource() == null) {
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "setRequest " + this.mProviderRequest);
        }
        if (this.mProviderRequest.isActive() && isGpsEnabled()) {
            updateClientUids(this.mProviderRequest.getWorkSource());
            if (this.mProviderRequest.getIntervalMillis() <= 2147483647L) {
                this.mFixInterval = (int) this.mProviderRequest.getIntervalMillis();
            } else {
                android.util.Log.w(TAG, "interval overflow: " + this.mProviderRequest.getIntervalMillis());
                this.mFixInterval = Integer.MAX_VALUE;
            }
            int max = java.lang.Math.max(this.mFixInterval, 1000);
            long min = java.lang.Math.min(this.mProviderRequest.getMaxUpdateDelayMillis(), 86400000L);
            if (this.mBatchingEnabled && min / 2 >= max) {
                stopNavigating();
                this.mFixInterval = max;
                startBatching(min);
                return;
            }
            stopBatching();
            if (this.mStarted && this.mGnssNative.getCapabilities().hasScheduling()) {
                if (android.location.flags.Flags.gnssCallStopBeforeSetPositionMode()) {
                    if (!new com.android.server.location.gnss.GnssPositionMode(this.mPositionMode, 0, this.mFixInterval, 0, 0, this.mProviderRequest.isLowPower()).equals(this.mLastPositionMode)) {
                        stopNavigating();
                        startNavigating();
                        return;
                    }
                    return;
                }
                if (!setPositionMode(this.mPositionMode, 0, this.mFixInterval, this.mProviderRequest.isLowPower())) {
                    android.util.Log.e(TAG, "set_position_mode failed in updateRequirements");
                    return;
                }
                return;
            }
            if (!this.mStarted) {
                startNavigating();
                return;
            }
            this.mAlarmManager.cancel(this.mTimeoutListener);
            if (this.mFixInterval >= 60000) {
                this.mAlarmManager.set(2, android.os.SystemClock.elapsedRealtime() + 60000, TAG, this.mTimeoutListener, this.mHandler);
                return;
            }
            return;
        }
        updateClientUids(new android.os.WorkSource());
        stopNavigating();
        stopBatching();
    }

    private boolean setPositionMode(int i, int i2, int i3, boolean z) {
        com.android.server.location.gnss.GnssPositionMode gnssPositionMode = new com.android.server.location.gnss.GnssPositionMode(i, i2, i3, 0, 0, z);
        if (this.mLastPositionMode != null && this.mLastPositionMode.equals(gnssPositionMode)) {
            return true;
        }
        boolean positionMode = this.mGnssNative.setPositionMode(i, i2, i3, 0, 0, z);
        if (positionMode) {
            this.mLastPositionMode = gnssPositionMode;
        } else {
            this.mLastPositionMode = null;
        }
        return positionMode;
    }

    private void updateClientUids(android.os.WorkSource workSource) {
        if (workSource.equals(this.mClientSource)) {
            return;
        }
        try {
            this.mBatteryStats.noteGpsChanged(this.mClientSource, workSource);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException", e);
        }
        java.util.ArrayList[] diffChains = android.os.WorkSource.diffChains(this.mClientSource, workSource);
        if (diffChains != null) {
            java.util.ArrayList<android.os.WorkSource.WorkChain> arrayList = diffChains[0];
            java.util.ArrayList<android.os.WorkSource.WorkChain> arrayList2 = diffChains[1];
            if (arrayList != null) {
                for (android.os.WorkSource.WorkChain workChain : arrayList) {
                    this.mAppOps.startOpNoThrow(2, workChain.getAttributionUid(), workChain.getAttributionTag());
                }
            }
            if (arrayList2 != null) {
                for (android.os.WorkSource.WorkChain workChain2 : arrayList2) {
                    this.mAppOps.finishOp(2, workChain2.getAttributionUid(), workChain2.getAttributionTag());
                }
            }
            this.mClientSource.transferWorkChains(workSource);
        }
        android.os.WorkSource[] returningDiffs = this.mClientSource.setReturningDiffs(workSource);
        if (returningDiffs != null) {
            android.os.WorkSource workSource2 = returningDiffs[0];
            android.os.WorkSource workSource3 = returningDiffs[1];
            if (workSource2 != null) {
                for (int i = 0; i < workSource2.size(); i++) {
                    this.mAppOps.startOpNoThrow(2, workSource2.getUid(i), workSource2.getPackageName(i));
                }
            }
            if (workSource3 != null) {
                for (int i2 = 0; i2 < workSource3.size(); i2++) {
                    this.mAppOps.finishOp(2, workSource3.getUid(i2), workSource3.getPackageName(i2));
                }
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void onExtraCommand(int i, int i2, java.lang.String str, android.os.Bundle bundle) {
        if ("delete_aiding_data".equals(str)) {
            deleteAidingData(bundle);
            return;
        }
        if ("force_time_injection".equals(str)) {
            demandUtcTimeInjection();
            return;
        }
        if ("force_psds_injection".equals(str)) {
            if (this.mSupportsPsds && isAssistedGpsEnabled()) {
                postWithWakeLockHeld(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.location.gnss.GnssLocationProvider.this.lambda$onExtraCommand$7();
                    }
                });
                return;
            }
            return;
        }
        if ("request_power_stats".equals(str)) {
            this.mGnssNative.requestPowerStats();
            return;
        }
        android.util.Log.w(TAG, "sendExtraCommand: unknown command " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onExtraCommand$7() {
        lambda$onRequestPsdsDownload$14(1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void deleteAidingData(android.os.Bundle bundle) {
        int i = 65535;
        if (bundle != null) {
            boolean z = bundle.getBoolean("ephemeris");
            boolean z2 = z;
            if (bundle.getBoolean("almanac")) {
                z2 = (z ? 1 : 0) | 2;
            }
            boolean z3 = z2;
            if (bundle.getBoolean("position")) {
                z3 = (z2 ? 1 : 0) | 4;
            }
            boolean z4 = z3;
            if (bundle.getBoolean("time")) {
                z4 = (z3 ? 1 : 0) | '\b';
            }
            boolean z5 = z4;
            if (bundle.getBoolean("iono")) {
                z5 = (z4 ? 1 : 0) | 16;
            }
            boolean z6 = z5;
            if (bundle.getBoolean("utc")) {
                z6 = (z5 ? 1 : 0) | ' ';
            }
            boolean z7 = z6;
            if (bundle.getBoolean("health")) {
                z7 = (z6 ? 1 : 0) | '@';
            }
            boolean z8 = z7;
            if (bundle.getBoolean("svdir")) {
                z8 = (z7 ? 1 : 0) | 128;
            }
            boolean z9 = z8;
            if (bundle.getBoolean("svsteer")) {
                z9 = (z8 ? 1 : 0) | 256;
            }
            boolean z10 = z9;
            if (bundle.getBoolean("sadata")) {
                z10 = (z9 ? 1 : 0) | 512;
            }
            boolean z11 = z10;
            if (bundle.getBoolean("rti")) {
                z11 = (z10 ? 1 : 0) | 1024;
            }
            boolean z12 = z11;
            if (bundle.getBoolean("celldb-info")) {
                z12 = (z11 ? 1 : 0) | 32768;
            }
            if (!bundle.getBoolean("all")) {
                i = z12;
            }
        }
        if (i != 0) {
            this.mGnssNative.deleteAidingData(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startNavigating() {
        java.lang.String str;
        if (!this.mStarted) {
            if (DEBUG) {
                android.util.Log.d(TAG, "startNavigating");
            }
            this.mTimeToFirstFix = 0;
            this.mLastFixTime = 0L;
            setStarted(true);
            this.mPositionMode = getSuplMode(isAssistedGpsEnabled());
            if (DEBUG) {
                switch (this.mPositionMode) {
                    case 0:
                        str = "standalone";
                        break;
                    case 1:
                        str = "MS_BASED";
                        break;
                    case 2:
                        str = "MS_ASSISTED";
                        break;
                    default:
                        str = "unknown";
                        break;
                }
                android.util.Log.d(TAG, "setting position_mode to " + str);
            }
            int i = this.mGnssNative.getCapabilities().hasScheduling() ? this.mFixInterval : 1000;
            if (android.location.flags.Flags.gnssCallStopBeforeSetPositionMode()) {
                if (this.mGnssNative.setPositionMode(this.mPositionMode, 0, i, 0, 0, this.mProviderRequest.isLowPower())) {
                    this.mLastPositionMode = new com.android.server.location.gnss.GnssPositionMode(this.mPositionMode, 0, i, 0, 0, this.mProviderRequest.isLowPower());
                } else {
                    this.mLastPositionMode = null;
                    setStarted(false);
                    android.util.Log.e(TAG, "set_position_mode failed in startNavigating()");
                    return;
                }
            } else if (!setPositionMode(this.mPositionMode, 0, i, this.mProviderRequest.isLowPower())) {
                setStarted(false);
                android.util.Log.e(TAG, "set_position_mode failed in startNavigating()");
                return;
            }
            if (!this.mGnssNative.start()) {
                setStarted(false);
                android.util.Log.e(TAG, "native_start failed in startNavigating()");
                return;
            }
            this.mLocationExtras.reset();
            this.mFixRequestTime = android.os.SystemClock.elapsedRealtime();
            if (!this.mGnssNative.getCapabilities().hasScheduling() && this.mFixInterval >= 60000) {
                this.mAlarmManager.set(2, android.os.SystemClock.elapsedRealtime() + 60000, TAG, this.mTimeoutListener, this.mHandler);
            }
        }
    }

    private void stopNavigating() {
        if (DEBUG) {
            android.util.Log.d(TAG, "stopNavigating");
        }
        if (this.mStarted) {
            setStarted(false);
            this.mGnssNative.stop();
            this.mLastFixTime = 0L;
            this.mLastPositionMode = null;
            this.mLocationExtras.reset();
        }
        this.mAlarmManager.cancel(this.mTimeoutListener);
        this.mAlarmManager.cancel(this.mWakeupListener);
    }

    private void startBatching(final long j) {
        long j2 = j / this.mFixInterval;
        if (DEBUG) {
            android.util.Log.d(TAG, "startBatching " + this.mFixInterval + " " + j);
        }
        if (this.mGnssNative.startBatch(java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(this.mFixInterval), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, true)) {
            this.mBatchingStarted = true;
            if (j2 < getBatchSize()) {
                this.mBatchingAlarm = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda9
                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        com.android.server.location.gnss.GnssLocationProvider.this.lambda$startBatching$8(j);
                    }
                };
                this.mAlarmManager.setExact(2, android.os.SystemClock.elapsedRealtime() + j, TAG, this.mBatchingAlarm, com.android.server.FgThread.getHandler());
                return;
            }
            return;
        }
        android.util.Log.e(TAG, "native_start_batch failed in startBatching()");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startBatching$8(long j) {
        boolean z;
        synchronized (this.mLock) {
            try {
                if (this.mBatchingAlarm == null) {
                    z = false;
                } else {
                    this.mAlarmManager.setExact(2, android.os.SystemClock.elapsedRealtime() + j, TAG, this.mBatchingAlarm, com.android.server.FgThread.getHandler());
                    z = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            this.mGnssNative.flushBatch();
        }
    }

    private void stopBatching() {
        if (DEBUG) {
            android.util.Log.d(TAG, "stopBatching");
        }
        if (this.mBatchingStarted) {
            if (this.mBatchingAlarm != null) {
                this.mAlarmManager.cancel(this.mBatchingAlarm);
                this.mBatchingAlarm = null;
            }
            this.mGnssNative.flushBatch();
            this.mGnssNative.stopBatch();
            this.mBatchingStarted = false;
        }
    }

    private void setStarted(boolean z) {
        if (this.mStarted != z) {
            this.mStarted = z;
            this.mStartedChangedElapsedRealtime = android.os.SystemClock.elapsedRealtime();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hibernate() {
        stopNavigating();
        this.mAlarmManager.set(2, android.os.SystemClock.elapsedRealtime() + this.mFixInterval, TAG, this.mWakeupListener, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleReportLocation, reason: merged with bridge method [inline-methods] */
    public void lambda$onReportLocation$12(boolean z, android.location.Location location) {
        if (VERBOSE) {
            android.util.Log.v(TAG, "reportLocation " + location.toString());
        }
        location.setExtras(this.mLocationExtras.getBundle());
        try {
            reportLocation(android.location.LocationResult.wrap(new android.location.Location[]{location}).validate());
            if (this.mStarted) {
                this.mGnssMetrics.logReceivedLocationStatus(z);
                if (z) {
                    if (location.hasAccuracy()) {
                        this.mGnssMetrics.logPositionAccuracyMeters(location.getAccuracy());
                    }
                    if (this.mTimeToFirstFix > 0) {
                        this.mGnssMetrics.logMissedReports(this.mFixInterval, (int) (android.os.SystemClock.elapsedRealtime() - this.mLastFixTime));
                    }
                }
            } else {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - this.mStartedChangedElapsedRealtime;
                if (elapsedRealtime > LOCATION_OFF_DELAY_THRESHOLD_WARN_MILLIS) {
                    java.lang.String str = "Unexpected GNSS Location report " + android.util.TimeUtils.formatDuration(elapsedRealtime) + " after location turned off";
                    if (elapsedRealtime > LOCATION_OFF_DELAY_THRESHOLD_ERROR_MILLIS) {
                        android.util.Log.e(TAG, str);
                    } else {
                        android.util.Log.w(TAG, str);
                    }
                }
            }
            this.mLastFixTime = android.os.SystemClock.elapsedRealtime();
            if (this.mTimeToFirstFix == 0 && z) {
                this.mTimeToFirstFix = (int) (this.mLastFixTime - this.mFixRequestTime);
                if (DEBUG) {
                    android.util.Log.d(TAG, "TTFF: " + this.mTimeToFirstFix);
                }
                if (this.mStarted) {
                    this.mGnssMetrics.logTimeToFirstFixMilliSecs(this.mTimeToFirstFix);
                }
            }
            if (this.mStarted && !this.mGnssNative.getCapabilities().hasScheduling() && this.mFixInterval < 60000) {
                this.mAlarmManager.cancel(this.mTimeoutListener);
            }
            if (!this.mGnssNative.getCapabilities().hasScheduling() && this.mStarted && this.mFixInterval > 10000) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "got fix, hibernating");
                }
                hibernate();
            }
        } catch (android.location.LocationResult.BadLocationException e) {
            android.util.Log.e(TAG, "Dropping invalid location: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleReportSvStatus, reason: merged with bridge method [inline-methods] */
    public void lambda$onReportSvStatus$13(android.location.GnssStatus gnssStatus) {
        this.mGnssMetrics.logCn0(gnssStatus);
        if (VERBOSE) {
            android.util.Log.v(TAG, "SV count: " + gnssStatus.getSatelliteCount());
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < gnssStatus.getSatelliteCount(); i4++) {
            if (gnssStatus.usedInFix(i4)) {
                hashSet.add(new android.util.Pair(java.lang.Integer.valueOf(gnssStatus.getConstellationType(i4)), java.lang.Integer.valueOf(gnssStatus.getSvid(i4))));
                i++;
                if (gnssStatus.getCn0DbHz(i4) > i3) {
                    i3 = (int) gnssStatus.getCn0DbHz(i4);
                }
                i2 = (int) (i2 + gnssStatus.getCn0DbHz(i4));
                this.mGnssMetrics.logConstellationType(gnssStatus.getConstellationType(i4));
            }
        }
        if (i > 0) {
            i2 /= i;
        }
        this.mLocationExtras.set(hashSet.size(), i2, i3);
        this.mGnssMetrics.logSvStatus(gnssStatus);
    }

    private void restartLocationRequest() {
        if (DEBUG) {
            android.util.Log.d(TAG, "restartLocationRequest");
        }
        setStarted(false);
        updateRequirements();
    }

    private void demandUtcTimeInjection() {
        if (DEBUG) {
            android.util.Log.d(TAG, "demandUtcTimeInjection");
        }
        final com.android.server.location.gnss.NetworkTimeHelper networkTimeHelper = this.mNetworkTimeHelper;
        java.util.Objects.requireNonNull(networkTimeHelper);
        postWithWakeLockHeld(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.NetworkTimeHelper.this.demandUtcTimeInjection();
            }
        });
    }

    private static int getCellType(android.telephony.CellInfo cellInfo) {
        if (cellInfo instanceof android.telephony.CellInfoGsm) {
            return 1;
        }
        if (cellInfo instanceof android.telephony.CellInfoWcdma) {
            return 4;
        }
        if (cellInfo instanceof android.telephony.CellInfoLte) {
            return 3;
        }
        if (cellInfo instanceof android.telephony.CellInfoNr) {
            return 6;
        }
        return 0;
    }

    private static long getCidFromCellIdentity(android.telephony.CellIdentity cellIdentity) {
        long cid;
        if (cellIdentity == null) {
            return -1L;
        }
        switch (cellIdentity.getType()) {
            case 1:
                cid = ((android.telephony.CellIdentityGsm) cellIdentity).getCid();
                break;
            case 2:
            case 5:
            default:
                cid = -1;
                break;
            case 3:
                cid = ((android.telephony.CellIdentityLte) cellIdentity).getCi();
                break;
            case 4:
                cid = ((android.telephony.CellIdentityWcdma) cellIdentity).getCid();
                break;
            case 6:
                cid = ((android.telephony.CellIdentityNr) cellIdentity).getNci();
                break;
        }
        if (cid == (cellIdentity.getType() == 6 ? com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME : 2147483647L)) {
            return -1L;
        }
        return cid;
    }

    private void setRefLocation(int i, android.telephony.CellIdentity cellIdentity) {
        int lac;
        long j;
        int i2;
        int i3;
        int i4;
        java.lang.String mccString = cellIdentity.getMccString();
        java.lang.String mncString = cellIdentity.getMncString();
        int parseInt = mccString != null ? java.lang.Integer.parseInt(mccString) : Integer.MAX_VALUE;
        int parseInt2 = mncString != null ? java.lang.Integer.parseInt(mncString) : Integer.MAX_VALUE;
        switch (i) {
            case 1:
                android.telephony.CellIdentityGsm cellIdentityGsm = (android.telephony.CellIdentityGsm) cellIdentity;
                long cid = cellIdentityGsm.getCid();
                lac = cellIdentityGsm.getLac();
                j = cid;
                i2 = Integer.MAX_VALUE;
                i3 = Integer.MAX_VALUE;
                i4 = Integer.MAX_VALUE;
                break;
            case 2:
                android.telephony.CellIdentityWcdma cellIdentityWcdma = (android.telephony.CellIdentityWcdma) cellIdentity;
                long cid2 = cellIdentityWcdma.getCid();
                lac = cellIdentityWcdma.getLac();
                j = cid2;
                i2 = Integer.MAX_VALUE;
                i3 = Integer.MAX_VALUE;
                i4 = Integer.MAX_VALUE;
                break;
            case 4:
                android.telephony.CellIdentityLte cellIdentityLte = (android.telephony.CellIdentityLte) cellIdentity;
                long ci = cellIdentityLte.getCi();
                int tac = cellIdentityLte.getTac();
                i3 = cellIdentityLte.getPci();
                j = ci;
                lac = Integer.MAX_VALUE;
                i4 = Integer.MAX_VALUE;
                i2 = tac;
                break;
            case 8:
                android.telephony.CellIdentityNr cellIdentityNr = (android.telephony.CellIdentityNr) cellIdentity;
                long nci = cellIdentityNr.getNci();
                int tac2 = cellIdentityNr.getTac();
                int pci = cellIdentityNr.getPci();
                i4 = cellIdentityNr.getNrarfcn();
                j = nci;
                lac = Integer.MAX_VALUE;
                i2 = tac2;
                i3 = pci;
                break;
            default:
                j = Long.MAX_VALUE;
                lac = Integer.MAX_VALUE;
                i2 = Integer.MAX_VALUE;
                i3 = Integer.MAX_VALUE;
                i4 = Integer.MAX_VALUE;
                break;
        }
        this.mGnssNative.setAgpsReferenceLocationCellId(i, parseInt, parseInt2, lac, j, i2, i3, i4);
    }

    private void requestRefLocation() {
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE);
        int phoneType = telephonyManager.getPhoneType();
        if (phoneType != 1) {
            if (phoneType == 2) {
                android.util.Log.e(TAG, "CDMA not supported.");
                return;
            }
            return;
        }
        java.util.List<android.telephony.CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
        if (allCellInfo == null) {
            android.util.Log.e(TAG, "Error getting cell location info.");
            return;
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        allCellInfo.sort(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda22
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int lambda$requestRefLocation$9;
                lambda$requestRefLocation$9 = com.android.server.location.gnss.GnssLocationProvider.lambda$requestRefLocation$9((android.telephony.CellInfo) obj);
                return lambda$requestRefLocation$9;
            }
        }).reversed());
        for (android.telephony.CellInfo cellInfo : allCellInfo) {
            int cellConnectionStatus = cellInfo.getCellConnectionStatus();
            if (cellInfo.isRegistered() || cellConnectionStatus == 1 || cellConnectionStatus == 2) {
                android.telephony.CellIdentity cellIdentity = cellInfo.getCellIdentity();
                int cellType = getCellType(cellInfo);
                if (getCidFromCellIdentity(cellIdentity) != -1 && !hashMap.containsKey(java.lang.Integer.valueOf(cellType))) {
                    hashMap.put(java.lang.Integer.valueOf(cellType), cellIdentity);
                }
            }
        }
        if (hashMap.containsKey(1)) {
            setRefLocation(1, (android.telephony.CellIdentity) hashMap.get(1));
            return;
        }
        if (hashMap.containsKey(4)) {
            setRefLocation(2, (android.telephony.CellIdentity) hashMap.get(4));
            return;
        }
        if (hashMap.containsKey(3)) {
            setRefLocation(4, (android.telephony.CellIdentity) hashMap.get(3));
        } else if (!hashMap.containsKey(6)) {
            android.util.Log.e(TAG, "No available serving cell information.");
        } else {
            setRefLocation(8, (android.telephony.CellIdentity) hashMap.get(6));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$requestRefLocation$9(android.telephony.CellInfo cellInfo) {
        return cellInfo.getCellSignalStrength().getAsuLevel();
    }

    private void postWithWakeLockHeld(final java.lang.Runnable runnable) {
        this.mWakeLock.acquire(30000L);
        if (!this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.lambda$postWithWakeLockHeld$10(runnable);
            }
        })) {
            this.mWakeLock.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postWithWakeLockHeld$10(java.lang.Runnable runnable) {
        try {
            runnable.run();
        } finally {
            this.mWakeLock.release();
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.String str;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= strArr.length || (str = strArr[i]) == null || str.length() <= 0 || str.charAt(0) != '-') {
                break;
            }
            i++;
            if ("-a".equals(str)) {
                z = true;
                break;
            }
        }
        printWriter.print("mStarted=" + this.mStarted + "   (changed ");
        android.util.TimeUtils.formatDuration(android.os.SystemClock.elapsedRealtime() - this.mStartedChangedElapsedRealtime, printWriter);
        printWriter.println(" ago)");
        printWriter.println("mBatchingEnabled=" + this.mBatchingEnabled);
        printWriter.println("mBatchingStarted=" + this.mBatchingStarted);
        printWriter.println("mBatchSize=" + getBatchSize());
        printWriter.println("mFixInterval=" + this.mFixInterval);
        printWriter.print(this.mGnssMetrics.dumpGnssMetricsAsText());
        if (z) {
            this.mNetworkTimeHelper.dump(printWriter);
            printWriter.println("mSupportsPsds=" + this.mSupportsPsds);
            printWriter.println("PsdsServerConfigured=" + this.mGnssConfiguration.isLongTermPsdsServerConfigured());
            printWriter.println("native internal state: ");
            printWriter.println("  " + this.mGnssNative.getInternalState());
            printWriter.println("isAssistedGpsEnabled=" + isAssistedGpsEnabled());
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onHalRestarted() {
        reloadGpsProperties();
        if (isGpsEnabled()) {
            setGpsEnabled(false);
            updateEnabled();
            restartLocationRequest();
        }
        synchronized (this.mLock) {
            try {
                if (this.mInitialized) {
                    this.mNetworkConnectivityHandler.unregisterNetworkCallbacks();
                    this.mNetworkConnectivityHandler.registerNetworkCallbacks();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.BaseCallbacks
    public void onCapabilitiesChanged(android.location.GnssCapabilities gnssCapabilities, android.location.GnssCapabilities gnssCapabilities2) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.lambda$onCapabilitiesChanged$11();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCapabilitiesChanged$11() {
        boolean hasOnDemandTime = this.mGnssNative.getCapabilities().hasOnDemandTime();
        this.mNetworkTimeHelper.setPeriodicTimeInjectionMode(hasOnDemandTime);
        if (hasOnDemandTime) {
            demandUtcTimeInjection();
        }
        restartLocationRequest();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LocationCallbacks
    public void onReportLocation(final boolean z, final android.location.Location location) {
        postWithWakeLockHeld(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.lambda$onReportLocation$12(z, location);
            }
        });
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LocationCallbacks
    public void onReportLocations(android.location.Location[] locationArr) {
        java.lang.Runnable[] runnableArr;
        boolean z;
        if (DEBUG) {
            android.util.Log.d(TAG, "Location batch of size " + locationArr.length + " reported");
        }
        if (locationArr.length > 0) {
            if (locationArr.length > 1) {
                int length = locationArr.length - 2;
                while (true) {
                    if (length < 0) {
                        z = false;
                        break;
                    }
                    int i = length + 1;
                    if (java.lang.Math.abs((locationArr[i].getTime() - locationArr[length].getTime()) - (locationArr[i].getElapsedRealtimeMillis() - locationArr[length].getElapsedRealtimeMillis())) > 500) {
                        z = true;
                        break;
                    }
                    length--;
                }
                if (z) {
                    java.util.Arrays.sort(locationArr, java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda2
                        @Override // java.util.function.ToLongFunction
                        public final long applyAsLong(java.lang.Object obj) {
                            return ((android.location.Location) obj).getTime();
                        }
                    }));
                    long time = locationArr[locationArr.length - 1].getTime() - locationArr[locationArr.length - 1].getElapsedRealtimeMillis();
                    for (int length2 = locationArr.length - 2; length2 >= 0; length2--) {
                        locationArr[length2].setElapsedRealtimeNanos(java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(java.lang.Math.max(locationArr[length2].getTime() - time, 0L)));
                    }
                } else {
                    java.util.Arrays.sort(locationArr, java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda3
                        @Override // java.util.function.ToLongFunction
                        public final long applyAsLong(java.lang.Object obj) {
                            return ((android.location.Location) obj).getElapsedRealtimeNanos();
                        }
                    }));
                }
            }
            try {
                reportLocation(android.location.LocationResult.wrap(locationArr).validate());
            } catch (android.location.LocationResult.BadLocationException e) {
                android.util.Log.e(TAG, "Dropping invalid locations: " + e);
                return;
            }
        }
        synchronized (this.mLock) {
            runnableArr = (java.lang.Runnable[]) this.mFlushListeners.toArray(new java.lang.Runnable[0]);
            this.mFlushListeners.clear();
        }
        for (java.lang.Runnable runnable : runnableArr) {
            runnable.run();
        }
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.SvStatusCallbacks
    public void onReportSvStatus(final android.location.GnssStatus gnssStatus) {
        postWithWakeLockHeld(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.lambda$onReportSvStatus$13(gnssStatus);
            }
        });
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.AGpsCallbacks
    public void onReportAGpsStatus(int i, int i2, byte[] bArr) {
        this.mNetworkConnectivityHandler.onReportAGpsStatus(i, i2, bArr);
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.PsdsCallbacks
    public void onRequestPsdsDownload(final int i) {
        postWithWakeLockHeld(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.lambda$onRequestPsdsDownload$14(i);
            }
        });
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.AGpsCallbacks
    public void onRequestSetID(int i) {
        java.lang.String str;
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE);
        java.lang.Boolean valueOf = java.lang.Boolean.valueOf(this.mNIHandler.getInEmergency());
        int i2 = 0;
        if (!valueOf.booleanValue()) {
            this.mGnssNative.setAgpsSetId(0, "");
            return;
        }
        int defaultDataSubscriptionId = android.telephony.SubscriptionManager.getDefaultDataSubscriptionId();
        if (this.mGnssConfiguration.isActiveSimEmergencySuplEnabled() && valueOf.booleanValue() && this.mNetworkConnectivityHandler.getActiveSubId() >= 0) {
            defaultDataSubscriptionId = this.mNetworkConnectivityHandler.getActiveSubId();
        }
        if (android.telephony.SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            telephonyManager = telephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
        }
        if ((i & 1) == 1) {
            str = telephonyManager.getSubscriberId();
            if (str != null) {
                i2 = 1;
            }
        } else if ((i & 2) != 2) {
            str = null;
        } else {
            str = telephonyManager.getLine1Number();
            if (str != null) {
                i2 = 2;
            }
        }
        this.mGnssNative.setAgpsSetId(i2, str != null ? str : "");
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks
    public void onRequestLocation(final boolean z, final boolean z2) {
        if (DEBUG) {
            android.util.Log.d(TAG, "requestLocation. independentFromGnss: " + z + ", isUserEmergency: " + z2);
        }
        postWithWakeLockHeld(new java.lang.Runnable() { // from class: com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.gnss.GnssLocationProvider.this.lambda$onRequestLocation$15(z, z2);
            }
        });
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.TimeCallbacks
    public void onRequestUtcTime() {
        demandUtcTimeInjection();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks
    public void onRequestRefLocation() {
        requestRefLocation();
    }

    @Override // com.android.server.location.gnss.hal.GnssNative.NotificationCallbacks
    public void onReportNfwNotification(java.lang.String str, byte b, java.lang.String str2, byte b2, java.lang.String str3, byte b3, boolean z, boolean z2) {
        if (this.mGnssVisibilityControl == null) {
            android.util.Log.e(TAG, "reportNfwNotification: mGnssVisibilityControl uninitialized.");
        } else {
            this.mGnssVisibilityControl.reportNfwNotification(str, b, str2, b2, str3, b3, z, z2);
        }
    }

    private boolean isAssistedGpsEnabled() {
        java.lang.Boolean valueOf = java.lang.Boolean.valueOf(this.mNIHandler.getInEmergency());
        if (valueOf.booleanValue()) {
            android.util.Log.i(TAG, "Forcing Assisted GPS due to emergency");
        }
        return android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "assisted_gps_enabled", 0) != 0 || valueOf.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleXtraDaemon() {
        android.util.Log.i(TAG, "Toggling xtra-daemon via property");
        android.os.SystemProperties.set("persist.sys.xtra-daemon.enabled", java.lang.Boolean.toString(isAssistedGpsEnabled()));
    }
}
