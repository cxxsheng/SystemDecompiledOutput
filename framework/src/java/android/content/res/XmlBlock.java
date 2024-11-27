package android.content.res;

/* loaded from: classes.dex */
public final class XmlBlock implements java.lang.AutoCloseable {
    private static final boolean DEBUG = false;
    private static final int ERROR_BAD_DOCUMENT = -android.system.OsConstants.EINVAL;
    private static final int ERROR_NULL_DOCUMENT = -2147483640;
    private final android.content.res.AssetManager mAssets;
    private long mNative;
    private boolean mOpen;
    private int mOpenCount;
    final android.content.res.StringBlock mStrings;

    private static final native long nativeCreate(byte[] bArr, int i, int i2);

    private static final native long nativeCreateParseState(long j, int i);

    private static final native void nativeDestroy(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void nativeDestroyParseState(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetAttributeCount(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetAttributeData(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetAttributeDataType(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native int nativeGetAttributeIndex(long j, java.lang.String str, java.lang.String str2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetAttributeName(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetAttributeNamespace(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetAttributeResource(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetAttributeStringValue(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetClassAttribute(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetIdAttribute(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetLineNumber(long j);

    @dalvik.annotation.optimization.CriticalNative
    static final native int nativeGetName(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetNamespace(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetSourceResId(long j);

    private static final native long nativeGetStringBlock(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetStyleAttribute(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static final native int nativeGetText(long j);

    @dalvik.annotation.optimization.CriticalNative
    static final native int nativeNext(long j);

    public XmlBlock(byte[] bArr) {
        this.mOpen = true;
        this.mOpenCount = 1;
        this.mAssets = null;
        this.mNative = nativeCreate(bArr, 0, bArr.length);
        this.mStrings = new android.content.res.StringBlock(nativeGetStringBlock(this.mNative), false);
    }

    public XmlBlock(byte[] bArr, int i, int i2) {
        this.mOpen = true;
        this.mOpenCount = 1;
        this.mAssets = null;
        this.mNative = nativeCreate(bArr, i, i2);
        this.mStrings = new android.content.res.StringBlock(nativeGetStringBlock(this.mNative), false);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                decOpenCountLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decOpenCountLocked() {
        this.mOpenCount--;
        if (this.mOpenCount == 0) {
            this.mStrings.close();
            nativeDestroy(this.mNative);
            this.mNative = 0L;
            if (this.mAssets != null) {
                this.mAssets.xmlBlockGone(hashCode());
            }
        }
    }

    public android.content.res.XmlResourceParser newParser() {
        return newParser(0);
    }

    public android.content.res.XmlResourceParser newParser(int i) {
        synchronized (this) {
            if (this.mNative == 0) {
                return null;
            }
            return new android.content.res.XmlBlock.Parser(nativeCreateParseState(this.mNative, i), this);
        }
    }

    public android.content.res.XmlResourceParser newParser(int i, android.content.res.Validator validator) {
        synchronized (this) {
            if (this.mNative == 0) {
                return null;
            }
            return new android.content.res.XmlBlock.Parser(this, nativeCreateParseState(this.mNative, i), this, validator);
        }
    }

    public final class Parser implements android.content.res.XmlResourceParser {
        private final android.content.res.XmlBlock mBlock;
        private boolean mDecNextDepth;
        private int mDepth;
        private int mEventType;
        long mParseState;
        private boolean mStarted;
        android.content.res.Validator mValidator;

        Parser(long j, android.content.res.XmlBlock xmlBlock) {
            this.mStarted = false;
            this.mDecNextDepth = false;
            this.mDepth = 0;
            this.mEventType = 0;
            this.mParseState = j;
            this.mBlock = xmlBlock;
            xmlBlock.mOpenCount++;
        }

        Parser(android.content.res.XmlBlock xmlBlock, long j, android.content.res.XmlBlock xmlBlock2, android.content.res.Validator validator) {
            this(j, xmlBlock2);
            this.mValidator = validator;
        }

        public int getSourceResId() {
            return android.content.res.XmlBlock.nativeGetSourceResId(this.mParseState);
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void setFeature(java.lang.String str, boolean z) throws org.xmlpull.v1.XmlPullParserException {
            if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(str) && z) {
                return;
            }
            if ("http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes".equals(str) && z) {
            } else {
                throw new org.xmlpull.v1.XmlPullParserException("Unsupported feature: " + str);
            }
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public boolean getFeature(java.lang.String str) {
            return "http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(str) || "http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes".equals(str);
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void setProperty(java.lang.String str, java.lang.Object obj) throws org.xmlpull.v1.XmlPullParserException {
            throw new org.xmlpull.v1.XmlPullParserException("setProperty() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.Object getProperty(java.lang.String str) {
            return null;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void setInput(java.io.Reader reader) throws org.xmlpull.v1.XmlPullParserException {
            throw new org.xmlpull.v1.XmlPullParserException("setInput() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void setInput(java.io.InputStream inputStream, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
            throw new org.xmlpull.v1.XmlPullParserException("setInput() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void defineEntityReplacementText(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
            throw new org.xmlpull.v1.XmlPullParserException("defineEntityReplacementText() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getNamespacePrefix(int i) throws org.xmlpull.v1.XmlPullParserException {
            throw new org.xmlpull.v1.XmlPullParserException("getNamespacePrefix() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getInputEncoding() {
            return null;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getNamespace(java.lang.String str) {
            throw new java.lang.RuntimeException("getNamespace() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getNamespaceCount(int i) throws org.xmlpull.v1.XmlPullParserException {
            throw new org.xmlpull.v1.XmlPullParserException("getNamespaceCount() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public java.lang.String getPositionDescription() {
            return "Binary XML file line #" + getLineNumber();
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getNamespaceUri(int i) throws org.xmlpull.v1.XmlPullParserException {
            throw new org.xmlpull.v1.XmlPullParserException("getNamespaceUri() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getColumnNumber() {
            return -1;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getDepth() {
            return this.mDepth;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getText() {
            int nativeGetText = android.content.res.XmlBlock.nativeGetText(this.mParseState);
            if (nativeGetText >= 0) {
                return getSequenceString(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetText));
            }
            return null;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getLineNumber() {
            int nativeGetLineNumber = android.content.res.XmlBlock.nativeGetLineNumber(this.mParseState);
            if (nativeGetLineNumber == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            return nativeGetLineNumber;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getEventType() throws org.xmlpull.v1.XmlPullParserException {
            return this.mEventType;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public boolean isWhitespace() throws org.xmlpull.v1.XmlPullParserException {
            return false;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getPrefix() {
            throw new java.lang.RuntimeException("getPrefix not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public char[] getTextCharacters(int[] iArr) {
            java.lang.String text = getText();
            if (text == null) {
                return null;
            }
            iArr[0] = 0;
            iArr[1] = text.length();
            char[] cArr = new char[text.length()];
            text.getChars(0, text.length(), cArr, 0);
            return cArr;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getNamespace() {
            int nativeGetNamespace = android.content.res.XmlBlock.nativeGetNamespace(this.mParseState);
            return nativeGetNamespace >= 0 ? getSequenceString(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetNamespace)) : "";
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getName() {
            int nativeGetName = android.content.res.XmlBlock.nativeGetName(this.mParseState);
            if (nativeGetName >= 0) {
                return getSequenceString(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetName));
            }
            return null;
        }

        @Override // android.content.res.XmlResourceParser, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public java.lang.String getAttributeNamespace(int i) {
            int nativeGetAttributeNamespace = android.content.res.XmlBlock.nativeGetAttributeNamespace(this.mParseState, i);
            if (nativeGetAttributeNamespace == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeNamespace >= 0) {
                return getSequenceString(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetAttributeNamespace));
            }
            if (nativeGetAttributeNamespace == -1) {
                return "";
            }
            throw new java.lang.IndexOutOfBoundsException(java.lang.String.valueOf(i));
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public java.lang.String getAttributeName(int i) {
            int nativeGetAttributeName = android.content.res.XmlBlock.nativeGetAttributeName(this.mParseState, i);
            if (nativeGetAttributeName == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeName >= 0) {
                return getSequenceString(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetAttributeName));
            }
            throw new java.lang.IndexOutOfBoundsException(java.lang.String.valueOf(i));
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getAttributePrefix(int i) {
            throw new java.lang.RuntimeException("getAttributePrefix not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public boolean isEmptyElementTag() throws org.xmlpull.v1.XmlPullParserException {
            return false;
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public int getAttributeCount() {
            if (this.mEventType == 2) {
                int nativeGetAttributeCount = android.content.res.XmlBlock.nativeGetAttributeCount(this.mParseState);
                if (nativeGetAttributeCount == -2147483640) {
                    throw new java.lang.NullPointerException("Null document");
                }
                return nativeGetAttributeCount;
            }
            return -1;
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public java.lang.String getAttributeValue(int i) {
            int nativeGetAttributeStringValue = android.content.res.XmlBlock.nativeGetAttributeStringValue(this.mParseState, i);
            if (nativeGetAttributeStringValue == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeStringValue >= 0) {
                return getSequenceString(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetAttributeStringValue));
            }
            int nativeGetAttributeDataType = android.content.res.XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (nativeGetAttributeDataType == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeDataType == 0) {
                throw new java.lang.IndexOutOfBoundsException(java.lang.String.valueOf(i));
            }
            int nativeGetAttributeData = android.content.res.XmlBlock.nativeGetAttributeData(this.mParseState, i);
            if (nativeGetAttributeData == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            return android.util.TypedValue.coerceToString(nativeGetAttributeDataType, nativeGetAttributeData);
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String getAttributeType(int i) {
            return "CDATA";
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public boolean isAttributeDefault(int i) {
            return false;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int nextToken() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            return next();
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public java.lang.String getAttributeValue(java.lang.String str, java.lang.String str2) {
            int nativeGetAttributeIndex = android.content.res.XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            if (nativeGetAttributeIndex >= 0) {
                java.lang.String attributeValue = getAttributeValue(nativeGetAttributeIndex);
                if (this.mValidator != null) {
                    this.mValidator.validateStrAttr(this, str2, attributeValue);
                }
                return attributeValue;
            }
            return null;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int next() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            if (!this.mStarted) {
                this.mStarted = true;
                return 0;
            }
            if (this.mParseState == 0) {
                return 1;
            }
            int nativeNext = android.content.res.XmlBlock.nativeNext(this.mParseState);
            if (nativeNext == android.content.res.XmlBlock.ERROR_BAD_DOCUMENT) {
                throw new org.xmlpull.v1.XmlPullParserException("Corrupt XML binary file");
            }
            if (this.mDecNextDepth) {
                this.mDepth--;
                this.mDecNextDepth = false;
            }
            switch (nativeNext) {
                case 2:
                    this.mDepth++;
                    break;
                case 3:
                    this.mDecNextDepth = true;
                    break;
            }
            this.mEventType = nativeNext;
            if (this.mValidator != null) {
                this.mValidator.validate(this);
            }
            if (nativeNext == 1) {
                close();
            }
            return nativeNext;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void require(int i, java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            if (i != getEventType() || ((str != null && !str.equals(getNamespace())) || (str2 != null && !str2.equals(getName())))) {
                throw new org.xmlpull.v1.XmlPullParserException("expected " + TYPES[i] + getPositionDescription());
            }
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public java.lang.String nextText() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            if (getEventType() != 2) {
                throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription() + ": parser must be on START_TAG to read next text", this, null);
            }
            int next = next();
            if (next == 4) {
                java.lang.String text = getText();
                if (next() != 3) {
                    throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription() + ": event TEXT it must be immediately followed by END_TAG", this, null);
                }
                return text;
            }
            if (next == 3) {
                return "";
            }
            throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription() + ": parser must be on START_TAG or TEXT to read text", this, null);
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int nextTag() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            int next = next();
            if (next == 4 && isWhitespace()) {
                next = next();
            }
            if (next != 2 && next != 3) {
                throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription() + ": expected start or end tag", this, null);
            }
            return next;
        }

        @Override // android.util.AttributeSet
        public int getAttributeNameResource(int i) {
            int nativeGetAttributeResource = android.content.res.XmlBlock.nativeGetAttributeResource(this.mParseState, i);
            if (nativeGetAttributeResource == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            return nativeGetAttributeResource;
        }

        @Override // android.util.AttributeSet
        public int getAttributeListValue(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, int i) {
            int nativeGetAttributeIndex = android.content.res.XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            if (nativeGetAttributeIndex >= 0) {
                return getAttributeListValue(nativeGetAttributeIndex, strArr, i);
            }
            return i;
        }

        @Override // android.util.AttributeSet
        public boolean getAttributeBooleanValue(java.lang.String str, java.lang.String str2, boolean z) {
            int nativeGetAttributeIndex = android.content.res.XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            if (nativeGetAttributeIndex >= 0) {
                return getAttributeBooleanValue(nativeGetAttributeIndex, z);
            }
            return z;
        }

        @Override // android.util.AttributeSet
        public int getAttributeResourceValue(java.lang.String str, java.lang.String str2, int i) {
            int nativeGetAttributeIndex = android.content.res.XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            if (nativeGetAttributeIndex >= 0) {
                return getAttributeResourceValue(nativeGetAttributeIndex, i);
            }
            return i;
        }

        @Override // android.util.AttributeSet
        public int getAttributeIntValue(java.lang.String str, java.lang.String str2, int i) {
            int nativeGetAttributeIndex = android.content.res.XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            if (nativeGetAttributeIndex >= 0) {
                return getAttributeIntValue(nativeGetAttributeIndex, i);
            }
            return i;
        }

        @Override // android.util.AttributeSet
        public int getAttributeUnsignedIntValue(java.lang.String str, java.lang.String str2, int i) {
            int nativeGetAttributeIndex = android.content.res.XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            if (nativeGetAttributeIndex >= 0) {
                return getAttributeUnsignedIntValue(nativeGetAttributeIndex, i);
            }
            return i;
        }

        @Override // android.util.AttributeSet
        public float getAttributeFloatValue(java.lang.String str, java.lang.String str2, float f) {
            int nativeGetAttributeIndex = android.content.res.XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            if (nativeGetAttributeIndex >= 0) {
                return getAttributeFloatValue(nativeGetAttributeIndex, f);
            }
            return f;
        }

        @Override // android.util.AttributeSet
        public int getAttributeListValue(int i, java.lang.String[] strArr, int i2) {
            int nativeGetAttributeDataType = android.content.res.XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (nativeGetAttributeDataType == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            int nativeGetAttributeData = android.content.res.XmlBlock.nativeGetAttributeData(this.mParseState, i);
            if (nativeGetAttributeData == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeDataType == 3) {
                return com.android.internal.util.XmlUtils.convertValueToList(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetAttributeData), strArr, i2);
            }
            return nativeGetAttributeData;
        }

        @Override // android.util.AttributeSet
        public boolean getAttributeBooleanValue(int i, boolean z) {
            int nativeGetAttributeDataType = android.content.res.XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (nativeGetAttributeDataType == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeDataType >= 16 && nativeGetAttributeDataType <= 31) {
                int nativeGetAttributeData = android.content.res.XmlBlock.nativeGetAttributeData(this.mParseState, i);
                if (nativeGetAttributeData != -2147483640) {
                    return nativeGetAttributeData != 0;
                }
                throw new java.lang.NullPointerException("Null document");
            }
            return z;
        }

        @Override // android.util.AttributeSet
        public int getAttributeResourceValue(int i, int i2) {
            int nativeGetAttributeDataType = android.content.res.XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (nativeGetAttributeDataType == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeDataType == 1) {
                int nativeGetAttributeData = android.content.res.XmlBlock.nativeGetAttributeData(this.mParseState, i);
                if (nativeGetAttributeData == -2147483640) {
                    throw new java.lang.NullPointerException("Null document");
                }
                return nativeGetAttributeData;
            }
            return i2;
        }

        @Override // android.util.AttributeSet
        public int getAttributeIntValue(int i, int i2) {
            int nativeGetAttributeDataType = android.content.res.XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (nativeGetAttributeDataType == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeDataType >= 16 && nativeGetAttributeDataType <= 31) {
                int nativeGetAttributeData = android.content.res.XmlBlock.nativeGetAttributeData(this.mParseState, i);
                if (nativeGetAttributeData == -2147483640) {
                    throw new java.lang.NullPointerException("Null document");
                }
                return nativeGetAttributeData;
            }
            return i2;
        }

        @Override // android.util.AttributeSet
        public int getAttributeUnsignedIntValue(int i, int i2) {
            int nativeGetAttributeDataType = android.content.res.XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (nativeGetAttributeDataType == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeDataType >= 16 && nativeGetAttributeDataType <= 31) {
                int nativeGetAttributeData = android.content.res.XmlBlock.nativeGetAttributeData(this.mParseState, i);
                if (nativeGetAttributeData == -2147483640) {
                    throw new java.lang.NullPointerException("Null document");
                }
                return nativeGetAttributeData;
            }
            return i2;
        }

        @Override // android.util.AttributeSet
        public float getAttributeFloatValue(int i, float f) {
            int nativeGetAttributeDataType = android.content.res.XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (nativeGetAttributeDataType == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetAttributeDataType == 4) {
                int nativeGetAttributeData = android.content.res.XmlBlock.nativeGetAttributeData(this.mParseState, i);
                if (nativeGetAttributeData == -2147483640) {
                    throw new java.lang.NullPointerException("Null document");
                }
                return java.lang.Float.intBitsToFloat(nativeGetAttributeData);
            }
            throw new java.lang.RuntimeException("not a float!");
        }

        @Override // android.util.AttributeSet
        public java.lang.String getIdAttribute() {
            int nativeGetIdAttribute = android.content.res.XmlBlock.nativeGetIdAttribute(this.mParseState);
            if (nativeGetIdAttribute == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetIdAttribute >= 0) {
                return getSequenceString(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetIdAttribute));
            }
            return null;
        }

        @Override // android.util.AttributeSet
        public java.lang.String getClassAttribute() {
            int nativeGetClassAttribute = android.content.res.XmlBlock.nativeGetClassAttribute(this.mParseState);
            if (nativeGetClassAttribute == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            if (nativeGetClassAttribute >= 0) {
                return getSequenceString(android.content.res.XmlBlock.this.mStrings.getSequence(nativeGetClassAttribute));
            }
            return null;
        }

        @Override // android.util.AttributeSet
        public int getIdAttributeResourceValue(int i) {
            return getAttributeResourceValue(null, "id", i);
        }

        @Override // android.util.AttributeSet
        public int getStyleAttribute() {
            int nativeGetStyleAttribute = android.content.res.XmlBlock.nativeGetStyleAttribute(this.mParseState);
            if (nativeGetStyleAttribute == -2147483640) {
                throw new java.lang.NullPointerException("Null document");
            }
            return nativeGetStyleAttribute;
        }

        private java.lang.String getSequenceString(java.lang.CharSequence charSequence) {
            if (charSequence == null) {
                throw new java.lang.IllegalStateException("Retrieving a string from the StringPool of an XmlBlock should never fail");
            }
            return charSequence.toString();
        }

        @Override // android.content.res.XmlResourceParser, java.lang.AutoCloseable
        public void close() {
            synchronized (this.mBlock) {
                if (this.mParseState != 0) {
                    android.content.res.XmlBlock.nativeDestroyParseState(this.mParseState);
                    this.mParseState = 0L;
                    this.mBlock.decOpenCountLocked();
                }
            }
        }

        protected void finalize() throws java.lang.Throwable {
            close();
        }

        final java.lang.CharSequence getPooledString(int i) {
            return android.content.res.XmlBlock.this.mStrings.getSequence(i);
        }
    }

    protected void finalize() throws java.lang.Throwable {
        close();
    }

    XmlBlock(android.content.res.AssetManager assetManager, long j) {
        this.mOpen = true;
        this.mOpenCount = 1;
        this.mAssets = assetManager;
        this.mNative = j;
        this.mStrings = new android.content.res.StringBlock(nativeGetStringBlock(j), false);
    }
}
