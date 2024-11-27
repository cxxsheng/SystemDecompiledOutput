package android.media;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlParser {
    private static final int DEFAULT_FRAMERATE = 30;
    private static final int DEFAULT_SUBFRAMERATE = 1;
    private static final int DEFAULT_TICKRATE = 1;
    static final java.lang.String TAG = "TtmlParser";
    private long mCurrentRunId;
    private final android.media.TtmlNodeListener mListener;
    private org.xmlpull.v1.XmlPullParser mParser;

    public TtmlParser(android.media.TtmlNodeListener ttmlNodeListener) {
        this.mListener = ttmlNodeListener;
    }

    public void parse(java.lang.String str, long j) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mParser = null;
        this.mCurrentRunId = j;
        loadParser(str);
        parseTtml();
    }

    private void loadParser(java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
        org.xmlpull.v1.XmlPullParserFactory newInstance = org.xmlpull.v1.XmlPullParserFactory.newInstance();
        newInstance.setNamespaceAware(false);
        this.mParser = newInstance.newPullParser();
        this.mParser.setInput(new java.io.StringReader(str));
    }

    private void extractAttribute(org.xmlpull.v1.XmlPullParser xmlPullParser, int i, java.lang.StringBuilder sb) {
        sb.append(" ");
        sb.append(xmlPullParser.getAttributeName(i));
        sb.append("=\"");
        sb.append(xmlPullParser.getAttributeValue(i));
        sb.append("\"");
    }

    private void parseTtml() throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.util.LinkedList linkedList = new java.util.LinkedList();
        boolean z = true;
        int i = 0;
        while (!isEndOfDoc()) {
            int eventType = this.mParser.getEventType();
            android.media.TtmlNode ttmlNode = (android.media.TtmlNode) linkedList.peekLast();
            if (z) {
                if (eventType == 2) {
                    if (!isSupportedTag(this.mParser.getName())) {
                        android.util.Log.w(TAG, "Unsupported tag " + this.mParser.getName() + " is ignored.");
                        i++;
                        z = false;
                    } else {
                        android.media.TtmlNode parseNode = parseNode(ttmlNode);
                        linkedList.addLast(parseNode);
                        if (ttmlNode != null) {
                            ttmlNode.mChildren.add(parseNode);
                        }
                    }
                } else if (eventType == 4) {
                    java.lang.String applyDefaultSpacePolicy = android.media.TtmlUtils.applyDefaultSpacePolicy(this.mParser.getText());
                    if (!android.text.TextUtils.isEmpty(applyDefaultSpacePolicy)) {
                        ttmlNode.mChildren.add(new android.media.TtmlNode(android.media.TtmlUtils.PCDATA, "", applyDefaultSpacePolicy, 0L, Long.MAX_VALUE, ttmlNode, this.mCurrentRunId));
                    }
                } else if (eventType == 3) {
                    if (this.mParser.getName().equals("p")) {
                        this.mListener.onTtmlNodeParsed((android.media.TtmlNode) linkedList.getLast());
                    } else if (this.mParser.getName().equals(android.media.TtmlUtils.TAG_TT)) {
                        this.mListener.onRootNodeParsed((android.media.TtmlNode) linkedList.getLast());
                    }
                    linkedList.removeLast();
                }
            } else if (eventType == 2) {
                i++;
            } else if (eventType == 3 && i - 1 == 0) {
                z = true;
            }
            this.mParser.next();
        }
    }

    private android.media.TtmlNode parseNode(android.media.TtmlNode ttmlNode) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        long j;
        long j2;
        if (this.mParser.getEventType() != 2) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        long j3 = 0;
        long j4 = Long.MAX_VALUE;
        long j5 = 0;
        for (int i = 0; i < this.mParser.getAttributeCount(); i++) {
            java.lang.String attributeName = this.mParser.getAttributeName(i);
            java.lang.String attributeValue = this.mParser.getAttributeValue(i);
            java.lang.String replaceFirst = attributeName.replaceFirst("^.*:", "");
            if (replaceFirst.equals("begin")) {
                j3 = android.media.TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
            } else if (replaceFirst.equals("end")) {
                j4 = android.media.TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
            } else if (!replaceFirst.equals(android.media.TtmlUtils.ATTR_DURATION)) {
                extractAttribute(this.mParser, i, sb);
            } else {
                j5 = android.media.TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
            }
        }
        if (ttmlNode == null) {
            j = j3;
        } else {
            long j6 = j3 + ttmlNode.mStartTimeMs;
            if (j4 == Long.MAX_VALUE) {
                j = j6;
            } else {
                j4 += ttmlNode.mStartTimeMs;
                j = j6;
            }
        }
        if (j5 > 0) {
            if (j4 != Long.MAX_VALUE) {
                android.util.Log.e(TAG, "'dur' and 'end' attributes are defined at the same time.'end' value is ignored.");
            }
            j4 = j + j5;
        }
        if (ttmlNode != null && j4 == Long.MAX_VALUE && ttmlNode.mEndTimeMs != Long.MAX_VALUE && j4 > ttmlNode.mEndTimeMs) {
            j2 = ttmlNode.mEndTimeMs;
        } else {
            j2 = j4;
        }
        return new android.media.TtmlNode(this.mParser.getName(), sb.toString(), null, j, j2, ttmlNode, this.mCurrentRunId);
    }

    private boolean isEndOfDoc() throws org.xmlpull.v1.XmlPullParserException {
        return this.mParser.getEventType() == 1;
    }

    private static boolean isSupportedTag(java.lang.String str) {
        if (str.equals(android.media.TtmlUtils.TAG_TT) || str.equals(android.media.TtmlUtils.TAG_HEAD) || str.equals("body") || str.equals(android.media.TtmlUtils.TAG_DIV) || str.equals("p") || str.equals(android.media.TtmlUtils.TAG_SPAN) || str.equals(android.media.TtmlUtils.TAG_BR) || str.equals("style") || str.equals(android.media.TtmlUtils.TAG_STYLING) || str.equals(android.media.TtmlUtils.TAG_LAYOUT) || str.equals(android.media.TtmlUtils.TAG_REGION) || str.equals(android.media.TtmlUtils.TAG_METADATA) || str.equals(android.media.TtmlUtils.TAG_SMPTE_IMAGE) || str.equals(android.media.TtmlUtils.TAG_SMPTE_DATA) || str.equals(android.media.TtmlUtils.TAG_SMPTE_INFORMATION)) {
            return true;
        }
        return false;
    }
}
