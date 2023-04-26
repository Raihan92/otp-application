package com.progcreek.otpapplication.service;

import com.progcreek.otpapplication.dto.PaylodDto;
import com.progcreek.otpapplication.request.OtpRequest;

import java.io.IOException;

public interface OneTimePasswordService {

    PaylodDto getOneTimePassword(OtpRequest otpRequest) throws IOException;

}
