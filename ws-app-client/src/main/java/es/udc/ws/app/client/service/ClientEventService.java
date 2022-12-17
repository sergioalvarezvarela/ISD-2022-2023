package es.udc.ws.app.client.service;

import es.udc.ws.app.client.service.dto.ClientEventDto;
import es.udc.ws.app.client.service.dto.ClientResponseDto;
import es.udc.ws.app.client.service.exceptions.ClientAlreadyCanceledException;
import es.udc.ws.app.client.service.exceptions.ClientAlreadyResponseException;
import es.udc.ws.app.client.service.exceptions.ClientOutOfTimeException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ClientEventService {

    Long addEvent(ClientEventDto event) throws InputValidationException;

    ClientEventDto findEvent(Long eventId) throws InstanceNotFoundException;
    void cancelEvent(Long eventId) throws InstanceNotFoundException, ClientOutOfTimeException, ClientAlreadyCanceledException, InputValidationException ;
    List<ClientEventDto> findEvents(LocalDate endDate,String keywords) throws InputValidationException ;

    Long addResponse (ClientResponseDto response) throws InputValidationException,InstanceNotFoundException,ClientOutOfTimeException, ClientAlreadyCanceledException, ClientAlreadyResponseException;
    List<ClientResponseDto> findResponses(String userEmail,boolean asistence) throws InputValidationException ;
}
