package android.service.voice;

/* loaded from: classes3.dex */
public abstract class VoiceInteractionSessionService extends android.app.Service {
    static final int MSG_NEW_SESSION = 1;
    private static final java.lang.String TAG = "VoiceInteractionSession";
    com.android.internal.os.HandlerCaller mHandlerCaller;
    android.service.voice.VoiceInteractionSession mSession;
    com.android.internal.app.IVoiceInteractionManagerService mSystemService;
    android.service.voice.IVoiceInteractionSessionService mInterface = new android.service.voice.IVoiceInteractionSessionService.Stub() { // from class: android.service.voice.VoiceInteractionSessionService.1
        @Override // android.service.voice.IVoiceInteractionSessionService
        public void newSession(android.os.IBinder iBinder, android.os.Bundle bundle, int i) {
            android.service.voice.VoiceInteractionSessionService.this.mHandlerCaller.sendMessage(android.service.voice.VoiceInteractionSessionService.this.mHandlerCaller.obtainMessageIOO(1, i, iBinder, bundle));
        }
    };
    final com.android.internal.os.HandlerCaller.Callback mHandlerCallerCallback = new com.android.internal.os.HandlerCaller.Callback() { // from class: android.service.voice.VoiceInteractionSessionService.2
        @Override // com.android.internal.os.HandlerCaller.Callback
        public void executeMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
            switch (message.what) {
                case 1:
                    android.service.voice.VoiceInteractionSessionService.this.doNewSession((android.os.IBinder) someArgs.arg1, (android.os.Bundle) someArgs.arg2, someArgs.argi1);
                    break;
            }
        }
    };

    public abstract android.service.voice.VoiceInteractionSession onNewSession(android.os.Bundle bundle);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mSystemService = com.android.internal.app.IVoiceInteractionManagerService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.VOICE_INTERACTION_MANAGER_SERVICE));
        this.mHandlerCaller = new com.android.internal.os.HandlerCaller(this, android.os.Looper.myLooper(), this.mHandlerCallerCallback, true);
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mInterface.asBinder();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mSession != null) {
            this.mSession.onConfigurationChanged(configuration);
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        if (this.mSession != null) {
            this.mSession.onLowMemory();
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        if (this.mSession != null) {
            this.mSession.onTrimMemory(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (this.mSession == null) {
            printWriter.println("(no active session)");
        } else {
            printWriter.println("VoiceInteractionSession:");
            this.mSession.dump("  ", fileDescriptor, printWriter, strArr);
        }
    }

    void doNewSession(android.os.IBinder iBinder, android.os.Bundle bundle, int i) {
        if (this.mSession != null) {
            this.mSession.doDestroy();
            this.mSession = null;
        }
        this.mSession = onNewSession(bundle);
        if (deliverSession(iBinder)) {
            this.mSession.doCreate(this.mSystemService, iBinder);
        } else {
            this.mSession.doDestroy();
            this.mSession = null;
        }
    }

    private boolean deliverSession(android.os.IBinder iBinder) {
        try {
            return this.mSystemService.deliverNewSession(iBinder, this.mSession.mSession, this.mSession.mInteractor);
        } catch (android.os.DeadObjectException e) {
            return false;
        } catch (android.os.RemoteException e2) {
            android.util.Log.e(TAG, "Failed to deliver session: " + e2);
            return false;
        }
    }
}
