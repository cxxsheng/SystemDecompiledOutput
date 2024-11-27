package android.service.autofill;

/* loaded from: classes3.dex */
public class InlineSuggestionRoot extends android.widget.FrameLayout {
    private static final java.lang.String TAG = "InlineSuggestionRoot";
    private final android.service.autofill.IInlineSuggestionUiCallback mCallback;
    private float mDownX;
    private float mDownY;
    private final int mTouchSlop;

    public InlineSuggestionRoot(android.content.Context context, android.service.autofill.IInlineSuggestionUiCallback iInlineSuggestionUiCallback) {
        super(context);
        this.mCallback = iInlineSuggestionUiCallback;
        this.mTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        setFocusable(false);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x002c  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mDownX = motionEvent.getX();
                this.mDownY = motionEvent.getY();
                float dist = android.util.MathUtils.dist(this.mDownX, this.mDownY, motionEvent.getX(), motionEvent.getY());
                if (((motionEvent.getFlags() & 2) != 0) || dist > this.mTouchSlop) {
                    try {
                        this.mCallback.onTransferTouchFocusToImeWindow(getViewRootImpl().getInputToken(), getContext().getDisplayId());
                        break;
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(TAG, "RemoteException transferring touch focus to IME");
                        break;
                    }
                }
                break;
            case 2:
                float dist2 = android.util.MathUtils.dist(this.mDownX, this.mDownY, motionEvent.getX(), motionEvent.getY());
                if ((motionEvent.getFlags() & 2) != 0) {
                    break;
                }
                this.mCallback.onTransferTouchFocusToImeWindow(getViewRootImpl().getInputToken(), getContext().getDisplayId());
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
