package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class CachingIconView extends android.widget.ImageView {
    private int mBackgroundColor;
    private int mDesiredVisibility;
    private boolean mForceHidden;
    private int mIconColor;
    private boolean mInternalSetDrawable;
    private java.lang.String mLastPackage;
    private int mLastResId;
    private int mMaxDrawableHeight;
    private int mMaxDrawableWidth;
    private java.util.function.Consumer<java.lang.Boolean> mOnForceHiddenChangedListener;
    private java.util.function.Consumer<java.lang.Integer> mOnVisibilityChangedListener;
    private boolean mWillBeForceHidden;

    public CachingIconView(android.content.Context context) {
        this(context, null, 0, 0);
    }

    public CachingIconView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public CachingIconView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CachingIconView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxDrawableWidth = -1;
        this.mMaxDrawableHeight = -1;
        init(context, attributeSet, i, i2);
    }

    private void init(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        if (attributeSet == null) {
            return;
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.CachingIconView, i, i2);
        this.mMaxDrawableWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        this.mMaxDrawableHeight = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.ImageView
    @android.view.RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(android.graphics.drawable.Icon icon) {
        if (!testAndSetCache(icon)) {
            this.mInternalSetDrawable = true;
            android.graphics.drawable.Drawable loadSizeRestrictedIcon = loadSizeRestrictedIcon(icon);
            if (loadSizeRestrictedIcon == null) {
                super.setImageIcon(icon);
            } else {
                super.lambda$setImageURIAsync$2(loadSizeRestrictedIcon);
            }
            this.mInternalSetDrawable = false;
        }
    }

    private android.graphics.drawable.Drawable loadSizeRestrictedIcon(android.graphics.drawable.Icon icon) {
        return com.android.internal.widget.LocalImageResolver.resolveImage(icon, getContext(), this.mMaxDrawableWidth, this.mMaxDrawableHeight);
    }

    @Override // android.widget.ImageView
    public java.lang.Runnable setImageIconAsync(android.graphics.drawable.Icon icon) {
        resetCache();
        final android.graphics.drawable.Drawable loadSizeRestrictedIcon = loadSizeRestrictedIcon(icon);
        if (loadSizeRestrictedIcon != null) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.CachingIconView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.CachingIconView.this.lambda$setImageIconAsync$0(loadSizeRestrictedIcon);
                }
            };
        }
        return super.setImageIconAsync(icon);
    }

    @Override // android.widget.ImageView
    @android.view.RemotableViewMethod(asyncImpl = "setImageResourceAsync")
    public void setImageResource(int i) {
        if (!testAndSetCache(i)) {
            this.mInternalSetDrawable = true;
            android.graphics.drawable.Drawable loadSizeRestrictedDrawable = loadSizeRestrictedDrawable(i);
            if (loadSizeRestrictedDrawable == null) {
                super.setImageResource(i);
            } else {
                super.lambda$setImageURIAsync$2(loadSizeRestrictedDrawable);
            }
            this.mInternalSetDrawable = false;
        }
    }

    private android.graphics.drawable.Drawable loadSizeRestrictedDrawable(int i) {
        return com.android.internal.widget.LocalImageResolver.resolveImage(i, getContext(), this.mMaxDrawableWidth, this.mMaxDrawableHeight);
    }

    @Override // android.widget.ImageView
    public java.lang.Runnable setImageResourceAsync(int i) {
        resetCache();
        final android.graphics.drawable.Drawable loadSizeRestrictedDrawable = loadSizeRestrictedDrawable(i);
        if (loadSizeRestrictedDrawable != null) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.CachingIconView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.CachingIconView.this.lambda$setImageResourceAsync$1(loadSizeRestrictedDrawable);
                }
            };
        }
        return super.setImageResourceAsync(i);
    }

    @Override // android.widget.ImageView
    @android.view.RemotableViewMethod(asyncImpl = "setImageURIAsync")
    public void setImageURI(android.net.Uri uri) {
        resetCache();
        android.graphics.drawable.Drawable loadSizeRestrictedUri = loadSizeRestrictedUri(uri);
        if (loadSizeRestrictedUri == null) {
            super.setImageURI(uri);
            return;
        }
        this.mInternalSetDrawable = true;
        super.lambda$setImageURIAsync$2(loadSizeRestrictedUri);
        this.mInternalSetDrawable = false;
    }

    private android.graphics.drawable.Drawable loadSizeRestrictedUri(android.net.Uri uri) {
        return com.android.internal.widget.LocalImageResolver.resolveImage(uri, getContext(), this.mMaxDrawableWidth, this.mMaxDrawableHeight);
    }

    @Override // android.widget.ImageView
    public java.lang.Runnable setImageURIAsync(android.net.Uri uri) {
        resetCache();
        final android.graphics.drawable.Drawable loadSizeRestrictedUri = loadSizeRestrictedUri(uri);
        if (loadSizeRestrictedUri == null) {
            return super.setImageURIAsync(uri);
        }
        return new java.lang.Runnable() { // from class: com.android.internal.widget.CachingIconView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.CachingIconView.this.lambda$setImageURIAsync$2(loadSizeRestrictedUri);
            }
        };
    }

    @Override // android.widget.ImageView, android.inputmethodservice.navigationbar.ButtonInterface
    /* renamed from: setImageDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setImageURIAsync$2(android.graphics.drawable.Drawable drawable) {
        if (!this.mInternalSetDrawable) {
            resetCache();
        }
        super.lambda$setImageURIAsync$2(drawable);
    }

    @Override // android.widget.ImageView
    @android.view.RemotableViewMethod
    public void setImageBitmap(android.graphics.Bitmap bitmap) {
        resetCache();
        super.setImageBitmap(bitmap);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        resetCache();
    }

    private synchronized boolean testAndSetCache(android.graphics.drawable.Icon icon) {
        boolean z = false;
        if (icon != null) {
            if (icon.getType() == 2) {
                java.lang.String normalizeIconPackage = normalizeIconPackage(icon);
                if (this.mLastResId != 0 && icon.getResId() == this.mLastResId && java.util.Objects.equals(normalizeIconPackage, this.mLastPackage)) {
                    z = true;
                }
                this.mLastPackage = normalizeIconPackage;
                this.mLastResId = icon.getResId();
                return z;
            }
        }
        resetCache();
        return false;
    }

    private synchronized boolean testAndSetCache(int i) {
        boolean z;
        z = false;
        if (i != 0) {
            try {
                if (this.mLastResId != 0 && i == this.mLastResId && this.mLastPackage == null) {
                    z = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mLastPackage = null;
        this.mLastResId = i;
        return z;
    }

    private java.lang.String normalizeIconPackage(android.graphics.drawable.Icon icon) {
        if (icon == null) {
            return null;
        }
        java.lang.String resPackage = icon.getResPackage();
        if (android.text.TextUtils.isEmpty(resPackage) || resPackage.equals(this.mContext.getPackageName())) {
            return null;
        }
        return resPackage;
    }

    private synchronized void resetCache() {
        this.mLastResId = 0;
        this.mLastPackage = null;
    }

    public void setForceHidden(boolean z) {
        if (z != this.mForceHidden) {
            this.mForceHidden = z;
            this.mWillBeForceHidden = false;
            updateVisibility();
            if (this.mOnForceHiddenChangedListener != null) {
                this.mOnForceHiddenChangedListener.accept(java.lang.Boolean.valueOf(z));
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    @android.view.RemotableViewMethod
    public void setVisibility(int i) {
        this.mDesiredVisibility = i;
        updateVisibility();
    }

    private void updateVisibility() {
        int i = (this.mDesiredVisibility == 0 && this.mForceHidden) ? 4 : this.mDesiredVisibility;
        if (this.mOnVisibilityChangedListener != null) {
            this.mOnVisibilityChangedListener.accept(java.lang.Integer.valueOf(i));
        }
        super.setVisibility(i);
    }

    public void setOnVisibilityChangedListener(java.util.function.Consumer<java.lang.Integer> consumer) {
        this.mOnVisibilityChangedListener = consumer;
    }

    public void setOnForceHiddenChangedListener(java.util.function.Consumer<java.lang.Boolean> consumer) {
        this.mOnForceHiddenChangedListener = consumer;
    }

    public boolean isForceHidden() {
        return this.mForceHidden;
    }

    @Override // android.view.View
    @android.view.RemotableViewMethod
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    @android.view.RemotableViewMethod
    public void setOriginalIconColor(int i) {
        this.mIconColor = i;
        android.graphics.drawable.Drawable background = getBackground();
        android.graphics.drawable.Drawable drawable = getDrawable();
        boolean z = i != 1;
        if (background == null) {
            if (z && drawable != null) {
                drawable.mutate().setColorFilter(i, android.graphics.PorterDuff.Mode.SRC_ATOP);
                return;
            }
            return;
        }
        if (z) {
            background.mutate().setColorFilter(i, android.graphics.PorterDuff.Mode.SRC_ATOP);
            if (drawable != null) {
                drawable.mutate().setColorFilter(this.mBackgroundColor, android.graphics.PorterDuff.Mode.SRC_ATOP);
                return;
            }
            return;
        }
        background.mutate().setColorFilter(this.mBackgroundColor, android.graphics.PorterDuff.Mode.SRC_ATOP);
    }

    public void setGrayedOut(boolean z) {
        android.graphics.drawable.Drawable background = getBackground();
        if (background == null) {
            background = getDrawable();
        }
        com.android.internal.widget.ColoredIconHelper.applyGrayTint(this.mContext, background, z, this.mIconColor);
    }

    public int getOriginalIconColor() {
        return this.mIconColor;
    }

    public boolean willBeForceHidden() {
        return this.mWillBeForceHidden;
    }

    public void setWillBeForceHidden(boolean z) {
        this.mWillBeForceHidden = z;
    }

    public int getMaxDrawableWidth() {
        return this.mMaxDrawableWidth;
    }

    public int getMaxDrawableHeight() {
        return this.mMaxDrawableHeight;
    }
}
