package android.telephony;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class NeighboringCellInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.NeighboringCellInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.NeighboringCellInfo>() { // from class: android.telephony.NeighboringCellInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.NeighboringCellInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.NeighboringCellInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.NeighboringCellInfo[] newArray(int i) {
            return new android.telephony.NeighboringCellInfo[i];
        }
    };
    public static final int UNKNOWN_CID = -1;
    public static final int UNKNOWN_RSSI = 99;
    private int mCid;
    private int mLac;
    private int mNetworkType;
    private int mPsc;
    private int mRssi;

    @java.lang.Deprecated
    public NeighboringCellInfo() {
        this.mRssi = 99;
        this.mLac = -1;
        this.mCid = -1;
        this.mPsc = -1;
        this.mNetworkType = 0;
    }

    @java.lang.Deprecated
    public NeighboringCellInfo(int i, int i2) {
        this.mRssi = i;
        this.mCid = i2;
    }

    public NeighboringCellInfo(android.telephony.CellInfoGsm cellInfoGsm) {
        this.mNetworkType = 1;
        this.mRssi = cellInfoGsm.getCellSignalStrength().getAsuLevel();
        if (this.mRssi == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        this.mLac = cellInfoGsm.getCellIdentity().getLac();
        if (this.mLac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        this.mCid = cellInfoGsm.getCellIdentity().getCid();
        if (this.mCid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = -1;
    }

    public NeighboringCellInfo(android.telephony.CellInfoWcdma cellInfoWcdma) {
        this.mNetworkType = 3;
        this.mRssi = cellInfoWcdma.getCellSignalStrength().getAsuLevel();
        if (this.mRssi == Integer.MAX_VALUE) {
            this.mRssi = 99;
        }
        this.mLac = cellInfoWcdma.getCellIdentity().getLac();
        if (this.mLac == Integer.MAX_VALUE) {
            this.mLac = -1;
        }
        this.mCid = cellInfoWcdma.getCellIdentity().getCid();
        if (this.mCid == Integer.MAX_VALUE) {
            this.mCid = -1;
        }
        this.mPsc = cellInfoWcdma.getCellIdentity().getPsc();
        if (this.mPsc == Integer.MAX_VALUE) {
            this.mPsc = -1;
        }
    }

    public NeighboringCellInfo(int i, java.lang.String str, int i2) {
        this.mRssi = i;
        this.mNetworkType = 0;
        this.mPsc = -1;
        this.mLac = -1;
        this.mCid = -1;
        int length = str.length();
        if (length > 8) {
            return;
        }
        if (length < 8) {
            for (int i3 = 0; i3 < 8 - length; i3++) {
                str = android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS + str;
            }
        }
        try {
            switch (i2) {
                case 1:
                case 2:
                    this.mNetworkType = i2;
                    if (!str.equalsIgnoreCase("FFFFFFFF")) {
                        this.mCid = java.lang.Integer.parseInt(str.substring(4), 16);
                        this.mLac = java.lang.Integer.parseInt(str.substring(0, 4), 16);
                        break;
                    }
                    break;
                case 3:
                case 8:
                case 9:
                case 10:
                    this.mNetworkType = i2;
                    this.mPsc = java.lang.Integer.parseInt(str, 16);
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                default:
                    return;
            }
        } catch (java.lang.NumberFormatException e) {
            this.mPsc = -1;
            this.mLac = -1;
            this.mCid = -1;
            this.mNetworkType = 0;
        }
    }

    public NeighboringCellInfo(android.os.Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mLac = parcel.readInt();
        this.mCid = parcel.readInt();
        this.mPsc = parcel.readInt();
        this.mNetworkType = parcel.readInt();
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getLac() {
        return this.mLac;
    }

    public int getCid() {
        return this.mCid;
    }

    public int getPsc() {
        return this.mPsc;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    @java.lang.Deprecated
    public void setCid(int i) {
        this.mCid = i;
    }

    @java.lang.Deprecated
    public void setRssi(int i) {
        this.mRssi = i;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        int i = this.mPsc;
        java.lang.Object obj = com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        if (i != -1) {
            java.lang.StringBuilder append = sb.append(java.lang.Integer.toHexString(this.mPsc)).append("@");
            if (this.mRssi != 99) {
                obj = java.lang.Integer.valueOf(this.mRssi);
            }
            append.append(obj);
        } else if (this.mLac != -1 && this.mCid != -1) {
            java.lang.StringBuilder append2 = sb.append(java.lang.Integer.toHexString(this.mLac)).append(java.lang.Integer.toHexString(this.mCid)).append("@");
            if (this.mRssi != 99) {
                obj = java.lang.Integer.valueOf(this.mRssi);
            }
            append2.append(obj);
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mLac);
        parcel.writeInt(this.mCid);
        parcel.writeInt(this.mPsc);
        parcel.writeInt(this.mNetworkType);
    }
}
