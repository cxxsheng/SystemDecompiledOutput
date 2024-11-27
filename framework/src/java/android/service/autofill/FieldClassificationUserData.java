package android.service.autofill;

/* loaded from: classes3.dex */
public interface FieldClassificationUserData {
    java.lang.String[] getCategoryIds();

    android.os.Bundle getDefaultFieldClassificationArgs();

    java.lang.String getFieldClassificationAlgorithm();

    java.lang.String getFieldClassificationAlgorithmForCategory(java.lang.String str);

    android.util.ArrayMap<java.lang.String, java.lang.String> getFieldClassificationAlgorithms();

    android.util.ArrayMap<java.lang.String, android.os.Bundle> getFieldClassificationArgs();

    java.lang.String[] getValues();
}
