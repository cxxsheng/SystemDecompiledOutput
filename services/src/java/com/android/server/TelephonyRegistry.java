package com.android.server;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes.dex */
public class TelephonyRegistry extends com.android.internal.telephony.ITelephonyRegistry.Stub {
    private static final java.lang.String ACTION_ANY_DATA_CONNECTION_STATE_CHANGED = "android.intent.action.ANY_DATA_STATE";
    public static final java.lang.String ACTION_SIGNAL_STRENGTH_CHANGED = "android.intent.action.SIG_STR";
    private static final java.lang.String ACTION_SUBSCRIPTION_PHONE_STATE_CHANGED = "android.intent.action.SUBSCRIPTION_PHONE_STATE";
    private static final boolean DBG = false;
    private static final boolean DBG_LOC = false;
    private static final long DISPLAY_INFO_NR_ADVANCED_SUPPORTED = 181658987;
    private static final int MSG_UPDATE_DEFAULT_SUB = 2;
    private static final int MSG_USER_SWITCHED = 1;
    private static final java.lang.String PHONE_CONSTANTS_DATA_APN_KEY = "apn";
    private static final java.lang.String PHONE_CONSTANTS_DATA_APN_TYPE_KEY = "apnType";
    private static final java.lang.String PHONE_CONSTANTS_SLOT_KEY = "slot";
    private static final java.lang.String PHONE_CONSTANTS_STATE_KEY = "state";
    private static final java.lang.String PHONE_CONSTANTS_SUBSCRIPTION_KEY = "subscription";
    private static final long REQUIRE_READ_PHONE_STATE_PERMISSION_FOR_ACTIVE_DATA_SUB_ID = 182478738;
    private static final long REQUIRE_READ_PHONE_STATE_PERMISSION_FOR_CELL_INFO = 184323934;
    private static final long REQUIRE_READ_PHONE_STATE_PERMISSION_FOR_DISPLAY_INFO = 183164979;
    private static final java.lang.String TAG = "TelephonyRegistry";
    private static final boolean VDBG = false;
    private int[] mAllowedNetworkTypeReason;
    private long[] mAllowedNetworkTypeValue;
    private final android.app.AppOpsManager mAppOps;
    private int[] mBackgroundCallState;
    private java.util.List<android.telephony.BarringInfo> mBarringInfo;
    private int[] mCallDisconnectCause;
    private boolean[] mCallForwarding;
    private java.lang.String[] mCallIncomingNumber;
    private int[] mCallNetworkType;
    private int[] mCallPreciseDisconnectCause;
    private android.telephony.CallQuality[] mCallQuality;
    private int[] mCallState;
    private java.util.ArrayList<java.util.List<android.telephony.CallState>> mCallStateLists;
    private boolean[] mCarrierNetworkChangeState;

    @android.annotation.NonNull
    private java.util.List<android.util.Pair<java.util.List<java.lang.String>, int[]>> mCarrierPrivilegeStates;

    @android.annotation.NonNull
    private java.util.List<android.util.Pair<java.lang.String, java.lang.Integer>> mCarrierServiceStates;
    private android.telephony.CellIdentity[] mCellIdentity;
    private java.util.ArrayList<java.util.List<android.telephony.CellInfo>> mCellInfo;
    private com.android.server.TelephonyRegistry.ConfigurationProvider mConfigurationProvider;
    private final android.content.Context mContext;
    private int[] mDataActivationState;
    private int[] mDataActivity;
    private int[] mDataConnectionNetworkType;
    private int[] mDataConnectionState;
    private int[] mDataEnabledReason;
    private int[] mECBMReason;
    private boolean[] mECBMStarted;
    private java.util.Map<java.lang.Integer, java.util.List<android.telephony.emergency.EmergencyNumber>> mEmergencyNumberList;
    private int[] mForegroundCallState;
    private java.util.List<android.telephony.ims.ImsReasonInfo> mImsReasonInfo;
    private boolean[] mIsDataEnabled;
    private java.util.List<java.util.List<android.telephony.LinkCapacityEstimate>> mLinkCapacityEstimateLists;
    private java.util.List<android.util.SparseArray<android.telephony.ims.MediaQualityStatus>> mMediaQualityStatus;
    private boolean[] mMessageWaiting;
    private int mNumPhones;
    private android.telephony.emergency.EmergencyNumber[] mOutgoingCallEmergencyNumber;
    private android.telephony.emergency.EmergencyNumber[] mOutgoingSmsEmergencyNumber;
    private java.util.List<java.util.List<android.telephony.PhysicalChannelConfig>> mPhysicalChannelConfigs;
    private android.telephony.PreciseCallState[] mPreciseCallState;
    private java.util.List<java.util.Map<android.util.Pair<java.lang.Integer, android.telephony.data.ApnSetting>, android.telephony.PreciseDataConnectionState>> mPreciseDataConnectionStates;
    private int[] mRingingCallState;
    private int[] mSCBMReason;
    private boolean[] mSCBMStarted;
    private android.telephony.ServiceState[] mServiceState;
    private android.telephony.SignalStrength[] mSignalStrength;
    private int[] mSrvccState;
    private android.telephony.TelephonyDisplayInfo[] mTelephonyDisplayInfos;
    private boolean[] mUserMobileDataState;
    private int[] mVoiceActivationState;
    private static final java.util.List<android.telephony.LinkCapacityEstimate> INVALID_LCE_LIST = new java.util.ArrayList(java.util.Arrays.asList(new android.telephony.LinkCapacityEstimate(2, -1, -1)));
    private static final java.util.Set<java.lang.Integer> REQUIRE_PRECISE_PHONE_STATE_PERMISSION = new java.util.HashSet();
    private final java.util.ArrayList<android.os.IBinder> mRemoveList = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.TelephonyRegistry.Record> mRecords = new java.util.ArrayList<>();
    private boolean mHasNotifySubscriptionInfoChangedOccurred = false;
    private boolean mHasNotifyOpportunisticSubscriptionInfoChangedOccurred = false;
    private int mDefaultSubId = -1;
    private int mDefaultPhoneId = -1;
    private android.telephony.PhoneCapability mPhoneCapability = null;
    private int mActiveDataSubId = -1;
    private int mRadioPowerState = 2;
    private final android.util.LocalLog mLocalLog = new android.util.LocalLog(200);
    private final android.util.LocalLog mListenLog = new android.util.LocalLog(200);
    private int[] mSimultaneousCellularCallingSubIds = new int[0];
    private final android.os.Handler mHandler = new android.os.Handler() { // from class: com.android.server.TelephonyRegistry.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    int activeModemCount = com.android.server.TelephonyRegistry.this.getTelephonyManager().getActiveModemCount();
                    for (int i = 0; i < activeModemCount; i++) {
                        int subscriptionId = android.telephony.SubscriptionManager.getSubscriptionId(i);
                        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
                            subscriptionId = Integer.MAX_VALUE;
                        }
                        com.android.server.TelephonyRegistry.this.notifyCellLocationForSubscriber(subscriptionId, com.android.server.TelephonyRegistry.this.mCellIdentity[i], true);
                    }
                    return;
                case 2:
                    int i2 = message.arg1;
                    int i3 = message.arg2;
                    synchronized (com.android.server.TelephonyRegistry.this.mRecords) {
                        try {
                            java.util.Iterator it = com.android.server.TelephonyRegistry.this.mRecords.iterator();
                            while (it.hasNext()) {
                                com.android.server.TelephonyRegistry.Record record = (com.android.server.TelephonyRegistry.Record) it.next();
                                if (record.subId == Integer.MAX_VALUE) {
                                    com.android.server.TelephonyRegistry.this.checkPossibleMissNotify(record, i2);
                                }
                            }
                            com.android.server.TelephonyRegistry.this.handleRemoveListLocked();
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    com.android.server.TelephonyRegistry.this.mDefaultSubId = i3;
                    com.android.server.TelephonyRegistry.this.mDefaultPhoneId = i2;
                    com.android.server.TelephonyRegistry.this.mLocalLog.log("Default subscription updated: mDefaultPhoneId=" + com.android.server.TelephonyRegistry.this.mDefaultPhoneId + ", mDefaultSubId=" + com.android.server.TelephonyRegistry.this.mDefaultSubId);
                    return;
                default:
                    return;
            }
        }
    };
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.TelephonyRegistry.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            if ("android.intent.action.USER_SWITCHED".equals(action)) {
                com.android.server.TelephonyRegistry.this.mHandler.sendMessage(com.android.server.TelephonyRegistry.this.mHandler.obtainMessage(1, intent.getIntExtra("android.intent.extra.user_handle", 0), 0));
                return;
            }
            if (!action.equals("android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED")) {
                if (action.equals("android.telephony.action.MULTI_SIM_CONFIG_CHANGED")) {
                    com.android.server.TelephonyRegistry.this.onMultiSimConfigChanged();
                    return;
                }
                return;
            }
            int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", android.telephony.SubscriptionManager.getDefaultSubscriptionId());
            int intExtra2 = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", com.android.server.TelephonyRegistry.this.getPhoneIdFromSubId(intExtra));
            if (com.android.server.TelephonyRegistry.this.validatePhoneId(intExtra2)) {
                if (intExtra != com.android.server.TelephonyRegistry.this.mDefaultSubId || intExtra2 != com.android.server.TelephonyRegistry.this.mDefaultPhoneId) {
                    com.android.server.TelephonyRegistry.this.mHandler.sendMessage(com.android.server.TelephonyRegistry.this.mHandler.obtainMessage(2, intExtra2, intExtra));
                }
            }
        }
    };
    private final com.android.internal.app.IBatteryStats mBatteryStats = com.android.server.am.BatteryStatsService.getService();

    private static class Record {
        android.os.IBinder binder;
        com.android.internal.telephony.IPhoneStateListener callback;
        int callerPid;
        int callerUid;
        java.lang.String callingFeatureId;
        java.lang.String callingPackage;
        com.android.internal.telephony.ICarrierConfigChangeListener carrierConfigChangeListener;
        com.android.internal.telephony.ICarrierPrivilegesCallback carrierPrivilegesCallback;
        android.content.Context context;
        com.android.server.TelephonyRegistry.TelephonyRegistryDeathRecipient deathRecipient;
        java.util.Set<java.lang.Integer> eventList;
        com.android.internal.telephony.IOnSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListenerCallback;
        com.android.internal.telephony.IOnSubscriptionsChangedListener onSubscriptionsChangedListenerCallback;
        int phoneId;
        boolean renounceCoarseLocationAccess;
        boolean renounceFineLocationAccess;
        int subId;

        private Record() {
            this.subId = -1;
            this.phoneId = -1;
        }

        boolean matchTelephonyCallbackEvent(int i) {
            return this.callback != null && this.eventList.contains(java.lang.Integer.valueOf(i));
        }

        boolean matchOnSubscriptionsChangedListener() {
            return this.onSubscriptionsChangedListenerCallback != null;
        }

        boolean matchOnOpportunisticSubscriptionsChangedListener() {
            return this.onOpportunisticSubscriptionsChangedListenerCallback != null;
        }

        boolean matchCarrierPrivilegesCallback() {
            return this.carrierPrivilegesCallback != null;
        }

        boolean matchCarrierConfigChangeListener() {
            return this.carrierConfigChangeListener != null;
        }

        boolean canReadCallLog() {
            try {
                return com.android.internal.telephony.TelephonyPermissions.checkReadCallLog(this.context, this.subId, this.callerPid, this.callerUid, this.callingPackage, this.callingFeatureId);
            } catch (java.lang.SecurityException e) {
                return false;
            }
        }

        public java.lang.String toString() {
            return "{callingPackage=" + com.android.server.TelephonyRegistry.pii(this.callingPackage) + " callerUid=" + this.callerUid + " binder=" + this.binder + " callback=" + this.callback + " onSubscriptionsChangedListenererCallback=" + this.onSubscriptionsChangedListenerCallback + " onOpportunisticSubscriptionsChangedListenererCallback=" + this.onOpportunisticSubscriptionsChangedListenerCallback + " carrierPrivilegesCallback=" + this.carrierPrivilegesCallback + " carrierConfigChangeListener=" + this.carrierConfigChangeListener + " subId=" + this.subId + " phoneId=" + this.phoneId + " events=" + this.eventList + "}";
        }
    }

    public static class ConfigurationProvider {
        public int getRegistrationLimit() {
            return ((java.lang.Integer) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda6
                public final java.lang.Object getOrThrow() {
                    java.lang.Integer lambda$getRegistrationLimit$0;
                    lambda$getRegistrationLimit$0 = com.android.server.TelephonyRegistry.ConfigurationProvider.lambda$getRegistrationLimit$0();
                    return lambda$getRegistrationLimit$0;
                }
            })).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Integer lambda$getRegistrationLimit$0() throws java.lang.Exception {
            return java.lang.Integer.valueOf(android.provider.DeviceConfig.getInt("telephony", "phone_state_listener_per_pid_registration_limit", 50));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Boolean lambda$isRegistrationLimitEnabledInPlatformCompat$1(int i) throws java.lang.Exception {
            return java.lang.Boolean.valueOf(android.app.compat.CompatChanges.isChangeEnabled(150880553L, i));
        }

        public boolean isRegistrationLimitEnabledInPlatformCompat(final int i) {
            return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda1
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$isRegistrationLimitEnabledInPlatformCompat$1;
                    lambda$isRegistrationLimitEnabledInPlatformCompat$1 = com.android.server.TelephonyRegistry.ConfigurationProvider.lambda$isRegistrationLimitEnabledInPlatformCompat$1(i);
                    return lambda$isRegistrationLimitEnabledInPlatformCompat$1;
                }
            })).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Boolean lambda$isCallStateReadPhoneStateEnforcedInPlatformCompat$2(java.lang.String str, android.os.UserHandle userHandle) throws java.lang.Exception {
            return java.lang.Boolean.valueOf(android.app.compat.CompatChanges.isChangeEnabled(157233955L, str, userHandle));
        }

        public boolean isCallStateReadPhoneStateEnforcedInPlatformCompat(final java.lang.String str, final android.os.UserHandle userHandle) {
            return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda3
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$isCallStateReadPhoneStateEnforcedInPlatformCompat$2;
                    lambda$isCallStateReadPhoneStateEnforcedInPlatformCompat$2 = com.android.server.TelephonyRegistry.ConfigurationProvider.lambda$isCallStateReadPhoneStateEnforcedInPlatformCompat$2(str, userHandle);
                    return lambda$isCallStateReadPhoneStateEnforcedInPlatformCompat$2;
                }
            })).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Boolean lambda$isActiveDataSubIdReadPhoneStateEnforcedInPlatformCompat$3(java.lang.String str, android.os.UserHandle userHandle) throws java.lang.Exception {
            return java.lang.Boolean.valueOf(android.app.compat.CompatChanges.isChangeEnabled(com.android.server.TelephonyRegistry.REQUIRE_READ_PHONE_STATE_PERMISSION_FOR_ACTIVE_DATA_SUB_ID, str, userHandle));
        }

        public boolean isActiveDataSubIdReadPhoneStateEnforcedInPlatformCompat(final java.lang.String str, final android.os.UserHandle userHandle) {
            return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda0
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$isActiveDataSubIdReadPhoneStateEnforcedInPlatformCompat$3;
                    lambda$isActiveDataSubIdReadPhoneStateEnforcedInPlatformCompat$3 = com.android.server.TelephonyRegistry.ConfigurationProvider.lambda$isActiveDataSubIdReadPhoneStateEnforcedInPlatformCompat$3(str, userHandle);
                    return lambda$isActiveDataSubIdReadPhoneStateEnforcedInPlatformCompat$3;
                }
            })).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Boolean lambda$isCellInfoReadPhoneStateEnforcedInPlatformCompat$4(java.lang.String str, android.os.UserHandle userHandle) throws java.lang.Exception {
            return java.lang.Boolean.valueOf(android.app.compat.CompatChanges.isChangeEnabled(com.android.server.TelephonyRegistry.REQUIRE_READ_PHONE_STATE_PERMISSION_FOR_CELL_INFO, str, userHandle));
        }

        public boolean isCellInfoReadPhoneStateEnforcedInPlatformCompat(final java.lang.String str, final android.os.UserHandle userHandle) {
            return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda4
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$isCellInfoReadPhoneStateEnforcedInPlatformCompat$4;
                    lambda$isCellInfoReadPhoneStateEnforcedInPlatformCompat$4 = com.android.server.TelephonyRegistry.ConfigurationProvider.lambda$isCellInfoReadPhoneStateEnforcedInPlatformCompat$4(str, userHandle);
                    return lambda$isCellInfoReadPhoneStateEnforcedInPlatformCompat$4;
                }
            })).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Boolean lambda$isDisplayInfoReadPhoneStateEnforcedInPlatformCompat$5(java.lang.String str, android.os.UserHandle userHandle) throws java.lang.Exception {
            return java.lang.Boolean.valueOf(android.app.compat.CompatChanges.isChangeEnabled(com.android.server.TelephonyRegistry.REQUIRE_READ_PHONE_STATE_PERMISSION_FOR_DISPLAY_INFO, str, userHandle));
        }

        public boolean isDisplayInfoReadPhoneStateEnforcedInPlatformCompat(final java.lang.String str, final android.os.UserHandle userHandle) {
            return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda5
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$isDisplayInfoReadPhoneStateEnforcedInPlatformCompat$5;
                    lambda$isDisplayInfoReadPhoneStateEnforcedInPlatformCompat$5 = com.android.server.TelephonyRegistry.ConfigurationProvider.lambda$isDisplayInfoReadPhoneStateEnforcedInPlatformCompat$5(str, userHandle);
                    return lambda$isDisplayInfoReadPhoneStateEnforcedInPlatformCompat$5;
                }
            })).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.lang.Boolean lambda$isDisplayInfoNrAdvancedSupported$6(java.lang.String str, android.os.UserHandle userHandle) throws java.lang.Exception {
            return java.lang.Boolean.valueOf(android.app.compat.CompatChanges.isChangeEnabled(com.android.server.TelephonyRegistry.DISPLAY_INFO_NR_ADVANCED_SUPPORTED, str, userHandle));
        }

        public boolean isDisplayInfoNrAdvancedSupported(final java.lang.String str, final android.os.UserHandle userHandle) {
            return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$ConfigurationProvider$$ExternalSyntheticLambda2
                public final java.lang.Object getOrThrow() {
                    java.lang.Boolean lambda$isDisplayInfoNrAdvancedSupported$6;
                    lambda$isDisplayInfoNrAdvancedSupported$6 = com.android.server.TelephonyRegistry.ConfigurationProvider.lambda$isDisplayInfoNrAdvancedSupported$6(str, userHandle);
                    return lambda$isDisplayInfoNrAdvancedSupported$6;
                }
            })).booleanValue();
        }
    }

    static {
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(13);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(14);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(12);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(26);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(27);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(28);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(31);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(32);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(33);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(34);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(37);
        REQUIRE_PRECISE_PHONE_STATE_PERMISSION.add(39);
    }

    private boolean isLocationPermissionRequired(java.util.Set<java.lang.Integer> set) {
        return set.contains(5) || set.contains(11);
    }

    private boolean isPhoneStatePermissionRequired(java.util.Set<java.lang.Integer> set, java.lang.String str, android.os.UserHandle userHandle) {
        if (set.contains(4) || set.contains(3) || set.contains(25)) {
            return true;
        }
        if ((set.contains(36) || set.contains(6)) && this.mConfigurationProvider.isCallStateReadPhoneStateEnforcedInPlatformCompat(str, userHandle)) {
            return true;
        }
        if (set.contains(23) && this.mConfigurationProvider.isActiveDataSubIdReadPhoneStateEnforcedInPlatformCompat(str, userHandle)) {
            return true;
        }
        if (set.contains(11) && this.mConfigurationProvider.isCellInfoReadPhoneStateEnforcedInPlatformCompat(str, userHandle)) {
            return true;
        }
        return set.contains(21) && !this.mConfigurationProvider.isDisplayInfoReadPhoneStateEnforcedInPlatformCompat(str, userHandle);
    }

    private boolean isPrecisePhoneStatePermissionRequired(java.util.Set<java.lang.Integer> set) {
        java.util.Iterator<java.lang.Integer> it = REQUIRE_PRECISE_PHONE_STATE_PERMISSION.iterator();
        while (it.hasNext()) {
            if (set.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean isActiveEmergencySessionPermissionRequired(java.util.Set<java.lang.Integer> set) {
        return set.contains(29) || set.contains(30);
    }

    private boolean isPrivilegedPhoneStatePermissionRequired(java.util.Set<java.lang.Integer> set) {
        return set.contains(16) || set.contains(18) || set.contains(24) || set.contains(35) || set.contains(40) || set.contains(41);
    }

    private class TelephonyRegistryDeathRecipient implements android.os.IBinder.DeathRecipient {
        private final android.os.IBinder binder;

        TelephonyRegistryDeathRecipient(android.os.IBinder iBinder) {
            this.binder = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.TelephonyRegistry.this.remove(this.binder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.telephony.TelephonyManager getTelephonyManager() {
        return (android.telephony.TelephonyManager) this.mContext.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMultiSimConfigChanged() {
        synchronized (this.mRecords) {
            try {
                int i = this.mNumPhones;
                this.mNumPhones = getTelephonyManager().getActiveModemCount();
                if (i == this.mNumPhones) {
                    return;
                }
                this.mCallState = java.util.Arrays.copyOf(this.mCallState, this.mNumPhones);
                this.mDataActivity = java.util.Arrays.copyOf(this.mCallState, this.mNumPhones);
                this.mDataConnectionState = java.util.Arrays.copyOf(this.mCallState, this.mNumPhones);
                this.mDataConnectionNetworkType = java.util.Arrays.copyOf(this.mCallState, this.mNumPhones);
                this.mCallIncomingNumber = (java.lang.String[]) java.util.Arrays.copyOf(this.mCallIncomingNumber, this.mNumPhones);
                this.mServiceState = (android.telephony.ServiceState[]) java.util.Arrays.copyOf(this.mServiceState, this.mNumPhones);
                this.mVoiceActivationState = java.util.Arrays.copyOf(this.mVoiceActivationState, this.mNumPhones);
                this.mDataActivationState = java.util.Arrays.copyOf(this.mDataActivationState, this.mNumPhones);
                this.mUserMobileDataState = java.util.Arrays.copyOf(this.mUserMobileDataState, this.mNumPhones);
                if (this.mSignalStrength != null) {
                    this.mSignalStrength = (android.telephony.SignalStrength[]) java.util.Arrays.copyOf(this.mSignalStrength, this.mNumPhones);
                } else {
                    this.mSignalStrength = new android.telephony.SignalStrength[this.mNumPhones];
                }
                this.mMessageWaiting = java.util.Arrays.copyOf(this.mMessageWaiting, this.mNumPhones);
                this.mCallForwarding = java.util.Arrays.copyOf(this.mCallForwarding, this.mNumPhones);
                this.mCellIdentity = (android.telephony.CellIdentity[]) java.util.Arrays.copyOf(this.mCellIdentity, this.mNumPhones);
                this.mSrvccState = java.util.Arrays.copyOf(this.mSrvccState, this.mNumPhones);
                this.mPreciseCallState = (android.telephony.PreciseCallState[]) java.util.Arrays.copyOf(this.mPreciseCallState, this.mNumPhones);
                this.mForegroundCallState = java.util.Arrays.copyOf(this.mForegroundCallState, this.mNumPhones);
                this.mBackgroundCallState = java.util.Arrays.copyOf(this.mBackgroundCallState, this.mNumPhones);
                this.mRingingCallState = java.util.Arrays.copyOf(this.mRingingCallState, this.mNumPhones);
                this.mCallDisconnectCause = java.util.Arrays.copyOf(this.mCallDisconnectCause, this.mNumPhones);
                this.mCallPreciseDisconnectCause = java.util.Arrays.copyOf(this.mCallPreciseDisconnectCause, this.mNumPhones);
                this.mCallQuality = (android.telephony.CallQuality[]) java.util.Arrays.copyOf(this.mCallQuality, this.mNumPhones);
                this.mCallNetworkType = java.util.Arrays.copyOf(this.mCallNetworkType, this.mNumPhones);
                this.mOutgoingCallEmergencyNumber = (android.telephony.emergency.EmergencyNumber[]) java.util.Arrays.copyOf(this.mOutgoingCallEmergencyNumber, this.mNumPhones);
                this.mOutgoingSmsEmergencyNumber = (android.telephony.emergency.EmergencyNumber[]) java.util.Arrays.copyOf(this.mOutgoingSmsEmergencyNumber, this.mNumPhones);
                this.mTelephonyDisplayInfos = (android.telephony.TelephonyDisplayInfo[]) java.util.Arrays.copyOf(this.mTelephonyDisplayInfos, this.mNumPhones);
                this.mCarrierNetworkChangeState = java.util.Arrays.copyOf(this.mCarrierNetworkChangeState, this.mNumPhones);
                this.mIsDataEnabled = java.util.Arrays.copyOf(this.mIsDataEnabled, this.mNumPhones);
                this.mDataEnabledReason = java.util.Arrays.copyOf(this.mDataEnabledReason, this.mNumPhones);
                this.mAllowedNetworkTypeReason = java.util.Arrays.copyOf(this.mAllowedNetworkTypeReason, this.mNumPhones);
                this.mAllowedNetworkTypeValue = java.util.Arrays.copyOf(this.mAllowedNetworkTypeValue, this.mNumPhones);
                this.mECBMReason = java.util.Arrays.copyOf(this.mECBMReason, this.mNumPhones);
                this.mECBMStarted = java.util.Arrays.copyOf(this.mECBMStarted, this.mNumPhones);
                this.mSCBMReason = java.util.Arrays.copyOf(this.mSCBMReason, this.mNumPhones);
                this.mSCBMStarted = java.util.Arrays.copyOf(this.mSCBMStarted, this.mNumPhones);
                if (this.mNumPhones < i) {
                    cutListToSize(this.mCellInfo, this.mNumPhones);
                    cutListToSize(this.mImsReasonInfo, this.mNumPhones);
                    cutListToSize(this.mPreciseDataConnectionStates, this.mNumPhones);
                    cutListToSize(this.mBarringInfo, this.mNumPhones);
                    cutListToSize(this.mPhysicalChannelConfigs, this.mNumPhones);
                    cutListToSize(this.mLinkCapacityEstimateLists, this.mNumPhones);
                    cutListToSize(this.mCarrierPrivilegeStates, this.mNumPhones);
                    cutListToSize(this.mCarrierServiceStates, this.mNumPhones);
                    cutListToSize(this.mCallStateLists, this.mNumPhones);
                    cutListToSize(this.mMediaQualityStatus, this.mNumPhones);
                    return;
                }
                while (i < this.mNumPhones) {
                    this.mCallState[i] = 0;
                    this.mDataActivity[i] = 0;
                    this.mDataConnectionState[i] = -1;
                    this.mVoiceActivationState[i] = 0;
                    this.mDataActivationState[i] = 0;
                    this.mCallIncomingNumber[i] = "";
                    this.mServiceState[i] = new android.telephony.ServiceState();
                    this.mSignalStrength[i] = null;
                    this.mUserMobileDataState[i] = false;
                    this.mMessageWaiting[i] = false;
                    this.mCallForwarding[i] = false;
                    this.mCellIdentity[i] = null;
                    this.mCellInfo.add(i, java.util.Collections.EMPTY_LIST);
                    this.mImsReasonInfo.add(i, null);
                    this.mSrvccState[i] = -1;
                    this.mCallDisconnectCause[i] = -1;
                    this.mCallPreciseDisconnectCause[i] = -1;
                    this.mCallQuality[i] = createCallQuality();
                    this.mMediaQualityStatus.add(i, new android.util.SparseArray<>());
                    this.mCallStateLists.add(i, new java.util.ArrayList());
                    this.mCallNetworkType[i] = 0;
                    this.mPreciseCallState[i] = createPreciseCallState();
                    this.mRingingCallState[i] = 0;
                    this.mForegroundCallState[i] = 0;
                    this.mBackgroundCallState[i] = 0;
                    this.mPreciseDataConnectionStates.add(new android.util.ArrayMap());
                    this.mBarringInfo.add(i, new android.telephony.BarringInfo());
                    this.mCarrierNetworkChangeState[i] = false;
                    this.mTelephonyDisplayInfos[i] = null;
                    this.mIsDataEnabled[i] = false;
                    this.mDataEnabledReason[i] = 0;
                    this.mPhysicalChannelConfigs.add(i, new java.util.ArrayList());
                    this.mAllowedNetworkTypeReason[i] = -1;
                    this.mAllowedNetworkTypeValue[i] = -1;
                    this.mLinkCapacityEstimateLists.add(i, INVALID_LCE_LIST);
                    this.mCarrierPrivilegeStates.add(i, new android.util.Pair<>(java.util.Collections.emptyList(), new int[0]));
                    this.mCarrierServiceStates.add(i, new android.util.Pair<>(null, -1));
                    this.mECBMReason[i] = 0;
                    this.mECBMStarted[i] = false;
                    this.mSCBMReason[i] = 0;
                    this.mSCBMStarted[i] = false;
                    i++;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void cutListToSize(java.util.List list, int i) {
        if (list == null) {
            return;
        }
        while (list.size() > i) {
            list.remove(list.size() - 1);
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public TelephonyRegistry(android.content.Context context, com.android.server.TelephonyRegistry.ConfigurationProvider configurationProvider) {
        this.mImsReasonInfo = null;
        this.mBarringInfo = null;
        this.mCarrierNetworkChangeState = null;
        this.mContext = context;
        this.mConfigurationProvider = configurationProvider;
        int activeModemCount = getTelephonyManager().getActiveModemCount();
        this.mNumPhones = activeModemCount;
        this.mCallState = new int[activeModemCount];
        this.mDataActivity = new int[activeModemCount];
        this.mDataConnectionState = new int[activeModemCount];
        this.mDataConnectionNetworkType = new int[activeModemCount];
        this.mCallIncomingNumber = new java.lang.String[activeModemCount];
        this.mServiceState = new android.telephony.ServiceState[activeModemCount];
        this.mVoiceActivationState = new int[activeModemCount];
        this.mDataActivationState = new int[activeModemCount];
        this.mUserMobileDataState = new boolean[activeModemCount];
        this.mSignalStrength = new android.telephony.SignalStrength[activeModemCount];
        this.mMessageWaiting = new boolean[activeModemCount];
        this.mCallForwarding = new boolean[activeModemCount];
        this.mCellIdentity = new android.telephony.CellIdentity[activeModemCount];
        this.mSrvccState = new int[activeModemCount];
        this.mPreciseCallState = new android.telephony.PreciseCallState[activeModemCount];
        this.mForegroundCallState = new int[activeModemCount];
        this.mBackgroundCallState = new int[activeModemCount];
        this.mRingingCallState = new int[activeModemCount];
        this.mCallDisconnectCause = new int[activeModemCount];
        this.mCallPreciseDisconnectCause = new int[activeModemCount];
        this.mCallQuality = new android.telephony.CallQuality[activeModemCount];
        this.mMediaQualityStatus = new java.util.ArrayList();
        this.mCallNetworkType = new int[activeModemCount];
        this.mCallStateLists = new java.util.ArrayList<>();
        this.mPreciseDataConnectionStates = new java.util.ArrayList();
        this.mCellInfo = new java.util.ArrayList<>(activeModemCount);
        this.mImsReasonInfo = new java.util.ArrayList();
        this.mEmergencyNumberList = new java.util.HashMap();
        this.mOutgoingCallEmergencyNumber = new android.telephony.emergency.EmergencyNumber[activeModemCount];
        this.mOutgoingSmsEmergencyNumber = new android.telephony.emergency.EmergencyNumber[activeModemCount];
        this.mBarringInfo = new java.util.ArrayList();
        this.mCarrierNetworkChangeState = new boolean[activeModemCount];
        this.mTelephonyDisplayInfos = new android.telephony.TelephonyDisplayInfo[activeModemCount];
        this.mPhysicalChannelConfigs = new java.util.ArrayList();
        this.mAllowedNetworkTypeReason = new int[activeModemCount];
        this.mAllowedNetworkTypeValue = new long[activeModemCount];
        this.mIsDataEnabled = new boolean[activeModemCount];
        this.mDataEnabledReason = new int[activeModemCount];
        this.mLinkCapacityEstimateLists = new java.util.ArrayList();
        this.mCarrierPrivilegeStates = new java.util.ArrayList();
        this.mCarrierServiceStates = new java.util.ArrayList();
        this.mECBMReason = new int[activeModemCount];
        this.mECBMStarted = new boolean[activeModemCount];
        this.mSCBMReason = new int[activeModemCount];
        this.mSCBMStarted = new boolean[activeModemCount];
        for (int i = 0; i < activeModemCount; i++) {
            this.mCallState[i] = 0;
            this.mDataActivity[i] = 0;
            this.mDataConnectionState[i] = -1;
            this.mVoiceActivationState[i] = 0;
            this.mDataActivationState[i] = 0;
            this.mCallIncomingNumber[i] = "";
            this.mServiceState[i] = new android.telephony.ServiceState();
            this.mSignalStrength[i] = null;
            this.mUserMobileDataState[i] = false;
            this.mMessageWaiting[i] = false;
            this.mCallForwarding[i] = false;
            this.mCellIdentity[i] = null;
            this.mCellInfo.add(i, java.util.Collections.EMPTY_LIST);
            this.mImsReasonInfo.add(i, new android.telephony.ims.ImsReasonInfo());
            this.mSrvccState[i] = -1;
            this.mCallDisconnectCause[i] = -1;
            this.mCallPreciseDisconnectCause[i] = -1;
            this.mCallQuality[i] = createCallQuality();
            this.mMediaQualityStatus.add(i, new android.util.SparseArray<>());
            this.mCallStateLists.add(i, new java.util.ArrayList());
            this.mCallNetworkType[i] = 0;
            this.mPreciseCallState[i] = createPreciseCallState();
            this.mRingingCallState[i] = 0;
            this.mForegroundCallState[i] = 0;
            this.mBackgroundCallState[i] = 0;
            this.mPreciseDataConnectionStates.add(new android.util.ArrayMap());
            this.mBarringInfo.add(i, new android.telephony.BarringInfo());
            this.mCarrierNetworkChangeState[i] = false;
            this.mTelephonyDisplayInfos[i] = null;
            this.mIsDataEnabled[i] = false;
            this.mDataEnabledReason[i] = 0;
            this.mPhysicalChannelConfigs.add(i, new java.util.ArrayList());
            this.mAllowedNetworkTypeReason[i] = -1;
            this.mAllowedNetworkTypeValue[i] = -1;
            this.mLinkCapacityEstimateLists.add(i, INVALID_LCE_LIST);
            this.mCarrierPrivilegeStates.add(i, new android.util.Pair<>(java.util.Collections.emptyList(), new int[0]));
            this.mCarrierServiceStates.add(i, new android.util.Pair<>(null, -1));
            this.mECBMReason[i] = 0;
            this.mECBMStarted[i] = false;
            this.mSCBMReason[i] = 0;
            this.mSCBMStarted[i] = false;
        }
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
    }

    public void systemRunning() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.telephony.action.MULTI_SIM_CONFIG_CHANGED");
        log("systemRunning register for intents");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private boolean doesLimitApplyForListeners(int i, int i2) {
        return (i == 1000 || i == 1001 || i == i2) ? false : true;
    }

    public void addOnSubscriptionsChangedListener(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) {
        android.os.UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
        synchronized (this.mRecords) {
            try {
                com.android.server.TelephonyRegistry.Record add = add(iOnSubscriptionsChangedListener.asBinder(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), doesLimitApplyForListeners(android.os.Binder.getCallingUid(), android.os.Process.myUid()));
                if (add == null) {
                    return;
                }
                add.context = this.mContext;
                add.onSubscriptionsChangedListenerCallback = iOnSubscriptionsChangedListener;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = android.os.Binder.getCallingUid();
                add.callerPid = android.os.Binder.getCallingPid();
                add.eventList = new android.util.ArraySet();
                if (this.mHasNotifySubscriptionInfoChangedOccurred) {
                    try {
                        add.onSubscriptionsChangedListenerCallback.onSubscriptionsChanged();
                    } catch (android.os.RemoteException e) {
                        remove(add.binder);
                    }
                } else {
                    log("listen oscl: mHasNotifySubscriptionInfoChangedOccurred==false no callback");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeOnSubscriptionsChangedListener(java.lang.String str, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) {
        remove(iOnSubscriptionsChangedListener.asBinder());
    }

    public void addOnOpportunisticSubscriptionsChangedListener(java.lang.String str, java.lang.String str2, com.android.internal.telephony.IOnSubscriptionsChangedListener iOnSubscriptionsChangedListener) {
        android.os.UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
        synchronized (this.mRecords) {
            try {
                com.android.server.TelephonyRegistry.Record add = add(iOnSubscriptionsChangedListener.asBinder(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), doesLimitApplyForListeners(android.os.Binder.getCallingUid(), android.os.Process.myUid()));
                if (add == null) {
                    return;
                }
                add.context = this.mContext;
                add.onOpportunisticSubscriptionsChangedListenerCallback = iOnSubscriptionsChangedListener;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = android.os.Binder.getCallingUid();
                add.callerPid = android.os.Binder.getCallingPid();
                add.eventList = new android.util.ArraySet();
                if (this.mHasNotifyOpportunisticSubscriptionInfoChangedOccurred) {
                    try {
                        add.onOpportunisticSubscriptionsChangedListenerCallback.onSubscriptionsChanged();
                    } catch (android.os.RemoteException e) {
                        remove(add.binder);
                    }
                } else {
                    log("listen ooscl: hasNotifyOpptSubInfoChangedOccurred==false no callback");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifySubscriptionInfoChanged() {
        if (!checkNotifyPermission("notifySubscriptionInfoChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            try {
                if (!this.mHasNotifySubscriptionInfoChangedOccurred) {
                    log("notifySubscriptionInfoChanged: first invocation mRecords.size=" + this.mRecords.size());
                }
                this.mHasNotifySubscriptionInfoChangedOccurred = true;
                this.mRemoveList.clear();
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchOnSubscriptionsChangedListener()) {
                        try {
                            next.onSubscriptionsChangedListenerCallback.onSubscriptionsChanged();
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyOpportunisticSubscriptionInfoChanged() {
        if (!checkNotifyPermission("notifyOpportunisticSubscriptionInfoChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            try {
                if (!this.mHasNotifyOpportunisticSubscriptionInfoChangedOccurred) {
                    log("notifyOpptSubscriptionInfoChanged: first invocation mRecords.size=" + this.mRecords.size());
                }
                this.mHasNotifyOpportunisticSubscriptionInfoChangedOccurred = true;
                this.mRemoveList.clear();
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchOnOpportunisticSubscriptionsChangedListener()) {
                        try {
                            next.onOpportunisticSubscriptionsChangedListenerCallback.onSubscriptionsChanged();
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void listenWithEventList(boolean z, boolean z2, int i, java.lang.String str, java.lang.String str2, com.android.internal.telephony.IPhoneStateListener iPhoneStateListener, int[] iArr, boolean z3) {
        listen(z, z2, str, str2, iPhoneStateListener, (java.util.Set) java.util.Arrays.stream(iArr).boxed().collect(java.util.stream.Collectors.toSet()), z3, i);
    }

    private void listen(boolean z, boolean z2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, com.android.internal.telephony.IPhoneStateListener iPhoneStateListener, java.util.Set<java.lang.Integer> set, boolean z3, int i) {
        int i2;
        int i3;
        java.util.List<android.telephony.PhysicalChannelConfig> list;
        android.telephony.CallState callState;
        android.telephony.BarringInfo barringInfo;
        android.telephony.ims.ImsReasonInfo imsReasonInfo;
        int callingUserId = android.os.UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
        this.mListenLog.log("listen: E pkg=" + pii(str) + " uid=" + android.os.Binder.getCallingUid() + " events=" + set + " notifyNow=" + z3 + " subId=" + i + " myUserId=" + android.os.UserHandle.myUserId() + " callerUserId=" + callingUserId);
        if (set.isEmpty()) {
            set.clear();
            remove(iPhoneStateListener.asBinder());
            return;
        }
        if (!checkListenerPermission(set, i, str, str2, "listen")) {
            return;
        }
        int i4 = -1;
        if (!com.android.internal.telephony.flags.Flags.preventSystemServerAndPhoneDeadlock()) {
            i2 = Integer.MAX_VALUE;
            i3 = -1;
        } else {
            i2 = !android.telephony.SubscriptionManager.isValidSubscriptionId(i) ? Integer.MAX_VALUE : i;
            i3 = getPhoneIdFromSubId(i2);
        }
        synchronized (this.mRecords) {
            try {
                com.android.server.TelephonyRegistry.Record add = add(iPhoneStateListener.asBinder(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), doesLimitApplyForListeners(android.os.Binder.getCallingUid(), android.os.Process.myUid()));
                if (add == null) {
                    return;
                }
                add.context = this.mContext;
                add.callback = iPhoneStateListener;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.renounceCoarseLocationAccess = z2;
                add.renounceFineLocationAccess = z;
                add.callerUid = android.os.Binder.getCallingUid();
                add.callerPid = android.os.Binder.getCallingPid();
                if (!com.android.internal.telephony.flags.Flags.preventSystemServerAndPhoneDeadlock()) {
                    if (!android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
                        add.subId = Integer.MAX_VALUE;
                    } else {
                        add.subId = i;
                    }
                    add.phoneId = getPhoneIdFromSubId(add.subId);
                } else {
                    add.subId = i2;
                    add.phoneId = i3;
                }
                add.eventList = set;
                if (z3 && validatePhoneId(add.phoneId)) {
                    if (set.contains(1)) {
                        try {
                            android.telephony.ServiceState serviceState = new android.telephony.ServiceState(this.mServiceState[add.phoneId]);
                            if (checkFineLocationAccess(add, 29)) {
                                add.callback.onServiceStateChanged(serviceState);
                            } else if (checkCoarseLocationAccess(add, 29)) {
                                add.callback.onServiceStateChanged(serviceState.createLocationInfoSanitizedCopy(false));
                            } else {
                                add.callback.onServiceStateChanged(serviceState.createLocationInfoSanitizedCopy(true));
                            }
                        } catch (android.os.RemoteException e) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(2)) {
                        try {
                            if (this.mSignalStrength[add.phoneId] != null) {
                                int gsmSignalStrength = this.mSignalStrength[add.phoneId].getGsmSignalStrength();
                                com.android.internal.telephony.IPhoneStateListener iPhoneStateListener2 = add.callback;
                                if (gsmSignalStrength != 99) {
                                    i4 = gsmSignalStrength;
                                }
                                iPhoneStateListener2.onSignalStrengthChanged(i4);
                            }
                        } catch (android.os.RemoteException e2) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(3)) {
                        try {
                            add.callback.onMessageWaitingIndicatorChanged(this.mMessageWaiting[add.phoneId]);
                        } catch (android.os.RemoteException e3) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(4)) {
                        try {
                            add.callback.onCallForwardingIndicatorChanged(this.mCallForwarding[add.phoneId]);
                        } catch (android.os.RemoteException e4) {
                            remove(add.binder);
                        }
                    }
                    if (validateEventAndUserLocked(add, 5)) {
                        try {
                            if (checkCoarseLocationAccess(add, 1) && checkFineLocationAccess(add, 29)) {
                                add.callback.onCellLocationChanged(this.mCellIdentity[add.phoneId]);
                            }
                        } catch (android.os.RemoteException e5) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(36)) {
                        try {
                            add.callback.onLegacyCallStateChanged(this.mCallState[add.phoneId], getCallIncomingNumber(add, add.phoneId));
                        } catch (android.os.RemoteException e6) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(6)) {
                        try {
                            add.callback.onCallStateChanged(this.mCallState[add.phoneId]);
                        } catch (android.os.RemoteException e7) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(7)) {
                        try {
                            add.callback.onDataConnectionStateChanged(this.mDataConnectionState[add.phoneId], this.mDataConnectionNetworkType[add.phoneId]);
                        } catch (android.os.RemoteException e8) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(8)) {
                        try {
                            add.callback.onDataActivity(this.mDataActivity[add.phoneId]);
                        } catch (android.os.RemoteException e9) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(9)) {
                        try {
                            if (this.mSignalStrength[add.phoneId] != null) {
                                add.callback.onSignalStrengthsChanged(this.mSignalStrength[add.phoneId]);
                            }
                        } catch (android.os.RemoteException e10) {
                            remove(add.binder);
                        }
                    }
                    if (validateEventAndUserLocked(add, 11)) {
                        try {
                            if (checkCoarseLocationAccess(add, 1) && checkFineLocationAccess(add, 29)) {
                                add.callback.onCellInfoChanged(this.mCellInfo.get(add.phoneId));
                            }
                        } catch (android.os.RemoteException e11) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(12)) {
                        try {
                            add.callback.onPreciseCallStateChanged(this.mPreciseCallState[add.phoneId]);
                        } catch (android.os.RemoteException e12) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(26)) {
                        try {
                            add.callback.onCallDisconnectCauseChanged(this.mCallDisconnectCause[add.phoneId], this.mCallPreciseDisconnectCause[add.phoneId]);
                        } catch (android.os.RemoteException e13) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(28) && (imsReasonInfo = this.mImsReasonInfo.get(add.phoneId)) != null) {
                        try {
                            add.callback.onImsCallDisconnectCauseChanged(imsReasonInfo);
                        } catch (android.os.RemoteException e14) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(13)) {
                        try {
                            java.util.Iterator<android.telephony.PreciseDataConnectionState> it = this.mPreciseDataConnectionStates.get(add.phoneId).values().iterator();
                            while (it.hasNext()) {
                                add.callback.onPreciseDataConnectionStateChanged(it.next());
                            }
                        } catch (android.os.RemoteException e15) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(17)) {
                        try {
                            add.callback.onCarrierNetworkChange(this.mCarrierNetworkChangeState[add.phoneId]);
                        } catch (android.os.RemoteException e16) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(18)) {
                        try {
                            add.callback.onVoiceActivationStateChanged(this.mVoiceActivationState[add.phoneId]);
                        } catch (android.os.RemoteException e17) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(19)) {
                        try {
                            add.callback.onDataActivationStateChanged(this.mDataActivationState[add.phoneId]);
                        } catch (android.os.RemoteException e18) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(20)) {
                        try {
                            add.callback.onUserMobileDataStateChanged(this.mUserMobileDataState[add.phoneId]);
                        } catch (android.os.RemoteException e19) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(21)) {
                        try {
                            if (this.mTelephonyDisplayInfos[add.phoneId] != null) {
                                add.callback.onDisplayInfoChanged(this.mTelephonyDisplayInfos[add.phoneId]);
                            }
                        } catch (android.os.RemoteException e20) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(25)) {
                        try {
                            add.callback.onEmergencyNumberListChanged(this.mEmergencyNumberList);
                        } catch (android.os.RemoteException e21) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(22)) {
                        try {
                            add.callback.onPhoneCapabilityChanged(this.mPhoneCapability);
                        } catch (android.os.RemoteException e22) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(23)) {
                        try {
                            add.callback.onActiveDataSubIdChanged(this.mActiveDataSubId);
                        } catch (android.os.RemoteException e23) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(24)) {
                        try {
                            add.callback.onRadioPowerStateChanged(this.mRadioPowerState);
                        } catch (android.os.RemoteException e24) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(16)) {
                        try {
                            add.callback.onSrvccStateChanged(this.mSrvccState[add.phoneId]);
                        } catch (android.os.RemoteException e25) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(27)) {
                        try {
                            add.callback.onCallStatesChanged(this.mCallStateLists.get(add.phoneId));
                        } catch (android.os.RemoteException e26) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(32) && (barringInfo = this.mBarringInfo.get(add.phoneId)) != null) {
                        android.telephony.BarringInfo createLocationInfoSanitizedCopy = barringInfo.createLocationInfoSanitizedCopy();
                        try {
                            com.android.internal.telephony.IPhoneStateListener iPhoneStateListener3 = add.callback;
                            if (!checkFineLocationAccess(add, 1)) {
                                barringInfo = createLocationInfoSanitizedCopy;
                            }
                            iPhoneStateListener3.onBarringInfoChanged(barringInfo);
                        } catch (android.os.RemoteException e27) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(33)) {
                        try {
                            com.android.internal.telephony.IPhoneStateListener iPhoneStateListener4 = add.callback;
                            if (shouldSanitizeLocationForPhysicalChannelConfig(add)) {
                                list = getLocationSanitizedConfigs(this.mPhysicalChannelConfigs.get(add.phoneId));
                            } else {
                                list = this.mPhysicalChannelConfigs.get(add.phoneId);
                            }
                            iPhoneStateListener4.onPhysicalChannelConfigChanged(list);
                        } catch (android.os.RemoteException e28) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(34)) {
                        try {
                            add.callback.onDataEnabledChanged(this.mIsDataEnabled[add.phoneId], this.mDataEnabledReason[add.phoneId]);
                        } catch (android.os.RemoteException e29) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(41)) {
                        try {
                            add.callback.onSimultaneousCallingStateChanged(this.mSimultaneousCellularCallingSubIds);
                        } catch (android.os.RemoteException e30) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(37)) {
                        try {
                            if (this.mLinkCapacityEstimateLists.get(add.phoneId) != null) {
                                add.callback.onLinkCapacityEstimateChanged(this.mLinkCapacityEstimateLists.get(add.phoneId));
                            }
                        } catch (android.os.RemoteException e31) {
                            remove(add.binder);
                        }
                    }
                    if (set.contains(39)) {
                        java.util.Iterator<android.telephony.CallState> it2 = this.mCallStateLists.get(add.phoneId).iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                callState = it2.next();
                                if (callState.getCallState() == 1) {
                                    break;
                                }
                            } else {
                                callState = null;
                                break;
                            }
                        }
                        if (callState != null) {
                            java.lang.String imsCallSessionId = callState.getImsCallSessionId();
                            try {
                                android.telephony.ims.MediaQualityStatus mediaQualityStatus = this.mMediaQualityStatus.get(add.phoneId).get(1);
                                if (mediaQualityStatus != null && mediaQualityStatus.getCallSessionId().equals(imsCallSessionId)) {
                                    add.callback.onMediaQualityStatusChanged(mediaQualityStatus);
                                }
                                android.telephony.ims.MediaQualityStatus mediaQualityStatus2 = this.mMediaQualityStatus.get(add.phoneId).get(2);
                                if (mediaQualityStatus2 != null && mediaQualityStatus2.getCallSessionId().equals(imsCallSessionId)) {
                                    add.callback.onMediaQualityStatusChanged(mediaQualityStatus2);
                                }
                            } catch (android.os.RemoteException e32) {
                                remove(add.binder);
                            }
                        }
                    }
                    if (set.contains(40)) {
                        try {
                            if (this.mECBMStarted[add.phoneId]) {
                                add.callback.onCallBackModeStarted(1);
                            } else {
                                add.callback.onCallBackModeStopped(1, this.mECBMReason[add.phoneId]);
                            }
                            if (this.mSCBMStarted[add.phoneId]) {
                                add.callback.onCallBackModeStarted(2);
                            } else {
                                add.callback.onCallBackModeStopped(2, this.mSCBMReason[add.phoneId]);
                            }
                        } catch (android.os.RemoteException e33) {
                            remove(add.binder);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private java.lang.String getCallIncomingNumber(com.android.server.TelephonyRegistry.Record record, int i) {
        return record.canReadCallLog() ? this.mCallIncomingNumber[i] : "";
    }

    private com.android.server.TelephonyRegistry.Record add(android.os.IBinder iBinder, int i, int i2, boolean z) {
        synchronized (this.mRecords) {
            try {
                int size = this.mRecords.size();
                int i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    com.android.server.TelephonyRegistry.Record record = this.mRecords.get(i4);
                    if (iBinder == record.binder) {
                        return record;
                    }
                    if (record.callerPid == i2) {
                        i3++;
                    }
                }
                int registrationLimit = this.mConfigurationProvider.getRegistrationLimit();
                try {
                    if (z && registrationLimit >= 1 && i3 >= registrationLimit) {
                        java.lang.String str = "Pid " + i2 + " has exceeded the number of permissible registered listeners. Ignoring request to add.";
                        loge(str);
                        if (this.mConfigurationProvider.isRegistrationLimitEnabledInPlatformCompat(i)) {
                            throw new java.lang.IllegalStateException(str);
                        }
                    } else if (i3 >= 25) {
                        android.telephony.Rlog.w(TAG, "Pid " + i2 + " has exceeded half the number of permissible registered listeners. Now at " + i3);
                        com.android.server.TelephonyRegistry.Record record2 = new com.android.server.TelephonyRegistry.Record();
                        record2.binder = iBinder;
                        record2.deathRecipient = new com.android.server.TelephonyRegistry.TelephonyRegistryDeathRecipient(iBinder);
                        iBinder.linkToDeath(record2.deathRecipient, 0);
                        this.mRecords.add(record2);
                        return record2;
                    }
                    iBinder.linkToDeath(record2.deathRecipient, 0);
                    this.mRecords.add(record2);
                    return record2;
                } catch (android.os.RemoteException e) {
                    return null;
                }
                com.android.server.TelephonyRegistry.Record record22 = new com.android.server.TelephonyRegistry.Record();
                record22.binder = iBinder;
                record22.deathRecipient = new com.android.server.TelephonyRegistry.TelephonyRegistryDeathRecipient(iBinder);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void remove(android.os.IBinder iBinder) {
        synchronized (this.mRecords) {
            int size = this.mRecords.size();
            for (int i = 0; i < size; i++) {
                com.android.server.TelephonyRegistry.Record record = this.mRecords.get(i);
                if (record.binder == iBinder) {
                    if (record.deathRecipient != null) {
                        try {
                            iBinder.unlinkToDeath(record.deathRecipient, 0);
                        } catch (java.util.NoSuchElementException e) {
                        }
                    }
                    this.mRecords.remove(i);
                    return;
                }
            }
        }
    }

    public void notifyCallStateForAllSubs(int i, java.lang.String str) {
        if (!checkNotifyPermission("notifyCallState()")) {
            return;
        }
        synchronized (this.mRecords) {
            java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
            while (it.hasNext()) {
                com.android.server.TelephonyRegistry.Record next = it.next();
                if (next.matchTelephonyCallbackEvent(36) && next.subId == Integer.MAX_VALUE) {
                    try {
                        next.callback.onLegacyCallStateChanged(i, next.canReadCallLog() ? str : "");
                    } catch (android.os.RemoteException e) {
                        this.mRemoveList.add(next.binder);
                    }
                }
                if (next.matchTelephonyCallbackEvent(6) && next.subId == Integer.MAX_VALUE) {
                    try {
                        next.callback.onCallStateChanged(i);
                    } catch (android.os.RemoteException e2) {
                        this.mRemoveList.add(next.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
        broadcastCallStateChanged(i, str, -1, -1);
    }

    public void notifyCallState(int i, int i2, int i3, java.lang.String str) {
        if (!checkNotifyPermission("notifyCallState()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mCallState[i] = i3;
                this.mCallIncomingNumber[i] = str;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(36) && next.subId == i2 && next.subId != Integer.MAX_VALUE) {
                        try {
                            next.callback.onLegacyCallStateChanged(i3, getCallIncomingNumber(next, i));
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                    if (next.matchTelephonyCallbackEvent(6) && next.subId == i2 && next.subId != Integer.MAX_VALUE) {
                        try {
                            next.callback.onCallStateChanged(i3);
                        } catch (android.os.RemoteException e2) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
        broadcastCallStateChanged(i3, str, i, i2);
    }

    public void notifyServiceStateForPhoneId(int i, int i2, android.telephony.ServiceState serviceState) {
        android.telephony.ServiceState createLocationInfoSanitizedCopy;
        if (!checkNotifyPermission("notifyServiceState()")) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mRecords) {
                try {
                    this.mLocalLog.log("notifyServiceStateForSubscriber: subId=" + i2 + " phoneId=" + i + " state=" + serviceState);
                    if (validatePhoneId(i) && android.telephony.SubscriptionManager.isValidSubscriptionId(i2)) {
                        this.mServiceState[i] = serviceState;
                        java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                        while (it.hasNext()) {
                            com.android.server.TelephonyRegistry.Record next = it.next();
                            if (next.matchTelephonyCallbackEvent(1) && idMatch(next, i2, i)) {
                                try {
                                    if (checkFineLocationAccess(next, 29)) {
                                        createLocationInfoSanitizedCopy = new android.telephony.ServiceState(serviceState);
                                    } else if (checkCoarseLocationAccess(next, 29)) {
                                        createLocationInfoSanitizedCopy = serviceState.createLocationInfoSanitizedCopy(false);
                                    } else {
                                        createLocationInfoSanitizedCopy = serviceState.createLocationInfoSanitizedCopy(true);
                                    }
                                    next.callback.onServiceStateChanged(createLocationInfoSanitizedCopy);
                                } catch (android.os.RemoteException e) {
                                    this.mRemoveList.add(next.binder);
                                }
                            }
                        }
                    } else {
                        log("notifyServiceStateForSubscriber: INVALID phoneId=" + i + " or subId=" + i2);
                    }
                    handleRemoveListLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            broadcastServiceStateChanged(serviceState, i, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void notifySimActivationStateChangedForPhoneId(int i, int i2, int i3, int i4) {
        if (!checkNotifyPermission("notifySimActivationState()")) {
            return;
        }
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(i)) {
                    switch (i3) {
                        case 0:
                            this.mVoiceActivationState[i] = i4;
                            break;
                        case 1:
                            this.mDataActivationState[i] = i4;
                            break;
                        default:
                            return;
                    }
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        com.android.server.TelephonyRegistry.Record next = it.next();
                        if (i3 == 0) {
                            try {
                                if (next.matchTelephonyCallbackEvent(18) && idMatch(next, i2, i)) {
                                    next.callback.onVoiceActivationStateChanged(i4);
                                }
                            } catch (android.os.RemoteException e) {
                                this.mRemoveList.add(next.binder);
                            }
                        }
                        if (i3 == 1 && next.matchTelephonyCallbackEvent(19) && idMatch(next, i2, i)) {
                            next.callback.onDataActivationStateChanged(i4);
                        }
                    }
                } else {
                    log("notifySimActivationStateForPhoneId: INVALID phoneId=" + i);
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifySignalStrengthForPhoneId(int i, int i2, android.telephony.SignalStrength signalStrength) {
        if (!checkNotifyPermission("notifySignalStrength()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mSignalStrength[i] = signalStrength;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(9) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onSignalStrengthsChanged(new android.telephony.SignalStrength(signalStrength));
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                    if (next.matchTelephonyCallbackEvent(2) && idMatch(next, i2, i)) {
                        try {
                            int gsmSignalStrength = signalStrength.getGsmSignalStrength();
                            if (gsmSignalStrength == 99) {
                                gsmSignalStrength = -1;
                            }
                            next.callback.onSignalStrengthChanged(gsmSignalStrength);
                        } catch (android.os.RemoteException e2) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            } else {
                log("notifySignalStrengthForPhoneId: invalid phoneId=" + i);
            }
            handleRemoveListLocked();
        }
        broadcastSignalStrengthChanged(signalStrength, i, i2);
    }

    public void notifyCarrierNetworkChange(boolean z) {
        int[] array = java.util.Arrays.stream(android.telephony.SubscriptionManager.from(this.mContext).getCompleteActiveSubscriptionIdList()).filter(new java.util.function.IntPredicate() { // from class: com.android.server.TelephonyRegistry$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                boolean lambda$notifyCarrierNetworkChange$0;
                lambda$notifyCarrierNetworkChange$0 = com.android.server.TelephonyRegistry.this.lambda$notifyCarrierNetworkChange$0(i);
                return lambda$notifyCarrierNetworkChange$0;
            }
        }).toArray();
        if (com.android.internal.util.ArrayUtils.isEmpty(array)) {
            loge("notifyCarrierNetworkChange without carrier privilege");
            throw new java.lang.SecurityException("notifyCarrierNetworkChange without carrier privilege");
        }
        for (int i : array) {
            notifyCarrierNetworkChangeWithPermission(i, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$notifyCarrierNetworkChange$0(int i) {
        return com.android.internal.telephony.TelephonyPermissions.checkCarrierPrivilegeForSubId(this.mContext, i);
    }

    public void notifyCarrierNetworkChangeWithSubId(int i, boolean z) {
        if (!com.android.internal.telephony.TelephonyPermissions.checkCarrierPrivilegeForSubId(this.mContext, i)) {
            throw new java.lang.SecurityException("notifyCarrierNetworkChange without carrier privilege on subId " + i);
        }
        notifyCarrierNetworkChangeWithPermission(i, z);
    }

    private void notifyCarrierNetworkChangeWithPermission(int i, boolean z) {
        int i2;
        if (!com.android.internal.telephony.flags.Flags.preventSystemServerAndPhoneDeadlock()) {
            i2 = -1;
        } else {
            i2 = getPhoneIdFromSubId(i);
        }
        synchronized (this.mRecords) {
            try {
                if (!com.android.internal.telephony.flags.Flags.preventSystemServerAndPhoneDeadlock()) {
                    i2 = getPhoneIdFromSubId(i);
                }
                this.mCarrierNetworkChangeState[i2] = z;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(17) && idMatch(next, i, i2)) {
                        try {
                            next.callback.onCarrierNetworkChange(z);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyCellInfo(java.util.List<android.telephony.CellInfo> list) {
        notifyCellInfoForSubscriber(Integer.MAX_VALUE, list);
    }

    public void notifyCellInfoForSubscriber(int i, java.util.List<android.telephony.CellInfo> list) {
        if (!checkNotifyPermission("notifyCellInfoForSubscriber()")) {
            return;
        }
        if (list == null) {
            loge("notifyCellInfoForSubscriber() received a null list");
            list = java.util.Collections.EMPTY_LIST;
        }
        int phoneIdFromSubId = getPhoneIdFromSubId(i);
        synchronized (this.mRecords) {
            if (validatePhoneId(phoneIdFromSubId)) {
                this.mCellInfo.set(phoneIdFromSubId, list);
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (validateEventAndUserLocked(next, 11) && idMatch(next, i, phoneIdFromSubId) && checkCoarseLocationAccess(next, 1) && checkFineLocationAccess(next, 29)) {
                        try {
                            next.callback.onCellInfoChanged(list);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyMessageWaitingChangedForPhoneId(int i, int i2, boolean z) {
        if (!checkNotifyPermission("notifyMessageWaitingChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mMessageWaiting[i] = z;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(3) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onMessageWaitingIndicatorChanged(z);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyUserMobileDataStateChangedForPhoneId(int i, int i2, boolean z) {
        if (!checkNotifyPermission("notifyUserMobileDataStateChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mUserMobileDataState[i] = z;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(20) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onUserMobileDataStateChanged(z);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyDisplayInfoChanged(int i, int i2, @android.annotation.NonNull android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) {
        if (!checkNotifyPermission("notifyDisplayInfoChanged()")) {
            return;
        }
        this.mLocalLog.log("notifyDisplayInfoChanged: PhoneId=" + i + " subId=" + i2 + " telephonyDisplayInfo=" + telephonyDisplayInfo);
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(i)) {
                    this.mTelephonyDisplayInfos[i] = telephonyDisplayInfo;
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        com.android.server.TelephonyRegistry.Record next = it.next();
                        if (next.matchTelephonyCallbackEvent(21) && idMatch(next, i2, i)) {
                            try {
                                if (!this.mConfigurationProvider.isDisplayInfoNrAdvancedSupported(next.callingPackage, android.os.Binder.getCallingUserHandle())) {
                                    next.callback.onDisplayInfoChanged(getBackwardCompatibleTelephonyDisplayInfo(telephonyDisplayInfo));
                                } else {
                                    next.callback.onDisplayInfoChanged(telephonyDisplayInfo);
                                }
                            } catch (android.os.RemoteException e) {
                                this.mRemoveList.add(next.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.telephony.TelephonyDisplayInfo getBackwardCompatibleTelephonyDisplayInfo(@android.annotation.NonNull android.telephony.TelephonyDisplayInfo telephonyDisplayInfo) {
        int networkType = telephonyDisplayInfo.getNetworkType();
        int overrideNetworkType = telephonyDisplayInfo.getOverrideNetworkType();
        if (networkType == 20) {
            overrideNetworkType = 0;
        } else if (networkType == 13 && overrideNetworkType == 5) {
            overrideNetworkType = 4;
        }
        return new android.telephony.TelephonyDisplayInfo(networkType, overrideNetworkType, telephonyDisplayInfo.isRoaming());
    }

    public void notifyCallForwardingChanged(boolean z) {
        notifyCallForwardingChangedForSubscriber(Integer.MAX_VALUE, z);
    }

    public void notifyCallForwardingChangedForSubscriber(int i, boolean z) {
        if (!checkNotifyPermission("notifyCallForwardingChanged()")) {
            return;
        }
        int phoneIdFromSubId = getPhoneIdFromSubId(i);
        synchronized (this.mRecords) {
            if (validatePhoneId(phoneIdFromSubId)) {
                this.mCallForwarding[phoneIdFromSubId] = z;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(4) && idMatch(next, i, phoneIdFromSubId)) {
                        try {
                            next.callback.onCallForwardingIndicatorChanged(z);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyDataActivityForSubscriber(int i, int i2) {
        if (!checkNotifyPermission("notifyDataActivity()")) {
            return;
        }
        int phoneIdFromSubId = getPhoneIdFromSubId(i);
        synchronized (this.mRecords) {
            if (validatePhoneId(phoneIdFromSubId)) {
                this.mDataActivity[phoneIdFromSubId] = i2;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(8) && idMatch(next, i, phoneIdFromSubId)) {
                        try {
                            next.callback.onDataActivity(i2);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyDataActivityForSubscriberWithSlot(int i, int i2, int i3) {
        if (!checkNotifyPermission("notifyDataActivityWithSlot()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mDataActivity[i] = i3;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(8) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onDataActivity(i3);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyDataConnectionForSubscriber(int i, int i2, @android.annotation.NonNull android.telephony.PreciseDataConnectionState preciseDataConnectionState) {
        int i3;
        if (!checkNotifyPermission("notifyDataConnection()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i) && preciseDataConnectionState.getApnSetting() != null) {
                android.util.Pair<java.lang.Integer, android.telephony.data.ApnSetting> create = android.util.Pair.create(java.lang.Integer.valueOf(preciseDataConnectionState.getTransportType()), preciseDataConnectionState.getApnSetting());
                if (!java.util.Objects.equals(this.mPreciseDataConnectionStates.get(i).remove(create), preciseDataConnectionState)) {
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        com.android.server.TelephonyRegistry.Record next = it.next();
                        if (next.matchTelephonyCallbackEvent(13) && idMatch(next, i2, i)) {
                            try {
                                next.callback.onPreciseDataConnectionStateChanged(preciseDataConnectionState);
                            } catch (android.os.RemoteException e) {
                                this.mRemoveList.add(next.binder);
                            }
                        }
                    }
                    handleRemoveListLocked();
                    broadcastDataConnectionStateChanged(i, i2, preciseDataConnectionState);
                    java.lang.String str = "notifyDataConnectionForSubscriber: phoneId=" + i + " subId=" + i2 + " " + preciseDataConnectionState;
                    log(str);
                    this.mLocalLog.log(str);
                }
                if (preciseDataConnectionState.getState() != 0) {
                    this.mPreciseDataConnectionStates.get(i).put(create, preciseDataConnectionState);
                }
                android.util.ArrayMap arrayMap = new android.util.ArrayMap();
                int i4 = 0;
                if (preciseDataConnectionState.getState() == 0 && preciseDataConnectionState.getApnSetting().getApnTypes().contains(17)) {
                    arrayMap.put(0, preciseDataConnectionState);
                }
                for (java.util.Map.Entry<android.util.Pair<java.lang.Integer, android.telephony.data.ApnSetting>, android.telephony.PreciseDataConnectionState> entry : this.mPreciseDataConnectionStates.get(i).entrySet()) {
                    if (((java.lang.Integer) entry.getKey().first).intValue() == 1 && ((android.telephony.data.ApnSetting) entry.getKey().second).getApnTypes().contains(17)) {
                        arrayMap.put(java.lang.Integer.valueOf(entry.getValue().getState()), entry.getValue());
                    }
                }
                int[] iArr = {2, 3, 1, 4, 0};
                int i5 = 0;
                while (true) {
                    if (i5 >= 5) {
                        i3 = 0;
                        break;
                    }
                    int i6 = iArr[i5];
                    if (!arrayMap.containsKey(java.lang.Integer.valueOf(i6))) {
                        i5++;
                    } else {
                        i3 = ((android.telephony.PreciseDataConnectionState) arrayMap.get(java.lang.Integer.valueOf(i6))).getNetworkType();
                        i4 = i6;
                        break;
                    }
                }
                if (this.mDataConnectionState[i] != i4 || this.mDataConnectionNetworkType[i] != i3) {
                    java.lang.String str2 = "onDataConnectionStateChanged(" + com.android.internal.telephony.util.TelephonyUtils.dataStateToString(i4) + ", " + android.telephony.TelephonyManager.getNetworkTypeName(i3) + ") subId=" + i2 + ", phoneId=" + i;
                    log(str2);
                    this.mLocalLog.log(str2);
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it2 = this.mRecords.iterator();
                    while (it2.hasNext()) {
                        com.android.server.TelephonyRegistry.Record next2 = it2.next();
                        if (next2.matchTelephonyCallbackEvent(7) && idMatch(next2, i2, i)) {
                            try {
                                next2.callback.onDataConnectionStateChanged(i4, i3);
                            } catch (android.os.RemoteException e2) {
                                this.mRemoveList.add(next2.binder);
                            }
                        }
                    }
                    this.mDataConnectionState[i] = i4;
                    this.mDataConnectionNetworkType[i] = i3;
                    handleRemoveListLocked();
                }
            }
        }
    }

    public void notifyCellLocationForSubscriber(int i, android.telephony.CellIdentity cellIdentity) {
        notifyCellLocationForSubscriber(i, cellIdentity, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCellLocationForSubscriber(int i, android.telephony.CellIdentity cellIdentity, boolean z) {
        log("notifyCellLocationForSubscriber: subId=" + i + " cellIdentity=" + android.telephony.Rlog.pii(false, cellIdentity));
        if (!checkNotifyPermission("notifyCellLocation()")) {
            return;
        }
        int phoneIdFromSubId = getPhoneIdFromSubId(i);
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(phoneIdFromSubId)) {
                    if (!z) {
                        if (!java.util.Objects.equals(cellIdentity, this.mCellIdentity[phoneIdFromSubId])) {
                        }
                    }
                    this.mCellIdentity[phoneIdFromSubId] = cellIdentity;
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        com.android.server.TelephonyRegistry.Record next = it.next();
                        if (validateEventAndUserLocked(next, 5) && idMatch(next, i, phoneIdFromSubId) && checkCoarseLocationAccess(next, 1) && checkFineLocationAccess(next, 29)) {
                            try {
                                next.callback.onCellLocationChanged(cellIdentity);
                            } catch (android.os.RemoteException e) {
                                this.mRemoveList.add(next.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyPreciseCallState(int i, int i2, int[] iArr, java.lang.String[] strArr, int[] iArr2, int[] iArr3) {
        boolean z;
        boolean z2;
        if (!checkNotifyPermission("notifyPreciseCallState()")) {
            return;
        }
        boolean z3 = false;
        int i3 = iArr[0];
        int i4 = iArr[1];
        int i5 = iArr[2];
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(i)) {
                    this.mRingingCallState[i] = i3;
                    this.mForegroundCallState[i] = i4;
                    this.mBackgroundCallState[i] = i5;
                    android.telephony.PreciseCallState preciseCallState = new android.telephony.PreciseCallState(i3, i4, i5, -1, -1);
                    if (preciseCallState.equals(this.mPreciseCallState[i])) {
                        z = false;
                    } else {
                        this.mPreciseCallState[i] = preciseCallState;
                        z = true;
                    }
                    if (this.mCallQuality != null) {
                        if (this.mPreciseCallState[i].getForegroundCallState() != 1) {
                            this.mCallNetworkType[i] = 0;
                            this.mCallQuality[i] = createCallQuality();
                        }
                        java.util.ArrayList arrayList = new java.util.ArrayList();
                        arrayList.addAll(this.mCallStateLists.get(i));
                        this.mCallStateLists.get(i).clear();
                        if (i4 != -1 && i4 != 0) {
                            android.telephony.CallState.Builder callClassification = new android.telephony.CallState.Builder(iArr[1]).setNetworkType(this.mCallNetworkType[i]).setCallQuality(this.mCallQuality[i]).setCallClassification(1);
                            if (strArr != null && iArr2 != null && iArr3 != null) {
                                callClassification = callClassification.setImsCallSessionId(strArr[1]).setImsCallServiceType(iArr2[1]).setImsCallType(iArr3[1]);
                            }
                            this.mCallStateLists.get(i).add(callClassification.build());
                        }
                        if (i5 != -1 && i5 != 0) {
                            android.telephony.CallState.Builder callClassification2 = new android.telephony.CallState.Builder(iArr[2]).setNetworkType(this.mCallNetworkType[i]).setCallQuality(createCallQuality()).setCallClassification(2);
                            if (strArr != null && iArr2 != null && iArr3 != null) {
                                callClassification2 = callClassification2.setImsCallSessionId(strArr[2]).setImsCallServiceType(iArr2[2]).setImsCallType(iArr3[2]);
                            }
                            this.mCallStateLists.get(i).add(callClassification2.build());
                        }
                        if (i3 != -1 && i3 != 0) {
                            android.telephony.CallState.Builder callClassification3 = new android.telephony.CallState.Builder(iArr[0]).setNetworkType(this.mCallNetworkType[i]).setCallQuality(createCallQuality()).setCallClassification(0);
                            if (strArr != null && iArr2 != null && iArr3 != null) {
                                callClassification3 = callClassification3.setImsCallSessionId(strArr[0]).setImsCallServiceType(iArr2[0]).setImsCallType(iArr3[0]);
                            }
                            this.mCallStateLists.get(i).add(callClassification3.build());
                        }
                        if (!arrayList.equals(this.mCallStateLists.get(i))) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        java.util.Iterator<android.telephony.CallState> it = this.mCallStateLists.get(i).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            } else if (it.next().getCallState() != 7) {
                                z3 = true;
                                break;
                            }
                        }
                        if (!z3) {
                            this.mMediaQualityStatus.get(i).clear();
                        }
                    } else {
                        log("notifyPreciseCallState: mCallQuality is null, skipping call attributes");
                        z2 = false;
                    }
                    if (z) {
                        java.util.Iterator<com.android.server.TelephonyRegistry.Record> it2 = this.mRecords.iterator();
                        while (it2.hasNext()) {
                            com.android.server.TelephonyRegistry.Record next = it2.next();
                            if (next.matchTelephonyCallbackEvent(12) && idMatch(next, i2, i)) {
                                try {
                                    next.callback.onPreciseCallStateChanged(this.mPreciseCallState[i]);
                                } catch (android.os.RemoteException e) {
                                    this.mRemoveList.add(next.binder);
                                }
                            }
                        }
                    }
                    if (z2) {
                        java.util.Iterator<com.android.server.TelephonyRegistry.Record> it3 = this.mRecords.iterator();
                        while (it3.hasNext()) {
                            com.android.server.TelephonyRegistry.Record next2 = it3.next();
                            if (next2.matchTelephonyCallbackEvent(27) && idMatch(next2, i2, i)) {
                                try {
                                    next2.callback.onCallStatesChanged(this.mCallStateLists.get(i));
                                } catch (android.os.RemoteException e2) {
                                    this.mRemoveList.add(next2.binder);
                                }
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyDisconnectCause(int i, int i2, int i3, int i4) {
        if (!checkNotifyPermission("notifyDisconnectCause()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mCallDisconnectCause[i] = i3;
                this.mCallPreciseDisconnectCause[i] = i4;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(26) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onCallDisconnectCauseChanged(this.mCallDisconnectCause[i], this.mCallPreciseDisconnectCause[i]);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyImsDisconnectCause(int i, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        if (!checkNotifyPermission("notifyImsCallDisconnectCause()")) {
            return;
        }
        int phoneIdFromSubId = getPhoneIdFromSubId(i);
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(phoneIdFromSubId)) {
                    if (imsReasonInfo == null) {
                        loge("ImsReasonInfo is null, subId=" + i + ", phoneId=" + phoneIdFromSubId);
                        this.mImsReasonInfo.set(phoneIdFromSubId, new android.telephony.ims.ImsReasonInfo());
                        return;
                    }
                    this.mImsReasonInfo.set(phoneIdFromSubId, imsReasonInfo);
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        com.android.server.TelephonyRegistry.Record next = it.next();
                        if (next.matchTelephonyCallbackEvent(28) && idMatch(next, i, phoneIdFromSubId)) {
                            try {
                                next.callback.onImsCallDisconnectCauseChanged(this.mImsReasonInfo.get(phoneIdFromSubId));
                            } catch (android.os.RemoteException e) {
                                this.mRemoveList.add(next.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifySrvccStateChanged(int i, int i2) {
        if (!checkNotifyPermission("notifySrvccStateChanged()")) {
            return;
        }
        int phoneIdFromSubId = getPhoneIdFromSubId(i);
        synchronized (this.mRecords) {
            if (validatePhoneId(phoneIdFromSubId)) {
                this.mSrvccState[phoneIdFromSubId] = i2;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(16) && idMatch(next, i, phoneIdFromSubId)) {
                        try {
                            next.callback.onSrvccStateChanged(i2);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyOemHookRawEventForSubscriber(int i, int i2, byte[] bArr) {
        if (!checkNotifyPermission("notifyOemHookRawEventForSubscriber")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(15) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onOemHookRawEvent(bArr);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyPhoneCapabilityChanged(android.telephony.PhoneCapability phoneCapability) {
        if (!checkNotifyPermission("notifyPhoneCapabilityChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            this.mPhoneCapability = phoneCapability;
            java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
            while (it.hasNext()) {
                com.android.server.TelephonyRegistry.Record next = it.next();
                if (next.matchTelephonyCallbackEvent(22)) {
                    try {
                        next.callback.onPhoneCapabilityChanged(phoneCapability);
                    } catch (android.os.RemoteException e) {
                        this.mRemoveList.add(next.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyActiveDataSubIdChanged(int i) {
        if (!checkNotifyPermission("notifyActiveDataSubIdChanged()")) {
            return;
        }
        log("notifyActiveDataSubIdChanged: activeDataSubId=" + i);
        this.mLocalLog.log("notifyActiveDataSubIdChanged: activeDataSubId=" + i);
        this.mActiveDataSubId = i;
        synchronized (this.mRecords) {
            java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
            while (it.hasNext()) {
                com.android.server.TelephonyRegistry.Record next = it.next();
                if (next.matchTelephonyCallbackEvent(23)) {
                    try {
                        next.callback.onActiveDataSubIdChanged(i);
                    } catch (android.os.RemoteException e) {
                        this.mRemoveList.add(next.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyRadioPowerStateChanged(int i, int i2, int i3) {
        if (!checkNotifyPermission("notifyRadioPowerStateChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mRadioPowerState = i3;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(24) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onRadioPowerStateChanged(i3);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyEmergencyNumberList(int i, int i2) {
        if (!checkNotifyPermission("notifyEmergencyNumberList()")) {
            return;
        }
        if (com.android.internal.telephony.flags.Flags.enforceTelephonyFeatureMappingForPublicApis() && !this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony.calling")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mEmergencyNumberList = ((android.telephony.TelephonyManager) this.mContext.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE)).getEmergencyNumberList();
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(25) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onEmergencyNumberListChanged(this.mEmergencyNumberList);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyOutgoingEmergencyCall(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) {
        if (!checkNotifyPermission("notifyOutgoingEmergencyCall()")) {
            return;
        }
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(i)) {
                    this.mOutgoingCallEmergencyNumber[i] = emergencyNumber;
                }
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(29)) {
                        try {
                            next.callback.onOutgoingEmergencyCall(emergencyNumber, i2);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        handleRemoveListLocked();
    }

    public void notifyOutgoingEmergencySms(int i, int i2, android.telephony.emergency.EmergencyNumber emergencyNumber) {
        if (!checkNotifyPermission("notifyOutgoingEmergencySms()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mOutgoingSmsEmergencyNumber[i] = emergencyNumber;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(30)) {
                        try {
                            next.callback.onOutgoingEmergencySms(emergencyNumber, i2);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyCallQualityChanged(android.telephony.CallQuality callQuality, int i, int i2, int i3) {
        if (!checkNotifyPermission("notifyCallQualityChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mCallQuality[i] = callQuality;
                this.mCallNetworkType[i] = i3;
                if (this.mCallStateLists.get(i).size() > 0 && this.mCallStateLists.get(i).get(0).getCallState() == 1) {
                    android.telephony.CallState remove = this.mCallStateLists.get(i).remove(0);
                    this.mCallStateLists.get(i).add(0, new android.telephony.CallState.Builder(remove.getCallState()).setNetworkType(i3).setCallQuality(callQuality).setCallClassification(remove.getCallClassification()).setImsCallSessionId(remove.getImsCallSessionId()).setImsCallServiceType(remove.getImsCallServiceType()).setImsCallType(remove.getImsCallType()).build());
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        com.android.server.TelephonyRegistry.Record next = it.next();
                        if (next.matchTelephonyCallbackEvent(27) && idMatch(next, i2, i)) {
                            try {
                                next.callback.onCallStatesChanged(this.mCallStateLists.get(i));
                            } catch (android.os.RemoteException e) {
                                this.mRemoveList.add(next.binder);
                            }
                        }
                    }
                } else {
                    log("There is no active call to report CallQuality");
                    return;
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyRegistrationFailed(int i, int i2, @android.annotation.NonNull android.telephony.CellIdentity cellIdentity, @android.annotation.NonNull java.lang.String str, int i3, int i4, int i5) {
        com.android.server.TelephonyRegistry.Record record;
        int i6 = i;
        if (!checkNotifyPermission("notifyRegistrationFailed()")) {
            return;
        }
        android.telephony.CellIdentity sanitizeLocationInfo = cellIdentity.sanitizeLocationInfo();
        this.mLocalLog.log("Registration Failed for phoneId=" + i6 + " subId=" + i2 + "primaryPlmn=" + cellIdentity.getPlmn() + " chosenPlmn=" + str + " domain=" + i3 + " causeCode=" + i4 + " additionalCauseCode=" + i5);
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(31) && idMatch(next, i2, i6)) {
                        try {
                            record = next;
                        } catch (android.os.RemoteException e) {
                            record = next;
                        }
                        try {
                            next.callback.onRegistrationFailed(checkFineLocationAccess(next, 1) ? cellIdentity : sanitizeLocationInfo, str, i3, i4, i5);
                        } catch (android.os.RemoteException e2) {
                            this.mRemoveList.add(record.binder);
                            i6 = i;
                        }
                    }
                    i6 = i;
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyBarringInfoChanged(int i, int i2, @android.annotation.NonNull android.telephony.BarringInfo barringInfo) {
        if (!checkNotifyPermission("notifyBarringInfo()")) {
            return;
        }
        if (!validatePhoneId(i)) {
            loge("Received invalid phoneId for BarringInfo = " + i);
            return;
        }
        synchronized (this.mRecords) {
            try {
                if (barringInfo == null) {
                    loge("Received null BarringInfo for subId=" + i2 + ", phoneId=" + i);
                    this.mBarringInfo.set(i, new android.telephony.BarringInfo());
                    return;
                }
                if (barringInfo.equals(this.mBarringInfo.get(i))) {
                    return;
                }
                this.mBarringInfo.set(i, barringInfo);
                android.telephony.BarringInfo createLocationInfoSanitizedCopy = barringInfo.createLocationInfoSanitizedCopy();
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(32) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onBarringInfoChanged(checkFineLocationAccess(next, 1) ? barringInfo : createLocationInfoSanitizedCopy);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyPhysicalChannelConfigForSubscriber(int i, int i2, java.util.List<android.telephony.PhysicalChannelConfig> list) {
        if (!checkNotifyPermission("notifyPhysicalChannelConfig()")) {
            return;
        }
        java.util.List<android.telephony.PhysicalChannelConfig> locationSanitizedConfigs = getLocationSanitizedConfigs(list);
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mPhysicalChannelConfigs.set(i, list);
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(33) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onPhysicalChannelConfigChanged(shouldSanitizeLocationForPhysicalChannelConfig(next) ? locationSanitizedConfigs : list);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    private static boolean shouldSanitizeLocationForPhysicalChannelConfig(com.android.server.TelephonyRegistry.Record record) {
        return (record.callerUid == 1001 || record.callerUid == 1000) ? false : true;
    }

    private static java.util.List<android.telephony.PhysicalChannelConfig> getLocationSanitizedConfigs(java.util.List<android.telephony.PhysicalChannelConfig> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        java.util.Iterator<android.telephony.PhysicalChannelConfig> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().createLocationInfoSanitizedCopy());
        }
        return arrayList;
    }

    public void notifyDataEnabled(int i, int i2, boolean z, int i3) {
        if (!checkNotifyPermission("notifyDataEnabled()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mIsDataEnabled[i] = z;
                this.mDataEnabledReason[i] = i3;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(34) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onDataEnabledChanged(z, i3);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) {
        if (!checkNotifyPermission("notifyAllowedNetworkTypesChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mAllowedNetworkTypeReason[i] = i3;
                this.mAllowedNetworkTypeValue[i] = j;
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(35) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onAllowedNetworkTypesChanged(i3, j);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyLinkCapacityEstimateChanged(int i, int i2, java.util.List<android.telephony.LinkCapacityEstimate> list) {
        if (!checkNotifyPermission("notifyLinkCapacityEstimateChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            if (validatePhoneId(i)) {
                this.mLinkCapacityEstimateLists.set(i, list);
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(37) && idMatch(next, i2, i)) {
                        try {
                            next.callback.onLinkCapacityEstimateChanged(list);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifySimultaneousCellularCallingSubscriptionsChanged(int[] iArr) {
        if (!checkNotifyPermission("notifySimultaneousCellularCallingSubscriptionsChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            this.mSimultaneousCellularCallingSubIds = iArr;
            java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
            while (it.hasNext()) {
                com.android.server.TelephonyRegistry.Record next = it.next();
                if (next.matchTelephonyCallbackEvent(41)) {
                    try {
                        next.callback.onSimultaneousCallingStateChanged(iArr);
                    } catch (android.os.RemoteException e) {
                        this.mRemoveList.add(next.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void addCarrierPrivilegesCallback(int i, @android.annotation.NonNull com.android.internal.telephony.ICarrierPrivilegesCallback iCarrierPrivilegesCallback, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.os.UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", "addCarrierPrivilegesCallback");
        onMultiSimConfigChanged();
        synchronized (this.mRecords) {
            try {
                if (!validatePhoneId(i)) {
                    throw new java.lang.IllegalArgumentException("Invalid slot index: " + i);
                }
                com.android.server.TelephonyRegistry.Record add = add(iCarrierPrivilegesCallback.asBinder(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), false);
                if (add == null) {
                    return;
                }
                add.context = this.mContext;
                add.carrierPrivilegesCallback = iCarrierPrivilegesCallback;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = android.os.Binder.getCallingUid();
                add.callerPid = android.os.Binder.getCallingPid();
                add.phoneId = i;
                add.eventList = new android.util.ArraySet();
                android.util.Pair<java.util.List<java.lang.String>, int[]> pair = this.mCarrierPrivilegeStates.get(i);
                android.util.Pair<java.lang.String, java.lang.Integer> pair2 = this.mCarrierServiceStates.get(i);
                try {
                    if (add.matchCarrierPrivilegesCallback()) {
                        add.carrierPrivilegesCallback.onCarrierPrivilegesChanged(java.util.Collections.unmodifiableList((java.util.List) pair.first), java.util.Arrays.copyOf((int[]) pair.second, ((int[]) pair.second).length));
                        add.carrierPrivilegesCallback.onCarrierServiceChanged((java.lang.String) pair2.first, ((java.lang.Integer) pair2.second).intValue());
                    }
                } catch (android.os.RemoteException e) {
                    remove(add.binder);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeCarrierPrivilegesCallback(@android.annotation.NonNull com.android.internal.telephony.ICarrierPrivilegesCallback iCarrierPrivilegesCallback, @android.annotation.NonNull java.lang.String str) {
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
        this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", "removeCarrierPrivilegesCallback");
        remove(iCarrierPrivilegesCallback.asBinder());
    }

    public void notifyCarrierPrivilegesChanged(int i, java.util.List<java.lang.String> list, int[] iArr) {
        if (!checkNotifyPermission("notifyCarrierPrivilegesChanged")) {
            return;
        }
        onMultiSimConfigChanged();
        synchronized (this.mRecords) {
            if (!validatePhoneId(i)) {
                throw new java.lang.IllegalArgumentException("Invalid slot index: " + i);
            }
            this.mCarrierPrivilegeStates.set(i, new android.util.Pair<>(list, iArr));
            java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
            while (it.hasNext()) {
                com.android.server.TelephonyRegistry.Record next = it.next();
                if (next.matchCarrierPrivilegesCallback() && idMatch(next, -1, i)) {
                    try {
                        next.carrierPrivilegesCallback.onCarrierPrivilegesChanged(java.util.Collections.unmodifiableList(list), java.util.Arrays.copyOf(iArr, iArr.length));
                    } catch (android.os.RemoteException e) {
                        this.mRemoveList.add(next.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyCarrierServiceChanged(int i, @android.annotation.Nullable java.lang.String str, int i2) {
        if (checkNotifyPermission("notifyCarrierServiceChanged") && validatePhoneId(i)) {
            onMultiSimConfigChanged();
            synchronized (this.mRecords) {
                this.mCarrierServiceStates.set(i, new android.util.Pair<>(str, java.lang.Integer.valueOf(i2)));
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchCarrierPrivilegesCallback() && idMatch(next, -1, i)) {
                        try {
                            next.carrierPrivilegesCallback.onCarrierServiceChanged(str, i2);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
                handleRemoveListLocked();
            }
        }
    }

    public void addCarrierConfigChangeListener(com.android.internal.telephony.ICarrierConfigChangeListener iCarrierConfigChangeListener, java.lang.String str, java.lang.String str2) {
        android.os.UserHandle.getCallingUserId();
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
        synchronized (this.mRecords) {
            try {
                com.android.server.TelephonyRegistry.Record add = add(iCarrierConfigChangeListener.asBinder(), android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), doesLimitApplyForListeners(android.os.Binder.getCallingUid(), android.os.Process.myUid()));
                if (add == null) {
                    loge("Can not create Record instance!");
                    return;
                }
                add.context = this.mContext;
                add.carrierConfigChangeListener = iCarrierConfigChangeListener;
                add.callingPackage = str;
                add.callingFeatureId = str2;
                add.callerUid = android.os.Binder.getCallingUid();
                add.callerPid = android.os.Binder.getCallingPid();
                add.eventList = new android.util.ArraySet();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeCarrierConfigChangeListener(com.android.internal.telephony.ICarrierConfigChangeListener iCarrierConfigChangeListener, java.lang.String str) {
        this.mAppOps.checkPackage(android.os.Binder.getCallingUid(), str);
        remove(iCarrierConfigChangeListener.asBinder());
    }

    public void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) {
        if (!validatePhoneId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid phoneId: " + i);
        }
        if (!checkNotifyPermission("notifyCarrierConfigChanged")) {
            loge("Caller has no notify permission!");
            return;
        }
        synchronized (this.mRecords) {
            this.mRemoveList.clear();
            java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
            while (it.hasNext()) {
                com.android.server.TelephonyRegistry.Record next = it.next();
                if (next.matchCarrierConfigChangeListener()) {
                    try {
                        next.carrierConfigChangeListener.onCarrierConfigChanged(i, i2, i3, i4);
                    } catch (android.os.RemoteException e) {
                        this.mRemoveList.add(next.binder);
                    }
                }
            }
            handleRemoveListLocked();
        }
    }

    public void notifyMediaQualityStatusChanged(int i, int i2, android.telephony.ims.MediaQualityStatus mediaQualityStatus) {
        android.telephony.CallState callState;
        if (!checkNotifyPermission("notifyMediaQualityStatusChanged()")) {
            return;
        }
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(i)) {
                    if (this.mCallStateLists.get(i).size() > 0) {
                        java.util.Iterator<android.telephony.CallState> it = this.mCallStateLists.get(i).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                callState = null;
                                break;
                            } else {
                                callState = it.next();
                                if (callState.getCallState() == 1) {
                                    break;
                                }
                            }
                        }
                        if (callState != null) {
                            java.lang.String imsCallSessionId = callState.getImsCallSessionId();
                            if (imsCallSessionId != null && imsCallSessionId.equals(mediaQualityStatus.getCallSessionId())) {
                                this.mMediaQualityStatus.get(i).put(mediaQualityStatus.getMediaSessionType(), mediaQualityStatus);
                            } else {
                                log("SessionId mismatch active call:" + imsCallSessionId + " media quality:" + mediaQualityStatus.getCallSessionId());
                                return;
                            }
                        } else {
                            log("There is no active call to report CallQaulity");
                            return;
                        }
                    }
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it2 = this.mRecords.iterator();
                    while (it2.hasNext()) {
                        com.android.server.TelephonyRegistry.Record next = it2.next();
                        if (next.matchTelephonyCallbackEvent(39) && idMatch(next, i2, i)) {
                            try {
                                next.callback.onMediaQualityStatusChanged(mediaQualityStatus);
                            } catch (android.os.RemoteException e) {
                                this.mRemoveList.add(next.binder);
                            }
                        }
                    }
                }
                handleRemoveListLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void notifyCallbackModeStarted(int i, int i2, int i3) {
        if (!checkNotifyPermission("notifyCallbackModeStarted()")) {
            return;
        }
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(i)) {
                    if (i3 == 1) {
                        this.mECBMStarted[i] = true;
                    } else if (i3 == 2) {
                        this.mSCBMStarted[i] = true;
                    }
                }
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(40)) {
                        try {
                            next.callback.onCallBackModeStarted(i3);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        handleRemoveListLocked();
    }

    public void notifyCallbackModeStopped(int i, int i2, int i3, int i4) {
        if (!checkNotifyPermission("notifyCallbackModeStopped()")) {
            return;
        }
        synchronized (this.mRecords) {
            try {
                if (validatePhoneId(i)) {
                    if (i3 == 1) {
                        this.mECBMStarted[i] = false;
                        this.mECBMReason[i] = i4;
                    } else if (i3 == 2) {
                        this.mSCBMStarted[i] = false;
                        this.mSCBMReason[i] = i4;
                    }
                }
                java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                while (it.hasNext()) {
                    com.android.server.TelephonyRegistry.Record next = it.next();
                    if (next.matchTelephonyCallbackEvent(40)) {
                        try {
                            next.callback.onCallBackModeStopped(i3, i4);
                        } catch (android.os.RemoteException e) {
                            this.mRemoveList.add(next.binder);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        handleRemoveListLocked();
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, indentingPrintWriter)) {
            synchronized (this.mRecords) {
                try {
                    int size = this.mRecords.size();
                    indentingPrintWriter.println("last known state:");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < getTelephonyManager().getActiveModemCount(); i++) {
                        indentingPrintWriter.println("Phone Id=" + i);
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println("mCallState=" + this.mCallState[i]);
                        indentingPrintWriter.println("mRingingCallState=" + this.mRingingCallState[i]);
                        indentingPrintWriter.println("mForegroundCallState=" + this.mForegroundCallState[i]);
                        indentingPrintWriter.println("mBackgroundCallState=" + this.mBackgroundCallState[i]);
                        indentingPrintWriter.println("mPreciseCallState=" + this.mPreciseCallState[i]);
                        indentingPrintWriter.println("mCallDisconnectCause=" + this.mCallDisconnectCause[i]);
                        indentingPrintWriter.println("mCallIncomingNumber=" + this.mCallIncomingNumber[i]);
                        indentingPrintWriter.println("mServiceState=" + this.mServiceState[i]);
                        indentingPrintWriter.println("mVoiceActivationState= " + this.mVoiceActivationState[i]);
                        indentingPrintWriter.println("mDataActivationState= " + this.mDataActivationState[i]);
                        indentingPrintWriter.println("mUserMobileDataState= " + this.mUserMobileDataState[i]);
                        indentingPrintWriter.println("mSignalStrength=" + this.mSignalStrength[i]);
                        indentingPrintWriter.println("mMessageWaiting=" + this.mMessageWaiting[i]);
                        indentingPrintWriter.println("mCallForwarding=" + this.mCallForwarding[i]);
                        indentingPrintWriter.println("mDataActivity=" + this.mDataActivity[i]);
                        indentingPrintWriter.println("mDataConnectionState=" + this.mDataConnectionState[i]);
                        indentingPrintWriter.println("mCellIdentity=" + this.mCellIdentity[i]);
                        indentingPrintWriter.println("mCellInfo=" + this.mCellInfo.get(i));
                        indentingPrintWriter.println("mImsCallDisconnectCause=" + this.mImsReasonInfo.get(i));
                        indentingPrintWriter.println("mSrvccState=" + this.mSrvccState[i]);
                        indentingPrintWriter.println("mCallPreciseDisconnectCause=" + this.mCallPreciseDisconnectCause[i]);
                        indentingPrintWriter.println("mCallQuality=" + this.mCallQuality[i]);
                        indentingPrintWriter.println("mCallNetworkType=" + this.mCallNetworkType[i]);
                        indentingPrintWriter.println("mPreciseDataConnectionStates=" + this.mPreciseDataConnectionStates.get(i));
                        indentingPrintWriter.println("mOutgoingCallEmergencyNumber=" + this.mOutgoingCallEmergencyNumber[i]);
                        indentingPrintWriter.println("mOutgoingSmsEmergencyNumber=" + this.mOutgoingSmsEmergencyNumber[i]);
                        indentingPrintWriter.println("mBarringInfo=" + this.mBarringInfo.get(i));
                        indentingPrintWriter.println("mCarrierNetworkChangeState=" + this.mCarrierNetworkChangeState[i]);
                        indentingPrintWriter.println("mTelephonyDisplayInfo=" + this.mTelephonyDisplayInfos[i]);
                        indentingPrintWriter.println("mIsDataEnabled=" + this.mIsDataEnabled[i]);
                        indentingPrintWriter.println("mDataEnabledReason=" + this.mDataEnabledReason[i]);
                        indentingPrintWriter.println("mAllowedNetworkTypeReason=" + this.mAllowedNetworkTypeReason[i]);
                        indentingPrintWriter.println("mAllowedNetworkTypeValue=" + this.mAllowedNetworkTypeValue[i]);
                        indentingPrintWriter.println("mPhysicalChannelConfigs=" + this.mPhysicalChannelConfigs.get(i));
                        indentingPrintWriter.println("mLinkCapacityEstimateList=" + this.mLinkCapacityEstimateLists.get(i));
                        indentingPrintWriter.println("mECBMReason=" + this.mECBMReason[i]);
                        indentingPrintWriter.println("mECBMStarted=" + this.mECBMStarted[i]);
                        indentingPrintWriter.println("mSCBMReason=" + this.mSCBMReason[i]);
                        indentingPrintWriter.println("mSCBMStarted=" + this.mSCBMStarted[i]);
                        android.util.Pair<java.util.List<java.lang.String>, int[]> pair = this.mCarrierPrivilegeStates.get(i);
                        indentingPrintWriter.println("mCarrierPrivilegeState=<packages=" + pii((java.util.List<java.lang.String>) pair.first) + ", uids=" + java.util.Arrays.toString((int[]) pair.second) + ">");
                        android.util.Pair<java.lang.String, java.lang.Integer> pair2 = this.mCarrierServiceStates.get(i);
                        indentingPrintWriter.println("mCarrierServiceState=<package=" + pii((java.lang.String) pair2.first) + ", uid=" + pair2.second + ">");
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.println("mPhoneCapability=" + this.mPhoneCapability);
                    indentingPrintWriter.println("mActiveDataSubId=" + this.mActiveDataSubId);
                    indentingPrintWriter.println("mRadioPowerState=" + this.mRadioPowerState);
                    indentingPrintWriter.println("mEmergencyNumberList=" + this.mEmergencyNumberList);
                    indentingPrintWriter.println("mDefaultPhoneId=" + this.mDefaultPhoneId);
                    indentingPrintWriter.println("mDefaultSubId=" + this.mDefaultSubId);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("local logs:");
                    indentingPrintWriter.increaseIndent();
                    this.mLocalLog.dump(fileDescriptor, indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("listen logs:");
                    indentingPrintWriter.increaseIndent();
                    this.mListenLog.dump(fileDescriptor, indentingPrintWriter, strArr);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("registrations: count=" + size);
                    indentingPrintWriter.increaseIndent();
                    java.util.Iterator<com.android.server.TelephonyRegistry.Record> it = this.mRecords.iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.println(it.next());
                    }
                    indentingPrintWriter.decreaseIndent();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void broadcastServiceStateChanged(android.telephony.ServiceState serviceState, int i, int i2) {
        try {
            this.mBatteryStats.notePhoneState(serviceState.getState());
        } catch (android.os.RemoteException e) {
        }
        if (!android.telephony.LocationAccessPolicy.isLocationModeEnabled(this.mContext, this.mContext.getUserId())) {
            java.lang.String[] strArr = (java.lang.String[]) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$$ExternalSyntheticLambda0
                public final java.lang.Object getOrThrow() {
                    java.lang.String[] lambda$broadcastServiceStateChanged$1;
                    lambda$broadcastServiceStateChanged$1 = com.android.server.TelephonyRegistry.this.lambda$broadcastServiceStateChanged$1();
                    return lambda$broadcastServiceStateChanged$1;
                }
            });
            for (java.lang.String str : strArr) {
                android.content.Intent createServiceStateIntent = createServiceStateIntent(serviceState, i2, i, false);
                createServiceStateIntent.setPackage(str);
                this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(createServiceStateIntent, new java.lang.String[]{"android.permission.READ_PHONE_STATE"}, createServiceStateBroadcastOptions(i2, i, "I:R"));
                this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(createServiceStateIntent, new java.lang.String[]{"android.permission.READ_PRIVILEGED_PHONE_STATE"}, new java.lang.String[]{"android.permission.READ_PHONE_STATE"}, null, createServiceStateBroadcastOptions(i2, i, "I:RP,E:R"));
            }
            android.content.Intent createServiceStateIntent2 = createServiceStateIntent(serviceState, i2, i, true);
            this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(createServiceStateIntent2, new java.lang.String[]{"android.permission.READ_PHONE_STATE"}, new java.lang.String[0], strArr, createServiceStateBroadcastOptions(i2, i, "I:R,lbp"));
            this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(createServiceStateIntent2, new java.lang.String[]{"android.permission.READ_PRIVILEGED_PHONE_STATE"}, new java.lang.String[]{"android.permission.READ_PHONE_STATE"}, strArr, createServiceStateBroadcastOptions(i2, i, "I:RP,E:R,lbp"));
            return;
        }
        android.content.Intent createServiceStateIntent3 = createServiceStateIntent(serviceState, i2, i, false);
        this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(createServiceStateIntent3, new java.lang.String[]{"android.permission.READ_PHONE_STATE", "android.permission.ACCESS_FINE_LOCATION"}, createServiceStateBroadcastOptions(i2, i, "I:RA"));
        this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(createServiceStateIntent3, new java.lang.String[]{"android.permission.READ_PRIVILEGED_PHONE_STATE", "android.permission.ACCESS_FINE_LOCATION"}, new java.lang.String[]{"android.permission.READ_PHONE_STATE"}, null, createServiceStateBroadcastOptions(i2, i, "I:RPA,E:R"));
        android.content.Intent createServiceStateIntent4 = createServiceStateIntent(serviceState, i2, i, true);
        this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(createServiceStateIntent4, new java.lang.String[]{"android.permission.READ_PHONE_STATE"}, new java.lang.String[]{"android.permission.ACCESS_FINE_LOCATION"}, null, createServiceStateBroadcastOptions(i2, i, "I:R,E:A"));
        this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(createServiceStateIntent4, new java.lang.String[]{"android.permission.READ_PRIVILEGED_PHONE_STATE"}, new java.lang.String[]{"android.permission.READ_PHONE_STATE", "android.permission.ACCESS_FINE_LOCATION"}, null, createServiceStateBroadcastOptions(i2, i, "I:RP,E:RA"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String[] lambda$broadcastServiceStateChanged$1() throws java.lang.Exception {
        return android.telephony.LocationAccessPolicy.getLocationBypassPackages(this.mContext);
    }

    private android.content.Intent createServiceStateIntent(android.telephony.ServiceState serviceState, int i, int i2, boolean z) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.SERVICE_STATE");
        intent.addFlags(16777216);
        android.os.Bundle bundle = new android.os.Bundle();
        if (z) {
            serviceState.createLocationInfoSanitizedCopy(true).fillInNotifierBundle(bundle);
        } else {
            serviceState.fillInNotifierBundle(bundle);
        }
        intent.putExtras(bundle);
        intent.putExtra(PHONE_CONSTANTS_SUBSCRIPTION_KEY, i);
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i);
        intent.putExtra(PHONE_CONSTANTS_SLOT_KEY, i2);
        intent.putExtra("android.telephony.extra.SLOT_INDEX", i2);
        return intent;
    }

    private android.app.BroadcastOptions createServiceStateBroadcastOptions(int i, int i2, java.lang.String str) {
        return new android.app.BroadcastOptions().setDeliveryGroupPolicy(1).setDeliveryGroupMatchingKey("android.intent.action.SERVICE_STATE", i + "-" + i2 + "-" + str).setDeferralPolicy(2);
    }

    private void broadcastSignalStrengthChanged(android.telephony.SignalStrength signalStrength, int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mBatteryStats.notePhoneSignalStrength(signalStrength);
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        android.content.Intent intent = new android.content.Intent(ACTION_SIGNAL_STRENGTH_CHANGED);
        android.os.Bundle bundle = new android.os.Bundle();
        fillInSignalStrengthNotifierBundle(signalStrength, bundle);
        intent.putExtras(bundle);
        intent.putExtra(PHONE_CONSTANTS_SUBSCRIPTION_KEY, i2);
        intent.putExtra(PHONE_CONSTANTS_SLOT_KEY, i);
        this.mContext.sendStickyBroadcastAsUser(intent, android.os.UserHandle.ALL);
    }

    private void fillInSignalStrengthNotifierBundle(android.telephony.SignalStrength signalStrength, android.os.Bundle bundle) {
        for (android.telephony.CellSignalStrength cellSignalStrength : signalStrength.getCellSignalStrengths()) {
            if (cellSignalStrength instanceof android.telephony.CellSignalStrengthLte) {
                bundle.putParcelable("Lte", (android.telephony.CellSignalStrengthLte) cellSignalStrength);
            } else if (cellSignalStrength instanceof android.telephony.CellSignalStrengthCdma) {
                bundle.putParcelable("Cdma", (android.telephony.CellSignalStrengthCdma) cellSignalStrength);
            } else if (cellSignalStrength instanceof android.telephony.CellSignalStrengthGsm) {
                bundle.putParcelable("Gsm", (android.telephony.CellSignalStrengthGsm) cellSignalStrength);
            } else if (cellSignalStrength instanceof android.telephony.CellSignalStrengthWcdma) {
                bundle.putParcelable("Wcdma", (android.telephony.CellSignalStrengthWcdma) cellSignalStrength);
            } else if (cellSignalStrength instanceof android.telephony.CellSignalStrengthTdscdma) {
                bundle.putParcelable("Tdscdma", (android.telephony.CellSignalStrengthTdscdma) cellSignalStrength);
            } else if (cellSignalStrength instanceof android.telephony.CellSignalStrengthNr) {
                bundle.putParcelable("Nr", (android.telephony.CellSignalStrengthNr) cellSignalStrength);
            }
        }
    }

    private void broadcastCallStateChanged(int i, java.lang.String str, int i2, int i3) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (i == 0) {
                this.mBatteryStats.notePhoneOff();
                com.android.internal.util.FrameworkStatsLog.write(95, 0);
            } else {
                this.mBatteryStats.notePhoneOn();
                com.android.internal.util.FrameworkStatsLog.write(95, 1);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        android.content.Intent intent = new android.content.Intent("android.intent.action.PHONE_STATE");
        intent.putExtra("state", callStateToString(i));
        if (i3 != -1) {
            intent.setAction(ACTION_SUBSCRIPTION_PHONE_STATE_CHANGED);
            intent.putExtra(PHONE_CONSTANTS_SUBSCRIPTION_KEY, i3);
            intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", i3);
        }
        if (i2 != -1) {
            intent.putExtra(PHONE_CONSTANTS_SLOT_KEY, i2);
            intent.putExtra("android.telephony.extra.SLOT_INDEX", i2);
        }
        intent.addFlags(16777216);
        android.content.Intent intent2 = new android.content.Intent(intent);
        intent2.putExtra("incoming_number", str);
        this.mContext.sendBroadcastAsUser(intent2, android.os.UserHandle.ALL, "android.permission.READ_PRIVILEGED_PHONE_STATE");
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.READ_PHONE_STATE", 51);
        this.mContext.sendBroadcastAsUserMultiplePermissions(intent2, android.os.UserHandle.ALL, new java.lang.String[]{"android.permission.READ_PHONE_STATE", "android.permission.READ_CALL_LOG"});
    }

    private static java.lang.String callStateToString(int i) {
        switch (i) {
            case 1:
                return android.telephony.TelephonyManager.EXTRA_STATE_RINGING;
            case 2:
                return android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK;
            default:
                return android.telephony.TelephonyManager.EXTRA_STATE_IDLE;
        }
    }

    private void broadcastDataConnectionStateChanged(int i, int i2, @android.annotation.NonNull android.telephony.PreciseDataConnectionState preciseDataConnectionState) {
        android.content.Intent intent = new android.content.Intent(ACTION_ANY_DATA_CONNECTION_STATE_CHANGED);
        intent.putExtra("state", com.android.internal.telephony.util.TelephonyUtils.dataStateToString(preciseDataConnectionState.getState()));
        intent.putExtra(PHONE_CONSTANTS_DATA_APN_KEY, preciseDataConnectionState.getApnSetting().getApnName());
        intent.putExtra(PHONE_CONSTANTS_DATA_APN_TYPE_KEY, getApnTypesStringFromBitmask(preciseDataConnectionState.getApnSetting().getApnTypeBitmask()));
        intent.putExtra(PHONE_CONSTANTS_SLOT_KEY, i);
        intent.putExtra(PHONE_CONSTANTS_SUBSCRIPTION_KEY, i2);
        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL, "android.permission.READ_PHONE_STATE");
        this.mContext.createContextAsUser(android.os.UserHandle.ALL, 0).sendBroadcastMultiplePermissions(intent, new java.lang.String[]{"android.permission.READ_PRIVILEGED_PHONE_STATE"}, new java.lang.String[]{"android.permission.READ_PHONE_STATE"});
    }

    @com.android.internal.annotations.VisibleForTesting
    public static java.lang.String getApnTypesStringFromBitmask(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 17) == 17) {
            arrayList.add("default");
            i &= -18;
        }
        while (i != 0) {
            int highestOneBit = java.lang.Integer.highestOneBit(i);
            java.lang.String apnTypeString = android.telephony.data.ApnSetting.getApnTypeString(highestOneBit);
            if (!android.text.TextUtils.isEmpty(apnTypeString)) {
                arrayList.add(apnTypeString);
            }
            i &= ~highestOneBit;
        }
        return android.text.TextUtils.join(",", arrayList);
    }

    private void enforceNotifyPermissionOrCarrierPrivilege(java.lang.String str) {
        if (checkNotifyPermission()) {
            return;
        }
        com.android.internal.telephony.TelephonyPermissions.enforceCallingOrSelfCarrierPrivilege(this.mContext, android.telephony.SubscriptionManager.getDefaultSubscriptionId(), str);
    }

    private boolean checkNotifyPermission(java.lang.String str) {
        if (checkNotifyPermission()) {
            return true;
        }
        java.lang.String str2 = "Modify Phone State Permission Denial: " + str + " from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid();
        return false;
    }

    private boolean checkNotifyPermission() {
        return this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean checkListenerPermission(java.util.Set<java.lang.Integer> set, int i, java.lang.String str, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        boolean z;
        if (isLocationPermissionRequired(set)) {
            android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder callingUid = new android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder().setCallingPackage(str).setCallingFeatureId(str2).setMethod(str3 + " events: " + set).setCallingPid(android.os.Binder.getCallingPid()).setCallingUid(android.os.Binder.getCallingUid());
            callingUid.setMinSdkVersionForFine(29);
            callingUid.setMinSdkVersionForCoarse(0);
            callingUid.setMinSdkVersionForEnforcement(0);
            switch (com.android.server.TelephonyRegistry.AnonymousClass3.$SwitchMap$android$telephony$LocationAccessPolicy$LocationPermissionResult[android.telephony.LocationAccessPolicy.checkLocationPermission(this.mContext, callingUid.build()).ordinal()]) {
                case 1:
                    throw new java.lang.SecurityException("Unable to listen for events " + set + " due to insufficient location permissions.");
                case 2:
                    z = false;
                    break;
            }
            boolean z2 = (isPhoneStatePermissionRequired(set, str, android.os.Binder.getCallingUserHandle()) || com.android.internal.telephony.TelephonyPermissions.checkCallingOrSelfReadPhoneState(this.mContext, i, str, str2, str3)) ? z : false;
            if (isPrecisePhoneStatePermissionRequired(set)) {
                try {
                    this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRECISE_PHONE_STATE", null);
                } catch (java.lang.SecurityException e) {
                    com.android.internal.telephony.TelephonyPermissions.enforceCallingOrSelfCarrierPrivilege(this.mContext, i, str3);
                }
            }
            if (isActiveEmergencySessionPermissionRequired(set)) {
                this.mContext.enforceCallingOrSelfPermission("android.permission.READ_ACTIVE_EMERGENCY_SESSION", null);
            }
            if (isPrivilegedPhoneStatePermissionRequired(set)) {
                this.mContext.enforceCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE", null);
            }
            return z2;
        }
        z = true;
        if (isPhoneStatePermissionRequired(set, str, android.os.Binder.getCallingUserHandle())) {
        }
        if (isPrecisePhoneStatePermissionRequired(set)) {
        }
        if (isActiveEmergencySessionPermissionRequired(set)) {
        }
        if (isPrivilegedPhoneStatePermissionRequired(set)) {
        }
        return z2;
    }

    /* renamed from: com.android.server.TelephonyRegistry$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$android$telephony$LocationAccessPolicy$LocationPermissionResult = new int[android.telephony.LocationAccessPolicy.LocationPermissionResult.values().length];

        static {
            try {
                $SwitchMap$android$telephony$LocationAccessPolicy$LocationPermissionResult[android.telephony.LocationAccessPolicy.LocationPermissionResult.DENIED_HARD.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$telephony$LocationAccessPolicy$LocationPermissionResult[android.telephony.LocationAccessPolicy.LocationPermissionResult.DENIED_SOFT.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveListLocked() {
        if (this.mRemoveList.size() > 0) {
            java.util.Iterator<android.os.IBinder> it = this.mRemoveList.iterator();
            while (it.hasNext()) {
                remove(it.next());
            }
            this.mRemoveList.clear();
        }
    }

    private boolean validateEventAndUserLocked(com.android.server.TelephonyRegistry.Record record, int i) {
        boolean z;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (android.os.UserHandle.getUserId(record.callerUid) == android.app.ActivityManager.getCurrentUser()) {
                if (record.matchTelephonyCallbackEvent(i)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean validatePhoneId(int i) {
        return i >= 0 && i < getTelephonyManager().getActiveModemCount();
    }

    private static void log(java.lang.String str) {
        android.telephony.Rlog.d(TAG, str);
    }

    private static void loge(java.lang.String str) {
        android.telephony.Rlog.e(TAG, str);
    }

    boolean idMatch(com.android.server.TelephonyRegistry.Record record, int i, int i2) {
        return i < 0 ? record.phoneId == i2 : record.subId == Integer.MAX_VALUE ? i == this.mDefaultSubId : record.subId == i;
    }

    private boolean checkFineLocationAccess(com.android.server.TelephonyRegistry.Record record) {
        return checkFineLocationAccess(record, 1);
    }

    private boolean checkCoarseLocationAccess(com.android.server.TelephonyRegistry.Record record) {
        return checkCoarseLocationAccess(record, 1);
    }

    private boolean checkFineLocationAccess(com.android.server.TelephonyRegistry.Record record, int i) {
        if (record.renounceFineLocationAccess) {
            return false;
        }
        final android.telephony.LocationAccessPolicy.LocationPermissionQuery build = new android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder().setCallingPackage(record.callingPackage).setCallingFeatureId(record.callingFeatureId).setCallingPid(record.callerPid).setCallingUid(record.callerUid).setMethod("TelephonyRegistry push").setLogAsInfo(true).setMinSdkVersionForFine(i).setMinSdkVersionForCoarse(i).setMinSdkVersionForEnforcement(i).build();
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$$ExternalSyntheticLambda2
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$checkFineLocationAccess$2;
                lambda$checkFineLocationAccess$2 = com.android.server.TelephonyRegistry.this.lambda$checkFineLocationAccess$2(build);
                return lambda$checkFineLocationAccess$2;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$checkFineLocationAccess$2(android.telephony.LocationAccessPolicy.LocationPermissionQuery locationPermissionQuery) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(android.telephony.LocationAccessPolicy.checkLocationPermission(this.mContext, locationPermissionQuery) == android.telephony.LocationAccessPolicy.LocationPermissionResult.ALLOWED);
    }

    private boolean checkCoarseLocationAccess(com.android.server.TelephonyRegistry.Record record, int i) {
        if (record.renounceCoarseLocationAccess) {
            return false;
        }
        final android.telephony.LocationAccessPolicy.LocationPermissionQuery build = new android.telephony.LocationAccessPolicy.LocationPermissionQuery.Builder().setCallingPackage(record.callingPackage).setCallingFeatureId(record.callingFeatureId).setCallingPid(record.callerPid).setCallingUid(record.callerUid).setMethod("TelephonyRegistry push").setLogAsInfo(true).setMinSdkVersionForCoarse(i).setMinSdkVersionForFine(Integer.MAX_VALUE).setMinSdkVersionForEnforcement(i).build();
        return ((java.lang.Boolean) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.TelephonyRegistry$$ExternalSyntheticLambda3
            public final java.lang.Object getOrThrow() {
                java.lang.Boolean lambda$checkCoarseLocationAccess$3;
                lambda$checkCoarseLocationAccess$3 = com.android.server.TelephonyRegistry.this.lambda$checkCoarseLocationAccess$3(build);
                return lambda$checkCoarseLocationAccess$3;
            }
        })).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Boolean lambda$checkCoarseLocationAccess$3(android.telephony.LocationAccessPolicy.LocationPermissionQuery locationPermissionQuery) throws java.lang.Exception {
        return java.lang.Boolean.valueOf(android.telephony.LocationAccessPolicy.checkLocationPermission(this.mContext, locationPermissionQuery) == android.telephony.LocationAccessPolicy.LocationPermissionResult.ALLOWED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkPossibleMissNotify(com.android.server.TelephonyRegistry.Record record, int i) {
        java.util.Set<java.lang.Integer> set = record.eventList;
        if (set == null || set.isEmpty()) {
            log("checkPossibleMissNotify: events = null.");
            return;
        }
        if (set.contains(1)) {
            try {
                android.telephony.ServiceState serviceState = new android.telephony.ServiceState(this.mServiceState[i]);
                if (checkFineLocationAccess(record, 29)) {
                    record.callback.onServiceStateChanged(serviceState);
                } else if (checkCoarseLocationAccess(record, 29)) {
                    record.callback.onServiceStateChanged(serviceState.createLocationInfoSanitizedCopy(false));
                } else {
                    record.callback.onServiceStateChanged(serviceState.createLocationInfoSanitizedCopy(true));
                }
            } catch (android.os.RemoteException e) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(9)) {
            try {
                if (this.mSignalStrength[i] != null) {
                    record.callback.onSignalStrengthsChanged(new android.telephony.SignalStrength(this.mSignalStrength[i]));
                }
            } catch (android.os.RemoteException e2) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(2)) {
            try {
                if (this.mSignalStrength[i] != null) {
                    int gsmSignalStrength = this.mSignalStrength[i].getGsmSignalStrength();
                    com.android.internal.telephony.IPhoneStateListener iPhoneStateListener = record.callback;
                    if (gsmSignalStrength == 99) {
                        gsmSignalStrength = -1;
                    }
                    iPhoneStateListener.onSignalStrengthChanged(gsmSignalStrength);
                }
            } catch (android.os.RemoteException e3) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (validateEventAndUserLocked(record, 11)) {
            try {
                if (checkCoarseLocationAccess(record, 1) && checkFineLocationAccess(record, 29)) {
                    record.callback.onCellInfoChanged(this.mCellInfo.get(i));
                }
            } catch (android.os.RemoteException e4) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(20)) {
            try {
                record.callback.onUserMobileDataStateChanged(this.mUserMobileDataState[i]);
            } catch (android.os.RemoteException e5) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(21)) {
            try {
                if (this.mTelephonyDisplayInfos[i] != null) {
                    record.callback.onDisplayInfoChanged(this.mTelephonyDisplayInfos[i]);
                }
            } catch (android.os.RemoteException e6) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(3)) {
            try {
                record.callback.onMessageWaitingIndicatorChanged(this.mMessageWaiting[i]);
            } catch (android.os.RemoteException e7) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(4)) {
            try {
                record.callback.onCallForwardingIndicatorChanged(this.mCallForwarding[i]);
            } catch (android.os.RemoteException e8) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (validateEventAndUserLocked(record, 5)) {
            try {
                if (checkCoarseLocationAccess(record, 1) && checkFineLocationAccess(record, 29)) {
                    record.callback.onCellLocationChanged(this.mCellIdentity[i]);
                }
            } catch (android.os.RemoteException e9) {
                this.mRemoveList.add(record.binder);
            }
        }
        if (set.contains(7)) {
            try {
                record.callback.onDataConnectionStateChanged(this.mDataConnectionState[i], this.mDataConnectionNetworkType[i]);
            } catch (android.os.RemoteException e10) {
                this.mRemoveList.add(record.binder);
            }
        }
    }

    private java.lang.String getNetworkTypeName(int i) {
        switch (i) {
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "CDMA - EvDo rev. 0";
            case 6:
                return "CDMA - EvDo rev. A";
            case 7:
                return "CDMA - 1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "iDEN";
            case 12:
                return "CDMA - EvDo rev. B";
            case 13:
                return "LTE";
            case 14:
                return "CDMA - eHRPD";
            case 15:
                return "HSPA+";
            case 16:
                return "GSM";
            case 17:
                return "TD_SCDMA";
            case 18:
                return "IWLAN";
            case 19:
            default:
                return "UNKNOWN";
            case 20:
                return "NR";
        }
    }

    private static android.telephony.PreciseCallState createPreciseCallState() {
        return new android.telephony.PreciseCallState(-1, -1, -1, -1, -1);
    }

    private static android.telephony.CallQuality createCallQuality() {
        return new android.telephony.CallQuality(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPhoneIdFromSubId(int i) {
        android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        if (subscriptionManager == null) {
            return -1;
        }
        if (i == Integer.MAX_VALUE) {
            i = android.telephony.SubscriptionManager.getDefaultSubscriptionId();
        }
        android.telephony.SubscriptionInfo activeSubscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(i);
        if (activeSubscriptionInfo == null) {
            return -1;
        }
        return activeSubscriptionInfo.getSimSlotIndex();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String pii(java.lang.String str) {
        return android.os.Build.IS_DEBUGGABLE ? str : "***";
    }

    private static java.lang.String pii(java.util.List<java.lang.String> list) {
        if (list.isEmpty() || android.os.Build.IS_DEBUGGABLE) {
            return list.toString();
        }
        return "[***, size=" + list.size() + "]";
    }
}
