package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class QosSession implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.QosSession> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.QosSession>() { // from class: android.hardware.radio.data.QosSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.QosSession createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.QosSession qosSession = new android.hardware.radio.data.QosSession();
            qosSession.readFromParcel(parcel);
            return qosSession;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.QosSession[] newArray(int i) {
            return new android.hardware.radio.data.QosSession[i];
        }
    };
    public android.hardware.radio.data.Qos qos;
    public android.hardware.radio.data.QosFilter[] qosFilters;
    public int qosSessionId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.qosSessionId);
        parcel.writeTypedObject(this.qos, i);
        parcel.writeTypedArray(this.qosFilters, i);
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
            this.qosSessionId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.qos = (android.hardware.radio.data.Qos) parcel.readTypedObject(android.hardware.radio.data.Qos.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.qosFilters = (android.hardware.radio.data.QosFilter[]) parcel.createTypedArray(android.hardware.radio.data.QosFilter.CREATOR);
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
        stringJoiner.add("qosSessionId: " + this.qosSessionId);
        stringJoiner.add("qos: " + java.util.Objects.toString(this.qos));
        stringJoiner.add("qosFilters: " + java.util.Arrays.toString(this.qosFilters));
        return "QosSession" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.qos) | 0 | describeContents(this.qosFilters);
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
