Feature: Definir idiomas disponibles para un libro

  Scenario: Un autor define los idiomas disponibles para un libro
    Given Soy un autor con el rol "autor"
    And Existe un libro con ID 1
    When Defino los idiomas disponibles para el libro con ID 1 "Español" "Inglés"
    Then Los idiomas se definen exitosamente para el libro con ID 1
