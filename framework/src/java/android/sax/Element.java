package android.sax;

/* loaded from: classes3.dex */
public class Element {
    android.sax.Children children;
    final int depth;
    android.sax.EndElementListener endElementListener;
    android.sax.EndTextElementListener endTextElementListener;
    final java.lang.String localName;
    final android.sax.Element parent;
    java.util.ArrayList<android.sax.Element> requiredChilden;
    android.sax.StartElementListener startElementListener;
    final java.lang.String uri;
    boolean visited;

    Element(android.sax.Element element, java.lang.String str, java.lang.String str2, int i) {
        this.parent = element;
        this.uri = str;
        this.localName = str2;
        this.depth = i;
    }

    public android.sax.Element getChild(java.lang.String str) {
        return getChild("", str);
    }

    public android.sax.Element getChild(java.lang.String str, java.lang.String str2) {
        if (this.endTextElementListener != null) {
            throw new java.lang.IllegalStateException("This element already has an end text element listener. It cannot have children.");
        }
        if (this.children == null) {
            this.children = new android.sax.Children();
        }
        return this.children.getOrCreate(this, str, str2);
    }

    public android.sax.Element requireChild(java.lang.String str) {
        return requireChild("", str);
    }

    public android.sax.Element requireChild(java.lang.String str, java.lang.String str2) {
        android.sax.Element child = getChild(str, str2);
        if (this.requiredChilden == null) {
            this.requiredChilden = new java.util.ArrayList<>();
            this.requiredChilden.add(child);
        } else if (!this.requiredChilden.contains(child)) {
            this.requiredChilden.add(child);
        }
        return child;
    }

    public void setElementListener(android.sax.ElementListener elementListener) {
        setStartElementListener(elementListener);
        setEndElementListener(elementListener);
    }

    public void setTextElementListener(android.sax.TextElementListener textElementListener) {
        setStartElementListener(textElementListener);
        setEndTextElementListener(textElementListener);
    }

    public void setStartElementListener(android.sax.StartElementListener startElementListener) {
        if (this.startElementListener != null) {
            throw new java.lang.IllegalStateException("Start element listener has already been set.");
        }
        this.startElementListener = startElementListener;
    }

    public void setEndElementListener(android.sax.EndElementListener endElementListener) {
        if (this.endElementListener != null) {
            throw new java.lang.IllegalStateException("End element listener has already been set.");
        }
        this.endElementListener = endElementListener;
    }

    public void setEndTextElementListener(android.sax.EndTextElementListener endTextElementListener) {
        if (this.endTextElementListener != null) {
            throw new java.lang.IllegalStateException("End text element listener has already been set.");
        }
        if (this.children != null) {
            throw new java.lang.IllegalStateException("This element already has children. It cannot have an end text element listener.");
        }
        this.endTextElementListener = endTextElementListener;
    }

    public java.lang.String toString() {
        return toString(this.uri, this.localName);
    }

    static java.lang.String toString(java.lang.String str, java.lang.String str2) {
        java.lang.StringBuilder append = new java.lang.StringBuilder().append("'");
        if (!str.equals("")) {
            str2 = str + ":" + str2;
        }
        return append.append(str2).append("'").toString();
    }

    void resetRequiredChildren() {
        java.util.ArrayList<android.sax.Element> arrayList = this.requiredChilden;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).visited = false;
            }
        }
    }

    void checkRequiredChildren(org.xml.sax.Locator locator) throws org.xml.sax.SAXParseException {
        java.util.ArrayList<android.sax.Element> arrayList = this.requiredChilden;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                android.sax.Element element = arrayList.get(size);
                if (!element.visited) {
                    throw new android.sax.BadXmlException("Element named " + this + " is missing required child element named " + element + android.media.MediaMetrics.SEPARATOR, locator);
                }
            }
        }
    }
}
