package br.com.osworksapi.api.controller;

import br.com.osworksapi.api.domain.model.OrdemOfService;
import br.com.osworksapi.api.domain.repository.OSRepository;
import br.com.osworksapi.api.domain.service.OSService;
import br.com.osworksapi.api.model.OrdemOfServiceInputModel;
import br.com.osworksapi.api.model.OrdemOfServiceRepresentationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/ordem-of-service")
public class OSController {

    @Autowired
    private OSService osService;

    @Autowired
    private OSRepository osRepository;

    @Autowired
    private ModelMapper modelMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/", produces = "application/json")
    public OrdemOfServiceRepresentationModel saveOS(@Valid @RequestBody OrdemOfServiceInputModel ordemOfServiceInputModel) {
        OrdemOfService os = toEntity(ordemOfServiceInputModel);
        return toModel(osService.save(os));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = "application/json")
    public List<OrdemOfServiceRepresentationModel> getAllOS() {
        return toCollectionModel(osRepository.findAll());
    }

    @GetMapping(value = "/{ordemOfServiceId}", produces = "application/json")
    public ResponseEntity<OrdemOfServiceRepresentationModel> getOS(@PathVariable Long ordemOfServiceId) {
        Optional<OrdemOfService> ordemOfService = osRepository.findById(ordemOfServiceId);

        if (ordemOfService.isPresent()) {
            OrdemOfServiceRepresentationModel ordemOfServiceRepresentationModel = toModel(ordemOfService.get());
            return ResponseEntity.ok(ordemOfServiceRepresentationModel);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finishOrdemOfService(@PathVariable Long ordemOfServiceId) {
        osService.finalizeOrdemOfService(ordemOfServiceId);
    }

    @PutMapping(value = "/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrdemOfService(@PathVariable Long ordemOfServiceId) {
        osService.cancelOrdemOfService(ordemOfServiceId);
    }

    private OrdemOfServiceRepresentationModel toModel(OrdemOfService ordemOfService) {
        return modelMapper.map(
                ordemOfService,
                OrdemOfServiceRepresentationModel.class
        );
    }

    private OrdemOfService toEntity(OrdemOfServiceInputModel ordemOfServiceInputModel) {
        return modelMapper.map(
                ordemOfServiceInputModel,
                OrdemOfService.class
        );
    }

    private List<OrdemOfServiceRepresentationModel> toCollectionModel(List<OrdemOfService> ordemOfServices) {
        return ordemOfServices.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
