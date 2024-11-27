package com.android.internal.util.jobs;

/* loaded from: classes.dex */
public class FastXmlSerializer implements org.xmlpull.v1.XmlSerializer {
    private static final int DEFAULT_BUFFER_LEN = 32768;
    private static final java.lang.String[] ESCAPE_TABLE = {"&#0;", "&#1;", "&#2;", "&#3;", "&#4;", "&#5;", "&#6;", "&#7;", "&#8;", "&#9;", "&#10;", "&#11;", "&#12;", "&#13;", "&#14;", "&#15;", "&#16;", "&#17;", "&#18;", "&#19;", "&#20;", "&#21;", "&#22;", "&#23;", "&#24;", "&#25;", "&#26;", "&#27;", "&#28;", "&#29;", "&#30;", "&#31;", null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null};
    private static java.lang.String sSpace = "                                                              ";
    private final int mBufferLen;
    private java.nio.ByteBuffer mBytes;
    private java.nio.charset.CharsetEncoder mCharset;
    private boolean mInTag;
    private boolean mIndent;
    private boolean mLineStart;
    private int mNesting;
    private java.io.OutputStream mOutputStream;
    private int mPos;
    private final char[] mText;
    private java.io.Writer mWriter;

    public FastXmlSerializer() {
        this(32768);
    }

    public FastXmlSerializer(int i) {
        this.mIndent = false;
        this.mNesting = 0;
        this.mLineStart = true;
        this.mBufferLen = i <= 0 ? 32768 : i;
        this.mText = new char[this.mBufferLen];
        this.mBytes = java.nio.ByteBuffer.allocate(this.mBufferLen);
    }

    private void append(char c) throws java.io.IOException {
        int i = this.mPos;
        if (i >= this.mBufferLen - 1) {
            flush();
            i = this.mPos;
        }
        this.mText[i] = c;
        this.mPos = i + 1;
    }

    private void append(java.lang.String str, int i, int i2) throws java.io.IOException {
        if (i2 > this.mBufferLen) {
            int i3 = i2 + i;
            while (i < i3) {
                int i4 = this.mBufferLen + i;
                append(str, i, i4 < i3 ? this.mBufferLen : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.mPos;
        if (i5 + i2 > this.mBufferLen) {
            flush();
            i5 = this.mPos;
        }
        str.getChars(i, i + i2, this.mText, i5);
        this.mPos = i5 + i2;
    }

    private void append(char[] cArr, int i, int i2) throws java.io.IOException {
        if (i2 > this.mBufferLen) {
            int i3 = i2 + i;
            while (i < i3) {
                int i4 = this.mBufferLen + i;
                append(cArr, i, i4 < i3 ? this.mBufferLen : i3 - i);
                i = i4;
            }
            return;
        }
        int i5 = this.mPos;
        if (i5 + i2 > this.mBufferLen) {
            flush();
            i5 = this.mPos;
        }
        java.lang.System.arraycopy(cArr, i, this.mText, i5, i2);
        this.mPos = i5 + i2;
    }

    private void append(java.lang.String str) throws java.io.IOException {
        append(str, 0, str.length());
    }

    private void appendIndent(int i) throws java.io.IOException {
        int i2 = i * 4;
        if (i2 > sSpace.length()) {
            i2 = sSpace.length();
        }
        append(sSpace, 0, i2);
    }

    private void escapeAndAppendString(java.lang.String str) throws java.io.IOException {
        java.lang.String str2;
        int length = str.length();
        char length2 = (char) ESCAPE_TABLE.length;
        java.lang.String[] strArr = ESCAPE_TABLE;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt < length2 && (str2 = strArr[charAt]) != null) {
                if (i2 < i) {
                    append(str, i2, i - i2);
                }
                i2 = i + 1;
                append(str2);
            }
            i++;
        }
        if (i2 < i) {
            append(str, i2, i - i2);
        }
    }

    private void escapeAndAppendString(char[] cArr, int i, int i2) throws java.io.IOException {
        java.lang.String str;
        char length = (char) ESCAPE_TABLE.length;
        java.lang.String[] strArr = ESCAPE_TABLE;
        int i3 = i2 + i;
        int i4 = i;
        while (i < i3) {
            char c = cArr[i];
            if (c < length && (str = strArr[c]) != null) {
                if (i4 < i) {
                    append(cArr, i4, i - i4);
                }
                i4 = i + 1;
                append(str);
            }
            i++;
        }
        if (i4 < i) {
            append(cArr, i4, i - i4);
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer attribute(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        append(' ');
        if (str != null) {
            append(str);
            append(':');
        }
        append(str2);
        append("=\"");
        escapeAndAppendString(str3);
        append('\"');
        this.mLineStart = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        flush();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer endTag(java.lang.String str, java.lang.String str2) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        this.mNesting--;
        if (this.mInTag) {
            append(" />\n");
        } else {
            if (this.mIndent && this.mLineStart) {
                appendIndent(this.mNesting);
            }
            append("</");
            if (str != null) {
                append(str);
                append(':');
            }
            append(str2);
            append(">\n");
        }
        this.mLineStart = true;
        this.mInTag = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        throw new java.lang.UnsupportedOperationException();
    }

    private void flushBytes() throws java.io.IOException {
        int position = this.mBytes.position();
        if (position > 0) {
            this.mBytes.flip();
            this.mOutputStream.write(this.mBytes.array(), 0, position);
            this.mBytes.clear();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws java.io.IOException {
        if (this.mPos > 0) {
            if (this.mOutputStream != null) {
                java.nio.CharBuffer wrap = java.nio.CharBuffer.wrap(this.mText, 0, this.mPos);
                java.nio.charset.CoderResult encode = this.mCharset.encode(wrap, this.mBytes, true);
                while (!encode.isError()) {
                    if (encode.isOverflow()) {
                        flushBytes();
                        encode = this.mCharset.encode(wrap, this.mBytes, true);
                    } else {
                        flushBytes();
                        this.mOutputStream.flush();
                    }
                }
                throw new java.io.IOException(encode.toString());
            }
            this.mWriter.write(this.mText, 0, this.mPos);
            this.mWriter.flush();
            this.mPos = 0;
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getName() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getNamespace() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getPrefix(java.lang.String str, boolean z) throws java.lang.IllegalArgumentException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.Object getProperty(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(java.lang.String str, boolean z) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        if (str.equals("http://xmlpull.org/v1/doc/features.html#indent-output")) {
            this.mIndent = true;
            return;
        }
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(java.io.OutputStream outputStream, java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        if (outputStream == null) {
            throw new java.lang.IllegalArgumentException();
        }
        try {
            this.mCharset = java.nio.charset.Charset.forName(str).newEncoder().onMalformedInput(java.nio.charset.CodingErrorAction.REPLACE).onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPLACE);
            this.mOutputStream = outputStream;
        } catch (java.nio.charset.IllegalCharsetNameException e) {
            throw ((java.io.UnsupportedEncodingException) new java.io.UnsupportedEncodingException(str).initCause(e));
        } catch (java.nio.charset.UnsupportedCharsetException e2) {
            throw ((java.io.UnsupportedEncodingException) new java.io.UnsupportedEncodingException(str).initCause(e2));
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(java.io.Writer writer) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        this.mWriter = writer;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(java.lang.String str, java.lang.String str2) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(java.lang.String str, java.lang.Object obj) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(java.lang.String str, java.lang.Boolean bool) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        append("<?xml version='1.0' encoding='utf-8'");
        if (bool != null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(" standalone='");
            sb.append(bool.booleanValue() ? com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_YES : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_NO);
            sb.append("'");
            append(sb.toString());
        }
        append(" ?>\n");
        this.mLineStart = true;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer startTag(java.lang.String str, java.lang.String str2) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        if (this.mInTag) {
            append(">\n");
        }
        if (this.mIndent) {
            appendIndent(this.mNesting);
        }
        this.mNesting++;
        append('<');
        if (str != null) {
            append(str);
            append(':');
        }
        append(str2);
        this.mInTag = true;
        this.mLineStart = false;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer text(char[] cArr, int i, int i2) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        if (this.mInTag) {
            append(">");
            this.mInTag = false;
        }
        escapeAndAppendString(cArr, i, i2);
        if (this.mIndent) {
            this.mLineStart = cArr[(i + i2) - 1] == '\n';
        }
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer text(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        boolean z = false;
        if (this.mInTag) {
            append(">");
            this.mInTag = false;
        }
        escapeAndAppendString(str);
        if (this.mIndent) {
            if (str.length() > 0 && str.charAt(str.length() - 1) == '\n') {
                z = true;
            }
            this.mLineStart = z;
        }
        return this;
    }
}
