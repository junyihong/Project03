package com.project.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.backend.dto.StaffDto;
import com.project.backend.dto.UpdatePasswordRequest;
import com.project.backend.entity.Staff;
import com.project.backend.repository.StaffRepository;
import com.project.backend.service.StaffService;

@RestController
@RequestMapping("/api")
public class StaffController {

	private final StaffService staffService;
	private final StaffRepository staffRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public StaffController(StaffService staffService, PasswordEncoder passwordEncoder, StaffRepository staffRepository) {
		this.staffService = staffService;
		this.passwordEncoder = passwordEncoder;
		this.staffRepository = staffRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerStaff(@RequestBody StaffDto staffDto) {
		Staff registeredStaff = staffService.registerStaff(staffDto);

		if (registeredStaff != null) {
			return ResponseEntity.ok("Staff registered successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register staff");
		}
	}

	@GetMapping("/staff")
	public List<Staff> getAllStaffs() {
		return staffService.getAllStaffs();
	}

	@PostMapping("/staff")
	public Staff saveStaff(@RequestBody StaffDto staffDto) {
		// StaffDto를 Staff 엔티티로 변환하여 저장하는 로직
		// staffService.saveStaff()를 호출하여 Staff를 저장하고 반환
		Staff staff = new Staff();
		staff.setEmpNum(staffDto.getEmpNum());
		staff.setEmpId(staffDto.getEmpId());
		staff.setEmpPwd(passwordEncoder.encode(staffDto.getEmpPwd()));
		staff.setDept(staffDto.getDept());
		staff.setPosition(staffDto.getPosition());
		staff.setEmpName(staffDto.getEmpName());
		staff.setBirthDate(staffDto.getBirthDate());
		staff.setPhoneNumber(staffDto.getPhoneNumber());
		staff.setAddress(staffDto.getAddress());
		staff.setEmail(staffDto.getEmail());
		staff.setBankName(staffDto.getBankName());
		staff.setAccountNumber(staffDto.getAccountNumber());

		return staffService.saveStaff(staff);
	}
	
	   // Staff 정보 업데이트를 위한 메서드 추가
    // 프로필 수정 엔드포인트
	@PostMapping("/staff/{empNum}")
    public ResponseEntity<?> updateStaffProfile(@PathVariable Long empNum, @RequestBody StaffDto staffDto) {
        try {
            Optional<Staff> optionalStaff = staffService.findByEmpNum(empNum);
            
            if (optionalStaff.isPresent()) {
                Staff existingStaff = optionalStaff.get();
                
                // 이름과 생년월일은 수정할 수 없도록 제어합니다.
                // 이름과 생년월일을 수정하려면 별도의 엔드포인트나 권한이 필요할 수 있습니다.
                existingStaff.setPhoneNumber(staffDto.getPhoneNumber());
                existingStaff.setAddress(staffDto.getAddress());
                existingStaff.setEmail(staffDto.getEmail());
                existingStaff.setBankName(staffDto.getBankName());
                existingStaff.setAccountNumber(staffDto.getAccountNumber());
                
                // 수정된 정보를 저장
                staffService.saveStaff(existingStaff);
                
                return ResponseEntity.ok("Staff profile updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update staff profile");
        }
    }
	
	
	// 비밀번호 업데이트를 위한 새로운 엔드포인트 추가
	@PostMapping("/staff/{empNum}/update-password")
	public ResponseEntity<?> updateStaffPassword(@PathVariable Long empNum, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
	    try {
	        Optional<Staff> optionalStaff = staffService.findByEmpNum(empNum);

	        if (optionalStaff.isPresent()) {
	            Staff existingStaff = optionalStaff.get();

	            // 현재 비밀번호가 일치하는지 확인
	            if (passwordEncoder.matches(updatePasswordRequest.getCurrentPassword(), existingStaff.getEmpPwd())) {
	                // 새 비밀번호를 암호화하여 저장
	                existingStaff.setEmpPwd(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
	                staffService.saveStaff(existingStaff);

	                return ResponseEntity.ok("Staff password updated successfully");
	            } else {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Staff not found");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update staff password");
	    }
	}



	@DeleteMapping("/staff/{empNum}")
	public ResponseEntity<?> deleteStaff(@PathVariable Long empNum) {
		// staffService.deleteStaffById()를 호출하여 주어진 id에 해당하는 Staff 삭제
		// 삭제가 성공적으로 이루어지면 HttpStatus.OK 반환
		// 삭제 과정에서 예외 발생 시 HttpStatus.INTERNAL_SERVER_ERROR 반환
		try {
			staffService.deleteStaffById(empNum);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}

