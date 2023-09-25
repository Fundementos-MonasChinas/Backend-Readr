Feature: Publicación de Contenido Único

  Scenario: Un autor publica un libro sin sagas ni capítulos
    Given Que soy un autor con el rol "autor"
    And Quiero publicar un libro autoconclusivo
    When cuando publico un libro con los siguente datos: "Mi Libro con Saga" "Resumen del libro" "2023-09-24"
    Then Entonces el libro se publico correctamente en la seccion saga
