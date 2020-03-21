package com.zkl.l_music.bo;

import lombok.Data;
import sun.security.util.ManifestEntryVerifier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginBo implements Serializable {

    @NotBlank(message = "phone is null")
    private String phone;
    @NotBlank(message = "password is null")
    private String password;
}
