package android.app;

/* loaded from: classes.dex */
public class Dialog implements android.content.DialogInterface, android.view.Window.Callback, android.view.KeyEvent.Callback, android.view.View.OnCreateContextMenuListener, android.view.Window.OnWindowDismissedCallback {
    private static final int CANCEL = 68;
    private static final java.lang.String DIALOG_HIERARCHY_TAG = "android:dialogHierarchy";
    private static final java.lang.String DIALOG_SHOWING_TAG = "android:dialogShowing";
    private static final int DISMISS = 67;
    private static final int SHOW = 69;
    private static final java.lang.String TAG = "Dialog";
    private android.app.ActionBar mActionBar;
    private android.view.ActionMode mActionMode;
    private int mActionModeTypeStarting;
    private java.lang.String mCancelAndDismissTaken;
    private android.os.Message mCancelMessage;
    protected boolean mCancelable;
    private boolean mCanceled;
    final android.content.Context mContext;
    private boolean mCreated;
    android.view.View mDecor;
    private android.window.OnBackInvokedCallback mDefaultBackCallback;
    private final java.lang.Runnable mDismissAction;
    private android.os.Message mDismissMessage;
    private java.lang.Runnable mDismissOverride;
    private final android.os.Handler mHandler;
    private final android.os.Handler mListenersHandler;
    private android.content.DialogInterface.OnKeyListener mOnKeyListener;
    private android.app.Activity mOwnerActivity;
    private android.view.SearchEvent mSearchEvent;
    private android.os.Message mShowMessage;
    private boolean mShowing;
    final android.view.Window mWindow;
    private final android.view.WindowManager mWindowManager;

    public Dialog(android.content.Context context) {
        this(context, 0, true);
    }

    public Dialog(android.content.Context context, int i) {
        this(context, i, true);
    }

    Dialog(android.content.Context context, int i, boolean z) {
        this.mCancelable = true;
        this.mCreated = false;
        this.mShowing = false;
        this.mCanceled = false;
        this.mHandler = new android.os.Handler();
        this.mActionModeTypeStarting = 0;
        this.mDismissAction = new java.lang.Runnable() { // from class: android.app.Dialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.Dialog.this.dismissDialog();
            }
        };
        if (z) {
            if (i == 0) {
                android.util.TypedValue typedValue = new android.util.TypedValue();
                context.getTheme().resolveAttribute(16843528, typedValue, true);
                i = typedValue.resourceId;
            }
            this.mContext = new android.view.ContextThemeWrapper(context, i);
        } else {
            this.mContext = context;
        }
        this.mWindowManager = (android.view.WindowManager) context.getSystemService(android.content.Context.WINDOW_SERVICE);
        com.android.internal.policy.PhoneWindow phoneWindow = new com.android.internal.policy.PhoneWindow(this.mContext);
        this.mWindow = phoneWindow;
        phoneWindow.setCallback(this);
        phoneWindow.setOnWindowDismissedCallback(this);
        phoneWindow.setOnWindowSwipeDismissedCallback(new android.view.Window.OnWindowSwipeDismissedCallback() { // from class: android.app.Dialog$$ExternalSyntheticLambda1
            @Override // android.view.Window.OnWindowSwipeDismissedCallback
            public final void onWindowSwipeDismissed() {
                android.app.Dialog.this.lambda$new$0();
            }
        });
        phoneWindow.setWindowManager(this.mWindowManager, null, null);
        phoneWindow.setGravity(17);
        this.mListenersHandler = new android.app.Dialog.ListenersHandler(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (this.mCancelable) {
            cancel();
        }
    }

    @java.lang.Deprecated
    protected Dialog(android.content.Context context, boolean z, android.os.Message message) {
        this(context);
        this.mCancelable = z;
        this.mCancelMessage = message;
    }

    protected Dialog(android.content.Context context, boolean z, android.content.DialogInterface.OnCancelListener onCancelListener) {
        this(context);
        this.mCancelable = z;
        setOnCancelListener(onCancelListener);
    }

    public final android.content.Context getContext() {
        return this.mContext;
    }

    public android.app.ActionBar getActionBar() {
        return this.mActionBar;
    }

    public final void setOwnerActivity(android.app.Activity activity) {
        this.mOwnerActivity = activity;
        getWindow().setVolumeControlStream(this.mOwnerActivity.getVolumeControlStream());
    }

    public final android.app.Activity getOwnerActivity() {
        return this.mOwnerActivity;
    }

    public boolean isShowing() {
        return this.mDecor != null && this.mDecor.getVisibility() == 0;
    }

    public void create() {
        if (!this.mCreated) {
            dispatchOnCreate(null);
        }
    }

    public void show() {
        boolean z = false;
        if (this.mShowing) {
            if (this.mDecor != null) {
                if (this.mWindow.hasFeature(8)) {
                    this.mWindow.invalidatePanelMenu(8);
                }
                this.mDecor.setVisibility(0);
                return;
            }
            return;
        }
        this.mCanceled = false;
        if (!this.mCreated) {
            dispatchOnCreate(null);
        } else {
            this.mWindow.getDecorView().dispatchConfigurationChanged(this.mContext.getResources().getConfiguration());
        }
        onStart();
        this.mDecor = this.mWindow.getDecorView();
        if (this.mActionBar == null && this.mWindow.hasFeature(8)) {
            android.content.pm.ApplicationInfo applicationInfo = this.mContext.getApplicationInfo();
            this.mWindow.setDefaultIcon(applicationInfo.icon);
            this.mWindow.setDefaultLogo(applicationInfo.logo);
            this.mActionBar = new com.android.internal.app.WindowDecorActionBar(this);
        }
        android.view.WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        if ((attributes.softInputMode & 256) == 0) {
            attributes.softInputMode |= 256;
            z = true;
        }
        this.mWindowManager.addView(this.mDecor, attributes);
        if (z) {
            attributes.softInputMode &= -257;
        }
        this.mShowing = true;
        sendShowMessage();
    }

    public void hide() {
        if (this.mDecor != null) {
            this.mDecor.setVisibility(8);
        }
    }

    @Override // android.content.DialogInterface
    public void dismiss() {
        if (this.mDismissOverride != null) {
            this.mDismissOverride.run();
        } else if (android.os.Looper.myLooper() == this.mHandler.getLooper()) {
            dismissDialog();
        } else {
            this.mHandler.post(this.mDismissAction);
        }
    }

    void dismissDialog() {
        if (this.mDecor == null || !this.mShowing) {
            return;
        }
        if (this.mWindow.isDestroyed()) {
            android.util.Log.e(TAG, "Tried to dismissDialog() but the Dialog's window was already destroyed!");
            return;
        }
        try {
            this.mWindowManager.removeViewImmediate(this.mDecor);
        } finally {
            if (this.mActionMode != null) {
                this.mActionMode.finish();
            }
            this.mDecor = null;
            this.mWindow.closeAllPanels();
            onStop();
            this.mShowing = false;
            sendDismissMessage();
        }
    }

    private void sendDismissMessage() {
        if (this.mDismissMessage != null) {
            android.os.Message.obtain(this.mDismissMessage).sendToTarget();
        }
    }

    private void sendShowMessage() {
        if (this.mShowMessage != null) {
            android.os.Message.obtain(this.mShowMessage).sendToTarget();
        }
    }

    void dispatchOnCreate(android.os.Bundle bundle) {
        if (!this.mCreated) {
            onCreate(bundle);
            this.mCreated = true;
        }
    }

    protected void onCreate(android.os.Bundle bundle) {
    }

    protected void onStart() {
        if (this.mActionBar != null) {
            this.mActionBar.setShowHideAnimationEnabled(true);
        }
        if (allowsRegisterDefaultOnBackInvokedCallback() && this.mContext != null && android.window.WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mContext)) {
            this.mDefaultBackCallback = new android.window.OnBackInvokedCallback() { // from class: android.app.Dialog$$ExternalSyntheticLambda2
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    android.app.Dialog.this.onBackPressed();
                }
            };
            getOnBackInvokedDispatcher().registerSystemOnBackInvokedCallback(this.mDefaultBackCallback);
        }
    }

    protected void onStop() {
        if (this.mActionBar != null) {
            this.mActionBar.setShowHideAnimationEnabled(false);
        }
        if (this.mDefaultBackCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mDefaultBackCallback);
            this.mDefaultBackCallback = null;
        }
    }

    protected boolean allowsRegisterDefaultOnBackInvokedCallback() {
        return true;
    }

    public android.os.Bundle onSaveInstanceState() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean(DIALOG_SHOWING_TAG, this.mShowing);
        if (this.mCreated) {
            bundle.putBundle(DIALOG_HIERARCHY_TAG, this.mWindow.saveHierarchyState());
        }
        return bundle;
    }

    public void onRestoreInstanceState(android.os.Bundle bundle) {
        android.os.Bundle bundle2 = bundle.getBundle(DIALOG_HIERARCHY_TAG);
        if (bundle2 == null) {
            return;
        }
        dispatchOnCreate(bundle);
        this.mWindow.restoreHierarchyState(bundle2);
        if (bundle.getBoolean(DIALOG_SHOWING_TAG)) {
            show();
        }
    }

    public android.view.Window getWindow() {
        return this.mWindow;
    }

    public android.view.View getCurrentFocus() {
        if (this.mWindow != null) {
            return this.mWindow.getCurrentFocus();
        }
        return null;
    }

    public <T extends android.view.View> T findViewById(int i) {
        return (T) this.mWindow.findViewById(i);
    }

    public final <T extends android.view.View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new java.lang.IllegalArgumentException("ID does not reference a View inside this Dialog");
        }
        return t;
    }

    public void setContentView(int i) {
        this.mWindow.setContentView(i);
    }

    public void setContentView(android.view.View view) {
        this.mWindow.setContentView(view);
    }

    public void setContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        this.mWindow.setContentView(view, layoutParams);
    }

    public void addContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        this.mWindow.addContentView(view, layoutParams);
    }

    public void setTitle(java.lang.CharSequence charSequence) {
        this.mWindow.setTitle(charSequence);
        this.mWindow.getAttributes().setTitle(charSequence);
    }

    public void setTitle(int i) {
        setTitle(this.mContext.getText(i));
    }

    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (i == 4) {
            keyEvent.startTracking();
            return true;
        }
        if (i == 111) {
            if (this.mCancelable) {
                cancel();
            } else {
                dismiss();
            }
            keyEvent.startTracking();
            return true;
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
            switch (i) {
                case 4:
                    if (!android.window.WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mContext) || !allowsRegisterDefaultOnBackInvokedCallback()) {
                        onBackPressed();
                        break;
                    }
                    break;
                case 111:
                    break;
            }
            return true;
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        return false;
    }

    @java.lang.Deprecated
    public void onBackPressed() {
        if (this.mCancelable) {
            cancel();
        }
    }

    public boolean onKeyShortcut(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (this.mCancelable && this.mShowing && this.mWindow.shouldCloseOnTouch(this.mContext, motionEvent)) {
            cancel();
            return true;
        }
        return false;
    }

    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(android.view.WindowManager.LayoutParams layoutParams) {
        if (this.mDecor != null) {
            this.mWindowManager.updateViewLayout(this.mDecor, layoutParams);
        }
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
    }

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
    }

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {
    }

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {
    }

    @Override // android.view.Window.OnWindowDismissedCallback
    public void onWindowDismissed(boolean z, boolean z2) {
        dismiss();
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        if ((this.mOnKeyListener == null || !this.mOnKeyListener.onKey(this, keyEvent.getKeyCode(), keyEvent)) && !this.mWindow.superDispatchKeyEvent(keyEvent)) {
            return keyEvent.dispatch(this, this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null, this);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
        if (this.mWindow.superDispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        return onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        if (this.mWindow.superDispatchTouchEvent(motionEvent)) {
            return true;
        }
        return onTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        if (this.mWindow.superDispatchTrackballEvent(motionEvent)) {
            return true;
        }
        return onTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
        if (this.mWindow.superDispatchGenericMotionEvent(motionEvent)) {
            return true;
        }
        return onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(getClass().getName());
        accessibilityEvent.setPackageName(this.mContext.getPackageName());
        android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
        accessibilityEvent.setFullScreen(attributes.width == -1 && attributes.height == -1);
        return false;
    }

    @Override // android.view.Window.Callback
    public android.view.View onCreatePanelView(int i) {
        return null;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, android.view.Menu menu) {
        if (i == 0) {
            return onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i, android.view.View view, android.view.Menu menu) {
        if (i == 0) {
            return onPrepareOptionsMenu(menu) && menu.hasVisibleItems();
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i, android.view.Menu menu) {
        if (i == 8) {
            this.mActionBar.dispatchMenuVisibilityChanged(true);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int i, android.view.MenuItem menuItem) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i, android.view.Menu menu) {
        if (i == 8) {
            this.mActionBar.dispatchMenuVisibilityChanged(false);
        }
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        return true;
    }

    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem menuItem) {
        return false;
    }

    public void onOptionsMenuClosed(android.view.Menu menu) {
    }

    public void openOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.openPanel(0, null);
        }
    }

    public void closeOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.closePanel(0);
        }
    }

    public void invalidateOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            this.mWindow.invalidatePanelMenu(0);
        }
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(android.view.ContextMenu contextMenu, android.view.View view, android.view.ContextMenu.ContextMenuInfo contextMenuInfo) {
    }

    public void registerForContextMenu(android.view.View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(android.view.View view) {
        view.setOnCreateContextMenuListener(null);
    }

    public void openContextMenu(android.view.View view) {
        view.showContextMenu();
    }

    public boolean onContextItemSelected(android.view.MenuItem menuItem) {
        return false;
    }

    public void onContextMenuClosed(android.view.Menu menu) {
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(android.view.SearchEvent searchEvent) {
        this.mSearchEvent = searchEvent;
        return onSearchRequested();
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        android.app.SearchManager searchManager = (android.app.SearchManager) this.mContext.getSystemService("search");
        android.content.ComponentName associatedActivity = getAssociatedActivity();
        if (associatedActivity != null && searchManager.getSearchableInfo(associatedActivity) != null) {
            searchManager.startSearch(null, false, associatedActivity, null, false);
            dismiss();
            return true;
        }
        return false;
    }

    public final android.view.SearchEvent getSearchEvent() {
        return this.mSearchEvent;
    }

    @Override // android.view.Window.Callback
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback) {
        if (this.mActionBar != null && this.mActionModeTypeStarting == 0) {
            return this.mActionBar.startActionMode(callback);
        }
        return null;
    }

    @Override // android.view.Window.Callback
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback, int i) {
        try {
            this.mActionModeTypeStarting = i;
            return onWindowStartingActionMode(callback);
        } finally {
            this.mActionModeTypeStarting = 0;
        }
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(android.view.ActionMode actionMode) {
        this.mActionMode = actionMode;
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(android.view.ActionMode actionMode) {
        if (actionMode == this.mActionMode) {
            this.mActionMode = null;
        }
    }

    private android.content.ComponentName getAssociatedActivity() {
        android.app.Activity activity = this.mOwnerActivity;
        android.content.Context context = getContext();
        while (activity == null && context != null) {
            if (context instanceof android.app.Activity) {
                activity = (android.app.Activity) context;
            } else if (context instanceof android.content.ContextWrapper) {
                context = ((android.content.ContextWrapper) context).getBaseContext();
            } else {
                context = null;
            }
        }
        if (activity == null) {
            return null;
        }
        return activity.getComponentName();
    }

    public void takeKeyEvents(boolean z) {
        this.mWindow.takeKeyEvents(z);
    }

    public final boolean requestWindowFeature(int i) {
        return getWindow().requestFeature(i);
    }

    public final void setFeatureDrawableResource(int i, int i2) {
        getWindow().setFeatureDrawableResource(i, i2);
    }

    public final void setFeatureDrawableUri(int i, android.net.Uri uri) {
        getWindow().setFeatureDrawableUri(i, uri);
    }

    public final void setFeatureDrawable(int i, android.graphics.drawable.Drawable drawable) {
        getWindow().setFeatureDrawable(i, drawable);
    }

    public final void setFeatureDrawableAlpha(int i, int i2) {
        getWindow().setFeatureDrawableAlpha(i, i2);
    }

    public android.view.LayoutInflater getLayoutInflater() {
        return getWindow().getLayoutInflater();
    }

    public void setCancelable(boolean z) {
        this.mCancelable = z;
    }

    public void setCanceledOnTouchOutside(boolean z) {
        if (z && !this.mCancelable) {
            this.mCancelable = true;
        }
        this.mWindow.setCloseOnTouchOutside(z);
    }

    @Override // android.content.DialogInterface
    public void cancel() {
        if (!this.mCanceled && this.mCancelMessage != null) {
            this.mCanceled = true;
            android.os.Message.obtain(this.mCancelMessage).sendToTarget();
        }
        dismiss();
    }

    public void setOnCancelListener(android.content.DialogInterface.OnCancelListener onCancelListener) {
        if (this.mCancelAndDismissTaken != null) {
            throw new java.lang.IllegalStateException("OnCancelListener is already taken by " + this.mCancelAndDismissTaken + " and can not be replaced.");
        }
        if (onCancelListener != null) {
            this.mCancelMessage = this.mListenersHandler.obtainMessage(68, onCancelListener);
        } else {
            this.mCancelMessage = null;
        }
    }

    public void setCancelMessage(android.os.Message message) {
        this.mCancelMessage = message;
    }

    public void setOnDismissListener(android.content.DialogInterface.OnDismissListener onDismissListener) {
        if (this.mCancelAndDismissTaken != null) {
            throw new java.lang.IllegalStateException("OnDismissListener is already taken by " + this.mCancelAndDismissTaken + " and can not be replaced.");
        }
        if (onDismissListener != null) {
            this.mDismissMessage = this.mListenersHandler.obtainMessage(67, onDismissListener);
        } else {
            this.mDismissMessage = null;
        }
    }

    public void setOnShowListener(android.content.DialogInterface.OnShowListener onShowListener) {
        if (onShowListener != null) {
            this.mShowMessage = this.mListenersHandler.obtainMessage(69, onShowListener);
        } else {
            this.mShowMessage = null;
        }
    }

    public void setDismissMessage(android.os.Message message) {
        this.mDismissMessage = message;
    }

    public void setDismissOverride(java.lang.Runnable runnable) {
        this.mDismissOverride = runnable;
    }

    public boolean takeCancelAndDismissListeners(java.lang.String str, android.content.DialogInterface.OnCancelListener onCancelListener, android.content.DialogInterface.OnDismissListener onDismissListener) {
        if (this.mCancelAndDismissTaken != null) {
            this.mCancelAndDismissTaken = null;
        } else if (this.mCancelMessage != null || this.mDismissMessage != null) {
            return false;
        }
        setOnCancelListener(onCancelListener);
        setOnDismissListener(onDismissListener);
        this.mCancelAndDismissTaken = str;
        return true;
    }

    public final void setVolumeControlStream(int i) {
        getWindow().setVolumeControlStream(i);
    }

    public final int getVolumeControlStream() {
        return getWindow().getVolumeControlStream();
    }

    public void setOnKeyListener(android.content.DialogInterface.OnKeyListener onKeyListener) {
        this.mOnKeyListener = onKeyListener;
    }

    private static final class ListenersHandler extends android.os.Handler {
        private final java.lang.ref.WeakReference<android.content.DialogInterface> mDialog;

        public ListenersHandler(android.app.Dialog dialog) {
            this.mDialog = new java.lang.ref.WeakReference<>(dialog);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 67:
                    ((android.content.DialogInterface.OnDismissListener) message.obj).onDismiss(this.mDialog.get());
                    break;
                case 68:
                    ((android.content.DialogInterface.OnCancelListener) message.obj).onCancel(this.mDialog.get());
                    break;
                case 69:
                    ((android.content.DialogInterface.OnShowListener) message.obj).onShow(this.mDialog.get());
                    break;
            }
        }
    }

    public android.window.OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return this.mWindow.getOnBackInvokedDispatcher();
    }
}
