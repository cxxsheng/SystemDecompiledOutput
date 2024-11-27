package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public abstract class TypeReference<T> {
    private final int mHash;
    private final java.lang.reflect.Type mType;

    protected TypeReference() {
        this.mType = ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (containsTypeVariable(this.mType)) {
            throw new java.lang.IllegalArgumentException("Including a type variable in a type reference is not allowed");
        }
        this.mHash = this.mType.hashCode();
    }

    public java.lang.reflect.Type getType() {
        return this.mType;
    }

    private TypeReference(java.lang.reflect.Type type) {
        this.mType = type;
        if (containsTypeVariable(this.mType)) {
            throw new java.lang.IllegalArgumentException("Including a type variable in a type reference is not allowed");
        }
        this.mHash = this.mType.hashCode();
    }

    private static class SpecializedTypeReference<T> extends android.hardware.camera2.utils.TypeReference<T> {
        public SpecializedTypeReference(java.lang.Class<T> cls) {
            super(cls);
        }
    }

    private static class SpecializedBaseTypeReference extends android.hardware.camera2.utils.TypeReference {
        public SpecializedBaseTypeReference(java.lang.reflect.Type type) {
            super(type);
        }
    }

    public static <T> android.hardware.camera2.utils.TypeReference<T> createSpecializedTypeReference(java.lang.Class<T> cls) {
        return new android.hardware.camera2.utils.TypeReference.SpecializedTypeReference(cls);
    }

    public static android.hardware.camera2.utils.TypeReference<?> createSpecializedTypeReference(java.lang.reflect.Type type) {
        return new android.hardware.camera2.utils.TypeReference.SpecializedBaseTypeReference(type);
    }

    public final java.lang.Class<? super T> getRawType() {
        return (java.lang.Class<? super T>) getRawType(this.mType);
    }

    private static final java.lang.Class<?> getRawType(java.lang.reflect.Type type) {
        if (type == null) {
            throw new java.lang.NullPointerException("type must not be null");
        }
        if (type instanceof java.lang.Class) {
            return (java.lang.Class) type;
        }
        if (type instanceof java.lang.reflect.ParameterizedType) {
            return (java.lang.Class) ((java.lang.reflect.ParameterizedType) type).getRawType();
        }
        if (type instanceof java.lang.reflect.GenericArrayType) {
            return getArrayClass(getRawType(((java.lang.reflect.GenericArrayType) type).getGenericComponentType()));
        }
        if (type instanceof java.lang.reflect.WildcardType) {
            return getRawType(((java.lang.reflect.WildcardType) type).getUpperBounds());
        }
        if (type instanceof java.lang.reflect.TypeVariable) {
            throw new java.lang.AssertionError("Type variables are not allowed in type references");
        }
        throw new java.lang.AssertionError("Unhandled branch to get raw type for type " + type);
    }

    private static final java.lang.Class<?> getRawType(java.lang.reflect.Type[] typeArr) {
        if (typeArr == null) {
            return null;
        }
        for (java.lang.reflect.Type type : typeArr) {
            java.lang.Class<?> rawType = getRawType(type);
            if (rawType != null) {
                return rawType;
            }
        }
        return null;
    }

    private static final java.lang.Class<?> getArrayClass(java.lang.Class<?> cls) {
        return java.lang.reflect.Array.newInstance(cls, 0).getClass();
    }

    public android.hardware.camera2.utils.TypeReference<?> getComponentType() {
        java.lang.reflect.Type componentType = getComponentType(this.mType);
        if (componentType != null) {
            return createSpecializedTypeReference(componentType);
        }
        return null;
    }

    private static java.lang.reflect.Type getComponentType(java.lang.reflect.Type type) {
        com.android.internal.util.Preconditions.checkNotNull(type, "type must not be null");
        if (type instanceof java.lang.Class) {
            return ((java.lang.Class) type).getComponentType();
        }
        if (type instanceof java.lang.reflect.ParameterizedType) {
            return null;
        }
        if (type instanceof java.lang.reflect.GenericArrayType) {
            return ((java.lang.reflect.GenericArrayType) type).getGenericComponentType();
        }
        if (type instanceof java.lang.reflect.WildcardType) {
            throw new java.lang.UnsupportedOperationException("TODO: support wild card components");
        }
        if (type instanceof java.lang.reflect.TypeVariable) {
            throw new java.lang.AssertionError("Type variables are not allowed in type references");
        }
        throw new java.lang.AssertionError("Unhandled branch to get component type for type " + type);
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.hardware.camera2.utils.TypeReference) && this.mType.equals(((android.hardware.camera2.utils.TypeReference) obj).mType);
    }

    public int hashCode() {
        return this.mHash;
    }

    public static boolean containsTypeVariable(java.lang.reflect.Type type) {
        if (type == null) {
            return false;
        }
        if (type instanceof java.lang.reflect.TypeVariable) {
            return true;
        }
        if (type instanceof java.lang.Class) {
            java.lang.Class cls = (java.lang.Class) type;
            if (cls.getTypeParameters().length != 0) {
                return true;
            }
            return containsTypeVariable(cls.getDeclaringClass());
        }
        if (type instanceof java.lang.reflect.ParameterizedType) {
            for (java.lang.reflect.Type type2 : ((java.lang.reflect.ParameterizedType) type).getActualTypeArguments()) {
                if (containsTypeVariable(type2)) {
                    return true;
                }
            }
            return false;
        }
        if (!(type instanceof java.lang.reflect.WildcardType)) {
            return false;
        }
        java.lang.reflect.WildcardType wildcardType = (java.lang.reflect.WildcardType) type;
        return containsTypeVariable(wildcardType.getLowerBounds()) || containsTypeVariable(wildcardType.getUpperBounds());
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("TypeReference<");
        toString(getType(), sb);
        sb.append(">");
        return sb.toString();
    }

    private static void toString(java.lang.reflect.Type type, java.lang.StringBuilder sb) {
        if (type == null) {
            return;
        }
        if (type instanceof java.lang.reflect.TypeVariable) {
            sb.append(((java.lang.reflect.TypeVariable) type).getName());
            return;
        }
        if (type instanceof java.lang.Class) {
            java.lang.Class cls = (java.lang.Class) type;
            sb.append(cls.getName());
            toString(cls.getTypeParameters(), sb);
        } else if (type instanceof java.lang.reflect.ParameterizedType) {
            java.lang.reflect.ParameterizedType parameterizedType = (java.lang.reflect.ParameterizedType) type;
            sb.append(((java.lang.Class) parameterizedType.getRawType()).getName());
            toString(parameterizedType.getActualTypeArguments(), sb);
        } else if (type instanceof java.lang.reflect.GenericArrayType) {
            toString(((java.lang.reflect.GenericArrayType) type).getGenericComponentType(), sb);
            sb.append("[]");
        } else {
            sb.append(type.toString());
        }
    }

    private static void toString(java.lang.reflect.Type[] typeArr, java.lang.StringBuilder sb) {
        if (typeArr == null || typeArr.length == 0) {
            return;
        }
        sb.append("<");
        for (int i = 0; i < typeArr.length; i++) {
            toString(typeArr[i], sb);
            if (i != typeArr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(">");
    }

    private static boolean containsTypeVariable(java.lang.reflect.Type[] typeArr) {
        if (typeArr == null) {
            return false;
        }
        for (java.lang.reflect.Type type : typeArr) {
            if (containsTypeVariable(type)) {
                return true;
            }
        }
        return false;
    }
}
