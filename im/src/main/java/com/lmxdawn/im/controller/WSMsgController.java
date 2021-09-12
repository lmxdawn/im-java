package com.lmxdawn.im.controller;

import com.lmxdawn.common.enums.ResultEnum;
import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.common.util.ResultVOUtils;
import com.lmxdawn.imcommon.req.ImMsgReq;
import com.lmxdawn.im.res.WSBaseRes;
import com.lmxdawn.im.res.WSMessageRes;
import com.lmxdawn.im.res.WSUserRes;
import com.lmxdawn.im.ws.WSServer;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/msg")
public class WSMsgController {

    @Resource
    private WSServer wsServer;

    @PostMapping("/push")
    public BaseResponse push(@RequestBody @Valid ImMsgReq imMsgReq,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        WSBaseRes wsBaseRes = new WSBaseRes();
        wsBaseRes.setType(imMsgReq.getType());
        wsBaseRes.setGid(imMsgReq.getGid());
        wsBaseRes.setReceiveId(imMsgReq.getReceiveId());

        WSMessageRes wsMessageRes = new WSMessageRes();
        wsMessageRes.setMsgType(imMsgReq.getMsgType());
        wsMessageRes.setMsgContent(imMsgReq.getMsgContent());
        wsBaseRes.setMessage(wsMessageRes);

        WSUserRes wsUserRes = new WSUserRes();
        wsUserRes.setUid(imMsgReq.getUid());
        wsUserRes.setName(imMsgReq.getName());
        wsUserRes.setAvatar(imMsgReq.getAvatar());
        wsUserRes.setRemark(imMsgReq.getRemark());
        wsBaseRes.setUser(wsUserRes);

        Boolean aBoolean = wsServer.sendMsg(imMsgReq.getReceiveId(), wsBaseRes);


        return  ResultVOUtils.success(aBoolean);
    }

}
