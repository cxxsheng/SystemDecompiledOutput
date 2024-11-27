package android.widget;

/* loaded from: classes4.dex */
public class MultiAutoCompleteTextView extends android.widget.AutoCompleteTextView {
    private android.widget.MultiAutoCompleteTextView.Tokenizer mTokenizer;

    public interface Tokenizer {
        int findTokenEnd(java.lang.CharSequence charSequence, int i);

        int findTokenStart(java.lang.CharSequence charSequence, int i);

        java.lang.CharSequence terminateToken(java.lang.CharSequence charSequence);
    }

    public MultiAutoCompleteTextView(android.content.Context context) {
        this(context, null);
    }

    public MultiAutoCompleteTextView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842859);
    }

    public MultiAutoCompleteTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiAutoCompleteTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    void finishInit() {
    }

    public void setTokenizer(android.widget.MultiAutoCompleteTextView.Tokenizer tokenizer) {
        this.mTokenizer = tokenizer;
    }

    @Override // android.widget.AutoCompleteTextView
    protected void performFiltering(java.lang.CharSequence charSequence, int i) {
        if (enoughToFilter()) {
            int selectionEnd = getSelectionEnd();
            performFiltering(charSequence, this.mTokenizer.findTokenStart(charSequence, selectionEnd), selectionEnd, i);
            return;
        }
        dismissDropDown();
        android.widget.Filter filter = getFilter();
        if (filter != null) {
            filter.filter(null);
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public boolean enoughToFilter() {
        android.text.Editable text = getText();
        int selectionEnd = getSelectionEnd();
        if (selectionEnd < 0 || this.mTokenizer == null || selectionEnd - this.mTokenizer.findTokenStart(text, selectionEnd) < getThreshold()) {
            return false;
        }
        return true;
    }

    @Override // android.widget.AutoCompleteTextView
    public void performValidation() {
        android.widget.AutoCompleteTextView.Validator validator = getValidator();
        if (validator == null || this.mTokenizer == null) {
            return;
        }
        android.text.Editable text = getText();
        int length = getText().length();
        while (length > 0) {
            int findTokenStart = this.mTokenizer.findTokenStart(text, length);
            java.lang.CharSequence subSequence = text.subSequence(findTokenStart, this.mTokenizer.findTokenEnd(text, findTokenStart));
            if (android.text.TextUtils.isEmpty(subSequence)) {
                text.replace(findTokenStart, length, "");
            } else if (!validator.isValid(subSequence)) {
                text.replace(findTokenStart, length, this.mTokenizer.terminateToken(validator.fixText(subSequence)));
            }
            length = findTokenStart;
        }
    }

    protected void performFiltering(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        getFilter().filter(charSequence.subSequence(i, i2), this);
    }

    @Override // android.widget.AutoCompleteTextView
    protected void replaceText(java.lang.CharSequence charSequence) {
        clearComposingText();
        int selectionEnd = getSelectionEnd();
        int findTokenStart = this.mTokenizer.findTokenStart(getText(), selectionEnd);
        android.text.Editable text = getText();
        android.text.method.QwertyKeyListener.markAsReplaced(text, findTokenStart, selectionEnd, android.text.TextUtils.substring(text, findTokenStart, selectionEnd));
        text.replace(findTokenStart, selectionEnd, this.mTokenizer.terminateToken(charSequence));
    }

    @Override // android.widget.AutoCompleteTextView, android.widget.EditText, android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.MultiAutoCompleteTextView.class.getName();
    }

    public static class CommaTokenizer implements android.widget.MultiAutoCompleteTextView.Tokenizer {
        @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
        public int findTokenStart(java.lang.CharSequence charSequence, int i) {
            int i2 = i;
            while (i2 > 0 && charSequence.charAt(i2 - 1) != ',') {
                i2--;
            }
            while (i2 < i && charSequence.charAt(i2) == ' ') {
                i2++;
            }
            return i2;
        }

        @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
        public int findTokenEnd(java.lang.CharSequence charSequence, int i) {
            int length = charSequence.length();
            while (i < length) {
                if (charSequence.charAt(i) == ',') {
                    return i;
                }
                i++;
            }
            return length;
        }

        @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
        public java.lang.CharSequence terminateToken(java.lang.CharSequence charSequence) {
            int length = charSequence.length();
            while (length > 0 && charSequence.charAt(length - 1) == ' ') {
                length--;
            }
            if (length > 0 && charSequence.charAt(length - 1) == ',') {
                return charSequence;
            }
            if (charSequence instanceof android.text.Spanned) {
                android.text.SpannableString spannableString = new android.text.SpannableString(((java.lang.Object) charSequence) + ", ");
                android.text.TextUtils.copySpansFrom((android.text.Spanned) charSequence, 0, charSequence.length(), java.lang.Object.class, spannableString, 0);
                return spannableString;
            }
            return ((java.lang.Object) charSequence) + ", ";
        }
    }
}
