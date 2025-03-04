package org.project.trainticketbookingsystem.web;

import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteDTO;
import org.project.trainticketbookingsystem.dto.SearchTicketDTO;
import org.project.trainticketbookingsystem.dto.SegmentDTO;
import org.project.trainticketbookingsystem.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
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
    public ResponseEntity<List<SegmentDTO>> searchRoute(@RequestBody SearchTicketDTO searchTicketDTO) {
        List<RouteDTO> routeDTOs = routeService.searchRoutes(searchTicketDTO);
        List<SegmentDTO> segmentDTOs = routeService.getRequirementSegment(routeDTOs, searchTicketDTO);
        return ResponseEntity.ok(segmentDTOs);
    }

    @GetMapping("/specificRoute/{id}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable Long id) {
        RouteDTO routeDTO;
        try{
            routeDTO = routeService.getRouteById(id);
            return ResponseEntity.ok(routeDTO);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.ok().body("Route deleted successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<RouteDTO> updateRoute(@PathVariable Long id, @RequestBody RouteDTO routeDTO) {
        RouteDTO updatedRoute = routeService.updateRoute(id, routeDTO);
        return ResponseEntity.ok(updatedRoute);
    }

}
