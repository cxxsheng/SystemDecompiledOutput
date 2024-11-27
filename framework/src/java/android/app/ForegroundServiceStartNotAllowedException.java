package android.app;

/* loaded from: classes.dex */
public final class ForegroundServiceStartNotAllowedException extends android.app.ServiceStartNotAllowedException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ForegroundServiceStartNotAllowedException> CREATOR = new android.os.Parcelable.Creator<android.app.ForegroundServiceStartNotAllowedException>() { // from class: android.app.ForegroundServiceStartNotAllowedException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ForegroundServiceStartNotAllowedException createFromParcel(android.os.Parcel parcel) {
            return new android.app.ForegroundServiceStartNotAllowedException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ForegroundServiceStartNotAllowedException[] newArray(int i) {
            return new android.app.ForegroundServiceStartNotAllowedException[i];
        }
    };

    public ForegroundServiceStartNotAllowedException(java.lang.String str) {
        super(str);
    }

    ForegroundServiceStartNotAllowedException(android.os.Parcel parcel) {
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
