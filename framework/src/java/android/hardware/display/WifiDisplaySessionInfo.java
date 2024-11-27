package android.hardware.display;

/* loaded from: classes2.dex */
public final class WifiDisplaySessionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.WifiDisplaySessionInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.WifiDisplaySessionInfo>() { // from class: android.hardware.display.WifiDisplaySessionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.WifiDisplaySessionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.WifiDisplaySessionInfo(parcel.readInt() != 0, parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.WifiDisplaySessionInfo[] newArray(int i) {
            return new android.hardware.display.WifiDisplaySessionInfo[i];
        }
    };
    private final boolean mClient;
    private final java.lang.String mGroupId;
    private final java.lang.String mIP;
    private final java.lang.String mPassphrase;
    private final int mSessionId;

    public WifiDisplaySessionInfo() {
        this(true, 0, "", "", "");
    }

    public WifiDisplaySessionInfo(boolean z, int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mClient = z;
        this.mSessionId = i;
        this.mGroupId = str;
        this.mPassphrase = str2;
        this.mIP = str3;
    }

    public boolean isClient() {
        return this.mClient;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public java.lang.String getGroupId() {
        return this.mGroupId;
    }

    public java.lang.String getPassphrase() {
        return this.mPassphrase;
    }

    public java.lang.String getIP() {
        return this.mIP;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mClient ? 1 : 0);
        parcel.writeInt(this.mSessionId);
        parcel.writeString(this.mGroupId);
        parcel.writeString(this.mPassphrase);
        parcel.writeString(this.mIP);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "WifiDisplaySessionInfo:\n    Client/Owner: " + (this.mClient ? "Client" : "Owner") + "\n    GroupId: " + this.mGroupId + "\n    Passphrase: " + this.mPassphrase + "\n    SessionId: " + this.mSessionId + "\n    IP Address: " + this.mIP;
    }
}
