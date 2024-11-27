package android.hardware.graphics.common;

/* loaded from: classes2.dex */
public class Smpte2086 implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.graphics.common.Smpte2086> CREATOR = new android.os.Parcelable.Creator<android.hardware.graphics.common.Smpte2086>() { // from class: android.hardware.graphics.common.Smpte2086.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.graphics.common.Smpte2086 createFromParcel(android.os.Parcel parcel) {
            android.hardware.graphics.common.Smpte2086 smpte2086 = new android.hardware.graphics.common.Smpte2086();
            smpte2086.readFromParcel(parcel);
            return smpte2086;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.graphics.common.Smpte2086[] newArray(int i) {
            return new android.hardware.graphics.common.Smpte2086[i];
        }
    };
    public float maxLuminance = 0.0f;
    public float minLuminance = 0.0f;
    public android.hardware.graphics.common.XyColor primaryBlue;
    public android.hardware.graphics.common.XyColor primaryGreen;
    public android.hardware.graphics.common.XyColor primaryRed;
    public android.hardware.graphics.common.XyColor whitePoint;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.primaryRed, i);
        parcel.writeTypedObject(this.primaryGreen, i);
        parcel.writeTypedObject(this.primaryBlue, i);
        parcel.writeTypedObject(this.whitePoint, i);
        parcel.writeFloat(this.maxLuminance);
        parcel.writeFloat(this.minLuminance);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.primaryRed = (android.hardware.graphics.common.XyColor) parcel.readTypedObject(android.hardware.graphics.common.XyColor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.primaryGreen = (android.hardware.graphics.common.XyColor) parcel.readTypedObject(android.hardware.graphics.common.XyColor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.primaryBlue = (android.hardware.graphics.common.XyColor) parcel.readTypedObject(android.hardware.graphics.common.XyColor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.whitePoint = (android.hardware.graphics.common.XyColor) parcel.readTypedObject(android.hardware.graphics.common.XyColor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxLuminance = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.minLuminance = parcel.readFloat();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.primaryRed) | 0 | describeContents(this.primaryGreen) | describeContents(this.primaryBlue) | describeContents(this.whitePoint);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
