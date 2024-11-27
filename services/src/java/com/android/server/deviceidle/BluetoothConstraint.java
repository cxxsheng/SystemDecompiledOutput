package com.android.server.deviceidle;

/* loaded from: classes.dex */
public class BluetoothConstraint implements com.android.server.deviceidle.IDeviceIdleConstraint {
    private static final long INACTIVITY_TIMEOUT_MS = 1200000;
    private static final java.lang.String TAG = com.android.server.deviceidle.BluetoothConstraint.class.getSimpleName();
    private final android.bluetooth.BluetoothManager mBluetoothManager;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final com.android.server.DeviceIdleInternal mLocalService;
    private volatile boolean mConnected = true;
    private volatile boolean mMonitoring = false;

    @com.android.internal.annotations.VisibleForTesting
    final android.content.BroadcastReceiver mReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.deviceidle.BluetoothConstraint.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.bluetooth.device.action.ACL_CONNECTED".equals(intent.getAction())) {
                com.android.server.deviceidle.BluetoothConstraint.this.mLocalService.exitIdle("bluetooth");
            } else {
                com.android.server.deviceidle.BluetoothConstraint.this.updateAndReportActiveLocked();
            }
        }
    };
    private final java.lang.Runnable mTimeoutCallback = new java.lang.Runnable() { // from class: com.android.server.deviceidle.BluetoothConstraint$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.deviceidle.BluetoothConstraint.this.lambda$new$0();
        }
    };

    public BluetoothConstraint(android.content.Context context, android.os.Handler handler, com.android.server.DeviceIdleInternal deviceIdleInternal) {
        this.mContext = context;
        this.mHandler = handler;
        this.mLocalService = deviceIdleInternal;
        this.mBluetoothManager = (android.bluetooth.BluetoothManager) this.mContext.getSystemService(android.bluetooth.BluetoothManager.class);
    }

    public synchronized void startMonitoring() {
        this.mConnected = true;
        this.mMonitoring = true;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        this.mHandler.sendMessageDelayed(android.os.Message.obtain(this.mHandler, this.mTimeoutCallback), INACTIVITY_TIMEOUT_MS);
        updateAndReportActiveLocked();
    }

    public synchronized void stopMonitoring() {
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mHandler.removeCallbacks(this.mTimeoutCallback);
        this.mMonitoring = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: cancelMonitoringDueToTimeout, reason: merged with bridge method [inline-methods] */
    public synchronized void lambda$new$0() {
        if (this.mMonitoring) {
            this.mMonitoring = false;
            this.mLocalService.onConstraintStateChanged(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateAndReportActiveLocked() {
        boolean isBluetoothConnected = isBluetoothConnected(this.mBluetoothManager);
        if (isBluetoothConnected != this.mConnected) {
            this.mConnected = isBluetoothConnected;
            this.mLocalService.onConstraintStateChanged(this, this.mConnected);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isBluetoothConnected(android.bluetooth.BluetoothManager bluetoothManager) {
        android.bluetooth.BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter != null && adapter.isEnabled() && bluetoothManager.getConnectedDevices(7).size() > 0;
    }
}
