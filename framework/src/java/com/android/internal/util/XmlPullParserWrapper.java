package com.android.internal.util;

/* loaded from: classes5.dex */
public class XmlPullParserWrapper implements org.xmlpull.v1.XmlPullParser {
    private final org.xmlpull.v1.XmlPullParser mWrapped;

    public XmlPullParserWrapper(org.xmlpull.v1.XmlPullParser xmlPullParser) {
        this.mWrapped = (org.xmlpull.v1.XmlPullParser) java.util.Objects.requireNonNull(xmlPullParser);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setFeature(java.lang.String str, boolean z) throws org.xmlpull.v1.XmlPullParserException {
        this.mWrapped.setFeature(str, z);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean getFeature(java.lang.String str) {
        return this.mWrapped.getFeature(str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setProperty(java.lang.String str, java.lang.Object obj) throws org.xmlpull.v1.XmlPullParserException {
        this.mWrapped.setProperty(str, obj);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.Object getProperty(java.lang.String str) {
        return this.mWrapped.getProperty(str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(java.io.Reader reader) throws org.xmlpull.v1.XmlPullParserException {
        this.mWrapped.setInput(reader);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(java.io.InputStream inputStream, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
        this.mWrapped.setInput(inputStream, str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getInputEncoding() {
        return this.mWrapped.getInputEncoding();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void defineEntityReplacementText(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        this.mWrapped.defineEntityReplacementText(str, str2);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getNamespaceCount(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mWrapped.getNamespaceCount(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getNamespacePrefix(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mWrapped.getNamespacePrefix(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getNamespaceUri(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mWrapped.getNamespaceUri(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getNamespace(java.lang.String str) {
        return this.mWrapped.getNamespace(str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getDepth() {
        return this.mWrapped.getDepth();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getPositionDescription() {
        return this.mWrapped.getPositionDescription();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getLineNumber() {
        return this.mWrapped.getLineNumber();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getColumnNumber() {
        return this.mWrapped.getColumnNumber();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isWhitespace() throws org.xmlpull.v1.XmlPullParserException {
        return this.mWrapped.isWhitespace();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getText() {
        return this.mWrapped.getText();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public char[] getTextCharacters(int[] iArr) {
        return this.mWrapped.getTextCharacters(iArr);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getNamespace() {
        return this.mWrapped.getNamespace();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getName() {
        return this.mWrapped.getName();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getPrefix() {
        return this.mWrapped.getPrefix();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isEmptyElementTag() throws org.xmlpull.v1.XmlPullParserException {
        return this.mWrapped.isEmptyElementTag();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getAttributeCount() {
        return this.mWrapped.getAttributeCount();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeNamespace(int i) {
        return this.mWrapped.getAttributeNamespace(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeName(int i) {
        return this.mWrapped.getAttributeName(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributePrefix(int i) {
        return this.mWrapped.getAttributePrefix(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeType(int i) {
        return this.mWrapped.getAttributeType(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isAttributeDefault(int i) {
        return this.mWrapped.isAttributeDefault(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeValue(int i) {
        return this.mWrapped.getAttributeValue(i);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeValue(java.lang.String str, java.lang.String str2) {
        return this.mWrapped.getAttributeValue(str, str2);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getEventType() throws org.xmlpull.v1.XmlPullParserException {
        return this.mWrapped.getEventType();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int next() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return this.mWrapped.next();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextToken() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return this.mWrapped.nextToken();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void require(int i, java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mWrapped.require(i, str, str2);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String nextText() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return this.mWrapped.nextText();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextTag() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return this.mWrapped.nextTag();
    }
}
