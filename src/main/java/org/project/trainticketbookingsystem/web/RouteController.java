package org.project.trainticketbookingsystem.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.trainticketbookingsystem.dto.RouteDto;
import org.project.trainticketbookingsystem.dto.SearchTicketDto;
import org.project.trainticketbookingsystem.dto.SegmentDto;
import org.project.trainticketbookingsystem.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<RouteDto> createRoute(@RequestBody RouteDto routeDto) {
        RouteDto createdRoutDto = routeService.createRoute(routeDto);
        return ResponseEntity.ok().body(createdRoutDto);
    }

    @GetMapping("/allRoutes")
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        List<RouteDto> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @PostMapping("/searchRoutes")
    public ResponseEntity<List<SegmentDto>> searchRoute(@RequestBody SearchTicketDto searchTicketDto) {
        List<RouteDto> routeDtos = routeService.searchRoutes(searchTicketDto);
        List<SegmentDto> segmentDtos = routeService.getRequirementSegment(routeDtos, searchTicketDto);
        return ResponseEntity.ok(segmentDtos);
    }

    @GetMapping("/specificRoute/{id}")
    public ResponseEntity<RouteDto> getRouteById(@PathVariable Long id) {
        RouteDto routeDto = routeService.getRouteById(id);
        return ResponseEntity.ok(routeDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.ok().body("Route deleted successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<RouteDto> updateRoute(@PathVariable Long id, @RequestBody RouteDto routeDto) {
        RouteDto updatedRoute = routeService.updateRoute(id, routeDto);
        return ResponseEntity.ok(updatedRoute);
    }
}
