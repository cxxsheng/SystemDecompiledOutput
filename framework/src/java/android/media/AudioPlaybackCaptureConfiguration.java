package android.media;

/* loaded from: classes2.dex */
public final class AudioPlaybackCaptureConfiguration {
    private final android.media.audiopolicy.AudioMixingRule mAudioMixingRule;
    private final android.media.projection.MediaProjection mProjection;

    private AudioPlaybackCaptureConfiguration(android.media.audiopolicy.AudioMixingRule audioMixingRule, android.media.projection.MediaProjection mediaProjection) {
        this.mAudioMixingRule = audioMixingRule;
        this.mProjection = mediaProjection;
    }

    public android.media.projection.MediaProjection getMediaProjection() {
        return this.mProjection;
    }

    public int[] getMatchingUsages() {
        return getIntPredicates(1, new java.util.function.ToIntFunction() { // from class: android.media.AudioPlaybackCaptureConfiguration$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int usage;
                usage = ((android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) obj).getAudioAttributes().getUsage();
                return usage;
            }
        });
    }

    public int[] getMatchingUids() {
        return getIntPredicates(4, new java.util.function.ToIntFunction() { // from class: android.media.AudioPlaybackCaptureConfiguration$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intProp;
                intProp = ((android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) obj).getIntProp();
                return intProp;
            }
        });
    }

    public int[] getExcludeUsages() {
        return getIntPredicates(32769, new java.util.function.ToIntFunction() { // from class: android.media.AudioPlaybackCaptureConfiguration$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int usage;
                usage = ((android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) obj).getAudioAttributes().getUsage();
                return usage;
            }
        });
    }

    public int[] getExcludeUids() {
        return getIntPredicates(32772, new java.util.function.ToIntFunction() { // from class: android.media.AudioPlaybackCaptureConfiguration$$ExternalSyntheticLambda3
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intProp;
                intProp = ((android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) obj).getIntProp();
                return intProp;
            }
        });
    }

    private int[] getIntPredicates(final int i, java.util.function.ToIntFunction<android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion> toIntFunction) {
        return this.mAudioMixingRule.getCriteria().stream().filter(new java.util.function.Predicate() { // from class: android.media.AudioPlaybackCaptureConfiguration$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.media.AudioPlaybackCaptureConfiguration.lambda$getIntPredicates$4(i, (android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion) obj);
            }
        }).mapToInt(toIntFunction).toArray();
    }

    static /* synthetic */ boolean lambda$getIntPredicates$4(int i, android.media.audiopolicy.AudioMixingRule.AudioMixMatchCriterion audioMixMatchCriterion) {
        return audioMixMatchCriterion.getRule() == i;
    }

    android.media.audiopolicy.AudioMix createAudioMix(android.media.AudioFormat audioFormat) {
        return new android.media.audiopolicy.AudioMix.Builder(this.mAudioMixingRule).setFormat(audioFormat).setRouteFlags(3).build();
    }

    public static final class Builder {
        private static final java.lang.String ERROR_MESSAGE_MISMATCHED_RULES = "Inclusive and exclusive usage rules cannot be combined";
        private static final java.lang.String ERROR_MESSAGE_NON_AUDIO_PROJECTION = "MediaProjection can not project audio";
        private static final java.lang.String ERROR_MESSAGE_START_ACTIVITY_FAILED = "startActivityForResult failed";
        private static final int MATCH_TYPE_EXCLUSIVE = 2;
        private static final int MATCH_TYPE_INCLUSIVE = 1;
        private static final int MATCH_TYPE_UNSPECIFIED = 0;
        private final android.media.audiopolicy.AudioMixingRule.Builder mAudioMixingRuleBuilder;
        private final android.media.projection.MediaProjection mProjection;
        private int mUsageMatchType = 0;
        private int mUidMatchType = 0;

        public Builder(android.media.projection.MediaProjection mediaProjection) {
            com.android.internal.util.Preconditions.checkNotNull(mediaProjection);
            try {
                com.android.internal.util.Preconditions.checkArgument(mediaProjection.getProjection().canProjectAudio(), ERROR_MESSAGE_NON_AUDIO_PROJECTION);
                this.mProjection = mediaProjection;
                this.mAudioMixingRuleBuilder = new android.media.audiopolicy.AudioMixingRule.Builder();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public android.media.AudioPlaybackCaptureConfiguration.Builder addMatchingUsage(int i) {
            com.android.internal.util.Preconditions.checkState(this.mUsageMatchType != 2, ERROR_MESSAGE_MISMATCHED_RULES);
            this.mAudioMixingRuleBuilder.addRule(new android.media.AudioAttributes.Builder().setUsage(i).build(), 1);
            this.mUsageMatchType = 1;
            return this;
        }

        public android.media.AudioPlaybackCaptureConfiguration.Builder addMatchingUid(int i) {
            com.android.internal.util.Preconditions.checkState(this.mUidMatchType != 2, ERROR_MESSAGE_MISMATCHED_RULES);
            this.mAudioMixingRuleBuilder.addMixRule(4, java.lang.Integer.valueOf(i));
            this.mUidMatchType = 1;
            return this;
        }

        public android.media.AudioPlaybackCaptureConfiguration.Builder excludeUsage(int i) {
            com.android.internal.util.Preconditions.checkState(this.mUsageMatchType != 1, ERROR_MESSAGE_MISMATCHED_RULES);
            this.mAudioMixingRuleBuilder.excludeRule(new android.media.AudioAttributes.Builder().setUsage(i).build(), 1);
            this.mUsageMatchType = 2;
            return this;
        }

        public android.media.AudioPlaybackCaptureConfiguration.Builder excludeUid(int i) {
            com.android.internal.util.Preconditions.checkState(this.mUidMatchType != 1, ERROR_MESSAGE_MISMATCHED_RULES);
            this.mAudioMixingRuleBuilder.excludeMixRule(4, java.lang.Integer.valueOf(i));
            this.mUidMatchType = 2;
            return this;
        }

        public android.media.AudioPlaybackCaptureConfiguration build() {
            return new android.media.AudioPlaybackCaptureConfiguration(this.mAudioMixingRuleBuilder.build(), this.mProjection);
        }
    }
}
