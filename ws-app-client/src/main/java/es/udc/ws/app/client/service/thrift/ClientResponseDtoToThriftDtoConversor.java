package es.udc.ws.app.client.service.thrift;

import es.udc.ws.app.client.service.dto.ClientResponseDto;
import es.udc.ws.app.thrift.ThriftResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ClientResponseDtoToThriftDtoConversor {

    public static ThriftResponseDto toThriftResponseDto(
            ClientResponseDto clientResponseDto) {

        Long responseId = clientResponseDto.getResponseId();

        return new ThriftResponseDto(
                responseId == null ? -1 : responseId.longValue(),
                clientResponseDto.getEventId(),
                clientResponseDto.getWorkerEmail(),
                clientResponseDto.isAttendance());

    }

    public static List<ClientResponseDto> toClientResponseDtos(List<ThriftResponseDto> responses) {

        List<ClientResponseDto> clientResponseDtos = new ArrayList<>(responses.size());

        for (ThriftResponseDto response : responses) {
            clientResponseDtos.add(toClientResponseDto(response));
        }
        return clientResponseDtos;

    }

    private static ClientResponseDto toClientResponseDto(ThriftResponseDto response) {

        return new ClientResponseDto(
                response.getResponseId(),
                response.getEventId(),
                response.getWorkerEmail(),
                response.isAttendance());
    }
}
