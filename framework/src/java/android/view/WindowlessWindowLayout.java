package android.view;

/* loaded from: classes4.dex */
public class WindowlessWindowLayout extends android.view.WindowLayout {
    @Override // android.view.WindowLayout
    public void computeFrames(android.view.WindowManager.LayoutParams layoutParams, android.view.InsetsState insetsState, android.graphics.Rect rect, android.graphics.Rect rect2, int i, int i2, int i3, int i4, float f, android.window.ClientWindowFrames clientWindowFrames) {
        if (clientWindowFrames.attachedFrame == null) {
            clientWindowFrames.frame.set(0, 0, layoutParams.width, layoutParams.height);
            clientWindowFrames.parentFrame.set(clientWindowFrames.frame);
            clientWindowFrames.displayFrame.set(clientWindowFrames.frame);
        } else {
            android.view.Gravity.apply(layoutParams.gravity, calculateLength(layoutParams.width, i2, clientWindowFrames.attachedFrame.width()), calculateLength(layoutParams.height, i3, clientWindowFrames.attachedFrame.height()), clientWindowFrames.attachedFrame, (int) (layoutParams.x + layoutParams.horizontalMargin), (int) (layoutParams.y + layoutParams.verticalMargin), clientWindowFrames.frame);
            clientWindowFrames.displayFrame.set(clientWindowFrames.frame);
            clientWindowFrames.parentFrame.set(clientWindowFrames.attachedFrame);
        }
    }

    private static int calculateLength(int i, int i2, int i3) {
        if (i == -1) {
            return i3;
        }
        if (i == -2) {
            return i2;
        }
        return i;
    }
}
