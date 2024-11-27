package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class LinkPropertiesUtils {

    public static class CompareResult<T> {
        public final java.util.List<T> removed = new java.util.ArrayList();
        public final java.util.List<T> added = new java.util.ArrayList();

        public CompareResult() {
        }

        public CompareResult(java.util.Collection<T> collection, java.util.Collection<T> collection2) {
            if (collection != null) {
                this.removed.addAll(collection);
            }
            if (collection2 != null) {
                for (T t : collection2) {
                    if (!this.removed.remove(t)) {
                        this.added.add(t);
                    }
                }
            }
        }

        public java.lang.String toString() {
            return "removed=[" + android.text.TextUtils.join(",", this.removed) + "] added=[" + android.text.TextUtils.join(",", this.added) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class CompareOrUpdateResult<K, T> {
        public final java.util.List<T> added = new java.util.ArrayList();
        public final java.util.List<T> removed = new java.util.ArrayList();
        public final java.util.List<T> updated = new java.util.ArrayList();

        public CompareOrUpdateResult(java.util.Collection<T> collection, java.util.Collection<T> collection2, java.util.function.Function<T, K> function) {
            java.util.HashMap hashMap = new java.util.HashMap();
            if (collection != null) {
                for (T t : collection) {
                    hashMap.put(function.apply(t), t);
                }
            }
            if (collection2 != null) {
                for (T t2 : collection2) {
                    java.lang.Object remove = hashMap.remove(function.apply(t2));
                    if (remove != null) {
                        if (!remove.equals(t2)) {
                            this.updated.add(t2);
                        }
                    } else {
                        this.added.add(t2);
                    }
                }
            }
            this.removed.addAll(hashMap.values());
        }

        public java.lang.String toString() {
            return "removed=[" + android.text.TextUtils.join(",", this.removed) + "] added=[" + android.text.TextUtils.join(",", this.added) + "] updated=[" + android.text.TextUtils.join(",", this.updated) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static com.android.net.module.util.LinkPropertiesUtils.CompareResult<android.net.LinkAddress> compareAddresses(android.net.LinkProperties linkProperties, android.net.LinkProperties linkProperties2) {
        return new com.android.net.module.util.LinkPropertiesUtils.CompareResult<>(linkProperties != null ? linkProperties.getLinkAddresses() : null, linkProperties2 != null ? linkProperties2.getLinkAddresses() : null);
    }

    public static boolean isIdenticalAllLinkAddresses(android.net.LinkProperties linkProperties, android.net.LinkProperties linkProperties2) {
        if (linkProperties == linkProperties2) {
            return true;
        }
        if (linkProperties == null || linkProperties2 == null) {
            return false;
        }
        java.util.List allLinkAddresses = linkProperties.getAllLinkAddresses();
        java.util.List allLinkAddresses2 = linkProperties2.getAllLinkAddresses();
        if (allLinkAddresses.size() != allLinkAddresses2.size()) {
            return false;
        }
        return allLinkAddresses.containsAll(allLinkAddresses2);
    }

    public static boolean isIdenticalAddresses(android.net.LinkProperties linkProperties, android.net.LinkProperties linkProperties2) {
        java.util.List addresses = linkProperties.getAddresses();
        java.util.List addresses2 = linkProperties2.getAddresses();
        if (addresses.size() == addresses2.size()) {
            return addresses.containsAll(addresses2);
        }
        return false;
    }

    public static boolean isIdenticalDnses(android.net.LinkProperties linkProperties, android.net.LinkProperties linkProperties2) {
        java.util.List<java.net.InetAddress> dnsServers = linkProperties.getDnsServers();
        java.util.List<java.net.InetAddress> dnsServers2 = linkProperties2.getDnsServers();
        java.lang.String domains = linkProperties.getDomains();
        java.lang.String domains2 = linkProperties2.getDomains();
        if (domains == null) {
            if (domains2 != null) {
                return false;
            }
        } else if (!domains.equals(domains2)) {
            return false;
        }
        if (dnsServers.size() != dnsServers2.size()) {
            return false;
        }
        return dnsServers.containsAll(dnsServers2);
    }

    public static boolean isIdenticalHttpProxy(android.net.LinkProperties linkProperties, android.net.LinkProperties linkProperties2) {
        return java.util.Objects.equals(linkProperties.getHttpProxy(), linkProperties2.getHttpProxy());
    }

    public static boolean isIdenticalInterfaceName(android.net.LinkProperties linkProperties, android.net.LinkProperties linkProperties2) {
        return android.text.TextUtils.equals(linkProperties.getInterfaceName(), linkProperties2.getInterfaceName());
    }

    public static boolean isIdenticalRoutes(android.net.LinkProperties linkProperties, android.net.LinkProperties linkProperties2) {
        java.util.List<android.net.RouteInfo> routes = linkProperties.getRoutes();
        java.util.List<android.net.RouteInfo> routes2 = linkProperties2.getRoutes();
        if (routes.size() == routes2.size()) {
            return routes.containsAll(routes2);
        }
        return false;
    }
}
