package com.android.ims.internal.uce.options;

/* loaded from: classes4.dex */
public class OptionsCmdId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.options.OptionsCmdId> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.options.OptionsCmdId>() { // from class: com.android.ims.internal.uce.options.OptionsCmdId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.options.OptionsCmdId createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.options.OptionsCmdId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.options.OptionsCmdId[] newArray(int i) {
            return new com.android.ims.internal.uce.options.OptionsCmdId[i];
        }
    };
    public static final int UCE_OPTIONS_CMD_GETCONTACTCAP = 2;
    public static final int UCE_OPTIONS_CMD_GETCONTACTLISTCAP = 3;
    public static final int UCE_OPTIONS_CMD_GETMYCDINFO = 0;
    public static final int UCE_OPTIONS_CMD_GET_VERSION = 5;
    public static final int UCE_OPTIONS_CMD_RESPONSEINCOMINGOPTIONS = 4;
    public static final int UCE_OPTIONS_CMD_SETMYCDINFO = 1;
    public static final int UCE_OPTIONS_CMD_UNKNOWN = 6;
    private int mCmdId;

    public int getCmdId() {
        return this.mCmdId;
    }

    public void setCmdId(int i) {
        this.mCmdId = i;
    }

    public OptionsCmdId() {
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

    private OptionsCmdId(android.os.Parcel parcel) {
        this.mCmdId = 6;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mCmdId = parcel.readInt();
    }
}
