package android.inputmethodservice;

/* loaded from: classes2.dex */
final class InkWindow extends com.android.internal.policy.PhoneWindow {
    private android.view.ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private android.view.View mInkView;
    private android.inputmethodservice.InkWindow.InkVisibilityListener mInkViewVisibilityListener;
    private boolean mIsViewAdded;
    private final android.view.WindowManager mWindowManager;

    interface InkVisibilityListener {
        void onInkViewVisible();
    }

    public InkWindow(android.content.Context context) {
        super(context);
        setType(2011);
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.layoutInDisplayCutoutMode = 3;
        attributes.setFitInsetsTypes(0);
        attributes.windowAnimations = -1;
        setAttributes(attributes);
        addFlags(792);
        setBackgroundDrawableResource(17170445);
        setLayout(-1, -1);
        this.mWindowManager = (android.view.WindowManager) context.getSystemService(android.view.WindowManager.class);
    }

    void initOnly() {
        show(true);
    }

    void show() {
        show(false);
    }

    private void show(boolean z) {
        if (getDecorView() == null) {
            android.util.Slog.i("InputMethodService", "DecorView is not set for InkWindow. show() failed.");
            return;
        }
        getDecorView().setVisibility(z ? 4 : 0);
        if (!this.mIsViewAdded) {
            this.mWindowManager.addView(getDecorView(), getAttributes());
            this.mIsViewAdded = true;
        }
    }

    void hide(boolean z) {
        if (getDecorView() != null) {
            if (z) {
                this.mWindowManager.removeViewImmediate(getDecorView());
            } else {
                getDecorView().setVisibility(4);
            }
        }
    }

    void setToken(android.os.IBinder iBinder) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.token = iBinder;
        setAttributes(attributes);
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void addContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (this.mInkView == null) {
            this.mInkView = view;
        } else if (this.mInkView != view) {
            throw new java.lang.IllegalStateException("Only one Child Inking view is permitted.");
        }
        super.addContentView(view, layoutParams);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void setContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        this.mInkView = view;
        super.setContentView(view, layoutParams);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void setContentView(android.view.View view) {
        this.mInkView = view;
        super.setContentView(view);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void clearContentView() {
        if (this.mGlobalLayoutListener != null && this.mInkView != null) {
            this.mInkView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
        this.mGlobalLayoutListener = null;
        this.mInkView = null;
        super.clearContentView();
    }

    void setInkViewVisibilityListener(android.inputmethodservice.InkWindow.InkVisibilityListener inkVisibilityListener) {
        this.mInkViewVisibilityListener = inkVisibilityListener;
        initInkViewVisibilityListener();
    }

    void initInkViewVisibilityListener() {
        if (this.mInkView == null || this.mInkViewVisibilityListener == null || this.mGlobalLayoutListener != null) {
            return;
        }
        this.mGlobalLayoutListener = new android.view.ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.inputmethodservice.InkWindow.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (android.inputmethodservice.InkWindow.this.mInkView != null && android.inputmethodservice.InkWindow.this.mInkView.isVisibleToUser()) {
                    if (android.inputmethodservice.InkWindow.this.mInkViewVisibilityListener != null) {
                        android.inputmethodservice.InkWindow.this.mInkViewVisibilityListener.onInkViewVisible();
                    }
                    android.inputmethodservice.InkWindow.this.mInkView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    android.inputmethodservice.InkWindow.this.mGlobalLayoutListener = null;
                }
            }
        };
        this.mInkView.getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
    }

    boolean isInkViewVisible() {
        return getDecorView().getVisibility() == 0 && this.mInkView != null && this.mInkView.isVisibleToUser();
    }

    void dispatchHandwritingEvent(android.view.MotionEvent motionEvent) {
        android.view.View decorView = getDecorView();
        java.util.Objects.requireNonNull(decorView);
        android.view.ViewRootImpl viewRootImpl = decorView.getViewRootImpl();
        java.util.Objects.requireNonNull(viewRootImpl);
        viewRootImpl.enqueueInputEvent(android.view.MotionEvent.obtain(motionEvent));
    }
}
