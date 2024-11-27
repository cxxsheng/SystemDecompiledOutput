package android.hardware.location;

/* loaded from: classes2.dex */
public class ActivityChangedEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.ActivityChangedEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.ActivityChangedEvent>() { // from class: android.hardware.location.ActivityChangedEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.ActivityChangedEvent createFromParcel(android.os.Parcel parcel) {
            android.hardware.location.ActivityRecognitionEvent[] activityRecognitionEventArr = new android.hardware.location.ActivityRecognitionEvent[parcel.readInt()];
            parcel.readTypedArray(activityRecognitionEventArr, android.hardware.location.ActivityRecognitionEvent.CREATOR);
            return new android.hardware.location.ActivityChangedEvent(activityRecognitionEventArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.ActivityChangedEvent[] newArray(int i) {
            return new android.hardware.location.ActivityChangedEvent[i];
        }
    };
    private final java.util.List<android.hardware.location.ActivityRecognitionEvent> mActivityRecognitionEvents;

    public ActivityChangedEvent(android.hardware.location.ActivityRecognitionEvent[] activityRecognitionEventArr) {
        if (activityRecognitionEventArr == null) {
            throw new java.security.InvalidParameterException("Parameter 'activityRecognitionEvents' must not be null.");
        }
        this.mActivityRecognitionEvents = java.util.Arrays.asList(activityRecognitionEventArr);
    }

    public java.lang.Iterable<android.hardware.location.ActivityRecognitionEvent> getActivityRecognitionEvents() {
        return this.mActivityRecognitionEvents;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.hardware.location.ActivityRecognitionEvent[] activityRecognitionEventArr = (android.hardware.location.ActivityRecognitionEvent[]) this.mActivityRecognitionEvents.toArray(new android.hardware.location.ActivityRecognitionEvent[0]);
        parcel.writeInt(activityRecognitionEventArr.length);
        parcel.writeTypedArray(activityRecognitionEventArr, i);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("[ ActivityChangedEvent:");
        for (android.hardware.location.ActivityRecognitionEvent activityRecognitionEvent : this.mActivityRecognitionEvents) {
            sb.append("\n    ");
            sb.append(activityRecognitionEvent.toString());
        }
        sb.append("\n]");
        return sb.toString();
    }
}
