package android.hardware.usb;

/* loaded from: classes.dex */
public final class AltModeData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.usb.AltModeData> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.AltModeData>() { // from class: android.hardware.usb.AltModeData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.AltModeData createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.usb.AltModeData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.AltModeData[] newArray(int i) {
            return new android.hardware.usb.AltModeData[i];
        }
    };
    public static final int displayPortAltModeData = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int displayPortAltModeData = 0;
    }

    public AltModeData() {
        this._tag = 0;
        this._value = null;
    }

    private AltModeData(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AltModeData(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.usb.AltModeData displayPortAltModeData(android.hardware.usb.AltModeData.DisplayPortAltModeData displayPortAltModeData2) {
        return new android.hardware.usb.AltModeData(0, displayPortAltModeData2);
    }

    public android.hardware.usb.AltModeData.DisplayPortAltModeData getDisplayPortAltModeData() {
        _assertTag(0);
        return (android.hardware.usb.AltModeData.DisplayPortAltModeData) this._value;
    }

    public void setDisplayPortAltModeData(android.hardware.usb.AltModeData.DisplayPortAltModeData displayPortAltModeData2) {
        _set(0, displayPortAltModeData2);
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getDisplayPortAltModeData(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.usb.AltModeData.DisplayPortAltModeData) parcel.readTypedObject(android.hardware.usb.AltModeData.DisplayPortAltModeData.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getDisplayPortAltModeData());
            default:
                return 0;
        }
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }

    private void _assertTag(int i) {
        if (getTag() != i) {
            throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private java.lang.String _tagString(int i) {
        switch (i) {
            case 0:
                return "displayPortAltModeData";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public static class DisplayPortAltModeData implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.usb.AltModeData.DisplayPortAltModeData> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.AltModeData.DisplayPortAltModeData>() { // from class: android.hardware.usb.AltModeData.DisplayPortAltModeData.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.usb.AltModeData.DisplayPortAltModeData createFromParcel(android.os.Parcel parcel) {
                android.hardware.usb.AltModeData.DisplayPortAltModeData displayPortAltModeData = new android.hardware.usb.AltModeData.DisplayPortAltModeData();
                displayPortAltModeData.readFromParcel(parcel);
                return displayPortAltModeData;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.usb.AltModeData.DisplayPortAltModeData[] newArray(int i) {
                return new android.hardware.usb.AltModeData.DisplayPortAltModeData[i];
            }
        };
        public int partnerSinkStatus = 0;
        public int cableStatus = 0;
        public int pinAssignment = 0;
        public boolean hpd = false;
        public int linkTrainingStatus = 0;

        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.partnerSinkStatus);
            parcel.writeInt(this.cableStatus);
            parcel.writeInt(this.pinAssignment);
            parcel.writeBoolean(this.hpd);
            parcel.writeInt(this.linkTrainingStatus);
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
                this.partnerSinkStatus = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.cableStatus = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.pinAssignment = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.hpd = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.linkTrainingStatus = parcel.readInt();
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
