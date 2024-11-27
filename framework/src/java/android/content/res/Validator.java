package android.content.res;

/* loaded from: classes.dex */
public class Validator {
    private final java.util.ArrayDeque<android.content.res.Element> mElements = new java.util.ArrayDeque<>();

    private void cleanUp() {
        while (!this.mElements.isEmpty()) {
            this.mElements.pop().recycle();
        }
    }

    public void validate(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth();
        if (depth > this.mElements.size() + 1) {
            return;
        }
        if (eventType != 2) {
            if (eventType == 3 && depth == this.mElements.size()) {
                this.mElements.pop().recycle();
                return;
            } else {
                if (eventType == 1) {
                    cleanUp();
                    return;
                }
                return;
            }
        }
        java.lang.String name = xmlPullParser.getName();
        if (android.content.res.Element.shouldValidate(name)) {
            android.content.res.Element obtain = android.content.res.Element.obtain(name);
            android.content.res.Element peek = this.mElements.peek();
            if (peek != null && peek.hasChild(name)) {
                try {
                    peek.seen(obtain);
                } catch (java.lang.SecurityException e) {
                    cleanUp();
                    throw e;
                }
            }
            this.mElements.push(obtain);
        }
    }

    public void validateResStrAttr(org.xmlpull.v1.XmlPullParser xmlPullParser, int i, java.lang.CharSequence charSequence) {
        if (xmlPullParser.getDepth() > this.mElements.size()) {
            return;
        }
        this.mElements.peek().validateResStrAttr(i, charSequence);
        if (i == 1) {
            validateComponentMetadata(charSequence.toString());
        }
    }

    public void validateStrAttr(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.lang.String str2) {
        if (xmlPullParser.getDepth() > this.mElements.size()) {
            return;
        }
        this.mElements.peek().validateStrAttr(str, str2);
        if (str.equals("value")) {
            validateComponentMetadata(str2);
        }
    }

    private void validateComponentMetadata(java.lang.String str) {
        if (this.mElements.peek().mTag.equals("meta-data") && this.mElements.size() > 1) {
            android.content.res.Element pop = this.mElements.pop();
            this.mElements.peek().validateComponentMetadata(str);
            this.mElements.push(pop);
        }
    }
}
