package android.app.usage;

/* loaded from: classes.dex */
public final class EventStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.EventStats> CREATOR = new android.os.Parcelable.Creator<android.app.usage.EventStats>() { // from class: android.app.usage.EventStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.EventStats createFromParcel(android.os.Parcel parcel) {
            android.app.usage.EventStats eventStats = new android.app.usage.EventStats();
            eventStats.mEventType = parcel.readInt();
            eventStats.mBeginTimeStamp = parcel.readLong();
            eventStats.mEndTimeStamp = parcel.readLong();
            eventStats.mLastEventTime = parcel.readLong();
            eventStats.mTotalTime = parcel.readLong();
            eventStats.mCount = parcel.readInt();
            return eventStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.EventStats[] newArray(int i) {
            return new android.app.usage.EventStats[i];
        }
    };
    public long mBeginTimeStamp;
    public int mCount;
    public long mEndTimeStamp;
    public int mEventType;
    public long mLastEventTime;
    public long mTotalTime;

    public EventStats() {
    }

    public EventStats(android.app.usage.EventStats eventStats) {
        this.mEventType = eventStats.mEventType;
        this.mBeginTimeStamp = eventStats.mBeginTimeStamp;
        this.mEndTimeStamp = eventStats.mEndTimeStamp;
        this.mLastEventTime = eventStats.mLastEventTime;
        this.mTotalTime = eventStats.mTotalTime;
        this.mCount = eventStats.mCount;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getLastEventTime() {
        return this.mLastEventTime;
    }

    public int getCount() {
        return this.mCount;
    }

    public long getTotalTime() {
        return this.mTotalTime;
    }

    public void add(android.app.usage.EventStats eventStats) {
        if (this.mEventType != eventStats.mEventType) {
            throw new java.lang.IllegalArgumentException("Can't merge EventStats for event #" + this.mEventType + " with EventStats for event #" + eventStats.mEventType);
        }
        if (eventStats.mBeginTimeStamp > this.mBeginTimeStamp) {
            this.mLastEventTime = java.lang.Math.max(this.mLastEventTime, eventStats.mLastEventTime);
        }
        this.mBeginTimeStamp = java.lang.Math.min(this.mBeginTimeStamp, eventStats.mBeginTimeStamp);
        this.mEndTimeStamp = java.lang.Math.max(this.mEndTimeStamp, eventStats.mEndTimeStamp);
        this.mTotalTime += eventStats.mTotalTime;
        this.mCount += eventStats.mCount;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mEventType);
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastEventTime);
        parcel.writeLong(this.mTotalTime);
        parcel.writeInt(this.mCount);
    }
}
