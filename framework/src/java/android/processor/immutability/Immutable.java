package android.processor.immutability;

/* loaded from: classes3.dex */
public @interface Immutable {

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.CLASS)
    public @interface Ignore {
        java.lang.String reason() default "";
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.CLASS)
    public @interface Policy {

        public enum Exception {
            FINAL_CLASSES_WITH_FINAL_FIELDS
        }

        android.processor.immutability.Immutable.Policy.Exception[] exceptions() default {};
    }
}
