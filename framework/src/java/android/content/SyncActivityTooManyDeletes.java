package android.content;

/* loaded from: classes.dex */
public class SyncActivityTooManyDeletes extends android.app.Activity implements android.widget.AdapterView.OnItemClickListener {
    private android.accounts.Account mAccount;
    private java.lang.String mAuthority;
    private long mNumDeletes;
    private java.lang.String mProvider;

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.os.Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        this.mNumDeletes = extras.getLong("numDeletes");
        this.mAccount = (android.accounts.Account) extras.getParcelable("account", android.accounts.Account.class);
        this.mAuthority = extras.getString(android.provider.ContactsContract.Directory.DIRECTORY_AUTHORITY);
        this.mProvider = extras.getString("provider");
        android.widget.ArrayAdapter arrayAdapter = new android.widget.ArrayAdapter(this, 17367043, 16908308, new java.lang.CharSequence[]{getResources().getText(com.android.internal.R.string.sync_really_delete), getResources().getText(com.android.internal.R.string.sync_undo_deletes), getResources().getText(com.android.internal.R.string.sync_do_nothing)});
        android.widget.ListView listView = new android.widget.ListView(this);
        listView.setAdapter((android.widget.ListAdapter) arrayAdapter);
        listView.setItemsCanFocus(true);
        listView.setOnItemClickListener(this);
        android.widget.TextView textView = new android.widget.TextView(this);
        textView.lambda$setTextAsync$0(java.lang.String.format(getResources().getText(com.android.internal.R.string.sync_too_many_deletes_desc).toString(), java.lang.Long.valueOf(this.mNumDeletes), this.mProvider, this.mAccount.name));
        android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(this);
        linearLayout.setOrientation(1);
        android.widget.LinearLayout.LayoutParams layoutParams = new android.widget.LinearLayout.LayoutParams(-1, -2, 0.0f);
        linearLayout.addView(textView, layoutParams);
        linearLayout.addView(listView, layoutParams);
        setContentView(linearLayout);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
        if (i == 0) {
            startSyncReallyDelete();
        } else if (i == 1) {
            startSyncUndoDeletes();
        }
        finish();
    }

    private void startSyncReallyDelete() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_OVERRIDE_TOO_MANY_DELETIONS, true);
        bundle.putBoolean("force", true);
        bundle.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        android.content.ContentResolver.requestSync(this.mAccount, this.mAuthority, bundle);
    }

    private void startSyncUndoDeletes() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_DISCARD_LOCAL_DELETIONS, true);
        bundle.putBoolean("force", true);
        bundle.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        android.content.ContentResolver.requestSync(this.mAccount, this.mAuthority, bundle);
    }
}
