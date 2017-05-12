package com.yaotuofu.android.framework.cache.bigcache.disk.write;

import com.yaotuofu.android.framework.baseutils.L;
import com.yaotuofu.android.framework.cache.bigcache.other.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by FelixFang on 10/25/16.
 */

public class FileWriteInDisk extends WriteInDisk<InputStream> {

    private static final int DEFAULT_BUFFER_SIZE = 4 * 1024; // 4 Kb

    /** 缓冲流大小 */
    private int bufferSize = DEFAULT_BUFFER_SIZE;

    private IoUtils.CopyListener mListener;

    public FileWriteInDisk(IoUtils.CopyListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean writeIn(OutputStream out, InputStream values) {
        boolean isSucess = false;
        try {
            isSucess = IoUtils.copyStream(values, out, mListener,
                    bufferSize);
            out.flush();
        } catch (IOException e) {
            L.e(e + "InputStream写入缓存错误");
        } catch (Exception e) {
            L.e(e + "InputStream写入缓存错误");
        } finally {
            IoUtils.closeSilently(out);
        }
        return isSucess;

    }

    /**
     * 设置缓冲流大小
     *
     * @param bufferSize
     *            void
     * @throws
     * @see [类、类#方法、类#成员]
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
