package android.accounts;

/* loaded from: classes.dex */
public class AccountManagerResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.accounts.AccountManagerResponse> CREATOR = new android.os.Parcelable.Creator<android.accounts.AccountManagerResponse>() { // from class: android.accounts.AccountManagerResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accounts.AccountManagerResponse createFromParcel(android.os.Parcel parcel) {
            return new android.accounts.AccountManagerResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accounts.AccountManagerResponse[] newArray(int i) {
            return new android.accounts.AccountManagerResponse[i];
        }
    };
    private android.accounts.IAccountManagerResponse mResponse;

    public AccountManagerResponse(android.accounts.IAccountManagerResponse iAccountManagerResponse) {
        this.mResponse = iAccountManagerResponse;
    }

    public AccountManagerResponse(android.os.Parcel parcel) {
        this.mResponse = android.accounts.IAccountManagerResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(android.os.Bundle bundle) {
        try {
            this.mResponse.onResult(bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public void onError(int i, java.lang.String str) {
        try {
            this.mResponse.onError(i, str);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mResponse.asBinder());
    }
}
