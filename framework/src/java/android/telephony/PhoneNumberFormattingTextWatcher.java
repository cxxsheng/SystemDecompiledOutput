package android.telephony;

/* loaded from: classes3.dex */
public class PhoneNumberFormattingTextWatcher implements android.text.TextWatcher {
    private com.android.i18n.phonenumbers.AsYouTypeFormatter mFormatter;
    private boolean mSelfChange;
    private boolean mStopFormatting;

    public PhoneNumberFormattingTextWatcher() {
        this(java.util.Locale.getDefault().getCountry());
    }

    public PhoneNumberFormattingTextWatcher(java.lang.String str) {
        this.mSelfChange = false;
        if (str == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mFormatter = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance().getAsYouTypeFormatter(str);
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        if (!this.mSelfChange && !this.mStopFormatting && i2 > 0 && hasSeparator(charSequence, i, i2)) {
            stopFormatting();
        }
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        if (!this.mSelfChange && !this.mStopFormatting && i3 > 0 && hasSeparator(charSequence, i, i3)) {
            stopFormatting();
        }
    }

    @Override // android.text.TextWatcher
    public synchronized void afterTextChanged(android.text.Editable editable) {
        boolean z = true;
        if (this.mStopFormatting) {
            if (editable.length() == 0) {
                z = false;
            }
            this.mStopFormatting = z;
            return;
        }
        if (this.mSelfChange) {
            return;
        }
        java.lang.String reformat = reformat(editable, android.text.Selection.getSelectionEnd(editable));
        if (reformat != null) {
            int rememberedPosition = this.mFormatter.getRememberedPosition();
            this.mSelfChange = true;
            editable.replace(0, editable.length(), reformat, 0, reformat.length());
            if (reformat.equals(editable.toString())) {
                android.text.Selection.setSelection(editable, rememberedPosition);
            }
            this.mSelfChange = false;
        }
        for (android.text.style.TtsSpan ttsSpan : (android.text.style.TtsSpan[]) editable.getSpans(0, editable.length(), android.text.style.TtsSpan.class)) {
            editable.removeSpan(ttsSpan);
        }
        android.telephony.PhoneNumberUtils.ttsSpanAsPhoneNumber(editable, 0, editable.length());
    }

    private java.lang.String reformat(java.lang.CharSequence charSequence, int i) {
        int i2 = i - 1;
        this.mFormatter.clear();
        int length = charSequence.length();
        java.lang.String str = null;
        char c = 0;
        boolean z = false;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = charSequence.charAt(i3);
            if (android.telephony.PhoneNumberUtils.isNonSeparator(charAt)) {
                if (c != 0) {
                    str = getFormattedNumber(c, z);
                    z = false;
                }
                c = charAt;
            }
            if (i3 == i2) {
                z = true;
            }
        }
        if (c != 0) {
            return getFormattedNumber(c, z);
        }
        return str;
    }

    private java.lang.String getFormattedNumber(char c, boolean z) {
        return z ? this.mFormatter.inputDigitAndRememberPosition(c) : this.mFormatter.inputDigit(c);
    }

    private void stopFormatting() {
        this.mStopFormatting = true;
        this.mFormatter.clear();
    }

    private boolean hasSeparator(java.lang.CharSequence charSequence, int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            if (!android.telephony.PhoneNumberUtils.isNonSeparator(charSequence.charAt(i3))) {
                return true;
            }
        }
        return false;
    }
}
