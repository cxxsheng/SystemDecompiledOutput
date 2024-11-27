package android.view;

/* loaded from: classes4.dex */
public class ScrollCaptureSession {
    private final android.graphics.Point mPositionInWindow;
    private final android.graphics.Rect mScrollBounds;
    private final android.view.Surface mSurface;

    public ScrollCaptureSession(android.view.Surface surface, android.graphics.Rect rect, android.graphics.Point point) {
        this.mSurface = (android.view.Surface) java.util.Objects.requireNonNull(surface);
        this.mScrollBounds = (android.graphics.Rect) java.util.Objects.requireNonNull(rect);
        this.mPositionInWindow = (android.graphics.Point) java.util.Objects.requireNonNull(point);
    }

    public android.view.Surface getSurface() {
        return this.mSurface;
    }

    public android.graphics.Rect getScrollBounds() {
        return this.mScrollBounds;
    }

    public android.graphics.Point getPositionInWindow() {
        return this.mPositionInWindow;
    }
}
