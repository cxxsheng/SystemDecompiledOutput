package android.window;

/* loaded from: classes4.dex */
public final class TaskFragmentOrganizerToken implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.TaskFragmentOrganizerToken> CREATOR = new android.os.Parcelable.Creator<android.window.TaskFragmentOrganizerToken>() { // from class: android.window.TaskFragmentOrganizerToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentOrganizerToken createFromParcel(android.os.Parcel parcel) {
            android.window.ITaskFragmentOrganizer asInterface = android.window.ITaskFragmentOrganizer.Stub.asInterface(parcel.readStrongBinder());
            if (asInterface == null) {
                return null;
            }
            return new android.window.TaskFragmentOrganizerToken(asInterface);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.TaskFragmentOrganizerToken[] newArray(int i) {
            return new android.window.TaskFragmentOrganizerToken[i];
        }
    };
    private final android.window.ITaskFragmentOrganizer mRealToken;

    TaskFragmentOrganizerToken(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer) {
        this.mRealToken = iTaskFragmentOrganizer;
    }

    public android.os.IBinder asBinder() {
        return this.mRealToken.asBinder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mRealToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return this.mRealToken.asBinder().hashCode();
    }

    public java.lang.String toString() {
        return "TaskFragmentOrganizerToken{" + this.mRealToken + "}";
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.window.TaskFragmentOrganizerToken) && this.mRealToken.asBinder() == ((android.window.TaskFragmentOrganizerToken) obj).asBinder();
    }
}
