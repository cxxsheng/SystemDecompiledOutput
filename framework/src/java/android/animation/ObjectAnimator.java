package android.animation;

/* loaded from: classes.dex */
public final class ObjectAnimator extends android.animation.ValueAnimator {
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "ObjectAnimator";
    private boolean mAutoCancel = false;
    private android.util.Property mProperty;
    private java.lang.String mPropertyName;
    private java.lang.Object mTarget;

    public void setPropertyName(java.lang.String str) {
        if (this.mValues != null) {
            android.animation.PropertyValuesHolder propertyValuesHolder = this.mValues[0];
            java.lang.String propertyName = propertyValuesHolder.getPropertyName();
            propertyValuesHolder.setPropertyName(str);
            this.mValuesMap.remove(propertyName);
            this.mValuesMap.put(str, propertyValuesHolder);
        }
        this.mPropertyName = str;
        this.mInitialized = false;
    }

    public void setProperty(android.util.Property property) {
        if (this.mValues != null) {
            android.animation.PropertyValuesHolder propertyValuesHolder = this.mValues[0];
            java.lang.String propertyName = propertyValuesHolder.getPropertyName();
            propertyValuesHolder.setProperty(property);
            this.mValuesMap.remove(propertyName);
            this.mValuesMap.put(this.mPropertyName, propertyValuesHolder);
        }
        if (this.mProperty != null) {
            this.mPropertyName = property.getName();
        }
        this.mProperty = property;
        this.mInitialized = false;
    }

    public java.lang.String getPropertyName() {
        java.lang.String str;
        if (this.mPropertyName != null) {
            return this.mPropertyName;
        }
        if (this.mProperty != null) {
            return this.mProperty.getName();
        }
        java.lang.String str2 = null;
        if (this.mValues == null || this.mValues.length <= 0) {
            return null;
        }
        for (int i = 0; i < this.mValues.length; i++) {
            if (i == 0) {
                str = "";
            } else {
                str = str2 + ",";
            }
            str2 = str + this.mValues[i].getPropertyName();
        }
        return str2;
    }

    @Override // android.animation.ValueAnimator
    java.lang.String getNameForTrace() {
        return "animator:" + getPropertyName();
    }

    public ObjectAnimator() {
    }

    private ObjectAnimator(java.lang.Object obj, java.lang.String str) {
        setTarget(obj);
        setPropertyName(str);
    }

    private <T> ObjectAnimator(T t, android.util.Property<T, ?> property) {
        setTarget(t);
        setProperty(property);
    }

    public static android.animation.ObjectAnimator ofInt(java.lang.Object obj, java.lang.String str, int... iArr) {
        android.animation.ObjectAnimator objectAnimator = new android.animation.ObjectAnimator(obj, str);
        objectAnimator.setIntValues(iArr);
        return objectAnimator;
    }

    public static android.animation.ObjectAnimator ofInt(java.lang.Object obj, java.lang.String str, java.lang.String str2, android.graphics.Path path) {
        android.animation.PathKeyframes ofPath = android.animation.KeyframeSet.ofPath(path);
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofKeyframes(str, ofPath.createXIntKeyframes()), android.animation.PropertyValuesHolder.ofKeyframes(str2, ofPath.createYIntKeyframes()));
    }

    public static <T> android.animation.ObjectAnimator ofInt(T t, android.util.Property<T, java.lang.Integer> property, int... iArr) {
        android.animation.ObjectAnimator objectAnimator = new android.animation.ObjectAnimator(t, property);
        objectAnimator.setIntValues(iArr);
        return objectAnimator;
    }

    public static <T> android.animation.ObjectAnimator ofInt(T t, android.util.Property<T, java.lang.Integer> property, android.util.Property<T, java.lang.Integer> property2, android.graphics.Path path) {
        android.animation.PathKeyframes ofPath = android.animation.KeyframeSet.ofPath(path);
        return ofPropertyValuesHolder(t, android.animation.PropertyValuesHolder.ofKeyframes(property, ofPath.createXIntKeyframes()), android.animation.PropertyValuesHolder.ofKeyframes(property2, ofPath.createYIntKeyframes()));
    }

    public static android.animation.ObjectAnimator ofMultiInt(java.lang.Object obj, java.lang.String str, int[][] iArr) {
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofMultiInt(str, iArr));
    }

    public static android.animation.ObjectAnimator ofMultiInt(java.lang.Object obj, java.lang.String str, android.graphics.Path path) {
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofMultiInt(str, path));
    }

    @java.lang.SafeVarargs
    public static <T> android.animation.ObjectAnimator ofMultiInt(java.lang.Object obj, java.lang.String str, android.animation.TypeConverter<T, int[]> typeConverter, android.animation.TypeEvaluator<T> typeEvaluator, T... tArr) {
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofMultiInt(str, typeConverter, typeEvaluator, tArr));
    }

    public static android.animation.ObjectAnimator ofArgb(java.lang.Object obj, java.lang.String str, int... iArr) {
        android.animation.ObjectAnimator ofInt = ofInt(obj, str, iArr);
        ofInt.setEvaluator(android.animation.ArgbEvaluator.getInstance());
        return ofInt;
    }

    public static <T> android.animation.ObjectAnimator ofArgb(T t, android.util.Property<T, java.lang.Integer> property, int... iArr) {
        android.animation.ObjectAnimator ofInt = ofInt(t, property, iArr);
        ofInt.setEvaluator(android.animation.ArgbEvaluator.getInstance());
        return ofInt;
    }

    public static android.animation.ObjectAnimator ofFloat(java.lang.Object obj, java.lang.String str, float... fArr) {
        android.animation.ObjectAnimator objectAnimator = new android.animation.ObjectAnimator(obj, str);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    public static android.animation.ObjectAnimator ofFloat(java.lang.Object obj, java.lang.String str, java.lang.String str2, android.graphics.Path path) {
        android.animation.PathKeyframes ofPath = android.animation.KeyframeSet.ofPath(path);
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofKeyframes(str, ofPath.createXFloatKeyframes()), android.animation.PropertyValuesHolder.ofKeyframes(str2, ofPath.createYFloatKeyframes()));
    }

    public static <T> android.animation.ObjectAnimator ofFloat(T t, android.util.Property<T, java.lang.Float> property, float... fArr) {
        android.animation.ObjectAnimator objectAnimator = new android.animation.ObjectAnimator(t, property);
        objectAnimator.setFloatValues(fArr);
        return objectAnimator;
    }

    public static <T> android.animation.ObjectAnimator ofFloat(T t, android.util.Property<T, java.lang.Float> property, android.util.Property<T, java.lang.Float> property2, android.graphics.Path path) {
        android.animation.PathKeyframes ofPath = android.animation.KeyframeSet.ofPath(path);
        return ofPropertyValuesHolder(t, android.animation.PropertyValuesHolder.ofKeyframes(property, ofPath.createXFloatKeyframes()), android.animation.PropertyValuesHolder.ofKeyframes(property2, ofPath.createYFloatKeyframes()));
    }

    public static android.animation.ObjectAnimator ofMultiFloat(java.lang.Object obj, java.lang.String str, float[][] fArr) {
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofMultiFloat(str, fArr));
    }

    public static android.animation.ObjectAnimator ofMultiFloat(java.lang.Object obj, java.lang.String str, android.graphics.Path path) {
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofMultiFloat(str, path));
    }

    @java.lang.SafeVarargs
    public static <T> android.animation.ObjectAnimator ofMultiFloat(java.lang.Object obj, java.lang.String str, android.animation.TypeConverter<T, float[]> typeConverter, android.animation.TypeEvaluator<T> typeEvaluator, T... tArr) {
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofMultiFloat(str, typeConverter, typeEvaluator, tArr));
    }

    public static android.animation.ObjectAnimator ofObject(java.lang.Object obj, java.lang.String str, android.animation.TypeEvaluator typeEvaluator, java.lang.Object... objArr) {
        android.animation.ObjectAnimator objectAnimator = new android.animation.ObjectAnimator(obj, str);
        objectAnimator.setObjectValues(objArr);
        objectAnimator.setEvaluator(typeEvaluator);
        return objectAnimator;
    }

    public static android.animation.ObjectAnimator ofObject(java.lang.Object obj, java.lang.String str, android.animation.TypeConverter<android.graphics.PointF, ?> typeConverter, android.graphics.Path path) {
        return ofPropertyValuesHolder(obj, android.animation.PropertyValuesHolder.ofObject(str, typeConverter, path));
    }

    @java.lang.SafeVarargs
    public static <T, V> android.animation.ObjectAnimator ofObject(T t, android.util.Property<T, V> property, android.animation.TypeEvaluator<V> typeEvaluator, V... vArr) {
        android.animation.ObjectAnimator objectAnimator = new android.animation.ObjectAnimator(t, property);
        objectAnimator.setObjectValues(vArr);
        objectAnimator.setEvaluator(typeEvaluator);
        return objectAnimator;
    }

    @java.lang.SafeVarargs
    public static <T, V, P> android.animation.ObjectAnimator ofObject(T t, android.util.Property<T, P> property, android.animation.TypeConverter<V, P> typeConverter, android.animation.TypeEvaluator<V> typeEvaluator, V... vArr) {
        return ofPropertyValuesHolder(t, android.animation.PropertyValuesHolder.ofObject(property, typeConverter, typeEvaluator, vArr));
    }

    public static <T, V> android.animation.ObjectAnimator ofObject(T t, android.util.Property<T, V> property, android.animation.TypeConverter<android.graphics.PointF, V> typeConverter, android.graphics.Path path) {
        return ofPropertyValuesHolder(t, android.animation.PropertyValuesHolder.ofObject(property, typeConverter, path));
    }

    public static android.animation.ObjectAnimator ofPropertyValuesHolder(java.lang.Object obj, android.animation.PropertyValuesHolder... propertyValuesHolderArr) {
        android.animation.ObjectAnimator objectAnimator = new android.animation.ObjectAnimator();
        objectAnimator.setTarget(obj);
        objectAnimator.setValues(propertyValuesHolderArr);
        return objectAnimator;
    }

    @Override // android.animation.ValueAnimator
    public void setIntValues(int... iArr) {
        if (this.mValues == null || this.mValues.length == 0) {
            if (this.mProperty != null) {
                setValues(android.animation.PropertyValuesHolder.ofInt((android.util.Property<?, java.lang.Integer>) this.mProperty, iArr));
                return;
            } else {
                setValues(android.animation.PropertyValuesHolder.ofInt(this.mPropertyName, iArr));
                return;
            }
        }
        super.setIntValues(iArr);
    }

    @Override // android.animation.ValueAnimator
    public void setFloatValues(float... fArr) {
        if (this.mValues == null || this.mValues.length == 0) {
            if (this.mProperty != null) {
                setValues(android.animation.PropertyValuesHolder.ofFloat((android.util.Property<?, java.lang.Float>) this.mProperty, fArr));
                return;
            } else {
                setValues(android.animation.PropertyValuesHolder.ofFloat(this.mPropertyName, fArr));
                return;
            }
        }
        super.setFloatValues(fArr);
    }

    @Override // android.animation.ValueAnimator
    public void setObjectValues(java.lang.Object... objArr) {
        if (this.mValues == null || this.mValues.length == 0) {
            if (this.mProperty != null) {
                setValues(android.animation.PropertyValuesHolder.ofObject(this.mProperty, (android.animation.TypeEvaluator) null, objArr));
                return;
            } else {
                setValues(android.animation.PropertyValuesHolder.ofObject(this.mPropertyName, (android.animation.TypeEvaluator) null, objArr));
                return;
            }
        }
        super.setObjectValues(objArr);
    }

    public void setAutoCancel(boolean z) {
        this.mAutoCancel = z;
    }

    private boolean hasSameTargetAndProperties(android.animation.Animator animator) {
        if (animator instanceof android.animation.ObjectAnimator) {
            android.animation.ObjectAnimator objectAnimator = (android.animation.ObjectAnimator) animator;
            android.animation.PropertyValuesHolder[] values = objectAnimator.getValues();
            if (objectAnimator.getTarget() == getTarget() && this.mValues.length == values.length) {
                for (int i = 0; i < this.mValues.length; i++) {
                    android.animation.PropertyValuesHolder propertyValuesHolder = this.mValues[i];
                    android.animation.PropertyValuesHolder propertyValuesHolder2 = values[i];
                    if (propertyValuesHolder.getPropertyName() == null || !propertyValuesHolder.getPropertyName().equals(propertyValuesHolder2.getPropertyName())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public void start() {
        android.animation.AnimationHandler.getInstance().autoCancelBasedOn(this);
        super.start();
    }

    boolean shouldAutoCancel(android.animation.AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        if (animationFrameCallback != null && (animationFrameCallback instanceof android.animation.ObjectAnimator)) {
            android.animation.ObjectAnimator objectAnimator = (android.animation.ObjectAnimator) animationFrameCallback;
            if (objectAnimator.mAutoCancel && hasSameTargetAndProperties(objectAnimator)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.animation.ValueAnimator
    void initAnimation() {
        if (!this.mInitialized) {
            java.lang.Object target = getTarget();
            if (target != null) {
                int length = this.mValues.length;
                for (int i = 0; i < length; i++) {
                    this.mValues[i].setupSetterAndGetter(target);
                }
            }
            super.initAnimation();
        }
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public android.animation.ObjectAnimator setDuration(long j) {
        super.setDuration(j);
        return this;
    }

    public java.lang.Object getTarget() {
        return this.mTarget;
    }

    @Override // android.animation.Animator
    public void setTarget(java.lang.Object obj) {
        if (getTarget() != obj) {
            if (isStarted()) {
                cancel();
            }
            this.mTarget = obj;
            this.mInitialized = false;
        }
    }

    @Override // android.animation.Animator
    public void setupStartValues() {
        initAnimation();
        java.lang.Object target = getTarget();
        if (target != null) {
            int length = this.mValues.length;
            for (int i = 0; i < length; i++) {
                this.mValues[i].setupStartValue(target);
            }
        }
    }

    @Override // android.animation.Animator
    public void setupEndValues() {
        initAnimation();
        java.lang.Object target = getTarget();
        if (target != null) {
            int length = this.mValues.length;
            for (int i = 0; i < length; i++) {
                this.mValues[i].setupEndValue(target);
            }
        }
    }

    @Override // android.animation.ValueAnimator
    void animateValue(float f) {
        java.lang.Object target = getTarget();
        super.animateValue(f);
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].setAnimatedValue(target);
        }
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    boolean isInitialized() {
        return this.mInitialized;
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    /* renamed from: clone */
    public android.animation.ObjectAnimator mo77clone() {
        return (android.animation.ObjectAnimator) super.mo77clone();
    }

    @Override // android.animation.ValueAnimator
    public java.lang.String toString() {
        java.lang.String str = "ObjectAnimator@" + java.lang.Integer.toHexString(hashCode()) + ", target " + getTarget();
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                str = str + "\n    " + this.mValues[i].toString();
            }
        }
        return str;
    }
}
