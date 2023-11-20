package telran.spring;

import java.io.Serializable;

import jakarta.validation.constraints.*;

public record Person (@NotNull long id,
					@Pattern(regexp = "[A-Z][a-z]{2,}", 
					message="Wrong name structure") String name, 
					@NotEmpty String city, 
					@Email @NotEmpty String email,
					@Pattern(message="not Israel mobile phone", 
					regexp = "(\\+972-?|0)5\\d-?\\d{7}") @NotEmpty String phone ) 
	implements Serializable {
	
}
 