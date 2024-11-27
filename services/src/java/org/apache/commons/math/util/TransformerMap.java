package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public class TransformerMap implements org.apache.commons.math.util.NumberTransformer, java.io.Serializable {
    private static final long serialVersionUID = 4605318041528645258L;
    private org.apache.commons.math.util.NumberTransformer defaultTransformer;
    private java.util.Map<java.lang.Class<?>, org.apache.commons.math.util.NumberTransformer> map;

    public TransformerMap() {
        this.defaultTransformer = null;
        this.map = null;
        this.map = new java.util.HashMap();
        this.defaultTransformer = new org.apache.commons.math.util.DefaultTransformer();
    }

    public boolean containsClass(java.lang.Class<?> cls) {
        return this.map.containsKey(cls);
    }

    public boolean containsTransformer(org.apache.commons.math.util.NumberTransformer numberTransformer) {
        return this.map.containsValue(numberTransformer);
    }

    public org.apache.commons.math.util.NumberTransformer getTransformer(java.lang.Class<?> cls) {
        return this.map.get(cls);
    }

    public org.apache.commons.math.util.NumberTransformer putTransformer(java.lang.Class<?> cls, org.apache.commons.math.util.NumberTransformer numberTransformer) {
        return this.map.put(cls, numberTransformer);
    }

    public org.apache.commons.math.util.NumberTransformer removeTransformer(java.lang.Class<?> cls) {
        return this.map.remove(cls);
    }

    public void clear() {
        this.map.clear();
    }

    public java.util.Set<java.lang.Class<?>> classes() {
        return this.map.keySet();
    }

    public java.util.Collection<org.apache.commons.math.util.NumberTransformer> transformers() {
        return this.map.values();
    }

    @Override // org.apache.commons.math.util.NumberTransformer
    public double transform(java.lang.Object obj) throws org.apache.commons.math.MathException {
        if ((obj instanceof java.lang.Number) || (obj instanceof java.lang.String)) {
            return this.defaultTransformer.transform(obj);
        }
        org.apache.commons.math.util.NumberTransformer transformer = getTransformer(obj.getClass());
        if (transformer == null) {
            return Double.NaN;
        }
        return transformer.transform(obj);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.util.TransformerMap)) {
            return false;
        }
        org.apache.commons.math.util.TransformerMap transformerMap = (org.apache.commons.math.util.TransformerMap) obj;
        if (!this.defaultTransformer.equals(transformerMap.defaultTransformer) || this.map.size() != transformerMap.map.size()) {
            return false;
        }
        for (java.util.Map.Entry<java.lang.Class<?>, org.apache.commons.math.util.NumberTransformer> entry : this.map.entrySet()) {
            if (!entry.getValue().equals(transformerMap.map.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hashCode = this.defaultTransformer.hashCode();
        java.util.Iterator<org.apache.commons.math.util.NumberTransformer> it = this.map.values().iterator();
        while (it.hasNext()) {
            hashCode = (hashCode * 31) + it.next().hashCode();
        }
        return hashCode;
    }
}
