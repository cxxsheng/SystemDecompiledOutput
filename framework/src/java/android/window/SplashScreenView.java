package android.window;

/* loaded from: classes4.dex */
public final class SplashScreenView extends android.widget.FrameLayout {
    private android.view.View mBrandingImageView;
    private android.os.RemoteCallback mClientCallback;
    private boolean mHasRemoved;
    private java.time.Duration mIconAnimationDuration;
    private java.time.Instant mIconAnimationStart;
    private android.view.View mIconView;
    private int mInitBackgroundColor;
    private boolean mIsCopied;
    private boolean mNotCopyable;
    private android.graphics.Bitmap mParceledBrandingBitmap;
    private android.graphics.Bitmap mParceledIconBackgroundBitmap;
    private android.graphics.Bitmap mParceledIconBitmap;
    private android.view.SurfaceControlViewHost mSurfaceHost;
    private android.view.SurfaceControlViewHost.SurfacePackage mSurfacePackage;
    private android.view.SurfaceControlViewHost.SurfacePackage mSurfacePackageCopy;
    private android.view.SurfaceView mSurfaceView;
    private final int[] mTmpPos;
    private final android.graphics.Rect mTmpRect;
    private android.view.Window mWindow;
    private static final java.lang.String TAG = android.window.SplashScreenView.class.getSimpleName();
    private static final boolean DEBUG = android.os.Build.IS_DEBUGGABLE;

    public static class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private boolean mAllowHandleSolidColor = true;
        private int mBackgroundColor;
        private android.graphics.drawable.Drawable mBrandingDrawable;
        private int mBrandingImageHeight;
        private int mBrandingImageWidth;
        private android.os.RemoteCallback mClientCallback;
        private final android.content.Context mContext;
        private java.time.Duration mIconAnimationDuration;
        private java.time.Instant mIconAnimationStart;
        private android.graphics.drawable.Drawable mIconBackground;
        private android.graphics.drawable.Drawable mIconDrawable;
        private int mIconSize;
        private android.graphics.drawable.Drawable mOverlayDrawable;
        private android.graphics.Bitmap mParceledBrandingBitmap;
        private android.graphics.Bitmap mParceledIconBackgroundBitmap;
        private android.graphics.Bitmap mParceledIconBitmap;
        private android.view.SurfaceControlViewHost.SurfacePackage mSurfacePackage;
        private java.util.function.Consumer<java.lang.Runnable> mUiThreadInitTask;

        public Builder(android.content.Context context) {
            this.mContext = context;
        }

        public android.window.SplashScreenView.Builder createFromParcel(android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable) {
            this.mIconSize = splashScreenViewParcelable.getIconSize();
            this.mBackgroundColor = splashScreenViewParcelable.getBackgroundColor();
            this.mSurfacePackage = splashScreenViewParcelable.mSurfacePackage;
            if (this.mSurfacePackage == null && splashScreenViewParcelable.mIconBitmap != null) {
                this.mIconDrawable = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), splashScreenViewParcelable.mIconBitmap);
                this.mParceledIconBitmap = splashScreenViewParcelable.mIconBitmap;
            }
            if (splashScreenViewParcelable.mIconBackground != null) {
                this.mIconBackground = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), splashScreenViewParcelable.mIconBackground);
                this.mParceledIconBackgroundBitmap = splashScreenViewParcelable.mIconBackground;
            }
            if (splashScreenViewParcelable.mBrandingBitmap != null) {
                setBrandingDrawable(new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), splashScreenViewParcelable.mBrandingBitmap), splashScreenViewParcelable.mBrandingWidth, splashScreenViewParcelable.mBrandingHeight);
                this.mParceledBrandingBitmap = splashScreenViewParcelable.mBrandingBitmap;
            }
            this.mIconAnimationStart = java.time.Instant.ofEpochMilli(splashScreenViewParcelable.mIconAnimationStartMillis);
            this.mIconAnimationDuration = java.time.Duration.ofMillis(splashScreenViewParcelable.mIconAnimationDurationMillis);
            this.mClientCallback = splashScreenViewParcelable.mClientCallback;
            if (android.window.SplashScreenView.DEBUG) {
                android.util.Log.d(android.window.SplashScreenView.TAG, java.lang.String.format("Building from parcel drawable: %s", this.mIconDrawable));
            }
            return this;
        }

        public android.window.SplashScreenView.Builder setIconSize(int i) {
            this.mIconSize = i;
            return this;
        }

        public android.window.SplashScreenView.Builder setBackgroundColor(int i) {
            this.mBackgroundColor = i;
            return this;
        }

        public android.window.SplashScreenView.Builder setOverlayDrawable(android.graphics.drawable.Drawable drawable) {
            this.mOverlayDrawable = drawable;
            return this;
        }

        public android.window.SplashScreenView.Builder setCenterViewDrawable(android.graphics.drawable.Drawable drawable) {
            this.mIconDrawable = drawable;
            return this;
        }

        public android.window.SplashScreenView.Builder setIconBackground(android.graphics.drawable.Drawable drawable) {
            this.mIconBackground = drawable;
            return this;
        }

        public android.window.SplashScreenView.Builder setUiThreadInitConsumer(java.util.function.Consumer<java.lang.Runnable> consumer) {
            this.mUiThreadInitTask = consumer;
            return this;
        }

        public android.window.SplashScreenView.Builder setBrandingDrawable(android.graphics.drawable.Drawable drawable, int i, int i2) {
            this.mBrandingDrawable = drawable;
            this.mBrandingImageWidth = i;
            this.mBrandingImageHeight = i2;
            return this;
        }

        public android.window.SplashScreenView.Builder setAllowHandleSolidColor(boolean z) {
            this.mAllowHandleSolidColor = z;
            return this;
        }

        public android.window.SplashScreenView build() {
            android.os.Trace.traceBegin(32L, "SplashScreenView#build");
            boolean z = false;
            final android.window.SplashScreenView splashScreenView = (android.window.SplashScreenView) android.view.LayoutInflater.from(this.mContext).inflate(com.android.internal.R.layout.splash_screen_view, (android.view.ViewGroup) null, false);
            splashScreenView.mInitBackgroundColor = this.mBackgroundColor;
            if (this.mOverlayDrawable != null) {
                splashScreenView.setBackground(this.mOverlayDrawable);
            } else {
                splashScreenView.setBackgroundColor(this.mBackgroundColor);
            }
            splashScreenView.mClientCallback = this.mClientCallback;
            splashScreenView.mBrandingImageView = splashScreenView.findViewById(com.android.internal.R.id.splashscreen_branding_view);
            if ((this.mIconDrawable instanceof android.window.SplashScreenView.IconAnimateListener) || this.mSurfacePackage != null) {
                if (this.mUiThreadInitTask != null) {
                    this.mUiThreadInitTask.accept(new java.lang.Runnable() { // from class: android.window.SplashScreenView$Builder$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.window.SplashScreenView.Builder.this.lambda$build$0(splashScreenView);
                        }
                    });
                } else {
                    splashScreenView.mIconView = createSurfaceView(splashScreenView);
                }
                splashScreenView.initIconAnimation(this.mIconDrawable);
                splashScreenView.mIconAnimationStart = this.mIconAnimationStart;
                splashScreenView.mIconAnimationDuration = this.mIconAnimationDuration;
                z = true;
            } else if (this.mIconSize != 0) {
                android.widget.ImageView imageView = (android.widget.ImageView) splashScreenView.findViewById(com.android.internal.R.id.splashscreen_icon_view);
                android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.width = this.mIconSize;
                layoutParams.height = this.mIconSize;
                imageView.setLayoutParams(layoutParams);
                if (this.mIconDrawable != null) {
                    imageView.setImageDrawable(this.mIconDrawable);
                }
                if (this.mIconBackground != null) {
                    imageView.setBackground(this.mIconBackground);
                }
                splashScreenView.mIconView = imageView;
                z = true;
            }
            if (this.mOverlayDrawable != null || (!z && !this.mAllowHandleSolidColor)) {
                splashScreenView.setNotCopyable();
            }
            splashScreenView.mParceledIconBackgroundBitmap = this.mParceledIconBackgroundBitmap;
            splashScreenView.mParceledIconBitmap = this.mParceledIconBitmap;
            if (this.mBrandingImageHeight > 0 && this.mBrandingImageWidth > 0 && this.mBrandingDrawable != null) {
                android.view.ViewGroup.LayoutParams layoutParams2 = splashScreenView.mBrandingImageView.getLayoutParams();
                layoutParams2.width = this.mBrandingImageWidth;
                layoutParams2.height = this.mBrandingImageHeight;
                splashScreenView.mBrandingImageView.setLayoutParams(layoutParams2);
                splashScreenView.mBrandingImageView.setBackground(this.mBrandingDrawable);
            } else {
                splashScreenView.mBrandingImageView.setVisibility(8);
            }
            if (this.mParceledBrandingBitmap != null) {
                splashScreenView.mParceledBrandingBitmap = this.mParceledBrandingBitmap;
            }
            if (android.window.SplashScreenView.DEBUG) {
                android.util.Log.d(android.window.SplashScreenView.TAG, "Build " + splashScreenView + "\nIcon: view: " + splashScreenView.mIconView + " drawable: " + this.mIconDrawable + " size: " + this.mIconSize + "\nBranding: view: " + splashScreenView.mBrandingImageView + " drawable: " + this.mBrandingDrawable + " size w: " + this.mBrandingImageWidth + " h: " + this.mBrandingImageHeight);
            }
            android.os.Trace.traceEnd(32L);
            return splashScreenView;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$build$0(android.window.SplashScreenView splashScreenView) {
            splashScreenView.mIconView = createSurfaceView(splashScreenView);
        }

        private android.view.SurfaceView createSurfaceView(android.window.SplashScreenView splashScreenView) {
            android.os.Trace.traceBegin(32L, "SplashScreenView#createSurfaceView");
            android.content.Context context = splashScreenView.getContext();
            android.view.SurfaceView surfaceView = new android.view.SurfaceView(context);
            surfaceView.setPadding(0, 0, 0, 0);
            surfaceView.setBackground(this.mIconBackground);
            if (this.mSurfacePackage == null) {
                if (android.window.SplashScreenView.DEBUG) {
                    android.util.Log.d(android.window.SplashScreenView.TAG, "SurfaceControlViewHost created on thread " + java.lang.Thread.currentThread().getId());
                }
                android.view.AttachedSurfaceControl rootSurfaceControl = surfaceView.getRootSurfaceControl();
                android.view.SurfaceControlViewHost surfaceControlViewHost = new android.view.SurfaceControlViewHost(context, context.getDisplay(), rootSurfaceControl == null ? null : rootSurfaceControl.getInputTransferToken(), "SplashScreenView");
                android.widget.ImageView imageView = new android.widget.ImageView(context);
                imageView.setBackground(this.mIconDrawable);
                surfaceControlViewHost.setView(imageView, new android.view.WindowManager.LayoutParams(this.mIconSize, this.mIconSize, 2, 131096, -2));
                android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = surfaceControlViewHost.getSurfacePackage();
                surfaceView.setChildSurfacePackage(surfacePackage);
                splashScreenView.mSurfacePackage = surfacePackage;
                splashScreenView.mSurfaceHost = surfaceControlViewHost;
                splashScreenView.mSurfacePackageCopy = new android.view.SurfaceControlViewHost.SurfacePackage(surfacePackage);
            } else {
                if (android.window.SplashScreenView.DEBUG) {
                    android.util.Log.d(android.window.SplashScreenView.TAG, "Using copy of SurfacePackage in the client");
                }
                splashScreenView.mSurfacePackage = this.mSurfacePackage;
            }
            if (this.mIconSize != 0) {
                android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(this.mIconSize, this.mIconSize);
                layoutParams.gravity = 17;
                surfaceView.setLayoutParams(layoutParams);
                if (android.window.SplashScreenView.DEBUG) {
                    android.util.Log.d(android.window.SplashScreenView.TAG, "Icon size " + this.mIconSize);
                }
            }
            surfaceView.setUseAlpha();
            surfaceView.setZOrderOnTop(true);
            surfaceView.getHolder().setFormat(-3);
            splashScreenView.addView(surfaceView);
            splashScreenView.mSurfaceView = surfaceView;
            android.os.Trace.traceEnd(32L);
            return surfaceView;
        }
    }

    public SplashScreenView(android.content.Context context) {
        super(context);
        this.mTmpRect = new android.graphics.Rect();
        this.mTmpPos = new int[2];
    }

    public SplashScreenView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpRect = new android.graphics.Rect();
        this.mTmpPos = new int[2];
    }

    public void setNotCopyable() {
        this.mNotCopyable = true;
    }

    public boolean isCopyable() {
        return !this.mNotCopyable;
    }

    public void onCopied() {
        this.mIsCopied = true;
        if (this.mSurfaceView == null) {
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "Setting SurfaceView's SurfacePackage to null.");
        }
        this.mSurfacePackage.release();
        this.mSurfacePackage = null;
    }

    public android.view.SurfaceControlViewHost getSurfaceHost() {
        return this.mSurfaceHost;
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        super.setAlpha(f);
        if (this.mSurfaceView != null) {
            this.mSurfaceView.setAlpha(this.mSurfaceView.getAlpha() * f);
        }
    }

    public java.time.Duration getIconAnimationDuration() {
        return this.mIconAnimationDuration;
    }

    public java.time.Instant getIconAnimationStart() {
        return this.mIconAnimationStart;
    }

    public void syncTransferSurfaceOnDraw() {
        if (this.mSurfacePackage == null) {
            return;
        }
        if (DEBUG) {
            this.mSurfacePackage.getSurfaceControl().addOnReparentListener(new android.view.SurfaceControl.OnReparentListener() { // from class: android.window.SplashScreenView$$ExternalSyntheticLambda0
                @Override // android.view.SurfaceControl.OnReparentListener
                public final void onReparent(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
                    android.util.Log.e(android.window.SplashScreenView.TAG, java.lang.String.format("SurfacePackage'surface reparented to %s", surfaceControl));
                }
            });
            android.util.Log.d(TAG, "Transferring surface " + this.mSurfaceView.toString());
        }
        this.mSurfaceView.setChildSurfacePackage(this.mSurfacePackage);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void initIconAnimation(android.graphics.drawable.Drawable drawable) {
        if (!(drawable instanceof android.window.SplashScreenView.IconAnimateListener)) {
            return;
        }
        android.window.SplashScreenView.IconAnimateListener iconAnimateListener = (android.window.SplashScreenView.IconAnimateListener) drawable;
        iconAnimateListener.prepareAnimate(new java.util.function.LongConsumer() { // from class: android.window.SplashScreenView$$ExternalSyntheticLambda1
            @Override // java.util.function.LongConsumer
            public final void accept(long j) {
                android.window.SplashScreenView.this.animationStartCallback(j);
            }
        });
        iconAnimateListener.setAnimationJankMonitoring(new android.animation.AnimatorListenerAdapter() { // from class: android.window.SplashScreenView.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
                com.android.internal.jank.InteractionJankMonitor.getInstance().cancel(38);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                com.android.internal.jank.InteractionJankMonitor.getInstance().end(38);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(android.animation.Animator animator) {
                com.android.internal.jank.InteractionJankMonitor.getInstance().begin(android.window.SplashScreenView.this, 38);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animationStartCallback(long j) {
        this.mIconAnimationStart = java.time.Instant.now();
        if (j >= 0) {
            this.mIconAnimationDuration = java.time.Duration.ofMillis(j);
        }
    }

    public void remove() {
        if (this.mHasRemoved) {
            return;
        }
        setVisibility(8);
        if (this.mParceledIconBitmap != null) {
            if (this.mIconView instanceof android.widget.ImageView) {
                ((android.widget.ImageView) this.mIconView).setImageDrawable(null);
            } else if (this.mIconView != null) {
                this.mIconView.setBackground(null);
            }
            this.mParceledIconBitmap.recycle();
            this.mParceledIconBitmap = null;
        }
        if (this.mParceledBrandingBitmap != null) {
            this.mBrandingImageView.setBackground(null);
            this.mParceledBrandingBitmap.recycle();
            this.mParceledBrandingBitmap = null;
        }
        if (this.mParceledIconBackgroundBitmap != null) {
            if (this.mIconView != null) {
                this.mIconView.setBackground(null);
            }
            this.mParceledIconBackgroundBitmap.recycle();
            this.mParceledIconBackgroundBitmap = null;
        }
        if (this.mWindow != null) {
            com.android.internal.policy.DecorView decorView = (com.android.internal.policy.DecorView) this.mWindow.peekDecorView();
            if (DEBUG) {
                android.util.Log.d(TAG, "remove starting view");
            }
            if (decorView != null) {
                decorView.removeView(this);
            }
            this.mWindow = null;
        }
        this.mHasRemoved = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseAnimationSurfaceHost();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mBrandingImageView.getDrawingRect(this.mTmpRect);
        int height = this.mTmpRect.height();
        if (height == 0 || this.mIconView == null || this.mBrandingImageView.getVisibility() != 0) {
            return;
        }
        int i5 = i4 - i2;
        this.mIconView.getLocationInWindow(this.mTmpPos);
        this.mIconView.getDrawingRect(this.mTmpRect);
        int height2 = this.mTmpRect.height();
        android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) this.mBrandingImageView.getLayoutParams();
        if (marginLayoutParams == null) {
            android.util.Log.e(TAG, "Unable to adjust branding image layout, layout changed?");
            return;
        }
        int i6 = marginLayoutParams.bottomMargin;
        int i7 = (i5 - this.mTmpPos[1]) - height2;
        int i8 = i7 - height;
        if (i7 < height) {
            this.mBrandingImageView.setVisibility(8);
        } else if (i8 < i6) {
            marginLayoutParams.bottomMargin = (int) java.lang.Math.round(i8 / 2.0d);
            this.mBrandingImageView.setLayoutParams(marginLayoutParams);
        }
    }

    private void releaseAnimationSurfaceHost() {
        if (this.mSurfaceHost != null && !this.mIsCopied) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Shell removed splash screen. Releasing SurfaceControlViewHost on thread #" + java.lang.Thread.currentThread().getId());
            }
            releaseIconHost(this.mSurfaceHost);
            this.mSurfaceHost = null;
            return;
        }
        if (this.mSurfacePackage != null && this.mSurfaceHost == null) {
            this.mSurfacePackage = null;
            this.mClientCallback.sendResult(null);
        }
    }

    public static void releaseIconHost(android.view.SurfaceControlViewHost surfaceControlViewHost) {
        java.lang.Object background = surfaceControlViewHost.getView().getBackground();
        if (background instanceof android.window.SplashScreenView.IconAnimateListener) {
            ((android.window.SplashScreenView.IconAnimateListener) background).stopAnimation();
        }
        surfaceControlViewHost.release();
    }

    public void attachHostWindow(android.view.Window window) {
        this.mWindow = window;
    }

    public android.view.View getIconView() {
        return this.mIconView;
    }

    public android.view.View getBrandingView() {
        return this.mBrandingImageView;
    }

    public int getInitBackgroundColor() {
        return this.mInitBackgroundColor;
    }

    public interface IconAnimateListener {
        void prepareAnimate(java.util.function.LongConsumer longConsumer);

        void stopAnimation();

        default void setAnimationJankMonitoring(android.animation.AnimatorListenerAdapter animatorListenerAdapter) {
        }
    }

    public static class SplashScreenViewParcelable implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.SplashScreenView.SplashScreenViewParcelable> CREATOR = new android.os.Parcelable.Creator<android.window.SplashScreenView.SplashScreenViewParcelable>() { // from class: android.window.SplashScreenView.SplashScreenViewParcelable.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.SplashScreenView.SplashScreenViewParcelable createFromParcel(android.os.Parcel parcel) {
                return new android.window.SplashScreenView.SplashScreenViewParcelable(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.SplashScreenView.SplashScreenViewParcelable[] newArray(int i) {
                return new android.window.SplashScreenView.SplashScreenViewParcelable[i];
            }
        };
        private int mBackgroundColor;
        private android.graphics.Bitmap mBrandingBitmap;
        private int mBrandingHeight;
        private int mBrandingWidth;
        private android.os.RemoteCallback mClientCallback;
        private long mIconAnimationDurationMillis;
        private long mIconAnimationStartMillis;
        private android.graphics.Bitmap mIconBackground;
        private android.graphics.Bitmap mIconBitmap;
        private int mIconSize;
        private android.view.SurfaceControlViewHost.SurfacePackage mSurfacePackage;

        public SplashScreenViewParcelable(android.window.SplashScreenView splashScreenView) {
            this.mIconBitmap = null;
            android.view.View iconView = splashScreenView.getIconView();
            this.mIconSize = iconView != null ? iconView.getWidth() : 0;
            this.mBackgroundColor = splashScreenView.getInitBackgroundColor();
            this.mIconBackground = iconView != null ? copyDrawable(iconView.getBackground()) : null;
            this.mSurfacePackage = splashScreenView.mSurfacePackageCopy;
            if (this.mSurfacePackage == null) {
                this.mIconBitmap = iconView != null ? copyDrawable(((android.widget.ImageView) splashScreenView.getIconView()).getDrawable()) : null;
            }
            this.mBrandingBitmap = copyDrawable(splashScreenView.getBrandingView().getBackground());
            android.view.ViewGroup.LayoutParams layoutParams = splashScreenView.getBrandingView().getLayoutParams();
            this.mBrandingWidth = layoutParams.width;
            this.mBrandingHeight = layoutParams.height;
            if (splashScreenView.getIconAnimationStart() != null) {
                this.mIconAnimationStartMillis = splashScreenView.getIconAnimationStart().toEpochMilli();
            }
            if (splashScreenView.getIconAnimationDuration() != null) {
                this.mIconAnimationDurationMillis = splashScreenView.getIconAnimationDuration().toMillis();
            }
        }

        private android.graphics.Bitmap copyDrawable(android.graphics.drawable.Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            android.graphics.Rect copyBounds = drawable.copyBounds();
            int width = copyBounds.width();
            int height = copyBounds.height();
            if (width <= 0 || height <= 0) {
                return null;
            }
            android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            android.graphics.Bitmap createAshmemBitmap = createBitmap.createAshmemBitmap();
            createBitmap.recycle();
            return createAshmemBitmap;
        }

        private SplashScreenViewParcelable(android.os.Parcel parcel) {
            this.mIconBitmap = null;
            readParcel(parcel);
        }

        private void readParcel(android.os.Parcel parcel) {
            this.mIconSize = parcel.readInt();
            this.mBackgroundColor = parcel.readInt();
            this.mIconBitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
            this.mBrandingWidth = parcel.readInt();
            this.mBrandingHeight = parcel.readInt();
            this.mBrandingBitmap = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
            this.mIconAnimationStartMillis = parcel.readLong();
            this.mIconAnimationDurationMillis = parcel.readLong();
            this.mIconBackground = (android.graphics.Bitmap) parcel.readTypedObject(android.graphics.Bitmap.CREATOR);
            this.mSurfacePackage = (android.view.SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(android.view.SurfaceControlViewHost.SurfacePackage.CREATOR);
            this.mClientCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mIconSize);
            parcel.writeInt(this.mBackgroundColor);
            parcel.writeTypedObject(this.mIconBitmap, i);
            parcel.writeInt(this.mBrandingWidth);
            parcel.writeInt(this.mBrandingHeight);
            parcel.writeTypedObject(this.mBrandingBitmap, i);
            parcel.writeLong(this.mIconAnimationStartMillis);
            parcel.writeLong(this.mIconAnimationDurationMillis);
            parcel.writeTypedObject(this.mIconBackground, i);
            parcel.writeTypedObject(this.mSurfacePackage, i);
            parcel.writeTypedObject(this.mClientCallback, i);
        }

        public void clearIfNeeded() {
            if (this.mIconBitmap != null) {
                this.mIconBitmap.recycle();
                this.mIconBitmap = null;
            }
            if (this.mBrandingBitmap != null) {
                this.mBrandingBitmap.recycle();
                this.mBrandingBitmap = null;
            }
        }

        int getIconSize() {
            return this.mIconSize;
        }

        int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public void setClientCallback(android.os.RemoteCallback remoteCallback) {
            this.mClientCallback = remoteCallback;
        }
    }
}
