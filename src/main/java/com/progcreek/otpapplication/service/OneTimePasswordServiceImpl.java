package com.progcreek.otpapplication.service;

import com.progcreek.otpapplication.dto.PaylodDto;
import com.progcreek.otpapplication.manager.OneTimePasswordHelper;
import com.progcreek.otpapplication.models.OneTimePassword;
import com.progcreek.otpapplication.repository.OneTimePasswordRepository;
import com.progcreek.otpapplication.request.OtpRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class OneTimePasswordServiceImpl implements OneTimePasswordService {

    private static final Logger log = LogManager.getLogger(OneTimePasswordServiceImpl.class);
    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailService emailService;

    private static Long expiryInterval = 5L*60*1000;
    @Autowired
    private OneTimePasswordRepository oneTimePasswordRepository;
    @Autowired
    private OneTimePasswordHelper oneTimePasswordHelper;

    @Async
    @Override
    public PaylodDto getOneTimePassword(OtpRequest otpRequest) throws IOException {

        OneTimePassword oneTimePassword = new OneTimePassword();

        oneTimePassword.setOneTimePasswordCode(oneTimePasswordHelper.createRandomOneTimePassword());
        oneTimePassword.setExpires(new Date(System.currentTimeMillis() + expiryInterval));

        oneTimePasswordRepository.save(oneTimePassword);

        PaylodDto paylodDto = new PaylodDto(oneTimePassword.getOneTimePasswordCode(), oneTimePassword.getExpires());

        if(otpRequest.getOtpType().equals("phone")) {
            if(otpRequest.getPhone().isEmpty())
                throw new IOException("Invalid: Phone");

            smsService.sendSms(otpRequest.getPhone(),
                    oneTimePasswordHelper.getMessageBody(String.valueOf(paylodDto.getOneTimePasswordCode()),
                            oneTimePassword.getExpires()));

        } else if(otpRequest.getOtpType().equals("email")) {
            if(otpRequest.getEmail().isEmpty())
                throw new IOException("Invalid: Email");

            emailService.sendEmail(otpRequest.getEmail(),
                    "Progcreek: (OTP). Your OTP is following that will be valid for 5 minutes!",
                    oneTimePasswordHelper.getMessageBody(String.valueOf(paylodDto.getOneTimePasswordCode()),
                            oneTimePassword.getExpires()));

        } else if(otpRequest.getOtpType().equals("both")) {

            if(otpRequest.getPhone().isEmpty())
                throw new IOException("Invalid: Phone");
            if(otpRequest.getEmail().isEmpty())
                throw new IOException("Invalid: Email");

            smsService.sendSms(otpRequest.getPhone(),
                    oneTimePasswordHelper.getMessageBody(String.valueOf(paylodDto.getOneTimePasswordCode()),
                            oneTimePassword.getExpires()));
            emailService.sendEmail(otpRequest.getEmail(),
                    "Progcreek: (OTP). Your OTP is following that will be valid for 5 minutes!",
                    oneTimePasswordHelper.getMessageBody(String.valueOf(paylodDto.getOneTimePasswordCode()),
                            oneTimePassword.getExpires()));
        }

        return paylodDto;

    }
}
