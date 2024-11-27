package com.android.internal.widget;

/* loaded from: classes5.dex */
public abstract class ExploreByTouchHelper extends android.view.View.AccessibilityDelegate {
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private final android.content.Context mContext;
    private int mFocusedVirtualViewId = Integer.MIN_VALUE;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;
    private final android.view.accessibility.AccessibilityManager mManager;
    private com.android.internal.widget.ExploreByTouchHelper.ExploreByTouchNodeProvider mNodeProvider;
    private android.util.IntArray mTempArray;
    private int[] mTempGlobalRect;
    private android.graphics.Rect mTempParentRect;
    private android.graphics.Rect mTempScreenRect;
    private android.graphics.Rect mTempVisibleRect;
    private final android.view.View mView;
    private static final java.lang.String DEFAULT_CLASS_NAME = android.view.View.class.getName();
    private static final android.graphics.Rect INVALID_PARENT_BOUNDS = new android.graphics.Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

    protected abstract int getVirtualViewAt(float f, float f2);

    protected abstract void getVisibleVirtualViews(android.util.IntArray intArray);

    protected abstract boolean onPerformActionForVirtualView(int i, int i2, android.os.Bundle bundle);

    protected abstract void onPopulateEventForVirtualView(int i, android.view.accessibility.AccessibilityEvent accessibilityEvent);

    protected abstract void onPopulateNodeForVirtualView(int i, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo);

    public ExploreByTouchHelper(android.view.View view) {
        if (view == null) {
            throw new java.lang.IllegalArgumentException("View may not be null");
        }
        this.mView = view;
        this.mContext = view.getContext();
        this.mManager = (android.view.accessibility.AccessibilityManager) this.mContext.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE);
    }

    @Override // android.view.View.AccessibilityDelegate
    public android.view.accessibility.AccessibilityNodeProvider getAccessibilityNodeProvider(android.view.View view) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new com.android.internal.widget.ExploreByTouchHelper.ExploreByTouchNodeProvider();
        }
        return this.mNodeProvider;
    }

    public boolean dispatchHoverEvent(android.view.MotionEvent motionEvent) {
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 7:
            case 9:
                int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
                updateHoveredVirtualView(virtualViewAt);
                return virtualViewAt != Integer.MIN_VALUE;
            case 8:
            default:
                return false;
            case 10:
                if (this.mHoveredVirtualViewId == Integer.MIN_VALUE) {
                    return false;
                }
                updateHoveredVirtualView(Integer.MIN_VALUE);
                return true;
        }
    }

    public boolean sendEventForVirtualView(int i, int i2) {
        android.view.ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mView.getParent()) == null) {
            return false;
        }
        return parent.requestSendAccessibilityEvent(this.mView, createEvent(i, i2));
    }

    public void invalidateRoot() {
        invalidateVirtualView(-1, 1);
    }

    public void invalidateVirtualView(int i) {
        invalidateVirtualView(i, 0);
    }

    public void invalidateVirtualView(int i, int i2) {
        android.view.ViewParent parent;
        if (i != Integer.MIN_VALUE && this.mManager.isEnabled() && (parent = this.mView.getParent()) != null) {
            android.view.accessibility.AccessibilityEvent createEvent = createEvent(i, 2048);
            createEvent.setContentChangeTypes(i2);
            parent.requestSendAccessibilityEvent(this.mView, createEvent);
        }
    }

    public int getFocusedVirtualView() {
        return this.mFocusedVirtualViewId;
    }

    private void updateHoveredVirtualView(int i) {
        if (this.mHoveredVirtualViewId == i) {
            return;
        }
        int i2 = this.mHoveredVirtualViewId;
        this.mHoveredVirtualViewId = i;
        sendEventForVirtualView(i, 128);
        sendEventForVirtualView(i2, 256);
    }

    private android.view.accessibility.AccessibilityEvent createEvent(int i, int i2) {
        switch (i) {
            case -1:
                return createEventForHost(i2);
            default:
                return createEventForChild(i, i2);
        }
    }

    private android.view.accessibility.AccessibilityEvent createEventForHost(int i) {
        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(i);
        this.mView.onInitializeAccessibilityEvent(obtain);
        onPopulateEventForHost(obtain);
        return obtain;
    }

    private android.view.accessibility.AccessibilityEvent createEventForChild(int i, int i2) {
        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(i2);
        obtain.setEnabled(true);
        obtain.setClassName(DEFAULT_CLASS_NAME);
        onPopulateEventForVirtualView(i, obtain);
        if (obtain.getText().isEmpty() && obtain.getContentDescription() == null) {
            throw new java.lang.RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        obtain.setPackageName(this.mView.getContext().getPackageName());
        obtain.setSource(this.mView, i);
        return obtain;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.accessibility.AccessibilityNodeInfo createNode(int i) {
        switch (i) {
            case -1:
                return createNodeForHost();
            default:
                return createNodeForChild(i);
        }
    }

    private android.view.accessibility.AccessibilityNodeInfo createNodeForHost() {
        android.view.accessibility.AccessibilityNodeInfo obtain = android.view.accessibility.AccessibilityNodeInfo.obtain(this.mView);
        this.mView.onInitializeAccessibilityNodeInfo(obtain);
        int childCount = obtain.getChildCount();
        onPopulateNodeForHost(obtain);
        if (this.mTempArray == null) {
            this.mTempArray = new android.util.IntArray();
        } else {
            this.mTempArray.clear();
        }
        android.util.IntArray intArray = this.mTempArray;
        getVisibleVirtualViews(intArray);
        if (childCount > 0 && intArray.size() > 0) {
            throw new java.lang.RuntimeException("Views cannot have both real and virtual children");
        }
        int size = intArray.size();
        for (int i = 0; i < size; i++) {
            obtain.addChild(this.mView, intArray.get(i));
        }
        return obtain;
    }

    private android.view.accessibility.AccessibilityNodeInfo createNodeForChild(int i) {
        ensureTempRects();
        android.graphics.Rect rect = this.mTempParentRect;
        int[] iArr = this.mTempGlobalRect;
        android.graphics.Rect rect2 = this.mTempScreenRect;
        android.view.accessibility.AccessibilityNodeInfo obtain = android.view.accessibility.AccessibilityNodeInfo.obtain();
        obtain.setEnabled(true);
        obtain.setClassName(DEFAULT_CLASS_NAME);
        obtain.setBoundsInParent(INVALID_PARENT_BOUNDS);
        onPopulateNodeForVirtualView(i, obtain);
        if (obtain.getText() == null && obtain.getContentDescription() == null) {
            throw new java.lang.RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        obtain.getBoundsInParent(rect);
        if (rect.equals(INVALID_PARENT_BOUNDS)) {
            throw new java.lang.RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = obtain.getActions();
        if ((actions & 64) != 0) {
            throw new java.lang.RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & 128) != 0) {
            throw new java.lang.RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        obtain.setPackageName(this.mView.getContext().getPackageName());
        obtain.setSource(this.mView, i);
        obtain.setParent(this.mView);
        if (this.mFocusedVirtualViewId == i) {
            obtain.setAccessibilityFocused(true);
            obtain.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
        } else {
            obtain.setAccessibilityFocused(false);
            obtain.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
        }
        if (intersectVisibleToUser(rect)) {
            obtain.setVisibleToUser(true);
            obtain.setBoundsInParent(rect);
        }
        this.mView.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        rect2.set(rect);
        rect2.offset(i2, i3);
        obtain.setBoundsInScreen(rect2);
        return obtain;
    }

    private void ensureTempRects() {
        this.mTempGlobalRect = new int[2];
        this.mTempParentRect = new android.graphics.Rect();
        this.mTempScreenRect = new android.graphics.Rect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performAction(int i, int i2, android.os.Bundle bundle) {
        switch (i) {
            case -1:
                return performActionForHost(i2, bundle);
            default:
                return performActionForChild(i, i2, bundle);
        }
    }

    private boolean performActionForHost(int i, android.os.Bundle bundle) {
        return this.mView.performAccessibilityAction(i, bundle);
    }

    private boolean performActionForChild(int i, int i2, android.os.Bundle bundle) {
        switch (i2) {
            case 64:
            case 128:
                return manageFocusForChild(i, i2);
            default:
                return onPerformActionForVirtualView(i, i2, bundle);
        }
    }

    private boolean manageFocusForChild(int i, int i2) {
        switch (i2) {
            case 64:
                return requestAccessibilityFocus(i);
            case 128:
                return clearAccessibilityFocus(i);
            default:
                return false;
        }
    }

    private boolean intersectVisibleToUser(android.graphics.Rect rect) {
        if (rect == null || rect.isEmpty() || this.mView.getWindowVisibility() != 0) {
            return false;
        }
        java.lang.Object parent = this.mView.getParent();
        while (parent instanceof android.view.View) {
            android.view.View view = (android.view.View) parent;
            if (view.getAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            parent = view.getParent();
        }
        if (parent == null) {
            return false;
        }
        if (this.mTempVisibleRect == null) {
            this.mTempVisibleRect = new android.graphics.Rect();
        }
        android.graphics.Rect rect2 = this.mTempVisibleRect;
        if (!this.mView.getLocalVisibleRect(rect2)) {
            return false;
        }
        return rect.intersect(rect2);
    }

    private boolean isAccessibilityFocused(int i) {
        return this.mFocusedVirtualViewId == i;
    }

    private boolean requestAccessibilityFocus(int i) {
        android.view.accessibility.AccessibilityManager accessibilityManager = (android.view.accessibility.AccessibilityManager) this.mContext.getSystemService(android.content.Context.ACCESSIBILITY_SERVICE);
        if (!this.mManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled() || isAccessibilityFocused(i)) {
            return false;
        }
        if (this.mFocusedVirtualViewId != Integer.MIN_VALUE) {
            sendEventForVirtualView(this.mFocusedVirtualViewId, 65536);
        }
        this.mFocusedVirtualViewId = i;
        this.mView.invalidate();
        sendEventForVirtualView(i, 32768);
        return true;
    }

    private boolean clearAccessibilityFocus(int i) {
        if (isAccessibilityFocused(i)) {
            this.mFocusedVirtualViewId = Integer.MIN_VALUE;
            this.mView.invalidate();
            sendEventForVirtualView(i, 65536);
            return true;
        }
        return false;
    }

    protected void onPopulateEventForHost(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
    }

    protected void onPopulateNodeForHost(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
    }

    private class ExploreByTouchNodeProvider extends android.view.accessibility.AccessibilityNodeProvider {
        private ExploreByTouchNodeProvider() {
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            return com.android.internal.widget.ExploreByTouchHelper.this.createNode(i);
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public boolean performAction(int i, int i2, android.os.Bundle bundle) {
            return com.android.internal.widget.ExploreByTouchHelper.this.performAction(i, i2, bundle);
        }
    }
}
