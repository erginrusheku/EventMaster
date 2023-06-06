package eventmasterapp;

import eventmasterapp.controller.EventController;
import eventmasterapp.model.Event;
import eventmasterapp.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void testGetAllEvents() throws Exception {
        List<Event> events = Arrays.asList(new Event(1L, "Event 1", "Description 1"), new Event(2L, "Event 2", "Description 2"));
        Mockito.when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Event 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Event 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Description 2"));
    }

    @Test
    public void testGetEventById() throws Exception {
        Long eventId = 1L;
        Event event = new Event(eventId, "Event 1", "Description 1");
        Mockito.when(eventService.getEventById(eventId)).thenReturn(event);

        mockMvc.perform(MockMvcRequestBuilders.get("/events/{eventId}", eventId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Event 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description 1"));
    }

    @Test
    public void testCreateEvent() throws Exception {
        Event event = new Event(1L, "Event 1", "Description 1");
        Mockito.when(eventService.createEvent(any(Event.class))).thenReturn(event);

        String eventJson = "{\"id\":1,\"name\":\"Event 1\",\"description\":\"Description 1\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/events").contentType(MediaType.APPLICATION_JSON).content(eventJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Event 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description 1"));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Long eventId = 1L;
        Event updatedEvent = new Event(eventId, "Updated Event", "Updated Description");
        Mockito.when(eventService.updateEvent(eventId, updatedEvent)).thenReturn(updatedEvent);

        String updatedEventJson = "{\"id\":1,\"name\":\"Updated Event\",\"description\":\"Updated Description\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/events/{eventId}", eventId).contentType(MediaType.APPLICATION_JSON).content(updatedEventJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Event"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        Long eventId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/events/{eventId}", eventId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(eventService, Mockito.times(1)).deleteEvent(eventId);
    }
}