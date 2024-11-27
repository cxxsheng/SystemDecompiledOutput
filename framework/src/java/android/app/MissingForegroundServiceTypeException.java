package android.app;

/* loaded from: classes.dex */
public final class MissingForegroundServiceTypeException extends android.app.ForegroundServiceTypeException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.MissingForegroundServiceTypeException> CREATOR = new android.os.Parcelable.Creator<android.app.MissingForegroundServiceTypeException>() { // from class: android.app.MissingForegroundServiceTypeException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.MissingForegroundServiceTypeException createFromParcel(android.os.Parcel parcel) {
            return new android.app.MissingForegroundServiceTypeException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.MissingForegroundServiceTypeException[] newArray(int i) {
            return new android.app.MissingForegroundServiceTypeException[i];
        }
    };

    public MissingForegroundServiceTypeException(java.lang.String str) {
        super(str);
    }

    MissingForegroundServiceTypeException(android.os.Parcel parcel) {
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
