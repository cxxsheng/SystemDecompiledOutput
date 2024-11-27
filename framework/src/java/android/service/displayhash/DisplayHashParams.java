package android.service.displayhash;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class DisplayHashParams implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.displayhash.DisplayHashParams> CREATOR = new android.os.Parcelable.Creator<android.service.displayhash.DisplayHashParams>() { // from class: android.service.displayhash.DisplayHashParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.displayhash.DisplayHashParams[] newArray(int i) {
            return new android.service.displayhash.DisplayHashParams[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.displayhash.DisplayHashParams createFromParcel(android.os.Parcel parcel) {
            return new android.service.displayhash.DisplayHashParams(parcel);
        }
    };
    private final android.util.Size mBufferSize;
    private final boolean mGrayscaleBuffer;

    public static final class Builder {
        private android.util.Size mBufferSize;
        private boolean mGrayscaleBuffer;

        public android.service.displayhash.DisplayHashParams.Builder setBufferSize(int i, int i2) {
            this.mBufferSize = new android.util.Size(i, i2);
            return this;
        }

        public android.service.displayhash.DisplayHashParams.Builder setGrayscaleBuffer(boolean z) {
            this.mGrayscaleBuffer = z;
            return this;
        }

        public android.service.displayhash.DisplayHashParams build() {
            return new android.service.displayhash.DisplayHashParams(this.mBufferSize, this.mGrayscaleBuffer);
        }
    }

    public DisplayHashParams(android.util.Size size, boolean z) {
        this.mBufferSize = size;
        this.mGrayscaleBuffer = z;
    }

    public android.util.Size getBufferSize() {
        return this.mBufferSize;
    }

    public boolean isGrayscaleBuffer() {
        return this.mGrayscaleBuffer;
    }

    public java.lang.String toString() {
        return "DisplayHashParams { bufferSize = " + this.mBufferSize + ", grayscaleBuffer = " + this.mGrayscaleBuffer + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mGrayscaleBuffer ? (byte) 2 : (byte) 0;
        if (this.mBufferSize != null) {
            b = (byte) (b | 1);
        }
        parcel.writeByte(b);
        if (this.mBufferSize != null) {
            parcel.writeSize(this.mBufferSize);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    DisplayHashParams(android.os.Parcel parcel) {
        byte readByte = parcel.readByte();
        boolean z = (readByte & 2) != 0;
        this.mBufferSize = (readByte & 1) == 0 ? null : parcel.readSize();
        this.mGrayscaleBuffer = z;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
