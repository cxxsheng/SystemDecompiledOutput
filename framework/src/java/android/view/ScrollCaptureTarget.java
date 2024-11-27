package android.view;

/* loaded from: classes4.dex */
public final class ScrollCaptureTarget {
    private final android.view.ScrollCaptureCallback mCallback;
    private final android.view.View mContainingView;
    private final int mHint;
    private final android.graphics.Rect mLocalVisibleRect;
    private final android.graphics.Point mPositionInWindow;
    private android.graphics.Rect mScrollBounds;
    private final int[] mTmpIntArr = new int[2];

    public ScrollCaptureTarget(android.view.View view, android.graphics.Rect rect, android.graphics.Point point, android.view.ScrollCaptureCallback scrollCaptureCallback) {
        this.mContainingView = (android.view.View) java.util.Objects.requireNonNull(view);
        this.mHint = this.mContainingView.getScrollCaptureHint();
        this.mCallback = (android.view.ScrollCaptureCallback) java.util.Objects.requireNonNull(scrollCaptureCallback);
        this.mLocalVisibleRect = (android.graphics.Rect) java.util.Objects.requireNonNull(rect);
        this.mPositionInWindow = (android.graphics.Point) java.util.Objects.requireNonNull(point);
    }

    public int getHint() {
        return this.mHint;
    }

    public android.view.ScrollCaptureCallback getCallback() {
        return this.mCallback;
    }

    public android.view.View getContainingView() {
        return this.mContainingView;
    }

    public android.graphics.Rect getLocalVisibleRect() {
        return this.mLocalVisibleRect;
    }

    public android.graphics.Point getPositionInWindow() {
        return this.mPositionInWindow;
    }

    public android.graphics.Rect getScrollBounds() {
        return this.mScrollBounds;
    }

    public void setScrollBounds(android.graphics.Rect rect) {
        this.mScrollBounds = android.graphics.Rect.copyOrNull(rect);
        if (this.mScrollBounds != null && !this.mScrollBounds.intersect(0, 0, this.mContainingView.getWidth(), this.mContainingView.getHeight())) {
            this.mScrollBounds.setEmpty();
        }
    }

    public void updatePositionInWindow() {
        this.mContainingView.getLocationInWindow(this.mTmpIntArr);
        this.mPositionInWindow.x = this.mTmpIntArr[0];
        this.mPositionInWindow.y = this.mTmpIntArr[1];
    }

    public java.lang.String toString() {
        return "ScrollCaptureTarget{view=" + this.mContainingView + ", callback=" + this.mCallback + ", scrollBounds=" + this.mScrollBounds + ", localVisibleRect=" + this.mLocalVisibleRect + ", positionInWindow=" + this.mPositionInWindow + "}";
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("view: " + getContainingView());
        printWriter.println("hint: " + this.mHint);
        printWriter.println("callback: " + this.mCallback);
        printWriter.println("scrollBounds: " + (this.mScrollBounds == null ? "null" : this.mScrollBounds.toShortString()));
        android.graphics.Point positionInWindow = getPositionInWindow();
        printWriter.println("positionInWindow: " + (positionInWindow == null ? "null" : android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + positionInWindow.x + "," + positionInWindow.y + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END));
        android.graphics.Rect localVisibleRect = getLocalVisibleRect();
        printWriter.println("localVisibleRect: " + (localVisibleRect != null ? localVisibleRect.toShortString() : "null"));
    }
}
