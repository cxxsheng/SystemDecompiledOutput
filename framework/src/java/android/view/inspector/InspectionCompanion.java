package android.view.inspector;

/* loaded from: classes4.dex */
public interface InspectionCompanion<T> {
    void mapProperties(android.view.inspector.PropertyMapper propertyMapper);

    void readProperties(T t, android.view.inspector.PropertyReader propertyReader);

    public static class UninitializedPropertyMapException extends java.lang.RuntimeException {
        public UninitializedPropertyMapException() {
            super("Unable to read properties of an inspectable before mapping their IDs.");
        }
    }
}
