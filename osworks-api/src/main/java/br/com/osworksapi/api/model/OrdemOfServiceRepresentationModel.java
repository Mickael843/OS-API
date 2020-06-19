package br.com.osworksapi.api.model;

import br.com.osworksapi.api.domain.model.OrdemOfServiceStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrdemOfServiceRepresentationModel {

    private Long id;

    private ClientResumeRepresentationModel client;

    private String description;

    private BigDecimal price;

    private OrdemOfServiceStatus status;

    private OffsetDateTime openDate;

    private OffsetDateTime finishDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientResumeRepresentationModel getClient() {
        return client;
    }

    public void setClient(ClientResumeRepresentationModel client) {
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
}
