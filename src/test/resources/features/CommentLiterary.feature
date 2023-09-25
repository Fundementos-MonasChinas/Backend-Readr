Feature: Comentar un libro después de leerlo

  Scenario: Un lector comenta un libro después de leerlo
    Given Soy un lector con el rol "lector"
    And Existe un libro con ID 1
    When Leo el libro con ID 1
    And Comento el libro con ID 1 con el siguiente contenido: "Este libro me encantó. Muy recomendado."
    Then El comentario se agrega exitosamente al libro con ID 1
