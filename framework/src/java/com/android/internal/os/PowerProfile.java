package com.android.internal.os;

/* loaded from: classes4.dex */
public class PowerProfile {
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String CPU_CLUSTER_POWER_COUNT = "cpu.cluster_power.cluster";
    private static final java.lang.String CPU_CORE_POWER_PREFIX = "cpu.core_power.cluster";
    private static final java.lang.String CPU_CORE_SPEED_PREFIX = "cpu.core_speeds.cluster";
    private static final java.lang.String CPU_PER_CLUSTER_CORE_COUNT = "cpu.clusters.cores";
    private static final java.lang.String CPU_POWER_BRACKETS_PREFIX = "cpu.power_brackets.policy";
    private static final java.lang.String CPU_SCALING_POLICY_POWER_POLICY = "cpu.scaling_policy_power.policy";
    private static final java.lang.String CPU_SCALING_STEP_POWER_POLICY = "cpu.scaling_step_power.policy";

    @java.lang.Deprecated
    public static final java.lang.String POWER_AMBIENT_DISPLAY = "ambient.on";
    public static final java.lang.String POWER_AUDIO = "audio";
    public static final java.lang.String POWER_BATTERY_CAPACITY = "battery.capacity";

    @java.lang.Deprecated
    public static final java.lang.String POWER_BLUETOOTH_ACTIVE = "bluetooth.active";

    @java.lang.Deprecated
    public static final java.lang.String POWER_BLUETOOTH_AT_CMD = "bluetooth.at";
    public static final java.lang.String POWER_BLUETOOTH_CONTROLLER_IDLE = "bluetooth.controller.idle";
    public static final java.lang.String POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE = "bluetooth.controller.voltage";
    public static final java.lang.String POWER_BLUETOOTH_CONTROLLER_RX = "bluetooth.controller.rx";
    public static final java.lang.String POWER_BLUETOOTH_CONTROLLER_TX = "bluetooth.controller.tx";

    @java.lang.Deprecated
    public static final java.lang.String POWER_BLUETOOTH_ON = "bluetooth.on";
    public static final int POWER_BRACKETS_UNSPECIFIED = -1;
    public static final java.lang.String POWER_CAMERA = "camera.avg";
    public static final java.lang.String POWER_CPU_ACTIVE = "cpu.active";
    public static final java.lang.String POWER_CPU_IDLE = "cpu.idle";
    public static final java.lang.String POWER_CPU_SUSPEND = "cpu.suspend";
    public static final java.lang.String POWER_FLASHLIGHT = "camera.flashlight";
    public static final java.lang.String POWER_GPS_ON = "gps.on";
    public static final java.lang.String POWER_GPS_OPERATING_VOLTAGE = "gps.voltage";
    public static final java.lang.String POWER_GPS_SIGNAL_QUALITY_BASED = "gps.signalqualitybased";
    public static final java.lang.String POWER_GROUP_DISPLAY_AMBIENT = "ambient.on.display";
    public static final java.lang.String POWER_GROUP_DISPLAY_SCREEN_FULL = "screen.full.display";
    public static final java.lang.String POWER_GROUP_DISPLAY_SCREEN_ON = "screen.on.display";
    public static final java.lang.String POWER_MEMORY = "memory.bandwidths";
    public static final java.lang.String POWER_MODEM_CONTROLLER_IDLE = "modem.controller.idle";
    public static final java.lang.String POWER_MODEM_CONTROLLER_OPERATING_VOLTAGE = "modem.controller.voltage";
    public static final java.lang.String POWER_MODEM_CONTROLLER_RX = "modem.controller.rx";
    public static final java.lang.String POWER_MODEM_CONTROLLER_SLEEP = "modem.controller.sleep";
    public static final java.lang.String POWER_MODEM_CONTROLLER_TX = "modem.controller.tx";
    public static final java.lang.String POWER_RADIO_ACTIVE = "radio.active";
    public static final java.lang.String POWER_RADIO_ON = "radio.on";
    public static final java.lang.String POWER_RADIO_SCANNING = "radio.scanning";

    @java.lang.Deprecated
    public static final java.lang.String POWER_SCREEN_FULL = "screen.full";

    @java.lang.Deprecated
    public static final java.lang.String POWER_SCREEN_ON = "screen.on";
    public static final java.lang.String POWER_VIDEO = "video";
    public static final java.lang.String POWER_WIFI_ACTIVE = "wifi.active";
    public static final java.lang.String POWER_WIFI_BATCHED_SCAN = "wifi.batchedscan";
    public static final java.lang.String POWER_WIFI_CONTROLLER_IDLE = "wifi.controller.idle";
    public static final java.lang.String POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE = "wifi.controller.voltage";
    public static final java.lang.String POWER_WIFI_CONTROLLER_RX = "wifi.controller.rx";
    public static final java.lang.String POWER_WIFI_CONTROLLER_TX = "wifi.controller.tx";
    public static final java.lang.String POWER_WIFI_CONTROLLER_TX_LEVELS = "wifi.controller.tx_levels";
    public static final java.lang.String POWER_WIFI_ON = "wifi.on";
    public static final java.lang.String POWER_WIFI_SCAN = "wifi.scan";
    private static final long SUBSYSTEM_FIELDS_MASK = -1;
    private static final long SUBSYSTEM_MASK = 64424509440L;
    public static final long SUBSYSTEM_MODEM = 4294967296L;
    public static final long SUBSYSTEM_NONE = 0;
    public static final java.lang.String TAG = "PowerProfile";
    private static final java.lang.String TAG_ARRAY = "array";
    private static final java.lang.String TAG_ARRAYITEM = "value";
    private static final java.lang.String TAG_DEVICE = "device";
    private static final java.lang.String TAG_ITEM = "item";
    private static final java.lang.String TAG_MODEM = "modem";
    private com.android.internal.os.PowerProfile.CpuClusterKey[] mCpuClusters;
    private int mCpuPowerBracketCount;
    private android.util.SparseArray<com.android.internal.os.PowerProfile.CpuScalingPolicyPower> mCpuScalingPolicies;
    private int mNumDisplays;
    static final java.util.HashMap<java.lang.String, java.lang.Double> sPowerItemMap = new java.util.HashMap<>();
    static final java.util.HashMap<java.lang.String, java.lang.Double[]> sPowerArrayMap = new java.util.HashMap<>();
    static final com.android.internal.power.ModemPowerProfile sModemPowerProfile = new com.android.internal.power.ModemPowerProfile();
    private static final java.lang.Object sLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PowerGroup {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Subsystem {
    }

    public PowerProfile() {
        synchronized (sLock) {
            initLocked();
        }
    }

    public PowerProfile(android.content.Context context) {
        this(context, false);
    }

    public PowerProfile(android.content.Context context, boolean z) {
        synchronized (sLock) {
            initLocked(context, z ? com.android.internal.R.xml.power_profile_test : com.android.internal.R.xml.power_profile);
        }
    }

    public void forceInitForTesting(android.content.Context context, int i) {
        synchronized (sLock) {
            sPowerItemMap.clear();
            sPowerArrayMap.clear();
            sModemPowerProfile.clear();
            initLocked(context, i);
        }
    }

    private void initLocked(android.content.Context context, int i) {
        if (sPowerItemMap.size() == 0 && sPowerArrayMap.size() == 0) {
            readPowerValuesFromXml(context, i);
        }
        initLocked();
    }

    private void initLocked() {
        initCpuClusters();
        initCpuScalingPolicies();
        initCpuPowerBrackets();
        initDisplays();
        initModem();
    }

    private void readPowerValuesFromXml(android.content.Context context, int i) {
        int integer;
        android.content.res.Resources resources = context.getResources();
        android.content.res.XmlResourceParser xml = resources.getXml(i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            try {
                com.android.internal.util.XmlUtils.beginDocument(xml, "device");
                boolean z = false;
                java.lang.String str = null;
                while (true) {
                    com.android.internal.util.XmlUtils.nextElement(xml);
                    java.lang.String name = xml.getName();
                    double d = 0.0d;
                    if (name == null) {
                        break;
                    }
                    if (z && !name.equals("value")) {
                        sPowerArrayMap.put(str, (java.lang.Double[]) arrayList.toArray(new java.lang.Double[arrayList.size()]));
                        z = false;
                    }
                    if (name.equals(TAG_ARRAY)) {
                        arrayList.clear();
                        str = xml.getAttributeValue(null, "name");
                        z = true;
                    } else {
                        if (!name.equals("item") && !name.equals("value")) {
                            if (name.equals("modem")) {
                                sModemPowerProfile.parseFromXml(xml);
                            }
                        }
                        java.lang.String attributeValue = !z ? xml.getAttributeValue(null, "name") : null;
                        if (xml.next() == 4) {
                            try {
                                d = java.lang.Double.valueOf(xml.getText()).doubleValue();
                            } catch (java.lang.NumberFormatException e) {
                            }
                            if (name.equals("item")) {
                                sPowerItemMap.put(attributeValue, java.lang.Double.valueOf(d));
                            } else if (z) {
                                arrayList.add(java.lang.Double.valueOf(d));
                            }
                        }
                    }
                }
                if (z) {
                    sPowerArrayMap.put(str, (java.lang.Double[]) arrayList.toArray(new java.lang.Double[arrayList.size()]));
                }
                xml.close();
                int[] iArr = {com.android.internal.R.integer.config_bluetooth_idle_cur_ma, com.android.internal.R.integer.config_bluetooth_rx_cur_ma, com.android.internal.R.integer.config_bluetooth_tx_cur_ma, com.android.internal.R.integer.config_bluetooth_operating_voltage_mv};
                java.lang.String[] strArr = {POWER_BLUETOOTH_CONTROLLER_IDLE, POWER_BLUETOOTH_CONTROLLER_RX, POWER_BLUETOOTH_CONTROLLER_TX, POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE};
                for (int i2 = 0; i2 < 4; i2++) {
                    java.lang.String str2 = strArr[i2];
                    if ((!sPowerItemMap.containsKey(str2) || sPowerItemMap.get(str2).doubleValue() <= 0.0d) && (integer = resources.getInteger(iArr[i2])) > 0) {
                        sPowerItemMap.put(str2, java.lang.Double.valueOf(integer));
                    }
                }
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException(e2);
            } catch (org.xmlpull.v1.XmlPullParserException e3) {
                throw new java.lang.RuntimeException(e3);
            }
        } catch (java.lang.Throwable th) {
            xml.close();
            throw th;
        }
    }

    private void initCpuClusters() {
        int i;
        if (sPowerArrayMap.containsKey(CPU_PER_CLUSTER_CORE_COUNT)) {
            java.lang.Double[] dArr = sPowerArrayMap.get(CPU_PER_CLUSTER_CORE_COUNT);
            this.mCpuClusters = new com.android.internal.os.PowerProfile.CpuClusterKey[dArr.length];
            for (int i2 = 0; i2 < dArr.length; i2++) {
                this.mCpuClusters[i2] = new com.android.internal.os.PowerProfile.CpuClusterKey(CPU_CORE_SPEED_PREFIX + i2, CPU_CLUSTER_POWER_COUNT + i2, CPU_CORE_POWER_PREFIX + i2, (int) java.lang.Math.round(dArr[i2].doubleValue()));
            }
            return;
        }
        this.mCpuClusters = new com.android.internal.os.PowerProfile.CpuClusterKey[1];
        if (!sPowerItemMap.containsKey(CPU_PER_CLUSTER_CORE_COUNT)) {
            i = 1;
        } else {
            i = (int) java.lang.Math.round(sPowerItemMap.get(CPU_PER_CLUSTER_CORE_COUNT).doubleValue());
        }
        this.mCpuClusters[0] = new com.android.internal.os.PowerProfile.CpuClusterKey("cpu.core_speeds.cluster0", "cpu.cluster_power.cluster0", "cpu.core_power.cluster0", i);
    }

    private void initCpuScalingPolicies() {
        double[] dArr;
        double[] dArr2;
        int i = 0;
        for (java.lang.String str : sPowerItemMap.keySet()) {
            if (str.startsWith(CPU_SCALING_POLICY_POWER_POLICY)) {
                i = java.lang.Math.max(i, java.lang.Integer.parseInt(str.substring(CPU_SCALING_POLICY_POWER_POLICY.length())) + 1);
            }
        }
        for (java.lang.String str2 : sPowerArrayMap.keySet()) {
            if (str2.startsWith(CPU_SCALING_STEP_POWER_POLICY)) {
                i = java.lang.Math.max(i, java.lang.Integer.parseInt(str2.substring(CPU_SCALING_STEP_POWER_POLICY.length())) + 1);
            }
        }
        if (i > 0) {
            this.mCpuScalingPolicies = new android.util.SparseArray<>(i);
            for (int i2 = 0; i2 < i; i2++) {
                java.lang.Double d = sPowerItemMap.get(CPU_SCALING_POLICY_POWER_POLICY + i2);
                java.lang.Double[] dArr3 = sPowerArrayMap.get(CPU_SCALING_STEP_POWER_POLICY + i2);
                if (d != null || dArr3 != null) {
                    if (dArr3 != null) {
                        dArr2 = new double[dArr3.length];
                        for (int i3 = 0; i3 < dArr3.length; i3++) {
                            dArr2[i3] = dArr3[i3].doubleValue();
                        }
                    } else {
                        dArr2 = new double[0];
                    }
                    this.mCpuScalingPolicies.put(i2, new com.android.internal.os.PowerProfile.CpuScalingPolicyPower(d != null ? d.doubleValue() : 0.0d, dArr2));
                }
            }
            return;
        }
        com.android.internal.os.PowerProfile.CpuClusterKey[] cpuClusterKeyArr = this.mCpuClusters;
        int length = cpuClusterKeyArr.length;
        int i4 = 0;
        int i5 = 0;
        while (i4 < length) {
            int i6 = i5 + 1;
            i5 += cpuClusterKeyArr[i4].numCpus;
            i4++;
            i = i6;
        }
        if (i > 0) {
            this.mCpuScalingPolicies = new android.util.SparseArray<>(i);
            int i7 = 0;
            for (com.android.internal.os.PowerProfile.CpuClusterKey cpuClusterKey : this.mCpuClusters) {
                double averagePower = getAveragePower(cpuClusterKey.clusterPowerKey);
                int numElements = getNumElements(cpuClusterKey.corePowerKey);
                if (numElements != 0) {
                    dArr = new double[numElements];
                    for (int i8 = 0; i8 < numElements; i8++) {
                        dArr[i8] = getAveragePower(cpuClusterKey.corePowerKey, i8);
                    }
                } else {
                    dArr = new double[1];
                }
                this.mCpuScalingPolicies.put(i7, new com.android.internal.os.PowerProfile.CpuScalingPolicyPower(averagePower, dArr));
                i7 += cpuClusterKey.numCpus;
            }
            return;
        }
        this.mCpuScalingPolicies = new android.util.SparseArray<>(1);
        this.mCpuScalingPolicies.put(0, new com.android.internal.os.PowerProfile.CpuScalingPolicyPower(getAveragePower(POWER_CPU_ACTIVE), new double[]{0.0d}));
    }

    private void initCpuPowerBrackets() {
        boolean z = true;
        boolean z2 = false;
        for (int size = this.mCpuScalingPolicies.size() - 1; size >= 0; size--) {
            int keyAt = this.mCpuScalingPolicies.keyAt(size);
            com.android.internal.os.PowerProfile.CpuScalingPolicyPower valueAt = this.mCpuScalingPolicies.valueAt(size);
            valueAt.powerBrackets = new int[valueAt.stepPower.length];
            if (sPowerArrayMap.get(CPU_POWER_BRACKETS_PREFIX + keyAt) != null) {
                z2 = true;
            } else {
                z = false;
            }
        }
        if (z2 && !z) {
            throw new java.lang.RuntimeException("Power brackets should be specified for all scaling policies or none");
        }
        if (!z) {
            this.mCpuPowerBracketCount = -1;
            return;
        }
        this.mCpuPowerBracketCount = 0;
        for (int size2 = this.mCpuScalingPolicies.size() - 1; size2 >= 0; size2--) {
            int keyAt2 = this.mCpuScalingPolicies.keyAt(size2);
            com.android.internal.os.PowerProfile.CpuScalingPolicyPower valueAt2 = this.mCpuScalingPolicies.valueAt(size2);
            java.lang.Double[] dArr = sPowerArrayMap.get(CPU_POWER_BRACKETS_PREFIX + keyAt2);
            if (dArr.length != valueAt2.powerBrackets.length) {
                throw new java.lang.RuntimeException("Wrong number of items in cpu.power_brackets.policy" + keyAt2 + ", expected: " + valueAt2.powerBrackets.length);
            }
            for (int i = 0; i < dArr.length; i++) {
                int round = (int) java.lang.Math.round(dArr[i].doubleValue());
                valueAt2.powerBrackets[i] = round;
                if (round > this.mCpuPowerBracketCount) {
                    this.mCpuPowerBracketCount = round;
                }
            }
        }
        this.mCpuPowerBracketCount++;
    }

    private static class CpuScalingPolicyPower {
        public final double policyPower;
        public int[] powerBrackets;
        public final double[] stepPower;

        private CpuScalingPolicyPower(double d, double[] dArr) {
            this.policyPower = d;
            this.stepPower = dArr;
        }
    }

    public double getAveragePowerForCpuScalingPolicy(int i) {
        com.android.internal.os.PowerProfile.CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(i);
        if (cpuScalingPolicyPower != null) {
            return cpuScalingPolicyPower.policyPower;
        }
        return 0.0d;
    }

    public double getAveragePowerForCpuScalingStep(int i, int i2) {
        com.android.internal.os.PowerProfile.CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(i);
        if (cpuScalingPolicyPower != null && i2 >= 0 && i2 < cpuScalingPolicyPower.stepPower.length) {
            return cpuScalingPolicyPower.stepPower[i2];
        }
        return 0.0d;
    }

    private static class CpuClusterKey {
        public final java.lang.String clusterPowerKey;
        public final java.lang.String corePowerKey;
        public final java.lang.String freqKey;
        public final int numCpus;

        private CpuClusterKey(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
            this.freqKey = str;
            this.clusterPowerKey = str2;
            this.corePowerKey = str3;
            this.numCpus = i;
        }
    }

    @java.lang.Deprecated
    public int getNumCpuClusters() {
        return this.mCpuClusters.length;
    }

    @java.lang.Deprecated
    public int getNumCoresInCpuCluster(int i) {
        if (i < 0 || i >= this.mCpuClusters.length) {
            return 0;
        }
        return this.mCpuClusters[i].numCpus;
    }

    @java.lang.Deprecated
    public int getNumSpeedStepsInCpuCluster(int i) {
        if (i < 0 || i >= this.mCpuClusters.length) {
            return 0;
        }
        if (sPowerArrayMap.containsKey(this.mCpuClusters[i].freqKey)) {
            return sPowerArrayMap.get(this.mCpuClusters[i].freqKey).length;
        }
        return 1;
    }

    @java.lang.Deprecated
    public double getAveragePowerForCpuCluster(int i) {
        if (i >= 0 && i < this.mCpuClusters.length) {
            return getAveragePower(this.mCpuClusters[i].clusterPowerKey);
        }
        return 0.0d;
    }

    @java.lang.Deprecated
    public double getAveragePowerForCpuCore(int i, int i2) {
        if (i >= 0 && i < this.mCpuClusters.length) {
            return getAveragePower(this.mCpuClusters[i].corePowerKey, i2);
        }
        return 0.0d;
    }

    public int getCpuPowerBracketCount() {
        return this.mCpuPowerBracketCount;
    }

    public int getCpuPowerBracketForScalingStep(int i, int i2) {
        com.android.internal.os.PowerProfile.CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(i);
        if (cpuScalingPolicyPower != null && i2 >= 0 && i2 < cpuScalingPolicyPower.powerBrackets.length) {
            return cpuScalingPolicyPower.powerBrackets[i2];
        }
        return 0;
    }

    private void initDisplays() {
        boolean z;
        this.mNumDisplays = 0;
        while (true) {
            if (java.lang.Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_AMBIENT, this.mNumDisplays, Double.NaN)) && java.lang.Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_SCREEN_ON, this.mNumDisplays, Double.NaN)) && java.lang.Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_SCREEN_FULL, this.mNumDisplays, Double.NaN))) {
                break;
            } else {
                this.mNumDisplays++;
            }
        }
        java.lang.Double d = sPowerItemMap.get(POWER_AMBIENT_DISPLAY);
        if (d != null && this.mNumDisplays == 0) {
            java.lang.String ordinalPowerType = getOrdinalPowerType(POWER_GROUP_DISPLAY_AMBIENT, 0);
            android.util.Slog.w(TAG, "ambient.on is deprecated! Use " + ordinalPowerType + " instead.");
            sPowerItemMap.put(ordinalPowerType, d);
            z = true;
        } else {
            z = false;
        }
        java.lang.Double d2 = sPowerItemMap.get(POWER_SCREEN_ON);
        if (d2 != null && this.mNumDisplays == 0) {
            java.lang.String ordinalPowerType2 = getOrdinalPowerType(POWER_GROUP_DISPLAY_SCREEN_ON, 0);
            android.util.Slog.w(TAG, "screen.on is deprecated! Use " + ordinalPowerType2 + " instead.");
            sPowerItemMap.put(ordinalPowerType2, d2);
            z = true;
        }
        java.lang.Double d3 = sPowerItemMap.get(POWER_SCREEN_FULL);
        if (d3 != null && this.mNumDisplays == 0) {
            java.lang.String ordinalPowerType3 = getOrdinalPowerType(POWER_GROUP_DISPLAY_SCREEN_FULL, 0);
            android.util.Slog.w(TAG, "screen.full is deprecated! Use " + ordinalPowerType3 + " instead.");
            sPowerItemMap.put(ordinalPowerType3, d3);
            z = true;
        }
        if (z) {
            this.mNumDisplays = 1;
        }
    }

    public int getNumDisplays() {
        return this.mNumDisplays;
    }

    private void initModem() {
        handleDeprecatedModemConstant(0, POWER_MODEM_CONTROLLER_SLEEP, 0);
        handleDeprecatedModemConstant(268435456, POWER_MODEM_CONTROLLER_IDLE, 0);
        handleDeprecatedModemConstant(536870912, POWER_MODEM_CONTROLLER_RX, 0);
        handleDeprecatedModemConstant(805306368, POWER_MODEM_CONTROLLER_TX, 0);
        handleDeprecatedModemConstant(android.media.audio.Enums.AUDIO_FORMAT_APTX_R4, POWER_MODEM_CONTROLLER_TX, 1);
        handleDeprecatedModemConstant(android.media.audio.Enums.AUDIO_FORMAT_DTS_HD_MA, POWER_MODEM_CONTROLLER_TX, 2);
        handleDeprecatedModemConstant(android.media.audio.Enums.AUDIO_FORMAT_DTS_UHD_P2, POWER_MODEM_CONTROLLER_TX, 3);
        handleDeprecatedModemConstant(872415232, POWER_MODEM_CONTROLLER_TX, 4);
    }

    private void handleDeprecatedModemConstant(int i, java.lang.String str, int i2) {
        if (java.lang.Double.isNaN(sModemPowerProfile.getAverageBatteryDrainMa(i))) {
            sModemPowerProfile.setPowerConstant(i, java.lang.Double.toString(getAveragePower(str, i2)));
        }
    }

    public int getNumElements(java.lang.String str) {
        if (sPowerItemMap.containsKey(str)) {
            return 1;
        }
        if (sPowerArrayMap.containsKey(str)) {
            return sPowerArrayMap.get(str).length;
        }
        return 0;
    }

    public double getAveragePowerOrDefault(java.lang.String str, double d) {
        if (sPowerItemMap.containsKey(str)) {
            return sPowerItemMap.get(str).doubleValue();
        }
        if (sPowerArrayMap.containsKey(str)) {
            return sPowerArrayMap.get(str)[0].doubleValue();
        }
        return d;
    }

    public double getAveragePower(java.lang.String str) {
        return getAveragePowerOrDefault(str, 0.0d);
    }

    public double getAverageBatteryDrainOrDefaultMa(long j, double d) {
        double d2;
        long j2 = 64424509440L & j;
        int i = (int) (j & (-1));
        if (j2 == 4294967296L) {
            d2 = sModemPowerProfile.getAverageBatteryDrainMa(i);
        } else {
            d2 = Double.NaN;
        }
        return java.lang.Double.isNaN(d2) ? d : d2;
    }

    public double getAverageBatteryDrainMa(long j) {
        return getAverageBatteryDrainOrDefaultMa(j, 0.0d);
    }

    public double getAveragePower(java.lang.String str, int i) {
        if (sPowerItemMap.containsKey(str)) {
            return sPowerItemMap.get(str).doubleValue();
        }
        if (!sPowerArrayMap.containsKey(str)) {
            return 0.0d;
        }
        java.lang.Double[] dArr = sPowerArrayMap.get(str);
        if (dArr.length > i && i >= 0) {
            return dArr[i].doubleValue();
        }
        if (i < 0 || dArr.length == 0) {
            return 0.0d;
        }
        return dArr[dArr.length - 1].doubleValue();
    }

    public double getAveragePowerForOrdinal(java.lang.String str, int i, double d) {
        return getAveragePowerOrDefault(getOrdinalPowerType(str, i), d);
    }

    public double getAveragePowerForOrdinal(java.lang.String str, int i) {
        return getAveragePowerForOrdinal(str, i, 0.0d);
    }

    public double getBatteryCapacity() {
        return getAveragePower(POWER_BATTERY_CAPACITY);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
        writePowerConstantToProto(protoOutputStream, POWER_CPU_SUSPEND, 1103806595073L);
        writePowerConstantToProto(protoOutputStream, POWER_CPU_IDLE, 1103806595074L);
        writePowerConstantToProto(protoOutputStream, POWER_CPU_ACTIVE, 1103806595075L);
        for (int i = 0; i < this.mCpuClusters.length; i++) {
            long start = protoOutputStream.start(2246267895848L);
            protoOutputStream.write(1120986464257L, i);
            protoOutputStream.write(1103806595074L, sPowerItemMap.get(this.mCpuClusters[i].clusterPowerKey).doubleValue());
            protoOutputStream.write(1120986464259L, this.mCpuClusters[i].numCpus);
            for (java.lang.Double d : sPowerArrayMap.get(this.mCpuClusters[i].freqKey)) {
                protoOutputStream.write(com.android.internal.os.PowerProfileProto.CpuCluster.SPEED, d.doubleValue());
            }
            for (java.lang.Double d2 : sPowerArrayMap.get(this.mCpuClusters[i].corePowerKey)) {
                protoOutputStream.write(com.android.internal.os.PowerProfileProto.CpuCluster.CORE_POWER, d2.doubleValue());
            }
            protoOutputStream.end(start);
        }
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_SCAN, 1103806595076L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_ON, 1103806595077L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_ACTIVE, 1103806595078L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_IDLE, 1103806595079L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_RX, 1103806595080L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_TX, 1103806595081L);
        writePowerConstantArrayToProto(protoOutputStream, POWER_WIFI_CONTROLLER_TX_LEVELS, com.android.internal.os.PowerProfileProto.WIFI_CONTROLLER_TX_LEVELS);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE, com.android.internal.os.PowerProfileProto.WIFI_CONTROLLER_OPERATING_VOLTAGE);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_IDLE, com.android.internal.os.PowerProfileProto.BLUETOOTH_CONTROLLER_IDLE);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_RX, com.android.internal.os.PowerProfileProto.BLUETOOTH_CONTROLLER_RX);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_TX, com.android.internal.os.PowerProfileProto.BLUETOOTH_CONTROLLER_TX);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE, com.android.internal.os.PowerProfileProto.BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE);
        writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_SLEEP, com.android.internal.os.PowerProfileProto.MODEM_CONTROLLER_SLEEP);
        writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_IDLE, com.android.internal.os.PowerProfileProto.MODEM_CONTROLLER_IDLE);
        writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_RX, 1103806595090L);
        writePowerConstantArrayToProto(protoOutputStream, POWER_MODEM_CONTROLLER_TX, com.android.internal.os.PowerProfileProto.MODEM_CONTROLLER_TX);
        writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_OPERATING_VOLTAGE, 1103806595092L);
        writePowerConstantToProto(protoOutputStream, POWER_GPS_ON, 1103806595093L);
        writePowerConstantArrayToProto(protoOutputStream, POWER_GPS_SIGNAL_QUALITY_BASED, com.android.internal.os.PowerProfileProto.GPS_SIGNAL_QUALITY_BASED);
        writePowerConstantToProto(protoOutputStream, POWER_GPS_OPERATING_VOLTAGE, 1103806595095L);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_ON, 1103806595096L);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_ACTIVE, 1103806595097L);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_AT_CMD, 1103806595098L);
        writePowerConstantToProto(protoOutputStream, POWER_AMBIENT_DISPLAY, com.android.internal.os.PowerProfileProto.AMBIENT_DISPLAY);
        writePowerConstantToProto(protoOutputStream, POWER_SCREEN_ON, com.android.internal.os.PowerProfileProto.SCREEN_ON);
        writePowerConstantToProto(protoOutputStream, POWER_RADIO_ON, com.android.internal.os.PowerProfileProto.RADIO_ON);
        writePowerConstantToProto(protoOutputStream, POWER_RADIO_SCANNING, com.android.internal.os.PowerProfileProto.RADIO_SCANNING);
        writePowerConstantToProto(protoOutputStream, POWER_RADIO_ACTIVE, com.android.internal.os.PowerProfileProto.RADIO_ACTIVE);
        writePowerConstantToProto(protoOutputStream, POWER_SCREEN_FULL, com.android.internal.os.PowerProfileProto.SCREEN_FULL);
        writePowerConstantToProto(protoOutputStream, "audio", com.android.internal.os.PowerProfileProto.AUDIO);
        writePowerConstantToProto(protoOutputStream, "video", com.android.internal.os.PowerProfileProto.VIDEO);
        writePowerConstantToProto(protoOutputStream, POWER_FLASHLIGHT, 1103806595107L);
        writePowerConstantToProto(protoOutputStream, POWER_MEMORY, 1103806595108L);
        writePowerConstantToProto(protoOutputStream, POWER_CAMERA, 1103806595109L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_BATCHED_SCAN, com.android.internal.os.PowerProfileProto.WIFI_BATCHED_SCAN);
        writePowerConstantToProto(protoOutputStream, POWER_BATTERY_CAPACITY, com.android.internal.os.PowerProfileProto.BATTERY_CAPACITY);
    }

    public void dump(java.io.PrintWriter printWriter) {
        final android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        sPowerItemMap.forEach(new java.util.function.BiConsumer() { // from class: com.android.internal.os.PowerProfile$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.internal.os.PowerProfile.lambda$dump$0(android.util.IndentingPrintWriter.this, (java.lang.String) obj, (java.lang.Double) obj2);
            }
        });
        sPowerArrayMap.forEach(new java.util.function.BiConsumer() { // from class: com.android.internal.os.PowerProfile$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.internal.os.PowerProfile.lambda$dump$1(android.util.IndentingPrintWriter.this, (java.lang.String) obj, (java.lang.Double[]) obj2);
            }
        });
        indentingPrintWriter.println("Modem values:");
        indentingPrintWriter.increaseIndent();
        sModemPowerProfile.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    static /* synthetic */ void lambda$dump$0(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.Double d) {
        indentingPrintWriter.print(str, d);
        indentingPrintWriter.println();
    }

    static /* synthetic */ void lambda$dump$1(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str, java.lang.Double[] dArr) {
        indentingPrintWriter.print(str, java.util.Arrays.toString(dArr));
        indentingPrintWriter.println();
    }

    private void writePowerConstantToProto(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String str, long j) {
        if (sPowerItemMap.containsKey(str)) {
            protoOutputStream.write(j, sPowerItemMap.get(str).doubleValue());
        }
    }

    private void writePowerConstantArrayToProto(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String str, long j) {
        if (sPowerArrayMap.containsKey(str)) {
            for (java.lang.Double d : sPowerArrayMap.get(str)) {
                protoOutputStream.write(j, d.doubleValue());
            }
        }
    }

    private static java.lang.String getOrdinalPowerType(java.lang.String str, int i) {
        return str + i;
    }
}
