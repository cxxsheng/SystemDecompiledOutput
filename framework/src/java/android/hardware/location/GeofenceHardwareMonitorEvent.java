package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class GeofenceHardwareMonitorEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.GeofenceHardwareMonitorEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.GeofenceHardwareMonitorEvent>() { // from class: android.hardware.location.GeofenceHardwareMonitorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.GeofenceHardwareMonitorEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.GeofenceHardwareMonitorEvent(parcel.readInt(), parcel.readInt(), parcel.readInt(), (android.location.Location) parcel.readParcelable(android.hardware.location.GeofenceHardwareMonitorEvent.class.getClassLoader(), android.location.Location.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.GeofenceHardwareMonitorEvent[] newArray(int i) {
            return new android.hardware.location.GeofenceHardwareMonitorEvent[i];
        }
    };
    private final android.location.Location mLocation;
    private final int mMonitoringStatus;
    private final int mMonitoringType;
    private final int mSourceTechnologies;

    public GeofenceHardwareMonitorEvent(int i, int i2, int i3, android.location.Location location) {
        this.mMonitoringType = i;
        this.mMonitoringStatus = i2;
        this.mSourceTechnologies = i3;
        this.mLocation = location;
    }

    public int getMonitoringType() {
        return this.mMonitoringType;
    }

    public int getMonitoringStatus() {
        return this.mMonitoringStatus;
    }

    public int getSourceTechnologies() {
        return this.mSourceTechnologies;
    }

    public android.location.Location getLocation() {
        return this.mLocation;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mMonitoringType);
        parcel.writeInt(this.mMonitoringStatus);
        parcel.writeInt(this.mSourceTechnologies);
        parcel.writeParcelable(this.mLocation, i);
    }

    public java.lang.String toString() {
        return java.lang.String.format("GeofenceHardwareMonitorEvent: type=%d, status=%d, sources=%d, location=%s", java.lang.Integer.valueOf(this.mMonitoringType), java.lang.Integer.valueOf(this.mMonitoringStatus), java.lang.Integer.valueOf(this.mSourceTechnologies), this.mLocation);
    }
}
