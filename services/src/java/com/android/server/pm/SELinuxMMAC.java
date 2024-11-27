package com.android.server.pm;

/* loaded from: classes2.dex */
public final class SELinuxMMAC {
    private static final boolean DEBUG_POLICY = false;
    private static final boolean DEBUG_POLICY_INSTALL = false;
    private static final boolean DEBUG_POLICY_ORDER = false;
    private static final java.lang.String DEFAULT_SEINFO = "default";
    private static final java.lang.String PARTITION_STR = ":partition=";
    private static final java.lang.String PRIVILEGED_APP_STR = ":privapp";
    static final long SELINUX_LATEST_CHANGES = 143539591;
    static final long SELINUX_R_CHANGES = 168782947;
    static final java.lang.String TAG = "SELinuxMMAC";
    private static final java.lang.String TARGETSDKVERSION_STR = ":targetSdkVersion=";
    private static boolean sPolicyRead;
    private static final java.util.List<com.android.server.pm.Policy> sPolicies = new java.util.ArrayList();
    private static final java.util.List<java.io.File> sMacPermissions = new java.util.ArrayList();

    static {
        sMacPermissions.add(new java.io.File(android.os.Environment.getRootDirectory(), "/etc/selinux/plat_mac_permissions.xml"));
        java.io.File file = new java.io.File(android.os.Environment.getSystemExtDirectory(), "/etc/selinux/system_ext_mac_permissions.xml");
        if (file.exists()) {
            sMacPermissions.add(file);
        }
        java.io.File file2 = new java.io.File(android.os.Environment.getProductDirectory(), "/etc/selinux/product_mac_permissions.xml");
        if (file2.exists()) {
            sMacPermissions.add(file2);
        }
        java.io.File file3 = new java.io.File(android.os.Environment.getVendorDirectory(), "/etc/selinux/vendor_mac_permissions.xml");
        if (file3.exists()) {
            sMacPermissions.add(file3);
        }
        java.io.File file4 = new java.io.File(android.os.Environment.getOdmDirectory(), "/etc/selinux/odm_mac_permissions.xml");
        if (file4.exists()) {
            sMacPermissions.add(file4);
        }
    }

    public static boolean readInstallPolicy() {
        char c;
        synchronized (sPolicies) {
            try {
                if (sPolicyRead) {
                    return true;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                int size = sMacPermissions.size();
                java.io.FileReader fileReader = null;
                int i = 0;
                while (i < size) {
                    java.io.File file = sMacPermissions.get(i);
                    try {
                        try {
                            java.io.FileReader fileReader2 = new java.io.FileReader(file);
                            try {
                                android.util.Slog.d(TAG, "Using policy file " + file);
                                newPullParser.setInput(fileReader2);
                                newPullParser.nextTag();
                                newPullParser.require(2, null, "policy");
                                while (newPullParser.next() != 3) {
                                    if (newPullParser.getEventType() == 2) {
                                        java.lang.String name = newPullParser.getName();
                                        switch (name.hashCode()) {
                                            case -902467798:
                                                if (name.equals("signer")) {
                                                    c = 0;
                                                    break;
                                                }
                                            default:
                                                c = 65535;
                                                break;
                                        }
                                        switch (c) {
                                            case 0:
                                                arrayList.add(readSignerOrThrow(newPullParser));
                                                break;
                                            default:
                                                skip(newPullParser);
                                                break;
                                        }
                                    }
                                }
                                libcore.io.IoUtils.closeQuietly(fileReader2);
                                i++;
                                fileReader = fileReader2;
                            } catch (java.io.IOException e) {
                                e = e;
                                fileReader = fileReader2;
                                android.util.Slog.w(TAG, "Exception parsing " + file, e);
                                libcore.io.IoUtils.closeQuietly(fileReader);
                                return false;
                            } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException | org.xmlpull.v1.XmlPullParserException e2) {
                                e = e2;
                                fileReader = fileReader2;
                                android.util.Slog.w(TAG, "Exception @" + newPullParser.getPositionDescription() + " while parsing " + file + ":" + e);
                                libcore.io.IoUtils.closeQuietly(fileReader);
                                return false;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                fileReader = fileReader2;
                                libcore.io.IoUtils.closeQuietly(fileReader);
                                throw th;
                            }
                        } catch (java.io.IOException e3) {
                            e = e3;
                        } catch (java.lang.IllegalArgumentException | java.lang.IllegalStateException | org.xmlpull.v1.XmlPullParserException e4) {
                            e = e4;
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                    }
                }
                com.android.server.pm.PolicyComparator policyComparator = new com.android.server.pm.PolicyComparator();
                java.util.Collections.sort(arrayList, policyComparator);
                if (policyComparator.foundDuplicate()) {
                    android.util.Slog.w(TAG, "ERROR! Duplicate entries found parsing mac_permissions.xml files");
                    return false;
                }
                synchronized (sPolicies) {
                    sPolicies.clear();
                    sPolicies.addAll(arrayList);
                    sPolicyRead = true;
                }
                return true;
            } finally {
            }
        }
    }

    private static com.android.server.pm.Policy readSignerOrThrow(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        xmlPullParser.require(2, null, "signer");
        com.android.server.pm.Policy.PolicyBuilder policyBuilder = new com.android.server.pm.Policy.PolicyBuilder();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "signature");
        if (attributeValue != null) {
            policyBuilder.addSignature(attributeValue);
        }
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if ("seinfo".equals(name)) {
                    policyBuilder.setGlobalSeinfoOrThrow(xmlPullParser.getAttributeValue(null, "value"));
                    readSeinfo(xmlPullParser);
                } else if (com.android.server.pm.Settings.ATTR_PACKAGE.equals(name)) {
                    readPackageOrThrow(xmlPullParser, policyBuilder);
                } else if ("cert".equals(name)) {
                    policyBuilder.addSignature(xmlPullParser.getAttributeValue(null, "signature"));
                    readCert(xmlPullParser);
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        return policyBuilder.build();
    }

    private static void readPackageOrThrow(org.xmlpull.v1.XmlPullParser xmlPullParser, com.android.server.pm.Policy.PolicyBuilder policyBuilder) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        xmlPullParser.require(2, null, com.android.server.pm.Settings.ATTR_PACKAGE);
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if ("seinfo".equals(xmlPullParser.getName())) {
                    policyBuilder.addInnerPackageMapOrThrow(attributeValue, xmlPullParser.getAttributeValue(null, "value"));
                    readSeinfo(xmlPullParser);
                } else {
                    skip(xmlPullParser);
                }
            }
        }
    }

    private static void readCert(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        xmlPullParser.require(2, null, "cert");
        xmlPullParser.nextTag();
    }

    private static void readSeinfo(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        xmlPullParser.require(2, null, "seinfo");
        xmlPullParser.nextTag();
    }

    private static void skip(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        if (xmlPullParser.getEventType() != 2) {
            throw new java.lang.IllegalStateException();
        }
        int i = 1;
        while (i != 0) {
            switch (xmlPullParser.next()) {
                case 2:
                    i++;
                    break;
                case 3:
                    i--;
                    break;
            }
        }
    }

    private static int getTargetSdkVersionForSeInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.SharedUserApi sharedUserApi, com.android.server.compat.PlatformCompat platformCompat) {
        if (sharedUserApi != null && sharedUserApi.getPackages().size() != 0) {
            return sharedUserApi.getSeInfoTargetSdkVersion();
        }
        android.content.pm.ApplicationInfo generateAppInfoWithoutState = com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(androidPackage);
        if (platformCompat.isChangeEnabledInternal(SELINUX_LATEST_CHANGES, generateAppInfoWithoutState)) {
            return java.lang.Math.max(10000, androidPackage.getTargetSdkVersion());
        }
        if (platformCompat.isChangeEnabledInternal(SELINUX_R_CHANGES, generateAppInfoWithoutState)) {
            return java.lang.Math.max(30, androidPackage.getTargetSdkVersion());
        }
        return androidPackage.getTargetSdkVersion();
    }

    private static java.lang.String getPartition(com.android.server.pm.pkg.PackageState packageState) {
        if (packageState.isSystemExt()) {
            return "system_ext";
        }
        if (packageState.isProduct()) {
            return "product";
        }
        if (packageState.isVendor()) {
            return "vendor";
        }
        if (packageState.isOem()) {
            return "oem";
        }
        if (packageState.isOdm()) {
            return "odm";
        }
        if (packageState.isSystem()) {
            return "system";
        }
        return "";
    }

    public static java.lang.String getSeInfo(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable com.android.server.pm.pkg.SharedUserApi sharedUserApi, @android.annotation.NonNull com.android.server.compat.PlatformCompat platformCompat) {
        return getSeInfo(packageState, androidPackage, sharedUserApi != null ? sharedUserApi.isPrivileged() | packageState.isPrivileged() : packageState.isPrivileged(), getTargetSdkVersionForSeInfo(androidPackage, sharedUserApi, platformCompat));
    }

    public static java.lang.String getSeInfo(com.android.server.pm.pkg.PackageState packageState, com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, int i) {
        java.lang.String str;
        synchronized (sPolicies) {
            try {
                str = null;
                if (sPolicyRead) {
                    java.util.Iterator<com.android.server.pm.Policy> it = sPolicies.iterator();
                    while (it.hasNext() && (str = it.next().getMatchedSeInfo(androidPackage)) == null) {
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (str == null) {
            str = "default";
        }
        if (z) {
            str = str + PRIVILEGED_APP_STR;
        }
        java.lang.String str2 = str + TARGETSDKVERSION_STR + i;
        java.lang.String partition = getPartition(packageState);
        if (!partition.isEmpty()) {
            return str2 + PARTITION_STR + partition;
        }
        return str2;
    }
}
