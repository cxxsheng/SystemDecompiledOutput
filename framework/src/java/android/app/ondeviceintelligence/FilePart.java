package android.app.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class FilePart implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ondeviceintelligence.FilePart> CREATOR = new android.os.Parcelable.Creator<android.app.ondeviceintelligence.FilePart>() { // from class: android.app.ondeviceintelligence.FilePart.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ondeviceintelligence.FilePart createFromParcel(android.os.Parcel parcel) {
            return new android.app.ondeviceintelligence.FilePart(parcel.readString(), (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR), (android.os.ParcelFileDescriptor) parcel.readParcelable(getClass().getClassLoader(), android.os.ParcelFileDescriptor.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ondeviceintelligence.FilePart[] newArray(int i) {
            return new android.app.ondeviceintelligence.FilePart[i];
        }
    };
    private final android.os.ParcelFileDescriptor mParcelFileDescriptor;
    private final java.lang.String mPartKey;
    private final android.os.PersistableBundle mPartParams;

    private FilePart(java.lang.String str, android.os.PersistableBundle persistableBundle, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(persistableBundle);
        this.mPartKey = str;
        this.mPartParams = persistableBundle;
        this.mParcelFileDescriptor = (android.os.ParcelFileDescriptor) java.util.Objects.requireNonNull(parcelFileDescriptor);
    }

    public FilePart(java.lang.String str, android.os.PersistableBundle persistableBundle, java.lang.String str2) throws java.io.FileNotFoundException {
        this(str, persistableBundle, (android.os.ParcelFileDescriptor) java.util.Objects.requireNonNull(android.os.ParcelFileDescriptor.open(new java.io.File((java.lang.String) java.util.Objects.requireNonNull(str2)), 268435456)));
    }

    public FilePart(java.lang.String str, android.os.PersistableBundle persistableBundle, java.io.FileInputStream fileInputStream) throws java.io.IOException {
        this(str, persistableBundle, android.os.ParcelFileDescriptor.dup(fileInputStream.getFD()));
    }

    public java.io.FileInputStream getFileInputStream() {
        return new java.io.FileInputStream(this.mParcelFileDescriptor.getFileDescriptor());
    }

    public java.lang.String getFilePartKey() {
        return this.mPartKey;
    }

    public android.os.PersistableBundle getFilePartParams() {
        return this.mPartParams;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(getFilePartKey());
        parcel.writePersistableBundle(getFilePartParams());
        this.mParcelFileDescriptor.writeToParcel(parcel, i | 1);
    }
}
