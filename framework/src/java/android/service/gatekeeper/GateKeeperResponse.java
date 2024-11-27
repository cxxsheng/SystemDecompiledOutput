package android.service.gatekeeper;

/* loaded from: classes3.dex */
public final class GateKeeperResponse implements android.os.Parcelable {
    public static final int RESPONSE_ERROR = -1;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_RETRY = 1;
    private byte[] mPayload;
    private final int mResponseCode;
    private boolean mShouldReEnroll;
    private int mTimeout;
    public static final android.service.gatekeeper.GateKeeperResponse ERROR = createGenericResponse(-1);
    public static final android.os.Parcelable.Creator<android.service.gatekeeper.GateKeeperResponse> CREATOR = new android.os.Parcelable.Creator<android.service.gatekeeper.GateKeeperResponse>() { // from class: android.service.gatekeeper.GateKeeperResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.gatekeeper.GateKeeperResponse createFromParcel(android.os.Parcel parcel) {
            byte[] bArr;
            int readInt = parcel.readInt();
            if (readInt == 1) {
                return android.service.gatekeeper.GateKeeperResponse.createRetryResponse(parcel.readInt());
            }
            if (readInt == 0) {
                boolean z = parcel.readInt() == 1;
                int readInt2 = parcel.readInt();
                if (readInt2 <= 0) {
                    bArr = null;
                } else {
                    bArr = new byte[readInt2];
                    parcel.readByteArray(bArr);
                }
                return android.service.gatekeeper.GateKeeperResponse.createOkResponse(bArr, z);
            }
            return android.service.gatekeeper.GateKeeperResponse.createGenericResponse(readInt);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.gatekeeper.GateKeeperResponse[] newArray(int i) {
            return new android.service.gatekeeper.GateKeeperResponse[i];
        }
    };

    private GateKeeperResponse(int i) {
        this.mResponseCode = i;
    }

    public static android.service.gatekeeper.GateKeeperResponse createGenericResponse(int i) {
        return new android.service.gatekeeper.GateKeeperResponse(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.gatekeeper.GateKeeperResponse createRetryResponse(int i) {
        android.service.gatekeeper.GateKeeperResponse gateKeeperResponse = new android.service.gatekeeper.GateKeeperResponse(1);
        gateKeeperResponse.mTimeout = i;
        return gateKeeperResponse;
    }

    public static android.service.gatekeeper.GateKeeperResponse createOkResponse(byte[] bArr, boolean z) {
        android.service.gatekeeper.GateKeeperResponse gateKeeperResponse = new android.service.gatekeeper.GateKeeperResponse(0);
        gateKeeperResponse.mPayload = bArr;
        gateKeeperResponse.mShouldReEnroll = z;
        return gateKeeperResponse;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResponseCode);
        if (this.mResponseCode == 1) {
            parcel.writeInt(this.mTimeout);
            return;
        }
        if (this.mResponseCode == 0) {
            parcel.writeInt(this.mShouldReEnroll ? 1 : 0);
            if (this.mPayload != null && this.mPayload.length > 0) {
                parcel.writeInt(this.mPayload.length);
                parcel.writeByteArray(this.mPayload);
            } else {
                parcel.writeInt(0);
            }
        }
    }

    public byte[] getPayload() {
        return this.mPayload;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public boolean getShouldReEnroll() {
        return this.mShouldReEnroll;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }
}
