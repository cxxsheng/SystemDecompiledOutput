package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class Button extends android.widget.TextView {
    public Button(android.content.Context context) {
        this(context, null);
    }

    public Button(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842824);
    }

    public Button(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Button(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.Button.class.getName();
    }

    @Override // android.widget.TextView, android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        if (!android.view.flags.Flags.enableArrowIconOnHoverWhenClickable() && getPointerIcon() == null && isClickable() && isEnabled() && motionEvent.isFromSource(8194)) {
            return android.view.PointerIcon.getSystemIcon(getContext(), 1002);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }
}
