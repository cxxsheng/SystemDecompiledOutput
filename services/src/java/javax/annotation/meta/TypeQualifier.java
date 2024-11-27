package javax.annotation.meta;

@java.lang.annotation.Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
@java.lang.annotation.Documented
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface TypeQualifier {
    java.lang.Class<?> applicableTo() default java.lang.Object.class;
}
