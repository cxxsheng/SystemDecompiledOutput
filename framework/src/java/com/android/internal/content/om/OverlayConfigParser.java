package com.android.internal.content.om;

/* loaded from: classes4.dex */
public final class OverlayConfigParser {
    private static final java.lang.String CONFIG_DEFAULT_FILENAME = "config/config.xml";
    private static final java.lang.String CONFIG_DIRECTORY = "config";
    static final boolean DEFAULT_ENABLED_STATE = false;
    static final boolean DEFAULT_MUTABILITY = true;
    private static final int MAXIMUM_MERGE_DEPTH = 5;

    public static class ParsedConfigFile {
        public final int line;
        public final java.lang.String path;
        public final java.lang.String xml;

        ParsedConfigFile(java.lang.String str, int i, java.lang.String str2) {
            this.path = str;
            this.line = i;
            this.xml = str2;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(getClass().getSimpleName());
            sb.append("{path=");
            sb.append(this.path);
            sb.append(", line=");
            sb.append(this.line);
            if (this.xml != null) {
                sb.append(", xml=");
                sb.append(this.xml);
            }
            sb.append("}");
            return sb.toString();
        }
    }

    public static class ParsedConfiguration {
        public final boolean enabled;
        public final boolean mutable;
        public final java.lang.String packageName;
        public final com.android.internal.content.om.OverlayConfigParser.ParsedConfigFile parsedConfigFile;
        public final com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parsedInfo;
        public final java.lang.String policy;

        ParsedConfiguration(java.lang.String str, boolean z, boolean z2, java.lang.String str2, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parsedOverlayInfo, com.android.internal.content.om.OverlayConfigParser.ParsedConfigFile parsedConfigFile) {
            this.packageName = str;
            this.enabled = z;
            this.mutable = z2;
            this.policy = str2;
            this.parsedInfo = parsedOverlayInfo;
            this.parsedConfigFile = parsedConfigFile;
        }

        public java.lang.String toString() {
            return getClass().getSimpleName() + java.lang.String.format("{packageName=%s, enabled=%s, mutable=%s, policy=%s, parsedInfo=%s, parsedConfigFile=%s}", this.packageName, java.lang.Boolean.valueOf(this.enabled), java.lang.Boolean.valueOf(this.mutable), this.policy, this.parsedInfo, this.parsedConfigFile);
        }
    }

    public static class OverlayPartition extends android.content.pm.PackagePartitions.SystemPartition {
        static final java.lang.String POLICY_ODM = "odm";
        static final java.lang.String POLICY_OEM = "oem";
        static final java.lang.String POLICY_PRODUCT = "product";
        static final java.lang.String POLICY_PUBLIC = "public";
        static final java.lang.String POLICY_SYSTEM = "system";
        static final java.lang.String POLICY_VENDOR = "vendor";
        public final java.lang.String policy;

        public OverlayPartition(android.content.pm.PackagePartitions.SystemPartition systemPartition) {
            super(systemPartition);
            this.policy = policyForPartition(systemPartition);
        }

        OverlayPartition(java.io.File file, android.content.pm.PackagePartitions.SystemPartition systemPartition) {
            super(file, systemPartition);
            this.policy = policyForPartition(systemPartition);
        }

        private static java.lang.String policyForPartition(android.content.pm.PackagePartitions.SystemPartition systemPartition) {
            switch (systemPartition.type) {
                case 0:
                case 5:
                    return "system";
                case 1:
                    return "vendor";
                case 2:
                    return "odm";
                case 3:
                    return "oem";
                case 4:
                    return "product";
                default:
                    throw new java.lang.IllegalStateException("Unable to determine policy for " + systemPartition.getFolder());
            }
        }
    }

    private static class ParsingContext {
        private final android.util.ArraySet<java.lang.String> mConfiguredOverlays;
        private boolean mFoundMutableOverlay;
        private int mMergeDepth;
        private final java.util.ArrayList<com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration> mOrderedConfigurations;
        private final com.android.internal.content.om.OverlayConfigParser.OverlayPartition mPartition;

        private ParsingContext(com.android.internal.content.om.OverlayConfigParser.OverlayPartition overlayPartition) {
            this.mOrderedConfigurations = new java.util.ArrayList<>();
            this.mConfiguredOverlays = new android.util.ArraySet<>();
            this.mPartition = overlayPartition;
        }
    }

    static java.util.ArrayList<com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration> getConfigurations(com.android.internal.content.om.OverlayConfigParser.OverlayPartition overlayPartition, com.android.internal.content.om.OverlayScanner overlayScanner, java.util.Map<java.lang.String, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> map, java.util.List<java.lang.String> list) {
        if (overlayScanner != null) {
            if (overlayPartition.getOverlayFolder() != null) {
                overlayScanner.scanDir(overlayPartition.getOverlayFolder());
            }
            java.util.Iterator<java.lang.String> it = list.iterator();
            while (it.hasNext()) {
                overlayScanner.scanDir(new java.io.File("/apex/" + it.next() + "/overlay/"));
            }
        }
        if (overlayPartition.getOverlayFolder() == null) {
            return null;
        }
        java.io.File file = new java.io.File(overlayPartition.getOverlayFolder(), CONFIG_DEFAULT_FILENAME);
        if (!file.exists()) {
            return null;
        }
        com.android.internal.content.om.OverlayConfigParser.ParsingContext parsingContext = new com.android.internal.content.om.OverlayConfigParser.ParsingContext(overlayPartition);
        readConfigFile(file, overlayScanner, map, parsingContext);
        return parsingContext.mOrderedConfigurations;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static void readConfigFile(java.io.File file, com.android.internal.content.om.OverlayScanner overlayScanner, java.util.Map<java.lang.String, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> map, com.android.internal.content.om.OverlayConfigParser.ParsingContext parsingContext) {
        java.io.FileReader fileReader;
        char c;
        try {
            try {
                fileReader = new java.io.FileReader(file);
                try {
                    org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                    newPullParser.setInput(fileReader);
                    com.android.internal.util.XmlUtils.beginDocument(newPullParser, CONFIG_DIRECTORY);
                    int depth = newPullParser.getDepth();
                    while (com.android.internal.util.XmlUtils.nextElementWithin(newPullParser, depth)) {
                        java.lang.String name = newPullParser.getName();
                        switch (name.hashCode()) {
                            case -1091287984:
                                if (name.equals("overlay")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 103785528:
                                if (name.equals("merge")) {
                                    c = 0;
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
                                parseMerge(file, newPullParser, overlayScanner, map, parsingContext);
                                break;
                            case 1:
                                parseOverlay(file, newPullParser, overlayScanner, map, parsingContext);
                                break;
                            default:
                                android.util.Log.w("OverlayConfig", java.lang.String.format("Tag %s is unknown in %s at %s", name, file, newPullParser.getPositionDescription()));
                                break;
                        }
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Log.w("OverlayConfig", "Got exception parsing overlay configuration.", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileReader);
            }
        } catch (java.io.FileNotFoundException e2) {
            android.util.Log.w("OverlayConfig", "Couldn't find or open overlay configuration file " + file);
        }
    }

    private static void parseMerge(java.io.File file, org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.internal.content.om.OverlayScanner overlayScanner, java.util.Map<java.lang.String, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> map, com.android.internal.content.om.OverlayConfigParser.ParsingContext parsingContext) {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "path");
        if (attributeValue == null) {
            throw new java.lang.IllegalStateException(java.lang.String.format("<merge> without path in %s at %s" + file, xmlPullParser.getPositionDescription()));
        }
        if (attributeValue.startsWith("/")) {
            throw new java.lang.IllegalStateException(java.lang.String.format("Path %s must be relative to the directory containing overlay configurations  files in %s at %s ", attributeValue, file, xmlPullParser.getPositionDescription()));
        }
        int i = parsingContext.mMergeDepth;
        parsingContext.mMergeDepth = i + 1;
        if (i == 5) {
            throw new java.lang.IllegalStateException(java.lang.String.format("Maximum <merge> depth exceeded in %s at %s", file, xmlPullParser.getPositionDescription()));
        }
        try {
            java.io.File canonicalFile = new java.io.File(parsingContext.mPartition.getOverlayFolder(), CONFIG_DIRECTORY).getCanonicalFile();
            java.io.File canonicalFile2 = new java.io.File(canonicalFile, attributeValue).getCanonicalFile();
            if (!canonicalFile2.exists()) {
                throw new java.lang.IllegalStateException(java.lang.String.format("Merged configuration file %s does not exist in %s at %s", attributeValue, file, xmlPullParser.getPositionDescription()));
            }
            if (!android.os.FileUtils.contains(canonicalFile, canonicalFile2)) {
                throw new java.lang.IllegalStateException(java.lang.String.format("Merged file %s outside of configuration directory in %s at %s", canonicalFile2.getAbsolutePath(), canonicalFile2, xmlPullParser.getPositionDescription()));
            }
            readConfigFile(canonicalFile2, overlayScanner, map, parsingContext);
            parsingContext.mMergeDepth--;
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException(java.lang.String.format("Couldn't find or open merged configuration file %s in %s at %s", attributeValue, file, xmlPullParser.getPositionDescription()), e);
        }
    }

    private static void parseOverlay(java.io.File file, org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.internal.content.om.OverlayScanner overlayScanner, java.util.Map<java.lang.String, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> map, com.android.internal.content.om.OverlayConfigParser.ParsingContext parsingContext) {
        com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parsedOverlayInfo;
        boolean z;
        boolean z2;
        com.android.internal.util.Preconditions.checkArgument((overlayScanner == null) != (map == null), "scanner and packageManagerOverlayInfos cannot be both null or both non-null");
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "package");
        if (attributeValue == null) {
            throw new java.lang.IllegalStateException(java.lang.String.format("\"<overlay> without package in %s at %s", file, xmlPullParser.getPositionDescription()));
        }
        if (overlayScanner != null) {
            com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parsedInfo = overlayScanner.getParsedInfo(attributeValue);
            if (parsedInfo == null && overlayScanner.isExcludedOverlayPackage(attributeValue, parsingContext.mPartition)) {
                android.util.Log.d("OverlayConfig", "overlay " + attributeValue + " in partition " + parsingContext.mPartition.getOverlayFolder() + " is ignored.");
                return;
            } else {
                if (parsedInfo == null || !parsingContext.mPartition.containsOverlay(parsedInfo.path)) {
                    throw new java.lang.IllegalStateException(java.lang.String.format("overlay %s not present in partition %s in %s at %s", attributeValue, parsingContext.mPartition.getOverlayFolder(), file, xmlPullParser.getPositionDescription()));
                }
                parsedOverlayInfo = parsedInfo;
            }
        } else if (map.get(attributeValue) != null) {
            parsedOverlayInfo = null;
        } else {
            android.util.Log.d("OverlayConfig", "overlay " + attributeValue + " in partition " + parsingContext.mPartition.getOverlayFolder() + " is ignored.");
            return;
        }
        if (parsingContext.mConfiguredOverlays.contains(attributeValue)) {
            throw new java.lang.IllegalStateException(java.lang.String.format("overlay %s configured multiple times in a single partition in %s at %s", attributeValue, file, xmlPullParser.getPositionDescription()));
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "enabled");
        if (attributeValue2 == null) {
            z = false;
        } else {
            z = !"false".equals(attributeValue2);
        }
        java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "mutable");
        if (attributeValue3 == null) {
            z2 = true;
        } else {
            z2 = !"false".equals(attributeValue3);
            if (!z2 && parsingContext.mFoundMutableOverlay) {
                throw new java.lang.IllegalStateException(java.lang.String.format("immutable overlays must precede mutable overlays: found in %s at %s", file, xmlPullParser.getPositionDescription()));
            }
        }
        if (z2) {
            parsingContext.mFoundMutableOverlay = true;
        } else if (!z) {
            android.util.Log.w("OverlayConfig", "found default-disabled immutable overlay " + attributeValue);
        }
        com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration parsedConfiguration = new com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration(attributeValue, z, z2, parsingContext.mPartition.policy, parsedOverlayInfo, new com.android.internal.content.om.OverlayConfigParser.ParsedConfigFile(file.getPath().intern(), xmlPullParser.getLineNumber(), (android.os.Build.IS_ENG || android.os.Build.IS_USERDEBUG) ? currentParserContextToString(xmlPullParser) : null));
        parsingContext.mConfiguredOverlays.add(attributeValue);
        parsingContext.mOrderedConfigurations.add(parsedConfiguration);
    }

    private static java.lang.String currentParserContextToString(org.xmlpull.v1.XmlPullParser xmlPullParser) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("<");
        sb.append(xmlPullParser.getName());
        sb.append(" ");
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            sb.append(xmlPullParser.getAttributeName(i));
            sb.append("=\"");
            sb.append(xmlPullParser.getAttributeValue(i));
            sb.append("\" ");
        }
        sb.append("/>");
        return sb.toString();
    }
}
