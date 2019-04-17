package com.shyfay.user.utils;

import com.shyfay.user.enums.ResultEnum;
import com.shyfay.user.vo.ResultVO;
import org.springframework.http.HttpStatus;

/**
 * @author mx
 * @since 2019/4/17
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(HttpStatus.OK.value());
        resultVO.setMessage("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(HttpStatus.OK.value());
        resultVO.setMessage("成功");
        return resultVO;
    }

    public static ResultVO error(ResultEnum resultEnum){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMessage(resultEnum.getMessage());
        return resultVO;
    }


}
