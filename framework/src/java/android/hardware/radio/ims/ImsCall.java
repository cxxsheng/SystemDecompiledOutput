package android.hardware.radio.ims;

/* loaded from: classes2.dex */
public class ImsCall implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.ims.ImsCall> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.ims.ImsCall>() { // from class: android.hardware.radio.ims.ImsCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.ims.ImsCall createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.ims.ImsCall imsCall = new android.hardware.radio.ims.ImsCall();
            imsCall.readFromParcel(parcel);
            return imsCall;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.ims.ImsCall[] newArray(int i) {
            return new android.hardware.radio.ims.ImsCall[i];
        }
    };
    public int accessNetwork;
    public int callState;
    public int callType;
    public int direction;
    public int index = 0;
    public boolean isHeldByRemote = false;

    public @interface CallState {
        public static final int ACTIVE = 0;
        public static final int ALERTING = 3;
        public static final int DIALING = 2;
        public static final int DISCONNECTED = 7;
        public static final int DISCONNECTING = 6;
        public static final int HOLDING = 1;
        public static final int INCOMING = 4;
        public static final int WAITING = 5;
    }

    public @interface CallType {
        public static final int EMERGENCY = 1;
        public static final int NORMAL = 0;
    }

    public @interface Direction {
        public static final int INCOMING = 0;
        public static final int OUTGOING = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.index);
        parcel.writeInt(this.callType);
        parcel.writeInt(this.accessNetwork);
        parcel.writeInt(this.callState);
        parcel.writeInt(this.direction);
        parcel.writeBoolean(this.isHeldByRemote);
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
            this.index = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.callType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.accessNetwork = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.callState = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.direction = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.isHeldByRemote = parcel.readBoolean();
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
        stringJoiner.add("index: " + this.index);
        stringJoiner.add("callType: " + this.callType);
        stringJoiner.add("accessNetwork: " + android.hardware.radio.AccessNetwork$$.toString(this.accessNetwork));
        stringJoiner.add("callState: " + this.callState);
        stringJoiner.add("direction: " + this.direction);
        stringJoiner.add("isHeldByRemote: " + this.isHeldByRemote);
        return "ImsCall" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
