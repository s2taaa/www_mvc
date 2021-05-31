package com.se.dungcuthethao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.dungcuthethao.entity.HoaDon;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.HoaDonService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")

public class HoaDonController {
	@Autowired
	private HoaDonService hoaDonService;

	public HoaDonController(HoaDonService hoaDonService) {
		super();
		this.hoaDonService = hoaDonService;
	}
	
	@GetMapping("/hoadons")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<List<HoaDon>>(hoaDonService.findAdd(), HttpStatus.OK);
	}
	@GetMapping("/hoadons/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		HoaDon rs = hoaDonService.findById(id);
		if (rs != null)
			return new ResponseEntity<HoaDon>(rs, HttpStatus.OK);
		return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy Hóa đơn có id: " + id));
	}


}
