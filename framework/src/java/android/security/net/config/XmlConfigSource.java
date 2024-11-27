package android.security.net.config;

/* loaded from: classes3.dex */
public class XmlConfigSource implements android.security.net.config.ConfigSource {
    private static final int CONFIG_BASE = 0;
    private static final int CONFIG_DEBUG = 2;
    private static final int CONFIG_DOMAIN = 1;
    private final android.content.pm.ApplicationInfo mApplicationInfo;
    private android.content.Context mContext;
    private final boolean mDebugBuild;
    private android.security.net.config.NetworkSecurityConfig mDefaultConfig;
    private java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> mDomainMap;
    private boolean mInitialized;
    private final java.lang.Object mLock = new java.lang.Object();
    private final int mResourceId;

    public XmlConfigSource(android.content.Context context, int i, android.content.pm.ApplicationInfo applicationInfo) {
        this.mContext = context;
        this.mResourceId = i;
        this.mApplicationInfo = new android.content.pm.ApplicationInfo(applicationInfo);
        this.mDebugBuild = (this.mApplicationInfo.flags & 2) != 0;
    }

    @Override // android.security.net.config.ConfigSource
    public java.util.Set<android.util.Pair<android.security.net.config.Domain, android.security.net.config.NetworkSecurityConfig>> getPerDomainConfigs() {
        ensureInitialized();
        return this.mDomainMap;
    }

    @Override // android.security.net.config.ConfigSource
    public android.security.net.config.NetworkSecurityConfig getDefaultConfig() {
        ensureInitialized();
        return this.mDefaultConfig;
    }

    private static final java.lang.String getConfigString(int i) {
        switch (i) {
            case 0:
                return "base-config";
            case 1:
                return "domain-config";
            case 2:
                return "debug-overrides";
            default:
                throw new java.lang.IllegalArgumentException("Unknown config type: " + i);
        }
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (this.mInitialized) {
                return;
            }
            try {
                android.content.res.XmlResourceParser xml = this.mContext.getResources().getXml(this.mResourceId);
                try {
                    parseNetworkSecurityConfig(xml);
                    this.mContext = null;
                    this.mInitialized = true;
                    if (xml != null) {
                        xml.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (xml != null) {
                        try {
                            xml.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (android.content.res.Resources.NotFoundException | android.security.net.config.XmlConfigSource.ParserException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                throw new java.lang.RuntimeException("Failed to parse XML configuration from " + this.mContext.getResources().getResourceEntryName(this.mResourceId), e);
            }
        }
    }

    private android.security.net.config.Pin parsePin(android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator.DIGEST);
        if (!android.security.net.config.Pin.isSupportedDigestAlgorithm(attributeValue)) {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Unsupported pin digest algorithm: " + attributeValue);
        }
        if (xmlResourceParser.next() != 4) {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Missing pin digest");
        }
        try {
            byte[] decode = android.util.Base64.decode(xmlResourceParser.getText().trim(), 0);
            int digestLength = android.security.net.config.Pin.getDigestLength(attributeValue);
            if (decode.length != digestLength) {
                throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "digest length " + decode.length + " does not match expected length for " + attributeValue + " of " + digestLength);
            }
            if (xmlResourceParser.next() != 3) {
                throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "pin contains additional elements");
            }
            return new android.security.net.config.Pin(attributeValue, decode);
        } catch (java.lang.IllegalArgumentException e) {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Invalid pin digest", e);
        }
    }

    private android.security.net.config.PinSet parsePinSet(android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        long time;
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, "expiration");
        if (attributeValue == null) {
            time = Long.MAX_VALUE;
        } else {
            try {
                java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                simpleDateFormat.setLenient(false);
                java.util.Date parse = simpleDateFormat.parse(attributeValue);
                if (parse == null) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Invalid expiration date in pin-set");
                }
                time = parse.getTime();
            } catch (java.text.ParseException e) {
                throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Invalid expiration date in pin-set", e);
            }
        }
        int depth = xmlResourceParser.getDepth();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if (xmlResourceParser.getName().equals(android.app.slice.SliceProvider.METHOD_PIN)) {
                arraySet.add(parsePin(xmlResourceParser));
            } else {
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        return new android.security.net.config.PinSet(arraySet, time);
    }

    private android.security.net.config.Domain parseDomain(android.content.res.XmlResourceParser xmlResourceParser, java.util.Set<java.lang.String> set) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        boolean attributeBooleanValue = xmlResourceParser.getAttributeBooleanValue(null, "includeSubdomains", false);
        if (xmlResourceParser.next() != 4) {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Domain name missing");
        }
        java.lang.String lowerCase = xmlResourceParser.getText().trim().toLowerCase(java.util.Locale.US);
        if (xmlResourceParser.next() != 3) {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "domain contains additional elements");
        }
        if (!set.add(lowerCase)) {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, lowerCase + " has already been specified");
        }
        return new android.security.net.config.Domain(lowerCase, attributeBooleanValue);
    }

    private boolean parseCertificateTransparency(android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        return xmlResourceParser.getAttributeBooleanValue(null, "enabled", false);
    }

    private android.security.net.config.CertificatesEntryRef parseCertificatesEntry(android.content.res.XmlResourceParser xmlResourceParser, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        android.security.net.config.CertificateSource wfaCertificateSource;
        boolean attributeBooleanValue = xmlResourceParser.getAttributeBooleanValue(null, "overridePins", z);
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, "src", -1);
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, "src");
        if (attributeValue == null) {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "certificates element missing src attribute");
        }
        if (attributeResourceValue != -1) {
            wfaCertificateSource = new android.security.net.config.ResourceCertificateSource(attributeResourceValue, this.mContext);
        } else if ("system".equals(attributeValue)) {
            wfaCertificateSource = android.security.net.config.SystemCertificateSource.getInstance();
        } else if ("user".equals(attributeValue)) {
            wfaCertificateSource = android.security.net.config.UserCertificateSource.getInstance();
        } else if ("wfa".equals(attributeValue)) {
            wfaCertificateSource = android.security.net.config.WfaCertificateSource.getInstance();
        } else {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Unknown certificates src. Should be one of system|user|@resourceVal");
        }
        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
        return new android.security.net.config.CertificatesEntryRef(wfaCertificateSource, attributeBooleanValue);
    }

    private java.util.Collection<android.security.net.config.CertificatesEntryRef> parseTrustAnchors(android.content.res.XmlResourceParser xmlResourceParser, boolean z) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        int depth = xmlResourceParser.getDepth();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if (xmlResourceParser.getName().equals("certificates")) {
                arrayList.add(parseCertificatesEntry(xmlResourceParser, z));
            } else {
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        return arrayList;
    }

    private java.util.List<android.util.Pair<android.security.net.config.NetworkSecurityConfig.Builder, java.util.Set<android.security.net.config.Domain>>> parseConfigEntry(android.content.res.XmlResourceParser xmlResourceParser, java.util.Set<java.lang.String> set, android.security.net.config.NetworkSecurityConfig.Builder builder, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.security.net.config.NetworkSecurityConfig.Builder builder2 = new android.security.net.config.NetworkSecurityConfig.Builder();
        builder2.setParent(builder);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        boolean z = false;
        boolean z2 = i == 2;
        int depth = xmlResourceParser.getDepth();
        arrayList.add(new android.util.Pair(builder2, arraySet));
        for (int i2 = 0; i2 < xmlResourceParser.getAttributeCount(); i2++) {
            java.lang.String attributeName = xmlResourceParser.getAttributeName(i2);
            if ("hstsEnforced".equals(attributeName)) {
                builder2.setHstsEnforced(xmlResourceParser.getAttributeBooleanValue(i2, false));
            } else if ("cleartextTrafficPermitted".equals(attributeName)) {
                builder2.setCleartextTrafficPermitted(xmlResourceParser.getAttributeBooleanValue(i2, true));
            }
        }
        boolean z3 = false;
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            java.lang.String name = xmlResourceParser.getName();
            if ("domain".equals(name)) {
                if (i != 1) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "domain element not allowed in " + getConfigString(i));
                }
                arraySet.add(parseDomain(xmlResourceParser, set));
            } else if ("trust-anchors".equals(name)) {
                if (z) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Multiple trust-anchor elements not allowed");
                }
                builder2.addCertificatesEntryRefs(parseTrustAnchors(xmlResourceParser, z2));
                z = true;
            } else if ("pin-set".equals(name)) {
                if (i != 1) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "pin-set element not allowed in " + getConfigString(i));
                }
                if (z3) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Multiple pin-set elements not allowed");
                }
                builder2.setPinSet(parsePinSet(xmlResourceParser));
                z3 = true;
            } else if ("domain-config".equals(name)) {
                if (i != 1) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Nested domain-config not allowed in " + getConfigString(i));
                }
                arrayList.addAll(parseConfigEntry(xmlResourceParser, set, builder2, i));
            } else if ("certificateTransparency".equals(name)) {
                if (i != 0 && i != 1) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "certificateTransparency not allowed in " + getConfigString(i));
                }
                builder2.setCertificateTransparencyVerificationRequired(parseCertificateTransparency(xmlResourceParser));
            } else {
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        if (i == 1 && arraySet.isEmpty()) {
            throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "No domain elements in domain-config");
        }
        return arrayList;
    }

    private void addDebugAnchorsIfNeeded(android.security.net.config.NetworkSecurityConfig.Builder builder, android.security.net.config.NetworkSecurityConfig.Builder builder2) {
        if (builder == null || !builder.hasCertificatesEntryRefs() || !builder2.hasCertificatesEntryRefs()) {
            return;
        }
        builder2.addCertificatesEntryRefs(builder.getCertificatesEntryRefs());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void parseNetworkSecurityConfig(android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.ArrayList<android.util.Pair> arrayList = new java.util.ArrayList();
        com.android.internal.util.XmlUtils.beginDocument(xmlResourceParser, "network-security-config");
        int depth = xmlResourceParser.getDepth();
        android.security.net.config.NetworkSecurityConfig.Builder builder = null;
        android.security.net.config.NetworkSecurityConfig.Builder builder2 = null;
        boolean z = false;
        boolean z2 = false;
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if ("base-config".equals(xmlResourceParser.getName())) {
                if (z) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Only one base-config allowed");
                }
                builder2 = parseConfigEntry(xmlResourceParser, arraySet, null, 0).get(0).first;
                z = true;
            } else if ("domain-config".equals(xmlResourceParser.getName())) {
                arrayList.addAll(parseConfigEntry(xmlResourceParser, arraySet, builder2, 1));
            } else if ("debug-overrides".equals(xmlResourceParser.getName())) {
                if (z2) {
                    throw new android.security.net.config.XmlConfigSource.ParserException(xmlResourceParser, "Only one debug-overrides allowed");
                }
                if (this.mDebugBuild) {
                    builder = parseConfigEntry(xmlResourceParser, null, null, 2).get(0).first;
                } else {
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                }
                z2 = true;
            } else {
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        if (this.mDebugBuild && builder == null) {
            builder = parseDebugOverridesResource();
        }
        android.security.net.config.NetworkSecurityConfig.Builder defaultBuilder = android.security.net.config.NetworkSecurityConfig.getDefaultBuilder(this.mApplicationInfo);
        addDebugAnchorsIfNeeded(builder, defaultBuilder);
        if (builder2 != null) {
            builder2.setParent(defaultBuilder);
            addDebugAnchorsIfNeeded(builder, builder2);
        } else {
            builder2 = defaultBuilder;
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        for (android.util.Pair pair : arrayList) {
            android.security.net.config.NetworkSecurityConfig.Builder builder3 = (android.security.net.config.NetworkSecurityConfig.Builder) pair.first;
            java.util.Set set = (java.util.Set) pair.second;
            if (builder3.getParent() == null) {
                builder3.setParent(builder2);
            }
            addDebugAnchorsIfNeeded(builder, builder3);
            android.security.net.config.NetworkSecurityConfig build = builder3.build();
            java.util.Iterator it = set.iterator();
            while (it.hasNext()) {
                arraySet2.add(new android.util.Pair((android.security.net.config.Domain) it.next(), build));
            }
        }
        this.mDefaultConfig = builder2.build();
        this.mDomainMap = arraySet2;
    }

    private android.security.net.config.NetworkSecurityConfig.Builder parseDebugOverridesResource() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.security.net.config.XmlConfigSource.ParserException {
        android.content.res.Resources resources = this.mContext.getResources();
        int identifier = resources.getIdentifier(resources.getResourceEntryName(this.mResourceId) + "_debug", "xml", resources.getResourcePackageName(this.mResourceId));
        if (identifier == 0) {
            return null;
        }
        android.content.res.XmlResourceParser xml = resources.getXml(identifier);
        try {
            com.android.internal.util.XmlUtils.beginDocument(xml, "network-security-config");
            int depth = xml.getDepth();
            android.security.net.config.NetworkSecurityConfig.Builder builder = null;
            boolean z = false;
            while (com.android.internal.util.XmlUtils.nextElementWithin(xml, depth)) {
                if ("debug-overrides".equals(xml.getName())) {
                    if (z) {
                        throw new android.security.net.config.XmlConfigSource.ParserException(xml, "Only one debug-overrides allowed");
                    }
                    if (this.mDebugBuild) {
                        builder = parseConfigEntry(xml, null, null, 2).get(0).first;
                    } else {
                        com.android.internal.util.XmlUtils.skipCurrentTag(xml);
                    }
                    z = true;
                } else {
                    com.android.internal.util.XmlUtils.skipCurrentTag(xml);
                }
            }
            if (xml != null) {
                xml.close();
            }
            return builder;
        } catch (java.lang.Throwable th) {
            if (xml != null) {
                try {
                    xml.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static class ParserException extends java.lang.Exception {
        public ParserException(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.lang.Throwable th) {
            super(str + " at: " + xmlPullParser.getPositionDescription(), th);
        }

        public ParserException(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) {
            this(xmlPullParser, str, null);
        }
    }
}
