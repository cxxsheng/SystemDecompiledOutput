package android.app;

/* compiled from: ContextImpl.java */
/* loaded from: classes.dex */
class ReceiverRestrictedContext extends android.content.ContextWrapper {
    ReceiverRestrictedContext(android.content.Context context) {
        super(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter) {
        return registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        if (broadcastReceiver == null) {
            return super.registerReceiver(null, intentFilter, str, handler);
        }
        throw new android.content.ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to register to receive intents");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.Intent registerReceiverForAllUsers(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        return registerReceiverAsUser(broadcastReceiver, android.os.UserHandle.ALL, intentFilter, str, handler);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.Intent registerReceiverAsUser(android.content.BroadcastReceiver broadcastReceiver, android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        if (broadcastReceiver == null) {
            return super.registerReceiverAsUser(null, userHandle, intentFilter, str, handler);
        }
        throw new android.content.ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to register to receive intents");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean bindService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i) {
        throw new android.content.ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean bindService(android.content.Intent intent, int i, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        throw new android.content.ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean bindIsolatedService(android.content.Intent intent, int i, java.lang.String str, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        throw new android.content.ReceiverCallNotAllowedException("BroadcastReceiver components are not allowed to bind to services");
    }
}
