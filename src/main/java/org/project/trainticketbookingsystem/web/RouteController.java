package org.project.trainticketbookingsystem.web;

import lombok.AllArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.dto.SearchTicketDTO;
import org.project.trainticketbookingsystem.dto.SegmentDTO;
import org.project.trainticketbookingsystem.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
@AllArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<RouteDTO> createRoute(@RequestBody RouteDTO routeDTO) {
        RouteDTO createdRoutDTO = routeService.createRoute(routeDTO);
        return ResponseEntity.ok().body(createdRoutDTO);
    }

    @GetMapping("/allRoutes")
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        List<RouteDTO> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @PostMapping("/searchRoutes")
    private ResponseEntity<List<SegmentDTO>> searchRoute(@RequestBody SearchTicketDTO searchTicketDTO) {
        List<RouteDTO> routeDTOs = routeService.searchRoutes(searchTicketDTO);
        List<SegmentDTO> segmentDTOs = routeService.getRequirementSegment(routeDTOs, searchTicketDTO);
        return ResponseEntity.ok(segmentDTOs);
    }
}
