package br.com.osworksapi.api.domain.service;

import br.com.osworksapi.api.domain.model.Comment;
import br.com.osworksapi.api.domain.model.OrdemOfService;

public interface OSService {

    OrdemOfService save(OrdemOfService ordemOfService);

    void deleteById(Long id);

    Comment addComment(Long ordemOfServiceId, String description);

    void finalizeOrdemOfService(Long ordemOfServiceId);

    void cancelOrdemOfService(Long ordemOfServiceId);
}
