package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class AliasActivity extends android.app.Activity {
    public final java.lang.String ALIAS_META_DATA = "android.app.alias";

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                android.content.res.XmlResourceParser loadXmlMetaData = getPackageManager().getActivityInfo(getComponentName(), 128).loadXmlMetaData(getPackageManager(), "android.app.alias");
                if (loadXmlMetaData == null) {
                    throw new java.lang.RuntimeException("Alias requires a meta-data field android.app.alias");
                }
                android.content.Intent parseAlias = parseAlias(loadXmlMetaData);
                if (parseAlias == null) {
                    throw new java.lang.RuntimeException("No <intent> tag found in alias description");
                }
                startActivity(parseAlias);
                finish();
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                throw new java.lang.RuntimeException("Error parsing alias", e);
            }
        } catch (java.lang.Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    private android.content.Intent parseAlias(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 1) {
                break;
            }
        } while (next != 2);
        java.lang.String name = xmlPullParser.getName();
        if (!"alias".equals(name)) {
            throw new java.lang.RuntimeException("Alias meta-data must start with <alias> tag; found" + name + " at " + xmlPullParser.getPositionDescription());
        }
        int depth = xmlPullParser.getDepth();
        android.content.Intent intent = null;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 == 1 || (next2 == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next2 != 3 && next2 != 4) {
                if ("intent".equals(xmlPullParser.getName())) {
                    android.content.Intent parseIntent = android.content.Intent.parseIntent(getResources(), xmlPullParser, asAttributeSet);
                    if (intent == null) {
                        intent = parseIntent;
                    }
                } else {
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
        return intent;
    }
}
