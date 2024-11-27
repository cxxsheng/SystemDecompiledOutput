package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresCmdId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresCmdId> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresCmdId>() { // from class: com.android.ims.internal.uce.presence.PresCmdId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresCmdId createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresCmdId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresCmdId[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresCmdId[i];
        }
    };
    public static final int UCE_PRES_CMD_GETCONTACTCAP = 2;
    public static final int UCE_PRES_CMD_GETCONTACTLISTCAP = 3;
    public static final int UCE_PRES_CMD_GET_VERSION = 0;
    public static final int UCE_PRES_CMD_PUBLISHMYCAP = 1;
    public static final int UCE_PRES_CMD_REENABLE_SERVICE = 5;
    public static final int UCE_PRES_CMD_SETNEWFEATURETAG = 4;
    public static final int UCE_PRES_CMD_UNKNOWN = 6;
    private int mCmdId;

    public int getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(int i) {
        this.mCmdId = i;
    }

    public PresCmdId() {
        this.mCmdId = 6;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCmdId);
    }

    private PresCmdId(android.os.Parcel parcel) {
        this.mCmdId = 6;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mCmdId = parcel.readInt();
    }
}
