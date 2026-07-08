package com.designpattern.dao;

import com.designpattern.model.Event;
import java.util.List;

public interface EventsDAO {
    List<Event> getAllEvents();
    Event getEventById(int id);
    boolean addEvent(Event event);
    boolean updateEvent(Event event);
    boolean deleteEvent(int id);
}
