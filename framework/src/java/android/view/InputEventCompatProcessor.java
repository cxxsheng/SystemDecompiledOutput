package android.view;

/* loaded from: classes4.dex */
public class InputEventCompatProcessor {
    protected android.content.Context mContext;
    private java.util.List<android.view.InputEvent> mProcessedEvents = new java.util.ArrayList();
    protected int mTargetSdkVersion;

    public InputEventCompatProcessor(android.content.Context context) {
        this.mContext = context;
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
    }

    public java.util.List<android.view.InputEvent> processInputEventForCompatibility(android.view.InputEvent inputEvent) {
        if (this.mTargetSdkVersion < 23 && (inputEvent instanceof android.view.MotionEvent)) {
            this.mProcessedEvents.clear();
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
            int buttonState = motionEvent.getButtonState();
            int i = (buttonState & 96) >> 4;
            if (i != 0) {
                motionEvent.setButtonState(buttonState | i);
            }
            this.mProcessedEvents.add(motionEvent);
            return this.mProcessedEvents;
        }
        return null;
    }

    public android.view.InputEvent processInputEventBeforeFinish(android.view.InputEvent inputEvent) {
        return inputEvent;
    }
}
