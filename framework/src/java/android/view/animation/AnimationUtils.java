package android.view.animation;

/* loaded from: classes4.dex */
public class AnimationUtils {
    private static final int SEQUENTIALLY = 1;
    private static final int TOGETHER = 0;
    private static boolean sExpectedPresentationTimeFlagValue = android.view.flags.Flags.expectedPresentationTimeReadOnly();
    private static java.lang.ThreadLocal<android.view.animation.AnimationUtils.AnimationState> sAnimationState = new java.lang.ThreadLocal<android.view.animation.AnimationUtils.AnimationState>() { // from class: android.view.animation.AnimationUtils.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public android.view.animation.AnimationUtils.AnimationState initialValue() {
            return new android.view.animation.AnimationUtils.AnimationState();
        }
    };

    private static class AnimationState {
        boolean animationClockLocked;
        long currentVsyncTimeMillis;
        long lastReportedTimeMillis;
        long mExpectedPresentationTimeNanos;

        private AnimationState() {
        }
    }

    public static void lockAnimationClock(long j, long j2) {
        android.view.animation.AnimationUtils.AnimationState animationState = sAnimationState.get();
        animationState.animationClockLocked = true;
        animationState.currentVsyncTimeMillis = j;
        if (!sExpectedPresentationTimeFlagValue) {
            animationState.mExpectedPresentationTimeNanos = j2;
        }
    }

    public static void lockAnimationClock(long j) {
        android.view.animation.AnimationUtils.AnimationState animationState = sAnimationState.get();
        animationState.animationClockLocked = true;
        animationState.currentVsyncTimeMillis = j;
    }

    public static void unlockAnimationClock() {
        sAnimationState.get().animationClockLocked = false;
    }

    public static long currentAnimationTimeMillis() {
        android.view.animation.AnimationUtils.AnimationState animationState = sAnimationState.get();
        if (animationState.animationClockLocked) {
            return java.lang.Math.max(animationState.currentVsyncTimeMillis, animationState.lastReportedTimeMillis);
        }
        animationState.lastReportedTimeMillis = android.os.SystemClock.uptimeMillis();
        return animationState.lastReportedTimeMillis;
    }

    public static long getExpectedPresentationTimeNanos() {
        if (!sExpectedPresentationTimeFlagValue) {
            return android.os.SystemClock.uptimeMillis() * 1000000;
        }
        return sAnimationState.get().mExpectedPresentationTimeNanos;
    }

    public static long getExpectedPresentationTimeMillis() {
        return getExpectedPresentationTimeNanos() / 1000000;
    }

    public static android.view.animation.Animation loadAnimation(android.content.Context context, int i) throws android.content.res.Resources.NotFoundException {
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = context.getResources().getAnimation(i);
                return createAnimationFromXml(context, xmlResourceParser);
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                throw new android.content.res.Resources.NotFoundException("Can't load animation resource ID #0x" + java.lang.Integer.toHexString(i), e);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    private static android.view.animation.Animation createAnimationFromXml(android.content.Context context, org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return createAnimationFromXml(context, xmlPullParser, null, android.util.Xml.asAttributeSet(xmlPullParser));
    }

    private static android.view.animation.Animation createAnimationFromXml(android.content.Context context, org.xmlpull.v1.XmlPullParser xmlPullParser, android.view.animation.AnimationSet animationSet, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, android.view.InflateException {
        int depth = xmlPullParser.getDepth();
        android.view.animation.Animation animation = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if (name.equals("set")) {
                        android.view.animation.AnimationSet animationSet2 = new android.view.animation.AnimationSet(context, attributeSet);
                        createAnimationFromXml(context, xmlPullParser, animationSet2, attributeSet);
                        animation = animationSet2;
                    } else if (name.equals("alpha")) {
                        animation = new android.view.animation.AlphaAnimation(context, attributeSet);
                    } else if (name.equals("scale")) {
                        animation = new android.view.animation.ScaleAnimation(context, attributeSet);
                    } else if (name.equals("rotate")) {
                        animation = new android.view.animation.RotateAnimation(context, attributeSet);
                    } else if (name.equals("translate")) {
                        animation = new android.view.animation.TranslateAnimation(context, attributeSet);
                    } else if (name.equals("cliprect")) {
                        animation = new android.view.animation.ClipRectAnimation(context, attributeSet);
                    } else if (name.equals("extend")) {
                        animation = new android.view.animation.ExtendAnimation(context, attributeSet);
                    } else {
                        throw new android.view.InflateException("Unknown animation name: " + xmlPullParser.getName());
                    }
                    if (animationSet != null) {
                        animationSet.addAnimation(animation);
                    }
                }
            }
        }
        return animation;
    }

    public static android.view.animation.LayoutAnimationController loadLayoutAnimation(android.content.Context context, int i) throws android.content.res.Resources.NotFoundException {
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = context.getResources().getAnimation(i);
                return createLayoutAnimationFromXml(context, xmlResourceParser);
            } catch (android.view.InflateException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                throw new android.content.res.Resources.NotFoundException("Can't load animation resource ID #0x" + java.lang.Integer.toHexString(i), e);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    private static android.view.animation.LayoutAnimationController createLayoutAnimationFromXml(android.content.Context context, org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, android.view.InflateException {
        return createLayoutAnimationFromXml(context, xmlPullParser, android.util.Xml.asAttributeSet(xmlPullParser));
    }

    private static android.view.animation.LayoutAnimationController createLayoutAnimationFromXml(android.content.Context context, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, android.view.InflateException {
        int depth = xmlPullParser.getDepth();
        android.view.animation.LayoutAnimationController layoutAnimationController = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if ("layoutAnimation".equals(name)) {
                        layoutAnimationController = new android.view.animation.LayoutAnimationController(context, attributeSet);
                    } else if ("gridLayoutAnimation".equals(name)) {
                        layoutAnimationController = new android.view.animation.GridLayoutAnimationController(context, attributeSet);
                    } else {
                        throw new android.view.InflateException("Unknown layout animation name: " + name);
                    }
                }
            }
        }
        return layoutAnimationController;
    }

    public static android.view.animation.Animation makeInAnimation(android.content.Context context, boolean z) {
        android.view.animation.Animation loadAnimation;
        if (z) {
            loadAnimation = loadAnimation(context, 17432578);
        } else {
            loadAnimation = loadAnimation(context, com.android.internal.R.anim.slide_in_right);
        }
        loadAnimation.setInterpolator(new android.view.animation.DecelerateInterpolator());
        loadAnimation.setStartTime(currentAnimationTimeMillis());
        return loadAnimation;
    }

    public static android.view.animation.Animation makeOutAnimation(android.content.Context context, boolean z) {
        android.view.animation.Animation loadAnimation;
        if (z) {
            loadAnimation = loadAnimation(context, 17432579);
        } else {
            loadAnimation = loadAnimation(context, com.android.internal.R.anim.slide_out_left);
        }
        loadAnimation.setInterpolator(new android.view.animation.AccelerateInterpolator());
        loadAnimation.setStartTime(currentAnimationTimeMillis());
        return loadAnimation;
    }

    public static android.view.animation.Animation makeInChildBottomAnimation(android.content.Context context) {
        android.view.animation.Animation loadAnimation = loadAnimation(context, com.android.internal.R.anim.slide_in_child_bottom);
        loadAnimation.setInterpolator(new android.view.animation.AccelerateInterpolator());
        loadAnimation.setStartTime(currentAnimationTimeMillis());
        return loadAnimation;
    }

    public static android.view.animation.Interpolator loadInterpolator(android.content.Context context, int i) throws android.content.res.Resources.NotFoundException {
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = context.getResources().getAnimation(i);
                return createInterpolatorFromXml(context.getResources(), context.getTheme(), xmlResourceParser);
            } catch (android.view.InflateException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                throw new android.content.res.Resources.NotFoundException("Can't load animation resource ID #0x" + java.lang.Integer.toHexString(i), e);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    public static android.view.animation.Interpolator loadInterpolator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, int i) throws android.content.res.Resources.NotFoundException {
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParser = resources.getAnimation(i);
                return createInterpolatorFromXml(resources, theme, xmlResourceParser);
            } catch (android.view.InflateException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                throw new android.content.res.Resources.NotFoundException("Can't load animation resource ID #0x" + java.lang.Integer.toHexString(i), e);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x00d8, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.view.animation.Interpolator createInterpolatorFromXml(android.content.res.Resources resources, android.content.res.Resources.Theme theme, org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, android.view.InflateException {
        int depth = xmlPullParser.getDepth();
        android.view.animation.Interpolator interpolator = null;
        while (true) {
            int next = xmlPullParser.next();
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlPullParser);
                    java.lang.String name = xmlPullParser.getName();
                    if (name.equals("linearInterpolator")) {
                        interpolator = new android.view.animation.LinearInterpolator();
                    } else if (name.equals("accelerateInterpolator")) {
                        interpolator = new android.view.animation.AccelerateInterpolator(resources, theme, asAttributeSet);
                    } else if (name.equals("decelerateInterpolator")) {
                        interpolator = new android.view.animation.DecelerateInterpolator(resources, theme, asAttributeSet);
                    } else if (name.equals("accelerateDecelerateInterpolator")) {
                        interpolator = new android.view.animation.AccelerateDecelerateInterpolator();
                    } else if (name.equals("cycleInterpolator")) {
                        interpolator = new android.view.animation.CycleInterpolator(resources, theme, asAttributeSet);
                    } else if (name.equals("anticipateInterpolator")) {
                        interpolator = new android.view.animation.AnticipateInterpolator(resources, theme, asAttributeSet);
                    } else if (name.equals("overshootInterpolator")) {
                        interpolator = new android.view.animation.OvershootInterpolator(resources, theme, asAttributeSet);
                    } else if (name.equals("anticipateOvershootInterpolator")) {
                        interpolator = new android.view.animation.AnticipateOvershootInterpolator(resources, theme, asAttributeSet);
                    } else if (name.equals("bounceInterpolator")) {
                        interpolator = new android.view.animation.BounceInterpolator();
                    } else if (name.equals("pathInterpolator")) {
                        interpolator = new android.view.animation.PathInterpolator(resources, theme, asAttributeSet);
                    } else {
                        throw new android.view.InflateException("Unknown interpolator name: " + xmlPullParser.getName());
                    }
                }
            }
        }
    }
}
