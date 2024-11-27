package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemEmergencyHelper extends com.android.server.location.injector.EmergencyHelper {
    private final android.content.Context mContext;
    boolean mIsInEmergencyCall;
    android.telephony.TelephonyManager mTelephonyManager;
    private final com.android.server.location.injector.SystemEmergencyHelper.EmergencyCallTelephonyCallback mEmergencyCallTelephonyCallback = new com.android.server.location.injector.SystemEmergencyHelper.EmergencyCallTelephonyCallback();
    long mEmergencyCallEndRealtimeMs = Long.MIN_VALUE;

    public SystemEmergencyHelper(android.content.Context context) {
        this.mContext = context;
    }

    public synchronized void onSystemReady() {
        if (this.mTelephonyManager != null) {
            return;
        }
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService(android.telephony.TelephonyManager.class);
        java.util.Objects.requireNonNull(telephonyManager);
        android.telephony.TelephonyManager telephonyManager2 = telephonyManager;
        this.mTelephonyManager = telephonyManager;
        this.mTelephonyManager.registerTelephonyCallback(com.android.server.FgThread.getExecutor(), this.mEmergencyCallTelephonyCallback);
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.location.injector.SystemEmergencyHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (!"android.intent.action.NEW_OUTGOING_CALL".equals(intent.getAction())) {
                    return;
                }
                synchronized (com.android.server.location.injector.SystemEmergencyHelper.this) {
                    try {
                        com.android.server.location.injector.SystemEmergencyHelper.this.mIsInEmergencyCall = com.android.server.location.injector.SystemEmergencyHelper.this.mTelephonyManager.isEmergencyNumber(intent.getStringExtra("android.intent.extra.PHONE_NUMBER"));
                        com.android.server.location.injector.SystemEmergencyHelper.this.dispatchEmergencyStateChanged();
                    } catch (java.lang.IllegalStateException e) {
                        android.util.Log.w(com.android.server.location.LocationManagerService.TAG, "Failed to call TelephonyManager.isEmergencyNumber().", e);
                    }
                }
            }
        }, new android.content.IntentFilter("android.intent.action.NEW_OUTGOING_CALL"));
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.location.injector.SystemEmergencyHelper.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (!"android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(intent.getAction())) {
                    return;
                }
                com.android.server.location.injector.SystemEmergencyHelper.this.dispatchEmergencyStateChanged();
            }
        }, new android.content.IntentFilter("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED"));
    }

    @Override // com.android.server.location.injector.EmergencyHelper
    public synchronized boolean isInEmergency(long j) {
        boolean z;
        boolean z2;
        try {
            if (this.mTelephonyManager == null) {
                return false;
            }
            boolean z3 = this.mEmergencyCallEndRealtimeMs != Long.MIN_VALUE && android.os.SystemClock.elapsedRealtime() - this.mEmergencyCallEndRealtimeMs < j;
            if (!com.android.internal.telephony.flags.Flags.enforceTelephonyFeatureMapping()) {
                return this.mIsInEmergencyCall || z3 || this.mTelephonyManager.getEmergencyCallbackMode() || this.mTelephonyManager.isInEmergencySmsMode();
            }
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            if (!packageManager.hasSystemFeature("android.hardware.telephony.calling")) {
                z = false;
            } else {
                z = this.mTelephonyManager.getEmergencyCallbackMode();
            }
            if (!packageManager.hasSystemFeature("android.hardware.telephony.messaging")) {
                z2 = false;
            } else {
                z2 = this.mTelephonyManager.isInEmergencySmsMode();
            }
            return this.mIsInEmergencyCall || z3 || z || z2;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private class EmergencyCallTelephonyCallback extends android.telephony.TelephonyCallback implements android.telephony.TelephonyCallback.CallStateListener {
        EmergencyCallTelephonyCallback() {
        }

        @Override // android.telephony.TelephonyCallback.CallStateListener
        public void onCallStateChanged(int i) {
            if (i == 0) {
                synchronized (com.android.server.location.injector.SystemEmergencyHelper.this) {
                    try {
                        if (com.android.server.location.injector.SystemEmergencyHelper.this.mIsInEmergencyCall) {
                            com.android.server.location.injector.SystemEmergencyHelper.this.mEmergencyCallEndRealtimeMs = android.os.SystemClock.elapsedRealtime();
                            com.android.server.location.injector.SystemEmergencyHelper.this.mIsInEmergencyCall = false;
                            com.android.server.location.injector.SystemEmergencyHelper.this.dispatchEmergencyStateChanged();
                        }
                    } finally {
                    }
                }
            }
        }
    }
}
