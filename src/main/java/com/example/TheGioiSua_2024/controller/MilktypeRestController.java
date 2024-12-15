package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.MilkType;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.MilktypeService;
import com.example.TheGioiSua_2024.Validator.MilkTypeValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Milktype")
public class MilktypeRestController {

  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private MilktypeService milktypeService;

  //http://localhost:1234/api/Milktype/lst
  @GetMapping("/lst")
  public List<MilkType> getAllMilktype() {
    return milktypeService.GetAllMilktype();
  }

  @GetMapping("/lst/{id}")
  public MilkType getMilktype(@PathVariable("id") Long id) {
    return milktypeService.GetMilktypeById(id);
  }

  //http://localhost:1234/api/Milktype/add
  @PostMapping("/add")
  public ResponseEntity<?> addMilktype(@NonNull HttpServletRequest request,
    @RequestBody MilkType milktype) {
    String token = jwtUtilities.getToken(request);
   return milktypeService.AddMilktype(token, milktype);
  }//http://localhost:1234/api/Milktype/update/{id}

  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateMilktype(@PathVariable("id") Long id,
    @RequestBody  MilkType milktype) {
    return milktypeService.UpdateMilktype(id, milktype);
  }//http://localhost:1234/api/Milktype/delete/{id}

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteMilktype(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
   return milktypeService.DeleteMilktype(token, id);
  }

  @GetMapping("/getTypePage")
  public Page<MilkType> getTypePage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return milktypeService.GetMilktypePage(pageable);
  }

  @GetMapping("/getTypePageByName")
  public Page<MilkType> getTypePageByName(@RequestParam("milkTypeName") String milkTypeName,
    @RequestParam("page") int page, @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return milktypeService.GetMilktypePageByName(milkTypeName, pageable);
  }
}
