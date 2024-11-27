package android.view;

/* loaded from: classes4.dex */
public class TouchDelegate {
    public static final int ABOVE = 1;
    public static final int BELOW = 2;
    public static final int TO_LEFT = 4;
    public static final int TO_RIGHT = 8;
    private android.graphics.Rect mBounds;
    private boolean mDelegateTargeted;
    private android.view.View mDelegateView;
    private int mSlop;
    private android.graphics.Rect mSlopBounds;
    private android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo mTouchDelegateInfo;

    public TouchDelegate(android.graphics.Rect rect, android.view.View view) {
        this.mBounds = rect;
        this.mSlop = android.view.ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        this.mSlopBounds = new android.graphics.Rect(rect);
        this.mSlopBounds.inset(-this.mSlop, -this.mSlop);
        this.mDelegateView = view;
    }

    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mDelegateTargeted = this.mBounds.contains(x, y);
                z = this.mDelegateTargeted;
                break;
            case 1:
            case 2:
            case 5:
            case 6:
                boolean z2 = this.mDelegateTargeted;
                if (!z2) {
                    z = z2;
                    break;
                } else {
                    r4 = this.mSlopBounds.contains(x, y);
                    z = z2;
                    break;
                }
            case 3:
                z = this.mDelegateTargeted;
                this.mDelegateTargeted = false;
                break;
            case 4:
            default:
                z = false;
                break;
        }
        if (!z) {
            return false;
        }
        if (r4) {
            motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
        } else {
            float f = -(this.mSlop * 2);
            motionEvent.setLocation(f, f);
        }
        return this.mDelegateView.dispatchTouchEvent(motionEvent);
    }

    public boolean onTouchExplorationHoverEvent(android.view.MotionEvent motionEvent) {
        if (this.mBounds == null) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean contains = this.mBounds.contains(x, y);
        boolean z = true;
        switch (motionEvent.getActionMasked()) {
            case 7:
                if (contains) {
                    this.mDelegateTargeted = true;
                    break;
                } else if (this.mDelegateTargeted && !this.mSlopBounds.contains(x, y)) {
                    z = false;
                    break;
                }
                break;
            case 9:
                this.mDelegateTargeted = contains;
                break;
            case 10:
                this.mDelegateTargeted = true;
                break;
        }
        if (!this.mDelegateTargeted) {
            return false;
        }
        if (z) {
            motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
        } else {
            this.mDelegateTargeted = false;
        }
        return this.mDelegateView.dispatchHoverEvent(motionEvent);
    }

    public android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo getTouchDelegateInfo() {
        if (this.mTouchDelegateInfo == null) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(1);
            android.graphics.Rect rect = this.mBounds;
            if (rect == null) {
                rect = new android.graphics.Rect();
            }
            arrayMap.put(new android.graphics.Region(rect), this.mDelegateView);
            this.mTouchDelegateInfo = new android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo(arrayMap);
        }
        return this.mTouchDelegateInfo;
    }
}
