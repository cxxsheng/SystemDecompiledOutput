package android.util;

/* loaded from: classes3.dex */
public class IndentingPrintWriter extends java.io.PrintWriter {
    private char[] mCurrentIndent;
    private int mCurrentLength;
    private boolean mEmptyLine;
    private java.lang.StringBuilder mIndentBuilder;
    private char[] mSingleChar;
    private final java.lang.String mSingleIndent;
    private final int mWrapLength;

    public IndentingPrintWriter(java.io.Writer writer) {
        this(writer, "  ", -1);
    }

    public IndentingPrintWriter(java.io.Writer writer, java.lang.String str) {
        this(writer, str, null, -1);
    }

    public IndentingPrintWriter(java.io.Writer writer, java.lang.String str, java.lang.String str2) {
        this(writer, str, str2, -1);
    }

    public IndentingPrintWriter(java.io.Writer writer, java.lang.String str, int i) {
        this(writer, str, null, i);
    }

    public IndentingPrintWriter(java.io.Writer writer, java.lang.String str, java.lang.String str2, int i) {
        super(writer);
        this.mIndentBuilder = new java.lang.StringBuilder();
        this.mEmptyLine = true;
        this.mSingleChar = new char[1];
        this.mSingleIndent = str;
        this.mWrapLength = i;
        if (str2 != null) {
            this.mIndentBuilder.append(str2);
        }
    }

    @java.lang.Deprecated
    public android.util.IndentingPrintWriter setIndent(java.lang.String str) {
        this.mIndentBuilder.setLength(0);
        this.mIndentBuilder.append(str);
        this.mCurrentIndent = null;
        return this;
    }

    @java.lang.Deprecated
    public android.util.IndentingPrintWriter setIndent(int i) {
        this.mIndentBuilder.setLength(0);
        for (int i2 = 0; i2 < i; i2++) {
            increaseIndent();
        }
        return this;
    }

    public android.util.IndentingPrintWriter increaseIndent() {
        this.mIndentBuilder.append(this.mSingleIndent);
        this.mCurrentIndent = null;
        return this;
    }

    public android.util.IndentingPrintWriter decreaseIndent() {
        this.mIndentBuilder.delete(0, this.mSingleIndent.length());
        this.mCurrentIndent = null;
        return this;
    }

    public android.util.IndentingPrintWriter print(java.lang.String str, java.lang.Object obj) {
        java.lang.String valueOf;
        if (obj == null) {
            valueOf = "null";
        } else if (obj.getClass().isArray()) {
            if (obj.getClass() == boolean[].class) {
                valueOf = java.util.Arrays.toString((boolean[]) obj);
            } else if (obj.getClass() == byte[].class) {
                valueOf = java.util.Arrays.toString((byte[]) obj);
            } else if (obj.getClass() == char[].class) {
                valueOf = java.util.Arrays.toString((char[]) obj);
            } else if (obj.getClass() == double[].class) {
                valueOf = java.util.Arrays.toString((double[]) obj);
            } else if (obj.getClass() == float[].class) {
                valueOf = java.util.Arrays.toString((float[]) obj);
            } else if (obj.getClass() == int[].class) {
                valueOf = java.util.Arrays.toString((int[]) obj);
            } else if (obj.getClass() == long[].class) {
                valueOf = java.util.Arrays.toString((long[]) obj);
            } else if (obj.getClass() == short[].class) {
                valueOf = java.util.Arrays.toString((short[]) obj);
            } else {
                valueOf = java.util.Arrays.toString((java.lang.Object[]) obj);
            }
        } else {
            valueOf = java.lang.String.valueOf(obj);
        }
        print(str + "=" + valueOf + " ");
        return this;
    }

    public android.util.IndentingPrintWriter printHexInt(java.lang.String str, int i) {
        print(str + "=0x" + java.lang.Integer.toHexString(i) + " ");
        return this;
    }

    @Override // java.io.PrintWriter
    public void println() {
        write(10);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(int i) {
        this.mSingleChar[0] = (char) i;
        write(this.mSingleChar, 0, 1);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(java.lang.String str, int i, int i2) {
        char[] cArr = new char[i2];
        str.getChars(i, i2 - i, cArr, 0);
        write(cArr, 0, i2);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        int length = this.mIndentBuilder.length();
        int i3 = i2 + i;
        int i4 = i;
        while (i < i3) {
            int i5 = i + 1;
            char c = cArr[i];
            this.mCurrentLength++;
            if (c == '\n') {
                maybeWriteIndent();
                super.write(cArr, i4, i5 - i4);
                this.mEmptyLine = true;
                this.mCurrentLength = 0;
                i4 = i5;
            }
            if (this.mWrapLength > 0 && this.mCurrentLength >= this.mWrapLength - length) {
                if (!this.mEmptyLine) {
                    super.write(10);
                    this.mEmptyLine = true;
                    this.mCurrentLength = i5 - i4;
                } else {
                    maybeWriteIndent();
                    super.write(cArr, i4, i5 - i4);
                    super.write(10);
                    this.mEmptyLine = true;
                    this.mCurrentLength = 0;
                    i4 = i5;
                }
            }
            i = i5;
        }
        if (i4 != i) {
            maybeWriteIndent();
            super.write(cArr, i4, i - i4);
        }
    }

    private void maybeWriteIndent() {
        if (this.mEmptyLine) {
            this.mEmptyLine = false;
            if (this.mIndentBuilder.length() != 0) {
                if (this.mCurrentIndent == null) {
                    this.mCurrentIndent = this.mIndentBuilder.toString().toCharArray();
                }
                super.write(this.mCurrentIndent, 0, this.mCurrentIndent.length);
            }
        }
    }
}
