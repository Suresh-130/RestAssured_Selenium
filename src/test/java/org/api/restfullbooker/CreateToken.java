package org.api.restfullbooker;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateToken {
	private String username;
    private String password;      
}
