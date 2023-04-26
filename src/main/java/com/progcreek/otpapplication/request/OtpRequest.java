package com.progcreek.otpapplication.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtpRequest {

    @NonNull
    private String otpType;

    private String email;

    private String phone;

}
