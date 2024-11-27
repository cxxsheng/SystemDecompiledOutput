package com.android.server.wm;

/* loaded from: classes3.dex */
public class TaskTapPointerEventListener implements android.view.WindowManagerPolicyConstants.PointerEventListener {
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final com.android.server.wm.WindowManagerService mService;
    private final android.graphics.Region mTouchExcludeRegion = new android.graphics.Region();
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    private int mPointerIconType = 1;

    public TaskTapPointerEventListener(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        if (com.android.input.flags.Flags.removePointerEventTrackingInWm()) {
            throw new java.lang.IllegalStateException("TaskTapPointerEventListener should not be used!");
        }
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
    }

    private void restorePointerIcon(int i, int i2) {
        if (this.mPointerIconType != 1) {
            this.mPointerIconType = 1;
            this.mService.mH.removeMessages(55);
            this.mService.mH.obtainMessage(55, i, i2, this.mDisplayContent).sendToTarget();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onPointerEvent(android.view.MotionEvent motionEvent) {
        int x;
        int y;
        int i;
        switch (motionEvent.getActionMasked()) {
            case 0:
                if (motionEvent.getSource() == 8194) {
                    x = (int) motionEvent.getXCursorPosition();
                    y = (int) motionEvent.getYCursorPosition();
                } else {
                    x = (int) motionEvent.getX();
                    y = (int) motionEvent.getY();
                }
                synchronized (this) {
                    try {
                        if (!this.mTouchExcludeRegion.contains(x, y)) {
                            this.mService.mTaskPositioningController.handleTapOutsideTask(this.mDisplayContent, x, y);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return;
            case 7:
            case 9:
                int x2 = (int) motionEvent.getX();
                int y2 = (int) motionEvent.getY();
                if (this.mTouchExcludeRegion.contains(x2, y2)) {
                    restorePointerIcon(x2, y2);
                    return;
                }
                com.android.server.wm.Task findTaskForResizePoint = this.mDisplayContent.findTaskForResizePoint(x2, y2);
                if (findTaskForResizePoint != null) {
                    findTaskForResizePoint.getDimBounds(this.mTmpRect);
                    if (!this.mTmpRect.isEmpty() && !this.mTmpRect.contains(x2, y2)) {
                        i = 1014;
                        if (x2 < this.mTmpRect.left) {
                            if (y2 < this.mTmpRect.top) {
                                i = 1017;
                            } else if (y2 > this.mTmpRect.bottom) {
                                i = 1016;
                            }
                        } else if (x2 > this.mTmpRect.right) {
                            if (y2 < this.mTmpRect.top) {
                                i = 1016;
                            } else if (y2 > this.mTmpRect.bottom) {
                                i = 1017;
                            }
                        } else if (y2 < this.mTmpRect.top || y2 > this.mTmpRect.bottom) {
                            i = 1015;
                        }
                        if (this.mPointerIconType == i) {
                            this.mPointerIconType = i;
                            if (this.mPointerIconType == 1) {
                                this.mService.mH.removeMessages(55);
                                this.mService.mH.obtainMessage(55, x2, y2, this.mDisplayContent).sendToTarget();
                                return;
                            } else {
                                android.hardware.input.InputManagerGlobal.getInstance().setPointerIconType(this.mPointerIconType);
                                return;
                            }
                        }
                        return;
                    }
                }
                i = 1;
                if (this.mPointerIconType == i) {
                }
                break;
            case 10:
                restorePointerIcon((int) motionEvent.getX(), (int) motionEvent.getY());
                return;
            default:
                return;
        }
    }

    void setTouchExcludeRegion(android.graphics.Region region) {
        synchronized (this) {
            this.mTouchExcludeRegion.set(region);
        }
    }
}
