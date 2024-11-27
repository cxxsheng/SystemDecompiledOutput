package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public class NotificationVisibility implements android.os.Parcelable {
    private static final int MAX_POOL_SIZE = 25;
    private static final java.lang.String TAG = "NoViz";
    public int count;
    int id;
    public java.lang.String key;
    public com.android.internal.statusbar.NotificationVisibility.NotificationLocation location;
    public int rank;
    public boolean visible;
    private static int sNexrId = 0;
    public static final android.os.Parcelable.Creator<com.android.internal.statusbar.NotificationVisibility> CREATOR = new android.os.Parcelable.Creator<com.android.internal.statusbar.NotificationVisibility>() { // from class: com.android.internal.statusbar.NotificationVisibility.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.statusbar.NotificationVisibility createFromParcel(android.os.Parcel parcel) {
            return com.android.internal.statusbar.NotificationVisibility.obtain(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.statusbar.NotificationVisibility[] newArray(int i) {
            return new com.android.internal.statusbar.NotificationVisibility[i];
        }
    };

    public enum NotificationLocation {
        LOCATION_UNKNOWN(0),
        LOCATION_FIRST_HEADS_UP(1),
        LOCATION_HIDDEN_TOP(2),
        LOCATION_MAIN_AREA(3),
        LOCATION_BOTTOM_STACK_PEEKING(4),
        LOCATION_BOTTOM_STACK_HIDDEN(5),
        LOCATION_GONE(6);

        private final int mMetricsEventNotificationLocation;

        NotificationLocation(int i) {
            this.mMetricsEventNotificationLocation = i;
        }

        public int toMetricsEventEnum() {
            return this.mMetricsEventNotificationLocation;
        }
    }

    private NotificationVisibility() {
        this.visible = true;
        int i = sNexrId;
        sNexrId = i + 1;
        this.id = i;
    }

    private NotificationVisibility(java.lang.String str, int i, int i2, boolean z, com.android.internal.statusbar.NotificationVisibility.NotificationLocation notificationLocation) {
        this();
        this.key = str;
        this.rank = i;
        this.count = i2;
        this.visible = z;
        this.location = notificationLocation;
    }

    public java.lang.String toString() {
        return "NotificationVisibility(id=" + this.id + " key=" + this.key + " rank=" + this.rank + " count=" + this.count + (this.visible ? " visible" : "") + " location=" + this.location.name() + " )";
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public com.android.internal.statusbar.NotificationVisibility m7030clone() {
        return obtain(this.key, this.rank, this.count, this.visible, this.location);
    }

    public int hashCode() {
        if (this.key == null) {
            return 0;
        }
        return this.key.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.statusbar.NotificationVisibility)) {
            return false;
        }
        com.android.internal.statusbar.NotificationVisibility notificationVisibility = (com.android.internal.statusbar.NotificationVisibility) obj;
        return (this.key == null && notificationVisibility.key == null) || this.key.equals(notificationVisibility.key);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.key);
        parcel.writeInt(this.rank);
        parcel.writeInt(this.count);
        parcel.writeInt(this.visible ? 1 : 0);
        parcel.writeString(this.location.name());
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.key = parcel.readString();
        this.rank = parcel.readInt();
        this.count = parcel.readInt();
        this.visible = parcel.readInt() != 0;
        this.location = com.android.internal.statusbar.NotificationVisibility.NotificationLocation.valueOf(parcel.readString());
    }

    public static com.android.internal.statusbar.NotificationVisibility obtain(java.lang.String str, int i, int i2, boolean z) {
        return obtain(str, i, i2, z, com.android.internal.statusbar.NotificationVisibility.NotificationLocation.LOCATION_UNKNOWN);
    }

    public static com.android.internal.statusbar.NotificationVisibility obtain(java.lang.String str, int i, int i2, boolean z, com.android.internal.statusbar.NotificationVisibility.NotificationLocation notificationLocation) {
        com.android.internal.statusbar.NotificationVisibility obtain = obtain();
        obtain.key = str;
        obtain.rank = i;
        obtain.count = i2;
        obtain.visible = z;
        obtain.location = notificationLocation;
        return obtain;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.statusbar.NotificationVisibility obtain(android.os.Parcel parcel) {
        com.android.internal.statusbar.NotificationVisibility obtain = obtain();
        obtain.readFromParcel(parcel);
        return obtain;
    }

    private static com.android.internal.statusbar.NotificationVisibility obtain() {
        return new com.android.internal.statusbar.NotificationVisibility();
    }

    public void recycle() {
    }
}
