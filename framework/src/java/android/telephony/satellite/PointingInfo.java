package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PointingInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.satellite.PointingInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.satellite.PointingInfo>() { // from class: android.telephony.satellite.PointingInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.PointingInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.satellite.PointingInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.PointingInfo[] newArray(int i) {
            return new android.telephony.satellite.PointingInfo[i];
        }
    };
    private float mSatelliteAzimuthDegrees;
    private float mSatelliteElevationDegrees;

    public PointingInfo(float f, float f2) {
        this.mSatelliteAzimuthDegrees = f;
        this.mSatelliteElevationDegrees = f2;
    }

    private PointingInfo(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mSatelliteAzimuthDegrees);
        parcel.writeFloat(this.mSatelliteElevationDegrees);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.satellite.PointingInfo pointingInfo = (android.telephony.satellite.PointingInfo) obj;
        if (this.mSatelliteAzimuthDegrees == pointingInfo.mSatelliteAzimuthDegrees && this.mSatelliteElevationDegrees == pointingInfo.mSatelliteElevationDegrees) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mSatelliteAzimuthDegrees), java.lang.Float.valueOf(this.mSatelliteElevationDegrees));
    }

    public java.lang.String toString() {
        return "SatelliteAzimuthDegrees:" + this.mSatelliteAzimuthDegrees + ",SatelliteElevationDegrees:" + this.mSatelliteElevationDegrees;
    }

    public float getSatelliteAzimuthDegrees() {
        return this.mSatelliteAzimuthDegrees;
    }

    public float getSatelliteElevationDegrees() {
        return this.mSatelliteElevationDegrees;
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mSatelliteAzimuthDegrees = parcel.readFloat();
        this.mSatelliteElevationDegrees = parcel.readFloat();
    }
}
