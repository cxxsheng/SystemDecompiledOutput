package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class TwoLineListItem extends android.widget.RelativeLayout {
    private android.widget.TextView mText1;
    private android.widget.TextView mText2;

    public TwoLineListItem(android.content.Context context) {
        this(context, null, 0);
    }

    public TwoLineListItem(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoLineListItem(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TwoLineListItem(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TwoLineListItem, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.TwoLineListItem, attributeSet, obtainStyledAttributes, i, i2);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mText1 = (android.widget.TextView) findViewById(16908308);
        this.mText2 = (android.widget.TextView) findViewById(16908309);
    }

    public android.widget.TextView getText1() {
        return this.mText1;
    }

    public android.widget.TextView getText2() {
        return this.mText2;
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.TwoLineListItem.class.getName();
    }
}
