package android.view.inspector;

/* loaded from: classes4.dex */
public class StaticInspectionCompanionProvider implements android.view.inspector.InspectionCompanionProvider {
    private static final java.lang.String COMPANION_SUFFIX = "$InspectionCompanion";

    @Override // android.view.inspector.InspectionCompanionProvider
    public <T> android.view.inspector.InspectionCompanion<T> provide(java.lang.Class<T> cls) {
        try {
            java.lang.Class<?> loadClass = cls.getClassLoader().loadClass(cls.getName() + COMPANION_SUFFIX);
            if (!android.view.inspector.InspectionCompanion.class.isAssignableFrom(loadClass)) {
                return null;
            }
            return (android.view.inspector.InspectionCompanion) loadClass.newInstance();
        } catch (java.lang.ClassNotFoundException e) {
            return null;
        } catch (java.lang.IllegalAccessException e2) {
            throw new java.lang.RuntimeException(e2);
        } catch (java.lang.InstantiationException e3) {
            java.lang.Throwable cause = e3.getCause();
            if (cause instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) cause);
            }
            if (cause instanceof java.lang.Error) {
                throw ((java.lang.Error) cause);
            }
            throw new java.lang.RuntimeException(cause);
        }
    }
}
