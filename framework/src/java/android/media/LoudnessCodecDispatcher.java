package android.media;

/* loaded from: classes2.dex */
public class LoudnessCodecDispatcher implements android.media.CallbackUtil.DispatcherStub {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "LoudnessCodecDispatcher";
    private final android.media.IAudioService mAudioService;

    /* JADX INFO: Access modifiers changed from: private */
    static final class LoudnessCodecUpdatesDispatcherStub extends android.media.ILoudnessCodecUpdatesDispatcher.Stub {
        private static android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub sLoudnessCodecStub;
        private final android.media.CallbackUtil.LazyListenerManager<android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener> mLoudnessListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
        private final java.lang.Object mLock = new java.lang.Object();
        private final java.util.HashMap<android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener, android.media.LoudnessCodecController> mConfiguratorListener = new java.util.HashMap<>();

        public static synchronized android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub getInstance() {
            android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub loudnessCodecUpdatesDispatcherStub;
            synchronized (android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.class) {
                if (sLoudnessCodecStub == null) {
                    sLoudnessCodecStub = new android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub();
                }
                loudnessCodecUpdatesDispatcherStub = sLoudnessCodecStub;
            }
            return loudnessCodecUpdatesDispatcherStub;
        }

        private LoudnessCodecUpdatesDispatcherStub() {
        }

        @Override // android.media.ILoudnessCodecUpdatesDispatcher
        public void dispatchLoudnessCodecParameterChange(final int i, final android.os.PersistableBundle persistableBundle) {
            this.mLoudnessListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.this.lambda$dispatchLoudnessCodecParameterChange$2(i, persistableBundle, (android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchLoudnessCodecParameterChange$2(final int i, final android.os.PersistableBundle persistableBundle, android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener) {
            synchronized (this.mLock) {
                this.mConfiguratorListener.computeIfPresent(onLoudnessCodecUpdateListener, new java.util.function.BiFunction() { // from class: android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiFunction
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                        return android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.lambda$dispatchLoudnessCodecParameterChange$1(i, persistableBundle, (android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener) obj, (android.media.LoudnessCodecController) obj2);
                    }
                });
            }
        }

        static /* synthetic */ android.media.LoudnessCodecController lambda$dispatchLoudnessCodecParameterChange$1(int i, final android.os.PersistableBundle persistableBundle, final android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener, android.media.LoudnessCodecController loudnessCodecController) {
            if (loudnessCodecController.getSessionId() == i) {
                loudnessCodecController.mediaCodecsConsume(new java.util.function.Consumer() { // from class: android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.lambda$dispatchLoudnessCodecParameterChange$0(android.os.PersistableBundle.this, onLoudnessCodecUpdateListener, (java.util.Map.Entry) obj);
                    }
                });
            }
            return loudnessCodecController;
        }

        static /* synthetic */ void lambda$dispatchLoudnessCodecParameterChange$0(android.os.PersistableBundle persistableBundle, android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener, java.util.Map.Entry entry) {
            android.os.Bundle bundle;
            boolean z;
            java.lang.String num = java.lang.Integer.toString(((android.media.LoudnessCodecInfo) entry.getKey()).hashCode());
            if (!persistableBundle.containsKey(num)) {
                bundle = null;
            } else {
                bundle = new android.os.Bundle(persistableBundle.getPersistableBundle(num));
            }
            for (android.media.MediaCodec mediaCodec : (java.util.Set) entry.getValue()) {
                java.lang.String num2 = java.lang.Integer.toString(mediaCodec.hashCode());
                if (bundle != null || persistableBundle.containsKey(num2)) {
                    if (bundle != null) {
                        z = false;
                    } else {
                        bundle = new android.os.Bundle(persistableBundle.getPersistableBundle(num2));
                        z = true;
                    }
                    bundle = filterLoudnessParams(onLoudnessCodecUpdateListener.onLoudnessCodecUpdate(mediaCodec, bundle));
                    if (!bundle.isDefinitelyEmpty()) {
                        try {
                            mediaCodec.setParameters(bundle);
                        } catch (java.lang.IllegalStateException e) {
                            android.util.Log.w(android.media.LoudnessCodecDispatcher.TAG, "Cannot set loudness bundle on media codec " + mediaCodec);
                        }
                    }
                    if (z) {
                        return;
                    }
                }
            }
        }

        private static android.os.Bundle filterLoudnessParams(android.os.Bundle bundle) {
            android.os.Bundle bundle2 = new android.os.Bundle();
            if (bundle.containsKey(android.media.MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL)) {
                bundle2.putInt(android.media.MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL, bundle.getInt(android.media.MediaFormat.KEY_AAC_DRC_TARGET_REFERENCE_LEVEL));
            }
            if (bundle.containsKey(android.media.MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION)) {
                bundle2.putInt(android.media.MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION, bundle.getInt(android.media.MediaFormat.KEY_AAC_DRC_HEAVY_COMPRESSION));
            }
            if (bundle.containsKey(android.media.MediaFormat.KEY_AAC_DRC_EFFECT_TYPE)) {
                bundle2.putInt(android.media.MediaFormat.KEY_AAC_DRC_EFFECT_TYPE, bundle.getInt(android.media.MediaFormat.KEY_AAC_DRC_EFFECT_TYPE));
            }
            return bundle2;
        }

        void addLoudnessCodecListener(final android.media.CallbackUtil.DispatcherStub dispatcherStub, android.media.LoudnessCodecController loudnessCodecController, java.util.concurrent.Executor executor, android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener) {
            java.util.Objects.requireNonNull(loudnessCodecController);
            java.util.Objects.requireNonNull(executor);
            java.util.Objects.requireNonNull(onLoudnessCodecUpdateListener);
            this.mLoudnessListenerMgr.addListener(executor, onLoudnessCodecUpdateListener, "addLoudnessCodecListener", new java.util.function.Supplier() { // from class: android.media.LoudnessCodecDispatcher$LoudnessCodecUpdatesDispatcherStub$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.lambda$addLoudnessCodecListener$3(android.media.CallbackUtil.DispatcherStub.this);
                }
            });
            synchronized (this.mLock) {
                this.mConfiguratorListener.put(onLoudnessCodecUpdateListener, loudnessCodecController);
            }
        }

        static /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addLoudnessCodecListener$3(android.media.CallbackUtil.DispatcherStub dispatcherStub) {
            return dispatcherStub;
        }

        void removeLoudnessCodecListener(android.media.LoudnessCodecController loudnessCodecController) {
            android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener;
            java.util.Objects.requireNonNull(loudnessCodecController);
            synchronized (this.mLock) {
                java.util.Iterator<java.util.Map.Entry<android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener, android.media.LoudnessCodecController>> it = this.mConfiguratorListener.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        onLoudnessCodecUpdateListener = null;
                        break;
                    }
                    java.util.Map.Entry<android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener, android.media.LoudnessCodecController> next = it.next();
                    if (next.getValue() == loudnessCodecController) {
                        onLoudnessCodecUpdateListener = next.getKey();
                        it.remove();
                        break;
                    }
                }
            }
            if (onLoudnessCodecUpdateListener != null) {
                this.mLoudnessListenerMgr.removeListener(onLoudnessCodecUpdateListener, "removeLoudnessCodecListener");
            }
        }
    }

    public LoudnessCodecDispatcher(android.media.IAudioService iAudioService) {
        this.mAudioService = (android.media.IAudioService) java.util.Objects.requireNonNull(iAudioService);
    }

    @Override // android.media.CallbackUtil.DispatcherStub
    public void register(boolean z) {
        try {
            if (z) {
                this.mAudioService.registerLoudnessCodecUpdatesDispatcher(android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.getInstance());
            } else {
                this.mAudioService.unregisterLoudnessCodecUpdatesDispatcher(android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.getInstance());
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void addLoudnessCodecListener(android.media.LoudnessCodecController loudnessCodecController, java.util.concurrent.Executor executor, android.media.LoudnessCodecController.OnLoudnessCodecUpdateListener onLoudnessCodecUpdateListener) {
        android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.getInstance().addLoudnessCodecListener(this, loudnessCodecController, executor, onLoudnessCodecUpdateListener);
    }

    public void removeLoudnessCodecListener(android.media.LoudnessCodecController loudnessCodecController) {
        android.media.LoudnessCodecDispatcher.LoudnessCodecUpdatesDispatcherStub.getInstance().removeLoudnessCodecListener(loudnessCodecController);
    }

    public void startLoudnessCodecUpdates(int i) {
        try {
            this.mAudioService.startLoudnessCodecUpdates(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void stopLoudnessCodecUpdates(int i) {
        try {
            this.mAudioService.stopLoudnessCodecUpdates(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void addLoudnessCodecInfo(int i, int i2, android.media.LoudnessCodecInfo loudnessCodecInfo) {
        try {
            this.mAudioService.addLoudnessCodecInfo(i, i2, loudnessCodecInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void removeLoudnessCodecInfo(int i, android.media.LoudnessCodecInfo loudnessCodecInfo) {
        try {
            this.mAudioService.removeLoudnessCodecInfo(i, loudnessCodecInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public android.os.Bundle getLoudnessCodecParams(android.media.LoudnessCodecInfo loudnessCodecInfo) {
        try {
            return new android.os.Bundle(this.mAudioService.getLoudnessParams(loudnessCodecInfo));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }
}
