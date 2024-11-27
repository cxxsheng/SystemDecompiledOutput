package android.media.tv;

/* loaded from: classes2.dex */
public final class TableResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.TableResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.TableResponse>() { // from class: android.media.tv.TableResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TableResponse createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.TableResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.TableResponse[] newArray(int i) {
            return new android.media.tv.TableResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 2;
    private final int mSize;
    private final byte[] mTableByteArray;
    private final android.os.SharedMemory mTableSharedMemory;
    private final android.net.Uri mTableUri;
    private final int mVersion;

    static android.media.tv.TableResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.TableResponse(parcel);
    }

    @java.lang.Deprecated
    public TableResponse(int i, int i2, int i3, android.net.Uri uri, int i4, int i5) {
        super(2, i, i2, i3);
        this.mVersion = i4;
        this.mSize = i5;
        this.mTableUri = uri;
        this.mTableByteArray = null;
        this.mTableSharedMemory = null;
    }

    private TableResponse(int i, int i2, int i3, int i4, int i5, android.net.Uri uri, byte[] bArr, android.os.SharedMemory sharedMemory) {
        super(2, i, i2, i3);
        this.mVersion = i4;
        this.mSize = i5;
        this.mTableUri = uri;
        this.mTableByteArray = bArr;
        this.mTableSharedMemory = sharedMemory;
    }

    public static final class Builder {
        private final int mRequestId;
        private final int mResponseResult;
        private final int mSequence;
        private final int mSize;
        private byte[] mTableByteArray;
        private android.os.SharedMemory mTableSharedMemory;
        private android.net.Uri mTableUri;
        private final int mVersion;

        public Builder(int i, int i2, int i3, int i4, int i5) {
            this.mRequestId = i;
            this.mSequence = i2;
            this.mResponseResult = i3;
            this.mVersion = i4;
            this.mSize = i5;
        }

        public android.media.tv.TableResponse.Builder setTableUri(android.net.Uri uri) {
            this.mTableUri = uri;
            this.mTableByteArray = null;
            this.mTableSharedMemory = null;
            return this;
        }

        public android.media.tv.TableResponse.Builder setTableByteArray(byte[] bArr) {
            this.mTableByteArray = bArr;
            this.mTableUri = null;
            this.mTableSharedMemory = null;
            return this;
        }

        public android.media.tv.TableResponse.Builder setTableSharedMemory(android.os.SharedMemory sharedMemory) {
            this.mTableSharedMemory = sharedMemory;
            this.mTableUri = null;
            this.mTableByteArray = null;
            return this;
        }

        public android.media.tv.TableResponse build() {
            return new android.media.tv.TableResponse(this.mRequestId, this.mSequence, this.mResponseResult, this.mVersion, this.mSize, this.mTableUri, this.mTableByteArray, this.mTableSharedMemory);
        }
    }

    TableResponse(android.os.Parcel parcel) {
        super(2, parcel);
        java.lang.String readString = parcel.readString();
        this.mTableUri = readString == null ? null : android.net.Uri.parse(readString);
        this.mVersion = parcel.readInt();
        this.mSize = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt >= 0) {
            this.mTableByteArray = new byte[readInt];
            parcel.readByteArray(this.mTableByteArray);
        } else {
            this.mTableByteArray = null;
        }
        this.mTableSharedMemory = (android.os.SharedMemory) parcel.readTypedObject(android.os.SharedMemory.CREATOR);
    }

    public android.net.Uri getTableUri() {
        return this.mTableUri;
    }

    public byte[] getTableByteArray() {
        return this.mTableByteArray;
    }

    public android.os.SharedMemory getTableSharedMemory() {
        return this.mTableSharedMemory;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getSize() {
        return this.mSize;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mTableUri == null ? null : this.mTableUri.toString());
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mSize);
        if (this.mTableByteArray != null) {
            parcel.writeInt(this.mTableByteArray.length);
            parcel.writeByteArray(this.mTableByteArray);
        } else {
            parcel.writeInt(-1);
        }
        parcel.writeTypedObject(this.mTableSharedMemory, i);
    }
}
