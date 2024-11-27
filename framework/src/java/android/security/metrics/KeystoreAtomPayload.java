package android.security.metrics;

/* loaded from: classes3.dex */
public final class KeystoreAtomPayload implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.metrics.KeystoreAtomPayload> CREATOR = new android.os.Parcelable.Creator<android.security.metrics.KeystoreAtomPayload>() { // from class: android.security.metrics.KeystoreAtomPayload.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.metrics.KeystoreAtomPayload createFromParcel(android.os.Parcel parcel) {
            return new android.security.metrics.KeystoreAtomPayload(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.metrics.KeystoreAtomPayload[] newArray(int i) {
            return new android.security.metrics.KeystoreAtomPayload[i];
        }
    };
    public static final int crashStats = 8;
    public static final int keyCreationWithAuthInfo = 2;
    public static final int keyCreationWithGeneralInfo = 1;
    public static final int keyCreationWithPurposeAndModesInfo = 3;
    public static final int keyOperationWithGeneralInfo = 6;
    public static final int keyOperationWithPurposeAndModesInfo = 5;
    public static final int keystore2AtomWithOverflow = 4;
    public static final int rkpErrorStats = 7;
    public static final int storageStats = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int crashStats = 8;
        public static final int keyCreationWithAuthInfo = 2;
        public static final int keyCreationWithGeneralInfo = 1;
        public static final int keyCreationWithPurposeAndModesInfo = 3;
        public static final int keyOperationWithGeneralInfo = 6;
        public static final int keyOperationWithPurposeAndModesInfo = 5;
        public static final int keystore2AtomWithOverflow = 4;
        public static final int rkpErrorStats = 7;
        public static final int storageStats = 0;
    }

    public KeystoreAtomPayload() {
        this._tag = 0;
        this._value = null;
    }

    private KeystoreAtomPayload(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private KeystoreAtomPayload(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.security.metrics.KeystoreAtomPayload storageStats(android.security.metrics.StorageStats storageStats2) {
        return new android.security.metrics.KeystoreAtomPayload(0, storageStats2);
    }

    public android.security.metrics.StorageStats getStorageStats() {
        _assertTag(0);
        return (android.security.metrics.StorageStats) this._value;
    }

    public void setStorageStats(android.security.metrics.StorageStats storageStats2) {
        _set(0, storageStats2);
    }

    public static android.security.metrics.KeystoreAtomPayload keyCreationWithGeneralInfo(android.security.metrics.KeyCreationWithGeneralInfo keyCreationWithGeneralInfo2) {
        return new android.security.metrics.KeystoreAtomPayload(1, keyCreationWithGeneralInfo2);
    }

    public android.security.metrics.KeyCreationWithGeneralInfo getKeyCreationWithGeneralInfo() {
        _assertTag(1);
        return (android.security.metrics.KeyCreationWithGeneralInfo) this._value;
    }

    public void setKeyCreationWithGeneralInfo(android.security.metrics.KeyCreationWithGeneralInfo keyCreationWithGeneralInfo2) {
        _set(1, keyCreationWithGeneralInfo2);
    }

    public static android.security.metrics.KeystoreAtomPayload keyCreationWithAuthInfo(android.security.metrics.KeyCreationWithAuthInfo keyCreationWithAuthInfo2) {
        return new android.security.metrics.KeystoreAtomPayload(2, keyCreationWithAuthInfo2);
    }

    public android.security.metrics.KeyCreationWithAuthInfo getKeyCreationWithAuthInfo() {
        _assertTag(2);
        return (android.security.metrics.KeyCreationWithAuthInfo) this._value;
    }

    public void setKeyCreationWithAuthInfo(android.security.metrics.KeyCreationWithAuthInfo keyCreationWithAuthInfo2) {
        _set(2, keyCreationWithAuthInfo2);
    }

    public static android.security.metrics.KeystoreAtomPayload keyCreationWithPurposeAndModesInfo(android.security.metrics.KeyCreationWithPurposeAndModesInfo keyCreationWithPurposeAndModesInfo2) {
        return new android.security.metrics.KeystoreAtomPayload(3, keyCreationWithPurposeAndModesInfo2);
    }

    public android.security.metrics.KeyCreationWithPurposeAndModesInfo getKeyCreationWithPurposeAndModesInfo() {
        _assertTag(3);
        return (android.security.metrics.KeyCreationWithPurposeAndModesInfo) this._value;
    }

    public void setKeyCreationWithPurposeAndModesInfo(android.security.metrics.KeyCreationWithPurposeAndModesInfo keyCreationWithPurposeAndModesInfo2) {
        _set(3, keyCreationWithPurposeAndModesInfo2);
    }

    public static android.security.metrics.KeystoreAtomPayload keystore2AtomWithOverflow(android.security.metrics.Keystore2AtomWithOverflow keystore2AtomWithOverflow2) {
        return new android.security.metrics.KeystoreAtomPayload(4, keystore2AtomWithOverflow2);
    }

    public android.security.metrics.Keystore2AtomWithOverflow getKeystore2AtomWithOverflow() {
        _assertTag(4);
        return (android.security.metrics.Keystore2AtomWithOverflow) this._value;
    }

    public void setKeystore2AtomWithOverflow(android.security.metrics.Keystore2AtomWithOverflow keystore2AtomWithOverflow2) {
        _set(4, keystore2AtomWithOverflow2);
    }

    public static android.security.metrics.KeystoreAtomPayload keyOperationWithPurposeAndModesInfo(android.security.metrics.KeyOperationWithPurposeAndModesInfo keyOperationWithPurposeAndModesInfo2) {
        return new android.security.metrics.KeystoreAtomPayload(5, keyOperationWithPurposeAndModesInfo2);
    }

    public android.security.metrics.KeyOperationWithPurposeAndModesInfo getKeyOperationWithPurposeAndModesInfo() {
        _assertTag(5);
        return (android.security.metrics.KeyOperationWithPurposeAndModesInfo) this._value;
    }

    public void setKeyOperationWithPurposeAndModesInfo(android.security.metrics.KeyOperationWithPurposeAndModesInfo keyOperationWithPurposeAndModesInfo2) {
        _set(5, keyOperationWithPurposeAndModesInfo2);
    }

    public static android.security.metrics.KeystoreAtomPayload keyOperationWithGeneralInfo(android.security.metrics.KeyOperationWithGeneralInfo keyOperationWithGeneralInfo2) {
        return new android.security.metrics.KeystoreAtomPayload(6, keyOperationWithGeneralInfo2);
    }

    public android.security.metrics.KeyOperationWithGeneralInfo getKeyOperationWithGeneralInfo() {
        _assertTag(6);
        return (android.security.metrics.KeyOperationWithGeneralInfo) this._value;
    }

    public void setKeyOperationWithGeneralInfo(android.security.metrics.KeyOperationWithGeneralInfo keyOperationWithGeneralInfo2) {
        _set(6, keyOperationWithGeneralInfo2);
    }

    public static android.security.metrics.KeystoreAtomPayload rkpErrorStats(android.security.metrics.RkpErrorStats rkpErrorStats2) {
        return new android.security.metrics.KeystoreAtomPayload(7, rkpErrorStats2);
    }

    public android.security.metrics.RkpErrorStats getRkpErrorStats() {
        _assertTag(7);
        return (android.security.metrics.RkpErrorStats) this._value;
    }

    public void setRkpErrorStats(android.security.metrics.RkpErrorStats rkpErrorStats2) {
        _set(7, rkpErrorStats2);
    }

    public static android.security.metrics.KeystoreAtomPayload crashStats(android.security.metrics.CrashStats crashStats2) {
        return new android.security.metrics.KeystoreAtomPayload(8, crashStats2);
    }

    public android.security.metrics.CrashStats getCrashStats() {
        _assertTag(8);
        return (android.security.metrics.CrashStats) this._value;
    }

    public void setCrashStats(android.security.metrics.CrashStats crashStats2) {
        _set(8, crashStats2);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeTypedObject(getStorageStats(), i);
                break;
            case 1:
                parcel.writeTypedObject(getKeyCreationWithGeneralInfo(), i);
                break;
            case 2:
                parcel.writeTypedObject(getKeyCreationWithAuthInfo(), i);
                break;
            case 3:
                parcel.writeTypedObject(getKeyCreationWithPurposeAndModesInfo(), i);
                break;
            case 4:
                parcel.writeTypedObject(getKeystore2AtomWithOverflow(), i);
                break;
            case 5:
                parcel.writeTypedObject(getKeyOperationWithPurposeAndModesInfo(), i);
                break;
            case 6:
                parcel.writeTypedObject(getKeyOperationWithGeneralInfo(), i);
                break;
            case 7:
                parcel.writeTypedObject(getRkpErrorStats(), i);
                break;
            case 8:
                parcel.writeTypedObject(getCrashStats(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.security.metrics.StorageStats) parcel.readTypedObject(android.security.metrics.StorageStats.CREATOR));
                return;
            case 1:
                _set(readInt, (android.security.metrics.KeyCreationWithGeneralInfo) parcel.readTypedObject(android.security.metrics.KeyCreationWithGeneralInfo.CREATOR));
                return;
            case 2:
                _set(readInt, (android.security.metrics.KeyCreationWithAuthInfo) parcel.readTypedObject(android.security.metrics.KeyCreationWithAuthInfo.CREATOR));
                return;
            case 3:
                _set(readInt, (android.security.metrics.KeyCreationWithPurposeAndModesInfo) parcel.readTypedObject(android.security.metrics.KeyCreationWithPurposeAndModesInfo.CREATOR));
                return;
            case 4:
                _set(readInt, (android.security.metrics.Keystore2AtomWithOverflow) parcel.readTypedObject(android.security.metrics.Keystore2AtomWithOverflow.CREATOR));
                return;
            case 5:
                _set(readInt, (android.security.metrics.KeyOperationWithPurposeAndModesInfo) parcel.readTypedObject(android.security.metrics.KeyOperationWithPurposeAndModesInfo.CREATOR));
                return;
            case 6:
                _set(readInt, (android.security.metrics.KeyOperationWithGeneralInfo) parcel.readTypedObject(android.security.metrics.KeyOperationWithGeneralInfo.CREATOR));
                return;
            case 7:
                _set(readInt, (android.security.metrics.RkpErrorStats) parcel.readTypedObject(android.security.metrics.RkpErrorStats.CREATOR));
                return;
            case 8:
                _set(readInt, (android.security.metrics.CrashStats) parcel.readTypedObject(android.security.metrics.CrashStats.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getStorageStats());
            case 1:
                return 0 | describeContents(getKeyCreationWithGeneralInfo());
            case 2:
                return 0 | describeContents(getKeyCreationWithAuthInfo());
            case 3:
                return 0 | describeContents(getKeyCreationWithPurposeAndModesInfo());
            case 4:
                return 0 | describeContents(getKeystore2AtomWithOverflow());
            case 5:
                return 0 | describeContents(getKeyOperationWithPurposeAndModesInfo());
            case 6:
                return 0 | describeContents(getKeyOperationWithGeneralInfo());
            case 7:
                return 0 | describeContents(getRkpErrorStats());
            case 8:
                return 0 | describeContents(getCrashStats());
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
                return "storageStats";
            case 1:
                return "keyCreationWithGeneralInfo";
            case 2:
                return "keyCreationWithAuthInfo";
            case 3:
                return "keyCreationWithPurposeAndModesInfo";
            case 4:
                return "keystore2AtomWithOverflow";
            case 5:
                return "keyOperationWithPurposeAndModesInfo";
            case 6:
                return "keyOperationWithGeneralInfo";
            case 7:
                return "rkpErrorStats";
            case 8:
                return "crashStats";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
