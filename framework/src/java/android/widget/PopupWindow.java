package android.widget;

/* loaded from: classes4.dex */
public class PopupWindow {
    private static final int[] ABOVE_ANCHOR_STATE_SET = {16842922};
    private static final int ANIMATION_STYLE_DEFAULT = -1;
    private static final int DEFAULT_ANCHORED_GRAVITY = 8388659;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    private boolean mAboveAnchor;
    private android.graphics.drawable.Drawable mAboveAnchorBackgroundDrawable;
    private boolean mAllowScrollingAnchorParent;
    private java.lang.ref.WeakReference<android.view.View> mAnchor;
    private java.lang.ref.WeakReference<android.view.View> mAnchorRoot;
    private int mAnchorXoff;
    private int mAnchorYoff;
    private int mAnchoredGravity;
    private int mAnimationStyle;
    private boolean mAttachedInDecor;
    private boolean mAttachedInDecorSet;
    private android.window.OnBackInvokedCallback mBackCallback;
    private android.graphics.drawable.Drawable mBackground;
    private android.view.View mBackgroundView;
    private android.graphics.drawable.Drawable mBelowAnchorBackgroundDrawable;
    private boolean mClipToScreen;
    private boolean mClippingEnabled;
    private android.view.View mContentView;
    private android.content.Context mContext;
    private android.widget.PopupWindow.PopupDecorView mDecorView;
    private float mElevation;
    private android.transition.Transition mEnterTransition;
    private android.graphics.Rect mEpicenterBounds;
    private android.transition.Transition mExitTransition;
    private boolean mFocusable;
    private int mGravity;
    private int mHeight;
    private int mHeightMode;
    private boolean mIgnoreCheekPress;
    private int mInputMethodMode;
    private boolean mIsAnchorRootAttached;
    private boolean mIsDropdown;
    private boolean mIsShowing;
    private boolean mIsTransitioningToDismiss;
    private int mLastHeight;
    private int mLastWidth;
    private boolean mLayoutInScreen;
    private boolean mLayoutInsetDecor;
    private boolean mNotTouchModal;
    private final android.view.View.OnAttachStateChangeListener mOnAnchorDetachedListener;
    private final android.view.View.OnAttachStateChangeListener mOnAnchorRootDetachedListener;
    private android.widget.PopupWindow.OnDismissListener mOnDismissListener;
    private final android.view.View.OnLayoutChangeListener mOnLayoutChangeListener;
    private final android.view.ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private boolean mOutsideTouchable;
    private boolean mOverlapAnchor;
    private java.lang.ref.WeakReference<android.view.View> mParentRootView;
    private boolean mPopupViewInitialLayoutDirectionInherited;
    private int mSoftInputMode;
    private int mSplitTouchEnabled;
    private final android.graphics.Rect mTempRect;
    private final int[] mTmpAppLocation;
    private final int[] mTmpDrawingLocation;
    private final int[] mTmpScreenLocation;
    private android.view.View.OnTouchListener mTouchInterceptor;
    private boolean mTouchable;
    private int mWidth;
    private int mWidthMode;
    private int mWindowLayoutType;
    private android.view.WindowManager mWindowManager;

    public interface OnDismissListener {
        void onDismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        alignToAnchor();
    }

    public PopupWindow(android.content.Context context) {
        this(context, (android.util.AttributeSet) null);
    }

    public PopupWindow(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842870);
    }

    public PopupWindow(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PopupWindow(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        android.transition.Transition mo4806clone;
        this.mTmpDrawingLocation = new int[2];
        this.mTmpScreenLocation = new int[2];
        this.mTmpAppLocation = new int[2];
        this.mTempRect = new android.graphics.Rect();
        this.mInputMethodMode = 0;
        this.mSoftInputMode = 1;
        this.mTouchable = true;
        this.mOutsideTouchable = false;
        this.mClippingEnabled = true;
        this.mSplitTouchEnabled = -1;
        this.mAllowScrollingAnchorParent = true;
        this.mLayoutInsetDecor = false;
        this.mAttachedInDecor = true;
        this.mAttachedInDecorSet = false;
        this.mWidth = -2;
        this.mHeight = -2;
        this.mWindowLayoutType = 1000;
        this.mIgnoreCheekPress = false;
        this.mAnimationStyle = -1;
        this.mGravity = 0;
        this.mOnAnchorDetachedListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(android.view.View view) {
                android.widget.PopupWindow.this.alignToAnchor();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(android.view.View view) {
            }
        };
        this.mOnAnchorRootDetachedListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.2
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(android.view.View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(android.view.View view) {
                android.widget.PopupWindow.this.mIsAnchorRootAttached = false;
            }
        };
        this.mOnScrollChangedListener = new android.view.ViewTreeObserver.OnScrollChangedListener() { // from class: android.widget.PopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                android.widget.PopupWindow.this.alignToAnchor();
            }
        };
        this.mOnLayoutChangeListener = new android.view.View.OnLayoutChangeListener() { // from class: android.widget.PopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(android.view.View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                android.widget.PopupWindow.this.lambda$new$0(view, i3, i4, i5, i6, i7, i8, i9, i10);
            }
        };
        this.mContext = context;
        this.mWindowManager = (android.view.WindowManager) context.getSystemService(android.content.Context.WINDOW_SERVICE);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.PopupWindow, i, i2);
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(0);
        this.mElevation = obtainStyledAttributes.getDimension(3, 0.0f);
        this.mOverlapAnchor = obtainStyledAttributes.getBoolean(2, false);
        if (obtainStyledAttributes.hasValueOrEmpty(1)) {
            int resourceId = obtainStyledAttributes.getResourceId(1, 0);
            if (resourceId == 16974604) {
                this.mAnimationStyle = -1;
            } else {
                this.mAnimationStyle = resourceId;
            }
        } else {
            this.mAnimationStyle = -1;
        }
        android.transition.Transition transition = getTransition(obtainStyledAttributes.getResourceId(4, 0));
        if (obtainStyledAttributes.hasValueOrEmpty(5)) {
            mo4806clone = getTransition(obtainStyledAttributes.getResourceId(5, 0));
        } else {
            mo4806clone = transition == null ? null : transition.mo4806clone();
        }
        obtainStyledAttributes.recycle();
        setEnterTransition(transition);
        setExitTransition(mo4806clone);
        setBackgroundDrawable(drawable);
    }

    public PopupWindow() {
        this((android.view.View) null, 0, 0);
    }

    public PopupWindow(android.view.View view) {
        this(view, 0, 0);
    }

    public PopupWindow(int i, int i2) {
        this((android.view.View) null, i, i2);
    }

    public PopupWindow(android.view.View view, int i, int i2) {
        this(view, i, i2, false);
    }

    public PopupWindow(android.view.View view, int i, int i2, boolean z) {
        this.mTmpDrawingLocation = new int[2];
        this.mTmpScreenLocation = new int[2];
        this.mTmpAppLocation = new int[2];
        this.mTempRect = new android.graphics.Rect();
        this.mInputMethodMode = 0;
        this.mSoftInputMode = 1;
        this.mTouchable = true;
        this.mOutsideTouchable = false;
        this.mClippingEnabled = true;
        this.mSplitTouchEnabled = -1;
        this.mAllowScrollingAnchorParent = true;
        this.mLayoutInsetDecor = false;
        this.mAttachedInDecor = true;
        this.mAttachedInDecorSet = false;
        this.mWidth = -2;
        this.mHeight = -2;
        this.mWindowLayoutType = 1000;
        this.mIgnoreCheekPress = false;
        this.mAnimationStyle = -1;
        this.mGravity = 0;
        this.mOnAnchorDetachedListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(android.view.View view2) {
                android.widget.PopupWindow.this.alignToAnchor();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(android.view.View view2) {
            }
        };
        this.mOnAnchorRootDetachedListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.2
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(android.view.View view2) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(android.view.View view2) {
                android.widget.PopupWindow.this.mIsAnchorRootAttached = false;
            }
        };
        this.mOnScrollChangedListener = new android.view.ViewTreeObserver.OnScrollChangedListener() { // from class: android.widget.PopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                android.widget.PopupWindow.this.alignToAnchor();
            }
        };
        this.mOnLayoutChangeListener = new android.view.View.OnLayoutChangeListener() { // from class: android.widget.PopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(android.view.View view2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                android.widget.PopupWindow.this.lambda$new$0(view2, i3, i4, i5, i6, i7, i8, i9, i10);
            }
        };
        if (view != null) {
            this.mContext = view.getContext();
            this.mWindowManager = (android.view.WindowManager) this.mContext.getSystemService(android.content.Context.WINDOW_SERVICE);
        }
        setContentView(view);
        setWidth(i);
        setHeight(i2);
        setFocusable(z);
    }

    public void setEnterTransition(android.transition.Transition transition) {
        this.mEnterTransition = transition;
    }

    public android.transition.Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    public void setExitTransition(android.transition.Transition transition) {
        this.mExitTransition = transition;
    }

    public android.transition.Transition getExitTransition() {
        return this.mExitTransition;
    }

    public android.graphics.Rect getEpicenterBounds() {
        if (this.mEpicenterBounds != null) {
            return new android.graphics.Rect(this.mEpicenterBounds);
        }
        return null;
    }

    public void setEpicenterBounds(android.graphics.Rect rect) {
        this.mEpicenterBounds = rect != null ? new android.graphics.Rect(rect) : null;
    }

    private android.transition.Transition getTransition(int i) {
        android.transition.Transition inflateTransition;
        if (i != 0 && i != 17760256 && (inflateTransition = android.transition.TransitionInflater.from(this.mContext).inflateTransition(i)) != null) {
            if (!((inflateTransition instanceof android.transition.TransitionSet) && ((android.transition.TransitionSet) inflateTransition).getTransitionCount() == 0)) {
                return inflateTransition;
            }
            return null;
        }
        return null;
    }

    public android.graphics.drawable.Drawable getBackground() {
        return this.mBackground;
    }

    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        this.mBackground = drawable;
        if (this.mBackground instanceof android.graphics.drawable.StateListDrawable) {
            android.graphics.drawable.StateListDrawable stateListDrawable = (android.graphics.drawable.StateListDrawable) this.mBackground;
            int findStateDrawableIndex = stateListDrawable.findStateDrawableIndex(ABOVE_ANCHOR_STATE_SET);
            int stateCount = stateListDrawable.getStateCount();
            int i = 0;
            while (true) {
                if (i >= stateCount) {
                    i = -1;
                    break;
                } else if (i != findStateDrawableIndex) {
                    break;
                } else {
                    i++;
                }
            }
            if (findStateDrawableIndex != -1 && i != -1) {
                this.mAboveAnchorBackgroundDrawable = stateListDrawable.getStateDrawable(findStateDrawableIndex);
                this.mBelowAnchorBackgroundDrawable = stateListDrawable.getStateDrawable(i);
            } else {
                this.mBelowAnchorBackgroundDrawable = null;
                this.mAboveAnchorBackgroundDrawable = null;
            }
        }
    }

    public float getElevation() {
        return this.mElevation;
    }

    public void setElevation(float f) {
        this.mElevation = f;
    }

    public int getAnimationStyle() {
        return this.mAnimationStyle;
    }

    public void setIgnoreCheekPress() {
        this.mIgnoreCheekPress = true;
    }

    public void setAnimationStyle(int i) {
        this.mAnimationStyle = i;
    }

    public android.view.View getContentView() {
        return this.mContentView;
    }

    public void setContentView(android.view.View view) {
        if (isShowing()) {
            return;
        }
        this.mContentView = view;
        if (this.mContext == null && this.mContentView != null) {
            this.mContext = this.mContentView.getContext();
        }
        if (this.mWindowManager == null && this.mContentView != null) {
            this.mWindowManager = (android.view.WindowManager) this.mContext.getSystemService(android.content.Context.WINDOW_SERVICE);
        }
        if (this.mContext != null && !this.mAttachedInDecorSet) {
            setAttachedInDecor(this.mContext.getApplicationInfo().targetSdkVersion >= 22);
        }
    }

    public void setTouchInterceptor(android.view.View.OnTouchListener onTouchListener) {
        this.mTouchInterceptor = onTouchListener;
    }

    public boolean isFocusable() {
        return this.mFocusable;
    }

    public void setFocusable(boolean z) {
        this.mFocusable = z;
    }

    public int getInputMethodMode() {
        return this.mInputMethodMode;
    }

    public void setInputMethodMode(int i) {
        this.mInputMethodMode = i;
    }

    public void setSoftInputMode(int i) {
        this.mSoftInputMode = i;
    }

    public int getSoftInputMode() {
        return this.mSoftInputMode;
    }

    public boolean isTouchable() {
        return this.mTouchable;
    }

    public void setTouchable(boolean z) {
        this.mTouchable = z;
    }

    public boolean isOutsideTouchable() {
        return this.mOutsideTouchable;
    }

    public void setOutsideTouchable(boolean z) {
        this.mOutsideTouchable = z;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public void setClippingEnabled(boolean z) {
        this.mClippingEnabled = z;
    }

    @java.lang.Deprecated
    public boolean isClipToScreenEnabled() {
        return this.mClipToScreen;
    }

    @java.lang.Deprecated
    public void setClipToScreenEnabled(boolean z) {
        this.mClipToScreen = z;
    }

    public boolean isClippedToScreen() {
        return this.mClipToScreen;
    }

    public void setIsClippedToScreen(boolean z) {
        this.mClipToScreen = z;
    }

    void setAllowScrollingAnchorParent(boolean z) {
        this.mAllowScrollingAnchorParent = z;
    }

    protected final boolean getAllowScrollingAnchorParent() {
        return this.mAllowScrollingAnchorParent;
    }

    public boolean isSplitTouchEnabled() {
        return (this.mSplitTouchEnabled >= 0 || this.mContext == null) ? this.mSplitTouchEnabled == 1 : this.mContext.getApplicationInfo().targetSdkVersion >= 11;
    }

    public void setSplitTouchEnabled(boolean z) {
        this.mSplitTouchEnabled = z ? 1 : 0;
    }

    @java.lang.Deprecated
    public boolean isLayoutInScreenEnabled() {
        return this.mLayoutInScreen;
    }

    @java.lang.Deprecated
    public void setLayoutInScreenEnabled(boolean z) {
        this.mLayoutInScreen = z;
    }

    public boolean isLaidOutInScreen() {
        return this.mLayoutInScreen;
    }

    public void setIsLaidOutInScreen(boolean z) {
        this.mLayoutInScreen = z;
    }

    public boolean isAttachedInDecor() {
        return this.mAttachedInDecor;
    }

    public void setAttachedInDecor(boolean z) {
        this.mAttachedInDecor = z;
        this.mAttachedInDecorSet = true;
    }

    public void setLayoutInsetDecor(boolean z) {
        this.mLayoutInsetDecor = z;
    }

    protected final boolean isLayoutInsetDecor() {
        return this.mLayoutInsetDecor;
    }

    public void setWindowLayoutType(int i) {
        this.mWindowLayoutType = i;
    }

    public int getWindowLayoutType() {
        return this.mWindowLayoutType;
    }

    public boolean isTouchModal() {
        return !this.mNotTouchModal;
    }

    public void setTouchModal(boolean z) {
        this.mNotTouchModal = !z;
    }

    @java.lang.Deprecated
    public void setWindowLayoutMode(int i, int i2) {
        this.mWidthMode = i;
        this.mHeightMode = i2;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public void setOverlapAnchor(boolean z) {
        this.mOverlapAnchor = z;
    }

    public boolean getOverlapAnchor() {
        return this.mOverlapAnchor;
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    protected final void setShowing(boolean z) {
        this.mIsShowing = z;
    }

    protected final void setDropDown(boolean z) {
        this.mIsDropdown = z;
    }

    protected final void setTransitioningToDismiss(boolean z) {
        this.mIsTransitioningToDismiss = z;
    }

    protected final boolean isTransitioningToDismiss() {
        return this.mIsTransitioningToDismiss;
    }

    public void showAtLocation(android.view.View view, int i, int i2, int i3) {
        this.mParentRootView = new java.lang.ref.WeakReference<>(view.getRootView());
        showAtLocation(view.getWindowToken(), i, i2, i3);
    }

    public void showAtLocation(android.os.IBinder iBinder, int i, int i2, int i3) {
        if (isShowing() || this.mContentView == null) {
            return;
        }
        android.transition.TransitionManager.endTransitions(this.mDecorView);
        detachFromAnchor();
        this.mIsShowing = true;
        this.mIsDropdown = false;
        this.mGravity = i;
        android.view.WindowManager.LayoutParams createPopupLayoutParams = createPopupLayoutParams(iBinder);
        preparePopup(createPopupLayoutParams);
        createPopupLayoutParams.x = i2;
        createPopupLayoutParams.y = i3;
        invokePopup(createPopupLayoutParams);
    }

    public void showAsDropDown(android.view.View view) {
        showAsDropDown(view, 0, 0);
    }

    public void showAsDropDown(android.view.View view, int i, int i2) {
        showAsDropDown(view, i, i2, DEFAULT_ANCHORED_GRAVITY);
    }

    public void showAsDropDown(android.view.View view, int i, int i2, int i3) {
        if (isShowing() || !hasContentView()) {
            return;
        }
        android.transition.TransitionManager.endTransitions(this.mDecorView);
        attachToAnchor(view, i, i2, i3);
        this.mIsShowing = true;
        this.mIsDropdown = true;
        android.view.WindowManager.LayoutParams createPopupLayoutParams = createPopupLayoutParams(view.getApplicationWindowToken());
        preparePopup(createPopupLayoutParams);
        updateAboveAnchor(findDropDownPosition(view, createPopupLayoutParams, i, i2, createPopupLayoutParams.width, createPopupLayoutParams.height, i3, this.mAllowScrollingAnchorParent));
        createPopupLayoutParams.accessibilityIdOfAnchor = view != null ? view.getAccessibilityViewId() : -1L;
        invokePopup(createPopupLayoutParams);
    }

    protected final void updateAboveAnchor(boolean z) {
        if (z != this.mAboveAnchor) {
            this.mAboveAnchor = z;
            if (this.mBackground != null && this.mBackgroundView != null) {
                if (this.mAboveAnchorBackgroundDrawable != null) {
                    if (this.mAboveAnchor) {
                        this.mBackgroundView.setBackground(this.mAboveAnchorBackgroundDrawable);
                        return;
                    } else {
                        this.mBackgroundView.setBackground(this.mBelowAnchorBackgroundDrawable);
                        return;
                    }
                }
                this.mBackgroundView.refreshDrawableState();
            }
        }
    }

    public boolean isAboveAnchor() {
        return this.mAboveAnchor;
    }

    private void preparePopup(android.view.WindowManager.LayoutParams layoutParams) {
        if (this.mContentView == null || this.mContext == null || this.mWindowManager == null) {
            throw new java.lang.IllegalStateException("You must specify a valid content view by calling setContentView() before attempting to show the popup.");
        }
        if (layoutParams.accessibilityTitle == null) {
            layoutParams.accessibilityTitle = this.mContext.getString(com.android.internal.R.string.popup_window_default_title);
        }
        if (this.mDecorView != null) {
            this.mDecorView.cancelTransitions();
        }
        if (this.mBackground != null) {
            this.mBackgroundView = createBackgroundView(this.mContentView);
            this.mBackgroundView.setBackground(this.mBackground);
        } else {
            this.mBackgroundView = this.mContentView;
        }
        this.mDecorView = createDecorView(this.mBackgroundView);
        this.mDecorView.setIsRootNamespace(true);
        this.mBackgroundView.setElevation(this.mElevation);
        layoutParams.setSurfaceInsets(this.mBackgroundView, true, true);
        this.mPopupViewInitialLayoutDirectionInherited = this.mContentView.getRawLayoutDirection() == 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000c, code lost:
    
        if (r0.height == (-2)) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.widget.PopupWindow.PopupBackgroundView createBackgroundView(android.view.View view) {
        int i;
        android.view.ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
        if (layoutParams != null) {
            i = -2;
        }
        i = -1;
        android.widget.PopupWindow.PopupBackgroundView popupBackgroundView = new android.widget.PopupWindow.PopupBackgroundView(this.mContext);
        popupBackgroundView.addView(view, new android.widget.FrameLayout.LayoutParams(-1, i));
        return popupBackgroundView;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000c, code lost:
    
        if (r0.height == (-2)) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.widget.PopupWindow.PopupDecorView createDecorView(android.view.View view) {
        int i;
        android.view.ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
        if (layoutParams != null) {
            i = -2;
        }
        i = -1;
        android.widget.PopupWindow.PopupDecorView popupDecorView = new android.widget.PopupWindow.PopupDecorView(this.mContext);
        popupDecorView.addView(view, -1, i);
        popupDecorView.setClipChildren(false);
        popupDecorView.setClipToPadding(false);
        return popupDecorView;
    }

    private void invokePopup(android.view.WindowManager.LayoutParams layoutParams) {
        if (this.mContext != null) {
            layoutParams.packageName = this.mContext.getPackageName();
        }
        android.widget.PopupWindow.PopupDecorView popupDecorView = this.mDecorView;
        popupDecorView.setFitsSystemWindows(this.mLayoutInsetDecor);
        setLayoutDirectionFromAnchor();
        this.mWindowManager.addView(popupDecorView, layoutParams);
        if (this.mEnterTransition != null) {
            popupDecorView.requestEnterTransition(this.mEnterTransition);
        }
    }

    private void setLayoutDirectionFromAnchor() {
        android.view.View view;
        if (this.mAnchor != null && (view = this.mAnchor.get()) != null && this.mPopupViewInitialLayoutDirectionInherited) {
            this.mDecorView.setLayoutDirection(view.getLayoutDirection());
        }
    }

    private int computeGravity() {
        int i = this.mGravity == 0 ? DEFAULT_ANCHORED_GRAVITY : this.mGravity;
        if (!this.mIsDropdown) {
            return i;
        }
        if (this.mClipToScreen || this.mClippingEnabled) {
            return i | 268435456;
        }
        return i;
    }

    protected final android.view.WindowManager.LayoutParams createPopupLayoutParams(android.os.IBinder iBinder) {
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams();
        layoutParams.gravity = computeGravity();
        layoutParams.flags = computeFlags(layoutParams.flags);
        layoutParams.type = this.mWindowLayoutType;
        layoutParams.token = iBinder;
        layoutParams.softInputMode = this.mSoftInputMode;
        layoutParams.windowAnimations = computeAnimationResource();
        if (this.mBackground != null) {
            layoutParams.format = this.mBackground.getOpacity();
        } else {
            layoutParams.format = -3;
        }
        if (this.mHeightMode < 0) {
            int i = this.mHeightMode;
            this.mLastHeight = i;
            layoutParams.height = i;
        } else {
            int i2 = this.mHeight;
            this.mLastHeight = i2;
            layoutParams.height = i2;
        }
        if (this.mWidthMode < 0) {
            int i3 = this.mWidthMode;
            this.mLastWidth = i3;
            layoutParams.width = i3;
        } else {
            int i4 = this.mWidth;
            this.mLastWidth = i4;
            layoutParams.width = i4;
        }
        layoutParams.privateFlags = 16384;
        layoutParams.setTitle("PopupWindow:" + java.lang.Integer.toHexString(hashCode()));
        return layoutParams;
    }

    private int computeFlags(int i) {
        int i2 = i & (-8815129);
        if (this.mIgnoreCheekPress) {
            i2 |= 32768;
        }
        if (!this.mFocusable) {
            i2 |= 8;
            if (this.mInputMethodMode == 1) {
                i2 |= 131072;
            }
        } else if (this.mInputMethodMode == 2) {
            i2 |= 131072;
        }
        if (!this.mTouchable) {
            i2 |= 16;
        }
        if (this.mOutsideTouchable) {
            i2 |= 262144;
        }
        if (!this.mClippingEnabled || this.mClipToScreen) {
            i2 |= 512;
        }
        if (isSplitTouchEnabled()) {
            i2 |= 8388608;
        }
        if (this.mLayoutInScreen) {
            i2 |= 256;
        }
        if (this.mLayoutInsetDecor) {
            i2 |= 65536;
        }
        if (this.mNotTouchModal) {
            i2 |= 32;
        }
        if (this.mAttachedInDecor) {
            return i2 | 1073741824;
        }
        return i2;
    }

    private int computeAnimationResource() {
        if (this.mAnimationStyle == -1) {
            if (this.mIsDropdown) {
                if (this.mAboveAnchor) {
                    return com.android.internal.R.style.Animation_DropDownUp;
                }
                return com.android.internal.R.style.Animation_DropDownDown;
            }
            return 0;
        }
        return this.mAnimationStyle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3 */
    protected boolean findDropDownPosition(android.view.View view, android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        int i6;
        int i7;
        int i8;
        char c;
        boolean z2;
        int height = view.getHeight();
        int width = view.getWidth();
        if (!this.mOverlapAnchor) {
            i6 = i2;
        } else {
            i6 = i2 - height;
        }
        int[] iArr = this.mTmpAppLocation;
        android.view.View appRootView = getAppRootView(view);
        appRootView.getLocationOnScreen(iArr);
        int[] iArr2 = this.mTmpScreenLocation;
        view.getLocationOnScreen(iArr2);
        int[] iArr3 = this.mTmpDrawingLocation;
        iArr3[0] = iArr2[0] - iArr[0];
        iArr3[1] = iArr2[1] - iArr[1];
        layoutParams.x = iArr3[0] + i;
        layoutParams.y = iArr3[1] + height + i6;
        android.graphics.Rect rect = new android.graphics.Rect();
        appRootView.getWindowVisibleDisplayFrame(rect);
        if (i3 != -1) {
            i7 = i3;
        } else {
            i7 = rect.right - rect.left;
        }
        if (i4 != -1) {
            i8 = i4;
        } else {
            i8 = rect.bottom - rect.top;
        }
        layoutParams.gravity = computeGravity();
        layoutParams.width = i7;
        layoutParams.height = i8;
        int absoluteGravity = android.view.Gravity.getAbsoluteGravity(i5, view.getLayoutDirection()) & 7;
        if (absoluteGravity == 5) {
            layoutParams.x -= i7 - width;
        }
        int i9 = i8;
        int i10 = i7;
        boolean tryFitVertical = tryFitVertical(layoutParams, i6, i8, height, iArr3[1], iArr2[1], rect.top, rect.bottom, false);
        boolean tryFitHorizontal = tryFitHorizontal(layoutParams, i, i10, width, iArr3[0], iArr2[0], rect.left, rect.right, false);
        if (tryFitVertical && tryFitHorizontal) {
            z2 = 1;
        } else {
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            android.graphics.Rect rect2 = new android.graphics.Rect(scrollX, scrollY, scrollX + i10 + i, scrollY + i9 + height + i6);
            if (z) {
                c = 1;
                if (view.requestRectangleOnScreen(rect2, true)) {
                    view.getLocationOnScreen(iArr2);
                    iArr3[0] = iArr2[0] - iArr[0];
                    iArr3[1] = iArr2[1] - iArr[1];
                    layoutParams.x = iArr3[0] + i;
                    layoutParams.y = iArr3[1] + height + i6;
                    if (absoluteGravity == 5) {
                        layoutParams.x -= i10 - width;
                    }
                }
            } else {
                c = 1;
            }
            z2 = c;
            tryFitVertical(layoutParams, i6, i9, height, iArr3[c], iArr2[c], rect.top, rect.bottom, this.mClipToScreen);
            tryFitHorizontal(layoutParams, i, i10, width, iArr3[0], iArr2[0], rect.left, rect.right, this.mClipToScreen);
        }
        if (layoutParams.y < iArr3[z2]) {
            return z2;
        }
        return false;
    }

    private boolean tryFitVertical(android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        int i8;
        int i9 = layoutParams.y + (i5 - i4);
        int i10 = i7 - i9;
        if (i9 >= i6 && i2 <= i10) {
            return true;
        }
        if (i2 <= (i9 - i3) - i6) {
            if (!this.mOverlapAnchor) {
                i8 = i;
            } else {
                i8 = i + i3;
            }
            layoutParams.y = (i4 - i2) + i8;
            return true;
        }
        if (positionInDisplayVertical(layoutParams, i2, i4, i5, i6, i7, z)) {
            return true;
        }
        return false;
    }

    private boolean positionInDisplayVertical(android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        boolean z2;
        int i6 = i3 - i2;
        layoutParams.y += i6;
        layoutParams.height = i;
        int i7 = layoutParams.y + i;
        if (i7 > i5) {
            layoutParams.y -= i7 - i5;
        }
        if (layoutParams.y < i4) {
            layoutParams.y = i4;
            int i8 = i5 - i4;
            if (z && i > i8) {
                layoutParams.height = i8;
            } else {
                z2 = false;
                layoutParams.y -= i6;
                return z2;
            }
        }
        z2 = true;
        layoutParams.y -= i6;
        return z2;
    }

    private boolean tryFitHorizontal(android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        int i8 = layoutParams.x + (i5 - i4);
        int i9 = i7 - i8;
        if (i8 >= i6 && i2 <= i9) {
            return true;
        }
        if (positionInDisplayHorizontal(layoutParams, i2, i4, i5, i6, i7, z)) {
            return true;
        }
        return false;
    }

    private boolean positionInDisplayHorizontal(android.view.WindowManager.LayoutParams layoutParams, int i, int i2, int i3, int i4, int i5, boolean z) {
        boolean z2;
        int i6 = i3 - i2;
        layoutParams.x += i6;
        int i7 = layoutParams.x + i;
        if (i7 > i5) {
            layoutParams.x -= i7 - i5;
        }
        if (layoutParams.x < i4) {
            layoutParams.x = i4;
            int i8 = i5 - i4;
            if (z && i > i8) {
                layoutParams.width = i8;
            } else {
                z2 = false;
                layoutParams.x -= i6;
                return z2;
            }
        }
        z2 = true;
        layoutParams.x -= i6;
        return z2;
    }

    public int getMaxAvailableHeight(android.view.View view) {
        return getMaxAvailableHeight(view, 0);
    }

    public int getMaxAvailableHeight(android.view.View view, int i) {
        return getMaxAvailableHeight(view, i, false);
    }

    public int getMaxAvailableHeight(android.view.View view, int i, boolean z) {
        int height;
        android.graphics.Rect rect = new android.graphics.Rect();
        getAppRootView(view).getWindowVisibleDisplayFrame(rect);
        if (z) {
            android.graphics.Rect rect2 = new android.graphics.Rect();
            view.getWindowDisplayFrame(rect2);
            rect2.top = rect.top;
            rect2.right = rect.right;
            rect2.left = rect.left;
            rect = rect2;
        }
        int[] iArr = this.mTmpDrawingLocation;
        view.getLocationOnScreen(iArr);
        int i2 = rect.bottom;
        if (this.mOverlapAnchor) {
            height = (i2 - iArr[1]) - i;
        } else {
            height = (i2 - (iArr[1] + view.getHeight())) - i;
        }
        int max = java.lang.Math.max(height, (iArr[1] - rect.top) + i);
        if (this.mBackground != null) {
            this.mBackground.getPadding(this.mTempRect);
            return max - (this.mTempRect.top + this.mTempRect.bottom);
        }
        return max;
    }

    public void dismiss() {
        final android.view.ViewGroup viewGroup;
        if (!isShowing() || isTransitioningToDismiss()) {
            return;
        }
        final android.widget.PopupWindow.PopupDecorView popupDecorView = this.mDecorView;
        final android.view.View view = this.mContentView;
        unregisterBackCallback(popupDecorView.findOnBackInvokedDispatcher());
        android.view.ViewParent parent = view.getParent();
        if (parent instanceof android.view.ViewGroup) {
            viewGroup = (android.view.ViewGroup) parent;
        } else {
            viewGroup = null;
        }
        popupDecorView.cancelTransitions();
        this.mIsShowing = false;
        this.mIsTransitioningToDismiss = true;
        android.transition.Transition transition = this.mExitTransition;
        if (transition != null && popupDecorView.isLaidOut() && (this.mIsAnchorRootAttached || this.mAnchorRoot == null)) {
            android.view.WindowManager.LayoutParams layoutParams = (android.view.WindowManager.LayoutParams) popupDecorView.getLayoutParams();
            layoutParams.flags |= 16;
            layoutParams.flags |= 8;
            layoutParams.flags &= -131073;
            this.mWindowManager.updateViewLayout(popupDecorView, layoutParams);
            popupDecorView.startExitTransition(transition, this.mAnchorRoot != null ? this.mAnchorRoot.get() : null, getTransitionEpicenter(), new android.transition.TransitionListenerAdapter() { // from class: android.widget.PopupWindow.3
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(android.transition.Transition transition2) {
                    android.widget.PopupWindow.this.dismissImmediate(popupDecorView, viewGroup, view);
                }
            });
        } else {
            dismissImmediate(popupDecorView, viewGroup, view);
        }
        detachFromAnchor();
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterBackCallback(android.window.OnBackInvokedDispatcher onBackInvokedDispatcher) {
        android.window.OnBackInvokedCallback onBackInvokedCallback = this.mBackCallback;
        this.mBackCallback = null;
        if (onBackInvokedDispatcher != null && onBackInvokedCallback != null) {
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
        }
    }

    protected final android.graphics.Rect getTransitionEpicenter() {
        android.view.View view = this.mAnchor != null ? this.mAnchor.get() : null;
        android.widget.PopupWindow.PopupDecorView popupDecorView = this.mDecorView;
        if (view == null || popupDecorView == null) {
            return null;
        }
        int[] locationOnScreen = view.getLocationOnScreen();
        int[] locationOnScreen2 = this.mDecorView.getLocationOnScreen();
        android.graphics.Rect rect = new android.graphics.Rect(0, 0, view.getWidth(), view.getHeight());
        rect.offset(locationOnScreen[0] - locationOnScreen2[0], locationOnScreen[1] - locationOnScreen2[1]);
        if (this.mEpicenterBounds != null) {
            int i = rect.left;
            int i2 = rect.top;
            rect.set(this.mEpicenterBounds);
            rect.offset(i, i2);
        }
        return rect;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissImmediate(android.view.View view, android.view.ViewGroup viewGroup, android.view.View view2) {
        if (view.getParent() != null) {
            this.mWindowManager.removeViewImmediate(view);
        }
        if (viewGroup != null) {
            viewGroup.removeView(view2);
        }
        this.mDecorView = null;
        this.mBackgroundView = null;
        this.mIsTransitioningToDismiss = false;
    }

    public void setOnDismissListener(android.widget.PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    protected final android.widget.PopupWindow.OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    public void update() {
        boolean z;
        if (!isShowing() || !hasContentView()) {
            return;
        }
        android.view.WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
        int computeAnimationResource = computeAnimationResource();
        boolean z2 = true;
        if (computeAnimationResource == decorViewLayoutParams.windowAnimations) {
            z = false;
        } else {
            decorViewLayoutParams.windowAnimations = computeAnimationResource;
            z = true;
        }
        int computeFlags = computeFlags(decorViewLayoutParams.flags);
        if (computeFlags != decorViewLayoutParams.flags) {
            decorViewLayoutParams.flags = computeFlags;
            z = true;
        }
        int computeGravity = computeGravity();
        if (computeGravity == decorViewLayoutParams.gravity) {
            z2 = z;
        } else {
            decorViewLayoutParams.gravity = computeGravity;
        }
        if (z2) {
            update(this.mAnchor != null ? this.mAnchor.get() : null, decorViewLayoutParams);
        }
    }

    protected void update(android.view.View view, android.view.WindowManager.LayoutParams layoutParams) {
        setLayoutDirectionFromAnchor();
        this.mWindowManager.updateViewLayout(this.mDecorView, layoutParams);
    }

    public void update(int i, int i2) {
        android.view.WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
        update(decorViewLayoutParams.x, decorViewLayoutParams.y, i, i2, false);
    }

    public void update(int i, int i2, int i3, int i4) {
        update(i, i2, i3, i4, false);
    }

    public void update(int i, int i2, int i3, int i4, boolean z) {
        android.view.View view;
        if (i3 >= 0) {
            this.mLastWidth = i3;
            setWidth(i3);
        }
        if (i4 >= 0) {
            this.mLastHeight = i4;
            setHeight(i4);
        }
        if (!isShowing() || !hasContentView()) {
            return;
        }
        android.view.WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
        int i5 = this.mWidthMode < 0 ? this.mWidthMode : this.mLastWidth;
        int i6 = -1;
        boolean z2 = true;
        if (i3 != -1 && decorViewLayoutParams.width != i5) {
            this.mLastWidth = i5;
            decorViewLayoutParams.width = i5;
            z = true;
        }
        int i7 = this.mHeightMode < 0 ? this.mHeightMode : this.mLastHeight;
        if (i4 != -1 && decorViewLayoutParams.height != i7) {
            this.mLastHeight = i7;
            decorViewLayoutParams.height = i7;
            z = true;
        }
        if (decorViewLayoutParams.x != i) {
            decorViewLayoutParams.x = i;
            z = true;
        }
        if (decorViewLayoutParams.y != i2) {
            decorViewLayoutParams.y = i2;
            z = true;
        }
        int computeAnimationResource = computeAnimationResource();
        if (computeAnimationResource != decorViewLayoutParams.windowAnimations) {
            decorViewLayoutParams.windowAnimations = computeAnimationResource;
            z = true;
        }
        int computeFlags = computeFlags(decorViewLayoutParams.flags);
        if (computeFlags != decorViewLayoutParams.flags) {
            decorViewLayoutParams.flags = computeFlags;
            z = true;
        }
        int computeGravity = computeGravity();
        if (computeGravity != decorViewLayoutParams.gravity) {
            decorViewLayoutParams.gravity = computeGravity;
            z = true;
        }
        if (this.mAnchor != null && this.mAnchor.get() != null) {
            view = this.mAnchor.get();
            i6 = view.getAccessibilityViewId();
        } else {
            view = null;
        }
        long j = i6;
        if (j == decorViewLayoutParams.accessibilityIdOfAnchor) {
            z2 = z;
        } else {
            decorViewLayoutParams.accessibilityIdOfAnchor = j;
        }
        if (z2) {
            update(view, decorViewLayoutParams);
        }
    }

    protected boolean hasContentView() {
        return this.mContentView != null;
    }

    protected boolean hasDecorView() {
        return this.mDecorView != null;
    }

    protected android.view.WindowManager.LayoutParams getDecorViewLayoutParams() {
        return (android.view.WindowManager.LayoutParams) this.mDecorView.getLayoutParams();
    }

    public void update(android.view.View view, int i, int i2) {
        update(view, false, 0, 0, i, i2);
    }

    public void update(android.view.View view, int i, int i2, int i3, int i4) {
        update(view, true, i, i2, i3, i4);
    }

    private void update(android.view.View view, boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (!isShowing() || !hasContentView()) {
            return;
        }
        java.lang.ref.WeakReference<android.view.View> weakReference = this.mAnchor;
        int i7 = this.mAnchoredGravity;
        boolean z2 = z && !(this.mAnchorXoff == i && this.mAnchorYoff == i2);
        if (weakReference == null || weakReference.get() != view || (z2 && !this.mIsDropdown)) {
            attachToAnchor(view, i, i2, i7);
        } else if (z2) {
            this.mAnchorXoff = i;
            this.mAnchorYoff = i2;
        }
        android.view.WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
        int i8 = decorViewLayoutParams.gravity;
        int i9 = decorViewLayoutParams.width;
        int i10 = decorViewLayoutParams.height;
        int i11 = decorViewLayoutParams.x;
        int i12 = decorViewLayoutParams.y;
        if (i3 >= 0) {
            i5 = i3;
        } else {
            i5 = this.mWidth;
        }
        if (i4 >= 0) {
            i6 = i4;
        } else {
            i6 = this.mHeight;
        }
        updateAboveAnchor(findDropDownPosition(view, decorViewLayoutParams, this.mAnchorXoff, this.mAnchorYoff, i5, i6, i7, this.mAllowScrollingAnchorParent));
        update(decorViewLayoutParams.x, decorViewLayoutParams.y, i5 < 0 ? i5 : decorViewLayoutParams.width, i6 < 0 ? i6 : decorViewLayoutParams.height, (i8 == decorViewLayoutParams.gravity && i11 == decorViewLayoutParams.x && i12 == decorViewLayoutParams.y && i9 == decorViewLayoutParams.width && i10 == decorViewLayoutParams.height) ? false : true);
    }

    protected void detachFromAnchor() {
        android.view.View anchor = getAnchor();
        if (anchor != null) {
            anchor.getViewTreeObserver().removeOnScrollChangedListener(this.mOnScrollChangedListener);
            anchor.removeOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        }
        android.view.View view = this.mAnchorRoot != null ? this.mAnchorRoot.get() : null;
        if (view != null) {
            view.removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            view.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        this.mAnchor = null;
        this.mAnchorRoot = null;
        this.mIsAnchorRootAttached = false;
    }

    protected void attachToAnchor(android.view.View view, int i, int i2, int i3) {
        detachFromAnchor();
        android.view.ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnScrollChangedListener(this.mOnScrollChangedListener);
        }
        view.addOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        android.view.View rootView = view.getRootView();
        rootView.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
        rootView.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mAnchor = new java.lang.ref.WeakReference<>(view);
        this.mAnchorRoot = new java.lang.ref.WeakReference<>(rootView);
        this.mIsAnchorRootAttached = rootView.isAttachedToWindow();
        this.mParentRootView = this.mAnchorRoot;
        this.mAnchorXoff = i;
        this.mAnchorYoff = i2;
        this.mAnchoredGravity = i3;
    }

    protected android.view.View getAnchor() {
        if (this.mAnchor != null) {
            return this.mAnchor.get();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void alignToAnchor() {
        android.view.View view = this.mAnchor != null ? this.mAnchor.get() : null;
        if (view != null && view.isAttachedToWindow() && hasDecorView()) {
            android.view.WindowManager.LayoutParams decorViewLayoutParams = getDecorViewLayoutParams();
            updateAboveAnchor(findDropDownPosition(view, decorViewLayoutParams, this.mAnchorXoff, this.mAnchorYoff, decorViewLayoutParams.width, decorViewLayoutParams.height, this.mAnchoredGravity, false));
            update(decorViewLayoutParams.x, decorViewLayoutParams.y, -1, -1, true);
        }
    }

    private android.view.View getAppRootView(android.view.View view) {
        android.view.View windowView = android.view.WindowManagerGlobal.getInstance().getWindowView(view.getApplicationWindowToken());
        if (windowView != null) {
            return windowView;
        }
        return view.getRootView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PopupDecorView extends android.widget.FrameLayout {
        private java.lang.Runnable mCleanupAfterExit;
        private final android.view.View.OnAttachStateChangeListener mOnAnchorRootDetachedListener;

        public PopupDecorView(android.content.Context context) {
            super(context);
            this.mOnAnchorRootDetachedListener = new android.view.View.OnAttachStateChangeListener() { // from class: android.widget.PopupWindow.PopupDecorView.4
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(android.view.View view) {
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(android.view.View view) {
                    view.removeOnAttachStateChangeListener(this);
                    if (android.widget.PopupWindow.PopupDecorView.this.isAttachedToWindow()) {
                        android.transition.TransitionManager.endTransitions(android.widget.PopupWindow.PopupDecorView.this);
                    }
                }
            };
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
            android.view.KeyEvent.DispatcherState keyDispatcherState;
            if (keyEvent.getKeyCode() == 4 || keyEvent.getKeyCode() == 111) {
                if (getKeyDispatcherState() == null) {
                    return super.dispatchKeyEvent(keyEvent);
                }
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    android.view.KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1 && (keyDispatcherState = getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent) && !keyEvent.isCanceled()) {
                    android.widget.PopupWindow.this.dismiss();
                    return true;
                }
                return super.dispatchKeyEvent(keyEvent);
            }
            return super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
            if (android.widget.PopupWindow.this.mTouchInterceptor != null && android.widget.PopupWindow.this.mTouchInterceptor.onTouch(this, motionEvent)) {
                return true;
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (motionEvent.getAction() == 0 && (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())) {
                android.widget.PopupWindow.this.dismiss();
                return true;
            }
            if (motionEvent.getAction() == 4) {
                android.widget.PopupWindow.this.dismiss();
                return true;
            }
            return super.onTouchEvent(motionEvent);
        }

        public void requestEnterTransition(android.transition.Transition transition) {
            android.view.ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver != null && transition != null) {
                final android.transition.Transition mo4806clone = transition.mo4806clone();
                viewTreeObserver.addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.widget.PopupWindow.PopupDecorView.1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        android.view.ViewTreeObserver viewTreeObserver2 = android.widget.PopupWindow.PopupDecorView.this.getViewTreeObserver();
                        if (viewTreeObserver2 != null) {
                            viewTreeObserver2.removeOnGlobalLayoutListener(this);
                        }
                        final android.graphics.Rect transitionEpicenter = android.widget.PopupWindow.this.getTransitionEpicenter();
                        mo4806clone.setEpicenterCallback(new android.transition.Transition.EpicenterCallback() { // from class: android.widget.PopupWindow.PopupDecorView.1.1
                            @Override // android.transition.Transition.EpicenterCallback
                            public android.graphics.Rect onGetEpicenter(android.transition.Transition transition2) {
                                return transitionEpicenter;
                            }
                        });
                        android.widget.PopupWindow.PopupDecorView.this.startEnterTransition(mo4806clone);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startEnterTransition(android.transition.Transition transition) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = getChildAt(i);
                transition.addTarget(childAt);
                childAt.setTransitionVisibility(4);
            }
            android.transition.TransitionManager.beginDelayedTransition(this, transition);
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).setTransitionVisibility(0);
            }
        }

        public void startExitTransition(final android.transition.Transition transition, final android.view.View view, final android.graphics.Rect rect, final android.transition.Transition.TransitionListener transitionListener) {
            if (transition == null) {
                return;
            }
            if (view != null) {
                view.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            }
            this.mCleanupAfterExit = new java.lang.Runnable() { // from class: android.widget.PopupWindow$PopupDecorView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.widget.PopupWindow.PopupDecorView.this.lambda$startExitTransition$0(transitionListener, transition, view);
                }
            };
            android.transition.Transition mo4806clone = transition.mo4806clone();
            mo4806clone.addListener(new android.transition.TransitionListenerAdapter() { // from class: android.widget.PopupWindow.PopupDecorView.2
                @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
                public void onTransitionEnd(android.transition.Transition transition2) {
                    transition2.removeListener(this);
                    if (android.widget.PopupWindow.PopupDecorView.this.mCleanupAfterExit != null) {
                        android.widget.PopupWindow.PopupDecorView.this.mCleanupAfterExit.run();
                    }
                }
            });
            mo4806clone.setEpicenterCallback(new android.transition.Transition.EpicenterCallback() { // from class: android.widget.PopupWindow.PopupDecorView.3
                @Override // android.transition.Transition.EpicenterCallback
                public android.graphics.Rect onGetEpicenter(android.transition.Transition transition2) {
                    return rect;
                }
            });
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                mo4806clone.addTarget(getChildAt(i));
            }
            android.transition.TransitionManager.beginDelayedTransition(this, mo4806clone);
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).setVisibility(4);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startExitTransition$0(android.transition.Transition.TransitionListener transitionListener, android.transition.Transition transition, android.view.View view) {
            transitionListener.onTransitionEnd(transition);
            if (view != null) {
                view.removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            }
            this.mCleanupAfterExit = null;
        }

        public void cancelTransitions() {
            android.transition.TransitionManager.endTransitions(this);
            if (this.mCleanupAfterExit != null) {
                this.mCleanupAfterExit.run();
            }
        }

        @Override // android.view.View
        public void requestKeyboardShortcuts(java.util.List<android.view.KeyboardShortcutGroup> list, int i) {
            android.view.View view;
            if (android.widget.PopupWindow.this.mParentRootView != null && (view = (android.view.View) android.widget.PopupWindow.this.mParentRootView.get()) != null) {
                view.requestKeyboardShortcuts(list, i);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            android.window.OnBackInvokedDispatcher findOnBackInvokedDispatcher;
            super.onAttachedToWindow();
            if (!android.window.WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this.mContext) || (findOnBackInvokedDispatcher = findOnBackInvokedDispatcher()) == null) {
                return;
            }
            android.widget.PopupWindow popupWindow = android.widget.PopupWindow.this;
            final android.widget.PopupWindow popupWindow2 = android.widget.PopupWindow.this;
            popupWindow.mBackCallback = new android.window.OnBackInvokedCallback() { // from class: android.widget.PopupWindow$PopupDecorView$$ExternalSyntheticLambda1
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    android.widget.PopupWindow.this.dismiss();
                }
            };
            findOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, android.widget.PopupWindow.this.mBackCallback);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            android.widget.PopupWindow.this.unregisterBackCallback(findOnBackInvokedDispatcher());
        }
    }

    private class PopupBackgroundView extends android.widget.FrameLayout {
        public PopupBackgroundView(android.content.Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected int[] onCreateDrawableState(int i) {
            if (android.widget.PopupWindow.this.mAboveAnchor) {
                int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
                android.view.View.mergeDrawableStates(onCreateDrawableState, android.widget.PopupWindow.ABOVE_ANCHOR_STATE_SET);
                return onCreateDrawableState;
            }
            return super.onCreateDrawableState(i);
        }
    }
}
