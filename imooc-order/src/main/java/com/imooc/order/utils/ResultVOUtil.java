package com.imooc.order.utils;

import com.imooc.order.vo.ResultVO;

/**
 * Created by mx
 * 2019-03-23 18:03
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }
}
