package com.gjzg.listener;

/**
 * Created by Administrator on 2017/10/26.
 */

public interface JsonListener {

    void success(String json);

    void failure(String failure);
}
