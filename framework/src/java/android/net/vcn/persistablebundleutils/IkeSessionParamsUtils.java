package android.net.vcn.persistablebundleutils;

/* loaded from: classes2.dex */
public final class IkeSessionParamsUtils {
    private static final java.lang.String CONFIG_REQUESTS_KEY = "CONFIG_REQUESTS_KEY";
    private static final java.lang.String DPD_DELAY_SEC_KEY = "DPD_DELAY_SEC_KEY";
    private static final java.lang.String ENCAP_TYPE_KEY = "ENCAP_TYPE_KEY";
    private static final java.lang.String HARD_LIFETIME_SEC_KEY = "HARD_LIFETIME_SEC_KEY";
    private static final java.lang.String IKE_OPTIONS_KEY = "IKE_OPTIONS_KEY";
    public static final int IKE_OPTION_AUTOMATIC_ADDRESS_FAMILY_SELECTION = 6;
    public static final int IKE_OPTION_AUTOMATIC_NATT_KEEPALIVES = 7;
    private static final java.lang.String IP_VERSION_KEY = "IP_VERSION_KEY";
    private static final java.lang.String LOCAL_AUTH_KEY = "LOCAL_AUTH_KEY";
    private static final java.lang.String LOCAL_ID_KEY = "LOCAL_ID_KEY";
    private static final java.lang.String NATT_KEEPALIVE_DELAY_SEC_KEY = "NATT_KEEPALIVE_DELAY_SEC_KEY";
    private static final java.lang.String REMOTE_AUTH_KEY = "REMOTE_AUTH_KEY";
    private static final java.lang.String REMOTE_ID_KEY = "REMOTE_ID_KEY";
    private static final java.lang.String RETRANS_TIMEOUTS_KEY = "RETRANS_TIMEOUTS_KEY";
    private static final java.lang.String SA_PROPOSALS_KEY = "SA_PROPOSALS_KEY";
    private static final java.lang.String SERVER_HOST_NAME_KEY = "SERVER_HOST_NAME_KEY";
    private static final java.lang.String SOFT_LIFETIME_SEC_KEY = "SOFT_LIFETIME_SEC_KEY";
    private static final java.lang.String TAG = android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.class.getSimpleName();
    private static final java.util.Set<java.lang.Integer> IKE_OPTIONS = new android.util.ArraySet();

    static {
        IKE_OPTIONS.add(0);
        IKE_OPTIONS.add(1);
        IKE_OPTIONS.add(2);
        IKE_OPTIONS.add(3);
        IKE_OPTIONS.add(4);
        IKE_OPTIONS.add(5);
        IKE_OPTIONS.add(6);
        IKE_OPTIONS.add(7);
        IKE_OPTIONS.add(8);
    }

    public static boolean isIkeOptionValid(int i) {
        try {
            new android.net.ipsec.ike.IkeSessionParams.Builder().addIkeOption(i);
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.d(TAG, "Option not supported; discarding: " + i);
            return false;
        }
    }

    public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeSessionParams ikeSessionParams) {
        if (ikeSessionParams.getNetwork() != null || ikeSessionParams.getIke3gppExtension() != null) {
            throw new java.lang.IllegalStateException("Cannot convert a IkeSessionParams with a caller configured network or with 3GPP extension enabled");
        }
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putString(SERVER_HOST_NAME_KEY, ikeSessionParams.getServerHostname());
        persistableBundle.putPersistableBundle(SA_PROPOSALS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(ikeSessionParams.getSaProposals(), new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda2
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return android.net.vcn.persistablebundleutils.IkeSaProposalUtils.toPersistableBundle((android.net.ipsec.ike.IkeSaProposal) obj);
            }
        }));
        persistableBundle.putPersistableBundle(LOCAL_ID_KEY, android.net.vcn.persistablebundleutils.IkeIdentificationUtils.toPersistableBundle(ikeSessionParams.getLocalIdentification()));
        persistableBundle.putPersistableBundle(REMOTE_ID_KEY, android.net.vcn.persistablebundleutils.IkeIdentificationUtils.toPersistableBundle(ikeSessionParams.getRemoteIdentification()));
        persistableBundle.putPersistableBundle(LOCAL_AUTH_KEY, android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.AuthConfigUtils.toPersistableBundle(ikeSessionParams.getLocalAuthConfig()));
        persistableBundle.putPersistableBundle(REMOTE_AUTH_KEY, android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.AuthConfigUtils.toPersistableBundle(ikeSessionParams.getRemoteAuthConfig()));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = ikeSessionParams.getConfigurationRequests().iterator();
        while (it.hasNext()) {
            arrayList.add(new android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.ConfigRequest((android.net.ipsec.ike.IkeSessionParams.IkeConfigRequest) it.next()));
        }
        persistableBundle.putPersistableBundle(CONFIG_REQUESTS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(arrayList, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda3
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
            public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                return ((android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.ConfigRequest) obj).toPersistableBundle();
            }
        }));
        persistableBundle.putIntArray(RETRANS_TIMEOUTS_KEY, ikeSessionParams.getRetransmissionTimeoutsMillis());
        persistableBundle.putInt(HARD_LIFETIME_SEC_KEY, ikeSessionParams.getHardLifetimeSeconds());
        persistableBundle.putInt(SOFT_LIFETIME_SEC_KEY, ikeSessionParams.getSoftLifetimeSeconds());
        persistableBundle.putInt(DPD_DELAY_SEC_KEY, ikeSessionParams.getDpdDelaySeconds());
        persistableBundle.putInt(NATT_KEEPALIVE_DELAY_SEC_KEY, ikeSessionParams.getNattKeepAliveDelaySeconds());
        persistableBundle.putInt(IP_VERSION_KEY, ikeSessionParams.getIpVersion());
        persistableBundle.putInt(ENCAP_TYPE_KEY, ikeSessionParams.getEncapType());
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.Iterator<java.lang.Integer> it2 = IKE_OPTIONS.iterator();
        while (it2.hasNext()) {
            int intValue = it2.next().intValue();
            if (isIkeOptionValid(intValue) && ikeSessionParams.hasIkeOption(intValue)) {
                arrayList2.add(java.lang.Integer.valueOf(intValue));
            }
        }
        persistableBundle.putIntArray(IKE_OPTIONS_KEY, arrayList2.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue2;
                intValue2 = ((java.lang.Integer) obj).intValue();
                return intValue2;
            }
        }).toArray());
        return persistableBundle;
    }

    public static android.net.ipsec.ike.IkeSessionParams fromPersistableBundle(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle is null");
        android.net.ipsec.ike.IkeSessionParams.Builder builder = new android.net.ipsec.ike.IkeSessionParams.Builder();
        builder.setServerHostname(persistableBundle.getString(SERVER_HOST_NAME_KEY));
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle(SA_PROPOSALS_KEY);
        java.util.Objects.requireNonNull(persistableBundle, "SA Proposals was null");
        java.util.Iterator it = com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle2, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda0
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
            public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle3) {
                return android.net.vcn.persistablebundleutils.IkeSaProposalUtils.fromPersistableBundle(persistableBundle3);
            }
        }).iterator();
        while (it.hasNext()) {
            builder.addSaProposal((android.net.ipsec.ike.IkeSaProposal) it.next());
        }
        builder.setLocalIdentification(android.net.vcn.persistablebundleutils.IkeIdentificationUtils.fromPersistableBundle(persistableBundle.getPersistableBundle(LOCAL_ID_KEY)));
        builder.setRemoteIdentification(android.net.vcn.persistablebundleutils.IkeIdentificationUtils.fromPersistableBundle(persistableBundle.getPersistableBundle(REMOTE_ID_KEY)));
        android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.AuthConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle.getPersistableBundle(LOCAL_AUTH_KEY), persistableBundle.getPersistableBundle(REMOTE_AUTH_KEY), builder);
        builder.setRetransmissionTimeoutsMillis(persistableBundle.getIntArray(RETRANS_TIMEOUTS_KEY));
        builder.setLifetimeSeconds(persistableBundle.getInt(HARD_LIFETIME_SEC_KEY), persistableBundle.getInt(SOFT_LIFETIME_SEC_KEY));
        builder.setDpdDelaySeconds(persistableBundle.getInt(DPD_DELAY_SEC_KEY));
        builder.setNattKeepAliveDelaySeconds(persistableBundle.getInt(NATT_KEEPALIVE_DELAY_SEC_KEY));
        builder.setIpVersion(persistableBundle.getInt(IP_VERSION_KEY));
        builder.setEncapType(persistableBundle.getInt(ENCAP_TYPE_KEY));
        android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(CONFIG_REQUESTS_KEY);
        java.util.Objects.requireNonNull(persistableBundle3, "Config request list was null");
        for (android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.ConfigRequest configRequest : com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle3, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$$ExternalSyntheticLambda1
            @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
            public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle4) {
                return new android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.ConfigRequest(persistableBundle4);
            }
        })) {
            switch (configRequest.type) {
                case 1:
                    if (configRequest.address == null) {
                        builder.addPcscfServerRequest(android.system.OsConstants.AF_INET);
                        break;
                    } else {
                        builder.addPcscfServerRequest(configRequest.address);
                        break;
                    }
                case 2:
                    if (configRequest.address == null) {
                        builder.addPcscfServerRequest(android.system.OsConstants.AF_INET6);
                        break;
                    } else {
                        builder.addPcscfServerRequest(configRequest.address);
                        break;
                    }
                default:
                    throw new java.lang.IllegalArgumentException("Unrecognized config request type: " + configRequest.type);
            }
        }
        java.util.Iterator<java.lang.Integer> it2 = IKE_OPTIONS.iterator();
        while (it2.hasNext()) {
            int intValue = it2.next().intValue();
            if (isIkeOptionValid(intValue)) {
                builder.removeIkeOption(intValue);
            }
        }
        for (int i : persistableBundle.getIntArray(IKE_OPTIONS_KEY)) {
            if (isIkeOptionValid(i)) {
                builder.addIkeOption(i);
            }
        }
        return builder.build();
    }

    private static final class AuthConfigUtils {
        private static final java.lang.String AUTH_METHOD_KEY = "AUTH_METHOD_KEY";
        private static final int IKE_AUTH_METHOD_EAP = 3;
        private static final int IKE_AUTH_METHOD_PSK = 1;
        private static final int IKE_AUTH_METHOD_PUB_KEY_SIGNATURE = 2;

        private AuthConfigUtils() {
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeSessionParams.IkeAuthConfig ikeAuthConfig) {
            if (ikeAuthConfig instanceof android.net.ipsec.ike.IkeSessionParams.IkeAuthPskConfig) {
                return android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.IkeAuthPskConfigUtils.toPersistableBundle((android.net.ipsec.ike.IkeSessionParams.IkeAuthPskConfig) ikeAuthConfig, createPersistableBundle(1));
            }
            if (ikeAuthConfig instanceof android.net.ipsec.ike.IkeSessionParams.IkeAuthDigitalSignLocalConfig) {
                return android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.IkeAuthDigitalSignConfigUtils.toPersistableBundle((android.net.ipsec.ike.IkeSessionParams.IkeAuthDigitalSignLocalConfig) ikeAuthConfig, createPersistableBundle(2));
            }
            if (ikeAuthConfig instanceof android.net.ipsec.ike.IkeSessionParams.IkeAuthDigitalSignRemoteConfig) {
                return android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.IkeAuthDigitalSignConfigUtils.toPersistableBundle((android.net.ipsec.ike.IkeSessionParams.IkeAuthDigitalSignRemoteConfig) ikeAuthConfig, createPersistableBundle(2));
            }
            if (ikeAuthConfig instanceof android.net.ipsec.ike.IkeSessionParams.IkeAuthEapConfig) {
                return android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.IkeAuthEapConfigUtils.toPersistableBundle((android.net.ipsec.ike.IkeSessionParams.IkeAuthEapConfig) ikeAuthConfig, createPersistableBundle(3));
            }
            throw new java.lang.IllegalStateException("Invalid IkeAuthConfig subclass");
        }

        private static android.os.PersistableBundle createPersistableBundle(int i) {
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            persistableBundle.putInt(AUTH_METHOD_KEY, i);
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.net.ipsec.ike.IkeSessionParams.Builder builder) {
            java.util.Objects.requireNonNull(persistableBundle, "localAuthBundle was null");
            java.util.Objects.requireNonNull(persistableBundle2, "remoteAuthBundle was null");
            int i = persistableBundle.getInt(AUTH_METHOD_KEY);
            int i2 = persistableBundle2.getInt(AUTH_METHOD_KEY);
            switch (i) {
                case 1:
                    if (i2 != 1) {
                        throw new java.lang.IllegalArgumentException("Expect remote auth method to be PSK based, but was " + i2);
                    }
                    android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.IkeAuthPskConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle, persistableBundle2, builder);
                    return;
                case 2:
                    if (i2 != 2) {
                        throw new java.lang.IllegalArgumentException("Expect remote auth method to be digital signature based, but was " + i2);
                    }
                    android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.IkeAuthDigitalSignConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle, persistableBundle2, builder);
                    return;
                case 3:
                    if (i2 != 2) {
                        throw new java.lang.IllegalArgumentException("When using EAP for local authentication, expect remote auth method to be digital signature based, but was " + i2);
                    }
                    android.net.vcn.persistablebundleutils.IkeSessionParamsUtils.IkeAuthEapConfigUtils.setBuilderByReadingPersistableBundle(persistableBundle, persistableBundle2, builder);
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid EAP method type " + i);
            }
        }
    }

    private static final class IkeAuthPskConfigUtils {
        private static final java.lang.String PSK_KEY = "PSK_KEY";

        private IkeAuthPskConfigUtils() {
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeSessionParams.IkeAuthPskConfig ikeAuthPskConfig, android.os.PersistableBundle persistableBundle) {
            persistableBundle.putPersistableBundle(PSK_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray(ikeAuthPskConfig.getPsk()));
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.net.ipsec.ike.IkeSessionParams.Builder builder) {
            java.util.Objects.requireNonNull(persistableBundle, "localAuthBundle was null");
            java.util.Objects.requireNonNull(persistableBundle2, "remoteAuthBundle was null");
            android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(PSK_KEY);
            android.os.PersistableBundle persistableBundle4 = persistableBundle2.getPersistableBundle(PSK_KEY);
            java.util.Objects.requireNonNull(persistableBundle, "Local PSK was null");
            java.util.Objects.requireNonNull(persistableBundle2, "Remote PSK was null");
            byte[] byteArray = com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle3);
            if (!java.util.Arrays.equals(byteArray, com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle4))) {
                throw new java.lang.IllegalArgumentException("Local PSK and remote PSK are different");
            }
            builder.setAuthPsk(byteArray);
        }
    }

    private static class IkeAuthDigitalSignConfigUtils {
        private static final java.lang.String END_CERT_KEY = "END_CERT_KEY";
        private static final java.lang.String INTERMEDIATE_CERTS_KEY = "INTERMEDIATE_CERTS_KEY";
        private static final java.lang.String PRIVATE_KEY_KEY = "PRIVATE_KEY_KEY";
        private static final java.lang.String TRUST_CERT_KEY = "TRUST_CERT_KEY";

        private IkeAuthDigitalSignConfigUtils() {
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeSessionParams.IkeAuthDigitalSignLocalConfig ikeAuthDigitalSignLocalConfig, android.os.PersistableBundle persistableBundle) {
            try {
                persistableBundle.putPersistableBundle(END_CERT_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray(ikeAuthDigitalSignLocalConfig.getClientEndCertificate().getEncoded()));
                java.util.List<java.security.cert.X509Certificate> intermediateCertificates = ikeAuthDigitalSignLocalConfig.getIntermediateCertificates();
                java.util.ArrayList arrayList = new java.util.ArrayList(intermediateCertificates.size());
                java.util.Iterator<java.security.cert.X509Certificate> it = intermediateCertificates.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getEncoded());
                }
                persistableBundle.putPersistableBundle(INTERMEDIATE_CERTS_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromList(arrayList, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$IkeAuthDigitalSignConfigUtils$$ExternalSyntheticLambda1
                    @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Serializer
                    public final android.os.PersistableBundle toPersistableBundle(java.lang.Object obj) {
                        return com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray((byte[]) obj);
                    }
                }));
                persistableBundle.putPersistableBundle(PRIVATE_KEY_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray(ikeAuthDigitalSignLocalConfig.getPrivateKey().getEncoded()));
                return persistableBundle;
            } catch (java.security.cert.CertificateEncodingException e) {
                throw new java.lang.IllegalArgumentException("Fail to encode certificate");
            }
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeSessionParams.IkeAuthDigitalSignRemoteConfig ikeAuthDigitalSignRemoteConfig, android.os.PersistableBundle persistableBundle) {
            try {
                java.security.cert.X509Certificate remoteCaCert = ikeAuthDigitalSignRemoteConfig.getRemoteCaCert();
                if (remoteCaCert != null) {
                    persistableBundle.putPersistableBundle(TRUST_CERT_KEY, com.android.server.vcn.repackaged.util.PersistableBundleUtils.fromByteArray(remoteCaCert.getEncoded()));
                }
                return persistableBundle;
            } catch (java.security.cert.CertificateEncodingException e) {
                throw new java.lang.IllegalArgumentException("Fail to encode the certificate");
            }
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.net.ipsec.ike.IkeSessionParams.Builder builder) {
            java.security.cert.X509Certificate x509Certificate;
            java.util.Objects.requireNonNull(persistableBundle, "localAuthBundle was null");
            java.util.Objects.requireNonNull(persistableBundle2, "remoteAuthBundle was null");
            android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(END_CERT_KEY);
            java.util.Objects.requireNonNull(persistableBundle3, "End cert was null");
            java.security.cert.X509Certificate certificateFromByteArray = android.net.vcn.persistablebundleutils.CertUtils.certificateFromByteArray(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle3));
            android.os.PersistableBundle persistableBundle4 = persistableBundle.getPersistableBundle(INTERMEDIATE_CERTS_KEY);
            java.util.Objects.requireNonNull(persistableBundle4, "Intermediate certs was null");
            java.util.List list = com.android.server.vcn.repackaged.util.PersistableBundleUtils.toList(persistableBundle4, new com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer() { // from class: android.net.vcn.persistablebundleutils.IkeSessionParamsUtils$IkeAuthDigitalSignConfigUtils$$ExternalSyntheticLambda0
                @Override // com.android.server.vcn.repackaged.util.PersistableBundleUtils.Deserializer
                public final java.lang.Object fromPersistableBundle(android.os.PersistableBundle persistableBundle5) {
                    return com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle5);
                }
            });
            java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
            java.util.Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(android.net.vcn.persistablebundleutils.CertUtils.certificateFromByteArray((byte[]) it.next()));
            }
            android.os.PersistableBundle persistableBundle5 = persistableBundle.getPersistableBundle(PRIVATE_KEY_KEY);
            java.util.Objects.requireNonNull(persistableBundle5, "PrivateKey bundle was null");
            java.security.interfaces.RSAPrivateKey privateKeyFromByteArray = android.net.vcn.persistablebundleutils.CertUtils.privateKeyFromByteArray(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle5));
            android.os.PersistableBundle persistableBundle6 = persistableBundle2.getPersistableBundle(TRUST_CERT_KEY);
            if (persistableBundle6 == null) {
                x509Certificate = null;
            } else {
                x509Certificate = android.net.vcn.persistablebundleutils.CertUtils.certificateFromByteArray(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle6));
            }
            builder.setAuthDigitalSignature(x509Certificate, certificateFromByteArray, arrayList, privateKeyFromByteArray);
        }
    }

    private static final class IkeAuthEapConfigUtils {
        private static final java.lang.String EAP_CONFIG_KEY = "EAP_CONFIG_KEY";

        private IkeAuthEapConfigUtils() {
        }

        public static android.os.PersistableBundle toPersistableBundle(android.net.ipsec.ike.IkeSessionParams.IkeAuthEapConfig ikeAuthEapConfig, android.os.PersistableBundle persistableBundle) {
            persistableBundle.putPersistableBundle(EAP_CONFIG_KEY, android.net.vcn.persistablebundleutils.EapSessionConfigUtils.toPersistableBundle(ikeAuthEapConfig.getEapConfig()));
            return persistableBundle;
        }

        public static void setBuilderByReadingPersistableBundle(android.os.PersistableBundle persistableBundle, android.os.PersistableBundle persistableBundle2, android.net.ipsec.ike.IkeSessionParams.Builder builder) {
            java.security.cert.X509Certificate x509Certificate;
            android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(EAP_CONFIG_KEY);
            java.util.Objects.requireNonNull(persistableBundle3, "EAP Config was null");
            android.net.eap.EapSessionConfig fromPersistableBundle = android.net.vcn.persistablebundleutils.EapSessionConfigUtils.fromPersistableBundle(persistableBundle3);
            android.os.PersistableBundle persistableBundle4 = persistableBundle2.getPersistableBundle("TRUST_CERT_KEY");
            if (persistableBundle4 == null) {
                x509Certificate = null;
            } else {
                x509Certificate = android.net.vcn.persistablebundleutils.CertUtils.certificateFromByteArray(com.android.server.vcn.repackaged.util.PersistableBundleUtils.toByteArray(persistableBundle4));
            }
            builder.setAuthEap(x509Certificate, fromPersistableBundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ConfigRequest {
        private static final java.lang.String ADDRESS_KEY = "address";
        private static final int IPV4_P_CSCF_ADDRESS = 1;
        private static final int IPV6_P_CSCF_ADDRESS = 2;
        private static final java.lang.String TYPE_KEY = "type";
        public final java.net.InetAddress address;
        public final int type;

        ConfigRequest(android.net.ipsec.ike.IkeSessionParams.IkeConfigRequest ikeConfigRequest) {
            if (ikeConfigRequest instanceof android.net.ipsec.ike.IkeSessionParams.ConfigRequestIpv4PcscfServer) {
                this.type = 1;
                this.address = ((android.net.ipsec.ike.IkeSessionParams.ConfigRequestIpv4PcscfServer) ikeConfigRequest).getAddress();
            } else {
                if (ikeConfigRequest instanceof android.net.ipsec.ike.IkeSessionParams.ConfigRequestIpv6PcscfServer) {
                    this.type = 2;
                    this.address = ((android.net.ipsec.ike.IkeSessionParams.ConfigRequestIpv6PcscfServer) ikeConfigRequest).getAddress();
                    return;
                }
                throw new java.lang.IllegalStateException("Unknown TunnelModeChildConfigRequest");
            }
        }

        ConfigRequest(android.os.PersistableBundle persistableBundle) {
            java.util.Objects.requireNonNull(persistableBundle, "PersistableBundle was null");
            this.type = persistableBundle.getInt("type");
            java.lang.String string = persistableBundle.getString("address");
            if (string == null) {
                this.address = null;
            } else {
                this.address = android.net.InetAddresses.parseNumericAddress(string);
            }
        }

        public android.os.PersistableBundle toPersistableBundle() {
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            persistableBundle.putInt("type", this.type);
            if (this.address != null) {
                persistableBundle.putString("address", this.address.getHostAddress());
            }
            return persistableBundle;
        }
    }
}
