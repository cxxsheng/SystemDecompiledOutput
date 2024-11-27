package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class BiometricServiceRegistry<T extends com.android.server.biometrics.sensors.BiometricServiceProvider<P>, P extends android.hardware.biometrics.SensorPropertiesInternal, C extends android.os.IInterface> {
    private static final java.lang.String TAG = "BiometricServiceRegistry";

    @android.annotation.Nullable
    private volatile java.util.List<P> mAllProps;

    @android.annotation.NonNull
    private final java.util.function.Supplier<android.hardware.biometrics.IBiometricService> mBiometricServiceSupplier;

    @android.annotation.NonNull
    private final android.os.RemoteCallbackList<C> mRegisteredCallbacks = new android.os.RemoteCallbackList<>();

    @android.annotation.Nullable
    private volatile java.util.List<T> mServiceProviders;

    protected abstract void invokeRegisteredCallback(@android.annotation.NonNull C c, @android.annotation.NonNull java.util.List<P> list) throws android.os.RemoteException;

    protected abstract void registerService(@android.annotation.NonNull android.hardware.biometrics.IBiometricService iBiometricService, @android.annotation.NonNull P p);

    public BiometricServiceRegistry(@android.annotation.NonNull java.util.function.Supplier<android.hardware.biometrics.IBiometricService> supplier) {
        this.mBiometricServiceSupplier = supplier;
    }

    public void registerAll(final java.util.function.Supplier<java.util.List<T>> supplier) {
        com.android.server.ServiceThread serviceThread = new com.android.server.ServiceThread(TAG, 10, true);
        serviceThread.start();
        new android.os.Handler(serviceThread.getLooper()).post(new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.BiometricServiceRegistry$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.biometrics.sensors.BiometricServiceRegistry.this.lambda$registerAll$0(supplier);
            }
        });
        serviceThread.quitSafely();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @com.android.internal.annotations.VisibleForTesting
    /* renamed from: registerAllInBackground, reason: merged with bridge method [inline-methods] */
    public void lambda$registerAll$0(java.util.function.Supplier<java.util.List<T>> supplier) {
        java.util.List<T> list = supplier.get();
        if (list == null) {
            list = new java.util.ArrayList<>();
        }
        android.hardware.biometrics.IBiometricService iBiometricService = this.mBiometricServiceSupplier.get();
        if (iBiometricService == null) {
            throw new java.lang.IllegalStateException("biometric service cannot be null");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            java.util.List sensorProperties = it.next().getSensorProperties();
            java.util.Iterator it2 = sensorProperties.iterator();
            while (it2.hasNext()) {
                registerService(iBiometricService, (android.hardware.biometrics.SensorPropertiesInternal) it2.next());
            }
            arrayList.addAll(sensorProperties);
        }
        finishRegistration(list, arrayList);
    }

    private synchronized void finishRegistration(@android.annotation.NonNull java.util.List<T> list, @android.annotation.NonNull java.util.List<P> list2) {
        this.mServiceProviders = java.util.Collections.unmodifiableList(list);
        this.mAllProps = java.util.Collections.unmodifiableList(list2);
        broadcastAllAuthenticatorsRegistered();
    }

    public synchronized void addAllRegisteredCallback(@android.annotation.Nullable C c) {
        if (c == null) {
            android.util.Slog.e(TAG, "addAllRegisteredCallback, callback is null");
            return;
        }
        boolean register = this.mRegisteredCallbacks.register(c);
        boolean z = this.mServiceProviders != null;
        if (register && z) {
            broadcastAllAuthenticatorsRegistered();
        } else if (!register) {
            android.util.Slog.e(TAG, "addAllRegisteredCallback failed to register callback");
        }
    }

    private synchronized void broadcastAllAuthenticatorsRegistered() {
        android.os.RemoteCallbackList<C> remoteCallbackList;
        int beginBroadcast = this.mRegisteredCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            C broadcastItem = this.mRegisteredCallbacks.getBroadcastItem(i);
            try {
                try {
                    invokeRegisteredCallback(broadcastItem, this.mAllProps);
                    remoteCallbackList = this.mRegisteredCallbacks;
                } catch (java.lang.Throwable th) {
                    this.mRegisteredCallbacks.unregister(broadcastItem);
                    throw th;
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Remote exception in broadcastAllAuthenticatorsRegistered", e);
                remoteCallbackList = this.mRegisteredCallbacks;
            }
            remoteCallbackList.unregister(broadcastItem);
        }
        this.mRegisteredCallbacks.finishBroadcast();
    }

    @android.annotation.NonNull
    public java.util.List<T> getProviders() {
        return this.mServiceProviders != null ? this.mServiceProviders : java.util.Collections.emptyList();
    }

    @android.annotation.Nullable
    public T getProviderForSensor(int i) {
        if (this.mServiceProviders != null) {
            for (T t : this.mServiceProviders) {
                if (t.containsSensor(i)) {
                    return t;
                }
            }
            return null;
        }
        return null;
    }

    @android.annotation.Nullable
    public android.util.Pair<java.lang.Integer, T> getSingleProvider() {
        java.lang.String str;
        if (this.mAllProps == null || this.mAllProps.isEmpty()) {
            android.util.Slog.e(TAG, "No sensors found");
            return null;
        }
        try {
            if (this.mAllProps.size() > 1) {
                android.util.Slog.e(TAG, "getSingleProvider() called but multiple sensors present: " + this.mAllProps.size());
            }
            int i = ((android.hardware.biometrics.SensorPropertiesInternal) this.mAllProps.get(0)).sensorId;
            T providerForSensor = getProviderForSensor(i);
            if (providerForSensor != null) {
                return new android.util.Pair<>(java.lang.Integer.valueOf(i), providerForSensor);
            }
            android.util.Slog.e(TAG, "Single sensor: " + i + ", but provider not found");
            return null;
        } catch (java.lang.NullPointerException e) {
            if (this.mAllProps == null) {
                str = "mAllProps: null";
            } else {
                str = "mAllProps.size(): " + this.mAllProps.size();
            }
            android.util.Slog.e(TAG, "This shouldn't happen. " + str, e);
            throw e;
        }
    }

    @android.annotation.NonNull
    public java.util.List<P> getAllProperties() {
        return this.mAllProps != null ? this.mAllProps : java.util.Collections.emptyList();
    }
}
