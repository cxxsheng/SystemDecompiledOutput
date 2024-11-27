package android.hardware.thermal;

/* loaded from: classes2.dex */
public class TemperatureThreshold implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.thermal.TemperatureThreshold> CREATOR = new android.os.Parcelable.Creator<android.hardware.thermal.TemperatureThreshold>() { // from class: android.hardware.thermal.TemperatureThreshold.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.thermal.TemperatureThreshold createFromParcel(android.os.Parcel parcel) {
            android.hardware.thermal.TemperatureThreshold temperatureThreshold = new android.hardware.thermal.TemperatureThreshold();
            temperatureThreshold.readFromParcel(parcel);
            return temperatureThreshold;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.thermal.TemperatureThreshold[] newArray(int i) {
            return new android.hardware.thermal.TemperatureThreshold[i];
        }
    };
    public float[] coldThrottlingThresholds;
    public float[] hotThrottlingThresholds;
    public java.lang.String name;
    public int type;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeString(this.name);
        parcel.writeFloatArray(this.hotThrottlingThresholds);
        parcel.writeFloatArray(this.coldThrottlingThresholds);
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
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.name = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.hotThrottlingThresholds = parcel.createFloatArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.coldThrottlingThresholds = parcel.createFloatArray();
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

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("name: " + java.util.Objects.toString(this.name));
        stringJoiner.add("hotThrottlingThresholds: " + java.util.Arrays.toString(this.hotThrottlingThresholds));
        stringJoiner.add("coldThrottlingThresholds: " + java.util.Arrays.toString(this.coldThrottlingThresholds));
        return "TemperatureThreshold" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
