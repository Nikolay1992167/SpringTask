package by.aston.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
//@EntityListeners(HouseListener.class)
@Table(name = "houses")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "login", nullable = false)
    private String city;

    @Column(name = "password", nullable = false)
    private String street;

    @Column(name = "phone")
    private Integer number;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
}
