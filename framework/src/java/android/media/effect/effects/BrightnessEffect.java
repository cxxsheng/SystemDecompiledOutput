package android.media.effect.effects;

/* loaded from: classes2.dex */
public class BrightnessEffect extends android.media.effect.SingleFilterEffect {
    public BrightnessEffect(android.media.effect.EffectContext effectContext, java.lang.String str) {
        super(effectContext, str, android.filterpacks.imageproc.BrightnessFilter.class, android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE, new java.lang.Object[0]);
    }
}
