package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class CheckBox extends android.widget.CompoundButton {
    public CheckBox(android.content.Context context) {
        this(context, null);
    }

    public CheckBox(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842860);
    }

    public CheckBox(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CheckBox(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.CheckBox.class.getName();
    }
}
