package android.hardware.display;

/* loaded from: classes2.dex */
public final class WifiDisplayStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.WifiDisplayStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.WifiDisplayStatus>() { // from class: android.hardware.display.WifiDisplayStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.WifiDisplayStatus createFromParcel(android.os.Parcel parcel) {
            android.hardware.display.WifiDisplay wifiDisplay;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            if (parcel.readInt() == 0) {
                wifiDisplay = null;
            } else {
                wifiDisplay = android.hardware.display.WifiDisplay.CREATOR.createFromParcel(parcel);
            }
            android.hardware.display.WifiDisplay[] newArray = android.hardware.display.WifiDisplay.CREATOR.newArray(parcel.readInt());
            for (int i = 0; i < newArray.length; i++) {
                newArray[i] = android.hardware.display.WifiDisplay.CREATOR.createFromParcel(parcel);
            }
            return new android.hardware.display.WifiDisplayStatus(readInt, readInt2, readInt3, wifiDisplay, newArray, android.hardware.display.WifiDisplaySessionInfo.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.WifiDisplayStatus[] newArray(int i) {
            return new android.hardware.display.WifiDisplayStatus[i];
        }
    };
    public static final int DISPLAY_STATE_CONNECTED = 2;
    public static final int DISPLAY_STATE_CONNECTING = 1;
    public static final int DISPLAY_STATE_NOT_CONNECTED = 0;
    public static final int FEATURE_STATE_DISABLED = 1;
    public static final int FEATURE_STATE_OFF = 2;
    public static final int FEATURE_STATE_ON = 3;
    public static final int FEATURE_STATE_UNAVAILABLE = 0;
    public static final int SCAN_STATE_NOT_SCANNING = 0;
    public static final int SCAN_STATE_SCANNING = 1;
    private final android.hardware.display.WifiDisplay mActiveDisplay;
    private final int mActiveDisplayState;
    private final android.hardware.display.WifiDisplay[] mDisplays;
    private final int mFeatureState;
    private final int mScanState;
    private final android.hardware.display.WifiDisplaySessionInfo mSessionInfo;

    public WifiDisplayStatus() {
        this(0, 0, 0, null, android.hardware.display.WifiDisplay.EMPTY_ARRAY, null);
    }

    public WifiDisplayStatus(int i, int i2, int i3, android.hardware.display.WifiDisplay wifiDisplay, android.hardware.display.WifiDisplay[] wifiDisplayArr, android.hardware.display.WifiDisplaySessionInfo wifiDisplaySessionInfo) {
        if (wifiDisplayArr == null) {
            throw new java.lang.IllegalArgumentException("displays must not be null");
        }
        this.mFeatureState = i;
        this.mScanState = i2;
        this.mActiveDisplayState = i3;
        this.mActiveDisplay = wifiDisplay;
        this.mDisplays = wifiDisplayArr;
        this.mSessionInfo = wifiDisplaySessionInfo == null ? new android.hardware.display.WifiDisplaySessionInfo() : wifiDisplaySessionInfo;
    }

    public int getFeatureState() {
        return this.mFeatureState;
    }

    public int getScanState() {
        return this.mScanState;
    }

    public int getActiveDisplayState() {
        return this.mActiveDisplayState;
    }

    public android.hardware.display.WifiDisplay getActiveDisplay() {
        return this.mActiveDisplay;
    }

    public android.hardware.display.WifiDisplay[] getDisplays() {
        return this.mDisplays;
    }

    public android.hardware.display.WifiDisplaySessionInfo getSessionInfo() {
        return this.mSessionInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mFeatureState);
        parcel.writeInt(this.mScanState);
        parcel.writeInt(this.mActiveDisplayState);
        if (this.mActiveDisplay != null) {
            parcel.writeInt(1);
            this.mActiveDisplay.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mDisplays.length);
        for (android.hardware.display.WifiDisplay wifiDisplay : this.mDisplays) {
            wifiDisplay.writeToParcel(parcel, i);
        }
        this.mSessionInfo.writeToParcel(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "WifiDisplayStatus{featureState=" + this.mFeatureState + ", scanState=" + this.mScanState + ", activeDisplayState=" + this.mActiveDisplayState + ", activeDisplay=" + this.mActiveDisplay + ", displays=" + java.util.Arrays.toString(this.mDisplays) + ", sessionInfo=" + this.mSessionInfo + "}";
    }
}
