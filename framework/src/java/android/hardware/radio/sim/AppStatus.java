package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public class AppStatus implements android.os.Parcelable {
    public static final int APP_STATE_DETECTED = 1;
    public static final int APP_STATE_PIN = 2;
    public static final int APP_STATE_PUK = 3;
    public static final int APP_STATE_READY = 5;
    public static final int APP_STATE_SUBSCRIPTION_PERSO = 4;
    public static final int APP_STATE_UNKNOWN = 0;
    public static final int APP_TYPE_CSIM = 4;
    public static final int APP_TYPE_ISIM = 5;
    public static final int APP_TYPE_RUIM = 3;
    public static final int APP_TYPE_SIM = 1;
    public static final int APP_TYPE_UNKNOWN = 0;
    public static final int APP_TYPE_USIM = 2;
    public static final android.os.Parcelable.Creator<android.hardware.radio.sim.AppStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.sim.AppStatus>() { // from class: android.hardware.radio.sim.AppStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.AppStatus createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.sim.AppStatus appStatus = new android.hardware.radio.sim.AppStatus();
            appStatus.readFromParcel(parcel);
            return appStatus;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.AppStatus[] newArray(int i) {
            return new android.hardware.radio.sim.AppStatus[i];
        }
    };
    public java.lang.String aidPtr;
    public java.lang.String appLabelPtr;
    public int persoSubstate;
    public int pin1;
    public int pin2;
    public int appType = 0;
    public int appState = 0;
    public boolean pin1Replaced = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.appType);
        parcel.writeInt(this.appState);
        parcel.writeInt(this.persoSubstate);
        parcel.writeString(this.aidPtr);
        parcel.writeString(this.appLabelPtr);
        parcel.writeBoolean(this.pin1Replaced);
        parcel.writeInt(this.pin1);
        parcel.writeInt(this.pin2);
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
            this.appType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.appState = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.persoSubstate = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.aidPtr = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.appLabelPtr = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pin1Replaced = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pin1 = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.pin2 = parcel.readInt();
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
        stringJoiner.add("appType: " + this.appType);
        stringJoiner.add("appState: " + this.appState);
        stringJoiner.add("persoSubstate: " + android.hardware.radio.sim.PersoSubstate$$.toString(this.persoSubstate));
        stringJoiner.add("aidPtr: " + java.util.Objects.toString(this.aidPtr));
        stringJoiner.add("appLabelPtr: " + java.util.Objects.toString(this.appLabelPtr));
        stringJoiner.add("pin1Replaced: " + this.pin1Replaced);
        stringJoiner.add("pin1: " + android.hardware.radio.sim.PinState$$.toString(this.pin1));
        stringJoiner.add("pin2: " + android.hardware.radio.sim.PinState$$.toString(this.pin2));
        return "AppStatus" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
