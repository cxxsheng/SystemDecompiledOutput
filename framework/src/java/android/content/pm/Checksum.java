package android.content.pm;

/* loaded from: classes.dex */
public final class Checksum implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.Checksum> CREATOR = new android.os.Parcelable.Creator<android.content.pm.Checksum>() { // from class: android.content.pm.Checksum.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.Checksum[] newArray(int i) {
            return new android.content.pm.Checksum[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.Checksum createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.Checksum(parcel);
        }
    };
    public static final int MAX_CHECKSUM_SIZE_BYTES = 64;
    public static final int TYPE_PARTIAL_MERKLE_ROOT_1M_SHA256 = 32;
    public static final int TYPE_PARTIAL_MERKLE_ROOT_1M_SHA512 = 64;

    @java.lang.Deprecated
    public static final int TYPE_WHOLE_MD5 = 2;
    public static final int TYPE_WHOLE_MERKLE_ROOT_4K_SHA256 = 1;

    @java.lang.Deprecated
    public static final int TYPE_WHOLE_SHA1 = 4;

    @java.lang.Deprecated
    public static final int TYPE_WHOLE_SHA256 = 8;

    @java.lang.Deprecated
    public static final int TYPE_WHOLE_SHA512 = 16;
    private final int mType;
    private final byte[] mValue;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TypeMask {
    }

    public static void writeToStream(java.io.DataOutputStream dataOutputStream, android.content.pm.Checksum checksum) throws java.io.IOException {
        dataOutputStream.writeInt(checksum.getType());
        byte[] value = checksum.getValue();
        dataOutputStream.writeInt(value.length);
        dataOutputStream.write(value);
    }

    public static android.content.pm.Checksum readFromStream(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        int readInt = dataInputStream.readInt();
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.read(bArr);
        return new android.content.pm.Checksum(readInt, bArr);
    }

    public Checksum(int i, byte[] bArr) {
        this.mType = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.Checksum.Type.class, (java.lang.annotation.Annotation) null, this.mType);
        this.mValue = bArr;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mValue);
    }

    public int getType() {
        return this.mType;
    }

    public byte[] getValue() {
        return this.mValue;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeByteArray(this.mValue);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Checksum(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        byte[] createByteArray = parcel.createByteArray();
        this.mType = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.Checksum.Type.class, (java.lang.annotation.Annotation) null, this.mType);
        this.mValue = createByteArray;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mValue);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
