package com.android.server.wm;

/* loaded from: classes3.dex */
class DragInputEventReceiver extends android.view.InputEventReceiver {
    private final com.android.server.wm.DragDropController mDragDropController;
    private boolean mIsStartEvent;
    private boolean mMuteInput;
    private boolean mStylusButtonDownAtStart;

    DragInputEventReceiver(android.view.InputChannel inputChannel, android.os.Looper looper, com.android.server.wm.DragDropController dragDropController) {
        super(inputChannel, looper);
        this.mIsStartEvent = true;
        this.mMuteInput = false;
        this.mDragDropController = dragDropController;
    }

    public void onInputEvent(android.view.InputEvent inputEvent) {
        try {
            try {
                if (inputEvent instanceof android.view.MotionEvent) {
                    if ((inputEvent.getSource() & 2) != 0 && !this.mMuteInput) {
                        android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
                        float rawX = motionEvent.getRawX();
                        float rawY = motionEvent.getRawY();
                        boolean z = (motionEvent.getButtonState() & 32) != 0;
                        if (this.mIsStartEvent) {
                            this.mStylusButtonDownAtStart = z;
                            this.mIsStartEvent = false;
                        }
                        switch (motionEvent.getAction()) {
                            case 0:
                                android.util.Slog.w("WindowManager", "Unexpected ACTION_DOWN in drag layer");
                                finishInputEvent(inputEvent, false);
                                return;
                            case 1:
                                android.util.Slog.d("WindowManager", "Got UP on move channel; dropping at " + rawX + "," + rawY);
                                this.mMuteInput = true;
                                break;
                            case 2:
                                if (this.mStylusButtonDownAtStart && !z) {
                                    android.util.Slog.d("WindowManager", "Button no longer pressed; dropping at " + rawX + "," + rawY);
                                    this.mMuteInput = true;
                                    break;
                                }
                                break;
                            case 3:
                                android.util.Slog.d("WindowManager", "Drag cancelled!");
                                this.mMuteInput = true;
                                break;
                            default:
                                finishInputEvent(inputEvent, false);
                                return;
                        }
                        this.mDragDropController.handleMotionEvent(!this.mMuteInput, rawX, rawY);
                        finishInputEvent(inputEvent, true);
                    }
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e("WindowManager", "Exception caught by drag handleMotion", e);
                finishInputEvent(inputEvent, false);
            }
        } finally {
            finishInputEvent(inputEvent, false);
        }
    }
}
