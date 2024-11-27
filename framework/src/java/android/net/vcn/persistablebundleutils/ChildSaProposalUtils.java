package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public final class ChildSaProposalUtils extends android.net.vcn.persistablebundleutils.SaProposalUtilsBase {
    public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.ChildSaProposal childSaProposal) {
        return android.net.vcn.persistablebundleutils.SaProposalUtilsBase.toPersistableBundle(childSaProposal);
    }

    public static android.net.ipsec.ike.ChildSaProposal fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        android.net.ipsec.ike.ChildSaProposal.Builder builder = new android.net.ipsec.ike.ChildSaProposal.Builder();
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
        return builder.build();
    }
}
