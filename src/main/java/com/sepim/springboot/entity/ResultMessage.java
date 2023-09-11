package com.sepim.springboot.entity;

import lombok.Data;

/**
 * @author: SorrowPhage
 * @date: 2023/9/6
 */
@Data
public class ResultMessage {

    /**
     * 结果标识
     * <p>
     * true执行成功，false执行失败
     */
    private boolean flag = false;
    /**
     * 结果消息
     */
    private String message = "";
    /**
     * 结果数据
     */
    private Object data = null;

    /**
     * 是否分页
     */
    private boolean paging = false;

    /**
     * 结果编码
     */
    private int code = 200;

    /**
     * 构造方法
     *
     * @param flag    执行结果标识
     * @param message 执行结果消息
     * @param data    执行结果数据
     */
    public ResultMessage(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param flag    执行结果标识
     * @param message 执行结果消息
     * @param data    执行结果数据
     */
    private ResultMessage(boolean flag, String message, Object data, int code, boolean paging) {
        this.flag = flag;
        this.message = message;
        this.data = data;
        this.paging = paging;
        this.code = code;
    }

    /**
     * 执行成功，返回消息和数据
     *
     * @param message 执行结果消息
     * @param data    执行结果数据
     * @return 结果信息
     */
    public static ResultMessage success(String message, Object data) {
        return new ResultMessage(true, message, data, 200, false);
    }

    /**
     * 执行成功，返回消息和数据
     *
     * @param message 执行结果消息
     * @param data    执行结果数据
     * @param paging  是否分页结果
     * @return 结果信息
     */
    public static ResultMessage success(String message, Object data, boolean paging) {
        return new ResultMessage(true, message, data, 200, paging);
    }

    /**
     * 执行成功，返回消息
     *
     * @param message 执行结果消息
     * @return 结果信息
     */
    public static ResultMessage success(String message) {
        return ResultMessage.success(message, null);
    }

    /**
     * 执行成功，返回数据
     *
     * @param data 执行结果数据
     * @return 结果信息
     */
    public static ResultMessage success(Object data) {
        return ResultMessage.success(null, data);
    }

    /**
     * 执行成功，返回数据
     *
     * @param data   执行结果数据
     * @param paging 是否分页结果
     * @return 结果信息
     */
    public static ResultMessage success(Object data, boolean paging) {
        return ResultMessage.success(null, data, paging);
    }

    /**
     * 执行失败，返回消息和数据
     *
     * @param message 执行结果消息
     * @param data    执行结果数据
     * @return 结果信息
     */
    public static ResultMessage failure(String message, Object data) {
        return new ResultMessage(false, message, data, 500, false);
    }

    /**
     * 执行失败，返回消息和数据
     *
     * @param message 执行结果消息
     * @param data    执行结果数据
     * @param paging  是否分页结果
     * @return 结果信息
     */
    public static ResultMessage failure(String message, Object data, boolean paging) {
        return new ResultMessage(false, message, data, 500, paging);
    }

    /**
     * 执行失败，返回消息
     *
     * @param message 执行结果消息
     * @return 结果信息
     */
    public static ResultMessage failure(String message) {
        return ResultMessage.failure(message, null);
    }

    /**
     * 执行失败，返回数据
     *
     * @param data 执行结果数据
     * @return 结果信息
     */
    public static ResultMessage failure(Object data) {
        return ResultMessage.success(null, data);
    }


    /**
     * 执行失败，返回数据
     *
     * @param data   执行结果数据
     * @param paging 是否分页结果
     * @return 结果信息
     */
    public static ResultMessage failure(Object data, boolean paging) {
        return ResultMessage.success(null, data, paging);
    }

}
