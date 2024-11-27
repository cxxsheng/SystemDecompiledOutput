package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public final class TunnelConnectionParamsUtils {
    private static final int EXPECTED_BUNDLE_KEY_CNT = 1;
    private static final java.lang.String PARAMS_TYPE_IKE = "IKE";

    public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putPersistableBundle(PARAMS_TYPE_IKE, android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils.IkeTunnelConnectionParamsUtils.serializeIkeParams(ikeTunnelConnectionParams));
        return persistableBundle;
    }

    public static android.net.ipsec.ike.IkeTunnelConnectionParams fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        if (persistableBundle.keySet().size() != 1) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Expect PersistableBundle to have %d element but found: %s", 1, persistableBundle.keySet()));
        }
        if (persistableBundle.get(PARAMS_TYPE_IKE) != null) {
            return android.net.vcn.persistablebundleutils.TunnelConnectionParamsUtils.IkeTunnelConnectionParamsUtils.deserializeIkeParams(persistableBundle.getPersistableBundle(PARAMS_TYPE_IKE));
        }
        throw new java.lang.IllegalArgumentException("Invalid Tunnel Connection Params type " + persistableBundle.keySet().iterator().next());
    }

    private static final class IkeTunnelConnectionParamsUtils {
        private static final java.lang.String CHILD_PARAMS_KEY = "CHILD_PARAMS_KEY";
        private static final java.lang.String IKE_PARAMS_KEY = "IKE_PARAMS_KEY";

        private IkeTunnelConnectionParamsUtils() {
        }

        public static android.os.PersistableBundle serializeIkeParams(android.net.ipsec.ike.IkeTunnelConnectionParams ikeTunnelConnectionParams) {
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            persistableBundle.putPersistableBundle(IKE_PARAMS_KEY, android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.toPersistableBundle(ikeTunnelConnectionParams.getIkeSessionParams()));
            persistableBundle.putPersistableBundle(CHILD_PARAMS_KEY, android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils.toPersistableBundle(ikeTunnelConnectionParams.getTunnelModeChildSessionParams()));
            return persistableBundle;
        }

        public static android.net.ipsec.ike.IkeTunnelConnectionParams deserializeIkeParams(android.os.PersistableBundle persistableBundle) {
            android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(IKE_PARAMS_KEY);
            android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(CHILD_PARAMS_KEY);
            java.util.Objects.requireNonNull(persistableBundle2, "IkeSessionParams was null");
            java.util.Objects.requireNonNull(persistableBundle2, "TunnelModeChildSessionParams was null");
            return new android.net.ipsec.ike.IkeTunnelConnectionParams(android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.fromPersistableBundle(persistableBundle2), android.net.vcn.persistablebundleutils.TunnelModeChildSessionParamsUtils.fromPersistableBundle(persistableBundle3));
        }
    }
}
