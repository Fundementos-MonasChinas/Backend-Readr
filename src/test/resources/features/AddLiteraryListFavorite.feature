Feature: Agregar Libro a Favoritos

  @profile-adding
  Scenario: Un lector agrega un libro a sus favoritos
    Given Soy un usuario con el rol "lector"
    And Hay un libro con el ID 1
    When Cuando añado el libro con ID 1 a mis favoritos
    Then El libro con ID 1 esta en mi lista de favoritos
