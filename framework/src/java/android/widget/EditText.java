package android.widget;

/* loaded from: classes4.dex */
public class EditText extends android.widget.TextView {
    private static final int ID_BOLD = 16908379;
    private static final int ID_ITALIC = 16908380;
    private static final int ID_UNDERLINE = 16908381;
    public static final long LINE_HEIGHT_FOR_LOCALE = 303326708;
    private boolean mStyleShortcutsEnabled;

    public EditText(android.content.Context context) {
        this(context, null);
    }

    public EditText(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842862);
    }

    public EditText(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public EditText(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int indexCount;
        int i3;
        this.mStyleShortcutsEnabled = false;
        android.content.res.Resources.Theme theme = context.getTheme();
        android.content.res.TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.EditText, i, i2);
        try {
            indexCount = obtainStyledAttributes.getIndexCount();
        } finally {
        }
        for (i3 = 0; i3 < indexCount; i3++) {
            int index = obtainStyledAttributes.getIndex(i3);
            switch (index) {
                case 0:
                    this.mStyleShortcutsEnabled = obtainStyledAttributes.getBoolean(index, false);
                    continue;
                default:
                    continue;
            }
        }
        obtainStyledAttributes.recycle();
        obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TextView, i, i2);
        try {
            boolean hasValue = obtainStyledAttributes.hasValue(104);
            setLocalePreferredLineHeightForMinimumUsed(hasValue ? hasValue ? obtainStyledAttributes.getBoolean(104, false) : false : android.app.compat.CompatChanges.isChangeEnabled(LINE_HEIGHT_FOR_LOCALE));
        } finally {
        }
    }

    @Override // android.widget.TextView
    public boolean getFreezesText() {
        return true;
    }

    @Override // android.widget.TextView
    protected boolean getDefaultEditable() {
        return true;
    }

    @Override // android.widget.TextView
    protected android.text.method.MovementMethod getDefaultMovementMethod() {
        return android.text.method.ArrowKeyMovementMethod.getInstance();
    }

    @Override // android.widget.TextView
    public android.text.Editable getText() {
        java.lang.CharSequence text = super.getText();
        if (text == null) {
            return null;
        }
        if (text instanceof android.text.Editable) {
            return (android.text.Editable) text;
        }
        super.setText(text, android.widget.TextView.BufferType.EDITABLE);
        return (android.text.Editable) super.getText();
    }

    @Override // android.widget.TextView
    public void setText(java.lang.CharSequence charSequence, android.widget.TextView.BufferType bufferType) {
        super.setText(charSequence, android.widget.TextView.BufferType.EDITABLE);
    }

    public void setSelection(int i, int i2) {
        android.text.Selection.setSelection(getText(), i, i2);
    }

    public void setSelection(int i) {
        android.text.Selection.setSelection(getText(), i);
    }

    public void selectAll() {
        android.text.Selection.selectAll(getText());
    }

    public void extendSelection(int i) {
        android.text.Selection.extendSelection(getText(), i);
    }

    @Override // android.widget.TextView
    public void setEllipsize(android.text.TextUtils.TruncateAt truncateAt) {
        if (truncateAt == android.text.TextUtils.TruncateAt.MARQUEE) {
            throw new java.lang.IllegalArgumentException("EditText cannot use the ellipsize mode TextUtils.TruncateAt.MARQUEE");
        }
        super.setEllipsize(truncateAt);
    }

    @Override // android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.EditText.class.getName();
    }

    @Override // android.widget.TextView
    protected boolean supportsAutoSizeText() {
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onKeyShortcut(int i, android.view.KeyEvent keyEvent) {
        if (keyEvent.hasModifiers(4096)) {
            switch (i) {
                case 30:
                    if (this.mStyleShortcutsEnabled && hasSelection()) {
                        return onTextContextMenuItem(16908379);
                    }
                    break;
                case 37:
                    if (this.mStyleShortcutsEnabled && hasSelection()) {
                        return onTextContextMenuItem(16908380);
                    }
                    break;
                case 49:
                    if (this.mStyleShortcutsEnabled && hasSelection()) {
                        return onTextContextMenuItem(16908381);
                    }
                    break;
            }
        }
        return super.onKeyShortcut(i, keyEvent);
    }

    @Override // android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        if (i == 16908379 || i == 16908380 || i == 16908381) {
            return performStylingAction(i);
        }
        return super.onTextContextMenuItem(i);
    }

    private boolean performStylingAction(int i) {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (selectionStart < 0 || selectionEnd < 0) {
            return false;
        }
        int min = java.lang.Math.min(selectionStart, selectionEnd);
        int max = java.lang.Math.max(selectionStart, selectionEnd);
        android.text.Editable text = getText();
        if (i == 16908379) {
            return android.text.style.SpanUtils.toggleBold(text, min, max);
        }
        if (i == 16908380) {
            return android.text.style.SpanUtils.toggleItalic(text, min, max);
        }
        if (i != 16908381) {
            return false;
        }
        return android.text.style.SpanUtils.toggleUnderline(text, min, max);
    }

    public void setStyleShortcutsEnabled(boolean z) {
        this.mStyleShortcutsEnabled = z;
    }

    public boolean isStyleShortcutEnabled() {
        return this.mStyleShortcutsEnabled;
    }
}
