package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class HdmiHotplugEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.hdmi.HdmiHotplugEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.hdmi.HdmiHotplugEvent>() { // from class: android.hardware.hdmi.HdmiHotplugEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.hdmi.HdmiHotplugEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.hdmi.HdmiHotplugEvent(parcel.readInt(), parcel.readByte() == 1);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.hdmi.HdmiHotplugEvent[] newArray(int i) {
            return new android.hardware.hdmi.HdmiHotplugEvent[i];
        }
    };
    private final boolean mConnected;
    private final int mPort;

    public HdmiHotplugEvent(int i, boolean z) {
        this.mPort = i;
        this.mConnected = z;
    }

    public int getPort() {
        return this.mPort;
    }

    public boolean isConnected() {
        return this.mConnected;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPort);
        parcel.writeByte(this.mConnected ? (byte) 1 : (byte) 0);
    }
}
