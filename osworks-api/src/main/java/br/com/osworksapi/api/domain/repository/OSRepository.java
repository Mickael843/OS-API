package br.com.osworksapi.api.domain.repository;

import br.com.osworksapi.api.domain.model.OrdemOfService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OSRepository extends JpaRepository<OrdemOfService, Long> {
}
