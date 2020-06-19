package br.com.osworksapi.api.controller;

import br.com.osworksapi.api.domain.exception.EntityNotFoundException;
import br.com.osworksapi.api.domain.model.Comment;
import br.com.osworksapi.api.domain.model.OrdemOfService;
import br.com.osworksapi.api.domain.repository.OSRepository;
import br.com.osworksapi.api.domain.service.OSService;
import br.com.osworksapi.api.model.CommentInput;
import br.com.osworksapi.api.model.CommentRepresentationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/ordem-of-service/{ordemOfServiceId}")
public class CommentController {

    @Autowired
    private OSService osService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OSRepository osRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/", produces = "application/json")
    public List<CommentRepresentationModel> getAllComment(@PathVariable Long ordemOfServiceId) {

        OrdemOfService ordemOfService = osRepository.findById(ordemOfServiceId)
                .orElseThrow(() -> new EntityNotFoundException("Ordem of service not found in DB"));

        return toCollectionModel(ordemOfService.getComments());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/", produces = "application/json")
    public CommentRepresentationModel addComment(@Valid @PathVariable Long ordemOfServiceId,
                                                 @RequestBody CommentInput commentInput) {

        Comment comment = osService.addComment(ordemOfServiceId, commentInput.getDescription());
        return toModel(comment);
    }

    private CommentRepresentationModel toModel(Comment comment) {
        return modelMapper.map(comment, CommentRepresentationModel.class);
    }

    private List<CommentRepresentationModel> toCollectionModel(List<Comment> comments) {
        return comments.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
