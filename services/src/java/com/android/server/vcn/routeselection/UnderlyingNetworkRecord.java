package com.android.server.vcn.routeselection;

/* loaded from: classes2.dex */
public class UnderlyingNetworkRecord {
    public final boolean isBlocked;

    @android.annotation.NonNull
    public final android.net.LinkProperties linkProperties;

    @android.annotation.NonNull
    public final android.net.Network network;

    @android.annotation.NonNull
    public final android.net.NetworkCapabilities networkCapabilities;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public UnderlyingNetworkRecord(@android.annotation.NonNull android.net.Network network, @android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities, @android.annotation.NonNull android.net.LinkProperties linkProperties, boolean z) {
        this.network = network;
        this.networkCapabilities = networkCapabilities;
        this.linkProperties = linkProperties;
        this.isBlocked = z;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.vcn.routeselection.UnderlyingNetworkRecord)) {
            return false;
        }
        com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord = (com.android.server.vcn.routeselection.UnderlyingNetworkRecord) obj;
        return this.network.equals(underlyingNetworkRecord.network) && this.networkCapabilities.equals(underlyingNetworkRecord.networkCapabilities) && this.linkProperties.equals(underlyingNetworkRecord.linkProperties) && this.isBlocked == underlyingNetworkRecord.isBlocked;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.network, this.networkCapabilities, this.linkProperties, java.lang.Boolean.valueOf(this.isBlocked));
    }

    public static boolean isSameNetwork(@android.annotation.Nullable com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord, @android.annotation.Nullable com.android.server.vcn.routeselection.UnderlyingNetworkRecord underlyingNetworkRecord2) {
        return java.util.Objects.equals(underlyingNetworkRecord == null ? null : underlyingNetworkRecord.network, underlyingNetworkRecord2 != null ? underlyingNetworkRecord2.network : null);
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("UnderlyingNetworkRecord:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mNetwork: " + this.network);
        indentingPrintWriter.println("mNetworkCapabilities: " + this.networkCapabilities);
        indentingPrintWriter.println("mLinkProperties: " + this.linkProperties);
        indentingPrintWriter.decreaseIndent();
    }

    static class Builder {
        boolean mIsBlocked;

        @android.annotation.Nullable
        private android.net.LinkProperties mLinkProperties;

        @android.annotation.NonNull
        private final android.net.Network mNetwork;

        @android.annotation.Nullable
        private android.net.NetworkCapabilities mNetworkCapabilities;
        boolean mWasIsBlockedSet;

        Builder(@android.annotation.NonNull android.net.Network network) {
            this.mNetwork = network;
        }

        @android.annotation.NonNull
        android.net.Network getNetwork() {
            return this.mNetwork;
        }

        void setNetworkCapabilities(@android.annotation.NonNull android.net.NetworkCapabilities networkCapabilities) {
            this.mNetworkCapabilities = networkCapabilities;
        }

        @android.annotation.Nullable
        android.net.NetworkCapabilities getNetworkCapabilities() {
            return this.mNetworkCapabilities;
        }

        void setLinkProperties(@android.annotation.NonNull android.net.LinkProperties linkProperties) {
            this.mLinkProperties = linkProperties;
        }

        void setIsBlocked(boolean z) {
            this.mIsBlocked = z;
            this.mWasIsBlockedSet = true;
        }

        boolean isValid() {
            return (this.mNetworkCapabilities == null || this.mLinkProperties == null || !this.mWasIsBlockedSet) ? false : true;
        }

        com.android.server.vcn.routeselection.UnderlyingNetworkRecord build() {
            if (!isValid()) {
                throw new java.lang.IllegalArgumentException("Called build before UnderlyingNetworkRecord was valid");
            }
            return new com.android.server.vcn.routeselection.UnderlyingNetworkRecord(this.mNetwork, this.mNetworkCapabilities, this.mLinkProperties, this.mIsBlocked);
        }
    }
}
