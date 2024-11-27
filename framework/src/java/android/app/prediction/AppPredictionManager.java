package android.app.prediction;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AppPredictionManager {
    private final android.content.Context mContext;

    public AppPredictionManager(android.content.Context context) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
    }

    public android.app.prediction.AppPredictor createAppPredictionSession(android.app.prediction.AppPredictionContext appPredictionContext) {
        return new android.app.prediction.AppPredictor(this.mContext, appPredictionContext);
    }
}
