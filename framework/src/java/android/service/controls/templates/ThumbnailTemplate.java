package android.service.controls.templates;

/* loaded from: classes3.dex */
public final class ThumbnailTemplate extends android.service.controls.templates.ControlTemplate {
    private static final java.lang.String KEY_ACTIVE = "key_active";
    private static final java.lang.String KEY_CONTENT_DESCRIPTION = "key_content_description";
    private static final java.lang.String KEY_ICON = "key_icon";
    private static final int TYPE = 3;
    private final boolean mActive;
    private final java.lang.CharSequence mContentDescription;
    private final android.graphics.drawable.Icon mThumbnail;

    public ThumbnailTemplate(java.lang.String str, boolean z, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) {
        super(str);
        com.android.internal.util.Preconditions.checkNotNull(icon);
        com.android.internal.util.Preconditions.checkNotNull(charSequence);
        this.mActive = z;
        this.mThumbnail = icon;
        this.mContentDescription = charSequence;
    }

    ThumbnailTemplate(android.os.Bundle bundle) {
        super(bundle);
        this.mActive = bundle.getBoolean(KEY_ACTIVE);
        this.mThumbnail = (android.graphics.drawable.Icon) bundle.getParcelable(KEY_ICON, android.graphics.drawable.Icon.class);
        this.mContentDescription = bundle.getCharSequence(KEY_CONTENT_DESCRIPTION, "");
    }

    public boolean isActive() {
        return this.mActive;
    }

    public android.graphics.drawable.Icon getThumbnail() {
        return this.mThumbnail;
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 3;
    }

    @Override // android.service.controls.templates.ControlTemplate
    public void prepareTemplateForBinder(android.content.Context context) {
        rescaleThumbnail(context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.controls_thumbnail_image_max_width), context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.controls_thumbnail_image_max_height));
    }

    private void rescaleThumbnail(int i, int i2) {
        this.mThumbnail.scaleDownIfNecessary(i, i2);
    }

    @Override // android.service.controls.templates.ControlTemplate
    android.os.Bundle getDataBundle() {
        android.os.Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean(KEY_ACTIVE, this.mActive);
        dataBundle.putObject(KEY_ICON, this.mThumbnail);
        dataBundle.putObject(KEY_CONTENT_DESCRIPTION, this.mContentDescription);
        return dataBundle;
    }
}
