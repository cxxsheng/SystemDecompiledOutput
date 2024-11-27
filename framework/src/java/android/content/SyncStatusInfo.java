package android.content;

/* loaded from: classes.dex */
public class SyncStatusInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.SyncStatusInfo> CREATOR = new android.os.Parcelable.Creator<android.content.SyncStatusInfo>() { // from class: android.content.SyncStatusInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncStatusInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.SyncStatusInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncStatusInfo[] newArray(int i) {
            return new android.content.SyncStatusInfo[i];
        }
    };
    private static final int MAX_EVENT_COUNT = 10;
    private static final int SOURCE_COUNT = 6;
    private static final java.lang.String TAG = "Sync";
    static final int VERSION = 6;
    public final int authorityId;
    public long initialFailureTime;
    public boolean initialize;
    public java.lang.String lastFailureMesg;
    public int lastFailureSource;
    public long lastFailureTime;
    public int lastSuccessSource;
    public long lastSuccessTime;
    public long lastTodayResetTime;
    private final java.util.ArrayList<java.lang.Long> mLastEventTimes;
    private final java.util.ArrayList<java.lang.String> mLastEvents;
    public boolean pending;
    public final long[] perSourceLastFailureTimes;
    public final long[] perSourceLastSuccessTimes;
    private java.util.ArrayList<java.lang.Long> periodicSyncTimes;
    public final android.content.SyncStatusInfo.Stats todayStats;
    public final android.content.SyncStatusInfo.Stats totalStats;
    public final android.content.SyncStatusInfo.Stats yesterdayStats;

    public static class Stats {
        public int numCancels;
        public int numFailures;
        public int numSourceFeed;
        public int numSourceLocal;
        public int numSourceOther;
        public int numSourcePeriodic;
        public int numSourcePoll;
        public int numSourceUser;
        public int numSyncs;
        public long totalElapsedTime;

        public void copyTo(android.content.SyncStatusInfo.Stats stats) {
            stats.totalElapsedTime = this.totalElapsedTime;
            stats.numSyncs = this.numSyncs;
            stats.numSourcePoll = this.numSourcePoll;
            stats.numSourceOther = this.numSourceOther;
            stats.numSourceLocal = this.numSourceLocal;
            stats.numSourceUser = this.numSourceUser;
            stats.numSourcePeriodic = this.numSourcePeriodic;
            stats.numSourceFeed = this.numSourceFeed;
            stats.numFailures = this.numFailures;
            stats.numCancels = this.numCancels;
        }

        public void clear() {
            this.totalElapsedTime = 0L;
            this.numSyncs = 0;
            this.numSourcePoll = 0;
            this.numSourceOther = 0;
            this.numSourceLocal = 0;
            this.numSourceUser = 0;
            this.numSourcePeriodic = 0;
            this.numSourceFeed = 0;
            this.numFailures = 0;
            this.numCancels = 0;
        }

        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeLong(this.totalElapsedTime);
            parcel.writeInt(this.numSyncs);
            parcel.writeInt(this.numSourcePoll);
            parcel.writeInt(this.numSourceOther);
            parcel.writeInt(this.numSourceLocal);
            parcel.writeInt(this.numSourceUser);
            parcel.writeInt(this.numSourcePeriodic);
            parcel.writeInt(this.numSourceFeed);
            parcel.writeInt(this.numFailures);
            parcel.writeInt(this.numCancels);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.totalElapsedTime = parcel.readLong();
            this.numSyncs = parcel.readInt();
            this.numSourcePoll = parcel.readInt();
            this.numSourceOther = parcel.readInt();
            this.numSourceLocal = parcel.readInt();
            this.numSourceUser = parcel.readInt();
            this.numSourcePeriodic = parcel.readInt();
            this.numSourceFeed = parcel.readInt();
            this.numFailures = parcel.readInt();
            this.numCancels = parcel.readInt();
        }
    }

    public SyncStatusInfo(int i) {
        this.totalStats = new android.content.SyncStatusInfo.Stats();
        this.todayStats = new android.content.SyncStatusInfo.Stats();
        this.yesterdayStats = new android.content.SyncStatusInfo.Stats();
        this.perSourceLastSuccessTimes = new long[6];
        this.perSourceLastFailureTimes = new long[6];
        this.mLastEventTimes = new java.util.ArrayList<>();
        this.mLastEvents = new java.util.ArrayList<>();
        this.authorityId = i;
    }

    public int getLastFailureMesgAsInt(int i) {
        int syncErrorStringToInt = android.content.ContentResolver.syncErrorStringToInt(this.lastFailureMesg);
        if (syncErrorStringToInt > 0) {
            return syncErrorStringToInt;
        }
        android.util.Log.d(TAG, "Unknown lastFailureMesg:" + this.lastFailureMesg);
        return i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(6);
        parcel.writeInt(this.authorityId);
        parcel.writeLong(this.totalStats.totalElapsedTime);
        parcel.writeInt(this.totalStats.numSyncs);
        parcel.writeInt(this.totalStats.numSourcePoll);
        parcel.writeInt(this.totalStats.numSourceOther);
        parcel.writeInt(this.totalStats.numSourceLocal);
        parcel.writeInt(this.totalStats.numSourceUser);
        parcel.writeLong(this.lastSuccessTime);
        parcel.writeInt(this.lastSuccessSource);
        parcel.writeLong(this.lastFailureTime);
        parcel.writeInt(this.lastFailureSource);
        parcel.writeString(this.lastFailureMesg);
        parcel.writeLong(this.initialFailureTime);
        parcel.writeInt(this.pending ? 1 : 0);
        parcel.writeInt(this.initialize ? 1 : 0);
        if (this.periodicSyncTimes != null) {
            parcel.writeInt(this.periodicSyncTimes.size());
            java.util.Iterator<java.lang.Long> it = this.periodicSyncTimes.iterator();
            while (it.hasNext()) {
                parcel.writeLong(it.next().longValue());
            }
        } else {
            parcel.writeInt(-1);
        }
        parcel.writeInt(this.mLastEventTimes.size());
        for (int i2 = 0; i2 < this.mLastEventTimes.size(); i2++) {
            parcel.writeLong(this.mLastEventTimes.get(i2).longValue());
            parcel.writeString(this.mLastEvents.get(i2));
        }
        parcel.writeInt(this.totalStats.numSourcePeriodic);
        parcel.writeInt(this.totalStats.numSourceFeed);
        parcel.writeInt(this.totalStats.numFailures);
        parcel.writeInt(this.totalStats.numCancels);
        parcel.writeLong(this.lastTodayResetTime);
        this.todayStats.writeToParcel(parcel);
        this.yesterdayStats.writeToParcel(parcel);
        parcel.writeLongArray(this.perSourceLastSuccessTimes);
        parcel.writeLongArray(this.perSourceLastFailureTimes);
    }

    public SyncStatusInfo(android.os.Parcel parcel) {
        this.totalStats = new android.content.SyncStatusInfo.Stats();
        this.todayStats = new android.content.SyncStatusInfo.Stats();
        this.yesterdayStats = new android.content.SyncStatusInfo.Stats();
        this.perSourceLastSuccessTimes = new long[6];
        this.perSourceLastFailureTimes = new long[6];
        this.mLastEventTimes = new java.util.ArrayList<>();
        this.mLastEvents = new java.util.ArrayList<>();
        int readInt = parcel.readInt();
        if (readInt != 6 && readInt != 1) {
            android.util.Log.w("SyncStatusInfo", "Unknown version: " + readInt);
        }
        this.authorityId = parcel.readInt();
        this.totalStats.totalElapsedTime = parcel.readLong();
        this.totalStats.numSyncs = parcel.readInt();
        this.totalStats.numSourcePoll = parcel.readInt();
        this.totalStats.numSourceOther = parcel.readInt();
        this.totalStats.numSourceLocal = parcel.readInt();
        this.totalStats.numSourceUser = parcel.readInt();
        this.lastSuccessTime = parcel.readLong();
        this.lastSuccessSource = parcel.readInt();
        this.lastFailureTime = parcel.readLong();
        this.lastFailureSource = parcel.readInt();
        this.lastFailureMesg = parcel.readString();
        this.initialFailureTime = parcel.readLong();
        this.pending = parcel.readInt() != 0;
        this.initialize = parcel.readInt() != 0;
        if (readInt == 1) {
            this.periodicSyncTimes = null;
        } else {
            int readInt2 = parcel.readInt();
            if (readInt2 < 0) {
                this.periodicSyncTimes = null;
            } else {
                this.periodicSyncTimes = new java.util.ArrayList<>();
                for (int i = 0; i < readInt2; i++) {
                    this.periodicSyncTimes.add(java.lang.Long.valueOf(parcel.readLong()));
                }
            }
            if (readInt >= 3) {
                this.mLastEventTimes.clear();
                this.mLastEvents.clear();
                int readInt3 = parcel.readInt();
                for (int i2 = 0; i2 < readInt3; i2++) {
                    this.mLastEventTimes.add(java.lang.Long.valueOf(parcel.readLong()));
                    this.mLastEvents.add(parcel.readString());
                }
            }
        }
        if (readInt < 4) {
            this.totalStats.numSourcePeriodic = (((this.totalStats.numSyncs - this.totalStats.numSourceLocal) - this.totalStats.numSourcePoll) - this.totalStats.numSourceOther) - this.totalStats.numSourceUser;
            if (this.totalStats.numSourcePeriodic < 0) {
                this.totalStats.numSourcePeriodic = 0;
            }
        } else {
            this.totalStats.numSourcePeriodic = parcel.readInt();
        }
        if (readInt >= 5) {
            this.totalStats.numSourceFeed = parcel.readInt();
            this.totalStats.numFailures = parcel.readInt();
            this.totalStats.numCancels = parcel.readInt();
            this.lastTodayResetTime = parcel.readLong();
            this.todayStats.readFromParcel(parcel);
            this.yesterdayStats.readFromParcel(parcel);
        }
        if (readInt >= 6) {
            parcel.readLongArray(this.perSourceLastSuccessTimes);
            parcel.readLongArray(this.perSourceLastFailureTimes);
        }
    }

    public SyncStatusInfo(android.content.SyncStatusInfo syncStatusInfo) {
        this.totalStats = new android.content.SyncStatusInfo.Stats();
        this.todayStats = new android.content.SyncStatusInfo.Stats();
        this.yesterdayStats = new android.content.SyncStatusInfo.Stats();
        this.perSourceLastSuccessTimes = new long[6];
        this.perSourceLastFailureTimes = new long[6];
        this.mLastEventTimes = new java.util.ArrayList<>();
        this.mLastEvents = new java.util.ArrayList<>();
        this.authorityId = syncStatusInfo.authorityId;
        copyFrom(syncStatusInfo);
    }

    public SyncStatusInfo(int i, android.content.SyncStatusInfo syncStatusInfo) {
        this.totalStats = new android.content.SyncStatusInfo.Stats();
        this.todayStats = new android.content.SyncStatusInfo.Stats();
        this.yesterdayStats = new android.content.SyncStatusInfo.Stats();
        this.perSourceLastSuccessTimes = new long[6];
        this.perSourceLastFailureTimes = new long[6];
        this.mLastEventTimes = new java.util.ArrayList<>();
        this.mLastEvents = new java.util.ArrayList<>();
        this.authorityId = i;
        copyFrom(syncStatusInfo);
    }

    private void copyFrom(android.content.SyncStatusInfo syncStatusInfo) {
        syncStatusInfo.totalStats.copyTo(this.totalStats);
        syncStatusInfo.todayStats.copyTo(this.todayStats);
        syncStatusInfo.yesterdayStats.copyTo(this.yesterdayStats);
        this.lastTodayResetTime = syncStatusInfo.lastTodayResetTime;
        this.lastSuccessTime = syncStatusInfo.lastSuccessTime;
        this.lastSuccessSource = syncStatusInfo.lastSuccessSource;
        this.lastFailureTime = syncStatusInfo.lastFailureTime;
        this.lastFailureSource = syncStatusInfo.lastFailureSource;
        this.lastFailureMesg = syncStatusInfo.lastFailureMesg;
        this.initialFailureTime = syncStatusInfo.initialFailureTime;
        this.pending = syncStatusInfo.pending;
        this.initialize = syncStatusInfo.initialize;
        if (syncStatusInfo.periodicSyncTimes != null) {
            this.periodicSyncTimes = new java.util.ArrayList<>(syncStatusInfo.periodicSyncTimes);
        }
        this.mLastEventTimes.addAll(syncStatusInfo.mLastEventTimes);
        this.mLastEvents.addAll(syncStatusInfo.mLastEvents);
        copy(this.perSourceLastSuccessTimes, syncStatusInfo.perSourceLastSuccessTimes);
        copy(this.perSourceLastFailureTimes, syncStatusInfo.perSourceLastFailureTimes);
    }

    private static void copy(long[] jArr, long[] jArr2) {
        java.lang.System.arraycopy(jArr2, 0, jArr, 0, jArr.length);
    }

    public int getPeriodicSyncTimesSize() {
        if (this.periodicSyncTimes == null) {
            return 0;
        }
        return this.periodicSyncTimes.size();
    }

    public void addPeriodicSyncTime(long j) {
        this.periodicSyncTimes = com.android.internal.util.ArrayUtils.add(this.periodicSyncTimes, java.lang.Long.valueOf(j));
    }

    public void setPeriodicSyncTime(int i, long j) {
        ensurePeriodicSyncTimeSize(i);
        this.periodicSyncTimes.set(i, java.lang.Long.valueOf(j));
    }

    public long getPeriodicSyncTime(int i) {
        if (this.periodicSyncTimes != null && i < this.periodicSyncTimes.size()) {
            return this.periodicSyncTimes.get(i).longValue();
        }
        return 0L;
    }

    public void removePeriodicSyncTime(int i) {
        if (this.periodicSyncTimes != null && i < this.periodicSyncTimes.size()) {
            this.periodicSyncTimes.remove(i);
        }
    }

    public void populateLastEventsInformation(java.util.ArrayList<android.util.Pair<java.lang.Long, java.lang.String>> arrayList) {
        this.mLastEventTimes.clear();
        this.mLastEvents.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            android.util.Pair<java.lang.Long, java.lang.String> pair = arrayList.get(i);
            this.mLastEventTimes.add(pair.first);
            this.mLastEvents.add(pair.second);
        }
    }

    public void addEvent(java.lang.String str) {
        if (this.mLastEventTimes.size() >= 10) {
            this.mLastEventTimes.remove(9);
            this.mLastEvents.remove(9);
        }
        this.mLastEventTimes.add(0, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
        this.mLastEvents.add(0, str);
    }

    public int getEventCount() {
        return this.mLastEventTimes.size();
    }

    public long getEventTime(int i) {
        return this.mLastEventTimes.get(i).longValue();
    }

    public java.lang.String getEvent(int i) {
        return this.mLastEvents.get(i);
    }

    public void setLastSuccess(int i, long j) {
        this.lastSuccessTime = j;
        this.lastSuccessSource = i;
        this.lastFailureTime = 0L;
        this.lastFailureSource = -1;
        this.lastFailureMesg = null;
        this.initialFailureTime = 0L;
        if (i >= 0 && i < this.perSourceLastSuccessTimes.length) {
            this.perSourceLastSuccessTimes[i] = j;
        }
    }

    public void setLastFailure(int i, long j, java.lang.String str) {
        this.lastFailureTime = j;
        this.lastFailureSource = i;
        this.lastFailureMesg = str;
        if (this.initialFailureTime == 0) {
            this.initialFailureTime = j;
        }
        if (i >= 0 && i < this.perSourceLastFailureTimes.length) {
            this.perSourceLastFailureTimes[i] = j;
        }
    }

    private void ensurePeriodicSyncTimeSize(int i) {
        if (this.periodicSyncTimes == null) {
            this.periodicSyncTimes = new java.util.ArrayList<>(0);
        }
        int i2 = i + 1;
        if (this.periodicSyncTimes.size() < i2) {
            for (int size = this.periodicSyncTimes.size(); size < i2; size++) {
                this.periodicSyncTimes.add(0L);
            }
        }
    }

    public void maybeResetTodayStats(boolean z, boolean z2) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (!z2) {
            if (areSameDates(currentTimeMillis, this.lastTodayResetTime)) {
                return;
            }
            if (currentTimeMillis < this.lastTodayResetTime && !z) {
                return;
            }
        }
        this.lastTodayResetTime = currentTimeMillis;
        this.todayStats.copyTo(this.yesterdayStats);
        this.todayStats.clear();
    }

    private static boolean areSameDates(long j, long j2) {
        java.util.GregorianCalendar gregorianCalendar = new java.util.GregorianCalendar();
        java.util.GregorianCalendar gregorianCalendar2 = new java.util.GregorianCalendar();
        gregorianCalendar.setTimeInMillis(j);
        gregorianCalendar2.setTimeInMillis(j2);
        return gregorianCalendar.get(1) == gregorianCalendar2.get(1) && gregorianCalendar.get(6) == gregorianCalendar2.get(6);
    }
}
