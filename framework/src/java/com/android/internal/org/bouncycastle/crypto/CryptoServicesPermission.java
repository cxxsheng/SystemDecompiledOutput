package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public class CryptoServicesPermission extends java.security.Permission {
    public static final java.lang.String DEFAULT_RANDOM = "defaultRandomConfig";
    public static final java.lang.String GLOBAL_CONFIG = "globalConfig";
    public static final java.lang.String THREAD_LOCAL_CONFIG = "threadLocalConfig";
    private final java.util.Set<java.lang.String> actions;

    public CryptoServicesPermission(java.lang.String str) {
        super(str);
        this.actions = new java.util.HashSet();
        this.actions.add(str);
    }

    @Override // java.security.Permission
    public boolean implies(java.security.Permission permission) {
        if (permission instanceof com.android.internal.org.bouncycastle.crypto.CryptoServicesPermission) {
            com.android.internal.org.bouncycastle.crypto.CryptoServicesPermission cryptoServicesPermission = (com.android.internal.org.bouncycastle.crypto.CryptoServicesPermission) permission;
            return getName().equals(cryptoServicesPermission.getName()) || this.actions.containsAll(cryptoServicesPermission.actions);
        }
        return false;
    }

    public boolean equals(java.lang.Object obj) {
        if ((obj instanceof com.android.internal.org.bouncycastle.crypto.CryptoServicesPermission) && this.actions.equals(((com.android.internal.org.bouncycastle.crypto.CryptoServicesPermission) obj).actions)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.actions.hashCode();
    }

    @Override // java.security.Permission
    public java.lang.String getActions() {
        return this.actions.toString();
    }
}
