package com.android.internal.util;

@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
/* loaded from: classes5.dex */
public @interface DataClass {

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.LOCAL_VARIABLE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Each {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
    @java.lang.Deprecated
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Generated {

        @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.TYPE})
        @java.lang.Deprecated
        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Member {
        }

        java.lang.String codegenVersion();

        java.lang.String inputSignatures() default "";

        java.lang.String sourceFile();

        long time();
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MaySetToNull {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ParcelWith {
        java.lang.Class<? extends com.android.internal.util.Parcelling> value();
    }

    public interface PerIntFieldAction<THIS> {
        void acceptInt(THIS r1, java.lang.String str, int i);
    }

    public interface PerObjectFieldAction<THIS> {
        void acceptObject(THIS r1, java.lang.String str, java.lang.Object obj);
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PluralOf {
        java.lang.String value();
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Suppress {
        java.lang.String[] value();
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SuppressConstDefsGeneration {
    }

    boolean genAidl() default false;

    boolean genBuilder() default false;

    boolean genConstDefs() default true;

    boolean genConstructor() default true;

    boolean genCopyConstructor() default false;

    boolean genEqualsHashCode() default false;

    boolean genForEachField() default false;

    boolean genGetters() default true;

    boolean genHiddenBuilder() default false;

    boolean genHiddenConstDefs() default false;

    boolean genHiddenConstructor() default false;

    boolean genHiddenCopyConstructor() default false;

    boolean genHiddenGetters() default false;

    boolean genHiddenSetters() default false;

    boolean genParcelable() default false;

    boolean genSetters() default false;

    boolean genToString() default false;
}
