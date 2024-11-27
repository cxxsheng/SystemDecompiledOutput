package android.accounts;

/* loaded from: classes.dex */
public class GrantCredentialsPermissionActivity extends android.app.Activity implements android.view.View.OnClickListener {
    public static final java.lang.String EXTRAS_ACCOUNT = "account";
    public static final java.lang.String EXTRAS_AUTH_TOKEN_TYPE = "authTokenType";
    public static final java.lang.String EXTRAS_REQUESTING_UID = "uid";
    public static final java.lang.String EXTRAS_RESPONSE = "response";
    private android.accounts.Account mAccount;
    private java.lang.String mAuthTokenType;
    private int mCallingUid;
    protected android.view.LayoutInflater mInflater;
    private android.os.Bundle mResultBundle = null;
    private int mUid;

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        setContentView(com.android.internal.R.layout.grant_credentials_permission);
        setTitle(com.android.internal.R.string.grant_permissions_header_text);
        this.mInflater = (android.view.LayoutInflater) getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        android.os.Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setResult(0);
            finish();
            return;
        }
        this.mAccount = (android.accounts.Account) extras.getParcelable("account", android.accounts.Account.class);
        this.mAuthTokenType = extras.getString("authTokenType");
        this.mUid = extras.getInt("uid");
        android.content.pm.PackageManager packageManager = getPackageManager();
        java.lang.String[] packagesForUid = packageManager.getPackagesForUid(this.mUid);
        if (this.mAccount == null || this.mAuthTokenType == null || packagesForUid == null) {
            setResult(0);
            finish();
            return;
        }
        this.mCallingUid = getLaunchedFromUid();
        if (!android.os.UserHandle.isSameApp(this.mCallingUid, 1000) && this.mCallingUid != this.mUid) {
            setResult(0);
            finish();
            return;
        }
        try {
            java.lang.String accountLabel = getAccountLabel(this.mAccount);
            final android.widget.TextView textView = (android.widget.TextView) findViewById(com.android.internal.R.id.authtoken_type);
            textView.setVisibility(8);
            android.accounts.AccountManagerCallback<java.lang.String> accountManagerCallback = new android.accounts.AccountManagerCallback<java.lang.String>() { // from class: android.accounts.GrantCredentialsPermissionActivity.1
                @Override // android.accounts.AccountManagerCallback
                public void run(android.accounts.AccountManagerFuture<java.lang.String> accountManagerFuture) {
                    try {
                        final java.lang.String result = accountManagerFuture.getResult();
                        if (!android.text.TextUtils.isEmpty(result)) {
                            android.accounts.GrantCredentialsPermissionActivity.this.runOnUiThread(new java.lang.Runnable() { // from class: android.accounts.GrantCredentialsPermissionActivity.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    if (!android.accounts.GrantCredentialsPermissionActivity.this.isFinishing()) {
                                        textView.lambda$setTextAsync$0(result);
                                        textView.setVisibility(0);
                                    }
                                }
                            });
                        }
                    } catch (android.accounts.AuthenticatorException e) {
                    } catch (android.accounts.OperationCanceledException e2) {
                    } catch (java.io.IOException e3) {
                    }
                }
            };
            if (!android.accounts.AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE.equals(this.mAuthTokenType)) {
                android.accounts.AccountManager.get(this).getAuthTokenLabel(this.mAccount.type, this.mAuthTokenType, accountManagerCallback, null);
            }
            findViewById(com.android.internal.R.id.allow_button).setOnClickListener(this);
            findViewById(com.android.internal.R.id.deny_button).setOnClickListener(this);
            android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) findViewById(com.android.internal.R.id.packages_list);
            for (java.lang.String str : packagesForUid) {
                try {
                    str = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                }
                linearLayout.addView(newPackageView(str));
            }
            ((android.widget.TextView) findViewById(com.android.internal.R.id.account_name)).lambda$setTextAsync$0(this.mAccount.name);
            ((android.widget.TextView) findViewById(com.android.internal.R.id.account_type)).lambda$setTextAsync$0(accountLabel);
        } catch (java.lang.IllegalArgumentException e2) {
            setResult(0);
            finish();
        }
    }

    private java.lang.String getAccountLabel(android.accounts.Account account) {
        for (android.accounts.AuthenticatorDescription authenticatorDescription : android.accounts.AccountManager.get(this).getAuthenticatorTypes()) {
            if (authenticatorDescription.type.equals(account.type)) {
                try {
                    return createPackageContext(authenticatorDescription.packageName, 0).getString(authenticatorDescription.labelId);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    return account.type;
                } catch (android.content.res.Resources.NotFoundException e2) {
                    return account.type;
                }
            }
        }
        return account.type;
    }

    private android.view.View newPackageView(java.lang.String str) {
        android.view.View inflate = this.mInflater.inflate(com.android.internal.R.layout.permissions_package_list_item, (android.view.ViewGroup) null);
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.package_label)).lambda$setTextAsync$0(str);
        return inflate;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case com.android.internal.R.id.allow_button /* 16908774 */:
                android.accounts.AccountManager.get(this).updateAppPermission(this.mAccount, this.mAuthTokenType, this.mUid, true);
                android.content.Intent intent = new android.content.Intent();
                intent.putExtra("retry", true);
                setResult(-1, intent);
                setAccountAuthenticatorResult(intent.getExtras());
                break;
            case com.android.internal.R.id.deny_button /* 16908974 */:
                android.accounts.AccountManager.get(this).updateAppPermission(this.mAccount, this.mAuthTokenType, this.mUid, false);
                setResult(0);
                break;
        }
        finish();
    }

    public final void setAccountAuthenticatorResult(android.os.Bundle bundle) {
        this.mResultBundle = bundle;
    }

    @Override // android.app.Activity
    public void finish() {
        android.accounts.AccountAuthenticatorResponse accountAuthenticatorResponse = (android.accounts.AccountAuthenticatorResponse) getIntent().getParcelableExtra("response", android.accounts.AccountAuthenticatorResponse.class);
        if (accountAuthenticatorResponse != null) {
            if (this.mResultBundle != null) {
                accountAuthenticatorResponse.onResult(this.mResultBundle);
            } else {
                accountAuthenticatorResponse.onError(4, android.companion.CompanionDeviceManager.REASON_CANCELED);
            }
        }
        super.finish();
    }
}
