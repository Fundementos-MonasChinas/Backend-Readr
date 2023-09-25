package com.monaschinas.readr.platform;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import com.monaschinas.readr.platform.profiling.domain.model.Role;
import com.monaschinas.readr.platform.profiling.domain.persistence.ProfileRepository;
import com.monaschinas.readr.platform.profiling.domain.service.ProfileService;
import com.monaschinas.readr.platform.profiling.domain.service.RoleService;
import com.monaschinas.readr.platform.publishing.domain.model.Book;
import com.monaschinas.readr.platform.publishing.domain.model.Comment;
import com.monaschinas.readr.platform.publishing.domain.persistence.BookRepository;
import com.monaschinas.readr.platform.publishing.domain.persistence.CommentRepository;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.domain.service.CommentService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class CommentLiteraryContentTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @InjectMocks
    private RoleService roleService;

    @InjectMocks
    private BookService bookService;

    @InjectMocks
    private ProfileService profileService;

    private Profile testProfile;
    private Book testBook;

    @Given("Soy un lector con el rol {string}")
    public void soyUnLectorConElRol(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        role = roleService.create(role);

        testProfile = new Profile();
        testProfile.setFirstName("John");
        testProfile.setLastName("Doe");
        testProfile.setRole(role);

        testProfile = profileService.create(testProfile);
    }

    @Given("Existe un libro con ID {int}")
    public void existeUnLibroConID(Integer id) {
        testBook = new Book();
        testBook.setId(id.longValue());

        when(bookRepository.findById(id.longValue())).thenReturn(Optional.of(testBook));
    }

    @When("Leo el libro con ID {int}")
    public void leoElLibroConID(Integer id) {
        // Simulación, no implementación necesaria.
    }

    @When("Comento el libro con ID {int} con el siguiente contenido: {string}")
    public void comentoElLibroConElSiguienteContenido(Integer id, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(new Date());
        comment.setProfileId(testProfile.getId());
        comment.setBook(testBook);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        commentService.create(comment);
    }

    @Then("El comentario se agrega exitosamente al libro con ID {int}")
    public void elComentarioSeAgregaExitosamenteAlLibroConID(Integer id) {
        List<Comment> comments = commentService.getAll();
        boolean isCommented = comments.stream().anyMatch(comment -> comment.getBook().getId().equals(id.longValue()) && comment.getProfileId().equals(testProfile.getId()));

        Assertions.assertTrue(isCommented);
    }
}
