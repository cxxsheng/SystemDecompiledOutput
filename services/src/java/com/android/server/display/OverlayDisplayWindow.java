package com.android.server.display;

/* loaded from: classes.dex */
final class OverlayDisplayWindow implements com.android.internal.util.DumpUtils.Dump {
    private final android.content.Context mContext;
    private final android.view.Display mDefaultDisplay;
    private int mDensityDpi;
    private final android.hardware.display.DisplayManager mDisplayManager;
    private android.view.GestureDetector mGestureDetector;
    private final int mGravity;
    private int mHeight;
    private final com.android.server.display.OverlayDisplayWindow.Listener mListener;
    private float mLiveTranslationX;
    private float mLiveTranslationY;
    private final java.lang.String mName;
    private android.view.ScaleGestureDetector mScaleGestureDetector;
    private final boolean mSecure;
    private android.view.TextureView mTextureView;
    private java.lang.String mTitle;
    private android.widget.TextView mTitleTextView;
    private int mWidth;
    private android.view.View mWindowContent;
    private final android.view.WindowManager mWindowManager;
    private android.view.WindowManager.LayoutParams mWindowParams;
    private float mWindowScale;
    private boolean mWindowVisible;
    private int mWindowX;
    private int mWindowY;
    private static final java.lang.String TAG = "OverlayDisplayWindow";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private final float INITIAL_SCALE = 0.5f;
    private final float MIN_SCALE = 0.3f;
    private final float MAX_SCALE = 1.0f;
    private final float WINDOW_ALPHA = 0.8f;
    private final boolean DISABLE_MOVE_AND_RESIZE = false;
    private final android.view.DisplayInfo mDefaultDisplayInfo = new android.view.DisplayInfo();
    private float mLiveScale = 1.0f;
    private final android.hardware.display.DisplayManager.DisplayListener mDisplayListener = new android.hardware.display.DisplayManager.DisplayListener() { // from class: com.android.server.display.OverlayDisplayWindow.1
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            if (i == com.android.server.display.OverlayDisplayWindow.this.mDefaultDisplay.getDisplayId()) {
                if (com.android.server.display.OverlayDisplayWindow.this.updateDefaultDisplayInfo()) {
                    com.android.server.display.OverlayDisplayWindow.this.relayout();
                    com.android.server.display.OverlayDisplayWindow.this.mListener.onStateChanged(com.android.server.display.OverlayDisplayWindow.this.mDefaultDisplayInfo.state);
                } else {
                    com.android.server.display.OverlayDisplayWindow.this.dismiss();
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            if (i == com.android.server.display.OverlayDisplayWindow.this.mDefaultDisplay.getDisplayId()) {
                com.android.server.display.OverlayDisplayWindow.this.dismiss();
            }
        }
    };
    private final android.view.TextureView.SurfaceTextureListener mSurfaceTextureListener = new android.view.TextureView.SurfaceTextureListener() { // from class: com.android.server.display.OverlayDisplayWindow.2
        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureAvailable(android.graphics.SurfaceTexture surfaceTexture, int i, int i2) {
            com.android.server.display.OverlayDisplayWindow.this.mListener.onWindowCreated(surfaceTexture, com.android.server.display.OverlayDisplayWindow.this.mDefaultDisplayInfo.getRefreshRate(), com.android.server.display.OverlayDisplayWindow.this.mDefaultDisplayInfo.presentationDeadlineNanos, com.android.server.display.OverlayDisplayWindow.this.mDefaultDisplayInfo.state);
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public boolean onSurfaceTextureDestroyed(android.graphics.SurfaceTexture surfaceTexture) {
            com.android.server.display.OverlayDisplayWindow.this.mListener.onWindowDestroyed();
            return true;
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureSizeChanged(android.graphics.SurfaceTexture surfaceTexture, int i, int i2) {
        }

        @Override // android.view.TextureView.SurfaceTextureListener
        public void onSurfaceTextureUpdated(android.graphics.SurfaceTexture surfaceTexture) {
        }
    };
    private final android.view.View.OnTouchListener mOnTouchListener = new android.view.View.OnTouchListener() { // from class: com.android.server.display.OverlayDisplayWindow.3
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            motionEvent.setLocation(motionEvent.getRawX(), motionEvent.getRawY());
            com.android.server.display.OverlayDisplayWindow.this.mGestureDetector.onTouchEvent(motionEvent);
            com.android.server.display.OverlayDisplayWindow.this.mScaleGestureDetector.onTouchEvent(motionEvent);
            switch (motionEvent.getActionMasked()) {
                case 1:
                case 3:
                    com.android.server.display.OverlayDisplayWindow.this.saveWindowParams();
                    break;
            }
            motionEvent.setLocation(x, y);
            return true;
        }
    };
    private final android.view.GestureDetector.OnGestureListener mOnGestureListener = new android.view.GestureDetector.SimpleOnGestureListener() { // from class: com.android.server.display.OverlayDisplayWindow.4
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
            com.android.server.display.OverlayDisplayWindow.this.mLiveTranslationX -= f;
            com.android.server.display.OverlayDisplayWindow.this.mLiveTranslationY -= f2;
            com.android.server.display.OverlayDisplayWindow.this.relayout();
            return true;
        }
    };
    private final android.view.ScaleGestureDetector.OnScaleGestureListener mOnScaleGestureListener = new android.view.ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.android.server.display.OverlayDisplayWindow.5
        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(android.view.ScaleGestureDetector scaleGestureDetector) {
            com.android.server.display.OverlayDisplayWindow.this.mLiveScale *= scaleGestureDetector.getScaleFactor();
            com.android.server.display.OverlayDisplayWindow.this.relayout();
            return true;
        }
    };

    public interface Listener {
        void onStateChanged(int i);

        void onWindowCreated(android.graphics.SurfaceTexture surfaceTexture, float f, long j, int i);

        void onWindowDestroyed();
    }

    public OverlayDisplayWindow(android.content.Context context, java.lang.String str, int i, int i2, int i3, int i4, boolean z, com.android.server.display.OverlayDisplayWindow.Listener listener) {
        android.view.ThreadedRenderer.disableVsync();
        this.mContext = context;
        this.mName = str;
        this.mGravity = i4;
        this.mSecure = z;
        this.mListener = listener;
        this.mDisplayManager = (android.hardware.display.DisplayManager) context.getSystemService("display");
        this.mWindowManager = (android.view.WindowManager) context.getSystemService("window");
        this.mDefaultDisplay = this.mContext.getDisplay();
        updateDefaultDisplayInfo();
        resize(i, i2, i3, false);
        createWindow();
    }

    public void show() {
        if (!this.mWindowVisible) {
            this.mDisplayManager.registerDisplayListener(this.mDisplayListener, null);
            if (!updateDefaultDisplayInfo()) {
                this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
                return;
            }
            clearLiveState();
            updateWindowParams();
            this.mWindowManager.addView(this.mWindowContent, this.mWindowParams);
            this.mWindowVisible = true;
        }
    }

    public void dismiss() {
        if (this.mWindowVisible) {
            this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
            this.mWindowManager.removeView(this.mWindowContent);
            this.mWindowVisible = false;
        }
    }

    public void resize(int i, int i2, int i3) {
        resize(i, i2, i3, true);
    }

    private void resize(int i, int i2, int i3, boolean z) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mDensityDpi = i3;
        this.mTitle = this.mContext.getResources().getString(android.R.string.device_ownership_relinquished, this.mName, java.lang.Integer.valueOf(this.mWidth), java.lang.Integer.valueOf(this.mHeight), java.lang.Integer.valueOf(this.mDensityDpi));
        if (this.mSecure) {
            this.mTitle += this.mContext.getResources().getString(android.R.string.description_target_unlock_tablet);
        }
        if (z) {
            relayout();
        }
    }

    public void relayout() {
        if (this.mWindowVisible) {
            updateWindowParams();
            this.mWindowManager.updateViewLayout(this.mWindowContent, this.mWindowParams);
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println("mWindowVisible=" + this.mWindowVisible);
        printWriter.println("mWindowX=" + this.mWindowX);
        printWriter.println("mWindowY=" + this.mWindowY);
        printWriter.println("mWindowScale=" + this.mWindowScale);
        printWriter.println("mWindowParams=" + this.mWindowParams);
        if (this.mTextureView != null) {
            printWriter.println("mTextureView.getScaleX()=" + this.mTextureView.getScaleX());
            printWriter.println("mTextureView.getScaleY()=" + this.mTextureView.getScaleY());
        }
        printWriter.println("mLiveTranslationX=" + this.mLiveTranslationX);
        printWriter.println("mLiveTranslationY=" + this.mLiveTranslationY);
        printWriter.println("mLiveScale=" + this.mLiveScale);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateDefaultDisplayInfo() {
        if (!this.mDefaultDisplay.getDisplayInfo(this.mDefaultDisplayInfo)) {
            android.util.Slog.w(TAG, "Cannot show overlay display because there is no default display upon which to show it.");
            return false;
        }
        return true;
    }

    private void createWindow() {
        this.mWindowContent = android.view.LayoutInflater.from(this.mContext).inflate(android.R.layout.number_picker_material, (android.view.ViewGroup) null);
        this.mWindowContent.setOnTouchListener(this.mOnTouchListener);
        this.mTextureView = (android.view.TextureView) this.mWindowContent.findViewById(android.R.id.overflow_menu_presenter);
        this.mTextureView.setPivotX(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        this.mTextureView.setPivotY(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        this.mTextureView.getLayoutParams().width = this.mWidth;
        this.mTextureView.getLayoutParams().height = this.mHeight;
        this.mTextureView.setOpaque(false);
        this.mTextureView.setSurfaceTextureListener(this.mSurfaceTextureListener);
        this.mTitleTextView = (android.widget.TextView) this.mWindowContent.findViewById(android.R.id.overlay);
        this.mTitleTextView.setText(this.mTitle);
        this.mWindowParams = new android.view.WindowManager.LayoutParams(2026);
        this.mWindowParams.flags |= 16778024;
        if (this.mSecure) {
            this.mWindowParams.flags |= 8192;
        }
        this.mWindowParams.privateFlags |= 2;
        this.mWindowParams.alpha = 0.8f;
        this.mWindowParams.gravity = 51;
        this.mWindowParams.setTitle(this.mTitle);
        this.mGestureDetector = new android.view.GestureDetector(this.mContext, this.mOnGestureListener);
        this.mScaleGestureDetector = new android.view.ScaleGestureDetector(this.mContext, this.mOnScaleGestureListener);
        this.mWindowX = (this.mGravity & 3) == 3 ? 0 : this.mDefaultDisplayInfo.logicalWidth;
        this.mWindowY = (this.mGravity & 48) != 48 ? this.mDefaultDisplayInfo.logicalHeight : 0;
        this.mWindowScale = 0.5f;
    }

    private void updateWindowParams() {
        float max = java.lang.Math.max(0.3f, java.lang.Math.min(1.0f, java.lang.Math.min(java.lang.Math.min(this.mWindowScale * this.mLiveScale, this.mDefaultDisplayInfo.logicalWidth / this.mWidth), this.mDefaultDisplayInfo.logicalHeight / this.mHeight)));
        float f = ((max / this.mWindowScale) - 1.0f) * 0.5f;
        int i = (int) (this.mWidth * max);
        int i2 = (int) (this.mHeight * max);
        int i3 = (int) ((this.mWindowX + this.mLiveTranslationX) - (i * f));
        int i4 = (int) ((this.mWindowY + this.mLiveTranslationY) - (i2 * f));
        int max2 = java.lang.Math.max(0, java.lang.Math.min(i3, this.mDefaultDisplayInfo.logicalWidth - i));
        int max3 = java.lang.Math.max(0, java.lang.Math.min(i4, this.mDefaultDisplayInfo.logicalHeight - i2));
        if (DEBUG) {
            android.util.Slog.d(TAG, "updateWindowParams: scale=" + max + ", offsetScale=" + f + ", x=" + max2 + ", y=" + max3 + ", width=" + i + ", height=" + i2);
        }
        this.mTextureView.setScaleX(max);
        this.mTextureView.setScaleY(max);
        this.mWindowParams.x = max2;
        this.mWindowParams.y = max3;
        this.mWindowParams.width = i;
        this.mWindowParams.height = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveWindowParams() {
        this.mWindowX = this.mWindowParams.x;
        this.mWindowY = this.mWindowParams.y;
        this.mWindowScale = this.mTextureView.getScaleX();
        clearLiveState();
    }

    private void clearLiveState() {
        this.mLiveTranslationX = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mLiveTranslationY = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mLiveScale = 1.0f;
    }
}
