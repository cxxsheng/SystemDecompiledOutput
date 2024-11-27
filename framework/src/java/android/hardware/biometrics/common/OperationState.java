package android.hardware.biometrics.common;

/* loaded from: classes.dex */
public final class OperationState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.common.OperationState> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.common.OperationState>() { // from class: android.hardware.biometrics.common.OperationState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.common.OperationState createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.biometrics.common.OperationState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.common.OperationState[] newArray(int i) {
            return new android.hardware.biometrics.common.OperationState[i];
        }
    };
    public static final int faceOperationState = 1;
    public static final int fingerprintOperationState = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int faceOperationState = 1;
        public static final int fingerprintOperationState = 0;
    }

    public OperationState() {
        this._tag = 0;
        this._value = null;
    }

    private OperationState(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private OperationState(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.biometrics.common.OperationState fingerprintOperationState(android.hardware.biometrics.common.OperationState.FingerprintOperationState fingerprintOperationState2) {
        return new android.hardware.biometrics.common.OperationState(0, fingerprintOperationState2);
    }

    public android.hardware.biometrics.common.OperationState.FingerprintOperationState getFingerprintOperationState() {
        _assertTag(0);
        return (android.hardware.biometrics.common.OperationState.FingerprintOperationState) this._value;
    }

    public void setFingerprintOperationState(android.hardware.biometrics.common.OperationState.FingerprintOperationState fingerprintOperationState2) {
        _set(0, fingerprintOperationState2);
    }

    public static android.hardware.biometrics.common.OperationState faceOperationState(android.hardware.biometrics.common.OperationState.FaceOperationState faceOperationState2) {
        return new android.hardware.biometrics.common.OperationState(1, faceOperationState2);
    }

    public android.hardware.biometrics.common.OperationState.FaceOperationState getFaceOperationState() {
        _assertTag(1);
        return (android.hardware.biometrics.common.OperationState.FaceOperationState) this._value;
    }

    public void setFaceOperationState(android.hardware.biometrics.common.OperationState.FaceOperationState faceOperationState2) {
        _set(1, faceOperationState2);
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
                parcel.writeTypedObject(getFingerprintOperationState(), i);
                break;
            case 1:
                parcel.writeTypedObject(getFaceOperationState(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.biometrics.common.OperationState.FingerprintOperationState) parcel.readTypedObject(android.hardware.biometrics.common.OperationState.FingerprintOperationState.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.biometrics.common.OperationState.FaceOperationState) parcel.readTypedObject(android.hardware.biometrics.common.OperationState.FaceOperationState.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getFingerprintOperationState());
            case 1:
                return 0 | describeContents(getFaceOperationState());
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
                return "fingerprintOperationState";
            case 1:
                return "faceOperationState";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public static class FingerprintOperationState implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.biometrics.common.OperationState.FingerprintOperationState> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.common.OperationState.FingerprintOperationState>() { // from class: android.hardware.biometrics.common.OperationState.FingerprintOperationState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.biometrics.common.OperationState.FingerprintOperationState createFromParcel(android.os.Parcel parcel) {
                android.hardware.biometrics.common.OperationState.FingerprintOperationState fingerprintOperationState = new android.hardware.biometrics.common.OperationState.FingerprintOperationState();
                fingerprintOperationState.readFromParcel(parcel);
                return fingerprintOperationState;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.biometrics.common.OperationState.FingerprintOperationState[] newArray(int i) {
                return new android.hardware.biometrics.common.OperationState.FingerprintOperationState[i];
            }
        };
        public final android.os.ParcelableHolder extension = new android.os.ParcelableHolder(1);
        public boolean isHardwareIgnoringTouches = false;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.extension, 0);
            parcel.writeBoolean(this.isHardwareIgnoringTouches);
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
                if (parcel.readInt() != 0) {
                    this.extension.readFromParcel(parcel);
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.isHardwareIgnoringTouches = parcel.readBoolean();
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

    public static class FaceOperationState implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.biometrics.common.OperationState.FaceOperationState> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.common.OperationState.FaceOperationState>() { // from class: android.hardware.biometrics.common.OperationState.FaceOperationState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.biometrics.common.OperationState.FaceOperationState createFromParcel(android.os.Parcel parcel) {
                android.hardware.biometrics.common.OperationState.FaceOperationState faceOperationState = new android.hardware.biometrics.common.OperationState.FaceOperationState();
                faceOperationState.readFromParcel(parcel);
                return faceOperationState;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.biometrics.common.OperationState.FaceOperationState[] newArray(int i) {
                return new android.hardware.biometrics.common.OperationState.FaceOperationState[i];
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
