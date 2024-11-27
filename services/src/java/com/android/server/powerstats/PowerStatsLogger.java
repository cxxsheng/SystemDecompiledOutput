package com.android.server.powerstats;

/* loaded from: classes2.dex */
public final class PowerStatsLogger extends android.os.Handler {
    private static final boolean DEBUG = false;
    protected static final int MSG_LOG_TO_DATA_STORAGE_BATTERY_DROP = 0;
    protected static final int MSG_LOG_TO_DATA_STORAGE_HIGH_FREQUENCY = 2;
    protected static final int MSG_LOG_TO_DATA_STORAGE_LOW_FREQUENCY = 1;
    private static final java.lang.String TAG = com.android.server.powerstats.PowerStatsLogger.class.getSimpleName();
    private java.io.File mDataStoragePath;
    private boolean mDeleteMeterDataOnBoot;
    private boolean mDeleteModelDataOnBoot;
    private boolean mDeleteResidencyDataOnBoot;
    private final com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper mPowerStatsHALWrapper;
    private final com.android.server.powerstats.PowerStatsDataStorage mPowerStatsMeterStorage;
    private final com.android.server.powerstats.PowerStatsDataStorage mPowerStatsModelStorage;
    private final com.android.server.powerstats.PowerStatsDataStorage mPowerStatsResidencyStorage;
    private final long mStartWallTime;

    @Override // android.os.Handler
    public void handleMessage(android.os.Message message) {
        switch (message.what) {
            case 0:
                android.hardware.power.stats.StateResidencyResult[] stateResidency = this.mPowerStatsHALWrapper.getStateResidency(new int[0]);
                com.android.server.powerstats.ProtoStreamUtils.StateResidencyResultUtils.adjustTimeSinceBootToEpoch(stateResidency, this.mStartWallTime);
                this.mPowerStatsResidencyStorage.write(com.android.server.powerstats.ProtoStreamUtils.StateResidencyResultUtils.getProtoBytes(stateResidency));
                break;
            case 1:
                android.hardware.power.stats.EnergyConsumerResult[] energyConsumed = this.mPowerStatsHALWrapper.getEnergyConsumed(new int[0]);
                com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerResultUtils.adjustTimeSinceBootToEpoch(energyConsumed, this.mStartWallTime);
                this.mPowerStatsModelStorage.write(com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerResultUtils.getProtoBytes(energyConsumed, true));
                break;
            case 2:
                android.hardware.power.stats.EnergyMeasurement[] readEnergyMeter = this.mPowerStatsHALWrapper.readEnergyMeter(new int[0]);
                com.android.server.powerstats.ProtoStreamUtils.EnergyMeasurementUtils.adjustTimeSinceBootToEpoch(readEnergyMeter, this.mStartWallTime);
                this.mPowerStatsMeterStorage.write(com.android.server.powerstats.ProtoStreamUtils.EnergyMeasurementUtils.getProtoBytes(readEnergyMeter));
                android.hardware.power.stats.EnergyConsumerResult[] energyConsumed2 = this.mPowerStatsHALWrapper.getEnergyConsumed(new int[0]);
                com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerResultUtils.adjustTimeSinceBootToEpoch(energyConsumed2, this.mStartWallTime);
                this.mPowerStatsModelStorage.write(com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerResultUtils.getProtoBytes(energyConsumed2, false));
                break;
        }
    }

    public void writeMeterDataToFile(java.io.FileDescriptor fileDescriptor) {
        final android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        try {
            com.android.server.powerstats.ProtoStreamUtils.ChannelUtils.packProtoMessage(this.mPowerStatsHALWrapper.getEnergyMeterInfo(), protoOutputStream);
            this.mPowerStatsMeterStorage.read(new com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback() { // from class: com.android.server.powerstats.PowerStatsLogger.1
                @Override // com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback
                public void onReadDataElement(byte[] bArr) {
                    try {
                        new android.util.proto.ProtoInputStream(new java.io.ByteArrayInputStream(bArr));
                        com.android.server.powerstats.ProtoStreamUtils.EnergyMeasurementUtils.packProtoMessage(com.android.server.powerstats.ProtoStreamUtils.EnergyMeasurementUtils.unpackProtoMessage(bArr), protoOutputStream);
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(com.android.server.powerstats.PowerStatsLogger.TAG, "Failed to write energy meter data to incident report.", e);
                    }
                }
            });
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write energy meter info to incident report.", e);
        }
        protoOutputStream.flush();
    }

    public void writeModelDataToFile(java.io.FileDescriptor fileDescriptor) {
        final android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        try {
            com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerUtils.packProtoMessage(this.mPowerStatsHALWrapper.getEnergyConsumerInfo(), protoOutputStream);
            this.mPowerStatsModelStorage.read(new com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback() { // from class: com.android.server.powerstats.PowerStatsLogger.2
                @Override // com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback
                public void onReadDataElement(byte[] bArr) {
                    try {
                        new android.util.proto.ProtoInputStream(new java.io.ByteArrayInputStream(bArr));
                        com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerResultUtils.packProtoMessage(com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerResultUtils.unpackProtoMessage(bArr), protoOutputStream, true);
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(com.android.server.powerstats.PowerStatsLogger.TAG, "Failed to write energy model data to incident report.", e);
                    }
                }
            });
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write energy model info to incident report.", e);
        }
        protoOutputStream.flush();
    }

    public void writeResidencyDataToFile(java.io.FileDescriptor fileDescriptor) {
        final android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        try {
            com.android.server.powerstats.ProtoStreamUtils.PowerEntityUtils.packProtoMessage(this.mPowerStatsHALWrapper.getPowerEntityInfo(), protoOutputStream);
            this.mPowerStatsResidencyStorage.read(new com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback() { // from class: com.android.server.powerstats.PowerStatsLogger.3
                @Override // com.android.server.powerstats.PowerStatsDataStorage.DataElementReadCallback
                public void onReadDataElement(byte[] bArr) {
                    try {
                        new android.util.proto.ProtoInputStream(new java.io.ByteArrayInputStream(bArr));
                        com.android.server.powerstats.ProtoStreamUtils.StateResidencyResultUtils.packProtoMessage(com.android.server.powerstats.ProtoStreamUtils.StateResidencyResultUtils.unpackProtoMessage(bArr), protoOutputStream);
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(com.android.server.powerstats.PowerStatsLogger.TAG, "Failed to write residency data to incident report.", e);
                    }
                }
            });
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write residency data to incident report.", e);
        }
        protoOutputStream.flush();
    }

    private boolean dataChanged(java.lang.String str, byte[] bArr) {
        if (!this.mDataStoragePath.exists() && !this.mDataStoragePath.mkdirs()) {
            return false;
        }
        java.io.File file = new java.io.File(this.mDataStoragePath, str);
        if (!file.exists()) {
            return true;
        }
        try {
            new java.io.FileInputStream(file.getPath()).read(new byte[(int) file.length()]);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to read cached data from file", e);
        }
        return !java.util.Arrays.equals(r5, bArr);
    }

    private void updateCacheFile(java.lang.String str, byte[] bArr) {
        try {
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(this.mDataStoragePath, str));
            java.io.FileOutputStream startWrite = atomicFile.startWrite();
            startWrite.write(bArr);
            atomicFile.finishWrite(startWrite);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to write current data to cached file", e);
        }
    }

    public boolean getDeleteMeterDataOnBoot() {
        return this.mDeleteMeterDataOnBoot;
    }

    public boolean getDeleteModelDataOnBoot() {
        return this.mDeleteModelDataOnBoot;
    }

    public boolean getDeleteResidencyDataOnBoot() {
        return this.mDeleteResidencyDataOnBoot;
    }

    @com.android.internal.annotations.VisibleForTesting
    public long getStartWallTime() {
        return this.mStartWallTime;
    }

    public PowerStatsLogger(android.content.Context context, android.os.Looper looper, java.io.File file, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, com.android.server.powerstats.PowerStatsHALWrapper.IPowerStatsHALWrapper iPowerStatsHALWrapper) {
        super(looper);
        this.mStartWallTime = java.lang.System.currentTimeMillis() - android.os.SystemClock.elapsedRealtime();
        this.mPowerStatsHALWrapper = iPowerStatsHALWrapper;
        this.mDataStoragePath = file;
        this.mPowerStatsMeterStorage = new com.android.server.powerstats.PowerStatsDataStorage(context, this.mDataStoragePath, str);
        this.mPowerStatsModelStorage = new com.android.server.powerstats.PowerStatsDataStorage(context, this.mDataStoragePath, str3);
        this.mPowerStatsResidencyStorage = new com.android.server.powerstats.PowerStatsDataStorage(context, this.mDataStoragePath, str5);
        byte[] protoBytes = com.android.server.powerstats.ProtoStreamUtils.ChannelUtils.getProtoBytes(this.mPowerStatsHALWrapper.getEnergyMeterInfo());
        this.mDeleteMeterDataOnBoot = dataChanged(str2, protoBytes);
        if (this.mDeleteMeterDataOnBoot) {
            this.mPowerStatsMeterStorage.deleteLogs();
            updateCacheFile(str2, protoBytes);
        }
        byte[] protoBytes2 = com.android.server.powerstats.ProtoStreamUtils.EnergyConsumerUtils.getProtoBytes(this.mPowerStatsHALWrapper.getEnergyConsumerInfo());
        this.mDeleteModelDataOnBoot = dataChanged(str4, protoBytes2);
        if (this.mDeleteModelDataOnBoot) {
            this.mPowerStatsModelStorage.deleteLogs();
            updateCacheFile(str4, protoBytes2);
        }
        byte[] protoBytes3 = com.android.server.powerstats.ProtoStreamUtils.PowerEntityUtils.getProtoBytes(this.mPowerStatsHALWrapper.getPowerEntityInfo());
        this.mDeleteResidencyDataOnBoot = dataChanged(str6, protoBytes3);
        if (this.mDeleteResidencyDataOnBoot) {
            this.mPowerStatsResidencyStorage.deleteLogs();
            updateCacheFile(str6, protoBytes3);
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("PowerStats Meter Data:");
        indentingPrintWriter.increaseIndent();
        this.mPowerStatsMeterStorage.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("PowerStats Model Data:");
        indentingPrintWriter.increaseIndent();
        this.mPowerStatsModelStorage.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("PowerStats State Residency Data:");
        indentingPrintWriter.increaseIndent();
        this.mPowerStatsResidencyStorage.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }
}
