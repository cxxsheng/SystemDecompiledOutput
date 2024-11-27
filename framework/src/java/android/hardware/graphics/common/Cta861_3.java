package android.hardware.graphics.common;

/* loaded from: classes2.dex */
public class Cta861_3 implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.graphics.common.Cta861_3> CREATOR = new android.os.Parcelable.Creator<android.hardware.graphics.common.Cta861_3>() { // from class: android.hardware.graphics.common.Cta861_3.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.graphics.common.Cta861_3 createFromParcel(android.os.Parcel parcel) {
            android.hardware.graphics.common.Cta861_3 cta861_3 = new android.hardware.graphics.common.Cta861_3();
            cta861_3.readFromParcel(parcel);
            return cta861_3;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.graphics.common.Cta861_3[] newArray(int i) {
            return new android.hardware.graphics.common.Cta861_3[i];
        }
    };
    public float maxContentLightLevel = 0.0f;
    public float maxFrameAverageLightLevel = 0.0f;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeFloat(this.maxContentLightLevel);
        parcel.writeFloat(this.maxFrameAverageLightLevel);
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
            this.maxContentLightLevel = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.maxFrameAverageLightLevel = parcel.readFloat();
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
