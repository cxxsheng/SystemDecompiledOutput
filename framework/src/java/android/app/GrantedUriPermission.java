package android.app;

/* loaded from: classes.dex */
public class GrantedUriPermission implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.GrantedUriPermission> CREATOR = new android.os.Parcelable.Creator<android.app.GrantedUriPermission>() { // from class: android.app.GrantedUriPermission.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.GrantedUriPermission createFromParcel(android.os.Parcel parcel) {
            return new android.app.GrantedUriPermission(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.GrantedUriPermission[] newArray(int i) {
            return new android.app.GrantedUriPermission[i];
        }
    };
    public final java.lang.String packageName;
    public final android.net.Uri uri;

    public GrantedUriPermission(android.net.Uri uri, java.lang.String str) {
        this.uri = uri;
        this.packageName = str;
    }

    public java.lang.String toString() {
        return this.packageName + ":" + this.uri;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.uri, i);
        parcel.writeString(this.packageName);
    }

    private GrantedUriPermission(android.os.Parcel parcel) {
        this.uri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
        this.packageName = parcel.readString();
    }
}
