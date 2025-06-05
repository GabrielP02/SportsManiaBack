package com.example.demo.dto.personDTO;

import lombok.Data;

@Data
public class PersonUpdateAuthDTO {
    private String username;
    private String password;
    private String email;
}
