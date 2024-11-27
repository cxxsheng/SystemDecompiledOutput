package com.android.internal.app;

/* loaded from: classes4.dex */
public class ConfirmUserCreationActivity extends com.android.internal.app.AlertActivity implements android.content.DialogInterface.OnClickListener {
    private static final java.lang.String TAG = "CreateUser";
    private static final java.lang.String USER_TYPE = "android.os.usertype.full.SECONDARY";
    private java.lang.String mAccountName;
    private android.os.PersistableBundle mAccountOptions;
    private java.lang.String mAccountType;
    private boolean mCanProceed;
    private boolean mIsFirstClick;
    private android.os.UserManager mUserManager;
    private java.lang.String mUserName;

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        android.content.Intent intent = getIntent();
        this.mUserName = intent.getStringExtra(android.os.UserManager.EXTRA_USER_NAME);
        this.mAccountName = intent.getStringExtra(android.os.UserManager.EXTRA_USER_ACCOUNT_NAME);
        this.mAccountType = intent.getStringExtra(android.os.UserManager.EXTRA_USER_ACCOUNT_TYPE);
        this.mAccountOptions = (android.os.PersistableBundle) intent.getParcelableExtra(android.os.UserManager.EXTRA_USER_ACCOUNT_OPTIONS, android.os.PersistableBundle.class);
        this.mUserManager = (android.os.UserManager) getSystemService(android.os.UserManager.class);
        java.lang.String checkUserCreationRequirements = checkUserCreationRequirements();
        if (checkUserCreationRequirements == null) {
            finish();
            return;
        }
        com.android.internal.app.AlertController.AlertParams alertParams = this.mAlertParams;
        alertParams.mMessage = checkUserCreationRequirements;
        alertParams.mPositiveButtonText = getString(17039370);
        alertParams.mPositiveButtonListener = this;
        if (this.mCanProceed) {
            alertParams.mNegativeButtonText = getString(17039360);
            alertParams.mNegativeButtonListener = this;
        }
        this.mIsFirstClick = true;
        setupAlert();
    }

    private java.lang.String checkUserCreationRequirements() {
        java.lang.String callingPackage = getCallingPackage();
        if (callingPackage == null) {
            throw new java.lang.SecurityException("User Creation intent must be launched with startActivityForResult");
        }
        try {
            boolean z = false;
            android.content.pm.ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(callingPackage, 0);
            boolean z2 = this.mUserManager.hasUserRestriction(android.os.UserManager.DISALLOW_ADD_USER) || !this.mUserManager.isAdminUser();
            boolean z3 = !this.mUserManager.canAddMoreUsers("android.os.usertype.full.SECONDARY");
            android.accounts.Account account = new android.accounts.Account(this.mAccountName, this.mAccountType);
            if (this.mAccountName != null && this.mAccountType != null && (android.accounts.AccountManager.get(this).someUserHasAccount(account) | this.mUserManager.someUserHasSeedAccount(this.mAccountName, this.mAccountType))) {
                z = true;
            }
            this.mCanProceed = true;
            java.lang.String charSequence = applicationInfo.loadLabel(getPackageManager()).toString();
            if (z2) {
                setResult(1);
                return null;
            }
            if (!isUserPropertyWithinLimit(this.mUserName, 100) || !isUserPropertyWithinLimit(this.mAccountName, 500) || !isUserPropertyWithinLimit(this.mAccountType, 500) || (this.mAccountOptions != null && !this.mAccountOptions.isBundleContentsWithinLengthLimit(1000))) {
                setResult(1);
                android.util.Log.i(TAG, "User properties must not exceed their character limits");
                return null;
            }
            if (z3) {
                setResult(2);
                return null;
            }
            if (z) {
                return getString(com.android.internal.R.string.user_creation_account_exists, charSequence, this.mAccountName);
            }
            return getString(com.android.internal.R.string.user_creation_adding, charSequence, this.mAccountName);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.SecurityException("Cannot find the calling package");
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        setResult(0);
        if (i == -1 && this.mCanProceed && this.mIsFirstClick) {
            this.mIsFirstClick = false;
            android.util.Log.i(TAG, "Ok, creating user");
            android.content.pm.UserInfo createUser = this.mUserManager.createUser(this.mUserName, "android.os.usertype.full.SECONDARY", 0);
            if (createUser == null) {
                android.util.Log.e(TAG, "Couldn't create user");
                finish();
                return;
            } else {
                this.mUserManager.setSeedAccountData(createUser.id, this.mAccountName, this.mAccountType, this.mAccountOptions);
                setResult(-1);
            }
        }
        finish();
    }

    private boolean isUserPropertyWithinLimit(java.lang.String str, int i) {
        return str == null || str.length() <= i;
    }
}
