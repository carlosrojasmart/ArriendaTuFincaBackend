package com.arriendatufinca.arriendatufinca.Controllers;

import java.util.List;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.arriendatufinca.arriendatufinca.DTO.RentalRequestDTO;
import com.arriendatufinca.arriendatufinca.Enums.RequestState;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Services.Tenant.RentalRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RentalRequestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RentalRequestService rentalRequestService;

    @InjectMocks
    private RentalRequestController rentalRequestController;

    private RentalRequestDTO rentalRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rentalRequestController).build();

        rentalRequestDTO = new RentalRequestDTO( 
            1L, 1L, 2L, RequestState.PENDING, StatusEnum.ACTIVE
        );
    }

    @Test
    void testCreateRentalRequest() throws Exception {
        when(rentalRequestService.createRentalRequest(any(RentalRequestDTO.class))).thenReturn(rentalRequestDTO);

        mockMvc.perform(post("/api/rental-requests/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(rentalRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.state").value("PENDING"));

        verify(rentalRequestService).createRentalRequest(any(RentalRequestDTO.class));
    }

    @Test
    void testGetRequestsForTenant() throws Exception {
        when(rentalRequestService.getRequestsForCurrentTenant(1L)).thenReturn(List.of(rentalRequestDTO));

        mockMvc.perform(get("/api/rental-requests/tenant/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(rentalRequestService).getRequestsForCurrentTenant(1L);
    }

    @Test
    void testGetRequestsForLandlord() throws Exception {
        when(rentalRequestService.getRequestsForLandlord(2L)).thenReturn(List.of(rentalRequestDTO));

        mockMvc.perform(get("/api/rental-requests/landlord/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(rentalRequestService).getRequestsForLandlord(2L);
    }

    @Test
    void testApproveRentalRequest() throws Exception {
        rentalRequestDTO.setState(RequestState.APPROVED);
        when(rentalRequestService.approveRentalRequest(1L)).thenReturn(rentalRequestDTO);

        mockMvc.perform(put("/api/rental-requests/1/approve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("APPROVED"));

        verify(rentalRequestService).approveRentalRequest(1L);
    }

    @Test
    void testRejectRentalRequest() throws Exception {
        rentalRequestDTO.setState(RequestState.REJECTED);
        when(rentalRequestService.rejectRentalRequest(1L)).thenReturn(rentalRequestDTO);

        mockMvc.perform(put("/api/rental-requests/1/reject"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("REJECTED"));

        verify(rentalRequestService).rejectRentalRequest(1L);
    }
}

