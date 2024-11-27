package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class BigPictureNotificationImageView extends android.widget.ImageView implements com.android.internal.widget.NotificationDrawableConsumer {
    private static final java.lang.String TAG = com.android.internal.widget.BigPictureNotificationImageView.class.getSimpleName();
    private com.android.internal.widget.NotificationIconManager mIconManager;
    private final int mMaximumDrawableHeight;
    private final int mMaximumDrawableWidth;

    public BigPictureNotificationImageView(android.content.Context context) {
        this(context, null, 0, 0);
    }

    public BigPictureNotificationImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public BigPictureNotificationImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BigPictureNotificationImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        boolean isLowRamDeviceStatic = android.app.ActivityManager.isLowRamDeviceStatic();
        this.mMaximumDrawableWidth = context.getResources().getDimensionPixelSize(isLowRamDeviceStatic ? com.android.internal.R.dimen.notification_big_picture_max_width_low_ram : com.android.internal.R.dimen.notification_big_picture_max_width);
        this.mMaximumDrawableHeight = context.getResources().getDimensionPixelSize(isLowRamDeviceStatic ? com.android.internal.R.dimen.notification_big_picture_max_height_low_ram : com.android.internal.R.dimen.notification_big_picture_max_height);
    }

    public void setIconManager(com.android.internal.widget.NotificationIconManager notificationIconManager) {
        this.mIconManager = notificationIconManager;
    }

    @Override // android.widget.ImageView
    @android.view.RemotableViewMethod(asyncImpl = "setImageURIAsync")
    public void setImageURI(android.net.Uri uri) {
        lambda$setImageURIAsync$2(loadImage(uri));
    }

    @Override // android.widget.ImageView
    public java.lang.Runnable setImageURIAsync(android.net.Uri uri) {
        final android.graphics.drawable.Drawable loadImage = loadImage(uri);
        return new java.lang.Runnable() { // from class: com.android.internal.widget.BigPictureNotificationImageView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.BigPictureNotificationImageView.this.lambda$setImageURIAsync$0(loadImage);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setImageURIAsync$0(android.graphics.drawable.Drawable drawable) {
        lambda$setImageURIAsync$2(drawable);
    }

    @Override // android.widget.ImageView
    @android.view.RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(android.graphics.drawable.Icon icon) {
        if (this.mIconManager != null) {
            this.mIconManager.updateIcon(this, icon).run();
        } else {
            lambda$setImageURIAsync$2(loadImage(icon));
        }
    }

    @Override // android.widget.ImageView
    public java.lang.Runnable setImageIconAsync(android.graphics.drawable.Icon icon) {
        if (this.mIconManager != null) {
            return this.mIconManager.updateIcon(this, icon);
        }
        final android.graphics.drawable.Drawable loadImage = loadImage(icon);
        return new java.lang.Runnable() { // from class: com.android.internal.widget.BigPictureNotificationImageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.BigPictureNotificationImageView.this.lambda$setImageIconAsync$1(loadImage);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setImageIconAsync$1(android.graphics.drawable.Drawable drawable) {
        lambda$setImageURIAsync$2(drawable);
    }

    private android.graphics.drawable.Drawable loadImage(android.net.Uri uri) {
        if (uri == null) {
            return null;
        }
        return com.android.internal.widget.LocalImageResolver.resolveImage(uri, this.mContext, this.mMaximumDrawableWidth, this.mMaximumDrawableHeight);
    }

    private android.graphics.drawable.Drawable loadImage(android.graphics.drawable.Icon icon) {
        if (icon == null) {
            return null;
        }
        android.graphics.drawable.Drawable resolveImage = com.android.internal.widget.LocalImageResolver.resolveImage(icon, this.mContext, this.mMaximumDrawableWidth, this.mMaximumDrawableHeight);
        if (resolveImage == null) {
            return icon.loadDrawable(this.mContext);
        }
        return resolveImage;
    }
}
