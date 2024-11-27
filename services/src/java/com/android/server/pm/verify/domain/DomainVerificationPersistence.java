package com.android.server.pm.verify.domain;

/* loaded from: classes2.dex */
public class DomainVerificationPersistence {
    public static final java.lang.String ATTR_ACTION = "action";
    public static final java.lang.String ATTR_ALLOW_LINK_HANDLING = "allowLinkHandling";
    public static final java.lang.String ATTR_FILTER = "filter";
    private static final java.lang.String ATTR_HAS_AUTO_VERIFY_DOMAINS = "hasAutoVerifyDomains";
    private static final java.lang.String ATTR_ID = "id";
    public static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_PACKAGE_NAME = "packageName";
    public static final java.lang.String ATTR_PATTERN_TYPE = "pattern-type";
    private static final java.lang.String ATTR_SIGNATURE = "signature";
    public static final java.lang.String ATTR_STATE = "state";
    public static final java.lang.String ATTR_URI_PART = "uri-part";
    public static final java.lang.String ATTR_USER_ID = "userId";
    private static final java.lang.String TAG = "DomainVerificationPersistence";
    public static final java.lang.String TAG_ACTIVE = "active";
    public static final java.lang.String TAG_DOMAIN = "domain";
    public static final java.lang.String TAG_DOMAIN_VERIFICATIONS = "domain-verifications";
    public static final java.lang.String TAG_ENABLED_HOSTS = "enabled-hosts";
    public static final java.lang.String TAG_HOST = "host";
    public static final java.lang.String TAG_PACKAGE_STATE = "package-state";
    public static final java.lang.String TAG_RESTORED = "restored";
    private static final java.lang.String TAG_STATE = "state";
    public static final java.lang.String TAG_URI_RELATIVE_FILTER = "uri-relative-filter";
    public static final java.lang.String TAG_URI_RELATIVE_FILTER_GROUP = "uri-relative-filter-group";
    public static final java.lang.String TAG_URI_RELATIVE_FILTER_GROUPS = "uri-relative-filter-groups";
    public static final java.lang.String TAG_USER_STATE = "user-state";
    private static final java.lang.String TAG_USER_STATES = "user-states";

    public static void writeToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationStateMap<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> domainVerificationStateMap, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> arrayMap, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> arrayMap2, int i, @android.annotation.Nullable java.util.function.Function<java.lang.String, java.lang.String> function) throws java.io.IOException {
        com.android.server.pm.SettingsXml.Serializer serializer = com.android.server.pm.SettingsXml.serializer(typedXmlSerializer);
        try {
            com.android.server.pm.SettingsXml.WriteSection startSection = serializer.startSection(TAG_DOMAIN_VERIFICATIONS);
            try {
                android.util.ArraySet arraySet = new android.util.ArraySet();
                int size = domainVerificationStateMap.size();
                for (int i2 = 0; i2 < size; i2++) {
                    arraySet.add(domainVerificationStateMap.valueAt(i2));
                }
                int size2 = arrayMap.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arraySet.add(arrayMap.valueAt(i3));
                }
                com.android.server.pm.SettingsXml.WriteSection startSection2 = serializer.startSection(TAG_ACTIVE);
                try {
                    writePackageStates(startSection2, arraySet, i, function);
                    if (startSection2 != null) {
                        startSection2.close();
                    }
                    startSection2 = serializer.startSection(TAG_RESTORED);
                    try {
                        writePackageStates(startSection2, arrayMap2.values(), i, function);
                        if (startSection2 != null) {
                            startSection2.close();
                        }
                        if (startSection != null) {
                            startSection.close();
                        }
                        serializer.close();
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (java.lang.Throwable th) {
            if (serializer != null) {
                try {
                    serializer.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static void writePackageStates(@android.annotation.NonNull com.android.server.pm.SettingsXml.WriteSection writeSection, @android.annotation.NonNull java.util.Collection<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> collection, int i, @android.annotation.Nullable java.util.function.Function<java.lang.String, java.lang.String> function) throws java.io.IOException {
        if (collection.isEmpty()) {
            return;
        }
        java.util.Iterator<com.android.server.pm.verify.domain.models.DomainVerificationPkgState> it = collection.iterator();
        while (it.hasNext()) {
            writePkgStateToXml(writeSection, it.next(), i, function);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @android.annotation.NonNull
    public static com.android.server.pm.verify.domain.DomainVerificationPersistence.ReadResult readFromXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        com.android.server.pm.SettingsXml.ChildSection children = com.android.server.pm.SettingsXml.parser(typedXmlPullParser).children();
        while (children.moveToNext()) {
            java.lang.String name = children.getName();
            switch (name.hashCode()) {
                case -1422950650:
                    if (name.equals(TAG_ACTIVE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -336625770:
                    if (name.equals(TAG_RESTORED)) {
                        c = 1;
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
                    readPackageStates(children, arrayMap);
                    break;
                case 1:
                    readPackageStates(children, arrayMap2);
                    break;
            }
        }
        return new com.android.server.pm.verify.domain.DomainVerificationPersistence.ReadResult(arrayMap, arrayMap2);
    }

    private static void readPackageStates(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> arrayMap) {
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext(TAG_PACKAGE_STATE)) {
            com.android.server.pm.verify.domain.models.DomainVerificationPkgState createPkgStateFromXml = createPkgStateFromXml(children);
            if (createPkgStateFromXml != null) {
                arrayMap.put(createPkgStateFromXml.getPackageName(), createPkgStateFromXml);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @android.annotation.Nullable
    private static com.android.server.pm.verify.domain.models.DomainVerificationPkgState createPkgStateFromXml(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection) {
        char c;
        java.lang.String string = readSection.getString("packageName");
        java.lang.String string2 = readSection.getString(ATTR_ID);
        boolean z = readSection.getBoolean(ATTR_HAS_AUTO_VERIFY_DOMAINS);
        java.lang.String string3 = readSection.getString(ATTR_SIGNATURE);
        if (android.text.TextUtils.isEmpty(string) || android.text.TextUtils.isEmpty(string2)) {
            return null;
        }
        java.util.UUID fromString = java.util.UUID.fromString(string2);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext()) {
            java.lang.String name = children.getName();
            switch (name.hashCode()) {
                case -1576041916:
                    if (name.equals("user-states")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 109757585:
                    if (name.equals("state")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1632406025:
                    if (name.equals(TAG_URI_RELATIVE_FILTER_GROUPS)) {
                        c = 2;
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
                    readDomainStates(children, arrayMap);
                    break;
                case 1:
                    readUserStates(children, sparseArray);
                    break;
                case 2:
                    readUriRelativeFilterGroups(children, arrayMap2);
                    break;
            }
        }
        return new com.android.server.pm.verify.domain.models.DomainVerificationPkgState(string, fromString, z, arrayMap, sparseArray, string3, arrayMap2);
    }

    private static void readUriRelativeFilterGroups(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> arrayMap) {
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext(TAG_DOMAIN)) {
            arrayMap.put(children.getString("name"), createUriRelativeFilterGroupsFromXml(children));
        }
    }

    private static java.util.ArrayList<android.content.UriRelativeFilterGroup> createUriRelativeFilterGroupsFromXml(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection) {
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        java.util.ArrayList<android.content.UriRelativeFilterGroup> arrayList = new java.util.ArrayList<>();
        while (children.moveToNext(TAG_URI_RELATIVE_FILTER_GROUP)) {
            android.content.UriRelativeFilterGroup uriRelativeFilterGroup = new android.content.UriRelativeFilterGroup(readSection.getInt(ATTR_ACTION));
            readUriRelativeFiltersFromXml(children, uriRelativeFilterGroup);
            arrayList.add(uriRelativeFilterGroup);
        }
        return arrayList;
    }

    private static void readUriRelativeFiltersFromXml(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection, android.content.UriRelativeFilterGroup uriRelativeFilterGroup) {
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext(TAG_URI_RELATIVE_FILTER)) {
            java.lang.String string = children.getString(ATTR_FILTER);
            if (string != null) {
                uriRelativeFilterGroup.addUriRelativeFilter(new android.content.UriRelativeFilter(children.getInt(ATTR_URI_PART), children.getInt(ATTR_PATTERN_TYPE), string));
            }
        }
    }

    private static void readUserStates(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection, @android.annotation.NonNull android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> sparseArray) {
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext("user-state")) {
            com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState createUserStateFromXml = createUserStateFromXml(children);
            if (createUserStateFromXml != null) {
                sparseArray.put(createUserStateFromXml.getUserId(), createUserStateFromXml);
            }
        }
    }

    private static void readDomainStates(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) {
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext(TAG_DOMAIN)) {
            arrayMap.put(children.getString("name"), java.lang.Integer.valueOf(children.getInt("state", 0)));
        }
    }

    private static void writePkgStateToXml(@android.annotation.NonNull com.android.server.pm.SettingsXml.WriteSection writeSection, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationPkgState domainVerificationPkgState, int i, @android.annotation.Nullable java.util.function.Function<java.lang.String, java.lang.String> function) throws java.io.IOException {
        java.lang.String packageName = domainVerificationPkgState.getPackageName();
        java.lang.String apply = function == null ? null : function.apply(packageName);
        if (apply == null) {
            apply = domainVerificationPkgState.getBackupSignatureHash();
        }
        com.android.server.pm.SettingsXml.WriteSection attribute = writeSection.startSection(TAG_PACKAGE_STATE).attribute("packageName", packageName).attribute(ATTR_ID, domainVerificationPkgState.getId().toString()).attribute(ATTR_HAS_AUTO_VERIFY_DOMAINS, domainVerificationPkgState.isHasAutoVerifyDomains()).attribute(ATTR_SIGNATURE, apply);
        try {
            writeStateMap(writeSection, domainVerificationPkgState.getStateMap());
            writeUserStates(writeSection, i, domainVerificationPkgState.getUserStates());
            writeUriRelativeFilterGroupMap(writeSection, domainVerificationPkgState.getUriRelativeFilterGroupMap());
            if (attribute != null) {
                attribute.close();
            }
        } catch (java.lang.Throwable th) {
            if (attribute != null) {
                try {
                    attribute.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static void writeUserStates(@android.annotation.NonNull com.android.server.pm.SettingsXml.WriteSection writeSection, int i, @android.annotation.NonNull android.util.SparseArray<com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState> sparseArray) throws java.io.IOException {
        int size = sparseArray.size();
        if (size == 0) {
            return;
        }
        com.android.server.pm.SettingsXml.WriteSection startSection = writeSection.startSection("user-states");
        try {
            if (i == -1) {
                for (int i2 = 0; i2 < size; i2++) {
                    writeUserStateToXml(startSection, sparseArray.valueAt(i2));
                }
            } else {
                com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState = sparseArray.get(i);
                if (domainVerificationInternalUserState != null) {
                    writeUserStateToXml(startSection, domainVerificationInternalUserState);
                }
            }
            if (startSection != null) {
                startSection.close();
            }
        } catch (java.lang.Throwable th) {
            if (startSection != null) {
                try {
                    startSection.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static void writeStateMap(@android.annotation.NonNull com.android.server.pm.SettingsXml.WriteSection writeSection, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.lang.Integer> arrayMap) throws java.io.IOException {
        if (arrayMap.isEmpty()) {
            return;
        }
        com.android.server.pm.SettingsXml.WriteSection startSection = writeSection.startSection("state");
        try {
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                startSection.startSection(TAG_DOMAIN).attribute("name", arrayMap.keyAt(i)).attribute("state", arrayMap.valueAt(i).intValue()).finish();
            }
            if (startSection != null) {
                startSection.close();
            }
        } catch (java.lang.Throwable th) {
            if (startSection != null) {
                try {
                    startSection.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @android.annotation.Nullable
    private static com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState createUserStateFromXml(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection) {
        int i = readSection.getInt("userId");
        if (i == -1) {
            return null;
        }
        boolean z = readSection.getBoolean(ATTR_ALLOW_LINK_HANDLING, false);
        android.util.ArraySet arraySet = new android.util.ArraySet();
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext(TAG_ENABLED_HOSTS)) {
            readEnabledHosts(children, arraySet);
        }
        return new com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState(i, arraySet, z);
    }

    private static void readEnabledHosts(@android.annotation.NonNull com.android.server.pm.SettingsXml.ReadSection readSection, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
        com.android.server.pm.SettingsXml.ChildSection children = readSection.children();
        while (children.moveToNext("host")) {
            java.lang.String string = children.getString("name");
            if (!android.text.TextUtils.isEmpty(string)) {
                arraySet.add(string);
            }
        }
    }

    private static void writeUserStateToXml(@android.annotation.NonNull com.android.server.pm.SettingsXml.WriteSection writeSection, @android.annotation.NonNull com.android.server.pm.verify.domain.models.DomainVerificationInternalUserState domainVerificationInternalUserState) throws java.io.IOException {
        com.android.server.pm.SettingsXml.WriteSection attribute = writeSection.startSection("user-state").attribute("userId", domainVerificationInternalUserState.getUserId()).attribute(ATTR_ALLOW_LINK_HANDLING, domainVerificationInternalUserState.isLinkHandlingAllowed());
        try {
            android.util.ArraySet<java.lang.String> enabledHosts = domainVerificationInternalUserState.getEnabledHosts();
            if (!enabledHosts.isEmpty()) {
                com.android.server.pm.SettingsXml.WriteSection startSection = attribute.startSection(TAG_ENABLED_HOSTS);
                try {
                    int size = enabledHosts.size();
                    for (int i = 0; i < size; i++) {
                        startSection.startSection("host").attribute("name", enabledHosts.valueAt(i)).finish();
                    }
                    if (startSection != null) {
                        startSection.close();
                    }
                } finally {
                }
            }
            if (attribute != null) {
                attribute.close();
            }
        } catch (java.lang.Throwable th) {
            if (attribute != null) {
                try {
                    attribute.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static void writeUriRelativeFilterGroupMap(@android.annotation.NonNull com.android.server.pm.SettingsXml.WriteSection writeSection, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, java.util.List<android.content.UriRelativeFilterGroup>> arrayMap) throws java.io.IOException {
        if (arrayMap.isEmpty()) {
            return;
        }
        com.android.server.pm.SettingsXml.WriteSection startSection = writeSection.startSection(TAG_URI_RELATIVE_FILTER_GROUPS);
        for (int i = 0; i < arrayMap.size(); i++) {
            try {
                writeUriRelativeFilterGroups(startSection, arrayMap.keyAt(i), arrayMap.valueAt(i));
            } catch (java.lang.Throwable th) {
                if (startSection != null) {
                    try {
                        startSection.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (startSection != null) {
            startSection.close();
        }
    }

    private static void writeUriRelativeFilterGroups(@android.annotation.NonNull com.android.server.pm.SettingsXml.WriteSection writeSection, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<android.content.UriRelativeFilterGroup> list) throws java.io.IOException {
        if (list.isEmpty()) {
            return;
        }
        com.android.server.pm.SettingsXml.WriteSection attribute = writeSection.startSection(TAG_DOMAIN).attribute("name", str);
        for (int i = 0; i < list.size(); i++) {
            try {
                writeUriRelativeFilterGroup(attribute, list.get(i));
            } catch (java.lang.Throwable th) {
                if (attribute != null) {
                    try {
                        attribute.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (attribute != null) {
            attribute.close();
        }
    }

    private static void writeUriRelativeFilterGroup(@android.annotation.NonNull com.android.server.pm.SettingsXml.WriteSection writeSection, @android.annotation.NonNull android.content.UriRelativeFilterGroup uriRelativeFilterGroup) throws java.io.IOException {
        com.android.server.pm.SettingsXml.WriteSection attribute = writeSection.startSection(TAG_URI_RELATIVE_FILTER_GROUP).attribute(ATTR_ACTION, uriRelativeFilterGroup.getAction());
        try {
            for (android.content.UriRelativeFilter uriRelativeFilter : uriRelativeFilterGroup.getUriRelativeFilters()) {
                attribute.startSection(TAG_URI_RELATIVE_FILTER).attribute(ATTR_URI_PART, uriRelativeFilter.getUriPart()).attribute(ATTR_PATTERN_TYPE, uriRelativeFilter.getPatternType()).attribute(ATTR_FILTER, uriRelativeFilter.getFilter()).finish();
            }
            if (attribute != null) {
                attribute.close();
            }
        } catch (java.lang.Throwable th) {
            if (attribute != null) {
                try {
                    attribute.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static class ReadResult {

        @android.annotation.NonNull
        public final android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> active;

        @android.annotation.NonNull
        public final android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> restored;

        public ReadResult(@android.annotation.NonNull android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> arrayMap, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, com.android.server.pm.verify.domain.models.DomainVerificationPkgState> arrayMap2) {
            this.active = arrayMap;
            this.restored = arrayMap2;
        }
    }
}
