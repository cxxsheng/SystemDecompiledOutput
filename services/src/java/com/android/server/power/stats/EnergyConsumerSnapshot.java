package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class EnergyConsumerSnapshot {
    private static final int MILLIVOLTS_PER_VOLT = 1000;
    private static final java.lang.String TAG = "EnergyConsumerSnapshot";
    public static final long UNAVAILABLE = -1;
    private final android.util.SparseArray<android.util.SparseLongArray> mAttributionSnapshots;
    private final android.util.SparseLongArray mEnergyConsumerSnapshots;
    private final android.util.SparseArray<android.hardware.power.stats.EnergyConsumer> mEnergyConsumers;
    private final int mNumCpuClusterOrdinals;
    private final int mNumDisplayOrdinals;
    private final int mNumOtherOrdinals;
    private final android.util.SparseIntArray mVoltageSnapshots;

    EnergyConsumerSnapshot(@android.annotation.NonNull android.util.SparseArray<android.hardware.power.stats.EnergyConsumer> sparseArray) {
        this.mEnergyConsumers = sparseArray;
        this.mEnergyConsumerSnapshots = new android.util.SparseLongArray(this.mEnergyConsumers.size());
        this.mVoltageSnapshots = new android.util.SparseIntArray(this.mEnergyConsumers.size());
        this.mNumCpuClusterOrdinals = calculateNumOrdinals(2, sparseArray);
        this.mNumDisplayOrdinals = calculateNumOrdinals(3, sparseArray);
        this.mNumOtherOrdinals = calculateNumOrdinals(0, sparseArray);
        this.mAttributionSnapshots = new android.util.SparseArray<>(this.mNumOtherOrdinals);
    }

    static class EnergyConsumerDeltaData {
        public long bluetoothChargeUC = -1;
        public long[] cpuClusterChargeUC = null;
        public long[] displayChargeUC = null;
        public long gnssChargeUC = -1;
        public long mobileRadioChargeUC = -1;
        public long wifiChargeUC = -1;
        public long cameraChargeUC = -1;

        @android.annotation.Nullable
        public long[] otherTotalChargeUC = null;

        @android.annotation.Nullable
        public android.util.SparseLongArray[] otherUidChargesUC = null;

        EnergyConsumerDeltaData() {
        }

        boolean isEmpty() {
            return this.bluetoothChargeUC <= 0 && isEmpty(this.cpuClusterChargeUC) && isEmpty(this.displayChargeUC) && this.gnssChargeUC <= 0 && this.mobileRadioChargeUC <= 0 && this.wifiChargeUC <= 0 && isEmpty(this.otherTotalChargeUC);
        }

        private boolean isEmpty(long[] jArr) {
            if (jArr == null) {
                return true;
            }
            for (long j : jArr) {
                if (j > 0) {
                    return false;
                }
            }
            return true;
        }
    }

    @android.annotation.Nullable
    public com.android.server.power.stats.EnergyConsumerSnapshot.EnergyConsumerDeltaData updateAndGetDelta(android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr, int i) {
        int i2;
        int i3;
        java.lang.String str;
        android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr2 = energyConsumerResultArr;
        android.hardware.power.stats.EnergyConsumer energyConsumer = null;
        if (energyConsumerResultArr2 == null || energyConsumerResultArr2.length == 0) {
            return null;
        }
        java.lang.String str2 = TAG;
        if (i <= 0) {
            android.util.Slog.wtf(TAG, "Unexpected battery voltage (" + i + " mV) when taking energy consumer snapshot");
            return null;
        }
        com.android.server.power.stats.EnergyConsumerSnapshot.EnergyConsumerDeltaData energyConsumerDeltaData = new com.android.server.power.stats.EnergyConsumerSnapshot.EnergyConsumerDeltaData();
        int length = energyConsumerResultArr2.length;
        int i4 = 0;
        while (i4 < length) {
            android.hardware.power.stats.EnergyConsumerResult energyConsumerResult = energyConsumerResultArr2[i4];
            int i5 = energyConsumerResult.id;
            long j = energyConsumerResult.energyUWs;
            android.hardware.power.stats.EnergyConsumerAttribution[] energyConsumerAttributionArr = energyConsumerResult.attribution;
            android.hardware.power.stats.EnergyConsumer energyConsumer2 = this.mEnergyConsumers.get(i5, energyConsumer);
            if (energyConsumer2 == null) {
                android.util.Slog.e(str2, "updateAndGetDelta given invalid consumerId " + i5);
                i2 = length;
                i3 = i4;
                str = str2;
            } else {
                byte b = energyConsumer2.type;
                int i6 = energyConsumer2.ordinal;
                java.lang.String str3 = str2;
                long j2 = this.mEnergyConsumerSnapshots.get(i5, -1L);
                int i7 = this.mVoltageSnapshots.get(i5);
                this.mEnergyConsumerSnapshots.put(i5, j);
                this.mVoltageSnapshots.put(i5, i);
                int i8 = ((i7 + i) + 1) / 2;
                android.util.SparseLongArray updateAndGetDeltaForTypeOther = updateAndGetDeltaForTypeOther(energyConsumer2, energyConsumerAttributionArr, i8);
                if (j2 < 0) {
                    i2 = length;
                    i3 = i4;
                    str = str3;
                } else if (j == j2) {
                    i2 = length;
                    i3 = i4;
                    str = str3;
                } else {
                    i2 = length;
                    i3 = i4;
                    long j3 = j - j2;
                    if (j3 < 0) {
                        str = str3;
                    } else if (i7 > 0) {
                        long calculateChargeConsumedUC = calculateChargeConsumedUC(j3, i8);
                        switch (b) {
                            case 0:
                                str = str3;
                                if (energyConsumerDeltaData.otherTotalChargeUC == null) {
                                    energyConsumerDeltaData.otherTotalChargeUC = new long[this.mNumOtherOrdinals];
                                    energyConsumerDeltaData.otherUidChargesUC = new android.util.SparseLongArray[this.mNumOtherOrdinals];
                                }
                                energyConsumerDeltaData.otherTotalChargeUC[i6] = calculateChargeConsumedUC;
                                energyConsumerDeltaData.otherUidChargesUC[i6] = updateAndGetDeltaForTypeOther;
                                break;
                            case 1:
                                str = str3;
                                energyConsumerDeltaData.bluetoothChargeUC = calculateChargeConsumedUC;
                                break;
                            case 2:
                                str = str3;
                                if (energyConsumerDeltaData.cpuClusterChargeUC == null) {
                                    energyConsumerDeltaData.cpuClusterChargeUC = new long[this.mNumCpuClusterOrdinals];
                                }
                                energyConsumerDeltaData.cpuClusterChargeUC[i6] = calculateChargeConsumedUC;
                                break;
                            case 3:
                                str = str3;
                                if (energyConsumerDeltaData.displayChargeUC == null) {
                                    energyConsumerDeltaData.displayChargeUC = new long[this.mNumDisplayOrdinals];
                                }
                                energyConsumerDeltaData.displayChargeUC[i6] = calculateChargeConsumedUC;
                                break;
                            case 4:
                                str = str3;
                                energyConsumerDeltaData.gnssChargeUC = calculateChargeConsumedUC;
                                break;
                            case 5:
                                str = str3;
                                energyConsumerDeltaData.mobileRadioChargeUC = calculateChargeConsumedUC;
                                break;
                            case 6:
                                str = str3;
                                energyConsumerDeltaData.wifiChargeUC = calculateChargeConsumedUC;
                                break;
                            case 7:
                                str = str3;
                                energyConsumerDeltaData.cameraChargeUC = calculateChargeConsumedUC;
                                break;
                            default:
                                str = str3;
                                android.util.Slog.w(str, "Ignoring consumer " + energyConsumer2.name + " of unknown type " + ((int) b));
                                break;
                        }
                    } else {
                        str = str3;
                    }
                    android.util.Slog.e(str, "Bad data! EnergyConsumer " + energyConsumer2.name + ": new energy (" + j + ") < old energy (" + j2 + "), new voltage (" + i + "), old voltage (" + i7 + "). Skipping. ");
                }
            }
            i4 = i3 + 1;
            energyConsumerResultArr2 = energyConsumerResultArr;
            str2 = str;
            length = i2;
            energyConsumer = null;
        }
        return energyConsumerDeltaData;
    }

    @android.annotation.Nullable
    private android.util.SparseLongArray updateAndGetDeltaForTypeOther(@android.annotation.NonNull android.hardware.power.stats.EnergyConsumer energyConsumer, @android.annotation.Nullable android.hardware.power.stats.EnergyConsumerAttribution[] energyConsumerAttributionArr, int i) {
        android.hardware.power.stats.EnergyConsumerAttribution[] energyConsumerAttributionArr2;
        android.hardware.power.stats.EnergyConsumerAttribution[] energyConsumerAttributionArr3;
        android.util.SparseLongArray sparseLongArray;
        if (energyConsumer.type != 0) {
            return null;
        }
        int i2 = 0;
        if (energyConsumerAttributionArr != null) {
            energyConsumerAttributionArr2 = energyConsumerAttributionArr;
        } else {
            energyConsumerAttributionArr2 = new android.hardware.power.stats.EnergyConsumerAttribution[0];
        }
        android.util.SparseLongArray sparseLongArray2 = this.mAttributionSnapshots.get(energyConsumer.id, null);
        if (sparseLongArray2 == null) {
            android.util.SparseLongArray sparseLongArray3 = new android.util.SparseLongArray(energyConsumerAttributionArr2.length);
            this.mAttributionSnapshots.put(energyConsumer.id, sparseLongArray3);
            int length = energyConsumerAttributionArr2.length;
            while (i2 < length) {
                android.hardware.power.stats.EnergyConsumerAttribution energyConsumerAttribution = energyConsumerAttributionArr2[i2];
                sparseLongArray3.put(energyConsumerAttribution.uid, energyConsumerAttribution.energyUWs);
                i2++;
            }
            return null;
        }
        android.util.SparseLongArray sparseLongArray4 = new android.util.SparseLongArray();
        int length2 = energyConsumerAttributionArr2.length;
        while (i2 < length2) {
            android.hardware.power.stats.EnergyConsumerAttribution energyConsumerAttribution2 = energyConsumerAttributionArr2[i2];
            int i3 = energyConsumerAttribution2.uid;
            long j = energyConsumerAttribution2.energyUWs;
            long j2 = sparseLongArray2.get(i3, 0L);
            sparseLongArray2.put(i3, j);
            if (j2 < 0) {
                energyConsumerAttributionArr3 = energyConsumerAttributionArr2;
                sparseLongArray = sparseLongArray2;
            } else if (j == j2) {
                energyConsumerAttributionArr3 = energyConsumerAttributionArr2;
                sparseLongArray = sparseLongArray2;
            } else {
                energyConsumerAttributionArr3 = energyConsumerAttributionArr2;
                sparseLongArray = sparseLongArray2;
                long j3 = j - j2;
                if (j3 < 0 || i <= 0) {
                    android.util.Slog.e(TAG, "EnergyConsumer " + energyConsumer.name + ": new energy (" + j + ") but old energy (" + j2 + "). Average voltage (" + i + ")Skipping. ");
                } else {
                    sparseLongArray4.put(i3, calculateChargeConsumedUC(j3, i));
                }
            }
            i2++;
            sparseLongArray2 = sparseLongArray;
            energyConsumerAttributionArr2 = energyConsumerAttributionArr3;
        }
        return sparseLongArray4;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Energy consumer snapshot");
        printWriter.println("List of EnergyConsumers:");
        for (int i = 0; i < this.mEnergyConsumers.size(); i++) {
            int keyAt = this.mEnergyConsumers.keyAt(i);
            android.hardware.power.stats.EnergyConsumer valueAt = this.mEnergyConsumers.valueAt(i);
            printWriter.println(java.lang.String.format("    Consumer %d is {id=%d, ordinal=%d, type=%d, name=%s}", java.lang.Integer.valueOf(keyAt), java.lang.Integer.valueOf(valueAt.id), java.lang.Integer.valueOf(valueAt.ordinal), java.lang.Byte.valueOf(valueAt.type), valueAt.name));
        }
        printWriter.println("Map of consumerIds to energy (in microjoules):");
        for (int i2 = 0; i2 < this.mEnergyConsumerSnapshots.size(); i2++) {
            printWriter.println(java.lang.String.format("    Consumer %d has energy %d uJ at %d mV", java.lang.Integer.valueOf(this.mEnergyConsumerSnapshots.keyAt(i2)), java.lang.Long.valueOf(this.mEnergyConsumerSnapshots.valueAt(i2)), java.lang.Long.valueOf(this.mVoltageSnapshots.valueAt(i2))));
        }
        printWriter.println("List of the " + this.mNumOtherOrdinals + " OTHER EnergyConsumers:");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("    ");
        sb.append(this.mAttributionSnapshots);
        printWriter.println(sb.toString());
        printWriter.println();
    }

    public java.lang.String[] getOtherOrdinalNames() {
        java.lang.String[] strArr = new java.lang.String[this.mNumOtherOrdinals];
        int size = this.mEnergyConsumers.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            android.hardware.power.stats.EnergyConsumer valueAt = this.mEnergyConsumers.valueAt(i2);
            if (valueAt.type == 0) {
                strArr[i] = sanitizeCustomBucketName(valueAt.name);
                i++;
            }
        }
        return strArr;
    }

    private java.lang.String sanitizeCustomBucketName(java.lang.String str) {
        if (str == null) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str.length());
        for (char c : str.toCharArray()) {
            if (java.lang.Character.isWhitespace(c)) {
                sb.append(' ');
            } else if (java.lang.Character.isISOControl(c)) {
                sb.append('_');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static int calculateNumOrdinals(@android.hardware.power.stats.EnergyConsumerType int i, android.util.SparseArray<android.hardware.power.stats.EnergyConsumer> sparseArray) {
        if (sparseArray == null) {
            return 0;
        }
        int size = sparseArray.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (sparseArray.valueAt(i3).type == i) {
                i2++;
            }
        }
        return i2;
    }

    private long calculateChargeConsumedUC(long j, int i) {
        return ((j * 1000) + (i / 2)) / i;
    }
}
