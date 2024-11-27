package com.android.internal.power;

/* loaded from: classes5.dex */
public class ModemPowerProfile {
    private static final java.lang.String ATTR_LEVEL = "level";
    private static final java.lang.String ATTR_NR_FREQUENCY = "nrFrequency";
    private static final java.lang.String ATTR_RAT = "rat";
    public static final int MODEM_DRAIN_TYPE_IDLE = 268435456;
    private static final int MODEM_DRAIN_TYPE_MASK = -268435456;
    private static final android.util.SparseArray<java.lang.String> MODEM_DRAIN_TYPE_NAMES = new android.util.SparseArray<>(4);
    public static final int MODEM_DRAIN_TYPE_RX = 536870912;
    public static final int MODEM_DRAIN_TYPE_SLEEP = 0;
    public static final int MODEM_DRAIN_TYPE_TX = 805306368;
    public static final int MODEM_NR_FREQUENCY_RANGE_DEFAULT = 0;
    public static final int MODEM_NR_FREQUENCY_RANGE_HIGH = 196608;
    public static final int MODEM_NR_FREQUENCY_RANGE_LOW = 65536;
    private static final int MODEM_NR_FREQUENCY_RANGE_MASK = 983040;
    public static final int MODEM_NR_FREQUENCY_RANGE_MID = 131072;
    public static final int MODEM_NR_FREQUENCY_RANGE_MMWAVE = 262144;
    private static final android.util.SparseArray<java.lang.String> MODEM_NR_FREQUENCY_RANGE_NAMES;
    public static final int MODEM_RAT_TYPE_DEFAULT = 0;
    public static final int MODEM_RAT_TYPE_LTE = 1048576;
    private static final int MODEM_RAT_TYPE_MASK = 15728640;
    private static final android.util.SparseArray<java.lang.String> MODEM_RAT_TYPE_NAMES;
    public static final int MODEM_RAT_TYPE_NR = 2097152;
    public static final int MODEM_TX_LEVEL_0 = 0;
    public static final int MODEM_TX_LEVEL_1 = 16777216;
    public static final int MODEM_TX_LEVEL_2 = 33554432;
    public static final int MODEM_TX_LEVEL_3 = 50331648;
    public static final int MODEM_TX_LEVEL_4 = 67108864;
    private static final int MODEM_TX_LEVEL_COUNT = 5;
    private static final int[] MODEM_TX_LEVEL_MAP;
    private static final int MODEM_TX_LEVEL_MASK = 251658240;
    private static final android.util.SparseArray<java.lang.String> MODEM_TX_LEVEL_NAMES;
    private static final java.lang.String TAG = "ModemPowerProfile";
    private static final java.lang.String TAG_ACTIVE = "active";
    private static final java.lang.String TAG_IDLE = "idle";
    private static final java.lang.String TAG_RECEIVE = "receive";
    private static final java.lang.String TAG_SLEEP = "sleep";
    private static final java.lang.String TAG_TRANSMIT = "transmit";
    private final android.util.SparseDoubleArray mPowerConstants = new android.util.SparseDoubleArray();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModemDrainType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModemNrFrequencyRange {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModemRatType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ModemTxLevel {
    }

    static {
        MODEM_DRAIN_TYPE_NAMES.put(0, "SLEEP");
        MODEM_DRAIN_TYPE_NAMES.put(268435456, "IDLE");
        MODEM_DRAIN_TYPE_NAMES.put(536870912, "RX");
        MODEM_DRAIN_TYPE_NAMES.put(805306368, "TX");
        MODEM_TX_LEVEL_NAMES = new android.util.SparseArray<>(5);
        MODEM_TX_LEVEL_NAMES.put(0, android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
        MODEM_TX_LEVEL_NAMES.put(16777216, "1");
        MODEM_TX_LEVEL_NAMES.put(33554432, "2");
        MODEM_TX_LEVEL_NAMES.put(50331648, "3");
        MODEM_TX_LEVEL_NAMES.put(67108864, "4");
        MODEM_TX_LEVEL_MAP = new int[]{0, 16777216, 33554432, 50331648, 67108864};
        MODEM_RAT_TYPE_NAMES = new android.util.SparseArray<>(3);
        MODEM_RAT_TYPE_NAMES.put(0, "DEFAULT");
        MODEM_RAT_TYPE_NAMES.put(1048576, com.android.internal.telephony.DctConstants.RAT_NAME_LTE);
        MODEM_RAT_TYPE_NAMES.put(2097152, "NR");
        MODEM_NR_FREQUENCY_RANGE_NAMES = new android.util.SparseArray<>(5);
        MODEM_NR_FREQUENCY_RANGE_NAMES.put(0, "DEFAULT");
        MODEM_NR_FREQUENCY_RANGE_NAMES.put(65536, "LOW");
        MODEM_NR_FREQUENCY_RANGE_NAMES.put(131072, "MID");
        MODEM_NR_FREQUENCY_RANGE_NAMES.put(196608, "HIGH");
        MODEM_NR_FREQUENCY_RANGE_NAMES.put(262144, "MMWAVE");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void parseFromXml(android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        int depth = xmlResourceParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            java.lang.String name = xmlResourceParser.getName();
            switch (name.hashCode()) {
                case -1422950650:
                    if (name.equals(TAG_ACTIVE)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3227604:
                    if (name.equals(TAG_IDLE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 109522647:
                    if (name.equals(TAG_SLEEP)) {
                        c = 0;
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
                    if (xmlResourceParser.next() == 4) {
                        setPowerConstant(0, xmlResourceParser.getText());
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (xmlResourceParser.next() == 4) {
                        setPowerConstant(268435456, xmlResourceParser.getText());
                        break;
                    } else {
                        break;
                    }
                case 2:
                    parseActivePowerConstantsFromXml(xmlResourceParser);
                    break;
                default:
                    android.util.Slog.e(TAG, "Unexpected element parsed: " + name);
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void parseActivePowerConstantsFromXml(android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int i;
        boolean z;
        try {
            int typeFromAttribute = getTypeFromAttribute(xmlResourceParser, ATTR_RAT, MODEM_RAT_TYPE_NAMES);
            if (typeFromAttribute == 2097152) {
                i = getTypeFromAttribute(xmlResourceParser, ATTR_NR_FREQUENCY, MODEM_NR_FREQUENCY_RANGE_NAMES);
            } else {
                i = 0;
            }
            int depth = xmlResourceParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
                java.lang.String name = xmlResourceParser.getName();
                switch (name.hashCode()) {
                    case 1082290915:
                        if (name.equals(TAG_RECEIVE)) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    case 1280889520:
                        if (name.equals(TAG_TRANSMIT)) {
                            z = true;
                            break;
                        }
                        z = -1;
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        if (xmlResourceParser.next() == 4) {
                            setPowerConstant(536870912 | typeFromAttribute | i, xmlResourceParser.getText());
                            break;
                        } else {
                            break;
                        }
                    case true:
                        int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(xmlResourceParser, "level", -1);
                        if (xmlResourceParser.next() == 4) {
                            java.lang.String text = xmlResourceParser.getText();
                            if (readIntAttribute < 0 || readIntAttribute >= 5) {
                                android.util.Slog.e(TAG, "Unexpected tx level: " + readIntAttribute + ". Must be between 0 and 4");
                                break;
                            } else {
                                setPowerConstant(MODEM_TX_LEVEL_MAP[readIntAttribute] | 805306368 | typeFromAttribute | i, text);
                                break;
                            }
                        } else {
                            break;
                        }
                        break;
                    default:
                        android.util.Slog.e(TAG, "Unexpected element parsed: " + name);
                        break;
                }
            }
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Failed parse to active modem power constants", e);
        }
    }

    private static int getTypeFromAttribute(android.content.res.XmlResourceParser xmlResourceParser, java.lang.String str, android.util.SparseArray<java.lang.String> sparseArray) {
        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(xmlResourceParser, str);
        if (readStringAttribute == null) {
            return 0;
        }
        int size = sparseArray.size();
        int i = -1;
        for (int i2 = 0; i2 < size; i2++) {
            if (readStringAttribute.equals(sparseArray.valueAt(i2))) {
                i = i2;
            }
        }
        if (i < 0) {
            java.lang.String[] strArr = new java.lang.String[size];
            for (int i3 = 0; i3 < size; i3++) {
                strArr[i3] = sparseArray.valueAt(i3);
            }
            throw new java.lang.IllegalArgumentException("Unexpected " + str + " value : " + readStringAttribute + ". Acceptable values are " + java.util.Arrays.toString(strArr));
        }
        return sparseArray.keyAt(i);
    }

    public void setPowerConstant(int i, java.lang.String str) {
        try {
            this.mPowerConstants.put(i, java.lang.Double.valueOf(str).doubleValue());
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to set power constant 0x" + java.lang.Integer.toHexString(i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + keyToString(i) + ") to " + str, e);
        }
    }

    public double getAverageBatteryDrainMa(int i) {
        int i2;
        double d = this.mPowerConstants.get(i, Double.NaN);
        if (!java.lang.Double.isNaN(d)) {
            return d;
        }
        if ((983040 & i) == 0) {
            i2 = i;
        } else {
            i2 = ((-983041) & i) | 0;
            double d2 = this.mPowerConstants.get(i2, Double.NaN);
            if (!java.lang.Double.isNaN(d2)) {
                return d2;
            }
        }
        if ((MODEM_RAT_TYPE_MASK & i2) != 0) {
            double d3 = this.mPowerConstants.get((i2 & (-15728641)) | 0, Double.NaN);
            if (!java.lang.Double.isNaN(d3)) {
                return d3;
            }
        }
        android.util.Slog.w(TAG, "getAverageBatteryDrainMaH called with unexpected key: 0x" + java.lang.Integer.toHexString(i) + ", " + keyToString(i));
        return Double.NaN;
    }

    public static java.lang.String keyToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int i2 = (-268435456) & i;
        appendFieldToString(sb, "drain", MODEM_DRAIN_TYPE_NAMES, i2);
        sb.append(",");
        if (i2 == 805306368) {
            appendFieldToString(sb, "level", MODEM_TX_LEVEL_NAMES, MODEM_TX_LEVEL_MASK & i);
            sb.append(",");
        }
        int i3 = MODEM_RAT_TYPE_MASK & i;
        appendFieldToString(sb, "RAT", MODEM_RAT_TYPE_NAMES, i3);
        if (i3 == 2097152) {
            sb.append(",");
            appendFieldToString(sb, "nrFreq", MODEM_NR_FREQUENCY_RANGE_NAMES, i & 983040);
        }
        return sb.toString();
    }

    private static void appendFieldToString(java.lang.StringBuilder sb, java.lang.String str, android.util.SparseArray<java.lang.String> sparseArray, int i) {
        sb.append(str);
        sb.append(":");
        java.lang.String str2 = sparseArray.get(i, null);
        if (str2 == null) {
            sb.append("UNKNOWN(0x");
            sb.append(java.lang.Integer.toHexString(i));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            return;
        }
        sb.append(str2);
    }

    public void clear() {
        this.mPowerConstants.clear();
    }

    public void dump(java.io.PrintWriter printWriter) {
        int size = this.mPowerConstants.size();
        for (int i = 0; i < size; i++) {
            printWriter.print(keyToString(this.mPowerConstants.keyAt(i)));
            printWriter.print("=");
            printWriter.println(this.mPowerConstants.valueAt(i));
        }
    }
}
