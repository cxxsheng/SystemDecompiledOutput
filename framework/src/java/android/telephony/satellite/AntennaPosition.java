package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class AntennaPosition implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.satellite.AntennaPosition> CREATOR = new android.os.Parcelable.Creator<android.telephony.satellite.AntennaPosition>() { // from class: android.telephony.satellite.AntennaPosition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.AntennaPosition createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.satellite.AntennaPosition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.AntennaPosition[] newArray(int i) {
            return new android.telephony.satellite.AntennaPosition[i];
        }
    };
    private android.telephony.satellite.AntennaDirection mAntennaDirection;
    private int mSuggestedHoldPosition;

    public AntennaPosition(android.telephony.satellite.AntennaDirection antennaDirection, int i) {
        this.mAntennaDirection = antennaDirection;
        this.mSuggestedHoldPosition = i;
    }

    private AntennaPosition(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mAntennaDirection, i);
        parcel.writeInt(this.mSuggestedHoldPosition);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.satellite.AntennaPosition antennaPosition = (android.telephony.satellite.AntennaPosition) obj;
        if (java.util.Objects.equals(this.mAntennaDirection, antennaPosition.mAntennaDirection) && this.mSuggestedHoldPosition == antennaPosition.mSuggestedHoldPosition) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mAntennaDirection, java.lang.Integer.valueOf(this.mSuggestedHoldPosition));
    }

    public java.lang.String toString() {
        return "antennaDirection:" + this.mAntennaDirection + ",suggestedHoldPosition:" + this.mSuggestedHoldPosition;
    }

    public android.telephony.satellite.AntennaDirection getAntennaDirection() {
        return this.mAntennaDirection;
    }

    public int getSuggestedHoldPosition() {
        return this.mSuggestedHoldPosition;
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mAntennaDirection = (android.telephony.satellite.AntennaDirection) parcel.readParcelable(android.telephony.satellite.AntennaDirection.class.getClassLoader(), android.telephony.satellite.AntennaDirection.class);
        this.mSuggestedHoldPosition = parcel.readInt();
    }
}
