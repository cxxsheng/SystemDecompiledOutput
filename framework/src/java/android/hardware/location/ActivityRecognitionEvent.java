package android.hardware.location;

/* loaded from: classes2.dex */
public class ActivityRecognitionEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.ActivityRecognitionEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.ActivityRecognitionEvent>() { // from class: android.hardware.location.ActivityRecognitionEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.ActivityRecognitionEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.ActivityRecognitionEvent(parcel.readString(), parcel.readInt(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.ActivityRecognitionEvent[] newArray(int i) {
            return new android.hardware.location.ActivityRecognitionEvent[i];
        }
    };
    private final java.lang.String mActivity;
    private final int mEventType;
    private final long mTimestampNs;

    public ActivityRecognitionEvent(java.lang.String str, int i, long j) {
        this.mActivity = str;
        this.mEventType = i;
        this.mTimestampNs = j;
    }

    public java.lang.String getActivity() {
        return this.mActivity;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public long getTimestampNs() {
        return this.mTimestampNs;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mActivity);
        parcel.writeInt(this.mEventType);
        parcel.writeLong(this.mTimestampNs);
    }

    public java.lang.String toString() {
        return java.lang.String.format("Activity='%s', EventType=%s, TimestampNs=%s", this.mActivity, java.lang.Integer.valueOf(this.mEventType), java.lang.Long.valueOf(this.mTimestampNs));
    }
}
