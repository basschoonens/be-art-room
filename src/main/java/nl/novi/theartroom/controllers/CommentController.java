package nl.novi.theartroom.controllers;

import nl.novi.theartroom.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


}
