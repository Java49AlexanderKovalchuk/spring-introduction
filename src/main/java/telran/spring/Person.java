package telran.spring;

import jakarta.validation.constraints.*;

public record Person(long id,
					@Pattern(regexp = "[A-Z][a-z]{2,}") String name, 
					@NotEmpty String city, 
					@Email @Pattern (regexp = "^[a-zA-Z0-9_+&*-]+(?:\\." +
		                    "[a-zA-Z0-9_+&*-]+)*@" +
		                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$") String email,
					@Pattern(regexp = "^((\\+|00)?972\\-?|0)"
							+ "(([23489]|[57]\\d)\\-?\\d{7})$") String phone ) {

}
