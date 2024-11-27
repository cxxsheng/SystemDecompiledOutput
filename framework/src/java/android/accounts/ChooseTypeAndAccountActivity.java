package android.accounts;

/* loaded from: classes.dex */
public class ChooseTypeAndAccountActivity extends android.app.Activity implements android.accounts.AccountManagerCallback<android.os.Bundle> {
    public static final java.lang.String EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING = "authTokenType";
    public static final java.lang.String EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE = "addAccountOptions";
    public static final java.lang.String EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY = "addAccountRequiredFeatures";
    public static final java.lang.String EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST = "allowableAccounts";
    public static final java.lang.String EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY = "allowableAccountTypes";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_ALWAYS_PROMPT_FOR_ACCOUNT = "alwaysPromptForAccount";
    public static final java.lang.String EXTRA_DESCRIPTION_TEXT_OVERRIDE = "descriptionTextOverride";
    public static final java.lang.String EXTRA_SELECTED_ACCOUNT = "selectedAccount";
    private static final java.lang.String KEY_INSTANCE_STATE_ACCOUNTS_LIST = "accountsList";
    private static final java.lang.String KEY_INSTANCE_STATE_EXISTING_ACCOUNTS = "existingAccounts";
    private static final java.lang.String KEY_INSTANCE_STATE_PENDING_REQUEST = "pendingRequest";
    private static final java.lang.String KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME = "selectedAccountName";
    private static final java.lang.String KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT = "selectedAddAccount";
    private static final java.lang.String KEY_INSTANCE_STATE_VISIBILITY_LIST = "visibilityList";
    public static final int REQUEST_ADD_ACCOUNT = 2;
    public static final int REQUEST_CHOOSE_TYPE = 1;
    public static final int REQUEST_NULL = 0;
    private static final int SELECTED_ITEM_NONE = -1;
    private static final java.lang.String TAG = "AccountChooser";
    private java.util.LinkedHashMap<android.accounts.Account, java.lang.Integer> mAccounts;
    private java.lang.String mCallingPackage;
    private int mCallingUid;
    private java.lang.String mDescriptionOverride;
    private boolean mDisallowAddAccounts;
    private boolean mDontShowPicker;
    private android.widget.Button mOkButton;
    private java.util.ArrayList<android.accounts.Account> mPossiblyVisibleAccounts;
    private int mSelectedItemIndex;
    private java.util.Set<android.accounts.Account> mSetOfAllowableAccounts;
    private java.util.Set<java.lang.String> mSetOfRelevantAccountTypes;
    private java.lang.String mSelectedAccountName = null;
    private boolean mSelectedAddNewAccount = false;
    private int mPendingRequest = 0;
    private android.os.Parcelable[] mExistingAccounts = null;

    @Override // android.app.Activity
    public void onCreate(android.os.Bundle bundle) {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "ChooseTypeAndAccountActivity.onCreate(savedInstanceState=" + bundle + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        getWindow().addSystemFlags(524288);
        this.mCallingUid = getLaunchedFromUid();
        this.mCallingPackage = getLaunchedFromPackage();
        if (this.mCallingUid != 0 && this.mCallingPackage != null) {
            this.mDisallowAddAccounts = android.os.UserManager.get(this).getUserRestrictions(new android.os.UserHandle(android.os.UserHandle.getUserId(this.mCallingUid))).getBoolean(android.os.UserManager.DISALLOW_MODIFY_ACCOUNTS, false);
        }
        android.content.Intent intent = getIntent();
        this.mSetOfAllowableAccounts = getAllowableAccountSet(intent);
        this.mSetOfRelevantAccountTypes = getReleventAccountTypes(intent);
        this.mDescriptionOverride = intent.getStringExtra(EXTRA_DESCRIPTION_TEXT_OVERRIDE);
        if (bundle != null) {
            this.mPendingRequest = bundle.getInt(KEY_INSTANCE_STATE_PENDING_REQUEST);
            this.mExistingAccounts = bundle.getParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS);
            this.mSelectedAccountName = bundle.getString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME);
            this.mSelectedAddNewAccount = bundle.getBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
            android.os.Parcelable[] parcelableArray = bundle.getParcelableArray(KEY_INSTANCE_STATE_ACCOUNTS_LIST);
            java.util.ArrayList<java.lang.Integer> integerArrayList = bundle.getIntegerArrayList(KEY_INSTANCE_STATE_VISIBILITY_LIST);
            this.mAccounts = new java.util.LinkedHashMap<>();
            for (int i = 0; i < parcelableArray.length; i++) {
                this.mAccounts.put((android.accounts.Account) parcelableArray[i], integerArrayList.get(i));
            }
        } else {
            this.mPendingRequest = 0;
            this.mExistingAccounts = null;
            android.accounts.Account account = (android.accounts.Account) intent.getParcelableExtra(EXTRA_SELECTED_ACCOUNT, android.accounts.Account.class);
            if (account != null) {
                this.mSelectedAccountName = account.name;
            }
            this.mAccounts = getAcceptableAccountChoices(android.accounts.AccountManager.get(this));
        }
        this.mPossiblyVisibleAccounts = new java.util.ArrayList<>(this.mAccounts.size());
        for (java.util.Map.Entry<android.accounts.Account, java.lang.Integer> entry : this.mAccounts.entrySet()) {
            if (3 != entry.getValue().intValue()) {
                this.mPossiblyVisibleAccounts.add(entry.getKey());
            }
        }
        if (this.mPossiblyVisibleAccounts.isEmpty() && this.mDisallowAddAccounts) {
            requestWindowFeature(1);
            setContentView(com.android.internal.R.layout.app_not_authorized);
            ((android.widget.TextView) findViewById(com.android.internal.R.id.description)).lambda$setTextAsync$0(((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.CANT_ADD_ACCOUNT_MESSAGE, new java.util.function.Supplier() { // from class: android.accounts.ChooseTypeAndAccountActivity$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$onCreate$0;
                    lambda$onCreate$0 = android.accounts.ChooseTypeAndAccountActivity.this.lambda$onCreate$0();
                    return lambda$onCreate$0;
                }
            }));
            this.mDontShowPicker = true;
        }
        if (this.mDontShowPicker) {
            super.onCreate(bundle);
            return;
        }
        if (this.mPendingRequest == 0 && this.mPossiblyVisibleAccounts.isEmpty()) {
            setNonLabelThemeAndCallSuperCreate(bundle);
            if (this.mSetOfRelevantAccountTypes.size() == 1) {
                runAddAccountForAuthenticator(this.mSetOfRelevantAccountTypes.iterator().next());
            } else {
                startChooseAccountTypeActivity();
            }
        }
        java.lang.String[] listOfDisplayableOptions = getListOfDisplayableOptions(this.mPossiblyVisibleAccounts);
        this.mSelectedItemIndex = getItemIndexToSelect(this.mPossiblyVisibleAccounts, this.mSelectedAccountName, this.mSelectedAddNewAccount);
        super.onCreate(bundle);
        setContentView(com.android.internal.R.layout.choose_type_and_account);
        overrideDescriptionIfSupplied(this.mDescriptionOverride);
        populateUIAccountList(listOfDisplayableOptions);
        this.mOkButton = (android.widget.Button) findViewById(16908314);
        this.mOkButton.setEnabled(this.mSelectedItemIndex != -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$onCreate$0() {
        return getString(com.android.internal.R.string.error_message_change_not_allowed);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "ChooseTypeAndAccountActivity.onDestroy()");
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(KEY_INSTANCE_STATE_PENDING_REQUEST, this.mPendingRequest);
        if (this.mPendingRequest == 2) {
            bundle.putParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS, this.mExistingAccounts);
        }
        int i = 0;
        if (this.mSelectedItemIndex != -1) {
            if (this.mSelectedItemIndex == this.mPossiblyVisibleAccounts.size()) {
                bundle.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, true);
            } else {
                bundle.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
                bundle.putString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME, this.mPossiblyVisibleAccounts.get(this.mSelectedItemIndex).name);
            }
        }
        android.os.Parcelable[] parcelableArr = new android.os.Parcelable[this.mAccounts.size()];
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>(this.mAccounts.size());
        for (java.util.Map.Entry<android.accounts.Account, java.lang.Integer> entry : this.mAccounts.entrySet()) {
            parcelableArr[i] = entry.getKey();
            arrayList.add(entry.getValue());
            i++;
        }
        bundle.putParcelableArray(KEY_INSTANCE_STATE_ACCOUNTS_LIST, parcelableArr);
        bundle.putIntegerArrayList(KEY_INSTANCE_STATE_VISIBILITY_LIST, arrayList);
    }

    public void onCancelButtonClicked(android.view.View view) {
        onBackPressed();
    }

    public void onOkButtonClicked(android.view.View view) {
        if (this.mSelectedItemIndex == this.mPossiblyVisibleAccounts.size()) {
            startChooseAccountTypeActivity();
        } else if (this.mSelectedItemIndex != -1) {
            onAccountSelected(this.mPossiblyVisibleAccounts.get(this.mSelectedItemIndex));
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String stringExtra;
        if (android.util.Log.isLoggable(TAG, 2)) {
            if (intent != null && intent.getExtras() != null) {
                intent.getExtras().keySet();
            }
            if (intent != null) {
                intent.getExtras();
            }
            android.util.Log.v(TAG, "ChooseTypeAndAccountActivity.onActivityResult(reqCode=" + i + ", resCode=" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mPendingRequest = 0;
        if (i2 == 0) {
            if (this.mPossiblyVisibleAccounts.isEmpty()) {
                setResult(0);
                finish();
                return;
            }
            return;
        }
        if (i2 == -1) {
            if (i == 1) {
                if (intent == null || (stringExtra = intent.getStringExtra("accountType")) == null) {
                    android.util.Log.d(TAG, "ChooseTypeAndAccountActivity.onActivityResult: unable to find account type, pretending the request was canceled");
                } else {
                    runAddAccountForAuthenticator(stringExtra);
                    return;
                }
            } else if (i == 2) {
                if (intent == null) {
                    str = null;
                    str2 = null;
                } else {
                    str = intent.getStringExtra(android.accounts.AccountManager.KEY_ACCOUNT_NAME);
                    str2 = intent.getStringExtra("accountType");
                }
                if (str == null || str2 == null) {
                    android.accounts.Account[] accountsForPackage = android.accounts.AccountManager.get(this).getAccountsForPackage(this.mCallingPackage, this.mCallingUid);
                    java.util.HashSet hashSet = new java.util.HashSet();
                    for (android.os.Parcelable parcelable : this.mExistingAccounts) {
                        hashSet.add((android.accounts.Account) parcelable);
                    }
                    int length = accountsForPackage.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            break;
                        }
                        android.accounts.Account account = accountsForPackage[i3];
                        if (hashSet.contains(account)) {
                            i3++;
                        } else {
                            str = account.name;
                            str2 = account.type;
                            break;
                        }
                    }
                }
                if (str != null || str2 != null) {
                    setResultAndFinish(str, str2);
                    return;
                }
            }
            android.util.Log.d(TAG, "ChooseTypeAndAccountActivity.onActivityResult: unable to find added account, pretending the request was canceled");
        }
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "ChooseTypeAndAccountActivity.onActivityResult: canceled");
        }
        setResult(0);
        finish();
    }

    protected void runAddAccountForAuthenticator(java.lang.String str) {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "runAddAccountForAuthenticator: " + str);
        }
        android.os.Bundle bundleExtra = getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE);
        android.accounts.AccountManager.get(this).addAccount(str, getIntent().getStringExtra("authTokenType"), getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY), bundleExtra, null, this, null);
    }

    @Override // android.accounts.AccountManagerCallback
    public void run(android.accounts.AccountManagerFuture<android.os.Bundle> accountManagerFuture) {
        try {
            android.content.Intent intent = (android.content.Intent) accountManagerFuture.getResult().getParcelable("intent", android.content.Intent.class);
            if (intent != null) {
                this.mPendingRequest = 2;
                this.mExistingAccounts = android.accounts.AccountManager.get(this).getAccountsForPackage(this.mCallingPackage, this.mCallingUid);
                intent.setFlags(intent.getFlags() & (-268435457));
                startActivityForResult(new android.content.Intent(intent), 2);
                return;
            }
        } catch (android.accounts.AuthenticatorException e) {
        } catch (android.accounts.OperationCanceledException e2) {
            setResult(0);
            finish();
            return;
        } catch (java.io.IOException e3) {
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.accounts.AccountManager.KEY_ERROR_MESSAGE, "error communicating with server");
        setResult(-1, new android.content.Intent().putExtras(bundle));
        finish();
    }

    private void setNonLabelThemeAndCallSuperCreate(android.os.Bundle bundle) {
        setTheme(16974132);
        super.onCreate(bundle);
    }

    private void onAccountSelected(android.accounts.Account account) {
        android.util.Log.d(TAG, "selected account " + account.toSafeString());
        setResultAndFinish(account.name, account.type);
    }

    private void setResultAndFinish(java.lang.String str, java.lang.String str2) {
        android.accounts.Account account = new android.accounts.Account(str, str2);
        java.lang.Integer valueOf = java.lang.Integer.valueOf(android.accounts.AccountManager.get(this).getAccountVisibility(account, this.mCallingPackage));
        if (valueOf != null && valueOf.intValue() == 4) {
            android.accounts.AccountManager.get(this).setAccountVisibility(account, this.mCallingPackage, 2);
        }
        if (valueOf != null && valueOf.intValue() == 3) {
            setResult(0);
            finish();
            return;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.accounts.AccountManager.KEY_ACCOUNT_NAME, str);
        bundle.putString("accountType", str2);
        setResult(-1, new android.content.Intent().putExtras(bundle));
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "ChooseTypeAndAccountActivity.setResultAndFinish: selected account " + account.toSafeString());
        }
        finish();
    }

    private void startChooseAccountTypeActivity() {
        if (android.util.Log.isLoggable(TAG, 2)) {
            android.util.Log.v(TAG, "ChooseAccountTypeActivity.startChooseAccountTypeActivity()");
        }
        android.content.Intent intent = new android.content.Intent(this, (java.lang.Class<?>) android.accounts.ChooseAccountTypeActivity.class);
        intent.setFlags(524288);
        intent.putExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY, getIntent().getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY));
        intent.putExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE, getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE));
        intent.putExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY, getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY));
        intent.putExtra("authTokenType", getIntent().getStringExtra("authTokenType"));
        startActivityForResult(intent, 1);
        this.mPendingRequest = 1;
    }

    private int getItemIndexToSelect(java.util.ArrayList<android.accounts.Account> arrayList, java.lang.String str, boolean z) {
        if (z) {
            return arrayList.size();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).name.equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private java.lang.String[] getListOfDisplayableOptions(java.util.ArrayList<android.accounts.Account> arrayList) {
        java.lang.String[] strArr = new java.lang.String[arrayList.size() + (!this.mDisallowAddAccounts ? 1 : 0)];
        for (int i = 0; i < arrayList.size(); i++) {
            strArr[i] = arrayList.get(i).name;
        }
        if (!this.mDisallowAddAccounts) {
            strArr[arrayList.size()] = getResources().getString(com.android.internal.R.string.add_account_button_label);
        }
        return strArr;
    }

    private java.util.LinkedHashMap<android.accounts.Account, java.lang.Integer> getAcceptableAccountChoices(android.accounts.AccountManager accountManager) {
        java.util.Map<android.accounts.Account, java.lang.Integer> accountsAndVisibilityForPackage = accountManager.getAccountsAndVisibilityForPackage(this.mCallingPackage, null);
        android.accounts.Account[] accounts = accountManager.getAccounts();
        java.util.LinkedHashMap<android.accounts.Account, java.lang.Integer> linkedHashMap = new java.util.LinkedHashMap<>(accountsAndVisibilityForPackage.size());
        for (android.accounts.Account account : accounts) {
            if ((this.mSetOfAllowableAccounts == null || this.mSetOfAllowableAccounts.contains(account)) && ((this.mSetOfRelevantAccountTypes == null || this.mSetOfRelevantAccountTypes.contains(account.type)) && accountsAndVisibilityForPackage.get(account) != null)) {
                linkedHashMap.put(account, accountsAndVisibilityForPackage.get(account));
            }
        }
        return linkedHashMap;
    }

    private java.util.Set<java.lang.String> getReleventAccountTypes(android.content.Intent intent) {
        java.lang.String[] stringArrayExtra = intent.getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY);
        android.accounts.AuthenticatorDescription[] authenticatorTypes = android.accounts.AccountManager.get(this).getAuthenticatorTypes();
        java.util.HashSet hashSet = new java.util.HashSet(authenticatorTypes.length);
        for (android.accounts.AuthenticatorDescription authenticatorDescription : authenticatorTypes) {
            hashSet.add(authenticatorDescription.type);
        }
        if (stringArrayExtra != null) {
            java.util.HashSet newHashSet = com.google.android.collect.Sets.newHashSet(stringArrayExtra);
            newHashSet.retainAll(hashSet);
            return newHashSet;
        }
        return hashSet;
    }

    private java.util.Set<android.accounts.Account> getAllowableAccountSet(android.content.Intent intent) {
        java.util.ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST);
        if (parcelableArrayListExtra == null) {
            return null;
        }
        java.util.HashSet hashSet = new java.util.HashSet(parcelableArrayListExtra.size());
        java.util.Iterator it = parcelableArrayListExtra.iterator();
        while (it.hasNext()) {
            hashSet.add((android.accounts.Account) ((android.os.Parcelable) it.next()));
        }
        return hashSet;
    }

    private void overrideDescriptionIfSupplied(java.lang.String str) {
        android.widget.TextView textView = (android.widget.TextView) findViewById(com.android.internal.R.id.description);
        if (!android.text.TextUtils.isEmpty(str)) {
            textView.lambda$setTextAsync$0(str);
        } else {
            textView.setVisibility(8);
        }
    }

    private final void populateUIAccountList(java.lang.String[] strArr) {
        android.widget.ListView listView = (android.widget.ListView) findViewById(16908298);
        listView.setAdapter((android.widget.ListAdapter) new android.widget.ArrayAdapter(this, 17367055, strArr));
        listView.setChoiceMode(1);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() { // from class: android.accounts.ChooseTypeAndAccountActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
                android.accounts.ChooseTypeAndAccountActivity.this.mSelectedItemIndex = i;
                android.accounts.ChooseTypeAndAccountActivity.this.mOkButton.setEnabled(true);
            }
        });
        if (this.mSelectedItemIndex != -1) {
            listView.setItemChecked(this.mSelectedItemIndex, true);
            if (android.util.Log.isLoggable(TAG, 2)) {
                android.util.Log.v(TAG, "List item " + this.mSelectedItemIndex + " should be selected");
            }
        }
    }
}
