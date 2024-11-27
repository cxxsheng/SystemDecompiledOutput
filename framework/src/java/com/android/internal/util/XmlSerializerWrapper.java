package com.android.internal.util;

/* loaded from: classes5.dex */
public class XmlSerializerWrapper implements org.xmlpull.v1.XmlSerializer {
    private final org.xmlpull.v1.XmlSerializer mWrapped;

    public XmlSerializerWrapper(org.xmlpull.v1.XmlSerializer xmlSerializer) {
        this.mWrapped = (org.xmlpull.v1.XmlSerializer) java.util.Objects.requireNonNull(xmlSerializer);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(java.lang.String str, boolean z) {
        this.mWrapped.setFeature(str, z);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(java.lang.String str) {
        return this.mWrapped.getFeature(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(java.lang.String str, java.lang.Object obj) {
        this.mWrapped.setProperty(str, obj);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.Object getProperty(java.lang.String str) {
        return this.mWrapped.getProperty(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(java.io.OutputStream outputStream, java.lang.String str) throws java.io.IOException {
        this.mWrapped.setOutput(outputStream, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(java.io.Writer writer) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        this.mWrapped.setOutput(writer);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(java.lang.String str, java.lang.Boolean bool) throws java.io.IOException {
        this.mWrapped.startDocument(str, bool);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws java.io.IOException {
        this.mWrapped.endDocument();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        this.mWrapped.setPrefix(str, str2);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getPrefix(java.lang.String str, boolean z) {
        return this.mWrapped.getPrefix(str, z);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        return this.mWrapped.getDepth();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getNamespace() {
        return this.mWrapped.getNamespace();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public java.lang.String getName() {
        return this.mWrapped.getName();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer startTag(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        return this.mWrapped.startTag(str, str2);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer attribute(java.lang.String str, java.lang.String str2, java.lang.String str3) throws java.io.IOException {
        return this.mWrapped.attribute(str, str2, str3);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer endTag(java.lang.String str, java.lang.String str2) throws java.io.IOException {
        return this.mWrapped.endTag(str, str2);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer text(java.lang.String str) throws java.io.IOException {
        return this.mWrapped.text(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public org.xmlpull.v1.XmlSerializer text(char[] cArr, int i, int i2) throws java.io.IOException {
        return this.mWrapped.text(cArr, i, i2);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(java.lang.String str) throws java.io.IOException, java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        this.mWrapped.cdsect(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(java.lang.String str) throws java.io.IOException {
        this.mWrapped.entityRef(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(java.lang.String str) throws java.io.IOException {
        this.mWrapped.processingInstruction(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(java.lang.String str) throws java.io.IOException {
        this.mWrapped.comment(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(java.lang.String str) throws java.io.IOException {
        this.mWrapped.docdecl(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(java.lang.String str) throws java.io.IOException {
        this.mWrapped.ignorableWhitespace(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws java.io.IOException {
        this.mWrapped.flush();
    }
}
