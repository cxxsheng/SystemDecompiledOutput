package android.accounts;

/* loaded from: classes.dex */
public class ChooseAccountTypeActivity extends android.app.Activity {
    private static final java.lang.String TAG = "AccountChooser";
    private java.util.ArrayList<android.accounts.ChooseAccountTypeActivity.AuthInfo> mAuthenticatorInfosToDisplay;
    private java.util.HashMap<java.lang.String, android.accounts.ChooseAccountTypeActivity.AuthInfo> mTypeToAuthenticatorInfo = new java.util.HashMap<>();

    @Override // android.app.Activity
    public void onCreate(android.os.Bundle bundle) {
        java.util.HashSet hashSet;
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "ChooseAccountTypeActivity.onCreate(savedInstanceState=" + bundle + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        java.lang.String[] stringArrayExtra = getIntent().getStringArrayExtra(android.accounts.ChooseTypeAndAccountActivity.EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY);
        if (stringArrayExtra == null) {
            hashSet = null;
        } else {
            hashSet = new java.util.HashSet(stringArrayExtra.length);
            for (java.lang.String str : stringArrayExtra) {
                hashSet.add(str);
            }
        }
        buildTypeToAuthDescriptionMap();
        this.mAuthenticatorInfosToDisplay = new java.util.ArrayList<>(this.mTypeToAuthenticatorInfo.size());
        for (java.util.Map.Entry<java.lang.String, android.accounts.ChooseAccountTypeActivity.AuthInfo> entry : this.mTypeToAuthenticatorInfo.entrySet()) {
            java.lang.String key = entry.getKey();
            android.accounts.ChooseAccountTypeActivity.AuthInfo value = entry.getValue();
            if (hashSet == null || hashSet.contains(key)) {
                this.mAuthenticatorInfosToDisplay.add(value);
            }
        }
        if (this.mAuthenticatorInfosToDisplay.isEmpty()) {
            android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putString(android.accounts.AccountManager.KEY_ERROR_MESSAGE, "no allowable account types");
            setResult(-1, new android.content.Intent().putExtras(bundle2));
            finish();
            return;
        }
        if (this.mAuthenticatorInfosToDisplay.size() == 1) {
            setResultAndFinish(this.mAuthenticatorInfosToDisplay.get(0).desc.type);
            return;
        }
        setContentView(com.android.internal.R.layout.choose_account_type);
        android.widget.ListView listView = (android.widget.ListView) findViewById(16908298);
        listView.setAdapter((android.widget.ListAdapter) new android.accounts.ChooseAccountTypeActivity.AccountArrayAdapter(this, 17367043, this.mAuthenticatorInfosToDisplay));
        listView.setChoiceMode(0);
        listView.setTextFilterEnabled(false);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: android.accounts.ChooseAccountTypeActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
                android.accounts.ChooseAccountTypeActivity.this.setResultAndFinish(((android.accounts.ChooseAccountTypeActivity.AuthInfo) android.accounts.ChooseAccountTypeActivity.this.mAuthenticatorInfosToDisplay.get(i)).desc.type);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResultAndFinish(java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("accountType", str);
        setResult(-1, new android.content.Intent().putExtras(bundle));
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "ChooseAccountTypeActivity.setResultAndFinish: selected account type " + str);
        }
        finish();
    }

    private void buildTypeToAuthDescriptionMap() {
        android.graphics.drawable.Drawable drawable;
        for (android.accounts.AuthenticatorDescription authenticatorDescription : android.accounts.AccountManager.get(this).getAuthenticatorTypes()) {
            java.lang.String str = null;
            try {
                android.content.Context createPackageContext = createPackageContext(authenticatorDescription.packageName, 0);
                drawable = createPackageContext.getDrawable(authenticatorDescription.iconId);
                try {
                    java.lang.CharSequence text = createPackageContext.getResources().getText(authenticatorDescription.labelId);
                    if (text != null) {
                        str = text.toString();
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    if (android.util.Log.isLoggable(TAG, 5)) {
                        android.util.Log.w(TAG, "No icon name for account type " + authenticatorDescription.type);
                    }
                    this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, new android.accounts.ChooseAccountTypeActivity.AuthInfo(authenticatorDescription, str, drawable));
                } catch (android.content.res.Resources.NotFoundException e2) {
                    if (android.util.Log.isLoggable(TAG, 5)) {
                        android.util.Log.w(TAG, "No icon resource for account type " + authenticatorDescription.type);
                    }
                    this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, new android.accounts.ChooseAccountTypeActivity.AuthInfo(authenticatorDescription, str, drawable));
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
                drawable = null;
            } catch (android.content.res.Resources.NotFoundException e4) {
                drawable = null;
            }
            this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, new android.accounts.ChooseAccountTypeActivity.AuthInfo(authenticatorDescription, str, drawable));
        }
    }

    private static class AuthInfo {
        final android.accounts.AuthenticatorDescription desc;
        final android.graphics.drawable.Drawable drawable;
        final java.lang.String name;

        AuthInfo(android.accounts.AuthenticatorDescription authenticatorDescription, java.lang.String str, android.graphics.drawable.Drawable drawable) {
            this.desc = authenticatorDescription;
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

    private static class AccountArrayAdapter extends android.widget.ArrayAdapter<android.accounts.ChooseAccountTypeActivity.AuthInfo> {
        private java.util.ArrayList<android.accounts.ChooseAccountTypeActivity.AuthInfo> mInfos;
        private android.view.LayoutInflater mLayoutInflater;

        public AccountArrayAdapter(android.content.Context context, int i, java.util.ArrayList<android.accounts.ChooseAccountTypeActivity.AuthInfo> arrayList) {
            super(context, i, arrayList);
            this.mInfos = arrayList;
            this.mLayoutInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            android.accounts.ChooseAccountTypeActivity.ViewHolder viewHolder;
            if (view == null) {
                view = this.mLayoutInflater.inflate(com.android.internal.R.layout.choose_account_row, (android.view.ViewGroup) null);
                viewHolder = new android.accounts.ChooseAccountTypeActivity.ViewHolder();
                viewHolder.text = (android.widget.TextView) view.findViewById(com.android.internal.R.id.account_row_text);
                viewHolder.icon = (android.widget.ImageView) view.findViewById(com.android.internal.R.id.account_row_icon);
                view.setTag(viewHolder);
            } else {
                viewHolder = (android.accounts.ChooseAccountTypeActivity.ViewHolder) view.getTag();
            }
            viewHolder.text.lambda$setTextAsync$0(this.mInfos.get(i).name);
            viewHolder.icon.lambda$setImageURIAsync$2(this.mInfos.get(i).drawable);
            return view;
        }
    }
}
