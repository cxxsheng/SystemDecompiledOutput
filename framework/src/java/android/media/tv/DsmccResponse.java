package android.media.tv;

/* loaded from: classes2.dex */
public final class DsmccResponse extends android.media.tv.BroadcastInfoResponse implements android.os.Parcelable {
    public static final java.lang.String BIOP_MESSAGE_TYPE_DIRECTORY = "directory";
    public static final java.lang.String BIOP_MESSAGE_TYPE_FILE = "file";
    public static final java.lang.String BIOP_MESSAGE_TYPE_SERVICE_GATEWAY = "service_gateway";
    public static final java.lang.String BIOP_MESSAGE_TYPE_STREAM = "stream";
    public static final android.os.Parcelable.Creator<android.media.tv.DsmccResponse> CREATOR = new android.os.Parcelable.Creator<android.media.tv.DsmccResponse>() { // from class: android.media.tv.DsmccResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.DsmccResponse createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.media.tv.DsmccResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.DsmccResponse[] newArray(int i) {
            return new android.media.tv.DsmccResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 6;
    private final java.lang.String mBiopMessageType;
    private final java.util.List<java.lang.String> mChildList;
    private final int[] mEventIds;
    private final java.lang.String[] mEventNames;
    private final android.os.ParcelFileDescriptor mFileDescriptor;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BiopMessageType {
    }

    static android.media.tv.DsmccResponse createFromParcelBody(android.os.Parcel parcel) {
        return new android.media.tv.DsmccResponse(parcel);
    }

    public DsmccResponse(int i, int i2, int i3, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        super(6, i, i2, i3);
        this.mBiopMessageType = "file";
        this.mFileDescriptor = parcelFileDescriptor;
        this.mChildList = null;
        this.mEventIds = null;
        this.mEventNames = null;
    }

    public DsmccResponse(int i, int i2, int i3, boolean z, java.util.List<java.lang.String> list) {
        super(6, i, i2, i3);
        if (z) {
            this.mBiopMessageType = BIOP_MESSAGE_TYPE_SERVICE_GATEWAY;
        } else {
            this.mBiopMessageType = "directory";
        }
        this.mFileDescriptor = null;
        this.mChildList = list;
        this.mEventIds = null;
        this.mEventNames = null;
    }

    public DsmccResponse(int i, int i2, int i3, int[] iArr, java.lang.String[] strArr) {
        super(6, i, i2, i3);
        this.mBiopMessageType = "stream";
        this.mFileDescriptor = null;
        this.mChildList = null;
        if ((iArr == null || strArr == null || iArr.length != strArr.length) && (iArr != null || strArr != null)) {
            throw new java.lang.IllegalStateException("The size of eventIds and eventNames must be equal");
        }
        this.mEventIds = iArr;
        this.mEventNames = strArr;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private DsmccResponse(android.os.Parcel parcel) {
        super(6, parcel);
        char c;
        this.mBiopMessageType = parcel.readString();
        java.lang.String str = this.mBiopMessageType;
        int i = 0;
        switch (str.hashCode()) {
            case -962584979:
                if (str.equals("directory")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -959828294:
                if (str.equals(BIOP_MESSAGE_TYPE_SERVICE_GATEWAY)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -891990144:
                if (str.equals("stream")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3143036:
                if (str.equals("file")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    this.mChildList = new java.util.ArrayList();
                    while (i < readInt) {
                        this.mChildList.add(parcel.readString());
                        i++;
                    }
                } else {
                    this.mChildList = null;
                }
                this.mFileDescriptor = null;
                this.mEventIds = null;
                this.mEventNames = null;
                return;
            case 2:
                this.mFileDescriptor = parcel.readFileDescriptor();
                this.mChildList = null;
                this.mEventIds = null;
                this.mEventNames = null;
                return;
            case 3:
                int readInt2 = parcel.readInt();
                if (readInt2 > 0) {
                    this.mEventIds = new int[readInt2];
                    this.mEventNames = new java.lang.String[readInt2];
                    while (i < readInt2) {
                        this.mEventIds[i] = parcel.readInt();
                        this.mEventNames[i] = parcel.readString();
                        i++;
                    }
                } else {
                    this.mEventIds = null;
                    this.mEventNames = null;
                }
                this.mChildList = null;
                this.mFileDescriptor = null;
                return;
            default:
                throw new java.lang.IllegalStateException("unexpected BIOP message type");
        }
    }

    public java.lang.String getBiopMessageType() {
        return this.mBiopMessageType;
    }

    public android.os.ParcelFileDescriptor getFile() {
        if (!this.mBiopMessageType.equals("file")) {
            throw new java.lang.IllegalStateException("Not file object");
        }
        return this.mFileDescriptor;
    }

    public java.util.List<java.lang.String> getChildList() {
        if (this.mBiopMessageType.equals("directory") || this.mBiopMessageType.equals(BIOP_MESSAGE_TYPE_SERVICE_GATEWAY)) {
            return this.mChildList != null ? new java.util.ArrayList(this.mChildList) : new java.util.ArrayList();
        }
        throw new java.lang.IllegalStateException("Not directory object");
    }

    public int[] getStreamEventIds() {
        if (this.mBiopMessageType.equals("stream")) {
            return this.mEventIds != null ? this.mEventIds : new int[0];
        }
        throw new java.lang.IllegalStateException("Not stream event object");
    }

    public java.lang.String[] getStreamEventNames() {
        if (this.mBiopMessageType.equals("stream")) {
            return this.mEventNames != null ? this.mEventNames : new java.lang.String[0];
        }
        throw new java.lang.IllegalStateException("Not stream event object");
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        char c;
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mBiopMessageType);
        java.lang.String str = this.mBiopMessageType;
        switch (str.hashCode()) {
            case -962584979:
                if (str.equals("directory")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -959828294:
                if (str.equals(BIOP_MESSAGE_TYPE_SERVICE_GATEWAY)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -891990144:
                if (str.equals("stream")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3143036:
                if (str.equals("file")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                if (this.mChildList != null && this.mChildList.size() > 0) {
                    parcel.writeInt(this.mChildList.size());
                    java.util.Iterator<java.lang.String> it = this.mChildList.iterator();
                    while (it.hasNext()) {
                        parcel.writeString(it.next());
                    }
                    return;
                }
                parcel.writeInt(0);
                return;
            case 2:
                parcel.writeFileDescriptor(this.mFileDescriptor.getFileDescriptor());
                return;
            case 3:
                if (this.mEventIds != null && this.mEventIds.length > 0) {
                    parcel.writeInt(this.mEventIds.length);
                    for (int i2 = 0; i2 < this.mEventIds.length; i2++) {
                        parcel.writeInt(this.mEventIds[i2]);
                        parcel.writeString(this.mEventNames[i2]);
                    }
                    return;
                }
                parcel.writeInt(0);
                return;
            default:
                throw new java.lang.IllegalStateException("unexpected BIOP message type");
        }
    }
}
