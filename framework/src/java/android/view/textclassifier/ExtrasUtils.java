package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class ExtrasUtils {
    private static final java.lang.String ACTIONS_INTENTS = "actions-intents";
    private static final java.lang.String ACTION_INTENT = "action-intent";
    private static final java.lang.String ENTITY_TYPE = "entity-type";
    private static final java.lang.String FOREIGN_LANGUAGE = "foreign-language";
    private static final java.lang.String MODEL_NAME = "model-name";
    private static final java.lang.String SCORE = "score";

    private ExtrasUtils() {
    }

    public static android.os.Bundle getForeignLanguageExtra(android.view.textclassifier.TextClassification textClassification) {
        if (textClassification == null) {
            return null;
        }
        return textClassification.getExtras().getBundle(FOREIGN_LANGUAGE);
    }

    public static android.content.Intent getActionIntent(android.os.Bundle bundle) {
        return (android.content.Intent) bundle.getParcelable(ACTION_INTENT, android.content.Intent.class);
    }

    public static java.util.ArrayList<android.content.Intent> getActionsIntents(android.view.textclassifier.TextClassification textClassification) {
        if (textClassification == null) {
            return null;
        }
        return textClassification.getExtras().getParcelableArrayList(ACTIONS_INTENTS, android.content.Intent.class);
    }

    private static android.app.RemoteAction findAction(android.view.textclassifier.TextClassification textClassification, java.lang.String str) {
        java.util.ArrayList<android.content.Intent> actionsIntents;
        if (textClassification != null && str != null && (actionsIntents = getActionsIntents(textClassification)) != null) {
            int size = actionsIntents.size();
            for (int i = 0; i < size; i++) {
                android.content.Intent intent = actionsIntents.get(i);
                if (intent != null && str.equals(intent.getAction())) {
                    return textClassification.getActions().get(i);
                }
            }
        }
        return null;
    }

    public static android.app.RemoteAction findTranslateAction(android.view.textclassifier.TextClassification textClassification) {
        return findAction(textClassification, android.content.Intent.ACTION_TRANSLATE);
    }

    public static java.lang.String getEntityType(android.os.Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getString(ENTITY_TYPE);
    }

    public static float getScore(android.os.Bundle bundle) {
        if (bundle == null) {
            return -1.0f;
        }
        return bundle.getFloat(SCORE, -1.0f);
    }

    public static java.lang.String getModelName(android.os.Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getString(MODEL_NAME);
    }
}
