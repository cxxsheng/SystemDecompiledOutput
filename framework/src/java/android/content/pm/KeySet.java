package android.content.pm;

/* loaded from: classes.dex */
public class KeySet implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.KeySet> CREATOR = new android.os.Parcelable.Creator<android.content.pm.KeySet>() { // from class: android.content.pm.KeySet.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.KeySet createFromParcel(android.os.Parcel parcel) {
            return android.content.pm.KeySet.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.KeySet[] newArray(int i) {
            return new android.content.pm.KeySet[i];
        }
    };
    private final android.os.IBinder token;

    public KeySet(android.os.IBinder iBinder) {
        if (iBinder == null) {
            throw new java.lang.NullPointerException("null value for KeySet IBinder token");
        }
        this.token = iBinder;
    }

    public android.os.IBinder getToken() {
        return this.token;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.content.pm.KeySet) && this.token == ((android.content.pm.KeySet) obj).token;
    }

    public int hashCode() {
        return this.token.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.pm.KeySet readFromParcel(android.os.Parcel parcel) {
        return new android.content.pm.KeySet(parcel.readStrongBinder());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.token);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
