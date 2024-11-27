package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class UrspRule implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.UrspRule> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.UrspRule>() { // from class: android.hardware.radio.data.UrspRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.UrspRule createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.UrspRule urspRule = new android.hardware.radio.data.UrspRule();
            urspRule.readFromParcel(parcel);
            return urspRule;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.UrspRule[] newArray(int i) {
            return new android.hardware.radio.data.UrspRule[i];
        }
    };
    public int precedence = 0;
    public android.hardware.radio.data.RouteSelectionDescriptor[] routeSelectionDescriptor;
    public android.hardware.radio.data.TrafficDescriptor[] trafficDescriptors;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.precedence);
        parcel.writeTypedArray(this.trafficDescriptors, i);
        parcel.writeTypedArray(this.routeSelectionDescriptor, i);
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
            this.precedence = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.trafficDescriptors = (android.hardware.radio.data.TrafficDescriptor[]) parcel.createTypedArray(android.hardware.radio.data.TrafficDescriptor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.routeSelectionDescriptor = (android.hardware.radio.data.RouteSelectionDescriptor[]) parcel.createTypedArray(android.hardware.radio.data.RouteSelectionDescriptor.CREATOR);
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
        stringJoiner.add("precedence: " + this.precedence);
        stringJoiner.add("trafficDescriptors: " + java.util.Arrays.toString(this.trafficDescriptors));
        stringJoiner.add("routeSelectionDescriptor: " + java.util.Arrays.toString(this.routeSelectionDescriptor));
        return "UrspRule" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.trafficDescriptors) | 0 | describeContents(this.routeSelectionDescriptor);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
