package android.widget;

/* loaded from: classes4.dex */
public class ImageSwitcher extends android.widget.ViewSwitcher {
    public ImageSwitcher(android.content.Context context) {
        super(context);
    }

    public ImageSwitcher(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setImageResource(int i) {
        ((android.widget.ImageView) getNextView()).setImageResource(i);
        showNext();
    }

    public void setImageURI(android.net.Uri uri) {
        ((android.widget.ImageView) getNextView()).setImageURI(uri);
        showNext();
    }

    public void setImageDrawable(android.graphics.drawable.Drawable drawable) {
        ((android.widget.ImageView) getNextView()).setImageDrawable(drawable);
        showNext();
    }

    @Override // android.widget.ViewSwitcher, android.widget.ViewAnimator, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ImageSwitcher.class.getName();
    }
}
