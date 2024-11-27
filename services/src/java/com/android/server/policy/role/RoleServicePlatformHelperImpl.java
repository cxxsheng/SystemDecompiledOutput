package com.android.server.policy.role;

/* loaded from: classes2.dex */
public class RoleServicePlatformHelperImpl implements com.android.server.role.RoleServicePlatformHelper {
    private static final java.lang.String ATTRIBUTE_NAME = "name";
    private static final java.lang.String LOG_TAG = com.android.server.policy.role.RoleServicePlatformHelperImpl.class.getSimpleName();
    private static final java.lang.String ROLES_FILE_NAME = "roles.xml";
    private static final java.lang.String TAG_HOLDER = "holder";
    private static final java.lang.String TAG_ROLE = "role";
    private static final java.lang.String TAG_ROLES = "roles";

    @android.annotation.NonNull
    private final android.content.Context mContext;

    public RoleServicePlatformHelperImpl(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.role.RoleServicePlatformHelper
    @android.annotation.NonNull
    public java.util.Map<java.lang.String, java.util.Set<java.lang.String>> getLegacyRoleState(int i) {
        java.util.Map<java.lang.String, java.util.Set<java.lang.String>> readFile = readFile(i);
        if (readFile == null) {
            return readFromLegacySettings(i);
        }
        return readFile;
    }

    @android.annotation.Nullable
    private java.util.Map<java.lang.String, java.util.Set<java.lang.String>> readFile(int i) {
        java.io.File file = getFile(i);
        try {
            try {
                java.io.FileInputStream openRead = new android.util.AtomicFile(file).openRead();
                try {
                    org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                    newPullParser.setInput(openRead, null);
                    java.util.Map<java.lang.String, java.util.Set<java.lang.String>> parseXml = parseXml(newPullParser);
                    android.util.Slog.i(LOG_TAG, "Read legacy roles.xml successfully");
                    if (openRead != null) {
                        openRead.close();
                    }
                    return parseXml;
                } catch (java.lang.Throwable th) {
                    if (openRead != null) {
                        try {
                            openRead.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.i(LOG_TAG, "Legacy roles.xml not found");
                return null;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Slog.wtf(LOG_TAG, "Failed to parse legacy roles.xml: " + file, e2);
            return null;
        }
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, java.util.Set<java.lang.String>> parseXml(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth;
        int depth2 = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (depth <= depth2 && next == 2 && xmlPullParser.getName().equals(TAG_ROLES)) {
                return parseRoles(xmlPullParser);
            }
        }
        throw new java.io.IOException("Missing <roles> in roles.xml");
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, java.util.Set<java.lang.String>> parseRoles(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int depth2 = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (depth <= depth2 && next == 2 && xmlPullParser.getName().equals(TAG_ROLE)) {
                arrayMap.put(xmlPullParser.getAttributeValue(null, "name"), parseRoleHoldersLocked(xmlPullParser));
            }
        }
        return arrayMap;
    }

    @android.annotation.NonNull
    private java.util.Set<java.lang.String> parseRoleHoldersLocked(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth;
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int depth2 = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (depth <= depth2 && next == 2 && xmlPullParser.getName().equals(TAG_HOLDER)) {
                arraySet.add(xmlPullParser.getAttributeValue(null, "name"));
            }
        }
        return arraySet;
    }

    @android.annotation.NonNull
    private static java.io.File getFile(int i) {
        return new java.io.File(android.os.Environment.getUserSystemDirectory(i), ROLES_FILE_NAME);
    }

    @android.annotation.NonNull
    private java.util.Map<java.lang.String, java.util.Set<java.lang.String>> readFromLegacySettings(int i) {
        java.lang.String str;
        java.lang.String str2;
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(contentResolver, "assistant", i);
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.lang.String str3 = null;
        if (stringForUser != null) {
            if (!stringForUser.isEmpty()) {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(stringForUser);
                str = unflattenFromString != null ? unflattenFromString.getPackageName() : null;
            } else {
                str = null;
            }
        } else if (packageManager.isDeviceUpgrading()) {
            str = this.mContext.getString(android.R.string.config_defaultAssistant);
            if (android.text.TextUtils.isEmpty(str)) {
                str = null;
            }
        } else {
            str = null;
        }
        if (str != null) {
            arrayMap.put("android.app.role.ASSISTANT", java.util.Collections.singleton(str));
        }
        java.lang.String removeLegacyDefaultBrowserPackageName = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).removeLegacyDefaultBrowserPackageName(i);
        if (removeLegacyDefaultBrowserPackageName != null) {
            arrayMap.put("android.app.role.BROWSER", java.util.Collections.singleton(removeLegacyDefaultBrowserPackageName));
        }
        java.lang.String stringForUser2 = android.provider.Settings.Secure.getStringForUser(contentResolver, "dialer_default_application", i);
        if (android.text.TextUtils.isEmpty(stringForUser2)) {
            if (packageManager.isDeviceUpgrading()) {
                stringForUser2 = this.mContext.getString(android.R.string.config_defaultDialer);
            } else {
                stringForUser2 = null;
            }
        }
        if (stringForUser2 != null) {
            arrayMap.put("android.app.role.DIALER", java.util.Collections.singleton(stringForUser2));
        }
        java.lang.String stringForUser3 = android.provider.Settings.Secure.getStringForUser(contentResolver, "sms_default_application", i);
        if (android.text.TextUtils.isEmpty(stringForUser3)) {
            if (this.mContext.getPackageManager().isDeviceUpgrading()) {
                stringForUser3 = this.mContext.getString(android.R.string.config_defaultSms);
            } else {
                stringForUser3 = null;
            }
        }
        if (stringForUser3 != null) {
            arrayMap.put("android.app.role.SMS", java.util.Collections.singleton(stringForUser3));
        }
        if (packageManager.isDeviceUpgrading()) {
            android.content.pm.ResolveInfo resolveActivityAsUser = packageManager.resolveActivityAsUser(new android.content.Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 851968, i);
            if (resolveActivityAsUser == null || resolveActivityAsUser.activityInfo == null) {
                str2 = null;
            } else {
                str2 = resolveActivityAsUser.activityInfo.packageName;
            }
            if (str2 == null || !isSettingsApplication(str2, i)) {
                str3 = str2;
            }
        }
        if (str3 != null) {
            arrayMap.put("android.app.role.HOME", java.util.Collections.singleton(str3));
        }
        java.lang.String stringForUser4 = android.provider.Settings.Secure.getStringForUser(contentResolver, "emergency_assistance_application", i);
        if (stringForUser4 != null) {
            arrayMap.put("android.app.role.EMERGENCY", java.util.Collections.singleton(stringForUser4));
        }
        return arrayMap;
    }

    private boolean isSettingsApplication(@android.annotation.NonNull java.lang.String str, int i) {
        android.content.pm.ResolveInfo resolveActivityAsUser = this.mContext.getPackageManager().resolveActivityAsUser(new android.content.Intent("android.settings.SETTINGS"), 851968, i);
        if (resolveActivityAsUser == null || resolveActivityAsUser.activityInfo == null) {
            return false;
        }
        return java.util.Objects.equals(str, resolveActivityAsUser.activityInfo.packageName);
    }

    @Override // com.android.server.role.RoleServicePlatformHelper
    @android.annotation.NonNull
    public java.lang.String computePackageStateHash(final int i) {
        android.content.ComponentName deviceOwnerComponent;
        java.lang.String packageName;
        android.content.ComponentName profileOwnerAsUser;
        final android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class);
        com.android.server.policy.role.RoleServicePlatformHelperImpl.MessageDigestOutputStream messageDigestOutputStream = new com.android.server.policy.role.RoleServicePlatformHelperImpl.MessageDigestOutputStream();
        final java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(new java.io.BufferedOutputStream(messageDigestOutputStream));
        packageManagerInternal.forEachInstalledPackage(new java.util.function.Consumer() { // from class: com.android.server.policy.role.RoleServicePlatformHelperImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.policy.role.RoleServicePlatformHelperImpl.lambda$computePackageStateHash$0(dataOutputStream, packageManagerInternal, i, (com.android.server.pm.pkg.AndroidPackage) obj);
            }
        }, i);
        java.lang.String str = "";
        if (devicePolicyManagerInternal != null) {
            try {
                if (devicePolicyManagerInternal.getDeviceOwnerUserId() == i && (deviceOwnerComponent = devicePolicyManagerInternal.getDeviceOwnerComponent(false)) != null) {
                    packageName = deviceOwnerComponent.getPackageName();
                    dataOutputStream.writeUTF(packageName);
                    if (devicePolicyManagerInternal != null && (profileOwnerAsUser = devicePolicyManagerInternal.getProfileOwnerAsUser(i)) != null) {
                        str = profileOwnerAsUser.getPackageName();
                    }
                    dataOutputStream.writeUTF(str);
                    dataOutputStream.writeInt(android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_demo_mode", 0));
                    dataOutputStream.writeBoolean(android.permission.flags.Flags.walletRoleEnabled());
                    dataOutputStream.flush();
                    return messageDigestOutputStream.getDigestAsString();
                }
            } catch (java.io.IOException e) {
                throw new java.lang.AssertionError(e);
            }
        }
        packageName = "";
        dataOutputStream.writeUTF(packageName);
        if (devicePolicyManagerInternal != null) {
            str = profileOwnerAsUser.getPackageName();
        }
        dataOutputStream.writeUTF(str);
        dataOutputStream.writeInt(android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_demo_mode", 0));
        dataOutputStream.writeBoolean(android.permission.flags.Flags.walletRoleEnabled());
        dataOutputStream.flush();
        return messageDigestOutputStream.getDigestAsString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$computePackageStateHash$0(java.io.DataOutputStream dataOutputStream, android.content.pm.PackageManagerInternal packageManagerInternal, int i, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        try {
            dataOutputStream.writeUTF(androidPackage.getPackageName());
            dataOutputStream.writeLong(androidPackage.getLongVersionCode());
            dataOutputStream.writeInt(packageManagerInternal.getApplicationEnabledState(androidPackage.getPackageName(), i));
            java.util.Set requestedPermissions = androidPackage.getRequestedPermissions();
            dataOutputStream.writeInt(requestedPermissions.size());
            java.util.Iterator it = requestedPermissions.iterator();
            while (it.hasNext()) {
                dataOutputStream.writeUTF((java.lang.String) it.next());
            }
            android.util.ArraySet<java.lang.String> enabledComponents = packageManagerInternal.getEnabledComponents(androidPackage.getPackageName(), i);
            int size = com.android.internal.util.CollectionUtils.size(enabledComponents);
            dataOutputStream.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                dataOutputStream.writeUTF(enabledComponents.valueAt(i2));
            }
            android.util.ArraySet<java.lang.String> disabledComponents = packageManagerInternal.getDisabledComponents(androidPackage.getPackageName(), i);
            int size2 = com.android.internal.util.CollectionUtils.size(disabledComponents);
            for (int i3 = 0; i3 < size2; i3++) {
                dataOutputStream.writeUTF(disabledComponents.valueAt(i3));
            }
            for (android.content.pm.Signature signature : androidPackage.getSigningDetails().getSignatures()) {
                dataOutputStream.write(signature.toByteArray());
            }
        } catch (java.io.IOException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    private static class MessageDigestOutputStream extends java.io.OutputStream {
        private final java.security.MessageDigest mMessageDigest;

        MessageDigestOutputStream() {
            try {
                this.mMessageDigest = java.security.MessageDigest.getInstance("SHA256");
            } catch (java.security.NoSuchAlgorithmException e) {
                throw new java.lang.RuntimeException("Failed to create MessageDigest", e);
            }
        }

        @android.annotation.NonNull
        java.lang.String getDigestAsString() {
            return libcore.util.HexEncoding.encodeToString(this.mMessageDigest.digest(), true);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws java.io.IOException {
            this.mMessageDigest.update((byte) i);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) throws java.io.IOException {
            this.mMessageDigest.update(bArr);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            this.mMessageDigest.update(bArr, i, i2);
        }
    }
}
