package android.hardware.input;

/* loaded from: classes2.dex */
public class InputDeviceSensorManager {
    private static final boolean DEBUG = false;
    private static final int MSG_SENSOR_ACCURACY_CHANGED = 1;
    private static final int MSG_SENSOR_CHANGED = 2;
    private static final java.lang.String TAG = "InputDeviceSensorManager";
    private final android.hardware.input.InputManagerGlobal mGlobal;
    private android.hardware.input.InputDeviceSensorManager.InputSensorEventListener mInputServiceSensorListener;
    private android.os.HandlerThread mSensorThread;
    private final java.util.Map<java.lang.Integer, java.util.List<android.hardware.Sensor>> mSensors = new java.util.HashMap();
    private final java.lang.Object mInputSensorLock = new java.lang.Object();
    private final java.util.ArrayList<android.hardware.input.InputDeviceSensorManager.InputSensorEventListenerDelegate> mInputSensorEventListeners = new java.util.ArrayList<>();

    public InputDeviceSensorManager(android.hardware.input.InputManagerGlobal inputManagerGlobal) {
        this.mGlobal = inputManagerGlobal;
        initializeSensors();
    }

    android.hardware.SensorManager getSensorManager(int i) {
        return new android.hardware.input.InputDeviceSensorManager.InputSensorManager(i);
    }

    private void updateInputDeviceSensorInfoLocked(int i) {
        android.view.InputDevice device = android.view.InputDevice.getDevice(i);
        if (device != null && device.hasSensor()) {
            populateSensorsForInputDeviceLocked(i, this.mGlobal.getSensorList(i));
        }
    }

    public void onInputDeviceAdded(int i) {
        synchronized (this.mInputSensorLock) {
            if (!this.mSensors.containsKey(java.lang.Integer.valueOf(i))) {
                updateInputDeviceSensorInfoLocked(i);
            } else {
                android.util.Slog.e(TAG, "Received 'device added' notification for device " + i + ", but it is already in the list");
            }
        }
    }

    public void onInputDeviceRemoved(int i) {
        synchronized (this.mInputSensorLock) {
            this.mSensors.remove(java.lang.Integer.valueOf(i));
        }
    }

    public void onInputDeviceChanged(int i) {
        synchronized (this.mInputSensorLock) {
            this.mSensors.remove(java.lang.Integer.valueOf(i));
            updateInputDeviceSensorInfoLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean sensorEquals(android.hardware.Sensor sensor, android.hardware.Sensor sensor2) {
        return sensor.getType() == sensor2.getType() && sensor.getId() == sensor2.getId();
    }

    private void populateSensorsForInputDeviceLocked(int i, android.hardware.input.InputSensorInfo[] inputSensorInfoArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.input.InputSensorInfo inputSensorInfo : inputSensorInfoArr) {
            arrayList.add(new android.hardware.Sensor(inputSensorInfo));
        }
        this.mSensors.put(java.lang.Integer.valueOf(i), arrayList);
    }

    private void initializeSensors() {
        synchronized (this.mInputSensorLock) {
            this.mSensors.clear();
            for (int i : this.mGlobal.getInputDeviceIds()) {
                updateInputDeviceSensorInfoLocked(i);
            }
        }
    }

    private android.hardware.Sensor getInputDeviceSensorLocked(int i, int i2) {
        for (android.hardware.Sensor sensor : this.mSensors.get(java.lang.Integer.valueOf(i))) {
            if (sensor.getType() == i2) {
                return sensor;
            }
        }
        return null;
    }

    private int findSensorEventListenerLocked(android.hardware.SensorEventListener sensorEventListener) {
        for (int i = 0; i < this.mInputSensorEventListeners.size(); i++) {
            if (this.mInputSensorEventListeners.get(i).getListener() == sensorEventListener) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInputSensorChanged(int i, int i2, int i3, long j, float[] fArr) {
        synchronized (this.mInputSensorLock) {
            android.hardware.Sensor inputDeviceSensorLocked = getInputDeviceSensorLocked(i, i2);
            if (inputDeviceSensorLocked == null) {
                android.util.Slog.wtf(TAG, "onInputSensorChanged: Got sensor update for device " + i + " but the sensor was not found.");
                return;
            }
            for (int i4 = 0; i4 < this.mInputSensorEventListeners.size(); i4++) {
                android.hardware.input.InputDeviceSensorManager.InputSensorEventListenerDelegate inputSensorEventListenerDelegate = this.mInputSensorEventListeners.get(i4);
                if (inputSensorEventListenerDelegate.hasSensorRegistered(i, i2)) {
                    android.hardware.SensorEvent sensorEvent = inputSensorEventListenerDelegate.getSensorEvent(inputDeviceSensorLocked);
                    if (sensorEvent == null) {
                        android.util.Slog.wtf(TAG, "Failed to get SensorEvent.");
                        return;
                    }
                    sensorEvent.sensor = inputDeviceSensorLocked;
                    sensorEvent.accuracy = i3;
                    sensorEvent.timestamp = j;
                    java.lang.System.arraycopy(fArr, 0, sensorEvent.values, 0, sensorEvent.values.length);
                    inputSensorEventListenerDelegate.sendSensorChanged(sensorEvent);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInputSensorAccuracyChanged(int i, int i2, int i3) {
        synchronized (this.mInputSensorLock) {
            for (int i4 = 0; i4 < this.mInputSensorEventListeners.size(); i4++) {
                android.hardware.input.InputDeviceSensorManager.InputSensorEventListenerDelegate inputSensorEventListenerDelegate = this.mInputSensorEventListeners.get(i4);
                if (inputSensorEventListenerDelegate.hasSensorRegistered(i, i2)) {
                    inputSensorEventListenerDelegate.sendSensorAccuracyChanged(i, i2, i3);
                }
            }
        }
    }

    private final class InputSensorEventListener extends android.hardware.input.IInputSensorEventListener.Stub {
        private InputSensorEventListener() {
        }

        @Override // android.hardware.input.IInputSensorEventListener
        public void onInputSensorChanged(int i, int i2, int i3, long j, float[] fArr) throws android.os.RemoteException {
            android.hardware.input.InputDeviceSensorManager.this.onInputSensorChanged(i, i2, i3, j, fArr);
        }

        @Override // android.hardware.input.IInputSensorEventListener
        public void onInputSensorAccuracyChanged(int i, int i2, int i3) throws android.os.RemoteException {
            android.hardware.input.InputDeviceSensorManager.this.onInputSensorAccuracyChanged(i, i2, i3);
        }
    }

    private static final class InputSensorEventListenerDelegate extends android.os.Handler {
        private final android.hardware.SensorEventListener mListener;
        private final android.util.SparseArray<android.hardware.SensorEvent> mSensorEvents;
        private final java.util.List<android.hardware.Sensor> mSensors;

        InputSensorEventListenerDelegate(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, android.os.Looper looper) {
            super(looper);
            this.mSensors = new java.util.ArrayList();
            this.mSensorEvents = new android.util.SparseArray<>();
            this.mListener = sensorEventListener;
            addSensor(sensor);
        }

        public java.util.List<android.hardware.Sensor> getSensors() {
            return this.mSensors;
        }

        public boolean isEmpty() {
            return this.mSensors.isEmpty();
        }

        public void removeSensor(android.hardware.Sensor sensor) {
            if (sensor == null) {
                this.mSensors.clear();
                this.mSensorEvents.clear();
                return;
            }
            java.util.Iterator<android.hardware.Sensor> it = this.mSensors.iterator();
            while (it.hasNext()) {
                if (android.hardware.input.InputDeviceSensorManager.sensorEquals(it.next(), sensor)) {
                    this.mSensors.remove(sensor);
                    this.mSensorEvents.remove(sensor.getType());
                }
            }
        }

        public void addSensor(android.hardware.Sensor sensor) {
            java.util.Iterator<android.hardware.Sensor> it = this.mSensors.iterator();
            while (it.hasNext()) {
                if (android.hardware.input.InputDeviceSensorManager.sensorEquals(it.next(), sensor)) {
                    android.util.Slog.w(android.hardware.input.InputDeviceSensorManager.TAG, "Adding sensor " + sensor + " already exist!");
                    return;
                }
            }
            this.mSensors.add(sensor);
            this.mSensorEvents.put(sensor.getType(), new android.hardware.SensorEvent(sensor, -1, 0L, new float[android.hardware.Sensor.getMaxLengthValuesArray(sensor, android.os.Build.VERSION.SDK_INT)]));
        }

        public boolean hasSensorRegistered(int i, int i2) {
            for (android.hardware.Sensor sensor : this.mSensors) {
                if (sensor.getType() == i2 && sensor.getId() == i) {
                    return true;
                }
            }
            return false;
        }

        public android.hardware.SensorEventListener getListener() {
            return this.mListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.hardware.SensorEvent getSensorEvent(android.hardware.Sensor sensor) {
            return this.mSensorEvents.get(sensor.getType());
        }

        public void sendSensorChanged(android.hardware.SensorEvent sensorEvent) {
            obtainMessage(2, sensorEvent).sendToTarget();
        }

        public void sendSensorAccuracyChanged(int i, int i2, int i3) {
            obtainMessage(1, i, i2, java.lang.Integer.valueOf(i3)).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    int i = message.arg1;
                    int i2 = message.arg2;
                    int intValue = ((java.lang.Integer) message.obj).intValue();
                    for (android.hardware.Sensor sensor : this.mSensors) {
                        if (sensor.getId() == i && sensor.getType() == i2) {
                            this.mListener.onAccuracyChanged(sensor, intValue);
                        }
                    }
                    break;
                case 2:
                    this.mListener.onSensorChanged((android.hardware.SensorEvent) message.obj);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.hardware.Sensor getSensorForInputDevice(int i, int i2) {
        synchronized (this.mInputSensorLock) {
            java.util.Iterator<java.util.Map.Entry<java.lang.Integer, java.util.List<android.hardware.Sensor>>> it = this.mSensors.entrySet().iterator();
            while (it.hasNext()) {
                for (android.hardware.Sensor sensor : it.next().getValue()) {
                    if (sensor.getId() == i && sensor.getType() == i2) {
                        return sensor;
                    }
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<android.hardware.Sensor> getFullSensorListForDevice(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mInputSensorLock) {
            java.util.Iterator<java.util.Map.Entry<java.lang.Integer, java.util.List<android.hardware.Sensor>>> it = this.mSensors.entrySet().iterator();
            while (it.hasNext()) {
                for (android.hardware.Sensor sensor : it.next().getValue()) {
                    if (sensor.getId() == i) {
                        arrayList.add(sensor);
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean registerListenerInternal(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, int i, int i2, android.os.Handler handler) {
        if (sensorEventListener == null) {
            android.util.Slog.e(TAG, "listener is null");
            return false;
        }
        if (sensor == null) {
            android.util.Slog.e(TAG, "sensor is null");
            return false;
        }
        if (sensor.getReportingMode() == 2) {
            android.util.Slog.e(TAG, "Trigger Sensors should use the requestTriggerSensor.");
            return false;
        }
        if (i2 < 0 || i < 0) {
            android.util.Slog.e(TAG, "maxBatchReportLatencyUs and delayUs should be non-negative");
            return false;
        }
        synchronized (this.mInputSensorLock) {
            if (getSensorForInputDevice(sensor.getId(), sensor.getType()) != null) {
                int id = sensor.getId();
                android.view.InputDevice inputDevice = this.mGlobal.getInputDevice(id);
                if (inputDevice == null) {
                    android.util.Slog.e(TAG, "input device not found for sensor " + sensor.getId());
                    return false;
                }
                if (!inputDevice.hasSensor()) {
                    android.util.Slog.e(TAG, "The device doesn't have the sensor:" + sensor);
                    return false;
                }
                if (!this.mGlobal.enableSensor(id, sensor.getType(), i, i2)) {
                    android.util.Slog.e(TAG, "Can't enable the sensor:" + sensor);
                    return false;
                }
            }
            if (this.mInputServiceSensorListener == null) {
                this.mInputServiceSensorListener = new android.hardware.input.InputDeviceSensorManager.InputSensorEventListener();
                if (!this.mGlobal.registerSensorListener(this.mInputServiceSensorListener)) {
                    android.util.Slog.e(TAG, "Failed registering the sensor listener");
                    return false;
                }
            }
            int findSensorEventListenerLocked = findSensorEventListenerLocked(sensorEventListener);
            if (findSensorEventListenerLocked < 0) {
                this.mInputSensorEventListeners.add(new android.hardware.input.InputDeviceSensorManager.InputSensorEventListenerDelegate(sensorEventListener, sensor, getLooperForListenerLocked(handler)));
            } else {
                this.mInputSensorEventListeners.get(findSensorEventListenerLocked).addSensor(sensor);
            }
            return true;
        }
    }

    private android.os.Looper getLooperForListenerLocked(android.os.Handler handler) {
        if (handler != null) {
            return handler.getLooper();
        }
        if (this.mSensorThread == null) {
            this.mSensorThread = new android.os.HandlerThread("SensorThread");
            this.mSensorThread.start();
        }
        return this.mSensorThread.getLooper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterListenerInternal(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor) {
        if (sensorEventListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mInputSensorLock) {
            int findSensorEventListenerLocked = findSensorEventListenerLocked(sensorEventListener);
            if (findSensorEventListenerLocked >= 0) {
                android.hardware.input.InputDeviceSensorManager.InputSensorEventListenerDelegate inputSensorEventListenerDelegate = this.mInputSensorEventListeners.get(findSensorEventListenerLocked);
                java.util.ArrayList<android.hardware.Sensor> arrayList = new java.util.ArrayList(inputSensorEventListenerDelegate.getSensors());
                inputSensorEventListenerDelegate.removeSensor(sensor);
                if (inputSensorEventListenerDelegate.isEmpty()) {
                    this.mInputSensorEventListeners.remove(findSensorEventListenerLocked);
                }
                if (this.mInputServiceSensorListener != null && this.mInputSensorEventListeners.isEmpty()) {
                    this.mGlobal.unregisterSensorListener(this.mInputServiceSensorListener);
                    this.mInputServiceSensorListener = null;
                }
                for (android.hardware.Sensor sensor2 : arrayList) {
                    int id = sensor2.getId();
                    int type = sensor2.getType();
                    boolean z = false;
                    int i = 0;
                    while (true) {
                        if (i >= this.mInputSensorEventListeners.size()) {
                            break;
                        }
                        if (!this.mInputSensorEventListeners.get(i).hasSensorRegistered(id, type)) {
                            i++;
                        } else {
                            android.util.Slog.w(TAG, "device " + id + " still uses sensor " + type);
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        this.mGlobal.disableSensor(id, type);
                    }
                }
                return;
            }
            android.util.Slog.e(TAG, "Listener is not registered");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean flushInternal(android.hardware.SensorEventListener sensorEventListener) {
        synchronized (this.mInputSensorLock) {
            int findSensorEventListenerLocked = findSensorEventListenerLocked(sensorEventListener);
            if (findSensorEventListenerLocked < 0) {
                return false;
            }
            for (android.hardware.Sensor sensor : this.mInputSensorEventListeners.get(findSensorEventListenerLocked).getSensors()) {
                if (!this.mGlobal.flushSensor(sensor.getId(), sensor.getType())) {
                    return false;
                }
            }
            return true;
        }
    }

    public class InputSensorManager extends android.hardware.SensorManager {
        final int mId;

        InputSensorManager(int i) {
            this.mId = i;
        }

        @Override // android.hardware.SensorManager
        public android.hardware.Sensor getDefaultSensor(int i) {
            return android.hardware.input.InputDeviceSensorManager.this.getSensorForInputDevice(this.mId, i);
        }

        @Override // android.hardware.SensorManager
        protected java.util.List<android.hardware.Sensor> getFullSensorList() {
            return android.hardware.input.InputDeviceSensorManager.this.getFullSensorListForDevice(this.mId);
        }

        @Override // android.hardware.SensorManager
        protected java.util.List<android.hardware.Sensor> getFullDynamicSensorList() {
            return new java.util.ArrayList();
        }

        @Override // android.hardware.SensorManager
        protected boolean registerListenerImpl(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor, int i, android.os.Handler handler, int i2, int i3) {
            return android.hardware.input.InputDeviceSensorManager.this.registerListenerInternal(sensorEventListener, sensor, i, i2, handler);
        }

        @Override // android.hardware.SensorManager
        protected void unregisterListenerImpl(android.hardware.SensorEventListener sensorEventListener, android.hardware.Sensor sensor) {
            android.hardware.input.InputDeviceSensorManager.this.unregisterListenerInternal(sensorEventListener, sensor);
        }

        @Override // android.hardware.SensorManager
        protected boolean flushImpl(android.hardware.SensorEventListener sensorEventListener) {
            return android.hardware.input.InputDeviceSensorManager.this.flushInternal(sensorEventListener);
        }

        @Override // android.hardware.SensorManager
        protected android.hardware.SensorDirectChannel createDirectChannelImpl(android.os.MemoryFile memoryFile, android.hardware.HardwareBuffer hardwareBuffer) {
            return null;
        }

        @Override // android.hardware.SensorManager
        protected void destroyDirectChannelImpl(android.hardware.SensorDirectChannel sensorDirectChannel) {
        }

        @Override // android.hardware.SensorManager
        protected int configureDirectChannelImpl(android.hardware.SensorDirectChannel sensorDirectChannel, android.hardware.Sensor sensor, int i) {
            return 0;
        }

        @Override // android.hardware.SensorManager
        protected void registerDynamicSensorCallbackImpl(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback, android.os.Handler handler) {
        }

        @Override // android.hardware.SensorManager
        protected void unregisterDynamicSensorCallbackImpl(android.hardware.SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        }

        @Override // android.hardware.SensorManager
        protected boolean requestTriggerSensorImpl(android.hardware.TriggerEventListener triggerEventListener, android.hardware.Sensor sensor) {
            return true;
        }

        @Override // android.hardware.SensorManager
        protected boolean cancelTriggerSensorImpl(android.hardware.TriggerEventListener triggerEventListener, android.hardware.Sensor sensor, boolean z) {
            return true;
        }

        @Override // android.hardware.SensorManager
        protected boolean initDataInjectionImpl(boolean z, int i) {
            return false;
        }

        @Override // android.hardware.SensorManager
        protected boolean injectSensorDataImpl(android.hardware.Sensor sensor, float[] fArr, int i, long j) {
            return false;
        }

        @Override // android.hardware.SensorManager
        protected boolean setOperationParameterImpl(android.hardware.SensorAdditionalInfo sensorAdditionalInfo) {
            return false;
        }
    }
}
