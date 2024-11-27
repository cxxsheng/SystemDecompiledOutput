package android.app.people;

/* loaded from: classes.dex */
public final class ConversationChannel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.people.ConversationChannel> CREATOR = new android.os.Parcelable.Creator<android.app.people.ConversationChannel>() { // from class: android.app.people.ConversationChannel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.people.ConversationChannel createFromParcel(android.os.Parcel parcel) {
            return new android.app.people.ConversationChannel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.people.ConversationChannel[] newArray(int i) {
            return new android.app.people.ConversationChannel[i];
        }
    };
    private boolean mHasActiveNotifications;
    private boolean mHasBirthdayToday;
    private long mLastEventTimestamp;
    private android.app.NotificationChannel mNotificationChannel;
    private android.app.NotificationChannelGroup mNotificationChannelGroup;
    private android.content.pm.ShortcutInfo mShortcutInfo;
    private java.util.List<android.app.people.ConversationStatus> mStatuses;
    private int mUid;

    public ConversationChannel(android.content.pm.ShortcutInfo shortcutInfo, int i, android.app.NotificationChannel notificationChannel, android.app.NotificationChannelGroup notificationChannelGroup, long j, boolean z) {
        this.mShortcutInfo = shortcutInfo;
        this.mUid = i;
        this.mNotificationChannel = notificationChannel;
        this.mNotificationChannelGroup = notificationChannelGroup;
        this.mLastEventTimestamp = j;
        this.mHasActiveNotifications = z;
    }

    public ConversationChannel(android.content.pm.ShortcutInfo shortcutInfo, int i, android.app.NotificationChannel notificationChannel, android.app.NotificationChannelGroup notificationChannelGroup, long j, boolean z, boolean z2, java.util.List<android.app.people.ConversationStatus> list) {
        this.mShortcutInfo = shortcutInfo;
        this.mUid = i;
        this.mNotificationChannel = notificationChannel;
        this.mNotificationChannelGroup = notificationChannelGroup;
        this.mLastEventTimestamp = j;
        this.mHasActiveNotifications = z;
        this.mHasBirthdayToday = z2;
        this.mStatuses = list;
    }

    public ConversationChannel(android.os.Parcel parcel) {
        this.mShortcutInfo = (android.content.pm.ShortcutInfo) parcel.readParcelable(android.content.pm.ShortcutInfo.class.getClassLoader(), android.content.pm.ShortcutInfo.class);
        this.mUid = parcel.readInt();
        this.mNotificationChannel = (android.app.NotificationChannel) parcel.readParcelable(android.app.NotificationChannel.class.getClassLoader(), android.app.NotificationChannel.class);
        this.mNotificationChannelGroup = (android.app.NotificationChannelGroup) parcel.readParcelable(android.app.NotificationChannelGroup.class.getClassLoader(), android.app.NotificationChannelGroup.class);
        this.mLastEventTimestamp = parcel.readLong();
        this.mHasActiveNotifications = parcel.readBoolean();
        this.mHasBirthdayToday = parcel.readBoolean();
        this.mStatuses = new java.util.ArrayList();
        parcel.readParcelableList(this.mStatuses, android.app.people.ConversationStatus.class.getClassLoader(), android.app.people.ConversationStatus.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mShortcutInfo, i);
        parcel.writeInt(this.mUid);
        parcel.writeParcelable(this.mNotificationChannel, i);
        parcel.writeParcelable(this.mNotificationChannelGroup, i);
        parcel.writeLong(this.mLastEventTimestamp);
        parcel.writeBoolean(this.mHasActiveNotifications);
        parcel.writeBoolean(this.mHasBirthdayToday);
        parcel.writeParcelableList(this.mStatuses, i);
    }

    public android.content.pm.ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public int getUid() {
        return this.mUid;
    }

    public android.app.NotificationChannel getNotificationChannel() {
        return this.mNotificationChannel;
    }

    public android.app.NotificationChannelGroup getNotificationChannelGroup() {
        return this.mNotificationChannelGroup;
    }

    public long getLastEventTimestamp() {
        return this.mLastEventTimestamp;
    }

    public boolean hasActiveNotifications() {
        return this.mHasActiveNotifications;
    }

    public boolean hasBirthdayToday() {
        return this.mHasBirthdayToday;
    }

    public java.util.List<android.app.people.ConversationStatus> getStatuses() {
        return this.mStatuses;
    }

    public java.lang.String toString() {
        return "ConversationChannel{mShortcutInfo=" + this.mShortcutInfo + ", mUid=" + this.mUid + ", mNotificationChannel=" + this.mNotificationChannel + ", mNotificationChannelGroup=" + this.mNotificationChannelGroup + ", mLastEventTimestamp=" + this.mLastEventTimestamp + ", mHasActiveNotifications=" + this.mHasActiveNotifications + ", mHasBirthdayToday=" + this.mHasBirthdayToday + ", mStatuses=" + this.mStatuses + '}';
    }
}
