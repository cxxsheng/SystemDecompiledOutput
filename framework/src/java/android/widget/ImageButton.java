package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class ImageButton extends android.widget.ImageView {
    public ImageButton(android.content.Context context) {
        this(context, null);
    }

    public ImageButton(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842866);
    }

    public ImageButton(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ImageButton(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setFocusable(true);
    }

    @Override // android.view.View
    protected boolean onSetAlpha(int i) {
        return false;
    }

    @Override // android.widget.ImageView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ImageButton.class.getName();
    }

    @Override // android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        int i2;
        if (getPointerIcon() == null && isClickable() && isEnabled() && motionEvent.isFromSource(8194)) {
            if (android.view.flags.Flags.enableArrowIconOnHoverWhenClickable()) {
                i2 = 1000;
            } else {
                i2 = 1002;
            }
            return android.view.PointerIcon.getSystemIcon(getContext(), i2);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }
}
