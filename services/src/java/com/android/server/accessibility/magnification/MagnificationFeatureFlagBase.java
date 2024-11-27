package com.android.server.accessibility.magnification;

/* loaded from: classes.dex */
abstract class MagnificationFeatureFlagBase {
    MagnificationFeatureFlagBase() {
    }

    abstract boolean getDefaultValue();

    abstract java.lang.String getFeatureName();

    abstract java.lang.String getNamespace();

    private void clearCallingIdentifyAndTryCatch(final java.lang.Runnable runnable, java.lang.Runnable runnable2) {
        try {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    runnable.run();
                }
            });
        } catch (java.lang.Throwable th) {
            runnable2.run();
        }
    }

    public boolean isFeatureFlagEnabled() {
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(getDefaultValue());
        clearCallingIdentifyAndTryCatch(new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationFeatureFlagBase.this.lambda$isFeatureFlagEnabled$1(atomicBoolean);
            }
        }, new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationFeatureFlagBase.this.lambda$isFeatureFlagEnabled$2(atomicBoolean);
            }
        });
        return atomicBoolean.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isFeatureFlagEnabled$1(java.util.concurrent.atomic.AtomicBoolean atomicBoolean) {
        atomicBoolean.set(android.provider.DeviceConfig.getBoolean(getNamespace(), getFeatureName(), getDefaultValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isFeatureFlagEnabled$2(java.util.concurrent.atomic.AtomicBoolean atomicBoolean) {
        atomicBoolean.set(getDefaultValue());
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean setFeatureFlagEnabled(final boolean z) {
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(getDefaultValue());
        clearCallingIdentifyAndTryCatch(new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationFeatureFlagBase.this.lambda$setFeatureFlagEnabled$3(atomicBoolean, z);
            }
        }, new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationFeatureFlagBase.this.lambda$setFeatureFlagEnabled$4(atomicBoolean);
            }
        });
        return atomicBoolean.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFeatureFlagEnabled$3(java.util.concurrent.atomic.AtomicBoolean atomicBoolean, boolean z) {
        atomicBoolean.set(android.provider.DeviceConfig.setProperty(getNamespace(), getFeatureName(), java.lang.Boolean.toString(z), false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFeatureFlagEnabled$4(java.util.concurrent.atomic.AtomicBoolean atomicBoolean) {
        atomicBoolean.set(getDefaultValue());
    }

    @android.annotation.NonNull
    public android.provider.DeviceConfig.OnPropertiesChangedListener addOnChangedListener(@android.annotation.NonNull final java.util.concurrent.Executor executor, @android.annotation.NonNull final java.lang.Runnable runnable) {
        final android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda1
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.accessibility.magnification.MagnificationFeatureFlagBase.this.lambda$addOnChangedListener$5(runnable, properties);
            }
        };
        clearCallingIdentifyAndTryCatch(new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationFeatureFlagBase.this.lambda$addOnChangedListener$6(executor, onPropertiesChangedListener);
            }
        }, new java.lang.Runnable() { // from class: com.android.server.accessibility.magnification.MagnificationFeatureFlagBase$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.accessibility.magnification.MagnificationFeatureFlagBase.lambda$addOnChangedListener$7();
            }
        });
        return onPropertiesChangedListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOnChangedListener$5(java.lang.Runnable runnable, android.provider.DeviceConfig.Properties properties) {
        if (properties.getKeyset().contains(getFeatureName())) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOnChangedListener$6(java.util.concurrent.Executor executor, android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        android.provider.DeviceConfig.addOnPropertiesChangedListener(getNamespace(), executor, onPropertiesChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addOnChangedListener$7() {
    }

    public void removeOnChangedListener(@android.annotation.NonNull android.provider.DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
        android.provider.DeviceConfig.removeOnPropertiesChangedListener(onPropertiesChangedListener);
    }
}
