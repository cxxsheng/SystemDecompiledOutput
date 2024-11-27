package com.android.server.integrity.parser;

/* loaded from: classes2.dex */
public class RuleMetadataParser {
    public static final java.lang.String RULE_PROVIDER_TAG = "P";
    public static final java.lang.String VERSION_TAG = "V";

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0025, code lost:
    
        if (r2.equals(com.android.server.integrity.parser.RuleMetadataParser.VERSION_TAG) != false) goto L17;
     */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.server.integrity.model.RuleMetadata parse(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        java.lang.String str = "";
        java.lang.String str2 = "";
        while (true) {
            int next = resolvePullParser.next();
            char c = 1;
            if (next != 1) {
                if (next == 2) {
                    java.lang.String name = resolvePullParser.getName();
                    switch (name.hashCode()) {
                        case 80:
                            if (name.equals(RULE_PROVIDER_TAG)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 86:
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            str = resolvePullParser.nextText();
                            break;
                        case 1:
                            str2 = resolvePullParser.nextText();
                            break;
                        default:
                            throw new java.lang.IllegalStateException("Unknown tag in metadata: " + name);
                    }
                }
            } else {
                return new com.android.server.integrity.model.RuleMetadata(str, str2);
            }
        }
    }
}
