package android.widget;

/* loaded from: classes4.dex */
public class ToastPresenter {
    private static final long LONG_DURATION_TIMEOUT = 7000;
    private static final long SHORT_DURATION_TIMEOUT = 4000;
    private static final java.lang.String TAG = "ToastPresenter";
    public static final int TEXT_TOAST_LAYOUT = 17367371;
    public static final int TEXT_TOAST_LAYOUT_WITH_ICON = 17367372;
    private static final java.lang.String WINDOW_TITLE = "Toast";
    private final android.view.accessibility.IAccessibilityManager mAccessibilityManagerService;
    private final java.lang.ref.WeakReference<android.content.Context> mContext;
    private final java.lang.String mContextPackageName;
    private final android.app.INotificationManager mNotificationManager;
    private final java.lang.String mPackageName;
    private final android.view.WindowManager.LayoutParams mParams = createLayoutParams();
    private final android.content.res.Resources mResources;
    private android.os.IBinder mToken;
    private android.view.View mView;

    public static android.view.View getTextToastView(android.content.Context context, java.lang.CharSequence charSequence) {
        android.view.View inflate = android.view.LayoutInflater.from(context).inflate(17367371, (android.view.ViewGroup) null);
        ((android.widget.TextView) inflate.findViewById(16908299)).lambda$setTextAsync$0(charSequence);
        return inflate;
    }

    public static android.view.View getTextToastViewWithIcon(android.content.Context context, java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            return getTextToastView(context, charSequence);
        }
        android.view.View inflate = android.view.LayoutInflater.from(context).inflate(17367372, (android.view.ViewGroup) null);
        ((android.widget.TextView) inflate.findViewById(16908299)).lambda$setTextAsync$0(charSequence);
        android.widget.ImageView imageView = (android.widget.ImageView) inflate.findViewById(16908294);
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$2(drawable);
        }
        return inflate;
    }

    public ToastPresenter(android.content.Context context, android.view.accessibility.IAccessibilityManager iAccessibilityManager, android.app.INotificationManager iNotificationManager, java.lang.String str) {
        this.mContext = new java.lang.ref.WeakReference<>(context);
        this.mResources = context.getResources();
        this.mNotificationManager = iNotificationManager;
        this.mPackageName = str;
        this.mContextPackageName = context.getPackageName();
        this.mAccessibilityManagerService = iAccessibilityManager;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.view.WindowManager.LayoutParams getLayoutParams() {
        return this.mParams;
    }

    public android.view.View getView() {
        return this.mView;
    }

    public android.os.IBinder getToken() {
        return this.mToken;
    }

    private android.view.WindowManager.LayoutParams createLayoutParams() {
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = 16973828;
        layoutParams.type = 2005;
        layoutParams.setFitInsetsIgnoringVisibility(true);
        layoutParams.setTitle(WINDOW_TITLE);
        layoutParams.flags = 152;
        setShowForAllUsersIfApplicable(layoutParams, this.mPackageName);
        return layoutParams;
    }

    private void adjustLayoutParams(android.view.WindowManager.LayoutParams layoutParams, android.os.IBinder iBinder, int i, int i2, int i3, int i4, float f, float f2, boolean z) {
        int absoluteGravity = android.view.Gravity.getAbsoluteGravity(i2, this.mResources.getConfiguration().getLayoutDirection());
        layoutParams.gravity = absoluteGravity;
        if ((absoluteGravity & 7) == 7) {
            layoutParams.horizontalWeight = 1.0f;
        }
        if ((absoluteGravity & 112) == 112) {
            layoutParams.verticalWeight = 1.0f;
        }
        layoutParams.x = i3;
        layoutParams.y = i4;
        layoutParams.horizontalMargin = f;
        layoutParams.verticalMargin = f2;
        layoutParams.packageName = this.mContextPackageName;
        layoutParams.hideTimeoutMilliseconds = i == 1 ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
        layoutParams.token = iBinder;
        if (z && layoutParams.windowAnimations == 16973828) {
            layoutParams.windowAnimations = 0;
        }
    }

    public void updateLayoutParams(int i, int i2, float f, float f2, int i3) {
        com.android.internal.util.Preconditions.checkState(this.mView != null, "Toast must be showing to update its layout parameters.");
        this.mParams.gravity = android.view.Gravity.getAbsoluteGravity(i3, this.mResources.getConfiguration().getLayoutDirection());
        this.mParams.x = i;
        this.mParams.y = i2;
        this.mParams.horizontalMargin = f;
        this.mParams.verticalMargin = f2;
        this.mView.setLayoutParams(this.mParams);
    }

    private void setShowForAllUsersIfApplicable(android.view.WindowManager.LayoutParams layoutParams, java.lang.String str) {
        if (isCrossUserPackage(str)) {
            layoutParams.privateFlags = 16;
        }
    }

    private boolean isCrossUserPackage(java.lang.String str) {
        return com.android.internal.util.ArrayUtils.contains(this.mResources.getStringArray(com.android.internal.R.array.config_toastCrossUserPackages), str);
    }

    public void show(android.view.View view, android.os.IBinder iBinder, android.os.IBinder iBinder2, int i, int i2, int i3, int i4, float f, float f2, android.app.ITransientNotificationCallback iTransientNotificationCallback) {
        show(view, iBinder, iBinder2, i, i2, i3, i4, f, f2, iTransientNotificationCallback, false);
    }

    public void show(android.view.View view, android.os.IBinder iBinder, android.os.IBinder iBinder2, int i, int i2, int i3, int i4, float f, float f2, android.app.ITransientNotificationCallback iTransientNotificationCallback, boolean z) {
        com.android.internal.util.Preconditions.checkState(this.mView == null, "Only one toast at a time is allowed, call hide() first.");
        this.mView = view;
        this.mToken = iBinder;
        adjustLayoutParams(this.mParams, iBinder2, i, i2, i3, i4, f, f2, z);
        addToastView();
        trySendAccessibilityEvent(this.mView, this.mPackageName);
        if (iTransientNotificationCallback != null) {
            try {
                iTransientNotificationCallback.onToastShown();
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "Error calling back " + this.mPackageName + " to notify onToastShow()", e);
            }
        }
    }

    public void hide(android.app.ITransientNotificationCallback iTransientNotificationCallback) {
        com.android.internal.util.Preconditions.checkState(this.mView != null, "No toast to hide.");
        android.view.WindowManager windowManager = getWindowManager(this.mView);
        if (this.mView.getParent() != null && windowManager != null) {
            windowManager.removeViewImmediate(this.mView);
        }
        try {
            this.mNotificationManager.finishToken(this.mPackageName, this.mToken);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Error finishing toast window token from package " + this.mPackageName, e);
        }
        if (iTransientNotificationCallback != null) {
            try {
                iTransientNotificationCallback.onToastHidden();
            } catch (android.os.RemoteException e2) {
                android.util.Log.w(TAG, "Error calling back " + this.mPackageName + " to notify onToastHide()", e2);
            }
        }
        this.mView = null;
        this.mToken = null;
    }

    private android.view.WindowManager getWindowManager(android.view.View view) {
        android.content.Context context = this.mContext.get();
        if (context == null && view != null) {
            context = view.getContext();
        }
        if (context != null) {
            return (android.view.WindowManager) context.getSystemService(android.view.WindowManager.class);
        }
        return null;
    }

    public void trySendAccessibilityEvent(android.view.View view, java.lang.String str) {
        android.content.Context context = this.mContext.get();
        if (context == null) {
            return;
        }
        android.view.accessibility.AccessibilityManager accessibilityManager = new android.view.accessibility.AccessibilityManager(context, this.mAccessibilityManagerService, context.getUserId());
        if (!accessibilityManager.isEnabled()) {
            accessibilityManager.removeClient();
            return;
        }
        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(64);
        obtain.setClassName(android.widget.Toast.class.getName());
        obtain.setPackageName(str);
        view.dispatchPopulateAccessibilityEvent(obtain);
        accessibilityManager.sendAccessibilityEvent(obtain);
        accessibilityManager.removeClient();
    }

    private void addToastView() {
        android.view.WindowManager windowManager = getWindowManager(this.mView);
        if (windowManager == null) {
            return;
        }
        if (this.mView.getParent() != null) {
            windowManager.removeView(this.mView);
        }
        try {
            windowManager.addView(this.mView, this.mParams);
        } catch (android.view.WindowManager.BadTokenException e) {
            android.util.Log.w(TAG, "Error while attempting to show toast from " + this.mPackageName, e);
        } catch (android.view.WindowManager.InvalidDisplayException e2) {
            android.util.Log.w(TAG, "Cannot show toast from " + this.mPackageName + " on display it was scheduled on.", e2);
        }
    }
}
