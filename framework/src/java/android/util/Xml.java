package android.util;

/* loaded from: classes3.dex */
public class Xml {
    public static java.lang.String FEATURE_RELAXED = "http://xmlpull.org/v1/doc/features.html#relaxed";
    public static final boolean ENABLE_BINARY_DEFAULT = shouldEnableBinaryDefault();
    public static final boolean ENABLE_RESOLVE_OPTIMIZATIONS = shouldEnableResolveOptimizations();

    private Xml() {
    }

    private static boolean shouldEnableBinaryDefault() {
        return android.os.SystemProperties.getBoolean("persist.sys.binary_xml", true);
    }

    private static boolean shouldEnableBinaryDefault$ravenwood() {
        return true;
    }

    private static boolean shouldEnableResolveOptimizations() {
        return true;
    }

    private static boolean shouldEnableResolveOptimizations$ravenwood() {
        return false;
    }

    public static void parse(java.lang.String str, org.xml.sax.ContentHandler contentHandler) throws org.xml.sax.SAXException {
        try {
            org.xml.sax.XMLReader newXMLReader = newXMLReader();
            newXMLReader.setContentHandler(contentHandler);
            newXMLReader.parse(new org.xml.sax.InputSource(new java.io.StringReader(str)));
        } catch (java.io.IOException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public static void parse(java.io.Reader reader, org.xml.sax.ContentHandler contentHandler) throws java.io.IOException, org.xml.sax.SAXException {
        org.xml.sax.XMLReader newXMLReader = newXMLReader();
        newXMLReader.setContentHandler(contentHandler);
        newXMLReader.parse(new org.xml.sax.InputSource(reader));
    }

    public static void parse(java.io.InputStream inputStream, android.util.Xml.Encoding encoding, org.xml.sax.ContentHandler contentHandler) throws java.io.IOException, org.xml.sax.SAXException {
        org.xml.sax.XMLReader newXMLReader = newXMLReader();
        newXMLReader.setContentHandler(contentHandler);
        org.xml.sax.InputSource inputSource = new org.xml.sax.InputSource(inputStream);
        inputSource.setEncoding(encoding.expatName);
        newXMLReader.parse(inputSource);
    }

    public static org.xmlpull.v1.XmlPullParser newPullParser() {
        try {
            org.xmlpull.v1.XmlPullParser newXmlPullParser = newXmlPullParser();
            newXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-docdecl", true);
            newXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            return newXmlPullParser;
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public static org.xmlpull.v1.XmlPullParser newPullParser$ravenwood() {
        try {
            org.xmlpull.v1.XmlPullParser newXmlPullParser = newXmlPullParser();
            newXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            return newXmlPullParser;
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public static com.android.modules.utils.TypedXmlPullParser newFastPullParser() {
        return com.android.internal.util.XmlUtils.makeTyped(newPullParser());
    }

    public static com.android.modules.utils.TypedXmlPullParser newBinaryPullParser() {
        return new com.android.internal.util.ArtBinaryXmlPullParser();
    }

    public static com.android.modules.utils.TypedXmlPullParser newBinaryPullParser$ravenwood() {
        return new com.android.modules.utils.BinaryXmlPullParser();
    }

    public static com.android.modules.utils.TypedXmlPullParser resolvePullParser(java.io.InputStream inputStream) throws java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser newFastPullParser;
        byte[] bArr = new byte[4];
        if (ENABLE_RESOLVE_OPTIMIZATIONS && (inputStream instanceof java.io.FileInputStream)) {
            try {
                android.system.Os.pread(((java.io.FileInputStream) inputStream).getFD(), bArr, 0, 4, 0L);
            } catch (android.system.ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } else {
            if (!inputStream.markSupported()) {
                inputStream = new java.io.BufferedInputStream(inputStream);
            }
            inputStream.mark(8);
            inputStream.read(bArr);
            inputStream.reset();
        }
        if (java.util.Arrays.equals(bArr, com.android.modules.utils.BinaryXmlSerializer.PROTOCOL_MAGIC_VERSION_0)) {
            newFastPullParser = newBinaryPullParser();
        } else {
            newFastPullParser = newFastPullParser();
        }
        try {
            newFastPullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            return newFastPullParser;
        } catch (org.xmlpull.v1.XmlPullParserException e2) {
            throw new java.io.IOException(e2);
        }
    }

    public static org.xmlpull.v1.XmlSerializer newSerializer() {
        return newXmlSerializer();
    }

    public static com.android.modules.utils.TypedXmlSerializer newFastSerializer() {
        return com.android.internal.util.XmlUtils.makeTyped(new com.android.internal.util.FastXmlSerializer());
    }

    public static com.android.modules.utils.TypedXmlSerializer newBinarySerializer() {
        return new com.android.internal.util.ArtBinaryXmlSerializer();
    }

    public static com.android.modules.utils.TypedXmlSerializer newBinarySerializer$ravenwood() {
        return new com.android.modules.utils.BinaryXmlSerializer();
    }

    public static com.android.modules.utils.TypedXmlSerializer resolveSerializer(java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer newFastSerializer;
        if (ENABLE_BINARY_DEFAULT) {
            newFastSerializer = newBinarySerializer();
        } else {
            newFastSerializer = newFastSerializer();
        }
        newFastSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        return newFastSerializer;
    }

    public static com.android.modules.utils.TypedXmlSerializer resolveSerializer$ravenwood(java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.modules.utils.BinaryXmlSerializer binaryXmlSerializer = new com.android.modules.utils.BinaryXmlSerializer();
        binaryXmlSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        return binaryXmlSerializer;
    }

    public static void copy(org.xmlpull.v1.XmlPullParser xmlPullParser, org.xmlpull.v1.XmlSerializer xmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (xmlPullParser.getEventType() == 0) {
            xmlSerializer.startDocument(xmlPullParser.getInputEncoding(), true);
        }
        while (true) {
            int nextToken = xmlPullParser.nextToken();
            switch (nextToken) {
                case 0:
                    xmlSerializer.startDocument(xmlPullParser.getInputEncoding(), true);
                    break;
                case 1:
                    xmlSerializer.endDocument();
                    return;
                case 2:
                    xmlSerializer.startTag(normalizeNamespace(xmlPullParser.getNamespace()), xmlPullParser.getName());
                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                        xmlSerializer.attribute(normalizeNamespace(xmlPullParser.getAttributeNamespace(i)), xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
                    }
                    break;
                case 3:
                    xmlSerializer.endTag(normalizeNamespace(xmlPullParser.getNamespace()), xmlPullParser.getName());
                    break;
                case 4:
                    xmlSerializer.text(xmlPullParser.getText());
                    break;
                case 5:
                    xmlSerializer.cdsect(xmlPullParser.getText());
                    break;
                case 6:
                    xmlSerializer.entityRef(xmlPullParser.getName());
                    break;
                case 7:
                    xmlSerializer.ignorableWhitespace(xmlPullParser.getText());
                    break;
                case 8:
                    xmlSerializer.processingInstruction(xmlPullParser.getText());
                    break;
                case 9:
                    xmlSerializer.comment(xmlPullParser.getText());
                    break;
                case 10:
                    xmlSerializer.docdecl(xmlPullParser.getText());
                    break;
                default:
                    throw new java.lang.IllegalStateException("Unknown token " + nextToken);
            }
        }
    }

    private static java.lang.String normalizeNamespace(java.lang.String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return str;
    }

    public enum Encoding {
        US_ASCII("US-ASCII"),
        UTF_8(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8),
        UTF_16("UTF-16"),
        ISO_8859_1("ISO-8859-1");

        final java.lang.String expatName;

        Encoding(java.lang.String str) {
            this.expatName = str;
        }
    }

    public static android.util.Xml.Encoding findEncodingByName(java.lang.String str) throws java.io.UnsupportedEncodingException {
        if (str == null) {
            return android.util.Xml.Encoding.UTF_8;
        }
        for (android.util.Xml.Encoding encoding : android.util.Xml.Encoding.values()) {
            if (encoding.expatName.equalsIgnoreCase(str)) {
                return encoding;
            }
        }
        throw new java.io.UnsupportedEncodingException(str);
    }

    public static android.util.AttributeSet asAttributeSet(org.xmlpull.v1.XmlPullParser xmlPullParser) {
        if (xmlPullParser instanceof android.util.AttributeSet) {
            return (android.util.AttributeSet) xmlPullParser;
        }
        return new android.util.XmlPullAttributes(xmlPullParser);
    }

    private static org.xmlpull.v1.XmlSerializer newXmlSerializer() {
        return libcore.util.XmlObjectFactory.newXmlSerializer();
    }

    private static org.xmlpull.v1.XmlSerializer newXmlSerializer$ravenwood() {
        try {
            return org.xmlpull.v1.XmlPullParserFactory.newInstance().newSerializer();
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw new java.lang.UnsupportedOperationException(e);
        }
    }

    private static org.xmlpull.v1.XmlPullParser newXmlPullParser() {
        return libcore.util.XmlObjectFactory.newXmlPullParser();
    }

    private static org.xmlpull.v1.XmlPullParser newXmlPullParser$ravenwood() {
        try {
            return org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser();
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw new java.lang.UnsupportedOperationException(e);
        }
    }

    private static org.xml.sax.XMLReader newXMLReader() {
        return libcore.util.XmlObjectFactory.newXMLReader();
    }

    private static org.xml.sax.XMLReader newXMLReader$ravenwood() {
        try {
            javax.xml.parsers.SAXParserFactory newInstance = javax.xml.parsers.SAXParserFactory.newInstance();
            newInstance.setNamespaceAware(true);
            return newInstance.newSAXParser().getXMLReader();
        } catch (java.lang.Exception e) {
            throw new java.lang.UnsupportedOperationException(e);
        }
    }
}
