package android.inputmethodservice;

/* loaded from: classes2.dex */
class ExtractButton extends android.widget.Button {
    public ExtractButton(android.content.Context context) {
        super(context, null);
    }

    public ExtractButton(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    public ExtractButton(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ExtractButton(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return isEnabled() && getVisibility() == 0;
    }
}
