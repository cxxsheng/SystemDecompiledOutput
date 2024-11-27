package android.app.usage;

/* loaded from: classes.dex */
public final class ConfigurationStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.ConfigurationStats> CREATOR = new android.os.Parcelable.Creator<android.app.usage.ConfigurationStats>() { // from class: android.app.usage.ConfigurationStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.ConfigurationStats createFromParcel(android.os.Parcel parcel) {
            android.app.usage.ConfigurationStats configurationStats = new android.app.usage.ConfigurationStats();
            if (parcel.readInt() != 0) {
                configurationStats.mConfiguration = android.content.res.Configuration.CREATOR.createFromParcel(parcel);
            }
            configurationStats.mBeginTimeStamp = parcel.readLong();
            configurationStats.mEndTimeStamp = parcel.readLong();
            configurationStats.mLastTimeActive = parcel.readLong();
            configurationStats.mTotalTimeActive = parcel.readLong();
            configurationStats.mActivationCount = parcel.readInt();
            return configurationStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.ConfigurationStats[] newArray(int i) {
            return new android.app.usage.ConfigurationStats[i];
        }
    };
    public int mActivationCount;
    public long mBeginTimeStamp;
    public android.content.res.Configuration mConfiguration;
    public long mEndTimeStamp;
    public long mLastTimeActive;
    public long mTotalTimeActive;

    public ConfigurationStats() {
    }

    public ConfigurationStats(android.app.usage.ConfigurationStats configurationStats) {
        this.mConfiguration = configurationStats.mConfiguration;
        this.mBeginTimeStamp = configurationStats.mBeginTimeStamp;
        this.mEndTimeStamp = configurationStats.mEndTimeStamp;
        this.mLastTimeActive = configurationStats.mLastTimeActive;
        this.mTotalTimeActive = configurationStats.mTotalTimeActive;
        this.mActivationCount = configurationStats.mActivationCount;
    }

    public android.content.res.Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getLastTimeActive() {
        return this.mLastTimeActive;
    }

    public long getTotalTimeActive() {
        return this.mTotalTimeActive;
    }

    public int getActivationCount() {
        return this.mActivationCount;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mConfiguration != null) {
            parcel.writeInt(1);
            this.mConfiguration.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastTimeActive);
        parcel.writeLong(this.mTotalTimeActive);
        parcel.writeInt(this.mActivationCount);
    }
}
