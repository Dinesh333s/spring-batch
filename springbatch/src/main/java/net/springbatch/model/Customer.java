package net.springbatch.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_info")
public class Customer {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "CONTACT")
    private  String contactNo;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "DOB")
    private String dob;

}
