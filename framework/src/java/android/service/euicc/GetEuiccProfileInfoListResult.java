package android.service.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class GetEuiccProfileInfoListResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.euicc.GetEuiccProfileInfoListResult> CREATOR = new android.os.Parcelable.Creator<android.service.euicc.GetEuiccProfileInfoListResult>() { // from class: android.service.euicc.GetEuiccProfileInfoListResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.GetEuiccProfileInfoListResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.euicc.GetEuiccProfileInfoListResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.GetEuiccProfileInfoListResult[] newArray(int i) {
            return new android.service.euicc.GetEuiccProfileInfoListResult[i];
        }
    };
    private final boolean mIsRemovable;
    private final android.service.euicc.EuiccProfileInfo[] mProfiles;

    @java.lang.Deprecated
    public final int result;

    public int getResult() {
        return this.result;
    }

    public java.util.List<android.service.euicc.EuiccProfileInfo> getProfiles() {
        if (this.mProfiles == null) {
            return null;
        }
        return java.util.Arrays.asList(this.mProfiles);
    }

    public boolean getIsRemovable() {
        return this.mIsRemovable;
    }

    public GetEuiccProfileInfoListResult(int i, android.service.euicc.EuiccProfileInfo[] euiccProfileInfoArr, boolean z) {
        this.result = i;
        this.mIsRemovable = z;
        if (this.result == 0) {
            this.mProfiles = euiccProfileInfoArr;
        } else {
            if (euiccProfileInfoArr != null && euiccProfileInfoArr.length > 0) {
                throw new java.lang.IllegalArgumentException("Error result with non-empty profiles: " + i);
            }
            this.mProfiles = null;
        }
    }

    private GetEuiccProfileInfoListResult(android.os.Parcel parcel) {
        this.result = parcel.readInt();
        this.mProfiles = (android.service.euicc.EuiccProfileInfo[]) parcel.createTypedArray(android.service.euicc.EuiccProfileInfo.CREATOR);
        this.mIsRemovable = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeTypedArray(this.mProfiles, i);
        parcel.writeBoolean(this.mIsRemovable);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "[GetEuiccProfileInfoListResult: result=" + android.service.euicc.EuiccService.resultToString(this.result) + ", isRemovable=" + this.mIsRemovable + ", mProfiles=" + java.util.Arrays.toString(this.mProfiles) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
