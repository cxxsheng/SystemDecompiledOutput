package com.android.server.firewall;

/* loaded from: classes.dex */
public abstract class FilterFactory {
    private final java.lang.String mTag;

    public abstract com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;

    protected FilterFactory(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException();
        }
        this.mTag = str;
    }

    public java.lang.String getTagName() {
        return this.mTag;
    }
}
