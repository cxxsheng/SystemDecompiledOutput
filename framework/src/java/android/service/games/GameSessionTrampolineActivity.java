package android.service.games;

/* loaded from: classes3.dex */
public final class GameSessionTrampolineActivity extends android.app.Activity {
    static final java.lang.String FUTURE_KEY = "GameSessionTrampolineActivity.future";
    private static final java.lang.String HAS_LAUNCHED_INTENT_KEY = "GameSessionTrampolineActivity.hasLaunchedIntent";
    static final java.lang.String INTENT_KEY = "GameSessionTrampolineActivity.intent";
    static final java.lang.String OPTIONS_KEY = "GameSessionTrampolineActivity.options";
    private static final int REQUEST_CODE = 1;
    private static final java.lang.String TAG = "GameSessionTrampoline";
    private boolean mHasLaunchedIntent = false;

    public static android.content.Intent createIntent(android.content.Intent intent, android.os.Bundle bundle, com.android.internal.infra.AndroidFuture<android.service.games.GameSessionActivityResult> androidFuture) {
        android.content.Intent intent2 = new android.content.Intent();
        intent2.setComponent(new android.content.ComponentName("android", "android.service.games.GameSessionTrampolineActivity"));
        intent2.putExtra(INTENT_KEY, intent);
        intent2.putExtra(OPTIONS_KEY, bundle);
        intent2.putExtra(FUTURE_KEY, androidFuture);
        return intent2;
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mHasLaunchedIntent = bundle.getBoolean(HAS_LAUNCHED_INTENT_KEY);
        }
        if (this.mHasLaunchedIntent) {
            return;
        }
        this.mHasLaunchedIntent = true;
        try {
            startActivityAsCaller((android.content.Intent) getIntent().getParcelableExtra(INTENT_KEY, android.content.Intent.class), getIntent().getBundleExtra(OPTIONS_KEY), false, getUserId(), 1);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unable to launch activity from game session");
            ((com.android.internal.infra.AndroidFuture) getIntent().getParcelableExtra(FUTURE_KEY, com.android.internal.infra.AndroidFuture.class)).completeExceptionally(e);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(HAS_LAUNCHED_INTENT_KEY, this.mHasLaunchedIntent);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
        if (i != 1) {
            throw new java.lang.IllegalStateException("Unexpected request code: " + i);
        }
        ((com.android.internal.infra.AndroidFuture) getIntent().getParcelableExtra(FUTURE_KEY, com.android.internal.infra.AndroidFuture.class)).complete(new android.service.games.GameSessionActivityResult(i2, intent));
        finish();
        overridePendingTransition(0, 0);
    }
}
