package android.app;

/* loaded from: classes.dex */
public final class BackgroundServiceStartNotAllowedException extends android.app.ServiceStartNotAllowedException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.BackgroundServiceStartNotAllowedException> CREATOR = new android.os.Parcelable.Creator<android.app.BackgroundServiceStartNotAllowedException>() { // from class: android.app.BackgroundServiceStartNotAllowedException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.BackgroundServiceStartNotAllowedException createFromParcel(android.os.Parcel parcel) {
            return new android.app.BackgroundServiceStartNotAllowedException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.BackgroundServiceStartNotAllowedException[] newArray(int i) {
            return new android.app.BackgroundServiceStartNotAllowedException[i];
        }
    };

    public BackgroundServiceStartNotAllowedException(java.lang.String str) {
        super(str);
    }

    BackgroundServiceStartNotAllowedException(android.os.Parcel parcel) {
        super(parcel.readString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getMessage());
    }
}
