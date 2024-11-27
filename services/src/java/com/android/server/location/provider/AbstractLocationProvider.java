package com.android.server.location.provider;

/* loaded from: classes2.dex */
public abstract class AbstractLocationProvider {
    private final com.android.server.location.provider.LocationProviderController mController;
    protected final java.util.concurrent.Executor mExecutor;
    private final java.util.concurrent.atomic.AtomicReference<com.android.server.location.provider.AbstractLocationProvider.InternalState> mInternalState;

    public interface Listener {
        void onReportLocation(android.location.LocationResult locationResult);

        void onStateChanged(com.android.server.location.provider.AbstractLocationProvider.State state, com.android.server.location.provider.AbstractLocationProvider.State state2);
    }

    protected abstract void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr);

    protected abstract void onExtraCommand(int i, int i2, java.lang.String str, android.os.Bundle bundle);

    protected abstract void onFlush(java.lang.Runnable runnable);

    protected abstract void onSetRequest(android.location.provider.ProviderRequest providerRequest);

    public static final class State {
        public static final com.android.server.location.provider.AbstractLocationProvider.State EMPTY_STATE = new com.android.server.location.provider.AbstractLocationProvider.State(false, null, null, java.util.Collections.emptySet());
        public final boolean allowed;
        public final java.util.Set<java.lang.String> extraAttributionTags;

        @android.annotation.Nullable
        public final android.location.util.identity.CallerIdentity identity;

        @android.annotation.Nullable
        public final android.location.provider.ProviderProperties properties;

        private State(boolean z, android.location.provider.ProviderProperties providerProperties, android.location.util.identity.CallerIdentity callerIdentity, java.util.Set<java.lang.String> set) {
            this.allowed = z;
            this.properties = providerProperties;
            this.identity = callerIdentity;
            java.util.Objects.requireNonNull(set);
            this.extraAttributionTags = set;
        }

        public com.android.server.location.provider.AbstractLocationProvider.State withAllowed(boolean z) {
            if (z == this.allowed) {
                return this;
            }
            return new com.android.server.location.provider.AbstractLocationProvider.State(z, this.properties, this.identity, this.extraAttributionTags);
        }

        public com.android.server.location.provider.AbstractLocationProvider.State withProperties(@android.annotation.Nullable android.location.provider.ProviderProperties providerProperties) {
            if (java.util.Objects.equals(providerProperties, this.properties)) {
                return this;
            }
            return new com.android.server.location.provider.AbstractLocationProvider.State(this.allowed, providerProperties, this.identity, this.extraAttributionTags);
        }

        public com.android.server.location.provider.AbstractLocationProvider.State withIdentity(@android.annotation.Nullable android.location.util.identity.CallerIdentity callerIdentity) {
            if (java.util.Objects.equals(callerIdentity, this.identity)) {
                return this;
            }
            return new com.android.server.location.provider.AbstractLocationProvider.State(this.allowed, this.properties, callerIdentity, this.extraAttributionTags);
        }

        public com.android.server.location.provider.AbstractLocationProvider.State withExtraAttributionTags(java.util.Set<java.lang.String> set) {
            if (set.equals(this.extraAttributionTags)) {
                return this;
            }
            return new com.android.server.location.provider.AbstractLocationProvider.State(this.allowed, this.properties, this.identity, set);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.location.provider.AbstractLocationProvider.State)) {
                return false;
            }
            com.android.server.location.provider.AbstractLocationProvider.State state = (com.android.server.location.provider.AbstractLocationProvider.State) obj;
            return this.allowed == state.allowed && this.properties == state.properties && java.util.Objects.equals(this.identity, state.identity) && this.extraAttributionTags.equals(state.extraAttributionTags);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Boolean.valueOf(this.allowed), this.properties, this.identity, this.extraAttributionTags);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InternalState {

        @android.annotation.Nullable
        public final com.android.server.location.provider.AbstractLocationProvider.Listener listener;
        public final com.android.server.location.provider.AbstractLocationProvider.State state;

        InternalState(@android.annotation.Nullable com.android.server.location.provider.AbstractLocationProvider.Listener listener, com.android.server.location.provider.AbstractLocationProvider.State state) {
            this.listener = listener;
            this.state = state;
        }

        com.android.server.location.provider.AbstractLocationProvider.InternalState withListener(com.android.server.location.provider.AbstractLocationProvider.Listener listener) {
            if (listener == this.listener) {
                return this;
            }
            return new com.android.server.location.provider.AbstractLocationProvider.InternalState(listener, this.state);
        }

        com.android.server.location.provider.AbstractLocationProvider.InternalState withState(com.android.server.location.provider.AbstractLocationProvider.State state) {
            if (state.equals(this.state)) {
                return this;
            }
            return new com.android.server.location.provider.AbstractLocationProvider.InternalState(this.listener, state);
        }

        com.android.server.location.provider.AbstractLocationProvider.InternalState withState(java.util.function.UnaryOperator<com.android.server.location.provider.AbstractLocationProvider.State> unaryOperator) {
            return withState((com.android.server.location.provider.AbstractLocationProvider.State) unaryOperator.apply(this.state));
        }
    }

    protected AbstractLocationProvider(java.util.concurrent.Executor executor, @android.annotation.Nullable android.location.util.identity.CallerIdentity callerIdentity, @android.annotation.Nullable android.location.provider.ProviderProperties providerProperties, java.util.Set<java.lang.String> set) {
        com.android.internal.util.Preconditions.checkArgument(callerIdentity == null || callerIdentity.getListenerId() == null);
        java.util.Objects.requireNonNull(executor);
        this.mExecutor = executor;
        this.mInternalState = new java.util.concurrent.atomic.AtomicReference<>(new com.android.server.location.provider.AbstractLocationProvider.InternalState(null, com.android.server.location.provider.AbstractLocationProvider.State.EMPTY_STATE.withIdentity(callerIdentity).withProperties(providerProperties).withExtraAttributionTags(set)));
        this.mController = new com.android.server.location.provider.AbstractLocationProvider.Controller();
    }

    com.android.server.location.provider.LocationProviderController getController() {
        return this.mController;
    }

    protected void setState(final java.util.function.UnaryOperator<com.android.server.location.provider.AbstractLocationProvider.State> unaryOperator) {
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        com.android.server.location.provider.AbstractLocationProvider.InternalState updateAndGet = this.mInternalState.updateAndGet(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.location.provider.AbstractLocationProvider.InternalState lambda$setState$0;
                lambda$setState$0 = com.android.server.location.provider.AbstractLocationProvider.lambda$setState$0(atomicReference, unaryOperator, (com.android.server.location.provider.AbstractLocationProvider.InternalState) obj);
                return lambda$setState$0;
            }
        });
        com.android.server.location.provider.AbstractLocationProvider.State state = (com.android.server.location.provider.AbstractLocationProvider.State) atomicReference.get();
        if (!state.equals(updateAndGet.state) && updateAndGet.listener != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                updateAndGet.listener.onStateChanged(state, updateAndGet.state);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.InternalState lambda$setState$0(java.util.concurrent.atomic.AtomicReference atomicReference, java.util.function.UnaryOperator unaryOperator, com.android.server.location.provider.AbstractLocationProvider.InternalState internalState) {
        atomicReference.set(internalState.state);
        return internalState.withState((java.util.function.UnaryOperator<com.android.server.location.provider.AbstractLocationProvider.State>) unaryOperator);
    }

    public final com.android.server.location.provider.AbstractLocationProvider.State getState() {
        return this.mInternalState.get().state;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$setAllowed$1(boolean z, com.android.server.location.provider.AbstractLocationProvider.State state) {
        return state.withAllowed(z);
    }

    protected void setAllowed(final boolean z) {
        setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.location.provider.AbstractLocationProvider.State lambda$setAllowed$1;
                lambda$setAllowed$1 = com.android.server.location.provider.AbstractLocationProvider.lambda$setAllowed$1(z, (com.android.server.location.provider.AbstractLocationProvider.State) obj);
                return lambda$setAllowed$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$setProperties$2(android.location.provider.ProviderProperties providerProperties, com.android.server.location.provider.AbstractLocationProvider.State state) {
        return state.withProperties(providerProperties);
    }

    protected void setProperties(@android.annotation.Nullable final android.location.provider.ProviderProperties providerProperties) {
        setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.location.provider.AbstractLocationProvider.State lambda$setProperties$2;
                lambda$setProperties$2 = com.android.server.location.provider.AbstractLocationProvider.lambda$setProperties$2(providerProperties, (com.android.server.location.provider.AbstractLocationProvider.State) obj);
                return lambda$setProperties$2;
            }
        });
    }

    protected void setIdentity(@android.annotation.Nullable final android.location.util.identity.CallerIdentity callerIdentity) {
        com.android.internal.util.Preconditions.checkArgument(callerIdentity == null || callerIdentity.getListenerId() == null);
        setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.location.provider.AbstractLocationProvider.State lambda$setIdentity$3;
                lambda$setIdentity$3 = com.android.server.location.provider.AbstractLocationProvider.lambda$setIdentity$3(callerIdentity, (com.android.server.location.provider.AbstractLocationProvider.State) obj);
                return lambda$setIdentity$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$setIdentity$3(android.location.util.identity.CallerIdentity callerIdentity, com.android.server.location.provider.AbstractLocationProvider.State state) {
        return state.withIdentity(callerIdentity);
    }

    public final java.util.Set<java.lang.String> getExtraAttributionTags() {
        return this.mInternalState.get().state.extraAttributionTags;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.State lambda$setExtraAttributionTags$4(java.util.Set set, com.android.server.location.provider.AbstractLocationProvider.State state) {
        return state.withExtraAttributionTags(set);
    }

    protected void setExtraAttributionTags(final java.util.Set<java.lang.String> set) {
        setState(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.location.provider.AbstractLocationProvider.State lambda$setExtraAttributionTags$4;
                lambda$setExtraAttributionTags$4 = com.android.server.location.provider.AbstractLocationProvider.lambda$setExtraAttributionTags$4(set, (com.android.server.location.provider.AbstractLocationProvider.State) obj);
                return lambda$setExtraAttributionTags$4;
            }
        });
    }

    protected void reportLocation(android.location.LocationResult locationResult) {
        com.android.server.location.provider.AbstractLocationProvider.Listener listener = this.mInternalState.get().listener;
        if (listener != null) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.Objects.requireNonNull(locationResult);
                android.location.LocationResult locationResult2 = locationResult;
                listener.onReportLocation(locationResult);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    protected void onStart() {
    }

    protected void onStop() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Controller implements com.android.server.location.provider.LocationProviderController {
        private boolean mStarted = false;

        Controller() {
        }

        @Override // com.android.server.location.provider.LocationProviderController
        public com.android.server.location.provider.AbstractLocationProvider.State setListener(@android.annotation.Nullable final com.android.server.location.provider.AbstractLocationProvider.Listener listener) {
            com.android.server.location.provider.AbstractLocationProvider.InternalState internalState = (com.android.server.location.provider.AbstractLocationProvider.InternalState) com.android.server.location.provider.AbstractLocationProvider.this.mInternalState.getAndUpdate(new java.util.function.UnaryOperator() { // from class: com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    com.android.server.location.provider.AbstractLocationProvider.InternalState lambda$setListener$0;
                    lambda$setListener$0 = com.android.server.location.provider.AbstractLocationProvider.Controller.lambda$setListener$0(com.android.server.location.provider.AbstractLocationProvider.Listener.this, (com.android.server.location.provider.AbstractLocationProvider.InternalState) obj);
                    return lambda$setListener$0;
                }
            });
            com.android.internal.util.Preconditions.checkState(listener == null || internalState.listener == null);
            return internalState.state;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ com.android.server.location.provider.AbstractLocationProvider.InternalState lambda$setListener$0(com.android.server.location.provider.AbstractLocationProvider.Listener listener, com.android.server.location.provider.AbstractLocationProvider.InternalState internalState) {
            return internalState.withListener(listener);
        }

        @Override // com.android.server.location.provider.LocationProviderController
        public boolean isStarted() {
            return this.mStarted;
        }

        @Override // com.android.server.location.provider.LocationProviderController
        public void start() {
            com.android.internal.util.Preconditions.checkState(!this.mStarted);
            this.mStarted = true;
            java.util.concurrent.Executor executor = com.android.server.location.provider.AbstractLocationProvider.this.mExecutor;
            final com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider = com.android.server.location.provider.AbstractLocationProvider.this;
            executor.execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.provider.AbstractLocationProvider.this.onStart();
                }
            });
        }

        @Override // com.android.server.location.provider.LocationProviderController
        public void stop() {
            com.android.internal.util.Preconditions.checkState(this.mStarted);
            this.mStarted = false;
            java.util.concurrent.Executor executor = com.android.server.location.provider.AbstractLocationProvider.this.mExecutor;
            final com.android.server.location.provider.AbstractLocationProvider abstractLocationProvider = com.android.server.location.provider.AbstractLocationProvider.this;
            executor.execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.provider.AbstractLocationProvider.this.onStop();
                }
            });
        }

        @Override // com.android.server.location.provider.LocationProviderController
        public void setRequest(final android.location.provider.ProviderRequest providerRequest) {
            com.android.internal.util.Preconditions.checkState(this.mStarted);
            com.android.server.location.provider.AbstractLocationProvider.this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.provider.AbstractLocationProvider.Controller.this.lambda$setRequest$1(providerRequest);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setRequest$1(android.location.provider.ProviderRequest providerRequest) {
            com.android.server.location.provider.AbstractLocationProvider.this.onSetRequest(providerRequest);
        }

        @Override // com.android.server.location.provider.LocationProviderController
        public void flush(final java.lang.Runnable runnable) {
            com.android.internal.util.Preconditions.checkState(this.mStarted);
            com.android.server.location.provider.AbstractLocationProvider.this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.provider.AbstractLocationProvider.Controller.this.lambda$flush$2(runnable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$flush$2(java.lang.Runnable runnable) {
            com.android.server.location.provider.AbstractLocationProvider.this.onFlush(runnable);
        }

        @Override // com.android.server.location.provider.LocationProviderController
        public void sendExtraCommand(final int i, final int i2, final java.lang.String str, final android.os.Bundle bundle) {
            com.android.internal.util.Preconditions.checkState(this.mStarted);
            com.android.server.location.provider.AbstractLocationProvider.this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.location.provider.AbstractLocationProvider$Controller$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.location.provider.AbstractLocationProvider.Controller.this.lambda$sendExtraCommand$3(i, i2, str, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendExtraCommand$3(int i, int i2, java.lang.String str, android.os.Bundle bundle) {
            com.android.server.location.provider.AbstractLocationProvider.this.onExtraCommand(i, i2, str, bundle);
        }
    }
}
