package com.android.server.health;

/* loaded from: classes2.dex */
final class HealthServiceWrapperHidl extends com.android.server.health.HealthServiceWrapper {
    public static final java.lang.String INSTANCE_VENDOR = "default";
    private static final java.lang.String TAG = "HealthServiceWrapperHidl";
    private com.android.server.health.HealthServiceWrapperHidl.Callback mCallback;
    private com.android.server.health.HealthServiceWrapperHidl.IHealthSupplier mHealthSupplier;
    private java.lang.String mInstanceName;
    private final android.hidl.manager.V1_0.IServiceNotification mNotification = new com.android.server.health.HealthServiceWrapperHidl.Notification();
    private final android.os.HandlerThread mHandlerThread = new android.os.HandlerThread("HealthServiceHwbinder");
    private final java.util.concurrent.atomic.AtomicReference<android.hardware.health.V2_0.IHealth> mLastService = new java.util.concurrent.atomic.AtomicReference<>();

    interface Callback {
        void onRegistration(android.hardware.health.V2_0.IHealth iHealth, android.hardware.health.V2_0.IHealth iHealth2, java.lang.String str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Mutable<T> {
        public T value;

        private Mutable() {
        }
    }

    private static void traceBegin(java.lang.String str) {
        android.os.Trace.traceBegin(524288L, str);
    }

    private static void traceEnd() {
        android.os.Trace.traceEnd(524288L);
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public int getProperty(int i, final android.os.BatteryProperty batteryProperty) throws android.os.RemoteException {
        traceBegin("HealthGetProperty");
        try {
            android.hardware.health.V2_0.IHealth iHealth = this.mLastService.get();
            if (iHealth == null) {
                throw new android.os.RemoteException("no health service");
            }
            final android.util.MutableInt mutableInt = new android.util.MutableInt(1);
            switch (i) {
                case 1:
                    iHealth.getChargeCounter(new android.hardware.health.V2_0.IHealth.getChargeCounterCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda1
                        @Override // android.hardware.health.V2_0.IHealth.getChargeCounterCallback
                        public final void onValues(int i2, int i3) {
                            com.android.server.health.HealthServiceWrapperHidl.lambda$getProperty$0(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
                case 2:
                    iHealth.getCurrentNow(new android.hardware.health.V2_0.IHealth.getCurrentNowCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda2
                        @Override // android.hardware.health.V2_0.IHealth.getCurrentNowCallback
                        public final void onValues(int i2, int i3) {
                            com.android.server.health.HealthServiceWrapperHidl.lambda$getProperty$1(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
                case 3:
                    iHealth.getCurrentAverage(new android.hardware.health.V2_0.IHealth.getCurrentAverageCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda3
                        @Override // android.hardware.health.V2_0.IHealth.getCurrentAverageCallback
                        public final void onValues(int i2, int i3) {
                            com.android.server.health.HealthServiceWrapperHidl.lambda$getProperty$2(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
                case 4:
                    iHealth.getCapacity(new android.hardware.health.V2_0.IHealth.getCapacityCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda4
                        @Override // android.hardware.health.V2_0.IHealth.getCapacityCallback
                        public final void onValues(int i2, int i3) {
                            com.android.server.health.HealthServiceWrapperHidl.lambda$getProperty$3(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
                case 5:
                    iHealth.getEnergyCounter(new android.hardware.health.V2_0.IHealth.getEnergyCounterCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda6
                        @Override // android.hardware.health.V2_0.IHealth.getEnergyCounterCallback
                        public final void onValues(int i2, long j) {
                            com.android.server.health.HealthServiceWrapperHidl.lambda$getProperty$5(mutableInt, batteryProperty, i2, j);
                        }
                    });
                    break;
                case 6:
                    iHealth.getChargeStatus(new android.hardware.health.V2_0.IHealth.getChargeStatusCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda5
                        @Override // android.hardware.health.V2_0.IHealth.getChargeStatusCallback
                        public final void onValues(int i2, int i3) {
                            com.android.server.health.HealthServiceWrapperHidl.lambda$getProperty$4(mutableInt, batteryProperty, i2, i3);
                        }
                    });
                    break;
            }
            int i2 = mutableInt.value;
            traceEnd();
            return i2;
        } catch (java.lang.Throwable th) {
            traceEnd();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getProperty$0(android.util.MutableInt mutableInt, android.os.BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getProperty$1(android.util.MutableInt mutableInt, android.os.BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getProperty$2(android.util.MutableInt mutableInt, android.os.BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getProperty$3(android.util.MutableInt mutableInt, android.os.BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getProperty$4(android.util.MutableInt mutableInt, android.os.BatteryProperty batteryProperty, int i, int i2) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getProperty$5(android.util.MutableInt mutableInt, android.os.BatteryProperty batteryProperty, int i, long j) {
        mutableInt.value = i;
        if (i == 0) {
            batteryProperty.setLong(j);
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    public void scheduleUpdate() throws android.os.RemoteException {
        getHandlerThread().getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.health.HealthServiceWrapperHidl.this.lambda$scheduleUpdate$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleUpdate$6() {
        android.hardware.health.V2_0.IHealth iHealth;
        traceBegin("HealthScheduleUpdate");
        try {
            try {
                iHealth = this.mLastService.get();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Cannot call update on health HAL", e);
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.health.HealthServiceWrapper
    public android.hardware.health.HealthInfo getHealthInfo() throws android.os.RemoteException {
        android.hardware.health.V2_0.IHealth iHealth = this.mLastService.get();
        if (iHealth == null) {
            return null;
        }
        final com.android.server.health.HealthServiceWrapperHidl.Mutable mutable = new com.android.server.health.HealthServiceWrapperHidl.Mutable();
        iHealth.getHealthInfo(new android.hardware.health.V2_0.IHealth.getHealthInfoCallback() { // from class: com.android.server.health.HealthServiceWrapperHidl$$ExternalSyntheticLambda0
            @Override // android.hardware.health.V2_0.IHealth.getHealthInfoCallback
            public final void onValues(int i, android.hardware.health.V2_0.HealthInfo healthInfo) {
                com.android.server.health.HealthServiceWrapperHidl.lambda$getHealthInfo$7(com.android.server.health.HealthServiceWrapperHidl.Mutable.this, i, healthInfo);
            }
        });
        return (android.hardware.health.HealthInfo) mutable.value;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, android.hardware.health.HealthInfo] */
    public static /* synthetic */ void lambda$getHealthInfo$7(com.android.server.health.HealthServiceWrapperHidl.Mutable mutable, int i, android.hardware.health.V2_0.HealthInfo healthInfo) {
        if (i == 0) {
            mutable.value = android.hardware.health.Translate.h2aTranslate(healthInfo.legacy);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    HealthServiceWrapperHidl(@android.annotation.Nullable com.android.server.health.HealthServiceWrapperHidl.Callback callback, @android.annotation.NonNull com.android.server.health.HealthServiceWrapperHidl.IServiceManagerSupplier iServiceManagerSupplier, @android.annotation.NonNull com.android.server.health.HealthServiceWrapperHidl.IHealthSupplier iHealthSupplier) throws android.os.RemoteException, java.util.NoSuchElementException, java.lang.NullPointerException {
        android.hardware.health.V2_0.IHealth iHealth;
        if (iServiceManagerSupplier == null || iHealthSupplier == null) {
            throw new java.lang.NullPointerException();
        }
        this.mHealthSupplier = iHealthSupplier;
        traceBegin("HealthInitGetService_default");
        try {
            iHealth = iHealthSupplier.get("default");
        } catch (java.util.NoSuchElementException e) {
            traceEnd();
            iHealth = null;
        } catch (java.lang.Throwable th) {
            throw th;
        }
        if (iHealth != null) {
            this.mInstanceName = "default";
            this.mLastService.set(iHealth);
        }
        if (this.mInstanceName == null || iHealth == null) {
            throw new java.util.NoSuchElementException(java.lang.String.format("IHealth service instance %s isn't available. Perhaps no permission?", "default"));
        }
        if (callback != null) {
            this.mCallback = callback;
            this.mCallback.onRegistration(null, iHealth, this.mInstanceName);
        }
        traceBegin("HealthInitRegisterNotification");
        this.mHandlerThread.start();
        try {
            iServiceManagerSupplier.get().registerForNotifications(android.hardware.health.V2_0.IHealth.kInterfaceName, this.mInstanceName, this.mNotification);
            traceEnd();
            android.util.Slog.i(TAG, "health: HealthServiceWrapper listening to instance " + this.mInstanceName);
        } finally {
            traceEnd();
        }
    }

    @Override // com.android.server.health.HealthServiceWrapper
    @com.android.internal.annotations.VisibleForTesting
    public android.os.HandlerThread getHandlerThread() {
        return this.mHandlerThread;
    }

    interface IServiceManagerSupplier {
        default android.hidl.manager.V1_0.IServiceManager get() throws java.util.NoSuchElementException, android.os.RemoteException {
            return android.hidl.manager.V1_0.IServiceManager.getService();
        }
    }

    interface IHealthSupplier {
        default android.hardware.health.V2_0.IHealth get(java.lang.String str) throws java.util.NoSuchElementException, android.os.RemoteException {
            return android.hardware.health.V2_0.IHealth.getService(str, true);
        }
    }

    private class Notification extends android.hidl.manager.V1_0.IServiceNotification.Stub {
        private Notification() {
        }

        @Override // android.hidl.manager.V1_0.IServiceNotification
        public final void onRegistration(java.lang.String str, java.lang.String str2, boolean z) {
            if (android.hardware.health.V2_0.IHealth.kInterfaceName.equals(str) && com.android.server.health.HealthServiceWrapperHidl.this.mInstanceName.equals(str2)) {
                com.android.server.health.HealthServiceWrapperHidl.this.mHandlerThread.getThreadHandler().post(new java.lang.Runnable() { // from class: com.android.server.health.HealthServiceWrapperHidl.Notification.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            android.hardware.health.V2_0.IHealth iHealth = com.android.server.health.HealthServiceWrapperHidl.this.mHealthSupplier.get(com.android.server.health.HealthServiceWrapperHidl.this.mInstanceName);
                            android.hardware.health.V2_0.IHealth iHealth2 = (android.hardware.health.V2_0.IHealth) com.android.server.health.HealthServiceWrapperHidl.this.mLastService.getAndSet(iHealth);
                            if (java.util.Objects.equals(iHealth, iHealth2)) {
                                return;
                            }
                            android.util.Slog.i(com.android.server.health.HealthServiceWrapperHidl.TAG, "health: new instance registered " + com.android.server.health.HealthServiceWrapperHidl.this.mInstanceName);
                            if (com.android.server.health.HealthServiceWrapperHidl.this.mCallback == null) {
                                return;
                            }
                            com.android.server.health.HealthServiceWrapperHidl.this.mCallback.onRegistration(iHealth2, iHealth, com.android.server.health.HealthServiceWrapperHidl.this.mInstanceName);
                        } catch (android.os.RemoteException | java.util.NoSuchElementException e) {
                            android.util.Slog.e(com.android.server.health.HealthServiceWrapperHidl.TAG, "health: Cannot get instance '" + com.android.server.health.HealthServiceWrapperHidl.this.mInstanceName + "': " + e.getMessage() + ". Perhaps no permission?");
                        }
                    }
                });
            }
        }
    }
}
