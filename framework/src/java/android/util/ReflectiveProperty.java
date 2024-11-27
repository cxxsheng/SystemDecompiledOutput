package android.util;

/* loaded from: classes3.dex */
class ReflectiveProperty<T, V> extends android.util.Property<T, V> {
    private static final java.lang.String PREFIX_GET = "get";
    private static final java.lang.String PREFIX_IS = "is";
    private static final java.lang.String PREFIX_SET = "set";
    private java.lang.reflect.Field mField;
    private java.lang.reflect.Method mGetter;
    private java.lang.reflect.Method mSetter;

    public ReflectiveProperty(java.lang.Class<T> cls, java.lang.Class<V> cls2, java.lang.String str) {
        super(cls2, str);
        java.lang.String str2 = java.lang.Character.toUpperCase(str.charAt(0)) + str.substring(1);
        try {
            this.mGetter = cls.getMethod(PREFIX_GET + str2, null);
        } catch (java.lang.NoSuchMethodException e) {
            try {
                this.mGetter = cls.getMethod(PREFIX_IS + str2, null);
            } catch (java.lang.NoSuchMethodException e2) {
                try {
                    this.mField = cls.getField(str);
                    java.lang.Class<?> type = this.mField.getType();
                    if (!typesMatch(cls2, type)) {
                        throw new android.util.NoSuchPropertyException("Underlying type (" + type + ") does not match Property type (" + cls2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    }
                    return;
                } catch (java.lang.NoSuchFieldException e3) {
                    throw new android.util.NoSuchPropertyException("No accessor method or field found for property with name " + str);
                }
            }
        }
        java.lang.Class<?> returnType = this.mGetter.getReturnType();
        if (!typesMatch(cls2, returnType)) {
            throw new android.util.NoSuchPropertyException("Underlying type (" + returnType + ") does not match Property type (" + cls2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        try {
            this.mSetter = cls.getMethod(PREFIX_SET + str2, returnType);
        } catch (java.lang.NoSuchMethodException e4) {
        }
    }

    private boolean typesMatch(java.lang.Class<V> cls, java.lang.Class cls2) {
        if (cls2 == cls) {
            return true;
        }
        if (!cls2.isPrimitive()) {
            return false;
        }
        if (cls2 == java.lang.Float.TYPE && cls == java.lang.Float.class) {
            return true;
        }
        if (cls2 == java.lang.Integer.TYPE && cls == java.lang.Integer.class) {
            return true;
        }
        if (cls2 == java.lang.Boolean.TYPE && cls == java.lang.Boolean.class) {
            return true;
        }
        if (cls2 == java.lang.Long.TYPE && cls == java.lang.Long.class) {
            return true;
        }
        if (cls2 == java.lang.Double.TYPE && cls == java.lang.Double.class) {
            return true;
        }
        if (cls2 == java.lang.Short.TYPE && cls == java.lang.Short.class) {
            return true;
        }
        if (cls2 == java.lang.Byte.TYPE && cls == java.lang.Byte.class) {
            return true;
        }
        return cls2 == java.lang.Character.TYPE && cls == java.lang.Character.class;
    }

    @Override // android.util.Property
    public void set(T t, V v) {
        if (this.mSetter != null) {
            try {
                this.mSetter.invoke(t, v);
                return;
            } catch (java.lang.IllegalAccessException e) {
                throw new java.lang.AssertionError();
            } catch (java.lang.reflect.InvocationTargetException e2) {
                throw new java.lang.RuntimeException(e2.getCause());
            }
        }
        if (this.mField != null) {
            try {
                this.mField.set(t, v);
                return;
            } catch (java.lang.IllegalAccessException e3) {
                throw new java.lang.AssertionError();
            }
        }
        throw new java.lang.UnsupportedOperationException("Property " + getName() + " is read-only");
    }

    @Override // android.util.Property
    public V get(T t) {
        if (this.mGetter != null) {
            try {
                return (V) this.mGetter.invoke(t, null);
            } catch (java.lang.IllegalAccessException e) {
                throw new java.lang.AssertionError();
            } catch (java.lang.reflect.InvocationTargetException e2) {
                throw new java.lang.RuntimeException(e2.getCause());
            }
        }
        if (this.mField != null) {
            try {
                return (V) this.mField.get(t);
            } catch (java.lang.IllegalAccessException e3) {
                throw new java.lang.AssertionError();
            }
        }
        throw new java.lang.AssertionError();
    }

    @Override // android.util.Property
    public boolean isReadOnly() {
        return this.mSetter == null && this.mField == null;
    }
}
