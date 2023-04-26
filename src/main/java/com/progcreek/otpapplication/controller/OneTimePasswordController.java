package com.progcreek.otpapplication.controller;

import com.progcreek.otpapplication.dto.PaylodDto;
import com.progcreek.otpapplication.global.ResponseCodes;
import com.progcreek.otpapplication.global.ResponseMessages;
import com.progcreek.otpapplication.request.OtpRequest;
import com.progcreek.otpapplication.response.Reply;
import com.progcreek.otpapplication.service.OneTimePasswordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/otp")
public class OneTimePasswordController {

    private static final Logger log = LogManager.getLogger(OneTimePasswordController.class);
    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    @PostMapping(  "/v1/generateOtp")
    @ResponseBody
    public Reply generateOtp(@RequestBody OtpRequest otpRequest) throws IOException {
        try {

            PaylodDto paylodDto = oneTimePasswordService.getOneTimePassword(otpRequest);

            if(paylodDto != null)
                return new Reply(true, ResponseCodes.OK,ResponseMessages.OK, paylodDto);
            else
                return new Reply(false, ResponseCodes.Failed, ResponseMessages.Failed, null, null);

        } catch (Exception ex) {
            log.error("error: {}", ex);
            throw ex;
        }
    }

}
