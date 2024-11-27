package android.widget;

/* loaded from: classes4.dex */
public class TextSwitcher extends android.widget.ViewSwitcher {
    public TextSwitcher(android.content.Context context) {
        super(context);
    }

    public TextSwitcher(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.ViewSwitcher, android.widget.ViewAnimator, android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof android.widget.TextView)) {
            throw new java.lang.IllegalArgumentException("TextSwitcher children must be instances of TextView");
        }
        super.addView(view, i, layoutParams);
    }

    public void setText(java.lang.CharSequence charSequence) {
        ((android.widget.TextView) getNextView()).lambda$setTextAsync$0(charSequence);
        showNext();
    }

    public void setCurrentText(java.lang.CharSequence charSequence) {
        ((android.widget.TextView) getCurrentView()).lambda$setTextAsync$0(charSequence);
    }

    @Override // android.widget.ViewSwitcher, android.widget.ViewAnimator, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.TextSwitcher.class.getName();
    }
}
