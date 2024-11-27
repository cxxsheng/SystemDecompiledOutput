package com.android.server.devicepolicy;

/* loaded from: classes.dex */
final class EnforcingAdmin {
    private static final java.lang.String ATTR_AUTHORITIES = "authorities";
    private static final java.lang.String ATTR_AUTHORITIES_SEPARATOR = ";";
    private static final java.lang.String ATTR_CLASS_NAME = "class-name";
    private static final java.lang.String ATTR_IS_ROLE = "is-role";
    private static final java.lang.String ATTR_PACKAGE_NAME = "package-name";
    private static final java.lang.String ATTR_USER_ID = "user-id";
    static final java.lang.String DEFAULT_AUTHORITY = "default";
    static final java.lang.String DEVICE_ADMIN_AUTHORITY = "device_admin";
    static final java.lang.String DPC_AUTHORITY = "enterprise";
    static final java.lang.String ROLE_AUTHORITY_PREFIX = "role:";
    static final java.lang.String TAG = "EnforcingAdmin";
    private final com.android.server.devicepolicy.ActiveAdmin mActiveAdmin;
    private java.util.Set<java.lang.String> mAuthorities;
    private final android.content.ComponentName mComponentName;
    private final boolean mIsRoleAuthority;
    private final java.lang.String mPackageName;
    private final int mUserId;

    static com.android.server.devicepolicy.EnforcingAdmin createEnforcingAdmin(@android.annotation.NonNull java.lang.String str, int i, com.android.server.devicepolicy.ActiveAdmin activeAdmin) {
        java.util.Objects.requireNonNull(str);
        return new com.android.server.devicepolicy.EnforcingAdmin(str, i, activeAdmin);
    }

    static com.android.server.devicepolicy.EnforcingAdmin createEnterpriseEnforcingAdmin(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        java.util.Objects.requireNonNull(componentName);
        return new com.android.server.devicepolicy.EnforcingAdmin(componentName.getPackageName(), componentName, java.util.Set.of(DPC_AUTHORITY), i, null);
    }

    static com.android.server.devicepolicy.EnforcingAdmin createEnterpriseEnforcingAdmin(@android.annotation.NonNull android.content.ComponentName componentName, int i, com.android.server.devicepolicy.ActiveAdmin activeAdmin) {
        java.util.Objects.requireNonNull(componentName);
        return new com.android.server.devicepolicy.EnforcingAdmin(componentName.getPackageName(), componentName, java.util.Set.of(DPC_AUTHORITY), i, activeAdmin);
    }

    static com.android.server.devicepolicy.EnforcingAdmin createDeviceAdminEnforcingAdmin(android.content.ComponentName componentName, int i, com.android.server.devicepolicy.ActiveAdmin activeAdmin) {
        java.util.Objects.requireNonNull(componentName);
        return new com.android.server.devicepolicy.EnforcingAdmin(componentName.getPackageName(), componentName, java.util.Set.of(DEVICE_ADMIN_AUTHORITY), i, activeAdmin);
    }

    static java.lang.String getRoleAuthorityOf(java.lang.String str) {
        return ROLE_AUTHORITY_PREFIX + str;
    }

    static android.app.admin.Authority getParcelableAuthority(java.lang.String str) {
        if (str == null || str.isEmpty()) {
            return android.app.admin.UnknownAuthority.UNKNOWN_AUTHORITY;
        }
        if (DPC_AUTHORITY.equals(str)) {
            return android.app.admin.DpcAuthority.DPC_AUTHORITY;
        }
        if (DEVICE_ADMIN_AUTHORITY.equals(str)) {
            return android.app.admin.DeviceAdminAuthority.DEVICE_ADMIN_AUTHORITY;
        }
        if (str.startsWith(ROLE_AUTHORITY_PREFIX)) {
            return new android.app.admin.RoleAuthority(java.util.Set.of(str.substring(ROLE_AUTHORITY_PREFIX.length())));
        }
        return android.app.admin.UnknownAuthority.UNKNOWN_AUTHORITY;
    }

    private EnforcingAdmin(java.lang.String str, @android.annotation.Nullable android.content.ComponentName componentName, java.util.Set<java.lang.String> set, int i, @android.annotation.Nullable com.android.server.devicepolicy.ActiveAdmin activeAdmin) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(set);
        this.mIsRoleAuthority = false;
        this.mPackageName = str;
        this.mComponentName = componentName;
        this.mAuthorities = new java.util.HashSet(set);
        this.mUserId = i;
        this.mActiveAdmin = activeAdmin;
    }

    private EnforcingAdmin(java.lang.String str, int i, com.android.server.devicepolicy.ActiveAdmin activeAdmin) {
        java.util.Objects.requireNonNull(str);
        this.mIsRoleAuthority = true;
        this.mPackageName = str;
        this.mUserId = i;
        this.mComponentName = null;
        this.mAuthorities = null;
        this.mActiveAdmin = activeAdmin;
    }

    private static java.util.Set<java.lang.String> getRoleAuthoritiesOrDefault(java.lang.String str, int i) {
        java.util.Set<java.lang.String> roles = getRoles(str, i);
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Iterator<java.lang.String> it = roles.iterator();
        while (it.hasNext()) {
            hashSet.add(ROLE_AUTHORITY_PREFIX + it.next());
        }
        return hashSet.isEmpty() ? java.util.Set.of("default") : hashSet;
    }

    private static java.util.Set<java.lang.String> getRoles(java.lang.String str, int i) {
        com.android.role.RoleManagerLocal roleManagerLocal = (com.android.role.RoleManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.role.RoleManagerLocal.class);
        java.util.HashSet hashSet = new java.util.HashSet();
        java.util.Map rolesAndHolders = roleManagerLocal.getRolesAndHolders(i);
        for (java.lang.String str2 : rolesAndHolders.keySet()) {
            if (((java.util.Set) rolesAndHolders.get(str2)).contains(str)) {
                hashSet.add(str2);
            }
        }
        return hashSet;
    }

    private java.util.Set<java.lang.String> getAuthorities() {
        if (this.mAuthorities == null && this.mIsRoleAuthority) {
            this.mAuthorities = getRoleAuthoritiesOrDefault(this.mPackageName, this.mUserId);
        }
        return this.mAuthorities;
    }

    void reloadRoleAuthorities() {
        if (this.mIsRoleAuthority) {
            this.mAuthorities = getRoleAuthoritiesOrDefault(this.mPackageName, this.mUserId);
        }
    }

    boolean hasAuthority(java.lang.String str) {
        return getAuthorities().contains(str);
    }

    @android.annotation.NonNull
    java.lang.String getPackageName() {
        return this.mPackageName;
    }

    int getUserId() {
        return this.mUserId;
    }

    @android.annotation.Nullable
    public com.android.server.devicepolicy.ActiveAdmin getActiveAdmin() {
        return this.mActiveAdmin;
    }

    @android.annotation.NonNull
    android.app.admin.EnforcingAdmin getParcelableAdmin() {
        android.app.admin.UnknownAuthority unknownAuthority;
        if (this.mIsRoleAuthority) {
            java.util.Set<java.lang.String> roles = getRoles(this.mPackageName, this.mUserId);
            if (roles.isEmpty()) {
                unknownAuthority = android.app.admin.UnknownAuthority.UNKNOWN_AUTHORITY;
            } else {
                unknownAuthority = new android.app.admin.RoleAuthority(roles);
            }
        } else if (this.mAuthorities.contains(DPC_AUTHORITY)) {
            unknownAuthority = android.app.admin.DpcAuthority.DPC_AUTHORITY;
        } else if (this.mAuthorities.contains(DEVICE_ADMIN_AUTHORITY)) {
            unknownAuthority = android.app.admin.DeviceAdminAuthority.DEVICE_ADMIN_AUTHORITY;
        } else {
            unknownAuthority = android.app.admin.UnknownAuthority.UNKNOWN_AUTHORITY;
        }
        return new android.app.admin.EnforcingAdmin(this.mPackageName, unknownAuthority, android.os.UserHandle.of(this.mUserId), this.mComponentName);
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.devicepolicy.EnforcingAdmin.class != obj.getClass()) {
            return false;
        }
        com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin = (com.android.server.devicepolicy.EnforcingAdmin) obj;
        if (java.util.Objects.equals(this.mPackageName, enforcingAdmin.mPackageName) && java.util.Objects.equals(this.mComponentName, enforcingAdmin.mComponentName) && java.util.Objects.equals(java.lang.Boolean.valueOf(this.mIsRoleAuthority), java.lang.Boolean.valueOf(enforcingAdmin.mIsRoleAuthority)) && hasMatchingAuthorities(this, enforcingAdmin)) {
            return true;
        }
        return false;
    }

    private static boolean hasMatchingAuthorities(com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin, com.android.server.devicepolicy.EnforcingAdmin enforcingAdmin2) {
        if (enforcingAdmin.mIsRoleAuthority && enforcingAdmin2.mIsRoleAuthority) {
            return true;
        }
        return enforcingAdmin.getAuthorities().equals(enforcingAdmin2.getAuthorities());
    }

    public int hashCode() {
        if (this.mIsRoleAuthority) {
            return java.util.Objects.hash(this.mPackageName, java.lang.Integer.valueOf(this.mUserId));
        }
        return java.util.Objects.hash(this.mComponentName == null ? this.mPackageName : this.mComponentName, java.lang.Integer.valueOf(this.mUserId), getAuthorities());
    }

    void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_PACKAGE_NAME, this.mPackageName);
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_IS_ROLE, this.mIsRoleAuthority);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_USER_ID, this.mUserId);
        if (!this.mIsRoleAuthority) {
            if (this.mComponentName != null) {
                typedXmlSerializer.attribute((java.lang.String) null, ATTR_CLASS_NAME, this.mComponentName.getClassName());
            }
            typedXmlSerializer.attribute((java.lang.String) null, ATTR_AUTHORITIES, java.lang.String.join(ATTR_AUTHORITIES_SEPARATOR, getAuthorities()));
        }
    }

    @android.annotation.Nullable
    static com.android.server.devicepolicy.EnforcingAdmin readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_PACKAGE_NAME);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_IS_ROLE);
        java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_AUTHORITIES);
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_USER_ID);
        if (attributeBoolean) {
            if (attributeValue == null) {
                com.android.server.utils.Slogf.wtf(TAG, "Error parsing EnforcingAdmin with RoleAuthority, packageName is null.");
                return null;
            }
            return new com.android.server.devicepolicy.EnforcingAdmin(attributeValue, attributeInt, null);
        }
        if (attributeValue == null || attributeValue2 == null) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Error parsing EnforcingAdmin, packageName is ");
            if (attributeValue == null) {
                attributeValue = "null";
            }
            sb.append(attributeValue);
            sb.append(", and authorities is ");
            if (attributeValue2 == null) {
                attributeValue2 = "null";
            }
            sb.append(attributeValue2);
            sb.append(".");
            com.android.server.utils.Slogf.wtf(TAG, sb.toString());
            return null;
        }
        java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, ATTR_CLASS_NAME);
        return new com.android.server.devicepolicy.EnforcingAdmin(attributeValue, attributeValue3 != null ? new android.content.ComponentName(attributeValue, attributeValue3) : null, java.util.Set.of((java.lang.Object[]) attributeValue2.split(ATTR_AUTHORITIES_SEPARATOR)), attributeInt, null);
    }

    public java.lang.String toString() {
        return "EnforcingAdmin { mPackageName= " + this.mPackageName + ", mComponentName= " + this.mComponentName + ", mAuthorities= " + this.mAuthorities + ", mUserId= " + this.mUserId + ", mIsRoleAuthority= " + this.mIsRoleAuthority + " }\n";
    }
}
