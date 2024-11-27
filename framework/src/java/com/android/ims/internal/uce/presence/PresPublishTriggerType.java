package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresPublishTriggerType implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresPublishTriggerType> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresPublishTriggerType>() { // from class: com.android.ims.internal.uce.presence.PresPublishTriggerType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresPublishTriggerType createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresPublishTriggerType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresPublishTriggerType[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresPublishTriggerType[i];
        }
    };
    public static final int UCE_PRES_PUBLISH_TRIGGER_ETAG_EXPIRED = 0;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_2G = 6;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_3G = 5;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_EHRPD = 3;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_HSPAPLUS = 4;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_IWLAN = 8;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_LTE_VOPS_DISABLED = 1;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_LTE_VOPS_ENABLED = 2;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_NR5G_VOPS_DISABLED = 10;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_NR5G_VOPS_ENABLED = 11;
    public static final int UCE_PRES_PUBLISH_TRIGGER_MOVE_TO_WLAN = 7;
    public static final int UCE_PRES_PUBLISH_TRIGGER_UNKNOWN = 9;
    private int mPublishTriggerType;

    public int getPublishTrigeerType() {
        return this.mPublishTriggerType;
    }

    public void setPublishTrigeerType(int i) {
        this.mPublishTriggerType = i;
    }

    public PresPublishTriggerType() {
        this.mPublishTriggerType = 9;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPublishTriggerType);
    }

    private PresPublishTriggerType(android.os.Parcel parcel) {
        this.mPublishTriggerType = 9;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mPublishTriggerType = parcel.readInt();
    }
}
