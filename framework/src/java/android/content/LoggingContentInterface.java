package android.content;

/* loaded from: classes.dex */
public class LoggingContentInterface implements android.content.ContentInterface {
    private final android.content.ContentInterface delegate;
    private final java.lang.String tag;

    public LoggingContentInterface(java.lang.String str, android.content.ContentInterface contentInterface) {
        this.tag = str;
        this.delegate = contentInterface;
    }

    private class Logger implements java.lang.AutoCloseable {
        private final java.lang.StringBuilder sb = new java.lang.StringBuilder();

        public Logger(java.lang.String str, java.lang.Object... objArr) {
            for (java.lang.Object obj : objArr) {
                if (obj instanceof android.os.Bundle) {
                    ((android.os.Bundle) obj).size();
                }
            }
            this.sb.append("callingUid=").append(android.os.Binder.getCallingUid()).append(' ');
            this.sb.append(str);
            this.sb.append('(').append(deepToString(objArr)).append(')');
        }

        private java.lang.String deepToString(java.lang.Object obj) {
            if (obj != null && obj.getClass().isArray()) {
                return java.util.Arrays.deepToString((java.lang.Object[]) obj);
            }
            return java.lang.String.valueOf(obj);
        }

        public <T> T setResult(T t) {
            if (t instanceof android.database.Cursor) {
                this.sb.append('\n');
                android.database.DatabaseUtils.dumpCursor((android.database.Cursor) t, this.sb);
            } else {
                this.sb.append(" = ").append(deepToString(t));
            }
            return t;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            android.util.Log.v(android.content.LoggingContentInterface.this.tag, this.sb.toString());
        }
    }

    @Override // android.content.ContentInterface
    public android.database.Cursor query(android.net.Uri uri, java.lang.String[] strArr, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("query", uri, strArr, bundle, cancellationSignal);
        try {
            try {
                android.database.Cursor cursor = (android.database.Cursor) logger.setResult(this.delegate.query(uri, strArr, bundle, cancellationSignal));
                logger.close();
                return cursor;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public java.lang.String getType(android.net.Uri uri) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("getType", uri);
        try {
            try {
                java.lang.String str = (java.lang.String) logger.setResult(this.delegate.getType(uri));
                logger.close();
                return str;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public java.lang.String[] getStreamTypes(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("getStreamTypes", uri, str);
        try {
            try {
                java.lang.String[] strArr = (java.lang.String[]) logger.setResult(this.delegate.getStreamTypes(uri, str));
                logger.close();
                return strArr;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public android.net.Uri canonicalize(android.net.Uri uri) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("canonicalize", uri);
        try {
            try {
                android.net.Uri uri2 = (android.net.Uri) logger.setResult(this.delegate.canonicalize(uri));
                logger.close();
                return uri2;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public android.net.Uri uncanonicalize(android.net.Uri uri) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("uncanonicalize", uri);
        try {
            try {
                android.net.Uri uri2 = (android.net.Uri) logger.setResult(this.delegate.uncanonicalize(uri));
                logger.close();
                return uri2;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public boolean refresh(android.net.Uri uri, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("refresh", uri, bundle, cancellationSignal);
        try {
            try {
                boolean booleanValue = ((java.lang.Boolean) logger.setResult(java.lang.Boolean.valueOf(this.delegate.refresh(uri, bundle, cancellationSignal)))).booleanValue();
                logger.close();
                return booleanValue;
            } catch (java.lang.Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (java.lang.Throwable th) {
            try {
                logger.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public int checkUriPermission(android.net.Uri uri, int i, int i2) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("checkUriPermission", uri, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        try {
            try {
                int intValue = ((java.lang.Integer) logger.setResult(java.lang.Integer.valueOf(this.delegate.checkUriPermission(uri, i, i2)))).intValue();
                logger.close();
                return intValue;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public android.net.Uri insert(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("insert", uri, contentValues, bundle);
        try {
            try {
                android.net.Uri uri2 = (android.net.Uri) logger.setResult(this.delegate.insert(uri, contentValues, bundle));
                logger.close();
                return uri2;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public int bulkInsert(android.net.Uri uri, android.content.ContentValues[] contentValuesArr) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("bulkInsert", uri, contentValuesArr);
        try {
            try {
                int intValue = ((java.lang.Integer) logger.setResult(java.lang.Integer.valueOf(this.delegate.bulkInsert(uri, contentValuesArr)))).intValue();
                logger.close();
                return intValue;
            } catch (java.lang.Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (java.lang.Throwable th) {
            try {
                logger.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public int delete(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("delete", uri, bundle);
        try {
            try {
                int intValue = ((java.lang.Integer) logger.setResult(java.lang.Integer.valueOf(this.delegate.delete(uri, bundle)))).intValue();
                logger.close();
                return intValue;
            } catch (java.lang.Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (java.lang.Throwable th) {
            try {
                logger.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public int update(android.net.Uri uri, android.content.ContentValues contentValues, android.os.Bundle bundle) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("update", uri, contentValues, bundle);
        try {
            try {
                int intValue = ((java.lang.Integer) logger.setResult(java.lang.Integer.valueOf(this.delegate.update(uri, contentValues, bundle)))).intValue();
                logger.close();
                return intValue;
            } catch (java.lang.Exception e) {
                logger.setResult(e);
                throw e;
            }
        } catch (java.lang.Throwable th) {
            try {
                logger.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // android.content.ContentInterface
    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("openFile", uri, str, cancellationSignal);
        try {
            try {
                android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) logger.setResult(this.delegate.openFile(uri, str, cancellationSignal));
                logger.close();
                return parcelFileDescriptor;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public android.content.res.AssetFileDescriptor openAssetFile(android.net.Uri uri, java.lang.String str, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("openAssetFile", uri, str, cancellationSignal);
        try {
            try {
                android.content.res.AssetFileDescriptor assetFileDescriptor = (android.content.res.AssetFileDescriptor) logger.setResult(this.delegate.openAssetFile(uri, str, cancellationSignal));
                logger.close();
                return assetFileDescriptor;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public android.content.res.AssetFileDescriptor openTypedAssetFile(android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal) throws android.os.RemoteException, java.io.FileNotFoundException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("openTypedAssetFile", uri, str, bundle, cancellationSignal);
        try {
            try {
                android.content.res.AssetFileDescriptor assetFileDescriptor = (android.content.res.AssetFileDescriptor) logger.setResult(this.delegate.openTypedAssetFile(uri, str, bundle, cancellationSignal));
                logger.close();
                return assetFileDescriptor;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public android.content.ContentProviderResult[] applyBatch(java.lang.String str, java.util.ArrayList<android.content.ContentProviderOperation> arrayList) throws android.os.RemoteException, android.content.OperationApplicationException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("applyBatch", str, arrayList);
        try {
            try {
                android.content.ContentProviderResult[] contentProviderResultArr = (android.content.ContentProviderResult[]) logger.setResult(this.delegate.applyBatch(str, arrayList));
                logger.close();
                return contentProviderResultArr;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }

    @Override // android.content.ContentInterface
    public android.os.Bundle call(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle) throws android.os.RemoteException {
        android.content.LoggingContentInterface.Logger logger = new android.content.LoggingContentInterface.Logger("call", str, str2, str3, bundle);
        try {
            try {
                android.os.Bundle bundle2 = (android.os.Bundle) logger.setResult(this.delegate.call(str, str2, str3, bundle));
                logger.close();
                return bundle2;
            } catch (java.lang.Throwable th) {
                try {
                    logger.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.lang.Exception e) {
            logger.setResult(e);
            throw e;
        }
    }
}
