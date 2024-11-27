package android.filterfw.io;

/* loaded from: classes.dex */
public abstract class GraphReader {
    protected android.filterfw.core.KeyValueMap mReferences = new android.filterfw.core.KeyValueMap();

    public abstract android.filterfw.core.FilterGraph readGraphString(java.lang.String str) throws android.filterfw.io.GraphIOException;

    public abstract android.filterfw.core.KeyValueMap readKeyValueAssignments(java.lang.String str) throws android.filterfw.io.GraphIOException;

    public android.filterfw.core.FilterGraph readGraphResource(android.content.Context context, int i) throws android.filterfw.io.GraphIOException {
        java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(context.getResources().openRawResource(i));
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        char[] cArr = new char[1024];
        while (true) {
            try {
                int read = inputStreamReader.read(cArr, 0, 1024);
                if (read > 0) {
                    stringWriter.write(cArr, 0, read);
                } else {
                    return readGraphString(stringWriter.toString());
                }
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException("Could not read specified resource file!");
            }
        }
    }

    public void addReference(java.lang.String str, java.lang.Object obj) {
        this.mReferences.put(str, obj);
    }

    public void addReferencesByMap(android.filterfw.core.KeyValueMap keyValueMap) {
        this.mReferences.putAll(keyValueMap);
    }

    public void addReferencesByKeysAndValues(java.lang.Object... objArr) {
        this.mReferences.setKeyValues(objArr);
    }
}
