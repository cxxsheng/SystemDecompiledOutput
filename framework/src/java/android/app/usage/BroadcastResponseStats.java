package android.app.usage;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class BroadcastResponseStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.BroadcastResponseStats> CREATOR = new android.os.Parcelable.Creator<android.app.usage.BroadcastResponseStats>() { // from class: android.app.usage.BroadcastResponseStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.BroadcastResponseStats createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.BroadcastResponseStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.BroadcastResponseStats[] newArray(int i) {
            return new android.app.usage.BroadcastResponseStats[i];
        }
    };
    private int mBroadcastsDispatchedCount;
    private final long mId;
    private int mNotificationsCancelledCount;
    private int mNotificationsPostedCount;
    private int mNotificationsUpdatedCount;
    private final java.lang.String mPackageName;

    public BroadcastResponseStats(java.lang.String str, long j) {
        this.mPackageName = str;
        this.mId = j;
    }

    private BroadcastResponseStats(android.os.Parcel parcel) {
        this.mPackageName = parcel.readString8();
        this.mId = parcel.readLong();
        this.mBroadcastsDispatchedCount = parcel.readInt();
        this.mNotificationsPostedCount = parcel.readInt();
        this.mNotificationsUpdatedCount = parcel.readInt();
        this.mNotificationsCancelledCount = parcel.readInt();
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public long getId() {
        return this.mId;
    }

    public int getBroadcastsDispatchedCount() {
        return this.mBroadcastsDispatchedCount;
    }

    public int getNotificationsPostedCount() {
        return this.mNotificationsPostedCount;
    }

    public int getNotificationsUpdatedCount() {
        return this.mNotificationsUpdatedCount;
    }

    public int getNotificationsCancelledCount() {
        return this.mNotificationsCancelledCount;
    }

    public void incrementBroadcastsDispatchedCount(int i) {
        this.mBroadcastsDispatchedCount += i;
    }

    public void incrementNotificationsPostedCount(int i) {
        this.mNotificationsPostedCount += i;
    }

    public void incrementNotificationsUpdatedCount(int i) {
        this.mNotificationsUpdatedCount += i;
    }

    public void incrementNotificationsCancelledCount(int i) {
        this.mNotificationsCancelledCount += i;
    }

    public void addCounts(android.app.usage.BroadcastResponseStats broadcastResponseStats) {
        incrementBroadcastsDispatchedCount(broadcastResponseStats.getBroadcastsDispatchedCount());
        incrementNotificationsPostedCount(broadcastResponseStats.getNotificationsPostedCount());
        incrementNotificationsUpdatedCount(broadcastResponseStats.getNotificationsUpdatedCount());
        incrementNotificationsCancelledCount(broadcastResponseStats.getNotificationsCancelledCount());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.app.usage.BroadcastResponseStats)) {
            return false;
        }
        android.app.usage.BroadcastResponseStats broadcastResponseStats = (android.app.usage.BroadcastResponseStats) obj;
        if (this.mBroadcastsDispatchedCount == broadcastResponseStats.mBroadcastsDispatchedCount && this.mNotificationsPostedCount == broadcastResponseStats.mNotificationsPostedCount && this.mNotificationsUpdatedCount == broadcastResponseStats.mNotificationsUpdatedCount && this.mNotificationsCancelledCount == broadcastResponseStats.mNotificationsCancelledCount && this.mId == broadcastResponseStats.mId && this.mPackageName.equals(broadcastResponseStats.mPackageName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPackageName, java.lang.Long.valueOf(this.mId), java.lang.Integer.valueOf(this.mBroadcastsDispatchedCount), java.lang.Integer.valueOf(this.mNotificationsPostedCount), java.lang.Integer.valueOf(this.mNotificationsUpdatedCount), java.lang.Integer.valueOf(this.mNotificationsCancelledCount));
    }

    public java.lang.String toString() {
        return "stats {package=" + this.mPackageName + ",id=" + this.mId + ",broadcastsSent=" + this.mBroadcastsDispatchedCount + ",notificationsPosted=" + this.mNotificationsPostedCount + ",notificationsUpdated=" + this.mNotificationsUpdatedCount + ",notificationsCancelled=" + this.mNotificationsCancelledCount + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mPackageName);
        parcel.writeLong(this.mId);
        parcel.writeInt(this.mBroadcastsDispatchedCount);
        parcel.writeInt(this.mNotificationsPostedCount);
        parcel.writeInt(this.mNotificationsUpdatedCount);
        parcel.writeInt(this.mNotificationsCancelledCount);
    }
}
