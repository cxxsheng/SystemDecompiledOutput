package com.android.server.power;

/* loaded from: classes2.dex */
public class LowPowerStandbyController {
    private static final java.lang.String ATTR_VALUE = "value";
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_ACTIVE_DURING_MAINTENANCE = false;

    @com.android.internal.annotations.VisibleForTesting
    static final android.os.PowerManager.LowPowerStandbyPolicy DEFAULT_POLICY = new android.os.PowerManager.LowPowerStandbyPolicy("DEFAULT_POLICY", java.util.Collections.emptySet(), 1, java.util.Collections.emptySet());
    private static final int MSG_FOREGROUND_SERVICE_STATE_CHANGED = 4;
    private static final int MSG_NOTIFY_ACTIVE_CHANGED = 1;
    private static final int MSG_NOTIFY_ALLOWLIST_CHANGED = 2;
    private static final int MSG_NOTIFY_POLICY_CHANGED = 3;
    private static final int MSG_NOTIFY_STANDBY_PORTS_CHANGED = 5;
    private static final int MSG_STANDBY_TIMEOUT = 0;
    private static final java.lang.String TAG = "LowPowerStandbyController";
    private static final java.lang.String TAG_ALLOWED_FEATURES = "allowed-features";
    private static final java.lang.String TAG_ALLOWED_REASONS = "allowed-reasons";
    private static final java.lang.String TAG_EXEMPT_PACKAGE = "exempt-package";
    private static final java.lang.String TAG_IDENTIFIER = "identifier";
    private static final java.lang.String TAG_ROOT = "low-power-standby-policy";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mActiveDuringMaintenance;
    private final java.util.function.Supplier<android.app.IActivityManager> mActivityManager;
    private android.app.ActivityManagerInternal mActivityManagerInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.app.AlarmManager mAlarmManager;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final com.android.server.power.LowPowerStandbyController.Clock mClock;
    private final android.content.Context mContext;
    private final com.android.server.power.LowPowerStandbyController.DeviceConfigWrapper mDeviceConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mEnableCustomPolicy;
    private boolean mEnableStandbyPorts;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mEnabledByDefaultConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mForceActive;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIdleSinceNonInteractive;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsActive;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsDeviceIdle;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsInteractive;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastInteractiveTimeElapsed;
    private final com.android.server.power.LowPowerStandbyControllerInternal mLocalService;
    private final java.lang.Object mLock;
    private final java.util.List<java.lang.String> mLowPowerStandbyManagingPackages;
    private final android.app.AlarmManager.OnAlarmListener mOnStandbyTimeoutExpired;
    private final android.content.BroadcastReceiver mPackageBroadcastReceiver;
    private final com.android.server.power.LowPowerStandbyController.PhoneCallServiceTracker mPhoneCallServiceTracker;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.os.PowerManager.LowPowerStandbyPolicy mPolicy;
    private final java.io.File mPolicyFile;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.PowerManager mPowerManager;
    private final com.android.server.power.LowPowerStandbyController.SettingsObserver mSettingsObserver;
    private final java.util.List<com.android.server.power.LowPowerStandbyController.StandbyPortsLock> mStandbyPortLocks;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mStandbyTimeoutConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSupportedConfig;
    private final com.android.server.power.LowPowerStandbyController.TempAllowlistChangeListener mTempAllowlistChangeListener;
    private final android.util.SparseIntArray mUidAllowedReasons;
    private final android.content.BroadcastReceiver mUserReceiver;

    @com.android.internal.annotations.VisibleForTesting
    interface Clock {
        long elapsedRealtime();

        long uptimeMillis();
    }

    private final class StandbyPortsLock implements android.os.IBinder.DeathRecipient {
        private final java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> mPorts;
        private final android.os.IBinder mToken;
        private final int mUid;

        StandbyPortsLock(android.os.IBinder iBinder, int i, java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> list) {
            this.mToken = iBinder;
            this.mUid = i;
            this.mPorts = list;
        }

        public boolean linkToDeath() {
            try {
                this.mToken.linkToDeath(this, 0);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.i(com.android.server.power.LowPowerStandbyController.TAG, "StandbyPorts token already died");
                return false;
            }
        }

        public void unlinkToDeath() {
            this.mToken.unlinkToDeath(this, 0);
        }

        public android.os.IBinder getToken() {
            return this.mToken;
        }

        public int getUid() {
            return this.mUid;
        }

        public java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> getPorts() {
            return this.mPorts;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.power.LowPowerStandbyController.this.releaseStandbyPorts(this.mToken);
        }
    }

    private static class RealClock implements com.android.server.power.LowPowerStandbyController.Clock {
        private RealClock() {
        }

        @Override // com.android.server.power.LowPowerStandbyController.Clock
        public long elapsedRealtime() {
            return android.os.SystemClock.elapsedRealtime();
        }

        @Override // com.android.server.power.LowPowerStandbyController.Clock
        public long uptimeMillis() {
            return android.os.SystemClock.uptimeMillis();
        }
    }

    public LowPowerStandbyController(android.content.Context context, android.os.Looper looper) {
        this(context, looper, new com.android.server.power.LowPowerStandbyController.RealClock(), new com.android.server.power.LowPowerStandbyController.DeviceConfigWrapper(), new java.util.function.Supplier() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.app.IActivityManager service;
                service = android.app.ActivityManager.getService();
                return service;
            }
        }, new java.io.File(android.os.Environment.getDataSystemDirectory(), "low_power_standby_policy.xml"));
    }

    @com.android.internal.annotations.VisibleForTesting
    LowPowerStandbyController(android.content.Context context, android.os.Looper looper, com.android.server.power.LowPowerStandbyController.Clock clock, com.android.server.power.LowPowerStandbyController.DeviceConfigWrapper deviceConfigWrapper, java.util.function.Supplier<android.app.IActivityManager> supplier, java.io.File file) {
        this.mLock = new java.lang.Object();
        this.mOnStandbyTimeoutExpired = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.power.LowPowerStandbyController.this.onStandbyTimeoutExpired();
            }
        };
        this.mLocalService = new com.android.server.power.LowPowerStandbyController.LocalService();
        this.mUidAllowedReasons = new android.util.SparseIntArray();
        this.mLowPowerStandbyManagingPackages = new java.util.ArrayList();
        this.mStandbyPortLocks = new java.util.ArrayList();
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.power.LowPowerStandbyController.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                char c;
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
                    case 870701415:
                        if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
                            c = 2;
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
                        com.android.server.power.LowPowerStandbyController.this.onNonInteractive();
                        break;
                    case 1:
                        com.android.server.power.LowPowerStandbyController.this.onInteractive();
                        break;
                    case 2:
                        com.android.server.power.LowPowerStandbyController.this.onDeviceIdleModeChanged();
                        break;
                }
            }
        };
        this.mTempAllowlistChangeListener = new com.android.server.power.LowPowerStandbyController.TempAllowlistChangeListener();
        this.mPhoneCallServiceTracker = new com.android.server.power.LowPowerStandbyController.PhoneCallServiceTracker();
        this.mPackageBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.power.LowPowerStandbyController.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    return;
                }
                android.net.Uri data = intent.getData();
                java.lang.String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                synchronized (com.android.server.power.LowPowerStandbyController.this.mLock) {
                    try {
                        if (com.android.server.power.LowPowerStandbyController.this.getPolicy().getExemptPackages().contains(schemeSpecificPart)) {
                            com.android.server.power.LowPowerStandbyController.this.enqueueNotifyAllowlistChangedLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mUserReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.power.LowPowerStandbyController.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                synchronized (com.android.server.power.LowPowerStandbyController.this.mLock) {
                    com.android.server.power.LowPowerStandbyController.this.enqueueNotifyAllowlistChangedLocked();
                }
            }
        };
        this.mContext = context;
        this.mHandler = new com.android.server.power.LowPowerStandbyController.LowPowerStandbyHandler(looper);
        this.mClock = clock;
        this.mSettingsObserver = new com.android.server.power.LowPowerStandbyController.SettingsObserver(this.mHandler);
        this.mDeviceConfig = deviceConfigWrapper;
        this.mActivityManager = supplier;
        this.mPolicyFile = file;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void systemReady() {
        android.content.res.Resources resources = this.mContext.getResources();
        synchronized (this.mLock) {
            try {
                this.mSupportedConfig = resources.getBoolean(android.R.bool.config_lockDayNightMode);
                if (this.mSupportedConfig) {
                    java.util.Iterator<android.content.pm.PackageInfo> it = this.mContext.getPackageManager().getPackagesHoldingPermissions(new java.lang.String[]{"android.permission.MANAGE_LOW_POWER_STANDBY"}, 1048576).iterator();
                    while (it.hasNext()) {
                        this.mLowPowerStandbyManagingPackages.add(it.next().packageName);
                    }
                    this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
                    this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
                    this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
                    this.mStandbyTimeoutConfig = resources.getInteger(android.R.integer.config_lowBatteryCloseWarningBump);
                    this.mEnabledByDefaultConfig = resources.getBoolean(android.R.bool.config_localDisplaysMirrorContent);
                    this.mIsInteractive = this.mPowerManager.isInteractive();
                    this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("low_power_standby_enabled"), false, this.mSettingsObserver, -1);
                    this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("low_power_standby_active_during_maintenance"), false, this.mSettingsObserver, -1);
                    this.mDeviceConfig.registerPropertyUpdateListener(this.mContext.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda3
                        public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                            com.android.server.power.LowPowerStandbyController.this.lambda$systemReady$1(properties);
                        }
                    });
                    this.mEnableCustomPolicy = this.mDeviceConfig.enableCustomPolicy();
                    this.mEnableStandbyPorts = this.mDeviceConfig.enableStandbyPorts();
                    if (this.mEnableCustomPolicy) {
                        this.mPolicy = loadPolicy();
                    } else {
                        this.mPolicy = DEFAULT_POLICY;
                    }
                    initSettingsLocked();
                    updateSettingsLocked();
                    if (this.mIsEnabled) {
                        registerListeners();
                    }
                    com.android.server.LocalServices.addService(com.android.server.power.LowPowerStandbyControllerInternal.class, this.mLocalService);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$1(android.provider.DeviceConfig.Properties properties) {
        onDeviceConfigFlagsChanged();
    }

    private void onDeviceConfigFlagsChanged() {
        synchronized (this.mLock) {
            try {
                boolean enableCustomPolicy = this.mDeviceConfig.enableCustomPolicy();
                if (this.mEnableCustomPolicy != enableCustomPolicy) {
                    enqueueNotifyPolicyChangedLocked();
                    enqueueNotifyAllowlistChangedLocked();
                    this.mEnableCustomPolicy = enableCustomPolicy;
                }
                this.mEnableStandbyPorts = this.mDeviceConfig.enableStandbyPorts();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void initSettingsLocked() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mSupportedConfig && android.provider.Settings.Global.getInt(contentResolver, "low_power_standby_enabled", -1) == -1) {
            android.provider.Settings.Global.putInt(contentResolver, "low_power_standby_enabled", this.mEnabledByDefaultConfig ? 1 : 0);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateSettingsLocked() {
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mIsEnabled = this.mSupportedConfig && android.provider.Settings.Global.getInt(contentResolver, "low_power_standby_enabled", this.mEnabledByDefaultConfig ? 1 : 0) != 0;
        this.mActiveDuringMaintenance = android.provider.Settings.Global.getInt(contentResolver, "low_power_standby_active_during_maintenance", 0) != 0;
        updateActiveLocked();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @android.annotation.Nullable
    private android.os.PowerManager.LowPowerStandbyPolicy loadPolicy() {
        android.util.AtomicFile policyFile = getPolicyFile();
        if (!policyFile.exists()) {
            return null;
        }
        try {
            try {
                java.io.FileInputStream openRead = policyFile.openRead();
                try {
                    android.util.ArraySet arraySet = new android.util.ArraySet();
                    android.util.ArraySet arraySet2 = new android.util.ArraySet();
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                    java.lang.String str = null;
                    int i = 0;
                    while (true) {
                        int next = resolvePullParser.next();
                        char c = 1;
                        if (next == 1) {
                            android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy = new android.os.PowerManager.LowPowerStandbyPolicy(str, arraySet, i, arraySet2);
                            if (openRead != null) {
                                openRead.close();
                            }
                            return lowPowerStandbyPolicy;
                        }
                        if (next == 2) {
                            int depth = resolvePullParser.getDepth();
                            java.lang.String name = resolvePullParser.getName();
                            if (depth != 1) {
                                switch (name.hashCode()) {
                                    case -1618432855:
                                        if (name.equals(TAG_IDENTIFIER)) {
                                            c = 0;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case -764820798:
                                        if (name.equals(TAG_ALLOWED_FEATURES)) {
                                            c = 3;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 1342665610:
                                        if (name.equals(TAG_ALLOWED_REASONS)) {
                                            c = 2;
                                            break;
                                        }
                                        c = 65535;
                                        break;
                                    case 2046809176:
                                        if (name.equals(TAG_EXEMPT_PACKAGE)) {
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
                                        str = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                                        break;
                                    case 1:
                                        arraySet.add(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE));
                                        break;
                                    case 2:
                                        i = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                                        break;
                                    case 3:
                                        arraySet2.add(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE));
                                        break;
                                    default:
                                        android.util.Slog.e(TAG, "Invalid tag: " + name);
                                        break;
                                }
                            } else if (!TAG_ROOT.equals(name)) {
                                android.util.Slog.e(TAG, "Invalid root tag: " + name);
                                if (openRead != null) {
                                    openRead.close();
                                }
                                return null;
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException | java.lang.IllegalArgumentException | java.lang.NullPointerException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.e(TAG, "Failed to read policy file " + policyFile.getBaseFile(), e);
                return null;
            }
        } catch (java.io.FileNotFoundException e2) {
            return null;
        }
    }

    static void writeTagValue(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, java.lang.String str2) throws java.io.IOException {
        if (android.text.TextUtils.isEmpty(str2)) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_VALUE, str2);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    static void writeTagValue(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, int i) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, str);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_VALUE, i);
        typedXmlSerializer.endTag((java.lang.String) null, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: savePolicy, reason: merged with bridge method [inline-methods] */
    public void lambda$enqueueSavePolicy$2(@android.annotation.Nullable android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        java.io.FileOutputStream startWrite;
        android.util.AtomicFile policyFile = getPolicyFile();
        if (lowPowerStandbyPolicy == null) {
            policyFile.delete();
            return;
        }
        java.io.FileOutputStream fileOutputStream = null;
        try {
            policyFile.getBaseFile().mkdirs();
            startWrite = policyFile.startWrite();
        } catch (java.io.IOException e) {
            e = e;
        }
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, TAG_ROOT);
            writeTagValue(resolveSerializer, TAG_IDENTIFIER, lowPowerStandbyPolicy.getIdentifier());
            java.util.Iterator it = lowPowerStandbyPolicy.getExemptPackages().iterator();
            while (it.hasNext()) {
                writeTagValue(resolveSerializer, TAG_EXEMPT_PACKAGE, (java.lang.String) it.next());
            }
            writeTagValue(resolveSerializer, TAG_ALLOWED_REASONS, lowPowerStandbyPolicy.getAllowedReasons());
            java.util.Iterator it2 = lowPowerStandbyPolicy.getAllowedFeatures().iterator();
            while (it2.hasNext()) {
                writeTagValue(resolveSerializer, TAG_ALLOWED_FEATURES, (java.lang.String) it2.next());
            }
            resolveSerializer.endTag((java.lang.String) null, TAG_ROOT);
            resolveSerializer.endDocument();
            policyFile.finishWrite(startWrite);
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            android.util.Slog.e(TAG, "Failed to write policy to file " + policyFile.getBaseFile(), e);
            policyFile.failWrite(fileOutputStream);
        }
    }

    private void enqueueSavePolicy(@android.annotation.Nullable final android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.LowPowerStandbyController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.LowPowerStandbyController.this.lambda$enqueueSavePolicy$2(lowPowerStandbyPolicy);
            }
        });
    }

    private android.util.AtomicFile getPolicyFile() {
        return new android.util.AtomicFile(this.mPolicyFile);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateActiveLocked() {
        boolean z = false;
        boolean z2 = this.mClock.elapsedRealtime() - this.mLastInteractiveTimeElapsed >= ((long) this.mStandbyTimeoutConfig);
        boolean z3 = this.mIdleSinceNonInteractive && !this.mIsDeviceIdle;
        if (this.mForceActive || (this.mIsEnabled && !this.mIsInteractive && z2 && (!z3 || this.mActiveDuringMaintenance))) {
            z = true;
        }
        if (this.mIsActive != z) {
            this.mIsActive = z;
            enqueueNotifyActiveChangedLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNonInteractive() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        synchronized (this.mLock) {
            try {
                this.mIsInteractive = false;
                this.mIsDeviceIdle = false;
                this.mLastInteractiveTimeElapsed = elapsedRealtime;
                if (this.mStandbyTimeoutConfig > 0) {
                    scheduleStandbyTimeoutAlarmLocked();
                }
                updateActiveLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInteractive() {
        synchronized (this.mLock) {
            cancelStandbyTimeoutAlarmLocked();
            this.mIsInteractive = true;
            this.mIsDeviceIdle = false;
            this.mIdleSinceNonInteractive = false;
            updateActiveLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void scheduleStandbyTimeoutAlarmLocked() {
        this.mAlarmManager.setExact(2, this.mClock.elapsedRealtime() + this.mStandbyTimeoutConfig, "LowPowerStandbyController.StandbyTimeout", this.mOnStandbyTimeoutExpired, this.mHandler);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void cancelStandbyTimeoutAlarmLocked() {
        this.mAlarmManager.cancel(this.mOnStandbyTimeoutExpired);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDeviceIdleModeChanged() {
        synchronized (this.mLock) {
            try {
                this.mIsDeviceIdle = this.mPowerManager.isDeviceIdleMode();
                this.mIdleSinceNonInteractive = this.mIdleSinceNonInteractive || this.mIsDeviceIdle;
                updateActiveLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onEnabledLocked() {
        if (this.mPowerManager.isInteractive()) {
            onInteractive();
        } else {
            onNonInteractive();
        }
        registerListeners();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onDisabledLocked() {
        cancelStandbyTimeoutAlarmLocked();
        unregisterListeners();
        updateActiveLocked();
    }

    @com.android.internal.annotations.VisibleForTesting
    void onSettingsChanged() {
        synchronized (this.mLock) {
            try {
                boolean z = this.mIsEnabled;
                updateSettingsLocked();
                if (this.mIsEnabled != z) {
                    if (this.mIsEnabled) {
                        onEnabledLocked();
                    } else {
                        onDisabledLocked();
                    }
                    notifyEnabledChangedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void registerListeners() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.setPriority(1000);
        this.mContext.registerReceiver(this.mPackageBroadcastReceiver, intentFilter2);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
        intentFilter3.addAction("android.intent.action.USER_ADDED");
        intentFilter3.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiver(this.mUserReceiver, intentFilter3, null, this.mHandler);
        ((com.android.server.PowerAllowlistInternal) com.android.server.LocalServices.getService(com.android.server.PowerAllowlistInternal.class)).registerTempAllowlistChangeListener(this.mTempAllowlistChangeListener);
        this.mPhoneCallServiceTracker.register();
    }

    private void unregisterListeners() {
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mPackageBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mUserReceiver);
        ((com.android.server.PowerAllowlistInternal) com.android.server.LocalServices.getService(com.android.server.PowerAllowlistInternal.class)).unregisterTempAllowlistChangeListener(this.mTempAllowlistChangeListener);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyEnabledChangedLocked() {
        sendExplicitBroadcast("android.os.action.LOW_POWER_STANDBY_ENABLED_CHANGED");
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enqueueNotifyPolicyChangedLocked() {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(3, getPolicy()), this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPolicyChanged(android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        sendExplicitBroadcast("android.os.action.LOW_POWER_STANDBY_POLICY_CHANGED");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStandbyTimeoutExpired() {
        synchronized (this.mLock) {
            updateActiveLocked();
        }
    }

    private void sendExplicitBroadcast(java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(str);
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
        android.content.Intent intent2 = new android.content.Intent(str);
        intent2.addFlags(268435456);
        for (java.lang.String str2 : this.mLowPowerStandbyManagingPackages) {
            android.content.Intent intent3 = new android.content.Intent(intent2);
            intent3.setPackage(str2);
            this.mContext.sendBroadcastAsUser(intent3, android.os.UserHandle.ALL, "android.permission.MANAGE_LOW_POWER_STANDBY");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enqueueNotifyActiveChangedLocked() {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(1, java.lang.Boolean.valueOf(this.mIsActive)), this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyActiveChanged(boolean z) {
        android.os.PowerManagerInternal powerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        com.android.server.net.NetworkPolicyManagerInternal networkPolicyManagerInternal = (com.android.server.net.NetworkPolicyManagerInternal) com.android.server.LocalServices.getService(com.android.server.net.NetworkPolicyManagerInternal.class);
        powerManagerInternal.setLowPowerStandbyActive(z);
        networkPolicyManagerInternal.setLowPowerStandbyActive(z);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isActive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsActive;
        }
        return z;
    }

    boolean isSupported() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSupportedConfig;
        }
        return z;
    }

    boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mSupportedConfig && this.mIsEnabled;
            } finally {
            }
        }
        return z;
    }

    void setEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mSupportedConfig) {
                    android.util.Slog.w(TAG, "Low Power Standby cannot be enabled because it is not supported on this device");
                } else {
                    android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "low_power_standby_enabled", z ? 1 : 0);
                    onSettingsChanged();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void setActiveDuringMaintenance(boolean z) {
        synchronized (this.mLock) {
            try {
                if (!this.mSupportedConfig) {
                    android.util.Slog.w(TAG, "Low Power Standby settings cannot be changed because it is not supported on this device");
                } else {
                    android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "low_power_standby_active_during_maintenance", z ? 1 : 0);
                    onSettingsChanged();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void forceActive(boolean z) {
        synchronized (this.mLock) {
            this.mForceActive = z;
            updateActiveLocked();
        }
    }

    void setPolicy(@android.annotation.Nullable android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        synchronized (this.mLock) {
            try {
                if (!this.mSupportedConfig) {
                    android.util.Slog.w(TAG, "Low Power Standby policy cannot be changed because it is not supported on this device");
                    return;
                }
                if (!this.mEnableCustomPolicy) {
                    android.util.Slog.d(TAG, "Custom policies are not enabled.");
                    return;
                }
                if (java.util.Objects.equals(this.mPolicy, lowPowerStandbyPolicy)) {
                    return;
                }
                boolean policyChangeAffectsAllowlistLocked = policyChangeAffectsAllowlistLocked(this.mPolicy, lowPowerStandbyPolicy);
                this.mPolicy = lowPowerStandbyPolicy;
                enqueueSavePolicy(this.mPolicy);
                if (policyChangeAffectsAllowlistLocked) {
                    enqueueNotifyAllowlistChangedLocked();
                }
                enqueueNotifyPolicyChangedLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    android.os.PowerManager.LowPowerStandbyPolicy getPolicy() {
        synchronized (this.mLock) {
            try {
                if (!this.mSupportedConfig) {
                    return null;
                }
                if (this.mEnableCustomPolicy) {
                    return policyOrDefault(this.mPolicy);
                }
                return DEFAULT_POLICY;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    private android.os.PowerManager.LowPowerStandbyPolicy policyOrDefault(@android.annotation.Nullable android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy) {
        if (lowPowerStandbyPolicy == null) {
            return DEFAULT_POLICY;
        }
        return lowPowerStandbyPolicy;
    }

    boolean isPackageExempt(int i) {
        synchronized (this.mLock) {
            try {
                if (!isEnabled()) {
                    return true;
                }
                return getExemptPackageAppIdsLocked().contains(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(i)));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isAllowed(int i) {
        synchronized (this.mLock) {
            try {
                boolean z = true;
                if (!isEnabled()) {
                    return true;
                }
                if ((i & getPolicy().getAllowedReasons()) == 0) {
                    z = false;
                }
                return z;
            } finally {
            }
        }
    }

    boolean isAllowed(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                boolean z = true;
                if (!this.mSupportedConfig) {
                    return true;
                }
                if (isEnabled() && !getPolicy().getAllowedFeatures().contains(str)) {
                    z = false;
                }
                return z;
            } finally {
            }
        }
    }

    private int findIndexOfStandbyPorts(@android.annotation.NonNull android.os.IBinder iBinder) {
        for (int i = 0; i < this.mStandbyPortLocks.size(); i++) {
            if (this.mStandbyPortLocks.get(i).getToken() == iBinder) {
                return i;
            }
        }
        return -1;
    }

    void acquireStandbyPorts(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> list) {
        validatePorts(list);
        com.android.server.power.LowPowerStandbyController.StandbyPortsLock standbyPortsLock = new com.android.server.power.LowPowerStandbyController.StandbyPortsLock(iBinder, i, list);
        synchronized (this.mLock) {
            try {
                if (findIndexOfStandbyPorts(iBinder) != -1) {
                    return;
                }
                if (standbyPortsLock.linkToDeath()) {
                    this.mStandbyPortLocks.add(standbyPortsLock);
                    if (this.mEnableStandbyPorts && isEnabled() && isPackageExempt(i)) {
                        enqueueNotifyStandbyPortsChangedLocked();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void validatePorts(@android.annotation.NonNull java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> list) {
        java.util.Iterator<android.os.PowerManager.LowPowerStandbyPortDescription> it = list.iterator();
        while (it.hasNext()) {
            int portNumber = it.next().getPortNumber();
            if (portNumber < 0 || portNumber > 65535) {
                throw new java.lang.IllegalArgumentException("port out of range:" + portNumber);
            }
        }
    }

    void releaseStandbyPorts(@android.annotation.NonNull android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                int findIndexOfStandbyPorts = findIndexOfStandbyPorts(iBinder);
                if (findIndexOfStandbyPorts == -1) {
                    return;
                }
                com.android.server.power.LowPowerStandbyController.StandbyPortsLock remove = this.mStandbyPortLocks.remove(findIndexOfStandbyPorts);
                remove.unlinkToDeath();
                if (this.mEnableStandbyPorts && isEnabled() && isPackageExempt(remove.getUid())) {
                    enqueueNotifyStandbyPortsChangedLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> getActiveStandbyPorts() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                if (!isEnabled() || !this.mEnableStandbyPorts) {
                    return arrayList;
                }
                java.util.List<java.lang.Integer> exemptPackageAppIdsLocked = getExemptPackageAppIdsLocked();
                for (com.android.server.power.LowPowerStandbyController.StandbyPortsLock standbyPortsLock : this.mStandbyPortLocks) {
                    if (exemptPackageAppIdsLocked.contains(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(standbyPortsLock.getUid())))) {
                        arrayList.addAll(standbyPortsLock.getPorts());
                    }
                }
                return arrayList;
            } finally {
            }
        }
    }

    private boolean policyChangeAffectsAllowlistLocked(@android.annotation.Nullable android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy, @android.annotation.Nullable android.os.PowerManager.LowPowerStandbyPolicy lowPowerStandbyPolicy2) {
        android.os.PowerManager.LowPowerStandbyPolicy policyOrDefault = policyOrDefault(lowPowerStandbyPolicy);
        android.os.PowerManager.LowPowerStandbyPolicy policyOrDefault2 = policyOrDefault(lowPowerStandbyPolicy2);
        int i = 0;
        for (int i2 = 0; i2 < this.mUidAllowedReasons.size(); i2++) {
            i |= this.mUidAllowedReasons.valueAt(i2);
        }
        int allowedReasons = policyOrDefault.getAllowedReasons() ^ policyOrDefault2.getAllowedReasons();
        boolean z = !policyOrDefault.getExemptPackages().equals(policyOrDefault2.getExemptPackages());
        if ((allowedReasons & i) == 0 && !z) {
            return false;
        }
        return true;
    }

    void dump(java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println();
        indentingPrintWriter.println("Low Power Standby Controller:");
        indentingPrintWriter.increaseIndent();
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.print("mIsActive=");
                indentingPrintWriter.println(this.mIsActive);
                indentingPrintWriter.print("mIsEnabled=");
                indentingPrintWriter.println(this.mIsEnabled);
                indentingPrintWriter.print("mSupportedConfig=");
                indentingPrintWriter.println(this.mSupportedConfig);
                indentingPrintWriter.print("mEnabledByDefaultConfig=");
                indentingPrintWriter.println(this.mEnabledByDefaultConfig);
                indentingPrintWriter.print("mStandbyTimeoutConfig=");
                indentingPrintWriter.println(this.mStandbyTimeoutConfig);
                indentingPrintWriter.print("mEnableCustomPolicy=");
                indentingPrintWriter.println(this.mEnableCustomPolicy);
                if (this.mIsActive || this.mIsEnabled) {
                    indentingPrintWriter.print("mIsInteractive=");
                    indentingPrintWriter.println(this.mIsInteractive);
                    indentingPrintWriter.print("mLastInteractiveTime=");
                    indentingPrintWriter.println(this.mLastInteractiveTimeElapsed);
                    indentingPrintWriter.print("mIdleSinceNonInteractive=");
                    indentingPrintWriter.println(this.mIdleSinceNonInteractive);
                    indentingPrintWriter.print("mIsDeviceIdle=");
                    indentingPrintWriter.println(this.mIsDeviceIdle);
                }
                int[] allowlistUidsLocked = getAllowlistUidsLocked();
                indentingPrintWriter.print("Allowed UIDs=");
                indentingPrintWriter.println(java.util.Arrays.toString(allowlistUidsLocked));
                android.os.PowerManager.LowPowerStandbyPolicy policy = getPolicy();
                if (policy != null) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("mPolicy:");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.print("mIdentifier=");
                    indentingPrintWriter.println(policy.getIdentifier());
                    indentingPrintWriter.print("mExemptPackages=");
                    indentingPrintWriter.println(java.lang.String.join(",", policy.getExemptPackages()));
                    indentingPrintWriter.print("mAllowedReasons=");
                    indentingPrintWriter.println(android.os.PowerManager.lowPowerStandbyAllowedReasonsToString(policy.getAllowedReasons()));
                    indentingPrintWriter.print("mAllowedFeatures=");
                    indentingPrintWriter.println(java.lang.String.join(",", policy.getAllowedFeatures()));
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("UID allowed reasons:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mUidAllowedReasons.size(); i++) {
                    if (this.mUidAllowedReasons.valueAt(i) > 0) {
                        indentingPrintWriter.print(this.mUidAllowedReasons.keyAt(i));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(android.os.PowerManager.lowPowerStandbyAllowedReasonsToString(this.mUidAllowedReasons.valueAt(i)));
                    }
                }
                indentingPrintWriter.decreaseIndent();
                java.util.List<android.os.PowerManager.LowPowerStandbyPortDescription> activeStandbyPorts = getActiveStandbyPorts();
                if (!activeStandbyPorts.isEmpty()) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Active standby ports locks:");
                    indentingPrintWriter.increaseIndent();
                    java.util.Iterator<android.os.PowerManager.LowPowerStandbyPortDescription> it = activeStandbyPorts.iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.print(it.next().toString());
                    }
                    indentingPrintWriter.decreaseIndent();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            try {
                long start = protoOutputStream.start(j);
                protoOutputStream.write(1133871366145L, this.mIsActive);
                protoOutputStream.write(1133871366146L, this.mIsEnabled);
                protoOutputStream.write(1133871366147L, this.mSupportedConfig);
                protoOutputStream.write(1133871366148L, this.mEnabledByDefaultConfig);
                protoOutputStream.write(1133871366149L, this.mIsInteractive);
                protoOutputStream.write(1112396529670L, this.mLastInteractiveTimeElapsed);
                protoOutputStream.write(1120986464263L, this.mStandbyTimeoutConfig);
                protoOutputStream.write(1133871366152L, this.mIdleSinceNonInteractive);
                protoOutputStream.write(1133871366153L, this.mIsDeviceIdle);
                for (int i : getAllowlistUidsLocked()) {
                    protoOutputStream.write(2220498092042L, i);
                }
                android.os.PowerManager.LowPowerStandbyPolicy policy = getPolicy();
                if (policy != null) {
                    long start2 = protoOutputStream.start(1146756268043L);
                    protoOutputStream.write(1138166333441L, policy.getIdentifier());
                    java.util.Iterator it = policy.getExemptPackages().iterator();
                    while (it.hasNext()) {
                        protoOutputStream.write(2237677961218L, (java.lang.String) it.next());
                    }
                    protoOutputStream.write(1120986464259L, policy.getAllowedReasons());
                    java.util.Iterator it2 = policy.getAllowedFeatures().iterator();
                    while (it2.hasNext()) {
                        protoOutputStream.write(2237677961220L, (java.lang.String) it2.next());
                    }
                    protoOutputStream.end(start2);
                }
                protoOutputStream.end(start);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class LowPowerStandbyHandler extends android.os.Handler {
        LowPowerStandbyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    com.android.server.power.LowPowerStandbyController.this.onStandbyTimeoutExpired();
                    break;
                case 1:
                    com.android.server.power.LowPowerStandbyController.this.notifyActiveChanged(((java.lang.Boolean) message.obj).booleanValue());
                    break;
                case 2:
                    com.android.server.power.LowPowerStandbyController.this.notifyAllowlistChanged((int[]) message.obj);
                    break;
                case 3:
                    com.android.server.power.LowPowerStandbyController.this.notifyPolicyChanged((android.os.PowerManager.LowPowerStandbyPolicy) message.obj);
                    break;
                case 4:
                    com.android.server.power.LowPowerStandbyController.this.mPhoneCallServiceTracker.foregroundServiceStateChanged(message.arg1);
                    break;
                case 5:
                    com.android.server.power.LowPowerStandbyController.this.notifyStandbyPortsChanged();
                    break;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasAllowedReasonLocked(int i, int i2) {
        return (this.mUidAllowedReasons.get(i) & i2) != 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean addAllowedReasonLocked(int i, int i2) {
        int i3 = this.mUidAllowedReasons.get(i);
        int i4 = i2 | i3;
        this.mUidAllowedReasons.put(i, i4);
        return i3 != i4;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean removeAllowedReasonLocked(int i, int i2) {
        int i3 = this.mUidAllowedReasons.get(i);
        if (i3 == 0) {
            return false;
        }
        int i4 = (~i2) & i3;
        if (i4 == 0) {
            this.mUidAllowedReasons.removeAt(this.mUidAllowedReasons.indexOfKey(i));
        } else {
            this.mUidAllowedReasons.put(i, i4);
        }
        return i3 != i4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addToAllowlistInternal(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mSupportedConfig) {
                    if (i2 != 0 && !hasAllowedReasonLocked(i, i2)) {
                        addAllowedReasonLocked(i, i2);
                        if ((getPolicy().getAllowedReasons() & i2) != 0) {
                            enqueueNotifyAllowlistChangedLocked();
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFromAllowlistInternal(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mSupportedConfig) {
                    if (i2 != 0 && hasAllowedReasonLocked(i, i2)) {
                        removeAllowedReasonLocked(i, i2);
                        if ((getPolicy().getAllowedReasons() & i2) != 0) {
                            enqueueNotifyAllowlistChangedLocked();
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<java.lang.Integer> getExemptPackageAppIdsLocked() {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.os.PowerManager.LowPowerStandbyPolicy policy = getPolicy();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (policy == null) {
            return arrayList;
        }
        java.util.Iterator it = policy.getExemptPackages().iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(packageManager.getPackageUid((java.lang.String) it.next(), android.content.pm.PackageManager.PackageInfoFlags.of(0L)))));
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] getAllowlistUidsLocked() {
        java.util.List userHandles = ((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).getUserHandles(true);
        android.util.ArraySet arraySet = new android.util.ArraySet(this.mUidAllowedReasons.size());
        android.os.PowerManager.LowPowerStandbyPolicy policy = getPolicy();
        if (policy == null) {
            return new int[0];
        }
        int allowedReasons = policy.getAllowedReasons();
        for (int i = 0; i < this.mUidAllowedReasons.size(); i++) {
            java.lang.Integer valueOf = java.lang.Integer.valueOf(this.mUidAllowedReasons.keyAt(i));
            if ((this.mUidAllowedReasons.valueAt(i) & allowedReasons) != 0) {
                arraySet.add(valueOf);
            }
        }
        java.util.Iterator<java.lang.Integer> it = getExemptPackageAppIdsLocked().iterator();
        while (it.hasNext()) {
            for (int i2 : uidsForAppId(it.next().intValue(), userHandles)) {
                arraySet.add(java.lang.Integer.valueOf(i2));
            }
        }
        int[] iArr = new int[arraySet.size()];
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
            iArr[i3] = ((java.lang.Integer) arraySet.valueAt(i3)).intValue();
        }
        java.util.Arrays.sort(iArr);
        return iArr;
    }

    private int[] uidsForAppId(int i, java.util.List<android.os.UserHandle> list) {
        int appId = android.os.UserHandle.getAppId(i);
        int[] iArr = new int[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            iArr[i2] = list.get(i2).getUid(appId);
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void enqueueNotifyAllowlistChangedLocked() {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(2, getAllowlistUidsLocked()), this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAllowlistChanged(int[] iArr) {
        android.os.PowerManagerInternal powerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
        com.android.server.net.NetworkPolicyManagerInternal networkPolicyManagerInternal = (com.android.server.net.NetworkPolicyManagerInternal) com.android.server.LocalServices.getService(com.android.server.net.NetworkPolicyManagerInternal.class);
        powerManagerInternal.setLowPowerStandbyAllowlist(iArr);
        networkPolicyManagerInternal.setLowPowerStandbyAllowlist(iArr);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enqueueNotifyStandbyPortsChangedLocked() {
        this.mHandler.sendMessageAtTime(this.mHandler.obtainMessage(5), this.mClock.uptimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyStandbyPortsChanged() {
        android.content.Intent intent = new android.content.Intent("android.os.action.LOW_POWER_STANDBY_PORTS_CHANGED");
        intent.addFlags(268435456);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.MANAGE_LOW_POWER_STANDBY");
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class DeviceConfigWrapper {
        public static final java.lang.String FEATURE_FLAG_ENABLE_POLICY = "enable_policy";
        public static final java.lang.String FEATURE_FLAG_ENABLE_STANDBY_PORTS = "enable_standby_ports";
        public static final java.lang.String NAMESPACE = "low_power_standby";

        public boolean enableCustomPolicy() {
            return android.provider.DeviceConfig.getBoolean(NAMESPACE, FEATURE_FLAG_ENABLE_POLICY, true);
        }

        public boolean enableStandbyPorts() {
            return android.provider.DeviceConfig.getBoolean(NAMESPACE, FEATURE_FLAG_ENABLE_STANDBY_PORTS, true);
        }

        public void registerPropertyUpdateListener(@android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
            android.provider.DeviceConfig.addOnPropertiesChangedListener(NAMESPACE, executor, onPropertiesChangedListener);
        }
    }

    private final class LocalService extends com.android.server.power.LowPowerStandbyControllerInternal {
        private LocalService() {
        }

        @Override // com.android.server.power.LowPowerStandbyControllerInternal
        public void addToAllowlist(int i, int i2) {
            com.android.server.power.LowPowerStandbyController.this.addToAllowlistInternal(i, i2);
        }

        @Override // com.android.server.power.LowPowerStandbyControllerInternal
        public void removeFromAllowlist(int i, int i2) {
            com.android.server.power.LowPowerStandbyController.this.removeFromAllowlistInternal(i, i2);
        }
    }

    private final class SettingsObserver extends android.database.ContentObserver {
        SettingsObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            com.android.server.power.LowPowerStandbyController.this.onSettingsChanged();
        }
    }

    final class TempAllowlistChangeListener implements com.android.server.PowerAllowlistInternal.TempAllowlistChangeListener {
        TempAllowlistChangeListener() {
        }

        public void onAppAdded(int i) {
            com.android.server.power.LowPowerStandbyController.this.addToAllowlistInternal(i, 2);
        }

        public void onAppRemoved(int i) {
            com.android.server.power.LowPowerStandbyController.this.removeFromAllowlistInternal(i, 2);
        }
    }

    final class PhoneCallServiceTracker extends android.app.IForegroundServiceObserver.Stub {
        private boolean mRegistered = false;
        private final android.util.SparseBooleanArray mUidsWithPhoneCallService = new android.util.SparseBooleanArray();

        PhoneCallServiceTracker() {
        }

        public void register() {
            if (this.mRegistered) {
                return;
            }
            try {
                ((android.app.IActivityManager) com.android.server.power.LowPowerStandbyController.this.mActivityManager.get()).registerForegroundServiceObserver(this);
                this.mRegistered = true;
            } catch (android.os.RemoteException e) {
            }
        }

        public void onForegroundStateChanged(android.os.IBinder iBinder, java.lang.String str, int i, boolean z) {
            try {
                com.android.server.power.LowPowerStandbyController.this.mHandler.sendMessageAtTime(com.android.server.power.LowPowerStandbyController.this.mHandler.obtainMessage(4, com.android.server.power.LowPowerStandbyController.this.mContext.getPackageManager().getPackageUidAsUser(str, i), 0), com.android.server.power.LowPowerStandbyController.this.mClock.uptimeMillis());
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }

        public void foregroundServiceStateChanged(int i) {
            boolean z = this.mUidsWithPhoneCallService.get(i);
            boolean hasRunningForegroundService = com.android.server.power.LowPowerStandbyController.this.mActivityManagerInternal.hasRunningForegroundService(i, 4);
            if (hasRunningForegroundService == z) {
                return;
            }
            if (hasRunningForegroundService) {
                this.mUidsWithPhoneCallService.append(i, true);
                uidStartedPhoneCallService(i);
            } else {
                this.mUidsWithPhoneCallService.delete(i);
                uidStoppedPhoneCallService(i);
            }
        }

        private void uidStartedPhoneCallService(int i) {
            com.android.server.power.LowPowerStandbyController.this.addToAllowlistInternal(i, 4);
        }

        private void uidStoppedPhoneCallService(int i) {
            com.android.server.power.LowPowerStandbyController.this.removeFromAllowlistInternal(i, 4);
        }
    }
}
