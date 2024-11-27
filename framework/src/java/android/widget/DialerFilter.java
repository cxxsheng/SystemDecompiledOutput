package android.widget;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class DialerFilter extends android.widget.RelativeLayout {
    public static final int DIGITS_AND_LETTERS = 1;
    public static final int DIGITS_AND_LETTERS_NO_DIGITS = 2;
    public static final int DIGITS_AND_LETTERS_NO_LETTERS = 3;
    public static final int DIGITS_ONLY = 4;
    public static final int LETTERS_ONLY = 5;
    android.widget.EditText mDigits;
    android.widget.EditText mHint;
    android.widget.ImageView mIcon;
    android.text.InputFilter[] mInputFilters;
    private boolean mIsQwerty;
    android.widget.EditText mLetters;
    int mMode;
    android.widget.EditText mPrimary;

    public DialerFilter(android.content.Context context) {
        super(context);
    }

    public DialerFilter(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mInputFilters = new android.text.InputFilter[]{new android.text.InputFilter.AllCaps()};
        this.mHint = (android.widget.EditText) findViewById(16908293);
        if (this.mHint == null) {
            throw new java.lang.IllegalStateException("DialerFilter must have a child EditText named hint");
        }
        this.mHint.setFilters(this.mInputFilters);
        this.mLetters = this.mHint;
        this.mLetters.setKeyListener(android.text.method.TextKeyListener.getInstance());
        this.mLetters.setMovementMethod(null);
        this.mLetters.setFocusable(false);
        this.mPrimary = (android.widget.EditText) findViewById(16908300);
        if (this.mPrimary == null) {
            throw new java.lang.IllegalStateException("DialerFilter must have a child EditText named primary");
        }
        this.mPrimary.setFilters(this.mInputFilters);
        this.mDigits = this.mPrimary;
        this.mDigits.setKeyListener(android.text.method.DialerKeyListener.getInstance());
        this.mDigits.setMovementMethod(null);
        this.mDigits.setFocusable(false);
        this.mIcon = (android.widget.ImageView) findViewById(16908294);
        setFocusable(true);
        this.mIsQwerty = true;
        setMode(1);
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (this.mIcon != null) {
            this.mIcon.setVisibility(z ? 0 : 8);
        }
    }

    public boolean isQwertyKeyboard() {
        return this.mIsQwerty;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        boolean z = false;
        switch (i) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 66:
                break;
            case 67:
                switch (this.mMode) {
                    case 1:
                        z = this.mDigits.onKeyDown(i, keyEvent) & this.mLetters.onKeyDown(i, keyEvent);
                        break;
                    case 2:
                        z = this.mLetters.onKeyDown(i, keyEvent);
                        if (this.mLetters.getText().length() == this.mDigits.getText().length()) {
                            setMode(1);
                            break;
                        }
                        break;
                    case 3:
                        if (this.mDigits.getText().length() == this.mLetters.getText().length()) {
                            this.mLetters.onKeyDown(i, keyEvent);
                            setMode(1);
                        }
                        z = this.mDigits.onKeyDown(i, keyEvent);
                        break;
                    case 4:
                        z = this.mDigits.onKeyDown(i, keyEvent);
                        break;
                    case 5:
                        z = this.mLetters.onKeyDown(i, keyEvent);
                        break;
                }
            default:
                switch (this.mMode) {
                    case 1:
                        z = this.mLetters.onKeyDown(i, keyEvent);
                        if (android.view.KeyEvent.isModifierKey(i)) {
                            this.mDigits.onKeyDown(i, keyEvent);
                            z = true;
                            break;
                        } else if (keyEvent.isPrintingKey() || i == 62 || i == 61) {
                            if (keyEvent.getMatch(android.text.method.DialerKeyListener.CHARACTERS) != 0) {
                                z &= this.mDigits.onKeyDown(i, keyEvent);
                                break;
                            } else {
                                setMode(2);
                                break;
                            }
                        }
                        break;
                    case 2:
                    case 5:
                        z = this.mLetters.onKeyDown(i, keyEvent);
                        break;
                    case 3:
                    case 4:
                        z = this.mDigits.onKeyDown(i, keyEvent);
                        break;
                }
        }
        if (z) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        return this.mLetters.onKeyUp(i, keyEvent) || this.mDigits.onKeyUp(i, keyEvent);
    }

    public int getMode() {
        return this.mMode;
    }

    public void setMode(int i) {
        switch (i) {
            case 1:
                makeDigitsPrimary();
                this.mLetters.setVisibility(0);
                this.mDigits.setVisibility(0);
                break;
            case 2:
                makeLettersPrimary();
                this.mLetters.setVisibility(0);
                this.mDigits.setVisibility(4);
                break;
            case 3:
                makeDigitsPrimary();
                this.mLetters.setVisibility(4);
                this.mDigits.setVisibility(0);
                break;
            case 4:
                makeDigitsPrimary();
                this.mLetters.setVisibility(8);
                this.mDigits.setVisibility(0);
                break;
            case 5:
                makeLettersPrimary();
                this.mLetters.setVisibility(0);
                this.mDigits.setVisibility(8);
                break;
        }
        int i2 = this.mMode;
        this.mMode = i;
        onModeChange(i2, i);
    }

    private void makeLettersPrimary() {
        if (this.mPrimary == this.mDigits) {
            swapPrimaryAndHint(true);
        }
    }

    private void makeDigitsPrimary() {
        if (this.mPrimary == this.mLetters) {
            swapPrimaryAndHint(false);
        }
    }

    private void swapPrimaryAndHint(boolean z) {
        android.text.Editable text = this.mLetters.getText();
        android.text.Editable text2 = this.mDigits.getText();
        android.text.method.KeyListener keyListener = this.mLetters.getKeyListener();
        android.text.method.KeyListener keyListener2 = this.mDigits.getKeyListener();
        if (z) {
            this.mLetters = this.mPrimary;
            this.mDigits = this.mHint;
        } else {
            this.mLetters = this.mHint;
            this.mDigits = this.mPrimary;
        }
        this.mLetters.setKeyListener(keyListener);
        this.mLetters.lambda$setTextAsync$0(text);
        android.text.Editable text3 = this.mLetters.getText();
        android.text.Selection.setSelection(text3, text3.length());
        this.mDigits.setKeyListener(keyListener2);
        this.mDigits.lambda$setTextAsync$0(text2);
        android.text.Editable text4 = this.mDigits.getText();
        android.text.Selection.setSelection(text4, text4.length());
        this.mPrimary.setFilters(this.mInputFilters);
        this.mHint.setFilters(this.mInputFilters);
    }

    public java.lang.CharSequence getLetters() {
        if (this.mLetters.getVisibility() == 0) {
            return this.mLetters.getText();
        }
        return "";
    }

    public java.lang.CharSequence getDigits() {
        if (this.mDigits.getVisibility() == 0) {
            return this.mDigits.getText();
        }
        return "";
    }

    public java.lang.CharSequence getFilterText() {
        if (this.mMode != 4) {
            return getLetters();
        }
        return getDigits();
    }

    public void append(java.lang.String str) {
        switch (this.mMode) {
            case 1:
                this.mDigits.getText().append((java.lang.CharSequence) str);
                this.mLetters.getText().append((java.lang.CharSequence) str);
                break;
            case 2:
            case 5:
                this.mLetters.getText().append((java.lang.CharSequence) str);
                break;
            case 3:
            case 4:
                this.mDigits.getText().append((java.lang.CharSequence) str);
                break;
        }
    }

    public void clearText() {
        this.mLetters.getText().clear();
        this.mDigits.getText().clear();
        if (this.mIsQwerty) {
            setMode(1);
        } else {
            setMode(4);
        }
    }

    public void setLettersWatcher(android.text.TextWatcher textWatcher) {
        android.text.Editable text = this.mLetters.getText();
        text.setSpan(textWatcher, 0, text.length(), 18);
    }

    public void setDigitsWatcher(android.text.TextWatcher textWatcher) {
        android.text.Editable text = this.mDigits.getText();
        text.setSpan(textWatcher, 0, text.length(), 18);
    }

    public void setFilterWatcher(android.text.TextWatcher textWatcher) {
        if (this.mMode != 4) {
            setLettersWatcher(textWatcher);
        } else {
            setDigitsWatcher(textWatcher);
        }
    }

    public void removeFilterWatcher(android.text.TextWatcher textWatcher) {
        android.text.Editable text;
        if (this.mMode != 4) {
            text = this.mLetters.getText();
        } else {
            text = this.mDigits.getText();
        }
        text.removeSpan(textWatcher);
    }

    protected void onModeChange(int i, int i2) {
    }
}
