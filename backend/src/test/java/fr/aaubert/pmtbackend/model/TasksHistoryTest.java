package fr.aaubert.pmtbackend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TasksHistoryTest {

    @Test
    void testGetTask() {
        Task task = new Task();
        TasksHistory tasksHistory = new TasksHistory();
        tasksHistory.setTask(task);
        assertEquals(task, tasksHistory.getTask());
    }

    @Test
    void testGetModifiedBy() {
        TasksHistory tasksHistory = new TasksHistory();
        tasksHistory.setModifiedBy(1L);
        assertEquals(1L, tasksHistory.getModifiedBy());
    }

    @Test
    void testGetModificationType() {
        TasksHistory tasksHistory = new TasksHistory();
        tasksHistory.setModificationType("modificationType");
        assertEquals("modificationType", tasksHistory.getModificationType());
    }

    @Test
    void testGetOldValue() {
        TasksHistory tasksHistory = new TasksHistory();
        tasksHistory.setOldValue("oldValue");
        assertEquals("oldValue", tasksHistory.getOldValue());
    }

    @Test
    void testGetNewValue() {
        TasksHistory tasksHistory = new TasksHistory();
        tasksHistory.setNewValue("newValue");
        assertEquals("newValue", tasksHistory.getNewValue());
    }

    @Test
    void testGetDateModified() {
        TasksHistory tasksHistory = new TasksHistory();
        tasksHistory.setDateModified(null);
        assertEquals(null, tasksHistory.getDateModified());
    }

    @Test
    void testGetId() {
        TasksHistory tasksHistory = new TasksHistory();
        tasksHistory.setId(1L);
        assertEquals(1L, tasksHistory.getId());
    }

    @Test
    void testSetters() {
        Task task = new Task();
        TasksHistory tasksHistory = new TasksHistory();
        tasksHistory.setTask(task);
        tasksHistory.setModifiedBy(1L);
        tasksHistory.setModificationType("modificationType");
        tasksHistory.setOldValue("oldValue");
        tasksHistory.setNewValue("newValue");
        tasksHistory.setDateModified(null);
        tasksHistory.setId(1L);

        assertEquals(task, tasksHistory.getTask());
        assertEquals(1L, tasksHistory.getModifiedBy());
        assertEquals("modificationType", tasksHistory.getModificationType());
        assertEquals("oldValue", tasksHistory.getOldValue());
        assertEquals("newValue", tasksHistory.getNewValue());
        assertEquals(null, tasksHistory.getDateModified());
        assertEquals(1L, tasksHistory.getId());
    }

}