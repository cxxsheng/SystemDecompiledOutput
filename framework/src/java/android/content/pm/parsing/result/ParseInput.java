package android.content.pm.parsing.result;

/* loaded from: classes.dex */
public interface ParseInput {

    public interface Callback {
        boolean isChangeEnabled(long j, java.lang.String str, int i);
    }

    android.content.pm.parsing.result.ParseResult<?> deferError(java.lang.String str, long j);

    android.content.pm.parsing.result.ParseResult<?> enableDeferredError(java.lang.String str, int i);

    <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(int i);

    <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(int i, java.lang.String str);

    <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(int i, java.lang.String str, java.lang.Exception exc);

    <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(android.content.pm.parsing.result.ParseResult<?> parseResult);

    <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> error(java.lang.String str);

    <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> skip(java.lang.String str);

    <ResultType> android.content.pm.parsing.result.ParseResult<ResultType> success(ResultType resulttype);

    public static final class DeferredError {
        public static final long EMPTY_INTENT_ACTION_CATEGORY = 151163173;
        public static final long MISSING_APP_TAG = 150776642;
        public static final long MISSING_EXPORTED_FLAG = 150232615;
        public static final long RESOURCES_ARSC_COMPRESSED = 132742131;

        public static int getTargetSdkForChange(long j) {
            if (j == MISSING_APP_TAG || j == EMPTY_INTENT_ACTION_CATEGORY || j == RESOURCES_ARSC_COMPRESSED) {
                return 29;
            }
            if (j == MISSING_EXPORTED_FLAG) {
                return 30;
            }
            return -1;
        }
    }
}
