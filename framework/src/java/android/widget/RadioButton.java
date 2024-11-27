package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class RadioButton extends android.widget.CompoundButton {
    public RadioButton(android.content.Context context) {
        this(context, null);
    }

    public RadioButton(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842878);
    }

    public RadioButton(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RadioButton(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void toggle() {
        if (!isChecked()) {
            super.toggle();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.RadioButton.class.getName();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (getParent() instanceof android.widget.RadioGroup) {
            android.widget.RadioGroup radioGroup = (android.widget.RadioGroup) getParent();
            if (radioGroup.getOrientation() == 0) {
                accessibilityNodeInfo.setCollectionItemInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(0, 1, radioGroup.getIndexWithinVisibleButtons(this), 1, false, isChecked()));
            } else {
                accessibilityNodeInfo.setCollectionItemInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(radioGroup.getIndexWithinVisibleButtons(this), 1, 0, 1, false, isChecked()));
            }
        }
    }

    @Override // android.widget.CompoundButton
    protected java.lang.CharSequence getButtonStateDescription() {
        if (isChecked()) {
            return getResources().getString(com.android.internal.R.string.selected);
        }
        return getResources().getString(com.android.internal.R.string.not_selected);
    }
}
