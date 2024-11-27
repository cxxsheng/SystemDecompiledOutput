package android.os;

/* compiled from: ServiceManagerNative.java */
/* loaded from: classes3.dex */
class ServiceManagerProxy implements android.os.IServiceManager {
    private android.os.IBinder mRemote;
    private android.os.IServiceManager mServiceManager;

    public ServiceManagerProxy(android.os.IBinder iBinder) {
        this.mRemote = iBinder;
        this.mServiceManager = android.os.IServiceManager.Stub.asInterface(iBinder);
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        return this.mRemote;
    }

    @Override // android.os.IServiceManager
    public android.os.IBinder getService(java.lang.String str) throws android.os.RemoteException {
        return this.mServiceManager.checkService(str);
    }

    @Override // android.os.IServiceManager
    public android.os.IBinder checkService(java.lang.String str) throws android.os.RemoteException {
        return this.mServiceManager.checkService(str);
    }

    @Override // android.os.IServiceManager
    public void addService(java.lang.String str, android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
        this.mServiceManager.addService(str, iBinder, z, i);
    }

    @Override // android.os.IServiceManager
    public java.lang.String[] listServices(int i) throws android.os.RemoteException {
        return this.mServiceManager.listServices(i);
    }

    @Override // android.os.IServiceManager
    public void registerForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException {
        this.mServiceManager.registerForNotifications(str, iServiceCallback);
    }

    @Override // android.os.IServiceManager
    public void unregisterForNotifications(java.lang.String str, android.os.IServiceCallback iServiceCallback) throws android.os.RemoteException {
        throw new android.os.RemoteException();
    }

    @Override // android.os.IServiceManager
    public boolean isDeclared(java.lang.String str) throws android.os.RemoteException {
        return this.mServiceManager.isDeclared(str);
    }

    @Override // android.os.IServiceManager
    public java.lang.String[] getDeclaredInstances(java.lang.String str) throws android.os.RemoteException {
        return this.mServiceManager.getDeclaredInstances(str);
    }

    @Override // android.os.IServiceManager
    public java.lang.String updatableViaApex(java.lang.String str) throws android.os.RemoteException {
        return this.mServiceManager.updatableViaApex(str);
    }

    @Override // android.os.IServiceManager
    public java.lang.String[] getUpdatableNames(java.lang.String str) throws android.os.RemoteException {
        return this.mServiceManager.getUpdatableNames(str);
    }

    @Override // android.os.IServiceManager
    public android.os.ConnectionInfo getConnectionInfo(java.lang.String str) throws android.os.RemoteException {
        return this.mServiceManager.getConnectionInfo(str);
    }

    @Override // android.os.IServiceManager
    public void registerClientCallback(java.lang.String str, android.os.IBinder iBinder, android.os.IClientCallback iClientCallback) throws android.os.RemoteException {
        throw new android.os.RemoteException();
    }

    @Override // android.os.IServiceManager
    public void tryUnregisterService(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        throw new android.os.RemoteException();
    }

    @Override // android.os.IServiceManager
    public android.os.ServiceDebugInfo[] getServiceDebugInfo() throws android.os.RemoteException {
        return this.mServiceManager.getServiceDebugInfo();
    }
}
