package com.android.internal.org.bouncycastle.jcajce.provider.config;

/* loaded from: classes4.dex */
public class ProviderConfigurationPermission extends java.security.BasicPermission {
    private static final int ACCEPTABLE_EC_CURVES = 16;
    private static final java.lang.String ACCEPTABLE_EC_CURVES_STR = "acceptableeccurves";
    private static final int ADDITIONAL_EC_PARAMETERS = 32;
    private static final java.lang.String ADDITIONAL_EC_PARAMETERS_STR = "additionalecparameters";
    private static final int ALL = 63;
    private static final java.lang.String ALL_STR = "all";
    private static final int DH_DEFAULT_PARAMS = 8;
    private static final java.lang.String DH_DEFAULT_PARAMS_STR = "dhdefaultparams";
    private static final int EC_IMPLICITLY_CA = 2;
    private static final java.lang.String EC_IMPLICITLY_CA_STR = "ecimplicitlyca";
    private static final int THREAD_LOCAL_DH_DEFAULT_PARAMS = 4;
    private static final java.lang.String THREAD_LOCAL_DH_DEFAULT_PARAMS_STR = "threadlocaldhdefaultparams";
    private static final int THREAD_LOCAL_EC_IMPLICITLY_CA = 1;
    private static final java.lang.String THREAD_LOCAL_EC_IMPLICITLY_CA_STR = "threadlocalecimplicitlyca";
    private final java.lang.String actions;
    private final int permissionMask;

    public ProviderConfigurationPermission(java.lang.String str) {
        super(str);
        this.actions = ALL_STR;
        this.permissionMask = 63;
    }

    public ProviderConfigurationPermission(java.lang.String str, java.lang.String str2) {
        super(str, str2);
        this.actions = str2;
        this.permissionMask = calculateMask(str2);
    }

    private int calculateMask(java.lang.String str) {
        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(com.android.internal.org.bouncycastle.util.Strings.toLowerCase(str), " ,");
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals(THREAD_LOCAL_EC_IMPLICITLY_CA_STR)) {
                i |= 1;
            } else if (nextToken.equals(EC_IMPLICITLY_CA_STR)) {
                i |= 2;
            } else if (nextToken.equals(THREAD_LOCAL_DH_DEFAULT_PARAMS_STR)) {
                i |= 4;
            } else if (nextToken.equals(DH_DEFAULT_PARAMS_STR)) {
                i |= 8;
            } else if (nextToken.equals(ACCEPTABLE_EC_CURVES_STR)) {
                i |= 16;
            } else if (nextToken.equals(ADDITIONAL_EC_PARAMETERS_STR)) {
                i |= 32;
            } else if (nextToken.equals(ALL_STR)) {
                i = 63;
            }
        }
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("unknown permissions passed to mask");
        }
        return i;
    }

    @Override // java.security.BasicPermission, java.security.Permission
    public java.lang.String getActions() {
        return this.actions;
    }

    @Override // java.security.BasicPermission, java.security.Permission
    public boolean implies(java.security.Permission permission) {
        if (!(permission instanceof com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission) || !getName().equals(permission.getName())) {
            return false;
        }
        com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission providerConfigurationPermission = (com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission) permission;
        return (this.permissionMask & providerConfigurationPermission.permissionMask) == providerConfigurationPermission.permissionMask;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission)) {
            return false;
        }
        com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission providerConfigurationPermission = (com.android.internal.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission) obj;
        return this.permissionMask == providerConfigurationPermission.permissionMask && getName().equals(providerConfigurationPermission.getName());
    }

    public int hashCode() {
        return getName().hashCode() + this.permissionMask;
    }
}
