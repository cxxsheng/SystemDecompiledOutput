package android.app.assist;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ActivityId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.assist.ActivityId> CREATOR = new android.os.Parcelable.Creator<android.app.assist.ActivityId>() { // from class: android.app.assist.ActivityId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.assist.ActivityId createFromParcel(android.os.Parcel parcel) {
            return new android.app.assist.ActivityId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.assist.ActivityId[] newArray(int i) {
            return new android.app.assist.ActivityId[i];
        }
    };
    private final android.os.IBinder mActivityId;
    private final int mTaskId;

    public ActivityId(int i, android.os.IBinder iBinder) {
        this.mTaskId = i;
        this.mActivityId = iBinder;
    }

    public ActivityId(android.os.Parcel parcel) {
        this.mTaskId = parcel.readInt();
        this.mActivityId = parcel.readStrongBinder();
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public android.os.IBinder getToken() {
        return this.mActivityId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeStrongBinder(this.mActivityId);
    }

    public java.lang.String toString() {
        return "ActivityId { taskId = " + this.mTaskId + ", activityId = " + this.mActivityId + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.assist.ActivityId activityId = (android.app.assist.ActivityId) obj;
        if (this.mTaskId != activityId.mTaskId) {
            return false;
        }
        if (this.mActivityId != null) {
            return this.mActivityId.equals(activityId.mActivityId);
        }
        if (activityId.mActivityId == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.mTaskId * 31) + (this.mActivityId != null ? this.mActivityId.hashCode() : 0);
    }
}
