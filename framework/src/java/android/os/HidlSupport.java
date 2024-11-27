package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class HidlSupport {
    @android.annotation.SystemApi
    public static native int getPidIfSharable();

    public static native boolean isHidlSupported();

    @android.annotation.SystemApi
    public static boolean deepEquals(java.lang.Object obj, java.lang.Object obj2) {
        java.lang.Class<?> cls;
        java.lang.Class<?> cls2;
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null || (cls = obj.getClass()) != (cls2 = obj2.getClass())) {
            return false;
        }
        if (cls.isArray()) {
            java.lang.Class<?> componentType = cls.getComponentType();
            if (componentType != cls2.getComponentType()) {
                return false;
            }
            if (componentType.isPrimitive()) {
                return java.util.Objects.deepEquals(obj, obj2);
            }
            final java.lang.Object[] objArr = (java.lang.Object[]) obj;
            final java.lang.Object[] objArr2 = (java.lang.Object[]) obj2;
            if (objArr.length == objArr2.length && java.util.stream.IntStream.range(0, objArr.length).allMatch(new java.util.function.IntPredicate() { // from class: android.os.HidlSupport$$ExternalSyntheticLambda2
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    boolean deepEquals;
                    deepEquals = android.os.HidlSupport.deepEquals(objArr[i], objArr2[i]);
                    return deepEquals;
                }
            })) {
                return true;
            }
            return false;
        }
        if (obj instanceof java.util.List) {
            java.util.List list = (java.util.List) obj;
            java.util.List list2 = (java.util.List) obj2;
            if (list.size() != list2.size()) {
                return false;
            }
            final java.util.Iterator it = list.iterator();
            return list2.stream().allMatch(new java.util.function.Predicate() { // from class: android.os.HidlSupport$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj3) {
                    boolean deepEquals;
                    deepEquals = android.os.HidlSupport.deepEquals(it.next(), obj3);
                    return deepEquals;
                }
            });
        }
        throwErrorIfUnsupportedType(obj);
        return obj.equals(obj2);
    }

    public static final class Mutable<E> {
        public E value;

        public Mutable() {
            this.value = null;
        }

        public Mutable(E e) {
            this.value = e;
        }
    }

    @android.annotation.SystemApi
    public static int deepHashCode(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        java.lang.Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            if (cls.getComponentType().isPrimitive()) {
                return primitiveArrayHashCode(obj);
            }
            return java.util.Arrays.hashCode(java.util.Arrays.stream((java.lang.Object[]) obj).mapToInt(new java.util.function.ToIntFunction() { // from class: android.os.HidlSupport$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj2) {
                    int deepHashCode;
                    deepHashCode = android.os.HidlSupport.deepHashCode(obj2);
                    return deepHashCode;
                }
            }).toArray());
        }
        if (obj instanceof java.util.List) {
            return java.util.Arrays.hashCode(((java.util.List) obj).stream().mapToInt(new java.util.function.ToIntFunction() { // from class: android.os.HidlSupport$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj2) {
                    int deepHashCode;
                    deepHashCode = android.os.HidlSupport.deepHashCode(obj2);
                    return deepHashCode;
                }
            }).toArray());
        }
        throwErrorIfUnsupportedType(obj);
        return obj.hashCode();
    }

    private static void throwErrorIfUnsupportedType(java.lang.Object obj) {
        if ((obj instanceof java.util.Collection) && !(obj instanceof java.util.List)) {
            throw new java.lang.UnsupportedOperationException("Cannot check equality on collections other than lists: " + obj.getClass().getName());
        }
        if (obj instanceof java.util.Map) {
            throw new java.lang.UnsupportedOperationException("Cannot check equality on maps");
        }
    }

    private static int primitiveArrayHashCode(java.lang.Object obj) {
        java.lang.Class<?> componentType = obj.getClass().getComponentType();
        if (componentType == java.lang.Boolean.TYPE) {
            return java.util.Arrays.hashCode((boolean[]) obj);
        }
        if (componentType == java.lang.Byte.TYPE) {
            return java.util.Arrays.hashCode((byte[]) obj);
        }
        if (componentType == java.lang.Character.TYPE) {
            return java.util.Arrays.hashCode((char[]) obj);
        }
        if (componentType == java.lang.Double.TYPE) {
            return java.util.Arrays.hashCode((double[]) obj);
        }
        if (componentType == java.lang.Float.TYPE) {
            return java.util.Arrays.hashCode((float[]) obj);
        }
        if (componentType == java.lang.Integer.TYPE) {
            return java.util.Arrays.hashCode((int[]) obj);
        }
        if (componentType == java.lang.Long.TYPE) {
            return java.util.Arrays.hashCode((long[]) obj);
        }
        if (componentType == java.lang.Short.TYPE) {
            return java.util.Arrays.hashCode((short[]) obj);
        }
        throw new java.lang.UnsupportedOperationException();
    }

    @android.annotation.SystemApi
    public static boolean interfacesEqual(android.os.IHwInterface iHwInterface, java.lang.Object obj) {
        if (iHwInterface == obj) {
            return true;
        }
        if (iHwInterface == null || obj == null || !(obj instanceof android.os.IHwInterface)) {
            return false;
        }
        return java.util.Objects.equals(iHwInterface.asBinder(), ((android.os.IHwInterface) obj).asBinder());
    }
}
