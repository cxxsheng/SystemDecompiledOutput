package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public final class IkeSaProposalUtils extends android.net.vcn.persistablebundleutils.SaProposalUtilsBase {
    private static final java.lang.String PRF_KEY = "PRF_KEY";

    public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeSaProposal ikeSaProposal) {
        android.os.PersistableBundle persistableBundle = android.net.vcn.persistablebundleutils.SaProposalUtilsBase.toPersistableBundle(ikeSaProposal);
        persistableBundle.putIntArray(PRF_KEY, ikeSaProposal.getPseudorandomFunctions().stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.net.vcn.persistablebundleutils.IkeSaProposalUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) obj).intValue();
                return intValue;
            }
        }).toArray());
        return persistableBundle;
    }

    public static android.net.ipsec.ike.IkeSaProposal fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        android.net.ipsec.ike.IkeSaProposal.Builder builder = new android.net.ipsec.ike.IkeSaProposal.Builder();
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle("ENCRYPT_ALGO_KEY");
        java.util.Objects.requireNonNull(persistableBundle2, "Encryption algo bundle was null");
        for (android.net.vcn.persistablebundleutils.SaProposalUtilsBase.EncryptionAlgoKeyLenPair encryptionAlgoKeyLenPair : com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle2, new android.net.vcn.persistablebundleutils.ChildSaProposalUtils$$ExternalSyntheticLambda0())) {
            builder.addEncryptionAlgorithm(encryptionAlgoKeyLenPair.encryptionAlgo, encryptionAlgoKeyLenPair.keyLen);
        }
        int[] intArray = persistableBundle.getIntArray("INTEGRITY_ALGO_KEY");
        java.util.Objects.requireNonNull(intArray, "Integrity algo array was null");
        for (int i : intArray) {
            builder.addIntegrityAlgorithm(i);
        }
        int[] intArray2 = persistableBundle.getIntArray("DH_GROUP_KEY");
        java.util.Objects.requireNonNull(intArray2, "DH Group array was null");
        for (int i2 : intArray2) {
            builder.addDhGroup(i2);
        }
        int[] intArray3 = persistableBundle.getIntArray(PRF_KEY);
        java.util.Objects.requireNonNull(intArray3, "PRF array was null");
        for (int i3 : intArray3) {
            builder.addPseudorandomFunction(i3);
        }
        return builder.build();
    }
}
