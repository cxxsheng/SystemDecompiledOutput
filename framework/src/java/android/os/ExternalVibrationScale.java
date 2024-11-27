package android.os;

/* loaded from: classes3.dex */
public class ExternalVibrationScale implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.ExternalVibrationScale> CREATOR = new android.os.Parcelable.Creator<android.os.ExternalVibrationScale>() { // from class: android.os.ExternalVibrationScale.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ExternalVibrationScale createFromParcel(android.os.Parcel parcel) {
            android.os.ExternalVibrationScale externalVibrationScale = new android.os.ExternalVibrationScale();
            externalVibrationScale.readFromParcel(parcel);
            return externalVibrationScale;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ExternalVibrationScale[] newArray(int i) {
            return new android.os.ExternalVibrationScale[i];
        }
    };
    public int scaleLevel = 0;
    public float adaptiveHapticsScale = 1.0f;

    public @interface ScaleLevel {
        public static final int SCALE_HIGH = 1;
        public static final int SCALE_LOW = -1;
        public static final int SCALE_MUTE = -100;
        public static final int SCALE_NONE = 0;
        public static final int SCALE_VERY_HIGH = 2;
        public static final int SCALE_VERY_LOW = -2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.scaleLevel);
        parcel.writeFloat(this.adaptiveHapticsScale);
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
            this.scaleLevel = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.adaptiveHapticsScale = parcel.readFloat();
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
        return 0;
    }
}
