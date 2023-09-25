package com.monaschinas.readr.platform;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import com.monaschinas.readr.platform.profiling.domain.model.Role;
import com.monaschinas.readr.platform.profiling.domain.persistence.ProfileRepository;
import com.monaschinas.readr.platform.profiling.domain.service.ProfileService;
import com.monaschinas.readr.platform.profiling.domain.service.RoleService;
import com.monaschinas.readr.platform.publishing.domain.model.*;
import com.monaschinas.readr.platform.publishing.domain.persistence.*;
import com.monaschinas.readr.platform.publishing.domain.service.BookLanguageService;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.domain.service.LanguageService;
import io.cucumber.java.en.And;
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
public class RegisterbeforePublishingBookTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private BookLanguageRepository bookLanguageRepository;

    @Mock
    private BookStatusRepository bookStatusRepository;

    @InjectMocks
    private LanguageService languageService;

    @InjectMocks
    private RoleService roleService;

    @InjectMocks
    private BookService bookService;

    @InjectMocks
    private ProfileService profileService;

    @InjectMocks
    private BookLanguageService bookLanguageService;  // Suponemos que hay un servicio para BookLanguage

    private Book testBook;
    private Profile testProfile;
    private Language testLanguage1;
    private Language testLanguage2;
    private BookStatus testBookStatus;

    @Given("Soy un autor con el rol {string}")
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

    @When("Publico un libro con los siguientes detalles: {string} {string} {string}")
    public void publicoUnLibroConLosSiguientesDetalles(String title, String synopsis, String publishedAt) throws ParseException {
        testBook = new Book();  // Aquí es donde se debería haber definido testBook
        testBook.setTitle(title);
        testBook.setSynopsis(synopsis);
        testBook.setPublishedAt(new SimpleDateFormat("yyyy-MM-dd").parse(publishedAt));
        testBook.setSaga(null);  // El libro es autoconclusivo, por lo que no pertenece a ninguna saga

        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        bookService.create(testBook);
    }

    @And("Defino los idiomas disponibles para el libro con los siguientes lenguajes: {string} {string} {string} {string}")
    public void definoLosIdiomasDisponiblesParaElLibro(String lang1, String abbr1, String lang2, String abbr2) {
        // Suponemos que los idiomas ya existen en la base de datos
        testLanguage1 = new Language();
        testLanguage1.setName(lang1);
        testLanguage1.setAbbreviation(abbr1);
        when(languageRepository.findByName(lang1)).thenReturn(Optional.of(testLanguage1));

        testLanguage2 = new Language();
        testLanguage2.setName(lang2);
        testLanguage2.setAbbreviation(abbr2);
        when(languageRepository.findByName(lang2)).thenReturn(Optional.of(testLanguage2));

        // Creamos las relaciones entre el libro y los idiomas
        BookLanguage bookLanguage1 = new BookLanguage();
        bookLanguage1.setBook(testBook);
        bookLanguage1.setLanguage(testLanguage1);

        BookLanguage bookLanguage2 = new BookLanguage();
        bookLanguage2.setBook(testBook);
        bookLanguage2.setLanguage(testLanguage2);

        when(bookLanguageRepository.save(any(BookLanguage.class))).thenReturn(bookLanguage1, bookLanguage2);

        bookLanguageService.create(bookLanguage1);
        bookLanguageService.create(bookLanguage2);
    }

    @And("Defino el status {string}")
    public void definoElStatus(String statusName) {
        // Suponemos que el estado ya existe en la base de datos
        testBookStatus = new BookStatus();
        testBookStatus.setName(statusName);
        when(bookStatusRepository.findByName(statusName)).thenReturn(Optional.of(testBookStatus));

        // Asignamos el estado al libro
        testBook.setBookStatus(testBookStatus);

        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        bookService.update(testBook.getId(), testBook);
    }

    @Then("El libro se publica exitosamente")
    public void elLibroSePublicaExitosamente() {
        List<Book> books = bookService.getAll();
        List<BookLanguage> bookLanguages = bookLanguageService.getAll();

        boolean isPublishedSuccessfully = books.stream().anyMatch(book ->
                book.getBookStatus().getName().equals(testBookStatus.getName()) &&
                        bookLanguages.stream().anyMatch(bl ->
                                bl.getBook().getId().equals(book.getId()) &&
                                        (bl.getLanguage().getAbbreviation().equals(testLanguage1.getAbbreviation()) ||
                                                bl.getLanguage().getAbbreviation().equals(testLanguage2.getAbbreviation()))
                        )
        );

        Assertions.assertTrue(isPublishedSuccessfully);
    }
}