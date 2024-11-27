package android.view.displayhash;

/* loaded from: classes4.dex */
public final class VerifiedDisplayHash implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.displayhash.VerifiedDisplayHash> CREATOR = new android.os.Parcelable.Creator<android.view.displayhash.VerifiedDisplayHash>() { // from class: android.view.displayhash.VerifiedDisplayHash.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.displayhash.VerifiedDisplayHash[] newArray(int i) {
            return new android.view.displayhash.VerifiedDisplayHash[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.displayhash.VerifiedDisplayHash createFromParcel(android.os.Parcel parcel) {
            return new android.view.displayhash.VerifiedDisplayHash(parcel);
        }
    };
    private final android.graphics.Rect mBoundsInWindow;
    private final java.lang.String mHashAlgorithm;
    private final byte[] mImageHash;
    private final long mTimeMillis;

    private java.lang.String imageHashToString() {
        return byteArrayToString(this.mImageHash);
    }

    private java.lang.String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        int length = bArr.length - 1;
        if (length == -1) {
            return "[]";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append('[');
        int i = 0;
        while (true) {
            sb.append(java.lang.String.format("%02X", java.lang.Integer.valueOf(bArr[i] & 255)));
            if (i == length) {
                return sb.append(']').toString();
            }
            sb.append(", ");
            i++;
        }
    }

    public VerifiedDisplayHash(long j, android.graphics.Rect rect, java.lang.String str, byte[] bArr) {
        this.mTimeMillis = j;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.CurrentTimeMillisLong.class, (java.lang.annotation.Annotation) null, this.mTimeMillis);
        this.mBoundsInWindow = rect;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBoundsInWindow);
        this.mHashAlgorithm = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHashAlgorithm);
        this.mImageHash = bArr;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mImageHash);
    }

    public long getTimeMillis() {
        return this.mTimeMillis;
    }

    public android.graphics.Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    public java.lang.String getHashAlgorithm() {
        return this.mHashAlgorithm;
    }

    public byte[] getImageHash() {
        return this.mImageHash;
    }

    public java.lang.String toString() {
        return "VerifiedDisplayHash { timeMillis = " + this.mTimeMillis + ", boundsInWindow = " + this.mBoundsInWindow + ", hashAlgorithm = " + this.mHashAlgorithm + ", imageHash = " + imageHashToString() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mTimeMillis);
        parcel.writeTypedObject(this.mBoundsInWindow, i);
        parcel.writeString(this.mHashAlgorithm);
        parcel.writeByteArray(this.mImageHash);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    VerifiedDisplayHash(android.os.Parcel parcel) {
        long readLong = parcel.readLong();
        android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        java.lang.String readString = parcel.readString();
        byte[] createByteArray = parcel.createByteArray();
        this.mTimeMillis = readLong;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.CurrentTimeMillisLong.class, (java.lang.annotation.Annotation) null, this.mTimeMillis);
        this.mBoundsInWindow = rect;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBoundsInWindow);
        this.mHashAlgorithm = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHashAlgorithm);
        this.mImageHash = createByteArray;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mImageHash);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
