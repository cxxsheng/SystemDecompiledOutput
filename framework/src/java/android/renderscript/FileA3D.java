package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class FileA3D extends android.renderscript.BaseObj {
    android.renderscript.FileA3D.IndexEntry[] mFileEntries;
    java.io.InputStream mInputStream;

    public enum EntryType {
        UNKNOWN(0),
        MESH(1);

        int mID;

        EntryType(int i) {
            this.mID = i;
        }

        static android.renderscript.FileA3D.EntryType toEntryType(int i) {
            return values()[i];
        }
    }

    public static class IndexEntry {
        android.renderscript.FileA3D.EntryType mEntryType;
        long mID;
        int mIndex;
        android.renderscript.BaseObj mLoadedObj = null;
        java.lang.String mName;
        android.renderscript.RenderScript mRS;

        public java.lang.String getName() {
            return this.mName;
        }

        public android.renderscript.FileA3D.EntryType getEntryType() {
            return this.mEntryType;
        }

        public android.renderscript.BaseObj getObject() {
            this.mRS.validate();
            return internalCreate(this.mRS, this);
        }

        public android.renderscript.Mesh getMesh() {
            return (android.renderscript.Mesh) getObject();
        }

        static synchronized android.renderscript.BaseObj internalCreate(android.renderscript.RenderScript renderScript, android.renderscript.FileA3D.IndexEntry indexEntry) {
            synchronized (android.renderscript.FileA3D.IndexEntry.class) {
                if (indexEntry.mLoadedObj != null) {
                    return indexEntry.mLoadedObj;
                }
                if (indexEntry.mEntryType == android.renderscript.FileA3D.EntryType.UNKNOWN) {
                    return null;
                }
                long nFileA3DGetEntryByIndex = renderScript.nFileA3DGetEntryByIndex(indexEntry.mID, indexEntry.mIndex);
                if (nFileA3DGetEntryByIndex == 0) {
                    return null;
                }
                switch (indexEntry.mEntryType) {
                    case MESH:
                        indexEntry.mLoadedObj = new android.renderscript.Mesh(nFileA3DGetEntryByIndex, renderScript);
                        indexEntry.mLoadedObj.updateFromNative();
                        return indexEntry.mLoadedObj;
                    default:
                        throw new android.renderscript.RSRuntimeException("Unrecognized object type in file.");
                }
            }
        }

        IndexEntry(android.renderscript.RenderScript renderScript, int i, long j, java.lang.String str, android.renderscript.FileA3D.EntryType entryType) {
            this.mRS = renderScript;
            this.mIndex = i;
            this.mID = j;
            this.mName = str;
            this.mEntryType = entryType;
        }
    }

    FileA3D(long j, android.renderscript.RenderScript renderScript, java.io.InputStream inputStream) {
        super(j, renderScript);
        this.mInputStream = inputStream;
        this.guard.open("destroy");
    }

    private void initEntries() {
        int nFileA3DGetNumIndexEntries = this.mRS.nFileA3DGetNumIndexEntries(getID(this.mRS));
        if (nFileA3DGetNumIndexEntries <= 0) {
            return;
        }
        this.mFileEntries = new android.renderscript.FileA3D.IndexEntry[nFileA3DGetNumIndexEntries];
        int[] iArr = new int[nFileA3DGetNumIndexEntries];
        java.lang.String[] strArr = new java.lang.String[nFileA3DGetNumIndexEntries];
        this.mRS.nFileA3DGetIndexEntries(getID(this.mRS), nFileA3DGetNumIndexEntries, iArr, strArr);
        for (int i = 0; i < nFileA3DGetNumIndexEntries; i++) {
            this.mFileEntries[i] = new android.renderscript.FileA3D.IndexEntry(this.mRS, i, getID(this.mRS), strArr[i], android.renderscript.FileA3D.EntryType.toEntryType(iArr[i]));
        }
    }

    public int getIndexEntryCount() {
        if (this.mFileEntries == null) {
            return 0;
        }
        return this.mFileEntries.length;
    }

    public android.renderscript.FileA3D.IndexEntry getIndexEntry(int i) {
        if (getIndexEntryCount() == 0 || i < 0 || i >= this.mFileEntries.length) {
            return null;
        }
        return this.mFileEntries[i];
    }

    public static android.renderscript.FileA3D createFromAsset(android.renderscript.RenderScript renderScript, android.content.res.AssetManager assetManager, java.lang.String str) {
        renderScript.validate();
        long nFileA3DCreateFromAsset = renderScript.nFileA3DCreateFromAsset(assetManager, str);
        if (nFileA3DCreateFromAsset == 0) {
            throw new android.renderscript.RSRuntimeException("Unable to create a3d file from asset " + str);
        }
        android.renderscript.FileA3D fileA3D = new android.renderscript.FileA3D(nFileA3DCreateFromAsset, renderScript, null);
        fileA3D.initEntries();
        return fileA3D;
    }

    public static android.renderscript.FileA3D createFromFile(android.renderscript.RenderScript renderScript, java.lang.String str) {
        long nFileA3DCreateFromFile = renderScript.nFileA3DCreateFromFile(str);
        if (nFileA3DCreateFromFile == 0) {
            throw new android.renderscript.RSRuntimeException("Unable to create a3d file from " + str);
        }
        android.renderscript.FileA3D fileA3D = new android.renderscript.FileA3D(nFileA3DCreateFromFile, renderScript, null);
        fileA3D.initEntries();
        return fileA3D;
    }

    public static android.renderscript.FileA3D createFromFile(android.renderscript.RenderScript renderScript, java.io.File file) {
        return createFromFile(renderScript, file.getAbsolutePath());
    }

    public static android.renderscript.FileA3D createFromResource(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, int i) {
        renderScript.validate();
        try {
            java.io.InputStream openRawResource = resources.openRawResource(i);
            if (openRawResource instanceof android.content.res.AssetManager.AssetInputStream) {
                long nFileA3DCreateFromAssetStream = renderScript.nFileA3DCreateFromAssetStream(((android.content.res.AssetManager.AssetInputStream) openRawResource).getNativeAsset());
                if (nFileA3DCreateFromAssetStream == 0) {
                    throw new android.renderscript.RSRuntimeException("Unable to create a3d file from resource " + i);
                }
                android.renderscript.FileA3D fileA3D = new android.renderscript.FileA3D(nFileA3DCreateFromAssetStream, renderScript, openRawResource);
                fileA3D.initEntries();
                return fileA3D;
            }
            throw new android.renderscript.RSRuntimeException("Unsupported asset stream");
        } catch (java.lang.Exception e) {
            throw new android.renderscript.RSRuntimeException("Unable to open resource " + i);
        }
    }
}
