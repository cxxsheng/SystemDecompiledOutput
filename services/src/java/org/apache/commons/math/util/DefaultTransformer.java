package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public class DefaultTransformer implements org.apache.commons.math.util.NumberTransformer, java.io.Serializable {
    private static final long serialVersionUID = 4019938025047800455L;

    @Override // org.apache.commons.math.util.NumberTransformer
    public double transform(java.lang.Object obj) throws org.apache.commons.math.MathException {
        if (obj == null) {
            throw new org.apache.commons.math.MathException(org.apache.commons.math.exception.util.LocalizedFormats.OBJECT_TRANSFORMATION, new java.lang.Object[0]);
        }
        if (obj instanceof java.lang.Number) {
            return ((java.lang.Number) obj).doubleValue();
        }
        try {
            return java.lang.Double.valueOf(obj.toString()).doubleValue();
        } catch (java.lang.NumberFormatException e) {
            throw new org.apache.commons.math.MathException(e, org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_TRANSFORM_TO_DOUBLE, e.getMessage());
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return obj instanceof org.apache.commons.math.util.DefaultTransformer;
    }

    public int hashCode() {
        return 401993047;
    }
}
