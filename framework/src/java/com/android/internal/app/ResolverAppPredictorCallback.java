package com.android.internal.app;

/* loaded from: classes4.dex */
public class ResolverAppPredictorCallback {
    private volatile java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> mCallback;

    public ResolverAppPredictorCallback(java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer) {
        this.mCallback = consumer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallback(java.util.List<android.app.prediction.AppTarget> list) {
        java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> consumer = this.mCallback;
        if (consumer != null) {
            consumer.accept((java.util.List) java.util.Objects.requireNonNullElseGet(list, new java.util.function.Supplier() { // from class: com.android.internal.app.ResolverAppPredictorCallback$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return java.util.List.of();
                }
            }));
        }
    }

    public java.util.function.Consumer<java.util.List<android.app.prediction.AppTarget>> asConsumer() {
        return new java.util.function.Consumer() { // from class: com.android.internal.app.ResolverAppPredictorCallback$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.app.ResolverAppPredictorCallback.this.notifyCallback((java.util.List) obj);
            }
        };
    }

    public android.app.prediction.AppPredictor.Callback asCallback() {
        return new android.app.prediction.AppPredictor.Callback() { // from class: com.android.internal.app.ResolverAppPredictorCallback$$ExternalSyntheticLambda1
            @Override // android.app.prediction.AppPredictor.Callback
            public final void onTargetsAvailable(java.util.List list) {
                com.android.internal.app.ResolverAppPredictorCallback.this.notifyCallback(list);
            }
        };
    }

    public void destroy() {
        this.mCallback = null;
    }
}
