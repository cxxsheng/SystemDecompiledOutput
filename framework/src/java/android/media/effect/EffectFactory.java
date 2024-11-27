package android.media.effect;

/* loaded from: classes2.dex */
public class EffectFactory {
    public static final java.lang.String EFFECT_AUTOFIX = "android.media.effect.effects.AutoFixEffect";
    public static final java.lang.String EFFECT_BACKDROPPER = "android.media.effect.effects.BackDropperEffect";
    public static final java.lang.String EFFECT_BITMAPOVERLAY = "android.media.effect.effects.BitmapOverlayEffect";
    public static final java.lang.String EFFECT_BLACKWHITE = "android.media.effect.effects.BlackWhiteEffect";
    public static final java.lang.String EFFECT_BRIGHTNESS = "android.media.effect.effects.BrightnessEffect";
    public static final java.lang.String EFFECT_CONTRAST = "android.media.effect.effects.ContrastEffect";
    public static final java.lang.String EFFECT_CROP = "android.media.effect.effects.CropEffect";
    public static final java.lang.String EFFECT_CROSSPROCESS = "android.media.effect.effects.CrossProcessEffect";
    public static final java.lang.String EFFECT_DOCUMENTARY = "android.media.effect.effects.DocumentaryEffect";
    public static final java.lang.String EFFECT_DUOTONE = "android.media.effect.effects.DuotoneEffect";
    public static final java.lang.String EFFECT_FILLLIGHT = "android.media.effect.effects.FillLightEffect";
    public static final java.lang.String EFFECT_FISHEYE = "android.media.effect.effects.FisheyeEffect";
    public static final java.lang.String EFFECT_FLIP = "android.media.effect.effects.FlipEffect";
    public static final java.lang.String EFFECT_GRAIN = "android.media.effect.effects.GrainEffect";
    public static final java.lang.String EFFECT_GRAYSCALE = "android.media.effect.effects.GrayscaleEffect";
    public static final java.lang.String EFFECT_IDENTITY = "IdentityEffect";
    public static final java.lang.String EFFECT_LOMOISH = "android.media.effect.effects.LomoishEffect";
    public static final java.lang.String EFFECT_NEGATIVE = "android.media.effect.effects.NegativeEffect";
    private static final java.lang.String[] EFFECT_PACKAGES = {"android.media.effect.effects.", ""};
    public static final java.lang.String EFFECT_POSTERIZE = "android.media.effect.effects.PosterizeEffect";
    public static final java.lang.String EFFECT_REDEYE = "android.media.effect.effects.RedEyeEffect";
    public static final java.lang.String EFFECT_ROTATE = "android.media.effect.effects.RotateEffect";
    public static final java.lang.String EFFECT_SATURATE = "android.media.effect.effects.SaturateEffect";
    public static final java.lang.String EFFECT_SEPIA = "android.media.effect.effects.SepiaEffect";
    public static final java.lang.String EFFECT_SHARPEN = "android.media.effect.effects.SharpenEffect";
    public static final java.lang.String EFFECT_STRAIGHTEN = "android.media.effect.effects.StraightenEffect";
    public static final java.lang.String EFFECT_TEMPERATURE = "android.media.effect.effects.ColorTemperatureEffect";
    public static final java.lang.String EFFECT_TINT = "android.media.effect.effects.TintEffect";
    public static final java.lang.String EFFECT_VIGNETTE = "android.media.effect.effects.VignetteEffect";
    private android.media.effect.EffectContext mEffectContext;

    EffectFactory(android.media.effect.EffectContext effectContext) {
        this.mEffectContext = effectContext;
    }

    public android.media.effect.Effect createEffect(java.lang.String str) {
        java.lang.Class effectClassByName = getEffectClassByName(str);
        if (effectClassByName == null) {
            throw new java.lang.IllegalArgumentException("Cannot instantiate unknown effect '" + str + "'!");
        }
        return instantiateEffect(effectClassByName, str);
    }

    public static boolean isEffectSupported(java.lang.String str) {
        return getEffectClassByName(str) != null;
    }

    private static java.lang.Class getEffectClassByName(java.lang.String str) {
        java.lang.ClassLoader contextClassLoader = java.lang.Thread.currentThread().getContextClassLoader();
        java.lang.Class<?> cls = null;
        for (java.lang.String str2 : EFFECT_PACKAGES) {
            try {
                cls = contextClassLoader.loadClass(str2 + str);
            } catch (java.lang.ClassNotFoundException e) {
            }
            if (cls != null) {
                break;
            }
        }
        return cls;
    }

    private android.media.effect.Effect instantiateEffect(java.lang.Class cls, java.lang.String str) {
        if (!android.media.effect.Effect.class.isAssignableFrom(cls)) {
            throw new java.lang.IllegalArgumentException("Attempting to allocate effect '" + cls + "' which is not a subclass of Effect!");
        }
        try {
            try {
                return (android.media.effect.Effect) cls.getConstructor(android.media.effect.EffectContext.class, java.lang.String.class).newInstance(this.mEffectContext, str);
            } catch (java.lang.Throwable th) {
                throw new java.lang.RuntimeException("There was an error constructing the effect '" + cls + "'!", th);
            }
        } catch (java.lang.NoSuchMethodException e) {
            throw new java.lang.RuntimeException("The effect class '" + cls + "' does not have the required constructor.", e);
        }
    }
}
