package android.view.translation;

/* loaded from: classes4.dex */
public interface ViewTranslationCallback {
    boolean onClearTranslation(android.view.View view);

    boolean onHideTranslation(android.view.View view);

    boolean onShowTranslation(android.view.View view);

    default void enableContentPadding() {
    }

    default void setAnimationDurationMillis(int i) {
    }
}
