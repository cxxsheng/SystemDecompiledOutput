package com.android.modules.utils;

/* loaded from: classes5.dex */
public class BinaryXmlSerializer implements com.android.modules.utils.TypedXmlSerializer {
    static final int ATTRIBUTE = 15;
    public static final byte[] PROTOCOL_MAGIC_VERSION_0 = {65, 66, 88, 0};
    static final int TYPE_BOOLEAN_FALSE = 208;
    static final int TYPE_BOOLEAN_TRUE = 192;
    static final int TYPE_BYTES_BASE64 = 80;
    static final int TYPE_BYTES_HEX = 64;
    static final int TYPE_DOUBLE = 176;
    static final int TYPE_FLOAT = 160;
    static final int TYPE_INT = 96;
    static final int TYPE_INT_HEX = 112;
    static final int TYPE_LONG = 128;
    static final int TYPE_LONG_HEX = 144;
    static final int TYPE_NULL = 16;
    static final int TYPE_STRING = 32;
    static final int TYPE_STRING_INTERNED = 48;
    private com.android.modules.utils.FastDataOutput mOut;
    private int mTagCount = 0;
    private java.lang.String[] mTagNames;

    private void writeToken(int i, java.lang.String str) throws java.io.IOException {
        if (str != null) {
            this.mOut.writeByte(i | 32);
            this.mOut.writeUTF(str);
        } else {
            this.mOut.writeByte(i | 16);
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(java.io.OutputStream outputStream, java.lang.String str) throws java.io.IOException {
        if (str != null && !java.nio.charset.StandardCharsets.UTF_8.name().equalsIgnoreCase(str)) {
            throw new java.lang.UnsupportedOperationException();
        }
        this.mOut = obtainFastDataOutput(outputStream);
        this.mOut.write(PROTOCOL_MAGIC_VERSION_0);
        this.mTagCount = 0;
        this.mTagNames = new java.lang.String[8];
    }

    protected com.android.modules.utils.FastDataOutput obtainFastDataOutput(java.io.OutputStream outputStream) {
        return com.android.modules.utils.FastDataOutput.obtain(outputStream);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(java.io.Writer writer) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws java.io.IOException {
        if (this.mOut != null) {
            this.mOut.flush();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(java.lang.String str, java.lang.Boolean bool) throws java.io.IOException {
        if (str != null && !java.nio.charset.StandardCharsets.UTF_8.name().equalsIgnoreCase(str)) {
            throw new java.lang.UnsupportedOperationException();
        }
        if (bool != null && !bool.booleanValue()) {
            throw new java.lang.UnsupportedOperationException();
        }
        this.mOut.writeByte(16);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws java.io.IOException {
        this.mOut.writeByte(17);
        flush();
        this.mOut.release();
        this.mOut = null;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        return this.mTagCount;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getNamespace() {
        return "";
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getName() {
        return this.mTagNames[this.mTagCount - 1];
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer startTag(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        if (this.mTagCount == this.mTagNames.length) {
            this.mTagNames = (java.lang.String[]) java.util.Arrays.copyOf(this.mTagNames, this.mTagCount + (this.mTagCount >> 1));
        }
        java.lang.String[] strArr = this.mTagNames;
        int i = this.mTagCount;
        this.mTagCount = i + 1;
        strArr[i] = str2;
        this.mOut.writeByte(50);
        this.mOut.writeInternedUTF(str2);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer endTag(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mTagCount--;
        this.mOut.writeByte(51);
        this.mOut.writeInternedUTF(str2);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer attribute(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(47);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeUTF(str3);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeInterned(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(63);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeInternedUTF(str3);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeBytesHex(java.lang.String str, java.lang.String str2, byte[] bArr) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(79);
        this.mOut.writeInternedUTF(str2);
        if (bArr.length > 65535) {
            throw new java.io.IOException("attributeBytesHex: input size (" + bArr.length + ") exceeds maximum allowed size (65535" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mOut.writeShort(bArr.length);
        this.mOut.write(bArr);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeBytesBase64(java.lang.String str, java.lang.String str2, byte[] bArr) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(95);
        this.mOut.writeInternedUTF(str2);
        if (bArr.length > 65535) {
            throw new java.io.IOException("attributeBytesBase64: input size (" + bArr.length + ") exceeds maximum allowed size (65535" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mOut.writeShort(bArr.length);
        this.mOut.write(bArr);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeInt(java.lang.String str, java.lang.String str2, int i) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(111);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeInt(i);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeIntHex(java.lang.String str, java.lang.String str2, int i) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(127);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeInt(i);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeLong(java.lang.String str, java.lang.String str2, long j) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(143);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeLong(j);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeLongHex(java.lang.String str, java.lang.String str2, long j) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(159);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeLong(j);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeFloat(java.lang.String str, java.lang.String str2, float f) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(175);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeFloat(f);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeDouble(java.lang.String str, java.lang.String str2, double d) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(191);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeDouble(d);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public org.xmlpull.v1.XmlSerializer attributeBoolean(java.lang.String str, java.lang.String str2, boolean z) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        if (z) {
            this.mOut.writeByte(207);
            this.mOut.writeInternedUTF(str2);
        } else {
            this.mOut.writeByte(223);
            this.mOut.writeInternedUTF(str2);
        }
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer text(char[] cArr, int i, int i2) throws java.io.IOException {
        writeToken(4, new java.lang.String(cArr, i, i2));
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer text(java.lang.String str) throws java.io.IOException {
        writeToken(4, str);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(java.lang.String str) throws java.io.IOException {
        writeToken(5, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(java.lang.String str) throws java.io.IOException {
        writeToken(6, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(java.lang.String str) throws java.io.IOException {
        writeToken(8, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(java.lang.String str) throws java.io.IOException {
        writeToken(9, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(java.lang.String str) throws java.io.IOException {
        writeToken(10, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(java.lang.String str) throws java.io.IOException {
        writeToken(7, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(java.lang.String str, boolean z) {
        if ("http://xmlpull.org/v1/doc/features.html#indent-output".equals(str)) {
        } else {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(java.lang.String str, java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.Object getProperty(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(java.lang.String str, java.lang.String str2) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getPrefix(java.lang.String str, boolean z) {
        throw new java.lang.UnsupportedOperationException();
    }

    private static java.lang.IllegalArgumentException illegalNamespace() {
        throw new java.lang.IllegalArgumentException("Namespaces are not supported");
    }
}
