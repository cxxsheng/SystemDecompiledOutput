package android.media.effect.effects;

/* loaded from: classes2.dex */
public class BackDropperEffect extends android.media.effect.FilterGraphEffect {
    private static final java.lang.String mGraphDefinition = "@import android.filterpacks.base;\n@import android.filterpacks.videoproc;\n@import android.filterpacks.videosrc;\n\n@filter GLTextureSource foreground {\n  texId = 0;\n  width = 0;\n  height = 0;\n  repeatFrame = true;\n}\n\n@filter MediaSource background {\n  sourceUrl = \"no_file_specified\";\n  waitForNewFrame = false;\n  sourceIsUrl = true;\n}\n\n@filter BackDropperFilter replacer {\n  autowbToggle = 1;\n}\n\n@filter GLTextureTarget output {\n  texId = 0;\n}\n\n@connect foreground[frame]  => replacer[video];\n@connect background[video]  => replacer[background];\n@connect replacer[video]    => output[frame];\n";
    private android.media.effect.EffectUpdateListener mEffectListener;
    private android.filterpacks.videoproc.BackDropperFilter.LearningDoneListener mLearningListener;

    public BackDropperEffect(android.media.effect.EffectContext effectContext, java.lang.String str) {
        super(effectContext, str, mGraphDefinition, "foreground", "output", android.filterfw.core.OneShotScheduler.class);
        this.mEffectListener = null;
        this.mLearningListener = new android.filterpacks.videoproc.BackDropperFilter.LearningDoneListener() { // from class: android.media.effect.effects.BackDropperEffect.1
            @Override // android.filterpacks.videoproc.BackDropperFilter.LearningDoneListener
            public void onLearningDone(android.filterpacks.videoproc.BackDropperFilter backDropperFilter) {
                if (android.media.effect.effects.BackDropperEffect.this.mEffectListener != null) {
                    android.media.effect.effects.BackDropperEffect.this.mEffectListener.onEffectUpdated(android.media.effect.effects.BackDropperEffect.this, null);
                }
            }
        };
        this.mGraph.getFilter("replacer").setInputValue("learningDoneListener", this.mLearningListener);
    }

    @Override // android.media.effect.FilterGraphEffect, android.media.effect.Effect
    public void setParameter(java.lang.String str, java.lang.Object obj) {
        if (str.equals(android.app.slice.Slice.SUBTYPE_SOURCE)) {
            this.mGraph.getFilter(android.net.NetworkPolicyManager.FIREWALL_CHAIN_NAME_BACKGROUND).setInputValue("sourceUrl", obj);
        } else if (str.equals("context")) {
            this.mGraph.getFilter(android.net.NetworkPolicyManager.FIREWALL_CHAIN_NAME_BACKGROUND).setInputValue("context", obj);
        }
    }

    @Override // android.media.effect.Effect
    public void setUpdateListener(android.media.effect.EffectUpdateListener effectUpdateListener) {
        this.mEffectListener = effectUpdateListener;
    }
}
