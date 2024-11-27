package android.accounts;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class AccountAuthenticatorActivity extends android.app.Activity {
    private android.accounts.AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
    private android.os.Bundle mResultBundle = null;

    public final void setAccountAuthenticatorResult(android.os.Bundle bundle) {
        this.mResultBundle = bundle;
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mAccountAuthenticatorResponse = (android.accounts.AccountAuthenticatorResponse) getIntent().getParcelableExtra(android.accounts.AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, android.accounts.AccountAuthenticatorResponse.class);
        if (this.mAccountAuthenticatorResponse != null) {
            this.mAccountAuthenticatorResponse.onRequestContinued();
        }
    }

    @Override // android.app.Activity
    public void finish() {
        if (this.mAccountAuthenticatorResponse != null) {
            if (this.mResultBundle != null) {
                this.mAccountAuthenticatorResponse.onResult(this.mResultBundle);
            } else {
                this.mAccountAuthenticatorResponse.onError(4, android.companion.CompanionDeviceManager.REASON_CANCELED);
            }
            this.mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }
}
