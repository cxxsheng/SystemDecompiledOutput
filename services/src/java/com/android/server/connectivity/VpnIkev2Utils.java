package com.android.server.connectivity;

/* loaded from: classes.dex */
public class VpnIkev2Utils {
    private static final java.lang.String TAG = com.android.server.connectivity.VpnIkev2Utils.class.getSimpleName();

    static android.net.ipsec.ike.IkeSessionParams.Builder makeIkeSessionParamsBuilder(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.net.Ikev2VpnProfile ikev2VpnProfile, @android.annotation.NonNull android.net.Network network) {
        android.net.ipsec.ike.IkeIdentification parseIkeIdentification = parseIkeIdentification(ikev2VpnProfile.getUserIdentity());
        android.net.ipsec.ike.IkeSessionParams.Builder remoteIdentification = new android.net.ipsec.ike.IkeSessionParams.Builder(context).setServerHostname(ikev2VpnProfile.getServerAddr()).setNetwork(network).addIkeOption(2).setLocalIdentification(parseIkeIdentification).setRemoteIdentification(parseIkeIdentification(ikev2VpnProfile.getServerAddr()));
        setIkeAuth(ikev2VpnProfile, remoteIdentification);
        java.util.Iterator<android.net.ipsec.ike.IkeSaProposal> it = getIkeSaProposals().iterator();
        while (it.hasNext()) {
            remoteIdentification.addSaProposal(it.next());
        }
        return remoteIdentification;
    }

    static android.net.ipsec.ike.ChildSessionParams buildChildSessionParams(java.util.List<java.lang.String> list) {
        android.net.ipsec.ike.TunnelModeChildSessionParams.Builder builder = new android.net.ipsec.ike.TunnelModeChildSessionParams.Builder();
        java.util.Iterator<android.net.ipsec.ike.ChildSaProposal> it = getChildSaProposals(list).iterator();
        while (it.hasNext()) {
            builder.addSaProposal(it.next());
        }
        builder.addInternalAddressRequest(android.system.OsConstants.AF_INET);
        builder.addInternalAddressRequest(android.system.OsConstants.AF_INET6);
        builder.addInternalDnsServerRequest(android.system.OsConstants.AF_INET);
        builder.addInternalDnsServerRequest(android.system.OsConstants.AF_INET6);
        return builder.build();
    }

    private static void setIkeAuth(@android.annotation.NonNull android.net.Ikev2VpnProfile ikev2VpnProfile, @android.annotation.NonNull android.net.ipsec.ike.IkeSessionParams.Builder builder) {
        switch (ikev2VpnProfile.getType()) {
            case 6:
                builder.setAuthEap(ikev2VpnProfile.getServerRootCaCert(), new android.net.eap.EapSessionConfig.Builder().setEapMsChapV2Config(ikev2VpnProfile.getUsername(), ikev2VpnProfile.getPassword()).build());
                return;
            case 7:
                builder.setAuthPsk(ikev2VpnProfile.getPresharedKey());
                return;
            case 8:
                builder.setAuthDigitalSignature(ikev2VpnProfile.getServerRootCaCert(), ikev2VpnProfile.getUserCert(), ikev2VpnProfile.getRsaPrivateKey());
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown auth method set");
        }
    }

    private static java.util.List<android.net.ipsec.ike.IkeSaProposal> getIkeSaProposals() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.net.ipsec.ike.IkeSaProposal.Builder builder = new android.net.ipsec.ike.IkeSaProposal.Builder();
        builder.addEncryptionAlgorithm(13, 256);
        builder.addEncryptionAlgorithm(12, 256);
        builder.addEncryptionAlgorithm(13, 192);
        builder.addEncryptionAlgorithm(12, 192);
        builder.addEncryptionAlgorithm(13, 128);
        builder.addEncryptionAlgorithm(12, 128);
        builder.addIntegrityAlgorithm(14);
        builder.addIntegrityAlgorithm(13);
        builder.addIntegrityAlgorithm(12);
        builder.addIntegrityAlgorithm(5);
        builder.addIntegrityAlgorithm(8);
        android.net.ipsec.ike.IkeSaProposal.Builder builder2 = new android.net.ipsec.ike.IkeSaProposal.Builder();
        builder2.addEncryptionAlgorithm(28, 0);
        builder2.addEncryptionAlgorithm(20, 256);
        builder2.addEncryptionAlgorithm(19, 256);
        builder2.addEncryptionAlgorithm(18, 256);
        builder2.addEncryptionAlgorithm(20, 192);
        builder2.addEncryptionAlgorithm(19, 192);
        builder2.addEncryptionAlgorithm(18, 192);
        builder2.addEncryptionAlgorithm(20, 128);
        builder2.addEncryptionAlgorithm(19, 128);
        builder2.addEncryptionAlgorithm(18, 128);
        for (android.net.ipsec.ike.IkeSaProposal.Builder builder3 : java.util.Arrays.asList(builder, builder2)) {
            builder3.addDhGroup(16);
            builder3.addDhGroup(31);
            builder3.addDhGroup(15);
            builder3.addDhGroup(14);
            builder3.addPseudorandomFunction(7);
            builder3.addPseudorandomFunction(6);
            builder3.addPseudorandomFunction(5);
            builder3.addPseudorandomFunction(4);
            builder3.addPseudorandomFunction(8);
            builder3.addPseudorandomFunction(2);
        }
        arrayList.add(builder.build());
        arrayList.add(builder2.build());
        return arrayList;
    }

    private static java.util.List<android.net.ipsec.ike.ChildSaProposal> getChildSaProposals(java.util.List<java.lang.String> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List asList = java.util.Arrays.asList(256, 192, 128);
        if (android.net.Ikev2VpnProfile.hasNormalModeAlgorithms(list)) {
            android.net.ipsec.ike.ChildSaProposal.Builder builder = new android.net.ipsec.ike.ChildSaProposal.Builder();
            java.util.Iterator it = asList.iterator();
            while (it.hasNext()) {
                int intValue = ((java.lang.Integer) it.next()).intValue();
                if (list.contains("rfc3686(ctr(aes))")) {
                    builder.addEncryptionAlgorithm(13, intValue);
                }
                if (list.contains("cbc(aes)")) {
                    builder.addEncryptionAlgorithm(12, intValue);
                }
            }
            if (list.contains("hmac(sha512)")) {
                builder.addIntegrityAlgorithm(14);
            }
            if (list.contains("hmac(sha384)")) {
                builder.addIntegrityAlgorithm(13);
            }
            if (list.contains("hmac(sha256)")) {
                builder.addIntegrityAlgorithm(12);
            }
            if (list.contains("xcbc(aes)")) {
                builder.addIntegrityAlgorithm(5);
            }
            if (list.contains("cmac(aes)")) {
                builder.addIntegrityAlgorithm(8);
            }
            if (builder.build().getIntegrityAlgorithms().isEmpty()) {
                android.util.Log.wtf(TAG, "Missing integrity algorithm when buildling Child SA proposal");
            } else {
                arrayList.add(builder.build());
            }
        }
        if (android.net.Ikev2VpnProfile.hasAeadAlgorithms(list)) {
            android.net.ipsec.ike.ChildSaProposal.Builder builder2 = new android.net.ipsec.ike.ChildSaProposal.Builder();
            if (list.contains("rfc7539esp(chacha20,poly1305)")) {
                builder2.addEncryptionAlgorithm(28, 0);
            }
            if (list.contains("rfc4106(gcm(aes))")) {
                builder2.addEncryptionAlgorithm(20, 256);
                builder2.addEncryptionAlgorithm(19, 256);
                builder2.addEncryptionAlgorithm(18, 256);
                builder2.addEncryptionAlgorithm(20, 192);
                builder2.addEncryptionAlgorithm(19, 192);
                builder2.addEncryptionAlgorithm(18, 192);
                builder2.addEncryptionAlgorithm(20, 128);
                builder2.addEncryptionAlgorithm(19, 128);
                builder2.addEncryptionAlgorithm(18, 128);
            }
            arrayList.add(builder2.build());
        }
        return arrayList;
    }

    static class IkeSessionCallbackImpl implements android.net.ipsec.ike.IkeSessionCallback {
        private final com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback mCallback;
        private final java.lang.String mTag;
        private final int mToken;

        IkeSessionCallbackImpl(java.lang.String str, com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback, int i) {
            this.mTag = str;
            this.mCallback = ikeV2VpnRunnerCallback;
            this.mToken = i;
        }

        @Override // android.net.ipsec.ike.IkeSessionCallback
        public void onOpened(@android.annotation.NonNull android.net.ipsec.ike.IkeSessionConfiguration ikeSessionConfiguration) {
            android.util.Log.d(this.mTag, "IkeOpened for token " + this.mToken);
            this.mCallback.onIkeOpened(this.mToken, ikeSessionConfiguration);
        }

        @Override // android.net.ipsec.ike.IkeSessionCallback
        public void onClosed() {
            android.util.Log.d(this.mTag, "IkeClosed for token " + this.mToken);
            this.mCallback.onSessionLost(this.mToken, null);
        }

        public void onClosedExceptionally(@android.annotation.NonNull android.net.ipsec.ike.exceptions.IkeException ikeException) {
            android.util.Log.d(this.mTag, "IkeClosedExceptionally for token " + this.mToken, ikeException);
            this.mCallback.onSessionLost(this.mToken, ikeException);
        }

        public void onError(@android.annotation.NonNull android.net.ipsec.ike.exceptions.IkeProtocolException ikeProtocolException) {
            android.util.Log.d(this.mTag, "IkeError for token " + this.mToken, ikeProtocolException);
        }

        public void onIkeSessionConnectionInfoChanged(@android.annotation.NonNull android.net.ipsec.ike.IkeSessionConnectionInfo ikeSessionConnectionInfo) {
            android.util.Log.d(this.mTag, "onIkeSessionConnectionInfoChanged for token " + this.mToken);
            this.mCallback.onIkeConnectionInfoChanged(this.mToken, ikeSessionConnectionInfo);
        }
    }

    static class ChildSessionCallbackImpl implements android.net.ipsec.ike.ChildSessionCallback {
        private final com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback mCallback;
        private final java.lang.String mTag;
        private final int mToken;

        ChildSessionCallbackImpl(java.lang.String str, com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback, int i) {
            this.mTag = str;
            this.mCallback = ikeV2VpnRunnerCallback;
            this.mToken = i;
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public void onOpened(@android.annotation.NonNull android.net.ipsec.ike.ChildSessionConfiguration childSessionConfiguration) {
            android.util.Log.d(this.mTag, "ChildOpened for token " + this.mToken);
            this.mCallback.onChildOpened(this.mToken, childSessionConfiguration);
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public void onClosed() {
            android.util.Log.d(this.mTag, "ChildClosed for token " + this.mToken);
            this.mCallback.onSessionLost(this.mToken, null);
        }

        public void onClosedExceptionally(@android.annotation.NonNull android.net.ipsec.ike.exceptions.IkeException ikeException) {
            android.util.Log.d(this.mTag, "ChildClosedExceptionally for token " + this.mToken, ikeException);
            this.mCallback.onSessionLost(this.mToken, ikeException);
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public void onIpSecTransformCreated(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform, int i) {
            android.util.Log.d(this.mTag, "ChildTransformCreated; Direction: " + i + "; token " + this.mToken);
            this.mCallback.onChildTransformCreated(this.mToken, ipSecTransform, i);
        }

        @Override // android.net.ipsec.ike.ChildSessionCallback
        public void onIpSecTransformDeleted(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform, int i) {
            android.util.Log.d(this.mTag, "ChildTransformDeleted; Direction: " + i + "; for token " + this.mToken);
        }

        public void onIpSecTransformsMigrated(@android.annotation.NonNull android.net.IpSecTransform ipSecTransform, @android.annotation.NonNull android.net.IpSecTransform ipSecTransform2) {
            android.util.Log.d(this.mTag, "ChildTransformsMigrated; token " + this.mToken);
            this.mCallback.onChildMigrated(this.mToken, ipSecTransform, ipSecTransform2);
        }
    }

    static class Ikev2VpnNetworkCallback extends android.net.ConnectivityManager.NetworkCallback {
        private final com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final java.lang.String mTag;

        Ikev2VpnNetworkCallback(java.lang.String str, com.android.server.connectivity.Vpn.IkeV2VpnRunnerCallback ikeV2VpnRunnerCallback, java.util.concurrent.Executor executor) {
            this.mTag = str;
            this.mCallback = ikeV2VpnRunnerCallback;
            this.mExecutor = executor;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(@android.annotation.NonNull final android.net.Network network) {
            android.util.Log.d(this.mTag, "onAvailable called for network: " + network);
            this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.connectivity.VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.connectivity.VpnIkev2Utils.Ikev2VpnNetworkCallback.this.lambda$onAvailable$0(network);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAvailable$0(android.net.Network network) {
            this.mCallback.onDefaultNetworkChanged(network);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull final android.net.NetworkCapabilities networkCapabilities) {
            android.util.Log.d(this.mTag, "NC changed for net " + network + " : " + networkCapabilities);
            this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.connectivity.VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.connectivity.VpnIkev2Utils.Ikev2VpnNetworkCallback.this.lambda$onCapabilitiesChanged$1(networkCapabilities);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCapabilitiesChanged$1(android.net.NetworkCapabilities networkCapabilities) {
            this.mCallback.onDefaultNetworkCapabilitiesChanged(networkCapabilities);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull final android.net.LinkProperties linkProperties) {
            android.util.Log.d(this.mTag, "LP changed for net " + network + " : " + linkProperties);
            this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.connectivity.VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.connectivity.VpnIkev2Utils.Ikev2VpnNetworkCallback.this.lambda$onLinkPropertiesChanged$2(linkProperties);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLinkPropertiesChanged$2(android.net.LinkProperties linkProperties) {
            this.mCallback.onDefaultNetworkLinkPropertiesChanged(linkProperties);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(@android.annotation.NonNull final android.net.Network network) {
            android.util.Log.d(this.mTag, "onLost called for network: " + network);
            this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.connectivity.VpnIkev2Utils$Ikev2VpnNetworkCallback$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.connectivity.VpnIkev2Utils.Ikev2VpnNetworkCallback.this.lambda$onLost$3(network);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLost$3(android.net.Network network) {
            this.mCallback.onDefaultNetworkLost(network);
        }
    }

    private static android.net.ipsec.ike.IkeIdentification parseIkeIdentification(@android.annotation.NonNull java.lang.String str) {
        if (str.contains("@")) {
            if (str.startsWith("@#")) {
                return new android.net.ipsec.ike.IkeKeyIdIdentification(com.android.internal.util.HexDump.hexStringToByteArray(str.substring(2)));
            }
            if (str.startsWith("@@")) {
                return new android.net.ipsec.ike.IkeRfc822AddrIdentification(str.substring(2));
            }
            if (str.startsWith("@")) {
                return new android.net.ipsec.ike.IkeFqdnIdentification(str.substring(1));
            }
            return new android.net.ipsec.ike.IkeRfc822AddrIdentification(str);
        }
        if (android.net.InetAddresses.isNumericAddress(str)) {
            java.net.InetAddress parseNumericAddress = android.net.InetAddresses.parseNumericAddress(str);
            if (parseNumericAddress instanceof java.net.Inet4Address) {
                return new android.net.ipsec.ike.IkeIpv4AddrIdentification((java.net.Inet4Address) parseNumericAddress);
            }
            if (parseNumericAddress instanceof java.net.Inet6Address) {
                return new android.net.ipsec.ike.IkeIpv6AddrIdentification((java.net.Inet6Address) parseNumericAddress);
            }
            throw new java.lang.IllegalArgumentException("IP version not supported");
        }
        if (str.contains(":")) {
            return new android.net.ipsec.ike.IkeKeyIdIdentification(str.getBytes());
        }
        return new android.net.ipsec.ike.IkeFqdnIdentification(str);
    }

    static java.util.Collection<android.net.RouteInfo> getRoutesFromTrafficSelectors(java.util.List<android.net.ipsec.ike.IkeTrafficSelector> list) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (android.net.ipsec.ike.IkeTrafficSelector ikeTrafficSelector : list) {
            java.util.Iterator it = new com.android.net.module.util.IpRange(ikeTrafficSelector.startingAddress, ikeTrafficSelector.endingAddress).asIpPrefixes().iterator();
            while (it.hasNext()) {
                hashSet.add(new android.net.RouteInfo((android.net.IpPrefix) it.next(), null, null, 1));
            }
        }
        return hashSet;
    }
}
