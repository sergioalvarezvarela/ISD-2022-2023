package es.udc.ws.app.restservice.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Response.Response;
import Event.Event;
import EventService.EventServiceFactory;
import EventService.Exception.AlreadyResponseException;
import EventService.Exception.OutOfTimeException;
import es.udc.ws.app.restservice.dto.EventToRestEventDtoConversor;
import es.udc.ws.app.restservice.dto.ResponseToRestResponseDtoConversor;
import es.udc.ws.app.restservice.dto.RestEventDto;
import es.udc.ws.app.restservice.dto.RestResponseDto;
import es.udc.ws.app.restservice.json.JsonToRestEventDtoConversor;
import es.udc.ws.app.restservice.json.JsonToRestResponseDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.RestHttpServletTemplate;
import es.udc.ws.util.servlet.ServletUtils;


@SuppressWarnings("serial")
public class ResponseServlet extends RestHttpServletTemplate{
 /*   @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse res) throws
        InputValidationException, InstanceNotFoundException, IOException {
            ServletUtils.checkEmptyPath(req);
            
            RestResponseDto responseDto = JsonToRestResponseDtoConversor.toRestResponseDto(req.getInputStream());
            Response response = ResponseToRestResponseDtoConversor.toResponse(responseDto);

            response = EventServiceFactory.getService().addResponse(response);
            
            responseDto = ResponseToRestResponseDtoConversor.toRestResponseDto(response);
            String responseURL = ServletUtils.normalizePath(req.getRequestURL().toString());
            Map<String,String> headers = new HashMap<>(1);
            headers.put("Location",responseURL);
            ServletUtils.writeServiceResponse(res, HttpServletResponse.SC_CREATED, JsonToRestEventDtoConversor.toObjectNode(responseDto), headers);
            
        }*/
}
