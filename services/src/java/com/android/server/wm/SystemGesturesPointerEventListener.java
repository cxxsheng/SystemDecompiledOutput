package com.android.server.wm;

/* loaded from: classes3.dex */
class SystemGesturesPointerEventListener implements android.view.WindowManagerPolicyConstants.PointerEventListener {
    private static final boolean DEBUG = false;
    private static final int MAX_FLING_TIME_MILLIS = 5000;
    private static final int MAX_TRACKED_POINTERS = 32;
    private static final int SWIPE_FROM_BOTTOM = 2;
    private static final int SWIPE_FROM_LEFT = 4;
    private static final int SWIPE_FROM_RIGHT = 3;
    private static final int SWIPE_FROM_TOP = 1;
    private static final int SWIPE_NONE = 0;
    private static final long SWIPE_TIMEOUT_MS = 500;
    private static final java.lang.String TAG = "SystemGestures";
    private static final int TRACKPAD_SWIPE_FROM_BOTTOM = 2;
    private static final int TRACKPAD_SWIPE_FROM_LEFT = 4;
    private static final int TRACKPAD_SWIPE_FROM_RIGHT = 3;
    private static final int TRACKPAD_SWIPE_FROM_TOP = 1;
    private static final int TRACKPAD_SWIPE_NONE = 0;
    private static final int UNTRACKED_POINTER = -1;
    private final com.android.server.wm.SystemGesturesPointerEventListener.Callbacks mCallbacks;
    private final android.content.Context mContext;
    private boolean mDebugFireable;
    private int mDisplayCutoutTouchableRegionSize;
    private int mDownPointers;
    private android.view.GestureDetector mGestureDetector;
    private final android.os.Handler mHandler;
    private long mLastFlingTime;
    private boolean mMouseHoveringAtBottom;
    private boolean mMouseHoveringAtLeft;
    private boolean mMouseHoveringAtRight;
    private boolean mMouseHoveringAtTop;
    private int mSwipeDistanceThreshold;
    private boolean mSwipeFireable;
    int screenHeight;
    int screenWidth;
    private final android.graphics.Rect mSwipeStartThreshold = new android.graphics.Rect();
    private final int[] mDownPointerId = new int[32];
    private final float[] mDownX = new float[32];
    private final float[] mDownY = new float[32];
    private final long[] mDownTime = new long[32];

    interface Callbacks {
        void onDebug();

        void onDown();

        void onFling(int i);

        void onMouseHoverAtBottom();

        void onMouseHoverAtLeft();

        void onMouseHoverAtRight();

        void onMouseHoverAtTop();

        void onMouseLeaveFromBottom();

        void onMouseLeaveFromLeft();

        void onMouseLeaveFromRight();

        void onMouseLeaveFromTop();

        void onSwipeFromBottom();

        void onSwipeFromLeft();

        void onSwipeFromRight();

        void onSwipeFromTop();

        void onUpOrCancel();
    }

    SystemGesturesPointerEventListener(android.content.Context context, android.os.Handler handler, com.android.server.wm.SystemGesturesPointerEventListener.Callbacks callbacks) {
        this.mContext = (android.content.Context) checkNull("context", context);
        this.mHandler = handler;
        this.mCallbacks = (com.android.server.wm.SystemGesturesPointerEventListener.Callbacks) checkNull("callbacks", callbacks);
        onConfigurationChanged();
    }

    void onDisplayInfoChanged(android.view.DisplayInfo displayInfo) {
        this.screenWidth = displayInfo.logicalWidth;
        this.screenHeight = displayInfo.logicalHeight;
        onConfigurationChanged();
    }

    void onConfigurationChanged() {
        android.content.res.Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(android.R.dimen.status_bar_height);
        this.mSwipeStartThreshold.set(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        this.mSwipeDistanceThreshold = dimensionPixelSize;
        android.view.DisplayCutout cutout = android.hardware.display.DisplayManagerGlobal.getInstance().getRealDisplay(0).getCutout();
        if (cutout != null) {
            this.mDisplayCutoutTouchableRegionSize = resources.getDimensionPixelSize(android.R.dimen.dialog_list_padding_bottom_no_buttons);
            android.graphics.Rect[] boundingRectsAll = cutout.getBoundingRectsAll();
            if (boundingRectsAll[0] != null) {
                this.mSwipeStartThreshold.left = java.lang.Math.max(this.mSwipeStartThreshold.left, boundingRectsAll[0].width() + this.mDisplayCutoutTouchableRegionSize);
            }
            if (boundingRectsAll[1] != null) {
                this.mSwipeStartThreshold.top = java.lang.Math.max(this.mSwipeStartThreshold.top, boundingRectsAll[1].height() + this.mDisplayCutoutTouchableRegionSize);
            }
            if (boundingRectsAll[2] != null) {
                this.mSwipeStartThreshold.right = java.lang.Math.max(this.mSwipeStartThreshold.right, boundingRectsAll[2].width() + this.mDisplayCutoutTouchableRegionSize);
            }
            if (boundingRectsAll[3] != null) {
                this.mSwipeStartThreshold.bottom = java.lang.Math.max(this.mSwipeStartThreshold.bottom, boundingRectsAll[3].height() + this.mDisplayCutoutTouchableRegionSize);
            }
        }
    }

    private static <T> T checkNull(java.lang.String str, T t) {
        if (t == null) {
            throw new java.lang.IllegalArgumentException(str + " must not be null");
        }
        return t;
    }

    public void systemReady() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.SystemGesturesPointerEventListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.SystemGesturesPointerEventListener.this.lambda$systemReady$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$systemReady$0() {
        int displayId = this.mContext.getDisplayId();
        if (android.hardware.display.DisplayManagerGlobal.getInstance().getDisplayInfo(displayId) == null) {
            android.util.Slog.w(TAG, "Cannot create GestureDetector, display removed:" + displayId);
            return;
        }
        this.mGestureDetector = new android.view.GestureDetector(this.mContext, new com.android.server.wm.SystemGesturesPointerEventListener.FlingGestureDetector(), this.mHandler) { // from class: com.android.server.wm.SystemGesturesPointerEventListener.1
        };
    }

    public void onPointerEvent(android.view.MotionEvent motionEvent) {
        if (this.mGestureDetector != null && motionEvent.isTouchEvent()) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mSwipeFireable = true;
                this.mDebugFireable = true;
                this.mDownPointers = 0;
                captureDown(motionEvent, 0);
                if (this.mMouseHoveringAtLeft) {
                    this.mMouseHoveringAtLeft = false;
                    this.mCallbacks.onMouseLeaveFromLeft();
                }
                if (this.mMouseHoveringAtTop) {
                    this.mMouseHoveringAtTop = false;
                    this.mCallbacks.onMouseLeaveFromTop();
                }
                if (this.mMouseHoveringAtRight) {
                    this.mMouseHoveringAtRight = false;
                    this.mCallbacks.onMouseLeaveFromRight();
                }
                if (this.mMouseHoveringAtBottom) {
                    this.mMouseHoveringAtBottom = false;
                    this.mCallbacks.onMouseLeaveFromBottom();
                }
                this.mCallbacks.onDown();
                break;
            case 1:
            case 3:
                this.mSwipeFireable = false;
                this.mDebugFireable = false;
                this.mCallbacks.onUpOrCancel();
                break;
            case 2:
                if (this.mSwipeFireable) {
                    int detectTrackpadThreeFingerSwipe = detectTrackpadThreeFingerSwipe(motionEvent);
                    this.mSwipeFireable = detectTrackpadThreeFingerSwipe == 0;
                    if (!this.mSwipeFireable) {
                        if (detectTrackpadThreeFingerSwipe == 1) {
                            this.mCallbacks.onSwipeFromTop();
                            break;
                        } else if (detectTrackpadThreeFingerSwipe == 2) {
                            this.mCallbacks.onSwipeFromBottom();
                            break;
                        } else if (detectTrackpadThreeFingerSwipe == 3) {
                            this.mCallbacks.onSwipeFromRight();
                            break;
                        } else if (detectTrackpadThreeFingerSwipe == 4) {
                            this.mCallbacks.onSwipeFromLeft();
                            break;
                        }
                    } else {
                        int detectSwipe = detectSwipe(motionEvent);
                        this.mSwipeFireable = detectSwipe == 0;
                        if (detectSwipe == 1) {
                            this.mCallbacks.onSwipeFromTop();
                            break;
                        } else if (detectSwipe == 2) {
                            this.mCallbacks.onSwipeFromBottom();
                            break;
                        } else if (detectSwipe == 3) {
                            this.mCallbacks.onSwipeFromRight();
                            break;
                        } else if (detectSwipe == 4) {
                            this.mCallbacks.onSwipeFromLeft();
                            break;
                        }
                    }
                }
                break;
            case 5:
                captureDown(motionEvent, motionEvent.getActionIndex());
                if (this.mDebugFireable) {
                    this.mDebugFireable = motionEvent.getPointerCount() < 5;
                    if (!this.mDebugFireable) {
                        this.mCallbacks.onDebug();
                        break;
                    }
                }
                break;
            case 7:
                if (motionEvent.isFromSource(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1)) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (!this.mMouseHoveringAtLeft && x == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        this.mCallbacks.onMouseHoverAtLeft();
                        this.mMouseHoveringAtLeft = true;
                    } else if (this.mMouseHoveringAtLeft && x > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        this.mCallbacks.onMouseLeaveFromLeft();
                        this.mMouseHoveringAtLeft = false;
                    }
                    if (!this.mMouseHoveringAtTop && y == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        this.mCallbacks.onMouseHoverAtTop();
                        this.mMouseHoveringAtTop = true;
                    } else if (this.mMouseHoveringAtTop && y > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                        this.mCallbacks.onMouseLeaveFromTop();
                        this.mMouseHoveringAtTop = false;
                    }
                    if (!this.mMouseHoveringAtRight && x >= this.screenWidth - 1) {
                        this.mCallbacks.onMouseHoverAtRight();
                        this.mMouseHoveringAtRight = true;
                    } else if (this.mMouseHoveringAtRight && x < this.screenWidth - 1) {
                        this.mCallbacks.onMouseLeaveFromRight();
                        this.mMouseHoveringAtRight = false;
                    }
                    if (!this.mMouseHoveringAtBottom && y >= this.screenHeight - 1) {
                        this.mCallbacks.onMouseHoverAtBottom();
                        this.mMouseHoveringAtBottom = true;
                        break;
                    } else if (this.mMouseHoveringAtBottom && y < this.screenHeight - 1) {
                        this.mCallbacks.onMouseLeaveFromBottom();
                        this.mMouseHoveringAtBottom = false;
                        break;
                    }
                }
                break;
        }
    }

    private void captureDown(android.view.MotionEvent motionEvent, int i) {
        int findIndex = findIndex(motionEvent.getPointerId(i));
        if (findIndex != -1) {
            this.mDownX[findIndex] = motionEvent.getX(i);
            this.mDownY[findIndex] = motionEvent.getY(i);
            this.mDownTime[findIndex] = motionEvent.getEventTime();
        }
    }

    protected boolean currentGestureStartedInRegion(android.graphics.Region region) {
        return region.contains((int) this.mDownX[0], (int) this.mDownY[0]);
    }

    private int findIndex(int i) {
        for (int i2 = 0; i2 < this.mDownPointers; i2++) {
            if (this.mDownPointerId[i2] == i) {
                return i2;
            }
        }
        if (this.mDownPointers == 32 || i == -1) {
            return -1;
        }
        int[] iArr = this.mDownPointerId;
        int i3 = this.mDownPointers;
        this.mDownPointers = i3 + 1;
        iArr[i3] = i;
        return this.mDownPointers - 1;
    }

    private int detectTrackpadThreeFingerSwipe(android.view.MotionEvent motionEvent) {
        if (!isTrackpadThreeFingerSwipe(motionEvent)) {
            return 0;
        }
        float x = motionEvent.getX() - this.mDownX[0];
        float y = motionEvent.getY() - this.mDownY[0];
        if (java.lang.Math.abs(x) < java.lang.Math.abs(y)) {
            if (java.lang.Math.abs(y) > this.mSwipeDistanceThreshold) {
                return y > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? 1 : 2;
            }
        } else if (java.lang.Math.abs(x) > this.mSwipeDistanceThreshold) {
            return x > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? 4 : 3;
        }
        return 0;
    }

    private static boolean isTrackpadThreeFingerSwipe(android.view.MotionEvent motionEvent) {
        return motionEvent.getClassification() == 4 && motionEvent.getAxisValue(53) == 3.0f;
    }

    private int detectSwipe(android.view.MotionEvent motionEvent) {
        int historySize = motionEvent.getHistorySize();
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int findIndex = findIndex(motionEvent.getPointerId(i));
            if (findIndex != -1) {
                for (int i2 = 0; i2 < historySize; i2++) {
                    int detectSwipe = detectSwipe(findIndex, motionEvent.getHistoricalEventTime(i2), motionEvent.getHistoricalX(i, i2), motionEvent.getHistoricalY(i, i2));
                    if (detectSwipe != 0) {
                        return detectSwipe;
                    }
                }
                int detectSwipe2 = detectSwipe(findIndex, motionEvent.getEventTime(), motionEvent.getX(i), motionEvent.getY(i));
                if (detectSwipe2 != 0) {
                    return detectSwipe2;
                }
            }
        }
        return 0;
    }

    private int detectSwipe(int i, long j, float f, float f2) {
        float f3 = this.mDownX[i];
        float f4 = this.mDownY[i];
        long j2 = j - this.mDownTime[i];
        if (f4 <= this.mSwipeStartThreshold.top && f2 > this.mSwipeDistanceThreshold + f4 && j2 < 500) {
            return 1;
        }
        if (f4 >= this.screenHeight - this.mSwipeStartThreshold.bottom && f2 < f4 - this.mSwipeDistanceThreshold && j2 < 500) {
            return 2;
        }
        if (f3 >= this.screenWidth - this.mSwipeStartThreshold.right && f < f3 - this.mSwipeDistanceThreshold && j2 < 500) {
            return 3;
        }
        if (f3 <= this.mSwipeStartThreshold.left && f > f3 + this.mSwipeDistanceThreshold && j2 < 500) {
            return 4;
        }
        return 0;
    }

    public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
        java.lang.String str2 = str + "  ";
        printWriter.println(str + TAG + ":");
        printWriter.print(str2);
        printWriter.print("mDisplayCutoutTouchableRegionSize=");
        printWriter.println(this.mDisplayCutoutTouchableRegionSize);
        printWriter.print(str2);
        printWriter.print("mSwipeStartThreshold=");
        printWriter.println(this.mSwipeStartThreshold);
        printWriter.print(str2);
        printWriter.print("mSwipeDistanceThreshold=");
        printWriter.println(this.mSwipeDistanceThreshold);
    }

    private final class FlingGestureDetector extends android.view.GestureDetector.SimpleOnGestureListener {
        private android.widget.OverScroller mOverscroller;

        FlingGestureDetector() {
            this.mOverscroller = new android.widget.OverScroller(com.android.server.wm.SystemGesturesPointerEventListener.this.mContext);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(android.view.MotionEvent motionEvent) {
            if (!this.mOverscroller.isFinished()) {
                this.mOverscroller.forceFinished(true);
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
            this.mOverscroller.computeScrollOffset();
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (com.android.server.wm.SystemGesturesPointerEventListener.this.mLastFlingTime != 0 && uptimeMillis > com.android.server.wm.SystemGesturesPointerEventListener.this.mLastFlingTime + 5000) {
                this.mOverscroller.forceFinished(true);
            }
            this.mOverscroller.fling(0, 0, (int) f, (int) f2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            int duration = this.mOverscroller.getDuration();
            if (duration > 5000) {
                duration = 5000;
            }
            com.android.server.wm.SystemGesturesPointerEventListener.this.mLastFlingTime = uptimeMillis;
            com.android.server.wm.SystemGesturesPointerEventListener.this.mCallbacks.onFling(duration);
            return true;
        }
    }
}
