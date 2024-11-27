package android.app.usage;

/* loaded from: classes.dex */
public final class UsageStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.UsageStats> CREATOR = new android.os.Parcelable.Creator<android.app.usage.UsageStats>() { // from class: android.app.usage.UsageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.UsageStats createFromParcel(android.os.Parcel parcel) {
            android.app.usage.UsageStats usageStats = new android.app.usage.UsageStats();
            usageStats.mPackageName = parcel.readString();
            usageStats.mBeginTimeStamp = parcel.readLong();
            usageStats.mEndTimeStamp = parcel.readLong();
            usageStats.mLastTimeUsed = parcel.readLong();
            usageStats.mLastTimeVisible = parcel.readLong();
            usageStats.mLastTimeComponentUsed = parcel.readLong();
            usageStats.mLastTimeForegroundServiceUsed = parcel.readLong();
            usageStats.mTotalTimeInForeground = parcel.readLong();
            usageStats.mTotalTimeVisible = parcel.readLong();
            usageStats.mTotalTimeForegroundServiceUsed = parcel.readLong();
            usageStats.mLaunchCount = parcel.readInt();
            usageStats.mAppLaunchCount = parcel.readInt();
            usageStats.mLastEvent = parcel.readInt();
            android.os.Bundle readBundle = parcel.readBundle();
            if (readBundle != null) {
                usageStats.mChooserCounts = new android.util.ArrayMap<>();
                for (java.lang.String str : readBundle.keySet()) {
                    if (!usageStats.mChooserCounts.containsKey(str)) {
                        usageStats.mChooserCounts.put(str, new android.util.ArrayMap<>());
                    }
                    android.os.Bundle bundle = readBundle.getBundle(str);
                    if (bundle != null) {
                        for (java.lang.String str2 : bundle.keySet()) {
                            int i = bundle.getInt(str2);
                            if (i > 0) {
                                usageStats.mChooserCounts.get(str).put(str2, java.lang.Integer.valueOf(i));
                            }
                        }
                    }
                }
            }
            readSparseIntArray(parcel, usageStats.mActivities);
            readBundleToEventMap(parcel.readBundle(), usageStats.mForegroundServices);
            return usageStats;
        }

        private void readSparseIntArray(android.os.Parcel parcel, android.util.SparseIntArray sparseIntArray) {
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                sparseIntArray.put(parcel.readInt(), parcel.readInt());
            }
        }

        private void readBundleToEventMap(android.os.Bundle bundle, android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) {
            if (bundle != null) {
                for (java.lang.String str : bundle.keySet()) {
                    arrayMap.put(str, java.lang.Integer.valueOf(bundle.getInt(str)));
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.UsageStats[] newArray(int i) {
            return new android.app.usage.UsageStats[i];
        }
    };
    public android.util.SparseIntArray mActivities;
    public int mAppLaunchCount;
    public long mBeginTimeStamp;
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Integer>> mChooserCounts;
    public android.util.SparseArray<android.util.SparseIntArray> mChooserCountsObfuscated;
    public long mEndTimeStamp;
    public android.util.ArrayMap<java.lang.String, java.lang.Integer> mForegroundServices;

    @java.lang.Deprecated
    public int mLastEvent;
    public long mLastTimeComponentUsed;
    public long mLastTimeForegroundServiceUsed;
    public long mLastTimeUsed;
    public long mLastTimeVisible;
    public int mLaunchCount;
    public java.lang.String mPackageName;
    public int mPackageToken;
    public long mTotalTimeForegroundServiceUsed;
    public long mTotalTimeInForeground;
    public long mTotalTimeVisible;

    public UsageStats() {
        this.mPackageToken = -1;
        this.mActivities = new android.util.SparseIntArray();
        this.mForegroundServices = new android.util.ArrayMap<>();
        this.mChooserCounts = new android.util.ArrayMap<>();
        this.mChooserCountsObfuscated = new android.util.SparseArray<>();
    }

    public UsageStats(android.app.usage.UsageStats usageStats) {
        this.mPackageToken = -1;
        this.mActivities = new android.util.SparseIntArray();
        this.mForegroundServices = new android.util.ArrayMap<>();
        this.mChooserCounts = new android.util.ArrayMap<>();
        this.mChooserCountsObfuscated = new android.util.SparseArray<>();
        this.mPackageName = usageStats.mPackageName;
        this.mBeginTimeStamp = usageStats.mBeginTimeStamp;
        this.mEndTimeStamp = usageStats.mEndTimeStamp;
        this.mLastTimeUsed = usageStats.mLastTimeUsed;
        this.mLastTimeVisible = usageStats.mLastTimeVisible;
        this.mLastTimeComponentUsed = usageStats.mLastTimeComponentUsed;
        this.mLastTimeForegroundServiceUsed = usageStats.mLastTimeForegroundServiceUsed;
        this.mTotalTimeInForeground = usageStats.mTotalTimeInForeground;
        this.mTotalTimeVisible = usageStats.mTotalTimeVisible;
        this.mTotalTimeForegroundServiceUsed = usageStats.mTotalTimeForegroundServiceUsed;
        this.mLaunchCount = usageStats.mLaunchCount;
        this.mAppLaunchCount = usageStats.mAppLaunchCount;
        this.mLastEvent = usageStats.mLastEvent;
        this.mActivities = usageStats.mActivities.m4838clone();
        this.mForegroundServices = new android.util.ArrayMap<>(usageStats.mForegroundServices);
        this.mChooserCounts = new android.util.ArrayMap<>(usageStats.mChooserCounts);
    }

    public android.app.usage.UsageStats getObfuscatedForInstantApp() {
        android.app.usage.UsageStats usageStats = new android.app.usage.UsageStats(this);
        usageStats.mPackageName = android.app.usage.UsageEvents.INSTANT_APP_PACKAGE_NAME;
        return usageStats;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public long getFirstTimeStamp() {
        return this.mBeginTimeStamp;
    }

    public long getLastTimeStamp() {
        return this.mEndTimeStamp;
    }

    public long getLastTimeUsed() {
        return this.mLastTimeUsed;
    }

    public long getLastTimeVisible() {
        return this.mLastTimeVisible;
    }

    public long getTotalTimeInForeground() {
        return this.mTotalTimeInForeground;
    }

    public long getTotalTimeVisible() {
        return this.mTotalTimeVisible;
    }

    public long getLastTimeForegroundServiceUsed() {
        return this.mLastTimeForegroundServiceUsed;
    }

    public long getTotalTimeForegroundServiceUsed() {
        return this.mTotalTimeForegroundServiceUsed;
    }

    @android.annotation.SystemApi
    public long getLastTimeAnyComponentUsed() {
        return this.mLastTimeComponentUsed;
    }

    public long getLastTimePackageUsed() {
        return java.lang.Math.max(this.mLastTimeUsed, java.lang.Math.max(this.mLastTimeVisible, java.lang.Math.max(this.mLastTimeForegroundServiceUsed, this.mLastTimeComponentUsed)));
    }

    @android.annotation.SystemApi
    public int getAppLaunchCount() {
        return this.mAppLaunchCount;
    }

    private void mergeEventMap(android.util.SparseIntArray sparseIntArray, android.util.SparseIntArray sparseIntArray2) {
        int size = sparseIntArray2.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseIntArray2.keyAt(i);
            int valueAt = sparseIntArray2.valueAt(i);
            int indexOfKey = sparseIntArray.indexOfKey(keyAt);
            if (indexOfKey >= 0) {
                sparseIntArray.put(keyAt, java.lang.Math.max(sparseIntArray.valueAt(indexOfKey), valueAt));
            } else {
                sparseIntArray.put(keyAt, valueAt);
            }
        }
    }

    private void mergeEventMap(android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap, android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap2) {
        int size = arrayMap2.size();
        for (int i = 0; i < size; i++) {
            java.lang.String keyAt = arrayMap2.keyAt(i);
            java.lang.Integer valueAt = arrayMap2.valueAt(i);
            if (arrayMap.containsKey(keyAt)) {
                arrayMap.put(keyAt, java.lang.Integer.valueOf(java.lang.Math.max(arrayMap.get(keyAt).intValue(), valueAt.intValue())));
            } else {
                arrayMap.put(keyAt, valueAt);
            }
        }
    }

    public void add(android.app.usage.UsageStats usageStats) {
        if (!this.mPackageName.equals(usageStats.mPackageName)) {
            throw new java.lang.IllegalArgumentException("Can't merge UsageStats for package '" + this.mPackageName + "' with UsageStats for package '" + usageStats.mPackageName + "'.");
        }
        if (usageStats.mBeginTimeStamp > this.mBeginTimeStamp) {
            mergeEventMap(this.mActivities, usageStats.mActivities);
            mergeEventMap(this.mForegroundServices, usageStats.mForegroundServices);
            this.mLastTimeUsed = java.lang.Math.max(this.mLastTimeUsed, usageStats.mLastTimeUsed);
            this.mLastTimeVisible = java.lang.Math.max(this.mLastTimeVisible, usageStats.mLastTimeVisible);
            this.mLastTimeComponentUsed = java.lang.Math.max(this.mLastTimeComponentUsed, usageStats.mLastTimeComponentUsed);
            this.mLastTimeForegroundServiceUsed = java.lang.Math.max(this.mLastTimeForegroundServiceUsed, usageStats.mLastTimeForegroundServiceUsed);
        }
        this.mBeginTimeStamp = java.lang.Math.min(this.mBeginTimeStamp, usageStats.mBeginTimeStamp);
        this.mEndTimeStamp = java.lang.Math.max(this.mEndTimeStamp, usageStats.mEndTimeStamp);
        this.mTotalTimeInForeground += usageStats.mTotalTimeInForeground;
        this.mTotalTimeVisible += usageStats.mTotalTimeVisible;
        this.mTotalTimeForegroundServiceUsed += usageStats.mTotalTimeForegroundServiceUsed;
        this.mLaunchCount += usageStats.mLaunchCount;
        this.mAppLaunchCount += usageStats.mAppLaunchCount;
        if (this.mChooserCounts == null) {
            this.mChooserCounts = usageStats.mChooserCounts;
            return;
        }
        if (usageStats.mChooserCounts != null) {
            int size = usageStats.mChooserCounts.size();
            for (int i = 0; i < size; i++) {
                java.lang.String keyAt = usageStats.mChooserCounts.keyAt(i);
                android.util.ArrayMap<java.lang.String, java.lang.Integer> valueAt = usageStats.mChooserCounts.valueAt(i);
                if (!this.mChooserCounts.containsKey(keyAt) || this.mChooserCounts.get(keyAt) == null) {
                    this.mChooserCounts.put(keyAt, valueAt);
                } else {
                    int size2 = valueAt.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        java.lang.String keyAt2 = valueAt.keyAt(i2);
                        this.mChooserCounts.get(keyAt).put(keyAt2, java.lang.Integer.valueOf(this.mChooserCounts.get(keyAt).getOrDefault(keyAt2, 0).intValue() + valueAt.valueAt(i2).intValue()));
                    }
                }
            }
        }
    }

    private boolean hasForegroundActivity() {
        int size = this.mActivities.size();
        for (int i = 0; i < size; i++) {
            if (this.mActivities.valueAt(i) == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean hasVisibleActivity() {
        int size = this.mActivities.size();
        for (int i = 0; i < size; i++) {
            int valueAt = this.mActivities.valueAt(i);
            if (valueAt == 1 || valueAt == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean anyForegroundServiceStarted() {
        return !this.mForegroundServices.isEmpty();
    }

    private void incrementTimeUsed(long j) {
        if (j > this.mLastTimeUsed) {
            this.mTotalTimeInForeground += j - this.mLastTimeUsed;
            this.mLastTimeUsed = j;
        }
    }

    private void incrementTimeVisible(long j) {
        if (j > this.mLastTimeVisible) {
            this.mTotalTimeVisible += j - this.mLastTimeVisible;
            this.mLastTimeVisible = j;
        }
    }

    private void incrementServiceTimeUsed(long j) {
        if (j > this.mLastTimeForegroundServiceUsed) {
            this.mTotalTimeForegroundServiceUsed += j - this.mLastTimeForegroundServiceUsed;
            this.mLastTimeForegroundServiceUsed = j;
        }
    }

    private void updateActivity(java.lang.String str, long j, int i, int i2) {
        if (i != 1 && i != 2 && i != 23 && i != 24) {
        }
        int indexOfKey = this.mActivities.indexOfKey(i2);
        if (indexOfKey >= 0) {
            switch (this.mActivities.valueAt(indexOfKey)) {
                case 1:
                    incrementTimeUsed(j);
                    incrementTimeVisible(j);
                    break;
                case 2:
                    incrementTimeVisible(j);
                    break;
            }
        }
        switch (i) {
            case 1:
                if (!hasVisibleActivity()) {
                    this.mLastTimeUsed = j;
                    this.mLastTimeVisible = j;
                } else if (!hasForegroundActivity()) {
                    this.mLastTimeUsed = j;
                }
                this.mActivities.put(i2, i);
                break;
            case 2:
                if (!hasVisibleActivity()) {
                    this.mLastTimeVisible = j;
                }
                this.mActivities.put(i2, i);
                break;
            case 23:
            case 24:
                this.mActivities.delete(i2);
                break;
        }
    }

    private void updateForegroundService(java.lang.String str, long j, int i) {
        if (i != 20 && i != 19) {
        }
        java.lang.Integer num = this.mForegroundServices.get(str);
        if (num != null) {
            switch (num.intValue()) {
                case 19:
                case 21:
                    incrementServiceTimeUsed(j);
                    break;
            }
        }
        switch (i) {
            case 19:
                if (!anyForegroundServiceStarted()) {
                    this.mLastTimeForegroundServiceUsed = j;
                }
                this.mForegroundServices.put(str, java.lang.Integer.valueOf(i));
                break;
            case 20:
                this.mForegroundServices.remove(str);
                break;
        }
    }

    public void update(java.lang.String str, long j, int i, int i2) {
        switch (i) {
            case 1:
            case 2:
            case 23:
            case 24:
                updateActivity(str, j, i, i2);
                break;
            case 3:
                if (hasForegroundActivity()) {
                    incrementTimeUsed(j);
                }
                if (hasVisibleActivity()) {
                    incrementTimeVisible(j);
                    break;
                }
                break;
            case 7:
                if (hasForegroundActivity()) {
                    incrementTimeUsed(j);
                } else {
                    this.mLastTimeUsed = j;
                }
                if (hasVisibleActivity()) {
                    incrementTimeVisible(j);
                    break;
                } else {
                    this.mLastTimeVisible = j;
                    break;
                }
            case 19:
            case 20:
                updateForegroundService(str, j, i);
                break;
            case 21:
                this.mLastTimeForegroundServiceUsed = j;
                this.mForegroundServices.put(str, java.lang.Integer.valueOf(i));
                break;
            case 22:
                if (anyForegroundServiceStarted()) {
                    incrementServiceTimeUsed(j);
                    break;
                }
                break;
            case 25:
            case 26:
                if (hasForegroundActivity()) {
                    incrementTimeUsed(j);
                }
                if (hasVisibleActivity()) {
                    incrementTimeVisible(j);
                }
                if (anyForegroundServiceStarted()) {
                    incrementServiceTimeUsed(j);
                    break;
                }
                break;
            case 31:
                this.mLastTimeComponentUsed = j;
                break;
        }
        this.mEndTimeStamp = j;
        if (i == 1) {
            this.mLaunchCount++;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mBeginTimeStamp);
        parcel.writeLong(this.mEndTimeStamp);
        parcel.writeLong(this.mLastTimeUsed);
        parcel.writeLong(this.mLastTimeVisible);
        parcel.writeLong(this.mLastTimeComponentUsed);
        parcel.writeLong(this.mLastTimeForegroundServiceUsed);
        parcel.writeLong(this.mTotalTimeInForeground);
        parcel.writeLong(this.mTotalTimeVisible);
        parcel.writeLong(this.mTotalTimeForegroundServiceUsed);
        parcel.writeInt(this.mLaunchCount);
        parcel.writeInt(this.mAppLaunchCount);
        parcel.writeInt(this.mLastEvent);
        android.os.Bundle bundle = new android.os.Bundle();
        if (this.mChooserCounts != null) {
            int size = this.mChooserCounts.size();
            for (int i2 = 0; i2 < size; i2++) {
                java.lang.String keyAt = this.mChooserCounts.keyAt(i2);
                android.util.ArrayMap<java.lang.String, java.lang.Integer> valueAt = this.mChooserCounts.valueAt(i2);
                android.os.Bundle bundle2 = new android.os.Bundle();
                int size2 = valueAt.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    bundle2.putInt(valueAt.keyAt(i3), valueAt.valueAt(i3).intValue());
                }
                bundle.putBundle(keyAt, bundle2);
            }
        }
        parcel.writeBundle(bundle);
        writeSparseIntArray(parcel, this.mActivities);
        parcel.writeBundle(eventMapToBundle(this.mForegroundServices));
    }

    private void writeSparseIntArray(android.os.Parcel parcel, android.util.SparseIntArray sparseIntArray) {
        int size = sparseIntArray.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeInt(sparseIntArray.keyAt(i));
            parcel.writeInt(sparseIntArray.valueAt(i));
        }
    }

    private android.os.Bundle eventMapToBundle(android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) {
        android.os.Bundle bundle = new android.os.Bundle();
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            bundle.putInt(arrayMap.keyAt(i), arrayMap.valueAt(i).intValue());
        }
        return bundle;
    }

    public static final class Builder {
        private final android.app.usage.UsageStats mUsageStats = new android.app.usage.UsageStats();

        public android.app.usage.UsageStats build() {
            return this.mUsageStats;
        }

        public android.app.usage.UsageStats.Builder setPackageName(java.lang.String str) {
            this.mUsageStats.mPackageName = str;
            return this;
        }

        public android.app.usage.UsageStats.Builder setFirstTimeStamp(long j) {
            this.mUsageStats.mBeginTimeStamp = j;
            return this;
        }

        public android.app.usage.UsageStats.Builder setLastTimeStamp(long j) {
            this.mUsageStats.mEndTimeStamp = j;
            return this;
        }

        public android.app.usage.UsageStats.Builder setTotalTimeInForeground(long j) {
            this.mUsageStats.mTotalTimeInForeground = j;
            return this;
        }

        public android.app.usage.UsageStats.Builder setLastTimeUsed(long j) {
            this.mUsageStats.mLastTimeUsed = j;
            return this;
        }
    }
}
