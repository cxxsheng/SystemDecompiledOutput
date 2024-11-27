package android.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface BroadcastBehavior {
    boolean explicitOnly() default false;

    boolean includeBackground() default false;

    boolean protectedBroadcast() default false;

    boolean registeredOnly() default false;
}
