package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class AntennaDirection implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.satellite.AntennaDirection> CREATOR = new android.os.Parcelable.Creator<android.telephony.satellite.AntennaDirection>() { // from class: android.telephony.satellite.AntennaDirection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.AntennaDirection createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.satellite.AntennaDirection(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.AntennaDirection[] newArray(int i) {
            return new android.telephony.satellite.AntennaDirection[i];
        }
    };
    private float mX;
    private float mY;
    private float mZ;

    public AntennaDirection(float f, float f2, float f3) {
        this.mX = f;
        this.mY = f2;
        this.mZ = f3;
    }

    private AntennaDirection(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mX);
        parcel.writeFloat(this.mY);
        parcel.writeFloat(this.mZ);
    }

    public java.lang.String toString() {
        return "X:" + this.mX + ",Y:" + this.mY + ",Z:" + this.mZ;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.satellite.AntennaDirection antennaDirection = (android.telephony.satellite.AntennaDirection) obj;
        if (this.mX == antennaDirection.mX && this.mY == antennaDirection.mY && this.mZ == antennaDirection.mZ) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mX), java.lang.Float.valueOf(this.mY), java.lang.Float.valueOf(this.mZ));
    }

    public float getX() {
        return this.mX;
    }

    public float getY() {
        return this.mY;
    }

    public float getZ() {
        return this.mZ;
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mX = parcel.readFloat();
        this.mY = parcel.readFloat();
        this.mZ = parcel.readFloat();
    }
}
