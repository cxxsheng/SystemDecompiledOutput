package android.app;

/* loaded from: classes.dex */
interface ActivityThreadInternal {
    java.util.ArrayList<android.content.ComponentCallbacks2> collectComponentCallbacks(boolean z);

    android.app.Application getApplication();

    android.app.ContextImpl getSystemContext();

    android.app.ContextImpl getSystemUiContextNoCreate();

    boolean isInDensityCompatMode();
}
