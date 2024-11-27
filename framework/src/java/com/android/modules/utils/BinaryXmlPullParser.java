package com.android.modules.utils;

/* loaded from: classes5.dex */
public class BinaryXmlPullParser implements com.android.modules.utils.TypedXmlPullParser {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', android.text.format.DateFormat.AM_PM, 'b', 'c', android.text.format.DateFormat.DATE, 'e', 'f'};
    private com.android.modules.utils.BinaryXmlPullParser.Attribute[] mAttributes;
    private java.lang.String mCurrentName;
    private java.lang.String mCurrentText;
    private com.android.modules.utils.FastDataInput mIn;
    private int mCurrentToken = 0;
    private int mCurrentDepth = 0;
    private int mAttributeCount = 0;

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(java.io.InputStream inputStream, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
        if (str != null && !java.nio.charset.StandardCharsets.UTF_8.name().equalsIgnoreCase(str)) {
            throw new java.lang.UnsupportedOperationException();
        }
        if (this.mIn != null) {
            this.mIn.release();
            this.mIn = null;
        }
        this.mIn = obtainFastDataInput(inputStream);
        this.mCurrentToken = 0;
        this.mCurrentDepth = 0;
        this.mCurrentName = null;
        this.mCurrentText = null;
        this.mAttributeCount = 0;
        this.mAttributes = new com.android.modules.utils.BinaryXmlPullParser.Attribute[8];
        for (int i = 0; i < this.mAttributes.length; i++) {
            this.mAttributes[i] = new com.android.modules.utils.BinaryXmlPullParser.Attribute();
        }
        try {
            byte[] bArr = new byte[4];
            this.mIn.readFully(bArr);
            if (!java.util.Arrays.equals(bArr, com.android.modules.utils.BinaryXmlSerializer.PROTOCOL_MAGIC_VERSION_0)) {
                throw new java.io.IOException("Unexpected magic " + bytesToHexString(bArr));
            }
            if (peekNextExternalToken() == 0) {
                consumeToken();
            }
        } catch (java.io.IOException e) {
            throw new org.xmlpull.v1.XmlPullParserException(e.toString());
        }
    }

    protected com.android.modules.utils.FastDataInput obtainFastDataInput(java.io.InputStream inputStream) {
        return com.android.modules.utils.FastDataInput.obtain(inputStream);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(java.io.Reader reader) throws org.xmlpull.v1.XmlPullParserException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int next() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        while (true) {
            int nextToken = nextToken();
            switch (nextToken) {
                case 1:
                case 2:
                case 3:
                    return nextToken;
                case 4:
                    consumeAdditionalText();
                    if (this.mCurrentText != null && this.mCurrentText.length() != 0) {
                        return 4;
                    }
                    break;
            }
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextToken() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        if (this.mCurrentToken == 3) {
            this.mCurrentDepth--;
        }
        try {
            i = peekNextExternalToken();
            consumeToken();
        } catch (java.io.EOFException e) {
            i = 1;
        }
        switch (i) {
            case 2:
                peekNextExternalToken();
                this.mCurrentDepth++;
                break;
        }
        this.mCurrentToken = i;
        return i;
    }

    private int peekNextExternalToken() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        while (true) {
            int peekNextToken = peekNextToken();
            switch (peekNextToken) {
                case 15:
                    consumeToken();
                default:
                    return peekNextToken;
            }
        }
    }

    private int peekNextToken() throws java.io.IOException {
        return this.mIn.peekByte() & 15;
    }

    private void consumeToken() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        byte readByte = this.mIn.readByte();
        int i = readByte & 15;
        int i2 = readByte & 240;
        switch (i) {
            case 0:
                this.mCurrentName = null;
                this.mCurrentText = null;
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 1:
                this.mCurrentName = null;
                this.mCurrentText = null;
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 2:
                this.mCurrentName = this.mIn.readInternedUTF();
                this.mCurrentText = null;
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 3:
                this.mCurrentName = this.mIn.readInternedUTF();
                this.mCurrentText = null;
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                this.mCurrentName = null;
                this.mCurrentText = this.mIn.readUTF();
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 6:
                this.mCurrentName = this.mIn.readUTF();
                this.mCurrentText = resolveEntity(this.mCurrentName);
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 11:
            case 12:
            case 13:
            case 14:
            default:
                throw new java.io.IOException("Unknown token " + i + " with type " + i2);
            case 15:
                com.android.modules.utils.BinaryXmlPullParser.Attribute obtainAttribute = obtainAttribute();
                obtainAttribute.name = this.mIn.readInternedUTF();
                obtainAttribute.type = i2;
                switch (i2) {
                    case 16:
                    case 192:
                    case 208:
                        return;
                    case 32:
                        obtainAttribute.valueString = this.mIn.readUTF();
                        return;
                    case 48:
                        obtainAttribute.valueString = this.mIn.readInternedUTF();
                        return;
                    case 64:
                    case 80:
                        byte[] bArr = new byte[this.mIn.readUnsignedShort()];
                        this.mIn.readFully(bArr);
                        obtainAttribute.valueBytes = bArr;
                        return;
                    case 96:
                    case 112:
                        obtainAttribute.valueInt = this.mIn.readInt();
                        return;
                    case 128:
                    case 144:
                        obtainAttribute.valueLong = this.mIn.readLong();
                        return;
                    case 160:
                        obtainAttribute.valueFloat = this.mIn.readFloat();
                        return;
                    case 176:
                        obtainAttribute.valueDouble = this.mIn.readDouble();
                        return;
                    default:
                        throw new java.io.IOException("Unexpected data type " + i2);
                }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0009, code lost:
    
        r2.mCurrentToken = 4;
        r2.mCurrentName = null;
        r2.mCurrentText = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0011, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void consumeAdditionalText() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String str = this.mCurrentText;
        while (true) {
            switch (peekNextExternalToken()) {
                case 4:
                case 5:
                case 6:
                    consumeToken();
                    str = str + this.mCurrentText;
                    break;
                case 8:
                case 9:
                    consumeToken();
                    break;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static java.lang.String resolveEntity(java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
        char c;
        switch (str.hashCode()) {
            case 3309:
                if (str.equals("gt")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3464:
                if (str.equals("lt")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 96708:
                if (str.equals("amp")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3000915:
                if (str.equals("apos")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3482377:
                if (str.equals("quot")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return "<";
            case 1:
                return ">";
            case 2:
                return "&";
            case 3:
                return "'";
            case 4:
                return "\"";
            default:
                if (str.length() > 1 && str.charAt(0) == '#') {
                    return new java.lang.String(new char[]{(char) java.lang.Integer.parseInt(str.substring(1))});
                }
                throw new org.xmlpull.v1.XmlPullParserException("Unknown entity " + str);
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void require(int i, java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        if (this.mCurrentToken != i || !java.util.Objects.equals(this.mCurrentName, str2)) {
            throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription());
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String nextText() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (getEventType() != 2) {
            throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription());
        }
        int next = next();
        if (next == 4) {
            java.lang.String text = getText();
            if (next() != 3) {
                throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription());
            }
            return text;
        }
        if (next == 3) {
            return "";
        }
        throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription());
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextTag() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next = next();
        if (next == 4 && isWhitespace()) {
            next = next();
        }
        if (next != 2 && next != 3) {
            throw new org.xmlpull.v1.XmlPullParserException(getPositionDescription());
        }
        return next;
    }

    private com.android.modules.utils.BinaryXmlPullParser.Attribute obtainAttribute() {
        if (this.mAttributeCount == this.mAttributes.length) {
            int length = this.mAttributes.length;
            int i = (length >> 1) + length;
            this.mAttributes = (com.android.modules.utils.BinaryXmlPullParser.Attribute[]) java.util.Arrays.copyOf(this.mAttributes, i);
            while (length < i) {
                this.mAttributes[length] = new com.android.modules.utils.BinaryXmlPullParser.Attribute();
                length++;
            }
        }
        com.android.modules.utils.BinaryXmlPullParser.Attribute[] attributeArr = this.mAttributes;
        int i2 = this.mAttributeCount;
        this.mAttributeCount = i2 + 1;
        return attributeArr[i2];
    }

    private void resetAttributes() {
        for (int i = 0; i < this.mAttributeCount; i++) {
            this.mAttributes[i].reset();
        }
        this.mAttributeCount = 0;
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public int getAttributeIndex(java.lang.String str, java.lang.String str2) {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        for (int i = 0; i < this.mAttributeCount; i++) {
            if (java.util.Objects.equals(this.mAttributes[i].name, str2)) {
                return i;
            }
        }
        return -1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeValue(java.lang.String str, java.lang.String str2) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            return this.mAttributes[attributeIndex].getValueString();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeValue(int i) {
        return this.mAttributes[i].getValueString();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public byte[] getAttributeBytesHex(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueBytesHex();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public byte[] getAttributeBytesBase64(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueBytesBase64();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public int getAttributeInt(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueInt();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public int getAttributeIntHex(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueIntHex();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public long getAttributeLong(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueLong();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public long getAttributeLongHex(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueLongHex();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public float getAttributeFloat(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueFloat();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public double getAttributeDouble(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueDouble();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public boolean getAttributeBoolean(int i) throws org.xmlpull.v1.XmlPullParserException {
        return this.mAttributes[i].getValueBoolean();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getText() {
        return this.mCurrentText;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public char[] getTextCharacters(int[] iArr) {
        char[] charArray = this.mCurrentText.toCharArray();
        iArr[0] = 0;
        iArr[1] = charArray.length;
        return charArray;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getInputEncoding() {
        return java.nio.charset.StandardCharsets.UTF_8.name();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getDepth() {
        return this.mCurrentDepth;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getPositionDescription() {
        return "Token " + this.mCurrentToken + " at depth " + this.mCurrentDepth;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getLineNumber() {
        return -1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getColumnNumber() {
        return -1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isWhitespace() throws org.xmlpull.v1.XmlPullParserException {
        switch (this.mCurrentToken) {
            case 4:
            case 5:
                return !android.text.TextUtils.isGraphic(this.mCurrentText);
            case 6:
            default:
                throw new org.xmlpull.v1.XmlPullParserException("Not applicable for token " + this.mCurrentToken);
            case 7:
                return true;
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getNamespace() {
        switch (this.mCurrentToken) {
            case 2:
            case 3:
                return "";
            default:
                return null;
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getName() {
        return this.mCurrentName;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getPrefix() {
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isEmptyElementTag() throws org.xmlpull.v1.XmlPullParserException {
        switch (this.mCurrentToken) {
            case 2:
                try {
                    return peekNextExternalToken() == 3;
                } catch (java.io.IOException e) {
                    throw new org.xmlpull.v1.XmlPullParserException(e.toString());
                }
            default:
                throw new org.xmlpull.v1.XmlPullParserException("Not at START_TAG");
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getAttributeCount() {
        return this.mAttributeCount;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeNamespace(int i) {
        return "";
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributeName(int i) {
        return this.mAttributes[i].name;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getAttributePrefix(int i) {
        return null;
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
    public int getEventType() throws org.xmlpull.v1.XmlPullParserException {
        return this.mCurrentToken;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getNamespaceCount(int i) throws org.xmlpull.v1.XmlPullParserException {
        return 0;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getNamespacePrefix(int i) throws org.xmlpull.v1.XmlPullParserException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getNamespaceUri(int i) throws org.xmlpull.v1.XmlPullParserException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.String getNamespace(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void defineEntityReplacementText(java.lang.String str, java.lang.String str2) throws org.xmlpull.v1.XmlPullParserException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setFeature(java.lang.String str, boolean z) throws org.xmlpull.v1.XmlPullParserException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean getFeature(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setProperty(java.lang.String str, java.lang.Object obj) throws org.xmlpull.v1.XmlPullParserException {
        throw new java.lang.UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public java.lang.Object getProperty(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException();
    }

    private static java.lang.IllegalArgumentException illegalNamespace() {
        throw new java.lang.IllegalArgumentException("Namespaces are not supported");
    }

    private static class Attribute {
        public java.lang.String name;
        public int type;
        public byte[] valueBytes;
        public double valueDouble;
        public float valueFloat;
        public int valueInt;
        public long valueLong;
        public java.lang.String valueString;

        private Attribute() {
        }

        public void reset() {
            this.name = null;
            this.valueString = null;
            this.valueBytes = null;
        }

        public java.lang.String getValueString() {
            switch (this.type) {
                case 16:
                    return null;
                case 32:
                case 48:
                    return this.valueString;
                case 64:
                    return com.android.modules.utils.BinaryXmlPullParser.bytesToHexString(this.valueBytes);
                case 80:
                    return android.util.Base64.encodeToString(this.valueBytes, 2);
                case 96:
                    return java.lang.Integer.toString(this.valueInt);
                case 112:
                    return java.lang.Integer.toString(this.valueInt, 16);
                case 128:
                    return java.lang.Long.toString(this.valueLong);
                case 144:
                    return java.lang.Long.toString(this.valueLong, 16);
                case 160:
                    return java.lang.Float.toString(this.valueFloat);
                case 176:
                    return java.lang.Double.toString(this.valueDouble);
                case 192:
                    return "true";
                case 208:
                    return "false";
                default:
                    return null;
            }
        }

        public byte[] getValueBytesHex() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 16:
                    return null;
                case 32:
                case 48:
                    try {
                        return com.android.modules.utils.BinaryXmlPullParser.hexStringToBytes(this.valueString);
                    } catch (java.lang.Exception e) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + e);
                    }
                case 64:
                case 80:
                    return this.valueBytes;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }

        public byte[] getValueBytesBase64() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 16:
                    return null;
                case 32:
                case 48:
                    try {
                        return android.util.Base64.decode(this.valueString, 2);
                    } catch (java.lang.Exception e) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + e);
                    }
                case 64:
                case 80:
                    return this.valueBytes;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }

        public int getValueInt() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 32:
                case 48:
                    try {
                        return java.lang.Integer.parseInt(this.valueString);
                    } catch (java.lang.Exception e) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + e);
                    }
                case 96:
                case 112:
                    return this.valueInt;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }

        public int getValueIntHex() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 32:
                case 48:
                    try {
                        return java.lang.Integer.parseInt(this.valueString, 16);
                    } catch (java.lang.Exception e) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + e);
                    }
                case 96:
                case 112:
                    return this.valueInt;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }

        public long getValueLong() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 32:
                case 48:
                    try {
                        return java.lang.Long.parseLong(this.valueString);
                    } catch (java.lang.Exception e) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + e);
                    }
                case 128:
                case 144:
                    return this.valueLong;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }

        public long getValueLongHex() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 32:
                case 48:
                    try {
                        return java.lang.Long.parseLong(this.valueString, 16);
                    } catch (java.lang.Exception e) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + e);
                    }
                case 128:
                case 144:
                    return this.valueLong;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }

        public float getValueFloat() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 32:
                case 48:
                    try {
                        return java.lang.Float.parseFloat(this.valueString);
                    } catch (java.lang.Exception e) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + e);
                    }
                case 160:
                    return this.valueFloat;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }

        public double getValueDouble() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 32:
                case 48:
                    try {
                        return java.lang.Double.parseDouble(this.valueString);
                    } catch (java.lang.Exception e) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + e);
                    }
                case 176:
                    return this.valueDouble;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }

        public boolean getValueBoolean() throws org.xmlpull.v1.XmlPullParserException {
            switch (this.type) {
                case 32:
                case 48:
                    if ("true".equalsIgnoreCase(this.valueString)) {
                        return true;
                    }
                    if ("false".equalsIgnoreCase(this.valueString)) {
                        return false;
                    }
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid attribute " + this.name + ": " + this.valueString);
                case 192:
                    return true;
                case 208:
                    return false;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid conversion from " + this.type);
            }
        }
    }

    private static int toByte(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return (c - android.text.format.DateFormat.CAPITAL_AM_PM) + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return (c - android.text.format.DateFormat.AM_PM) + 10;
        }
        throw new java.lang.IllegalArgumentException("Invalid hex char '" + c + "'");
    }

    static java.lang.String bytesToHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            cArr[i] = HEX_DIGITS[(b >>> 4) & 15];
            i = i2 + 1;
            cArr[i2] = HEX_DIGITS[b & 15];
        }
        return new java.lang.String(cArr);
    }

    static byte[] hexStringToBytes(java.lang.String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new java.lang.IllegalArgumentException("Invalid hex length " + length);
        }
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((toByte(str.charAt(i)) << 4) | toByte(str.charAt(i + 1)));
        }
        return bArr;
    }
}
