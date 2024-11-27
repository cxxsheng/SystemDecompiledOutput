package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public class CardStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.sim.CardStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.sim.CardStatus>() { // from class: android.hardware.radio.sim.CardStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.CardStatus createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.sim.CardStatus cardStatus = new android.hardware.radio.sim.CardStatus();
            cardStatus.readFromParcel(parcel);
            return cardStatus;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.CardStatus[] newArray(int i) {
            return new android.hardware.radio.sim.CardStatus[i];
        }
    };
    public static final int STATE_ABSENT = 0;
    public static final int STATE_ERROR = 2;
    public static final int STATE_PRESENT = 1;
    public static final int STATE_RESTRICTED = 3;
    public android.hardware.radio.sim.AppStatus[] applications;
    public java.lang.String atr;
    public java.lang.String eid;
    public java.lang.String iccid;
    public android.hardware.radio.config.SlotPortMapping slotMap;
    public int universalPinState;
    public int cardState = 0;
    public int gsmUmtsSubscriptionAppIndex = 0;
    public int cdmaSubscriptionAppIndex = 0;
    public int imsSubscriptionAppIndex = 0;
    public int supportedMepMode = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.cardState);
        parcel.writeInt(this.universalPinState);
        parcel.writeInt(this.gsmUmtsSubscriptionAppIndex);
        parcel.writeInt(this.cdmaSubscriptionAppIndex);
        parcel.writeInt(this.imsSubscriptionAppIndex);
        parcel.writeTypedArray(this.applications, i);
        parcel.writeString(this.atr);
        parcel.writeString(this.iccid);
        parcel.writeString(this.eid);
        parcel.writeTypedObject(this.slotMap, i);
        parcel.writeInt(this.supportedMepMode);
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
            this.cardState = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.universalPinState = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.gsmUmtsSubscriptionAppIndex = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cdmaSubscriptionAppIndex = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.imsSubscriptionAppIndex = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.applications = (android.hardware.radio.sim.AppStatus[]) parcel.createTypedArray(android.hardware.radio.sim.AppStatus.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.atr = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.iccid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.eid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.slotMap = (android.hardware.radio.config.SlotPortMapping) parcel.readTypedObject(android.hardware.radio.config.SlotPortMapping.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.supportedMepMode = parcel.readInt();
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
        stringJoiner.add("cardState: " + this.cardState);
        stringJoiner.add("universalPinState: " + android.hardware.radio.sim.PinState$$.toString(this.universalPinState));
        stringJoiner.add("gsmUmtsSubscriptionAppIndex: " + this.gsmUmtsSubscriptionAppIndex);
        stringJoiner.add("cdmaSubscriptionAppIndex: " + this.cdmaSubscriptionAppIndex);
        stringJoiner.add("imsSubscriptionAppIndex: " + this.imsSubscriptionAppIndex);
        stringJoiner.add("applications: " + java.util.Arrays.toString(this.applications));
        stringJoiner.add("atr: " + java.util.Objects.toString(this.atr));
        stringJoiner.add("iccid: " + java.util.Objects.toString(this.iccid));
        stringJoiner.add("eid: " + java.util.Objects.toString(this.eid));
        stringJoiner.add("slotMap: " + java.util.Objects.toString(this.slotMap));
        stringJoiner.add("supportedMepMode: " + android.hardware.radio.config.MultipleEnabledProfilesMode$$.toString(this.supportedMepMode));
        return "CardStatus" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.applications) | 0 | describeContents(this.slotMap);
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
