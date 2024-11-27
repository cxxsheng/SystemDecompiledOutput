package android.animation;

/* loaded from: classes.dex */
public class AnimatorInflater {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int SEQUENTIALLY = 1;
    private static final java.lang.String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;
    private static final android.util.TypedValue sTmpTypedValue = new android.util.TypedValue();

    public static android.animation.Animator loadAnimator(android.content.Context context, int i) throws android.content.res.Resources.NotFoundException {
        return loadAnimator(context.getResources(), context.getTheme(), i);
    }

    public static android.animation.Animator loadAnimator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, int i) throws android.content.res.Resources.NotFoundException {
        return loadAnimator(resources, theme, i, 1.0f);
    }

    public static android.animation.Animator loadAnimator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, int i, float f) throws android.content.res.Resources.NotFoundException {
        android.content.res.ConfigurationBoundResourceCache<android.animation.Animator> animatorCache = resources.getAnimatorCache();
        long j = i;
        android.animation.Animator configurationBoundResourceCache = animatorCache.getInstance(j, resources, theme);
        if (configurationBoundResourceCache != null) {
            return configurationBoundResourceCache;
        }
        int generation = animatorCache.getGeneration();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                android.content.res.XmlResourceParser animation = resources.getAnimation(i);
                try {
                    android.animation.Animator createAnimatorFromXml = createAnimatorFromXml(resources, theme, animation, f);
                    if (createAnimatorFromXml != null) {
                        createAnimatorFromXml.appendChangingConfigurations(getChangingConfigs(resources, i));
                        android.content.res.ConstantState<android.animation.Animator> createConstantState = createAnimatorFromXml.createConstantState();
                        if (createConstantState != null) {
                            animatorCache.put(j, theme, createConstantState, generation);
                            createAnimatorFromXml = createConstantState.newInstance2(resources, theme);
                        }
                    }
                    if (animation != null) {
                        animation.close();
                    }
                    return createAnimatorFromXml;
                } catch (java.io.IOException e) {
                    e = e;
                    android.content.res.Resources.NotFoundException notFoundException = new android.content.res.Resources.NotFoundException("Can't load animation resource ID #0x" + java.lang.Integer.toHexString(i));
                    notFoundException.initCause(e);
                    throw notFoundException;
                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    e = e2;
                    android.content.res.Resources.NotFoundException notFoundException2 = new android.content.res.Resources.NotFoundException("Can't load animation resource ID #0x" + java.lang.Integer.toHexString(i));
                    notFoundException2.initCause(e);
                    throw notFoundException2;
                } catch (java.lang.Throwable th) {
                    th = th;
                    xmlResourceParser = animation;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (java.io.IOException e3) {
                e = e3;
            } catch (org.xmlpull.v1.XmlPullParserException e4) {
                e = e4;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public static android.animation.StateListAnimator loadStateListAnimator(android.content.Context context, int i) throws android.content.res.Resources.NotFoundException {
        android.content.res.Resources resources = context.getResources();
        android.content.res.ConfigurationBoundResourceCache<android.animation.StateListAnimator> stateListAnimatorCache = resources.getStateListAnimatorCache();
        android.content.res.Resources.Theme theme = context.getTheme();
        long j = i;
        android.animation.StateListAnimator configurationBoundResourceCache = stateListAnimatorCache.getInstance(j, resources, theme);
        if (configurationBoundResourceCache != null) {
            return configurationBoundResourceCache;
        }
        int generation = stateListAnimatorCache.getGeneration();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                android.content.res.XmlResourceParser animation = resources.getAnimation(i);
                try {
                    android.animation.StateListAnimator createStateListAnimatorFromXml = createStateListAnimatorFromXml(context, animation, android.util.Xml.asAttributeSet(animation));
                    if (createStateListAnimatorFromXml != null) {
                        createStateListAnimatorFromXml.appendChangingConfigurations(getChangingConfigs(resources, i));
                        android.content.res.ConstantState<android.animation.StateListAnimator> createConstantState = createStateListAnimatorFromXml.createConstantState();
                        if (createConstantState != null) {
                            stateListAnimatorCache.put(j, theme, createConstantState, generation);
                            createStateListAnimatorFromXml = createConstantState.newInstance2(resources, theme);
                        }
                    }
                    if (animation != null) {
                        animation.close();
                    }
                    return createStateListAnimatorFromXml;
                } catch (java.io.IOException e) {
                    e = e;
                    android.content.res.Resources.NotFoundException notFoundException = new android.content.res.Resources.NotFoundException("Can't load state list animator resource ID #0x" + java.lang.Integer.toHexString(i));
                    notFoundException.initCause(e);
                    throw notFoundException;
                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    e = e2;
                    android.content.res.Resources.NotFoundException notFoundException2 = new android.content.res.Resources.NotFoundException("Can't load state list animator resource ID #0x" + java.lang.Integer.toHexString(i));
                    notFoundException2.initCause(e);
                    throw notFoundException2;
                } catch (java.lang.Throwable th) {
                    th = th;
                    xmlResourceParser = animation;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (java.io.IOException e3) {
                e = e3;
            } catch (org.xmlpull.v1.XmlPullParserException e4) {
                e = e4;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private static android.animation.StateListAnimator createStateListAnimatorFromXml(android.content.Context context, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.animation.StateListAnimator stateListAnimator = new android.animation.StateListAnimator();
        while (true) {
            switch (xmlPullParser.next()) {
                case 1:
                case 3:
                    return stateListAnimator;
                case 2:
                    if (com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM.equals(xmlPullParser.getName())) {
                        int attributeCount = xmlPullParser.getAttributeCount();
                        int[] iArr = new int[attributeCount];
                        android.animation.Animator animator = null;
                        int i = 0;
                        for (int i2 = 0; i2 < attributeCount; i2++) {
                            int attributeNameResource = attributeSet.getAttributeNameResource(i2);
                            if (attributeNameResource == 16843213) {
                                animator = loadAnimator(context, attributeSet.getAttributeResourceValue(i2, 0));
                            } else {
                                int i3 = i + 1;
                                if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                                    attributeNameResource = -attributeNameResource;
                                }
                                iArr[i] = attributeNameResource;
                                i = i3;
                            }
                        }
                        if (animator == null) {
                            animator = createAnimatorFromXml(context.getResources(), context.getTheme(), xmlPullParser, 1.0f);
                        }
                        if (animator == null) {
                            throw new android.content.res.Resources.NotFoundException("animation state item must have a valid animation");
                        }
                        stateListAnimator.addState(android.util.StateSet.trimStateSet(iArr, i), animator);
                        break;
                    } else {
                        continue;
                    }
            }
        }
    }

    private static class PathDataEvaluator implements android.animation.TypeEvaluator<android.util.PathParser.PathData> {
        private final android.util.PathParser.PathData mPathData;

        private PathDataEvaluator() {
            this.mPathData = new android.util.PathParser.PathData();
        }

        @Override // android.animation.TypeEvaluator
        public android.util.PathParser.PathData evaluate(float f, android.util.PathParser.PathData pathData, android.util.PathParser.PathData pathData2) {
            if (!android.util.PathParser.interpolatePathData(this.mPathData, pathData, pathData2, f)) {
                throw new java.lang.IllegalArgumentException("Can't interpolate between two incompatible pathData");
            }
            return this.mPathData;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static android.animation.PropertyValuesHolder getPVH(android.content.res.TypedArray typedArray, int i, int i2, int i3, java.lang.String str) {
        android.animation.ArgbEvaluator argbEvaluator;
        int i4;
        int i5;
        int i6;
        float f;
        float f2;
        float f3;
        android.util.TypedValue peekValue = typedArray.peekValue(i2);
        byte b = peekValue != null;
        int i7 = b != false ? peekValue.type : 0;
        android.util.TypedValue peekValue2 = typedArray.peekValue(i3);
        byte b2 = peekValue2 != null;
        int i8 = b2 != false ? peekValue2.type : 0;
        if (i == 4) {
            if ((b != false && isColorType(i7)) || (b2 != false && isColorType(i8))) {
                i = 3;
            } else {
                i = 0;
            }
        }
        byte b3 = i == 0;
        android.animation.PropertyValuesHolder propertyValuesHolder = null;
        byte b4 = 0;
        byte b5 = 0;
        if (i == 2) {
            java.lang.String string = typedArray.getString(i2);
            java.lang.String string2 = typedArray.getString(i3);
            android.util.PathParser.PathData pathData = string == null ? null : new android.util.PathParser.PathData(string);
            android.util.PathParser.PathData pathData2 = string2 == null ? null : new android.util.PathParser.PathData(string2);
            if (pathData == null && pathData2 == null) {
                return null;
            }
            if (pathData == null) {
                if (pathData2 != null) {
                    return android.animation.PropertyValuesHolder.ofObject(str, new android.animation.AnimatorInflater.PathDataEvaluator(), pathData2);
                }
                return null;
            }
            android.animation.AnimatorInflater.PathDataEvaluator pathDataEvaluator = new android.animation.AnimatorInflater.PathDataEvaluator();
            if (pathData2 != null) {
                if (!android.util.PathParser.canMorph(pathData, pathData2)) {
                    throw new android.view.InflateException(" Can't morph from " + string + " to " + string2);
                }
                return android.animation.PropertyValuesHolder.ofObject(str, pathDataEvaluator, pathData, pathData2);
            }
            return android.animation.PropertyValuesHolder.ofObject(str, pathDataEvaluator, pathData);
        }
        if (i != 3) {
            argbEvaluator = null;
        } else {
            argbEvaluator = android.animation.ArgbEvaluator.getInstance();
        }
        if (b3 != false) {
            if (b != false) {
                if (i7 == 5) {
                    f2 = typedArray.getDimension(i2, 0.0f);
                } else {
                    f2 = typedArray.getFloat(i2, 0.0f);
                }
                if (b2 != false) {
                    if (i8 == 5) {
                        f3 = typedArray.getDimension(i3, 0.0f);
                    } else {
                        f3 = typedArray.getFloat(i3, 0.0f);
                    }
                    propertyValuesHolder = android.animation.PropertyValuesHolder.ofFloat(str, f2, f3);
                } else {
                    propertyValuesHolder = android.animation.PropertyValuesHolder.ofFloat(str, f2);
                }
            } else {
                if (i8 == 5) {
                    f = typedArray.getDimension(i3, 0.0f);
                } else {
                    f = typedArray.getFloat(i3, 0.0f);
                }
                propertyValuesHolder = android.animation.PropertyValuesHolder.ofFloat(str, f);
            }
        } else if (b != false) {
            if (i7 == 5) {
                i5 = (int) typedArray.getDimension(i2, 0.0f);
            } else if (isColorType(i7)) {
                i5 = typedArray.getColor(i2, 0);
            } else {
                i5 = typedArray.getInt(i2, 0);
            }
            if (b2 != false) {
                if (i8 == 5) {
                    i6 = (int) typedArray.getDimension(i3, 0.0f);
                } else if (isColorType(i8)) {
                    i6 = typedArray.getColor(i3, 0);
                } else {
                    i6 = typedArray.getInt(i3, 0);
                }
                propertyValuesHolder = android.animation.PropertyValuesHolder.ofInt(str, i5, i6);
            } else {
                propertyValuesHolder = android.animation.PropertyValuesHolder.ofInt(str, i5);
            }
        } else if (b2 != false) {
            if (i8 == 5) {
                i4 = (int) typedArray.getDimension(i3, 0.0f);
            } else if (isColorType(i8)) {
                i4 = typedArray.getColor(i3, 0);
            } else {
                i4 = typedArray.getInt(i3, 0);
            }
            propertyValuesHolder = android.animation.PropertyValuesHolder.ofInt(str, i4);
        }
        if (propertyValuesHolder != null && argbEvaluator != null) {
            propertyValuesHolder.setEvaluator(argbEvaluator);
            return propertyValuesHolder;
        }
        return propertyValuesHolder;
    }

    private static void parseAnimatorFromTypeArray(android.animation.ValueAnimator valueAnimator, android.content.res.TypedArray typedArray, android.content.res.TypedArray typedArray2, float f) {
        long j = typedArray.getInt(1, 300);
        long j2 = typedArray.getInt(2, 0);
        int i = typedArray.getInt(7, 4);
        if (i == 4) {
            i = inferValueTypeFromValues(typedArray, 5, 6);
        }
        android.animation.PropertyValuesHolder pvh = getPVH(typedArray, i, 5, 6, "");
        if (pvh != null) {
            valueAnimator.setValues(pvh);
        }
        valueAnimator.setDuration(j);
        valueAnimator.setStartDelay(j2);
        if (typedArray.hasValue(3)) {
            valueAnimator.setRepeatCount(typedArray.getInt(3, 0));
        }
        if (typedArray.hasValue(4)) {
            valueAnimator.setRepeatMode(typedArray.getInt(4, 1));
        }
        if (typedArray2 != null) {
            setupObjectAnimator(valueAnimator, typedArray2, i, f);
        }
    }

    private static android.animation.TypeEvaluator setupAnimatorForPath(android.animation.ValueAnimator valueAnimator, android.content.res.TypedArray typedArray) {
        java.lang.String string = typedArray.getString(5);
        java.lang.String string2 = typedArray.getString(6);
        android.util.PathParser.PathData pathData = string == null ? null : new android.util.PathParser.PathData(string);
        android.util.PathParser.PathData pathData2 = string2 == null ? null : new android.util.PathParser.PathData(string2);
        if (pathData != null) {
            if (pathData2 != null) {
                valueAnimator.setObjectValues(pathData, pathData2);
                if (!android.util.PathParser.canMorph(pathData, pathData2)) {
                    throw new android.view.InflateException(typedArray.getPositionDescription() + " Can't morph from " + string + " to " + string2);
                }
            } else {
                valueAnimator.setObjectValues(pathData);
            }
            return new android.animation.AnimatorInflater.PathDataEvaluator();
        }
        if (pathData2 == null) {
            return null;
        }
        valueAnimator.setObjectValues(pathData2);
        return new android.animation.AnimatorInflater.PathDataEvaluator();
    }

    private static void setupObjectAnimator(android.animation.ValueAnimator valueAnimator, android.content.res.TypedArray typedArray, int i, float f) {
        android.animation.Keyframes createXIntKeyframes;
        android.animation.Keyframes createYIntKeyframes;
        android.animation.PropertyValuesHolder propertyValuesHolder;
        android.animation.ObjectAnimator objectAnimator = (android.animation.ObjectAnimator) valueAnimator;
        java.lang.String string = typedArray.getString(1);
        if (string != null) {
            java.lang.String string2 = typedArray.getString(2);
            java.lang.String string3 = typedArray.getString(3);
            if (i == 2 || i == 4) {
                i = 0;
            }
            if (string2 == null && string3 == null) {
                throw new android.view.InflateException(typedArray.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
            }
            android.animation.PathKeyframes ofPath = android.animation.KeyframeSet.ofPath(android.util.PathParser.createPathFromPathData(string), f * 0.5f);
            if (i == 0) {
                createXIntKeyframes = ofPath.createXFloatKeyframes();
                createYIntKeyframes = ofPath.createYFloatKeyframes();
            } else {
                createXIntKeyframes = ofPath.createXIntKeyframes();
                createYIntKeyframes = ofPath.createYIntKeyframes();
            }
            android.animation.PropertyValuesHolder propertyValuesHolder2 = null;
            if (string2 == null) {
                propertyValuesHolder = null;
            } else {
                propertyValuesHolder = android.animation.PropertyValuesHolder.ofKeyframes(string2, createXIntKeyframes);
            }
            if (string3 != null) {
                propertyValuesHolder2 = android.animation.PropertyValuesHolder.ofKeyframes(string3, createYIntKeyframes);
            }
            if (propertyValuesHolder == null) {
                objectAnimator.setValues(propertyValuesHolder2);
                return;
            } else if (propertyValuesHolder2 == null) {
                objectAnimator.setValues(propertyValuesHolder);
                return;
            } else {
                objectAnimator.setValues(propertyValuesHolder, propertyValuesHolder2);
                return;
            }
        }
        objectAnimator.setPropertyName(typedArray.getString(0));
    }

    private static void setupValues(android.animation.ValueAnimator valueAnimator, android.content.res.TypedArray typedArray, boolean z, boolean z2, int i, boolean z3, int i2) {
        int i3;
        int i4;
        int i5;
        float f;
        float f2;
        float f3;
        if (z) {
            if (z2) {
                if (i == 5) {
                    f2 = typedArray.getDimension(5, 0.0f);
                } else {
                    f2 = typedArray.getFloat(5, 0.0f);
                }
                if (z3) {
                    if (i2 == 5) {
                        f3 = typedArray.getDimension(6, 0.0f);
                    } else {
                        f3 = typedArray.getFloat(6, 0.0f);
                    }
                    valueAnimator.setFloatValues(f2, f3);
                    return;
                }
                valueAnimator.setFloatValues(f2);
                return;
            }
            if (i2 == 5) {
                f = typedArray.getDimension(6, 0.0f);
            } else {
                f = typedArray.getFloat(6, 0.0f);
            }
            valueAnimator.setFloatValues(f);
            return;
        }
        if (z2) {
            if (i == 5) {
                i4 = (int) typedArray.getDimension(5, 0.0f);
            } else if (isColorType(i)) {
                i4 = typedArray.getColor(5, 0);
            } else {
                i4 = typedArray.getInt(5, 0);
            }
            if (z3) {
                if (i2 == 5) {
                    i5 = (int) typedArray.getDimension(6, 0.0f);
                } else if (isColorType(i2)) {
                    i5 = typedArray.getColor(6, 0);
                } else {
                    i5 = typedArray.getInt(6, 0);
                }
                valueAnimator.setIntValues(i4, i5);
                return;
            }
            valueAnimator.setIntValues(i4);
            return;
        }
        if (z3) {
            if (i2 == 5) {
                i3 = (int) typedArray.getDimension(6, 0.0f);
            } else if (isColorType(i2)) {
                i3 = typedArray.getColor(6, 0);
            } else {
                i3 = typedArray.getInt(6, 0);
            }
            valueAnimator.setIntValues(i3);
        }
    }

    private static android.animation.Animator createAnimatorFromXml(android.content.res.Resources resources, android.content.res.Resources.Theme theme, org.xmlpull.v1.XmlPullParser xmlPullParser, float f) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return createAnimatorFromXml(resources, theme, xmlPullParser, android.util.Xml.asAttributeSet(xmlPullParser), null, 0, f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static android.animation.Animator createAnimatorFromXml(android.content.res.Resources resources, android.content.res.Resources.Theme theme, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.animation.AnimatorSet animatorSet, int i, float f) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i2;
        android.content.res.TypedArray obtainAttributes;
        int depth = xmlPullParser.getDepth();
        android.animation.ValueAnimator valueAnimator = null;
        java.util.ArrayList arrayList = null;
        while (true) {
            int next = xmlPullParser.next();
            i2 = 0;
            if ((next != 3 || xmlPullParser.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if (name.equals("objectAnimator")) {
                        valueAnimator = loadObjectAnimator(resources, theme, attributeSet, f);
                    } else if (name.equals("animator")) {
                        valueAnimator = loadAnimator(resources, theme, attributeSet, null, f);
                    } else if (name.equals("set")) {
                        android.animation.AnimatorSet animatorSet2 = new android.animation.AnimatorSet();
                        if (theme == null) {
                            obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AnimatorSet);
                        } else {
                            obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AnimatorSet, 0, 0);
                        }
                        animatorSet2.appendChangingConfigurations(obtainAttributes.getChangingConfigurations());
                        createAnimatorFromXml(resources, theme, xmlPullParser, attributeSet, animatorSet2, obtainAttributes.getInt(0, 0), f);
                        obtainAttributes.recycle();
                        valueAnimator = animatorSet2;
                    } else {
                        if (!name.equals("propertyValuesHolder")) {
                            throw new java.lang.RuntimeException("Unknown animator name: " + xmlPullParser.getName());
                        }
                        android.animation.PropertyValuesHolder[] loadValues = loadValues(resources, theme, xmlPullParser, android.util.Xml.asAttributeSet(xmlPullParser));
                        if (loadValues != null && valueAnimator != null && (valueAnimator instanceof android.animation.ValueAnimator)) {
                            valueAnimator.setValues(loadValues);
                        }
                        i2 = 1;
                    }
                    if (animatorSet != null && i2 == 0) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(valueAnimator);
                    }
                }
            }
        }
        if (animatorSet != null && arrayList != null) {
            android.animation.Animator[] animatorArr = new android.animation.Animator[arrayList.size()];
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                animatorArr[i2] = (android.animation.Animator) it.next();
                i2++;
            }
            if (i == 0) {
                animatorSet.playTogether(animatorArr);
            } else {
                animatorSet.playSequentially(animatorArr);
            }
        }
        return valueAnimator;
    }

    private static android.animation.PropertyValuesHolder[] loadValues(android.content.res.Resources resources, android.content.res.Resources.Theme theme, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        android.content.res.TypedArray obtainAttributes;
        android.animation.PropertyValuesHolder[] propertyValuesHolderArr = null;
        java.util.ArrayList arrayList = null;
        while (true) {
            int eventType = xmlPullParser.getEventType();
            if (eventType == 3 || eventType == 1) {
                break;
            }
            if (eventType != 2) {
                xmlPullParser.next();
            } else {
                if (xmlPullParser.getName().equals("propertyValuesHolder")) {
                    if (theme != null) {
                        obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.PropertyValuesHolder, 0, 0);
                    } else {
                        obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.PropertyValuesHolder);
                    }
                    java.lang.String string = obtainAttributes.getString(3);
                    int i2 = obtainAttributes.getInt(2, 4);
                    android.animation.PropertyValuesHolder loadPvh = loadPvh(resources, theme, xmlPullParser, string, i2);
                    if (loadPvh == null) {
                        loadPvh = getPVH(obtainAttributes, i2, 0, 1, string);
                    }
                    if (loadPvh != null) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(loadPvh);
                    }
                    obtainAttributes.recycle();
                }
                xmlPullParser.next();
            }
        }
        if (arrayList != null) {
            int size = arrayList.size();
            propertyValuesHolderArr = new android.animation.PropertyValuesHolder[size];
            for (i = 0; i < size; i++) {
                propertyValuesHolderArr[i] = (android.animation.PropertyValuesHolder) arrayList.get(i);
            }
        }
        return propertyValuesHolderArr;
    }

    private static int inferValueTypeOfKeyframe(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes;
        int i = 0;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Keyframe, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.Keyframe);
        }
        android.util.TypedValue peekValue = obtainAttributes.peekValue(0);
        if ((peekValue != null) && isColorType(peekValue.type)) {
            i = 3;
        }
        obtainAttributes.recycle();
        return i;
    }

    private static int inferValueTypeFromValues(android.content.res.TypedArray typedArray, int i, int i2) {
        android.util.TypedValue peekValue = typedArray.peekValue(i);
        boolean z = peekValue != null;
        int i3 = z ? peekValue.type : 0;
        android.util.TypedValue peekValue2 = typedArray.peekValue(i2);
        boolean z2 = peekValue2 != null;
        return ((z && isColorType(i3)) || (z2 && isColorType(z2 ? peekValue2.type : 0))) ? 3 : 0;
    }

    private static void dumpKeyframes(java.lang.Object[] objArr, java.lang.String str) {
        if (objArr == null || objArr.length == 0) {
            return;
        }
        android.util.Log.d(TAG, str);
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            android.animation.Keyframe keyframe = (android.animation.Keyframe) objArr[i];
            java.lang.Object obj = "null";
            java.lang.StringBuilder append = new java.lang.StringBuilder().append("Keyframe ").append(i).append(": fraction ").append(keyframe.getFraction() < 0.0f ? "null" : java.lang.Float.valueOf(keyframe.getFraction())).append(", , value : ");
            if (keyframe.hasValue()) {
                obj = keyframe.getValue();
            }
            android.util.Log.d(TAG, append.append(obj).toString());
        }
    }

    private static android.animation.PropertyValuesHolder loadPvh(android.content.res.Resources resources, android.content.res.Resources.Theme theme, org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int size;
        android.animation.PropertyValuesHolder propertyValuesHolder = null;
        java.util.ArrayList arrayList = null;
        while (true) {
            int next = xmlPullParser.next();
            if (next == 3 || next == 1) {
                break;
            }
            if (xmlPullParser.getName().equals("keyframe")) {
                if (i == 4) {
                    i = inferValueTypeOfKeyframe(resources, theme, android.util.Xml.asAttributeSet(xmlPullParser));
                }
                android.animation.Keyframe loadKeyframe = loadKeyframe(resources, theme, android.util.Xml.asAttributeSet(xmlPullParser), i);
                if (loadKeyframe != null) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(loadKeyframe);
                }
                xmlPullParser.next();
            }
        }
        if (arrayList != null && (size = arrayList.size()) > 0) {
            android.animation.Keyframe keyframe = (android.animation.Keyframe) arrayList.get(0);
            android.animation.Keyframe keyframe2 = (android.animation.Keyframe) arrayList.get(size - 1);
            float fraction = keyframe2.getFraction();
            if (fraction < 1.0f) {
                if (fraction >= 0.0f) {
                    arrayList.add(arrayList.size(), createNewKeyframe(keyframe2, 1.0f));
                    size++;
                } else {
                    keyframe2.setFraction(1.0f);
                }
            }
            float fraction2 = keyframe.getFraction();
            if (fraction2 != 0.0f) {
                if (fraction2 >= 0.0f) {
                    arrayList.add(0, createNewKeyframe(keyframe, 0.0f));
                    size++;
                } else {
                    keyframe.setFraction(0.0f);
                }
            }
            android.animation.Keyframe[] keyframeArr = new android.animation.Keyframe[size];
            arrayList.toArray(keyframeArr);
            for (int i2 = 0; i2 < size; i2++) {
                android.animation.Keyframe keyframe3 = keyframeArr[i2];
                if (keyframe3.getFraction() < 0.0f) {
                    if (i2 == 0) {
                        keyframe3.setFraction(0.0f);
                    } else {
                        int i3 = size - 1;
                        if (i2 == i3) {
                            keyframe3.setFraction(1.0f);
                        } else {
                            int i4 = i2;
                            for (int i5 = i2 + 1; i5 < i3 && keyframeArr[i5].getFraction() < 0.0f; i5++) {
                                i4 = i5;
                            }
                            distributeKeyframes(keyframeArr, keyframeArr[i4 + 1].getFraction() - keyframeArr[i2 - 1].getFraction(), i2, i4);
                        }
                    }
                }
            }
            propertyValuesHolder = android.animation.PropertyValuesHolder.ofKeyframe(str, keyframeArr);
            if (i == 3) {
                propertyValuesHolder.setEvaluator(android.animation.ArgbEvaluator.getInstance());
            }
        }
        return propertyValuesHolder;
    }

    private static android.animation.Keyframe createNewKeyframe(android.animation.Keyframe keyframe, float f) {
        if (keyframe.getType() == java.lang.Float.TYPE) {
            return android.animation.Keyframe.ofFloat(f);
        }
        if (keyframe.getType() == java.lang.Integer.TYPE) {
            return android.animation.Keyframe.ofInt(f);
        }
        return android.animation.Keyframe.ofObject(f);
    }

    private static void distributeKeyframes(android.animation.Keyframe[] keyframeArr, float f, int i, int i2) {
        float f2 = f / ((i2 - i) + 2);
        while (i <= i2) {
            keyframeArr[i].setFraction(keyframeArr[i - 1].getFraction() + f2);
            i++;
        }
    }

    private static android.animation.Keyframe loadKeyframe(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes;
        boolean z;
        android.animation.Keyframe ofFloat;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Keyframe, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.Keyframe);
        }
        float f = obtainAttributes.getFloat(3, -1.0f);
        android.util.TypedValue peekValue = obtainAttributes.peekValue(0);
        if (peekValue == null) {
            z = false;
        } else {
            z = true;
        }
        if (i == 4) {
            if (z && isColorType(peekValue.type)) {
                i = 3;
            } else {
                i = 0;
            }
        }
        if (z) {
            switch (i) {
                case 0:
                    ofFloat = android.animation.Keyframe.ofFloat(f, obtainAttributes.getFloat(0, 0.0f));
                    break;
                case 1:
                case 3:
                    ofFloat = android.animation.Keyframe.ofInt(f, obtainAttributes.getInt(0, 0));
                    break;
                case 2:
                default:
                    ofFloat = null;
                    break;
            }
        } else {
            ofFloat = i == 0 ? android.animation.Keyframe.ofFloat(f) : android.animation.Keyframe.ofInt(f);
        }
        int resourceId = obtainAttributes.getResourceId(1, 0);
        if (resourceId > 0) {
            ofFloat.setInterpolator(android.view.animation.AnimationUtils.loadInterpolator(resources, theme, resourceId));
        }
        obtainAttributes.recycle();
        return ofFloat;
    }

    private static android.animation.ObjectAnimator loadObjectAnimator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet, float f) throws android.content.res.Resources.NotFoundException {
        android.animation.ObjectAnimator objectAnimator = new android.animation.ObjectAnimator();
        loadAnimator(resources, theme, attributeSet, objectAnimator, f);
        return objectAnimator;
    }

    private static android.animation.ValueAnimator loadAnimator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet, android.animation.ValueAnimator valueAnimator, float f) throws android.content.res.Resources.NotFoundException {
        android.content.res.TypedArray obtainAttributes;
        android.content.res.TypedArray typedArray;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Animator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.Animator);
        }
        if (valueAnimator == null) {
            typedArray = null;
        } else {
            if (theme != null) {
                typedArray = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.PropertyAnimator, 0, 0);
            } else {
                typedArray = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.PropertyAnimator);
            }
            valueAnimator.appendChangingConfigurations(typedArray.getChangingConfigurations());
        }
        if (valueAnimator == null) {
            valueAnimator = new android.animation.ValueAnimator();
        }
        valueAnimator.appendChangingConfigurations(obtainAttributes.getChangingConfigurations());
        parseAnimatorFromTypeArray(valueAnimator, obtainAttributes, typedArray, f);
        int resourceId = obtainAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            android.view.animation.Interpolator loadInterpolator = android.view.animation.AnimationUtils.loadInterpolator(resources, theme, resourceId);
            if (loadInterpolator instanceof android.view.animation.BaseInterpolator) {
                valueAnimator.appendChangingConfigurations(((android.view.animation.BaseInterpolator) loadInterpolator).getChangingConfiguration());
            }
            valueAnimator.setInterpolator(loadInterpolator);
        }
        obtainAttributes.recycle();
        if (typedArray != null) {
            typedArray.recycle();
        }
        return valueAnimator;
    }

    private static int getChangingConfigs(android.content.res.Resources resources, int i) {
        int i2;
        synchronized (sTmpTypedValue) {
            resources.getValue(i, sTmpTypedValue, true);
            i2 = sTmpTypedValue.changingConfigurations;
        }
        return i2;
    }

    private static boolean isColorType(int i) {
        return i >= 28 && i <= 31;
    }
}
