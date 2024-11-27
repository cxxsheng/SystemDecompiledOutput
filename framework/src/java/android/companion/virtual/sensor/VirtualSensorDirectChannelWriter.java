package android.companion.virtual.sensor;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualSensorDirectChannelWriter implements java.lang.AutoCloseable {
    private static final java.lang.String TAG = "VirtualSensorWriter";
    private static final long UINT32_MAX = 4294967295L;
    private final android.util.SparseArray<android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.SharedMemoryWrapper> mChannels = new android.util.SparseArray<>();
    private final java.lang.Object mChannelsLock = new java.lang.Object();
    private final android.util.SparseArray<android.util.SparseArray<android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.DirectChannelConfiguration>> mConfiguredChannels = new android.util.SparseArray<>();

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mChannelsLock) {
            for (int i = 0; i < this.mChannels.size(); i++) {
                this.mChannels.valueAt(i).close();
            }
            this.mChannels.clear();
            this.mConfiguredChannels.clear();
        }
    }

    public void addChannel(int i, android.os.SharedMemory sharedMemory) throws android.system.ErrnoException {
        synchronized (this.mChannelsLock) {
            if (this.mChannels.contains(i)) {
                android.util.Log.w(TAG, "Channel with handle " + i + " already added.");
            } else {
                this.mChannels.put(i, new android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.SharedMemoryWrapper((android.os.SharedMemory) java.util.Objects.requireNonNull(sharedMemory)));
            }
        }
    }

    public void removeChannel(int i) {
        synchronized (this.mChannelsLock) {
            android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.SharedMemoryWrapper removeReturnOld = this.mChannels.removeReturnOld(i);
            if (removeReturnOld != null) {
                removeReturnOld.close();
            }
            for (int i2 = 0; i2 < this.mConfiguredChannels.size(); i2++) {
                this.mConfiguredChannels.valueAt(i2).remove(i);
            }
        }
    }

    public boolean configureChannel(int i, android.companion.virtual.sensor.VirtualSensor virtualSensor, int i2, int i3) {
        synchronized (this.mChannelsLock) {
            android.util.SparseArray<android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.DirectChannelConfiguration> sparseArray = this.mConfiguredChannels.get(((android.companion.virtual.sensor.VirtualSensor) java.util.Objects.requireNonNull(virtualSensor)).getHandle());
            if (i2 == 0) {
                if (sparseArray != null && sparseArray.removeReturnOld(i) != null) {
                    return true;
                }
                android.util.Log.w(TAG, "Channel configuration failed - channel with handle " + i + " not found");
                return false;
            }
            if (sparseArray == null) {
                sparseArray = new android.util.SparseArray<>();
                this.mConfiguredChannels.put(virtualSensor.getHandle(), sparseArray);
            }
            android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.SharedMemoryWrapper sharedMemoryWrapper = this.mChannels.get(i);
            if (sharedMemoryWrapper == null) {
                android.util.Log.w(TAG, "Channel configuration failed - channel with handle " + i + " not found");
                return false;
            }
            sparseArray.put(i, new android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.DirectChannelConfiguration(i3, virtualSensor.getType(), sharedMemoryWrapper));
            return true;
        }
    }

    public boolean writeSensorEvent(android.companion.virtual.sensor.VirtualSensor virtualSensor, android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) {
        java.util.Objects.requireNonNull(virtualSensorEvent);
        synchronized (this.mChannelsLock) {
            android.util.SparseArray<android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.DirectChannelConfiguration> sparseArray = this.mConfiguredChannels.get(((android.companion.virtual.sensor.VirtualSensor) java.util.Objects.requireNonNull(virtualSensor)).getHandle());
            if (sparseArray != null && sparseArray.size() != 0) {
                for (int i = 0; i < sparseArray.size(); i++) {
                    sparseArray.valueAt(i).write((android.companion.virtual.sensor.VirtualSensorEvent) java.util.Objects.requireNonNull(virtualSensorEvent));
                }
                return true;
            }
            android.util.Log.w(TAG, "Sensor event write failed - no direct sensor channels configured for sensor " + virtualSensor.getName());
            return false;
        }
    }

    private static final class SharedMemoryWrapper {
        private static final int MAXIMUM_NUMBER_OF_SENSOR_VALUES = 16;
        private static final int SENSOR_EVENT_SIZE = 104;
        private final java.nio.ByteBuffer mMemoryMapping;
        private final android.os.SharedMemory mSharedMemory;
        private int mWriteOffset = 0;
        private final java.nio.ByteBuffer mEventBuffer = java.nio.ByteBuffer.allocate(104);
        private final java.lang.Object mWriteLock = new java.lang.Object();

        SharedMemoryWrapper(android.os.SharedMemory sharedMemory) throws android.system.ErrnoException {
            this.mSharedMemory = sharedMemory;
            this.mMemoryMapping = this.mSharedMemory.mapReadWrite();
            this.mEventBuffer.order(java.nio.ByteOrder.nativeOrder());
        }

        void close() {
            synchronized (this.mWriteLock) {
                this.mSharedMemory.close();
            }
        }

        void write(int i, int i2, long j, android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) {
            synchronized (this.mWriteLock) {
                this.mEventBuffer.position(0);
                this.mEventBuffer.putInt(104);
                this.mEventBuffer.putInt(i);
                this.mEventBuffer.putInt(i2);
                this.mEventBuffer.putInt((int) (j & 4294967295L));
                this.mEventBuffer.putLong(virtualSensorEvent.getTimestampNanos());
                for (int i3 = 0; i3 < 16; i3++) {
                    if (i3 < virtualSensorEvent.getValues().length) {
                        this.mEventBuffer.putFloat(virtualSensorEvent.getValues()[i3]);
                    } else {
                        this.mEventBuffer.putFloat(0.0f);
                    }
                }
                this.mEventBuffer.putInt(0);
                this.mMemoryMapping.position(this.mWriteOffset);
                this.mMemoryMapping.put(this.mEventBuffer.array(), 0, 104);
                this.mWriteOffset += 104;
                if (this.mWriteOffset + 104 >= this.mSharedMemory.getSize()) {
                    this.mWriteOffset = 0;
                }
            }
        }
    }

    private static final class DirectChannelConfiguration {
        private final java.util.concurrent.atomic.AtomicLong mEventCounter = new java.util.concurrent.atomic.AtomicLong(1);
        private final int mReportToken;
        private final int mSensorType;
        private final android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.SharedMemoryWrapper mSharedMemoryWrapper;

        DirectChannelConfiguration(int i, int i2, android.companion.virtual.sensor.VirtualSensorDirectChannelWriter.SharedMemoryWrapper sharedMemoryWrapper) {
            this.mReportToken = i;
            this.mSensorType = i2;
            this.mSharedMemoryWrapper = sharedMemoryWrapper;
        }

        void write(android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) {
            long acquire = this.mEventCounter.getAcquire();
            long j = acquire + 1;
            this.mSharedMemoryWrapper.write(this.mReportToken, this.mSensorType, acquire, virtualSensorEvent);
            this.mEventCounter.setRelease(j != 4294967296L ? j : 1L);
        }
    }
}
