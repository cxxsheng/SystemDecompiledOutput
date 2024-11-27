package android.hardware.biometrics.common;

/* loaded from: classes.dex */
public final class AuthenticateReason implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.common.AuthenticateReason> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.common.AuthenticateReason>() { // from class: android.hardware.biometrics.common.AuthenticateReason.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.common.AuthenticateReason createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.common.AuthenticateReason(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.common.AuthenticateReason[] newArray(int i) {
            return new android.hardware.biometrics.common.AuthenticateReason[i];
        }
    };
    public static final int faceAuthenticateReason = 1;
    public static final int fingerprintAuthenticateReason = 2;
    public static final int vendorAuthenticateReason = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Face {
        public static final int ALTERNATE_BIOMETRIC_BOUNCER_SHOWN = 4;
        public static final int ASSISTANT_VISIBLE = 3;
        public static final int NOTIFICATION_PANEL_CLICKED = 5;
        public static final int OCCLUDING_APP_REQUESTED = 6;
        public static final int PICK_UP_GESTURE_TRIGGERED = 7;
        public static final int PRIMARY_BOUNCER_SHOWN = 2;
        public static final int QS_EXPANDED = 8;
        public static final int STARTED_WAKING_UP = 1;
        public static final int SWIPE_UP_ON_BOUNCER = 9;
        public static final int UDFPS_POINTER_DOWN = 10;
        public static final int UNKNOWN = 0;
    }

    public @interface Fingerprint {
        public static final int UNKNOWN = 0;
    }

    public @interface Tag {
        public static final int faceAuthenticateReason = 1;
        public static final int fingerprintAuthenticateReason = 2;
        public static final int vendorAuthenticateReason = 0;
    }

    public AuthenticateReason() {
        this._tag = 0;
        this._value = null;
    }

    private AuthenticateReason(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AuthenticateReason(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.biometrics.common.AuthenticateReason vendorAuthenticateReason(android.hardware.biometrics.common.AuthenticateReason.Vendor vendor) {
        return new android.hardware.biometrics.common.AuthenticateReason(0, vendor);
    }

    public android.hardware.biometrics.common.AuthenticateReason.Vendor getVendorAuthenticateReason() {
        _assertTag(0);
        return (android.hardware.biometrics.common.AuthenticateReason.Vendor) this._value;
    }

    public void setVendorAuthenticateReason(android.hardware.biometrics.common.AuthenticateReason.Vendor vendor) {
        _set(0, vendor);
    }

    public static android.hardware.biometrics.common.AuthenticateReason faceAuthenticateReason(int i) {
        return new android.hardware.biometrics.common.AuthenticateReason(1, java.lang.Integer.valueOf(i));
    }

    public int getFaceAuthenticateReason() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setFaceAuthenticateReason(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.biometrics.common.AuthenticateReason fingerprintAuthenticateReason(int i) {
        return new android.hardware.biometrics.common.AuthenticateReason(2, java.lang.Integer.valueOf(i));
    }

    public int getFingerprintAuthenticateReason() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setFingerprintAuthenticateReason(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getVendorAuthenticateReason(), i);
                break;
            case 1:
                parcel.writeInt(getFaceAuthenticateReason());
                break;
            case 2:
                parcel.writeInt(getFingerprintAuthenticateReason());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.biometrics.common.AuthenticateReason.Vendor) parcel.readTypedObject(android.hardware.biometrics.common.AuthenticateReason.Vendor.CREATOR));
                return;
            case 1:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getVendorAuthenticateReason());
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
                return "vendorAuthenticateReason";
            case 1:
                return "faceAuthenticateReason";
            case 2:
                return "fingerprintAuthenticateReason";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public static class Vendor implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.biometrics.common.AuthenticateReason.Vendor> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.common.AuthenticateReason.Vendor>() { // from class: android.hardware.biometrics.common.AuthenticateReason.Vendor.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.biometrics.common.AuthenticateReason.Vendor createFromParcel(android.os.Parcel parcel) {
                android.hardware.biometrics.common.AuthenticateReason.Vendor vendor = new android.hardware.biometrics.common.AuthenticateReason.Vendor();
                vendor.readFromParcel(parcel);
                return vendor;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.biometrics.common.AuthenticateReason.Vendor[] newArray(int i) {
                return new android.hardware.biometrics.common.AuthenticateReason.Vendor[i];
            }
        };
        public final android.os.ParcelableHolder extension = new android.os.ParcelableHolder(1);

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.extension, 0);
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
                } else {
                    if (parcel.readInt() != 0) {
                        this.extension.readFromParcel(parcel);
                    }
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
            return describeContents(this.extension) | 0;
        }

        private int describeContents(java.lang.Object obj) {
            if (obj == null || !(obj instanceof android.os.Parcelable)) {
                return 0;
            }
            return ((android.os.Parcelable) obj).describeContents();
        }
    }
}
