package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public final class PermissionAllowlist {

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mOemAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mPrivilegedAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mVendorPrivilegedAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mProductPrivilegedAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mSystemExtPrivilegedAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>>> mApexPrivilegedAppAllowlists = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mSignatureAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mVendorSignatureAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mProductSignatureAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mSystemExtSignatureAppAllowlist = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getOemAppAllowlist() {
        return this.mOemAppAllowlist;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getPrivilegedAppAllowlist() {
        return this.mPrivilegedAppAllowlist;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getVendorPrivilegedAppAllowlist() {
        return this.mVendorPrivilegedAppAllowlist;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getProductPrivilegedAppAllowlist() {
        return this.mProductPrivilegedAppAllowlist;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getSystemExtPrivilegedAppAllowlist() {
        return this.mSystemExtPrivilegedAppAllowlist;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>>> getApexPrivilegedAppAllowlists() {
        return this.mApexPrivilegedAppAllowlists;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getSignatureAppAllowlist() {
        return this.mSignatureAppAllowlist;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getVendorSignatureAppAllowlist() {
        return this.mVendorSignatureAppAllowlist;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getProductSignatureAppAllowlist() {
        return this.mProductSignatureAppAllowlist;
    }

    @android.annotation.NonNull
    public android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> getSystemExtSignatureAppAllowlist() {
        return this.mSystemExtSignatureAppAllowlist;
    }

    @android.annotation.Nullable
    public java.lang.Boolean getOemAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mOemAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getPrivilegedAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mPrivilegedAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getVendorPrivilegedAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mVendorPrivilegedAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getProductPrivilegedAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mProductPrivilegedAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getSystemExtPrivilegedAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mSystemExtPrivilegedAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getApexPrivilegedAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.String str3) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap;
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> arrayMap2 = this.mApexPrivilegedAppAllowlists.get(str);
        if (arrayMap2 == null || (arrayMap = arrayMap2.get(str2)) == null) {
            return null;
        }
        return arrayMap.get(str3);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getSignatureAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mSignatureAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getVendorSignatureAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mVendorSignatureAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getProductSignatureAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mProductSignatureAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }

    @android.annotation.Nullable
    public java.lang.Boolean getSystemExtSignatureAppAllowlistState(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mSystemExtSignatureAppAllowlist.get(str);
        if (arrayMap == null) {
            return null;
        }
        return arrayMap.get(str2);
    }
}
