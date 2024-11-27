package com.android.server.location;

/* loaded from: classes2.dex */
public class HardwareActivityRecognitionProxy implements com.android.server.servicewatcher.ServiceWatcher.ServiceListener<com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo> {
    private static final java.lang.String SERVICE_ACTION = "com.android.location.service.ActivityRecognitionProvider";
    private static final java.lang.String TAG = "ARProxy";
    private final android.hardware.location.ActivityRecognitionHardware mInstance;
    private final boolean mIsSupported = android.hardware.location.ActivityRecognitionHardware.isSupported();
    private final com.android.server.servicewatcher.ServiceWatcher mServiceWatcher;

    @android.annotation.Nullable
    public static com.android.server.location.HardwareActivityRecognitionProxy createAndRegister(android.content.Context context) {
        com.android.server.location.HardwareActivityRecognitionProxy hardwareActivityRecognitionProxy = new com.android.server.location.HardwareActivityRecognitionProxy(context);
        if (hardwareActivityRecognitionProxy.register()) {
            return hardwareActivityRecognitionProxy;
        }
        return null;
    }

    private HardwareActivityRecognitionProxy(android.content.Context context) {
        if (this.mIsSupported) {
            this.mInstance = android.hardware.location.ActivityRecognitionHardware.getInstance(context);
        } else {
            this.mInstance = null;
        }
        this.mServiceWatcher = com.android.server.servicewatcher.ServiceWatcher.create(context, "HardwareActivityRecognitionProxy", com.android.server.servicewatcher.CurrentUserServiceSupplier.createFromConfig(context, SERVICE_ACTION, android.R.bool.config_emergencyGestureEnabled, android.R.string.concurrent_display_notification_power_save_content), this);
    }

    private boolean register() {
        boolean checkServiceResolves = this.mServiceWatcher.checkServiceResolves();
        if (checkServiceResolves) {
            this.mServiceWatcher.register();
        }
        return checkServiceResolves;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
    public void onBind(android.os.IBinder iBinder, com.android.server.servicewatcher.CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) throws android.os.RemoteException {
        java.lang.String interfaceDescriptor = iBinder.getInterfaceDescriptor();
        if (android.hardware.location.IActivityRecognitionHardwareWatcher.class.getCanonicalName().equals(interfaceDescriptor)) {
            android.hardware.location.IActivityRecognitionHardwareWatcher asInterface = android.hardware.location.IActivityRecognitionHardwareWatcher.Stub.asInterface(iBinder);
            if (this.mInstance != null) {
                asInterface.onInstanceChanged(this.mInstance);
                return;
            }
            return;
        }
        if (android.hardware.location.IActivityRecognitionHardwareClient.class.getCanonicalName().equals(interfaceDescriptor)) {
            android.hardware.location.IActivityRecognitionHardwareClient.Stub.asInterface(iBinder).onAvailabilityChanged(this.mIsSupported, this.mInstance);
            return;
        }
        android.util.Log.e(TAG, "Unknown descriptor: " + interfaceDescriptor);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceListener
    public void onUnbind() {
    }
}
