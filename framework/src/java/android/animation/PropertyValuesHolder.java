package android.animation;

/* loaded from: classes.dex */
public class PropertyValuesHolder implements java.lang.Cloneable {
    private java.lang.Object mAnimatedValue;
    private android.animation.TypeConverter mConverter;
    private android.animation.TypeEvaluator mEvaluator;
    private java.lang.reflect.Method mGetter;
    android.animation.Keyframes mKeyframes;
    protected android.util.Property mProperty;
    java.lang.String mPropertyName;
    java.lang.reflect.Method mSetter;
    final java.lang.Object[] mTmpValueArray;
    java.lang.Class mValueType;
    private static final android.animation.TypeEvaluator sIntEvaluator = new android.animation.IntEvaluator();
    private static final android.animation.TypeEvaluator sFloatEvaluator = new android.animation.FloatEvaluator();
    private static java.lang.Class[] FLOAT_VARIANTS = {java.lang.Float.TYPE, java.lang.Float.class, java.lang.Double.TYPE, java.lang.Integer.TYPE, java.lang.Double.class, java.lang.Integer.class};
    private static java.lang.Class[] INTEGER_VARIANTS = {java.lang.Integer.TYPE, java.lang.Integer.class, java.lang.Float.TYPE, java.lang.Double.TYPE, java.lang.Float.class, java.lang.Double.class};
    private static java.lang.Class[] DOUBLE_VARIANTS = {java.lang.Double.TYPE, java.lang.Double.class, java.lang.Float.TYPE, java.lang.Integer.TYPE, java.lang.Float.class, java.lang.Integer.class};
    private static final java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.reflect.Method>> sSetterPropertyMap = new java.util.HashMap<>();
    private static final java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.reflect.Method>> sGetterPropertyMap = new java.util.HashMap<>();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallFloatMethod(java.lang.Object obj, long j, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallFourFloatMethod(java.lang.Object obj, long j, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallFourIntMethod(java.lang.Object obj, long j, int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallIntMethod(java.lang.Object obj, long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallMultipleFloatMethod(java.lang.Object obj, long j, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallMultipleIntMethod(java.lang.Object obj, long j, int[] iArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallTwoFloatMethod(java.lang.Object obj, long j, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nCallTwoIntMethod(java.lang.Object obj, long j, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetFloatMethod(java.lang.Class cls, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetIntMethod(java.lang.Class cls, java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetMultipleFloatMethod(java.lang.Class cls, java.lang.String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetMultipleIntMethod(java.lang.Class cls, java.lang.String str, int i);

    private PropertyValuesHolder(java.lang.String str) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new java.lang.Object[1];
        this.mPropertyName = str;
    }

    private PropertyValuesHolder(android.util.Property property) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new java.lang.Object[1];
        this.mProperty = property;
        if (property != null) {
            this.mPropertyName = property.getName();
        }
    }

    public static android.animation.PropertyValuesHolder ofInt(java.lang.String str, int... iArr) {
        return new android.animation.PropertyValuesHolder.IntPropertyValuesHolder(str, iArr);
    }

    public static android.animation.PropertyValuesHolder ofInt(android.util.Property<?, java.lang.Integer> property, int... iArr) {
        return new android.animation.PropertyValuesHolder.IntPropertyValuesHolder(property, iArr);
    }

    public static android.animation.PropertyValuesHolder ofMultiInt(java.lang.String str, int[][] iArr) {
        if (iArr.length < 2) {
            throw new java.lang.IllegalArgumentException("At least 2 values must be supplied");
        }
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] == null) {
                throw new java.lang.IllegalArgumentException("values must not be null");
            }
            int length = iArr[i2].length;
            if (i2 == 0) {
                i = length;
            } else if (length != i) {
                throw new java.lang.IllegalArgumentException("Values must all have the same length");
            }
        }
        return new android.animation.PropertyValuesHolder.MultiIntValuesHolder(str, (android.animation.TypeConverter) null, new android.animation.IntArrayEvaluator(new int[i]), iArr);
    }

    public static android.animation.PropertyValuesHolder ofMultiInt(java.lang.String str, android.graphics.Path path) {
        return new android.animation.PropertyValuesHolder.MultiIntValuesHolder(str, new android.animation.PropertyValuesHolder.PointFToIntArray(), (android.animation.TypeEvaluator) null, android.animation.KeyframeSet.ofPath(path));
    }

    @java.lang.SafeVarargs
    public static <V> android.animation.PropertyValuesHolder ofMultiInt(java.lang.String str, android.animation.TypeConverter<V, int[]> typeConverter, android.animation.TypeEvaluator<V> typeEvaluator, V... vArr) {
        return new android.animation.PropertyValuesHolder.MultiIntValuesHolder(str, typeConverter, typeEvaluator, vArr);
    }

    public static <T> android.animation.PropertyValuesHolder ofMultiInt(java.lang.String str, android.animation.TypeConverter<T, int[]> typeConverter, android.animation.TypeEvaluator<T> typeEvaluator, android.animation.Keyframe... keyframeArr) {
        return new android.animation.PropertyValuesHolder.MultiIntValuesHolder(str, typeConverter, typeEvaluator, android.animation.KeyframeSet.ofKeyframe(keyframeArr));
    }

    public static android.animation.PropertyValuesHolder ofFloat(java.lang.String str, float... fArr) {
        return new android.animation.PropertyValuesHolder.FloatPropertyValuesHolder(str, fArr);
    }

    public static android.animation.PropertyValuesHolder ofFloat(android.util.Property<?, java.lang.Float> property, float... fArr) {
        return new android.animation.PropertyValuesHolder.FloatPropertyValuesHolder(property, fArr);
    }

    public static android.animation.PropertyValuesHolder ofMultiFloat(java.lang.String str, float[][] fArr) {
        if (fArr.length < 2) {
            throw new java.lang.IllegalArgumentException("At least 2 values must be supplied");
        }
        int i = 0;
        for (int i2 = 0; i2 < fArr.length; i2++) {
            if (fArr[i2] == null) {
                throw new java.lang.IllegalArgumentException("values must not be null");
            }
            int length = fArr[i2].length;
            if (i2 == 0) {
                i = length;
            } else if (length != i) {
                throw new java.lang.IllegalArgumentException("Values must all have the same length");
            }
        }
        return new android.animation.PropertyValuesHolder.MultiFloatValuesHolder(str, (android.animation.TypeConverter) null, new android.animation.FloatArrayEvaluator(new float[i]), fArr);
    }

    public static android.animation.PropertyValuesHolder ofMultiFloat(java.lang.String str, android.graphics.Path path) {
        return new android.animation.PropertyValuesHolder.MultiFloatValuesHolder(str, new android.animation.PropertyValuesHolder.PointFToFloatArray(), (android.animation.TypeEvaluator) null, android.animation.KeyframeSet.ofPath(path));
    }

    @java.lang.SafeVarargs
    public static <V> android.animation.PropertyValuesHolder ofMultiFloat(java.lang.String str, android.animation.TypeConverter<V, float[]> typeConverter, android.animation.TypeEvaluator<V> typeEvaluator, V... vArr) {
        return new android.animation.PropertyValuesHolder.MultiFloatValuesHolder(str, typeConverter, typeEvaluator, vArr);
    }

    public static <T> android.animation.PropertyValuesHolder ofMultiFloat(java.lang.String str, android.animation.TypeConverter<T, float[]> typeConverter, android.animation.TypeEvaluator<T> typeEvaluator, android.animation.Keyframe... keyframeArr) {
        return new android.animation.PropertyValuesHolder.MultiFloatValuesHolder(str, typeConverter, typeEvaluator, android.animation.KeyframeSet.ofKeyframe(keyframeArr));
    }

    public static android.animation.PropertyValuesHolder ofObject(java.lang.String str, android.animation.TypeEvaluator typeEvaluator, java.lang.Object... objArr) {
        android.animation.PropertyValuesHolder propertyValuesHolder = new android.animation.PropertyValuesHolder(str);
        propertyValuesHolder.setObjectValues(objArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    public static android.animation.PropertyValuesHolder ofObject(java.lang.String str, android.animation.TypeConverter<android.graphics.PointF, ?> typeConverter, android.graphics.Path path) {
        android.animation.PropertyValuesHolder propertyValuesHolder = new android.animation.PropertyValuesHolder(str);
        propertyValuesHolder.mKeyframes = android.animation.KeyframeSet.ofPath(path);
        propertyValuesHolder.mValueType = android.graphics.PointF.class;
        propertyValuesHolder.setConverter(typeConverter);
        return propertyValuesHolder;
    }

    @java.lang.SafeVarargs
    public static <V> android.animation.PropertyValuesHolder ofObject(android.util.Property property, android.animation.TypeEvaluator<V> typeEvaluator, V... vArr) {
        android.animation.PropertyValuesHolder propertyValuesHolder = new android.animation.PropertyValuesHolder(property);
        propertyValuesHolder.setObjectValues(vArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    @java.lang.SafeVarargs
    public static <T, V> android.animation.PropertyValuesHolder ofObject(android.util.Property<?, V> property, android.animation.TypeConverter<T, V> typeConverter, android.animation.TypeEvaluator<T> typeEvaluator, T... tArr) {
        android.animation.PropertyValuesHolder propertyValuesHolder = new android.animation.PropertyValuesHolder(property);
        propertyValuesHolder.setConverter(typeConverter);
        propertyValuesHolder.setObjectValues(tArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    public static <V> android.animation.PropertyValuesHolder ofObject(android.util.Property<?, V> property, android.animation.TypeConverter<android.graphics.PointF, V> typeConverter, android.graphics.Path path) {
        android.animation.PropertyValuesHolder propertyValuesHolder = new android.animation.PropertyValuesHolder(property);
        propertyValuesHolder.mKeyframes = android.animation.KeyframeSet.ofPath(path);
        propertyValuesHolder.mValueType = android.graphics.PointF.class;
        propertyValuesHolder.setConverter(typeConverter);
        return propertyValuesHolder;
    }

    public static android.animation.PropertyValuesHolder ofKeyframe(java.lang.String str, android.animation.Keyframe... keyframeArr) {
        return ofKeyframes(str, android.animation.KeyframeSet.ofKeyframe(keyframeArr));
    }

    public static android.animation.PropertyValuesHolder ofKeyframe(android.util.Property property, android.animation.Keyframe... keyframeArr) {
        return ofKeyframes(property, android.animation.KeyframeSet.ofKeyframe(keyframeArr));
    }

    static android.animation.PropertyValuesHolder ofKeyframes(java.lang.String str, android.animation.Keyframes keyframes) {
        if (keyframes instanceof android.animation.Keyframes.IntKeyframes) {
            return new android.animation.PropertyValuesHolder.IntPropertyValuesHolder(str, (android.animation.Keyframes.IntKeyframes) keyframes);
        }
        if (keyframes instanceof android.animation.Keyframes.FloatKeyframes) {
            return new android.animation.PropertyValuesHolder.FloatPropertyValuesHolder(str, (android.animation.Keyframes.FloatKeyframes) keyframes);
        }
        android.animation.PropertyValuesHolder propertyValuesHolder = new android.animation.PropertyValuesHolder(str);
        propertyValuesHolder.mKeyframes = keyframes;
        propertyValuesHolder.mValueType = keyframes.getType();
        return propertyValuesHolder;
    }

    static android.animation.PropertyValuesHolder ofKeyframes(android.util.Property property, android.animation.Keyframes keyframes) {
        if (keyframes instanceof android.animation.Keyframes.IntKeyframes) {
            return new android.animation.PropertyValuesHolder.IntPropertyValuesHolder(property, (android.animation.Keyframes.IntKeyframes) keyframes);
        }
        if (keyframes instanceof android.animation.Keyframes.FloatKeyframes) {
            return new android.animation.PropertyValuesHolder.FloatPropertyValuesHolder(property, (android.animation.Keyframes.FloatKeyframes) keyframes);
        }
        android.animation.PropertyValuesHolder propertyValuesHolder = new android.animation.PropertyValuesHolder(property);
        propertyValuesHolder.mKeyframes = keyframes;
        propertyValuesHolder.mValueType = keyframes.getType();
        return propertyValuesHolder;
    }

    public void setIntValues(int... iArr) {
        this.mValueType = java.lang.Integer.TYPE;
        this.mKeyframes = android.animation.KeyframeSet.ofInt(iArr);
    }

    public void setFloatValues(float... fArr) {
        this.mValueType = java.lang.Float.TYPE;
        this.mKeyframes = android.animation.KeyframeSet.ofFloat(fArr);
    }

    public void setKeyframes(android.animation.Keyframe... keyframeArr) {
        int length = keyframeArr.length;
        android.animation.Keyframe[] keyframeArr2 = new android.animation.Keyframe[java.lang.Math.max(length, 2)];
        this.mValueType = keyframeArr[0].getType();
        for (int i = 0; i < length; i++) {
            keyframeArr2[i] = keyframeArr[i];
        }
        this.mKeyframes = new android.animation.KeyframeSet(keyframeArr2);
    }

    public void setObjectValues(java.lang.Object... objArr) {
        this.mValueType = objArr[0].getClass();
        this.mKeyframes = android.animation.KeyframeSet.ofObject(objArr);
        if (this.mEvaluator != null) {
            this.mKeyframes.setEvaluator(this.mEvaluator);
        }
    }

    public void setConverter(android.animation.TypeConverter typeConverter) {
        this.mConverter = typeConverter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private java.lang.reflect.Method getPropertyFunction(java.lang.Class cls, java.lang.String str, java.lang.Class cls2) {
        java.lang.Class[] clsArr;
        java.lang.String methodName = getMethodName(str, this.mPropertyName);
        java.lang.reflect.Method method = null;
        if (cls2 == null) {
            try {
                method = cls.getMethod(methodName, null);
            } catch (java.lang.NoSuchMethodException e) {
            }
        } else {
            java.lang.Class[] clsArr2 = new java.lang.Class[1];
            if (cls2.equals(java.lang.Float.class)) {
                clsArr = FLOAT_VARIANTS;
            } else if (cls2.equals(java.lang.Integer.class)) {
                clsArr = INTEGER_VARIANTS;
            } else if (cls2.equals(java.lang.Double.class)) {
                clsArr = DOUBLE_VARIANTS;
            } else {
                clsArr = new java.lang.Class[]{cls2};
            }
            for (java.lang.Class cls3 : clsArr) {
                clsArr2[0] = cls3;
                try {
                    method = cls.getMethod(methodName, clsArr2);
                    if (this.mConverter == null) {
                        this.mValueType = cls3;
                    }
                    return method;
                } catch (java.lang.NoSuchMethodException e2) {
                }
            }
        }
        if (method == null) {
            android.util.Log.w("PropertyValuesHolder", "Method " + getMethodName(str, this.mPropertyName) + "() with type " + cls2 + " not found on target class " + cls);
        }
        return method;
    }

    private java.lang.reflect.Method setupSetterOrGetter(java.lang.Class cls, java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.reflect.Method>> hashMap, java.lang.String str, java.lang.Class cls2) {
        java.lang.reflect.Method method;
        boolean z;
        synchronized (hashMap) {
            java.util.HashMap<java.lang.String, java.lang.reflect.Method> hashMap2 = hashMap.get(cls);
            method = null;
            if (hashMap2 == null) {
                z = false;
            } else {
                z = hashMap2.containsKey(this.mPropertyName);
                if (z) {
                    method = hashMap2.get(this.mPropertyName);
                }
            }
            if (!z) {
                method = getPropertyFunction(cls, str, cls2);
                if (hashMap2 == null) {
                    hashMap2 = new java.util.HashMap<>();
                    hashMap.put(cls, hashMap2);
                }
                hashMap2.put(this.mPropertyName, method);
            }
        }
        return method;
    }

    void setupSetter(java.lang.Class cls) {
        this.mSetter = setupSetterOrGetter(cls, sSetterPropertyMap, "set", this.mConverter == null ? this.mValueType : this.mConverter.getTargetType());
    }

    private void setupGetter(java.lang.Class cls) {
        this.mGetter = setupSetterOrGetter(cls, sGetterPropertyMap, "get", null);
    }

    void setupSetterAndGetter(java.lang.Object obj) {
        if (this.mProperty != null) {
            try {
                java.util.List<android.animation.Keyframe> keyframes = this.mKeyframes.getKeyframes();
                int size = keyframes == null ? 0 : keyframes.size();
                java.lang.Object obj2 = null;
                for (int i = 0; i < size; i++) {
                    android.animation.Keyframe keyframe = keyframes.get(i);
                    if (!keyframe.hasValue() || keyframe.valueWasSetOnStart()) {
                        if (obj2 == null) {
                            obj2 = convertBack(this.mProperty.get(obj));
                        }
                        keyframe.setValue(obj2);
                        keyframe.setValueWasSetOnStart(true);
                    }
                }
                return;
            } catch (java.lang.ClassCastException e) {
                android.util.Log.w("PropertyValuesHolder", "No such property (" + this.mProperty.getName() + ") on target object " + obj + ". Trying reflection instead");
                this.mProperty = null;
            }
        }
        if (this.mProperty == null) {
            java.lang.Class<?> cls = obj.getClass();
            if (this.mSetter == null) {
                setupSetter(cls);
            }
            java.util.List<android.animation.Keyframe> keyframes2 = this.mKeyframes.getKeyframes();
            int size2 = keyframes2 == null ? 0 : keyframes2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                android.animation.Keyframe keyframe2 = keyframes2.get(i2);
                if (!keyframe2.hasValue() || keyframe2.valueWasSetOnStart()) {
                    if (this.mGetter == null) {
                        setupGetter(cls);
                        if (this.mGetter == null) {
                            return;
                        }
                    }
                    try {
                        keyframe2.setValue(convertBack(this.mGetter.invoke(obj, new java.lang.Object[0])));
                        keyframe2.setValueWasSetOnStart(true);
                    } catch (java.lang.IllegalAccessException e2) {
                        android.util.Log.e("PropertyValuesHolder", e2.toString());
                    } catch (java.lang.reflect.InvocationTargetException e3) {
                        android.util.Log.e("PropertyValuesHolder", e3.toString());
                    }
                }
            }
        }
    }

    private java.lang.Object convertBack(java.lang.Object obj) {
        if (this.mConverter != null) {
            if (!(this.mConverter instanceof android.animation.BidirectionalTypeConverter)) {
                throw new java.lang.IllegalArgumentException("Converter " + this.mConverter.getClass().getName() + " must be a BidirectionalTypeConverter");
            }
            return ((android.animation.BidirectionalTypeConverter) this.mConverter).convertBack(obj);
        }
        return obj;
    }

    private void setupValue(java.lang.Object obj, android.animation.Keyframe keyframe) {
        if (this.mProperty != null) {
            keyframe.setValue(convertBack(this.mProperty.get(obj)));
            return;
        }
        try {
            if (this.mGetter == null) {
                setupGetter(obj.getClass());
                if (this.mGetter == null) {
                    return;
                }
            }
            keyframe.setValue(convertBack(this.mGetter.invoke(obj, new java.lang.Object[0])));
        } catch (java.lang.IllegalAccessException e) {
            android.util.Log.e("PropertyValuesHolder", e.toString());
        } catch (java.lang.reflect.InvocationTargetException e2) {
            android.util.Log.e("PropertyValuesHolder", e2.toString());
        }
    }

    void setupStartValue(java.lang.Object obj) {
        java.util.List<android.animation.Keyframe> keyframes = this.mKeyframes.getKeyframes();
        if (!keyframes.isEmpty()) {
            setupValue(obj, keyframes.get(0));
        }
    }

    void setupEndValue(java.lang.Object obj) {
        java.util.List<android.animation.Keyframe> keyframes = this.mKeyframes.getKeyframes();
        if (!keyframes.isEmpty()) {
            setupValue(obj, keyframes.get(keyframes.size() - 1));
        }
    }

    @Override // 
    /* renamed from: clone */
    public android.animation.PropertyValuesHolder mo122clone() {
        try {
            android.animation.PropertyValuesHolder propertyValuesHolder = (android.animation.PropertyValuesHolder) super.clone();
            propertyValuesHolder.mPropertyName = this.mPropertyName;
            propertyValuesHolder.mProperty = this.mProperty;
            propertyValuesHolder.mKeyframes = this.mKeyframes.mo84clone();
            propertyValuesHolder.mEvaluator = this.mEvaluator;
            return propertyValuesHolder;
        } catch (java.lang.CloneNotSupportedException e) {
            return null;
        }
    }

    void setAnimatedValue(java.lang.Object obj) {
        if (this.mProperty != null) {
            this.mProperty.set(obj, getAnimatedValue());
        }
        if (this.mSetter != null) {
            try {
                this.mTmpValueArray[0] = getAnimatedValue();
                this.mSetter.invoke(obj, this.mTmpValueArray);
            } catch (java.lang.IllegalAccessException e) {
                android.util.Log.e("PropertyValuesHolder", e.toString());
            } catch (java.lang.reflect.InvocationTargetException e2) {
                android.util.Log.e("PropertyValuesHolder", e2.toString());
            }
        }
    }

    void init() {
        android.animation.TypeEvaluator typeEvaluator;
        if (this.mEvaluator == null) {
            if (this.mValueType == java.lang.Integer.class) {
                typeEvaluator = sIntEvaluator;
            } else {
                typeEvaluator = this.mValueType == java.lang.Float.class ? sFloatEvaluator : null;
            }
            this.mEvaluator = typeEvaluator;
        }
        if (this.mEvaluator != null) {
            this.mKeyframes.setEvaluator(this.mEvaluator);
        }
    }

    public void setEvaluator(android.animation.TypeEvaluator typeEvaluator) {
        this.mEvaluator = typeEvaluator;
        this.mKeyframes.setEvaluator(typeEvaluator);
    }

    void calculateValue(float f) {
        java.lang.Object value = this.mKeyframes.getValue(f);
        if (this.mConverter != null) {
            value = this.mConverter.convert(value);
        }
        this.mAnimatedValue = value;
    }

    public void setPropertyName(java.lang.String str) {
        this.mPropertyName = str;
    }

    public void setProperty(android.util.Property property) {
        this.mProperty = property;
    }

    public java.lang.String getPropertyName() {
        return this.mPropertyName;
    }

    java.lang.Object getAnimatedValue() {
        return this.mAnimatedValue;
    }

    public void getPropertyValues(android.animation.PropertyValuesHolder.PropertyValues propertyValues) {
        init();
        propertyValues.propertyName = this.mPropertyName;
        propertyValues.type = this.mValueType;
        propertyValues.startValue = this.mKeyframes.getValue(0.0f);
        if (propertyValues.startValue instanceof android.util.PathParser.PathData) {
            propertyValues.startValue = new android.util.PathParser.PathData((android.util.PathParser.PathData) propertyValues.startValue);
        }
        propertyValues.endValue = this.mKeyframes.getValue(1.0f);
        if (propertyValues.endValue instanceof android.util.PathParser.PathData) {
            propertyValues.endValue = new android.util.PathParser.PathData((android.util.PathParser.PathData) propertyValues.endValue);
        }
        if ((this.mKeyframes instanceof android.animation.PathKeyframes.FloatKeyframesBase) || (this.mKeyframes instanceof android.animation.PathKeyframes.IntKeyframesBase) || (this.mKeyframes.getKeyframes() != null && this.mKeyframes.getKeyframes().size() > 2)) {
            propertyValues.dataSource = new android.animation.PropertyValuesHolder.PropertyValues.DataSource() { // from class: android.animation.PropertyValuesHolder.1
                @Override // android.animation.PropertyValuesHolder.PropertyValues.DataSource
                public java.lang.Object getValueAtFraction(float f) {
                    return android.animation.PropertyValuesHolder.this.mKeyframes.getValue(f);
                }
            };
        } else {
            propertyValues.dataSource = null;
        }
    }

    public java.lang.Class getValueType() {
        return this.mValueType;
    }

    public java.lang.String toString() {
        return this.mPropertyName + ": " + this.mKeyframes.toString();
    }

    static java.lang.String getMethodName(java.lang.String str, java.lang.String str2) {
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        return str + java.lang.Character.toUpperCase(str2.charAt(0)) + str2.substring(1);
    }

    static class IntPropertyValuesHolder extends android.animation.PropertyValuesHolder {
        private static final java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> sJNISetterPropertyMap = new java.util.HashMap<>();
        int mIntAnimatedValue;
        android.animation.Keyframes.IntKeyframes mIntKeyframes;
        private android.util.IntProperty mIntProperty;
        long mJniSetter;

        public IntPropertyValuesHolder(java.lang.String str, android.animation.Keyframes.IntKeyframes intKeyframes) {
            super(str);
            this.mValueType = java.lang.Integer.TYPE;
            this.mKeyframes = intKeyframes;
            this.mIntKeyframes = intKeyframes;
        }

        public IntPropertyValuesHolder(android.util.Property property, android.animation.Keyframes.IntKeyframes intKeyframes) {
            super(property);
            this.mValueType = java.lang.Integer.TYPE;
            this.mKeyframes = intKeyframes;
            this.mIntKeyframes = intKeyframes;
            if (property instanceof android.util.IntProperty) {
                this.mIntProperty = (android.util.IntProperty) this.mProperty;
            }
        }

        public IntPropertyValuesHolder(java.lang.String str, int... iArr) {
            super(str);
            setIntValues(iArr);
        }

        public IntPropertyValuesHolder(android.util.Property property, int... iArr) {
            super(property);
            setIntValues(iArr);
            if (property instanceof android.util.IntProperty) {
                this.mIntProperty = (android.util.IntProperty) this.mProperty;
            }
        }

        @Override // android.animation.PropertyValuesHolder
        public void setProperty(android.util.Property property) {
            if (property instanceof android.util.IntProperty) {
                this.mIntProperty = (android.util.IntProperty) property;
            } else {
                super.setProperty(property);
            }
        }

        @Override // android.animation.PropertyValuesHolder
        public void setIntValues(int... iArr) {
            super.setIntValues(iArr);
            this.mIntKeyframes = (android.animation.Keyframes.IntKeyframes) this.mKeyframes;
        }

        @Override // android.animation.PropertyValuesHolder
        void calculateValue(float f) {
            this.mIntAnimatedValue = this.mIntKeyframes.getIntValue(f);
        }

        @Override // android.animation.PropertyValuesHolder
        java.lang.Object getAnimatedValue() {
            return java.lang.Integer.valueOf(this.mIntAnimatedValue);
        }

        @Override // android.animation.PropertyValuesHolder
        /* renamed from: clone */
        public android.animation.PropertyValuesHolder.IntPropertyValuesHolder mo122clone() {
            android.animation.PropertyValuesHolder.IntPropertyValuesHolder intPropertyValuesHolder = (android.animation.PropertyValuesHolder.IntPropertyValuesHolder) super.mo122clone();
            intPropertyValuesHolder.mIntKeyframes = (android.animation.Keyframes.IntKeyframes) intPropertyValuesHolder.mKeyframes;
            return intPropertyValuesHolder;
        }

        @Override // android.animation.PropertyValuesHolder
        void setAnimatedValue(java.lang.Object obj) {
            if (this.mIntProperty != null) {
                this.mIntProperty.setValue(obj, this.mIntAnimatedValue);
                return;
            }
            if (this.mProperty != null) {
                this.mProperty.set(obj, java.lang.Integer.valueOf(this.mIntAnimatedValue));
                return;
            }
            if (this.mJniSetter != 0) {
                android.animation.PropertyValuesHolder.nCallIntMethod(obj, this.mJniSetter, this.mIntAnimatedValue);
                return;
            }
            if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = java.lang.Integer.valueOf(this.mIntAnimatedValue);
                    this.mSetter.invoke(obj, this.mTmpValueArray);
                } catch (java.lang.IllegalAccessException e) {
                    android.util.Log.e("PropertyValuesHolder", e.toString());
                } catch (java.lang.reflect.InvocationTargetException e2) {
                    android.util.Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetter(java.lang.Class cls) {
            boolean z;
            java.lang.Long l;
            if (this.mProperty != null) {
                return;
            }
            synchronized (sJNISetterPropertyMap) {
                java.util.HashMap<java.lang.String, java.lang.Long> hashMap = sJNISetterPropertyMap.get(cls);
                if (hashMap == null) {
                    z = false;
                } else {
                    z = hashMap.containsKey(this.mPropertyName);
                    if (z && (l = hashMap.get(this.mPropertyName)) != null) {
                        this.mJniSetter = l.longValue();
                    }
                }
                if (!z) {
                    try {
                        this.mJniSetter = android.animation.PropertyValuesHolder.nGetIntMethod(cls, getMethodName("set", this.mPropertyName));
                    } catch (java.lang.NoSuchMethodError e) {
                    }
                    if (hashMap == null) {
                        hashMap = new java.util.HashMap<>();
                        sJNISetterPropertyMap.put(cls, hashMap);
                    }
                    hashMap.put(this.mPropertyName, java.lang.Long.valueOf(this.mJniSetter));
                }
            }
            if (this.mJniSetter == 0) {
                super.setupSetter(cls);
            }
        }
    }

    static class FloatPropertyValuesHolder extends android.animation.PropertyValuesHolder {
        private static final java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> sJNISetterPropertyMap = new java.util.HashMap<>();
        float mFloatAnimatedValue;
        android.animation.Keyframes.FloatKeyframes mFloatKeyframes;
        private android.util.FloatProperty mFloatProperty;
        long mJniSetter;

        public FloatPropertyValuesHolder(java.lang.String str, android.animation.Keyframes.FloatKeyframes floatKeyframes) {
            super(str);
            this.mValueType = java.lang.Float.TYPE;
            this.mKeyframes = floatKeyframes;
            this.mFloatKeyframes = floatKeyframes;
        }

        public FloatPropertyValuesHolder(android.util.Property property, android.animation.Keyframes.FloatKeyframes floatKeyframes) {
            super(property);
            this.mValueType = java.lang.Float.TYPE;
            this.mKeyframes = floatKeyframes;
            this.mFloatKeyframes = floatKeyframes;
            if (property instanceof android.util.FloatProperty) {
                this.mFloatProperty = (android.util.FloatProperty) this.mProperty;
            }
        }

        public FloatPropertyValuesHolder(java.lang.String str, float... fArr) {
            super(str);
            setFloatValues(fArr);
        }

        public FloatPropertyValuesHolder(android.util.Property property, float... fArr) {
            super(property);
            setFloatValues(fArr);
            if (property instanceof android.util.FloatProperty) {
                this.mFloatProperty = (android.util.FloatProperty) this.mProperty;
            }
        }

        @Override // android.animation.PropertyValuesHolder
        public void setProperty(android.util.Property property) {
            if (property instanceof android.util.FloatProperty) {
                this.mFloatProperty = (android.util.FloatProperty) property;
            } else {
                super.setProperty(property);
            }
        }

        @Override // android.animation.PropertyValuesHolder
        public void setFloatValues(float... fArr) {
            super.setFloatValues(fArr);
            this.mFloatKeyframes = (android.animation.Keyframes.FloatKeyframes) this.mKeyframes;
        }

        @Override // android.animation.PropertyValuesHolder
        void calculateValue(float f) {
            this.mFloatAnimatedValue = this.mFloatKeyframes.getFloatValue(f);
        }

        @Override // android.animation.PropertyValuesHolder
        java.lang.Object getAnimatedValue() {
            return java.lang.Float.valueOf(this.mFloatAnimatedValue);
        }

        @Override // android.animation.PropertyValuesHolder
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.animation.PropertyValuesHolder.FloatPropertyValuesHolder mo122clone() {
            android.animation.PropertyValuesHolder.FloatPropertyValuesHolder floatPropertyValuesHolder = (android.animation.PropertyValuesHolder.FloatPropertyValuesHolder) super.mo122clone();
            floatPropertyValuesHolder.mFloatKeyframes = (android.animation.Keyframes.FloatKeyframes) floatPropertyValuesHolder.mKeyframes;
            return floatPropertyValuesHolder;
        }

        @Override // android.animation.PropertyValuesHolder
        void setAnimatedValue(java.lang.Object obj) {
            if (this.mFloatProperty != null) {
                this.mFloatProperty.setValue(obj, this.mFloatAnimatedValue);
                return;
            }
            if (this.mProperty != null) {
                this.mProperty.set(obj, java.lang.Float.valueOf(this.mFloatAnimatedValue));
                return;
            }
            if (this.mJniSetter != 0) {
                android.animation.PropertyValuesHolder.nCallFloatMethod(obj, this.mJniSetter, this.mFloatAnimatedValue);
                return;
            }
            if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = java.lang.Float.valueOf(this.mFloatAnimatedValue);
                    this.mSetter.invoke(obj, this.mTmpValueArray);
                } catch (java.lang.IllegalAccessException e) {
                    android.util.Log.e("PropertyValuesHolder", e.toString());
                } catch (java.lang.reflect.InvocationTargetException e2) {
                    android.util.Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetter(java.lang.Class cls) {
            boolean z;
            java.lang.Long l;
            if (this.mProperty != null) {
                return;
            }
            synchronized (sJNISetterPropertyMap) {
                java.util.HashMap<java.lang.String, java.lang.Long> hashMap = sJNISetterPropertyMap.get(cls);
                if (hashMap == null) {
                    z = false;
                } else {
                    z = hashMap.containsKey(this.mPropertyName);
                    if (z && (l = hashMap.get(this.mPropertyName)) != null) {
                        this.mJniSetter = l.longValue();
                    }
                }
                if (!z) {
                    try {
                        this.mJniSetter = android.animation.PropertyValuesHolder.nGetFloatMethod(cls, getMethodName("set", this.mPropertyName));
                    } catch (java.lang.NoSuchMethodError e) {
                    }
                    if (hashMap == null) {
                        hashMap = new java.util.HashMap<>();
                        sJNISetterPropertyMap.put(cls, hashMap);
                    }
                    hashMap.put(this.mPropertyName, java.lang.Long.valueOf(this.mJniSetter));
                }
            }
            if (this.mJniSetter == 0) {
                super.setupSetter(cls);
            }
        }
    }

    static class MultiFloatValuesHolder extends android.animation.PropertyValuesHolder {
        private static final java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> sJNISetterPropertyMap = new java.util.HashMap<>();
        private long mJniSetter;

        public MultiFloatValuesHolder(java.lang.String str, android.animation.TypeConverter typeConverter, android.animation.TypeEvaluator typeEvaluator, java.lang.Object... objArr) {
            super(str);
            setConverter(typeConverter);
            setObjectValues(objArr);
            setEvaluator(typeEvaluator);
        }

        public MultiFloatValuesHolder(java.lang.String str, android.animation.TypeConverter typeConverter, android.animation.TypeEvaluator typeEvaluator, android.animation.Keyframes keyframes) {
            super(str);
            setConverter(typeConverter);
            this.mKeyframes = keyframes;
            setEvaluator(typeEvaluator);
        }

        @Override // android.animation.PropertyValuesHolder
        void setAnimatedValue(java.lang.Object obj) {
            float[] fArr = (float[]) getAnimatedValue();
            int length = fArr.length;
            if (this.mJniSetter != 0) {
                switch (length) {
                    case 1:
                        android.animation.PropertyValuesHolder.nCallFloatMethod(obj, this.mJniSetter, fArr[0]);
                        break;
                    case 2:
                        android.animation.PropertyValuesHolder.nCallTwoFloatMethod(obj, this.mJniSetter, fArr[0], fArr[1]);
                        break;
                    case 3:
                    default:
                        android.animation.PropertyValuesHolder.nCallMultipleFloatMethod(obj, this.mJniSetter, fArr);
                        break;
                    case 4:
                        android.animation.PropertyValuesHolder.nCallFourFloatMethod(obj, this.mJniSetter, fArr[0], fArr[1], fArr[2], fArr[3]);
                        break;
                }
            }
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetterAndGetter(java.lang.Object obj) {
            setupSetter(obj.getClass());
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetter(java.lang.Class cls) {
            boolean z;
            java.lang.Long l;
            if (this.mJniSetter != 0) {
                return;
            }
            synchronized (sJNISetterPropertyMap) {
                java.util.HashMap<java.lang.String, java.lang.Long> hashMap = sJNISetterPropertyMap.get(cls);
                if (hashMap == null) {
                    z = false;
                } else {
                    z = hashMap.containsKey(this.mPropertyName);
                    if (z && (l = hashMap.get(this.mPropertyName)) != null) {
                        this.mJniSetter = l.longValue();
                    }
                }
                if (!z) {
                    java.lang.String methodName = getMethodName("set", this.mPropertyName);
                    calculateValue(0.0f);
                    int length = ((float[]) getAnimatedValue()).length;
                    try {
                        this.mJniSetter = android.animation.PropertyValuesHolder.nGetMultipleFloatMethod(cls, methodName, length);
                    } catch (java.lang.NoSuchMethodError e) {
                        try {
                            this.mJniSetter = android.animation.PropertyValuesHolder.nGetMultipleFloatMethod(cls, this.mPropertyName, length);
                        } catch (java.lang.NoSuchMethodError e2) {
                        }
                    }
                    if (hashMap == null) {
                        hashMap = new java.util.HashMap<>();
                        sJNISetterPropertyMap.put(cls, hashMap);
                    }
                    hashMap.put(this.mPropertyName, java.lang.Long.valueOf(this.mJniSetter));
                }
            }
        }
    }

    static class MultiIntValuesHolder extends android.animation.PropertyValuesHolder {
        private static final java.util.HashMap<java.lang.Class, java.util.HashMap<java.lang.String, java.lang.Long>> sJNISetterPropertyMap = new java.util.HashMap<>();
        private long mJniSetter;

        public MultiIntValuesHolder(java.lang.String str, android.animation.TypeConverter typeConverter, android.animation.TypeEvaluator typeEvaluator, java.lang.Object... objArr) {
            super(str);
            setConverter(typeConverter);
            setObjectValues(objArr);
            setEvaluator(typeEvaluator);
        }

        public MultiIntValuesHolder(java.lang.String str, android.animation.TypeConverter typeConverter, android.animation.TypeEvaluator typeEvaluator, android.animation.Keyframes keyframes) {
            super(str);
            setConverter(typeConverter);
            this.mKeyframes = keyframes;
            setEvaluator(typeEvaluator);
        }

        @Override // android.animation.PropertyValuesHolder
        void setAnimatedValue(java.lang.Object obj) {
            int[] iArr = (int[]) getAnimatedValue();
            int length = iArr.length;
            if (this.mJniSetter != 0) {
                switch (length) {
                    case 1:
                        android.animation.PropertyValuesHolder.nCallIntMethod(obj, this.mJniSetter, iArr[0]);
                        break;
                    case 2:
                        android.animation.PropertyValuesHolder.nCallTwoIntMethod(obj, this.mJniSetter, iArr[0], iArr[1]);
                        break;
                    case 3:
                    default:
                        android.animation.PropertyValuesHolder.nCallMultipleIntMethod(obj, this.mJniSetter, iArr);
                        break;
                    case 4:
                        android.animation.PropertyValuesHolder.nCallFourIntMethod(obj, this.mJniSetter, iArr[0], iArr[1], iArr[2], iArr[3]);
                        break;
                }
            }
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetterAndGetter(java.lang.Object obj) {
            setupSetter(obj.getClass());
        }

        @Override // android.animation.PropertyValuesHolder
        void setupSetter(java.lang.Class cls) {
            boolean z;
            java.lang.Long l;
            if (this.mJniSetter != 0) {
                return;
            }
            synchronized (sJNISetterPropertyMap) {
                java.util.HashMap<java.lang.String, java.lang.Long> hashMap = sJNISetterPropertyMap.get(cls);
                if (hashMap == null) {
                    z = false;
                } else {
                    z = hashMap.containsKey(this.mPropertyName);
                    if (z && (l = hashMap.get(this.mPropertyName)) != null) {
                        this.mJniSetter = l.longValue();
                    }
                }
                if (!z) {
                    java.lang.String methodName = getMethodName("set", this.mPropertyName);
                    calculateValue(0.0f);
                    int length = ((int[]) getAnimatedValue()).length;
                    try {
                        this.mJniSetter = android.animation.PropertyValuesHolder.nGetMultipleIntMethod(cls, methodName, length);
                    } catch (java.lang.NoSuchMethodError e) {
                        try {
                            this.mJniSetter = android.animation.PropertyValuesHolder.nGetMultipleIntMethod(cls, this.mPropertyName, length);
                        } catch (java.lang.NoSuchMethodError e2) {
                        }
                    }
                    if (hashMap == null) {
                        hashMap = new java.util.HashMap<>();
                        sJNISetterPropertyMap.put(cls, hashMap);
                    }
                    hashMap.put(this.mPropertyName, java.lang.Long.valueOf(this.mJniSetter));
                }
            }
        }
    }

    private static class PointFToFloatArray extends android.animation.TypeConverter<android.graphics.PointF, float[]> {
        private float[] mCoordinates;

        public PointFToFloatArray() {
            super(android.graphics.PointF.class, float[].class);
            this.mCoordinates = new float[2];
        }

        @Override // android.animation.TypeConverter
        public float[] convert(android.graphics.PointF pointF) {
            this.mCoordinates[0] = pointF.x;
            this.mCoordinates[1] = pointF.y;
            return this.mCoordinates;
        }
    }

    private static class PointFToIntArray extends android.animation.TypeConverter<android.graphics.PointF, int[]> {
        private int[] mCoordinates;

        public PointFToIntArray() {
            super(android.graphics.PointF.class, int[].class);
            this.mCoordinates = new int[2];
        }

        @Override // android.animation.TypeConverter
        public int[] convert(android.graphics.PointF pointF) {
            this.mCoordinates[0] = java.lang.Math.round(pointF.x);
            this.mCoordinates[1] = java.lang.Math.round(pointF.y);
            return this.mCoordinates;
        }
    }

    public static class PropertyValues {
        public android.animation.PropertyValuesHolder.PropertyValues.DataSource dataSource = null;
        public java.lang.Object endValue;
        public java.lang.String propertyName;
        public java.lang.Object startValue;
        public java.lang.Class type;

        public interface DataSource {
            java.lang.Object getValueAtFraction(float f);
        }

        public java.lang.String toString() {
            return "property name: " + this.propertyName + ", type: " + this.type + ", startValue: " + this.startValue.toString() + ", endValue: " + this.endValue.toString();
        }
    }
}
