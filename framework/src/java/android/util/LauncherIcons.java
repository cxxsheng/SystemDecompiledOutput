package android.util;

/* loaded from: classes3.dex */
public final class LauncherIcons {
    private static final int AMBIENT_SHADOW_ALPHA = 30;
    private static final float ICON_SIZE_BLUR_FACTOR = 0.010416667f;
    private static final float ICON_SIZE_KEY_SHADOW_DELTA_FACTOR = 0.020833334f;
    private static final int KEY_SHADOW_ALPHA = 61;
    private final android.content.Context mContext;
    private final int mIconSize;
    private final android.content.res.Resources mRes;
    private final android.util.SparseArray<android.graphics.Bitmap> mShadowCache = new android.util.SparseArray<>();

    public LauncherIcons(android.content.Context context) {
        this.mRes = context.getResources();
        this.mIconSize = this.mRes.getDimensionPixelSize(17104896);
        this.mContext = context;
    }

    public android.graphics.drawable.Drawable wrapIconDrawableWithShadow(android.graphics.drawable.Drawable drawable) {
        if (!(drawable instanceof android.graphics.drawable.AdaptiveIconDrawable)) {
            return drawable;
        }
        return new android.util.LauncherIcons.ShadowDrawable(getShadowBitmap((android.graphics.drawable.AdaptiveIconDrawable) drawable), drawable);
    }

    private android.graphics.Bitmap getShadowBitmap(android.graphics.drawable.AdaptiveIconDrawable adaptiveIconDrawable) {
        int max = java.lang.Math.max(this.mIconSize, adaptiveIconDrawable.getIntrinsicHeight());
        synchronized (this.mShadowCache) {
            android.graphics.Bitmap bitmap = this.mShadowCache.get(max);
            if (bitmap != null) {
                return bitmap;
            }
            adaptiveIconDrawable.setBounds(0, 0, max, max);
            float f = max;
            float f2 = ICON_SIZE_BLUR_FACTOR * f;
            float f3 = ICON_SIZE_KEY_SHADOW_DELTA_FACTOR * f;
            int i = (int) (f + (f2 * 2.0f) + f3);
            android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i, i, android.graphics.Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
            canvas.translate((f3 / 2.0f) + f2, f2);
            android.graphics.Paint paint = new android.graphics.Paint(1);
            paint.setColor(0);
            paint.setShadowLayer(f2, 0.0f, 0.0f, android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS);
            canvas.drawPath(adaptiveIconDrawable.getIconMask(), paint);
            canvas.translate(0.0f, f3);
            paint.setShadowLayer(f2, 0.0f, 0.0f, 1023410176);
            canvas.drawPath(adaptiveIconDrawable.getIconMask(), paint);
            canvas.setBitmap(null);
            synchronized (this.mShadowCache) {
                this.mShadowCache.put(max, createBitmap);
            }
            return createBitmap;
        }
    }

    public android.graphics.drawable.Drawable getBadgeDrawable(android.graphics.drawable.Drawable drawable, int i) {
        return getBadgedDrawable(null, drawable, i);
    }

    public android.graphics.drawable.Drawable getBadgedDrawable(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, int i) {
        android.graphics.drawable.Drawable[] drawableArr;
        android.content.res.Resources resources = android.app.ActivityThread.currentActivityThread().getApplication().getResources();
        android.graphics.drawable.Drawable drawable3 = resources.getDrawable(com.android.internal.R.drawable.ic_corp_icon_badge_shadow);
        android.graphics.drawable.Drawable mutate = resources.getDrawable(com.android.internal.R.drawable.ic_corp_icon_badge_color).getConstantState().newDrawable().mutate();
        drawable2.setTint(i);
        if (drawable == null) {
            drawableArr = new android.graphics.drawable.Drawable[]{drawable3, mutate, drawable2};
        } else {
            drawableArr = new android.graphics.drawable.Drawable[]{drawable, drawable3, mutate, drawable2};
        }
        return new android.graphics.drawable.LayerDrawable(drawableArr);
    }

    private static class ShadowDrawable extends android.graphics.drawable.DrawableWrapper {
        final android.util.LauncherIcons.ShadowDrawable.MyConstantState mState;

        public ShadowDrawable(android.graphics.Bitmap bitmap, android.graphics.drawable.Drawable drawable) {
            super(drawable);
            this.mState = new android.util.LauncherIcons.ShadowDrawable.MyConstantState(bitmap, drawable.getConstantState());
        }

        ShadowDrawable(android.util.LauncherIcons.ShadowDrawable.MyConstantState myConstantState) {
            super(myConstantState.mChildState.newDrawable());
            this.mState = myConstantState;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public android.graphics.drawable.Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(android.graphics.Canvas canvas) {
            canvas.drawBitmap(this.mState.mShadow, (android.graphics.Rect) null, getBounds(), this.mState.mPaint);
            canvas.save();
            canvas.translate(r0.width() * 0.9599999f * android.util.LauncherIcons.ICON_SIZE_KEY_SHADOW_DELTA_FACTOR, r0.height() * 0.9599999f * android.util.LauncherIcons.ICON_SIZE_BLUR_FACTOR);
            canvas.scale(0.9599999f, 0.9599999f);
            super.draw(canvas);
            canvas.restore();
        }

        private static class MyConstantState extends android.graphics.drawable.Drawable.ConstantState {
            final android.graphics.drawable.Drawable.ConstantState mChildState;
            final android.graphics.Paint mPaint = new android.graphics.Paint(2);
            final android.graphics.Bitmap mShadow;

            MyConstantState(android.graphics.Bitmap bitmap, android.graphics.drawable.Drawable.ConstantState constantState) {
                this.mShadow = bitmap;
                this.mChildState = constantState;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public android.graphics.drawable.Drawable newDrawable() {
                return new android.util.LauncherIcons.ShadowDrawable(this);
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return this.mChildState.getChangingConfigurations();
            }
        }
    }
}
