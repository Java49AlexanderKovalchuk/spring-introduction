package telran.spring;

import jakarta.validation.constraints.*;

public record Person(long id,
					@Pattern(regexp = "[A-Z][a-z]{2,}") String name, 
					@NotEmpty String city, 
					@Email @NotEmpty String email,
					@Pattern(message="not Israel mobile phone", 
					regexp = "(\\+972-?|0)5\\d-?\\d{7}") @NotEmpty String phone ) {

}
