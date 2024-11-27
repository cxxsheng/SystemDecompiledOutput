package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SatelliteCapabilities implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.satellite.SatelliteCapabilities> CREATOR = new android.os.Parcelable.Creator<android.telephony.satellite.SatelliteCapabilities>() { // from class: android.telephony.satellite.SatelliteCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.SatelliteCapabilities createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.satellite.SatelliteCapabilities(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.SatelliteCapabilities[] newArray(int i) {
            return new android.telephony.satellite.SatelliteCapabilities[i];
        }
    };
    private java.util.Map<java.lang.Integer, android.telephony.satellite.AntennaPosition> mAntennaPositionMap;
    private boolean mIsPointingRequired;
    private int mMaxBytesPerOutgoingDatagram;
    private java.util.Set<java.lang.Integer> mSupportedRadioTechnologies;

    public SatelliteCapabilities(java.util.Set<java.lang.Integer> set, boolean z, int i, java.util.Map<java.lang.Integer, android.telephony.satellite.AntennaPosition> map) {
        this.mSupportedRadioTechnologies = set == null ? new java.util.HashSet<>() : set;
        this.mIsPointingRequired = z;
        this.mMaxBytesPerOutgoingDatagram = i;
        this.mAntennaPositionMap = map;
    }

    private SatelliteCapabilities(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mSupportedRadioTechnologies != null && !this.mSupportedRadioTechnologies.isEmpty()) {
            parcel.writeInt(this.mSupportedRadioTechnologies.size());
            java.util.Iterator<java.lang.Integer> it = this.mSupportedRadioTechnologies.iterator();
            while (it.hasNext()) {
                parcel.writeInt(it.next().intValue());
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.mIsPointingRequired);
        parcel.writeInt(this.mMaxBytesPerOutgoingDatagram);
        if (this.mAntennaPositionMap != null && !this.mAntennaPositionMap.isEmpty()) {
            parcel.writeInt(this.mAntennaPositionMap.size());
            for (java.util.Map.Entry<java.lang.Integer, android.telephony.satellite.AntennaPosition> entry : this.mAntennaPositionMap.entrySet()) {
                parcel.writeInt(entry.getKey().intValue());
                parcel.writeParcelable(entry.getValue(), i);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("SupportedRadioTechnology:");
        if (this.mSupportedRadioTechnologies != null && !this.mSupportedRadioTechnologies.isEmpty()) {
            java.util.Iterator<java.lang.Integer> it = this.mSupportedRadioTechnologies.iterator();
            while (it.hasNext()) {
                sb.append(it.next().intValue());
                sb.append(",");
            }
        } else {
            sb.append("none,");
        }
        sb.append("isPointingRequired:");
        sb.append(this.mIsPointingRequired);
        sb.append(",");
        sb.append("maxBytesPerOutgoingDatagram:");
        sb.append(this.mMaxBytesPerOutgoingDatagram);
        sb.append(",");
        sb.append("antennaPositionMap:");
        sb.append(this.mAntennaPositionMap);
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.satellite.SatelliteCapabilities satelliteCapabilities = (android.telephony.satellite.SatelliteCapabilities) obj;
        if (java.util.Objects.equals(this.mSupportedRadioTechnologies, satelliteCapabilities.mSupportedRadioTechnologies) && this.mIsPointingRequired == satelliteCapabilities.mIsPointingRequired && this.mMaxBytesPerOutgoingDatagram == satelliteCapabilities.mMaxBytesPerOutgoingDatagram && java.util.Objects.equals(this.mAntennaPositionMap, satelliteCapabilities.mAntennaPositionMap)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mSupportedRadioTechnologies, java.lang.Boolean.valueOf(this.mIsPointingRequired), java.lang.Integer.valueOf(this.mMaxBytesPerOutgoingDatagram), this.mAntennaPositionMap);
    }

    public java.util.Set<java.lang.Integer> getSupportedRadioTechnologies() {
        return this.mSupportedRadioTechnologies;
    }

    public boolean isPointingRequired() {
        return this.mIsPointingRequired;
    }

    public int getMaxBytesPerOutgoingDatagram() {
        return this.mMaxBytesPerOutgoingDatagram;
    }

    public java.util.Map<java.lang.Integer, android.telephony.satellite.AntennaPosition> getAntennaPositionMap() {
        return this.mAntennaPositionMap;
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mSupportedRadioTechnologies = new java.util.HashSet();
        int readInt = parcel.readInt();
        if (readInt > 0) {
            for (int i = 0; i < readInt; i++) {
                this.mSupportedRadioTechnologies.add(java.lang.Integer.valueOf(parcel.readInt()));
            }
        }
        this.mIsPointingRequired = parcel.readBoolean();
        this.mMaxBytesPerOutgoingDatagram = parcel.readInt();
        this.mAntennaPositionMap = new java.util.HashMap();
        int readInt2 = parcel.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mAntennaPositionMap.put(java.lang.Integer.valueOf(parcel.readInt()), (android.telephony.satellite.AntennaPosition) parcel.readParcelable(android.telephony.satellite.AntennaPosition.class.getClassLoader(), android.telephony.satellite.AntennaPosition.class));
        }
    }
}
