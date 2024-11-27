package com.android.internal.app;

/* loaded from: classes4.dex */
public class NfcResolverActivity extends com.android.internal.app.ResolverActivity {
    @Override // com.android.internal.app.ResolverActivity, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        if (!android.nfc.Flags.enableNfcMainline()) {
            super_onCreate(bundle);
            finish();
        } else {
            android.content.Intent intent = getIntent();
            super.onCreate(bundle, (android.content.Intent) intent.getParcelableExtra(android.content.Intent.EXTRA_INTENT, android.content.Intent.class), intent.getExtras().getCharSequence(android.content.Intent.EXTRA_TITLE, getResources().getText(com.android.internal.R.string.chooseActivity)), null, intent.getParcelableArrayListExtra("android.nfc.extra.RESOLVE_INFOS", android.content.pm.ResolveInfo.class), false);
        }
    }
}
