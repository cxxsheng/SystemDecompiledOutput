package android.service.dreams;

/* loaded from: classes3.dex */
public class DreamActivity extends android.app.Activity {
    static final java.lang.String EXTRA_CALLBACK = "binder";
    static final java.lang.String EXTRA_DREAM_TITLE = "title";
    private android.service.dreams.DreamService.DreamActivityCallbacks mCallback;

    @Override // android.app.Activity
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        java.lang.String stringExtra = getIntent().getStringExtra("title");
        if (!android.text.TextUtils.isEmpty(stringExtra)) {
            setTitle(stringExtra);
        }
        android.os.IBinder binder = getIntent().getExtras().getBinder("binder");
        if (binder instanceof android.service.dreams.DreamService.DreamActivityCallbacks) {
            this.mCallback = (android.service.dreams.DreamService.DreamActivityCallbacks) binder;
            this.mCallback.onActivityCreated(this);
        } else {
            this.mCallback = null;
            finishAndRemoveTask();
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        if (this.mCallback != null) {
            this.mCallback.onActivityDestroyed();
        }
        super.onDestroy();
    }
}
