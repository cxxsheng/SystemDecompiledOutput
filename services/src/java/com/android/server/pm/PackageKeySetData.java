package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageKeySetData {
    static final long KEYSET_UNASSIGNED = -1;
    private final android.util.ArrayMap<java.lang.String, java.lang.Long> mKeySetAliases;
    private long mProperSigningKeySet;
    private long[] mUpgradeKeySets;

    PackageKeySetData() {
        this.mKeySetAliases = new android.util.ArrayMap<>();
        this.mProperSigningKeySet = -1L;
    }

    PackageKeySetData(com.android.server.pm.PackageKeySetData packageKeySetData) {
        this.mKeySetAliases = new android.util.ArrayMap<>();
        this.mProperSigningKeySet = packageKeySetData.mProperSigningKeySet;
        this.mUpgradeKeySets = com.android.internal.util.ArrayUtils.cloneOrNull(packageKeySetData.mUpgradeKeySets);
        this.mKeySetAliases.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Long>) packageKeySetData.mKeySetAliases);
    }

    protected void setProperSigningKeySet(long j) {
        this.mProperSigningKeySet = j;
    }

    protected long getProperSigningKeySet() {
        return this.mProperSigningKeySet;
    }

    protected void addUpgradeKeySet(java.lang.String str) {
        if (str == null) {
            return;
        }
        java.lang.Long l = this.mKeySetAliases.get(str);
        if (l != null) {
            this.mUpgradeKeySets = com.android.internal.util.ArrayUtils.appendLong(this.mUpgradeKeySets, l.longValue());
            return;
        }
        throw new java.lang.IllegalArgumentException("Upgrade keyset alias " + str + "does not refer to a defined keyset alias!");
    }

    protected void addUpgradeKeySetById(long j) {
        this.mUpgradeKeySets = com.android.internal.util.ArrayUtils.appendLong(this.mUpgradeKeySets, j);
    }

    protected void removeAllUpgradeKeySets() {
        this.mUpgradeKeySets = null;
    }

    protected long[] getUpgradeKeySets() {
        return this.mUpgradeKeySets;
    }

    protected android.util.ArrayMap<java.lang.String, java.lang.Long> getAliases() {
        return this.mKeySetAliases;
    }

    protected void setAliases(java.util.Map<java.lang.String, java.lang.Long> map) {
        removeAllDefinedKeySets();
        this.mKeySetAliases.putAll(map);
    }

    protected void addDefinedKeySet(long j, java.lang.String str) {
        this.mKeySetAliases.put(str, java.lang.Long.valueOf(j));
    }

    protected void removeAllDefinedKeySets() {
        this.mKeySetAliases.erase();
    }

    protected boolean isUsingDefinedKeySets() {
        return this.mKeySetAliases.size() > 0;
    }

    protected boolean isUsingUpgradeKeySets() {
        return this.mUpgradeKeySets != null && this.mUpgradeKeySets.length > 0;
    }
}
