package android.os;

/* loaded from: classes3.dex */
public final class ParcelableException extends java.lang.RuntimeException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.ParcelableException> CREATOR = new android.os.Parcelable.Creator<android.os.ParcelableException>() { // from class: android.os.ParcelableException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelableException createFromParcel(android.os.Parcel parcel) {
            return new android.os.ParcelableException(android.os.ParcelableException.readFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.ParcelableException[] newArray(int i) {
            return new android.os.ParcelableException[i];
        }
    };

    public ParcelableException(java.lang.Throwable th) {
        super(th);
    }

    public <T extends java.lang.Throwable> void maybeRethrow(java.lang.Class<T> cls) throws java.lang.Throwable {
        if (cls.isAssignableFrom(getCause().getClass())) {
            throw getCause();
        }
    }

    public static java.lang.Throwable readFromParcel(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        java.lang.String readString2 = parcel.readString();
        try {
            java.lang.Class<?> cls = java.lang.Class.forName(readString, true, android.os.Parcelable.class.getClassLoader());
            if (java.lang.Throwable.class.isAssignableFrom(cls)) {
                return (java.lang.Throwable) cls.getConstructor(java.lang.String.class).newInstance(readString2);
            }
        } catch (java.lang.ReflectiveOperationException e) {
        }
        return new java.lang.RuntimeException(readString + ": " + readString2);
    }

    public static void writeToParcel(android.os.Parcel parcel, java.lang.Throwable th) {
        parcel.writeString(th.getClass().getName());
        parcel.writeString(th.getMessage());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcel(parcel, getCause());
    }
}
