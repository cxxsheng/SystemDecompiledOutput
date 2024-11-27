package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class ZoomButtonsController implements android.view.View.OnTouchListener {
    private static final int MSG_DISMISS_ZOOM_CONTROLS = 3;
    private static final int MSG_POST_CONFIGURATION_CHANGED = 2;
    private static final int MSG_POST_SET_VISIBLE = 4;
    private static final java.lang.String TAG = "ZoomButtonsController";
    private static final int ZOOM_CONTROLS_TIMEOUT = (int) android.view.ViewConfiguration.getZoomControlsTimeout();
    private static final int ZOOM_CONTROLS_TOUCH_PADDING = 20;
    private android.widget.ZoomButtonsController.OnZoomListener mCallback;
    private final android.widget.FrameLayout mContainer;
    private android.view.WindowManager.LayoutParams mContainerLayoutParams;
    private final android.content.Context mContext;
    private android.widget.ZoomControls mControls;
    private boolean mIsVisible;
    private final android.view.View mOwnerView;
    private java.lang.Runnable mPostedVisibleInitializer;
    private boolean mReleaseTouchListenerOnUp;
    private int mTouchPaddingScaledSq;
    private android.view.View mTouchTargetView;
    private final android.view.WindowManager mWindowManager;
    private boolean mAutoDismissControls = true;
    private final int[] mOwnerViewRawLocation = new int[2];
    private final int[] mContainerRawLocation = new int[2];
    private final int[] mTouchTargetWindowLocation = new int[2];
    private final android.graphics.Rect mTempRect = new android.graphics.Rect();
    private final int[] mTempIntArray = new int[2];
    private final android.content.IntentFilter mConfigurationChangedFilter = new android.content.IntentFilter(android.content.Intent.ACTION_CONFIGURATION_CHANGED);
    private final android.content.BroadcastReceiver mConfigurationChangedReceiver = new android.content.BroadcastReceiver() { // from class: android.widget.ZoomButtonsController.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (android.widget.ZoomButtonsController.this.mIsVisible) {
                android.widget.ZoomButtonsController.this.mHandler.removeMessages(2);
                android.widget.ZoomButtonsController.this.mHandler.sendEmptyMessage(2);
            }
        }
    };
    private final android.os.Handler mHandler = new android.os.Handler() { // from class: android.widget.ZoomButtonsController.2
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 2:
                    android.widget.ZoomButtonsController.this.onPostConfigurationChanged();
                    break;
                case 3:
                    android.widget.ZoomButtonsController.this.setVisible(false);
                    break;
                case 4:
                    if (android.widget.ZoomButtonsController.this.mOwnerView.getWindowToken() == null) {
                        android.util.Log.e(android.widget.ZoomButtonsController.TAG, "Cannot make the zoom controller visible if the owner view is not attached to a window.");
                        break;
                    } else {
                        android.widget.ZoomButtonsController.this.setVisible(true);
                        break;
                    }
            }
        }
    };

    public interface OnZoomListener {
        void onVisibilityChanged(boolean z);

        void onZoom(boolean z);
    }

    public ZoomButtonsController(android.view.View view) {
        this.mContext = view.getContext();
        this.mWindowManager = (android.view.WindowManager) this.mContext.getSystemService(android.content.Context.WINDOW_SERVICE);
        this.mOwnerView = view;
        this.mTouchPaddingScaledSq = (int) (this.mContext.getResources().getDisplayMetrics().density * 20.0f);
        this.mTouchPaddingScaledSq *= this.mTouchPaddingScaledSq;
        this.mContainer = createContainer();
    }

    public void setZoomInEnabled(boolean z) {
        this.mControls.setIsZoomInEnabled(z);
    }

    public void setZoomOutEnabled(boolean z) {
        this.mControls.setIsZoomOutEnabled(z);
    }

    public void setZoomSpeed(long j) {
        this.mControls.setZoomSpeed(j);
    }

    private android.widget.FrameLayout createContainer() {
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams(-2, -2);
        layoutParams.gravity = 8388659;
        layoutParams.flags = 131608;
        layoutParams.height = -2;
        layoutParams.width = -1;
        layoutParams.type = 1000;
        layoutParams.format = -3;
        layoutParams.windowAnimations = com.android.internal.R.style.Animation_ZoomButtons;
        this.mContainerLayoutParams = layoutParams;
        android.widget.ZoomButtonsController.Container container = new android.widget.ZoomButtonsController.Container(this.mContext);
        container.setLayoutParams(layoutParams);
        container.setMeasureAllChildren(true);
        ((android.view.LayoutInflater) this.mContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(com.android.internal.R.layout.zoom_container, container);
        this.mControls = (android.widget.ZoomControls) container.findViewById(com.android.internal.R.id.zoomControls);
        this.mControls.setOnZoomInClickListener(new android.view.View.OnClickListener() { // from class: android.widget.ZoomButtonsController.3
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.ZoomButtonsController.this.dismissControlsDelayed(android.widget.ZoomButtonsController.ZOOM_CONTROLS_TIMEOUT);
                if (android.widget.ZoomButtonsController.this.mCallback != null) {
                    android.widget.ZoomButtonsController.this.mCallback.onZoom(true);
                }
            }
        });
        this.mControls.setOnZoomOutClickListener(new android.view.View.OnClickListener() { // from class: android.widget.ZoomButtonsController.4
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                android.widget.ZoomButtonsController.this.dismissControlsDelayed(android.widget.ZoomButtonsController.ZOOM_CONTROLS_TIMEOUT);
                if (android.widget.ZoomButtonsController.this.mCallback != null) {
                    android.widget.ZoomButtonsController.this.mCallback.onZoom(false);
                }
            }
        });
        return container;
    }

    public void setOnZoomListener(android.widget.ZoomButtonsController.OnZoomListener onZoomListener) {
        this.mCallback = onZoomListener;
    }

    public void setFocusable(boolean z) {
        int i = this.mContainerLayoutParams.flags;
        if (z) {
            this.mContainerLayoutParams.flags &= -9;
        } else {
            this.mContainerLayoutParams.flags |= 8;
        }
        if (this.mContainerLayoutParams.flags != i && this.mIsVisible) {
            this.mWindowManager.updateViewLayout(this.mContainer, this.mContainerLayoutParams);
        }
    }

    public boolean isAutoDismissed() {
        return this.mAutoDismissControls;
    }

    public void setAutoDismissed(boolean z) {
        if (this.mAutoDismissControls == z) {
            return;
        }
        this.mAutoDismissControls = z;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    public void setVisible(boolean z) {
        if (z) {
            if (this.mOwnerView.getWindowToken() == null) {
                if (!this.mHandler.hasMessages(4)) {
                    this.mHandler.sendEmptyMessage(4);
                    return;
                }
                return;
            }
            dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        }
        if (this.mIsVisible == z) {
            return;
        }
        this.mIsVisible = z;
        if (z) {
            if (this.mContainerLayoutParams.token == null) {
                this.mContainerLayoutParams.token = this.mOwnerView.getWindowToken();
            }
            this.mWindowManager.addView(this.mContainer, this.mContainerLayoutParams);
            if (this.mPostedVisibleInitializer == null) {
                this.mPostedVisibleInitializer = new java.lang.Runnable() { // from class: android.widget.ZoomButtonsController.5
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.ZoomButtonsController.this.refreshPositioningVariables();
                        if (android.widget.ZoomButtonsController.this.mCallback != null) {
                            android.widget.ZoomButtonsController.this.mCallback.onVisibilityChanged(true);
                        }
                    }
                };
            }
            this.mHandler.post(this.mPostedVisibleInitializer);
            this.mContext.registerReceiver(this.mConfigurationChangedReceiver, this.mConfigurationChangedFilter);
            this.mOwnerView.setOnTouchListener(this);
            this.mReleaseTouchListenerOnUp = false;
            return;
        }
        if (this.mTouchTargetView != null) {
            this.mReleaseTouchListenerOnUp = true;
        } else {
            this.mOwnerView.setOnTouchListener(null);
        }
        this.mContext.unregisterReceiver(this.mConfigurationChangedReceiver);
        this.mWindowManager.removeViewImmediate(this.mContainer);
        this.mHandler.removeCallbacks(this.mPostedVisibleInitializer);
        if (this.mCallback != null) {
            this.mCallback.onVisibilityChanged(false);
        }
    }

    public android.view.ViewGroup getContainer() {
        return this.mContainer;
    }

    public android.view.View getZoomControls() {
        return this.mControls;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissControlsDelayed(int i) {
        if (this.mAutoDismissControls) {
            this.mHandler.removeMessages(3);
            this.mHandler.sendEmptyMessageDelayed(3, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshPositioningVariables() {
        if (this.mOwnerView.getWindowToken() == null) {
            return;
        }
        int height = this.mOwnerView.getHeight();
        int width = this.mOwnerView.getWidth();
        int height2 = height - this.mContainer.getHeight();
        this.mOwnerView.getLocationOnScreen(this.mOwnerViewRawLocation);
        this.mContainerRawLocation[0] = this.mOwnerViewRawLocation[0];
        this.mContainerRawLocation[1] = this.mOwnerViewRawLocation[1] + height2;
        int[] iArr = this.mTempIntArray;
        this.mOwnerView.getLocationInWindow(iArr);
        this.mContainerLayoutParams.x = iArr[0];
        this.mContainerLayoutParams.width = width;
        this.mContainerLayoutParams.y = iArr[1] + height2;
        if (this.mIsVisible) {
            this.mWindowManager.updateViewLayout(this.mContainer, this.mContainerLayoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onContainerKey(android.view.KeyEvent keyEvent) {
        android.view.KeyEvent.DispatcherState keyDispatcherState;
        int keyCode = keyEvent.getKeyCode();
        if (isInterestingKey(keyCode)) {
            if (keyCode == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    if (this.mOwnerView != null && (keyDispatcherState = this.mOwnerView.getKeyDispatcherState()) != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    setVisible(false);
                    return true;
                }
            } else {
                dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
            }
            return false;
        }
        android.view.ViewRootImpl viewRootImpl = this.mOwnerView.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.dispatchInputEvent(keyEvent);
        }
        return true;
    }

    private boolean isInterestingKey(int i) {
        switch (i) {
            case 4:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 66:
                return true;
            default:
                return false;
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (motionEvent.getPointerCount() > 1) {
            return false;
        }
        if (this.mReleaseTouchListenerOnUp) {
            if (action == 1 || action == 3) {
                this.mOwnerView.setOnTouchListener(null);
                setTouchTargetView(null);
                this.mReleaseTouchListenerOnUp = false;
            }
            return true;
        }
        dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        android.view.View view2 = this.mTouchTargetView;
        switch (action) {
            case 0:
                view2 = findViewForTouch((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                setTouchTargetView(view2);
                break;
            case 1:
            case 3:
                setTouchTargetView(null);
                break;
        }
        if (view2 == null) {
            return false;
        }
        int i = this.mContainerRawLocation[0] + this.mTouchTargetWindowLocation[0];
        int i2 = this.mContainerRawLocation[1] + this.mTouchTargetWindowLocation[1];
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent);
        obtain.offsetLocation(this.mOwnerViewRawLocation[0] - i, this.mOwnerViewRawLocation[1] - i2);
        float x = obtain.getX();
        float y = obtain.getY();
        if (x < 0.0f && x > -20.0f) {
            obtain.offsetLocation(-x, 0.0f);
        }
        if (y < 0.0f && y > -20.0f) {
            obtain.offsetLocation(0.0f, -y);
        }
        boolean dispatchTouchEvent = view2.dispatchTouchEvent(obtain);
        obtain.recycle();
        return dispatchTouchEvent;
    }

    private void setTouchTargetView(android.view.View view) {
        this.mTouchTargetView = view;
        if (view != null) {
            view.getLocationInWindow(this.mTouchTargetWindowLocation);
        }
    }

    private android.view.View findViewForTouch(int i, int i2) {
        int min;
        int min2;
        int i3 = i - this.mContainerRawLocation[0];
        int i4 = i2 - this.mContainerRawLocation[1];
        android.graphics.Rect rect = this.mTempRect;
        android.view.View view = null;
        int i5 = Integer.MAX_VALUE;
        for (int childCount = this.mContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = this.mContainer.getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (rect.contains(i3, i4)) {
                    return childAt;
                }
                if (i3 >= rect.left && i3 <= rect.right) {
                    min = 0;
                } else {
                    min = java.lang.Math.min(java.lang.Math.abs(rect.left - i3), java.lang.Math.abs(i3 - rect.right));
                }
                if (i4 >= rect.top && i4 <= rect.bottom) {
                    min2 = 0;
                } else {
                    min2 = java.lang.Math.min(java.lang.Math.abs(rect.top - i4), java.lang.Math.abs(i4 - rect.bottom));
                }
                int i6 = (min * min) + (min2 * min2);
                if (i6 < this.mTouchPaddingScaledSq && i6 < i5) {
                    view = childAt;
                    i5 = i6;
                }
            }
        }
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPostConfigurationChanged() {
        dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        refreshPositioningVariables();
    }

    private class Container extends android.widget.FrameLayout {
        public Container(android.content.Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
            if (android.widget.ZoomButtonsController.this.onContainerKey(keyEvent)) {
                return true;
            }
            return super.dispatchKeyEvent(keyEvent);
        }
    }
}
