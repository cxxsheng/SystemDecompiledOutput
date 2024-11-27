package android.net.ipmemorystore;

/* loaded from: classes.dex */
public interface OnNetworkAttributesRetrievedListener {
    void onNetworkAttributesRetrieved(android.net.ipmemorystore.Status status, java.lang.String str, android.net.ipmemorystore.NetworkAttributes networkAttributes);

    @android.annotation.NonNull
    static android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener toAIDL(@android.annotation.NonNull android.net.ipmemorystore.OnNetworkAttributesRetrievedListener onNetworkAttributesRetrievedListener) {
        return new android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener.Stub() { // from class: android.net.ipmemorystore.OnNetworkAttributesRetrievedListener.1
            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public void onNetworkAttributesRetrieved(android.net.ipmemorystore.StatusParcelable statusParcelable, java.lang.String str, android.net.ipmemorystore.NetworkAttributesParcelable networkAttributesParcelable) {
                if (android.net.ipmemorystore.OnNetworkAttributesRetrievedListener.this != null) {
                    android.net.ipmemorystore.OnNetworkAttributesRetrievedListener.this.onNetworkAttributesRetrieved(new android.net.ipmemorystore.Status(statusParcelable), str, networkAttributesParcelable == null ? null : new android.net.ipmemorystore.NetworkAttributes(networkAttributesParcelable));
                }
            }

            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.ipmemorystore.IOnNetworkAttributesRetrievedListener
            public java.lang.String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }
        };
    }
}
