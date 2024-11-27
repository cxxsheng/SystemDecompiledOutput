package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class PersoSubstate {
    public static final int IN_PROGRESS = 1;
    public static final int READY = 2;
    public static final int RUIM_CORPORATE = 16;
    public static final int RUIM_CORPORATE_PUK = 22;
    public static final int RUIM_HRPD = 15;
    public static final int RUIM_HRPD_PUK = 21;
    public static final int RUIM_NETWORK1 = 13;
    public static final int RUIM_NETWORK1_PUK = 19;
    public static final int RUIM_NETWORK2 = 14;
    public static final int RUIM_NETWORK2_PUK = 20;
    public static final int RUIM_RUIM = 18;
    public static final int RUIM_RUIM_PUK = 24;
    public static final int RUIM_SERVICE_PROVIDER = 17;
    public static final int RUIM_SERVICE_PROVIDER_PUK = 23;
    public static final int SIM_CORPORATE = 5;
    public static final int SIM_CORPORATE_PUK = 10;
    public static final int SIM_NETWORK = 3;
    public static final int SIM_NETWORK_PUK = 8;
    public static final int SIM_NETWORK_SUBSET = 4;
    public static final int SIM_NETWORK_SUBSET_PUK = 9;
    public static final int SIM_SERVICE_PROVIDER = 6;
    public static final int SIM_SERVICE_PROVIDER_PUK = 11;
    public static final int SIM_SIM = 7;
    public static final int SIM_SIM_PUK = 12;
    public static final int UNKNOWN = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "IN_PROGRESS";
        }
        if (i == 2) {
            return "READY";
        }
        if (i == 3) {
            return "SIM_NETWORK";
        }
        if (i == 4) {
            return "SIM_NETWORK_SUBSET";
        }
        if (i == 5) {
            return "SIM_CORPORATE";
        }
        if (i == 6) {
            return "SIM_SERVICE_PROVIDER";
        }
        if (i == 7) {
            return "SIM_SIM";
        }
        if (i == 8) {
            return "SIM_NETWORK_PUK";
        }
        if (i == 9) {
            return "SIM_NETWORK_SUBSET_PUK";
        }
        if (i == 10) {
            return "SIM_CORPORATE_PUK";
        }
        if (i == 11) {
            return "SIM_SERVICE_PROVIDER_PUK";
        }
        if (i == 12) {
            return "SIM_SIM_PUK";
        }
        if (i == 13) {
            return "RUIM_NETWORK1";
        }
        if (i == 14) {
            return "RUIM_NETWORK2";
        }
        if (i == 15) {
            return "RUIM_HRPD";
        }
        if (i == 16) {
            return "RUIM_CORPORATE";
        }
        if (i == 17) {
            return "RUIM_SERVICE_PROVIDER";
        }
        if (i == 18) {
            return "RUIM_RUIM";
        }
        if (i == 19) {
            return "RUIM_NETWORK1_PUK";
        }
        if (i == 20) {
            return "RUIM_NETWORK2_PUK";
        }
        if (i == 21) {
            return "RUIM_HRPD_PUK";
        }
        if (i == 22) {
            return "RUIM_CORPORATE_PUK";
        }
        if (i == 23) {
            return "RUIM_SERVICE_PROVIDER_PUK";
        }
        if (i == 24) {
            return "RUIM_RUIM_PUK";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            arrayList.add("IN_PROGRESS");
        }
        if ((i & 2) == 2) {
            arrayList.add("READY");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SIM_NETWORK");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("SIM_NETWORK_SUBSET");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("SIM_CORPORATE");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("SIM_SERVICE_PROVIDER");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("SIM_SIM");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("SIM_NETWORK_PUK");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("SIM_NETWORK_SUBSET_PUK");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("SIM_CORPORATE_PUK");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("SIM_SERVICE_PROVIDER_PUK");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("SIM_SIM_PUK");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("RUIM_NETWORK1");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("RUIM_NETWORK2");
            i2 |= 14;
        }
        if ((i & 15) == 15) {
            arrayList.add("RUIM_HRPD");
            i2 = 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("RUIM_CORPORATE");
            i2 |= 16;
        }
        if ((i & 17) == 17) {
            arrayList.add("RUIM_SERVICE_PROVIDER");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("RUIM_RUIM");
            i2 |= 18;
        }
        if ((i & 19) == 19) {
            arrayList.add("RUIM_NETWORK1_PUK");
            i2 |= 19;
        }
        if ((i & 20) == 20) {
            arrayList.add("RUIM_NETWORK2_PUK");
            i2 |= 20;
        }
        if ((i & 21) == 21) {
            arrayList.add("RUIM_HRPD_PUK");
            i2 |= 21;
        }
        if ((i & 22) == 22) {
            arrayList.add("RUIM_CORPORATE_PUK");
            i2 |= 22;
        }
        if ((i & 23) == 23) {
            arrayList.add("RUIM_SERVICE_PROVIDER_PUK");
            i2 |= 23;
        }
        if ((i & 24) == 24) {
            arrayList.add("RUIM_RUIM_PUK");
            i2 |= 24;
        }
        if (i != i2) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (~i2)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
