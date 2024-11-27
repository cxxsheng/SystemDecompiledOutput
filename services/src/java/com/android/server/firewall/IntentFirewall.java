package com.android.server.firewall;

/* loaded from: classes.dex */
public class IntentFirewall {
    private static final int LOG_PACKAGES_MAX_LENGTH = 150;
    private static final int LOG_PACKAGES_SUFFICIENT_LENGTH = 125;
    private static final java.io.File RULES_DIR = new java.io.File(android.os.Environment.getDataSystemDirectory(), "ifw");
    static final java.lang.String TAG = "IntentFirewall";
    private static final java.lang.String TAG_ACTIVITY = "activity";
    private static final java.lang.String TAG_BROADCAST = "broadcast";
    private static final java.lang.String TAG_RULES = "rules";
    private static final java.lang.String TAG_SERVICE = "service";
    private static final int TYPE_ACTIVITY = 0;
    private static final int TYPE_BROADCAST = 1;
    private static final int TYPE_SERVICE = 2;
    private static final java.util.HashMap<java.lang.String, com.android.server.firewall.FilterFactory> factoryMap;
    private com.android.server.firewall.IntentFirewall.FirewallIntentResolver mActivityResolver;
    private final com.android.server.firewall.IntentFirewall.AMSInterface mAms;
    private com.android.server.firewall.IntentFirewall.FirewallIntentResolver mBroadcastResolver;
    final com.android.server.firewall.IntentFirewall.FirewallHandler mHandler;
    private final com.android.server.firewall.IntentFirewall.RuleObserver mObserver;

    @android.annotation.NonNull
    private android.content.pm.PackageManagerInternal mPackageManager;
    private com.android.server.firewall.IntentFirewall.FirewallIntentResolver mServiceResolver;

    public interface AMSInterface {
        int checkComponentPermission(java.lang.String str, int i, int i2, int i3, boolean z);

        java.lang.Object getAMSLock();
    }

    static {
        com.android.server.firewall.FilterFactory[] filterFactoryArr = {com.android.server.firewall.AndFilter.FACTORY, com.android.server.firewall.OrFilter.FACTORY, com.android.server.firewall.NotFilter.FACTORY, com.android.server.firewall.StringFilter.ACTION, com.android.server.firewall.StringFilter.COMPONENT, com.android.server.firewall.StringFilter.COMPONENT_NAME, com.android.server.firewall.StringFilter.COMPONENT_PACKAGE, com.android.server.firewall.StringFilter.DATA, com.android.server.firewall.StringFilter.HOST, com.android.server.firewall.StringFilter.MIME_TYPE, com.android.server.firewall.StringFilter.SCHEME, com.android.server.firewall.StringFilter.PATH, com.android.server.firewall.StringFilter.SSP, com.android.server.firewall.CategoryFilter.FACTORY, com.android.server.firewall.SenderFilter.FACTORY, com.android.server.firewall.SenderPackageFilter.FACTORY, com.android.server.firewall.SenderPermissionFilter.FACTORY, com.android.server.firewall.PortFilter.FACTORY};
        factoryMap = new java.util.HashMap<>(24);
        for (int i = 0; i < 18; i++) {
            com.android.server.firewall.FilterFactory filterFactory = filterFactoryArr[i];
            factoryMap.put(filterFactory.getTagName(), filterFactory);
        }
    }

    public IntentFirewall(com.android.server.firewall.IntentFirewall.AMSInterface aMSInterface, android.os.Handler handler) {
        this.mActivityResolver = new com.android.server.firewall.IntentFirewall.FirewallIntentResolver();
        this.mBroadcastResolver = new com.android.server.firewall.IntentFirewall.FirewallIntentResolver();
        this.mServiceResolver = new com.android.server.firewall.IntentFirewall.FirewallIntentResolver();
        this.mAms = aMSInterface;
        this.mHandler = new com.android.server.firewall.IntentFirewall.FirewallHandler(handler.getLooper());
        java.io.File rulesDir = getRulesDir();
        rulesDir.mkdirs();
        readRulesDir(rulesDir);
        this.mObserver = new com.android.server.firewall.IntentFirewall.RuleObserver(rulesDir);
        this.mObserver.startWatching();
    }

    android.content.pm.PackageManagerInternal getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        }
        return this.mPackageManager;
    }

    public boolean checkStartActivity(android.content.Intent intent, int i, int i2, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        return checkIntent(this.mActivityResolver, intent.getComponent(), 0, intent, i, i2, str, applicationInfo.uid);
    }

    public boolean checkService(android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        return checkIntent(this.mServiceResolver, componentName, 2, intent, i, i2, str, applicationInfo.uid);
    }

    public boolean checkBroadcast(android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
        return checkIntent(this.mBroadcastResolver, intent.getComponent(), 1, intent, i, i2, str, i3);
    }

    public boolean checkIntent(com.android.server.firewall.IntentFirewall.FirewallIntentResolver firewallIntentResolver, android.content.ComponentName componentName, int i, android.content.Intent intent, int i2, int i3, java.lang.String str, int i4) {
        java.util.List<com.android.server.firewall.IntentFirewall.Rule> queryIntent = firewallIntentResolver.queryIntent(getPackageManager().snapshot(), intent, str, false, 0);
        if (queryIntent == null) {
            queryIntent = new java.util.ArrayList<>();
        }
        firewallIntentResolver.queryByComponent(componentName, queryIntent);
        boolean z = false;
        boolean z2 = false;
        for (int i5 = 0; i5 < queryIntent.size(); i5++) {
            com.android.server.firewall.IntentFirewall.Rule rule = queryIntent.get(i5);
            if (rule.matches(this, componentName, intent, i2, i3, str, i4)) {
                z |= rule.getBlock();
                z2 |= rule.getLog();
                if (z && z2) {
                    break;
                }
            }
        }
        if (z2) {
            logIntent(i, intent, i2, str);
        }
        return !z;
    }

    private static void logIntent(int i, android.content.Intent intent, int i2, java.lang.String str) {
        java.lang.String str2;
        java.lang.String str3;
        int i3;
        android.content.ComponentName component = intent.getComponent();
        java.lang.String str4 = null;
        if (component == null) {
            str2 = null;
        } else {
            str2 = component.flattenToShortString();
        }
        android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
        int i4 = 0;
        if (packageManager == null) {
            str3 = null;
            i3 = 0;
        } else {
            try {
                java.lang.String[] packagesForUid = packageManager.getPackagesForUid(i2);
                if (packagesForUid != null) {
                    i4 = packagesForUid.length;
                    str4 = joinPackages(packagesForUid);
                }
                str3 = str4;
                i3 = i4;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception while retrieving packages", e);
                str3 = null;
                i3 = i4;
            }
        }
        com.android.server.EventLogTags.writeIfwIntentMatched(i, str2, i2, i3, str3, intent.getAction(), str, intent.getDataString(), intent.getFlags());
    }

    private static java.lang.String joinPackages(java.lang.String[] strArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        boolean z = true;
        for (java.lang.String str : strArr) {
            if (sb.length() + str.length() + 1 < 150) {
                if (!z) {
                    sb.append(',');
                } else {
                    z = false;
                }
                sb.append(str);
            } else if (sb.length() >= 125) {
                return sb.toString();
            }
        }
        if (sb.length() == 0 && strArr.length > 0) {
            java.lang.String str2 = strArr[0];
            return str2.substring((str2.length() - 150) + 1) + '-';
        }
        return null;
    }

    public static java.io.File getRulesDir() {
        return RULES_DIR;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readRulesDir(java.io.File file) {
        com.android.server.firewall.IntentFirewall.FirewallIntentResolver[] firewallIntentResolverArr = new com.android.server.firewall.IntentFirewall.FirewallIntentResolver[3];
        for (int i = 0; i < 3; i++) {
            firewallIntentResolverArr[i] = new com.android.server.firewall.IntentFirewall.FirewallIntentResolver();
        }
        java.io.File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (java.io.File file2 : listFiles) {
                if (file2.getName().endsWith(".xml")) {
                    readRules(file2, firewallIntentResolverArr);
                }
            }
        }
        android.util.Slog.i(TAG, "Read new rules (A:" + firewallIntentResolverArr[0].filterSet().size() + " B:" + firewallIntentResolverArr[1].filterSet().size() + " S:" + firewallIntentResolverArr[2].filterSet().size() + ")");
        synchronized (this.mAms.getAMSLock()) {
            this.mActivityResolver = firewallIntentResolverArr[0];
            this.mBroadcastResolver = firewallIntentResolverArr[1];
            this.mServiceResolver = firewallIntentResolverArr[2];
        }
    }

    private void readRules(java.io.File file, com.android.server.firewall.IntentFirewall.FirewallIntentResolver[] firewallIntentResolverArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(3);
        for (int i = 0; i < 3; i++) {
            arrayList.add(new java.util.ArrayList());
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
            try {
                try {
                    try {
                        org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                        newPullParser.setInput(fileInputStream, null);
                        com.android.internal.util.XmlUtils.beginDocument(newPullParser, TAG_RULES);
                        int depth = newPullParser.getDepth();
                        while (com.android.internal.util.XmlUtils.nextElementWithin(newPullParser, depth)) {
                            java.lang.String name = newPullParser.getName();
                            int i2 = name.equals("activity") ? 0 : name.equals("broadcast") ? 1 : name.equals("service") ? 2 : -1;
                            if (i2 != -1) {
                                com.android.server.firewall.IntentFirewall.Rule rule = new com.android.server.firewall.IntentFirewall.Rule();
                                java.util.List list = (java.util.List) arrayList.get(i2);
                                try {
                                    rule.readFromXml(newPullParser);
                                    list.add(rule);
                                } catch (org.xmlpull.v1.XmlPullParserException e) {
                                    android.util.Slog.e(TAG, "Error reading an intent firewall rule from " + file, e);
                                }
                            }
                        }
                        try {
                            fileInputStream.close();
                        } catch (java.io.IOException e2) {
                            android.util.Slog.e(TAG, "Error while closing " + file, e2);
                        }
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            java.util.List list2 = (java.util.List) arrayList.get(i3);
                            com.android.server.firewall.IntentFirewall.FirewallIntentResolver firewallIntentResolver = firewallIntentResolverArr[i3];
                            for (int i4 = 0; i4 < list2.size(); i4++) {
                                com.android.server.firewall.IntentFirewall.Rule rule2 = (com.android.server.firewall.IntentFirewall.Rule) list2.get(i4);
                                for (int i5 = 0; i5 < rule2.getIntentFilterCount(); i5++) {
                                    firewallIntentResolver.addFilter(null, rule2.getIntentFilter(i5));
                                }
                                for (int i6 = 0; i6 < rule2.getComponentFilterCount(); i6++) {
                                    firewallIntentResolver.addComponentFilter(rule2.getComponentFilter(i6), rule2);
                                }
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (java.io.IOException e3) {
                            android.util.Slog.e(TAG, "Error while closing " + file, e3);
                        }
                        throw th;
                    }
                } catch (java.io.IOException e4) {
                    android.util.Slog.e(TAG, "Error reading intent firewall rules from " + file, e4);
                    try {
                        fileInputStream.close();
                    } catch (java.io.IOException e5) {
                        android.util.Slog.e(TAG, "Error while closing " + file, e5);
                    }
                }
            } catch (org.xmlpull.v1.XmlPullParserException e6) {
                android.util.Slog.e(TAG, "Error reading intent firewall rules from " + file, e6);
                try {
                    fileInputStream.close();
                } catch (java.io.IOException e7) {
                    android.util.Slog.e(TAG, "Error while closing " + file, e7);
                }
            }
        } catch (java.io.FileNotFoundException e8) {
        }
    }

    static com.android.server.firewall.Filter parseFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String name = xmlPullParser.getName();
        com.android.server.firewall.FilterFactory filterFactory = factoryMap.get(name);
        if (filterFactory == null) {
            throw new org.xmlpull.v1.XmlPullParserException("Unknown element in filter list: " + name);
        }
        return filterFactory.newFilter(xmlPullParser);
    }

    private static class Rule extends com.android.server.firewall.AndFilter {
        private static final java.lang.String ATTR_BLOCK = "block";
        private static final java.lang.String ATTR_LOG = "log";
        private static final java.lang.String ATTR_NAME = "name";
        private static final java.lang.String TAG_COMPONENT_FILTER = "component-filter";
        private static final java.lang.String TAG_INTENT_FILTER = "intent-filter";
        private boolean block;
        private boolean log;
        private final java.util.ArrayList<android.content.ComponentName> mComponentFilters;
        private final java.util.ArrayList<com.android.server.firewall.IntentFirewall.FirewallIntentFilter> mIntentFilters;

        private Rule() {
            this.mIntentFilters = new java.util.ArrayList<>(1);
            this.mComponentFilters = new java.util.ArrayList<>(0);
        }

        @Override // com.android.server.firewall.FilterList
        public com.android.server.firewall.IntentFirewall.Rule readFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            this.block = java.lang.Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, ATTR_BLOCK));
            this.log = java.lang.Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, ATTR_LOG));
            super.readFromXml(xmlPullParser);
            return this;
        }

        @Override // com.android.server.firewall.FilterList
        protected void readChild(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.lang.String name = xmlPullParser.getName();
            if (name.equals(TAG_INTENT_FILTER)) {
                com.android.server.firewall.IntentFirewall.FirewallIntentFilter firewallIntentFilter = new com.android.server.firewall.IntentFirewall.FirewallIntentFilter(this);
                firewallIntentFilter.readFromXml(xmlPullParser);
                this.mIntentFilters.add(firewallIntentFilter);
            } else {
                if (name.equals(TAG_COMPONENT_FILTER)) {
                    java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("Component name must be specified.", xmlPullParser, null);
                    }
                    android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(attributeValue);
                    if (unflattenFromString == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid component name: " + attributeValue);
                    }
                    this.mComponentFilters.add(unflattenFromString);
                    return;
                }
                super.readChild(xmlPullParser);
            }
        }

        public int getIntentFilterCount() {
            return this.mIntentFilters.size();
        }

        public com.android.server.firewall.IntentFirewall.FirewallIntentFilter getIntentFilter(int i) {
            return this.mIntentFilters.get(i);
        }

        public int getComponentFilterCount() {
            return this.mComponentFilters.size();
        }

        public android.content.ComponentName getComponentFilter(int i) {
            return this.mComponentFilters.get(i);
        }

        public boolean getBlock() {
            return this.block;
        }

        public boolean getLog() {
            return this.log;
        }
    }

    private static class FirewallIntentFilter extends android.content.IntentFilter {
        private final com.android.server.firewall.IntentFirewall.Rule rule;

        public FirewallIntentFilter(com.android.server.firewall.IntentFirewall.Rule rule) {
            this.rule = rule;
        }
    }

    private static class FirewallIntentResolver extends com.android.server.IntentResolver<com.android.server.firewall.IntentFirewall.FirewallIntentFilter, com.android.server.firewall.IntentFirewall.Rule> {
        private final android.util.ArrayMap<android.content.ComponentName, com.android.server.firewall.IntentFirewall.Rule[]> mRulesByComponent;

        private FirewallIntentResolver() {
            this.mRulesByComponent = new android.util.ArrayMap<>(0);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public boolean allowFilterResult(com.android.server.firewall.IntentFirewall.FirewallIntentFilter firewallIntentFilter, java.util.List<com.android.server.firewall.IntentFirewall.Rule> list) {
            return !list.contains(firewallIntentFilter.rule);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public boolean isPackageForFilter(java.lang.String str, com.android.server.firewall.IntentFirewall.FirewallIntentFilter firewallIntentFilter) {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.IntentResolver
        public com.android.server.firewall.IntentFirewall.FirewallIntentFilter[] newArray(int i) {
            return new com.android.server.firewall.IntentFirewall.FirewallIntentFilter[i];
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public com.android.server.firewall.IntentFirewall.Rule newResult(@android.annotation.NonNull com.android.server.pm.Computer computer, com.android.server.firewall.IntentFirewall.FirewallIntentFilter firewallIntentFilter, int i, int i2, long j) {
            return firewallIntentFilter.rule;
        }

        @Override // com.android.server.IntentResolver
        protected void sortResults(java.util.List<com.android.server.firewall.IntentFirewall.Rule> list) {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.IntentResolver
        public android.content.IntentFilter getIntentFilter(@android.annotation.NonNull com.android.server.firewall.IntentFirewall.FirewallIntentFilter firewallIntentFilter) {
            return firewallIntentFilter;
        }

        public void queryByComponent(android.content.ComponentName componentName, java.util.List<com.android.server.firewall.IntentFirewall.Rule> list) {
            com.android.server.firewall.IntentFirewall.Rule[] ruleArr = this.mRulesByComponent.get(componentName);
            if (ruleArr != null) {
                list.addAll(java.util.Arrays.asList(ruleArr));
            }
        }

        public void addComponentFilter(android.content.ComponentName componentName, com.android.server.firewall.IntentFirewall.Rule rule) {
            this.mRulesByComponent.put(componentName, (com.android.server.firewall.IntentFirewall.Rule[]) com.android.internal.util.ArrayUtils.appendElement(com.android.server.firewall.IntentFirewall.Rule.class, this.mRulesByComponent.get(componentName), rule));
        }
    }

    private final class FirewallHandler extends android.os.Handler {
        public FirewallHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.server.firewall.IntentFirewall.this.readRulesDir(com.android.server.firewall.IntentFirewall.getRulesDir());
        }
    }

    private class RuleObserver extends android.os.FileObserver {
        private static final int MONITORED_EVENTS = 968;

        public RuleObserver(java.io.File file) {
            super(file.getAbsolutePath(), MONITORED_EVENTS);
        }

        @Override // android.os.FileObserver
        public void onEvent(int i, java.lang.String str) {
            if (str != null && str.endsWith(".xml")) {
                com.android.server.firewall.IntentFirewall.this.mHandler.removeMessages(0);
                com.android.server.firewall.IntentFirewall.this.mHandler.sendEmptyMessageDelayed(0, 250L);
            }
        }
    }

    boolean checkComponentPermission(java.lang.String str, int i, int i2, int i3, boolean z) {
        return this.mAms.checkComponentPermission(str, i, i2, i3, z) == 0;
    }

    boolean signaturesMatch(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getPackageManager().checkUidSignaturesForAllUsers(i, i2) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
