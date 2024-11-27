package android.media;

/* loaded from: classes2.dex */
public interface IAudioPolicyService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IAudioPolicyService";

    android.media.SoundTriggerSession acquireSoundTriggerSession() throws android.os.RemoteException;

    void addDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException;

    int addSourceDefaultEffect(android.media.audio.common.AudioUuid audioUuid, java.lang.String str, android.media.audio.common.AudioUuid audioUuid2, int i, int i2) throws android.os.RemoteException;

    int addStreamDefaultEffect(android.media.audio.common.AudioUuid audioUuid, java.lang.String str, android.media.audio.common.AudioUuid audioUuid2, int i, int i2) throws android.os.RemoteException;

    boolean canBeSpatialized(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioConfig audioConfig, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException;

    void clearDevicesRoleForCapturePreset(int i, int i2) throws android.os.RemoteException;

    void clearDevicesRoleForStrategy(int i, int i2) throws android.os.RemoteException;

    void clearPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2) throws android.os.RemoteException;

    int createAudioPatch(android.media.AudioPatchFw audioPatchFw, int i) throws android.os.RemoteException;

    android.media.AudioPortFw getAudioPort(int i) throws android.os.RemoteException;

    int getDeviceConnectionState(android.media.audio.common.AudioDevice audioDevice) throws android.os.RemoteException;

    android.media.audio.common.AudioDevice[] getDevicesForAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException;

    android.media.audio.common.AudioDevice[] getDevicesForRoleAndCapturePreset(int i, int i2) throws android.os.RemoteException;

    android.media.audio.common.AudioDevice[] getDevicesForRoleAndStrategy(int i, int i2) throws android.os.RemoteException;

    int getDirectPlaybackSupport(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioConfig audioConfig) throws android.os.RemoteException;

    android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException;

    int getForceUse(int i) throws android.os.RemoteException;

    android.media.audio.common.AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException;

    android.media.GetInputForAttrResponse getInputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, int i3, android.content.AttributionSourceState attributionSourceState, android.media.audio.common.AudioConfigBase audioConfigBase, int i4, int i5) throws android.os.RemoteException;

    boolean getMasterMono() throws android.os.RemoteException;

    int getMaxVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException;

    int getMinVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException;

    int getOffloadSupport(android.media.audio.common.AudioOffloadInfo audioOffloadInfo) throws android.os.RemoteException;

    int getOutput(int i) throws android.os.RemoteException;

    android.media.GetOutputForAttrResponse getOutputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, android.content.AttributionSourceState attributionSourceState, android.media.audio.common.AudioConfig audioConfig, int i2, int i3) throws android.os.RemoteException;

    int getOutputForEffect(android.media.EffectDescriptor effectDescriptor) throws android.os.RemoteException;

    int getPhoneState() throws android.os.RemoteException;

    android.media.AudioMixerAttributesInternal getPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i) throws android.os.RemoteException;

    int getProductStrategyFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException;

    java.util.List<android.media.AudioMix> getRegisteredPolicyMixes() throws android.os.RemoteException;

    void getReportedSurroundFormats(android.media.audio.common.Int r1, android.media.audio.common.AudioFormatDescription[] audioFormatDescriptionArr) throws android.os.RemoteException;

    android.media.GetSpatializerResponse getSpatializer(android.media.INativeSpatializerCallback iNativeSpatializerCallback) throws android.os.RemoteException;

    int getStrategyForStream(int i) throws android.os.RemoteException;

    float getStreamVolumeDB(int i, int i2, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException;

    int getStreamVolumeIndex(int i, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException;

    android.media.AudioMixerAttributesInternal[] getSupportedMixerAttributes(int i) throws android.os.RemoteException;

    void getSurroundFormats(android.media.audio.common.Int r1, android.media.audio.common.AudioFormatDescription[] audioFormatDescriptionArr, boolean[] zArr) throws android.os.RemoteException;

    int getVolumeGroupFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException;

    int getVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException;

    void handleDeviceConfigChange(android.media.audio.common.AudioDevice audioDevice, java.lang.String str, android.media.audio.common.AudioFormatDescription audioFormatDescription) throws android.os.RemoteException;

    void initStreamVolume(int i, int i2, int i3) throws android.os.RemoteException;

    boolean isCallScreenModeSupported() throws android.os.RemoteException;

    boolean isDirectOutputSupported(android.media.audio.common.AudioConfigBase audioConfigBase, android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException;

    boolean isHapticPlaybackSupported() throws android.os.RemoteException;

    boolean isHotwordStreamSupported(boolean z) throws android.os.RemoteException;

    boolean isSourceActive(int i) throws android.os.RemoteException;

    boolean isStreamActive(int i, int i2) throws android.os.RemoteException;

    boolean isStreamActiveRemotely(int i, int i2) throws android.os.RemoteException;

    boolean isUltrasoundSupported() throws android.os.RemoteException;

    int listAudioPatches(android.media.audio.common.Int r1, android.media.AudioPatchFw[] audioPatchFwArr) throws android.os.RemoteException;

    int listAudioPorts(int i, int i2, android.media.audio.common.Int r3, android.media.AudioPortFw[] audioPortFwArr) throws android.os.RemoteException;

    android.media.AudioProductStrategy[] listAudioProductStrategies() throws android.os.RemoteException;

    android.media.AudioVolumeGroup[] listAudioVolumeGroups() throws android.os.RemoteException;

    android.media.AudioPortFw[] listDeclaredDevicePorts(int i) throws android.os.RemoteException;

    void moveEffectsToIo(int[] iArr, int i) throws android.os.RemoteException;

    void onNewAudioModulesAvailable() throws android.os.RemoteException;

    android.media.EffectDescriptor[] queryDefaultPreProcessing(int i, android.media.audio.common.Int r2) throws android.os.RemoteException;

    void registerClient(android.media.IAudioPolicyServiceClient iAudioPolicyServiceClient) throws android.os.RemoteException;

    void registerEffect(android.media.EffectDescriptor effectDescriptor, int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void registerPolicyMixes(android.media.AudioMix[] audioMixArr, boolean z) throws android.os.RemoteException;

    boolean registerSoundTriggerCaptureStateListener(android.media.ICaptureStateListener iCaptureStateListener) throws android.os.RemoteException;

    void releaseAudioPatch(int i) throws android.os.RemoteException;

    void releaseInput(int i) throws android.os.RemoteException;

    void releaseOutput(int i) throws android.os.RemoteException;

    void releaseSoundTriggerSession(int i) throws android.os.RemoteException;

    void removeDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException;

    void removeDevicesRoleForStrategy(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException;

    void removeSourceDefaultEffect(int i) throws android.os.RemoteException;

    void removeStreamDefaultEffect(int i) throws android.os.RemoteException;

    void removeUidDeviceAffinities(int i) throws android.os.RemoteException;

    void removeUserIdDeviceAffinities(int i) throws android.os.RemoteException;

    void setA11yServicesUids(int[] iArr) throws android.os.RemoteException;

    void setActiveAssistantServicesUids(int[] iArr) throws android.os.RemoteException;

    void setAllowedCapturePolicy(int i, int i2) throws android.os.RemoteException;

    void setAssistantServicesUids(int[] iArr) throws android.os.RemoteException;

    void setAudioPortCallbacksEnabled(boolean z) throws android.os.RemoteException;

    void setAudioPortConfig(android.media.AudioPortConfigFw audioPortConfigFw) throws android.os.RemoteException;

    void setAudioVolumeGroupCallbacksEnabled(boolean z) throws android.os.RemoteException;

    void setCurrentImeUid(int i) throws android.os.RemoteException;

    void setDeviceConnectionState(int i, android.media.audio.common.AudioPort audioPort, android.media.audio.common.AudioFormatDescription audioFormatDescription) throws android.os.RemoteException;

    void setDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException;

    void setDevicesRoleForStrategy(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException;

    void setEffectEnabled(int i, boolean z) throws android.os.RemoteException;

    void setForceUse(int i, int i2) throws android.os.RemoteException;

    void setMasterMono(boolean z) throws android.os.RemoteException;

    void setPhoneState(int i, int i2) throws android.os.RemoteException;

    void setPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, android.media.AudioMixerAttributesInternal audioMixerAttributesInternal) throws android.os.RemoteException;

    void setRttEnabled(boolean z) throws android.os.RemoteException;

    void setStreamVolumeIndex(int i, android.media.audio.common.AudioDeviceDescription audioDeviceDescription, int i2) throws android.os.RemoteException;

    void setSupportedSystemUsages(int[] iArr) throws android.os.RemoteException;

    void setSurroundFormatEnabled(android.media.audio.common.AudioFormatDescription audioFormatDescription, boolean z) throws android.os.RemoteException;

    void setUidDeviceAffinities(int i, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException;

    void setUserIdDeviceAffinities(int i, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException;

    void setVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioDeviceDescription audioDeviceDescription, int i) throws android.os.RemoteException;

    int startAudioSource(android.media.AudioPortConfigFw audioPortConfigFw, android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException;

    void startInput(int i) throws android.os.RemoteException;

    void startOutput(int i) throws android.os.RemoteException;

    void stopAudioSource(int i) throws android.os.RemoteException;

    void stopInput(int i) throws android.os.RemoteException;

    void stopOutput(int i) throws android.os.RemoteException;

    void unregisterEffect(int i) throws android.os.RemoteException;

    void updatePolicyMixes(android.media.AudioMixUpdate[] audioMixUpdateArr) throws android.os.RemoteException;

    public static class Default implements android.media.IAudioPolicyService {
        @Override // android.media.IAudioPolicyService
        public void onNewAudioModulesAvailable() throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setDeviceConnectionState(int i, android.media.audio.common.AudioPort audioPort, android.media.audio.common.AudioFormatDescription audioFormatDescription) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int getDeviceConnectionState(android.media.audio.common.AudioDevice audioDevice) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void handleDeviceConfigChange(android.media.audio.common.AudioDevice audioDevice, java.lang.String str, android.media.audio.common.AudioFormatDescription audioFormatDescription) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setPhoneState(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setForceUse(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int getForceUse(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int getOutput(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.GetOutputForAttrResponse getOutputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, android.content.AttributionSourceState attributionSourceState, android.media.audio.common.AudioConfig audioConfig, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void startOutput(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void stopOutput(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void releaseOutput(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public android.media.GetInputForAttrResponse getInputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, int i3, android.content.AttributionSourceState attributionSourceState, android.media.audio.common.AudioConfigBase audioConfigBase, int i4, int i5) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void startInput(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void stopInput(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void releaseInput(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void initStreamVolume(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setStreamVolumeIndex(int i, android.media.audio.common.AudioDeviceDescription audioDeviceDescription, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int getStreamVolumeIndex(int i, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void setVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioDeviceDescription audioDeviceDescription, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int getVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int getMaxVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int getMinVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int getStrategyForStream(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.audio.common.AudioDevice[] getDevicesForAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getOutputForEffect(android.media.EffectDescriptor effectDescriptor) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void registerEffect(android.media.EffectDescriptor effectDescriptor, int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void unregisterEffect(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setEffectEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void moveEffectsToIo(int[] iArr, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public boolean isStreamActive(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isStreamActiveRemotely(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isSourceActive(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.EffectDescriptor[] queryDefaultPreProcessing(int i, android.media.audio.common.Int r2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int addSourceDefaultEffect(android.media.audio.common.AudioUuid audioUuid, java.lang.String str, android.media.audio.common.AudioUuid audioUuid2, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public int addStreamDefaultEffect(android.media.audio.common.AudioUuid audioUuid, java.lang.String str, android.media.audio.common.AudioUuid audioUuid2, int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void removeSourceDefaultEffect(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeStreamDefaultEffect(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setSupportedSystemUsages(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAllowedCapturePolicy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int getOffloadSupport(android.media.audio.common.AudioOffloadInfo audioOffloadInfo) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isDirectOutputSupported(android.media.audio.common.AudioConfigBase audioConfigBase, android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public int listAudioPorts(int i, int i2, android.media.audio.common.Int r3, android.media.AudioPortFw[] audioPortFwArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.AudioPortFw[] listDeclaredDevicePorts(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.AudioPortFw getAudioPort(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int createAudioPatch(android.media.AudioPatchFw audioPatchFw, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void releaseAudioPatch(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int listAudioPatches(android.media.audio.common.Int r1, android.media.AudioPatchFw[] audioPatchFwArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void setAudioPortConfig(android.media.AudioPortConfigFw audioPortConfigFw) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void registerClient(android.media.IAudioPolicyServiceClient iAudioPolicyServiceClient) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAudioPortCallbacksEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAudioVolumeGroupCallbacksEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public android.media.SoundTriggerSession acquireSoundTriggerSession() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void releaseSoundTriggerSession(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int getPhoneState() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void registerPolicyMixes(android.media.AudioMix[] audioMixArr, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public java.util.List<android.media.AudioMix> getRegisteredPolicyMixes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void updatePolicyMixes(android.media.AudioMixUpdate[] audioMixUpdateArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setUidDeviceAffinities(int i, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeUidDeviceAffinities(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setUserIdDeviceAffinities(int i, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeUserIdDeviceAffinities(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public int startAudioSource(android.media.AudioPortConfigFw audioPortConfigFw, android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void stopAudioSource(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setMasterMono(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public boolean getMasterMono() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public float getStreamVolumeDB(int i, int i2, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.media.IAudioPolicyService
        public void getSurroundFormats(android.media.audio.common.Int r1, android.media.audio.common.AudioFormatDescription[] audioFormatDescriptionArr, boolean[] zArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void getReportedSurroundFormats(android.media.audio.common.Int r1, android.media.audio.common.AudioFormatDescription[] audioFormatDescriptionArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public android.media.audio.common.AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void setSurroundFormatEnabled(android.media.audio.common.AudioFormatDescription audioFormatDescription, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setAssistantServicesUids(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setActiveAssistantServicesUids(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setA11yServicesUids(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void setCurrentImeUid(int i) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public boolean isHapticPlaybackSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isUltrasoundSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public boolean isHotwordStreamSupported(boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.AudioProductStrategy[] listAudioProductStrategies() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getProductStrategyFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.AudioVolumeGroup[] listAudioVolumeGroups() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public int getVolumeGroupFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public void setRttEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public boolean isCallScreenModeSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public void setDevicesRoleForStrategy(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeDevicesRoleForStrategy(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void clearDevicesRoleForStrategy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public android.media.audio.common.AudioDevice[] getDevicesForRoleAndStrategy(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void setDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void addDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void removeDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public void clearDevicesRoleForCapturePreset(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public android.media.audio.common.AudioDevice[] getDevicesForRoleAndCapturePreset(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public boolean registerSoundTriggerCaptureStateListener(android.media.ICaptureStateListener iCaptureStateListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.GetSpatializerResponse getSpatializer(android.media.INativeSpatializerCallback iNativeSpatializerCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public boolean canBeSpatialized(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioConfig audioConfig, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IAudioPolicyService
        public int getDirectPlaybackSupport(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioConfig audioConfig) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public android.media.AudioMixerAttributesInternal[] getSupportedMixerAttributes(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void setPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, android.media.AudioMixerAttributesInternal audioMixerAttributesInternal) throws android.os.RemoteException {
        }

        @Override // android.media.IAudioPolicyService
        public android.media.AudioMixerAttributesInternal getPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IAudioPolicyService
        public void clearPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IAudioPolicyService {
        static final int TRANSACTION_acquireSoundTriggerSession = 53;
        static final int TRANSACTION_addDevicesRoleForCapturePreset = 90;
        static final int TRANSACTION_addSourceDefaultEffect = 35;
        static final int TRANSACTION_addStreamDefaultEffect = 36;
        static final int TRANSACTION_canBeSpatialized = 96;
        static final int TRANSACTION_clearDevicesRoleForCapturePreset = 92;
        static final int TRANSACTION_clearDevicesRoleForStrategy = 87;
        static final int TRANSACTION_clearPreferredMixerAttributes = 102;
        static final int TRANSACTION_createAudioPatch = 46;
        static final int TRANSACTION_getAudioPort = 45;
        static final int TRANSACTION_getDeviceConnectionState = 3;
        static final int TRANSACTION_getDevicesForAttributes = 25;
        static final int TRANSACTION_getDevicesForRoleAndCapturePreset = 93;
        static final int TRANSACTION_getDevicesForRoleAndStrategy = 88;
        static final int TRANSACTION_getDirectPlaybackSupport = 97;
        static final int TRANSACTION_getDirectProfilesForAttributes = 98;
        static final int TRANSACTION_getForceUse = 7;
        static final int TRANSACTION_getHwOffloadFormatsSupportedForBluetoothMedia = 70;
        static final int TRANSACTION_getInputForAttr = 13;
        static final int TRANSACTION_getMasterMono = 66;
        static final int TRANSACTION_getMaxVolumeIndexForAttributes = 22;
        static final int TRANSACTION_getMinVolumeIndexForAttributes = 23;
        static final int TRANSACTION_getOffloadSupport = 41;
        static final int TRANSACTION_getOutput = 8;
        static final int TRANSACTION_getOutputForAttr = 9;
        static final int TRANSACTION_getOutputForEffect = 26;
        static final int TRANSACTION_getPhoneState = 55;
        static final int TRANSACTION_getPreferredMixerAttributes = 101;
        static final int TRANSACTION_getProductStrategyFromAudioAttributes = 80;
        static final int TRANSACTION_getRegisteredPolicyMixes = 57;
        static final int TRANSACTION_getReportedSurroundFormats = 69;
        static final int TRANSACTION_getSpatializer = 95;
        static final int TRANSACTION_getStrategyForStream = 24;
        static final int TRANSACTION_getStreamVolumeDB = 67;
        static final int TRANSACTION_getStreamVolumeIndex = 19;
        static final int TRANSACTION_getSupportedMixerAttributes = 99;
        static final int TRANSACTION_getSurroundFormats = 68;
        static final int TRANSACTION_getVolumeGroupFromAudioAttributes = 82;
        static final int TRANSACTION_getVolumeIndexForAttributes = 21;
        static final int TRANSACTION_handleDeviceConfigChange = 4;
        static final int TRANSACTION_initStreamVolume = 17;
        static final int TRANSACTION_isCallScreenModeSupported = 84;
        static final int TRANSACTION_isDirectOutputSupported = 42;
        static final int TRANSACTION_isHapticPlaybackSupported = 76;
        static final int TRANSACTION_isHotwordStreamSupported = 78;
        static final int TRANSACTION_isSourceActive = 33;
        static final int TRANSACTION_isStreamActive = 31;
        static final int TRANSACTION_isStreamActiveRemotely = 32;
        static final int TRANSACTION_isUltrasoundSupported = 77;
        static final int TRANSACTION_listAudioPatches = 48;
        static final int TRANSACTION_listAudioPorts = 43;
        static final int TRANSACTION_listAudioProductStrategies = 79;
        static final int TRANSACTION_listAudioVolumeGroups = 81;
        static final int TRANSACTION_listDeclaredDevicePorts = 44;
        static final int TRANSACTION_moveEffectsToIo = 30;
        static final int TRANSACTION_onNewAudioModulesAvailable = 1;
        static final int TRANSACTION_queryDefaultPreProcessing = 34;
        static final int TRANSACTION_registerClient = 50;
        static final int TRANSACTION_registerEffect = 27;
        static final int TRANSACTION_registerPolicyMixes = 56;
        static final int TRANSACTION_registerSoundTriggerCaptureStateListener = 94;
        static final int TRANSACTION_releaseAudioPatch = 47;
        static final int TRANSACTION_releaseInput = 16;
        static final int TRANSACTION_releaseOutput = 12;
        static final int TRANSACTION_releaseSoundTriggerSession = 54;
        static final int TRANSACTION_removeDevicesRoleForCapturePreset = 91;
        static final int TRANSACTION_removeDevicesRoleForStrategy = 86;
        static final int TRANSACTION_removeSourceDefaultEffect = 37;
        static final int TRANSACTION_removeStreamDefaultEffect = 38;
        static final int TRANSACTION_removeUidDeviceAffinities = 60;
        static final int TRANSACTION_removeUserIdDeviceAffinities = 62;
        static final int TRANSACTION_setA11yServicesUids = 74;
        static final int TRANSACTION_setActiveAssistantServicesUids = 73;
        static final int TRANSACTION_setAllowedCapturePolicy = 40;
        static final int TRANSACTION_setAssistantServicesUids = 72;
        static final int TRANSACTION_setAudioPortCallbacksEnabled = 51;
        static final int TRANSACTION_setAudioPortConfig = 49;
        static final int TRANSACTION_setAudioVolumeGroupCallbacksEnabled = 52;
        static final int TRANSACTION_setCurrentImeUid = 75;
        static final int TRANSACTION_setDeviceConnectionState = 2;
        static final int TRANSACTION_setDevicesRoleForCapturePreset = 89;
        static final int TRANSACTION_setDevicesRoleForStrategy = 85;
        static final int TRANSACTION_setEffectEnabled = 29;
        static final int TRANSACTION_setForceUse = 6;
        static final int TRANSACTION_setMasterMono = 65;
        static final int TRANSACTION_setPhoneState = 5;
        static final int TRANSACTION_setPreferredMixerAttributes = 100;
        static final int TRANSACTION_setRttEnabled = 83;
        static final int TRANSACTION_setStreamVolumeIndex = 18;
        static final int TRANSACTION_setSupportedSystemUsages = 39;
        static final int TRANSACTION_setSurroundFormatEnabled = 71;
        static final int TRANSACTION_setUidDeviceAffinities = 59;
        static final int TRANSACTION_setUserIdDeviceAffinities = 61;
        static final int TRANSACTION_setVolumeIndexForAttributes = 20;
        static final int TRANSACTION_startAudioSource = 63;
        static final int TRANSACTION_startInput = 14;
        static final int TRANSACTION_startOutput = 10;
        static final int TRANSACTION_stopAudioSource = 64;
        static final int TRANSACTION_stopInput = 15;
        static final int TRANSACTION_stopOutput = 11;
        static final int TRANSACTION_unregisterEffect = 28;
        static final int TRANSACTION_updatePolicyMixes = 58;

        public Stub() {
            attachInterface(this, android.media.IAudioPolicyService.DESCRIPTOR);
        }

        public static android.media.IAudioPolicyService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IAudioPolicyService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IAudioPolicyService)) {
                return (android.media.IAudioPolicyService) queryLocalInterface;
            }
            return new android.media.IAudioPolicyService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            android.media.audio.common.AudioFormatDescription[] audioFormatDescriptionArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.IAudioPolicyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IAudioPolicyService.DESCRIPTOR);
                return true;
            }
            android.media.AudioPortFw[] audioPortFwArr = null;
            android.media.audio.common.AudioFormatDescription[] audioFormatDescriptionArr2 = null;
            boolean[] zArr = null;
            android.media.AudioPatchFw[] audioPatchFwArr = null;
            switch (i) {
                case 1:
                    onNewAudioModulesAvailable();
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    android.media.audio.common.AudioPort audioPort = (android.media.audio.common.AudioPort) parcel.readTypedObject(android.media.audio.common.AudioPort.CREATOR);
                    android.media.audio.common.AudioFormatDescription audioFormatDescription = (android.media.audio.common.AudioFormatDescription) parcel.readTypedObject(android.media.audio.common.AudioFormatDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceConnectionState(readInt, audioPort, audioFormatDescription);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.media.audio.common.AudioDevice audioDevice = (android.media.audio.common.AudioDevice) parcel.readTypedObject(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    int deviceConnectionState = getDeviceConnectionState(audioDevice);
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceConnectionState);
                    return true;
                case 4:
                    android.media.audio.common.AudioDevice audioDevice2 = (android.media.audio.common.AudioDevice) parcel.readTypedObject(android.media.audio.common.AudioDevice.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.media.audio.common.AudioFormatDescription audioFormatDescription2 = (android.media.audio.common.AudioFormatDescription) parcel.readTypedObject(android.media.audio.common.AudioFormatDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleDeviceConfigChange(audioDevice2, readString, audioFormatDescription2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPhoneState(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForceUse(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int forceUse = getForceUse(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(forceUse);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int output = getOutput(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(output);
                    return true;
                case 9:
                    android.media.audio.common.AudioAttributes audioAttributes = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt8 = parcel.readInt();
                    android.content.AttributionSourceState attributionSourceState = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    android.media.audio.common.AudioConfig audioConfig = (android.media.audio.common.AudioConfig) parcel.readTypedObject(android.media.audio.common.AudioConfig.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.GetOutputForAttrResponse outputForAttr = getOutputForAttr(audioAttributes, readInt8, attributionSourceState, audioConfig, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(outputForAttr, 1);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startOutput(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopOutput(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseOutput(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.media.audio.common.AudioAttributes audioAttributes2 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    android.content.AttributionSourceState attributionSourceState2 = (android.content.AttributionSourceState) parcel.readTypedObject(android.content.AttributionSourceState.CREATOR);
                    android.media.audio.common.AudioConfigBase audioConfigBase = (android.media.audio.common.AudioConfigBase) parcel.readTypedObject(android.media.audio.common.AudioConfigBase.CREATOR);
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.GetInputForAttrResponse inputForAttr = getInputForAttr(audioAttributes2, readInt14, readInt15, readInt16, attributionSourceState2, audioConfigBase, readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputForAttr, 1);
                    return true;
                case 14:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startInput(readInt19);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopInput(readInt20);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseInput(readInt21);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initStreamVolume(readInt22, readInt23, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt25 = parcel.readInt();
                    android.media.audio.common.AudioDeviceDescription audioDeviceDescription = (android.media.audio.common.AudioDeviceDescription) parcel.readTypedObject(android.media.audio.common.AudioDeviceDescription.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setStreamVolumeIndex(readInt25, audioDeviceDescription, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt27 = parcel.readInt();
                    android.media.audio.common.AudioDeviceDescription audioDeviceDescription2 = (android.media.audio.common.AudioDeviceDescription) parcel.readTypedObject(android.media.audio.common.AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    int streamVolumeIndex = getStreamVolumeIndex(readInt27, audioDeviceDescription2);
                    parcel2.writeNoException();
                    parcel2.writeInt(streamVolumeIndex);
                    return true;
                case 20:
                    android.media.audio.common.AudioAttributes audioAttributes3 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    android.media.audio.common.AudioDeviceDescription audioDeviceDescription3 = (android.media.audio.common.AudioDeviceDescription) parcel.readTypedObject(android.media.audio.common.AudioDeviceDescription.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolumeIndexForAttributes(audioAttributes3, audioDeviceDescription3, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.media.audio.common.AudioAttributes audioAttributes4 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    android.media.audio.common.AudioDeviceDescription audioDeviceDescription4 = (android.media.audio.common.AudioDeviceDescription) parcel.readTypedObject(android.media.audio.common.AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    int volumeIndexForAttributes = getVolumeIndexForAttributes(audioAttributes4, audioDeviceDescription4);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeIndexForAttributes);
                    return true;
                case 22:
                    android.media.audio.common.AudioAttributes audioAttributes5 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int maxVolumeIndexForAttributes = getMaxVolumeIndexForAttributes(audioAttributes5);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxVolumeIndexForAttributes);
                    return true;
                case 23:
                    android.media.audio.common.AudioAttributes audioAttributes6 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int minVolumeIndexForAttributes = getMinVolumeIndexForAttributes(audioAttributes6);
                    parcel2.writeNoException();
                    parcel2.writeInt(minVolumeIndexForAttributes);
                    return true;
                case 24:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int strategyForStream = getStrategyForStream(readInt29);
                    parcel2.writeNoException();
                    parcel2.writeInt(strategyForStream);
                    return true;
                case 25:
                    android.media.audio.common.AudioAttributes audioAttributes7 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.media.audio.common.AudioDevice[] devicesForAttributes = getDevicesForAttributes(audioAttributes7, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForAttributes, 1);
                    return true;
                case 26:
                    android.media.EffectDescriptor effectDescriptor = (android.media.EffectDescriptor) parcel.readTypedObject(android.media.EffectDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    int outputForEffect = getOutputForEffect(effectDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeInt(outputForEffect);
                    return true;
                case 27:
                    android.media.EffectDescriptor effectDescriptor2 = (android.media.EffectDescriptor) parcel.readTypedObject(android.media.EffectDescriptor.CREATOR);
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerEffect(effectDescriptor2, readInt30, readInt31, readInt32, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterEffect(readInt34);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt35 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEffectEnabled(readInt35, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int[] createIntArray = parcel.createIntArray();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveEffectsToIo(createIntArray, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStreamActive = isStreamActive(readInt37, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStreamActive);
                    return true;
                case 32:
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isStreamActiveRemotely = isStreamActiveRemotely(readInt39, readInt40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isStreamActiveRemotely);
                    return true;
                case 33:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSourceActive = isSourceActive(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSourceActive);
                    return true;
                case 34:
                    int readInt42 = parcel.readInt();
                    android.media.audio.common.Int r15 = (android.media.audio.common.Int) parcel.readTypedObject(android.media.audio.common.Int.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.media.EffectDescriptor[] queryDefaultPreProcessing = queryDefaultPreProcessing(readInt42, r15);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(queryDefaultPreProcessing, 1);
                    parcel2.writeTypedObject(r15, 1);
                    return true;
                case 35:
                    android.media.audio.common.AudioUuid audioUuid = (android.media.audio.common.AudioUuid) parcel.readTypedObject(android.media.audio.common.AudioUuid.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    android.media.audio.common.AudioUuid audioUuid2 = (android.media.audio.common.AudioUuid) parcel.readTypedObject(android.media.audio.common.AudioUuid.CREATOR);
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addSourceDefaultEffect = addSourceDefaultEffect(audioUuid, readString2, audioUuid2, readInt43, readInt44);
                    parcel2.writeNoException();
                    parcel2.writeInt(addSourceDefaultEffect);
                    return true;
                case 36:
                    android.media.audio.common.AudioUuid audioUuid3 = (android.media.audio.common.AudioUuid) parcel.readTypedObject(android.media.audio.common.AudioUuid.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    android.media.audio.common.AudioUuid audioUuid4 = (android.media.audio.common.AudioUuid) parcel.readTypedObject(android.media.audio.common.AudioUuid.CREATOR);
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addStreamDefaultEffect = addStreamDefaultEffect(audioUuid3, readString3, audioUuid4, readInt45, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeInt(addStreamDefaultEffect);
                    return true;
                case 37:
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeSourceDefaultEffect(readInt47);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeStreamDefaultEffect(readInt48);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setSupportedSystemUsages(createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowedCapturePolicy(readInt49, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    android.media.audio.common.AudioOffloadInfo audioOffloadInfo = (android.media.audio.common.AudioOffloadInfo) parcel.readTypedObject(android.media.audio.common.AudioOffloadInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int offloadSupport = getOffloadSupport(audioOffloadInfo);
                    parcel2.writeNoException();
                    parcel2.writeInt(offloadSupport);
                    return true;
                case 42:
                    android.media.audio.common.AudioConfigBase audioConfigBase2 = (android.media.audio.common.AudioConfigBase) parcel.readTypedObject(android.media.audio.common.AudioConfigBase.CREATOR);
                    android.media.audio.common.AudioAttributes audioAttributes8 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDirectOutputSupported = isDirectOutputSupported(audioConfigBase2, audioAttributes8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDirectOutputSupported);
                    return true;
                case 43:
                    int readInt51 = parcel.readInt();
                    int readInt52 = parcel.readInt();
                    android.media.audio.common.Int r2 = (android.media.audio.common.Int) parcel.readTypedObject(android.media.audio.common.Int.CREATOR);
                    int readInt53 = parcel.readInt();
                    if (readInt53 >= 0) {
                        audioPortFwArr = new android.media.AudioPortFw[readInt53];
                    }
                    parcel.enforceNoDataAvail();
                    int listAudioPorts = listAudioPorts(readInt51, readInt52, r2, audioPortFwArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(listAudioPorts);
                    parcel2.writeTypedObject(r2, 1);
                    parcel2.writeTypedArray(audioPortFwArr, 1);
                    return true;
                case 44:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.AudioPortFw[] listDeclaredDevicePorts = listDeclaredDevicePorts(readInt54);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listDeclaredDevicePorts, 1);
                    return true;
                case 45:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.AudioPortFw audioPort2 = getAudioPort(readInt55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(audioPort2, 1);
                    return true;
                case 46:
                    android.media.AudioPatchFw audioPatchFw = (android.media.AudioPatchFw) parcel.readTypedObject(android.media.AudioPatchFw.CREATOR);
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int createAudioPatch = createAudioPatch(audioPatchFw, readInt56);
                    parcel2.writeNoException();
                    parcel2.writeInt(createAudioPatch);
                    return true;
                case 47:
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseAudioPatch(readInt57);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    android.media.audio.common.Int r12 = (android.media.audio.common.Int) parcel.readTypedObject(android.media.audio.common.Int.CREATOR);
                    int readInt58 = parcel.readInt();
                    if (readInt58 >= 0) {
                        audioPatchFwArr = new android.media.AudioPatchFw[readInt58];
                    }
                    parcel.enforceNoDataAvail();
                    int listAudioPatches = listAudioPatches(r12, audioPatchFwArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(listAudioPatches);
                    parcel2.writeTypedObject(r12, 1);
                    parcel2.writeTypedArray(audioPatchFwArr, 1);
                    return true;
                case 49:
                    android.media.AudioPortConfigFw audioPortConfigFw = (android.media.AudioPortConfigFw) parcel.readTypedObject(android.media.AudioPortConfigFw.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAudioPortConfig(audioPortConfigFw);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    android.media.IAudioPolicyServiceClient asInterface = android.media.IAudioPolicyServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerClient(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAudioPortCallbacksEnabled(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAudioVolumeGroupCallbacksEnabled(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    android.media.SoundTriggerSession acquireSoundTriggerSession = acquireSoundTriggerSession();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(acquireSoundTriggerSession, 1);
                    return true;
                case 54:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSoundTriggerSession(readInt59);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int phoneState = getPhoneState();
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneState);
                    return true;
                case 56:
                    android.media.AudioMix[] audioMixArr = (android.media.AudioMix[]) parcel.createTypedArray(android.media.AudioMix.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerPolicyMixes(audioMixArr, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    java.util.List<android.media.AudioMix> registeredPolicyMixes = getRegisteredPolicyMixes();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(registeredPolicyMixes, 1);
                    return true;
                case 58:
                    android.media.AudioMixUpdate[] audioMixUpdateArr = (android.media.AudioMixUpdate[]) parcel.createTypedArray(android.media.AudioMixUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    updatePolicyMixes(audioMixUpdateArr);
                    parcel2.writeNoException();
                    return true;
                case 59:
                    int readInt60 = parcel.readInt();
                    android.media.audio.common.AudioDevice[] audioDeviceArr = (android.media.audio.common.AudioDevice[]) parcel.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUidDeviceAffinities(readInt60, audioDeviceArr);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUidDeviceAffinities(readInt61);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt62 = parcel.readInt();
                    android.media.audio.common.AudioDevice[] audioDeviceArr2 = (android.media.audio.common.AudioDevice[]) parcel.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserIdDeviceAffinities(readInt62, audioDeviceArr2);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeUserIdDeviceAffinities(readInt63);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    android.media.AudioPortConfigFw audioPortConfigFw2 = (android.media.AudioPortConfigFw) parcel.readTypedObject(android.media.AudioPortConfigFw.CREATOR);
                    android.media.audio.common.AudioAttributes audioAttributes9 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    int startAudioSource = startAudioSource(audioPortConfigFw2, audioAttributes9);
                    parcel2.writeNoException();
                    parcel2.writeInt(startAudioSource);
                    return true;
                case 64:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAudioSource(readInt64);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterMono(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    boolean masterMono = getMasterMono();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterMono);
                    return true;
                case 67:
                    int readInt65 = parcel.readInt();
                    int readInt66 = parcel.readInt();
                    android.media.audio.common.AudioDeviceDescription audioDeviceDescription5 = (android.media.audio.common.AudioDeviceDescription) parcel.readTypedObject(android.media.audio.common.AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    float streamVolumeDB = getStreamVolumeDB(readInt65, readInt66, audioDeviceDescription5);
                    parcel2.writeNoException();
                    parcel2.writeFloat(streamVolumeDB);
                    return true;
                case 68:
                    android.media.audio.common.Int r122 = (android.media.audio.common.Int) parcel.readTypedObject(android.media.audio.common.Int.CREATOR);
                    int readInt67 = parcel.readInt();
                    if (readInt67 < 0) {
                        audioFormatDescriptionArr = null;
                    } else {
                        audioFormatDescriptionArr = new android.media.audio.common.AudioFormatDescription[readInt67];
                    }
                    int readInt68 = parcel.readInt();
                    if (readInt68 >= 0) {
                        zArr = new boolean[readInt68];
                    }
                    parcel.enforceNoDataAvail();
                    getSurroundFormats(r122, audioFormatDescriptionArr, zArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(r122, 1);
                    parcel2.writeTypedArray(audioFormatDescriptionArr, 1);
                    parcel2.writeBooleanArray(zArr);
                    return true;
                case 69:
                    android.media.audio.common.Int r123 = (android.media.audio.common.Int) parcel.readTypedObject(android.media.audio.common.Int.CREATOR);
                    int readInt69 = parcel.readInt();
                    if (readInt69 >= 0) {
                        audioFormatDescriptionArr2 = new android.media.audio.common.AudioFormatDescription[readInt69];
                    }
                    parcel.enforceNoDataAvail();
                    getReportedSurroundFormats(r123, audioFormatDescriptionArr2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(r123, 1);
                    parcel2.writeTypedArray(audioFormatDescriptionArr2, 1);
                    return true;
                case 70:
                    android.media.audio.common.AudioDeviceDescription audioDeviceDescription6 = (android.media.audio.common.AudioDeviceDescription) parcel.readTypedObject(android.media.audio.common.AudioDeviceDescription.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.media.audio.common.AudioFormatDescription[] hwOffloadFormatsSupportedForBluetoothMedia = getHwOffloadFormatsSupportedForBluetoothMedia(audioDeviceDescription6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(hwOffloadFormatsSupportedForBluetoothMedia, 1);
                    return true;
                case 71:
                    android.media.audio.common.AudioFormatDescription audioFormatDescription3 = (android.media.audio.common.AudioFormatDescription) parcel.readTypedObject(android.media.audio.common.AudioFormatDescription.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSurroundFormatEnabled(audioFormatDescription3, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setAssistantServicesUids(createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int[] createIntArray4 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setActiveAssistantServicesUids(createIntArray4);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    int[] createIntArray5 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setA11yServicesUids(createIntArray5);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCurrentImeUid(readInt70);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    boolean isHapticPlaybackSupported = isHapticPlaybackSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHapticPlaybackSupported);
                    return true;
                case 77:
                    boolean isUltrasoundSupported = isUltrasoundSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUltrasoundSupported);
                    return true;
                case 78:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isHotwordStreamSupported = isHotwordStreamSupported(readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHotwordStreamSupported);
                    return true;
                case 79:
                    android.media.AudioProductStrategy[] listAudioProductStrategies = listAudioProductStrategies();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAudioProductStrategies, 1);
                    return true;
                case 80:
                    android.media.audio.common.AudioAttributes audioAttributes10 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int productStrategyFromAudioAttributes = getProductStrategyFromAudioAttributes(audioAttributes10, readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeInt(productStrategyFromAudioAttributes);
                    return true;
                case 81:
                    android.media.AudioVolumeGroup[] listAudioVolumeGroups = listAudioVolumeGroups();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAudioVolumeGroups, 1);
                    return true;
                case 82:
                    android.media.audio.common.AudioAttributes audioAttributes11 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int volumeGroupFromAudioAttributes = getVolumeGroupFromAudioAttributes(audioAttributes11, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeInt(volumeGroupFromAudioAttributes);
                    return true;
                case 83:
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRttEnabled(readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    boolean isCallScreenModeSupported = isCallScreenModeSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallScreenModeSupported);
                    return true;
                case 85:
                    int readInt71 = parcel.readInt();
                    int readInt72 = parcel.readInt();
                    android.media.audio.common.AudioDevice[] audioDeviceArr3 = (android.media.audio.common.AudioDevice[]) parcel.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDevicesRoleForStrategy(readInt71, readInt72, audioDeviceArr3);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    int readInt73 = parcel.readInt();
                    int readInt74 = parcel.readInt();
                    android.media.audio.common.AudioDevice[] audioDeviceArr4 = (android.media.audio.common.AudioDevice[]) parcel.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicesRoleForStrategy(readInt73, readInt74, audioDeviceArr4);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    int readInt75 = parcel.readInt();
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDevicesRoleForStrategy(readInt75, readInt76);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int readInt77 = parcel.readInt();
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.audio.common.AudioDevice[] devicesForRoleAndStrategy = getDevicesForRoleAndStrategy(readInt77, readInt78);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForRoleAndStrategy, 1);
                    return true;
                case 89:
                    int readInt79 = parcel.readInt();
                    int readInt80 = parcel.readInt();
                    android.media.audio.common.AudioDevice[] audioDeviceArr5 = (android.media.audio.common.AudioDevice[]) parcel.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDevicesRoleForCapturePreset(readInt79, readInt80, audioDeviceArr5);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    int readInt81 = parcel.readInt();
                    int readInt82 = parcel.readInt();
                    android.media.audio.common.AudioDevice[] audioDeviceArr6 = (android.media.audio.common.AudioDevice[]) parcel.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    addDevicesRoleForCapturePreset(readInt81, readInt82, audioDeviceArr6);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    int readInt83 = parcel.readInt();
                    int readInt84 = parcel.readInt();
                    android.media.audio.common.AudioDevice[] audioDeviceArr7 = (android.media.audio.common.AudioDevice[]) parcel.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeDevicesRoleForCapturePreset(readInt83, readInt84, audioDeviceArr7);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int readInt85 = parcel.readInt();
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearDevicesRoleForCapturePreset(readInt85, readInt86);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    int readInt87 = parcel.readInt();
                    int readInt88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.audio.common.AudioDevice[] devicesForRoleAndCapturePreset = getDevicesForRoleAndCapturePreset(readInt87, readInt88);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(devicesForRoleAndCapturePreset, 1);
                    return true;
                case 94:
                    android.media.ICaptureStateListener asInterface2 = android.media.ICaptureStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerSoundTriggerCaptureStateListener = registerSoundTriggerCaptureStateListener(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerSoundTriggerCaptureStateListener);
                    return true;
                case 95:
                    android.media.INativeSpatializerCallback asInterface3 = android.media.INativeSpatializerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.media.GetSpatializerResponse spatializer = getSpatializer(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(spatializer, 1);
                    return true;
                case 96:
                    android.media.audio.common.AudioAttributes audioAttributes12 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    android.media.audio.common.AudioConfig audioConfig2 = (android.media.audio.common.AudioConfig) parcel.readTypedObject(android.media.audio.common.AudioConfig.CREATOR);
                    android.media.audio.common.AudioDevice[] audioDeviceArr8 = (android.media.audio.common.AudioDevice[]) parcel.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean canBeSpatialized = canBeSpatialized(audioAttributes12, audioConfig2, audioDeviceArr8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canBeSpatialized);
                    return true;
                case 97:
                    android.media.audio.common.AudioAttributes audioAttributes13 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    android.media.audio.common.AudioConfig audioConfig3 = (android.media.audio.common.AudioConfig) parcel.readTypedObject(android.media.audio.common.AudioConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    int directPlaybackSupport = getDirectPlaybackSupport(audioAttributes13, audioConfig3);
                    parcel2.writeNoException();
                    parcel2.writeInt(directPlaybackSupport);
                    return true;
                case 98:
                    android.media.audio.common.AudioAttributes audioAttributes14 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.media.audio.common.AudioProfile[] directProfilesForAttributes = getDirectProfilesForAttributes(audioAttributes14);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(directProfilesForAttributes, 1);
                    return true;
                case 99:
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.AudioMixerAttributesInternal[] supportedMixerAttributes = getSupportedMixerAttributes(readInt89);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(supportedMixerAttributes, 1);
                    return true;
                case 100:
                    android.media.audio.common.AudioAttributes audioAttributes15 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt90 = parcel.readInt();
                    int readInt91 = parcel.readInt();
                    android.media.AudioMixerAttributesInternal audioMixerAttributesInternal = (android.media.AudioMixerAttributesInternal) parcel.readTypedObject(android.media.AudioMixerAttributesInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredMixerAttributes(audioAttributes15, readInt90, readInt91, audioMixerAttributesInternal);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    android.media.audio.common.AudioAttributes audioAttributes16 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.AudioMixerAttributesInternal preferredMixerAttributes = getPreferredMixerAttributes(audioAttributes16, readInt92);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(preferredMixerAttributes, 1);
                    return true;
                case 102:
                    android.media.audio.common.AudioAttributes audioAttributes17 = (android.media.audio.common.AudioAttributes) parcel.readTypedObject(android.media.audio.common.AudioAttributes.CREATOR);
                    int readInt93 = parcel.readInt();
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearPreferredMixerAttributes(audioAttributes17, readInt93, readInt94);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IAudioPolicyService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IAudioPolicyService.DESCRIPTOR;
            }

            @Override // android.media.IAudioPolicyService
            public void onNewAudioModulesAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDeviceConnectionState(int i, android.media.audio.common.AudioPort audioPort, android.media.audio.common.AudioFormatDescription audioFormatDescription) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioPort, 0);
                    obtain.writeTypedObject(audioFormatDescription, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getDeviceConnectionState(android.media.audio.common.AudioDevice audioDevice) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioDevice, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void handleDeviceConfigChange(android.media.audio.common.AudioDevice audioDevice, java.lang.String str, android.media.audio.common.AudioFormatDescription audioFormatDescription) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioDevice, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioFormatDescription, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setPhoneState(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setForceUse(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getForceUse(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOutput(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.GetOutputForAttrResponse getOutputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, android.content.AttributionSourceState attributionSourceState, android.media.audio.common.AudioConfig audioConfig, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeTypedObject(audioConfig, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.GetOutputForAttrResponse) obtain2.readTypedObject(android.media.GetOutputForAttrResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void startOutput(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopOutput(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseOutput(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.GetInputForAttrResponse getInputForAttr(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, int i3, android.content.AttributionSourceState attributionSourceState, android.media.audio.common.AudioConfigBase audioConfigBase, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeTypedObject(audioConfigBase, 0);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.GetInputForAttrResponse) obtain2.readTypedObject(android.media.GetInputForAttrResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void startInput(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopInput(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseInput(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void initStreamVolume(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setStreamVolumeIndex(int i, android.media.audio.common.AudioDeviceDescription audioDeviceDescription, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getStreamVolumeIndex(int i, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioDeviceDescription audioDeviceDescription, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getMaxVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getMinVolumeIndexForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getStrategyForStream(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.audio.common.AudioDevice[] getDevicesForAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.audio.common.AudioDevice[]) obtain2.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOutputForEffect(android.media.EffectDescriptor effectDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(effectDescriptor, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerEffect(android.media.EffectDescriptor effectDescriptor, int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(effectDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void unregisterEffect(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setEffectEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void moveEffectsToIo(int[] iArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isStreamActive(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isStreamActiveRemotely(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isSourceActive(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.EffectDescriptor[] queryDefaultPreProcessing(int i, android.media.audio.common.Int r6) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(r6, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    android.media.EffectDescriptor[] effectDescriptorArr = (android.media.EffectDescriptor[]) obtain2.createTypedArray(android.media.EffectDescriptor.CREATOR);
                    if (obtain2.readInt() != 0) {
                        r6.readFromParcel(obtain2);
                    }
                    return effectDescriptorArr;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int addSourceDefaultEffect(android.media.audio.common.AudioUuid audioUuid, java.lang.String str, android.media.audio.common.AudioUuid audioUuid2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioUuid, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioUuid2, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int addStreamDefaultEffect(android.media.audio.common.AudioUuid audioUuid, java.lang.String str, android.media.audio.common.AudioUuid audioUuid2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioUuid, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(audioUuid2, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeSourceDefaultEffect(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeStreamDefaultEffect(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setSupportedSystemUsages(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAllowedCapturePolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getOffloadSupport(android.media.audio.common.AudioOffloadInfo audioOffloadInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioOffloadInfo, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isDirectOutputSupported(android.media.audio.common.AudioConfigBase audioConfigBase, android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioConfigBase, 0);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int listAudioPorts(int i, int i2, android.media.audio.common.Int r6, android.media.AudioPortFw[] audioPortFwArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(r6, 0);
                    obtain.writeInt(audioPortFwArr.length);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        r6.readFromParcel(obtain2);
                    }
                    obtain2.readTypedArray(audioPortFwArr, android.media.AudioPortFw.CREATOR);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.AudioPortFw[] listDeclaredDevicePorts(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioPortFw[]) obtain2.createTypedArray(android.media.AudioPortFw.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.AudioPortFw getAudioPort(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioPortFw) obtain2.readTypedObject(android.media.AudioPortFw.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int createAudioPatch(android.media.AudioPatchFw audioPatchFw, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioPatchFw, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseAudioPatch(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int listAudioPatches(android.media.audio.common.Int r6, android.media.AudioPatchFw[] audioPatchFwArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(r6, 0);
                    obtain.writeInt(audioPatchFwArr.length);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        r6.readFromParcel(obtain2);
                    }
                    obtain2.readTypedArray(audioPatchFwArr, android.media.AudioPatchFw.CREATOR);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioPortConfig(android.media.AudioPortConfigFw audioPortConfigFw) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioPortConfigFw, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerClient(android.media.IAudioPolicyServiceClient iAudioPolicyServiceClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAudioPolicyServiceClient);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioPortCallbacksEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAudioVolumeGroupCallbacksEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.SoundTriggerSession acquireSoundTriggerSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.SoundTriggerSession) obtain2.readTypedObject(android.media.SoundTriggerSession.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void releaseSoundTriggerSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getPhoneState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void registerPolicyMixes(android.media.AudioMix[] audioMixArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedArray(audioMixArr, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public java.util.List<android.media.AudioMix> getRegisteredPolicyMixes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.AudioMix.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void updatePolicyMixes(android.media.AudioMixUpdate[] audioMixUpdateArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedArray(audioMixUpdateArr, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setUidDeviceAffinities(int i, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeUidDeviceAffinities(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setUserIdDeviceAffinities(int i, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeUserIdDeviceAffinities(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int startAudioSource(android.media.AudioPortConfigFw audioPortConfigFw, android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioPortConfigFw, 0);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void stopAudioSource(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setMasterMono(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean getMasterMono() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public float getStreamVolumeDB(int i, int i2, android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void getSurroundFormats(android.media.audio.common.Int r6, android.media.audio.common.AudioFormatDescription[] audioFormatDescriptionArr, boolean[] zArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(r6, 0);
                    obtain.writeInt(audioFormatDescriptionArr.length);
                    obtain.writeInt(zArr.length);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        r6.readFromParcel(obtain2);
                    }
                    obtain2.readTypedArray(audioFormatDescriptionArr, android.media.audio.common.AudioFormatDescription.CREATOR);
                    obtain2.readBooleanArray(zArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void getReportedSurroundFormats(android.media.audio.common.Int r6, android.media.audio.common.AudioFormatDescription[] audioFormatDescriptionArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(r6, 0);
                    obtain.writeInt(audioFormatDescriptionArr.length);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        r6.readFromParcel(obtain2);
                    }
                    obtain2.readTypedArray(audioFormatDescriptionArr, android.media.audio.common.AudioFormatDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.audio.common.AudioFormatDescription[] getHwOffloadFormatsSupportedForBluetoothMedia(android.media.audio.common.AudioDeviceDescription audioDeviceDescription) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioDeviceDescription, 0);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.audio.common.AudioFormatDescription[]) obtain2.createTypedArray(android.media.audio.common.AudioFormatDescription.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setSurroundFormatEnabled(android.media.audio.common.AudioFormatDescription audioFormatDescription, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioFormatDescription, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setAssistantServicesUids(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setActiveAssistantServicesUids(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setA11yServicesUids(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setCurrentImeUid(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isHapticPlaybackSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isUltrasoundSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isHotwordStreamSupported(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.AudioProductStrategy[] listAudioProductStrategies() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioProductStrategy[]) obtain2.createTypedArray(android.media.AudioProductStrategy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getProductStrategyFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.AudioVolumeGroup[] listAudioVolumeGroups() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioVolumeGroup[]) obtain2.createTypedArray(android.media.AudioVolumeGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getVolumeGroupFromAudioAttributes(android.media.audio.common.AudioAttributes audioAttributes, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setRttEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean isCallScreenModeSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDevicesRoleForStrategy(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeDevicesRoleForStrategy(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearDevicesRoleForStrategy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.audio.common.AudioDevice[] getDevicesForRoleAndStrategy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.audio.common.AudioDevice[]) obtain2.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void addDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void removeDevicesRoleForCapturePreset(int i, int i2, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearDevicesRoleForCapturePreset(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.audio.common.AudioDevice[] getDevicesForRoleAndCapturePreset(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.audio.common.AudioDevice[]) obtain2.createTypedArray(android.media.audio.common.AudioDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean registerSoundTriggerCaptureStateListener(android.media.ICaptureStateListener iCaptureStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCaptureStateListener);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.GetSpatializerResponse getSpatializer(android.media.INativeSpatializerCallback iNativeSpatializerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeStrongInterface(iNativeSpatializerCallback);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.GetSpatializerResponse) obtain2.readTypedObject(android.media.GetSpatializerResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public boolean canBeSpatialized(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioConfig audioConfig, android.media.audio.common.AudioDevice[] audioDeviceArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioConfig, 0);
                    obtain.writeTypedArray(audioDeviceArr, 0);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public int getDirectPlaybackSupport(android.media.audio.common.AudioAttributes audioAttributes, android.media.audio.common.AudioConfig audioConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeTypedObject(audioConfig, 0);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.audio.common.AudioProfile[] getDirectProfilesForAttributes(android.media.audio.common.AudioAttributes audioAttributes) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.audio.common.AudioProfile[]) obtain2.createTypedArray(android.media.audio.common.AudioProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.AudioMixerAttributesInternal[] getSupportedMixerAttributes(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioMixerAttributesInternal[]) obtain2.createTypedArray(android.media.AudioMixerAttributesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void setPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2, android.media.AudioMixerAttributesInternal audioMixerAttributesInternal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(audioMixerAttributesInternal, 0);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public android.media.AudioMixerAttributesInternal getPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.AudioMixerAttributesInternal) obtain2.readTypedObject(android.media.AudioMixerAttributesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyService
            public void clearPreferredMixerAttributes(android.media.audio.common.AudioAttributes audioAttributes, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IAudioPolicyService.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
