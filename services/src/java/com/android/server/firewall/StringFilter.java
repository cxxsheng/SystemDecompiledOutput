package com.android.server.firewall;

/* loaded from: classes.dex */
abstract class StringFilter implements com.android.server.firewall.Filter {
    private static final java.lang.String ATTR_CONTAINS = "contains";
    private static final java.lang.String ATTR_EQUALS = "equals";
    private static final java.lang.String ATTR_IS_NULL = "isNull";
    private static final java.lang.String ATTR_PATTERN = "pattern";
    private static final java.lang.String ATTR_REGEX = "regex";
    private static final java.lang.String ATTR_STARTS_WITH = "startsWith";
    private final com.android.server.firewall.StringFilter.ValueProvider mValueProvider;
    public static final com.android.server.firewall.StringFilter.ValueProvider COMPONENT = new com.android.server.firewall.StringFilter.ValueProvider("component") { // from class: com.android.server.firewall.StringFilter.1
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            if (componentName != null) {
                return componentName.flattenToString();
            }
            return null;
        }
    };
    public static final com.android.server.firewall.StringFilter.ValueProvider COMPONENT_NAME = new com.android.server.firewall.StringFilter.ValueProvider("component-name") { // from class: com.android.server.firewall.StringFilter.2
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            if (componentName != null) {
                return componentName.getClassName();
            }
            return null;
        }
    };
    public static final com.android.server.firewall.StringFilter.ValueProvider COMPONENT_PACKAGE = new com.android.server.firewall.StringFilter.ValueProvider("component-package") { // from class: com.android.server.firewall.StringFilter.3
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            if (componentName != null) {
                return componentName.getPackageName();
            }
            return null;
        }
    };
    public static final com.android.server.firewall.FilterFactory ACTION = new com.android.server.firewall.StringFilter.ValueProvider(com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_ACTION) { // from class: com.android.server.firewall.StringFilter.4
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            return intent.getAction();
        }
    };
    public static final com.android.server.firewall.StringFilter.ValueProvider DATA = new com.android.server.firewall.StringFilter.ValueProvider("data") { // from class: com.android.server.firewall.StringFilter.5
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            android.net.Uri data = intent.getData();
            if (data != null) {
                return data.toString();
            }
            return null;
        }
    };
    public static final com.android.server.firewall.StringFilter.ValueProvider MIME_TYPE = new com.android.server.firewall.StringFilter.ValueProvider("mime-type") { // from class: com.android.server.firewall.StringFilter.6
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            return str;
        }
    };
    public static final com.android.server.firewall.StringFilter.ValueProvider SCHEME = new com.android.server.firewall.StringFilter.ValueProvider("scheme") { // from class: com.android.server.firewall.StringFilter.7
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            android.net.Uri data = intent.getData();
            if (data != null) {
                return data.getScheme();
            }
            return null;
        }
    };
    public static final com.android.server.firewall.StringFilter.ValueProvider SSP = new com.android.server.firewall.StringFilter.ValueProvider("scheme-specific-part") { // from class: com.android.server.firewall.StringFilter.8
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            android.net.Uri data = intent.getData();
            if (data != null) {
                return data.getSchemeSpecificPart();
            }
            return null;
        }
    };
    public static final com.android.server.firewall.StringFilter.ValueProvider HOST = new com.android.server.firewall.StringFilter.ValueProvider("host") { // from class: com.android.server.firewall.StringFilter.9
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            android.net.Uri data = intent.getData();
            if (data != null) {
                return data.getHost();
            }
            return null;
        }
    };
    public static final com.android.server.firewall.StringFilter.ValueProvider PATH = new com.android.server.firewall.StringFilter.ValueProvider("path") { // from class: com.android.server.firewall.StringFilter.10
        @Override // com.android.server.firewall.StringFilter.ValueProvider
        public java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str) {
            android.net.Uri data = intent.getData();
            if (data != null) {
                return data.getPath();
            }
            return null;
        }
    };

    protected abstract boolean matchesValue(java.lang.String str);

    private StringFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider) {
        this.mValueProvider = valueProvider;
    }

    public static com.android.server.firewall.StringFilter readFromXml(com.android.server.firewall.StringFilter.ValueProvider valueProvider, org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.firewall.StringFilter stringFilter = null;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            com.android.server.firewall.StringFilter filter = getFilter(valueProvider, xmlPullParser, i);
            if (filter != null) {
                if (stringFilter != null) {
                    throw new org.xmlpull.v1.XmlPullParserException("Multiple string filter attributes found");
                }
                stringFilter = filter;
            }
        }
        if (stringFilter == null) {
            return new com.android.server.firewall.StringFilter.IsNullFilter(valueProvider, false);
        }
        return stringFilter;
    }

    private static com.android.server.firewall.StringFilter getFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider, org.xmlpull.v1.XmlPullParser xmlPullParser, int i) {
        java.lang.String attributeName = xmlPullParser.getAttributeName(i);
        switch (attributeName.charAt(0)) {
            case 'c':
                if (attributeName.equals(ATTR_CONTAINS)) {
                    break;
                }
                break;
            case 'e':
                if (attributeName.equals(ATTR_EQUALS)) {
                    break;
                }
                break;
            case 'i':
                if (attributeName.equals(ATTR_IS_NULL)) {
                    break;
                }
                break;
            case 'p':
                if (attributeName.equals(ATTR_PATTERN)) {
                    break;
                }
                break;
            case 'r':
                if (attributeName.equals(ATTR_REGEX)) {
                    break;
                }
                break;
            case 's':
                if (attributeName.equals(ATTR_STARTS_WITH)) {
                    break;
                }
                break;
        }
        return null;
    }

    @Override // com.android.server.firewall.Filter
    public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
        return matchesValue(this.mValueProvider.getValue(componentName, intent, str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class ValueProvider extends com.android.server.firewall.FilterFactory {
        public abstract java.lang.String getValue(android.content.ComponentName componentName, android.content.Intent intent, java.lang.String str);

        protected ValueProvider(java.lang.String str) {
            super(str);
        }

        @Override // com.android.server.firewall.FilterFactory
        public com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            return com.android.server.firewall.StringFilter.readFromXml(this, xmlPullParser);
        }
    }

    private static class EqualsFilter extends com.android.server.firewall.StringFilter {
        private final java.lang.String mFilterValue;

        public EqualsFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider, java.lang.String str) {
            super(valueProvider);
            this.mFilterValue = str;
        }

        @Override // com.android.server.firewall.StringFilter
        public boolean matchesValue(java.lang.String str) {
            return str != null && str.equals(this.mFilterValue);
        }
    }

    private static class ContainsFilter extends com.android.server.firewall.StringFilter {
        private final java.lang.String mFilterValue;

        public ContainsFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider, java.lang.String str) {
            super(valueProvider);
            this.mFilterValue = str;
        }

        @Override // com.android.server.firewall.StringFilter
        public boolean matchesValue(java.lang.String str) {
            return str != null && str.contains(this.mFilterValue);
        }
    }

    private static class StartsWithFilter extends com.android.server.firewall.StringFilter {
        private final java.lang.String mFilterValue;

        public StartsWithFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider, java.lang.String str) {
            super(valueProvider);
            this.mFilterValue = str;
        }

        @Override // com.android.server.firewall.StringFilter
        public boolean matchesValue(java.lang.String str) {
            return str != null && str.startsWith(this.mFilterValue);
        }
    }

    private static class PatternStringFilter extends com.android.server.firewall.StringFilter {
        private final android.os.PatternMatcher mPattern;

        public PatternStringFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider, java.lang.String str) {
            super(valueProvider);
            this.mPattern = new android.os.PatternMatcher(str, 2);
        }

        @Override // com.android.server.firewall.StringFilter
        public boolean matchesValue(java.lang.String str) {
            return str != null && this.mPattern.match(str);
        }
    }

    private static class RegexFilter extends com.android.server.firewall.StringFilter {
        private final java.util.regex.Pattern mPattern;

        public RegexFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider, java.lang.String str) {
            super(valueProvider);
            this.mPattern = java.util.regex.Pattern.compile(str);
        }

        @Override // com.android.server.firewall.StringFilter
        public boolean matchesValue(java.lang.String str) {
            return str != null && this.mPattern.matcher(str).matches();
        }
    }

    private static class IsNullFilter extends com.android.server.firewall.StringFilter {
        private final boolean mIsNull;

        public IsNullFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider, java.lang.String str) {
            super(valueProvider);
            this.mIsNull = java.lang.Boolean.parseBoolean(str);
        }

        public IsNullFilter(com.android.server.firewall.StringFilter.ValueProvider valueProvider, boolean z) {
            super(valueProvider);
            this.mIsNull = z;
        }

        @Override // com.android.server.firewall.StringFilter
        public boolean matchesValue(java.lang.String str) {
            return (str == null) == this.mIsNull;
        }
    }
}
