package android.app;

/* loaded from: classes.dex */
public final class AuthenticationRequiredException extends java.lang.SecurityException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.AuthenticationRequiredException> CREATOR = new android.os.Parcelable.Creator<android.app.AuthenticationRequiredException>() { // from class: android.app.AuthenticationRequiredException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.AuthenticationRequiredException createFromParcel(android.os.Parcel parcel) {
            return new android.app.AuthenticationRequiredException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.AuthenticationRequiredException[] newArray(int i) {
            return new android.app.AuthenticationRequiredException[i];
        }
    };
    private static final java.lang.String TAG = "AuthenticationRequiredException";
    private final android.app.PendingIntent mUserAction;

    public AuthenticationRequiredException(android.os.Parcel parcel) {
        this(new java.lang.SecurityException(parcel.readString()), android.app.PendingIntent.CREATOR.createFromParcel(parcel));
    }

    public AuthenticationRequiredException(java.lang.Throwable th, android.app.PendingIntent pendingIntent) {
        super(th.getMessage());
        this.mUserAction = (android.app.PendingIntent) java.util.Objects.requireNonNull(pendingIntent);
    }

    public android.app.PendingIntent getUserAction() {
        return this.mUserAction;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getMessage());
        this.mUserAction.writeToParcel(parcel, i);
    }
}
