package com.android.internal.app;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class SimpleIconFactory {
    private static final int AMBIENT_SHADOW_ALPHA = 7;
    private static final float BLUR_FACTOR = 0.03125f;
    private static final float CIRCLE_AREA_BY_RECT = 0.7853982f;
    private static final int DEFAULT_WRAPPER_BACKGROUND = -1;
    private static final int KEY_SHADOW_ALPHA = 10;
    private static final float KEY_SHADOW_DISTANCE = 0.020833334f;
    private static final float LINEAR_SCALE_SLOPE = 0.040449437f;
    private static final float MAX_CIRCLE_AREA_FACTOR = 0.6597222f;
    private static final float MAX_SQUARE_AREA_FACTOR = 0.6510417f;
    private static final int MIN_VISIBLE_ALPHA = 40;
    private static final float SCALE_NOT_INITIALIZED = 0.0f;
    private static final android.util.Pools.SynchronizedPool<com.android.internal.app.SimpleIconFactory> sPool = new android.util.Pools.SynchronizedPool<>(java.lang.Runtime.getRuntime().availableProcessors());
    private static boolean sPoolEnabled = true;
    private final android.graphics.Rect mAdaptiveIconBounds;
    private float mAdaptiveIconScale;
    private int mBadgeBitmapSize;
    private final android.graphics.Bitmap mBitmap;
    private final android.graphics.Rect mBounds;
    private android.content.Context mContext;
    private android.graphics.BlurMaskFilter mDefaultBlurMaskFilter;
    private int mFillResIconDpi;
    private int mIconBitmapSize;
    private final float[] mLeftBorder;
    private final int mMaxSize;
    private final byte[] mPixels;
    private android.content.pm.PackageManager mPm;
    private final float[] mRightBorder;
    private final android.graphics.Canvas mScaleCheckCanvas;
    private int mWrapperBackgroundColor;
    private android.graphics.drawable.Drawable mWrapperIcon;
    private final android.graphics.Rect mOldBounds = new android.graphics.Rect();
    private android.graphics.Paint mBlurPaint = new android.graphics.Paint(3);
    private android.graphics.Paint mDrawPaint = new android.graphics.Paint(3);
    private android.graphics.Canvas mCanvas = new android.graphics.Canvas();

    @java.lang.Deprecated
    public static com.android.internal.app.SimpleIconFactory obtain(android.content.Context context) {
        com.android.internal.app.SimpleIconFactory acquire = sPoolEnabled ? sPool.acquire() : null;
        if (acquire == null) {
            android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService("activity");
            com.android.internal.app.SimpleIconFactory simpleIconFactory = new com.android.internal.app.SimpleIconFactory(context, activityManager == null ? 0 : activityManager.getLauncherLargeIconDensity(), getIconSizeFromContext(context), getBadgeSizeFromContext(context));
            simpleIconFactory.setWrapperBackgroundColor(-1);
            return simpleIconFactory;
        }
        return acquire;
    }

    public static void setPoolEnabled(boolean z) {
        sPoolEnabled = z;
    }

    private static int getAttrDimFromContext(android.content.Context context, int i, java.lang.String str) {
        android.content.res.Resources resources = context.getResources();
        android.util.TypedValue typedValue = new android.util.TypedValue();
        if (!context.getTheme().resolveAttribute(i, typedValue, true)) {
            throw new java.lang.IllegalStateException(str);
        }
        return resources.getDimensionPixelSize(typedValue.resourceId);
    }

    private static int getIconSizeFromContext(android.content.Context context) {
        return getAttrDimFromContext(context, com.android.internal.R.attr.iconfactoryIconSize, "Expected theme to define iconfactoryIconSize.");
    }

    private static int getBadgeSizeFromContext(android.content.Context context) {
        return getAttrDimFromContext(context, com.android.internal.R.attr.iconfactoryBadgeSize, "Expected theme to define iconfactoryBadgeSize.");
    }

    @java.lang.Deprecated
    public void recycle() {
        setWrapperBackgroundColor(-1);
        sPool.release(this);
    }

    @java.lang.Deprecated
    private SimpleIconFactory(android.content.Context context, int i, int i2, int i3) {
        this.mContext = context.getApplicationContext();
        this.mPm = this.mContext.getPackageManager();
        this.mIconBitmapSize = i2;
        this.mBadgeBitmapSize = i3;
        this.mFillResIconDpi = i;
        this.mCanvas.setDrawFilter(new android.graphics.PaintFlagsDrawFilter(4, 2));
        this.mMaxSize = i2 * 2;
        this.mBitmap = android.graphics.Bitmap.createBitmap(this.mMaxSize, this.mMaxSize, android.graphics.Bitmap.Config.ALPHA_8);
        this.mScaleCheckCanvas = new android.graphics.Canvas(this.mBitmap);
        this.mPixels = new byte[this.mMaxSize * this.mMaxSize];
        this.mLeftBorder = new float[this.mMaxSize];
        this.mRightBorder = new float[this.mMaxSize];
        this.mBounds = new android.graphics.Rect();
        this.mAdaptiveIconBounds = new android.graphics.Rect();
        this.mAdaptiveIconScale = 0.0f;
        this.mDefaultBlurMaskFilter = new android.graphics.BlurMaskFilter(i2 * BLUR_FACTOR, android.graphics.BlurMaskFilter.Blur.NORMAL);
    }

    @java.lang.Deprecated
    void setWrapperBackgroundColor(int i) {
        if (android.graphics.Color.alpha(i) < 255) {
            i = -1;
        }
        this.mWrapperBackgroundColor = i;
    }

    @java.lang.Deprecated
    android.graphics.Bitmap createUserBadgedIconBitmap(android.graphics.drawable.Drawable drawable, android.os.UserHandle userHandle) {
        float[] fArr = new float[1];
        if (drawable == null) {
            drawable = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        android.graphics.drawable.Drawable normalizeAndWrapToAdaptiveIcon = normalizeAndWrapToAdaptiveIcon(drawable, null, fArr);
        android.graphics.Bitmap createIconBitmap = createIconBitmap(normalizeAndWrapToAdaptiveIcon, fArr[0]);
        if (normalizeAndWrapToAdaptiveIcon instanceof android.graphics.drawable.AdaptiveIconDrawable) {
            this.mCanvas.setBitmap(createIconBitmap);
            recreateIcon(android.graphics.Bitmap.createBitmap(createIconBitmap), this.mCanvas);
            this.mCanvas.setBitmap(null);
        }
        if (userHandle != null) {
            android.graphics.drawable.Drawable userBadgedIcon = this.mPm.getUserBadgedIcon(new com.android.internal.app.SimpleIconFactory.FixedSizeBitmapDrawable(createIconBitmap), userHandle);
            if (userBadgedIcon instanceof android.graphics.drawable.BitmapDrawable) {
                return ((android.graphics.drawable.BitmapDrawable) userBadgedIcon).getBitmap();
            }
            return createIconBitmap(userBadgedIcon, 1.0f);
        }
        return createIconBitmap;
    }

    @java.lang.Deprecated
    public android.graphics.Bitmap createAppBadgedIconBitmap(android.graphics.drawable.Drawable drawable, android.graphics.Bitmap bitmap) {
        float f;
        if (drawable == null) {
            drawable = getFullResDefaultActivityIcon(this.mFillResIconDpi);
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicHeight > intrinsicWidth && intrinsicWidth > 0) {
            f = intrinsicHeight / intrinsicWidth;
        } else if (intrinsicWidth > intrinsicHeight && intrinsicHeight > 0) {
            f = intrinsicWidth / intrinsicHeight;
        } else {
            f = 1.0f;
        }
        android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(this.mContext.getResources(), maskBitmapToCircle(createIconBitmapNoInsetOrMask(drawable, f)));
        android.graphics.Bitmap createIconBitmap = createIconBitmap(bitmapDrawable, getScale(bitmapDrawable, null));
        this.mCanvas.setBitmap(createIconBitmap);
        recreateIcon(android.graphics.Bitmap.createBitmap(createIconBitmap), this.mCanvas);
        if (bitmap != null) {
            this.mCanvas.drawBitmap(android.graphics.Bitmap.createScaledBitmap(bitmap, this.mBadgeBitmapSize, this.mBadgeBitmapSize, false), this.mIconBitmapSize - this.mBadgeBitmapSize, this.mIconBitmapSize - this.mBadgeBitmapSize, (android.graphics.Paint) null);
        }
        this.mCanvas.setBitmap(null);
        return createIconBitmap;
    }

    private android.graphics.Bitmap maskBitmapToCircle(android.graphics.Bitmap bitmap) {
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        android.graphics.Paint paint = new android.graphics.Paint(7);
        int max = java.lang.Math.max((int) java.lang.Math.ceil(bitmap.getWidth() * BLUR_FACTOR), 1);
        paint.setColor(-1);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2.0f, bitmap.getHeight() / 2.0f, (bitmap.getWidth() / 2.0f) - max, paint);
        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        android.graphics.Rect rect = new android.graphics.Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    private static android.graphics.drawable.Drawable getFullResDefaultActivityIcon(int i) {
        return android.content.res.Resources.getSystem().getDrawableForDensity(17629184, i);
    }

    private android.graphics.Bitmap createIconBitmap(android.graphics.drawable.Drawable drawable, float f) {
        return createIconBitmap(drawable, f, this.mIconBitmapSize, true, false);
    }

    private android.graphics.Bitmap createIconBitmapNoInsetOrMask(android.graphics.drawable.Drawable drawable, float f) {
        return createIconBitmap(drawable, f, this.mIconBitmapSize, false, true);
    }

    private android.graphics.Bitmap createIconBitmap(android.graphics.drawable.Drawable drawable, float f, int i, boolean z, boolean z2) {
        int i2;
        int i3;
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i, i, android.graphics.Bitmap.Config.ARGB_8888);
        this.mCanvas.setBitmap(createBitmap);
        this.mOldBounds.set(drawable.getBounds());
        if (drawable instanceof android.graphics.drawable.AdaptiveIconDrawable) {
            android.graphics.drawable.AdaptiveIconDrawable adaptiveIconDrawable = (android.graphics.drawable.AdaptiveIconDrawable) drawable;
            int round = java.lang.Math.round(((1.0f - f) * i) / 2.0f);
            if (z) {
                round = java.lang.Math.max((int) java.lang.Math.ceil(r2 * BLUR_FACTOR), round);
            }
            int i4 = i - round;
            android.graphics.Rect rect = new android.graphics.Rect(round, round, i4, i4);
            if (z2) {
                int width = rect.width() / 2;
                int height = rect.height() / 2;
                float extraInsetFraction = (1.0f / ((android.graphics.drawable.AdaptiveIconDrawable.getExtraInsetFraction() * 2.0f) + 1.0f)) * 2.0f;
                int width2 = (int) (rect.width() / extraInsetFraction);
                int height2 = (int) (rect.height() / extraInsetFraction);
                final android.graphics.Rect rect2 = new android.graphics.Rect(width - width2, height - height2, width + width2, height + height2);
                java.util.Optional.ofNullable(adaptiveIconDrawable.getBackground()).ifPresent(new java.util.function.Consumer() { // from class: com.android.internal.app.SimpleIconFactory$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.internal.app.SimpleIconFactory.this.lambda$createIconBitmap$0(rect2, (android.graphics.drawable.Drawable) obj);
                    }
                });
                java.util.Optional.ofNullable(adaptiveIconDrawable.getForeground()).ifPresent(new java.util.function.Consumer() { // from class: com.android.internal.app.SimpleIconFactory$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.internal.app.SimpleIconFactory.this.lambda$createIconBitmap$1(rect2, (android.graphics.drawable.Drawable) obj);
                    }
                });
            } else {
                adaptiveIconDrawable.setBounds(rect);
                adaptiveIconDrawable.draw(this.mCanvas);
            }
        } else {
            if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
                android.graphics.drawable.BitmapDrawable bitmapDrawable = (android.graphics.drawable.BitmapDrawable) drawable;
                android.graphics.Bitmap bitmap = bitmapDrawable.getBitmap();
                if (createBitmap != null && bitmap.getDensity() == 0) {
                    bitmapDrawable.setTargetDensity(this.mContext.getResources().getDisplayMetrics());
                }
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                float f2 = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i3 = (int) (i / f2);
                    i2 = i;
                } else if (intrinsicHeight > intrinsicWidth) {
                    i2 = (int) (i * f2);
                    i3 = i;
                }
                int i5 = (i - i2) / 2;
                int i6 = (i - i3) / 2;
                drawable.setBounds(i5, i6, i2 + i5, i3 + i6);
                this.mCanvas.save();
                float f3 = i / 2;
                this.mCanvas.scale(f, f, f3, f3);
                drawable.draw(this.mCanvas);
                this.mCanvas.restore();
            }
            i2 = i;
            i3 = i2;
            int i52 = (i - i2) / 2;
            int i62 = (i - i3) / 2;
            drawable.setBounds(i52, i62, i2 + i52, i3 + i62);
            this.mCanvas.save();
            float f32 = i / 2;
            this.mCanvas.scale(f, f, f32, f32);
            drawable.draw(this.mCanvas);
            this.mCanvas.restore();
        }
        drawable.setBounds(this.mOldBounds);
        this.mCanvas.setBitmap(null);
        return createBitmap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createIconBitmap$0(android.graphics.Rect rect, android.graphics.drawable.Drawable drawable) {
        drawable.setBounds(rect);
        drawable.draw(this.mCanvas);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createIconBitmap$1(android.graphics.Rect rect, android.graphics.drawable.Drawable drawable) {
        drawable.setBounds(rect);
        drawable.draw(this.mCanvas);
    }

    private android.graphics.drawable.Drawable normalizeAndWrapToAdaptiveIcon(android.graphics.drawable.Drawable drawable, android.graphics.RectF rectF, float[] fArr) {
        if (this.mWrapperIcon == null) {
            this.mWrapperIcon = this.mContext.getDrawable(com.android.internal.R.drawable.iconfactory_adaptive_icon_drawable_wrapper).mutate();
        }
        android.graphics.drawable.AdaptiveIconDrawable adaptiveIconDrawable = (android.graphics.drawable.AdaptiveIconDrawable) this.mWrapperIcon;
        adaptiveIconDrawable.setBounds(0, 0, 1, 1);
        float scale = getScale(drawable, rectF);
        if (!(drawable instanceof android.graphics.drawable.AdaptiveIconDrawable)) {
            com.android.internal.app.SimpleIconFactory.FixedScaleDrawable fixedScaleDrawable = (com.android.internal.app.SimpleIconFactory.FixedScaleDrawable) adaptiveIconDrawable.getForeground();
            fixedScaleDrawable.setDrawable(drawable);
            fixedScaleDrawable.setScale(scale);
            scale = getScale(adaptiveIconDrawable, rectF);
            ((android.graphics.drawable.ColorDrawable) adaptiveIconDrawable.getBackground()).setColor(this.mWrapperBackgroundColor);
            drawable = adaptiveIconDrawable;
        }
        fArr[0] = scale;
        return drawable;
    }

    /* JADX WARN: Code restructure failed: missing block: B:81:0x0045, code lost:
    
        if (r3 <= r16.mMaxSize) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d8 A[Catch: all -> 0x016b, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:14:0x001d, B:18:0x002a, B:20:0x002e, B:22:0x0051, B:26:0x0084, B:33:0x0093, B:36:0x009a, B:40:0x00ac, B:42:0x00b6, B:49:0x00c7, B:51:0x00d8, B:55:0x00ee, B:56:0x00e3, B:59:0x00f1, B:62:0x0110, B:64:0x0122, B:65:0x0143, B:67:0x014a, B:68:0x0151, B:70:0x0157, B:72:0x015e, B:75:0x0106, B:78:0x0032, B:80:0x0043, B:83:0x004b, B:85:0x004f, B:86:0x0047), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0122 A[Catch: all -> 0x016b, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:14:0x001d, B:18:0x002a, B:20:0x002e, B:22:0x0051, B:26:0x0084, B:33:0x0093, B:36:0x009a, B:40:0x00ac, B:42:0x00b6, B:49:0x00c7, B:51:0x00d8, B:55:0x00ee, B:56:0x00e3, B:59:0x00f1, B:62:0x0110, B:64:0x0122, B:65:0x0143, B:67:0x014a, B:68:0x0151, B:70:0x0157, B:72:0x015e, B:75:0x0106, B:78:0x0032, B:80:0x0043, B:83:0x004b, B:85:0x004f, B:86:0x0047), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x014a A[Catch: all -> 0x016b, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:14:0x001d, B:18:0x002a, B:20:0x002e, B:22:0x0051, B:26:0x0084, B:33:0x0093, B:36:0x009a, B:40:0x00ac, B:42:0x00b6, B:49:0x00c7, B:51:0x00d8, B:55:0x00ee, B:56:0x00e3, B:59:0x00f1, B:62:0x0110, B:64:0x0122, B:65:0x0143, B:67:0x014a, B:68:0x0151, B:70:0x0157, B:72:0x015e, B:75:0x0106, B:78:0x0032, B:80:0x0043, B:83:0x004b, B:85:0x004f, B:86:0x0047), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0106 A[Catch: all -> 0x016b, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000c, B:9:0x0014, B:10:0x0019, B:14:0x001d, B:18:0x002a, B:20:0x002e, B:22:0x0051, B:26:0x0084, B:33:0x0093, B:36:0x009a, B:40:0x00ac, B:42:0x00b6, B:49:0x00c7, B:51:0x00d8, B:55:0x00ee, B:56:0x00e3, B:59:0x00f1, B:62:0x0110, B:64:0x0122, B:65:0x0143, B:67:0x014a, B:68:0x0151, B:70:0x0157, B:72:0x015e, B:75:0x0106, B:78:0x0032, B:80:0x0043, B:83:0x004b, B:85:0x004f, B:86:0x0047), top: B:3:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized float getScale(android.graphics.drawable.Drawable drawable, android.graphics.RectF rectF) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f;
        float f2;
        float sqrt;
        if ((drawable instanceof android.graphics.drawable.AdaptiveIconDrawable) && this.mAdaptiveIconScale != 0.0f) {
            if (rectF != null) {
                rectF.set(this.mAdaptiveIconBounds);
            }
            return this.mAdaptiveIconScale;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth > 0 && intrinsicHeight > 0) {
            if (intrinsicWidth > this.mMaxSize || intrinsicHeight > this.mMaxSize) {
                int max = java.lang.Math.max(intrinsicWidth, intrinsicHeight);
                intrinsicWidth = (this.mMaxSize * intrinsicWidth) / max;
                intrinsicHeight = (this.mMaxSize * intrinsicHeight) / max;
            }
            int i7 = 0;
            this.mBitmap.eraseColor(0);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(this.mScaleCheckCanvas);
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(this.mPixels);
            wrap.rewind();
            this.mBitmap.copyPixelsToBuffer(wrap);
            i = this.mMaxSize + 1;
            int i8 = this.mMaxSize - intrinsicWidth;
            i2 = 0;
            int i9 = 0;
            i3 = -1;
            i4 = -1;
            i5 = -1;
            while (i2 < intrinsicHeight) {
                int i10 = -1;
                int i11 = -1;
                while (i7 < intrinsicWidth) {
                    if ((this.mPixels[i9] & 255) > 40) {
                        if (i10 == -1) {
                            i10 = i7;
                        }
                        i11 = i7;
                    }
                    i9++;
                    i7++;
                }
                i9 += i8;
                this.mLeftBorder[i2] = i10;
                this.mRightBorder[i2] = i11;
                if (i10 != -1) {
                    if (i3 == -1) {
                        i3 = i2;
                    }
                    int min = java.lang.Math.min(i, i10);
                    i4 = java.lang.Math.max(i4, i11);
                    i = min;
                    i5 = i2;
                }
                i2++;
                i7 = 0;
            }
            if (i3 != -1 && i4 != -1) {
                convertToConvexArray(this.mLeftBorder, 1, i3, i5);
                convertToConvexArray(this.mRightBorder, -1, i3, i5);
                float f3 = 0.0f;
                for (i6 = 0; i6 < intrinsicHeight; i6++) {
                    if (this.mLeftBorder[i6] > -1.0f) {
                        f3 += (this.mRightBorder[i6] - this.mLeftBorder[i6]) + 1.0f;
                    }
                }
                f = f3 / (((i5 + 1) - i3) * ((i4 + 1) - i));
                if (f >= CIRCLE_AREA_BY_RECT) {
                    f2 = MAX_CIRCLE_AREA_FACTOR;
                } else {
                    f2 = ((1.0f - f) * LINEAR_SCALE_SLOPE) + MAX_SQUARE_AREA_FACTOR;
                }
                this.mBounds.left = i;
                this.mBounds.right = i4;
                this.mBounds.top = i3;
                this.mBounds.bottom = i5;
                if (rectF != null) {
                    float f4 = intrinsicWidth;
                    float f5 = intrinsicHeight;
                    rectF.set(this.mBounds.left / f4, this.mBounds.top / f5, 1.0f - (this.mBounds.right / f4), 1.0f - (this.mBounds.bottom / f5));
                }
                sqrt = f3 / (intrinsicWidth * intrinsicHeight) > f2 ? (float) java.lang.Math.sqrt(f2 / r4) : 1.0f;
                if ((drawable instanceof android.graphics.drawable.AdaptiveIconDrawable) && this.mAdaptiveIconScale == 0.0f) {
                    this.mAdaptiveIconScale = sqrt;
                    this.mAdaptiveIconBounds.set(this.mBounds);
                }
                return sqrt;
            }
            return 1.0f;
        }
        intrinsicWidth = this.mMaxSize;
        if (intrinsicHeight <= 0 || intrinsicHeight > this.mMaxSize) {
            intrinsicHeight = this.mMaxSize;
        }
        int i72 = 0;
        this.mBitmap.eraseColor(0);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(this.mScaleCheckCanvas);
        java.nio.ByteBuffer wrap2 = java.nio.ByteBuffer.wrap(this.mPixels);
        wrap2.rewind();
        this.mBitmap.copyPixelsToBuffer(wrap2);
        i = this.mMaxSize + 1;
        int i82 = this.mMaxSize - intrinsicWidth;
        i2 = 0;
        int i92 = 0;
        i3 = -1;
        i4 = -1;
        i5 = -1;
        while (i2 < intrinsicHeight) {
        }
        if (i3 != -1) {
            convertToConvexArray(this.mLeftBorder, 1, i3, i5);
            convertToConvexArray(this.mRightBorder, -1, i3, i5);
            float f32 = 0.0f;
            while (i6 < intrinsicHeight) {
            }
            f = f32 / (((i5 + 1) - i3) * ((i4 + 1) - i));
            if (f >= CIRCLE_AREA_BY_RECT) {
            }
            this.mBounds.left = i;
            this.mBounds.right = i4;
            this.mBounds.top = i3;
            this.mBounds.bottom = i5;
            if (rectF != null) {
            }
            if (f32 / (intrinsicWidth * intrinsicHeight) > f2) {
            }
            if (drawable instanceof android.graphics.drawable.AdaptiveIconDrawable) {
                this.mAdaptiveIconScale = sqrt;
                this.mAdaptiveIconBounds.set(this.mBounds);
            }
            return sqrt;
        }
        return 1.0f;
    }

    private static void convertToConvexArray(float[] fArr, int i, int i2, int i3) {
        float[] fArr2 = new float[fArr.length - 1];
        int i4 = -1;
        float f = Float.MAX_VALUE;
        for (int i5 = i2 + 1; i5 <= i3; i5++) {
            if (fArr[i5] > -1.0f) {
                if (f == Float.MAX_VALUE) {
                    i4 = i2;
                } else {
                    float f2 = ((fArr[i5] - fArr[i4]) / (i5 - i4)) - f;
                    float f3 = i;
                    if (f2 * f3 < 0.0f) {
                        while (i4 > i2) {
                            i4--;
                            if ((((fArr[i5] - fArr[i4]) / (i5 - i4)) - fArr2[i4]) * f3 >= 0.0f) {
                                break;
                            }
                        }
                    }
                }
                f = (fArr[i5] - fArr[i4]) / (i5 - i4);
                for (int i6 = i4; i6 < i5; i6++) {
                    fArr2[i6] = f;
                    fArr[i6] = fArr[i4] + ((i6 - i4) * f);
                }
                i4 = i5;
            }
        }
    }

    private synchronized void recreateIcon(android.graphics.Bitmap bitmap, android.graphics.Canvas canvas) {
        recreateIcon(bitmap, this.mDefaultBlurMaskFilter, 7, 10, canvas);
    }

    private synchronized void recreateIcon(android.graphics.Bitmap bitmap, android.graphics.BlurMaskFilter blurMaskFilter, int i, int i2, android.graphics.Canvas canvas) {
        this.mBlurPaint.setMaskFilter(blurMaskFilter);
        android.graphics.Bitmap extractAlpha = bitmap.extractAlpha(this.mBlurPaint, new int[2]);
        this.mDrawPaint.setAlpha(i);
        canvas.drawBitmap(extractAlpha, r0[0], r0[1], this.mDrawPaint);
        this.mDrawPaint.setAlpha(i2);
        canvas.drawBitmap(extractAlpha, r0[0], r0[1] + (this.mIconBitmapSize * KEY_SHADOW_DISTANCE), this.mDrawPaint);
        this.mDrawPaint.setAlpha(255);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mDrawPaint);
    }

    public static class FixedScaleDrawable extends android.graphics.drawable.DrawableWrapper {
        private static final float LEGACY_ICON_SCALE = 0.46669f;
        private float mScaleX;
        private float mScaleY;

        public FixedScaleDrawable() {
            super(new android.graphics.drawable.ColorDrawable());
            this.mScaleX = LEGACY_ICON_SCALE;
            this.mScaleY = LEGACY_ICON_SCALE;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(android.graphics.Canvas canvas) {
            int save = canvas.save();
            canvas.scale(this.mScaleX, this.mScaleY, getBounds().exactCenterX(), getBounds().exactCenterY());
            super.draw(canvas);
            canvas.restoreToCount(save);
        }

        @Override // android.graphics.drawable.Drawable
        public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) {
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) {
        }

        public void setScale(float f) {
            float intrinsicHeight = getIntrinsicHeight();
            float intrinsicWidth = getIntrinsicWidth();
            float f2 = f * LEGACY_ICON_SCALE;
            this.mScaleX = f2;
            this.mScaleY = f2;
            if (intrinsicHeight > intrinsicWidth && intrinsicWidth > 0.0f) {
                this.mScaleX *= intrinsicWidth / intrinsicHeight;
            } else if (intrinsicWidth > intrinsicHeight && intrinsicHeight > 0.0f) {
                this.mScaleY *= intrinsicHeight / intrinsicWidth;
            }
        }
    }

    private static class FixedSizeBitmapDrawable extends android.graphics.drawable.BitmapDrawable {
        FixedSizeBitmapDrawable(android.graphics.Bitmap bitmap) {
            super((android.content.res.Resources) null, bitmap);
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return getBitmap().getWidth();
        }

        @Override // android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return getBitmap().getWidth();
        }
    }
}
