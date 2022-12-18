# Memoria justificativa del proyecto
---------------------------------------------------------------------

## Iteración 1
---------------------------------------------------------------------

### Resumen de contribución de cada miembro del grupo (consistente con commits en repositorio GIT)
- miembro1: Sergio Álvarez Varela
    - Creación de tablas
    - Creación de entidades: Event y Response
    - Factorías:
        * EventServiceFactory
    - Funcionalidades:
        * Funcionalidad 1
        * Funcionalidad 3
    - Test:
        * Auxiliar Remove response
        * Auxiliar Update event
        * testAddEventandFindEvent
        * testAddInvalidEvent
        * RemoveEventTest
        * RemoveEventTestException
        * TestUpdateEvent
        * TestUpdateEventException
        * testFindByDateorKeyword
        * testFindByDateorKeywordException
        * testCancel
        * testCancelException
    - Dao:
        - Event:
            * findEventById
            * update 
            * create
        - Response:
            * existsResponse
            * findResponsebyId    
    
- miembro2: Juan Piñeiro Torres 
    - Factorías:
        * SqlEventDaoFactory
        * SqlResponseDaoFactory
    - Funcionalidades:
        * Funcionalidad 2
        * Funcionalidad 6
    - Interfaces:
        * EventService
    - Test:
        * Auxiliar Remove event
        * testFindByDateorKeyword
        * testFindByDateorKeywordException
        * testfindByEmail
        * TestFindByEmailException
    - Dao:
        - Event:
            * remove
            * findEventByDateOrAndKeyWord 
        - Response:
            * findByEmployee
            * remove 
- miembro3: Graciela Méndez Olmos
    - Funcionalidades:
            * Funcionalidad 4
            * Funcionalidad 5
        - Interfaces:
            * SqlEventDao
            * SqlResponseDao
        - Test:
            * removeResponse
            * testAddResponseandFindResponse
            * testAddResponseandFindResponseException
            * RemoveResponseTestException
        - Dao:
            - Event:
                * CancelEvent
                * isAlreadyCancelled
            - Response:
                * create

## Iteración 2
---------------------------------------------------------------------

### Resumen de contribución de cada miembro del grupo (consistente con commits en repositorio GIT)
- miembro1: Sergio Álvarez Varela
    - DTOS en Cliente y Servicio
    - Conversores en Cliente y Servicio
    - Excepciones en Cliente y Servicio
    - Funcionalidad 1 y 3 en Cliente y Servicio
    - Factoría en Cliente
- miembro2: Juan Piñeiro Torres 
    - Funcionalidad 2 y 6 en Cliente y Servicio
- miembro3: Graciela Méndez Olmos
    - Funcionalidad 4 y 5 en Cliente y Servicio