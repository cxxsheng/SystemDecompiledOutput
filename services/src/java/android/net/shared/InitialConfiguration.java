package android.net.shared;

/* loaded from: classes.dex */
public class InitialConfiguration {
    public static final java.net.InetAddress INET6_ANY = android.net.InetAddresses.parseNumericAddress("::");
    private static final int RFC6177_MIN_PREFIX_LENGTH = 48;
    private static final int RFC7421_PREFIX_LENGTH = 64;
    public final java.util.Set<android.net.LinkAddress> ipAddresses = new java.util.HashSet();
    public final java.util.Set<android.net.IpPrefix> directlyConnectedRoutes = new java.util.HashSet();
    public final java.util.Set<java.net.InetAddress> dnsServers = new java.util.HashSet();

    public static android.net.shared.InitialConfiguration copy(android.net.shared.InitialConfiguration initialConfiguration) {
        if (initialConfiguration == null) {
            return null;
        }
        android.net.shared.InitialConfiguration initialConfiguration2 = new android.net.shared.InitialConfiguration();
        initialConfiguration2.ipAddresses.addAll(initialConfiguration.ipAddresses);
        initialConfiguration2.directlyConnectedRoutes.addAll(initialConfiguration.directlyConnectedRoutes);
        initialConfiguration2.dnsServers.addAll(initialConfiguration.dnsServers);
        return initialConfiguration2;
    }

    public java.lang.String toString() {
        return java.lang.String.format("InitialConfiguration(IPs: {%s}, prefixes: {%s}, DNS: {%s})", android.text.TextUtils.join(", ", this.ipAddresses), android.text.TextUtils.join(", ", this.directlyConnectedRoutes), android.text.TextUtils.join(", ", this.dnsServers));
    }

    public boolean isValid() {
        if (this.ipAddresses.isEmpty()) {
            return false;
        }
        for (final android.net.LinkAddress linkAddress : this.ipAddresses) {
            if (!any(this.directlyConnectedRoutes, new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$isValid$0;
                    lambda$isValid$0 = android.net.shared.InitialConfiguration.lambda$isValid$0(linkAddress, (android.net.IpPrefix) obj);
                    return lambda$isValid$0;
                }
            })) {
                return false;
            }
        }
        for (final java.net.InetAddress inetAddress : this.dnsServers) {
            if (!any(this.directlyConnectedRoutes, new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$isValid$1;
                    lambda$isValid$1 = android.net.shared.InitialConfiguration.lambda$isValid$1(inetAddress, (android.net.IpPrefix) obj);
                    return lambda$isValid$1;
                }
            })) {
                return false;
            }
        }
        if (any(this.ipAddresses, not(new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isPrefixLengthCompliant;
                isPrefixLengthCompliant = android.net.shared.InitialConfiguration.isPrefixLengthCompliant((android.net.LinkAddress) obj);
                return isPrefixLengthCompliant;
            }
        }))) {
            return false;
        }
        return ((any(this.directlyConnectedRoutes, new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isIPv6DefaultRoute;
                isIPv6DefaultRoute = android.net.shared.InitialConfiguration.isIPv6DefaultRoute((android.net.IpPrefix) obj);
                return isIPv6DefaultRoute;
            }
        }) && all(this.ipAddresses, not(new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isIPv6GUA;
                isIPv6GUA = android.net.shared.InitialConfiguration.isIPv6GUA((android.net.LinkAddress) obj);
                return isIPv6GUA;
            }
        }))) || any(this.directlyConnectedRoutes, not(new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isPrefixLengthCompliant;
                isPrefixLengthCompliant = android.net.shared.InitialConfiguration.isPrefixLengthCompliant((android.net.IpPrefix) obj);
                return isPrefixLengthCompliant;
            }
        })) || this.ipAddresses.stream().filter(new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isIPv4;
                isIPv4 = android.net.shared.InitialConfiguration.isIPv4((android.net.LinkAddress) obj);
                return isIPv4;
            }
        }).count() > 1) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isValid$0(android.net.LinkAddress linkAddress, android.net.IpPrefix ipPrefix) {
        return ipPrefix.contains(linkAddress.getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isValid$1(java.net.InetAddress inetAddress, android.net.IpPrefix ipPrefix) {
        return ipPrefix.contains(inetAddress);
    }

    public boolean isProvisionedBy(java.util.List<android.net.LinkAddress> list, java.util.List<android.net.RouteInfo> list2) {
        if (this.ipAddresses.isEmpty()) {
            return false;
        }
        for (final android.net.LinkAddress linkAddress : this.ipAddresses) {
            if (!any(list, new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean isSameAddressAs;
                    isSameAddressAs = linkAddress.isSameAddressAs((android.net.LinkAddress) obj);
                    return isSameAddressAs;
                }
            })) {
                return false;
            }
        }
        if (list2 != null) {
            for (final android.net.IpPrefix ipPrefix : this.directlyConnectedRoutes) {
                if (!any(list2, new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$isProvisionedBy$3;
                        lambda$isProvisionedBy$3 = android.net.shared.InitialConfiguration.lambda$isProvisionedBy$3(ipPrefix, (android.net.RouteInfo) obj);
                        return lambda$isProvisionedBy$3;
                    }
                })) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isProvisionedBy$3(android.net.IpPrefix ipPrefix, android.net.RouteInfo routeInfo) {
        return isDirectlyConnectedRoute(routeInfo, ipPrefix);
    }

    public android.net.InitialConfigurationParcelable toStableParcelable() {
        android.net.InitialConfigurationParcelable initialConfigurationParcelable = new android.net.InitialConfigurationParcelable();
        initialConfigurationParcelable.ipAddresses = (android.net.LinkAddress[]) this.ipAddresses.toArray(new android.net.LinkAddress[0]);
        initialConfigurationParcelable.directlyConnectedRoutes = (android.net.IpPrefix[]) this.directlyConnectedRoutes.toArray(new android.net.IpPrefix[0]);
        initialConfigurationParcelable.dnsServers = (java.lang.String[]) android.net.shared.ParcelableUtil.toParcelableArray(this.dnsServers, new android.net.shared.InitialConfiguration$$ExternalSyntheticLambda11(), java.lang.String.class);
        return initialConfigurationParcelable;
    }

    public static android.net.shared.InitialConfiguration fromStableParcelable(android.net.InitialConfigurationParcelable initialConfigurationParcelable) {
        if (initialConfigurationParcelable == null) {
            return null;
        }
        android.net.shared.InitialConfiguration initialConfiguration = new android.net.shared.InitialConfiguration();
        initialConfiguration.ipAddresses.addAll(java.util.Arrays.asList(initialConfigurationParcelable.ipAddresses));
        initialConfiguration.directlyConnectedRoutes.addAll(java.util.Arrays.asList(initialConfigurationParcelable.directlyConnectedRoutes));
        initialConfiguration.dnsServers.addAll(android.net.shared.ParcelableUtil.fromParcelableArray(initialConfigurationParcelable.dnsServers, new android.net.shared.InitialConfiguration$$ExternalSyntheticLambda8()));
        return initialConfiguration;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.shared.InitialConfiguration)) {
            return false;
        }
        android.net.shared.InitialConfiguration initialConfiguration = (android.net.shared.InitialConfiguration) obj;
        return this.ipAddresses.equals(initialConfiguration.ipAddresses) && this.directlyConnectedRoutes.equals(initialConfiguration.directlyConnectedRoutes) && this.dnsServers.equals(initialConfiguration.dnsServers);
    }

    private static boolean isDirectlyConnectedRoute(android.net.RouteInfo routeInfo, android.net.IpPrefix ipPrefix) {
        return !routeInfo.hasGateway() && routeInfo.getType() == 1 && ipPrefix.equals(routeInfo.getDestination());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPrefixLengthCompliant(android.net.LinkAddress linkAddress) {
        return isIPv4(linkAddress) || isCompliantIPv6PrefixLength(linkAddress.getPrefixLength());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPrefixLengthCompliant(android.net.IpPrefix ipPrefix) {
        return isIPv4(ipPrefix) || isCompliantIPv6PrefixLength(ipPrefix.getPrefixLength());
    }

    private static boolean isCompliantIPv6PrefixLength(int i) {
        return 48 <= i && i <= 64;
    }

    private static boolean isIPv4(android.net.IpPrefix ipPrefix) {
        return ipPrefix.getAddress() instanceof java.net.Inet4Address;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isIPv4(android.net.LinkAddress linkAddress) {
        return linkAddress.getAddress() instanceof java.net.Inet4Address;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isIPv6DefaultRoute(android.net.IpPrefix ipPrefix) {
        return ipPrefix.getAddress().equals(INET6_ANY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isIPv6GUA(android.net.LinkAddress linkAddress) {
        return linkAddress.isIpv6() && linkAddress.isGlobalPreferred();
    }

    public static <T> boolean any(java.lang.Iterable<T> iterable, java.util.function.Predicate<T> predicate) {
        java.util.Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean all(java.lang.Iterable<T> iterable, java.util.function.Predicate<T> predicate) {
        return !any(iterable, not(predicate));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$not$4(java.util.function.Predicate predicate, java.lang.Object obj) {
        return !predicate.test(obj);
    }

    public static <T> java.util.function.Predicate<T> not(final java.util.function.Predicate<T> predicate) {
        return new java.util.function.Predicate() { // from class: android.net.shared.InitialConfiguration$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$not$4;
                lambda$not$4 = android.net.shared.InitialConfiguration.lambda$not$4(predicate, obj);
                return lambda$not$4;
            }
        };
    }
}
