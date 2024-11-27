package android.service.notification;

/* loaded from: classes3.dex */
public final class NotifyingApp implements android.os.Parcelable, java.lang.Comparable<android.service.notification.NotifyingApp> {
    public static final android.os.Parcelable.Creator<android.service.notification.NotifyingApp> CREATOR = new android.os.Parcelable.Creator<android.service.notification.NotifyingApp>() { // from class: android.service.notification.NotifyingApp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.NotifyingApp createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.NotifyingApp(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.NotifyingApp[] newArray(int i) {
            return new android.service.notification.NotifyingApp[i];
        }
    };
    private long mLastNotified;
    private java.lang.String mPkg;
    private int mUserId;

    public NotifyingApp() {
    }

    protected NotifyingApp(android.os.Parcel parcel) {
        this.mUserId = parcel.readInt();
        this.mPkg = parcel.readString();
        this.mLastNotified = parcel.readLong();
    }

    public int getUserId() {
        return this.mUserId;
    }

    public android.service.notification.NotifyingApp setUserId(int i) {
        this.mUserId = i;
        return this;
    }

    public java.lang.String getPackage() {
        return this.mPkg;
    }

    public android.service.notification.NotifyingApp setPackage(java.lang.String str) {
        this.mPkg = str;
        return this;
    }

    public long getLastNotified() {
        return this.mLastNotified;
    }

    public android.service.notification.NotifyingApp setLastNotified(long j) {
        this.mLastNotified = j;
        return this;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUserId);
        parcel.writeString(this.mPkg);
        parcel.writeLong(this.mLastNotified);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.notification.NotifyingApp notifyingApp = (android.service.notification.NotifyingApp) obj;
        if (getUserId() == notifyingApp.getUserId() && getLastNotified() == notifyingApp.getLastNotified() && java.util.Objects.equals(this.mPkg, notifyingApp.mPkg)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(getUserId()), this.mPkg, java.lang.Long.valueOf(getLastNotified()));
    }

    @Override // java.lang.Comparable
    public int compareTo(android.service.notification.NotifyingApp notifyingApp) {
        if (getLastNotified() == notifyingApp.getLastNotified()) {
            if (getUserId() == notifyingApp.getUserId()) {
                return getPackage().compareTo(notifyingApp.getPackage());
            }
            return java.lang.Integer.compare(getUserId(), notifyingApp.getUserId());
        }
        return -java.lang.Long.compare(getLastNotified(), notifyingApp.getLastNotified());
    }

    public java.lang.String toString() {
        return "NotifyingApp{mUserId=" + this.mUserId + ", mPkg='" + this.mPkg + android.text.format.DateFormat.QUOTE + ", mLastNotified=" + this.mLastNotified + '}';
    }
}
