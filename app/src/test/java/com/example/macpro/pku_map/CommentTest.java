package com.example.macpro.pku_map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peter on 2017/12/12.
 */
public class CommentTest {
    private Comment mComment;
    @Before
    public void setUp() throws Exception {
        mComment = new Comment();
        mComment.setCommentID(0);
        mComment.setContent("test");
        mComment.setFatherType("event");
        mComment.setFatherID(1);
        mComment.setPublisherID(2);
        mComment.setUsername("admin");
    }

    @Test
    public void testGetCommentID() throws Exception {
        assertEquals(0, mComment.getCommentID());
    }

    @Test
    public void testGetContent() throws Exception {
        assertEquals("test", mComment.getContent());
    }

    @Test
    public void testGetFatherType() throws Exception {
        assertEquals("event", mComment.getFatherType());
    }

    @Test
    public void testGetFatherID() throws Exception {
        assertEquals(1, mComment.getFatherID());
    }

    @Test
    public void testGetPublisherID() throws Exception {
        assertEquals(2, mComment.getPublisherID());
    }

    @Test
    public void testGetUsername() throws Exception {
        assertEquals("admin", mComment.getUsername());
    }

}