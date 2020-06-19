package br.com.osworksapi.api.domain.service.serviceImpl;

import br.com.osworksapi.api.domain.exception.BusinessException;
import br.com.osworksapi.api.domain.exception.EntityNotFoundException;
import br.com.osworksapi.api.domain.model.Comment;
import br.com.osworksapi.api.domain.repository.CommentRepository;
import br.com.osworksapi.api.domain.service.OSService;
import br.com.osworksapi.api.domain.model.Client;
import br.com.osworksapi.api.domain.model.OrdemOfService;
import br.com.osworksapi.api.domain.model.OrdemOfServiceStatus;
import br.com.osworksapi.api.domain.repository.ClientRepository;
import br.com.osworksapi.api.domain.repository.OSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class OSServiceImpl implements OSService {

    @Autowired
    private OSRepository osRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public OrdemOfService save(OrdemOfService os) {

        Client client = clientRepository.findById(os.getClient().getId())
                .orElseThrow(() -> new BusinessException("Client not found in DB"));

        os.setClient(client);
        os.setOpenDate(OffsetDateTime.now());
        os.setStatus(OrdemOfServiceStatus.OPEN);

        return osRepository.save(os);
    }

    @Override
    public void deleteById(Long id) {
        osRepository.deleteById(id);
    }

    @Override
    public Comment addComment(Long ordemOfServiceId, String description) {
        OrdemOfService ordemOfService = getOrdemOfService(ordemOfServiceId);

        Comment comment = new Comment();
        comment.setSendDate(OffsetDateTime.now());
        comment.setDescription(description);
        comment.setOrdemOfService(ordemOfService);

        return commentRepository.save(comment);
    }

    @Override
    public void finalizeOrdemOfService(Long ordemOfServiceId) {
        OrdemOfService ordemOfService = getOrdemOfService(ordemOfServiceId);
        ordemOfService.finish();
        osRepository.save(ordemOfService);
    }

    @Override
    public void cancelOrdemOfService(Long ordemOfServiceId) {
        OrdemOfService ordemOfService = getOrdemOfService(ordemOfServiceId);
        ordemOfService.cancel();
        osRepository.save(ordemOfService);
    }

    private OrdemOfService getOrdemOfService(Long ordemOfServiceId) {
        return osRepository.findById(ordemOfServiceId)
                .orElseThrow(() -> new EntityNotFoundException("Ordem of service not found in DB"));
    }
}
