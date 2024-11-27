package android.hardware;

/* loaded from: classes.dex */
public class SystemSensorManager extends android.hardware.SensorManager {
    private static final int CAPPED_SAMPLING_PERIOD_US = 5000;
    private static final int CAPPED_SAMPLING_RATE_LEVEL = 1;
    static final long CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION = 136069189;
    private static final boolean DEBUG_DYNAMIC_SENSOR = true;
    private static final java.lang.String HIGH_SAMPLING_RATE_SENSORS_PERMISSION = "android.permission.HIGH_SAMPLING_RATE_SENSORS";
    private static final int MAX_LISTENER_COUNT = 128;
    private static final int MIN_DIRECT_CHANNEL_BUFFER_SIZE = 104;
    private final android.content.Context mContext;
    private android.content.BroadcastReceiver mDynamicSensorBroadcastReceiver;
    private final boolean mIsPackageDebuggable;
    private final android.os.Looper mMainLooper;
    private final long mNativeInstance;
    private android.content.BroadcastReceiver mRuntimeSensorBroadcastReceiver;
    private final int mTargetSdkLevel;
    private android.companion.virtual.VirtualDeviceManager mVdm;
    private android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener mVirtualDeviceListener;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static boolean sNativeClassInited = false;
    private static android.hardware.SystemSensorManager.InjectEventQueue sInjectEventQueue = null;
    private final java.util.ArrayList<android.hardware.Sensor> mFullSensorsList = new java.util.ArrayList<>();
    private java.util.List<android.hardware.Sensor> mFullDynamicSensorsList = new java.util.ArrayList();
    private final android.util.SparseArray<java.util.List<android.hardware.Sensor>> mFullRuntimeSensorListByDevice = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.util.SparseArray<java.util.List<android.hardware.Sensor>>> mRuntimeSensorListByDeviceByType = new android.util.SparseArray<>();
    private boolean mDynamicSensorListDirty = true;
    private final java.util.HashMap<java.lang.Integer, android.hardware.Sensor> mHandleToSensor = new java.util.HashMap<>();
    private final java.util.HashMap<android.hardware.SensorEventListener, android.hardware.SystemSensorManager.SensorEventQueue> mSensorListeners = new java.util.HashMap<>();
    private final java.util.HashMap<android.hardware.TriggerEventListener, android.hardware.SystemSensorManager.TriggerEventQueue> mTriggerListeners = new java.util.HashMap<>();
    private java.util.HashMap<android.hardware.SensorManager.DynamicSensorCallback, android.os.Handler> mDynamicSensorCallbacks = new java.util.HashMap<>();
    private java.util.Optional<java.lang.Boolean> mHasHighSamplingRateSensorsPermission = java.util.Optional.empty();

    private static native void nativeClassInit();

    private static native int nativeConfigDirectChannel(long j, int i, int i2, int i3);

    private static native long nativeCreate(java.lang.String str);

    private static native int nativeCreateDirectChannel(long j, int i, long j2, int i2, int i3, android.hardware.HardwareBuffer hardwareBuffer);

    private static native void nativeDestroyDirectChannel(long j, int i);

    private static native boolean nativeGetDefaultDeviceSensorAtIndex(long j, android.hardware.Sensor sensor, int i);

    private static native void nativeGetDynamicSensors(long j, java.util.List<android.hardware.Sensor> list);

    private static native void nativeGetRuntimeSensors(long j, int i, java.util.List<android.hardware.Sensor> list);

    private static native boolean nativeGetSensorAtIndex(long j, android.hardware.Sensor sensor, int i);

    private static native boolean nativeIsDataInjectionEnabled(long j);

    private static native boolean nativeIsHalBypassReplayDataInjectionEnabled(long j);

    private static native boolean nativeIsReplayDataInjectionEnabled(long j);

    private static native int nativeSetOperationParameter(long j, int i, int i2, float[] fArr, int[] iArr);

    public SystemSensorManager(android.content.Context context, android.os.Looper looper) {
        synchronized (sLock) {
            if (!sNativeClassInited) {
                sNativeClassInited = true;
                nativeClassInit();
            }
        }
        this.mMainLooper = looper;
        android.content.pm.ApplicationInfo applicationInfo = context.getApplicationInfo();
        this.mTargetSdkLevel = applicationInfo.targetSdkVersion;
        this.mContext = context;
        this.mNativeInstance = nativeCreate(context.getOpPackageName());
        int i = applicationInfo.flags & 2;
        int i2 = 0;
        this.mIsPackageDebuggable = i != 0;
        while (true) {
            android.hardware.Sensor sensor = new android.hardware.Sensor();
            if (android.companion.virtual.flags.Flags.enableNativeVdm()) {
                if (!nativeGetDefaultDeviceSensorAtIndex(this.mNativeInstance, sensor, i2)) {
                    return;
                }
            } else if (!nativeGetSensorAtIndex(this.mNativeInstance, sensor, i2)) {
                return;
            }
            this.mFullSensorsList.add(sensor);
            this.mHandleToSensor.put(java.lang.Integer.valueOf(sensor.getHandle()), sensor);
            i2++;
        }
    }

    @Override // android.hardware.SensorManager
    public java.util.List<android.hardware.Sensor> getSensorList(int i) {
        java.util.List<android.hardware.Sensor> list;
        int deviceId = this.mContext.getDeviceId();
        if (isDeviceSensorPolicyDefault(deviceId)) {
            return super.getSensorList(i);
        }
        synchronized (this.mFullRuntimeSensorListByDevice) {
            java.util.List<android.hardware.Sensor> list2 = this.mFullRuntimeSensorListByDevice.get(deviceId);
            if (list2 == null) {
                list2 = createRuntimeSensorListLocked(deviceId);
            }
            android.util.SparseArray<java.util.List<android.hardware.Sensor>> sparseArray = this.mRuntimeSensorListByDeviceByType.get(deviceId);
            list = sparseArray.get(i);
            if (list == null) {
                if (i != -1) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (android.hardware.Sensor sensor : list2) {
                        if (sensor.getType() == i) {
                            arrayList.add(sensor);
                        }
                    }
                    list2 = arrayList;
                }
                list = java.util.Collections.unmodifiableList(list2);
                sparseArray.append(i, list);
            }
        }
        return list;
    }

    @Override // android.hardware.SensorManager
    protected java.util.List<android.hardware.Sensor> getFullSensorList() {
        java.util.List<android.hardware.Sensor> list;
        int deviceId = this.mContext.getDeviceId();
        if (isDeviceSensorPolicyDefault(deviceId)) {
            return this.mFullSensorsList;
        }
        synchronized (this.mFullRuntimeSensorListByDevice) {
            list = this.mFullRuntimeSensorListByDevice.get(deviceId);
            if (list == null) {
                list = createRuntimeSensorListLocked(deviceId);
            }
        }
        return list;
    }

    @Override // android.hardware.SensorManager
    public android.hardware.Sensor getSensorByHandle(int i) {
        return this.mHandleToSensor.get(java.lang.Integer.valueOf(i));
    }

    @Override // android.hardware.SensorManager
    protected java.util.List<android.hardware.Sensor> getFullDynamicSensorList() {
        setupDynamicSensorBroadcastReceiver();
        updateDynamicSensorList();
        return this.mFullDynamicSensorsList;
    }

    @Override // android.hardware.SensorManager
    protected boolean registerListenerImpl(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, int i, android.os.Handler handler, int i2, int i3) {
        java.lang.String name;
        if (sensorEventListener == null || sensor == null) {
            android.util.Log.e("SensorManager", "sensor or listener is null");
            return false;
        }
        if (sensor.getReportingMode() == 2) {
            android.util.Log.e("SensorManager", "Trigger Sensors should use the requestTriggerSensor.");
            return false;
        }
        if (i2 < 0 || i < 0) {
            android.util.Log.e("SensorManager", "maxBatchReportLatencyUs and delayUs should be non-negative");
            return false;
        }
        if (this.mSensorListeners.size() >= 128) {
            throw new java.lang.IllegalStateException("register failed, the sensor listeners size has exceeded the maximum limit 128");
        }
        synchronized (this.mSensorListeners) {
            android.hardware.SystemSensorManager.SensorEventQueue sensorEventQueue = this.mSensorListeners.get(sensorEventListener);
            if (sensorEventQueue == null) {
                android.os.Looper looper = handler != null ? handler.getLooper() : this.mMainLooper;
                if (sensorEventListener.getClass().getEnclosingClass() != null) {
                    name = sensorEventListener.getClass().getEnclosingClass().getName();
                } else {
                    name = sensorEventListener.getClass().getName();
                }
                android.hardware.SystemSensorManager.SensorEventQueue sensorEventQueue2 = new android.hardware.SystemSensorManager.SensorEventQueue(sensorEventListener, looper, this, name);
                if (!sensorEventQueue2.addSensor(sensor, i, i2)) {
                    sensorEventQueue2.dispose();
                    return false;
                }
                this.mSensorListeners.put(sensorEventListener, sensorEventQueue2);
                return true;
            }
            return sensorEventQueue.addSensor(sensor, i, i2);
        }
    }

    @Override // android.hardware.SensorManager
    protected void unregisterListenerImpl(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor) {
        boolean removeSensor;
        if (sensor != null && sensor.getReportingMode() == 2) {
            return;
        }
        synchronized (this.mSensorListeners) {
            android.hardware.SystemSensorManager.SensorEventQueue sensorEventQueue = this.mSensorListeners.get(sensorEventListener);
            if (sensorEventQueue != null) {
                if (sensor == null) {
                    removeSensor = sensorEventQueue.removeAllSensors();
                } else {
                    removeSensor = sensorEventQueue.removeSensor(sensor, true);
                }
                if (removeSensor && !sensorEventQueue.hasSensors()) {
                    this.mSensorListeners.remove(sensorEventListener);
                    sensorEventQueue.dispose();
                }
            }
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean requestTriggerSensorImpl(android.hardware.TriggerEventListener triggerEventListener, android.hardware.Sensor sensor) {
        java.lang.String name;
        if (sensor == null) {
            throw new java.lang.IllegalArgumentException("sensor cannot be null");
        }
        if (triggerEventListener == null) {
            throw new java.lang.IllegalArgumentException("listener cannot be null");
        }
        if (sensor.getReportingMode() != 2) {
            return false;
        }
        if (this.mTriggerListeners.size() >= 128) {
            throw new java.lang.IllegalStateException("request failed, the trigger listeners size has exceeded the maximum limit 128");
        }
        synchronized (this.mTriggerListeners) {
            android.hardware.SystemSensorManager.TriggerEventQueue triggerEventQueue = this.mTriggerListeners.get(triggerEventListener);
            if (triggerEventQueue == null) {
                if (triggerEventListener.getClass().getEnclosingClass() != null) {
                    name = triggerEventListener.getClass().getEnclosingClass().getName();
                } else {
                    name = triggerEventListener.getClass().getName();
                }
                android.hardware.SystemSensorManager.TriggerEventQueue triggerEventQueue2 = new android.hardware.SystemSensorManager.TriggerEventQueue(triggerEventListener, this.mMainLooper, this, name);
                if (!triggerEventQueue2.addSensor(sensor, 0, 0)) {
                    triggerEventQueue2.dispose();
                    return false;
                }
                this.mTriggerListeners.put(triggerEventListener, triggerEventQueue2);
                return true;
            }
            return triggerEventQueue.addSensor(sensor, 0, 0);
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean cancelTriggerSensorImpl(android.hardware.TriggerEventListener triggerEventListener, android.hardware.Sensor sensor, boolean z) {
        boolean removeSensor;
        if (sensor != null && sensor.getReportingMode() != 2) {
            return false;
        }
        synchronized (this.mTriggerListeners) {
            android.hardware.SystemSensorManager.TriggerEventQueue triggerEventQueue = this.mTriggerListeners.get(triggerEventListener);
            if (triggerEventQueue == null) {
                return false;
            }
            if (sensor == null) {
                removeSensor = triggerEventQueue.removeAllSensors();
            } else {
                removeSensor = triggerEventQueue.removeSensor(sensor, z);
            }
            if (removeSensor && !triggerEventQueue.hasSensors()) {
                this.mTriggerListeners.remove(triggerEventListener);
                triggerEventQueue.dispose();
            }
            return removeSensor;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean flushImpl(android.hardware.SensorEventListener sensorEventListener) {
        if (sensorEventListener == null) {
            throw new java.lang.IllegalArgumentException("listener cannot be null");
        }
        synchronized (this.mSensorListeners) {
            android.hardware.SystemSensorManager.SensorEventQueue sensorEventQueue = this.mSensorListeners.get(sensorEventListener);
            if (sensorEventQueue == null) {
                return false;
            }
            return sensorEventQueue.flush() == 0;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean initDataInjectionImpl(boolean z, int i) {
        boolean nativeIsDataInjectionEnabled;
        synchronized (sLock) {
            boolean z2 = true;
            if (z) {
                switch (i) {
                    case 1:
                        nativeIsDataInjectionEnabled = nativeIsDataInjectionEnabled(this.mNativeInstance);
                        break;
                    case 2:
                    default:
                        nativeIsDataInjectionEnabled = false;
                        break;
                    case 3:
                        nativeIsDataInjectionEnabled = nativeIsReplayDataInjectionEnabled(this.mNativeInstance);
                        break;
                    case 4:
                        nativeIsDataInjectionEnabled = nativeIsHalBypassReplayDataInjectionEnabled(this.mNativeInstance);
                        break;
                }
                if (!nativeIsDataInjectionEnabled) {
                    android.util.Log.e("SensorManager", "The correct Data Injection mode has not been enabled");
                    return false;
                }
                if (sInjectEventQueue != null && sInjectEventQueue.getDataInjectionMode() != i) {
                    sInjectEventQueue.dispose();
                    sInjectEventQueue = null;
                }
                if (sInjectEventQueue == null) {
                    try {
                        sInjectEventQueue = new android.hardware.SystemSensorManager.InjectEventQueue(this.mMainLooper, this, i, this.mContext.getPackageName());
                    } catch (java.lang.RuntimeException e) {
                        android.util.Log.e("SensorManager", "Cannot create InjectEventQueue: " + e);
                    }
                }
                if (sInjectEventQueue == null) {
                    z2 = false;
                }
                return z2;
            }
            if (sInjectEventQueue != null) {
                sInjectEventQueue.dispose();
                sInjectEventQueue = null;
            }
            return true;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean injectSensorDataImpl(android.hardware.Sensor sensor, float[] fArr, int i, long j) {
        synchronized (sLock) {
            if (sInjectEventQueue == null) {
                android.util.Log.e("SensorManager", "Data injection mode not activated before calling injectSensorData");
                return false;
            }
            if (sInjectEventQueue.getDataInjectionMode() != 4 && !sensor.isDataInjectionSupported()) {
                throw new java.lang.IllegalArgumentException("sensor does not support data injection");
            }
            int injectSensorData = sInjectEventQueue.injectSensorData(sensor.getHandle(), fArr, i, j);
            if (injectSensorData != 0) {
                sInjectEventQueue.dispose();
                sInjectEventQueue = null;
            }
            return injectSensorData == 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupSensorConnection(android.hardware.Sensor sensor) {
        this.mHandleToSensor.remove(java.lang.Integer.valueOf(sensor.getHandle()));
        if (sensor.getReportingMode() == 2) {
            synchronized (this.mTriggerListeners) {
                for (android.hardware.TriggerEventListener triggerEventListener : new java.util.HashMap(this.mTriggerListeners).keySet()) {
                    android.util.Log.i("SensorManager", "removed trigger listener" + triggerEventListener.toString() + " due to sensor disconnection");
                    cancelTriggerSensorImpl(triggerEventListener, sensor, true);
                }
            }
            return;
        }
        synchronized (this.mSensorListeners) {
            for (android.hardware.SensorEventListener sensorEventListener : new java.util.HashMap(this.mSensorListeners).keySet()) {
                android.util.Log.i("SensorManager", "removed event listener" + sensorEventListener.toString() + " due to sensor disconnection");
                unregisterListenerImpl(sensorEventListener, sensor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDynamicSensorList() {
        synchronized (this.mFullDynamicSensorsList) {
            if (this.mDynamicSensorListDirty) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                nativeGetDynamicSensors(this.mNativeInstance, arrayList);
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                final java.util.ArrayList<android.hardware.Sensor> arrayList3 = new java.util.ArrayList();
                final java.util.ArrayList arrayList4 = new java.util.ArrayList();
                if (diffSortedSensorList(this.mFullDynamicSensorsList, arrayList, arrayList2, arrayList3, arrayList4)) {
                    android.util.Log.i("SensorManager", "DYNS dynamic sensor list cached should be updated");
                    this.mFullDynamicSensorsList = arrayList2;
                    for (android.hardware.Sensor sensor : arrayList3) {
                        this.mHandleToSensor.put(java.lang.Integer.valueOf(sensor.getHandle()), sensor);
                    }
                    android.os.Handler handler = new android.os.Handler(this.mContext.getMainLooper());
                    for (java.util.Map.Entry<android.hardware.SensorManager.DynamicSensorCallback, android.os.Handler> entry : this.mDynamicSensorCallbacks.entrySet()) {
                        final android.hardware.SensorManager.DynamicSensorCallback key = entry.getKey();
                        (entry.getValue() == null ? handler : entry.getValue()).post(new java.lang.Runnable() { // from class: android.hardware.SystemSensorManager.1
                            @Override // java.lang.Runnable
                            public void run() {
                                java.util.Iterator it = arrayList3.iterator();
                                while (it.hasNext()) {
                                    key.onDynamicSensorConnected((android.hardware.Sensor) it.next());
                                }
                                java.util.Iterator it2 = arrayList4.iterator();
                                while (it2.hasNext()) {
                                    key.onDynamicSensorDisconnected((android.hardware.Sensor) it2.next());
                                }
                            }
                        });
                    }
                    java.util.Iterator it = arrayList4.iterator();
                    while (it.hasNext()) {
                        cleanupSensorConnection((android.hardware.Sensor) it.next());
                    }
                }
                this.mDynamicSensorListDirty = false;
            }
        }
    }

    private java.util.List<android.hardware.Sensor> createRuntimeSensorListLocked(int i) {
        if (android.companion.virtual.flags.Flags.vdmPublicApis()) {
            setupVirtualDeviceListener();
        } else {
            setupRuntimeSensorBroadcastReceiver();
        }
        java.util.ArrayList<android.hardware.Sensor> arrayList = new java.util.ArrayList();
        nativeGetRuntimeSensors(this.mNativeInstance, i, arrayList);
        this.mFullRuntimeSensorListByDevice.put(i, arrayList);
        this.mRuntimeSensorListByDeviceByType.put(i, new android.util.SparseArray<>());
        for (android.hardware.Sensor sensor : arrayList) {
            this.mHandleToSensor.put(java.lang.Integer.valueOf(sensor.getHandle()), sensor);
        }
        return arrayList;
    }

    private void setupRuntimeSensorBroadcastReceiver() {
        if (this.mRuntimeSensorBroadcastReceiver == null) {
            this.mRuntimeSensorBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: android.hardware.SystemSensorManager.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    if (intent.getAction().equals(android.companion.virtual.VirtualDeviceManager.ACTION_VIRTUAL_DEVICE_REMOVED)) {
                        synchronized (android.hardware.SystemSensorManager.this.mFullRuntimeSensorListByDevice) {
                            int intExtra = intent.getIntExtra(android.companion.virtual.VirtualDeviceManager.EXTRA_VIRTUAL_DEVICE_ID, 0);
                            java.util.List list = (java.util.List) android.hardware.SystemSensorManager.this.mFullRuntimeSensorListByDevice.removeReturnOld(intExtra);
                            if (list != null) {
                                java.util.Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    android.hardware.SystemSensorManager.this.cleanupSensorConnection((android.hardware.Sensor) it.next());
                                }
                            }
                            android.hardware.SystemSensorManager.this.mRuntimeSensorListByDeviceByType.remove(intExtra);
                        }
                    }
                }
            };
            android.content.IntentFilter intentFilter = new android.content.IntentFilter("virtual_device_removed");
            intentFilter.addAction(android.companion.virtual.VirtualDeviceManager.ACTION_VIRTUAL_DEVICE_REMOVED);
            this.mContext.registerReceiver(this.mRuntimeSensorBroadcastReceiver, intentFilter, 4);
        }
    }

    private void setupVirtualDeviceListener() {
        if (this.mVirtualDeviceListener != null) {
            return;
        }
        if (this.mVdm == null) {
            this.mVdm = (android.companion.virtual.VirtualDeviceManager) this.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
            if (this.mVdm == null) {
                return;
            }
        }
        this.mVirtualDeviceListener = new android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener() { // from class: android.hardware.SystemSensorManager.3
            @Override // android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener
            public void onVirtualDeviceClosed(int i) {
                synchronized (android.hardware.SystemSensorManager.this.mFullRuntimeSensorListByDevice) {
                    java.util.List list = (java.util.List) android.hardware.SystemSensorManager.this.mFullRuntimeSensorListByDevice.removeReturnOld(i);
                    if (list != null) {
                        java.util.Iterator it = list.iterator();
                        while (it.hasNext()) {
                            android.hardware.SystemSensorManager.this.cleanupSensorConnection((android.hardware.Sensor) it.next());
                        }
                    }
                    android.hardware.SystemSensorManager.this.mRuntimeSensorListByDeviceByType.remove(i);
                }
            }
        };
        this.mVdm.registerVirtualDeviceListener(this.mContext.getMainExecutor(), this.mVirtualDeviceListener);
    }

    private void setupDynamicSensorBroadcastReceiver() {
        if (this.mDynamicSensorBroadcastReceiver == null) {
            this.mDynamicSensorBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: android.hardware.SystemSensorManager.4
                @Override // android.content.BroadcastReceiver
                public void onReceive(android.content.Context context, android.content.Intent intent) {
                    if (intent.getAction().equals(android.content.Intent.ACTION_DYNAMIC_SENSOR_CHANGED)) {
                        android.util.Log.i("SensorManager", "DYNS received DYNAMIC_SENSOR_CHANGED broadcast");
                        android.hardware.SystemSensorManager.this.mDynamicSensorListDirty = true;
                        android.hardware.SystemSensorManager.this.updateDynamicSensorList();
                    }
                }
            };
            android.content.IntentFilter intentFilter = new android.content.IntentFilter("dynamic_sensor_change");
            intentFilter.addAction(android.content.Intent.ACTION_DYNAMIC_SENSOR_CHANGED);
            this.mContext.registerReceiver(this.mDynamicSensorBroadcastReceiver, intentFilter, 4);
        }
    }

    @Override // android.hardware.SensorManager
    protected void registerDynamicSensorCallbackImpl(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback, android.os.Handler handler) {
        android.util.Log.i("SensorManager", "DYNS Register dynamic sensor callback");
        if (dynamicSensorCallback == null) {
            throw new java.lang.IllegalArgumentException("callback cannot be null");
        }
        if (this.mDynamicSensorCallbacks.containsKey(dynamicSensorCallback)) {
            return;
        }
        setupDynamicSensorBroadcastReceiver();
        this.mDynamicSensorCallbacks.put(dynamicSensorCallback, handler);
    }

    @Override // android.hardware.SensorManager
    protected void unregisterDynamicSensorCallbackImpl(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        android.util.Log.i("SensorManager", "Removing dynamic sensor listener");
        this.mDynamicSensorCallbacks.remove(dynamicSensorCallback);
    }

    private static boolean diffSortedSensorList(java.util.List<android.hardware.Sensor> list, java.util.List<android.hardware.Sensor> list2, java.util.List<android.hardware.Sensor> list3, java.util.List<android.hardware.Sensor> list4, java.util.List<android.hardware.Sensor> list5) {
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (true) {
            if (i < list.size() && (i2 >= list2.size() || list2.get(i2).getHandle() > list.get(i).getHandle())) {
                if (list5 != null) {
                    list5.add(list.get(i));
                }
                i++;
                z = true;
            } else if (i2 < list2.size() && (i >= list.size() || list2.get(i2).getHandle() < list.get(i).getHandle())) {
                if (list4 != null) {
                    list4.add(list2.get(i2));
                }
                if (list3 != null) {
                    list3.add(list2.get(i2));
                }
                i2++;
                z = true;
            } else {
                if (i2 >= list2.size() || i >= list.size() || list2.get(i2).getHandle() != list.get(i).getHandle()) {
                    break;
                }
                if (list3 != null) {
                    list3.add(list.get(i));
                }
                i2++;
                i++;
            }
        }
        return z;
    }

    @Override // android.hardware.SensorManager
    protected int configureDirectChannelImpl(android.hardware.SensorDirectChannel sensorDirectChannel, android.hardware.Sensor sensor, int i) {
        if (!sensorDirectChannel.isOpen()) {
            throw new java.lang.IllegalStateException("channel is closed");
        }
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("rate parameter invalid");
        }
        if (sensor == null && i != 0) {
            throw new java.lang.IllegalArgumentException("when sensor is null, rate can only be DIRECT_RATE_STOP");
        }
        int handle = sensor == null ? -1 : sensor.getHandle();
        if (sensor != null && isSensorInCappedSet(sensor.getType()) && i > 1 && this.mIsPackageDebuggable && !hasHighSamplingRateSensorsPermission() && android.compat.Compatibility.isChangeEnabled(CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION)) {
            throw new java.lang.SecurityException("To use the sampling rate level " + i + ", app needs to declare the normal permission HIGH_SAMPLING_RATE_SENSORS.");
        }
        int nativeConfigDirectChannel = nativeConfigDirectChannel(this.mNativeInstance, sensorDirectChannel.getNativeHandle(), handle, i);
        if (i == 0) {
            if (nativeConfigDirectChannel == 0) {
                return 1;
            }
            return 0;
        }
        if (nativeConfigDirectChannel > 0) {
            return nativeConfigDirectChannel;
        }
        return 0;
    }

    @Override // android.hardware.SensorManager
    protected android.hardware.SensorDirectChannel createDirectChannelImpl(android.os.MemoryFile memoryFile, android.hardware.HardwareBuffer hardwareBuffer) {
        int i;
        long j;
        int i2;
        int i3;
        int deviceId = this.mContext.getDeviceId();
        if (!isDeviceSensorPolicyDefault(deviceId)) {
            i = deviceId;
        } else {
            i = 0;
        }
        if (memoryFile != null) {
            try {
                int int$ = memoryFile.getFileDescriptor().getInt$();
                if (memoryFile.length() < 104) {
                    throw new java.lang.IllegalArgumentException("Size of MemoryFile has to be greater than 104");
                }
                long length = memoryFile.length();
                int nativeCreateDirectChannel = nativeCreateDirectChannel(this.mNativeInstance, i, length, 1, int$, null);
                if (nativeCreateDirectChannel <= 0) {
                    throw new java.io.UncheckedIOException(new java.io.IOException("create MemoryFile direct channel failed " + nativeCreateDirectChannel));
                }
                j = length;
                i2 = nativeCreateDirectChannel;
                i3 = 1;
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalArgumentException("MemoryFile object is not valid");
            }
        } else if (hardwareBuffer != null) {
            if (hardwareBuffer.getFormat() != 33) {
                throw new java.lang.IllegalArgumentException("Format of HardwareBuffer must be BLOB");
            }
            if (hardwareBuffer.getHeight() == 1) {
                if (hardwareBuffer.getWidth() < 104) {
                    throw new java.lang.IllegalArgumentException("Width if HardwareBuffer must be greater than 104");
                }
                if ((hardwareBuffer.getUsage() & 8388608) == 0) {
                    throw new java.lang.IllegalArgumentException("HardwareBuffer must set usage flag USAGE_SENSOR_DIRECT_DATA");
                }
                long width = hardwareBuffer.getWidth();
                int nativeCreateDirectChannel2 = nativeCreateDirectChannel(this.mNativeInstance, i, width, 2, -1, hardwareBuffer);
                if (nativeCreateDirectChannel2 <= 0) {
                    throw new java.io.UncheckedIOException(new java.io.IOException("create HardwareBuffer direct channel failed " + nativeCreateDirectChannel2));
                }
                i3 = 2;
                i2 = nativeCreateDirectChannel2;
                j = width;
            } else {
                throw new java.lang.IllegalArgumentException("Height of HardwareBuffer must be 1");
            }
        } else {
            throw new java.lang.NullPointerException("shared memory object cannot be null");
        }
        return new android.hardware.SensorDirectChannel(this, i2, i3, j);
    }

    @Override // android.hardware.SensorManager
    protected void destroyDirectChannelImpl(android.hardware.SensorDirectChannel sensorDirectChannel) {
        if (sensorDirectChannel != null) {
            nativeDestroyDirectChannel(this.mNativeInstance, sensorDirectChannel.getNativeHandle());
        }
    }

    private static abstract class BaseEventQueue {
        protected static final int OPERATING_MODE_DATA_INJECTION = 1;
        protected static final int OPERATING_MODE_HAL_BYPASS_REPLAY_DATA_INJECTION = 4;
        protected static final int OPERATING_MODE_NORMAL = 0;
        protected static final int OPERATING_MODE_REPLAY_DATA_INJECTION = 3;
        protected final android.hardware.SystemSensorManager mManager;
        private long mNativeSensorEventQueue;
        private final android.util.SparseBooleanArray mActiveSensors = new android.util.SparseBooleanArray();
        protected final android.util.SparseIntArray mSensorAccuracies = new android.util.SparseIntArray();
        protected final android.util.SparseIntArray mSensorDiscontinuityCounts = new android.util.SparseIntArray();
        private final dalvik.system.CloseGuard mCloseGuard = dalvik.system.CloseGuard.get();

        private static native void nativeDestroySensorEventQueue(long j);

        private static native int nativeDisableSensor(long j, int i);

        private static native int nativeEnableSensor(long j, int i, int i2, int i3);

        private static native int nativeFlushSensor(long j);

        private static native long nativeInitBaseEventQueue(long j, java.lang.ref.WeakReference<android.hardware.SystemSensorManager.BaseEventQueue> weakReference, android.os.MessageQueue messageQueue, java.lang.String str, int i, java.lang.String str2, java.lang.String str3);

        private static native int nativeInjectSensorData(long j, int i, float[] fArr, int i2, long j2);

        protected abstract void addSensorEvent(android.hardware.Sensor sensor);

        protected abstract void dispatchFlushCompleteEvent(int i);

        protected abstract void dispatchSensorEvent(int i, float[] fArr, int i2, long j);

        protected abstract void removeSensorEvent(android.hardware.Sensor sensor);

        BaseEventQueue(android.os.Looper looper, android.hardware.SystemSensorManager systemSensorManager, int i, java.lang.String str) {
            this.mNativeSensorEventQueue = nativeInitBaseEventQueue(systemSensorManager.mNativeInstance, new java.lang.ref.WeakReference(this), looper.getQueue(), str == null ? "" : str, i, systemSensorManager.mContext.getOpPackageName(), systemSensorManager.mContext.getAttributionTag());
            this.mCloseGuard.open("BaseEventQueue.dispose");
            this.mManager = systemSensorManager;
        }

        public void dispose() {
            dispose(false);
        }

        public boolean addSensor(android.hardware.Sensor sensor, int i, int i2) {
            int handle = sensor.getHandle();
            if (this.mActiveSensors.get(handle)) {
                return false;
            }
            this.mActiveSensors.put(handle, true);
            addSensorEvent(sensor);
            if (enableSensor(sensor, i, i2) == 0 || (i2 != 0 && (i2 <= 0 || enableSensor(sensor, i, 0) == 0))) {
                return true;
            }
            removeSensor(sensor, false);
            return false;
        }

        public boolean removeAllSensors() {
            for (int i = 0; i < this.mActiveSensors.size(); i++) {
                if (this.mActiveSensors.valueAt(i)) {
                    int keyAt = this.mActiveSensors.keyAt(i);
                    android.hardware.Sensor sensor = (android.hardware.Sensor) this.mManager.mHandleToSensor.get(java.lang.Integer.valueOf(keyAt));
                    if (sensor != null) {
                        disableSensor(sensor);
                        this.mActiveSensors.put(keyAt, false);
                        removeSensorEvent(sensor);
                    }
                }
            }
            return true;
        }

        public boolean removeSensor(android.hardware.Sensor sensor, boolean z) {
            if (!this.mActiveSensors.get(sensor.getHandle())) {
                return false;
            }
            if (z) {
                disableSensor(sensor);
            }
            this.mActiveSensors.put(sensor.getHandle(), false);
            removeSensorEvent(sensor);
            return true;
        }

        public int flush() {
            if (this.mNativeSensorEventQueue == 0) {
                throw new java.lang.NullPointerException();
            }
            return nativeFlushSensor(this.mNativeSensorEventQueue);
        }

        public boolean hasSensors() {
            return this.mActiveSensors.indexOfValue(true) >= 0;
        }

        protected void finalize() throws java.lang.Throwable {
            try {
                dispose(true);
            } finally {
                super.finalize();
            }
        }

        private void dispose(boolean z) {
            if (this.mCloseGuard != null) {
                if (z) {
                    this.mCloseGuard.warnIfOpen();
                }
                this.mCloseGuard.close();
            }
            if (this.mNativeSensorEventQueue != 0) {
                nativeDestroySensorEventQueue(this.mNativeSensorEventQueue);
                this.mNativeSensorEventQueue = 0L;
            }
        }

        private int enableSensor(android.hardware.Sensor sensor, int i, int i2) {
            if (this.mNativeSensorEventQueue == 0) {
                throw new java.lang.NullPointerException();
            }
            if (sensor == null) {
                throw new java.lang.NullPointerException();
            }
            if (this.mManager.isSensorInCappedSet(sensor.getType()) && i < 5000 && this.mManager.mIsPackageDebuggable && !this.mManager.hasHighSamplingRateSensorsPermission() && android.compat.Compatibility.isChangeEnabled(android.hardware.SystemSensorManager.CHANGE_ID_SAMPLING_RATE_SENSORS_PERMISSION)) {
                throw new java.lang.SecurityException("To use the sampling rate of " + i + " microseconds, app needs to declare the normal permission HIGH_SAMPLING_RATE_SENSORS.");
            }
            return nativeEnableSensor(this.mNativeSensorEventQueue, sensor.getHandle(), i, i2);
        }

        protected int injectSensorDataBase(int i, float[] fArr, int i2, long j) {
            return nativeInjectSensorData(this.mNativeSensorEventQueue, i, fArr, i2, j);
        }

        private int disableSensor(android.hardware.Sensor sensor) {
            if (this.mNativeSensorEventQueue == 0) {
                throw new java.lang.NullPointerException();
            }
            if (sensor == null) {
                throw new java.lang.NullPointerException();
            }
            return nativeDisableSensor(this.mNativeSensorEventQueue, sensor.getHandle());
        }

        protected void dispatchAdditionalInfoEvent(int i, int i2, int i3, float[] fArr, int[] iArr) {
        }
    }

    static final class SensorEventQueue extends android.hardware.SystemSensorManager.BaseEventQueue {
        private final android.hardware.SensorEventListener mListener;
        private final android.util.SparseArray<android.hardware.SensorEvent> mSensorsEvents;

        public SensorEventQueue(android.hardware.SensorEventListener sensorEventListener, android.os.Looper looper, android.hardware.SystemSensorManager systemSensorManager, java.lang.String str) {
            super(looper, systemSensorManager, 0, str);
            this.mSensorsEvents = new android.util.SparseArray<>();
            this.mListener = sensorEventListener;
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void addSensorEvent(android.hardware.Sensor sensor) {
            android.hardware.SensorEvent sensorEvent = new android.hardware.SensorEvent(android.hardware.Sensor.getMaxLengthValuesArray(sensor, this.mManager.mTargetSdkLevel));
            synchronized (this.mSensorsEvents) {
                this.mSensorsEvents.put(sensor.getHandle(), sensorEvent);
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void removeSensorEvent(android.hardware.Sensor sensor) {
            synchronized (this.mSensorsEvents) {
                this.mSensorsEvents.delete(sensor.getHandle());
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(int i, float[] fArr, int i2, long j) {
            android.hardware.SensorEvent sensorEvent;
            android.hardware.Sensor sensor = (android.hardware.Sensor) this.mManager.mHandleToSensor.get(java.lang.Integer.valueOf(i));
            if (sensor == null) {
                return;
            }
            synchronized (this.mSensorsEvents) {
                sensorEvent = this.mSensorsEvents.get(i);
            }
            if (sensorEvent == null) {
                return;
            }
            java.lang.System.arraycopy(fArr, 0, sensorEvent.values, 0, sensorEvent.values.length);
            sensorEvent.timestamp = j;
            sensorEvent.accuracy = i2;
            sensorEvent.sensor = sensor;
            int i3 = this.mSensorAccuracies.get(i);
            if (sensorEvent.accuracy >= 0 && i3 != sensorEvent.accuracy) {
                this.mSensorAccuracies.put(i, sensorEvent.accuracy);
                this.mListener.onAccuracyChanged(sensorEvent.sensor, sensorEvent.accuracy);
            }
            sensorEvent.firstEventAfterDiscontinuity = false;
            if (sensorEvent.sensor.getType() == 37) {
                int i4 = this.mSensorDiscontinuityCounts.get(i);
                int floatToIntBits = java.lang.Float.floatToIntBits(fArr[6]);
                if (i4 >= 0 && i4 != floatToIntBits) {
                    this.mSensorDiscontinuityCounts.put(i, floatToIntBits);
                    sensorEvent.firstEventAfterDiscontinuity = true;
                }
            }
            this.mListener.onSensorChanged(sensorEvent);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int i) {
            android.hardware.Sensor sensor;
            if (!(this.mListener instanceof android.hardware.SensorEventListener2) || (sensor = (android.hardware.Sensor) this.mManager.mHandleToSensor.get(java.lang.Integer.valueOf(i))) == null) {
                return;
            }
            ((android.hardware.SensorEventListener2) this.mListener).onFlushCompleted(sensor);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchAdditionalInfoEvent(int i, int i2, int i3, float[] fArr, int[] iArr) {
            android.hardware.Sensor sensor;
            if (!(this.mListener instanceof android.hardware.SensorEventCallback) || (sensor = (android.hardware.Sensor) this.mManager.mHandleToSensor.get(java.lang.Integer.valueOf(i))) == null) {
                return;
            }
            ((android.hardware.SensorEventCallback) this.mListener).onSensorAdditionalInfo(new android.hardware.SensorAdditionalInfo(sensor, i2, i3, iArr, fArr));
        }
    }

    static final class TriggerEventQueue extends android.hardware.SystemSensorManager.BaseEventQueue {
        private final android.hardware.TriggerEventListener mListener;
        private final android.util.SparseArray<android.hardware.TriggerEvent> mTriggerEvents;

        public TriggerEventQueue(android.hardware.TriggerEventListener triggerEventListener, android.os.Looper looper, android.hardware.SystemSensorManager systemSensorManager, java.lang.String str) {
            super(looper, systemSensorManager, 0, str);
            this.mTriggerEvents = new android.util.SparseArray<>();
            this.mListener = triggerEventListener;
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void addSensorEvent(android.hardware.Sensor sensor) {
            android.hardware.TriggerEvent triggerEvent = new android.hardware.TriggerEvent(android.hardware.Sensor.getMaxLengthValuesArray(sensor, this.mManager.mTargetSdkLevel));
            synchronized (this.mTriggerEvents) {
                this.mTriggerEvents.put(sensor.getHandle(), triggerEvent);
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        public void removeSensorEvent(android.hardware.Sensor sensor) {
            synchronized (this.mTriggerEvents) {
                this.mTriggerEvents.delete(sensor.getHandle());
            }
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(int i, float[] fArr, int i2, long j) {
            android.hardware.TriggerEvent triggerEvent;
            android.hardware.Sensor sensor = (android.hardware.Sensor) this.mManager.mHandleToSensor.get(java.lang.Integer.valueOf(i));
            if (sensor == null) {
                return;
            }
            synchronized (this.mTriggerEvents) {
                triggerEvent = this.mTriggerEvents.get(i);
            }
            if (triggerEvent == null) {
                android.util.Log.e("SensorManager", "Error: Trigger Event is null for Sensor: " + sensor);
                return;
            }
            java.lang.System.arraycopy(fArr, 0, triggerEvent.values, 0, triggerEvent.values.length);
            triggerEvent.timestamp = j;
            triggerEvent.sensor = sensor;
            this.mManager.cancelTriggerSensorImpl(this.mListener, sensor, false);
            this.mListener.onTrigger(triggerEvent);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int i) {
        }
    }

    final class InjectEventQueue extends android.hardware.SystemSensorManager.BaseEventQueue {
        private int mMode;

        public InjectEventQueue(android.os.Looper looper, android.hardware.SystemSensorManager systemSensorManager, int i, java.lang.String str) {
            super(looper, systemSensorManager, i, str);
            this.mMode = i;
        }

        int injectSensorData(int i, float[] fArr, int i2, long j) {
            return injectSensorDataBase(i, fArr, i2, j);
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchSensorEvent(int i, float[] fArr, int i2, long j) {
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void dispatchFlushCompleteEvent(int i) {
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void addSensorEvent(android.hardware.Sensor sensor) {
        }

        @Override // android.hardware.SystemSensorManager.BaseEventQueue
        protected void removeSensorEvent(android.hardware.Sensor sensor) {
        }

        int getDataInjectionMode() {
            return this.mMode;
        }
    }

    @Override // android.hardware.SensorManager
    protected boolean setOperationParameterImpl(android.hardware.SensorAdditionalInfo sensorAdditionalInfo) {
        return nativeSetOperationParameter(this.mNativeInstance, sensorAdditionalInfo.sensor != null ? sensorAdditionalInfo.sensor.getHandle() : -1, sensorAdditionalInfo.type, sensorAdditionalInfo.floatValues, sensorAdditionalInfo.intValues) == 0;
    }

    private boolean isDeviceSensorPolicyDefault(int i) {
        if (i == 0) {
            return true;
        }
        if (this.mVdm == null) {
            this.mVdm = (android.companion.virtual.VirtualDeviceManager) this.mContext.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
        }
        if (this.mVdm == null || this.mVdm.getDevicePolicy(i, 0) == 0) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSensorInCappedSet(int i) {
        return i == 1 || i == 35 || i == 4 || i == 16 || i == 2 || i == 14;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasHighSamplingRateSensorsPermission() {
        if (!this.mHasHighSamplingRateSensorsPermission.isPresent()) {
            this.mHasHighSamplingRateSensorsPermission = java.util.Optional.of(java.lang.Boolean.valueOf(this.mContext.getPackageManager().checkPermission("android.permission.HIGH_SAMPLING_RATE_SENSORS", this.mContext.getApplicationInfo().packageName) == 0));
        }
        return this.mHasHighSamplingRateSensorsPermission.get().booleanValue();
    }
}
