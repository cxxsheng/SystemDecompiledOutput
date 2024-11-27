package com.android.server.sensors;

/* loaded from: classes2.dex */
public class SensorService extends com.android.server.SystemService {
    private static final java.lang.String START_NATIVE_SENSOR_SERVICE = "StartNativeSensorService";
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<com.android.server.sensors.SensorManagerInternal.ProximityActiveListener, com.android.server.sensors.SensorService.ProximityListenerProxy> mProximityListeners;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mPtr;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.Integer> mRuntimeSensorHandles;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.concurrent.Future<?> mSensorServiceStart;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void registerProximityActiveListenerNative(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int registerRuntimeSensorNative(long j, int i, int i2, java.lang.String str, java.lang.String str2, float f, float f2, float f3, int i3, int i4, int i5, com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback runtimeSensorCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native boolean sendRuntimeSensorEventNative(long j, int i, int i2, long j2, float[] fArr);

    private static native long startSensorServiceNative(com.android.server.sensors.SensorManagerInternal.ProximityActiveListener proximityActiveListener);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void unregisterProximityActiveListenerNative(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void unregisterRuntimeSensorNative(long j, int i);

    public SensorService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mProximityListeners = new android.util.ArrayMap<>();
        this.mRuntimeSensorHandles = new java.util.HashSet();
        synchronized (this.mLock) {
            this.mSensorServiceStart = com.android.server.SystemServerInitThreadPool.submit(new java.lang.Runnable() { // from class: com.android.server.sensors.SensorService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.sensors.SensorService.this.lambda$new$0();
                }
            }, START_NATIVE_SENSOR_SERVICE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        com.android.server.utils.TimingsTraceAndSlog newAsyncLog = com.android.server.utils.TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin(START_NATIVE_SENSOR_SERVICE);
        long startSensorServiceNative = startSensorServiceNative(new com.android.server.sensors.SensorService.ProximityListenerDelegate());
        synchronized (this.mLock) {
            this.mPtr = startSensorServiceNative;
        }
        newAsyncLog.traceEnd();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        com.android.server.LocalServices.addService(com.android.server.sensors.SensorManagerInternal.class, new com.android.server.sensors.SensorService.LocalService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 200) {
            com.android.internal.util.ConcurrentUtils.waitForFutureNoInterrupt(this.mSensorServiceStart, START_NATIVE_SENSOR_SERVICE);
            synchronized (this.mLock) {
                this.mSensorServiceStart = null;
            }
        }
    }

    class LocalService extends com.android.server.sensors.SensorManagerInternal {
        LocalService() {
        }

        @Override // com.android.server.sensors.SensorManagerInternal
        public int createRuntimeSensor(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, float f, float f2, float f3, int i3, int i4, int i5, @android.annotation.NonNull com.android.server.sensors.SensorManagerInternal.RuntimeSensorCallback runtimeSensorCallback) {
            int registerRuntimeSensorNative;
            synchronized (com.android.server.sensors.SensorService.this.mLock) {
                registerRuntimeSensorNative = com.android.server.sensors.SensorService.registerRuntimeSensorNative(com.android.server.sensors.SensorService.this.mPtr, i, i2, str, str2, f, f2, f3, i3, i4, i5, runtimeSensorCallback);
                com.android.server.sensors.SensorService.this.mRuntimeSensorHandles.add(java.lang.Integer.valueOf(registerRuntimeSensorNative));
            }
            return registerRuntimeSensorNative;
        }

        @Override // com.android.server.sensors.SensorManagerInternal
        public void removeRuntimeSensor(int i) {
            synchronized (com.android.server.sensors.SensorService.this.mLock) {
                try {
                    if (com.android.server.sensors.SensorService.this.mRuntimeSensorHandles.contains(java.lang.Integer.valueOf(i))) {
                        com.android.server.sensors.SensorService.this.mRuntimeSensorHandles.remove(java.lang.Integer.valueOf(i));
                        com.android.server.sensors.SensorService.unregisterRuntimeSensorNative(com.android.server.sensors.SensorService.this.mPtr, i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.sensors.SensorManagerInternal
        public boolean sendSensorEvent(int i, int i2, long j, @android.annotation.NonNull float[] fArr) {
            synchronized (com.android.server.sensors.SensorService.this.mLock) {
                try {
                    if (!com.android.server.sensors.SensorService.this.mRuntimeSensorHandles.contains(java.lang.Integer.valueOf(i))) {
                        return false;
                    }
                    return com.android.server.sensors.SensorService.sendRuntimeSensorEventNative(com.android.server.sensors.SensorService.this.mPtr, i, i2, j, fArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.sensors.SensorManagerInternal
        public void addProximityActiveListener(@android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull com.android.server.sensors.SensorManagerInternal.ProximityActiveListener proximityActiveListener) {
            java.util.Objects.requireNonNull(executor, "executor must not be null");
            java.util.Objects.requireNonNull(proximityActiveListener, "listener must not be null");
            com.android.server.sensors.SensorService.ProximityListenerProxy proximityListenerProxy = new com.android.server.sensors.SensorService.ProximityListenerProxy(executor, proximityActiveListener);
            synchronized (com.android.server.sensors.SensorService.this.mLock) {
                try {
                    if (com.android.server.sensors.SensorService.this.mProximityListeners.containsKey(proximityActiveListener)) {
                        throw new java.lang.IllegalArgumentException("listener already registered");
                    }
                    com.android.server.sensors.SensorService.this.mProximityListeners.put(proximityActiveListener, proximityListenerProxy);
                    if (com.android.server.sensors.SensorService.this.mProximityListeners.size() == 1) {
                        com.android.server.sensors.SensorService.registerProximityActiveListenerNative(com.android.server.sensors.SensorService.this.mPtr);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.sensors.SensorManagerInternal
        public void removeProximityActiveListener(@android.annotation.NonNull com.android.server.sensors.SensorManagerInternal.ProximityActiveListener proximityActiveListener) {
            java.util.Objects.requireNonNull(proximityActiveListener, "listener must not be null");
            synchronized (com.android.server.sensors.SensorService.this.mLock) {
                try {
                    if (((com.android.server.sensors.SensorService.ProximityListenerProxy) com.android.server.sensors.SensorService.this.mProximityListeners.remove(proximityActiveListener)) == null) {
                        throw new java.lang.IllegalArgumentException("listener was not registered with sensor service");
                    }
                    if (com.android.server.sensors.SensorService.this.mProximityListeners.isEmpty()) {
                        com.android.server.sensors.SensorService.unregisterProximityActiveListenerNative(com.android.server.sensors.SensorService.this.mPtr);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ProximityListenerProxy implements com.android.server.sensors.SensorManagerInternal.ProximityActiveListener {
        private final java.util.concurrent.Executor mExecutor;
        private final com.android.server.sensors.SensorManagerInternal.ProximityActiveListener mListener;

        ProximityListenerProxy(java.util.concurrent.Executor executor, com.android.server.sensors.SensorManagerInternal.ProximityActiveListener proximityActiveListener) {
            this.mExecutor = executor;
            this.mListener = proximityActiveListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProximityActive$0(boolean z) {
            this.mListener.onProximityActive(z);
        }

        @Override // com.android.server.sensors.SensorManagerInternal.ProximityActiveListener
        public void onProximityActive(final boolean z) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.sensors.SensorService$ProximityListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.sensors.SensorService.ProximityListenerProxy.this.lambda$onProximityActive$0(z);
                }
            });
        }
    }

    private class ProximityListenerDelegate implements com.android.server.sensors.SensorManagerInternal.ProximityActiveListener {
        private ProximityListenerDelegate() {
        }

        @Override // com.android.server.sensors.SensorManagerInternal.ProximityActiveListener
        public void onProximityActive(boolean z) {
            int i;
            com.android.server.sensors.SensorService.ProximityListenerProxy[] proximityListenerProxyArr;
            synchronized (com.android.server.sensors.SensorService.this.mLock) {
                proximityListenerProxyArr = (com.android.server.sensors.SensorService.ProximityListenerProxy[]) com.android.server.sensors.SensorService.this.mProximityListeners.values().toArray(new com.android.server.sensors.SensorService.ProximityListenerProxy[0]);
            }
            for (com.android.server.sensors.SensorService.ProximityListenerProxy proximityListenerProxy : proximityListenerProxyArr) {
                proximityListenerProxy.onProximityActive(z);
            }
        }
    }
}
