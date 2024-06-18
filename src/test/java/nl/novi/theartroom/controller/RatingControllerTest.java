package nl.novi.theartroom.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.theartroom.dto.ratingdto.RatingOutputWithArtworkDto;
import nl.novi.theartroom.model.Rating;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.ArtworkRepository;
import nl.novi.theartroom.repository.OrderRepository;
import nl.novi.theartroom.repository.RatingRepository;
import nl.novi.theartroom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
        ratingRepository.deleteAll();
        artworkRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("artistUser");
        user.setPassword("password");
        userRepository.save(user);

        Artwork artwork1 = new Artwork();
        artwork1.setTitle("Artwork 1");
        artwork1.setArtist("artistUser");
        artwork1.setDescription("Description 1");
        artwork1.setDateCreated(LocalDate.now());
        artwork1.setGalleryBuyingPrice(500.0);
        artwork1.setEdition("First Edition");
        artwork1.setArtworkType("Type 1");
        artwork1.setUser(user);

        Artwork artwork2 = new Artwork();
        artwork2.setTitle("Artwork 2");
        artwork2.setArtist("artistUser");
        artwork2.setDescription("Description 2");
        artwork2.setDateCreated(LocalDate.now());
        artwork2.setGalleryBuyingPrice(600.0);
        artwork2.setEdition("Second Edition");
        artwork2.setArtworkType("Type 2");
        artwork2.setUser(user);

        artwork1 = artworkRepository.save(artwork1);
        artwork2 = artworkRepository.save(artwork2);

        Rating rating1 = new Rating();
        rating1.setRating(4);
        rating1.setComment("Good");
        rating1.setArtwork(artwork1);
        rating1.setUser(user);

        Rating rating2 = new Rating();
        rating2.setRating(5);
        rating2.setComment("Excellent");
        rating2.setArtwork(artwork2);
        rating2.setUser(user);

        ratingRepository.saveAll(List.of(rating1, rating2));
    }

    @Test
    void getAllRatingsForAdmin() throws Exception {
        String response = mockMvc.perform(get("/ratings/admin")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<RatingOutputWithArtworkDto> ratingOutputList = objectMapper.readValue(response, new TypeReference<>() {});

        assertThat(ratingOutputList).hasSize(2);

        assertThat(ratingOutputList).extracting("rating").containsExactlyInAnyOrder(4, 5);
        assertThat(ratingOutputList).extracting("comment").containsExactlyInAnyOrder("Good", "Excellent");

        assertThat(ratingOutputList).extracting("artworkTitle").containsExactlyInAnyOrder("Artwork 1", "Artwork 2");
        assertThat(ratingOutputList).extracting("artworkArtist").containsExactlyInAnyOrder("artistUser", "artistUser");
    }
}