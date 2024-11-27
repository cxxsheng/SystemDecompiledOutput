package com.android.server.companion.virtual;

/* loaded from: classes.dex */
public class SensorController {
    private static final int BAD_VALUE = -22;
    private static final int OK = 0;
    private static final java.lang.String TAG = "SensorController";
    private static final int UNKNOWN_ERROR = Integer.MIN_VALUE;
    private static java.util.concurrent.atomic.AtomicInteger sNextDirectChannelHandle = new java.util.concurrent.atomic.AtomicInteger(1);

    @android.annotation.NonNull
    private final android.content.AttributionSource mAttributionSource;

    @android.annotation.NonNull
    private final com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback mRuntimeSensorCallback;
    private final int mVirtualDeviceId;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.companion.virtual.SensorController.SensorDescriptor> mSensorDescriptors = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<android.companion.virtual.sensor.VirtualSensor> mVirtualSensors = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.companion.virtual.sensor.VirtualSensor> mVirtualSensorList = null;
    private final com.android.server.sensors.SensorManagerInternal mSensorManagerInternal = (com.android.server.sensors.SensorManagerInternal) com.android.server.LocalServices.getService(com.android.server.sensors.SensorManagerInternal.class);
    private final com.android.server.companion.virtual.VirtualDeviceManagerInternal mVdmInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);

    public SensorController(@android.annotation.NonNull android.companion.virtual.IVirtualDevice iVirtualDevice, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, @android.annotation.Nullable android.companion.virtual.sensor.IVirtualSensorCallback iVirtualSensorCallback, @android.annotation.NonNull java.util.List<android.companion.virtual.sensor.VirtualSensorConfig> list) {
        this.mVirtualDeviceId = i;
        this.mAttributionSource = attributionSource;
        this.mRuntimeSensorCallback = new com.android.server.companion.virtual.SensorController.RuntimeSensorCallbackWrapper(iVirtualSensorCallback);
        createSensors(iVirtualDevice, list);
    }

    void close() {
        synchronized (this.mLock) {
            this.mSensorDescriptors.values().forEach(new java.util.function.Consumer() { // from class: com.android.server.companion.virtual.SensorController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.companion.virtual.SensorController.this.lambda$close$0((com.android.server.companion.virtual.SensorController.SensorDescriptor) obj);
                }
            });
            this.mSensorDescriptors.clear();
            this.mVirtualSensors.clear();
            this.mVirtualSensorList = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$close$0(com.android.server.companion.virtual.SensorController.SensorDescriptor sensorDescriptor) {
        this.mSensorManagerInternal.removeRuntimeSensor(sensorDescriptor.mHandle);
    }

    private void createSensors(@android.annotation.NonNull android.companion.virtual.IVirtualDevice iVirtualDevice, @android.annotation.NonNull java.util.List<android.companion.virtual.sensor.VirtualSensorConfig> list) {
        java.util.Objects.requireNonNull(iVirtualDevice);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                java.util.Iterator<android.companion.virtual.sensor.VirtualSensorConfig> it = list.iterator();
                while (it.hasNext()) {
                    createSensorInternal(iVirtualDevice, it.next());
                }
            } catch (com.android.server.companion.virtual.SensorController.SensorCreationException e) {
                throw new java.lang.RuntimeException("Failed to create virtual sensor", e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void createSensorInternal(@android.annotation.NonNull android.companion.virtual.IVirtualDevice iVirtualDevice, @android.annotation.NonNull android.companion.virtual.sensor.VirtualSensorConfig virtualSensorConfig) throws com.android.server.companion.virtual.SensorController.SensorCreationException {
        java.util.Objects.requireNonNull(virtualSensorConfig);
        if (virtualSensorConfig.getType() <= 0) {
            throw new com.android.server.companion.virtual.SensorController.SensorCreationException("Received an invalid virtual sensor type (config name '" + virtualSensorConfig.getName() + "').");
        }
        int createRuntimeSensor = this.mSensorManagerInternal.createRuntimeSensor(this.mVirtualDeviceId, virtualSensorConfig.getType(), virtualSensorConfig.getName(), virtualSensorConfig.getVendor() == null ? "" : virtualSensorConfig.getVendor(), virtualSensorConfig.getMaximumRange(), virtualSensorConfig.getResolution(), virtualSensorConfig.getPower(), virtualSensorConfig.getMinDelay(), virtualSensorConfig.getMaxDelay(), virtualSensorConfig.getFlags(), this.mRuntimeSensorCallback);
        if (createRuntimeSensor <= 0) {
            throw new com.android.server.companion.virtual.SensorController.SensorCreationException("Received an invalid virtual sensor handle '" + virtualSensorConfig.getName() + "'.");
        }
        com.android.server.companion.virtual.SensorController.SensorDescriptor sensorDescriptor = new com.android.server.companion.virtual.SensorController.SensorDescriptor(createRuntimeSensor, virtualSensorConfig.getType(), virtualSensorConfig.getName());
        android.os.Binder binder = new android.os.Binder("android.hardware.sensor.VirtualSensor:" + virtualSensorConfig.getName());
        android.companion.virtual.sensor.VirtualSensor virtualSensor = new android.companion.virtual.sensor.VirtualSensor(createRuntimeSensor, virtualSensorConfig.getType(), virtualSensorConfig.getName(), iVirtualDevice, binder);
        synchronized (this.mLock) {
            this.mSensorDescriptors.put(binder, sensorDescriptor);
            this.mVirtualSensors.put(createRuntimeSensor, virtualSensor);
        }
        if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
            com.android.modules.expresslog.Counter.logIncrementWithUid("virtual_devices.value_virtual_sensors_created_count", this.mAttributionSource.getUid());
        }
    }

    boolean sendSensorEvent(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) {
        boolean sendSensorEvent;
        java.util.Objects.requireNonNull(iBinder);
        java.util.Objects.requireNonNull(virtualSensorEvent);
        synchronized (this.mLock) {
            try {
                com.android.server.companion.virtual.SensorController.SensorDescriptor sensorDescriptor = this.mSensorDescriptors.get(iBinder);
                if (sensorDescriptor == null) {
                    throw new java.lang.IllegalArgumentException("Could not send sensor event for given token");
                }
                sendSensorEvent = this.mSensorManagerInternal.sendSensorEvent(sensorDescriptor.getHandle(), sensorDescriptor.getType(), virtualSensorEvent.getTimestampNanos(), virtualSensorEvent.getValues());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return sendSensorEvent;
    }

    @android.annotation.Nullable
    android.companion.virtual.sensor.VirtualSensor getSensorByHandle(int i) {
        android.companion.virtual.sensor.VirtualSensor virtualSensor;
        synchronized (this.mLock) {
            virtualSensor = this.mVirtualSensors.get(i);
        }
        return virtualSensor;
    }

    java.util.List<android.companion.virtual.sensor.VirtualSensor> getSensorList() {
        java.util.List<android.companion.virtual.sensor.VirtualSensor> list;
        synchronized (this.mLock) {
            try {
                if (this.mVirtualSensorList == null) {
                    this.mVirtualSensorList = new java.util.ArrayList(this.mVirtualSensors.size());
                    for (int i = 0; i < this.mVirtualSensors.size(); i++) {
                        this.mVirtualSensorList.add(this.mVirtualSensors.valueAt(i));
                    }
                    this.mVirtualSensorList = java.util.Collections.unmodifiableList(this.mVirtualSensorList);
                }
                list = this.mVirtualSensorList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return list;
    }

    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.println("    SensorController: ");
        synchronized (this.mLock) {
            try {
                printWriter.println("      Active descriptors: ");
                for (com.android.server.companion.virtual.SensorController.SensorDescriptor sensorDescriptor : this.mSensorDescriptors.values()) {
                    printWriter.println("        handle: " + sensorDescriptor.getHandle());
                    printWriter.println("          type: " + sensorDescriptor.getType());
                    printWriter.println("          name: " + sensorDescriptor.getName());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void addSensorForTesting(android.os.IBinder iBinder, int i, int i2, java.lang.String str) {
        synchronized (this.mLock) {
            this.mSensorDescriptors.put(iBinder, new com.android.server.companion.virtual.SensorController.SensorDescriptor(i, i2, str));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.Map<android.os.IBinder, com.android.server.companion.virtual.SensorController.SensorDescriptor> getSensorDescriptors() {
        android.util.ArrayMap arrayMap;
        synchronized (this.mLock) {
            arrayMap = new android.util.ArrayMap(this.mSensorDescriptors);
        }
        return arrayMap;
    }

    private final class RuntimeSensorCallbackWrapper implements com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback {

        @android.annotation.Nullable
        private android.companion.virtual.sensor.IVirtualSensorCallback mCallback;

        RuntimeSensorCallbackWrapper(@android.annotation.Nullable android.companion.virtual.sensor.IVirtualSensorCallback iVirtualSensorCallback) {
            this.mCallback = iVirtualSensorCallback;
        }

        @Override // com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback
        public int onConfigurationChanged(int i, boolean z, int i2, int i3) {
            if (this.mCallback == null) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "No sensor callback configured for sensor handle " + i);
                return -22;
            }
            android.companion.virtual.sensor.VirtualSensor virtualSensor = com.android.server.companion.virtual.SensorController.this.mVdmInternal.getVirtualSensor(com.android.server.companion.virtual.SensorController.this.mVirtualDeviceId, i);
            if (virtualSensor == null) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "No sensor found for deviceId=" + com.android.server.companion.virtual.SensorController.this.mVirtualDeviceId + " and sensor handle=" + i);
                return -22;
            }
            try {
                this.mCallback.onConfigurationChanged(virtualSensor, z, i2, i3);
                return 0;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "Failed to call sensor callback: " + e);
                return Integer.MIN_VALUE;
            }
        }

        @Override // com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback
        public int onDirectChannelCreated(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            if (this.mCallback == null) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "No sensor callback for virtual deviceId " + com.android.server.companion.virtual.SensorController.this.mVirtualDeviceId);
                return -22;
            }
            if (parcelFileDescriptor == null) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "Received invalid ParcelFileDescriptor");
                return -22;
            }
            int andIncrement = com.android.server.companion.virtual.SensorController.sNextDirectChannelHandle.getAndIncrement();
            try {
                this.mCallback.onDirectChannelCreated(andIncrement, android.os.SharedMemory.fromFileDescriptor(parcelFileDescriptor));
                return andIncrement;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "Failed to call sensor callback: " + e);
                return Integer.MIN_VALUE;
            }
        }

        @Override // com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback
        public void onDirectChannelDestroyed(int i) {
            if (this.mCallback == null) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "No sensor callback for virtual deviceId " + com.android.server.companion.virtual.SensorController.this.mVirtualDeviceId);
                return;
            }
            try {
                this.mCallback.onDirectChannelDestroyed(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "Failed to call sensor callback: " + e);
            }
        }

        @Override // com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback
        public int onDirectChannelConfigured(int i, int i2, int i3) {
            if (this.mCallback == null) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "No runtime sensor callback configured.");
                return -22;
            }
            android.companion.virtual.sensor.VirtualSensor virtualSensor = com.android.server.companion.virtual.SensorController.this.mVdmInternal.getVirtualSensor(com.android.server.companion.virtual.SensorController.this.mVirtualDeviceId, i2);
            if (virtualSensor == null) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "No sensor found for deviceId=" + com.android.server.companion.virtual.SensorController.this.mVirtualDeviceId + " and sensor handle=" + i2);
                return -22;
            }
            try {
                this.mCallback.onDirectChannelConfigured(i, virtualSensor, i3, i2);
                if (i3 == 0) {
                    return 0;
                }
                return i2;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.companion.virtual.SensorController.TAG, "Failed to call sensor callback: " + e);
                return Integer.MIN_VALUE;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class SensorDescriptor {
        private final int mHandle;
        private final java.lang.String mName;
        private final int mType;

        SensorDescriptor(int i, int i2, java.lang.String str) {
            this.mHandle = i;
            this.mType = i2;
            this.mName = str;
        }

        public int getHandle() {
            return this.mHandle;
        }

        public int getType() {
            return this.mType;
        }

        public java.lang.String getName() {
            return this.mName;
        }
    }

    private static class SensorCreationException extends java.lang.Exception {
        SensorCreationException(java.lang.String str) {
            super(str);
        }
    }
}
