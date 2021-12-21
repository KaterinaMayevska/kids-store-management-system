package edu.kpi.iasa.mmsa.ka9616.kidshop.model;

import lombok.ToString;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

public enum Roles implements GrantedAuthority {
   CUSTOMER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

}
