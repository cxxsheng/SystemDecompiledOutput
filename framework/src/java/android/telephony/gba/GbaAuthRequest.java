package android.telephony.gba;

/* loaded from: classes3.dex */
public final class GbaAuthRequest implements android.os.Parcelable {
    private int mAppType;
    private android.telephony.IBootstrapAuthenticationCallback mCallback;
    private boolean mForceBootStrapping;
    private android.net.Uri mNafUrl;
    private byte[] mSecurityProtocol;
    private int mSubId;
    private int mToken;
    private static java.util.concurrent.atomic.AtomicInteger sUniqueToken = new java.util.concurrent.atomic.AtomicInteger(0);
    public static final android.os.Parcelable.Creator<android.telephony.gba.GbaAuthRequest> CREATOR = new android.os.Parcelable.Creator<android.telephony.gba.GbaAuthRequest>() { // from class: android.telephony.gba.GbaAuthRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.gba.GbaAuthRequest createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            android.net.Uri uri = (android.net.Uri) parcel.readParcelable(android.telephony.gba.GbaAuthRequest.class.getClassLoader(), android.net.Uri.class);
            byte[] bArr = new byte[parcel.readInt()];
            parcel.readByteArray(bArr);
            return new android.telephony.gba.GbaAuthRequest(readInt, readInt2, readInt3, uri, bArr, parcel.readBoolean(), android.telephony.IBootstrapAuthenticationCallback.Stub.asInterface(parcel.readStrongBinder()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.gba.GbaAuthRequest[] newArray(int i) {
            return new android.telephony.gba.GbaAuthRequest[i];
        }
    };

    public GbaAuthRequest(int i, int i2, android.net.Uri uri, byte[] bArr, boolean z, android.telephony.IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) {
        this(nextUniqueToken(), i, i2, uri, bArr, z, iBootstrapAuthenticationCallback);
    }

    public GbaAuthRequest(android.telephony.gba.GbaAuthRequest gbaAuthRequest) {
        this(gbaAuthRequest.mToken, gbaAuthRequest.mSubId, gbaAuthRequest.mAppType, gbaAuthRequest.mNafUrl, gbaAuthRequest.mSecurityProtocol, gbaAuthRequest.mForceBootStrapping, gbaAuthRequest.mCallback);
    }

    public GbaAuthRequest(int i, int i2, int i3, android.net.Uri uri, byte[] bArr, boolean z, android.telephony.IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) {
        this.mToken = i;
        this.mSubId = i2;
        this.mAppType = i3;
        this.mNafUrl = uri;
        this.mSecurityProtocol = bArr;
        this.mCallback = iBootstrapAuthenticationCallback;
        this.mForceBootStrapping = z;
    }

    public int getToken() {
        return this.mToken;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public int getAppType() {
        return this.mAppType;
    }

    public android.net.Uri getNafUrl() {
        return this.mNafUrl;
    }

    public byte[] getSecurityProtocol() {
        return this.mSecurityProtocol;
    }

    public boolean isForceBootStrapping() {
        return this.mForceBootStrapping;
    }

    public void setCallback(android.telephony.IBootstrapAuthenticationCallback iBootstrapAuthenticationCallback) {
        this.mCallback = iBootstrapAuthenticationCallback;
    }

    public android.telephony.IBootstrapAuthenticationCallback getCallback() {
        return this.mCallback;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mToken);
        parcel.writeInt(this.mSubId);
        parcel.writeInt(this.mAppType);
        parcel.writeParcelable(this.mNafUrl, 0);
        parcel.writeInt(this.mSecurityProtocol.length);
        parcel.writeByteArray(this.mSecurityProtocol);
        parcel.writeBoolean(this.mForceBootStrapping);
        parcel.writeStrongInterface(this.mCallback);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static int nextUniqueToken() {
        return (sUniqueToken.getAndIncrement() << 16) | (((int) java.lang.System.currentTimeMillis()) & 65535);
    }

    public java.lang.String toString() {
        return "Token: " + this.mToken + "SubId:" + this.mSubId + ", AppType:" + this.mAppType + ", NafUrl:" + this.mNafUrl + ", SecurityProtocol:" + com.android.internal.telephony.uicc.IccUtils.bytesToHexString(this.mSecurityProtocol) + ", ForceBootStrapping:" + this.mForceBootStrapping + ", CallBack:" + this.mCallback;
    }
}
