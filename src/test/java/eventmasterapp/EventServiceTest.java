package eventmasterapp;
import eventmasterapp.model.Event;
import eventmasterapp.repository.EventRepository;
import eventmasterapp.service.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    private EventService eventService;

    @BeforeEach
    public void setUp() {
        eventService = new EventService(eventRepository);
    }

    @Test
    public void testGetAllEvents() {
        List<Event> events = Arrays.asList(
                new Event(1L, "Event 1", "Description 1"),
                new Event(2L, "Event 2", "Description 2")
        );

        Mockito.when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        Assertions.assertEquals(events.size(), result.size());
        Assertions.assertEquals(events.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(events.get(1).getDescription(), result.get(1).getDescription());
    }

    @Test
    public void testGetEventById() {
        Long eventId = 1L;
        Event event = new Event(eventId, "Event 1", "Description 1");

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Event result = eventService.getEventById(eventId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(eventId, result.getId());
        Assertions.assertEquals(event.getName(), result.getName());
        Assertions.assertEquals(event.getDescription(), result.getDescription());
    }

    @Test
    public void testCreateEvent() {
        Event event = new Event(1L, "New Event", "New Description");
        Event savedEvent = new Event(1L, "New Event", "New Description");

        Mockito.when(eventRepository.save(event)).thenReturn(savedEvent);

        Event result = eventService.createEvent(event);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedEvent.getId(), result.getId());
        Assertions.assertEquals(savedEvent.getName(), result.getName());
        Assertions.assertEquals(savedEvent.getDescription(), result.getDescription());
    }

    @Test
    public void testUpdateEvent() {
        Long eventId = 1L;
        Event existingEvent = new Event(eventId, "Existing Event", "Existing Description");
        Event updatedEvent = new Event(eventId, "Updated Event", "Updated Description");

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        Mockito.when(eventRepository.save(existingEvent)).thenReturn(updatedEvent);

        Event result = eventService.updateEvent(eventId, updatedEvent);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(eventId, result.getId());
        Assertions.assertEquals(updatedEvent.getName(), result.getName());
        Assertions.assertEquals(updatedEvent.getDescription(), result.getDescription());
    }

    @Test
    public void testDeleteEvent() {
        Long eventId = 1L;

        eventService.deleteEvent(eventId);

        Mockito.verify(eventRepository, Mockito.times(1)).deleteById(eventId);
    }
}
