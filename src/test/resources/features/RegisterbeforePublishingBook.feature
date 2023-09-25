Feature: Publicar un libro con todas las características

  Scenario: Un autor publica un libro con todas las características
    Given Soy un autor con el rol "autor"
    When Publico un libro con los siguientes detalles: "Mi Libro" "Una breve descripción..." "2023-09-24"
    And Defino los idiomas disponibles para el libro con los siguientes lenguajes: "Español" "ESP" "Ingles" "ING"
    And Defino el status "En Transicion"
    Then El libro se publica exitosamente
