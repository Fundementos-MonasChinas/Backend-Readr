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
public class PublishWihoutContinuationTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private RoleService roleService;

    @InjectMocks
    private BookService bookService;

    @InjectMocks
    private ProfileService profileService;

    private Profile testProfile;

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

    @Given("Quiero publicar un libro autoconclusivo")
    public void quieroPublicarUnLibroAutoconclusivo() {
        // Simulación, no implementación necesaria.
    }

    @When("cuando publico un libro con los siguente datos: {string} {string} {string}")
    public void cuandoPublicoUnLibroConLosSiguenteDatos(String title, String synopsis, String publishedAt) throws ParseException {
        Book book = new Book();
        book.setTitle(title);
        book.setSynopsis(synopsis);
        book.setPublishedAt(new SimpleDateFormat("yyyy-MM-dd").parse(publishedAt));
        book.setSaga(null);  // El libro es autoconclusivo, por lo que no pertenece a ninguna saga

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.create(book);
    }

    @Then("Entonces el libro se publico correctamente en la seccion saga")
    public void entoncesElLibroSePublicoCorrectamenteEnLaSeccionSaga() {
        List<Book> books = bookService.getAll();

        boolean isPublishedAutoconclusive = books.stream().anyMatch(book -> book.getSaga() == null);

        Assertions.assertTrue(isPublishedAutoconclusive);
    }
}
