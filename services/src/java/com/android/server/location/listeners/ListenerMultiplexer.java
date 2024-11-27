package com.android.server.location.listeners;

/* loaded from: classes2.dex */
public abstract class ListenerMultiplexer<TKey, TListener, TRegistration extends com.android.server.location.listeners.ListenerRegistration<TListener>, TMergedRegistration> {

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    @android.annotation.Nullable
    private TMergedRegistration mMerged;
    protected final java.lang.Object mMultiplexerLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private final android.util.ArrayMap<TKey, TRegistration> mRegistrations = new android.util.ArrayMap<>();
    private final com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.UpdateServiceBuffer mUpdateServiceBuffer = new com.android.server.location.listeners.ListenerMultiplexer.UpdateServiceBuffer();
    private final com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard mReentrancyGuard = new com.android.server.location.listeners.ListenerMultiplexer.ReentrancyGuard();

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private int mActiveRegistrationsCount = 0;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private boolean mServiceRegistered = false;

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected abstract boolean isActive(@android.annotation.NonNull TRegistration tregistration);

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected abstract TMergedRegistration mergeRegistrations(@android.annotation.NonNull java.util.Collection<TRegistration> collection);

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected abstract boolean registerWithService(TMergedRegistration tmergedregistration, @android.annotation.NonNull java.util.Collection<TRegistration> collection);

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected abstract void unregisterWithService();

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected boolean reregisterWithService(TMergedRegistration tmergedregistration, TMergedRegistration tmergedregistration2, @android.annotation.NonNull java.util.Collection<TRegistration> collection) {
        return registerWithService(tmergedregistration2, collection);
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onRegister() {
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onUnregister() {
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onRegistrationAdded(@android.annotation.NonNull TKey tkey, @android.annotation.NonNull TRegistration tregistration) {
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onRegistrationReplaced(@android.annotation.NonNull TKey tkey, @android.annotation.NonNull TRegistration tregistration, @android.annotation.NonNull TKey tkey2, @android.annotation.NonNull TRegistration tregistration2) {
        onRegistrationRemoved(tkey, tregistration);
        onRegistrationAdded(tkey2, tregistration2);
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onRegistrationRemoved(@android.annotation.NonNull TKey tkey, @android.annotation.NonNull TRegistration tregistration) {
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onActive() {
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected void onInactive() {
    }

    protected final void putRegistration(@android.annotation.NonNull TKey tkey, @android.annotation.NonNull TRegistration tregistration) {
        replaceRegistration(tkey, tkey, tregistration);
    }

    protected final void replaceRegistration(@android.annotation.NonNull TKey tkey, @android.annotation.NonNull TKey tkey2, @android.annotation.NonNull TRegistration tregistration) {
        TRegistration tregistration2;
        java.util.Objects.requireNonNull(tkey);
        java.util.Objects.requireNonNull(tkey2);
        java.util.Objects.requireNonNull(tregistration);
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
                com.android.internal.util.Preconditions.checkArgument(tkey == tkey2 || !this.mRegistrations.containsKey(tkey2));
                com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
                try {
                    com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
                    try {
                        boolean isEmpty = this.mRegistrations.isEmpty();
                        int indexOfKey = this.mRegistrations.indexOfKey(tkey);
                        if (indexOfKey >= 0) {
                            tregistration2 = this.mRegistrations.valueAt(indexOfKey);
                            unregister(tregistration2);
                            tregistration2.onUnregister();
                            if (tkey != tkey2) {
                                this.mRegistrations.removeAt(indexOfKey);
                            }
                        } else {
                            tregistration2 = null;
                        }
                        if (tkey != tkey2 || indexOfKey < 0) {
                            this.mRegistrations.put(tkey2, tregistration);
                        } else {
                            this.mRegistrations.setValueAt(indexOfKey, tregistration);
                        }
                        if (isEmpty) {
                            onRegister();
                        }
                        tregistration.onRegister(tkey2);
                        if (tregistration2 == null) {
                            onRegistrationAdded(tkey2, tregistration);
                        } else {
                            onRegistrationReplaced(tkey, tregistration2, tkey2, tregistration);
                        }
                        onRegistrationActiveChanged(tregistration);
                        if (acquire2 != null) {
                            acquire2.close();
                        }
                        if (acquire != null) {
                            acquire.close();
                        }
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        }
    }

    protected final void removeRegistrationIf(@android.annotation.NonNull java.util.function.Predicate<TKey> predicate) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
                com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
                try {
                    com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
                    try {
                        int size = this.mRegistrations.size();
                        for (int i = 0; i < size; i++) {
                            TKey keyAt = this.mRegistrations.keyAt(i);
                            if (predicate.test(keyAt)) {
                                removeRegistration(keyAt, this.mRegistrations.valueAt(i));
                            }
                        }
                        if (acquire2 != null) {
                            acquire2.close();
                        }
                        if (acquire != null) {
                            acquire.close();
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected final void removeRegistration(TKey tkey) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.internal.util.Preconditions.checkState(!this.mReentrancyGuard.isReentrant());
                int indexOfKey = this.mRegistrations.indexOfKey(tkey);
                if (indexOfKey < 0) {
                    return;
                }
                removeRegistration(indexOfKey);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected final void removeRegistration(@android.annotation.NonNull TKey tkey, @android.annotation.NonNull com.android.server.location.listeners.ListenerRegistration<?> listenerRegistration) {
        synchronized (this.mMultiplexerLock) {
            try {
                int indexOfKey = this.mRegistrations.indexOfKey(tkey);
                if (indexOfKey < 0) {
                    return;
                }
                TRegistration valueAt = this.mRegistrations.valueAt(indexOfKey);
                if (valueAt != listenerRegistration) {
                    return;
                }
                if (this.mReentrancyGuard.isReentrant()) {
                    unregister(valueAt);
                    this.mReentrancyGuard.markForRemoval(tkey, valueAt);
                } else {
                    removeRegistration(indexOfKey);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private void removeRegistration(int i) {
        TKey keyAt = this.mRegistrations.keyAt(i);
        TRegistration valueAt = this.mRegistrations.valueAt(i);
        com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
        try {
            com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
            try {
                unregister(valueAt);
                onRegistrationRemoved(keyAt, valueAt);
                valueAt.onUnregister();
                this.mRegistrations.removeAt(i);
                if (this.mRegistrations.isEmpty()) {
                    onUnregister();
                }
                if (acquire2 != null) {
                    acquire2.close();
                }
                if (acquire != null) {
                    acquire.close();
                }
            } finally {
            }
        } catch (java.lang.Throwable th) {
            if (acquire != null) {
                try {
                    acquire.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void updateService() {
        synchronized (this.mMultiplexerLock) {
            try {
                if (this.mUpdateServiceBuffer.isBuffered()) {
                    this.mUpdateServiceBuffer.markUpdateServiceRequired();
                    return;
                }
                int size = this.mRegistrations.size();
                java.util.ArrayList arrayList = new java.util.ArrayList(size);
                for (int i = 0; i < size; i++) {
                    TRegistration valueAt = this.mRegistrations.valueAt(i);
                    if (valueAt.isActive()) {
                        arrayList.add(valueAt);
                    }
                }
                if (arrayList.isEmpty()) {
                    if (this.mServiceRegistered) {
                        this.mMerged = null;
                        this.mServiceRegistered = false;
                        unregisterWithService();
                    }
                } else {
                    TMergedRegistration mergeRegistrations = mergeRegistrations(arrayList);
                    if (this.mServiceRegistered) {
                        if (!java.util.Objects.equals(mergeRegistrations, this.mMerged)) {
                            this.mServiceRegistered = reregisterWithService(this.mMerged, mergeRegistrations, arrayList);
                            this.mMerged = this.mServiceRegistered ? mergeRegistrations : null;
                        }
                    } else {
                        this.mServiceRegistered = registerWithService(mergeRegistrations, arrayList);
                        this.mMerged = this.mServiceRegistered ? mergeRegistrations : null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected final void resetService() {
        synchronized (this.mMultiplexerLock) {
            try {
                if (this.mServiceRegistered) {
                    this.mMerged = null;
                    this.mServiceRegistered = false;
                    unregisterWithService();
                    updateService();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public com.android.server.location.listeners.ListenerMultiplexer.UpdateServiceLock newUpdateServiceLock() {
        return new com.android.server.location.listeners.ListenerMultiplexer.UpdateServiceLock(this.mUpdateServiceBuffer);
    }

    /* JADX WARN: Finally extract failed */
    protected final boolean findRegistration(java.util.function.Predicate<TRegistration> predicate) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire = this.mReentrancyGuard.acquire();
                try {
                    int size = this.mRegistrations.size();
                    for (int i = 0; i < size; i++) {
                        if (predicate.test(this.mRegistrations.valueAt(i))) {
                            if (acquire != null) {
                                acquire.close();
                            }
                            return true;
                        }
                    }
                    if (acquire != null) {
                        acquire.close();
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    if (acquire != null) {
                        try {
                            acquire.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th3) {
                throw th3;
            }
        }
    }

    protected final void updateRegistrations(@android.annotation.NonNull java.util.function.Predicate<TRegistration> predicate) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
                try {
                    com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
                    try {
                        int size = this.mRegistrations.size();
                        for (int i = 0; i < size; i++) {
                            TRegistration valueAt = this.mRegistrations.valueAt(i);
                            if (predicate.test(valueAt)) {
                                onRegistrationActiveChanged(valueAt);
                            }
                        }
                        if (acquire2 != null) {
                            acquire2.close();
                        }
                        if (acquire != null) {
                            acquire.close();
                        }
                    } finally {
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected final boolean updateRegistration(@android.annotation.NonNull java.lang.Object obj, @android.annotation.NonNull java.util.function.Predicate<TRegistration> predicate) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.UpdateServiceBuffer acquire = this.mUpdateServiceBuffer.acquire();
                try {
                    com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire2 = this.mReentrancyGuard.acquire();
                    try {
                        int indexOfKey = this.mRegistrations.indexOfKey(obj);
                        if (indexOfKey < 0) {
                            if (acquire2 != null) {
                                acquire2.close();
                            }
                            if (acquire != null) {
                                acquire.close();
                            }
                            return false;
                        }
                        TRegistration valueAt = this.mRegistrations.valueAt(indexOfKey);
                        if (predicate.test(valueAt)) {
                            onRegistrationActiveChanged(valueAt);
                        }
                        if (acquire2 != null) {
                            acquire2.close();
                        }
                        if (acquire != null) {
                            acquire.close();
                        }
                        return true;
                    } finally {
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private void onRegistrationActiveChanged(TRegistration tregistration) {
        boolean z = tregistration.isRegistered() && isActive(tregistration);
        if (tregistration.setActive(z)) {
            if (z) {
                int i = this.mActiveRegistrationsCount + 1;
                this.mActiveRegistrationsCount = i;
                if (i == 1) {
                    onActive();
                }
                tregistration.onActive();
            } else {
                tregistration.onInactive();
                int i2 = this.mActiveRegistrationsCount - 1;
                this.mActiveRegistrationsCount = i2;
                if (i2 == 0) {
                    onInactive();
                }
            }
            updateService();
        }
    }

    protected final void deliverToListeners(@android.annotation.NonNull java.util.function.Function<TRegistration, com.android.internal.listeners.ListenerExecutor.ListenerOperation<TListener>> function) {
        com.android.internal.listeners.ListenerExecutor.ListenerOperation<TListener> apply;
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire = this.mReentrancyGuard.acquire();
                try {
                    int size = this.mRegistrations.size();
                    for (int i = 0; i < size; i++) {
                        TRegistration valueAt = this.mRegistrations.valueAt(i);
                        if (valueAt.isActive() && (apply = function.apply(valueAt)) != null) {
                            valueAt.executeOperation(apply);
                        }
                    }
                    if (acquire != null) {
                        acquire.close();
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected final void deliverToListeners(@android.annotation.NonNull com.android.internal.listeners.ListenerExecutor.ListenerOperation<TListener> listenerOperation) {
        synchronized (this.mMultiplexerLock) {
            try {
                com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire = this.mReentrancyGuard.acquire();
                try {
                    int size = this.mRegistrations.size();
                    for (int i = 0; i < size; i++) {
                        TRegistration valueAt = this.mRegistrations.valueAt(i);
                        if (valueAt.isActive()) {
                            valueAt.executeOperation(listenerOperation);
                        }
                    }
                    if (acquire != null) {
                        acquire.close();
                    }
                } finally {
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    private void unregister(TRegistration tregistration) {
        tregistration.unregisterInternal();
        onRegistrationActiveChanged(tregistration);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        synchronized (this.mMultiplexerLock) {
            try {
                printWriter.print("service: ");
                printWriter.print(getServiceState());
                printWriter.println();
                if (!this.mRegistrations.isEmpty()) {
                    printWriter.println("listeners:");
                    int size = this.mRegistrations.size();
                    for (int i = 0; i < size; i++) {
                        TRegistration valueAt = this.mRegistrations.valueAt(i);
                        printWriter.print("  ");
                        printWriter.print(valueAt);
                        if (!valueAt.isActive()) {
                            printWriter.println(" (inactive)");
                        } else {
                            printWriter.println();
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
    protected java.lang.String getServiceState() {
        if (this.mServiceRegistered) {
            if (this.mMerged != null) {
                return this.mMerged.toString();
            }
            return "registered";
        }
        return "unregistered";
    }

    private final class ReentrancyGuard implements java.lang.AutoCloseable {

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        private int mGuardCount = 0;

        @com.android.internal.annotations.GuardedBy({"mMultiplexerLock"})
        @android.annotation.Nullable
        private android.util.ArraySet<java.util.Map.Entry<TKey, com.android.server.location.listeners.ListenerRegistration<?>>> mScheduledRemovals = null;

        ReentrancyGuard() {
        }

        boolean isReentrant() {
            boolean z;
            synchronized (com.android.server.location.listeners.ListenerMultiplexer.this.mMultiplexerLock) {
                z = this.mGuardCount != 0;
            }
            return z;
        }

        void markForRemoval(TKey tkey, com.android.server.location.listeners.ListenerRegistration<?> listenerRegistration) {
            synchronized (com.android.server.location.listeners.ListenerMultiplexer.this.mMultiplexerLock) {
                try {
                    com.android.internal.util.Preconditions.checkState(isReentrant());
                    if (this.mScheduledRemovals == null) {
                        this.mScheduledRemovals = new android.util.ArraySet<>(com.android.server.location.listeners.ListenerMultiplexer.this.mRegistrations.size());
                    }
                    this.mScheduledRemovals.add(new java.util.AbstractMap.SimpleImmutableEntry(tkey, listenerRegistration));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.ReentrancyGuard acquire() {
            synchronized (com.android.server.location.listeners.ListenerMultiplexer.this.mMultiplexerLock) {
                this.mGuardCount++;
            }
            return this;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            synchronized (com.android.server.location.listeners.ListenerMultiplexer.this.mMultiplexerLock) {
                try {
                    com.android.internal.util.Preconditions.checkState(this.mGuardCount > 0);
                    int i = this.mGuardCount - 1;
                    this.mGuardCount = i;
                    android.util.ArraySet<java.util.Map.Entry<TKey, com.android.server.location.listeners.ListenerRegistration<?>>> arraySet = null;
                    if (i == 0) {
                        android.util.ArraySet<java.util.Map.Entry<TKey, com.android.server.location.listeners.ListenerRegistration<?>>> arraySet2 = this.mScheduledRemovals;
                        this.mScheduledRemovals = null;
                        arraySet = arraySet2;
                    }
                    if (arraySet == null) {
                        return;
                    }
                    com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.UpdateServiceBuffer acquire = com.android.server.location.listeners.ListenerMultiplexer.this.mUpdateServiceBuffer.acquire();
                    try {
                        int size = arraySet.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            java.util.Map.Entry<TKey, com.android.server.location.listeners.ListenerRegistration<?>> valueAt = arraySet.valueAt(i2);
                            com.android.server.location.listeners.ListenerMultiplexer.this.removeRegistration(valueAt.getKey(), valueAt.getValue());
                        }
                        if (acquire != null) {
                            acquire.close();
                        }
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class UpdateServiceBuffer implements java.lang.AutoCloseable {

        @com.android.internal.annotations.GuardedBy({"this"})
        private int mBufferCount = 0;

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mUpdateServiceRequired = false;

        UpdateServiceBuffer() {
        }

        synchronized boolean isBuffered() {
            return this.mBufferCount != 0;
        }

        synchronized void markUpdateServiceRequired() {
            com.android.internal.util.Preconditions.checkState(isBuffered());
            this.mUpdateServiceRequired = true;
        }

        synchronized com.android.server.location.listeners.ListenerMultiplexer<TKey, TListener, TRegistration, TMergedRegistration>.UpdateServiceBuffer acquire() {
            this.mBufferCount++;
            return this;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            boolean z;
            synchronized (this) {
                try {
                    z = false;
                    com.android.internal.util.Preconditions.checkState(this.mBufferCount > 0);
                    int i = this.mBufferCount - 1;
                    this.mBufferCount = i;
                    if (i == 0) {
                        boolean z2 = this.mUpdateServiceRequired;
                        this.mUpdateServiceRequired = false;
                        z = z2;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z) {
                com.android.server.location.listeners.ListenerMultiplexer.this.updateService();
            }
        }
    }

    public static final class UpdateServiceLock implements java.lang.AutoCloseable {

        @android.annotation.Nullable
        private com.android.server.location.listeners.ListenerMultiplexer<?, ?, ?, ?>.UpdateServiceBuffer mUpdateServiceBuffer;

        UpdateServiceLock(com.android.server.location.listeners.ListenerMultiplexer<?, ?, ?, ?>.UpdateServiceBuffer updateServiceBuffer) {
            this.mUpdateServiceBuffer = updateServiceBuffer.acquire();
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            if (this.mUpdateServiceBuffer != null) {
                com.android.server.location.listeners.ListenerMultiplexer<?, ?, ?, ?>.UpdateServiceBuffer updateServiceBuffer = this.mUpdateServiceBuffer;
                this.mUpdateServiceBuffer = null;
                updateServiceBuffer.close();
            }
        }
    }
}
