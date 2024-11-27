package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
class FakeHalFactory implements com.android.server.soundtrigger_middleware.HalFactory {
    private static final java.lang.String TAG = "FakeHalFactory";
    private final android.media.soundtrigger_middleware.ISoundTriggerInjection mInjection;

    FakeHalFactory(android.media.soundtrigger_middleware.ISoundTriggerInjection iSoundTriggerInjection) {
        this.mInjection = iSoundTriggerInjection;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.soundtrigger_middleware.FakeSoundTriggerHal] */
    @Override // com.android.server.soundtrigger_middleware.HalFactory
    public com.android.server.soundtrigger_middleware.ISoundTriggerHal create() {
        ?? fakeSoundTriggerHal = new com.android.server.soundtrigger_middleware.FakeSoundTriggerHal(this.mInjection);
        final android.media.soundtrigger_middleware.IInjectGlobalEvent globalEventInjection = fakeSoundTriggerHal.getGlobalEventInjection();
        return new com.android.server.soundtrigger_middleware.FakeHalFactory.AnonymousClass1(fakeSoundTriggerHal, new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeHalFactory$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.soundtrigger_middleware.FakeHalFactory.lambda$create$0(globalEventInjection);
            }
        }, globalEventInjection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$create$0(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
        try {
            iInjectGlobalEvent.triggerRestart();
        } catch (android.os.RemoteException e) {
            android.util.Slog.wtf(TAG, "Unexpected RemoteException from same process");
        }
    }

    /* renamed from: com.android.server.soundtrigger_middleware.FakeHalFactory$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat {
        final /* synthetic */ android.media.soundtrigger_middleware.IInjectGlobalEvent val$session;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(android.os.IBinder iBinder, java.lang.Runnable runnable, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
            super(iBinder, runnable);
            this.val$session = iInjectGlobalEvent;
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat, com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public void detach() {
            java.util.concurrent.Executor executor = com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR;
            final android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent = this.val$session;
            executor.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeHalFactory$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.FakeHalFactory.AnonymousClass1.this.lambda$detach$0(iInjectGlobalEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$detach$0(android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
            try {
                com.android.server.soundtrigger_middleware.FakeHalFactory.this.mInjection.onFrameworkDetached(iInjectGlobalEvent);
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(com.android.server.soundtrigger_middleware.FakeHalFactory.TAG, "Unexpected RemoteException from same process");
            }
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat, com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public void clientAttached(final android.os.IBinder iBinder) {
            java.util.concurrent.Executor executor = com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR;
            final android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent = this.val$session;
            executor.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeHalFactory$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.FakeHalFactory.AnonymousClass1.this.lambda$clientAttached$1(iBinder, iInjectGlobalEvent);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clientAttached$1(android.os.IBinder iBinder, android.media.soundtrigger_middleware.IInjectGlobalEvent iInjectGlobalEvent) {
            try {
                com.android.server.soundtrigger_middleware.FakeHalFactory.this.mInjection.onClientAttached(iBinder, iInjectGlobalEvent);
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(com.android.server.soundtrigger_middleware.FakeHalFactory.TAG, "Unexpected RemoteException from same process");
            }
        }

        @Override // com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat, com.android.server.soundtrigger_middleware.ISoundTriggerHal
        public void clientDetached(final android.os.IBinder iBinder) {
            com.android.server.soundtrigger_middleware.FakeSoundTriggerHal.ExecutorHolder.INJECTION_EXECUTOR.execute(new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.FakeHalFactory$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.soundtrigger_middleware.FakeHalFactory.AnonymousClass1.this.lambda$clientDetached$2(iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clientDetached$2(android.os.IBinder iBinder) {
            try {
                com.android.server.soundtrigger_middleware.FakeHalFactory.this.mInjection.onClientDetached(iBinder);
            } catch (android.os.RemoteException e) {
                android.util.Slog.wtf(com.android.server.soundtrigger_middleware.FakeHalFactory.TAG, "Unexpected RemoteException from same process");
            }
        }
    }
}
