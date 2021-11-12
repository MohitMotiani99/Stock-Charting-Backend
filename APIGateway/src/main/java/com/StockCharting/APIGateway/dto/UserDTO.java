package com.StockCharting.APIGateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String userId;
    private String username;
    private String password;
    private String userType;
    private String email;
    private String mobile;
    private Boolean confirmed;

}
