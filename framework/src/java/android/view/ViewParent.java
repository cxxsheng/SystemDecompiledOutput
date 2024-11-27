package android.view;

/* loaded from: classes4.dex */
public interface ViewParent {
    void bringChildToFront(android.view.View view);

    boolean canResolveLayoutDirection();

    boolean canResolveTextAlignment();

    boolean canResolveTextDirection();

    void childDrawableStateChanged(android.view.View view);

    void childHasTransientStateChanged(android.view.View view, boolean z);

    void clearChildFocus(android.view.View view);

    void createContextMenu(android.view.ContextMenu contextMenu);

    android.view.View focusSearch(android.view.View view, int i);

    void focusableViewAvailable(android.view.View view);

    boolean getChildVisibleRect(android.view.View view, android.graphics.Rect rect, android.graphics.Point point);

    int getLayoutDirection();

    android.view.ViewParent getParent();

    android.view.ViewParent getParentForAccessibility();

    int getTextAlignment();

    int getTextDirection();

    @java.lang.Deprecated
    void invalidateChild(android.view.View view, android.graphics.Rect rect);

    @java.lang.Deprecated
    android.view.ViewParent invalidateChildInParent(int[] iArr, android.graphics.Rect rect);

    boolean isLayoutDirectionResolved();

    boolean isLayoutRequested();

    boolean isTextAlignmentResolved();

    boolean isTextDirectionResolved();

    android.view.View keyboardNavigationClusterSearch(android.view.View view, int i);

    void notifySubtreeAccessibilityStateChanged(android.view.View view, android.view.View view2, int i);

    boolean onNestedFling(android.view.View view, float f, float f2, boolean z);

    boolean onNestedPreFling(android.view.View view, float f, float f2);

    boolean onNestedPrePerformAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle);

    void onNestedPreScroll(android.view.View view, int i, int i2, int[] iArr);

    void onNestedScroll(android.view.View view, int i, int i2, int i3, int i4);

    void onNestedScrollAccepted(android.view.View view, android.view.View view2, int i);

    boolean onStartNestedScroll(android.view.View view, android.view.View view2, int i);

    void onStopNestedScroll(android.view.View view);

    void recomputeViewAttributes(android.view.View view);

    void requestChildFocus(android.view.View view, android.view.View view2);

    boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z);

    void requestDisallowInterceptTouchEvent(boolean z);

    void requestFitSystemWindows();

    void requestLayout();

    boolean requestSendAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent);

    void requestTransparentRegion(android.view.View view);

    boolean showContextMenuForChild(android.view.View view);

    boolean showContextMenuForChild(android.view.View view, float f, float f2);

    android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback);

    android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback, int i);

    default void onDescendantInvalidated(android.view.View view, android.view.View view2) {
        if (getParent() != null) {
            getParent().onDescendantInvalidated(view, view2);
        }
    }

    default void subtractObscuredTouchableRegion(android.graphics.Region region, android.view.View view) {
    }

    default boolean getChildLocalHitRegion(android.view.View view, android.graphics.Region region, android.graphics.Matrix matrix, boolean z) {
        region.setEmpty();
        return false;
    }

    default void onDescendantUnbufferedRequested() {
        if (getParent() != null) {
            getParent().onDescendantUnbufferedRequested();
        }
    }

    default android.window.OnBackInvokedDispatcher findOnBackInvokedDispatcherForChild(android.view.View view, android.view.View view2) {
        return null;
    }
}
