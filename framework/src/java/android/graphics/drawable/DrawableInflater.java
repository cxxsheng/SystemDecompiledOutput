package android.graphics.drawable;

/* loaded from: classes.dex */
public final class DrawableInflater {
    private static final java.util.HashMap<java.lang.String, java.lang.reflect.Constructor<? extends android.graphics.drawable.Drawable>> CONSTRUCTOR_MAP = new java.util.HashMap<>();
    private final java.lang.ClassLoader mClassLoader;
    private final android.content.res.Resources mRes;

    public static android.graphics.drawable.Drawable loadDrawable(android.content.Context context, int i) {
        return loadDrawable(context.getResources(), context.getTheme(), i);
    }

    public static android.graphics.drawable.Drawable loadDrawable(android.content.res.Resources resources, android.content.res.Resources.Theme theme, int i) {
        return resources.getDrawable(i, theme);
    }

    public DrawableInflater(android.content.res.Resources resources, java.lang.ClassLoader classLoader) {
        this.mRes = resources;
        this.mClassLoader = classLoader;
    }

    public android.graphics.drawable.Drawable inflateFromXml(java.lang.String str, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return inflateFromXmlForDensity(str, xmlPullParser, attributeSet, 0, theme);
    }

    android.graphics.drawable.Drawable inflateFromXmlForDensity(java.lang.String str, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, int i, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (str.equals("drawable") && (str = attributeSet.getAttributeValue(null, "class")) == null) {
            throw new android.view.InflateException("<drawable> tag must specify class attribute");
        }
        android.graphics.drawable.Drawable inflateFromTag = inflateFromTag(str);
        if (inflateFromTag == null) {
            inflateFromTag = inflateFromClass(str);
        }
        inflateFromTag.setSrcDensityOverride(i);
        inflateFromTag.inflate(this.mRes, xmlPullParser, attributeSet, theme);
        return inflateFromTag;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private android.graphics.drawable.Drawable inflateFromTag(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -2024464016:
                if (str.equals("adaptive-icon")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1724158635:
                if (str.equals("transition")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1671889043:
                if (str.equals("nine-patch")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1493546681:
                if (str.equals("animation-list")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1388777169:
                if (str.equals("bitmap")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -930826704:
                if (str.equals("ripple")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -925180581:
                if (str.equals("rotate")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -820387517:
                if (str.equals("vector")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -510364471:
                if (str.equals("animated-selector")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -94197862:
                if (str.equals("layer-list")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3056464:
                if (str.equals("clip")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 94842723:
                if (str.equals("color")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 100360477:
                if (str.equals("inset")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 109250890:
                if (str.equals("scale")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 109399969:
                if (str.equals("shape")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 160680263:
                if (str.equals("level-list")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1191572447:
                if (str.equals("selector")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1442046129:
                if (str.equals("animated-image")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 2013827269:
                if (str.equals("animated-rotate")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 2118620333:
                if (str.equals("animated-vector")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new android.graphics.drawable.StateListDrawable();
            case 1:
                return new android.graphics.drawable.AnimatedStateListDrawable();
            case 2:
                return new android.graphics.drawable.LevelListDrawable();
            case 3:
                return new android.graphics.drawable.LayerDrawable();
            case 4:
                return new android.graphics.drawable.TransitionDrawable();
            case 5:
                return new android.graphics.drawable.RippleDrawable();
            case 6:
                return new android.graphics.drawable.AdaptiveIconDrawable();
            case 7:
                return new android.graphics.drawable.ColorDrawable();
            case '\b':
                return new android.graphics.drawable.GradientDrawable();
            case '\t':
                return new android.graphics.drawable.VectorDrawable();
            case '\n':
                return new android.graphics.drawable.AnimatedVectorDrawable();
            case 11:
                return new android.graphics.drawable.ScaleDrawable();
            case '\f':
                return new android.graphics.drawable.ClipDrawable();
            case '\r':
                return new android.graphics.drawable.RotateDrawable();
            case 14:
                return new android.graphics.drawable.AnimatedRotateDrawable();
            case 15:
                return new android.graphics.drawable.AnimationDrawable();
            case 16:
                return new android.graphics.drawable.InsetDrawable();
            case 17:
                return new android.graphics.drawable.BitmapDrawable();
            case 18:
                return new android.graphics.drawable.NinePatchDrawable();
            case 19:
                return new android.graphics.drawable.AnimatedImageDrawable();
            default:
                return null;
        }
    }

    private android.graphics.drawable.Drawable inflateFromClass(java.lang.String str) {
        java.lang.reflect.Constructor<? extends android.graphics.drawable.Drawable> constructor;
        try {
            synchronized (CONSTRUCTOR_MAP) {
                constructor = CONSTRUCTOR_MAP.get(str);
                if (constructor == null) {
                    constructor = this.mClassLoader.loadClass(str).asSubclass(android.graphics.drawable.Drawable.class).getConstructor(new java.lang.Class[0]);
                    CONSTRUCTOR_MAP.put(str, constructor);
                }
            }
            return constructor.newInstance(new java.lang.Object[0]);
        } catch (java.lang.ClassCastException e) {
            android.view.InflateException inflateException = new android.view.InflateException("Class is not a Drawable " + str);
            inflateException.initCause(e);
            throw inflateException;
        } catch (java.lang.ClassNotFoundException e2) {
            android.view.InflateException inflateException2 = new android.view.InflateException("Class not found " + str);
            inflateException2.initCause(e2);
            throw inflateException2;
        } catch (java.lang.NoSuchMethodException e3) {
            android.view.InflateException inflateException3 = new android.view.InflateException("Error inflating class " + str);
            inflateException3.initCause(e3);
            throw inflateException3;
        } catch (java.lang.Exception e4) {
            android.view.InflateException inflateException4 = new android.view.InflateException("Error inflating class " + str);
            inflateException4.initCause(e4);
            throw inflateException4;
        }
    }
}
