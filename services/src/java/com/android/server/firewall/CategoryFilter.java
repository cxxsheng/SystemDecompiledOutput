package com.android.server.firewall;

/* loaded from: classes.dex */
class CategoryFilter implements com.android.server.firewall.Filter {
    private static final java.lang.String ATTR_NAME = "name";
    public static final com.android.server.firewall.FilterFactory FACTORY = new com.android.server.firewall.FilterFactory("category") { // from class: com.android.server.firewall.CategoryFilter.1
        @Override // com.android.server.firewall.FilterFactory
        public com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue == null) {
                throw new org.xmlpull.v1.XmlPullParserException("Category name must be specified.", xmlPullParser, null);
            }
            return new com.android.server.firewall.CategoryFilter(attributeValue);
        }
    };
    private final java.lang.String mCategoryName;

    private CategoryFilter(java.lang.String str) {
        this.mCategoryName = str;
    }

    @Override // com.android.server.firewall.Filter
    public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
        java.util.Set<java.lang.String> categories = intent.getCategories();
        if (categories == null) {
            return false;
        }
        return categories.contains(this.mCategoryName);
    }
}
