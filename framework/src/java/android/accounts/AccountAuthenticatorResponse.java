package android.accounts;

/* loaded from: classes.dex */
public class AccountAuthenticatorResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.accounts.AccountAuthenticatorResponse> CREATOR = new android.os.Parcelable.Creator<android.accounts.AccountAuthenticatorResponse>() { // from class: android.accounts.AccountAuthenticatorResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accounts.AccountAuthenticatorResponse createFromParcel(android.os.Parcel parcel) {
            return new android.accounts.AccountAuthenticatorResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accounts.AccountAuthenticatorResponse[] newArray(int i) {
            return new android.accounts.AccountAuthenticatorResponse[i];
        }
    };
    private static final java.lang.String TAG = "AccountAuthenticator";
    private android.accounts.IAccountAuthenticatorResponse mAccountAuthenticatorResponse;

    public AccountAuthenticatorResponse(android.accounts.IAccountAuthenticatorResponse iAccountAuthenticatorResponse) {
        this.mAccountAuthenticatorResponse = iAccountAuthenticatorResponse;
    }

    public AccountAuthenticatorResponse(android.os.Parcel parcel) {
        this.mAccountAuthenticatorResponse = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(android.os.Bundle bundle) {
        if (android.util.Log.isLoggable(TAG, 2)) {
            bundle.keySet();
            android.util.Log.v(TAG, "AccountAuthenticatorResponse.onResult: " + android.accounts.AccountManager.sanitizeResult(bundle));
        }
        try {
            this.mAccountAuthenticatorResponse.onResult(bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    public void onRequestContinued() {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "AccountAuthenticatorResponse.onRequestContinued");
        }
        try {
            this.mAccountAuthenticatorResponse.onRequestContinued();
        } catch (android.os.RemoteException e) {
        }
    }

    public void onError(int i, java.lang.String str) {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "AccountAuthenticatorResponse.onError: " + i + ", " + str);
        }
        try {
            this.mAccountAuthenticatorResponse.onError(i, str);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mAccountAuthenticatorResponse.asBinder());
    }
}
