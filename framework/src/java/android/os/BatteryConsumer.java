package android.os;

/* loaded from: classes3.dex */
public abstract class BatteryConsumer {
    static final int COLUMN_COUNT = 1;
    static final int COLUMN_INDEX_BATTERY_CONSUMER_TYPE = 0;
    public static final int FIRST_CUSTOM_POWER_COMPONENT_ID = 1000;
    public static final int LAST_CUSTOM_POWER_COMPONENT_ID = 9999;
    public static final int POWER_COMPONENT_AMBIENT_DISPLAY = 15;
    public static final int POWER_COMPONENT_ANY = -1;
    public static final int POWER_COMPONENT_AUDIO = 4;
    public static final int POWER_COMPONENT_BLUETOOTH = 2;
    public static final int POWER_COMPONENT_CAMERA = 3;
    public static final int POWER_COMPONENT_COUNT = 18;
    public static final int POWER_COMPONENT_CPU = 1;
    public static final int POWER_COMPONENT_FLASHLIGHT = 6;
    public static final int POWER_COMPONENT_GNSS = 10;
    public static final int POWER_COMPONENT_IDLE = 16;
    public static final int POWER_COMPONENT_MEMORY = 13;
    public static final int POWER_COMPONENT_MOBILE_RADIO = 8;
    public static final int POWER_COMPONENT_PHONE = 14;
    public static final int POWER_COMPONENT_REATTRIBUTED_TO_OTHER_CONSUMERS = 17;
    public static final int POWER_COMPONENT_SCREEN = 0;
    public static final int POWER_COMPONENT_SENSORS = 9;
    public static final int POWER_COMPONENT_SYSTEM_SERVICES = 7;
    public static final int POWER_COMPONENT_VIDEO = 5;
    public static final int POWER_COMPONENT_WAKELOCK = 12;
    public static final int POWER_COMPONENT_WIFI = 11;
    public static final int POWER_MODEL_ENERGY_CONSUMPTION = 2;
    public static final int POWER_MODEL_POWER_PROFILE = 1;
    public static final int POWER_MODEL_UNDEFINED = 0;
    public static final int PROCESS_STATE_ANY = 0;
    public static final int PROCESS_STATE_BACKGROUND = 2;
    public static final int PROCESS_STATE_CACHED = 4;
    public static final int PROCESS_STATE_COUNT = 5;
    public static final int PROCESS_STATE_FOREGROUND = 1;
    public static final int PROCESS_STATE_FOREGROUND_SERVICE = 3;
    public static final int PROCESS_STATE_UNSPECIFIED = 0;
    private static final int[] SUPPORTED_POWER_COMPONENTS_PER_PROCESS_STATE;
    private static final java.lang.String TAG = "BatteryConsumer";
    public static final android.os.BatteryConsumer.Dimensions UNSPECIFIED_DIMENSIONS;
    private static final java.lang.String[] sPowerComponentNames = new java.lang.String[18];
    private static final java.lang.String[] sProcessStateNames;
    protected final android.os.BatteryConsumer.BatteryConsumerData mData;
    protected final android.os.PowerComponents mPowerComponents;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PowerComponent {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PowerModel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProcessState {
    }

    public abstract void dump(java.io.PrintWriter printWriter, boolean z);

    static {
        sPowerComponentNames[0] = "screen";
        sPowerComponentNames[1] = "cpu";
        sPowerComponentNames[2] = "bluetooth";
        sPowerComponentNames[3] = android.content.Context.CAMERA_SERVICE;
        sPowerComponentNames[4] = "audio";
        sPowerComponentNames[5] = "video";
        sPowerComponentNames[6] = "flashlight";
        sPowerComponentNames[7] = "system_services";
        sPowerComponentNames[8] = "mobile_radio";
        sPowerComponentNames[9] = "sensors";
        sPowerComponentNames[10] = "gnss";
        sPowerComponentNames[11] = "wifi";
        sPowerComponentNames[12] = "wakelock";
        sPowerComponentNames[13] = "memory";
        sPowerComponentNames[14] = "phone";
        sPowerComponentNames[15] = "ambient_display";
        sPowerComponentNames[16] = "idle";
        sPowerComponentNames[17] = "reattributed";
        sProcessStateNames = new java.lang.String[5];
        sProcessStateNames[0] = "unspecified";
        sProcessStateNames[1] = "fg";
        sProcessStateNames[2] = "bg";
        sProcessStateNames[3] = "fgs";
        sProcessStateNames[4] = "cached";
        SUPPORTED_POWER_COMPONENTS_PER_PROCESS_STATE = new int[]{1, 8, 11, 2};
        UNSPECIFIED_DIMENSIONS = new android.os.BatteryConsumer.Dimensions(-1, 0);
    }

    public static final class Dimensions {
        public final int powerComponent;
        public final int processState;

        public Dimensions(int i, int i2) {
            this.powerComponent = i;
            this.processState = i2;
        }

        public java.lang.String toString() {
            boolean z;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            boolean z2 = true;
            if (this.powerComponent == -1) {
                z = false;
            } else {
                sb.append("powerComponent=").append(android.os.BatteryConsumer.sPowerComponentNames[this.powerComponent]);
                z = true;
            }
            if (this.processState == 0) {
                z2 = z;
            } else {
                if (z) {
                    sb.append(", ");
                }
                sb.append("processState=").append(android.os.BatteryConsumer.sProcessStateNames[this.processState]);
            }
            if (!z2) {
                sb.append("any components and process states");
            }
            return sb.toString();
        }
    }

    public static final class Key {
        final int mDurationColumnIndex;
        final int mPowerColumnIndex;
        final int mPowerModelColumnIndex;
        private java.lang.String mShortString;
        public final int powerComponent;
        public final int processState;

        private Key(int i, int i2, int i3, int i4, int i5) {
            this.powerComponent = i;
            this.processState = i2;
            this.mPowerModelColumnIndex = i3;
            this.mPowerColumnIndex = i4;
            this.mDurationColumnIndex = i5;
        }

        public boolean equals(java.lang.Object obj) {
            android.os.BatteryConsumer.Key key = (android.os.BatteryConsumer.Key) obj;
            return this.powerComponent == key.powerComponent && this.processState == key.processState;
        }

        public int hashCode() {
            return (this.powerComponent * 31) + this.processState;
        }

        public java.lang.String toShortString() {
            if (this.mShortString == null) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(android.os.BatteryConsumer.powerComponentIdToString(this.powerComponent));
                if (this.processState != 0) {
                    sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
                    sb.append(android.os.BatteryConsumer.processStateToString(this.processState));
                }
                this.mShortString = sb.toString();
            }
            return this.mShortString;
        }
    }

    protected BatteryConsumer(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData, android.os.PowerComponents powerComponents) {
        this.mData = batteryConsumerData;
        this.mPowerComponents = powerComponents;
    }

    public BatteryConsumer(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        this.mData = batteryConsumerData;
        this.mPowerComponents = new android.os.PowerComponents(batteryConsumerData);
    }

    public double getConsumedPower() {
        return this.mPowerComponents.getConsumedPower(UNSPECIFIED_DIMENSIONS);
    }

    public double getConsumedPower(android.os.BatteryConsumer.Dimensions dimensions) {
        return this.mPowerComponents.getConsumedPower(dimensions);
    }

    public android.os.BatteryConsumer.Key[] getKeys(int i) {
        return this.mData.getKeys(i);
    }

    public android.os.BatteryConsumer.Key getKey(int i) {
        return this.mData.getKey(i, 0);
    }

    public android.os.BatteryConsumer.Key getKey(int i, int i2) {
        return this.mData.getKey(i, i2);
    }

    public double getConsumedPower(int i) {
        return this.mPowerComponents.getConsumedPower(this.mData.getKeyOrThrow(i, 0));
    }

    public double getConsumedPower(android.os.BatteryConsumer.Key key) {
        return this.mPowerComponents.getConsumedPower(key);
    }

    public int getPowerModel(int i) {
        return this.mPowerComponents.getPowerModel(this.mData.getKeyOrThrow(i, 0));
    }

    public int getPowerModel(android.os.BatteryConsumer.Key key) {
        return this.mPowerComponents.getPowerModel(key);
    }

    public double getConsumedPowerForCustomComponent(int i) {
        return this.mPowerComponents.getConsumedPowerForCustomComponent(i);
    }

    public int getCustomPowerComponentCount() {
        return this.mData.layout.customPowerComponentCount;
    }

    public java.lang.String getCustomPowerComponentName(int i) {
        return this.mPowerComponents.getCustomPowerComponentName(i);
    }

    public long getUsageDurationMillis(int i) {
        return this.mPowerComponents.getUsageDurationMillis(getKey(i));
    }

    public long getUsageDurationMillis(android.os.BatteryConsumer.Key key) {
        return this.mPowerComponents.getUsageDurationMillis(key);
    }

    public long getUsageDurationForCustomComponentMillis(int i) {
        return this.mPowerComponents.getUsageDurationForCustomComponentMillis(i);
    }

    public static java.lang.String powerComponentIdToString(int i) {
        if (i == -1) {
            return "all";
        }
        return sPowerComponentNames[i];
    }

    public static java.lang.String powerModelToString(int i) {
        switch (i) {
            case 1:
                return "power profile";
            case 2:
                return "energy consumption";
            default:
                return "";
        }
    }

    public static int powerModelToProtoEnum(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    public static java.lang.String processStateToString(int i) {
        return sProcessStateNames[i];
    }

    public void dump(java.io.PrintWriter printWriter) {
        dump(printWriter, true);
    }

    boolean hasStatsProtoData() {
        return writeStatsProtoImpl(null, 0L);
    }

    void writeStatsProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        writeStatsProtoImpl(protoOutputStream, j);
    }

    private boolean writeStatsProtoImpl(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long convertMahToDeciCoulombs = convertMahToDeciCoulombs(getConsumedPower());
        if (convertMahToDeciCoulombs == 0) {
            return false;
        }
        if (protoOutputStream == null) {
            return true;
        }
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, convertMahToDeciCoulombs);
        this.mPowerComponents.writeStatsProto(protoOutputStream);
        protoOutputStream.end(start);
        return true;
    }

    static long convertMahToDeciCoulombs(double d) {
        return (long) ((d * 36.0d) + 0.5d);
    }

    static class BatteryConsumerData {
        public final android.os.BatteryConsumer.BatteryConsumerDataLayout layout;
        private final int mCursorRow;
        private final android.database.CursorWindow mCursorWindow;

        BatteryConsumerData(android.database.CursorWindow cursorWindow, int i, android.os.BatteryConsumer.BatteryConsumerDataLayout batteryConsumerDataLayout) {
            this.mCursorWindow = cursorWindow;
            this.mCursorRow = i;
            this.layout = batteryConsumerDataLayout;
        }

        static android.os.BatteryConsumer.BatteryConsumerData create(android.database.CursorWindow cursorWindow, android.os.BatteryConsumer.BatteryConsumerDataLayout batteryConsumerDataLayout) {
            int numRows = cursorWindow.getNumRows();
            if (!cursorWindow.allocRow()) {
                android.util.Slog.e(android.os.BatteryConsumer.TAG, "Cannot allocate BatteryConsumerData: too many UIDs: " + numRows);
                numRows = -1;
            }
            return new android.os.BatteryConsumer.BatteryConsumerData(cursorWindow, numRows, batteryConsumerDataLayout);
        }

        public android.os.BatteryConsumer.Key[] getKeys(int i) {
            return this.layout.keys[i];
        }

        android.os.BatteryConsumer.Key getKeyOrThrow(int i, int i2) {
            android.os.BatteryConsumer.Key key = getKey(i, i2);
            if (key != null) {
                return key;
            }
            if (i2 == 0) {
                throw new java.lang.IllegalArgumentException("Unsupported power component ID: " + i);
            }
            throw new java.lang.IllegalArgumentException("Unsupported power component ID: " + i + " process state: " + i2);
        }

        android.os.BatteryConsumer.Key getKey(int i, int i2) {
            if (i >= 18) {
                return null;
            }
            if (i2 == 0) {
                return this.layout.keys[i][0];
            }
            for (android.os.BatteryConsumer.Key key : this.layout.keys[i]) {
                if (key.processState == i2) {
                    return key;
                }
            }
            return null;
        }

        void putInt(int i, int i2) {
            if (this.mCursorRow == -1) {
                return;
            }
            this.mCursorWindow.putLong(i2, this.mCursorRow, i);
        }

        int getInt(int i) {
            if (this.mCursorRow == -1) {
                return 0;
            }
            return this.mCursorWindow.getInt(this.mCursorRow, i);
        }

        void putDouble(int i, double d) {
            if (this.mCursorRow == -1) {
                return;
            }
            this.mCursorWindow.putDouble(d, this.mCursorRow, i);
        }

        double getDouble(int i) {
            if (this.mCursorRow == -1) {
                return 0.0d;
            }
            return this.mCursorWindow.getDouble(this.mCursorRow, i);
        }

        void putLong(int i, long j) {
            if (this.mCursorRow == -1) {
                return;
            }
            this.mCursorWindow.putLong(j, this.mCursorRow, i);
        }

        long getLong(int i) {
            if (this.mCursorRow == -1) {
                return 0L;
            }
            return this.mCursorWindow.getLong(this.mCursorRow, i);
        }

        void putString(int i, java.lang.String str) {
            if (this.mCursorRow == -1) {
                return;
            }
            this.mCursorWindow.putString(str, this.mCursorRow, i);
        }

        java.lang.String getString(int i) {
            if (this.mCursorRow == -1) {
                return null;
            }
            return this.mCursorWindow.getString(this.mCursorRow, i);
        }
    }

    static class BatteryConsumerDataLayout {
        private static final android.os.BatteryConsumer.Key[] KEY_ARRAY = new android.os.BatteryConsumer.Key[0];
        public static final int POWER_MODEL_NOT_INCLUDED = -1;
        public final int columnCount;
        public final int customPowerComponentCount;
        public final java.lang.String[] customPowerComponentNames;
        public final int firstCustomConsumedPowerColumn;
        public final int firstCustomUsageDurationColumn;
        public final android.os.BatteryConsumer.Key[][] keys;
        public final boolean powerModelsIncluded;
        public final boolean processStateDataIncluded;
        public final android.os.BatteryConsumer.Key[][] processStateKeys;
        public final int totalConsumedPowerColumnIndex;

        private BatteryConsumerDataLayout(int i, java.lang.String[] strArr, boolean z, boolean z2) {
            int i2;
            int i3;
            boolean z3;
            int i4;
            int i5;
            this.customPowerComponentNames = strArr;
            this.customPowerComponentCount = strArr.length;
            this.powerModelsIncluded = z;
            this.processStateDataIncluded = z2;
            int i6 = i + 1;
            this.totalConsumedPowerColumnIndex = i;
            this.keys = new android.os.BatteryConsumer.Key[18][];
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int i7 = 0;
            for (int i8 = 18; i7 < i8; i8 = 18) {
                arrayList.clear();
                if (z) {
                    i3 = i6;
                    i2 = i6 + 1;
                } else {
                    i2 = i6;
                    i3 = -1;
                }
                int i9 = i2 + 1;
                i6 = i9 + 1;
                arrayList.add(new android.os.BatteryConsumer.Key(i7, 0, i3, i2, i9));
                if (z2) {
                    int[] iArr = android.os.BatteryConsumer.SUPPORTED_POWER_COMPONENTS_PER_PROCESS_STATE;
                    int length = iArr.length;
                    int i10 = 0;
                    while (true) {
                        if (i10 < length) {
                            if (iArr[i10] != i7) {
                                i10++;
                            } else {
                                z3 = true;
                                break;
                            }
                        } else {
                            z3 = false;
                            break;
                        }
                    }
                    if (z3) {
                        for (int i11 = 0; i11 < 5; i11++) {
                            if (i11 != 0) {
                                if (z) {
                                    i5 = i6;
                                    i4 = i6 + 1;
                                } else {
                                    i4 = i6;
                                    i5 = -1;
                                }
                                int i12 = i4 + 1;
                                i6 = i12 + 1;
                                arrayList.add(new android.os.BatteryConsumer.Key(i7, i11, i5, i4, i12));
                            }
                        }
                    }
                }
                this.keys[i7] = (android.os.BatteryConsumer.Key[]) arrayList.toArray(KEY_ARRAY);
                i7++;
            }
            if (z2) {
                this.processStateKeys = new android.os.BatteryConsumer.Key[5][];
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                for (int i13 = 0; i13 < 5; i13++) {
                    if (i13 != 0) {
                        arrayList2.clear();
                        for (int i14 = 0; i14 < this.keys.length; i14++) {
                            for (int i15 = 0; i15 < this.keys[i14].length; i15++) {
                                if (this.keys[i14][i15].processState == i13) {
                                    arrayList2.add(this.keys[i14][i15]);
                                }
                            }
                        }
                        this.processStateKeys[i13] = (android.os.BatteryConsumer.Key[]) arrayList2.toArray(KEY_ARRAY);
                    }
                }
            } else {
                this.processStateKeys = null;
            }
            this.firstCustomConsumedPowerColumn = i6;
            int i16 = i6 + this.customPowerComponentCount;
            this.firstCustomUsageDurationColumn = i16;
            this.columnCount = i16 + this.customPowerComponentCount;
        }
    }

    static android.os.BatteryConsumer.BatteryConsumerDataLayout createBatteryConsumerDataLayout(java.lang.String[] strArr, boolean z, boolean z2) {
        return new android.os.BatteryConsumer.BatteryConsumerDataLayout(java.lang.Math.max(java.lang.Math.max(java.lang.Math.max(1, 3), 6), 2), strArr, z, z2);
    }

    protected static abstract class BaseBuilder<T extends android.os.BatteryConsumer.BaseBuilder<?>> {
        protected final android.os.BatteryConsumer.BatteryConsumerData mData;
        protected final android.os.PowerComponents.Builder mPowerComponentsBuilder;

        public BaseBuilder(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData, int i, double d) {
            this.mData = batteryConsumerData;
            batteryConsumerData.putLong(0, i);
            this.mPowerComponentsBuilder = new android.os.PowerComponents.Builder(batteryConsumerData, d);
        }

        public android.os.BatteryConsumer.Key[] getKeys(int i) {
            return this.mData.getKeys(i);
        }

        public android.os.BatteryConsumer.Key getKey(int i, int i2) {
            return this.mData.getKey(i, i2);
        }

        public T setConsumedPower(int i, double d) {
            return setConsumedPower(i, d, 1);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setConsumedPower(int i, double d, int i2) {
            this.mPowerComponentsBuilder.setConsumedPower(getKey(i, 0), d, i2);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addConsumedPower(int i, double d, int i2) {
            this.mPowerComponentsBuilder.addConsumedPower(getKey(i, 0), d, i2);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
            this.mPowerComponentsBuilder.setConsumedPower(key, d, i);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T addConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
            this.mPowerComponentsBuilder.addConsumedPower(key, d, i);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setConsumedPowerForCustomComponent(int i, double d) {
            this.mPowerComponentsBuilder.setConsumedPowerForCustomComponent(i, d);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setUsageDurationMillis(int i, long j) {
            this.mPowerComponentsBuilder.setUsageDurationMillis(getKey(i, 0), j);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setUsageDurationMillis(android.os.BatteryConsumer.Key key, long j) {
            this.mPowerComponentsBuilder.setUsageDurationMillis(key, j);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public T setUsageDurationForCustomComponentMillis(int i, long j) {
            this.mPowerComponentsBuilder.setUsageDurationForCustomComponentMillis(i, j);
            return this;
        }

        public double getTotalPower() {
            return this.mPowerComponentsBuilder.getTotalPower();
        }
    }
}
