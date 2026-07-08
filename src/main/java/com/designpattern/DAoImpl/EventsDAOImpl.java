package com.designpattern.DAoImpl;

import com.designpattern.dao.EventsDAO;
import com.designpattern.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventsDAOImpl implements EventsDAO {

    private Connection conn;

    public EventsDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT id, title, description, image_path FROM events WHERE isActive=1";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                // Prefix folder name so JSP can load correctly
                e.setImagePath("events/" + rs.getString("image_path"));
                events.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return events;
    }

    @Override
    public Event getEventById(int id) {
        Event e = null;
        String sql = "SELECT id, title, description, image_path FROM events WHERE id=? AND isActive=1";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    e = new Event();
                    e.setId(rs.getInt("id"));
                    e.setTitle(rs.getString("title"));
                    e.setDescription(rs.getString("description"));
                    e.setImagePath("events/" + rs.getString("image_path"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }

    @Override
    public boolean addEvent(Event event) {
        String sql = "INSERT INTO events (title, description, image_path, isActive) VALUES (?, ?, ?, 1)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getImagePath());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET title=?, description=?, image_path=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setString(3, event.getImagePath());
            ps.setInt(4, event.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
