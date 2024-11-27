package android.accounts;

/* loaded from: classes.dex */
public class ChooseAccountActivity extends android.app.Activity {
    private static final java.lang.String TAG = "AccountManager";
    private java.lang.String mCallingPackage;
    private int mCallingUid;
    private android.os.Bundle mResult;
    private android.os.Parcelable[] mAccounts = null;
    private android.accounts.AccountManagerResponse mAccountManagerResponse = null;
    private java.util.HashMap<java.lang.String, android.accounts.AuthenticatorDescription> mTypeToAuthDescription = new java.util.HashMap<>();

    @Override // android.app.Activity
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        this.mAccounts = getIntent().getParcelableArrayExtra(android.accounts.AccountManager.KEY_ACCOUNTS);
        this.mAccountManagerResponse = (android.accounts.AccountManagerResponse) getIntent().getParcelableExtra(android.accounts.AccountManager.KEY_ACCOUNT_MANAGER_RESPONSE, android.accounts.AccountManagerResponse.class);
        if (this.mAccounts == null) {
            setResult(0);
            finish();
            return;
        }
        this.mCallingUid = getLaunchedFromUid();
        this.mCallingPackage = getLaunchedFromPackage();
        if (android.os.UserHandle.isSameApp(this.mCallingUid, 1000) && getIntent().getStringExtra(android.accounts.AccountManager.KEY_ANDROID_PACKAGE_NAME) != null) {
            this.mCallingPackage = getIntent().getStringExtra(android.accounts.AccountManager.KEY_ANDROID_PACKAGE_NAME);
        }
        if (!android.os.UserHandle.isSameApp(this.mCallingUid, 1000) && getIntent().getStringExtra(android.accounts.AccountManager.KEY_ANDROID_PACKAGE_NAME) != null) {
            android.util.Log.w(getClass().getSimpleName(), "Non-system Uid: " + this.mCallingUid + " tried to override packageName \n");
        }
        getAuthDescriptions();
        android.accounts.ChooseAccountActivity.AccountInfo[] accountInfoArr = new android.accounts.ChooseAccountActivity.AccountInfo[this.mAccounts.length];
        for (int i = 0; i < this.mAccounts.length; i++) {
            accountInfoArr[i] = new android.accounts.ChooseAccountActivity.AccountInfo(((android.accounts.Account) this.mAccounts[i]).name, getDrawableForType(((android.accounts.Account) this.mAccounts[i]).type));
        }
        setContentView(com.android.internal.R.layout.choose_account);
        android.widget.ListView listView = (android.widget.ListView) findViewById(16908298);
        listView.setAdapter((android.widget.ListAdapter) new android.accounts.ChooseAccountActivity.AccountArrayAdapter(this, 17367043, accountInfoArr));
        listView.setChoiceMode(1);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: android.accounts.ChooseAccountActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i2, long j) {
                android.accounts.ChooseAccountActivity.this.onListItemClick((android.widget.ListView) adapterView, view, i2, j);
            }
        });
    }

    private void getAuthDescriptions() {
        for (android.accounts.AuthenticatorDescription authenticatorDescription : android.accounts.AccountManager.get(this).getAuthenticatorTypes()) {
            this.mTypeToAuthDescription.put(authenticatorDescription.type, authenticatorDescription);
        }
    }

    private android.graphics.drawable.Drawable getDrawableForType(java.lang.String str) {
        if (this.mTypeToAuthDescription.containsKey(str)) {
            try {
                android.accounts.AuthenticatorDescription authenticatorDescription = this.mTypeToAuthDescription.get(str);
                return createPackageContext(authenticatorDescription.packageName, 0).getDrawable(authenticatorDescription.iconId);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                if (android.util.Log.isLoggable(TAG, 5)) {
                    android.util.Log.w(TAG, "No icon name for account type " + str);
                }
            } catch (android.content.res.Resources.NotFoundException e2) {
                if (android.util.Log.isLoggable(TAG, 5)) {
                    android.util.Log.w(TAG, "No icon resource for account type " + str);
                }
            }
        }
        return null;
    }

    protected void onListItemClick(android.widget.ListView listView, android.view.View view, int i, long j) {
        android.accounts.Account account = (android.accounts.Account) this.mAccounts[i];
        android.accounts.AccountManager accountManager = android.accounts.AccountManager.get(this);
        java.lang.Integer valueOf = java.lang.Integer.valueOf(accountManager.getAccountVisibility(account, this.mCallingPackage));
        if (valueOf != null && valueOf.intValue() == 4) {
            accountManager.setAccountVisibility(account, this.mCallingPackage, 2);
        }
        android.util.Log.d(TAG, "selected account " + account.toSafeString());
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.accounts.AccountManager.KEY_ACCOUNT_NAME, account.name);
        bundle.putString("accountType", account.type);
        this.mResult = bundle;
        finish();
    }

    @Override // android.app.Activity
    public void finish() {
        if (this.mAccountManagerResponse != null) {
            if (this.mResult != null) {
                this.mAccountManagerResponse.onResult(this.mResult);
            } else {
                this.mAccountManagerResponse.onError(4, android.companion.CompanionDeviceManager.REASON_CANCELED);
            }
        }
        super.finish();
    }

    private static class AccountInfo {
        final android.graphics.drawable.Drawable drawable;
        final java.lang.String name;

        AccountInfo(java.lang.String str, android.graphics.drawable.Drawable drawable) {
            this.name = str;
            this.drawable = drawable;
        }
    }

    private static class ViewHolder {
        android.widget.ImageView icon;
        android.widget.TextView text;

        private ViewHolder() {
        }
    }

    private static class AccountArrayAdapter extends android.widget.ArrayAdapter<android.accounts.ChooseAccountActivity.AccountInfo> {
        private android.accounts.ChooseAccountActivity.AccountInfo[] mInfos;
        private android.view.LayoutInflater mLayoutInflater;

        public AccountArrayAdapter(android.content.Context context, int i, android.accounts.ChooseAccountActivity.AccountInfo[] accountInfoArr) {
            super(context, i, accountInfoArr);
            this.mInfos = accountInfoArr;
            this.mLayoutInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            android.accounts.ChooseAccountActivity.ViewHolder viewHolder;
            if (view == null) {
                view = this.mLayoutInflater.inflate(com.android.internal.R.layout.choose_account_row, (android.view.ViewGroup) null);
                viewHolder = new android.accounts.ChooseAccountActivity.ViewHolder();
                viewHolder.text = (android.widget.TextView) view.findViewById(com.android.internal.R.id.account_row_text);
                viewHolder.icon = (android.widget.ImageView) view.findViewById(com.android.internal.R.id.account_row_icon);
                view.setTag(viewHolder);
            } else {
                viewHolder = (android.accounts.ChooseAccountActivity.ViewHolder) view.getTag();
            }
            viewHolder.text.lambda$setTextAsync$0(this.mInfos[i].name);
            viewHolder.icon.lambda$setImageURIAsync$2(this.mInfos[i].drawable);
            return view;
        }
    }
}
