package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class CompositeAlgorithmSpec implements java.security.spec.AlgorithmParameterSpec {
    private final java.util.List<java.lang.String> algorithmNames;
    private final java.util.List<java.security.spec.AlgorithmParameterSpec> parameterSpecs;

    public static class Builder {
        private java.util.List<java.lang.String> algorithmNames = new java.util.ArrayList();
        private java.util.List<java.security.spec.AlgorithmParameterSpec> parameterSpecs = new java.util.ArrayList();

        public com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec.Builder add(java.lang.String str) {
            this.algorithmNames.add(str);
            this.parameterSpecs.add(null);
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec.Builder add(java.lang.String str, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
            this.algorithmNames.add(str);
            this.parameterSpecs.add(algorithmParameterSpec);
            return this;
        }

        public com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec build() {
            if (this.algorithmNames.isEmpty()) {
                throw new java.lang.IllegalStateException("cannot call build with no algorithm names added");
            }
            return new com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec(this);
        }
    }

    public CompositeAlgorithmSpec(com.android.internal.org.bouncycastle.jcajce.spec.CompositeAlgorithmSpec.Builder builder) {
        this.algorithmNames = java.util.Collections.unmodifiableList(new java.util.ArrayList(builder.algorithmNames));
        this.parameterSpecs = java.util.Collections.unmodifiableList(new java.util.ArrayList(builder.parameterSpecs));
    }

    public java.util.List<java.lang.String> getAlgorithmNames() {
        return this.algorithmNames;
    }

    public java.util.List<java.security.spec.AlgorithmParameterSpec> getParameterSpecs() {
        return this.parameterSpecs;
    }
}
