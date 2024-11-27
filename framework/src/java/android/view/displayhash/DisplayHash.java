package android.view.displayhash;

/* loaded from: classes4.dex */
public final class DisplayHash implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.displayhash.DisplayHash> CREATOR = new android.os.Parcelable.Creator<android.view.displayhash.DisplayHash>() { // from class: android.view.displayhash.DisplayHash.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.displayhash.DisplayHash[] newArray(int i) {
            return new android.view.displayhash.DisplayHash[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.displayhash.DisplayHash createFromParcel(android.os.Parcel parcel) {
            return new android.view.displayhash.DisplayHash(parcel);
        }
    };
    private final android.graphics.Rect mBoundsInWindow;
    private final java.lang.String mHashAlgorithm;
    private final byte[] mHmac;
    private final byte[] mImageHash;
    private final long mTimeMillis;

    @android.annotation.SystemApi
    public DisplayHash(long j, android.graphics.Rect rect, java.lang.String str, byte[] bArr, byte[] bArr2) {
        this.mTimeMillis = j;
        this.mBoundsInWindow = rect;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBoundsInWindow);
        this.mHashAlgorithm = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHashAlgorithm);
        this.mImageHash = bArr;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mImageHash);
        this.mHmac = bArr2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHmac);
    }

    @android.annotation.SystemApi
    public long getTimeMillis() {
        return this.mTimeMillis;
    }

    @android.annotation.SystemApi
    public android.graphics.Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    @android.annotation.SystemApi
    public java.lang.String getHashAlgorithm() {
        return this.mHashAlgorithm;
    }

    @android.annotation.SystemApi
    public byte[] getImageHash() {
        return this.mImageHash;
    }

    @android.annotation.SystemApi
    public byte[] getHmac() {
        return this.mHmac;
    }

    public java.lang.String toString() {
        return "DisplayHash { timeMillis = " + this.mTimeMillis + ", boundsInWindow = " + this.mBoundsInWindow + ", hashAlgorithm = " + this.mHashAlgorithm + ", imageHash = " + byteArrayToString(this.mImageHash) + ", hmac = " + byteArrayToString(this.mHmac) + " }";
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

    @Override // android.os.Parcelable
    @android.annotation.SystemApi
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mTimeMillis);
        parcel.writeTypedObject(this.mBoundsInWindow, i);
        parcel.writeString(this.mHashAlgorithm);
        parcel.writeByteArray(this.mImageHash);
        parcel.writeByteArray(this.mHmac);
    }

    @Override // android.os.Parcelable
    @android.annotation.SystemApi
    public int describeContents() {
        return 0;
    }

    private DisplayHash(android.os.Parcel parcel) {
        this.mTimeMillis = parcel.readLong();
        android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
        java.lang.String readString = parcel.readString();
        byte[] createByteArray = parcel.createByteArray();
        byte[] createByteArray2 = parcel.createByteArray();
        this.mBoundsInWindow = rect;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBoundsInWindow);
        this.mHashAlgorithm = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHashAlgorithm);
        this.mImageHash = createByteArray;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mImageHash);
        this.mHmac = createByteArray2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHmac);
    }
}
