package android.net.ipmemorystore;

/* loaded from: classes.dex */
public interface OnL2KeyResponseListener {
    void onL2KeyResponse(android.net.ipmemorystore.Status status, java.lang.String str);

    @android.annotation.NonNull
    static android.net.ipmemorystore.IOnL2KeyResponseListener toAIDL(@android.annotation.NonNull android.net.ipmemorystore.OnL2KeyResponseListener onL2KeyResponseListener) {
        return new android.net.ipmemorystore.IOnL2KeyResponseListener.Stub() { // from class: android.net.ipmemorystore.OnL2KeyResponseListener.1
            @Override // android.net.ipmemorystore.IOnL2KeyResponseListener
            public void onL2KeyResponse(android.net.ipmemorystore.StatusParcelable statusParcelable, java.lang.String str) {
                if (android.net.ipmemorystore.OnL2KeyResponseListener.this != null) {
                    android.net.ipmemorystore.OnL2KeyResponseListener.this.onL2KeyResponse(new android.net.ipmemorystore.Status(statusParcelable), str);
                }
            }

            @Override // android.net.ipmemorystore.IOnL2KeyResponseListener
            public int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.ipmemorystore.IOnL2KeyResponseListener
            public java.lang.String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }
        };
    }
}
