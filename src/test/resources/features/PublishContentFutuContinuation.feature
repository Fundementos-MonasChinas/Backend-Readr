Feature: Publicación de Contenido con Sagas o Capítulos

  Scenario: Un autor publica un libro con sagas o capítulos
    Given Que soy un autor con el rol "autor"
    And Quiero publicar una saga de libro con los datos: "Mi Saga de libros" "Mi Synopsis"
    When cuando publico un libro con los siguente datos: "Mi Libro con Saga" "Resumen del libro" "2023-09-24"
    Then Entonces el libro se publico correctamente en la seccion saga
