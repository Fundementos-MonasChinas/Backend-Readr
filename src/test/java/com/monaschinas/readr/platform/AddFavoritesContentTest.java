package com.monaschinas.readr.platform;

import com.monaschinas.readr.platform.profiling.domain.model.Profile;
import com.monaschinas.readr.platform.profiling.domain.model.Role;
import com.monaschinas.readr.platform.profiling.domain.persistence.ProfileRepository;
import com.monaschinas.readr.platform.profiling.domain.service.ProfileService;
import com.monaschinas.readr.platform.profiling.domain.service.RoleService;
import com.monaschinas.readr.platform.publishing.domain.model.Book;
import com.monaschinas.readr.platform.publishing.domain.model.Favorite;
import com.monaschinas.readr.platform.publishing.domain.persistence.BookRepository;
import com.monaschinas.readr.platform.publishing.domain.persistence.FavoriteRepository;
import com.monaschinas.readr.platform.publishing.domain.service.BookService;
import com.monaschinas.readr.platform.publishing.service.FavoriteServiceImpl;
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
public class AddFavoritesContentTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    // Suponiendo que también hay un servicio para Role y Book:
    @InjectMocks
    private RoleService roleService;

    @InjectMocks
    private BookService bookService;

    @InjectMocks
    private ProfileService profileService;  // Agregamos el servicio de Profile

    private Profile testProfile;
    private Book testBook;

    @Given("El endpoint de favoritos {string} esta disponible")
    public void theEndpointIsAvailable(String endpoint) {
        // Simulación, no implementación necesaria.
    }

    @Given("Soy un usuario con el rol {string}")
    public void soyUnUsuarioConElRol(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        role = roleService.create(role);  // Suponemos que hay un método createRole en RoleService

        testProfile = new Profile();
        testProfile.setFirstName("John");
        testProfile.setLastName("Doe");
        testProfile.setRole(role);

        testProfile = profileService.create(testProfile);  // Usamos el servicio de Profile para crear el perfil
    }

    @Given("Hay un libro con el ID {int}")
    public void hayUnLibroConElID(Integer id) {
        testBook = new Book();
        testBook.setId(id.longValue());
        // Llenar otros campos si es necesario

        when(bookRepository.findById(id.longValue())).thenReturn(Optional.of(testBook));
    }

    @When("Cuando añado el libro con ID {int} a mis favoritos")
    public void cuandoAñadoElLibroAMisFavoritos(Integer id) {
        Favorite favorite = new Favorite();
        favorite.setProfileId(testProfile.getId());
        favorite.setBook(testBook);

        favoriteService.create(favorite);
    }

    @Then("El libro con ID {int} esta en mi lista de favoritos")
    public void elLibroEstaEnMiListaDeFavoritos(Integer id) {
        List<Favorite> favorites = favoriteService.getAll();
        boolean isFavorite = favorites.stream().anyMatch(fav -> fav.getBook().getId().equals(id.longValue()) && fav.getProfileId().equals(testProfile.getId()));

        Assertions.assertTrue(isFavorite);
    }
}