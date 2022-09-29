package se.ruffieux.userService.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int emailQuotaInGB;
    private int totalCostInCents;
}
