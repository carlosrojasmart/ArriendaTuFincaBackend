package com.arriendatufinca.arriendatufinca.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.arriendatufinca.arriendatufinca.DTO.RatingDTO;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;

class RatingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private RatingDTO ratingDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();

        ratingDTO = new RatingDTO(1L, 5, "Excelente experiencia");
    }

    @Test
    void testRateLandlord() throws Exception {
        when(ratingService.rateLandlord(any(RatingDTO.class))).thenReturn(ratingDTO);

        mockMvc.perform(post("/api/ratings/landlord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ratingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(1L))
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.comment").value("Excelente experiencia"));

        verify(ratingService).rateLandlord(any(RatingDTO.class));
    }

    @Test
    void testRateTenant() throws Exception {
        when(ratingService.rateTenant(any(RatingDTO.class))).thenReturn(ratingDTO);

        mockMvc.perform(post("/api/ratings/tenant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ratingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(1L))
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.comment").value("Excelente experiencia"));

        verify(ratingService).rateTenant(any(RatingDTO.class));
    }

    @Test
    void testRateProperty() throws Exception {
        when(ratingService.rateProperty(any(RatingDTO.class))).thenReturn(ratingDTO);

        mockMvc.perform(post("/api/ratings/property")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ratingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value(1L))
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.comment").value("Excelente experiencia"));

        verify(ratingService).rateProperty(any(RatingDTO.class));
    }
}
