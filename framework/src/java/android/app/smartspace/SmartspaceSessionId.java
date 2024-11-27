package android.app.smartspace;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SmartspaceSessionId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.smartspace.SmartspaceSessionId> CREATOR = new android.os.Parcelable.Creator<android.app.smartspace.SmartspaceSessionId>() { // from class: android.app.smartspace.SmartspaceSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceSessionId createFromParcel(android.os.Parcel parcel) {
            return new android.app.smartspace.SmartspaceSessionId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.smartspace.SmartspaceSessionId[] newArray(int i) {
            return new android.app.smartspace.SmartspaceSessionId[i];
        }
    };
    private final java.lang.String mId;
    private final android.os.UserHandle mUserHandle;

    public SmartspaceSessionId(java.lang.String str, android.os.UserHandle userHandle) {
        this.mId = str;
        this.mUserHandle = userHandle;
    }

    private SmartspaceSessionId(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mUserHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public boolean equals(java.lang.Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        android.app.smartspace.SmartspaceSessionId smartspaceSessionId = (android.app.smartspace.SmartspaceSessionId) obj;
        return this.mId.equals(smartspaceSessionId.mId) && this.mUserHandle == smartspaceSessionId.mUserHandle;
    }

    public java.lang.String toString() {
        return "SmartspaceSessionId{mId='" + this.mId + android.text.format.DateFormat.QUOTE + ", mUserId=" + this.mUserHandle.getIdentifier() + '}';
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId, this.mUserHandle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeTypedObject(this.mUserHandle, i);
    }
}
