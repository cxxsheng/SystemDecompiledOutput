package com.android.ims.internal.uce.presence;

/* loaded from: classes4.dex */
public class PresSubscriptionState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresSubscriptionState> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.presence.PresSubscriptionState>() { // from class: com.android.ims.internal.uce.presence.PresSubscriptionState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresSubscriptionState createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.presence.PresSubscriptionState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.presence.PresSubscriptionState[] newArray(int i) {
            return new com.android.ims.internal.uce.presence.PresSubscriptionState[i];
        }
    };
    public static final int UCE_PRES_SUBSCRIPTION_STATE_ACTIVE = 0;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_PENDING = 1;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_TERMINATED = 2;
    public static final int UCE_PRES_SUBSCRIPTION_STATE_UNKNOWN = 3;
    private int mPresSubscriptionState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPresSubscriptionState);
    }

    private PresSubscriptionState(android.os.Parcel parcel) {
        this.mPresSubscriptionState = 3;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mPresSubscriptionState = parcel.readInt();
    }

    public PresSubscriptionState() {
        this.mPresSubscriptionState = 3;
    }

    public int getPresSubscriptionStateValue() {
        return this.mPresSubscriptionState;
    }

    public void setPresSubscriptionState(int i) {
        this.mPresSubscriptionState = i;
    }
}
