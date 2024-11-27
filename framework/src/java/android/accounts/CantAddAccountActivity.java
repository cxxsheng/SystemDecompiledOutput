package android.accounts;

/* loaded from: classes.dex */
public class CantAddAccountActivity extends android.app.Activity {
    public static final java.lang.String EXTRA_ERROR_CODE = "android.accounts.extra.ERROR_CODE";

    @Override // android.app.Activity
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(com.android.internal.R.layout.app_not_authorized);
        ((android.widget.TextView) findViewById(com.android.internal.R.id.description)).lambda$setTextAsync$0(((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.CANT_ADD_ACCOUNT_MESSAGE, new java.util.function.Supplier() { // from class: android.accounts.CantAddAccountActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$onCreate$0;
                lambda$onCreate$0 = android.accounts.CantAddAccountActivity.this.lambda$onCreate$0();
                return lambda$onCreate$0;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$onCreate$0() {
        return getString(com.android.internal.R.string.error_message_change_not_allowed);
    }

    public void onCancelButtonClicked(android.view.View view) {
        onBackPressed();
    }
}
