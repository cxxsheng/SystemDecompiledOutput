package android.hardware.vibrator;

/* loaded from: classes2.dex */
public final class PrimitivePwle implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.vibrator.PrimitivePwle> CREATOR = new android.os.Parcelable.Creator<android.hardware.vibrator.PrimitivePwle>() { // from class: android.hardware.vibrator.PrimitivePwle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.vibrator.PrimitivePwle createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.vibrator.PrimitivePwle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.vibrator.PrimitivePwle[] newArray(int i) {
            return new android.hardware.vibrator.PrimitivePwle[i];
        }
    };
    public static final int active = 0;
    public static final int braking = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int active = 0;
        public static final int braking = 1;
    }

    public PrimitivePwle() {
        this._tag = 0;
        this._value = null;
    }

    private PrimitivePwle(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private PrimitivePwle(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.vibrator.PrimitivePwle active(android.hardware.vibrator.ActivePwle activePwle) {
        return new android.hardware.vibrator.PrimitivePwle(0, activePwle);
    }

    public android.hardware.vibrator.ActivePwle getActive() {
        _assertTag(0);
        return (android.hardware.vibrator.ActivePwle) this._value;
    }

    public void setActive(android.hardware.vibrator.ActivePwle activePwle) {
        _set(0, activePwle);
    }

    public static android.hardware.vibrator.PrimitivePwle braking(android.hardware.vibrator.BrakingPwle brakingPwle) {
        return new android.hardware.vibrator.PrimitivePwle(1, brakingPwle);
    }

    public android.hardware.vibrator.BrakingPwle getBraking() {
        _assertTag(1);
        return (android.hardware.vibrator.BrakingPwle) this._value;
    }

    public void setBraking(android.hardware.vibrator.BrakingPwle brakingPwle) {
        _set(1, brakingPwle);
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
                parcel.writeTypedObject(getActive(), i);
                break;
            case 1:
                parcel.writeTypedObject(getBraking(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.vibrator.ActivePwle) parcel.readTypedObject(android.hardware.vibrator.ActivePwle.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.vibrator.BrakingPwle) parcel.readTypedObject(android.hardware.vibrator.BrakingPwle.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getActive());
            case 1:
                return 0 | describeContents(getBraking());
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
                return "active";
            case 1:
                return "braking";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
