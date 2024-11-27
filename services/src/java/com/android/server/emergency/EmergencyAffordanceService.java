package com.android.server.emergency;

/* loaded from: classes.dex */
public class EmergencyAffordanceService extends com.android.server.SystemService {
    private static final boolean DBG = false;
    private static final java.lang.String EMERGENCY_AFFORDANCE_OVERRIDE_ISO = "emergency_affordance_override_iso";
    private static final int INITIALIZE_STATE = 1;
    private static final int NETWORK_COUNTRY_CHANGED = 2;
    private static final java.lang.String SERVICE_NAME = "emergency_affordance";
    private static final int SUBSCRIPTION_CHANGED = 3;
    private static final java.lang.String TAG = "EmergencyAffordanceService";
    private static final int UPDATE_AIRPLANE_MODE_STATUS = 4;
    private boolean mAirplaneModeEnabled;
    private boolean mAnyNetworkNeedsEmergencyAffordance;
    private boolean mAnySimNeedsEmergencyAffordance;
    private android.content.BroadcastReceiver mBroadcastReceiver;
    private final android.content.Context mContext;
    private boolean mEmergencyAffordanceNeeded;
    private final java.util.ArrayList<java.lang.String> mEmergencyCallCountryIsos;
    private com.android.server.emergency.EmergencyAffordanceService.MyHandler mHandler;
    private android.telephony.SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionChangedListener;
    private android.telephony.SubscriptionManager mSubscriptionManager;
    private android.telephony.TelephonyManager mTelephonyManager;
    private boolean mVoiceCapable;

    public EmergencyAffordanceService(android.content.Context context) {
        super(context);
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.emergency.EmergencyAffordanceService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.telephony.action.NETWORK_COUNTRY_CHANGED".equals(intent.getAction())) {
                    java.lang.String stringExtra = intent.getStringExtra("android.telephony.extra.NETWORK_COUNTRY");
                    com.android.server.emergency.EmergencyAffordanceService.this.mHandler.obtainMessage(2, intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1), 0, stringExtra).sendToTarget();
                } else if ("android.intent.action.AIRPLANE_MODE".equals(intent.getAction())) {
                    com.android.server.emergency.EmergencyAffordanceService.this.mHandler.obtainMessage(4).sendToTarget();
                }
            }
        };
        this.mSubscriptionChangedListener = new android.telephony.SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.emergency.EmergencyAffordanceService.2
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public void onSubscriptionsChanged() {
                com.android.server.emergency.EmergencyAffordanceService.this.mHandler.obtainMessage(3).sendToTarget();
            }
        };
        this.mContext = context;
        java.lang.String[] stringArray = context.getResources().getStringArray(android.R.array.config_dozeTapSensorPostureMapping);
        this.mEmergencyCallCountryIsos = new java.util.ArrayList<>(stringArray.length);
        for (java.lang.String str : stringArray) {
            this.mEmergencyCallCountryIsos.add(str);
        }
        if (android.os.Build.IS_DEBUGGABLE) {
            java.lang.String string = android.provider.Settings.Global.getString(this.mContext.getContentResolver(), EMERGENCY_AFFORDANCE_OVERRIDE_ISO);
            if (!android.text.TextUtils.isEmpty(string)) {
                this.mEmergencyCallCountryIsos.clear();
                this.mEmergencyCallCountryIsos.add(string);
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService(SERVICE_NAME, new com.android.server.emergency.EmergencyAffordanceService.BinderService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 600) {
            handleThirdPartyBootPhase();
        }
    }

    private class MyHandler extends android.os.Handler {
        public MyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.emergency.EmergencyAffordanceService.this.handleInitializeState();
                    break;
                case 2:
                    com.android.server.emergency.EmergencyAffordanceService.this.handleNetworkCountryChanged((java.lang.String) message.obj, message.arg1);
                    break;
                case 3:
                    com.android.server.emergency.EmergencyAffordanceService.this.handleUpdateSimSubscriptionInfo();
                    break;
                case 4:
                    com.android.server.emergency.EmergencyAffordanceService.this.handleUpdateAirplaneModeStatus();
                    break;
                default:
                    android.util.Slog.e(com.android.server.emergency.EmergencyAffordanceService.TAG, "Unexpected message received: " + message.what);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInitializeState() {
        handleUpdateAirplaneModeStatus();
        handleUpdateSimSubscriptionInfo();
        updateNetworkCountry();
        updateEmergencyAffordanceNeeded();
    }

    private void handleThirdPartyBootPhase() {
        this.mTelephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
        this.mVoiceCapable = this.mTelephonyManager.isVoiceCapable();
        if (!this.mVoiceCapable) {
            updateEmergencyAffordanceNeeded();
            return;
        }
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new com.android.server.emergency.EmergencyAffordanceService.MyHandler(handlerThread.getLooper());
        this.mSubscriptionManager = android.telephony.SubscriptionManager.from(this.mContext);
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionChangedListener);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.telephony.action.NETWORK_COUNTRY_CHANGED");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdateAirplaneModeStatus() {
        this.mAirplaneModeEnabled = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdateSimSubscriptionInfo() {
        boolean z;
        java.util.List<android.telephony.SubscriptionInfo> activeSubscriptionInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            return;
        }
        java.util.Iterator<android.telephony.SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (isoRequiresEmergencyAffordance(it.next().getCountryIso())) {
                z = true;
                break;
            }
        }
        this.mAnySimNeedsEmergencyAffordance = z;
        updateEmergencyAffordanceNeeded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNetworkCountryChanged(java.lang.String str, int i) {
        if (android.text.TextUtils.isEmpty(str) && this.mAirplaneModeEnabled) {
            android.util.Slog.w(TAG, "Ignore empty countryIso report when APM is on.");
        } else {
            updateNetworkCountry();
            updateEmergencyAffordanceNeeded();
        }
    }

    private void updateNetworkCountry() {
        int activeModemCount = this.mTelephonyManager.getActiveModemCount();
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= activeModemCount) {
                break;
            }
            if (!isoRequiresEmergencyAffordance(this.mTelephonyManager.getNetworkCountryIso(i))) {
                i++;
            } else {
                z = true;
                break;
            }
        }
        this.mAnyNetworkNeedsEmergencyAffordance = z;
        updateEmergencyAffordanceNeeded();
    }

    private boolean isoRequiresEmergencyAffordance(java.lang.String str) {
        return this.mEmergencyCallCountryIsos.contains(str);
    }

    private void updateEmergencyAffordanceNeeded() {
        boolean z = this.mEmergencyAffordanceNeeded;
        this.mEmergencyAffordanceNeeded = this.mVoiceCapable && (this.mAnySimNeedsEmergencyAffordance || this.mAnyNetworkNeedsEmergencyAffordance);
        if (z != this.mEmergencyAffordanceNeeded) {
            android.provider.Settings.Global.putInt(this.mContext.getContentResolver(), "emergency_affordance_needed", this.mEmergencyAffordanceNeeded ? 1 : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpInternal(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("EmergencyAffordanceService (dumpsys emergency_affordance) state:\n");
        indentingPrintWriter.println("mEmergencyAffordanceNeeded=" + this.mEmergencyAffordanceNeeded);
        indentingPrintWriter.println("mVoiceCapable=" + this.mVoiceCapable);
        indentingPrintWriter.println("mAnySimNeedsEmergencyAffordance=" + this.mAnySimNeedsEmergencyAffordance);
        indentingPrintWriter.println("mAnyNetworkNeedsEmergencyAffordance=" + this.mAnyNetworkNeedsEmergencyAffordance);
        indentingPrintWriter.println("mEmergencyCallCountryIsos=" + java.lang.String.join(",", this.mEmergencyCallCountryIsos));
    }

    private final class BinderService extends android.os.Binder {
        private BinderService() {
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (!com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.emergency.EmergencyAffordanceService.this.mContext, com.android.server.emergency.EmergencyAffordanceService.TAG, printWriter)) {
                return;
            }
            com.android.server.emergency.EmergencyAffordanceService.this.dumpInternal(new com.android.internal.util.IndentingPrintWriter(printWriter, "  "));
        }
    }
}
