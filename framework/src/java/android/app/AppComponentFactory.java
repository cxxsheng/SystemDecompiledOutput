package android.app;

/* loaded from: classes.dex */
public class AppComponentFactory {
    public static final android.app.AppComponentFactory DEFAULT = new android.app.AppComponentFactory();

    public java.lang.ClassLoader instantiateClassLoader(java.lang.ClassLoader classLoader, android.content.pm.ApplicationInfo applicationInfo) {
        return classLoader;
    }

    public android.app.Application instantiateApplication(java.lang.ClassLoader classLoader, java.lang.String str) throws java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException {
        return (android.app.Application) classLoader.loadClass(str).newInstance();
    }

    public android.app.Activity instantiateActivity(java.lang.ClassLoader classLoader, java.lang.String str, android.content.Intent intent) throws java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException {
        return (android.app.Activity) classLoader.loadClass(str).newInstance();
    }

    public android.content.BroadcastReceiver instantiateReceiver(java.lang.ClassLoader classLoader, java.lang.String str, android.content.Intent intent) throws java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException {
        return (android.content.BroadcastReceiver) classLoader.loadClass(str).newInstance();
    }

    public android.app.Service instantiateService(java.lang.ClassLoader classLoader, java.lang.String str, android.content.Intent intent) throws java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException {
        return (android.app.Service) classLoader.loadClass(str).newInstance();
    }

    public android.content.ContentProvider instantiateProvider(java.lang.ClassLoader classLoader, java.lang.String str) throws java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException {
        return (android.content.ContentProvider) classLoader.loadClass(str).newInstance();
    }
}
