package android.os;

/* loaded from: classes3.dex */
public class ParcelableParcel implements android.os.Parcelable {
    public static final android.os.Parcelable.ClassLoaderCreator<android.os.ParcelableParcel> CREATOR = new android.os.Parcelable.ClassLoaderCreator<android.os.ParcelableParcel>() { // from class: android.os.ParcelableParcel.1
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelableParcel createFromParcel(android.os.Parcel parcel) {
            return new android.os.ParcelableParcel(parcel, null);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.ClassLoaderCreator
        public android.os.ParcelableParcel createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            return new android.os.ParcelableParcel(parcel, classLoader);
        }

        @Override // android.os.Parcelable.Creator
        public android.os.ParcelableParcel[] newArray(int i) {
            return new android.os.ParcelableParcel[i];
        }
    };
    final java.lang.ClassLoader mClassLoader;
    final android.os.Parcel mParcel = android.os.Parcel.obtain();

    public ParcelableParcel(java.lang.ClassLoader classLoader) {
        this.mClassLoader = classLoader;
    }

    public ParcelableParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
        this.mClassLoader = classLoader;
        int readInt = parcel.readInt();
        if (readInt < 0) {
            throw new java.lang.IllegalArgumentException("Negative size read from parcel");
        }
        int dataPosition = parcel.dataPosition();
        parcel.setDataPosition(android.util.MathUtils.addOrThrow(dataPosition, readInt));
        this.mParcel.appendFrom(parcel, dataPosition, readInt);
    }

    public android.os.Parcel getParcel() {
        this.mParcel.setDataPosition(0);
        return this.mParcel;
    }

    public java.lang.ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mParcel.dataSize());
        parcel.appendFrom(this.mParcel, 0, this.mParcel.dataSize());
    }
}
