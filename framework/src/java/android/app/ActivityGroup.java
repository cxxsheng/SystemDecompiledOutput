package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class ActivityGroup extends android.app.Activity {
    static final java.lang.String PARENT_NON_CONFIG_INSTANCE_KEY = "android:parent_non_config_instance";
    private static final java.lang.String STATES_KEY = "android:states";
    protected android.app.LocalActivityManager mLocalActivityManager;

    public ActivityGroup() {
        this(true);
    }

    public ActivityGroup(boolean z) {
        this.mLocalActivityManager = new android.app.LocalActivityManager(this, z);
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mLocalActivityManager.dispatchCreate(bundle != null ? bundle.getBundle(STATES_KEY) : null);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.mLocalActivityManager.dispatchResume();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        android.os.Bundle saveInstanceState = this.mLocalActivityManager.saveInstanceState();
        if (saveInstanceState != null) {
            bundle.putBundle(STATES_KEY, saveInstanceState);
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.mLocalActivityManager.dispatchPause(isFinishing());
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        this.mLocalActivityManager.dispatchStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mLocalActivityManager.dispatchDestroy(isFinishing());
    }

    @Override // android.app.Activity
    public java.util.HashMap<java.lang.String, java.lang.Object> onRetainNonConfigurationChildInstances() {
        return this.mLocalActivityManager.dispatchRetainNonConfigurationInstance();
    }

    public android.app.Activity getCurrentActivity() {
        return this.mLocalActivityManager.getCurrentActivity();
    }

    public final android.app.LocalActivityManager getLocalActivityManager() {
        return this.mLocalActivityManager;
    }

    @Override // android.app.Activity
    void dispatchActivityResult(java.lang.String str, int i, int i2, android.content.Intent intent, android.app.ComponentCaller componentCaller, java.lang.String str2) {
        android.app.Activity activity;
        if (str != null && (activity = this.mLocalActivityManager.getActivity(str)) != null) {
            activity.onActivityResult(i, i2, intent);
        } else {
            super.dispatchActivityResult(str, i, i2, intent, componentCaller, str2);
        }
    }
}
