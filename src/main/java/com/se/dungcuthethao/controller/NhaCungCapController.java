package com.se.dungcuthethao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.dungcuthethao.entity.NhaCungCap;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.NhaCungCapService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class NhaCungCapController {
	@Autowired
	private NhaCungCapService nhaCungCapService;

	@Autowired
	public NhaCungCapController(NhaCungCapService nhaCungCapService) {
		super();
		this.nhaCungCapService = nhaCungCapService;
	}
	

	@GetMapping("/nhacungcaps")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<List<NhaCungCap>>(nhaCungCapService.findAdd(), HttpStatus.OK);
	}


	@GetMapping("/nhacungcaps/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		NhaCungCap rs = nhaCungCapService.findById(id);
		if (rs != null)
			return new ResponseEntity<NhaCungCap>(rs, HttpStatus.OK);
		return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy nhà cung cấp có id: " + id));
	}

	@PutMapping(value = "/nhacungcaps/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateKhachHang(@PathVariable("id") Long id, @RequestBody NhaCungCap nhaCungCap) {
		NhaCungCap rs = nhaCungCapService.findById(id);
		if (rs != null) {
			rs.setTen(nhaCungCap.getTen());
			rs.setDiaChi(nhaCungCap.getDiaChi());
			rs.setSdt(nhaCungCap.getSdt());
			rs.setEmail(nhaCungCap.getEmail());
		
			nhaCungCapService.update(rs);
			return new ResponseEntity<NhaCungCap>(nhaCungCap, HttpStatus.OK);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Cập nhật nhà cung cấp không thành công"));
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@DeleteMapping("/nhacungcaps/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
		try {
			nhaCungCapService.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("Xóa thành công nhà cung cấp"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse(
					"Xóa nhà cung cấp không thành công. Chỉ xóa được khi nhà cung cấp này chưa cung cấp sản phẩm nào"));
		}
	}
}
