package android.widget;

/* loaded from: classes4.dex */
public class Toast {
    private static final long CHANGE_TEXT_TOASTS_IN_THE_SYSTEM = 147798919;
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    static final java.lang.String TAG = "Toast";
    static final boolean localLOGV = false;
    private static android.app.INotificationManager sService;
    private final java.util.List<android.widget.Toast.Callback> mCallbacks;
    private final android.content.Context mContext;
    int mDuration;
    private final android.os.Handler mHandler;
    private android.view.View mNextView;
    final android.widget.Toast.TN mTN;
    private java.lang.CharSequence mText;
    private final android.os.Binder mToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public Toast(android.content.Context context) {
        this(context, null);
    }

    public Toast(android.content.Context context, android.os.Looper looper) {
        this.mContext = context;
        this.mToken = new android.os.Binder();
        android.os.Looper looper2 = getLooper(looper);
        this.mHandler = new android.os.Handler(looper2);
        this.mCallbacks = new java.util.ArrayList();
        this.mTN = new android.widget.Toast.TN(context, context.getPackageName(), this.mToken, this.mCallbacks, looper2);
        this.mTN.mY = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.toast_y_offset);
        this.mTN.mGravity = context.getResources().getInteger(com.android.internal.R.integer.config_toastDefaultGravity);
    }

    private android.os.Looper getLooper(android.os.Looper looper) {
        if (looper != null) {
            return looper;
        }
        return (android.os.Looper) com.android.internal.util.Preconditions.checkNotNull(android.os.Looper.myLooper(), "Can't toast on a thread that has not called Looper.prepare()");
    }

    public void show() {
        if (android.compat.Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            com.android.internal.util.Preconditions.checkState((this.mNextView == null && this.mText == null) ? false : true, "You must either set a text or a view");
        } else if (this.mNextView == null) {
            throw new java.lang.RuntimeException("setView must have been called");
        }
        android.app.INotificationManager service = getService();
        java.lang.String opPackageName = this.mContext.getOpPackageName();
        android.widget.Toast.TN tn = this.mTN;
        tn.mNextView = new java.lang.ref.WeakReference<>(this.mNextView);
        boolean isUiContext = this.mContext.isUiContext();
        int displayId = this.mContext.getDisplayId();
        try {
            if (android.compat.Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
                if (this.mNextView != null) {
                    service.enqueueToast(opPackageName, this.mToken, tn, this.mDuration, isUiContext, displayId);
                } else {
                    service.enqueueTextToast(opPackageName, this.mToken, this.mText, this.mDuration, isUiContext, displayId, new android.widget.Toast.CallbackBinder(this.mCallbacks, this.mHandler));
                }
            } else {
                service.enqueueToast(opPackageName, this.mToken, tn, this.mDuration, isUiContext, displayId);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public void cancel() {
        if (android.compat.Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM) && this.mNextView == null) {
            try {
                getService().cancelToast(this.mContext.getOpPackageName(), this.mToken);
            } catch (android.os.RemoteException e) {
            }
        } else {
            this.mTN.cancel();
        }
    }

    @java.lang.Deprecated
    public void setView(android.view.View view) {
        this.mNextView = view;
    }

    @java.lang.Deprecated
    public android.view.View getView() {
        return this.mNextView;
    }

    public void setDuration(int i) {
        this.mDuration = i;
        this.mTN.mDuration = i;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setMargin(float f, float f2) {
        if (isSystemRenderedTextToast()) {
            android.util.Log.e(TAG, "setMargin() shouldn't be called on text toasts, the values won't be used");
        }
        this.mTN.mHorizontalMargin = f;
        this.mTN.mVerticalMargin = f2;
    }

    public float getHorizontalMargin() {
        if (isSystemRenderedTextToast()) {
            android.util.Log.e(TAG, "getHorizontalMargin() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mHorizontalMargin;
    }

    public float getVerticalMargin() {
        if (isSystemRenderedTextToast()) {
            android.util.Log.e(TAG, "getVerticalMargin() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mVerticalMargin;
    }

    public void setGravity(int i, int i2, int i3) {
        if (isSystemRenderedTextToast()) {
            android.util.Log.e(TAG, "setGravity() shouldn't be called on text toasts, the values won't be used");
        }
        this.mTN.mGravity = i;
        this.mTN.mX = i2;
        this.mTN.mY = i3;
    }

    public int getGravity() {
        if (isSystemRenderedTextToast()) {
            android.util.Log.e(TAG, "getGravity() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mGravity;
    }

    public int getXOffset() {
        if (isSystemRenderedTextToast()) {
            android.util.Log.e(TAG, "getXOffset() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mX;
    }

    public int getYOffset() {
        if (isSystemRenderedTextToast()) {
            android.util.Log.e(TAG, "getYOffset() shouldn't be called on text toasts, the result may not reflect actual values.");
        }
        return this.mTN.mY;
    }

    private boolean isSystemRenderedTextToast() {
        return android.compat.Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM) && this.mNextView == null;
    }

    public void addCallback(android.widget.Toast.Callback callback) {
        com.android.internal.util.Preconditions.checkNotNull(callback);
        synchronized (this.mCallbacks) {
            this.mCallbacks.add(callback);
        }
    }

    public void removeCallback(android.widget.Toast.Callback callback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(callback);
        }
    }

    public android.view.WindowManager.LayoutParams getWindowParams() {
        if (android.compat.Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            if (this.mNextView != null) {
                return this.mTN.mParams;
            }
            return null;
        }
        return this.mTN.mParams;
    }

    public static android.widget.Toast makeText(android.content.Context context, java.lang.CharSequence charSequence, int i) {
        return makeText(context, null, charSequence, i);
    }

    public static android.widget.Toast makeText(android.content.Context context, android.os.Looper looper, java.lang.CharSequence charSequence, int i) {
        android.widget.Toast toast = new android.widget.Toast(context, looper);
        if (android.compat.Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            toast.mText = charSequence;
        } else {
            toast.mNextView = android.widget.ToastPresenter.getTextToastView(context, charSequence);
        }
        toast.mDuration = i;
        return toast;
    }

    public static android.widget.Toast makeCustomToastWithIcon(android.content.Context context, android.os.Looper looper, java.lang.CharSequence charSequence, int i, android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            throw new java.lang.IllegalArgumentException("Drawable icon should not be null for makeCustomToastWithIcon");
        }
        android.widget.Toast toast = new android.widget.Toast(context, looper);
        toast.mNextView = android.widget.ToastPresenter.getTextToastViewWithIcon(context, charSequence, drawable);
        toast.mDuration = i;
        return toast;
    }

    public static android.widget.Toast makeText(android.content.Context context, int i, int i2) throws android.content.res.Resources.NotFoundException {
        return makeText(context, context.getResources().getText(i), i2);
    }

    public void setText(int i) {
        setText(this.mContext.getText(i));
    }

    public void setText(java.lang.CharSequence charSequence) {
        if (android.compat.Compatibility.isChangeEnabled(CHANGE_TEXT_TOASTS_IN_THE_SYSTEM)) {
            if (this.mNextView != null) {
                throw new java.lang.IllegalStateException("Text provided for custom toast, remove previous setView() calls if you want a text toast instead.");
            }
            this.mText = charSequence;
        } else {
            if (this.mNextView == null) {
                throw new java.lang.RuntimeException("This Toast was not created with Toast.makeText()");
            }
            android.widget.TextView textView = (android.widget.TextView) this.mNextView.findViewById(16908299);
            if (textView == null) {
                throw new java.lang.RuntimeException("This Toast was not created with Toast.makeText()");
            }
            textView.lambda$setTextAsync$0(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.INotificationManager getService() {
        if (sService != null) {
            return sService;
        }
        sService = android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
        return sService;
    }

    private static class TN extends android.app.ITransientNotification.Stub {
        private static final int CANCEL = 2;
        private static final int HIDE = 1;
        private static final int SHOW = 0;
        private final java.lang.ref.WeakReference<java.util.List<android.widget.Toast.Callback>> mCallbacks;
        int mDuration;
        int mGravity;
        final android.os.Handler mHandler;
        float mHorizontalMargin;
        java.lang.ref.WeakReference<android.view.View> mNextView;
        final java.lang.String mPackageName;
        private final android.view.WindowManager.LayoutParams mParams;
        private final android.widget.ToastPresenter mPresenter;
        final android.os.Binder mToken;
        float mVerticalMargin;
        android.view.View mView;
        android.view.WindowManager mWM;
        int mX;
        int mY;

        TN(android.content.Context context, java.lang.String str, android.os.Binder binder, java.util.List<android.widget.Toast.Callback> list, android.os.Looper looper) {
            this.mPresenter = new android.widget.ToastPresenter(context, android.view.accessibility.IAccessibilityManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.ACCESSIBILITY_SERVICE)), android.widget.Toast.getService(), str);
            this.mParams = this.mPresenter.getLayoutParams();
            this.mPackageName = str;
            this.mToken = binder;
            this.mCallbacks = new java.lang.ref.WeakReference<>(list);
            this.mHandler = new android.os.Handler(looper, null) { // from class: android.widget.Toast.TN.1
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    switch (message.what) {
                        case 0:
                            android.widget.Toast.TN.this.handleShow((android.os.IBinder) message.obj);
                            break;
                        case 1:
                            android.widget.Toast.TN.this.handleHide();
                            android.widget.Toast.TN.this.mNextView = null;
                            break;
                        case 2:
                            android.widget.Toast.TN.this.handleHide();
                            android.widget.Toast.TN.this.mNextView = null;
                            try {
                                android.widget.Toast.getService().cancelToast(android.widget.Toast.TN.this.mPackageName, android.widget.Toast.TN.this.mToken);
                                break;
                            } catch (android.os.RemoteException e) {
                                return;
                            }
                    }
                }
            };
        }

        private java.util.List<android.widget.Toast.Callback> getCallbacks() {
            synchronized (this.mCallbacks) {
                if (this.mCallbacks.get() != null) {
                    return new java.util.ArrayList(this.mCallbacks.get());
                }
                return new java.util.ArrayList();
            }
        }

        @Override // android.app.ITransientNotification
        public void show(android.os.IBinder iBinder) {
            this.mHandler.obtainMessage(0, iBinder).sendToTarget();
        }

        @Override // android.app.ITransientNotification
        public void hide() {
            this.mHandler.obtainMessage(1).sendToTarget();
        }

        public void cancel() {
            this.mHandler.obtainMessage(2).sendToTarget();
        }

        public void handleShow(android.os.IBinder iBinder) {
            if (!this.mHandler.hasMessages(2) && !this.mHandler.hasMessages(1) && this.mNextView != null && this.mView != this.mNextView.get()) {
                handleHide();
                this.mView = this.mNextView.get();
                if (this.mView != null) {
                    this.mPresenter.show(this.mView, this.mToken, iBinder, this.mDuration, this.mGravity, this.mX, this.mY, this.mHorizontalMargin, this.mVerticalMargin, new android.widget.Toast.CallbackBinder(getCallbacks(), this.mHandler));
                }
            }
        }

        public void handleHide() {
            if (this.mView != null) {
                com.android.internal.util.Preconditions.checkState(this.mView == this.mPresenter.getView(), "Trying to hide toast view different than the last one displayed");
                this.mPresenter.hide(new android.widget.Toast.CallbackBinder(getCallbacks(), this.mHandler));
                this.mView = null;
            }
        }
    }

    public static abstract class Callback {
        public void onToastShown() {
        }

        public void onToastHidden() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CallbackBinder extends android.app.ITransientNotificationCallback.Stub {
        private final java.util.List<android.widget.Toast.Callback> mCallbacks;
        private final android.os.Handler mHandler;

        private CallbackBinder(java.util.List<android.widget.Toast.Callback> list, android.os.Handler handler) {
            this.mCallbacks = list;
            this.mHandler = handler;
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastShown() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.widget.Toast$CallbackBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.Toast.CallbackBinder.this.lambda$onToastShown$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onToastShown$0() {
            java.util.Iterator<android.widget.Toast.Callback> it = getCallbacks().iterator();
            while (it.hasNext()) {
                it.next().onToastShown();
            }
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastHidden() {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.widget.Toast$CallbackBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.Toast.CallbackBinder.this.lambda$onToastHidden$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onToastHidden$1() {
            java.util.Iterator<android.widget.Toast.Callback> it = getCallbacks().iterator();
            while (it.hasNext()) {
                it.next().onToastHidden();
            }
        }

        private java.util.List<android.widget.Toast.Callback> getCallbacks() {
            java.util.ArrayList arrayList;
            synchronized (this.mCallbacks) {
                arrayList = new java.util.ArrayList(this.mCallbacks);
            }
            return arrayList;
        }
    }
}
