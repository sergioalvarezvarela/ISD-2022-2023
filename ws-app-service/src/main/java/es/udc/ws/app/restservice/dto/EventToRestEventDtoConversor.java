package es.udc.ws.app.restservice.dto;
import Event.Event;

import java.util.ArrayList;
import java.util.List;

public class EventToRestEventDtoConversor {

    public static RestEventDto toRestEventDto(Event event) {
        return new RestEventDto(event.getEventId(), event.getName(),event.getCelebrationDate(),event.getRuntime(), event.getDescription(),
                event.getEventState(),event.getAttendance(), event.getAttendance() + event.getNot_Attendance());
    }

    public static Event toEvent(RestEventDto event) {
        return new Event(event.getEventId(), event.getEventName(),event.getEventDescription(), event.getCelebrationDate(),null,event.getRuntime(),
                event.getEventState(),event.getAttendance(),event.getTotalAttendance() - event.getAttendance());
    }

    public static List<RestEventDto> toRestEventDtos(List<Event> events) {
        List<RestEventDto> eventDtos = new ArrayList<>(events.size());
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            eventDtos.add(toRestEventDto(event));
        }
        return eventDtos;
    }

}

