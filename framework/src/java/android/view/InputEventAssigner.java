package android.view;

/* loaded from: classes4.dex */
public class InputEventAssigner {
    private static final java.lang.String TAG = "InputEventAssigner";
    private boolean mHasUnprocessedDown = false;
    private int mDownEventId = 0;

    public void notifyFrameProcessed() {
        this.mHasUnprocessedDown = false;
    }

    public int processEvent(android.view.InputEvent inputEvent) {
        if (inputEvent instanceof android.view.MotionEvent) {
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
            if (motionEvent.isFromSource(4098)) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    this.mHasUnprocessedDown = true;
                    this.mDownEventId = inputEvent.getId();
                }
                if (this.mHasUnprocessedDown && actionMasked == 2) {
                    return this.mDownEventId;
                }
                if (actionMasked == 3 || actionMasked == 1) {
                    this.mHasUnprocessedDown = false;
                }
            }
        }
        return inputEvent.getId();
    }
}
