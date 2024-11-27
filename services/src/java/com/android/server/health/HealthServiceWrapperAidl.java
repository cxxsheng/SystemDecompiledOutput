package com.android.server.health;

/* loaded from: classes2.dex */
class HealthServiceWrapperAidl extends com.android.server.health.HealthServiceWrapper {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String SERVICE_NAME = android.hardware.health.IHealth.DESCRIPTOR + "/default";
    private static final java.lang.String TAG = "HealthServiceWrapperAidl";
    private final com.android.server.health.HealthRegCallbackAidl mRegCallback;
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread("HealthServiceBinder");
    private final java.util.concurrent.atomic.AtomicReference<android.hardware.health.IHealth> mLastService = new java.util.concurrent.atomic.AtomicReference<>();
    private final android.os.IServiceCallback mServiceCallback = new com.android.server.health.HealthServiceWrapperAidl.ServiceCallback();

    interface ServiceManagerStub {
        @android.annotation.Nullable
        default android.hardware.health.IHealth waitForDeclaredService(@android.annotation.NonNull java.lang.String str) {
            return android.hardware.health.IHealth.Stub.asInterface(android.os.ServiceManager.waitForDeclaredService(str));
        }

        default void registerForNotifications(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException {
            android.os.ServiceManager.registerForNotifications(str, iServiceCallback);
        }
    }

    HealthServiceWrapperAidl(@android.annotation.Nullable com.android.server.health.HealthRegCallbackAidl healthRegCallbackAidl, @android.annotation.NonNull com.android.server.health.HealthServiceWrapperAidl.ServiceManagerStub serviceManagerStub) throws android.os.RemoteException, java.util.NoSuchElementException {
        traceBegin("HealthInitGetServiceAidl");
        try {
            android.hardware.health.IHealth waitForDeclaredService = serviceManagerStub.waitForDeclaredService(SERVICE_NAME);
            if (waitForDeclaredService == null) {
                throw new java.util.NoSuchElementException("IHealth service instance isn't available. Perhaps no permission?");
            }
            this.mLastService.set(waitForDeclaredService);
            this.mRegCallback = healthRegCallbackAidl;
            if (this.mRegCallback != null) {
                this.mRegCallback.onRegistration(null, waitForDeclaredService);
            }
            traceBegin("HealthInitRegisterNotificationAidl");
            this.mHandlerThread.start();
            try {
                serviceManagerStub.registerForNotifications(SERVICE_NAME, this.mServiceCallback);
                traceEnd();
                android.util.Slog.i(TAG, "health: HealthServiceWrapper listening to AIDL HAL");
            } finally {
            }
        } finally {
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    @com.android.internal.annotations.VisibleForTesting
    public android.os.HandlerThread getHandlerThread() {
        return this.mHandlerThread;
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public int getProperty(int i, android.os.BatteryProperty batteryProperty) throws android.os.RemoteException {
        traceBegin("HealthGetPropertyAidl");
        try {
            return getPropertyInternal(i, batteryProperty);
        } finally {
            traceEnd();
        }
    }

    private int getPropertyInternal(int i, android.os.BatteryProperty batteryProperty) throws android.os.RemoteException {
        android.hardware.health.IHealth iHealth = this.mLastService.get();
        if (iHealth == null) {
            throw new android.os.RemoteException("no health service");
        }
        try {
            switch (i) {
                case 1:
                    batteryProperty.setLong(iHealth.getChargeCounterUah());
                    break;
                case 2:
                    batteryProperty.setLong(iHealth.getCurrentNowMicroamps());
                    break;
                case 3:
                    batteryProperty.setLong(iHealth.getCurrentAverageMicroamps());
                    break;
                case 4:
                    batteryProperty.setLong(iHealth.getCapacity());
                    break;
                case 5:
                    batteryProperty.setLong(iHealth.getEnergyCounterNwh());
                    break;
                case 6:
                    batteryProperty.setLong(iHealth.getChargeStatus());
                    break;
                case 7:
                    batteryProperty.setLong(iHealth.getBatteryHealthData().batteryManufacturingDateSeconds);
                    break;
                case 8:
                    batteryProperty.setLong(iHealth.getBatteryHealthData().batteryFirstUsageSeconds);
                    break;
                case 9:
                    batteryProperty.setLong(iHealth.getChargingPolicy());
                    break;
                case 10:
                    batteryProperty.setLong(iHealth.getBatteryHealthData().batteryStateOfHealth);
                    break;
                case 11:
                    if (android.os.Flags.batteryPartStatusApi()) {
                        batteryProperty.setString(iHealth.getBatteryHealthData().batterySerialNumber);
                        break;
                    }
                    break;
                case 12:
                    if (android.os.Flags.batteryPartStatusApi()) {
                        batteryProperty.setLong(iHealth.getBatteryHealthData().batteryPartStatus);
                        break;
                    }
                    break;
                default:
                    return 0;
            }
            return 0;
        } catch (java.lang.UnsupportedOperationException e) {
            return -1;
        } catch (android.os.ServiceSpecificException e2) {
            return -2;
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public void scheduleUpdate() throws android.os.RemoteException {
        getHandlerThread().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.health.HealthServiceWrapperAidl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.health.HealthServiceWrapperAidl.this.lambda$scheduleUpdate$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleUpdate$0() {
        android.hardware.health.IHealth iHealth;
        traceBegin("HealthScheduleUpdate");
        try {
            try {
                iHealth = this.mLastService.get();
            } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                android.util.Slog.e(TAG, "Cannot call update on health AIDL HAL", e);
            }
            if (iHealth == null) {
                android.util.Slog.e(TAG, "no health service");
            } else {
                iHealth.update();
            }
        } finally {
            traceEnd();
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public android.hardware.health.HealthInfo getHealthInfo() throws android.os.RemoteException {
        android.hardware.health.IHealth iHealth = this.mLastService.get();
        if (iHealth == null) {
            return null;
        }
        try {
            return iHealth.getHealthInfo();
        } catch (java.lang.UnsupportedOperationException | android.os.ServiceSpecificException e) {
            return null;
        }
    }

    private static void traceBegin(java.lang.String str) {
        android.os.Trace.traceBegin(524288L, str);
    }

    private static void traceEnd() {
        android.os.Trace.traceEnd(524288L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ServiceCallback extends android.os.IServiceCallback.Stub {
        private ServiceCallback() {
        }

        public void onRegistration(java.lang.String str, @android.annotation.NonNull final android.os.IBinder iBinder) throws android.os.RemoteException {
            if (com.android.server.health.HealthServiceWrapperAidl.SERVICE_NAME.equals(str)) {
                com.android.server.health.HealthServiceWrapperAidl.this.getHandlerThread().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.health.HealthServiceWrapperAidl$ServiceCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.health.HealthServiceWrapperAidl.ServiceCallback.this.lambda$onRegistration$0(iBinder);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRegistration$0(android.os.IBinder iBinder) {
            android.hardware.health.IHealth asInterface = android.hardware.health.IHealth.Stub.asInterface(android.os.Binder.allowBlocking(iBinder));
            android.hardware.health.IHealth iHealth = (android.hardware.health.IHealth) com.android.server.health.HealthServiceWrapperAidl.this.mLastService.getAndSet(asInterface);
            if (java.util.Objects.equals(iBinder, iHealth != null ? iHealth.asBinder() : null)) {
                return;
            }
            android.util.Slog.i(com.android.server.health.HealthServiceWrapperAidl.TAG, "New health AIDL HAL service registered");
            if (com.android.server.health.HealthServiceWrapperAidl.this.mRegCallback != null) {
                com.android.server.health.HealthServiceWrapperAidl.this.mRegCallback.onRegistration(iHealth, asInterface);
            }
        }
    }
}
