package com.android.server.pm;

/* loaded from: classes2.dex */
class ShareTargetInfo {
    private static final java.lang.String ATTR_HOST = "host";
    private static final java.lang.String ATTR_MIME_TYPE = "mimeType";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_PATH = "path";
    private static final java.lang.String ATTR_PATH_PATTERN = "pathPattern";
    private static final java.lang.String ATTR_PATH_PREFIX = "pathPrefix";
    private static final java.lang.String ATTR_PORT = "port";
    private static final java.lang.String ATTR_SCHEME = "scheme";
    private static final java.lang.String ATTR_TARGET_CLASS = "targetClass";
    private static final java.lang.String TAG_CATEGORY = "category";
    private static final java.lang.String TAG_DATA = "data";
    private static final java.lang.String TAG_SHARE_TARGET = "share-target";
    final java.lang.String[] mCategories;
    final java.lang.String mTargetClass;
    final com.android.server.pm.ShareTargetInfo.TargetData[] mTargetData;

    static class TargetData {
        final java.lang.String mHost;
        final java.lang.String mMimeType;
        final java.lang.String mPath;
        final java.lang.String mPathPattern;
        final java.lang.String mPathPrefix;
        final java.lang.String mPort;
        final java.lang.String mScheme;

        TargetData(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7) {
            this.mScheme = str;
            this.mHost = str2;
            this.mPort = str3;
            this.mPath = str4;
            this.mPathPattern = str5;
            this.mPathPrefix = str6;
            this.mMimeType = str7;
        }

        public void toStringInner(java.lang.StringBuilder sb) {
            if (!android.text.TextUtils.isEmpty(this.mScheme)) {
                sb.append(" scheme=");
                sb.append(this.mScheme);
            }
            if (!android.text.TextUtils.isEmpty(this.mHost)) {
                sb.append(" host=");
                sb.append(this.mHost);
            }
            if (!android.text.TextUtils.isEmpty(this.mPort)) {
                sb.append(" port=");
                sb.append(this.mPort);
            }
            if (!android.text.TextUtils.isEmpty(this.mPath)) {
                sb.append(" path=");
                sb.append(this.mPath);
            }
            if (!android.text.TextUtils.isEmpty(this.mPathPattern)) {
                sb.append(" pathPattern=");
                sb.append(this.mPathPattern);
            }
            if (!android.text.TextUtils.isEmpty(this.mPathPrefix)) {
                sb.append(" pathPrefix=");
                sb.append(this.mPathPrefix);
            }
            if (!android.text.TextUtils.isEmpty(this.mMimeType)) {
                sb.append(" mimeType=");
                sb.append(this.mMimeType);
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            toStringInner(sb);
            return sb.toString();
        }
    }

    ShareTargetInfo(com.android.server.pm.ShareTargetInfo.TargetData[] targetDataArr, java.lang.String str, java.lang.String[] strArr) {
        this.mTargetData = targetDataArr;
        this.mTargetClass = str;
        this.mCategories = strArr;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("targetClass=");
        sb.append(this.mTargetClass);
        for (int i = 0; i < this.mTargetData.length; i++) {
            sb.append(" data={");
            this.mTargetData[i].toStringInner(sb);
            sb.append("}");
        }
        for (int i2 = 0; i2 < this.mCategories.length; i2++) {
            sb.append(" category=");
            sb.append(this.mCategories[i2]);
        }
        return sb.toString();
    }

    void saveToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, TAG_SHARE_TARGET);
        com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_TARGET_CLASS, this.mTargetClass);
        for (int i = 0; i < this.mTargetData.length; i++) {
            typedXmlSerializer.startTag((java.lang.String) null, "data");
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_SCHEME, this.mTargetData[i].mScheme);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, "host", this.mTargetData[i].mHost);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PORT, this.mTargetData[i].mPort);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PATH, this.mTargetData[i].mPath);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PATH_PATTERN, this.mTargetData[i].mPathPattern);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_PATH_PREFIX, this.mTargetData[i].mPathPrefix);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, ATTR_MIME_TYPE, this.mTargetData[i].mMimeType);
            typedXmlSerializer.endTag((java.lang.String) null, "data");
        }
        for (int i2 = 0; i2 < this.mCategories.length; i2++) {
            typedXmlSerializer.startTag((java.lang.String) null, TAG_CATEGORY);
            com.android.server.pm.ShortcutService.writeAttr(typedXmlSerializer, "name", this.mCategories[i2]);
            typedXmlSerializer.endTag((java.lang.String) null, TAG_CATEGORY);
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_SHARE_TARGET);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x002d, code lost:
    
        if (r3.equals(com.android.server.pm.ShareTargetInfo.TAG_CATEGORY) != false) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static com.android.server.pm.ShareTargetInfo loadFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String parseStringAttribute = com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_TARGET_CLASS);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        while (true) {
            int next = typedXmlPullParser.next();
            char c = 1;
            if (next != 1) {
                if (next == 2) {
                    java.lang.String name = typedXmlPullParser.getName();
                    switch (name.hashCode()) {
                        case 3076010:
                            if (name.equals("data")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 50511102:
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            arrayList.add(parseTargetData(typedXmlPullParser));
                            break;
                        case 1:
                            arrayList2.add(com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, "name"));
                            break;
                    }
                } else if (next == 3 && typedXmlPullParser.getName().equals(TAG_SHARE_TARGET)) {
                }
            }
        }
        if (arrayList.isEmpty() || parseStringAttribute == null || arrayList2.isEmpty()) {
            return null;
        }
        return new com.android.server.pm.ShareTargetInfo((com.android.server.pm.ShareTargetInfo.TargetData[]) arrayList.toArray(new com.android.server.pm.ShareTargetInfo.TargetData[arrayList.size()]), parseStringAttribute, (java.lang.String[]) arrayList2.toArray(new java.lang.String[arrayList2.size()]));
    }

    private static com.android.server.pm.ShareTargetInfo.TargetData parseTargetData(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        return new com.android.server.pm.ShareTargetInfo.TargetData(com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_SCHEME), com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, "host"), com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_PORT), com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_PATH), com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_PATH_PATTERN), com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_PATH_PREFIX), com.android.server.pm.ShortcutService.parseStringAttribute(typedXmlPullParser, ATTR_MIME_TYPE));
    }
}
