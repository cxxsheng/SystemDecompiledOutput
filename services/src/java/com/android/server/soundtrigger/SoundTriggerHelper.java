package com.android.server.soundtrigger;

/* loaded from: classes2.dex */
public class SoundTriggerHelper implements android.hardware.soundtrigger.SoundTrigger.StatusListener {
    public static final int INVALID_MODULE_ID = -1;
    private static final int INVALID_VALUE = Integer.MIN_VALUE;
    public static final int STATUS_ERROR = Integer.MIN_VALUE;
    public static final int STATUS_OK = 0;
    static final java.lang.String TAG = "SoundTriggerHelper";
    private final android.content.Context mContext;
    private final com.android.server.utils.EventLogger mEventLogger;
    private android.hardware.soundtrigger.SoundTriggerModule mModule;
    private final int mModuleId;
    private final java.util.function.Supplier<java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties>> mModulePropertiesProvider;
    private final java.util.function.Function<android.hardware.soundtrigger.SoundTrigger.StatusListener, android.hardware.soundtrigger.SoundTriggerModule> mModuleProvider;
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.HashMap<java.util.UUID, com.android.server.soundtrigger.SoundTriggerHelper.ModelData> mModelDataMap = new java.util.HashMap<>();
    private final java.util.HashMap<java.lang.Integer, java.util.UUID> mKeyphraseUuidMap = new java.util.HashMap<>();
    private boolean mRecognitionRequested = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsDetached = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState mDeviceState = com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.DISABLE;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsAppOpPermitted = true;

    SoundTriggerHelper(android.content.Context context, com.android.server.utils.EventLogger eventLogger, @android.annotation.NonNull java.util.function.Function<android.hardware.soundtrigger.SoundTrigger.StatusListener, android.hardware.soundtrigger.SoundTriggerModule> function, int i, @android.annotation.NonNull java.util.function.Supplier<java.util.List<android.hardware.soundtrigger.SoundTrigger.ModuleProperties>> supplier) {
        this.mModuleId = i;
        this.mContext = context;
        this.mModuleProvider = function;
        this.mEventLogger = eventLogger;
        this.mModulePropertiesProvider = supplier;
        if (i == -1) {
            this.mModule = null;
        } else {
            this.mModule = this.mModuleProvider.apply(this);
        }
    }

    public int startGenericRecognition(java.util.UUID uuid, android.hardware.soundtrigger.SoundTrigger.GenericSoundModel genericSoundModel, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) {
        com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_start_recognition", 1);
        if (uuid == null || genericSoundModel == null || iRecognitionStatusCallback == null || recognitionConfig == null) {
            android.util.Slog.w(TAG, "Passed in bad data to startGenericRecognition().");
            return Integer.MIN_VALUE;
        }
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                com.android.server.soundtrigger.SoundTriggerHelper.ModelData orCreateGenericModelDataLocked = getOrCreateGenericModelDataLocked(uuid);
                if (orCreateGenericModelDataLocked == null) {
                    android.util.Slog.w(TAG, "Irrecoverable error occurred, check UUID / sound model data.");
                    return Integer.MIN_VALUE;
                }
                return startRecognition(genericSoundModel, orCreateGenericModelDataLocked, iRecognitionStatusCallback, recognitionConfig, Integer.MIN_VALUE, z);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int startKeyphraseRecognition(int i, android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel keyphraseSoundModel, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, boolean z) {
        com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData;
        synchronized (this.mLock) {
            try {
                com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_start_recognition", 1);
                if (keyphraseSoundModel == null || iRecognitionStatusCallback == null || recognitionConfig == null) {
                    return Integer.MIN_VALUE;
                }
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                com.android.server.soundtrigger.SoundTriggerHelper.ModelData keyphraseModelDataLocked = getKeyphraseModelDataLocked(i);
                if (keyphraseModelDataLocked != null && !keyphraseModelDataLocked.isKeyphraseModel()) {
                    android.util.Slog.e(TAG, "Generic model with same UUID exists.");
                    return Integer.MIN_VALUE;
                }
                if (keyphraseModelDataLocked != null && !keyphraseModelDataLocked.getModelId().equals(keyphraseSoundModel.getUuid())) {
                    int cleanUpExistingKeyphraseModelLocked = cleanUpExistingKeyphraseModelLocked(keyphraseModelDataLocked);
                    if (cleanUpExistingKeyphraseModelLocked != 0) {
                        return cleanUpExistingKeyphraseModelLocked;
                    }
                    removeKeyphraseModelLocked(i);
                    keyphraseModelDataLocked = null;
                }
                if (keyphraseModelDataLocked != null) {
                    modelData = keyphraseModelDataLocked;
                } else {
                    modelData = createKeyphraseModelDataLocked(keyphraseSoundModel.getUuid(), i);
                }
                return startRecognition(keyphraseSoundModel, modelData, iRecognitionStatusCallback, recognitionConfig, i, z);
            } finally {
            }
        }
    }

    private int cleanUpExistingKeyphraseModelLocked(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData) {
        int tryStopAndUnloadLocked = tryStopAndUnloadLocked(modelData, true, true);
        if (tryStopAndUnloadLocked != 0) {
            android.util.Slog.w(TAG, "Unable to stop or unload previous model: " + modelData.toString());
        }
        return tryStopAndUnloadLocked;
    }

    private int prepareForRecognition(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData) {
        if (this.mModule == null) {
            android.util.Slog.w(TAG, "prepareForRecognition: cannot attach to sound trigger module");
            return Integer.MIN_VALUE;
        }
        if (!modelData.isModelLoaded()) {
            stopAndUnloadDeadModelsLocked();
            int[] iArr = {0};
            int loadSoundModel = this.mModule.loadSoundModel(modelData.getSoundModel(), iArr);
            if (loadSoundModel != 0) {
                android.util.Slog.w(TAG, "prepareForRecognition: loadSoundModel failed with status: " + loadSoundModel);
                return loadSoundModel;
            }
            modelData.setHandle(iArr[0]);
            modelData.setLoaded();
        }
        return 0;
    }

    private int startRecognition(android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel, com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback, android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig, int i, boolean z) {
        boolean z2;
        boolean z3;
        int tryStopAndUnloadLocked;
        synchronized (this.mLock) {
            android.hardware.soundtrigger.IRecognitionStatusCallback callback = modelData.getCallback();
            if (callback != null && callback.asBinder() != iRecognitionStatusCallback.asBinder()) {
                android.util.Slog.w(TAG, "Canceling previous recognition for model id: " + modelData.getModelId());
                try {
                    callback.onPreempted();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "RemoteException in onDetectionStopped", e);
                }
                modelData.clearCallback();
            }
            if (modelData.getSoundModel() != null) {
                if (modelData.getSoundModel().equals(soundModel) && modelData.isModelStarted()) {
                    z2 = true;
                    z3 = false;
                } else if (modelData.getSoundModel().equals(soundModel)) {
                    z2 = false;
                    z3 = false;
                } else {
                    z2 = modelData.isModelStarted();
                    z3 = modelData.isModelLoaded();
                }
                if ((z2 || z3) && (tryStopAndUnloadLocked = tryStopAndUnloadLocked(modelData, z2, z3)) != 0) {
                    android.util.Slog.w(TAG, "Unable to stop or unload previous model: " + modelData.toString());
                    return tryStopAndUnloadLocked;
                }
            }
            modelData.setCallback(iRecognitionStatusCallback);
            modelData.setRequested(true);
            modelData.setRecognitionConfig(recognitionConfig);
            modelData.setRunInBatterySaverMode(z);
            modelData.setSoundModel(soundModel);
            if (isRecognitionAllowed(modelData)) {
                int updateRecognitionLocked = updateRecognitionLocked(modelData, false);
                if (updateRecognitionLocked == 0) {
                    return updateRecognitionLocked;
                }
                if (updateRecognitionLocked != android.hardware.soundtrigger.SoundTrigger.STATUS_BUSY) {
                    modelData.setRequested(false);
                    return updateRecognitionLocked;
                }
            }
            if (iRecognitionStatusCallback != null) {
                try {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.PAUSE, modelData.getModelId()));
                    iRecognitionStatusCallback.onRecognitionPaused();
                } catch (android.os.RemoteException e2) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.PAUSE, modelData.getModelId(), "RemoteException").printLog(2, TAG));
                    forceStopAndUnloadModelLocked(modelData, e2);
                }
            }
            return 0;
        }
    }

    public int stopGenericRecognition(java.util.UUID uuid, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) {
        synchronized (this.mLock) {
            try {
                com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_stop_recognition", 1);
                if (iRecognitionStatusCallback == null || uuid == null) {
                    android.util.Slog.e(TAG, "Null callbackreceived for stopGenericRecognition() for modelid:" + uuid);
                    return Integer.MIN_VALUE;
                }
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData = this.mModelDataMap.get(uuid);
                if (modelData == null || !modelData.isGenericModel()) {
                    android.util.Slog.w(TAG, "Attempting stopRecognition on invalid model with id:" + uuid);
                    return Integer.MIN_VALUE;
                }
                int stopRecognition = stopRecognition(modelData, iRecognitionStatusCallback);
                if (stopRecognition != 0) {
                    android.util.Slog.w(TAG, "stopGenericRecognition failed: " + stopRecognition);
                }
                return stopRecognition;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int stopKeyphraseRecognition(int i, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) {
        synchronized (this.mLock) {
            try {
                com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_stop_recognition", 1);
                if (iRecognitionStatusCallback == null) {
                    android.util.Slog.e(TAG, "Null callback received for stopKeyphraseRecognition() for keyphraseId:" + i);
                    return Integer.MIN_VALUE;
                }
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                com.android.server.soundtrigger.SoundTriggerHelper.ModelData keyphraseModelDataLocked = getKeyphraseModelDataLocked(i);
                if (keyphraseModelDataLocked == null || !keyphraseModelDataLocked.isKeyphraseModel()) {
                    android.util.Slog.w(TAG, "No model exists for given keyphrase Id " + i);
                    return Integer.MIN_VALUE;
                }
                int stopRecognition = stopRecognition(keyphraseModelDataLocked, iRecognitionStatusCallback);
                return stopRecognition != 0 ? stopRecognition : stopRecognition;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int stopRecognition(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) {
        synchronized (this.mLock) {
            try {
                if (iRecognitionStatusCallback == null) {
                    return Integer.MIN_VALUE;
                }
                if (this.mModule == null) {
                    android.util.Slog.w(TAG, "Attempting stopRecognition after detach");
                    return Integer.MIN_VALUE;
                }
                android.hardware.soundtrigger.IRecognitionStatusCallback callback = modelData.getCallback();
                if (callback != null && (modelData.isRequested() || modelData.isModelStarted())) {
                    if (callback.asBinder() != iRecognitionStatusCallback.asBinder()) {
                        android.util.Slog.w(TAG, "Attempting stopRecognition for another recognition");
                        return Integer.MIN_VALUE;
                    }
                    modelData.setRequested(false);
                    int updateRecognitionLocked = updateRecognitionLocked(modelData, false);
                    if (updateRecognitionLocked != 0) {
                        return updateRecognitionLocked;
                    }
                    modelData.setLoaded();
                    modelData.clearCallback();
                    modelData.setRecognitionConfig(null);
                    return updateRecognitionLocked;
                }
                android.util.Slog.w(TAG, "Attempting stopRecognition without a successful startRecognition");
                return Integer.MIN_VALUE;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int tryStopAndUnloadLocked(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, boolean z, boolean z2) {
        int i = 0;
        if (modelData.isModelNotLoaded()) {
            return 0;
        }
        if (z && modelData.isModelStarted() && (i = stopRecognitionLocked(modelData, false)) != 0) {
            android.util.Slog.w(TAG, "stopRecognition failed: " + i);
            return i;
        }
        if (z2 && modelData.isModelLoaded()) {
            android.util.Slog.d(TAG, "Unloading previously loaded stale model.");
            if (this.mModule == null) {
                return Integer.MIN_VALUE;
            }
            i = this.mModule.unloadSoundModel(modelData.getHandle());
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_unloading_stale_model", 1);
            if (i != 0) {
                android.util.Slog.w(TAG, "unloadSoundModel call failed with " + i);
            } else {
                modelData.clearState();
            }
        }
        return i;
    }

    public android.hardware.soundtrigger.SoundTrigger.ModuleProperties getModuleProperties() {
        synchronized (this.mLock) {
            if (this.mIsDetached) {
                throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
            }
        }
        for (android.hardware.soundtrigger.SoundTrigger.ModuleProperties moduleProperties : this.mModulePropertiesProvider.get()) {
            if (moduleProperties.getId() == this.mModuleId) {
                return moduleProperties;
            }
        }
        android.util.Slog.e(TAG, "Module properties not found for existing moduleId " + this.mModuleId);
        return null;
    }

    public int unloadKeyphraseSoundModel(int i) {
        synchronized (this.mLock) {
            try {
                com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_unload_keyphrase_sound_model", 1);
                com.android.server.soundtrigger.SoundTriggerHelper.ModelData keyphraseModelDataLocked = getKeyphraseModelDataLocked(i);
                if (this.mModule != null && keyphraseModelDataLocked != null && keyphraseModelDataLocked.isModelLoaded() && keyphraseModelDataLocked.isKeyphraseModel()) {
                    if (this.mIsDetached) {
                        throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                    }
                    keyphraseModelDataLocked.setRequested(false);
                    int updateRecognitionLocked = updateRecognitionLocked(keyphraseModelDataLocked, false);
                    if (updateRecognitionLocked != 0) {
                        android.util.Slog.w(TAG, "Stop recognition failed for keyphrase ID:" + updateRecognitionLocked);
                    }
                    int unloadSoundModel = this.mModule.unloadSoundModel(keyphraseModelDataLocked.getHandle());
                    if (unloadSoundModel != 0) {
                        android.util.Slog.w(TAG, "unloadKeyphraseSoundModel call failed with " + unloadSoundModel);
                    }
                    removeKeyphraseModelLocked(i);
                    return unloadSoundModel;
                }
                return Integer.MIN_VALUE;
            } finally {
            }
        }
    }

    public int unloadGenericSoundModel(java.util.UUID uuid) {
        int stopRecognitionLocked;
        synchronized (this.mLock) {
            try {
                com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_unload_generic_sound_model", 1);
                if (uuid == null || this.mModule == null) {
                    return Integer.MIN_VALUE;
                }
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData = this.mModelDataMap.get(uuid);
                if (modelData == null || !modelData.isGenericModel()) {
                    android.util.Slog.w(TAG, "Unload error: Attempting unload invalid generic model with id:" + uuid);
                    return Integer.MIN_VALUE;
                }
                if (!modelData.isModelLoaded()) {
                    android.util.Slog.i(TAG, "Unload: Given generic model is not loaded:" + uuid);
                    return 0;
                }
                if (modelData.isModelStarted() && (stopRecognitionLocked = stopRecognitionLocked(modelData, false)) != 0) {
                    android.util.Slog.w(TAG, "stopGenericRecognition failed: " + stopRecognitionLocked);
                }
                if (this.mModule == null) {
                    return Integer.MIN_VALUE;
                }
                int unloadSoundModel = this.mModule.unloadSoundModel(modelData.getHandle());
                if (unloadSoundModel != 0) {
                    android.util.Slog.w(TAG, "unloadGenericSoundModel() call failed with " + unloadSoundModel);
                    android.util.Slog.w(TAG, "unloadGenericSoundModel() force-marking model as unloaded.");
                }
                this.mModelDataMap.remove(uuid);
                return unloadSoundModel;
            } finally {
            }
        }
    }

    public boolean isRecognitionRequested(java.util.UUID uuid) {
        boolean z;
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData = this.mModelDataMap.get(uuid);
                z = modelData != null && modelData.isRequested();
            } finally {
            }
        }
        return z;
    }

    public void onDeviceStateChanged(com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState soundTriggerDeviceState) {
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached || this.mDeviceState == soundTriggerDeviceState) {
                    return;
                }
                this.mDeviceState = soundTriggerDeviceState;
                updateAllRecognitionsLocked();
            } finally {
            }
        }
    }

    public void onAppOpStateChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mIsAppOpPermitted == z) {
                    return;
                }
                this.mIsAppOpPermitted = z;
                updateAllRecognitionsLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public int getGenericModelState(java.util.UUID uuid) {
        synchronized (this.mLock) {
            try {
                com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_get_generic_model_state", 1);
                if (uuid == null || this.mModule == null) {
                    return Integer.MIN_VALUE;
                }
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData = this.mModelDataMap.get(uuid);
                if (modelData == null || !modelData.isGenericModel()) {
                    android.util.Slog.w(TAG, "GetGenericModelState error: Invalid generic model id:" + uuid);
                    return Integer.MIN_VALUE;
                }
                if (!modelData.isModelLoaded()) {
                    android.util.Slog.i(TAG, "GetGenericModelState: Given generic model is not loaded:" + uuid);
                    return Integer.MIN_VALUE;
                }
                if (!modelData.isModelStarted()) {
                    android.util.Slog.i(TAG, "GetGenericModelState: Given generic model is not started:" + uuid);
                    return Integer.MIN_VALUE;
                }
                return this.mModule.getModelState(modelData.getHandle());
            } finally {
            }
        }
    }

    public int setParameter(java.util.UUID uuid, @android.hardware.soundtrigger.ModelParams int i, int i2) {
        int parameterLocked;
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                parameterLocked = setParameterLocked(this.mModelDataMap.get(uuid), i, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return parameterLocked;
    }

    public int setKeyphraseParameter(int i, @android.hardware.soundtrigger.ModelParams int i2, int i3) {
        int parameterLocked;
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                parameterLocked = setParameterLocked(getKeyphraseModelDataLocked(i), i2, i3);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return parameterLocked;
    }

    private int setParameterLocked(@android.annotation.Nullable com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, @android.hardware.soundtrigger.ModelParams int i, int i2) {
        com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_set_parameter", 1);
        if (this.mModule == null) {
            return android.hardware.soundtrigger.SoundTrigger.STATUS_NO_INIT;
        }
        if (modelData == null || !modelData.isModelLoaded()) {
            android.util.Slog.i(TAG, "SetParameter: Given model is not loaded:" + modelData);
            return android.hardware.soundtrigger.SoundTrigger.STATUS_BAD_VALUE;
        }
        return this.mModule.setParameter(modelData.getHandle(), i, i2);
    }

    public int getParameter(@android.annotation.NonNull java.util.UUID uuid, @android.hardware.soundtrigger.ModelParams int i) {
        int parameterLocked;
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                parameterLocked = getParameterLocked(this.mModelDataMap.get(uuid), i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return parameterLocked;
    }

    public int getKeyphraseParameter(int i, @android.hardware.soundtrigger.ModelParams int i2) {
        int parameterLocked;
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                parameterLocked = getParameterLocked(getKeyphraseModelDataLocked(i), i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return parameterLocked;
    }

    private int getParameterLocked(@android.annotation.Nullable com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, @android.hardware.soundtrigger.ModelParams int i) {
        com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_get_parameter", 1);
        if (this.mModule == null) {
            throw new java.lang.UnsupportedOperationException("SoundTriggerModule not initialized");
        }
        if (modelData == null) {
            throw new java.lang.IllegalArgumentException("Invalid model id");
        }
        if (!modelData.isModelLoaded()) {
            throw new java.lang.UnsupportedOperationException("Given model is not loaded:" + modelData);
        }
        return this.mModule.getParameter(modelData.getHandle(), i);
    }

    @android.annotation.Nullable
    public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameter(@android.annotation.NonNull java.util.UUID uuid, @android.hardware.soundtrigger.ModelParams int i) {
        android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameterLocked;
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                queryParameterLocked = queryParameterLocked(this.mModelDataMap.get(uuid), i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return queryParameterLocked;
    }

    @android.annotation.Nullable
    public android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryKeyphraseParameter(int i, @android.hardware.soundtrigger.ModelParams int i2) {
        android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameterLocked;
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    throw new java.lang.IllegalStateException("SoundTriggerHelper has been detached");
                }
                queryParameterLocked = queryParameterLocked(getKeyphraseModelDataLocked(i), i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return queryParameterLocked;
    }

    @android.annotation.Nullable
    private android.hardware.soundtrigger.SoundTrigger.ModelParamRange queryParameterLocked(@android.annotation.Nullable com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, @android.hardware.soundtrigger.ModelParams int i) {
        com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_query_parameter", 1);
        if (this.mModule == null) {
            return null;
        }
        if (modelData == null) {
            android.util.Slog.w(TAG, "queryParameter: Invalid model id");
            return null;
        }
        if (!modelData.isModelLoaded()) {
            android.util.Slog.i(TAG, "queryParameter: Given model is not loaded:" + modelData);
            return null;
        }
        return this.mModule.queryParameter(modelData.getHandle(), i);
    }

    public void onRecognition(android.hardware.soundtrigger.SoundTrigger.RecognitionEvent recognitionEvent) {
        if (recognitionEvent == null) {
            android.util.Slog.w(TAG, "Null recognition event!");
            return;
        }
        if (!(recognitionEvent instanceof android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent) && !(recognitionEvent instanceof android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent)) {
            android.util.Slog.w(TAG, "Invalid recognition event type (not one of generic or keyphrase)!");
            return;
        }
        synchronized (this.mLock) {
            try {
                switch (recognitionEvent.status) {
                    case 0:
                    case 2:
                    case 3:
                        if (isKeyphraseRecognitionEvent(recognitionEvent)) {
                            onKeyphraseRecognitionLocked((android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent) recognitionEvent);
                            break;
                        } else {
                            onGenericRecognitionLocked((android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent) recognitionEvent);
                            break;
                        }
                    case 1:
                        onRecognitionAbortLocked(recognitionEvent);
                        break;
                }
            } finally {
            }
        }
    }

    private boolean isKeyphraseRecognitionEvent(android.hardware.soundtrigger.SoundTrigger.RecognitionEvent recognitionEvent) {
        return recognitionEvent instanceof android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onGenericRecognitionLocked(android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
        com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_generic_recognition_event", 1);
        if (genericRecognitionEvent.status != 0 && genericRecognitionEvent.status != 3) {
            return;
        }
        com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelDataForLocked = getModelDataForLocked(genericRecognitionEvent.soundModelHandle);
        if (modelDataForLocked == null || !modelDataForLocked.isGenericModel()) {
            android.util.Slog.w(TAG, "Generic recognition event: Model does not exist for handle: " + genericRecognitionEvent.soundModelHandle);
            return;
        }
        if (!java.util.Objects.equals(genericRecognitionEvent.getToken(), modelDataForLocked.getToken())) {
            return;
        }
        android.hardware.soundtrigger.IRecognitionStatusCallback callback = modelDataForLocked.getCallback();
        if (callback == null) {
            android.util.Slog.w(TAG, "Generic recognition event: Null callback for model handle: " + genericRecognitionEvent.soundModelHandle);
            return;
        }
        if (!genericRecognitionEvent.recognitionStillActive) {
            modelDataForLocked.setStopped();
        }
        try {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RECOGNITION, modelDataForLocked.getModelId()));
            callback.onGenericSoundTriggerDetected(genericRecognitionEvent);
            android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig = modelDataForLocked.getRecognitionConfig();
            if (recognitionConfig == null) {
                android.util.Slog.w(TAG, "Generic recognition event: Null RecognitionConfig for model handle: " + genericRecognitionEvent.soundModelHandle);
                return;
            }
            modelDataForLocked.setRequested(recognitionConfig.allowMultipleTriggers);
            if (modelDataForLocked.isRequested()) {
                updateRecognitionLocked(modelDataForLocked, true);
            }
        } catch (android.os.RemoteException e) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RECOGNITION, modelDataForLocked.getModelId(), "RemoteException").printLog(2, TAG));
            forceStopAndUnloadModelLocked(modelDataForLocked, e);
        }
    }

    public void onModelUnloaded(int i) {
        synchronized (this.mLock) {
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_sound_model_updated", 1);
            onModelUnloadedLocked(i);
        }
    }

    public void onResourcesAvailable() {
        synchronized (this.mLock) {
            onResourcesAvailableLocked();
        }
    }

    public void onServiceDied() {
        android.util.Slog.e(TAG, "onServiceDied!!");
        com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_service_died", 1);
        synchronized (this.mLock) {
            onServiceDiedLocked();
        }
    }

    private void onModelUnloadedLocked(int i) {
        com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelDataForLocked = getModelDataForLocked(i);
        if (modelDataForLocked != null) {
            modelDataForLocked.setNotLoaded();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onResourcesAvailableLocked() {
        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RESOURCES_AVAILABLE, null));
        updateAllRecognitionsLocked();
    }

    private void onRecognitionAbortLocked(android.hardware.soundtrigger.SoundTrigger.RecognitionEvent recognitionEvent) {
        android.util.Slog.w(TAG, "Recognition aborted");
        com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_recognition_aborted", 1);
        com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelDataForLocked = getModelDataForLocked(recognitionEvent.soundModelHandle);
        if (modelDataForLocked == null || !modelDataForLocked.isModelStarted() || !java.util.Objects.equals(recognitionEvent.getToken(), modelDataForLocked.getToken())) {
            return;
        }
        modelDataForLocked.setStopped();
        try {
            android.hardware.soundtrigger.IRecognitionStatusCallback callback = modelDataForLocked.getCallback();
            if (callback != null) {
                this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.PAUSE, modelDataForLocked.getModelId()));
                callback.onRecognitionPaused();
            }
        } catch (android.os.RemoteException e) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.PAUSE, modelDataForLocked.getModelId(), "RemoteException").printLog(2, TAG));
            forceStopAndUnloadModelLocked(modelDataForLocked, e);
        }
    }

    private int getKeyphraseIdFromEvent(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
        if (keyphraseRecognitionEvent == null) {
            android.util.Slog.w(TAG, "Null RecognitionEvent received.");
            return Integer.MIN_VALUE;
        }
        android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra[] keyphraseRecognitionExtraArr = keyphraseRecognitionEvent.keyphraseExtras;
        if (keyphraseRecognitionExtraArr == null || keyphraseRecognitionExtraArr.length == 0) {
            android.util.Slog.w(TAG, "Invalid keyphrase recognition event!");
            return Integer.MIN_VALUE;
        }
        return keyphraseRecognitionExtraArr[0].id;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void onKeyphraseRecognitionLocked(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {
        android.util.Slog.i(TAG, "Recognition success");
        com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_keyphrase_recognition_event", 1);
        int keyphraseIdFromEvent = getKeyphraseIdFromEvent(keyphraseRecognitionEvent);
        com.android.server.soundtrigger.SoundTriggerHelper.ModelData keyphraseModelDataLocked = getKeyphraseModelDataLocked(keyphraseIdFromEvent);
        if (keyphraseModelDataLocked == null || !keyphraseModelDataLocked.isKeyphraseModel()) {
            android.util.Slog.e(TAG, "Keyphase model data does not exist for ID:" + keyphraseIdFromEvent);
            return;
        }
        if (!java.util.Objects.equals(keyphraseRecognitionEvent.getToken(), keyphraseModelDataLocked.getToken())) {
            return;
        }
        if (keyphraseModelDataLocked.getCallback() == null) {
            android.util.Slog.w(TAG, "Received onRecognition event without callback for keyphrase model.");
            return;
        }
        if (!keyphraseRecognitionEvent.recognitionStillActive) {
            keyphraseModelDataLocked.setStopped();
        }
        try {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RECOGNITION, keyphraseModelDataLocked.getModelId()));
            keyphraseModelDataLocked.getCallback().onKeyphraseDetected(keyphraseRecognitionEvent);
            android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig = keyphraseModelDataLocked.getRecognitionConfig();
            if (recognitionConfig != null) {
                keyphraseModelDataLocked.setRequested(recognitionConfig.allowMultipleTriggers);
            }
            if (keyphraseModelDataLocked.isRequested()) {
                updateRecognitionLocked(keyphraseModelDataLocked, true);
            }
        } catch (android.os.RemoteException e) {
            this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RECOGNITION, keyphraseModelDataLocked.getModelId(), "RemoteException").printLog(2, TAG));
            forceStopAndUnloadModelLocked(keyphraseModelDataLocked, e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateAllRecognitionsLocked() {
        java.util.Iterator it = new java.util.ArrayList(this.mModelDataMap.values()).iterator();
        while (it.hasNext()) {
            updateRecognitionLocked((com.android.server.soundtrigger.SoundTriggerHelper.ModelData) it.next(), true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int updateRecognitionLocked(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, boolean z) {
        boolean z2 = modelData.isRequested() && isRecognitionAllowed(modelData);
        if (z2 == modelData.isModelStarted()) {
            return 0;
        }
        if (z2) {
            int prepareForRecognition = prepareForRecognition(modelData);
            if (prepareForRecognition != 0) {
                android.util.Slog.w(TAG, "startRecognition failed to prepare model for recognition");
                return prepareForRecognition;
            }
            return startRecognitionLocked(modelData, z);
        }
        return stopRecognitionLocked(modelData, z);
    }

    private void onServiceDiedLocked() {
        try {
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_service_died", 1);
            for (com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData : this.mModelDataMap.values()) {
                android.hardware.soundtrigger.IRecognitionStatusCallback callback = modelData.getCallback();
                if (callback != null) {
                    try {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.MODULE_DIED, modelData.getModelId()).printLog(2, TAG));
                        callback.onModuleDied();
                    } catch (android.os.RemoteException e) {
                        this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.MODULE_DIED, modelData.getModelId(), "RemoteException").printLog(2, TAG));
                    }
                }
            }
            internalClearModelStateLocked();
            if (this.mModule != null) {
                this.mModule.detach();
                try {
                    this.mModule = this.mModuleProvider.apply(this);
                } catch (java.lang.Exception e2) {
                    this.mModule = null;
                }
            }
        } catch (java.lang.Throwable th) {
            internalClearModelStateLocked();
            if (this.mModule != null) {
                this.mModule.detach();
                try {
                    this.mModule = this.mModuleProvider.apply(this);
                } catch (java.lang.Exception e3) {
                    this.mModule = null;
                }
            }
            throw th;
        }
    }

    private void internalClearModelStateLocked() {
        java.util.Iterator<com.android.server.soundtrigger.SoundTriggerHelper.ModelData> it = this.mModelDataMap.values().iterator();
        while (it.hasNext()) {
            it.next().clearState();
        }
    }

    public void detach() {
        synchronized (this.mLock) {
            try {
                if (this.mIsDetached) {
                    return;
                }
                this.mIsDetached = true;
                java.util.Iterator<com.android.server.soundtrigger.SoundTriggerHelper.ModelData> it = this.mModelDataMap.values().iterator();
                while (it.hasNext()) {
                    forceStopAndUnloadModelLocked(it.next(), null);
                }
                this.mModelDataMap.clear();
                if (this.mModule != null) {
                    this.mModule.detach();
                    this.mModule = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void forceStopAndUnloadModelLocked(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, java.lang.Exception exc) {
        forceStopAndUnloadModelLocked(modelData, exc, null);
    }

    private void forceStopAndUnloadModelLocked(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, java.lang.Exception exc, java.util.Iterator it) {
        if (exc != null) {
            android.util.Slog.e(TAG, "forceStopAndUnloadModel", exc);
        }
        if (this.mModule == null) {
            return;
        }
        if (modelData.isModelStarted()) {
            android.util.Slog.d(TAG, "Stopping previously started dangling model " + modelData.getHandle());
            if (this.mModule.stopRecognition(modelData.getHandle()) == 0) {
                modelData.setStopped();
                modelData.setRequested(false);
            } else {
                android.util.Slog.e(TAG, "Failed to stop model " + modelData.getHandle());
            }
        }
        if (modelData.isModelLoaded()) {
            android.util.Slog.d(TAG, "Unloading previously loaded dangling model " + modelData.getHandle());
            if (this.mModule.unloadSoundModel(modelData.getHandle()) == 0) {
                if (it != null) {
                    it.remove();
                } else {
                    this.mModelDataMap.remove(modelData.getModelId());
                }
                java.util.Iterator<java.util.Map.Entry<java.lang.Integer, java.util.UUID>> it2 = this.mKeyphraseUuidMap.entrySet().iterator();
                while (it2.hasNext()) {
                    if (it2.next().getValue().equals(modelData.getModelId())) {
                        it2.remove();
                    }
                }
                modelData.clearState();
                return;
            }
            android.util.Slog.e(TAG, "Failed to unload model " + modelData.getHandle());
        }
    }

    private void stopAndUnloadDeadModelsLocked() {
        java.util.Iterator<java.util.Map.Entry<java.util.UUID, com.android.server.soundtrigger.SoundTriggerHelper.ModelData>> it = this.mModelDataMap.entrySet().iterator();
        while (it.hasNext()) {
            com.android.server.soundtrigger.SoundTriggerHelper.ModelData value = it.next().getValue();
            if (value.isModelLoaded() && (value.getCallback() == null || (value.getCallback().asBinder() != null && !value.getCallback().asBinder().pingBinder()))) {
                android.util.Slog.w(TAG, "Removing model " + value.getHandle() + " that has no clients");
                forceStopAndUnloadModelLocked(value, null, it);
            }
        }
    }

    private com.android.server.soundtrigger.SoundTriggerHelper.ModelData getOrCreateGenericModelDataLocked(java.util.UUID uuid) {
        com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData = this.mModelDataMap.get(uuid);
        if (modelData == null) {
            com.android.server.soundtrigger.SoundTriggerHelper.ModelData createGenericModelData = com.android.server.soundtrigger.SoundTriggerHelper.ModelData.createGenericModelData(uuid);
            this.mModelDataMap.put(uuid, createGenericModelData);
            return createGenericModelData;
        }
        if (!modelData.isGenericModel()) {
            android.util.Slog.e(TAG, "UUID already used for non-generic model.");
            return null;
        }
        return modelData;
    }

    private void removeKeyphraseModelLocked(int i) {
        java.util.UUID uuid = this.mKeyphraseUuidMap.get(java.lang.Integer.valueOf(i));
        if (uuid == null) {
            return;
        }
        this.mModelDataMap.remove(uuid);
        this.mKeyphraseUuidMap.remove(java.lang.Integer.valueOf(i));
    }

    private com.android.server.soundtrigger.SoundTriggerHelper.ModelData getKeyphraseModelDataLocked(int i) {
        java.util.UUID uuid = this.mKeyphraseUuidMap.get(java.lang.Integer.valueOf(i));
        if (uuid == null) {
            return null;
        }
        return this.mModelDataMap.get(uuid);
    }

    private com.android.server.soundtrigger.SoundTriggerHelper.ModelData createKeyphraseModelDataLocked(java.util.UUID uuid, int i) {
        this.mKeyphraseUuidMap.remove(java.lang.Integer.valueOf(i));
        this.mModelDataMap.remove(uuid);
        this.mKeyphraseUuidMap.put(java.lang.Integer.valueOf(i), uuid);
        com.android.server.soundtrigger.SoundTriggerHelper.ModelData createKeyphraseModelData = com.android.server.soundtrigger.SoundTriggerHelper.ModelData.createKeyphraseModelData(uuid);
        this.mModelDataMap.put(uuid, createKeyphraseModelData);
        return createKeyphraseModelData;
    }

    private com.android.server.soundtrigger.SoundTriggerHelper.ModelData getModelDataForLocked(int i) {
        for (com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData : this.mModelDataMap.values()) {
            if (modelData.getHandle() == i) {
                return modelData;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isRecognitionAllowed(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData) {
        if (!this.mIsAppOpPermitted) {
            return false;
        }
        switch (com.android.server.soundtrigger.SoundTriggerHelper.AnonymousClass1.$SwitchMap$com$android$server$soundtrigger$DeviceStateHandler$SoundTriggerDeviceState[this.mDeviceState.ordinal()]) {
            case 1:
                return false;
            case 2:
                return modelData.shouldRunInBatterySaverMode();
            case 3:
                return true;
            default:
                throw new java.lang.AssertionError("Enum changed between compile and runtime");
        }
    }

    /* renamed from: com.android.server.soundtrigger.SoundTriggerHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$server$soundtrigger$DeviceStateHandler$SoundTriggerDeviceState = new int[com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.values().length];

        static {
            try {
                $SwitchMap$com$android$server$soundtrigger$DeviceStateHandler$SoundTriggerDeviceState[com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.DISABLE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$server$soundtrigger$DeviceStateHandler$SoundTriggerDeviceState[com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.CRITICAL.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$server$soundtrigger$DeviceStateHandler$SoundTriggerDeviceState[com.android.server.soundtrigger.DeviceStateHandler.SoundTriggerDeviceState.ENABLE.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int startRecognitionLocked(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, boolean z) {
        int handleException;
        android.hardware.soundtrigger.IRecognitionStatusCallback callback = modelData.getCallback();
        android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig = modelData.getRecognitionConfig();
        if (callback == null || !modelData.isModelLoaded() || recognitionConfig == null) {
            android.util.Slog.w(TAG, "startRecognition: Bad data passed in.");
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_start_recognition_error", 1);
            return Integer.MIN_VALUE;
        }
        if (!isRecognitionAllowed(modelData)) {
            android.util.Slog.w(TAG, "startRecognition requested but not allowed.");
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_start_recognition_not_allowed", 1);
            return 0;
        }
        if (this.mModule == null) {
            return Integer.MIN_VALUE;
        }
        try {
            modelData.setToken(this.mModule.startRecognitionWithToken(modelData.getHandle(), recognitionConfig));
            handleException = 0;
        } catch (java.lang.Exception e) {
            handleException = android.hardware.soundtrigger.SoundTrigger.handleException(e);
        }
        if (handleException != 0) {
            android.util.Slog.w(TAG, "startRecognition failed with " + handleException);
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_start_recognition_error", 1);
            if (z) {
                try {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RESUME_FAILED, modelData.getModelId(), java.lang.String.valueOf(handleException)).printLog(2, TAG));
                    modelData.setRequested(false);
                    callback.onResumeFailed(handleException);
                } catch (android.os.RemoteException e2) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RESUME_FAILED, modelData.getModelId(), java.lang.String.valueOf(handleException) + " - RemoteException").printLog(2, TAG));
                    forceStopAndUnloadModelLocked(modelData, e2);
                }
            }
        } else {
            android.util.Slog.i(TAG, "startRecognition successful.");
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_start_recognition_success", 1);
            modelData.setStarted();
            if (z) {
                try {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RESUME, modelData.getModelId()));
                    callback.onRecognitionResumed();
                } catch (android.os.RemoteException e3) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.RESUME, modelData.getModelId(), "RemoteException").printLog(2, TAG));
                    forceStopAndUnloadModelLocked(modelData, e3);
                }
            }
        }
        return handleException;
    }

    private int stopRecognitionLocked(com.android.server.soundtrigger.SoundTriggerHelper.ModelData modelData, boolean z) {
        if (this.mModule == null) {
            return Integer.MIN_VALUE;
        }
        android.hardware.soundtrigger.IRecognitionStatusCallback callback = modelData.getCallback();
        int stopRecognition = this.mModule.stopRecognition(modelData.getHandle());
        if (stopRecognition != 0) {
            android.util.Slog.e(TAG, "stopRecognition call failed with " + stopRecognition);
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_stop_recognition_error", 1);
            if (z) {
                try {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.PAUSE_FAILED, modelData.getModelId(), java.lang.String.valueOf(stopRecognition)).printLog(2, TAG));
                    modelData.setRequested(false);
                    callback.onPauseFailed(stopRecognition);
                } catch (android.os.RemoteException e) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.PAUSE_FAILED, modelData.getModelId(), java.lang.String.valueOf(stopRecognition) + " - RemoteException").printLog(2, TAG));
                    forceStopAndUnloadModelLocked(modelData, e);
                }
            }
        } else {
            modelData.setStopped();
            com.android.internal.logging.MetricsLogger.count(this.mContext, "sth_stop_recognition_success", 1);
            if (z) {
                try {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.PAUSE, modelData.getModelId()));
                    callback.onRecognitionPaused();
                } catch (android.os.RemoteException e2) {
                    this.mEventLogger.enqueue(new com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type.PAUSE, modelData.getModelId(), "RemoteException").printLog(2, TAG));
                    forceStopAndUnloadModelLocked(modelData, e2);
                }
            }
        }
        return stopRecognition;
    }

    private boolean computeRecognitionRequestedLocked() {
        if (this.mModule == null) {
            this.mRecognitionRequested = false;
            return this.mRecognitionRequested;
        }
        java.util.Iterator<com.android.server.soundtrigger.SoundTriggerHelper.ModelData> it = this.mModelDataMap.values().iterator();
        while (it.hasNext()) {
            if (it.next().isRequested()) {
                this.mRecognitionRequested = true;
                return this.mRecognitionRequested;
            }
        }
        this.mRecognitionRequested = false;
        return this.mRecognitionRequested;
    }

    private static class ModelData {
        static final int MODEL_LOADED = 1;
        static final int MODEL_NOTLOADED = 0;
        static final int MODEL_STARTED = 2;
        private int mModelHandle;
        private java.util.UUID mModelId;
        private int mModelState;
        private int mModelType;
        private boolean mRequested = false;
        private android.hardware.soundtrigger.IRecognitionStatusCallback mCallback = null;
        private android.hardware.soundtrigger.SoundTrigger.RecognitionConfig mRecognitionConfig = null;
        public boolean mRunInBatterySaverMode = false;
        private android.hardware.soundtrigger.SoundTrigger.SoundModel mSoundModel = null;
        private android.os.IBinder mRecognitionToken = null;

        private ModelData(java.util.UUID uuid, int i) {
            this.mModelType = -1;
            this.mModelId = uuid;
            this.mModelType = i;
        }

        static com.android.server.soundtrigger.SoundTriggerHelper.ModelData createKeyphraseModelData(java.util.UUID uuid) {
            return new com.android.server.soundtrigger.SoundTriggerHelper.ModelData(uuid, 0);
        }

        static com.android.server.soundtrigger.SoundTriggerHelper.ModelData createGenericModelData(java.util.UUID uuid) {
            return new com.android.server.soundtrigger.SoundTriggerHelper.ModelData(uuid, 1);
        }

        static com.android.server.soundtrigger.SoundTriggerHelper.ModelData createModelDataOfUnknownType(java.util.UUID uuid) {
            return new com.android.server.soundtrigger.SoundTriggerHelper.ModelData(uuid, -1);
        }

        synchronized void setCallback(android.hardware.soundtrigger.IRecognitionStatusCallback iRecognitionStatusCallback) {
            this.mCallback = iRecognitionStatusCallback;
        }

        synchronized android.hardware.soundtrigger.IRecognitionStatusCallback getCallback() {
            return this.mCallback;
        }

        synchronized boolean isModelLoaded() {
            boolean z;
            z = true;
            if (this.mModelState != 1) {
                if (this.mModelState != 2) {
                    z = false;
                }
            }
            return z;
        }

        synchronized boolean isModelNotLoaded() {
            return this.mModelState == 0;
        }

        synchronized void setStarted() {
            this.mModelState = 2;
        }

        synchronized void setStopped() {
            this.mRecognitionToken = null;
            this.mModelState = 1;
        }

        synchronized void setLoaded() {
            this.mModelState = 1;
        }

        synchronized void setNotLoaded() {
            this.mRecognitionToken = null;
            this.mModelState = 0;
        }

        synchronized boolean isModelStarted() {
            return this.mModelState == 2;
        }

        synchronized void clearState() {
            this.mModelState = 0;
            this.mRecognitionToken = null;
            this.mRecognitionConfig = null;
            this.mRequested = false;
            this.mCallback = null;
        }

        synchronized void clearCallback() {
            this.mCallback = null;
        }

        synchronized void setHandle(int i) {
            this.mModelHandle = i;
        }

        synchronized void setRecognitionConfig(android.hardware.soundtrigger.SoundTrigger.RecognitionConfig recognitionConfig) {
            this.mRecognitionConfig = recognitionConfig;
        }

        synchronized void setRunInBatterySaverMode(boolean z) {
            this.mRunInBatterySaverMode = z;
        }

        synchronized boolean shouldRunInBatterySaverMode() {
            return this.mRunInBatterySaverMode;
        }

        synchronized int getHandle() {
            return this.mModelHandle;
        }

        synchronized java.util.UUID getModelId() {
            return this.mModelId;
        }

        synchronized android.hardware.soundtrigger.SoundTrigger.RecognitionConfig getRecognitionConfig() {
            return this.mRecognitionConfig;
        }

        synchronized boolean isRequested() {
            return this.mRequested;
        }

        synchronized void setRequested(boolean z) {
            this.mRequested = z;
        }

        synchronized void setSoundModel(android.hardware.soundtrigger.SoundTrigger.SoundModel soundModel) {
            this.mSoundModel = soundModel;
        }

        synchronized android.hardware.soundtrigger.SoundTrigger.SoundModel getSoundModel() {
            return this.mSoundModel;
        }

        synchronized android.os.IBinder getToken() {
            return this.mRecognitionToken;
        }

        synchronized void setToken(android.os.IBinder iBinder) {
            this.mRecognitionToken = iBinder;
        }

        synchronized int getModelType() {
            return this.mModelType;
        }

        synchronized boolean isKeyphraseModel() {
            return this.mModelType == 0;
        }

        synchronized boolean isGenericModel() {
            return this.mModelType == 1;
        }

        synchronized java.lang.String stateToString() {
            switch (this.mModelState) {
                case 0:
                    return "NOT_LOADED";
                case 1:
                    return "LOADED";
                case 2:
                    return "STARTED";
                default:
                    return "Unknown state";
            }
        }

        synchronized java.lang.String requestedToString() {
            java.lang.StringBuilder sb;
            try {
                sb = new java.lang.StringBuilder();
                sb.append("Requested: ");
                sb.append(this.mRequested ? "Yes" : "No");
            } catch (java.lang.Throwable th) {
                throw th;
            }
            return sb.toString();
        }

        synchronized java.lang.String callbackToString() {
            java.lang.StringBuilder sb;
            try {
                sb = new java.lang.StringBuilder();
                sb.append("Callback: ");
                sb.append(this.mCallback != null ? this.mCallback.asBinder() : "null");
            } catch (java.lang.Throwable th) {
                throw th;
            }
            return sb.toString();
        }

        synchronized java.lang.String uuidToString() {
            return "UUID: " + this.mModelId;
        }

        public synchronized java.lang.String toString() {
            return "Handle: " + this.mModelHandle + "\nModelState: " + stateToString() + "\n" + requestedToString() + "\n" + callbackToString() + "\n" + uuidToString() + "\n" + modelTypeToString() + "RunInBatterySaverMode=" + this.mRunInBatterySaverMode;
        }

        synchronized java.lang.String modelTypeToString() {
            java.lang.String str;
            try {
                switch (this.mModelType) {
                    case -1:
                        str = "Unknown";
                        break;
                    case 0:
                        str = "Keyphrase";
                        break;
                    case 1:
                        str = "Generic";
                        break;
                    default:
                        str = null;
                        break;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
            return "Model type: " + str + "\n";
        }
    }
}
