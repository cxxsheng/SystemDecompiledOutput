package android.hardware.security.authgraph;

/* loaded from: classes.dex */
public final class PubKey implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.authgraph.PubKey> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.authgraph.PubKey>() { // from class: android.hardware.security.authgraph.PubKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.PubKey createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.security.authgraph.PubKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.PubKey[] newArray(int i) {
            return new android.hardware.security.authgraph.PubKey[i];
        }
    };
    public static final int plainKey = 0;
    public static final int signedKey = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int plainKey = 0;
        public static final int signedKey = 1;
    }

    public PubKey() {
        this._tag = 0;
        this._value = null;
    }

    private PubKey(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private PubKey(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.security.authgraph.PubKey plainKey(android.hardware.security.authgraph.PlainPubKey plainPubKey) {
        return new android.hardware.security.authgraph.PubKey(0, plainPubKey);
    }

    public android.hardware.security.authgraph.PlainPubKey getPlainKey() {
        _assertTag(0);
        return (android.hardware.security.authgraph.PlainPubKey) this._value;
    }

    public void setPlainKey(android.hardware.security.authgraph.PlainPubKey plainPubKey) {
        _set(0, plainPubKey);
    }

    public static android.hardware.security.authgraph.PubKey signedKey(android.hardware.security.authgraph.SignedPubKey signedPubKey) {
        return new android.hardware.security.authgraph.PubKey(1, signedPubKey);
    }

    public android.hardware.security.authgraph.SignedPubKey getSignedKey() {
        _assertTag(1);
        return (android.hardware.security.authgraph.SignedPubKey) this._value;
    }

    public void setSignedKey(android.hardware.security.authgraph.SignedPubKey signedPubKey) {
        _set(1, signedPubKey);
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getPlainKey(), i);
                break;
            case 1:
                parcel.writeTypedObject(getSignedKey(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.security.authgraph.PlainPubKey) parcel.readTypedObject(android.hardware.security.authgraph.PlainPubKey.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.security.authgraph.SignedPubKey) parcel.readTypedObject(android.hardware.security.authgraph.SignedPubKey.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getPlainKey());
            case 1:
                return 0 | describeContents(getSignedKey());
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
                return "plainKey";
            case 1:
                return "signedKey";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
