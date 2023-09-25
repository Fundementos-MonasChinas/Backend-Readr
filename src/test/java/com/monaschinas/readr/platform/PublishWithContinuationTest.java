package com.monaschinas.readr.platform;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import com.monaschinas.readr.platform.profiling.domain.model.Role;
import com.monaschinas.readr.platform.profiling.domain.persistence.ProfileRepository;
import com.monaschinas.readr.platform.profiling.domain.service.ProfileService;
import com.monaschinas.readr.platform.profiling.domain.service.RoleService;
import com.monaschinas.readr.platform.publishing.domain.model.*;
import com.monaschinas.readr.platform.publishing.domain.persistence.*;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.service.SagaServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PublishWithContinuationTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private SagaRepository sagaRepository;

    @Mock
    private SagaStatusRepository sagaStatusRepository;

    @InjectMocks
    private SagaServiceImpl sagaService;

    @InjectMocks
    private RoleService roleService;

    @InjectMocks
    private BookService bookService;

    @InjectMocks
    private ProfileService profileService;

    private Profile testProfile;
    private Saga testSaga;
    private SagaStatus testSagaStatus;

    @Given("Que soy un autor con el rol {string}")
    public void soyUnAutorConElRol(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        role = roleService.create(role);

        testProfile = new Profile();
        testProfile.setFirstName("John");
        testProfile.setLastName("Doe");
        testProfile.setRole(role);

        testProfile = profileService.create(testProfile);
    }

    @Given("Quiero publicar una saga de libro con los datos: {string} {string}")
    public void quieroPublicarUnaSagaDeLibroConLosDatos(String title, String synopsis) {
        testSagaStatus = new SagaStatus();
        testSagaStatus.setName("Publicado");

        when(sagaStatusRepository.findByName("Publicado")).thenReturn(Optional.of(testSagaStatus));

        testSaga = new Saga();
        testSaga.setTitle(title);
        testSaga.setSynopsis(synopsis);
        testSaga.setSagaStatus(testSagaStatus);

        when(sagaRepository.save(any(Saga.class))).thenReturn(testSaga);

        sagaService.create(testSaga);
    }

    @When("cuando publico un libro con los siguente datos: {string} {string} {string}")
    public void cuandoPublicoUnLibroConLosSiguenteDatos(String title, String synopsis, String publishedAt) throws ParseException {
        Book book = new Book();
        book.setTitle(title);
        book.setSynopsis(synopsis);
        book.setPublishedAt(new SimpleDateFormat("yyyy-MM-dd").parse(publishedAt));
        book.setSaga(testSaga);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.create(book);
    }

    @Then("Entonces el libro se publico correctamente en la seccion saga")
    public void entoncesElLibroSePublicoCorrectamenteEnLaSeccionSaga() {
        List<Book> books = bookService.getAll();

        boolean isPublishedInSaga = books.stream().anyMatch(book -> book.getSaga().getId().equals(testSaga.getId()));

        Assertions.assertTrue(isPublishedInSaga);
    }
}
