package android.filterfw.io;

/* loaded from: classes.dex */
public class PatternScanner {
    private java.util.regex.Pattern mIgnorePattern;
    private java.lang.String mInput;
    private int mOffset = 0;
    private int mLineNo = 0;
    private int mStartOfLine = 0;

    public PatternScanner(java.lang.String str) {
        this.mInput = str;
    }

    public PatternScanner(java.lang.String str, java.util.regex.Pattern pattern) {
        this.mInput = str;
        this.mIgnorePattern = pattern;
        skip(this.mIgnorePattern);
    }

    public java.lang.String tryEat(java.util.regex.Pattern pattern) {
        java.lang.String str;
        if (this.mIgnorePattern != null) {
            skip(this.mIgnorePattern);
        }
        java.util.regex.Matcher matcher = pattern.matcher(this.mInput);
        matcher.region(this.mOffset, this.mInput.length());
        if (!matcher.lookingAt()) {
            str = null;
        } else {
            updateLineCount(this.mOffset, matcher.end());
            this.mOffset = matcher.end();
            str = this.mInput.substring(matcher.start(), matcher.end());
        }
        if (str != null && this.mIgnorePattern != null) {
            skip(this.mIgnorePattern);
        }
        return str;
    }

    public java.lang.String eat(java.util.regex.Pattern pattern, java.lang.String str) {
        java.lang.String tryEat = tryEat(pattern);
        if (tryEat == null) {
            throw new java.lang.RuntimeException(unexpectedTokenMessage(str));
        }
        return tryEat;
    }

    public boolean peek(java.util.regex.Pattern pattern) {
        if (this.mIgnorePattern != null) {
            skip(this.mIgnorePattern);
        }
        java.util.regex.Matcher matcher = pattern.matcher(this.mInput);
        matcher.region(this.mOffset, this.mInput.length());
        return matcher.lookingAt();
    }

    public void skip(java.util.regex.Pattern pattern) {
        java.util.regex.Matcher matcher = pattern.matcher(this.mInput);
        matcher.region(this.mOffset, this.mInput.length());
        if (matcher.lookingAt()) {
            updateLineCount(this.mOffset, matcher.end());
            this.mOffset = matcher.end();
        }
    }

    public boolean atEnd() {
        return this.mOffset >= this.mInput.length();
    }

    public int lineNo() {
        return this.mLineNo;
    }

    public java.lang.String unexpectedTokenMessage(java.lang.String str) {
        return "Unexpected token on line " + (this.mLineNo + 1) + " after '" + this.mInput.substring(this.mStartOfLine, this.mOffset) + "' <- Expected " + str + "!";
    }

    public void updateLineCount(int i, int i2) {
        while (i < i2) {
            if (this.mInput.charAt(i) == '\n') {
                this.mLineNo++;
                this.mStartOfLine = i + 1;
            }
            i++;
        }
    }
}
