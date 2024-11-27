package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class IccLogicalChannelRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.telephony.IccLogicalChannelRequest> CREATOR = new android.os.Parcelable.Creator<com.android.internal.telephony.IccLogicalChannelRequest>() { // from class: com.android.internal.telephony.IccLogicalChannelRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.IccLogicalChannelRequest createFromParcel(android.os.Parcel parcel) {
            com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = new com.android.internal.telephony.IccLogicalChannelRequest();
            iccLogicalChannelRequest.readFromParcel(parcel);
            return iccLogicalChannelRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.IccLogicalChannelRequest[] newArray(int i) {
            return new com.android.internal.telephony.IccLogicalChannelRequest[i];
        }
    };
    public java.lang.String aid;
    public android.os.IBinder binder;
    public java.lang.String callingPackage;
    public int subId = -1;
    public int slotIndex = -1;
    public int portIndex = 0;
    public int p2 = 0;
    public int channel = -1;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.subId);
        parcel.writeInt(this.slotIndex);
        parcel.writeInt(this.portIndex);
        parcel.writeString(this.callingPackage);
        parcel.writeString(this.aid);
        parcel.writeInt(this.p2);
        parcel.writeInt(this.channel);
        parcel.writeStrongBinder(this.binder);
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
            this.subId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.slotIndex = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.portIndex = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.callingPackage = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.aid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.p2 = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.channel = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.binder = parcel.readStrongBinder();
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
        stringJoiner.add("subId: " + this.subId);
        stringJoiner.add("slotIndex: " + this.slotIndex);
        stringJoiner.add("portIndex: " + this.portIndex);
        stringJoiner.add("callingPackage: " + java.util.Objects.toString(this.callingPackage));
        stringJoiner.add("aid: " + java.util.Objects.toString(this.aid));
        stringJoiner.add("p2: " + this.p2);
        stringJoiner.add("channel: " + this.channel);
        stringJoiner.add("binder: " + java.util.Objects.toString(this.binder));
        return "IccLogicalChannelRequest" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof com.android.internal.telephony.IccLogicalChannelRequest)) {
            return false;
        }
        com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = (com.android.internal.telephony.IccLogicalChannelRequest) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.subId), java.lang.Integer.valueOf(iccLogicalChannelRequest.subId)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.slotIndex), java.lang.Integer.valueOf(iccLogicalChannelRequest.slotIndex)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.portIndex), java.lang.Integer.valueOf(iccLogicalChannelRequest.portIndex)) && java.util.Objects.deepEquals(this.callingPackage, iccLogicalChannelRequest.callingPackage) && java.util.Objects.deepEquals(this.aid, iccLogicalChannelRequest.aid) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.p2), java.lang.Integer.valueOf(iccLogicalChannelRequest.p2)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.channel), java.lang.Integer.valueOf(iccLogicalChannelRequest.channel)) && java.util.Objects.deepEquals(this.binder, iccLogicalChannelRequest.binder)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.subId), java.lang.Integer.valueOf(this.slotIndex), java.lang.Integer.valueOf(this.portIndex), this.callingPackage, this.aid, java.lang.Integer.valueOf(this.p2), java.lang.Integer.valueOf(this.channel), this.binder).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
