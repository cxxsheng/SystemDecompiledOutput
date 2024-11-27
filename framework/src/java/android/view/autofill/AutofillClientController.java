package android.view.autofill;

/* loaded from: classes4.dex */
public final class AutofillClientController implements android.view.autofill.AutofillManager.AutofillClient, android.util.Dumpable {
    public static final java.lang.String AUTOFILL_RESET_NEEDED = "@android:autofillResetNeeded";
    public static final java.lang.String AUTO_FILL_AUTH_WHO_PREFIX = "@android:autoFillAuth:";
    public static final java.lang.String DUMPABLE_NAME = "AutofillManager";
    public static final java.lang.String LAST_AUTOFILL_ID = "android:lastAutofillId";
    private static final java.lang.String TAG = "AutofillClientController";
    private final android.app.Activity mActivity;
    private boolean mAutoFillIgnoreFirstResumePause;
    private boolean mAutoFillResetNeeded;
    private android.view.autofill.AutofillManager mAutofillManager;
    private android.view.autofill.AutofillPopupWindow mAutofillPopupWindow;
    public int mLastAutofillId = android.view.View.LAST_APP_AUTOFILL_ID;
    private static final java.lang.String LOG_TAG = "autofill_client";
    public static final boolean DEBUG = android.util.Log.isLoggable(LOG_TAG, 3);

    public AutofillClientController(android.app.Activity activity) {
        this.mActivity = activity;
    }

    private android.view.autofill.AutofillManager getAutofillManager() {
        if (this.mAutofillManager == null) {
            this.mAutofillManager = (android.view.autofill.AutofillManager) this.mActivity.getSystemService(android.view.autofill.AutofillManager.class);
        }
        return this.mAutofillManager;
    }

    public void onActivityAttached(android.app.Application application) {
        this.mActivity.setAutofillOptions(application.getAutofillOptions());
    }

    public void onActivityCreated(android.os.Bundle bundle) {
        this.mAutoFillResetNeeded = bundle.getBoolean(AUTOFILL_RESET_NEEDED, false);
        this.mLastAutofillId = bundle.getInt(LAST_AUTOFILL_ID, android.view.View.LAST_APP_AUTOFILL_ID);
        if (this.mAutoFillResetNeeded) {
            getAutofillManager().onCreate(bundle);
        }
    }

    public void onActivityStarted() {
        if (this.mAutoFillResetNeeded) {
            getAutofillManager().onVisibleForAutofill();
        }
    }

    public void onActivityResumed() {
        android.view.View currentFocus;
        enableAutofillCompatibilityIfNeeded();
        if (this.mAutoFillResetNeeded && !this.mAutoFillIgnoreFirstResumePause && (currentFocus = this.mActivity.getCurrentFocus()) != null && currentFocus.canNotifyAutofillEnterExitEvent()) {
            getAutofillManager().notifyViewEntered(currentFocus);
        }
    }

    public void onActivityPerformResume(boolean z) {
        if (this.mAutoFillResetNeeded) {
            this.mAutoFillIgnoreFirstResumePause = z;
            if (this.mAutoFillIgnoreFirstResumePause && DEBUG) {
                android.util.Slog.v(TAG, "autofill will ignore first pause when relaunching " + this);
            }
        }
    }

    public void onActivityPaused() {
        if (this.mAutoFillResetNeeded) {
            if (!this.mAutoFillIgnoreFirstResumePause) {
                if (DEBUG) {
                    android.util.Log.v(TAG, "autofill notifyViewExited " + this);
                }
                android.view.View currentFocus = this.mActivity.getCurrentFocus();
                if (currentFocus != null && currentFocus.canNotifyAutofillEnterExitEvent()) {
                    getAutofillManager().notifyViewExited(currentFocus);
                    return;
                }
                return;
            }
            if (DEBUG) {
                android.util.Log.v(TAG, "autofill got first pause " + this);
            }
            this.mAutoFillIgnoreFirstResumePause = false;
        }
    }

    public void onActivityStopped(android.content.Intent intent, boolean z) {
        if (this.mAutoFillResetNeeded) {
            getAutofillManager().onInvisibleForAutofill(!z);
        } else if (intent != null && intent.hasExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN) && intent.hasExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY)) {
            restoreAutofillSaveUi(intent);
        }
    }

    public void onActivityDestroyed() {
        if (this.mActivity.isFinishing() && this.mAutoFillResetNeeded) {
            getAutofillManager().onActivityFinishing();
        }
    }

    public void onSaveInstanceState(android.os.Bundle bundle) {
        bundle.putInt(LAST_AUTOFILL_ID, this.mLastAutofillId);
        if (this.mAutoFillResetNeeded) {
            bundle.putBoolean(AUTOFILL_RESET_NEEDED, true);
            getAutofillManager().onSaveInstanceState(bundle);
        }
    }

    public void onActivityFinish(android.content.Intent intent) {
        if (intent != null && intent.hasExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN)) {
            restoreAutofillSaveUi(intent);
        }
    }

    public void onActivityBackPressed(android.content.Intent intent) {
        if (intent != null && intent.hasExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN)) {
            restoreAutofillSaveUi(intent);
        }
    }

    public void onDispatchActivityResult(int i, int i2, android.content.Intent intent) {
        if (i2 != -1) {
            intent = null;
        }
        getAutofillManager().onAuthenticationResult(i, intent, this.mActivity.getCurrentFocus());
    }

    public void onStartActivity(android.content.Intent intent, android.content.Intent intent2) {
        if (intent2 != null && intent2.hasExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN) && intent2.hasExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY) && android.text.TextUtils.equals(this.mActivity.getPackageName(), intent.resolveActivity(this.mActivity.getPackageManager()).getPackageName())) {
            android.os.IBinder iBinderExtra = intent2.getIBinderExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN);
            intent2.removeExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN);
            intent2.removeExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY);
            intent.putExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN, iBinderExtra);
            intent.putExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY, true);
        }
    }

    public void restoreAutofillSaveUi(android.content.Intent intent) {
        android.os.IBinder iBinderExtra = intent.getIBinderExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN);
        intent.removeExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_SESSION_TOKEN);
        intent.removeExtra(android.view.autofill.AutofillManager.EXTRA_RESTORE_CROSS_ACTIVITY);
        getAutofillManager().onPendingSaveUi(2, iBinderExtra);
    }

    public void enableAutofillCompatibilityIfNeeded() {
        android.view.autofill.AutofillManager autofillManager;
        if (this.mActivity.isAutofillCompatibilityEnabled() && (autofillManager = (android.view.autofill.AutofillManager) this.mActivity.getSystemService(android.view.autofill.AutofillManager.class)) != null) {
            autofillManager.enableCompatibilityMode();
        }
    }

    @Override // android.util.Dumpable
    public java.lang.String getDumpableName() {
        return DUMPABLE_NAME;
    }

    @Override // android.util.Dumpable
    public void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        android.view.autofill.AutofillManager autofillManager = getAutofillManager();
        if (autofillManager != null) {
            autofillManager.dump("", printWriter);
            printWriter.print("");
            printWriter.print("Autofill Compat Mode: ");
            printWriter.println(this.mActivity.isAutofillCompatibilityEnabled());
            return;
        }
        printWriter.print("");
        printWriter.println("No AutofillManager");
    }

    public int getNextAutofillId() {
        if (this.mLastAutofillId == 2147483646) {
            this.mLastAutofillId = android.view.View.LAST_APP_AUTOFILL_ID;
        }
        this.mLastAutofillId++;
        return this.mLastAutofillId;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public android.view.autofill.AutofillId autofillClientGetNextAutofillId() {
        return new android.view.autofill.AutofillId(getNextAutofillId());
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientIsCompatibilityModeEnabled() {
        return this.mActivity.isAutofillCompatibilityEnabled();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientIsVisibleForAutofill() {
        return this.mActivity.isVisibleForAutofill();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public android.content.ComponentName autofillClientGetComponentName() {
        return this.mActivity.getComponentName();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public android.os.IBinder autofillClientGetActivityToken() {
        return this.mActivity.getActivityToken();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean[] autofillClientGetViewVisibility(android.view.autofill.AutofillId[] autofillIdArr) {
        int length = autofillIdArr.length;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            android.view.autofill.AutofillId autofillId = autofillIdArr[i];
            if (autofillId == null) {
                zArr[i] = false;
            } else {
                android.view.View autofillClientFindViewByAutofillIdTraversal = autofillClientFindViewByAutofillIdTraversal(autofillId);
                if (autofillClientFindViewByAutofillIdTraversal != null) {
                    if (!autofillId.isVirtualInt()) {
                        zArr[i] = autofillClientFindViewByAutofillIdTraversal.isVisibleToUser();
                    } else {
                        zArr[i] = autofillClientFindViewByAutofillIdTraversal.isVisibleToUserForAutofill(autofillId.getVirtualChildIntId());
                    }
                }
            }
        }
        if (android.view.autofill.Helper.sVerbose) {
            android.util.Log.v(TAG, "autofillClientGetViewVisibility(): " + java.util.Arrays.toString(zArr));
        }
        return zArr;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public android.view.View autofillClientFindViewByAccessibilityIdTraversal(int i, int i2) {
        android.view.View findViewByAccessibilityIdTraversal;
        java.util.ArrayList<android.view.ViewRootImpl> rootViews = android.view.WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i3 = 0; i3 < rootViews.size(); i3++) {
            android.view.View view = rootViews.get(i3).getView();
            if (view != null && view.getAccessibilityWindowId() == i2 && (findViewByAccessibilityIdTraversal = view.findViewByAccessibilityIdTraversal(i)) != null) {
                return findViewByAccessibilityIdTraversal;
            }
        }
        return null;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public android.view.View autofillClientFindViewByAutofillIdTraversal(android.view.autofill.AutofillId autofillId) {
        android.view.View findViewByAutofillIdTraversal;
        if (autofillId == null) {
            return null;
        }
        java.util.ArrayList<android.view.ViewRootImpl> rootViews = android.view.WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i = 0; i < rootViews.size(); i++) {
            android.view.View view = rootViews.get(i).getView();
            if (view != null && (findViewByAutofillIdTraversal = view.findViewByAutofillIdTraversal(autofillId.getViewId())) != null) {
                return findViewByAutofillIdTraversal;
            }
        }
        return null;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public android.view.View[] autofillClientFindViewsByAutofillIdTraversal(android.view.autofill.AutofillId[] autofillIdArr) {
        android.view.View[] viewArr = new android.view.View[autofillIdArr.length];
        java.util.ArrayList<android.view.ViewRootImpl> rootViews = android.view.WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i = 0; i < rootViews.size(); i++) {
            android.view.View view = rootViews.get(i).getView();
            if (view != null) {
                int length = autofillIdArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    if (autofillIdArr[i2] != null && viewArr[i2] == null) {
                        viewArr[i2] = view.findViewByAutofillIdTraversal(autofillIdArr[i2].getViewId());
                    }
                }
            }
        }
        return viewArr;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientIsFillUiShowing() {
        return this.mAutofillPopupWindow != null && this.mAutofillPopupWindow.isShowing();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientRequestHideFillUi() {
        if (this.mAutofillPopupWindow == null) {
            return false;
        }
        this.mAutofillPopupWindow.dismiss();
        this.mAutofillPopupWindow = null;
        return true;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean autofillClientRequestShowFillUi(android.view.View view, int i, int i2, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) {
        boolean isShowing;
        if (this.mAutofillPopupWindow == null) {
            this.mAutofillPopupWindow = new android.view.autofill.AutofillPopupWindow(iAutofillWindowPresenter);
            isShowing = false;
        } else {
            isShowing = this.mAutofillPopupWindow.isShowing();
        }
        this.mAutofillPopupWindow.update(view, 0, 0, i, i2, rect);
        return !isShowing && this.mAutofillPopupWindow.isShowing();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public void autofillClientDispatchUnhandledKey(android.view.View view, android.view.KeyEvent keyEvent) {
        android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.dispatchKeyFromAutofill(keyEvent);
        }
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public boolean isDisablingEnterExitEventForAutofill() {
        return this.mAutoFillIgnoreFirstResumePause || !this.mActivity.isResumed();
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public void autofillClientResetableStateAvailable() {
        this.mAutoFillResetNeeded = true;
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public void autofillClientRunOnUiThread(java.lang.Runnable runnable) {
        this.mActivity.runOnUiThread(runnable);
    }

    @Override // android.view.autofill.AutofillManager.AutofillClient
    public void autofillClientAuthenticate(int i, android.content.IntentSender intentSender, android.content.Intent intent, boolean z) {
        try {
            this.mActivity.startIntentSenderForResult(intentSender, AUTO_FILL_AUTH_WHO_PREFIX, i, intent, 0, 0, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
            android.util.Log.e(TAG, "authenticate() failed for intent:" + intentSender, e);
        }
    }
}
