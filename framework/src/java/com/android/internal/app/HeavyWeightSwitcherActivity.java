package com.android.internal.app;

/* loaded from: classes4.dex */
public class HeavyWeightSwitcherActivity extends android.app.Activity {
    public static final java.lang.String KEY_CUR_APP = "cur_app";
    public static final java.lang.String KEY_CUR_TASK = "cur_task";
    public static final java.lang.String KEY_HAS_RESULT = "has_result";
    public static final java.lang.String KEY_INTENT = "intent";
    public static final java.lang.String KEY_NEW_APP = "new_app";
    java.lang.String mCurApp;
    int mCurTask;
    boolean mHasResult;
    java.lang.String mNewApp;
    android.content.IntentSender mStartIntent;
    private android.view.View.OnClickListener mSwitchOldListener = new android.view.View.OnClickListener() { // from class: com.android.internal.app.HeavyWeightSwitcherActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            try {
                android.app.ActivityTaskManager.getService().moveTaskToFront(android.app.ActivityThread.currentActivityThread().getApplicationThread(), com.android.internal.app.HeavyWeightSwitcherActivity.this.getPackageName(), com.android.internal.app.HeavyWeightSwitcherActivity.this.mCurTask, 0, null);
            } catch (android.os.RemoteException e) {
            }
            com.android.internal.app.HeavyWeightSwitcherActivity.this.finish();
        }
    };
    private android.view.View.OnClickListener mSwitchNewListener = new android.view.View.OnClickListener() { // from class: com.android.internal.app.HeavyWeightSwitcherActivity.2
        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            try {
                android.app.ActivityManager.getService().finishHeavyWeightApp();
            } catch (android.os.RemoteException e) {
            }
            try {
                if (com.android.internal.app.HeavyWeightSwitcherActivity.this.mHasResult) {
                    com.android.internal.app.HeavyWeightSwitcherActivity.this.startIntentSenderForResult(com.android.internal.app.HeavyWeightSwitcherActivity.this.mStartIntent, -1, null, 33554432, 33554432, 0);
                } else {
                    com.android.internal.app.HeavyWeightSwitcherActivity.this.startIntentSenderForResult(com.android.internal.app.HeavyWeightSwitcherActivity.this.mStartIntent, -1, (android.content.Intent) null, 0, 0, 0, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
                }
            } catch (android.content.IntentSender.SendIntentException e2) {
                android.util.Log.w("HeavyWeightSwitcherActivity", "Failure starting", e2);
            }
            com.android.internal.app.HeavyWeightSwitcherActivity.this.finish();
        }
    };

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        this.mStartIntent = (android.content.IntentSender) getIntent().getParcelableExtra("intent", android.content.IntentSender.class);
        this.mHasResult = getIntent().getBooleanExtra(KEY_HAS_RESULT, false);
        this.mCurApp = getIntent().getStringExtra(KEY_CUR_APP);
        this.mCurTask = getIntent().getIntExtra(KEY_CUR_TASK, 0);
        this.mNewApp = getIntent().getStringExtra(KEY_NEW_APP);
        setContentView(com.android.internal.R.layout.heavy_weight_switcher);
        setIconAndText(com.android.internal.R.id.old_app_icon, com.android.internal.R.id.old_app_action, 0, this.mCurApp, this.mNewApp, com.android.internal.R.string.old_app_action, 0);
        setIconAndText(com.android.internal.R.id.new_app_icon, com.android.internal.R.id.new_app_action, com.android.internal.R.id.new_app_description, this.mNewApp, this.mCurApp, com.android.internal.R.string.new_app_action, com.android.internal.R.string.new_app_description);
        findViewById(com.android.internal.R.id.switch_old).setOnClickListener(this.mSwitchOldListener);
        findViewById(com.android.internal.R.id.switch_new).setOnClickListener(this.mSwitchNewListener);
    }

    void setText(int i, java.lang.CharSequence charSequence) {
        ((android.widget.TextView) findViewById(i)).lambda$setTextAsync$0(charSequence);
    }

    void setDrawable(int i, android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            ((android.widget.ImageView) findViewById(i)).lambda$setImageURIAsync$2(drawable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.lang.CharSequence] */
    void setIconAndText(int i, int i2, int i3, java.lang.String str, java.lang.String str2, int i4, int i5) {
        android.graphics.drawable.Drawable drawable = null;
        if (str != 0) {
            try {
                android.content.pm.ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(str, 0);
                str = applicationInfo.loadLabel(getPackageManager());
                drawable = applicationInfo.loadIcon(getPackageManager());
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
        setDrawable(i, drawable);
        setText(i2, getString(i4, str));
        if (i3 != 0) {
            if (str2 != 0) {
                try {
                    str2 = getPackageManager().getApplicationInfo(str2, 0).loadLabel(getPackageManager());
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                }
            }
            setText(i3, getString(i5, str2));
        }
    }
}
