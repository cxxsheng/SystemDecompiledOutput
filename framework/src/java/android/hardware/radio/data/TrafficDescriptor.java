package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class TrafficDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.TrafficDescriptor> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.TrafficDescriptor>() { // from class: android.hardware.radio.data.TrafficDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.TrafficDescriptor createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.TrafficDescriptor trafficDescriptor = new android.hardware.radio.data.TrafficDescriptor();
            trafficDescriptor.readFromParcel(parcel);
            return trafficDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.TrafficDescriptor[] newArray(int i) {
            return new android.hardware.radio.data.TrafficDescriptor[i];
        }
    };
    public java.lang.String dnn;
    public android.hardware.radio.data.OsAppId osAppId;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.dnn);
        parcel.writeTypedObject(this.osAppId, i);
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
            this.dnn = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.osAppId = (android.hardware.radio.data.OsAppId) parcel.readTypedObject(android.hardware.radio.data.OsAppId.CREATOR);
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
        stringJoiner.add("dnn: " + java.util.Objects.toString(this.dnn));
        stringJoiner.add("osAppId: " + java.util.Objects.toString(this.osAppId));
        return "TrafficDescriptor" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.osAppId) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
