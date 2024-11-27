package com.android.server.location.provider;

/* loaded from: classes2.dex */
public class MockableLocationProvider extends com.android.server.location.provider.AbstractLocationProvider {

    @com.android.internal.annotations.GuardedBy({"mOwnerLock"})
    @android.annotation.Nullable
    private com.android.server.location.provider.MockLocationProvider mMockProvider;
    final java.lang.Object mOwnerLock;

    @com.android.internal.annotations.GuardedBy({"mOwnerLock"})
    @android.annotation.Nullable
    private com.android.server.location.provider.AbstractLocationProvider mProvider;

    @com.android.internal.annotations.GuardedBy({"mOwnerLock"})
    @android.annotation.Nullable
    private com.android.server.location.provider.AbstractLocationProvider mRealProvider;

    @com.android.internal.annotations.GuardedBy({"mOwnerLock"})
    private android.location.provider.ProviderRequest mRequest;

    @com.android.internal.annotations.GuardedBy({"mOwnerLock"})
    private boolean mStarted;

    public MockableLocationProvider(java.lang.Object obj) {
        super(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, null, null, java.util.Collections.emptySet());
        this.mOwnerLock = obj;
        this.mRequest = android.location.provider.ProviderRequest.EMPTY_REQUEST;
    }

    @android.annotation.Nullable
    public com.android.server.location.provider.AbstractLocationProvider getProvider() {
        com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider;
        synchronized (this.mOwnerLock) {
            abstractLocationProvider = this.mProvider;
        }
        return abstractLocationProvider;
    }

    public void setRealProvider(@android.annotation.Nullable com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider) {
        synchronized (this.mOwnerLock) {
            try {
                if (this.mRealProvider == abstractLocationProvider) {
                    return;
                }
                this.mRealProvider = abstractLocationProvider;
                if (!isMock()) {
                    setProviderLocked(this.mRealProvider);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setMockProvider(@android.annotation.Nullable com.android.server.location.provider.MockLocationProvider mockLocationProvider) {
        synchronized (this.mOwnerLock) {
            try {
                if (this.mMockProvider == mockLocationProvider) {
                    return;
                }
                this.mMockProvider = mockLocationProvider;
                if (this.mMockProvider != null) {
                    setProviderLocked(this.mMockProvider);
                } else {
                    setProviderLocked(this.mRealProvider);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mOwnerLock"})
    private void setProviderLocked(@android.annotation.Nullable com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider) {
        final com.android.server.location.provider.AbstractLocationProvider.State state;
        if (this.mProvider == abstractLocationProvider) {
            return;
        }
        com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider2 = this.mProvider;
        this.mProvider = abstractLocationProvider;
        if (abstractLocationProvider2 != null) {
            abstractLocationProvider2.getController().setListener(null);
            if (abstractLocationProvider2.getController().isStarted()) {
                abstractLocationProvider2.getController().setRequest(android.location.provider.ProviderRequest.EMPTY_REQUEST);
                abstractLocationProvider2.getController().stop();
            }
        }
        if (this.mProvider != null) {
            state = this.mProvider.getController().setListener(new com.android.server.location.provider.MockableLocationProvider.ListenerWrapper(this.mProvider));
            if (this.mStarted) {
                if (!this.mProvider.getController().isStarted()) {
                    this.mProvider.getController().start();
                }
                this.mProvider.getController().setRequest(this.mRequest);
            } else if (this.mProvider.getController().isStarted()) {
                this.mProvider.getController().setRequest(android.location.provider.ProviderRequest.EMPTY_REQUEST);
                this.mProvider.getController().stop();
            }
        } else {
            state = com.android.server.location.provider.AbstractLocationProvider.State.EMPTY_STATE;
        }
        setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.MockableLocationProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.location.provider.AbstractLocationProvider.State lambda$setProviderLocked$0;
                lambda$setProviderLocked$0 = com.android.server.location.provider.MockableLocationProvider.lambda$setProviderLocked$0(com.android.server.location.provider.AbstractLocationProvider.State.this, (com.android.server.location.provider.AbstractLocationProvider.State) obj);
                return lambda$setProviderLocked$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$setProviderLocked$0(com.android.server.location.provider.AbstractLocationProvider.State state, com.android.server.location.provider.AbstractLocationProvider.State state2) {
        return state;
    }

    public boolean isMock() {
        boolean z;
        synchronized (this.mOwnerLock) {
            try {
                z = this.mMockProvider != null && this.mProvider == this.mMockProvider;
            } finally {
            }
        }
        return z;
    }

    public void setMockProviderAllowed(boolean z) {
        synchronized (this.mOwnerLock) {
            com.android.internal.util.Preconditions.checkState(isMock());
            this.mMockProvider.setProviderAllowed(z);
        }
    }

    public void setMockProviderLocation(android.location.Location location) {
        synchronized (this.mOwnerLock) {
            com.android.internal.util.Preconditions.checkState(isMock());
            this.mMockProvider.setProviderLocation(location);
        }
    }

    public android.location.provider.ProviderRequest getCurrentRequest() {
        android.location.provider.ProviderRequest providerRequest;
        synchronized (this.mOwnerLock) {
            providerRequest = this.mRequest;
        }
        return providerRequest;
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onStart() {
        synchronized (this.mOwnerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(!this.mStarted);
                this.mStarted = true;
                if (this.mProvider != null) {
                    this.mProvider.getController().start();
                    if (!this.mRequest.equals(android.location.provider.ProviderRequest.EMPTY_REQUEST)) {
                        this.mProvider.getController().setRequest(this.mRequest);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onStop() {
        synchronized (this.mOwnerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(this.mStarted);
                this.mStarted = false;
                if (this.mProvider != null) {
                    if (!this.mRequest.equals(android.location.provider.ProviderRequest.EMPTY_REQUEST)) {
                        this.mProvider.getController().setRequest(android.location.provider.ProviderRequest.EMPTY_REQUEST);
                    }
                    this.mProvider.getController().stop();
                }
                this.mRequest = android.location.provider.ProviderRequest.EMPTY_REQUEST;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onSetRequest(android.location.provider.ProviderRequest providerRequest) {
        synchronized (this.mOwnerLock) {
            try {
                if (providerRequest == this.mRequest) {
                    return;
                }
                this.mRequest = providerRequest;
                if (this.mProvider != null) {
                    this.mProvider.getController().setRequest(providerRequest);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onFlush(java.lang.Runnable runnable) {
        synchronized (this.mOwnerLock) {
            try {
                if (this.mProvider != null) {
                    this.mProvider.getController().flush(runnable);
                } else {
                    runnable.run();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    protected void onExtraCommand(int i, int i2, java.lang.String str, android.os.Bundle bundle) {
        synchronized (this.mOwnerLock) {
            try {
                if (this.mProvider != null) {
                    this.mProvider.getController().sendExtraCommand(i, i2, str, bundle);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider;
        com.android.server.location.provider.AbstractLocationProvider.State state;
        com.android.internal.util.Preconditions.checkState(!java.lang.Thread.holdsLock(this.mOwnerLock));
        synchronized (this.mOwnerLock) {
            abstractLocationProvider = this.mProvider;
            state = getState();
        }
        printWriter.println("allowed=" + state.allowed);
        if (state.identity != null) {
            printWriter.println("identity=" + state.identity);
        }
        if (!state.extraAttributionTags.isEmpty()) {
            printWriter.println("extra attribution tags=" + state.extraAttributionTags);
        }
        if (state.properties != null) {
            printWriter.println("properties=" + state.properties);
        }
        if (abstractLocationProvider != null) {
            abstractLocationProvider.dump(fileDescriptor, printWriter, strArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ListenerWrapper implements com.android.server.location.provider.AbstractLocationProvider.Listener {
        private final com.android.server.location.provider.AbstractLocationProvider mListenerProvider;

        ListenerWrapper(com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider) {
            this.mListenerProvider = abstractLocationProvider;
        }

        @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
        public final void onStateChanged(com.android.server.location.provider.AbstractLocationProvider.State state, final com.android.server.location.provider.AbstractLocationProvider.State state2) {
            synchronized (com.android.server.location.provider.MockableLocationProvider.this.mOwnerLock) {
                try {
                    if (this.mListenerProvider != com.android.server.location.provider.MockableLocationProvider.this.mProvider) {
                        return;
                    }
                    com.android.server.location.provider.MockableLocationProvider.this.setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.MockableLocationProvider$ListenerWrapper$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final java.lang.Object apply(java.lang.Object obj) {
                            com.android.server.location.provider.AbstractLocationProvider.State lambda$onStateChanged$0;
                            lambda$onStateChanged$0 = com.android.server.location.provider.MockableLocationProvider.ListenerWrapper.lambda$onStateChanged$0(com.android.server.location.provider.AbstractLocationProvider.State.this, (com.android.server.location.provider.AbstractLocationProvider.State) obj);
                            return lambda$onStateChanged$0;
                        }
                    });
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$onStateChanged$0(com.android.server.location.provider.AbstractLocationProvider.State state, com.android.server.location.provider.AbstractLocationProvider.State state2) {
            return state;
        }

        @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
        public final void onReportLocation(android.location.LocationResult locationResult) {
            synchronized (com.android.server.location.provider.MockableLocationProvider.this.mOwnerLock) {
                try {
                    if (this.mListenerProvider != com.android.server.location.provider.MockableLocationProvider.this.mProvider) {
                        return;
                    }
                    com.android.server.location.provider.MockableLocationProvider.this.reportLocation(locationResult);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
