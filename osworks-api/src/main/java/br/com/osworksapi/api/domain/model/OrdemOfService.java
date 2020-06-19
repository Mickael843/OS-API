package br.com.osworksapi.api.domain.model;

import br.com.osworksapi.api.domain.exception.BusinessException;
import br.com.osworksapi.api.domain.validation.ValidationGroup;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class OrdemOfService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Valid
    @NotNull
    @ManyToOne
    @ConvertGroup(from = Default.class, to = ValidationGroup.ClientId.class)
    private Client client;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrdemOfServiceStatus status;

    private OffsetDateTime openDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime finishDate;

    @OneToMany(mappedBy = "ordemOfService")
    private List<Comment> comments = new ArrayList<>();

    public void finish() {

        if (cannotBeCompleted()) {
            throw new BusinessException("Service order cannot be finalized");
        }

        setStatus(OrdemOfServiceStatus.FINISH);
        setFinishDate(OffsetDateTime.now());
    }

    public void cancel() {

        if (cannotBeCompleted()) {
            throw new BusinessException("Service order cannot be canceled");
        }

        setStatus(OrdemOfServiceStatus.CANCELED);
        setFinishDate(OffsetDateTime.now());
    }

    private boolean cannotBeCompleted() {
        return !OrdemOfServiceStatus.OPEN.equals(getStatus());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdemOfService that = (OrdemOfService) o;
        return Objects.equals(id, that.id);
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OrdemOfServiceStatus getStatus() {
        return status;
    }

    public void setStatus(OrdemOfServiceStatus status) {
        this.status = status;
    }

    public OffsetDateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(OffsetDateTime openDate) {
        this.openDate = openDate;
    }

    public OffsetDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(OffsetDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
