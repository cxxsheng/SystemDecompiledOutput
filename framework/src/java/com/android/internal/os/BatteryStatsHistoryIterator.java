package com.android.internal.os;

/* loaded from: classes4.dex */
public class BatteryStatsHistoryIterator implements java.util.Iterator<android.os.BatteryStats.HistoryItem>, java.lang.AutoCloseable {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "BatteryStatsHistoryItr";
    private final com.android.internal.os.BatteryStatsHistory mBatteryStatsHistory;
    private boolean mClosed;
    private final long mEndTimeMs;
    private boolean mNextItemReady;
    private final long mStartTimeMs;
    private boolean mTimeInitialized;
    private final android.os.BatteryStats.HistoryStepDetails mReadHistoryStepDetails = new android.os.BatteryStats.HistoryStepDetails();
    private final android.util.SparseArray<android.os.BatteryStats.HistoryTag> mHistoryTags = new android.util.SparseArray<>();
    private final com.android.internal.os.PowerStats.DescriptorRegistry mDescriptorRegistry = new com.android.internal.os.PowerStats.DescriptorRegistry();
    private android.os.BatteryStats.HistoryItem mHistoryItem = new android.os.BatteryStats.HistoryItem();

    public BatteryStatsHistoryIterator(com.android.internal.os.BatteryStatsHistory batteryStatsHistory, long j, long j2) {
        this.mBatteryStatsHistory = batteryStatsHistory;
        this.mStartTimeMs = j;
        this.mEndTimeMs = j2 == -1 ? Long.MAX_VALUE : j2;
        this.mHistoryItem.clear();
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.mNextItemReady) {
            advance();
        }
        return this.mHistoryItem != null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    public android.os.BatteryStats.HistoryItem next() {
        if (!this.mNextItemReady) {
            advance();
        }
        this.mNextItemReady = false;
        return this.mHistoryItem;
    }

    private void advance() {
        do {
            android.os.Parcel nextParcel = this.mBatteryStatsHistory.getNextParcel(this.mStartTimeMs, this.mEndTimeMs);
            if (nextParcel != null) {
                if (!this.mTimeInitialized) {
                    this.mHistoryItem.time = this.mBatteryStatsHistory.getHistoryBufferStartTime(nextParcel);
                    this.mTimeInitialized = true;
                }
                long j = this.mHistoryItem.time;
                long j2 = this.mHistoryItem.currentTime;
                try {
                    readHistoryDelta(nextParcel, this.mHistoryItem);
                    if (this.mHistoryItem.cmd != 5 && this.mHistoryItem.cmd != 7 && j2 != 0) {
                        this.mHistoryItem.currentTime = j2 + (this.mHistoryItem.time - j);
                    }
                    if (this.mEndTimeMs == 0 || this.mHistoryItem.time < this.mEndTimeMs) {
                    }
                } catch (java.lang.Throwable th) {
                    android.util.Slog.wtf(TAG, "Corrupted battery history", th);
                }
            }
            this.mHistoryItem = null;
            this.mNextItemReady = true;
            close();
            return;
        } while (this.mHistoryItem.time < this.mStartTimeMs);
        this.mNextItemReady = true;
    }

    private void readHistoryDelta(android.os.Parcel parcel, android.os.BatteryStats.HistoryItem historyItem) {
        int i;
        int readInt = parcel.readInt();
        int i2 = 524287 & readInt;
        historyItem.cmd = (byte) 0;
        historyItem.numReadInts = 1;
        if (i2 < 524285) {
            historyItem.time += i2;
        } else if (i2 == 524285) {
            historyItem.readFromParcel(parcel);
            return;
        } else if (i2 == 524286) {
            historyItem.time += parcel.readInt();
            historyItem.numReadInts++;
        } else {
            historyItem.time += parcel.readLong();
            historyItem.numReadInts += 2;
        }
        if ((524288 & readInt) != 0) {
            i = parcel.readInt();
            readBatteryLevelInt(i, historyItem);
            historyItem.numReadInts++;
        } else {
            i = 0;
        }
        if ((1048576 & readInt) != 0) {
            int readInt2 = parcel.readInt();
            historyItem.states = (16777215 & readInt2) | ((-33554432) & readInt);
            historyItem.batteryStatus = (byte) ((readInt2 >> 29) & 7);
            historyItem.batteryHealth = (byte) ((readInt2 >> 26) & 7);
            historyItem.batteryPlugType = (byte) ((readInt2 >> 24) & 3);
            switch (historyItem.batteryPlugType) {
                case 1:
                    historyItem.batteryPlugType = (byte) 1;
                    break;
                case 2:
                    historyItem.batteryPlugType = (byte) 2;
                    break;
                case 3:
                    historyItem.batteryPlugType = (byte) 4;
                    break;
            }
            historyItem.numReadInts++;
        } else {
            historyItem.states = (readInt & (-33554432)) | (historyItem.states & 16777215);
        }
        if ((2097152 & readInt) != 0) {
            historyItem.states2 = parcel.readInt();
        }
        if ((4194304 & readInt) != 0) {
            int readInt3 = parcel.readInt();
            int i3 = readInt3 & 65535;
            int i4 = (readInt3 >> 16) & 65535;
            if (readHistoryTag(parcel, i3, historyItem.localWakelockTag)) {
                historyItem.wakelockTag = historyItem.localWakelockTag;
            } else {
                historyItem.wakelockTag = null;
            }
            if (readHistoryTag(parcel, i4, historyItem.localWakeReasonTag)) {
                historyItem.wakeReasonTag = historyItem.localWakeReasonTag;
            } else {
                historyItem.wakeReasonTag = null;
            }
            historyItem.numReadInts++;
        } else {
            historyItem.wakelockTag = null;
            historyItem.wakeReasonTag = null;
        }
        if ((8388608 & readInt) != 0) {
            historyItem.eventTag = historyItem.localEventTag;
            int readInt4 = parcel.readInt();
            historyItem.eventCode = readInt4 & 65535;
            if (readHistoryTag(parcel, (readInt4 >> 16) & 65535, historyItem.localEventTag)) {
                historyItem.eventTag = historyItem.localEventTag;
            } else {
                historyItem.eventTag = null;
            }
            historyItem.numReadInts++;
        } else {
            historyItem.eventCode = 0;
        }
        if ((i & 1) != 0) {
            historyItem.stepDetails = this.mReadHistoryStepDetails;
            historyItem.stepDetails.readFromParcel(parcel);
        } else {
            historyItem.stepDetails = null;
        }
        if ((readInt & 16777216) != 0) {
            historyItem.batteryChargeUah = parcel.readInt();
        }
        historyItem.modemRailChargeMah = parcel.readDouble();
        historyItem.wifiRailChargeMah = parcel.readDouble();
        if ((historyItem.states2 & 131072) != 0) {
            int readInt5 = parcel.readInt();
            if ((readInt5 & 1) != 0) {
                this.mDescriptorRegistry.register(com.android.internal.os.PowerStats.Descriptor.readSummaryFromParcel(parcel));
            }
            if ((readInt5 & 2) != 0) {
                historyItem.powerStats = com.android.internal.os.PowerStats.readFromParcel(parcel, this.mDescriptorRegistry);
            } else {
                historyItem.powerStats = null;
            }
            if ((readInt5 & 4) != 0) {
                historyItem.processStateChange = historyItem.localProcessStateChange;
                historyItem.processStateChange.readFromParcel(parcel);
                return;
            } else {
                historyItem.processStateChange = null;
                return;
            }
        }
        historyItem.powerStats = null;
        historyItem.processStateChange = null;
    }

    private boolean readHistoryTag(android.os.Parcel parcel, int i, android.os.BatteryStats.HistoryTag historyTag) {
        if (i == 65535) {
            return false;
        }
        if ((32768 & i) != 0) {
            android.os.BatteryStats.HistoryTag historyTag2 = new android.os.BatteryStats.HistoryTag();
            historyTag2.readFromParcel(parcel);
            historyTag2.poolIdx = (-32769) & i;
            if (historyTag2.poolIdx < 32766) {
                this.mHistoryTags.put(historyTag2.poolIdx, historyTag2);
            } else {
                historyTag2.poolIdx = -1;
            }
            historyTag.setTo(historyTag2);
            return true;
        }
        android.os.BatteryStats.HistoryTag historyTag3 = this.mHistoryTags.get(i);
        if (historyTag3 != null) {
            historyTag.setTo(historyTag3);
        } else {
            historyTag.string = null;
            historyTag.uid = 0;
        }
        historyTag.poolIdx = i;
        return true;
    }

    private static void readBatteryLevelInt(int i, android.os.BatteryStats.HistoryItem historyItem) {
        historyItem.batteryLevel = (byte) (((-33554432) & i) >>> 25);
        historyItem.batteryTemperature = (short) ((33521664 & i) >>> 15);
        int i2 = (i & 32766) >>> 1;
        if (i2 == 16383) {
            i2 = -1;
        }
        historyItem.batteryVoltage = (short) i2;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (!this.mClosed) {
            this.mClosed = true;
            this.mBatteryStatsHistory.iteratorFinished();
        }
    }
}
