package android.hardware.soundtrigger;

/* loaded from: classes2.dex */
public class SoundTriggerModule {
    private static final int EVENT_MODEL_UNLOADED = 4;
    private static final int EVENT_RECOGNITION = 1;
    private static final int EVENT_RESOURCES_AVAILABLE = 3;
    private static final int EVENT_SERVICE_DIED = 2;
    private static final java.lang.String TAG = "SoundTriggerModule";
    private android.hardware.soundtrigger.SoundTriggerModule.EventHandlerDelegate mEventHandlerDelegate;
    private int mId;
    private android.media.soundtrigger_middleware.ISoundTriggerModule mService;

    public SoundTriggerModule(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService iSoundTriggerMiddlewareService, int i, android.hardware.soundtrigger.SoundTrigger.StatusListener statusListener, android.os.Looper looper, android.media.permission.Identity identity) {
        this.mId = i;
        this.mEventHandlerDelegate = new android.hardware.soundtrigger.SoundTriggerModule.EventHandlerDelegate(statusListener, looper);
        try {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                this.mService = iSoundTriggerMiddlewareService.attachAsOriginator(i, identity, this.mEventHandlerDelegate);
                if (create != null) {
                    create.close();
                }
                this.mService.asBinder().linkToDeath(this.mEventHandlerDelegate, 0);
            } finally {
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public SoundTriggerModule(android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService iSoundTriggerMiddlewareService, int i, android.hardware.soundtrigger.SoundTrigger.StatusListener statusListener, android.os.Looper looper, android.media.permission.Identity identity, android.media.permission.Identity identity2, boolean z) {
        this.mId = i;
        this.mEventHandlerDelegate = new android.hardware.soundtrigger.SoundTriggerModule.EventHandlerDelegate(statusListener, looper);
        try {
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                this.mService = iSoundTriggerMiddlewareService.attachAsMiddleman(i, identity, identity2, this.mEventHandlerDelegate, z);
                if (create != null) {
                    create.close();
                }
                this.mService.asBinder().linkToDeath(this.mEventHandlerDelegate, 0);
            } finally {
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void finalize() {
        detach();
    }

    @java.lang.Deprecated
    public synchronized void detach() {
        try {
            if (this.mService != null) {
                this.mService.asBinder().unlinkToDeath(this.mEventHandlerDelegate, 0);
                this.mService.detach();
                this.mService = null;
            }
        } catch (java.lang.Exception e) {
            android.hardware.soundtrigger.SoundTrigger.handleException(e);
        }
    }

    @java.lang.Deprecated
    public synchronized int loadSoundModel(android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel, int[] iArr) {
        try {
            if (soundModel instanceof android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) {
                android.media.soundtrigger.SoundModel api2aidlGenericSoundModel = android.hardware.soundtrigger.ConversionUtil.api2aidlGenericSoundModel((android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) soundModel);
                try {
                    iArr[0] = this.mService.loadModel(api2aidlGenericSoundModel);
                    return 0;
                } finally {
                    if (api2aidlGenericSoundModel.data != null) {
                        try {
                            api2aidlGenericSoundModel.data.close();
                        } catch (java.io.IOException e) {
                            android.util.Log.e(TAG, "Failed to close file", e);
                        }
                    }
                }
            }
            if (soundModel instanceof android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) {
                android.media.soundtrigger.PhraseSoundModel api2aidlPhraseSoundModel = android.hardware.soundtrigger.ConversionUtil.api2aidlPhraseSoundModel((android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) soundModel);
                try {
                    iArr[0] = this.mService.loadPhraseModel(api2aidlPhraseSoundModel);
                    return 0;
                } finally {
                    if (api2aidlPhraseSoundModel.common.data != null) {
                        try {
                            api2aidlPhraseSoundModel.common.data.close();
                        } catch (java.io.IOException e2) {
                            android.util.Log.e(TAG, "Failed to close file", e2);
                        }
                    }
                }
            }
            return android.hardware.soundtrigger.SoundTrigger.STATUS_BAD_VALUE;
        } catch (java.lang.Exception e3) {
            return android.hardware.soundtrigger.SoundTrigger.handleException(e3);
        }
    }

    @java.lang.Deprecated
    public synchronized int unloadSoundModel(int i) {
        try {
            this.mService.unloadModel(i);
        } catch (java.lang.Exception e) {
            return android.hardware.soundtrigger.SoundTrigger.handleException(e);
        }
        return 0;
    }

    @java.lang.Deprecated
    public synchronized int startRecognition(int i, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) {
        try {
            this.mService.startRecognition(i, android.hardware.soundtrigger.ConversionUtil.api2aidlRecognitionConfig(recognitionConfig));
        } catch (java.lang.Exception e) {
            return android.hardware.soundtrigger.SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized android.os.IBinder startRecognitionWithToken(int i, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) throws android.os.RemoteException {
        return this.mService.startRecognition(i, android.hardware.soundtrigger.ConversionUtil.api2aidlRecognitionConfig(recognitionConfig));
    }

    @java.lang.Deprecated
    public synchronized int stopRecognition(int i) {
        try {
            this.mService.stopRecognition(i);
        } catch (java.lang.Exception e) {
            return android.hardware.soundtrigger.SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int getModelState(int i) {
        try {
            this.mService.forceRecognitionEvent(i);
        } catch (java.lang.Exception e) {
            return android.hardware.soundtrigger.SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int setParameter(int i, int i2, int i3) {
        try {
            this.mService.setModelParameter(i, android.hardware.soundtrigger.ConversionUtil.api2aidlModelParameter(i2), i3);
        } catch (java.lang.Exception e) {
            return android.hardware.soundtrigger.SoundTrigger.handleException(e);
        }
        return 0;
    }

    public synchronized int getParameter(int i, int i2) {
        try {
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
        return this.mService.getModelParameter(i, android.hardware.soundtrigger.ConversionUtil.api2aidlModelParameter(i2));
    }

    public synchronized android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(int i, int i2) {
        try {
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
        return android.hardware.soundtrigger.ConversionUtil.aidl2apiModelParameterRange(this.mService.queryModelParameterSupport(i, android.hardware.soundtrigger.ConversionUtil.api2aidlModelParameter(i2)));
    }

    private class EventHandlerDelegate extends android.media.soundtrigger_middleware.ISoundTriggerCallback.Stub implements android.os.IBinder.DeathRecipient {
        private final android.os.Handler mHandler;

        EventHandlerDelegate(final android.hardware.soundtrigger.SoundTrigger.StatusListener statusListener, android.os.Looper looper) {
            this.mHandler = new android.os.Handler(looper) { // from class: android.hardware.soundtrigger.SoundTriggerModule.EventHandlerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    switch (message.what) {
                        case 1:
                            statusListener.onRecognition((android.hardware.soundtrigger.SoundTrigger.RecognitionEvent) message.obj);
                            break;
                        case 2:
                            statusListener.onServiceDied();
                            break;
                        case 3:
                            statusListener.onResourcesAvailable();
                            break;
                        case 4:
                            statusListener.onModelUnloaded(((java.lang.Integer) message.obj).intValue());
                            break;
                        default:
                            android.util.Log.e(android.hardware.soundtrigger.SoundTriggerModule.TAG, "Unknown message: " + message.toString());
                            break;
                    }
                }
            };
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onRecognition(int i, android.media.soundtrigger_middleware.RecognitionEventSys recognitionEventSys, int i2) throws android.os.RemoteException {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, android.hardware.soundtrigger.ConversionUtil.aidl2apiRecognitionEvent(i, i2, recognitionEventSys)));
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onPhraseRecognition(int i, android.media.soundtrigger_middleware.PhraseRecognitionEventSys phraseRecognitionEventSys, int i2) throws android.os.RemoteException {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, android.hardware.soundtrigger.ConversionUtil.aidl2apiPhraseRecognitionEvent(i, i2, phraseRecognitionEventSys)));
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public void onModelUnloaded(int i) throws android.os.RemoteException {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(4, java.lang.Integer.valueOf(i)));
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onResourcesAvailable() throws android.os.RemoteException {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
        }

        @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
        public synchronized void onModuleDied() {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
        }

        @Override // android.os.IBinder.DeathRecipient
        public synchronized void binderDied() {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
        }
    }
}
