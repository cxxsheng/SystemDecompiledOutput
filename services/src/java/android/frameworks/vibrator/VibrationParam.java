package android.frameworks.vibrator;

/* loaded from: classes.dex */
public final class VibrationParam implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.frameworks.vibrator.VibrationParam> CREATOR = new android.os.Parcelable.Creator<android.frameworks.vibrator.VibrationParam>() { // from class: android.frameworks.vibrator.VibrationParam.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.vibrator.VibrationParam createFromParcel(android.os.Parcel parcel) {
            return new android.frameworks.vibrator.VibrationParam(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.vibrator.VibrationParam[] newArray(int i) {
            return new android.frameworks.vibrator.VibrationParam[i];
        }
    };
    public static final int scale = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int scale = 0;
    }

    public VibrationParam() {
        this._tag = 0;
        this._value = null;
    }

    private VibrationParam(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private VibrationParam(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.frameworks.vibrator.VibrationParam scale(android.frameworks.vibrator.ScaleParam scaleParam) {
        return new android.frameworks.vibrator.VibrationParam(0, scaleParam);
    }

    public android.frameworks.vibrator.ScaleParam getScale() {
        _assertTag(0);
        return (android.frameworks.vibrator.ScaleParam) this._value;
    }

    public void setScale(android.frameworks.vibrator.ScaleParam scaleParam) {
        _set(0, scaleParam);
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getScale(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.frameworks.vibrator.ScaleParam) parcel.readTypedObject(android.frameworks.vibrator.ScaleParam.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getScale());
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
                return "scale";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
