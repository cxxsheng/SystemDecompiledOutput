package android.service.contentcapture;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ActivityEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.contentcapture.ActivityEvent> CREATOR = new android.os.Parcelable.Creator<android.service.contentcapture.ActivityEvent>() { // from class: android.service.contentcapture.ActivityEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.contentcapture.ActivityEvent createFromParcel(android.os.Parcel parcel) {
            return new android.service.contentcapture.ActivityEvent((android.app.assist.ActivityId) parcel.readParcelable(null, android.app.assist.ActivityId.class), (android.content.ComponentName) parcel.readParcelable(null, android.content.ComponentName.class), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.contentcapture.ActivityEvent[] newArray(int i) {
            return new android.service.contentcapture.ActivityEvent[i];
        }
    };
    public static final int TYPE_ACTIVITY_DESTROYED = 24;
    public static final int TYPE_ACTIVITY_PAUSED = 2;
    public static final int TYPE_ACTIVITY_RESUMED = 1;
    public static final int TYPE_ACTIVITY_STARTED = 10000;
    public static final int TYPE_ACTIVITY_STOPPED = 23;
    private final android.app.assist.ActivityId mActivityId;
    private final android.content.ComponentName mComponentName;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActivityEventType {
    }

    public ActivityEvent(android.app.assist.ActivityId activityId, android.content.ComponentName componentName, int i) {
        this.mActivityId = activityId;
        this.mComponentName = componentName;
        this.mType = i;
    }

    public android.app.assist.ActivityId getActivityId() {
        return this.mActivityId;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public int getEventType() {
        return this.mType;
    }

    public static java.lang.String getTypeAsString(int i) {
        switch (i) {
            case 1:
                return "ACTIVITY_RESUMED";
            case 2:
                return "ACTIVITY_PAUSED";
            case 23:
                return "ACTIVITY_STOPPED";
            case 24:
                return "ACTIVITY_DESTROYED";
            case 10000:
                return "ACTIVITY_STARTED";
            default:
                return "UKNOWN_TYPE: " + i;
        }
    }

    public java.lang.String toString() {
        return "ActivityEvent[" + this.mComponentName.toShortString() + ", ActivityId: " + this.mActivityId + "]:" + getTypeAsString(this.mType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mComponentName, i);
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mActivityId, i);
    }
}
