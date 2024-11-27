package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class ClosedSubscriberGroupInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.ClosedSubscriberGroupInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.ClosedSubscriberGroupInfo>() { // from class: android.hardware.radio.network.ClosedSubscriberGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.ClosedSubscriberGroupInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.ClosedSubscriberGroupInfo closedSubscriberGroupInfo = new android.hardware.radio.network.ClosedSubscriberGroupInfo();
            closedSubscriberGroupInfo.readFromParcel(parcel);
            return closedSubscriberGroupInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.ClosedSubscriberGroupInfo[] newArray(int i) {
            return new android.hardware.radio.network.ClosedSubscriberGroupInfo[i];
        }
    };
    public java.lang.String homeNodebName;
    public boolean csgIndication = false;
    public int csgIdentity = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.csgIndication);
        parcel.writeString(this.homeNodebName);
        parcel.writeInt(this.csgIdentity);
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
            this.csgIndication = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.homeNodebName = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.csgIdentity = parcel.readInt();
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
        stringJoiner.add("csgIndication: " + this.csgIndication);
        stringJoiner.add("homeNodebName: " + java.util.Objects.toString(this.homeNodebName));
        stringJoiner.add("csgIdentity: " + this.csgIdentity);
        return "ClosedSubscriberGroupInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
