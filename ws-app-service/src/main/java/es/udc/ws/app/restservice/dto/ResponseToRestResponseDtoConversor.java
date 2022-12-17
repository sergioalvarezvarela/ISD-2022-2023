package es.udc.ws.app.restservice.dto;

import Response.Response;

public class ResponseToRestResponseDtoConversor {

    public static RestResponseDto toRestResponseDto(Response response) {
        return new RestResponseDto(response.getResponseId(), response.getEventId(), response.getEmail(), response.getAsistencia());
    }

    public static Response toResponse(RestResponseDto response) {
        return new Response(response.getResponseId(),response.getEventId(), response.getWorkerEmail(), null, response.getAttendance());
    }
}
