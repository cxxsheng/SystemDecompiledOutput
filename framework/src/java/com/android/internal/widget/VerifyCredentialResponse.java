package com.android.internal.widget;

/* loaded from: classes5.dex */
public final class VerifyCredentialResponse implements android.os.Parcelable {
    public static final int RESPONSE_ERROR = -1;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_RETRY = 1;
    private static final java.lang.String TAG = "VerifyCredentialResponse";
    private final byte[] mGatekeeperHAT;
    private final long mGatekeeperPasswordHandle;
    private final int mResponseCode;
    private final int mTimeout;
    public static final com.android.internal.widget.VerifyCredentialResponse OK = new com.android.internal.widget.VerifyCredentialResponse.Builder().build();
    public static final com.android.internal.widget.VerifyCredentialResponse ERROR = fromError();
    public static final android.os.Parcelable.Creator<com.android.internal.widget.VerifyCredentialResponse> CREATOR = new android.os.Parcelable.Creator<com.android.internal.widget.VerifyCredentialResponse>() { // from class: com.android.internal.widget.VerifyCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.widget.VerifyCredentialResponse createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.widget.VerifyCredentialResponse(parcel.readInt(), parcel.readInt(), parcel.createByteArray(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.widget.VerifyCredentialResponse[] newArray(int i) {
            return new com.android.internal.widget.VerifyCredentialResponse[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ResponseCode {
    }

    public static class Builder {
        private byte[] mGatekeeperHAT;
        private long mGatekeeperPasswordHandle;

        public com.android.internal.widget.VerifyCredentialResponse.Builder setGatekeeperHAT(byte[] bArr) {
            this.mGatekeeperHAT = bArr;
            return this;
        }

        public com.android.internal.widget.VerifyCredentialResponse.Builder setGatekeeperPasswordHandle(long j) {
            this.mGatekeeperPasswordHandle = j;
            return this;
        }

        public com.android.internal.widget.VerifyCredentialResponse build() {
            return new com.android.internal.widget.VerifyCredentialResponse(0, 0, this.mGatekeeperHAT, this.mGatekeeperPasswordHandle);
        }
    }

    public static com.android.internal.widget.VerifyCredentialResponse fromTimeout(int i) {
        return new com.android.internal.widget.VerifyCredentialResponse(1, i, null, 0L);
    }

    public static com.android.internal.widget.VerifyCredentialResponse fromError() {
        return new com.android.internal.widget.VerifyCredentialResponse(-1, 0, null, 0L);
    }

    private VerifyCredentialResponse(int i, int i2, byte[] bArr, long j) {
        this.mResponseCode = i;
        this.mTimeout = i2;
        this.mGatekeeperHAT = bArr;
        this.mGatekeeperPasswordHandle = j;
    }

    public com.android.internal.widget.VerifyCredentialResponse stripPayload() {
        return new com.android.internal.widget.VerifyCredentialResponse(this.mResponseCode, this.mTimeout, null, 0L);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResponseCode);
        parcel.writeInt(this.mTimeout);
        parcel.writeByteArray(this.mGatekeeperHAT);
        parcel.writeLong(this.mGatekeeperPasswordHandle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public byte[] getGatekeeperHAT() {
        return this.mGatekeeperHAT;
    }

    public long getGatekeeperPasswordHandle() {
        return this.mGatekeeperPasswordHandle;
    }

    public boolean containsGatekeeperPasswordHandle() {
        return this.mGatekeeperPasswordHandle != 0;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public boolean isMatched() {
        return this.mResponseCode == 0;
    }

    public java.lang.String toString() {
        return "Response: " + this.mResponseCode + ", GK HAT: " + (this.mGatekeeperHAT != null) + ", GK PW: " + (this.mGatekeeperPasswordHandle != 0);
    }

    public static com.android.internal.widget.VerifyCredentialResponse fromGateKeeperResponse(android.service.gatekeeper.GateKeeperResponse gateKeeperResponse) {
        int responseCode = gateKeeperResponse.getResponseCode();
        if (responseCode == 1) {
            return fromTimeout(gateKeeperResponse.getTimeout());
        }
        if (responseCode == 0) {
            byte[] payload = gateKeeperResponse.getPayload();
            if (payload == null) {
                android.util.Slog.e(TAG, "verifyChallenge response had no associated payload");
                return fromError();
            }
            return new com.android.internal.widget.VerifyCredentialResponse.Builder().setGatekeeperHAT(payload).build();
        }
        return fromError();
    }
}
