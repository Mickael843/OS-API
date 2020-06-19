package br.com.osworksapi.api.domain.model;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @ManyToOne
    private OrdemOfService ordemOfService;

    private OffsetDateTime sendDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrdemOfService getOrdemOfService() {
        return ordemOfService;
    }

    public void setOrdemOfService(OrdemOfService ordemOfService) {
        this.ordemOfService = ordemOfService;
    }

    public OffsetDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(OffsetDateTime sendDate) {
        this.sendDate = sendDate;
    }
}
