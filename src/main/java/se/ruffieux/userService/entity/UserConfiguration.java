package se.ruffieux.userService.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private int emailQuotaInGB;
}
