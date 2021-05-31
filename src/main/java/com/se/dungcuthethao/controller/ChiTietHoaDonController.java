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

import com.se.dungcuthethao.entity.ChiTietHoaDon;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.ChiTietHoaDonService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ChiTietHoaDonController {
	@Autowired
	private ChiTietHoaDonService chiTietHoaDonService;

	public ChiTietHoaDonController(ChiTietHoaDonService chiTietHoaDonService) {
		super();
		this.chiTietHoaDonService = chiTietHoaDonService;
	}
	@GetMapping("/chitiethoadons")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<List<ChiTietHoaDon>>(chiTietHoaDonService.findAdd(), HttpStatus.OK);
	}
	@GetMapping("/chitiethoadons/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		ChiTietHoaDon rs = chiTietHoaDonService.findById(id);
		if (rs != null)
			return new ResponseEntity<ChiTietHoaDon>(rs, HttpStatus.OK);
		return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy chi tiết hóa đơn có id: " + id));
	}
	
}
