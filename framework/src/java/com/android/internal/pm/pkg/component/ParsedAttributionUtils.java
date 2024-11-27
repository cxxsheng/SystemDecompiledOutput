package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedAttributionUtils {
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00ad, code lost:
    
        if (r5 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00af, code lost:
    
        r5 = java.util.Collections.emptyList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c3, code lost:
    
        return r11.success(new com.android.internal.pm.pkg.component.ParsedAttributionImpl(r3, r4, r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b4, code lost:
    
        ((java.util.ArrayList) r5).trimToSize();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedAttribution> parseAttribution(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestAttribution);
        if (obtainAttributes == null) {
            return parseInput.error("<attribution> could not be parsed");
        }
        try {
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(1, 0);
            if (nonConfigurationString == null) {
                return parseInput.error("<attribution> does not specify android:tag");
            }
            if (nonConfigurationString.length() > 50) {
                return parseInput.error("android:tag is too long. Max length is 50");
            }
            int resourceId = obtainAttributes.getResourceId(0, 0);
            if (resourceId == 0) {
                return parseInput.error("<attribution> does not specify android:label");
            }
            obtainAttributes.recycle();
            int depth = xmlResourceParser.getDepth();
            java.util.List list = null;
            while (true) {
                int next = xmlResourceParser.next();
                if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                    break;
                }
                if (next != 3 && next != 4) {
                    java.lang.String name = xmlResourceParser.getName();
                    if (!name.equals("inherit-from")) {
                        return parseInput.error("Bad element under <attribution>: " + name);
                    }
                    obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestAttributionInheritFrom);
                    if (obtainAttributes == null) {
                        return parseInput.error("<inherit-from> could not be parsed");
                    }
                    try {
                        java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(0, 0);
                        if (list == null) {
                            list = new java.util.ArrayList();
                        }
                        list.add(nonConfigurationString2);
                    } finally {
                    }
                }
            }
        } finally {
        }
    }

    public static boolean isCombinationValid(java.util.List<com.android.internal.pm.pkg.component.ParsedAttribution> list) {
        if (list == null) {
            return true;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(list.size());
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        int size = list.size();
        if (size > 400) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!arraySet.add(list.get(i).getTag())) {
                return false;
            }
        }
        for (int i2 = 0; i2 < size; i2++) {
            java.util.List<java.lang.String> inheritFrom = list.get(i2).getInheritFrom();
            int size2 = inheritFrom.size();
            for (int i3 = 0; i3 < size2; i3++) {
                java.lang.String str = inheritFrom.get(i3);
                if (arraySet.contains(str) || !arraySet2.add(str)) {
                    return false;
                }
            }
        }
        return true;
    }
}
