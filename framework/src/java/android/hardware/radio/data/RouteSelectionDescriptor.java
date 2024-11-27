package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class RouteSelectionDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.RouteSelectionDescriptor> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.RouteSelectionDescriptor>() { // from class: android.hardware.radio.data.RouteSelectionDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.RouteSelectionDescriptor createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.RouteSelectionDescriptor routeSelectionDescriptor = new android.hardware.radio.data.RouteSelectionDescriptor();
            routeSelectionDescriptor.readFromParcel(parcel);
            return routeSelectionDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.RouteSelectionDescriptor[] newArray(int i) {
            return new android.hardware.radio.data.RouteSelectionDescriptor[i];
        }
    };
    public static final byte SSC_MODE_1 = 1;
    public static final byte SSC_MODE_2 = 2;
    public static final byte SSC_MODE_3 = 3;
    public static final byte SSC_MODE_UNKNOWN = -1;
    public java.lang.String[] dnn;
    public int sessionType;
    public android.hardware.radio.data.SliceInfo[] sliceInfo;
    public byte precedence = 0;
    public byte sscMode = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.precedence);
        parcel.writeInt(this.sessionType);
        parcel.writeByte(this.sscMode);
        parcel.writeTypedArray(this.sliceInfo, i);
        parcel.writeStringArray(this.dnn);
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
            this.precedence = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sessionType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sscMode = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sliceInfo = (android.hardware.radio.data.SliceInfo[]) parcel.createTypedArray(android.hardware.radio.data.SliceInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.dnn = parcel.createStringArray();
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
        stringJoiner.add("precedence: " + ((int) this.precedence));
        stringJoiner.add("sessionType: " + android.hardware.radio.data.PdpProtocolType$$.toString(this.sessionType));
        stringJoiner.add("sscMode: " + ((int) this.sscMode));
        stringJoiner.add("sliceInfo: " + java.util.Arrays.toString(this.sliceInfo));
        stringJoiner.add("dnn: " + java.util.Arrays.toString(this.dnn));
        return "RouteSelectionDescriptor" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.sliceInfo) | 0;
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
