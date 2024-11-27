package android.media.effect.effects;

/* loaded from: classes2.dex */
public class FlipEffect extends android.media.effect.SingleFilterEffect {
    public FlipEffect(android.media.effect.EffectContext effectContext, java.lang.String str) {
        super(effectContext, str, android.filterpacks.imageproc.FlipFilter.class, android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE, new java.lang.Object[0]);
    }
}
