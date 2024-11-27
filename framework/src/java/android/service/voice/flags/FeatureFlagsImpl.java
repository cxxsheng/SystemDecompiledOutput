package android.service.voice.flags;

/* loaded from: classes3.dex */
public final class FeatureFlagsImpl implements android.service.voice.flags.FeatureFlags {
    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowComplexResultsEgressFromVqds() {
        return false;
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowForegroundActivitiesInOnShow() {
        return false;
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowHotwordBumpEgress() {
        return false;
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowSpeakerIdEgress() {
        return false;
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowTrainingDataEgressFromHds() {
        return false;
    }

    @Override // android.service.voice.flags.FeatureFlags
    public boolean allowVariousAttentionTypes() {
        return false;
    }
}
