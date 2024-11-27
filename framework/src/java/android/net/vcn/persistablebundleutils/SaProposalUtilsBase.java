package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
abstract class SaProposalUtilsBase {
    static final java.lang.String DH_GROUP_KEY = "DH_GROUP_KEY";
    static final java.lang.String ENCRYPT_ALGO_KEY = "ENCRYPT_ALGO_KEY";
    static final java.lang.String INTEGRITY_ALGO_KEY = "INTEGRITY_ALGO_KEY";

    SaProposalUtilsBase() {
    }

    static class EncryptionAlgoKeyLenPair {
        private static final java.lang.String ALGO_KEY = "ALGO_KEY";
        private static final java.lang.String KEY_LEN_KEY = "KEY_LEN_KEY";
        public final int encryptionAlgo;
        public final int keyLen;

        EncryptionAlgoKeyLenPair(int i, int i2) {
            this.encryptionAlgo = i;
            this.keyLen = i2;
        }

        EncryptionAlgoKeyLenPair(android.os.PersistableBundle persistableBundle) {
            java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            this.encryptionAlgo = persistableBundle.getInt(ALGO_KEY);
            this.keyLen = persistableBundle.getInt(KEY_LEN_KEY);
        }

        public android.os.PersistableBundle toPersistableBundle() {
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            persistableBundle.putInt(ALGO_KEY, this.encryptionAlgo);
            persistableBundle.putInt(KEY_LEN_KEY, this.keyLen);
            return persistableBundle;
        }
    }

    static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.SaProposal saProposal) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.util.Pair<java.lang.Integer, java.lang.Integer> pair : saProposal.getEncryptionAlgorithms()) {
            arrayList.add(new android.net.vcn.persistablebundleutils.SaProposalUtilsBase.EncryptionAlgoKeyLenPair(pair.first.intValue(), pair.second.intValue()));
        }
        persistableBundle.putPersistableBundle(ENCRYPT_ALGO_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(arrayList, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.SaProposalUtilsBase$$ExternalSyntheticLambda0
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return ((android.net.vcn.persistablebundleutils.SaProposalUtilsBase.EncryptionAlgoKeyLenPair) obj).toPersistableBundle();
            }
        }));
        persistableBundle.putIntArray(INTEGRITY_ALGO_KEY, saProposal.getIntegrityAlgorithms().stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.net.vcn.persistablebundleutils.SaProposalUtilsBase$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) obj).intValue();
                return intValue;
            }
        }).toArray());
        persistableBundle.putIntArray(DH_GROUP_KEY, saProposal.getDhGroups().stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.net.vcn.persistablebundleutils.SaProposalUtilsBase$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) obj).intValue();
                return intValue;
            }
        }).toArray());
        return persistableBundle;
    }
}
