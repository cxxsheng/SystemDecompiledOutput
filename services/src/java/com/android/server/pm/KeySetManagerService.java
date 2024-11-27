package com.android.server.pm;

/* loaded from: classes2.dex */
public class KeySetManagerService {
    public static final int CURRENT_VERSION = 1;
    public static final int FIRST_VERSION = 1;
    public static final long KEYSET_NOT_FOUND = -1;
    protected static final long PUBLIC_KEY_NOT_FOUND = -1;
    static final java.lang.String TAG = "KeySetManagerService";
    private long lastIssuedKeyId;
    private long lastIssuedKeySetId;
    protected final android.util.LongSparseArray<android.util.ArraySet<java.lang.Long>> mKeySetMapping;
    private final android.util.LongSparseArray<com.android.server.pm.KeySetHandle> mKeySets;
    private final com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> mPackages;
    private final android.util.LongSparseArray<com.android.server.pm.KeySetManagerService.PublicKeyHandle> mPublicKeys;

    class PublicKeyHandle {
        private final long mId;
        private final java.security.PublicKey mKey;
        private int mRefCount;

        public PublicKeyHandle(long j, java.security.PublicKey publicKey) {
            this.mId = j;
            this.mRefCount = 1;
            this.mKey = publicKey;
        }

        private PublicKeyHandle(long j, int i, java.security.PublicKey publicKey) {
            this.mId = j;
            this.mRefCount = i;
            this.mKey = publicKey;
        }

        public long getId() {
            return this.mId;
        }

        public java.security.PublicKey getKey() {
            return this.mKey;
        }

        public int getRefCountLPr() {
            return this.mRefCount;
        }

        public void incrRefCountLPw() {
            this.mRefCount++;
        }

        public long decrRefCountLPw() {
            this.mRefCount--;
            return this.mRefCount;
        }
    }

    public KeySetManagerService(com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> watchedArrayMap) {
        this.lastIssuedKeySetId = 0L;
        this.lastIssuedKeyId = 0L;
        this.mKeySets = new android.util.LongSparseArray<>();
        this.mPublicKeys = new android.util.LongSparseArray<>();
        this.mKeySetMapping = new android.util.LongSparseArray<>();
        this.mPackages = watchedArrayMap;
    }

    public KeySetManagerService(@android.annotation.NonNull com.android.server.pm.KeySetManagerService keySetManagerService, @android.annotation.NonNull com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> watchedArrayMap) {
        this.lastIssuedKeySetId = 0L;
        this.lastIssuedKeyId = 0L;
        this.mKeySets = keySetManagerService.mKeySets.clone();
        this.mPublicKeys = keySetManagerService.mPublicKeys.clone();
        this.mKeySetMapping = keySetManagerService.mKeySetMapping.clone();
        this.mPackages = watchedArrayMap;
    }

    public boolean packageIsSignedByLPr(java.lang.String str, com.android.server.pm.KeySetHandle keySetHandle) {
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(str);
        if (packageSetting == null) {
            throw new java.lang.NullPointerException("Invalid package name");
        }
        if (packageSetting.getKeySetData() == null) {
            throw new java.lang.NullPointerException("Package has no KeySet data");
        }
        long idByKeySetLPr = getIdByKeySetLPr(keySetHandle);
        if (idByKeySetLPr == -1) {
            return false;
        }
        return this.mKeySetMapping.get(packageSetting.getKeySetData().getProperSigningKeySet()).containsAll(this.mKeySetMapping.get(idByKeySetLPr));
    }

    public boolean packageIsSignedByExactlyLPr(java.lang.String str, com.android.server.pm.KeySetHandle keySetHandle) {
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(str);
        if (packageSetting == null) {
            throw new java.lang.NullPointerException("Invalid package name");
        }
        if (packageSetting.getKeySetData() == null || packageSetting.getKeySetData().getProperSigningKeySet() == -1) {
            throw new java.lang.NullPointerException("Package has no KeySet data");
        }
        long idByKeySetLPr = getIdByKeySetLPr(keySetHandle);
        if (idByKeySetLPr == -1) {
            return false;
        }
        return this.mKeySetMapping.get(packageSetting.getKeySetData().getProperSigningKeySet()).equals(this.mKeySetMapping.get(idByKeySetLPr));
    }

    public void assertScannedPackageValid(com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.server.pm.PackageManagerException {
        if (androidPackage == null || androidPackage.getPackageName() == null) {
            throw new com.android.server.pm.PackageManagerException(-2, "Passed invalid package to keyset validation.");
        }
        android.util.ArraySet publicKeys = androidPackage.getSigningDetails().getPublicKeys();
        if (publicKeys == null || publicKeys.size() <= 0 || publicKeys.contains(null)) {
            throw new com.android.server.pm.PackageManagerException(-2, "Package has invalid signing-key-set.");
        }
        java.util.Map keySetMapping = androidPackage.getKeySetMapping();
        if (keySetMapping != null) {
            if (keySetMapping.containsKey(null) || keySetMapping.containsValue(null)) {
                throw new com.android.server.pm.PackageManagerException(-2, "Package has null defined key set.");
            }
            for (android.util.ArraySet arraySet : keySetMapping.values()) {
                if (arraySet.size() <= 0 || arraySet.contains(null)) {
                    throw new com.android.server.pm.PackageManagerException(-2, "Package has null/no public keys for defined key-sets.");
                }
            }
        }
        java.util.Set upgradeKeySets = androidPackage.getUpgradeKeySets();
        if (upgradeKeySets != null) {
            if (keySetMapping == null || !keySetMapping.keySet().containsAll(upgradeKeySets)) {
                throw new com.android.server.pm.PackageManagerException(-2, "Package has upgrade-key-sets without corresponding definitions.");
            }
        }
    }

    public void addScannedPackageLPw(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        java.util.Objects.requireNonNull(androidPackage, "Attempted to add null pkg to ksms.");
        java.util.Objects.requireNonNull(androidPackage.getPackageName(), "Attempted to add null pkg to ksms.");
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(androidPackage.getPackageName());
        java.util.Objects.requireNonNull(packageSetting, "pkg: " + androidPackage.getPackageName() + "does not have a corresponding entry in mPackages.");
        addSigningKeySetToPackageLPw(packageSetting, androidPackage.getSigningDetails().getPublicKeys());
        if (androidPackage.getKeySetMapping() != null) {
            addDefinedKeySetsToPackageLPw(packageSetting, androidPackage.getKeySetMapping());
            if (androidPackage.getUpgradeKeySets() != null) {
                addUpgradeKeySetsToPackageLPw(packageSetting, androidPackage.getUpgradeKeySets());
            }
        }
    }

    void addSigningKeySetToPackageLPw(com.android.server.pm.PackageSetting packageSetting, android.util.ArraySet<java.security.PublicKey> arraySet) {
        long properSigningKeySet = packageSetting.getKeySetData().getProperSigningKeySet();
        if (properSigningKeySet != -1) {
            android.util.ArraySet<java.security.PublicKey> publicKeysFromKeySetLPr = getPublicKeysFromKeySetLPr(properSigningKeySet);
            if (publicKeysFromKeySetLPr != null && publicKeysFromKeySetLPr.equals(arraySet)) {
                return;
            } else {
                decrementKeySetLPw(properSigningKeySet);
            }
        }
        packageSetting.getKeySetData().setProperSigningKeySet(addKeySetLPw(arraySet).getId());
    }

    private long getIdByKeySetLPr(com.android.server.pm.KeySetHandle keySetHandle) {
        for (int i = 0; i < this.mKeySets.size(); i++) {
            if (keySetHandle.equals(this.mKeySets.valueAt(i))) {
                return this.mKeySets.keyAt(i);
            }
        }
        return -1L;
    }

    void addDefinedKeySetsToPackageLPw(com.android.server.pm.PackageSetting packageSetting, java.util.Map<java.lang.String, android.util.ArraySet<java.security.PublicKey>> map) {
        android.util.ArrayMap<java.lang.String, java.lang.Long> aliases = packageSetting.getKeySetData().getAliases();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (java.util.Map.Entry<java.lang.String, android.util.ArraySet<java.security.PublicKey>> entry : map.entrySet()) {
            java.lang.String key = entry.getKey();
            android.util.ArraySet<java.security.PublicKey> value = entry.getValue();
            if (key != null && value != null && value.size() > 0) {
                arrayMap.put(key, java.lang.Long.valueOf(addKeySetLPw(value).getId()));
            }
        }
        int size = aliases.size();
        for (int i = 0; i < size; i++) {
            decrementKeySetLPw(aliases.valueAt(i).longValue());
        }
        packageSetting.getKeySetData().removeAllUpgradeKeySets();
        packageSetting.getKeySetData().setAliases(arrayMap);
    }

    void addUpgradeKeySetsToPackageLPw(com.android.server.pm.PackageSetting packageSetting, java.util.Set<java.lang.String> set) {
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            packageSetting.getKeySetData().addUpgradeKeySet(it.next());
        }
    }

    public com.android.server.pm.KeySetHandle getKeySetByAliasAndPackageNameLPr(java.lang.String str, java.lang.String str2) {
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(str);
        if (packageSetting == null || packageSetting.getKeySetData() == null) {
            return null;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Long> aliases = packageSetting.getKeySetData().getAliases();
        java.lang.Long l = aliases.get(str2);
        if (l == null) {
            throw new java.lang.IllegalArgumentException("Unknown KeySet alias: " + str2 + ", aliases = " + aliases);
        }
        return this.mKeySets.get(l.longValue());
    }

    public boolean isIdValidKeySetId(long j) {
        return this.mKeySets.get(j) != null;
    }

    public boolean shouldCheckUpgradeKeySetLocked(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, com.android.server.pm.pkg.SharedUserApi sharedUserApi, int i) {
        if (packageStateInternal == null || (i & 512) != 0 || sharedUserApi != null || !packageStateInternal.getKeySetData().isUsingUpgradeKeySets()) {
            return false;
        }
        long[] upgradeKeySets = packageStateInternal.getKeySetData().getUpgradeKeySets();
        for (int i2 = 0; i2 < upgradeKeySets.length; i2++) {
            if (!isIdValidKeySetId(upgradeKeySets[i2])) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("Package ");
                sb.append(packageStateInternal.getPackageName() != null ? packageStateInternal.getPackageName() : "<null>");
                sb.append(" contains upgrade-key-set reference to unknown key-set: ");
                sb.append(upgradeKeySets[i2]);
                sb.append(" reverting to signatures check.");
                android.util.Slog.wtf(TAG, sb.toString());
                return false;
            }
        }
        return true;
    }

    public boolean checkUpgradeKeySetLocked(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, com.android.server.pm.pkg.AndroidPackage androidPackage) {
        for (long j : packageStateInternal.getKeySetData().getUpgradeKeySets()) {
            android.util.ArraySet<java.security.PublicKey> publicKeysFromKeySetLPr = getPublicKeysFromKeySetLPr(j);
            if (publicKeysFromKeySetLPr != null && androidPackage.getSigningDetails().getPublicKeys().containsAll(publicKeysFromKeySetLPr)) {
                return true;
            }
        }
        return false;
    }

    public android.util.ArraySet<java.security.PublicKey> getPublicKeysFromKeySetLPr(long j) {
        android.util.ArraySet<java.lang.Long> arraySet = this.mKeySetMapping.get(j);
        if (arraySet == null) {
            return null;
        }
        android.util.ArraySet<java.security.PublicKey> arraySet2 = new android.util.ArraySet<>();
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            arraySet2.add(this.mPublicKeys.get(arraySet.valueAt(i).longValue()).getKey());
        }
        return arraySet2;
    }

    public com.android.server.pm.KeySetHandle getSigningKeySetByPackageNameLPr(java.lang.String str) {
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(str);
        if (packageSetting == null || packageSetting.getKeySetData() == null || packageSetting.getKeySetData().getProperSigningKeySet() == -1) {
            return null;
        }
        return this.mKeySets.get(packageSetting.getKeySetData().getProperSigningKeySet());
    }

    private com.android.server.pm.KeySetHandle addKeySetLPw(android.util.ArraySet<java.security.PublicKey> arraySet) {
        if (arraySet == null || arraySet.size() == 0) {
            throw new java.lang.IllegalArgumentException("Cannot add an empty set of keys!");
        }
        android.util.ArraySet<java.lang.Long> arraySet2 = new android.util.ArraySet<>(arraySet.size());
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            arraySet2.add(java.lang.Long.valueOf(addPublicKeyLPw(arraySet.valueAt(i))));
        }
        long idFromKeyIdsLPr = getIdFromKeyIdsLPr(arraySet2);
        if (idFromKeyIdsLPr != -1) {
            for (int i2 = 0; i2 < size; i2++) {
                decrementPublicKeyLPw(arraySet2.valueAt(i2).longValue());
            }
            com.android.server.pm.KeySetHandle keySetHandle = this.mKeySets.get(idFromKeyIdsLPr);
            keySetHandle.incrRefCountLPw();
            return keySetHandle;
        }
        long freeKeySetIDLPw = getFreeKeySetIDLPw();
        com.android.server.pm.KeySetHandle keySetHandle2 = new com.android.server.pm.KeySetHandle(freeKeySetIDLPw);
        this.mKeySets.put(freeKeySetIDLPw, keySetHandle2);
        this.mKeySetMapping.put(freeKeySetIDLPw, arraySet2);
        return keySetHandle2;
    }

    private void decrementKeySetLPw(long j) {
        com.android.server.pm.KeySetHandle keySetHandle = this.mKeySets.get(j);
        if (keySetHandle != null && keySetHandle.decrRefCountLPw() <= 0) {
            android.util.ArraySet<java.lang.Long> arraySet = this.mKeySetMapping.get(j);
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                decrementPublicKeyLPw(arraySet.valueAt(i).longValue());
            }
            this.mKeySets.delete(j);
            this.mKeySetMapping.delete(j);
        }
    }

    private void decrementPublicKeyLPw(long j) {
        com.android.server.pm.KeySetManagerService.PublicKeyHandle publicKeyHandle = this.mPublicKeys.get(j);
        if (publicKeyHandle != null && publicKeyHandle.decrRefCountLPw() <= 0) {
            this.mPublicKeys.delete(j);
        }
    }

    private long addPublicKeyLPw(java.security.PublicKey publicKey) {
        java.util.Objects.requireNonNull(publicKey, "Cannot add null public key!");
        long idForPublicKeyLPr = getIdForPublicKeyLPr(publicKey);
        if (idForPublicKeyLPr != -1) {
            this.mPublicKeys.get(idForPublicKeyLPr).incrRefCountLPw();
            return idForPublicKeyLPr;
        }
        long freePublicKeyIdLPw = getFreePublicKeyIdLPw();
        this.mPublicKeys.put(freePublicKeyIdLPw, new com.android.server.pm.KeySetManagerService.PublicKeyHandle(freePublicKeyIdLPw, publicKey));
        return freePublicKeyIdLPw;
    }

    private long getIdFromKeyIdsLPr(java.util.Set<java.lang.Long> set) {
        for (int i = 0; i < this.mKeySetMapping.size(); i++) {
            if (this.mKeySetMapping.valueAt(i).equals(set)) {
                return this.mKeySetMapping.keyAt(i);
            }
        }
        return -1L;
    }

    private long getIdForPublicKeyLPr(java.security.PublicKey publicKey) {
        java.lang.String str = new java.lang.String(publicKey.getEncoded());
        for (int i = 0; i < this.mPublicKeys.size(); i++) {
            if (str.equals(new java.lang.String(this.mPublicKeys.valueAt(i).getKey().getEncoded()))) {
                return this.mPublicKeys.keyAt(i);
            }
        }
        return -1L;
    }

    private long getFreeKeySetIDLPw() {
        this.lastIssuedKeySetId++;
        return this.lastIssuedKeySetId;
    }

    private long getFreePublicKeyIdLPw() {
        this.lastIssuedKeyId++;
        return this.lastIssuedKeyId;
    }

    public void removeAppKeySetDataLPw(java.lang.String str) {
        com.android.server.pm.PackageSetting packageSetting = this.mPackages.get(str);
        java.util.Objects.requireNonNull(packageSetting, "pkg name: " + str + "does not have a corresponding entry in mPackages.");
        decrementKeySetLPw(packageSetting.getKeySetData().getProperSigningKeySet());
        android.util.ArrayMap<java.lang.String, java.lang.Long> aliases = packageSetting.getKeySetData().getAliases();
        for (int i = 0; i < aliases.size(); i++) {
            decrementKeySetLPw(aliases.valueAt(i).longValue());
        }
        clearPackageKeySetDataLPw(packageSetting);
    }

    private void clearPackageKeySetDataLPw(com.android.server.pm.PackageSetting packageSetting) {
        packageSetting.getKeySetData().setProperSigningKeySet(-1L);
        packageSetting.getKeySetData().removeAllDefinedKeySets();
        packageSetting.getKeySetData().removeAllUpgradeKeySets();
    }

    @java.lang.Deprecated
    public java.lang.String encodePublicKey(java.security.PublicKey publicKey) throws java.io.IOException {
        return new java.lang.String(android.util.Base64.encode(publicKey.getEncoded(), 2));
    }

    public void dumpLPr(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.pm.DumpState dumpState) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        for (java.util.Map.Entry<java.lang.String, com.android.server.pm.PackageSetting> entry : this.mPackages.entrySet()) {
            java.lang.String key = entry.getKey();
            if (str == null || str.equals(key)) {
                if (!z3) {
                    if (dumpState.onTitlePrinted()) {
                        printWriter.println();
                    }
                    printWriter.println("Key Set Manager:");
                    z3 = true;
                }
                com.android.server.pm.PackageSetting value = entry.getValue();
                printWriter.print("  [");
                printWriter.print(key);
                printWriter.println("]");
                if (value.getKeySetData() != null) {
                    boolean z4 = false;
                    for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry2 : value.getKeySetData().getAliases().entrySet()) {
                        if (!z4) {
                            printWriter.print("      KeySets Aliases: ");
                            z4 = true;
                        } else {
                            printWriter.print(", ");
                        }
                        printWriter.print(entry2.getKey());
                        printWriter.print('=');
                        printWriter.print(java.lang.Long.toString(entry2.getValue().longValue()));
                    }
                    if (z4) {
                        printWriter.println("");
                    }
                    if (!value.getKeySetData().isUsingDefinedKeySets()) {
                        z = false;
                    } else {
                        android.util.ArrayMap<java.lang.String, java.lang.Long> aliases = value.getKeySetData().getAliases();
                        int size = aliases.size();
                        z = false;
                        for (int i = 0; i < size; i++) {
                            if (!z) {
                                printWriter.print("      Defined KeySets: ");
                                z = true;
                            } else {
                                printWriter.print(", ");
                            }
                            printWriter.print(java.lang.Long.toString(aliases.valueAt(i).longValue()));
                        }
                    }
                    if (z) {
                        printWriter.println("");
                    }
                    long properSigningKeySet = value.getKeySetData().getProperSigningKeySet();
                    printWriter.print("      Signing KeySets: ");
                    printWriter.print(java.lang.Long.toString(properSigningKeySet));
                    printWriter.println("");
                    if (!value.getKeySetData().isUsingUpgradeKeySets()) {
                        z2 = false;
                    } else {
                        z2 = false;
                        for (long j : value.getKeySetData().getUpgradeKeySets()) {
                            if (!z2) {
                                printWriter.print("      Upgrade KeySets: ");
                                z2 = true;
                            } else {
                                printWriter.print(", ");
                            }
                            printWriter.print(java.lang.Long.toString(j));
                        }
                    }
                    if (z2) {
                        printWriter.println("");
                    }
                }
            }
        }
    }

    void writeKeySetManagerServiceLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "keyset-settings");
        typedXmlSerializer.attributeInt((java.lang.String) null, "version", 1);
        writePublicKeysLPr(typedXmlSerializer);
        writeKeySetsLPr(typedXmlSerializer);
        typedXmlSerializer.startTag((java.lang.String) null, "lastIssuedKeyId");
        typedXmlSerializer.attributeLong((java.lang.String) null, "value", this.lastIssuedKeyId);
        typedXmlSerializer.endTag((java.lang.String) null, "lastIssuedKeyId");
        typedXmlSerializer.startTag((java.lang.String) null, "lastIssuedKeySetId");
        typedXmlSerializer.attributeLong((java.lang.String) null, "value", this.lastIssuedKeySetId);
        typedXmlSerializer.endTag((java.lang.String) null, "lastIssuedKeySetId");
        typedXmlSerializer.endTag((java.lang.String) null, "keyset-settings");
    }

    void writePublicKeysLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "keys");
        for (int i = 0; i < this.mPublicKeys.size(); i++) {
            long keyAt = this.mPublicKeys.keyAt(i);
            com.android.server.pm.KeySetManagerService.PublicKeyHandle valueAt = this.mPublicKeys.valueAt(i);
            typedXmlSerializer.startTag((java.lang.String) null, "public-key");
            typedXmlSerializer.attributeLong((java.lang.String) null, "identifier", keyAt);
            typedXmlSerializer.attributeBytesBase64((java.lang.String) null, "value", valueAt.getKey().getEncoded());
            typedXmlSerializer.endTag((java.lang.String) null, "public-key");
        }
        typedXmlSerializer.endTag((java.lang.String) null, "keys");
    }

    void writeKeySetsLPr(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, "keysets");
        for (int i = 0; i < this.mKeySetMapping.size(); i++) {
            long keyAt = this.mKeySetMapping.keyAt(i);
            android.util.ArraySet<java.lang.Long> valueAt = this.mKeySetMapping.valueAt(i);
            typedXmlSerializer.startTag((java.lang.String) null, "keyset");
            typedXmlSerializer.attributeLong((java.lang.String) null, "identifier", keyAt);
            java.util.Iterator<java.lang.Long> it = valueAt.iterator();
            while (it.hasNext()) {
                long longValue = it.next().longValue();
                typedXmlSerializer.startTag((java.lang.String) null, "key-id");
                typedXmlSerializer.attributeLong((java.lang.String) null, "identifier", longValue);
                typedXmlSerializer.endTag((java.lang.String) null, "key-id");
            }
            typedXmlSerializer.endTag((java.lang.String) null, "keyset");
        }
        typedXmlSerializer.endTag((java.lang.String) null, "keysets");
    }

    void readKeySetsLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.util.ArrayMap<java.lang.Long, java.lang.Integer> arrayMap) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "version") == null) {
            while (true) {
                int next = typedXmlPullParser.next();
                if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                    break;
                }
            }
            java.util.Iterator<com.android.server.pm.PackageSetting> it = this.mPackages.values().iterator();
            while (it.hasNext()) {
                clearPackageKeySetDataLPw(it.next());
            }
            return;
        }
        while (true) {
            int next2 = typedXmlPullParser.next();
            if (next2 == 1 || (next2 == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next2 != 3 && next2 != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (name.equals("keys")) {
                    readKeysLPw(typedXmlPullParser);
                } else if (name.equals("keysets")) {
                    readKeySetListLPw(typedXmlPullParser);
                } else if (name.equals("lastIssuedKeyId")) {
                    this.lastIssuedKeyId = typedXmlPullParser.getAttributeLong((java.lang.String) null, "value");
                } else if (name.equals("lastIssuedKeySetId")) {
                    this.lastIssuedKeySetId = typedXmlPullParser.getAttributeLong((java.lang.String) null, "value");
                }
            }
        }
        addRefCountsFromSavedPackagesLPw(arrayMap);
    }

    void readKeysLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next != 3 || typedXmlPullParser.getDepth() > depth) {
                    if (next != 3 && next != 4 && typedXmlPullParser.getName().equals("public-key")) {
                        readPublicKeyLPw(typedXmlPullParser);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    void readKeySetListLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = typedXmlPullParser.getDepth();
        long j = 0;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    java.lang.String name = typedXmlPullParser.getName();
                    if (name.equals("keyset")) {
                        j = typedXmlPullParser.getAttributeLong((java.lang.String) null, "identifier");
                        this.mKeySets.put(j, new com.android.server.pm.KeySetHandle(j, 0));
                        this.mKeySetMapping.put(j, new android.util.ArraySet<>());
                    } else if (name.equals("key-id")) {
                        this.mKeySetMapping.get(j).add(java.lang.Long.valueOf(typedXmlPullParser.getAttributeLong((java.lang.String) null, "identifier")));
                    }
                }
            } else {
                return;
            }
        }
    }

    void readPublicKeyLPw(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
        long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "identifier");
        java.security.PublicKey parsePublicKey = android.content.pm.parsing.FrameworkParsingPackageUtils.parsePublicKey(typedXmlPullParser.getAttributeBytesBase64((java.lang.String) null, "value", (byte[]) null));
        if (parsePublicKey != null) {
            this.mPublicKeys.put(attributeLong, new com.android.server.pm.KeySetManagerService.PublicKeyHandle(attributeLong, 0, parsePublicKey));
        }
    }

    private void addRefCountsFromSavedPackagesLPw(android.util.ArrayMap<java.lang.Long, java.lang.Integer> arrayMap) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.KeySetHandle keySetHandle = this.mKeySets.get(arrayMap.keyAt(i).longValue());
            if (keySetHandle == null) {
                android.util.Slog.wtf(TAG, "Encountered non-existent key-set reference when reading settings");
            } else {
                keySetHandle.setRefCountLPw(arrayMap.valueAt(i).intValue());
            }
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int size2 = this.mKeySets.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (this.mKeySets.valueAt(i2).getRefCountLPr() == 0) {
                android.util.Slog.wtf(TAG, "Encountered key-set w/out package references when reading settings");
                arraySet.add(java.lang.Long.valueOf(this.mKeySets.keyAt(i2)));
            }
            android.util.ArraySet<java.lang.Long> valueAt = this.mKeySetMapping.valueAt(i2);
            int size3 = valueAt.size();
            for (int i3 = 0; i3 < size3; i3++) {
                this.mPublicKeys.get(valueAt.valueAt(i3).longValue()).incrRefCountLPw();
            }
        }
        int size4 = arraySet.size();
        for (int i4 = 0; i4 < size4; i4++) {
            decrementKeySetLPw(((java.lang.Long) arraySet.valueAt(i4)).longValue());
        }
    }
}
