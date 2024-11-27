package android.view;

/* loaded from: classes4.dex */
public final class ImeFocusController {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ImeFocusController";
    private android.view.ImeFocusController.InputMethodManagerDelegate mDelegate;
    private boolean mHasImeFocus = false;
    private final android.view.ViewRootImpl mViewRootImpl;

    public interface InputMethodManagerDelegate {
        void onPostWindowGainedFocus(android.view.View view, android.view.WindowManager.LayoutParams layoutParams);

        void onPreWindowGainedFocus(android.view.ViewRootImpl viewRootImpl);

        void onScheduledCheckFocus(android.view.ViewRootImpl viewRootImpl);

        void onViewDetachedFromWindow(android.view.View view, android.view.ViewRootImpl viewRootImpl);

        void onViewFocusChanged(android.view.View view, boolean z);

        void onWindowDismissed(android.view.ViewRootImpl viewRootImpl);

        void onWindowLostFocus(android.view.ViewRootImpl viewRootImpl);
    }

    ImeFocusController(android.view.ViewRootImpl viewRootImpl) {
        this.mViewRootImpl = viewRootImpl;
    }

    private android.view.ImeFocusController.InputMethodManagerDelegate getImmDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = ((android.view.inputmethod.InputMethodManager) this.mViewRootImpl.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class)).getDelegate();
        }
        return this.mDelegate;
    }

    void onMovedToDisplay() {
        this.mDelegate = null;
    }

    void onTraversal(boolean z, android.view.WindowManager.LayoutParams layoutParams) {
        boolean mayUseInputMethod = android.view.WindowManager.LayoutParams.mayUseInputMethod(layoutParams.flags);
        if (!z || isInLocalFocusMode(layoutParams) || mayUseInputMethod == this.mHasImeFocus) {
            return;
        }
        this.mHasImeFocus = mayUseInputMethod;
        if (this.mHasImeFocus) {
            getImmDelegate().onPreWindowGainedFocus(this.mViewRootImpl);
            android.view.View findFocus = this.mViewRootImpl.mView.findFocus();
            if (findFocus == null) {
                findFocus = this.mViewRootImpl.mView;
            }
            getImmDelegate().onPostWindowGainedFocus(findFocus, layoutParams);
        }
    }

    void onPreWindowFocus(boolean z, android.view.WindowManager.LayoutParams layoutParams) {
        this.mHasImeFocus = android.view.WindowManager.LayoutParams.mayUseInputMethod(layoutParams.flags);
        if (!z || !this.mHasImeFocus || isInLocalFocusMode(layoutParams)) {
            if (!z) {
                getImmDelegate().onWindowLostFocus(this.mViewRootImpl);
                return;
            }
            return;
        }
        getImmDelegate().onPreWindowGainedFocus(this.mViewRootImpl);
    }

    void onPostWindowFocus(android.view.View view, boolean z, android.view.WindowManager.LayoutParams layoutParams) {
        if (!z || !this.mHasImeFocus || isInLocalFocusMode(layoutParams)) {
            return;
        }
        if (view == null) {
            view = this.mViewRootImpl.mView;
        }
        getImmDelegate().onPostWindowGainedFocus(view, layoutParams);
    }

    void onScheduledCheckFocus() {
        getImmDelegate().onScheduledCheckFocus(this.mViewRootImpl);
    }

    void onViewFocusChanged(android.view.View view, boolean z) {
        getImmDelegate().onViewFocusChanged(view, z);
    }

    void onViewDetachedFromWindow(android.view.View view) {
        getImmDelegate().onViewDetachedFromWindow(view, this.mViewRootImpl);
    }

    void onWindowDismissed() {
        getImmDelegate().onWindowDismissed(this.mViewRootImpl);
        this.mHasImeFocus = false;
    }

    private static boolean isInLocalFocusMode(android.view.WindowManager.LayoutParams layoutParams) {
        return (layoutParams.flags & 268435456) != 0;
    }

    int onProcessImeInputStage(java.lang.Object obj, android.view.InputEvent inputEvent, android.view.WindowManager.LayoutParams layoutParams, android.view.inputmethod.InputMethodManager.FinishedInputEventCallback finishedInputEventCallback) {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        if (!this.mHasImeFocus || isInLocalFocusMode(layoutParams) || (inputMethodManager = (android.view.inputmethod.InputMethodManager) this.mViewRootImpl.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class)) == null) {
            return 0;
        }
        return inputMethodManager.dispatchInputEvent(inputEvent, obj, finishedInputEventCallback, this.mViewRootImpl.mHandler);
    }

    boolean hasImeFocus() {
        return this.mHasImeFocus;
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mHasImeFocus);
        protoOutputStream.end(start);
    }
}
