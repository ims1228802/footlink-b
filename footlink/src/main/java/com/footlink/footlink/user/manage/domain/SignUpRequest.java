package com.footlink.footlink.user.manage.domain;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SignUpRequest {

	private String id;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	@Size(min = 8, max = 15)
	private String password;
	private String confirmPassword;
	
	@NotBlank
	@Pattern(regexp = "^[0-9]{11}$")
	private String phone;
	
	private String birth;
	private String gender;

    private String city;
    private String district;
	
	@NotBlank
	private String name;
	
}
