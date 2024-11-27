package android.app;

/* loaded from: classes.dex */
public final class InvalidForegroundServiceTypeException extends android.app.ForegroundServiceTypeException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.InvalidForegroundServiceTypeException> CREATOR = new android.os.Parcelable.Creator<android.app.InvalidForegroundServiceTypeException>() { // from class: android.app.InvalidForegroundServiceTypeException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.InvalidForegroundServiceTypeException createFromParcel(android.os.Parcel parcel) {
            return new android.app.InvalidForegroundServiceTypeException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.InvalidForegroundServiceTypeException[] newArray(int i) {
            return new android.app.InvalidForegroundServiceTypeException[i];
        }
    };

    public InvalidForegroundServiceTypeException(java.lang.String str) {
        super(str);
    }

    InvalidForegroundServiceTypeException(android.os.Parcel parcel) {
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
