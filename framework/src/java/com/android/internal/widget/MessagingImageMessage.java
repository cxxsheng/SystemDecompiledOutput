package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class MessagingImageMessage extends android.widget.ImageView implements com.android.internal.widget.MessagingMessage {
    private static final java.lang.String TAG = "MessagingImageMessage";
    private static final com.android.internal.widget.MessagingPool<com.android.internal.widget.MessagingImageMessage> sInstancePool = new com.android.internal.widget.MessagingPool<>(10);
    private int mActualHeight;
    private int mActualWidth;
    private float mAspectRatio;
    private android.graphics.drawable.Drawable mDrawable;
    private final int mExtraSpacing;
    private com.android.internal.widget.ImageResolver mImageResolver;
    private final int mImageRounding;
    private boolean mIsIsolated;
    private final int mIsolatedSize;
    private final int mMaxImageHeight;
    private final int mMinImageHeight;
    private final android.graphics.Path mPath;
    private final com.android.internal.widget.MessagingMessageState mState;

    public MessagingImageMessage(android.content.Context context) {
        this(context, null);
    }

    public MessagingImageMessage(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MessagingImageMessage(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MessagingImageMessage(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mState = new com.android.internal.widget.MessagingMessageState(this);
        this.mPath = new android.graphics.Path();
        this.mMinImageHeight = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.messaging_image_min_size);
        this.mMaxImageHeight = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.messaging_image_max_height);
        this.mImageRounding = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.messaging_image_rounding);
        this.mExtraSpacing = context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.messaging_image_extra_spacing);
        setMaxHeight(this.mMaxImageHeight);
        this.mIsolatedSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.messaging_avatar_size);
    }

    @Override // com.android.internal.widget.MessagingMessage
    public com.android.internal.widget.MessagingMessageState getState() {
        return this.mState;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public boolean setMessage(android.app.Notification.MessagingStyle.Message message, boolean z) {
        super.setMessage(message, z);
        try {
            android.net.Uri dataUri = message.getDataUri();
            android.graphics.drawable.Drawable loadImage = this.mImageResolver != null ? this.mImageResolver.loadImage(dataUri) : com.android.internal.widget.LocalImageResolver.resolveImage(dataUri, getContext());
            if (loadImage == null) {
                return false;
            }
            int intrinsicHeight = loadImage.getIntrinsicHeight();
            if (intrinsicHeight == 0) {
                android.util.Log.w(TAG, "Drawable with 0 intrinsic height was returned");
                return false;
            }
            this.mDrawable = loadImage;
            this.mAspectRatio = this.mDrawable.getIntrinsicWidth() / intrinsicHeight;
            if (!z) {
                finalizeInflate();
                return true;
            }
            return true;
        } catch (java.io.IOException | java.lang.SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    static com.android.internal.widget.MessagingMessage createMessage(com.android.internal.widget.IMessagingLayout iMessagingLayout, android.app.Notification.MessagingStyle.Message message, com.android.internal.widget.ImageResolver imageResolver, boolean z) {
        com.android.internal.widget.MessagingLinearLayout messagingLinearLayout = iMessagingLayout.getMessagingLinearLayout();
        com.android.internal.widget.MessagingImageMessage acquire = sInstancePool.acquire();
        if (acquire == null) {
            acquire = (com.android.internal.widget.MessagingImageMessage) android.view.LayoutInflater.from(iMessagingLayout.getContext()).inflate(com.android.internal.R.layout.notification_template_messaging_image_message, (android.view.ViewGroup) messagingLinearLayout, false);
            acquire.addOnLayoutChangeListener(com.android.internal.widget.MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        acquire.setImageResolver(imageResolver);
        if (!acquire.setMessage(message, false)) {
            acquire.recycle();
            return com.android.internal.widget.MessagingTextMessage.createMessage(iMessagingLayout, message, z);
        }
        return acquire;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public void finalizeInflate() {
        lambda$setImageURIAsync$2(this.mDrawable);
        setContentDescription(getMessage().getText());
    }

    private void setImageResolver(com.android.internal.widget.ImageResolver imageResolver) {
        this.mImageResolver = imageResolver;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        canvas.save();
        canvas.clipPath(getRoundedRectPath());
        int max = (int) java.lang.Math.max(java.lang.Math.min(getHeight(), getActualHeight()) * this.mAspectRatio, getActualWidth());
        int actualWidth = (int) ((getActualWidth() - max) / 2.0f);
        int actualHeight = (int) ((getActualHeight() - r1) / 2.0f);
        this.mDrawable.setBounds(actualWidth, actualHeight, max + actualWidth, ((int) java.lang.Math.max((int) java.lang.Math.max(java.lang.Math.min(getWidth(), getActualWidth()) / this.mAspectRatio, getActualHeight()), max / this.mAspectRatio)) + actualHeight);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    public android.graphics.Path getRoundedRectPath() {
        int actualWidth = getActualWidth();
        int actualHeight = getActualHeight();
        this.mPath.reset();
        float f = this.mImageRounding;
        float f2 = this.mImageRounding;
        float min = java.lang.Math.min((actualWidth + 0) / 2, f);
        float min2 = java.lang.Math.min((actualHeight + 0) / 2, f2);
        float f3 = 0;
        float f4 = f3 + min2;
        this.mPath.moveTo(f3, f4);
        float f5 = f3 + min;
        this.mPath.quadTo(f3, f3, f5, f3);
        float f6 = actualWidth;
        float f7 = f6 - min;
        this.mPath.lineTo(f7, f3);
        this.mPath.quadTo(f6, f3, f6, f4);
        float f8 = actualHeight;
        float f9 = f8 - min2;
        this.mPath.lineTo(f6, f9);
        this.mPath.quadTo(f6, f8, f7, f8);
        this.mPath.lineTo(f5, f8);
        this.mPath.quadTo(f3, f8, f3, f9);
        this.mPath.close();
        return this.mPath;
    }

    @Override // com.android.internal.widget.MessagingMessage, com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void recycle() {
        super.recycle();
        setImageBitmap(null);
        this.mDrawable = null;
        sInstancePool.release((com.android.internal.widget.MessagingPool<com.android.internal.widget.MessagingImageMessage>) this);
    }

    public static void dropCache() {
        sInstancePool.clear();
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getMeasuredType() {
        int i;
        if (this.mDrawable == null) {
            android.util.Log.e(TAG, "getMeasuredType() after recycle()!");
            return 0;
        }
        int measuredHeight = getMeasuredHeight();
        if (this.mIsIsolated) {
            i = this.mIsolatedSize;
        } else {
            i = this.mMinImageHeight;
        }
        if (measuredHeight < i && measuredHeight != this.mDrawable.getIntrinsicHeight()) {
            return 2;
        }
        return (this.mIsIsolated || measuredHeight == this.mDrawable.getIntrinsicHeight()) ? 0 : 1;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setMaxDisplayedLines(int i) {
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mDrawable == null) {
            android.util.Log.e(TAG, "onMeasure() after recycle()!");
            setMeasuredDimension(0, 0);
        } else if (this.mIsIsolated) {
            setMeasuredDimension(android.view.View.MeasureSpec.getSize(i), android.view.View.MeasureSpec.getSize(i2));
        } else {
            int min = java.lang.Math.min(android.view.View.MeasureSpec.getSize(i), this.mDrawable.getIntrinsicWidth());
            setMeasuredDimension(min, (int) java.lang.Math.min(android.view.View.MeasureSpec.getSize(i2), min / this.mAspectRatio));
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setActualWidth(getWidth());
        setActualHeight(getHeight());
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getConsumedLines() {
        return 3;
    }

    public void setActualWidth(int i) {
        this.mActualWidth = i;
        invalidate();
    }

    public int getActualWidth() {
        return this.mActualWidth;
    }

    public void setActualHeight(int i) {
        this.mActualHeight = i;
        invalidate();
    }

    public int getActualHeight() {
        return this.mActualHeight;
    }

    public void setIsolated(boolean z) {
        if (this.mIsIsolated != z) {
            this.mIsIsolated = z;
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) getLayoutParams();
            marginLayoutParams.topMargin = z ? 0 : this.mExtraSpacing;
            setLayoutParams(marginLayoutParams);
        }
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getExtraSpacing() {
        return this.mExtraSpacing;
    }
}
