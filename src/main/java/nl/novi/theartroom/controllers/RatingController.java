package nl.novi.theartroom.controllers;

import nl.novi.theartroom.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ratings")
public class RatingController {


    // TODO: Implement RatingController
    @Autowired
    private RatingService ratingService;




}
