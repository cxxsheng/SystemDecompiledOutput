package com.android.internal.view;

/* loaded from: classes5.dex */
public final class FloatingActionMode extends android.view.ActionMode {
    private static final int MAX_HIDE_DURATION = 3000;
    private static final int MOVING_HIDE_DELAY = 50;
    private final int mBottomAllowance;
    private final android.view.ActionMode.Callback2 mCallback;
    private final android.graphics.Rect mContentRect;
    private final android.graphics.Rect mContentRectOnScreen;
    private final android.content.Context mContext;
    private final android.graphics.Point mDisplaySize;
    private com.android.internal.widget.floatingtoolbar.FloatingToolbar mFloatingToolbar;
    private com.android.internal.view.FloatingActionMode.FloatingToolbarVisibilityHelper mFloatingToolbarVisibilityHelper;
    private final com.android.internal.view.menu.MenuBuilder mMenu;
    private final android.view.View mOriginatingView;
    private final android.graphics.Rect mPreviousContentRectOnScreen;
    private final int[] mPreviousViewPositionOnScreen;
    private final android.graphics.Rect mPreviousViewRectOnScreen;
    private final int[] mRootViewPositionOnScreen;
    private final android.graphics.Rect mScreenRect;
    private final int[] mViewPositionOnScreen;
    private final android.graphics.Rect mViewRectOnScreen;
    private final java.lang.Runnable mMovingOff = new java.lang.Runnable() { // from class: com.android.internal.view.FloatingActionMode.1
        @Override // java.lang.Runnable
        public void run() {
            if (com.android.internal.view.FloatingActionMode.this.isViewStillActive()) {
                com.android.internal.view.FloatingActionMode.this.mFloatingToolbarVisibilityHelper.setMoving(false);
                com.android.internal.view.FloatingActionMode.this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
            }
        }
    };
    private final java.lang.Runnable mHideOff = new java.lang.Runnable() { // from class: com.android.internal.view.FloatingActionMode.2
        @Override // java.lang.Runnable
        public void run() {
            if (com.android.internal.view.FloatingActionMode.this.isViewStillActive()) {
                com.android.internal.view.FloatingActionMode.this.mFloatingToolbarVisibilityHelper.setHideRequested(false);
                com.android.internal.view.FloatingActionMode.this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
            }
        }
    };

    public FloatingActionMode(android.content.Context context, android.view.ActionMode.Callback2 callback2, android.view.View view, com.android.internal.widget.floatingtoolbar.FloatingToolbar floatingToolbar) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
        this.mCallback = (android.view.ActionMode.Callback2) java.util.Objects.requireNonNull(callback2);
        this.mMenu = new com.android.internal.view.menu.MenuBuilder(context).setDefaultShowAsAction(1);
        setType(1);
        this.mMenu.setCallback(new com.android.internal.view.menu.MenuBuilder.Callback() { // from class: com.android.internal.view.FloatingActionMode.3
            @Override // com.android.internal.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            }

            @Override // com.android.internal.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
                return com.android.internal.view.FloatingActionMode.this.mCallback.onActionItemClicked(com.android.internal.view.FloatingActionMode.this, menuItem);
            }
        });
        this.mContentRect = new android.graphics.Rect();
        this.mContentRectOnScreen = new android.graphics.Rect();
        this.mPreviousContentRectOnScreen = new android.graphics.Rect();
        this.mViewPositionOnScreen = new int[2];
        this.mPreviousViewPositionOnScreen = new int[2];
        this.mRootViewPositionOnScreen = new int[2];
        this.mViewRectOnScreen = new android.graphics.Rect();
        this.mPreviousViewRectOnScreen = new android.graphics.Rect();
        this.mScreenRect = new android.graphics.Rect();
        this.mOriginatingView = (android.view.View) java.util.Objects.requireNonNull(view);
        this.mOriginatingView.getLocationOnScreen(this.mViewPositionOnScreen);
        this.mBottomAllowance = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.content_rect_bottom_clip_allowance);
        this.mDisplaySize = new android.graphics.Point();
        setFloatingToolbar((com.android.internal.widget.floatingtoolbar.FloatingToolbar) java.util.Objects.requireNonNull(floatingToolbar));
    }

    private void setFloatingToolbar(com.android.internal.widget.floatingtoolbar.FloatingToolbar floatingToolbar) {
        this.mFloatingToolbar = floatingToolbar.setMenu(this.mMenu).setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener() { // from class: com.android.internal.view.FloatingActionMode$$ExternalSyntheticLambda0
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public final boolean onMenuItemClick(android.view.MenuItem menuItem) {
                boolean lambda$setFloatingToolbar$0;
                lambda$setFloatingToolbar$0 = com.android.internal.view.FloatingActionMode.this.lambda$setFloatingToolbar$0(menuItem);
                return lambda$setFloatingToolbar$0;
            }
        });
        this.mFloatingToolbarVisibilityHelper = new com.android.internal.view.FloatingActionMode.FloatingToolbarVisibilityHelper(this.mFloatingToolbar);
        this.mFloatingToolbarVisibilityHelper.activate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setFloatingToolbar$0(android.view.MenuItem menuItem) {
        return this.mMenu.performItemAction(menuItem, 0);
    }

    @Override // android.view.ActionMode
    public void setTitle(java.lang.CharSequence charSequence) {
    }

    @Override // android.view.ActionMode
    public void setTitle(int i) {
    }

    @Override // android.view.ActionMode
    public void setSubtitle(java.lang.CharSequence charSequence) {
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i) {
    }

    @Override // android.view.ActionMode
    public void setCustomView(android.view.View view) {
    }

    @Override // android.view.ActionMode
    public void invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
        invalidateContentRect();
    }

    @Override // android.view.ActionMode
    public void invalidateContentRect() {
        this.mCallback.onGetContentRect(this, this.mOriginatingView, this.mContentRect);
        updateViewLocationInWindow(true);
    }

    public void updateViewLocationInWindow() {
        updateViewLocationInWindow(false);
    }

    private void updateViewLocationInWindow(boolean z) {
        this.mOriginatingView.getLocationOnScreen(this.mViewPositionOnScreen);
        this.mOriginatingView.getRootView().getLocationOnScreen(this.mRootViewPositionOnScreen);
        this.mOriginatingView.getGlobalVisibleRect(this.mViewRectOnScreen);
        this.mViewRectOnScreen.offset(this.mRootViewPositionOnScreen[0], this.mRootViewPositionOnScreen[1]);
        if (z || !java.util.Arrays.equals(this.mViewPositionOnScreen, this.mPreviousViewPositionOnScreen) || !this.mViewRectOnScreen.equals(this.mPreviousViewRectOnScreen)) {
            repositionToolbar();
            this.mPreviousViewPositionOnScreen[0] = this.mViewPositionOnScreen[0];
            this.mPreviousViewPositionOnScreen[1] = this.mViewPositionOnScreen[1];
            this.mPreviousViewRectOnScreen.set(this.mViewRectOnScreen);
        }
    }

    private void repositionToolbar() {
        this.mContentRectOnScreen.set(this.mContentRect);
        android.view.ViewParent parent = this.mOriginatingView.getParent();
        if (parent instanceof android.view.ViewGroup) {
            ((android.view.ViewGroup) parent).getChildVisibleRect(this.mOriginatingView, this.mContentRectOnScreen, null, true);
            this.mContentRectOnScreen.offset(this.mRootViewPositionOnScreen[0], this.mRootViewPositionOnScreen[1]);
        } else {
            this.mContentRectOnScreen.offset(this.mViewPositionOnScreen[0], this.mViewPositionOnScreen[1]);
        }
        if (isContentRectWithinBounds()) {
            this.mFloatingToolbarVisibilityHelper.setOutOfBounds(false);
            this.mContentRectOnScreen.set(java.lang.Math.max(this.mContentRectOnScreen.left, this.mViewRectOnScreen.left), java.lang.Math.max(this.mContentRectOnScreen.top, this.mViewRectOnScreen.top), java.lang.Math.min(this.mContentRectOnScreen.right, this.mViewRectOnScreen.right), java.lang.Math.min(this.mContentRectOnScreen.bottom, this.mViewRectOnScreen.bottom + this.mBottomAllowance));
            if (!this.mContentRectOnScreen.equals(this.mPreviousContentRectOnScreen)) {
                if (!this.mPreviousContentRectOnScreen.isEmpty()) {
                    this.mOriginatingView.removeCallbacks(this.mMovingOff);
                    this.mFloatingToolbarVisibilityHelper.setMoving(true);
                    this.mOriginatingView.postDelayed(this.mMovingOff, 50L);
                }
                this.mFloatingToolbar.setContentRect(this.mContentRectOnScreen);
                this.mFloatingToolbar.updateLayout();
            }
        } else {
            this.mFloatingToolbarVisibilityHelper.setOutOfBounds(true);
            this.mContentRectOnScreen.setEmpty();
        }
        this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
        this.mPreviousContentRectOnScreen.set(this.mContentRectOnScreen);
    }

    private boolean isContentRectWithinBounds() {
        this.mContext.getDisplayNoVerify().getRealSize(this.mDisplaySize);
        this.mScreenRect.set(0, 0, this.mDisplaySize.x, this.mDisplaySize.y);
        this.mScreenRect.offset(this.mRootViewPositionOnScreen[0], this.mRootViewPositionOnScreen[1]);
        return intersectsClosed(this.mContentRectOnScreen, this.mScreenRect) && intersectsClosed(this.mContentRectOnScreen, this.mViewRectOnScreen);
    }

    private static boolean intersectsClosed(android.graphics.Rect rect, android.graphics.Rect rect2) {
        return rect.left <= rect2.right && rect2.left <= rect.right && rect.top <= rect2.bottom && rect2.top <= rect.bottom;
    }

    @Override // android.view.ActionMode
    public void hide(long j) {
        if (j == -1) {
            j = android.view.ViewConfiguration.getDefaultActionModeHideDuration();
        }
        long min = java.lang.Math.min(3000L, j);
        this.mOriginatingView.removeCallbacks(this.mHideOff);
        if (min <= 0) {
            this.mHideOff.run();
            return;
        }
        this.mFloatingToolbarVisibilityHelper.setHideRequested(true);
        this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
        this.mOriginatingView.postDelayed(this.mHideOff, min);
    }

    public void setOutsideTouchable(boolean z, android.widget.PopupWindow.OnDismissListener onDismissListener) {
        this.mFloatingToolbar.setOutsideTouchable(z, onDismissListener);
    }

    @Override // android.view.ActionMode
    public void onWindowFocusChanged(boolean z) {
        this.mFloatingToolbarVisibilityHelper.setWindowFocused(z);
        this.mFloatingToolbarVisibilityHelper.updateToolbarVisibility();
    }

    @Override // android.view.ActionMode
    public void finish() {
        reset();
        this.mCallback.onDestroyActionMode(this);
    }

    @Override // android.view.ActionMode
    public android.view.Menu getMenu() {
        return this.mMenu;
    }

    @Override // android.view.ActionMode
    public java.lang.CharSequence getTitle() {
        return null;
    }

    @Override // android.view.ActionMode
    public java.lang.CharSequence getSubtitle() {
        return null;
    }

    @Override // android.view.ActionMode
    public android.view.View getCustomView() {
        return null;
    }

    @Override // android.view.ActionMode
    public android.view.MenuInflater getMenuInflater() {
        return new android.view.MenuInflater(this.mContext);
    }

    private void reset() {
        this.mFloatingToolbar.dismiss();
        this.mFloatingToolbarVisibilityHelper.deactivate();
        this.mOriginatingView.removeCallbacks(this.mMovingOff);
        this.mOriginatingView.removeCallbacks(this.mHideOff);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isViewStillActive() {
        return this.mOriginatingView.getWindowVisibility() == 0 && this.mOriginatingView.isShown();
    }

    private static final class FloatingToolbarVisibilityHelper {
        private static final long MIN_SHOW_DURATION_FOR_MOVE_HIDE = 500;
        private boolean mActive;
        private boolean mHideRequested;
        private long mLastShowTime;
        private boolean mMoving;
        private boolean mOutOfBounds;
        private final com.android.internal.widget.floatingtoolbar.FloatingToolbar mToolbar;
        private boolean mWindowFocused = true;

        public FloatingToolbarVisibilityHelper(com.android.internal.widget.floatingtoolbar.FloatingToolbar floatingToolbar) {
            this.mToolbar = (com.android.internal.widget.floatingtoolbar.FloatingToolbar) java.util.Objects.requireNonNull(floatingToolbar);
        }

        public void activate() {
            this.mHideRequested = false;
            this.mMoving = false;
            this.mOutOfBounds = false;
            this.mWindowFocused = true;
            this.mActive = true;
        }

        public void deactivate() {
            this.mActive = false;
            this.mToolbar.dismiss();
        }

        public void setHideRequested(boolean z) {
            this.mHideRequested = z;
        }

        public void setMoving(boolean z) {
            boolean z2 = java.lang.System.currentTimeMillis() - this.mLastShowTime > MIN_SHOW_DURATION_FOR_MOVE_HIDE;
            if (!z || z2) {
                this.mMoving = z;
            }
        }

        public void setOutOfBounds(boolean z) {
            this.mOutOfBounds = z;
        }

        public void setWindowFocused(boolean z) {
            this.mWindowFocused = z;
        }

        public void updateToolbarVisibility() {
            if (!this.mActive) {
                return;
            }
            if (this.mHideRequested || this.mMoving || this.mOutOfBounds || !this.mWindowFocused) {
                this.mToolbar.hide();
            } else {
                this.mToolbar.show();
                this.mLastShowTime = java.lang.System.currentTimeMillis();
            }
        }
    }
}
