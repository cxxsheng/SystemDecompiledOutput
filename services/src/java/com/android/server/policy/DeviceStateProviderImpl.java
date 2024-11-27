package com.android.server.policy;

/* loaded from: classes2.dex */
public final class DeviceStateProviderImpl implements com.android.server.devicestate.DeviceStateProvider, com.android.server.input.InputManagerInternal.LidSwitchCallback, android.hardware.SensorEventListener, android.os.PowerManager.OnThermalStatusChangedListener {
    private static final java.lang.String CONFIG_FILE_NAME = "device_state_configuration.xml";
    private static final java.lang.String DATA_CONFIG_FILE_PATH = "system/devicestate/";
    private static final boolean DEBUG = false;
    private static final java.lang.String FLAG_APP_INACCESSIBLE = "FLAG_APP_INACCESSIBLE";
    private static final java.lang.String FLAG_CANCEL_OVERRIDE_REQUESTS = "FLAG_CANCEL_OVERRIDE_REQUESTS";
    private static final java.lang.String FLAG_CANCEL_WHEN_REQUESTER_NOT_ON_TOP = "FLAG_CANCEL_WHEN_REQUESTER_NOT_ON_TOP";
    private static final java.lang.String FLAG_EMULATED_ONLY = "FLAG_EMULATED_ONLY";
    private static final java.lang.String FLAG_UNSUPPORTED_WHEN_POWER_SAVE_MODE = "FLAG_UNSUPPORTED_WHEN_POWER_SAVE_MODE";
    private static final java.lang.String FLAG_UNSUPPORTED_WHEN_THERMAL_STATUS_CRITICAL = "FLAG_UNSUPPORTED_WHEN_THERMAL_STATUS_CRITICAL";
    private static final java.lang.String TAG = "DeviceStateProviderImpl";
    private static final java.lang.String VENDOR_CONFIG_FILE_PATH = "etc/devicestate/";
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Boolean mIsLidOpen;
    private final android.hardware.devicestate.DeviceState[] mOrderedStates;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPowerSaveModeEnabled;
    private static final java.util.function.BooleanSupplier TRUE_BOOLEAN_SUPPLIER = new java.util.function.BooleanSupplier() { // from class: com.android.server.policy.DeviceStateProviderImpl$$ExternalSyntheticLambda0
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            boolean lambda$static$0;
            lambda$static$0 = com.android.server.policy.DeviceStateProviderImpl.lambda$static$0();
            return lambda$static$0;
        }
    };
    private static final java.util.function.BooleanSupplier FALSE_BOOLEAN_SUPPLIER = new java.util.function.BooleanSupplier() { // from class: com.android.server.policy.DeviceStateProviderImpl$$ExternalSyntheticLambda1
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            boolean lambda$static$1;
            lambda$static$1 = com.android.server.policy.DeviceStateProviderImpl.lambda$static$1();
            return lambda$static$1;
        }
    };

    @com.android.internal.annotations.VisibleForTesting
    static final android.hardware.devicestate.DeviceState DEFAULT_DEVICE_STATE = new android.hardware.devicestate.DeviceState(0, "DEFAULT", 0);
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.SparseArray<java.util.function.BooleanSupplier> mStateConditions = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.devicestate.DeviceStateProvider.Listener mListener = null;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mLastReportedState = -1;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<android.hardware.Sensor, android.hardware.SensorEvent> mLatestSensorEvent = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mThermalStatus = 0;

    interface ReadableConfig {
        @android.annotation.NonNull
        java.io.InputStream openRead() throws java.io.IOException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$0() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$1() {
        return false;
    }

    public static com.android.server.policy.DeviceStateProviderImpl create(@android.annotation.NonNull android.content.Context context) {
        java.io.File configurationFile = getConfigurationFile();
        if (configurationFile == null) {
            return createFromConfig(context, null);
        }
        return createFromConfig(context, new com.android.server.policy.DeviceStateProviderImpl.ReadableFileConfig(configurationFile));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.internal.annotations.VisibleForTesting
    static com.android.server.policy.DeviceStateProviderImpl createFromConfig(@android.annotation.NonNull android.content.Context context, @android.annotation.Nullable com.android.server.policy.DeviceStateProviderImpl.ReadableConfig readableConfig) {
        com.android.server.policy.devicestate.config.DeviceStateConfig parseConfig;
        char c;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        if (readableConfig != null && (parseConfig = parseConfig(readableConfig)) != null) {
            for (com.android.server.policy.devicestate.config.DeviceState deviceState : parseConfig.getDeviceState()) {
                int intValue = deviceState.getIdentifier().intValue();
                java.lang.String name = deviceState.getName() == null ? "" : deviceState.getName();
                com.android.server.policy.devicestate.config.Flags flags = deviceState.getFlags();
                int i = 0;
                if (flags != null) {
                    java.util.List<java.lang.String> flag = flags.getFlag();
                    int i2 = 0;
                    for (int i3 = 0; i3 < flag.size(); i3++) {
                        java.lang.String str = flag.get(i3);
                        switch (str.hashCode()) {
                            case -1145436729:
                                if (str.equals(FLAG_EMULATED_ONLY)) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1134441332:
                                if (str.equals(FLAG_APP_INACCESSIBLE)) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1054037563:
                                if (str.equals(FLAG_CANCEL_OVERRIDE_REQUESTS)) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -900521097:
                                if (str.equals(FLAG_CANCEL_WHEN_REQUESTER_NOT_ON_TOP)) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 574499747:
                                if (str.equals(FLAG_UNSUPPORTED_WHEN_POWER_SAVE_MODE)) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1671240668:
                                if (str.equals(FLAG_UNSUPPORTED_WHEN_THERMAL_STATUS_CRITICAL)) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                i2 |= 1;
                                break;
                            case 1:
                                i2 |= 2;
                                break;
                            case 2:
                                i2 |= 4;
                                break;
                            case 3:
                                i2 |= 8;
                                break;
                            case 4:
                                i2 |= 16;
                                break;
                            case 5:
                                i2 |= 32;
                            default:
                                android.util.Slog.w(TAG, "Parsed unknown flag with name: " + str);
                                break;
                        }
                    }
                    i = i2;
                }
                arrayList.add(new android.hardware.devicestate.DeviceState(intValue, name, i));
                arrayList2.add(deviceState.getConditions());
            }
        }
        if (arrayList.size() == 0) {
            arrayList.add(DEFAULT_DEVICE_STATE);
            arrayList2.add(null);
        }
        return new com.android.server.policy.DeviceStateProviderImpl(context, arrayList, arrayList2);
    }

    private DeviceStateProviderImpl(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.List<android.hardware.devicestate.DeviceState> list, @android.annotation.NonNull java.util.List<com.android.server.policy.devicestate.config.Conditions> list2) {
        com.android.internal.util.Preconditions.checkArgument(list.size() == list2.size(), "Number of device states must be equal to the number of device state conditions.");
        this.mContext = context;
        android.hardware.devicestate.DeviceState[] deviceStateArr = (android.hardware.devicestate.DeviceState[]) list.toArray(new android.hardware.devicestate.DeviceState[list.size()]);
        java.util.Arrays.sort(deviceStateArr, java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.server.policy.DeviceStateProviderImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                return ((android.hardware.devicestate.DeviceState) obj).getIdentifier();
            }
        }));
        this.mOrderedStates = deviceStateArr;
        setStateConditions(list, list2);
        final android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        if (powerManager != null) {
            if (hasThermalSensitiveState(list)) {
                powerManager.addThermalStatusListener(this);
            }
            if (hasPowerSaveSensitiveState(list)) {
                this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.policy.DeviceStateProviderImpl.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(android.content.Context context2, android.content.Intent intent) {
                        if ("android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL".equals(intent.getAction())) {
                            com.android.server.policy.DeviceStateProviderImpl.this.onPowerSaveModeChanged(powerManager.isPowerSaveMode());
                        }
                    }
                }, new android.content.IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL"));
            }
        }
    }

    private void setStateConditions(@android.annotation.NonNull java.util.List<android.hardware.devicestate.DeviceState> list, @android.annotation.NonNull java.util.List<com.android.server.policy.devicestate.config.Conditions> list2) {
        boolean z;
        boolean z2;
        android.util.ArraySet arraySet = new android.util.ArraySet();
        boolean z3 = false;
        for (int i = 0; i < list2.size(); i++) {
            int identifier = list.get(i).getIdentifier();
            com.android.server.policy.devicestate.config.Conditions conditions = list2.get(i);
            if (conditions == null) {
                if (list.get(i).hasFlag(4)) {
                    this.mStateConditions.put(identifier, FALSE_BOOLEAN_SUPPLIER);
                } else {
                    this.mStateConditions.put(identifier, TRUE_BOOLEAN_SUPPLIER);
                }
            } else {
                android.util.ArraySet arraySet2 = new android.util.ArraySet();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                com.android.server.policy.devicestate.config.LidSwitchCondition lidSwitch = conditions.getLidSwitch();
                if (lidSwitch != null) {
                    arrayList.add(new com.android.server.policy.DeviceStateProviderImpl.LidSwitchBooleanSupplier(lidSwitch.getOpen()));
                    z = true;
                } else {
                    z = false;
                }
                java.util.List<com.android.server.policy.devicestate.config.SensorCondition> sensor = conditions.getSensor();
                int i2 = 0;
                while (true) {
                    if (i2 >= sensor.size()) {
                        z2 = true;
                        break;
                    }
                    com.android.server.policy.devicestate.config.SensorCondition sensorCondition = sensor.get(i2);
                    java.lang.String type = sensorCondition.getType();
                    java.lang.String name = sensorCondition.getName();
                    android.hardware.Sensor findSensor = findSensor(type, name);
                    if (findSensor != null) {
                        arrayList.add(new com.android.server.policy.DeviceStateProviderImpl.SensorBooleanSupplier(findSensor, sensorCondition.getValue()));
                        arraySet2.add(findSensor);
                        i2++;
                    } else {
                        android.util.Slog.e(TAG, "Failed to find Sensor with type: " + type + " and name: " + name);
                        z2 = false;
                        break;
                    }
                }
                if (z2) {
                    z3 |= z;
                    arraySet.addAll(arraySet2);
                    if (arrayList.size() > 1) {
                        this.mStateConditions.put(identifier, new com.android.server.policy.DeviceStateProviderImpl.AndBooleanSupplier(arrayList));
                    } else if (arrayList.size() > 0) {
                        this.mStateConditions.put(identifier, (java.util.function.BooleanSupplier) arrayList.get(0));
                    } else {
                        this.mStateConditions.put(identifier, TRUE_BOOLEAN_SUPPLIER);
                    }
                } else {
                    this.mStateConditions.put(identifier, FALSE_BOOLEAN_SUPPLIER);
                }
            }
        }
        if (z3) {
            ((com.android.server.input.InputManagerInternal) com.android.server.LocalServices.getService(com.android.server.input.InputManagerInternal.class)).registerLidSwitchCallback(this);
        }
        android.hardware.SensorManager sensorManager = (android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class);
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
            sensorManager.registerListener(this, (android.hardware.Sensor) arraySet.valueAt(i3), 0);
        }
    }

    @android.annotation.Nullable
    private android.hardware.Sensor findSensor(java.lang.String str, java.lang.String str2) {
        java.util.List<android.hardware.Sensor> sensorList = ((android.hardware.SensorManager) this.mContext.getSystemService(android.hardware.SensorManager.class)).getSensorList(-1);
        for (int i = 0; i < sensorList.size(); i++) {
            android.hardware.Sensor sensor = sensorList.get(i);
            java.lang.String stringType = sensor.getStringType();
            java.lang.String name = sensor.getName();
            if (stringType != null && name != null && stringType.equals(str) && name.equals(str2)) {
                return sensor;
            }
        }
        return null;
    }

    @Override // com.android.server.devicestate.DeviceStateProvider
    public void setListener(com.android.server.devicestate.DeviceStateProvider.Listener listener) {
        synchronized (this.mLock) {
            if (this.mListener != null) {
                throw new java.lang.RuntimeException("Provider already has a listener set.");
            }
            this.mListener = listener;
        }
        notifySupportedStatesChanged(1);
        notifyDeviceStateChangedIfNeeded();
    }

    private void notifySupportedStatesChanged(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                if (this.mListener == null) {
                    return;
                }
                com.android.server.devicestate.DeviceStateProvider.Listener listener = this.mListener;
                for (android.hardware.devicestate.DeviceState deviceState : this.mOrderedStates) {
                    if ((!isThermalStatusCriticalOrAbove(this.mThermalStatus) || !deviceState.hasFlag(16)) && (!this.mPowerSaveModeEnabled || !deviceState.hasFlag(32))) {
                        arrayList.add(deviceState);
                    }
                }
                listener.onSupportedDeviceStatesChanged((android.hardware.devicestate.DeviceState[]) arrayList.toArray(new android.hardware.devicestate.DeviceState[arrayList.size()]), i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void notifyDeviceStateChangedIfNeeded() {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mListener == null) {
                    return;
                }
                int i2 = 0;
                while (true) {
                    if (i2 >= this.mOrderedStates.length) {
                        i = -1;
                        break;
                    }
                    i = this.mOrderedStates[i2].getIdentifier();
                    if (this.mStateConditions.get(i).getAsBoolean()) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i == -1) {
                    android.util.Slog.e(TAG, "No declared device states match any of the required conditions.");
                    dumpSensorValues();
                }
                if (i != -1 && i != this.mLastReportedState) {
                    this.mLastReportedState = i;
                } else {
                    i = -1;
                }
                if (i != -1) {
                    this.mListener.onStateChanged(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerInternal.LidSwitchCallback
    public void notifyLidSwitchChanged(long j, boolean z) {
        synchronized (this.mLock) {
            this.mIsLidOpen = java.lang.Boolean.valueOf(z);
        }
        notifyDeviceStateChangedIfNeeded();
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
        synchronized (this.mLock) {
            this.mLatestSensorEvent.put(sensorEvent.sensor, sensorEvent);
        }
        notifyDeviceStateChangedIfNeeded();
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
    }

    @Override // android.util.Dumpable
    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
        printWriter.println(TAG);
        synchronized (this.mLock) {
            try {
                printWriter.println("  mLastReportedState = " + this.mLastReportedState);
                printWriter.println("  mPowerSaveModeEnabled = " + this.mPowerSaveModeEnabled);
                printWriter.println("  mThermalStatus = " + this.mThermalStatus);
                printWriter.println("  mIsLidOpen = " + this.mIsLidOpen);
                printWriter.println("  Sensor values:");
                for (android.hardware.Sensor sensor : this.mLatestSensorEvent.keySet()) {
                    printWriter.println("   - " + toSensorValueString(sensor, this.mLatestSensorEvent.get(sensor)));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private final class LidSwitchBooleanSupplier implements java.util.function.BooleanSupplier {
        private final boolean mExpectedOpen;

        LidSwitchBooleanSupplier(boolean z) {
            this.mExpectedOpen = z;
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            boolean z;
            synchronized (com.android.server.policy.DeviceStateProviderImpl.this.mLock) {
                try {
                    if (com.android.server.policy.DeviceStateProviderImpl.this.mIsLidOpen == null) {
                        throw new java.lang.IllegalStateException("Have not received lid switch value.");
                    }
                    z = com.android.server.policy.DeviceStateProviderImpl.this.mIsLidOpen.booleanValue() == this.mExpectedOpen;
                } finally {
                }
            }
            return z;
        }
    }

    private final class SensorBooleanSupplier implements java.util.function.BooleanSupplier {

        @android.annotation.NonNull
        private final java.util.List<com.android.server.policy.devicestate.config.NumericRange> mExpectedValues;

        @android.annotation.NonNull
        private final android.hardware.Sensor mSensor;

        SensorBooleanSupplier(@android.annotation.NonNull android.hardware.Sensor sensor, @android.annotation.NonNull java.util.List<com.android.server.policy.devicestate.config.NumericRange> list) {
            this.mSensor = sensor;
            this.mExpectedValues = list;
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            synchronized (com.android.server.policy.DeviceStateProviderImpl.this.mLock) {
                try {
                    android.hardware.SensorEvent sensorEvent = (android.hardware.SensorEvent) com.android.server.policy.DeviceStateProviderImpl.this.mLatestSensorEvent.get(this.mSensor);
                    if (sensorEvent == null) {
                        throw new java.lang.IllegalStateException("Have not received sensor event.");
                    }
                    if (sensorEvent.values.length < this.mExpectedValues.size()) {
                        throw new java.lang.RuntimeException("Number of supplied numeric range(s) does not match the number of values in the latest sensor event for sensor: " + this.mSensor);
                    }
                    for (int i = 0; i < this.mExpectedValues.size(); i++) {
                        if (!adheresToRange(sensorEvent.values[i], this.mExpectedValues.get(i))) {
                            return false;
                        }
                    }
                    return true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private boolean adheresToRange(float f, @android.annotation.NonNull com.android.server.policy.devicestate.config.NumericRange numericRange) {
            java.math.BigDecimal min_optional = numericRange.getMin_optional();
            if (min_optional != null && f <= min_optional.floatValue()) {
                return false;
            }
            java.math.BigDecimal minInclusive_optional = numericRange.getMinInclusive_optional();
            if (minInclusive_optional != null && f < minInclusive_optional.floatValue()) {
                return false;
            }
            java.math.BigDecimal max_optional = numericRange.getMax_optional();
            if (max_optional != null && f >= max_optional.floatValue()) {
                return false;
            }
            java.math.BigDecimal maxInclusive_optional = numericRange.getMaxInclusive_optional();
            if (maxInclusive_optional != null && f > maxInclusive_optional.floatValue()) {
                return false;
            }
            return true;
        }
    }

    private static final class AndBooleanSupplier implements java.util.function.BooleanSupplier {

        @android.annotation.NonNull
        java.util.List<java.util.function.BooleanSupplier> mBooleanSuppliers;

        AndBooleanSupplier(@android.annotation.NonNull java.util.List<java.util.function.BooleanSupplier> list) {
            this.mBooleanSuppliers = list;
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            for (int i = 0; i < this.mBooleanSuppliers.size(); i++) {
                if (!this.mBooleanSuppliers.get(i).getAsBoolean()) {
                    return false;
                }
            }
            return true;
        }
    }

    @android.annotation.Nullable
    private static java.io.File getConfigurationFile() {
        java.io.File buildPath = android.os.Environment.buildPath(android.os.Environment.getDataDirectory(), new java.lang.String[]{DATA_CONFIG_FILE_PATH, CONFIG_FILE_NAME});
        if (buildPath.exists()) {
            return buildPath;
        }
        java.io.File buildPath2 = android.os.Environment.buildPath(android.os.Environment.getVendorDirectory(), new java.lang.String[]{VENDOR_CONFIG_FILE_PATH, CONFIG_FILE_NAME});
        if (buildPath2.exists()) {
            return buildPath2;
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void dumpSensorValues() {
        android.util.Slog.i(TAG, "Sensor values:");
        for (android.hardware.Sensor sensor : this.mLatestSensorEvent.keySet()) {
            android.util.Slog.i(TAG, toSensorValueString(sensor, this.mLatestSensorEvent.get(sensor)));
        }
    }

    private java.lang.String toSensorValueString(android.hardware.Sensor sensor, @android.annotation.Nullable android.hardware.SensorEvent sensorEvent) {
        return (sensor == null ? "null" : sensor.getName()) + " : " + (sensorEvent != null ? java.util.Arrays.toString(sensorEvent.values) : "null");
    }

    @android.annotation.Nullable
    private static com.android.server.policy.devicestate.config.DeviceStateConfig parseConfig(@android.annotation.NonNull com.android.server.policy.DeviceStateProviderImpl.ReadableConfig readableConfig) {
        try {
            java.io.InputStream openRead = readableConfig.openRead();
            try {
                java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(openRead);
                try {
                    com.android.server.policy.devicestate.config.DeviceStateConfig read = com.android.server.policy.devicestate.config.XmlParser.read(bufferedInputStream);
                    bufferedInputStream.close();
                    if (openRead != null) {
                        openRead.close();
                    }
                    return read;
                } finally {
                }
            } catch (java.lang.Throwable th) {
                if (openRead != null) {
                    try {
                        openRead.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException | javax.xml.datatype.DatatypeConfigurationException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Encountered an error while reading device state config", e);
            return null;
        }
    }

    private static final class ReadableFileConfig implements com.android.server.policy.DeviceStateProviderImpl.ReadableConfig {

        @android.annotation.NonNull
        private final java.io.File mFile;

        private ReadableFileConfig(@android.annotation.NonNull java.io.File file) {
            this.mFile = file;
        }

        @Override // com.android.server.policy.DeviceStateProviderImpl.ReadableConfig
        public java.io.InputStream openRead() throws java.io.IOException {
            return new java.io.FileInputStream(this.mFile);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void onPowerSaveModeChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mPowerSaveModeEnabled != z) {
                    this.mPowerSaveModeEnabled = z;
                    notifySupportedStatesChanged(z ? 4 : 5);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.PowerManager.OnThermalStatusChangedListener
    public void onThermalStatusChanged(int i) {
        int i2;
        int i3;
        synchronized (this.mLock) {
            i2 = this.mThermalStatus;
            this.mThermalStatus = i;
        }
        boolean isThermalStatusCriticalOrAbove = isThermalStatusCriticalOrAbove(i);
        if (isThermalStatusCriticalOrAbove != isThermalStatusCriticalOrAbove(i2)) {
            android.util.Slog.i(TAG, "Updating supported device states due to thermal status change. isThermalStatusCriticalOrAbove: " + isThermalStatusCriticalOrAbove);
            if (isThermalStatusCriticalOrAbove) {
                i3 = 3;
            } else {
                i3 = 2;
            }
            notifySupportedStatesChanged(i3);
        }
    }

    private static boolean isThermalStatusCriticalOrAbove(int i) {
        switch (i) {
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    private static boolean hasThermalSensitiveState(java.util.List<android.hardware.devicestate.DeviceState> list) {
        java.util.Iterator<android.hardware.devicestate.DeviceState> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().hasFlag(16)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasPowerSaveSensitiveState(java.util.List<android.hardware.devicestate.DeviceState> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).hasFlag(32)) {
                return true;
            }
        }
        return false;
    }
}
