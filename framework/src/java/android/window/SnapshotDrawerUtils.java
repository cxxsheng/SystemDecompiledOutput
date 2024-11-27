package android.window;

/* loaded from: classes4.dex */
public class SnapshotDrawerUtils {
    static final int FLAG_INHERIT_EXCLUDES = 830922810;
    private static final java.lang.String TAG = "SnapshotDrawerUtils";
    private static final android.graphics.RectF sTmpSnapshotSize = new android.graphics.RectF();
    private static final android.graphics.RectF sTmpDstFrame = new android.graphics.RectF();
    private static final android.graphics.Matrix sSnapshotMatrix = new android.graphics.Matrix();
    private static final float[] sTmpFloat9 = new float[9];
    private static final android.graphics.Paint sBackgroundPaint = new android.graphics.Paint();

    public static class SnapshotSurface {
        private final android.view.SurfaceControl mRootSurface;
        private boolean mSizeMismatch;
        private final android.window.TaskSnapshot mSnapshot;
        private android.window.SnapshotDrawerUtils.SystemBarBackgroundPainter mSystemBarBackgroundPainter;
        private final android.graphics.Rect mTaskBounds;
        private final java.lang.CharSequence mTitle;
        private final android.view.SurfaceControl.Transaction mTransaction = new android.view.SurfaceControl.Transaction();
        private final android.graphics.Rect mFrame = new android.graphics.Rect();
        private final android.graphics.Rect mSystemBarInsets = new android.graphics.Rect();

        public SnapshotSurface(android.view.SurfaceControl surfaceControl, android.window.TaskSnapshot taskSnapshot, java.lang.CharSequence charSequence, android.graphics.Rect rect) {
            this.mRootSurface = surfaceControl;
            this.mSnapshot = taskSnapshot;
            this.mTitle = charSequence;
            this.mTaskBounds = rect;
        }

        void initiateSystemBarPainter(int i, int i2, int i3, android.app.ActivityManager.TaskDescription taskDescription, int i4) {
            this.mSystemBarBackgroundPainter = new android.window.SnapshotDrawerUtils.SystemBarBackgroundPainter(i, i2, i3, taskDescription, 1.0f, i4);
            int backgroundColor = taskDescription.getBackgroundColor();
            android.graphics.Paint paint = android.window.SnapshotDrawerUtils.sBackgroundPaint;
            if (backgroundColor == 0) {
                backgroundColor = -1;
            }
            paint.setColor(backgroundColor);
        }

        void setFrames(android.graphics.Rect rect, android.graphics.Rect rect2) {
            this.mFrame.set(rect);
            this.mSystemBarInsets.set(rect2);
            android.hardware.HardwareBuffer hardwareBuffer = this.mSnapshot.getHardwareBuffer();
            this.mSizeMismatch = (this.mFrame.width() == hardwareBuffer.getWidth() && this.mFrame.height() == hardwareBuffer.getHeight()) ? false : true;
            this.mSystemBarBackgroundPainter.setInsets(rect2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void drawSnapshot(boolean z) {
            android.util.Log.v(android.window.SnapshotDrawerUtils.TAG, "Drawing snapshot surface sizeMismatch=" + this.mSizeMismatch);
            if (this.mSizeMismatch) {
                drawSizeMismatchSnapshot();
            } else {
                drawSizeMatchSnapshot();
            }
            if (this.mSnapshot.getHardwareBuffer() != null) {
                this.mSnapshot.getHardwareBuffer().close();
            }
            if (z) {
                this.mRootSurface.release();
            }
        }

        private void drawSizeMatchSnapshot() {
            this.mTransaction.setBuffer(this.mRootSurface, this.mSnapshot.getHardwareBuffer()).setColorSpace(this.mRootSurface, this.mSnapshot.getColorSpace()).apply();
        }

        private void drawSizeMismatchSnapshot() {
            android.graphics.Rect rect;
            android.graphics.Rect calculateSnapshotCrop;
            android.hardware.HardwareBuffer hardwareBuffer = this.mSnapshot.getHardwareBuffer();
            android.view.SurfaceSession surfaceSession = new android.view.SurfaceSession();
            boolean z = !android.window.SnapshotDrawerUtils.isAspectRatioMatch(this.mFrame, this.mSnapshot);
            android.view.SurfaceControl build = new android.view.SurfaceControl.Builder(surfaceSession).setName(((java.lang.Object) this.mTitle) + " - task-snapshot-surface").setBLASTLayer().setFormat(hardwareBuffer.getFormat()).setParent(this.mRootSurface).setCallsite("TaskSnapshotWindow.drawSizeMismatchSnapshot").build();
            this.mTransaction.show(build);
            if (z) {
                android.graphics.Rect letterboxInsets = this.mSnapshot.getLetterboxInsets();
                if (letterboxInsets.left == 0 && letterboxInsets.top == 0 && letterboxInsets.right == 0 && letterboxInsets.bottom == 0) {
                    calculateSnapshotCrop = null;
                } else {
                    calculateSnapshotCrop = calculateSnapshotCrop(letterboxInsets);
                    z = !android.window.SnapshotDrawerUtils.isAspectRatioMatch(this.mFrame, calculateSnapshotCrop);
                }
                if (z) {
                    calculateSnapshotCrop = calculateSnapshotCrop(this.mSnapshot.getContentInsets());
                }
                rect = calculateSnapshotFrame(calculateSnapshotCrop);
                this.mTransaction.setWindowCrop(build, calculateSnapshotCrop);
                this.mTransaction.setPosition(build, rect.left, rect.top);
                android.window.SnapshotDrawerUtils.sTmpSnapshotSize.set(calculateSnapshotCrop);
                android.window.SnapshotDrawerUtils.sTmpDstFrame.set(rect);
            } else {
                android.window.SnapshotDrawerUtils.sTmpSnapshotSize.set(0.0f, 0.0f, hardwareBuffer.getWidth(), hardwareBuffer.getHeight());
                android.window.SnapshotDrawerUtils.sTmpDstFrame.set(this.mFrame);
                android.window.SnapshotDrawerUtils.sTmpDstFrame.offsetTo(0.0f, 0.0f);
                rect = null;
            }
            android.window.SnapshotDrawerUtils.sSnapshotMatrix.setRectToRect(android.window.SnapshotDrawerUtils.sTmpSnapshotSize, android.window.SnapshotDrawerUtils.sTmpDstFrame, android.graphics.Matrix.ScaleToFit.FILL);
            this.mTransaction.setMatrix(build, android.window.SnapshotDrawerUtils.sSnapshotMatrix, android.window.SnapshotDrawerUtils.sTmpFloat9);
            this.mTransaction.setColorSpace(build, this.mSnapshot.getColorSpace());
            this.mTransaction.setBuffer(build, this.mSnapshot.getHardwareBuffer());
            if (z) {
                android.graphics.GraphicBuffer create = android.graphics.GraphicBuffer.create(this.mFrame.width(), this.mFrame.height(), 1, 2336);
                android.graphics.Canvas lockCanvas = create != null ? create.lockCanvas() : null;
                if (lockCanvas == null) {
                    android.util.Log.e(android.window.SnapshotDrawerUtils.TAG, "Unable to draw snapshot: failed to allocate graphic buffer for " + ((java.lang.Object) this.mTitle));
                    this.mTransaction.clear();
                    build.release();
                    return;
                } else {
                    drawBackgroundAndBars(lockCanvas, rect);
                    create.unlockCanvasAndPost(lockCanvas);
                    this.mTransaction.setBuffer(this.mRootSurface, android.hardware.HardwareBuffer.createFromGraphicBuffer(create));
                }
            }
            this.mTransaction.apply();
            build.release();
        }

        android.graphics.Rect calculateSnapshotCrop(android.graphics.Rect rect) {
            android.graphics.Rect rect2 = new android.graphics.Rect();
            android.hardware.HardwareBuffer hardwareBuffer = this.mSnapshot.getHardwareBuffer();
            rect2.set(0, 0, hardwareBuffer.getWidth(), hardwareBuffer.getHeight());
            float width = hardwareBuffer.getWidth() / this.mSnapshot.getTaskSize().x;
            float height = hardwareBuffer.getHeight() / this.mSnapshot.getTaskSize().y;
            rect2.inset((int) (rect.left * width), this.mTaskBounds.top == 0 && this.mFrame.top == 0 ? 0 : (int) (rect.top * height), (int) (rect.right * width), (int) (rect.bottom * height));
            return rect2;
        }

        android.graphics.Rect calculateSnapshotFrame(android.graphics.Rect rect) {
            android.hardware.HardwareBuffer hardwareBuffer = this.mSnapshot.getHardwareBuffer();
            android.graphics.Rect rect2 = new android.graphics.Rect(0, 0, (int) ((rect.width() / (hardwareBuffer.getWidth() / this.mSnapshot.getTaskSize().x)) + 0.5f), (int) ((rect.height() / (hardwareBuffer.getHeight() / this.mSnapshot.getTaskSize().y)) + 0.5f));
            rect2.offset(this.mSystemBarInsets.left, 0);
            return rect2;
        }

        void drawBackgroundAndBars(android.graphics.Canvas canvas, android.graphics.Rect rect) {
            int statusBarColorViewHeight = this.mSystemBarBackgroundPainter.getStatusBarColorViewHeight();
            boolean z = canvas.getWidth() > rect.right;
            boolean z2 = canvas.getHeight() > rect.bottom;
            if (z) {
                canvas.drawRect(rect.right, android.graphics.Color.alpha(this.mSystemBarBackgroundPainter.mStatusBarColor) == 255 ? statusBarColorViewHeight : 0.0f, canvas.getWidth(), z2 ? rect.bottom : canvas.getHeight(), android.window.SnapshotDrawerUtils.sBackgroundPaint);
            }
            if (z2) {
                canvas.drawRect(0.0f, rect.bottom, canvas.getWidth(), canvas.getHeight(), android.window.SnapshotDrawerUtils.sBackgroundPaint);
            }
            this.mSystemBarBackgroundPainter.drawDecors(canvas, rect);
        }

        void drawStatusBarBackground(android.graphics.Canvas canvas, android.graphics.Rect rect) {
            this.mSystemBarBackgroundPainter.drawStatusBarBackground(canvas, rect, this.mSystemBarBackgroundPainter.getStatusBarColorViewHeight());
        }

        void drawNavigationBarBackground(android.graphics.Canvas canvas) {
            this.mSystemBarBackgroundPainter.drawNavigationBarBackground(canvas);
        }
    }

    public static boolean isAspectRatioMatch(android.graphics.Rect rect, android.window.TaskSnapshot taskSnapshot) {
        if (rect.isEmpty()) {
            return false;
        }
        android.hardware.HardwareBuffer hardwareBuffer = taskSnapshot.getHardwareBuffer();
        return java.lang.Math.abs((((float) hardwareBuffer.getWidth()) / ((float) hardwareBuffer.getHeight())) - (((float) rect.width()) / ((float) rect.height()))) <= 0.01f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAspectRatioMatch(android.graphics.Rect rect, android.graphics.Rect rect2) {
        return (rect.isEmpty() || rect2.isEmpty() || java.lang.Math.abs((((float) rect2.width()) / ((float) rect2.height())) - (((float) rect.width()) / ((float) rect.height()))) > 0.01f) ? false : true;
    }

    public static android.app.ActivityManager.TaskDescription getOrCreateTaskDescription(android.app.ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo.taskDescription != null) {
            return runningTaskInfo.taskDescription;
        }
        android.app.ActivityManager.TaskDescription taskDescription = new android.app.ActivityManager.TaskDescription();
        taskDescription.setBackgroundColor(-1);
        return taskDescription;
    }

    public static void drawSnapshotOnSurface(android.window.StartingWindowInfo startingWindowInfo, android.view.WindowManager.LayoutParams layoutParams, android.view.SurfaceControl surfaceControl, android.window.TaskSnapshot taskSnapshot, android.graphics.Rect rect, android.graphics.Rect rect2, android.view.InsetsState insetsState, boolean z) {
        if (rect2.isEmpty()) {
            android.util.Log.e(TAG, "Unable to draw snapshot on an empty windowBounds");
            return;
        }
        android.window.SnapshotDrawerUtils.SnapshotSurface snapshotSurface = new android.window.SnapshotDrawerUtils.SnapshotSurface(surfaceControl, taskSnapshot, layoutParams.getTitle(), rect);
        android.view.WindowManager.LayoutParams layoutParams2 = startingWindowInfo.topOpaqueWindowLayoutParams;
        snapshotSurface.initiateSystemBarPainter(layoutParams.flags, layoutParams.privateFlags, layoutParams2.insetsFlags.appearance, getOrCreateTaskDescription(startingWindowInfo.taskInfo), startingWindowInfo.requestedVisibleTypes);
        snapshotSurface.setFrames(rect2, getSystemBarInsets(rect2, insetsState));
        snapshotSurface.drawSnapshot(z);
    }

    public static android.view.WindowManager.LayoutParams createLayoutParameters(android.window.StartingWindowInfo startingWindowInfo, java.lang.CharSequence charSequence, int i, int i2, android.os.IBinder iBinder) {
        android.view.WindowManager.LayoutParams layoutParams = startingWindowInfo.topOpaqueWindowLayoutParams;
        android.view.WindowManager.LayoutParams layoutParams2 = startingWindowInfo.mainWindowLayoutParams;
        android.view.InsetsState insetsState = startingWindowInfo.topOpaqueWindowInsetsState;
        if (layoutParams == null || layoutParams2 == null || insetsState == null) {
            android.util.Log.w(TAG, "unable to create taskSnapshot surface ");
            return null;
        }
        android.view.WindowManager.LayoutParams layoutParams3 = new android.view.WindowManager.LayoutParams();
        int i3 = layoutParams.insetsFlags.appearance;
        int i4 = layoutParams.flags;
        int i5 = layoutParams.privateFlags;
        layoutParams3.packageName = layoutParams2.packageName;
        layoutParams3.windowAnimations = layoutParams2.windowAnimations;
        layoutParams3.dimAmount = layoutParams2.dimAmount;
        layoutParams3.type = i;
        layoutParams3.format = i2;
        layoutParams3.flags = ((-830922811) & i4) | 8 | 16;
        layoutParams3.privateFlags = (32768 & i5) | 536870912;
        layoutParams3.token = iBinder;
        layoutParams3.width = -1;
        layoutParams3.height = -1;
        layoutParams3.insetsFlags.appearance = i3;
        layoutParams3.insetsFlags.behavior = layoutParams.insetsFlags.behavior;
        layoutParams3.layoutInDisplayCutoutMode = layoutParams.layoutInDisplayCutoutMode;
        layoutParams3.setFitInsetsTypes(layoutParams.getFitInsetsTypes());
        layoutParams3.setFitInsetsSides(layoutParams.getFitInsetsSides());
        layoutParams3.setFitInsetsIgnoringVisibility(layoutParams.isFitInsetsIgnoringVisibility());
        layoutParams3.setTitle(charSequence);
        layoutParams3.inputFeatures |= 1;
        return layoutParams3;
    }

    static android.graphics.Rect getSystemBarInsets(android.graphics.Rect rect, android.view.InsetsState insetsState) {
        return insetsState.calculateInsets(rect, android.view.WindowInsets.Type.systemBars(), false).toRect();
    }

    public static class SystemBarBackgroundPainter {
        private final int mNavigationBarColor;
        private final int mRequestedVisibleTypes;
        private final float mScale;
        private final int mStatusBarColor;
        private final int mWindowFlags;
        private final int mWindowPrivateFlags;
        private final android.graphics.Paint mStatusBarPaint = new android.graphics.Paint();
        private final android.graphics.Paint mNavigationBarPaint = new android.graphics.Paint();
        private final android.graphics.Rect mSystemBarInsets = new android.graphics.Rect();

        public SystemBarBackgroundPainter(int i, int i2, int i3, android.app.ActivityManager.TaskDescription taskDescription, float f, int i4) {
            this.mWindowFlags = i;
            this.mWindowPrivateFlags = i2;
            this.mScale = f;
            android.app.ContextImpl systemUiContext = android.app.ActivityThread.currentActivityThread().getSystemUiContext();
            int color = systemUiContext.getColor(com.android.internal.R.color.system_bar_background_semi_transparent);
            this.mStatusBarColor = com.android.internal.policy.DecorView.calculateBarColor(i, 67108864, color, taskDescription.getStatusBarColor(), i3, 8, taskDescription.getEnsureStatusBarContrastWhenTransparent());
            this.mNavigationBarColor = com.android.internal.policy.DecorView.calculateBarColor(i, 134217728, color, taskDescription.getNavigationBarColor(), i3, 16, taskDescription.getEnsureNavigationBarContrastWhenTransparent() && systemUiContext.getResources().getBoolean(com.android.internal.R.bool.config_navBarNeedsScrim));
            this.mStatusBarPaint.setColor(this.mStatusBarColor);
            this.mNavigationBarPaint.setColor(this.mNavigationBarColor);
            this.mRequestedVisibleTypes = i4;
        }

        public void setInsets(android.graphics.Rect rect) {
            this.mSystemBarInsets.set(rect);
        }

        int getStatusBarColorViewHeight() {
            if (com.android.internal.policy.DecorView.STATUS_BAR_COLOR_VIEW_ATTRIBUTES.isVisible(this.mRequestedVisibleTypes, this.mStatusBarColor, this.mWindowFlags, (this.mWindowPrivateFlags & 32768) != 0)) {
                return (int) (this.mSystemBarInsets.top * this.mScale);
            }
            return 0;
        }

        private boolean isNavigationBarColorViewVisible() {
            return com.android.internal.policy.DecorView.NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES.isVisible(this.mRequestedVisibleTypes, this.mNavigationBarColor, this.mWindowFlags, (this.mWindowPrivateFlags & 32768) != 0);
        }

        public void drawDecors(android.graphics.Canvas canvas, android.graphics.Rect rect) {
            drawStatusBarBackground(canvas, rect, getStatusBarColorViewHeight());
            drawNavigationBarBackground(canvas);
        }

        void drawStatusBarBackground(android.graphics.Canvas canvas, android.graphics.Rect rect, int i) {
            if (i > 0 && android.graphics.Color.alpha(this.mStatusBarColor) != 0) {
                if (rect == null || canvas.getWidth() > rect.right) {
                    canvas.drawRect(rect != null ? rect.right : 0, 0.0f, canvas.getWidth() - ((int) (this.mSystemBarInsets.right * this.mScale)), i, this.mStatusBarPaint);
                }
            }
        }

        void drawNavigationBarBackground(android.graphics.Canvas canvas) {
            android.graphics.Rect rect = new android.graphics.Rect();
            com.android.internal.policy.DecorView.getNavigationBarRect(canvas.getWidth(), canvas.getHeight(), this.mSystemBarInsets, rect, this.mScale);
            if (isNavigationBarColorViewVisible() && android.graphics.Color.alpha(this.mNavigationBarColor) != 0 && !rect.isEmpty()) {
                canvas.drawRect(rect, this.mNavigationBarPaint);
            }
        }
    }
}
