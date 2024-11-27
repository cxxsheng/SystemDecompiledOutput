package android.service.autofill;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class AutofillFieldClassificationService extends android.app.Service {
    public static final java.lang.String EXTRA_SCORES = "scores";
    public static final java.lang.String REQUIRED_ALGORITHM_CREDIT_CARD = "CREDIT_CARD";
    public static final java.lang.String REQUIRED_ALGORITHM_EDIT_DISTANCE = "EDIT_DISTANCE";
    public static final java.lang.String REQUIRED_ALGORITHM_EXACT_MATCH = "EXACT_MATCH";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.autofill.AutofillFieldClassificationService";
    public static final java.lang.String SERVICE_META_DATA_KEY_AVAILABLE_ALGORITHMS = "android.autofill.field_classification.available_algorithms";
    public static final java.lang.String SERVICE_META_DATA_KEY_DEFAULT_ALGORITHM = "android.autofill.field_classification.default_algorithm";
    private static final java.lang.String TAG = "AutofillFieldClassificationService";
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
    private android.service.autofill.AutofillFieldClassificationService.AutofillFieldClassificationServiceWrapper mWrapper;

    /* JADX INFO: Access modifiers changed from: private */
    public void calculateScores(android.os.RemoteCallback remoteCallback, java.util.List<android.view.autofill.AutofillValue> list, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.util.Map map2) {
        android.os.Bundle bundle2 = new android.os.Bundle();
        float[][] onCalculateScores = onCalculateScores(list, java.util.Arrays.asList(strArr), java.util.Arrays.asList(strArr2), str, bundle, map, map2);
        if (onCalculateScores != null) {
            bundle2.putParcelable(EXTRA_SCORES, new android.service.autofill.AutofillFieldClassificationService.Scores(onCalculateScores));
        }
        remoteCallback.sendResult(bundle2);
    }

    @android.annotation.SystemApi
    public AutofillFieldClassificationService() {
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mWrapper = new android.service.autofill.AutofillFieldClassificationService.AutofillFieldClassificationServiceWrapper();
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mWrapper;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public float[][] onGetScores(java.lang.String str, android.os.Bundle bundle, java.util.List<android.view.autofill.AutofillValue> list, java.util.List<java.lang.String> list2) {
        android.util.Log.e(TAG, "service implementation (" + getClass() + " does not implement onGetScores()");
        return null;
    }

    @android.annotation.SystemApi
    public float[][] onCalculateScores(java.util.List<android.view.autofill.AutofillValue> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.String> list3, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.util.Map map2) {
        android.util.Log.e(TAG, "service implementation (" + getClass() + " does not implement onCalculateScore()");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class AutofillFieldClassificationServiceWrapper extends android.service.autofill.IAutofillFieldClassificationService.Stub {
        private AutofillFieldClassificationServiceWrapper() {
        }

        @Override // android.service.autofill.IAutofillFieldClassificationService
        public void calculateScores(android.os.RemoteCallback remoteCallback, java.util.List<android.view.autofill.AutofillValue> list, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.util.Map map2) throws android.os.RemoteException {
            android.service.autofill.AutofillFieldClassificationService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.NonaConsumer() { // from class: android.service.autofill.AutofillFieldClassificationService$AutofillFieldClassificationServiceWrapper$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.NonaConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9) {
                    ((android.service.autofill.AutofillFieldClassificationService) obj).calculateScores((android.os.RemoteCallback) obj2, (java.util.List) obj3, (java.lang.String[]) obj4, (java.lang.String[]) obj5, (java.lang.String) obj6, (android.os.Bundle) obj7, (java.util.Map) obj8, (java.util.Map) obj9);
                }
            }, android.service.autofill.AutofillFieldClassificationService.this, remoteCallback, list, strArr, strArr2, str, bundle, map, map2));
        }
    }

    public static final class Scores implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.service.autofill.AutofillFieldClassificationService.Scores> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.AutofillFieldClassificationService.Scores>() { // from class: android.service.autofill.AutofillFieldClassificationService.Scores.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.autofill.AutofillFieldClassificationService.Scores createFromParcel(android.os.Parcel parcel) {
                return new android.service.autofill.AutofillFieldClassificationService.Scores(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.autofill.AutofillFieldClassificationService.Scores[] newArray(int i) {
                return new android.service.autofill.AutofillFieldClassificationService.Scores[i];
            }
        };
        public final float[][] scores;

        private Scores(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            this.scores = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, readInt, readInt2);
            for (int i = 0; i < readInt; i++) {
                for (int i2 = 0; i2 < readInt2; i2++) {
                    this.scores[i][i2] = parcel.readFloat();
                }
            }
        }

        private Scores(float[][] fArr) {
            this.scores = fArr;
        }

        public java.lang.String toString() {
            int length = this.scores.length;
            java.lang.StringBuilder append = new java.lang.StringBuilder("Scores [").append(length).append("x").append(length > 0 ? this.scores[0].length : 0).append("] ");
            for (int i = 0; i < length; i++) {
                append.append(i).append(": ").append(java.util.Arrays.toString(this.scores[i])).append(' ');
            }
            return append.toString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            int length = this.scores.length;
            int length2 = this.scores[0].length;
            parcel.writeInt(length);
            parcel.writeInt(length2);
            for (int i2 = 0; i2 < length; i2++) {
                for (int i3 = 0; i3 < length2; i3++) {
                    parcel.writeFloat(this.scores[i2][i3]);
                }
            }
        }
    }
}
