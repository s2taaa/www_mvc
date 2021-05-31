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

import com.se.dungcuthethao.entity.SanPham;
import com.se.dungcuthethao.jwt.response.MessageResponse;
import com.se.dungcuthethao.service.SanPhamService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SanPhamController {
	@Autowired
	private SanPhamService sanPhamService;

	public SanPhamController(SanPhamService sanPhamService) {
		super();
		this.sanPhamService = sanPhamService;
	}
	@GetMapping("/sanphams")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<List<SanPham>>(sanPhamService.findAdd(), HttpStatus.OK);
	}
	@GetMapping("/sanphams/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		SanPham rs = sanPhamService.findById(id);
		if (rs != null)
			return new ResponseEntity<SanPham>(rs, HttpStatus.OK);
		return ResponseEntity.badRequest().body(new MessageResponse("Không tìm thấy sản phẩm có id: " + id));
	}
	@PutMapping(value = "/sanphams/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateKhachHang(@PathVariable("id") Long id, @RequestBody SanPham sanPham) {
		SanPham rs = sanPhamService.findById(id);
		if (rs != null) {
			rs.setTen(sanPham.getTen());
			rs.setSoLuong(sanPham.getSoLuong());
			rs.setDonGia(sanPham.getDonGia());
			rs.setLoai(sanPham.getLoai());
			rs.setKichCo(sanPham.getKichCo());
			rs.setMauSac(sanPham.getMauSac());
			rs.setXuatXu(sanPham.getXuatXu());
			rs.setImages(sanPham.getImages());
			rs.setNhaCungCap(sanPham.getNhaCungCap());
			sanPhamService.update(rs);
			return new ResponseEntity<SanPham>(sanPham, HttpStatus.OK);
		}
		return ResponseEntity.badRequest().body(new MessageResponse("Cập nhật sản phẩm không thành công"));
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	@DeleteMapping("/sanphams/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
		try {
			sanPhamService.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("Xóa thành công sản phẩm"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse(
					"Xóa sản phẩm không thành công. Chỉ xóa được khi sản phẩm này này chưa lập hóa đơn nào"));
		}
	}
}
