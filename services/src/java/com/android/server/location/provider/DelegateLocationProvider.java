package com.android.server.location.provider;

/* loaded from: classes2.dex */
class DelegateLocationProvider extends com.android.server.location.provider.AbstractLocationProvider implements com.android.server.location.provider.AbstractLocationProvider.Listener {
    protected final com.android.server.location.provider.AbstractLocationProvider mDelegate;
    private final java.lang.Object mInitializationLock;
    private boolean mInitialized;

    DelegateLocationProvider(java.util.concurrent.Executor executor, com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider) {
        super(executor, null, null, java.util.Collections.emptySet());
        this.mInitializationLock = new java.lang.Object();
        this.mInitialized = false;
        this.mDelegate = abstractLocationProvider;
    }

    protected void initializeDelegate() {
        synchronized (this.mInitializationLock) {
            com.android.internal.util.Preconditions.checkState(!this.mInitialized);
            setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.DelegateLocationProvider$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    com.android.server.location.provider.AbstractLocationProvider.State lambda$initializeDelegate$0;
                    lambda$initializeDelegate$0 = com.android.server.location.provider.DelegateLocationProvider.this.lambda$initializeDelegate$0((com.android.server.location.provider.AbstractLocationProvider.State) obj);
                    return lambda$initializeDelegate$0;
                }
            });
            this.mInitialized = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$initializeDelegate$0(com.android.server.location.provider.AbstractLocationProvider.State state) {
        return this.mDelegate.getController().setListener(this);
    }

    protected final void waitForInitialization() {
        synchronized (this.mInitializationLock) {
            com.android.internal.util.Preconditions.checkState(this.mInitialized);
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
    public void onStateChanged(com.android.server.location.provider.AbstractLocationProvider.State state, final com.android.server.location.provider.AbstractLocationProvider.State state2) {
        waitForInitialization();
        setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.DelegateLocationProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.location.provider.AbstractLocationProvider.State lambda$onStateChanged$1;
                lambda$onStateChanged$1 = com.android.server.location.provider.DelegateLocationProvider.lambda$onStateChanged$1(com.android.server.location.provider.AbstractLocationProvider.State.this, (com.android.server.location.provider.AbstractLocationProvider.State) obj);
                return lambda$onStateChanged$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$onStateChanged$1(com.android.server.location.provider.AbstractLocationProvider.State state, com.android.server.location.provider.AbstractLocationProvider.State state2) {
        return state;
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
    public void onReportLocation(android.location.LocationResult locationResult) {
        waitForInitialization();
        reportLocation(locationResult);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onStart() {
        com.android.internal.util.Preconditions.checkState(this.mInitialized);
        this.mDelegate.getController().start();
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onStop() {
        com.android.internal.util.Preconditions.checkState(this.mInitialized);
        this.mDelegate.getController().stop();
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onSetRequest(android.location.provider.ProviderRequest providerRequest) {
        com.android.internal.util.Preconditions.checkState(this.mInitialized);
        this.mDelegate.getController().setRequest(providerRequest);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onFlush(java.lang.Runnable runnable) {
        com.android.internal.util.Preconditions.checkState(this.mInitialized);
        this.mDelegate.getController().flush(runnable);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onExtraCommand(int i, int i2, java.lang.String str, android.os.Bundle bundle) {
        com.android.internal.util.Preconditions.checkState(this.mInitialized);
        this.mDelegate.getController().sendExtraCommand(i, i2, str, bundle);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.internal.util.Preconditions.checkState(this.mInitialized);
        this.mDelegate.dump(fileDescriptor, printWriter, strArr);
    }
}
