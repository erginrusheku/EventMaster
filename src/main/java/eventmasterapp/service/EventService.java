package eventmasterapp.service;

import eventmasterapp.model.Event;
import eventmasterapp.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(@PathVariable Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("\"Event is not found with id:\"+id"));
    }
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(@PathVariable Long eventId, @RequestBody Event updateEvent) {
        return eventRepository.findById(eventId).map(event -> {
            event.setName(updateEvent.getName());
            event.setDescription(updateEvent.getDescription());
            return eventRepository.save(event);
        }).orElseThrow(() -> new IllegalArgumentException("Event is not found with id " + eventId));
    }
    public void deleteEvent(@PathVariable Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
