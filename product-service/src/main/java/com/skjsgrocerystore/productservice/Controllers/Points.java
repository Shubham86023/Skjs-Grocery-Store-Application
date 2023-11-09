package com.skjsgrocerystore.productservice.Controllers;

import com.skjsgrocerystore.productservice.DTO.Point.PointsResponse;
import com.skjsgrocerystore.productservice.Exceptions.ResourceNotFoundException;
import com.skjsgrocerystore.productservice.Services.Points_ops;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/points")
public class Points {

    private final Points_ops pointsOps;

    @GetMapping("/getPoints/{id}")
    public ResponseEntity<List<PointsResponse>> getPoints(@PathVariable int id) {

        List<PointsResponse> response = pointsOps.getPoints(id);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Points Available.");
        }
    }

    @DeleteMapping("/deletePoint/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable int id) {
        if (pointsOps.deletePoint(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
