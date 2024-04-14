package nl.novi.theartroom.services;

import nl.novi.theartroom.repositories.CommentRepository;
import nl.novi.theartroom.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

}
