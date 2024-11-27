package android.inputmethodservice;

/* loaded from: classes2.dex */
public class ExtractEditText extends android.widget.EditText {
    private android.inputmethodservice.InputMethodService mIME;
    private int mSettingExtractedText;

    public ExtractEditText(android.content.Context context) {
        super(context, null);
    }

    public ExtractEditText(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet, 16842862);
    }

    public ExtractEditText(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ExtractEditText(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    void setIME(android.inputmethodservice.InputMethodService inputMethodService) {
        this.mIME = inputMethodService;
    }

    public void startInternalChanges() {
        this.mSettingExtractedText++;
    }

    public void finishInternalChanges() {
        this.mSettingExtractedText--;
    }

    @Override // android.widget.TextView
    public void setExtractedText(android.view.inputmethod.ExtractedText extractedText) {
        try {
            this.mSettingExtractedText++;
            super.setExtractedText(extractedText);
        } finally {
            this.mSettingExtractedText--;
        }
    }

    @Override // android.widget.TextView
    protected void onSelectionChanged(int i, int i2) {
        if (this.mSettingExtractedText == 0 && this.mIME != null && i >= 0 && i2 >= 0) {
            this.mIME.onExtractedSelectionChanged(i, i2);
        }
    }

    @Override // android.view.View
    public boolean performClick() {
        if (!super.performClick() && this.mIME != null) {
            this.mIME.onExtractedTextClicked();
            return true;
        }
        return false;
    }

    @Override // android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        if (i == 16908319 || i == 16908340) {
            return super.onTextContextMenuItem(i);
        }
        if (this.mIME != null && this.mIME.onExtractTextContextMenuItem(i)) {
            if (i == 16908321 || i == 16908322) {
                stopTextActionMode();
                return true;
            }
            return true;
        }
        return super.onTextContextMenuItem(i);
    }

    @Override // android.widget.TextView
    public boolean isInputMethodTarget() {
        return true;
    }

    public boolean hasVerticalScrollBar() {
        return computeVerticalScrollRange() > computeVerticalScrollExtent();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return isEnabled();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return isEnabled();
    }

    @Override // android.view.View
    public boolean hasFocus() {
        return isEnabled();
    }

    @Override // android.widget.TextView
    protected void viewClicked(android.view.inputmethod.InputMethodManager inputMethodManager) {
        if (this.mIME != null) {
            this.mIME.onViewClicked(false);
        }
    }

    @Override // android.widget.TextView
    public boolean isInExtractedMode() {
        return true;
    }

    @Override // android.widget.TextView
    protected void deleteText_internal(int i, int i2) {
        this.mIME.onExtractedDeleteText(i, i2);
    }

    @Override // android.widget.TextView
    protected void replaceText_internal(int i, int i2, java.lang.CharSequence charSequence) {
        this.mIME.onExtractedReplaceText(i, i2, charSequence);
    }

    @Override // android.widget.TextView
    protected void setSpan_internal(java.lang.Object obj, int i, int i2, int i3) {
        this.mIME.onExtractedSetSpan(obj, i, i2, i3);
    }

    @Override // android.widget.TextView
    protected void setCursorPosition_internal(int i, int i2) {
        this.mIME.onExtractedSelectionChanged(i, i2);
    }
}
