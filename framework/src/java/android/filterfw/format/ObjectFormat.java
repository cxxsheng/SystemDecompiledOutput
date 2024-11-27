package android.filterfw.format;

/* loaded from: classes.dex */
public class ObjectFormat {
    public static android.filterfw.core.MutableFrameFormat fromClass(java.lang.Class cls, int i, int i2) {
        android.filterfw.core.MutableFrameFormat mutableFrameFormat = new android.filterfw.core.MutableFrameFormat(8, i2);
        mutableFrameFormat.setObjectClass(getBoxedClass(cls));
        if (i != 0) {
            mutableFrameFormat.setDimensions(i);
        }
        mutableFrameFormat.setBytesPerSample(bytesPerSampleForClass(cls, i2));
        return mutableFrameFormat;
    }

    public static android.filterfw.core.MutableFrameFormat fromClass(java.lang.Class cls, int i) {
        return fromClass(cls, 0, i);
    }

    public static android.filterfw.core.MutableFrameFormat fromObject(java.lang.Object obj, int i) {
        if (obj == null) {
            return new android.filterfw.core.MutableFrameFormat(8, i);
        }
        return fromClass(obj.getClass(), 0, i);
    }

    public static android.filterfw.core.MutableFrameFormat fromObject(java.lang.Object obj, int i, int i2) {
        if (obj == null) {
            return new android.filterfw.core.MutableFrameFormat(8, i2);
        }
        return fromClass(obj.getClass(), i, i2);
    }

    private static int bytesPerSampleForClass(java.lang.Class cls, int i) {
        if (i == 2) {
            if (!android.filterfw.core.NativeBuffer.class.isAssignableFrom(cls)) {
                throw new java.lang.IllegalArgumentException("Native object-based formats must be of a NativeBuffer subclass! (Received class: " + cls + ").");
            }
            try {
                return ((android.filterfw.core.NativeBuffer) cls.newInstance()).getElementSize();
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException("Could not determine the size of an element in a native object-based frame of type " + cls + "! Perhaps it is missing a default constructor?");
            }
        }
        return 1;
    }

    private static java.lang.Class getBoxedClass(java.lang.Class cls) {
        if (cls.isPrimitive()) {
            if (cls == java.lang.Boolean.TYPE) {
                return java.lang.Boolean.class;
            }
            if (cls == java.lang.Byte.TYPE) {
                return java.lang.Byte.class;
            }
            if (cls == java.lang.Character.TYPE) {
                return java.lang.Character.class;
            }
            if (cls == java.lang.Short.TYPE) {
                return java.lang.Short.class;
            }
            if (cls == java.lang.Integer.TYPE) {
                return java.lang.Integer.class;
            }
            if (cls == java.lang.Long.TYPE) {
                return java.lang.Long.class;
            }
            if (cls == java.lang.Float.TYPE) {
                return java.lang.Float.class;
            }
            if (cls == java.lang.Double.TYPE) {
                return java.lang.Double.class;
            }
            throw new java.lang.IllegalArgumentException("Unknown primitive type: " + cls.getSimpleName() + "!");
        }
        return cls;
    }
}
