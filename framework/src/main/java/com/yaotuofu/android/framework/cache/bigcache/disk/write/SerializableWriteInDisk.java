/*
 * 文 件 名:  StrigWriteInCache.java
 * 版    权:  Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  jiangyufeng
 * 修改时间:  2015年12月9日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.yaotuofu.android.framework.cache.bigcache.disk.write;

import com.yaotuofu.android.framework.baseutils.L;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.yaotuofu.android.framework.cache.bigcache.other.IoUtils;

/**
 * 把string 写入OutputStream中
 * 
 * @author jiangyufeng
 * @version [版本号, 2015年12月9日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SerializableWriteInDisk<V extends Serializable> extends WriteInDisk<V> {

	@Override
	public boolean writeIn(OutputStream out, V values){
		ObjectOutputStream objectOut = null;
		boolean isSucce = false;
		try {
			objectOut = new ObjectOutputStream(out);
			objectOut.writeObject(values);
			objectOut.flush();
			isSucce=true;
		} catch (IOException e) {
			L.e(e +  "Serialzable写入缓存错误");
		}catch (Exception e) {
			L.e(e +  "Serialzable写入缓存错误");
		}finally{
			IoUtils.closeSilently(objectOut);
		}
		return isSucce;
	}

}
