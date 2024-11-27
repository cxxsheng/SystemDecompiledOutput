package android.hardware.location;

/* loaded from: classes2.dex */
public final class GeofenceHardwareRequestParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.GeofenceHardwareRequestParcelable> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.GeofenceHardwareRequestParcelable>() { // from class: android.hardware.location.GeofenceHardwareRequestParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.GeofenceHardwareRequestParcelable createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt != 0) {
                throw new android.os.BadParcelableException("Invalid Geofence type: " + readInt);
            }
            android.hardware.location.GeofenceHardwareRequest createCircularGeofence = android.hardware.location.GeofenceHardwareRequest.createCircularGeofence(parcel.readDouble(), parcel.readDouble(), parcel.readDouble());
            createCircularGeofence.setLastTransition(parcel.readInt());
            createCircularGeofence.setMonitorTransitions(parcel.readInt());
            createCircularGeofence.setUnknownTimer(parcel.readInt());
            createCircularGeofence.setNotificationResponsiveness(parcel.readInt());
            createCircularGeofence.setSourceTechnologies(parcel.readInt());
            return new android.hardware.location.GeofenceHardwareRequestParcelable(parcel.readInt(), createCircularGeofence);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.GeofenceHardwareRequestParcelable[] newArray(int i) {
            return new android.hardware.location.GeofenceHardwareRequestParcelable[i];
        }
    };
    private int mId;
    private android.hardware.location.GeofenceHardwareRequest mRequest;

    public GeofenceHardwareRequestParcelable(int i, android.hardware.location.GeofenceHardwareRequest geofenceHardwareRequest) {
        this.mId = i;
        this.mRequest = geofenceHardwareRequest;
    }

    public int getId() {
        return this.mId;
    }

    public double getLatitude() {
        return this.mRequest.getLatitude();
    }

    public double getLongitude() {
        return this.mRequest.getLongitude();
    }

    public double getRadius() {
        return this.mRequest.getRadius();
    }

    public int getMonitorTransitions() {
        return this.mRequest.getMonitorTransitions();
    }

    public int getUnknownTimer() {
        return this.mRequest.getUnknownTimer();
    }

    public int getNotificationResponsiveness() {
        return this.mRequest.getNotificationResponsiveness();
    }

    public int getLastTransition() {
        return this.mRequest.getLastTransition();
    }

    int getType() {
        return this.mRequest.getType();
    }

    int getSourceTechnologies() {
        return this.mRequest.getSourceTechnologies();
    }

    public java.lang.String toString() {
        return "id=" + this.mId + ", type=" + this.mRequest.getType() + ", latitude=" + this.mRequest.getLatitude() + ", longitude=" + this.mRequest.getLongitude() + ", radius=" + this.mRequest.getRadius() + ", lastTransition=" + this.mRequest.getLastTransition() + ", unknownTimer=" + this.mRequest.getUnknownTimer() + ", monitorTransitions=" + this.mRequest.getMonitorTransitions() + ", notificationResponsiveness=" + this.mRequest.getNotificationResponsiveness() + ", sourceTechnologies=" + this.mRequest.getSourceTechnologies();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(getType());
        parcel.writeDouble(getLatitude());
        parcel.writeDouble(getLongitude());
        parcel.writeDouble(getRadius());
        parcel.writeInt(getLastTransition());
        parcel.writeInt(getMonitorTransitions());
        parcel.writeInt(getUnknownTimer());
        parcel.writeInt(getNotificationResponsiveness());
        parcel.writeInt(getSourceTechnologies());
        parcel.writeInt(getId());
    }
}
