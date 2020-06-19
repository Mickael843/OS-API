package br.com.osworksapi.api.domain.model;

import br.com.osworksapi.api.domain.validation.ValidationGroup;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(groups = ValidationGroup.ClientId.class)
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(name = "nome")
    private String name;

    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @Size(max = 20)
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client Cliente = (Client) o;
        return Objects.equals(id, Cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
