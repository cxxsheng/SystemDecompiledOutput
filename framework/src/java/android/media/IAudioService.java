package android.media;

/* loaded from: classes2.dex */
public interface IAudioService extends android.os.IInterface {
    int abandonAudioFocus(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) throws android.os.RemoteException;

    int abandonAudioFocusForTest(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) throws android.os.RemoteException;

    void addAssistantServicesUids(int[] iArr) throws android.os.RemoteException;

    void addLoudnessCodecInfo(int i, int i2, android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException;

    int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    void addOnDevicesForAttributesChangedListener(android.media.AudioAttributes audioAttributes, android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) throws android.os.RemoteException;

    void addSpatializerCompatibleAudioDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    void adjustStreamVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException;

    void adjustStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException;

    void adjustStreamVolumeWithAttribution(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void adjustSuggestedStreamVolume(int i, int i2, int i3) throws android.os.RemoteException;

    void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException;

    void adjustVolume(int i, int i2) throws android.os.RemoteException;

    void adjustVolumeGroupVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException;

    boolean areNavigationRepeatSoundEffectsEnabled() throws android.os.RemoteException;

    boolean canBeSpatialized(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat) throws android.os.RemoteException;

    void cancelMuteAwaitConnection(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    int clearFadeManagerConfigurationForFocusLoss() throws android.os.RemoteException;

    int clearPreferredDevicesForCapturePreset(int i) throws android.os.RemoteException;

    int clearPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i) throws android.os.RemoteException;

    void disableSafeMediaVolume(java.lang.String str) throws android.os.RemoteException;

    int dispatchFocusChange(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    int dispatchFocusChangeWithFade(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, java.util.List<android.media.AudioFocusInfo> list, android.media.FadeManagerConfiguration fadeManagerConfiguration) throws android.os.RemoteException;

    boolean enterAudioFocusFreezeForTest(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException;

    boolean exitAudioFocusFreezeForTest(android.os.IBinder iBinder) throws android.os.RemoteException;

    void forceComputeCsdOnAllDevices(boolean z) throws android.os.RemoteException;

    void forceRemoteSubmixFullVolume(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException;

    void forceUseFrameworkMel(boolean z) throws android.os.RemoteException;

    void forceVolumeControlStream(int i, android.os.IBinder iBinder) throws android.os.RemoteException;

    int[] getActiveAssistantServiceUids() throws android.os.RemoteException;

    java.util.List<android.media.AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws android.os.RemoteException;

    java.util.List<android.media.AudioRecordingConfiguration> getActiveRecordingConfigurations() throws android.os.RemoteException;

    int getActualHeadTrackingMode() throws android.os.RemoteException;

    long getAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    int getAllowedCapturePolicy() throws android.os.RemoteException;

    int[] getAssistantServicesUids() throws android.os.RemoteException;

    java.util.List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() throws android.os.RemoteException;

    java.util.List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws android.os.RemoteException;

    int[] getAvailableCommunicationDeviceIds() throws android.os.RemoteException;

    int getBluetoothAudioDeviceCategory(java.lang.String str) throws android.os.RemoteException;

    int getBluetoothAudioDeviceCategory_legacy(java.lang.String str, boolean z) throws android.os.RemoteException;

    int getCommunicationDevice() throws android.os.RemoteException;

    float getCsd() throws android.os.RemoteException;

    int getCurrentAudioFocus() throws android.os.RemoteException;

    android.media.VolumeInfo getDefaultVolumeInfo() throws android.os.RemoteException;

    int getDesiredHeadTrackingMode() throws android.os.RemoteException;

    int getDeviceMaskForStream(int i) throws android.os.RemoteException;

    android.media.VolumeInfo getDeviceVolume(android.media.VolumeInfo volumeInfo, android.media.AudioDeviceAttributes audioDeviceAttributes, java.lang.String str) throws android.os.RemoteException;

    int getDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    java.util.List<android.media.AudioDeviceAttributes> getDevicesForAttributes(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException;

    java.util.List<android.media.AudioDeviceAttributes> getDevicesForAttributesUnprotected(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException;

    int getEncodedSurroundMode(int i) throws android.os.RemoteException;

    android.media.FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() throws android.os.RemoteException;

    long getFadeOutDurationOnFocusLossMillis(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException;

    java.util.List getFocusDuckedUidsForTest() throws android.os.RemoteException;

    long getFocusFadeOutDurationForTest() throws android.os.RemoteException;

    int getFocusRampTimeMs(int i, android.media.AudioAttributes audioAttributes) throws android.os.RemoteException;

    java.util.List<android.media.AudioFocusInfo> getFocusStack() throws android.os.RemoteException;

    long getFocusUnmuteDelayAfterFadeOutForTest() throws android.os.RemoteException;

    android.media.AudioHalVersionInfo getHalVersion() throws android.os.RemoteException;

    java.util.List getIndependentStreamTypes() throws android.os.RemoteException;

    int getLastAudibleStreamVolume(int i) throws android.os.RemoteException;

    int getLastAudibleVolumeForVolumeGroup(int i) throws android.os.RemoteException;

    android.os.PersistableBundle getLoudnessParams(android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException;

    long getMaxAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    int getMode() throws android.os.RemoteException;

    android.media.AudioDeviceAttributes getMutingExpectedDevice() throws android.os.RemoteException;

    java.util.List<android.media.AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int i) throws android.os.RemoteException;

    float getOutputRs2UpperBound() throws android.os.RemoteException;

    java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) throws android.os.RemoteException;

    java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForStrategy(int i) throws android.os.RemoteException;

    java.util.List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() throws android.os.RemoteException;

    java.util.List getReportedSurroundFormats() throws android.os.RemoteException;

    int getRingerModeExternal() throws android.os.RemoteException;

    int getRingerModeInternal() throws android.os.RemoteException;

    android.media.IRingtonePlayer getRingtonePlayer() throws android.os.RemoteException;

    java.util.List<android.media.AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() throws android.os.RemoteException;

    int getSpatializerImmersiveAudioLevel() throws android.os.RemoteException;

    int getSpatializerOutput() throws android.os.RemoteException;

    void getSpatializerParameter(int i, byte[] bArr) throws android.os.RemoteException;

    int getStreamMaxVolume(int i) throws android.os.RemoteException;

    int getStreamMinVolume(int i) throws android.os.RemoteException;

    int getStreamTypeAlias(int i) throws android.os.RemoteException;

    int getStreamVolume(int i) throws android.os.RemoteException;

    int[] getSupportedHeadTrackingModes() throws android.os.RemoteException;

    int[] getSupportedSystemUsages() throws android.os.RemoteException;

    java.util.Map getSurroundFormats() throws android.os.RemoteException;

    int getUiSoundsStreamType() throws android.os.RemoteException;

    int getVibrateSetting(int i) throws android.os.RemoteException;

    android.media.IVolumeController getVolumeController() throws android.os.RemoteException;

    int getVolumeGroupMaxVolumeIndex(int i) throws android.os.RemoteException;

    int getVolumeGroupMinVolumeIndex(int i) throws android.os.RemoteException;

    int getVolumeGroupVolumeIndex(int i) throws android.os.RemoteException;

    void handleBluetoothActiveDeviceChanged(android.bluetooth.BluetoothDevice bluetoothDevice, android.bluetooth.BluetoothDevice bluetoothDevice2, android.media.BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) throws android.os.RemoteException;

    void handleVolumeKey(android.view.KeyEvent keyEvent, boolean z, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    boolean hasHapticChannels(android.net.Uri uri) throws android.os.RemoteException;

    boolean hasHeadTracker(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    boolean hasRegisteredDynamicPolicy() throws android.os.RemoteException;

    boolean isAudioServerRunning() throws android.os.RemoteException;

    boolean isBluetoothA2dpOn() throws android.os.RemoteException;

    boolean isBluetoothAudioDeviceCategoryFixed(java.lang.String str) throws android.os.RemoteException;

    boolean isBluetoothScoOn() throws android.os.RemoteException;

    boolean isBluetoothVariableLatencyEnabled() throws android.os.RemoteException;

    boolean isCallScreeningModeSupported() throws android.os.RemoteException;

    boolean isCameraSoundForced() throws android.os.RemoteException;

    boolean isCsdAsAFeatureAvailable() throws android.os.RemoteException;

    boolean isCsdAsAFeatureEnabled() throws android.os.RemoteException;

    boolean isCsdEnabled() throws android.os.RemoteException;

    boolean isHdmiSystemAudioSupported() throws android.os.RemoteException;

    boolean isHeadTrackerAvailable() throws android.os.RemoteException;

    boolean isHeadTrackerEnabled(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    boolean isHomeSoundEffectEnabled() throws android.os.RemoteException;

    boolean isHotwordStreamSupported(boolean z) throws android.os.RemoteException;

    boolean isMasterMute() throws android.os.RemoteException;

    boolean isMicrophoneMuted() throws android.os.RemoteException;

    boolean isMusicActive(boolean z) throws android.os.RemoteException;

    boolean isPstnCallAudioInterceptable() throws android.os.RemoteException;

    boolean isSpatializerAvailable() throws android.os.RemoteException;

    boolean isSpatializerAvailableForDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    boolean isSpatializerEnabled() throws android.os.RemoteException;

    boolean isSpeakerphoneOn() throws android.os.RemoteException;

    boolean isStreamAffectedByMute(int i) throws android.os.RemoteException;

    boolean isStreamAffectedByRingerMode(int i) throws android.os.RemoteException;

    boolean isStreamMute(int i) throws android.os.RemoteException;

    boolean isSurroundFormatEnabled(int i) throws android.os.RemoteException;

    boolean isUltrasoundSupported() throws android.os.RemoteException;

    boolean isValidRingerMode(int i) throws android.os.RemoteException;

    boolean isVolumeControlUsingVolumeGroups() throws android.os.RemoteException;

    boolean isVolumeFixed() throws android.os.RemoteException;

    boolean isVolumeGroupMuted(int i) throws android.os.RemoteException;

    boolean loadSoundEffects() throws android.os.RemoteException;

    void lowerVolumeToRs1(java.lang.String str) throws android.os.RemoteException;

    void muteAwaitConnection(int[] iArr, android.media.AudioDeviceAttributes audioDeviceAttributes, long j) throws android.os.RemoteException;

    void notifyVolumeControllerVisible(android.media.IVolumeController iVolumeController, boolean z) throws android.os.RemoteException;

    void playSoundEffect(int i, int i2) throws android.os.RemoteException;

    void playSoundEffectVolume(int i, float f) throws android.os.RemoteException;

    void playerAttributes(int i, android.media.AudioAttributes audioAttributes) throws android.os.RemoteException;

    void playerEvent(int i, int i2, int i3) throws android.os.RemoteException;

    void playerHasOpPlayAudio(int i, boolean z) throws android.os.RemoteException;

    void playerSessionId(int i, int i2) throws android.os.RemoteException;

    void portEvent(int i, int i2, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    void recenterHeadTracker() throws android.os.RemoteException;

    void recorderEvent(int i, int i2) throws android.os.RemoteException;

    java.lang.String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException;

    void registerAudioServerStateDispatcher(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) throws android.os.RemoteException;

    void registerCapturePresetDevicesRoleDispatcher(android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws android.os.RemoteException;

    void registerCommunicationDeviceDispatcher(android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws android.os.RemoteException;

    void registerDeviceVolumeBehaviorDispatcher(boolean z, android.media.IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) throws android.os.RemoteException;

    void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, android.media.IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, java.lang.String str, android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, boolean z2, int i) throws android.os.RemoteException;

    void registerHeadToSoundstagePoseCallback(android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws android.os.RemoteException;

    void registerLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws android.os.RemoteException;

    void registerModeDispatcher(android.media.IAudioModeDispatcher iAudioModeDispatcher) throws android.os.RemoteException;

    void registerMuteAwaitConnectionDispatcher(android.media.IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) throws android.os.RemoteException;

    void registerPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws android.os.RemoteException;

    void registerPreferredMixerAttributesDispatcher(android.media.IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws android.os.RemoteException;

    void registerRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) throws android.os.RemoteException;

    void registerSpatializerCallback(android.media.ISpatializerCallback iSpatializerCallback) throws android.os.RemoteException;

    void registerSpatializerHeadTrackerAvailableCallback(android.media.ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) throws android.os.RemoteException;

    void registerSpatializerHeadTrackingCallback(android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws android.os.RemoteException;

    void registerSpatializerOutputCallback(android.media.ISpatializerOutputCallback iSpatializerOutputCallback) throws android.os.RemoteException;

    void registerStrategyNonDefaultDevicesDispatcher(android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws android.os.RemoteException;

    void registerStrategyPreferredDevicesDispatcher(android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws android.os.RemoteException;

    void registerStreamAliasingDispatcher(android.media.IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) throws android.os.RemoteException;

    void releasePlayer(int i) throws android.os.RemoteException;

    void releaseRecorder(int i) throws android.os.RemoteException;

    void reloadAudioSettings() throws android.os.RemoteException;

    void removeAssistantServicesUids(int[] iArr) throws android.os.RemoteException;

    int removeDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    void removeLoudnessCodecInfo(int i, android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException;

    int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    void removeOnDevicesForAttributesChangedListener(android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) throws android.os.RemoteException;

    int removePreferredDevicesForStrategy(int i) throws android.os.RemoteException;

    void removeSpatializerCompatibleAudioDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    int removeUidDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) throws android.os.RemoteException;

    int removeUserIdDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) throws android.os.RemoteException;

    int requestAudioFocus(android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i3) throws android.os.RemoteException;

    int requestAudioFocusForTest(android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, java.lang.String str2, int i2, int i3, int i4) throws android.os.RemoteException;

    boolean sendFocusLoss(android.media.AudioFocusInfo audioFocusInfo, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    void setA2dpSuspended(boolean z) throws android.os.RemoteException;

    void setActiveAssistantServiceUids(int[] iArr) throws android.os.RemoteException;

    boolean setAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes, long j) throws android.os.RemoteException;

    int setAllowedCapturePolicy(int i) throws android.os.RemoteException;

    void setBluetoothA2dpOn(boolean z) throws android.os.RemoteException;

    boolean setBluetoothAudioDeviceCategory(java.lang.String str, int i) throws android.os.RemoteException;

    void setBluetoothAudioDeviceCategory_legacy(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    void setBluetoothScoOn(boolean z) throws android.os.RemoteException;

    void setBluetoothVariableLatencyEnabled(boolean z) throws android.os.RemoteException;

    boolean setCommunicationDevice(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void setCsd(float f) throws android.os.RemoteException;

    void setCsdAsAFeatureEnabled(boolean z) throws android.os.RemoteException;

    void setDesiredHeadTrackingMode(int i) throws android.os.RemoteException;

    int setDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    void setDeviceVolume(android.media.VolumeInfo volumeInfo, android.media.AudioDeviceAttributes audioDeviceAttributes, java.lang.String str) throws android.os.RemoteException;

    void setDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) throws android.os.RemoteException;

    boolean setEncodedSurroundMode(int i) throws android.os.RemoteException;

    int setFadeManagerConfigurationForFocusLoss(android.media.FadeManagerConfiguration fadeManagerConfiguration) throws android.os.RemoteException;

    int setFocusPropertiesForPolicy(int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    void setFocusRequestResultFromExtPolicy(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    int setHdmiSystemAudioSupported(boolean z) throws android.os.RemoteException;

    void setHeadTrackerEnabled(boolean z, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException;

    void setHomeSoundEffectEnabled(boolean z) throws android.os.RemoteException;

    void setLeAudioSuspended(boolean z) throws android.os.RemoteException;

    void setMasterMute(boolean z, int i, java.lang.String str, int i2, java.lang.String str2) throws android.os.RemoteException;

    void setMicrophoneMute(boolean z, java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    void setMicrophoneMuteFromSwitch(boolean z) throws android.os.RemoteException;

    void setMode(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException;

    void setMultiAudioFocusEnabled(boolean z) throws android.os.RemoteException;

    void setNavigationRepeatSoundEffectsEnabled(boolean z) throws android.os.RemoteException;

    void setNotifAliasRingForTest(boolean z) throws android.os.RemoteException;

    void setOutputRs2UpperBound(float f) throws android.os.RemoteException;

    int setPreferredDevicesForCapturePreset(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException;

    int setPreferredDevicesForStrategy(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException;

    int setPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i, android.media.AudioMixerAttributes audioMixerAttributes) throws android.os.RemoteException;

    void setRingerModeExternal(int i, java.lang.String str) throws android.os.RemoteException;

    void setRingerModeInternal(int i, java.lang.String str) throws android.os.RemoteException;

    void setRingtonePlayer(android.media.IRingtonePlayer iRingtonePlayer) throws android.os.RemoteException;

    void setRttEnabled(boolean z) throws android.os.RemoteException;

    void setSpatializerEnabled(boolean z) throws android.os.RemoteException;

    void setSpatializerGlobalTransform(float[] fArr) throws android.os.RemoteException;

    void setSpatializerParameter(int i, byte[] bArr) throws android.os.RemoteException;

    void setSpeakerphoneOn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    void setStreamVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException;

    void setStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException;

    void setStreamVolumeWithAttribution(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setSupportedSystemUsages(int[] iArr) throws android.os.RemoteException;

    boolean setSurroundFormatEnabled(int i, boolean z) throws android.os.RemoteException;

    void setTestDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) throws android.os.RemoteException;

    int setUidDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException;

    int setUserIdDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException;

    void setVibrateSetting(int i, int i2) throws android.os.RemoteException;

    void setVolumeController(android.media.IVolumeController iVolumeController) throws android.os.RemoteException;

    void setVolumeGroupVolumeIndex(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setVolumePolicy(android.media.VolumePolicy volumePolicy) throws android.os.RemoteException;

    void setWiredDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) throws android.os.RemoteException;

    boolean shouldNotificationSoundPlay(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException;

    boolean shouldVibrate(int i) throws android.os.RemoteException;

    void startBluetoothSco(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void startBluetoothScoVirtualCall(android.os.IBinder iBinder) throws android.os.RemoteException;

    void startLoudnessCodecUpdates(int i) throws android.os.RemoteException;

    android.media.AudioRoutesInfo startWatchingRoutes(android.media.IAudioRoutesObserver iAudioRoutesObserver) throws android.os.RemoteException;

    void stopBluetoothSco(android.os.IBinder iBinder) throws android.os.RemoteException;

    void stopLoudnessCodecUpdates(int i) throws android.os.RemoteException;

    boolean supportsBluetoothVariableLatency() throws android.os.RemoteException;

    int trackPlayer(android.media.PlayerBase.PlayerIdCard playerIdCard) throws android.os.RemoteException;

    int trackRecorder(android.os.IBinder iBinder) throws android.os.RemoteException;

    void unloadSoundEffects() throws android.os.RemoteException;

    void unregisterAudioFocusClient(java.lang.String str) throws android.os.RemoteException;

    void unregisterAudioPolicy(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    void unregisterAudioPolicyAsync(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    void unregisterAudioServerStateDispatcher(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) throws android.os.RemoteException;

    void unregisterCapturePresetDevicesRoleDispatcher(android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws android.os.RemoteException;

    void unregisterCommunicationDeviceDispatcher(android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws android.os.RemoteException;

    void unregisterHeadToSoundstagePoseCallback(android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws android.os.RemoteException;

    void unregisterLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws android.os.RemoteException;

    void unregisterModeDispatcher(android.media.IAudioModeDispatcher iAudioModeDispatcher) throws android.os.RemoteException;

    void unregisterPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws android.os.RemoteException;

    void unregisterPreferredMixerAttributesDispatcher(android.media.IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws android.os.RemoteException;

    void unregisterRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) throws android.os.RemoteException;

    void unregisterSpatializerCallback(android.media.ISpatializerCallback iSpatializerCallback) throws android.os.RemoteException;

    void unregisterSpatializerHeadTrackingCallback(android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws android.os.RemoteException;

    void unregisterSpatializerOutputCallback(android.media.ISpatializerOutputCallback iSpatializerOutputCallback) throws android.os.RemoteException;

    void unregisterStrategyNonDefaultDevicesDispatcher(android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws android.os.RemoteException;

    void unregisterStrategyPreferredDevicesDispatcher(android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws android.os.RemoteException;

    int updateMixingRulesForPolicy(android.media.audiopolicy.AudioMix[] audioMixArr, android.media.audiopolicy.AudioMixingRule[] audioMixingRuleArr, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException;

    public static class Default implements android.media.IAudioService {
        @Override // android.media.IAudioService
        public int trackPlayer(android.media.PlayerBase.PlayerIdCard playerIdCard) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playerAttributes(int i, android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerEvent(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void releasePlayer(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int trackRecorder(android.os.IBinder iBinder) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void recorderEvent(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void releaseRecorder(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerSessionId(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void portEvent(int i, int i2, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolumeWithAttribution(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolumeWithAttribution(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setDeviceVolume(android.media.VolumeInfo volumeInfo, android.media.AudioDeviceAttributes audioDeviceAttributes, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public android.media.VolumeInfo getDeviceVolume(android.media.VolumeInfo volumeInfo, android.media.AudioDeviceAttributes audioDeviceAttributes, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void handleVolumeKey(android.view.KeyEvent keyEvent, boolean z, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isStreamMute(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void forceRemoteSubmixFullVolume(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isMasterMute() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMasterMute(boolean z, int i, java.lang.String str, int i2, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getStreamVolume(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamMinVolume(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamMaxVolume(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void setVolumeGroupVolumeIndex(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupVolumeIndex(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupMaxVolumeIndex(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getVolumeGroupMinVolumeIndex(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getLastAudibleVolumeForVolumeGroup(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isVolumeGroupMuted(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void adjustVolumeGroupVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getLastAudibleStreamVolume(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setSupportedSystemUsages(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int[] getSupportedSystemUsages() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean isMicrophoneMuted() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isUltrasoundSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHotwordStreamSupported(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMicrophoneMute(boolean z, java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setMicrophoneMuteFromSwitch(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingerModeExternal(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingerModeInternal(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getRingerModeExternal() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getRingerModeInternal() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isValidRingerMode(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setVibrateSetting(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getVibrateSetting(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean shouldVibrate(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMode(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playSoundEffect(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void playSoundEffectVolume(int i, float f) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean loadSoundEffects() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void unloadSoundEffects() throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void reloadAudioSettings() throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public java.util.Map getSurroundFormats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public java.util.List getReportedSurroundFormats() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean setSurroundFormatEnabled(int i, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSurroundFormatEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean setEncodedSurroundMode(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int getEncodedSurroundMode(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setSpeakerphoneOn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isSpeakerphoneOn() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothScoOn(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setA2dpSuspended(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setLeAudioSuspended(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothScoOn() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothA2dpOn(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothA2dpOn() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int requestAudioFocus(android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i3) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int abandonAudioFocus(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void unregisterAudioFocusClient(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getCurrentAudioFocus() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void startBluetoothSco(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void startBluetoothScoVirtualCall(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void stopBluetoothSco(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceVolumeControlStream(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingtonePlayer(android.media.IRingtonePlayer iRingtonePlayer) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public android.media.IRingtonePlayer getRingtonePlayer() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getUiSoundsStreamType() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public java.util.List getIndependentStreamTypes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getStreamTypeAlias(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isVolumeControlUsingVolumeGroups() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerStreamAliasingDispatcher(android.media.IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setNotifAliasRingForTest(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setWiredDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public android.media.AudioRoutesInfo startWatchingRoutes(android.media.IAudioRoutesObserver iAudioRoutesObserver) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean isCameraSoundForced() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setVolumeController(android.media.IVolumeController iVolumeController) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public android.media.IVolumeController getVolumeController() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void notifyVolumeControllerVisible(android.media.IVolumeController iVolumeController, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isStreamAffectedByRingerMode(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isStreamAffectedByMute(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void disableSafeMediaVolume(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void lowerVolumeToRs1(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public float getOutputRs2UpperBound() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.media.IAudioService
        public void setOutputRs2UpperBound(float f) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public float getCsd() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.media.IAudioService
        public void setCsd(float f) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceUseFrameworkMel(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceComputeCsdOnAllDevices(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isCsdEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isCsdAsAFeatureAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isCsdAsAFeatureEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setCsdAsAFeatureEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setBluetoothAudioDeviceCategory_legacy(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getBluetoothAudioDeviceCategory_legacy(java.lang.String str, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean setBluetoothAudioDeviceCategory(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int getBluetoothAudioDeviceCategory(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothAudioDeviceCategoryFixed(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setHdmiSystemAudioSupported(boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isHdmiSystemAudioSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public java.lang.String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void unregisterAudioPolicyAsync(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void unregisterAudioPolicy(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int updateMixingRulesForPolicy(android.media.audiopolicy.AudioMix[] audioMixArr, android.media.audiopolicy.AudioMixingRule[] audioMixingRuleArr, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setFocusPropertiesForPolicy(int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setVolumePolicy(android.media.VolumePolicy volumePolicy) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean hasRegisteredDynamicPolicy() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioRecordingConfiguration> getActiveRecordingConfigurations() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getFocusRampTimeMs(int i, android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int dispatchFocusChange(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int dispatchFocusChangeWithFade(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, java.util.List<android.media.AudioFocusInfo> list, android.media.FadeManagerConfiguration fadeManagerConfiguration) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playerHasOpPlayAudio(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void handleBluetoothActiveDeviceChanged(android.bluetooth.BluetoothDevice bluetoothDevice, android.bluetooth.BluetoothDevice bluetoothDevice2, android.media.BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setFocusRequestResultFromExtPolicy(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerAudioServerStateDispatcher(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioServerStateDispatcher(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isAudioServerRunning() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setUidDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeUidDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setUserIdDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeUserIdDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean hasHapticChannels(android.net.Uri uri) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isCallScreeningModeSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setPreferredDevicesForStrategy(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removePreferredDevicesForStrategy(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForStrategy(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int setDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioDeviceAttributes> getDevicesForAttributes(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioDeviceAttributes> getDevicesForAttributesUnprotected(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void addOnDevicesForAttributesChangedListener(android.media.AudioAttributes audioAttributes, android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeOnDevicesForAttributesChangedListener(android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int setAllowedCapturePolicy(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getAllowedCapturePolicy() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void registerStrategyPreferredDevicesDispatcher(android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterStrategyPreferredDevicesDispatcher(android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerStrategyNonDefaultDevicesDispatcher(android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterStrategyNonDefaultDevicesDispatcher(android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRttEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setMultiAudioFocusEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int setPreferredDevicesForCapturePreset(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int clearPreferredDevicesForCapturePreset(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerCapturePresetDevicesRoleDispatcher(android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterCapturePresetDevicesRoleDispatcher(android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustVolume(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustSuggestedStreamVolume(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isMusicActive(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int getDeviceMaskForStream(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int[] getAvailableCommunicationDeviceIds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean setCommunicationDevice(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int getCommunicationDevice() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void registerCommunicationDeviceDispatcher(android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterCommunicationDeviceDispatcher(android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean areNavigationRepeatSoundEffectsEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setNavigationRepeatSoundEffectsEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isHomeSoundEffectEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setHomeSoundEffectEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean setAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public long getAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public long getMaxAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public int requestAudioFocusForTest(android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, java.lang.String str2, int i2, int i3, int i4) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int abandonAudioFocusForTest(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public long getFadeOutDurationOnFocusLossMillis(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public java.util.List getFocusDuckedUidsForTest() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public long getFocusFadeOutDurationForTest() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public long getFocusUnmuteDelayAfterFadeOutForTest() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.media.IAudioService
        public boolean enterAudioFocusFreezeForTest(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean exitAudioFocusFreezeForTest(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerModeDispatcher(android.media.IAudioModeDispatcher iAudioModeDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterModeDispatcher(android.media.IAudioModeDispatcher iAudioModeDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getSpatializerImmersiveAudioLevel() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isSpatializerAvailableForDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean hasHeadTracker(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setHeadTrackerEnabled(boolean z, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isHeadTrackerEnabled(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isHeadTrackerAvailable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerSpatializerHeadTrackerAvailableCallback(android.media.ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpatializerEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean canBeSpatialized(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerSpatializerCallback(android.media.ISpatializerCallback iSpatializerCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerCallback(android.media.ISpatializerCallback iSpatializerCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerSpatializerHeadTrackingCallback(android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerHeadTrackingCallback(android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerHeadToSoundstagePoseCallback(android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterHeadToSoundstagePoseCallback(android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void addSpatializerCompatibleAudioDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeSpatializerCompatibleAudioDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setDesiredHeadTrackingMode(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getDesiredHeadTrackingMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int[] getSupportedHeadTrackingModes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getActualHeadTrackingMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setSpatializerGlobalTransform(float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void recenterHeadTracker() throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpatializerParameter(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void getSpatializerParameter(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int getSpatializerOutput() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void registerSpatializerOutputCallback(android.media.ISpatializerOutputCallback iSpatializerOutputCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterSpatializerOutputCallback(android.media.ISpatializerOutputCallback iSpatializerOutputCallback) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isVolumeFixed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public android.media.VolumeInfo getDefaultVolumeInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean isPstnCallAudioInterceptable() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void muteAwaitConnection(int[] iArr, android.media.AudioDeviceAttributes audioDeviceAttributes, long j) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void cancelMuteAwaitConnection(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public android.media.AudioDeviceAttributes getMutingExpectedDevice() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerMuteAwaitConnectionDispatcher(android.media.IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setTestDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerDeviceVolumeBehaviorDispatcher(boolean z, android.media.IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public java.util.List<android.media.AudioFocusInfo> getFocusStack() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean sendFocusLoss(android.media.AudioFocusInfo audioFocusInfo, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void addAssistantServicesUids(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeAssistantServicesUids(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void setActiveAssistantServiceUids(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public int[] getAssistantServicesUids() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int[] getActiveAssistantServiceUids() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, android.media.IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, java.lang.String str, android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, boolean z2, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public android.media.AudioHalVersionInfo getHalVersion() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int setPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i, android.media.AudioMixerAttributes audioMixerAttributes) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int clearPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void registerPreferredMixerAttributesDispatcher(android.media.IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterPreferredMixerAttributesDispatcher(android.media.IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean supportsBluetoothVariableLatency() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothVariableLatencyEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothVariableLatencyEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void startLoudnessCodecUpdates(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void stopLoudnessCodecUpdates(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void addLoudnessCodecInfo(int i, int i2, android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public void removeLoudnessCodecInfo(int i, android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioService
        public android.os.PersistableBundle getLoudnessParams(android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int setFadeManagerConfigurationForFocusLoss(android.media.FadeManagerConfiguration fadeManagerConfiguration) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int clearFadeManagerConfigurationForFocusLoss() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public android.media.FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean shouldNotificationSoundPlay(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IAudioService {
        public static final java.lang.String DESCRIPTOR = "android.media.IAudioService";
        static final int TRANSACTION_abandonAudioFocus = 71;
        static final int TRANSACTION_abandonAudioFocusForTest = 189;
        static final int TRANSACTION_addAssistantServicesUids = 240;
        static final int TRANSACTION_addLoudnessCodecInfo = 258;
        static final int TRANSACTION_addMixForPolicy = 117;
        static final int TRANSACTION_addOnDevicesForAttributesChangedListener = 152;
        static final int TRANSACTION_addSpatializerCompatibleAudioDevice = 216;
        static final int TRANSACTION_adjustStreamVolume = 10;
        static final int TRANSACTION_adjustStreamVolumeForUid = 169;
        static final int TRANSACTION_adjustStreamVolumeWithAttribution = 11;
        static final int TRANSACTION_adjustSuggestedStreamVolume = 173;
        static final int TRANSACTION_adjustSuggestedStreamVolumeForUid = 170;
        static final int TRANSACTION_adjustVolume = 172;
        static final int TRANSACTION_adjustVolumeGroupVolume = 31;
        static final int TRANSACTION_areNavigationRepeatSoundEffectsEnabled = 181;
        static final int TRANSACTION_canBeSpatialized = 208;
        static final int TRANSACTION_cancelMuteAwaitConnection = 233;
        static final int TRANSACTION_clearFadeManagerConfigurationForFocusLoss = 262;
        static final int TRANSACTION_clearPreferredDevicesForCapturePreset = 165;
        static final int TRANSACTION_clearPreferredMixerAttributes = 248;
        static final int TRANSACTION_disableSafeMediaVolume = 94;
        static final int TRANSACTION_dispatchFocusChange = 130;
        static final int TRANSACTION_dispatchFocusChangeWithFade = 131;
        static final int TRANSACTION_enterAudioFocusFreezeForTest = 194;
        static final int TRANSACTION_exitAudioFocusFreezeForTest = 195;
        static final int TRANSACTION_forceComputeCsdOnAllDevices = 101;
        static final int TRANSACTION_forceRemoteSubmixFullVolume = 18;
        static final int TRANSACTION_forceUseFrameworkMel = 100;
        static final int TRANSACTION_forceVolumeControlStream = 77;
        static final int TRANSACTION_getActiveAssistantServiceUids = 244;
        static final int TRANSACTION_getActivePlaybackConfigurations = 128;
        static final int TRANSACTION_getActiveRecordingConfigurations = 125;
        static final int TRANSACTION_getActualHeadTrackingMode = 221;
        static final int TRANSACTION_getAdditionalOutputDeviceDelay = 186;
        static final int TRANSACTION_getAllowedCapturePolicy = 155;
        static final int TRANSACTION_getAssistantServicesUids = 243;
        static final int TRANSACTION_getAudioProductStrategies = 35;
        static final int TRANSACTION_getAudioVolumeGroups = 24;
        static final int TRANSACTION_getAvailableCommunicationDeviceIds = 176;
        static final int TRANSACTION_getBluetoothAudioDeviceCategory = 109;
        static final int TRANSACTION_getBluetoothAudioDeviceCategory_legacy = 107;
        static final int TRANSACTION_getCommunicationDevice = 178;
        static final int TRANSACTION_getCsd = 98;
        static final int TRANSACTION_getCurrentAudioFocus = 73;
        static final int TRANSACTION_getDefaultVolumeInfo = 230;
        static final int TRANSACTION_getDesiredHeadTrackingMode = 219;
        static final int TRANSACTION_getDeviceMaskForStream = 175;
        static final int TRANSACTION_getDeviceVolume = 15;
        static final int TRANSACTION_getDeviceVolumeBehavior = 162;
        static final int TRANSACTION_getDevicesForAttributes = 150;
        static final int TRANSACTION_getDevicesForAttributesUnprotected = 151;
        static final int TRANSACTION_getEncodedSurroundMode = 61;
        static final int TRANSACTION_getFadeManagerConfigurationForFocusLoss = 263;
        static final int TRANSACTION_getFadeOutDurationOnFocusLossMillis = 190;
        static final int TRANSACTION_getFocusDuckedUidsForTest = 191;
        static final int TRANSACTION_getFocusFadeOutDurationForTest = 192;
        static final int TRANSACTION_getFocusRampTimeMs = 129;
        static final int TRANSACTION_getFocusStack = 238;
        static final int TRANSACTION_getFocusUnmuteDelayAfterFadeOutForTest = 193;
        static final int TRANSACTION_getHalVersion = 246;
        static final int TRANSACTION_getIndependentStreamTypes = 81;
        static final int TRANSACTION_getLastAudibleStreamVolume = 32;
        static final int TRANSACTION_getLastAudibleVolumeForVolumeGroup = 29;
        static final int TRANSACTION_getLoudnessParams = 260;
        static final int TRANSACTION_getMaxAdditionalOutputDeviceDelay = 187;
        static final int TRANSACTION_getMode = 50;
        static final int TRANSACTION_getMutingExpectedDevice = 234;
        static final int TRANSACTION_getNonDefaultDevicesForStrategy = 149;
        static final int TRANSACTION_getOutputRs2UpperBound = 96;
        static final int TRANSACTION_getPreferredDevicesForCapturePreset = 166;
        static final int TRANSACTION_getPreferredDevicesForStrategy = 146;
        static final int TRANSACTION_getRegisteredPolicyMixes = 115;
        static final int TRANSACTION_getReportedSurroundFormats = 57;
        static final int TRANSACTION_getRingerModeExternal = 43;
        static final int TRANSACTION_getRingerModeInternal = 44;
        static final int TRANSACTION_getRingtonePlayer = 79;
        static final int TRANSACTION_getSpatializerCompatibleAudioDevices = 215;
        static final int TRANSACTION_getSpatializerImmersiveAudioLevel = 198;
        static final int TRANSACTION_getSpatializerOutput = 226;
        static final int TRANSACTION_getSpatializerParameter = 225;
        static final int TRANSACTION_getStreamMaxVolume = 23;
        static final int TRANSACTION_getStreamMinVolume = 22;
        static final int TRANSACTION_getStreamTypeAlias = 82;
        static final int TRANSACTION_getStreamVolume = 21;
        static final int TRANSACTION_getSupportedHeadTrackingModes = 220;
        static final int TRANSACTION_getSupportedSystemUsages = 34;
        static final int TRANSACTION_getSurroundFormats = 56;
        static final int TRANSACTION_getUiSoundsStreamType = 80;
        static final int TRANSACTION_getVibrateSetting = 47;
        static final int TRANSACTION_getVolumeController = 90;
        static final int TRANSACTION_getVolumeGroupMaxVolumeIndex = 27;
        static final int TRANSACTION_getVolumeGroupMinVolumeIndex = 28;
        static final int TRANSACTION_getVolumeGroupVolumeIndex = 26;
        static final int TRANSACTION_handleBluetoothActiveDeviceChanged = 133;
        static final int TRANSACTION_handleVolumeKey = 16;
        static final int TRANSACTION_hasHapticChannels = 142;
        static final int TRANSACTION_hasHeadTracker = 202;
        static final int TRANSACTION_hasRegisteredDynamicPolicy = 122;
        static final int TRANSACTION_isAudioServerRunning = 137;
        static final int TRANSACTION_isBluetoothA2dpOn = 69;
        static final int TRANSACTION_isBluetoothAudioDeviceCategoryFixed = 110;
        static final int TRANSACTION_isBluetoothScoOn = 67;
        static final int TRANSACTION_isBluetoothVariableLatencyEnabled = 253;
        static final int TRANSACTION_isCallScreeningModeSupported = 143;
        static final int TRANSACTION_isCameraSoundForced = 88;
        static final int TRANSACTION_isCsdAsAFeatureAvailable = 103;
        static final int TRANSACTION_isCsdAsAFeatureEnabled = 104;
        static final int TRANSACTION_isCsdEnabled = 102;
        static final int TRANSACTION_isHdmiSystemAudioSupported = 112;
        static final int TRANSACTION_isHeadTrackerAvailable = 205;
        static final int TRANSACTION_isHeadTrackerEnabled = 204;
        static final int TRANSACTION_isHomeSoundEffectEnabled = 183;
        static final int TRANSACTION_isHotwordStreamSupported = 38;
        static final int TRANSACTION_isMasterMute = 19;
        static final int TRANSACTION_isMicrophoneMuted = 36;
        static final int TRANSACTION_isMusicActive = 174;
        static final int TRANSACTION_isPstnCallAudioInterceptable = 231;
        static final int TRANSACTION_isSpatializerAvailable = 200;
        static final int TRANSACTION_isSpatializerAvailableForDevice = 201;
        static final int TRANSACTION_isSpatializerEnabled = 199;
        static final int TRANSACTION_isSpeakerphoneOn = 63;
        static final int TRANSACTION_isStreamAffectedByMute = 93;
        static final int TRANSACTION_isStreamAffectedByRingerMode = 92;
        static final int TRANSACTION_isStreamMute = 17;
        static final int TRANSACTION_isSurroundFormatEnabled = 59;
        static final int TRANSACTION_isUltrasoundSupported = 37;
        static final int TRANSACTION_isValidRingerMode = 45;
        static final int TRANSACTION_isVolumeControlUsingVolumeGroups = 83;
        static final int TRANSACTION_isVolumeFixed = 229;
        static final int TRANSACTION_isVolumeGroupMuted = 30;
        static final int TRANSACTION_loadSoundEffects = 53;
        static final int TRANSACTION_lowerVolumeToRs1 = 95;
        static final int TRANSACTION_muteAwaitConnection = 232;
        static final int TRANSACTION_notifyVolumeControllerVisible = 91;
        static final int TRANSACTION_playSoundEffect = 51;
        static final int TRANSACTION_playSoundEffectVolume = 52;
        static final int TRANSACTION_playerAttributes = 2;
        static final int TRANSACTION_playerEvent = 3;
        static final int TRANSACTION_playerHasOpPlayAudio = 132;
        static final int TRANSACTION_playerSessionId = 8;
        static final int TRANSACTION_portEvent = 9;
        static final int TRANSACTION_recenterHeadTracker = 223;
        static final int TRANSACTION_recorderEvent = 6;
        static final int TRANSACTION_registerAudioPolicy = 113;
        static final int TRANSACTION_registerAudioServerStateDispatcher = 135;
        static final int TRANSACTION_registerCapturePresetDevicesRoleDispatcher = 167;
        static final int TRANSACTION_registerCommunicationDeviceDispatcher = 179;
        static final int TRANSACTION_registerDeviceVolumeBehaviorDispatcher = 237;
        static final int TRANSACTION_registerDeviceVolumeDispatcherForAbsoluteVolume = 245;
        static final int TRANSACTION_registerHeadToSoundstagePoseCallback = 213;
        static final int TRANSACTION_registerLoudnessCodecUpdatesDispatcher = 254;
        static final int TRANSACTION_registerModeDispatcher = 196;
        static final int TRANSACTION_registerMuteAwaitConnectionDispatcher = 235;
        static final int TRANSACTION_registerPlaybackCallback = 126;
        static final int TRANSACTION_registerPreferredMixerAttributesDispatcher = 249;
        static final int TRANSACTION_registerRecordingCallback = 123;
        static final int TRANSACTION_registerSpatializerCallback = 209;
        static final int TRANSACTION_registerSpatializerHeadTrackerAvailableCallback = 206;
        static final int TRANSACTION_registerSpatializerHeadTrackingCallback = 211;
        static final int TRANSACTION_registerSpatializerOutputCallback = 227;
        static final int TRANSACTION_registerStrategyNonDefaultDevicesDispatcher = 158;
        static final int TRANSACTION_registerStrategyPreferredDevicesDispatcher = 156;
        static final int TRANSACTION_registerStreamAliasingDispatcher = 84;
        static final int TRANSACTION_releasePlayer = 4;
        static final int TRANSACTION_releaseRecorder = 7;
        static final int TRANSACTION_reloadAudioSettings = 55;
        static final int TRANSACTION_removeAssistantServicesUids = 241;
        static final int TRANSACTION_removeDeviceAsNonDefaultForStrategy = 148;
        static final int TRANSACTION_removeLoudnessCodecInfo = 259;
        static final int TRANSACTION_removeMixForPolicy = 118;
        static final int TRANSACTION_removeOnDevicesForAttributesChangedListener = 153;
        static final int TRANSACTION_removePreferredDevicesForStrategy = 145;
        static final int TRANSACTION_removeSpatializerCompatibleAudioDevice = 217;
        static final int TRANSACTION_removeUidDeviceAffinity = 139;
        static final int TRANSACTION_removeUserIdDeviceAffinity = 141;
        static final int TRANSACTION_requestAudioFocus = 70;
        static final int TRANSACTION_requestAudioFocusForTest = 188;
        static final int TRANSACTION_sendFocusLoss = 239;
        static final int TRANSACTION_setA2dpSuspended = 65;
        static final int TRANSACTION_setActiveAssistantServiceUids = 242;
        static final int TRANSACTION_setAdditionalOutputDeviceDelay = 185;
        static final int TRANSACTION_setAllowedCapturePolicy = 154;
        static final int TRANSACTION_setBluetoothA2dpOn = 68;
        static final int TRANSACTION_setBluetoothAudioDeviceCategory = 108;
        static final int TRANSACTION_setBluetoothAudioDeviceCategory_legacy = 106;
        static final int TRANSACTION_setBluetoothScoOn = 64;
        static final int TRANSACTION_setBluetoothVariableLatencyEnabled = 252;
        static final int TRANSACTION_setCommunicationDevice = 177;
        static final int TRANSACTION_setCsd = 99;
        static final int TRANSACTION_setCsdAsAFeatureEnabled = 105;
        static final int TRANSACTION_setDesiredHeadTrackingMode = 218;
        static final int TRANSACTION_setDeviceAsNonDefaultForStrategy = 147;
        static final int TRANSACTION_setDeviceVolume = 14;
        static final int TRANSACTION_setDeviceVolumeBehavior = 161;
        static final int TRANSACTION_setEncodedSurroundMode = 60;
        static final int TRANSACTION_setFadeManagerConfigurationForFocusLoss = 261;
        static final int TRANSACTION_setFocusPropertiesForPolicy = 120;
        static final int TRANSACTION_setFocusRequestResultFromExtPolicy = 134;
        static final int TRANSACTION_setHdmiSystemAudioSupported = 111;
        static final int TRANSACTION_setHeadTrackerEnabled = 203;
        static final int TRANSACTION_setHomeSoundEffectEnabled = 184;
        static final int TRANSACTION_setLeAudioSuspended = 66;
        static final int TRANSACTION_setMasterMute = 20;
        static final int TRANSACTION_setMicrophoneMute = 39;
        static final int TRANSACTION_setMicrophoneMuteFromSwitch = 40;
        static final int TRANSACTION_setMode = 49;
        static final int TRANSACTION_setMultiAudioFocusEnabled = 163;
        static final int TRANSACTION_setNavigationRepeatSoundEffectsEnabled = 182;
        static final int TRANSACTION_setNotifAliasRingForTest = 85;
        static final int TRANSACTION_setOutputRs2UpperBound = 97;
        static final int TRANSACTION_setPreferredDevicesForCapturePreset = 164;
        static final int TRANSACTION_setPreferredDevicesForStrategy = 144;
        static final int TRANSACTION_setPreferredMixerAttributes = 247;
        static final int TRANSACTION_setRingerModeExternal = 41;
        static final int TRANSACTION_setRingerModeInternal = 42;
        static final int TRANSACTION_setRingtonePlayer = 78;
        static final int TRANSACTION_setRttEnabled = 160;
        static final int TRANSACTION_setSpatializerEnabled = 207;
        static final int TRANSACTION_setSpatializerGlobalTransform = 222;
        static final int TRANSACTION_setSpatializerParameter = 224;
        static final int TRANSACTION_setSpeakerphoneOn = 62;
        static final int TRANSACTION_setStreamVolume = 12;
        static final int TRANSACTION_setStreamVolumeForUid = 171;
        static final int TRANSACTION_setStreamVolumeWithAttribution = 13;
        static final int TRANSACTION_setSupportedSystemUsages = 33;
        static final int TRANSACTION_setSurroundFormatEnabled = 58;
        static final int TRANSACTION_setTestDeviceConnectionState = 236;
        static final int TRANSACTION_setUidDeviceAffinity = 138;
        static final int TRANSACTION_setUserIdDeviceAffinity = 140;
        static final int TRANSACTION_setVibrateSetting = 46;
        static final int TRANSACTION_setVolumeController = 89;
        static final int TRANSACTION_setVolumeGroupVolumeIndex = 25;
        static final int TRANSACTION_setVolumePolicy = 121;
        static final int TRANSACTION_setWiredDeviceConnectionState = 86;
        static final int TRANSACTION_shouldNotificationSoundPlay = 264;
        static final int TRANSACTION_shouldVibrate = 48;
        static final int TRANSACTION_startBluetoothSco = 74;
        static final int TRANSACTION_startBluetoothScoVirtualCall = 75;
        static final int TRANSACTION_startLoudnessCodecUpdates = 256;
        static final int TRANSACTION_startWatchingRoutes = 87;
        static final int TRANSACTION_stopBluetoothSco = 76;
        static final int TRANSACTION_stopLoudnessCodecUpdates = 257;
        static final int TRANSACTION_supportsBluetoothVariableLatency = 251;
        static final int TRANSACTION_trackPlayer = 1;
        static final int TRANSACTION_trackRecorder = 5;
        static final int TRANSACTION_unloadSoundEffects = 54;
        static final int TRANSACTION_unregisterAudioFocusClient = 72;
        static final int TRANSACTION_unregisterAudioPolicy = 116;
        static final int TRANSACTION_unregisterAudioPolicyAsync = 114;
        static final int TRANSACTION_unregisterAudioServerStateDispatcher = 136;
        static final int TRANSACTION_unregisterCapturePresetDevicesRoleDispatcher = 168;
        static final int TRANSACTION_unregisterCommunicationDeviceDispatcher = 180;
        static final int TRANSACTION_unregisterHeadToSoundstagePoseCallback = 214;
        static final int TRANSACTION_unregisterLoudnessCodecUpdatesDispatcher = 255;
        static final int TRANSACTION_unregisterModeDispatcher = 197;
        static final int TRANSACTION_unregisterPlaybackCallback = 127;
        static final int TRANSACTION_unregisterPreferredMixerAttributesDispatcher = 250;
        static final int TRANSACTION_unregisterRecordingCallback = 124;
        static final int TRANSACTION_unregisterSpatializerCallback = 210;
        static final int TRANSACTION_unregisterSpatializerHeadTrackingCallback = 212;
        static final int TRANSACTION_unregisterSpatializerOutputCallback = 228;
        static final int TRANSACTION_unregisterStrategyNonDefaultDevicesDispatcher = 159;
        static final int TRANSACTION_unregisterStrategyPreferredDevicesDispatcher = 157;
        static final int TRANSACTION_updateMixingRulesForPolicy = 119;
        private final android.os.PermissionEnforcer mEnforcer;
        static final java.lang.String[] PERMISSIONS_setDeviceVolume = {android.Manifest.permission.MODIFY_AUDIO_ROUTING, android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final java.lang.String[] PERMISSIONS_getDeviceVolume = {android.Manifest.permission.MODIFY_AUDIO_ROUTING, android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final java.lang.String[] PERMISSIONS_setVolumeGroupVolumeIndex = {android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, android.Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final java.lang.String[] PERMISSIONS_getVolumeGroupVolumeIndex = {android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, android.Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final java.lang.String[] PERMISSIONS_getVolumeGroupMaxVolumeIndex = {android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, android.Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final java.lang.String[] PERMISSIONS_getVolumeGroupMinVolumeIndex = {android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, android.Manifest.permission.MODIFY_AUDIO_ROUTING};
        static final java.lang.String[] PERMISSIONS_setDeviceVolumeBehavior = {android.Manifest.permission.MODIFY_AUDIO_ROUTING, android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};
        static final java.lang.String[] PERMISSIONS_getDeviceVolumeBehavior = {android.Manifest.permission.MODIFY_AUDIO_ROUTING, android.Manifest.permission.QUERY_AUDIO_STATE, android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED};

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.media.IAudioService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IAudioService)) {
                return (android.media.IAudioService) queryLocalInterface;
            }
            return new android.media.IAudioService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "trackPlayer";
                case 2:
                    return "playerAttributes";
                case 3:
                    return "playerEvent";
                case 4:
                    return "releasePlayer";
                case 5:
                    return "trackRecorder";
                case 6:
                    return "recorderEvent";
                case 7:
                    return "releaseRecorder";
                case 8:
                    return "playerSessionId";
                case 9:
                    return "portEvent";
                case 10:
                    return "adjustStreamVolume";
                case 11:
                    return "adjustStreamVolumeWithAttribution";
                case 12:
                    return "setStreamVolume";
                case 13:
                    return "setStreamVolumeWithAttribution";
                case 14:
                    return "setDeviceVolume";
                case 15:
                    return "getDeviceVolume";
                case 16:
                    return "handleVolumeKey";
                case 17:
                    return "isStreamMute";
                case 18:
                    return "forceRemoteSubmixFullVolume";
                case 19:
                    return "isMasterMute";
                case 20:
                    return "setMasterMute";
                case 21:
                    return "getStreamVolume";
                case 22:
                    return "getStreamMinVolume";
                case 23:
                    return "getStreamMaxVolume";
                case 24:
                    return "getAudioVolumeGroups";
                case 25:
                    return "setVolumeGroupVolumeIndex";
                case 26:
                    return "getVolumeGroupVolumeIndex";
                case 27:
                    return "getVolumeGroupMaxVolumeIndex";
                case 28:
                    return "getVolumeGroupMinVolumeIndex";
                case 29:
                    return "getLastAudibleVolumeForVolumeGroup";
                case 30:
                    return "isVolumeGroupMuted";
                case 31:
                    return "adjustVolumeGroupVolume";
                case 32:
                    return "getLastAudibleStreamVolume";
                case 33:
                    return "setSupportedSystemUsages";
                case 34:
                    return "getSupportedSystemUsages";
                case 35:
                    return "getAudioProductStrategies";
                case 36:
                    return "isMicrophoneMuted";
                case 37:
                    return "isUltrasoundSupported";
                case 38:
                    return "isHotwordStreamSupported";
                case 39:
                    return "setMicrophoneMute";
                case 40:
                    return "setMicrophoneMuteFromSwitch";
                case 41:
                    return "setRingerModeExternal";
                case 42:
                    return "setRingerModeInternal";
                case 43:
                    return "getRingerModeExternal";
                case 44:
                    return "getRingerModeInternal";
                case 45:
                    return "isValidRingerMode";
                case 46:
                    return "setVibrateSetting";
                case 47:
                    return "getVibrateSetting";
                case 48:
                    return "shouldVibrate";
                case 49:
                    return "setMode";
                case 50:
                    return "getMode";
                case 51:
                    return "playSoundEffect";
                case 52:
                    return "playSoundEffectVolume";
                case 53:
                    return "loadSoundEffects";
                case 54:
                    return "unloadSoundEffects";
                case 55:
                    return "reloadAudioSettings";
                case 56:
                    return "getSurroundFormats";
                case 57:
                    return "getReportedSurroundFormats";
                case 58:
                    return "setSurroundFormatEnabled";
                case 59:
                    return "isSurroundFormatEnabled";
                case 60:
                    return "setEncodedSurroundMode";
                case 61:
                    return "getEncodedSurroundMode";
                case 62:
                    return "setSpeakerphoneOn";
                case 63:
                    return "isSpeakerphoneOn";
                case 64:
                    return "setBluetoothScoOn";
                case 65:
                    return "setA2dpSuspended";
                case 66:
                    return "setLeAudioSuspended";
                case 67:
                    return "isBluetoothScoOn";
                case 68:
                    return "setBluetoothA2dpOn";
                case 69:
                    return "isBluetoothA2dpOn";
                case 70:
                    return "requestAudioFocus";
                case 71:
                    return "abandonAudioFocus";
                case 72:
                    return "unregisterAudioFocusClient";
                case 73:
                    return "getCurrentAudioFocus";
                case 74:
                    return "startBluetoothSco";
                case 75:
                    return "startBluetoothScoVirtualCall";
                case 76:
                    return "stopBluetoothSco";
                case 77:
                    return "forceVolumeControlStream";
                case 78:
                    return "setRingtonePlayer";
                case 79:
                    return "getRingtonePlayer";
                case 80:
                    return "getUiSoundsStreamType";
                case 81:
                    return "getIndependentStreamTypes";
                case 82:
                    return "getStreamTypeAlias";
                case 83:
                    return "isVolumeControlUsingVolumeGroups";
                case 84:
                    return "registerStreamAliasingDispatcher";
                case 85:
                    return "setNotifAliasRingForTest";
                case 86:
                    return "setWiredDeviceConnectionState";
                case 87:
                    return "startWatchingRoutes";
                case 88:
                    return "isCameraSoundForced";
                case 89:
                    return "setVolumeController";
                case 90:
                    return "getVolumeController";
                case 91:
                    return "notifyVolumeControllerVisible";
                case 92:
                    return "isStreamAffectedByRingerMode";
                case 93:
                    return "isStreamAffectedByMute";
                case 94:
                    return "disableSafeMediaVolume";
                case 95:
                    return "lowerVolumeToRs1";
                case 96:
                    return "getOutputRs2UpperBound";
                case 97:
                    return "setOutputRs2UpperBound";
                case 98:
                    return "getCsd";
                case 99:
                    return "setCsd";
                case 100:
                    return "forceUseFrameworkMel";
                case 101:
                    return "forceComputeCsdOnAllDevices";
                case 102:
                    return "isCsdEnabled";
                case 103:
                    return "isCsdAsAFeatureAvailable";
                case 104:
                    return "isCsdAsAFeatureEnabled";
                case 105:
                    return "setCsdAsAFeatureEnabled";
                case 106:
                    return "setBluetoothAudioDeviceCategory_legacy";
                case 107:
                    return "getBluetoothAudioDeviceCategory_legacy";
                case 108:
                    return "setBluetoothAudioDeviceCategory";
                case 109:
                    return "getBluetoothAudioDeviceCategory";
                case 110:
                    return "isBluetoothAudioDeviceCategoryFixed";
                case 111:
                    return "setHdmiSystemAudioSupported";
                case 112:
                    return "isHdmiSystemAudioSupported";
                case 113:
                    return "registerAudioPolicy";
                case 114:
                    return "unregisterAudioPolicyAsync";
                case 115:
                    return "getRegisteredPolicyMixes";
                case 116:
                    return "unregisterAudioPolicy";
                case 117:
                    return "addMixForPolicy";
                case 118:
                    return "removeMixForPolicy";
                case 119:
                    return "updateMixingRulesForPolicy";
                case 120:
                    return "setFocusPropertiesForPolicy";
                case 121:
                    return "setVolumePolicy";
                case 122:
                    return "hasRegisteredDynamicPolicy";
                case 123:
                    return "registerRecordingCallback";
                case 124:
                    return "unregisterRecordingCallback";
                case 125:
                    return "getActiveRecordingConfigurations";
                case 126:
                    return "registerPlaybackCallback";
                case 127:
                    return "unregisterPlaybackCallback";
                case 128:
                    return "getActivePlaybackConfigurations";
                case 129:
                    return "getFocusRampTimeMs";
                case 130:
                    return "dispatchFocusChange";
                case 131:
                    return "dispatchFocusChangeWithFade";
                case 132:
                    return "playerHasOpPlayAudio";
                case 133:
                    return "handleBluetoothActiveDeviceChanged";
                case 134:
                    return "setFocusRequestResultFromExtPolicy";
                case 135:
                    return "registerAudioServerStateDispatcher";
                case 136:
                    return "unregisterAudioServerStateDispatcher";
                case 137:
                    return "isAudioServerRunning";
                case 138:
                    return "setUidDeviceAffinity";
                case 139:
                    return "removeUidDeviceAffinity";
                case 140:
                    return "setUserIdDeviceAffinity";
                case 141:
                    return "removeUserIdDeviceAffinity";
                case 142:
                    return "hasHapticChannels";
                case 143:
                    return "isCallScreeningModeSupported";
                case 144:
                    return "setPreferredDevicesForStrategy";
                case 145:
                    return "removePreferredDevicesForStrategy";
                case 146:
                    return "getPreferredDevicesForStrategy";
                case 147:
                    return "setDeviceAsNonDefaultForStrategy";
                case 148:
                    return "removeDeviceAsNonDefaultForStrategy";
                case 149:
                    return "getNonDefaultDevicesForStrategy";
                case 150:
                    return "getDevicesForAttributes";
                case 151:
                    return "getDevicesForAttributesUnprotected";
                case 152:
                    return "addOnDevicesForAttributesChangedListener";
                case 153:
                    return "removeOnDevicesForAttributesChangedListener";
                case 154:
                    return "setAllowedCapturePolicy";
                case 155:
                    return "getAllowedCapturePolicy";
                case 156:
                    return "registerStrategyPreferredDevicesDispatcher";
                case 157:
                    return "unregisterStrategyPreferredDevicesDispatcher";
                case 158:
                    return "registerStrategyNonDefaultDevicesDispatcher";
                case 159:
                    return "unregisterStrategyNonDefaultDevicesDispatcher";
                case 160:
                    return "setRttEnabled";
                case 161:
                    return "setDeviceVolumeBehavior";
                case 162:
                    return "getDeviceVolumeBehavior";
                case 163:
                    return "setMultiAudioFocusEnabled";
                case 164:
                    return "setPreferredDevicesForCapturePreset";
                case 165:
                    return "clearPreferredDevicesForCapturePreset";
                case 166:
                    return "getPreferredDevicesForCapturePreset";
                case 167:
                    return "registerCapturePresetDevicesRoleDispatcher";
                case 168:
                    return "unregisterCapturePresetDevicesRoleDispatcher";
                case 169:
                    return "adjustStreamVolumeForUid";
                case 170:
                    return "adjustSuggestedStreamVolumeForUid";
                case 171:
                    return "setStreamVolumeForUid";
                case 172:
                    return "adjustVolume";
                case 173:
                    return "adjustSuggestedStreamVolume";
                case 174:
                    return "isMusicActive";
                case 175:
                    return "getDeviceMaskForStream";
                case 176:
                    return "getAvailableCommunicationDeviceIds";
                case 177:
                    return "setCommunicationDevice";
                case 178:
                    return "getCommunicationDevice";
                case 179:
                    return "registerCommunicationDeviceDispatcher";
                case 180:
                    return "unregisterCommunicationDeviceDispatcher";
                case 181:
                    return "areNavigationRepeatSoundEffectsEnabled";
                case 182:
                    return "setNavigationRepeatSoundEffectsEnabled";
                case 183:
                    return "isHomeSoundEffectEnabled";
                case 184:
                    return "setHomeSoundEffectEnabled";
                case 185:
                    return "setAdditionalOutputDeviceDelay";
                case 186:
                    return "getAdditionalOutputDeviceDelay";
                case 187:
                    return "getMaxAdditionalOutputDeviceDelay";
                case 188:
                    return "requestAudioFocusForTest";
                case 189:
                    return "abandonAudioFocusForTest";
                case 190:
                    return "getFadeOutDurationOnFocusLossMillis";
                case 191:
                    return "getFocusDuckedUidsForTest";
                case 192:
                    return "getFocusFadeOutDurationForTest";
                case 193:
                    return "getFocusUnmuteDelayAfterFadeOutForTest";
                case 194:
                    return "enterAudioFocusFreezeForTest";
                case 195:
                    return "exitAudioFocusFreezeForTest";
                case 196:
                    return "registerModeDispatcher";
                case 197:
                    return "unregisterModeDispatcher";
                case 198:
                    return "getSpatializerImmersiveAudioLevel";
                case 199:
                    return "isSpatializerEnabled";
                case 200:
                    return "isSpatializerAvailable";
                case 201:
                    return "isSpatializerAvailableForDevice";
                case 202:
                    return "hasHeadTracker";
                case 203:
                    return "setHeadTrackerEnabled";
                case 204:
                    return "isHeadTrackerEnabled";
                case 205:
                    return "isHeadTrackerAvailable";
                case 206:
                    return "registerSpatializerHeadTrackerAvailableCallback";
                case 207:
                    return "setSpatializerEnabled";
                case 208:
                    return "canBeSpatialized";
                case 209:
                    return "registerSpatializerCallback";
                case 210:
                    return "unregisterSpatializerCallback";
                case 211:
                    return "registerSpatializerHeadTrackingCallback";
                case 212:
                    return "unregisterSpatializerHeadTrackingCallback";
                case 213:
                    return "registerHeadToSoundstagePoseCallback";
                case 214:
                    return "unregisterHeadToSoundstagePoseCallback";
                case 215:
                    return "getSpatializerCompatibleAudioDevices";
                case 216:
                    return "addSpatializerCompatibleAudioDevice";
                case 217:
                    return "removeSpatializerCompatibleAudioDevice";
                case 218:
                    return "setDesiredHeadTrackingMode";
                case 219:
                    return "getDesiredHeadTrackingMode";
                case 220:
                    return "getSupportedHeadTrackingModes";
                case 221:
                    return "getActualHeadTrackingMode";
                case 222:
                    return "setSpatializerGlobalTransform";
                case 223:
                    return "recenterHeadTracker";
                case 224:
                    return "setSpatializerParameter";
                case 225:
                    return "getSpatializerParameter";
                case 226:
                    return "getSpatializerOutput";
                case 227:
                    return "registerSpatializerOutputCallback";
                case 228:
                    return "unregisterSpatializerOutputCallback";
                case 229:
                    return "isVolumeFixed";
                case 230:
                    return "getDefaultVolumeInfo";
                case 231:
                    return "isPstnCallAudioInterceptable";
                case 232:
                    return "muteAwaitConnection";
                case 233:
                    return "cancelMuteAwaitConnection";
                case 234:
                    return "getMutingExpectedDevice";
                case 235:
                    return "registerMuteAwaitConnectionDispatcher";
                case 236:
                    return "setTestDeviceConnectionState";
                case 237:
                    return "registerDeviceVolumeBehaviorDispatcher";
                case 238:
                    return "getFocusStack";
                case 239:
                    return "sendFocusLoss";
                case 240:
                    return "addAssistantServicesUids";
                case 241:
                    return "removeAssistantServicesUids";
                case 242:
                    return "setActiveAssistantServiceUids";
                case 243:
                    return "getAssistantServicesUids";
                case 244:
                    return "getActiveAssistantServiceUids";
                case 245:
                    return "registerDeviceVolumeDispatcherForAbsoluteVolume";
                case 246:
                    return "getHalVersion";
                case 247:
                    return "setPreferredMixerAttributes";
                case 248:
                    return "clearPreferredMixerAttributes";
                case 249:
                    return "registerPreferredMixerAttributesDispatcher";
                case 250:
                    return "unregisterPreferredMixerAttributesDispatcher";
                case 251:
                    return "supportsBluetoothVariableLatency";
                case 252:
                    return "setBluetoothVariableLatencyEnabled";
                case 253:
                    return "isBluetoothVariableLatencyEnabled";
                case 254:
                    return "registerLoudnessCodecUpdatesDispatcher";
                case 255:
                    return "unregisterLoudnessCodecUpdatesDispatcher";
                case 256:
                    return "startLoudnessCodecUpdates";
                case 257:
                    return "stopLoudnessCodecUpdates";
                case 258:
                    return "addLoudnessCodecInfo";
                case 259:
                    return "removeLoudnessCodecInfo";
                case 260:
                    return "getLoudnessParams";
                case 261:
                    return "setFadeManagerConfigurationForFocusLoss";
                case 262:
                    return "clearFadeManagerConfigurationForFocusLoss";
                case 263:
                    return "getFadeManagerConfigurationForFocusLoss";
                case 264:
                    return "shouldNotificationSoundPlay";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.PlayerBase.PlayerIdCard playerIdCard = (android.media.PlayerBase.PlayerIdCard) parcel.readTypedObject(android.media.PlayerBase.PlayerIdCard.CREATOR);
                    parcel.enforceNoDataAvail();
                    int trackPlayer = trackPlayer(playerIdCard);
                    parcel2.writeNoException();
                    parcel2.writeInt(trackPlayer);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    playerAttributes(readInt, audioAttributes);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playerEvent(readInt2, readInt3, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releasePlayer(readInt5);
                    return true;
                case 5:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int trackRecorder = trackRecorder(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeInt(trackRecorder);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    recorderEvent(readInt6, readInt7);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseRecorder(readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playerSessionId(readInt9, readInt10);
                    return true;
                case 9:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    portEvent(readInt11, readInt12, persistableBundle);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    adjustStreamVolume(readInt13, readInt14, readInt15, readString);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    adjustStreamVolumeWithAttribution(readInt16, readInt17, readInt18, readString2, readString3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setStreamVolume(readInt19, readInt20, readInt21, readString4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setStreamVolumeWithAttribution(readInt22, readInt23, readInt24, readString5, readString6);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.media.VolumeInfo volumeInfo = (android.media.VolumeInfo) parcel.readTypedObject(android.media.VolumeInfo.CREATOR);
                    android.media.AudioDeviceAttributes audioDeviceAttributes = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDeviceVolume(volumeInfo, audioDeviceAttributes, readString7);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.media.VolumeInfo volumeInfo2 = (android.media.VolumeInfo) parcel.readTypedObject(android.media.VolumeInfo.CREATOR);
                    android.media.AudioDeviceAttributes audioDeviceAttributes2 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.media.VolumeInfo deviceVolume = getDeviceVolume(volumeInfo2, audioDeviceAttributes2, readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(deviceVolume, 1);
                    return true;
                case 16:
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    handleVolumeKey(keyEvent, readBoolean, readString9, readString10);
                    return true;
                case 17:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStreamMute = isStreamMute(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStreamMute);
                    return true;
                case 18:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    forceRemoteSubmixFullVolume(readBoolean2, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean isMasterMute = isMasterMute();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMasterMute);
                    return true;
                case 20:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt26 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMasterMute(readBoolean3, readInt26, readString11, readInt27, readString12);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int streamVolume = getStreamVolume(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamVolume);
                    return true;
                case 22:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int streamMinVolume = getStreamMinVolume(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamMinVolume);
                    return true;
                case 23:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int streamMaxVolume = getStreamMaxVolume(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamMaxVolume);
                    return true;
                case 24:
                    java.util.List<android.media.audiopolicy.AudioVolumeGroup> audioVolumeGroups = getAudioVolumeGroups();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(audioVolumeGroups, 1);
                    return true;
                case 25:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setVolumeGroupVolumeIndex(readInt31, readInt32, readInt33, readString13, readString14);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int volumeGroupVolumeIndex = getVolumeGroupVolumeIndex(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupVolumeIndex);
                    return true;
                case 27:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int volumeGroupMaxVolumeIndex = getVolumeGroupMaxVolumeIndex(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupMaxVolumeIndex);
                    return true;
                case 28:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int volumeGroupMinVolumeIndex = getVolumeGroupMinVolumeIndex(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupMinVolumeIndex);
                    return true;
                case 29:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lastAudibleVolumeForVolumeGroup = getLastAudibleVolumeForVolumeGroup(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeInt(lastAudibleVolumeForVolumeGroup);
                    return true;
                case 30:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isVolumeGroupMuted = isVolumeGroupMuted(readInt38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVolumeGroupMuted);
                    return true;
                case 31:
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    adjustVolumeGroupVolume(readInt39, readInt40, readInt41, readString15);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lastAudibleStreamVolume = getLastAudibleStreamVolume(readInt42);
                    parcel2.writeNoException();
                    parcel2.writeInt(lastAudibleStreamVolume);
                    return true;
                case 33:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setSupportedSystemUsages(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int[] supportedSystemUsages = getSupportedSystemUsages();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedSystemUsages);
                    return true;
                case 35:
                    java.util.List<android.media.audiopolicy.AudioProductStrategy> audioProductStrategies = getAudioProductStrategies();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(audioProductStrategies, 1);
                    return true;
                case 36:
                    boolean isMicrophoneMuted = isMicrophoneMuted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMicrophoneMuted);
                    return true;
                case 37:
                    boolean isUltrasoundSupported = isUltrasoundSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUltrasoundSupported);
                    return true;
                case 38:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isHotwordStreamSupported = isHotwordStreamSupported(readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHotwordStreamSupported);
                    return true;
                case 39:
                    boolean readBoolean5 = parcel.readBoolean();
                    java.lang.String readString16 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMicrophoneMute(readBoolean5, readString16, readInt43, readString17);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMicrophoneMuteFromSwitch(readBoolean6);
                    return true;
                case 41:
                    int readInt44 = parcel.readInt();
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRingerModeExternal(readInt44, readString18);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt45 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRingerModeInternal(readInt45, readString19);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int ringerModeExternal = getRingerModeExternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(ringerModeExternal);
                    return true;
                case 44:
                    int ringerModeInternal = getRingerModeInternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(ringerModeInternal);
                    return true;
                case 45:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isValidRingerMode = isValidRingerMode(readInt46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isValidRingerMode);
                    return true;
                case 46:
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVibrateSetting(readInt47, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vibrateSetting = getVibrateSetting(readInt49);
                    parcel2.writeNoException();
                    parcel2.writeInt(vibrateSetting);
                    return true;
                case 48:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldVibrate = shouldVibrate(readInt50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldVibrate);
                    return true;
                case 49:
                    int readInt51 = parcel.readInt();
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    java.lang.String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setMode(readInt51, readStrongBinder3, readString20);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int mode = getMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(mode);
                    return true;
                case 51:
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playSoundEffect(readInt52, readInt53);
                    return true;
                case 52:
                    int readInt54 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    playSoundEffectVolume(readInt54, readFloat);
                    return true;
                case 53:
                    boolean loadSoundEffects = loadSoundEffects();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(loadSoundEffects);
                    return true;
                case 54:
                    unloadSoundEffects();
                    return true;
                case 55:
                    reloadAudioSettings();
                    return true;
                case 56:
                    java.util.Map surroundFormats = getSurroundFormats();
                    parcel2.writeNoException();
                    parcel2.writeMap(surroundFormats);
                    return true;
                case 57:
                    java.util.List reportedSurroundFormats = getReportedSurroundFormats();
                    parcel2.writeNoException();
                    parcel2.writeList(reportedSurroundFormats);
                    return true;
                case 58:
                    int readInt55 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean surroundFormatEnabled = setSurroundFormatEnabled(readInt55, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(surroundFormatEnabled);
                    return true;
                case 59:
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSurroundFormatEnabled = isSurroundFormatEnabled(readInt56);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSurroundFormatEnabled);
                    return true;
                case 60:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean encodedSurroundMode = setEncodedSurroundMode(readInt57);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(encodedSurroundMode);
                    return true;
                case 61:
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int encodedSurroundMode2 = getEncodedSurroundMode(readInt58);
                    parcel2.writeNoException();
                    parcel2.writeInt(encodedSurroundMode2);
                    return true;
                case 62:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSpeakerphoneOn(readStrongBinder4, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    boolean isSpeakerphoneOn = isSpeakerphoneOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpeakerphoneOn);
                    return true;
                case 64:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothScoOn(readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setA2dpSuspended(readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLeAudioSuspended(readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    boolean isBluetoothScoOn = isBluetoothScoOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothScoOn);
                    return true;
                case 68:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothA2dpOn(readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    boolean isBluetoothA2dpOn = isBluetoothA2dpOn();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothA2dpOn);
                    return true;
                case 70:
                    android.media.AudioAttributes audioAttributes2 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    int readInt59 = parcel.readInt();
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    android.media.IAudioFocusDispatcher asInterface = android.media.IAudioFocusDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString21 = parcel.readString();
                    java.lang.String readString22 = parcel.readString();
                    java.lang.String readString23 = parcel.readString();
                    int readInt60 = parcel.readInt();
                    android.media.audiopolicy.IAudioPolicyCallback asInterface2 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int requestAudioFocus = requestAudioFocus(audioAttributes2, readInt59, readStrongBinder5, asInterface, readString21, readString22, readString23, readInt60, asInterface2, readInt61);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestAudioFocus);
                    return true;
                case 71:
                    android.media.IAudioFocusDispatcher asInterface3 = android.media.IAudioFocusDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString24 = parcel.readString();
                    android.media.AudioAttributes audioAttributes3 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int abandonAudioFocus = abandonAudioFocus(asInterface3, readString24, audioAttributes3, readString25);
                    parcel2.writeNoException();
                    parcel2.writeInt(abandonAudioFocus);
                    return true;
                case 72:
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAudioFocusClient(readString26);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int currentAudioFocus = getCurrentAudioFocus();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentAudioFocus);
                    return true;
                case 74:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startBluetoothSco(readStrongBinder6, readInt62);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startBluetoothScoVirtualCall(readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopBluetoothSco(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    int readInt63 = parcel.readInt();
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    forceVolumeControlStream(readInt63, readStrongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    android.media.IRingtonePlayer asInterface4 = android.media.IRingtonePlayer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setRingtonePlayer(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    android.media.IRingtonePlayer ringtonePlayer = getRingtonePlayer();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(ringtonePlayer);
                    return true;
                case 80:
                    int uiSoundsStreamType = getUiSoundsStreamType();
                    parcel2.writeNoException();
                    parcel2.writeInt(uiSoundsStreamType);
                    return true;
                case 81:
                    java.util.List independentStreamTypes = getIndependentStreamTypes();
                    parcel2.writeNoException();
                    parcel2.writeList(independentStreamTypes);
                    return true;
                case 82:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int streamTypeAlias = getStreamTypeAlias(readInt64);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamTypeAlias);
                    return true;
                case 83:
                    boolean isVolumeControlUsingVolumeGroups = isVolumeControlUsingVolumeGroups();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVolumeControlUsingVolumeGroups);
                    return true;
                case 84:
                    android.media.IStreamAliasingDispatcher asInterface5 = android.media.IStreamAliasingDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerStreamAliasingDispatcher(asInterface5, readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotifAliasRingForTest(readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    android.media.AudioDeviceAttributes audioDeviceAttributes3 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    int readInt65 = parcel.readInt();
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setWiredDeviceConnectionState(audioDeviceAttributes3, readInt65, readString27);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    android.media.IAudioRoutesObserver asInterface6 = android.media.IAudioRoutesObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.media.AudioRoutesInfo startWatchingRoutes = startWatchingRoutes(asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startWatchingRoutes, 1);
                    return true;
                case 88:
                    boolean isCameraSoundForced = isCameraSoundForced();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraSoundForced);
                    return true;
                case 89:
                    android.media.IVolumeController asInterface7 = android.media.IVolumeController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setVolumeController(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    android.media.IVolumeController volumeController = getVolumeController();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(volumeController);
                    return true;
                case 91:
                    android.media.IVolumeController asInterface8 = android.media.IVolumeController.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyVolumeControllerVisible(asInterface8, readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStreamAffectedByRingerMode = isStreamAffectedByRingerMode(readInt66);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStreamAffectedByRingerMode);
                    return true;
                case 93:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStreamAffectedByMute = isStreamAffectedByMute(readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStreamAffectedByMute);
                    return true;
                case 94:
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disableSafeMediaVolume(readString28);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    java.lang.String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    lowerVolumeToRs1(readString29);
                    return true;
                case 96:
                    float outputRs2UpperBound = getOutputRs2UpperBound();
                    parcel2.writeNoException();
                    parcel2.writeFloat(outputRs2UpperBound);
                    return true;
                case 97:
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setOutputRs2UpperBound(readFloat2);
                    return true;
                case 98:
                    float csd = getCsd();
                    parcel2.writeNoException();
                    parcel2.writeFloat(csd);
                    return true;
                case 99:
                    float readFloat3 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setCsd(readFloat3);
                    return true;
                case 100:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceUseFrameworkMel(readBoolean16);
                    return true;
                case 101:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceComputeCsdOnAllDevices(readBoolean17);
                    return true;
                case 102:
                    boolean isCsdEnabled = isCsdEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCsdEnabled);
                    return true;
                case 103:
                    boolean isCsdAsAFeatureAvailable = isCsdAsAFeatureAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCsdAsAFeatureAvailable);
                    return true;
                case 104:
                    boolean isCsdAsAFeatureEnabled = isCsdAsAFeatureEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCsdAsAFeatureEnabled);
                    return true;
                case 105:
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCsdAsAFeatureEnabled(readBoolean18);
                    return true;
                case 106:
                    java.lang.String readString30 = parcel.readString();
                    boolean readBoolean19 = parcel.readBoolean();
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBluetoothAudioDeviceCategory_legacy(readString30, readBoolean19, readInt68);
                    return true;
                case 107:
                    java.lang.String readString31 = parcel.readString();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int bluetoothAudioDeviceCategory_legacy = getBluetoothAudioDeviceCategory_legacy(readString31, readBoolean20);
                    parcel2.writeNoException();
                    parcel2.writeInt(bluetoothAudioDeviceCategory_legacy);
                    return true;
                case 108:
                    java.lang.String readString32 = parcel.readString();
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bluetoothAudioDeviceCategory = setBluetoothAudioDeviceCategory(readString32, readInt69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bluetoothAudioDeviceCategory);
                    return true;
                case 109:
                    java.lang.String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int bluetoothAudioDeviceCategory2 = getBluetoothAudioDeviceCategory(readString33);
                    parcel2.writeNoException();
                    parcel2.writeInt(bluetoothAudioDeviceCategory2);
                    return true;
                case 110:
                    java.lang.String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isBluetoothAudioDeviceCategoryFixed = isBluetoothAudioDeviceCategoryFixed(readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothAudioDeviceCategoryFixed);
                    return true;
                case 111:
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int hdmiSystemAudioSupported = setHdmiSystemAudioSupported(readBoolean21);
                    parcel2.writeNoException();
                    parcel2.writeInt(hdmiSystemAudioSupported);
                    return true;
                case 112:
                    boolean isHdmiSystemAudioSupported = isHdmiSystemAudioSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHdmiSystemAudioSupported);
                    return true;
                case 113:
                    android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig = (android.media.audiopolicy.AudioPolicyConfig) parcel.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
                    android.media.audiopolicy.IAudioPolicyCallback asInterface9 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean22 = parcel.readBoolean();
                    boolean readBoolean23 = parcel.readBoolean();
                    boolean readBoolean24 = parcel.readBoolean();
                    boolean readBoolean25 = parcel.readBoolean();
                    android.media.projection.IMediaProjection asInterface10 = android.media.projection.IMediaProjection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    java.lang.String registerAudioPolicy = registerAudioPolicy(audioPolicyConfig, asInterface9, readBoolean22, readBoolean23, readBoolean24, readBoolean25, asInterface10);
                    parcel2.writeNoException();
                    parcel2.writeString(registerAudioPolicy);
                    return true;
                case 114:
                    android.media.audiopolicy.IAudioPolicyCallback asInterface11 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAudioPolicyAsync(asInterface11);
                    return true;
                case 115:
                    java.util.List<android.media.audiopolicy.AudioMix> registeredPolicyMixes = getRegisteredPolicyMixes();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(registeredPolicyMixes, 1);
                    return true;
                case 116:
                    android.media.audiopolicy.IAudioPolicyCallback asInterface12 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAudioPolicy(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig2 = (android.media.audiopolicy.AudioPolicyConfig) parcel.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
                    android.media.audiopolicy.IAudioPolicyCallback asInterface13 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int addMixForPolicy = addMixForPolicy(audioPolicyConfig2, asInterface13);
                    parcel2.writeNoException();
                    parcel2.writeInt(addMixForPolicy);
                    return true;
                case 118:
                    android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig3 = (android.media.audiopolicy.AudioPolicyConfig) parcel.readTypedObject(android.media.audiopolicy.AudioPolicyConfig.CREATOR);
                    android.media.audiopolicy.IAudioPolicyCallback asInterface14 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int removeMixForPolicy = removeMixForPolicy(audioPolicyConfig3, asInterface14);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeMixForPolicy);
                    return true;
                case 119:
                    android.media.audiopolicy.AudioMix[] audioMixArr = (android.media.audiopolicy.AudioMix[]) parcel.createTypedArray(android.media.audiopolicy.AudioMix.CREATOR);
                    android.media.audiopolicy.AudioMixingRule[] audioMixingRuleArr = (android.media.audiopolicy.AudioMixingRule[]) parcel.createTypedArray(android.media.audiopolicy.AudioMixingRule.CREATOR);
                    android.media.audiopolicy.IAudioPolicyCallback asInterface15 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int updateMixingRulesForPolicy = updateMixingRulesForPolicy(audioMixArr, audioMixingRuleArr, asInterface15);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateMixingRulesForPolicy);
                    return true;
                case 120:
                    int readInt70 = parcel.readInt();
                    android.media.audiopolicy.IAudioPolicyCallback asInterface16 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int focusPropertiesForPolicy = setFocusPropertiesForPolicy(readInt70, asInterface16);
                    parcel2.writeNoException();
                    parcel2.writeInt(focusPropertiesForPolicy);
                    return true;
                case 121:
                    android.media.VolumePolicy volumePolicy = (android.media.VolumePolicy) parcel.readTypedObject(android.media.VolumePolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVolumePolicy(volumePolicy);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    boolean hasRegisteredDynamicPolicy = hasRegisteredDynamicPolicy();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasRegisteredDynamicPolicy);
                    return true;
                case 123:
                    android.media.IRecordingConfigDispatcher asInterface17 = android.media.IRecordingConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRecordingCallback(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 124:
                    android.media.IRecordingConfigDispatcher asInterface18 = android.media.IRecordingConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRecordingCallback(asInterface18);
                    return true;
                case 125:
                    java.util.List<android.media.AudioRecordingConfiguration> activeRecordingConfigurations = getActiveRecordingConfigurations();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activeRecordingConfigurations, 1);
                    return true;
                case 126:
                    android.media.IPlaybackConfigDispatcher asInterface19 = android.media.IPlaybackConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPlaybackCallback(asInterface19);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    android.media.IPlaybackConfigDispatcher asInterface20 = android.media.IPlaybackConfigDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPlaybackCallback(asInterface20);
                    return true;
                case 128:
                    java.util.List<android.media.AudioPlaybackConfiguration> activePlaybackConfigurations = getActivePlaybackConfigurations();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(activePlaybackConfigurations, 1);
                    return true;
                case 129:
                    int readInt71 = parcel.readInt();
                    android.media.AudioAttributes audioAttributes4 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int focusRampTimeMs = getFocusRampTimeMs(readInt71, audioAttributes4);
                    parcel2.writeNoException();
                    parcel2.writeInt(focusRampTimeMs);
                    return true;
                case 130:
                    android.media.AudioFocusInfo audioFocusInfo = (android.media.AudioFocusInfo) parcel.readTypedObject(android.media.AudioFocusInfo.CREATOR);
                    int readInt72 = parcel.readInt();
                    android.media.audiopolicy.IAudioPolicyCallback asInterface21 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int dispatchFocusChange = dispatchFocusChange(audioFocusInfo, readInt72, asInterface21);
                    parcel2.writeNoException();
                    parcel2.writeInt(dispatchFocusChange);
                    return true;
                case 131:
                    android.media.AudioFocusInfo audioFocusInfo2 = (android.media.AudioFocusInfo) parcel.readTypedObject(android.media.AudioFocusInfo.CREATOR);
                    int readInt73 = parcel.readInt();
                    android.media.audiopolicy.IAudioPolicyCallback asInterface22 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.AudioFocusInfo.CREATOR);
                    android.media.FadeManagerConfiguration fadeManagerConfiguration = (android.media.FadeManagerConfiguration) parcel.readTypedObject(android.media.FadeManagerConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    int dispatchFocusChangeWithFade = dispatchFocusChangeWithFade(audioFocusInfo2, readInt73, asInterface22, createTypedArrayList, fadeManagerConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeInt(dispatchFocusChangeWithFade);
                    return true;
                case 132:
                    int readInt74 = parcel.readInt();
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    playerHasOpPlayAudio(readInt74, readBoolean26);
                    return true;
                case 133:
                    android.bluetooth.BluetoothDevice bluetoothDevice = (android.bluetooth.BluetoothDevice) parcel.readTypedObject(android.bluetooth.BluetoothDevice.CREATOR);
                    android.bluetooth.BluetoothDevice bluetoothDevice2 = (android.bluetooth.BluetoothDevice) parcel.readTypedObject(android.bluetooth.BluetoothDevice.CREATOR);
                    android.media.BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo = (android.media.BluetoothProfileConnectionInfo) parcel.readTypedObject(android.media.BluetoothProfileConnectionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleBluetoothActiveDeviceChanged(bluetoothDevice, bluetoothDevice2, bluetoothProfileConnectionInfo);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    android.media.AudioFocusInfo audioFocusInfo3 = (android.media.AudioFocusInfo) parcel.readTypedObject(android.media.AudioFocusInfo.CREATOR);
                    int readInt75 = parcel.readInt();
                    android.media.audiopolicy.IAudioPolicyCallback asInterface23 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setFocusRequestResultFromExtPolicy(audioFocusInfo3, readInt75, asInterface23);
                    return true;
                case 135:
                    android.media.IAudioServerStateDispatcher asInterface24 = android.media.IAudioServerStateDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAudioServerStateDispatcher(asInterface24);
                    parcel2.writeNoException();
                    return true;
                case 136:
                    android.media.IAudioServerStateDispatcher asInterface25 = android.media.IAudioServerStateDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAudioServerStateDispatcher(asInterface25);
                    return true;
                case 137:
                    boolean isAudioServerRunning = isAudioServerRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAudioServerRunning);
                    return true;
                case 138:
                    android.media.audiopolicy.IAudioPolicyCallback asInterface26 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt76 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int uidDeviceAffinity = setUidDeviceAffinity(asInterface26, readInt76, createIntArray2, createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(uidDeviceAffinity);
                    return true;
                case 139:
                    android.media.audiopolicy.IAudioPolicyCallback asInterface27 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removeUidDeviceAffinity = removeUidDeviceAffinity(asInterface27, readInt77);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeUidDeviceAffinity);
                    return true;
                case 140:
                    android.media.audiopolicy.IAudioPolicyCallback asInterface28 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt78 = parcel.readInt();
                    int[] createIntArray3 = parcel.createIntArray();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int userIdDeviceAffinity = setUserIdDeviceAffinity(asInterface28, readInt78, createIntArray3, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeInt(userIdDeviceAffinity);
                    return true;
                case 141:
                    android.media.audiopolicy.IAudioPolicyCallback asInterface29 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removeUserIdDeviceAffinity = removeUserIdDeviceAffinity(asInterface29, readInt79);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeUserIdDeviceAffinity);
                    return true;
                case 142:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasHapticChannels = hasHapticChannels(uri);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasHapticChannels);
                    return true;
                case 143:
                    boolean isCallScreeningModeSupported = isCallScreeningModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallScreeningModeSupported);
                    return true;
                case 144:
                    int readInt80 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int preferredDevicesForStrategy = setPreferredDevicesForStrategy(readInt80, createTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredDevicesForStrategy);
                    return true;
                case 145:
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removePreferredDevicesForStrategy = removePreferredDevicesForStrategy(readInt81);
                    parcel2.writeNoException();
                    parcel2.writeInt(removePreferredDevicesForStrategy);
                    return true;
                case 146:
                    int readInt82 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.AudioDeviceAttributes> preferredDevicesForStrategy2 = getPreferredDevicesForStrategy(readInt82);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(preferredDevicesForStrategy2, 1);
                    return true;
                case 147:
                    int readInt83 = parcel.readInt();
                    android.media.AudioDeviceAttributes audioDeviceAttributes4 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceAsNonDefaultForStrategy = setDeviceAsNonDefaultForStrategy(readInt83, audioDeviceAttributes4);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceAsNonDefaultForStrategy);
                    return true;
                case 148:
                    int readInt84 = parcel.readInt();
                    android.media.AudioDeviceAttributes audioDeviceAttributes5 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int removeDeviceAsNonDefaultForStrategy = removeDeviceAsNonDefaultForStrategy(readInt84, audioDeviceAttributes5);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeDeviceAsNonDefaultForStrategy);
                    return true;
                case 149:
                    int readInt85 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.AudioDeviceAttributes> nonDefaultDevicesForStrategy = getNonDefaultDevicesForStrategy(readInt85);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(nonDefaultDevicesForStrategy, 1);
                    return true;
                case 150:
                    android.media.AudioAttributes audioAttributes5 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.AudioDeviceAttributes> devicesForAttributes = getDevicesForAttributes(audioAttributes5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(devicesForAttributes, 1);
                    return true;
                case 151:
                    android.media.AudioAttributes audioAttributes6 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.AudioDeviceAttributes> devicesForAttributesUnprotected = getDevicesForAttributesUnprotected(audioAttributes6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(devicesForAttributesUnprotected, 1);
                    return true;
                case 152:
                    android.media.AudioAttributes audioAttributes7 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    android.media.IDevicesForAttributesCallback asInterface30 = android.media.IDevicesForAttributesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnDevicesForAttributesChangedListener(audioAttributes7, asInterface30);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    android.media.IDevicesForAttributesCallback asInterface31 = android.media.IDevicesForAttributesCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnDevicesForAttributesChangedListener(asInterface31);
                    return true;
                case 154:
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int allowedCapturePolicy = setAllowedCapturePolicy(readInt86);
                    parcel2.writeNoException();
                    parcel2.writeInt(allowedCapturePolicy);
                    return true;
                case 155:
                    int allowedCapturePolicy2 = getAllowedCapturePolicy();
                    parcel2.writeNoException();
                    parcel2.writeInt(allowedCapturePolicy2);
                    return true;
                case 156:
                    android.media.IStrategyPreferredDevicesDispatcher asInterface32 = android.media.IStrategyPreferredDevicesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStrategyPreferredDevicesDispatcher(asInterface32);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    android.media.IStrategyPreferredDevicesDispatcher asInterface33 = android.media.IStrategyPreferredDevicesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStrategyPreferredDevicesDispatcher(asInterface33);
                    return true;
                case 158:
                    android.media.IStrategyNonDefaultDevicesDispatcher asInterface34 = android.media.IStrategyNonDefaultDevicesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStrategyNonDefaultDevicesDispatcher(asInterface34);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    android.media.IStrategyNonDefaultDevicesDispatcher asInterface35 = android.media.IStrategyNonDefaultDevicesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterStrategyNonDefaultDevicesDispatcher(asInterface35);
                    return true;
                case 160:
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRttEnabled(readBoolean27);
                    return true;
                case 161:
                    android.media.AudioDeviceAttributes audioDeviceAttributes6 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    int readInt87 = parcel.readInt();
                    java.lang.String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setDeviceVolumeBehavior(audioDeviceAttributes6, readInt87, readString35);
                    parcel2.writeNoException();
                    return true;
                case 162:
                    android.media.AudioDeviceAttributes audioDeviceAttributes7 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceVolumeBehavior = getDeviceVolumeBehavior(audioDeviceAttributes7);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceVolumeBehavior);
                    return true;
                case 163:
                    boolean readBoolean28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMultiAudioFocusEnabled(readBoolean28);
                    return true;
                case 164:
                    int readInt88 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int preferredDevicesForCapturePreset = setPreferredDevicesForCapturePreset(readInt88, createTypedArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredDevicesForCapturePreset);
                    return true;
                case 165:
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int clearPreferredDevicesForCapturePreset = clearPreferredDevicesForCapturePreset(readInt89);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearPreferredDevicesForCapturePreset);
                    return true;
                case 166:
                    int readInt90 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.AudioDeviceAttributes> preferredDevicesForCapturePreset2 = getPreferredDevicesForCapturePreset(readInt90);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(preferredDevicesForCapturePreset2, 1);
                    return true;
                case 167:
                    android.media.ICapturePresetDevicesRoleDispatcher asInterface36 = android.media.ICapturePresetDevicesRoleDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCapturePresetDevicesRoleDispatcher(asInterface36);
                    parcel2.writeNoException();
                    return true;
                case 168:
                    android.media.ICapturePresetDevicesRoleDispatcher asInterface37 = android.media.ICapturePresetDevicesRoleDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCapturePresetDevicesRoleDispatcher(asInterface37);
                    return true;
                case 169:
                    int readInt91 = parcel.readInt();
                    int readInt92 = parcel.readInt();
                    int readInt93 = parcel.readInt();
                    java.lang.String readString36 = parcel.readString();
                    int readInt94 = parcel.readInt();
                    int readInt95 = parcel.readInt();
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    int readInt96 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustStreamVolumeForUid(readInt91, readInt92, readInt93, readString36, readInt94, readInt95, userHandle, readInt96);
                    return true;
                case 170:
                    int readInt97 = parcel.readInt();
                    int readInt98 = parcel.readInt();
                    int readInt99 = parcel.readInt();
                    java.lang.String readString37 = parcel.readString();
                    int readInt100 = parcel.readInt();
                    int readInt101 = parcel.readInt();
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    int readInt102 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustSuggestedStreamVolumeForUid(readInt97, readInt98, readInt99, readString37, readInt100, readInt101, userHandle2, readInt102);
                    return true;
                case 171:
                    int readInt103 = parcel.readInt();
                    int readInt104 = parcel.readInt();
                    int readInt105 = parcel.readInt();
                    java.lang.String readString38 = parcel.readString();
                    int readInt106 = parcel.readInt();
                    int readInt107 = parcel.readInt();
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    int readInt108 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStreamVolumeForUid(readInt103, readInt104, readInt105, readString38, readInt106, readInt107, userHandle3, readInt108);
                    return true;
                case 172:
                    int readInt109 = parcel.readInt();
                    int readInt110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustVolume(readInt109, readInt110);
                    return true;
                case 173:
                    int readInt111 = parcel.readInt();
                    int readInt112 = parcel.readInt();
                    int readInt113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustSuggestedStreamVolume(readInt111, readInt112, readInt113);
                    return true;
                case 174:
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isMusicActive = isMusicActive(readBoolean29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMusicActive);
                    return true;
                case 175:
                    int readInt114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deviceMaskForStream = getDeviceMaskForStream(readInt114);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceMaskForStream);
                    return true;
                case 176:
                    int[] availableCommunicationDeviceIds = getAvailableCommunicationDeviceIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(availableCommunicationDeviceIds);
                    return true;
                case 177:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt115 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean communicationDevice = setCommunicationDevice(readStrongBinder10, readInt115);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(communicationDevice);
                    return true;
                case 178:
                    int communicationDevice2 = getCommunicationDevice();
                    parcel2.writeNoException();
                    parcel2.writeInt(communicationDevice2);
                    return true;
                case 179:
                    android.media.ICommunicationDeviceDispatcher asInterface38 = android.media.ICommunicationDeviceDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCommunicationDeviceDispatcher(asInterface38);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    android.media.ICommunicationDeviceDispatcher asInterface39 = android.media.ICommunicationDeviceDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCommunicationDeviceDispatcher(asInterface39);
                    return true;
                case 181:
                    boolean areNavigationRepeatSoundEffectsEnabled = areNavigationRepeatSoundEffectsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areNavigationRepeatSoundEffectsEnabled);
                    return true;
                case 182:
                    boolean readBoolean30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavigationRepeatSoundEffectsEnabled(readBoolean30);
                    return true;
                case 183:
                    boolean isHomeSoundEffectEnabled = isHomeSoundEffectEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHomeSoundEffectEnabled);
                    return true;
                case 184:
                    boolean readBoolean31 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHomeSoundEffectEnabled(readBoolean31);
                    return true;
                case 185:
                    android.media.AudioDeviceAttributes audioDeviceAttributes8 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean additionalOutputDeviceDelay = setAdditionalOutputDeviceDelay(audioDeviceAttributes8, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(additionalOutputDeviceDelay);
                    return true;
                case 186:
                    android.media.AudioDeviceAttributes audioDeviceAttributes9 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    long additionalOutputDeviceDelay2 = getAdditionalOutputDeviceDelay(audioDeviceAttributes9);
                    parcel2.writeNoException();
                    parcel2.writeLong(additionalOutputDeviceDelay2);
                    return true;
                case 187:
                    android.media.AudioDeviceAttributes audioDeviceAttributes10 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    long maxAdditionalOutputDeviceDelay = getMaxAdditionalOutputDeviceDelay(audioDeviceAttributes10);
                    parcel2.writeNoException();
                    parcel2.writeLong(maxAdditionalOutputDeviceDelay);
                    return true;
                case 188:
                    android.media.AudioAttributes audioAttributes8 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    int readInt116 = parcel.readInt();
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    android.media.IAudioFocusDispatcher asInterface40 = android.media.IAudioFocusDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString39 = parcel.readString();
                    java.lang.String readString40 = parcel.readString();
                    int readInt117 = parcel.readInt();
                    int readInt118 = parcel.readInt();
                    int readInt119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int requestAudioFocusForTest = requestAudioFocusForTest(audioAttributes8, readInt116, readStrongBinder11, asInterface40, readString39, readString40, readInt117, readInt118, readInt119);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestAudioFocusForTest);
                    return true;
                case 189:
                    android.media.IAudioFocusDispatcher asInterface41 = android.media.IAudioFocusDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString41 = parcel.readString();
                    android.media.AudioAttributes audioAttributes9 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    java.lang.String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int abandonAudioFocusForTest = abandonAudioFocusForTest(asInterface41, readString41, audioAttributes9, readString42);
                    parcel2.writeNoException();
                    parcel2.writeInt(abandonAudioFocusForTest);
                    return true;
                case 190:
                    android.media.AudioAttributes audioAttributes10 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    long fadeOutDurationOnFocusLossMillis = getFadeOutDurationOnFocusLossMillis(audioAttributes10);
                    parcel2.writeNoException();
                    parcel2.writeLong(fadeOutDurationOnFocusLossMillis);
                    return true;
                case 191:
                    java.util.List focusDuckedUidsForTest = getFocusDuckedUidsForTest();
                    parcel2.writeNoException();
                    parcel2.writeList(focusDuckedUidsForTest);
                    return true;
                case 192:
                    long focusFadeOutDurationForTest = getFocusFadeOutDurationForTest();
                    parcel2.writeNoException();
                    parcel2.writeLong(focusFadeOutDurationForTest);
                    return true;
                case 193:
                    long focusUnmuteDelayAfterFadeOutForTest = getFocusUnmuteDelayAfterFadeOutForTest();
                    parcel2.writeNoException();
                    parcel2.writeLong(focusUnmuteDelayAfterFadeOutForTest);
                    return true;
                case 194:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean enterAudioFocusFreezeForTest = enterAudioFocusFreezeForTest(readStrongBinder12, createIntArray4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enterAudioFocusFreezeForTest);
                    return true;
                case 195:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean exitAudioFocusFreezeForTest = exitAudioFocusFreezeForTest(readStrongBinder13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(exitAudioFocusFreezeForTest);
                    return true;
                case 196:
                    android.media.IAudioModeDispatcher asInterface42 = android.media.IAudioModeDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerModeDispatcher(asInterface42);
                    parcel2.writeNoException();
                    return true;
                case 197:
                    android.media.IAudioModeDispatcher asInterface43 = android.media.IAudioModeDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterModeDispatcher(asInterface43);
                    return true;
                case 198:
                    int spatializerImmersiveAudioLevel = getSpatializerImmersiveAudioLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(spatializerImmersiveAudioLevel);
                    return true;
                case 199:
                    boolean isSpatializerEnabled = isSpatializerEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpatializerEnabled);
                    return true;
                case 200:
                    boolean isSpatializerAvailable = isSpatializerAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpatializerAvailable);
                    return true;
                case 201:
                    android.media.AudioDeviceAttributes audioDeviceAttributes11 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSpatializerAvailableForDevice = isSpatializerAvailableForDevice(audioDeviceAttributes11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSpatializerAvailableForDevice);
                    return true;
                case 202:
                    android.media.AudioDeviceAttributes audioDeviceAttributes12 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean hasHeadTracker = hasHeadTracker(audioDeviceAttributes12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasHeadTracker);
                    return true;
                case 203:
                    boolean readBoolean32 = parcel.readBoolean();
                    android.media.AudioDeviceAttributes audioDeviceAttributes13 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    setHeadTrackerEnabled(readBoolean32, audioDeviceAttributes13);
                    parcel2.writeNoException();
                    return true;
                case 204:
                    android.media.AudioDeviceAttributes audioDeviceAttributes14 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isHeadTrackerEnabled = isHeadTrackerEnabled(audioDeviceAttributes14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHeadTrackerEnabled);
                    return true;
                case 205:
                    boolean isHeadTrackerAvailable = isHeadTrackerAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHeadTrackerAvailable);
                    return true;
                case 206:
                    android.media.ISpatializerHeadTrackerAvailableCallback asInterface44 = android.media.ISpatializerHeadTrackerAvailableCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerSpatializerHeadTrackerAvailableCallback(asInterface44, readBoolean33);
                    parcel2.writeNoException();
                    return true;
                case 207:
                    boolean readBoolean34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSpatializerEnabled(readBoolean34);
                    parcel2.writeNoException();
                    return true;
                case 208:
                    android.media.AudioAttributes audioAttributes11 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    android.media.AudioFormat audioFormat = (android.media.AudioFormat) parcel.readTypedObject(android.media.AudioFormat.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean canBeSpatialized = canBeSpatialized(audioAttributes11, audioFormat);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canBeSpatialized);
                    return true;
                case 209:
                    android.media.ISpatializerCallback asInterface45 = android.media.ISpatializerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSpatializerCallback(asInterface45);
                    parcel2.writeNoException();
                    return true;
                case 210:
                    android.media.ISpatializerCallback asInterface46 = android.media.ISpatializerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSpatializerCallback(asInterface46);
                    parcel2.writeNoException();
                    return true;
                case 211:
                    android.media.ISpatializerHeadTrackingModeCallback asInterface47 = android.media.ISpatializerHeadTrackingModeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSpatializerHeadTrackingCallback(asInterface47);
                    parcel2.writeNoException();
                    return true;
                case 212:
                    android.media.ISpatializerHeadTrackingModeCallback asInterface48 = android.media.ISpatializerHeadTrackingModeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSpatializerHeadTrackingCallback(asInterface48);
                    parcel2.writeNoException();
                    return true;
                case 213:
                    android.media.ISpatializerHeadToSoundStagePoseCallback asInterface49 = android.media.ISpatializerHeadToSoundStagePoseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerHeadToSoundstagePoseCallback(asInterface49);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    android.media.ISpatializerHeadToSoundStagePoseCallback asInterface50 = android.media.ISpatializerHeadToSoundStagePoseCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterHeadToSoundstagePoseCallback(asInterface50);
                    parcel2.writeNoException();
                    return true;
                case 215:
                    java.util.List<android.media.AudioDeviceAttributes> spatializerCompatibleAudioDevices = getSpatializerCompatibleAudioDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(spatializerCompatibleAudioDevices, 1);
                    return true;
                case 216:
                    android.media.AudioDeviceAttributes audioDeviceAttributes15 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    addSpatializerCompatibleAudioDevice(audioDeviceAttributes15);
                    parcel2.writeNoException();
                    return true;
                case 217:
                    android.media.AudioDeviceAttributes audioDeviceAttributes16 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeSpatializerCompatibleAudioDevice(audioDeviceAttributes16);
                    parcel2.writeNoException();
                    return true;
                case 218:
                    int readInt120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDesiredHeadTrackingMode(readInt120);
                    parcel2.writeNoException();
                    return true;
                case 219:
                    int desiredHeadTrackingMode = getDesiredHeadTrackingMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(desiredHeadTrackingMode);
                    return true;
                case 220:
                    int[] supportedHeadTrackingModes = getSupportedHeadTrackingModes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedHeadTrackingModes);
                    return true;
                case 221:
                    int actualHeadTrackingMode = getActualHeadTrackingMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(actualHeadTrackingMode);
                    return true;
                case 222:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setSpatializerGlobalTransform(createFloatArray);
                    return true;
                case 223:
                    recenterHeadTracker();
                    return true;
                case 224:
                    int readInt121 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setSpatializerParameter(readInt121, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 225:
                    int readInt122 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    getSpatializerParameter(readInt122, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(createByteArray2);
                    return true;
                case 226:
                    int spatializerOutput = getSpatializerOutput();
                    parcel2.writeNoException();
                    parcel2.writeInt(spatializerOutput);
                    return true;
                case 227:
                    android.media.ISpatializerOutputCallback asInterface51 = android.media.ISpatializerOutputCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSpatializerOutputCallback(asInterface51);
                    parcel2.writeNoException();
                    return true;
                case 228:
                    android.media.ISpatializerOutputCallback asInterface52 = android.media.ISpatializerOutputCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSpatializerOutputCallback(asInterface52);
                    parcel2.writeNoException();
                    return true;
                case 229:
                    boolean isVolumeFixed = isVolumeFixed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isVolumeFixed);
                    return true;
                case 230:
                    android.media.VolumeInfo defaultVolumeInfo = getDefaultVolumeInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultVolumeInfo, 1);
                    return true;
                case 231:
                    boolean isPstnCallAudioInterceptable = isPstnCallAudioInterceptable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPstnCallAudioInterceptable);
                    return true;
                case 232:
                    int[] createIntArray5 = parcel.createIntArray();
                    android.media.AudioDeviceAttributes audioDeviceAttributes17 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    muteAwaitConnection(createIntArray5, audioDeviceAttributes17, readLong2);
                    return true;
                case 233:
                    android.media.AudioDeviceAttributes audioDeviceAttributes18 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelMuteAwaitConnection(audioDeviceAttributes18);
                    return true;
                case 234:
                    android.media.AudioDeviceAttributes mutingExpectedDevice = getMutingExpectedDevice();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mutingExpectedDevice, 1);
                    return true;
                case 235:
                    android.media.IMuteAwaitConnectionCallback asInterface53 = android.media.IMuteAwaitConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean35 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerMuteAwaitConnectionDispatcher(asInterface53, readBoolean35);
                    parcel2.writeNoException();
                    return true;
                case 236:
                    android.media.AudioDeviceAttributes audioDeviceAttributes19 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    boolean readBoolean36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTestDeviceConnectionState(audioDeviceAttributes19, readBoolean36);
                    parcel2.writeNoException();
                    return true;
                case 237:
                    boolean readBoolean37 = parcel.readBoolean();
                    android.media.IDeviceVolumeBehaviorDispatcher asInterface54 = android.media.IDeviceVolumeBehaviorDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDeviceVolumeBehaviorDispatcher(readBoolean37, asInterface54);
                    parcel2.writeNoException();
                    return true;
                case 238:
                    java.util.List<android.media.AudioFocusInfo> focusStack = getFocusStack();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(focusStack, 1);
                    return true;
                case 239:
                    android.media.AudioFocusInfo audioFocusInfo4 = (android.media.AudioFocusInfo) parcel.readTypedObject(android.media.AudioFocusInfo.CREATOR);
                    android.media.audiopolicy.IAudioPolicyCallback asInterface55 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean sendFocusLoss = sendFocusLoss(audioFocusInfo4, asInterface55);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendFocusLoss);
                    return true;
                case 240:
                    int[] createIntArray6 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    addAssistantServicesUids(createIntArray6);
                    parcel2.writeNoException();
                    return true;
                case 241:
                    int[] createIntArray7 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    removeAssistantServicesUids(createIntArray7);
                    parcel2.writeNoException();
                    return true;
                case 242:
                    int[] createIntArray8 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setActiveAssistantServiceUids(createIntArray8);
                    parcel2.writeNoException();
                    return true;
                case 243:
                    int[] assistantServicesUids = getAssistantServicesUids();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(assistantServicesUids);
                    return true;
                case 244:
                    int[] activeAssistantServiceUids = getActiveAssistantServiceUids();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(activeAssistantServiceUids);
                    return true;
                case 245:
                    boolean readBoolean38 = parcel.readBoolean();
                    android.media.IAudioDeviceVolumeDispatcher asInterface56 = android.media.IAudioDeviceVolumeDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString43 = parcel.readString();
                    android.media.AudioDeviceAttributes audioDeviceAttributes20 = (android.media.AudioDeviceAttributes) parcel.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                    java.util.ArrayList createTypedArrayList4 = parcel.createTypedArrayList(android.media.VolumeInfo.CREATOR);
                    boolean readBoolean39 = parcel.readBoolean();
                    int readInt123 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDeviceVolumeDispatcherForAbsoluteVolume(readBoolean38, asInterface56, readString43, audioDeviceAttributes20, createTypedArrayList4, readBoolean39, readInt123);
                    parcel2.writeNoException();
                    return true;
                case 246:
                    android.media.AudioHalVersionInfo halVersion = getHalVersion();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(halVersion, 1);
                    return true;
                case 247:
                    android.media.AudioAttributes audioAttributes12 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    int readInt124 = parcel.readInt();
                    android.media.AudioMixerAttributes audioMixerAttributes = (android.media.AudioMixerAttributes) parcel.readTypedObject(android.media.AudioMixerAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int preferredMixerAttributes = setPreferredMixerAttributes(audioAttributes12, readInt124, audioMixerAttributes);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredMixerAttributes);
                    return true;
                case 248:
                    android.media.AudioAttributes audioAttributes13 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    int readInt125 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int clearPreferredMixerAttributes = clearPreferredMixerAttributes(audioAttributes13, readInt125);
                    parcel2.writeNoException();
                    parcel2.writeInt(clearPreferredMixerAttributes);
                    return true;
                case 249:
                    android.media.IPreferredMixerAttributesDispatcher asInterface57 = android.media.IPreferredMixerAttributesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPreferredMixerAttributesDispatcher(asInterface57);
                    parcel2.writeNoException();
                    return true;
                case 250:
                    android.media.IPreferredMixerAttributesDispatcher asInterface58 = android.media.IPreferredMixerAttributesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterPreferredMixerAttributesDispatcher(asInterface58);
                    return true;
                case 251:
                    boolean supportsBluetoothVariableLatency = supportsBluetoothVariableLatency();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsBluetoothVariableLatency);
                    return true;
                case 252:
                    boolean readBoolean40 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothVariableLatencyEnabled(readBoolean40);
                    parcel2.writeNoException();
                    return true;
                case 253:
                    boolean isBluetoothVariableLatencyEnabled = isBluetoothVariableLatencyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBluetoothVariableLatencyEnabled);
                    return true;
                case 254:
                    android.media.ILoudnessCodecUpdatesDispatcher asInterface59 = android.media.ILoudnessCodecUpdatesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerLoudnessCodecUpdatesDispatcher(asInterface59);
                    parcel2.writeNoException();
                    return true;
                case 255:
                    android.media.ILoudnessCodecUpdatesDispatcher asInterface60 = android.media.ILoudnessCodecUpdatesDispatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterLoudnessCodecUpdatesDispatcher(asInterface60);
                    parcel2.writeNoException();
                    return true;
                case 256:
                    int readInt126 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startLoudnessCodecUpdates(readInt126);
                    return true;
                case 257:
                    int readInt127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopLoudnessCodecUpdates(readInt127);
                    return true;
                case 258:
                    int readInt128 = parcel.readInt();
                    int readInt129 = parcel.readInt();
                    android.media.LoudnessCodecInfo loudnessCodecInfo = (android.media.LoudnessCodecInfo) parcel.readTypedObject(android.media.LoudnessCodecInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    addLoudnessCodecInfo(readInt128, readInt129, loudnessCodecInfo);
                    return true;
                case 259:
                    int readInt130 = parcel.readInt();
                    android.media.LoudnessCodecInfo loudnessCodecInfo2 = (android.media.LoudnessCodecInfo) parcel.readTypedObject(android.media.LoudnessCodecInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeLoudnessCodecInfo(readInt130, loudnessCodecInfo2);
                    return true;
                case 260:
                    android.media.LoudnessCodecInfo loudnessCodecInfo3 = (android.media.LoudnessCodecInfo) parcel.readTypedObject(android.media.LoudnessCodecInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.PersistableBundle loudnessParams = getLoudnessParams(loudnessCodecInfo3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(loudnessParams, 1);
                    return true;
                case 261:
                    android.media.FadeManagerConfiguration fadeManagerConfiguration2 = (android.media.FadeManagerConfiguration) parcel.readTypedObject(android.media.FadeManagerConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    int fadeManagerConfigurationForFocusLoss = setFadeManagerConfigurationForFocusLoss(fadeManagerConfiguration2);
                    parcel2.writeNoException();
                    parcel2.writeInt(fadeManagerConfigurationForFocusLoss);
                    return true;
                case 262:
                    int clearFadeManagerConfigurationForFocusLoss = clearFadeManagerConfigurationForFocusLoss();
                    parcel2.writeNoException();
                    parcel2.writeInt(clearFadeManagerConfigurationForFocusLoss);
                    return true;
                case 263:
                    android.media.FadeManagerConfiguration fadeManagerConfigurationForFocusLoss2 = getFadeManagerConfigurationForFocusLoss();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fadeManagerConfigurationForFocusLoss2, 1);
                    return true;
                case 264:
                    android.media.AudioAttributes audioAttributes14 = (android.media.AudioAttributes) parcel.readTypedObject(android.media.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean shouldNotificationSoundPlay = shouldNotificationSoundPlay(audioAttributes14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldNotificationSoundPlay);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IAudioService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IAudioService.Stub.DESCRIPTOR;
            }

            @Override // android.media.IAudioService
            public int trackPlayer(android.media.PlayerBase.PlayerIdCard playerIdCard) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(playerIdCard, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerAttributes(int i, android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerEvent(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void releasePlayer(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int trackRecorder(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recorderEvent(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void releaseRecorder(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerSessionId(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void portEvent(int i, int i2, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolumeWithAttribution(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolumeWithAttribution(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDeviceVolume(android.media.VolumeInfo volumeInfo, android.media.AudioDeviceAttributes audioDeviceAttributes, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(volumeInfo, 0);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.media.VolumeInfo getDeviceVolume(android.media.VolumeInfo volumeInfo, android.media.AudioDeviceAttributes audioDeviceAttributes, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(volumeInfo, 0);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.VolumeInfo) obtain2.readTypedObject(android.media.VolumeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void handleVolumeKey(android.view.KeyEvent keyEvent, boolean z, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamMute(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceRemoteSubmixFullVolume(boolean z, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMasterMute() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMasterMute(boolean z, int i, java.lang.String str, int i2, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamVolume(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamMinVolume(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamMaxVolume(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.audiopolicy.AudioVolumeGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeGroupVolumeIndex(int i, int i2, int i3, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupVolumeIndex(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupMaxVolumeIndex(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeGroupMinVolumeIndex(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getLastAudibleVolumeForVolumeGroup(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeGroupMuted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustVolumeGroupVolume(int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getLastAudibleStreamVolume(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSupportedSystemUsages(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getSupportedSystemUsages() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.audiopolicy.AudioProductStrategy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMicrophoneMuted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isUltrasoundSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHotwordStreamSupported(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicrophoneMute(boolean z, java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicrophoneMuteFromSwitch(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingerModeExternal(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingerModeInternal(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRingerModeExternal() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRingerModeInternal() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isValidRingerMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVibrateSetting(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVibrateSetting(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldVibrate(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMode(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playSoundEffect(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playSoundEffectVolume(int i, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(52, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean loadSoundEffects() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unloadSoundEffects() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void reloadAudioSettings() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.Map getSurroundFormats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List getReportedSurroundFormats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setSurroundFormatEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSurroundFormatEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setEncodedSurroundMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getEncodedSurroundMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpeakerphoneOn(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpeakerphoneOn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothScoOn(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setA2dpSuspended(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setLeAudioSuspended(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothScoOn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothA2dpOn(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothA2dpOn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int requestAudioFocus(android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iAudioFocusDispatcher);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    obtain.writeInt(i3);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int abandonAudioFocus(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioFocusDispatcher);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioFocusClient(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getCurrentAudioFocus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startBluetoothSco(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startBluetoothScoVirtualCall(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void stopBluetoothSco(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceVolumeControlStream(int i, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingtonePlayer(android.media.IRingtonePlayer iRingtonePlayer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRingtonePlayer);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.media.IRingtonePlayer getRingtonePlayer() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.IRingtonePlayer.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getUiSoundsStreamType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List getIndependentStreamTypes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamTypeAlias(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeControlUsingVolumeGroups() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStreamAliasingDispatcher(android.media.IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStreamAliasingDispatcher);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setNotifAliasRingForTest(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setWiredDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.media.AudioRoutesInfo startWatchingRoutes(android.media.IAudioRoutesObserver iAudioRoutesObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioRoutesObserver);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioRoutesInfo) obtain2.readTypedObject(android.media.AudioRoutesInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCameraSoundForced() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeController(android.media.IVolumeController iVolumeController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVolumeController);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.media.IVolumeController getVolumeController() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.IVolumeController.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void notifyVolumeControllerVisible(android.media.IVolumeController iVolumeController, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVolumeController);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamAffectedByRingerMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamAffectedByMute(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void disableSafeMediaVolume(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void lowerVolumeToRs1(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(95, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public float getOutputRs2UpperBound() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setOutputRs2UpperBound(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(97, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public float getCsd() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setCsd(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(99, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceUseFrameworkMel(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(100, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceComputeCsdOnAllDevices(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(101, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdAsAFeatureAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCsdAsAFeatureEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setCsdAsAFeatureEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(105, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothAudioDeviceCategory_legacy(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(106, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getBluetoothAudioDeviceCategory_legacy(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setBluetoothAudioDeviceCategory(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getBluetoothAudioDeviceCategory(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothAudioDeviceCategoryFixed(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setHdmiSystemAudioSupported(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHdmiSystemAudioSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.lang.String registerAudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, android.media.projection.IMediaProjection iMediaProjection) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioPolicyConfig, 0);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeStrongInterface(iMediaProjection);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioPolicyAsync(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(114, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.audiopolicy.AudioMix.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioPolicy(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int addMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioPolicyConfig, 0);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeMixForPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioPolicyConfig, 0);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int updateMixingRulesForPolicy(android.media.audiopolicy.AudioMix[] audioMixArr, android.media.audiopolicy.AudioMixingRule[] audioMixingRuleArr, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(audioMixArr, 0);
                    obtain.writeTypedArray(audioMixingRuleArr, 0);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setFocusPropertiesForPolicy(int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumePolicy(android.media.VolumePolicy volumePolicy) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(volumePolicy, 0);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasRegisteredDynamicPolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecordingConfigDispatcher);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRecordingConfigDispatcher);
                    this.mRemote.transact(124, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioRecordingConfiguration> getActiveRecordingConfigurations() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioRecordingConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPlaybackConfigDispatcher);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterPlaybackCallback(android.media.IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPlaybackConfigDispatcher);
                    this.mRemote.transact(127, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioPlaybackConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getFocusRampTimeMs(int i, android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int dispatchFocusChange(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int dispatchFocusChangeWithFade(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, java.util.List<android.media.AudioFocusInfo> list, android.media.FadeManagerConfiguration fadeManagerConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(fadeManagerConfiguration, 0);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerHasOpPlayAudio(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(132, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void handleBluetoothActiveDeviceChanged(android.bluetooth.BluetoothDevice bluetoothDevice, android.bluetooth.BluetoothDevice bluetoothDevice2, android.media.BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bluetoothDevice, 0);
                    obtain.writeTypedObject(bluetoothDevice2, 0);
                    obtain.writeTypedObject(bluetoothProfileConnectionInfo, 0);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setFocusRequestResultFromExtPolicy(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(134, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerAudioServerStateDispatcher(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioServerStateDispatcher);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioServerStateDispatcher(android.media.IAudioServerStateDispatcher iAudioServerStateDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioServerStateDispatcher);
                    this.mRemote.transact(136, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isAudioServerRunning() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setUidDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeUidDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setUserIdDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeUserIdDeviceAffinity(android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasHapticChannels(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCallScreeningModeSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredDevicesForStrategy(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removePreferredDevicesForStrategy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForStrategy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioDeviceAttributes> getNonDefaultDevicesForStrategy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioDeviceAttributes> getDevicesForAttributes(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioDeviceAttributes> getDevicesForAttributesUnprotected(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addOnDevicesForAttributesChangedListener(android.media.AudioAttributes audioAttributes, android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeStrongInterface(iDevicesForAttributesCallback);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeOnDevicesForAttributesChangedListener(android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDevicesForAttributesCallback);
                    this.mRemote.transact(153, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setAllowedCapturePolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getAllowedCapturePolicy() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStrategyPreferredDevicesDispatcher(android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStrategyPreferredDevicesDispatcher);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterStrategyPreferredDevicesDispatcher(android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStrategyPreferredDevicesDispatcher);
                    this.mRemote.transact(157, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerStrategyNonDefaultDevicesDispatcher(android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStrategyNonDefaultDevicesDispatcher);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterStrategyNonDefaultDevicesDispatcher(android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStrategyNonDefaultDevicesDispatcher);
                    this.mRemote.transact(159, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRttEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(160, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMultiAudioFocusEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(163, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredDevicesForCapturePreset(int i, java.util.List<android.media.AudioDeviceAttributes> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearPreferredDevicesForCapturePreset(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerCapturePresetDevicesRoleDispatcher(android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCapturePresetDevicesRoleDispatcher);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterCapturePresetDevicesRoleDispatcher(android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCapturePresetDevicesRoleDispatcher);
                    this.mRemote.transact(168, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i6);
                    this.mRemote.transact(169, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i6);
                    this.mRemote.transact(170, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, android.os.UserHandle userHandle, int i6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i6);
                    this.mRemote.transact(171, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustVolume(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(172, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustSuggestedStreamVolume(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(173, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMusicActive(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDeviceMaskForStream(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getAvailableCommunicationDeviceIds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setCommunicationDevice(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getCommunicationDevice() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerCommunicationDeviceDispatcher(android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCommunicationDeviceDispatcher);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterCommunicationDeviceDispatcher(android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCommunicationDeviceDispatcher);
                    this.mRemote.transact(180, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean areNavigationRepeatSoundEffectsEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setNavigationRepeatSoundEffectsEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(182, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHomeSoundEffectEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setHomeSoundEffectEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(184, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean setAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getMaxAdditionalOutputDeviceDelay(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int requestAudioFocusForTest(android.media.AudioAttributes audioAttributes, int i, android.os.IBinder iBinder, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, java.lang.String str2, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iAudioFocusDispatcher);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int abandonAudioFocusForTest(android.media.IAudioFocusDispatcher iAudioFocusDispatcher, java.lang.String str, android.media.AudioAttributes audioAttributes, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioFocusDispatcher);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFadeOutDurationOnFocusLossMillis(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List getFocusDuckedUidsForTest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFocusFadeOutDurationForTest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public long getFocusUnmuteDelayAfterFadeOutForTest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean enterAudioFocusFreezeForTest(android.os.IBinder iBinder, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean exitAudioFocusFreezeForTest(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerModeDispatcher(android.media.IAudioModeDispatcher iAudioModeDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioModeDispatcher);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterModeDispatcher(android.media.IAudioModeDispatcher iAudioModeDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioModeDispatcher);
                    this.mRemote.transact(197, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getSpatializerImmersiveAudioLevel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpatializerAvailableForDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasHeadTracker(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setHeadTrackerEnabled(boolean z, android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHeadTrackerEnabled(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHeadTrackerAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerHeadTrackerAvailableCallback(android.media.ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerHeadTrackerAvailableCallback);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean canBeSpatialized(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioFormat, 0);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerCallback(android.media.ISpatializerCallback iSpatializerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerCallback);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerCallback(android.media.ISpatializerCallback iSpatializerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerCallback);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerHeadTrackingCallback(android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerHeadTrackingModeCallback);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerHeadTrackingCallback(android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerHeadTrackingModeCallback);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerHeadToSoundstagePoseCallback(android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerHeadToSoundStagePoseCallback);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterHeadToSoundstagePoseCallback(android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerHeadToSoundStagePoseCallback);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioDeviceAttributes> getSpatializerCompatibleAudioDevices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioDeviceAttributes.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addSpatializerCompatibleAudioDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeSpatializerCompatibleAudioDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setDesiredHeadTrackingMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getDesiredHeadTrackingMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getSupportedHeadTrackingModes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getActualHeadTrackingMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(221, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerGlobalTransform(float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(222, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recenterHeadTracker() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(223, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpatializerParameter(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void getSpatializerParameter(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readByteArray(bArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getSpatializerOutput() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerSpatializerOutputCallback(android.media.ISpatializerOutputCallback iSpatializerOutputCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerOutputCallback);
                    this.mRemote.transact(227, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterSpatializerOutputCallback(android.media.ISpatializerOutputCallback iSpatializerOutputCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSpatializerOutputCallback);
                    this.mRemote.transact(228, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isVolumeFixed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.media.VolumeInfo getDefaultVolumeInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.VolumeInfo) obtain2.readTypedObject(android.media.VolumeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isPstnCallAudioInterceptable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void muteAwaitConnection(int[] iArr, android.media.AudioDeviceAttributes audioDeviceAttributes, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(232, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void cancelMuteAwaitConnection(android.media.AudioDeviceAttributes audioDeviceAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    this.mRemote.transact(233, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.media.AudioDeviceAttributes getMutingExpectedDevice() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioDeviceAttributes) obtain2.readTypedObject(android.media.AudioDeviceAttributes.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerMuteAwaitConnectionDispatcher(android.media.IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMuteAwaitConnectionCallback);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(235, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setTestDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerDeviceVolumeBehaviorDispatcher(boolean z, android.media.IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDeviceVolumeBehaviorDispatcher);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public java.util.List<android.media.AudioFocusInfo> getFocusStack() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(238, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioFocusInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean sendFocusLoss(android.media.AudioFocusInfo audioFocusInfo, android.media.audiopolicy.IAudioPolicyCallback iAudioPolicyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeStrongInterface(iAudioPolicyCallback);
                    this.mRemote.transact(239, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addAssistantServicesUids(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(240, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeAssistantServicesUids(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(241, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setActiveAssistantServiceUids(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(242, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getAssistantServicesUids() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(243, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int[] getActiveAssistantServiceUids() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(244, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, android.media.IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, java.lang.String str, android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, boolean z2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iAudioDeviceVolumeDispatcher);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioDeviceAttributes, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    this.mRemote.transact(245, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.media.AudioHalVersionInfo getHalVersion() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(246, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioHalVersionInfo) obtain2.readTypedObject(android.media.AudioHalVersionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i, android.media.AudioMixerAttributes audioMixerAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioMixerAttributes, 0);
                    this.mRemote.transact(247, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(248, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPreferredMixerAttributesDispatcher(android.media.IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPreferredMixerAttributesDispatcher);
                    this.mRemote.transact(249, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterPreferredMixerAttributesDispatcher(android.media.IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iPreferredMixerAttributesDispatcher);
                    this.mRemote.transact(250, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean supportsBluetoothVariableLatency() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(251, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothVariableLatencyEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(252, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothVariableLatencyEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(253, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iLoudnessCodecUpdatesDispatcher);
                    this.mRemote.transact(254, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iLoudnessCodecUpdatesDispatcher);
                    this.mRemote.transact(255, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startLoudnessCodecUpdates(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(256, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void stopLoudnessCodecUpdates(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(257, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void addLoudnessCodecInfo(int i, int i2, android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(loudnessCodecInfo, 0);
                    this.mRemote.transact(258, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void removeLoudnessCodecInfo(int i, android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(loudnessCodecInfo, 0);
                    this.mRemote.transact(259, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.os.PersistableBundle getLoudnessParams(android.media.LoudnessCodecInfo loudnessCodecInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(loudnessCodecInfo, 0);
                    this.mRemote.transact(260, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.PersistableBundle) obtain2.readTypedObject(android.os.PersistableBundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setFadeManagerConfigurationForFocusLoss(android.media.FadeManagerConfiguration fadeManagerConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fadeManagerConfiguration, 0);
                    this.mRemote.transact(261, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int clearFadeManagerConfigurationForFocusLoss() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(262, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public android.media.FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(263, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.FadeManagerConfiguration) obtain2.readTypedObject(android.media.FadeManagerConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldNotificationSoundPlay(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(264, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setDeviceVolume_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_setDeviceVolume, getCallingPid(), getCallingUid());
        }

        protected void getDeviceVolume_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getDeviceVolume, getCallingPid(), getCallingUid());
        }

        protected void setMasterMute_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getAudioVolumeGroups_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setVolumeGroupVolumeIndex_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_setVolumeGroupVolumeIndex, getCallingPid(), getCallingUid());
        }

        protected void getVolumeGroupVolumeIndex_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getVolumeGroupVolumeIndex, getCallingPid(), getCallingUid());
        }

        protected void getVolumeGroupMaxVolumeIndex_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getVolumeGroupMaxVolumeIndex, getCallingPid(), getCallingUid());
        }

        protected void getVolumeGroupMinVolumeIndex_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getVolumeGroupMinVolumeIndex, getCallingPid(), getCallingUid());
        }

        protected void getLastAudibleVolumeForVolumeGroup_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void getLastAudibleStreamVolume_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void setSupportedSystemUsages_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getSupportedSystemUsages_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getAudioProductStrategies_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void isUltrasoundSupported_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.ACCESS_ULTRASOUND, getCallingPid(), getCallingUid());
        }

        protected void isHotwordStreamSupported_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CAPTURE_AUDIO_HOTWORD, getCallingPid(), getCallingUid());
        }

        protected void setEncodedSurroundMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setA2dpSuspended_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        protected void setLeAudioSuspended_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        protected void setRingtonePlayer_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.REMOTE_AUDIO_PLAYBACK, getCallingPid(), getCallingUid());
        }

        protected void getIndependentStreamTypes_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getStreamTypeAlias_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isVolumeControlUsingVolumeGroups_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void registerStreamAliasingDispatcher_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setNotifAliasRingForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setWiredDeviceConnectionState_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getOutputRs2UpperBound_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setOutputRs2UpperBound_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getCsd_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setCsd_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void forceUseFrameworkMel_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void forceComputeCsdOnAllDevices_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isCsdEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isCsdAsAFeatureAvailable_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isCsdAsAFeatureEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setCsdAsAFeatureEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setBluetoothAudioDeviceCategory_legacy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getBluetoothAudioDeviceCategory_legacy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void setBluetoothAudioDeviceCategory_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getBluetoothAudioDeviceCategory_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isBluetoothAudioDeviceCategoryFixed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void updateMixingRulesForPolicy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void dispatchFocusChangeWithFade_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void handleBluetoothActiveDeviceChanged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.BLUETOOTH_STACK, getCallingPid(), getCallingUid());
        }

        protected void setPreferredDevicesForStrategy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void removePreferredDevicesForStrategy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getPreferredDevicesForStrategy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setDeviceAsNonDefaultForStrategy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void removeDeviceAsNonDefaultForStrategy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getNonDefaultDevicesForStrategy_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setDeviceVolumeBehavior_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_setDeviceVolumeBehavior, getCallingPid(), getCallingUid());
        }

        protected void getDeviceVolumeBehavior_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_getDeviceVolumeBehavior, getCallingPid(), getCallingUid());
        }

        protected void setMultiAudioFocusEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void clearPreferredDevicesForCapturePreset_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getPreferredDevicesForCapturePreset_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getFocusDuckedUidsForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void getFocusFadeOutDurationForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void getFocusUnmuteDelayAfterFadeOutForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        protected void enterAudioFocusFreezeForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void exitAudioFocusFreezeForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void isSpatializerAvailableForDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void hasHeadTracker_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setHeadTrackerEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void isHeadTrackerEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setSpatializerEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void registerSpatializerHeadTrackingCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void unregisterSpatializerHeadTrackingCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void registerHeadToSoundstagePoseCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void unregisterHeadToSoundstagePoseCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getSpatializerCompatibleAudioDevices_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void addSpatializerCompatibleAudioDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void removeSpatializerCompatibleAudioDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setDesiredHeadTrackingMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getDesiredHeadTrackingMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getSupportedHeadTrackingModes_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getActualHeadTrackingMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setSpatializerGlobalTransform_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void recenterHeadTracker_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void setSpatializerParameter_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getSpatializerParameter_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void getSpatializerOutput_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void registerSpatializerOutputCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void unregisterSpatializerOutputCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_DEFAULT_AUDIO_EFFECTS, getCallingPid(), getCallingUid());
        }

        protected void isPstnCallAudioInterceptable_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CALL_AUDIO_INTERCEPTION, getCallingPid(), getCallingUid());
        }

        protected void getMutingExpectedDevice_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void registerMuteAwaitConnectionDispatcher_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getFocusStack_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void addAssistantServicesUids_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void removeAssistantServicesUids_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setActiveAssistantServiceUids_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getAssistantServicesUids_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void getActiveAssistantServiceUids_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void supportsBluetoothVariableLatency_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setBluetoothVariableLatencyEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void isBluetoothVariableLatencyEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING, getCallingPid(), getCallingUid());
        }

        protected void setFadeManagerConfigurationForFocusLoss_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void clearFadeManagerConfigurationForFocusLoss_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void getFadeManagerConfigurationForFocusLoss_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED, getCallingPid(), getCallingUid());
        }

        protected void shouldNotificationSoundPlay_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.QUERY_AUDIO_STATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 263;
        }
    }
}
