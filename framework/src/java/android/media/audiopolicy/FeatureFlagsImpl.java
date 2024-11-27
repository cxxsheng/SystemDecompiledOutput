package android.media.audiopolicy;

/* loaded from: classes2.dex */
public final class FeatureFlagsImpl implements android.media.audiopolicy.FeatureFlags {
    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixOwnership() {
        return false;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixPolicyOrdering() {
        return false;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioMixTestApi() {
        return false;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean audioPolicyUpdateMixingRulesApi() {
        return false;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean enableFadeManagerConfiguration() {
        return false;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean multiZoneAudio() {
        return false;
    }

    @Override // android.media.audiopolicy.FeatureFlags
    public boolean recordAudioDeviceAwarePermission() {
        return false;
    }
}
