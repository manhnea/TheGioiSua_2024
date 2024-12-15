package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Packagingunit;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.PackagingunitService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Packagingunit")
public class PackagingunitRestController {

  @Autowired
  private PackagingunitService packagingunitService;
  @Autowired
  private JwtUtilities jwtUtilities;

    //http://localhost:1234/api/Packagingunit/lst
@GetMapping ("/lst")
public List<Packagingunit> getAllPackagingunit() {
    return packagingunitService.getAllPackagingunit();
}
  @GetMapping("/lst/{id}")
  public Packagingunit getPackagingunit(@PathVariable("id") Long id) {
    return packagingunitService.getPackagingunitById(id);
  }

  //http://localhost:1234/api/Packagingunit/add
  @PostMapping("/add")
  public ResponseEntity<?> addPackagingunit(@NonNull HttpServletRequest request,
    @RequestBody Packagingunit packagingunit) {
 
    String token = jwtUtilities.getToken(request);
    return packagingunitService.addPackagingunit(token, packagingunit);
  }

  //http://localhost:1234/api/Packagingunit/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> updatePackagingunit(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id,
    @RequestBody  Packagingunit packagingunit) {
   
    String token = jwtUtilities.getToken(request);
    return packagingunitService.updatePackagingunit(token, id, packagingunit);
  }
//
  //http://localhost:1234/api/Packagingunit/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deletePackagingunit(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    return packagingunitService.deletePackagingunit(token, id);
  }

  @GetMapping("/getPackagingunitPage")
  public Page<Packagingunit> getPackagingunitPage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return packagingunitService.getPackagingunitPage(pageable);
  }
  @GetMapping("/getPackagingunitPageByName")
    public Page<Packagingunit> getPackagingunitPageByName(@RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("packagingunitName") String packagingunitName) {
        Pageable pageable = PageRequest.of(page, size);
        return packagingunitService.getPackagingunitPageByName(packagingunitName, pageable);
    }
}
