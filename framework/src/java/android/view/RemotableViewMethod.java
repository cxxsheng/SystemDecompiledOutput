package android.view;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
/* loaded from: classes4.dex */
public @interface RemotableViewMethod {
    java.lang.String asyncImpl() default "";
}
