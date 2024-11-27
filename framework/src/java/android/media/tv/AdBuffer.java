package android.media.tv;

/* loaded from: classes2.dex */
public final class AdBuffer implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.AdBuffer> CREATOR = new android.os.Parcelable.Creator<android.media.tv.AdBuffer>() { // from class: android.media.tv.AdBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.AdBuffer[] newArray(int i) {
            return new android.media.tv.AdBuffer[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.AdBuffer createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.AdBuffer(parcel);
        }
    };
    private final android.os.SharedMemory mBuffer;
    private final int mFlags;
    private final int mId;
    private final int mLength;
    private final java.lang.String mMimeType;
    private final int mOffset;
    private final long mPresentationTimeUs;

    public AdBuffer(int i, java.lang.String str, android.os.SharedMemory sharedMemory, int i2, int i3, long j, int i4) {
        this.mId = i;
        this.mMimeType = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
        this.mBuffer = sharedMemory;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) sharedMemory);
        this.mOffset = i2;
        this.mLength = i3;
        this.mPresentationTimeUs = j;
        this.mFlags = i4;
    }

    public static android.media.tv.AdBuffer dupAdBuffer(android.media.tv.AdBuffer adBuffer) throws java.io.IOException {
        if (adBuffer == null) {
            return null;
        }
        return new android.media.tv.AdBuffer(adBuffer.mId, adBuffer.mMimeType, android.os.SharedMemory.fromFileDescriptor(adBuffer.mBuffer.getFdDup()), adBuffer.mOffset, adBuffer.mLength, adBuffer.mPresentationTimeUs, adBuffer.mFlags);
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String getMimeType() {
        return this.mMimeType;
    }

    public android.os.SharedMemory getSharedMemory() {
        return this.mBuffer;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public int getLength() {
        return this.mLength;
    }

    public long getPresentationTimeUs() {
        return this.mPresentationTimeUs;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mMimeType);
        parcel.writeTypedObject(this.mBuffer, i);
        parcel.writeInt(this.mOffset);
        parcel.writeInt(this.mLength);
        parcel.writeLong(this.mPresentationTimeUs);
        parcel.writeInt(this.mFlags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AdBuffer(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.lang.String readString = parcel.readString();
        android.os.SharedMemory sharedMemory = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        long readLong = parcel.readLong();
        int readInt4 = parcel.readInt();
        this.mId = readInt;
        this.mMimeType = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMimeType);
        this.mBuffer = sharedMemory;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBuffer);
        this.mOffset = readInt2;
        this.mLength = readInt3;
        this.mPresentationTimeUs = readLong;
        this.mFlags = readInt4;
    }
}
