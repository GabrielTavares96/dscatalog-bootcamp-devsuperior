package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.services.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO{

    private String password;

    UserInsertDTO(){
        super();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
