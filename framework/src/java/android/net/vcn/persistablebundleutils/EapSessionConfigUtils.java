package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public final class EapSessionConfigUtils {
    private static final java.lang.String EAP_AKA_CONFIG_KEY = "EAP_AKA_CONFIG_KEY";
    private static final java.lang.String EAP_AKA_PRIME_CONFIG_KEY = "EAP_AKA_PRIME_CONFIG_KEY";
    private static final java.lang.String EAP_ID_KEY = "EAP_ID_KEY";
    private static final java.lang.String EAP_MSCHAP_V2_CONFIG_KEY = "EAP_MSCHAP_V2_CONFIG_KEY";
    private static final java.lang.String EAP_SIM_CONFIG_KEY = "EAP_SIM_CONFIG_KEY";
    private static final java.lang.String EAP_TTLS_CONFIG_KEY = "EAP_TTLS_CONFIG_KEY";

    public static android.os.PersistableBundle toPersistableBundle(android.net.eap.EapSessionConfig eapSessionConfig) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putPersistableBundle(EAP_ID_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray(eapSessionConfig.getEapIdentity()));
        if (eapSessionConfig.getEapSimConfig() != null) {
            persistableBundle.putPersistableBundle(EAP_SIM_CONFIG_KEY, android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapSimConfigUtils.toPersistableBundle(eapSessionConfig.getEapSimConfig()));
        }
        if (eapSessionConfig.getEapTtlsConfig() != null) {
            persistableBundle.putPersistableBundle(EAP_TTLS_CONFIG_KEY, android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapTtlsConfigUtils.toPersistableBundle(eapSessionConfig.getEapTtlsConfig()));
        }
        if (eapSessionConfig.getEapAkaConfig() != null) {
            persistableBundle.putPersistableBundle(EAP_AKA_CONFIG_KEY, android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapAkaConfigUtils.toPersistableBundle(eapSessionConfig.getEapAkaConfig()));
        }
        if (eapSessionConfig.getEapMsChapV2Config() != null) {
            persistableBundle.putPersistableBundle(EAP_MSCHAP_V2_CONFIG_KEY, android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapMsChapV2ConfigUtils.toPersistableBundle(eapSessionConfig.getEapMsChapV2Config()));
        }
        if (eapSessionConfig.getEapAkaPrimeConfig() != null) {
            persistableBundle.putPersistableBundle(EAP_AKA_PRIME_CONFIG_KEY, android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapAkaPrimeConfigUtils.toPersistableBundle(eapSessionConfig.getEapAkaPrimeConfig()));
        }
        return persistableBundle;
    }

    public static android.net.eap.EapSessionConfig fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
        android.net.eap.EapSessionConfig.Builder builder = new android.net.eap.EapSessionConfig.Builder();
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(EAP_ID_KEY);
        java.util.Objects.requireNonNull(persistableBundle2, "EAP ID was null");
        builder.setEapIdentity(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle2));
        android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(EAP_SIM_CONFIG_KEY);
        if (persistableBundle3 != null) {
            android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapSimConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle3, builder);
        }
        android.os.PersistableBundle persistableBundle4 = persistableBundle.getPersistableBundle(EAP_TTLS_CONFIG_KEY);
        if (persistableBundle4 != null) {
            android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapTtlsConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle4, builder);
        }
        android.os.PersistableBundle persistableBundle5 = persistableBundle.getPersistableBundle(EAP_AKA_CONFIG_KEY);
        if (persistableBundle5 != null) {
            android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapAkaConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle5, builder);
        }
        android.os.PersistableBundle persistableBundle6 = persistableBundle.getPersistableBundle(EAP_MSCHAP_V2_CONFIG_KEY);
        if (persistableBundle6 != null) {
            android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapMsChapV2ConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle6, builder);
        }
        android.os.PersistableBundle persistableBundle7 = persistableBundle.getPersistableBundle(EAP_AKA_PRIME_CONFIG_KEY);
        if (persistableBundle7 != null) {
            android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapAkaPrimeConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle7, builder);
        }
        return builder.build();
    }

    private static class EapMethodConfigUtils {
        private static final java.lang.String METHOD_TYPE = "METHOD_TYPE";

        private EapMethodConfigUtils() {
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.eap.EapSessionConfig.EapMethodConfig eapMethodConfig) {
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            persistableBundle.putInt(METHOD_TYPE, eapMethodConfig.getMethodType());
            return persistableBundle;
        }
    }

    private static class EapUiccConfigUtils extends android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapMethodConfigUtils {
        static final java.lang.String APP_TYPE_KEY = "APP_TYPE_KEY";
        static final java.lang.String SUB_ID_KEY = "SUB_ID_KEY";

        private EapUiccConfigUtils() {
            super();
        }

        protected static android.os.PersistableBundle toPersistableBundle(android.net.eap.EapSessionConfig.EapUiccConfig eapUiccConfig) {
            android.os.PersistableBundle persistableBundle = android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapMethodConfigUtils.toPersistableBundle(eapUiccConfig);
            persistableBundle.putInt(SUB_ID_KEY, eapUiccConfig.getSubId());
            persistableBundle.putInt(APP_TYPE_KEY, eapUiccConfig.getAppType());
            return persistableBundle;
        }
    }

    private static final class EapSimConfigUtils extends android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapUiccConfigUtils {
        private EapSimConfigUtils() {
            super();
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.eap.EapSessionConfig.EapSimConfig eapSimConfig) {
            return android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapUiccConfigUtils.toPersistableBundle((android.net.eap.EapSessionConfig.EapUiccConfig) eapSimConfig);
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.net.eap.EapSessionConfig.Builder builder) {
            java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            builder.setEapSimConfig(persistableBundle.getInt("SUB_ID_KEY"), persistableBundle.getInt("APP_TYPE_KEY"));
        }
    }

    private static class EapAkaConfigUtils extends android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapUiccConfigUtils {
        private EapAkaConfigUtils() {
            super();
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.eap.EapSessionConfig.EapAkaConfig eapAkaConfig) {
            return android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapUiccConfigUtils.toPersistableBundle((android.net.eap.EapSessionConfig.EapUiccConfig) eapAkaConfig);
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.net.eap.EapSessionConfig.Builder builder) {
            java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            builder.setEapAkaConfig(persistableBundle.getInt("SUB_ID_KEY"), persistableBundle.getInt("APP_TYPE_KEY"));
        }
    }

    private static final class EapAkaPrimeConfigUtils extends android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapAkaConfigUtils {
        private static final java.lang.String ALL_MISMATCHED_NETWORK_KEY = "ALL_MISMATCHED_NETWORK_KEY";
        private static final java.lang.String NETWORK_NAME_KEY = "NETWORK_NAME_KEY";

        private EapAkaPrimeConfigUtils() {
            super();
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.eap.EapSessionConfig.EapAkaPrimeConfig eapAkaPrimeConfig) {
            android.os.PersistableBundle persistableBundle = android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapUiccConfigUtils.toPersistableBundle((android.net.eap.EapSessionConfig.EapUiccConfig) eapAkaPrimeConfig);
            persistableBundle.putString(NETWORK_NAME_KEY, eapAkaPrimeConfig.getNetworkName());
            persistableBundle.putBoolean(ALL_MISMATCHED_NETWORK_KEY, eapAkaPrimeConfig.allowsMismatchedNetworkNames());
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.net.eap.EapSessionConfig.Builder builder) {
            java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            builder.setEapAkaPrimeConfig(persistableBundle.getInt("SUB_ID_KEY"), persistableBundle.getInt("APP_TYPE_KEY"), persistableBundle.getString(NETWORK_NAME_KEY), persistableBundle.getBoolean(ALL_MISMATCHED_NETWORK_KEY));
        }
    }

    private static final class EapMsChapV2ConfigUtils extends android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapMethodConfigUtils {
        private static final java.lang.String PASSWORD_KEY = "PASSWORD_KEY";
        private static final java.lang.String USERNAME_KEY = "USERNAME_KEY";

        private EapMsChapV2ConfigUtils() {
            super();
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.eap.EapSessionConfig.EapMsChapV2Config eapMsChapV2Config) {
            android.os.PersistableBundle persistableBundle = android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapMethodConfigUtils.toPersistableBundle(eapMsChapV2Config);
            persistableBundle.putString(USERNAME_KEY, eapMsChapV2Config.getUsername());
            persistableBundle.putString(PASSWORD_KEY, eapMsChapV2Config.getPassword());
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.net.eap.EapSessionConfig.Builder builder) {
            java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            builder.setEapMsChapV2Config(persistableBundle.getString(USERNAME_KEY), persistableBundle.getString(PASSWORD_KEY));
        }
    }

    private static final class EapTtlsConfigUtils extends android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapMethodConfigUtils {
        private static final java.lang.String EAP_SESSION_CONFIG_KEY = "EAP_SESSION_CONFIG_KEY";
        private static final java.lang.String TRUST_CERT_KEY = "TRUST_CERT_KEY";

        private EapTtlsConfigUtils() {
            super();
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.eap.EapSessionConfig.EapTtlsConfig eapTtlsConfig) {
            android.os.PersistableBundle persistableBundle = android.net.vcn.persistablebundleutils.EapSessionConfigUtils.EapMethodConfigUtils.toPersistableBundle(eapTtlsConfig);
            try {
                if (eapTtlsConfig.getServerCaCert() != null) {
                    persistableBundle.putPersistableBundle(TRUST_CERT_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray(eapTtlsConfig.getServerCaCert().getEncoded()));
                }
                persistableBundle.putPersistableBundle(EAP_SESSION_CONFIG_KEY, android.net.vcn.persistablebundleutils.EapSessionConfigUtils.toPersistableBundle(eapTtlsConfig.getInnerEapSessionConfig()));
                return persistableBundle;
            } catch (java.security.cert.CertificateEncodingException e) {
                throw new java.lang.IllegalStateException("Fail to encode the certificate");
            }
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.net.eap.EapSessionConfig.Builder builder) {
            java.security.cert.X509Certificate x509Certificate;
            java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(TRUST_CERT_KEY);
            if (persistableBundle2 == null) {
                x509Certificate = null;
            } else {
                x509Certificate = android.net.vcn.persistablebundleutils.CertUtils.certificateFromByteArray(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle2));
            }
            android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(EAP_SESSION_CONFIG_KEY);
            java.util.Objects.requireNonNull(persistableBundle3, "Inner EAP Session Config was null");
            builder.setEapTtlsConfig(x509Certificate, android.net.vcn.persistablebundleutils.EapSessionConfigUtils.fromPersistableBundle(persistableBundle3));
        }
    }
}
