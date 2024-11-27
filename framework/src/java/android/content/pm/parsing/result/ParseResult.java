package android.content.pm.parsing.result;

/* loaded from: classes.dex */
public interface ParseResult<ResultType> {
    int getErrorCode();

    java.lang.String getErrorMessage();

    java.lang.Exception getException();

    ResultType getResult();

    boolean isError();

    boolean isSuccess();
}
