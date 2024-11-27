package android.os;

/* loaded from: classes3.dex */
final class HwNoService extends android.internal.hidl.manager.V1_2.IServiceManager.Stub implements android.os.IHwBinder, android.os.IHwInterface {
    private static final java.lang.String TAG = "HwNoService";

    HwNoService() {
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager.Stub
    public java.lang.String toString() {
        return "[HwNoService]";
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public android.internal.hidl.base.V1_0.IBase get(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        android.util.Log.i(TAG, "get " + str + "/" + str2 + " with no hwservicemanager");
        return null;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public boolean add(java.lang.String str, android.internal.hidl.base.V1_0.IBase iBase) throws android.os.RemoteException {
        android.util.Log.i(TAG, "get " + str + " with no hwservicemanager");
        return false;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public byte getTransport(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        android.util.Log.i(TAG, "getTransoport " + str + "/" + str2 + " with no hwservicemanager");
        return (byte) 0;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public java.util.ArrayList<java.lang.String> list() throws android.os.RemoteException {
        android.util.Log.i(TAG, "list with no hwservicemanager");
        return new java.util.ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public java.util.ArrayList<java.lang.String> listByInterface(java.lang.String str) throws android.os.RemoteException {
        android.util.Log.i(TAG, "listByInterface with no hwservicemanager");
        return new java.util.ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public boolean registerForNotifications(java.lang.String str, java.lang.String str2, android.internal.hidl.manager.V1_0.IServiceNotification iServiceNotification) throws android.os.RemoteException {
        android.util.Log.i(TAG, "registerForNotifications with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public java.util.ArrayList<android.internal.hidl.manager.V1_0.IServiceManager.InstanceDebugInfo> debugDump() throws android.os.RemoteException {
        android.util.Log.i(TAG, "debugDump with no hwservicemanager");
        return new java.util.ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_0.IServiceManager
    public void registerPassthroughClient(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        android.util.Log.i(TAG, "registerPassthroughClient with no hwservicemanager");
    }

    @Override // android.internal.hidl.manager.V1_1.IServiceManager
    public boolean unregisterForNotifications(java.lang.String str, java.lang.String str2, android.internal.hidl.manager.V1_0.IServiceNotification iServiceNotification) throws android.os.RemoteException {
        android.util.Log.i(TAG, "unregisterForNotifications with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean registerClientCallback(java.lang.String str, java.lang.String str2, android.internal.hidl.base.V1_0.IBase iBase, android.internal.hidl.manager.V1_2.IClientCallback iClientCallback) throws android.os.RemoteException {
        android.util.Log.i(TAG, "registerClientCallback for " + str + "/" + str2 + " with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean unregisterClientCallback(android.internal.hidl.base.V1_0.IBase iBase, android.internal.hidl.manager.V1_2.IClientCallback iClientCallback) throws android.os.RemoteException {
        android.util.Log.i(TAG, "unregisterClientCallback with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean addWithChain(java.lang.String str, android.internal.hidl.base.V1_0.IBase iBase, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException {
        android.util.Log.i(TAG, "addWithChain with no hwservicemanager");
        return true;
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public java.util.ArrayList<java.lang.String> listManifestByInterface(java.lang.String str) throws android.os.RemoteException {
        android.util.Log.i(TAG, "listManifestByInterface for " + str + " with no hwservicemanager");
        return new java.util.ArrayList<>();
    }

    @Override // android.internal.hidl.manager.V1_2.IServiceManager
    public boolean tryUnregister(java.lang.String str, java.lang.String str2, android.internal.hidl.base.V1_0.IBase iBase) throws android.os.RemoteException {
        android.util.Log.i(TAG, "tryUnregister for " + str + "/" + str2 + " with no hwservicemanager");
        return true;
    }
}
