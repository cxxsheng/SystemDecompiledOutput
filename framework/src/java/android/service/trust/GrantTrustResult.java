package android.service.trust;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class GrantTrustResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.trust.GrantTrustResult> CREATOR = new android.os.Parcelable.Creator<android.service.trust.GrantTrustResult>() { // from class: android.service.trust.GrantTrustResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.trust.GrantTrustResult[] newArray(int i) {
            return new android.service.trust.GrantTrustResult[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.trust.GrantTrustResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.trust.GrantTrustResult(parcel);
        }
    };
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_UNLOCKED_BY_GRANT = 1;
    private int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public static java.lang.String statusToString(int i) {
        switch (i) {
            case 0:
                return "STATUS_UNKNOWN";
            case 1:
                return "STATUS_UNLOCKED_BY_GRANT";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    public GrantTrustResult(int i) {
        this.mStatus = i;
        if (this.mStatus != 0 && this.mStatus != 1) {
            throw new java.lang.IllegalArgumentException("status was " + this.mStatus + " but must be one of: STATUS_UNKNOWN(0), STATUS_UNLOCKED_BY_GRANT(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public int getStatus() {
        return this.mStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    GrantTrustResult(android.os.Parcel parcel) {
        this.mStatus = parcel.readInt();
        if (this.mStatus != 0 && this.mStatus != 1) {
            throw new java.lang.IllegalArgumentException("status was " + this.mStatus + " but must be one of: STATUS_UNKNOWN(0), STATUS_UNLOCKED_BY_GRANT(1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
