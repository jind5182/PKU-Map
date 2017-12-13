package com.example.macpro.pku_map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peter on 2017/12/13.
 */
public class EventTest {
    private Event mEvent;
    @Before
    public void setUp() throws Exception {
        mEvent = new Event();
        mEvent.setEventID(0);
        mEvent.setBeginTime("2017-12-13 20:40:00");
        mEvent.setEndTime("2017-12-13 20:50:00");
        mEvent.setUsername("lzixuan");
        mEvent.setPublisherID(1);
        mEvent.setTitle("test");
        mEvent.setDescription("2333");
        mEvent.setType("实时");
        mEvent.setLocation(1.0, 1.0);
        mEvent.setIshelped(0);
        mEvent.setHelper(0);
        mEvent.setOutdate(0);
    }

    @Test
    public void getEventId() throws Exception {
        assertEquals(0, mEvent.getEventId());
    }

    @Test
    public void getPublisherID() throws Exception {
        assertEquals(1, mEvent.getPublisherID());
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals("test", mEvent.getTitle());
    }

    @Test
    public void getLocationID() throws Exception {
        assertEquals(-1, mEvent.getLocationID());
    }

    @Test
    public void getLocationX() throws Exception {
        assertEquals(1.0, mEvent.getLocationX(), 0);
    }

    @Test
    public void getLocationY() throws Exception {
        assertEquals(1.0, mEvent.getLocationY(), 0);
    }

    @Test
    public void getType() throws Exception {
        assertEquals(0, mEvent.getType());
    }

    @Test
    public void getBeginTime() throws Exception {
        assertEquals("2017-12-13 20:40:00", mEvent.getBeginTime());
    }

    @Test
    public void getEndTime() throws Exception {
        assertEquals("2017-12-13 20:50:00", mEvent.getEndTime());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("2333", mEvent.getDescription());
    }

    @Test
    public void getOutdate() throws Exception {
        assertEquals(0, mEvent.getOutdate());
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("lzixuan", mEvent.getUsername());
    }

    @Test
    public void getIshelped() throws Exception {
        assertEquals(0, mEvent.getIshelped());
    }

    @Test
    public void getHelper() throws Exception {
        assertEquals(0, mEvent.getHelper());
    }

}