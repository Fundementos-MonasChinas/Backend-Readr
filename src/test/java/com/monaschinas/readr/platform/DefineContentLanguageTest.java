package com.monaschinas.readr.platform;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import com.monaschinas.readr.platform.profiling.domain.model.Role;
import com.monaschinas.readr.platform.profiling.domain.persistence.ProfileRepository;
import com.monaschinas.readr.platform.profiling.domain.service.ProfileService;
import com.monaschinas.readr.platform.profiling.domain.service.RoleService;
import com.monaschinas.readr.platform.publishing.domain.model.Book;
import com.monaschinas.readr.platform.publishing.domain.model.BookLanguage;
import com.monaschinas.readr.platform.publishing.domain.model.Language;
import com.monaschinas.readr.platform.publishing.domain.persistence.BookLanguageRepository;
import com.monaschinas.readr.platform.publishing.domain.persistence.BookRepository;
import com.monaschinas.readr.platform.publishing.domain.persistence.LanguageRepository;
import com.monaschinas.readr.platform.publishing.domain.service.BookLanguageService;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.domain.service.LanguageService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class DefineContentLanguageTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private BookLanguageRepository bookLanguageRepository;

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

    private Profile testProfile;
    private Book testBook;
    private Language testLanguage1;
    private Language testLanguage2;

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

    @Given("Existe un libro con ID {int}")
    public void existeUnLibroConID(Integer id) {
        testBook = new Book();
        testBook.setId(id.longValue());

        when(bookRepository.findById(id.longValue())).thenReturn(Optional.of(testBook));
    }

    @When("Defino los idiomas disponibles para el libro con ID {int} {string} {string}")
    public void definoLosIdiomasDisponiblesParaElLibroID(Integer id, String lang1, String lang2) {
        // Suponemos que los idiomas ya existen en la base de datos
        testLanguage1 = new Language();
        testLanguage1.setName(lang1);
        when(languageRepository.findByName(lang1)).thenReturn(Optional.of(testLanguage1));

        testLanguage2 = new Language();
        testLanguage2.setName(lang2);
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

    @Then("Los idiomas se definen exitosamente para el libro con ID {int}")
    public void losIdiomasSeDefinenExitosamenteParaElLibroConID(Integer id) {
        List<BookLanguage> bookLanguages = bookLanguageService.getAll();

        boolean isDefinedLang1 = bookLanguages.stream().anyMatch(bl -> bl.getBook().getId().equals(id.longValue()) && bl.getLanguage().getName().equals(testLanguage1.getName()));

        boolean isDefinedLang2 = bookLanguages.stream().anyMatch(bl -> bl.getBook().getId().equals(id.longValue()) && bl.getLanguage().getName().equals(testLanguage2.getName()));

        Assertions.assertTrue(isDefinedLang1 && isDefinedLang2);
    }
}