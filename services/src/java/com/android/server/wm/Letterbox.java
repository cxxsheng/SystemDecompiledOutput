package com.android.server.wm;

/* loaded from: classes3.dex */
public class Letterbox {
    static final android.graphics.Rect EMPTY_RECT = new android.graphics.Rect();
    private static final android.graphics.Point ZERO_POINT = new android.graphics.Point(0, 0);
    private final java.util.function.BooleanSupplier mAreCornersRounded;
    private final java.util.function.IntSupplier mBlurRadiusSupplier;
    private final java.util.function.Supplier<android.graphics.Color> mColorSupplier;
    private final java.util.function.DoubleSupplier mDarkScrimAlphaSupplier;
    private final java.util.function.IntConsumer mDoubleTapCallbackX;
    private final java.util.function.IntConsumer mDoubleTapCallbackY;
    private final java.util.function.BooleanSupplier mHasWallpaperBackgroundSupplier;
    private final java.util.function.Supplier<android.view.SurfaceControl> mParentSurfaceSupplier;
    private final java.util.function.Supplier<android.view.SurfaceControl.Builder> mSurfaceControlFactory;
    private final java.util.function.Supplier<android.view.SurfaceControl.Transaction> mTransactionFactory;
    private final android.graphics.Rect mOuter = new android.graphics.Rect();
    private final android.graphics.Rect mInner = new android.graphics.Rect();
    private final com.android.server.wm.Letterbox.LetterboxSurface mTop = new com.android.server.wm.Letterbox.LetterboxSurface("top");
    private final com.android.server.wm.Letterbox.LetterboxSurface mLeft = new com.android.server.wm.Letterbox.LetterboxSurface("left");
    private final com.android.server.wm.Letterbox.LetterboxSurface mBottom = new com.android.server.wm.Letterbox.LetterboxSurface("bottom");
    private final com.android.server.wm.Letterbox.LetterboxSurface mRight = new com.android.server.wm.Letterbox.LetterboxSurface("right");
    private final com.android.server.wm.Letterbox.LetterboxSurface mFullWindowSurface = new com.android.server.wm.Letterbox.LetterboxSurface("fullWindow");
    private final com.android.server.wm.Letterbox.LetterboxSurface[] mSurfaces = {this.mLeft, this.mTop, this.mRight, this.mBottom};

    public Letterbox(java.util.function.Supplier<android.view.SurfaceControl.Builder> supplier, java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier2, java.util.function.BooleanSupplier booleanSupplier, java.util.function.Supplier<android.graphics.Color> supplier3, java.util.function.BooleanSupplier booleanSupplier2, java.util.function.IntSupplier intSupplier, java.util.function.DoubleSupplier doubleSupplier, java.util.function.IntConsumer intConsumer, java.util.function.IntConsumer intConsumer2, java.util.function.Supplier<android.view.SurfaceControl> supplier4) {
        this.mSurfaceControlFactory = supplier;
        this.mTransactionFactory = supplier2;
        this.mAreCornersRounded = booleanSupplier;
        this.mColorSupplier = supplier3;
        this.mHasWallpaperBackgroundSupplier = booleanSupplier2;
        this.mBlurRadiusSupplier = intSupplier;
        this.mDarkScrimAlphaSupplier = doubleSupplier;
        this.mDoubleTapCallbackX = intConsumer;
        this.mDoubleTapCallbackY = intConsumer2;
        this.mParentSurfaceSupplier = supplier4;
    }

    public void layout(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Point point) {
        this.mOuter.set(rect);
        this.mInner.set(rect2);
        this.mTop.layout(rect.left, rect.top, rect.right, rect2.top, point);
        this.mLeft.layout(rect.left, rect.top, rect2.left, rect.bottom, point);
        this.mBottom.layout(rect.left, rect2.bottom, rect.right, rect.bottom, point);
        this.mRight.layout(rect2.right, rect.top, rect.right, rect.bottom, point);
        this.mFullWindowSurface.layout(rect.left, rect.top, rect.right, rect.bottom, point);
    }

    public android.graphics.Rect getInsets() {
        return new android.graphics.Rect(this.mLeft.getWidth(), this.mTop.getHeight(), this.mRight.getWidth(), this.mBottom.getHeight());
    }

    android.graphics.Rect getInnerFrame() {
        return this.mInner;
    }

    android.graphics.Rect getOuterFrame() {
        return this.mOuter;
    }

    boolean notIntersectsOrFullyContains(android.graphics.Rect rect) {
        int i = 0;
        int i2 = 0;
        for (com.android.server.wm.Letterbox.LetterboxSurface letterboxSurface : this.mSurfaces) {
            android.graphics.Rect rect2 = letterboxSurface.mLayoutFrameGlobal;
            if (rect2.isEmpty()) {
                i++;
            } else if (!android.graphics.Rect.intersects(rect2, rect)) {
                i2++;
            } else if (rect2.contains(rect)) {
                return true;
            }
        }
        return i + i2 == this.mSurfaces.length;
    }

    public void hide() {
        layout(EMPTY_RECT, EMPTY_RECT, ZERO_POINT);
    }

    public void destroy() {
        this.mOuter.setEmpty();
        this.mInner.setEmpty();
        for (com.android.server.wm.Letterbox.LetterboxSurface letterboxSurface : this.mSurfaces) {
            letterboxSurface.remove();
        }
        this.mFullWindowSurface.remove();
    }

    public boolean needsApplySurfaceChanges() {
        if (useFullWindowSurface()) {
            return this.mFullWindowSurface.needsApplySurfaceChanges();
        }
        for (com.android.server.wm.Letterbox.LetterboxSurface letterboxSurface : this.mSurfaces) {
            if (letterboxSurface.needsApplySurfaceChanges()) {
                return true;
            }
        }
        return false;
    }

    public void applySurfaceChanges(android.view.SurfaceControl.Transaction transaction) {
        int i = 0;
        if (useFullWindowSurface()) {
            this.mFullWindowSurface.applySurfaceChanges(transaction);
            com.android.server.wm.Letterbox.LetterboxSurface[] letterboxSurfaceArr = this.mSurfaces;
            int length = letterboxSurfaceArr.length;
            while (i < length) {
                letterboxSurfaceArr[i].remove();
                i++;
            }
            return;
        }
        com.android.server.wm.Letterbox.LetterboxSurface[] letterboxSurfaceArr2 = this.mSurfaces;
        int length2 = letterboxSurfaceArr2.length;
        while (i < length2) {
            letterboxSurfaceArr2[i].applySurfaceChanges(transaction);
            i++;
        }
        this.mFullWindowSurface.remove();
    }

    void attachInput(com.android.server.wm.WindowState windowState) {
        if (useFullWindowSurface()) {
            this.mFullWindowSurface.attachInput(windowState);
            return;
        }
        for (com.android.server.wm.Letterbox.LetterboxSurface letterboxSurface : this.mSurfaces) {
            letterboxSurface.attachInput(windowState);
        }
    }

    void onMovedToDisplay(int i) {
        for (com.android.server.wm.Letterbox.LetterboxSurface letterboxSurface : this.mSurfaces) {
            if (letterboxSurface.mInputInterceptor != null) {
                letterboxSurface.mInputInterceptor.mWindowHandle.displayId = i;
            }
        }
        if (this.mFullWindowSurface.mInputInterceptor != null) {
            this.mFullWindowSurface.mInputInterceptor.mWindowHandle.displayId = i;
        }
    }

    private boolean useFullWindowSurface() {
        return this.mAreCornersRounded.getAsBoolean() || this.mHasWallpaperBackgroundSupplier.getAsBoolean();
    }

    private final class TapEventReceiver extends android.view.InputEventReceiver {
        private final android.view.GestureDetector mDoubleTapDetector;
        private final com.android.server.wm.Letterbox.DoubleTapListener mDoubleTapListener;

        TapEventReceiver(android.view.InputChannel inputChannel, com.android.server.wm.WindowManagerService windowManagerService, android.os.Handler handler) {
            super(inputChannel, handler.getLooper());
            this.mDoubleTapListener = new com.android.server.wm.Letterbox.DoubleTapListener(windowManagerService);
            this.mDoubleTapDetector = new android.view.GestureDetector(windowManagerService.mContext, this.mDoubleTapListener, handler);
        }

        public void onInputEvent(android.view.InputEvent inputEvent) {
            finishInputEvent(inputEvent, this.mDoubleTapDetector.onTouchEvent((android.view.MotionEvent) inputEvent));
        }
    }

    private class DoubleTapListener extends android.view.GestureDetector.SimpleOnGestureListener {
        private final com.android.server.wm.WindowManagerService mWmService;

        private DoubleTapListener(com.android.server.wm.WindowManagerService windowManagerService) {
            this.mWmService = windowManagerService;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTapEvent(android.view.MotionEvent motionEvent) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (com.android.server.wm.Letterbox.this.mOuter.isEmpty() || motionEvent.getAction() != 1) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return false;
                    }
                    com.android.server.wm.Letterbox.this.mDoubleTapCallbackX.accept((int) motionEvent.getRawX());
                    com.android.server.wm.Letterbox.this.mDoubleTapCallbackY.accept((int) motionEvent.getRawY());
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return true;
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    private final class InputInterceptor implements java.lang.Runnable {
        private final android.view.InputChannel mClientChannel;
        private final android.os.Handler mHandler = com.android.server.UiThread.getHandler();
        private final android.view.InputEventReceiver mInputEventReceiver;
        private final android.os.IBinder mToken;
        private final android.view.InputWindowHandle mWindowHandle;
        private final com.android.server.wm.WindowManagerService mWmService;

        InputInterceptor(java.lang.String str, com.android.server.wm.WindowState windowState) {
            this.mWmService = windowState.mWmService;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str);
            sb.append(windowState.mActivityRecord != null ? windowState.mActivityRecord : windowState);
            java.lang.String sb2 = sb.toString();
            this.mClientChannel = this.mWmService.mInputManager.createInputChannel(sb2);
            this.mInputEventReceiver = com.android.server.wm.Letterbox.this.new TapEventReceiver(this.mClientChannel, this.mWmService, this.mHandler);
            this.mToken = this.mClientChannel.getToken();
            this.mWindowHandle = new android.view.InputWindowHandle((android.view.InputApplicationHandle) null, windowState.getDisplayId());
            this.mWindowHandle.name = sb2;
            this.mWindowHandle.token = this.mToken;
            this.mWindowHandle.layoutParamsType = 2022;
            this.mWindowHandle.dispatchingTimeoutMillis = android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
            this.mWindowHandle.ownerPid = com.android.server.wm.WindowManagerService.MY_PID;
            this.mWindowHandle.ownerUid = com.android.server.wm.WindowManagerService.MY_UID;
            this.mWindowHandle.scaleFactor = 1.0f;
            this.mWindowHandle.inputConfig = com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE_SUPRESS;
        }

        void updateTouchableRegion(android.graphics.Rect rect) {
            if (rect.isEmpty()) {
                this.mWindowHandle.token = null;
                return;
            }
            this.mWindowHandle.token = this.mToken;
            this.mWindowHandle.touchableRegion.set(rect);
            this.mWindowHandle.touchableRegion.translate(-rect.left, -rect.top);
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mInputEventReceiver.dispose();
            this.mClientChannel.dispose();
        }

        void dispose() {
            this.mWmService.mInputManager.removeInputChannel(this.mToken);
            this.mHandler.post(this);
        }
    }

    private class LetterboxSurface {
        private android.graphics.Color mColor;
        private boolean mHasWallpaperBackground;
        private com.android.server.wm.Letterbox.InputInterceptor mInputInterceptor;
        private android.view.SurfaceControl mParentSurface;
        private android.view.SurfaceControl mSurface;
        private final java.lang.String mType;
        private final android.graphics.Rect mSurfaceFrameRelative = new android.graphics.Rect();
        private final android.graphics.Rect mLayoutFrameGlobal = new android.graphics.Rect();
        private final android.graphics.Rect mLayoutFrameRelative = new android.graphics.Rect();

        public LetterboxSurface(java.lang.String str) {
            this.mType = str;
        }

        public void layout(int i, int i2, int i3, int i4, android.graphics.Point point) {
            this.mLayoutFrameGlobal.set(i, i2, i3, i4);
            this.mLayoutFrameRelative.set(this.mLayoutFrameGlobal);
            this.mLayoutFrameRelative.offset(-point.x, -point.y);
        }

        private void createSurface(android.view.SurfaceControl.Transaction transaction) {
            this.mSurface = ((android.view.SurfaceControl.Builder) com.android.server.wm.Letterbox.this.mSurfaceControlFactory.get()).setName("Letterbox - " + this.mType).setFlags(4).setColorLayer().setCallsite("LetterboxSurface.createSurface").build();
            transaction.setLayer(this.mSurface, -20000).setColorSpaceAgnostic(this.mSurface, true);
        }

        void attachInput(com.android.server.wm.WindowState windowState) {
            if (this.mInputInterceptor != null) {
                this.mInputInterceptor.dispose();
            }
            this.mInputInterceptor = com.android.server.wm.Letterbox.this.new InputInterceptor("Letterbox_" + this.mType + "_", windowState);
        }

        boolean isRemoved() {
            return (this.mSurface == null && this.mInputInterceptor == null) ? false : true;
        }

        public void remove() {
            if (this.mSurface != null) {
                ((android.view.SurfaceControl.Transaction) com.android.server.wm.Letterbox.this.mTransactionFactory.get()).remove(this.mSurface).apply();
                this.mSurface = null;
            }
            if (this.mInputInterceptor != null) {
                this.mInputInterceptor.dispose();
                this.mInputInterceptor = null;
            }
        }

        public int getWidth() {
            return java.lang.Math.max(0, this.mLayoutFrameGlobal.width());
        }

        public int getHeight() {
            return java.lang.Math.max(0, this.mLayoutFrameGlobal.height());
        }

        public void applySurfaceChanges(android.view.SurfaceControl.Transaction transaction) {
            if (!needsApplySurfaceChanges()) {
                return;
            }
            this.mSurfaceFrameRelative.set(this.mLayoutFrameRelative);
            if (!this.mSurfaceFrameRelative.isEmpty()) {
                if (this.mSurface == null) {
                    createSurface(transaction);
                }
                this.mColor = (android.graphics.Color) com.android.server.wm.Letterbox.this.mColorSupplier.get();
                this.mParentSurface = (android.view.SurfaceControl) com.android.server.wm.Letterbox.this.mParentSurfaceSupplier.get();
                transaction.setColor(this.mSurface, getRgbColorArray());
                transaction.setPosition(this.mSurface, this.mSurfaceFrameRelative.left, this.mSurfaceFrameRelative.top);
                transaction.setWindowCrop(this.mSurface, this.mSurfaceFrameRelative.width(), this.mSurfaceFrameRelative.height());
                transaction.reparent(this.mSurface, this.mParentSurface);
                this.mHasWallpaperBackground = com.android.server.wm.Letterbox.this.mHasWallpaperBackgroundSupplier.getAsBoolean();
                updateAlphaAndBlur(transaction);
                transaction.show(this.mSurface);
            } else if (this.mSurface != null) {
                transaction.hide(this.mSurface);
            }
            if (this.mSurface != null && this.mInputInterceptor != null) {
                this.mInputInterceptor.updateTouchableRegion(this.mSurfaceFrameRelative);
                transaction.setInputWindowInfo(this.mSurface, this.mInputInterceptor.mWindowHandle);
            }
        }

        private void updateAlphaAndBlur(android.view.SurfaceControl.Transaction transaction) {
            if (!this.mHasWallpaperBackground) {
                transaction.setAlpha(this.mSurface, 1.0f);
                transaction.setBackgroundBlurRadius(this.mSurface, 0);
                return;
            }
            transaction.setAlpha(this.mSurface, (float) com.android.server.wm.Letterbox.this.mDarkScrimAlphaSupplier.getAsDouble());
            if (com.android.server.wm.Letterbox.this.mBlurRadiusSupplier.getAsInt() <= 0) {
                transaction.setBackgroundBlurRadius(this.mSurface, 0);
            } else {
                transaction.setBackgroundBlurRadius(this.mSurface, com.android.server.wm.Letterbox.this.mBlurRadiusSupplier.getAsInt());
            }
        }

        private float[] getRgbColorArray() {
            return new float[]{this.mColor.red(), this.mColor.green(), this.mColor.blue()};
        }

        public boolean needsApplySurfaceChanges() {
            return (this.mSurfaceFrameRelative.equals(this.mLayoutFrameRelative) && (this.mSurfaceFrameRelative.isEmpty() || (com.android.server.wm.Letterbox.this.mHasWallpaperBackgroundSupplier.getAsBoolean() == this.mHasWallpaperBackground && ((android.graphics.Color) com.android.server.wm.Letterbox.this.mColorSupplier.get()).equals(this.mColor) && com.android.server.wm.Letterbox.this.mParentSurfaceSupplier.get() == this.mParentSurface))) ? false : true;
        }
    }
}
