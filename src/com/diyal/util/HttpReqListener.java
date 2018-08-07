package com.diyal.util;

import java.io.InputStream;

public interface HttpReqListener<T> {
	// 下载线程接口
	public T loadSuccess(InputStream is);

	// UI线程接口
	public void onSuccess(T uiData);

	public void onFailure(Throwable error);

	public void onFinished();
}
