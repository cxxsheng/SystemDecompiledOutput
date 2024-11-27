package com.android.server.input.debug;

/* loaded from: classes2.dex */
class FocusEventDebugGlobalMonitor extends android.view.InputEventReceiver {
    private final com.android.server.input.debug.FocusEventDebugView mDebugView;

    FocusEventDebugGlobalMonitor(com.android.server.input.debug.FocusEventDebugView focusEventDebugView, com.android.server.input.InputManagerService inputManagerService) {
        super(inputManagerService.monitorInput("FocusEventDebugGlobalMonitor", 0), com.android.server.UiThread.getHandler().getLooper());
        this.mDebugView = focusEventDebugView;
    }

    public void onInputEvent(android.view.InputEvent inputEvent) {
        try {
            if (inputEvent instanceof android.view.MotionEvent) {
                this.mDebugView.reportMotionEvent((android.view.MotionEvent) inputEvent);
            }
        } finally {
            finishInputEvent(inputEvent, false);
        }
    }
}
