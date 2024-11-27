package android.media.effect.effects;

/* loaded from: classes2.dex */
public class CropEffect extends android.media.effect.SizeChangeEffect {
    public CropEffect(android.media.effect.EffectContext effectContext, java.lang.String str) {
        super(effectContext, str, android.filterpacks.imageproc.CropRectFilter.class, android.app.slice.SliceItem.FORMAT_IMAGE, android.app.slice.SliceItem.FORMAT_IMAGE, new java.lang.Object[0]);
    }
}
